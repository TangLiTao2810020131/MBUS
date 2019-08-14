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
<title>系统管理-区域-列表</title>
<link rel="stylesheet" href="${ctx}/resources/css/layui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/common.css" />
	<style type="text/css">
		.layui-layer-content {
			border: none;
			box-shadow: 0px 0px 0px #000 inset;
		}
	</style>
</head>
<body class="inner-body layui-fluid" style="background:#f2f2f2;position: relative; ">

	<!-------默认表格开始----------->

	<div class="tableHtml " style="height: 100%;background: #FFFFFF;">
		<div class="top-btn">
			<div class="layui-btn-group">
				<%--<shiro:hasPermission name="新增区域信息">--%>
				<button class="layui-btn" id="addBtn">添加</button>
				<%--</shiro:hasPermission>--%>
				<%--<shiro:hasPermission name="编辑区域信息">--%>
				<button class="layui-btn" id="editBtn">编辑</button>
			<%--	</shiro:hasPermission>--%>
				<%--<shiro:hasPermission name="批量删除区域信息">--%>
				<button class="layui-btn" id="delBtn">删除</button>
				<%--</shiro:hasPermission>--%>
			</div>
			<div class="search-btn-group">
				区域编号
				<input type="text" autocomplete="off" class="layui-input layui-input_" id="code" placeholder="请输入区域编码" style="width:150px;" maxlength="20">
				区域名称:
				<input type="text" autocomplete="off" class="layui-input layui-input_" id="name" placeholder="请输入区域名称" style="width:150px;" maxlength="20">
                <a class="layui-btn search_btn "  id="searchBtn"  data-type="reload">查询</a>
			</div>
		</div>
		<div class="tableDiv">
			<table id="layui-table" lay-filter="tableBox" class="layui-table"></table>
		</div>
	</div>
	<!-----------------默认表格结束------------>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script type="text/html" id="handle">
	<%--<shiro:hasPermission name="查看区域信息">--%>
  	<a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
	<%--</shiro:hasPermission>--%>
   <%--<shiro:hasPermission name="删除区域信息">--%>
   	<a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
   <%--</shiro:hasPermission>--%>
</script>
<script type="text/javascript">
    //僵硬控制表格高度
   /* $(function() {
        var tree_height = $(window).height() + 'px';
        $(".layui-fluid ").css("min-height", tree_height);



        $(window).resize(function() {
            var tree_height = $(window).height() + 'px';
            $(".layui-fluid ").css("min-height", tree_height);

        });
    });*/



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
				title : '区域编号',
			}
			, {
				field : 'name',
				title : '区域名称'
			}
			// , {
			// 	field : 'remark',
			// 	title : '备注'
			// }
			, {
				toolbar : '#handle',
				title : '操作'
			}
		] ];

		var limitArr = [ 10, 20, 30 ];
		var urls = '${ctx}/region/listData.action';
		com.tableRender('#layui-table', 'tableId', 'full-50', limitArr, urls, arr);//加载表格,注意tableId是自定义的，在表格重载需要！！
        /*-----查询-----*/
        $('#searchBtn').on('click',function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
                 });
      active = {
             reload: function () {
                        var code=$('#code').val();
                        var name=$('#name').val();
                 if(code.length>20){
                     layer.msg('区域编码请输入小于20位的字符！', {
                         icon: 7,
                         time: 2000 //2秒关闭（如果不配置，默认是3秒）
                     }, function () {
                         $("#code").focus();
                     });
                     return false;

                 }
                 if(name.length>20){
                     layer.msg('区域名称请输入小于20位的字符！', {
                         icon: 7,
                         time: 2000 //2秒关闭（如果不配置，默认是3秒）
                     }, function () {
                         $("#name").focus();
                     });
                     return false;

                 }
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
			com.pageOpen("添加区域", "${ctx}/region/add.action", [ '700px', '350px' ]);
		});
		/*-----编辑-----*/
		$("#editBtn").click(function() {
			com.checkOpen("编辑区域", "${ctx}/region/edit.action", [ '700px', '350px' ]);
		});
        /*----删除之前检测该区域下的房间是否绑定了水表-------*/
        function isCheckRegionWaterMeter(ids){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/watermeterroominfomgt/isCheckRegionWaterMeter.action", //路径
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
                    var num =isCheckRegionWaterMeter(ids);
                    if(num != 0){
                        parent.layer.open({
                            title: '温馨提示'
                            , content: '该区域下有绑定的水表，不能删除！'
                        })
                        return false ;
					}
                    $.ajax({
                        type : "POST", //提交方式
							url : "${ctx}/region/delete.action", //路径
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
					title : '区域详情',
					type : 2,
					area : [ '550px', '350px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/region/detail.action?id=' + data.id
				});
			} else if (obj.event === 'del') {
				layer.confirm("确定删除?删除后不可恢复!", {
					btn : [ "确定", "取消" ] //按钮
				}, function(index) {
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/region/delete.action", //路径
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
	}
</script>
</html>