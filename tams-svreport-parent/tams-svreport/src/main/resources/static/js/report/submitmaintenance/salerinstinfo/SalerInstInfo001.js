/**
 * 总部或分支机构信息
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
        Joyin.dialog(JoyinUtil.getLabel('总部或分支机构信息新建'),basePath+'report/submitmaintenance/salerinstinfo/SalerInstInfo002/init', '98%', '95%',data,function(){table.reload();});
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
            Joyin.dialog(JoyinUtil.getLabel("总部或分支机构信息查看"),
                    JoyinUtil.getRealPath("report/submitmaintenance/salerinstinfo/SalerInstInfo002/init"),
                    "98%", "95%", data,
                    function(){table.reload();});
        },
        cols:[
             {field:'erroOrInfo', title:'来自erro还是info',visible:false}
            ,{field:'workflowId', title:'错误表的workflowId',visible:false}
            ,{field:'statusValid', visible:false}
            ,{field:'statusValidStr', title: '数据状态', width:'200',sortField:'statusValid'}
            ,{field:'salerInstCode', title: '销售机构代码', width:'200',sortField:'salerInstCode'}
            ,{field:'salerOrgCode', title: '总部或分支机构编码', width:'200',sortField:'salerOrgCode'}
            ,{field:'salerOrgName', title: '总部或分支机构名称', width:'200',sortField:'salerOrgName'}
            ,{field:'salerOrgLevel', title: '机构层级', width:'200',sortField:'salerOrgLevel'}
            ,{field:'errMsg', title: '错误信息', width:'200',sortField:'errMsg'}
            ,{ field: 'operation', title: 'SysCommon.operation', type:'action', buttons: ['edit', 'delt']}
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

//        /**
//         * 绑定编辑事件
//         */
//        table.bind('click', '.edit', function(data, index) {
//            data.operateType = "edit";
//            data.pageModel=PAGE_MODEL_EDIT;
//            Joyin.dialog(JoyinUtil.getLabel("总部或分支机构信息编辑"),
//                    JoyinUtil.getRealPath("report/submitmaintenance/salerinstinfo/SalerInstInfo002/init"),
//                    "98%", "95%", data,
//               function(){table.reload();});
//        });


    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.operateType = "edit";
        data.pageModel=PAGE_MODEL_EDIT;
        Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo002/editCheck', data, function(result) {
            Joyin.dialog(JoyinUtil.getLabel("总部或分支机构信息编辑"),
                JoyinUtil.getRealPath("report/submitmaintenance/salerinstinfo/SalerInstInfo002/init"),
                "98%", "95%", data,
           function(){table.reload();});
        });

    });

    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
                    if(data.statusValid !="E"){
                        return Joyin.error("只能删除有效数据，请重新选择");
                    }
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo001/check', object, function(result) {
          Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo001/deleteCheck', object, function(result1) {
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                   Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo001/delete', object, function() {
                                   $('#SalerInstInfo001_btn_search').trigger("click");
                   });
            });
          });
        });
    });

    /**
     * 綁定按钮事件
     */
    $(function () {
    // 绑定重置按钮事件
                $('#SalerInstInfo001_btn_reset').click(function(){
                    window.location.reload();
                });
        /**
         * 查询按钮
         */
        $('#SalerInstInfo001_btn_search').click(function() {
            table.load();
            $('#SalerInstInfo001_btn_delete').show();
        });
        /**
         * 导出按钮
         */
        $('#SalerInstInfo001_btn_export').click(function() {
            exportExcel();
        });
        /**
         * 新建按钮
         */
        $('#SalerInstInfo001_btn_add').click(function() {
            showDialog();
        });

    // 导入按钮点击事件
    $('#SalerInstInfo001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
        var options = {};
        options.pageId = "SalerInstInfo";
            Joyin.uploadFile(options,function(fileName) {
                Joyin.ajax(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                    Joyin.ajax(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo001/importFile', {fileName: fileName});
                });
            },function() {
                table.reload();
            });
        });
    });
        /**
         * 批量删除按钮
         */
        $('#SalerInstInfo001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
            for(var i=0;i<selArr.length;i++){
                if(selArr[i].statusValid !="E"){
                   return Joyin.error("只能删除有效数据，请重新选择");
                }
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo001/check', object, function(result) {
              Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo001/deleteCheck', object, function(result1) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo001/delete', object, function() {
                                     $('#SalerInstInfo001_btn_search').trigger("click");
                    });
                });
              });
            });
        });
    });
}());