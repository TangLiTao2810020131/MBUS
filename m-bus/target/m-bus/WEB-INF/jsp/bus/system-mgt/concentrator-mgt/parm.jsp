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
    <title>系统管理-集中器管理-发参数</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<form class="layui-form" action="" id="formHtml" style="width: ;margin-top: 20px;" lay-filter="updForm">
    <p style="text-align:center">请选择</p>
    <div class="layui-form-item">
        <input type="hidden" name="concentratorId" value="${concentratorId}">
        <div class="layui-input-block" style="width: 350px;margin-left: 35px;">
            <input type="radio" name="type" value="0" title="更新集中器和水表中的系统参数" checked="">
        </div>
        <div class="layui-input-block" style="width: 350px;margin-left: 35px;">
            <input type="radio" name="type" value="1" title="仅更新集中器中的系统参数">
        </div>
    </div>
    <div class="layui-form-item  window-block-btn">
        <div class="layui-input-block">
            <button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn">取消</button>
            <button type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">确定</button>
        </div>
    </div>
</form>

</body>

<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript">
    //主动加载jquery模块
    var $ = layui.jquery //重点处
    layui.use(['jquery', 'layer'], function () {
        layer = layui.layer;
        //得到父页面的id值
        var index = (window.parent.index_);
        console.log(index);

        layui.use('form', function () {
            var form = layui.form;
            //------确认-------
            form.on('submit(confirmBtn)', function (data) {
                var indexMsg = layer.msg('提交中，请稍后...', {icon: 16, time: false, shade: 0});
                var obj = data.field;
                $.ajax({
                    type: "POST",
                    async: false,
                    data: obj,
                    url: '${ctx}/concentratormgt/setParamConfirm.action',
                    success: function (data) {
                        layer.close(indexMsg);
                        layer.msg(data.msg);
                        if (data.status == "success") {
                            setTimeout("flutable()",2500);
                        }
                    }
                });

                /*添加成功之后，关闭弹窗，刷新父页面*/
                //关闭layer
                // $('#iframe', parent.document).attr('src', '{ctx}/concentratormgt/concentratorMgt.action');
            });
            //取消
            $("body").on("click", "#canleBtn", function () {
                parent.layer.close(index);//关闭layer
            })
        })
    })

    function flutable() {
        $("#canleBtn").click();
        // parent.location.reload();
    }

</script>
</html>