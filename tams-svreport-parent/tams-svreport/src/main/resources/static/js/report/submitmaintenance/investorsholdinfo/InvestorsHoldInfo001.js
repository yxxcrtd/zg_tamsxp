$(function() {
    /**
     * 初始化函数
     */
    var init=function(){
        if(isFromMain=='01'){
            $('#holdDate').val(searchDataStart+' - '+searchDataEnd);
        	setTimeout(function() {
                 table.load();
             }, 500);
            //setTimeout(function() {$('#InvestorsHoldInfo001_btn_search').click();},500);
        }
    };
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return{
                //登记银行编码
                registBankCode: $('#registBankCode').val(),
                //产品登记编码
                prdRegistCode:$('#prdRegistCode').val()==null?null:$('#prdRegistCode').val().join(","),
                //识别标识
                indentifyMark:$('#indentifyMark').val(),
                //投资者名称
                holderName:$('#holderName').val(),
                //产品代码
                prodId:$('#ProRegistCodeManage001_prod').val(),
                //机构代码
                orgCode : $('#orgCode').val()==null?null:$('#orgCode').val().join(","),
                //持有日期
                holdDate:$('#holdDate').val(),
//                holdDateStart:$('#holdDate').data("start"),
//                holdDateEnd:$('#holdDate').data("end"),
                //数据状态
                status : $('#status').val()==null?null:$('#status').val().join(",")
            }
    };
    var deleteList=function(param){
        Joyin.ajaxJson(basePath + 'svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo001/checkDelete', param, function(result) {
            Joyin.confirm(JoyinUtil.getMessage("REPORT_COMMON_C_0003",JoyinUtil.getLabel("SysCommon.delete")), function(){
                Joyin.ajaxJson(basePath + 'svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo001/delete', param,
                        function(){
                    $('#InvestorsHoldInfo001_btn_search').trigger('click');
                });
            });
        });
    }
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
//        dblclick:function(table, data) {
//            Joyin.dialog(JoyinUtil.getLabel("SysCommon.refrence"),
//                    basePath + "submitmaintenance/investorsholdinfo/InvestorsHoldInfo002/init",
//                    "95%", "90%", {pageModel:PAGE_MODEL_REFERENCE,
//                                    registBankCode:data.registBankCode,
//                                    prdRegistCode:data.prdRegistCode,
//                                    indentifyMark:data.indentifyMark,
//                                    ccy:data.ccy,
//                                    holdDate:new Date (data.holdDate).format(),
//                                    status:data.status});
//        },
        cols:[
             {field:'status', width:100, title: 'InvestorsHoldInfo001.status'}
            ,{field:'registBankCode', width:120, title: 'InvestorsHoldInfo001.registBankCode'}
            ,{field:'prodid', width:150, title: 'InvestorsHoldInfo001.prodid'}
            ,{field:'prdRegistCode', width:180, title: 'InvestorsHoldInfo001.prdRegistCode'}
            ,{field:'indentifyMark', width:150, title: 'InvestorsHoldInfo001.indentifyMark'}
            ,{field:'ccyCode', width:150, title: 'InvestorsHoldInfo001.ccy'}
            ,{field:'holdDate', width:100, title: 'InvestorsHoldInfo001.holdDate',type:'date'}
            ,{field:'qty', width:150, title: 'InvestorsHoldInfo001.qty',type:'number', digit:5}
            ,{field:'amount', width:150, title: 'InvestorsHoldInfo001.amount',type:'number'}
            ,{field:'cnyAmt', width:180, title: 'InvestorsHoldInfo001.cnyAmt',type:'number'}
            ,{field:'agent', width:180, title: 'InvestorsHoldInfo001.agent'}
            ,{field:'ccy', visible:false}
            ,{field:'statusCode', visible:false}
            ,{field:'dataSource', visible:false}
            ,{field:'interfCheckResult',width:150,title:'InvestorsHoldInfo001.interfCheckResult'}
            ,{field:'operation', title: 'SysCommon.operation', type:'action', buttons: ['edit', 'delt']}
        ]
        ,done: function() {
            $("#dataTable").next('.layui-table-view').find(':checkbox').prop('disabled', false);
        }
    });
    
    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.holdDate = Date.ofDate(data.holdDate).format();
        data.pageModel = PAGE_MODEL_EDIT;
        data.operateType = "edit";
        Joyin.ajaxJson(basePath + 'svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo001/checkDelete', data, function(result) {
            Joyin.dialog(JoyinUtil.getLabel("InvestorsHoldInfo002.edit"),
                    basePath + "svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo002/init",
                    "80%", "70%", data,function(){
                table.reload();
            });
        });
    });
    
    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
        data.holdDate = Date.ofDate(data.holdDate).format();
        if(data.status =="删除"){
            Joyin.error("只能删除有效、待处理数据，请重新选择");
            return;
        }
        var object = {selectedItem: [data], operateType:"delete"};
        deleteList(object);
    });
    // 导入按钮点击事件
    var importExcel = function() {
        Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'),function() {
            Joyin.uploadFile(function(fileName) {
                Joyin.ajax('checkConfirmMsgForImportData', {fileName: fileName}, function() {
                   Joyin.ajax('importFile', {fileName: fileName});
                },function() {
                    $('iframe').contents().find("#loading").hide();
                });
            },function() {
                table.reload();
            });
        });
    };
    

    //导入代销数据点击事件
    $('#InvestorsHoldInfo001_btn_chbImport').click(function() {
    	Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'), function() {
    		var options = {};
    		options.pageId = "InvestorsHoldInfo001_2";
            Joyin.uploadFile(options, function(fileName) {
                Joyin.ajax('checkConfirmMsgForChbImportData', {fileName: fileName}, function() {
                    Joyin.ajax('chbImportFile', {fileName: fileName});
                },function() {
                    $('iframe').contents().find("#loading").hide();
                });
            },function() {
                table.reload();
            });
        });
    });
    
    
    var exportExcel = function() {
        var selectedItem = table.getAllCheckedRows();
        var paramObj={};
        if(selectedItem.length==0){
            paramObj= getSearchParams();
        }
        else{
            var keyList = [];
            $.each(selectedItem,function(i,v){
                keyList.push(v.registBankCode+"|"+v.prodid+"|"+v.indentifyMark+"|"+v.ccy+"|"+Date.ofDate(v.holdDate).format()+"|"
                        +v.statusCode);
            })
            paramObj.keyList = keyList.join(",");
        }
        Joyin.exportExcel('export', paramObj);
    };
    /**
     * 画面加载完成
     */
    $(function() {
        init();
        // 绑定按钮事件
        $('#InvestorsHoldInfo001_btn_search').click(function() {
            table.load();
        });
        $('#InvestorsHoldInfo001_btn_import').click(function() {
            importExcel();
        });
        $('#InvestorsHoldInfo001_btn_export').click(function() {
            exportExcel();
        });
        $('#InvestorsHoldInfo001_btn_add').click(function() {
            Joyin.dialog(JoyinUtil.getLabel("InvestorsHoldInfo002"),
                        basePath + "svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo002/init",
                        "80%", "70%", {pageModel:PAGE_MODEL_NEW},function(){
                            table.reload();
                        });
        });
        $('#InvestorsHoldInfo001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("REPORT_COMMON_E_0009"));
                return;
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            deleteList(object);
        });
    });
}());