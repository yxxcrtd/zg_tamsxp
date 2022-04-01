(function() {
    function searchTreeSingle() {
        $('#treeIndPortf').joyinMixTree({
            onTreeLoadSuccess: function () {
                $("#treeIndPortf_tree").closest('div').css("height",$('#treeIndPortf').height());
                // if(pageModel == PAGE_MODEL_REFERENCE) {
                //     var treeObj = $.fn.zTree.getZTreeObj("treeIndPortf_tree");
                //     var nodes = treeObj.getNodes();
                //     for(var i=0; i < nodes.length;i++) {
                //         var node = nodes[i];
                //         DisabledNode(treeObj, node);
                //     }
                //
                // }
                if(pageModel ==PAGE_MODEL_NEW){
                    var treeObj = $.fn.zTree.getZTreeObj("treeIndPortf_tree");
                    var nodes = treeObj.getNodes();
                    if(nodes.length==0){
                        $("div.mixtree-div").append('<a class="refreshAicon" ><i class="iconfont icon-found"></i>&nbsp;新建分类</a>')
                    }else{
                        $("div.mixtree-div").find('a.refreshAicon').hide();
                    }
                    $.fn.zTree.getZTreeObj("treeIndPortf_tree").setting.callback.onClick=function(event, treeId, treeNode){
                        event.stopPropagation();
                        if(treeNode.level==1){
                            $('#indId').val(treeNode.id);
                            $('#indName').val(treeNode.name);
                            //框架中有800毫秒限制，按钮点击太快会有问题
                            table.load();
                        }
                    };//触发函数

                    //绑定组合刷新事件
                    $("div.mixtree-div").find('a.refreshAicon').click(function(){
                        Joyin.dialog(JoyinUtil.getLabel("编辑"),
                            basePath + "invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance005/init",
                            "80%", "70%",{ID:""},
                            function(){
                                $('#treeIndPortf').reloadTree(null);
                            });
                    });
                }
            },
            onTreeEditFunction: function(obj,callback){
            var pid=obj.pId == null ? "": obj.pId;
            var id=obj.id;
            var name=obj.name;
            Joyin.dialog(JoyinUtil.getLabel("编辑"),
                basePath + "invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance005/init",
                "80%", "70%",{Name:name,ID:id,pId:pid},
                function(){
                    $('#treeIndPortf').reloadTree(null);
                });
            },
            onTreeAddSubFunction: function(obj,obj1){
                var parTreeId=obj;
                Joyin.dialog(JoyinUtil.getLabel("增加子级"),
                    basePath + "invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance004/init",
                    "80%", "70%",{parTreeId:parTreeId},
                    function(){
                        $('#treeIndPortf').reloadTree(null);
                    });
            },
            onTreeAddFunction: function(callback){
                Joyin.dialog(JoyinUtil.getLabel("增加一级"),
                    basePath + "invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance003/init",
                    "80%", "70%",null,function(){
                        $('#treeIndPortf').reloadTree(null);
                    });
            },
            onTreeDelFunction: function(obj,callback){
                var id=obj.id;
                Joyin.confirm("确定删除吗？",function(){
                    Joyin.ajax('getDelete',
                        {treeId:id},function(){
                            $('#treeIndPortf').reloadTree(null);
                        });

                });
                $('#typeDatatree').reloadTree(null);
            },col:[
                {text:'编辑',clas:'edit',isdefine:true},
                {text:'删除',clas:'del',isdefine:true},
                {text:'增加子级',clas:'addsub',isdefine:true},
                {text:'增加一级',clas:'add',isdefine:true}
            ]
        });
    }
    function DisabledNode(treeObj, node) {
        treeObj.setChkDisabled(node, true);
        if(node.isParent) {
            var children = node.children;
            for(var i=0; i < children.length; i++) {
                DisabledNode(treeObj, children[i]);
            }
        }
    };
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return {
            treeId: $('#indId').val(),
        }
    };
    /**
     * 定义多组合明细表格
     */
    var table=$("#dataIndTable").joyintable({
        ajax: {
            url:  basePath + "invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance001/findPage",
            type: 'POST',
            data: function() {
                return getSearchParams();
            }
        },
        cols:[
            {field:'seqNo', width:150 , visible: false}
            ,{field:'portfolioId', width:200, title: '组合代码' ,sortable: true}
            ,{field:'portfolioName', width:200, title: '组合名称',sortable: true}
            ,{field:'acctNo', width:200, title: '资金账户',sortable: true}
            ,{field:'effDate', width:200, title: '生效日',sortable: true,type:'date'}
        ]
    });



    /**
     * 弹出新建画面
     * @returns
     */
    function openNew() {
        var indId="",indName="";
        var treeObj = $.fn.zTree.getZTreeObj("treeIndPortf_tree");
        var nodes = treeObj.getSelectedNodes();

        $.each(nodes,function(i,v){
            if(v.level==1){
                indId=v.id;
                indName=v.name;
            }
        })
        if(nodes.length==0||indId==""){
            Joyin.error(JoyinUtil.getMessage("RSKRDS_W_0017",JoyinUtil.getLabel("RskSupervisionPortfPoint001.indicator")));
            return;
        }
        Joyin.dialog(JoyinUtil.getLabel("MultiPortfolioMaintenance002"),
            JoyinUtil.getRealPath("invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance002/init"),
            "100%", "100%",{pageModel : PAGE_MODEL_NEW,treeId:indId,indName:indName},function() {
                table.reload();
            });
    }

    /**
     * 画面加载完成
     */
    $(function() {
        var jqWin = $(window);
        var winH = jqWin.height();
        console.log('winH=' + winH);

        var jqReport = $('#treeIndPortf');
        var reportTop = jqReport.offset().top;
        var reportH = winH - reportTop-70;
        jqReport.height(reportH);
        jqWin.resize(function() {
            var winH = jqWin.height();
            var reportH = winH - reportTop-70;
            jqReport.height(reportH);
        });
        //初始化，产生树结构
        var indType= $("#indType").val();
        $("#treeIndPortf").attr('url',getAllRelationUrl+'?type='+indType)
        searchTreeSingle();


        $('#MultiPortfolioMaintenance001_btn_add').click(function() {
            openNew();
        });
        $('#MultiPortfolioMaintenance001_btn_delete').click(function() {
            var selArr = table.getAllCheckedRows();
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
            var object={};
            object.selectedItem=selArr;
            object.operateType="DELETE";
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                Joyin.ajaxJson(basePath + 'invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance002/delete', object,function (result) {
                    table.reload();
                });
            });
        });
        //指标类型联动事件
        $("#indType").change(function(){
            var indType=$(this).val();
            $("#treeIndPortf").css('height','670px')
            $("#treeIndPortf").attr('url',getAllRelationUrl+'?type='+indType)
            searchTreeSingle();
        })



    });
}());

function serachKey(){
    $('#treeIndPortf').reloadTree({
        treeName: $('#treeName').val()
    });

}