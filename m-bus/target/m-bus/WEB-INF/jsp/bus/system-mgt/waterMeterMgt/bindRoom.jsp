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
    <title>报表查询-用水明细记录</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
    <style>
        .layui-layer-content{
            border: none;
            box-shadow:0px 0px 0px #000 inset;
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
    <input type="hidden" id="level">
    <input type="hidden" id="parentId">
    <div class="top-btn">
        <div class="layui-btn-group">
            <button class="layui-btn" id="bindBtn">确定绑定</button>
        </div>
    </div>
    <div class="tableDiv">
        <table id="layui-table" lay-filter="tableBox" class="layui-table"></table>
    </div>
</div>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript">
    //得到父页面的id值

    var index = (window.parent.index_);
    var id = (window.parent.id_);
    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function () {
        var $ = layui.jquery //重点处
            , layer = layui.layer;



        //执行一个laydate实例
        var laydate = layui.laydate;
        laydate.render({
            elem: '#startTime' //指定元素
        });
        laydate.render({
            elem: '#endTime' //指定元素
        });
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type: 'checkbox'}
            , {field: 'regionName', title: '区域',}
            , {field: 'apartmentName', title: '公寓'}
            , {field: 'floorName', title: '楼栋'}
            , {field: 'layerName', title: '楼层'}
            , {field: 'roomNum', title: '房间号'}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/waterMeterMgt/bindRoomData.action';
        com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！
    })



    layui.config({
        base: '${ctx}/resources/layui_ext/dtree/' //配置 layui 第三方扩展组件存放的基础目录
    }).extend({
        dtree: 'dtree' //定义该组件模块名
    }).use(['element','layer', 'dtree'], function(){
        var layer = layui.layer,
            dtree = layui.dtree,
            $ = layui.$;
        /*----确定绑定-----*/
        $("#bindBtn").click(function(){
            var checkStatus = table.checkStatus('tableId');//test即为基础参数id对应的值
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请勾选一条目标数据！'
                })
                return false ;
            }
            if (checkStatus.data.length != 1) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '只能选择一条目标数据！'
                })
                return false ;
            }
            layer.confirm("请问是否确定绑定!", {
                btn : [ "确定", "取消" ] //按钮
            }, function() {
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/watermeterroominfomgt/bindRoom.action", //路径
                    data : {
                        'id' :checkStatus.data[0].id,
                        'waterMeterId':id
                    }, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    success : function(result) { //返回数据根据结果进行相应的处理
                        layer.msg("绑定成功", {
                            icon : 1,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function() {
                            parent.layer.close(index);//关闭layer
                            $('#iframe',parent.document).attr('src', '${ctx}/waterMeterMgt/waterMeterMgt.action');
                        });
                    }
                });
            });

            return false;
        });

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
                level:$('#level').val()
                ,parentId:$('#parentId').val()
            };
            com.reloadTable(tableWhere);
        });
    });

</script>
</html>