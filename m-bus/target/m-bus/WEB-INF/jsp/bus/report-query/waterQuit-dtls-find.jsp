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
    <title>报表查询-退水记录-查看</title>
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
                <input type="text" value="${br.serial_number}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="操作人">操作人：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.user_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="区域">区域：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.area_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="公寓">公寓：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.apartment_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="楼栋">楼栋：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.build_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="楼层">楼层：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.floor_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="房间号">房间号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.room_num}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="房间类型">房间类型：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.room_type_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <%--<div class="layui-form-item">--%>
        <%--<div class="layui-inline">--%>
            <%--<label class="layui-form-label label-120" title="退水日期">退水日期：</label>--%>
            <%--<div class="layui-input-inline">--%>
                <%--<input type="text" value="${br.create_time}" autocomplete="off" class="layui-input" disabled>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="layui-inline">--%>
            <%--<label class="layui-form-label label-120" title="更新时间">更新日期：</label>--%>
            <%--<div class="layui-input-inline">--%>
                <%--<input type="text" value="${br.update_time}" autocomplete="off" class="layui-input" disabled>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="退水量">退水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.return_water}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="退水金额">退水金额：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.return_money}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="退水类型">退水类型：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.type_name}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="退水日期">退水日期：</label>
            <div class="layui-input-inline">
                <input type="text" value="${br.create_time}" autocomplete="off" class="layui-input" disabled>
            </div>
        </div>
    </div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
</html>
