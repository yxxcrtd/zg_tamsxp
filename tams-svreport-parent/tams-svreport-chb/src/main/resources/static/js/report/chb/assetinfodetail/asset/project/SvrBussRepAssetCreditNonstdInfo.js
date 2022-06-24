/**
 * 信贷及非标资产中债报送组件JS
 * @auth zhangnian
 */
(function(){

    var star = function() {
        // 加红星星
        // 查找label
        var _findPrevLabel = function(element, level) {
            var $el = element.prev();
            if (!$el.is('label') && level > 0) {
                $el = $el.find('label');
                if ($el.length === 0) {
                    return _findPrevLabel(element.parent(), level - 1);
                }
            }
            return $el;
        };

        // 非查看模式
        $('.must').each(function() {
            $(this).remove();
        })
        $('*[required]').each(function() {
            var $el = $(this);
            var $target = _findPrevLabel($el, 3);
            if ($target.find('> .must').length === 0) {
                $target.append($('<i class="must">*</i>'));
            }
        });
    }

    var getElementById = function(id) {
        return $('#' + entityName_BussRepAssetCreditNonstdInfo + '_' + id);
    };
    
    var repIndustryFlagChange2 = function(){
        
        var repIndustryFlag = getElementById('repIndustryFlag')[0].checked;
        if(repIndustryFlag){
            getElementById('repMonitType').attr('disabled',false);
            getElementById('repMonTypeComm').attr('disabled',false);
        }else{
            getElementById('repMonTypeComm').val('');
            getElementById('repMonitType').val('').trigger('change');
            getElementById('repMonTypeComm').attr('disabled',true);
            getElementById('repMonitType').attr('disabled',true);
        }
    }

    //行权方式事件
    function byRepExerciseType() {
        if (getElementById('repExerciseType').val() == '02') {
            //固定行权日
            getElementById('repExerciseDate').attr('disabled', false).trigger('change');
            getElementById('repFirstExerciseDate').val('').attr('disabled', true).trigger('change');
            getElementById('repExercisePeriod').val('').attr('disabled', true).trigger('change');
        } else if (getElementById('repExerciseType').val() == '03') {
            //首次行权日
            getElementById('repFirstExerciseDate').attr('disabled', false).trigger('change');
            //行权周期
            getElementById('repExercisePeriod').attr('disabled', false).trigger('change');
            getElementById('repExerciseDate').val('').attr('disabled', true).trigger('change');
        }else{
            getElementById('repExerciseDate').attr('disabled', true).trigger('change');
            getElementById('repFirstExerciseDate').attr('disabled', true).trigger('change');
            getElementById('repExercisePeriod').attr('disabled', true).trigger('change');
            if(getElementById('repExerciseType').val()=='' ||
                getElementById('repExerciseType').val()=='01'){
                getElementById('repExerciseDate').val('');
                getElementById('repFirstExerciseDate').val('');
                getElementById('repExercisePeriod').val('');
            }

        }
    }

    //含权类型事件
    function byRepContainType() {
        if(getElementById('repContainType').val()==''){
            //行权方式
            getElementById('repExerciseType').val('').attr('disabled', true).trigger('change');
        }else{
            getElementById('repExerciseType').attr('disabled', false).trigger('change');
        }
        if (getElementById('repContainType').val()=='02'||getElementById('repContainType').val()=='03'
        ||getElementById('repContainType').val()=='05'||getElementById('repContainType').val()=='06'||
            getElementById('repContainType').val()=='07'){
        //行权价格
            getElementById('repExercisePrice').val('').attr('disabled', true).trigger('change');
        }else if(getElementById('repContainType').val() != null &&　getElementById('repContainType').val() != ''){
            getElementById('repExercisePrice').attr('disabled', false).trigger('change');
        }
        // if(getElementById('repContainType').val()==''){
        //     //行权方式
        //     getElementById('repExerciseType').val('').attr('disabled', true).trigger('change');
        // }else  if(getElementById('repContainType').val()=='01'||getElementById('repContainType').val()=='03'){
        //     getElementById('repExerciseType').attr('disabled', false).trigger('change');
        //     //固定行权日
        //     getElementById('repExerciseDate').val('').attr('disabled', true).trigger('change');
        //    //首次行权日期
        //     getElementById('repFirstExerciseDate').val('').attr('disabled', true).trigger('change');
        //     //行权周期
        //     getElementById('repExercisePeriod').val('').attr('disabled', true).trigger('change');
        // }else{
        //     getElementById('repExerciseType').attr('disabled', false).trigger('change');
        //     getElementById('repExerciseDate').attr('disabled', false).trigger('change');
        //     //首次行权日期
        //     etElementById('repFirstExerciseDate').attr('disabled', false).trigger('change');
        //     //行权周期
        //     getElementById('repExercisePeriod').attr('disabled', false).trigger('change');
        // }
        // if (getElementById('repContainType').val()=='02'||getElementById('repContainType').val()=='03'
        // ||getElementById('repContainType').val()=='05'||getElementById('repContainType').val()=='06'||
        //     getElementById('repContainType').val()=='07'){
        // //行权价格
        //     getElementById('_repExercisePrice').val('').attr('disabled', true).trigger('change');
        // }else{
        //     getElementById('_repExercisePrice').attr('disabled', false).trigger('change');
        // }

    }
    //递延利息是否计息
    function byRepDeferredInterestType(){
    	if (getElementById('repDeferredInterestType').val() == '01') {
            //递延利息是否计息
            getElementById('repDeferredInterestInt').val('').attr('disabled', true).trigger('change');
        }else if(getElementById('repDeferredInterestType').val() != null &&　getElementById('repDeferredInterestType').val() != ''){
            getElementById('repDeferredInterestInt').attr('disabled', false).trigger('change');
        }
    }
    //永续条款类型事件
    function byRepPerpetualClauseType() {
        if (getElementById('repPerpetualClauseType').val() == '01' || getElementById('repPerpetualClauseType').val() == '02') {
            //首次重定价日期
            getElementById('repFirstReprceDate').attr('disabled', false).trigger('change');
            //重定价周期
            getElementById('repReprceCycle').attr('disabled', false).trigger('change');
        }else{
            getElementById('repFirstReprceDate').val('').attr('disabled', true).trigger('change');
            getElementById('repReprceCycle').val('').attr('disabled', true).trigger('change');
        }
    }

    //担保方式事件
    function byRepGuaType() {
        if(getElementById('repGuaType').val() == '01' || getElementById('repGuaType').val() == '02'||
            getElementById('repGuaType').val() == '05' || getElementById('repGuaType').val() == '99'){
             //抵质押物类型
            getElementById('repPledgeType').val('').attr('disabled', true).trigger('change');
            getElementById('repPledgeValue').val('').attr('disabled', true).trigger('change');
        }else{
            //抵质押物类型
            getElementById('repPledgeType').attr('disabled', false).trigger('change');
            getElementById('repPledgeValue').attr('disabled', false).trigger('change');
        }
        if(getElementById('repGuaType').val() == '03' || getElementById('repGuaType').val() == '04'||
            getElementById('repGuaType').val() == '05' || getElementById('repGuaType').val() == '99'){
            getElementById('repNatureGuarantee').val('').attr('disabled', true).trigger('change');
            getElementById('repRelationFinan').val('').attr('disabled', true).trigger('change');
        }else{
            getElementById('repNatureGuarantee').attr('disabled', false).trigger('change');
            getElementById('repRelationFinan').attr('disabled', false).trigger('change');
        }
    }
    

    var  repRateFlagChangeType=function(){
        var  repRateFlagval=getElementById('repRateFlag').val();
        if(repRateFlagval=='02'){
            getElementById('assetYield').val('').trigger("change");
            getElementById('assetYield').attr('disabled',true);
        }else{
            getElementById('assetYield').attr('disabled',false);
        }
    }

    var  repIndustryFlagChangeType=function(){
        var  repIndustryFlagval=getElementById('repIndustryFlag').val();
        if(repIndustryFlagval=='02'){
            getElementById('repMonitType').val('').trigger("change");
            getElementById('repMonitType').attr('disabled',true);

            getElementById('repMonTypeComm').val('').trigger("change");
            getElementById('repMonTypeComm').attr('disabled',true);
        }else{
            getElementById('repMonitType').attr('disabled',false);
            getElementById('repMonTypeComm').attr('disabled',false);

        }
    }

    var  repGuaTypeChangeType=function(){
         var  repGuaTypeVal=getElementById('repGuaType').val();

         if(repGuaTypeVal==''){
             getElementById('repGuaTypeRemark').val('').trigger("change");
             getElementById('repGuaTypeRemark').attr('disabled',true);
         }else{
             getElementById('repGuaTypeRemark').attr('disabled',false);
         }


    }

    //是否有浮动因子
    var  floatFactorFlagChangeType=function () {
      if(getElementById('floatFactorFlag').val()=='02'){
          getElementById('floatFactor').val('').trigger("change");
          getElementById('floatFactor').attr("disabled",true);

          // getElementById('spreadRate').val('').trigger("change");
          // getElementById('spreadRate').attr("disabled",true);

      }else if(getElementById('floatFactorFlag').val() != null &&　getElementById('floatFactorFlag').val() != ''){
          getElementById('floatFactor').attr("disabled",false);
          getElementById('spreadRate').attr("disabled",false);

      }
    }

    var  repPartRedemFlagChangeType=function(){
//        if(getElementById('repPartRedemFlag').val()=='02'){
//            getElementById('repPartRedemRate').val('').trigger("change");
//            getElementById('repPartRedemRate').attr("disabled",true);
//        }else if(getElementById('repPartRedemFlag').val() != null &&　getElementById('repPartRedemFlag').val() != ''){
//            getElementById('repPartRedemRate').attr("disabled",false);
//        }
    }

    var  initAssetCreditNonstd=function(){
        var assetTvalueCreditNonstd=$('#assetType2').val();

        if(assetTvalueCreditNonstd=='2201'||assetTvalueCreditNonstd=='2202'){
            //份额面值置灰不可填
            $("#svrBussRepAssetCreditNonstdInfoDto_repPrinBaseamt").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repPrinBaseamt").attr("disabled",true);
            //法定到期日置灰不可填
            $("#svrBussRepAssetCreditNonstdInfoDto_repFmdate").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repFmdate").attr("disabled",true);
            //计息类型置灰不可填
            $("#svrBussRepAssetCreditNonstdInfoDto_repCouponSpecies").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repCouponSpecies").attr("disabled",true);
            //规则付息标识
            $("#svrBussRepAssetCreditNonstdInfoDto_interestRuleFlag").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_interestRuleFlag").attr("disabled",true);
            //利息发布方式
            $("#svrBussRepAssetCreditNonstdInfoDto_repIntpayRule").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repIntpayRule").attr("disabled",true);
            //还本付息情况
           // $("#svrBussRepAssetCreditNonstdInfoDto_repRepayCmt").val('').trigger("change");
          //  $("#svrBussRepAssetCreditNonstdInfoDto_repRepayCmt").attr("disabled",true);

            //浮动因子
            $("#svrBussRepAssetCreditNonstdInfoDto_floatFactor").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_floatFactor").attr("disabled",true);
            //担保方式
            $("#svrBussRepAssetCreditNonstdInfoDto_repGuaType").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repGuaType").attr("disabled",true);
            //抵质押物类型
            $("#svrBussRepAssetCreditNonstdInfoDto_repPledgeType").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repPledgeType").attr("disabled",true);
            //抵质押物价值
            $("#svrBussRepAssetCreditNonstdInfoDto_repPledgeValue").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repPledgeValue").attr("disabled",true);
            //担保性质
            $("#svrBussRepAssetCreditNonstdInfoDto_repNatureGuarantee").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repNatureGuarantee").attr("disabled",true);
            //担保人与融资人关系
            $("#svrBussRepAssetCreditNonstdInfoDto_repRelationFinan").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repRelationFinan").attr("disabled",true);
            //行权价格
            $("#svrBussRepAssetCreditNonstdInfoDto_repExercisePrice").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repExercisePrice").attr("disabled",true);
            //递延利息是否计息
            $("#svrBussRepAssetCreditNonstdInfoDto_repDeferredInterestInt").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repDeferredInterestInt").attr("disabled",true);
            //部分赎回比例%
            $("#svrBussRepAssetCreditNonstdInfoDto_repPartRedemRate").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repPartRedemRate").attr("disabled",true);

            //是否有浮动因子
            $("#svrBussRepAssetCreditNonstdInfoDto_floatFactorFlag").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_floatFactorFlag").attr("disabled",true);
            //结构档次
            $("#svrBussRepAssetCreditNonstdInfoDto_repStructGrade").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repStructGrade").attr("disabled",true);
            //还本方式
            $("#svrBussRepAssetCreditNonstdInfoDto_repRepaymentType").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repRepaymentType").attr("disabled",true);
            //分期还本条款标识
            $("#svrBussRepAssetCreditNonstdInfoDto_repPeriodRepaymentFlag").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repPeriodRepaymentFlag").attr("disabled",true);
            //基础资产类型
            $("#svrBussRepAssetCreditNonstdInfoDto_repBasicAssetType").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repBasicAssetType").attr("disabled",true);
            //超额收益分配比例%
            $("#svrBussRepAssetCreditNonstdInfoDto_repExcessProfitRate").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repExcessProfitRate").attr("disabled",true);
            //含权类型
            $("#svrBussRepAssetCreditNonstdInfoDto_repContainType").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repContainType").attr("disabled",true);

            //永续条款类型
            $("#svrBussRepAssetCreditNonstdInfoDto_repPerpetualClauseType").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repPerpetualClauseType").attr("disabled",true);

            $("#svrBussRepAssetCreditNonstdInfoDto_repDeferredInterestType").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repDeferredInterestType").attr("disabled",true);


            $("#svrBussRepAssetCreditNonstdInfoDto_repPartRedemFlag").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repPartRedemFlag").attr("disabled",true);


            $("#svrBussRepAssetCreditNonstdInfoDto_creditOrgCode").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_creditOrgCode").attr("disabled",true);

            $("#svrBussRepAssetCreditNonstdInfoDto_creditOrgName").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_creditOrgName").attr("disabled",true);


            //要素去除必填标识 add by tuqirong 2022-01-08 start
            $("#svrBussRepAssetCreditNonstdInfoDto_repPrinBaseamt").prop("required",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repCouponSpecies").prop("required",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_interestRuleFlag").prop("required",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repIntpayRule").prop("required",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_floatFactorFlag").prop("required",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repPeriodRepaymentFlag").prop("required",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repPerpetualClauseType").prop("required",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repDeferredInterestType").prop("required",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repPartRedemFlag").prop("required",false);
            //要素去除必填标识 add by tuqirong 2022-01-08 end
        }else{

            $("#svrBussRepAssetCreditNonstdInfoDto_repPrinBaseamt").attr("disabled",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repFmdate").attr("disabled",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repCouponSpecies").attr("disabled",false);
            //规则付息标识
            $("#svrBussRepAssetCreditNonstdInfoDto_interestRuleFlag").attr("disabled",false);
            //利息发布方式
            $("#svrBussRepAssetCreditNonstdInfoDto_repIntpayRule").attr("disabled",false);
            //还本付息情况
            //$("#svrBussRepAssetCreditNonstdInfoDto_repRepayCmt").attr("disabled",false);
            //浮动因子
            $("#svrBussRepAssetCreditNonstdInfoDto_floatFactor").attr("disabled",false);
            //担保方式
            $("#svrBussRepAssetCreditNonstdInfoDto_repGuaType").attr("disabled",false);
            //抵质押物类型
            $("#svrBussRepAssetCreditNonstdInfoDto_repPledgeType").attr("disabled",false);
            //抵质押物价值
            $("#svrBussRepAssetCreditNonstdInfoDto_repPledgeValue").attr("disabled",false);
            //担保性质
            $("#svrBussRepAssetCreditNonstdInfoDto_repNatureGuarantee").attr("disabled",false);
            //担保人与融资人关系
            $("#svrBussRepAssetCreditNonstdInfoDto_repRelationFinan").attr("disabled",false);
            //行权价格
            $("#svrBussRepAssetCreditNonstdInfoDto_repExercisePrice").attr("disabled",false);
            //递延利息是否计息
            $("#svrBussRepAssetCreditNonstdInfoDto_repDeferredInterestInt").attr("disabled",false);
            //部分赎回比例%
            $("#svrBussRepAssetCreditNonstdInfoDto_repPartRedemRate").attr("disabled",false);
            //是否有浮动因子
            $("#svrBussRepAssetCreditNonstdInfoDto_floatFactorFlag").attr("disabled",false);
            //结构档次
            $("#svrBussRepAssetCreditNonstdInfoDto_repStructGrade").attr("disabled",false);
            //还本方式
            $("#svrBussRepAssetCreditNonstdInfoDto_repRepaymentType").attr("disabled",false);
            //分期还本条款标识
            $("#svrBussRepAssetCreditNonstdInfoDto_repPeriodRepaymentFlag").attr("disabled",false);
            //基础资产类型
            $("#svrBussRepAssetCreditNonstdInfoDto_repBasicAssetType").attr("disabled",false);
            //超额收益分配比例%
            $("#svrBussRepAssetCreditNonstdInfoDto_repExcessProfitRate").attr("disabled",false);
            //含权类型
            $("#svrBussRepAssetCreditNonstdInfoDto_repContainType").attr("disabled",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repPerpetualClauseType").attr("disabled",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repDeferredInterestType").attr("disabled",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_repPartRedemFlag").attr("disabled",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_creditOrgCode").attr("disabled",false);
            $("#svrBussRepAssetCreditNonstdInfoDto_creditOrgName").attr("disabled",false);
            //要素添加必填标识 add by tuqirong 2022-01-08 start
            $("#svrBussRepAssetCreditNonstdInfoDto_repPrinBaseamt").prop("required",true);
            $("#svrBussRepAssetCreditNonstdInfoDto_repCouponSpecies").prop("required",true);
            $("#svrBussRepAssetCreditNonstdInfoDto_interestRuleFlag").prop("required",true);
            $("#svrBussRepAssetCreditNonstdInfoDto_repIntpayRule").prop("required",true);
            $("#svrBussRepAssetCreditNonstdInfoDto_floatFactorFlag").prop("required",true);
            $("#svrBussRepAssetCreditNonstdInfoDto_repPeriodRepaymentFlag").prop("required",true);
            $("#svrBussRepAssetCreditNonstdInfoDto_repPerpetualClauseType").prop("required",true);
            $("#svrBussRepAssetCreditNonstdInfoDto_repDeferredInterestType").prop("required",true);
            $("#svrBussRepAssetCreditNonstdInfoDto_repPartRedemFlag").prop("required",true);
            //要素添加必填标识 add by tuqirong 2022-01-08 end
        }
        star();
        if(assetTvalueCreditNonstd=='2201'||assetTvalueCreditNonstd=='2202'||assetTvalueCreditNonstd=='1202'||assetTvalueCreditNonstd=='1203'
            ||assetTvalueCreditNonstd=='1204'||assetTvalueCreditNonstd=='1206'||assetTvalueCreditNonstd=='1207'||assetTvalueCreditNonstd=='1208'||
            assetTvalueCreditNonstd=='1209'||assetTvalueCreditNonstd=='1211'||assetTvalueCreditNonstd=='1212'||assetTvalueCreditNonstd=='1213'){
            //收/受益权类型，下拉框选择空白，且置灰不可修改
            $("#svrBussRepAssetCreditNonstdInfoDto_repProfitType").val('').trigger("change");
            $("#svrBussRepAssetCreditNonstdInfoDto_repProfitType").attr("disabled",true);
        }else{
            $("#svrBussRepAssetCreditNonstdInfoDto_repProfitType").attr("disabled",false);
        }
    }

    $(function() {
        // layui.form.on('switch(repIndustryFlag2)',function(){
        //     repIndustryFlagChange2();
        // });
        // repIndustryFlagChange2();
        initAssetCreditNonstd();
        repGuaTypeChangeType();
        byRepContainType();
        floatFactorFlagChangeType();
        getElementById('repFinancierType1').change(function(){
            if(getElementById('repFinancierType1').val()=='01'){
                getElementById('repInstitSize_div').show();
            }else{
                getElementById('repInstitSize_div').val('').trigger("change");
                getElementById('repInstitSize_div').hide();
            }
        });
        /*getElementById('repAssetType').change(function(){
            if(getElementById('repAssetType').val()){
                getElementById('content_one_div').nextAll().show();
                getElementById('repAssetType_div').nextAll().show();
            }else{
                getElementById('content_one_div').nextAll().hide();
                getElementById('repAssetType_div').nextAll().hide();
            }
        });*/
        //行权方式事件 中债新增 20200407 start
        getElementById('repExerciseType').change(function(){
            byRepExerciseType();
        });
        //含权类型事件
        getElementById('repContainType').change(function(){
            byRepContainType();
        });
        //永续条款类型事件
        getElementById('repPerpetualClauseType').change(function(){
            byRepPerpetualClauseType();
        });
        //担保方式事件 中债新增 20200407 end
        getElementById('repGuaType').change(function(){
            byRepGuaType();
        });
        getElementById('repDeferredInterestType').change(function(){
        	byRepDeferredInterestType();
        });
        

         //部分赎回标识 事件
        getElementById('repPartRedemFlag').change(function(){
            repPartRedemFlagChangeType();
        });
        //是否有预期收益率
        getElementById('repRateFlag').change(function(){
            repRateFlagChangeType();
        });
        //项目是否属于重点监控行业和领域
        getElementById('repIndustryFlag').change(function(){
            repIndustryFlagChangeType();
        });

        getElementById('repGuaType').change(function(){
            repGuaTypeChangeType();
        });

        getElementById('floatFactorFlag').change(function(){
            floatFactorFlagChangeType();
        });

        // 融资人2
        // $('#svrBussRepAssetCreditNonstdInfoDto_chbFinancierId').change(function(){
        //     // $("#fundType").val('').trigger('change');
        //     var svrBussRepAssetCreditNonstdInfoDto_chbFinancierId = $('#svrBussRepAssetCreditNonstdInfoDto_chbFinancierId').val();
        //     if(svrBussRepAssetCreditNonstdInfoDto_chbFinancierId ==''|| svrBussRepAssetCreditNonstdInfoDto_chbFinancierId==undefined){
        //         // $('#fundName').val('');
        //     }
        //     else{
        //         var data = {
        //             chbFinancierId: $('#svrBussRepAssetCreditNonstdInfoDto_chbFinancierId').val()
        //         }
        //         //$('#fundName').val($('#finprodId').select2('data')[0].text).trigger('change');
        //         Joyin.ajaxJson(basePath + 'common/select/getFinancierInfo', data,
        //             function (resultObj) {
        //                 //if (resultObj.fundType) {
        //                 // $('#fundName').val(resultObj.fundName).trigger('change');
        //                 // $("#fundType").val(resultObj.fundType).trigger('change');
        //                 //}
        //                 if (resultObj.partyType1!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType1').val(resultObj.partyType1).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType1').val('').trigger('change');
        //                 }
        //                 if (resultObj.partyType2!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType2').val(resultObj.partyType2).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType2').val('').trigger('change');
        //                 }
        //                 if (resultObj.partyType3!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType3').val(resultObj.partyType3).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType3').val('').trigger('change');
        //                 }
        //                 if (resultObj.industry!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repIndustry1').val(resultObj.industry).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repIndustry1').val('').trigger('change');
        //                 }
        //                 if (resultObj.industry2!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_industryLevel2Type').val(resultObj.industry2).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_industryLevel2Type').val('').trigger('change');
        //                 }
        //             });
        //     }
        // });
        //
        // // 融资人
        // $('#svrBussRepAssetCreditNonstdInfoDto_repFinancie').change(function(){
        //     // $("#fundType").val('').trigger('change');
        //     var svrBussRepAssetCreditNonstdInfoDto_repFinancie = $('#svrBussRepAssetCreditNonstdInfoDto_repFinancie').val();
        //     if(svrBussRepAssetCreditNonstdInfoDto_repFinancie ==''|| svrBussRepAssetCreditNonstdInfoDto_repFinancie==undefined){
        //         // $('#fundName').val('');
        //     }
        //     else{
        //         var data = {
        //             chbFinancierId: $('#svrBussRepAssetCreditNonstdInfoDto_repFinancie').val()
        //         };
        //         //$('#fundName').val($('#finprodId').select2('data')[0].text).trigger('change');
        //         Joyin.ajaxJson(basePath + 'common/select/getFinancierInfo', data,
        //             function (resultObj) {
        //                 //if (resultObj.fundType) {
        //                 // $('#fundName').val(resultObj.fundName).trigger('change');
        //                 // $("#fundType").val(resultObj.fundType).trigger('change');
        //                 //}
        //                 if (resultObj.partyType1!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType1').val(resultObj.partyType1).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType1').val('').trigger('change');
        //                 }
        //                 if (resultObj.partyType2!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType2').val(resultObj.partyType2).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType2').val('').trigger('change');
        //                 }
        //                 if (resultObj.partyType3!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType3').val(resultObj.partyType3).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType3').val('').trigger('change');
        //                 }
        //                 if (resultObj.industry!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repIndustry1').val(resultObj.industry).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_repIndustry1').val('').trigger('change');
        //                 }
        //                 if (resultObj.industry2!=null){
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_industryLevel2Type').val(resultObj.industry2).trigger('change');
        //                 } else {
        //                     $('#svrBussRepAssetCreditNonstdInfoDto_industryLevel2Type').val('').trigger('change');
        //                 }
        //             });
        //     }
        // });
    });
}());