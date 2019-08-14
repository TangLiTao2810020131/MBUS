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
    <title>系统管理-房间-新增</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
    <style>
        .layui-form-item
        {
            margin-bottom: 10px;
        }
    </style>
</head>
<body style="background: #fff; overflow-x: hidden;" class="innerBody">
    <div class="layui-form layui-form-pane ml30" style="margin-top: 20px;">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">
                    房间编号</label>
                <div class="layui-input-inline">
                	<input type="hidden" name="parentid" id="parentid" value="${parentid}" class="layui-input" >
                    <input type="text" name="code" id="code" class="layui-input" lay-verify="required|code" maxlength="20">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">
                    房间号</label>
                <div class="layui-input-inline">
                    <input type="text" name="name" id="name" class="layui-input" lay-verify="required|name" maxlength="20">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">房间类型</label>
                <div class="layui-input-inline" >
                    <select name="roomType" id="roomType">
                        <option value="">--请选择房间类型(可选)--</option>
                        <c:forEach items="${roomTypeList}" var="roomType">
                            <option value="${roomType.id}">${roomType.type_name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>

        <div class="layui-form-item layui-form-text" style="width: 623px;">
            <label class="layui-form-label">
                备注</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入内容(最多200字符)" maxlength="200" name="remark" id="remark" class="layui-textarea" style="resize: none;"></textarea>
            </div>
        </div>
        <div class="layui-form-item window-block-btn">
            <div class="layui-input-block">
                <button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 47%">
                    取消</button>
                <button type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn" id="confirmBtn">
                    确定</button>
            </div>
        </div>
    </div>
</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function () {
        var $ = layui.jquery //重点处
		, layer = layui.layer;

        //得到父页面的id值

        var index = (window.parent.index_);
        console.log(index);
        //检查公寓编号的唯一性
        function isCheckRoomCode(obj){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/room/isCheckRoomCode.action", //路径
                data : obj, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        }
        //检查房间号在楼栋下的唯一性
        function isCheckRoomNum(obj){
            var mmmm;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/room/isCheckRoomNum.action", //路径
                data : obj, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    mmmm = result;
                }
            });
            return mmmm;
        }

        layui.use('form', function () {
            var form = layui.form;

            //------授权提交-------
            form.on('submit(confirmBtn)', function (data) {
                var obj = data.field;
                console.log(obj);
                /*var roomType=$('#roomType').val();*/
                var num=isCheckRoomCode(obj);
                if(num != 0 && num != -1) {
                    layer.msg('房间编号已存在,请重新输入！', {
                        icon: 7,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        $("#code").focus();
                        $("#confirmBtn").attr('disabled', false);
                    });
                    return false;
                }
                var mmmm=isCheckRoomNum(obj);
                if(mmmm != 0 && mmmm != -1) {
                    layer.msg('房间号已存在,请重新输入！', {
                        icon: 7,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        $("#name").focus();
                        $("#confirmBtn").attr('disabled', false);
                    });
                    return false;
                }
                //以下是ajax
			     $.ajax({
			     	type: 'POST',
			     	data:obj,//参数
			     	url: "${ctx}/room/save.action?roomTypeId="+$('#roomType').val(),
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
            });                
            //取消
            $("body").on("click", "#canleBtn", function () {
                parent.layer.close(index); //关闭layer
            });
        });
    });
    function closes(){
        var index = window.parent.layer.getFrameIndex(window.name); //获取窗口索引
        window.parent.layer.close(index);
        window.parent.raloadts();
    }
   
</script>
</html>
