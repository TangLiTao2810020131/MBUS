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
    <title>系统管理-集中器</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
    <style>
        .layui-form-item{margin-bottom: 10px;}
    </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top: 40px;" lay-filter="updForm">
    <input type="hidden" value="${cc.id}" name="id"/>
    <input type="hidden" value="${cc.apartment_id}" id="apartment_id"/>
    <input type="hidden" value="${cc.collect_id}" id="collect_name"/>
    <input type="hidden" value="${cc.communication_mode}" id="communication_mode"/>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="公寓名称">公寓名称</label>
            <div class="layui-input-inline">
                <select name="apartment_id">
                    <c:forEach items="${tList}" var="t">
                        <option value="${t.id}">${t.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器编码">集中器编号</label>
            <div class="layui-input-inline">
                <input type="text" id="concentrator_num" name="concentrator_num" value="${cc.concentrator_num}" oninput = "value=value.replace(/[^\d]/g,'')" maxlength="16" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器IP地址">IP地址</label>
            <div class="layui-input-inline">
                <input type="text" name="ip_address" value="${cc.ip_address}"  maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器密码">集中器密码</label>
            <div class="layui-input-inline">
                <input type="text" name="concentrator_pwd"  maxlength="20" oninput = "value=value.replace(/[^\d]/g,'')" value="${cc.concentrator_pwd}" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="集中器版本">集中器版本</label>
            <div class="layui-input-inline">
                <input type="text" name="concentrator_version"  maxlength="20" value="${cc.concentrator_version}" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="通讯模式">通讯模式</label>
            <div class="layui-input-inline">
                <select name="communication_mode">
                    <option value="0">TCP</option>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="更新时间">更新时间</label>
            <div class="layui-input-inline">
                <input type="text" name="update_time"  value="${cc.update_time}" id="updateTime" lay-verify="required" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label-120" title="采集名称">采集名称</label>
            <div class="layui-input-inline">
                <select name="collect_name">
                    <c:forEach items="${cList}" var="c">
                        <option value="${c.id}">${c.collect_name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item layui-form-text" style="width:624px;">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入内容(最多200个字符)" name="remark" class="layui-textarea"  maxlength="200">${cc.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item window-block-btn">
        <div class="layui-input-block">
            <button type="reset" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 47%">取消</button>
            <a type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">确定</a>
        </div>
    </div>
</form>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            type:'datetime'
            ,elem: '#updateTime' //指定元素
            ,  trigger: 'click'//采用click弹出
            ,max:new Date()+""
        });
    });

    //主动加载jquery模块
    layui.use(['jquery', 'layer','form'], function(){
        var $ = layui.jquery //重点处
            ,layer = layui.layer
            ,form=layui.form;
        //得到父页面的id值
        var index = (window.parent.index_);
        console.log(index);

        layui.use('form', function(){
            var form = layui.form;
            //------确认添加分区-------
            form.on('submit(confirmBtn)', function (data) {
                var obj = data.field;
                var reg=/^\d{16}$/;
                // if(!reg.test($("#concentrator_num").val())){
                //     layer.msg('集中器编号只能输入16位数字！',{
                //         icon:7,
                //         time:2000
                //     });
                //     return false ;
                //
                // }
                //以下是ajax
                $.ajax({
                    url:"${ctx}/concentrator/editConcentrator.action",
                    data:$("#formHtml").serialize(),
                    dataType:"json",
                    type:"post",
                    success:function(result) {
                        layer.msg("编辑成功!",{
                            icon:1,
                            time:2000
                        },function(){
                            parent.location.reload();
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
            $("body").on("click","#canleBtn",function(){
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            });
        })

        //设置select初始选中
        $(function(){
            //区域select
            $("[name='apartment_id'] option").each(function(){
                var str=$(this).val();
                if(str==$("#apartment_id").val())
                {
                    $(this).attr("selected",true);
                }
            });
            //采集名称select
            $("[name='collect_name'] option").each(function(){
                var str=$(this).val();
                if(str==$("#collect_name").val())
                {
                    $(this).attr("selected",true);
                }
            });
            //通讯模式select
            $("[name='communication_mode'] option").each(function(){
                var str=$(this).val();
                if(str==$("#communication_mode").val())
                {
                    $(this).attr("selected",true);
                }
            });

            //重新渲染表单
            form.render();
        });
    });

</script>
</html>