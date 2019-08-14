<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>区域详情</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
    <style type="text/css">
        .layui-form-label{width:90px;}
        .layui-input-block{ margin-left:120px;}

         .layui-layer-content {
             border: none;
             box-shadow: 0px 0px 0px #000 inset;
         }
    </style>
</head>
<body>
<div class="layui-body layui-bg-white">
    <div class="layui-fluid">
        <div class="layui-row  layui-col-space15">
            <fieldset class="layui-elem-field layui-field-title"
                      style="margin-top: 12px;">
                <legend style="font-size:15px;font-weight:bold;">区域</legend>
            </fieldset>


                <!-- 顶部切换卡 -->
                <!--ul的id要和lay-filter一致-->
                <div class="layui-tab" lay-filter="main_tab1">
                    <div class="layui-tab-content">
                        <div class="layui-tab-item layui-show">
                            <div class="layui-form-item">
                                <label class="layui-form-label">区域编号:</label>
                                <label class="layui-form-mid">
                                    ${region.code }
                                </label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">区域名称:</label>
                                <label class="layui-form-mid">
                                    ${region.name }
                                </label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">备注:</label>
                                <label class="layui-form-mid">
                                    ${region.remark }
                            </div>
                        </div>
                    </div>
                </div>


        </div>
    </div>
</div>

<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script>
    layui.use('element', function(){
        var element = layui.element;
    });

    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
    });






</script>
</body>
</html>
