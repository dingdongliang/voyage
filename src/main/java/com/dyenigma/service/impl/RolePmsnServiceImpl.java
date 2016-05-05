package com.dyenigma.service.impl;

import com.dyenigma.entity.BaseDomain;
import com.dyenigma.entity.Permission;
import com.dyenigma.entity.RolePmsn;
import com.dyenigma.service.RolePmsnService;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.StringUtil;
import com.dyenigma.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * topic
 * author: dyenigma
 * create: 2016/4/8 9:23
 */
@Transactional
@Service("rolePmsnService")
public class RolePmsnServiceImpl extends BaseServiceImpl<RolePmsn> implements RolePmsnService {

    private final Logger LOGGER = LoggerFactory.getLogger(RolePmsnServiceImpl.class);

    @Override
    public List<Permission> findAllByRoleId(String roleId) {
        LOGGER.info("开始读取id为" + roleId + "的角色权限信息");
        List<Permission> permissions = new ArrayList<>();
        List<RolePmsn> idList = rolePmsnMapper.findAllByRoleId(roleId);
        for (RolePmsn rolePmsn : idList) {
            Permission permission = permissionMapper.selectByPrimaryKey(rolePmsn.getPmsnId());
            permissions.add(permission);
        }
        return permissions;
    }

    /**
     * 保存选取的权限与角色映射
     * param roleId
     * param checkedIds
     * return
     */
    @Override
    public boolean savePermission(String roleId, String checkedIds) {
        String userId = Constants.getCurrendUser().getUserId();

        //盛放没有修改以前的角色权限对应记录，用于在修改后删除多余的记录
        Map<String, RolePmsn> map = new HashMap<>();

        //获取某角色ID对应的所有权限
        List<RolePmsn> rolePermissionList = rolePmsnMapper.findAllByRoleId(roleId);
        //循环处理这些权限与角色的对应记录，逐一放入map中，然后设置该记录为过期，用于标记删除
        for (RolePmsn rolePmsn : rolePermissionList) {
            String permissionId = rolePmsn.getPmsnId();
            //对于角色权限对应记录来说，权限ID是互斥的，所以当成key处理
            map.put(permissionId.toString(), rolePmsn);
            //设置所有记录过期
            updRolePermission(userId, rolePmsn, Constants.PERSISTENCE_DELETE_STATUS);
        }
        //开始处理修改后提交的对应数据，checkedIds为权限集合
        if (null != checkedIds && !"".equals(checkedIds)) {
            String[] ids = checkedIds.split(",");
            for (String id : ids) {
                if (StringUtil.isEmpty(id)) {
                    continue;
                }
                //然后看这些权限ID是否在map中
                RolePmsn rolePmsn = map.get(id);
                if (rolePmsn != null) {
                    //如果在map中，说明在数据库中有记录，把状态改成正常
                    updRolePermission(userId, rolePmsn, Constants.PERSISTENCE_STATUS);
                } else {
                    //如果不在msp中，说明该对应记录在数据库中没有，要新增
                    rolePmsn = new RolePmsn();
                    BaseDomain.createLog(rolePmsn, userId);
                    rolePmsn.setStatus(Constants.PERSISTENCE_STATUS);
                    rolePmsn.setPmsnId(id);
                    rolePmsn.setRoleId(roleId);
                    rolePmsn.setRpId(UUIDUtils.getUUID());
                    rolePmsnMapper.insert(rolePmsn);
                }
                //同时删除已经处理过的map值
                map.remove(id);
            }
        }
        //当所有值都处理完毕以后，剩下的map值就是：原来有对应关系，修改后没有对应关系，删除之
        for (Map.Entry<String, RolePmsn> entry : map.entrySet()) {
            rolePmsnMapper.deleteByPrimaryKey(entry.getValue().getRpId());
        }

        return true;
    }

    private void updRolePermission(String userId, RolePmsn rolePermission, String status) {
        BaseDomain.editLog(rolePermission, userId);
        rolePermission.setStatus(status);
        rolePmsnMapper.updateByPrimaryKeySelective(rolePermission);
    }

    @Override
    public boolean setDefaultRole(String roleId) {
        String userId = Constants.getCurrendUser().getUserId();

        //获取所有的默认权限
        List<Permission> pList = permissionMapper.findAllDefault();
        //获取默认权限的父节点
        Set<Permission> pSet = new HashSet<>();
        for (Permission permission : pList) {
            pSet.add(permission);
            getPrntPmsn(pSet, permission);
        }
        //把所有的权限节点映射到角色上
        for (Permission pmsn : pSet) {
            RolePmsn rolePmsn = new RolePmsn();
            BaseDomain.createLog(rolePmsn, userId);
            rolePmsn.setRpId(UUIDUtils.getUUID());
            rolePmsn.setRoleId(roleId);
            rolePmsn.setPmsnId(pmsn.getPmsnId());
            rolePmsn.setStatus(Constants.PERSISTENCE_STATUS);
            rolePmsnMapper.insert(rolePmsn);
        }

        return true;
    }

    private void getPrntPmsn(Set<Permission> pSet, Permission pmsn) {
        String pId = pmsn.getPrntId();
        if (!"0".equals(pId)) {
            Permission prntPmsn = permissionMapper.selectByPrimaryKey(pId);
            pSet.add(prntPmsn);
            getPrntPmsn(pSet, prntPmsn);
        }
    }
}
