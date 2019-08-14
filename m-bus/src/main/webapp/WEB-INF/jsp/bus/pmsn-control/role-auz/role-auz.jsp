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
  <title>权限管理-角色授权</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
  <style type="text/css">
.roleAuz .gridtable {
    font-family: verdana,arial,sans-serif;
    font-size:11px;
    color:#333333;
    border-width: 1px;
    border-color: #666666;
    border-collapse: collapse;
    width: 100%;
}
.roleAuz .gridtable th {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #CCCCCC;
    background-color: #dedede;
}
.roleAuz .gridtable td {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #CCCCCC;
    background-color: #ffffff;
}
.roleAuz .td1{
	width: 10%;
	min-width: 140px;
}
.roleAuz .td2{
	width: 90%;
	min-width: 575px;
}
.roleAuz ul li{
	float: left;
	margin: 5px;
}
::selection { background:transparent;  }

::-moz-selection { background:transparent;  }

::-webkit-selection{ background:transparent; }
.roleAuz label {font-size:14px;cursor:pointer;}
.roleAuz label i {font-size:14px;font-style:normal;display:inline-block;width:14px;height:14px;text-align:center;line-height:12px;color:#fff;vertical-align:middle;margin:-2px 2px 1px 0px;border:#000 1px solid;}
.roleAuz input[type="checkbox"],input[type="radio"] {display:none;}
.roleAuz input[type="radio"] + i {border-radius:7px;}
.roleAuz input[type="checkbox"]:checked + i,input[type="radio"]:checked + i {background:#2489c5;border:#2489c5 1px solid;}
.roleAuz input[type="checkbox"]:disabled + i,input[type="radio"]:disabled + i {border-color:#ccc;}
.roleAuz input[type="checkbox"]:checked:disabled + i,input[type="radio"]:checked:disabled + i {background:#ccc;}
</style>
</head>
<body class="inner-body roleAuz" style="background:#f2f2f2;position: relative;">

	<!-------默认表格开始----------->
	<div class="tableHtml" style="margin-left: 2px;height: 100%;background: #FFFFFF;position: relative;">
		<table class="gridtable">
			<tr>
			    <th>	
					<label><input type="checkbox"><i>✓</i>一级菜单</label>
			    </th>
			    <th style="font-size: 14px;">二级菜单</th>
			</tr>
			<tr class="">
			    <td class="td1 bbcx">
			    	<label id="ser"><input type="checkbox"><i>✓</i>报表查询</label>
			    </td>
			    <td class="td2 bbcx">
			    	<ul class="bbcxUl">
			    		<li>
			    			<label><input type="checkbox"><i>✓</i>用水明细记录</label>
			    		</li>
			    		<li>
			    			<label><input type="checkbox"><i>✓</i>现金购水记录</label>
			    		</li>
			    		<li>
			    			<label><input type="checkbox"><i>✓</i>用水明细记录</label>
			    		</li>
			    		<li>
			    			<label><input type="checkbox"><i>✓</i>现金购水记录</label>
			    		</li>
			    		<li>
			    			<label><input type="checkbox"><i>✓</i>用水明细记录</label>
			    		</li>
			    		<li>
			    			<label><input type="checkbox"><i>✓</i>现金购水记录</label>
			    		</li>
			    		<li>
			    			<label><input type="checkbox"><i>✓</i>用水明细记录</label>
			    		</li>
			    		<li>
			    			<label><input type="checkbox"><i>✓</i>现金购水记录</label>
			    		</li>
			    	</ul>
			    </td>
			</tr>
			<tr>
			    <td class="td1">Text 2A</td>
			    <td class="td2">Text 2B</td>
			</tr>
		</table>

	</div>
	
</body>

	<script src="${ctx}/resources/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
	 //主动加载jquery模块
	layui.use(['jquery', 'layer'], function(){
		var $ = layui.jquery //重点处
		,layer = layui.layer;
		/*树形菜单*/
		com.normalTree("#tree","../../../treeJson.js");
		/*$(".td1").width($('#iframe', parent.document).width()*0.2);
		$(".td1").width($('#iframe', parent.document).width()*0.8);*/
		$(".roleAuz").on("click","#ser",function(){
		
			if($("#ser i").css("background-color") == "rgb(36, 137, 197)"){
			  $("#ser input").attr("checked","checked");
			  $(".bbcxUl input").attr("checked","checked");
			  $(".bbcxUl i").css({background:"#2489c5",border:"#2489c5 1px solid"});
			}else{
				$("#ser input").removeAttr("checked");
				$(".bbcxUl input").removeAttr("checked");
				$(".bbcxUl i").css({background:"#fff",border:"#000 1px solid"});
			}
		})
	
	})
   
</script>
</html>