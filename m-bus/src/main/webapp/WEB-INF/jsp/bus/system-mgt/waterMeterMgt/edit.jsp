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
  <title>系统管理-水表管理-编辑</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
  <style>
  	.layui-form-item{margin-bottom: 10px;}
  </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">

<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top: 40px;" lay-filter="updForm">
    <input type="hidden" name="id" value="${waterMeter.id}"/>
	<div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label label-120">水表编号</label>
      <div class="layui-input-inline">
        <input type="text" name="water_meter_id" id="water_meter_id" value="${waterMeter.water_meter_id}" lay-verify="required" autocomplete="off" class="layui-input" maxlength="8" onkeyup="this.value=this.value.replace(/[^\d]/g,'');" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120">水表类型</label>
      <div class="layui-input-inline">
        <select name="type" id="type">
          <option value="" <c:if test="${waterMeter.type == ''}">selected="selected"</c:if> >请选择水表类型</option>
          <option value="0" <c:if test="${waterMeter.type == '0'}">selected="selected"</c:if>  >冷水水表</option>
          <option value="1" <c:if test="${waterMeter.type == '1'}">selected="selected"</c:if> >生活热水表</option>
        </select>
      </div>
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
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#update_time' //指定元素
            ,type:'datetime'
            ,trigger: 'click' //采用click弹出
            ,max:new Date()+""
        });
    });
    //检查水表编号的唯一性
    function isCheckWaterMeterId(obj){
        var num;
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/waterMeterMgt/isCheckWaterMeterId.action", //路径
            data : obj, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                num = result;
            }
        });
        return num;
    }

	 //主动加载jquery模块
	layui.use(['jquery', 'layer'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;
		//得到父页面的id值
		var index = (window.parent.index_);
		console.log(index);

		
		layui.use('form', function(){  
		 	var form = layui.form;
		 	//------确认添加分区-------
			form.on('submit(confirmBtn)', function (data) {
			    var obj = data.field;
                var water_meter_id=$('#water_meter_id').val();
                var type=$('#type').val();
                var reg=/^\d{8}$/;


                if(obj.water_meter_id != '${waterMeter.water_meter_id}'){
                    var num=isCheckWaterMeterId(obj);
                    if(num != 0 && num != -1) {
                        parent.layer.open({
                            title: '温馨提示'
                            , content: '水表编号已存在，请重新输入！'
                        })
                        return false ;
                    }
                    if(type == ''){
                        parent.layer.open({
                            title: '温馨提示'
                            , content: '请选择水表类型！'
                        })
                        return false ;
                    }
                }

                if(!reg.test(water_meter_id)){
                    parent.layer.open({
                        title: '温馨提示'
                        , content: '水表编号只能输入8位数字！'
                    })
                    return false ;

                }
                if(type == ''){
                    parent.layer.open({
                        title: '温馨提示'
                        , content: '请选择水表类型！'
                    })
                    return false ;
                }
                $.ajax({
                    url:"${ctx}/waterMeterMgt/editWaterMeterMgt.action",
                    data:obj,
                    dataType:"json",
                    type:"post",
                    success:function(result) {
                        layer.msg("编辑成功!",{
                            icon:1,
                            time:2000
                        },function(){
                            parent.layer.close(index);//关闭layer
                            $('#iframe',parent.document).attr('src', '${ctx}/waterMeterMgt/waterMeterMgt.action');
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