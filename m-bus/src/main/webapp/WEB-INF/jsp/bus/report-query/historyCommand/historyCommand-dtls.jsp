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
    <title>报表查询-历史命令</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>

    <style>
        .layui-layer-content{
            border:none !important;
            box-shadow:none !important;
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
           <%-- <shiro:hasPermission name="历史命令导出">--%>
            <button class="layui-btn" id="exportBtn"> &nbsp;导 &nbsp;出&nbsp;</button>
           <%-- </shiro:hasPermission>--%>
        </div>
        <div class="search-btn-group">
            <input type="text" id="roomNumOut" autocomplete="off" class="layui-input layui-input_" placeholder="请输入房间号"  maxlength="20">
            <input type="text" id="startTime" lay-verify="date" placeholder="请选择开始时间" autocomplete="off"
                   class="layui-input layui-input_">
            <input type="text" id="endTime" lay-verify="date" placeholder="请选择结束时间" autocomplete="off"
                   class="layui-input layui-input_">
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
            <label class="layui-form-label" title="公寓">公寓</label>
            <div class="layui-input-inline">
                <input type="text" id="apartmentName" placeholder="请输入" lay-verify="required" autocomplete="off"
                       class="layui-input"  maxlength="20">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="楼层">楼层</label>
            <div class="layui-input-inline">
                <input type="text" id="floorNum" placeholder="请输入" lay-verify="required" autocomplete="off"
                       class="layui-input"  maxlength="20">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" title="房间号">房间号</label>
            <div class="layui-input-inline">
                <input type="text" id="roomNum" placeholder="请输入" onchange="fangjian()" lay-verify="required" autocomplete="off"
                       class="layui-input"  maxlength="20">
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
   <%-- <shiro:hasPermission name="查看历史命令">--%>
    <a title="查看" lay-event="detail" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
    <%--</shiro:hasPermission>--%>
   <%-- <shiro:hasPermission name="重新下发">--%>
    <a title="重新下发" lay-event="again" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe609;</i></a>
    <%--</shiro:hasPermission>--%>
</script>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript">

    layui.use([ 'laydate'], function(){
        var $ = layui.$;
        var laydate = layui.laydate;
        var nowTime = new Date().valueOf();
        var max = null;

        var start = laydate.render({
            elem: '#startTime',
            type: 'date',
            format:'yyyy-MM-dd',
            max: nowTime,
            btns: ['clear', 'confirm'],
            done: function(value, date){
                endMax = end.config.max;
                end.config.min = date; //最大时间为结束时间的开始值
                end.config.min.month = date.month -1;
            }
        });

        var end = laydate.render({
            elem: '#endTime',
            type: 'date',
            max:  nowTime,
            format:'yyyy-MM-dd',
            done: function(value, date){
                if($.trim(value) == ''){
                    var curDate = new Date();
                    date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
                }
                start.config.max = date;//最小时间为开始时间的最大值
                start.config.max.month = date.month -1;
            }
        })
    });

    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function () {
        var $ = layui.jquery //重点处
            , layer = layui.layer;
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type: 'checkbox'}
            , {field: 'areaName', title: '区域',}
            , {field: 'apartmentName', title: '公寓'}
            //, {field: 'floorName', title: '楼层'}
            , {field: 'roomNum', title: '房间号', templet : function(d) {
                    if (d.roomNum == null) { return "---";}
                    return d.roomNum;
                }}
            ,{field: 'concentratorNum', title: '集中器编号'}
            , {field: 'typeName', title: '指令类型'}
            , {field: 'instructionDetatil', title: '下发指令'}
            , {field: 'result', title: '响应结果', templet : function(d) {
                    if (d.result == null) { return "---";}
                    return d.result;
                }}
            //, {field: 'concentratorIp', title: '集中器'}
            //, {field: 'collectIp', title: '采集表'}
            //, {field: 'createTime', title: '冲红日期',templet : "<div>{{layui.util.toDateString(d.ordertime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'currentStatusName', title: '操作状态'}
            , {field: 'createTime', title: '操作时间' ,templet : "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            //, {field: 'operatName', title: '操作员'}
            ,{title: '操作',toolbar:'#handle'}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/historyCommand/listData.action';
        com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        //查看
        table.on('tool(tableBox)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                com.pageOpen("查看历史命令记录","${ctx}/historyCommand/findHistoryCommand.action?id="+data.id,['800px', '550px']);
            }else if(obj.event === 'again'){
                if(data.currentStatusName == '未下发'){
                    layer.confirm('确认重新下发命令!', {btn: ['确定', '取消']}, function (index, layero) {
                        var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                        $.ajax({
                            type: "POST",
                            async: false,
                            data: {"id": data.id},
                            url: '${ctx}/historyCommand/again.action',
                            success: function (data) {
                                layer.close(indexMsg);
                                if (data.status == "success") {
                                    layer.msg(data.msg,{
                                        icon:1,
                                        time:3000
                                    },function(){
                                        var tableWhere={
                                            roomTypeId: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                                            ,startTime:$('#startTime').val().replace(/(^\s*)|(\s*$)/g, "")
                                            ,endTime:$('#endTime').val().replace(/(^\s*)|(\s*$)/g, "")
                                            ,apartmentName:$('#apartmentName').val().replace(/(^\s*)|(\s*$)/g, "")
                                            ,floorNum:$('#floorNum').val().replace(/(^\s*)|(\s*$)/g, "")
                                            ,roomNum:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                                            ,roomNumOut:$('#roomNumOut').val().replace(/(^\s*)|(\s*$)/g, "")
                                            ,level:$('#level').val()
                                            ,parentId:$('#parentId').val()
                                        };
                                        com.reloadTable(tableWhere);
                                    });
                                }else{
                                    layer.msg(data.msg,{
                                        icon:7,
                                        time:3000
                                    });
                                }
                            }
                        });
                        }
                    );

                }else{
                    layer.msg("此命令已下发！",{
                        icon:7,
                        time:3000
                    });
                    return;
                }
            }
        });

        $('.search-class').on('click', function(){
            /*查询条件*/
            var tableWhere={
                roomTypeId: $('#roomTypeId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,startTime:$('#startTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,endTime:$('#endTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,apartmentName:$('#apartmentName').val().replace(/(^\s*)|(\s*$)/g, "")
                ,floorNum:$('#floorNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNum:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNumOut:$('#roomNumOut').val().replace(/(^\s*)|(\s*$)/g, "")
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
                ,startTime:$('#startTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,endTime:$('#endTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,apartmentName:$('#apartmentName').val().replace(/(^\s*)|(\s*$)/g, "")
                ,floorNum:$('#floorNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNum:$('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,roomNumOut:$('#roomNumOut').val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            com.reloadTable(tableWhere);
        });

        //表格数据导出
        $(function(){
            $("#exportBtn").on("click",function(){
                var str="区域,公寓,房间号,水表编号,指令类型,下发指令,响应结果,操作状态,操作时间";
                var name="历史命令";
                var url="${ctx}/historyCommand/export.action?str="+str+"&name="+name+"&startTime="+$('#startTime').val()+
                    "&endTime="+$('#endTime').val()+"&roomTypeId="+$('#roomTypeId').val()+
                    "&apartmentName="+$('#apartmentName').val()+"&floorNum="+$('#floorNum').val()+
                    "&roomNum="+$('#roomNum').val()+"&concentratorId="+$('#concentratorId').val()+
                    "&level="+$("#level").val()+"&parentId="+$("#parentId").val();
                window.location.href=url;
            });
        });

    });

    function fangjian() {
        $('#roomNumOut').val($('#roomNum').val());
    }
</script>
</html>