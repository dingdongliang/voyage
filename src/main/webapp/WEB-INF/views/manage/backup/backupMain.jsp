<%--
  Created by IntelliJ IDEA.
  User: Dyenigma
  Date: 2016/4/2
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %><%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>备份日志管理</title>
    <script type="text/javascript" src="resources/public/js/jquery.js"></script>
    <link href="resources/core/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="resources/public/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="resources/public/css/icon.css">
    <script type="text/javascript" src="resources/public/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/check/css/common.css">
    <script type="text/javascript" src="resources/check/js/jqueryUtil.js"></script>
    <script type="text/javascript">
        var $dg;
        var $grid;
        $(function () {
            $dg = $("#dg");
            $grid = $dg.datagrid({
                url: "",
                width: 'auto',
                height: $(this).height() - 85,
                pagination: true,
                rownumbers: true,
                border: true,
                striped: true,
                singleSelect: true,
                columns: [[{field: 'name', title: '操作用户', width: parseInt($(this).width() * 0.1)},
                    {field: 'ip', title: 'IP地址', width: parseInt($(this).width() * 0.1)},
                    {field: 'mac', title: '物理地址', width: parseInt($(this).width() * 0.1)},
                    {field: 'logDate', title: '备份日期', width: parseInt($(this).width() * 0.1)},
                    {
                        field: 'type',
                        title: '日志类型',
                        width: parseInt($(this).width() * 0.1),
                        align: 'left',
                        formatter: function (value, row) {
                            if ("1" == row.type)
                                return "<font color=green>安全日志<font>";
                            else
                                return "<font color=red>操作日志<font>";
                        }
                    },
                    {
                        field: 'objectId', title: '备份文件名称', width: parseInt($(this).width() * 0.3), align: 'left',
                        formatter: function (value, row) {
                            return "<a href='javascript:void(0);' id='backUpA' onclick='downBackUpFile(\"" + row.objectId + "\")'>" + row.objectId + "</a>";
                        }
                    }
                ]], toolbar: '#tb'
            });
            //搜索框
            $("#searchbox").searchbox({
                menu: "#mm",
                prompt: '模糊查询',
                searcher: function (value, name) {
                    var str = "{\"searchName\":\"" + name + "\",\"searchValue\":\"" + value + "\"}";
                    var obj = eval('(' + str + ')');
                    $dg.datagrid('reload', obj);
                }

            });
        });

        function downBackUpFile(fileName) {
            $.ajax({
                url: "dbBackUp/dbBackUpAction!checkBackUp.action",
                type: "POST",
                data: "fileName=" + fileName,
                beforeSend: function () {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '正在压缩数据，请稍后....'
                    });
                },
                complete: function () {
                    parent.$.messager.progress('close');
                },
                success: function (res) {
                    if (res.status) {
                        window.location.href = "dbBackUp/dbBackUpAction!downBackUpFile.action?fileName=" + fileName;
                    } else {
                        parent.$.messager.show({
                            title: res.title,
                            msg: res.message,
                            timeout: 1000 * 2
                        });
                    }
                }
            });
        }
        //删除
        function delRows() {
            var row = $dg.datagrid('getSelected');
            if (row) {
                var rowIndex = $dg.datagrid('getRowIndex', row);
                $dg.datagrid('deleteRow', rowIndex);
                $.ajax({
                    url: "logs/logsAction!delLogs.action",
                    data: "logId=" + row.logId,
                    success: function (rsp) {
                        parent.$.messager.show({
                            title: rsp.title,
                            msg: rsp.message,
                            timeout: 1000 * 2
                        });
                    }
                });
            } else {
                parent.$.messager.show({
                    title: "提示",
                    msg: "请选择一行记录!",
                    timeout: 1000 * 2
                });
            }
        }
        //弹窗修改
        function updRowsOpenDlg() {
            parent.$.modalDialog({
                title: '手动备份',
                width: 600,
                height: 400,
                href: "jsp/dbBackUp/autoEdit.jsp",
                buttons: [{
                    text: '备份',
                    iconCls: 'icon-start',
                    handler: function () {
                        parent.$.modalDialog.openner = $grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find("#form");
                        f.submit();
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        parent.$.modalDialog.handler.dialog('destroy');
                        parent.$.modalDialog.handler = undefined;
                    }
                }
                ]
            });
        }
        //弹窗增加公司
        function addRowsOpenDlg() {
            parent.$.modalDialog({
                title: '定时备份',
                width: 600,
                height: 400,
                href: "jsp/dbBackUp/timeEdit.jsp",
                onLoad: function () {
                    $.ajax({
                        url: "dbBackUp/dbBackUpAction!getScheduleConfig.action",
                        type: "POST",
                        success: function (res) {
                            var f = parent.$.modalDialog.handler.find("#form");
                            f.form("load", res);
                        }
                    });

                },
                buttons: [{
                    text: '启动',
                    iconCls: 'icon-start',
                    handler: function () {
                        parent.$.modalDialog.openner = $grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find("#form");
                        f.submit();
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        parent.$.modalDialog.handler.dialog('destroy');
                        parent.$.modalDialog.handler = undefined;
                    }
                }
                ]
            });
        }

        //高级搜索 删除 row
        function tbCompanySearchRemove(curr) {
            $(curr).closest('tr').remove();
        }
        //高级查询
        function tbsCompanySearch() {
            jqueryUtil.gradeSearch($dg, "#tbCompanySearchFm", "jsp/company/companySearchDlg.jsp");
        }
    </script>
    <style type="text/css">
        #backUpA {
            font-size: 12px
        }

        #backUpA:link {
            color: blue;
            text-decoration: none;
        }

        #backUpA:active{
            color: red;
        }

        #backUpA:visited {
            color: purple;
            text-decoration: none;
        }

        #backUpA:hover {
            color: red;
            text-decoration: underline;
        }

    </style>
</head>
<body>
<div data-options="region:'center',border : false">
    <div class="well well-small" style="margin-left: 5px;margin-top: 5px">
        <span class="badge">提示</span>
        <p>
            在此你可以对数据进行<span class="label-info"><strong>备份!有两种备份方式定时备份和手动备份，手动备份不影响定时备份！</strong></span>
        </p>
    </div>
    <div id="tb" style="padding:2px 0">
        <table cellpadding="0" cellspacing="0">
            <tr>
                <td style="padding-left:2px">
                    <shiro:hasPermission name="dbBackUpHand">
                        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-time" plain="true"
                           onclick="addRowsOpenDlg();">定时备份</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="dbBackUpTime">
                        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-auto" plain="true"
                           onclick="updRowsOpenDlg();">手动备份</a>
                    </shiro:hasPermission>
                </td>
                <td style="padding-left:2px">
                    <input id="searchbox" type="text"/>
                </td>
                <!--  <td style="padding-left:2px">
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tbsCompanySearch();">高级查询</a>
                </td>-->
            </tr>
        </table>
    </div>
    <div id="mm">
        <div name="name">日志名称</div>
        <div name="logDate">创建日期</div>
        <div name="type">类型</div>
        <div name="eventName">操作名称</div>
        <div name="eventRecord">操作描述</div>
    </div>
    <table id="dg" title="备份管理"></table>
</div>
</body>
</html>
