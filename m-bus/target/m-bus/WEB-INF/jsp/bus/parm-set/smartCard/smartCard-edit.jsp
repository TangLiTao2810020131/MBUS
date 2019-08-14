<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <title>参数设置-一卡通终端-编辑</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
  <style>
	  .layui-form-item{
		  margin-top: 20px !important;
		  margin-left:50px !important;
	  }
  </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<br/>
<div class="layui-form layui-form-pane ml30" style="margin-top: 20px;">
	<input type="hidden" id="flag1" value="${cv.terminalNum}"/>
	<input type="hidden" id="flag2" value="${cv.terminalAddress}"/>
	<form id="formHtml" action="" method="post">
		<input type="hidden" name="id" value="${cv.id}"/>
		<input type="hidden" value="${cv.terminalType}" id="terminalType"/>
		<input type="hidden" value="${cv.terminalStatus}" id="terminalStatus"/>
		<div class="layui-form layui-form-pane ml30" style="margin-top: 20px;">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">
						终端编号</label>
					<div class="layui-input-inline">
						<input type="text" value="${cv.terminalNum}" lay-verify="required|terminalNum" oninput="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" id="terminalNum" name="terminalNum" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">
						IP地址</label>
					<div class="layui-input-inline">
						<input type="text" name="terminalAddress"  lay-verify="required|terminalAddress" value="${cv.terminalAddress}" id="terminalAddress" class="layui-input">
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">
						心跳周期</label>
					<div class="layui-input-inline">
						<input type="text" name="heartbeatTime" id="heartbeatTime" oninput="this.value=this.value.replace(/\D/g,'')" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20" value="${cv.heartbeatTime}">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">
						采集时间</label>
					<div class="layui-input-inline">
						<input type="text" name="collectTime" id="collectTime" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20" value="${cv.collectTime}">
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">
						终端类型</label>
					<div class="layui-input-inline">
						<select name="terminalType" lay-filter="aihao" lay-verify="required">
							<option value="0">购水终端</option>
							<option value="1">退水终端</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">
						终端状态</label>
					<div class="layui-input-inline">
						<select name="terminalStatus" lay-filter="aihao" lay-verify="required">
							<option value="0">在线</option>
							<option value="1">离线</option>
						</select>
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">
						终端功能</label>
					<div class="layui-input-inline">
						<input type="text" name="terminalFun" lay-verify="required" id="terminalFun" class="layui-input" value="${cv.terminalFun}">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">
						水量下限</label>
					<div class="layui-input-inline">
						<input type="text" name="minWater" id="minWater" lay-verify="required" oninput="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" class="layui-input" value="${cv.minWater}">
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">
						卡底金</label>
					<div class="layui-input-inline">
						<input type="text" name="minMoney" lay-verify="required" oninput="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" id="minMoney" class="layui-input" value="${cv.minMoney}">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">
						日限金额</label>
					<div class="layui-input-inline">
						<input type="text" name="dayRestrict" lay-verify="required" oninput="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" id="dayRestrict" class="layui-input" value="${cv.dayRestrict}">
					</div>
				</div>
			</div>

			<div class="layui-form-item window-block-btn">
				<div class="layui-input-block">
					<button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 50%">
						取消</button>
					<a type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">
						确定</a>
				</div>
			</div>
		</div>
	</form>
</div>
</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">


    layui.use('laydate', function(){
        var laydate = layui.laydate;

        // //执行一个laydate实例
        // laydate.render({
        //     elem: '#heartbeatTime' //指定元素
        //     ,type:'datetime',
        //     max:new Date()+''
        // });

        //执行一个laydate实例
        laydate.render({
            elem: '#collectTime' //指定元素
            ,type:'datetime',
            max:new Date()+''
        });
    });


    //主动加载jquery模块
	layui.use(['jquery', 'layer','form'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;

        var form = layui.form;
	
		//得到父页面的id值
	
		var index = (window.parent.index_);
		console.log(index);

		//表单验证
        form.verify({
            terminalNum:function(value,ele){
                if(value.trim()!=$("#flag1").val()){
                    $.ajax({
                        url:'${ctx}/smartcard/checkTerminalNum.action',
                        data:{terminalNum:value},
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
                        return "终端编号已经存在！";
                    }
				}
			},
            terminalAddress:function(value, item){
                if(value.trim()!=$("#flag2").val()){
                    if(!/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value)){
                        return "IP格式不正确！"
                    };
                    $.ajax({
                        url:'${ctx}/smartcard/checkTerminalAddress.action',
                        data:{terminalAddress:value},
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
                        return "终端IP地址已经存在！";
                    }
                }
            }
        });


        layui.use('form', function(){
            var form = layui.form;
		 	//------授权提交-------
                form.on('submit(confirmBtn)', function (data) {
                    var obj = data.field;
                    $.ajax({
                        url:"${ctx}/smartcard/editSmartCard.action",
                        data:$("#formHtml").serialize(),
                        dataType:"json",
                        type:"post",
                        success:function(result) {
                            layer.msg("编辑成功!",{
                                icon:1,
                                time:2000
                            },function(){
                                parent.layer.close(index);//关闭layer
                                $('#iframe',parent.document).attr('src', '${ctx}/smartcard/smartCard.action');
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
			//取消
			$("body").on("click","#canleBtn",function(){
				parent.layer.close(index);//关闭layer
			});
        });


        //设置select初始选中
        $(function() {
            //终端类型select
            $("[name='terminalType'] option").each(function () {
                var str = $(this).val();
                if (str == $("#terminalType").val()) {
                    $(this).attr("selected", true);
                }
            });

            //终端状态select
            $("[name='terminalStatus'] option").each(function () {
                var str = $(this).val();
                if (str == $("#terminalStatus").val()) {
                    $(this).attr("selected", true);
                }
            });

            //重新渲染表单
            form.render();
        });
	});
</script>
</html>