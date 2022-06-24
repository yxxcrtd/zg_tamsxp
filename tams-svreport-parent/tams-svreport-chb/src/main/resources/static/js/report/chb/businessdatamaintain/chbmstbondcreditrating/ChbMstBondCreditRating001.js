/**
 * 证券账户管理
 */
$(function() {
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return {
            oCode : $('#oCode').val() == null ? null : $(
                '#oCode').val().join(","),
            oType:$('#oType').val(),
            status:$('#status').val(),
            shortLongTerm:$('#shortLongTerm').val(),
     	   	gradeDateStart: $.trim($('#gradeDate').startDate()),
     	   	gradeDateEnd: $.trim($('#gradeDate').endDate()),
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
        cols:[
            {field:'oCode', sortable:false, width:100, title: 'ChbMstBondCreditRating001.oCode'}
            ,{field:'oName',sortable:false,width:160,  title: 'ChbMstBondCreditRating001.oName'}
            ,{field:'oTypeStr', sortable:false,width:100,  title: 'ChbMstBondCreditRating001.oType'}
            ,{field:'gradeOrgIdStr',sortable:false,width:180,title: 'ChbMstBondCreditRating001.gradeOrgId'}
            ,{field:'shortLongTermStr', sortable:false,width:100, title: 'ChbMstBondCreditRating001.shortLongTerm'}
            ,{field:'gradeResultStr',sortable:false,width:100,title: 'ChbMstBondCreditRating001.gradeResult'}
            ,{field:'sourceStr', sortable:false, width:100,title: 'ChbMstBondCreditRating001.source'}
            ,{field:'gradeDateStr', sortable:false, width:100,title: 'ChbMstBondCreditRating001.gradeDate'}
            ,{field:'statusValidStr', sortable:false, width:100, title: 'ChbMstBondCreditRating001.status'}
            ,{field:'errMsg',sortable:false,   width:150,title: 'ChbMstBondCreditRating001.errMsg'}
            ,{field:'updateTimeStr',sortable:false,width:100, title: 'ChbMstBondCreditRating001.updateTime'}
            ,{field:'updateUser', sortable:false,width:100,title: 'ChbMstBondCreditRating001.updateUser'}
            ,{field:'operation', width:100, title: 'SysCommon.operation',sortable:false, type:'action', buttons: ['edit', 'delt']}
        ]
        
    });

    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.operateType='edit'
        data.pageModel=PAGE_MODEL_EDIT;
        Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating002/check', data, function(result) {
            Joyin.confirm("请确认是否修改?", function(){
                Joyin.dialog(JoyinUtil.getLabel("ChbMstBondCreditRating002"),
                    basePath + "chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating002/init",
                    "98%", "95%",data,
                    function(){table.reload();});
            });
        });

    });

    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/check', object, function(result) {
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                WorkFlow.doDeleteList(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/delete', object, "ChbMstBondCreditRating001_btn_delete", "ChbMstBondCreditRating002",function(){
                    table.reload();
                });
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
        $('#ChbMstBondCreditRating001_btn_add').click(function() {
            Joyin.dialog(JoyinUtil.getLabel("SysCommon.create")+JoyinUtil.getLabel("ChbMstBondCreditRating002"),
                basePath + "chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating002/init",
                "95%", "80%", {pageModel:PAGE_MODEL_NEW});
        });
        // 绑定按钮事件
        $('#ChbMstBondCreditRating001_btn_search').click(function() {
            table.load();
            $('#ChbMstBondCreditRating001_btn_delete').show();
        });
        $('#ChbMstBondCreditRating001_btn_export').click(function() {
            exportExcel();
        });
        $('#ChbMstBondCreditRating001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("没有选中数据"));
                return;
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/check', object, function(result) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    WorkFlow.doDeleteList(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/delete', object, "ChbMstBondCreditRating001_btn_delete", "ChbMstBondCreditRating002",function(){
                        table.reload();
                    });
                });
            });
        });
      
        /** 事件绑定: 置空按钮 */
        $("#ChbMstBondCreditRating001_btn_reset").click(function(event){
            reset( '#selectArea',table);
        });
        /**
         * 导入按钮
         */
        // $('#ChbMstBondCreditRating001_btn_import').click(function() {
        //     Joyin.uploadFile(function(fileName) {
        //         Joyin.ajax(basePath +"chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/importFile", {
        //             fileName : fileName
        //         },function(result){
        //             if(!result || !($.isArray(result) && result.length > 0)){
        //                 Joyin.ajax(
        //                     basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/importFileSave',
        //                     {fileName : fileName},function (data1) {
        //                         layui.layer.closeAll()
        //                         table.reload();
        //                     });
        //             }
        //         });
        //     });
        // });


        // 导入按钮
        $('#ChbMstBondCreditRating001_btn_import').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
                var options = {};
                options.pageId = "ChbMstBondCreditRating001";
                options.exts = 'xls|xlsx';
                Joyin.uploadFile(options,function(fileName) {
                    Joyin.ajax(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/checkConfirmMsgForImportData', {fileName: fileName,tableName:'MST_BOND_CREDIT_RATING'}, function(result) {
                        if ($.isArray(result.joyinMessages) && result.joyinMessages.length > 0) {
                            var joyinMessages = "";
                            $.each(result.joyinMessages, function(i, joyinMessage){
                                joyinMessages += joyinMessage.message + "<br />"
                            });
                            Joyin.confirm(joyinMessages, function(){
                                Joyin.ajax(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/importFileSave', {fileName: fileName,tableName:'MST_BOND_CREDIT_RATING'},function () {
                                    table.reload();
                                });
                            });
                        }else{
                            Joyin.ajax(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001/importFileSave', {fileName: fileName,tableName:'MST_BOND_CREDIT_RATING'},function () {
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