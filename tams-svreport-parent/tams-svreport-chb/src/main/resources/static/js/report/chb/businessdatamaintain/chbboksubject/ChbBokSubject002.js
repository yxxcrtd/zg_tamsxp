(function() {

    if($('#subjectNo').val()!=''){
        $("#subjectNo").attr("disabled","disabled");
    }
    function saveOrSubmit(operateType) {
        var object = $.serializeToObject('#submitForm');
        object.operateType = operateType;
        object.pageModel = pageModel;
        Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbboksubject/chbboksubject002/check', object, function(result) {
           //提交工作流
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.submit")), function() {
                    WorkFlow.doSubmitList(basePath + 'chb/businessdatamaintain/chbboksubject/chbboksubject002/save', object, "ChbBokSubject002_btn_submit");
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
        $('#ChbBokSubject002_btn_submit').click(function() {
            saveOrSubmit("submit");
        });
    });
}());