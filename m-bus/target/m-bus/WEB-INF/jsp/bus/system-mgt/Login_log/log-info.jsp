<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css"/>
</head>
<body>
<div class="layui-body layui-bg-white">
	<div class="layui-fluid">
		<div class="layui-row  layui-col-space15">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 12px;">
            	<legend>登录日志信息</legend>
            </fieldset>
            <div class="layui-form-item">
                <label class="layui-form-label">职员账号:</label>
                <label class="layui-form-mid">${loginfo.workerAccount }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">职员登录名:</label>
                <label class="layui-form-mid">${loginfo.workerName }</label>
            </div>
            <div class="layui-form-item">
            	<label class="layui-form-label">IP地址:</label>
                <label class="layui-form-mid">${loginfo.ipaddress }</label>
            </div>
            <div class="layui-form-item">
            	<label class="layui-form-label">登录时间:</label>
                <label class="layui-form-mid">${loginfo.loginTime }</label>
            </div>
            <div class="layui-form-item">
            	<label class="layui-form-label">登录状态:</label>
                <label class="layui-form-mid">${loginfo.loginState }</label>
            </div>
          
		</div>
	</div>
</div>

</body>
</html>