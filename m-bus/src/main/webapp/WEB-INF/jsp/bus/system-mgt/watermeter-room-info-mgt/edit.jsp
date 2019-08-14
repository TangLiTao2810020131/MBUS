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
  <title>系统管理-水表房间信息管理-编辑</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
  <style>
  	.layui-form-item{margin-bottom: 10px;}
  </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<form class="layui-form layui-form-pane ml30"  action="" id="formHtml" style="width: ;margin-top: 20px;" lay-filter="updForm">
	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">分区名称</label>
	      <div class="layui-input-inline">
	        <input type="text" name="" lay-verify="required" autocomplete="off" class="layui-input">
	      </div>
	    </div>
  	</div>
  	<div class="layui-form-item layui-form-text"  style="width: 400px;">
	    <label class="layui-form-label">备注</label>
	    <div class="layui-input-block" >
	      <textarea placeholder="请输入内容" class="layui-textarea" style="resize:none;"></textarea>
	    </div>
   </div>
  	<div class="layui-form-item window-block-btn">
	    <div class="layui-input-block">
	    	<button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 47%">取消</button>
	      <button type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">确定</button>
	      
	    </div>
 	</div>
</form>


</body>

	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
	 //主动加载jquery模块
	layui.use(['jquery', 'layer'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;
		//得到父页面的id值
		var index = (window.parent.index_);
		console.log(index);
		
		layui.use('form', function(){  
		 	var form = layui.form;
		 	//------确认添加分区-------
			form.on('submit(confirmBtn)', function (data) {
			    var obj = data.field;
			    console.log(obj);
			    //以下是ajax
			    
			    /*添加成功之后，关闭弹窗，刷新父页面*/
			    parent.layer.close(index);//关闭layer
			    $('#iframe',parent.document).attr('src', 'html/system-mgt/watermeter-roominfo-mgt/watermeter-roominfo-mgt.html');
			});
			//取消
			$("body").on("click","#canleBtn",function(){
				parent.layer.close(index);//关闭layer
			})
			
		})	
	})
   
</script>
</html>