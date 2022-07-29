$(function() {
  //跳转的页码
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
            dataStatus : $('#dataStatus').val()==null?null:$('#dataStatus').val().join(","),
            //数据日期
            statusDateStart:$('#statusDate').startDate(),
            statusDateEnd:$('#statusDate').endDate(),
            //报送日期
            submitTimeStart:$('#submitTime').startDate(),
            submitTimeEnd:$('#submitTime').endDate(),
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
            { title: "报表类型", width : 130, field: 'reporttypeStr',sortField:'reporttype'},
            { title: "数据状态", width : 130, field: 'dataStatusStr',sortField:'dataStatus'},
            { title: "数据日期", width : 200, field: 'statusDate', type: 'datetime',sortField:'statusDate'},
            { title: "报送状态", width : 130, field: 'sendStatusStr',sortField:'sendStatus'},
            { title: "报送日期", width : 200, field: 'submitTime', type: 'datetime',sortField:'submitTime'},
            { title: "报送截止日", width : 200, field: 'chbSubmitDeadline', type: 'date',sortField:'chbSubmitDeadline'},
            { title: "反馈状态",  width : 130,field: 'submitFeedbackStatusStr',sortField:'submitFeedbackStatus'},
            {title: '统计年月', width : 130,field:'termid',sortField:'termid'},
            {title: '报表名称',width : 310,field:'reportName',sortField:'reportName'},
			{
            title : '操作',
			field : 'operation',
			type : 'action',
			width : 80,
			buttons : [ 
			{
				text : '&#xe615;',
				clas : 'batch',
				title : '预览'
			} ]
			},
            {field:'sendStatus', visible:false},
            {field:'dataStatus', visible:false}
        ]
    });
    
    //导出中债Excel
    $('#SellMonthReport001_btn_chbexport').click(function() {
        exportExcel("0");
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
            if(flag=="0" &&(param.dataStatus==null||param.dataStatus=='')){
            	param.dataStatus="01,02";
            }
            param.exportflag = 'export';
            Joyin.exportExcel("export",param);
        }
        else{
        	if(flag=="0")
        		{
        		if(expArr.length >1){
        			Joyin.error("只能选择一条数据导出！");
        			return;
        		}
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
    
	table.bind('click', '.batch', function(data, index) {
	    var height = '90%';
	    var btns = {};
	    var keyList = [];
	    var dialogIndex = -1;
	    var path = '/chb/monthreport/SellMonthReport001/preView';
	    if(data.dataStatus=='03'){
	    	Joyin.error("删除状态数据不支持预览");
            return;
	    }
	   
	    var param={reportuuid:data.reportuuid,reporttype:data.reporttype,
	    			termid:data.termid}
	    dialogIndex = Joyin.dialog('预览', path, '90%', height, param, function (params) {
	    }, btns);

	});

        /**
         * 手工处理
         */
        $('#SellMonthReport001_btn_manual').click(function(){
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
                      Joyin.error("只能选择报送状态为未报送的数据，或报送状态为接口报送且反馈状态为发送中、发送失败、中债退回和发送异常的数据进行手工处理，请重新选择！");
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
       $('#SellMonthReport001_btn_cancelManual').click(function() {
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

       function init(){
           if(isMain=='01'){
               setTimeout(function() {
            	   if($('#sendStatus').val()=='02'||$('#sendStatus').val()=='03'){
              		   $('#submitTime').val(searchDataStart+' - '+searchDataEnd);
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
           $('#SellMonthReport001_btn_search').click(function() {
               table.load();
           });
       });
}());