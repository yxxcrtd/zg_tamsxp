
/**
 * Created by Administrator on 2017/8/15 0015.
 */
// By zhengyan
$(function(){
    //跳转的页码
    var pageCount = 1;
    var getPageParams = function() {
        /* 产品名称*/
        var prodId = $('#prodId').val();
    	if (prodId) {
    		prodId = prodId.join();
    	}
    	 /*产品登记编码*/
        var prodRegistCode=$('#prodRegistCode').val();
        if(prodRegistCode){
            prodRegistCode=prodRegistCode.join();
        }
    	/*报送状态*/
        var submitStatus=$('#submitStatus').val();
        if(submitStatus){
            submitStatus=submitStatus.join();
        }
    	/*数据状态*/
        var status=$('#status').val();
        if(status){
            status=status.join();
        }
        /*反馈状态*/
        var submitFeedbackStatus=$('#submitFeedbackStatus').val();
        if(submitFeedbackStatus){
            submitFeedbackStatus=submitFeedbackStatus.join();
        }
       
        return {
            // 报送状态
            submitFeedbackStatus : submitFeedbackStatus,
            // 反馈状态
            submitStatus : submitStatus,
            // 数据状态
            status : status,
            //报送日开始
            submitDate : $('#submitDate').startDate(),
            //报送日结束
            submitDateEnd : $('#submitDate').endDate(),
            //数据日期开始日
            updateTime : $('#updateTime').startDate(),
            //数据日期截止日
            updateTimeEnd : $('#updateTime').endDate(),
            //报送截止日开始
            submitDeadLineStart : $('#submitDeadLine').startDate(),
            //报送截止日结束
            submitDeadLineEnd : $('#submitDeadLine').endDate(),
            //产品名称
            prodId: prodId,
            //产品登记编码
            prodRegistCode:prodRegistCode,
            //募集起始日开始日
            raiseVdateStart:$('#raiseVdate').startDate(),
            //募集起始日结束日
            raiseVdateEnd: $('#raiseVdate').endDate(),
            //产品起始日开始
            prdVdateStart:$('#prdVdate').startDate(),
            //产品起始日结束
            prdVdateEnd: $('#prdVdate').endDate()
        };
    };
    var table = $('#prodPersonIssue').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        showDetail: true,//双击详情对比画面
        done :function(){
            if(indentifyMark){
                table.resize($(document).height() - $('.container').height() + table.getHeight());
                var addTableData = table.getAllRows();
                //标志是否在当前页选中数据
                var isChecked = false;
                if(addTableData){
                    $(addTableData).each(function(i,v){
                        if(indentifyMark===v.indentifyMark && v.submitStatusName==='未报送'){
                            table.checkRow(v.LAY_TABLE_INDEX);
                            dateCheckMethod("checkData");
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
        },
        cols: [
               
            { title: "数据状态", field: 'statusStr',width:100, sortField:'status' },
            { field: 'sendStatus',visible:false},
            { field: 'status',visible:false},
            { field: 'registSeqno',width:0,sortField:'',visible:false},
            { title: "数据日期", field: 'statusDate', type: 'datetime',width:100, sortField:'statusDate' },
            { title: "报送状态", field: 'sendStatusStr',width:100, sortField:'sendStatus',
                render:function (value,visible,row,options) {
                    if(value == "接口报送"){
                        return '<a href="javascript:void(0)" class="sendStatusClass">'+value+'</a>';
                    }else{
                        return value;
                    }
                }},
            { title: "报送日期", field: 'submitTime', type: 'date',width:100, sortField:'submitTime' },
            { title: "报送截止日", field: 'chbSubmitDeadline', type: 'date',width:100, sortField:'chbSubmitDeadline' },
            { title: "反馈状态", field: 'submitFeedbackStatus',width:100, sortField:'submitFeedbackStatus',
                render:function (value,visible,row,options) {
                    if(value == "发送中" || value == "发送异常"){
                        return '<a href="javascript:void(0)" class="feedbackStatusClass">'+value+'</a>';
                    }else{
                        return value;
                    }
                }},
            { title: "产品登记编码", field: 'prodRegCode',width:200, sortField:'prodRegistCode'},
            { title: "理财产品代码", field: 'prodId' ,width:200, sortField:'prodId'},
            { title: "产品名称", field: 'prodName' ,width:200, sortField:'prodName'},
            { title: "发行机构代码", field: 'issuePartyId' ,width:200, sortField:'issuePartyId'},
            { title: "募集起始日期", field: 'raiseVdate',type: 'date',width:200, sortField:'raiseVdate' },
            { title: "募集结束日期",field:'raiseEnddate',type: 'date',width:200, sortField:'raiseEnddate'},
            { title: "产品起始日期", field: 'prdVdate',type: 'date',width:200, sortField:'prdVdate' },
            { title: "产品终止日期", field: 'prdMdate',type: 'date',width:200, sortField:'prdMdate' },
            { title: "管理方式", field: 'manageType',width:200, sortField:'manageType'},
            { title: "是否为结构化（分级）产品", field: 'isConstracte',width:200, sortField:'isConstracte'},
            { title: "分级比例", field: 'gradProportion',width:200, sortField:'gradProportion'},
            { title: "业绩比较基准下限(%)", field: 'yieldLow',type:'number', digit:5, width:200, sortField:'yieldLow'},
            { title: "业绩比较基准上限(%)", field: 'yieldHigh',type:'number', digit:5, width:200, sortField:'yieldHigh'},
            { title: "业绩比较基准说明", field: 'yieldRemark',width:200, sortField:'yieldRemark'},
            /*{ title: "层级类别", field: 'gradeType',width:200, sortField:'gradeType' },
            { title: "预计客户最低年收益率%", field: 'expMinYield',width:200, sortField:'expMinYield'},
            { title: "预计客户最高年收益率%", field: 'expMaxYield',width:200, sortField:'expMaxYield'},
            { title: "平均预计客户年收益率%", field: 'expAvgYield',width:200, sortField:'expAvgYield' },
            { title: "收益率分档情况说明", field: 'yieldComment',width:200, sortField:'yieldComment'},*/
            { title: "开放模式", field: 'openMode',width:200, sortField:'openMode'},
            { title: "规律开放周期", field: 'openCircle',width:200, sortField:'openCircle'},
            { title: "其他规律开放周期（天）", field: 'openCircleDays',width:200, sortField:'openCircleDays'},
            { title: "无规律开放说明", field: 'ruleComment',width:200, sortField:'ruleComment'},
            { title: "首次开放周期起始日", field: 'firstOpenDay',type: 'date',width:200, sortField:'firstOpenDay'},
            { title: "节假日是否开放", field: 'holidayOnFlag', width:200, sortField:'holidayOnFlag'},
            { title: "平均开放次数（年化）", field: 'avgOpenCount',width:200, sortField:'avgOpenCount'},
            {title:"开放期业务", field: 'openBuss', width:200, sortField:'openBuss'},
            {title:"开放期业务说明", field: 'openBussRemark', width:200, sortField:'openBussRemark'},
           /* { title: "资金托管账号", field: 'busiAcctNo',width:200, sortField:'busiAcctNo'},
            { title: "资金托管账户", field: 'acctName',width:200, sortField:'acctName'},*/
            { field: 'sumitSeq', visible:false}
        ]
    });

    // 查询按钮点击事件
    $('#SvrProdPersonIssue001_btn_search').click(function() {
        /*if($('#searchForm').joyinvalid()) {*/
            table.load();
//        }
    });

    //绑定点击跳转反馈文件管理页面
    table.bind('click', '.feedbackStatusClass', function(data, index) {
        Joyin.dialog(JoyinUtil.getLabel("中债反馈文件管理"),
            basePath + "svreport/system/chbfeedbackfilemanagement/ChbFeedbackFileManagement001/init",
            "95%", "96%", {     //pageModel:PAGE_MODEL_REFERENCE,
                reportEtrType:SVR_REPORT_CODE,    //报表类型
                handleStatus:FILE_HANDLE_STATUS,       //处理状态-未处理
                repSeqNo:data.sumitSeq,       //报送流水号
                // reportSendDate:'',
                // reportAcceptDate:''
            });
    });
    //绑定点击跳转发送状态信息查询页面
    table.bind('click', '.sendStatusClass', function(data, index) {
        Joyin.dialog(JoyinUtil.getLabel("发送状态信息查询"),
            basePath + "svreport/system/reportstatusinfo/ReportStatusInfo001/init",
            "95%", "96%", {       //pageModel:PAGE_MODEL_REFERENCE,
                repType:SVR_REPORT_CODE,    //报表类型
                registSeqNo:data.registSeqno,       //报送流水号
            });
    });

    // 导出中债Excel按钮点击事件
    $('#SvrProdPersonIssue001_btn_chbexport').click(function() {
        exportMethod("0");
    });

    // 导出查询结果按钮点击事件
    $('#SvrProdPersonIssue001_btn_export').click(function() {
        exportMethod("1");
    });

    //导出方法
    var exportMethod = function (flag){
        var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
            param = getPageParams();
            param.flag = flag;
            if(flag=="0"){
            	param.status="01";
            	param.submitStatus="01";
            }
            param.exportflag = 'export';
            var isHaveData = true;
            Joyin.ajaxSync('searchPage',param, function(result){
                if (!result || result.root.length == 0){
             	   isHaveData = false;
             	   return;
                }
            })
            
            Joyin.exportExcel("export",param);
            if (isHaveData){
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
        	// 用来标注选择数据中存在修改或者已报送数据，默认为false
			var statuFlag = false;
        	if(flag=="0"){
	        	for(var i=0;i<expArr.length;i++){
//	        		if(expArr[i].status!="01"||expArr[i].sendStatus!="01"){
//	        			Joyin.error("只能导出新建未报送的数据，请重新选择");
//	        			return;
//        			}
	        		// 含有删除状态数据
					if (expArr[i].status=="03") {
						Joyin.error("所选数据中包含数据状态为删除的数据，请重新选择。");
	        			return;
	        			// 含有修改状态数据或者已报送数据，将标志改为true
					} else if (expArr[i].status=="02"||expArr[i].sendStatus!="01") {
						statuFlag = true;
					}
        		}
        	}
            var keyList = [];
            $.each(expArr, function(i, row) {
            	 keyList.push(row.registSeqno);
            });
            param.keyList = JSON.stringify(expArr);
            param.expKeys = keyList.join(',')
            param.flag = flag;
            param.exportflag = 'export';
            if (statuFlag) {
				Joyin.confirm("导出数据包含修改或已报送数据，请确认是否导出！", function() {
					confirmExport(param, expArr);
				});
			} else {
				confirmExport(param, expArr);
			}
//            Joyin.exportExcel("export",param);
//            Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
//            	layui.layer.open({type:3});
//                Joyin.ajaxJson('doManual', {keyList : JSON.stringify(expArr)}, function() {
//                    table.load();
//                    layui.layer.closeAll();
//                });
//            });
        }
    };

    /**
	 * 确认导出之后的操作
	 */
	var confirmExport = function(param, expArr) {
		 Joyin.exportExcel("export",param);
		//判断所选导出的数据是否已经报送送成功，若报送成功，则无需"手工处理"，只对报送失败的数据进行处理
        var isNotSendData = false;
		for(var j=expArr.length-1;j>=0;j--){
		    if(expArr[j].sendStatus=="01" || (expArr[j].sendStatus == '02' && (expArr[j].submitFeedbackStatus == '发送失败' || expArr[j].submitFeedbackStatus == '中债退回'))) {
                  isNotSendData = true;//有未报送成功的数据
            }
            if(!(expArr[j].sendStatus == '01' || (expArr[j].sendStatus == '02' && (expArr[j].submitFeedbackStatus == '发送失败' || expArr[j].submitFeedbackStatus == '中债退回')))){
                   expArr.splice(j,1);
            }
        }
		if (isNotSendData){
      　　　 Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
       　　　	layui.layer.open({type:3});
           　　　Joyin.ajaxJson('doManual', {keyList : JSON.stringify(expArr)}, function() {
              　　　 table.load();
               　　　layui.layer.closeAll();
          　　　 });
       　　　});
		}
	};

    // 导入按钮点击事件
    $('#SvrProdPersonIssue001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
            Joyin.uploadFile(function(fileName) {
                Joyin.ajax('importFile', {fileName: fileName});
            },function() {
                table.load();
            });
        });
    });
    //导出删除模板
    $('#SvrProdPersonIssue001_btn_exportDel').click(function(){
        if(!table.isSearched()) {
            return;
        }
        var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
            param = getPageParams();
            if (param.status==null||param.status==''){
            	param.status='03';
            }
            param.flag = "2";
            param.submitStatus='01';
            Joyin.exportExcel("export",param);
        }
        else{
        	
        	 for(var i=0;i<expArr.length;i++){
                 if(expArr[i].status !="03"||expArr[i].submitStatus !="01"){
                     Joyin.error("只能导出删除状态且未报送的数据，请重新选择");
                     return;
                 }
             }
        	 
            var keyList = [];
            $.each(expArr, function(i, row) {
                keyList.push(row.bankCode + '|' + row.indentifyMark + '|' + row.submitStatus);
            });
            param.expKeys = keyList.join(',')
            param.flag = "2";
            Joyin.exportExcel("exportDel",param);
        }
    });

    /*add by zhangliang 20201019 start*/
    // 数据检查
    $('#SvrProdPersonIssue001_btn_checkData').click(function(){
        var paramObj = getPageParams();
        paramObj.opertionType = "checkData";
        paramObj.sendStatus = paramObj.submitStatus;
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    // 报送
    $('#SvrProdPersonIssue001_btn_submitChb').click(function(){
        var paramObj = getPageParams();
        paramObj.opertionType = "submitChb";
        paramObj.sendStatus = paramObj.submitStatus;
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    // 重新报送
    $('#SvrProdPersonIssue001_btn_reSubmitChb').click(function(){
        var paramObj = getPageParams();
        paramObj.opertionType = "reSubmitChb";
        paramObj.sendStatus = paramObj.submitStatus;
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    /*add by zhangliang 20201019 end*/

    /**
     * 报送发送接口
     */
    var submitChb = function(rows){
        Joyin.confirm("请确认是否要报送选中记录？", function() {
            layui.layer.open({type:3});
            Joyin.ajaxJson('submitChb', {keyList: JSON.stringify(rows)}, function() {
                table.load();
                layui.layer.closeAll();
            });
        })
    
    }
    // 手工处理 
    $('#SvrProdPersonIssue001_btn_manual').click(function(){
        var rows = table.getAllCheckedRows();
        if(rows.length === 0) {
          var submitStatus = $("#submitStatus").val();
          var submitFeedbackStatus = $("#submitFeedbackStatus").val();
          if(!((submitStatus && submitStatus.length == 1 &&submitStatus[0] == '01')
              || (submitStatus && submitStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1 ||  submitFeedbackStatus.indexOf('04')> -1
              || submitFeedbackStatus.indexOf('00')>-1 ||  submitFeedbackStatus.indexOf('06')> -1)))){
            Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送中、发送失败、中债退回和发送异常的数据进行手工处理");
            return;
          }
          var paramObj = getPageParams();
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
                  || (rows[i].sendStatus == '02' && (rows[i].submitFeedbackStatus == '发送失败' || rows[i].submitFeedbackStatus == '中债退回'
                  || rows[i].submitFeedbackStatus == '发送中' || rows[i].submitFeedbackStatus == '发送异常')))) {
                  Joyin.error("只能选择报送状态为未报送的数据，或报送状态为接口报送且反馈状态为发送中、发送失败、中债退回和发送异常的数据进行手工处理，请重新选择！");
                  return;
              }
          }
        var keyList = [];
        keyList = JSON.stringify(rows);
          Joyin.confirm("请确认是否要手工处理选中记录？", function() {
            layui.layer.open({type:3});
              Joyin.ajaxJson('doManual', {keyList: JSON.stringify(rows)}, function() {
                  table.load();
                  layui.layer.closeAll();
              });
          });
        }
        
    });
    //liaochunyu add 2017-12-02 start
    /**
     * 取消手工处理 
     */
   $('#SvrProdPersonIssue001_btn_cancelManual').click(function() {
       var manArr=table.getAllCheckedRows();
       if(!manArr || manArr.length ==0){
    	   var submitStatus = $("#submitStatus").val();
       	   if(!(submitStatus && submitStatus.length==1 && submitStatus[0] == '03')){
       		 Joyin.error("只能选择手工处理的数据，请重新选择！");
                return;
       	   }
       	   var paramObj = getPageParams();
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
    //liaochunyu add 2017-12-02 end
    //报送
    $('#SvrProdPersonIssue001_btn_submit').click(function() {
        var submitArr=table.getAllCheckedRows();
        if(submitArr.length === 0) {
            var paramObj = getPageParams();
            var submitStatus = $('#submitStatus').val();
            var status = $('#status').val();
//            if(submitStatus!='01'){
            if (!(submitStatus && submitStatus.length == 1 && submitStatus[0] == '01')) {
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行报送！");
                return;
            }
//            else if(status && status.includes('03')){
            else if(status && status.indexOf('03') > -1){
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            if(!status){
                paramObj.status = '01,02'; 
            }
            Joyin.ajax('sendBeforeCheck',paramObj, function(result){
                if(!result || result.length ==0){
                    Joyin.confirm("按照已选择当前查询条件报送,是否确认！",function(){
                    	layui.layer.open({type:3});
                        Joyin.ajaxJson('submitChb', getPageParams(), function() {
                            table.load();
                            layui.layer.closeAll();
                        });
                    });
                }
                else{
                    window.checkResult = result;
                    Joyin.checkData(dataTitle, checkUrl, getPageParams());
                }
            })
            return ;
        }
        var keyList = [];
        for(var i=0;i<submitArr.length;i++){
            keyList.push(submitArr[i].bankCode + '|' + submitArr[i].indentifyMark + '|' + submitArr[i].submitStatus+ '|' + submitArr[i].sumitSeq);
            if(submitArr[i].submitStatus !="01"){
                Joyin.error("选择的报送数据中存在已报送数据，请重新选择！");
                return;
            }
            else if(submitArr[i].status =="03" && submitArr[i].submitStatus =="01"){
                Joyin.error("选择的报送数据中存在删除状态的数据，请重新选择！");
                return;
            }
        };

        Joyin.ajax('sendBeforeCheck',{checkKeys:keyList.join(',')}, function(result){
            if(!result || result.length ==0){
                Joyin.confirm("请确认是否要报送选中记录？", function() {
                	layui.layer.open({type:3});
                    Joyin.ajaxJson('submitChb', {submitKeys:keyList.join(',')}, function() {
                        table.load();
                        layui.layer.closeAll();
                    });
                });
            }
            else{
                window.checkResult = result;
                Joyin.checkData(dataTitle, checkUrl, {checkKeys: keyList.join(',')});
            }
        })
//        Joyin.confirm("请确认是否要报送选中记录？", function() {
//            Joyin.ajaxJson('submitChb', {submitKeys:keyList.join(',')}, function() {
//                table.load();
//            });
//        });
    });
    
    // add by etliu 20180824 start
    // 二次报送
    $('#SvrProdPersonIssue001_btn_submitAgain').click(function() {
        var paramObj = getPageParams();
        
        var submitArr = table.getAllCheckedRows();
        if(submitArr.length === 0) {
            var submitStatus = $('#submitStatus').val();
            var status = $('#status').val();
            var submitFeedbackStatus = $('#submitFeedbackStatus').val();
            if (!(submitStatus && submitStatus.length == 1 && submitStatus[0] == '02')) {
                Joyin.error("只能对报送状态为接口报送的数据进行重新报送！");
                return;
            }
//            if(status && status.includes('03')){
            if(status && status.indexOf('03') > -1){
                Joyin.error("只能对数据状态为新建和修改的数据进行重新报送！");
                return;
            }
            if(submitFeedbackStatus && 
                    (submitFeedbackStatus.indexOf('01') > 0
                            || submitFeedbackStatus.indexOf('03') > 0
                            || submitFeedbackStatus.indexOf('05') > 0)) {
                Joyin.error("只能对反馈状态为发送失败和中债退回的数据进行重新报送！");
                return;
            }
            
            if(!status){
                paramObj.status = '01,02'; 
            }
            if (!submitFeedbackStatus) {
                paramObj.submitFeedbackStatus = '02,04'; 
            }
            Joyin.confirm("请确认是否要按照已选择当前查询条件进行二次报送？",function(){
            	layui.layer.open({type:3});
                Joyin.ajaxJson('submitCbAgain', paramObj, function() {
                    table.load();
                    layui.layer.closeAll();
                });
            });
            return ;
        }
        
        var keyList = [];
        for (var i = 0; i < submitArr.length; i++) {
        	var submitFeedbackStatus = submitArr[i].submitFeedbackStatus;
        	var submitStatus = submitArr[i].submitStatus;
        	if(submitStatus&&submitStatus.indexOf('01') > -1){
        		 Joyin.error("未报送的数据不能进行二次报送！");
                 return;
        	}else if(!submitFeedbackStatus){
                Joyin.error("反馈状态为空的数据不能进行二次报送！");
                return;
        	}else if(submitFeedbackStatus&& 
                    (submitFeedbackStatus.indexOf('01') > -1
                            || submitFeedbackStatus.indexOf('03') > -1
                            || submitFeedbackStatus.indexOf('05') > -1)){
                    Joyin.error("只能对反馈状态为发送失败和中债退回的数据进行二次报送！");
                    return;
        	}
            keyList.push(submitArr[i].sumitSeq);
        };
        
        paramObj.keyList = keyList.join(',');
        
        Joyin.confirm("请确认是否要二次报送？",function(){
        	layui.layer.open({type:3});
            Joyin.ajaxJson('submitCbAgain', paramObj, function() {
                table.load();
                layui.layer.closeAll();
            });
        });
    });
    // add by etliu 20180824 end
    
    
    // 上传按钮点击事件
    $('#SvrProdPersonIssue001_btn_upload').click(function() {
        Joyin.confirm("请确认是否要上传？", function() {
            Joyin.uploadFile({exts:'zip'},function(fileName) {
                Joyin.ajax('importFile', {fileName: fileName});
            },function() {
                table.load();
            });
        });
    });
    
    
    // 下载按钮点击事件
    $('#SvrProdPersonIssue001_btn_download').click(function() {
        Joyin.confirm("请确认是否要下载？", function() {
//            Joyin.downloadFile({exts:'zip'},function(fileName) {
//                Joyin.ajax('download', {fileName: fileName});
//            }
            Joyin.ajaxJson('download', paramObj, function() {
                table.load();
                layui.layer.closeAll();
            }
            ,function() {
                table.load();
            });
        });
    });
    
    var init= function(){
        $('#updateTime').val('');
        $('#submitDate').val('');

        if(isMain=='01'){
            setTimeout(function() {
         	   if($('#submitStatus').val()=='02'||$('#submitStatus').val()=='03'){
         		   $('#submitDate').val(searchDataStart+' - '+searchDataEnd);
         	   }else{
         		   $('#submitDeadLine').val(searchDataStart+' - '+searchDataEnd);
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
});