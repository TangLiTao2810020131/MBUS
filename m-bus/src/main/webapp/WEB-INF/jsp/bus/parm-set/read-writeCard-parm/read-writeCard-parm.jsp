<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>参数设置-读写卡参数</title>
		<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
		<link rel="shortcut  icon" type="../image/x-icon" href="${ctx}/resources/images/etsIco.ico" media="screen" />
	</head>
	<body class="parm parms">
		<form class="layui-form" action="" style="color: #666; background: #FFFFFF; padding-top: 30px;">
		  <div class="layui-form-item">
			  <input type="hidden" name="id" id="id" class="layui-input" value="${readWriteCardParm.id}">
		    <div class="layui-inline">
		      <label class="layui-form-label">参数卡版本</label>
		      <div class="layui-input-inline">
		        <input type="text" name="pversion_num" id="pversion_num" readonly="readonly" disabled="disabled" class="layui-input layui-disabled" value="${paramCard.version_num}">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">基本信息扇区地址</label>
		      <div class="layui-input-inline">
		        <input name="base_address" id="base_address" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.base_address}" maxlength="20">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">金额信息区扇区地址</label>
		      <div class="layui-input-inline">
		        <input name="money_address" id="money_address" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.money_address}" maxlength="20">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">记录信息区扇区地址</label>
		      <div class="layui-input-inline">
		        <input name="record_address" id="record_address" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.record_address}" maxlength="20">
		      </div>
		    </div>
		  </div>
		  
		  <div class="layui-form-item">
		    
		    <div class="layui-inline">
		      <label class="layui-form-label">水表信息区扇区地址</label>
		      <div class="layui-input-inline">
		        <input name="watermete_address" id="watermete_address" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.watermete_address}" maxlength="20">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">扩展信息区扇区地址</label>
		      <div class="layui-input-inline">
		        <input name="extend_address" id="extend_address" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.extend_address}" maxlength="20">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">小钱包信息区扇区地址</label>
		      <div class="layui-input-inline">
		        <input name="purse_address" id="purse_address" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.purse_address}" maxlength="20">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">是否使用CRC校验</label>
		      <div class="layui-input-inline">
		        <input name="use_crc" id="use_crc" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.use_crc}" maxlength="20">
		      </div>
		    </div>
		  </div>
		  
		  <div class="layui-form-item">
		    
		    <div class="layui-inline">
		      <label class="layui-form-label">用户的卡密码模式</label>
		      <div class="layui-input-inline">
		        <input name="card_pass_mode" id="card_pass_mode" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.card_pass_mode}" maxlength="20">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">保留值1</label>
		      <div class="layui-input-inline">
		        <input name="retention_one" id="retention_one" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.retention_one}" maxlength="20">
		      </div>
		    </div>
		     <div class="layui-inline">
		      <label class="layui-form-label">保留值2</label>
		      <div class="layui-input-inline">
		        <input name="retention_two" id="retention_two" class="layui-input" type="text" autocomplete="off" lay-verify="required" value="${readWriteCardParm.retention_two}" maxlength="20">
		      </div>
		    </div>
		  </div>
		 
		  <div class="layui-form-item" style="padding-bottom: 20px;">
		    <div class="layui-input-block" style="margin-left: 395px;">
				<%--<shiro:hasPermission name="编辑读写卡参数">--%>
		      <button class="layui-btn" type="button" lay-submit lay-filter="save" id="save" style="margin-left: 84px;">&nbsp;保  存&nbsp;</button>
			<%--	</shiro:hasPermission>--%>
		    </div>
		  </div>
		</form>
	</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
	<script>
		layui.use(['form', 'layedit', 'laydate'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  ,layedit = layui.layedit
		  ,laydate = layui.laydate;
		  
		  //日期
		  laydate.render({
		    elem: '#date'
		  });
            //检查参数设置的版本号
            function isCheckParmVersionNum(obj){
                var str;
				var pversion_num=$("#pversion_num").val();
                $.ajax({
                    type : "POST", //提交方式
                    url : '${ctx}/readWritecard/isCheckParmVersionNum.action?pversion_num'+pversion_num, //路径
                    data : obj, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    async : false,
                    success : function(result) { //返回数据根据结果进行相应的处理
                        str = result.status;
                    }
                });
                return str;
            }
		/*	//页面加载完成就去检验参数配置版本号
            $(document).ready(function (data){
			var obj=data.field;
			var str=isCheckParmVersionNum(obj);

            });*/
		
		  
		  //监听提交
		  form.on('submit(save)', function(data){
		    console.log(JSON.stringify(data.field));
              var obj=data.field;
              var str=isCheckParmVersionNum(obj);
              var pversion_num=$("#pversion_num").val();
              if(str == '2'){
                  layer.msg('版本号发生改变，请重新配置参数!',{
                      icon:5,
                      time:2000
                  },function(){
                      layer.close(layer.index);
                      $("#save").attr('disabled', false);
                  })
              }
                  //提交参数数据保存
                  $.ajax({
                      type : 'post', //提交方式
                      url : '${ctx}/readWritecard/saveReadWritecardparm.action?pversion_num'+pversion_num,//路径
                      data :obj,//参数
                      dataType:'json',
                      async:false,
                      success : function(result) {//返回数据根据结果进行相应的处理
                            var msg=result.msg;
                              layer.msg(msg,{
                                  icon:1,
                                  time:2000
                              },function(){
                                  layer.close(layer.index);
                                  window.location.reload();
                              })
                          }
                      })
                  });
                  return false;


		  });

	</script>
</html>
