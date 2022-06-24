/**
 * 证券账户管理
 */
$(function() {
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return {
            prodId:$('#prodId').val(),
            prodName:$('#prodName').val(),
            regCode:$('#regCode').val(),
            raiseVdateStart: $.trim($('#raiseVdate').startDate()),
            raiseVdateEnd: $.trim($('#raiseVdate').endDate()),
            vdateStart: $.trim($('#vdate').startDate()),
            vdateEnd: $.trim($('#vdate').endDate()),
            issuingStatus : $('#issuingStatus').val() == null ? null : $(
                '#issuingStatus').val().join(","),
            customerType : $('#customerType').val() == null ? null : $(
                '#customerType').val().join(","),
        };
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
        dblclick: function (table, data) {
            Joyin.dialog(JoyinUtil.getLabel("FinStockInfo002.title"),
                basePath + "/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002/init",
                "99%", "99%", {
                    pageModel: PAGE_MODEL_REFERENCE,
                    produuid: data.produuid,
                    statusValid:data.statusValid

                });
        },
        cols:[
            {field:'produuid',visible:false}
            ,{field:'statusValid',visible:false}
            ,{field:'prodId', sortable:false, width:100, title: 'CchbPrdBaseInfo001.prodId'}
            ,{field:'prodInsId', sortable:false, width:100, title: 'CchbPrdBaseInfo001.prodInsId'}
            ,{field:'prodName',sortable:false,width:100,  title: 'CchbPrdBaseInfo001.prodName'}
            ,{field:'regCode', sortable:false,width:100,  title: 'CchbPrdBaseInfo001.regCode'}
            ,{field:'issuePartyId',sortable:false,width:100,title: 'CchbPrdBaseInfo001.issuePartyId'}
            ,{field:'raiseVdateStr', sortable:false,width:100, title: 'CchbPrdBaseInfo001.raiseVdate'}
            ,{field:'raiseEnddateStr',sortable:false,width:100,title: 'CchbPrdBaseInfo001.raiseEnddate'}
            ,{field:'vdateStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.vdate'}
            ,{field:'mdateStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.mdate'}
            ,{field:'manageTypeStr', sortable:false, width:100, title: 'CchbPrdBaseInfo001.manageType'}
            ,{field:'isConstracteStr',sortable:false,   width:100,title: 'CchbPrdBaseInfo001.isConstracte'}
            ,{field:'gradProportion',sortable:false,width:100, title: 'CchbPrdBaseInfo001.gradProportion'}
            ,{field:'yieldHigh', sortable:false,width:100,title: 'CchbPrdBaseInfo001.yieldHigh'}

            ,{field:'yieldLow', sortable:false, width:100, title: 'CchbPrdBaseInfo001.yieldLow'}
            ,{field:'yieldRemark',sortable:false,width:100,  title: 'CchbPrdBaseInfo001.yieldRemark'}
            ,{field:'openModeStr',sortable:false,width:100,title: 'CchbPrdBaseInfo001.openMode'}
            ,{field:'openCircleStr', sortable:false,width:100, title: 'CchbPrdBaseInfo001.openCircle'}
            ,{field:'fitCircleDays', sortable:false,width:100, title: 'CchbPrdBaseInfo001.fitCircleDays'}
            ,{field:'openCircleDays',sortable:false,width:100,title: 'CchbPrdBaseInfo001.openCircleDays'}
            ,{field:'ruleComment', sortable:false, width:100,title: 'CchbPrdBaseInfo001.ruleComment'}
            ,{field:'firstOpenDayStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.firstOpenDay'}
            ,{field:'holidayOnFlagStr', sortable:false, width:100, title: 'CchbPrdBaseInfo001.holidayOnFlag'}
            ,{field:'avgOpenCount',sortable:false,   width:100,title: 'CchbPrdBaseInfo001.avgOpenCount'}
            ,{field:'openBussStr',sortable:false,width:100, title: 'CchbPrdBaseInfo001.openBuss'}
            ,{field:'openBussRemark', sortable:false,width:100,title: 'CchbPrdBaseInfo001.openBussRemark'}

            ,{field:'prodtypeStr',sortable:false,width:180,title: 'CchbPrdBaseInfo001.prodtype'}
            ,{field:'calendarId', sortable:false,width:100, title: 'CchbPrdBaseInfo001.calendarId'}
            ,{field:'busiAcctNo',sortable:false,width:100,title: 'CchbPrdBaseInfo001.busiAcctNo'}
            ,{field:'acctName',sortable:false,width:100,title: 'CchbPrdBaseInfo001.acctName'}
            ,{field:'basicStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.basic'}
            ,{field:'terminatedateStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.terminatedate'}
            ,{field:'issuingStatusStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.issuingStatus'}
            ,{field:'fiduciaryDutyStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.fiduciaryDuty'}
            ,{field:'transferTypeStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.transferType'}
            ,{field:'customerTypeStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.customerType'}
            ,{field:'zjxtFlagStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.zjxtFlag'}
            ,{field:'remark', sortable:false, width:100,title: 'CchbPrdBaseInfo001.remark'}
            ,{field:'updateTimeStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.updateTime'}
            ,{field:'updateUser', sortable:false, width:100,title: 'CchbPrdBaseInfo001.updateUser'}
            ,{field:'statusValidStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.status'}
            ,{field:'errMsg', sortable:false, width:180,title: 'CchbPrdBaseInfo001.errMsg'}
            ,{field:'operation', width:100, title: 'SysCommon.operation',sortable:false, type:'action', buttons: ['edit', 'delt']}
        ]
    });
    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.operateType='edit'
        data.pageModel=PAGE_MODEL_EDIT;
        Joyin.confirm("请确认是否修改?", function(){
            Joyin.dialog(JoyinUtil.getLabel("CchbPrdBaseInfo002"),
                basePath + "/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002/init",
                "98%", "95%",data,
                function(){table.reload();});
        });
    });

    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
        console.log(data)
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
            WorkFlow.doDeleteList(basePath + '/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo001/delete', object, "CchbPrdBaseInfo001_btn_delete", "CchbPrdBaseInfo002",function(){
                table.reload();
            });
        });
    });
    
    var exportExcel = function() {   
        var paramObj = getSearchParams();
        Joyin.exportExcel('export', paramObj);
    };

    
    var reset = function(conditionArea,table){
        $(conditionArea).find("input").val("");
        $(conditionArea).find("select").val("").trigger("change");// select清空
    }
    /**
     * 画面加载完成
     */
    $(function() {
        //新建
        $('#CchbPrdBaseInfo001_btn_add').click(function() {
            Joyin.dialog(JoyinUtil.getLabel("SysCommon.create")+JoyinUtil.getLabel("CchbPrdBaseInfo002"),
                basePath + "/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002/init",
                "95%", "80%", {pageModel:PAGE_MODEL_NEW});
        });
        $('#CchbPrdBaseInfo001_btn_issueStatusMan').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
                if( selArr != null &&  selArr.length > 0 ){
                    var data = {};
                    data.selectedItem = selArr;
                    data.operateType = 'issueStatus';
                    Joyin.ajaxJson(basePath + '/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo001/check', data, function(result) {
                        var prodIdStr = "";
                        $.each(selArr, function(idx,v) {
                            prodIdStr = prodIdStr + v.prodId + ",";
                        });
                        // 跳转发行状态管理页面
                        Joyin.dialog(JoyinUtil.getLabel("CchbPrdBaseInfo003"),
                            JoyinUtil.getRealPath("/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo003/init"),
                            "70%", "60%",{prodIdStr : prodIdStr.substring(0, prodIdStr.length - 1)},function(){table.reload();}
                        );
                    });
                }else{
                    Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                    return;
                }
            }else{
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
        });

        // 绑定按钮事件
        $('#CchbPrdBaseInfo001_btn_search').click(function() {
            table.load();
            $('#CchbPrdBaseInfo001_btn_delete').show();
        });
        $('#CchbPrdBaseInfo001_btn_export').click(function() {
            exportExcel();
        });
        $('#CchbPrdBaseInfo001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("没有选中数据"));
                return;
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                WorkFlow.doDeleteList(basePath + '/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo001/delete', object, "CchbPrdBaseInfo001_btn_delete", "CchbPrdBaseInfo002",function(){
                    table.reload();
                });
            });
        });
      
        /** 事件绑定: 置空按钮 */
        $("#CchbPrdBaseInfo001_btn_reset").click(function(event){
            reset( '#selectArea',table);
        });


        // 导入按钮
        $('#CchbPrdBaseInfo001_btn_import').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
                var options = {};
                options.pageId = "CchbPrdBaseInfo001";
                options.exts = 'xls|xlsx';
                Joyin.uploadFile(options,function(fileName) {
                    Joyin.ajax(basePath + '/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo001/checkConfirmMsgForImportData', {fileName: fileName}, function(result) {
                        if ($.isArray(result.joyinMessages) && result.joyinMessages.length > 0) {
                            var joyinMessages = "";
                            $.each(result.joyinMessages, function(i, joyinMessage){
                                joyinMessages += joyinMessage.message + "<br />"
                            });
                            Joyin.confirm(joyinMessages, function(){
                                Joyin.ajax(basePath + '/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo001/importFileSave', {fileName: fileName},function () {
                                    table.reload();
                                });
                            });
                        }else{
                            Joyin.ajax(basePath + '/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo001/importFileSave', {fileName: fileName},function () {
                                table.reload();
                            });
                        }
                    });
                },function() {
                    table.reload();
                });
            });
        });
    });
}());