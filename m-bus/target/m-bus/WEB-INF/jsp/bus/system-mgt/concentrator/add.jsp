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

<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="width: ; margin-top: 40px;" lay-filter="updForm">
  <div class="layui-form-item">

    <div class="layui-inline">
      <label class="layui-form-label label-120" title="创建时间">创建时间</label>
      <div class="layui-input-inline">
        <input type="text" name="create_time"  id="createTime" lay-verify="required" autocomplete="off" class="layui-input" placeholder="请选择创建时间"/>
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="采集名称">集中器编号</label>
      <div class="layui-input-inline">
       <input type="input" id="concentrator_num" name="concentrator_num" oninput = "value=value.replace(/[^\d]/g,'')" lay-verify="required" class="layui-input" placeholder="请输入集中器编号" maxlength="16">
      </div>
    </div>
  </div>
	<div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="集中器IP地址">IP地址</label>
      <div class="layui-input-inline">
        <input type="text" name="ip_address" lay-verify="required|ip_address" autocomplete="off" class="layui-input" placeholder="请输入IP地址"  maxlength="20">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="集中器密码">集中器密码</label>
      <div class="layui-input-inline">
        <input type="text" name="concentrator_pwd" lay-verify="required|concentrator_pwd" oninput = "value=value.replace(/[^\d]/g,'')" autocomplete="off" class="layui-input" placeholder="请输入集中器密码"  maxlength="20">
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="集中器版本">集中器版本</label>
      <div class="layui-input-inline">
        <input type="text" name="concentrator_version"  id="concentrator_version" lay-verify="required|concentrator_version" autocomplete="off" class="layui-input" placeholder="请输入集中器版本"  maxlength="20"/>
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120" title="通讯模式">通讯模式</label>
      <div class="layui-input-inline">
        <select name="communication_mode">
          <option value="">请选择通讯模式</option>
          <option value="0">TCP</option>
          <%--<option value="1">UDP</option>--%>
        </select>
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-form search-btn-group-box" style="display: inline-block;">
      <div class="layui-inline">
        <label class="layui-form-label label-120" title="公寓名称">公寓名称</label>
        <div class="layui-input-inline">
          <select name="apartment_id">
            <option value="">请选择公寓名称</option>
            <c:forEach items="${tList}" var="t">
              <option value="${t.id}">${t.name}</option>
            </c:forEach>
          </select>
        </div>
      </div>
      <div class="layui-inline">
        <label class="layui-form-label label-120" title="采集名称">采集名称</label>
        <div class="layui-input-inline">
          <select name="collect_name">
            <option value="">请选择采集名称</option>
            <c:forEach items="${cList}" var="c">
              <option value="${c.id}">${c.collect_name}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
  </div>
  <div class="layui-form-item layui-form-text" style="width:624px;">
    <label class="layui-form-label">备注</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容(最多200个字符)" name="remark" class="layui-textarea" maxlength="200"></textarea>
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
            ,elem: '#createTime' //指定元素
            ,trigger: 'click' //采用click弹出
            ,max:new Date()+""
        });
    });

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#time' ,//指定元素
            trigger: 'click' //采用click弹出
            ,max:new Date()+""
        });
    });

	 //主动加载jquery模块
	layui.use(['jquery', 'layer','form'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer
            ,form = layui.form;
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
                    url:"${ctx}/concentrator/addConcentrator.action",
                    data:$("#formHtml").serialize(),
                    dataType:"json",
                    type:"post",
                    success:function(result) {
                        var index=parent.layer.msg("添加成功!",{
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
		});

		//表单验证
        form.verify({
            ip_address:function(value,ele){
                if(!/(?=(\b|\D))(((\d{1,2})|(1\d{1,2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{1,2})|(2[0-4]\d)|(25[0-5]))(?=(\b|\D))/.test(value.trim()))
                {
                    return "IP地址格式不正确！"
                }else{
                    //验证IP地址唯一性
                    var flag=0;
                    $.ajax({
                        url:'${ctx}/concentrator/checkConcentratorIp.action',
                        data:{ipAddress:value.trim()},
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
                        return "集中器IP已经存在!";
                    }
                }
            },
            concentrator_pwd:function(value,ele){
                if(!/^[0-9]{6,8}$/.test(value.trim()))
                {
                    return "集中器密码不正确(6-8位数字)！";
                }
            },
            concentrator_version:function(value,ele){
                // if(!/^([1-9]\d|[1-9])(\.([1-9]\d|\d)){3}$/.test(value.trim()))
                // {
                //     return "集中器版本号不正确(如:1.1.1.1)！";
                // }
            },
        });
	})
   
</script>
</html>