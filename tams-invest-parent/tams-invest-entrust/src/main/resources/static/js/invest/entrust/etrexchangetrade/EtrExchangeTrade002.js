/**
 * 持仓信息（右上）
 */
(function(){

    function getPositionsSearchParams(){
        var data = $.serializeToObject('#searchForm_cc');
        data.portfolioId = data.portfolioId ? data.portfolioId.join(','):'';
        data.tradeMarket = data.acctTradeMarket;
        data.finprodType2 = data.finprodType2;
        return data;
    }

    function getZqzyPositionParams() {
        var data = $.serializeToObject('#searchForm_zqzy');
        return data;
    }

    function getSearchParams() {
        var data = $.serializeToObject('#searchForm');
        data.entrustStatus = $('#searchForm').find('input:radio:checked').val();
        data.portfolioId = data.portfolioId ? data.portfolioId.join(','):'';
        data.finprodType2 = data.finprodType2;
        data.secId = data.secId;
        data.ps = data.ps;
        return data;
    }

    /**
     * 定义数据表格
     */
    var dateTable = $("#dateTable").joyintable({
        ajax: {
            url: 'selectEtrExchange',//请求路径
            type: 'POST',//请求类型
            data: function() {
                return getSearchParams();//获取请求参数
            }//ajax请求数据
        },
        dblclick:function(table, data) {
            if(window.parent.dbclickCheck(data)){
                //多组合单资产指令拼装数据
                if(data.entrustClass=ENTRUST_CLASS_02) {
                    Joyin.ajax(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/selectMultDetail', data, function (result) {
                        data.tradeMultiPtl001Dtos = result.tradeMultiPtl001Dtos;
                    });
                }
                if (data.priceModel == PRICE_MODEL_PRM03) {
                    data.entrustPrice = "0.00";//市价指令，价格默认显示0
                } else {
                    data.entrustPrice = data.price;
                }
                data.secId = data.finprodMarketId;
                data.seatId = data.counterSeat;
                data.agreeOnId = data.counterConNo;
                data.acctNo= data.dealCurAcct;
                //大宗交易、竞价交易  正回购、逆回购  [大宗交易&&深交所 ->交易平台_综合协议交易平台,大宗交易&&上交所 ->交易平台_大宗交易平台]
                if((data.tradePlat == '02' || data.tradePlat == '04' || data.tradePlat == '05') && (data.ps == '03' || data.ps == '04')){
                    data.finprodType2 ='ZYSHG';
                }
                if(data.entrustClass=ENTRUST_CLASS_02) {
                    parent.EtrExchangeTradeMulti001.dbclickRefresh(data);
                }else{
                    parent.EtrExchangeTrade001.dbclickRefresh(data);
                }

            }
        },
        /**
         * 定义表格名称
         */
        searchOnInit:true,
        /*loadonce:true,*/
        cols:[
            {field:'entrustId', title: 'EtrExchangeTrade003.entrustId', sortField:'ENTRUST_ID',width : 200}//
            ,{field:'entrustStatus', title: 'EtrExchangeTrade003.entrustStatus',visible:false, sortField:'ENTRUST_STATUS',width : 150}//组合名称
            ,{field:'entrustStatusString', title: 'EtrExchangeTrade003.entrustStatus', sortField:'ENTRUST_STATUS',width : 150}//组合名称
            ,{field:'implementPercentAge', title: 'EtrExchangeTrade003.implementPercentAge',render: function (data, type, row, meta) {
                    return "<div class='layui-progress layui-progress-big' style='margin-top:3px;'><div class='layui-progress-bar layui-bg-green' style='width:"
                        + (row.implementPercentAge)
                        + "%'><span class='layui-progress-text' style='color:black;'>"
                        + (row.implementPercentAge)
                        + "%</span></div></div>";
                }, sortField:'implementPercent',width : 150}//证券账户代码
            ,{field:'dealPercentAge', title: 'EtrExchangeTrade003.dealPercentAge',render: function (data, type, row, meta) {
                    // 申报类指令成交进度显示为N/A 涉及指令业务类型：1.配股 2.配债 3.债转股 4.行权回售 5.新股申购 6.可转债申购
                    // 数据字典:上农商_指令业务类型("ENTRUST_BUSS_TYPE")
                    if(row.entrustType == "09" || row.entrustType == "10" || row.entrustType == "11" || row.entrustType == "12" || row.entrustType == "13" || row.entrustType == "16"){
                        return '<span class="s-color-gray">N/A</span>';
                    }
                    return "<div class='layui-progress layui-progress-big' style='margin-top:3px;'><div class='layui-progress-bar layui-bg-green' style='width:"
                        + (row.dealPercentAge)
                        + "%'><span class='layui-progress-text' style='color:black;'>"
                        + (row.dealPercentAge)
                        + "%</span></div></div>";
                },sortField:'dealPercent', width : 150}//证券账户名称

            ,{field:'portfolioName', title: 'EtrExchangeTrade003.portfolioName', sortField:'PORTFOLIO_NAME',width : 150}//资产代码
            ,{field:'dealCurAcctName', title: 'EtrExchangeTrade003.dealCurAcctName', sortField:'dealCurAcctName',width : 200}//资金账号名称
            ,{field:'secAcctId', title: 'EtrExchangeTrade003.secAcctId', sortField:'SEC_ACCT_ID',width : 200}//证券账户代码
            ,{field:'secAcctName', title: 'EtrExchangeTrade003.secAcctName', sortField:'SEC_ACCT_NAME',width : 200}//证券账户名称
            ,{field:'finprodAbbr', title: 'EtrExchangeTrade003.finpordName',sortField:'FINPROD_ABBR', width : 150}//资产名称
            ,{field:'assetTypeString', title: 'EtrExchangeTrade003.finprodType2',sortField:'ASSET_TYPE', width : 100}//投资目的
            ,{field:'tradeMarketString', title: 'EtrExchangeTrade003.tradeMarket',sortField:'TRADE_MARKET', width : 100}//资产名称
            ,{field:'tradePlat', title: 'EtrExchangeTrade003.tradePlat',sortField:'TRADE_PLAT', width : 100,visible:false}//投资目的
            ,{field:'tradePlatString', title: 'EtrExchangeTrade003.tradePlat',sortField:'TRADE_PLAT', width : 100}//投资目的
            ,{field:'ps', title: 'EtrExchangeTrade003.ps2',sortField:'PS',visible:false, width : 100}//投资目的
            ,{field:'psStr', title: 'EtrExchangeTrade003.ps2',sortField:'PS', width : 100}//投资目的
            ,{field:'priceModel', title: 'EtrExchangeTrade003.priceModel',visible:false,sortField:'PRICE_MODEL', width : 100}//投资目的
            ,{field:'priceModelString', title: 'EtrExchangeTrade003.priceModel',sortField:'PRICE_MODEL', width : 100}//投资目的
            ,{field:'price', title: 'EtrExchangeTrade003.price',type:'number',digit:4, width : 120,sortField:'PRICE',
                render: function(data, type, row, meta) {
                    if (row.priceModel == PRICE_MODEL_PRM03) {
                        return "0.00";//市价指令，价格默认显示0
                    } else {
                        return row.price;
                    }
                }
            }//指令价格
            ,{field:'entrustAmt', title: 'EtrExchangeTrade003.entrustAmt',type:'number',digit:4, width : 120,sortField:'ENTRUST_AMT'}//可卖数量
            ,{field:'vdate', title: 'EtrExchangeTrade003.vdate',type:'date', width : 120,sortField:'VDATE'}//持仓成本
            ,{field:'mdate', title: 'EtrExchangeTrade003.mdate',type:'date', width : 120,sortField:'MDATE'}//最新价
            ,{field:'nextUser', title: 'EtrCommon.nextUser', width:120,sortable:false}
            ,{field:'finprodId',visible: false}//资产ID
            ,{field:'ps', visible: false}//交易方向
            ,{field:'portfolioId', visible: false}//组合代码
            ,{field:'paAssetType', visible:false}//平安资产类型
            ,{field:'entrustType', visible:false}//平安资产类型
            ,{field:'finprodMarketId', visible:false}//平安资产类型
            ,{field:'counterSeat', visible:false}//平安资产类型
            ,{field:'counterConNo', visible:false}//平安资产类型
            ,{field:'counterId', visible:false}//平安资产类型
            ,{field:'finprodType2', visible:false}//平安资产类型
            ,{field:'entrustNum', visible:false}//平安资产类型
            ,{field:'dealCurAcct', visible:false}//平安资产类型
            ,{field:'targetType', visible:false}//平安资产类型
            ,{field:'counterOrg', visible:false}//对手方交易商
            ,{field:'counterTrader', visible:false}//对手方交易员
            ,{field:'trader', visible:false}//本方交易员
        ]
    });

    /**
     * 定义数据表格
     */
    var positionsTable = $("#positionsTable").joyintable({
        ajax: {
            url: 'findPositionsPage',//请求路径
            type: 'POST',//请求类型
            data: function() {
                return getPositionsSearchParams();//获取请求参数
            }//ajax请求数据
        },
        dblclick:function(table, data) {
            if (data.finprodType2 != FINPROD_TYPE_TG_F01){
                data.entrustPrice = data.price;
            }
            data.secId = data.finprodMarketId;
            if(data.sellNum){
                data.entrustNum = data.sellNum;
            }
            else{
                data.entrustNum = "0";
            }
            data.ps = PS_T02;
            data.targetType = TARGET_CLASS_01;
            //价格模式默认带出限价
            data.priceModel = "PRM02";
            if(parent.EtrExchangeTrade001.getCurrentTab() == 'dz'){
                data.declaType = DECLA_TYPE_PRM05;
            }
            if(parent.EtrExchangeTrade001.getCurrentTab() == 'gd'){
                data.declaType = DECLA_TYPE_PRM14;
            }
            if(window.parent.checkDbClickData(data)){
                parent.EtrExchangeTrade001.dbclickRefresh002(data);
            }
        },
        /**
         * 定义表格名称
         */
        checkbox:false,
        searchOnInit:true,
        cols:[
            {field:'portfolioId', title: 'EtrExchangeTrade002.portfolioId', sortField:'portfolioId',width : 100}//组合名称
            ,{field:'portfolioName', title: 'EtrExchangeTrade002.portfolioName', sortField:'portfolioName',width : 100}//组合名称
            ,{field:'secAcctId', title: 'EtrExchangeTrade002.secAcctId', sortField:'secAcctId',width : 130}//证券账户代码
            ,{field:'secAcctName', title: 'EtrExchangeTrade002.secAcctName',sortField:'secAcctName', width : 130}//证券账户名称
            ,{field:'tradeMarketStr', title: 'EtrExchangeTrade002.tradeMarket',sortField:'tradeMarket', width : 100}//交易市场
            ,{field:'finprodId', title: 'EtrExchangeTrade002.finprodId', width : 180,visible:false}//资产代码
            ,{field:'finpordName', title: 'EtrExchangeTrade002.finpordName', width : 160,visible:false}//资产名称
            ,{field:'finprodMarketId', title: 'EtrExchangeTrade002.finprodId', width : 180}//资产代码
            ,{field:'secName', title: 'EtrExchangeTrade002.finpordName', width : 160}//资产名称
            ,{field:'finprodType2', title: 'EtrExchangeTrade002.finprodType', width : 160,render: function (data, type, row, meta) {
                    if (data == 'F01'){data = "债券";} if(data == 'F06'){data = "基金";}if (data=='F10'){data="股票";} return data;}}//资产类型
            ,{field:'invAimStr', title: 'EtrExchangeTrade002.invAim',sortField:'invAim', width : 150}//投资目的
            ,{field:'positionsNum', title: 'EtrExchangeTrade002.positionsNum',type:'number', digit : 0, width : 150}//持仓数量
            ,{field:'sellNum', title: 'EtrExchangeTrade002.sellNum',type:'number', digit : 0, width : 150}//可卖数量
            ,{field:'positionsAmt', title: 'EtrExchangeTrade002.positionsAmt',type:'number', width : 150}//持仓成本
            ,{field:'price', title: 'EtrExchangeTrade002.price',type:'number', width : 100}//最新价
            ,{field:'floatingPL', title: 'EtrExchangeTrade002.floatingPL',type:'number', width : 150}//浮动盈亏（%）
        ]
    });

    /**
     * 定义数据表格
     */
    var positionsTable_zqzy = $("#positionsTable_zqzy").joyintable({
        ajax: {
            url: 'findZqzyPositionsPage',//请求路径
            type: 'POST',//请求类型
            data: function() {
                return getZqzyPositionParams();//获取请求参数
            }//ajax请求数据
        },
        dblclick:function(table, data) {
            data.secId = data.finprodMarketId;
            if(data.repoNum){
                data.entrustNum = data.repoNum;
            }
            else{
                data.entrustNum = "0";
            }
            // 数据字典.共通业务类型.债券质押式回购竞价指令
            data.entrustType = ENTRUST_BUSS_TYPE_15;
            data.ps = PS_T69;
            data.targetType = TARGET_CLASS_01;
            data.priceModel = PRICE_MODEL_PRM01;
            if(window.parent.checkDbClickData(data)){
                parent.EtrExchangeTrade001.dbclickRefresh002(data);
            }
        },
        /**
         * 定义表格名称
         */
        checkbox:false,
        searchOnInit:true,
        height: document.documentElement.clientHeight -110,
        cols:[
            {field:'portfolioId', title: 'EtrExchangeTrade002.portfolioId', sortField:'portfolioId',width : 100}//组合名称
            ,{field:'portfolioName', title: 'EtrExchangeTrade002.portfolioName', sortField:'portfolioName',width : 100}//组合名称
            ,{field:'secAcctId', title: 'EtrExchangeTrade002.secAcctId', sortField:'secAcctId',width : 130}//证券账户代码
            ,{field:'secAcctName', title: 'EtrExchangeTrade002.secAcctName',sortField:'secAcctName', width : 130}//证券账户名称
            ,{field:'tradeMarketStr', title: 'EtrExchangeTrade002.tradeMarket',sortField:'tradeMarket', width : 100}//交易市场
            ,{field:'finprodId', title: 'EtrExchangeTrade002.finprodId', width : 180,visible:false}//资产代码
            ,{field:'finpordName', title: 'EtrExchangeTrade002.finpordName', width : 160,visible:false}//资产名称
            ,{field:'finprodMarketId', title: 'EtrExchangeTrade002.finprodId', width : 180}//市场代码
            ,{field:'secName', title: 'EtrExchangeTrade002.finpordName', width : 160}//资产名称
            ,{field:'price', title: 'EtrExchangeTrade002.repoPrice', width : 160}//折算率（%）
            ,{field:'positionsNum', title: 'EtrExchangeTrade002.positionsNum',type:'number',digit : 0, width : 150}//持仓数量
            ,{field:'repoNum', title: 'EtrExchangeTrade002.repoNum',type:'number', digit : 0, width : 150}//质押数量
            ,{field:'secNum', title: 'EtrExchangeTrade002.secNum',type:'number', digit : 0,  width : 150}//标准券数量
            ,{field:'canRepoNum', title: 'EtrExchangeTrade002.canRepoNum',type:'number', digit : 0, width : 150}//可质押数量
        ],
        done : function(res) {
            // 最后一行汇总标准券数量
            var totalNum = Number(0);
            var allRows = positionsTable_zqzy.getAllRows();
            if (allRows){
                allRows.forEach(item => {
                    totalNum +=  Number(item.secNum);
                });
                $('#secNumTotal').text(totalNum+ '张')
            }
            var param = getZqzyPositionParams();
            clearlabelElement();
            if(param.portfolioId){
                secNum_sh = 0;
                secNum_sz = 0;
                if (res != null && res.length > 0) {
                    for (var i = 0; i < res.length; i++) {
                        if(res[i].tradeMarket == "03"){
                            secNum_sh += Number(res[i].canUseSecNum);
                        }else if(res[i].tradeMarket == "04"){
                            secNum_sz += Number(res[i].canUseSecNum);
                        }
                    }
                    Joyin.ajax(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade002/loadSecNum', param, function(result) {
                        if(result) {
                            $("#usableNum_sh").text(result.secNum_sh + '张');
                            if(secNum_sh != 0){
                                var usableNum_sh = result.secNum_sh.replace(/,/g,'')
                                var res_sh = parseFloat(usableNum_sh)/parseFloat(secNum_sh);
                                $("#usingRate_sh").text(Math.round(res_sh*10000)/100 + '%');
                                $("#usingRate_sh").css('color', res_sh < 1 ? "green":"red");
                                $("#usableNum_sh").css('color', res_sh < 1 ? "green":"red");

                                $("#secNum_sh").text(toThousands(secNum_sh - parseFloat(usableNum_sh)) + '张');
                                $("#secNum_sh").css('color', secNum_sh - parseFloat(usableNum_sh) > 0 ? "green":"red");
                            }
                            $("#usableNum_sz").text(result.secNum_sz + '张');
                            if(secNum_sz != 0){
                                var usableNum_sz = result.secNum_sz.replace(/,/g,'');
                                var res_sz = parseFloat(usableNum_sz)/parseFloat(secNum_sz);
                                $("#usingRate_sz").text(Math.round(res_sz*10000)/100 + '%');
                                $("#usingRate_sz").css('color', res_sz < 1 ? "green":"red");
                                $("#usableNum_sz").css('color', res_sz < 1 ? "green":"red");

                                $("#secNum_sz").text(toThousands(secNum_sz - parseFloat(usableNum_sz)) + '张');
                                $("#secNum_sz").css('color', secNum_sz - parseFloat(usableNum_sz) > 0 ? "green":"red");
                            }
                        }
                    });
                }
            }
        }
    });

    function toThousands(num) {
        return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
    }

    function clearlabelElement() {
        $("#secNum_sh").text('--');
        $("#secNum_sh").css('color', 'black');
        $("#secNum_sz").text('--');
        $("#secNum_sz").css('color', 'black');
        $("#usableNum_sh").text('--');
        $("#usableNum_sh").css('color', 'black');
        $("#usableNum_sz").text('--');
        $("#usableNum_sz").css('color', 'black');
        $("#usingRate_sh").text('--');
        $("#usingRate_sh").css('color', 'black');
        $("#usingRate_sz").text('--');
        $("#usingRate_sz").css('color', 'black');
    }

    /**
     * 刷新列表
     */
    var refreshPositionsTable = function(){
        positionsTable.table ? positionsTable.reload() : positionsTable.load();
    }
    /**
     * 刷新债券质押列表
     */
    var refreshZqzyPositionsTable = function(){
        positionsTable_zqzy.table ? positionsTable_zqzy.reload() : positionsTable_zqzy.load();
        //loadSecNum();
    }

    var myRefresh = function(){
        dateTable.table ? dateTable.reload() : dateTable.load();
    }
    /**
     * 更新上交所和深交所汇总标准券数量
     */
    var loadSecNum = function() {

    }
    var init = function() {
        //loadSecNum();
    }


    var zqzyPositionRefresh = function(portfolioId) {
        $("#portfolioId_zqzy").textSelect('setValue', portfolioId);
        if (!portfolioId) {
            $("#portfolioId_zqzy").val("").trigger('change');
        }
        refreshZqzyPositionsTable();// 加载表格

    };
    //add by chenjiaxing 指令管理添加导出功能 start
    /**
     * 导出
     */
    var exportExcel = function () {
        if (dateTable != null && dateTable != '') {
            var selArr = dateTable.getAllCheckedRows();
            var datas = JSON.stringify(selArr);
        }
        if (!selArr || selArr.length == 0) {
            var paramObj = getSearchParams();
            Joyin.exportExcel('export', paramObj);
        } else {
            Joyin.exportExcel('export', {
                datas: datas,
            });
        }
    };
    //add by chenjiaxing 指令管理添加导出功能 end
    /**
     * 画面加载完成
     */
    $(function() {
        $('.zlgl-transfer-form #EtrExchangeTrade002_btn_filter').hide();
        $('.zlgl-transfer-form #EtrExchangeTrade002_btn_export').hide();
        $('.zlgl-transfer-form #EtrExchangeTrade002_btn_search').hide();
        $('.zlgl-transfer-form #EtrExchangeTrade002_btn_reset').hide();
        // 切换到质押持仓，查询页面
        layui.use(['element', 'form'], function() {
            // 绑定交易平台TAB切换事件
            var element = layui.element;
            element.on('tab(tradePlatform)', function(data) {
                if(data.index == 0) {
                    refreshPositionsTable();
                }else if(data.index == 1) {
                    refreshZqzyPositionsTable();
                }
            });
        });

        // 绑定查询按钮
        $('.zlgl-transfer-form #EtrExchangeTrade002_btn_ccsearch').click(function() {
            refreshPositionsTable();//加载表格
        });
        // 绑定债券质押查询按钮
        $('#EtrExchangeTrade002_zqzy_search_btn').click(function() {
            refreshZqzyPositionsTable();// 加载表格
        });
        //add by chenjiaxing 指令管理导出 start
        $('.zhiling-transfer-form #EtrExchangeTrade002_btn_export').click(function() {
            exportExcel();
        });
        //add by chenjiaxing 指令管理导出 end
        /**
         * radio事件
         */
        layui.form.on('radio(entrustStatus)',function(data){
            myRefresh();
        });

        /**
         * 关闭委托
         */
        $('#EtrExchangeTrade003_btn_import').click(function() {
            window.parent.importEtr();
        });
        /**
         * 关闭委托
         */
        $('#EtrExchangeTrade003_btn_close').click(function() {
            window.parent.closeEtr();
        });
        /**
         * 撤销
         */
        $('#EtrExchangeTrade003_btn_revoke').click(function() {
            window.parent.revokeEtr();
        });

        // 绑定查询按钮
        $('.zhiling-transfer-form #EtrExchangeTrade002_btn_search').click(function() {
            myRefresh();
        });
        //重置按钮点击事件
        $('.zhiling-transfer-form #EtrExchangeTrade002_btn_reset').click(function() {
            $("#portfolioId_zhiling").val("");
            $("#finprodType2_zhiling").val("");
            $("#ps_zhiling").val("");
            $("#secId_zhiling").val("");
            var selects = document.getElementsByName("entrustStatus");
            for (var i=0; i<selects.length; i++){
                if (selects[i].value=="") {
                    selects[i].checked= true;
                    break;
                }
            }
        });

        //质押券重置按钮点击事件
        $('#EtrExchangeTrade002_zqzy_reset_btn').click(function() {
            $("#portfolioId_zqzy").val("").trigger('change');
            $("#tradeMarket_zqzy").val("").trigger('change');
            $("#date_zqzy").val("").trigger('change');
            $("#finprodMarketId_zqzy").val("").trigger('change');
        });

        $('.zlgl-transfer-form #EtrExchangeTrade002_btn_ccreset').click(function() {
            $("#portfolioId").val("").trigger('change');
            $("#tradeMarket_cc").val("").trigger('change');
            $("#secId").val("").trigger('change');
            $("#finprodType2").val("").trigger('change');
            positionsTable.clear();
        });

        init();
        /**
         * 定时刷新
         */
        setInterval(function(){
            refreshPositionsTable();
            refreshZqzyPositionsTable();
            myRefresh();
        },etrExchange_posTable_refresh_period);

    });

    var getAllCheckedRows = function(){
        if(dateTable){
            return dateTable.getAllCheckedRows();
        }
    }
    /**
     * 对外公开的接口
     */
    parent.EtrExchangeTrade002 = {
        zqzyPositionRefresh:zqzyPositionRefresh,
        getAllCheckedRows:getAllCheckedRows
    }

}());