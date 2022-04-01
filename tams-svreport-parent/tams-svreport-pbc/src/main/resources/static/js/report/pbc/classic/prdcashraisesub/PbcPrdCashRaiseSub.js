$(function(){
	// 获取查询参数
	var getSearchParams = function(){
		return {
			pbcProdId:$('#pbcProdId').val()==null?null:$('#pbcProdId').val().join(","),
			prodId:$('#prodId').val()==null?null:$('#prodId').val().join(","),
			operateMode:$('#operateMode').val()==null?null:$('#operateMode').val().join(','),
			ivstorientation:$('#ivstorientation').val()==null?null:$('#ivstorientation').val().join(','),
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
              { title: "数据日期", field: 'statusDate',width:150,type: 'datetime',sortField:'statusDate'},
              { title: "报送状态", field: 'sendStatusStr',width:150,sortField:'sendStatus'},
              { title: '报送日期',field:'submitTime',width:150,type: 'datetime',sortField:'submitTime'},
              { title: "报送截止日", field: 'pbcSubmitDeadline',width:150, type: 'datetime',sortField:'pbcSubmitDeadline'},
              { title: "反馈状态", field: 'submitFeedbackStatusStr',width:150,sortField:'submitFeedbackStatus'},
              { title: "募集日期", field: 'raisingDate',width:150, type: 'date',sortField:'raisingDate'},
//              { title: "产品代码", field: 'pbcProdId',width:150,sortField:''},
              { field: 'pbcProdId',visible:false},
//              { title: "产品代码(内部)", field: 'pbcProdIdShow',width:150,sortField:''},
//              { title: "产品代码(外部)", field: 'pbcCode',width:150,sortField:''},
              { title: "产品代码", field: 'pbcProdIdShow',width:150,sortField:'pbcProdIdShow'},
              { title: "反馈产品代码", field: 'pbcCode',width:150,sortField:'pbcCode',visible: false},
              { title: "内部产品代码", field: 'prodId',width:150,sortField:'prodId'},
              { title: "地区", field: 'buyPlace',width:150,sortField:'buyPlace'},
              { title: "客户类型", field: 'customerType1',width:150,sortField:'customerType1'},
              { title: "币种", field: 'ccy',width:150,sortField:'ccy'},
              { title: "当期申购金额", field: 'raisingAmtsum',width:150, type: 'money',sortField:'raisingAmtsum'},
              { title: "当期申购金额折人民币", field: 'raisingAmtsumCny',width:150, type: 'money',sortField:'raisingAmtsumCny'},
              { title: "当期申购份额", field: 'raisingQty',width:150, type: 'number',digit:5,sortField:'raisingQty'},
              { title: "当期兑付/赎回金额", field: 'redamt',width:150, type: 'money',sortField:'redamt'},
              { title: "当期兑付/赎回份额", field: 'redqty',width:150, type: 'number',digit:5,sortField:'redqty'},
              { title: "期末产品金额", field: 'endamtsum',width:150, type: 'money',sortField:'endamtsum'},
              { title: "期末产品金额折人民币", field: 'endamtsumCny',width:150, type: 'money',sortField:'endamtsumCny'},
              { title: "期末产品份额", field: 'endqty',width:150, type: 'number',digit:5,sortField:'endqty'},
              { title: "开放净值型产品期末净值", field: 'prodValue',width:150, type: 'number',digit:5,sortField:'prodValue'},
              { title: "开放净值型产品期末净值折人民币", field: 'prodValueCny',width:250, type: 'number',digit:5,sortField:'prodValueCny'},
              { title: "开放净值型产品期末累计净值", field: 'unitValueTotal',width:250, type: 'number',digit:5,sortField:'unitValueTotal'},
              { title: "开放净值型产品期末累计净值折人民币", field: 'unitValueTotalCny',width:250, type: 'number',digit:5,sortField:'unitValueTotalCny'},
              { title: "开放非净值型产品预期最高收益率", field: 'prodHighYield',width:250, type: 'number',digit:4,sortField:'prodHighYield'},
              { title: "开放非净值型产品预期最低收益率 ", field: 'prodLowYield',width:250, type: 'number',digit:4,sortField:'prodLowYield'},
              {field:'sendStatus', visible:false},
              {field:'status', visible:false},
              {field:'submitFeedbackStatus', visible:false}
              
          ]
	})
	// 查询按钮
	$('#PbcPrdCashRaiseSub_btn_search').click(function(){
		table.load();
	})
	
	// 导出按钮
	$('#PbcPrdCashRaiseSub_btn_chbexport').click(function(){
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
    // 手工处理
    $('#PbcPrdCashRaiseSub_btn_manual').click(function(){
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
            Joyin.confirm("请确认是否要手工处理选中产品对应日期的所有记录？", function() {
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
          Joyin.confirm("请确认是否要手工处理选中产品对应日期的所有记录？", function() {
        	  layui.layer.open({type:3});
              Joyin.ajaxJson('doManul', paramObj, function() {
                  table.load();
                  layui.layer.closeAll();
              });
          });
        }
        
    })
    // 取消手工处理
    $('#PbcPrdCashRaiseSub_btn_cancelManual').click(function(){
        var manArr=table.getAllCheckedRows();
        if(!manArr || manArr.length ==0){
     	   var sendStatus = $("#sendStatus").val();
        	   if(!(sendStatus && sendStatus.length==1 && sendStatus[0] == '03')){
        		 Joyin.error("只能选择手工处理的数据，请重新选择！");
                 return;
        	   }
        	   var paramObj = getSearchParams();
        	   paramObj.flag = '02';
        	   Joyin.confirm("确认将选中产品对应日期的所有记录取消手工处理吗？", function() {
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
      	   Joyin.confirm("确认将选中产品对应日期的所有记录取消手工处理吗？", function() {
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
	
  //数据检查
    $('#PbcPrdCashRaiseSub_btn_checkData').click(dateCheckMethod);
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
        	keyList.push(checkArr[i].registSeqno);
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
    
    
    
    // 导出人行Excel按钮
    $('#PbcPrdCashRaiseSub_btn_chbexportxls').click(function(){
    	var expArr=table.getAllCheckedRows();
    	if(expArr != null && expArr.length >0){
    		Joyin.confirm('确认导出选中信息？',function(){
    			downloadExcel(1);
    		})
    	}else{
    		Joyin.confirm('确认导出查询结果？',function(){
    			downloadExcel(0);
    		})
    	}
    })
    
    /**
     * 导出人行Excel
     * flag:0 全部导出，选中导出
     */
     var downloadExcel = function(flag) {
		var expArr = table.getAllCheckedRows();
		var param = {};
		var paramObj = getSearchParams();
		
		if(flag == "0"){
			
			param = getSearchParams();
			param.flag = flag;
			if (flag == "0" && param.sendStatus == null) {
				//param.status="01";
				param.sendStatus="01";
			}
			var isHaveData = true;
			Joyin.ajaxSync('searchPage',param, function(result){
                if (!result || result.root.length == 0){
             	   isHaveData = false;
             	   return;
                }
            })
            
            //Joyin.exportExcel("download",paramObj);
			Joyin.exportExcel("download", param);
			if (isHaveData){
				Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
        		//页面添加滚动条
        		layui.layer.open({type:3});
	            Joyin.ajaxJson('doManul', param, function() {
	                table.load();
	                layui.layer.closeAll();
	            });
	        });
            }
			
		}else{
			// 用来标注选择数据中存在修改或者已报送数据，默认为false
			var statuFlag = false;
			if (flag == "1") {
				for (var i = 0; i < expArr.length; i++) {
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
			var infoList = [];
			param.infoList = JSON.stringify(expArr);
			//param.expKeys = infoList.join(',')
			param.flag = "01";
			param.exportflag = 'exportSelected';
			if (statuFlag) {
				Joyin.confirm("导出数据包含修改或已报送数据，请确认是否导出！", function() {
					confirmExport(param, expArr);
				});
			} else {
				confirmExport(param, expArr);
			}
		}
    };
    
    /**
	 * 确认导出之后的操作
	 */
	var confirmExport = function(param, expArr) {
     	var datas = JSON.stringify(expArr);
     	Joyin.exportExcel("download",{datas : datas});
     	
     	
     	var obj={};
     	obj.flag='01';
     	obj.infoList=expArr;
     	
     	for (var i = 0; i < expArr.length; i++) {
			// 含有删除状态数据
			if (expArr[i].sendStatus=="01") {
				Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
					layui.layer.open({type:3});
					Joyin.ajaxJson('doManul', obj, function() {
						table.load();
						layui.layer.closeAll();
					});
				});
				}else{
					return;
				}
		}
	};
    
})