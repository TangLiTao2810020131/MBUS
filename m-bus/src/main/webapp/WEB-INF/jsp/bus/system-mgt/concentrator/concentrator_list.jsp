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
    <title>集中器</title>
    <link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
</head>
<body class="inner-body" style="background:#f2f2f2;position: relative;padding:10px;">
<!-------默认表格开始----------->
<div class="tableHtml" style="height: 100%;background: #FFFFFF;">
    <div class="top-btn">
        <div class="layui-btn-group" style="position: relative;">
            <button class="layui-btn" id="addBtn">添加</button>
            <button class="layui-btn" id="editBtn">编辑</button>
            <button class="layui-btn" id="delBtn">删除</button>
            <button class="layui-btn" id="exportBtn">导出</button>
            <button class="layui-btn" id="room">绑定房间</button>
            <button class="layui-btn" id="relieveRoom">已绑房间</button>
        </div>
        <div class="search-btn-group">
            <input type="text"  id="concentrator_num" autocomplete="off" class="layui-input layui-input_" placeholder="输入集中器编号" maxlength="20">
            <button class="layui-btn layui-btns" id="simple">查询</button>

            <button class="layui-btn layui-btns layui-bg-orange" id="superBtn">高级查询</button>

        </div>
    </div>
    <div class="tableDiv">
        <table id="layui-table" lay-filter="tableBox" class="layui-table"></table>
    </div>
</div>
<!-----------------默认表格结束------------>
<!--高级查询-->
<form class="layui-form" action="" id="formHtml" lay-filter="updForm">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="IP地址">IP地址</label>
            <div class="layui-input-inline">
                <input type="text" name="" id="ip_address" placeholder="请输入IP地址" lay-verify="" autocomplete="off" class="layui-input" maxlength="20">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label" title="通讯模式">通讯模式</label>
            <div class="layui-input-inline">
                <select id="communication_mode">
                    <option value="" selected>请输入通讯模式</option>
                    <option value="0">TCP</option>
                    <%--<option value="1">UDP</option>--%>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn" lay-submit="" lay-filter="confirmBtn" id="gj">查询</button>
            <button type="button" class="layui-btn layui-btn-primary" id="closeBtn">收起</button>
        </div>
    </div>
</form>
<!--高级查询结束-->
</body>
<script type="text/html" id="handle">
    <a title="查看" lay-event="detail" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>

<script src="${ctx}/resources/js/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js" ></script>
<script type="text/javascript">
    //主动加载jquery模块
    layui.use(['jquery', 'layer'], function(){
        var $ = layui.jquery //重点处
            ,layer = layui.layer;
        table = layui.table;//表格，注意此处table必须是全局变量
        var arr = [[
            {type:'checkbox'}
            ,{field: 'concentrator_num', title: '集中器编号', }
            ,{field: 'ip_address', title: 'IP地址'}
            ,{field: 'communication_mode_name', title: '通讯模式', }
            ,{field: 'concentrator_version', title: '集中器版本'}
            // ,{field: 'remark', title: '备注'}
            ,{title: '操作',toolbar:'#handle'}
        ]];
        var limitArr =[10,20,30];
        var urls = '${ctx}/concentrator/listData.action';
        com.tableRender('#layui-table','tableId','full-50',limitArr,urls,arr);//加载表格,注意tableId是自定义的，在表格重载需要！！

        /*-----添加-----*/

        $("#addBtn").click(function(){
            var index =layer.open({ //在父窗口打开
                title:'新增集中器' ,
                resize:false,//禁止拉伸
                type: 2,
                area: ['750px', '500px'],
                content: "${ctx}/concentrator/addConcentratorPage.action",
                maxmin:true
            });
        })
        /*-----编辑-----*/
        $("#editBtn").click(function(){
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            //数据只能是一条
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请勾选一条目标数据！'
                });
                return false;
            } else if (checkStatus.data.length > 1) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '只能勾选一条数据！'
                });
                return false;
            }
            console.log(checkStatus.data);
            var index = layer.open({ //在父窗口打开
                title:'编辑集中器' ,
                resize:false,//禁止拉伸
                type: 2,
                area:['750px','500px'],
                content:"${ctx}/concentrator/editConcentratorPage.action?id="+checkStatus.data[0].id,
                maxmin:true
            });
            <%--com.checkOpen("编辑集中器","${ctx}/concentrator/editConcentratorPage.action",['800px', '610px']);--%>
        })
        /*-----绑定房间-----*/
        $("#room").click(function(){
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            if (checkStatus.data.length!=1) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请勾选一条目标数据！'
                });
                return false;
            }else{
                com.pageOpen("绑定房间","${ctx}/concentrator/setRoom.action?concentratorId="+checkStatus.data[0].id,['800px', '500px']);
            }
        });
        /*-----解除绑定-----*/
        $("#relieveRoom").click(function(){
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            if (checkStatus.data.length!=1) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请勾选一条目标数据！'
                });
                return false;
            }else{
                com.pageOpen("房间解除绑定","${ctx}/concentrator/relieveRoom.action?concentratorId="+checkStatus.data[0].id,['800px', '500px']);
            }
        })
        /*----删除-----*/
        $("#delBtn").click(function(){
            var checkStatus = table.checkStatus('tableId'); //test即为基础参数id对应的值
            //数据至少是一条
            if (checkStatus.data.length == 0) {
                parent.layer.open({
                    title: '温馨提示'
                    , content: '请至少勾选一条目标数据！'
                });
                return false;
            }else{
                var tc=parent.layer.confirm('数据删除后将不可恢复,请谨慎操作!',{
                    title:'温馨提示',
                    btn:['确定','取消']
                },function(){
                    parent.layer.close(tc);
                    var str="";
                    for(var i=0;i<checkStatus.data.length;i++)
                    {
                        str+=checkStatus.data[i].id+",";
                    }
                    $.ajax({
                        url:"${ctx}/concentrator/delConcentrators.action",
                        data:{str:str,concentratorId:""},
                        dataType:"json",
                        type:"post",
                        success:function(result){
                            if(result==0){
                                parent.layer.msg('该集中器已经绑定房间，不能删除！',{
                                    time:2000,
                                    icon:7
                                });
                            }else{
                                parent.layer.msg("删除成功!",{
                                    icon:1,
                                    time:2000
                                },function(){
                                    window.location.reload();
                                });
                            }
                        },
                        error:function(){
                            parent.layer.msg("服务器异常!",{
                                icon:7,
                                time:2000
                            });
                        }
                    });
                });
            }
        });

        //查看
        table.on('tool(tableBox)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                com.pageOpen("查看集中器","${ctx}/concentrator/findConcentratorPage.action?id="+data.id,['800px', '550px']);
            }
        });

        //表格重载
        $(function(){
            //查询重载表格
            $("#simple,#gj").on("click",function(){
                com.reloadTable(
                    {
                        concentrator_num:$("#concentrator_num").val(),
                        ip_address:$("#ip_address").val(),
                        communication_mode:$("#communication_mode").val()
                    }
                )
            });
        });

        //表格数据导出
        $(function(){
            $("#exportBtn").on("click",function(){
                var str="集中器编号,IP地址,通讯模式,集中器版本";
                var name="集中器";
                var url="${ctx}/concentrator/export.action?str="+str+"&name="+name+"&concentrator_num="+$("#concentrator_num").val()+
                    "&ip_address="+$("#ip_address").val()+"&communication_mode="+$("#communication_mode").val();
                window.location.href=url;
            });
        });
    })
</script>
</html>