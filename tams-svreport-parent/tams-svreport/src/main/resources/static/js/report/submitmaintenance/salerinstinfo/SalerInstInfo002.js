(function() {
    /**
     * 获取表单参数
     */
        var informationform = function() {
            var param = $.serializeToObject('#informationForm');
            return param;
        }

            //获取机构代码默认值
            function getSalerInstCode(){
               var salerOrgCode = $('#salerOrgCode').val();
               Joyin.ajax(JoyinUtil.getRealPath('report/submitmaintenance/salerinstinfo/SalerInstInfo002/getSalerInstCode'), {salerOrgCode : salerOrgCode}, function(result){
                         if (result){
                           $('#salerInstCode').val(result);
                         }
               });
            }

        /**
         * 保存或提交
         */
        function saveOrSubmit(operateType) {
            var object = informationform();
            object.operateType = operateType;
            object.pageModel = pageModel;
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo002/check', object, function(result) {
                Joyin.confirm(result.data, function() {
                    if(operateType === 'save') {
                        WorkFlow.doSaveList(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo002/save', object, "SalerInstInfo002_btn_save");
                    }
                    else {
                        WorkFlow.doSubmitList(basePath + 'report/submitmaintenance/salerinstinfo/SalerInstInfo002/save', object, "SalerInstInfo002_btn_submit");
                    }
                });
            });
        }

          //控制编辑时的输入框置灰
           var init = function(){
           var erroOrInfo = $('#erroOrInfo').val();
           if(pageModel==PAGE_MODEL_EDIT){
                $('#salerInstCode').prop("disabled",true);
                $('#salerOrgCode').prop("disabled",true);
           }else{
                  $('#salerInstCode').prop("disabled",true);
                  getSalerInstCode();
                }
           }

        $(function() {
            //编辑字段置灰
            init();
            // 按钮事件
            $('#SalerInstInfo002_btn_save').click(function() {
                saveOrSubmit('save');
            });
            $('#SalerInstInfo002_btn_submit').click(function() {
                saveOrSubmit('submit');
            });
        });    
}());