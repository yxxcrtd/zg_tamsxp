$(function(){
	// 获取查询参数
	var getSearchParams = function(){
		return {
			pbcAccountId:$('#pbcAccountId').val()==null?null:$('#pbcAccountId').val().join(','),
			prodId:$('#accountId').val()==null?null:$('#accountId').val().join(','),		
			sendStatus:$('#sendStatus').val()==null?null:$('#sendStatus').val().join(','),
			status:$('#status').val()==null?null:$('#status').val().join(','),
			statusDateStart:$('#statusDate').startDate(),
			statusDateEnd:$('#statusDate').endDate(),
			submitTimeStart:$('#submitTime').startDate(),
			submitTimeEnd:$('#submitTime').endDate(),
			submitFeedbackStatus:$('#submitFeedbackStatus').val()==null?null:$('#submitFeedbackStatus').val().join(','),
			pbcSubmitDeadlineDateEnd:$('#pbcSubmitDeadline').endDate(),
			pbcSubmitDeadlineDateStart:$('#pbcSubmitDeadline').startDate(),
			vdateStart:$('#vdate').startDate(),
			vdateEnd:$('#vdate').endDate(),
		};
	}
	// 定义数据表格
	var table=$("#dataTable").joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getSearchParams();
            }
        },
        showDetail: true,//双击详情对比画面
        rowno: false, // 显示行号
        searchOnInit: false, //初始化的时候就请求后台数据
        cols:[	
				{ field: 'registSeqno',visible:false},
				{ title: "数据状态", field: 'statusStr',width:150,sortField:'status'},
				{ title: "数据日期", field: 'statusDate',width:150,type:'datetime',sortField:'statusDate'},
				{ title: "报送状态", field: 'sendStatusStr',width:150,sortField:'sendStatus'},
				{ title: '报送日期',field:'submitTime',width:150,type:'datetime',sortField:'submitTime'},
				{ title: "报送截止日", field: 'pbcSubmitDeadline',width:150, type: 'date',sortField:'pbcSubmitDeadline'},
				{ title: "反馈状态", field: 'submitFeedbackStatusStr',width:150,sortField:'submitFeedbackStatus'},
				{ field: 'pbcAccountId',visible:false},
//				{ title: "资产池代码(内部)", field: 'pbcAccountIdShow',width:150,sortField:''},
//				{ title: "资产池代码(外部)", field: 'pbcCode',width:150,sortField:''},
				{ title: "资产池代码", field: 'pbcAccountIdShow',width:150,sortField:'pbcAccountIdShow'},
				{ title: "反馈资产池代码", field: 'pbcCode',width:150,sortField:'pbcCode'},
				{ title: "内部产品代码", field: 'accountId',width:150,sortField:'accountId'},
				{ title: "发起机构代码", field: 'issuePartyId',width:150,sortField:'issuePartyId'},
				{ title: "资产池名称", field: 'accountName',width:150,sortField:'accountName'},
				{ title: "资产池起始日期", field: 'vdate',width:150,type:'date',sortField:'vdate'},
				{field:'sendStatus', visible:false},
	            {field:'status', visible:false},
	            {field:'submitFeedbackStatus', visible:false}
          ]
	})
	// 查询按钮
	$('#PbcPrdAccountSub_btn_search').click(function(){
		table.load();
	})
	
	// 导出按钮
	$('#PbcPrdAccountSub_btn_chbexport').click(function(){
		var expArr=table.getAllCheckedRows();
		if(expArr != null && expArr.length >0){
			Joyin.confirm('确认导出选中信息？',function(){
				exportExcel(1);
			})
		}else{
			Joyin.confirm('确认导出查询结果？',function(){
				exportExcel(0);
			})
		}
	})
	 /**
     * 导出
     * flag:0 全部导出，选中导出
     */
    var exportExcel = function(flag) {
		if(flag == 0){
	    	 var param = getSearchParams();
	         Joyin.exportExcel("export",param);
		}else{
			var param = {};
			var expArr=table.getAllCheckedRows();
			var keyList = '';
			layui.each(expArr,function(index,data){
				if(index == expArr.length-1){
					keyList = keyList + data.registSeqno+'';
				}else{
					keyList = keyList + data.registSeqno+',';
				}
			})
			param.keyList = keyList;
			Joyin.exportExcel("export",param);
		}
        
    };
    
    
	// 人行Excel导出按钮
	$('#PbcPrdAccountSub_btn_chbpedestrianexport').click(function(){
		var expArr=table.getAllCheckedRows();
		if(expArr != null && expArr.length >0){
			Joyin.confirm('确认导出选中信息？',function(){
				pedestrianExport(1);
			})
		}else{
			Joyin.confirm('确认导出查询结果？',function(){
				pedestrianExport(0);
			})
		}
	})
	 /**
     * 人行Excel
     * flag:0 全部导出，选中导出
     */
    var pedestrianExport = function(flag) {
		var isHaveData = true;
		if(flag == 0){
			var sendStatus = $("#sendStatus").val();
		  	if(sendStatus == null){
		  		sendStatus = '01';
        	}
	    	 var param = getSearchParams();
	      	 param.sendStatus = sendStatus;
	         Joyin.exportExcel("pedestrianExport",param);
	         Joyin.ajaxSync('searchPage',param, function(result){
		            if (!result || result.root.length == 0){
		         	   isHaveData = false;
		         	   return;
		            }
		     })
		}else{
			var param = {};
			var expArr=table.getAllCheckedRows();
            for(var i=0;i<expArr.length;i++){
                if(expArr[i].status =="03"){
                   //如果包含有删除状态的数据，给出提示并返回
                   Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0010"));
                   return;
                 }
            }
			var keyList = '';
			layui.each(expArr,function(index,data){
				if(index == expArr.length-1){
					keyList = keyList + data.registSeqno+'';
				}else{
					keyList = keyList + data.registSeqno+',';
				}
			})
			param.keyList = keyList;
			Joyin.exportExcel("pedestrianExport",param);
			Joyin.ajaxSync('searchPage',param, function(result){
	            if (!result || result.root.length == 0){
	         	   isHaveData = false;
	         	   return;
	            }
	        })
		}
		if (isHaveData){
	        var rows = table.getAllCheckedRows();
	        if(rows.length === 0) {
//	            return Joyin.error("请至少选择一条记录");
	        	//add by liubin
	        	var sendStatus = $("#sendStatus").val();
	        	var submitFeedbackStatus = $("#submitFeedbackStatus").val();
	          	if(sendStatus == null){
			  		sendStatus = new Array();
	        		sendStatus[0] = '01';
	        	}
	        	if(((sendStatus && sendStatus.length == 1 &&sendStatus[0] == '01')
	        			|| (sendStatus && sendStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1 ||  submitFeedbackStatus.indexOf('04')> -1)))){
	        		var paramObj = getSearchParams();
		        	paramObj.flag = '02';
		        	Joyin.confirm("请确认是否要手工处理符合查询条件的全部未报送的记录？", function() {
		        		layui.layer.open({type:3});
		                 Joyin.ajaxJson('doManul', paramObj, function() {
		                     table.load();
		                     layui.layer.closeAll();
		                 });
		             });
	        	}
	        }else{
	        	for(var i=0;i<rows.length;i++){
	              if ((rows[i].sendStatus == '01'
	                  || (rows[i].sendStatus == '02' && (rows[i].submitFeedbackStatus == '02' || rows[i].submitFeedbackStatus == '04')))) {
	            	   	var paramObj= {};
	    	        	paramObj.flag = '01';
	    	        	paramObj.infoList = rows;
	    	          Joyin.confirm("请确认是否要手工处理选中产品的全部未报送的记录？", function() {
	    	        	  layui.layer.open({type:3});
	    	              Joyin.ajaxJson('doManul', paramObj, function() {
	    	                  table.load();
	    	                  layui.layer.closeAll();
	    	              });
	    	          });
	              }
	        	}
	        }
		}
        
    };
    
    // 手工处理
    $('#PbcPrdAccountSub_btn_manual').click(function(){
        var rows = table.getAllCheckedRows();
        if(rows.length === 0) {
//            return Joyin.error("请至少选择一条记录");
        	//add by liubin
        	var sendStatus = $("#sendStatus").val();
        	var submitFeedbackStatus = $("#submitFeedbackStatus").val();
        	if(!((sendStatus && sendStatus.length == 1 &&sendStatus[0] == '01')
        			|| (sendStatus && sendStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1 ||  submitFeedbackStatus.indexOf('04')> -1)))){
        		Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理");
        		return;
        	}
        	var paramObj = getSearchParams();
        	paramObj.flag = '02';
        	Joyin.confirm("请确认是否要手工处理符合查询条件的全部记录？", function() {
        		layui.layer.open({type:3});
                 Joyin.ajaxJson('doManul', paramObj, function() {
                     table.load();
                     layui.layer.closeAll();
                 });
             });
        }else{
        	for(var i=0;i<rows.length;i++){
              if (!(rows[i].sendStatus == '01'
                  || (rows[i].sendStatus == '02' && (rows[i].submitFeedbackStatus == '02' || rows[i].submitFeedbackStatus == '04')))) {
                  Joyin.error("只能选择报送状态为未报送的数据，或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理，请重新选择！");
                  return;
              }
          }
        	var paramObj= {};
        	paramObj.flag = '01';
        	paramObj.infoList = rows;
          Joyin.confirm("请确认是否要手工处理选中记录？", function() {
        	  layui.layer.open({type:3});
              Joyin.ajaxJson('doManul', paramObj, function() {
                  table.load();
                  layui.layer.closeAll();
              });
          });
        }
        
    })
    // 取消手工处理
    $('#PbcPrdAccountSub_btn_cancelManual').click(function(){
        var manArr=table.getAllCheckedRows();
        if(!manArr || manArr.length ==0){
     	   var sendStatus = $("#sendStatus").val();
        	   if(!(sendStatus && sendStatus.length==1 && sendStatus[0] == '03')){
        		 Joyin.error("只能选择手工处理的数据，请重新选择！");
                 return;
        	   }
        	   var paramObj = getSearchParams();
        	   paramObj.flag = '02';
        	   Joyin.confirm("请确认是否要取消手工处理符合查询条件所有记录？", function() {
        		layui.layer.open({type:3});
             Joyin.ajaxJson('cancelManul', paramObj, function() {
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
            var paramObj= {};
        	paramObj.flag = '01';
        	paramObj.infoList = manArr;
             Joyin.confirm("请确认是否要取消手工处理选中记录？", function() {
             	layui.layer.open({type:3});
                 Joyin.ajaxJson('cancelManul', paramObj, function() {
                     table.load();
                     layui.layer.closeAll();
                 });
            });
        }
         
    })
	
    function init(){
        if(isMain=='01'){
            setTimeout(function() {
            	if($('#sendStatus').val()=='02'||$('#sendStatus').val()=='03'){
          		   $('#submitTime').val(searchDataStart+' - '+searchDataEnd);
          	   }else{
          		   $('#pbcSubmitDeadline').val(searchDataStart+' - '+searchDataEnd);
          	   }
                table.load();
            }, 500);
        }
    };
    init();
	
})