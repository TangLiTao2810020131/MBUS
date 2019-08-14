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
  <title>系统管理-采集应用管理</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">	
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
 
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;">
</div>

<!-------默认表格开始----------->

<div class="tableHtml" style="height: 100%;background: #FFFFFF;">
  <div class="top-btn">
		<div class="layui-btn-group" style="position: relative;">
			<%--<shiro:hasPermission name="添加采集应用">--%>
	    <button class="layui-btn" id="addBtn">添加</button>
			<%--</shiro:hasPermission>--%>
			<%--<shiro:hasPermission name="编辑采集应用">--%>
	    <button class="layui-btn" id="editBtn">编辑</button>
			<%--</shiro:hasPermission>--%>
			<%--<shiro:hasPermission name="删除采集应用">--%>
	    <button class="layui-btn" id="delBtn">删除</button>
			<%--</shiro:hasPermission>--%>
	 </div>
	 <div class="search-btn-group">
	  	<input type="text"  id="collect_address" autocomplete="off" class="layui-input layui-input_" placeholder="请输入IP地址" maxlength="20">
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
	  <div class="layui-form-item">
  		<div class="layui-inline">
	      <label class="layui-form-label" title="采集应用编号">采集应用编号</label>
	      <div class="layui-input-inline">
	        <input type="text" name="" id="application_number" placeholder="请输入采集应用编号" lay-verify="" autocomplete="off" class="layui-input" maxlength="20">
	      </div>
		  </div>
	  </div>
  	<div class="layui-form-item">
			<div class="layui-inline">
	      <label class="layui-form-label" title="采集名称">采集名称</label>
	      <div class="layui-input-inline">
	       	<input type="text" name=""  id="collect_name" placeholder="请输入采集名称" lay-verify="" autocomplete="off" class="layui-input" maxlength="20">
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
      ,{field: 'application_number', title: '采集应用编号', }
      ,{field: 'collect_name', title: '采集名称'}
      ,{field: 'collect_address', title: 'IP地址'}
      ,{field: 'failure', title: '密钥失效期', }
      ,{field: 'auto_collect_frequency', title: '自动采集频率'}
      /*,{field: 'instruction_number', title: '当前执行的命令编号'}*/
     
    ]];
	var limitArr =[10,20,30];
	var urls = '${ctx}/clctapplmgt/listData.action';
	com.tableRender('#layui-table','tableId','full-50',limitArr,urls,arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

	/*-----添加-----*/
 	$("#addBtn").click(function(){	
	  com.pageOpen("添加采集应用","${ctx}/clctapplmgt/addClctApplMgtPage.action",['850px', '555px']);
	})

 	
 	/*-----编辑-----*/
 	$("#editBtn").click(function(){	
	  com.checkOpen("编辑","${ctx}/clctapplmgt/editClctApplMgtPage.action",['850px', '555px']);
    })
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
	            btn:['确定','取消']
				,title:'温馨提示'
			},function(){
                var str="";
                for(var i=0;i<checkStatus.data.length;i++)
                {
                    str+=checkStatus.data[i].id+",";
                }
                $.ajax({
                    url:"${ctx}/clctapplmgt/delClctApplMgt.action",
                    data:{str:str},
                    dataType:"json",
                    type:"post",
                    success:function(result){
                        if(result=="yes"){
                            parent.layer.msg("选中的采集应用中有部分关联集中器，不允许删除！",{
                                icon:1,
                                time:2000
                            });
                        }else{
                            parent.layer.msg("删除成功!",{
                                icon:1,
                                time:2000
                            },function(){
                                window.location.reload();
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
    $(function(){
        //查询重载表格
        $("#simple,#gj").on("click",function(){

        com.reloadTable(
                {
                    collect_address:$("#collect_address").val(),
                    application_number:$("#application_number").val(),
                    collect_name:$("#collect_name").val()
                }
            )
        });
    });


})	
</script>
</html>