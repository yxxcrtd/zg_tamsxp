$(function() {
    var getPageParams = function() {
        var orgCode = $('#orgCode').val();
        if (orgCode) {
            orgCode = orgCode.join();
        }
        var holderType = $('#holderType').val();
        if (holderType) {
            holderType = holderType.join();
        }
        var dataStatus = $('#status').val();
        if (dataStatus) {
            dataStatus = dataStatus.join();
        }
        return {
            // 登记银行代码
            bankCode : $('#bankCode').val(),
            // 识别标识
            indentifyMark : $('#indentifyMark').val(),
            // 投资者类别
            holderType : holderType,
            // 投资者名称
            holderName : $('#holderName').val(),
            // 证件号码
            identifyNumber : $('#identifyNumber').val(),
            // 投资者分类
            holderClassify : $('#holderClassify').val(),
            // 数据状态
            status : dataStatus,
            orgCode: orgCode
        };
    };
    var table = $('#equityInfo').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        cols: [
            { title: "数据状态", field: 'status',sortField:'status',width:100,align:'center'},
            { title: "登记银行代码", field: 'bankCode',width:150,align:'center' },
            { title: "机构代码",field:'orgCode', width: 150,align:'center'},
            { title: "识别标识", field: 'indentifyMark',width:105, align:'center' },
            { title: "原识别标识", field: 'orgIndentifyMark',width:110, align:'center' },
            { title: "该投资者是否属于本行", field: 'isBelongBank',sortField:'isBelongBank',width:179,align:'center'},
            { title: "投资者所属银行名称", field: 'theBankName',width:200 },
            { title: "投资者所属银行代码", field: 'theBankCode',width:168,align:'center'},
            { title: "投资者境内外标识", field: 'holderMark',sortField:'holderMark',width:152,align:'center'},
            { title: "投资者所属国家或地区", field: 'holderRegion',sortField:'holderRegion',width:180 },
            { title: "投资者类别", field: 'holderType',sortField:'holderType',width:130},
            { title: "投资者分类", field: 'holderClassify',sortField:'holderClassify',width:130},
            { title: "证件类别", field: 'inentifyType',sortField:'inentifyType', width: 260},
            { title: "证件号码", field: 'identifyNumber', width: 171},
            { title: "其他证件名称", field: 'otherIdentifyName', width: 126},
            { title: "SPV资金托管户开户行", field: 'spvCapAccBank',sortField:'spvCapAccBank', width: 181},
            { title: "其他资金托管账户开户行", field: 'otherCapAccBank', width: 181},
            /*{ title: "SPV登记编码", field: 'spvRegistCode', width: 125},*/
            { title: "投资者名称", field: 'holderName', width: 114},
            { title: "性别", field: 'holderSex', width: 70},
            { title: "风险偏好", field: 'holderRiskPreferStr', width: 100, align:'center'},
            { title: "手机号码", field: 'holderCellphone', width: 120},
            { title: "固定电话", field: 'holderTelephone', width: 120},
            { title: "电子邮箱", field: 'holderEmail', width: 150 },
            { title: "经办人", field: 'agent',width:100 },
            { title: "备注", field: 'remark', sortable:false, width: 120},
            {title:"接入检查结果",field: 'interfCheckResult', width: 220},
            {field: 'holderRiskPrefer',visible: false},
            { field: 'operation', title: 'SysCommon.operation', type:'action', buttons: ['edit', 'delt']}
        ]
    });
    /**
     * 删除数据
     * @returns
     */
    function removeSelectedData() {
        var rows = table.getAllCheckedRows();
        if(rows.length === 0) {
            return Joyin.Error("请至少选择一条记录");
        }
        for(var i=0;i<rows.length;i++){
            if(rows[i].status !="有效"){
                return Joyin.Error("只能删除有效数据，请重新选择");
            }
        }
        Joyin.ajaxJson('checkDelete', {deleteList:rows}, function(result){
            if(result){
                Joyin.confirm ("请确认是否要删除选中记录？", function() {
                    Joyin.ajaxJson('delete', {deleteList:rows}, function() {
                        $('#searchForm').trigger("click");
                    });
                });
            }
        });
    };
    /**
     * 页面初始加载 dingzhipeng
     */
    function init(){
        if(isFromMain=="01"){
            $('#status').val("A").trigger("change");
            setTimeout(function() {
                if($('#searchForm').joyinvalid()) {
                    table.load();
                }
            },100);
            table.load();
        }
    }
    $(function() {
        init();
        // 查询按钮点击事件
        $('#EquityInfo001_btn_search').click(function() {
            if($('#searchForm').joyinvalid()) {
                table.load();
            }
        });
        // 导入按钮点击事件
        $('#EquityInfo001_btn_import').click(function() {
            Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'), function() {
                Joyin.uploadFile({height:'530px',width:'850px'},function(fileName) {
                    Joyin.ajax('checkConfirmMsgForImportData', {fileName: fileName}, function() {
                        Joyin.ajax('importFile', {fileName: fileName});
                    },function() {
                        $('iframe').contents().find("#loading").hide();
                    });
                },function() {
                    table.reload();
                });
            });
        });

        //导入代销数据点击事件
        $('#EquityInfo001_btn_chbImport').click(function() {
            Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'), function() {
                var options = {};
                options.pageId = "EquityInfo001_2";
                Joyin.uploadFile({height:'530px',width:'850px'}, function(fileName) {
                    Joyin.ajax('checkConfirmMsgForChbImportData', {fileName: fileName}, function() {
                        Joyin.ajax('chbImportFile', {fileName: fileName});
                    },function() {
                        $('iframe').contents().find("#loading").hide();
                    });
                },function() {
                    table.load();
                });
            });
        });

        //新建按钮的事件
        $('#EquityInfo001_btn_add').click(function() {
            Joyin.dialog(JoyinUtil.getLabel("EquityInfo002"),
                basePath + "svreport/submitmaintenance/equityinfo/EquityInfo002/init",
                "90%", "100%", {pageModel:PAGE_MODEL_NEW},function(){
                    //防止出现查询转圈圈
                    setTimeout(function(){table.reload();},100);
                });
        });
        // 导出按钮点击事件
        $('#EquityInfo001_btn_export').click(function() {
            /*if(!table.isSearched()) {
                return;
            }*/
            var expArr=table.getAllCheckedRows();
            if(!expArr || expArr.length ==0){
                Joyin.exportExcel("export",getPageParams());
            }
            else{
                var keyList = "";
                $.each(expArr, function(i, row) {
                    keyList = keyList+row.bankCode + '|' + row.indentifyMark +",";
                });
                var param={};
                param.expKeys = keyList.substring(0,keyList.length-1);
                Joyin.exportExcel("exportSelect",param);
            }
        });



        //删除按钮点击事件
        $('#EquityInfo001_btn_delete').click(function(){
            var rows = table.getAllCheckedRows();
            if(rows.length === 0) {
                return Joyin.error("请至少选择一条记录");
            }
            for(var i=0;i<rows.length;i++){
                if(rows[i].status =="删除"){
                    return Joyin.error("只能删除有效、待处理数据，请重新选择");
                }
            }
            Joyin.ajaxJson('checkDelete', {deleteList:rows}, function(result){
                if(result){
                    Joyin.confirm("请确认是否要删除选中记录？", function() {
                        Joyin.ajaxJson('delete', {deleteList:rows}, function() {
                            $('#EquityInfo001_btn_search').trigger('click');
                        });
                    });
                }
            });
        });

        table.bind('click','.edit', function(row,index) {
            if(row.status == "删除"){
                return Joyin.error("不能操作已被删除的数据");
            }
            row.pageModel = PAGE_MODEL_EDIT;
//            data.operateType = "edit";
            Joyin.ajaxJson('checkEdit', row, function(result){
                Joyin.dialog("投资者身份信息修改",
                    basePath + "svreport/submitmaintenance/equityinfo/EquityInfo002/init",
                    "90%", "100%", row,function(){
                        table.reload();
                    });
            });
        });
        /**
         * 绑定删除事件
         */
        table.bind('click', '.delt', function(data, index) {
            if(data.status !="有效"){
                return Joyin.error("只能删除有效数据，请重新选择");
            }
            var param = {deleteList:[data]};
            Joyin.ajaxJson('checkDelete', param, function(result){
                if(result){
                    Joyin.confirm("请确认是否要删除选中记录？", function() {
                        Joyin.ajaxJson('delete', param, function() {
                            $('#EquityInfo001_btn_search').trigger("click");
                        });
                    });
                }
            });
        });
    });
}());