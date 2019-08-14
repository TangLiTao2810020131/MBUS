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
    <title>设备管理-历史抄表-查看</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
    <style>
        .layui-form-item{margin-bottom: 10px;margin-right:100px !important;}
        [type='text']{
            border:none;
            width:240px !important;
        }
        [title]{
            border:none !important;
            text-align:right !important;
        }
    </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top:40px;" lay-filter="updForm">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="区域">区域：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.areaName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="公寓">公寓：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.apartmentName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="楼栋">楼栋：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.buildName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="楼层">楼层：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.floorName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="房间号">房间号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.roomNum}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="水表编号">水表编号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.waterMeterNum}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作状态">操作状态：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.currentStatusName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="阀门状态">阀门状态：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.valveStatusName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="模块状态">模块状态：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.moduleStatusName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="抄表日期">抄表日期：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.createTime}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline" style="float:left;">
            <label class="layui-form-label label-120" title="购水量">购水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.buyWaterTotal}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline" style="float:left;">
            <label class="layui-form-label label-120" title="补水量">补水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.supplementWater}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline" style="float:left;">
            <label class="layui-form-label label-120" title="touz">透支水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.overWater}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline" style="float:left;">
            <label class="layui-form-label label-120" title="总用水量">总用水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${nm.userWater}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
</html>

