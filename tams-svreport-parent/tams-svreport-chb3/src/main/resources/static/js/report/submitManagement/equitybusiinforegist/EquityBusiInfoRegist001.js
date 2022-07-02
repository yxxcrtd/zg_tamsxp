$(function() {
    //跳转的页码
    var pageCount = 1;
    var daySelectRangeNum=0;
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
            busiTypes : $('#busiType').val()==null?null:$('#busiType').val().join(","),
            //数据状态
            status : $('#status').val()==null?null:$('#status').val().join(","),
            //数据日期
            updateTimeStart:$('#updateTime').startDate(),
            updateTimeEnd:$('#updateTime').endDate(),
            //报送日期
            submitDateStart:$('#submitDate').startDate(),
            submitDateEnd:$('#submitDate').endDate(),
            //反馈状态
            submitFeedbackStatus: $('#submitFeedbackStatus').val()==null?null:$('#submitFeedbackStatus').val().join(","),
            //登记银行编码
            registBankCode: $('#registBankCode').val(),
            //识别标识
            indentifyMark:$('#indentifyMark').val(),
            //产品
            // prdCode: $('#prdCode').val()==null?null:$('#prdCode').val().join(","),
            //产品登记编码
            prdRegistCode: $('#prdRegistCode').val()==null?null:$('#prdRegistCode').val().join(","),
            //持有日期
            holdDateStart:$('#holdDate').startDate(),
            holdDateEnd:$('#holdDate').endDate(),
            //机构代码
            orgCode : $('#orgCode').val()==null?null:$('#orgCode').val().join(","),
            //渠道
            channel: $('#channel').val()==null?null:$('#channel').val().join(","),
            //核心交易流水号
            busiSeqno : $('#busiSeqno').val(),
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
        done : function(){
            if(validKey){
                table.resize($(document).height() - $('.container').height() + table.getHeight());
                //标志是否在当前页选中数据
                var isChecked = false;
                var addTableData = table.getAllRows();
                if(addTableData){
                    $(addTableData).each(function(i,v){
                        if(validKey.prdRegistCode===v.prdRegistCode && validKey.indentifyMark===v.indentifyMark
                            && (Date.ofDate(validKey.holdDate).getTime()) === (Date.ofDate(v.holdDate).getTime())
                            && validKey.ccy===v.ccyCode
                            && validKey.registBankCode===v.registBankCode
                            && v.sendStatusCode==='01'){
                            table.checkRow(v.LAY_TABLE_INDEX);
                            isChecked = true;
                            dateCheckMethod();
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
        cols:[
            { title: "数据状态", field: 'statusName',width:100,sortField:'status'},
            { field: 'status',visible:false},
            { title: "数据日期", field: 'statusDateStr', width:100,sortField:'statusDate'},
            { title: "报送状态", field: 'submitStatusName',width:100 ,sortField:'submitStatus'},
            { field: 'submitStatus',visible:false},
            { title: "报送日期", field: 'submitTimeStr', width:100,sortField:'submitTime'},
            { title: "报送截止日", field: 'chbSubmitDeadline', type: 'date', width:100,sortField:'chbSubmitDeadline'},
            { title: "反馈状态", field: 'submitFeedbackStatus',width:100,visible: false},
            { title: "反馈状态", field: 'submitFeedbackStatusStr',width:100,sortField:'submitFeedbackStatus' /*界面查询性能优化,
                  render: function (data, type, row, meta) {
                      if(!row.submitFeedbackStatus){
                          return "";
                      }
                      if(row.submitFeedbackStatus=="01"){
                          return "<span class='debt-suc'>发送成功</span>";
                      }
                      if(row.submitFeedbackStatus=="02"){
                          return "<span class='debt-fail'>发送失败</span>";
                      }
                      if(row.submitFeedbackStatus=="03"){
                          return "<span class='debt-pass'>中债通过</span>";
                      }
                      if(row.submitFeedbackStatus=="04"){
                          return "<span class='debt-return'>中债退回</span>";
                      }
                      if(row.submitFeedbackStatus=="05"){
                          return "<span class='debt-return'>文件下载失败</span>";
                      }
                  }*/
            },
            {title: '报送流水号',field:'sumitSeq', width:150},
            {title: '发行机构代码',field:'registBankCode', width:200},
            {title: '识别标识',field:'indentifyMark', width:150},
            {title: '产品登记编码', field:'regCode', width:100},
            {title: '业务种类',field:'busiType', width:150},
            {title: '业务确认日期',field:'equityConfDate', width:150, type: 'date', sortField:'equityConfDate'},/*界面查询性能优化*/
            {title: '业务确认日期',field:'equityConfDateStr', width:150, sortField:'equityConfDate', visible: false},
            {title: '业务确认时间',field:'shareConfDatetime', width:150},
            {title: '币种',field:'ccy', width:150},
            {title: '金额',field:'amount', width:150,type:'number'},
            {title: '折算人民币金额（元）',field:'amountCny', width:180,type:'number'},
            {title: '确认净值',field:'prodValue', width:150,type:'number', digit:5},
            {title: '份额',field:'shares', width:150,type:'number', digit:5},
            {title: '费用',field:'feeAmt', width:150,type:'number'},
            {title: '渠道',field:'channel', width:150},
            {title: '销售合同号',field:'contractNo', width:150},
            {title: '核心交易流水号',field:'busiSeqno', width:150},
            {title: '理财账号',field:'finAcc', width:150},
            {title: '客户统一编号',field:'custContractNo', width:150},
            {title: '客户姓名',field:'custName', width:150},
            {title: '关联活期存款账号',field:'fundAcc', width:150},
            {title: '关联账号开户所在地',field:'fundAccPlace', width:150},
            {title: '是否代销',field:'isConsign', width:150},
            {title: '代销机构代码',field:'agencyCode', width:150},
            {title: '代销机构名称',field:'agencyName', width:150},
            {title: '代销机构所属监管机构',field:'agencyFsa', width:150},
            {title: '业务发生地所属监管',field:'busiFsa', width:150},
            {title: '交易柜员号',field:'tranTellName', width:150},
            {title: '备注',field:'remark', width:150},
              {field:'submitStatus', visible:false},
            {field:'checkSeqno', visible:false},
              {field:'busiType', visible:false}
        ]
    });
    
    
    
    //导出所有查询结果
    $('#EquityBusiInfoRegist001_btn_export').click(function() {
        exportExcel("1");
    });

    //导出中债Excel
    $('#EquityBusiInfoRegist001_btn_chbexport').click(function() {
        //exportExcel("0");
        exportZZExcel();
    });


    //导出删除模板
    $('#EquityBusiInfoRegist001_btn_exportDel').click(function() {
        if(!table.isSearched()) {
            return;
        }
        // 选中的导出数据
        // var expArr=table.getAllCheckedRows();
        // if(!expArr || expArr.length ==0){
        //     Joyin.error("没有选中的记录！");
        //     return;
        // }
        var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
            param = getSearchParams();
            if (param.status==null||param.status==''){
                param.status='03';
            }
            param.flag = "2";
       	    param.sendStatus="01";
//             Joyin.exportExcel("export",param);
//             Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
//                 Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
//                     layui.layer.open({type:3});
//                     Joyin.ajax('doExportManual',null, function() {
//                         table.load();
//                         layui.layer.closeAll();
//                     });
//                 });
//             },function() {
//                 $('iframe').contents().find("#loading").hide();
//             });
            // 打开删除信息页面
            Joyin.dialog(JoyinUtil.getLabel("SvrExportDeleteExcelConfirm001"),
                basePath + "/chb/common/EquityExportDeleteExcelConfirm001/init",
                "50%", "50%", {pageModel:PAGE_MODEL_NEW,orgPageId:"SvrEquityBusiInfoRegist001",paramKeys:JSON.stringify(param)},function(){
                    //防止出现查询转圈圈
                    setTimeout(function(){table.reload();},100);
                });
        }
        else{
            for(var i=0;i<expArr.length;i++){
                if(expArr[i].status !="03"||expArr[i].submitStatus !="01"){
                    Joyin.error("只能导出删除状态且未报送的数据，请重新选择");
                    return;
                }
            }
            var keyList = [];
            $.each(expArr, function(i, row) {
                keyList.push(row.busiSeqno + '|' + row.regCode + '|' + row.registBankCode+ '|' + row.indentifyMark+ '|' + row.submitStatus);
            });
            param.keyList = JSON.stringify(expArr);
            param.expKeys = keyList.join(',')
            param.flag = "2";
            // Joyin.exportExcel('exportDel', param);
            // Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0012"), function() {
            //     layui.layer.open({type:3});
            //     Joyin.ajax('doManual', {flag : '02',keyList: JSON.stringify(expArr)}, function() {
            //         table.load();
            //         layui.layer.closeAll();
            //     });
            // });
            // 打开删除信息页面
            Joyin.dialog(JoyinUtil.getLabel("SvrExportDeleteExcelConfirm001"),
                basePath + "/chb/common/EquityExportDeleteExcelConfirm001/init",
                "50%", "50%", {pageModel:PAGE_MODEL_NEW,orgPageId:"SvrEquityBusiInfoRegist001",expKeys:param.expKeys},function(){
                    //防止出现查询转圈圈
                    setTimeout(function(){table.reload();},100);
                });
        }
    });

    /**
     * 导出
     */
    var exportExcel = function(flag) {
        if(!table.isSearched()) {
            return;
        }
        var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){

            //业务确认日范围
            var holdDateStart=$('#holdDate').startDate();
            var holdDateEnd=$('#holdDate').endDate();
            if(holdDateStart.length == 0 ){
                Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0018"));
                return;
            }
            var start=Date.parse(holdDateStart);
            var end=Date.parse(holdDateEnd);
            dateSpan = end - start;
            dayDiff = Math.floor(dateSpan / (24 * 3600 * 1000))+1;

            if(dayDiff>daySelectRangeNum){
                //比较选择的日期范围
                Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0019"));
                return;
            }else if(dayDiff==1){
                param = getSearchParams();
                param.flag = flag;
                if(flag=="0" &&(param.status==null||param.status=='')){
                    param.status="01,02";
                }else if(flag=="2"){
                    if (param.status==null||param.status==''){
                        param.status='03';
                    }
                }
                Joyin.exportExcel("export",param);
            }else{
                Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0020"), function() {
                    param = getSearchParams();
                    param.flag = flag;
                    if(flag=="0" &&(param.status==null||param.status=='')){
                        param.status="01,02";
                    }else if(flag=="2"){
                        if (param.status==null||param.status==''){
                            param.status='03';
                        }
                    }
                    Joyin.exportExcel("export",param);
                });
            }
        }
        else{
            if(flag=="0")
            {
                for(var i=0;i<expArr.length;i++){
                    if(expArr[i].status=="删除"){
                        Joyin.error(JoyinUtil.getMessage("REPORT_EXPORT_0001"));
                        return;
                    }
                }
            }
            else if(flag=="2"){
                for(var i=0;i<expArr.length;i++){
                    if(expArr[i].status !="03"||expArr[i].submitStatus !="01"){
                        Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0012"));
                        return;
                    }
                }
            }

            var keyList = [];
            $.each(expArr,function(i,v){
                v.sendStatus=v.sendStatusCode;
                v.status=v.statusCode;
                v.ccy=v.ccyCode;
            })
            param.keyList = JSON.stringify(expArr);
            param.expKeys = keyList.join(',')
            param.flag = flag;
            Joyin.exportExcel('export', param);
        }

    };


    //导出中债excel
    var exportZZExcel = function(flag) {
        var expArr=table.getAllCheckedRows();

        //先判断有没有勾选数据
        if(!expArr || expArr.length ==0){
            //业务确认日
            var holdDateStart=$('#holdDate').startDate();
            var holdDateEnd=$('#holdDate').endDate();
            if(holdDateStart.length == 0 ){
                Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0018"));
                return;
            }
            var start=Date.parse(holdDateStart);
            var end=Date.parse(holdDateEnd);
            dateSpan = end - start;
            dayDiff = Math.floor(dateSpan / (24 * 3600 * 1000))+1;

            if(dayDiff>daySelectRangeNum){
                //比较选择的日期范围
                Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0019"));
                return;
            }else if(dayDiff==1){
                var param = getSearchParams();
                param.status='01,02';
                param.flag = "0";
                //没有勾选数据
                //默认导出满足所选查询条件（包括空查询条件）的所有数据状态为“新建”且报送状态为“未报送”的数据。
                //导出成功后，系统弹出确认框“导出成功，是否自动标识未报送数据的报送状态为“手工处理”？
                Joyin.ajax('countExportNum',param,function(dataNum){
                    if(dataNum>100000){
                        Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0016"), function() {
                            exportAndDoManual(param);
                        });
                    }else{
                        exportAndDoManual(param);
                    }
                });
            }
            else{
                Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0020"), function() {
                    var param = getSearchParams();
                    param.status='01,02';
                    param.flag = "0";
                    //没有勾选数据
                    //默认导出满足所选查询条件（包括空查询条件）的所有数据状态为“新建”且报送状态为“未报送”的数据。
                    //导出成功后，系统弹出确认框“导出成功，是否自动标识未报送数据的报送状态为“手工处理”？
                    Joyin.ajax('countExportNum',param,function(dataNum){
                        if(dataNum>100000){
                            Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0016"), function() {
                                exportAndDoManual(param);
                            });
                        }else{
                            exportAndDoManual(param);
                        }
                    });
                });
            }

        }else{
            //有勾选数据
            //先判断勾选的数据有没有包含有删除状态的数据
            for(var i=0;i<expArr.length;i++){
                if(expArr[i].status =="03"){
                    //如果包含有删除状态的数据，给出提示并返回
                    Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0010"));
                    return;
                }
            }
            //不包含有删除状态的数据
            var showTip=false;
            var doManualTip=false;
            for(var i=0;i<expArr.length;i++){
                if(expArr[i].submitStatus=="01"){
                    doManualTip=true;
                }
            }
            for(var i=0;i<expArr.length;i++){
                if(expArr[i].status =="02"||expArr[i].submitStatus!="01"){
                    //若勾选数据中含有数据状态为“修改”或报送状态不为“未报送”的数据时
                    //赋值showTip为true，给出提示框
                    showTip=true;
                    break;
                }

            }
            //根据提示状态判断
            if(showTip){
                //给出提示框【导出数据包含修改或已报送数据，请确认是否导出！】
                Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0011"), function() {
                    //点击“是”导出数据并且手工处理，否则重新选择数据
                    exportZZ(doManualTip,expArr);
                });
            }else{
                //勾选的数据正常，不需要给出提示，直接导出数据并且手工处理
                exportZZ(doManualTip,expArr);
            }
        }

    }
    function exportZZ(flag,expArr){
        //导出中债excel并且调用手工处理
        // var expArr=table.getAllCheckedRows();
        $.each(expArr, function(i, v) {
            v.sendStatus=v.sendStatusCode;
            v.status=v.statusCode;
            v.ccy=v.ccyCode;
        });
        var param = {};
        var keyList = [];
        param.status='01,02';
        param.flag = "0";
        param.keyList = JSON.stringify(expArr);
        param.expKeys = keyList.join(',')
        Joyin.exportExcel('export', param);
        if(flag){
            Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0012"), function() {
                Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
                    layui.layer.open({type:3});
                    Joyin.ajax('doManual', {flag : '02',keyList: JSON.stringify(expArr)}, function() {
                        table.load();
                        layui.layer.closeAll();
                    });
                });
            });
        }
    }

    function exportAndDoManual(param){

        // 添加后台批处理导出逻辑 zhangyf 20200113 start
        Joyin.ajaxJson('checkExport', param, function(result) {
            if(result.data){
                Joyin.confirm('是否自动标识未报送数据的报送状态为“手工处理”？', function() {
                    Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
//		                layui.layer.open({type:3});
                        Joyin.ajax('batchExportAndDoManual',
                            {key: JSON.stringify(param),
                                busiConfirmDate: $('#holdDate').startDate()+' - '+$('#holdDate').endDate(),// 业务确认日期
                                selectedBatchGroup: '3075,3067',// 批处理id
                                selectedProductGroup: '',
                                startDate: new Date(),
                                endDate: new Date(),
                                batchGroupType: '1',
                                prodGroupType: '0',
                                detailName: '投资者明细信息登记-导出中债excel文件'},
                            function(result2) {
                                Joyin.alert(JoyinUtil.getMessage("REPORT_MANUAL_0009"));
                                table.load();
                            }
                        );
                    });
                    return ;
                }, null,function(){
                    // 后台批处理导出
                    Joyin.ajax('batchExport',
                        {key: JSON.stringify(param),
                            busiConfirmDate: $('#holdDate').startDate()+' - '+$('#holdDate').endDate(),// 业务确认日期
                            selectedBatchGroup: '3075',// 批处理id
                            selectedProductGroup: '',
                            startDate: new Date(),
                            endDate: new Date(),
                            batchGroupType: '1',
                            prodGroupType: '0',
                            detailName: '投资者明细信息登记-导出中债excel文件'},
                        function(result2) {
                            if(result2.data){
                                doManual("导出excel文件批处理启动成功。是否自动标识未报送数据的报送状态为“手工处理”？",param);//原手工处理逻辑，改造提示消息
                            }
                        }
                    );
                });
            }else{
                // Joyin.exportExcel('export', param);//原导出逻辑
                // doManual(JoyinUtil.getMessage("REPORT_MANUAL_0012"),param);//原手工处理逻辑
                Joyin.ajax('searchInfo',
                    param,
                    function (result) {
                        if (result && result.length > 0){
                            exportZZ(true,result);
                        } else {
                            Joyin.alert(JoyinUtil.getMessage("无可导出数据!"));
                        }
                    });
            }
        });
        // 添加后台批处理导出逻辑 zhangyf 20200113 end


    }

    function doManual(message,param){
        Joyin.confirm(message, function() {
            Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
//                layui.layer.open({type:3});
                Joyin.ajax('doManual', {flag:"01",key: JSON.stringify(param),selectedBatchGroup: '3067',
                    selectedProductGroup: '',
                    startDate: new Date(),
                    endDate: new Date(),
                    batchGroupType: '1',
                    prodGroupType: '0',
                    detailName: 'SGCL'}, function() {
                    table.load();
//                    layui.layer.closeAll();
                });
            });
        });
    }

    //数据检查
    function dateCheckMethod(){
        var checkArr=table.getAllCheckedRows();
        var param = {};
        if(checkArr.length === 0) {
            var sendStatus = $('#sendStatus').val();
            var status = $('#status').val();
            //业务确认日-业务确认日期不能为空。
            var holdDateStart=$('#holdDate').startDate();
//            if(sendStatus!='01'){
            if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '01')) {
                Joyin.error("请选择筛选条件 报送状态为未报送，数据状态为新建和修改 再进行数据检查！");
                return;
            }
//            else if(status && status!='03'){
//            else if(status && status.includes('03')){
            else if(status && status.indexOf('03') > -1){
                Joyin.error(JoyinUtil.getMessage("REPORT_EXPORT_0003"));
                return;
            }else if(holdDateStart.length == 0 ){
                Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0018"));
                return;
            }
            param = getSearchParams();
            if(!status){
                param.status = '01,02';
            }
            param.isCheck = true;
            Joyin.ajax('checkConfirmMsgForAllCheck', null, function() {
                Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0015"), function() {
                    Joyin.ajax('doCheck', {
                        flag : '01',
                        selectedBatchGroup: '3068',
                        selectedProductGroup: '',
                        startDate: param.holdDateStart,
                        endDate: param.holdDateEnd,
                        batchGroupType: '1',
                        prodGroupType: '0',
                        detailName: 'MXSJJC'
                    }, function() {
                        table.load();
                    });
                });
            },function() {
                $('iframe').contents().find("#loading").hide();
            });
//            Joyin.ajax('sendBeforeCheck',param, function(result){
//                window.checkResult = result;
//                Joyin.checkData('<h4 class="modal-title" id="myModalLabel">投资者明细信息数据检查 </h4>', 'sendBeforeCheck', getSearchParams());
//            })
            return ;
        }
        var errorStatus;
        $.each(checkArr,function(i,v){
            if(v.submitStatus !="01"){
                Joyin.error(JoyinUtil.getMessage("REPORT_EXPORT_0002"));
                errorStatus = true;
                return;
            }
//            else if(v.statusCode && v.statusCode.includes('03')){
            else if(v.status && v.status.indexOf('03') > -1){
                Joyin.error(JoyinUtil.getMessage("REPORT_EXPORT_0004"));
                errorStatus = true;
                return;
            }
        });
        if(errorStatus) return;
        param.keyList = JSON.stringify(checkArr);
        param.isCheck = true;
        Joyin.ajax('sendBeforeCheck',param, function(result){
            window.checkResult = result;
            Joyin.checkData('<h4 class="modal-title" id="myModalLabel">投资者明细信息数据检查 </h4>', 'sendBeforeCheck', param);
        })

    }
    $('#EquityBusiInfoRegist001_btn_checkData').click(dateCheckMethod);

    /**
     * 报送
     */
    $('#EquityBusiInfoRegist001_btn_submit').click(function () {
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
            Joyin.ajax('submitBeforeCheck',getSearchParams(), function(result) {
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
                    Joyin.checkData('<h4 class="modal-title" id="myModalLabel">投资者明细信息数据检查 </h4>', 'submitBeforeCheck', getSearchParams());
                }
            });
            return ;
        }
        for(var i=0;i<rows.length;i++){
            if(rows[i].submitStatus !="01"){
                Joyin.error("只能报送报送状态为未报送的数据");
                return;
            }
            else if(rows[i].status && rows[i].status.indexOf('03') > -1){
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行报送！");
                return;
            }
        }
        $.each(rows, function(i, v) {
            v.sendStatus=v.submitStatus;
            //v.status=v.statusCode;
            //v.ccy=v.ccyCode;
        });
        Joyin.ajax('submitBeforeCheck', {keyList: JSON.stringify(rows)}, function(result) {
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
                Joyin.checkData('<h4 class="modal-title" id="myModalLabel">投资者明细信息数据检查 </h4>', 'submitBeforeCheck', {keyList: JSON.stringify(rows)});
            }
        });
    });

    /**
     * 重新报送
     */
    $('#EquityBusiInfoRegist001_btn_submitAgain').click(function() {
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
        for (var i = 0; i < submitArr.length; i++) {
            var submitFeedbackStatus = submitArr[i].submitFeedbackStatus;
            var submitStatus = submitArr[i].submitStatus;
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
            v.sendStatus=v.submitStatus;
            //v.status=v.statusCode;
            //v.ccy=v.ccyCode;
        });

        Joyin.confirm("请确认是否要二次报送？",function(){
            layui.layer.open({type:3});
            Joyin.ajaxJson('submitCbAgain', {keyList: JSON.stringify(submitArr)}, function() {
                table.load();
                layui.layer.closeAll();
            });
        });
    });

    /**
     * 手工处理
     */
    $('#EquityBusiInfoRegist001_btn_manual').click(function(){
        var rows = table.getAllCheckedRows();
        if(rows.length === 0) {
//            return Joyin.error(JoyinUtil.getMessage("REPORT_COMMON_E_0017"));
            //add by liubin
            var sendStatus = $("#sendStatus").val();
            var submitFeedbackStatus = $("#submitFeedbackStatus").val();
            var holdDateStart=$('#holdDate').startDate();
            //业务确认日
            var holdDateEnd=$('#holdDate').endDate();
            if(!((sendStatus && sendStatus.length == 1 &&sendStatus[0] == '01')
                || (sendStatus && sendStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1
                    ||submitFeedbackStatus.indexOf('04')> -1)))||holdDateStart.length == 0){
                Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0002"));
                return;
            }else if(holdDateStart.length == 0 ){
                Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0017"));
                return;
            }
            var paramObj = getSearchParams();
            Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
                Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0001"), function() {
                    layui.layer.open({type:3});
                    Joyin.ajax('doManual', {
                        flag : '01',
                        selectedBatchGroup: '3067',
                        selectedProductGroup: '',
                        startDate: paramObj.holdDateStart,
                        endDate: paramObj.holdDateEnd,
                        batchGroupType: '1',
                        prodGroupType: '0',
                        detailName: 'SGCL'
                    }, function() {
                        table.load();
                        layui.layer.closeAll();
                    });
                });
            },function() {
                $('iframe').contents().find("#loading").hide();
            });
        }else{
            for(var i=0;i<rows.length;i++){
//              if(rows[i].sendStatusCode !="01"){
                if (!(rows[i].submitStatus == '01'
                    || (rows[i].submitStatus == '02' && (rows[i].submitFeedbackStatus == '02' || rows[i].submitFeedbackStatus == '04')))) {
                    Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0003"));
                    return;
                }
            }
            $.each(rows, function(i, v) {
                v.sendStatus=v.sendStatusCode;
                v.status=v.statusCode;
                v.ccy=v.ccyCode;
            });
            Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0004"), function() {
                layui.layer.open({type:3});
                Joyin.ajax('doManual', {flag : '02',keyList: JSON.stringify(rows)}, function() {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        }

    });

    /**
     * 取消手工处理
     */
    $('#EquityBusiInfoRegist001_btn_cancelManual').click(function() {
        var manArr=table.getAllCheckedRows();
        if(!manArr || manArr.length ==0){
            return Joyin.error(JoyinUtil.getMessage("REPORT_COMMON_E_0017"));
            var sendStatus = $("#sendStatus").val();
            if(!(sendStatus && sendStatus.length==1 && sendStatus[0] == '03')){
                Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0005"));
                return;
            }
            var paramObj = getSearchParams();
            paramObj.flag = '01';
            Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0006"), function() {
                layui.layer.open({type:3});
                Joyin.ajaxJson('cancelMannul', paramObj, function() {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        }else{
            for(var i=0;i<manArr.length;i++){
                if(manArr[i].submitStatus !="03"){
                    Joyin.error(JoyinUtil.getMessage("REPORT_MANUAL_0005"));
                    return;
                }
                manArr[i].sendStatus=manArr[i].sendStatusCode;
                manArr[i].status=manArr[i].statusCode;
                manArr[i].ccy=manArr[i].ccyCode;
            };
            Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0007"), function() {
                layui.layer.open({type:3});
                Joyin.ajaxJson('cancelMannul', {keyList: JSON.stringify(manArr)}, function(result) {
                    Joyin.processReportAjaxResult(result, function() {
                        // 正常情况下
                        table.load();
                        layui.layer.closeAll();
                    }, function() {
                        // 错误情况下
                        layui.layer.closeAll();
                    });
                });
            });
        }

    });

    function init(){
        // if(!isSend){
        //     $('#EquityBusiInfoRegist001_btn_submit').hide();
        //     $('#EquityBusiInfoRegist001_btn_submitAgain').hide();
        // }
        if(isMain=='01'){
            $('#holdDate').val('');
            setTimeout(function() {
                if($('#sendStatus').val()=='02'||$('#sendStatus').val()=='03'){
                    $('#submitDate').val(searchDataStart+' - '+searchDataEnd);
                }else{
                    $('#submitDeadLine').val(searchDataStart+' - '+searchDataEnd);
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
        daySelectRangeNum=parseInt(daySelectRangeStr);
    };
    init();

    $(function(){
        // 绑定按钮事件
        $('#SvrEquityBusiInfoRegist001_btn_search').click(function() {
            table.load();
        });
    });
}());