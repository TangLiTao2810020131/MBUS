<%@ taglib prefix="shiri" uri="http://shiro.apache.org/tags" %>
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
  <title>权限管理-职工管理</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx}/resources/css/addClass.css" />
	<link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
	<style type="text/css">
		.demoTable .layui-inline {
			margin-top: 10px;
		}

		.layui-fluid {
			margin-bottom: 10px;
		}
		.layui-input-inline {
			width: 120px;
		}
		.layui-form-label {
			width: 70px;
		}

	</style>

</head>
<body class="layui-layout-body">


<!-- user-list 主体内容 -->
<div class="layui-body">
	<div class="layui-fluid white-bg" style="margin: -5px;">
		<div class="layui-row  layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-body">
						<div class="layui-form layuiadmin-card-header-auto marginBottom">
							<div class="demoTable" style="margin-left: 0px;">

								<div class="layui-inline">
									<label class="layui-form-label">账号</label>
									<div class="layui-input-inline">
										<input type="text" name="workerAccount" id="workerAccount" placeholder="请输入账号" autocomplete="off" class="layui-input" maxlength="20">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label">登录名</label>
									<div class="layui-input-inline">
										<input type="text" name="workerName" id="workerName" placeholder="请输入登录名" autocomplete="off" class="layui-input" maxlength="20">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label">姓名</label>
									<div class="layui-input-inline">
										<input type="text" name="realName" id="realName" placeholder="请输入姓名" autocomplete="off" class="layui-input" maxlength="20">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label">创建时间</label>
									<div class="layui-input-inline">
										<input type="text" name="ctime" id="ctime"
											   placeholder="请输入创建时间" autocomplete="off"
											   class="layui-input" maxlength="20">
									</div>
								</div>
								<div class="layui-inline">
									<button data-type="reload"
											class="layui-btn layuiadmin-btn-list" lay-submit=""
											lay-filter="LAY-app-contlist-search">
										<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
									</button>
								</div>
							</div>
						</div>

						<div class="demoTable marginBottom" id="buttons">
							<div class="layui-btn-group">
								<shiro:hasPermission name="新增职工">
								<button class="layui-btn" data-method="addWorker">
									<i class="layui-icon">&#xe654;</i> 新增
								</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="删除职工">
								<button class="layui-btn" data-method="deleteWorkers">
									<i class="layui-icon">&#xe640;</i> 删除
								</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="关闭职员">
								<button class="layui-btn" data-method="closeWorker">
									<i class="layui-icon">&#x1006;</i> 禁用
								</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="开启职工">
								<button class="layui-btn" data-method="openWorker">
									<i class="layui-icon">&#xe605;</i> 启用
								</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="分配角色">
								<button class="layui-btn" data-method="inRole">
									<i class="layui-icon">&#xe608;</i> 分配角色
								</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="重置密码">
								<button class="layui-btn" data-method="restPassword">
									<i class="layui-icon">&#xe631;</i>重置密码
								</button>
								</shiro:hasPermission>
								<%--<shiro:hasPermission name="楼宇授权">
								<button class="layui-btn" data-method="buildingmgt">
									<i class="layui-icon">&#xe60a;</i>楼宇授权
								</button>
								</shiro:hasPermission>--%>
							</div>
						</div>

						<table class="layui-hide" id="demo" lay-filter="myTable"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script
		src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>

<script type="text/html" id="handle">
	<shiro:hasPermission name="查看职工信息">
	<a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
	</shiro:hasPermission>
	<shiro:hasPermission name="编辑职工信息">
	<a lay-event="edit" title="编辑" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe642;</i></a>
	</shiro:hasPermission>
	<shiro:hasPermission name="删除职工">
	<a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
	</shiro:hasPermission>
</script>


<script>

    //僵硬控制表格高度
    $(function() {
        var tree_height = $(window).height() + 'px';
        $(".layui-fluid ").css("min-height", tree_height);



        $(window).resize(function() {
            var tree_height = $(window).height() + 'px';
            $(".layui-fluid ").css("min-height", tree_height);

        });
    });

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#ctime',
            max:maxDate()
        });
    })
    function maxDate(){
        var now = new Date();
        return now.getFullYear()+"-" + (now.getMonth()+1) + "-" + now.getDate();

    }
    layui.use('element', function() {
        var element = layui.element;
    });

    layui.use('layer', function() {
        var layer = layui.layer;
    });



    layui.use('table', function() {
        var table = layui.table;

        //var $ = layui.jquery, layer = layui.layer;
        //展示已知数据
        table.render({
            elem : '#demo',
            cols : [ [ //标题栏
                {
                    type : 'checkbox'
                }
                , {
                    field : 'workerAccount',
                    title : '职工账号',
                    minWidth : 80,
                    align : 'center'
                }
                , {
                    field : 'workerName',
                    title : '登录名',
                    minWidth : 80,
                    align : 'center'
                }
                , {
                    field : 'realName',
                    title : '职工姓名',
                    minWidth : 80,
                    align : 'center'
                }
               /* , {
                    field : 'identity',
                    title : '身份类型',
                    minWidth : 80,
                    align : 'center',
                    templet : function(d) {
                        if (d.identity == 0) { return "领导";}
                        if(d.identity == 1){ return "管理员";}
                        else {return "员工"; } //
                    }
                }*/
               ,{
                    field : 'roleName',
                    title : '角色名称',
                    minWidth : 80,
                    align : 'center'
                }
                , {
                    field : 'email',
                    title : '邮箱',
                    minWidth : 80,
                    align : 'center'
                }
                , {
                    field : 'ctime',
                    title : '注册时间',
                    minWidth : 80,
                    align : 'center',
                    templet : function(d) {
                        return formatlistdate(d.ctime); //处理时间戳格式
                    }
                }
                , {
                    field : 'isOpen',
                    title : '状态',
                    minWidth : 80,
                    align : 'center',
                    templet : function(d) {
                        if (d.isOpen== 0) {return "启用";}
                        if (d.isOpen== 1) {return "禁用";}
                        else{return "";}
                    }
                }
                , {
                    field : 'handle',
                    title : '操作',
                    minWidth : 80,
                    toolbar : '#handle',
                    align : 'center'
                }
            ] ], //,data: ${list}
            url : '${ctx}/workermgt/listData.action', //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数 //method: 'post' //如果无需自定义HTTP类型，可不加该参数 //,skin: 'line' //表格风格
            id : 'myTable',
            even : true,
            page : true, //是否显示分页
            limits : [ 10, 20, 50 ],
            limit : 15, //每页默认显示的数量
            height : 'full-150'
        });

        var $ = layui.$;
        active = {
            reload : function() {
                var workerAccount = $('#workerAccount');
                var workerName = $('#workerName');
                var realName = $('#realName');
                var identity = $('#identity');
                var ctime = $('#ctime');

                //执行重载
                table.reload('myTable', {
                    page : {
                        curr : 1 //重新从第 1 页开始
                    },
                    where : {
                        workerAccount :workerAccount.val(),
                        workerName : workerName.val(),
                        realName : realName.val(),
                        identity: identity.val(),
                        ctime : ctime.val(),
                    }
                });
            },

            addWorker : function() {
                layer.open({
                    type : 2,
                    title : '新建用户',
                    area : [ '700px', '400px' ],
                    fixed : false, //不固定
                    maxmin : true,
                    content : '${ctx}/workermgt/workerSave.action'
                });
            },
            closeWorker : function() {
                var checkStatus = table.checkStatus('myTable'),
                    data = checkStatus.data;
                if (data.length < 1) {
                    layer.msg('请选择用户！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
                var ids = "";
                for (var i = 0; i < data.length; i++) {
                    ids += data[i].id + ",";
                }
                ids = ids.substring(0, ids.length - 1);
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/workermgt/close.action", //路径
                    data : {
                        "id" : ids
                    }, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    success : function(result) { //返回数据根据结果进行相应的处理
                        layer.msg("禁用职员成功!", {
                            icon : 1,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function() {
                            treload();
                        });
                    }
                });
            },
            openWorker : function() {
                var checkStatus = table.checkStatus('myTable'),
                    data = checkStatus.data;
                if (data.length < 1) {
                    layer.msg('请选择用户！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
                var ids = "";
                for (var i = 0; i < data.length; i++) {
                    ids += data[i].id + ",";
                }
                ids = ids.substring(0, ids.length - 1);
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/workermgt/open.action", //路径
                    data : {
                        "id" : ids
                    }, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    success : function(result) { //返回数据根据结果进行相应的处理
                        layer.msg("启用用户成功!", {
                            icon : 1,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function() {
                            treload();
                        });
                    }
                });
            },
            deleteWorkers : function() {
                var checkStatus = table.checkStatus('myTable'),
                    data = checkStatus.data;
                //layer.alert(data.length);
                //layer.alert(JSON.stringify(data));
                if (data.length < 1) {
                    layer.msg('请选择职员！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
                var ids = "";
                for (var i = 0; i < data.length; i++) {
                    ids += data[i].id + ",";
                }
                ids = ids.substring(0, ids.length - 1);
                layer.confirm("确定删除?删除后不可恢复!", {
                    btn : [ "确定", "取消" ] //按钮
                }, function(index) {
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/workermgt/delete.action", //路径
                        data : {
                            "id" : ids
                        }, //数据，这里使用的是Json格式进行传输
                        dataType : "json",
                        success : function(result) { //返回数据根据结果进行相应的处理
                            layer.msg("删除成功", {
                                icon : 1,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function() {
                                treload();
                            });
                        }
                    });
                });
            },
            inRole : function() {
                var checkStatus = table.checkStatus('myTable'),
                    data = checkStatus.data;
                if (data.length < 1) {
                    layer.msg('请选择用户！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
                if (data.length != 1) {
                    layer.msg('只能选择一个用户进行操作！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }

                layer.open({
                    type : 2,
                    title : '分配角色',
                    area : [ '500px', '350px' ],
                    fixed : false, //不固定
                    maxmin : true,
                    content : '${ctx}/workermgt/inRole.action?id=' + data[0].id
                });
            },
            restPassword : function() {
                var checkStatus = table.checkStatus('myTable'),
                    data = checkStatus.data;
                if (data.length < 1) {
                    layer.msg('请选择用户！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
                var ids = "";
                for (var i = 0; i < data.length; i++) {
                    ids += data[i].id + ",";
                }
                ids = ids.substring(0, ids.length - 1);
                layer.confirm("重置密码后需要重新登录，是否确定重置密码!", {
                    btn : [ "确定", "取消" ] //按钮
                }, function(index) {
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/workermgt/restPassword.action", //路径
                        data : {
                            "id" : ids
                        }, //数据，这里使用的是Json格式进行传输
                        dataType : "json",
                        success : function(result) { //返回数据根据结果进行相应的处理
                            layer.msg("重置密码成功!", {
                                icon : 1,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function() {
                                treload();
                            });

                        }
                    });
                });
            },
            buildingmgt : function() {
                var checkStatus = table.checkStatus('myTable'),
                    data = checkStatus.data;
                if (data.length < 1) {
                    layer.msg('请选择用户！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
                if (data.length != 1) {
                    layer.msg('只能选择一个用户进行操作！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }

                layer.open({
                    type : 2,
                    title : '楼宇授权',
                    area : [ '700px', '375px' ],
                    fixed : false, //不固定
                    maxmin : true,
                    content : '${ctx}/buildingmgt/buildingMgt.action?id=' + data[0].id
                });
            }
        };

        //监听工具条
        table.on('tool(myTable)', function(obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                layer.open({
                    type : 2,
                    title : '用户详情',
                    area : [ '700px', '450px' ],
                    fixed : false, //不固定
                    maxmin : true,
                    content : '${ctx}/workermgt/info.action?id=' + data.id
                });
            } else if (obj.event === 'del') {
                layer.confirm("确定删除?删除后不可恢复!", {
                    btn : [ "确定", "取消" ] //按钮
                }, function(index) {
                    //$.post("${ctx}/user/delete.action?id=" + data.id);
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/workermgt/delete.action", //路径
                        data : {
                            "id" : data.id
                        }, //数据，这里使用的是Json格式进行传输
                        dataType : "json",
                        success : function(result) { //返回数据根据结果进行相应的处理
                            layer.msg("删除成功", {
                                icon : 1,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function() {
                                treload();
                            });

                        }
                    });
                });

            } else if (obj.event === 'edit') {
                layer.open({
                    type : 2,
                    title : '编辑用户',
                    area : [ '700px', '400px' ],
                    fixed : false, //不固定
                    maxmin : true,
                    content : '${ctx}/workermgt/workerEdit.action?id=' + data.id
                });


            }
        });

        $('.demoTable .layui-btn').on('click', function() {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        $('#buttons .layui-btn').on('click', function() {
            var othis = $(this),
                method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
    });

    function treload() {
        var table = layui.table;
        table.reload('myTable');
    }
</script>
</body>
</html>