package com.dyenigma.controller;

import com.dyenigma.model.GridModel;
import com.dyenigma.service.QuestionService;
import com.dyenigma.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * author  dyenigma
 * date 2016/5/13 10:57
 */
@Controller
@RequestMapping(value = "/manage/qstn")
public class QuestionController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    /**
     * param return 参数
     * return String 返回类型
     * throws
     * Title: main
     * Description: 打开操作菜单页面
     */
    @RequestMapping("/qaList")
    public String qaList() {

        LOGGER.debug("qaList() is executed!");

        return "manage/research/qaList";
    }

    @ResponseBody
    @RequestMapping(value = "/findAllByPage", produces = "application/json;charset=utf-8")
    public GridModel findAllByPage(HttpServletRequest request) {
        LOGGER.debug("findAllByPage() is executed!");
        int pageNo = Integer.parseInt(request.getParameter("page"));
        int length = Integer.parseInt(request.getParameter("rows"));
        PageUtil pageUtil = new PageUtil((pageNo - 1) * length, length);
        GridModel gridModel = new GridModel();
        gridModel.setRows(questionService.findAllByPage(pageUtil));
        gridModel.setTotal(questionService.getCount(null));
        return gridModel;
    }
}
