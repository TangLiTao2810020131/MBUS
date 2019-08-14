<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <title>水表管理-补水管理-按导入补水</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend class="window-leg">导入文件</legend>
</fieldset>
<div class="layui-btn-group" style="position: relative;top: 0;left: 50px;">
    <button type="button" class="layui-btn" id="test1">
        <i class="layui-icon">&#xe67c;</i>上传文件
    </button>
    <button class="layui-btn" id="downBtn">下载模板</button>
</div>

<div class="import-msg" style="display:none;" id="msg_id">
    <p><span style="font-weight: bold;">提示：</span>导入文件中有<span style="color: red;" id="num_id"></span> 条数据异常。请修正后重新选择。</p>
    </br>
    <div id="error_msg"></div>
</div>
<%--<div class="bottomBtn" style="border-top: 1px solid #e6e6e6;">--%>
	<%--<button type="button" class="layui-btn" lay-submit="" lay-filter="importBtn" id="importBtn">导入</button>--%>
	<%--<button type="button" class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>--%>
<%--</div>--%>


</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
	 //主动加载jquery模块
	layui.use(['jquery', 'layer'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;
		var index = (window.parent.index_);
		console.log(index);
		
		//取消
		$("body").on("click","#cancelBtn",function(){
			parent.layer.close(index);//关闭layer
		});

		//下载模版
        $(function(){
            $("#downBtn").on("click",function(){
                var url="${ctx}/wateraddmgt/download.action";
                window.location.href=url;
            });
        });

        //文件上传
        layui.use(['upload','layer'], function(){
            var upload = layui.upload
                 layer=layui.layer;
            var uploadInst = upload.render({
                elem: '#test1' //绑定元素
                ,url: '${ctx}/wateraddmgt/excelUpload.action'
                ,accept:'file'
                ,exts:'xlsx|xls'
                ,done: function(res){
                    if(res.status == "success"){
                        $("#msg_id").hide();
                        $("#error_msg").empty();
                        layer.msg('补水成功！',{
                            time:3000,
                            icon:1
                        });
                    }else{
                        $("#error_msg").empty();
                        $("#msg_id").show();
                        $("#num_id").html(res.body.length);
                        var itemHtml="";
                        for(var i=0; i<res.body.length; i++){
                            itemHtml+='<span style="color: red;"></span>'+ res.body[i] +'</p>'
                        }
                        $("#error_msg").append(itemHtml);
                        layer.msg('补水失败！',{
                            time:3000,
                            icon:1
                        });
                    }

                }
                ,error: function(){
                    layer.msg('服务器异常！',{
                        time:3000,
                        icon:7
                    });
                }
            });
        });
	});
</script>
</html>