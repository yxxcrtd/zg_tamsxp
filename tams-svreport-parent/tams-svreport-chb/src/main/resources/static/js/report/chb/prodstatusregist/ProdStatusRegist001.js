$(function() {
    //跳转的页码
      var pageCount = 1;
      /**
       * 获取查询参数
       */
      var getSearchParams = function() {
          return {
              //产品ID
              prodId:$('#prodId').val()==null?null:$('#prodId').val().join(","),
              //产品登记编码
              prdRegistCode:$('#prdRegistCode').val()==null?null:$('#prdRegistCode').val().join(","),
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
              { title: "数据状态", field: 'statusStr',width:120},
              { field: 'sendStatus',visible:false},
              { field: 'status',visible:false},
              { title: "数据日期", field: 'statusDate', type: 'datetime', sortField:'statusDate'},
              { title: "报送状态", field: 'sendStatusStr',width:120 , sortField:'sendStatus',
                  render:function (value,visible,row,options) {
                      if(value == "接口报送"){
                          return '<a href="javascript:void(0)" class="sendStatusClass">'+value+'</a>';
                      }else{
                          return value;
                      }
                  }},
              { title: "报送日期", field: 'submitTime', type: 'datetime', sortField:'submitTime'},
              { title: "报送截止日", field: 'chbSubmitDeadline', type: 'datetime', sortField:'chbSubmitDeadline'},
              { title: "反馈状态", field: 'submitFeedbackStatusStr',width:120,
                  render:function (value,visible,row,options) {
                      if(value == "发送中" || value == "发送异常"){
                          return '<a href="javascript:void(0)" class="feedbackStatusClass">'+value+'</a>';
                      }else{
                          return value;
                      }
                  }},
              { title: "产品名称", field: 'prodName',width:250},
              {title: '发行机构代码', field:'issuePartyId', width:200, sortField:'issuePartyId'},
              {title: '产品登记编码', field:'prdRegistCode', width:200, sortField:'prdRegistCode'},
              {title: '理财产品总资产金额(元)', field:'prdTotalAmt',type:'money', width:200, sortField:'prdTotalAmt'},
              {title: '理财产品杠杆率(%)', field:'prdLeverage', width:200,type:'money', sortField:'prdLeverage'},
              { title: "产品状态统计日", field: 'prdStatusDateStr',  width:150,sortField:'prdStatusDate'},
              { field: 'prdStatusDate', type: 'datetime',visible:false},
              { title: "备注", field: 'remark',width:300}
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
      $('#SvrProdStatusRegist001_btn_chbexport').click(function() {
          exportExcel();
      });

      //手工处理 
      $('#SvrProdStatusRegist001_btn_manual').click(function() {
          var manArr=table.getAllCheckedRows();
          if(!manArr || manArr.length ==0){
              //当不选择时默认选择符合查询条件的全部数据
              var submitStatus = $("#submitStatus").val();
              var submitFeedbackStatus = $("#submitFeedbackStatus").val();
              if(!((submitStatus && submitStatus.length == 1 &&submitStatus[0] == '01')
                      || (submitStatus && submitStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1 ||  submitFeedbackStatus.indexOf('04')> -1)))){
                  Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理");
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
                  if (!(manArr[i].sendStatus == '01' || (manArr[i].sendStatus == '02' && (manArr[i].submitFeedbackStatus == '发送失败' || manArr[i].submitFeedbackStatus == '中债退回')))) {
                      Joyin.error("只能选择报送状态为未报送的数据，或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理，请重新选择！");
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
     $('#SvrProdStatusRegist001_btn_cancelManual').click(function() {
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
      var exportExcel = function() {
          var expArr=table.getAllCheckedRows();
          var param={};
          if(expArr.length ==0){
              Joyin.error("请勾选需要导出的月份数据! ");
              return;
          }
          // 用来标注选择数据中存在修改或者已报送数据，默认为false
          var statuFlag = false;
          var length = expArr.length;
          for(var i=0;i<length;i++){
              param.prdStatusDateStr = expArr[i].prdStatusDateStr;
              // 含有删除状态数据
              if (expArr[i].status=="03") {
                  Joyin.error("所选数据中包含数据状态为删除的数据，请重新选择。");
                  return;
                  // 含有修改状态数据或者已报送数据，将标志改为true
              } else if (expArr[i].status=="02"||expArr[i].sendStatus!="01") {
                  statuFlag = true;
              }
              //勾选多条数据时判断是否为相同年月份的数据
              if (length>1) {
                  for(var j=1;j<length;j++){
                      if (expArr[i].prdStatusDateStr!=expArr[j].prdStatusDateStr){
                          Joyin.error("所选报送数据包含多个报送月份数据，不允许中债导出Excel");
                          return;
                      }
                  }
              }

          }
          var keyList = [];
          param.keyList = JSON.stringify(expArr);
          param.expKeys = keyList.join(',');
          if (statuFlag) {
              Joyin.confirm("导出数据包含修改或已报送数据，请确认是否导出！", function() {
                  confirmExport(param, expArr);
              });
          } else {
              confirmExport(param, expArr);
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
                  //有未报送成功的数据
                  isNotSendData = true;
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

    // 数据检查
    $('#SvrProdStatusRegist001_btn_checkData').click(function(){
        var paramObj = getSearchParams();
        paramObj.opertionType = "checkData";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    // 报送
    $('#SvrProdStatusRegist001_btn_submit').click(function(){
        var paramObj = getSearchParams();
        paramObj.opertionType = "submitChb";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
    // 重新发送
    $('#SvrProdStatusRegist001_btn_reSubmit').click(function(){
        var paramObj = getSearchParams();
        paramObj.opertionType = "reSubmitChb";
        chbCommonCheck(table, paramObj, 'reportCheck', dataTitle, 'submitChb');
    });
         
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
             $('#SvrProdStatusRegist001_btn_search').click(function() {
                 table.load();
             });
         });
  }());