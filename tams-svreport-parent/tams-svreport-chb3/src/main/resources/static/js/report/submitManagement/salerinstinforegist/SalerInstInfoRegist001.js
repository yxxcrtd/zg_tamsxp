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
            if(salerOrgCode){
                table.resize($(document).height() - $('.container').height() + table.getHeight());
                var addTableData = table.getAllRows();
                //标志是否在当前页选中数据
                var isChecked = false;
                if(addTableData){
                    $(addTableData).each(function(i,v){
                        if(salerOrgCode===v.salerOrgCode && v.sendStatusName==='未报送'){
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
            ,{field: 'submitTime', title: "报送日期", type: 'datetime',width:150,sortField:'submitTime'}
            ,{field: 'submitTimeStr', title: "报送日期",visible:false}
            ,{field: 'chbSubmitDeadline', title: "报送截止日", type:'date', width:100,sortField:'chbSubmitDeadline'}
            ,{field: 'submitFeedbackStatusStr', title: "反馈状态", width:100,sortField:'submitFeedbackStatus'}
            ,{field: 'salerInstCode', title: '销售机构代码', width:200,sortField:'salerInstCode'}
            ,{field: 'salerOrgCode', title: '总部或分支机构编码', width:200,sortField:'salerOrgCode'}
            ,{field: 'salerOrgName', title: '总部或分支机构名称', width:200,sortField:'salerOrgName'}
            ,{field: 'salerOrgLevel', title: '机构层级', width:200,sortField:'salerOrgLevel'}
        ],
    });

        // 查询按钮点击事件
        $('#SalerInstInfoRegist001_btn_search').click(function() {
            table.load();
        });

        //导出中债Excel
        $('#SalerInstInfoRegist001_btn_chbexport').click(function() {
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
        $('#SalerInstInfoRegist001_btn_manual').click(function(){
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
       $('#SalerInstInfoRegist001_btn_canManual').click(function() {
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