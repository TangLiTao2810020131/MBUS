function tree(url)
{
    var setting = {
        callback: {
            onClick: onClick
        }
    };
    var zNodes = "";
    $.ajax({
        url: url,
        data: {},
        dataType: "json",
        type: "post",
        async: false,
        success: function (data) {
            zNodes = data;
        }
    });
    //单击树节点触发事件
    function onClick(event, treeId, treeNode, clickFlag) {
        com.reloadTable({id: treeNode.id, level: treeNode.level});
    }
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });
}
