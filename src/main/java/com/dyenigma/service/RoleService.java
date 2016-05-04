package com.dyenigma.service;

import com.dyenigma.entity.Role;
import com.dyenigma.utils.PageUtil;

import java.util.List;

/**
 * topic
 * author: dyenigma
 * create: 2016/4/8 9:22
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 分页查询角色信息
     * param pageUtil
     * return
     */
    List<Role> findAllRoleList(PageUtil pageUtil);

    /**
     * 持久化角色信息（包含新增或修改）
     * param role
     * return
     */

    boolean persistenceRole(Role role);

    /**
     * 先判断是否有级联关系，再删除角色信息
     * param id
     * return
     */
    boolean delRole(String id);
}
