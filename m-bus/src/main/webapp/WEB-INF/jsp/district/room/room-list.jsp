<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8">
<title>系统管理-房间-列表</title>
<link rel="stylesheet" href="${ctx}/resources/css/layui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/common.css" />
	<style>
		.layui-layer-content{
			border: none;
			box-shadow:0px 0px 0px #000 inset;
		}
	</style>

</head>
<body class="inner-body" style="background:#f2f2f2;position: relative; ">
	<div style="border-top: 1px solid #e6e6e6;"></div>

	<!-------默认表格开始----------->

	<div class="tableHtml" style="height: 100%;background: #FFFFFF;">
		<div class="top-btn">
			<div class="layui-btn-group">
				<%--<shiro:hasPermission name="新增房间信息">--%>
				<button class="layui-btn" id="addBtn">添加</button>
				<%--</shiro:hasPermission>--%>
				<%--<shiro:hasPermission name="编辑房间信息">--%>
				<button class="layui-btn" id="editBtn">编辑</button><%--</shiro:hasPermission>--%>
			<%--	<shiro:hasPermission name="批量删除房间信息">--%>
				<button class="layui-btn" id="delBtn">删除</button>
				<%--</shiro:hasPermission>--%>
			</div>
			<div class="search-btn-group">

				房间编号:<input type="text" autocomplete="off" class="layui-input layui-input_" id="code" placeholder="请输入房间编号" style="width:150px;" maxlength="20">
				房间号:<input type="text" autocomplete="off" class="layui-input layui-input_" id="name" placeholder="请输入房间号" style="width:150px;" maxlength="20">

				<!--<input type="text" autocomplete="off"-->
				<!--class="layui-input layui-input_" placeholder="请输入">-->
				<a class="layui-btn search_btn "  id="searchBtn"  data-type="reload">查询</a>

				<!--<button class="layui-btn layui-btns layui-bg-orange" id="superBtn">高级查询</button>-->

			</div>
		</div>
		<div class="tableDiv">
			<table id="layui-table" lay-filter="tableBox" class="layui-table"></table>
		</div>
	</div>
	<!-----------------默认表格结束------------>
	<!--高级查询-->
	<!--<form class="layui-form" action="" id="formHtml" lay-filter="updForm">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label" title="房间类型">房间类型</label>
				<div class="layui-input-inline">
					<select name="">
						<option value="0">全部</option>
						<option value="1">学生公寓</option>
						<option value="2">教师公寓</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" title="公寓">公寓</label>
				<div class="layui-input-inline">
					<input type="text" name="" placeholder="请输入" lay-verify="required"
						autocomplete="off" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label" title="楼层">楼层</label>
				<div class="layui-input-inline">
					<input type="text" name="" placeholder="请输入" lay-verify="required"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" title="房间号">房间号</label>
				<div class="layui-input-inline">
					<input type="text" name="" placeholder="请输入" lay-verify="required"
						autocomplete="off" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button type="button" class="layui-btn" lay-submit=""
					lay-filter="confirmBtn">查询</button>
				<button type="button" class="layui-btn layui-btn-primary"
					id="closeBtn">收起</button>
			</div>
		</div>
	</form>-->
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/html" id="handle">
	<%--<shiro:hasPermission name="查看房间管理">--%>
	<a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
	<%--</shiro:hasPermission>--%>
  <%-- <shiro:hasPermission name="删除房间信息">--%>
	<a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
   <%--</shiro:hasPermission>--%>
</script>

<script type="text/javascript">
	//主动加载jquery模块
	layui.use([ 'jquery', 'layer' ], function() {
		var $ = layui.jquery, //重点处
			layer = layui.layer;
		table = layui.table; //表格，注意此处table必须是全局变量
		var arr = [ [
			{
				type : 'checkbox'
			}
			, {
				field : 'code',
				title : '房间编号',
			}
			, {
				field : 'name',
				title : '房间号'
			}
            , {
                field : 'roomTypeName',
                title : '房间类型'
                ,templet : function(d) {
                    if (d.roomTypeName == null) { return "---";}
                    return d.roomTypeName;
                }
            }
			, {
			field : 'water_meter_id',
			title : '绑定水表',templet : function(d) {
            if (d.water_meter_id == null) { return "未绑定水表";}
            return d.water_meter_id;
        }
			 }
            , {
                toolbar : '#handle',
                title : '操作'
            }
			
		] ];
		
		var limitArr = [ 10, 20, 30 ];
		var urls = '${ctx}/room/listData.action?parentid=${parentid}';
		com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr); //加载表格,注意tableId是自定义的，在表格重载需要！！
		/*---查询---*/
        $('#searchBtn').on('click',function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        active = {
            reload: function () {
                var code=$('#code').val();
                var name=$('#name').val();
                var index = layer.msg('查询中，请稍候...',{icon: 16,time:false,shade:0});
                setTimeout(function(){
                    table.reload('tableId', {
                        where: {
                            'code':code,
                            'name':name
                        }
                    });
                    layer.close(index);
                },800);
            }
        };

		/*-----新增-----*/
		$("#addBtn").click(function() {
			com.pageOpen("新增", "${ctx}/room/add.action?parentid=${parentid}", [ '700px', '380px' ]);
		});
		/*-----编辑-----*/
		$("#editBtn").click(function() {
			com.checkOpen("编辑", "${ctx}/room/edit.action", [ '700px', '380px' ]);
		});
        /*----删除之前检测该公寓下的房间是否绑定了水表-------*/
        function isCheckRoomWaterMeter(ids){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/watermeterroominfomgt/isCheckRoomWaterMeter.action", //路径
                data : {'ids':ids}, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        }
        /*-----删除-----*/
        $("#delBtn").click(function(){
            var checkStatus = table.checkStatus('tableId');//test即为基础参数id对应的值
            //数据只能是一条
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请勾选一条目标数据！'
                })
				return false ;
            }
            var ids = "";
            for (var i = 0; i < checkStatus.data.length; i++) {
                ids += checkStatus.data[i].id + ",";
            }
            ids = ids.substring(0, ids.length - 1);
            layer.confirm("确定删除?删除后不可恢复!", {
                btn : [ "确定", "取消" ] //按钮
            }, function(index) {
                var num =isCheckRoomWaterMeter(ids);
                if(num != 0){
                    parent.layer.open({
                        title: '温馨提示'
                        , content: '所选房间下有绑定的水表，请检查后删除！'
                    })
                    return false ;
                }
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/room/delete.action", //路径
                    data : {
                        "id" : ids
                    }, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    success : function(result) { //返回数据根据结果进行相应的处理
                        layer.msg("删除成功", {
                            icon : 1,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function() {
                            aa();
                        });
                    }
                });
            });

            return false;
        })
        //监听操作栏按钮
        table.on('tool(tableBox)', function(obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                layer.open({
                    title : '房间详情',
                    type : 2,
                    area : [ '550px', '350px' ],
                    fixed : false, //不固定
                    maxmin : true,
                    content : '${ctx}/room/detail.action?id=' + data.id+'&parentId='+'${parentid}'
                });
            } else if (obj.event === 'del') {
                layer.confirm("确定删除?删除后不可恢复!", {
                    btn : [ "确定", "取消" ] //按钮
                }, function(index) {
                    var num =isCheckRoomWaterMeter(data .id);
                    if(num != 0){
                        parent.layer.open({
                            title: '温馨提示'
                            , content: '所选房间下有绑定的水表，请检查后删除！'
                        })
                        return false ;
                    }
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/room/delete.action", //路径
                        data : {
                            "id" : data .id
                        }, //数据，这里使用的是Json格式进行传输
                        dataType : "json",
                        success : function(result) { //返回数据根据结果进行相应的处理
                            layer.msg("删除成功", {
                                icon : 1,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function() {
                                aa();
                            });
                        }
                    });
                });
            }
        });



	});
    function aa(){
        var table = layui.table;
        table.reload('tableId');
    }</script>
</html>