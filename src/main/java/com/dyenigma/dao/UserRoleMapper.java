package com.dyenigma.dao;

import com.dyenigma.entity.UserRole;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * Description: 获取某个用户的所有角色信息
     * Name:findAllByUserId
     * Author:Dyenigma
     * Time:2016/4/23 10:48
     * param:[userId]
     * return:java.util.List<com.dyenigma.entity.UserRole>
     */
    List<UserRole> findAllByUserId(String userId);

    List<UserRole> findAllByRoleId(String roleId);
}