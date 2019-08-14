<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>水表管理-集中器管理-指令记录-查看</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
    <style>
        .layui-form-item{margin-bottom: 10px;}
        [type='text']{
            border:none;
        }
        [title]{
            border:none !important;
            text-align:right !important;
            width:140px !important;
        }
    </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top:40px;margin-left:80px;" lay-filter="updForm">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="命令编号">命令编号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.instructionNum}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="公寓">公寓：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.apartmentName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作类型">操作类型：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.typeName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="命令状态">命令状态：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.currentStatusName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="区域">创建时间：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.createTime}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <%--<div class="layui-inline">--%>
            <%--<label class="layui-form-label label-120" title="更新时间">更新时间：</label>--%>
            <%--<div class="layui-input-inline">--%>
                <%--<input type="text" value="${br.updateTime}" autocomplete="off" class="layui-input" disabled>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作指令">操作指令：</label>
            <div class="layui-input-inline">
                <div class="layui-input-inline" style="width:300px;height:100px;overflow:auto;">
                    ${br.instructionDetail}
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="返回描述">返回描述：</label>
            <div class="layui-input-inline">
                <div class="layui-input-inline" style="width:300px;height:100px;overflow:auto;">
                    ${br.result}
                </div>
            </div>
        </div>
    </div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
</html>