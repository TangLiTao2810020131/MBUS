<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <title>水表管理-补水管理-按房间补水</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
  <style>
  	.layui-form-item{margin-bottom: 10px;}
	[class="layui-form-label"]{
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
	<div class="layui-form-item ">
	    <div class="layui-inline">
	      <label class="layui-form-label">公寓名称</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.apartmentName}"  readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">房间编号</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.roomNum}"  readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
  	</div>
  	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">集中器</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.moduleStatusName}"  id="moduleStatusName" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">采集时间</label>
	      <div class="layui-input-inline">
			  <c:choose>
				  <c:when test="${empty waterMeterInfo.collectTime}">
					  <input type="text" value="- - - -" readonly="readonly" disabled="disabled" class="layui-input">
				  </c:when>
				  <c:otherwise>
					  <input type="text" value="<fmt:formatDate pattern="yyyy:MM:dd HH:mm:ss" value="${waterMeterInfo.collectTime}"></fmt:formatDate>" readonly="readonly" disabled="disabled" class="layui-input">
				  </c:otherwise>
			  </c:choose>
	      </div>
	    </div>
  	</div>
  	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">剩余水量</label>
	      <div class="layui-input-inline">
	        <input type="text" value="${waterMeterInfo.surplusWater} 吨" readonly="readonly" disabled="disabled" class="layui-input">
	      </div>
		 <%--<span class="unit_">（吨）</span>--%>
	    </div>
		<div class="layui-inline">
			<label class="layui-form-label">最大补水量</label>
			<div class="layui-input-inline">
				<input type="text" value="${waterMeterInfo.allowWater} 吨" readonly="readonly" disabled="disabled" class="layui-input">
			</div>
			<%--<span class="unit_">（吨）</span>--%>
		</div>
	   
  	</div>
</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend class="window-leg">补水信息</legend>
</fieldset>
<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="width: ;margin-top: 20px;" lay-filter="updForm">
	<input type="hidden" name="waterMeterInfoId" value="${waterMeterInfo.id}">
	<input type="hidden" id="allowWater" value="${waterMeterInfo.allowWater}">
	<input type="hidden" id="operatMoney" name="operatMoney" value="0">

	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">补水量</label>
	      <div class="layui-input-inline">
	        <input type="text" id="addWater" name="addWater" onchange="changeAddWater()" lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="10" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)">
	      </div>
	      <span class="unit_">（吨）</span>
	    </div>
  	</div>
  	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label w650">普通文本域</label>
	    <div class="layui-input-block  w650">
	      <textarea placeholder="请输入内容" name="remark" class="layui-textarea" style="resize:none;" maxlength="200" onchange="this.value=this.value.substring(0, 200)" onkeydown="this.value=this.value.substring(0, 200)" onkeyup="this.value=this.value.substring(0, 200)"></textarea>
	    </div>
   </div>
  	<div class="layui-form-item  window-block-btn">
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
	 var $ = layui.jquery //重点处
	layui.use(['jquery', 'layer'], function(){
		layer = layui.layer;
		//得到父页面的id值
		var index = (window.parent.index_);

		layui.use('form', function(){  
		 	var form = layui.form;
		 	//------授权提交-------
			form.on('submit(confirmBtn)', function (data) {
			    var obj = data.field;
                if(Number(data.field.addWater) <= 0){
                    layer.msg("请填写补水量！",{
                        icon:7,
						time:3000
					});
                    return;
                }
                if(Number(data.field.addWater) > Number($("#allowWater").val())){
                    layer.msg("补水量大于最大补水量！",{
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
                layer.confirm("确认补水吗？", {btn: ['确定', '取消'], title: "温馨提示"}, function () {
                    var indexMsg = layer.msg('补水提交中，请稍后...',{icon:16,time:false,shade:0});
                    $.ajax({
                        type: "POST",
                        async:false,
                        data: obj,
                        url: '${ctx}/wateraddmgt/addConfirm.action',
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
			
			//测试
			$("body").on("click","#testBtn",function(){
				/*弹窗里嵌套弹窗*/
				 com.pageOpen("test","html/watermeter-mgt/waterAddtion-mgt/test.html",['300', '200px']);
	
			})
		})	
	})
   
</script>
</html>