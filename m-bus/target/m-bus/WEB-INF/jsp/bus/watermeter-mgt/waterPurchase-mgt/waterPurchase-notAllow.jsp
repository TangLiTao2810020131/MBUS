<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>水表管理-购水管理-现金购水</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
    <style>
        .layui-form-item{margin-bottom: 10px;}
    </style>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend class="window-leg">提示信息</legend>
</fieldset>
<div class="layui-form layui-form-pane ml30">
    <div class="layui-form-item" style="color: red">${errMsg}</div>
</div>
</body>

</html>