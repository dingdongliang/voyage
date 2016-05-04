package com.dyenigma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 前端页面控制器
 */
@Controller
public class FrontJumpController {
    private final Logger LOGGER = LoggerFactory.getLogger(FrontJumpController.class);


    @RequestMapping(value = "/base", method = RequestMethod.GET)
    public String base() {

        LOGGER.debug("base() is executed!");

        return "main/base";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {

        LOGGER.debug("main() is executed!");

        return "main/main";
    }

    @RequestMapping(value = "/adv", method = RequestMethod.GET)
    public String adv() {

        LOGGER.debug("adv() is executed!");

        return "main/adv";
    }

    @RequestMapping(value = "/web", method = RequestMethod.GET)
    public String web() {

        LOGGER.debug("web() is executed!");

        return "main/web";
    }

    @RequestMapping(value = "/sources", method = RequestMethod.GET)
    public String sources() {

        LOGGER.debug("sources() is executed!");

        return "main/sources";
    }

    @RequestMapping(value = "/database", method = RequestMethod.GET)
    public String database() {

        LOGGER.debug("database() is executed!");

        return "main/database";
    }

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String question() {

        LOGGER.debug("question() is executed!");

        return "main/question";
    }

    @RequestMapping(value = "/life", method = RequestMethod.GET)
    public String life() {

        LOGGER.debug("life() is executed!");

        return "main/life";
    }

    @RequestMapping(value = "/tools", method = RequestMethod.GET)
    public String tools() {

        LOGGER.debug("tools() is executed!");

        return "main/tools";
    }
}
