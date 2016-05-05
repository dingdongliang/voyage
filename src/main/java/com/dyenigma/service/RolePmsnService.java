package com.dyenigma.service;

import com.dyenigma.entity.Permission;
import com.dyenigma.entity.RolePmsn;

import java.util.List;

/**
 * topic
 * author: dyenigma
 * create: 2016/4/8 9:22
 */
public interface RolePmsnService extends BaseService<RolePmsn> {

    /**
     * 好代码自己会说话
     * param roleId
     * return
     */
    List<Permission> findAllByRoleId(String roleId);

    /**
     * 保存分配角色权限
     * param roleId 角色id
     * param checkedIds 菜单权限ID集合
     * return
     */
    boolean savePermission(String roleId, String checkedIds);

    boolean setDefaultRole(String roleId);

}
