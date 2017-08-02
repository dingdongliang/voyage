package com.dyenigma.service.impl;

import com.dyenigma.dao.PrjRoleMapper;
import com.dyenigma.dao.PrjUserMapper;
import com.dyenigma.dao.ProjectMapper;
import com.dyenigma.entity.BaseDomain;
import com.dyenigma.entity.PrjRole;
import com.dyenigma.entity.PrjUser;
import com.dyenigma.entity.Project;
import com.dyenigma.entity.User;
import com.dyenigma.service.ProjectService;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.StringUtil;
import com.dyenigma.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * author  dyenigma
 * date 2016/4/29 16:18
 */
@Transactional
@Service("projectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService {
    @Autowired
    protected ProjectMapper projectMapper;
    @Autowired
    protected PrjRoleMapper prjRoleMapper;
    @Autowired
    protected PrjUserMapper prjUserMapper;

    /**
     * Description:查询公司所属项目组信息
     * Name:getPrjByCoId
     * Author:dyenigma
     * Time:2016/4/29 16:02
     * param:[coId]
     * return:java.util.List<com.dyenigma.entity.Project>
     */
    @Override
    public List<Project> getPrjByCoId(String coId) {
        return projectMapper.getPrjByCoId(coId);
    }

    /**
     * Description: 实例化项目组信息
     * Name:persistencePrj
     * Author:dyenigma
     * Time:2016/4/29 16:02
     * param:[prj]
     * return:boolean
     */
    @Override
    public boolean persistencePrj(Project prj) {
        String userId = Constants.getCurrendUser().getUserId();

        if (StringUtil.isEmpty(prj.getPrjId())) {
            BaseDomain.createLog(prj, userId);
            prj.setStatus(Constants.PERSISTENCE_STATUS);
            prj.setPrjId(UUIDUtils.getUUID());
            baseMapper.insert(prj);
        } else {

            BaseDomain.editLog(prj, userId);
            baseMapper.updateByPrimaryKeySelective(prj);
        }
        return true;
    }

    /**
     * Description: 删除项目组
     * Name:delPrj
     * Author:dyenigma
     * Time:2016/4/29 16:04
     * param:[prjId]
     * return:boolean
     */
    @Override
    public boolean delPrj(String prjId) {

        List<PrjUser> prjUserList = prjUserMapper.getPrjUserByPrjId(prjId);
        if (prjUserList.size() > 0) {
            return false;
        } else {
            List<PrjRole> prjRoleList = prjRoleMapper.findAllByPrjId(prjId);
            for (PrjRole prjRole : prjRoleList) {
                baseMapper.deleteByPrimaryKey(prjRole.getPrId());
            }

            Project prj = projectMapper.selectByPrimaryKey(prjId);
            prj.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
            return baseMapper.updateByPrimaryKey(prj) > 0;
        }
    }

    /**
     * Description: 保存分配给项目组的角色
     * Name:savePrjRole
     * Author:dyenigma
     * Time:2016/4/29 16:17
     * param:[prjId, checkedIds]
     * return:boolean
     */
    @Override
    public boolean savePrjRole(String prjId, String checkedIds) {
        String userId = Constants.getCurrendUser().getUserId();

        //盛放没有修改以前的对应记录，用于在修改后删除多余的记录
        Map<String, PrjRole> map = new HashMap<>();

        //获取某ID对应的所有角色
        List<PrjRole> prjRoleList = prjRoleMapper.findAllByPrjId(prjId);
        //循环处理这些岗位与角色的对应记录，逐一放入map中，然后设置该记录为过期，用于标记删除
        for (PrjRole prjRole : prjRoleList) {
            String roleId = prjRole.getRoleId();

            //对于岗位角色对应记录来说，角色ID是互斥的，所以当成key处理
            map.put(roleId.toString(), prjRole);
            //设置所有记录过期
            updPrjRole(userId, prjRole, Constants.PERSISTENCE_DELETE_STATUS);
        }
        //开始处理修改后提交的对应数据，checkedIds为权限集合
        if (null != checkedIds && !"".equals(checkedIds)) {
            String[] ids = checkedIds.split(",");
            for (String id : ids) {
                if (StringUtil.isEmpty(id)) {
                    continue;
                }
                //然后看这些角色ID是否在map中
                PrjRole prjRole = map.get(id);
                if (prjRole != null) {
                    //如果在map中，说明在数据库中有记录，把状态改成正常
                    updPrjRole(userId, prjRole, Constants.PERSISTENCE_STATUS);
                } else {
                    //如果不在msp中，说明该对应记录在数据库中没有，要新增
                    prjRole = new PrjRole();
                    BaseDomain.createLog(prjRole, userId);
                    prjRole.setStatus(Constants.PERSISTENCE_STATUS);
                    prjRole.setRoleId(id);
                    prjRole.setPrjId(prjId);
                    prjRole.setPrId(UUIDUtils.getUUID());
                    prjRoleMapper.insert(prjRole);
                }
                //同时删除已经处理过的map值
                map.remove(id);
            }
        }
        //当所有值都处理完毕以后，剩下的map值就是：原来有对应关系，修改后没有对应关系，删除之
        for (Map.Entry<String, PrjRole> entry : map.entrySet()) {
            prjRoleMapper.deleteByPrimaryKey(entry.getValue().getPrId());
        }

        return true;
    }

    private void updPrjRole(String userId, PrjRole prjRole, String status) {
        BaseDomain.editLog(prjRole, userId);
        prjRole.setStatus(status);
        prjRoleMapper.updateByPrimaryKeySelective(prjRole);
    }

    /**
     * Description: 查找项目组成员
     * Name:searchPrjUser
     * Author:dyenigma
     * Time:2016/4/29 16:23
     * param:[prjId]
     * return:java.util.List<com.dyenigma.entity.User>
     */
    @Override
    public List<User> searchPrjUser(String prjId) {
        //首先查找prjUser表，找到对应关系，获取userId
        //然后使用userId查询用户集合返回
        return null;
    }


    /**
     * Description: 保存分配给项目组的角色
     * Name:savePrjRole
     * Author:dyenigma
     * Time:2016/4/29 16:17
     * param:[prjId, checkedIds]
     * return:boolean
     */
    @Override
    public boolean savePrjUsers(String prjId, String checkedIds) {
        String userId = Constants.getCurrendUser().getUserId();

        //盛放没有修改以前的对应记录，用于在修改后删除多余的记录
        Map<String, PrjUser> map = new HashMap<>();

        //获取某ID对应的所有角色
        List<PrjUser> prjUserList = prjUserMapper.getPrjUserByPrjId(prjId);
        //循环处理这些岗位与角色的对应记录，逐一放入map中，然后设置该记录为过期，用于标记删除
        for (PrjUser prjUser : prjUserList) {
            String roleId = prjUser.getUserId();

            //对于岗位角色对应记录来说，角色ID是互斥的，所以当成key处理
            map.put(roleId.toString(), prjUser);
            //设置所有记录过期
            updPrjUser(userId, prjUser, Constants.PERSISTENCE_DELETE_STATUS);
        }
        //开始处理修改后提交的对应数据，checkedIds为权限集合
        if (null != checkedIds && !"".equals(checkedIds)) {
            String[] ids = checkedIds.split(",");
            for (String id : ids) {
                if (StringUtil.isEmpty(id)) {
                    continue;
                }
                //然后看这些角色ID是否在map中
                PrjUser prjUser = map.get(id);
                if (prjUser != null) {
                    //如果在map中，说明在数据库中有记录，把状态改成正常
                    updPrjUser(userId, prjUser, Constants.PERSISTENCE_STATUS);
                } else {
                    //如果不在msp中，说明该对应记录在数据库中没有，要新增
                    prjUser = new PrjUser();
                    BaseDomain.createLog(prjUser, userId);
                    prjUser.setStatus(Constants.PERSISTENCE_STATUS);
                    prjUser.setUserId(id);
                    prjUser.setPrjId(prjId);
                    prjUser.setPuId(UUIDUtils.getUUID());
                    prjUserMapper.insert(prjUser);
                }
                //同时删除已经处理过的map值
                map.remove(id);
            }
        }
        //当所有值都处理完毕以后，剩下的map值就是：原来有对应关系，修改后没有对应关系，删除之
        for (Map.Entry<String, PrjUser> entry : map.entrySet()) {
            baseMapper.deleteByPrimaryKey(entry.getValue().getPuId());
        }

        return true;
    }

    private void updPrjUser(String userId, PrjUser prjUser, String status) {
        BaseDomain.editLog(prjUser, userId);
        prjUser.setStatus(status);
        prjUserMapper.updateByPrimaryKeySelective(prjUser);
    }
}
