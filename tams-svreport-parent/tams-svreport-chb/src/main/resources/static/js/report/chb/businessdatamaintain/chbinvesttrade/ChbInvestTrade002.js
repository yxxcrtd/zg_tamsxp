(function() {

    var editFlag = true;
    var init=function() {
        if(pageModel!=PAGE_MODEL_NEW && pageModel!=PAGE_MODEL_EDIT){
            editFlag=false;
        }
    };

    //双击查看
    if('detail'==detail){
        $('#CchbInvestTrade002_div').hide();
        $("#prodName").attr("disabled","disabled");
        $("#prodId").attr("disabled","disabled");
        $("#regCode").attr("disabled","disabled");
        $("#registBankCode").attr("disabled","disabled");
        $("#tradeCode").attr("disabled","disabled");
        $("#assetCode").attr("disabled","disabled");
        $("#assetName").attr("disabled","disabled");
        $("#ccy").attr("disabled","disabled");
        $("#amt").attr("disabled","disabled");
        $("#cnyAmt").attr("disabled","disabled");
        $("#num").attr("disabled","disabled");
        $("#assetMethod").attr("disabled","disabled");
        $("#curAssetdetType").attr("disabled","disabled");
        $("#dealdate").attr("disabled","disabled");
        $("#tradeCounter").attr("disabled","disabled");
        $("#rivalPartyType").attr("disabled","disabled");
        $("#unitPriceFull").attr("disabled","disabled");
        $("#unitPriceNet").attr("disabled","disabled");
        $("#endRate").attr("disabled","disabled");
        $("#contractCode").attr("disabled","disabled");
        $("#investApproverId").attr("disabled","disabled");
        $("#investApproverName").attr("disabled","disabled");
        $("#investTraderId").attr("disabled","disabled");
        $("#investTraderName").attr("disabled","disabled");
        $("#remark").attr("disabled","disabled");
        $("#curAssetType").attr("disabled","disabled");
    }else {

        var tradeCode = $('#tradeCode').val();
        if ('' == tradeCode || null == tradeCode) {
            //新建页面
            $('#tradeCode').val(tradeCode_New);
            $('#registBankCode').val(registBankCode_New);
            $('#contractCode').val(tradeCode_New);
        } else {
            //修改页面
            $("#tradeCode").attr("disabled", "disabled");
            $("#prodId").attr("disabled", "disabled");
            $("#registBankCode").attr("disabled", "disabled");
            $("#assetCode").attr("disabled", "disabled");
            $("#prodName").attr("disabled", "disabled");
            $("#curAssetType").attr("disabled", "disabled");
        }


        $('#prodName').change(function () {
            var prodName = $('#prodName').val();
            if ('' == prodName || null == prodName) {
            } else {
                Joyin.ajaxJson(basePath + '/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002/selectProdIdAndRegCodeByProdName?prodName=' + prodName, {}, function (result) {
                    $('#prodId').val(result['PROD_ID']);
                    $('#regCode').val(result['REG_CODE']);
                })
            }
        });
        $('#assetCode').change(function () {
            var assetCode = $('#assetCode').val();
            if ('' == assetCode || null == assetCode) {
            } else {
                Joyin.ajaxJson(basePath + '/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002/selectAssetNameByAssetCode?assetCode=' + assetCode, {}, function (result) {
                    $('#assetName').val(result['ASSET_NAME']);
                })
            }

        });
        $('#tradeCounter').change(function () {
            var tradeCounter = $('#tradeCounter').val();
            if ('' == tradeCounter || null == tradeCounter) {
            } else {
                Joyin.ajaxJson(basePath + '/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002/selectRivalPartyType?tradeCounter=' + tradeCounter, {}, function (result) {
                    $('#rivalPartyType').val(result['COUNTER_TYPE']).trigger('change');
                })
            }
        });
        let curAssetType = ['01', '02', '03', '04', '05', '06', '07', '08']
        $('#curAssetType').change(function () {
            if (!curAssetType.includes($('#curAssetType').val()) && '' != $('#curAssetType').val()) {
                $('#assetMethod').val('').trigger('change');
                $('#assetMethod').attr("disabled", "disabled");
            } else {
                $("#assetMethod").removeAttr("disabled");
            }
        });
        $('#ccy').change(function () {
            if ($('#ccy').val() == 'CNY') {
                $('#cnyAmt').attr("disabled", "disabled");
                $('#amt').blur(function () {
                    $('#cnyAmt').val($('#amt').val());
                })
            } else {
                $("#cnyAmt").removeAttr("disabled");
            }
        });

        function saveOrSubmit(operateType) {
            var object = $.serializeToObject('#submitForm');
            object.operateType = operateType;
            object.pageModel = pageModel;
            Joyin.ajaxJson(basePath + '/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002/check', object, function (result) {
                //提交工作流
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001", JoyinUtil.getLabel("SysCommon.submit")), function () {
                    WorkFlow.doSubmitList(basePath + '/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002/save', object, "CchbInvestTrade002_btn_submit");
                });
            });
        };

    //折合人民币金额字段取得
    var countCnyAmt = function(){
        if($("#ccy").val()=='CNY'||$("#dealdate").val()==""||$('#amt').val()==""){
            $("#cnyAmt").val($("#amt").val()).blur();
            return ;
        }
        else{
            var param = {};
            var holdDate=$("#dealdate").val();
            param.HOLDDATE= Date.ofDate(holdDate).format();
            param.amount=$('#amt').val();
            param.CCY=$('#ccy').val();
            if (param.HOLDDATE && param.CCY && param.amount) {
                Joyin.ajax('getCnyAmt',param,function(result){
                    if(result.data){
                        $("#cnyAmt").val(result.data).trigger('change').blur();
                    }
                });
            }
        }

    };

        /**
         * 画面加载完成
         */
        $(function () {
            init();
            $("#ccy").on('change',function(){
               if(editFlag){
                  countCnyAmt();
               }
             });

            $("#dealdate").on('change',function(){
                 if(editFlag){
                    countCnyAmt();
                 }
            });

            $("#amt").on('change',function(){
               if(editFlag){
                  countCnyAmt();
               }
            });
            //修改和审批被驳回时，保存按钮隐藏
            $('#btnSave_wfl').hide();
            // 绑定按钮事件
            $('#CchbInvestTrade002_btn_submit').click(function () {
                saveOrSubmit("submit");
            });

        });
    }
}());