<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>职员登录日志</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <style type="text/css">
		.demoTable .layui-inline{margin-top:10px;}
		.layui-fluid{margin-bottom:10px;}
	</style>
</head>

<body class="inner-body" style="background:#f2f2f2;position: relative; ">
<div style="border-top: 1px solid #e6e6e6;"></div>

<div class="tableHtml" style="height: 100%;background: #FFFFFF;">
    <div class="top-btn" id="buttons">
        <div class="layui-btn-group" style="position: relative;height: 36px">
            <%--<button class="layui-btn" id="exportBtn" style="display: block"></button>--%>
        </div>
        <div class="search-btn-group">
            <input type="text" name="workerAccount" id="workerAccount" placeholder="请输入登录用户账号" autocomplete="off" class="layui-input" maxlength="20">
            <input type="text" name="workerName" id="workerName" placeholder="请输入登录用户名" autocomplete="off" class="layui-input" maxlength="20">
            <input type="text" name="loginTime" id="loginTime" placeholder="请输入登录时间" autocomplete="off" class="layui-input" maxlength="20" >
            <%--<button class="layui-btn layui-btns" id="find">查询</button>--%>
            <button data-type="reload"
                    class="layui-btn" lay-submit="">
                查询
            </button>

        </div>
        <div>
            <table class="layui-hide" id="demo" lay-filter="myTable"></table>
        </div>

    </div>

</div>
<%--<body class="layui-layout-body" >


<div class="layui-body">
    <div class="layui-fluid white-bg">
        <div class="layui-row  layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-body">
                        <div class="layui-form layuiadmin-card-header-auto marginBottom">
                            <div class="demoTable">
                                <div class="layui-inline" >
                                    <label class="layui-form-label">职员账号</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="workerAccount" id="workerAccount" placeholder="请输入登录用户账号" autocomplete="off" class="layui-input" maxlength="20">
                                    </div>
                                </div>


                                <div class="layui-inline">
                                    <label class="layui-form-label">职员登录名</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="workerName" id="workerName" placeholder="请输入登录用户名" autocomplete="off" class="layui-input" maxlength="20">
                                    </div>
                                </div>


                                <div class="layui-inline">
                                    <label class="layui-form-label">登录时间</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="loginTime" id="loginTime" placeholder="请输入登录时间" autocomplete="off" class="layui-input" maxlength="20" >
                                    </div>
                                </div>

                                <div class="layui-inline">
                                    <button data-type="reload" class="layui-btn layuiadmin-btn-list" lay-submit="" lay-filter="LAY-app-contlist-search">
                                        &lt;%&ndash;<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>&ndash;%&gt;
                                    搜索
                                    </button>
                                </div>
                            </div>
                        </div>

                        <table class="layui-hide" id="demo" lay-filter="myTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>--%>


<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->


<script type="text/html" id="handle">
    <a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>

<script>

    <%--
         由于layui table组件没有自适应功能（不能根据屏幕变化显示表格数据),
         因此根据原生的javascript为表格组件添加浏览器窗口大小改变事件（onresize)
    --%>
    var clientHeight,clientWidth,avg;
    window.onresize=tableData;
    window.onload=tableData;
    function tableData() {
        clientHeight=window.innerHeight;//获取浏览器的可用高度
        clientWidth=window.innerWidth;//获取浏览器的宽度
        avg=(clientWidth-100)/5;//分割6等分，表示表格的六列
        layui.use('table', function(){
            var table = layui.table;

            //var $ = layui.jquery, layer = layui.layer;
            //展示已知数据
            table.render({
                elem: '#demo'
                ,cols: [[ //标题栏
                    {field: 'workerAccount', title: '登录用户账号', minWidth:120,unresize:true,align:'center'},
                    {field: 'workerName', title: '登录用户名', minWidth:120,unresize:true,align:'center'},
                    {field: 'ipaddress', title: '登录IP', minWidth:120,unresize:true,align:'center'}
                    ,{field: 'loginTime', title: '登录时间', minWidth:120,unresize:true,align:'center'}
                    ,{field: 'loginState', title: '登录状态',minWidth:120,unresize:true,align:'center'}
                    // ,{field: 'isclose', title: '操作', width:avg,toolbar:'#handle',unresize:true,align:'center'}
                ]]
                //,data: ${list}
                ,url: '${ctx}/loginLog/listData.action'
                ,id:'myTable'
                ,even: false
                ,page: true //是否显示分页
                ,limits: [15,20,50]
                ,limit:15 //每页默认显示的数量
                ,height:clientHeight-100
                ,width:clientWidth
            });

            var $ = layui.$;
            active = {
                reload: function(){
                    var workerAccount = $('#workerAccount');
                    var workerName = $('#workerName');
                    var loginTime = $('#loginTime');



                    //执行重载
                    table.reload('myTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            workerAccount:workerAccount.val(),
                            workerName: workerName.val()
                            ,loginTime:loginTime.val()

                        }
                    });
                }
            };

            //监听工具条
            table.on('tool(myTable)', function(obj){
                var data = obj.data;
                console.log(data)
                if(obj.event === 'detail'){
                    layer.open({
                        type: 2,
                        area: ['700px', '450px'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: '${ctx}/loginLog/info.action?id='+data.id
                    });
                }
            });

            $('.search-btn-group .layui-btn').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            $('#buttons .layui-btn').on('click', function(){
                var othis = $(this), method = othis.data('method');
                active[method] ? active[method].call(this, othis) : '';
            });
        });
    };
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        var date=new Date();
         //时间控件
         laydate.render({
             elem: '#loginTime',
             max:''+date
         });
    })
	layui.use('element', function() {
		var element = layui.element;
	});

</script>

<script>
//僵硬控制表格高度
$(function(){
	var tree_height = $(window).height()+'px';
	$(".layui-fluid ").css("min-height",tree_height);
	
	
	
	$(window).resize(function() {
		var tree_height = $(window).height()+'px';
		$(".layui-fluid ").css("min-height",tree_height);
		
	});
})

</script>
</body>
</html>