/**
 * 销售结算资金交易信息维护
 */
(function(){
    /**
     * 获取查询参数
     */
    var searchform =function(){
        var param = $.serializeToObject('#searchForm');
    	param.searchTradeVdateStart = $('#tradeVdate').startDate();
    	param.searchTradeVdateEnd = $('#tradeVdate').endDate();
        return param;
    };

    /**
     * 新建
     */
    function showDialog() {
        var data={"operateType":"add"};
        Joyin.dialog(JoyinUtil.getLabel('销售结算资金交易信息维护新建'),basePath+'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002/init', '98%', '95%',data,function(){table.reload();});
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
            Joyin.dialog(JoyinUtil.getLabel("销售结算资金交易信息维护查看"),
                    JoyinUtil.getRealPath("report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002/init"),
                    "98%", "95%", data,
                    function(){table.reload();});
        },
        cols:[
             { title: "主键uuid",field:'registSeqno',visible:false}
            ,{ title: "置灰控制",field:'disabledThing',visible:false}
            ,{field:'maintainStatusName', title: '报送信息维护状态', width:'150',sortField:'maintainStatus'}
            ,{field:'dataStatusName', title: '数据状态', width:'150',sortField:'dataStatus'}
            ,{field:'institutionCode', title: '提供销售结算资金服务机构代码', width:'150',sortField:'institutionCode'}
            ,{field:'prdRegistCode', title: '产品登记编码', width:'150',sortField:'prdRegistCode'}
            ,{field:'accountNo', title: '销售结算资金账号', width:'150',sortField:'accountNo'}
            ,{field:'accountName', title: '销售结算资金账户名称', width:'150',sortField:'accountName'}
            ,{field:'tradeCounterAccNo', title: '销售结算资金交易对手方账号', width:'150',sortField:'tradeCounterAccNo'}
            ,{field:'tradeCounterAccName', title: '销售结算资金交易对手方账户名称', width:'150',sortField:'tradeCounterAccName'}
            ,{field:'tradeCounterAccBankNo', title: '销售结算资金交易对手方开户行名称 ', width:'150',sortField:'tradeCounterAccBankNo'}
            ,{field:'tradeCounterAccBankName', title: '销售结算资金交易对手方账户开户行号 ', width:'150',sortField:'tradeCounterAccBankName'}
            ,{field:'tradeCcyName', title: '销售结算资金交易币种', width:'150',sortField:'tradeCcy'}
            ,{field:'tradeAmt', title: '销售结算资金交易金额', width:'150',sortField:'tradeAmt',type:'money',digit: 2}
            ,{field:'tradeTime', title: '销售结算资金交易时间', width:'150',sortField:'tradeTime'}
            ,{field:'tradeNo', title: '销售结算资金交易流水号', width:'150',sortField:'tradeNo'}
            ,{field:'tradePsName', title: '销售结算资金流动方向', width:'150',sortField:'tradePs'}
            ,{field:'tradeVdate', title: '销售结算资金交易日期', width:'150',sortField:'tradeVdate',type:'date'}
            ,{field:'errMsg', title: '错误信息', width:'150',sortField:'errMsg'}
            ,{field:'postscript', title: '销售结算资金交易附言', width:'150',sortField:'postscript'}
            ,{field:'remark', title: '备注', width:'150',sortField:'remark'}
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
        Joyin.ajaxJson(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001/editCheck', data, function(result) {
            Joyin.dialog(JoyinUtil.getLabel("销售结算资金交易信息维护编辑"),
                JoyinUtil.getRealPath("report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002/init"),
                "98%", "95%", data,
           function(){table.reload();});
        });

    });
    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
                    if(data.dataStatus !="E"){
                        return Joyin.error("只能删除有效数据，请重新选择");
                    }
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.ajaxJson(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001/check', object, function(result) {
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                  Joyin.ajaxJson(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001/delete', object, function() {
                         $('#SalesTransactionMaintenance001_btn_search').trigger("click");
                  });
            });
        });
    });
    /**
     * 编辑按钮事件
     */
    $(function () {
        /**
         * 查询按钮
         */
        $('#SalesTransactionMaintenance001_btn_search').click(function() {
            table.load();
            $('#SalesTransactionMaintenance001_btn_delete').show();
        });
        /**
         * 导出按钮
         */
        $('#SalesTransactionMaintenance001_btn_export').click(function() {
            exportExcel();
        });
        /**
         * 新建按钮
         */
        $('#SalesTransactionMaintenance001_btn_add').click(function() {
            showDialog();
        });

    // 导入按钮点击事件
    $('#SalesTransactionMaintenance001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
        var options = {};
        options.pageId = "SalesTransactionMaintenance";
            Joyin.uploadFile(options,function(fileName) {
                Joyin.ajax(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                    Joyin.ajax(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001/importFile', {fileName: fileName});
                });
            },function() {
                table.reload();
            });
        });
    });
//            // 导入按钮点击事件
//            $('#SalesTransactionMaintenance001_btn_import').click(function() {
//                Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'), function() {
//                    Joyin.uploadFile(function(fileName) {
//                        Joyin.ajax('checkConfirmMsgForImportData', {fileName: fileName}, function() {
//                            Joyin.ajax('importFile', {fileName: fileName});
//                        },function() {
//                            $('iframe').contents().find("#loading").hide();
//                        });
//                    },function() {
//                        table.reload();
//                    });
//                });
//            });

        /**
         * 批量删除按钮
         */
        $('#SalesTransactionMaintenance001_btn_delete').click(function() {
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
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001/check', object, function(result) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    Joyin.ajaxJson(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001/delete', object, function() {
                             $('#SalesTransactionMaintenance001_btn_search').trigger("click");
                    });
                });
            });
        });
    });
}());