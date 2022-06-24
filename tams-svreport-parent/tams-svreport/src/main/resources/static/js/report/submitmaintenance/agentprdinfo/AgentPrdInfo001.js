/**
 * 委托销售产品编码信息
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
        Joyin.dialog(JoyinUtil.getLabel('委托销售产品编码信息新建'),basePath+'report/submitmaintenance/agentprdinfo/AgentPrdInfo002/init', '98%', '95%',data,function(){table.reload();});
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
            Joyin.dialog(JoyinUtil.getLabel("委托销售产品编码信息查看"),
                    JoyinUtil.getRealPath("report/submitmaintenance/agentprdinfo/AgentPrdInfo002/init"),
                    "98%", "95%", data,
                    function(){table.reload();});
        },
        cols:[
             {field:'statusStr', title: '数据状态', width:'200',sortField:'status'}
            ,{field:'issuePartyId', title: '发行机构代码', width:'250',sortField:'issuePartyId'}
            ,{field:'agentInstitutionCode', title: '代理销售机构代码', width:'250',sortField:'agentInstitutionCode'}
            ,{field:'agentInstitutionName', title: '代理销售机构名称', width:'250',sortField:'agentInstitutionName'}
            ,{field:'prodId', title: '产品代码', width:'250',sortField:'prodId'}
            ,{field:'prodName', title: '产品名称', width:'250',sortField:'prodName'}
            ,{field:'prodRegistCode', title: '产品登记编码', width:'200',sortField:'prodRegistCode'}
            ,{field:'action', title: '操作' , type:'action',buttons: ['delt'] , width:'150'}
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
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
                    if(data.status !="E"){
                        return Joyin.error("只能删除有效数据，请重新选择");
                    }
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentprdinfo/AgentPrdInfo001/check', object, function(result) {
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                           Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentprdinfo/AgentPrdInfo001/delete', object, function() {
                                                      $('#AgentPrdInfo001_btn_search').trigger("click");
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
        $('#AgentPrdInfo001_btn_search').click(function() {
            table.load();
            $('#AgentPrdInfo001_btn_delete').show();
        });
        /**
         * 导出按钮
         */
        $('#AgentPrdInfo001_btn_export').click(function() {
            exportExcel();
        });
        /**
         * 新建按钮
         */
        $('#AgentPrdInfo001_btn_add').click(function() {
            showDialog();
        });

    // 导入按钮点击事件
    $('#AgentPrdInfo001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
        var options = {};
        options.pageId = "AgentPrdInfo";
            Joyin.uploadFile(options,function(fileName) {
                Joyin.ajax(basePath + 'report/submitmaintenance/agentprdinfo/AgentPrdInfo001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                    Joyin.ajax(basePath + 'report/submitmaintenance/agentprdinfo/AgentPrdInfo001/importFile', {fileName: fileName});
                });
            },function() {
                table.reload();
            });
        });
    });
        /**
         * 批量删除按钮
         */
        $('#AgentPrdInfo001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
            for(var i=0;i<selArr.length;i++){
                if(selArr[i].status !="E"){
                   return Joyin.error("只能删除有效数据，请重新选择");
                }
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentprdinfo/AgentPrdInfo001/check', object, function(result) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentprdinfo/AgentPrdInfo001/delete', object, function() {
                           $('#AgentPrdInfo001_btn_search').trigger("click");
                    });
                });
            });
        });
    });
}());