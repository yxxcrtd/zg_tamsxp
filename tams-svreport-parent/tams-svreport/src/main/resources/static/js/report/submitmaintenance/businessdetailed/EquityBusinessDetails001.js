$(function() {
	var daySelectRangeNum=0;
	/**
     * 获取查询参数
     */
    var getPageParams = function() {
        return {
        	//产品登记编码
        	regCode : $('#regCode').val()==null?null:$('#regCode').val().join(","),
            //产品
        	account : $('#account').val()==null?null:$('#account').val().join(","),
            //识别标志
            indentifyMark : $('#indentifyMark').val(),
            //业务种类
            busiType : $('#busiType').val()==null?null:$('#busiType').val().join(","),
            //权益确认日期开始
            equityConfDateStart : $('#equityConfDate').startDate(),
            //权益确认日期结束
            equityConfDateEnd : $('#equityConfDate').endDate(),
            //渠道
            channel : $('#channel').val()==null?null:$('#channel').val().join(","),
            //数据状态
            dataStatus : $('#status').val()==null?null:$('#status').val().join(","),
            busiSeqno: $('#busiSeqno').val()
        };
    };
    var table = $('#dataTable').joyintable({
    	ajax: {
    		url: 'searchPage',
    		type: 'POST',
    		data: function() {
    		    return getPageParams();
    		}
    	},
        dblclick:function(table, data) {
            data.pageModel=PAGE_MODEL_REFERENCE;
            Joyin.dialog(JoyinUtil.getLabel("SvrEquityBusinessDetails001.reference"),
                    JoyinUtil.getRealPath("/svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails002/init"),
                    "98%", "95%", data,
                    function(){table.reload();});
        },
    	cols: [  					
            { title: "数据状态", field: 'statusStr',sortField:'dataStatus',width:100},
            { title: "发行机构代码", field: 'registBankCode',sortField:'registBankCode',width:120},
            { title: "销售合同号", field: 'contractNo',sortField:'contractNo', width: 150 },
            { title: "核心交易流水号", field: 'busiSeqno',sortField:'busiSeqno', width:150},
            { title: "理财账号", field: 'finAcc',sortField:'finAcc', width:150},
            { title: "客户统一编号", field: 'custContractNo',sortField:'custContractNo', width: 150 },
            { title: "识别标识", field: 'indentifyMark',sortField:'indentifyMark',width:100},
            { title: "客户姓名", field: 'custName',sortField:'custName', width: 150 },
            { title: "交易序列号", field: 'tradeSeq',sortField:'tradeSeq', width: 150 },
            { title: "关联活期存款账号", field: 'fundAcc',sortField:'fundAcc',width: 150 },
            { title: "关联活期存款账号开户行代码", field: 'fundAccCode',sortField:'fundAccCode',width: 150 },
            { title: "关联活期存款账号开户行名称", field: 'fundAccName',sortField:'fundAccName',width: 150 },
            { title: "关联账号开户所在地", field: 'fundAccPlace',sortField:'fundAccPlace',width:200},
            { title: "是否代销", field: 'isConsign',sortField:'isConsign',width: 100 },
            { title: "代销机构代码", field: 'agencyCode',sortField:'agencyCode',width: 150 },
            { title: "代销机构名称", field:'agencyName',sortField:'agencyName',width: 200},
            { title: "代销机构所属监管机构", field: 'agencyFsa',sortField:'agencyFsa', width: 200},
            { title: "产品代码", field:'account',sortField:'regCode',width:150},
            { title: "产品登记编码", field:'regCode',sortField:'regCode',width:150},
            { title: "业务种类", field: 'busiType',sortField:'busiType',width:100},
            { title: "业务发生地所属监管", field: 'busiFsa',sortField:'busiFsa', width:200},
            { title: "业务确认日期", field:'eQuityConfDateStr',sortField:'equityConfDate',width:150},
            { title: "业务确认时间", field: 'shareConfDatetime',sortField:'shareConfDatetime',width:150},
            { title: "币种", field:'ccy',sortField:'ccy',width:100},
            { title: "金额", field: 'amount',sortField:'amount',width:120,type:'number'},
            { title: "折算人民币金额", field:'amountCny',sortField:'amountCny',width:120,type:'number'},
            { title: "确认净值", field: 'prodValue',sortField:'prodValue',width:120,type:'number', digit:5},
            { title: "份额", field:'shares',sortField:'shares',width:120,type:'number', digit:5},
            { title: "费用", field: 'feeAmt',sortField:'feeAmt', width: 120,type:'number'},
            { title: "渠道", field: 'channel',sortField:'channel', width: 120 },
            { title: "交易柜员号", field: 'tranTellName',sortField:'tranTellName', width: 150},
            { title: "备注", field: 'remark', sortable:false, width: 200},
            { title: "导入检查结果", field: 'interfCheckResult', sortField:'interfCheckResult', width: 150},
            { field: 'dataStatus',visible:false},
            { title:'操作', field: 'action', type:'action', buttons: ['edit', 'delt']}
        ],
    });
    /**
     * 行内编辑按钮事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.pageModel = PAGE_MODEL_EDIT;
        data.operateType = "edit";
        Joyin.ajaxJson(basePath + 'svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails001/check', data, function(result) {
            Joyin.dialog(JoyinUtil.getLabel("SvrEquityBusinessDetails002.edit"),
                    basePath + "svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails002/init",
                    "98%", "95%", data,function(){
                table.reload();
            });
        });
    });
    
    /**
     * 行内删除按钮事件
     */
    table.bind('click', '.delt', function(data, index) {
        var object = {selectedItem: [data], operateType:"delete"};
        deleteList(object);
    });

    var deleteList=function(param){
        Joyin.ajaxJson(basePath + 'svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails001/check', param, function(result) {
            Joyin.confirm(JoyinUtil.getMessage("REPORT_COMMON_C_0003",JoyinUtil.getLabel("SysCommon.delete")), function(){
                Joyin.ajaxJson(basePath + 'svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails001/delete', param,
                        function(){
                	table.reload();
                });
            });
        });
    }
    
    // 导入按钮点击事件
    var importExcel = function() {
        Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'), function() {
            Joyin.uploadFile(function(fileName) {
                Joyin.ajax('checkConfirmMsgForImportData', {fileName: fileName}, function() {
                    Joyin.ajax('importFile', {fileName: fileName});
                },function() {
                    $('iframe').contents().find("#loading").hide();
                });
            },function() {
                table.load();
            });
        });
    };
    
    //导入代销数据点击事件
    $('#SvrEquityBusinessDetails001_btn_chbImport').click(function() {
    	Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'), function() {
    		var options = {};
    		options.pageId = "EquityBusinessDetails001_2";
            Joyin.uploadFile(options, function(fileName) {
            	Joyin.ajax('checkConfirmMsgForChbImportData', {fileName: fileName}, function() {
	            	JoyinProgress.showInDialog('importProgressBar');
	                Joyin.ajax('chbImportFile', {fileName: fileName});
            	},function() {
                    $('iframe').contents().find("#loading").hide();
                });
            },function() {
                table.reload();
            });
        });
    });

    // 导出按钮点击事件
    var exportExcel = function() {
//        var selectedItem = table.getAllCheckedRows();
//        var param={};
//        if(selectedItem.length==0){
//        	param= getPageParams();
//        }else{
//        	param.keyList = JSON.stringify(selectedItem);
//        }
//        Joyin.exportExcel('export', param);
        
        
    	if (table != null && table != '') {
            var selArr = table.getAllCheckedRows();
            var datas = JSON.stringify(selArr);
        }
        if (!selArr || selArr.length == 0) {
        	var holdDateStart=$('#equityConfDate').startDate();
        	var holdDateEnd=$('#equityConfDate').endDate();
        	if(holdDateStart.length == 0 ){
        		Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0018"));
        		return;
        	}
        	var start=Date.parse(holdDateStart);
        	var end=Date.parse(holdDateEnd);
        	dateSpan = end - start;
        	dayDiff = Math.floor(dateSpan / (24 * 3600 * 1000))+1;
        	
        	if(dayDiff>daySelectRangeNum){
        		//比较选择的日期范围
        		Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0019"));
                return;
        	}else if(dayDiff==1){
        		param= getPageParams();
            	Joyin.exportExcel('export', param);
        	}else{
        		Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0020"), function() {
            		param= getPageParams();
                	Joyin.exportExcel('export', param);
        		});
        	}
        } else {
            Joyin.exportExcel('export', {
                datas : datas
            });
        }
    };
    
    
    /**
     * 页面初始加载 zhengxiaolian
     */
     function init(){
         if(isFromMain=="01"){
             $('#status').val("A").trigger("change")
             //业务首页跳转
              $('#equityConfDate').val(searchDataStart+' - '+searchDataEnd);
             setTimeout(function() {
             	if($('#searchForm').joyinvalid()) {
             		table.load();
             	}
             },100);
         }
         daySelectRangeNum=parseInt(daySelectRangeStr);
     }
    
   
    /**
     * 画面加载完成
     */
    $(function() {
    	init();
        // 查询按钮点击事件
        $('#SvrEquityBusinessDetails001_btn_search').click(function() {
            table.load();
        });
        $('#SvrEquityBusinessDetails001_btn_import').click(function() {
            importExcel();
        });
        $('#SvrEquityBusinessDetails001_btn_export').click(function() {
            exportExcel();
        });
        $('#SvrEquityBusinessDetails001_btn_delete').click(function() {
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
});