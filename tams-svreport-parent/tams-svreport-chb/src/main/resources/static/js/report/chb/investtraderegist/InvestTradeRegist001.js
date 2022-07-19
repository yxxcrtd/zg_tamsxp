$(function() {
    //跳转的页码
      var pageCount = 1;
      /**
       * 获取查询参数
       */
      var getSearchParams = function() {
          return {
              //产品代码
              prodCode:$('#prodName').val()==null?null:$('#prodName').val().join(","),
              //产品登记编码
              prodRegCode:$('#prodRegCode').val()==null?null:$('#prodRegCode').val().join(","),
              //行内交易编码
              tradeCode:$('#tradeCode').val()==null?null:$('#tradeCode').val().join(","),
              //资产代码
              assetCode:$('#assetCode').val()==null?null:$('#assetCode').val(),
              //资产已登记
              assetRegist:$('#assetRegist').val()==null?null:$('#assetRegist').val().join(","),
              //交易日
              dealdateStart:$('#dealdate').startDate(),
              dealdateEnd:$('#dealdate').endDate(),
              //报送状态
              sendStatus : $('#submitStatus').val()==null?null:$('#submitStatus').val().join(","),
              //反馈状态
              submitFeedbackStatus: $('#submitFeedbackStatus').val()==null?null:$('#submitFeedbackStatus').val().join(","),
              //报送日期
              submitDateStart:$('#submitDate').startDate(),
              submitDateEnd:$('#submitDate').endDate(),
              //数据日期
              dataDateStart:$('#dataDate').startDate(),
              dataDateEnd:$('#dataDate').endDate(),
              //报送截止日
              submitDeadLineStart:$('#submitDeadLine').startDate(),
              submitDeadLineEnd:$('#submitDeadLine').endDate(),
              //数据状态
              status:$('#status').val()==null?null:$('#status').val().join(",")
          }
      };
  
      /**
       * 定义数据表格
       */
      var table=$("#dataTable").joyintable({
          ajax: {
              url: 'searchPage',
              type: 'POST',
              data: function() {
                  return getSearchParams();
              }
          },
          showDetail: true,//双击详情对比画面
          cols:[
              { field: 'registSeqno',visible:false},
              { field: 'status',visible:false},
              { title: "relatedTradeStatus",  visible:false, field:'relatedTradeStatus'},
              {field:'rivalPartyType',visible:false},
              { title: "数据状态", field: 'statusName',width:100,sortField:'status'},
              { title: "数据日期", field: 'statusDate', type: 'datetime',sortField:'statusDate'},
              { field: 'sendStatus',visible:false},
              {field:'sumitSeq', visible:false},
              { title: "报送状态", field: 'sendStatusName',width:100 ,sortField:'sendStatus',
                  render:function (value,visible,row,options) {
                      if(value == "接口报送"){
                          return '<a href="javascript:void(0)" class="sendStatusClass">'+value+'</a>';
                      }else{
                          return value;
                      }
                  }},
              { title: "报送日期", field: 'submitTime', type: 'datetime',sortField:'submitTime'},
              { title: "报送截止日", field: 'chbSubmitDeadline', type: 'datetime',sortField:'chbSubmitDeadline'},
              { title: "反馈状态", field: 'submitFeedbackStatus',width:100 ,sortField:'submitFeedbackStatus',
                  render:function (value,visible,row,options) {
                      if(value == "发送中" || value == "发送异常"){
                          return '<a href="javascript:void(0)" class="feedbackStatusClass">'+value+'</a>';
                      }else{
                          return value;
                      }
                  }},
              { title: "产品代码", field: 'prodId',width:100 ,sortField:'prodId'},
              { title: "产品名称", field: 'prodName',width:100 ,sortField:'prodName'},
              // modify by dubaojun 2020-10-30 产品登记编码导出为空，字段设置有误问题修复 start
              {title: '产品登记编码', field:'prodRegistCode', width:100,sortField:'prodRegistCode'},
              // modify by dubaojun 2020-10-30 产品登记编码导出为空，字段设置有误问题修复 end
              {title: "发行机构代码",field:'registBankCode', width:150,sortField:'registBankCode'},
              {title: '行内交易编码', field:'tradeCode', width:100,sortField:'tradeCode'},
              {title: '行内资产负债编码', field:'assetCode', width:100,sortField:'assetCode'},
              {title: '币种', field:'ccy', width:100,sortField:'ccy'},
              {title: '发生金额', field:'amt', width:100, digit:2,type:'money',sortField:'amt'},
              {title: '折算人民币金额（元）', field:'cnyAmt', digit:2, type:'money',width:100,sortField:'cnyAmt'},
              {title: '数量',field:'num', digit:5, type:'number',width:150,sortField:'num'},
              {title: '资产计量方式',field:'assetMethod',width:150,sortField:'assetMethod'},
              {title: '资金流动类型',field:'curAssetType', width:150,sortField:'curAssetType'},
              {title: '资金流动类型说明',field:'curAssetdetType', width:200,sortField:'curAssetdetType'},
              {title: '交易日',field:'dealdate', width:200,type: 'date',sortField:'dealdate'},
              { title: "交易发起时间", field:'tradeTimeStr',width:'12%',sortField:'tradeTime'},
              {title: '交易对手方',field:'tradeCounter', width:200,sortField:'tradeCounter'},
              { title: "关联交易情况", field:'relatedTradeStatusStr',width:'12%',sortField:'relatedTradeStatus'},
              {title: '交易对手方类型',field:'rivalPartyTypeStr',width:150,sortField:'rivalPartyType'},
              {title: '单位成交价格（全价）',field:'unitPriceFull', digit:4,type:'money',width:200,sortField:'unitPriceFull'},
              {title: '单位成交价格（净价）',field:'unitPriceNet', digit:4,type:'money',width:200,sortField:'unitPriceNet'},
              {title: '到期收益率(%)',field:'endRate',type:'number', digit:5,width:200,sortField:'endRate'},
              {title: '成交编号/合同号',field:'contractCode', width:200,sortField:'contractCode'},
              {title: '交易审批人身份证号',field:'investApproverId', width:200,sortField:'investApproverId'},
              {title: '交易审批人姓名',field:'investApproverName', width:150,sortField:'investApproverName'},
              {title: '交易员身份证号',field:'investTraderId', width:200,sortField:'investTraderId'},
              {title: '交易员姓名',field:'investTraderName', width:150,sortField:'investTraderName'},
              {title: '备注',field:'remark', width:200,sortField:'remark'}
          ]
      });

      //绑定点击跳转反馈文件管理页面
      table.bind('click', '.feedbackStatusClass', function(data, index) {
          Joyin.dialog(JoyinUtil.getLabel("中债反馈文件管理"),
              basePath + "svreport/system/chbfeedbackfilemanagement/ChbFeedbackFileManagement001/init",
              "95%", "96%", {       //pageModel:PAGE_MODEL_REFERENCE,
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
      
      //导出中债Excel
      $('#InvestTradeRegist001_btn_chbexport').click(function() {
          exportExcel("0");
      });
      
      /**
       * 导出
       */
      var exportExcel = function(flag) {
          var expArr=table.getAllCheckedRows();
          var param={};
          if(!expArr || expArr.length ==0){
              param = getSearchParams();
              param.flag = flag;
              if(flag=="0"){
                  param.status="01";
                  param.sendStatus="01";
              }
              var isHaveData = true;
              Joyin.ajaxSync('searchPage',param, function(result){
                  if (!result || result.root.length == 0){
                      isHaveData = false;
                      return;
                  }
              })
              Joyin.exportExcel("export", param);
              if (isHaveData){
                  Joyin.confirm("导出成功，是否自动标识新建未报送数据的报送状态为“手工处理”？", function() {
                      layui.layer.open({type:3});
                      Joyin.ajaxJson('manual', param, function() {
                          table.load();
                          layui.layer.closeAll();
                      });
                  });
              }
          }
          else{
              // 用来标注选择数据中存在修改或者已报送数据，默认为false
              var statuFlag = false;
              if (flag == "0") {
                  for (var i = 0; i < expArr.length; i++) {
  //					if (expArr[i].status != "01" || expArr[i].sendStatus != "01"){
  //						Joyin.error("只能导出新建未报送的数据，请重新选择");
  //						return;
  //					}
                      // 含有删除状态数据
                      if (expArr[i].status=="03") {
                          Joyin.error("所选数据中包含数据状态为删除的数据，请重新选择!");
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
              param.expKeys = keyList.join(',');
              param.keyList = JSON.stringify(expArr);
              param.flag = flag;
              if (statuFlag) {
                  Joyin.confirm("导出数据包含修改或已报送数据，请确认是否导出！", function() {
                      confirmExport(param, expArr);
                  });
              } else {
                  confirmExport(param, expArr);
              }
  //			Joyin.exportExcel("export", param);
  //	        Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
  //	        	layui.layer.open({type:3});
  //	            Joyin.ajaxJson('manual', {keyList : JSON.stringify(expArr)}, function() {
  //	                table.load();
  //	                layui.layer.closeAll();
  //	            });
  //	        });
          }
          
      };
      
      /**
       * 确认导出之后的操作
       */
      var confirmExport = function(param, expArr) {
          Joyin.exportExcel("export", param);
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
                      Joyin.ajaxJson('manual', {keyList : JSON.stringify(expArr)}, function() {
                          table.load();
                          layui.layer.closeAll();
                      });
                  });
            }
      };
      
      //手工处理 
      $('#InvestTradeRegist001_btn_manual').click(function() {
          // debugger
          var manArr=table.getAllCheckedRows();
          if(!manArr || manArr.length ==0){
              //当不选择时默认选择符合查询条件的全部数据
              var submitStatus = $("#submitStatus").val();
              var submitFeedbackStatus = $("#submitFeedbackStatus").val();
              if(!((submitStatus && submitStatus.length == 1 &&submitStatus[0] == '01')
                      || (submitStatus && submitStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1 ||  submitFeedbackStatus.indexOf('04')> -1
                  || submitFeedbackStatus.indexOf('00')>-1 ||  submitFeedbackStatus.indexOf('06')> -1)))){
                  Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送中、发送失败、中债退回和发送异常的数据进行手工处理");
                  return;
              }
              var paramObj = getSearchParams();
              paramObj.flag = "01";//判断是否选中记录
              Joyin.confirm("请确认是否要手工处理全部未报送记录？", function() {
                  //页面添加滚动条
                  layui.layer.open({type:3});
                  Joyin.ajaxJson('manual', paramObj, function() {
                      table.load();
                      layui.layer.closeAll();
                  });
              });
          }else{
              var keyList = [];
              for(var i=0;i<manArr.length;i++){
                  if (!(manArr[i].sendStatus == '01' || (manArr[i].sendStatus == '02' && (manArr[i].submitFeedbackStatus == '发送失败' || manArr[i].submitFeedbackStatus == '中债退回'
                        || manArr[i].submitFeedbackStatus == '发送中' || manArr[i].submitFeedbackStatus == '发送异常')))) {
                      Joyin.error("只能选择报送状态为未报送的数据，或报送状态为接口报送且反馈状态为发送中、发送失败、中债退回和发送异常的数据进行手工处理，请重新选择！");
                      return;
                  }
              };
              keyList = JSON.stringify(manArr);
              Joyin.confirm("请确认是否要手工处理选中记录？", function() {
                  layui.layer.open({type:3});
                  Joyin.ajaxJson('manual', {keyList : JSON.stringify(manArr)}, function() {
                      table.load();
                      layui.layer.closeAll();
                  });
              });
          }
      });
      
      /**
       * 取消手工处理 
       */
     $('#InvestTradeRegist001_btn_cancelManual').click(function() {
         var manArr=table.getAllCheckedRows();
         if(!manArr || manArr.length ==0){
             var sendStatus = $("#submitStatus").val();
                if(!(sendStatus && sendStatus.length==1 && sendStatus[0] == '03')){
                  Joyin.error("只能选择手工处理的数据，请重新选择！");
                  return;
                }
                var paramObj = getSearchParams();
                paramObj.flag = '01';
                Joyin.confirm("请确认是否要取消手工处理符合查询条件所有记录？", function() {
                 layui.layer.open({type:3});
              Joyin.ajaxJson('cancelMannul', paramObj, function(result) {
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
                  Joyin.ajaxJson('cancelMannul', {keyList: JSON.stringify(manArr)}, function(result) {
                      table.load();
                      layui.layer.closeAll();
                  });
             });
         }
          
     });

    /**start   中债一二期直连新加  add  by wangzehui  20201012  */
    /**
     * 数据检查
     */
    $('#InvestTradeRegist001_btn_checkData').click(function(){
        var paramObj = getSearchParams();
        paramObj.opertionType = "checkData";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    /**
     * 报送
     */
    $('#InvestTradeRegist001_btn_submit').click(function () {
        var paramObj = getSearchParams();
        paramObj.opertionType = "submitChb";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    /**
     * 重新发送
     */
    $('#InvestTradeRegist001_btn_reSubmit').click(function () {
        var paramObj = getSearchParams();
        paramObj.opertionType = "reSubmitChb";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    /**end   中债一二期直连新加  add  by wangzehui  20201012  */

     function init(){
  //			// 报送日期初始化
  //			$('#submitDate').val(searchDataStart + ' - ' + searchDataEnd);
  //			// 数据日期初始化
  //			$('#statusDate').val(searchDataStart + ' - ' + searchDataEnd);
         if(isMain=='01'){
             setTimeout(function() {
                 if($('#submitStatus').val()=='02'||$('#submitStatus').val()=='03'){
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
     }
     init();
     
     $(function(){
         // 绑定按钮事件
         $('#SvrInvestTradeRegist001_btn_search').click(function() {
             table.load();
         });
     });
  
     //导出删除模板
     $('#InvestTradeRegist001_btn_exportDel').click(function(){
         // 如果没有查询则返回提示
         if(!table.isSearched()) {
             return;
         }
         // 根据查询条件查询数据
         var param = getSearchParams();
         // 只导出删除未报送的数据
         param.status='03';
         param.sendStatus='01';
         var isHaveData = true;
         Joyin.ajaxSync('searchPage',param, function(result){
             if (!result || result.root.length == 0){
  //            	   Joyin.error("没有数据导出，请重新选择");
                 isHaveData = false;
  //            	   return;
             }
         })
  //           if (!isHaveData){
  //        	  return; 
  //           }
         // 选中的导出数据
         var expArr=table.getAllCheckedRows();
         // 报送状态标志
          var sendFlag = false;
         if(!expArr || expArr.length ==0){
             if (!isHaveData){
                 // 不勾选不能导出
                 Joyin.error("没有可导出的数据，请重新选择导出条件！");
                 return; 
              }
         }
         else{
              // 选中数据判断是否是删除和未报送状态
              for(var i=0;i<expArr.length;i++){
  //				    if(expArr[i].status !="03"||expArr[i].sendStatus !="01"){
  //				    Joyin.error("只能导出删除状态且未报送的数据，请重新选择");
  //				        return;
  //				    }
                  if(expArr[i].status !="03"){
                      Joyin.error("只能导出删除的数据，请重新选择");
                      return;
                  }
                     if(expArr[i].sendStatus !="01"){
                         sendFlag = true;
                  }
              }
              // 选中数据
              param.expKeys = JSON.stringify(expArr);
         }
         if (sendFlag) {
             Joyin.confirm("导出数据中含有已报送的数据，请确认是否导出！", function() {
              // 打开删除信息页面
                 Joyin.dialog(JoyinUtil.getLabel("SvrExportDeleteExcelConfirm001"),
                     basePath + "chb/common/ExportDeleteExcelConfirm001/init",
                     "50%", "50%", {pageModel:PAGE_MODEL_NEW,orgPageId:param.pageId,expKeys:param.expKeys},function(){
                         //防止出现查询转圈圈
                         setTimeout(function(){table.reload();},100);
                 });
             });
         } else {
             // 打开删除信息页面
             Joyin.dialog(JoyinUtil.getLabel("SvrExportDeleteExcelConfirm001"),
                 basePath + "chb/common/ExportDeleteExcelConfirm001/init",
                 "50%", "50%", {pageModel:PAGE_MODEL_NEW,orgPageId:param.pageId,expKeys:param.expKeys},function(){
                     //防止出现查询转圈圈
                     setTimeout(function(){table.reload();},100);
             });
         }
        
     });
         //判断字符是否为空的方法
         function isEmpty(obj) {
             if (typeof obj == "undefined" || obj == null || obj == "") {
                 return true;
             } else {
                 return false;
             }
         }
  }());