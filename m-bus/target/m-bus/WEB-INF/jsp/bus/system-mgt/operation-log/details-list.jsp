<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <title>报表查询-职工操作日志-详情页面</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">	
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;overflow: hidden;">
<div class="layui-body layui-bg-white" >
	<div class="layui-fluid">
		<div class="layui-row  layui-col-space15" style="margin-right:50px;">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 12px;">
				<legend>职工操作日志信息</legend>
			</fieldset>
			<div class="layui-form-item">
				<label class="layui-form-label">操作职员:</label>
				<label class="layui-form-mid">${loginfo.workerName }</label>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">操作业务:</label>
				<label class="layui-form-mid">${loginfo.moduleName }</label>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">业务信息:</label>
				<label class="layui-form-mid">${loginfo.operaContent}</label>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">操作时间:</label>
				<label class="layui-form-mid">${loginfo.operaTime }</label>
			</div>


		</div>
	</div>
</div>

</body>
</html>