/**
 * 销售人员奖惩投诉记录信息
 */
(function(){
    /**
     * 获取查询参数
     */
    var searchform =function(){
        var param = $.serializeToObject('#searchForm');
        param.searchSalerRewPenaStartStart = $('#salerRewPenaStart').startDate();
        param.searchSalerRewPenaStartEnd = $('#salerRewPenaStart').endDate();
        param.searchSalerComplainDateStart = $('#salerComplainDate').startDate();
        param.searchSalerComplainDateEnd = $('#salerComplainDate').endDate();
        return param;
    };

    /**
     * 新建
     */
    function showDialog() {
        var data={"operateType":"add"};
        Joyin.dialog(JoyinUtil.getLabel('销售人员奖惩投诉记录信息新建'),basePath+'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002/init', '98%', '95%',data,function(){table.reload();});
    }
    
    /**
     * 定义数据表格
     */
    var table=$("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return searchform();
            }
        },
        dblclick:function(table, data) {
            data.operateType = "dblclick";
            data.pageModel=PAGE_MODEL_REFERENCE;
            Joyin.dialog(JoyinUtil.getLabel("销售人员奖惩投诉记录信息查看"),
                    JoyinUtil.getRealPath("report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002/init"),
                    "98%", "95%", data,
                    function(){table.reload();});
        },
        cols:[
             {field:'erroOrInfo', title:'来自erro还是info',visible:false}
            ,{field:'salerRewPenaNo', title:'奖惩编号',visible:false}
            ,{field:'dataStatusStr', title: '数据状态', width:'150',sortField:'dataStatus'}
            ,{field:'salerInstCode', title: '销售机构代码', width:'150',sortField:'salerInstCode'}
            ,{field:'salerInsNo', title: '内部编号', width:'150',sortField:'salerInsNo'}
            ,{field:'salerRewPenaTyep', title: '记录分类', width:'150',sortField:'salerRewPenaTyep'}
            ,{field:'salerRewPenaName', title: '奖惩名称', width:'150',sortField:'salerRewPenaName'}
            ,{field:'salerRewPenaComments', title: '奖惩说明', width:'150',sortField:'salerRewPenaComments'}
            ,{field:'salerRewPenaInst', title: '奖惩单位', width:'150',sortField:'salerRewPenaInst'}
            ,{field:'salerRewPenaInstName', title: '奖惩单位具体名称', width:'150',sortField:'salerRewPenaInstName'}
            ,{field:'salerRewPenaStart', title: '奖惩起始日期', width:'150',sortField:'salerRewPenaStart',type:'date'}
            ,{field:'salerRewPenaEnd', title: '奖惩结束日期', width:'150',sortField:'salerRewPenaEnd',type:'date'}
            ,{field:'salerComplainDetail', title: '投诉详情', width:'150',sortField:'salerComplainDetail'}
            ,{field:'salerComplainResult', title: '投诉处理结果', width:'150',sortField:'salerComplainResult'}
            ,{field:'salerComplainDate', title: '投诉日期', width:'150',sortField:'salerComplainDate',type:'date'}
            ,{field:'errMsg', title: '错误信息', width:'150',sortField:'errMsg'}
            ,{field:'action', title: '操作' , type:'action',buttons: ['edit', 'delt'] , width:'150'}
        ],
    });
/**
 * 导出表格
 */
    var exportExcel = function() {
        if (table != null && table != '') {
            var selArr = table.getAllCheckedRows();
            var datas = JSON.stringify(selArr);
        }
        if (!selArr || selArr.length == 0) {
            var paramObj = searchform();
            Joyin.exportExcel('export', paramObj);
        } else {
            Joyin.exportExcel('export', {
                datas : datas
            });
        }
    };

                /**
                 * 绑定编辑事件
                 */
                table.bind('click', '.edit', function(data, index) {
                    data.operateType = "edit";
                    data.pageModel=PAGE_MODEL_EDIT;
                    Joyin.dialog(JoyinUtil.getLabel("销售人员奖惩投诉记录信息编辑"),
                            JoyinUtil.getRealPath("report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002/init"),
                            "98%", "95%", data,
                       function(){table.reload();});
                });

    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
                    if(data.dataStatus !="E"){
                        return Joyin.error("只能删除有效数据，请重新选择");
                    }
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo001/check', object, function(result) {
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo001/delete', object, function() {
                                 $('#SalerRewPenaInfo001_btn_search').trigger("click");
                    });
            });
        });
    });

    /**
     * 綁定按钮事件
     */
    $(function () {
        /**
         * 查询按钮
         */
        $('#SalerRewPenaInfo001_btn_search').click(function() {
            table.load();
            $('#SalerRewPenaInfo001_btn_delete').show();
        });
        /**
         * 导出按钮
         */
        $('#SalerRewPenaInfo001_btn_export').click(function() {
            exportExcel();
        });
        /**
         * 新建按钮
         */
        $('#SalerRewPenaInfo001_btn_add').click(function() {
            showDialog();
        });

    // 导入按钮点击事件
    $('#SalerRewPenaInfo001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
        var options = {};
        options.pageId = "SalerRewPenaInfo";
            Joyin.uploadFile(options,function(fileName) {
                Joyin.ajax(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                    Joyin.ajax(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo001/importFile', {fileName: fileName});
                });
            },function() {
                table.reload();
            });
        });
    });
        /**
         * 批量删除按钮
         */
        $('#SalerRewPenaInfo001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
            for(var i=0;i<selArr.length;i++){
                if(selArr[i].dataStatus !="E"){
                   return Joyin.error("只能删除有效数据，请重新选择");
                }
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo001/check', object, function(result) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo001/delete', object, function() {
                                $('#SalerRewPenaInfo001_btn_search').trigger("click");
                    });
                });
            });
        });
    });
}());