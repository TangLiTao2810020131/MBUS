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
    <title>报表查询-补水记录-查看</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
    <style>
        .layui-form-item{margin-bottom: 10px;}
        [type='text']{
            border:none;
        }
        [title]{
            border:none !important;
            text-align:right !important;
        }
    </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top:40px;margin-left:80px;" lay-filter="updForm">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="流水号">流水号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.serialNumber}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作员">操作员：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.operatName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="区域">区域：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.areaName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="公寓">公寓：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.apartmentName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="楼层">楼层：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.floorName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="房间号">房间号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.roomNum}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="交易类型">交易类型：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.transacType}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作金额">操作金额：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.operatMoney}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作水量">操作水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.operatWater}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作状态">操作状态：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.currentStatusName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">

        <div class="layui-inline">
            <label class="layui-form-label label-120" title="是否冲红">是否冲红：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.hasRedrushName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
</div>
    <div class="layui-form-item">

        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作时间">操作时间：</label>
            <div class="layui-input-inline">
                <input type="text" value="<fmt:formatDate value='${br.operatTime}' pattern='yyyy-MM-dd HH:mm:ss' />" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>

    </div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
</html>
