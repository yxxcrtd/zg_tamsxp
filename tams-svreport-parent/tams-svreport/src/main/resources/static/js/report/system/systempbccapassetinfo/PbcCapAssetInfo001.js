(function() {
    var getPageParams = function() {
        var assetCode = $('#PbcCapAssetInfo001_assetCode').val();
        if(assetCode==='为空'){
            assetCode = null;
        }
        if (assetCode) {
            assetCode = assetCode.join();
        }
        var pobcassetid = $('#PbcCapAssetInfo001_pobcassetid').val();
        if(pobcassetid==='为空'){
            pobcassetid = null;
        }
        if (pobcassetid) {
            pobcassetid = pobcassetid.join();
        }
        var orgCode = $("#orgCode").val();
        if (orgCode) {
            orgCode = orgCode.join();
        }
        return {
            orgCode : orgCode,
            // 资产代码
            assetCode : assetCode,
            // 人行资金信托代码
            pobcassetid : pobcassetid,
            // 资产起息日开始
            planSdateStart : $('#PbcCapAssetInfo001_planStartDate').startDate(),
            // 资产起息日结束
            planSdateEnd : $('#PbcCapAssetInfo001_planStartDate').endDate(),
            // 资产到期日开始
            planEdateStart : $('#PbcCapAssetInfo001_planEndDate').startDate(),
            // 资产到期日结束
            planEdateEnd : $('#PbcCapAssetInfo001_planEndDate').endDate()
        };
    };
    var table = $('#PbcCapAssetInfo001_PbcCapAssetInfo001').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        cols: [
             { title: "assetUuid", field:'assetUuid',visible:false},
            { title: "行内资产/负债编码", field:'assetCode',width:180},
            { title: "资产名称", field:'assetName'},
            { title: "*人行资金信托代码", field:'pobcassetid'},
            { title: "资产起息日",type : 'date',field:'planSdate',width:200},
            { title: "资产到期日",type : 'date',field:'planEdate',width:200},
            // { title: "隐藏列代码类型", field:'codeType',visible:false,width:10},
        ]
    });

    $(function() {
        // 查询按钮点击事件
        $('#PbcCapAssetInfo001_btn_search').click(function() {
            if($('#searchForm').joyinvalid()) {
                table.load();
                initFlag=false;
            }
        });
        // 导入按钮点击事件
        $('#PbcCapAssetInfo001_btn_import').click(function() {
            // add by zhoubin 20220326 start
            Joyin.confirm("请确认是否要导入数据？", function() {
                Joyin.uploadFile(function(fileName) {
                    Joyin.ajax(basePath + 'svreport/system/systempbccapassetinfo/PbcCapAssetInfo001/checkConfirmMsgForImportDataByExcel', {fileName: fileName}, function(result) {
                        if ($.isArray(result.errorList) && result.errorList.length > 0) {
                            return Joyin.error(result.errorList);
                        }
                        Joyin.ajax(basePath + 'svreport/system/systempbccapassetinfo/PbcCapAssetInfo001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                            Joyin.ajax(basePath + 'svreport/system/systempbccapassetinfo/PbcCapAssetInfo001/importFile', {fileName: fileName});
                        });
                    });
                },function() {
                    table.reload();
                });
            });
        });
        // add by zhoubin 20220326 end

        // 删除按钮点击事件
        $("#PbcCapAssetInfo001_btn_delete").click(function(){
            if(!table.isSearched()) {
                return;
            }
            var selArr = table.getAllCheckedRows();
            if (!selArr || selArr.length == 0) {
                Joyin.error("请从列表中选择数据");
                return;
            }
            var param = {};
            var expArr=table.getAllCheckedRows();
            var keyList = [];
            $.each(expArr, function(i, row) {
                keyList.push(row.assetUuid);
            });
            if(expArr.length > 1){
                param.keyList = keyList.join(',');
            }else{
                param.keyList = keyList.join(',');
            }
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")),function() {
                    Joyin.ajaxJson(basePath + 'svreport/system/systempbccapassetinfo/PbcCapAssetInfo001/deletePbcCapAssetInfo', param, function() {
                        $("#PbcCapAssetInfo001_btn_search").trigger("click");
                    });
                });
        });

        // 导出按钮
        $('#PbcCapAssetInfo001_btn_export').click(function(){
            var expArr=table.getAllCheckedRows();
            if(expArr != null && expArr.length >0){
                Joyin.confirm('确认导出选中信息？',function(){
                    exportExcel(1);
                })
            }else{
                Joyin.confirm('确认导出查询结果？',function(){
                    exportExcel(0);
                })
            }
        })

        /**
         * 导出
         * flag:0 全部导出，选中导出
         */
        var exportExcel = function(flag) {
            if(flag == 0){
                var param = getPageParams();
                Joyin.exportExcel("export",param);
            }else{
                var param = {};
                var expArr=table.getAllCheckedRows();
                var keyList = [];
                $.each(expArr, function(i, row) {
                    keyList.push(row.assetUuid);
                });
                if(expArr.length > 1){
                    param.keyList = keyList.join(',');
                }else{
                    param.keyList = keyList;
                }
                Joyin.exportExcel("export",param);
            }
        };

    });
}());
