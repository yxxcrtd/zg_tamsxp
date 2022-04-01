/**
 * 机构登记银行代码管理画面
 */
(function() {
    var getPageParams = function() {
        var orgCode = $("#SysInstRegBankCode001_orgCode").val();
        if (orgCode) {
            orgCode = orgCode.join();
        }
        return {
            orgCode : orgCode,
            registBankCode : $('#SysInstRegBankCode001_registBankCode').val()
        };
    };
    var SysInstRegBankCode001_table001 = $('#SysInstRegBankCode001_table001').joyintable({
        ajax: {
            url: basePath + 'svreport/system/instregbankcode/SysInstRegBankCode001/searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        cols: [
            { title: "机构代码", field:'orgCode'},
            { title: "机构名称", field:'orgName', sortField:'orgCode'},
            { title: "中债登记机构代码", field:'registBankCode'},
            { title: "人行登记机构代码", field:'pbcBankCode'},
            { title: "是否理财子公司", field:'isFinSubCompany'},
            { title: "社会信用代码", field:'insitituteCode'},
            { title: "所属银保监局", field:'belogCodeCBRC'},
            { field:'operation', title: '操作', type:'action', buttons: ['edit', 'delt']}
        ]
    });
    
    
    $(function() {
        // 查询按钮点击事件
        $('#SysInstRegBankCode001_btn_search').click(function() {
            if($('#SysInstRegBankCode001_searchForm').joyinvalid()) {
                SysInstRegBankCode001_table001.load();
            }
        });
        
        // 导入按钮点击事件
        $('#SysInstRegBankCode001_btn_import').click(function() {
            Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'), function() {
                Joyin.uploadFile(function(fileName) {
                    Joyin.ajax('checkConfirmMsgForImportData', {fileName: fileName}, function() {
                        Joyin.ajax('importFile', {fileName: fileName});
                    },function() {
                        $('iframe')[0].contentWindow.JoyinUtil.hideLoading();
                    });
                    
                },function() {
                    SysInstRegBankCode001_table001.reload();
                });
            });
        });
        
        // 导出按钮点击事件
        $('#SysInstRegBankCode001_btn_export').click(function() {
            var param = getPageParams();
            var rows = SysInstRegBankCode001_table001.getAllCheckedRows();
            if(rows.length > 0) {
                var keyList = [];
                $.each(rows, function(i, row) {
                    keyList.push(row.orgCode);
                });
                param.keys = keyList.join(',');
            }
            Joyin.exportExcel("export", param);
        });
        
        /**
         * 新建按钮点击事件
         * 跳转至新增页面
         */
        $('#SysInstRegBankCode001_btn_add').click(function() {
            var paramObj = {
                    pageModel : PAGE_MODEL_NEW
                }
            var pageUrl = basePath + "svreport/system/instregbankcode/SysInstRegBankCode002/init";
            Joyin.dialog("新建机构登记银行代码", pageUrl, '50%', '70%', paramObj, function() {
                SysInstRegBankCode001_table001.reload();
            });
        });
        
        // 行内编辑按钮点击事件
        SysInstRegBankCode001_table001.bind('click', '.edit', function(row) {
            var paramObj = {
                    orgCode : row.orgCode,
                    pageModel : PAGE_MODEL_EDIT
            }
            var pageUrl = basePath + "svreport/system/instregbankcode/SysInstRegBankCode002/init";
            Joyin.dialog("编辑机构登记银行代码", pageUrl, '50%', '70%', paramObj, function() {
                SysInstRegBankCode001_table001.reload();
            });
        });
        
        // 行内删除按钮点击事件
        SysInstRegBankCode001_table001.bind('click', '.delt', function(data, index) {
            var keyList = [];
            keyList.push(data.orgCode);
            Joyin.confirm(JoyinUtil.getMessage('REPORT_COMMON_C_0003'), function() {
                var deleteUrl = basePath + 'svreport/system/instregbankcode/SysInstRegBankCode001/delete';
                Joyin.ajax(deleteUrl, {deleteKeys: keyList.join(',')}, function() {
                    SysInstRegBankCode001_table001.reload();
                });
            });
        });
        
        // 删除按钮点击事件 删除数据
        $('#SysInstRegBankCode001_btn_delete').click(function(){
            var rows = SysInstRegBankCode001_table001.getAllCheckedRows();
            if(rows.length === 0) {
                return Joyin.error(JoyinUtil.getMessage('REPORT_COMMON_E_0017'));
            }
            var keyList = [];
            $.each(rows, function(i, row) {
                keyList.push(row.orgCode);
            });
            Joyin.confirm(JoyinUtil.getMessage('REPORT_COMMON_C_0003'),function() {
                var deleteUrl = basePath + 'svreport/system/instregbankcode/SysInstRegBankCode001/delete';
                Joyin.ajax(deleteUrl, {deleteKeys: keyList.join(',')}, function() {
                    SysInstRegBankCode001_table001.reload();
                });
            });
        });
    });
}());