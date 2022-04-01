$(function(){
	// 获取查询参数
	var getSearchParams = function(){
		return {
			pbcAccountId:$('#pbcAccountId').val()==null?null:$('#pbcAccountId').val().join(','),
			prodId:$('#prodId').val()==null?null:$('#prodId').val().join(","),
			sendStatus:$('#sendStatus').val()==null?null:$('#sendStatus').val().join(','),
			status:$('#status').val()==null?null:$('#status').val().join(','),
			statusDateStart:$('#statusDate').startDate(),
			statusDateEnd:$('#statusDate').endDate(),
			submitTimeStart:$('#submitTime').startDate(),
			submitTimeEnd:$('#submitTime').endDate(),
			submitFeedbackStatus:$('#submitFeedbackStatus').val()==null?null:$('#submitFeedbackStatus').val().join(','),
			submitDeadLineEnd:$('#pbcSubmitDeadline').endDate(),
			submitDeadLineStart:$('#pbcSubmitDeadline').startDate(),
			submitDateStart:$('#enddate').startDate(),
			submitDateEnd:$('#enddate').endDate(),

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
		rowno: false, // 显示行号
		searchOnInit: false, //初始化的时候就请求后台数据
		cols:[
			{ field: 'reportuuid',visible:false},
			{ title: "数据状态", field: 'statusStr',width:150,sortField:'status'},
			{ title: "数据日期", field: 'statusDate',width:150,type: 'datetime',sortField:'STATUS_DATE'},
			{ title: "报送状态", field: 'sendStatusStr',width:150,sortField:'SEND_STATUS'},
			{ title: '报送日期',field:'submitTime',width:150,type: 'datetime',sortField:'SUBMIT_TIME'},
			{ title: "报送截止日", field: 'chbSubmitDeadline',width:150, type: 'date',sortField:'CHB_SUBMIT_DEADLINE'},
			{ title: "反馈状态", field: 'submitFeedbackStatusStr',width:150,sortField:'SUBMIT_FEEDBACK_STATUS'},
			{ title: "资产池代码", field: 'pbcAccountId',width:250,sortField:'pbcAccountIdShow',visible:false},
//				{ title: "资产池代码(内部)", field: 'pbcAccountIdShow',width:250,sortField:''},
//				{ title: "资产池代码(外部)", field: 'pbcCode',width:250,sortField:''},
			{ title: "资产池代码", field: 'pbcAccountIdShow',width:250,sortField:'pbcAccountIdShow'},
			{ title: "反馈资产池代码", field: 'pbcCode',width:250,sortField:'pbc_code'},
			{ title: "内部产品代码", field: 'accountId',width:250,sortField:'ACCOUNT_ID'},
			{ title: "报告期", field: 'termname',width:150,sortField:'TERMID'},
			{field:'sendStatus', visible:false},
			{field:'status', visible:false},
			{field:'submitFeedbackStatus', visible:false},
			{
				title : '操作',
				field : 'operation',
				type : 'action',
				width : 150,
				buttons : [
					{
						text : '&#xe615;',
						clas : 'manual',
						title : '资产负债预览'
					},{
						text : '&#xe615;',
						clas : 'batch',
						title : '贷款明细预览'
					},{
						text : '&#xe615;',
						clas : 'edit',
						title : '资金信托预览'
					} ]
//					render: function (data, type, row, meta) {
//					    var buttons='';
//			            if(purviewElements.indexOf('_manual')!=-1){
//			                buttons+='<i class="btn layui-icon manual" title="资产负债预览">&#xe615;</i>';
//			            }
//			            if(purviewElements.indexOf('_batch')!=-1){
//		                    buttons+='<i class="btn layui-icon batch" title="贷款明细预览">&#xe615;</i>';
//		                }
//			            if(purviewElements.indexOf('_edit')!=-1){
//			            	buttons+='<i class="btn layui-icon edit" title="修改凭证备注">&#xe615;</i>';
//			            }
//			            return buttons;
//			        }
			}
		]
	})
	// 查询按钮
	$('#PbcPrdAssetDebtSub_btn_search').click(function(){
		table.load();
	})

	// 导出按钮
	$('#PbcPrdAssetDebtSub_btn_chbexport').click(function(){
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
					keyList = keyList + data.reportuuid+'';
				}else{
					keyList = keyList + data.reportuuid+',';
				}
			})
			param.keyList = keyList;
			Joyin.exportExcel("export",param);
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
			keyList.push(checkArr[i].termid);
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
	$('#PbcPrdAssetDebtSub_btn_checkData').click(dateCheckMethod);
	// 手工处理
	$('#PbcPrdAssetDebtSub_btn_manual').click(function(){
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
			// Joyin.confirm("请确认是否要手工处理符合查询条件的全部记录？", function() {
			// 	layui.layer.open({type:3});
			//      Joyin.ajaxJson('doManul', paramObj, function() {
			//          table.load();
			//          layui.layer.closeAll();
			//      });
			//  });

			Joyin.confirm("请确认是否要手工处理符合查询条件的全部记录？", function() {
				layui.layer.open({type:3});
				var param = getSearchParams();
				param.flag = "0";
				//针对daTa，判断贷款明细信息和资金信托明细信息是否存在
				Joyin.ajaxJson(basePath + '/pbc/prdassetdebt/PbcPrdAssetDebtSub/searchNoDxAndXt',param, function (result) {
					var mes;
					if(result && result.length>0 ){
						mes="资产池";
						for (var i = 0; i < result.length; i++) {
							mes+=","+result[i].pbcAccountId;
						}
						mes+="没有对应的贷款明细信息和资金信托明细信息，不会更新报送状态，是否确认数据进行手工处理？";
						Joyin.confirm("请确认是否要手工处理符合查询条件的全部记录？", function() {
							layui.layer.open({type:3});
							Joyin.ajaxJson('doManul', paramObj, function() {
								table.load();
								layui.layer.closeAll();
							});
						});
					}else{
						//页面添加滚动条
						Joyin.ajaxJson('doManul', paramObj, function() {
							table.load();
						});
					}
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
				var datasExpArr = JSON.stringify(rows);
				var data = {
					datasExpArr: datasExpArr,
					flag: "1"
				};
				Joyin.ajaxJson(basePath + '/pbc/prdassetdebt/PbcPrdAssetDebtSub/searchNoDxAndXt',data, function (result) {
					var mes;
					if(result && result.length>0 ){
						mes="资产池";
						for (var i = 0; i < result.length; i++) {
							if(i == 0){
								mes+=result[i].pbcAccountId;
							}else{
								mes+=","+result[i].pbcAccountId;
							}

						}
						mes+="没有对应的贷款明细信息和资金信托明细信息，不会更新报送状态，是否确认数据进行手工处理？";
						Joyin.confirm(mes,function() {
							layui.layer.open({type:3});
							Joyin.ajaxJson('doManul', paramObj, function() {
								table.load();
								layui.layer.closeAll();
							});
						});
					}else{
						layui.layer.open({type:3});
						Joyin.ajaxJson('doManul', paramObj, function() {
							table.load();
							layui.layer.closeAll();
						});
					}
				});
			});
		}

	})
	// 取消手工处理
	$('#PbcPrdAssetDebtSub_btn_cancelManual').click(function(){
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
	table.bind('click', '.manual', function(data, index) {
		var height = '600px';
		var btns = {};
		var keyList = [];
		var dialogIndex = -1;
		var path = '/pbc/prdassetdebt/PbcPrdAssetDebtSub/preView';
		if(data.status=='03'){
			Joyin.error("删除状态数据不支持预览");
			return;
		}
		console.log(data)
		var param={reportuuid:data.reportuuid,
			termid:data.termid,reporttype:data.reporttype}

		Joyin.ajax('preView', param, function(result) {
			if ("fail" == result){
				Joyin.error("所选数据不存在，请重新查询！");
				table.load();
			} else {
				dialogIndex = Joyin.dialog('预览', path, '1200px', height, param, function (params) {
				}, btns);
			}
		});

	});
	//贷款明细预览
	table.bind('click', '.batch', function(data,index) {
		if(data.status=='03'){
			Joyin.error("删除状态数据不支持预览");
			return;
		}
		var param={reportuuid:data.reportuuid, previewType:'DK'};
		Joyin.dialog('贷款明细预览',
			JoyinUtil.getRealPath("/pbc/prdassetdebt/PbcPrdAssetDebtSubPreview/init"),
			"98%", "95%", param, function(){});
	});
	//资金信托预览
	table.bind('click', '.edit', function(data,index) {
		if(data.status=='03'){
			Joyin.error("删除状态数据不支持预览");
			return;
		}
		var param={reportuuid:data.reportuuid, previewType:'XT'};
		Joyin.dialog('资金信托预览',
			JoyinUtil.getRealPath("/pbc/prdassetdebt/PbcPrdAssetDebtSubPreview/init"),
			"98%", "95%", param, function(){});
	});
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





	// 导出人行Excel按钮
	$('#PbcPrdAssetDebtSub_btn_chbexport').click(function(){
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
	 * flag:1 选中导出
	 * flag:0 全部导出
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
			//针对daTa，判断贷款明细信息和资金信托明细信息是否存在
			Joyin.ajaxJson(basePath + '/pbc/prdassetdebt/PbcPrdAssetDebtSub/searchNoDxAndXt',param, function (result) {
				var mes;
				if(result && result.length>0 ){
					mes="资产池";
					for (var i = 0; i < result.length; i++) {
						mes+=","+result[i].pbcAccountId;
					}
					mes+="没有对应的贷款明细信息和资金信托明细信息，不会导出到Excel文件中，是否确认导出其资产池的资产负债信息？？";
					Joyin.confirm(mes,function() {
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
					});
				}else{
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
				}
			});
			//Joyin.exportExcel("download",paramObj);

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
					//针对expArr，判断贷款明细信息和资金信托明细信息是否存在
					var datasExpArr = JSON.stringify(expArr);
					var data = {
						datasExpArr: datasExpArr,
						flag:flag
					};
					Joyin.ajaxJson(basePath + '/pbc/prdassetdebt/PbcPrdAssetDebtSub/searchNoDxAndXt',data, function (result) {
						var mes;
						if(result && result.length>0 ){
							mes="资产池";
							for (var i = 0; i < result.length; i++) {
								if(i == 0){
									mes+=result[i].pbcAccountId;
								}else{
									mes+=","+result[i].pbcAccountId;
								}
								// for(var j=expArr.length-1;j>=0;j--){
								//     if(expArr[j].reportuuid=result[i].reportuuid){
								//      expArr.splice(j,1);
								//      break;
								//     }
								//  }

							}
							mes+="没有对应的贷款明细信息和资金信托明细信息，不会导出到Excel文件中，是否确认导出其资产池的资产负债信息？？";
							Joyin.confirm(mes,function() {
								confirmExport(param, expArr);
							});
						}else{
							confirmExport(param, expArr);
						}
					});
					//confirmExport(param, expArr);
				});
			} else {
				//针对expArr，判断贷款明细信息和资金信托明细信息是否存在
				var datasExpArr = JSON.stringify(expArr);
				var data = {
					datasExpArr: datasExpArr,
					flag:flag
				};
				Joyin.ajaxJson(basePath + '/pbc/prdassetdebt/PbcPrdAssetDebtSub/searchNoDxAndXt',data, function (result) {
					var mes;
					if(result && result.length>0 ){
						mes="资产池";
						for (var i = 0; i < result.length; i++) {
							if(i == 0){
								mes+=result[i].pbcAccountId;
							}else{
								mes+=","+result[i].pbcAccountId;
							}

							// for(var j=expArr.length-1;j>=0;j--){
							//     if(expArr[j].reportuuid=result[i].reportuuid){
							//      expArr.splice(j,1);
							//      break;
							//     }
							//  }

						}
						mes+="没有对应的贷款明细信息和资金信托明细信息，不会导出到Excel文件中，是否确认导出其资产池的资产负债信息？？";
						Joyin.confirm(mes,function() {
							confirmExport(param, expArr);
						});
					}else{
						confirmExport(param, expArr);
					}
				});
				//confirmExport(param, expArr);
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


	init();

})