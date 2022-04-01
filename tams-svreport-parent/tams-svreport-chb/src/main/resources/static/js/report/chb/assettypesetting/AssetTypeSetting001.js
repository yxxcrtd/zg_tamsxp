(function() {
    var str;
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return {
            //一级分类
            assetType1 : $('#assetType1').val(),
            //二级分类
            assetType2 : $('#assetType2').val(),
            //三级分类
            assetType3 : $('#assetType3').val()
            //业务分类
//            ,assetBusiType : $('#assetBusiType').val()
        }
    };
    /**
     * 左侧列表：主查询
     * @type {jQuery}
     */
    var tableA = $('#dataTable1').joyintable({
        ajax : {
            url : 'selectForPage',
            type : 'POST',
            data : function() {
                return getSearchParams();
            }
        },
        title: '投管资产类型',
        checkbox : true,
        cols : [
            {field: 'typeuuid', title: "主键",width:120 ,visible:false},
            {field: 'assetType1', title: "资产一级分类",width:120 ,visible:false},
            {field: 'assetType1Str', title: "资产一级分类",width:140 ,sortField:'assetType1'},//资产一级类型
            {field: 'assetType2', title: "资产二级分类",width:120 ,visible:false},
            {field: 'assetType2Str', title: "资产二级分类",width:140 ,sortField:'assetType2'},//资产二级类型
            {field: 'assetType3', title: "资产三级分类",width:120 ,visible:false},
            {field: 'assetType3Str', title: "资产三级分类",width:140 ,sortField:'assetType3'},//资产三级类型
//            {field: 'assetBusiType', title: "业务分类",width:120 ,visible:false},
//            {field: 'assetBusiTypeStr', title: "业务分类",width:140 ,sortField:'assetBusiType'},//业务分类
            {field: 'operation', title: "操作",type: 'action',buttons: ['edit','delt'] ,width: 100}//操作
        ],
        click: function (table, rowData) {
            str = rowData.typeuuid;
            /*选中委托加载指令报价信息*/
            tableB.table ? tableB.reload() : tableB.load();
        },
    });

    /**
     * 绑定编辑事件
     */
    tableA.bind('click', '.edit', function(data, index) {
        data.pageModel = PAGE_MODEL_EDIT;
        data.operateType = 'edit';
        var object = {selectedItem: [data],operateType: 'edit'};//请求参数
        Joyin.ajaxJson(basePath + 'chb/assettypesetting/AssetTypeSetting002/check', object, function(result) {//请求路径，请求参数回调函数
            Joyin.dialog(JoyinUtil.getLabel("资产负债类别配置修改"),
                basePath + "chb/assettypesetting/AssetTypeSetting002/init",//打开新界面
                "85%", "85%", {pageModel:PAGE_MODEL_EDIT,
                    typeuuid: data.typeuuid, selectedItem: [data]
                },function(){
                    tableA.reload();//回调函数，表格刷新
                });
        });
    });

    /**
     * 绑定删除事件
     */
    tableA.bind('click', '.delt', function(data, index) {
        var object = {selectedItem: [data], operateType:"delete"};//请求参数
        Joyin.ajaxJson(basePath + 'chb/assettypesetting/AssetTypeSetting001/check', object, function(result) {//请求路径，请求参数回调函数
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("删除[资产一级分类]及配置的所有对应中债资产类型")), function(){//删除事件的请求
                WorkFlow.doDeleteList(basePath + 'chb/assettypesetting/AssetTypeSetting001/delete', object,"AssetTypeSetting001_btn_delete", "AssetTypeSetting002", function(){
                    tableA.reload();//删除事件请求及回调函数
                });
            });
        })
    });

            /**
             * 批量删除按钮
             */
            $('#AssetTypeSetting001_btn_delete').click(function() {
                if(tableA!=null && tableA!=''){
                    var selArr = tableA.getAllCheckedRows();
                }
                if (!selArr || selArr.length == 0) {
                    Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                    return;
                }
                var object = {selectedItem: selArr, operateType:"delete"};//请求参数
                        Joyin.ajaxJson(basePath + 'chb/assettypesetting/AssetTypeSetting001/check', object, function(result) {//请求路径，请求参数回调函数
                            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("删除[资产一级分类]及配置的所有对应中债资产类型")), function(){//删除事件的请求
                                WorkFlow.doDeleteList(basePath + 'chb/assettypesetting/AssetTypeSetting001/delete', object,"AssetTypeSetting001_btn_delete", "AssetTypeSetting002", function(){
                                    tableA.reload();//删除事件请求及回调函数
                                });
                            });
                        })
            });


    /**
     * 右侧列表： 点击左侧数据触发查询
     * @type {jQuery}
     */
    var tableB = $('#dataTable2').joyintable({
        ajax : {
            url : 'selectById',
            type : 'POST',
            data : function() {
                return {typeuuid: str};
            }
        },
        title: '中债资产类型',
        checkbox : false,
        cols : [
            {field: 'typeuuid', title: "主键",width:120 ,visible:false},
            {field: 'mappinguuid', title: "主键",width:120 ,visible:false},
            {field: 'chbAssetTypeStr', title: "资产/负债类别",width:150 ,sortField:'chbAssetType'},//资产负债类别
            {field: 'chbAssetType', title: "资产/负债类别",width:150 ,visible:false},
            {field: 'chbAssetType2Str', title: "资产/负债二级类别",width:150 ,sortField:'chbAssetType2'},//资产负债二级类别
            {field: 'chbAssetType2', title: "资产/负债二级类别",width:150 ,visible:false},
            {field: 'operation', title: "操作",type: 'action',buttons: ['delt']}//操作
        ]
    });

    tableB.bind('click', '.delt', function(data, index) {
        var object = {selectedItem: [data], operateType:"delete"};//请求参数
        Joyin.ajaxJson(basePath + 'chb/assettypesetting/AssetTypeSetting001/check', object, function(result) {//请求路径，请求参数回调函数
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("删除[资产一级分类]下的[资产二级分类]配置")), function(){//删除事件的请求
                WorkFlow.doDeleteList(basePath + 'chb/assettypesetting/AssetTypeSetting001/doExDelete', object,"AssetTypeSetting001_btn_delete", "AssetTypeSetting002", function(){
                    tableB.reload();//删除事件请求及回调函数
                });
            });
        })
    });

        /**
         * 导出事件
         */
        var exportExcel = function() {
            var paramObj = getSearchParams();
            Joyin.exportExcel('export', paramObj);
        };


    /**
     * 画面加载完成
     */
    $(function () {
          // 绑定重置按钮事件
          $('#AssetTypeSetting001_btn_reset').click(function(){
               window.location.reload();
          });
        //导出按钮隐藏
//        $('#AssetTypeSetting001_btn_export').hide();
        //筛选按钮隐藏
//        $('#AssetTypeSetting001_btn_filter').hide();
        // 查询
        $('#AssetTypeSetting001_btn_search').click(function() {
            tableA.load();
            // tableB.load();
        });
        // 新建
        $('#AssetTypeSetting001_btn_create').click(function() {
            Joyin.dialog(JoyinUtil.getLabel("资产/负债类别配置新建"),
                basePath + "/chb/assettypesetting/AssetTypeSetting002/init",
                "85%", "85%", {pageModel:PAGE_MODEL_NEW});
        });
        // 导出
        $('#AssetTypeSetting001_btn_export').click(function() {
            exportExcel();
        });
    });
}());