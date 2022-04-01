(function() {
    /**
     * 获取表单参数
     */
        var informationform = function() {
            var param = $.serializeToObject('#informationForm');
            return param;
        }

        var laydate = layui.laydate;
        laydate.render({
    		elem: '#salerWorkStartYmd',
    		type: 'month',
    		format: 'yyyy-MM'
    	});
    	laydate.render({
            elem: '#salerFinanceStartYmd',
            type: 'month',
            format: 'yyyy-MM'
        });
        laydate.render({
            elem: '#salerStartYmd',
            type: 'month',
            format: 'yyyy-MM'
        });

           //证件类别change事件
           function salerCredentTypeOnChange(){
               var salerCredentType = $('#salerCredentType').val();
               if (salerCredentType === '99'){
                    $('#salerOtherCredentName').prop("disabled",false);
                    $("#salerOtherCredentName").val('').trigger('change');
               }else{
                    $('#salerOtherCredentName').prop("disabled",true);
                    $("#salerOtherCredentName").val('').trigger('change');
               }
            }

            //获取机构代码默认值
            function getIssuePartyId(){
               var salerInstCode = $('#salerInstCode').val();
               Joyin.ajax(JoyinUtil.getRealPath('report/submitmaintenance/salerideninfo/SalerIdenInfo002/getIssuePartyId'), {salerInstCode : salerInstCode}, function(result){
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
             // 理财专业证书
             if (object.salerCertificateType) {
                   object.salerCertificateType = object.salerCertificateType.join(',');
             }
             // 可销售产品风险等级
             if (object.salerRiskLevel) {
                   object.salerRiskLevel = object.salerRiskLevel.join(',');
             }
            object.operateType = operateType;
            object.pageModel = pageModel;
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo002/check', object, function(result) {
                Joyin.confirm(result.data, function() {
                    if(operateType === 'save') {
                        WorkFlow.doSaveList(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo002/save', object, "SalerIdenInfo002_btn_save");
                    }
                    else {
                        WorkFlow.doSubmitList(basePath + 'report/submitmaintenance/salerideninfo/SalerIdenInfo002/save', object, "SalerIdenInfo002_btn_submit");
                    }
                });
            });
        }

          //控制编辑时的输入框置灰
           var init = function(){
             var erroOrInfo = $('#erroOrInfo').val();
             var salerCredentType = $('#salerCredentType').val();
             if(pageModel==PAGE_MODEL_EDIT){
                     $('#salerInstCode').prop("disabled",true);
                     $('#salerInsNo').prop("disabled",true);
                     if(salerCredentType != '99'){
                      $('#salerOtherCredentName').prop("disabled",true);
                     }

             }
              else{
                     $('#salerInstCode').prop("disabled",true);
                     getIssuePartyId();
                     if(salerCredentType != '99'){
                          $('#salerOtherCredentName').prop("disabled",true);
                     }
              }
           }

      $(document).ready(function(){
            $('#salerCredentType').change(function(){
               salerCredentTypeOnChange();
            });
      });

        $(function() {
            //编辑字段置灰
            init();
            // 按钮事件
            $('#SalerIdenInfo002_btn_save').click(function() {
                saveOrSubmit('save');
            });
            $('#SalerIdenInfo002_btn_submit').click(function() {
                saveOrSubmit('submit');
            });
        });    
}());