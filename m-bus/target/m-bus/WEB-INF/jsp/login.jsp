<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>登录</title>
   <meta charset="utf-8"/>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="shortcut  icon" type="${ctx}/resources/image/x-icon" href="${ctx}/resources/images/etsIco.ico" media="screen" />
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <style type="text/css">
  	body,html{
  		height: 100%;
  		min-width: 1000px;
  		color: #fffff;
  	}
  	.logo{
  		
      height: 70px;
      width: 100%;
      background-color: #fff;
  	}
  	.logo div{
  		left: 30px;
      position: absolute;
      height: 70px;
      width: 240px;
      top: 5px;
  	}
  	.logo img{
  		 position: absolute;
      top: 0;
      left: 0;
      width: 100%;
  	}
  	.write{
  		color: #FFFFFF;
  	}
  	.layui-form-item{
  		margin-bottom: 30px;
  	}
  	/*验证码*/
  	#vCode{
  		display: inline-block;
  		border: 1px solid #666;
  		width: 100px;
  		height: 38px;
  		position: absolute;
  		top: -2px;
  		right: 20px;
  	}
    #vCode img{
      display: inline-block;
      height: 100%;
      width: 100%;
      position: absolute;
      top: 0;
      left: 0;
    }
   .layui-form-item{
   		padding: 0px 20px 0 0;
   		margin-left: -85px;
   }
   #layui-form{
     /* box-shadow: 6px 6px 15px #666;*/
      height: 400px;
      width: 320px;
      /*background-color:rgba(255,255,255,0.4);*/
     background-color:rgba(80,80,80,0.4);
      border-radius: 10px;
      padding: 10px;
      position: absolute;
      right: 10%;
      top: 50%;
      transform: translateY(-50%);
      color: #FFFFFF;
   }
  </style>
</head>
<body>
<div class="logo">
	<div><img src="${ctx}/resources/images/logo/etsLogo.png"/></div>
</div>
<div style="background: url(${ctx}/resources/images/bg.png) no-repeat;  height: 80%;  margin-top:10px; background-size: 100% 100%;">
	<form class="layui-form" action="loginin" method="post" lay-filter="example" id="layui-form">
		<h2 class="write" style="text-align: center; padding-top: 25px;">智能用水管理系统</h2>
	  <div class="layui-form-item" style="margin-top:50px ;">
	   <!-- <label class="layui-form-label write">用户名</label>-->
	    <div class="layui-input-block">
	      <input type="text" name="workerAccount" id="workerAccount" lay-verify="required|workerAccount" autocomplete="off" placeholder="请输入账号" maxlength="17" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	   <!-- <label class="layui-form-label write">密码</label>-->
	    <div class="layui-input-block">
	      <input type="password" name="password" id="password" lay-verify="required|password"  placeholder="请输入密码" maxlength="16" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item" style="position: relative;">
	   <!-- <label class="layui-form-label">验证码</label>-->
	    <div class="layui-input-block" style="width: 40%;">
	      <input type="text" name="vCode" id="inputStr" lay-verify="required" autocomplete="off" placeholder="请输入验证码" maxlength="4" class="layui-input">
	    </div>
	     <a href="javascript:void(0);" class="" id="vCode" style="">
			 <img id="imgVerify" src="" alt="点击更换验证码"/>
		 </a>
	  </div>
	 
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button type="button" class="layui-btn" lay-submit="" lay-filter="loginBtn" style="width:275px;" id="loginBtn">登录</button>
	    </div>
	  </div>
	
	</form>
	 
</div>
 <div class="bottom" style="position: fixed;bottom:10px;left: 0;width: 100%;text-align: center;">
	<span style="color: #333333;font-size: 14px;">技术支持：<a href="http://www.etslink.cn/" target="_blank">安徽银通物联有限公司</a></span>
</div>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>

<script type="text/javascript">
    $(document.body).ready(function () {
        //首次获取验证码
        $("#imgVerify").attr("src","${ctx}/login/getVerify.action?"+Math.random());
    });
 /*   //获取验证码
    function getVerify(){
        var src1=document.getElementById('imgVerify')
        src1.src = "${ctx}/login/getVerify.action?"+Math.random();
    }*/
    // 更换验证码
    $('#imgVerify').click(function()
    {
        $('#imgVerify').attr("src", "${ctx}/login/getVerify.action?"+Math.random())
    });

    //主动加载jquery模块
layui.use(['jquery', 'layer'], function(){ 

  var $ = layui.jquery,layer = layui.layer;
  
	layui.use(['form', 'layedit', 'laydate'], function(){
	
		var form = layui.form,layer = layui.layer;
        form.verify({
            workerAccount:[/[a-zA-Z0-9_]{6,18}$/, '请输入正确的格式（账号必须是6到18位数字和字母）'],
            password:[/[a-zA-Z0-9_]{6,18}$/, '请输入正确的格式（密码必须是6-18数字和字母）']
        });
		
		$("body").keydown(function () {
	      var thEvent = window.event || arguments.callee.caller.arguments[0];
	      if (thEvent.keyCode == "13") {
	       	$("#loginBtn").click(); 
	      }
 		});
 		
	  //监听提交
	  form.on('submit(loginBtn)', function(data){
	  
      //$('.layui-form').submit();
		  //console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
		  //以下是登录的ajax，为了演示需要注释了~
		  var workerAccount=$.trim($('#workerAccount').val());
          var password=$('#password').val();
          var inputStr=$.trim($('#inputStr').val().toUpperCase());
          var sss={'workerAccount':workerAccount,'password':password,'inputStr':inputStr}
	    $.ajax({
			  type: 'POST',
			  data:sss,//登录入参
			  url: "${ctx}/login/loginCheck.action",//登录接口
			  dataType : "json",
			  success: function(result){
				
				var status = result.status;
				var msg = result.msg;
				
				//console.log(msg);
				if (status == 0) {
					layer.msg(msg, {icon:6,time:1500},function(){//登陆成功跳转到主页
						window.location.href = "${ctx}/login/loginSuccess.action";
					});	
				}else if(status == 1){
					layer.msg(msg, {icon: 5, anim: 6,time:1500}, function () {
                        $("#inputStr").val('');
                        $("#imgVerify").attr("src","${ctx}/login/getVerify.action?"+Math.random());
                    });
				}else if(status == 2 || status == 3 || status == 4
                ){
					layer.msg(msg, {icon: 2, anim: 6,time:1500}, function () {
                        $("#inputStr").val('');
                        $("#imgVerify").attr("src","${ctx}/login/getVerify.action?"+Math.random());
                    });
				}else if(status == 5){
                    layer.msg(msg, {icon: 2, anim: 6,time:1500},
                        function () {
                        $("#inputStr").val('');
                        $("#imgVerify").focus();
                        $("#imgVerify").attr("src","${ctx}/login/getVerify.action?"+Math.random());
                    });
                }else {
                    layer.msg("验证码错误", {icon: 2, anim: 6,time:1500},
                        function () {
                            $("#inputStr").val('');
                            $("#imgVerify").focus();
                            $("#imgVerify").attr("src","${ctx}/login/getVerify.action?"+Math.random());
                        });
                }
			},error:function(e){
			  	layer.msg('系统异常，请稍后重试！', {icon: 5, anim: 6,time:1500}, function () {
                    $("#inputStr").val('');
                    $("#imgVerify").attr("src","${ctx}/login/getVerify.action?"+Math.random());
                });
			}
		});
	  });
	 
	  /*$('#vCode').click(function(){
	      $(this).children('img').hide().attr('src','kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
	  });*/
	});
});  
</script>
</body>
</html>