package com.dyenigma.service;

import com.dyenigma.entity.User;
import com.dyenigma.model.TreeModel;
import com.dyenigma.utils.PageUtil;

import java.util.List;
import java.util.Set;

public interface UserService extends BaseService<User> {
    /**
     * 根据名字查询用户,用于shiro权限控制
     * param name
     * return
     */
    User getUserByName(String name);

    /**
     * 持久化用户信息
     * param user
     * return
     */
    boolean persistenceUser(User user);

    /**
     * 分页查, String postId询用户信息
     * param pageUtil
     * return
     */
    List<User> allUserByPage(PageUtil pageUtil);

    /**
     * Description: 按用户ID集合查询用户信息，分页，用于获取岗位、部门、公司的所有用户信息
     * Name:findUserByPage
     * Author:dyenigma
     * Time:2016/4/28 10:20
     * param:[pageUtil, idList]
     * return:java.util.List<com.dyenigma.entity.User>
     */
    List<User> findUserByPage(PageUtil pageUtil, Set<String> idList);


    boolean delUser(String userId);


    /**
     * Description: 获取所有可添加用户的岗位，按树状结构展示
     * Name:getPostList
     * Author:Dyenigma
     * Time:2016/4/27 23:24
     * param:[list]
     * return:java.util.List<com.dyenigma.model.TreeModel>
     */
    List<TreeModel> getPostList(List<TreeModel> list);

}