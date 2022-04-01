/**
 * 行情（左上）
 */
(function(){

    // 默认画面为“净价交易平台”
    var currentTab = "jj";

    var tradeQuotationsTable = $("#tradeQuotationsTable").joyintable({
        ajax: {
            url: 'findTradeQuotationsPage',//请求路径
            type: 'POST',//请求类型
            data: function() {
                return parent.EtrExchangeTrade001.getDzSearchParams();//获取请求参数
            }//ajax请求数据
        },
        /**
         * 定义表格双击事件
         */
        dblclick:function(table, data) {
            //验证
            if(!window.parent.checkDbClickData(data)){
                return;
            }
            //定价申购带值到指令录单界面
            parent.EtrExchangeTrade001.setEntrust001PageItems(parent.EtrExchangeTrade001.dblDzHqTable(data));
        },
        /**
         * 定义表格名称
         */
        checkbox:false,
        height: 200,
        cols:[
            {field:'secId', title: 'EtrExchangeTrade001.finprodId', width : 120,sortable:false}//证券代码
            ,{field:'secName', title: 'EtrExchangeTrade001.finprodName', width : 150,sortable:false}//证券名称
            ,{field:'quotationType', title: 'EtrExchangeTrade001.quotationType', width : 100,visible:false}//行情类型
            ,{field:'quotationTypeStr', title: 'EtrExchangeTrade001.quotationType', width : 100,sortable:false}//行情类型
            ,{field:'entrustPs', title: 'EtrExchangeTrade001.entrustPs', width : 120,visible:false}//方向
            ,{field:'entrustPsStr', title: 'EtrExchangeTrade001.entrustPs', width : 120,sortable:false}//方向
            ,{field:'entrustNum', title: 'EtrExchangeTrade001.entrustNum',type:'number', width : 120,sortable:false}//数量(张)
            ,{field:'entrustPrice', title: 'EtrExchangeTrade001.entrustPrice',type:'number', width : 120,sortable:false}//价格
            ,{field:'oppositeSeat', title: 'EtrExchangeTrade001.seatId', width : 100,sortable:false}//对方席位
            ,{field:'agreeNum', title: 'EtrExchangeTrade001.agreeOnId',width : 120,sortable:false}//约定号
            ,{field:'contacts', title: 'EtrExchangeTrade001.contacts', width : 120,sortable:false}//联系人
            ,{field:'contactNumber', title: 'EtrExchangeTrade001.contactNumber', width : 120,sortable:false}// 联系方式
            ,{field:'market', title: 'EtrExchangeTrade001.entrustPs',visible:false}//市场代码
        ]
    });

    /**
     * 证券信息
     */
    var secMap = new Map();

    /**
     * 定义交易行情数据表格
     */
    var afterHoursTable = $("#afterHoursTable").joyintable({
        ajax: {
            url: 'findAfterHoursPage',//请求路径
            type: 'POST',//请求类型
            data: function() {
                return parent.EtrExchangeTrade001.getDzSearchParams();//获取请求参数
            }//ajax请求数据
        },
        /**
         * 定义表格双击事件
         */
        dblclick:function(table, data) {
            //验证
            if(!window.parent.checkDbClickData(data)){
                return;
            }
            //盘后带值到指令录单界面
            parent.EtrExchangeTrade001.setEntrust001PageItems(parent.EtrExchangeTrade001.dblPhHqTable(data));
        },
        /**
         * 定义表格名称
         */
        checkbox:false,
        height: 200,
        cols:[
            {field:'secId', title: 'EtrExchangeTrade001.finprodId', width : 120}//证券代码
            ,{field:'secName', title: 'EtrExchangeTrade001.finprodName', width : 150}//证券名称
            ,{field:'closingPrice', title: 'EtrExchangeTrade001.closingPrice',type:'number', width : 100}//收盘价
            ,{field:'dailyAvgPrice', title: 'EtrExchangeTrade001.dailyAvgPrice',type:'number', width : 100}//日均价
            ,{field:'market', title: 'EtrExchangeTrade001.entrustPs',visible:false}//市场代码
        ]
    });


    /**
     * 定义固定收益行情数据表格
     */
    var fixedIncomeTable = $("#fixedIncomeTable").joyintable({
        ajax: {
            url: 'findFixedIncomePage',//请求路径
            type: 'POST',//请求类型
            data: function() {
                return parent.EtrExchangeTrade001.getDzSearchParams();//获取请求参数
            }//ajax请求数据
        },
        /**
         * 定义表格双击事件 没有双击事件
         */
        /*dblclick:function(table, data) {
            //验证
            if(!window.parent.checkDbClickData(data)){
                return;
            }
            //定价申购带值到指令录单界面
            setEntrust001PageItems(dblGsHqTable(data));
        },*/
        /**
         * 定义表格名称
         */
        height:200,
        checkbox:false,
        //searchOnInit:true,
        cols:[
            {field:'orderId', title: 'EtrExchangeTrade001.orderId', width : 100,sortable:false}//订单编号
            ,{field:'secId', title: 'EtrExchangeTrade001.finprodId', width : 120,sortable:false}//证券代码
            ,{field:'secName', title: 'EtrExchangeTrade001.finprodName', width : 150,sortable:false}//证券名称
            ,{field:'entrustPs', title: 'EtrExchangeTrade001.entrustPs', width : 120 ,visible:false}//方向
            ,{field:'entrustPsStr', title: 'EtrExchangeTrade001.entrustPs', width : 120,sortable:false}//方向
            ,{field:'entrustNum', title: 'EtrExchangeTrade001.entrustNum',type:'number', width : 100,sortable:false}//数量(张)
            ,{field:'nPrice', title: 'EtrExchangeTrade001.price',type:'number', width : 120,sortable:false}//净价价格
            ,{field:'fPrice', title: 'EtrExchangeTrade001.fprice',type:'number', width : 100,sortable:false}//全价价格
            ,{field:'yield', title: 'EtrExchangeTrade001.yield',type:'number', width : 100,sortable:false}//收益率
        ]
    });

    var websocket = null;
    /**
     * 打开Websocket连接
     */
    //判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
    if ('WebSocket' in window) {
        if (useInterfQuoteFlag) {
            websocket = new WebSocket(wsUri);
        }
    } else {
        alert('Not support websocket')
    }

    if (useInterfQuoteFlag && websocket != null) {
        //连接发生错误的回调方法
        websocket.onerror = function () {
            console.log("websocket error");
        }
        //连接成功建立的回调方法
        websocket.onopen = function (event) {
            console.log("websocket open");
        }

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            // setMessageInnerHTML(event.data);
            console.log(event.data);
            // 处理 空数据
            if (event.data == '没有数据') {
                updateLevel2();
                return;
            }
            $.ajax({
                type: "POST",
                url: basePath + "joyin/quote/tick/convert",
                data: event.data,
                async: false,
                dataType: "json",
                contentType: "application/json",
                success: function (info) {
                    var result = info.data;
                    if (result != null && result != '') {
                        var xtradeTickData = new Object();
                        xtradeTickData.stockCode = result.instrumentID;
                        var secInfo = secMap.get(result.instrumentID);
                        xtradeTickData.stockName = secInfo.split("=")[0];
                        xtradeTickData.finprodType = secInfo.split("=")[1];
                        xtradeTickData.open = result.openPrice;
                        xtradeTickData.highest = result.highPrice;
                        xtradeTickData.lowest = result.lowPrice;
                        xtradeTickData.latest = result.lastPrice;
                        xtradeTickData.preclose = result.preClosePrice;
                        xtradeTickData.tradeVolume = result.volume || 0;
                        xtradeTickData.tradeAmount = result.tradeBalance || 0;
                        xtradeTickData.ceiling = result.upperLimitPrice;
                        xtradeTickData.floor = result.lowerLimitPrice;
                        xtradeTickData.change = xtradeTickData.latest - xtradeTickData.preclose;
                        xtradeTickData.changePercent = xtradeTickData.change / (xtradeTickData.preclose > 0 ? xtradeTickData.preclose : 1);

                        /**
                         * 卖出数量，顺序，1档 ~ 10档
                         */
                        xtradeTickData.sellVolumes = result.askVolume instanceof Array ? result.askVolume : [];

                        /**
                         * 卖出报价，顺序，1档 ~ 10档
                         */
                        xtradeTickData.sells = result.askPrice instanceof Array ? result.askPrice : [];

                        /**
                         * 买入数量，顺序，10档 ~ 1档
                         */
                        xtradeTickData.buyVolumes = result.bidVolume instanceof Array ? result.bidVolume : [];

                        /**
                         * 买入报价，顺序，10档 ~ 1档
                         */
                        xtradeTickData.buys = result.bidPrice instanceof Array ? result.bidPrice : [];
                        updateLevel2(xtradeTickData);
                    } else {
                        updateLevel2();
                    }
                }
            });
        }

        //连接关闭的回调方法
        websocket.onclose = function () {
            console.log("websocket close");
        }
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function() {
        if (useInterfQuoteFlag && websocket != null) {
            websocket.close();
        }
    }

    /**根据市场代码，查询行情信息，展示出来*/
    var queryJJHQXXByWS = function(secId,secName,finprodType) {
        if(websocket != null && websocket.readyState == websocket.OPEN){
            /** 使用WebSocket方式查询行情信息*/
            websocket.send(secId.split(".")[0]);
            secMap.set(secId, secName + "=" + finprodType);
        } else {
            console.log("websocket 未连接")
            updateLevel2();
        }
    }


    /**根据市场代码，查询行情信息，展示出来*/
    var queryJJHQXX = function(secId,secName,finprodType) {
        $.ajax({
            /**临时用这个地址，后面调整！！！！**/
            //url: "http://192.168.90.105:8080/tams/joyin/quote/tick?stock_code="+secId,
            url: basePath + "joyin/quote/tick?stock_code="+secId,
            async: false,
            type: "GET",
            dataType:"json",
            success: function(info) {
                var result = info.data;
                if (result != null && result != '') {
                    var xtradeTickData = new Object();
                    xtradeTickData.stockCode = secId;
                    xtradeTickData.stockName = secName;
                    xtradeTickData.finprodType = finprodType;
                    //    this.stockName = stock_name;
                    //   this.updateTime = tick[0] * 1000;
                    xtradeTickData.open = result.openPrice;
                    xtradeTickData.highest = result.highPrice;
                    xtradeTickData.lowest = result.lowPrice;
                    xtradeTickData.latest = result.lastPrice;
                    xtradeTickData.preclose = result.preClosePrice;
                    xtradeTickData.tradeVolume = result.volume || 0;
                    xtradeTickData.tradeAmount = result.tradeBalance || 0;
                    xtradeTickData.ceiling = result.upperLimitPrice;
                    xtradeTickData.floor = result.lowerLimitPrice;
                    xtradeTickData.change = xtradeTickData.latest - xtradeTickData.preclose;
                    xtradeTickData.changePercent = xtradeTickData.change / (xtradeTickData.preclose > 0 ? xtradeTickData.preclose : 1);

                    /**
                     * 卖出数量，顺序，1档 ~ 10档
                     */
                    xtradeTickData.sellVolumes = result.askVolume instanceof Array ? result.askVolume : [];

                    /**
                     * 卖出报价，顺序，1档 ~ 10档
                     */
                    xtradeTickData.sells = result.askPrice instanceof Array ? result.askPrice : [];

                    /**
                     * 买入数量，顺序，10档 ~ 1档
                     */
                    xtradeTickData.buyVolumes = result.bidVolume instanceof Array ? result.bidVolume: [];

                    /**
                     * 买入报价，顺序，10档 ~ 1档
                     */
                    xtradeTickData.buys = result.bidPrice instanceof Array ? result.bidPrice : [];
                    updateLevel2(xtradeTickData);
                }
                else {
                    updateLevel2();
                }
            }
        });

    }

    _last_tick_data =null;
    /**
     * 更新行情面板数据信息
     * @param {TickData} tick_data
     */
    var updateLevel2= function (tick_data) {
        var level2 = document.querySelector(".level2-panel");
        var $bulletin = level2.querySelector('.price-bulletin');
        var $boundary = level2.querySelector('.price-boundary');
        var $level_sell = level2.querySelector('.level-sell');
        var $level_buy = level2.querySelector('.level-buy');

        var $header = level2.querySelector('h3');

        var $lastPrice = $bulletin.querySelector('.latest-price > a');
        var $up = $bulletin.querySelector('.change-direction > .icon-up');
        var $down = $bulletin.querySelector('.change-direction > .icon-down');
        var $change = $bulletin.querySelector('.change-info > .change');
        var $percent = $bulletin.querySelector('.change-info > .percentage');

        var $open = $boundary.querySelector('.item-open > a');
        var $preclose = $boundary.querySelector('.item-preclose > a');
        var $ceiling = $boundary.querySelector('.item-ceiling > a');
        var $floor = $boundary.querySelector('.item-floor > a');

        var $sells = makeLevel2($level_sell);
        var $buys = makeLevel2($level_buy);

        /**
         * @param {Array<LevelItem>} $sells
         * @param {Array<LevelItem>} $buys
         * @param {TickData} tick_data
         */
        const updateLevels = function ($sells, $buys, tick_data) {
            var sellIndex = $sells.length -1;

            var countNum = 0.01;

            if(tick_data.finprodType== "F01" ||tick_data.finprodType== "ZYSHG") {
                countNum=1;
            }

            $sells.forEach(($item, level_no) => {
            	var $price = tick_data.sells[ sellIndex] || 0;
                $item.$price.textContent = parent.EtrExchangeTrade001.formatPrice($price) == 0 ? '--' : parent.EtrExchangeTrade001.formatPrice($price);
                $item.$volume.textContent = Math.floor((tick_data.sellVolumes[sellIndex--] || 0) * countNum) == 0 ? '--' : Math.floor((tick_data.sellVolumes[sellIndex--] || 0) * countNum);
            });

            $buys.forEach(($item, level_no) => {
            	var $price = tick_data.buys[level_no] || 0;
                $item.$price.textContent = parent.EtrExchangeTrade001.formatPrice($price) == 0 ? '--' : parent.EtrExchangeTrade001.formatPrice($price);
                $item.$volume.textContent = Math.floor((tick_data.buyVolumes[level_no] || 0) * countNum) == 0 ? '--' : Math.floor((tick_data.buyVolumes[level_no] || 0) * countNum);
            });
        };

        /**
         * 清除已设置合约level2行情信息
         */

        if (!tick_data) {
            // const zero = '0.00'
            const zero = '--';
            $header.textContent='-----';
            $lastPrice.textContent = zero;
            $lastPrice.className = 'price-anchor';
            $up.style.display = 'none';
            $down.style.display = 'none';
            $change.textContent = zero;
            $change.className = 'change';
            $percent.textContent = '0.00%';
            $percent.className = 'percentage';
            $open.textContent = zero;
            $open.className = 'price-anchor';
            $preclose.textContent = zero;
            $ceiling.textContent = zero;
            $floor.textContent = zero;

            $sells.forEach(($item, level_no) => {

                $item.$price.textContent = zero;
                $item.$volume.textContent = zero;
            });

            $buys.forEach(($item, level_no) => {

                $item.$price.textContent = zero;
                $item.$volume.textContent = zero;
            });
            _last_tick_data = null;
            //delete _last_tick_data;
        }

        else if (_last_tick_data === undefined || _last_tick_data == null || _last_tick_data.stockCode !== tick_data.stockCode) {

            /**
             * 新合约
             */

            let change = tick_data.latest - tick_data.preclose;
            let is_up = change > 0;
            let is_down = change < 0;
            let color_class = is_up ? 's-color-red' : is_down ? 's-color-green' : '';
            let anchor_class = 'price-anchor ' + color_class;
            let direction_flag = is_up ? '+' : is_down ? '-' : '';

            var stockCodetmp = "";
            if(tick_data.stockCode) {
                var index = tick_data.stockCode.lastIndexOf(".");
                stockCodetmp = tick_data.stockCode.substring(0,index);
            }
            var stockNametmp = "";
            if(tick_data.stockName) {
                var index = tick_data.stockName.lastIndexOf("|");
                stockNametmp = tick_data.stockName.substring(index,tick_data.stockName.length);
            }
            $header.textContent = stockCodetmp + stockNametmp;
            $lastPrice.textContent = parent.EtrExchangeTrade001.formatPrice(tick_data.latest);
            $lastPrice.className = anchor_class;
            $up.style.display = is_up ? 'inline' : 'none';
            $down.style.display = is_down ? 'inline' : 'none';
            $change.textContent = direction_flag +  parent.EtrExchangeTrade001.formatPrice(change);
            $change.className = 'change ' + color_class;
            $percent.textContent = direction_flag + (100 * change / tick_data.preclose).toFixed(2) + '%';
            $percent.className = 'percentage ' + color_class;
            $open.textContent = parent.EtrExchangeTrade001.formatPrice(tick_data.open);
            $open.className = anchor_class;
            $preclose.textContent = parent.EtrExchangeTrade001.formatPrice(tick_data.preclose);;
            $ceiling.textContent = tick_data.ceiling >= 10000000 ? '-----' :  parent.EtrExchangeTrade001.formatPrice(tick_data.ceiling);
            $floor.textContent = parent.EtrExchangeTrade001.formatPrice(tick_data.floor);

            updateLevels($sells, $buys, tick_data);
            _last_tick_data = tick_data;
        }
        else {

            /**
             * 合约未产生变化，行情发生变化
             */

            let change = tick_data.latest - tick_data.preclose;
            let hot_change = tick_data.latest - _last_tick_data.latest;

            if (hot_change == 0) {
                return;
            }

            let is_up = change > 0;
            let is_down = change < 0;
            let is_hot_up = hot_change > 0;
            let is_hot_down = hot_change < 0;

            let color_class = is_up ? 's-color-red' : is_down ? 's-color-green' : '';
            let anchor_class = 'price-anchor ' + color_class;
            let direction_flag = is_up ? '+' : is_down ? '-' : '';

            $lastPrice.textContent = parent.EtrExchangeTrade001.formatPrice(tick_data.latest);
            $lastPrice.className = anchor_class;
            $up.style.display = is_hot_up ? 'inline' : 'none';
            $down.style.display = is_hot_down ? 'inline' : 'none';
            $change.textContent = direction_flag + parent.EtrExchangeTrade001.formatPrice(change);
            $change.className = 'change ' + color_class;
            $percent.textContent = direction_flag + (100 * change / tick_data.preclose).toFixed(2) + '%';
            $percent.className = 'percentage ' + color_class;

            /**
             * 如果合约未产生变化，仅为tick数据变更，则仅更新level2各档位价格和数量，不再检查各表单的价格值是否在有效涨跌停范围内
             */
            updateLevels($sells, $buys, tick_data);

            _last_tick_data = tick_data;
        }
    }

    var makeLevel2 = function($level_root) {
        var levels = [];
        var $items = $level_root.querySelectorAll('.level-item');

        for (let idx = 0; idx < $items.length; idx++) {
            var object = new Object();
            object.$price = $items[idx].querySelector('.level-price');
            object.$volume = $items[idx].querySelector('.level-volume');
            levels.push(object);
        }
        return levels;
    }

    /***
     * 绑定交易平台TAB切换事件
     */
    var setTradePlatform =function(tradePlat,deleteOther) {
        layui.use(['element'], function() {
                var element = layui.element;
                if(tradePlat) {
                    element.tabChange('tradePlatform', tradePlat);
                }
                if(deleteOther) {
                    var tabtitle = $(".layui-tab-title li");
                    $.each(tabtitle, function (i) {
                        var layId = $(this).attr("lay-id");
                        if(tradePlat && tradePlat != layId) {
                            element.tabDelete('tradePlatform', layId);
                        }
                    });
                }
        });
    }

    /***
     *  获取大宗交易行情信息
     * @param secId 证券代码
     */
    var getDzhqxx  = function (secId) {
        if(secId) {//
            var data = tradeQuotationsTable.getAllRows();
            if(data.length > 0){
                for (var i in data) {
                    var secIdtmp =  data[i].secId+"."+data[i].market
                    if (secIdtmp == secId) {
                        return data[i];
                    }
                }
            }
        }
    }

    /***
     * 加载大宗交易行情信息
     */
    var loadDzHqTable  = function () {
        tradeQuotationsTable.table ? tradeQuotationsTable.reload() : tradeQuotationsTable.load();
        afterHoursTable.table ? afterHoursTable.reload() : afterHoursTable.load();
    }

    /***
    * 加载固定交易行情信息
    */
   var loadGdHqTable  = function () {
       fixedIncomeTable.table ? fixedIncomeTable.reload() : fixedIncomeTable.load();
   }

    /***
     * 清空大宗交易行情信息
     */
    var clearDzHqTable  = function () {
        //清空行情列表
        if(tradeQuotationsTable.table) {
            tradeQuotationsTable.clear();
        }
        if(afterHoursTable.table) {
            afterHoursTable.clear();
        }
    }
    /***
     * 清空固定交易行情信息
     */
    var clearGdHqTable  = function () {
        //清空行情列表
        if(fixedIncomeTable.table) {
        	fixedIncomeTable.clear();
        }
       
    }

    //竞价交易时，设置涨停上下限
    var setLimitUpAndDown = function(object){
        if(currentTab == 'jj'){
            var $up = $('div.typical-price.item-ceiling').find('a.price-anchor.s-color-red').text();
            var $down = $('div.typical-price.item-floor').find('a.price-anchor.s-color-green').text();
            var $accAvailableCash = $('div.typical-price.item-kyje').find('a.price-anchor').text();
            var $accAvailablePos = $('div.typical-price.item-kmsl').find('a.price-anchor').text();
            if(isNumber($up)){
                object.limitUp = $up;
            }
            if(isNumber($down)){
                object.limitDown = $down;
            }
            if(isNumber($accAvailableCash)){
                object.accAvailableCash = $accAvailableCash;
            }
            if(isNumber($accAvailablePos)){
                object.accAvailablePos = $accAvailablePos;
            }
            //console.log($up,$down,$accAvailableCash,$accAvailablePos);
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
    }

    function getJJMktPrice(priceId) {
        return $(priceId).text();
    }

    function showFiveLevel() {
        $(".price-buy-sell").show();
    }

    function hideFiveLevel() {
        $(".price-buy-sell").hide();
    }

    function setAccAvailableCash(result,type) {
        var availableNum;
        if('cash'==type) {
            availableNum = $("#availableNum_" + currentTab).text();
        }else if('num'==type) {
            availableNum = $("#accAvailableCash_" + currentTab).text()
        }

        if (result>-100000000&&result<10000000000 && new Number(parent.EtrExchangeTrade001.moneyDelete(availableNum))>-100000000 && new Number(parent.EtrExchangeTrade001.moneyDelete(availableNum))<10000000000){
            $("#availableNum_div_" + currentTab).addClass('typical-price item-kmsl');
            $("#accAvailableCash_div_" + currentTab).addClass('typical-price item-kyje');
            $(".price-boundary").css("height",'65px');
        }
        else {
            $("#availableNum_div_" + currentTab).removeClass();
            $("#accAvailableCash_div_" + currentTab).removeClass();
            $(".price-boundary").css("height",'75px');
        }
        if('cash'==type) {
            $("#accAvailableCash_" + currentTab).text(parent.EtrExchangeTrade001.formatMoney(result));
        }else if('num'==type) {
            $("#availableNum_" + currentTab).text(parent.EtrExchangeTrade001.formatMoney(result));
        }
    }

    function setCashOrNum(type) {
        if('cash'==type) {
            $("#accAvailableCash_" + currentTab).text('0.00');
        } else if('num'==type) {
            $("#availableNum_" + currentTab).text('0.00');
        }
    }

    /**
     * 对外公开的接口
     */
    parent.EtrExchangeTradeHq = {
        queryJJHQXX:queryJJHQXX,
        updateLevel2:updateLevel2,
        loadDzHqTable:loadDzHqTable,
        loadGdHqTable:loadGdHqTable,
        clearDzHqTable:clearDzHqTable,
        clearGdHqTable:clearGdHqTable,
        setTradePlatform:setTradePlatform,
        setLimitUpAndDown:setLimitUpAndDown,
        getJJMktPrice:getJJMktPrice,
        showFiveLevel:showFiveLevel,
        hideFiveLevel:hideFiveLevel,
        setAccAvailableCash:setAccAvailableCash,
        setCashOrNum:setCashOrNum,
        queryJJHQXXByWS:queryJJHQXXByWS
    }

    /**
     * 画面加载完成
     */
    $(function() {
        //初始化时让平台tab显示出来
        //$("#tradePlatform_ul_tab").show();

        layui.use(['element'], function() {
            // 绑定交易平台TAB切换事件
            var element = layui.element;
            element.on('tab(tradePlatform)', function(data) {
                if(data.index == 0) {
                    currentTab = 'jj';
                }
                else if(data.index == 1) {
                    currentTab = 'dz';
                }
                else if(data.index == 2) {
                    currentTab = 'gd';
                }
                else {
                    currentTab = 'zy';
                }
                //设置TAB页
                if(parent.EtrExchangeTrade001) {
                    parent.EtrExchangeTrade001.setCurrentTab(currentTab);
                }

                if(window.parent.getIsNeedMutiPtl() == '01') {
                    if(parent.EtrExchangeTradeMulti001) {
                        parent.EtrExchangeTradeMulti001.setCurrentTab(currentTab);
                    }
                }
            });

        });

        $(".level-price").click(function(e){
            var quotaPrice = e.target.innerText;
            if(quotaPrice){
                parent.EtrExchangeTrade001.setQuotaPrice(quotaPrice);
            }
        });

    });

}());