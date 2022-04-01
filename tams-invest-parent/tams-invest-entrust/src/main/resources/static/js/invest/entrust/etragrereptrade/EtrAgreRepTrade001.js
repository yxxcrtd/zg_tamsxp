/**
 * 行情和交易信息
 */
(function(){

    var etrData = {};
    //试算结果
    var riskResultdata;
    var cluflag = true;

    /**
     * 返回表单数据
     * @returns
     */
    function getSearchParams() {
        var object = $.serializeToObject('#sumbitForm_hg');

        var acctNo = $("#acctNo").val();
        object.acctNo = acctNo;

        //如果
        var yield = $("#yield").val();
        object.yield = yield;
        //根据指令方向设置质押式回购的金融产品类型
        return object;
    };

    // 双击持仓列表
    var setpositionsItems = function(param){
        doReset(false);
        //交易场所
        $("#tradeMarket").attr("disabled", true);
        etrData.tradeMarket = param.tradeMarket;
        if(param.tradeMarket == '03'){
            etrData.ps = '03';
            $("#psList").val('03');
        }else{
            etrData.ps = '03';
            $("#psList").val('03,04');
        }
        etrData.portfolioId = param.portfolioId;
        etrData.secAcctId = param.secAcctId;
        etrData.repSecId = param.finprodMarketId;
        etrData.repSecNum = param.sellNum;
        setPageItem(etrData);
    };
    // 双击公开行情
    var setunOpenQuotaItems = function(param){
        doReset(false);
        //交易场所
        $("#tradeMarket").attr("disabled", true);
        etrData.tradeMarket = '03';
        param.ps = param.reqType;
        //请求类型 转换委托方向
        if(param.ps == '03'){
            etrData.ps = '04';
            $("#psList").val('04');
        }else{
            etrData.ps = param.ps;
            $("#psList").val('T56,T96,T94,T93,T95');
            //将委托方向中的中途换券去掉
            // $("#psList").val('T56,T96,T93,T95');
        }
        etrData.repPs = BACK_LEND_DIRECTION_04;
        //回购期限
        etrData.term = param.term;
        //回购金额
        etrData.tradeAmt = param.tradeAmt;
        //回购利率
        etrData.yield = param.yield;
        //首期结算日
        etrData.fristSettleDate = param.fristSettleDate;
        //到期结算日
        etrData.expireSettleDate = param.expireSettleDate;
        //占款天数
        etrData.occupyDays =  param.occupyDays;
        //回购利息
        etrData.intAmt =  param.intAmt;
        //到期结算金额
        etrData.expireSettleAmt =  param.expireSettleAmt;
        //质押券
        etrData.repSecId =  param.repSecId;
        //折算比例
        etrData.conRatio =  param.conRatio;
        //质押数量
        etrData.repSecNum =  param.repSecNum;
        //券面金额
        etrData.printAmt =  param.printAmt;
        //新质押券
        etrData.newRepSecId =  param.repSecId;
        //新折算比例
        etrData.newConRatio =  param.conRatio;
        //新质押数量
        etrData.newRepSecNum =  param.repSecNum;
        //新券面金额
        etrData.newPrintAmt =  param.printAmt;
        //交易对手
        etrData.counterId = null;
        //本方交易员
        etrData.trader =  param.traderCode;
        //对手交易员
        etrData.counterTrader =  param.sponsorTraderCode;
        //请求号
        etrData.reqOnId =  param.reqOnId;
        //补充协议
        etrData.addCla=  param.addCla;
        //资金账户
        etrData.acctNo = param.acctNo;
        //续做类型
        etrData.redoType = param.redoType?param.redoType:'0';
        param.tradeMarket = etrData.tradeMarket;
        //成交编号
        etrData.tradeId=  param.tradeId;
        if(param.tradeId){
            //查询成交编号 对应的组合代码，资金账户，以及原交易标的信息
            Joyin.ajaxSync(basePath + 'invest/entrust/etragrereptrade/EtrAgreRepTrade001/getEntrustInfoByTradeId', param, function(resultObj) {
                if(resultObj){
                    etrData.portfolioId = resultObj.portfolioId;
                    etrData.acctNo = resultObj.acctNo;
                    etrData.secAcctId = resultObj.secAcctId;
                    etrData.origRepSecId = resultObj.origRepSecId;
                    etrData.origConRatio = resultObj.origConRatio;
                    etrData.origRepSecNum = resultObj.origRepSecNum;
                    etrData.origPrintAmt = resultObj.origPrintAmt;
                    etrData.counterId = resultObj.counterId;
                }
            });
        }
        if (param.acctNo && param.tradeMarket){
            //查询资金账户、交易市场 对应的组合代码【如果有多个组合，则可编辑】
            Joyin.ajaxSync(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/getPtlSecAcctInfo', param, function(resultObj) {
                var test = "";
                if(resultObj && resultObj.size>0){
                    $.each(resultObj,function(key,val){
                        //回调函数有两个参数,第一个是元素索引,第二个为当前值
                        test = val.parentId ;
                    });
                    etrData.portfolioId = test;
                }
            });
        }
        setPageItem(etrData);

    };
    // 双击在途回购列表
    var setNnWayRepItems = function(param){
        doReset(false);
        //交易场所
        $("#tradeMarket").attr("disabled", true);
        if(param.intiTradeId && !param.tradeId){
            param.tradeId = param.intiTradeId;
        }
        //成交编号不为空
        if(param.tradeId){
            //查询成交编号 对应的组合代码，资金账户，以及原交易标的信息
            Joyin.ajaxSync(basePath + 'invest/entrust/etragrereptrade/EtrAgreRepTrade001/getEntrustInfoByTradeId', param, function(resultObj) {
                if(resultObj){
                    etrData = resultObj;
                }
            });
        }
        etrData.repPs = param.ps;

        if(!etrData.tradeMarket || !etrData.repPs ){
            Joyin.alert("交易场所不能为空");
            return;
        }
        if(!etrData.repPs ){
            Joyin.alert("回购方向不能为空");
            return;
        }
        if(!etrData.expireSettleDate ){
            Joyin.alert("到期结算日 不能为空");
            return;
        }
        // del by yufei 20201023 start 上交所提前购回正逆回购方均可发起，目前上交所只有正回购方能发起
        // if(etrData.tradeMarket == '03' && etrData.repPs == BACK_LEND_DIRECTION_04){
        //     Joyin.alert("上交所只有正回购方可以发起申请");
        //     return;
        // }
        // del by yufei 20201023 end

        var a  = Date.ofDate(etrData.expireSettleDate) - Date.ofDate(iPlatformDate);
        if(etrData.tradeMarket == '03'){
            if(a>0){
                etrData.ps = 'T96';
                $("#psList").val('T96,T94,T93');
                //将委托方向中的中途换券去掉
                // $("#psList").val('T96,T93');
            }else if (a==0){
                etrData.ps = 'T95';
                $("#psList").val('T95,T56,T93');
            }else{
                etrData.ps = 'T93';
                $("#psList").val('T93');
            }
        }else if(etrData.tradeMarket == '04'){
            if(a>0){
                etrData.ps = 'T96';
                $("#psList").val('T96');
            }else{
                etrData.ps = 'T95';
                $("#psList").val('T95,T56');
            }
        }
        etrData.repSecId = etrData.origRepSecId;
        etrData.repSecNum = etrData.origRepSecNum;
        etrData.printAmt = etrData.origPrintAmt;
        etrData.conRatio = etrData.origConRatio;
        etrData.tradeId = param.tradeId;
        setPageItem(etrData);
        pageShowEdit(false);

        if(etrData.tradeMarket == '03' && etrData.ps == 'T96'){
            var fristSettleDate = $("#fristSettleDate").val();
            var expireSettleDate = $("#expireSettleDate").val();
            var term = (Date.ofDate(expireSettleDate) - Date.ofDate(fristSettleDate))/(24*60*60*1000);
            $("#term").val(term == 0 ? 1 : term);
        }
    };


    //双击指令列表
    var setEtrExchangeItems = function(param){
        doReset(false);
        //交易场所
        $("#tradeMarket").attr("disabled", true);
        if(param.entrustId){
            Joyin.ajaxSync(basePath + 'invest/entrust/etragrereptrade/EtrAgreRepTrade001/getEntrustInfoByEntrustId', param, function(resultObj) {
                if(resultObj){
                    etrData = resultObj;
                }
            });
        }
        if(etrData.entrustId){
            $("#psList").val('');
            var a  = Date.ofDate(etrData.expireSettleDate) - Date.ofDate(iPlatformDate);
            var b  = Date.ofDate(etrData.fristSettleDate) - Date.ofDate(iPlatformDate);
            if(etrData.tradeMarket == '03'){
                if(etrData.repPs == '03') {
                    if(etrData.ps == '03') {
                        $("#psList").val('03');
                    }
                    else if((etrData.ps === 'T96' || etrData.ps === 'T94' || etrData.ps === 'T93')
                    && a>0){
                        $("#psList").val('T96,T94,T93');
                    }
                    else if((etrData.ps === 'T95' || etrData.ps === 'T56' || etrData.ps === 'T93')
                    && a==0){
                        $("#psList").val('T95,T56,T93');
                    }
                    //到期续作退回之后指令的到期结算日已经被改变了，使用指令的首次结算日去算
                    else if (etrData.ps === 'T56' && a>0 && b == 0) {
                        $("#psList").val('T95,T56,T93');
                    }
                    //提前购回退回后由于到期结算日已经被改变了，所以特殊处理
                    else if (etrData.ps === 'T96'&& a==0) {
                        $("#psList").val('T96');
                    }
                    else if(etrData.ps === 'T93' && a < 0){
                        $("#psList").val('T93');
                    }
                    else {
                        etrData.ps = null;
                        $("#psList").val('null');
                    }
                }
            }else if(etrData.tradeMarket == '04'){
                if(etrData.ps == '03' || etrData.ps == '04') {
                    $("#psList").val('03,04');
                }
                else if(etrData.ps === 'T96' && a>0){
                    $("#psList").val('T96');
                }
                else if((etrData.ps === 'T95' || etrData.ps === 'T56') && a==0 ) {
                    $("#psList").val('T95,T56');
                }
                //到期续作退回之后指令的到期结算日已经被改变了
                else if (etrData.ps === 'T56' && a>0) {
                    $("#psList").val('T56');
                }
                //提前购回退回后由于到期结算日已经被改变了，所以特殊处理
                else if (etrData.ps === 'T96'&& a==0) {
                    $("#psList").val('T96');
                }
                else {
                    etrData.ps = null;
                    $("#psList").val('null');
                }
            }
            setPageItem(etrData);
        }
    };
    var setPageItem = function(etrData){
        if (!etrData) {
            return;
        }

        if(etrData.redoType){
            $("#redoType").val(etrData.redoType);
        }

        if (etrData.entrustId){
            $("#entrustId").val(etrData.entrustId);
        }
        if(etrData.portfolioId){
            $("#portfolioId").textSelect('setValue', etrData.portfolioId);
        }
        if(etrData.acctNo){
            $("#acctNo").textSelect('setValue', etrData.acctNo);
        }
        if(etrData.secAcctId){
            $("#secAcctId").textSelect('setValue', etrData.secAcctId);
        }
        if(etrData.tradeMarket){
            $("#tradeMarket").textSelect('setValue', etrData.tradeMarket);
        }
        if(etrData.repPs){
            $("#repPs").val(etrData.repPs).trigger('change');
        }
        if(etrData.ps){
            $("#ps").textSelect('setValue', etrData.ps);
        }
        if(etrData.term){
            $("#term").val(etrData.term).trigger('change');
        }
        if(etrData.tradeAmt){
            $("#tradeAmt").val(Number(etrData.tradeAmt).toFixed(2)).trigger('change');
        }
        if(etrData.yield){
            $("#yield").val(Number(etrData.yield).toFixed(4)).trigger('change');
        }
        if(etrData.fristSettleDate){
            $("#fristSettleDate").val(etrData.fristSettleDate).trigger('change');
        }
        if(etrData.expireSettleDate){
            $("#expireSettleDate").val(etrData.expireSettleDate).trigger('change');
        }
        if(etrData.occupyDays){
            $("#occupyDays").val(etrData.occupyDays).trigger('change');
        }
        if(etrData.intAmt){
            $("#intAmt").val(Number(etrData.intAmt).toFixed(2)).trigger('change');
        }
        if(etrData.expireSettleAmt){
            $("#expireSettleAmt").val(Number(etrData.expireSettleAmt).toFixed(2)).trigger('change');
        }
        if(etrData.repSecId){
            $("#repSecId").textSelect('setValue', etrData.repSecId);
        }
        if(etrData.repSecNum){
            $("#repSecNum").val(etrData.repSecNum).trigger('change');
        }
        if(etrData.printAmt){
            $("#printAmt").val(Number(etrData.printAmt).toFixed(2)).trigger('change');
        }
        if(etrData.newRepSecId){
            $("#newRepSecId").textSelect('setValue', etrData.newRepSecId);
        }
        if(etrData.newRepSecNum){
            $("#newRepSecNum").val(etrData.newRepSecNum).trigger('change');
        }
        if(etrData.newPrintAmt){
            $("#newPrintAmt").val(etrData.newPrintAmt).trigger('change');
        }
        if(etrData.newConRatio){
            $("#newConRatio").val(Number(etrData.newConRatio).toFixed(2)).trigger('change');
        }
        if(etrData.origRepSecId){
            $("#origRepSecId").textSelect('setValue', etrData.origRepSecId);
        }
        if(etrData.origRepSecNum){
            $("#origRepSecNum").val(etrData.origRepSecNum).trigger('change');
        }
        if(etrData.origPrintAmt){
            $("#origPrintAmt").val(etrData.origPrintAmt).trigger('change');
        }
        if(etrData.origConRatio){
            $("#origConRatio").val(Number(etrData.origConRatio).toFixed(2)).trigger('change');
        }
        if(etrData.counterId){
            $("#counterId").textSelect('setValue', etrData.counterId);
        }
        if(etrData.trader){
            $("#trader").val(etrData.trader).trigger('change');
        }
        if(etrData.counterTrader){
            $("#counterTrader").val(etrData.counterTrader).trigger('change');
        }
        if(etrData.tradeId){
            $("#tradeId").val(etrData.tradeId).trigger('change');
        }
        if(etrData.reqOnId){
            $("#reqOnId").val(etrData.reqOnId).trigger('change');
        }
        if(etrData.seatId){
            $("#seatId").val(etrData.seatId).trigger('change');
        }
        if(etrData.agreeOnId){
            $("#agreeOnId").val(etrData.agreeOnId).trigger('change');
        }
        if(etrData.intiTradeId){
            $("#intiTradeId").val(etrData.intiTradeId).trigger('change');
        }
        if(etrData.addCla){
            $("#addCla").val(etrData.addCla).trigger('change');
        }
        if(etrData.conRatio){
            $("#conRatio").val(Number(etrData.conRatio).toFixed(2)).trigger('change');
        }
    };

    /**
     * 取消,清空指令数据
     */
    var doReset = function(opt) {
        $("#trader").attr({maxlength:"6"});
        $("#counterTrader").attr({maxlength:"6"});
        //设置指令记录操作类型
        optType = ENTRUST_RECORD_NEW;
        $("#entrustId").val('').trigger('change');
        etrData = {};
        $("#psList").val('03,04');

        if(!opt) {
            $("#portfolioId").val("").trigger('change');
            $("#acctNo").empty();
        }
        $("#secAcctId").textSelect('setValue', '');
        $("#tradeMarket").val('').trigger('change');
        $("#repPs").val('').trigger('change');
        $("#ps").val('').trigger('change');
        $("#term").val('').trigger('change');
        $("#tradeAmt").val('').trigger('change');
        $("#yield").val('').trigger('change');
        $("#fristSettleDate").val('').trigger('change');
        $("#expireSettleDate").val('').trigger('change');
        $("#occupyDays").val('').trigger('change');
        $("#intAmt").val('').trigger('change');
        $("#expireSettleAmt").val('').trigger('change');
        $("#repSecId").val('').trigger('change');
        $("#repSecNum").val('').trigger('change');
        $("#printAmt").val('').trigger('change');
        $("#conRatio").val('').trigger('change');
        $("#newRepSecId").textSelect('setValue', '');
        $("#newRepSecNum").val('').trigger('change');
        $("#newPrintAmt").val('').trigger('change');
        $("#newConRatio").val('').trigger('change');
        $("#origRepSecId").textSelect('setValue', '');
        $("#origRepSecNum").val('').trigger('change');
        $("#origPrintAmt").val('').trigger('change');
        $("#origConRatio").val('').trigger('change');
        $("#counterId").val('').trigger('change');
        $("#trader").val('').trigger('change');
        $("#counterTrader").val('').trigger('change');
        $("#tradeId").val('').trigger('change');
        $("#reqOnId").val('').trigger('change');
        $("#seatId").val('').trigger('change');
        $("#agreeOnId").val('').trigger('change');
        $("#intiTradeId").val('').trigger('change');
        $("#addCla").val('').trigger('change');
        $("#finprodId").val('');
        $("#pledgeFaceValue").val('');
        $("#newfinprodId").val('');
        $("#newpledgeFaceValue").val('');
        $("#acctTradeMarket").val('');
        //清空风险试算结果
        riskResultdata = null;
        cluflag = true;
    }

    /**
     * 风险试算URL
     * @returns
     */
    var getRiskInterfUrl = function () {
        var riskInterfUrl = basePath +  'riskrds/interf/doInfData/0/RSK002/F07';
        return riskInterfUrl;
    }


    /**
     * 组合代码变更时，联动其他字段
     * 组合代码变更时会联动资金账户，此处会自动调用资金账户的联动事件。
     */
    var portfolioIdChange = function(init) {
        linkAcctNo(init);
        // GZYHTZJY-143:修改查询证券账户传参 fuyongsheng 20211101 edit start
        linkSecAcctId();
        // GZYHTZJY-143:修改查询证券账户传参 fuyongsheng 20211101 edit end
        showCashData();
    };



    /**
     * 资金账户选择之后的联动事件
     */
    var acctNoChange = function(init) {
        var acctNo = $("#acctNo").val();
        var secAcctId = $("#secAcctId").val();
//        if(!secAcctId){
            linkSecAcctId();
//        }
        showCashData();
    };
    /**
     * 根据参数获取证券代码对应的Object属性
     * secId:证券代码
     */
    var getSecIdSelectObjectData = function(secId) {
        var result = {};
        var param = {secId:secId};
        Joyin.ajaxSync(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/getFinprodMarketInfo', param, function(page) {
            if(page && page.root && page.root.length > 0) {
                result = page.root[0].object || {};
            }
        });
        return result;
    };
    /**
     * 根据参数获取资金账户下拉框的值
     * portfolioId:组合代码
     * acctNo:资金账户
     */
    var getAcctNoSelectList = function(portfolioId, acctNo,tradeMarket) {
        var result = null;
        var param = {portfolioId:portfolioId, acctNo:acctNo,tradeMarket:tradeMarket};
        Joyin.ajaxSync(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/getPtlSecAcctInfo', param, function(list) {
            result = list;
        });
        return result;
    };
    /**
     * 联动资金账户下拉框。
     */
    var linkAcctNo = function(init) {
        var portfolioId = $("#portfolioId").val();
       var tradeMarket = $("#tradeMarket").val();
        var param = {portfolioId:portfolioId/*, tradeMarket:tradeMarket*/};
        if(portfolioId) {
            var list = getAcctNoSelectList(portfolioId, null,tradeMarket);
            var acctNo = '';
            if(list && list.length > 0) {
                var acctNo = list[0].id;
                if(list && list.length == 1) {
                    $("#acctNo_div").hide();
                    if(!init) {
                        $("#acctNo").textSelect('setValue', acctNo);
                    }
                }
                else {
                    if(!init) {
                        $("#acctNo").textSelect('setValue', acctNo);
                    }
                    $("#acctNo_div").show();
                }
            }
            else {
                Joyin.error("当前组合未绑定券商资金账号，无法下投资指令");
                $("#acctNo").val('').trigger('change');
                $("#acctNo_div" ).show();
            }
        }
        else {
            $("#acctNo" ).val('').trigger('change');
            $("#acctNo_div"  ).show();
        }
    };
    /**
     * 联动资金账户下拉框显示隐藏。
     */
    var linkAcctNoCtrl = function() {
        var portfolioId = $("#portfolioId").val();
        var tradeMarket = $("#tradeMarket").val();
        var param = {portfolioId:portfolioId};
        if(portfolioId) {
            var list = getAcctNoSelectList(portfolioId, null, tradeMarket);
            if (list && list.length > 0) {
                if (list && list.length == 1) {
                    $("#acctNo_div").hide();
                    return;
                }
            }
        }
        $("#acctNo_div").show();
    };
        /**
         * 交易场所变更时
         */
        var tradeMarketChange = function() {
            var tradeMarket = $("#tradeMarket").val();
            var attr = $("#tradeMarket").attr("disabled");
            if(tradeMarket && !attr && tradeMarket == '03'){
                $("#psList").val('03');
                $("#ps").val('').trigger('change');
                $("#repSecId").val('').trigger('change');
            }else if(tradeMarket && !attr && tradeMarket == '04'){
                $("#psList").val('03,04');
                $("#ps").val('').trigger('change');
                $("#repSecId").val('').trigger('change');
            }else if(!tradeMarket && !attr){
                $("#psList").val('03,04');
                $("#ps").val('').trigger('change');
                $("#repSecId").val('').trigger('change');
            }
            // GZYHTZJY-143:修改查询证券账户传参 fuyongsheng 20211101 edit start
            linkSecAcctId();
            // GZYHTZJY-143:修改查询证券账户传参 fuyongsheng 20211101 edit end
            linkAcctNo(false);
            pageShowEdit(false);
            $("#acctTradeMarket").val(tradeMarket);
            removeMustOfRepSecId();
        };
        var settleDateChange = function() {
            //首期结算日
            var fristSettleDate = $("#fristSettleDate").val();
            //到期结算日
            var expireSettleDate = $("#expireSettleDate").val();
            if(fristSettleDate && expireSettleDate){
                var days = (Date.ofDate(expireSettleDate) - Date.ofDate(fristSettleDate))/(24*60*60*1000);
                $("#occupyDays").val(days).trigger('change');
            }

        };
        var termChange = function() {
            //首期结算日
            var fristSettleDate = $("#fristSettleDate").val();
            //到期结算日
            var term = $("#term").val();
            var tradeMarket = $("#tradeMarket").val();
            var param = {fristSettleDate:fristSettleDate,term:term,tradeMarket:tradeMarket};
            if(fristSettleDate && term && tradeMarket){
                Joyin.ajaxSync(basePath +   'invest/entrust/etragrereptrade/EtrAgreRepTrade001/addDate', param, function(obj) {
                    $("#expireSettleDate").val(obj.expireSettleDate).trigger('change');
                    $("#mdate").val(obj.mdate);
                });
            }

        };
        var psChange = function() {
            var ps =  $("#ps").val();
            if(ps == "03"){
                $("#repPs").val(BACK_LEND_DIRECTION_03);
            }else if (ps == "04"){
                $("#repPs").val(BACK_LEND_DIRECTION_04);
            }
            pageShowEdit(false);
            var tradeMarket = $("#tradeMarket").val();
            if(tradeMarket == '03' && ps == 'T56'){
                $("#term").trigger("change");
            }

            //预防ZGJYXTO-1234 问题：选择到期续作修改了期限和回购金额之后，在切回到期购回或者解除质押，页面值没有还原的问题
            if((ps== 'T93' || ps == 'T95') && $("#tradeId").val()) {
                //查询成交编号 对应的组合代码，资金账户，以及原交易标的信息
                var param = {
                    tradeId:$("#tradeId").val()
                };
                Joyin.ajaxSync(basePath + 'invest/entrust/etragrereptrade/EtrAgreRepTrade001/getEntrustInfoByTradeId', param, function(resultObj) {
                    if(resultObj){
                        $("#term").val(resultObj.term).trigger("change");
                        $("#tradeAmt").val(resultObj.tradeAmt).trigger("change");
                        $("#yield").val(resultObj.yield).trigger("change");
                        $("#counterId").textSelect('setValue', etrData.counterId);
                    }
                });
            }

            removeMustOfRepSecId();
        };
        var amtCal = function() {
            var occupyDays = $("#occupyDays").val();
            var tradeAmt = $("#tradeAmt").val();
            var y = $("#yield").val();
            if(occupyDays && tradeAmt && y){
                var intamt = Math.round(tradeAmt*y/100*occupyDays/365*100)/100;
                var expireSettleAmt =  parseFloat(intamt) + parseFloat(tradeAmt);
                $("#intAmt").val(intamt);
                $("#expireSettleAmt").val(expireSettleAmt);
            }
        };
        var printAmtCal = function() {
            var repSecId = $("#repSecId").val();
            var object = getSecIdSelectObjectData(repSecId);
            var repSecNum = $("#repSecNum").val();
            if(repSecId && object.finprodId){
                $("#finprodId").val(object.finprodId).trigger('change');
                var param = {repSecId:object.finprodId,iPlatformDate:iPlatformDate};
                Joyin.ajaxSync(basePath +   'invest/entrust/etragrereptrade/EtrAgreRepTrade001/getFaceValue', param, function(obj) {
                    if(obj && obj != '' && repSecNum){
                        $("#printAmt").val(obj*repSecNum).trigger('change');
                        $("#pledgeFaceValue").val(obj).trigger('change');
                    }
                });
            }else{
                //质押券清空的时候，折算比例、质押数量、券面金额也要一起清空
                if(!repSecId){
                    $('#conRatio').val("");
                    $('#repSecNum').val("");
                    $('#printAmt').val("");
                    $("#finprodId").val('');
                }
            }
        };
        var newprintAmtCal = function() {
            var repSecId = $("#newRepSecId").val();
            var object = getSecIdSelectObjectData(repSecId);
            var repSecNum = $("#newRepSecNum").val();

            if(repSecId && repSecNum && object.finprodId){
                $("#newfinprodId").val(object.finprodId).trigger('change');
                var param = {repSecId:object.finprodId,iPlatformDate:iPlatformDate};
                Joyin.ajaxSync(basePath +   'invest/entrust/etragrereptrade/EtrAgreRepTrade001/getFaceValue', param, function(obj) {
                    if(obj && obj != ''){
                        $("#newPrintAmt").val(obj*repSecNum).trigger('change');
                        $("#newpledgeFaceValue").val(obj).trigger('change');
                    }
                });

            }
        };
        var conRatioCal = function() {
            var printAmt = $("#printAmt").val();
            var newPrintAmt = $("#newPrintAmt").val();
            var tradeAmt = $("#tradeAmt").val();

            if(printAmt && tradeAmt ){
                if(printAmt == 0) {
                    $("#conRatio").val(0);
                }
                else {
                    $("#conRatio").val(Math.round(tradeAmt/printAmt*100*100)/100);
                }

            }
            if(newPrintAmt && tradeAmt ){
                if(newPrintAmt == 0) {
                    $("#newConRatio").val(0);
                }
                else {
                    $("#newConRatio").val((Math.round(tradeAmt/newPrintAmt*100*100)/100).toFixed(2));
                }
            }
        };

        var repSecNumCal = function() {
            var tradeAmt = $("#tradeAmt").val();
            var conRatio = $("#conRatio").val();
            var repSecId = $("#repSecId").val();
            if(repSecId) {
                var object = getSecIdSelectObjectData(repSecId);
                if(object.finprodId && conRatio != 0 && tradeAmt ){
                    $("#finprodId").val(object.finprodId).trigger('change');
                    var param = {repSecId:object.finprodId,iPlatformDate:iPlatformDate};
                    Joyin.ajaxSync(basePath +   'invest/entrust/etragrereptrade/EtrAgreRepTrade001/getFaceValue', param, function(obj) {
                        if(obj && obj != ''){
                            if(obj == 0) {
                                $("#repSecNum").val(0);
                                $("#printAmt").val(0);
                            }
                            else {
                                $("#repSecNum").val(Math.round(tradeAmt * 100 / conRatio / obj));
                                $("#printAmt").val(Math.round(tradeAmt * 100 / conRatio));
                            }
                            $("#pledgeFaceValue").val(obj).trigger('change');
                        }
                    });
                }
            }
        }
        var pageShowEdit= function(init) {
            var tradeMarket =  $("#tradeMarket").val();
            var ps =  $("#ps").val();
            var repPs =  $("#repPs").val();
            //回购期限
            $("#term_div").show();
            //回购利率
            $("#yield_div").show();
            //首期结算日
            $("#fristSettleDate_div").show();
            //到期结算日
            $("#expireSettleDate_div").show();
            //占款天数
            $("#occupyDays_div").show();
            //回购利息
            $("#intAmt_div").show();
            //到期结算金额
            $("#expireSettleAmt_div").show();
            //质押券
            $("#repSecId_div").show();
            //折算比例
            $("#conRatio_div").show();
            //质押数量
            $("#repSecNum_div").show();
            //券面金额
            $("#printAmt_div").show();
            //本方交易员
            $("#trader_div").show();
            //对手交易员
            $("#counterTrader_div").show();
            if(!tradeMarket || !ps){
                //新质押券
                $("#newRepSecId_div").hide();
                //新折算比例
                $("#newConRatio_div").hide();
                //新质押数量
                $("#newRepSecNum_div").hide();
                //新券面金额
                $("#newPrintAmt_div").hide();
                //原质押券
                $("#origRepSecId_div").hide();
                //原折算比例
                $("#origConRatio_div").hide();
                //原质押数量
                $("#origRepSecNum_div").hide();
                //原券面金额
                $("#origPrintAmt_div").hide();
                //成交编号
                $("#tradeId_div").hide();
                //请求号
                $("#reqOnId_div").hide();
                //对手席位
                $("#seatId_div").hide();
                //约定号
                $("#agreeOnId_div").hide();
                //初始交易编号
                $("#intiTradeId_div").hide();
                //补充协议
                $("#addCla_div").hide();
                //回购期限
                $("#term").attr("disabled", true);
                //回购金额
                $("#tradeAmt").attr("disabled", true);
                //回购利率
                $("#yield").attr("disabled", true);
                //首期结算日
                $("#fristSettleDate").attr("disabled", true);
                //到期结算日
                $("#expireSettleDate").attr("disabled", true);
                //占款天数
                $("#occupyDays").attr("disabled", true);
                //回购利息
                $("#intAmt").attr("disabled", true);
                //到期结算金额
                $("#expireSettleAmt").attr("disabled", true);
                //质押券
                $("#repSecId").attr("disabled", true);
                //折算比例
                $("#conRatio").attr("disabled", true);
                //质押数量
                $("#repSecNum").attr("disabled", true);
                //券面金额
                $("#printAmt").attr("disabled", true);
                //交易对手
                $("#counterId").attr("disabled", true);
                //本方交易员
                $("#trader").attr("disabled", true);
                //对手交易员
                $("#counterTrader").attr("disabled", true);
                //组合名称
                $("#portfolioId").attr("disabled", false);
                //资金账号
                $("#acctNo").attr("disabled", false);
                //交易场所
                $("#tradeMarket").attr("disabled", false);
                //委托方向
                $("#ps").attr("disabled", false);
            } else if(tradeMarket == '03' || tradeMarket == '04'){
                if(ps == '03'){
                    //组合名称
                    $("#portfolioId").attr("disabled", false);
                    //资金账号
                    $("#acctNo").attr("disabled", false);
                    //交易场所
                    $("#tradeMarket").attr("disabled", false);
                    //委托方向
                    $("#ps").attr("disabled", false);
                    //回购期限
                    $("#term").attr("disabled", false);
                    //回购金额
                    $("#tradeAmt").attr("disabled", false);
                    //回购利率
                    $("#yield").attr("disabled", false);
                    //首期结算日
                    if(init){
                        $("#fristSettleDate").attr("disabled", true);
                    }else{
                        $("#fristSettleDate").val(iPlatformDate).attr("disabled", true).trigger('change');
                    }
                    //到期结算日
                    $("#expireSettleDate").attr("disabled", true);
                    //占款天数
                    $("#occupyDays").attr("disabled", true);
                    //回购利息
                    $("#intAmt").attr("disabled", true);
                    //到期结算金额
                    $("#expireSettleAmt").attr("disabled", true);
                    //质押券
                    $("#repSecId").attr("disabled", false);
                    //折算比例
                    $("#conRatio").attr("disabled", false);
                    //质押数量
                    $("#repSecNum").attr("disabled", false);
                    //券面金额
                    $("#printAmt").attr("disabled", true);
                    //交易对手
                    $("#counterId").attr("disabled", false);
                    //本方交易员
                    $("#trader").attr("disabled", false);
                    //对手交易员
                    $("#counterTrader").attr("disabled", false);
                    //新质押券
                    $("#newRepSecId_div").hide();
                    //新折算比例
                    $("#newConRatio_div").hide();
                    //新质押数量
                    $("#newRepSecNum_div").hide();
                    //新券面金额
                    $("#newPrintAmt_div").hide();
                    //原质押券
                    $("#origRepSecId_div").hide();
                    //原折算比例
                    $("#origConRatio_div").hide();
                    //原质押数量
                    $("#origRepSecNum_div").hide();
                    //原券面金额
                    $("#origPrintAmt_div").hide();
                    //成交编号
                    $("#tradeId_div").hide();
                    //请求号
                    $("#reqOnId_div").hide();
                    //对手席位
                    $("#seatId_div").hide();
                    //约定号
                    $("#agreeOnId_div").hide();
                    //初始交易编号
                    $("#intiTradeId_div").hide();
                    //补充协议
                    $("#addCla_div").show();
                    $("#addCla").attr("disabled", false);

                }else if(ps == '04') {
                    //组合名称
                    $("#portfolioId").attr("disabled", false);
                    //资金账号
                    $("#acctNo").attr("disabled", false);
                    //交易场所
                    $("#tradeMarket").attr("disabled", true);
                    //委托方向
                    $("#ps").attr("disabled", true);
                    //回购期限
                    $("#term").attr("disabled", true);
                    //回购金额
                    $("#tradeAmt").attr("disabled", true);
                    //回购利率
                    $("#yield").attr("disabled", true);
                    //首期结算日
                    $("#fristSettleDate").attr("disabled", true);
                    //到期结算日
                    $("#expireSettleDate").attr("disabled", true);
                    //占款天数
                    $("#occupyDays").attr("disabled", true);
                    //回购利息
                    $("#intAmt").attr("disabled", true);
                    //到期结算金额
                    $("#expireSettleAmt").attr("disabled", true);
                    //质押券
                    $("#repSecId").attr("disabled", true);
                    //折算比例
                    $("#conRatio").attr("disabled", true);
                    //质押数量
                    $("#repSecNum").attr("disabled", true);
                    //券面金额
                    $("#printAmt").attr("disabled", true);
                    //交易对手
                    $("#counterId").attr("disabled", false);
                    //本方交易员
                    $("#trader").attr("disabled", true);
                    //对手交易员
                    $("#counterTrader").attr("disabled", true);
                    //新质押券
                    $("#newRepSecId_div").hide();
                    //新折算比例
                    $("#newConRatio_div").hide();
                    //新质押数量
                    $("#newRepSecNum_div").hide();
                    //新券面金额
                    $("#newPrintAmt_div").hide();
                    //原质押券
                    $("#origRepSecId_div").hide();
                    //原折算比例
                    $("#origConRatio_div").hide();
                    //原质押数量
                    $("#origRepSecNum_div").hide();
                    //原券面金额
                    $("#origPrintAmt_div").hide();
                    //成交编号
                    $("#tradeId_div").hide();
                    //请求号
                    $("#reqOnId_div").show();
                    $("#reqOnId").attr("disabled", true);
                    //对手席位
                    $("#seatId_div").hide();
                    //约定号
                    $("#agreeOnId_div").hide();
                    //初始交易编号
                    $("#intiTradeId_div").hide();
                    //补充协议
                    $("#addCla_div").show();
                    $("#addCla").attr("disabled", false);
                }else if(ps == 'T96') {
                    if(repPs == BACK_LEND_DIRECTION_03){
                        //组合名称
                        $("#portfolioId").attr("disabled", true);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", false);
                        //回购期限
                        $("#term").attr("disabled", true);
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield").attr("disabled", false);
                        if(init){
                            //首期结算日
                            $("#fristSettleDate").attr("disabled", true);
                            //到期结算日
                            $("#expireSettleDate").attr("disabled", true);
                        }else{
                            //首期结算日
                            $("#fristSettleDate").val(etrData.fristSettleDate).attr("disabled", true).trigger('change');
                            //到期结算日
                            $("#expireSettleDate").val(iPlatformDate).attr("disabled", true).trigger('change');
                        }
                        //占款天数
                        $("#occupyDays").attr("disabled", true);
                        //回购利息
                        $("#intAmt").attr("disabled", true);
                        //到期结算金额
                        $("#expireSettleAmt").attr("disabled", true);
                        //质押券
                        $("#repSecId").attr("disabled", true);
                        //折算比例
                        $("#conRatio").attr("disabled", true);
                        //质押数量
                        $("#repSecNum").attr("disabled", true);
                        //券面金额
                        $("#printAmt").attr("disabled", true);
                        //交易对手
                        $("#counterId").attr("disabled", true);
                        //本方交易员
                        $("#trader").attr("disabled", false);
                        //对手交易员
                        $("#counterTrader").attr("disabled", false);
                        //新质押券
                        $("#newRepSecId_div").hide();
                        //新折算比例
                        $("#newConRatio_div").hide();
                        //新质押数量
                        $("#newRepSecNum_div").hide();
                        //新券面金额
                        $("#newPrintAmt_div").hide();
                        //原质押券
                        $("#origRepSecId_div").hide();
                        //原折算比例
                        $("#origConRatio_div").hide();
                        //原质押数量
                        $("#origRepSecNum_div").hide();
                        //原券面金额
                        $("#origPrintAmt_div").hide();
                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").hide();
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }else if(repPs == BACK_LEND_DIRECTION_04){
                        //组合名称
                        $("#portfolioId").attr("disabled", false);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", true);
                        //回购期限
                        $("#term").attr("disabled", true);
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield").attr("disabled", true);
                        //首期结算日
                        $("#fristSettleDate").attr("disabled", true);
                        //到期结算日
                        $("#expireSettleDate").attr("disabled", true);
                        //占款天数
                        $("#occupyDays").attr("disabled", true);
                        //回购利息
                        $("#intAmt").attr("disabled", true);
                        //到期结算金额
                        $("#expireSettleAmt").attr("disabled", true);
                        //质押券
                        $("#repSecId").attr("disabled", true);
                        //折算比例
                        $("#conRatio").attr("disabled", true);
                        //质押数量
                        $("#repSecNum").attr("disabled", true);
                        //券面金额
                        $("#printAmt").attr("disabled", true);
                        //交易对手
                        $("#counterId").attr("disabled", true);
                        //本方交易员
                        $("#trader").attr("disabled", true);
                        //对手交易员
                        $("#counterTrader").attr("disabled", true);
                        //新质押券
                        $("#newRepSecId_div").hide();
                        //新折算比例
                        $("#newConRatio_div").hide();
                        //新质押数量
                        $("#newRepSecNum_div").hide();
                        //新券面金额
                        $("#newPrintAmt_div").hide();
                        //原质押券
                        $("#origRepSecId_div").hide();
                        //原折算比例
                        $("#origConRatio_div").hide();
                        //原质押数量
                        $("#origRepSecNum_div").hide();
                        //原券面金额
                        $("#origPrintAmt_div").hide();
                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").show();
                        $("#reqOnId").attr("disabled", true);
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }
                }else if(ps == 'T95') {
                    if(repPs == BACK_LEND_DIRECTION_03){
                        //组合名称
                        $("#portfolioId").attr("disabled", true);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", false);
                        //回购期限
                        $("#term").attr("disabled", true);
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield").attr("disabled", true);
                        if(init){
                            //首期结算日
                            $("#fristSettleDate").attr("disabled", true);
                            //到期结算日
                            $("#expireSettleDate").attr("disabled", true);

                        }else{
                            //首期结算日
                            $("#fristSettleDate").val(etrData.fristSettleDate).attr("disabled", true).trigger('change');
                            //到期结算日
                            $("#expireSettleDate").val(etrData.expireSettleDate).attr("disabled", true).trigger('change');
                        }
                        //占款天数
                        $("#occupyDays").attr("disabled", true);
                        //回购利息
                        $("#intAmt").attr("disabled", true);
                        //到期结算金额
                        $("#expireSettleAmt").attr("disabled", true);
                        //质押券
                        $("#repSecId").attr("disabled", true);
                        //折算比例
                        $("#conRatio").attr("disabled", true);
                        //质押数量
                        $("#repSecNum").attr("disabled", true);
                        //券面金额
                        $("#printAmt").attr("disabled", true);
                        //交易对手
                        $("#counterId").attr("disabled", true);
                        //本方交易员
                        $("#trader").attr("disabled", false);
                        //对手交易员
                        $("#counterTrader").attr("disabled", false);
                        //新质押券
                        $("#newRepSecId_div").hide();
                        //新折算比例
                        $("#newConRatio_div").hide();
                        //新质押数量
                        $("#newRepSecNum_div").hide();
                        //新券面金额
                        $("#newPrintAmt_div").hide();
                        //原质押券
                        $("#origRepSecId_div").hide();
                        //原折算比例
                        $("#origConRatio_div").hide();
                        //原质押数量
                        $("#origRepSecNum_div").hide();
                        //原券面金额
                        $("#origPrintAmt_div").hide();
                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").hide();
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }else if(repPs == BACK_LEND_DIRECTION_04){
                        //组合名称
                        $("#portfolioId").attr("disabled", false);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", true);
                        //回购期限
                        $("#term").attr("disabled", true);
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield").attr("disabled", true);
                        //首期结算日
                        $("#fristSettleDate").attr("disabled", true);
                        //到期结算日
                        $("#expireSettleDate").attr("disabled", true);
                        //占款天数
                        $("#occupyDays").attr("disabled", true);
                        //回购利息
                        $("#intAmt").attr("disabled", true);
                        //到期结算金额
                        $("#expireSettleAmt").attr("disabled", true);
                        //质押券
                        $("#repSecId").attr("disabled", true);
                        //折算比例
                        $("#conRatio").attr("disabled", true);
                        //质押数量
                        $("#repSecNum").attr("disabled", true);
                        //券面金额
                        $("#printAmt").attr("disabled", true);
                        //交易对手
                        $("#counterId").attr("disabled", true);
                        //本方交易员
                        $("#trader").attr("disabled", true);
                        //对手交易员
                        $("#counterTrader").attr("disabled", true);
                        //新质押券
                        $("#newRepSecId_div").hide();
                        //新折算比例
                        $("#newConRatio_div").hide();
                        //新质押数量
                        $("#newRepSecNum_div").hide();
                        //新券面金额
                        $("#newPrintAmt_div").hide();
                        //原质押券
                        $("#origRepSecId_div").hide();
                        //原折算比例
                        $("#origConRatio_div").hide();
                        //原质押数量
                        $("#origRepSecNum_div").hide();
                        //原券面金额
                        $("#origPrintAmt_div").hide();
                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").show();
                        $("#reqOnId").attr("disabled", true);
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }
                }else if(ps == 'T56') {
                    if(repPs == BACK_LEND_DIRECTION_03){
                        //组合名称
                        $("#portfolioId").attr("disabled", true);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", false);
                        //回购期限
                        $("#term").attr("disabled", false);
                        //回购金额
                        $("#tradeAmt").attr("disabled", false);
                        //回购利率
                        $("#yield").attr("disabled", false);
                        if(init){
                            //首期结算日
                            $("#fristSettleDate").attr("disabled", true);
                        }else{
                            //首期结算日
                            $("#fristSettleDate").val(iPlatformDate).attr("disabled", true).trigger('change');
                        }
                        //到期结算日
                        $("#expireSettleDate").attr("disabled", true);
                        //占款天数
                        $("#occupyDays").attr("disabled", true);
                        //回购利息
                        $("#intAmt").attr("disabled", true);
                        //到期结算金额
                        $("#expireSettleAmt").attr("disabled", true);
                        //质押券
                        $("#repSecId").attr("disabled", true);
                        //折算比例
                        $("#conRatio").attr("disabled", true);
                        //质押数量
                        $("#repSecNum").attr("disabled", true);
                        //券面金额
                        $("#printAmt").attr("disabled", true);
                        //交易对手
                        $("#counterId").attr("disabled", false);
                        //本方交易员
                        $("#trader").attr("disabled", false);
                        //对手交易员
                        $("#counterTrader").attr("disabled", false);
                        //新质押券
                        $("#newRepSecId_div").hide();
                        //新折算比例
                        $("#newConRatio_div").hide();
                        //新质押数量
                        $("#newRepSecNum_div").hide();
                        //新券面金额
                        $("#newPrintAmt_div").hide();
                        //原质押券
                        $("#origRepSecId_div").hide();
                        //原折算比例
                        $("#origConRatio_div").hide();
                        //原质押数量
                        $("#origRepSecNum_div").hide();
                        //原券面金额
                        $("#origPrintAmt_div").hide();
                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").hide();
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }else if(repPs == BACK_LEND_DIRECTION_04){
                        //组合名称
                        $("#portfolioId").attr("disabled", false);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", true);
                        //回购期限
                        $("#term").attr("disabled", true);
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield").attr("disabled", true);
                        //首期结算日
                        $("#fristSettleDate").attr("disabled", true);
                        //到期结算日
                        $("#expireSettleDate").attr("disabled", true);
                        //占款天数
                        $("#occupyDays").attr("disabled", true);
                        //回购利息
                        $("#intAmt").attr("disabled", true);
                        //到期结算金额
                        $("#expireSettleAmt").attr("disabled", true);
                        //质押券
                        $("#repSecId").attr("disabled", true);
                        //折算比例
                        $("#conRatio").attr("disabled", true);
                        //质押数量
                        $("#repSecNum").attr("disabled", true);
                        //券面金额
                        $("#printAmt").attr("disabled", true);
                        //交易对手
                        if($("#redoType").val() == '1') {
                            $("#counterId").attr("disabled", false);
                        }
                        else {
                            $("#counterId").attr("disabled", true);
                        }

                        //本方交易员
                        $("#trader").attr("disabled", true);
                        //对手交易员
                        $("#counterTrader").attr("disabled", true);
                        //新质押券
                        $("#newRepSecId_div").hide();
                        //新折算比例
                        $("#newConRatio_div").hide();
                        //新质押数量
                        $("#newRepSecNum_div").hide();
                        //新券面金额
                        $("#newPrintAmt_div").hide();
                        //原质押券
                        $("#origRepSecId_div").hide();
                        //原折算比例
                        $("#origConRatio_div").hide();
                        //原质押数量
                        $("#origRepSecNum_div").hide();
                        //原券面金额
                        $("#origPrintAmt_div").hide();
                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").show();
                        $("#reqOnId").attr("disabled", true);
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }
                }else if(ps == 'T94') {
                    if(repPs == BACK_LEND_DIRECTION_03){
                        //组合名称
                        $("#portfolioId").attr("disabled", true);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", false);
                        //回购期限
                        $("#term_div").hide();
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield_div").hide();
                        //首期结算日
                        $("#fristSettleDate_div").hide();
                        //到期结算日
                        $("#expireSettleDate_div").hide();
                        //占款天数
                        $("#occupyDays_div").hide();
                        //回购利息
                        $("#intAmt_div").hide();
                        //到期结算金额
                        $("#expireSettleAmt_div").hide();
                        //质押券
                        $("#repSecId_div").hide();
                        //折算比例
                        $("#conRatio_div").hide();
                        //质押数量
                        $("#repSecNum_div").hide();
                        //券面金额
                        $("#printAmt_div").hide();

                        //新质押券
                        $("#newRepSecId_div").show();
                        $("#newRepSecId").attr("disabled", false);
                        //新折算比例
                        $("#newConRatio_div").show();
                        $("#newConRatio").attr("disabled", true);
                        //新质押数量
                        $("#newRepSecNum_div").show();
                        $("#newRepSecNum").attr("disabled", false);
                        //新券面金额
                        $("#newPrintAmt_div").show();
                        $("#newPrintAmt").attr("disabled", true);
                        //原质押券
                        $("#origRepSecId_div").show();
                        $("#origRepSecId").attr("disabled", true);
                        //原折算比例
                        $("#origConRatio_div").show();
                        $("#origConRatio").attr("disabled", true);
                        //原质押数量
                        $("#origRepSecNum_div").show();
                        $("#origRepSecNum").attr("disabled", true);
                        //原券面金额
                        $("#origPrintAmt_div").show();
                        $("#origPrintAmt").attr("disabled", true);

                        //交易对手
                        $("#counterId").attr("disabled", true);
                        //本方交易员
                        $("#trader").attr("disabled", false);
                        //对手交易员
                        $("#counterTrader").attr("disabled", false);

                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").hide();
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }else if(repPs == BACK_LEND_DIRECTION_04){
                        //组合名称
                        $("#portfolioId").attr("disabled", false);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", true);
                        //回购期限
                        $("#term_div").hide();
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield_div").hide();
                        //首期结算日
                        $("#fristSettleDate_div").hide();
                        //到期结算日
                        $("#expireSettleDate_div").hide();
                        //占款天数
                        $("#occupyDays_div").hide();
                        //回购利息
                        $("#intAmt_div").hide();
                        //到期结算金额
                        $("#expireSettleAmt_div").hide();
                        //质押券
                        $("#repSecId_div").hide();
                        //折算比例
                        $("#conRatio_div").hide();
                        //质押数量
                        $("#repSecNum_div").hide();
                        //券面金额
                        $("#printAmt_div").hide();

                        //新质押券
                        $("#newRepSecId_div").show();
                        $("#newRepSecId").attr("disabled", true);
                        //新折算比例
                        $("#newConRatio_div").show();
                        $("#newConRatio").attr("disabled", true);
                        //新质押数量
                        $("#newRepSecNum_div").show();
                        $("#newRepSecNum").attr("disabled", true);
                        //新券面金额
                        $("#newPrintAmt_div").show();
                        $("#newPrintAmt").attr("disabled", true);
                        //原质押券
                        $("#origRepSecId_div").show();
                        $("#origRepSecId").attr("disabled", true);
                        //原折算比例
                        $("#origConRatio_div").show();
                        $("#origConRatio").attr("disabled", true);
                        //原质押数量
                        $("#origRepSecNum_div").show();
                        $("#origRepSecNum").attr("disabled", true);
                        //原券面金额
                        $("#origPrintAmt_div").show();
                        $("#origPrintAmt").attr("disabled", true);

                        //交易对手
                        $("#counterId").attr("disabled", true);
                        //本方交易员
                        $("#trader").attr("disabled", true);
                        //对手交易员
                        $("#counterTrader").attr("disabled", true);

                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").show();
                        $("#reqOnId").attr("disabled", true);
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }
                }else if(ps == 'T93') {
                    if(repPs == BACK_LEND_DIRECTION_03){
                        //组合名称
                        $("#portfolioId").attr("disabled", true);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", false);
                        //回购期限
                        $("#term_div").hide();
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield_div").hide();
                        //首期结算日
                        $("#fristSettleDate_div").hide();
                        //到期结算日
                        $("#expireSettleDate_div").hide();
                        //占款天数
                        $("#occupyDays_div").hide();
                        //回购利息
                        $("#intAmt_div").hide();
                        //到期结算金额
                        $("#expireSettleAmt_div").hide();
                        //质押券
                        $("#repSecId_div").show();
                        $("#repSecId").attr("disabled", true);
                        //折算比例
                        $("#conRatio_div").hide();
                        //质押数量
                        $("#repSecNum_div").show();
                        $("#repSecNum").attr("disabled", true);
                        //券面金额
                        $("#printAmt_div").hide();
                        //新质押券
                        $("#newRepSecId_div").hide();
                        //新折算比例
                        $("#newConRatio_div").hide();
                        //新质押数量
                        $("#newRepSecNum_div").hide();
                        //新券面金额
                        $("#newPrintAmt_div").hide();
                        //原质押券
                        $("#origRepSecId_div").hide();
                        //原折算比例
                        $("#origConRatio_div").hide();
                        //原质押数量
                        $("#origRepSecNum_div").hide();
                        //原券面金额
                        $("#origPrintAmt_div").hide();

                        //交易对手
                        $("#counterId").attr("disabled", true);
                        //本方交易员
                        $("#trader").attr("disabled", false);
                        //对手交易员
                        $("#counterTrader").attr("disabled", false);

                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").hide();
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }else if(repPs == BACK_LEND_DIRECTION_04){
                        //组合名称
                        $("#portfolioId").attr("disabled", false);
                        //资金账号
                        $("#acctNo").attr("disabled", true);
                        //交易场所
                        $("#tradeMarket").attr("disabled", true);
                        //委托方向
                        $("#ps").attr("disabled", true);
                        //回购期限
                        $("#term_div").hide();
                        //回购金额
                        $("#tradeAmt").attr("disabled", true);
                        //回购利率
                        $("#yield_div").hide();
                        //首期结算日
                        $("#fristSettleDate_div").hide();
                        //到期结算日
                        $("#expireSettleDate_div").hide();
                        //占款天数
                        $("#occupyDays_div").hide();
                        //回购利息
                        $("#intAmt_div").hide();
                        //到期结算金额
                        $("#expireSettleAmt_div").hide();
                        //质押券
                        $("#repSecId_div").show();
                        $("#repSecId").attr("disabled", true);
                        //折算比例
                        $("#conRatio_div").hide();
                        //质押数量
                        $("#repSecNum_div").show();
                        $("#repSecNum").attr("disabled", true);;
                        //券面金额
                        $("#printAmt_div").hide();
                        //新质押券
                        $("#newRepSecId_div").hide();
                        //新折算比例
                        $("#newConRatio_div").hide();
                        //新质押数量
                        $("#newRepSecNum_div").hide();
                        //新券面金额
                        $("#newPrintAmt_div").hide();
                        //原质押券
                        $("#origRepSecId_div").hide();
                        //原折算比例
                        $("#origConRatio_div").hide();
                        //原质押数量
                        $("#origRepSecNum_div").hide();
                        //原券面金额
                        $("#origPrintAmt_div").hide();
                        //交易对手
                        $("#counterId").attr("disabled", true);
                        //本方交易员
                        $("#trader").attr("disabled", true);
                        //对手交易员
                        $("#counterTrader").attr("disabled", true);

                        //成交编号
                        $("#tradeId_div").show();
                        $("#tradeId").attr("disabled", true);
                        //请求号
                        $("#reqOnId_div").show();
                        $("#reqOnId").attr("disabled", true);
                        //对手席位
                        $("#seatId_div").hide();
                        //约定号
                        $("#agreeOnId_div").hide();
                        //初始交易编号
                        $("#intiTradeId_div").hide();
                        //补充协议
                        $("#addCla_div").show();
                        $("#addCla").attr("disabled", false);
                    }
                }
            }
            // else if(tradeMarket == '04'){
            //     if(ps == '03' || ps == '04'){
            //         //组合名称
            //         $("#portfolioId").attr("disabled", false);
            //         //资金账号
            //         $("#acctNo").attr("disabled", false);
            //         //交易场所
            //         $("#tradeMarket").attr("disabled", false);
            //         //委托方向
            //         $("#ps").attr("disabled", false);
            //         //回购期限
            //         $("#term").attr("disabled", false);
            //         //回购金额
            //         $("#tradeAmt").attr("disabled", false);
            //         //回购利率
            //         $("#yield").attr("disabled", false);
            //         if(init){
            //             //首期结算日
            //             $("#fristSettleDate").attr("disabled", true);
            //         }else{
            //             //首期结算日
            //             $("#fristSettleDate").val(iPlatformDate).attr("disabled", true).trigger('change');
            //         }
            //         //到期结算日
            //         $("#expireSettleDate").attr("disabled", true);
            //         //占款天数
            //         $("#occupyDays").attr("disabled", true);
            //         //回购利息
            //         $("#intAmt").attr("disabled", true);
            //         //到期结算金额
            //         $("#expireSettleAmt").attr("disabled", true);
            //         //质押券
            //         $("#repSecId").attr("disabled", false);
            //         //折算比例
            //         $("#conRatio").attr("disabled", false);
            //         //质押数量
            //         $("#repSecNum").attr("disabled", false);
            //         //券面金额
            //         $("#printAmt").attr("disabled", true);
            //
            //         //交易对手
            //         $("#counterId").attr("disabled", false);
            //         //本方交易员
            //         $("#trader_div").hide();
            //         //对手交易员
            //         $("#counterTrader_div").hide();
            //         //新质押券
            //         $("#newRepSecId_div").hide();
            //         //新折算比例
            //         $("#newConRatio_div").hide();
            //         //新质押数量
            //         $("#newRepSecNum_div").hide();
            //         //新券面金额
            //         $("#newPrintAmt_div").hide();
            //         //原质押券
            //         $("#origRepSecId_div").hide();
            //         //原折算比例
            //         $("#origConRatio_div").hide();
            //         //原质押数量
            //         $("#origRepSecNum_div").hide();
            //         //原券面金额
            //         $("#origPrintAmt_div").hide();
            //         //成交编号
            //         $("#tradeId_div").hide();
            //         //请求号
            //         $("#reqOnId_div").hide();
            //         //对手席位
            //         $("#seatId_div").show();
            //         $("#seatId").attr("disabled", false);
            //         //约定号
            //         $("#agreeOnId_div").show();
            //         //初始交易编号
            //         $("#intiTradeId_div").hide();
            //         //补充协议
            //         $("#addCla_div").hide();
            //     }else if(ps == 'T96'){
            //         //组合名称
            //         $("#portfolioId").attr("disabled", true);
            //         //资金账号
            //         $("#acctNo").attr("disabled", true);
            //         //交易场所
            //         $("#tradeMarket").attr("disabled", true);
            //         //委托方向
            //         $("#ps").attr("disabled", false);
            //         //回购期限
            //         $("#term").attr("disabled", true);
            //         //回购金额
            //         $("#tradeAmt").attr("disabled", true);
            //         //回购利率
            //         $("#yield").attr("disabled", false);
            //         //首期结算日
            //         $("#fristSettleDate").attr("disabled", true);
            //         if(init){
            //             //首期结算日
            //             $("#expireSettleDate").attr("disabled", true);
            //         }else{
            //             //到期结算日
            //             $("#expireSettleDate").val(iPlatformDate).attr("disabled", true).trigger('change');
            //         }
            //         //占款天数
            //         $("#occupyDays").attr("disabled", true);
            //         //回购利息
            //         $("#intAmt").attr("disabled", true);
            //         //到期结算金额
            //         $("#expireSettleAmt").attr("disabled", true);
            //         //质押券
            //         $("#repSecId").attr("disabled", true);
            //         //折算比例
            //         $("#conRatio").attr("disabled", true);
            //         //质押数量
            //         $("#repSecNum").attr("disabled", true);
            //         //券面金额
            //         $("#printAmt").attr("disabled", true);
            //
            //         //交易对手
            //         $("#counterId").attr("disabled", true);
            //         //本方交易员
            //         $("#trader_div").hide();
            //         //对手交易员
            //         $("#counterTrader_div").hide();
            //         //新质押券
            //         $("#newRepSecId_div").hide();
            //         //新折算比例
            //         $("#newConRatio_div").hide();
            //         //新质押数量
            //         $("#newRepSecNum_div").hide();
            //         //新券面金额
            //         $("#newPrintAmt_div").hide();
            //         //原质押券
            //         $("#origRepSecId_div").hide();
            //         //原折算比例
            //         $("#origConRatio_div").hide();
            //         //原质押数量
            //         $("#origRepSecNum_div").hide();
            //         //原券面金额
            //         $("#origPrintAmt_div").hide();
            //         //成交编号
            //         $("#tradeId_div").hide();
            //         //请求号
            //         $("#reqOnId_div").hide();
            //         //对手席位
            //         $("#seatId_div").hide();
            //         $("#seatId").attr("disabled", true);
            //         //约定号
            //         $("#agreeOnId_div").hide();
            //         //初始交易编号
            //         $("#intiTradeId_div").show();
            //         $("#intiTradeId").attr("disabled", true);
            //         //补充协议
            //         $("#addCla_div").hide();
            //     }else if(ps == 'T95'){
            //         //组合名称
            //         $("#portfolioId").attr("disabled", true);
            //         //资金账号
            //         $("#acctNo").attr("disabled", true);
            //         //交易场所
            //         $("#tradeMarket").attr("disabled", true);
            //         //委托方向
            //         $("#ps").attr("disabled", false);
            //         //回购期限
            //         $("#term").attr("disabled", true);
            //         //回购金额
            //         $("#tradeAmt").attr("disabled", true);
            //         //回购利率
            //         $("#yield").attr("disabled", false);
            //         //首期结算日
            //         $("#fristSettleDate").attr("disabled", true);
            //         if(init){
            //             //首期结算日
            //             $("#expireSettleDate").attr("disabled", true);
            //         }else{
            //             //到期结算日
            //             $("#expireSettleDate").val(etrData.expireSettleDate).attr("disabled", true).trigger('change');
            //         }
            //         //占款天数
            //         $("#occupyDays").attr("disabled", true);
            //         //回购利息
            //         $("#intAmt").attr("disabled", true);
            //         //到期结算金额
            //         $("#expireSettleAmt").attr("disabled", true);
            //         //质押券
            //         $("#repSecId").attr("disabled", true);
            //         //折算比例
            //         $("#conRatio").attr("disabled", true);
            //         //质押数量
            //         $("#repSecNum").attr("disabled", true);
            //         //券面金额
            //         $("#printAmt").attr("disabled", true);
            //
            //         //交易对手
            //         $("#counterId").attr("disabled", true);
            //         //本方交易员
            //         $("#trader_div").hide();
            //         //对手交易员
            //         $("#counterTrader_div").hide();
            //         //新质押券
            //         $("#newRepSecId_div").hide();
            //         //新折算比例
            //         $("#newConRatio_div").hide();
            //         //新质押数量
            //         $("#newRepSecNum_div").hide();
            //         //新券面金额
            //         $("#newPrintAmt_div").hide();
            //         //原质押券
            //         $("#origRepSecId_div").hide();
            //         //原折算比例
            //         $("#origConRatio_div").hide();
            //         //原质押数量
            //         $("#origRepSecNum_div").hide();
            //         //原券面金额
            //         $("#origPrintAmt_div").hide();
            //         //成交编号
            //         $("#tradeId_div").hide();
            //         //请求号
            //         $("#reqOnId_div").hide();
            //         //对手席位
            //         $("#seatId_div").hide();
            //         $("#seatId").attr("disabled", true);
            //         //约定号
            //         $("#agreeOnId_div").hide();
            //         //初始交易编号
            //         $("#intiTradeId_div").show();
            //         $("#intiTradeId").attr("disabled", true);
            //         //补充协议
            //         $("#addCla_div").hide();
            //     }else if(ps == 'T56'){
            //         //组合名称
            //         $("#portfolioId").attr("disabled", true);
            //         //资金账号
            //         $("#acctNo").attr("disabled", true);
            //         //交易场所
            //         $("#tradeMarket").attr("disabled", true);
            //         //委托方向
            //         $("#ps").attr("disabled", false);
            //         //回购期限
            //         $("#term").attr("disabled", false);
            //         //回购金额
            //         $("#tradeAmt").attr("disabled", true);
            //         //回购利率
            //         $("#yield").attr("disabled", false);
            //         //首期结算日
            //         $("#fristSettleDate").attr("disabled", true);
            //         //到期结算日
            //         $("#expireSettleDate").attr("disabled", true);
            //         //占款天数
            //         $("#occupyDays").attr("disabled", true);
            //         //回购利息
            //         $("#intAmt").attr("disabled", true);
            //         //到期结算金额
            //         $("#expireSettleAmt").attr("disabled", true);
            //         //质押券
            //         $("#repSecId").attr("disabled", true);
            //         //折算比例
            //         $("#conRatio").attr("disabled", true);
            //         //质押数量
            //         $("#repSecNum").attr("disabled", true);
            //         //券面金额
            //         $("#printAmt").attr("disabled", true);
            //
            //         //交易对手
            //         $("#counterId").attr("disabled", true);
            //         //本方交易员
            //         $("#trader_div").hide();
            //         //对手交易员
            //         $("#counterTrader_div").hide();
            //         //新质押券
            //         $("#newRepSecId_div").hide();
            //         //新折算比例
            //         $("#newConRatio_div").hide();
            //         //新质押数量
            //         $("#newRepSecNum_div").hide();
            //         //新券面金额
            //         $("#newPrintAmt_div").hide();
            //         //原质押券
            //         $("#origRepSecId_div").hide();
            //         //原折算比例
            //         $("#origConRatio_div").hide();
            //         //原质押数量
            //         $("#origRepSecNum_div").hide();
            //         //原券面金额
            //         $("#origPrintAmt_div").hide();
            //         //成交编号
            //         $("#tradeId_div").hide();
            //         //请求号
            //         $("#reqOnId_div").hide();
            //         //对手席位
            //         $("#seatId_div").hide();
            //         $("#seatId").attr("disabled", true);
            //         //约定号
            //         $("#agreeOnId_div").hide();
            //         //初始交易编号
            //         $("#intiTradeId_div").show();
            //         $("#intiTradeId").attr("disabled", true);
            //         //补充协议
            //         $("#addCla_div").hide();
            //     }
            // }
            // // 存续期交易： 委托方向 = 正回购 、逆回购以外的 【存续期交易：本方交易员、对手交易员可修改】
            // if(ps != '03' && ps != '04'){
            //     //本方交易员
            //     $("#trader").attr("disabled", false);
            //     //对手交易员
            //     $("#counterTrader").attr("disabled", false);
            // }
            if (!$("#portfolioId").val()){
                $("#portfolioId").attr("disabled", false);
            }
            // 回购方向--正回购 + 上交所： 展示补充协议并可编辑

            // if ($("#repPs").val() === BACK_LEND_DIRECTION_03 && $("#tradeMarket").val() === '03'){
            //     //补充协议
            //     $("#addCla_div").show();
            //     $("#addCla").attr("disabled", false);
            // }

            linkAcctNoCtrl();
        };
        /**
         * 交易场所+资金账户联动证券账户
         */
        var linkSecAcctId = function() {
            // GZYHTZJY-143:修改查询证券账户传参 fuyongsheng 20211101 edit start
            var portfolioId = $("#portfolioId").val();
            // GZYHTZJY-143:修改查询证券账户传参 fuyongsheng 20211101 edit end
            var tradeMarket = $("#tradeMarket").val();
            var acctNo = $("#acctNo").val();
            if(tradeMarket && acctNo) {
                // GZYHTZJY-143:修改查询证券账户传参 fuyongsheng 20211101 edit start
                var list = getAcctNoSelectList(portfolioId, acctNo, tradeMarket);
                // GZYHTZJY-143:修改查询证券账户传参 fuyongsheng 20211101 edit end
                if(list && list.length > 0 && list[0].object) {
                    var object = list[0].object;
                    for(var index in object) {
                        if(object[index].tradeMarket == tradeMarket) {
                            $("#secAcctId").textSelect('setValue', object[index].secAcctId);
                            return;
                        }
                    }
                    $("#secAcctId").val('').trigger('change');
                    $("#finprodId").val('');
                }
                else {
                    $("#secAcctId").val('').trigger('change');
                    $("#finprodId").val('');
                }
            }
            else {
                $("#secAcctId").val('').trigger('change');
                $("#finprodId").val('');
            }
        }









//    /**
//     * 格式化价格
//     * @param price
//     * @returns
//     */
//    function formatPrice(price){
//     var finprodType2 = $("#finprodType2_" + currentTab).val();
//    	if(finprodType2 == FINPROD_TYPE_TG_F01){
//    		return Math.round(price * 1000) / 1000;
//    	}
//    	return Math.round(price * 100) / 100;
//    }



        /**
         * 对外公开的接口
         */
        parent.EtrAgreRepTrade001 = {
            getSearchParams:getSearchParams,
            setpositionsItems:setpositionsItems,
            setunOpenQuotaItems:setunOpenQuotaItems,
            setNnWayRepItems:setNnWayRepItems,
            setEtrExchangeItems:setEtrExchangeItems,
            doReset:doReset,
            getRiskInterfUrl:getRiskInterfUrl,
            getriskdata:getriskdata
        }
        /**
         * 对外公开的接口
         */
        parent.EtrExchangeTrade001 = {
            riskResultdata:riskResultdata,
            cluflag:cluflag
        }
        /**
         * 对外公开的接口
         */
        EtrExchangeTrade001 = {
            riskResultdata:riskResultdata,
            cluflag:cluflag
        }
        /**
         * 获取风险试算数据
         */
        function getriskdata (object) {

            //质押式回购
            var obj = {
                tradeDirection: object.ps,
                timePoint: MY_SUP_TIME_TYPE_02,
                valDate: iPlatformDate,
                portfolioCode: object.portfolioId,
                tradeAmount: object.tradeAmt,
                assetType: 'F07',
                isEnabled: '01',
                assetCode: object.repSecId,
                flag:'xyhg',
                repPs: object.repPs,
                tradeMarket: object.tradeMarket,
                counterId:object.counterId,
                term:object.term
            }
            var riskdata = {
                tradeDirection: ['ps', 'EtrExchangeTrade001.ps'],
                portfolioCode: ['portfolioId', 'EtrExchangeTrade001.portfolioId'],
                tradeAmount: ['tradeAmt','EtrExchangeTrade001.tradeAmt'],
                assetType: ['assetType1', 'EtrExchangeTrade003.finprodType2'],
                data: obj,
                counterId: ['counterId', 'EtrExchangeTrade001.counterId']
            }
            return riskdata;
        }
        /**
         * 风险试算
         * @param url
         * @param object
         * @param riskdata
         * @returns
         */
        function doRiskcal() {
            var object = getSearchParams();
            if(object) {
                // 交易所协议回购 指令环节，只有正回购、逆回购、到期续做等交易方向参与风险试算
                if(BaseRiskMathComm && (object.ps == '03' || object.ps == '04' || object.ps == 'T56')) {
                    var riskdata = getriskdata(object);
                    var riskInterfUrl = getRiskInterfUrl();
                    object.fromEtrExchange = "Y";
                    object.busiPs = object.repPs;
                    object.assetType2 = "H01";
                    if(riskdata && riskInterfUrl) {
                        window.BaseRiskMathComm.initBaseRiskMathRedis(riskInterfUrl, object,riskdata,riskMathRedisCallback);
                    }
                }
            }

        }

        function riskMathRedisCallback(object,cluflag) {
            var objectMap = new Map();
            var length = object.length
            // 根据指标Id 去重
            for(var i = 0; i < length; i++) {
                var item = objectMap.get(object[i].kriCode);
                if(item) {
                    object[i].kriOldExpr = item.kriOldExpr;
                    object[i].oldValue = item.oldValue;
                }
                objectMap.set(object[i].kriCode,object[i]);
            }
            var arr = new Array();
            objectMap.forEach(function (item) {
                arr.push(item)
            });
            EtrExchangeTrade001.riskResultdata = arr;
            EtrExchangeTrade001.cluflag = cluflag;
            Joyin.dialog(JoyinUtil.getLabel('EtrExchangeTradeRiskcal'), '/invest/entrust/etrexchangetrade/EtrExchangeTradeRiskcal/init', '60%', '75%',null);
        }
        function saveOrSubmit(operateType) {
            var s;
            var object = getSearchParams();
            object.operateType = operateType;
            //object.pageModel = pageModel;

            var riskdata = getriskdata(object);
            if(BaseRiskMathComm && (object.ps == '03' || object.ps == '04' || object.ps == 'T56')) {
                s = BaseRiskMathComm.saveOrSubmitCheck(operateType);
                if(!s && s != ''){
                    return;
                }
                if (window.BaseRiskMathComm.isCalcConditionChanged(riskdata)){
                    return;
                }
            }
            //验证
//        if(!checkDbClickData(object)){
//            return;
//        }
            //TODO 风险和授信
//        Joyin.ajaxJson(basePath + 'invest/entrust/EtrCommonController/check', object, function(result1) {
            Joyin.ajaxJson(basePath + '/invest/entrust/etragrereptrade/EtrAgreRepTrade001/check',object,
                function(result) {
                    Joyin.confirm(result.data,function() {
                        if (object.operateType === 'save') {
                            WorkFlow.doSaveList(basePath+'/invest/entrust/etragrereptrade/EtrAgreRepTrade001/save',object,"EtrAgreRepTrade001_btn_save",null,function() {

                                doReset(true);
                                JoyinUtil.disableButton("EtrAgreRepTrade001_btn_save", false);
                            })
                        }else {
                            WorkFlow.doSubmitList(basePath+'/invest/entrust/etragrereptrade/EtrAgreRepTrade001/save',object,"EtrAgreRepTrade001_btn_submit",null,function() {
                                doReset(true);
                                JoyinUtil.disableButton("EtrAgreRepTrade001_btn_submit", false);
                            })
                        }
                    });
                });
//        })

        }
        /***
         * 定时刷新行情
         */
        var dsRefreshHq = function() {
            setInterval(function () {
                if(currentTab == 'jj') {
                    /**定时刷新竞价行情*/
                    var secId = $("#secId_" + currentTab).val();
                    var finprodName = '';
                    var finprodType = '';
                    if(secId) {
                        var object = getSecIdSelectObjectData(secId);
                        if(object) {
                            finprodName = object.finprodName;
                            finprodType = object.finprodType2;
                            if(object.othCodeType && object.othCodeType == FINPROD_OTH_CODE_TYPE_06){
                                secId = object.marketId;
                            }
                            queryJJHQXX(secId, finprodName,finprodType);
                        }
                    }
                    //刷新可用现金
                    linkAccAvailableCash();
                    //刷新可卖数量
                    linkAvailableNum();
                }
                if(currentTab == 'dz') {
                    /**定时大宗行情*/
                    loadDzHqTable();
                }

                if(currentTab == 'gd') {
                    /**定时固定收益行情*/
                    if($("#secId_" + currentTab).val()) {
                        fixedIncomeTable.table ? fixedIncomeTable.reload() : fixedIncomeTable.load();
                    }
                }
            }, 50000);

        }

        function getRiskMathPrompt() {
            var ps = $("#ps_" + currentTab).val();
            var finprodType2 = $("#finprodType2_" + currentTab).val();
            var calculateField = $("[name=calculateField_" + currentTab + "]:radio").next(".layui-form-radioed").prev("input").val();
            if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
                if(calculateField == SEC_CALC_MODE_01) {
                    if(!$("#price_" + currentTab).val()){
                        return 'EtrExchangeTrade001.price';
                    }
                }
                if(calculateField == SEC_CALC_MODE_02) {
                    if(!$("#fprice_" + currentTab).val()){
                        return 'EtrExchangeTrade001.fprice';
                    }
                }
                if(calculateField == SEC_CALC_MODE_03) {
                    if(!$("#yield_" + currentTab).val()){
                        return 'EtrExchangeTrade001.yield';
                    }
                }
            }else if(currentTab == 'jj' && finprodType2 == 'ZYSHG') {
                if(!$("#entrustPrice_" + currentTab).val()){
                    return 'EtrRepo001.price';
                }
                if(!$("#entrustNum_" + currentTab).val()){
                    return 'EtrExchangeTrade002.secNum';
                }
            }else{
                if(!$("#entrustPrice_" + currentTab).val()){
                    return 'EtrExchangeTrade003.price';
                }
            }
            if(!$("#entrustNum_" + currentTab).val()){
                return 'EtrExchangeTrade001.entrustNum';
            }
            return 'EtrInvestBondInfo002.entrustAmt';
        }


        /***行情相关方法 add by chaowei end*/

        /**
         * 画面加载完成
         */


            //初始化时，是否隐藏左上角内容
        var initPage = function(){
                // hideLeftTopPanel();

                pageShowEdit(true);
                if(parentPageModel==PAGE_MODEL_REFERENCE || parentPageModel==PAGE_MODEL_EDIT){
                    // 隐藏重置按钮
//            linkTradePlat(entrustType, true);
//            portfolioIdChange(true);
//            acctNoChange(true);
//            secIdChange();
//            tradeMarketChange();
//            finprodType2Change();
//            secAcctIdChange();
//            targetTypeChange();
//            entrustNumChange();
//            psChange();
//            priceModelChange(true);

                }
                if(workflowId){
                    $("#EtrAgreRepTrade001_btn_div").show();
                }else{
                    $("#psList").val('03,04');
                }
            }

        /**
         * 深交所逆回购质押券非必填，其它场景必填，打上*号
         */
        var removeMustOfRepSecId = function(){
            if($('#tradeMarket').val()==TRADE_MARKET_04&&$('#repPs').val()==BACK_LEND_DIRECTION_04
                && ($('#ps').val()==PS_04 || $('#ps').val()=='T96' || $('#ps').val()=='T95')){
                $('#repSecIdLabel').find('> .must').remove();
            }
            else{
                if ($('#repSecIdLabel').find('> .must').length === 0) {
                    $('#repSecIdLabel').append($('<i class="must">*</i>'));
                }
            }
        }

    /**
     * 显示现金
     */
    var showCashData = function(){
        if($('#portfolioId').val()&&$('#acctNo').val()){
            $('#cashUse_div').show();
            $('#cashGet_div').show();
            Joyin.ajaxSync(basePath + 'invest/entrust/etragrereptrade/EtrAgreRepTrade001/getCashAmount', {portfolioId:$('#portfolioId').val(),acctNo:$('#acctNo').val(),sysDate:$('#vdate').val()}, function(result) {
                if(result){
                    $('#cashUse').val(result.cashUse).trigger('blur');
                    $('#cashGet').val(result.cashGet).trigger('blur');
                }
            });
        }
        else{
            $('#cashUse_div').hide();
            $('#cashGet_div').hide();
            $('#cashUse').text("");
            $('#cashGet').text("");
        }
    }

    /*function toThousands(num) {
        var num = (num || 0).toString(), result = '';
        while (num.length > 3) {
            result = ',' + num.slice(-3) + result;
            num = num.slice(0, num.length - 3);
        }
        if (num) { result = num + result; }
        return result;
    }*/

        $(function() {

            initPage();

            // 绑定各种字段的change事件
            // 组合名称
            $("#portfolioId").change(function() {
                portfolioIdChange(false);
            });
//        // 证券代码
//        $("#secId_jj,#secId_dz,#secId_gd").change(function() {
//            secIdChange();
//        });
            // 交易场所
            $("#tradeMarket").change(function() {
                tradeMarketChange();
            });
            $("#ps").change(function() {
                psChange();
            });
            // 资金账户
            $("#acctNo").change(function() {
                acctNoChange(false);
            });
            $("#fristSettleDate").change(function() {
                settleDateChange();
            });
            $("#expireSettleDate").change(function() {
                settleDateChange();
            });
            $("#term").change(function() {
                termChange();
            });
            $("#occupyDays").change(function() {
                amtCal();
            });
            $("#tradeAmt").change(function() {
                amtCal();
                // conRatioCal();
                repSecNumCal();
            });
            $("#yield").change(function() {
                amtCal();
            });
            $("#repSecId").change(function() {
                printAmtCal();
            });
            $("#repSecNum").change(function() {
                printAmtCal();
            });
            $("#newRepSecId").change(function() {
                newprintAmtCal();
            });
            $("#newRepSecNum").change(function() {
                newprintAmtCal();
            });
            $("#printAmt").change(function() {
                conRatioCal();
            });
            $("#newPrintAmt").change(function() {
                conRatioCal();
            });
            $("#conRatio").change(function() {
                repSecNumCal();
            });
            // 绑定提交按钮
            $('#EtrAgreRepTrade001_btn_submit').click(function() {
                saveOrSubmit('submit');
            });

            // 绑定保存按钮
            $('#EtrAgreRepTrade001_btn_save').click(function() {
                saveOrSubmit('save');
            });

            // 绑定取消按钮
            $('#EtrAgreRepTrade0011_btn_reset').click(function() {
                doReset(false);
            });

            // 绑定风险按钮
            $('#EtrAgreRepTrade001_btn_riskcal').click(function() {
                doRiskcal();
            });
            // dsRefreshHq();

            if(entityStr){
                var etrData = JSON.parse(entityStr);
                setPageItem(etrData);
            }
        });
    }());