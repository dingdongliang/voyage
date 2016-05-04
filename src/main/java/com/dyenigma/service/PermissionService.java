/**
 * Title: PermissionService.java
 * Package com.dyenigma.service
 * author dingdongliang
 * date 2015年9月14日 下午4:49:28
 * version V1.0
 * Copyright (c) 2015,dyenigma@163.com All Rights Reserved.
 */

package com.dyenigma.service;

import com.dyenigma.dao.BaseMapper;
import com.dyenigma.entity.Permission;
import com.dyenigma.model.MenuModel;
import com.dyenigma.model.MultiMenu;
import com.dyenigma.model.TreeModel;

import java.util.List;

/**
 * ClassName: PermissionService
 * Description: 权限类业务处理
 * author dingdongliang
 * date 2015年9月14日 下午4:49:28
 */

public interface PermissionService extends BaseMapper<Permission> {
    /**
     * Title: createMenu
     * Description: 获取当前用户权限并拼接菜单信息
     * param   type
     * param return 参数
     * return List<MenuModel> 返回类型
     * throws
     */
    List<MenuModel> createMenu();

    /**
     * Title: getShiro
     * Description: 获取当前用户的权限, type标志查询是菜单项(F)还是操作项(O)
     * param   type
     * param return 参数
     * return List<Permission> 返回类型
     * throws
     */
    List<Permission> getShiro(String type);

    /**
     * Title: findByPid
     * Description: 根据父类id获取子类菜单
     * param   pid
     * param return 参数
     * return List<TreeGridModel> 返回类型
     * throws
     */
    List<Permission> findByPid(String pid);


    /**
     * 获取所有的权限，用于角色权限分配，尝试替代finaAllMenu方法
     * return
     */
    List<MultiMenu> multiMenu();


    /**
     * Title: deleteById
     * Description: 删除某个节点菜单(更新状态为I)
     * param   id
     * param return 参数
     * return boolean 返回类型
     * throws
     */
    boolean deleteById(String id);

    /**
     * Title: findSuperFunc
     * Description: 获取所有可添加子项的菜单
     * param return 参数
     * return List<TreeModel> 返回类型
     * throws
     */
    List<TreeModel> findSuperFunc();

    /**
     * Title: persistenceFunction
     * Description:持久化处理Permission
     * param   permission
     * param return 参数
     * return boolean 返回类型
     * throws
     */
    boolean persistenceFunction(Permission permission);
}
