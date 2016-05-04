package com.dyenigma.controller;

import com.alibaba.fastjson.JSONArray;
import com.dyenigma.entity.PrjRole;
import com.dyenigma.entity.Project;
import com.dyenigma.entity.User;
import com.dyenigma.model.Json;
import com.dyenigma.service.PrjRoleService;
import com.dyenigma.service.PrjUserService;
import com.dyenigma.service.ProjectService;
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
 * Description: 项目组控制器
 * author  dyenigma
 * date 2016/4/22 14:08
 */
@Controller
@RequestMapping(value = "/manage/project")
public class ProjectController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;
    @Autowired
    private PrjRoleService prjRoleService;
    @Autowired
    private PrjUserService prjUserService;

    //项目主页面跳转
    @RequestMapping(value = "/prjMain")
    public String prjMain() {
        LOGGER.debug("prjMain() is executed!");
        return "manage/project/prjMain";
    }

    //项目编辑页面跳转
    @RequestMapping(value = "/prjEdit")
    public String prjEdit() {
        LOGGER.debug("prjEdit() is executed!");
        return "manage/project/prjEdit";
    }

    /**
     * Description: 查询公司内项目组信息
     * Name:getPrjByCoId
     * Author:dyenigma
     * Time:2016/4/29 15:53
     * param:[request]
     * return:java.util.List<com.dyenigma.entity.Project>
     */
    @ResponseBody
    @RequestMapping(value = "/getPrjByCoId/{coId}", produces = "application/json;charset=utf-8")
    public List<Project> getPrjByCoId(@PathVariable("coId") String coId) {
        return projectService.getPrjByCoId(coId);
    }


    /**
     * Description: 项目组的新增和修改操作
     * Name:saveOrUpdatePrj
     * Author:dyenigma
     * Time:2016/4/29 16:02
     * param:[prj]
     * return:java.lang.String
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdatePrj", produces = "application/json;charset=utf-8")
    public String saveOrUpdatePrj(Project prj) {
        Json json = getMessage(projectService.persistencePrj(prj));
        return JSONArray.toJSONString(json);
    }

    /**
     * Description: 把项目组标记为过期、结束
     * Name:delPrj
     * Author:dyenigma
     * Time:2016/4/29 16:06
     * param:[request]
     * return:java.lang.String
     */
    @ResponseBody
    @RequestMapping(value = "/delPrj", produces = "application/json;charset=utf-8")
    public String delPrj(HttpServletRequest request) {
        String prjId = request.getParameter("prjId");

        Json json = new Json();
        boolean flag = projectService.delPrj(prjId);

        if (flag) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL + Constants.IS_EXT_SUBMENU);
        }

        return JSONArray.toJSONString(json);
    }

    /**
     * Description: 授予项目组角色
     * Name:prjRole
     * Author:dyenigma
     * Time:2016/4/29 16:09
     * param:[request]
     * return:java.util.List<com.dyenigma.entity.Project>
     */
    @ResponseBody
    @RequestMapping(value = "/prjRole", produces = "application/json;charset=utf-8")
    public List<Project> prjRole(HttpServletRequest request) {
        String coId = request.getParameter("coId");
        return projectService.getPrjByCoId(coId);
    }


    /**
     * Description: 项目组角色分配
     * Name:savePrjRoles
     * Author:dyenigma
     * Time:2016/4/29 16:21
     * param:[request]
     * return:java.lang.String
     */
    @ResponseBody
    @RequestMapping(value = "/savePrjRoles", produces = "application/json;charset=utf-8")
    public String savePrjRoles(HttpServletRequest request) {
        String prjId = request.getParameter("prjId");
        String checkedIds = request.getParameter("isCheckedIds");
        Json json = new Json();

        if (projectService.savePrjRole(prjId, checkedIds)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL);
        }

        return JSONArray.toJSONString(json);
    }

    /**
     * Description: 项目组角色分配
     * Name:savePrjRoles
     * Author:dyenigma
     * Time:2016/4/29 16:21
     * param:[request]
     * return:java.lang.String
     */
    @ResponseBody
    @RequestMapping(value = "/savePrjUsers", produces = "application/json;charset=utf-8")
    public String savePrjUsers(HttpServletRequest request) {
        String prjId = request.getParameter("prjId");
        String checkedIds = request.getParameter("isCheckedIds");
        Json json = new Json();

        if (projectService.savePrjUsers(prjId, checkedIds)) {
            json.setStatus(true);
            json.setMessage(Constants.POST_DATA_SUCCESS);
        } else {
            json.setMessage(Constants.POST_DATA_FAIL);
        }

        return JSONArray.toJSONString(json);
    }




    @ResponseBody
    @RequestMapping(value = "/searchPrjUser", produces = "application/json;charset=utf-8")
    public List<User> searchPrjUser(HttpServletRequest request) {
        String prjId = request.getParameter("prjId");
        return projectService.searchPrjUser(prjId);
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
        model.setViewName("manage/project/prjRole");

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
    @RequestMapping(value = "/toUserList", method = RequestMethod.GET)
    public ModelAndView toUserList() {

        LOGGER.debug("toUserList() is executed!");

        ModelAndView model = new ModelAndView();
        model.setViewName("manage/project/prjUser");

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/getPrjRoleByPrjId", produces = "application/json;charset=utf-8")
    public List<PrjRole> getPrjRoleByPrjId(HttpServletRequest request) {
        String prjId = request.getParameter("prjId");
        return prjRoleService.getPrjRoleByPrjId(prjId);
    }


    @ResponseBody
    @RequestMapping(value = "/getPrjUserByPrjId", produces = "application/json;charset=utf-8")
    public List<User> getPrjUserByPrjId(HttpServletRequest request) {
        String prjId = request.getParameter("prjId");
        return prjUserService.getPrjUserByPrjId(prjId);
    }


}
