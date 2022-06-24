$(function(){
    //跳转的页码
    var pageCount = 1;
    var getPageParams = function() {
            var param = $.serializeToObject('#searchForm');
        	param.searchSubmitDeadlineStart = $('#submitDeadline').startDate();
        	param.searchSubmitDeadlineEnd = $('#submitDeadline').endDate();
        	param.searchDataDateStart = $('#dataDate').startDate();
        	param.searchDataDateEnd = $('#dataDate').endDate();
        	param.searchSubmitTimeStart = $('#submitTime').startDate();
        	param.searchSubmitTimeEnd = $('#submitTime').endDate();
        	if (param.salerCredentType) {
                param.salerCredentType = param.salerCredentType.join(',');
            }
            return param;
        };

    var table = $('#dataTable').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        showDetail: true,//双击详情对比画面
        done :function(){
            if(salerInsNo){
                table.resize($(document).height() - $('.container').height() + table.getHeight());
                var addTableData = table.getAllRows();
                //标志是否在当前页选中数据
                var isChecked = false;
                if(addTableData){
                    $(addTableData).each(function(i,v){
                        if(salerInsNo===v.salerInsNo && v.sendStatusName==='未报送'){
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
        },
        cols: [
             {title: "登记序列",field:'registSeqno',visible:false}
            ,{field: 'status', title: "数据状态",visible:false,sortField:'status'}
            ,{field: 'statusStr', title: "数据状态",width:100,sortField:'status'}
            ,{field: 'sendStatus', title: "报送状态",visible:false,sortField:'sendStatus'}
            ,{field: 'sendStatusStr', title: "报送状态", width:100 ,sortField:'sendStatus'}
            ,{field: 'submitTime', title: "报送日期",type: 'datetime', width:150,sortField:'submitTime'}
            ,{field: 'submitTimeStr', title: "报送日期", visible:false}
            ,{field: 'chbSubmitDeadline', title: "报送截止日", type:'date', width:100,sortField:'chbSubmitDeadline'}
            ,{field: 'submitFeedbackStatusStr', title: "反馈状态", width:100,sortField:'submitFeedbackStatus'}
            ,{field: 'salerInstCode', title: '销售机构代码', width:100,sortField:'salerInstCode'}
            ,{field: 'salerName', title: '姓名', width:100,sortField:'salerName'}
            ,{field: 'salerSex', title: '性别', width:100,sortField:'salerSex'}
            ,{field: 'salerBirthday', title: '出生日期', width:100, type:'date',sortField:'salerBirthday'}
            ,{field: 'salerCredentType', title: '证件类别', width:150,sortField:'salerCredentType'}
            ,{field: 'salerOtherCredentName', title: "其他证件名称",width:100,sortField:'salerOtherCredentName'}
            ,{field: 'salerCredentNo', title: "证件号码",width:100,sortField:'salerCredentNo'}
            ,{field: 'salerInsNo', title: "内部编号", width:100 ,sortField:'salerInsNo'}
            ,{field: 'salerRegistNo', title: "销售人员登记编码", width:150,sortField:'salerRegistNo'}
            ,{field: 'salerAreaType', title: "所属区域",width:100,sortField:'salerAreaType'}
            ,{field: 'salerOrgCode', title: "所属总部或分支机构", width:100,sortField:'salerOrgCode'}
            ,{field: 'salerDept', title: '所属部门', width:100,sortField:'salerDept'}
            ,{field: 'salerPosition', title: '职位', width:100,sortField:'salerPosition'}
            ,{field: 'salerEduLevel', title: '学历', width:100,sortField:'salerEduLevel'}
            ,{field: 'salerDegree', title: '学位', width:100,sortField:'salerDegree'}
            ,{field: 'salerWorkStartYmd', title: '首次参加工作时间', width:100,sortField:'salerWorkStartYmd'}
            ,{field: 'salerFinanceStartYmd', title: "首次从事金融工作时间",width:100,sortField:'salerFinanceStartYmd'}
            ,{field: 'salerStartYmd', title: "首次从事理财销售时间",width:100,sortField:'salerStartYmd'}
            ,{field: 'salerLevle', title: "专业技术职称", width:100 ,sortField:'salerLevle'}
            ,{field: 'salerCertificateType', title: "理财专业证书", width:150,sortField:'salerCertificateType'}
            ,{field: 'salerChbTrainNo', title: "理财登记培训证书编号", width:100,sortField:'salerChbTrainNo'}
            ,{field: 'salerRiskLevel', title: "可销售风险等级", width:100,sortField:'salerRiskLevel'}
            ,{field: 'salerQualification', title: '销售资质', width:100,sortField:'salerQualification'}
            ,{field: 'salerTel', title: '办公电话', width:100,sortField:'salerTel'}
            ,{field: 'salerMobile', title: '移动电话', width:100,sortField:'salerMobile'}
            ,{field: 'salerMail', title: '邮箱', width:100,sortField:'salerMail'}
            ,{field: 'salerStatus', title: '当前状态', width:150,sortField:'salerStatus'}
            ,{field: 'remark', title: '备注', width:150,sortField:'remark'}
        ],
    });

        // 查询按钮点击事件
        $('#SalerIdenInfoRegist001_btn_search').click(function() {
            table.load();
        });

        //导出中债Excel
        $('#SalerIdenInfoRegist001_btn_chbexport').click(function() {
            var rows = table.getAllCheckedRows();
            if(rows.length === 0){
                  exportExcel("01");
            }else{
                   exportExcel("0");
                 }
        });

        /**
         * 手工处理
         */
        $('#SalerIdenInfoRegist001_btn_manual').click(function(){
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
       $('#SalerIdenInfoRegist001_btn_canManual').click(function() {
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
               param.status="01,02";
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
            Joyin.exportExcel("download",param);
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
        		if(expArr[i].status=="03"){
        			Joyin.error("只能导出新建或修改未报送状态的数据，请重新选择");
        			return;
        			}
        		}
        		}
            var keyList = [];
            param.keyList = JSON.stringify(expArr);
            param.expKeys = keyList.join(',')
            param.flag = flag;
            param.exportflag = 'exportSelected';
            Joyin.exportExcel('download', param);
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