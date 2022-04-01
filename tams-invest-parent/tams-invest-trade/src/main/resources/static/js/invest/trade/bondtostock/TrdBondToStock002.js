(function(){
    /**
     * 将资产代码带出的值置灰不可修改
     */
    var unitNum;
    var init = function() {
        // $("#invAim").trigger("change").attr("disabled", true);
        // if ($("#finprodId").val()){
        //     // $("#assetType1").trigger("change").attr("disabled", true);
        //     // $("#assetType10").trigger("change").attr("disabled", true);
        //     // $("#assetType11").trigger("change").attr("disabled", true);
        //     // $("#ccy").trigger("change").attr("disabled", true);
        //     // $("#tradeMarket").trigger("change").attr("disabled", true);
        //     $("#finprodId").trigger('change');
        // }
        //配置单位 edit by yeming 20210726 start
        if(entrustAmtUnit == '01' ){
            $(".unitNum").text('元');
            unitNum = 1;
        }else{
            unitNum = 10000;
        }
//配置单位 edit by yeming 20210726 start
        if($("#finprodId").val() != null && $("#finprodId").val() != ''){
            $("#finprodName").val($('#finprodId').select2('data')[0].text);
            $("#finprodMarketId").val($('#finprodId').select2('data')[0].object.shareId);
            $("#finprodMarketName").val($('#finprodId').select2('data')[0].object.shareName);
            if(!isTranRecord ||isTranRecord == 'N' || isTranRecord == '') {
                $("#conShareId").val($('#finprodId').select2('data')[0].object.conShareId);
                if(!$("#conPrice").val()){
                    $("#conPrice").val($('#finprodId').select2('data')[0].object.conPrice).trigger("blur");
                }
            }
            $("#convdate").val(new Date($('#finprodId').select2('data')[0].object.conVdate).format());
            //股票对应金融产品代码
            $("#shareFinprodId").val($('#finprodId').select2('data')[0].object.shareFinprodId);
            $("#tradeMarket").val($('#finprodId').select2('data')[0].object.tradeMarket);
            $("#ccy").val($('#finprodId').select2('data')[0].object.ccy).trigger("change");
            $("#firstUnitCprice3").val($('#finprodId').select2('data')[0].object.sharePrice).trigger("blur");
            $("#pbondvalue").val($('#finprodId').select2('data')[0].pbondvalue).trigger("blur");
            $("#prec").val($('#finprodId').select2('data')[0].object.prec).trigger("blur");
            $("#preb").val($('#finprodId').select2('data')[0].object.preb).trigger("blur");
            $("#expConValue").val($('#finprodId').select2('data')[0].object.conValue).trigger("blur");
            getPositionInfo();
        }
        $("#tradeAmt").trigger('change');
        if($("#entrustId")&&pageModel==PAGE_MODEL_NEW){
            calTradeAmt();
        }
    };

    //查询持仓信息
    var getPositionInfo =function(){
        //转债代码
        var finprodId = $("#finprodId").val();
        //组合代码
        var portfolioId = $("#portfolioId").val();
        var cdate = $("#tradeDate").val();
        if(finprodId&&portfolioId&&cdate){
            var params={
                "portfolioId":portfolioId,
                "finprodId":finprodId,
                'cdate':$("#tradeDate").val(),
                "tradeDate":$("#tradeDate").val(),
                "acctTypeList":ACCT_TYPE_A01 + "," + ACCT_TYPE_A07,
                "ccy":$("#ccy").val(),
                "invAim":$("#invAim").val(),
                "vdate": $('#tradeDate').val(),
                "secAcctId":$('#secManageAcctId').val(),
                "chlId":$("#chlId").val()
            };
            Joyin.ajax(basePath + 'invest/trade/bondtostock/TrdBondToStock002/getPositionInfo',params,function (ret) {
                if(ret&&ret.data){
                    if(ret.data.holdetrPriceTotal){
                        //配置单位 edit by yeming 20210726 start
                        var holdetrPriceTotal = JoyinMath.doDiv(ret.data.holdetrPriceTotal,unitNum,4,JoyinMath.ROUND_HALF_UP);
                        $("#holdetrPriceTotal").val(holdetrPriceTotal);
                        //配置单位 edit by yeming 20210726 start
                    }
                    else{
                        $("#holdetrPriceTotal").val(0);
                    }
                    $("#holdetrPriceTotal").trigger('blur');
                }
            });

            Joyin.ajaxJson(JoyinUtil.getRealPath('/component/trade/BussTrdAvailableCashQuery/getAvailableCash'), params, function (cashdata) {
                // table.renderRow(data.LAY_TABLE_INDEX, {"assetsavailablepositions": cashdata.position});
                if(cashdata.position) {
                    //配置单位 edit by yeming 20210726 start
                    var conetrPriceTotal = JoyinMath.doDiv(cashdata.position,unitNum,4,JoyinMath.ROUND_HALF_UP);
                    $("#conetrPriceTotal").val(JoyinMath.doMul(conetrPriceTotal, 100));
                    //配置单位 edit by yeming 20210726 start
                }
                else{
                    $("#conetrPriceTotal").val(0);
                }
                $("#conetrPriceTotal").trigger('blur');
            },false);
        }
    };
    var calTradeAmt = function () {
        //转股价格
        var conPrice = $("#conPrice").val();
        // 转股券面总额
        //配置单位 edit by yeming 20210726 start
            var shareAmt = JoyinMath.doMul($("#shareAmt").val(),unitNum,2,JoyinMath.ROUND_HALF_UP);
        //配置单位 edit by yeming 20210726 start
        if(!conPrice || !shareAmt){
            return;
        }
        var actconnum = JoyinMath.doDiv(shareAmt,conPrice,14);
        // 转股数=转股券面总额/转股价格，向下取整
        var conNum = parseInt(actconnum);
        // 零星股数
        var spoShareNum = parseFloat(actconnum) - conNum;
        // 零股折算金额
        $("#conNum").val(conNum);
        //零星股数
        $("#spoShareNum").val(spoShareNum);
        $("#tradeAmt").val(JoyinMath.doMul(spoShareNum,conPrice));
        $("#tradeAmt").trigger("change");
        $("#spoShareNum").trigger("blur");
        $("#conNum").trigger("blur");
        // calStockInfo();
    };

    var calStockInfo = function(){
        //         转股价值= 转股券面总额/转股价格*正股价；
        //         转股溢价率= （转债价格—转股价值）/转股价值*100%；
        //         纯债溢价率=（转债价格—纯债价值）/纯债价值*100%;
        //配置单位 edit by yeming 20210726 start
        var shareAmt = JoyinMath.doMul($("#shareAmt").val(),unitNum,2,JoyinMath.ROUND_HALF_UP);
        //配置单位 edit by yeming 20210726 start
        //正股价
        var firstUnitCprice3 = $("#firstUnitCprice3").val();
        // 转股价
        var conPrice = $("#conPrice").val();
        var data = $('#finprodId').select2('data');
        if(!data){
            return;
        }
        var bondPrice = data['0']['object']['bondPrice'];
        var pbondvalue = data['0']['object']['pbondvalue'];
        if(shareAmt&&firstUnitCprice3&&conPrice){
            //转股价值
            var expConValue = JoyinMath.doMul(JoyinMath.doDiv(shareAmt,conPrice,14),firstUnitCprice3);
            //转股溢价率
            var preb = JoyinMath.doMul(JoyinMath.doDiv(JoyinMath.doMinus(conPrice,expConValue),expConValue),100);
            //转债溢价率
            var prec = JoyinMath.doMul(JoyinMath.doDiv(JoyinMath.doMinus(conPrice,pbondvalue),pbondvalue),100);
            $("#expConValue").val(expConValue).trigger('blur');
            $("#preb").val(preb).trigger('blur');
            $("#prec").val(prec).trigger('blur');
        }
    };
    var getriskdata = function () {
        //配置单位 edit by yeming 20210726 start
        var shareAmt = JoyinMath.doMul($("#shareAmt").val(),unitNum,2,JoyinMath.ROUND_HALF_UP);
        //配置单位 edit by yeming 20210726 start
        var obj = {
            assetCode:$("#finprodId").val(),
            timePoint:MY_SUP_TIME_TYPE_02,
            valDate:$('#tradeDate').val(),
            portfolioCode:$('#portfolioId').val(),
            tradeAmount:shareAmt,
            assetType:PA_TYPE_ZQMC,
            isEnabled:'01',
            tradeDirection:PS_MC
        };
        var finType = 'paBussType';
        var finMng = 'TrdDepositInvestTrade001.finprodType';
        obj.assetType = FINPROD_TYPE_TG_ZQ;
        // add by zhangrui1 交易对手影响风险试算 start
        obj.counterId = $('#trdInvestPersonInfo_counterId').val();
        // add by zhangrui1 交易对手影响风险试算 end
        var object = {valDate:['tradeDate','TrdBondToStock001.tradeDate'],portfolioCode:['portfolioId','TrdBondToStock001.portfolioId'],tradeAmount:['shareAmt','TrdBondToStock001.shareAmt'],assetType:[finType,finMng],tradeDirection:['busiPs','TrdBondToStock002.ps'],data:obj}
        // add by zhangrui1 交易对手影响风险试算 start
        object.counterId = ['counterId', 'TrdInvestPersonInfo.counterId'];
        // add by zhangrui1 交易对手影响风险试算 end
        return object;
    };
    window.componentLoadCallback = function(isDone, pagePath, params){
        if(isDone){
            if(typeof(BaseRiskMathComm) != 'undefined') {
            var riskparam = getriskdata();
            if (pageModel==PAGE_MODEL_NEW){
                riskparam.data =null;
            }
            window.BaseRiskMathComm.getparam(riskparam);
            }
            window.BussTrdTransferOrder['bussTrdTransferOrder'].setStlCommandInput(function () {
                if($('#tradeAmt').val()==''){
                    return Joyin.error("请输入零股折算金额！");
                }
                if($('#settleDate').val()==''){
                    return Joyin.error("请输入现金到账日！");
                }
                return {
                    'isTotalTrade':YES_OR_NO_N
                    ,'bankLendingType':PA_TYPE_ZZG
                    ,'portfolioId':$("#portfolioId").val()
                    ,'deal':{
                        'finprodType2' : FINPROD_TYPE_TG_ZQ,
                        'tradeDate' : $('#settleDate').val(),
                        'settleDate' :$('#settleDate').val(),
                        'tradeMarket':$("#tradeMarket").val(),
                        "invAim":$("#invAim").val(),
                        'ps' : PS_ZZG,
                        'ccy' : $('#ccy').val(),
                        'tradeAmt' : $("#tradeAmt").val(),
                        'bankLendingType' : PA_TYPE_ZZG,
                        "secManageAcctId":$('#secManageAcctId').val(),
                        "chlId":$("#chlId").val(),
                        'counterId' : $("#trdInvestPersonInfo_counterId").val()
                    }
                };
            });
            if( pageModel != PAGE_MODEL_NEW  && $("#tradeAmt").val()&& parseInt($("#tradeAmt").val())!=0){
                window.BussTrdTransferOrder['bussTrdTransferOrder'].saveCalcCondition();
            }
//                updateSecAcctBalance();
//                updateCash();
//             window.BussTrdAvailableCashQuery['bussTrdAvailableCashQuery'].showCash(true);
//             $("#bussTrdAvailableCashQuery_div_channelCash").hide();
//             if(entrustId != ''){
//                 if(etrEntrust){
//                     window.TrdInvestPersonInfo002['trdInvestPersonInfo'].setEntrust(function () {
//                         return etrEntrust;
//                     });
//                 }
//             }
        }
};
    function saveOrSubmit(operateType) {
    	// 划款指令隐藏时，自动生成划款指令信息
    	generateOrders(function () {
	        //---add by 裴家俊 2019/03/25-----start---
	        var s;
            if(typeof(BaseRiskMathComm) != 'undefined') {
	        if(BaseRiskMathComm) {
	            s = BaseRiskMathComm.saveOrSubmitCheck(operateType);
	            if(!s && s != ''){
	                return;
	            }
	            if (window.BaseRiskMathComm.isCalcConditionChanged(getriskdata())){
	                return;
                    }
	            }
	        }
	        var object = $.serializeToObject('#submitForm', true);
	        //---add by 裴家俊 2019/03/25-----end---
	        var needOrder = parseInt($('#tradeAmt').val())!=0;
	        // 检查生成划款指令参数是否发生变化
	        if (needOrder) {
	            if(window.BussTrdTransferOrder['bussTrdTransferOrder'].isCalcConditionChanged()){
	                return;
	            }
	            if(!(object.bussTrdTransferOrder)||object.bussTrdTransferOrder.length==0){
	                return Joyin.error("请生成划款指令信息！");
	            }
	        }
	        object.pageModel = pageModel;
	        object.operateType = operateType;
	        object.shareAmt = JoyinMath.doMul($("#shareAmt").val(),unitNum,2,JoyinMath.ROUND_HALF_UP);
	        // TISHKS-255 可转换面额和持有券面总额 进行单位换算 fuyongsheng add start
            object.conetrPriceTotal = JoyinMath.doMul($("#conetrPriceTotal").val(),unitNum,2,JoyinMath.ROUND_HALF_UP);
            object.holdetrPriceTotal = JoyinMath.doMul($("#holdetrPriceTotal").val(),unitNum,2,JoyinMath.ROUND_HALF_UP);
            // TISHKS-255 可转换面额和持有券面总额 进行单位换算 fuyongsheng add end
            if(FileCommon) {
	            object.uploadFileList = FileCommon.getUploadFileInfo();
	        }
	        //零股折算金额为空时不需要划款指令
	        if(!needOrder){
	            object.bussTrdTransferOrder = null;
	        }
	        if ($('#trdInvestPersonInfo_counterId').select2('data') && $('#trdInvestPersonInfo_counterId').select2('data')[0]) {
	            object.counterId=$('#trdInvestPersonInfo_counterId').select2('data')[0].id;
	        }
	        Joyin.ajaxJson(basePath + 'invest/trade/TrdCommonController/check', object, function(result1) {
	            Joyin.ajaxJson(basePath + 'invest/trade/bondtostock/TrdBondToStock002/check', object, function(result) {
	            	//授信占用额度
//	                Joyin.ajaxJson(basePath + 'invest/trade/TrdCommonController/delLinesDeal',result1.data,function(result2) {});
	            	object.riskCreditListCommonDto = result1.data;
	            	Joyin.confirm(result.data, function() {
	                    if(operateType === 'save') {
	                        WorkFlow.doSaveList(basePath + 'invest/trade/bondtostock/TrdBondToStock002/save', object, "TrdBondToStock002_btn_save");
	                    }
	                    else {
	                        WorkFlow.doSubmitList(basePath + 'invest/trade/bondtostock/TrdBondToStock002/save', object, "TrdBondToStock002_btn_submit");
	                    }
	                });
	            });
	        });
    	});    
	};
    //add by lizhi 2020-2-26 划款指令是否自动生成  start
    var generateOrders = function (callback) {
    	// 如果还款指令设置为隐藏，且页面参数需要自动生成指令（页面存在不生成指令情况）
    	if(GEN_PAY_ORDERS_AUTO =='01'){
    		// 零股折算现金不为0
    		var tradeAmt = $("#tradeAmt").val();
    		if(parseInt(tradeAmt|0) != 0){
    			$('#bussTrdTransferOrder_btn_generate').click();
    		}
    	}
    	// 回调方法
	    if (callback && typeof callback === 'function') {
	        callback();
	    }
    }
    //add by lizhi 2020-2-26 划款指令是否自动生成  end
    $(function () {
        //画面联动，转债代码带出
        $("#finprodId").change(function(){
            var finprodId = $('#finprodId').val();
            if($("#finprodId").val() != null && $("#finprodId").val() != ''){
                $("#finprodName").val($('#finprodId').select2('data')[0].text).trigger("change");
                $("#finprodMarketId").val($('#finprodId').select2('data')[0].object.shareId).trigger("change");
                $("#finprodMarketName").val($('#finprodId').select2('data')[0].object.shareName).trigger("change");
                $("#conShareId").val($('#finprodId').select2('data')[0].object.conShareId).trigger("change");
                //股票对应金融产品代码
                $("#shareFinprodId").val($('#finprodId').select2('data')[0].object.shareFinprodId);
                $("#tradeMarket").val($('#finprodId').select2('data')[0].object.tradeMarket).trigger("change");
                $("#ccy").val($('#finprodId').select2('data')[0].object.ccy).trigger("change");
                $("#convdate").val(new Date($('#finprodId').select2('data')[0].object.conVdate).format()).trigger("change");
                $("#conPrice").val($('#finprodId').select2('data')[0].object.conPrice).trigger("change");
                $("#firstUnitCprice3").val($('#finprodId').select2('data')[0].object.sharePrice).trigger("blur");
                $("#pbondvalue").val($('#finprodId').select2('data')[0].pbondvalue).trigger("blur");
                $("#prec").val($('#finprodId').select2('data')[0].object.prec).trigger("change");
                $("#preb").val($('#finprodId').select2('data')[0].object.preb).trigger("change");
                $("#expConValue").val($('#finprodId').select2('data')[0].object.conValue).trigger("blur");
            }
            else{
                if(pageModel == PAGE_MODEL_NEW){
                    $("#ccy").val('').trigger("change");
                    $("#finprodName").val('').trigger("change");
                    $("#shareId").val('').trigger("change");
                    $("#shareName").val('').trigger("change");
                    $("#tradeMarket").val('').trigger("change");
                    $("#conShareId").val('').trigger("change");
                }
            }
        });
        /**
         * 保存
         */
        $('#TrdBondToStock002_btn_save').click(function() {
            saveOrSubmit('save');
        });
        /**
         * 提交
         */
        $('#TrdBondToStock002_btn_submit').click(function() {
            saveOrSubmit('submit');
        });
        $('#TrdBondToStock002_btn_riskcal').click(function() {
            //window.BaseRiskMathComm.initBaseRiskMathCommPage(getriskdata());
            //-----------add by jjpei 2020-01-07 风险新版本 start----------
            var object = $.serializeToObject('#submitForm', true);
             object.ps=PS_ZZG;
             window.BaseRiskMathComm.initBaseRiskMathRedis(basePath + 'riskrds/interf/doInfData/1/RSK011/F01',object,getriskdata());
            //-----------add by jjpei 2020-01-07 风险新版本 end----------
             window.BaseRiskMathComm.initBaseRiskMathCommPageCus(getriskdata());

        });
        //划款信息生成规则见清算路径文件；当零股折算现金为0时，不显示划款指令信息；
        $("#tradeAmt").change(function () {
            var tradeAmt = $("#tradeAmt").val();
            if(!tradeAmt ||tradeAmt==0){
                 $("#settleDate").attr("disabled",true);
                $("#settleDate").val("");
                $("#bussTrdTransferOrder_div").hide();
            }
            else{
                 $("#settleDate").attr("disabled",false);
                // 系统参数GEN_PAY_ORDERS_AUTO不等于隐藏，才能显示 lizhi 2020-2-27
            	if(GEN_PAY_ORDERS_AUTO != '01'){
            		$("#bussTrdTransferOrder_div").show();
            	}
            }
        });
        /**
         * 证券托管户变化更新资产可用持仓
         */
        $("#secManageAcctId").on("change", function () {
            if ($("#secManageAcctId").val()) {
                $("#chlId").val('').prop("disabled", true);
            }
            else {
                $("#chlId").prop("disabled", false);
            }
            getPositionInfo();

            // 将甲类户对应的交易手续费、结算费、佣金设置为不可编辑
//        controllFeeItem();
        });

        $("#chlId").on("change", function () {
            if ($("#chlId").val()) {
                $("#secManageAcctId").val('').prop("disabled", true);
            }
            else {
                $("#secManageAcctId").prop("disabled", false);
            }
            getPositionInfo();
        });
        $("#portfolioId,#finprodId,#tradeDate,#invAim").change(getPositionInfo);
        $("#portfolioId").change(function () {
            $("#secManageAcctId").val('');
            $("#secManageAcctId").trigger("change");
        });
        $("#shareAmt,#conPrice").change(calTradeAmt);
        init();
        // 是否显示划款指令
        if(GEN_PAY_ORDERS_AUTO=='01'){
        	$('#bussTrdTransferOrder_div').hide() ;
        }else{
        	$('#bussTrdTransferOrder_div').show() ;
        }
    });
}());