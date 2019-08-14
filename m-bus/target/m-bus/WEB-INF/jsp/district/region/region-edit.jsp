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
  <title>系统管理-区域-编辑</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
  <style>
  	.layui-form-item{margin-bottom: 10px;}
  </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<div class="layui-form layui-form-pane ml30" style="margin-top: 20px;">
	<div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">区域编号</label>
	      <div class="layui-input-inline">
	      	<input type="hidden" name="id" id = "id" class="layui-input">
	        <input type="text" name="code" id = "code" class="layui-input" maxlength="20">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">区域名称</label>
	      <div class="layui-input-inline">
	        <input type="text" name="name" id = "name"  class="layui-input" lay-verify="required|name" maxlength="20">
	      </div>
	    </div>
  	</div>
		<div class="layui-form-item layui-form-text" style="width: 623px;">
	    <label class="layui-form-label">备注</label>
	    <div class="layui-input-block">
	      <textarea placeholder="请输入内容" name="remark" id = "remark" class="layui-textarea" style="resize:none;"></textarea>
	    </div>
   </div>
  	<div class="layui-form-item window-block-btn">
	    <div class="layui-input-block">
	    	<button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 47%">取消</button>
	      <button type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">确定</button>
	      
	    </div>
 	</div>
</div>

</body>

	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">

	//初始化数据
	$(function(){
		var index = (window.parent.index_);
		var id = (window.parent.id_);
		initdata(id);
	});
	 //主动加载jquery模块
	layui.use(['jquery', 'layer'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;
	
		//得到父页面的id值
		var index = (window.parent.index_);
		var id = (window.parent.id_);

        //检查区域编号的唯一性
        function isCheckRegionCode(obj){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/region/isCheckRegionCode.action", //路径
                data : obj, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        }
		
		layui.use('form', function(){  
		 	var form = layui.form;
		 	//------授权提交-------
			form.on('submit(confirmBtn)', function (data) {
                var obj = data.field;
                var code=$("#code").val();
                var name=$("#name").val();
                if(code == '${region.code}'){
                    //以下是ajax
                    $.ajax({
                        type: 'POST',
                        data:obj,//参数
                        url: "${ctx}/region/update.action",
                        dataType : "json",
                        success: function(result){
                            layer.msg(result, {
                                icon: 1,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function(){
                                closes();
                            });
                        }
                    });

				}else {
                    var num=isCheckRegionCode(obj);
                    if(num != 0 && num != -1) {
                        layer.msg('区域编号已存在,请重新输入！', {
                            icon: 7,
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function () {
                            $("#code").focus();
                            $("#confirmBtn").attr('disabled', false);
                        });
                        return false;
                    }

                    //以下是ajax
                    $.ajax({
                        type: 'POST',
                        data:obj,//参数
                        url: "${ctx}/region/update.action",
                        dataType : "json",
                        success: function(result){
                            layer.msg(result, {
                                icon: 1,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function(){
                                closes();
                            });
                        }
                    });

				}

			});
			//取消
			$("body").on("click","#canleBtn",function(){
				parent.layer.close(index);//关闭layer
			})
	
		})	
	});
	
	//初始化数据
	function initdata(id){
		$.ajax({
			type: 'POST',
			data:{id:id},//参数
			url: "${ctx}/region/info.action",
			dataType : "json",
			success: function(result){
				$("#id").val(result.id);
				$("#code").val(result.code);
				$("#name").val(result.name);
				$("#remark").val(result.remark);
			}
		});
	}
    function closes(){
        var index = window.parent.layer.getFrameIndex(window.name); //获取窗口索引
        window.parent.layer.close(index);
        window.parent.raloadts();
    }
   
</script>
</html>