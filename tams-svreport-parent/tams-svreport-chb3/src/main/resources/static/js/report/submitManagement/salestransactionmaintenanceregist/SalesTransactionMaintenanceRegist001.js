$(function(){
    //跳转的页码
    var pageCount = 1;
    var getPageParams = function() {
            var param = $.serializeToObject('#searchForm');
        	param.searchSubmitDeadlineStart = $('#submitDeadline').startDate();
        	param.searchSubmitDeadlineEnd = $('#submitDeadline').endDate();
        	param.searchStatusDateStart = $('#statusDate').startDate();
        	param.searchStatusDateEnd = $('#statusDate').endDate();
        	param.searchSubmitTimeStart = $('#submitTime').startDate();
        	param.searchSubmitTimeEnd = $('#submitTime').endDate();
            return param;
        };

    var table = $('#SalesTransactionMaintenanceRegist').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        showDetail: true,//双击详情对比画面
        done :function(){
            if(institutionCode){
            if(prdRegistCode){
            if(accountNo){
            if(tradeNo){
                table.resize($(document).height() - $('.container').height() + table.getHeight());
                var addTableData = table.getAllRows();
                //标志是否在当前页选中数据
                var isChecked = false;
                if(addTableData){
                    $(addTableData).each(function(i,v){
                        if(institutionCode===v.institutionCode && prdRegistCode===v.prdRegistCode && tradeNo===v.tradeNo &&
                        accountNo===v.accountNo && v.sendStatusName==='未报送'){
                            table.checkRow(v.LAY_TABLE_INDEX);
                            dateCheckMethod();
                            isChecked = true;
                            return false;
                        }
                    })
                }
                if(!isChecked){
                    pageCount++;
                    table.reload({page:{curr:pageCount}});
                }
                }
                }
                }
            }
        },
        cols: [
             { title: "主键uuid",field:'registSeqno',visible:false}
            ,{field: 'status', title: "数据状态",visible:false,sortField:'dataStatus'}
            ,{field: 'dataStatusName', title: "数据状态",width:100,sortField:'dataStatus'}
            ,{field: 'sendStatus', title: "报送状态",visible:false,sortField:'sendStatus'}
            ,{field: 'sendStatusName', title: "报送状态", width:100 ,sortField:'sendStatus'}
            ,{field: 'submitTime', title: "报送日期", type:"datetime", width:150,sortField:'submitTime'}
            ,{field: 'chbSubmitDeadline', title: "报送截止日", type:"date",width:150,sortField:'chbSubmitDeadline'}
            ,{field: 'submitFeedbackStatusStr', title: "反馈状态", width:100,sortField:'submitFeedbackStatus'}
            ,{field:'institutionCode', title: '提供销售结算资金服务机构代码', width:'150',sortField:'institutionCode'}
            ,{field:'prdRegistCode', title: '产品登记编码', width:'150',sortField:'prdRegistCode'}
            ,{field:'accountNo', title: '销售结算资金账号', width:'150',sortField:'accountNo'}
            ,{field:'accountName', title: '销售结算资金账户名称', width:'150',sortField:'accountName'}
            ,{field:'tradeNo', title: '销售结算资金交易流水号', width:'150',sortField:'tradeNo'}
            ,{field:'tradePs', title: '销售结算资金流动方向', width:'150'}
            ,{field:'tradePsName', title: '销售结算资金流动方向', visible:false}
            ,{field:'tradeCounterAccNo', title: '销售结算资金交易对手方账号', width:'150',sortField:'tradeCounterAccNo'}
            ,{field:'tradeCounterAccName', title: '销售结算资金交易对手方账户名称', width:'150',sortField:'tradeCounterAccName'}
            ,{field:'tradeCounterAccBankName', title: '销售结算资金交易对手方开户行名称', width:'150',sortField:'tradeCounterAccBankName'}
            ,{field:'tradeCounterAccBankNo', title: '销售结算资金交易对手方账户开户行号', width:'150',sortField:'tradeCounterAccBankNo'}
            ,{field:'tradeCcy', title: '销售结算资金交易币种', width:'150',sortField:'tradeCcy'}
            ,{field:'tradeAmt', title: '销售结算资金交易金额', width:'150',sortField:'tradeAmt',type:'money',digit: 2}
            ,{field:'tradeVdate', title: '销售结算资金交易日期', width:'150',sortField:'tradeVdate',type:'date'}
            ,{field:'tradeTime', title: '销售结算资金交易时间', width:'150',sortField:'tradeTime'}
            ,{field:'postscript', title: '销售结算资金交易附言', width:'150',sortField:'postscript'}
            ,{field:'remark', title: '备注', width:'150',sortField:'remark'}
        ]
    });

    // 查询按钮点击事件
    $('#SalesTransactionMaintenanceRegist001_btn_search').click(function() {
        table.load();
    });

        //导出所有查询结果
        $('#SalesTransactionMaintenanceRegist001_btn_export').click(function() {
            exportExcel("1");
        });

        //导出中债Excel
        $('#SalesTransactionMaintenanceRegist001_btn_chbexport').click(function() {
        var rows = table.getAllCheckedRows();
        if(rows.length === 0){
            exportExcel("01");
        }
        else{
            exportExcel("0");
        }
        });

    //数据检查
    $('#SalesTransactionMaintenanceRegist001_btn_checkData').click(dateCheckMethod);
    function dateCheckMethod(){
        var checkArr=table.getAllCheckedRows();
        if(checkArr.length === 0) {
            var sendStatus = $('#sendStatus').val();
            var dataStatus = $('#dataStatus').val();
            if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '01')) {
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行数据检查！");
                return;
            }
            else if(dataStatus && dataStatus.indexOf('03') > -1){
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            var paramObj = getPageParams()
            if(!dataStatus){
                paramObj.dataStatus = '01,02';
            }
            Joyin.ajax('reportCheck',paramObj, function(result){
                window.checkResult = result;
                Joyin.checkData(dataTitle, checkUrl, getPageParams());
            })
            return ;
        }
        var keyList = [];
        for(var i=0;i<checkArr.length;i++){
            keyList.push(checkArr[i].institutionCode + '|' + checkArr[i].tradeNo + '|' + checkArr[i].sendStatus );
            if(checkArr[i].sendStatusName !="未报送"){
                Joyin.error("数据检查只能检查未报送数据，请重新选择！");
                return;
            }
            else if(checkArr[i].status && checkArr[i].status.indexOf('03') > -1){
                Joyin.error("选择的报送数据中存在删除状态的数据，请重新选择！");
                return;
            }
        };
        Joyin.ajax('reportCheck',{checkKeys:keyList.join(',')}, function(result){
            window.checkResult = result;
            Joyin.checkData(dataTitle, checkUrl, {checkKeys: keyList.join(',')});
        })

    }
        /**
         * 手工处理
         */
        $('#SalesTransactionMaintenanceRegist001_btn_manual').click(function(){
            var rows = table.getAllCheckedRows();
            if(rows.length === 0) {
            	var sendStatus = $("#sendStatus").val();
            	var submitFeedbackStatus = $("#submitFeedbackStatus").val();
            	if(!((sendStatus && sendStatus.length == 1 &&sendStatus[0] == '01')
            			|| (sendStatus && sendStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1 ||  submitFeedbackStatus.indexOf('04')> -1
                    || submitFeedbackStatus.indexOf('00')>-1 ||  submitFeedbackStatus.indexOf('06')> -1)))){
            		Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送中、发送失败、中债退回和发送异常的数据进行手工处理");
            		return;
            	}
            	var paramObj = getSearchParams();
            	paramObj.flag = '01';
            	Joyin.confirm("请确认是否要手工处理符合查询条件的全部记录？", function() {
            		layui.layer.open({type:3});
                     Joyin.ajaxJson('doManual', paramObj, function() {
                         table.load();
                         layui.layer.closeAll();
                     });
                 });
            }else{
            	for(var i=0;i<rows.length;i++){
                  if (!(rows[i].sendStatus == '01'
                      || (rows[i].sendStatus == '02' && (rows[i].submitFeedbackStatus == '02' || rows[i].submitFeedbackStatus == '04'
                      || rows[i].submitFeedbackStatus == '00' || rows[i].submitFeedbackStatus == '06')))) {
                      Joyin.error("只能选择报送状态为未报送的数据进行手工处理，请重新选择！");
                      return;
                  }
              }

              Joyin.confirm("请确认是否要手工处理选中记录？", function() {
            	  layui.layer.open({type:3});
                  Joyin.ajaxJson('doManual', {keyList: JSON.stringify(rows)}, function() {
                      table.load();
                      layui.layer.closeAll();
                  });
              });
            }

        });

   /**
    * 取消手工处理
    */
       $('#SalesTransactionMaintenanceRegist001_btn_canManual').click(function() {
           var manArr=table.getAllCheckedRows();
           if(!manArr || manArr.length ==0){
        	   var sendStatus = $("#sendStatus").val();
           	   if(!(sendStatus && sendStatus.length==1 && sendStatus[0] == '03')){
           		 Joyin.error("只能选择手工处理的数据，请重新选择！");
                    return;
           	   }
           	   var paramObj = getSearchParams();
           	   paramObj.flag = '01';
           	   Joyin.confirm("请确认是否要取消手工处理符合查询条件所有记录？", function() {
           		layui.layer.open({type:3});
                Joyin.ajaxJson('cancelMannul', paramObj, function() {
                    table.load();
                    layui.layer.closeAll();
                	});
           	   });
           }else{
        	   for(var i=0;i<manArr.length;i++){
                   if(manArr[i].sendStatus !="03"){
                       Joyin.error("只能选择手工处理的数据，请重新选择！");
                       return;
                   }
               };
                Joyin.confirm("请确认是否要取消手工处理选中记录？", function() {
                	layui.layer.open({type:3});
                    Joyin.ajaxJson('cancelMannul', {keyList: JSON.stringify(manArr)}, function() {
                        table.load();
                        layui.layer.closeAll();
                    });
               });
           }

       });

    var init= function(){
        if(isMain=='01'){
            setTimeout(function() {
                if($('#sendStatus').val()=='02'||$('#sendStatus').val()=='03'){
                    $('#submitTime').val(searchDataStart+' - '+searchDataEnd);
                }else{
                    $('#submitDeadline').val(searchDataStart+' - '+searchDataEnd);
                }
                table.load();
            }, 500);
        }

        //报送前检查调用数据检查
        if(indentifyMark){
            setTimeout(function() {
                table.load();
            },500)
        }
    };
    init();
    
    function doManual(message,param){
    	Joyin.confirm(message, function() {
    		Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
    			layui.layer.open({type:3});
	            Joyin.ajax('update', {
	            	flag:'01',
                	key: JSON.stringify(param),
                	selectedBatchGroup: '3073',
    				selectedProductGroup: '',
    				startDate: new Date(),
    				endDate: new Date(),
    				batchGroupType: '1',
    				prodGroupType: '0',
    				detailName: 'SFSGCL'
	            }, function() {
	                table.load();
	                layui.layer.closeAll();
	            });
    		});
            }); 
    }

    /**
     * 导出
     */
    var exportExcel = function(flag) {
    	var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
            param = getPageParams();
            param.flag = flag;
            if(flag=="01"){
                param.dataStatus="01,02";
                param.sendStatus="01";
            }
             var isHaveData = true;
                         Joyin.ajaxSync('searchPage',param, function(result){
                             if (!result || result.root.length == 0){
                                  isHaveData = false;
                                  return;
                              }
                         })
            param.exportflag = 'export';
            Joyin.exportExcel("export",param);
             if(isHaveData){
            Joyin.confirm("导出成功，是否自动标识新建未报送数据的报送状态为“手工处理”？", function() {
                //页面添加滚动条
                layui.layer.open({type:3});
                Joyin.ajaxJson('doManual', param, function() {
                    table.load();
                    layui.layer.closeAll();
                });
            });
            }
        }
        else{
        	if(flag=="0")
        		{
        	for(var i=0;i<expArr.length;i++){
        		if(expArr[i].dataStatus=="03"){
        			Joyin.error("只能导出新建或修改状态的数据，请重新选择");
        			return;
        			}
        		}
        		}
            var keyList = [];
            param.keyList = JSON.stringify(expArr);
            param.expKeys = keyList.join(',')
            param.flag = flag;
            param.exportflag = 'exportSelected';
            Joyin.exportExcel('export', param);
            //判断所选导出的数据是否已经报送送成功，若报送成功，则无需"手工处理"，只对报送失败的数据进行处理
            var isNotSendData = false;
            for(var j=expArr.length-1;j>=0;j--){
                if(expArr[j].sendStatus=="01" || (expArr[j].sendStatus == '02' && (expArr[j].submitFeedbackStatus == '发送失败' || expArr[j].submitFeedbackStatus == '中债退回'))) {
                      isNotSendData = true;//有未报送成功的数据
                }
            }
            if (isNotSendData){
                Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
                    //页面添加滚动条
                    layui.layer.open({type:3});
                    Joyin.ajaxJson('doManual', param, function() {
                        table.load();
                        layui.layer.closeAll();
                    });
                });
            }
        }

    };
});