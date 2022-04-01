/**
 * 所有投资查询
 */
(function(){
	/**
	 * 
	 */
	var assetType = '';
    /**
     *
     */
    var assetType1 = '';
	/**
	 * 
	 */
	var entrustId = '';
    /**
     * 获取查询参数
     */
    var searchform =function(){ 
        var data = $.serializeToObject('#searchForm');
        if($('#finprodType2').val()){
           data.finprodType2 = $('#finprodType2').val().join(',');
        }
        if($('#portfolioId').val()){
            data.portfolioId = $('#portfolioId').val().join(',');
         }
        if($('#entrustStatus').val()){
			data.entrustStatus = $('#entrustStatus').val().join(',');
		}
        //利率互换交易方向  后台转换
        if($('#ps').val() && $('#ps').select2('data')[0].col ==IRS_TYPE ){
        	data.ps= IRS_TYPE + data.ps
        }
        return data;
    };

    /**
     * 定义数据表格
     */
    var table=$("#dataTable").joyintable({
    	height:'200',
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return searchform();
            }
        },
        checkbox:false,
        click:function(table, data) {
            data.operateType = "click";
            data.pageModel = PAGE_MODEL_REFERENCE;
            tradeMarket = data.tradeMarket;
            //交易所（上交和深交）的指令
            if(tradeMarket == TRADE_PLACE_03 || tradeMarket == TRADE_PLACE_04 ) {
            	assetType = data.assetType;
            	entrustId = data.entrustId;
                assetType1 = data.assetType1;
            	if(entrustId != ''){
               		dataTableDetail.table == null ? dataTableDetail.load() : dataTableDetail.reload();
               		if(assetType == F01){
               			dataTableDetail.showColumns([{field:'entrustId',visible:true} ,{field:'orderNo',visible:true},{field:'tradeId',visible:false},{field:'finprodMarketId',visible:true},{field:'termVriety',visible:false} ,{field:'finprodName',visible:false},{field:'finprodAbbr',visible:true},{field:'bondTotalFace',visible:true},{field:'tradeAmt',visible:false},{field:'shareAmt',visible:false},{field:'tradeNum',visible:false},{field:'unitCprice',visible:true},{field:'backRate',visible:false},{field:'tradePrice',visible:false},{field:'unitFprice',visible:true},{field:'fpriceAmt',visible:true},{field:'depositAmt',visible:false},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
               		}else if(assetType == F02){
               			dataTableDetail.showColumns([{field:'entrustId',visible:true} ,{field:'orderNo',visible:true},{field:'tradeId',visible:false},{field:'finprodMarketId',visible:true},{field:'termVriety',visible:false} ,{field:'finprodName',visible:false},{field:'finprodAbbr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:true},{field:'shareAmt',visible:true},{field:'tradeNum',visible:false},{field:'unitCprice',visible:false},{field:'backRate',visible:false},{field:'tradePrice',visible:true},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:false},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
               		}else if(assetType == F07){
                        if(assetType1 === TARGET_BACK_TYPE_H01) {
                            dataTableDetail.showColumns([{field:'entrustId',visible:true} ,{field:'orderNo',visible:true},{field:'fTradeId',visible:true},{field:'finprodMarketId',visible:false} ,{field:'termVriety',visible:false} ,{field:'finprodName',visible:false},{field:'finprodAbbr',visible:false},{field:'psStr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:false},{field:'shareAmt',visible:false},{field:'tradeNum',visible:false},{field:'unitCprice',visible:false},{field:'backRate',visible:true},{field:'tradePrice',visible:false},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:false},{field:'term',visible:true},{field:'vPayDate',visible:true},{field:'mPayDate',visible:true},{field:'origRepSecId',visible:true},{field:'repSecId',visible:true},{field:'repSecName',visible:true},{field:'repSecNum',visible:true}]);
                        }
                        else {
                            // ZGZGST-1372:设置成交编号为可见 fuyongsheng 20210825 edit start
                            dataTableDetail.showColumns([{field:'entrustId',visible:true} ,{field:'orderNo',visible:true},{field:'fTradeId',visible:true},{field:'finprodMarketId',visible:false} ,{field:'termVriety',visible:true} ,{field:'finprodName',visible:false},{field:'finprodAbbr',visible:false},{field:'psStr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:true},{field:'shareAmt',visible:false},{field:'tradeNum',visible:false},{field:'unitCprice',visible:false},{field:'backRate',visible:true},{field:'tradePrice',visible:false},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:false},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
                            // ZGZGST-1372:设置成交编号为可见 fuyongsheng 20210825 edit end
                        }
               		}else if(assetType == F11){
               			dataTableDetail.showColumns([{field:'entrustId',visible:true} ,{field:'orderNo',visible:true},{field:'tradeId',visible:false},{field:'finprodMarketId',visible:true},{field:'termVriety',visible:false} ,{field:'finprodName',visible:true},{field:'finprodAbbr',visible:false},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:true},{field:'shareAmt',visible:false},{field:'tradeNum',visible:true},{field:'unitCprice',visible:false},{field:'backRate',visible:false},{field:'tradePrice',visible:true},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:false},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
               		}else if(assetType == F20){
               			dataTableDetail.showColumns([{field:'entrustId',visible:true}, {field:'orderNo',visible:true},{field:'tradeId',visible:true},{field:'finprodMarketId',visible:true} ,{field:'termVriety',visible:false} ,{field:'finprodName',visible:true},{field:'finprodAbbr',visible:false},{field:'psStr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:false},{field:'shareAmt',visible:false},{field:'tradeNum',visible:true},{field:'unitCprice',visible:false},{field:'backRate',visible:false},{field:'tradePrice',visible:true},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:true},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
               		}
               	}
              /*  label='EtrTransactionDetail001.detail';
                pagePath = "/invest/entrust/etrtransactiondetail/EtrTransactionDetail002/init";
                Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                    "95%", "90%",data,
                    function(){table.reload();});//交易所指令成交明细
*/            }
            else {
                if(data.assetType == F01 && data.ps != PS_T04 && data.ps != PS_T49){
                    Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.etrBond"),
                             basePath
                            + "invest/entrust/etrinvestbondinfo/EtrInvestBondInfo002/init",
                            "95%", "80%", data,
                            function(){table.reload();});
                }else if(data.assetType == F07){
                    Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.etrBack"),
                            basePath
                           + "invest/entrust/etrrepo/EtrRepo002/init",
                           "95%", "90%", data,
                           function(){table.reload();});
                    // add by zhangpuyu 2910/10/18 修复指令查询买断式回购双击跳转失败start
                }else if(data.assetType == F10) {
                    Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.etrBack"),
                        basePath
                        + "invest/entrust/etrboutrepo/EtrBoutRepo002/init",
                        "95%", "80%", data,
                        function (){table.reload();});
                }else if(data.assetType == F11){
                    Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.etrStock"),
                        basePath
                        + "invest/entrust/etrstock/EtrStock002/init",
                        "95%", "80%", data,
                        function(){table.reload();});
                }else if(data.assetType == F20){
                    Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.etrfuturetrade"),
                            basePath
                            + "invest/entrust/etrfuturetrade/EtrFutureTradeMain/init",
                            "95%", "80%", data,
                            function(){table.reload();});
                }
            }
        },
        dblclick:function(table, data) {
            data.operateType = "dblclick";
            data.pageModel=PAGE_MODEL_REFERENCE;
            tradeMarket = data.tradeMarket;
            entrustCreateSource = data.entrustCreateSource;
            //交易所（上交和深交）的指令
            if(tradeMarket == TRADE_PLACE_03 || tradeMarket == TRADE_PLACE_04) {
                // ENTRUST_CLASS=多组合单资产指令 且 资产类型 IN（债券、股票、基金、回购）且 交易场所IN（上交所、深交所）打开场内交易指令画面对应交易平台的多组合指令画面
                if(data.entrustClass==ENTRUST_CLASS_02&&(data.assetType == F01 || data.assetType == F02||data.assetType == F07||data.assetType == F11 )){
                    pagePath = "invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/init";
                    Joyin.dialog(JoyinUtil.getLabel('EtrAgreRepTrade001.detail'), basePath + pagePath,
                        "95%", "90%", {
                            pageModel: PAGE_MODEL_REFERENCE,
                            entrustId: data.entrustId,
                        });//交易所协议回购指令详情
                    return;
                }
                if (entrustCreateSource != null && entrustCreateSource == ENTRUST_CREATE_SOURCE_02) {
                    var entrustType = data.entrustType;
                    if(entrustType == ENTRUST_BUSS_TYPE_22 || entrustType == ENTRUST_BUSS_TYPE_21
                        || entrustType == ENTRUST_BUSS_TYPE_20 || entrustType == ENTRUST_BUSS_TYPE_19
                        || entrustType == ENTRUST_BUSS_TYPE_18 || entrustType == ENTRUST_BUSS_TYPE_17) {
                        pagePath = "invest/entrust/etragrereptrade/EtrAgreRepTrade001/init";
                        Joyin.dialog(JoyinUtil.getLabel('EtrAgreRepTrade001.detail'), basePath + pagePath,
                            "95%", "90%", {
                                pageModel: PAGE_MODEL_REFERENCE,
                                entrustId: data.entrustId,
                            });//交易所协议回购指令详情
                        return;
                    }
                    //债券申购进可转债页面
                    if(data.assetType == F01 && data.ps == PS_T04 ){
                        label='EtrConBond002.refrence';
                        pagePath = '/invest/entrust/etrconbond/EtrConBond002/init'
                    }else{
                        label='EtrExchangeTrade001.detail';
                        pagePath = "/invest/entrust/etrexchangetrade/EtrExchangeTrade001/init";
                    }
                    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                        "95%", "90%",data,
                        function(){table.reload();});//交易所场内指令详情
                    return;
                }
            }
            /*//交易所（上交和深交）的指令
            if((tradeMarket == TRADE_PLACE_03 || tradeMarket == TRADE_PLACE_04) && data.assetType != F17) {
                var entrustType = data.entrustType;
                if(entrustType == ENTRUST_BUSS_TYPE_22 || entrustType == ENTRUST_BUSS_TYPE_21
                    || entrustType == ENTRUST_BUSS_TYPE_20 || entrustType == ENTRUST_BUSS_TYPE_19
                    || entrustType == ENTRUST_BUSS_TYPE_18 || entrustType == ENTRUST_BUSS_TYPE_17) {
                    pagePath = "invest/entrust/etragrereptrade/EtrAgreRepTrade001/init";
                    Joyin.dialog(JoyinUtil.getLabel('EtrAgreRepTrade001.detail'), basePath + pagePath,
                        "95%", "90%", {
                            pageModel: PAGE_MODEL_REFERENCE,
                            entrustId: data.entrustId,
                        });//交易所指令详情
                    return;
                }
            	//债券申购进可转债页面
            	else if(data.assetType == F01 && data.ps == PS_T04 ){
            		label='EtrConBond002.refrence';
            		pagePath = '/invest/entrust/etrconbond/EtrConBond002/init'
            	}else{
                	label='EtrExchangeTrade001.detail';
                	pagePath = "/invest/entrust/etrexchangetrade/EtrExchangeTrade001/init";
            	}
                Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                    "95%", "90%",data,
                    function(){table.reload();});//交易所指令详情

            }*/
            if(data.assetType == F01 && data.ps != PS_T04 && data.ps != PS_T49){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrBond"),
                    basePath
                    + "invest/entrust/etrinvestbondinfo/EtrInvestBondInfo002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F07){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrBack"),
                    basePath
                    + "invest/entrust/etrrepo/EtrRepo002/init",
                    "95%", "90%", data,
                    function(){table.reload();});
                // add by zhangpuyu 2910/10/18 修复委托查询买断式回购双击跳转失败start
            }else if(data.assetType == F10) {
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrBack"),
                    basePath
                    + "invest/entrust/etrboutrepo/EtrBoutRepo002/init",
                    "95%", "80%", data,
                    function (){table.reload();});
            }else if(data.assetType == F02){
                // add by zhangpuyu 2910/10/18 修复委托查询买断式回购双击跳转失败 end
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrFund"),
                    basePath
                    + "invest/entrust/etrfund/EtrFund002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F05){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrBrow"),
                    basePath
                    + "invest/entrust/etrbrowdepoinfo/EtrBrowDepoInfo002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F08){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrLend"),
                    basePath
                    + "invest/entrust/etrlendspecinfo/EtrLendSpecInfo002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F04){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrPlanN"),
                    basePath
                    + "invest/entrust/etrplanassetn/EtrPlanAssetN002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F03){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrPlanR"),
                    basePath
                    + "invest/entrust/etrplanassetr/EtrPlanAssetR002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F06){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrProject"),
                    basePath
                    + "invest/entrust/etrproject/EtrProject002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F09){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrPlanC"),
                    basePath
                    + "invest/entrust/etrplanassetc/EtrPlanAssetC002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F11){
                Joyin.dialog(JoyinUtil.getLabel("EtrStock002.refence"),
                    basePath
                    + "invest/entrust/etrstock/EtrStock002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }else if(data.assetType == F12){
                Joyin.dialog(JoyinUtil.getLabel("EtrSecLend.refrence"),
                    basePath
                    + "invest/entrust/etrseclend/EtrSecLend002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId,
                        targetFinprodId:data.finprodId,
                        portfolioId:data.portfolioId
                    },
                    function(){table.reload();});
            }else if(data.assetType == F13){
                Joyin.dialog(JoyinUtil.getLabel("EtrWtSpotex.dblclick"),
                    basePath
                    + "invest/entrust/etrwtspotex/EtrWtSpotex002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId,
                        targetFinprodId:data.finprodId,
                        portfolioId:data.portfolioId
                    },
                    function(){table.reload();});
            }else if(data.assetType == F14){
                Joyin.dialog(JoyinUtil.getLabel("EtrForwex002.refrence"),
                    basePath
                    + "invest/entrust/etrforwex/EtrForwex002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId,
                        targetFinprodId:data.finprodId,
                        portfolioId:data.portfolioId
                    },
                    function(){table.reload();});
            }else if(data.assetType == F15){
                Joyin.dialog(JoyinUtil.getLabel("EtrWtFxswap002.refrence"),
                    basePath
                    + "invest/entrust/etrwtfxswap/EtrWtFxswap002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId,
                        targetFinprodId:data.finprodId,
                        portfolioId:data.portfolioId
                    },
                    function(){table.reload();});
            }else if(data.assetType == F16){
                Joyin.dialog(JoyinUtil.getLabel("EtrIRS.dblclick"),
                    basePath
                    + "invest/entrust/etrirs/EtrIRS002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId,
                        targetFinprodId:data.finprodId,
                        portfolioId:data.portfolioId
                    },
                    function(){table.reload();});
            }else if(data.assetType == F17){
                Joyin.dialog(JoyinUtil.getLabel("EtrBondFirstLBidding002.refrence"),
                    basePath
                    + "invest/entrust/etrbondfirstlbidding/EtrBondFirstLBidding002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId,
                        entrustStatus:data.entrustStatus,
                        targetFinprodId:data.finprodId,
                        portfolioId:data.portfolioId
                    },
                    function(){table.reload();});
                //ADD  BY GAOPENG 20191123 增加可转债转股委托 start
            }else if(data.assetType == F01 && data.ps == PS_T49){
                Joyin.dialog(JoyinUtil.getLabel("EtrBondToStock002.dblclick"),
                    basePath
                    + "invest/entrust/etrbondtostock/EtrBondToStock002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId,
                        targetFinprodId:data.finprodId,
                        portfolioId:data.portfolioId
                    },
                    function(){table.reload();});
                //ADD  BY GAOPENG 20191123 增加可转债转股委托 end
            }else if(data.assetType == F01 && data.ps == PS_T04){
                Joyin.dialog(JoyinUtil.getLabel("EtrConBond002.dblclick"),
                    basePath
                    + "invest/entrust/etrconbond/EtrConBond002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId,
                        targetFinprodId:data.finprodId,
                        portfolioId:data.portfolioId
                    },
                    function(){table.reload();});
            }else if(data.assetType == F36 ){
                Joyin.dialog(JoyinUtil.getLabel("EtrInstruction002.dblclick"),
                    basePath
                    + "invest/entrust/etrinstruction/EtrInstruction002/init",
                    "95%", "80%", {
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:data.entrustId
                    },
                    function(){table.reload();});
            } else if(data.assetType == F19){
                Joyin.dialog(JoyinUtil.getLabel("EtrInvestAllInfo001.etrProjectnet"),
                    basePath
                    + "invest/entrust/etrprojectnet/EtrProjectNet002/init",
                    "95%", "80%", data,
                    function(){table.reload();});
            }
        },
        cols:[ 
             {field:'entrustId',title: 'EtrTransactionDetail001.entrustId', width:'200', visible:true}
            ,{field:'entrustStatus', visible:false}
            ,{field:'entrustStatusStr', title: 'EtrTransactionDetail001.entrustStatus', width:'10%', sortField:"entrustStatus"}
            ,{field:'portfolioId', title: 'EtrTransactionDetail001.portfolioId', width:'200',
                render : function(data, type, row, meta) {
                    if(row.portfolioId&&row.entrustClass==ENTRUST_CLASS_01){
                        return "<a  class='pro-link portfolioIdLink' style='cursor: pointer;'>"+row.portfolioId+"</ a>";
                    }else{
                        return '';
                    }
                } ,sortField:"portfolioId"
            }
            ,{field:'portfolioName', title: 'EtrTransactionDetail001.portfolioName', width:'200',sortField:"portfolioName"}
            ,{field:'assetType',visible:false}
            ,{field:'assetTypeStr', title: 'EtrTransactionDetail001.finprodType2', width:'10%', sortField:"assetType"}
            ,{field:'finprodId',visible:false}
            ,{field:'finprodMarketId', title: 'EtrTransactionDetail001.finprodId', width:'200',
                render : function(data, type, row, meta) {
                	if(row.assetType == F07 && data.finprodMarketId){ //非质押式回购
                		return convertTranscationCode(row);
                	}else{//质押券出库 资产类型特殊处理为质押式回购
                		if(row.assetType != F07 || (row.assetType == F07 && (row.ps == 'T69' ))){
                			return "<a  class='pro-link finprodIdLink' style='cursor: pointer;'>"+data+"</ a>";
                		}else{
                			return data;
                		}
                	}   
                },sortField:"finprodMarketId"
            }
            ,{field:'finprodName', title: 'EtrTransactionDetail001.finprodName', width:'200',sortField:"finprodName"}
            ,{field:'ps', visible:false}
            ,{field:'psStr', title: 'EtrTransactionDetail001.ps', width:'10%'}
            ,{field:'tradeMarket', visible:false}
            ,{field:'tradeMarketStr', title: 'EtrTransactionDetail001.tradeMarket', width:'10%',sortField:"tradeMarket"}
            ,{field:'tradePlat', visible:false}
            ,{field:'bondTotalFace',type:'number', title: 'EtrTransactionDetail001.entrustTotal', width:'200', type:'money',sortField:"entrustAmt"}
            ,{field:'entrustAmtStr',type:'number', title: 'EtrTransactionDetail001.entrustAmt', width:'200', type:'money',sortField:"entrustAmt"}
            ,{field:'entrustNum',type:'number', title: 'EtrTransactionDetail001.entrustNum', width:'200', type:'money',sortField:"entrustNum"}
            ,{field:'entrustDateStr', title: 'EtrTransactionDetail001.entrustDate',width:'10%', type:'date',sortField:"entrustDate"}
            ,{field:'vdateStr', title: 'EtrTransactionDetail001.vdate', width:'10%', type:'date',sortField:"vdate"}
            ,{field:'mdateStr', title: 'EtrTransactionDetail001.mdate', width:'10%', type:'date',sortField:"mdate"}
            ,{field:'invManagerId', title: 'EtrTransactionDetail001.invManagerId', width:'200',sortField:"invManagerId"}
            ,{field:'remark', title: 'EtrTransactionDetail001.remark', width:'200',sortable:false,visible:false}
        ],
        done: function(res){
        	   if (res != null && res.length > 0) {
        		   var data = res[0];
        		   tradeMarket = data.tradeMarket;
                   //交易所（上交和深交）的指令
                   if(tradeMarket == TRADE_PLACE_03 || tradeMarket == TRADE_PLACE_04 ) {
                   	assetType = data.assetType;
                   	assetType1 = data.assetType1;
                   	entrustId = data.entrustId;
                   	if(entrustId != ''){
                   		dataTableDetail.table == null ? dataTableDetail.load() : dataTableDetail.reload();
                   		if(assetType == F01){
                   			dataTableDetail.showColumns([{field:'entrustId',visible:false} ,{field:'orderNo',visible:false},{field:'tradeId',visible:false},{field:'finprodMarketId',visible:true} ,{field:'termVriety',visible:false} ,{field:'finprodName',visible:false},{field:'finprodAbbr',visible:true},{field:'psStr',visible:true},{field:'bondTotalFace',visible:true},{field:'tradeAmt',visible:false},{field:'shareAmt',visible:false},{field:'tradeNum',visible:false},{field:'unitCprice',visible:true},{field:'backRate',visible:false},{field:'tradePrice',visible:false},{field:'unitFprice',visible:true},{field:'fpriceAmt',visible:true},{field:'depositAmt',visible:false},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
                   		}else if(assetType == F02){
                   			dataTableDetail.showColumns([{field:'entrustId',visible:false} ,{field:'orderNo',visible:false},{field:'tradeId',visible:false},{field:'finprodMarketId',visible:true} ,{field:'termVriety',visible:false} ,{field:'finprodName',visible:false},{field:'finprodAbbr',visible:true},{field:'psStr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:true},{field:'shareAmt',visible:true},{field:'tradeNum',visible:false},{field:'unitCprice',visible:false},{field:'backRate',visible:false},{field:'tradePrice',visible:true},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:false},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
                   		}else if(assetType == F07){
                   		    if(assetType1 == TARGET_BACK_TYPE_H01) {
                                dataTableDetail.showColumns([{field:'entrustId',visible:false} ,{field:'orderNo',visible:false},{field:'fTradeId',visible:true},{field:'finprodMarketId',visible:false} ,{field:'termVriety',visible:false} ,{field:'finprodName',visible:false},{field:'finprodAbbr',visible:false},{field:'psStr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:false},{field:'shareAmt',visible:false},{field:'tradeNum',visible:false},{field:'unitCprice',visible:false},{field:'backRate',visible:true},{field:'tradePrice',visible:false},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:false},{field:'term',visible:true},{field:'vPayDate',visible:true},{field:'mPayDate',visible:true},{field:'origRepSecId',visible:true},{field:'repSecId',visible:true},{field:'repSecName',visible:true},{field:'repSecNum',visible:true}]);
                            }
                   		    else {
                                dataTableDetail.showColumns([{field:'entrustId',visible:false} ,{field:'orderNo',visible:false},{field:'tradeId',visible:false},{field:'finprodMarketId',visible:false} ,{field:'termVriety',visible:true} ,{field:'finprodName',visible:false},{field:'finprodAbbr',visible:false},{field:'psStr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:true},{field:'shareAmt',visible:false},{field:'tradeNum',visible:false},{field:'unitCprice',visible:false},{field:'backRate',visible:true},{field:'tradePrice',visible:false},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:false},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
                            }

                   		}else if(assetType == F11){
                   			dataTableDetail.showColumns([{field:'entrustId',visible:false} ,{field:'orderNo',visible:false},{field:'tradeId',visible:false},{field:'finprodMarketId',visible:true} ,{field:'termVriety',visible:false} ,{field:'finprodName',visible:true},{field:'finprodAbbr',visible:false},{field:'psStr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:true},{field:'shareAmt',visible:false},{field:'tradeNum',visible:true},{field:'unitCprice',visible:false},{field:'backRate',visible:false},{field:'tradePrice',visible:true},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:false},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
                   		}else if(assetType == F20){
                   			dataTableDetail.showColumns([{field:'entrustId',visible:true} ,{field:'orderNo',visible:true},{field:'fTradeId',visible:true},{field:'finprodMarketId',visible:true} ,{field:'termVriety',visible:false} ,{field:'finprodName',visible:true},{field:'finprodAbbr',visible:false},{field:'psStr',visible:true},{field:'bondTotalFace',visible:false},{field:'tradeAmt',visible:false},{field:'shareAmt',visible:false},{field:'tradeNum',visible:true},{field:'unitCprice',visible:false},{field:'backRate',visible:false},{field:'tradePrice',visible:true},{field:'unitFprice',visible:false},{field:'fpriceAmt',visible:false},{field:'depositAmt',visible:true},{field:'term',visible:false},{field:'vPayDate',visible:false},{field:'mPayDate',visible:false},{field:'origRepSecId',visible:false},{field:'repSecId',visible:false},{field:'repSecName',visible:false},{field:'repSecNum',visible:false}]);
                   		}
                   	}
        	   }
           }
        }
    });
    
    

   
    /**
     * 定义成交明细数据表格
     */
    var dataTableDetail = $("#dataTableDetail").joyintable({
        ajax: {
            url: 'selectTransDetailPage',
            type: 'POST',
            data: function() {
                return {entrustId:entrustId};
            }
        },
        cols:[ 
              {field:'entrustId',title: 'EtrTransactionDetail001.entrustId', width:'200',sortField:"entrustId",visible:false}
             ,{field:'orderNo',title: 'EtrTransactionDetail001.orderNo', width:'200', sortField:"orderNo",visible:false}
             ,{field:'fTradeId',title: 'EtrTransactionDetail001.fTradeId', width:'200', sortField:"fTradeId",visible:false}
             ,{field:'portfolioId', title: 'EtrTransactionDetail001.portfolioId', width:'200',sortField:"portfolioId"}
             ,{field:'portfolioName', title: 'EtrTransactionDetail001.portfolioName', width:'200',sortField:"portfolioName"}
             ,{field:'tradeDate', title: 'EtrTransactionDetail001.tradeDate', width:'200', type:'date',sortField:"tradeDate"}
            ,{field:'repSecId', title: 'EtrTransactionDetail001.repSecId', width:'200',sortField:"repSecId"}
            ,{field:'repSecName', title: 'EtrTransactionDetail001.repSecName', width:'200', sortField:"repSecName"}
            ,{field:'repSecNum', title: 'EtrTransactionDetail001.repSecNum', width:'200', type:'number',sortField:"repSecNum"}
             ,{field:'finprodMarketId', title:'EtrTransactionDetail001.finprodId', width:'200',sortField:"finprodMarketId"}
             ,{field:'termVriety', title:'EtrTransactionDetail001.termVriety', width:'200',sortField:"finprodMarketId"}
             ,{field:'finprodName', title:'EtrTransactionDetail001.finprodName', width:'200',sortField:"finprodName"}
             ,{field:'finprodAbbr', title:'EtrTransactionDetail001.finprodAbbr', width:'200',sortField:"finprodName"}
             ,{field:'psStr', title: 'EtrTransactionDetail001.ps', width:'200'}
             ,{field:'bondTotalFace',type:'number', title:'EtrTransactionDetail001.entrustTotal', width:'200', type:'money',sortField:"tradeAmt",render : function(data, type, row, meta) {return row.tradeAmt;}}
             ,{field:'tradeAmt',type:'number', title:'EtrTransactionDetail001.tradeAmt', width:'200', type:'money',sortField:"tradeAmt"}
             ,{field:'shareAmt',type:'number', title:'EtrTransactionDetail001.shareAmt', width:'200', type:'money',sortField:"shareAmt"}
             ,{field:'tradeNum',type:'number', title:'EtrTransactionDetail001.tradeNum', width:'200', type:'money',sortField:"shareAmt"}
             ,{field:'unitCprice',type:'number', title:'EtrTransactionDetail001.unitCprice', width:'200', type:'money',sortField:"unitCprice"}
             ,{field:'backRate',type:'number', title:'EtrTransactionDetail001.backRate', width:'200', type:'money',sortField:"unitCprice"}
             ,{field:'tradePrice',type:'number', title:'EtrTransactionDetail001.price', width:'200', type:'money',sortField:"unitCprice"}
             ,{field:'unitFprice',type:'number', title: 'EtrTransactionDetail001.unitFprice', width:'200', type:'money',sortField:"unitFprice"}
             ,{field:'fpriceAmt',type:'number', title: 'EtrTransactionDetail001.fpriceAmt', width:'200', type:'money',sortField:"fpriceAmt"}
             ,{field:'depositAmt',type:'number', title: 'EtrTransactionDetail001.depositAmt', width:'200', type:'money',sortField:"depositAmt"}
            ,{field:'term',type:'number', title:'EtrTransactionDetail001.term', width:'200',sortField:"term",digit:0}
            ,{field:'vPayDate',type:'date', title: 'EtrTransactionDetail001.vPayDate', width:'200', sortField:"vPayDate"}
            ,{field:'mPayDate',type:'date', title: 'EtrTransactionDetail001.mPayDate', width:'200', sortField:"mPayDate"}
            ,{field:'origRepSecId', title: 'EtrTransactionDetail001.origRepSecId', width:'200',sortField:"origRepSecId"}
         ],
    });
    /**
     * 组合代码链接
     */
    table.bind('click','.portfolioIdLink', function(row) {
        Joyin.dialog(JoyinUtil.getLabel("SysCommon.refrence"),
                basePath + "invest/portfolio/ptlmanagementinfo/PtlManagementInfo002/init",
                "98%", "90%", {pageModel:PAGE_MODEL_REFERENCE,//page_model:查看
                portfolioId:row.portfolioId});//主键组合代码
    });
    /**
     * 资产代码链接
     */
    table.bind('click','.finprodIdLink', function(row) {
        //在新建界面进行显示单行的详细信息
        if(row.assetType == F01 ||(row.assetType == F07 && row.ps == 'T69')){
            Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.astBond"),
                     basePath
                    + "invest/asset/bond/FinBondTargetAsset002/init",
                    "95%", "80%", {pageModel:PAGE_MODEL_REFERENCE,
                                    finprodId:row.finprodId,  //金融产品代码
                                    });
        }else if(row.assetType == F02){
            Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.astFund"),
                    basePath
                   + "invest/asset/fund/FinFundTarget002/init",
                   "95%", "80%", {pageModel:PAGE_MODEL_REFERENCE,
                                   finprodId:row.finprodId,  //金融产品代码
                                   });
        }else if(row.assetType == F11){
        	Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.astStock"),
                    basePath + "invest/asset/stock/FinStockInfo002/init",
                   "95%", "80%", {pageModel:PAGE_MODEL_REFERENCE,
                                   finprodId:row.finprodId,  //金融产品代码
                                   finprodMarketId:row.finprodMarketId//市场代码
                                   });
        }
        else if(row.assetType == F20){
        	Joyin.dialog(JoyinUtil.getLabel("EtrTransactionDetail001.astFutures"),
                    basePath + "invest/asset/futures/FinFutures002/init",
                   "95%", "80%", {pageModel:PAGE_MODEL_REFERENCE,
                                   finprodId:row.finprodId  //金融产品代码
                                   });
        }
    });
    /**
     * 导出
     */
    var exportExcel = function(){       
        //勾选导出
        var paramObj = searchform();
        var selectedItem = [];
        $.each(table.getAllCheckedRows() ,function (i, item) {
            selectedItem.push(item.entrustId);
        });
        paramObj.selectedItem = selectedItem.join(',');
        Joyin.exportExcel(basePath + "invest/entrust/etrtransactiondetail/EtrTransactionDetail001/export", paramObj);
    };
    
    var convertTranscationCode = function(data){
      var finprodMarketId = '';
    	if(data.tradeMarket && data.assetType1){
    		if(data.tradeMarket == '03' && data.assetType1 == 'H02'){// 上交所竞价回购
    			switch(data.finprodMarketId){
    			case 'GC001':
    				finprodMarketId= '204001.SH';
    				break;
    			case 'GC002':
    				finprodMarketId= '204002.SH';
    				break;
    			case 'GC003':
    				finprodMarketId= '204003.SH';
    				break;
    			case 'GC004':
    				finprodMarketId= '204004.SH';
    				break;
    			case 'GC007':
    				finprodMarketId= '204007.SH';
    				break;
    			case 'GC014':
    				finprodMarketId= '204014.SH';
    				break;
    			case 'GC028':
    				finprodMarketId= '204028.SH';
    				break;
    			case 'GC091':
    				finprodMarketId= '204091.SH';
    				break;
    			case 'GC182':
    				finprodMarketId= '204182.SH';
    				break;
    			}
    		}else if(data.tradeMarket == '03' && data.assetType1 == 'H01'){// 上交所协议回购
    			switch(data.finprodMarketId){
    			case 'R001':
    				finprodMarketId= '206001.SH';
    				break;
    			case 'R007':
    				finprodMarketId= '206007.SH';
    				break;
    			case 'R014':
    				finprodMarketId= '206014.SH';
    				break;
    			case 'R021':
    				finprodMarketId= '206021.SH';
    				break;
    			case 'R1M':
    				finprodMarketId= '206030.SH';
    				break;
    			case 'R3M':
    				finprodMarketId= '206090.SH';
    				break;
    			case 'R6M':
    				finprodMarketId= '206180.SH';
    				break;
    			case 'R9M':
    				finprodMarketId= '206270.SH';
    				break;
    			case 'R1Y':
    				finprodMarketId= '206365.SH';
    				break;
    		    }
    		}else if(data.tradeMarket == '04' && data.assetType1 == 'H02'){// 深交所竞价回购
    			switch(data.finprodMarketId){
    			case 'R-001':
    				finprodMarketId= '131810.SZ';
    				break;
    			case 'R-002':
    				finprodMarketId= '131811.SZ';
    				break;
    			case 'R-003':
    				finprodMarketId= '131800.SZ';
    				break;
    			case 'R-004':
    				finprodMarketId= '131809.SZ';
    				break;
    			case 'R-007':
    				finprodMarketId= '131801.SZ';
    				break;
    			case 'R-014':
    				finprodMarketId= '131802.SZ';
    				break;
    			case 'R-028':
    				finprodMarketId= '131803.SZ';
    				break;
    			case 'R-091':
    				finprodMarketId= '131805.SZ';
    				break;
    			case 'R-182':
    				finprodMarketId= '131806.SZ';
    				break;
    			case 'RC-001':
    				finprodMarketId= '131910.SZ';
    				break;
    			case 'RC-002':
    				finprodMarketId= '131911.SZ';
    				break;
    			case 'RC-003':
    				finprodMarketId= '131900.SZ';
    				break;
    			case 'RC-007':
    				finprodMarketId= '131901.SZ';
    				break;
    		}
    	  }
    	}
    	return finprodMarketId;
    }

    /**
     * 编辑按钮事件
     */
    $(function () {
        /**
         * 查询按钮
         */
        $('#EtrTransactionDetail001_btn_search').click(function() {
            table.load();
        });
        /**
         * 导出按钮
         */
        $('#EtrTransactionDetail001_btn_export').click(function() {
            exportExcel();
        });
    });
}());