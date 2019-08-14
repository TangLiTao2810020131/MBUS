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
    <title>水表管理-水表信息-查看</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
    <style type="text/css">
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
<body style="background:#fff;overflow-x:hidden ;margin-top:10px;" class="innerBody">

<form class="layui-form layui-form-pane ml30" action="" id="formHtml"    lay-filter="updForm">
            <c:if test="${roomWaterMeterVo.regionName != null}">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label label-120" title="区域名称">区域名称：</label>
                        <div class="layui-input-inline">
                            <input type="text" value="${roomWaterMeterVo.regionName}" autocomplete="off" class="layui-input" disabled>
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label label-120" title="公寓名称">公寓名称：</label>
                        <div class="layui-input-inline">
                            <input type="text" value="${roomWaterMeterVo.apartmentName}" autocomplete="off" class="layui-input" disabled>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label label-120" title="楼栋号">楼栋号：</label>
                        <div class="layui-input-inline">
                            <input type="text" value="${roomWaterMeterVo.floorName}" autocomplete="off" class="layui-input" disabled>
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label label-120" title="楼层名称">楼层名称：</label>
                        <div class="layui-input-inline">
                            <input type="text" value="${roomWaterMeterVo.layerName}" autocomplete="off" class="layui-input" disabled>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                    <label class="layui-form-label label-120" title="房间号">房间号：</label>
                        <div class="layui-input-inline">
                            <input type="text" value="${roomWaterMeterVo.roomNum}" autocomplete="off" class="layui-input" disabled>
                        </div>
                </div>
                </div>
            </c:if>
            <c:if test="${roomWaterMeterVo.regionName == null}">
                <div class="layui-form-item" style="text-align:center">
                    <font style="color: red">未绑定房间</font>
                </div>
            </c:if>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label label-120" title="水表编号">水表编号：</label>
                    <div class="layui-input-inline">
                        <input type="text" value="${roomWaterMeterVo.water_meter_id}" autocomplete="off" class="layui-input" disabled>
                    </div>
                </div>

                <div class="layui-inline" >
                    <label class="layui-form-label label-120" title="水表类型">水表类型：</label>
                    <div class="layui-input-inline">
                        <input type="text" value="<c:if test="${roomWaterMeterVo.type == 0}">冷水水表</c:if><c:if test="${roomWaterMeterVo.type == 1}">生活热水表</c:if>" autocomplete="off" class="layui-input" disabled>

                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label label-120" title="更新时间">更新时间：</label>
                <div class="layui-input-inline">
                    <input type="text" value="${roomWaterMeterVo.update_time}" autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label label-120" title="创建时间">创建时间：</label>
                <div class="layui-input-inline">
                    <input type="text" value="${roomWaterMeterVo.create_time}" autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            </div>
</form>

</body>
</html>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
