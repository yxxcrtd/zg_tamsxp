$(function() {
	// 跳转的页码
	var pageCount = 1;
	/**
	 * 获取查询参数
	 */
	var getSearchParams = function() {
		return {
			// 产品名称
			prodName : $('#prodname').val() == null ? null : $('#prodname')
					.val(),
			// 报送状态
			sendStatus : $('#sendStatus').val() == null ? null : $(
					'#sendStatus').val().join(","),
			// 数据状态
			status : $('#status').val() == null ? null : $('#status').val()
					.join(","),

			// 产品代码
			prodId : $('#prod_id').val() == null ? null : $('#prod_id').val()
					.join(","),
			// 反馈状态
			submitFeedbackStatus : $('#submitFeedbackStatus').val() == null ? null
					: $('#submitFeedbackStatus').val().join(","),
			// 报送截止日
			submitDeadLineStart : $('#submitDeadLine').startDate(),
			submitDeadLineEnd : $('#submitDeadLine').endDate(),

			// 募集起始日期
			raise_startdateStart : $('#raise_startdate').startDate(),
			raise_startdateEnd : $('#raise_startdate').endDate(),
			
			//行内标识码 
			prodInsId : $('#prod_ins_id').val() == null ? null : $('#prod_ins_id').val()
					.join(","),
			
			// 数据日期
			statusDateStart : $('#statusDate').startDate(),
			statusDateEnd : $('#statusDate').endDate(),
			// 报送日期
			submitDateStart : $('#submitDate').startDate(),
			submitDateEnd : $('#submitDate').endDate(),
		}
	};

	
	/**
	 * 定义数据表格
	 */
	var table = $("#dataTable")
			.joyintable(
					{
						ajax : {
							url : 'findPage',
							type : 'POST',
							data : function() {
								return getSearchParams();
							}
						},
						showDetail: true,//双击详情对比画面
						cols : [
								{
									title : '数据状态',
									field : 'statusSTR',
									width : 100, 
									sortField:'status'
								},
								{
									title : '数据日期',
									field : 'statusDate',
									type : 'date',
									type : 'datetime', 
									sortField:'statusDate'
								},
								{
									title : '报送状态',
									field : 'sendStatusSTR',
									width : 100, 
									sortField:'sendStatus',
									render:function (value,visible,row,options) {
										if(value == "接口报送"){
											return '<a href="javascript:void(0)" class="sendStatusClass">'+value+'</a>';
										}else{
											return value;
										}
									}
								},
								{
									title : '报送日期',
									field : 'submitTime',
									type : 'datetime', 
									sortField:'submitTime'
								},
								{
									title : '报送截止日',
									field : 'chbSubmitDeadline',
									type : 'date',
									sortField:'chbSubmitDeadline'
								},
								{
									title : '反馈状态',
									field : 'submitFeedbackStatus',
									width : 100, 
									sortField:'submitFeedbackStatus',
									render:function (value,visible,row,options) {
										if(value == "发送中" || value == "发送异常"){
											return '<a href="javascript:void(0)" class="feedbackStatusClass">'+value+'</a>';
										}else{
											return value;
										}
									}
								},
								{
									field : 'registSeqno',
									width : 200, 
									sortable:false,
									visible : false
								},
								{
									title : '产品登记编码',
									field : 'regCode',
									width : 200,
									sortField:'regCode'
								},
								{
									title : '产品代码',
									field : 'prodId',
									width : 200, 
									sortField:'prodId'
								},
								{
									title : '产品名称',
									field : 'prodName',
									width : 200, 
									sortField:'prodName'
								},
								{
									title : '行内标识码',
									field : 'prodInsId',
									width : 200, 
									sortField:'prodInsId'
								},
								{
									title : '产品品牌',
									field : 'prodBrand',
									width : 200, 
									sortField:'prodBrand'
								},
								{
									title : '产品期次',
									field : 'prodStage',
									width : 200, 
									sortField:'prodStage'
								},
								{
									title : '发行机构代码',
									field : 'issuePartyId',
									width : 200, 
									sortField:'issuePartyId'
								},
								{
									title : '产品审批人身份证号',
									field : 'approverId',
									width : 200, 
									sortField:'approverId'
								},
								{
									title : '产品审批人姓名',
									field : 'approverName',
									width : 200, 
									sortField:'approverName'
								},
								{
									title : '产品设计人身份证号',
									field : 'designerId',
									width : 200, 
									sortField:'designerId'
								},
								{
									title : '产品设计人姓名',
									field : 'designerName',
									width : 200, 
									sortField:'designerName'
								},
								{
									title : '投资经理身份证号',
									field : 'managerId',
									width : 200, 
									sortField:'managerId'
								},
								{
									title : '投资经理姓名',
									field : 'managerName',
									width : 200, 
									sortField:'managerName'
								},
								{
									title : '业务联络人姓名',
									field : 'liaisonName',
									width : 200, 
									sortField:'liaisonName'
								},
								{
									title : '业务联络人座机',
									field : 'liaisonTel',
									width : 200, 
									sortField:'liaisonTel'
								},
								{
									title : '业务联络人手机',
									field : 'liaisonMobile',
									width : 200, 
									sortField:'liaisonMobile'
								},
								{
									title : '业务联络人邮箱',
									field : 'liaisonEmail',
									width : 200, 
									sortField:'liaisonEmail'
								},
								{
									title : '产品募集方式',
									field : 'raiseType',
									width : 200, 
									sortField:'raiseType'
								},
								{
									title : '产品收益类型',
									field : 'profitType',
									width : 200, 
									sortField:'profitType'
								},
								{
									title : '新老产品标记',
									field : 'isFhxgcp',
									width : 200,
									sortField:'isFhxgcp'
								},
								{
									title : '产品期限',
									field : 'termtype',
									width : 200, 
									sortField:'termtype'
								},
								{
									title : '是否金融同业专属',
									field : 'excFinSector',
									width : 200, 
									sortField:'excFinSector'
								},
								{
									title : '资金投向地区',
									field : 'investArae',
									width : 200, 
									sortField:'investArae'
								},
								{
									title : '产品投资国家或地区',
									field : 'investCountry',
									width : 200, 
									sortField:'investCountry'
								},
								{
									title : '理财业务服务模式',
									field : 'finanSerModel',
									width : 200, 
									sortField:'finanSerModel'
								},
								{
									title : '产品运作模式',
									field : 'operateMode',
									width : 200, 
									sortField:'operateMode'
								},
								{
									title : '是否设置最短持有期限',
									field : 'isShortTerm',
									width : 200, 
									sortField:'isShortTerm'
								},
								{
									title : '最短持有期限（天）',
									field : 'shortTerm',
									width : 200, 
									sortField:'shortTerm'
								},
								{
									title : '最短持有期后是否自由赎回',
									field : 'isFreeRedem',
									width : 200, 
									sortField:'isFreeRedem'
								},
								{ 
									title : '是否现金管理类',
									field : 'cashManaCate',
									width : 200, 
									sortField:'cashManaCate'
								},
								{
									title : '产品资产配置方式',
									field : 'cmmode',
									width : 200, 
									sortField:'cmmode'
								},
								{
									title : '产品管理模式',
									field : 'pmmode',
									width : 200, 
									sortField:'pmmode'
								},
								{
									title : '实际管理人名称',
									field : 'mname',
									width : 200, 
									sortField:'mname'
								},
								{
									title : '产品定价方式',
									field : 'pricedMode',
									width : 200, sortField:'pricedMode'
								},
								{
									title : '产品投资性质',
									field : 'ivstorientation',
									width : 200, 
									sortField:'ivstorientation'
								},
								{
									title : '合作模式',
									field : 'contMode',
									width : 200, sortField:'contMode'
								},
								{
									title : '合作机构名称',
									field : 'cooperationName',
									width : 200, sortField:'cooperationName'
								},
								{
									title : '投资资产种类及比例',
									field : 'invTypeRat',
									width : 200, sortField:'invTypeRat'
								},
								{
									title : '业绩比较基准%',
									field : 'yield',
									width : 200, sortField:'yield'
								},
								
								
								{
									title : '投资者风险偏好',
									field : 'riskType',
									width : 200, 
									sortField:'riskType'
								},
								{
									title : '产品销售区域',
									field : 'saleArea',
									width : 200, 
									sortField:'saleArea'
								},
								{
									title : '募集币种',
									field : 'raiseCcy',
									width : 200, 
									sortField:'raiseCcy'
								},
								{
									title : '兑付本金币种',
									field : 'cashPrinCcy',
									width : 200, 
									sortField:'cashPrinCcy'
								},
								{
									title : '兑付收益币种',
									field : 'cashProfCcy',
									width : 200, 
									sortField:'cashProfCcy'
								},
								{
									title : '起点销售金额',
									field : 'saleAmtStr',
									width : 200, 
									sortField:'saleAmtStr'
								},
								{
									title : '计划募集金额',
									field : 'raiseAmtPlan',
									width : 200, 
									sortField:'raiseAmtPlan'
								},
								{
									title : '募集起始日期（从）',
									field : 'raisePstrdate',
									type : 'date',
									width : 200, 
									sortField:'raisePstrdate'
								},
								{
									title : '募集起始日期（到）',
									field : 'raisePenddate',
									type : 'date',
									width : 200, 
									sortField:'raisePenddate'
								},
								{
									title : '投资本金到账日',
									field : 'investPrinDate',
									width : 200, 
									sortField:'investPrinDate'
								},
								{
									title : '投资收益到账日',
									field : 'investProfDate',
									width : 200, 
									sortField:'investProfDate'
								},
								{
									title : '销售手续费率%',
									field : 'saleFeeRate',
									width : 200, 
									sortField:'saleFeeRate'
								},
								{
									title : '投资管理费率%',
									field : 'investFeeRate',
									width : 200, 
									sortField:'investFeeRate'
								},
								{
									title : '境内托管机构名称',
									field : 'inDepName',
									width : 200, 
									sortField:'inDepName'
								},
								{
									title : '境内托管机构代码',
									field : 'inDepCode',
									width : 200, 
									sortField:'inDepCode'
								},
								{
									title : '境外托管机构国别',
									field : 'outDepCtry',
									width : 200, 
									sortField:'outDepCtry'
								},
								{
									title : '境外托管机构名称',
									field : 'outDepName',
									width : 200, 
									sortField:'outDepName'
								},
								{
									title : '托管费率%',
									field : 'truFeeRate',
									width : 200, 
									sortField:'truFeeRate'
								},
								{
									title : '产品风险等级',
									field : 'riskLevel',
									width : 200, 
									sortField:'riskLevel'
								},
								{
									title : '发行机构提前终止权标识',
									field : 'termRightFlag',
									width : 200, 
									sortField:'termRightFlag'
								},
								{
									title : '客户赎回权标识',
									field : 'cusRedeemFlag',
									width : 200, 
									sortField:'cusRedeemFlag'
								},
								{
									title : '产品增信标识',
									field : 'creditFlag',
									width : 200, 
									sortField:'creditFlag'
								},
								{
									title : '产品增信机构类型',
									field : 'creditType',
									width : 200, 
									sortField:'creditType'
								},
								{
									title : '产品增信形式',
									field : 'creditMode',
									width : 200, 
									sortField:'creditMode'
								},
							    {
								title : '产品特殊属性',
								field : 'prdSpecProperty',
								width : 200,
								sortField:'prdSpecProperty'
						     	},
								{
									title : '备注',
									field : 'remark',
									width : 200, 
									sortField:'remark'
								},
								{
									field : 'sendStatus',
									visible : false
								},
								{
									field : 'status',
									visible : false
								},
								{
									field : 'fileNo',
									visible : false
								},
								{
									title : '附件个数',
									field : 'fileNum',
									width : 100, 
									sortField:'fileNum'
								},
								{
									title : '操作附件',
									field : 'operation',
									type : 'action',
									width : 150,
									buttons : [  {
										text : '&#xe67c;',
										clas : 'upload',
										title : '上传'

									}, {
										text : '&#xe640;',
										clas : 'delt',
										title : '删除'

									}, {
										text : '&#xe601;',
										clas : 'download',
										title : '下载'
									} , {
										text : '&#xe615;',
										clas : 'fileView',
										title : '查看'
									},{
										field: 'sumitSeq',
										visible:false
									} ]
								}

						]
					});

	//绑定点击跳转反馈文件管理页面
	table.bind('click', '.feedbackStatusClass', function(data, index) {
		Joyin.dialog(JoyinUtil.getLabel("中债反馈文件管理"),
			basePath + "svreport/system/chbfeedbackfilemanagement/ChbFeedbackFileManagement001/init",
			"95%", "96%", {		//pageModel:PAGE_MODEL_REFERENCE,
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

	// 导出中债Excel
	$('#SvrPerProDeclareReg001_btn_chbexport').click(function() {
		exportExcel("0");
	});

	/**
	 * 导出
	 */
	var exportExcel = function(flag) {
		var expArr = table.getAllCheckedRows();
		var param = {};
		if (!expArr || expArr.length == 0) {
			param = getSearchParams();
			param.flag = flag;
			if (flag == "0") {
				param.status="01";
				param.sendStatus="01";
			}
			var isHaveData = true;
			Joyin.ajaxSync('findPage',param, function(result){
                if (!result || result.root.length == 0){
             	   isHaveData = false;
             	   return;
                }
            })
            
			Joyin.exportExcel("export", param);
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

		} else {
			// 用来标注选择数据中存在修改或者已报送数据，默认为false
			var statuFlag = false;
			if (flag == "0") {
				for (var i = 0; i < expArr.length; i++) {
//					if(expArr[i].status!="01"||expArr[i].sendStatus!="01"){
//	        			Joyin.error("只能导出新建未报送的数据，请重新选择");
//	        			return;
//	        		}
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
			param.keyList = JSON.stringify(expArr);
			param.expKeys = keyList.join(',')
			param.flag = flag;
			param.exportflag = 'exportSelected';
			if (statuFlag) {
				Joyin.confirm("导出数据包含修改或已报送数据，请确认是否导出！", function() {
					confirmExport(param, expArr);
				});
			} else {
				confirmExport(param, expArr);
			}
//			Joyin.exportExcel('export', param);
//			Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
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
		Joyin.exportExcel('export', param);
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
	/**start   中债一二期直连新加  add  by dongxiangyang  20201009  */
	// 数据检查
	$('#SvrPerProDeclareReg001_btn_checkData').click(function(){
		var paramObj = getSearchParams();
		paramObj.opertionType = "checkData";
		paramObj.sendStatus = paramObj.sendStatus;
		chbCommonCheck(table, paramObj, 'submitBeforeCheck', dataTitle, 'submitChb');
	});
	/**
	 * 报送
	 */
	$('#SvrPerProDeclareReg001_btn_submit').click(function () {
		var paramObj = getSearchParams();
		paramObj.opertionType = "submitChb";
		chbCommonCheck(table, paramObj, 'submitBeforeCheck', dataTitle, 'submitChb');
	});
	/**
	 * 重新报送(只有数据状态为新建、反馈状态为发送失败的数据可以重新报送)
	 *
	 */
	$('#SvrPerProDeclareReg001_btn_reSubmit').click(function () {
		var paramObj = getSearchParams();
		paramObj.opertionType = "reSubmitChb";
		chbCommonCheck(table, paramObj, 'submitBeforeCheck', dataTitle, 'submitChb');
	});
	/**end   中债一二期直连新加  add  by dongxiangyang  20201009  */
	
    /**
	 * 手工处理
	 */
	$('#SvrPerProDeclareReg001_btn_manual')
			.click(
					function() {
						var rows = table.getAllCheckedRows();
						var param = {};
						if (rows.length === 0) {
							var sendStatus = $("#sendStatus").val();
							var submitFeedbackStatus = $(
									"#submitFeedbackStatus").val();
							if (!((sendStatus && sendStatus.length == 1 && sendStatus[0] == '01') || (sendStatus
									&& sendStatus.indexOf('02') > -1
									&& submitFeedbackStatus && (submitFeedbackStatus.indexOf('02') > -1 || submitFeedbackStatus.indexOf('04') > -1
									|| submitFeedbackStatus.indexOf('00') > -1 || submitFeedbackStatus.indexOf('06') > -1)))) {
								Joyin
										.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送中、发送失败、中债退回和发送异常的数据进行手工处理");
								return;
							}
							var paramObj = getSearchParams();
							paramObj.flag = '01';
							Joyin.confirm("请确认是否要手工处理符合查询条件的全部记录？", function() {
								layui.layer.open({
									type : 3
								});
								Joyin.ajaxJson('doManual', paramObj,
										function() {
											table.load();
											layui.layer.closeAll();
										});
							});
						} else {
							for (var i = 0; i < rows.length; i++) {
								if (!(rows[i].sendStatus == '01' || (rows[i].sendStatus == '02' && (rows[i].submitFeedbackStatus == '发送失败' || rows[i].submitFeedbackStatus == '中债退回'
									|| rows[i].submitFeedbackStatus == '发送中' || rows[i].submitFeedbackStatus == '发送异常')))) {
									Joyin
											.error("只能选择报送状态为未报送的数据，或报送状态为接口报送且反馈状态为发送中、发送失败、中债退回和发送异常的数据进行手工处理，请重新选择！");
									return;
								}
							}
							var keyList = [];
							param.keyList = JSON.stringify(rows);
							Joyin.confirm("请确认是否要手工处理选中记录？", function() {
								layui.layer.open({
									type : 3
								});
								Joyin.ajaxJson('doManual', param, function() {
									table.load();
									layui.layer.closeAll();
								});
							});
						}

					});

	/**
	 * 取消手工处理
	 */
	$('#SvrPerProDeclareReg001_btn_cancelManual')
			.click(
					function() {
						var manArr = table.getAllCheckedRows();
						var param = {};
						if (!manArr || manArr.length == 0) {
							var sendStatus = $("#sendStatus").val();
							if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '03')) {
								Joyin.error("只能选择手工处理的数据，请重新选择！");
								return;
							}
							var paramObj = getSearchParams();
							paramObj.flag = '01';
							Joyin.confirm("请确认是否要取消手工处理符合查询条件所有记录？",
									function() {
										layui.layer.open({
											type : 3
										});
										Joyin.ajaxJson('cancelMannul',
												paramObj, function() {
													table.load();
													layui.layer.closeAll();
												});
									});
						} else {
							for (var i = 0; i < manArr.length; i++) {
								if (manArr[i].sendStatus != "03") {
									Joyin.error("只能选择手工处理的数据，请重新选择！");
									return;
								}
							}
							;
							var keyList = [];
							param.keyList = JSON.stringify(manArr);
							Joyin.confirm("请确认是否要取消手工处理选中记录？", function() {
								layui.layer.open({
									type : 3
								});
								Joyin.ajaxJson('cancelMannul', param,
										function() {
											table.load();
											layui.layer.closeAll();
										});
							});
						}

					});

	var uploadTheFile = function(data,index){
		Joyin.uploadFile(
				{	
					exts : 'zip',
			        hideTpl : true
		       } ,
		function(fileName,param,fileNo,targetFile,fileNo) {
		    Joyin.ajax('uploadFile', {
				fileName : fileName,
				registSeqno : data.registSeqno,
				prodId : data.prodId,
				param : param
			},
			function() {
				table.reload();
				//layui.layer.closeAll();
			});
		    
		}
		       );
		
	}
	
	table.bind('click', '.upload', function(data, index) {
		var dataArr=[];
		var dataArrStr;   
		if (data.sendStatus != '01') {
			Joyin.error("只有未报送的记录可以上传！");
			return;
		}else{
			for(var i = 0; i < 1; i++){
				var dataSrc={
						fileNo:data.fileNo,
						prodId:data.prodId,
						registSeqno:data.registSeqno
				}
				dataArr[i]=dataSrc;
			}
		}

		dataArrStr=JSON.stringify(dataArr);
		Joyin.confirm("请确认是否要导入数据？", function() {

			var flag=false;
			if(data.fileNo && data.fileNo != null){
				flag=true;
			}
			if(flag){
				Joyin.confirm('所选产品已存在附件，是否覆盖？',
						function(){
					Joyin.dialog(JoyinUtil.getLabel("附件上传-修改"), basePath
		                    + "report/chb/perprodeclarereg/PerProDeclareReg002/init", "70%",
		                    "60%", {
		                        pageModel : PAGE_MODEL_NEW,
		                        businessId : data.prodId,
		                        dataArrStr : dataArrStr
		                    },function(){
		                    	table.reload();
		                    });
				})
			}else{
				Joyin.dialog(JoyinUtil.getLabel("附件上传"), basePath
	                    + "report/chb/perprodeclarereg/PerProDeclareReg002/init", "70%",
	                    "60%", {
	                        pageModel : PAGE_MODEL_NEW,
	                        businessId : data.prodId,
	                        dataArrStr : dataArrStr
	                    },function(){
	                    	table.reload();
	                    });
			}
        });	
		
		
	});
	
	


//		$('#SvrPerProDeclareReg001_btn_upload').click(function() {
//		Joyin.uploadFile({
//			exts : 'zip',
//			hideTpl : true
//		}, function(fileName, param, fileNo, targetFile, fileId) {
//			Joyin.ajax('uploadFile', {param}, function() {
//				table.reload();
//			});
//		});
//	});
		
		
		

	table.bind('click', '.download', function(data, index) {
		if (!data.fileNo || data.fileNo == null) {
			Joyin.error("未上传附件，不能下载！");
			return;
		}
		parm = {};
		var url = JoyinUtil.getRealPath('/framework/download/' + data.fileNo);
		Joyin.ajax(url, parm, function downloadFileByForm() {
			Joyin.exportExcel('/framework/download/' + data.fileNo, parm);
		},
		function errorDownload(){
			Joyin.error("文件未找到！");
		}
		);
	});
	
	
	
	
	table.bind('click', '.fileView', function(data, index) {
		
		$.post('getFileList', {fileNo:data.fileNo}, function(str){
			  layer.open({
			    type: 0,
			    content:decodeURIComponent(str), //注意，如果str是object，那么需要字符拼接。
			    area: ['300px', '300px'],
			    skin: 'layui-layer-lan',
			    offset: 'rb'
			  });
			});
		
  

	});

	table.bind('click', '.delt', function(data, index) {
		if (data.sendStatus != '01') {
			Joyin.error("不允许删除已登记数据的附件");
			return;
		}
		if (!data.fileNo || data.fileNo == null) {
			Joyin.error("未上传附件，不能删除！");
			return;
		} else {
			Joyin.confirm("请确认是否要删除已存在的附件？", function() {
				
				Joyin.ajax('deltFile', {
					registSeqno : data.registSeqno,
					fileNo:data.fileNo
				}, function() {
					table.reload();
				})
				
				

				

			});
		}
	});

	function init() {
		console.log(111)
		if (isMain == '01') {
			setTimeout(function() {
				if($('#sendStatus').val()=='02'||$('#sendStatus').val()=='03'){
          		   $('#submitDate').val(searchDataStart+' - '+searchDataEnd);
          	   }else{
          		   $('#submitDeadLine').val(searchDataStart + ' - ' + searchDataEnd);
          	   }
				table.load();
			}, 500);
		}
		
	}
	;
	init();

	/**
	 * 九大附件批量上传
	 */
	var nineFileUpload = function(){
		Joyin.uploadFile(
			{
				exts : 'zip',
				hideTpl : true
			} ,
			function(fileName,param) {
				// 上传校验
				Joyin.ajax('nineFileUploadCheck', {fileName : fileName, param : param}, function(result){
					// 调用上传方法
					Joyin.ajax('nineFileUpload', {fileName : fileName, param : param}, function() {
						table.reload();
					})
				});
			}
		);
	}

	$(function() {
		// 绑定按钮事件
		$('#SvrPerProDeclareReg001_btn_search').click(function() {
			table.load();
		});

		/**
		 * 九大附件批量上传（杭州版本）
		 */
		$('#SvrPerProDeclareReg001_btn_fileUpload').click(function() {
			nineFileUpload();
		});
		
		/**
		 * 九大附件批量上传-批量上传（徽商合并）
		 */
		$('#SvrPerProDeclareReg001_btn_batchFileUpload').click(function() {
			var uploadArr = table.getAllCheckedRows();
			var uploadArrStr=JSON.stringify(uploadArr);
			var dataArr=[];
			var dataArrStr;
			if(!uploadArr || uploadArr.length == 0){
				Joyin.error("请选择要上传的数据！");
				return;
			}else{
				for(var i = 0; i < uploadArr.length; i++){
					if(uploadArr[i].sendStatus != '01'){
						Joyin.error("只有未报送的数据才能进行上传！");
						return;
					}
					var data={
							fileNo:uploadArr[i].fileNo,
							prodId:uploadArr[i].prodId,
							registSeqno:uploadArr[i].registSeqno
					}
					dataArr[i]=data;
					
				}
				dataArrStr=JSON.stringify(dataArr);
				Joyin.confirm("请确认是否要导入数据？", function() {
					var flag=false;
					for(var i = 0; i < uploadArr.length; i++){
						if(uploadArr[i].fileNo && uploadArr[i].fileNo != null){
							flag=true;
						}
					}
					if(flag){
						Joyin.confirm('所选产品已存在附件，是否覆盖？',
								function(){
							Joyin.dialog(JoyinUtil.getLabel("附件上传-修改"), basePath
				                    + "report/chb/perprodeclarereg/PerProDeclareReg002/init", "70%",
				                    "60%", {
				                        pageModel : PAGE_MODEL_NEW,
				                        businessId : uploadArr.prodId,
				                        dataArrStr : dataArrStr
				                    },function(){
				                    	table.reload();
				                    });
						})
					}else{
						Joyin.dialog(JoyinUtil.getLabel("附件上传"), basePath
			                    + "report/chb/perprodeclarereg/PerProDeclareReg002/init", "70%",
			                    "60%", {
			                        pageModel : PAGE_MODEL_NEW,
			                        businessId : uploadArr.prodId,
			                        dataArrStr : dataArrStr
			                    },function(){
			                    	table.reload();
			                    });
					}
		        });
//				nineFileUpload();
			}
		});
	});


}());

