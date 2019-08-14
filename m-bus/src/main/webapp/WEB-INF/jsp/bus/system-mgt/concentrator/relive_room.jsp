<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>解除绑定</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;padding:10px;">

<input type="hidden" value="${concentratorId}" id="concentratorId"/>
<!-------默认表格开始----------->
<div class="tableHtml" style="height: 100%;background: #FFFFFF;">
    <div class="top-btn">
        <div class="layui-btn-group">
            <button class="layui-btn" id="cashBtn">解除绑定</button>
        </div>
        <div class="search-btn-group">
            <input type="text"  id="roomNum" autocomplete="off" class="layui-input layui-input_" placeholder="请输入房间号" maxlength="20">
            <button class="layui-btn layui-btns search-class" data-type="reload" >查询</button>
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
                <select id="roomTypeId">
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
                <input type="text" id="apartment_name" placeholder="请输入公寓名" autocomplete="off" class="layui-input" maxlength="20">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="楼层">楼层</label>
            <div class="layui-input-inline">
                <input type="text" id="floor_name" placeholder="请输入楼层" autocomplete="off" class="layui-input" maxlength="20">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn search-class" lay-submit="" lay-filter="confirmBtn">查询</button>
            <button type="button" class="layui-btn layui-btn-primary" id="closeBtn">收起</button>
        </div>
    </div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript" src="${ctx}/resources/water_meter_manage/waterMeterManage.js" ></script>
<script type="text/javascript">
    //主动加载jquery模块
    var $ = layui.jquery //重点处
    layui.use(['jquery', 'layer'], function(){
        layer = layui.layer;
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type:'checkbox'}
            ,{field: 'area_name', title: '区域'}
            ,{field: 'apartment_name', title: '公寓'}
            ,{field: 'floor_name', title: '楼层'}
            ,{field: 'room_num', title: '房间号'}
            ,{field: 'water_meter_id', title: '水表编号'}
        ]];
        var limitArr =[10,20,30];
        var urls = '${ctx}/concentrator/findRoomInfoNotConcentrator.action?concentratorId='+$("#concentratorId").val();
        com.tableRender('#layui-table','tableId','full-80',limitArr,urls,arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        $('.search-class').on('click', function(){
            /*查询条件*/
            var tableWhere={
                room_num:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,room_type_id: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,apartment_name:$('#apartment_name').val().replace(/(^\s*)|(\s*$)/g, "")
                ,floor_name:$('#floor_name').val().replace(/(^\s*)|(\s*$)/g, "")
            };
            com.reloadTable(tableWhere);
        });

        $("#cashBtn").click(function(){
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            if (checkStatus.data.length==0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请至少勾选一条目标数据！'
                });
                return false;
            }else{
                parent.layer.confirm('确定解除房间绑定的集中器!',{
                    title:'温馨提示',
                    btn:['确定','取消'],
                },function(){
                    var str=""
                    for(var i=0;i<checkStatus.data.length;i++)
                    {
                        str+=checkStatus.data[i].id+",";
                    }
                    $.ajax({
                        url:"${ctx}/concentrator/clearRoomConcentrator.action",
                        data:{str:str,concentratorId:$("#concentratorId").val()},
                        dataType:"json",
                        type:"post",
                        success:function(){
                            parent.layer.msg("操作成功!",{
                                icon:1,
                                time:2000
                            },function(){
                                var parentIndex = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(parentIndex);
                               /* window.location.reload();*/
                            });
                        },
                        error:function(){
                            parent.layer.msg("服务器异常!",{
                                icon:7,
                                time:2000
                            },function(){
                                var parentIndex = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(parentIndex);
                                /*window.location.reload();*/
                            });
                        }
                    });
                });
            }
        })
    });
</script>
</html>