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
<title>首页</title>
<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
<link rel="shortcut  icon" type="../image/x-icon" href="${ctx}/resources/images/etsIco.ico" media="screen" />
<style type="text/css">
* {
	padding: 0;
	margin: 0;
}
 .layui-table-cell{
  	padding: 0;
  	text-align: center;
  }
.container{
	width: 100%;
	
	/*overflow: auto;*/
}
.left-sider{
	background: #FFFFFF;
	float: left;
	width:330px;
	
}
.left-sider ul {
	overflow: hidden;
	margin-left: -3.3333%;
}
.left-sider ul li{
	width: 30%;
	float: left;
	text-align: center;
	background-color: #F8F8F8;
	margin:5px 0 5px 3.3333333%;
	cursor: pointer;
}
.left-sider ul li:hover{
	background: #428BCA;
}
.left-sider ul li:hover cite{
	color: #FFFFFF;
	transform:scale(1.2);
}
.left-sider ul li:hover i{
	color: #FFFFFF;
	transform:scale(1.05);
}
.left-sider li i{
	display: inline-block;
    width: 100%;
    height: 60px;
    line-height: 60px;
    text-align: center;
    border-radius: 2px;
    font-size: 30px;
    transition: all 0.5s;  
}
.left-sider li cite {
    position: relative;
    top: -5px;
    display: block;
    color: #666;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    font-size: 14px;
    transition: all .5s;  
}

.right-sider{
	background: #FFFFFF;
	margin-left: 340px;
	
}
.layui-tab{
	margin: 0;
}


.layui-tab-content{
	padding: 0;
}
</style>
</head>
<body>
	<div class="container">
		<!--高频功能快捷操作区  start-->
		<div class="left-sider">
		<div class="layui-card">
            <div class="layui-card-header">高频功能快捷操作区</div>
            <div class="layui-card-body">
                <ul>
                	<li class="">
                        <a href="${ctx}/waterpurchasemgt/waterPurchaseMgt.action">
                          <i class="layui-icon layui-icon-rmb"></i>
                          <cite>购水管理</cite>
                        </a>
                    </li>
                    <li class="">
                        <a href="${ctx}/wateraddmgt/waterAddMgt.action">
                          <i class="layui-icon layui-icon-water"></i>
                          <cite>补水管理</cite>
                        </a>
                    </li>
                    <li class="">
                        <a href="${ctx}/waterquitmgt/waterQuitMgt.action">
                          <i class="layui-icon layui-icon-set"></i>
                          <cite>退水管理</cite>
                        </a>
                    </li>
                    <li class="">
                        <a href="${ctx}/waterroommgt/waterRoomMgt.action">
                          <i class="layui-icon layui-icon-file-b"></i>
                          <cite>控水换房管理</cite>
                        </a>
                    </li>
               		 <li class="">
                        <a href="404.html">
                          <i class="layui-icon layui-icon-file-b"></i>
                          <cite>404页面</cite>
                        </a>
                    </li>
                </ul>
            </div>
		</div>
		<!--高频功能快捷操作区  end-->
		</div>
		
		<!--右边表格部分  start-->
		<div class="right-sider">
			<div class="layui-tab layui-tab-brief">
			  <ul class="layui-tab-title">
			    <li class="layui-this">电池电量报警</li>
			    <li>低水量报警</li>
			    <li>磁攻击报警</li>
			   
			  </ul>
			  <div class="layui-tab-content" style="margin-top: -6px;">
			    <div class="layui-tab-item layui-show">
			    	<table id="layui-table1" lay-filter="tableBox" class="layui-table"></table>
			    </div>
			    <div class="layui-tab-item">
			    	<table id="layui-table2" lay-filter="tableBox" class="layui-table"></table>
			    </div>
			    <div class="layui-tab-item">
			    	<table id="layui-table3" lay-filter="tableBox" class="layui-table"></table>
			    </div>
			  </div>
			</div>
		</div>
		<!--右边表格部分  end-->
	</div>
</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script>
//主动加载jquery模块
layui.use(['jquery', 'layer'], function(){
	var $ = layui.jquery //重点处
	,layer = layui.layer;
	/*---快捷操作----*/
	var url_;
	$(".layui-card-body ul a").click(function(){
		url_ = $(this).attr("href");//获取到当前点击跳转的快捷方式的url地址
		var uls = window.parent.document.getElementById("navList");
		for(var i = 0;i<$(uls).find("a").length;i++){
			if($(uls).find("a").eq(i).attr("menu_url") == url_){								
				var txt = $('.nav-bar', parent.document);//获取iframe父级页面的nav-bar的节点
				$(txt).find("span").text('当前位置：'+$(uls).find("a").eq(i).attr("page_name"));//当前位置的路径
				$(uls).find("a").eq(i).parents('li').siblings().removeClass("layui-this").removeClass("layui-nav-itemed");
				$(uls).find("a").eq(i).parents('li').addClass('layui-nav-itemed');
				$(uls).find("a").eq(i).parents('dd').addClass('layui-nav-itemed');
				$(uls).find("a").eq(i).parent().addClass('layui-this').removeClass("layui-nav-itemed");
			}
		}
	})
	table = layui.table;//表格，注意此处table必须是全局变量
	/*--电池电量报警  表格开始----*/
	var arr1 = [[ 
	    //{type:'checkbox'}
      {field: 'quyu', title: '区域1', }
      ,{field: 'gongyu', title: '公寓'}
      ,{field: 'louceng', title: '楼层'}
      ,{field: 'fangjianhao', title: '房间号'} 
    ]];
	var limitArr1 =[10,20,30];
	var urls1 = '${ctx}/resources/js/tableJson.js';
	com.tableRender('#layui-table1','tableId1','full-57',limitArr1,urls1,arr1);
	/*--电池电量报警  表格结束----*/
	
	/*--低水量报警  表格开始----*/
	var arr2 = [[ 
	   // {type:'checkbox'}
      {field: 'quyu', title: '区域2', }
      ,{field: 'gongyu', title: '公寓'}
      ,{field: 'louceng', title: '楼层'}
      ,{field: 'fangjianhao', title: '房间号'} 
    ]];
	var limitArr2 =[10,20,30];
	var urls2 = '${ctx}/resources/js/tableJson.js';
	com.tableRender('#layui-table2','tableId2','full-57',limitArr2,urls2,arr2);
	/*--低水量报警  表格结束----*/
	
	/*--磁攻击报警  表格开始----*/
	var arr3 = [[ 
	   //{type:'checkbox'}
      {field: 'quyu', title: '区域3', }
      ,{field: 'gongyu', title: '公寓'}
      ,{field: 'louceng', title: '楼层'}
      ,{field: 'fangjianhao', title: '房间号'} 
    ]];
	var limitArr3 =[10,20,30];
	var urls3 = '${ctx}/resources/js/tableJson.js';
	com.tableRender('#layui-table3','tableId3','full-57',limitArr3,urls3,arr3);
	/*--磁攻击报警  表格结束----*/
		
}) 
 
   

</script>
</html>