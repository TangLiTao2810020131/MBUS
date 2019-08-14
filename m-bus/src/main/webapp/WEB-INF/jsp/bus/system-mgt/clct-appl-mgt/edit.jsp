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
  <title>系统管理-采集应用管理-编辑</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
  <style>
  	.layui-form-item{margin-bottom: 10px;}
    [title]{
      width:150px !important;
    }
  </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<input type="hidden" value="${cm.application_number}" id="flag1"/>
<input type="hidden" value="${cm.collect_name}" id="flag2"/>
<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top: 40px;" lay-filter="updForm">
    <input type="hidden" name="id" value="${cm.id}"/>
	<div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="采集应用编号">采集应用编号</label>
      <div class="layui-input-inline">
        <input type="text" name="application_number" value="${cm.application_number}" placeholder="请输入采集编号(数字类型)" lay-verify="required|application_number" autocomplete="off" class="layui-input" oninput="value=value.replace(/[^\d]/g,'')" maxlength="20">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="采集名称">采集名称</label>
      <div class="layui-input-inline">
        <input type="text" name="collect_name" value="${cm.collect_name}" placeholder="请输入采集名称" lay-verify="required|collect_name" autocomplete="off" class="layui-input" maxlength="20">
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="采集IP地址">采集IP地址</label>
      <div class="layui-input-inline">
        <input type="text" name="collect_address" value="${cm.collect_address}" placeholder="请输入采集IP地址" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="自动采集频率">自动采集频率</label>
      <div class="layui-input-inline">
        <input type="text" name="auto_collect_frequency" value="${cm.auto_collect_frequency}" placeholder="请输入自动采集频率" lay-verify="required" autocomplete="off" class="layui-input" oninput="value=value.replace(/[^\-?\d.]/g,'')" maxlength="20">
      </div>
      <span class="unit_">（分钟）</span>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="采集秘钥">采集秘钥</label>
      <div class="layui-input-inline">
        <input type="text" name="collect_key" value="${cm.collect_key}" placeholder="请输入采集密钥" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="秘钥失效时间">秘钥失效时间</label>
      <div class="layui-input-inline">
        <input type="text" name="failure"  value="${cm.failure}" id="time" placeholder="请选择密钥失效时间" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20">
      </div>
    </div>
  </div>
  <div class="layui-form-item  window-block-btn">
    <div class="layui-input-block">
    	<button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 47%">取消</button>
      <button type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn">确定</button>
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
            elem: '#time' //指定元素
            ,type:"datetime"
            ,trigger: 'click' //采用click弹出
            ,max:new Date()+""
        });
    });

	 //主动加载jquery模块
	layui.use(['jquery', 'layer'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;
		//得到父页面的id值
		var index = (window.parent.index_);
		console.log(index);

		layui.use('form', function(){  
		 	var form = layui.form;

		 	//表单验证
            form.verify({
                application_number:function(value, item){
                    if(value.trim()!=$("#flag1").val()){
                        if(!/^[0-9]*$/.test(value.trim()))
                        {
                            return "采集应用编号为纯数字!";
                        }else{
                            var flag=0;
                            $.ajax({
                                url:'${ctx}/clctapplmgt/checkApplicationNumber.action',
                                data:{applicationNumber:value.trim()},
                                dataType:'json',
                                async:false,
                                success:function(result){
                                    if(result=='1')
                                    {
                                        flag=1;
                                    }
                                },
                                error:function(){
                                    layer.msg('服务器异常!',{
                                        icon:7,
                                        time:2000
                                    });
                                }
                            });
                            if(flag==1)
                            {
                                return "应用编号已经存在!";
                            }
                        }
                    }
                },
                collect_name:function(value, item) {
                    if(value.trim()!=$("#flag2").val()){
                        // if(!/^[\u4e00-\u9fa5]{0,}$/.test(value.trim())){
                        //     return "类型名称只能为中文!"
                        // }else
                        {
                            var flag=0;
                            $.ajax({
                                url:'${ctx}/clctapplmgt/checkApplicationName.action',
                                data:{collectName:value.trim()},
                                dataType:'json',
                                async:false,
                                success:function(result){
                                    if(result=='1')
                                    {
                                        flag=1;
                                    }
                                },
                                error:function(){
                                    layer.msg('服务器异常!',{
                                        icon:7,
                                        time:2000
                                    });
                                }
                            });
                            if(flag==1)
                            {
                                return "应用名称已经存在!";
                            }
                        }
                    }
                }
            });

		 	//------确认添加分区-------
			form.on('submit(confirmBtn)', function (data) {
			    var obj = data.field;
                $.ajax({
                    url:"${ctx}/clctapplmgt/editClctApplMgt.action",
                    data:$("#formHtml").serialize(),
                    dataType:"json",
                    type:"post",
                    success:function(result) {
                        layer.msg("编辑成功!",{
                            icon:1,
                            time:2000
                        },function(){
                            parent.layer.close(index);//关闭layer
                            $('#iframe',parent.document).attr('src', '${ctx}/clctapplmgt/clctApplMgt.action');
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
				parent.layer.close(index);//关闭layer
			})
			
		})	
	})
   
</script>
</html>