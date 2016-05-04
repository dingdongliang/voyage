<%--
  Created by IntelliJ IDEA.
  User: Dyenigma
  Date: 2016/4/2
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>用户角色分配</title>
    <script type="text/javascript" src="resources/public/js/jquery.js"></script>
    <link href="resources/core/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="resources/public/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="resources/public/css/icon.css">
    <script type="text/javascript" src="resources/public/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/check/css/common.css">
    <script type="text/javascript" src="resources/check/js/jqueryUtil.js"></script>
    <script type="text/javascript">
        var $role;
        var $user;
        $(function () {
            $("#panel").panel({
                width: 'auto',
                height: $(this).height()
            });
            $user = $("#user");
            $user.datagrid({
                url: "manage/users/allUserByPage",
                width: 'auto',
                height: $(this).height() - 2,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                striped: true,
                idField: 'userId',
                border: false,
                columns: [[{
                    field: 'myid',
                    title: '用户编码',
                    width: parseInt($(this).width() * 0.08),
                    align: 'center',
                    editor: "text"
                },
                    {
                        field: 'account',
                        title: '用户账号',
                        width: parseInt($(this).width() * 0.08),
                        align: 'center',
                        editor: "text"
                    },
                    {
                        field: 'name',
                        title: '用户名',
                        width: parseInt($(this).width() * 0.08),
                        align: 'center',
                        editor: {type: 'validatebox', options: {required: true}}
                    },
                    {
                        field: 'organizeName',
                        title: '组织部门',
                        width: parseInt($(this).width() * 0.1),
                        align: 'center',
                        editor: "text"
                    },
                    {
                        field: 'description',
                        title: '描述',
                        width: parseInt($(this).width() * 0.18),
                        align: 'center',
                        editor: "text"
                    }
                ]],
                toolbar: "#tbUser",
                onDblClickRow: function (rowIndex, rowData) {
                    $.ajax({
                        url: "manage/users/usersRoleList",
                        data: {
                            'userId': rowData.userId
                        },
                        method: "POST",
                        dataType: "JSON",
                        success: function (msg) {
                            //初始化权限，取消所有的选中
                            $role.datagrid("unselectAll");
                            //判断是否有权限
                            if (msg.length != 0) {
                                //循环选中包含的权限
                                $.each(msg, function (i, e) {
                                    $role.datagrid("selectRecord", e.roleId);
                                });
                            } else {
                                $.messager.show({
                                    title: "提示",
                                    msg: "该用户暂无角色!",
                                    timeout: 1000 * 2
                                });
                            }
                        },
                        error: function () {
                            //获取失败，取消所有的选中
                            $role.datagrid("unselectAll");
                            $.messager.show({
                                title: "提示",
                                msg: "获取用户角色失败!",
                                timeout: 1000 * 2
                            });
                        }
                    });
                },
                onLoadSuccess: function () {
                    var pager = $user.datagrid('getPager');
                    pager.pagination({
                        beforePageText: '第',
                        afterPageText: '页 共{pages}页',
                        displayMsg: '当前第{from}-{to}条记录 共{total}条记录'
                    });
                }
            });
            $role = $("#role");
            $role.datagrid({
                url: "manage/role/findAllRoleList",
                width: 'auto',
                height: $(this).height() - 120,
                pagination: false,
                border: false,
                rownumbers: true,
                singleSelect: false,
                striped: true,
                idField: 'roleId',
                columns: [[{field: 'ck', checkbox: true},
                    {
                        field: 'name',
                        title: '角色名称',
                        width: parseInt($(this).width() * 0.1),
                        align: 'center',
                        editor: {type: 'validatebox', options: {required: true}}
                    }, {
                        field: 'description',
                        title: '角色描述',
                        width: parseInt($(this).width() * 0.2),
                        align: 'center',
                        editor: "text"
                    }
                ]], toolbar: "#tbRole"
            });

            //刷新角色菜单
            $("#refresh").click(function () {
                $role.datagrid('reload');
            });

            $("#saveUserRole").click(function () {
                var selectRow = $user.datagrid("getSelected");
                var selectRows = $role.datagrid("getSelections");
                var isCheckedIds = [];
                $.each(selectRows, function (i, e) {
                    isCheckedIds.push(e.roleId);
                });
                if (selectRow) {
                    $.ajax({
                        url: "manage/users/saveUserRoles",
                        data: {
                            //这里的数组必须转化成字符串形式
                            isCheckedIds: isCheckedIds.length == 0 ? "" : isCheckedIds.join(","),
                            userId: selectRow.userId
                        },
                        success: function (rsp) {
                            $.messager.show({
                                title: rsp.title,
                                msg: rsp.message,
                                timeout: 1000 * 2
                            });
                        },
                        error: function () {
                            $.messager.show({
                                title: "提示",
                                msg: "保存用户角色失败！",
                                timeout: 1000 * 2
                            });
                        }
                    });
                } else {
                    $.messager.show({
                        title: "提示",
                        msg: "请选择用户！",
                        timeout: 1000 * 2
                    });
                }
            });
        });
    </script>
</head>
<body>
<div id="panel" data-options="border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',split:true,border:true" style="width:650px;">
            <div id="tbUser" class="easyui-layout">
                <ul class="toolbar">
                    <shiro:hasPermission name="alltoRole">
                        <li id="saveUserRole"><span><img
                                src="resources/core/images/save.png"/></span>保存设置
                        </li>
                    </shiro:hasPermission>
                </ul>
            </div>
            <table id="user" title="用户"></table>
        </div>
        <div data-options="region:'center',border:true">
            <div id="tbRole" class="easyui-layout">
                <ul class="toolbar">
                    <li id="refresh"><span><img
                            src="resources/core/images/refresh.png"/></span>刷新
                    </li>
                </ul>
            </div>
            <table id="role" title="角色"></table>
        </div>
    </div>
</div>
</body>
</html>
