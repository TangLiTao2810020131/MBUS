<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>注册引导页</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="shortcut  icon" type="image/x-icon" href="${ctx}/resources/images/etsIco.ico" media="screen" />
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
  <style type="text/css" media="screen">
    html,body{
      height: 100%;
      font: 14px Helvetica Neue,Helvetica,PingFang SC,\5FAE\8F6F\96C5\9ED1,Tahoma,Arial,sans-serif;
      background-color: #f9f9f9;
    }
    *{
      padding: 0;
      margin: 0;
    } 
    .header{
      position: absolute;
      height: 70px;
      width: 100%;
      background-color: #fff;
    }
    .header-img{
      left: 30px;
      position: absolute;
      height: 70px;
      width: 240px;

    }
    .header-img img{
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
    }
    .layui-row{
      min-width: 1300px;
    }
    .left-img{
      height: 550px;
      width: 350px;
      background-color: #ccc;
      margin: 120px 0 0 200px;
    }
    .stpe1,.stpe2,.stpe3{
      margin:180px;
     
    }
    #btn2, #btn1, #btn3,#backBtn,{
    	width: 170px;
    }
    h2{
      font-weight: bold;
      font-size: 30px;
    }
    ol li{
      color: #ff0000;
      margin-top: 30px;
    }
    .layui-btn-fluid {
      width: 30%;
      margin-left: 25%;
      margin-top: 80px;
    }
    .layui-form{
    	margin-top: 30px;
    }
    #getCode-btn{
    	position: absolute;
    	bottom: 0;
    	right: -110px;
    }
    #backBtn{
    	position: absolute;
    	bottom: -120px;
    	left: -50px;
    }
    #btn3{
    	position: absolute;
    	bottom: -120px;
    	right: -50px;
    }
    
   @media screen and (max-width: 1200px) {
    .stpe1,.stpe2,.stpe3{
      margin:50px 180px 180px 130px;
     
    }
}
  </style>
</head>
<body>
 
  <div class="layui-row">
    <div class="header">
    <div class="header-img"><img src="${ctx}/resources/images/logo/etsLogo.png" alt=""></div>
    <div>
    <div class="layui-col-md4">
      <div class="left-img">
          
      </div>
    </div>
    <div class="layui-col-md8" style="min-width: 800px;">
      <!---------授权引导页start------------>
      <div class="stpe1">
        <h2>欢迎使用 银通物联智能用水管理系统</h2>
        <div class="card-body">
          <p style="font-size:18px;padding:40px 0 20px 0;">您还需要完成下面的操作完成软件授权注册：</p>
          <ol>
            <li>1、点击“获取特征码”按钮，获取当前系统的特征码。</li>
            <li>2、如果获取特征码成功，根据获取到的当前系统的特征码，联系技术支持人员获取系统
      授权文件。</li>
            <li>3、将获取到的系统授权文件上传，再点击“授权注册”。完成系统注册操作。</li>
          </ol>
          
          <button class="layui-btn layui-btn-fluid" id="btn1">知道了</button>
        </div>
      </div>
      <!---------授权引导页end------------>
 
      <!---------获取系统特征码start---------->
      <div class="stpe2" style="display: none;">
        <h2>欢迎使用 银通物联智能用水管理系统</h2>
        <div class="layui-form layui-form-pane">
          <div class="layui-form-item layui-form-text" style="width: 395px;position: relative;">
				    <label class="layui-form-label">系统特征码</label>
				    <div class="layui-input-block">
				      <textarea placeholder="" class="layui-textarea" readonly="readonly" style="resize:none;">34223efwerewrew324323</textarea>
				      <button class="layui-btn-sm layui-btn-primary" id="getCode-btn">获取特征码</button>
				    </div>
				   
				  </div>
				  <p style="font-size:15px;padding:20px 0 20px 0;color: red;">根据获取到的当前系统的特征码，联系技术支持人员获取系统授权文件。</p>
				  <button class="layui-btn layui-btn-fluid" id="btn2">我已收到授权文件</button>
        </div>
      </div>
      <!---------获取系统特征码end------------>
      
      <!---------授权注册start---------------->
      <div class="stpe3" style="display: none;">
        <h2>欢迎使用 银通物联智能用水管理系统</h2>
        
        <div class="layui-form" style="width: 395px;position: relative;">
	        <input type="file" name="file" id="upFile">
	        <p style="font-size:15px;padding:20px 0 20px 0;color: red;">选择授权文件进行授权注册。</p>
	        <div class="btns">
						<button class="layui-btn layui-btn-fluid" id="backBtn">上一步</button>
		        <button class="layui-btn layui-btn-fluid" id="btn3">授权注册</button>	        	
	        </div>
        </div>
      </div>
      <!---------授权注册end------------------>
    </div>
  </div>
</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
//主动加载jquery模块
layui.use(['jquery', 'layer'], function(){
  var $ = layui.jquery //重点处
  ,layer = layui.layer
  var upload = layui.upload;
  //后面就跟你平时使用jQuery一样
	
	/*点击  知道了 按钮btn1 显示  获取系统特征码*/
	$("#btn1").click(function(){
		$(".stpe2").show(0);
		$(".stpe1").hide(0);
		$(".stpe3").hide(0);
	})
	
	/*点击收到文件按钮 btn2  显示 授权注册 */
	$("#btn2").click(function(){
		$(".stpe3").show(0);
		$(".stpe1").hide(0);
		$(".stpe2").hide(0);
	})
	/*点击返回上一步backBtn  显示获取系统特征码*/
	$("#backBtn").click(function(){
		$(".stpe2").show(0);
		$(".stpe1").hide(0);
		$(".stpe3").hide(0);
	})
 //选完文件后不自动上传
  upload.render({
    elem: '#upFile'
    ,url: '/upload/'//接口
    //,accept: 'file' //允许上传的文件类型
  	//,size: 50 //最大允许上传的文件大小
    ,auto: false
    //,multiple: true
    ,bindAction: '#btn3'
    ,done: function(res){
      console.log(res)
    }
  });

})
</script>
</html>