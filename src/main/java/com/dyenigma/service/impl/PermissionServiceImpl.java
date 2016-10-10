/**
 * Title: PermissionServiceImpl.java
 * Package com.dyenigma.service.impl
 * author dingdongliang
 * date 2015年9月14日 下午4:52:52
 * version V1.0
 * Copyright (c) 2015,dyenigma@163.com All Rights Reserved.
 */

package com.dyenigma.service.impl;

import com.dyenigma.dao.PermissionMapper;
import com.dyenigma.dao.RolePmsnMapper;
import com.dyenigma.dao.UserPmsnMapper;
import com.dyenigma.entity.BaseDomain;
import com.dyenigma.entity.Permission;
import com.dyenigma.model.MenuModel;
import com.dyenigma.model.MultiMenu;
import com.dyenigma.model.TreeModel;
import com.dyenigma.service.PermissionService;
import com.dyenigma.shiro.ShiroUser;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.StringUtil;
import com.dyenigma.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * author dingdongliang
 * ClassName: PermissionServiceImpl
 * Description: 权限业务处理实现类
 * date 2015年9月14日 下午4:52:52
 */
@Transactional
@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    private final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);
    @Autowired
    protected PermissionMapper permissionMapper;
    @Autowired
    protected RolePmsnMapper rolePmsnMapper;
    @Autowired
    protected UserPmsnMapper userPmsnMapper;

    @Override
    public List<MenuModel> createMenu() {

        List<Permission> pList = getShiro(Constants.PMSN_M);

        // 用于存放根目录的List
        List<MenuModel> parentList = new ArrayList<>();
        // 循环的逻辑：首先遍历所有记录，当PrntId为空时，该记录为根目录，因菜单格式布局，只显示两级菜单
        for (Permission parent : pList) {
            String id = String.valueOf(parent.getPmsnId());
            if ("0".equals(parent.getPrntId())) {
                MenuModel menuModel = new MenuModel();
                menuModel.setId(id);
                menuModel.setName(String.valueOf(parent.getPmsnName()));
                menuModel.setIconCls(String.valueOf(parent.getIconCls()));
                menuModel.setUrl(String.valueOf(parent.getPmsnUrl()));
                List<MenuModel> childList = new ArrayList<>();
                for (Permission child : pList) {
                    MenuModel menuChildModel = new MenuModel();
                    String sid = String.valueOf(child.getPrntId());
                    if (sid.equals(id)) {
                        menuChildModel.setId(String.valueOf(child.getPmsnId()));
                        menuChildModel.setPid(sid);
                        menuChildModel.setName(String.valueOf(child.getPmsnName()));
                        menuChildModel.setIconCls(String.valueOf(child.getIconCls()));
                        menuChildModel.setUrl(String.valueOf(child.getPmsnUrl()));
                        childList.add(menuChildModel);
                    }
                }
                menuModel.setChild(childList);
                parentList.add(menuModel);
            }
        }
        return parentList;
    }

    @Override
    public List<Permission> getShiro(String type) {
        ShiroUser user = Constants.getCurrendUser();
        LOGGER.debug("获取权限user====>" + user);
        List<Permission> pList;
        // 超级管理员默认拥有所有功能权限
        if (Constants.SYSTEM_ADMINISTRATOR.equals(user.getAccount())) {
            pList = permissionMapper.adminMenu(type);
        } else {
            pList = permissionMapper.usersMenu(user.getUserId(), type);
        }
        return pList;
    }

    @Override
    public List<Permission> findByPid(String pid) {
        List<Permission> pList = StringUtil.isEmpty(pid) ?
                permissionMapper.findRootMenu() : permissionMapper.findByPid(pid);
        pList.stream().filter(permission -> StringUtil.isEmpty(pid)).forEach(permission -> permission.setPrntId("0"));
        return pList;
    }

    /**
     * 删除菜单
     * 1.如果包含子菜单，不能删除
     * 2.满足上面的条件，先删除用户权限映射和角色权限映射后再删除
     * param id
     * return
     */
    @Override
    public boolean deleteById(String id) {

        //如果包含有子菜单，则不能删除
        List<Permission> pList = permissionMapper.findByPid(id);

        if (pList.size() > 0) {
            return false;
        } else {
            userPmsnMapper.delByPmsnId(id);//删除用户权限映射
            rolePmsnMapper.delByPmsnId(id);//删除角色权限映射
            Permission pmsn = baseMapper.selectByPrimaryKey(id);
            pmsn.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
            return baseMapper.updateByPrimaryKey(pmsn) > 0;
        }
    }


    @Override
    public List<TreeModel> findSuperFunc() {
        List<Permission> pList = permissionMapper.findSuperFunc();
        return permToTree("0", pList);
    }

    //递归转化成菜单模型，支持无限级菜单
    private List<TreeModel> permToTree(String id, List<Permission> list) {
        List<TreeModel> menuList = new ArrayList<>();
        list.stream().filter(perm -> id.equals(perm.getPrntId())).forEach(perm -> {
            if (Constants.TREE_STATUS_OPEN.equals(perm.getState())) {
                return; //lambda表达式循环用return跳到下一项，相当于continue
            } else {
                TreeModel menu = new TreeModel();
                menu.setState(Constants.TREE_STATUS_OPEN); //这里必须关闭节点，否则会出现无限节点
                menu.setId(perm.getPmsnId());
                menu.setPid("0".equals(perm.getPrntId()) ? "" : perm.getPrntId());
                menu.setIconCls(perm.getIconCls());
                menu.setText(perm.getPmsnName());
                menu.setChildren(permToTree(perm.getPmsnId(), list));
                menuList.add(menu);
            }
        });
        return menuList;
    }


    @Override
    public boolean persistenceFunction(Permission permission) {
        String userId = Constants.getCurrendUser().getUserId();
        if (StringUtil.isEmpty(permission.getPmsnId())) {
            BaseDomain.createLog(permission, userId);
            permission.setStatus(Constants.PERSISTENCE_STATUS);
            if (Constants.PMSN_M.equals(permission.getPmsnType())) {
                permission.setState(Constants.TREE_STATUS_CLOSED);
            } else {
                permission.setState(Constants.TREE_STATUS_OPEN);
            }
            //因permission的Pid是Integer类型，默认为null，要改成0
            if (StringUtil.isEmpty(permission.getPrntId())) {
                permission.setPrntId("0");
            }
            permission.setPmsnId(UUIDUtils.getUUID());
            baseMapper.insert(permission);
        } else {
            //这里还要考虑如果修改菜单名称，同步修改其子菜单对应的prntName名称，关系不大
            if (Constants.PMSN_M.equals(permission.getPmsnType())) {
                permission.setState(Constants.TREE_STATUS_CLOSED);
            } else {
                permission.setState(Constants.TREE_STATUS_OPEN);
            }
            BaseDomain.editLog(permission, userId);
            baseMapper.updateByPrimaryKeySelective(permission);
        }
        return true;
    }

    /**
     * 获取所有的权限，用于角色权限分配
     * return
     */
    @Override
    public List<MultiMenu> multiMenu() {
        List<Permission> list = permissionMapper.findAllMenu();
        return permToMenu("0", list);
    }

    //递归转化成菜单模型，支持无限极菜单模型
    private List<MultiMenu> permToMenu(String id, List<Permission> list) {
        List<MultiMenu> menuList = new ArrayList<>();
        list.stream().filter(perm -> id.equals(perm.getPrntId())).forEach(perm -> {
            MultiMenu menu = new MultiMenu();
            menu.setId(perm.getPmsnId());
            menu.setPid("0".equals(perm.getPrntId()) ? "" : perm.getPrntId());
            menu.setIconCls(perm.getIconCls());
            menu.setName(perm.getPmsnName());
            menu.setPath(perm.getPmsnUrl());
            menu.setMyid(perm.getPmsnCode());
            menu.setpName(perm.getPrntName());
            menu.setSort(perm.getSort() + "");
            menu.setType(perm.getPmsnType());
            menu.setDescription(perm.getPmsnDesc());
            menu.setIfUsed(perm.getIsUsed());
            menu.setChildren(permToMenu(perm.getPmsnId(), list));
            menuList.add(menu);
        });
        return menuList;
    }
}
