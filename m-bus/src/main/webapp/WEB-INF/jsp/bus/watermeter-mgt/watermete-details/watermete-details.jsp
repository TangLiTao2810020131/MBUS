<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>水表管理-水表交易明细</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
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
           <%-- <shiro:hasPermission name="冲红">--%>
            <button class="layui-btn" id="chBtn"> &nbsp;&nbsp;冲 红&nbsp;&nbsp;</button>
            <%--</shiro:hasPermission>--%>
        </div>
        <div class="search-btn-group">
            <input type="text" autocomplete="off" class="layui-input layui-input_" placeholder="请输入房间号" id="roomNum" maxlength="20">
            <input type="text" id="startTime" lay-verify="date" placeholder="请选择开始时间" autocomplete="off"
                   class="layui-input layui-input_">
            <input type="text" id="endTime" lay-verify="date" placeholder="请选择结束时间" autocomplete="off"
                   class="layui-input layui-input_">
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
        <input type="hidden" id="level">
        <input type="hidden" id="parentId">
        <div class="layui-inline">
            <label class="layui-form-label" title="操作员">操作员</label>
            <div class="layui-input-inline">
                <select id="operatId">
                    <option value="">全部</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" title="状态">状态</label>
            <div class="layui-input-inline">
                <select id="currentStatus">
                    <option value="">全部</option>
                    <option value="0">下发中</option>
                    <option value="1">操作成功</option>
                    <option value="2">操作失败</option>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="交易类型">交易类型</label>
            <div class="layui-input-inline">
                <select id="transacType">
                    <option value="">全部</option>
                    <option value="1">现金购水</option>
                    <option value="2">一卡通购水</option>
                    <option value="3">房间补水</option>
                    <option value="4">楼层补水</option>
                    <option value="5">按导入补水</option>
                    <option value="6">退水</option>
                    <option value="7">水量清零</option>
                    <option value="8">按导入清零</option>
                    <option value="9">换房补水</option>
                    <option value="10">换房退水</option>
                    <option value="11">交易冲红</option>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn search-class" lay-filter="confirmBtn">查询</button>
            <button type="button" class="layui-btn layui-btn-primary" id="closeBtn">收起</button>
        </div>
    </div>
</form>

</body>
<script type="text/html" id="handle">
  <%-- <shiro:hasPermission name="查看水表交易明细">--%>
    <a title="查看" lay-event="detail" style="cursor:pointer; margin-right:5px;">
        <i class="layui-icon">&#xe63c;</i>
    </a>
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
            type: 'datetime',
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
            type: 'datetime',
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
            , {field: 'serialNumber', title: '流水号',width:120}
            , {field: 'areaName', title: '区域',}
            , {field: 'apartmentName', title: '公寓'}
            , {field: 'floorName', title: '楼层'}
            , {field: 'roomNum', title: '房间号'}
            , {field: 'transacType', title: '交易类型'}
            , {field: 'operatMoney', title: '操作金额'}
            , {field: 'date', title: '操作时间', width:160, templet : "<div>{{layui.util.toDateString(d.operatTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'currentStatusName', title: '操作状态'}
            , {field: 'operatWater', title: '操作水量', templet : function(d) {
                    if (d.operatWater == null){ return '---';}
                    return d.operatWater;
                }}
            , {field: 'operatName', title: '操作员'}
            , {field: 'hasRedrushName', title: '是否冲红'}
            ,{title: '操作',toolbar:'#handle'}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/watermetedetails/listData.action';
        com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        //查看
        table.on('tool(tableBox)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                com.pageOpen("查看水表交易明细","${ctx}/watermetedetails/findWaterMeterDetails.action?id="+data.id,['800px', '550px']);
            }
        });

        $('.search-class').on('click', function(){
            /*查询条件*/
            var tableWhere={
                roomNum: $('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,startTime:$('#startTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,endTime:$('#endTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,operatId:$('#operatId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,currentStatus:$('#currentStatus').val().replace(/(^\s*)|(\s*$)/g, "")
                ,transacType:$('#transacType').val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            var indexMsg = layer.msg('查询中，请稍后...',{icon:16,time:false,shade:0});
            com.reloadTable(tableWhere);
            layer.close(indexMsg);
        });

        function reload(){
            var tableWhere={
                roomNum: $('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,startTime:$('#startTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,endTime:$('#endTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,operatId:$('#operatId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,currentStatus:$('#currentStatus').val().replace(/(^\s*)|(\s*$)/g, "")
                ,transacType:$('#transacType').val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            return tableWhere;
        }

        /*-----按房间补水-----*/
        $("#chBtn").click(function () {
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
            if(checkStatus.data[0].transacType == "现金购水"
                || checkStatus.data[0].transacType == "一卡通购水"
                || checkStatus.data[0].transacType == "清零"
                || checkStatus.data[0].transacType == "按导入清零"
                || checkStatus.data[0].transacType == "交易冲红"){
                parent.layer.open({
                    title: '温馨提示'
                    , content: '只能对各类补水或退水记录进行冲红操作！'
                });
                return;
            }
            if(checkStatus.data[0].hasRedrush == 1){
                parent.layer.open({
                    title: '温馨提示'
                    , content: '该笔记录有冲红记录，不允许再次冲红！'
                });
                return;
            }

            com.checkOpen("冲红", "${ctx}/watermetedetails/redrushIndex.action?id="+checkStatus.data[0].id + "&transacType="+checkStatus.data[0].transacType, ['1000px', '600px'],reload());
            parent.index_  = index;//传给子页面的值index
            parent.id_ = checkStatus.data[0].id;//传给子页面的数据id！！！
        })
    });

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
                roomNum: $('#roomNum').val().replace(/(^\s*)|(\s*$)/g, "")
                ,startTime:$('#startTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,endTime:$('#endTime').val().replace(/(^\s*)|(\s*$)/g, "")
                ,operatId:$('#operatId').val().replace(/(^\s*)|(\s*$)/g, "")
                ,currentStatus:$('#currentStatus').val().replace(/(^\s*)|(\s*$)/g, "")
                ,transacType:$('#transacType').val().replace(/(^\s*)|(\s*$)/g, "")
                ,level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            com.reloadTable(tableWhere);
        });
    });
</script>
</html>