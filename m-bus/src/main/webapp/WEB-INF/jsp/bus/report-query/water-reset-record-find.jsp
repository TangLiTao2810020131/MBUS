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
    <title>报表查询-清零记录-查看</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
    <style>
        .layui-form-item{margin-bottom: 10px;margin-left:50px;}
        [type='text']{
            border:none;
        }
        [title]{
            border:none !important;
            text-align:right !important;
            width:150px !important;
        }
        .layui-inline{
            margin-left:-50px !important;
        }
    </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top:40px;margin-left:60px;" lay-filter="updForm">
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
            <label class="layui-form-label label-120" title="楼栋">楼栋：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.buildName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="楼层">楼层：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.floorName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="房间号">房间号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.roomNum}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="类型">类型：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.typeName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="清零前补水量">清零前补水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.supplementWater}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="清零前购水量">清零前购水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.buyWaterTotal}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="清零前退水量">清零前退水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.returnWater}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="清零前已用水量">清零前已用水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.userWater}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作人">操作人：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.userName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="状态">状态：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.currentStatusName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="房间类型">房间类型：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.roomTypeName}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="创建时间">创建时间：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.createTime}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>


</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
</html>

