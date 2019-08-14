<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>水表管理-购水管理-现金购水</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css"/>
    <style>
        .layui-form-item .layui-inline {
            margin-right: 55px;
        }

        .layui-form-item {
            margin-bottom: 10px;
        }
    </style>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend class="window-leg">基本信息</legend>
</fieldset>
<div class="layui-form layui-form-pane ml30">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label nokuangzi">公寓名称：</label>
            <div class="layui-input-inline">
                <input type="text" value="${waterMeterInfo.apartmentName}" readonly="readonly" disabled="disabled"
                       class="layui-input nokuangzi">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label nokuangzi">房间编号：</label>
            <div class="layui-input-inline">
                <input type="text" value="${waterMeterInfo.roomNum}" readonly="readonly" disabled="disabled"
                       class="layui-input nokuangzi">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label nokuangzi">集中器：</label>
            <div class="layui-input-inline">
                <input type="text" value="${waterMeterInfo.moduleStatusName}" id="moduleStatusName" readonly="readonly" disabled="disabled"
                       class="layui-input nokuangzi">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label nokuangzi">采集时间：</label>
            <div class="layui-input-inline">
                <c:choose>
                    <c:when test="${empty waterMeterInfo.collectTime}">
                        <input type="text"
                               value="- - - -"
                               readonly="readonly" disabled="disabled" class="layui-input nokuangzi">
                    </c:when>
                    <c:otherwise>
                        <input type="text"
                               value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${waterMeterInfo.collectTime}"></fmt:formatDate>"
                               readonly="readonly" disabled="disabled" class="layui-input nokuangzi">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label nokuangzi">剩余水量：</label>
            <div class="layui-input-inline">
                <input type="text" value="${waterMeterInfo.surplusWater}" readonly="readonly" disabled="disabled"
                       class="layui-input nokuangzi">
            </div>
            <span class="unit_">（吨）</span>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label marginL nokuangzi">水量单价：</label>
            <div class="layui-input-inline">
                <input type="text" id="price" value="${waterMeterInfo.price}" readonly="readonly" disabled=""
                       class="layui-input nokuangzi">
            </div>
            <span class="unit_">（元）</span>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label nokuangzi" style="">最大购水：</label>
            <div class="layui-input-inline">
                <input type="text" value="${waterMeterInfo.allowWater}" readonly="readonly" disabled="disabled"
                       class="layui-input nokuangzi">
            </div>
            <span class="unit_">（吨）</span>
        </div>
    </div>
</div>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend class="window-leg">购水信息</legend>
</fieldset>


<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top: 20px;" lay-filter="updForm">
    <input type="hidden" name="waterMeterInfoId" value="${waterMeterInfo.id}">
    <input type="hidden" id="allowWater" value="${waterMeterInfo.allowWater}">
    <input type="hidden" name="concentratorNum" value="${waterMeterInfo.concentratorNum}">

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">购水人姓名</label>
            <div class="layui-input-inline">
                <input type="text" id="payerName" name="payerName" lay-verify="required" autocomplete="off"
                       class="layui-input" maxlength="20">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">购水金额</label>
            <div class="layui-input-inline">
                <input type="text" id="buyMoney" name="buyMoney" onchange="changeBuyMoney()"
                       lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="10"
                       onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)">
            </div>
            <span class="unit_">（元）</span>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">购买水量</label>
            <div class="layui-input-inline">
                <input type="text" id="buyWater" name="buyWater" onchange="changeBuyWater()"
                       lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="10"
                       onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)">
            </div>
            <span class="unit_">（吨）</span>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label marginL">实收金额</label>
            <div class="layui-input-inline">
                <input type="text" id="actualMoney" name="actualMoney" onchange="changeActualMoney()"
                       lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="10"
                       onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)">
            </div>
            <span class="unit_">（元）</span>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">找零</label>
            <div class="layui-input-inline">
                <input type="text" id="returnMoney" name="returnMoney" lay-verify="number" disabled="disabled" placeholder="0.00"
                       autocomplete="off" class="layui-input">
            </div>
            <span class="unit_">（元）</span>
        </div>
    </div>
    <div class="layui-form-item window-block-btn">
        <div class="layui-input-block">
            <button style="margin-right: 50%;" type="button" class="layui-btn layui-btn-primary window-btn"
                    id="canleBtn">取消
            </button>
            <button type="button" id="confirm_id" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">
                确定
            </button>
        </div>
    </div>
</form>


</body>

<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript" src="${ctx}/resources/water_meter_manage/waterMeterManage.js"></script>
<script type="text/javascript">
    //主动加载jquery模块
    var $ = layui.jquery;//重点处
    layui.use(['jquery', 'layer'], function () {
        layer = layui.layer;
        table = layui.table;

        //得到父页面的id值
        var id = (window.parent.id_);
        var index = (window.parent.index_);
        console.log(index + '、' + id);

        layui.use('form', function () {
            var form = layui.form;
            //------授权提交-------
            form.on('submit(confirmBtn)', function (data) {
                var obj = data.field;
                if (Number(data.field.buyWater) <= 0) {
                    layer.msg("请填写购水量！",{
                        icon:7,
                        time:3000
                    });
                    return;
                }
                if (Number(data.field.buyWater) > Number($("#allowWater").val())) {
                    layer.msg("购水量大于最大购水量！",{
                        icon:7,
                        time:3000
                    });
                    return;
                }
                if (Number(data.field.actualMoney) < Number(data.field.buyMoney)) {
                    layer.msg("实收金额小余购水金额！",{
                        icon:7,
                        time:3000
                    });
                    return;
                }
                if ($("#moduleStatusName").val().trim()=='离线') {
                    layer.msg("集中器处于离线状态！",{
                        icon:7,
                        time:3000
                    });
                    return;
                }
                layer.confirm("确认购水吗？", {btn: ['确定', '取消'], title: "温馨提示"}, function () {

                    $.ajax({
                        type: "POST",
                        async: false,
                        data: obj,
                        url: '${ctx}/waterpurchasemgt/buyConfirm.action',
                        success: function (data) {
                            if(data.msg=='集中器不在线！'){

                            }
                            layer.msg(data.msg);
                            if (data.status == "success") {
                                $("#confirm_id").attr("disabled", true);
                                setTimeout("flutable()", 2500);
                                layer.close(indexMsg);
                            }
                        }
                    });
                });
            });
            //取消
            $("body").on("click", "#canleBtn", function () {
                parent.layer.close(index);//关闭layer
            })
        })
    })

    function changeBuyMoney() {
        var buyMoney = $("#buyMoney").val();
        if (!/^\d+\.?\d{0,2}$/.test(buyMoney)) {
            layer.msg('只能输入数字!');
            $("#buyMoney").val(0);
            $("#buyWater").val(0);
            return;
        }
        var price = $("#price").val();
        $("#buyWater").val(accDiv(buyMoney, price).toFixed(2));
        changeActualMoney()
    }

    function changeBuyWater() {
        var buyWater = $("#buyWater").val();
        if (!/^\d+\.?\d{0,2}$/.test(buyWater)) {
            layer.msg('只能输入数字!');
            $("#buyMoney").val(0);
            $("#buyWater").val(0);
            return;
        }
        var price = $("#price").val();
        $("#buyMoney").val(accMul(buyWater, price).toFixed(2));
        changeActualMoney()
    }

    function changeActualMoney() {
        var actualMoney = $("#actualMoney").val();
        if (!actualMoney) {
            return;
        }
        if (!/^\d+\.?\d{0,2}$/.test(actualMoney)) {
            layer.msg('只能输入数字!');
            $("#actualMoney").val(0);
            $("#returnMoney").val(0);
            return;
        }
        var buyMoney = $("#buyMoney").val();
        $("#returnMoney").val(accSub(actualMoney, buyMoney));
    }

    function flutable() {
        $("#canleBtn").click();
        // parent.location.reload();
    }

</script>
</html>