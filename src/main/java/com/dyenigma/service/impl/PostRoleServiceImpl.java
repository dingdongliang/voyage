package com.dyenigma.service.impl;

import com.dyenigma.entity.BaseDomain;
import com.dyenigma.entity.PostRole;
import com.dyenigma.service.PostRoleService;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.StringUtil;
import com.dyenigma.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * author  dyenigma
 * date 2016/4/27 17:31
 */
@Transactional
@Service("postRoleService")
public class PostRoleServiceImpl extends BaseServiceImpl<PostRole> implements PostRoleService {
    /**
     * 保存分配岗位角色
     * param postId 岗位id
     * param checkedIds 角色ID集合
     * return
     */
    @Override
    public boolean savePostRole(String postId, String checkedIds) {

        String userId = Constants.getCurrendUser().getUserId();

        //盛放没有修改以前的岗位角色对应记录，用于在修改后删除多余的记录
        Map<String, PostRole> map = new HashMap<>();

        //获取某岗位ID对应的所有角色
        List<PostRole> postRoleList = postRoleMapper.findAllByPostId(postId);
        //循环处理这些岗位与角色的对应记录，逐一放入map中，然后设置该记录为过期，用于标记删除
        for (PostRole postRole : postRoleList) {
            String roleId = postRole.getRoleId();

            //对于岗位角色对应记录来说，角色ID是互斥的，所以当成key处理
            map.put(roleId.toString(), postRole);
            //设置所有记录过期
            updPostRole(userId, postRole, Constants.PERSISTENCE_DELETE_STATUS);
        }
        //开始处理修改后提交的对应数据，checkedIds为权限集合
        if (null != checkedIds && !"".equals(checkedIds)) {
            String[] ids = checkedIds.split(",");
            for (String id : ids) {
                if (StringUtil.isEmpty(id)) {
                    continue;
                }
                //然后看这些角色ID是否在map中
                PostRole postRole = map.get(id);
                if (postRole != null) {
                    //如果在map中，说明在数据库中有记录，把状态改成正常
                    updPostRole(userId, postRole, Constants.PERSISTENCE_STATUS);
                } else {
                    //如果不在msp中，说明该对应记录在数据库中没有，要新增
                    postRole = new PostRole();
                    BaseDomain.createLog(postRole, userId);
                    postRole.setStatus(Constants.PERSISTENCE_STATUS);
                    postRole.setRoleId(id);
                    postRole.setPostId(postId);
                    postRole.setPrId(UUIDUtils.getUUID());
                    postRoleMapper.insert(postRole);
                }
                //同时删除已经处理过的map值
                map.remove(id);
            }
        }
        //当所有值都处理完毕以后，剩下的map值就是：原来有对应关系，修改后没有对应关系，删除之
        for (Map.Entry<String, PostRole> entry : map.entrySet()) {
            postRoleMapper.deleteByPrimaryKey(entry.getValue().getPrId());
        }

        return true;
    }

    private void updPostRole(String userId, PostRole postRole, String status) {
        BaseDomain.editLog(postRole, userId);
        postRole.setStatus(status);
        postRoleMapper.updateByPrimaryKeySelective(postRole);
    }


    @Override
    public List<PostRole> getPostRoleByPostId(String postId) {
        return postRoleMapper.findAllByPostId(postId);
    }
}
