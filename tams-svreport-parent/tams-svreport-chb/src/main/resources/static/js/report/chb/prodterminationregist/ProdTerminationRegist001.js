$(function() {
    //跳转的页码
      var pageCount = 1;
      /**
       * 获取查询参数
       */
      var getSearchParams = function() {
          return {
              //产品名称
              prodName:$('#prodname').val()==null?null:$('#prodname').val().join(","),
              //产品登记编码
              regCode:$('#regCode').val()==null?null:$('#regCode').val().join(","),
              //产品终止日
              prodDateStart:$('#prodDate').startDate(),
              prodDateEnd:$('#prodDate').endDate(),
              //报送截止日
              submitendDateStart:$('#submitendDate').startDate(),
              submitendDateEnd:$('#submitendDate').endDate(),
              //数据日期
              dataDateStart:$('#dataDate').startDate(),
              dataDateEnd:$('#dataDate').endDate(),
              //报送日期
              submitDateStart:$('#submitDate').startDate(),
              submitDateEnd:$('#submitDate').endDate(),
              //报送状态
              sendStatus : $('#submitStatus').val()==null?null:$('#submitStatus').val().join(","),
              //数据状态
              status : $('#status').val()==null?null:$('#status').val().join(","),
              //反馈状态
              submitFeedbackStatus: $('#submitFeedbackStatus').val()==null?null:$('#submitFeedbackStatus').val().join(",")
          }
      };
  
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
          showDetail: true,//双击详情对比画面
          cols:[
              { field: 'registSeqno',width:0, sortField:'',visible:false},
              { title: "数据状态", field: 'statusStr',width:100,sortField:'status'},
              { field: 'sendStatus',visible:false},
              { field: 'status',visible:false},
              { field: 'sumitSeq', visible:false},
              { title: "数据日期", field: 'statusDate', type: 'datetime', sortField:'statusDate'},
              { title: "报送状态", field: 'sendStatusStr',width:100 , sortField:'sendStatus',
                  render:function (value,visible,row,options) {
                      if(value == "接口报送"){
                          return '<a href="javascript:void(0)" class="sendStatusClass">'+value+'</a>';
                      }else{
                          return value;
                      }
                  }},
              { title: "报送日期", field: 'submitTime', type: 'datetime', sortField:'submitTime'},
              { title: "报送截止日", field: 'chbSubmitDeadline', type: 'date', sortField:'chbSubmitDeadline'},
              { title: "反馈状态", field: 'submitFeedbackStatus',width:100 ,sortField:'submitFeedbackStatus',
                  render:function (value,visible,row,options) {
                      if(value == "发送中" || value == "发送异常"){
                          return '<a href="javascript:void(0)" class="feedbackStatusClass">'+value+'</a>';
                      }else{
                          return value;
                      }
                  }},
              { title: "发行机构代码", field: 'issuePartyId',width:150 , sortField:'issuePartyId'},
              { title: "理财产品代码", field: 'prodId',width:150 , sortField:'prodId'},
              {title: '产品登记编码', field:'prodRegCode', width:150, sortField:'regCode'},
              {title: '理财产品实际终止日期', field:'mdate', width:200,type: 'date', sortField:'mdate'},
              {title: '银行实际实现收入', field:'profit',type:'money', width:200, sortField:'profit'},
              {title: '兑付客户收益（元）', field:'customProfit', width:200,type:'money', sortField:'customProfit'},
              {title: '兑付客户总金额（元）', field:'customAmt', width:200,type:'money',digit:2,  sortField:'customAmt'},
              {title: '兑付客户总份额', field:'customQty',type:'money',digit:5, width:200, sortField:'customQty'},
              {title: '客户实际年化收益率(%)',field:'cusYearYield',type:'number',digit:5, width:200, sortField:'cusYearYield'},
              {title: '产品实际年化收益率(%)',field:'prdYearYield',type:'number',digit:5, width:200, sortField:'prdYearYield'},
              {title: '本机构托管费（元）',field:'custFeeIn',type:'money',digit:2, width:200, sortField:'custFeeIn'},
              {title: '本机构管理费（元）',field:'manFeeIn', width:200,type:'money',digit:2, sortField:'manFeeIn'},
              {title: '本机构销售手续费（元）',field:'couFeeIn', width:250,type:'money',digit:2, sortField:'couFeeIn'},
              {title: '本机构其他产品费用（元）',field:'otherFeeIn', width:250,type:'money',digit:2, sortField:'otherFeeIn'},
              {title: '其他机构托管费（元）',field:'custFeeOut', width:200,type:'money',digit:2, sortField:'custFeeOut'},
              {title: '其他机构管理费（元）',field:'manFeeOut', width:250,type:'money',digit:2, sortField:'manFeeOut'},
              {title: '其他机构销售手续费（元）',field:'couFeeOut', width:250,type:'money',digit:2, sortField:'couFeeOut'},
              {title: '投资顾问费用（元）',field:'advFeeOut', width:200,type:'money',digit:2, sortField:'advFeeOut'},
              {title: '其他机构其他产品费用（元）',field:'otherFeeOut', width:250,type:'money',digit:2, sortField:'otherFeeOut'}
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
      
      //导出所有查询结果
      $('#SvrProdTerminationRegist001_btn_export').click(function() {
          exportExcel("1");
      });
      
      //导出中债Excel
      $('#SvrProdTerminationRegist001_btn_chbexport').click(function() {
          exportExcel("0");
      });

      //手工处理 
      $('#SvrProdTerminationRegist001_btn_manual').click(function() {
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
                  Joyin.ajaxJson('update', paramObj, function() {
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
                  Joyin.ajaxJson('update', {keyList : JSON.stringify(manArr)}, function() {
                      table.load();
                      layui.layer.closeAll();
                  });
              });
          }
      });
      
      /**
       * 取消手工处理 
       */
     $('#SvrProdTerminationRegist001_btn_cancelManual').click(function() {
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
              Joyin.ajaxSync('findPage',param, function(result){
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
                      Joyin.ajaxJson('update', param, function() {
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
  //            $.each(expArr,function(i,v){
  //                v.sendStatus=v.sendStatusCode;
  //                v.status=v.statusCode;
  //                v.ccy=v.ccyCode;
  //            })
              param.keyList = JSON.stringify(expArr);
              param.expKeys = keyList.join(',');
              param.flag = flag;
              if (statuFlag) {
                  Joyin.confirm("导出数据包含修改或已报送数据，请确认是否导出！", function() {
                      confirmExport(param, expArr);
                  });
              } else {
                  confirmExport(param, expArr);
              }
  //            Joyin.exportExcel('export', param);
  //            Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
  //            	layui.layer.open({type:3});
  //                Joyin.ajaxJson('update', {keyList : JSON.stringify(expArr)}, function() {
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
               　　　Joyin.ajaxJson('update', {keyList : JSON.stringify(expArr)}, function() {
                   　　　table.load();
                  　　　 layui.layer.closeAll();
              　　　 });
          　　 });
		　　}
      };

    /*add by zhangliang 20201019 start*/
    // 数据检查
    $('#SvrProdTerminationRegist001_btn_checkData').click(function(){
        var paramObj = getSearchParams();
        paramObj.opertionType = "checkData";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    // 报送
    $('#SvrProdTerminationRegist001_btn_submit').click(function(){
        var paramObj = getSearchParams();
        paramObj.opertionType = "submitChb";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    // 重新发送
    $('#SvrProdTerminationRegist001_btn_reSubmit').click(function(){
        var paramObj = getSearchParams();
        paramObj.opertionType = "reSubmitChb";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    /*add by zhangliang 20201019 end*/

      // add by wangmin 20180827 end
         
         function init(){
             if(isMain=='01'){
                 setTimeout(function() {
                     if($('#submitStatus').val()=='02'||$('#submitStatus').val()=='03'){
                         $('#submitDate').val(searchDataStart+' - '+searchDataEnd);
                     }else{
                         $('#submitendDate').val(searchDataStart+' - '+searchDataEnd);
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
             $('#SvrProdTerminationRegist001_btn_search').click(function() {
                 table.load();
             });
         });
  }());