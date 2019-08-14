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
	<title>参数设置-房间类型-编辑</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
	<style>
		.layui-form-item{margin-bottom: 10px;}
	</style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<input type="hidden" id="flag1" value="${rt.type_num}"/>
<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="width: ;margin-top: 40px;" lay-filter="updForm">
	<input type="hidden" name="id" value="${rt.id}"/>
	<input type="hidden" id="add_cycle" value="${rt.add_cycle}"/>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="类型编号" style="width:140px;">类型编号</label>
			<div class="layui-input-inline">
				<input type="text" name="type_num" id="type_num" value="${rt.type_num}" lay-verify="required|type_num" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="类型名称" style="width:140px;">类型名称</label>
			<div class="layui-input-inline">
				<input type="text" name="type_name" value="${rt.type_name}" lay-verify="required|type_name" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="软件补水周期" style="width:140px;">软件补水周期</label>
			<div class="layui-input-inline">
				<select name="add_cycle" lay-verify="required">
					<option value="0">每日补一次</option>
					<option value="1">每月补一次</option>
					<option value="2">不限制周期</option>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="参数生效时间" style="width:140px;">参数生效时间</label>
			<div class="layui-input-inline">
				<input type="text" name="effect_time" id="effect_time" value="${rt.effect_time}" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="购水单价(元/m³" style="width:140px;">购水单价(元/m³)</label>
			<div class="layui-input-inline">
				<input type="text" name="buy_money" value="${rt.buy_money}" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" lay-verify="required|buy_money" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="退水单价(元/m³)" style="width:140px;">退水单价(元/m³)</label>
			<div class="layui-input-inline">
				<input type="text" name="return_money" value="${rt.return_money}" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" lay-verify="required|return_money" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="报警水量(m³)" style="width:140px;">报警水量(m³)</label>
			<div class="layui-input-inline">
				<input type="text" name="warn_water" value="${rt.warn_water}" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" lay-verify="required|warn_water" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="关阀水量(m³)" style="width:140px;">关阀水量(m³)</label>
			<div class="layui-input-inline">
				<input type="text" name="valve_water"  value="${rt.valve_water}" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" lay-verify="required|valve_water" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="最大透支量(m³)" style="width:140px;">最大透支量(m³)</label>
			<div class="layui-input-inline">
				<input type="text" name="over_water" value="${rt.over_water}" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" lay-verify="required|over_water" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label label-120" title="最大囤积量(m³)" style="width:140px;">最大囤积量(m³)</label>
			<div class="layui-input-inline">
				<input type="text" name="hoard_water"  value="${rt.hoard_water}"  onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="20">
			</div>
		</div>
	</div>
	<div class="layui-form-item  window-block-btn">
		<div class="layui-input-block">
			<button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 47%">取消</button>
			<button type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">确定</button>
		</div>
	</div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#effect_time' //指定元素
            ,max:new Date()+""
            ,trigger: 'click' //采用click弹出
			,type:'datetime'
        });
    });

    //主动加载jquery模块
    var $ = layui.jquery;
    layui.use(['jquery', 'layer','form'], function(){
        var layer = layui.layer
            ,form = layui.form;
        //得到父页面的id值
        var index = (window.parent.index_);
        console.log(index);

        layui.use('form', function(){
            var form = layui.form;
            //------确认添加分区-------
            form.on('submit(confirmBtn)', function (data) {
                var obj = data.field;
                $.ajax({
                    url:"${ctx}/roomtype/editRoomType.action",
                    data:$("#formHtml").serialize(),
                    dataType:"json",
                    type:"post",
                    success:function(result) {
                        layer.msg("编辑成功!",{
                            icon:1,
                            time:2000
                        },function(){
                            parent.layer.close(index);//关闭layer
                            $('#iframe',parent.document).attr('src', '${ctx}/roomtype/roomTypeParm.action');
                        });
                    },
                    error:function(result){
                        layer.msg("服务器异常!",{
                            icon:7,
                            time:2000
                        });
                    }
                });
            });

            //设置select初始选中
            $(function() {
                //区域select
                $("[name='add_cycle'] option").each(function () {
                    var str = $(this).val();
                    if (str == $("#add_cycle").val()) {
                        $(this).attr("selected", true);
                    }
                });

                //重新渲染表单
                form.render();

            });

            //取消
            $("body").on("click","#canleBtn",function(){
                parent.layer.close(index);//关闭layer
            })
        });

        //表单验证
        form.verify({
            type_num:function(value,ele){
                if(value.trim()!=$("#flag1").val()){
                    $.ajax({
                        url:'${ctx}/roomtype/checkRoomTypeNum.action',
                        data:{roomTypeNum:value},
                        dataType:'json',
                        type:'post',
                        async:false,
                        success:function(result){
                            flag=result;
                        },error:function(){
                            layer.msg("服务器异常!",{
                                icon:7,
                                time:2000
                            });
                        }
                    });
                    if(flag==1){
                        return "房间类型编号已经存在！";
                    }
				}
            },
            type_name:function(value, item) {
                if(!/^[\u4e00-\u9fa5]{0,}$/.test(value.trim())) {
                    return "类型名称只能为中文!"
                }
            },
            buy_money:function(value, item) {
                if(value.trim()<=0)
                {
                    return "购水单价应为大于零的整数或小数类型";
                }
                if(!/^[0-9]+(.[0-9]{0,2})?$/.test(value.trim())){
                    return "购水单价应为小数或整数类型（最多两位小数）！"
                }
            },
            return_money:function(value, item) {
                if(value.trim()<=0)
                {
                    return "退水单价应为大于零的整数或小数类型";
                }
                if(!/^[0-9]+(.[0-9]{0,2})?$/.test(value.trim())){
                    return "退水单价应为小数或整数类型（最多两位小数）！"
                }
            },
            warn_water:function(value, item) {
                if(value.trim()<0)
                {
                    return "报警水量不能为小于零的整数或小数类型";
                }
                if(!/^[0-9]+(.[0-9]{0,2})?$/.test(value.trim())){
                    return "报警水量应为小数或整数类型（最多两位小数）！"
                }
            },
            valve_water:function(value, item) {
                if(value.trim()<0)
                {
                    return "关阀水量不能为小于零的整数或小数类型";
                }
                if(!/^[0-9]+(.[0-9]{0,2})?$/.test(value.trim())){
                    return "关阀水量应为小数或整数类型（最多两位小数）！"
                }
            },
            over_water:function(value, item) {
                if(value.trim()<0)
                {
                    return "最大透支量不能为小于零的整数或小数类型";
                }
                if (!/^[0-9]+(.[0-9]{0,2})?$/.test(value.trim())) {
                    return "最大透支量应为小数或整数类型（最多两位小数）！"
                }
            },
            hoard_water:function(value, item) {
                if(value.trim()<0)
                {
                    return "最大囤积量不能为小于零的整数或小数类型";
                }
                if (!/^[0-9]+(.[0-9]{0,2})?$/.test(value.trim())) {
                    return "最大囤积量应为小数或整数类型（最多两位小数）！"
                }
            },
        });

    });

    function checkNumber(result){
        if (!/^\d+\.?\d{0,2}$/.test($(result).val())){
            layer.msg('只能输入数字!');
            $(result).val(0);
            return;
        }
    };

</script>
</html>