/**
 * 销售结算资金交易信息维护新建
 */
(function() {
    /**
     * 获取表单参数
     */
        var informationform = function() {
            var param = $.serializeToObject('#informationForm');
            return param;
        }

        /**
         * 保存或提交
         */
        function saveOrSubmit(operateType) {
            var object = informationform();
            object.operateType = operateType;
            object.pageModel = pageModel;
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002/check', object, function(result) {
                Joyin.confirm(result.data, function() {
                    if(operateType === 'save') {
                        WorkFlow.doSaveList(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002/save', object, "SalesTransactionMaintenance002_btn_save");
                    }
                    else {
                        WorkFlow.doSubmitList(basePath + 'report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002/save', object, "SalesTransactionMaintenance002_btn_submit");
                    }
                });
            });
        }

          //控制编辑时的输入框置灰
           var init = function(){
           if(pageModel==PAGE_MODEL_EDIT){
              if($('#disabledThing').val() != null && $('#disabledThing').val() != ""){
                     $('#institutionCode').prop("disabled",true);
                     $('#prdRegistCode').prop("disabled",true);
                     $('#accountNo').prop("disabled",true);
                     $('#tradeNo').prop("disabled",true);
                     $('#tradePs').prop("disabled",true);
                     $('#tradeType').prop("disabled",true);
                      $('#accountName').prop("disabled",true);
                      $('#tradeCounterAccNo').prop("disabled",true);
                      $('#tradeCounterAccName').prop("disabled",true);
                      $('#tradeCounterAccBankName').prop("disabled",true);
                      $('#tradeCounterAccBankNo').prop("disabled",true);
                      $('#tradeCcy').prop("disabled",true);
                      $('#tradeTime').prop("disabled",true);
                      $('#tradeVdate').prop("disabled",true);
                  }
           }
           }


        $(function() {
            //编辑字段置灰
            init();
            // 按钮事件
            $('#SalesTransactionMaintenance002_btn_save').click(function() {
                saveOrSubmit('save');
            });
            $('#SalesTransactionMaintenance002_btn_submit').click(function() {
                saveOrSubmit('submit');
            });
            
        });    
}());