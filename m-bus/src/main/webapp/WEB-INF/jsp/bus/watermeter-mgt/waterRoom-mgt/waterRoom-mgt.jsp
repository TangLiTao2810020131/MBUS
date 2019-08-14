<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>水表管理-控水换房管理</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
    <style>
        .layui-layer-content{
            border: none;
            box-shadow:0px 0px 0px #000 inset;
        }
        .right-layer [type="text"]{
            border:none !important;
        }
        [title]{
            width:100px !important;
        }
    </style>
</head>
<body class="inner-body" style="background: #f2f2f2; position: relative;">
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
<div class="tableHtml" style="margin-left: 210px; height: 100%; background: #FFFFFF;">
    <div class="top-btn">
        <div class="layui-btn-group" style="position: relative;">
            <%--<shiro:hasPermission name="设为原房间">--%>
            <button class="layui-btn" id="old-roomBtn">
                设为原房间
            </button>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="设为新房间">--%>
            <button class="layui-btn" id="new-roomBtn">
                设为新房间
            </button>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="查看房间信息">--%>
            <button class="layui-btn" id="look-roomBtn">
                查看房间信息
            </button>
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
        <table id="layui-table" lay-filter="tableBox" class="layui-table">
        </table>
    </div>
</div>
<!-----------------默认表格结束------------>
<!--高级查询-->
<form class="layui-form" action="" id="formHtml" lay-filter="updForm">
    <div class="layui-form-item">
        <input type="hidden" id="level">
        <input type="hidden" id="parentId">
        <div class="layui-inline">
            <label class="layui-form-label" title="房间类型">
                房间类型</label>
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
            <label class="layui-form-label" title="公寓">
                公寓</label>
            <div class="layui-input-inline">
                <input type="text" id="apartmentId" autocomplete="off"
                       class="layui-input" maxlength="20">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="楼层">
                楼层</label>
            <div class="layui-input-inline">
                <input type="text" id="floorId" placeholder="请输入" autocomplete="off"
                       class="layui-input" maxlength="20">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" title="房间号">
                房间号</label>
            <div class="layui-input-inline">
                <input type="text" id="roomNum" placeholder="请输入" autocomplete="off"
                       class="layui-input" maxlength="20" onchange="fangjian()">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="集中器编号">
                集中器编号</label>
            <div class="layui-input-inline">
                <input type="text" id="concentratorNum" placeholder="请输入" autocomplete="off"
                       class="layui-input" maxlength="20">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn search-class" lay-submit="" lay-filter="confirmBtn">
                查询
            </button>
            <button type="button" class="layui-btn layui-btn-primary" id="closeBtn">
                收起
            </button>
        </div>
    </div>
</form>
<!--右边弹出层   开始------->
<div class="right-layer" style="display: none;">
    <!------原房间信息----->
    <div class="layui-form oldroom-info">
        <h3 style="padding: 5px 0 5px 20px; font-weight: bold;">原房间信息</h3>
        <a href="javascript:void(0)" class="right-layer-shrink" title="收缩房间信息"><i style="font-size: 18px;
                font-weight: bold;" class="layui-icon layui-icon-spread-left"></i></a>
        <input type="hidden" id="old_room_id">
        <input type="hidden" id="old_type_id">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" title="公寓名称">
                    公寓名称</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_apartmentName" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="楼层">
                    楼层</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_floorName" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="房间编号">
                    房间编号</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_roomNum" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="房间类型">
                    房间类型</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_roomTypeName" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="集中器IP">
                    集中器编号</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_concentratorIp" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="持卡购水量">
                    持卡购水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_cardBuyWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="总补水量">
                    总补水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_supplementWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="总退水量">
                    总退水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_returnWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="总用水量">
                    总用水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_userWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="现金购水量">
                    现金购水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_cashBuyWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="剩余水量">
                    剩余水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="o_surplusWater" class="layui-input" readonly="readonly">
                </div>
            </div>
        </div>
    </div>
    <!--新房间信息-->
    <div class="layui-form newroom-info">
        <h3 style="padding: 0px 0 5px 20px; font-weight: bold;">新房间信息</h3>
        <a href="javascript:void(0)" class="right-layer-shrink" title="收缩房间信息"><i style="font-size: 18px;
                font-weight: bold;" class="layui-icon layui-icon-spread-left"></i></a>
        <input type="hidden" id="new_room_id">
        <input type="hidden" id="new_type_id">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" title="公寓名称">
                    公寓名称</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_apartmentName" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="楼层">
                    楼层</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_floorName" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="房间编号">
                    房间编号</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_roomNum" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="房间类型">
                    房间类型</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_roomTypeName" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="集中器IP">
                    集中器编号</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_concentratorIp" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="持卡购水量">
                    持卡购水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_cardBuyWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="总补水量">
                    总补水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_supplementWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="总退水量">
                    总退水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_returnWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="总用水量">
                    总用水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_userWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="现金购水量">
                    现金购水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_cashBuyWater" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" title="剩余水量">
                    剩余水量</label>
                <div class="layui-input-inline">
                    <input type="text" id="n_surplusWater" class="layui-input" readonly="readonly">
                </div>
            </div>
        </div>
    </div>
    <button class="layui-btn" id="subBtn" style="position: relative; left: 50%; transform: translateX(-50%);">
        确认更换
    </button>
</div>
<!--右边弹出层   结束------->
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript" src="${ctx}/resources/water_meter_manage/waterMeterManage.js"></script>
<script type="text/javascript">
    //主动加载jquery模块
    var $ = layui.jquery //重点处
    var bol = 0;
    layui.use(['jquery', 'layer'], function () {
        layer = layui.layer;
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type: 'checkbox'}
            ,{field: 'areaName', title: '区域', }
            ,{field: 'apartmentName', title: '公寓'}
            ,{field: 'floorName', title: '楼层'}
            ,{field: 'roomNum', title: '房间号'}
            // ,{field: 'initStatusName', title: '初始化状态'}
            // ,{field: 'valveStatusName', title: '阀门状态'}
            // ,{field: 'moduleStatusName', title: '模块状态'}
            // ,{field: 'surplusWater', title: '剩余水量'}
            ,{field: 'buyWaterTotal', title: '购水量'}
            ,{field: 'supplementWater', title: '补水量'}
            ,{field: 'overWater', title: '透水量'}
            // ,{field: 'cashBuyWater', title: '现金购水量'}
            // ,{field: 'returnWater', title: '退水量'}
            ,{field: 'userWater', title: '总用水量'}
        ]];
        var limitArr = [10, 20, 30];
        var urls = '${ctx}/waterroommgt/listData.action';
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

        /*-----设为原房间-----*/
        $("#old-roomBtn").click(function () {
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
            var checkId = parent.id_ = checkStatus.data[0].id;
            var checkTypeId = parent.id_ = checkStatus.data[0].roomTypeId;
            var newId = $("#new_room_id").val();
            var typeId = $("#new_type_id").val();
            if(newId && newId == checkId){
                layer.msg("该房间已被选择为新房间！");
                return;
            }
            if(typeId && typeId != checkTypeId){
                layer.msg("该房间类型与新房间类型不同！");
                return;
            }
            $.ajax({
                type: "POST",
                async:false,
                data: {'id':checkId},
                url: '${ctx}/waterroommgt/getRoomInfo.action',
                success: function(data){
                   if(data.status == "fail"){
                       layer.msg(data.msg);
                   }else{
                       $("#old_room_id").val(data.body.id);
                       $("#old_type_id").val(data.body.roomTypeId);
                       $("#o_apartmentName").val(data.body.apartmentName);
                       $("#o_floorName").val(data.body.floorName);
                       $("#o_roomNum").val(data.body.roomNum);
                       $("#o_roomTypeName").val(data.body.roomTypeName);
                       $("#o_concentratorIp").val(data.body.concentratorNum);
                       $("#o_cardBuyWater").val(data.body.cardBuyWater);
                       $("#o_supplementWater").val(data.body.supplementWater);
                       $("#o_returnWater").val(data.body.returnWater);
                       $("#o_userWater").val(data.body.userWater);
                       $("#o_cashBuyWater").val(data.body.cashBuyWater);
                       $("#o_surplusWater").val(data.body.surplusWater);
                   }
                }
            });
            checkBtn();
        })

        /*-----设为新房间-----*/
        $("#new-roomBtn").click(function () {
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
            var checkId = parent.id_ = checkStatus.data[0].id;
            var checkTypeId = parent.id_ = checkStatus.data[0].roomTypeId;
            var oldId = $("#old_room_id").val();
            var oldTypeId = $("#old_type_id").val();
            if(oldId && oldId == checkId){
                layer.msg("该房间已被选择为原房间！");
                return;
            }
            if(oldTypeId && oldTypeId != checkTypeId){
                layer.msg("该房间类型与新房间类型不同！");
                return;
            }

            $.ajax({
                type: "POST",
                async:false,
                data: {'id':checkId},
                url: '${ctx}/waterroommgt/getRoomInfo.action',
                success: function(data){
                    if(data.status == "fail"){
                        layer.msg(data.msg);
                    }else{
                        $("#new_room_id").val(data.body.id);
                        $("#new_type_id").val(data.body.roomTypeId);
                        $("#n_apartmentName").val(data.body.apartmentName);
                        $("#n_floorName").val(data.body.floorName);
                        $("#n_roomNum").val(data.body.roomNum);
                        $("#n_roomTypeName").val(data.body.roomTypeName);
                        $("#n_concentratorIp").val(data.body.concentratorNum);
                        $("#n_cardBuyWater").val(data.body.cardBuyWater);
                        $("#n_supplementWater").val(data.body.supplementWater);
                        $("#n_returnWater").val(data.body.returnWater);
                        $("#n_userWater").val(data.body.userWater);
                        $("#n_cashBuyWater").val(data.body.cashBuyWater);
                        $("#n_surplusWater").val(data.body.surplusWater);
                    }
                }
            });
            checkBtn();
        })
        /*-----查看房间信息-----*/


        $("#look-roomBtn").click(function () {	//点击查看房间信息按钮
            if (!bol) {
                $(this).text("关闭房间信息");
                bol = 1;
            }else {
                $(this).text("查看房间信息");
                bol = 0;
            }
            $(".right-layer").animate({width: 'toggle'}, 300);
        })
        $(".right-layer-shrink").click(function () {	//点击收缩图标
            $("#look-roomBtn").text("查看房间信息");
            bol = 0;
            $(".right-layer").animate({width: 'toggle'}, 300);
        })

        $("#subBtn").click(function () {
            var newRoomId = $("#new_room_id").val();
            var oldRoomId = $("#old_room_id").val();
            if(!oldRoomId){
                layer.msg("请选择原房间信息！");
                return;
            }
            if(!newRoomId){
                layer.msg("请选择新房间信息！");
                return;
            }
            layer.confirm("确认控水换房吗？", {btn: ['确定', '取消'], title: "温馨提示"}, function () {
                var indexMsg = layer.msg('控水换房提交中，请稍后...',{icon:16,time:false,shade:0});
                $.ajax({
                    type: "POST",
                    async:false,
                    data: {"newRoomId":newRoomId,"oldRoomId":oldRoomId},
                    url: '${ctx}/waterroommgt/roomConfirm.action',
                    success: function(data){
                        layer.msg(data.msg);
                        if(data.status == "success"){
                            $("#subBtn").attr("disabled",true);
                            setTimeout("flutable()",2500);
                            layer.close(indexMsg);
                        }
                    }
                });
            });
        });
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
                roomTypeId: ''
                , apartmentId:''
                , floorId: ''
                , roomNum: ''
                , roomNumOut: ''
                ,concentratorNum:''
                ,level:param.level
                ,parentId:param.parentId
            };
            com.reloadTable(tableWhere);
        });
    });

    function checkBtn() {
        if (!bol) {
            $("#look-roomBtn").text("关闭房间信息");
            bol = 1;
            $(".right-layer").animate({width: 'toggle'}, 300);
        }
    }
</script>
</html>
