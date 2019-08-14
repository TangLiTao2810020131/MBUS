<%@ taglib prefix="shiri" uri="http://shiro.apache.org/tags" %>
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
    <title>系统管理-水表管理</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <style>
        .layui-layer-content{
            border: none;
            box-shadow:0px 0px 0px #000 inset;
        }
    </style>
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;">

<!-------默认表格开始----------->

<div class="tableHtml" style="height: 100%;background: #FFFFFF;">
    <div class="top-btn">
        <div class="layui-btn-group" style="position: relative;">
           <%-- <shiro:hasPermission name="新增水表信息">--%>
            <button class="layui-btn" id="addBtn">添加</button>
            <%--</shiro:hasPermission>--%>
           <%-- <shiro:hasPermission name="编辑水表信息">--%>
            <button class="layui-btn" id="editBtn">编辑</button>
           <%-- </shiro:hasPermission>--%>
         <%--   <shiro:hasPermission name="批量删除水表信息">--%>
            <button class="layui-btn" id="delBtn">删除</button>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="绑定房间">--%>
            <button class="layui-btn" id="bindRoom">绑定房间</button>
           <%-- </shiro:hasPermission>--%>
           <%--<shiro:hasPermission name="批量增加水表">--%>
           <%--<button class="layui-btn" id="importBtn">批量增加水表</button>--%>
           <%-- </shiro:hasPermission>--%>
           <%--<shiro:hasPermission name="批量增加水表">--%>
           <button class="layui-btn" id="cancelBindBtn">解除房间绑定</button>
           <%-- </shiro:hasPermission>--%>
        </div>
        <div class="search-btn-group">
            <input type="text" name="water_meter_id"  id="water_meter_id" autocomplete="off" class="layui-input layui-input_" placeholder="请输入水表编号" maxlength="20">
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
            <label class="layui-form-label" title="水表类型">水表类型</label>
            <div class="layui-input-inline">
                <select name="type" id="type">
                    <option value="">请选择水表类型</option>
                    <option value="0">冷水水表</option>
                    <option value="1">生活热水表</option>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="添加时间">添加时间</label>
            <div class="layui-input-inline">
                <input type="text" name="create_time" id="create_time"
                       placeholder="请输入添加时间" autocomplete="off"
                       class="layui-input" maxlength="20">
			</div>
		</div>

  	<div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="button" class="layui-btn" lay-submit="" lay-filter="confirmBtn" id="gj">查询</button>
                    <button type="button" class="layui-btn layui-btn-primary" id="closeBtn">收起</button>
                </div>
            </div>
    </div>
</form>
<!--高级查询结束-->
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/html" id="handle">
    <%--<shiro:hasPermission name="查看绑定房间信息">--%>
    <a title="查看绑定房间信息" lay-event="detail" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
  <%--  </shiro:hasPermission>--%>
</script>
<script type="text/javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#create_time'
            ,type:'date'
            ,trigger: 'click'
            ,max:new Date()+''
        });
    })
    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function(){
        var $ = layui.jquery //重点处
            ,layer = layui.layer;
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type:'checkbox'}
            ,{field: 'water_meter_id', title: '水表编号', }
            /*,{field: 'name', title: '水表名称'}*/
            ,{field: 'type', title: '水表类型', templet : function(d) {
                    if (d.type == 0) { return "冷水水表";}
                    if(d.type == 1){ return "生活热水水表";}
                }}
            ,{field: 'roomNum', title: '绑定房间号', templet : function(d) {
                    if (d.roomNum == null) { return "未绑定房间";}
                    return d.roomNum;
                }}
            ,{field: 'roomTypeName', title: '房间类型', templet : function(d) {
            if (d.roomTypeName == null) { return "---";}
            return d.roomTypeName;
        } }
            ,{field: 'create_time', title: '添加时间', }
            ,{title: '操作',toolbar:'#handle'}


        ]];
        var limitArr =[10,20,30];
        var urls = '${ctx}/waterMeterMgt/listData.action';
        com.tableRender('#layui-table','tableId','full-50',limitArr,urls,arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        /*-----添加-----*/
        $("#addBtn").click(function(){
            com.pageOpen("添加水表信息","${ctx}/waterMeterMgt/addWaterMeterMgt.action",['600px', '350px']);
        })


        /*-----编辑-----*/
        $("#editBtn").click(function(){
            com.checkOpen("编辑水表信息","${ctx}/waterMeterMgt/edit.action",['600px', '350px']);
        })
        /*-----绑定房间------*/
        /*----检测水表是否绑定过房间------*/
        function isCheckWaterMeter(id){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/watermeterroominfomgt/isCheckWaterMeter.action", //路径
                data :{'id':id}, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        }
        /*----删除操作检测水表是否绑定过房间------*/
        function isCheckWaterMeterBind(ids){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/watermeterroominfomgt/isCheckWaterMeterBind.action", //路径
                data :{'ids':ids}, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        }

        /*----检验解除绑定的水表是否存在数据------*/
        function checkWaterMeterExitData(ids){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/watermeterroominfomgt/checkWaterMeterExitData.action", //路径
                data :{'ids':ids}, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        }


        $("#bindRoom").click(function(obj){
            var checkStatus = table.checkStatus('tableId');
            //数据至少是一条
            if (checkStatus.data.length != 1) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '只能勾选一条目标数据！'
                });
                return false;
            }
            var id=checkStatus.data[0].id;
            var num=isCheckWaterMeter(id);
            if (num != 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '已经绑定过房间！'
                })
                return false ;
            }

            com.checkOpen("绑定房间","${ctx}/waterMeterMgt/bindRoom.action",['800px', '700px']);
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
            }
            //var checkStatus = table.checkStatus('tableId');//test即为基础参数id对应的值
            var ids = "";
            for (var i = 0; i < checkStatus.data.length; i++) {
                ids += checkStatus.data[i].id + ",";
            }
            ids = ids.substring(0, ids.length - 1);
            var num=isCheckWaterMeterBind(ids);
            if (num != 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '删除的水表中存在绑定房间,不能删除！'
                })
                return false ;
            }
            layer.confirm("确定删除吗，删除后不可恢复!", {
                btn : [ "确定", "取消" ] //按钮
            }, function(index) {
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/waterMeterMgt/delWaterMeterMgt.action", //路径
                    data : {
                        "id" : ids
                    }, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    success : function(result) { //返回数据根据结果进行相应的处理
                        layer.msg("删除成功", {
                            icon : 1,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function() {
                            window.location.reload();
                        });
                    }
                });
            });

            return false;
        });

        //查看
        table.on('tool(tableBox)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                com.pageOpen("查看绑定房间信息","${ctx}/waterMeterMgt/waterMeterRoomInfo.action?id="+data.id,['700px', '400px']);
            }
        });

        /*-----批量增加-----*/
        $("#importBtn").click(function () {
            com.pageOpen("批量增加", "${ctx}/waterMeterMgt/waterImport.action", ['1000px', '565px']);
        })

<<<<<<< .mine
        $(function(){
            //查询重载表格
            $("#simple,#gj").on("click",function(){
                com.reloadTable(
                    {
                        water_meter_id:$("#water_meter_id").val(),
                        type:$('#type').val(),
                        create_time:$('#create_time').val()
                    }
                )
            });
        });
||||||| .r2243
        //表格重载
        // $(function(){
        //     //查询重载表格
        //     $("#simple,#gj").on("click",function(){
        //         com.reloadTable(
        //             {
        //                 water_meter_id:$("#water_meter_id").val(),
        //                 type:$('#type').val(),
        //                 update_time:$('#update_time').val()
        //             }
        //         )
        //     });
        // });
=======
        // 解除房间的绑定
        $("#cancelBindBtn").click(function(){
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            //数据至少是一条
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请至少勾选一条目标数据！'
                });
                return false;
            }
            var ids = "";
            for (var i = 0; i < checkStatus.data.length; i++) {
                ids += checkStatus.data[i].id + ",";
            }
            ids = ids.substring(0, ids.length - 1);
            debugger
            var num=isCheckWaterMeterBind(ids);
            if (num != checkStatus.data.length) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '选择的数据中存在未绑定的房间的水表,不能解除绑定！'
                })
                return false ;
            }

            var num2=checkWaterMeterExitData(ids);
            if (num2 != 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '选择的数据中有水表存在数据,请清零后再解除绑定！'
                })
                return false ;
            }


            layer.confirm("确定解除绑定吗？", {
                btn : [ "确定", "取消" ] //按钮
            }, function(index) {
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/waterMeterMgt/cancelBind.action", //路径
                    data : {
                        "id" : ids
                    }, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    success : function(result) { //返回数据根据结果进行相应的处理
                        layer.msg("解除绑定成功", {
                            icon : 1,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function() {
                            window.location.reload();
                        });
                    }
                });
            });

            return false;
        });

        //表格重载
        // $(function(){
        //     //查询重载表格
        //     $("#simple,#gj").on("click",function(){
        //         com.reloadTable(
        //             {
        //                 water_meter_id:$("#water_meter_id").val(),
        //                 type:$('#type').val(),
        //                 update_time:$('#update_time').val()
        //             }
        //         )
        //     });
        // });
>>>>>>> .r2286


    })
</script>
</html>