(function() {
    /**
     * 获取表单参数
     */
        var informationform = function() {
            var param = $.serializeToObject('#informationForm');
            return param;
        }


           //记录分类change事件
           function salerRewPenaTyepOnChange(){
               var salerRewPenaTyep = $('#salerRewPenaTyep').val();
               var salerRewPenaInst = $('#salerRewPenaInst').val();
               if (salerRewPenaTyep === '03'){
                    $('#salerRewPenaName').prop("disabled",true);
                    $("#salerRewPenaName").val('').trigger('change');
                    $('#salerRewPenaComments').prop("disabled",true);
                    $("#salerRewPenaComments").val('').trigger('change');
                    $('#salerRewPenaInst').prop("disabled",true);
                    $("#salerRewPenaInst").val('').trigger('change');
                    $('#salerRewPenaInstName').prop("disabled",true);
                    $("#salerRewPenaInstName").val('').trigger('change');
                    $('#salerRewPenaStart').prop("disabled",true);
                    $("#salerRewPenaStart").val('').trigger('change');
                    $('#salerRewPenaEnd').prop("disabled",true);
                    $("#salerRewPenaEnd").val('').trigger('change');
                    $('#salerComplainDetail').prop("disabled",false);
                    $('#salerComplainResult').prop("disabled",false);
                    $('#salerComplainDate').prop("disabled",false);
               }else{
                       $('#salerRewPenaName').prop("disabled",false);
                       $('#salerRewPenaComments').prop("disabled",false);
                       $('#salerRewPenaInst').prop("disabled",false);
                       $('#salerRewPenaInstName').prop("disabled",false);
                       $('#salerRewPenaStart').prop("disabled",false);
                       $('#salerRewPenaEnd').prop("disabled",false);
                       $('#salerComplainDetail').prop("disabled",true);
                       $("#salerComplainDetail").val('').trigger('change');
                       $('#salerComplainResult').prop("disabled",true);
                       $("#salerComplainResult").val('').trigger('change');
                       $('#salerComplainDate').prop("disabled",true);
                       $("#salerComplainDate").val('').trigger('change');
                       if(salerRewPenaInst === '01' || salerRewPenaInst === '02'){
                         $('#salerRewPenaInstName').prop("disabled",true);
                         $("#salerRewPenaInstName").val('').trigger('change');
                       }
               }
            }

            //奖惩单位change事件
            function salerRewPenaInstOnChange(){
               var salerRewPenaInst = $('#salerRewPenaInst').val();
                   if (salerRewPenaInst === '99'){
                        $('#salerRewPenaInstName').prop("disabled",false);
                   }else{
                        $('#salerRewPenaInstName').prop("disabled",true);
                        $("#salerRewPenaInstName").val('').trigger('change');
                   }
            }

                        //内部编号change事件
                        function salerInsNoOnChange(){
                           var salerInsNo = $('#salerInsNo').val();
                               Joyin.ajax(JoyinUtil.getRealPath('report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002/getSalerName'), {salerInsNo : salerInsNo}, function(result){
                                  if (result){
                                       $('#salerName').val(result);
                                     }
                               });
                        }

            //获取机构代码默认值
            function getIssuePartyId(){
               var salerInstCode = $('#salerInstCode').val();
               Joyin.ajax(JoyinUtil.getRealPath('report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002/getIssuePartyId'), {salerInstCode : salerInstCode}, function(result){
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
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002/check', object, function(result) {
                Joyin.confirm(result.data, function() {
                    if(operateType === 'save') {
                        WorkFlow.doSaveList(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002/save', object, "SalerRewPenaInfo002_btn_save");
                    }
                    else {
                        WorkFlow.doSubmitList(basePath + 'report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002/save', object, "SalerRewPenaInfo002_btn_submit");
                    }
                });
            });
        }

          //控制编辑时的输入框置灰
           var init = function(){
           var erroOrInfo = $('#erroOrInfo').val();
           if(pageModel==PAGE_MODEL_EDIT){
                     $('#salerInstCode').prop("disabled",true);
                     var salerRewPenaTyep = $('#salerRewPenaTyep').val();
                     if (erroOrInfo==='info'){
                         $('#salerName').prop("disabled",true);
                         if(salerRewPenaTyep === '03'){
                             $('#salerRewPenaName').prop("disabled",true);
                             $('#salerRewPenaComments').prop("disabled",true);
                             $('#salerRewPenaInst').prop("disabled",true);
                             $('#salerRewPenaInstName').prop("disabled",true);
                             $('#salerRewPenaStart').prop("disabled",true);
                             $('#salerRewPenaEnd').prop("disabled",true);
                         }else{
                             $('#salerComplainDetail').prop("disabled",true);
                             $('#salerComplainResult').prop("disabled",true);
                             $('#salerComplainDate').prop("disabled",true);
                             var salerRewPenaInst = $('#salerRewPenaInst').val();
                             if(salerRewPenaInst === '01' || salerRewPenaInst === '02'){
                                 $('#salerRewPenaInstName').prop("disabled",true);
                             }
                         }
                     }

           }
           else{
                     $('#salerName').prop("disabled",true);
                     $('#salerInstCode').prop("disabled",true);
                     getIssuePartyId();
           }
           }

      $(document).ready(function(){
            $('#salerRewPenaTyep').change(function(){
               salerRewPenaTyepOnChange();
            });
            $('#salerRewPenaInst').change(function(){
                salerRewPenaInstOnChange();
            });
            $('#salerInsNo').change(function(){
                salerInsNoOnChange();
            });
      });

        $(function() {
            //编辑字段置灰
            init();
            // 按钮事件
            $('#SalerRewPenaInfo002_btn_save').click(function() {
                saveOrSubmit('save');
            });
            $('#SalerRewPenaInfo002_btn_submit').click(function() {
                saveOrSubmit('submit');
            });
        });    
}());