<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>参数设置-系统运行参数</title>
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
    <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
	</head>
	<body class="parm">
		<form class="layui-form" action="" style="color: #666;background: #FFFFFF;">
			<input type="hidden" name="id" id = "id" class="layui-input" value="${sysRunningParm.id}">
			<input type="hidden" name="del_status" id = "del_status" class="layui-input" value="${sysRunningParm.del_status}">
			<input type="hidden" name="effect_status" id = "effect_status" class="layui-input" value="${sysRunningParm.effect_status}">
			<input type="hidden" name="create_time" id = "create_time" class="layui-input" value="${sysRunningParm.create_time}">

		  <div class="layui-form-item" style="margin-top:25px">
		    <div class="layui-inline">
		      <label class="layui-form-label">报警水量</label>
		      <div class="layui-input-inline">
		        <input name="warn_water" id="warn_water" class="layui-input" type="text"  autocomplete="off" lay-verify="required|warn_water"  value="${sysRunningParm.warn_water}" onchange="changeWarnWater()" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" maxlength="10">
		      </div>
		      <div class="dun">吨</div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">关阀水量</label>
		      <div class="layui-input-inline">
		        <input name="valve_water" id="valve_water" class="layui-input" type="text" autocomplete="off" lay-verify="required|valve_water" value="${sysRunningParm.valve_water}" onchange="changeValveWater()" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" maxlength="10">
		      </div>
		      <div class="dun">吨</div>
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-inline">
		      <label class="layui-form-label">最大透支量</label>
		      <div class="layui-input-inline">
		        <input name="over_water" id="over_water" class="layui-input" type="text" autocomplete="off" lay-verify="required|over_water" value="${sysRunningParm.over_water}" onchange="changeOverWater()" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" maxlength="10">
		      </div>
		      <div class="dun">吨</div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">最大囤积量</label>
		      <div class="layui-input-inline">
		        <input name="hoard_water" id="hoard_water" class="layui-input" type="text" autocomplete="off" lay-verify="required|hoard_water" value="${sysRunningParm.hoard_water}" onchange="changeHoardWater()" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" maxlength="10">
		      </div>
		      <div class="dun">吨</div>
		    </div>
		  </div>

		  <div class="layui-form-item">
		    <div class="layui-inline">
		      <label class="layui-form-label">购水单价</label>
		      <div class="layui-input-inline">
					<input name="buy_money" id="buy_money" class="layui-input" type="text" autocomplete="off" lay-verify="required|buy_money" value="${sysRunningParm.buy_money}" onchange="changeBuyMoney()" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" maxlength="10">
		      </div>
		      <div class="dun">元/m3</div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">退水单价</label>
		      <div class="layui-input-inline">
		        <input name="return_money" id="return_money" class="layui-input" type="text" autocomplete="off" lay-verify="required|return_money" value="${sysRunningParm.return_money}" onchange="changeReturnMoney()" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)" maxlength="10">
		      </div>
		      <div class="dun">元/m3</div>
		    </div>
		  </div>

		  <div class="layui-form-item">
		    <label class="layui-form-label">软件补水周期</label>
		    <div class="layui-input-block">
		      <input type="radio" name="add_cycle" value="0" title="每日补一次" <c:if test="${sysRunningParm.add_cycle =='0'}">checked</c:if>>
		      <input type="radio" name="add_cycle" value="1" title="每月补一次" <c:if test="${sysRunningParm.add_cycle =='1'}">checked</c:if>>
		      <input type="radio" name="add_cycle" value="2" title="不限制补水周期" <c:if test="${sysRunningParm.add_cycle =='2'}">checked</c:if>>
		    </div>
		  </div>

		 <div class="layui-form-item">
		    <div class="layui-inline" >
		      <label class="layui-form-label">参数生效时间</label>
		      <div class="layui-input-inline" style="width: 180px;">
		        <input name="effect_time" id="effect_time" class="layui-input" type="text" placeholder="请选择时间" autocomplete="off" lay-verify="required" value='${sysRunningParm.effect_time}'>
		      </div>
		    </div>
		  </div>
		  <div class="layui-form-item layui-layout-admin" style="height:150px;background: #FFFFFF;">
            <div class="layui-input-block">
              <div class="layui-footer" style="left: 0;text-align: center;background: #FFFFFF;padding-bottom: 10px;">
				  <%--<shiro:hasPermission name="编辑系统运行参数">--%>
                <button class="layui-btn" lay-submit="" lay-filter="component-form-demo1" id="component-form-demo1">立即提交</button>
				 <%-- </shiro:hasPermission>--%>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
              </div>
            </div>
          </div>
		</form>
	</body>
	<script src="${ctx}/resources/js/layui.all.js"></script>
    <script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
	<script>

		layui.use(['form', 'layedit', 'laydate'], function(){
		  var $ = layui.jquery
	      ,layer = layui.layer
		  ,form = layui.form
		  ,layedit = layui.layedit
		  ,laydate = layui.laydate;

		  //日期
		  laydate.render({
		    elem: '#effect_time',
              type: 'datetime',
              trigger: 'click', //采用click弹出
			 format:'yyyy-MM-dd HH:mm:ss'
		  });

		//设置页面高度
		 var pageH = $("#layui-body",parent.document).height();
		  $(".layui-form").css({
		  	"height":pageH,
		  	"maxHeight":pageH,
		  	"overflow":"auto"
		  })
			//自定义正则验证
        /*    form.verify({
                warn_water: [/^\d+(.\d+)?$/, '只能输入数字！'],
                valve_water: [/^\d+(.\d+)?$/, '只能输入数字！'],
                over_water: [/^\d+(.\d+)?$/, '只能输入数字！'],
                hoard_water:[/^\d+(.\d+)?$/, '只能输入数字！'],
                buy_money: [/^\d+(.\d+)?$/, '只能输入数字！'],
                return_money: [/^\d+(.\d+)?$/, '只能输入数字！']
			});*/
		  //监听提交
		  form.on('submit(component-form-demo1)', function(data){
			  var obj=(data.field);
			  //提交参数数据
             $.ajax({
                  type : 'post', //提交方式
                  url : '${ctx}/sysrunningparm/saveSysrunningparm.action',//路径
                  data :obj,//参数
                  dataType:'json',
                  async:false,
                  success : function(result) {//返回数据根据结果进行相应的处理
                      var status = result.status;
                      var msg = result.msg;
                      if(status == '1'){
                      layer.msg(msg,{
                          icon:1,
                          time:2000
                      },function(){
                          layer.close(layer.index);
                          window.location.reload();
                      })
                      }else {
                          layer.msg(msg,{
                              icon:5,
                              time:2000
                          },function(){
                              layer.close(layer.index);
                              $("#component-form-demo1").attr('disabled', false);
                          })

					  }
                  }
              });
              return false;
		  });
		});

        function changeWarnWater(){
            var warn_water = $("#warn_water").val();
            if(!/^\d+\.?\d{0,2}$/.test(warn_water)){
                layer.msg('只能输入数字!');
                $("#warn_water").val(0);
                return;
            }
            }
        function changeValveWater(){
            var valve_water = $("#valve_water").val();
            if(!/^\d+\.?\d{0,2}$/.test(valve_water)){
                layer.msg('只能输入数字!');
                $("#valve_water").val(0);
                return;
            }
        }
        function changeOverWater(){
            var over_water = $("#over_water").val();
            if(!/^\d+\.?\d{0,2}$/.test(over_water)){
                layer.msg('只能输入数字!');
                $("#over_water").val(0);
                return;
            }
        }
        function changeHoardWater(){
            var hoard_water = $("#hoard_water").val();
            if(!/^\d+\.?\d{0,2}$/.test(hoard_water)){
                layer.msg('只能输入数字!');
                $("#hoard_water").val(0);
                return;
            }
        }
        function changeBuyMoney(){
            var buy_money = $("#buy_money").val();
            if(!/^\d+\.?\d{0,2}$/.test(buy_money)){
                layer.msg('只能输入数字!');
                $("#buy_money").val(0);
                return;
            }
        }
        function changeReturnMoney(){
            var return_money = $("#return_money").val();
            if(!/^\d+\.?\d{0,2}$/.test(return_money)){
                layer.msg('只能输入数字!');
                $("#return_money").val(0);
                return;
            }
        }
        function changeWarnWater(){
            var warn_water = $("#warn_water").val();
            if(!/^\d+\.?\d{0,2}$/.test(warn_water)){
                layer.msg('只能输入数字!');
                $("#warn_water").val(0);
                return;
            }
        }
	</script>
</html>
