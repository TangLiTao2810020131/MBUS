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
  <title>权限管理-职工管理-编辑</title>
  <link rel="stylesheet" href="${ctx}/resources/css/layui.css"/>
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common-layer.css"/>
  <style>
    .layui-form-item{margin-bottom: 10px;}
    .layui-form-select dl{max-height: 187px;}
  </style>
</head>
<body style="background: #fff;overflow-x:hidden ;" class="innerBody">
<form class="layui-form layui-form-pane ml30" action="" id="formHtml" style="margin-top: 40px;" lay-filter="updForm">
  <input type="hidden" name="id"  value="${worker.id }" autocomplete="off" class="layui-input"/>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label label-120">职工账号</label>
      <div class="layui-input-inline">
        <label class="layui-form-label label-120">${worker.workerAccount}</label>
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120">登录名</label>
      <div class="layui-input-inline">
        <input type="text" name="workerName" id="workerName" value="${worker.workerName}" lay-verify="required|workerName" autocomplete="off" class="layui-input" maxlength="20">
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label label-120">职员姓名</label>
      <div class="layui-input-inline">
        <input type="text" name="realName" id="realName" value="${worker.realName}" lay-verify="required|realName" autocomplete="off" class="layui-input" maxlength="20">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label label-120">电子邮箱</label>
      <div class="layui-input-inline">
        <input type="text" name="email" id="email" lay-verify="required|email" value="${worker.email}" autocomplete="off" class="layui-input" maxlength="20">
      </div>

    </div>


  </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">角色名称</label>
      <div class="layui-input-inline">
        <div class="layui-input-inline">
          <select name="roleId" id="roleId">
            <option value="">请选择身份类型</option>
            <c:forEach items="${roles}" var="role">
              <option <c:if test="${role.id == mbRole.roleId}" > selected="selected" </c:if>value="${role.id}">${role.rolename}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">账号状态</label>
      <div class="layui-input-inline">
        <select name="isOpen" id="isOpen" lay-verify="required">
          <option value=""<c:if test="${worker.isOpen ==''}">selected="selected"</c:if>>请选择账号状态</option>
          <option value="0"<c:if test="${worker.isOpen =='0'}">selected="selected"</c:if>>启用</option>
          <option value="1"<c:if test="${worker.isOpen =='1'}">selected="selected"</c:if>>禁用</option>

        </select>
      </div>
    </div>
  </div>


  <div class="layui-form-item  window-block-btn">
    <div class="layui-input-block">
      <button type="button" class="layui-btn layui-btn-primary window-btn" id="canleBtn" style="margin-right: 47%">取消</button>
      <button type="button" class="layui-btn window-btn" lay-submit="" lay-filter="confirmBtn" id="confirmBtn">确定</button>

    </div>
  </div>
</form>


</body>

<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function(){
        var $ = layui.jquery //重点处
            ,layer = layui.layer;
        //得到父页面的id值
        /*var index = (window.parent.index_);
        console.log(index);*/

        layui.use('form', function(){
            var form = layui.form;

            form.verify({
                workerName:[/^[a-zA-Z][a-zA-Z0-9_]{4,12}$/, '登录名必须是字母开头，允许4-12字节，允许字母数字下划线！'],
                realName:[/^[a-zA-Z0-9_\u4e00-\u9fa5]{1,12}$/, '职员姓名1到12位   汉字、大小写字母、数字']

            });
            //检查职员账号的唯一性
            /*function isCheckWorker(obj){
                var num;
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/workermgt/isCheckWorker.action", //路径
                    data : obj, //数据，这里使用的是Json格式进行传输
                    dataType : "json",
                    async : false,
                    success : function(result) { //返回数据根据结果进行相应的处理
                        num = result;
                    }
                });
                return num;
            }
*/



            //------确认添加分区-------
            form.on('submit(confirmBtn)', function (data) {
                var obj = data.field;
                console.log(obj);
                var roleId=$('#roleId').val();
                if(roleId == ''){
                    layer.msg('请选择角色！', {
                        icon: 7,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        $("#roleId").focus();
                        $("#confirmBtn").attr('disabled', false);
                    });
                    return false;

                }
                /*var num=isCheckWorker(obj);
                if(num != 0 && num != -1) {
                    layer.msg('职员账号已存在,请重新输入！', {
                        icon: 7,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        $("#workerAccount").focus();
                        $("#confirmBtn").attr('disabled', false);
                    });
                    return false;
                }*/
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/workermgt/edit.action",//路径
                    data : obj,//数据，这里使用的是Json格式进行传输
                    dataType:"json",
                    async:false,
                    success : function(result) {//返回数据根据结果进行相应的处理
                        //alert(result);
                        layer.msg(result,{
                            icon:1,
                            time:2000
                        },function(){
                            layer.close(layer.index);
                            window.parent.location.reload();
                        })
                    },
                    error : function(result){
                        //alert(result);
                        layer.msg(result,{
                            icon:2,
                            time:2000
                        },function(){
                            layer.close(layer.index);
                            window.parent.location.reload();
                        })
                    }
                });
                return false;


            });


            //取消
            $("body").on("click","#canleBtn",function(){
                layer.close(layer.index);
                window.parent.location.reload();
            })

        })
    })

</script>
</html>