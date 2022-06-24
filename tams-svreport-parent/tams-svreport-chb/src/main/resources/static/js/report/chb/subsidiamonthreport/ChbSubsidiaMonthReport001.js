$(function() {
    // 跳转的页码
    var pageCount = 1;
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return {
            //报送截止日
            submitDeadLineStart:$('#submitDeadLine').startDate(),
            submitDeadLineEnd:$('#submitDeadLine').endDate(),
            //报送状态
            sendStatus : $('#sendStatus').val()==null?null:$('#sendStatus').val().join(","),
            //数据状态
            status : $('#status').val()==null?null:$('#status').val().join(","),
            //数据日期
            statusDateStart:$('#updateTime').startDate(),
            statusDateEnd:$('#updateTime').endDate(),
            //报送日期
            submitDateStart:$('#submitDate').startDate(),
            submitDateEnd:$('#submitDate').endDate(),
            //反馈状态
            submitFeedbackStatus: $('#submitFeedbackStatus').val()==null?null:$('#submitFeedbackStatus').val().join(","),
            //结束日（统计日期）
            enddate : $('#enddate').val()
        }
    };
    
    var laydate = layui.laydate;
    laydate.render({
		elem: '#enddate',
		type: 'month',
		format: 'yyyy-MM'
			
	});

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
        cols:[
            { title: "主键", field: 'reportuuid',sortable:false,visible:false},
            { title: "数据状态", width : 130, field: 'statusStr',sortField:'status'},
            { title: "数据日期", width : 200, field: 'statusDate', type: 'datetime',sortField:'status_Date'},
            { title: "报送状态", width : 130, field: 'sendStatusStr',sortField:'send_Status'},
            { title: "报送日期", width : 200, field: 'submitTime', type: 'datetime',sortField:'submit_Time'},
            { title: "报送截止日", width : 200, field: 'chbSubmitDeadline', type: 'datetime',sortField:'chb_Submit_Deadline'},
            { title: "反馈状态",  width : 130,field: 'submitFeedbackStatusStr',sortField:'submit_Feedback_Status'},
            {title: '统计年月', width : 130,field:'termname',sortField:'termid'},
            {title: '报表名称',width : 310,field:'reportname',sortField:'termid'},
			{
            title : '操作',
			field : 'operation',
			type : 'action',
			width : 80,
			buttons : [ 
			{
				text : '&#xe615;',
				clas : 'fileView',
				title : '预览'
			} ] 
			},
            {field:'sendStatus', visible:false},
            {field:'status', visible:false}
        ]
    });
    
    
    // 导入按钮点击事件
    $('#ChbSubsidiaMonthReport001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
            Joyin.uploadFile(function(fileName) {
                Joyin.ajax('importFile', {fileName: fileName});
            },function() {
                table.load();
            });
        });
    });
    
    //导出所有查询结果
    $('#ChbSubsidiaMonthReport001_btn_export').click(function() {
        exportExcel("1");
    });
    
    //导出中债Excel
    $('#ChbSubsidiaMonthReport001_btn_chbexport').click(function() {
        exportExcel("0");
    });
    
  //导出删除模板
    $('#ChbSubsidiaMonthReport001_btn_exportDel').click(function(){
    	/*if(!table.isSearched()) {
            return;
        }*/
        var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
            param = getSearchParams();
            if (param.status==null||param.status==''){
            	param.status='03';
            }
            param.flag = "2";
            param.sendStatus='01';
            Joyin.exportExcel("export",param);
        }
        else{
       	
        	 for(var i=0;i<expArr.length;i++){
                 if(expArr[i].status !="删除"||expArr[i].sendStatus !="未报送"){
                     Joyin.error("只能导出删除状态且未报送的数据，请重新选择");
                     return;
                 }
        	 }
        	 var keyList = [];
             $.each(expArr,function(i,v){
                 v.sendStatus=v.sendStatusCode;
                 v.status=v.statusCode;
                 v.ccy=v.ccyCode;
             })
             param.keyList = JSON.stringify(expArr);
             param.flag='2';
             Joyin.exportExcel('export', param);
        }
    });
    
    /**
     * 导出
     */
    var exportExcel = function(flag) {
    	var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
        	if(expArr.length ==0){
        		Joyin.error("请选择导出数据！");
    			return;
        	}
            param = getSearchParams();
            param.flag = flag;
            if(flag=="0" &&(param.status==null||param.status=='')){
            	param.status="01,02";
            }
            param.exportflag = 'export';
            Joyin.exportExcel("export",param);
        }
        else{
            var sendStatu='';
        	if(flag=="0")
        		{
        		if(expArr.length >1){
        			Joyin.error("只能选择一条数据导出！");
        			return;
        		}
        	for(var i=0;i<expArr.length;i++){
                sendStatu=expArr[i].sendStatus;
        		if(expArr[i].status=="03"){
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
            if(sendStatu=='01'||sendStatu==''){
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
  //数据检查
    function dateCheckMethod(){
        var checkArr=table.getAllCheckedRows();
        if(checkArr.length === 0) {
            var sendStatus = $('#sendStatus').val();
            var status = $('#status').val();
            if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '01')) {
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行数据检查！");
                return;
            }
            else if(status && status.indexOf('03') > -1){
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            var paramObj = getSearchParams()
            if(!status){
                paramObj.status = '01,02'; 
            }
            Joyin.ajax('reportCheck',paramObj, function(result){
                window.checkResult = result;
                Joyin.checkData(dataTitle, checkUrl, getSearchParams());
            })
            return ;
        }
        var keyList = [];
        for(var i=0;i<checkArr.length;i++){
        	keyList.push(checkArr[i].reportuuid);
            if(checkArr[i].sendStatus !="01"){
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
     * 报送
     */
    $('#ChbSubsidiaMonthReport001_btn_submit').click(function(){
        var rows = table.getAllCheckedRows();
        if(rows.length === 0) {
            var sendStatus = $('#sendStatus').val();
            var status = $('#status').val();
            if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '01')) {
                Joyin.error("只能报送报送状态为未报送的数据");
                return;
            }
            else if(status && status.indexOf('03') > -1){
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            Joyin.ajax('sendBeforeCheck',getSearchParams(), function(result) {
                if(!result || result.length ==0){
                    Joyin.confirm("按照已选择当前查询条件报送,是否确认！",function(){
                    	layui.layer.open({type:3});
                        Joyin.ajaxJson('submitChb', getSearchParams(), function() {
                          table.load();
                          layui.layer.closeAll();
                        });
                    });
                }
                else{
                    window.checkResult = result;
                    Joyin.checkData('<h4 class="modal-title" id="myModalLabel">投资者持有信息数据检查 </h4>', 'sendBeforeCheck', getSearchParams());
                }
            });
            return ;
        }
        for(var i=0;i<rows.length;i++){
            if(rows[i].sendStatus !="01"){
                Joyin.error("只能报送报送状态为未报送的数据");
                return;
            }
            else if(rows[i].status && rows[i].status.indexOf('03') > -1){
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行报送！");
                return;
            }
        }
        var keyList = [];
        Joyin.ajax('sendBeforeCheck', {keyList: JSON.stringify(rows)}, function(result) {
            if(!result || result.length ==0){
                Joyin.confirm("请确认是否要报送选中记录？", function() {
                	layui.layer.open({type:3});
                    Joyin.ajaxJson('submitChb', {keyList: JSON.stringify(rows)}, function() {
                        table.load();
                        layui.layer.closeAll();
                    });
                })
            }
            else{
                window.checkResult = result;
                Joyin.checkData('<h4 class="modal-title" id="myModalLabel">投资者持有信息数据检查 </h4>', 'sendBeforeCheck', {keyList: JSON.stringify(rows)});
            }
        });
    });
    
	table.bind('click', '.fileView', function(data, index) {
	    var height = '600px';
	    var btns = {};
	    var keyList = [];
	    var dialogIndex = -1;
	    var path = '/chb/subsidiamonthreport/ChbSubsidiaMonthReport001/preView';
	    if(data.status=='03'){
	    	Joyin.error("删除状态数据不支持预览");
            return;
	    }
	   
	    var param={reportuuid:data.reportuuid,
	    			termid:data.termid}
	    dialogIndex = Joyin.dialog('预览', path, '1200px', height, param, function (params) {
	    }, btns);

	});
	//数据检查
    $('#ChbSubsidiaMonthReport001_btn_checkData').click(dateCheckMethod);
    function dateCheckMethod(){
        var checkArr=table.getAllCheckedRows();
        if(checkArr.length === 0) {
            var sendStatus = $('#sendStatus').val();
            var status = $('#status').val();
            if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '01')) {
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行数据检查！");
                return;
            }
            else if(status && status.indexOf('03') > -1){
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            var paramObj = getSearchParams()
            if(!status){
                paramObj.status = '01,02'; 
            }
            Joyin.ajax('reportCheck',paramObj, function(result){
                window.checkResult = result;
                Joyin.checkData(dataTitle, checkUrl, getSearchParams());
            })
            return ;
        }
        var keyList = [];
        for(var i=0;i<checkArr.length;i++){
        	keyList.push(checkArr[i].reportuuid);
            if(checkArr[i].sendStatus !="01"){
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
    $('#ChbSubsidiaMonthReport001_btn_manual').click(function(){
        var rows = table.getAllCheckedRows();
        if(rows.length === 0) {
        	var sendStatus = $("#sendStatus").val();
        	var submitFeedbackStatus = $("#submitFeedbackStatus").val();
        	if(!((sendStatus && sendStatus.length == 1 &&sendStatus[0] == '01')
        			|| (sendStatus && sendStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1 ||  submitFeedbackStatus.indexOf('04')> -1)))){
        		Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理");
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
                  || (rows[i].sendStatus == '02' && (rows[i].submitFeedbackStatus == '02' || rows[i].submitFeedbackStatus == '04')))) {
                  Joyin.error("只能选择报送状态为未报送的数据，或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理，请重新选择！");
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
     * 重新计算
     */
    $('#ChbSubsidiaMonthReport001_btn_recal').click(function(){
        
    	var paramObj = getSearchParams();
    	if ($('#enddate').val()==null
    			|| $('#enddate').val()== ""){
    		Joyin.error("重新计算前，请先选择统计月份！");
    		return;
    	} else {
    		// 重新查询是否有已生成的报表
            Joyin.ajaxSync('findPageBysubmitDate',paramObj, function(result){
            	var jsonResult = jQuery.parseJSON(result);
            	var yearStart = jsonResult["YEAR"];
            	var monthStart = jsonResult["MONTH"];
            	var status = jsonResult["STATUS"];
            	var sendstatus = jsonResult["SENDSTATUS"];
            	var submitDateStart = Date.ofDate($('#submitDate').startDate());
                if (yearStart && monthStart){
                	if(status=='02' && !sendstatus){
                		Joyin.error("计算日期存在已经报送的数据，请先将已报送数据取消手工处理！");
                	}else if(!status&& sendstatus=='01'){
                		Joyin.confirm(/*yearStart+"年"+monthStart+*/"重新计算会将对应日期的数据删除，建议先做好备份，请确认是否继续?",function(){
                       		layui.layer.open({type:3});
                        	recalPbcReport(paramObj);
                        });
                		
                	}else if(status=='02'&& sendstatus=='01'){
                		Joyin.confirm(/*yearStart+"年"+monthStart+*/"重新计算会将对应日期的数据删除，建议先做好备份，请确认是否继续?",function(){
                       		layui.layer.open({type:3});
                        	recalPbcReport(paramObj);
                        });
                		
                	}
                } else {
                	recalPbcReport(paramObj);
                }
            })
    	}
    
    }); 
    
    /**
     * 重新计算人行报表
     * @returns
     */
    function recalPbcReport(paramObj){
    	// 打开虚拟进度条
    	JoyinProgress.showInvented(false);
    	// 执行批处理
    	Joyin.ajaxJson('doBatch', paramObj, function(date) {
			table.load();
	    	// 关闭虚拟进度条
	    	JoyinProgress.hideCommon();
	    	// 重新加载表格
	        table.load();
	        // 关闭弹出层
	        layui.layer.closeAll();
		});
    }
        /**
         * 取消手工处理 
         */
       $('#ChbSubsidiaMonthReport001_btn_cancelManual').click(function() {
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
       // 二次报送
       $('#ChbSubsidiaMonthReport001_btn_submitAgain').click(function() {
           var paramObj = getSearchParams();
           
           var submitArr = table.getAllCheckedRows();
           if(submitArr.length === 0) {
               var submitStatus = $('#sendStatus').val();
               var status = $('#status').val();
               var submitFeedbackStatus = $('#submitFeedbackStatus').val();
               if (!(submitStatus && submitStatus.length == 1 && submitStatus[0] == '02')) {
                   Joyin.error("只能对报送状态为接口报送的数据进行重新报送！");
                   return;
               }
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
           	var submitStatus = submitArr[i].sendStatusCode;
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
           };
           $.each(submitArr, function(i, v) {
               v.sendStatus=v.sendStatusCode;
               v.status=v.statusCode;
               v.ccy=v.ccyCode;
           });
           
           Joyin.confirm("请确认是否要二次报送？",function(){
        	   layui.layer.open({type:3});
               Joyin.ajaxJson('submitCbAgain', {keyList: JSON.stringify(submitArr)}, function() {
                   table.load();
                   layui.layer.closeAll();
               });
           });
       });

       function init(){
           if(isMain=='01'){
               setTimeout(function() {
            	   if($('#sendStatus').val()=='02'||$('#sendStatus').val()=='03'){
              		   $('#submitDate').val(searchDataStart+' - '+searchDataEnd);
              	   }else{
              		   $('#submitDeadLine').val(searchDataStart + ' - ' + searchDataEnd);
              	   }
                   table.load();
               }, 500);
           }
           //报送前检查调用数据检查
           if(validKey){
               setTimeout(function() {
                   table.load();
               },500)
           }
       };
       init();
       
       $(function(){
           // 绑定按钮事件
           $('#SvrSubsidiaMonthReport001_btn_search').click(function() {
               table.load();
           });
       });
}());