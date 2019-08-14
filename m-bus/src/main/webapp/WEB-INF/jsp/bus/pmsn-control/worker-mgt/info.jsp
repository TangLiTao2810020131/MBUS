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
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css"/>
</head>
<body>
<div class="layui-body layui-bg-white">
    <div class="layui-fluid">
        <div class="layui-row  layui-col-space15">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 12px;">
                <legend>账号信息</legend>
            </fieldset>
            <div class="layui-form-item">
                <label class="layui-form-label">职工账号:</label>
                <label class="layui-form-mid">${worker.workerAccount }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">登录名:</label>
                <label class="layui-form-mid">${worker.workerName }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">姓名:</label>
                <label class="layui-form-mid">${worker.realName }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">邮箱:</label>
                <label class="layui-form-mid">${worker.email }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">注册时间:</label>
                <label class="layui-form-mid"><fmt:formatDate value="${worker.ctime }" pattern="yyyy-MM-dd	HH:mm:ss" /></label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">状态:</label>
                <label class="layui-form-mid">
                    <c:if test="${worker.isOpen==0}">启用</c:if>
                    <c:if test="${worker.isOpen==1}">禁用</c:if>
                </label>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">角色:</label>
                <label class="layui-form-mid">
                    <c:forEach items="${list }" var="roleName">
                        【${ roleName}】
                    </c:forEach>
                </label>
            </div>

        </div>
    </div>
</div>

</body>
</html>