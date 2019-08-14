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
    <title>系统管理-集中器</title>
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

<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top:40px;margin-left:40px;" lay-filter="updForm">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器IP地址">IP地址：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.ip_address}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器密码">集中器密码：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.concentrator_pwd}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器版本">集中器版本：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.concentrator_version}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="通讯模式">通讯模式：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.communication_mode_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器版本">采集名称：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.collect_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器编号">集中器编号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.concentrator_num}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="创建时间">创建时间：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.create_time}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="更新时间">更新时间：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.update_time}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="公寓名称"name>公寓名称：</label>
            <div class="layui-input-inline">
                <input type="text" value="${ct.apartment_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item" style="width:600px;">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="备注">备注：</label>
            <div class="layui-input-inline">
                <textarea  name="remark" class="layui-textarea" maxlength="200" style="width:500px;border:none;" disabled>${ct.remark}</textarea>
            </div>
        </div>
    </div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
</html>