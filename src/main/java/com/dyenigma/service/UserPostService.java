package com.dyenigma.service;

import com.dyenigma.entity.UserPost;

import java.util.List;

/**
 * Description:
 * author  dyenigma
 * date 2016/4/28 10:35
 */
public interface UserPostService extends BaseService<UserPost> {
    /**
     * Description: 根据岗位查询用户ID集合
     * Name:findByPostId
     * Author:dyenigma
     * Time:2016/4/28 10:36
     * param:[postId]
     * return:java.util.List<com.dyenigma.entity.Integer>
     */
    List<String> findByPostId(String postId);

    /**
     * Description: 根据用户ID查询所有的用户角色映射关系，用于预设
     * Name:findByUserId
     * Author:Dyenigma
     * Time:2016/4/28 22:06
     * param:[userId]
     * return:java.util.List<com.dyenigma.entity.UserPost>
     */
    List<UserPost> findByUserId(String userId);


    /**
     * 保存分配用户岗位
     * param userId 用户id
     * param checkedIds 岗位ID集合
     * return
     */
    boolean saveUserPost(String userId, String checkedIds);
}
