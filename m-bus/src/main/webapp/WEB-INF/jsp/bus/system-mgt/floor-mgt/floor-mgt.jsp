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
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>

    <style>
        .uls_cla{
            left: 465px !important;
        }
        .top-btn .layui-btn {
            padding: 0 6px;
        }
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

<div class="tableHtml" style="margin-left: 210px;height: 100%;background: #FFFFFF;position: relative;">

    <div class="tablebox" style="height: 680px; width: auto;overflow: auto;">
        <div class="top-btn">
            <div class="layui-btn-group" style="position: relative;">
                <%--<shiro:hasPermission name="初始化房间">--%>
                <button class="layui-btn" id="Btn9">初始化房间</button>
                <%--</shiro:hasPermission>--%>
               <%-- <shiro:hasPermission name="更新房间参数">--%>
                <button class="layui-btn" id="Btn1">更新房间参数</button>
              <%--  </shiro:hasPermission>--%>
                <%--<shiro:hasPermission name="获取房间信息">--%>
                <button class="layui-btn" id="Btn2">获取房间信息</button>
               <%-- </shiro:hasPermission>--%>
               <%-- <shiro:hasPermission name="一般开阀">--%>
                <button class="layui-btn" id="Btn3">一般开阀</button>
                <%--</shiro:hasPermission>--%>
               <%-- <shiro:hasPermission name="一般关阀">--%>
                <button class="layui-btn" id="Btn4">一般关阀</button>
             <%--   </shiro:hasPermission>--%>
                <%--<shiro:hasPermission name="强制开阀">--%>
                <button class="layui-btn" id="Btn5">强制开阀</button>
              <%--  </shiro:hasPermission>--%>
                <%--<shiro:hasPermission name="强制关阀">--%>
                <button class="layui-btn" id="Btn6">强制关阀</button>
               <%-- </shiro:hasPermission>--%>
               <%-- <shiro:hasPermission name="更换水表">--%>
                <button class="layui-btn" id="Btn7">更换水表</button>
               <%-- </shiro:hasPermission>--%>
             <%--   <shiro:hasPermission name="房间管理指令记录">--%>
                <button class="layui-btn" id="Btn8">指令记录</button>
              <%--  </shiro:hasPermission>--%>
            </div>
            <div class="search-btn-group">
                <input type="text" id="roomNumOut" autocomplete="off" class="layui-input layui-input_" placeholder="请输入房间号">
                <button class="layui-btn layui-btns search-class">查询</button>
                <button class="layui-btn layui-btns layui-bg-orange" id="superBtn">高级查询</button>
            </div>
        </div>

        <div class="tableDiv">
            <table id="layui-table" lay-filter="tableBox" class="layui-table"></table>
        </div>
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
                <input type="text" id="apartmentId" placeholder="请输入" autocomplete="off" class="layui-input" maxlength="20">
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
        //执行一个laydate实例
        var laydate = layui.laydate;
        laydate.render({
            elem: '#date' //指定元素
        });
        table = layui.table;//表格，注意此处table必须是全局变量
        /*table1*/
        var arr = [[
            {type: 'checkbox'}
            , {field: 'apartmentName', title: '公寓'}
            , {field: 'floorName', title: '楼层'}
            , {field: 'roomNum', title: '房间号'}
            , {field: 'valveStatusName', title: '阀状态',}
            // , {field: 'moduleStatusName', title: '模块状态'}
            , {field: 'initStatusName', title: '初始化状态'}
            , {field: 'buyWaterTotal', title: '剩余购水量'}
            , {field: 'supplementWater', title: '剩余补水量'}
            // , {field: 'surplusWater', title: '剩余水量'}
            , {field: 'userWater', title: '用水量',}
            , {field: 'roomTypeName', title: '房间类型', templet : function(d) {
                    if (d.roomTypeName == null) { return "---";}
                    return d.roomTypeName;
                }}
            , {field: 'waterMeterNum', title: '水表编号',templet : function(d) {
                    if (d.waterMeterNum == null) { return "未绑定房间";}
                    return d.waterMeterNum;
                }}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/waterpurchasemgt/listData.action';
        com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        var idArray = [];//选中行的集合

        function reload(){
            var tableWhere={
                roomTypeId: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,apartmentId:$('#apartmentId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,floorId:$('#floorId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNum:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNumOut:$('#roomNumOut').val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
                ,concentratorNum:$("#concentratorNum").val().replace(/(^\s*)|(\s*$)/g, "")
            };
        }

        /*-----更新房间参数-----*/
        $("#Btn1").click(function () {
            idArray = [];//清空
            com.confirm('将平台中的房间参数更新到水表中。', '更新房间参数', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"ids": idArray.join(',')},
                    url: '${ctx}/floormgt/updateRoomParam.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {
                            setTimeout("flutable()", 2500);
                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        })

        /*----获取房间信息-----*/
        $("#Btn2").click(function () {
            idArray = [];//清空
            com.confirm('确认获取房间信息。', '获取房间信息', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"ids": idArray.join(',')},
                    url: '${ctx}/floormgt/getRoomInfo.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {
                            setTimeout("flutable()", 2500);
                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        })

        /*----一般开阀-----*/
        $("#Btn3").click(function () {
            idArray = [];//清空
            com.confirm('确认一般开阀。', '获取房间信息', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"ids": idArray.join(',')},
                        url: '${ctx}/floormgt/open.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {
                            setTimeout("flutable()", 2500);
                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        })

        /*----一般关阀-----*/
        $("#Btn4").click(function () {
            idArray = [];//清空
            com.confirm('确认一般关阀。', '获取房间信息', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"ids": idArray.join(',')},
                    url: '${ctx}/floormgt/close.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {
                            setTimeout("flutable()", 2500);
                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        })

        /*----强制开阀-----*/
        $("#Btn5").click(function () {
            idArray = [];//清空
            com.confirm('确认强制开阀。', '获取房间信息', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"ids": idArray.join(',')},
                    url: '${ctx}/floormgt/forceOpen.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {
                            setTimeout("flutable()", 2500);
                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        })

        /*----强制关阀-----*/
        $("#Btn6").click(function () {
            idArray = [];//清空
            com.confirm('确认强制关阀。', '获取房间信息', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"ids": idArray.join(',')},
                    url: '${ctx}/floormgt/forceClose.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {
                            setTimeout("flutable()", 2500);
                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        });

        /*----更换水表-----*/
        $("#Btn7").click(function () {
            var checkStatus = table.checkStatus('tableId');
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请勾选一条目标数据！'
                });
                return false;
            }else if (checkStatus.data.length > 1) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '只能勾选一条数据！'
                });
                return false;
            }
            if(checkStatus.data[0].waterMeterNum == null){
                parent.layer.open({
                    title: '温馨提示'
                    , content: '未绑定水表，不能进行换表操作！'
                })
                return false ;
            }
            com.checkOpen("更换水表", "${ctx}/floormgt/changeWatermeter.action", ['800px', '600px'],reload());

        })
        /*----指令记录-----*/
        $("#Btn8").click(function(){
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            //数据只能是一条
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请勾选一条目标数据！'
                });
                return false;
            }else if (checkStatus.data.length > 1) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '只能勾选一条数据！'
                });
                return false;
            }
            console.log(checkStatus.data);
            var index = parent.layer.open({ //在父窗口打开
                title:'指令记录' ,
                resize:false,//禁止拉伸
                type: 2,
                area: ['1000px','600px'],
                content:"${ctx}/floormgt/record.action?id="+checkStatus.data[0].id,
            });
            parent.index_  = index;//传给子页面的值index
            parent.id_ = checkStatus.data[0].id;//传给子页面的数据id！！！！
        })

        /*-----初始化房间-----*/
        $("#Btn9").click(function () {
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            //数据只能是一条
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请勾选一条目标数据！'
                });
                return false;
            } else if (checkStatus.data.length > 1) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '只能勾选一条数据！'
                });
                return false;
            }
            idArray = [];//清空
            com.confirm('确认初始化房间。', '更新房间参数', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"id": checkStatus.data[0].id},
                    url: '${ctx}/floormgt/initRoom.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {
                            setTimeout("flutable()", 2500);
                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        })

        $('.search-class').on('click', function(){
            /*查询条件*/
            var tableWhere={
                roomTypeId: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,apartmentId:$('#apartmentId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,floorId:$('#floorId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNum:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNumOut:$('#roomNumOut').val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
                ,concentratorNum:$("#concentratorNum").val().replace(/(^\s*)|(\s*$)/g, "")
            };
            com.reloadTable(tableWhere);
        });
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
                roomTypeId: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,apartmentId:$('#apartmentId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,floorId:$('#floorId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNum:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNumOut:$('#roomNumOut').val().replace(/(^\s*)|(\s*$)/g, "")
                ,concentratorNum:$("#concentratorNum").val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:param.level
                ,parentId:param.parentId
            };
            com.reloadTable(tableWhere);
        });
    });

    function fangjian() {
        $('#roomNumOut').val($('#roomNum').val());
    }
</script>
</html>