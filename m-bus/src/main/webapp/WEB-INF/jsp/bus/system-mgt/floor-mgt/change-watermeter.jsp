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
  <title>系统管理-楼层管理-更换水表</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/resources/css/common-layer.css" />
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<div class="layui-bg-white">
	<div class="layui-fluid">
		<div class="layui-row  ">
	<form class="layui-form" method="post">
		<input type="hidden" name="id" id="id" value="${waterMeterRoomInfo.id}" />

		<!-- 卡片一 -->
		<div class="layui-card">
            <div class="layui-card-header"><b>${waterMeterRoomInfo.regionName}-${waterMeterRoomInfo.apartmentName}-${waterMeterRoomInfo.floorName}-${waterMeterRoomInfo.layerName}-${waterMeterRoomInfo.roomNum}</b></div>
			<%--<div class="layui-card-body" style="float: none;height:80px;">--%>
				<%--<div style="float: left;">--%>
					<%--<div class="layui-form-item">--%>
						<%--<label class="layui-form-label">区域名称:</label>--%>
						<%--<label class="layui-form-mid">${waterMeterRoomInfo.regionName}</label>--%>
					<%--</div>--%>
					<%--<div class="layui-form-item">--%>
						<%--<label class="layui-form-label">公寓名称:</label>--%>
						<%--<label class="layui-form-mid">${waterMeterRoomInfo.apartmentName}</label>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div style="float: left;">--%>

				<%--</div>--%>
				<%--<div style="float: left;">--%>
					<%--<div class="layui-form-item">--%>
						<%--<label class="layui-form-label">楼栋名称:</label>--%>
						<%--<label class="layui-form-mid">${waterMeterRoomInfo.floorName}</label>--%>
					<%--</div>--%>
					<%--<div class="layui-form-item">--%>
						<%--<label class="layui-form-label">楼层:</label>--%>
						<%--<label class="layui-form-mid">${waterMeterRoomInfo.layerName}</label>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div style="float: left;">--%>

				<%--</div>--%>
				<%--<div style="float: left;">--%>
					<%--<div class="layui-form-item">--%>
						<%--<label class="layui-form-label">房间号:</label>--%>
						<%--<label class="layui-form-mid">${waterMeterRoomInfo.roomNum}</label>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<!-- 卡片二 -->
		<div class="layui-card">
            <div class="layui-card-header"><b>旧水表信息</b></div>
			<div class="layui-card-body" style="float: none;height:80px;">
				<div style="float: left;">
					<div class="layui-form-item">
						<label class="layui-form-label">水表编号:</label>
						<label class="layui-form-mid">${waterMeterRoomInfo.water_meter_id}</label>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">开户时间:</label>
						<label class="layui-form-mid" style="width:131px;" >
							${waterMeterRoomInfo.openTime}
						</label>
					</div>
				</div>
				<div style="float: left;">
					<div class="layui-form-item">
						<label class="layui-form-label">旧水表类型:</label>
						<label class="layui-form-mid">
							<c:if test="${waterMeterRoomInfo.type == 0}">冷水水表</c:if>
							<c:if test="${waterMeterRoomInfo.type == 1}">生活热水表</c:if>
						</label>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">上次换表日期:</label>
						<label class="layui-form-mid" style="width: 131px;">
							${waterMeterRoomInfo.update_time}
						</label>
					</div>
				</div>
			</div>
		</div>

		<!-- 卡片三 -->
		<div class="layui-card" style="float:left;">
            <div class="layui-card-header"><b>新水表信息</b></div>
			<div class="layui-card-body" style="float: none;height:80px;">
				<div style="float: left;">
					<div class="layui-form-item">
						<label class="layui-form-label">新水表编号:</label>
						<div class="layui-input-inline">
							<input type="text" id="newWaterMeterId" name="newWaterMeterId" lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="9" placeholder="请输入水表编号" onkeyup="this.value=this.value.replace(/[^\d]/g,'');" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">换表费用:</label>
						<div class="layui-input-inline">
							<input type="text" id="replace_money" name="replace_money" onchange="changeBuyMoney()" lay-verify="required|number" autocomplete="off" class="layui-input" maxlength="10" placeholder="请输入换表费用" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : this.value.substring(0,this.value.length-1)">
						</div>
					</div>
				</div>
				<div style="float: left;">
					<div class="layui-form-item">
						<label class="layui-form-label">水表型号:</label>
						<div class="layui-input-inline">
							<select name="type" id="type">
								<option value="">请选择水表类型</option>
								<option value='0'>冷水水表</option>
								<option value='1'>生活热水表</option>
							</select>
						</div>
					</div>
					<label class="layui-form-label"></label>
					<div class="layui-input-inline" style="height: 60px">
					</div>
				</div>
				<div style="float: left;">
					<div class="layui-form-item">
						<label class="layui-form-label">换表原因:</label>
						<div class="layui-input-inline">
                            <textarea style="resize: none;width: 520px;height: 30px;" placeholder="请输入换表原因" class="layui-textarea" name="remark" id="remark" maxlength="100" onchange="this.value=this.value.substring(0, 100)" onkeydown="this.value=this.value.substring(0, 100)" onkeyup="this.value=this.value.substring(0, 100)" ></textarea>
						</div>
				    </div>
		        </div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block" style="margin-left: 292px;">
				<button type="button" class="layui-btn" lay-submit="" lay-filter="confirmBtn" id="confirmBtn">确定</button>
				<button type="button" class="layui-btn layui-btn-primary" id="canleBtn" >取消</button>
			</div>
		</div>
		</div>
	</form>
		</div>
	</div>
</div>
</body>

<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
    var $ = layui.jquery;//重点处
	 //主动加载jquery模块
	layui.use(['jquery', 'layer'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;
		$(".layui-form-label").width("100px");
		//得到父页面的id值
		var id = (window.parent.id_);
		var index = (window.parent.index_);
        //检查水表编号的唯一性
        function isCheckWaterMeterId(newWaterMeterId){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/waterMeterMgt/isCheckWaterMeterId.action", //路径
                data : {'water_meter_id':newWaterMeterId}, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        }

		layui.use('form', function(){
		 	var form = layui.form;
		 	//------授权提交-------
			form.on('submit(confirmBtn)', function (data) {
              /*  $("#confirmBtn").attr('disabled', true);//防止重复提交*/
			    var obj = data.field;
			    var id=$('#id').val();
                var newWaterMeterId=$('#newWaterMeterId').val();
                var type=$('#type').val();
                var replace_money=$('#replace_money').val();
                var remark=$('#remark').val();
                var reg=/^\d{8}$/;
                if(!reg.test(newWaterMeterId)){
                      parent.layer.open({
                          title: '温馨提示'
                          , content: '水表编号必须输入8位数字！'
                      })
                      return;

                  }
                if(type == ''){
                    parent.layer.open({
                        title: '温馨提示'
                        , content: '请选择水表类型！'
                    })
                    return;
				}
                /*if(remark == ''){
                    parent.layer.open({
                        title: '温馨提示'
                        , content: '请输入换表原因！'
                    })
                    return false ;
                }*/
                var num=isCheckWaterMeterId(newWaterMeterId);
                if(num != 0 && num != -1) {
                    parent.layer.open({
                        title: '温馨提示'
                        , content: '水表Id已存在，请重新输入！'
                    })
                    return false ;
                }
			     //以下是ajax
                $.ajax({
                    url:"${ctx}/floormgt/change.action",
                    data:{'id':id,'oldWaterMeterId':'${waterMeterRoomInfo.water_meter_id}','newWaterMeterId':newWaterMeterId,'type':type,'replace_money':replace_money,'openTime':'${waterMeterRoomInfo.openTime}','remark': remark},
                    dataType:"json",
                    type:"post",
                    success : function(result) {//返回数据根据结果进行相应的处理
                    var msg=result.msg;
                        layer.msg(msg,{
                            icon:1,
                            time:2000
                        },function(){
                            parent.layer.close(index);//关闭layer
                            $('#iframe',parent.document).attr('src', '${ctx}/floormgt/floorMgt.action');
                        })
                    },
                    error : function(result){
                        var msg=result.msg;
                        layer.msg(msg,{
                            icon:2,
                            time:2000
                        },function(){
                            layer.close(layer.index);
                            $('#iframe',parent.document).attr('src', '${ctx}/floormgt/floorMgt.action');
                        })
                    }
                });

			});
			//取消
			$("body").on("click","#canleBtn",function(){
				parent.layer.close(index);//关闭layer
			})
		})



	})

     function changeBuyMoney() {
         var replace_money = $("#replace_money").val();
         if (!/^\d+\.?\d{0,2}$/.test(replace_money)) {
             layer.msg('只能输入数字!');
             $("#replace_money").val(0);
             return;
         }
     }

</script>
</html>