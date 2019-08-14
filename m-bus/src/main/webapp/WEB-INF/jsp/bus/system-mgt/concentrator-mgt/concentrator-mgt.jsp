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
    <title>系统管理-集中器管理</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
    <style>
        .layui-input-inline {
            width: 120px;
        }

        .layui-form-label {
            width: 60px;
        }
    </style>

</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;">
<!-----------树菜单--------------->
<div id="treeBox" style="width: 200px;background: #FFFFFF;float: left;height: 100%;">
    <div class="treename">
        <span>公寓房间列表</span>
    </div>
    <div class="layui-form1" id="tree" style="height:600px;overflow:auto">
        <ul id="demoTree1" class="dtree" data-id="0"></ul>
    </div>
</div>

<!-------默认表格开始----------->

<div class="tableHtml" style="margin-left: 210px;height: 100%;background: #FFFFFF;">
    <div class="top-btn">
        <!--更多操作html-->
        <input type="hidden" id="level"/>
        <input type="hidden" id="parentId"/>

        <div class="layui-btn-group" style="position: relative;">
            <%--<shiro:hasPermission name="设置集中器">--%>
            <button class="layui-btn" id="Btn7">设置集中器</button>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="集中器校时">--%>
            <button class="layui-btn" id="Btn1">&nbsp;&nbsp;校时&nbsp;&nbsp;</button>
            <%--  </shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="集中器发参数">--%>
            <button class="layui-btn" id="Btn2">发参数</button>
            <%--</shiro:hasPermission>--%>
            <%-- <shiro:hasPermission name="集中器获取房间信息">--%>
            <button class="layui-btn" id="Btn3">获取房间信息</button>
            <%--  </shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="重启集中器">--%>
            <button class="layui-btn" id="Btn4">重启集中器</button>
            <%--    </shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="更换集中器">--%>
            <button class="layui-btn" id="Btn5">更换集中器</button>
            <%--     </shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="集中器指令记录">--%>
            <button class="layui-btn" id="Btn6">指令记录</button>
            <%--<button class="layui-btn" id="moreBtn">更多</button>--%>
            <%--     </shiro:hasPermission>--%>
        </div>
        <div class="search-btn-group">
            <div class="layui-inline">
                <label class="layui-form-label" title="集中器">集中器</label>
                <div class="layui-input-inline">
                    <input type="text" id="concentratorNum" placeholder="请输入集中器编号" maxlength="20" autocomplete="off" class="layui-input"/>
                </div>
            </div>
            <input type="hidden" id="areaName"/>
            <input type="hidden" id="apartmentName"/>
            <button class="layui-btn layui-btns search-class">查询</button>
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
            <label class="layui-form-label" title="在线情况">在线情况</label>
            <div class="layui-input-inline">
                <select id="status">
                    <option value="" selected>请选择在线情况</option>
                    <option value="0">在线</option>
                    <option value="1">离线</option>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn search-class" lay-submit="" lay-filter="confirmBtn" id="gj">查询</button>
            <button type="button" class="layui-btn layui-btn-primary" id="closeBtn">收起</button>
        </div>
    </div>
</form>
<!--高级查询结束-->

</body>
<script type="text/html" id="handle">
    <a title="查看" lay-event="detail" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>
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
            , {field: 'concentratorNum', title: '集中器编号'}
            , {field: 'ipAddress', title: '集中器'}
            , {field: 'areaName', title: '区域'}
            , {field: 'apartmentName', title: '公寓'}
            , {field: 'online', title: '在线情况' ,
                templet : function(d) {
                    if (d.online == "在线") {
                        return '<div>'+ '<span class="layui-badge">'+d.online+'</span> '+'</div>'
                    }else {
                        return '<div>'+ '<span class="layui-badge layui-bg-gray">'+d.online+'</span> '+'</div>'
                    }
                }

            }
            , {title: '操作', toolbar: '#handle'}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/concentratormgt/listData.action';
        com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        //查看
        table.on('tool(tableBox)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                com.pageOpen("查看集中器", "${ctx}/concentratormgt/findConcentratormgtById.action?id=" + data.id, ['800px', '550px']);
            }
        });

        $('.search-class').on('click', function () {
            /*查询条件*/
            var tableWhere = {
                apartmentId: $('#apartmentName').val().replace(/(^\s*)|(\s*$)/g, "")
                , areaId: $('#areaName').val().replace(/(^\s*)|(\s*$)/g, "")
                , concentratorNum: $('#concentratorNum').val().replace(/(^\s*)|(\s*$)/g, "")
                , status: $("#status").val().replace(/(^\s*)|(\s*$)/g, "")
                , level: $('#level').val()
                , parentId: $('#parentId').val()
            };
            com.reloadTable(tableWhere);
        });

        var idArray = [];//选中行的集合
        /*-----校时-----*/
        $("#Btn1").click(function () {
            idArray = [];//清空
            com.confirm('将服务器时间更新到集中器上。', '校时', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"idArray": idArray.join(',')},
                    url: '${ctx}/concentratormgt/calibrationTime.action',
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
        /*----发参数-----*/
        $("#Btn2").click(function () {
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
            var index = parent.layer.open({ //在父窗口打开
                title: '发参数',
                resize: false,//禁止拉伸
                type: 2,
                area: ['360px', '260px'],
                content: "${ctx}/concentratormgt/setParam.action?id=" + checkStatus.data[0].id,
            });
            parent.index_ = index;//传给子页面的值index
            parent.id_ = checkStatus.data[0].id;//传给子页面的数据id！！！！
        })
        /*----获取房间信息-----*/
        $("#Btn3").click(function () {
            idArray = [];//清空
            com.confirm('获取集中器下所有的房间的当前信息。', '获取房间信息', ['确定', '取消'], idArray, function () {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"idArray": idArray.join(',')},
                    url: '${ctx}/concentratormgt/getRoomInfo.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {

                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层

            });
        })
        /*----重启集中器-----*/
        $("#Btn4").click(function () {
            idArray = [];//清空
            com.confirm('重新重启集中器。', '重启集中器', ['确定', '取消'], idArray, function () {
                //重启集中器，确定的回调
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"idArray": idArray.join(',')},
                    url: '${ctx}/concentratormgt/restart.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {

                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        })
        /*----换集中器-----*/
        $("#Btn5").click(function () {
            idArray = [];//清空
            com.confirm('将集中器所管理的房间信息更新到新的集中器上，不影响计量模块。', '换集中器', ['确定', '取消'], idArray, function () {
                //换集中器，确定的回调
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: {"idArray": idArray.join(',')},
                    url: '${ctx}/concentratormgt/replace.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {

                        }
                    }
                });
                parent.layer.closeAll();//关闭弹出层
            });
        })
        /*----指令记录-----*/
        $("#Btn6").click(function () {
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
            console.log(checkStatus.data);
            var index = parent.layer.open({ //在父窗口打开
                title: '指令记录',
                resize: false,//禁止拉伸
                type: 2,
                area: ['1000px', '600px'],
                content: "${ctx}/concentratormgt/record.action?id=" + checkStatus.data[0].id,
            });
            parent.index_ = index;//传给子页面的值index
            parent.id_ = checkStatus.data[0].id;//传给子页面的数据id！！！！
        });

        //集中器列表
        $("#Btn7").click(function () {
            parent.layer.open({
                title: '集中器列表',
                resize: false,//禁止拉伸
                type: 2,
                maxmin: true,
                area: ['1000px', '600px'],
                    content: "${ctx}/concentrator/list.action"
            });
        });

    })

    layui.config({
        base: '${ctx}/resources/layui_ext/dtree/' //配置 layui 第三方扩展组件存放的基础目录
    }).extend({
        dtree: 'dtree' //定义该组件模块名
    }).use(['element', 'layer', 'dtree'], function () {
        var layer = layui.layer,
            dtree = layui.dtree,
            $ = layui.$;
        dtree.render({
            elem: "#demoTree1",  //绑定元素
            url: "${ctx}/region/treeDataApartment.action", //异步接口
            initLevel: 1,  // 指定初始展开节点级别
            cache: false,  // 当取消节点缓存时，则每次加载子节点都会往服务器发送请求
            async: false,
        });

        //单击节点 监听事件
        dtree.on("node('demoTree1')", function (param) {
            $("#level").val(param.level);
            $("#parentId").val(param.parentId);
            /*查询条件*/
            var tableWhere = {
                apartmentId: $('#apartmentName').val().replace(/(^\s*)|(\s*$)/g, "")
                , areaId: $('#areaName').val().replace(/(^\s*)|(\s*$)/g, "")
                ,concentratorNum:''
                ,status:''
                , level: param.level
                , parentId: param.parentId
            };
            com.reloadTable(tableWhere);
        });
    });
</script>
</html>