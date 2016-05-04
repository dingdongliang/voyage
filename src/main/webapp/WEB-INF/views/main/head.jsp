<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>"/>
<link rel="stylesheet" href="resources/show/css/head.css"/>
<script type="text/javascript"
        src="resources/public/js/jquery.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('.nav li a').each(function () {
            if ($($(this))[0].href == String(window.location))
                $(this).parent().addClass('active');
        });
    })
</script>

<header class="header">
    <div class="navbar">
        <div class="container-fluid">
            <div class="logo">
                <a class="spring-logo" href="main"><span></span></a>
            </div>
            <ul class="nav pull-right">
                <li class="navLink"><a href="main">首页</a></li>
                <li class="navLink"><a href="tools">开发工具</a></li>
                <li class="navLink"><a href="base">编程基础</a></li>
                <li class="navLink"><a href="adv">编程进阶</a></li>
                <li class="navLink"><a href="web">WEB</a></li>
                <li class="navLink"><a href="database">数据库</a></li>
                <li class="navLink"><a href="sources">资源</a></li>
                <li class="navLink"><a href="question">问道</a></li>
                <li class="navLink"><a href="life">学习生活</a></li>
            </ul>
        </div>
    </div>
</header>
