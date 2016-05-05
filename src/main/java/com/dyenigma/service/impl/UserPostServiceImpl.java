package com.dyenigma.service.impl;

import com.dyenigma.entity.BaseDomain;
import com.dyenigma.entity.UserPost;
import com.dyenigma.service.UserPostService;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.StringUtil;
import com.dyenigma.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * author  dyenigma
 * date 2016/4/28 10:37
 */
@Transactional
@Service("userPostService")
public class UserPostServiceImpl extends BaseServiceImpl<UserPost> implements UserPostService {
    /**
     * Description: 根据岗位查询用户ID集合
     * Name:findByPostId
     * Author:dyenigma
     * Time:2016/4/28 10:36
     * param:[postId]
     * return:java.util.List<com.dyenigma.entity.Integer>
     *
     * @param postId
     */
    @Override
    public List<String> findByPostId(String postId) {
        List<UserPost> upList = userPostMapper.findByPostId(postId);

        List<String> idList = upList.stream().map(UserPost::getUserId).collect(Collectors.toList());

        return idList;
    }

    /**
     * 保存分配角色岗位
     * 处理逻辑：根据用户查找所有的已有角色信息，然后全部删除，最后赋予新角色
     * param userId 用户id
     * param checkedIds 岗位ID集合
     * return
     */
    @Override
    public boolean saveUserPost(String userId, String checkedIds) {

        String currentUserId = Constants.getCurrendUser().getUserId();

        //盛放没有修改以前的对应记录，用于在修改后删除多余的记录
        Map<String, UserPost> map = new HashMap<>();

        //获取ID对应的所有权限
        List<UserPost> urList = userPostMapper.findAllByUserId(userId);
        //循环处理这些对应记录，逐一放入map中，然后设置该记录为过期，用于标记删除
        for (UserPost userPost : urList) {
            //对于该对应记录来说，互斥的ID当成key处理
            map.put(userPost.getPostId(), userPost);
            //设置所有记录过期
            updUserPost(currentUserId, userPost, Constants.PERSISTENCE_DELETE_STATUS);
        }

        //开始处理修改后提交的对应数据，checkedIds为权限集合
        if (null != checkedIds && !"".equals(checkedIds)) {
            String[] ids = checkedIds.split(",");
            for (String id : ids) {
                if (StringUtil.isEmpty(id)) {
                    continue;
                }
                //然后看这些ID是否在map中
                UserPost userPost = map.get(id);
                if (userPost != null) {
                    //如果在map中，说明在数据库中有记录，把状态改成正常
                    updUserPost(userId, userPost, Constants.PERSISTENCE_STATUS);
                } else {
                    //如果不在msp中，说明该对应记录在数据库中没有，要新增
                    userPost = new UserPost();
                    BaseDomain.createLog(userPost, userId);
                    userPost.setStatus(Constants.PERSISTENCE_STATUS);
                    //循环处理的ID
                    userPost.setPostId(id);
                    //传递过来的Id
                    userPost.setUserId(userId);
                    userPost.setUpId(UUIDUtils.getUUID());
                    userPostMapper.insert(userPost);
                }
                //同时删除已经处理过的map值
                map.remove(id);
            }
        }
        //当所有值都处理完毕以后，剩下的map值就是：原来有对应关系，修改后没有对应关系，删除之
        for (Map.Entry<String, UserPost> entry : map.entrySet()) {
            userPostMapper.deleteByPrimaryKey(entry.getValue().getUpId());
        }

        return true;
    }

    private void updUserPost(String userId, UserPost userPost, String status) {
        BaseDomain.editLog(userPost, userId);
        userPost.setStatus(status);
        userPostMapper.updateByPrimaryKeySelective(userPost);
    }

    /**
     * Description: 根据用户ID查询所有的用户角色映射关系，用于预设
     * Name:findByUserId
     * Author:Dyenigma
     * Time:2016/4/28 22:06
     * param:[userId]
     * return:java.util.List<com.dyenigma.entity.UserPost>
     */
    @Override
    public List<UserPost> findByUserId(String userId) {
        return userPostMapper.findAllByUserId(userId);
    }
}
