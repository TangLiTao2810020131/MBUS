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
            <label class="layui-form-label label-120" title="指令编号">指令编号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${hc.instructionNum}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label label-120" title="指令类型">指令类型：</label>
            <div class="layui-input-inline">
                <input type="text" value="${hc.typeName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
    <div class="layui-inline">
        <label class="layui-form-label label-120" title="下发指令">下发指令：</label>
        <div class="layui-input-inline">
            <textarea style="border-style: none;width: 300px;height: 90px;resize:none" readonly="true">${hc.instructionDetatil}</textarea>
        </div>
    </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="响应结果">响应结果：</label>
            <div class="layui-input-inline">
                <textarea style="border-style: none;width: 300px;height: 90px;resize:none" readonly="true">${hc.result}</textarea>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
    <div class="layui-inline">
        <label class="layui-form-label label-120" title="区域">区域：</label>
        <div class="layui-input-inline">
            <input type="text" value="${hc.areaName}" autocomplete="off" class="layui-input" disabled>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label label-120" title="公寓">公寓：</label>
        <div class="layui-input-inline">
            <input type="text" value="${hc.apartmentName}" autocomplete="off" class="layui-input" disabled>
        </div>
    </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="楼层">楼层：</label>
            <div class="layui-input-inline">
                <input type="text" value="${hc.floorName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="房间号">房间号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${hc.roomNum}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="水表编号">水表编号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${hc.wateMeterNum}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器编号" style="    width: 114px;">集中器编号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${hc.concentratorNum}" style="width: 260px;" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作人">操作人：</label>
            <div class="layui-input-inline">
                <input type="text" value="${hc.operatName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作时间">操作时间：</label>
            <div class="layui-input-inline">
                <input type="text" value ="<fmt:formatDate value='${hc.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
</html>
