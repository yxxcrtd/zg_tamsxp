$(function () {
    //跳转的页码
    var pageCount = 1;
    /**
     * 获取查询参数
     */
    var getSearchParams = function () {
        return {
            //报送截止日
            submitDeadLineStart: $('#submitDeadLine').startDate(),
            submitDeadLineEnd: $('#submitDeadLine').endDate(),
            //报送状态
            sendStatus: $('#sendStatus').val() == null ? null : $('#sendStatus').val().join(","),
            status: $('#status').val() == null ? null : $('#status').val().join(','),
            //报表类型
            reporttypes: $('#reporttype').val() == null ? null : $('#reporttype').val().join(","),
            //报送期
            submitDateStart: $('#submitDate').startDate(),
            submitDateEnd: $('#submitDate').endDate(),
            submitTimeStart: $('#submitTime').startDate(),
            submitTimeEnd: $('#submitTime').endDate(),
            statusDateStart: $('#statusDate').startDate(),
            statusDateEnd: $('#statusDate').endDate(),
            //反馈状态
            submitFeedbackStatus: $('#submitFeedbackStatus').val() == null ? null : $('#submitFeedbackStatus').val().join(","),
            //结束日（统计日期）
            enddate: $('#enddate').val()
        }
    };

    /**
     * 定义数据表格
     */
    var table = $("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function () {
                return getSearchParams();
            }
        },
//        showDetail: true,//双击详情对比画面
//        done : function(){
//            if(validKey){
//                table.resize($(document).height() - $('.container').height() + table.getHeight());
//              //标志是否在当前页选中数据
//                var isChecked = false;
//                var addTableData = table.getAllRows();
//                if(addTableData){
//                    $(addTableData).each(function(i,v){
//                        if(validKey.prdRegistCode===v.prdRegistCode && validKey.indentifyMark===v.indentifyMark
//                                && (Date.ofDate(validKey.holdDate).getTime()) === (Date.ofDate(v.holdDate).getTime()) 
//                                && validKey.ccy===v.ccyCode 
//                                && validKey.registBankCode===v.registBankCode
//                                && v.sendStatusCode==='01'){
//                            table.checkRow(v.LAY_TABLE_INDEX);
//                            isChecked = true;
//                            dateCheckMethod();
//                            return false;
//                        }
//                    })
//                }
//                if(!isChecked){
//                    pageCount++;
//                    table.reload({page:{curr:pageCount}});
//                }
//            }
//        
//        },
        cols: [
            {title: "数据状态", field: 'statusStr', sortable: false},
            {title: "数据日期", field: 'statusDate', width: 200, type: 'datetime', sortable: false},
            {title: "报送状态", field: 'sendStatusStr', width: 100, sortable: false},
            {title: "报送日期", field: 'submitTime', width: 200, type: 'datetime', sortable: false},
            {title: "报送截止日", field: 'chbSubmitDeadline', width: 200, type: 'date', sortable: false},
            {title: "反馈状态", field: 'submitFeedbackStatusStr', width: 100, sortable: false},
            {title: '报表名称', field: 'reportname', width: 350, sortable: false},
            {title: '报告期', field: 'termname', width: 150, sortable: false},
            {title: '机构名称', field: 'deptName', width: 150, sortable: false},
            {field: 'sendStatus', visible: false},
            {field: 'status', visible: false},
            {field: 'reportuuid', visible: false},
            {field: 'termid', visible: false},
            {
                title: '操作',
                field: 'operation',
                type: 'action',
                width: 150,
                buttons: [
                    {
                        text: '&#xe615;',
                        clas: 'batch',
                        title: '预览'
                    }]
            }
            /* {field: 'operation', title: 'SysCommon.operation', type: 'action',width:150,
                 render: function (data, type, row, meta) {
                         if(purviewElements.indexOf('_batch')!=-1){
                             return '<i class="btn layui-icon batch" title="预览">&#xe615;</i>';
                         }
                     }
                 }*/

        ]
    });


    // 导入按钮点击事件
    $('#ChbMonthReport001_btn_import').click(function () {
        Joyin.confirm("请确认是否要导入数据？", function () {
            Joyin.uploadFile(function (fileName) {
                Joyin.ajax('importFile', {fileName: fileName});
            }, function () {
                table.load();
            });
        });
    });

    //导出所有查询结果
    $('#ChbMonthReport001_btn_export').click(function () {
        exportExcel("1");
    });

    //导出中债Excel
    $('#ChbMonthReport001_btn_chbexport').click(function () {
        exportExcel("0");
    });

    //导出删除模板
    $('#ChbMonthReport001_btn_exportDel').click(function () {
        /*if(!table.isSearched()) {
            return;
        }*/
        var expArr = table.getAllCheckedRows();
        var param = {};
        if (!expArr || expArr.length == 0) {
            param = getSearchParams();
            if (param.status == null || param.status == '') {
                param.status = '03';
            }
            param.flag = "2";
            param.sendStatus = '01';
            Joyin.exportExcel("export", param);
        } else {

            for (var i = 0; i < expArr.length; i++) {
                if (expArr[i].status != "删除" || expArr[i].sendStatus != "未报送") {
                    Joyin.error("只能导出删除状态且未报送的数据，请重新选择");
                    return;
                }
            }
            var keyList = [];
            $.each(expArr, function (i, v) {
                v.sendStatus = v.sendStatusCode;
                v.status = v.statusCode;
                v.ccy = v.ccyCode;
            })
            param.keyList = JSON.stringify(expArr);
            param.flag = '2';
            Joyin.exportExcel('export', param);
        }
    });

    /**
     * 导出
     */
    var exportExcel = function (flag) {

        var paramObj = getSearchParams();
//    	 Joyin.ajaxJson('doBatch', paramObj, function() {
//             table.load();
//             layui.layer.closeAll();
//         });
//    	var isCond = true;
//        Joyin.ajaxSync('findPage',paramObj, function(result){
//            var recordNum = result.records;
//            if (recordNum <= 0){
//            	isCond = false;
//            }
//        });
//        if (!isCond){
//    		Joyin.error("请选择导出数据！");
//			return;
//        }
        var expArr = table.getAllCheckedRows();
        var param = {};
        if (!expArr || expArr.length == 0) {
//        	if(expArr.length ==0){
//        		Joyin.error("请选择导出数据！");
//    			return;
//        	}
            param = getSearchParams();
            param.flag = "01";
//            if(flag=="0" &&(param.status==null||param.status=='')){
//            	param.status="01,02";
//            }
            param.status = "01";
            param.sendStatus = "01";
            param.exportflag = 'export';
            var isHaveData = true;
            Joyin.ajaxSync('findPage', param, function (result) {
                if (!result || result.root.length == 0) {
                    isHaveData = false;
                    return;
                }
            })
            Joyin.exportExcel("export", param);
            //ZGBSEI-394  新人行产品基本信息登记页面，统计模板登记页面，产品终止信息登记页面，导出待修复按钮点击后，去掉是否手工处理的提示 LEIPENG 20210827
            isHaveData = false;
            if (isHaveData) {
                Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function () {
                    layui.layer.open({type: 3});
                    Joyin.ajaxJson('doManual', param, function () {
                        table.load();
                        layui.layer.closeAll();
                    });
                });
            }
            Joyin.exportExcel("export",param);
            if (isHaveData){
	            Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function() {
	            	layui.layer.open({type:3});
	                Joyin.ajaxJson('doManual', param, function() {
	                    table.load();
	                    layui.layer.closeAll();
	                });
	            });
            }
        } else {
//        	Joyin.error("请选择导出数据！");
//			return;
//        }
            // 用来标注选择数据中存在修改或者已报送数据，默认为false
            var statuFlag = false;
            if (flag == "0") {
//        		if(expArr.length >1){
//        			Joyin.error("只能选择一条数据导出！");
//        			return;
//        		}
                for (var i = 0; i < expArr.length; i++) {
                    // 含有删除状态数据
                    if (expArr[i].status == "03") {
                        Joyin.error("所选数据中包含数据状态为删除的数据，请重新选择!");
                        return;
                        // 含有修改状态数据或者已报送数据，将标志改为true
                    } else if (expArr[i].status == "02" || expArr[i].sendStatus != "01") {
                        statuFlag = true;
                    }
                }
            }
            var keyList = [];
            param.keyList = JSON.stringify(expArr);
            param.expKeys = keyList.join(',')
            param.flag = "02";
            param.exportflag = 'exportSelected';
            if (statuFlag) {
                Joyin.confirm("导出数据包含修改或已报送数据，请确认是否导出！", function () {
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
    var confirmExport = function (param, expArr) {
        Joyin.exportExcel("export", param);
        //判断所选导出的数据是否已经报送送成功，若报送成功，则无需"手工处理"，只对报送失败的数据进行处理
        var isNotSendData = false;
        for (var j = expArr.length - 1; j >= 0; j--) {
            if (expArr[j].sendStatus == "01" || (expArr[j].sendStatus == '02' && (expArr[j].submitFeedbackStatus == '发送失败' || expArr[j].submitFeedbackStatus == '中债退回'))) {
                isNotSendData = true;//有未报送成功的数据
            }
            if (!(expArr[j].sendStatus == '01' || (expArr[j].sendStatus == '02' && (expArr[j].submitFeedbackStatus == '发送失败' || expArr[j].submitFeedbackStatus == '中债退回')))) {
                expArr.splice(j, 1);
            }
        }

        //ZGBSEI-394  新人行产品基本信息登记页面，统计模板登记页面，产品终止信息登记页面，导出待修复按钮点击后，去掉是否手工处理的提示 LEIPENG 20210827
        isNotSendData = false;
        if (isNotSendData) {
            Joyin.confirm("导出成功，是否自动标识未报送数据的报送状态为“手工处理”？", function () {
                layui.layer.open({type: 3});
                Joyin.ajaxJson('doManual', {keyList: JSON.stringify(expArr)}, function () {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        }

    };

    //数据检查
    function dateCheckMethod() {
        var checkArr = table.getAllCheckedRows();
        if (checkArr.length === 0) {
            var sendStatus = $('#sendStatus').val();
            var status = $('#status').val();
            if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '01')) {
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行数据检查！");
                return;
            } else if (status && status.indexOf('03') > -1) {
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            var paramObj = getSearchParams()
            if (!status) {
                paramObj.status = '01,02';
            }
            Joyin.ajax('reportCheck', paramObj, function (result) {
                window.checkResult = result;
                Joyin.checkData(dataTitle, checkUrl, getSearchParams());
            })
            return;
        }
        var keyList = [];
        for (var i = 0; i < checkArr.length; i++) {
            keyList.push(checkArr[i].termid);
            if (checkArr[i].sendStatus != "01") {
                Joyin.error("数据检查只能检查未报送数据，请重新选择！");
                return;
            } else if (checkArr[i].status && checkArr[i].status.indexOf('03') > -1) {
                Joyin.error("选择的报送数据中存在删除状态的数据，请重新选择！");
                return;
            }
        }
        ;
        Joyin.ajax('reportCheck', {checkKeys: keyList.join(',')}, function (result) {
            window.checkResult = result;
            Joyin.checkData(dataTitle, checkUrl, {checkKeys: keyList.join(',')});
        })

    }

    $('#ChbMonthReport001_btn_checkData').click(dateCheckMethod);

    /**
     * 报送
     */
    $('#ChbMonthReport001_btn_submit').click(function () {
        var rows = table.getAllCheckedRows();
        if (rows.length === 0) {
            var sendStatus = $('#sendStatus').val();
            var status = $('#status').val();
//            if(sendStatus!='01'){
            if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '01')) {
                Joyin.error("只能报送报送状态为未报送的数据");
                return;
            }
//            else if(status!='01'){
//                Joyin.error("只能报送数据状态为新增的数据");
//                return;
//            }
//            else if(status && status.includes('03')){
            else if (status && status.indexOf('03') > -1) {
                Joyin.error("只能对数据状态为新建和修改的数据进行报送！");
                return;
            }
            Joyin.ajax('sendBeforeCheck', getSearchParams(), function (result) {
                if (!result || result.length == 0) {
                    Joyin.confirm("按照已选择当前查询条件报送,是否确认！", function () {
                        layui.layer.open({type: 3});
                        Joyin.ajaxJson('submitChb', getSearchParams(), function () {
                            table.load();
                            layui.layer.closeAll();
                        });
                    });
                } else {
                    window.checkResult = result;
                    Joyin.checkData('<h4 class="modal-title" id="myModalLabel">投资者持有信息数据检查 </h4>', 'sendBeforeCheck', getSearchParams());
                }
            });
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].sendStatus != "01") {
                Joyin.error("只能报送报送状态为未报送的数据");
                return;
            } else if (rows[i].status && rows[i].status.indexOf('03') > -1) {
                Joyin.error("只能对报送状态为未报送，数据状态为新建和修改的数据进行报送！");
                return;
            }
        }
        var keyList = [];
        Joyin.ajax('sendBeforeCheck', {keyList: JSON.stringify(rows)}, function (result) {
            if (!result || result.length == 0) {
                Joyin.confirm("请确认是否要报送选中记录？", function () {
                    layui.layer.open({type: 3});
                    Joyin.ajaxJson('submitChb', {keyList: JSON.stringify(rows)}, function () {
                        table.load();
                        layui.layer.closeAll();
                    });
                })
            } else {
                window.checkResult = result;
                Joyin.checkData('<h4 class="modal-title" id="myModalLabel">投资者持有信息数据检查 </h4>', 'sendBeforeCheck', {keyList: JSON.stringify(rows)});
            }
        });
    });

    table.bind('click', '.batch', function (data, index) {
        var height = '600px';
        var btns = {};
        var keyList = [];
        var dialogIndex = -1;
        var path = basePath + 'svreport/pbc/PbcMonthReport001/preView';
        if (data.status == '03') {
            Joyin.error("删除状态数据不支持预览");
            return;
        }

        var param = {
            reportuuid: data.reportuuid,
            reporttype: data.reporttype
        }

        Joyin.ajax('preView', param, function (result) {
            if ("fail" == result) {
                Joyin.error("所选数据不存在，请重新查询！");
                table.load();
            } else {
                dialogIndex = Joyin.dialog('预览', path, '1200px', height, param, function (params) {
                }, btns);
            }
        });

    });


    /**
     * 手工处理
     */
    $('#ChbMonthReport001_btn_manual').click(function () {
        var rows = table.getAllCheckedRows();
        if (rows.length === 0) {
//            return Joyin.error("请至少选择一条记录");
            //add by liubin
            var sendStatus = $("#sendStatus").val();
            var submitFeedbackStatus = $("#submitFeedbackStatus").val();
            if (
                (
                    (
                        (sendStatus && sendStatus.length == 1 && sendStatus[0] == '03')
                        || (sendStatus && sendStatus.indexOf('03') > -1)
                    )
                    || (((sendStatus && sendStatus.length == 1 && sendStatus[0] == '02')
                            || (sendStatus && sendStatus.indexOf('02') > -1))
                        && (submitFeedbackStatus &&
                            (submitFeedbackStatus.indexOf('01') > -1 || submitFeedbackStatus.indexOf('03') > -1)
                        )
                    )
                )
            ) {
                Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态不为人行通过和发送成功的数据进行手工处理");
                return;
            }
            var paramObj = getSearchParams();
            paramObj.flag = '01';
            Joyin.confirm("请确认是否要手工处理符合查询条件的全部记录？", function () {
                layui.layer.open({type: 3});
                Joyin.ajaxJson('doManual', paramObj, function () {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        } else {
            for (let i = 0; i < rows.length; i++) {
                let sendStatus = rows[i].sendStatus;
                let submitFeedbackStatus = rows[i].submitFeedbackStatus;
                if (
                    (
                        (
                            (sendStatus && sendStatus.length == 1 && sendStatus[0] == '03')
                            || (sendStatus && sendStatus.indexOf('03') > -1)
                        )
                        || (((sendStatus && sendStatus.length == 1 && sendStatus[0] == '02')
                                || (sendStatus && sendStatus.indexOf('02') > -1))
                            && (submitFeedbackStatus &&
                                (submitFeedbackStatus.indexOf('01') > -1 || submitFeedbackStatus.indexOf('03') > -1)
                            )
                        )
                    )
                ) {
                    Joyin.error("只能选择报送状态为未报送、或报送状态为接口报送且反馈状态不为人行通过和发送成功的数据进行手工处理");
                    return;
                }
            }
            Joyin.confirm("请确认是否要手工处理选中相同报告期的记录？", function () {
                layui.layer.open({type: 3});
                Joyin.ajaxJson('doManual', {keyList: JSON.stringify(rows)}, function () {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        }

    });
    /**
     * 资产端批处理测试
     */
    $('#ChbMonthReport001_btn_assetbatch').click(function () {

        var paramObj = getSearchParams();
        Joyin.ajaxJson('doBatch', paramObj, function () {
            table.load();
            layui.layer.closeAll();
        });

    });
    /**
     * 重新计算
     */
    $('#ChbMonthReport001_btn_recal').click(function () {

        var paramObj = getSearchParams();
//    	 Joyin.ajaxJson('doBatch', paramObj, function() {
//             table.load();
//             layui.layer.closeAll();
//         });
        if ($('#submitDate').startDate() == null
            || $('#submitDate').startDate() == ""
            || $('#submitDate').endDate() == null
            || $('#submitDate').endDate() == "") {
            Joyin.error("请先选择一个报告期日期！");
            return;
        } else {
            // 重新查询是否有已生成的报表
            Joyin.ajaxSync('findPageBysubmitDate', paramObj, function (result) {
                var jsonResult = jQuery.parseJSON(result);
//            	var jsonResult = JSON.parse(result);
                //var jsonResult = eval('(' + result + ')');
                var yearStart = jsonResult["YEAR"];
                var monthStart = jsonResult["MONTH"];
                var SendStatus = jsonResult["SendStatus"];
                if (SendStatus != null && SendStatus != '01') {
                     Joyin.error(yearStart + "年" + monthStart + "月的统计表已报送,请重新选择！");
                     return;
                }
                if (yearStart && monthStart) {
                    var submitDateStart = Date.ofDate($('#submitDate').startDate());
                    Joyin.confirm(yearStart + "年" + monthStart + "月的统计表已经存在，请确认是否重新计算并生成?", function () {
                        layui.layer.open({type: 3});
                        recalPbcReport(paramObj);
                    });

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
    function recalPbcReport(paramObj) {
        // 打开虚拟进度条
        JoyinProgress.showInvented(false);
        // 执行批处理
        Joyin.ajaxJson('doBatch', paramObj, function (date) {
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
    $('#ChbMonthReport001_btn_cancelManual').click(function () {
        var manArr = table.getAllCheckedRows();
        if (!manArr || manArr.length == 0) {
            var sendStatus = $("#sendStatus").val();
            if (!(sendStatus && sendStatus.length == 1 && sendStatus[0] == '03')) {
                Joyin.error("只能选择手工处理的数据，请重新选择！");
                return;
            }
            var paramObj = getSearchParams();
            paramObj.flag = '01';
            Joyin.confirm("请确认是否要取消手工处理符合查询条件所有记录？", function () {
                layui.layer.open({type: 3});
                Joyin.ajaxJson('cancelMannul', paramObj, function () {
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
            Joyin.confirm("请确认是否要取消手工处理选中相同报告期的记录？", function () {
                layui.layer.open({type: 3});
                Joyin.ajaxJson('cancelMannul', {keyList: JSON.stringify(manArr)}, function () {
                    table.load();
                    layui.layer.closeAll();
                });
            });
        }

    });
    // add by wangmin 20180827 start
    // 二次报送
    $('#ChbMonthReport001_btn_submitAgain').click(function () {
        var paramObj = getSearchParams();

        var submitArr = table.getAllCheckedRows();
        if (submitArr.length === 0) {
            var submitStatus = $('#sendStatus').val();
            var status = $('#status').val();
            var submitFeedbackStatus = $('#submitFeedbackStatus').val();
            if (!(submitStatus && submitStatus.length == 1 && submitStatus[0] == '02')) {
                Joyin.error("只能对报送状态为接口报送的数据进行重新报送！");
                return;
            }
//               if(status && status.includes('03')){
            if (status && status.indexOf('03') > -1) {
                Joyin.error("只能对数据状态为新建和修改的数据进行重新报送！");
                return;
            }
            if (submitFeedbackStatus &&
                (submitFeedbackStatus.indexOf('01') > 0
                    || submitFeedbackStatus.indexOf('03') > 0
                    || submitFeedbackStatus.indexOf('05') > 0)) {
                Joyin.error("只能对反馈状态为发送失败和中债退回的数据进行重新报送！");
                return;
            }

            if (!status) {
                paramObj.status = '01,02';
            }
            if (!submitFeedbackStatus) {
                paramObj.submitFeedbackStatus = '02,04';
            }
            Joyin.confirm("请确认是否要按照已选择当前查询条件进行二次报送？", function () {
                layui.layer.open({type: 3});
                Joyin.ajaxJson('submitCbAgain', paramObj, function () {
                    table.load();
                    layui.layer.closeAll();
                });
            });
            return;
        }

        var keyList = [];
        for (var i = 0; i < submitArr.length; i++) {
            var submitFeedbackStatus = submitArr[i].submitFeedbackStatus;
            var submitStatus = submitArr[i].sendStatusCode;
            if (submitStatus && submitStatus.indexOf('01') > -1) {
                Joyin.error("未报送的数据不能进行二次报送！");
                return;
            } else if (!submitFeedbackStatus) {
                Joyin.error("反馈状态为空的数据不能进行二次报送！");
                return;
            } else if (submitFeedbackStatus &&
                (submitFeedbackStatus.indexOf('01') > -1
                    || submitFeedbackStatus.indexOf('03') > -1
                    || submitFeedbackStatus.indexOf('05') > -1)) {
                Joyin.error("只能对反馈状态为发送失败和中债退回的数据进行二次报送！");
                return;
            }
        }
        ;
        $.each(submitArr, function (i, v) {
            v.sendStatus = v.sendStatusCode;
            v.status = v.statusCode;
            v.ccy = v.ccyCode;
        });

        Joyin.confirm("请确认是否要二次报送？", function () {
            layui.layer.open({type: 3});
            Joyin.ajaxJson('submitCbAgain', {keyList: JSON.stringify(submitArr)}, function () {
                table.load();
                layui.layer.closeAll();
            });
        });
    });

    // add by wangmin 20180827 end

    function init() {
        if (isMain == '01') {
            setTimeout(function () {
                if ($('#sendStatus').val() == '02' || $('#sendStatus').val() == '03') {
                    $('#submitTime').val(searchDataStart + ' - ' + searchDataEnd);
                } else {
                    $('#submitDeadLine').val(searchDataStart + ' - ' + searchDataEnd);
                }
                table.load();
            }, 500);
        } else {
            setTimeout(function () {
                // $('#submitDeadLine').val(searchDataStart + ' - ' + searchDataEnd);
                $('#submitDate').val(searchDataStart + ' - ' + searchDataEnd);
                table.load();
            }, 500);
        }
        //报送前检查调用数据检查
        if (validKey) {
            setTimeout(function () {
                table.load();
            }, 500)
        }
    };
    init();

    $(function () {
        // 绑定按钮事件
        $('#PbcMonthReport001_btn_search').click(function () {
            table.load();
        });
    });
}());