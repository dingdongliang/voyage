/**
 * Title: MgrUserController.java
 * Package com.dyenigma.controller
 * author dingdongliang
 * date 2015年10月26日 下午2:49:45
 * version V1.0
 * Copyright (c) 2015,dyenigma@163.com All Rights Reserved.
 */

package com.dyenigma.controller;

import com.alibaba.fastjson.JSONArray;
import com.dyenigma.entity.*;
import com.dyenigma.model.GridModel;
import com.dyenigma.model.Json;
import com.dyenigma.model.TreeModel;
import com.dyenigma.service.*;
import com.dyenigma.utils.Constants;
import com.dyenigma.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: MgrUserController
 * Description: 用户管理控制类（跳转和业务控制）
 * author dingdongliang
 * date 2015年10月26日 下午2:49:45
 */
@Controller
@RequestMapping(value = "/manage/users")
public class UserController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService coService;
    @Autowired
    private DivisionService divService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserPostService userPostService;
    @Autowired
    private UserPmsnService userPmsnService;

    /**
     * Description:获取所有的岗位信息，按树结构显示
     * Name:getPostList
     * Author:Dyenigma
     * Time:2016/4/27 23:17
     * param:[]
     * return:java.util.List<com.dyenigma.model.TreeModel>
     */
    @ResponseBody
    @RequestMapping(value = "/getPostList", produces = "application/json;charset=utf-8")
    public List<TreeModel> getPostList() {
        //获取公司和部门树结构
        List<TreeModel> divList = postService.getCoDivList();
        //循环处理每个部门添加岗位结构,返回
        return userService.getPostList(divList);
    }

    //用户主页面跳转
    @RequestMapping("/usersMain")
    public String main() {
        LOGGER.debug("main() is executed!");
        return "manage/user/userMain";
    }

    /**
     * Title: findAllOrganList
     * Description: 查询所有用户
     * param   request
     * param return 参数
     * return List<Organization> 返回类型
     * throws
     */
    @ResponseBody
    @RequestMapping(value = "/findAllUserList", produces = "application/json;charset=utf-8")
    public List<User> findAllUserList() {
        return userService.findAll();
    }


    @ResponseBody
    @RequestMapping(value = "/findUserByPost/{postId}", produces = "application/json;charset=utf-8")
    public GridModel findUserByPost(@PathVariable("postId") String postId, HttpServletRequest request) {
        LOGGER.debug("findUserByPost() is executed!");
        List<String> idList = userPostService.findByPostId(postId);

        Set<String> idSet = new HashSet<>();

        idSet.addAll(idList.stream().collect(Collectors.toList()));


        return getGridModel(request, idSet);
    }

    @ResponseBody
    @RequestMapping(value = "/findUserByDiv/{divId}", produces = "application/json;charset=utf-8")
    public GridModel findUserByDiv(@PathVariable("divId") String divId, HttpServletRequest request) {
        LOGGER.debug("findUserByDiv() is executed!");
        //先获取部门下属的所有岗位
        List<Post> pList = postService.finaPostByDiv(divId);
        //然后逐一获取岗位下的所有用户ID，累加,获取总的用户ID集合
        Set<String> idSet = new HashSet<>();

        for (Post post : pList) {
            List<String> perList = userPostService.findByPostId(post.getPostId() + "");
            //一对多，有重复信息，要使用set
            idSet.addAll(perList.stream().collect(Collectors.toList()));
        }

        // 查询，分页展示
        return getGridModel(request, idSet);

    }

    @ResponseBody
    @RequestMapping(value = "/findUserByCo/{coId}", produces = "application/json;charset=utf-8")
    public GridModel findUserByCo(@PathVariable("coId") String coId, HttpServletRequest request) {
        LOGGER.debug("findUserByCo() is executed!");
        //获取所有的下属公司集合
        List<Company> coList = coService.AllCoById(coId);
        //循环获取所有公司的下属所有部门
        Set<Division> allDivList = new HashSet<>();
        for (Company co : coList) {
            List<Division> divList = divService.findDivByCoId(co.getCoId() + "");
            //一对多，有重复信息，要使用set
            allDivList.addAll(divList.stream().collect(Collectors.toList()));
        }

        //循环获取所有部门下属的所有岗位
        Set<Post> allPostList = new HashSet<>();
        for (Division division : allDivList) {
            List<Post> pList = postService.finaPostByDiv(division.getDivId() + "");
            //一对多，有重复信息，要使用set
            allPostList.addAll(pList.stream().collect(Collectors.toList()));
        }

        //循环获取每个岗位对应的用户，累加，获取总用户ID集合
        Set<String> totalList = new HashSet<>();
        for (Post post : allPostList) {
            List<String> perList = userPostService.findByPostId(post.getPostId() + "");
            //一对多，有重复信息，要使用set
            totalList.addAll(perList.stream().collect(Collectors.toList()));
        }
        //查询，分页展示
        return getGridModel(request, totalList);
    }

    //查询分页显示，适用于findUserByPage方法分页
    private GridModel getGridModel(HttpServletRequest request, Set<String> totalList) {

        int pageNo = Integer.parseInt(request.getParameter("page"));
        int length = Integer.parseInt(request.getParameter("rows"));
        PageUtil pageUtil = new PageUtil((pageNo - 1) * length, length);
        GridModel gridModel = new GridModel();
        gridModel.setRows(userService.findUserByPage(pageUtil, totalList));
        //TODO 这种获取total的方式还是不严谨，待更换
        gridModel.setTotal(totalList == null ? 0L : totalList.size());
        return gridModel;

    }

    //用户编辑页面跳转
    @RequestMapping("/usersEditDlg")
    public String usersEditDlg() {
        LOGGER.debug("usersEditDlg() is executed!");
        return "manage/user/userEdit";
    }

    //用户岗位设置页面跳转
    @RequestMapping("/userPost")
    public String userPost() {
        LOGGER.debug("userPost() is executed!");
        return "manage/user/userPost";
    }

    //用户角色设置页面跳转
    @RequestMapping("/userRole")
    public String userRole() {
        LOGGER.debug("userRole() is executed!");
        return "manage/user/userRole";
    }

    //用户权限设置页面跳转
    @RequestMapping("/userPmsn")
    public String userPmsn() {
        LOGGER.debug("userPmsn() is executed!");
        return "manage/user/userPmsn";
    }

    /**
     * 搜索所有的用户信息，分页
     * request
     */
    @ResponseBody
    @RequestMapping(value = "/allUserByPage", produces = "application/json;charset=utf-8")
    public GridModel allUserByPage(HttpServletRequest request) {
        LOGGER.debug("allUserByPage() is executed!");
        int pageNo = Integer.parseInt(request.getParameter("page"));
        int length = Integer.parseInt(request.getParameter("rows"));
        PageUtil pageUtil = new PageUtil((pageNo - 1) * length, length);
        GridModel gridModel = new GridModel();
        gridModel.setRows(userService.allUserByPage(pageUtil));
        gridModel.setTotal(userService.getCount(null));
        return gridModel;
    }


    /**
     * param    request
     * param return 参数
     * return String 返回类型
     * throws
     * Title: saveOrUpdateUser
     * Description: 新增用户或者更新用户处理
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateUser", produces = "application/json;charset=utf-8")
    public String saveOrUpdateUser(User user) {
        Json json = getMessage(userService.persistenceUser(user));
        return JSONArray.toJSONString(json);
    }


    @ResponseBody
    @RequestMapping(value = "/delUser", produces = "application/json;charset=utf-8")
    public String delUser(HttpServletRequest request) {
        String id = request.getParameter("userId");

        Json json = new Json();
        boolean flag = userService.delUser(id);

        if (flag) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL + Constants.IS_EXT_SUBMENU);
        }

        return JSONArray.toJSONString(json);
    }

    @ResponseBody
    @RequestMapping(value = "/usersRoleList", produces = "application/json;charset=utf-8")
    public List<Role> usersRoleList(HttpServletRequest request) {
        String id = request.getParameter("userId");

        return userRoleService.findAllByUserId(id);
    }

    /**
     * 保存某个用户的角色分配
     * param request
     * return
     */
    @ResponseBody
    @RequestMapping(value = "/saveUserRoles", produces = "application/json;charset=utf-8")
    public String saveUserRoles(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String checkedIds = request.getParameter("isCheckedIds");
        Json json = new Json();

        if (userRoleService.saveRole(userId, checkedIds)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL);
        }

        return JSONArray.toJSONString(json);
    }


    /**
     * 保存某个用户的岗位分配
     * param request
     * return
     */
    @ResponseBody
    @RequestMapping(value = "/saveUserPost", produces = "application/json;charset=utf-8")
    public String saveUserPost(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String checkedIds = request.getParameter("isCheckedIds");
        Json json = new Json();

        if (userPostService.saveUserPost(userId, checkedIds)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL);
        }

        return JSONArray.toJSONString(json);
    }


    /**
     * 保存某个用户的权限分配
     * param request
     * return
     */
    @ResponseBody
    @RequestMapping(value = "/saveUserPmsn", produces = "application/json;charset=utf-8")
    public String saveUserPmsn(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String checkedIds = request.getParameter("isCheckedIds");
        Json json = new Json();

        if (userPmsnService.saveUserPmsn(userId, checkedIds)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL);
        }

        return JSONArray.toJSONString(json);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserPostByUsedId", produces = "application/json;charset=utf-8")
    public List<UserPost> getUserPostByUsedId(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        return userPostService.findByUserId(userId);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserRoleByUsedId", produces = "application/json;charset=utf-8")
    public List<UserRole> getUserRoleByUsedId(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        return userRoleService.findByUserId(userId);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserPmsnByUsedId", produces = "application/json;charset=utf-8")
    public List<UserPmsn> getUserPmsnByUsedId(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        return userPmsnService.findByUserId(userId);
    }
}
