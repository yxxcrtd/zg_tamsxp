/**
 * 销售人员身份信息
 */
(function(){
    /**
     * 获取查询参数
     */
    var searchform =function(){
        var param = $.serializeToObject('#searchForm');
        return param;
    };

    /**
     * 新建
     */
    function showDialog() {
        var data={"operateType":"add"};
        Joyin.dialog(JoyinUtil.getLabel('销售人员身份信息新建'),basePath+'report/submitmaintenance/salerideninfo/SalerIdenInfo002/init', '98%', '95%',data,function(){table.reload();});
    }
    
    /**
     * 定义数据表格
     */
    var table=$("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return searchform();
            }
        },
        dblclick:function(table, data) {
            data.operateType = "dblclick";
            data.pageModel=PAGE_MODEL_REFERENCE;
            Joyin.dialog(JoyinUtil.getLabel("销售人员身份信息查看"),
                    JoyinUtil.getRealPath("report/submitmaintenance/salerideninfo/SalerIdenInfo002/init"),
                    "98%", "95%", data,
                    function(){table.reload();});
        },
        cols:[
             {field:'erroOrInfo', title:'来自erro还是info',visible:false}
            ,{field:'workflowId', title:'错误表的workflowId',visible:false}
            ,{field:'dataStatusStr', title: '数据状态', width:'150',sortField:'dataStatus'}
            ,{field:'salerInstCode', title: '销售机构代码', width:'150',sortField:'salerInstCode'}
            ,{field:'salerName', title: '姓名', width:'150',sortField:'salerName'}
            ,{field:'salerSex', title: '性别', width:'150',sortField:'salerSex'}
            ,{field:'salerBirthday', title: '出生日期', width:'150',sortField:'salerBirthday',type:'date'}
            ,{field:'salerCredentType', title: '证件类别', width:'150',sortField:'salerCredentType'}
            ,{field:'salerOtherCredentName', title: '其他证件名称', width:'150',sortField:'salerOtherCredentName'}
            ,{field:'salerCredentNo', title: '证件号码', width:'150',sortField:'salerCredentNo'}
            ,{field:'salerInsNo', title: '内部编号', width:'150',sortField:'salerInsNo'}
            ,{field:'salerRegistNo', title: '销售人员登记编码', width:'150',sortField:'salerRegistNo'}
            ,{field:'salerAreaType', title: '所属区域', width:'150',sortField:'salerAreaType'}
            ,{field:'salerOrgCode', title: '所属总部或分支机构', width:'150',sortField:'salerOrgCode'}
            ,{field:'salerDept', title: '所属部门', width:'150',sortField:'salerDept'}
            ,{field:'salerPosition', title: '职位', width:'150',sortField:'salerPosition'}
            ,{field:'salerEduLevel', title: '学历', width:'150',sortField:'salerEduLevel'}
            ,{field:'salerDegree', title: '学位', width:'150',sortField:'salerDegree'}
            ,{field:'salerWorkStartYmd', title: '首次参加工作时间', width:'150',sortField:'salerWorkStartYmd'}
            ,{field:'salerFinanceStartYmd', title: '首次从事金融工作时间', width:'150',sortField:'salerFinanceStartYmd'}
            ,{field:'salerStartYmd', title: '首次从事理财销售时间', width:'150',sortField:'salerStartYmd'}
            ,{field:'salerLevle', title: '专业技术职称', width:'150',sortField:'salerLevle'}
            ,{field:'salerCertificateTypeStr', title: '理财专业证书', width:'150',sortField:'salerCertificateType'}
            ,{field:'salerChbTrainNo', title: '理财登记培训证书编号', width:'150',sortField:'salerChbTrainNo'}
            ,{field:'salerRiskLevelStr', title: '可销售产品风险等级', width:'150',sortField:'salerRiskLevel'}
            ,{field:'salerQualification', title: '销售资质', width:'150',sortField:'salerQualification'}
            ,{field:'salerTel', title: '办公电话', width:'150',sortField:'salerTel'}
            ,{field:'salerMobile', title: '移动电话', width:'150',sortField:'salerMobile'}
            ,{field:'salerMail', title: '邮箱', width:'150',sortField:'salerMail'}
            ,{field:'salerStatus', title: '当前状态', width:'150',sortField:'salerStatus'}
            ,{field:'remark', title: '备注', width:'150',sortField:'remark'}
            ,{field:'errMsg', title: '错误信息', width:'150',sortField:'errMsg'}
            ,{field:'action', title: '操作' , type:'action',buttons: ['edit', 'delt'] , width:'150'}
        ],
    });
/**
 * 导出表格
 */
    var exportExcel = function() {
        if (table != null && table != '') {
            var selArr = table.getAllCheckedRows();
            var datas = JSON.stringify(selArr);
        }
        if (!selArr || selArr.length == 0) {
            var paramObj = searchform();
            Joyin.exportExcel('export', paramObj);
        } else {
            Joyin.exportExcel('export', {
                datas : datas
            });
        }
    };

            /**
             * 绑定编辑事件
             */
            table.bind('click', '.edit', function(data, index) {
                data.operateType = "edit";
                data.pageModel=PAGE_MODEL_EDIT;
                Joyin.dialog(JoyinUtil.getLabel("销售人员身份信息编辑"),
                        JoyinUtil.getRealPath("report/submitmaintenance/salerideninfo/SalerIdenInfo002/init"),
                        "98%", "95%", data,
                   function(){table.reload();});
            });

    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
                    if(data.dataStatus !="E"){
                        return Joyin.error("只能删除有效数据，请重新选择");
                    }
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/check', object, function(result) {
         Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/deleteCheck', object, function(result1) {
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                  Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/delete', object, function() {
                                     $('#SalerIdenInfo001_btn_search').trigger("click");
                  });
            });
          });
        });
    });

    /**
     * 綁定按钮事件
     */
    $(function () {
        /**
         * 查询按钮
         */
        $('#SalerIdenInfo001_btn_search').click(function() {
            table.load();
            $('#SalerIdenInfo001_btn_delete').show();
        });
        /**
         * 导出按钮
         */
        $('#SalerIdenInfo001_btn_export').click(function() {
            exportExcel();
        });
        /**
         * 新建按钮
         */
        $('#SalerIdenInfo001_btn_add').click(function() {
            showDialog();
        });

    // 导入按钮点击事件
    $('#SalerIdenInfo001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
        var options = {};
        options.pageId = "SalerIdenInfo";
            Joyin.uploadFile(options,function(fileName) {
                Joyin.ajax(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                    Joyin.ajax(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/importFile', {fileName: fileName});
                });
            },function() {
                table.reload();
            });
        });
    });

        // 导入销售人员编码按钮点击事件
        $('#SalerIdenInfo001_btn_importCode').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
            var options = {};
            options.pageId = "SalerIdenInfo_importCode";
                Joyin.uploadFile(options,function(fileName) {
                    Joyin.ajax(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/checkConfirmMsgForImportCodeData', {fileName: fileName}, function() {
                        Joyin.ajax(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/importCode', {fileName: fileName});
                    });
                },function() {
                    table.reload();
                });
            });
        });

        /**
         * 批量删除按钮
         */
        $('#SalerIdenInfo001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
            for(var i=0;i<selArr.length;i++){
                if(selArr[i].dataStatus !="E"){
                   return Joyin.error("只能删除有效数据，请重新选择");
                }
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/check', object, function(result) {
              Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/deleteCheck', object, function(result1) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo001/delete', object, function() {
                                  $('#SalerIdenInfo001_btn_search').trigger("click");
                    });
                });
              });
            });
        });
    });
}());