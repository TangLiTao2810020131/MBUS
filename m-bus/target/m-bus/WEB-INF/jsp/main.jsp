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
    <script src="${ctx}/resources/echarts/echarts.min.js"></script>
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
            <div class="layui-card-header" style="background-color: #cdd1d4;">高频功能快捷操作区</div>
            <div class="layui-card-body">
                <ul>
                   <%-- <shiro:hasPermission name="购水管理">--%>
                	<li class="">
                        <a href="${ctx}/waterpurchasemgt/waterPurchaseMgt.action">
                          <i class="layui-icon layui-icon-rmb"></i>
                          <cite>购水管理</cite>
                        </a>
                    </li>
                    <%--</shiro:hasPermission>--%>
                    <%--<shiro:hasPermission name="补水管理">--%>
                    <li class="">
                        <a href="${ctx}/wateraddmgt/waterAddMgt.action">
                          <i class="layui-icon layui-icon-water"></i>
                          <cite>补水管理</cite>
                        </a>
                    </li>
                  <%--  </shiro:hasPermission>--%>
                <%--    <shiro:hasPermission name="退水管理">--%>
                    <li class="">
                        <a href="${ctx}/waterquitmgt/waterQuitMgt.action">
                          <i class="layui-icon layui-icon-water"></i>
                          <cite>退水管理</cite>
                        </a>
                    </li>
                   <%-- </shiro:hasPermission>
                    <shiro:hasPermission name="集中器管理">--%>
                    <li class="">
                        <a href="${ctx}/concentratormgt/concentratorMgt.action">
                          <i class="layui-icon layui-icon-set"></i>
                          <cite>集中器管理</cite>
                        </a>
                    </li>
               <%--     </shiro:hasPermission>
                    <shiro:hasPermission name="房间管理">--%>
               		 <li class="">
                        <a href="${ctx}/floormgt/floorMgt.action">
                          <i class="layui-icon layui-icon-templeate-1"></i>
                          <cite>房间管理</cite>
                        </a>
                    </li>
                  <%--  </shiro:hasPermission>
                    <shiro:hasPermission name="职工管理">--%>
                    <li class="">
                        <a href="${ctx}/workermgt/workerMgt.action">
                            <i class="layui-icon layui-icon-user"></i>
                            <cite>职工管理</cite>
                        </a>
                    </li>
                   <%-- </shiro:hasPermission>--%>
                </ul>
            </div>
		</div>
            <div class="layui-card">
                <div class="layui-card-header" style="background-color: #cdd1d4">集中器在线情况</div>
                <div class="layui-card-body">
                    <div id="emain" style="width: 330px;height:330px;"></div>
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
var $ = layui.jquery //重点处
layer = layui.layer;
layui.use(['jquery', 'layer'], function(){
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
         {field: 'areaName', title: '区域', }
        ,{field: 'apartmentName', title: '公寓'}
        ,{field: 'floorName', title: '楼层'}
        ,{field: 'roomNum', title: '房间号'}
        ,{field: 'waterNum', title: '水表编号'}
        ,{field: 'warningTime', title: '报警时间'}

    ]];
	var limitArr1 =[10,20,30];
	var urls1 = '${ctx}/warning/warningData.action?flag=0';
	com.tableRender('#layui-table1','tableId1','full-57',limitArr1,urls1,arr1);
	/*--电池电量报警  表格结束----*/
	
	/*--低水量报警  表格开始----*/
	var arr2 = [[ 
	   // {type:'checkbox'}
        {field: 'areaName', title: '区域', }
        ,{field: 'apartmentName', title: '公寓'}
        ,{field: 'floorName', title: '楼层'}
        ,{field: 'roomNum', title: '房间号'}
        ,{field: 'waterNum', title: '水表编号'}
        ,{field: 'warningTime', title: '报警时间'}
    ]];
	var limitArr2 =[10,20,30];
	var urls2 = '${ctx}/warning/warningData.action?flag=1';
	com.tableRender('#layui-table2','tableId2','full-57',limitArr2,urls2,arr2);
	/*--低水量报警  表格结束----*/
	
	/*--磁攻击报警  表格开始----*/
	var arr3 = [[ 
	   //{type:'checkbox'}
        {field: 'areaName', title: '区域', }
        ,{field: 'apartmentName', title: '公寓'}
        ,{field: 'floorName', title: '楼层'}
        ,{field: 'roomNum', title: '房间号'}
        ,{field: 'waterNum', title: '水表编号'}
        ,{field: 'warningTime', title: '报警时间'}
    ]];
	var limitArr3 =[10,20,30];
	var urls3 = '${ctx}/warning/warningData.action?flag=2';
	com.tableRender('#layui-table3','tableId3','full-57',limitArr3,urls3,arr3);
	/*--磁攻击报警  表格结束----*/
		
});


// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('emain'));
$(function() {
    echartAjax();
});


function echartAjax(){
    $.ajax({
        type: "POST",
        async: false,
        url: '${ctx}/waterpurchasemgt/onlineSituation.action',
        success: function (data) {
            option = {
                color: ['#5fd25d', '#6f6e73'],
                // title : {
                //     text: '某站点用户访问来源',
                //     subtext: '纯属虚构',
                //     x:'center'
                // },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['在线','离线'],
                },
                series : [
                    {
                        name: '在线情况',
                        type: 'pie',
                        // radius: ['30%', '60%'],
                        radius: '55%',
                        center: ['40%', '35%'],
                        data:[
                            {value:data[0].num, name:data[0].name},
                            {value:data[1].num, name:data[1].name},
                        ],
                        itemStyle: {
                            normal: {
                                label: {        //此处为指示线文字
                                    show: true,
                                    position: 'inner', //标签的位置
                                    textStyle: {
                                        fontWeight: 150,
                                        fontSize: 15    //文字的字体大小
                                    },
                                },
                                labelLine: {    //指示线状态
                                    show: true,
                                    smooth: 0.2,
                                    length: 10,
                                    length2: 20
                                }
                            }
                        },
                        // 设置值域的那指向线
                        labelLine: {
                            normal: {
                                show: true   // show设置线是否显示，默认为true，可选值：true ¦ false
                            }
                        }
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    });
}

// myChart.on('click', function (params) {
//
// });
</script>
</html>