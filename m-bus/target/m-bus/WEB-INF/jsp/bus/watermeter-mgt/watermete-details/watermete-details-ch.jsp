<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <title>水表管理-水表交易明细-冲红</title>
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
	        <input type="text" value="${transactionDetails.apartmentName}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">房间编号</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${transactionDetails.roomNum}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
  	</div>
  	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">模块状态</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${transactionDetails.moduleStatusName}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">操作时间</label>
	      <div class="layui-input-inline">
	        <input type="text" value="<fmt:formatDate pattern="yyyy:MM:dd HH:mm:ss" value="${transactionDetails.operatTime}"></fmt:formatDate>" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
  	</div>
  	<div class="layui-form-item">
  		<div class="layui-inline">
	      <label class="layui-form-label">流水号</label>
	      <div class="layui-input-inline">
	        <input type="text" name="" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">操作水量</label>
	      <div class="layui-input-inline">
	        <input type="text" id="operatWater" value="${transactionDetails.operatWater}" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
		 <span class="unit_">（吨）</span>
	    </div>
	   
  	</div>
</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend class="window-leg">冲红信息</legend>
</fieldset>
<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="width: ;margin-top: 20px;" lay-filter="updForm">
	<input type="hidden" name="transactionDetailsId" id="transactionDetailsId" value="${transactionDetails.id}">
	<input type="hidden" name="transacType" id="transacType" value="${transactionDetails.transacType}">

	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">冲红水量</label>
	      <div class="layui-input-inline">
	        <input type="text" id="redrushWater" name="redrushWater" value="${transactionDetails.operatWater}" onchange="changeWater()" lay-verify="required|number" autocomplete="off" class="layui-input"maxlength="10" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)">
	      </div>
	      <span class="unit_">（吨）</span>
	    </div>
  	</div>
  	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label w650">备注</label>
	    <div class="layui-input-block w650">
	      <textarea placeholder="请输入内容" id="remark"  name="remark" class="layui-textarea" style="resize:none;" maxlength="200" onchange="this.value=this.value.substring(0, 200)" onkeydown="this.value=this.value.substring(0, 200)" onkeyup="this.value=this.value.substring(0, 200)"></textarea>
	    </div>
   </div>
  	<div class="layui-form-item window-block-btn">
	    <div class="layui-input-block">
	    	<button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn">取消</button>
	      <button type="button" id="confirm_id" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">查询</button>
	    </div>
 	</div>
</form>

</body>

	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
	 //主动加载jquery模块
     var $ = layui.jquery //重点处
	layui.use(['jquery', 'layer'], function(){
		layer = layui.layer;
	
		var index = (window.parent.index_);
		layui.use('form', function(){
		 	var form = layui.form;
		 	//------授权提交-------
			form.on('submit(confirmBtn)', function (data) {
			    var obj = data.field;
                if(Number(data.field.redrushWater) <= 0){
                    layer.msg("请填写冲红水量！");
                    return;
                }
                if(Number(data.field.redrushWater) > Number($("#operatWater").val())){
                    layer.msg("冲红水量不能大于操作水量！");
                    return;
                }
                layer.confirm("确认冲红吗？", {btn: ['确定', '取消'], title: "温馨提示"}, function () {
                    var indexMsg = layer.msg('冲红提交中，请稍后...',{icon:16,time:false,shade:0});
                    $.ajax({
                        type: "POST",
                        async:false,
                        data: obj,
                        url: '${ctx}/watermetedetails/redrushConfirm.action',
                        success: function(data){
                            layer.msg(data.msg);
                            if(data.status == "success"){
                                $("#confirm_id").attr("disabled",true);
                                setTimeout("flutable()",2500);
                                layer.close(indexMsg);
                            }
                        }
                    });
                });
			});
			//取消
			$("body").on("click","#canleBtn",function(){
				parent.layer.close(index);//关闭layer
			})
		})
	})

     function changeWater(){
         var redrushWater = $("#redrushWater").val();
         if(!/^\d+\.?\d{0,2}$/.test(redrushWater)){
             layer.msg('只能输入数字!');
             $("#redrushWater").val(0);
             return;
         }
     }

     function flutable() {
         $("#canleBtn").click();
         // parent.location.reload();
     }
   
</script>
</html>