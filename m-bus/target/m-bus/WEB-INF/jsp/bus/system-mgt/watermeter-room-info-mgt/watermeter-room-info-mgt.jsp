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
  <title>系统管理-水表房间信息管理</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">	
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
 
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;">
<!-----------树菜单--------------->
<div id="treeBox" style="width: 200px;background: #FFFFFF;float: left;height: 100%;">
	
	<div class="treename">
		<span>水表房间信息列表</span>
	</div>
	<div class="layui-form1" id="tree">
		<ul id="tree"></ul>
		<!--<div id="xtree" class="xtree_contianer"></div>-->
	</div>
</div>

<!-------默认表格开始----------->

<div class="tableHtml" style="margin-left: 210px;height: 100%;background: #FFFFFF;">
  <div class="top-btn">
		<div class="layui-btn-group" style="position: relative;">
	    <button class="layui-btn" id="addBtn">添加</button>
	    <button class="layui-btn" id="editBtn">编辑</button>
	    <button class="layui-btn" id="delBtn">删除</button>
	    <button class="layui-btn" id="importBtn">导出</button>
	 </div>
	 <div class="search-btn-group">
	  	<input type="text"  autocomplete="off" class="layui-input layui-input_" placeholder="请输入">
	 		<button class="layui-btn layui-btns">查询</button>
			
				<button class="layui-btn layui-btns layui-bg-orange" id="superBtn">高级查询</button>

		</div>
	</div>
	<div class="tableDiv">
		<table id="layui-table" lay-filter="tableBox" class="layui-table"></table>
	</div>
</div>
<!-----------------默认表格结束------------>
<!--高级查询-->
<form class="layui-form" action="" id="formHtml" lay-filter="updForm">
	  <div class="layui-form-item">
			<div class="layui-inline">
	      <label class="layui-form-label" title="房间类型">房间类型</label>
	      <div class="layui-input-inline">
	        <select name="">   
	        	  <option value="0">全部</option>
	            <option value="1">学生公寓</option>
	            <option value="2">教师公寓</option>
	        </select>
	      </div>
	    </div>
  		<div class="layui-inline">
	      <label class="layui-form-label" title="公寓">公寓</label>
	      <div class="layui-input-inline">
	        <input type="text" name="" placeholder="请输入" lay-verify="required" autocomplete="off" class="layui-input">
	      </div>
		  </div>
	  </div>
  	<div class="layui-form-item">
			<div class="layui-inline">
	      <label class="layui-form-label" title="楼层">楼层</label>
	      <div class="layui-input-inline">
	       	<input type="text" name=""  placeholder="请输入" lay-verify="required" autocomplete="off" class="layui-input">
	      </div>
	   	</div>
	   	<div class="layui-inline">
	      <label class="layui-form-label" title="房间号">房间号</label>
	      <div class="layui-input-inline">
	       	<input type="text" name="" placeholder="请输入"  lay-verify="required" autocomplete="off" class="layui-input">
	      </div>
	   	</div>
  	</div>
  	<div class="layui-form-item">
	    <div class="layui-input-block">
	      <button type="button" class="layui-btn" lay-submit="" lay-filter="confirmBtn">查询</button>
	      <button type="button" class="layui-btn layui-btn-primary" id="closeBtn">收起</button>
	    </div>
 		</div>
</form>
<!--高级查询结束-->
</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
//主动加载jquery模块
layui.use(['jquery', 'layer'], function(){
	var $ = layui.jquery //重点处
	,layer = layui.layer;
	table = layui.table;//表格，注意此处table必须是全局变量
	var arr = [[ 
	    {type:'checkbox'}
      ,{field: 'quyu', title: '区域名称', }
      ,{field: 'gongyu', title: '区域名称'}
      ,{field: 'louceng', title: '分区备注'}
     
    ]];
	var limitArr =[10,20,30];
	var urls = '${ctx}/resources/js/tableJson.js';
	com.tableRender('#layui-table','tableId','full-50',limitArr,urls,arr);//加载表格,注意tableId是自定义的，在表格重载需要！！
	
	/*树形菜单*/
	com.normalTree("#tree","${ctx}/resources/js/treeJson.js");
	/*-----添加-----*/
 	$("#addBtn").click(function(){	
	  com.pageOpen("添加分区","html/system-mgt/watermeter-roominfo-mgt/add.html",['500px', '345px']);
	})

 	
 	/*-----编辑-----*/
 	$("#editBtn").click(function(){	
	  com.checkOpen("编辑","html/system-mgt/watermeter-roominfo-mgt/edit.html",['500px', '345px']);
	})	
	/*----删除-----*/
  $("#delBtn").click(function(){	
	  var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
    //数据只能是一条
    if (checkStatus.data.length == 0) {
        parent.layer.open({
            title: '温馨提示'
            , content: '请勾选一条目标数据！'
        });
        return false;
    } /*else if (checkStatus.data.length > 1) {
        parent.layer.open({
            title: '温馨提示'
            , content: '只能勾选一条数据！'
        });
        return false;
    }*/
    	console.log(checkStatus.data);
	})



})	
</script>
</html>