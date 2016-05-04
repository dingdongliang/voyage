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
    <meta charset="utf-8">
    <title>4Tiro-学习生活</title>
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
    <div class="left" id="about_me">
        <div class="weizi">
            <div class="wz_text">
                当前位置：<a href="#">首页</a>>
                <h1>关于我</h1>
            </div>
        </div>
        <div class="about_content">博主是一个草根站长，喜欢研究web前端技术和SEO技术。</div>
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
