<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>404页面</title>
<link rel="stylesheet" href="css/layui.css"/>
<link rel="shortcut  icon" type="../image/x-icon" href="images/etsIco.ico" media="screen" />
</head>
<body style="background: #FFFFFF;">
	<style type="text/css">
		a:hover{color: #333333;}
	</style>
	<div class="content" style="text-align: center;">
		<a href="javascript:void(0);">
          <i class="layui-icon layui-icon-404" style="font-size: 400px;"></i>
          <h3>抱歉！您访问的页面出现了问题，请稍后再试。</h3>
          <a href="main.html" style="display: inline-block;color: #428BCA; text-decoration: underline;margin-top: 10px;" id="backBtn">返回首页</a>
        </a>
	</div>
</body>
<script src="layui.all.js"></script>
<script src="js/common.js" type="text/javascript" charset="utf-8"></script>
<script>
//主动加载jquery模块
layui.use(['jquery', 'layer'], function(){
	var $ = layui.jquery //重点 处
	,layer = layui.layer;
	
}) 
 
   

</script>
</html>