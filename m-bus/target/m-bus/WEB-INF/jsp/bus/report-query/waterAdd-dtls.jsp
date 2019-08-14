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
  <title>报表查询-补水记录</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
  	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
	<link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
	<link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;">
<!-----------树菜单--------------->
<div id="treeBox" style="width: 200px;background: #FFFFFF;float: left;height: 100%;">
	<div class="treename">
		<span>公寓房间列表</span>
	</div>
	<div class="layui-form1" id="tree"  style="height:600px;overflow:auto">
		<ul id="demoTree1" class="dtree" data-id="0"></ul>
	</div>
</div>
<!-------默认表格开始----------->

<div class="tableHtml" style="margin-left: 210px;height: 100%;background: #FFFFFF;">
  <div class="top-btn">
		<div class="layui-btn-group">
			<%--<shiro:hasPermission name="补水明细导出">--%>
	    <button class="layui-btn" id="exportBtn"> &nbsp;导 &nbsp;出&nbsp; </button>
			<%--</shiro:hasPermission>--%>
	  </div>
	  <div class="search-btn-group">
	  	    <input type="text" name="date" id="date2" id="startTime" lay-verify="date" placeholder="请选择开始时间" autocomplete="off" class="layui-input layui-input_">
			<input type="text" name="date" id="date1" id="endTime" lay-verify="date" placeholder="请选择结束时间" autocomplete="off" class="layui-input layui-input_">
	 		<button class="layui-btn layui-btns" id="simple">查询</button>
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
	  <input type="hidden" id="level"/>
	  <input type="hidden" id="parentId"/>
	  <div class="layui-form-item">
		  <div class="layui-inline">
	      	<label class="layui-form-label" title="房间类型">房间类型</label>
			  <div class="layui-input-inline">
				<select name="" id="room_type_id">
					<option value="">全部</option>
					<c:forEach items="${roomTypeList}" var="roomType">
						<option value="${roomType.typeNum}">${roomType.typeName}</option>
					</c:forEach>
				</select>
			  </div>
	    </div>
		  <div class="layui-inline">
			  <label class="layui-form-label" title="公寓">公寓</label>
			  <div class="layui-input-inline">
				  <input type="text" name="" id="apartment_name" placeholder="请输入公寓" lay-verify="" autocomplete="off" class="layui-input" maxlength="20">
			  </div>
		  </div>
	  </div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label" title="楼层">楼层</label>
			<div class="layui-input-inline">
				<input type="text" name="" id="floor_name" placeholder="请输入楼层" lay-verify="" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label" title="购水方式">房间号</label>
			<div class="layui-input-inline">
				<input type="text" name="" id="room_num" placeholder="请输入房间号" lay-verify="" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
	</div>
  	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button type="button" class="layui-btn" lay-submit="" lay-filter="confirmBtn" id="gj">查询</button>
	      <button type="button" class="layui-btn layui-btn-primary" id="closeBtn">收起</button>
	    </div>
	  </div>
</form>
</body>
<script type="text/html" id="handle">
	<shiro:hasPermission name="查看补水记录">
	<a title="查看" lay-event="detail" style="cursor:pointer; margin-right:5px;">
		<i class="layui-icon">&#xe63c;</i>
	</shiro:hasPermission>
	</a>
</script>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
	<script type="text/javascript" src="${ctx}/resources/plugins/zTree_v3/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
//主动加载jquery模块
layui.use(['jquery', 'layer'], function(){
	var $ = layui.jquery //重点处
	,layer = layui.layer;
	//执行一个laydate实例
  var laydate = layui.laydate;
 	laydate.render({
    	elem: '#date1', //指定元素
		max:new Date()+"" //设置当前日期最大值不能超过当前日期
        ,type:'datetime'
        ,done: function(value, date, endDate){
            var startDate=$("#date2").val();
            if(startDate.trim().length!=0)
            {
                if(value<startDate)
                {
                    layer.msg('结束时间不能小于开始时间!',{
                        icon:7,
                        time:2000
                    },function(){
                        $("#date1").val("");
                    });
                }
            }
        }
    });
  	laydate.render({
    	elem: '#date2', //指定元素
        max:new Date()+"" //设置当前日期最大值不能超过当前日期
        ,type:'datetime'
        ,done: function(value, date, endDate){
            var endDate=$("#date1").val();
            if(endDate.trim().length!=0)
            {
                if(value>endDate)
                {
                    layer.msg('开始时间不能大于结束时间!',{
                        icon:7,
                        time:2000
                    },function(){
                        $("#date2").val("");
                    });
                }
            }
        }
    });
	table = layui.table;//表格，注意此处table必须是全局变量
	var arr = [[ 
	    {type:'checkbox'}
        ,{field: 'area_name', title: '区域'}
        ,{field: 'apartment_name', title: '公寓'}
        ,{field: 'floor_name', title: '楼层'}
        ,{field: 'room_num', title: '房间号'}
        // ,{field: 'room_type_name', title: '房间类型'}
        ,{field: 'current_status_name', title: '操作状态'}
        ,{field: 'create_time', title: '补水日期'}
        // ,{field: 'ip_address', title: '集中器'}
        // ,{field: 'current_status_name', title: '是否成功'}
        ,{field: 'supplement_water', title: '补水量'}
        ,{field: 'user_name', title: '操作员'}
        ,{title: '操作',toolbar:'#handle'}
    ]];
	var limitArr =[10,20,30];
	var urls = '${ctx}/addWaterRecord/listData.action';
	com.tableRender('#layui-table','tableId','full-50',limitArr,urls,arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

    //查看
    table.on('tool(tableBox)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            com.pageOpen("查看补水记录","${ctx}/addWaterRecord/findAddRecord.action?id="+data.id,['800px', '550px']);
        }
    });


    $(function(){
        //简单查询重载表格
        $("#simple,#gj").on("click",function(){
            var endDate=$("#date1").val();
            var startDate=$("#date2").val();
            endDate.length==0&&startDate.length!=0||endDate.length!=0&&startDate.length==0 ? layer.msg("请选择开始时间和结束时间!",{
                    icon:7,
                    time:2000
                }) : com.reloadTable({
						room_num:$("#room_num").val().trim(),
						startTime:$("#date2").val().trim(),
						endTime:$("#date1").val().trim(),
						room_type_id:$("#room_type_id").val().trim(),
						apartment_name:$("#apartment_name").val().trim(),
						floor_name:$("#floor_name").val().trim()
                });
        });
    });

    //表格数据导出
    $(function(){
        $("#exportBtn").on("click",function(){
            var str="区域,公寓,楼层,房间号,操作状态,补水日期,补水量(吨),操作员";
            var name="补水记录";
            var url="${ctx}/addWaterRecord/export.action?str="+str+"&name="+name+"&room_num="+$("#room_num").val()+
                "&startTime="+$("#date2").val()+"&endTime="+$("#date1").val()+
                "&room_type_id="+$("#room_type_id").val()+"&apartment_name="+$("#apartment_name").val()+"&floor_name="+$("#floor_name").val()+
                "&level="+$("#level").val()+"&id="+$("#parentId").val();
            window.location.href=url;
        });
    });

    //tree
    layui.config({
        base: '${ctx}/resources/layui_ext/dtree/' //配置 layui 第三方扩展组件存放的基础目录
    }).extend({
        dtree: 'dtree' //定义该组件模块名
    }).use(['element','layer', 'dtree'], function(){
        var layer = layui.layer,
            dtree = layui.dtree,
            $ = layui.$;
        dtree.render({
            elem: "#demoTree1",  //绑定元素
            url: "${ctx}/region/treeData.action", //异步接口
            initLevel: 1,  // 指定初始展开节点级别
            cache: false,  // 当取消节点缓存时，则每次加载子节点都会往服务器发送请求
            async: false,
        });

        //单击节点 监听事件
        dtree.on("node('demoTree1')" ,function(param){
            $("#level").val(param.level);
            $("#parentId").val(param.parentId);
            /*查询条件*/
            var tableWhere={
                endTime: ''
                ,startTime:''
				,room_num:''
				,room_type_id:''
				,apartment_name:''
				,floor_name:''
                ,level:param.level
                ,id:param.parentId
            };
            com.reloadTable(tableWhere);
        });
    });
})	
</script>
</html>