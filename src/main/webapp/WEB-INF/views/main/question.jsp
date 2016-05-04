<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <title>4Tiro-问道</title>
    <meta name="keywords" content="Java,git,spring,mvc,mybatis,oracle,mysql,shiro"/>
    <meta name="description" content=""/>
    <link rel="stylesheet" href="resources/show/css/index.css"/>
    <link rel="stylesheet" href="resources/show/css/style.css"/>
    <script type="text/javascript"
            src="resources/public/js/jquery.js"></script>
    <script type="text/javascript"
            src="resources/show/js/jquery.SuperSlide.2.1.1.js"></script>
    <!--[if lt IE 9]>
    <script src="resources/show/js/html5.js"></script>
    <![endif]-->
</head>

<body>
<c:import url="head.jsp"></c:import>
<!--content start-->
<div id="content">
    <!--left-->
    <div class="left" id="guestbook">
        <div class="weizi">
            <div class="wz_text">
                当前位置：<a href="#">首页</a>>
                <h1>留言板</h1>
            </div>
        </div>
        <div class="news_content">
            <div class="news_top">
                <h1>浅谈：html5和html的区别</h1>
                <p>
                    <span class="left sj">时间:2014-8-9</span><span class="left fl">分类:学无止境</span>
                    <span class="left author">DyEnigma</span>
                </p>
                <div class="clear"></div>
            </div>
            <div class="news_text">
                <p>统一项目开发人员的编码风格。主要包括：设置Code Templates、Eclipse formatter.</p>
                <p>修改eclipse中的$(user)变量</p>
                <p>在eclipse.ini中添加 -vmargs -Duser.name=your name
                    记得一定要在-vmargs之后，否则无效。 保存重启Eclipse即可。</p>
            </div>
        </div>
    </div>
    <!--end left -->
    <c:import url="right.jsp"></c:import>
    <div class="clear"></div>
</div>
<!--content end-->
<c:import url="foot.jsp"></c:import>
<script type="text/javascript">
    jQuery(".lanmubox").slide({
        easing: "easeOutBounce",
        delayTime: 400
    });
</script>
</body>
</html>

