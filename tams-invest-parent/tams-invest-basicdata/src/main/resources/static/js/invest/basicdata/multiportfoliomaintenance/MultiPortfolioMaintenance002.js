(function() {
    function searchTreeSingle() {
        $('#treeIndPortf').joyinMixTree({
            onTreeLoadSuccess: function () {
                $("#treeIndPortf_tree").closest('div').css("height",$('#treeIndPortf').height());
                if(pageModel == PAGE_MODEL_REFERENCE) {
                    var treeObj = $.fn.zTree.getZTreeObj("treeIndPortf_tree");
                    var nodes = treeObj.getNodes();
                    for(var i=0; i < nodes.length;i++) {
                        var node = nodes[i];
                        DisabledNode(treeObj, node);
                    }

                }
                if(pageModel ==PAGE_MODEL_NEW){
                    $.fn.zTree.getZTreeObj("treeIndPortf_tree").setting.callback.onClick=function(event, treeId, treeNode){
                        event.stopPropagation();
                        if(treeNode==null){
                            $("div.mixtree-div").append('<a class="refreshAicon" ><i class="iconfont icon-found"></i>&nbsp;新建分类</a>')
                        }
                        if(treeNode.level==1){
                            $('#indId').val(treeNode.id);
                            $('#indName').val(treeNode.name);
                            //框架中有800毫秒限制，按钮点击太快会有问题
                            table.load();
                        }
                    };//触发函数

                    //绑定组合刷新事件
                    $("div.mixtree-div").find('a.refreshAicon').click(function(){
                        event.stopPropagation();
                        $("#indType").trigger('change');
                    });
                }
            }
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
     * 定义单组合数据表格
     */
    var table=$("#dataIndTable").joyintable({
        data:[],
        cols:[
            {field:'portfolioId', width:200, title: '组合代码' ,sortable: true}
            ,{field:'portfolioName', width:200, title: '组合名称',sortable: true}
            ,{field:'acctNo', width:200, title: '资金账户',sortable: true}
            ,{field:'effDate', width:200, title: '生效日',sortable: true,type:'date'}
            ,{field:'treeId', visible: false}
        ]
    });

    /**
     * 重设表格数据
     */
    var resetTableData = function () {
        var treeObj = $.fn.zTree.getZTreeObj("treeIndPortf_tree");
        var nodes = treeObj.getCheckedNodes();
        if (!nodes || nodes.length == 0) {
            Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
            return;
        }
        if($('#effDate').val()==null||$('#effDate').val()==''){
            Joyin.error(JoyinUtil.getMessage("MultiPortfolioMaintenance002.effDate.isEmpty"));
            return;
        }
        if($('#effDate').val()&&typeof $('#effDate').val() != 'undefined'
            &&iPlatformDate && typeof iPlatformDate != 'undefined'){
            if(Date.ofDate($('#effDate').val()).DateDiff('d',iPlatformDate)>0){
                Joyin.error(JoyinUtil.getMessage("MultiPortfolioMaintenance002.effDate.vaild"));
                return;
            }
        }
        var selectedItem = [];
        $.each(nodes,function(i,v){
            if(v.level==1){
                selectedItem.push({'portfolioId':v.pId,'portfolioName':v.object,'acctNo':v.id,'effDate':$('#effDate').val(),'treeId':$('#treeId').val()})
            }
        })
        var object ={};
        object.selectedItem=selectedItem;
        object.operateType='confirm';
        var selArr = table.getAllRows();
        for(var i=0;i<selArr.length;i++){
                table.removeRow(selArr[i])
        }
        Joyin.ajaxJson(basePath + 'invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance002/check', object,function (result) {
            table.load();
            table.reload({data:selectedItem});
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

        /**
         * 确定按钮绑定事件
         */
        $('#MultiPortfolioMaintenance002_btn_confirm').click(function () {
            resetTableData();
        });
        // 取消
        $('#MultiPortfolioMaintenance002_btn_cancel').click(function() {
            table.clear();
            table.load();
        });
        $('#MultiPortfolioMaintenance001_btn_add').click(function() {
            openNew();
        });
        $('#MultiPortfolioMaintenance002_btn_delete').click(function() {
            var selArr = table.getAllRows();
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
            for(var i=0;i<selArr.length;i++){
                if(selArr[i].LAY_CHECKED){
                    table.removeRow(selArr[i])
                }
            }
            table.load();
        });
        /**
         * 取消
         */
        $('#MultiPortfolioMaintenance002_btn_commit').click(function () {
            var selArr = table.getAllRows();
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("MultiPortfolioMaintenance002.commit"));
                return;
            }
            var object={};
            object.selectedItem=selArr;
            object.operateType="NEW";
            Joyin.ajaxJson(basePath + 'invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance002/check', object,function (result) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.submit")), function(){
                    Joyin.ajaxJson(basePath + 'invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance002/insert', object,function (result) {
                        // window.parent.Multiportfoliomaintenance001.reloadTable();//刷新父页面表格
                        parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭子页面

                    });
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