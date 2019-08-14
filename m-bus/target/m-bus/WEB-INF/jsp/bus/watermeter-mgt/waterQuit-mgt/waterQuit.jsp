<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <title>水表管理-退水管理-退水</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
  <style>
  	.layui-form-item .layui-inline{margin-right: 55px;}
  	.layui-form-item{margin-bottom: 10px;}
	[class="layui-form-label"],[class="layui-form-label marginL"]{
		border:none !important;
	}
	[readonly="readonly"]{
		border:none !important;
	}
  </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend class="window-leg">基本信息</legend>
</fieldset>
<div class="layui-form layui-form-pane ml30">
	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">公寓名称</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.apartmentName}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">房间编号</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.roomNum}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
  	</div>
  	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">集中器</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.moduleStatusName}" id="moduleStatusName" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">退水单价</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.price}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
  	</div>
  	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">剩余购水量</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.buyWaterTotal}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
		 <span class="unit_">（吨）</span>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label marginL">剩余金额</label>
	      <div class="layui-input-inline">
	        <input type="text" id="allowReturnWater" value="${waterMeterInfo.allowReturnMoney}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
		 <span class="unit_">（元）</span>
	    </div>
  	</div>
</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend class="window-leg">退水信息</legend>
</fieldset>
<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top: 20px;" lay-filter="updForm">
	<input type="hidden" name="waterMeterInfoId" value="${waterMeterInfo.id}">

	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">退水金额</label>
	      <div class="layui-input-inline">
	        <input type="text" id="returnMoney" name="returnMoney" onchange="changeReturnMoney()" lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="10" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)">
	      </div>
	      <span class="unit_">（元）</span>
	    </div>
  	</div>
  	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label w650">备注</label>
	    <div class="layui-input-block w650">
	      <textarea id="remark" name="remark" placeholder="请输入内容" class="layui-textarea" style="resize:none;" maxlength="200" onchange="this.value=this.value.substring(0, 200)" onkeydown="this.value=this.value.substring(0, 200)" onkeyup="this.value=this.value.substring(0, 200)"></textarea>
	    </div>
   </div>
  	<div class="layui-form-item window-block-btn">
	    <div class="layui-input-block">
	    	<button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn">取消</button>
	      	<button type="button" id="confirm_id" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">确定</button>
	    </div>
 	</div>
</form>
</body>

	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
	<script type="text/javascript" src="${ctx}/resources/water_meter_manage/waterMeterManage.js"></script>
<script type="text/javascript">
	 //主动加载jquery模块
	layui.use(['jquery', 'layer'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;
	
		//得到父页面的id值
		var index = (window.parent.index_);
		layui.use('form', function(){
		 	var form = layui.form;
		 	//------授权提交-------
			form.on('submit(confirmBtn)', function (data) {
			    var obj = data.field;
                if(Number(data.field.returnMoney) <= 0){
                    layer.msg("请填写退水金额！",{
                        icon:7,
						time:3000
					});
                    return;
                }
                if(Number(data.field.returnMoney) > Number($("#allowReturnWater").val())){
                    layer.msg("退水金额大于最大退水金额！",{
                        icon:7,
						time:3000
					});
                    return;
                }
                if($("#moduleStatusName").val().trim()=="离线"){
                    layer.msg("集中器处于离线状态！",{
                        icon:7,
                        time:3000
                    });
                    return;
				}
               layer.confirm("确认退水吗？", {btn: ['确定', '取消'], title: "温馨提示"}, function () {
                    var indexMsg = layer.msg('退水提交中，请稍后...',{icon:16,time:false,shade:0});
                    $.ajax({
                        type: "POST",
                        async:false,
                        data: obj,
                        url: '${ctx}/waterquitmgt/returnConfirm.action',
                        success: function(data){
                            layer.msg(data.msg);
                            if(data.status == "success"){
                                $("#confirm_id").attr("disabled",true);
                                //setTimeout("flutable()",2500); 因为不生效所以重新设置等待时间，ajax请求需要等待结果返回
                                layer.close(indexMsg);
                                 setTimeout(function () { parent.layer.close(index) },2500)

                            }

                        }

                    });
                });
			});
			//取消
			$("body").on("click","#canleBtn",function(){
				parent.layer.close(index);//关闭layer
			})
			
			//测试
			$("body").on("click","#testBtn",function(){
				/*弹窗里嵌套弹窗*/
				 com.pageOpen("test","html/watermeter-mgt/waterAddtion-mgt/test.html",['300', '200px']);
	
			})
		})
	})
	
	function changeReturnMoney() {
        var returnMoney = $("#returnMoney").val();
        if(!/^\d+\.?\d{0,2}$/.test(returnMoney)){
            layer.msg('只能输入数字!');
            $("#returnMoney").val(0);
            return;
        }
    }
   
</script>
</html>