(function() {
    if($('#oCode').val()!=''){
        $("#oCode").attr("disabled","disabled");
        $("#oName").attr("disabled","disabled");
        $("#oType").attr("disabled","disabled");
        $("#source").attr("disabled","disabled");
        $("#gradeDate").attr("disabled","disabled");
        $("#gradeOrgId").attr("disabled","disabled");
    }
    function saveOrSubmit(operateType) {
        var object = $.serializeToObject('#submitForm');
        object.operateType = operateType;
        object.pageModel = pageModel;
        Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating002/check', object, function(result) {
           //提交工作流
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.submit")), function() {
                    WorkFlow.doSubmitList(basePath + 'chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating002/save', object, "ChbMstBondCreditRating002_btn_submit");
            });
        });
    };

    /**
     * 画面加载完成
     */
    $(function () {
        if(pageModel === PAGE_MODEL_EDIT){
            $('#btnSave_wfl').hide();
        }
        // 绑定按钮事件
        $('#ChbMstBondCreditRating002_btn_submit').click(function() {
            saveOrSubmit("submit");
        });
        
        //修改上级科目号时，自动添加到科目号中
        $("#fsubjectId").change(function(){
            $("#subjectNo").val($("#fsubjectId").val());
        });
    });
}());