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
    <title>报表查询-换表历史记录</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
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
            <%--<shiro:hasPermission name="换表历史记录导出">--%>
            <button class="layui-btn" id="exportBtn"> &nbsp;导 &nbsp;出&nbsp;</button>
          <%--  </shiro:hasPermission>--%>
        </div>
        <div class="search-btn-group">
            <input type="text" id="roomNum" autocomplete="off" class="layui-input layui-input_" placeholder="请输入房间号"
                   maxlength="20">
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
            <label class="layui-form-label" title="开户日期">开户日期</label>
            <div class="layui-input-inline">
                <input type="text" id="openTime" lay-verify="date" placeholder="请选择开户日期" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="换表日期">换表日期</label>
            <div class="layui-input-inline">
                <input type="text" id="changeTime" lay-verify="date" placeholder="请选择换表日期" autocomplete="off"
                       class="layui-input">
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
<script type="text/html" id="handle">
    <%--<shiro:hasPermission name="查看换表历史记录">--%>
    <a title="查看" lay-event="detail" style="cursor:pointer; margin-right:5px;">
        <i class="layui-icon">&#xe63c;</i>
    </a>
   <%-- </shiro:hasPermission>--%>
</script>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript">
    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function () {
        var $ = layui.jquery //重点处
            , layer = layui.layer;
        //执行一个laydate实例
        var laydate = layui.laydate;
        laydate.render({
            elem: '#openTime' //指定元素
        });
        laydate.render({
            elem: '#changeTime' //指定元素
        });
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type: 'checkbox'}
            , {field: 'apartmentName', title: '公寓'}
            , {field: 'roomNum', title: '房间号'}
            // , {field: 'roomTypeName', title: '房间类型'}
            , {field: 'waterType', title: '水表类型'}
            ,{field: 'currentStatusName', title: '操作状态'}
            , {field: 'openTime', title: '开户日期',templet : "<div>{{layui.util.toDateString(d.ordertime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'replaceTime', title: '换表日期',templet : "<div>{{layui.util.toDateString(d.ordertime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'replaceMoney', title: '换表费用'}
            , {field: 'replaceWaterNum', title: '换表次数'}
            ,{field: 'operationUser', title: '操作员'}
            ,{title: '操作',toolbar:'#handle'}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/replaceRecord/listData.action';
        com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        //查看
        table.on('tool(tableBox)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                com.pageOpen("查看换表历史记录","${ctx}/replaceRecord/findReplaceWaterRecord.action?id="+data.id,['800px', '550px']);
            }
        });

        $('.search-class').on('click', function(){
            /*查询条件*/
            var tableWhere={
                roomTypeId: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,openTime:$('#openTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,changeTime:$('#changeTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNum:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
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
                ,openTime:$('#openTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,changeTime:$('#changeTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNum:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            com.reloadTable(tableWhere);
        });

        //表格数据导出
        $(function(){
            $("#exportBtn").on("click",function(){
                var str="公寓,房间号,操作状态,水表类型,开户日期,换表日期,换表费用（元）,换表次数,操作员";
                var name="换表记录";
                var url="${ctx}/replaceRecord/export.action?str="+str+"&name="+name+"&roomTypeId="+$('#roomTypeId').val()+
                    "&openTime="+$('#openTime').val()+"&changeTime="+$('#changeTime').val()+
                    "&roomNum="+$('#roomNum').val()+
                    "&level="+$("#level").val()+"&parentId="+$("#parentId").val();
                window.location.href=url;
            });
        });
    });
</script>
</html>