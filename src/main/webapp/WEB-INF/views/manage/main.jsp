<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <title>4Tiro后台管理界面</title>
    <link href="resources/core/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="resources/public/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="resources/public/css/icon.css">
    <script type="text/javascript" src="resources/public/js/jquery.js"></script>
    <script type="text/javascript" src="resources/public/js/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',border:false"
     style="height: 88px; overflow: hidden;background:url(resources/core/images/topbg.png) repeat-x;"
     href="manage/mTop"></div>
<div data-options="region:'west',split:true,title:'导航菜单'" style="width: 220px;" href="manage/mLeft"></div>
<div data-options="region:'south',border:false" style="height: 16px !important" href="manage/mFoot"></div>
<div data-options="region:'center',plain:true" style="overflow: hidden; border: 0px" href="manage/mCenter"></div>
</body>
</html>
