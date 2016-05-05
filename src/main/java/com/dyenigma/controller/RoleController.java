package com.dyenigma.controller;

import com.alibaba.fastjson.JSONArray;
import com.dyenigma.entity.Permission;
import com.dyenigma.entity.Role;
import com.dyenigma.model.GridModel;
import com.dyenigma.model.Json;
import com.dyenigma.service.RolePmsnService;
import com.dyenigma.service.RoleService;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * topic 角色与权限分配控制器
 * author Dyenigma
 * create 2016/4/2 18:57
 */
@Controller
@RequestMapping(value = "/manage/role")
public class RoleController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePmsnService rolePmsnService;

    /**
     * 跳转到主页面
     * return
     */
    @RequestMapping("/roleMgr")
    public String roleMain() {
        LOGGER.debug("roleMain() is executed!");
        return "manage/role/roleMain";
    }

    /**
     * 搜索所有的角色信息，分页
     * request
     */
    @ResponseBody
    @RequestMapping(value = "/allRoleByPage", produces = "application/json;charset=utf-8")
    public GridModel allRoleByPage(HttpServletRequest request) {
        LOGGER.debug("allRoleByPage() is executed!");
        int pageNo = Integer.parseInt(request.getParameter("page"));
        int length = Integer.parseInt(request.getParameter("rows"));
        PageUtil pageUtil = new PageUtil((pageNo - 1) * length, length);
        GridModel gridModel = new GridModel();
        gridModel.setRows(roleService.findAllRoleList(pageUtil));
        gridModel.setTotal(roleService.getCount(null));
        return gridModel;
    }


    /**
     * Title: findAllRoleList
     * Description: 查询所有角色
     * param   request
     * param return 参数
     * return List<Role> 返回类型
     * throws
     */
    @ResponseBody
    @RequestMapping(value = "/findAllRoleList", produces = "application/json;charset=utf-8")
    public List<Role> findAllRoleList() {
        LOGGER.debug("findAllRoleList() is executed!");
        return roleService.findAll();
    }

    /**
     * 跳转到添加和编辑子页面
     * return
     */
    @RequestMapping("/roleEditDlg")
    public String roleEditDlg() {
        LOGGER.debug("roleEditDlg() is executed!");
        return "manage/role/roleEdit";
    }

    /**
     * 持久化角色对象，提交到业务处理层后根据roleId来判断是新增还是编辑
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateRole", produces = "application/json;charset=utf-8")
    public String saveOrUpdateRole(Role role) {
        Json json = getMessage(roleService.persistenceRole(role));
        return JSONArray.toJSONString(json);
    }

    /**
     * 删除角色信息
     * param request
     * return
     */
    @ResponseBody
    @RequestMapping(value = "/delRole", produces = "application/json;charset=utf-8")
    public String delRole(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");

        Json json = new Json();
        boolean flag = roleService.delRole(roleId);

        if (flag) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL + Constants.IS_EXT_SUBMENU);
        }

        return JSONArray.toJSONString(json);
    }

    /**
     * 获取某个角色的所有权限
     * param request
     * return
     */
    @ResponseBody
    @RequestMapping(value = "/getRolePermission", produces = "application/json;charset=utf-8")
    public List<Permission> getRolePermission(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        List<Permission> permissions = rolePmsnService.findAllByRoleId(roleId);

        return permissions;
    }

    /**
     * 保存某个角色的权限分配
     * param request
     * return
     */
    @ResponseBody
    @RequestMapping(value = "/savePermission", produces = "application/json;charset=utf-8")
    public String savePermission(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        String checkedIds = request.getParameter("allCheck");
        Json json = new Json();

        if (rolePmsnService.savePermission(roleId, checkedIds)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL);
        }

        return JSONArray.toJSONString(json);
    }

    @ResponseBody
    @RequestMapping(value="/setDefaultRole",produces = "application/json;charset=utf-8")
    public String setDefaultRole(HttpServletRequest request){
        String roleId = request.getParameter("roleId");
        Json json = new Json();

        if (rolePmsnService.setDefaultRole(roleId)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL);
        }

        return JSONArray.toJSONString(json);
    }
}
