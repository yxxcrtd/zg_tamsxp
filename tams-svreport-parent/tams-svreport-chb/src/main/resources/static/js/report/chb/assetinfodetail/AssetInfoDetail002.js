var QueryFunc=function(){
    return {
        serachKey:function(){
        }
    }
}();

(function() {
    /**
     * 画星星
     */
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
    /**
     * 中债报送show/hide
     */
    var showReportInfo = function(){
        var repAssetType = $('#assetType2').val();
        //隐藏所有中债报送信息
        $(".businessDeptClass").hide();
        $(".businessDeptClass").attr('disabled',true);
        var obj = JSON.parse(AssetTypeListLink) ;
        if(obj["01"].indexOf(repAssetType)>=0){
            //本行/他行存款||大额存单
            $("#svrBussRepDepositInfoDto_div").show();
        }else if(obj["02"].indexOf(repAssetType)>=0){
            //拆放同业/买入返售（资产）||同业拆入/卖出回购（负债）
            $("#svrBussRepPlacementInfo_div").show();
        }else if(obj["03"].indexOf(repAssetType)>=0){
            //债券类资产/理财直接融资工具/同业存单
            $("#svrBussFinBondRepInfo_div").show();
        }else if(obj["04"].indexOf(repAssetType)>=0){
            //非标准化债权类资产/信贷资产流转项目
            $("#svrBussRepAssetCreditNonstdInfo_div").show();
        }else if(obj["05"].indexOf(repAssetType)>=0){
            //票据类资产/信用证类资产
            $("#svrBussRepAssetAstBillInfo_div").show();
        }else if(obj["06"].indexOf(repAssetType)>=0){
            //权益类资产
            $("#svrBussRepEquityInfo_div").show();
        }else if(obj["07"].indexOf(repAssetType)>=0){
            //金融衍生品
            $("#svrBussRepFinanciallInfo_div").show();
        }else if(obj["08"].indexOf(repAssetType)>=0){
            //QDII债券资产
            $("#svrBussQDIIBondRepInfo_div").show();
        }else if(obj["09"].indexOf(repAssetType)>=0){
            //QDII拆出/逆回购资产
            $("#svrBussRepQDIIdemolitionInfo_div").show();
        }else if(obj["10"].indexOf(repAssetType)>=0){
            //QDII股票/基金类资产
            $("#svrBussQDIISharesInfo_div").show();
        }else if(obj["11"].indexOf(repAssetType)>=0){
            //QDII结构性票据类资产
            $("#svrBussRepQDIIStructureInfo_div").show();
        }else if(obj["12"].indexOf(repAssetType)>=0){
            //资产管理产品
            $("#svrBussRepAssetMngProdInfo_div").show();
        }else if(obj["13"].indexOf(repAssetType)>=0){
            //商品类资产
            $("#svrBussRepNobleMetalInfo_div").show();
        }else if(obj["14"].indexOf(repAssetType)>=0){
            //各类资产的其他项
            $("#svrBussRepOtherInvestTargetInfo_div").show();
        }else if(obj["16"].indexOf(repAssetType)>=0){
            //委外投资——协议方式
            $("#svrBussRepOutInvestAgrInfo_div").show();
        }else if(obj["17"].indexOf(repAssetType)>=0){
            //其他（负债）类资产
            $("#svrBussRepOtherInfo_div").show();
        }else if(obj["18"].indexOf(repAssetType)>=0){
            //另类资产
            $("#svrBussRepOthKindInfo_div").show();
        }else if(obj["L01"].indexOf(repAssetType)>=0){
            //公募基金/私募基金
            $("#svrBussRepFundInfo_div").show();
        }
        star();
        layui.form.render();
        dateVal();
    }
    /**
     * 主画面嵌子页面的联动
     */
    var changeReport = function(){
        var repAssetType = $('#assetType2').val();
        var obj = JSON.parse(AssetTypeListLink) ;
        if(obj["01"].indexOf(repAssetType)>=0){
            //本行/他行存款||大额存单
            if(repAssetType=='1004'||repAssetType=='1005'){
                //存款类型置灰不可修改
                $("#svrBussRepDepositInfoDto_repDepoType").val('').trigger("change");
                $("#svrBussRepDepositInfoDto_repDepoType").attr("disabled",true);
            }else{
                $("#svrBussRepDepositInfoDto_repDepoType").attr("disabled",false);
            }
            if(repAssetType=='1002'||repAssetType=='1004'){
                //资金存入银行置灰不可修改
                $("#svrBussRepDepositInfoDto_bankName").val('').attr("disabled",true);
            }else{
                $("#svrBussRepDepositInfoDto_bankName").attr("disabled",false);
            }
        }else if(obj["02"].indexOf(repAssetType)>=0){
            //拆放同业/买入返售（资产）||同业拆入/卖出回购（负债）
            if(repAssetType=='1108'||repAssetType=='1902'){
                $("#svrBussRepPlacementInfoDto_targetType1").attr("disabled",false);
                $("#issueAmt").attr("disabled",false);
                $("#svrBussRepPlacementInfoDto_targetType1").val('01').trigger("change");
                $("#svrBussRepPlacementInfoDto_targetType1").prop('required', true);
                $("#targetAmt").attr("disabled",false);

                // var parms={assetuuid:$("#assetUuid").val()}
                // Joyin.ajaxJson(
                //     basePath + "chb/assetinfodetail/AssetInfoDetail002/getValue",parms,
                //     function(result) {
                //         $("#issueAmt").val(result);
                //     }
                // );
            }else if(repAssetType=='1107'||repAssetType=='1901'){
                $("#svrBussRepPlacementInfoDto_targetType1").val('').trigger("change");
                $("#svrBussRepPlacementInfoDto_targetType1").attr("disabled",true);
                $("#issueAmt").attr("disabled",true);
                $("#issueAmt").val('');
                $("#svrBussRepPlacementInfoDto_targetType1").prop('required', false);
                $("#targetAmt").val('').trigger("change");
                $("#targetAmt").attr("disabled",true);
            }
        }else if(obj["03"].indexOf(repAssetType)>=0){
            //债券类资产/理财直接融资工具/同业存单
            if(repAssetType=='1112'){
                $("#svrBussFinBondRepInfoDto_repSectype").remove();
            }
        }else if(obj["04"].indexOf(repAssetType)>=0){
            $("#svrBussRepAssetCreditNonstdInfoDto_repChose").val('无');
            $('#repCreditAssetCode_Div').hide();
            if (repAssetType!="2202") {
                $('#svrBussRepAssetCreditNonstdInfoDto_repCreditAssetCode').val('').trigger('change');
            }
            //非标准化债权类资产/信贷资产流转项目
            if(repAssetType=='2201'||repAssetType=='2202'){
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
                //$("#svrBussRepAssetCreditNonstdInfoDto_repRepayCmt").val('').trigger("change");
                //$("#svrBussRepAssetCreditNonstdInfoDto_repRepayCmt").attr("disabled",true);

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

                if(repAssetType=="2202"){
                    $('#repCreditAssetCode_Div').show();
                }
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
                //  $("#svrBussRepAssetCreditNonstdInfoDto_repRepayCmt").attr("disabled",false);

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
            if(repAssetType=='2201'||repAssetType=='2202'||repAssetType=='1202'||repAssetType=='1203'
                ||repAssetType=='1204'||repAssetType=='1206'||repAssetType=='1207'||repAssetType=='1208'||
                repAssetType=='1209'||repAssetType=='1211'||repAssetType=='1212'||repAssetType=='1213'){
                //收/受益权类型，下拉框选择空白，且置灰不可修改
                $("#svrBussRepAssetCreditNonstdInfoDto_repProfitType").val('').trigger("change");
                $("#svrBussRepAssetCreditNonstdInfoDto_repProfitType").attr("disabled",true);
            }else{
                $("#svrBussRepAssetCreditNonstdInfoDto_repProfitType").attr("disabled",false);
            }


        }else if(obj["05"].indexOf(repAssetType)>=0){
            //票据类资产/信用证类资产
            $("#svrBussRepAssetAstBillInfoDto_privateFlag").val('02').trigger("change");
            if(repAssetType=='1210'){
                //类型默认05置灰不可修改
                $("#svrBussRepAssetAstBillInfoDto_notstandSecType").val('05').attr('disabled', true).trigger('change');
                $("#svrBussRepAssetAstBillInfoDto_content_one_div").hide();
            }else{
                $("#svrBussRepAssetAstBillInfoDto_notstandSecType").val('').trigger("change");
                $("#svrBussRepAssetAstBillInfoDto_notstandSecType").attr("disabled",false);
                $("#svrBussRepAssetAstBillInfoDto_content_one_div").show();
            }
        }else if(obj["06"].indexOf(repAssetType)>=0){
            //权益类资产
            if(repAssetType=='1305'||repAssetType=='1302'){
                $("#svrBussRepEquityInfoDto_equityQuit").val('').trigger("change");
                $("#svrBussRepEquityInfoDto_equityQuit").attr("disabled",true);
            }else if(repAssetType=='1301'){
                $("#svrBussRepEquityInfoDto_equityQuit").attr("disabled",false);
            }
        }else if(obj["07"].indexOf(repAssetType)>=0){
            //金融衍生品
            if(repAssetType!='1403'){
                $("#svrBussRepFinanciallInfoDto_holdPurpose").val('99').trigger("change");
            }else{
                $("#svrBussRepFinanciallInfoDto_holdPurpose").val('').trigger("change");
            }
        }else if(obj["08"].indexOf(repAssetType)>=0){
            //QDII债券资产
        }else if(obj["09"].indexOf(repAssetType)>=0){
            //QDII拆出/逆回购资产
        }else if(obj["10"].indexOf(repAssetType)>=0){
            //QDII股票/基金类资产
        }else if(obj["11"].indexOf(repAssetType)>=0){
            //QDII结构性票据类资产
        }else if(obj["12"].indexOf(repAssetType)>=0){
            //资产管理产品
            if(repAssetType=='1701'||repAssetType=='1703'||
                repAssetType=='1704'||repAssetType=='1706'||repAssetType=='1702'){
                $("#svrBussRepAssetMngProdInfoDto_financialComissue").val('').trigger("change");
                $("#svrBussRepAssetMngProdInfoDto_financialComissue").attr("disabled",true);
                $("#svrBussRepAssetMngProdInfoDto_content_one_div").hide();
            }else{
                $("#svrBussRepAssetMngProdInfoDto_financialComissue").attr("disabled",false);
                $("#svrBussRepAssetMngProdInfoDto_content_one_div").show();
            }

            if(repAssetType=='1705'){
                $("#svrBussRepAssetMngProdInfoDto_financialProductFlag").attr("disabled",false);
                $("#svrBussRepAssetMngProdInfoDto_financialProductFlag").prop("required",true).trigger('change');
            }else{
                $("#svrBussRepAssetMngProdInfoDto_financialProductFlag").attr("disabled",true);
            }
        }else if(obj["13"].indexOf(repAssetType)>=0){
            //商品类资产
        }else if(obj["14"].indexOf(repAssetType)>=0){
            //各类资产的其他项
            $("#svrBussRepOtherInvestTargetInfoDto_relationFinan").trigger("change");
            $("#svrBussRepOtherInvestTargetInfoDto_guaType").trigger("change");
            $("#svrBussRepOtherInvestTargetInfoDto_pledgeValue").trigger("change");
            $("#svrBussRepOtherInvestTargetInfoDto_natureGuarantee").trigger("change");
            $("#svrBussRepOtherInvestTargetInfoDto_deptEquity").trigger("change");
        }else if(obj["16"].indexOf(repAssetType)>=0){
            //委外投资——协议方式
        }else if(obj["17"].indexOf(repAssetType)>=0){
            //其他（负债）类资产
        }else if(obj["18"].indexOf(repAssetType)>=0){
            //另类资产
        }else if(obj["L01"].indexOf(repAssetType)>=0){
            //公募基金/私募基金
            if(repAssetType=='2607'||repAssetType=='2610'||repAssetType=='2613'||
                repAssetType=='2611'||repAssetType=='2612'||repAssetType=='2617'){
                $("#svrBussRepFundInfoDto_isGovernFund").attr("disabled",false);
            }else{
                $("#svrBussRepFundInfoDto_isGovernFund").val('').trigger("change");
                $("#svrBussRepFundInfoDto_isGovernFund").attr("disabled",true);
            }
            if(repAssetType=='1106'||repAssetType=='2401'||repAssetType=='2402'||
                repAssetType=='1303'||repAssetType=='2403'||repAssetType=='2499'){
                //公募
                $("#svrBussRepFundInfoDto_financialComissue").val('').trigger("change");
                $("#svrBussRepFundInfoDto_financialComissue").attr("disabled",true);
                $("#svrBussRepFundInfoDto_profitFlag_div").show();
            }else{
                $("#svrBussRepFundInfoDto_financialComissue").attr("disabled",false);
                $("#svrBussRepFundInfoDto_profitFlag_div").hide();
            }
        }
        star();
        layui.form.render();
    }

    function saveOrSubmit(operateType) {

        var object = $.serializeToObject('#submitForm', true);
        object.operateType = operateType;

        /**【资产基本信息维护】-新建页面（1401、1402、1403、1404、1405、1406、1499），提交报错**/
        object.svrBussRepFinanciallInfoDto.targetType3 = object.svrBussRepFinanciallInfoDto.targetType3?object.svrBussRepFinanciallInfoDto.targetType3.join(","):'';
        object.svrBussRepDepositInfoDto.repTargetType = object.svrBussRepDepositInfoDto.repTargetType?object.svrBussRepDepositInfoDto.repTargetType.join(","):'';
        object.svrBussRepDepositInfoDto.repBankName = object.svrBussRepDepositInfoDto.bankName?object.svrBussRepDepositInfoDto.bankName:'';
        object.svrBussRepDepositInfoDto.repBasis = object.svrBussRepDepositInfoDto.basis?object.svrBussRepDepositInfoDto.basis:'';
        //object.svrBussRepAssetCreditNonstdInfoDto.repFinancie = $("#svrBussRepAssetCreditNonstdInfoDto_repFinancie").select2('data')[0].text;
        object.pageModel = pageModel;

        Joyin.ajaxJson(basePath + 'chb/assetinfodetail/AssetInfoDetail002/check',object,function(result){
            if(result){
                if(operateType === 'save') {
                    WorkFlow.doSaveList(basePath + 'chb/assetinfodetail/AssetInfoDetail002/save', object, "AssetInfoDetail002_btn_save");
                }
                else {
                    WorkFlow.doSubmitList(basePath + 'chb/assetinfodetail/AssetInfoDetail002/save', object, "AssetInfoDetail002_btn_submit");
                }
            }
        })
    }

    /**
     * 最终提交
     */
    var finalSava = function(object,operateType,result){
        Joyin.ajaxJson(basePath + 'invest/asset/common/isHoliday', {mdate:object.mdate,calendarId:object.calendarId}, function(isHoliday){
            if(isHoliday){
                Joyin.confirm("到日期为节假日，是否确认提交", function() {
                    if (operateType === 'save') {
                        WorkFlow.doSaveList(basePath + 'invest/asset/bond/FinBondTargetAsset002/save',
                            object, "FinBondTargetAsset002_btn_save");
                    } else {
                        WorkFlow.doSubmitList(basePath + 'invest/asset/bond/FinBondTargetAsset002/save',
                            object, "FinBondTargetAsset002_btn_submit");
                    }
                })
            }else{
                Joyin.confirm(result.data, function() {
                    if (operateType === 'save') {
                        WorkFlow.doSaveList(basePath + 'invest/asset/bond/FinBondTargetAsset002/save',
                            object, "FinBondTargetAsset002_btn_save");
                    } else {
                        WorkFlow.doSubmitList(basePath + 'invest/asset/bond/FinBondTargetAsset002/save',
                            object, "FinBondTargetAsset002_btn_submit");
                    }
                })
            }
        });
    }



    //控件加载完成后
    // window.componentLoadCallback = function(isDone, pagePath, params){
    //     if(isDone){
    //         if(typeof pageModel !== 'undefined'
    //             && (pageModel === PAGE_MODEL_EDIT || pageModel === PAGE_MODEL_NEW)){
    //             $('.purSpeed').append('<i class="must">*</i>');
    //             $('.redSpeed').append('<i class="must">*</i>');
    //         }
    //         $('#assetType2').trigger('change');
    //     }
    // }
    var init = function(){
        showReportInfo();
    }

    /**
     * 将当前子页面的数据赋值
     */
    var assetVal = function(){
        var repAssetType = $('#assetType2').val();
        $("input[id$='_assetName']").val($('#assetName').val());
        $("input[id$='_assetId']").val($('#assetCode').val());
        if (repAssetType == '1503'){
            $("#svrBussRepQDIIStructureInfoDto_contractName").val($('#assetName').val());
        }else {
            $("#svrBussRepQDIIStructureInfoDto_contractName").val('');
        }
        if(repAssetType=="2202"){
            if($('#assetCode').val().length == 8){
                $("input[id$='_repCreditAssetCode']").val($('#assetCode').val());
            }
        }

    }

    var dateVal = function(){
        $("input[id$='_vdate']").val($('#vdate').val());
        $("input[id$='_mdate']").val($('#mdate').val());

    }

    var poolCode = function(){
        var assetinfo = JSON.parse(PbcCapAssetInfo) ;
        $("#pobcassetid").val(assetinfo[$('#assetCode').val()]).trigger("change");
    }

    var isBankCapVal = function(){
        var isBankCap = $('#isBankCap').val();

        if(isBankCap == '01'){
            $('#bankCapital').attr('disabled',false);
        }else{
            $('#bankCapital').attr('disabled',true);
            $("#bankCapital").val('').trigger("change");
        }
    }

    var isConstracteBusiVal = function(){
        var isConstracteBusi = $('#isConstracteBusi').val();

        if(isConstracteBusi == '01'){
            $('#constracteBusiType').attr('disabled',false);
            $('#constracteGrade').attr('disabled',false);
            $('#constracteHeaverTimes').attr('disabled',false);
        }else{
            $('#constracteBusiType').attr('disabled',true);
            $("#constracteBusiType").val('').trigger("change");
            $('#constracteGrade').attr('disabled',true);
            $("#constracteGrade").val('').trigger("change");
            $('#constracteHeaverTimes').attr('disabled',true);
            $("#constracteHeaverTimes").val('').trigger("change");
        }
    }


    var assetTypeDetail = function () {
        var repAssetType = $('#assetType2').val();
        //是否专项债券
        if(repAssetType == '1110'){
            $('#bankSecZxzq').attr('disabled',false);
        }else{
            $('#bankSecZxzq').attr('disabled',true);
            $("#bankSecZxzq").val('').trigger("change");
        }


        if(repAssetType=="2202"){
            $("input[id$='_repCreditAssetCode']").attr('disabled',false);
        }else{
            $("input[id$='_repCreditAssetCode']").attr('disabled',true);
            $("input[id$='_repCreditAssetCode']").val('').trigger("change");
        }

        //是否银行资本补充工具
        if((repAssetType == '1101'||repAssetType == '1110'||repAssetType == '1109'||
            repAssetType == '1102'||repAssetType == '1111'||repAssetType == '1112'||
            repAssetType == '1113'||repAssetType == '1114'||repAssetType == '1115'||
            repAssetType == '1116'||repAssetType == '1117')){
            $('#isBankCap').attr('disabled',false);
        }else if(repAssetType == '1301'||repAssetType == '1305'||repAssetType == '1302'){
            if($('#svrBussRepEquityInfoDto_sharesType').val() == '02'){
                $('#isBankCap').attr('disabled',false);
            }
        }else {
            $('#isBankCap').attr('disabled',true);
            $("#isBankCap").val('').trigger("change");
        }

        //是否投向金融资产投资公司或其附属机构发行的私募股权投资基金
        if(repAssetType == '2601'||repAssetType == '2602'||repAssetType == '2604'||
            repAssetType == '2605'||repAssetType == '2606'||repAssetType == '2607'||
            repAssetType == '2610'||repAssetType == '2611'||repAssetType == '2612'||
            repAssetType == '2617'||repAssetType == '2613'||repAssetType == '2699'
            ||repAssetType == '2616'||repAssetType == '2618'||repAssetType == '2619'
            ||repAssetType == '2620'||repAssetType == '2614'){
            $('#privatefundFlag').attr('disabled',false);
        }else{
            $('#privatefundFlag').attr('disabled',true);
            $("#privatefundFlag").val('').trigger("change");
        }

        //是否政府出资产业投资基金
        if(repAssetType == '2607'||repAssetType == '2610'||repAssetType == '2611'||
            repAssetType == '2612'||repAssetType == '2613'||repAssetType == '2617'){
            $('#isunlisted').attr('disabled',false);
        }else{
            $('#isunlisted').attr('disabled',true);
            $("#isunlisted").val('').trigger("change");
        }

        //是否投向金融资产投资公司发行的私募资产管理产品
        if(repAssetType == '1701'||repAssetType == '1703'||repAssetType == '1704'||
            repAssetType == '1705'||repAssetType == '1702'||repAssetType == '1706'){
             $('#privateprdFlag').attr('disabled',false);
        }else{
            $('#privateprdFlag').attr('disabled',true);
            $("#privateprdFlag").val('').trigger("change");
        }

        //是否投向地方政府融资平台
        if(repAssetType == '1201'||repAssetType == '1210'||repAssetType == '1202'||
            repAssetType == '1203'||repAssetType == '1204'||repAssetType == '1205'||
            repAssetType == '1206'||repAssetType == '1207'||repAssetType == '1208'||
        repAssetType == '1211'||repAssetType == '1212'||repAssetType == '1213'||
        repAssetType == '1209'||repAssetType == '1299'||repAssetType == '2202'){
            $('#privateFlag').attr('disabled',false);
        }else{
            $('#privateFlag').attr('disabled',true);
            $("#privateFlag").val('').trigger("change");
        }

        //是否投向地方政府融资平台
        if(repAssetType == '1116'||repAssetType == '2299'||repAssetType == '1201'||
            repAssetType == '1203'||repAssetType == '1204'||repAssetType == '1205'||
            repAssetType == '1206'||repAssetType == '1207'||repAssetType == '1208'||
            repAssetType == '1211'||repAssetType == '1212'||repAssetType == '1213'||
            repAssetType == '1209'||repAssetType == '1299'||repAssetType == '2202'||
            repAssetType == '1202'||repAssetType == '1301'||repAssetType == '1399'||
            repAssetType == '2101'||repAssetType == '2617'||repAssetType == '1210'){
            $('#isConstracteBusi').attr('disabled',false);
        }else{
            $('#isConstracteBusi').attr('disabled',true);
            $("#isConstracteBusi").val('').trigger("change");
        }

        var obj = JSON.parse(AssetTypeListLinkTwo) ;
        if(obj["002"].indexOf(repAssetType)>=0||
            obj["004"].indexOf(repAssetType)>=0){
            $('#chbIssuerId').attr('disabled',false);
        }else{
            $('#chbIssuerId').attr('disabled',true);
            $("#chbIssuerId").val('').trigger("change");
        }


    }

    var  initprofitFlag=function(){
        var assetTvalueFund=$('#assetType2').val();
        //公募基金/私募基金
        if(assetTvalueFund=='1106'||assetTvalueFund=='2401'||assetTvalueFund=='1303'||
            assetTvalueFund=='2403'||assetTvalueFund=='2402'||assetTvalueFund=='2499'){
            $("#svrBussRepFundInfoDto_profitFlag").attr("disabled",false);
        }else{
            $("#svrBussRepFundInfoDto_profitFlag").val('').trigger("change");
            $("#svrBussRepFundInfoDto_profitFlag").attr("disabled",true);
        }
    }

    var assetTypeValSet = function(){
        var repAssetType = $('#assetType2').val();
        //隐藏所有中债报送信息
        var obj = JSON.parse(AssetTypeListLinkTwo) ;
        for(var i in obj){
            if(obj[i].indexOf(repAssetType)>=0){
                $("#assetType").val(i).trigger('change');
                break;
            }
        }
    }

    // 置空
    var resetVal = function () {
        // 发行人置空
        $('#svrBussFinBondRepInfoDto_chbIssuerId').val('').trigger('change');
        $('#svrBussFinBondRepInfoDto_repPartyType1').val('').trigger('change');
        $('#svrBussFinBondRepInfoDto_repPartyType2').val('').trigger('change');
        $('#svrBussFinBondRepInfoDto_repPartyType3').val('').trigger('change');
        $('#svrBussFinBondRepInfoDto_repIndustry').val('').trigger('change');
        $('#svrBussFinBondRepInfoDto_repIndustryLevel2').val('').trigger('change');
        // 融资人2置空
        $('#svrBussRepAssetCreditNonstdInfoDto_chbFinancierId').val('').trigger('change');
        $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType1').val('').trigger('change');
        $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType2').val('').trigger('change');
        $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType3').val('').trigger('change');

        // 管理人2置空
        $('#svrBussRepFundInfoDto_chbManagerId').val('').trigger('change');
        $('#svrBussRepFundInfoDto_sharesType1').val('').trigger('change');
        $('#svrBussRepFundInfoDto_sharesType2').val('').trigger('change');
        $('#svrBussRepFundInfoDto_sharesType3').val('').trigger('change');
        // 托管人置空
        $('#svrBussRepAssetMngProdInfoDto_trusteeId').val('').trigger('change');
        // 管理人置空
        $('#svrBussRepAssetMngProdInfoDto_managerId').val('').trigger('change');
        // 融资人置空
        $('#svrBussRepAssetCreditNonstdInfoDto_repFinancie').val('').trigger('change');
        // $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType1').val('').trigger('change');
        // $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType2').val('').trigger('change');
        // $('#svrBussRepAssetCreditNonstdInfoDto_repFinancierType3').val('').trigger('change');
        // $('#svrBussRepAssetCreditNonstdInfoDto_repIndustry1').val('').trigger('change');
        // $('#svrBussRepAssetCreditNonstdInfoDto_industryLevel2Type').val('').trigger('change');
    };

    /**
     * 画面加载完成
     *
     */

    //新增的日期及产品名称赋值..延迟10秒以便保证可以赋值到
    setTimeout(function () {
        dateVal();
        poolCode();
        assetTypeValSet();
        assetVal();

        if(pageModel!=PAGE_MODEL_REFERENCE){

            assetTypeDetail();
            isConstracteBusiVal();

            changeReport();
            isBankCapVal();
            initprofitFlag();
        }
    }, 10000);


    $(function() {


        if(pageModel!=PAGE_MODEL_NEW){
            $("#assetCode").attr('disabled',true);
        }


        $('#svrBussRepEquityInfoDto_sharesType').change(function(){
            assetTypeDetail();
        });

        //隐藏所有中债报送信息
        $(".businessDeptClass").hide();
        init();

        $('#AssetInfoDetail002_btn_save').click(function() {
            saveOrSubmit('save');
        });

        $('#AssetInfoDetail002_btn_submit').click(function() {
            saveOrSubmit('submit');
        });

        $('#assetType2').change(function(){
            //展示中债资产要素
            showReportInfo();
            //assetType2联动子页面的内容
            changeReport();
            assetVal();
            resetVal();
            assetTypeValSet();
            initprofitFlag();
            //新增中债月报要素信息分类配置
            assetTypeDetail();
        });


        $('#isBankCap').change(function(){
            isBankCapVal();
        });

        $('#isConstracteBusi').change(function(){
            isConstracteBusiVal();
        });

        $('#assetCode').change(function(){
            assetVal();
            poolCode();
        });

        $('#assetName').change(function(){
            assetVal();
        });


        $('#vdate').change(function(){
            dateVal();
        });

        $('#mdate').change(function(){
            dateVal();
        });
    });
}());

