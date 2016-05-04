package com.dyenigma.service;

import com.dyenigma.entity.Role;
import com.dyenigma.entity.UserRole;

import java.util.List;

/**
 * topic
 * author: dyenigma
 * create: 2016/4/8 9:22
 */
public interface UserRoleService extends BaseService<UserRole> {

    /**
     * 好代码自己会说话
     * param roleId
     * return
     */
    List<Role> findAllByUserId(String userId);

    /**
     * Description: 根据用户ID获取所有用户角色映射关系
     * Name:findByUserId
     * Author:Dyenigma
     * Time:2016/4/28 22:13
     * param:[userId]
     * return:java.util.List<com.dyenigma.entity.UserRole>
     */
    List<UserRole> findByUserId(String userId);

    /**
     * 保存分配角色权限
     * param roleId 角色id
     * param checkedIds 菜单权限ID集合
     * return
     */
    boolean saveRole(String userId, String checkedIds);

}
