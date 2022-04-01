/**
 * 行情和交易信息
 */
(function(){
    /**===============================================定义变量start========================================= **/
    //申报价格
    var declarePriceObj = {};
    //是否需要格式化指令价格(申报信息get出来不需要格式化)
    var declarePriceFlag = true;
    //初始化标志
    var  initFlag = false;
    var  availableNum = "";
    // 默认画面为“净价交易平台”
    var currentTab = "jj";
    //试算结果
    var riskResultdata;
    var cluflag = true;
    //默认新增指令 指令操作类型  NEW 是新增指令  EDIT 修改指令
    var optType = ENTRUST_RECORD_NEW;
    //默认清空表示为NULL值,点击清空时，置为true
    var resetFlag = null;
    //判断竞价平台资产二级分类;
    var type9 = '';
    //证券代码关联的对象;
    secIdObjRelative = '';
    //默认指令价格小数点后位数
    var scale = 3;
    //双击后在录入页面加载联动的开关，防止计算价格和收益率的方法重复调用,提高速度
    var setPageItemsFlag = false;
    //市价指令默认0价
    var zeroPrice = '0.00';
    //指令真实价格
    var factPrice = zeroPrice;

    //当前编辑的组合代码
    var curPortfolioId = "";


    /**===============================================定义变量end========================================= **/

    /**===============================================获取参数的方法start========================================= **/
    /**
     * 返回表单数据
     * @returns
     */
    function getSearchParams() {
        var object = $.serializeToObject('#sumbitForm_'+currentTab);
        object.finprodType2 =  $("#finprodType2_" + currentTab).val();
        //根据指令方向设置质押式回购的金融产品类型
        if (object.finprodType2 && object.finprodType2 == FINPROD_TYPE_TG_ZYSHG) {
            if(object.ps == PS_03) {
                object.finprodType2 = FINPROD_TYPE_TG_F02;
            }
            else {
                object.finprodType2 = FINPROD_TYPE_TG_F03;
            }
        }
        // 隐藏项需要手动获取
        object.finprodId = $("#finprodId_" + currentTab).val();
        object.finprodName = $("#finprodName_" + currentTab).val();
        object.tradeMarket = $("#tradeMarket_" + currentTab).val();
        object.entrustId = $("#entrustId_" + currentTab).val();
        object.entrustStatus = $("#entrustStatus_" + currentTab).val();
        object.isMulti = "Y";
        object.tradePlat = currentTab;
        object.workflowId = workflowId;
        if(currentTab == 'jj'){//竞价
            object.tradePlat = SHARES_TRADE_PLATFORM_02;
        }else if(currentTab == 'dz'){//大宗
            object.tradePlat = SHARES_TRADE_PLATFORM_04;
        }else if(currentTab == 'gd'){//固定
            object.tradePlat = SHARES_TRADE_PLATFORM_03;
        }
        if(object.secId){//证券代码
            object.stockId = object.secId;
        }

        // 计算模式需要特殊处理
        object.calculateField = object['calculateField_' + currentTab];
        //指令价格为空或者债券时 交易方向非配债、行权回售、可转债申购
        if(object.finprodType2 && object.finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(object.ps)){
            object.entrustPrice = object.price;
        };

        if(object.priceModel == PRICE_MODEL_PRM03){
            object.entrustPrice = $("#factPrice_" + currentTab).val();
            if (!object.entrustPrice) {
                object.entrustPrice = 0;
            }
        }
        object.pageModel = pageModel;
        object.optType = optType;

        object.fromEtrExchange = YES_OR_NO_Y;

        if(riskResultdata) {
            object.riskTrialRetDto =  riskResultdata;
        }
        if(dateTable) {
            object.tradeMultiPtl001Dtos = dateTable.getAllRows();
        }
        return object;
    }

    /**
     * 返回大宗查询条件
     * @returns
     */
    function getDzSearchParams() {
        var object = new Object();
        object.secId = $("#secId_" + currentTab).val();
        return object;
    }

    /**
     * 获取风险试算数据
     */
    var getRiskCheckData = function (object) {
        var checkData = {};
        var objects = new Array();
        for (var index in object.tradeMultiPtl001Dtos) {
            var item =  object.tradeMultiPtl001Dtos[index];
            var obj = {}
            //债券
            if(object.finprodType2 == FINPROD_TYPE_TG_F01) {
                var tradeAmount = 0;
                if(object.ps == PS_T49) {
                    tradeAmount = item.entrustNum;
                }
                else {
                    tradeAmount = item.entrustAmt;
                }
                obj = {
                    tradeDirection: item.ps,
                    timePoint: MY_SUP_TIME_TYPE_02,
                    valDate: iPlatformDate,
                    portfolioCode: item.portfolioId,
                    tradeAmount: tradeAmount,
                    assetType: item.finprodType2,
                    isEnabled: '01',
                    assetCode: item.finprodId
                }

            }
            //基金
            else if (object.finprodType2 == FINPROD_TYPE_TG_F06) {
                //买入
                if(object.ps == PS_T01) {
                    obj = {
                        tradeDirection: item.ps,
                        timePoint: MY_SUP_TIME_TYPE_02,
                        valDate: iPlatformDate,
                        portfolioCode: item.portfolioId,
                        tradeAmount: item.entrustAmt,
                        assetType: item.finprodType2,
                        isEnabled: '01',
                        assetCode: item.finprodId
                    }
                }
                //卖出
                else if(object.ps == PS_T02){
                    obj = {
                        tradeDirection: item.ps,
                        timePoint: MY_SUP_TIME_TYPE_02,
                        valDate: iPlatformDate,
                        portfolioCode: item.portfolioId,
                        tradeAmount: item.entrustAmt,
                        assetType: item.finprodType2,
                        isEnabled: '01',
                        assetCode: item.finprodId,
                        amtType:'1'
                    }
                }
            }
            //股票
            else if (object.finprodType2 == FINPROD_TYPE_TG_F10) {
                obj = {
                    tradeDirection: item.ps,
                    timePoint: MY_SUP_TIME_TYPE_02,
                    valDate: iPlatformDate,
                    portfolioCode: item.portfolioId,
                    tradeAmount: item.entrustAmt,
                    assetType: item.finprodType2,
                    isEnabled: '01',
                    assetCode: item.finprodId
                }
            }
            //质押式回购
            else if (object.finprodType2 == FINPROD_TYPE_TG_F02 || object.finprodType2 == FINPROD_TYPE_TG_F03) {
                obj = {
                    tradeDirection: item.ps,
                    timePoint: MY_SUP_TIME_TYPE_02,
                    valDate: iPlatformDate,
                    portfolioCode: item.portfolioId,
                    tradeAmount: item.entrustAmt,
                    assetType: ps == PS_03?FINPROD_TYPE_TG_F02:FINPROD_TYPE_TG_F03,
                    isEnabled: '01',
                    assetCode: item.finprodId
                }
            }

            //增加交易对手影响试算
            if(currentTab=='dz' || currentTab=='gd') {
                obj.counterId = object.counterId;
            }

            var riskdata = {
                tradeDirection: ['ps', 'EtrExchangeTrade001.ps'],
                portfolioCode: ['portfolioId', 'EtrExchangeTrade001.portfolioId'],
                tradeAmount: ['entrustNum',getRiskMathPrompt()],
                assetType: ['assetType1', 'EtrExchangeTrade003.finprodType2'],
                data: obj,
                // add by zhangrui1 交易对手影响风险试算 start
                counterId: ['counterId', 'EtrExchangeTrade001.counterId']
            }
            objects.push(riskdata);
        }
        checkData.data = objects;
        return checkData;
    }

    var getRiskData = function (object) {
        var objects = new Array();
        for (var index in object.tradeMultiPtl001Dtos) {
            var item = object.tradeMultiPtl001Dtos[index];
            if(item == PS_03) {
                item.finprodType2 = FINPROD_TYPE_TG_F02;
            }
            else if(item == PS_04){
                item.finprodType2 = FINPROD_TYPE_TG_F03;
            }
            item.vdate = object.vdate;
            item.mdate = object.mdate;
            item.invAim = object.invAim;
            objects.push(item);
        }
        return objects;
    }

    /**
     * 获取当前tab页
     * @return
     */
    var getCurrentTab = function (){
        return currentTab;
    }


    /**
     * 风险试算URL
     * @returns
     */
    var getRiskInterfUrl = function () {


        var riskInterfUrl = basePath + 'riskRdsTrial/interf/trialList/'+ pageId + '/0';

        return riskInterfUrl;
    }

    /**===============================================获取参数的方法end========================================= **/

    /**===============================================工具类的方法start========================================= **/

    /**
     * 重写更新下拉框选项的方法
     */
    $.fn.updateChosenArr = function updateChosenArr(obj) {
        var $this = $(this), arr = obj.dataResult || JSON.parse($this.attr('data-result') || '[]'), item = obj.item;
        $this.empty();
        $this.append($('<option value>'));
        for (var i in arr) {
            if (!arr.hasOwnProperty(i)) continue;
            if (obj.item && obj.item.length > 0) {
                if ($.inArray(arr[i].id, item) >= 0)
                    $('<option>').attr('value', arr[i].id).text(arr[i].text).appendTo($this);
            } else
                $('<option>').attr('value', arr[i].id).text(arr[i].text).appendTo($this)
        }
        if (obj.chosen)
            $this.val(obj.chosen);
        if (obj.disabled === true || obj.disabled === false)
            $this.attr('disabled', obj.disabled);
        if(obj.isNotTrigger) {
            $this.trigger("chosen:updated").trigger('change',obj.isNotTrigger);
        }
        else {
            $this.trigger("chosen:updated").trigger('change');
        }

        if (obj.callback)
            obj.callback($this);
        return $this;
    };


    /**
     * 比较函数
     * @param arg
     * @returns {function(*, *): number}
     */
    function compare(arg){
        return function(m,n){
            var a = m[arg];
            var b = n[arg];
            return a - b; //升序
        }
    };


    /**
     * 校验只要是数字（包含正负整数，0以及正负浮点数）就返回true
     **/

    function isNumber(val){
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if(regPos.test(val) || regNeg.test(val)){
            return true;
        }else{
            return false;
        }
    };

    /**
     * 获取最小价差
     */
    var getMinPrice = function(obj){
        if(!obj){
            return null;
        }
        //设置默认值
        obj.minPrice = 0.001;
        obj.multiple = 1000;
        obj.scale = 3;

        if(!minPriceWhole){
            return obj;
        }
        else {
            var obj = formatSecIdData(obj);
            var filter;
            if(obj.tradePlat && obj.tradeMarket && obj.assetType){
                filter = minPriceWhole.filter(m => m.tradePlat == obj.tradePlat).filter(m => m.tradeMarket == obj.tradeMarket).filter(m => m.assetType == obj.assetType);
            }
            //资产分类配置规则:  1)资产一级分类,资产二级分类不细分;
            //             2)只有资产一级 分类 细分,资产二级分类不细分;
            //             3)资产一级分类 细分,资产二级分类细分;
            //同一交易平台,同一交易场所,同一资产大类【平安资产类型】过滤后仍然有多条(>1)记录
            //同一细则,仅可以配置不分档共1条记录，或者分档共2-n条记录；
            if(null != filter && filter.length > 1){
                var exactFilter;
                var assetType1 = filter[0].assetType1;
                var assetType2 = filter[0].assetType2;
                if(assetType1 && obj.assetType1){
                    exactFilter = filter.filter(m => m.assetType1 == obj.assetType1);
                    if(null != exactFilter && exactFilter.length > 0){
                        filter = exactFilter;
                    }
                    if(assetType2 && obj.assetType2){
                        exactFilter = filter.filter(m => m.assetType2 == obj.assetType2);
                        if(null != exactFilter && exactFilter.length > 0){
                            filter = exactFilter;
                        }
                    }
                }
            }
            if(null != filter && filter.length == 1){
                var minPrice = filter[0].minPrice;
                if(minPrice){
                    obj.minPrice = minPrice;
                    obj.multiple = 1 / minPrice;
                    var precision = parseInt(obj.multiple) - 1;
                    obj.scale = precision <= 0 ? 0 : precision.toString().length;
                }
            }else if (null != filter && filter.length > 1) {
                var minPriceObj = null;
                filter = filter.sort(compare("gradeAmt"));
                for (var i in filter) {
                    var gradeAmt = filter[i].gradeAmt;
                    var gradeCondition = filter[i].gradeCondition;
                    var flag = gradeAmt - obj.price;
                    if (gradeCondition == CONTROL_TYPE_03) {
                        if (flag > 0) {
                            minPriceObj = filter[i];
                        }
                    } else if (gradeCondition == CONTROL_TYPE_04) {
                        if (flag >= 0) {
                            minPriceObj = filter[i];
                        }
                    } else if (gradeCondition == CONTROL_TYPE_01) {
                        if (flag < 0) {
                            minPriceObj = filter[i];
                        }
                    } else if (gradeCondition == CONTROL_TYPE_02) {
                        if (flag <= 0) {
                            minPriceObj = filter[i];
                        }
                    }
                    if (minPriceObj) {
                        var minPrice = minPriceObj.minPrice;
                        obj.minPrice = minPrice;
                        obj.multiple = 1 / minPrice;
                        var precision = parseInt(obj.multiple) - 1;
                        obj.scale = precision <= 0 ? 0 : precision.toString().length;
                        return obj;
                    }
                }
            }
            setScale(obj);
            return obj;
        }
    };


    /**
     * 格式化证券代码关联的对象
     */
    var formatSecIdData = function(obj) {
        var tradePlat = currentTab;
        var tradeMarket = obj.tradeMarket;
        if (tradePlat == "jj") {
            tradePlat = SHARES_TRADE_PLATFORM_02;
        } else if (tradePlat == "dz") {
            if (tradeMarket && tradeMarket == TRADE_PLACE_03) {
                tradePlat = SHARES_TRADE_PLATFORM_04;
            } else if (tradeMarket && tradeMarket == TRADE_PLACE_04) {
                tradePlat = SHARES_TRADE_PLATFORM_05;
            }
        } else if (tradePlat == "gd") {
            tradePlat = SHARES_TRADE_PLATFORM_03;
        }
        obj.tradePlat = tradePlat;
        obj.assetType = '';
        if (obj.finprodType2) {
            if (obj.finprodType2 == FINPROD_TYPE_TG_F01) {
                obj.assetType = FINPROD_TYPE_TG_F01;
                if(obj.tradeMarket == TRADE_PLACE_03){
                    obj.assetType1 = null;
                    obj.assetType2 = null;
                }
            } else {
                obj.assetType1 = null;
                obj.assetType2 = null;
                if (obj.finprodType2 == FINPROD_TYPE_TG_ZYSHG || obj.finprodType2 == FINPROD_TYPE_TG_F02 || obj.finprodType2 == FINPROD_TYPE_TG_F03) {
                    obj.assetType = 'F07';
                } else if (obj.finprodType2 == FINPROD_TYPE_TG_F06) {
                    obj.assetType = 'F02';
                } else if (obj.finprodType2 == FINPROD_TYPE_TG_F10) {
                    obj.assetType = 'F11';
                }
            }
        }
        return obj;
    };


    /**
     * 格式化指令价格
     */
    var formatPrice = function($price,$price2) {
        var $secIdObj = $("#secIdObjRelative_" + currentTab).val();
        if ($secIdObj) {
            var $obj = JSON.parse($secIdObj);
            $.extend($obj, {
                price: $price
            });
            var precision = getMinPrice($obj).scale;
            if (precision >= 0) {
                setScale($obj);
                if($price2){
                    return (Math.round($price2/$obj.minPrice)*$obj.minPrice).toFixed(precision);
                }
                else
                    return (Math.round($price/$obj.minPrice)*$obj.minPrice).toFixed(precision);
            }
        }
        return $price;
    };


    /**
     * 获取行情中的价格
     */
    var getMktPrice = function() {
        if (currentTab == "jj") {
            var finprodType2 = $("#finprodType2_" + currentTab).val();
            var ps = $("#ps_" + currentTab).val();
            if (finprodType2 == FINPROD_TYPE_TG_F06 || finprodType2 == FINPROD_TYPE_TG_F10) {
                if (ps == PS_T01) {
                    return parent.EtrExchangeTradeHq.getJJMktPrice("#limitUp_jj");
                } else if (ps == PS_T02) {
                    return parent.EtrExchangeTradeHq.getJJMktPrice("#limitDown_jj");
                }else {
                    return parent.EtrExchangeTradeHq.getJJMktPrice("#latestPrice_jj");
                }
            } else {
                return parent.EtrExchangeTradeHq.getJJMktPrice("#latestPrice_jj");
            }
        } else if (currentTab == "gd") {
            return $("#price_gd").get(0).value;
        }
        return '';
    };


    var setScale = function(obj) {
        var ps = $("#ps_" + currentTab).val();
        if (obj.finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
            $("#price_" + currentTab).attr("data-textrange", obj.scale);
            $("#fprice_" + currentTab).attr("data-textrange", obj.scale);
        } else {
            $("#entrustPrice_" + currentTab).attr("data-textrange", obj.scale);
        }
    };

    var formatMoney = function (s, n)
    {
        //区分正负数，且整数位%3==0的负数  显示异常
        var flag="";
        n = n > 0 && n <= 20 ? n : 2;
        var s = parseFloat((s + '').replace(/[^\d\.-]/g, '')).toFixed(n) + '';
        if(s.indexOf("-")>=0){
            flag = "-";
            s = s.substring(1,s.length);
        }
        var l = s.split('.') [0].split('').reverse(),
            r = s.split('.') [1];
        var t = '';
        for (var i = 0; i < l.length; i++)
        {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? ',' : '');
        }
        return (flag+t.split('').reverse().join('') + '.' + r).trim();

    };


    /**
     * 删除千分符
     * @param num
     * @returns {string|*}
     */

    function moneyDelete(num){
        if(num&&num!=undefined&&num!=null){
            let _num = num;
            _num = _num.toString();
            _num = _num.replace(/,/gi,'');
            return _num
        }else{
            return num
        }
    }


    /**
     * 判断交易方向是不是标准的交易方向。
     * 当金融产品类型为债券时，需要根据不同的交易方向确定是否为标准券交易。
     * 画面.委托方向 != 数据字典.交易方向.行权-回售 or 申购 or 配债 or 债转股 or 质押入库 or 质押出库
     */
    var isStandardPs = function(ps) {
        return ps != PS_T03 && ps != PS_T04 && ps != PS_T97 && ps != PS_T49 && ps != PS_T46 && ps != PS_T68 && ps != PS_T69;
    };

    var getNumResult = function (type,num1,num2) {
        if (type === 'add'){
            return Number(num1) + Number(num2)
        } else {
            return Number(num1) - Number(num2)
        }
    };
    /**===============================================工具类的方法end========================================= **/

    /**===============================================页面业务处理方法start========================================= **/
    /**
     * 多组合代码变更时，联动其他字段
     *
     */
    var portfolioTreeIdChange = function(init) {
        if(!init) {
            if(dateTable.table) {
                dateTable.clear(function () {
                    linkRefreshTable();
                });
            }
        }
    };


    /**
     * 价格模式选择后的联动处理
     */
    var priceModelChange = function(init,isNotTrigger) {
        var priceModel = $("#priceModel_" + currentTab).val();
        var declaType = $("#declaType_" + currentTab).val();
        var finprodId = $("#finprodId_" + currentTab).val();
        // 十二.价格模式和指令价格的联动
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var ps = $("#ps_" + currentTab).val();
        var secId = $("#secId_" + currentTab).val();
        if(priceModel) {
            // if 画面.价格模式 = 市价指令  and 点击成交成报
            if(priceModel == PRICE_MODEL_PRM03 || (currentTab =="dz" && declaType == DECLA_TYPE_PRM08)) {
                if(priceModel == PRICE_MODEL_PRM03){
                    var obj = {secId: secId, finprodType2: finprodType2,ps: ps};
                    queryJJHQPrice(obj);
                }
                if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
                    $("#price_" + currentTab).attr("disabled", true);
                    if(priceModel == PRICE_MODEL_PRM03){
                        $("#price_" + currentTab).val(zeroPrice).trigger('change');
                    }
                    $("input[name=calculateField_" + currentTab + "]").attr("disabled", true);
                    $("#price_div_" + currentTab).find(':radio[value="01"]').prop('checked', true);
                    layui.form.render();
                    // 单选按钮不会自动触发change事件，需要手动调用
                    calculateFieldChange();
                }
                else {
                    $("#entrustPrice_" + currentTab).attr("disabled", true);
                    $("#entrustPrice_" + currentTab).val(zeroPrice).trigger('change');
                }
            }
            else {
                // 默认隐藏可转股数量
                $("#toShareAvailableNum_div_" + currentTab).hide();
                if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
                    //$("#price_" + currentTab).attr("disabled", false);
                    calculateFieldChange();
                    $("input[name=calculateField_" + currentTab + "]").attr("disabled", false);
                    $("input[name=calculateField_" + currentTab + "]").next().removeClass('layui-radio-disbaled layui-disabled');
                }
                // else if 画面.金融产品类型 = 债券 and 画面.交易方向=数据字典.交易方向.债转股
                else if(finprodType2 == FINPROD_TYPE_TG_F01 && ps == PS_T49) {
                    $("#entrustPrice_" + currentTab).attr("disabled", true);
                    // 画面.可转股数量 展示，不可编辑.
                    $("#toShareAvailableNum_div_" + currentTab).show();
                    // 联动可转股数量
                    linkToShareAvailableNum();
                }
                // else if 画面.金融产品类型 = 债券 and （画面.交易方向=数据字典.交易方向.质押入库 or 质押出库 or 行权-回售） 配债
                else if(finprodType2 == FINPROD_TYPE_TG_F01 && (ps == PS_T97 || ps == PS_T68 || ps == PS_T69 || ps == PS_T04 || (ps == PS_T03))) {
                    $("#entrustPrice_" + currentTab).attr("disabled", true);
                }
                else if(finprodType2 == FINPROD_TYPE_TG_F10 && ps == PS_T46){//配股
                    $("#entrustPrice_" + currentTab).attr("disabled", true);
                }
                else {
                    $("#entrustPrice_" + currentTab).attr("disabled", false);
                }
            }
            if(!init && !isNotTrigger) {
                if(priceModel != PRICE_MODEL_PRM03){
                    // 获取行情价
                    if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
                        $("#price_" + currentTab).val(formatPrice(getMktPrice())).trigger('blur').trigger('change');
                    }else {
                        $("#entrustPrice_" + currentTab).val(formatPrice(getMktPrice())).trigger('blur').trigger('change');
                    }
                }
            }
            // 科创板打新股、股票直连交易支持 fuyongsheng 20211116 edit start
            // 当前tab是竞价交易,股票资产是科创板,并且价格模式选择收盘价固定价格申报时,指令价格根据时间判断
            if(currentTab === "jj" && $("#board_" + currentTab).val() == BOARD_BD03
                && priceModel === DECLA_TYPE_PRM10){
                var currentDate = new Date();
                // 15:00 时间戳
                var time1500 = new Date(currentDate.getFullYear(),currentDate.getMonth(),currentDate.getDate(),15,0,0).getTime();
                // 当前时间为15:00前时，指令价格处理同股票市价指令时处理（指令价格只读，显示0，小数位按照股票最小价差显示）
                if(currentDate.getTime()<time1500){
                    $("#entrustPrice_" + currentTab).attr("disabled", true);
                    $("#entrustPrice_" + currentTab).val(zeroPrice).trigger('change');
                }else{ // 当前时间为15:00-15:30时，指令价格只读，显示最新价（小数位按照股票最小价差显示）；
                    $("#entrustPrice_" + currentTab).val(
                        formatPrice(parent.EtrExchangeTradeHq.getJJMktPrice("#latestPrice_jj")))
                        .trigger('blur').trigger('change');
                }
            }
            // 科创板打新股、股票直连交易支持 fuyongsheng 20211116 edit end
        }
        else {
            if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
                $("#price_" + currentTab).val('').trigger('blur').trigger('change');
            }
            else {
                $("#entrustPrice_" + currentTab).val('').trigger('blur').trigger('change');
            }
        }
        // 十五.申报类型和席位号、交易对手、约定号联动
        // 联动画面.席位号、交易对手、约定号
        if(currentTab == 'dz') {
            // 画面.价格模式 IN（意向申报，定价申报，收盘价固定价格申报，日均价固定价格申报）
            if(declaType == DECLA_TYPE_PRM06 || declaType == DECLA_TYPE_PRM07 || declaType == DECLA_TYPE_PRM10 || declaType == DECLA_TYPE_PRM11) {
                // 画面.席位号、交易对手、约定号 隐藏
                $("#counterId_" + currentTab).val('').trigger('change');
                $("#seatId_" + currentTab).val('').trigger('change');
                $("#agreeOnId_" + currentTab).val('').trigger('change');
                $("#counterId_div_" + currentTab).hide();
                $("#seatId_div_" + currentTab).hide();
                $("#agreeOnId_div_" + currentTab).hide();
            }
            // 画面.价格模式 IN（点击成交申报，成交申报，不指定）
            else if(declaType == DECLA_TYPE_PRM05 || declaType == DECLA_TYPE_PRM08 || declaType == DECLA_TYPE_PRM09) {
                // 画面.席位号、交易对手、约定号展示，可编辑
                $("#counterId_div_" + currentTab).show();
                $("#seatId_div_" + currentTab).show();
                $("#agreeOnId_div_" + currentTab).show();
            }
            if(declaType == DECLA_TYPE_PRM08){
                $("#seatId_" + currentTab).attr("disabled", true);
                $("#agreeOnId_" + currentTab).attr("disabled", true);
            }else{
                $("#seatId_" + currentTab).attr("disabled", false);
                $("#agreeOnId_" + currentTab).attr("disabled", false);
            }
        }
    };

    /**
     * 委托价格变更时联动计算委托金额
     */
    var entrustPriceChange = function() {
        formatEntrustPrice(calcEntrustNumAndAmt);
    };

    /**
     * 目标类型变更时联动计算委托金额
     */
    var targetTypeChange = function(init) {
        var targetType = $("#targetType_" + currentTab).val();
        if(targetType && targetType == TARGET_CLASS_01) {
            $("#entrustNum_" + currentTab).prop("disabled", false);
            $("#entrustAmt_" + currentTab).prop("disabled", true);
        }
        else {
            $("#entrustAmt_" + currentTab).prop("disabled", false);
            $("#entrustNum_" + currentTab).prop("disabled", true);
        }
        calcEntrustNumAndAmt(init);
    };


    /**
     * 委托数量变更时联动计算委托金额
     */
    var entrustNumChange = function() {
        calcEntrustNumAndAmt();
    };

    /**
     * 委托金额更时联动计算委托数量
     */
    var entrustAmtChange = function() {
        calcEntrustNumAndAmt();
    };

    /**
     * 委托方向变更时，需要联动计算收益率等
     */
    var psChange = function(init) {
        linkDeclaType();
        controlEntrustPrice();
        calcPriceAndYield(init);
        linkTargetType();
        linkMulDistriClass();

        linkConversionRate();
        linkRefreshTable();
    };

    /**
     * 分配方式变动方法
     */
    var mulDistriClassChange = function () {

        //联动目标值
        linkTargetValue();
        //联动目标值单位
        linkMulTargetUnit();
        //联动权重方式
        linkWeightType();
        //联动组合分配列表
        linkRefreshTable();
    }

    /**
     * 计算方式改变时。
     * 决定采用净价、全价、到期收益率的哪一个计算另外两个
     */
    var calculateFieldChange = function(init) {
        var calculateField = $("[name=calculateField_" + currentTab + "]:radio").next(".layui-form-radioed").prev("input").val();
        // 选择的计算方式为“净价”时
        if(calculateField == SEC_CALC_MODE_01) {
            var priceModel = $("#priceModel_" + currentTab).val();
            var declaType = $("#declaType_" + currentTab).val();
            // if  画面.价格模式 = 市价指令 or 点击成交成报 or 收盘价固定价格申报 or 日均价固定价格申报
            if(priceModel == PRICE_MODEL_PRM03 || declaType == DECLA_TYPE_PRM08
                || declaType == DECLA_TYPE_PRM10 || declaType == DECLA_TYPE_PRM11) {
                $("#price_" + currentTab).attr("disabled", true);
            }
            else {
                $("#price_" + currentTab).attr("disabled", false);
            }
            $("#fprice_" + currentTab).attr("disabled", true);
            $("#yield_" + currentTab).attr("disabled", true);
        }
        // 选择的计算方式为“全价”时
        else if(calculateField == SEC_CALC_MODE_02) {
            $("#price_" + currentTab).attr("disabled", true);
            $("#fprice_" + currentTab).attr("disabled", false);
            $("#yield_" + currentTab).attr("disabled", true);
        }
        // 选择的计算方式为“到期收益率”时
        else if(calculateField == SEC_CALC_MODE_03) {
            $("#price_" + currentTab).attr("disabled", true);
            $("#fprice_" + currentTab).attr("disabled", true);
            $("#yield_" + currentTab).attr("disabled", false);
        }
        // 重新计算价格（包含净价和全价）和到期收益率
        calcPriceAndYield(init);
    };

    /**
     * 联动可转股数量
     * 画面.可转股数量 = 画面.指令数量 *画面.单位面值 /画面.指令价格
     */
    var linkToShareAvailableNum = function() {
        var ps = $("#ps_" + currentTab).val();
        if(ps == PS_T49) {
            var finprodId = $("#finprodId_" + currentTab).val();
            var entrustNum = $("#entrustNum_" + currentTab).val()
            //债券交易数量单位 add by yeming 20210729 start
            if(bond_traded_unit == '02' && finprodType2 == FINPROD_TYPE_TG_F01){
                entrustNum = entrustNum * 10;
            }
            //债券交易数量单位 add by yeming 20210729 end
            var entrustPrice = $("#entrustPrice_" + currentTab).val();
            // 如果金融产品类型不是“债券”，则可以不需要投资目的
            if(finprodId && entrustNum && entrustPrice && entrustPrice != 0) {
                var param = {
                    finprodId: finprodId
                };
                Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/selectSecFaceValue', param, function(result) {
                    var toShareAvailableNum = Math.floor(entrustNum * result / entrustPrice, 2);
                    $("#toShareAvailableNum_" + currentTab).val(toShareAvailableNum).trigger('blur');
                }, false);
            }
            else {
                $("#toShareAvailableNum_" + currentTab).val('').trigger('blur');
            }
        }
    };


    /**
     * 联动设置对应的tab
     * tradePlat：数据库中的交易平台
     * deleteOther：是否删除其他Tab
     */
    var linkTradePlat = function(entrustType, deleteOther) {
        var tradePlat;
        if(entrustType == ENTRUST_BUSS_TYPE_01 || entrustType == ENTRUST_BUSS_TYPE_02
            || entrustType == ENTRUST_BUSS_TYPE_07 || entrustType == ENTRUST_BUSS_TYPE_09
            || entrustType == ENTRUST_BUSS_TYPE_10 || entrustType == ENTRUST_BUSS_TYPE_11
            || entrustType == ENTRUST_BUSS_TYPE_12 || entrustType == ENTRUST_BUSS_TYPE_13) {
            tradePlat = SHARES_TRADE_PLATFORM_02;
        }
        else if(entrustType == ENTRUST_BUSS_TYPE_03 || entrustType == ENTRUST_BUSS_TYPE_04
            || entrustType == ENTRUST_BUSS_TYPE_05 || entrustType == ENTRUST_BUSS_TYPE_06) {
            tradePlat = SHARES_TRADE_PLATFORM_04;
        }
        else if(entrustType == ENTRUST_BUSS_TYPE_08) {
            tradePlat = SHARES_TRADE_PLATFORM_03;
        }
        // 默认竞价平台
        else {
            tradePlat = SHARES_TRADE_PLATFORM_02;
        }

        //绑定交易平台TAB切换事件
        if(parent.EtrExchangeTradeHq){
            parent.EtrExchangeTradeHq.setTradePlatform(tradePlat,deleteOther);
        }

        if(tradePlat == SHARES_TRADE_PLATFORM_02) {
            $("#content_div_jj").show();
            $("#content_div_dz").hide();
            $("#content_div_gd").hide();
        } else if(tradePlat == SHARES_TRADE_PLATFORM_04) {
            $("#content_div_jj").hide();
            $("#content_div_dz").show();
            $("#content_div_gd").hide();
        }else if( tradePlat == SHARES_TRADE_PLATFORM_03) {
            $("#content_div_jj").hide();
            $("#content_div_dz").hide();
            $("#content_div_gd").show();
        }
    };

    /**
     * 联动委托方向
     */
    var linkPs = function() {
        var object = $("#secIdObjRelative_" + currentTab).val();
        if(object){
            object = JSON.parse(object);
        }
        if(object && object.othCodeType == FINPROD_OTH_CODE_TYPE_06) {
            $("#ps_" + currentTab).updateChosenArr({
                item: [PS_T46],
                chosen: PS_T46,
                disabled: true
            });
            return;
        }
        else if(object && object.othCodeType == FINPROD_OTH_CODE_TYPE_05) {
            $("#ps_" + currentTab).updateChosenArr({
                item: [PS_T97],
                chosen: PS_T97,
                disabled: true
            });
            return;
        }
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        //add by chenjiaxing 资产代码清除是投资方向联动为空 start 20210826
        if(!finprodType2){
            $("#ps_" + currentTab).val('').trigger('change');
        }
        //add by chenjiaxing 资产代码清除是投资方向联动为空 end 20210826
        // if  交易平台 = 数据字典.交易平台.大宗交易平台
        if(currentTab == 'dz') {
            // if 画面.金融产品类型（投管）= 数据字典.金融产品类型（投管）.债券质押式回购(不属于数据字典范围，仅仅判断用)
            if(finprodType2 == FINPROD_TYPE_TG_ZYSHG) {
                $("#ps_" + currentTab).updateChosenArr({
                    item: [PS_03, PS_04, PS_T100],
                    chosen: $("#ps_" + currentTab).val(),
                    disabled: false
                });
            }else if(!finprodType2) {
                $("#ps_" + currentTab).updateChosenArr({
                    item: [PS_T01, PS_T02,PS_03, PS_04, PS_T100],
                    chosen: $("#ps_" + currentTab).val(),
                    disabled: false
                });
            } else {
                $("#ps_" + currentTab).updateChosenArr({
                    item: [PS_T01, PS_T02, PS_T100],
                    chosen: $("#ps_" + currentTab).val(),
                    disabled: false
                });
            }
        }
        else if(currentTab == 'jj') {
            //  if 画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.基金
            if(finprodType2 && finprodType2 == FINPROD_TYPE_TG_F06) {
                $("#ps_" + currentTab).updateChosenArr({
                    item: [PS_T01, PS_T02, PS_T100],
                    chosen: $("#ps_" + currentTab).val(),
                    disabled: false
                });
            }
            // if 画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.股票
            else if(finprodType2 && finprodType2 == FINPROD_TYPE_TG_F10) {
                $("#ps_" + currentTab).updateChosenArr({
                    item: [PS_T01, PS_T02, PS_T04,PS_T100],
                    chosen: $("#ps_" + currentTab).val(),
                    disabled: false
                });
            }
            // if 画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.债券
            else if(finprodType2 && finprodType2 == FINPROD_TYPE_TG_F01) {
                setExtPs();
            }
            // 画面.金融产品类型（投管）为空或者为质押式回购
            //add by chenjiaxing 更改金融产品为空时的枚举值 start
            else if(!finprodType2) {
                $("#ps_" + currentTab).updateChosenArr({
                    item: [PS_03, PS_04,PS_T01, PS_T02,, PS_T03, PS_T04, PS_T46, PS_T49, PS_T100],
                    chosen: $("#ps_" + currentTab).val(),
                    disabled: false
                });
            }else{
                $("#ps_" + currentTab).updateChosenArr({
                    item: [PS_03, PS_04, PS_T100],
                    chosen: $("#ps_" + currentTab).val(),
                    disabled: false
                });
            }
            //add by chenjiaxing 更改金融产品为空时的枚举值 end
        }
        else {
            $("#ps_" + currentTab).trigger('change');
        }
    };

    /**
     * 质押券出入库
     */
    var linkToConverseRatio = function(){
        var ps = $("#ps_" + currentTab).val();
        //设置默认小数位
        $("#entrustPrice_" + currentTab).attr("data-textrange", scale);
        if(ps == PS_T68 || ps == PS_T69) {
            var finprodId = $("#finprodId_" + currentTab).val();
            // 如果金融产品类型不是“债券”，则可以不需要投资目的
            if(finprodId) {
                var param = {finprodId: finprodId};
                Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/selectStdsecConverseRatio', param, function(result) {
                    if(result){
                        $("#entrustPrice_" + currentTab).val(result).trigger('blur');
                    }else{
                        $("#entrustPrice_" + currentTab).val('').trigger('blur');
                    }
                }, false);
            }
            else {
                $("#entrustPrice_" + currentTab).val('').trigger('blur');
            }
        }
    };

    /**
     * 配债、配股价格
     */
    var linkToTrdOthPrice = function(secId) {
        var ps = $("#ps_" + currentTab).val();
        //设置默认小数位
        $("#entrustPrice_" + currentTab).attr("data-textrange", scale);
        if (ps == PS_T46 || ps == PS_T97) {
            if (secId) {
                var param = {
                    finprodMarketId: secId,
                    othCodeType: (ps == PS_T97) ? FINPROD_OTH_CODE_TYPE_05: FINPROD_OTH_CODE_TYPE_06
                };
                Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/selectTrdOthPrice', param,
                    function(result) {
                        if (result) {
                            $("#entrustPrice_" + currentTab).val(result);
                        } else {
                            $("#entrustPrice_" + currentTab).val('').trigger('blur');
                        }
                    },
                    false);
            } else {
                $("#entrustPrice_" + currentTab).val('').trigger('blur');
            }
        }
    };

    /**
     * 联动目标类型
     */
    var linkTargetType = function() {
        var ps = $("#ps_" + currentTab).val();
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        //配债 T97 配股 T46 T03
        if(ps == PS_T49 || ps == PS_T68 || ps == PS_T69 || ps == PS_03 || ps == PS_04 || ps == PS_T97 || ps == PS_T46 || ps == PS_T03) {
            $("#targetType_" + currentTab).prop("disabled", true);
            $("#targetType_" + currentTab).val(TARGET_CLASS_01).trigger('change');
        }else if((finprodType2 == FINPROD_TYPE_TG_F10 && ps == PS_T04) || (finprodType2 == FINPROD_TYPE_TG_F01 && ps == PS_T04 && type9 == WINDTYPEONE_B14)){
            //新股申购
            $("#targetType_" + currentTab).prop("disabled", true);
            $("#targetType_" + currentTab).val(TARGET_CLASS_01).trigger('change');
        }
        else {
            $("#targetType_" + currentTab).prop("disabled", false);
        }
    };

    var setExtPs = function() {
        declarePriceFlag = true;
        //债券默认买入、卖出
        var item = [PS_T01, PS_T02,PS_T100];
        var finprodId = $("#finprodId_" + currentTab).val();
        var param = {
            finprodId: finprodId
        };
        Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/selectExtPs', param,
            function(res) {
                if (null != res) {
                    declarePriceObj = res;
                    for (var key in res) {//如果是申购、直接设置申购
                        if (key == PS_T04) {
                            $("#ps_" + currentTab).updateChosenArr({
                                item: [PS_T04],
                                chosen: PS_T04,
                                disabled: true
                            });
                            return;
                        } else {
                            item.push(key);
                        }
                    }
                    $("#ps_" + currentTab).updateChosenArr({
                        item: item, /*[PS_T01, PS_T02, PS_T03, PS_T49, PS_T04, PS_T68, PS_T69],*/
                        chosen: $("#ps_" + currentTab).val(),
                        disabled: false
                    });
                }
            });
    }


    /**
     * 联动价格模式，控制价格模式的下拉选项。
     */
    var linkDeclaType = function() {
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var dataSource = $("#dataSource_" + currentTab).val();
        if(dataSource && dataSource == '01') {
            // 画面.价格模式  = 数据字典.价格模式.点击成交申报
            $("#declaType_" + currentTab).updateChosenArr({
                item: [DECLA_TYPE_PRM08],
                chosen: DECLA_TYPE_PRM08,
                disabled: true,
                isNotTrigger:true
            });
        }
        else if(currentTab == 'dz') {
            var tradeMarket = $("#tradeMarket_" + currentTab).val();
            // 画面.交易场所 = 数据字典.交易场所.上海证券交易场所
            if(tradeMarket == TRADE_PLACE_03) {
                // 当资产类型为股票,并且上市板是科创板时,申请类型不展示收盘价固定价格申报
                if(finprodType2 == FINPROD_TYPE_TG_F10 && $("#board_" + currentTab).val() == BOARD_BD03){
                    // 画面.价格模式  = 数据字典.意向申报，数据字典.成交申报，不指定
                    $("#declaType_" + currentTab).updateChosenArr({
                        item: [DECLA_TYPE_PRM06, DECLA_TYPE_PRM09, DECLA_TYPE_PRM05],
                        chosen: $("#declaType_" + currentTab).val() == DECLA_TYPE_PRM10?"":$("#declaType_" + currentTab).val(),
                        disabled: false,
                        isNotTrigger:true
                    });
                }else {
                    // 画面.价格模式  = 数据字典.意向申报，数据字典.成交申报，收盘价固定价格申报，不指定
                    $("#declaType_" + currentTab).updateChosenArr({
                        item: [DECLA_TYPE_PRM06, DECLA_TYPE_PRM09, DECLA_TYPE_PRM10, DECLA_TYPE_PRM05],
                        chosen: $("#declaType_" + currentTab).val(),
                        disabled: false,
                        isNotTrigger:true
                    });
                }
            }
            // 画面.金融产品类型 IN（债券、股票、基金）
            else if(/*finprodType2 == FINPROD_TYPE_TG_F01 || */finprodType2 == FINPROD_TYPE_TG_F06) {
                // 画面.价格模式  = 数据字典.收盘价固定价格申报，数据字典.日均价固定价格申报，不指定
                $("#declaType_" + currentTab).updateChosenArr({
                    item: [DECLA_TYPE_PRM10, DECLA_TYPE_PRM11, DECLA_TYPE_PRM05],
                    chosen: $("#declaType_" + currentTab).val(),
                    disabled: false,
                    isNotTrigger:true
                });
            }
            else if(finprodType2 == FINPROD_TYPE_TG_F10){
                // 大宗交易资产为股票且股票的上市板是科创板时,不展示 收盘价固定价格申报
                if($("#board_" + currentTab).val() == BOARD_BD03){
                    $("#declaType_" + currentTab).updateChosenArr({
                        item: [DECLA_TYPE_PRM11, DECLA_TYPE_PRM05],
                        chosen: $("#declaType_" + currentTab).val() == DECLA_TYPE_PRM10?"":$("#declaType_" + currentTab).val(),
                        disabled: false,
                        isNotTrigger:true
                    });
                }else{
                    $("#declaType_" + currentTab).updateChosenArr({
                        item: [DECLA_TYPE_PRM10, DECLA_TYPE_PRM11, DECLA_TYPE_PRM05],
                        chosen: $("#declaType_" + currentTab).val(),
                        disabled: false,
                        isNotTrigger:true
                    });
                }
            }
            //债券申报类型放开
            else if(finprodType2 == FINPROD_TYPE_TG_F01){
                $("#declaType_" + currentTab).updateChosenArr({
                    item: [DECLA_TYPE_PRM06,DECLA_TYPE_PRM08, DECLA_TYPE_PRM09, DECLA_TYPE_PRM07, DECLA_TYPE_PRM10, DECLA_TYPE_PRM11, DECLA_TYPE_PRM05],
                    chosen: $("#declaType_" + currentTab).val(),
                    disabled: false,
                    isNotTrigger:true
                });
            }
            else if(finprodType2 == FINPROD_TYPE_TG_ZYSHG) {
                // 画面.价格模式  = 数据字典.意向申报，数据字典.成交申报，定价申报、数据字典.收盘价固定价格申报，数据字典.日均价固定价格申报、不指定
                $("#declaType_" + currentTab).updateChosenArr({
                    item: [DECLA_TYPE_PRM06, DECLA_TYPE_PRM09, DECLA_TYPE_PRM07, DECLA_TYPE_PRM10, DECLA_TYPE_PRM11, DECLA_TYPE_PRM05],
                    chosen: $("#declaType_" + currentTab).val(),
                    disabled: false,
                    isNotTrigger:true
                });
            }
        }
        else if(currentTab == 'jj') {
            var ps = $("#ps_" + currentTab).val();
            // if 画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.债券
            if(finprodType2 == FINPROD_TYPE_TG_F01) {
                // 回售 or 申购 or 配债 or 债转股 or 质押入库 or 质押出库
                if(ps == PS_T03 || ps == PS_T04 || ps == PS_T97 || ps == PS_T49 || ps == PS_T68 || ps == PS_T69) {
                    // 画面.价格模式  = 数据字典.价格模式.指定价格 不可编辑
                    $("#priceModel_" + currentTab).updateChosenArr({
                        item: [PRICE_MODEL_PRM01],
                        chosen: PRICE_MODEL_PRM01,
                        disabled: true,
                        isNotTrigger:true
                    });
                }else {
                    // 画面.价格模式  = 数据字典.价格模式.限价指令，数据字典.价格模式.市价指令
                    $("#priceModel_" + currentTab).updateChosenArr({
                        item: [PRICE_MODEL_PRM02, PRICE_MODEL_PRM03],
                        chosen: $("#priceModel_" + currentTab).val(),
                        disabled: false,
                        isNotTrigger:true
                    });
                }
            }
            // else if 画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.股票 and （画面.委托方向=数据字典.交易方向.配股 or 数据字典.交易方向.申购）
            else if(finprodType2 == FINPROD_TYPE_TG_F10) {
                // 科创板打新股、股票直连交易支持 fuyongsheng 20211123 edit start
                // 资产为股票,并且上市板=科创板时,价格模式下拉框追加【收盘价固定价格申报】
                if($("#board_" + currentTab).val() == BOARD_BD03){
                    // 判断是否有【收盘价固定价格申报】选项,如果已添加,则无需重复添加
                    var isExist = false;
                    $("#priceModel_" + currentTab).find( "option" ).each(function(i, value) {
                        if(value.value == DECLA_TYPE_PRM10){
                            isExist = true;
                        }
                    });
                    if(!isExist){
                        $("#priceModel_" + currentTab).append("<option value='PRM10'>收盘价固定价格申报</option>")
                    }
                }else{// 如果不是科创板股票时,价格模式下拉框去除【收盘价固定价格申报】
                    $("#priceModel_" + currentTab).find( "option" ).each(function(i, value) {
                        if(value.value === DECLA_TYPE_PRM10){
                            $(this).remove();
                        }
                    });
                }
                // 科创板打新股、股票直连交易支持 fuyongsheng 20211123 edit end
                if((ps == PS_T46 || ps == PS_T04)){
                    // 画面.价格模式  = 数据字典.价格模式.指定价格 不可编辑
                    $("#priceModel_" + currentTab).updateChosenArr({
                        item: [PRICE_MODEL_PRM01],
                        chosen: PRICE_MODEL_PRM01,
                        disabled: true,
                        isNotTrigger:true
                    });
                }
                //add by chenjiaxing 更改资产为空时的价格模式枚举值 start
            }else if(!finprodType2) {
                // 画面.价格模式  = 数据字典.价格模式.限价指令，数据字典.价格模式.市价指令
                $("#priceModel_" + currentTab).updateChosenArr({
                    item: [PRICE_MODEL_PRM01,PRICE_MODEL_PRM02, PRICE_MODEL_PRM03],
                    chosen: $("#priceModel_" + currentTab).val(),
                    disabled: false,
                    isNotTrigger:true
                });
                //add by chenjiaxing 更改资产为空时的价格模式枚举值 end
            }
            // else if 画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.质押式回购
            /*            else if(finprodType2 == FINPROD_TYPE_TG_ZYSHG || finprodType2 == FINPROD_TYPE_TG_F02 || finprodType2 == FINPROD_TYPE_TG_F03) {
                            // 画面.价格模式  = 数据字典.价格模式.指定价格、限价指令
                            $("#priceModel_" + currentTab).updateChosenArr({
                                item: [PRICE_MODEL_PRM01, PRICE_MODEL_PRM02],
                                chosen: $("#priceModel_" + currentTab).val(),
                                disabled: false
                            });
                        }*/
            else {
                // 画面.价格模式  = 数据字典.价格模式.限价指令，数据字典.价格模式.市价指令
                $("#priceModel_" + currentTab).updateChosenArr({
                    item: [PRICE_MODEL_PRM02, PRICE_MODEL_PRM03],
                    chosen: $("#priceModel_" + currentTab).val(),
                    disabled: false,
                    isNotTrigger:true
                });
            }
        }
        else if(currentTab == 'gd') {
            // 画面.价格模式  = 数据字典.价格模式.不指定、定价申报、最优价成交申报、可转换成交申报、指定对手方成交申报
            $("#declaType_" + currentTab).updateChosenArr({
                item: [DECLA_TYPE_PRM14],
                chosen: DECLA_TYPE_PRM14,
                disabled: true,
                isNotTrigger:true
            });
        }
        else {
            $("#priceModel_" + currentTab).trigger('change',true);
            $("#declaType_" + currentTab).trigger('change',true);
        }
    };



    /**
     * 联动委托数量单位。
     * 交易场所和金融产品类型改变时调用
     */
    var linkEntrustNumUnit = function() {
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var tradeMarket = $("#tradeMarket_" + currentTab).val();
        // if 画面.金融产品类型（投管） = 股票
        if(finprodType2 == FINPROD_TYPE_TG_F10) {
            $("#entrustNumUnit_" + currentTab).text("股");
        }
        // if 画面.金融产品类型（债券） = 基金
        else if(finprodType2 == FINPROD_TYPE_TG_F06) {
            $("#entrustNumUnit_" + currentTab).text("份");
        }
        // if 画面.金融产品类型（债券） = 债券
        else if(finprodType2 == FINPROD_TYPE_TG_F01) {
            if(currentTab == 'dz' && tradeMarket == TRADE_PLACE_03) {
//                $("#entrustNumUnit_" + currentTab).text("手");
                //债券交易数量单位 edit by yeming 20210729 start
                if(bond_traded_unit == '02'){
                    $("#entrustNumUnit_" + currentTab).text("手");
                }else{
                    $("#entrustNumUnit_" + currentTab).text("张");
                }
                //债券交易数量单位 edit by yeming 20210729 end
            }
            else {
                //债券交易数量单位 edit by yeming 20210729 start
                if(bond_traded_unit == '02'){
                    $("#entrustNumUnit_" + currentTab).text("手");
                }else{
                    $("#entrustNumUnit_" + currentTab).text("张");
                }
                //债券交易数量单位 edit by yeming 20210729 end
            }
        }
        // if 画面.金融产品类型（债券） = 质押式回购
        else if(finprodType2 == FINPROD_TYPE_TG_ZYSHG || finprodType2 == FINPROD_TYPE_TG_F02 || finprodType2 == FINPROD_TYPE_TG_F03 ) {
            if(currentTab == 'dz' && tradeMarket == TRADE_PLACE_03) {
                $("#entrustNumUnit_" + currentTab).text("张");
            }
            else {
                $("#entrustNumUnit_" + currentTab).text("张");
            }
        }
        if(currentTab == 'dz'){
            if(finprodType2 == FINPROD_TYPE_TG_ZYSHG) {//大宗质押式回购 指令价格单位 % 特殊处理
                $("#entrustPriceUnit").text("%");
            }
            else{
                $("#entrustPriceUnit").text("元");
            }
        }
    };


    var linkMulDistriClass = function () {
        var ps = $("#ps_" + currentTab).val();
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var curMulDistriClass = $("#mulDistriClass_" + currentTab).val();
        if (finprodType2 == FINPROD_TYPE_TG_F01) {
            if(ps == PS_T01) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_01, MUL_DISTRI_CLASS_02, MUL_DISTRI_CLASS_03, MUL_DISTRI_CLASS_04, MUL_DISTRI_CLASS_05, MUL_DISTRI_CLASS_08],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_T02) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_01, MUL_DISTRI_CLASS_02, MUL_DISTRI_CLASS_03, MUL_DISTRI_CLASS_04, MUL_DISTRI_CLASS_05, MUL_DISTRI_CLASS_08],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_T68 || ps == PS_T69) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_10],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_T100) {
                if(currentTab == 'jj') {
                    $("#mulDistriClass_" + currentTab).updateChosenArr({
                        item: [MUL_DISTRI_CLASS_08, MUL_DISTRI_CLASS_10],
                        chosen: curMulDistriClass,
                        disabled: false
                    });
                }
                else {
                    $("#mulDistriClass_" + currentTab).updateChosenArr({
                        item: [MUL_DISTRI_CLASS_08],
                        chosen: curMulDistriClass,
                        disabled: false
                    });
                }

                return;
            }

        }
        else if(finprodType2 == FINPROD_TYPE_TG_F06) {
            if(ps == PS_T01) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_01, MUL_DISTRI_CLASS_02, MUL_DISTRI_CLASS_03, MUL_DISTRI_CLASS_04, MUL_DISTRI_CLASS_05, MUL_DISTRI_CLASS_08],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_T02) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_01, MUL_DISTRI_CLASS_02, MUL_DISTRI_CLASS_03, MUL_DISTRI_CLASS_04, MUL_DISTRI_CLASS_05, MUL_DISTRI_CLASS_08],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_T100) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_08],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }

        }
        else if (finprodType2 == FINPROD_TYPE_TG_F10) {
            if(ps == PS_T01) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_01, MUL_DISTRI_CLASS_02, MUL_DISTRI_CLASS_03, MUL_DISTRI_CLASS_04, MUL_DISTRI_CLASS_05, MUL_DISTRI_CLASS_06, MUL_DISTRI_CLASS_08],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_T02) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_01, MUL_DISTRI_CLASS_02, MUL_DISTRI_CLASS_03, MUL_DISTRI_CLASS_04, MUL_DISTRI_CLASS_05, MUL_DISTRI_CLASS_08],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_T100) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_08],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
        }
        else if (finprodType2 == FINPROD_TYPE_TG_ZYSHG) {
            if(ps == PS_03) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_07, MUL_DISTRI_CLASS_09],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_04) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_06, MUL_DISTRI_CLASS_09],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
            else if(ps == PS_T100) {
                $("#mulDistriClass_" + currentTab).updateChosenArr({
                    item: [MUL_DISTRI_CLASS_09],
                    chosen: curMulDistriClass,
                    disabled: false
                });
                return;
            }
        }
    }

    /**
     * 联动权重
     */
    var linkWeightType = function(){
        $("#weightType_" + currentTab).val("");
        var mulDistriClass = $("#mulDistriClass_" + currentTab).val();
        if(mulDistriClass == MUL_DISTRI_CLASS_03 || mulDistriClass == MUL_DISTRI_CLASS_04) {
            $("#weightType_div_" + currentTab).show();
        }
        else {
            $("#weightType_div_" + currentTab).hide();
        }
    }

    /**
     * 联动目标值单位
     */
    var linkMulTargetUnit = function () {
        var mulDistriClass = $("#mulDistriClass_" + currentTab).val();
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        if(mulDistriClass == MUL_DISTRI_CLASS_01) {
            if(finprodType2 == FINPROD_TYPE_TG_F01) {
                $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_03);
            }
            else if(finprodType2 == FINPROD_TYPE_TG_F06) {
                $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_02);
            }
            else if(finprodType2 == FINPROD_TYPE_TG_F10) {
                $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_01);
            }
        }
        else if(mulDistriClass == MUL_DISTRI_CLASS_02) {

            $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_06);
        }
        else if(mulDistriClass == MUL_DISTRI_CLASS_03) {
            if(finprodType2 == FINPROD_TYPE_TG_F01) {
                $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_03);
            }
            else if(finprodType2 == FINPROD_TYPE_TG_F06) {
                $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_02);
            }
            else if(finprodType2 == FINPROD_TYPE_TG_F10) {
                $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_01);
            }
        }
        else if(mulDistriClass == MUL_DISTRI_CLASS_04) {

            $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_06);
        }
        else if(mulDistriClass == MUL_DISTRI_CLASS_05
            || mulDistriClass == MUL_DISTRI_CLASS_06
            || mulDistriClass == MUL_DISTRI_CLASS_07
            || mulDistriClass == MUL_DISTRI_CLASS_08
            ||mulDistriClass == MUL_DISTRI_CLASS_10) {

            $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_05);
        }
        else if(mulDistriClass == MUL_DISTRI_CLASS_09) {
            $("#mulTargetUnit_" + currentTab).val(MUL_TARGET_UNIT_06);
        }
        else {
            $("#mulTargetUnit_" + currentTab).val("");
        }

    }
    /**
     * 联动目标值
     */
    var linkTargetValue = function(){
        $("#targetValue" + currentTab).val("");
    }

    var linkConversionRate = function () {
        var ps = $("#ps_" + currentTab).val();
        $("#conversionRate_" + currentTab).val("");
        if(ps == PS_T68 || ps == PS_T69) {
            $("#conversionRate_div_" + currentTab).show();
        }
        else {
            $("#conversionRate_div_" + currentTab).hide();
        }
    }

    //add by chenjiaixng 20210907 债券净价交易添加偏离度展示 start
    /**
     * 净值偏离度
     */
    function calDeviationDegree() {
        var price = $("#price_" + currentTab).val();
        var finprodId = $("#finprodId_" + currentTab).val();
        var secId = $("#secId_" + currentTab).val();
        if (secId && price) {
            var paramObj = {};
            paramObj.finprodId = secId;
            if(finprodId){
                paramObj.finprodMarketId = finprodId
            }
            paramObj.unitCprice = price;
            Joyin.ajax(JoyinUtil.getRealPath('/invest/trade/bond/TrdBondInvestTrade003/calcDeviationDegree'), paramObj, function (data) {
                $("#deviationDegree").text(data);
            });
        } else {
            $("#deviationDegree").text("");
        }
    }
    //add by chenjiaixng 20210907 债券净价交易添加偏离度展示 end

    /**
     * 1、根据金融产品类型和委托方向控制委托价格是否展示
     * 2、设置价格字段的小数位数。
     */
    var controlEntrustPrice = function() {
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var ps = $("#ps_" + currentTab).val();
        var $star = $('<i class="must">*</i>');
        if(finprodType2) {
            // if 画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.债券 && 画面.委托方向 != 数据字典.交易方向.行权-回售 or 申购 or 配债 or 债转股 or 质押入库 or 质押出库
            if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
                // 净价、到期收益率、全价显示，单选按钮，计算方式 = 数据字典.计算方式.
                $("#price_div_" + currentTab).show();
                $("#fprice_div_" + currentTab).show();
                $("#yield_div_" + currentTab).show();
                $("#entrustPrice_div_" + currentTab).hide();

                $("#entrustPrice_label_" + currentTab).text("指令价格").append($star.clone());
                $("#entrustNum_label_" + currentTab).text("指令数量").append($star.clone());
                $("#entrustAmt_label_" + currentTab).text("指令金额").append($star.clone());
                $("#entrustPriceUnit_" + currentTab).text("元");
                $("#entrustAmtUnit_" + currentTab).text("元");
            }
            else {
                $("#price_div_" + currentTab).hide();
                $("#fprice_div_" + currentTab).hide();
                $("#yield_div_" + currentTab).hide();
                $("#entrustPrice_div_" + currentTab).show();
                //  画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.债券 and （画面.委托方向=数据字典.交易方向.质押入库、质押出库）
                if(finprodType2 == FINPROD_TYPE_TG_F01 && (ps == PS_T68 || ps == PS_T69)) {
                    // 指令价格展示为折算率标签、指令金额展示为标准券数量
                    $("#entrustPrice_label_" + currentTab).text("折算率").append($star.clone());
                    $("#entrustNum_label_" + currentTab).text("指令数量").append($star.clone());
                    $("#entrustAmt_label_" + currentTab).text("标准券数量").append($star.clone());
                    $("#entrustPriceUnit_" + currentTab).text("%");
                    $("#entrustAmtUnit_" + currentTab).text("张");
                }
                // if 画面.金融产品类型（投管） = 数据字典.金融产品类型（投管）.质押式回购
                else if(finprodType2 == FINPROD_TYPE_TG_ZYSHG) {
                    // 指令价格显示，标签联动为回购利率、指令数量标签联动为标准券数量、指令金额标签联动为回购金额
                    $("#entrustPrice_label_" + currentTab).text("回购利率").append($star.clone());
                    $("#entrustNum_label_" + currentTab).text("标准券数量").append($star.clone());
                    $("#entrustAmt_label_" + currentTab).text("回购金额").append($star.clone());
                    $("#entrustPriceUnit_" + currentTab).text("%");
                    $("#entrustAmtUnit_" + currentTab).text("元");
                }
                else {
                    $("#entrustPrice_label_" + currentTab).text("指令价格").append($star.clone());
                    $("#entrustNum_label_" + currentTab).text("指令数量").append($star.clone());
                    $("#entrustAmt_label_" + currentTab).text("指令金额").append($star.clone());
                    $("#entrustPriceUnit_" + currentTab).text("元");
                    $("#entrustAmtUnit_" + currentTab).text("元");
                }
            }
        }
    };

    /**
     * 联动指令价格、指令数量、指令金额
     */
    var calcEntrustNumAndAmt = function(init) {
        if(init||setPageItemsFlag) {
            return;
        }
        var secId = $("#secId_" + currentTab).val();
        var finprodId = $("#finprodId_" + currentTab).val();
        var targetType = $("#targetType_" + currentTab).val();
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var tradeMarket = $("#tradeMarket_" + currentTab).val();
        var priceModel = $("#priceModel_" + currentTab).val();
        var entrustNum = $("#entrustNum_" + currentTab).val();
        //债券交易数量单位 add by yeming 20210729 start
        if(bond_traded_unit == '02' && finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)){
            entrustNum = entrustNum * 10;
        }
        //债券交易数量单位 add by yeming 20210729 end
        var entrustAmt = $("#entrustAmt_" + currentTab).val();
        var entrustPrice = $("#entrustPrice_" + currentTab).val();
        var ps = $("#ps_" + currentTab).val();
        var board = $("#board_" + currentTab).val();
        // 如果是债券，则需要根据计算方式取单位净价或全价作为价格
        if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
            entrustPrice = $("#price_" + currentTab).val();
        }
        var param = {
            secId: secId,
            finprodId: finprodId,
            targetType: targetType,
            finprodType2: finprodType2,
            entrustNum: entrustNum,
            entrustAmt: entrustAmt,
            tradeMarket: tradeMarket,
            ps: ps,
            board: board,
            currentTab: currentTab,
            resetFlag: resetFlag
        };
        //竞价平台股票、债券、基金，卖出可能会存在零股，增加可用持仓参数；
        if(currentTab == 'jj' && ps == PS_T02 && (finprodType2 == FINPROD_TYPE_TG_F10 || finprodType2 == FINPROD_TYPE_TG_F01 || finprodType2 == FINPROD_TYPE_TG_F06)){
            var availableNum = parent.EtrExchangeTradeHq.getJJMktPrice("#availableNum_jj").replace(/,/g, "");
            if(availableNum && availableNum > 0){
                $.extend(param,{availableNum: availableNum});
            }
        }
        if(priceModel == PRICE_MODEL_PRM03 && isStandardPs(ps)){
            if(targetType) {
                queryJJHQPrice(param,ajaxCalcEntrustNumAndAmt);
            }
        }else {
            if(targetType) {
                if(declarePriceFlag){
                    $.extend(param,{entrustPrice: formatPrice(entrustPrice)});
                }else{
                    $.extend(param,{entrustPrice: entrustPrice});
                }
                ajaxCalcEntrustNumAndAmt(param);
            }
        }
        // 计算完数量需要重新计算可转股数量
        if(ps == PS_T49){
            linkToShareAvailableNum();
        }else if(ps == PS_T68 || ps == PS_T69){
            linkToConverseRatio();
        }else if(ps == PS_T46 || ps == PS_T97){
            var secId = $("#secId_" + currentTab).val();
            linkToTrdOthPrice(secId);
        }
        declarePriceHandle();
    };

    /**
     * 申报维护的申报价格应该作为场内指令的指令价格
     */
    var declarePriceHandle = function () {
        var ps = $("#ps_" + currentTab).val();
        //委托价格==申报价格
        if(declarePriceObj){
            for (var key in declarePriceObj){
                if(key === ps){
                    declarePriceFlag = false
                    $("#entrustPrice_" + currentTab).val(declarePriceObj[key]);
                    break;
                }
            }
        }
    }
    //根据参数、计算指令数量和金额
    var ajaxCalcEntrustNumAndAmt = function(param){
        if (!param.targetType) {
            return;
        }
        if (!param.entrustAmt && !param.entrustNum) {
            return;
        }
        Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/calcEntrustNumAndAmt', param, function(result) {
            if(param.targetType == TARGET_CLASS_01 && param.availableNum && param.availableNum > 0){
                var stockNum = param.availableNum - result.entrustNum;
                if(result.minNum){
                    if(stockNum > 0 && stockNum < result.minNum){
                        availableNum = param.availableNum;
                        if(!initFlag){
                            window.parent.checkSalesOfSmallShares(availableNum);
                        }
                    }
                }
            }
            //债券交易数量单位 add by yeming 20210729 start
            if(bond_traded_unit == '02' && $("#finprodType2_" + currentTab).val() == FINPROD_TYPE_TG_F01 && isStandardPs($("#ps_" + currentTab).val())){
                result.entrustNum = JoyinMath.doDiv(result.entrustNum, 10, 0, JoyinMath.ROUND_DOWN);//转成手配置
            }
            //债券交易数量单位 add by yeming 20210729 end
            // modify by etliu 20210901 start
            if (result.entrustAmt) {
                $("#entrustAmt_" + currentTab).val(result.entrustAmt).trigger('blur');
            } else if ($("#priceModel_" + currentTab).val() == PRICE_MODEL_PRM03) {
                $("#entrustAmt_" + currentTab).val(0).trigger('blur');
            }
            // modify by etliu 20210901 end
            $("#entrustNum_" + currentTab).val(result.entrustNum).trigger('blur');
        }, false);
    }

    /**
     * 零股卖出全部
     */
    var setTotalSharesNum = function(entrustNum){
        $("#entrustNum_" + currentTab).val(entrustNum).change();
    }
    /**
     * 计算净价、全价、到期收益率。
     *
     */
    var calcPriceAndYield = function(init) {
        if(init || setPageItemsFlag){
            return;
        }

        var finprodId = $("#finprodId_" + currentTab).val();
        // 默认使用百元券面计算
        var prinAmt = new BigNumber(100);
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        // 以下条件不满足时直接返回
        if(finprodType2 != FINPROD_TYPE_TG_F01 || !finprodId || !prinAmt) {
            return;
        }
        var tradeMarket = $("#tradeMarket_" + currentTab).val();
        var ps = $("#ps_" + currentTab).val();
        var calculateField = $("[name=calculateField_" + currentTab + "]:radio").next(".layui-form-radioed").prev("input").val();
        var priceModel = $("#priceModel_" + currentTab).val();
        var param = {
            finprodId: finprodId,
            prinAmt: prinAmt,
            tradeMarket: tradeMarket,
            ps: ps,
            calculateField: calculateField
        };
        // 选择的计算方式为“净价”时
        if(calculateField == SEC_CALC_MODE_01) {
            var price = $("#price_" + currentTab).val();
            if(priceModel == PRICE_MODEL_PRM03){
                var price = $("#factPrice_" + currentTab).val();
            }
            // 首先格式化价格
            formatEntrustPrice();
            if(!resetFlag && price) {
                param.price = price;
                Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/calcPriceAndYield', param, function(result) {
                    var fprice = formatPrice(price, result.fprice);
                    $("#fprice_" + currentTab).val(fprice).trigger('blur');
                    $("#yield_" + currentTab).val(result.yield).trigger('blur');
                }, false);
                calcEntrustNumAndAmt();
            }
            else {
                $("#fprice_" + currentTab).val('').trigger('blur');
                $("#yield_" + currentTab).val('').trigger('blur');
            }
            // 计算完净价需要计算数量或金额
            // calcEntrustNumAndAmt();
        }
        // 选择的计算方式为“全价”时
        else if(calculateField == SEC_CALC_MODE_02) {
            var fprice = $("#fprice_" + currentTab).val();
            fprice = formatPrice(fprice);
            if(!resetFlag && fprice) {
                param.fprice = fprice;
                Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/calcPriceAndYield', param, function(result) {
                    var cprice = formatPrice(fprice,result.cprice);
                    $("#price_" + currentTab).val(cprice).trigger('blur');
                    $("#yield_" + currentTab).val(result.yield).trigger('blur');
                    // 设置净价后格式化价格
                    calcEntrustNumAndAmt();
                }, false);
            }
            else {
                $("#price_" + currentTab).val('').trigger('blur');
                $("#yield_" + currentTab).val('').trigger('blur');
                // 计算完净价需要计算数量或金额
                calcEntrustNumAndAmt();
            }
        }
        // 选择的计算方式为“到期收益率”时
        else if(calculateField == SEC_CALC_MODE_03) {
            var yield = $("#yield_" + currentTab).val();
            if(!resetFlag && yield) {
                param.yield = yield;
                Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/calcPriceAndYield', param, function(result) {
                    var cprice = formatPrice(result.cprice);
                    var fprice = formatPrice(result.cprice,result.fprice);
                    $("#price_" + currentTab).val(cprice).trigger('blur');
                    $("#fprice_" + currentTab).val(fprice).trigger('blur');
                    // 计算完净价需要计算数量或金额
                    calcEntrustNumAndAmt();
                }, false);
            }
            else {
                $("#price_" + currentTab).val('').trigger('blur');
                $("#fprice_" + currentTab).val('').trigger('blur');
                // 计算完净价需要计算数量或金额
                calcEntrustNumAndAmt();
            }
        }
    }

    /**
     * 根据金融产品类型和交易场所联动价格小数位数（包含净价和委托价格）
     *          债券      股票      回购      基金
     * 上交所   0.01      0.01     0.005      0.001
     * 深交所   0.001     0.01     0.001      0.001
     */
    var formatEntrustPrice = function(calcEntrustNumAndAmt) {
        var entrustPrice = null;
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var ps = $("#ps_" + currentTab).val();
        var priceModel = $("#priceModel_" + currentTab).val();
        if(ps == PS_T46 || ps == PS_T97 || ps == PS_T03 ||/* ps == PS_T04 ||*/ ps == PS_T49 || ps == PS_T68 || ps == PS_T69
        /*||((ps == PS_03 || ps == PS_04) && currentTab == 'dz')*/) {
            if(BOND_SCALE){
                entrustPrice = $("#entrustPrice_" + currentTab).val();
                $("#entrustPrice_" + currentTab).val(new Number(entrustPrice).toFixed(BOND_SCALE));
            }
            return;
        }
        if(priceModel == PRICE_MODEL_PRM03){
            entrustPrice = $("#factPrice_" + currentTab).val();
        }else{
            // 根据金融产品类型和交易方向取得不同的价格字段
            if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
                entrustPrice = $("#price_" + currentTab).val();
            }
            else {
                entrustPrice = $("#entrustPrice_" + currentTab).val();
            }
        }
        // 指令价格非市价
        if (entrustPrice) {
            if (priceModel != PRICE_MODEL_PRM03) {
                var price = formatPrice(entrustPrice);
                // 根据金融产品类型和交易方向取得不同的价格字段
                if (finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
                    entrustPrice = $("#price_" + currentTab).val(price);
                } else {
                    entrustPrice = $("#entrustPrice_" + currentTab).val(price);
                }
            }
            if (calcEntrustNumAndAmt) {
                calcEntrustNumAndAmt();
            }
        }
    };

    /**
     * 风险试算时不需要进行风险试算的校验
     */
    var isNeedRiskcal = function (){
        //质押券入库和出库不需要走风险试算
        ps = $("#ps_" + currentTab).val();
        if(ps == PS_T68 || ps == PS_T69) {
            return false;
        }
        return true;
    }


    /**
     * 查询投资目的
     */
    var getInvAim = function() {
        var secId = $("#secId_" + currentTab).val();
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var invAim;
        // 如果金融产品类型债券”,需要投资目的
        if(secId &&  finprodType2 == FINPROD_TYPE_TG_F01) {
            Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/getInvAim', null, function(result) {
                invAim = result;
            });
        }
        return invAim;
    };

    /**
     *联动带出，指令价格
     **/
    function setQuotaPrice(quotaPrice) {
        var ps = $("#ps_" + currentTab).val();
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        if(finprodType2 == FINPROD_TYPE_TG_F01 && isStandardPs(ps)) {
            $("#price_" + currentTab).val(quotaPrice).trigger('blur').trigger('change');
        }
        else {
            $("#entrustPrice_" + currentTab).val(quotaPrice).trigger('blur').trigger('change');
        }
    };

    /**
     * 联动指令价格、指令数量、指令金额 callback1 格式化指令价格 callback2 计算数量与金额 callback3 设置价格小数位 callback4 获取行情
     */
    var standardizedPrice = function(param, callback3, callback4) {
        if (callback3) {
            if (callback4) {
                callback3(param, callback4);
            } else {
                callback3(result);
            }
        }
    };


    /**
     * 获取风险试算标签提示信息
     * @returns
     */
    function getRiskMathPrompt() {
        var ps = $("#ps_" + currentTab).val();
        var targetType = $("#targetType_" + currentTab).val();
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
        }else if(currentTab == 'jj' && finprodType2 == FINPROD_TYPE_TG_ZYSHG) {
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
        if(targetType == TARGET_CLASS_01 && !$("#entrustNum_" + currentTab).val()){
            return 'EtrExchangeTrade001.entrustNum';
        }
        if(!$("#entrustAmt_" + currentTab).val()){
            return 'EtrExchangeTrade001.entrustAmt';
        }
        return 'EtrExchangeTrade001.entrustAmt';
    };

    /***行情相关方法 add by chaowei begin*/

    /**根据市场代码，查询行情价格，展示出来*/
    var queryJJHQPrice = function(param,callback) {
        $.ajax({
            /**临时用这个地址，后面调整！！！！**/
            //url: "http://192.168.70.3:8081/tams/joyin/quote/tick?stock_code="+param.secId,
            url: basePath + "joyin/quote/tick?stock_code="+param.secId,
            async: false,
            type: "GET",
            dataType:"json",
            success: function(info) {
                var result = info.data;
                if (result != null && result != '') {
                    //债券买卖、质押券正逆回购 指令头寸、风控按照最新价格、回购利率控制；
                    if(param.finprodType2 == FINPROD_TYPE_TG_F01 || param.finprodType2 == FINPROD_TYPE_TG_ZYSHG || param.finprodType2 == FINPROD_TYPE_TG_F02 || param.finprodType2 == FINPROD_TYPE_TG_F03){
                        //场内交易指令，新建 。市价指令时 ，先取最新价，再去收盘价，收盘价都没有才不能下指令
                        var lastPrice = formatPrice(result.lastPrice==0||result.lastPrice==""?result.preClosePrice:result.lastPrice);
                        $("#factPrice_" + currentTab).val(lastPrice).trigger('change');
                        $.extend(param,{entrustPrice: lastPrice});
                    }else if(param.finprodType2 == FINPROD_TYPE_TG_F06 || param.finprodType2 == FINPROD_TYPE_TG_F10){
                        if(param.ps == PS_T01){//市价指令股票、基金买入 价格按涨停价计算风控、头寸
                            var upperLimitPrice = formatPrice(result.upperLimitPrice);
                            $("#factPrice_" + currentTab).val(upperLimitPrice).trigger('change');
                            $.extend(param,{entrustPrice: upperLimitPrice});
                        }else if(param.ps == PS_T02){//市价指令股票基金卖出 价格按跌停价计算风控、头寸
                            var lowerLimitPrice = formatPrice(result.lowerLimitPrice);
                            $("#factPrice_" + currentTab).val(lowerLimitPrice).trigger('change');
                            $.extend(param,{entrustPrice: lowerLimitPrice});
                        }
                    }
                    if(callback){
                        return callback(param);
                    }
                }else {
                    if(callback){
                        return callback(param);
                    }
                }
            }
        });
    };

    var addMinPriceStep = function(type,num,input) {
        var $secIdObj = $("#secIdObjRelative_" + currentTab).val();
        if ($secIdObj) {
            var $obj = JSON.parse($secIdObj);
            $.extend($obj,{price: num});
            var precision = getMinPrice($obj);
            if (precision) {
                input.val(getNumResult(type,num,Number(precision.minPrice))).blur().trigger('change');
                return;
            }
        }
        input.val(getNumResult(type,num ,Number(0.01))).blur().trigger('change');
    }



    var addMinNumStep = function(type,num,input){
        var finprodId = $("#finprodId_" + currentTab).val();
        var targetType = $("#targetType_" + currentTab).val();
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        var tradeMarket = $("#tradeMarket_" + currentTab).val();
        var ps = $("#ps_" + currentTab).val();
        if(targetType) {
            var step = 1;
            var param = {
                finprodId: finprodId,
                targetType: targetType,
                finprodType2: finprodType2,
                tradeMarket: tradeMarket,
                ps: ps,
                currentTab: currentTab
            };
            Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/getMinNumStep', param, function(result) {
                if (result.minNum){
                    input.val(getNumResult(type,num ,Number(result.minNum))).blur().trigger('change');
                }
            }, false);
            return
        }
        input.val(getNumResult(type,num ,Number(1))).blur().trigger('change');
    }

    var getScale = function(){
        var $secIdObj = $("#secIdObjRelative_" + currentTab).val();
        if ($secIdObj) {
            var $obj = JSON.parse($secIdObj);
            return getMinPrice($obj).scale;
        }
        return scale;
    }

    /***
     * 双击大宗行情，联动带出指令录入。
     */
    var dblDzHqTable =function (data) {
        //设置指令记录操作类型
        $("#entrustId_" + currentTab).val('').trigger('change');
        optType = ENTRUST_RECORD_NEW;
        var secId = $("#secId_" + currentTab).val();
        //返回指令DTO
        var zlobj = new Object();
        if(secId) {
            //var object = getSecIdSelectObjectData(secId);
            var object = $("#secIdObjRelative_" + currentTab).val();
            if (object) {
                object = JSON.parse(object);
                //交易场所
                var tradeMarket=object.tradeMarket;
                //行情类型
                var quotationType = data.quotationType;
                //交易场所 = 深圳证券交易所 and 行情类型 = 定价申报
                if(tradeMarket=='04' && quotationType=='PRM07') {
                    //金融产品类型 （投管）
                    zlobj.finprodType2 = object.finprodType2;
                    //金融产品代码
                    zlobj.finprodId = object.finprodId;
                    //证券代码
                    zlobj.secId = secId;
                    //行情方向
                    if(data.entrustPs=="T01") {//买入
                        zlobj.ps="T02";
                    }else if(data.entrustPs=="T02") {//卖出
                        zlobj.ps="T01";
                    }
                    if(data.entrustPs=="03") {// 正回购
                        zlobj.ps="04";
                    }else if(data.entrustPs=="04") {// 逆回购
                        zlobj.ps="03";
                    }
                    //指令价格
                    zlobj.entrustPrice = formatPrice(data.entrustPrice);
                    if(data.entrustPs == "03" || data.entrustPs=="04") {// 正回购 或 逆回购
                        // 指令数量
                        zlobj.entrustNum=data.entrustNum;
                        // 指令金额
                        zlobj.entrustAmt=data.entrustNum * 100;
                    }else{
                        // 指令数量
                        zlobj.entrustNum=data.entrustNum;
                    }
                    //价格模式  限价价格
                    zlobj.priceModel='PRM02';
                    //申报类型
                    zlobj.declaType='PRM08';
                    //交易场所
                    zlobj.tradeMarket= object.tradeMarket;
                    //交易平台
                    zlobj.tradePlat='04';
                    //金融产品类型（投管） =债券
                    // if(object.finprodType2='F01') {
                    //     zlobj.invAim = getInvAim();
                    // }
                    //对方席位
                    zlobj.seatId = data.oppositeSeat;
                    //约定号
                    zlobj.agreeOnId= data.agreeNum;

                    //数据来源 = 01：市场行情
                    zlobj.dataSource='01';

                    //指令数量 min（选择记录.数量，可卖数量）
                    var enNum = queryHqAvailableNum();
                    if(enNum){
                        zlobj.entrustNum= data.entrustNum < enNum ? data.entrustNum : enNum;
                    }
                }

            }
        }
        return zlobj;
    }

    /***
     * 双击大宗盘后行情，联动带出指令录入。
     */
    var dblPhHqTable =function (data) {
        //设置指令记录操作类型
        $("#entrustId_" + currentTab).val('').trigger('change');
        optType = ENTRUST_RECORD_NEW;
        var secId = $("#secId_" + currentTab).val();
        //返回指令DTO
        var zlobj = new Object();
        if(secId) {
            //var object = getSecIdSelectObjectData(secId);
            var object = $("#secIdObjRelative_" + currentTab).val();
            if (object) {
                object = JSON.parse(object);
                //交易场所
                var tradeMarket=object.tradeMarket;
                //行情类型
                var quotationType = data.quotationType;
                //金融产品类型 （投管）
                zlobj.finprodType2 = object.finprodType2;
                //金融产品代码
                zlobj.finprodId = object.finprodId;
                //证券代码
                zlobj.secId = secId;
                if(data.closingPrice) {//收盘价不为空
                    //指令价格
                    zlobj.entrustPrice=data.closingPrice;
                    //
                    zlobj.priceModel='PRM02';
                    //申报类型
                    zlobj.declaType='PRM10';

                }else if(data.dailyAvgPrice) {//日均价固定价格申报
                    //指令价格
                    zlobj.entrustPrice=data.dailyAvgPrice;
                    //价格模式
                    zlobj.priceModel='PRM02';
                    //价格模式
                    zlobj.declaType='PRM11';
                }

                //交易场所
                zlobj.tradeMarket=object.tradeMarket;
                //交易平台
                zlobj.tradePlat='04';
                // //金融产品类型（投管） =债券
                // if(object.finprodType2='F01') {
                //     zlobj.invAim = getInvAim();
                // }
            }
        }
        return zlobj;
    }


    /***
     * 双击固收行情，联动带出指令录入。
     */
    var dblGsHqTable =function (data) {
        //设置指令记录操作类型
        $("#entrustId_" + currentTab).val('').trigger('change');
        optType = ENTRUST_RECORD_NEW;
        //返回指令DTO
        var zlobj = new Object();
        if (data) {
            //证券代码
            zlobj.secId=data.secId;
            //净价
            zlobj.price=data.nPrice;
            //全价
            zlobj.fprice=data.fPrice;
            //收益率
            zlobj.yield=data.yield;
            //行情方向
            if(data.entrustPs=="T01") {//买入
                zlobj.ps="T02";
            }else if(data.entrustPs=="T02") {//卖出
                zlobj.ps="T01";
            }

            //指令数量 min（选择记录.数量，可卖数量）
            var enNum = queryHqAvailableNum();
            if (enNum) {
                zlobj.entrustNum = data.entrustNum < enNum ? data.entrustNum: enNum;
            } else {
                zlobj.entrustNum = data.entrustNum;
            }

            //价格模式--点击成交申报
            zlobj.declaType='PRM08';

            //交易平台--固定收益平台
            zlobj.tradePlat='03';

            //交易场所 上海证券交易所
            zlobj.tradeMarket='03';

            //数据来源 = 01：市场行情
            zlobj.dataSource='01';

        }
        return zlobj;
    }
    /***行情相关方法 add by chaowei end*/


    /**
     * 查看资产详情事件
     */
    var searchDetail = function() {
        var finprodId =  $('#finprodId_' + currentTab).val();
        var finprodType2 =  $('#finprodType2_' + currentTab).val();
        if (finprodId != "" && FINPROD_TYPE_TG_ZYSHG != finprodType2){
            var url = "invest/asset/bond/FinBondTargetAsset002/init"; // 默认债券标的
            if (FINPROD_TYPE_TG_F06 == finprodType2){
                var flagStr = finprodId.substr(finprodId.length-2,finprodId.length);
                // 场内基金标的
                url = "invest/asset/exfund/FinExFundTarget002/init";
                if (flagStr == "OF"){
                    // 公募基金标的
                    url = "invest/asset/fund/FinFundTarget002/init";
                }
            } else if (FINPROD_TYPE_TG_F10 == finprodType2){
                // 股票标的
                url = "invest/asset/stock/FinStockInfo002/init";
            }
            var param = {
                pageModel:PAGE_MODEL_REFERENCE,
                isQuickInput:true,
                finprodId:finprodId
            };
            parent.EtrExchangeTradeMain.dialogSelf.dialog(url,param);
        } else if (FINPROD_TYPE_TG_ZYSHG == finprodType2){
            Joyin.error("质押式回购不支持查看资产详情")
        } else {
            Joyin.error("资产名称不能为空")
        }
    };

    var createMultiPtl = function(){
        Joyin.dialog(JoyinUtil.getLabel("新建多组合维护"), basePath
            + "/invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance001/init", "99%", "99%", {
            pageModel : PAGE_MODEL_NEW,
        }, function() {

        });
    };

    /**
     * 画面加载完成
     */
        //初始化时，是否隐藏左上角内容
    var initPage = function(){
            $("#businessModelDiv_jj").hide();
            $("#businessModelDiv_gd").hide();
            $("#businessModelDiv_dz").hide();

            initFlag = true;
            hideLeftTopPanel();
            if(parentPageModel==PAGE_MODEL_REFERENCE || parentPageModel==PAGE_MODEL_EDIT){
                // 隐藏重置按钮
                if(isMulti == 'Y') {
                    linkTradePlat(entrustType, true);
                }
                portfolioTreeIdChange(true);
                targetTypeChange(true);
                entrustNumChange();
                psChange(true);
                priceModelChange(true,true);
                formatEntrustPrice();
                //页面初始化结束后  是否弹出 <<剩余可用数量存在零股，是否卖出零股？>>
                if(availableNum!=""){
                    window.parent.checkSalesOfSmallShares(availableNum);
                }

            }
            setTimeout(function () {
                initFlag = false;
            }, 3000 );

            if(calculateField){
                $("input[name=calculateField_" + currentTab + "][value='"+ calculateField +"']").attr("checked",true);
                layui.form.render(); //更新
            }

        };

    /**
     * 联动证券代码用交易场所变更时，联动证券代码
     */
    var acctTradeMarketChange = function() {
        // 判断当前选择的证券代码对应的交易场所是否与账户对应的交易场所一致，不一致则清空证券代码
        var acctTradeMarket = $("#acctTradeMarket_" + currentTab).val();
        var secId = $("#secId_" + currentTab).val();
        var object = $("#secIdObjRelative_" + currentTab).val();
        if(acctTradeMarket && secId) {
            if(object) {
                object = JSON.parse(object);
                if(object.tradeMarket != acctTradeMarket) {
                    $("#secId_" + currentTab).val('').trigger('change');
                }
            }
            else {
                $("#secId_" + currentTab).val('').trigger('change');
            }
        }
    };

    /**
     * 申报类型的联动处理
     */
    var declaTypeChange = function(init, isNotTrigger) {
        priceModelChange(init,isNotTrigger);
    };

    /**
     * 查询可用持仓
     */
    var queryHqAvailableNum = function() {
        var portfolioId = $("#portfolioId_" + currentTab).val();
        var finprodId = $("#finprodId_" + currentTab).val();
        var secAcctId = $("#secAcctId_" + currentTab).val();
        var finprodType2 = $("#finprodType2_" + currentTab).val();

        var availableNum;

        // 如果金融产品类型不是“债券”，则可以不需要投资目的
        if(portfolioId && secAcctId && finprodId) {
            var param = {
                portfolioId: portfolioId,
                finprodId: finprodId,
                secAcctId: secAcctId
            }
            Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/selectAvailableNum', param, function(result) {
                availableNum = result;
            });
        }
        return availableNum;
    };

    /**
     * 证券代码变更时，联动其他字段
     * 联动字段：证券名称、金融产品类型、交易场所、金融产品代码
     * 此处联动金融产品类型，再通过金融产品类型联动委托方向
     */
    var secIdChange = function() {
        // 证券代码改变，清空数据来源
        $("#dataSource_" + currentTab).val('').trigger('change');
        var secId = $("#secId_" + currentTab).val();

        if(secId) {
            var object = getSecIdSelectObjectData(secId);
            $("#secIdObjRelative_" + currentTab).val(JSON.stringify(object)).trigger('change');
            // if(currentTab == 'gd') {
            //     if(object){//固定收益平台 设置清算速度
            //         setSettleSpeed(object.isNetSett);
            //     }
            // }
        }
        // 证券代码选择后联动【行情信息】
        if(currentTab == 'jj') {
            if(parent.EtrExchangeTradeHq){
                parent.EtrExchangeTradeHq.showFiveLevel();
            }
            if(secId) {
                var object = $("#secIdObjRelative_" + currentTab).val();
                if(object) {
                    object = JSON.parse(object);
                    type9 = object.assetType1;
                    if(object.othCodeType && (object.othCodeType == FINPROD_OTH_CODE_TYPE_06 || object.othCodeType == FINPROD_OTH_CODE_TYPE_05)){
                        secId = object.marketId;
                        if(parent.EtrExchangeTradeHq) {
                            parent.EtrExchangeTradeHq.hideFiveLevel();
                        }
                    }
                    if(secId) {
                        if(parent.EtrExchangeTradeHq) {
                            if (useInterfQuoteFlag) {
                                parent.EtrExchangeTradeHq.queryJJHQXXByWS(secId, object.finprodName, object.finprodType2);
                            } else {
                                parent.EtrExchangeTradeHq.queryJJHQXX(secId, object.finprodName, object.finprodType2);
                            }
                        }
                    }else {
                        if(parent.EtrExchangeTradeHq) {
                            parent.EtrExchangeTradeHq.updateLevel2();
                        }
                    }
                }
            }
        }
        else if(currentTab == 'dz') {
            if(secId) {
                if(parent.EtrExchangeTradeHq) {
                    parent.EtrExchangeTradeHq.loadDzHqTable();
                }
            } else{
                //清空行情列表
                if(parent.EtrExchangeTradeHq) {
                    parent.EtrExchangeTradeHq.clearDzHqTable();
                }
            }
        }else if(currentTab == 'gd') {
            if(secId) {
                if(parent.EtrExchangeTradeHq) {
                    parent.EtrExchangeTradeHq.loadGdHqTable();
                }
            } else{
                //清空行情列表
                if(parent.EtrExchangeTradeHq) {
                    parent.EtrExchangeTradeHq.clearGdHqTable();
                }
            }
        }
        if(secId) {
            var object = $("#secIdObjRelative_" + currentTab).val();
            if(object) {
                object = JSON.parse(object);
                $("#finprodId_" + currentTab).val(object.finprodId).trigger('change');
                $("#finprodName_" + currentTab).val(object.finprodName).trigger('change');
                $("#tradeMarket_" + currentTab).val(object.tradeMarket).trigger('change');
                $("#finprodType2_" + currentTab).val(object.finprodType2 || FINPROD_TYPE_TG_ZYSHG).trigger('change');
                $("#board_" + currentTab).val(object.board).trigger('change');
            }
            else {
                $("#finprodId_" + currentTab).val('').trigger('change');
                $("#finprodName_" + currentTab).val('').trigger('change');
                $("#tradeMarket_" + currentTab).val('').trigger('change');
                $("#finprodType2_" + currentTab).val('').trigger('change');
                $("#board_" + currentTab).val('').trigger('change');
                if(type9) {
                    type9 = '';
                }
            }
        }
        else {
            $("#finprodId_" + currentTab).val('').trigger('change');
            $("#finprodName_" + currentTab).val('').trigger('change');
            $("#tradeMarket_" + currentTab).val('').trigger('change');
            $("#finprodType2_" + currentTab).val('').trigger('change');
            $("#board_" + currentTab).val('').trigger('change');
            if(type9) {
                type9 = '';
            };
        }
        
        linkRefreshTable();
    };

    /**
     * 根据参数获取证券代码对应的Object属性
     * secId:证券代码
     */
    var getSecIdSelectObjectData = function(secId) {
        var tradePlat = SHARES_TRADE_PLATFORM_02;
        if(currentTab == 'dz') {
            tradePlat = SHARES_TRADE_PLATFORM_04;
        } else if(currentTab == 'gd') {
            tradePlat = SHARES_TRADE_PLATFORM_03;
        }
        var result = {};
        var param = {secId:secId, tradePlat:tradePlat};
        Joyin.ajaxSync(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/getFinprodMarketInfo', param, function(page) {
            if(page && page.root && page.root.length > 0) {
                result = page.root[0].object || {};
            }
        });
        return result;
    };

    /**
     * 金融产品类型（投管）变更时
     */
    var finprodType2Change = function() {
        // 此处联动委托方向会带动价格模式和委托价格显示隐藏
        linkPs();
        linkEntrustNumUnit();
        //add by chenjiaxing 股票与基金时投资目的字段不显示 start
        var finprodType2 = $("#finprodType2_" + currentTab).val();
        if((currentTab == 'jj' && finprodType2 && finprodType2 == FINPROD_TYPE_TG_F06)
            ||(currentTab == 'dz' && finprodType2 && finprodType2 == FINPROD_TYPE_TG_F06)
            ||(currentTab == 'gd' && finprodType2 && finprodType2 == FINPROD_TYPE_TG_F06)){
            $("#invAim_div_" + currentTab).hide();
        }else if((currentTab == 'jj' && finprodType2 && finprodType2 == FINPROD_TYPE_TG_F10)
            ||(currentTab == 'dz' && finprodType2 && finprodType2 == FINPROD_TYPE_TG_F10)
            ||(currentTab == 'gd' && finprodType2 && finprodType2 == FINPROD_TYPE_TG_F10)){
            $("#invAim_div_" + currentTab).hide();
        }else{
            $("#invAim_div_" + currentTab).show();
        }
        //add by chenjiaxing 股票与基金时投资目的字段不显示 end
    };

    /**
     * 数据来源变更时
     */
    var dataSourceChange = function() {
        linkDeclaType();
    };
    /**===============================================页面业务处理方法end========================================= **/

    /**===============================================页面联动的方法start========================================= **/


    /**
     *
     * @param
     * @returns
     */
    function hideLeftTopPanel(){
        if(parentPageModel==PAGE_MODEL_NEW){
            window.parent.showLeftTopPanel();
        } else {
            if(window.parent.hideLeftTopPanel) {
                window.parent.hideLeftTopPanel();
            }
        }
    }

    /**
     * 取消,清空指令数据
     */
    var doReset = function(opt) {
        //设置指令记录操作类型
        optType = ENTRUST_RECORD_NEW;
        //清空操作开始
        resetFlag = true;
        $("#entrustId_" + currentTab).val('').trigger('change');
        $("#entrustStatus_" + currentTab).val('').trigger('change');
        $("#portfolioTreeId_" + currentTab).val("").trigger('change');
        $("#secId_" + currentTab).val("").trigger('change');
        $("#ps_" + currentTab).val('').trigger('change');
        $("#priceModel_" + currentTab).val('').trigger('change',true);
        $("#targetType_" + currentTab).val('').trigger('change');
        $("#price_" + currentTab).val('').trigger('change');
        $("#fprice_" + currentTab).val('').trigger('change');
        $("#yield_" + currentTab).val('').trigger('change');
        $("#entrustPrice_" + currentTab).val('').trigger('change');
        $("#entrustNum_" + currentTab).val('').trigger('change');
        $("#entrustAmt_" + currentTab).val('').trigger('change');
        $("#toShareAvailableNum_" + currentTab).val('').trigger('change');
        $("#invAim_" + currentTab).val('').trigger('change');
        $("#weightType_" + currentTab).val('').trigger('change');
        $("#mulDistriClass_" + currentTab).val('').trigger('change');
        $("#targetValue_" + currentTab).val('').trigger('change');

        if(currentTab == 'dz' || currentTab == 'gd') {
            $("#declaType_" + currentTab).val('').trigger('change');
            $("#counterId_" + currentTab).val('').trigger('change');
            $("#seatId_" + currentTab).val('').trigger('change');
            $("#agreeOnId_" + currentTab).val('').trigger('change');

            if(currentTab == 'gd') {
                $("#trader_" + currentTab).val('').trigger('change');
                $("#counterOrg_" + currentTab).val('').trigger('change');
                $("#counterTrader_" + currentTab).val('').trigger('change');
                $("#settleSpeed_" + currentTab).val('').trigger('change');
            }
        }


        /***
         * 清空行情列表
         */
        if(currentTab=='jj') {
            parent.EtrExchangeTradeHq.updateLevel2();
        }

        if(currentTab=='dz') {
            parent.EtrExchangeTradeHq.clearDzHqTable();
        }

        if(dateTable){
            dateTable.clear(function () {

            });
        }
        //清空操作结束
        resetFlag = null;
        //清空风险试算结果
        riskResultdata = null;
        cluflag = true;
    };


    /**
     * 设置当前tab页
     * @return
     */
    var setCurrentTab = function (tab){
        currentTab=tab;
        if(currentTab == 'jj') {
            $("#content_div_jj").show();
            $("#content_div_dz").hide();
            $("#content_div_gd").hide();
        } else if(currentTab == 'dz') {
            $("#content_div_jj").hide();
            $("#content_div_dz").show();
            $("#content_div_gd").hide();
        }else if(currentTab == 'gd') {
            $("#content_div_jj").hide();
            $("#content_div_dz").hide();
            $("#content_div_gd").show();
        }
        if(parentPageModel==PAGE_MODEL_NEW){
            if(dateTable){
                dateTable.clear(function () {

                });
            }
        }
    }

    var linkRefreshTable = function(){
        //刷新组合分配
        if(dateTable) {
            dateTable.reload();
        }
        var mulDistriClass = $("#mulDistriClass_" + currentTab).val();
        var isShowProportion = (mulDistriClass == MUL_DISTRI_CLASS_03|| mulDistriClass == MUL_DISTRI_CLASS_04) ? true:false;
        if($("#finprodType2_" + currentTab).val() == FINPROD_TYPE_TG_F01) {
            if($("#ps_" + currentTab).val() == PS_T68 || $("#ps_" + currentTab).val() == PS_T69
                || ($("#ps_" + currentTab).val() == PS_T100 && mulDistriClass == MUL_DISTRI_CLASS_10)) {
                dateTable.showColumns(
                    [{field:'portfolioName',visible:true} , {field:'acctName',visible:true},
                        {field:'finprodMarketId',visible:true}, {field:'finprodName',visible:true} ,
                        {field:'proportionStr',visible:isShowProportion} ,{field:'psStr',visible:true},
                        {field:'price',visible:false},  {field:'fprice',visible:false},
                        {field:'yield',visible:false}, {field:'entrustPrice',title: 'EtrExchangeTrade001.conversionRate',visible:true},
                        {field:'entrustNum',visible:true}, {field:'entrustAmt',title: 'EtrExchangeTrade001.secNum',visible:true},
                        {field:'availableCashT',visible:true}, {field:'availablePositionsT',visible:true},
                        {field:'availableCashT1',visible:true}, {field:'availablePositionsT1',visible:true},
                        {field:'productNetValue',visible:true}]);
            }
            else {
                dateTable.showColumns(
                    [{field:'portfolioName',visible:true} , {field:'acctName',visible:true},
                        {field:'finprodMarketId',visible:true}, {field:'finprodName',visible:true} ,
                        {field:'proportionStr',visible:isShowProportion} ,{field:'psStr',visible:true},
                        {field:'price',visible:true},  {field:'fprice',visible:true},
                        {field:'yield', visible:true}, {field:'entrustPrice',title: 'EtrExchangeTrade003.price',visible:false},
                        {field:'entrustNum',visible:true}, {field:'entrustAmt',title: 'EtrExchangeTrade001.entrustAmt',visible:true},
                        {field:'availableCashT',visible:true}, {field:'availablePositionsT',visible:true},
                        {field:'availableCashT1',visible:true}, {field:'availablePositionsT1',visible:true},
                        {field:'productNetValue',visible:true}]);
            }

        }
        else if($("#finprodType2_" + currentTab).val() == FINPROD_TYPE_TG_ZYSHG){
            dateTable.showColumns(
                [{field:'portfolioName',visible:true} , {field:'acctName',visible:true},
                    {field:'finprodMarketId',visible:true}, {field:'finprodName',visible:true} ,
                    {field:'proportionStr',visible:isShowProportion} ,{field:'psStr',visible:true},
                    {field:'price',visible:false},  {field:'fprice',visible:false},
                    {field:'yield', visible:true}, {field:'entrustPrice',title: 'EtrExchangeTrade001.repoYield',visible:true},
                    {field:'entrustNum',visible:true}, {field:'entrustAmt', title: 'EtrExchangeTrade001.entrustAmt', visible:true},
                    {field:'availableCashT',visible:true}, {field:'availablePositionsT',visible:true},
                    {field:'availableCashT1',visible:true}, {field:'availablePositionsT1',visible:true},
                    {field:'productNetValue',visible:true}]);
        }
        else if($("#finprodType2_" + currentTab).val() == FINPROD_TYPE_TG_F06){
            dateTable.showColumns(
                [{field:'portfolioName',visible:true} , {field:'acctName',visible:true},
                    {field:'finprodMarketId',visible:true}, {field:'finprodName',visible:true} ,
                    {field:'proportionStr',visible:isShowProportion} ,{field:'psStr',visible:true},
                    {field:'price',visible:false},  {field:'fprice',visible:false},
                    {field:'yield', visible:false}, {field:'entrustPrice',title: 'EtrExchangeTrade003.price',visible:true},
                    {field:'entrustNum',visible:true}, {field:'entrustAmt', title: 'EtrExchangeTrade001.entrustAmt', visible:true},
                    {field:'availableCashT',visible:true}, {field:'availablePositionsT',visible:true},
                    {field:'availableCashT1',visible:true}, {field:'availablePositionsT1',visible:true},
                    {field:'productNetValue',visible:true}]);

        }
        else if($("#finprodType2_" + currentTab).val() == FINPROD_TYPE_TG_F10){
            dateTable.showColumns(
                [{field:'portfolioName',visible:true} , {field:'acctName',visible:true},
                    {field:'finprodMarketId',visible:true}, {field:'finprodName',visible:true} ,
                    {field:'proportionStr',visible:isShowProportion} ,{field:'psStr',visible:true},
                    {field:'price',visible:false},  {field:'fprice',visible:false},
                    {field:'yield', visible:false}, {field:'entrustPrice',title: 'EtrExchangeTrade003.price',visible:true},
                    {field:'entrustNum',visible:true}, {field:'entrustAmt', title: 'EtrExchangeTrade001.entrustAmt', visible:true},
                    {field:'availableCashT',visible:true}, {field:'availablePositionsT',visible:true},
                    {field:'availableCashT1',visible:true}, {field:'availablePositionsT1',visible:true},
                    {field:'productNetValue',visible:true}]);
        }
        else {
            dateTable.showColumns(
                [{field:'portfolioName',visible:true} , {field:'acctName',visible:true},
                    {field:'finprodMarketId',visible:true}, {field:'finprodName',visible:true} ,
                    {field:'proportionStr',visible:isShowProportion} ,{field:'psStr',visible:true},
                    {field:'price',visible:true},  {field:'fprice',visible:true},
                    {field:'yield', visible:true}, {field:'entrustPrice',title: 'EtrExchangeTrade003.price',visible:false},
                    {field:'entrustNum',visible:true}, {field:'entrustAmt', title: 'EtrExchangeTrade001.entrustAmt',visible:true},
                    {field:'availableCashT',visible:true}, {field:'availablePositionsT',visible:true},
                    {field:'availableCashT1',visible:true}, {field:'availablePositionsT1',visible:true},
                    {field:'productNetValue',visible:true}]);
        }


        linkTableData();
    }

    /**
     * 联动表数据
     */
    var linkTableData = function(){
        if(dateTable) {
            var mulDistriClass = $("#mulDistriClass_" + currentTab).val();
            var finprodType2 = $("#finprodType2_" + currentTab).val();
            var ps =  $("#ps_" + currentTab).val();
            var priceValue =  $("#price_" + currentTab).val();
            var fpriceValue =  $("#fprice_" + currentTab).val();
            var yieldValue =  $("#yield_" + currentTab).val();
            var entrustPriceValue =  $("#entrustPrice_" + currentTab).val();
            var array = dateTable.getAllRows();
            for(var i=0;i<array.length;i++) {
                //设置交易方向
                if(ps == PS_T100) {
                    array[i].ps = "";
                }
                else {
                    array[i].ps = ps?ps:"";
                }

                //设置价格
                if(finprodType2 == FINPROD_TYPE_TG_F01) {
                    if(ps == PS_T01 || ps == PS_T02) {
                        array[i].price = priceValue?priceValue:"";
                        array[i].fprice = fpriceValue?fpriceValue:"";
                        array[i].yield = yieldValue?yieldValue:"";
                    }
                    else if(ps == PS_T68 || ps == PS_T69 || ps == PS_T100) {
                        array[i].entrustPrice = entrustPriceValue ? entrustPriceValue:"";
                    }
                }
                else if(finprodType2 == FINPROD_TYPE_TG_ZYSHG){
                    array[i].entrustPrice = entrustPriceValue ? entrustPriceValue:"";
                }
                else {
                    array[i].entrustPrice = entrustPriceValue ? entrustPriceValue:"";
                }
            }

            for(var i=0;i<array.length;i++) {
                dateTable.renderRow(i,array[i])
            };
        }
    }
    /**
     * 003画面双击刷新001
     * @param data
     */
    var dbclickRefresh = function(data){
        //设置
        setEntrust001PageItems(data);
        /*$.each(data, function(id, value) {
            setData(id, value, '');
        });*/
        if(data.entrustId){
            //设置指令记录操作类型
            optType = ENTRUST_RECORD_MODIFY;
        }
        //双击校验后，将风险试算标识重置
        window.parent.dbclickRefreshRisk();
    }

    /**
     * 根据传入的数据，设置画面元素
     */
    var setEntrust001PageItems = function(param) {
        setPageItemsFlag = true;
        if(param) {
            if(param.entrustType) {
                linkTradePlat(param.entrustType, false);
            }
            if(param.entrustId) {
                $("#entrustId_" + currentTab).val(param.entrustId).trigger('change');
            }
            if(param.entrustStatus) {
                $("#entrustStatus_" + currentTab).val(param.entrustStatus).trigger('change');
            }
            if(param.portfolioTreeId) {
                $("#portfolioTreeId_" + currentTab).textSelect('setValue', param.portfolioTreeId);
            }
            if(param.acctNo) {
                $("#acctNo_" + currentTab).textSelect('setValue', param.acctNo);
            }
            if(param.secId) {
                $("#secId_" + currentTab).textSelect('setValue', param.secId);
            }
            /*if(param.finprodType2) {
                $("#finprodType2_" + currentTab).val(param.finprodType2).trigger('change');
            }*/
            if(param.ps) {
                $("#ps_" + currentTab).textSelect('setValue', param.ps);
            }
            if(param.tradeMarket) {
                $("#tradeMarket_" + currentTab).val(param.tradeMarket).trigger('change');
            }
            if(param.dataSource) {
                $("#dataSource_" + currentTab).val(param.dataSource).trigger('change');
            }
            if(param.priceModel) {
                $("#priceModel_" + currentTab).val(param.priceModel).trigger('change',true);
            }
            if(param.declaType) {
                $("#declaType_" + currentTab).val(param.declaType).trigger('change',true);
            }
            if(param.calMode) {
                //$("#declaType_" + currentTab).val(param.calMode).trigger('change');
                $("input[name=calculateField_" + currentTab + "][value='"+ param.calMode +"']").attr("checked",true);
                layui.form.render(); //更新
                calculateFieldChange();
            }
            // 需要根据金融产品类型设置不同的价格
            if(param.price) {
                if($("#finprodType2_" + currentTab).val() == FINPROD_TYPE_TG_F01 && isStandardPs(param.ps)) {
                    if(param.calMode && param.calMode == SEC_CALC_MODE_01){
                        $("#price_" + currentTab).val(param.price).trigger('blur').trigger('change');
                    }
                }
                else {
                    $("#entrustPrice_" + currentTab).val(param.price).trigger('blur').trigger('change');
                }
            }
            if(param.calMode && param.calMode == SEC_CALC_MODE_02 && param.fprice) {
                $("#fprice_" + currentTab).val(param.fprice).trigger('blur').trigger('change');
            }
            if(param.calMode && param.calMode == SEC_CALC_MODE_03 && param.yield) {
                $("#yield_" + currentTab).val(param.yield).trigger('blur').trigger('change');
            }
            if(param.entrustPrice) {
                if($("#finprodType2_" + currentTab).val() == FINPROD_TYPE_TG_F01 && isStandardPs(param.ps)) {
                    $("#price_" + currentTab).val(param.entrustPrice).trigger('blur').trigger('change');
                }
                else {
                    $("#entrustPrice_" + currentTab).val(param.entrustPrice).trigger('blur').trigger('change');
                }
            }

            if(param.entrustAmt) {
                $("#entrustAmt_" + currentTab).val(param.entrustAmt).trigger('blur').trigger('change');
            }

            //打开开关
            setPageItemsFlag = false;
            if(param.targetType) {
                $("#targetType_" + currentTab).val(param.targetType).trigger('change');
            }

            if(param.entrustNum) {
                $("#entrustNum_" + currentTab).val(param.entrustNum).trigger('blur').trigger('change');
            }
            if(param.invAim) {
                $("#invAim_" + currentTab).val(param.invAim).trigger('change');
            }
            if(param.counterId) {
                $("#counterId_" + currentTab).textSelect('setValue', param.counterId);
            }
            if(param.seatId) {
                $("#seatId_" + currentTab).val(param.seatId).trigger('change');
            }
            if(param.agreeOnId) {
                $("#agreeOnId_" + currentTab).val(param.agreeOnId).trigger('change');
            }
            if(param.counterOrg) {
                $("#counterOrg_" + currentTab).val(param.counterOrg).trigger('change');
            }
            if(param.counterTrader) {
                $("#counterTrader_" + currentTab).val(param.counterTrader).trigger('change');
            }
            if(param.trader) {
                $("#trader_" + currentTab).val(param.trader).trigger('change');
            }
            var array=param.tradeMultiPtl001Dtos;
            dateTable.clear(function () {
                for(var i;i<array.length;i++) {
                    dateTable.addRow(array[i]);
                }
            })
        }
        //计算价格和收益率
        calcPriceAndYield();

    }

    /**
     * 对外公开的接口
     */
    parent.EtrExchangeTradeMulti001 = {
        dbclickRefresh:dbclickRefresh,
        getSearchParams:getSearchParams,
        getRiskCheckData:getRiskCheckData,
        getRiskData:getRiskData,
        riskResultdata:riskResultdata,
        cluflag:cluflag,
        doReset:doReset,
        getRiskInterfUrl:getRiskInterfUrl,
        getCurrentTab:getCurrentTab,
        getDzSearchParams:getDzSearchParams,
        dblPhHqTable:dblPhHqTable,
        dblDzHqTable:dblDzHqTable,
        setCurrentTab:setCurrentTab,
        currentTab:currentTab,
        isNeedRiskcal:isNeedRiskcal,
        setQuotaPrice:setQuotaPrice,
        formatMoney:formatMoney,
        moneyDelete:moneyDelete,
        setTotalSharesNum: setTotalSharesNum,
        getScale:getScale,
        formatPrice:formatPrice
    }

    /**===============================================页面联动的方法end========================================= **/

    var dateTable = $("#dateTable").joyintable({
        ajax: {
            url: 'selectPortfolioTreeDetail',//请求路径
            type: 'POST',//请求类型
            data: function() {
                return getSearchParams();//获取请求参数
            }//ajax请求数据
        },
        searchOnInit:true,
        loadonce:true,
        page:false,
        cols:[
            {field:'portfolioId', title: 'EtrExchangeTrade002.portfolioId', sortField:'portfolioId',width : 200,edit : true,
                editOption:{
                    type: 'select', multiple: false, searchable: true, showField: 'id',
                    url: JoyinUtil.getRealPath("/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/selectPortfolioId"),
                    postData:function () {
                        return getSearchParams();
                    }
                }
            },
            {field:'portfolioName',title: 'EtrExchangeTrade002.portfolioName', sortField:'portfolioName',width : 200},
            {field:'acctNo', title: 'EtrExchangeTrade001.acctNo', width : 200,sortable:false,edit : true,
                editOption: {
                    type: 'select', multiple: false, searchable: true, showField: 'id',
                    url: JoyinUtil.getRealPath("/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/selectMultiAcctNo"),
                    postData:function () {
                        return {portfolioId:curPortfolioId,portfolioTreeId: $("#portfolioTreeId_"+currentTab).val()};
                    }
                }
            },
            {field:'finprodMarketId', title: 'EtrExchangeTrade001.finprodId', sortField:'finprodMarketId',width : 200},
            {field:'finprodName', title: 'EtrExchangeTrade001.finprodName', sortField:'finprodName',width : 200},
            {field:'proportion', title: 'EtrExchangeTrade001.proportion', sortField:'proportion',width : 200,edit : true},
            {field:'psStr', title: 'EtrExchangeTrade001.ps', sortField:'ps',width : 200},
            {field:'price', title: 'EtrExchangeTrade001.price', sortField:'price',width : 200,type:'number' ,digit:3 ,edit : true},
            {field:'fprice', title: 'EtrExchangeTrade001.fprice', sortField:'fprice',width : 200,type:'number' ,digit:3 ,edit : true},
            {field:'yield', title: 'EtrExchangeTrade001.yield', sortField:'yield',width : 200,type:'number' ,digit:6 ,edit : true},
            {field:'entrustPrice', title: 'EtrExchangeTrade003.price', sortField:'entrustPrice',width : 200,type:'number',digit:3 ,edit : true},
            {field:'entrustNum', title: 'EtrExchangeTrade001.entrustNum', sortField:'entrustNum',width : 200,type:'number',edit : true},
            {field:'entrustAmt', title: 'EtrExchangeTrade001.entrustAmt', sortField:'entrustAmt',width : 200,type:'number',edit : true},
            {field:'availableCashT', title: 'EtrExchangeTrade001.availableCashT', sortField:'availableCashT',width : 200,type:'number'},
            {field:'availablePositionsT', title: 'EtrExchangeTrade001.availablePositionsT', sortField:'availablePositionsT',width : 200,type:'number'},
            {field:'availableCashT1', title: 'EtrExchangeTrade001.availableCashT1', sortField:'availableCashT1',width : 200,type:'number'},
            {field:'availablePositionsT1', title: 'EtrExchangeTrade001.availablePositionsT1', sortField:'availablePositionsT1',width : 200,type:'number'},
            {field:'productNetValue', title: 'EtrExchangeTrade001.productNetValue', sortField:'productNetValue',width : 200,type:'number'},
            {field:'finprodId', visible:false},
            {field:'ps', visible:false},
            {field:'acctName', visible:false},
            {field:'secAcctId', visible:false},
            {field:'finprodType2', visible:false},
            {field:'operation', title: 'SysCommon.operation', type:'action', buttons: ['delt']}
        ],
        beforeEditCell:function (preVal, field, rowData){
            var calculateField = $("[name=calculateField_" + currentTab + "]:radio").next(".layui-form-radioed").prev("input").val();
            var ps =  $("#ps_" + currentTab).val();
            if((field=="price" || field=="fprice" || field=="yield") && ps != PS_T100){
                // 选择的计算方式为“净价”时
                if(calculateField == SEC_CALC_MODE_01 && field=="price") {
                    return true;
                }
                // 选择的计算方式为“全价”时
                else if(calculateField == SEC_CALC_MODE_02 && field=="fprice") {
                    return true;
                }
                // 选择的计算方式为“到期收益率”时
                else if(calculateField == SEC_CALC_MODE_03 && field=="yield") {
                    return true;
                }
                else {
                    return false;
                }
            }
            else if (field == "entrustPrice") {
                if(ps == PS_T68 || ps == PS_T69) {
                    return false;
                }
                else {
                    return true;
                }
            }
            else if(field == "acctNo") {
                curPortfolioId = rowData.portfolioId;
                return true;
            }
            else if(field == "proportion") {
                if($("#mulDistriClass_"+currentTab).val() == MUL_DISTRI_CLASS_03
                || $("#mulDistriClass_"+currentTab).val() == MUL_DISTRI_CLASS_04) {
                    return true;
                }
                else {
                    return false;
                }

            }
            else if (field == "portfolioId") {
                if(!$("#portfolioTreeId_"+currentTab).val()) {
                    return false;
                }
                else {
                    return true;
                }

            }
            else {
                return false;
            }
        },
        editCell : function(value, data, field, cell, column){
            if(field=="price" || field=="fprice" || field=="yield"){
                var finprodId = data.finprodId;
                // 默认使用百元券面计算
                var prinAmt = new BigNumber(100);
                var finprodType2 = data.finprodType2;
                var ps = data.ps;
                // 以下条件不满足时直接返回
                if(finprodType2 != FINPROD_TYPE_TG_F01 || !finprodId || !prinAmt || !ps) {
                    return;
                }
                var tradeMarket = $("#tradeMarket_" + currentTab).val();
                var calculateField = $("[name=calculateField_" + currentTab + "]:radio").next(".layui-form-radioed").prev("input").val();
                var priceModel = $("#priceModel_" + currentTab).val();
                var param = {
                    finprodId: finprodId,
                    prinAmt: prinAmt,
                    tradeMarket: tradeMarket,
                    ps: ps,
                    calculateField: calculateField
                };
                // 选择的计算方式为“净价”时
                if(calculateField == SEC_CALC_MODE_01) {
                    var price = data.price;
                    // 首先格式化价格
                    if(price) {
                        param.price = price;
                        Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/calcPriceAndYield', param, function(result) {
                            var fprice = formatPrice(price, result.fprice);
                            data.fprice = fprice;
                            data.yield = result.yield;
                        }, false);
                    }
                    else {
                        data.fprice = "";
                        data.yield = "";
                    }
                }
                // 选择的计算方式为“全价”时
                else if(calculateField == SEC_CALC_MODE_02) {
                    var fprice = data.fprice;
                    fprice = formatPrice(fprice);
                    if(fprice) {
                        param.fprice = fprice;
                        Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/calcPriceAndYield', param, function(result) {
                            var cprice = formatPrice(fprice,result.cprice);
                            data.price = cprice;
                            data.yield = result.yield;
                        }, false);
                    }
                    else {
                        data.price = "";
                        data.yield = "";
                    }
                }
                // 选择的计算方式为“到期收益率”时
                else if(calculateField == SEC_CALC_MODE_03) {
                    var yield = data.yield;
                    if(yield) {
                        param.yield = yield;
                        Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/calcPriceAndYield', param, function(result) {
                            data.price = formatPrice(result.cprice);
                            data.fprice = formatPrice(result.cprice,result.fprice);
                        }, false);
                    }
                    else {
                        data.price = "";
                        data.fprice = "";
                    }
                }

                var object = getSearchParams();
                var ptlList = [];
                ptlList.push(data);
                object.tradeMultiPtl001Dtos = ptlList;
                Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/calc', object, function(result) {
                    if(result) {
                        if(result.data&& result.data.tradeMultiPtl001Dtos) {
                            $("#targetType_"+currentTab).val("");
                            $("#targetType_"+currentTab).val(result.data.targetType);
                            for(var i=0;i<result.data.tradeMultiPtl001Dtos.length;i++) {
                                dateTable.renderRow(data.LAY_TABLE_INDEX, result.data.tradeMultiPtl001Dtos[i]);
                            }
                        }
                    }
                }, false);
            }
            else if (field == "entrustPrice") {
                var object = getSearchParams();
                var ptlList = [];
                ptlList.push(data);
                object.tradeMultiPtl001Dtos = ptlList;
                Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/calc', object, function(result) {
                    if(result) {
                        if(result.data&& result.data.tradeMultiPtl001Dtos) {
                            $("#targetType_"+currentTab).val("");
                            $("#targetType_"+currentTab).val(result.data.targetType);
                            for(var i=0;i<result.data.tradeMultiPtl001Dtos.length;i++) {
                                dateTable.renderRow(data.LAY_TABLE_INDEX, result.data.tradeMultiPtl001Dtos[i]);
                            }
                        }
                    }
                }, false);
            }
            else if(field == "acctNo") {
                if(data.acctNo && $("#tradeMarket_"+currentTab).val()) {
                    var param = {};
                    param.portfolioId = data.portfolioId;
                    param.acctNo = data.acctNo;
                    param.tradeMarket = $("#tradeMarket_"+currentTab).val();
                    Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/selectMultiSecAcctId', param, function(result) {
                        if(result) {
                            dateTable.renderRow(data.LAY_TABLE_INDEX, {"secAcctId":result.data});
                        }
                    });
                }

                curPortfolioId = "";
            }
            else if (field == "portfolioId") {
                var param = {};
                param.portfolioId = data.portfolioId;
                param.tradeMarket = $("#tradeMarket_"+currentTab).val();
                param.ps = $("#ps_"+currentTab).val();
                param.portfolioTreeId = $("#portfolioTreeId_"+currentTab).val();
                param.finprodId = $("#finprodId_"+currentTab).val();

                dateTable.renderRow(data.LAY_TABLE_INDEX, {"acctNo":null});
                Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/selectAddPortfolioDetail', param, function(result) {
                    if(result) {
                        dateTable.renderRow(data.LAY_TABLE_INDEX,result);
                    }
                });
            }
        },
        done: function (table, allData) {
            if(dateTable.getAllRows()) {
                var array = dateTable.getAllRows();
                if(array.length>0) {
                    if(array[0].ps == PS_T68 || array[0].ps == PS_T69) {
                        $("#entrustAmout_total_label").text("标准券数量");
                    }
                    else {
                        $("#entrustAmout_total_label").text("指令金额");
                    }
                    if(array[0].finprodMarketId) {
                        $("#finprodId_total").text(array[0].finprodMarketId);
                        $("#finprodName_total").text(array[0].finprodName);

                        var entrustNumTotal = 0;
                        var entrustAmoutTotal = 0;
                        for(var i=0;i<array.length;i++) {
                            entrustNumTotal = parseFloat(entrustNumTotal) + parseFloat(isNumber(array[i].entrustNum)?array[i].entrustNum:0);
                            entrustAmoutTotal = parseFloat(entrustAmoutTotal) + parseFloat(isNumber(array[i].entrustAmt)?array[i].entrustAmt:0);
                        }
                        $("#entrustNum_total").text(entrustNumTotal);
                        $("#entrustAmout_total").text(entrustAmoutTotal);
                    }
                    else {
                        $("#finprodId_total").text("--");
                        $("#finprodName_total").text("--");
                        $("#entrustNum_total").text("--");
                        $("#entrustAmout_total").text("--");
                    }

                }
                else {
                    $("#finprodId_total").text("--");
                    $("#finprodName_total").text("--");
                    $("#entrustNum_total").text("--");
                    $("#entrustAmout_total").text("--");
                }

            }
        }
    });

    /**
     * 绑定删除事件
     */
    dateTable.bind('click', '.delt', function(data, index) {
        dateTable.removeRow(data);
    });


    $(function() {
      //按tab键定位按钮事件
        var inp=$(".input-control").val();
        $(document).bind('keydown', function (e) {
            var e = window.event ? window.event : e;
            var keyCode = e.which ? e.which : e.keyCode;
            if (keyCode == 9) {
                e.preventDefault();
                if(inp!=null){
                    console.log(2345);
                    var submitBtn = $("#EtrExchangeTrade001_btn_submit");
                    if(!submitBtn || submitBtn.length == 0){
                        submitBtn =  $("#EtrExchangeTrade001_btn_submit",window.parent.document);
                    }

                    if(submitBtn){
                        submitBtn.attr("tabindex","-1");
                        if(!submitBtn.attr("firstAddFocus")){
                            submitBtn.attr("firstAddFocus",true)
                            submitBtn.append("<style>#EtrExchangeTrade001_btn_submit:focus{border:1px solid red}</style>");
                        }
                        setTimeout(function(){
                            submitBtn.focus();
                        },50);
                    }

                }
            }
        });
        // 加号点击事件
        $('.increase-button').click(function () {
            var input = $(this).parent().find('input');
            if (input.attr("disabled") === 'disabled' || input.attr("disabled") === true){
                return
            }
            var name = input.attr("name");
            var num = Number(input.val());
            switch (name) {
                case 'price':
                    addMinPriceStep('add',num,input);
                    break;
                case 'entrustPrice':
                    addMinPriceStep('add',num,input);
                    break;
                case 'entrustNum':
                    addMinNumStep('add',num,input);
                    break;
                case 'entrustAmt':
                    input.val(num + Number(0.01)).blur().trigger('change');
                    break;
                default:
                    input.val(num + Number(1)).blur().trigger('change');
            }
        });
        // 减号点击事件
        $('.decrease-button').click(function () {
            var input = $(this).parent().find('input');
            if (input.attr("disabled") === 'disabled' || input.attr("disabled") === true){
                return
            }
            var name = input.attr("name");
            var num = Number(input.val());
            switch (name) {
                case 'price':
                    addMinPriceStep('sub',num,input);
                    break;
                case 'entrustPrice':
                    addMinPriceStep('sub',num,input);
                    break;
                case 'entrustNum':
                    addMinNumStep('sub',num,input);
                    break;
                case 'entrustAmt':
                    input.val(num - Number(0.01)).blur().trigger('change');
                    break;
                default:
                    input.val(num - Number(1)).blur().trigger('change');
            }
        });

        layui.use(['form'], function() {
            // 绑定计算模式的点击事件
            var form = layui.form;
            form.on('radio(calculateField)', function(data) {
                layui.form.render(); //更新
                calculateFieldChange();
            });
        });

        initPage();
        // 绑定各种字段的change事件
        // 组合名称
        $("#portfolioTreeId_jj,#portfolioTreeId_dz,#portfolioTreeId_gd").change(function() {
            portfolioTreeIdChange(false);
        });
        // 证券代码
        $("#secId_jj,#secId_dz,#secId_gd").change(function() {
            secIdChange();
            // 资产代码变化联动价格模式下拉框
            linkDeclaType();
            // 联动指令价格变化
            priceModelChange();
        });

        // 委托方向变更时
        $("#ps_jj,#ps_dz,#ps_gd").change(function() {
            if (parentPageModel!=PAGE_MODEL_REFERENCE){  //解决场内交易指令流程代办显示为0
                psChange(false);
            }
        });

        // 价格模式
        $("#priceModel_jj,#priceModel_dz,#priceModel_gd").change(function(e,isNotTrigger) {
            if(setPageItemsFlag){
                return;
            }
            priceModelChange(false,isNotTrigger);
        });

        // 价格模式
        $("#declaType_dz,#declaType_gd").change(function(e,isNotTrigger) {
            declaTypeChange(false,isNotTrigger);
        });

        // 目标类型
        $("#targetType_jj,#targetType_dz,#targetType_gd").change(function() {
            if(setPageItemsFlag){
                return;
            }
            targetTypeChange();
        });

        // 净价、全价、到期收益率变更时重新计算
        $("#price_jj,#price_dz,#price_gd,#fprice_jj,#fprice_dz,#fprice_gd,#yield_jj,#yield_dz,#yield_gd").change(function() {
            if(setPageItemsFlag){
                return;
            }
            calcPriceAndYield();
            if(currentTab == 'jj' && $("#price_jj").val()){
                calDeviationDegree();
            }

            linkTableData();
        });

        // 委托价格
        $("#entrustPrice_jj,#entrustPrice_dz,#entrustPrice_gd").change(function() {
            if(setPageItemsFlag){
                return;
            }
            entrustPriceChange();
        });

        // 委托数量
        $("#entrustNum_jj,#entrustNum_dz,#entrustNum_gd").change(function() {
            entrustNumChange();
        });
        // 委托金额
        $("#entrustAmt_jj,#entrustAmt_dz,#entrustAmt_gd").change(function() {
            if(setPageItemsFlag){
                return;
            }
            entrustAmtChange();
        });

        // 金融产品类型（投管）
        $("#finprodType2_jj,#finprodType2_dz,#finprodType2_gd").change(function() {
            finprodType2Change();
            //add by chenjiaxing 债券竞价交易添加偏离度 20210907 start
            if($("#finprodType2_" + currentTab).val() == FINPROD_TYPE_TG_F01 && currentTab == 'jj'){
                document.getElementById('deviationDegree').innerHTML = "<span style='color: red'>"+
                    '0.0000%' +"</span>";
            }
            //add by chenjiaxing 债券竞价交易添加偏离度 20210907 end
        });
        // 资产代码变更时
        $("#finprodName_jj").change(function() {
            if (parentPageModel!=PAGE_MODEL_REFERENCE){  //解决场内交易指令流程代办显示为0
                psChange(false);
            }
        });

        // 数据来源变更时
        $("#dataSource_jj,#dataSource_dz,#dataSource_gd").change(function() {
            dataSourceChange();
        });

        // 数据来源变更时
        $("#mulDistriClass_jj,#mulDistriClass_dz,#mulDistriClass_gd").change(function() {
            mulDistriClassChange();
        });

        //查询资产信息
        $('#EtrExchangeTrade001JJ_btn_search').click(function() {
            searchDetail();
        });
        $('#EtrExchangeTrade001DZ_btn_search').click(function() {
            searchDetail();
        });
        $('#EtrExchangeTrade001GD_btn_search').click(function() {
            searchDetail();
        });

        //新增多组合
        $("#ptl_distribution_btn_add_jj").click(function () {
            createMultiPtl();
        });

        $("#ptl_distribution_btn_add_dz").click(function () {
            createMultiPtl();
        });

        $("#ptl_distribution_btn_add_gd").click(function () {
            createMultiPtl();
        });

        //计算逻辑
        $("#etrExchangeTradeMulti001_btn_calc").click(function () {
            Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/calcCheck', getSearchParams(), function(result1) {
                if (result1){
                    Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/calc', getSearchParams(), function(result) {
                        if (result){
                            if(result.data&& result.data.tradeMultiPtl001Dtos) {
                                $("#targetType_"+currentTab).val("");
                                $("#targetType_"+currentTab).val(result.data.targetType);
                                dateTable.clear(function () {
                                    for(var i=0;i<result.data.tradeMultiPtl001Dtos.length;i++) {
                                        dateTable.addRow(result.data.tradeMultiPtl001Dtos[i]);
                                    }
                                })
                            }
                        }
                    });
                }
            });
        });

        $("#etrExchangeTradeMulti001_btn_add").click(function () {
            Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/addRow', getSearchParams(), function(result) {
                if (result) {
                    if(result.data) {
                        dateTable.addRow(result.data);
                    }
                }
            });
        });

        //大宗交易席位号输入时设置动画开始时左偏移为0px;
        $("#seatId_dz").keyup(function(e){
            $("#seatId_dz").attr({maxlength:"6"});
        });

        //固定收益平台对手交易商输入限制
        $("#counterOrg_gd").keyup(function(e){
            $("#counterOrg_gd").attr({maxlength:"3"});
        });

        //固定收益平台对手交易员输入限制
        $("#counterTrader_gd").keyup(function(e){
            $("#counterTrader_gd").attr({maxlength:"6"});
        });

        //固定收益平台本方交易员输入限制
        $("#trader_gd").keyup(function(e){
            $("#trader_gd").attr({maxlength:"6"});
        });
    });
}());