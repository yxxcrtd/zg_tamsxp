/**
 * 证券账户管理
 */
$(function() {
    $('#dealdate').val(sysDate+' - '+sysDate);

    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return {
            prodName : $('#prodName').val() == null ? null : $(
                '#prodName').val().join(","),
            regCode : $('#regCode').val() == null ? null : $(
                '#regCode').val().join(","),
            assetCode : $('#assetCode').val() == null ? null : $(
                '#assetCode').val().join(","),
            tradeCode:$('#tradeCode').val(),
            status:$('#status').val(),
            tradeCounter:$('#tradeCounter').val(),
            dealDateStart: $.trim($('#dealdate').startDate()),
            dealDateEnd: $.trim($('#dealdate').endDate()),
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
        dblclick: function(table, data){

            Joyin.dialog(JoyinUtil.getLabel("CchbInvestTrade002"),
                basePath + "/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002/init",
                "98%", "95%", {
                    pageModel: PAGE_MODEL_REFERENCE,
                    tradeSeqno: data.tradeSeqno,
                    statusValid: data.statusValid
                },
                function(){table.reload();});

        },


        cols:[
            {field:'tradeSeqno',visible:false}
            ,{field:'statusValid',visible:false}
            ,{field:'statusValidStr', sortable:false, width:100, title: 'ChbInvestTrade001.status'}
            ,{field:'prodId',sortable:false,width:100,  title: 'ChbInvestTrade001.prodId'}
            ,{field:'regCode', sortable:false,width:120,  title: 'ChbInvestTrade001.regCode'}
            ,{field:'registBankCode',sortable:false,width:120,title: 'ChbInvestTrade001.registBankCode'}
            ,{field:'tradeCode',sortable:false,width:120,title: 'ChbInvestTrade001.tradeCode'}
            ,{field:'assetCode', sortable:false,width:180, title: 'ChbInvestTrade001.assetCode'}
            ,{field:'assetName',sortable:false,width:100,title: 'ChbInvestTrade001.assetName'}
            ,{field:'ccyStr', sortable:false, width:100,title: 'ChbInvestTrade001.ccy'}
            ,{field:'amt', sortable:false, width:100,title: 'ChbInvestTrade001.amt'}
            ,{field:'cnyAmt', sortable:false, width:100, title: 'ChbInvestTrade001.cnyAmt'}
            ,{field:'num',sortable:false,   width:50,title: 'ChbInvestTrade001.num'}
            ,{field:'assetMethodStr',sortable:false,width:100, title: 'ChbInvestTrade001.assetMethod'}
            ,{field:'curAssetTypeStr', sortable:false,width:100,title: 'ChbInvestTrade001.curAssetType'}

            ,{field:'curAssetdetType', sortable:false, width:100, title: '资金流动类型说明'}
            ,{field:'dealdateStr',sortable:false,width:160,  title: 'ChbInvestTrade001.dealdate'}
            ,{field:'tradeCounter',sortable:false,width:180,title: 'ChbInvestTrade001.tradeCounter'}
            ,{field:'rivalPartyTypeStr', sortable:false,width:100, title: 'ChbInvestTrade001.rivalPartyType'}
            ,{field:'unitPriceFull',sortable:false,width:100,title: 'ChbInvestTrade001.unitPriceFull'}
            ,{field:'unitPriceNet', sortable:false, width:100,title: 'ChbInvestTrade001.unitPriceNet'}
            ,{field:'endRate', sortable:false, width:100,title: 'ChbInvestTrade001.endRate'}
            ,{field:'contractCode', sortable:false, width:100, title: 'ChbInvestTrade001.contractCode'}
            ,{field:'investApproverId',sortable:false,   width:150,title: 'ChbInvestTrade001.investApproverId'}
            ,{field:'investApproverName',sortable:false,width:100, title: 'ChbInvestTrade001.investApproverName'}
            ,{field:'investTraderId', sortable:false,width:100,title: 'ChbInvestTrade001.investTraderId'}

            ,{field:'investTraderName',sortable:false,width:180,title: 'ChbInvestTrade001.investTraderName'}
            ,{field:'remark', sortable:false,width:100, title: 'ChbInvestTrade001.remark'}
            ,{field:'errMsg',sortable:false,width:100,title: 'ChbInvestTrade001.errMsg'}
            ,{field:'updateTimeStr', sortable:false, width:100,title: 'ChbInvestTrade001.updateTime'}
            ,{field:'createUser', sortable:false, width:100,title: 'ChbInvestTrade001.updateUser'}
            ,{field:'operation', width:100, title: 'SysCommon.operation',sortable:false, type:'action', buttons: ['edit', 'delt']}
        ]
    });

    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.operateType='edit'
        data.pageModel=PAGE_MODEL_EDIT;
        Joyin.confirm("请确认是否修改?", function(){
            Joyin.dialog(JoyinUtil.getLabel("CchbInvestTrade002"),
                basePath + "/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002/init",
                "98%", "95%",data,
                function(){table.reload();});
        });
    });

    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
            WorkFlow.doDeleteList(basePath + 'chb/businessdatamaintain/chbinvesttrade/chbinvesttrade001/delete', object, "CchbInvestTrade001_btn_delete", "CchbInvestTrade002",function(){
                table.reload();
            });
        });
    });
    
    var exportExcel = function() {   
        var paramObj = getSearchParams();
        Joyin.exportExcel('export', paramObj);
    };

    
    var reset = function(conditionArea,table){
        $(conditionArea).find("input").val("");
        $(conditionArea).find("select").val("").trigger("change");// select清空
    }
    /**
     * 画面加载完成
     */
    $(function() {
        //新建
        $('#CchbInvestTrade001_btn_add').click(function() {
            Joyin.dialog(JoyinUtil.getLabel("SysCommon.create")+JoyinUtil.getLabel("CchbInvestTrade002"),
                basePath + "chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002/init",
                "95%", "80%", {pageModel:PAGE_MODEL_NEW});
        });
        // 绑定按钮事件
        $('#CchbInvestTrade001_btn_search').click(function() {
            table.load();
            $('#CchbInvestTrade001_btn_delete').show();
        });
        $('#CchbInvestTrade001_btn_export').click(function() {
            exportExcel();
        });
        $('#CchbInvestTrade001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("没有选中数据"));
                return;
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                WorkFlow.doDeleteList(basePath + 'chb/businessdatamaintain/chbinvesttrade/chbinvesttrade001/delete', object, "CchbInvestTrade001_btn_delete", "CchbInvestTrade002",function(){
                    table.reload();
                });
            });
        });
      
        /** 事件绑定: 置空按钮 */
        $("#CchbInvestTrade001_btn_reset").click(function(event){
            reset( '#selectArea',table);
        });


        // 导入按钮
        $('#CchbInvestTrade001_btn_import').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
                var options = {};
                options.pageId = "ChbInvestTrade001";
                options.exts = 'xls|xlsx';
                Joyin.uploadFile(options,function(fileName) {
                    Joyin.ajax(basePath + 'chb/businessdatamaintain/chbinvesttrade/chbinvesttrade001/checkConfirmMsgForImportData', {fileName: fileName}, function(result) {
                        if ($.isArray(result.joyinMessages) && result.joyinMessages.length > 0) {
                            var joyinMessages = "";
                            $.each(result.joyinMessages, function(i, joyinMessage){
                                joyinMessages += joyinMessage.message + "<br />"
                            });
                            Joyin.confirm(joyinMessages, function(){
                                Joyin.ajax(basePath + '/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade001/importFileSave', {fileName: fileName},function () {
                                    table.reload();
                                });
                            });
                        }else{
                            Joyin.ajax(basePath + '/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade001/importFileSave', {fileName: fileName},function () {
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