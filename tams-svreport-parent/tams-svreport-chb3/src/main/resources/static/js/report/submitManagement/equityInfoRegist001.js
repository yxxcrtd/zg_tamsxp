
/**
 * Created by Administrator on 2017/8/15 0015.
 */
// By zhengyan
$(function(){
    //跳转的页码
    var pageCount = 1;
    var getPageParams = function() {
        var submitStatus=$('#submitStatus').val();
        if(submitStatus){
            submitStatus=submitStatus.join();
        }
        var holderType = $('#holderType').val();
        if (holderType) {
            holderType = holderType.join();
        }
        var status=$('#status').val();
        if(status){
            status=status.join();
        }
        var submitFeedbackStatus=$('#submitFeedbackStatus').val();
        if(submitFeedbackStatus){
            submitFeedbackStatus=submitFeedbackStatus.join();
        }
        var personalIdentifyType=$('#personalIdentifyType').val();
        if(personalIdentifyType){
            personalIdentifyType=personalIdentifyType.join();
        }
        var institutionIdentifyType=$('#institutionIdentifyType').val();
        if(institutionIdentifyType){
            institutionIdentifyType=institutionIdentifyType.join();
        }
        var orgCode=$('#orgCode').val();
        if(orgCode){
            orgCode=orgCode.join();
        }
        return {
            // 登记银行代码
            bankCode : $('#bankCode').val(),
            // 识别标识
            indentifyMark : $('#indentifyMark').val(),
            // 原识别标识
            orgIndentifyMark : $('#orgIndentifyMark').val(),
            // 投资者名称
            holderName : $('#holderName').val(),
            //投资者类别
            holderType :holderType,
            // 个人证件类别
            personalIdentifyType : personalIdentifyType,
            // 机构证件类别
            institutionIdentifyType : institutionIdentifyType,
            // 证件号码
            identifyNumber : $('#identifyNumber').val(),
            // 报送状态
            submitFeedbackStatus : submitFeedbackStatus,
            // 反馈状态
            submitStatus : submitStatus,
            // 数据状态
            status : status,
            //报送日开始
            submitDate : $('#submitDate').startDate(),
            //报送日结束
            submitDateEnd : $('#submitDate').endDate(),
            //数据日期开始日
            updateTime : $('#updateTime').startDate(),
            //数据日期截止日
            updateTimeEnd : $('#updateTime').endDate(),
            //报送截止日开始
            submitDeadLineStart : $('#submitDeadLine').startDate(),
            //报送截止日结束
            submitDeadLineEnd : $('#submitDeadLine').endDate(),
            //机构代码
            orgCode: orgCode
        };
    };
    var table = $('#equityInfoRegist').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        showDetail: true,//双击详情对比画面
        done :function(){
            if(indentifyMark){
                table.resize($(document).height() - $('.container').height() + table.getHeight());
                var addTableData = table.getAllRows();
                //标志是否在当前页选中数据
                var isChecked = false;
                if(addTableData){
                    $(addTableData).each(function(i,v){
                        if(indentifyMark===v.indentifyMark && v.submitStatusName==='未报送'){
                            table.checkRow(v.LAY_TABLE_INDEX);
                            dateCheckMethod();
                            isChecked = true;
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
        cols: [
            { title: "数据状态", field: 'statusName',width:100,sortField:'status'},
            { field: 'status',visible:false},
            { title: "数据日期", field: 'updateTimeStr',width:150,sortField:'updateTime'},
            { title: "报送状态", field: 'submitStatusName',width:100 ,sortField:'submitStatus'},           
            { title: "报送日期", field: 'submitTime',type: 'datetime',width:150,sortField:'submitTime'},
            { title: "报送截止日", field: 'chbSubmitDeadline', type: 'date',width:150,sortField:'chbSubmitDeadline'},
            { title: "反馈状态", field: 'submitFeedbackStatus',width:100,visible: false},
            { title: "反馈状态", field: 'submitFeedbackStatusStr',width:100,sortField:'submitFeedbackStatus'/*界面查询性能优化,
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
                        return "<span class='debt-return'>审核文件下载失败</span>";
                    }
                }*/
            },
            { title: "数据类型", field: 'type' ,sortField:'type',width:100},
            { field: 'submitStatus',visible:false},
            { title: "报送流水号", field: 'sumitSeq',width:150},
            { title: "登记银行代码", field: 'bankCode',width:160 },
            { title: "机构代码",field:'orgCode',width: 160},
            { title: "识别标识", field: 'indentifyMark',width:100 },
            { title: "原识别标识", field: 'orgIndentifyMark',width:120 },
            { title: "该投资者是否属于本行", field: 'isBelongBank',sortField:'isBelongBank',width:180},
            { title: "投资者所属银行名称", field: 'theBankName',width:168 },
            { title: "投资者所属银行代码", field: 'theBankCode',width:168},
            { title: "投资者境内外标识", field: 'holderMark',sortField:'holderMark',width:152},
            { title: "投资者所属国家或地区", field: 'holderRegion',sortField:'holderRegion',width:180 },
            { title: "投资者类别", field: 'holderType',sortField:'holderType',width:114},
            { title: "证件类别", field: 'identifyType',sortField:'identifyType',width: 200},
            { title: "其他证件名称", field: 'otherIdentifyName',width:126},
            { title: "证件号码", field: 'identifyNumber',width: 172},
            { title: "SPV资金托管账户及开户行", field: 'spvCapAccBank',sortField:'spvCapAccBank',width: 210},
            { title: "其他资金托管账户及开户行", field: 'otherCapAccBank',width: 210},
            /*{ title: "SPV登记编码", field: 'spvRegistCode',width: 128 },*/
            { title: "投资者名称", field: 'holderName', width: 109},
            { title: "性别", field: 'holderSex',width:75},
            { title: "风险偏好", field: 'holderRiskPrefer',width:100/*界面查询性能优化,
                render: function (data, type, row, meta) {
                    if(!row.holderRiskPrefer){
                        return "";
                    }
                    if(row.holderRiskPrefer=="01"){
                        return "<span class='text-Conervative'>保守型</span>";
                    }
                    if(row.holderRiskPrefer=="02"){
                        return "<span class='text-steady'>稳健型</span>";
                    }
                    if(row.holderRiskPrefer=="03"){
                        return "<span class='text-balance'>平衡型</span>";
                    }
                    if(row.holderRiskPrefer=="04"){
                        return "<span class='text-grow'>成长型</span>";
                    }
                    if(row.holderRiskPrefer=="05"){
                        return "<span class='text-progress'>进取型</span>";
                    }
                }*/
            },
            {title:"手机号码", field: 'holderCellphone', width: 120},
            {title:"固定电话", field: 'holderTelephone', width: 120},
            {title:"电子邮箱", field: 'holderEmail', width: 150 },
            {title:"备注", field: 'remark', sortable:false, width:160}
        ]
    });

    // 查询按钮点击事件
    $('#EquityInfoRegist001_btn_search').click(function() {
        /*if($('#searchForm').joyinvalid()) {*/
        table.load();
//        }
    });

    // 导出中债Excel按钮点击事件
    $('#EquityInfoRegist001_btn_chbexport').click(function() {
//        exportMethod("0");
        exportZZExcel();
    });

    // 导出查询结果按钮点击事件
    $('#EquityInfoRegist001_btn_export').click(function() {
    var expArr=table.getAllCheckedRows();
    if(!expArr || expArr.length ==0){
        var param={};
              Joyin.ajax('searchInfo',
              param,
              function (result) {
                if (result && result.length > 0){
                     exportAll(result);
                    }
              });
    }
    else{
        exportMethod("1");
        }
    });

     function exportAll(expArr){
            $.each(expArr, function(i, v) {
                v.sendStatus=v.sendStatusCode;
                v.status=v.statusCode;
                v.ccy=v.ccyCode;
            });
            var param = {};
            var keyList = [];
            $.each(expArr, function(i, row) {
                keyList.push(row.bankCode + '|' + row.indentifyMark + '|' + row.submitStatus + '|' + row.sumitSeq);
            });
            param.flag = "1";
            param.keyList = JSON.stringify(expArr);
            param.expKeys = keyList.join(',')
            Joyin.exportExcel('export', param);
        }

    //导出方法
    var exportMethod = function (flag){
        var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
            param = getPageParams();
            param.flag = flag;
            if(flag=="0" &&(param.status==null||param.status=='')){
                param.status="01,02";
            }
            Joyin.exportExcel("export",param);
        }
        else{
            if(flag=="0")
            {
                for(var i=0;i<expArr.length;i++){
                    if(expArr[i].status=="03"){
                        Joyin.error("只能导出新建或修改状态的数据，请重新选择");
                        return;
                    }
                }
            }
            var keyList = [];
            $.each(expArr, function(i, row) {
                keyList.push(row.bankCode + '|' + row.indentifyMark + '|' + row.submitStatus);
            });
            param.expKeys = keyList.join(',')
            param.flag = flag;
            Joyin.exportExcel("exportSelect",param);
        }

    };


    // 导入按钮点击事件
    $('#EquityInfoRegist001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
            Joyin.uploadFile(function(fileName) {
                Joyin.ajax('importFile', {fileName: fileName});
            },function() {
                table.load();
            });
        });
    });
    //导出删除模板
    $('#EquityInfoRegist001_btn_exportDel').click(function(){
        if(!table.isSearched()) {
            return;
        }
        var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
            param = getPageParams();
            if (param.status==null||param.status==''){
                param.status='03';
            }
            param.flag = "2";
            param.submitStatus='01';
            Joyin.exportExcel("export",param);
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
                keyList.push(row.bankCode + '|' + row.indentifyMark + '|' + row.submitStatus);
            });
            param.expKeys = keyList.join(',')
            param.flag = "2";
            Joyin.exportExcel("exportDel",param);
        }
    });

    //导出删除模板
    $('#SvrEquityInfoRegist001_btn_exportDel').click(function(){
        if(!table.isSearched()) {
            return;
        }
        var expArr=table.getAllCheckedRows();
        var param={};
        if(!expArr || expArr.length ==0){
            param = getPageParams();
            if (param.status==null||param.status==''){
                param.status='03';
            }
            param.flag = "2";
            param.submitStatus='01';
            // Joyin.exportExcel("export",param);
            // 打开删除信息页面
            Joyin.dialog(JoyinUtil.getLabel("SvrExportDeleteExcelConfirm001"),
                basePath + "/chb/common/EquityExportDeleteExcelConfirm001/init",
                "50%", "50%", {pageModel:PAGE_MODEL_NEW,orgPageId:"EquityInfoRegist001",paramKeys:JSON.stringify(param)},function(){
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
                keyList.push(row.bankCode + '|' + row.indentifyMark + '|' + row.submitStatus);
            });
            param.expKeys = keyList.join(',')
            param.flag = "2";
            // Joyin.exportExcel("exportDel",param);
            // 打开删除信息页面
            Joyin.dialog(JoyinUtil.getLabel("SvrExportDeleteExcelConfirm001"),
                basePath + "/chb/common/EquityExportDeleteExcelConfirm001/init",
                "50%", "50%", {pageModel:PAGE_MODEL_NEW,orgPageId:"EquityInfoRegist001",expKeys:param.expKeys},function(){
                    //防止出现查询转圈圈
                    setTimeout(function(){table.reload();},100);
                });
        }
    });




    //数据检查
    $('#EquityInfoRegist001_btn_checkData').click(dateCheckMethod);
    function dateCheckMethod(){
        var checkArr=table.getAllCheckedRows();
        if(checkArr.length === 0) {
            var submitStatus = $('#submitStatus').val();
            var status = $('#status').val();
//            if(submitStatus!='01'){
            if (!(submitStatus && submitStatus.length == 1 && submitStatus[0] == '01')) {
                Joyin.error("请选择筛选条件 报送状态为未报送，数据状态为新建和修改 再进行数据检查！");
                return;
            }
//            else if(status && status.includes('03')){
            else if(status && status.indexOf('03') > -1){
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            var paramObj = getPageParams()
            if(!status){
                paramObj.status = '01,02';
            }
            Joyin.ajax('reportCheck',paramObj, function(result){
                window.checkResult = result;
                Joyin.checkData(dataTitle, checkUrl, getPageParams());
            })
            return ;
        }
        var keyList = [];
        for(var i=0;i<checkArr.length;i++){
            //modify by wangmin 修改取数顺序 start 2018-07-12
//          keyList.push(checkArr[i].submitStatus + '|' + checkArr[i].bankCode + '|' + checkArr[i].indentifyMark );
            keyList.push(checkArr[i].bankCode + '|' + checkArr[i].indentifyMark + '|' + checkArr[i].submitStatus );
            //modify by wangmin 修改取数顺序 end 2018-07-12
            if(checkArr[i].submitStatusName !="未报送"){
                Joyin.error("数据检查只能检查未报送数据，请重新选择！");
                return;
            }
//            else if(checkArr[i].status && checkArr[i].status.includes('03')){
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
    // 手工处理 
    $('#EquityInfoRegist001_btn_manual').click(function() {
        // debugger
        var manArr=table.getAllCheckedRows();
        if(!manArr || manArr.length ==0){
            //modify by liubin 当不选择时默认选择符合查询条件的全部数据
            var submitStatus = $("#submitStatus").val();
            var submitFeedbackStatus = $("#submitFeedbackStatus").val();
            if(!((submitStatus && submitStatus.length == 1 &&submitStatus[0] == '01')
                || (submitStatus && submitStatus.indexOf('02')>-1 && submitFeedbackStatus && (submitFeedbackStatus.indexOf('02')>-1 ||  submitFeedbackStatus.indexOf('04')> -1)))){
                Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理");
                return;
            }
            var paramObj = getPageParams();
            paramObj.flag = "01";//判断是否选中记录
            Joyin.confirm("请确认是否要手工处理全部未报送记录？", function() {
                //add by liubin 页面添加滚动条

                Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
                    layui.layer.open({type:3});
                    Joyin.ajax('update', {
                        flag:'01',
                        key: JSON.stringify(paramObj),
                        selectedBatchGroup: '3073',
                        selectedProductGroup: '',
                        startDate: new Date(),
                        endDate: new Date(),
                        batchGroupType: '1',
                        prodGroupType: '0',
                        detailName: 'SFSGCL'
                    }, function() {
                        table.load();
                        layui.layer.closeAll();
                    });
                });

//        		layui.layer.open({type:3});
//	            Joyin.ajaxJson('update', paramObj, function() {
//	                table.load();
//	                layui.layer.closeAll();
//	            });
            });
//            Joyin.error(JoyinUtil.getMessage('REPORT_COMMON_E_0017'));
//            return;
        }else{
            var keyList = [];
            for(var i=0;i<manArr.length;i++){
                keyList.push(manArr[i].bankCode + '|' + manArr[i].indentifyMark + '|' + manArr[i].submitStatus+ '|' + manArr[i].sumitSeq);
                //            if(manArr[i].submitStatusName !="未报送"){
                if (!(manArr[i].submitStatus == '01'
                    || (manArr[i].submitStatus == '02' && (manArr[i].submitFeedbackStatus == '02' || manArr[i].submitFeedbackStatus == '04')))) {
                    //                Joyin.error("只能选择未报送的数据，请重新选择！");
                    Joyin.error("只能选择报送状态为未报送的数据，或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理，请重新选择！");
                    return;
                }
            };
            Joyin.confirm("请确认是否要手工处理选中记录？", function() {
                layui.layer.open({type:3});
                Joyin.ajax('update', {updateKeys:keyList.join(',')}, function() {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        }
    });
    //liaochunyu add 2017-12-02 start
    // 取消手工处理
    $('#EquityInfoRegist001_btn_cancelManual').click(function() {
        var manArr=table.getAllCheckedRows();
        if(!manArr || manArr.length ==0){
            //add by liubin
            var submitStatus = $("#submitStatus").val();
            if(!(submitStatus && submitStatus.length==1 && submitStatus[0] == '03')){
                Joyin.error("只能选择手工处理的数据，请重新选择！");
                return;
            }
            var paramObj = getPageParams();
            paramObj.flag = "01";//判断是否选中记录
            Joyin.confirm("请确认是否取消手工处理全部未报送记录？", function() {
                layui.layer.open({type:3});
                Joyin.ajaxJson('cancelMannul', paramObj, function() {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        }else{
            var keyList = [];
            for(var i=0;i<manArr.length;i++){
                keyList.push((manArr[i].sumitSeq =='' ? null : manArr[i].sumitSeq) +'|'+ manArr[i].bankCode + '|' + manArr[i].indentifyMark);
                if(manArr[i].submitStatusName !="手工处理"){
                    Joyin.error("只能选择手工处理的数据，请重新选择！");
                    return;
                }
            };
            Joyin.confirm("请确认是否要取消手工处理选中记录？", function() {
                layui.layer.open({type:3});
                Joyin.ajaxJson('cancelMannul', {updateKeys:keyList.join(',')}, function() {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        }

    });
    //liaochunyu add 2017-12-02 end
    //报送
    $('#EquityInfoRegist001_btn_submit').click(function() {
        var submitArr=table.getAllCheckedRows();
        if(submitArr.length === 0) {
            var paramObj = getPageParams();
            var submitStatus = $('#submitStatus').val();
            var status = $('#status').val();
//            if(submitStatus!='01'){
            if (!(submitStatus && submitStatus.length == 1 && submitStatus[0] == '01')) {
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行报送！");
                return;
            }
//            else if(status && status.includes('03')){
            else if(status && status.indexOf('03') > -1){
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            if(!status){
                paramObj.status = '01,02';
            }
            Joyin.ajax('sendBeforeCheck',paramObj, function(result){
                if(!result || result.length ==0){
                    Joyin.confirm("按照已选择当前查询条件报送,是否确认！",function(){
                        layui.layer.open({type:3});
                        Joyin.ajaxJson('submitChb', getPageParams(), function() {
                            table.load();
                            layui.layer.closeAll();
                        });
                    });
                }
                else{
                    window.checkResult = result;
                    Joyin.checkData(dataTitle, checkUrl, getPageParams());
                }
            })
            return ;
        }
        var keyList = [];
        for(var i=0;i<submitArr.length;i++){
            keyList.push(submitArr[i].bankCode + '|' + submitArr[i].indentifyMark + '|' + submitArr[i].submitStatus+ '|' + submitArr[i].sumitSeq);
            if(submitArr[i].submitStatus !="01"){
                Joyin.error("选择的报送数据中存在已报送数据，请重新选择！");
                return;
            }
            else if(submitArr[i].status =="03" && submitArr[i].submitStatus =="01"){
                Joyin.error("选择的报送数据中存在删除状态的数据，请重新选择！");
                return;
            }
        };

        Joyin.ajax('sendBeforeCheck',{checkKeys:keyList.join(',')}, function(result){
            if(!result || result.length ==0){
                Joyin.confirm("请确认是否要报送选中记录？", function() {
                    layui.layer.open({type:3});
                    Joyin.ajaxJson('submitChb', {submitKeys:keyList.join(',')}, function() {
                        table.load();
                        layui.layer.closeAll();
                    });
                });
            }
            else{
                window.checkResult = result;
                Joyin.checkData(dataTitle, checkUrl, {checkKeys: keyList.join(',')});
            }
        })
//        Joyin.confirm("请确认是否要报送选中记录？", function() {
//            Joyin.ajaxJson('submitChb', {submitKeys:keyList.join(',')}, function() {
//                table.load();
//            });
//        });
    });

    // add by etliu 20180824 start
    // 二次报送
    $('#EquityInfoRegist001_btn_submitAgain').click(function() {
        var paramObj = getPageParams();

        var submitArr = table.getAllCheckedRows();
        if(submitArr.length === 0) {
            var submitStatus = $('#submitStatus').val();
            var status = $('#status').val();
            var submitFeedbackStatus = $('#submitFeedbackStatus').val();
            if (!(submitStatus && submitStatus.length == 1 && submitStatus[0] == '02')) {
                Joyin.error("只能对报送状态为接口报送且反馈状态为发送失败或中债退回的数据进行重新报送！");
                return;
            }
//            if(status && status.includes('03')){
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
                Joyin.error("只能对报送状态为接口报送且反馈状态为发送失败或中债退回的数据进行重新报送!");
                return;
            }
            keyList.push(submitArr[i].sumitSeq);
        };

        paramObj.keyList = keyList.join(',');

        Joyin.confirm("请确认是否要二次报送？",function(){
            layui.layer.open({type:3});
            Joyin.ajaxJson('submitCbAgain', paramObj, function() {
                table.load();
                layui.layer.closeAll();
            });
        });
    });
    // add by etliu 20180824 end

    var init= function(){
        $('#updateTime').val('');
        $('#submitDate').val('');

        if(isMain=='01'){
            setTimeout(function() {
                if($('#submitStatus').val()=='02'||$('#submitStatus').val()=='03'){
                    $('#submitDate').val(searchDataStart+' - '+searchDataEnd);
                }else{
                    $('#submitDeadLine').val(searchDataStart+' - '+searchDataEnd);
                }
                table.load();
            }, 500);
        }

        //报送前检查调用数据检查
        if(indentifyMark){
            setTimeout(function() {
                table.load();
            },500)
        }
    };
    init();
    
    
    //导出中债excel
    var exportZZExcel = function(flag) {
    	var expArr=table.getAllCheckedRows();
        
        //先判断有没有勾选数据
        if(!expArr || expArr.length ==0){
            	var param = getPageParams();
                param.status='01,02';
                param.flag = "0";
                param.submitStatus='01';
            //add by zhoubin 20220422 start
            Joyin.ajax('countExportNum',param,function(dataNum){
                if (dataNum==0) {
                    Joyin.error("只能导出报送状态为“未报送”的数据，请重新选择");
                    return;
                }
               });
            //add by zhoubin 20220422 end
            	//没有勾选数据
            	//默认导出满足所选查询条件（包括空查询条件）的所有数据状态为“新建”且报送状态为“未报送”的数据。
            	//导出成功后，系统弹出确认框“导出成功，是否自动标识未报送数据的报送状态为“手工处理”？
//                Joyin.ajax('countExportNum',param,function(dataNum){
//                    if(dataNum>500){
//                         Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0016"), function() {
//                        	 exportAndDoManual(param);
//                         });
//                    }else{
//                    	exportAndDoManual(param);
//                    }
//                });
//        		Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0020"), function() {
//                 	var param = getPageParams();
//                     param.status='01,02';
//                     param.flag = "0";
                	//没有勾选数据
                	//默认导出满足所选查询条件（包括空查询条件）的所有数据状态为“新建”且报送状态为“未报送”的数据。
                	//导出成功后，系统弹出确认框“导出成功，是否自动标识未报送数据的报送状态为“手工处理”？
                    // Joyin.ajax('countExportNum',param,function(dataNum){
                    //     if(dataNum>100000){
                    //          Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0016"), function() {
                    //         	 exportAndDoManual(param);
                    //          });
                    //     }else{
                    //     	Joyin.confirm("是否确认导出中债Excel?", function() {
                    //     	     exportAndDoManual(param);
                    //     	});
                    //     }
                    // });
//                });
            exportAndDoManual(param);
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
        $.each(expArr, function(i, row) {
            keyList.push(row.bankCode + '|' + row.indentifyMark + '|' + row.submitStatus + '|' + row.sumitSeq);
        });
        param.status='01,02';
        param.flag = "0";
        param.keyList = JSON.stringify(expArr);
        param.expKeys = keyList.join(',')
        Joyin.exportExcel('export', param);
       if(flag){
	        Joyin.confirm(JoyinUtil.getMessage("REPORT_MANUAL_0012"), function() {
	              Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
                     layui.layer.open({type:3});
                   Joyin.ajax('update', {flag : '02',updateKeys:keyList.join(',')}, function() {
                       table.load();
                       layui.layer.closeAll();
                   });
                 });
	          });
       }
    }
    
    function exportAndDoManual(param){
    	// 添加后台批处理导出逻辑 zhangyf 20200113 start
        Joyin.ajaxJson('checkExport', param, function(result){
            if(result.data){
                Joyin.confirm(JoyinUtil.getMessage("是否将导出数据的报送状态改为“手工处理”？"), function() {
                    // 后台批处理导出
                    Joyin.ajax('batchExportAndDoManual',
                        {key: JSON.stringify(param),
                            //busiConfirmDate: $('#holdDate').startDate()+' - '+$('#holdDate').endDate(),// 业务确认日期
                            selectedBatchGroup: '3074,3073',// 批处理id
                            selectedProductGroup: '',
                            startDate: new Date(),
                            endDate: new Date(),
                            batchGroupType: '1',
                            prodGroupType: '0',
                            detailName: '投资者身份信息登记-导出中债excel文件'},
                        function(result2) {
                            if(result2.data){
                                Joyin.alert(JoyinUtil.getMessage("REPORT_MANUAL_0009"));
                            }else{
                                Joyin.alert(result2.message);
                            }
                            table.load();
                        }
                    );
                },null,function(){
                    // 后台批处理导出
                    Joyin.ajax('batchExport',
                        {key: JSON.stringify(param),
                            //busiConfirmDate: $('#holdDate').startDate()+' - '+$('#holdDate').endDate(),// 业务确认日期
                            selectedBatchGroup: '3074',// 批处理id
                            selectedProductGroup: '',
                            startDate: new Date(),
                            endDate: new Date(),
                            batchGroupType: '1',
                            prodGroupType: '0',
                            detailName: '投资者身份信息登记-导出中债excel文件'},
                        function(result2) {
                            if(result2.data){
                                Joyin.alert(JoyinUtil.getMessage("REPORT_MANUAL_0009"));
                            }else{
                                Joyin.alert(result2.message);
                            }
                            table.load();
                        }
                    );
                });
            } else {
                // 无勾选导出，数量小于设置值走勾选逻辑
                Joyin.ajax('searchInfo',
                    param,
                    function (result) {
                            if (result && result.length > 0){
                                exportZZ(true,result);
                            }
                    });
            }
        });
    }
    
    function doManual(message,param){
    	Joyin.confirm(message, function() {
    		Joyin.ajax('checkConfirmMsgForAllManual', null, function() {
    			layui.layer.open({type:3});
	            Joyin.ajax('update', {
	            	flag:'01',
                	key: JSON.stringify(param),
                	selectedBatchGroup: '3073',
    				selectedProductGroup: '',
    				startDate: new Date(),
    				endDate: new Date(),
    				batchGroupType: '1',
    				prodGroupType: '0',
    				detailName: 'SFSGCL'
	            }, function() {
	                table.load();
	                layui.layer.closeAll();
	            });
    		});
            }); 
    }

});