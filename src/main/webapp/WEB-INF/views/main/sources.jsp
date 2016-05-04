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
    <title>4Tiro-资源</title>
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
<div id="say">
    <div class="weizi">
        <div class="wz_text">
            当前位置：<a href="#">首页</a>>
            <h1>碎言碎语</h1>
        </div>
    </div>
    <div class="say_box">
        <div class="sy">
            <p>那个可以任意挥霍的年纪，人们叫它'青春'。</p>
        </div>
        <span class="dateview">2014-5-31</span>
    </div>
    <div class="say_box">
        <div class="sy">
            <p>过去就像回形针，把青春一页页的固定，然后变成了一本不被出版的书。</p>
        </div>
        <span class="dateview">2014-5-31</span>
    </div>
    <div class="say_box">
        <div class="sy">
            <p>时间好象一把尺子，它能衡量奋斗者前进的进程。 时间如同一架天平，它能称量奋斗者成果的重量；
                时间就像一把皮鞭，它能鞭策我们追赶人生的目标。时间犹如一面战鼓，它能激励我们加快前进的脚步。</p>
        </div>
        <span class="dateview">2014-5-31</span>
    </div>
    <div class="say_box">
        <div class="sy">
            <p>青春，一半明媚，一半忧伤。 它是一本惊天地泣鬼神的着作，而我们却读的太匆忙。
                于不经意间，青春的书籍悄然合上，以至于我们要重新研读它时， 却发现青春的字迹早已落满尘埃，模糊不清。</p>
        </div>
        <span class="dateview">2014-5-31</span>
    </div>
    <div class="say_box">
        <div class="sy">
            <p>青春，一半明媚，一半忧伤。 它是一本惊天地泣鬼神的着作，而我们却读的太匆忙。
                于不经意间，青春的书籍悄然合上，以至于我们要重新研读它时， 却发现青春的字迹早已落满尘埃，模糊不清。</p>
        </div>
        <span class="dateview">2014-5-31</span>
    </div>
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
