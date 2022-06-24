$(function() {
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        var exchargeCode = $('#exchargeCode').val();
        if (exchargeCode) {
            exchargeCode = exchargeCode.join();
        }
        return {
        	exchargeCode: exchargeCode,
            status: $('#status').val(),
        	tradeDateBegin: $('#tradeDate').startDate(),
        	tradeDateEnd: $('#tradeDate').endDate()
        };
    };

    /**
     * 定义数据表格
     */
    var table=$("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return getSearchParams();
            }
        },
        dblclick:function(table, data) {

            Joyin.dialog(JoyinUtil.getLabel("SysCommon.refrence"),
                    basePath + "chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo002/init",
                    "95%", "80%", {pageModel:PAGE_MODEL_REFERENCE,
            	                    exchargeCode:data.exchargeCode,tradeDate:data.tradeDateStr,
                                    datasourceType:data.datasourceType,status:data.statusValid,datauuid:data.datauuid});
        },            
        cols:[{field:'statusValidStr',width:80, title: '数据状态',sortable:false}
             ,{field:'exchargeCode',width:120,  title: '货币对',sortable:false}
            ,{field:'tradeDateStr',width:120,  title: '交易日期',type:'date'}
            ,{field:'openPrice',width:100, title: '开盘价',sortable:false,type:'money',digit: 5}
            ,{field:'closePrice',width:100, title: '收盘价',sortable:false,type:'money',digit: 5}
            ,{field:'midPrice',width:100, title: '中间价',sortable:false,type:'money',digit: 5}
            ,{field:'highPrice',width:100, title: '最高价',sortable:false,type:'money',digit: 5}
            ,{field:'lowPrice',width:100, title: '最低价',sortable:false,type:'money',digit: 5}
            ,{field:'errMsg',width:200, title: '错误信息',sortable:false}
            ,{field:'updateTimeStr',width:180, title: '更新时间',sortable:false}
            ,{field:'updateUser',width:100, title: '更新人',sortable:false}
             ,{field:'datasourceType',visible: false}
             ,{field:'statusValid',visible: false}
             ,{field:'datauuid',visible: false}
            ,{field:'operation', title: 'SysCommon.operation', type:'action', buttons: ['edit', 'delt']}
        ]
    });
    
    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.pageModel = PAGE_MODEL_EDIT;

        Joyin.dialog("汇率行情信息修改",
            basePath + "chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo002/init",
            "60%", "80%", {pageModel:PAGE_MODEL_EDIT,
                exchargeCode:data.exchargeCode, tradeDate:data.tradeDateStr,
                datasourceType:data.datasourceType,status:data.statusValid,datauuid:data.datauuid},function() {
                table.reload();
            });
    });
    
    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
        var object = {selectedItem: [data], operateType:"DELETE"};
        Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo001/check', object, function(result) {
        	Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                WorkFlow.doDeleteList(basePath + 'chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo001/delete', object, "ChbExchangeQuotationInfo001_btn_delete", "ChbExchangeQuotationInfo002",function() {
                    table.reload();
                });
            });
        });
    });

    var exportExcel = function() {
        var paramObj = getSearchParams();
        Joyin.exportExcel('export', paramObj);
    };

    /**
     * 画面加载完成
     */
    $(function() {
        // 绑定按钮事件
        $('#ChbExchangeQuotationInfo001_btn_search').click(function() {
            table.load();
        });
        $('#ChbExchangeQuotationInfo001_btn_export').click(function() {
            exportExcel();
        });
        $('#ChbExchangeQuotationInfo001_btn_add').click(function() {
        
            Joyin.dialog("汇率行情信息新建",
                        basePath + "chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo002/init",
                        "50%", "90%", {pageModel:PAGE_MODEL_NEW});
        }); 
        $('#ChbExchangeQuotationInfo001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
            for (var i=0;i<selArr.length;i++) {
              if (selArr[i].statusValid=='D'){
                  Joyin.error("存在已删除状态的数据");
                  return;
              }
            }
            var object = {selectedItem: selArr, operateType:"DELETE"};
            Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo001/check', object, function(result) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    WorkFlow.doDeleteList(basePath + 'chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo001/delete', object, "ChbExchangeQuotationInfo001_btn_delete", "ChbExchangeQuotationInfo002",function() {
                        table.reload();
                    });
             });
           });
        });
        
        // 导入按钮点击事件
        $('#ChbExchangeQuotationInfo001_btn_import').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
                Joyin.uploadFile(function(fileName) {
                    Joyin.ajax(basePath + 'chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo001/checkConfirmMsgForImportData', {fileName: fileName}, function(result) {
                        if ($.isArray(result.joyinMessages) && result.joyinMessages.length > 0) {
                            var joyinMessages = "";
                            $.each(result.joyinMessages, function(i, joyinMessage){
                                joyinMessages += joyinMessage.message + "<br />"
                            });
                            Joyin.confirm(joyinMessages, function(){
                                Joyin.ajax(basePath + '/chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo001/importFile', {fileName: fileName},function () {
                                    table.reload();
                                });
                            });
                        }else{
                            Joyin.ajax(basePath + '/chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo001/importFile', {fileName: fileName},function () {
                                table.reload();
                            });
                        }
                    });
                },function() {
                    table.reload();
                });
            });
        });
     });
}());