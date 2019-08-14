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
    <title>参数设置-一卡通终端</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>

</head>
<body class="inner-body" style="background:#f2f2f2;position: relative; ">
<div style="border-top: 1px solid #e6e6e6;"></div>

<!-------默认表格开始----------->

<div class="tableHtml" style="height: 100%;background: #FFFFFF;">
    <div class="top-btn">
        <div class="layui-btn-group">
            <%--<shiro:hasPermission name="新增一卡通终端参数">--%>
            <button class="layui-btn" id="addBtn">添加</button>
          <%--  </shiro:hasPermission>--%>
          <%--  <shiro:hasPermission name="编辑一卡通终端参数">--%>
            <button class="layui-btn" id="editBtn">编辑</button>
           <%-- </shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="删除一卡通终端参数">--%>
            <button class="layui-btn" id="delBtn">删除</button>
           <%-- </shiro:hasPermission>--%>
        <%--    <shiro:hasPermission name="启用一卡通终端参数">--%>
            <button class="layui-btn" id="start">启用</button>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="禁用一卡通终端参数">--%>
            <button class="layui-btn" id="end">禁用</button>
           <%-- </shiro:hasPermission>--%>
        </div>
        <div class="search-btn-group">

            <input type="text" autocomplete="off" id="terminalNum" class="layui-input layui-input_" placeholder="请输入终端编号" maxlength="20">
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
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript">
    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function () {
        var $ = layui.jquery //重点处
            , layer = layui.layer;
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type: 'checkbox'}
            // , {field: 'areaName', title: '区域',}
            // , {field: 'apartmentName', title: '公寓'}
            ,{field: 'terminalNum', title: '终端编号'}
            , {field: 'terminalAddress', title: 'IP地址'}
            , {field: 'terminalTypeName', title: '终端类型'}
            , {field: 'terminalFun', title: '终端功能'}
            , {field: 'terminalStatusName', title: '终端状态'}
            , {field: 'minWater', title: '水量下限（吨）'}
            , {field: 'minMoney', title: '卡底金（元）'}
            , {field: 'minMoney', title: '日限金额（元）'}
            , {field: 'heartbeatTime', title: '心跳时间（秒）'}
            , {field: 'collectTime', title: '信息采集时间', templet : "<div>{{layui.util.toDateString(d.collectTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/smartcard/listData.action';
        com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        /*-----新增-----*/
        $("#addBtn").click(function () {
            com.pageOpen("新增一卡通参数", "${ctx}/smartcard/add.action", ['900px', '550px']);
        });
        /*-----编辑-----*/
        $("#editBtn").click(function () {
            com.checkOpen("编辑一卡通参数", "${ctx}/smartcard/edit.action", ['800px', '550px']);
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
                var tc=parent.layer.confirm('数据删除后将不可恢复,请谨慎操作!',{
                    title:'温馨提示',
                    btn:['确定','取消']
                },function(){
                    parent.layer.close(tc);
                    var str="";
                    for(var i=0;i<checkStatus.data.length;i++)
                    {
                        str+=checkStatus.data[i].id+",";
                    }
                    $.ajax({
                        url:"${ctx}/smartcard/delSmartCard.action",
                        data:{str:str},
                        dataType:"json",
                        type:"post",
                        success:function(result){
                            parent.layer.msg("删除成功!",{
                                icon:1,
                                time:2000
                            },function(){
                                window.location.reload();
                            });
                        },
                        error:function(){
                            parent.layer.msg("服务器异常!",{
                                icon:7,
                                time:2000
                            });
                        }
                    });
                });
            }
        });

        /*----启用/禁用-----*/
        $("#start,#end").click(function(){
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            //数据至少是一条
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请至少勾选一条目标数据！'
                });
                return false;
            }else{
                    var str="";
                    for(var i=0;i<checkStatus.data.length;i++)
                    {
                        str+=checkStatus.data[i].id+",";
                    }
                    var flag='';
                    if($(this).attr("id")=="start"){
                        flag=0;
                    }else {
                        flag = 1;
                    }
                    $.ajax({
                        url:"${ctx}/smartcard/startAndEnd.action?flag="+flag,
                        data:{str:str},
                        dataType:"json",
                        type:"post",
                        success:function(result){
                            parent.layer.msg(result,{
                                icon:1,
                                time:2000
                            },function(){
                                window.location.reload();
                            });
                        },
                        error:function(){
                            parent.layer.msg("服务器异常!",{
                                icon:7,
                                time:2000
                            });
                        }
                    });
            }
        });

        //表格重载
        $("#find").on("click",function(){
            com.reloadTable({terminalNum:$("#terminalNum").val()});
        });
    });
</script>
</html>