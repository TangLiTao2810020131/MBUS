<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>智能用水管理系统</title>
<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
  <link rel="shortcut  icon" type="${ctx}/resources/image/x-icon" href="${ctx}/resources/images/etsIco.ico" media="screen" />
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
<style type="text/css">
* {
	padding: 0;
	margin: 0;
}
.logo_img{
	position: relative;
	height: 30px;
	width: 30px;
	top: 13px;
	left: 22px;
	border-radius: 50%;

}
.logo_img img{
	position: absolute;
	height:100%;
	width: 100%;
	top: 0;
	left: 8px;

}

#addEmployeeForm input {

	margin-top: 25px;
	width: 350px;
}
.nav-bar{
	background: #FFFFFF;
	position: fixed;
	top: 50px;
	width: 100%;
	
}
.nav-bar span{
	display: inline-block;
	padding: 12.5px 0;
	color: #666;
    font-style: normal;
    font-size: 15px;
}
/*侧边导航栏  图标*/
.layui-side .layui-icon,.layui-layout-left .layui-icon{
	font-size: 16px;
    margin-left: -6px;
    width: 20px;
    display: inline-block;
}
.layui-nav-tree .layui-nav-item a {
	transition: none;
}

/*主题颜色覆盖*/
#layui-header .layui-nav-item>a{
	color: #FFFFFF;
}
#layui-header .layui-nav .layui-nav-more{
	border-top-color: #FFFFFF;
}
#layui-header .layui-nav .layui-nav-item a:hover{
	color: #E2E2E2;
}
#layui-header #upd:hover{
	background: #428BCA;
}
#layui-header .layui-nav-item:hover span{margin-top:-9px; border-style: dashed; border-color: transparent; border-bottom-style: solid;border-bottom-color: #CCCCCC; }
#layui-header .layui-nav-mored{
	margin-top: -3px;
	border-top-color: #FFFFFF;
}
.layui-nav-itemed .layui-nav-child dd:hover{
	background: #FFFFFF;
}
.layui-nav .layui-nav-item {
	line-height: 50px;
}
#fold-btn{
	display: inline-block;
	cursor: pointer;
/*	font-size: 20px;*/
	/*color: #ffffff;
	padding: 13px 15px;
	position: relative;
    z-index: 999999;*/
 
}
.layui-layout-admin .layui-body{
	top: 5px;
}
#layui-header .layui-nav-child{
	top: 50px;
}
.layui-nav .layui-this:after{
	width: 0 !important;
}
.marginL-15{
	padding-left: 35px !important;
}
/*body .layer-ext-fo .layui-layer-title{
    background-color: #0000FF;
    color: red;
}*/
</style>
</head>
<body class="layui-layout-body" id="outbody">
	<!--test  修改密码html-->
	<div class="layui-row" id="test" style="display: none;">

		<form class="layui-form" id="addEmployeeForm" lay-filter="updPwd">
			<div class="layui-form-item">
				<label class="layui-form-label">原密码：</label>
				<div class="layui-input-block">
					<input type="password" id="oldPwd" name="oldPwd" lay-verify="required|pass"
						placeholder="请输入原密码" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">新密码：</label>
				<div class="layui-input-block">
					<input type="password" id="userPwd"  name="userPwd" lay-verify="required|pass"
						placeholder="请输入新密码" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">确认密码：</label>
				<div class="layui-input-block">
					<input type="password"  id="newPwd" name="newPwd" lay-verify="required|pass"
						placeholder="请确认新密码" autocomplete="off" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-block">
					<button type="button" class="layui-btn" lay-submit=""
						lay-filter="updBut_" style="width: 20%;" id="updBut">保存</button>
					<button type="button" class="layui-btn layui-btn-primary" style="width: 20%;" id="cancelBtn">取消</button>
				</div>
			</div>
		</form>
	</div>
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header" id="layui-header">
			<!--<div class="logo_img">
				<img src="images/logo/logo_inti.png" alt="">
			</div>-->
			
			<div class="layui-logo" style="color:#ffffff;top: -5px;left: 15px;width: auto;"><span style="cursor: pointer;" onclick="window.location.reload();">智能用水管理系统</span></div>
			 <!-- 头部区域（可配合layui已有的水平导航） -->
		    <ul class="layui-nav layui-layout-left">
		      <li class="layui-nav-item" id="fold"><a href="javascript:void(0);"><i id="fold-btn" class="layui-icon layui-icon-shrink-right"></i>菜单缩进</a></li>
		      <li class="layui-nav-item"><a href=""><i class="layui-icon layui-icon-home"></i>主页</a></li>
		      <li class="layui-nav-item"> <a href="javascript:;" layadmin-event="refresh" title="刷新" onclick="document.getElementById('iframe').contentWindow.location.reload(true)"><i class="layui-icon layui-icon-refresh-1"></i>刷新</a></li>		  
		    </ul>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item">
					<a href="javascript:;"> <img src="${ctx}/resources/images/logo/headerLogos.png"
						class="layui-nav-img" />${sessionScope.workerSession.workerName}</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="javascript:void(0);" id="upd">修改密码</a>
						</dd>
					</dl></li>
				<li class="layui-nav-item"><a href="javascript:void(0);"
					id="login_out">退出登录</a></li>
			</ul>
		</div>
		<div class="layui-side layui-bg-bai"><!---->
			<div class="layui-side-scroll">
				<!-- 左侧导航区域 -->
				<ul class="layui-nav layui-nav-tree" lay-shrink="all" lay-filter="test" id="navList">
				 <!--<li class="layui-nav-item layui-this"><a href="javascript:void(0);" menu_url='main.html' page_name="首页" ><i class="layui-icon layui-icon-home"></i>首页</a></li>-->
				 <li class="layui-nav-item">
			        <a href="javascript:void(0);"><i class="layui-icon layui-icon-console"></i>水表管理</a>
			        <dl class="layui-nav-child">
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/waterpurchasemgt/waterPurchaseMgt.action' page_name="水表管理 / 购水管理" >购水管理</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/wateraddmgt/waterAddMgt.action' page_name="水表管理 / 补水管理">补水管理</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/waterquitmgt/waterQuitMgt.action' page_name="水表管理 / 退水管理" >退水管理</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/waterroommgt/waterRoomMgt.action' page_name="水表管理 / 控水换房管理">控水换房管理</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/waterreset/waterResetIndex.action' page_name="水表管理 / 房间水量清零">房间水量清零</a> </dd>	
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/watermetedetails/waterMeteDetails.action' page_name="水表管理 / 水表交易明细">水表交易明细</a> </dd>
			        </dl>
			      </li>
			      <li class="layui-nav-item">
			        <a href="javascript:void(0);"><i class="layui-icon layui-icon-search"></i>报表查询</a>
			        <dl class="layui-nav-child">
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/redrushRecord/index.action' page_name="报表查询 / 冲红记录">冲红记录</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/reportquery/waterPayRecord.action' page_name="报表查询 / 购水记录">购水记录</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/reportquery/waterAddDtls.action' page_name="报表查询 / 补水明细">补水明细</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/reportquery/waterQuitDtls.action' page_name="报表查询 / 退水明细">退水明细</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/replaceRecord/index.action' page_name="报表查询 / 换表历史记录">换表历史记录</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/replaceRoomRecord/index.action' page_name="报表查询 / 控水换房明细">控水换房明细</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/roomDaily/index.action' page_name="报表查询 / 用水房间日报">用水房间日报</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/roomMonth/index.action' page_name="报表查询 / 用水房间月报">用水房间月报</a></dd>
			        </dl>
			      </li>			     
			      <li class="layui-nav-item">
			        <a href="javascript:void(0);"><i class="layui-icon layui-icon-set"></i>系统管理</a>
			        <dl class="layui-nav-child">
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/region/tree.action' page_name="系统管理 / 区域管理">区域管理</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/concentratormgt/concentratorMgt.action' page_name="系统管理 / 集中器管理">集中器管理</a></dd>
						<dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/waterMeterMgt/waterMeterMgt.action' page_name="系统管理 / 水表管理">水表管理</a></dd>
			          <%--<dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/watermeterroominfomgt/watermeterRoomInfoMgt.action' page_name="系统管理 / 用水房间月报">房间管理</a></dd>--%>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/floormgt/floorMgt.action' page_name="系统管理 / 房间管理">房间管理</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/clctapplmgt/clctApplMgt.action' page_name="系统管理 / 采集应用管理">采集管理</a></dd>
						<dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/concentrator/list.action' page_name="系统管理 / 公寓集中器">公寓集中器</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/operationlog/operationLog.action' page_name="系统管理 / 职工操作日志">操作日志</a></dd>
						<dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/loginLog/list.action' page_name="系统管理 / 职工登录日志">登录日志</a></dd>
			        </dl>
			      </li>
			      <li class="layui-nav-item">
			        <a href="javascript:void(0);"><i class="layui-icon layui-icon-util"></i>参数设置</a>
			        <dl class="layui-nav-child">
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/sysrunningparm/sysRunningParm.action' page_name="参数设置 / 系统运行参数">系统运行参数</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/readWritecard/readWriteCardParm.action' page_name="参数设置 / 读写卡参数">读写卡参数</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/smartcard/smartCard.action' page_name="参数设置 / 一卡通终端">一卡通终端</a></dd>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/roomtype/roomTypeParm.action' page_name="参数设置 / 房间类型参数">房间类型参数</a></dd>	         
			        </dl>
			      </li>
			      <li class="layui-nav-item">
					  <%--<shiro:hasPermission name="权限控制">--%>
						  <a href="javascript:void(0);"><i class="layui-icon layui-icon-about"></i>权限控制</a><%--</shiro:hasPermission>--%>
			        <dl class="layui-nav-child">
						<%--<shiro:hasPermission name="职工管理">--%>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/workermgt/workerMgt.action' page_name="权限控制 / 职工管理">职工管理</a></dd>
						<%--</shiro:hasPermission>--%>
						<%--<shiro:hasPermission name="角色管理">--%>
			          <dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/rolemgt/roleMgt.action' page_name="权限控制 / 角色管理">角色管理</a></dd>
						<%--</shiro:hasPermission>--%>
						<%--<shiro:hasPermission name="权限管理">--%>
						<dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/authorityMgt/list.action' page_name="权限控制 / 权限管理">权限管理</a></dd>
						<%--</shiro:hasPermission>--%>
						<%--<shiro:hasPermission name="资源管理">--%>
						<dd><a class="marginL-15" href="javascript:void(0);" menu_url='${ctx}/resourceMgt/list.action' page_name="权限控制 / 资源管理">资源管理</a></dd>
						<%--</shiro:hasPermission>--%>
			        </dl>
			      </li>
			    </ul>
			</div>
		</div>
		<div class="layui-body" style="height: 100%;background:#F2F2F2;top: 96px;" id="layui-body">
			<!--导航-->
			<div class="nav-bar" style="">
				
				<span style="margin-left: 12px;" pageUrl>当前位置：首页</span>
			</div>
			<!-- 内容主体区域 -->
			<div  style="padding:10px 10px 0 10px;" id="iframeBox">
				<iframe style="" src="${ctx}/login/main.action" id="iframe" frameborder="0" width="100%" scrolling="no" height="100%"> </iframe>
			</div>
		</div>
	</div>
	
</body>

	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script>
//主动加载jquery模块
layui.use(['jquery', 'layer'], function(){
	var $ = layui.jquery //重点处
	,layer = layui.layer;
	var form = layui.form;
	//折叠右侧菜单
	var flag = 1;//是否折叠菜单标识，默认1打开、0折叠
	$("#fold").click(function(){
		if(flag==1){//折叠菜单
			$("#fold-btn").removeClass("layui-icon layui-icon-shrink-right").addClass("layui-icon layui-icon-spread-left");
			flag = 0;	
			$(".layui-body").animate({left:'50px'},"fast");
			$(".layui-nav-tree a").css('font-size',0);		
			$(".layui-nav-tree").animate({width:'50px'},"fast");			
			$(".layui-layout-admin .layui-side").animate({width:'50px'},"fast");
			$(".layui-nav-tree .layui-nav-more").hide();
			$(".layui-nav-tree .layui-nav-item").find('dd').hide();
			/*给折叠的菜单，加上hover效果*/
			var tipindex;//tip下标
			var tiptext;//tip内容
			$(".layui-nav-tree .layui-nav-item").hover(function (){
				if(flag==1){//判断菜单折叠状态，只有折叠时，才有hover效果
					return;
				}
			   	tiptext = $(this).children().eq(0).text();
		        tipindex= layer.tips(tiptext,$(this), {
		            tips: 2,//右边出现
		            time:0//tip 消失时间
		        });
		    },function (){//关闭tip的回调函数  
		        layer.close(tipindex);
		    }); 
		}else{//打开菜单
			$("#fold-btn").removeClass("layui-icon layui-icon-spread-left").addClass("layui-icon layui-icon-shrink-right");
			flag = 1;
			$(".layui-body").animate({left:'200px'},"fast");
			$(".layui-nav-tree a").css('font-size','14px');
			$(".layui-nav-tree").animate({width:'200px'},"fast");
			$(".layui-layout-admin .layui-side").animate({width:'200px'},"fast");
			$(".layui-nav-tree .layui-nav-more").show();
			$(".layui-nav-tree .layui-nav-item").find('dd').show();	
			
		}
		
	})
	/*当侧边菜单折叠时，点击某个子菜单，此时也打开菜单*/
	$(".layui-nav-tree .layui-nav-item").click(function(){
		if(flag==0){
			$(".layui-nav-tree .layui-nav-item").find('dd').show();
			$(".layui-nav-tree .layui-nav-more").show();
			$('#fold-btn').removeClass("layui-icon layui-icon-spread-left").addClass("layui-icon layui-icon-shrink-right");
			flag = 1;
			$(".layui-body").animate({left:'200px'},"fast");
			$(".layui-nav-tree a").css('font-size','14px');
			$(".layui-nav-tree").animate({width:'200px'},"fast");
			$(".layui-layout-admin .layui-side").animate({width:'200px'},"fast");
		}
	})
	
    //退出登录
    $('#login_out').click(function(){
        layer.confirm('退出登录?', function(index){
            location.href = '${ctx}/login/logOut.action';
            layer.close(index);
        });
    });
    //设置iframe的高度
    var hh = $("#layui-body").height();
    $('#iframeBox').height( hh-106);
    //点击左侧   子菜单，页面跳转，
    var topage = function(url){
        $("#iframe").attr('src', url);
    }
	//获取到在菜单路径，切换iframe页面，设置-当前位置 pageName
	var pageName = '';
    $(".layui-nav-tree li a").click(function(){
    	if($(this).get(0).hasAttribute("menu_url")){
    		topage($(this).get(0).getAttribute("menu_url"));
    	} 
    	if($(this).get(0).hasAttribute("page_name")){
    		pageName = $(this).get(0).getAttribute("page_name");//获取点击菜单的名称
    		$(".nav-bar span").text("当前位置："+pageName);//赋值
    	}
    })

	//修改密码
	layui.use('form', function(){
        /*var oldPwd=$("#oldPwd").val();
        var userPwd=$("#userPwd").val();
        var newPwd=$("#newPwd").val();*/
        //检测原密码输入是否正确
        function isCheckOldPwd(obj){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : '${ctx}/workermgt/isCheckOldPwd.action?id='+'${sessionScope.workerSession.id}', //路径
                data : obj, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        };


        form.on('submit(updBut_)', function(data){

            var obj=data.field;

            var num=isCheckOldPwd(obj);
            if(num == 0) {
                layer.msg('原密码输入错误,请重新输入！', {
                    icon: 7,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#oldPwd").focus();
                    $("#updBut").attr('disabled', false);
                });
                return false;
            }
            var oldPwd=obj.oldPwd;
            var userPwd=obj.userPwd;
            var newPwd=obj.newPwd;
           var re=/[a-zA-Z0-9]{6,18}$/;
            if (!re.test(userPwd)){
                layer.msg('新密码必须是6到18位的数字或字母！', {
                    icon : 5,
                    time : 2000 //2秒关闭（如果不配置，默认是3秒）
                },function(){
                    $("#updBut").attr('disabled', false);
                });
                return false;

            }
            if(oldPwd == userPwd){
                layer.msg('原密码与新密码不能一致！', {
                    icon : 7,
                    time : 2000 //2秒关闭（如果不配置，默认是3秒）
                },function(){
                    $("#updBut").attr('disabled', false);
                });
                return false;
            }
            if(userPwd != newPwd){
                layer.msg('两次新密码输入不一致！', {
                    icon : 7,
                    time : 2000 //2秒关闭（如果不配置，默认是3秒）
                },function(){
                    $("#updBut").attr('disabled', false);
                });
                return false;
            }

            layer.confirm("修改密码需要重新登录，是否确定修改密码？", {
                btn : [ "确定", "取消" ] //按钮
            }, function() {
                $.ajax({
                    type : "POST", //提交方式
                    url : '${ctx}/workermgt/savePassWord.action?id='+'${sessionScope.workerSession.id}', //路径
                    data : obj, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    async : false,
                    success : function(result) { //返回数据根据结果进行相应的处理
                        console.log(result);
                        var status = result.status;
                        var msg = result.msg;
                        layer.msg(msg, {
                            icon : 1,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function() {
                           /* window.parent.location.reload();*/
                            window.location.href = "${ctx}/login/logOut.action";
                        });
                    }
                });
            });
		});
    });
    var  indexIn='';
    $("#upd").click(function() {
        indexIn = layer.open({
	        type:1,
	        title:"修改密码",
	        skin:"myclass",
	        area:['500px', '300px'],
	        content:$("#test").html()
	    });
	});

 $("body").on('click','#cancelBtn',function(){
		layer.close(indexIn);
	  	console.log("点击了取消 ");
	});
	/*刷新，即 当前iframe里面的页面刷新*/
	var pageUrl = "main.html";


    $("#rePage").click(function(){
        if($("#navList .layui-nav-itemed").length>0){
            pageUrl = $("#navList .layui-this a").attr("menu_url");//获取选中菜单的url,给刷新使用
        }
        $('#iframe').attr('src', pageUrl);
    })
})





</script>
var re=/[a-zA-Z0-9]{6,18}$/;
</html>