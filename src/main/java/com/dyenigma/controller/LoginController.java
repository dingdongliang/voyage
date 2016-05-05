
/**
 * Title: LoginController.java
 * Package com.dyenigma.controller
 * author dingdongliang
 * date 2015年9月8日 下午3:37:14
 * version V1.0
 * Copyright (c) 2015,dyenigma@163.com All Rights Reserved.
 */


package com.dyenigma.controller;


import com.dyenigma.entity.User;
import com.dyenigma.model.MenuModel;
import com.dyenigma.service.PermissionService;
import com.dyenigma.service.UserService;
import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * author dingdongliang
 * ClassName: LoginController
 * Description: 登录处理
 * date 2015年9月8日 下午3:37:14
 */
@Controller
public class LoginController {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;

    /**
     * param request
     * param return 参数
     * return String 返回类型
     * throws
     * Title: login
     * Description: 用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        String resultPageURL = InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
        String account = request.getParameter("account");
        // MD5加密
        // 数据库中密码数据为98a6a31a7801d0418606e2d8cbc9d4a0，输入密码为system
        String password = request.getParameter("password");
        // 获取HttpSession中的验证码
        String verifyCode = (String) request.getSession().getAttribute(
                Constants.KAPTCHA_SESSION_KEY);
        // 获取用户请求表单中输入的验证码
        String submitCode = WebUtils.getCleanParam(request, "verifyCode");
        LOGGER.debug("用户账号[" + account + "]登录时输入的验证码为[" + submitCode + "],HttpSession中的验证码为["
                + verifyCode + "]");
        if (StringUtils.isEmpty(submitCode)
                || !StringUtils.equals(verifyCode, submitCode.toLowerCase())) {
            request.setAttribute("message_login", "验证码不正确");
            return resultPageURL;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        LOGGER.debug("为了验证登录用户而封装的token为"
                + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        // 获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            LOGGER.debug("对用户[" + account + "]进行登录验证..验证开始");
            if (!subject.isAuthenticated()) {
                token.setRememberMe(true);
                subject.login(token);
            }
            LOGGER.debug("对用户[" + account + "]进行登录验证..验证通过");

            //获取登录的时间，方便前台页面显示
            String userId = com.dyenigma.utils.Constants.getCurrendUser().getUserId();
            User user = userService.selectByPrimaryKey(userId);
            user.setPrevLogin(new Date());
            userService.updateByPrimaryKey(user);

            request.getSession().setAttribute("currUser", account);
            resultPageURL += "manage/main";
            LOGGER.debug(resultPageURL);
        } catch (UnknownAccountException uae) {
            LOGGER.debug("对用户[" + account + "]进行登录验证..验证未通过,未知账户");
            request.setAttribute("message_login", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            LOGGER.debug("对用户[" + account + "]进行登录验证..验证未通过,错误的凭证");
            request.setAttribute("message_login", "密码不正确");
        } catch (LockedAccountException lae) {
            LOGGER.debug("对用户[" + account + "]进行登录验证..验证未通过,账户已锁定");
            request.setAttribute("message_login", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            LOGGER.debug("对用户[" + account + "]进行登录验证..验证未通过,错误次数过多");
            request.setAttribute("message_login", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            LOGGER.debug("对用户[" + account + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            request.setAttribute("message_login", "用户名或密码不正确");
        }
        // 验证是否登录成功
        if (subject.isAuthenticated()) {
            // 这里可以进行一些认证通过后的一些系统参数初始化操作
            LOGGER.debug("用户[" + account + "]登录认证通过.");
        } else {
            token.clear();
        }
        return resultPageURL;
    }

    /**
     * param    request
     * param return 参数
     * return String 返回类型
     * throws
     * Title: logout
     * Description: 用户登出
     */
    @RequestMapping("/logout")
    public String logout() {
        String result = "manage/login";
        SecurityUtils.getSubject().logout();
        return result;
    }

    /**
     * param    response
     * param return 参数
     * return String 返回类型
     * throws
     * Title: getUsersMenu
     * Description:查询用户所有权限菜单
     */
    @ResponseBody
    @RequestMapping(value = "/getUsersMenu", produces = "application/json;charset=utf-8")
    public List<MenuModel> getUsersMenu() {
        return permissionService.createMenu();
    }

}
