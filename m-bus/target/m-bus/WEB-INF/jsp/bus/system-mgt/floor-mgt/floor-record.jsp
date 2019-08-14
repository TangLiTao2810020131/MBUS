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
    <title>系统管理-房间管理</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;">
<!-------默认表格开始----------->

<div class="tableHtml" style="height: 100%;background: #FFFFFF;overflow: hidden;">

    <div class="tablebox" style="height: 680px; width: auto; overflow-x: hidden;overflow-y: auto;">
        <div class="tableDiv">
            <table id="layui-table" lay-filter="tableBox" class="layui-table"></table>
        </div>
        <div class="top-btn">
            <div class="layui-btn-group" style="position: relative;height: 38px;">
            </div>
            <div class="search-btn-group">
                <div class="layui-form search-btn-group-box" style="display: inline-block;">
                    <input type="hidden" id="tid" value="${tid}">
                    <div class="layui-inline">
                        <label class="layui-form-label" title="命令状态">命令状态</label>
                        <div class="layui-input-inline">
                            <select id="currentStatus">
                                <option value="">全部</option>
                                <option value="0">未下发</option>
                                <option value="1">下发成功</option>
                                <option value="2">下发失败</option>
                                <option value="3">未知</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form search-btn-group-box" style="display: inline-block;">
                    <div class="layui-inline">
                        <label class="layui-form-label" title="日期">日期</label>
                        <input type="text" id="date" lay-verify="date" placeholder="请选择时间" autocomplete="off"
                               class="layui-input layui-input_" lay-key="1">
                    </div>
                </div>

                <button class="layui-btn layui-btns search-class2" style="margin-right: 30px"> 查 询</button>

            </div>
        </div>
        <div class="tableDiv2" style="height: 600px;">
            <table id="layui-table2" lay-filter="tableBox2" class="layui-table"></table>
        </div>
    </div>
</div>
<!-----------------默认表格结束------------>

</body>
<script type="text/html" id="handle">
    <a title="查看" lay-event="detail" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>

<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript">
    //主动加载jquery模块
    var $ = layui.jquery //重点处
    layui.use(['jquery', 'layer'], function () {
        layer = layui.layer;
        //执行一个laydate实例
        var laydate = layui.laydate;
        laydate.render({
            elem: '#date' //指定元素
        });
        table = layui.table;//表格，注意此处table必须是全局变量
        /*table2*/
        var arr2 = [[
            {type: 'checkbox'}
            , {field: 'instructionNum', title: '命令编号'}
            , {field: 'apartmentName', title: '公寓'}
            , {field: 'typeName', title: '操作类型',}
            , {field: 'currentStatusName', title: '命令状态'}
            , {field: 'createTime', title: '创建时间',templet : "<div>{{layui.util.toDateString(d.ordertime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'result', title: '返回描述'}
            ,{title: '操作',toolbar:'#handle'}
        ]];
        var limitArr2 = [10, 20, 30];
        var urls2 = '${ctx}/floormgt/recordListData.action?id='+$("#tid").val();
        com.tableRender('#layui-table2', 'tableId2', 'full-80', limitArr2, urls2, arr2);//加载表格,注意tableId是自定义的，在表格重载需要！！

        //查看
        table.on('tool(tableBox2)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                com.pageOpen("查看指令记录","${ctx}/floormgt/findInstructionsRecord.action?id="+data.id,['800px', '550px']);
            }
        });

        $('.search-class2').on('click', function () {
            /*查询条件*/
            var tableWhere = {
                date: $('#date').val()
                ,currentStatus: $('#currentStatus').val()
                ,id: $('#tid').val()
            };
            table.reload('tableId2', {
                where:tableWhere
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        });
    })

</script>
</html>