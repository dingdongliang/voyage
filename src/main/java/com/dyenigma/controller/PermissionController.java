/**
 * Title: MgrFunController.java
 * Package com.dyenigma.web
 * author dingdongliang
 * date 2015年9月17日 下午3:20:40
 * version V1.0
 * Copyright (c) 2015,dyenigma@163.com All Rights Reserved.
 */

package com.dyenigma.controller;


import com.alibaba.fastjson.JSONArray;
import com.dyenigma.entity.Permission;
import com.dyenigma.model.Json;
import com.dyenigma.model.MultiMenu;
import com.dyenigma.model.TreeModel;
import com.dyenigma.service.PermissionService;
import com.dyenigma.utils.Constants;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author dingdongliang
 * ClassName: MgrFunController
 * Description: 菜单控制（跳转and业务调用）
 * date 2015年9月17日 下午3:20:40
 */
@Controller
@RequestMapping(value = "/manage/menu")
public class PermissionController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    /**
     * param return 参数
     * return String 返回类型
     * throws
     * Title: main
     * Description: 打开操作菜单页面
     */
    @RequestMapping("/menuMain")
    public String main() {

        LOGGER.debug("main() is executed!");

        return "manage/permission/pmsnMain";
    }

    /**
     * param    request
     * param return 参数
     * return List<Permission> 返回类型
     * throws
     * Title: findAllFunctionList
     * Description: 显示所有可操作的菜单项，用于菜单编辑页面
     */
    @ResponseBody
    @RequestMapping(value = "/findAllMenuList", produces = "application/json;charset=utf-8")
    public List<Permission> findAllMenuList(HttpServletRequest request) {
        String pmsnId = request.getParameter("id");
        return permissionService.findByPid(pmsnId);
    }


    /**
     * 用于角色权限菜单分配
     */
    @ResponseBody
    @RequestMapping(value = "/findAllRoleMenu", produces = "application/json;charset=utf-8")
    public List<MultiMenu> findAllRoleMenu() {
        return permissionService.multiMenu();
    }


    /**
     * param return 参数
     * return List<TreeModel> 返回类型
     * throws
     * Title: findSuperFunc
     * Description:显示所有可添加子项的菜单项
     */
    @ResponseBody
    @RequestMapping(value = "/findSuperMenu", produces = "application/json;charset=utf-8")
    public List<TreeModel> findSuperFunc() {
        return permissionService.findSuperFunc();
    }


    /**
     * param    request
     * param return 参数
     * return String 返回类型
     * throws
     * Title: delFunction
     * Description: 删除菜单处理
     */
    @ResponseBody
    @RequestMapping(value = "/delMenu", produces = "application/json;charset=utf-8")
    public String delFunction(HttpServletRequest request) {
        String id = request.getParameter("id");

        Json json = new Json();
        if (permissionService.deleteById(id)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL + Constants.IS_EXT_SUBMENU);
        }
        return JSONArray.toJSONString(json);
    }

    /**
     * param    request
     * param return 参数
     * return ModelAndView 返回类型
     * throws
     * Title: functionEditDlg
     * Description: 跳转到编辑菜单页面
     */
    @RequestMapping(value = "/menuEditDlg", method = RequestMethod.GET)
    public ModelAndView functionEditDlg() {

        LOGGER.debug("functionEditDlg() is executed!");

        ModelAndView model = new ModelAndView();
        model.setViewName("manage/permission/pmsnEdit");

        return model;
    }

    /**
     * param    request
     * param return 参数
     * return String 返回类型
     * throws
     * Title: saveOrUpdateFunc
     * Description: 新增菜单或者更新菜单处理
     */
    @RequiresPermissions({"menuAdd", "menuEdit"})
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateMenu", produces = "application/json;charset=utf-8")
    public String saveOrUpdateFunc(Permission permission) {
        Json json = getMessage(permissionService.persistenceFunction(permission));
        return JSONArray.toJSONString(json);
    }
}
