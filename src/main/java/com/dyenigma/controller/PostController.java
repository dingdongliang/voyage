/**
 * Title: DivisionController.java
 * Package com.dyenigma.controller
 * author dingdongliang
 * date 2015年10月10日 上午11:09:11
 * version V1.0
 * Copyright (c) 2015,dyenigma@163.com All Rights Reserved.
 */

package com.dyenigma.controller;

import com.alibaba.fastjson.JSONArray;
import com.dyenigma.entity.Post;
import com.dyenigma.entity.PostRole;
import com.dyenigma.model.Json;
import com.dyenigma.model.TreeModel;
import com.dyenigma.service.PostRoleService;
import com.dyenigma.service.PostService;
import com.dyenigma.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author dingdongliang
 * ClassName: DivisionController
 * Description: 岗位管理控制类（跳转和业务控制）
 * date 2015年10月10日 上午11:09:11
 */
@Controller
@RequestMapping(value = "/manage/post")
public class PostController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;
    @Autowired
    private PostRoleService postRoleService;

    @RequestMapping("/postMain")
    public String main() {
        LOGGER.debug("main() is executed!");
        return "manage/post/postMain";
    }

    @ResponseBody
    @RequestMapping(value = "/getCoDivList", produces = "application/json;charset=utf-8")
    public List<TreeModel> getCoDivList() {
        return postService.getCoDivList();
    }

    /**
     * Description: 查看每个岗位的用户列表
     * Name:postView
     * Author:dyenigma
     * Time:2016/4/28 14:53
     * param:[]
     * return:java.lang.String
     */
    @RequestMapping("/postView")
    public String postView() {
        LOGGER.debug("postView() is executed!");
        return "manage/post/postView";
    }

    /**
     * param    request
     * param return 参数
     * return List<Organization> 返回类型
     * throws
     * Title: findAllOrganList
     * Description: 按部门查询所有岗位
     */
    @ResponseBody
    @RequestMapping(value = "/findPostByDiv/{divId}", produces = "application/json;charset=utf-8")
    public List<Post> findPostByDiv(@PathVariable("divId") String divId) {
        return postService.finaPostByDiv(divId);
    }

    /**
     * param    request
     * param return 参数
     * return ModelAndView 返回类型
     * throws
     * Title: organEditDlg
     * Description: 跳转到编辑组织页面
     */
    @RequestMapping(value = "/postEdit", method = RequestMethod.GET)
    public ModelAndView postEdit() {

        LOGGER.debug("postEdit() is executed!");

        ModelAndView model = new ModelAndView();
        model.setViewName("manage/post/postEdit");

        return model;
    }


    /**
     * param    request
     * param return 参数
     * return ModelAndView 返回类型
     * throws
     * Title: organEditDlg
     * Description: 跳转到编辑组织页面
     */
    @RequestMapping(value = "/toSetRole", method = RequestMethod.GET)
    public ModelAndView toSetRole() {

        LOGGER.debug("toSetRole() is executed!");

        ModelAndView model = new ModelAndView();
        model.setViewName("manage/post/postRole");

        return model;
    }

    /**
     * Description: 保存岗位关联的角色
     * Name:savePostRole
     * Author:dyenigma
     * Time:2016/4/27 16:30
     * param:[request]
     * return:java.lang.String
     */
    @ResponseBody
    @RequestMapping(value = "/savePostRole", produces = "application/json;charset=utf-8")
    public String savePostRole(HttpServletRequest request) {
        String postId = request.getParameter("postId");
        String checkedIds = request.getParameter("allCheck");
        Json json = new Json();

        if (postRoleService.savePostRole(postId, checkedIds)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL);
        }

        return JSONArray.toJSONString(json);
    }


    /**
     * param    request
     * param return 参数
     * return String 返回类型
     * throws
     * Title: delOrgan
     * Description: 删除组织处理
     */
    @ResponseBody
    @RequestMapping(value = "/delPost", produces = "application/json;charset=utf-8")
    public String delPost(HttpServletRequest request) {
        String postId = request.getParameter("id");

        Json json = new Json();
        boolean flag = postService.invalidByPrimaryKey(postId) > 0;

        if (flag) {
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
     * return String 返回类型
     * throws
     * Title: saveOrUpdateOrgan
     * Description: 新增岗位或者更新岗位处理
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdatePost", produces = "application/json;charset=utf-8")
    public String saveOrUpdatePost(Post post) {
        Json json = getMessage(postService.persistencePost(post));
        return JSONArray.toJSONString(json);
    }

    @ResponseBody
    @RequestMapping(value = "/getPostRoleByPostId", produces = "application/json;charset=utf-8")
    public List<PostRole> getPostRoleByPostId(HttpServletRequest request) {
        String postId = request.getParameter("postId");
        return postRoleService.getPostRoleByPostId(postId);
    }
}
