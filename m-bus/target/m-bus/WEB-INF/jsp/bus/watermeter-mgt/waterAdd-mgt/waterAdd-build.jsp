<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="utf-8">
	<title>水表管理-补水管理-按楼层补水</title>
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
<div class="layui-form  layui-form-pane ml30">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">区域名称</label>
			<div class="layui-input-inline">
				<input type="text" value="${waterMeterInfo.areaName}" readonly="readonly" disabled="disabled" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">公寓名称</label>
			<div class="layui-input-inline">
				<input type="text" value="${waterMeterInfo.apartmentName}" readonly="readonly" disabled="disabled" class="layui-input">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">楼栋名称</label>
			<div class="layui-input-inline">
				<input type="text" value="${waterMeterInfo.buildName}" readonly="readonly" disabled="disabled" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">楼层名称</label>
			<div class="layui-input-inline">
				<input type="text" value="${waterMeterInfo.floorName}" readonly="readonly" disabled="disabled" class="layui-input">
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
		<div class="layui-inline" style="float:right;margin-top:-130px;">
			<table id="demo" lay-filter="test"></table>
		</div>
	</div>
</div>
<div style="float:left;width:500px;margin-top:-350px;">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend class="window-leg">补水信息</legend>
	</fieldset>
	<form class="layui-form layui-form-pane ml30" action="" id="formHtml"  lay-filter="updForm">
		<input type="hidden" id="operatMoney" name="operatMoney" value="0">
		<input type="hidden" id="roomTypes" name="" value="0">
		<input type="hidden" id="floorId" name="floorId" value="${waterMeterInfo.floorId}">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">房间类型</label>
				<div class="layui-input-inline">
					<select name="roomType">
						<option value="">全部</option>
						<c:forEach items="${roomTypeList}" var="roomType">
							<option value="${roomType.typeNum}">${roomType.typeName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">补水量</label>
					<div class="layui-input-inline">
						<input type="text" id="addWater" name="addWater" onchange="changeAddWater()" lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="10" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)">
					</div>
					<span class="unit_">（吨）</span>
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" name="remark" class="layui-textarea" style="resize:none;" maxlength="200" onchange="this.value=this.value.substring(0, 200)" onkeydown="this.value=this.value.substring(0, 200)" onkeyup="this.value=this.value.substring(0, 200)"></textarea>
				</div>
			</div>
			<div class="layui-form-item window-block-btn">
				<div class="layui-input-block">
					<button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn">取消</button>
					<button type="button" id="confirm_id" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">确定</button>
				</div>
			</div>
		</div>
	</form>
</div>
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
        var id = (window.parent.id_);
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
                        url: '${ctx}/wateraddmgt/addFloorConfirm.action',
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
        });
    });

    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo'
            ,height: 500
			,width: 450
            ,url: '${ctx}/wateraddmgt/getRoomsByFloorId.action?floorId='+$("#floorId").val() //数据接口
            ,page: true
            ,cols: [[
                {field: 'roomNum', title: '房间号', sort: true,align:'center'}
                ,{field: 'allowWater', title: '最大补水量' ,align:'center'}
                ,{field: 'surplusWater', title: '剩余水量' ,align:'center'}
            ]]
        });

    });

</script>
</html>