<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>水表管理-房间水量清零</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
    <style>
        [title]{
            width:100px !important;
        }
    </style>
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
           <%-- <shiro:hasPermission name="清零">--%>
            <button class="layui-btn" id="resetBtn"> &nbsp;&nbsp;清 零 &nbsp;&nbsp;</button>
         <%--   </shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="按导入清零">--%>
            <button class="layui-btn" id="importBtn">按导入清零</button>
            <%--</shiro:hasPermission>--%>
        </div>
        <div class="search-btn-group">
            <input type="text" id="roomNumOut" autocomplete="off" class="layui-input layui-input_" placeholder="请输入房间号"
                   maxlength="20">
            <button class="layui-btn layui-btns search-class" data-type="reload">查询</button>

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
        <input type="hidden" id="level">
        <input type="hidden" id="parentId">
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
                <input type="text" name="" id="apartmentId" autocomplete="off" class="layui-input" maxlength="20">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="楼层">楼层</label>
            <div class="layui-input-inline">
                <input type="text" id="floorId" placeholder="请输入" autocomplete="off" class="layui-input" maxlength="20">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" title="房间号">房间号</label>
            <div class="layui-input-inline">
                <input type="text" id="roomNum" placeholder="请输入" autocomplete="off" class="layui-input" onchange="fangjian()" maxlength="20">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="集中器编号">集中器编号</label>
            <div class="layui-input-inline">
                <input type="text" id="concentratorNum" placeholder="请输入" autocomplete="off" class="layui-input" maxlength="20">
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
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript" src="${ctx}/resources/water_meter_manage/waterMeterManage.js"></script>
<script type="text/javascript">
    //主动加载jquery模块
    var $ = layui.jquery //重点处
    layui.use(['jquery', 'layer'], function () {
           layer = layui.layer;
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type: 'checkbox'}
            ,{field: 'areaName', title: '区域', }
            ,{field: 'apartmentName', title: '公寓'}
            ,{field: 'buildName', title: '栋'}
            ,{field: 'floorName', title: '楼层'}
            ,{field: 'roomNum', title: '房间号'}
            // ,{field: 'initStatusName', title: '初始化状态'}
            // ,{field: 'valveStatusName', title: '阀门状态'}
            // ,{field: 'moduleStatusName', title: '模块状态'}
            ,{field: 'surplusWater', title: '剩余水量'}
            ,{field: 'buyWaterTotal', title: '购水量'}
            ,{field: 'supplementWater', title: '补水量'}
            ,{field: 'returnWater', title: '退水量'}
            ,{field: 'userWater', title: '总用水量'}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/waterreset/listData.action';
        com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！
        $('.search-class').on('click', function () {
            /*查询条件*/
            var tableWhere = {
                roomTypeId: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                , apartmentId: $('#apartmentId').val().replace(/(^\s*)|(\s*$)/g, "")
                , floorId: $('#floorId').val().replace(/(^\s*)|(\s*$)/g, "")
                , roomNum: $('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                , roomNumOut: $('#roomNumOut').val().replace(/(^\s*)|(\s*$)/g, "")
                ,concentratorNum:$("#concentratorNum").val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            com.reloadTable(tableWhere);
        });

        function reload(){
            var tableWhere = {
                roomTypeId: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                , apartmentId: $('#apartmentId').val().replace(/(^\s*)|(\s*$)/g, "")
                , floorId: $('#floorId').val().replace(/(^\s*)|(\s*$)/g, "")
                , roomNum: $('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                , roomNumOut: $('#roomNumOut').val().replace(/(^\s*)|(\s*$)/g, "")
                ,concentratorNum:$("#concentratorNum").val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            return tableWhere;
        }
        /*-----清零-----*/
        $("#resetBtn").click(function () {
            com.checkOpen("清零", "${ctx}/waterreset/waterReset.action", ['1000px', '540px'],reload());
        })

        /*-----按导入清零-----*/
        $("#importBtn").click(function () {
            com.pageOpen("按导入清零", "${ctx}/waterreset/waterResetImport.action", ['1000px', '565px'],reload());
        })
    })

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
                roomTypeId: ''
                , apartmentId: ''
                , floorId: ''
                , roomNum: ''
                , roomNumOut: ''
                ,concentratorNum:''
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            com.reloadTable(tableWhere);
        });
    });
</script>
</html>