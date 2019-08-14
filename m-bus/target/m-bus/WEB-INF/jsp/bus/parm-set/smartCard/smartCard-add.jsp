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
    <title>参数设置-一卡通终端-新增</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
    <style>
        .layui-form-item{
            margin-top: 20px !important;
            margin-left:100px !important;
        }
    </style>
</head>
<body style="background: #fff; overflow-x: hidden;" class="innerBody">
    <br/>
    <form id="formHtml" action="" method="post" class="layui-form layui-form-pane ml30" style="">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">
                        终端编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="terminalNum" id="terminalNum" class="layui-input" oninput="this.value=this.value.replace(/\D/g,'')" lay-verify="required|terminalNum" maxlength="20" placeholder="请输入终端编号">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        IP地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="terminalAddress" id="terminalAddress" class="layui-input" lay-verify="required|terminalAddress" maxlength="20" placeholder="请输入IP地址">
                    </div>
                </div>
            </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">
                    心跳周期</label>
                <div class="layui-input-inline">
                    <input type="text" name="heartbeatTime" id="heartbeatTime"  lay-verify="required" oninput="this.value=this.value.replace(/\D/g,'')" autocomplete="off" class="layui-input" maxlength="20" placeholder="请输入心跳周期(秒)">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">
                    采集时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="collectTime" id="collectTime" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20" placeholder="请选择采集时间">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">
                        终端类型</label>
                    <div class="layui-input-inline">
                        <select name="terminalType" lay-filter="aihao"  lay-verify="required">
                            <option value="">请选择终端类型</option>
                            <option value="0">购水终端</option>
                            <option value="1">退水终端</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        终端状态</label>
                    <div class="layui-input-inline">
                        <select name="terminalStatus" lay-filter="aihao" lay-verify="required">
                            <option value="">请选择终端状态</option>
                            <option value="0">在线</option>
                            <option value="1">离线</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">
                        终端功能</label>
                    <div class="layui-input-inline">
                        <input type="text" name="terminalFun" id="terminalFun" class="layui-input"  lay-verify="required" maxlength="20" placeholder="请输入终端功能">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        水量下限</label>
                    <div class="layui-input-inline">
                        <input type="text" name="minWater" id="minWater" oninput="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" class="layui-input"  lay-verify="required" placeholder="请输入水量下限（吨）" maxlength="20">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">
                        卡底金</label>
                    <div class="layui-input-inline">
                        <input type="text" name="minMoney" id="minMoney" oninput="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" class="layui-input" placeholder="请输入卡底金（元）" maxlength="20">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        日限金额</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dayRestrict" id="dayRestrict" oninput="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" class="layui-input" placeholder="请输入日限金额（元）" maxlength="20">
                    </div>
                </div>
            </div>

            <div class="layui-form-item window-block-btn">
                <div class="layui-input-block">
                    <button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 50%">
                        取消</button>
                    <a type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">
                        确定</a>
                </div>
            </div>
    </form>
</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">


    layui.use('laydate', function(){
        var laydate = layui.laydate;

        // //执行一个laydate实例
        // laydate.render({
        //     elem: '#heartbeatTime' //指定元素
        //     ,type:'datetime',
        //     max:new Date()+''
        // });

        //执行一个laydate实例
        laydate.render({
            elem: '#collectTime' //指定元素
            ,type:'datetime',
            max:new Date()+''
        });
    });


    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function () {
        var $ = layui.jquery //重点处
		, layer = layui.layer;

        //得到父页面的id值

        var index = (window.parent.index_);
        console.log(index);

        layui.use('form', function () {
            var form = layui.form;

            //表单验证
            var flag='';
            form.verify({
                terminalNum:function(value, item){
                    $.ajax({
                        url:'${ctx}/smartcard/checkTerminalNum.action',
                        data:{terminalNum:value},
                        dataType:'json',
                        type:'post',
                        async:false,
                        success:function(result){
                           flag=result;
                        },error:function(){
                            layer.msg("服务器异常!",{
                                icon:7,
                                time:2000
                            });
                        }
                    });
                    if(flag==1){
                        return "终端编号已经存在！";
                    }
                },
                terminalAddress:function(value, item){
                    if(!/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value)){
                        return "IP格式不正确！"
                    };
                    $.ajax({
                        url:'${ctx}/smartcard/checkTerminalAddress.action',
                        data:{terminalAddress:value},
                        dataType:'json',
                        type:'post',
                        async:false,
                        success:function(result){
                            flag=result;
                        },error:function(){
                            layer.msg("服务器异常!",{
                                icon:7,
                                time:2000
                            });
                        }
                    });
                    if(flag==1){
                        return "终端IP地址已经存在！";
                    }
                }
            });

            //------授权提交-------
            form.on('submit(confirmBtn)', function (data) {
                var obj = data.field;
                $.ajax({
                    url:"${ctx}/smartcard/addSmartCard.action",
                    data:$("#formHtml").serialize(),
                    dataType:"json",
                    type:"post",
                    success:function(result) {
                        layer.msg("添加成功!",{
                            icon:1,
                            time:2000
                        },function(){
                            parent.layer.close(index);//关闭layer
                            $('#iframe',parent.document).attr('src', '${ctx}/smartcard/smartCard.action');
                        });
                    },
                    error:function(result){
                        layer.msg("服务器异常!",{
                            icon:7,
                            time:2000
                        });
                    }
                });
            });
            //取消
            $("body").on("click", "#canleBtn", function () {
                parent.layer.close(index); //关闭layer
            })
        });
    });
   
</script>
</html>
