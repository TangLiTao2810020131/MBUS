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
  <title>参数设置-房间类型参数</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
	<link rel="shortcut  icon" type="../image/x-icon" href="${ctx}/resources/images/etsIco.ico" media="screen" />
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
 
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative; ">
	<div style="border-top: 1px solid #e6e6e6;"></div>

<!-------默认表格开始----------->

<div class="tableHtml" style="height: 100%;background: #FFFFFF;">
  <div class="top-btn">
		<div class="layui-btn-group" style="position: relative;">
			<%--<shiro:hasPermission name="新增房间类型">--%>
	    <button class="layui-btn" id="addBtn">添加</button>
			<%--</shiro:hasPermission>--%>
			<%--<shiro:hasPermission name="编辑房间类型">--%>
	    <button class="layui-btn" id="editBtn">编辑</button>
		<%--	</shiro:hasPermission>--%>
			<%--<shiro:hasPermission name="删除房间类型">--%>
	    <button class="layui-btn" id="delBtn">删除</button>
			<%--</shiro:hasPermission>--%>
	  </div>
	  <div class="search-btn-group">
			<input type="text" autocomplete="off" id="type_name" class="layui-input layui-input_" maxlength="20" placeholder="请输入类型名称">
	 		<button class="layui-btn layui-btns" id="find">查询</button>

	</div>
  </div>
	<div class="tableDiv">
		<table id="layui-table" lay-filter="tableBox" class="layui-table"></table>
	</div>
</div>
<!-----------------默认表格结束------------>
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
  ,{field: 'type_num', title: '类型编号', }
  ,{field: 'type_name', title: '类型名称'}
  ,{field: 'buy_money', title: '购水单价(元/m3)'}
  ,{field: 'return_money', title: '退水单价(元/m3)'}
  ,{field: 'warn_water', title: '报警水量(m3)'}
  ,{field: 'valve_water', title: '关阀水量(m3)'}
  ,{field: 'over_water', title: '最大透支量(m3)'}
  ,{field: 'hoard_water', title: '最大囤积量(m3)'}
  ,{field: 'add_cycle_name', title: '软件补水周期'}
  ,{field: 'create_time', title: '创建时间'}
  ,{field: 'effect_time', title: '参数生效时间'}
  ]];
	var limitArr =[10,20,30];
	var urls = '${ctx}/roomtype/listData.action';
	com.tableRender('#layui-table','tableId','full-50',limitArr,urls,arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

	/*-----新增-----*/
 	$("#addBtn").click(function(){	
	  com.pageOpen("新增房间类型","${ctx}/roomtype/addRoomTypePage.action",['800px', '540px']);
	})
 	/*-----编辑-----*/
 	$("#editBtn").click(function(){	
	  com.checkOpen("编辑房间类型","${ctx}/roomtype/editRoomTypePage.action",['800px', '540px']);
	});

    /*----删除-----*/
    $("#delBtn").click(function(){
        var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
        //数据至少是一条
        if (checkStatus.data.length == 0) {
            parent.layer.open({
                title: '温馨提示'
                , content: '请至少勾选一条目标数据！'
            });
            return false;
        }else{
            parent.layer.confirm('数据删除后不可恢复，请谨慎操作!',{
                btn:['确定','取消'],
				title:'温馨提示'
			},function(){
                var str="";
                var arr=""
                for(var i=0;i<checkStatus.data.length;i++)
                {
                    str+=checkStatus.data[i].id+",";
                    arr+=checkStatus.data[i].type_num+",";
                }
                $.ajax({
                    url:"${ctx}/roomtype/delRoomType.action",
                    data:{str:str,arr:arr},
                    dataType:"json",
                    type:"post",
                    success:function(result){
                        if(result=="yes"){
							 parent.layer.msg("删除成功！",{
                                 icon:1,
                                 time:3000
                             },function(){
                                 window.location.reload();
							 });
                        }else{
                            parent.layer.msg("选中房间类型中有被使用的，不允许被删除！", {
                                icon: 1,
                                time: 2000
                            });
                        }
                    },
                    error:function(){
                        layer.msg("服务器异常!",{
                            icon:7,
                            time:2000
                        });
                    }
                });
			});
        }
    });
    //表格重载
    $("#find").on("click",function(){
        com.reloadTable({type_name:$("#type_name").val()})
    });
})	
</script>
</html>