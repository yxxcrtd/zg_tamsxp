/**
 * 组合信息查看页面
 */
var showPtlManagement=function(portfolioId){
	Joyin.dialog(JoyinUtil.getLabel("PtlManagementInfo002.see"),
        basePath + "invest/portfolio/ptlmanagementinfo/PtlManagementInfo002/init",
        "95%", "90%",{
        pageModel:PAGE_MODEL_REFERENCE,
        portfolioId:portfolioId,
    });
}

var showDetailByFinprodId=function(finprodId,finprodType2){
	var label='';
	//债券标的信息查看
	if(finprodType2 == PINAN_ASSET_TYPE_F01 || finprodType2 == PINAN_ASSET_TYPE_F10){
		label='FinBondTargetAsset002.title';
	    pagePath = "invest/asset/bond/FinBondTargetAsset002/init";
		Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
		    "95%", "90%",{
		    pageModel:PAGE_MODEL_REFERENCE,
		    finprodId:finprodId
		});//基金类标的信息查看
	}else if(finprodType2 == PINAN_ASSET_TYPE_F02){
		label='FinFundTarget002.detail';
	    pagePath = "invest/asset/fund/FinFundTarget002/init";
		Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
		    "95%", "90%",{
		    pageModel:PAGE_MODEL_REFERENCE,
		    finprodId:finprodId
		});//计划类利率型标的信息查看
	}else if(finprodType2 == PINAN_ASSET_TYPE_F03){
		label='FinPlanProfitAsset002.detail';
	    pagePath = "invest/asset/plan/planProfit/FinPlanProfitAsset002/init";
		Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
		    "95%", "90%",{
		    pageModel:PAGE_MODEL_REFERENCE,
		    finprodId:finprodId
		});//计划类净值型标的信息查看
	}else if(finprodType2 == PINAN_ASSET_TYPE_F04){
		label='FinPlanNetAsset002.detail';
	    pagePath = "invest/asset/plan/planNet/FinPlanNetAsset002/init";
		Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
		    "95%", "90%",{
		    pageModel:PAGE_MODEL_REFERENCE,
		    finprodId:finprodId
		});//存款标的信息查看
	}else if(finprodType2 == PINAN_ASSET_TYPE_F05){
		label='FinDepositTarget002.title';
	    pagePath = "invest/asset/depo/FinDepositTarget002/init";
	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
	    	"95%", "90%",{
	    	pageModel:PAGE_MODEL_REFERENCE,
	    	finprodId:finprodId
	     });//项目类资产标的信息查看
	}else if(finprodType2 == PINAN_ASSET_TYPE_F06){
		label='FinProjectTargetAsset002.title';
	    pagePath = "invest/asset/project/FinProjectTargetAsset002/init";
		Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
		    "95%", "90%",{
		    pageModel:PAGE_MODEL_REFERENCE,
		    finprodId:finprodId
		});//回购标的信息查看
	}else if(finprodType2 == PINAN_ASSET_TYPE_F07){
		label='FinBondTargetAsset002.title';
		pagePath = "invest/asset/bond/FinBondTargetAsset002/init";
		Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
		    "95%", "90%",{
		    pageModel:PAGE_MODEL_REFERENCE,
		    finprodId:finprodId
		});//拆借标的信息查看
	}else if(finprodType2 == PINAN_ASSET_TYPE_F08){
		label='';
	    pagePath = "";
		Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
		    "95%", "90%",{
		    pageModel:PAGE_MODEL_REFERENCE,
		    finprodId:finprodId
		});
	}else if(finprodType2 == PINAN_ASSET_TYPE_F09){
		label='SysCommon.refrence';
	    pagePath = "invest/asset/plan/plancur/FinPlanAssetC002/init";
		Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
		    "95%", "90%",{
		    pageModel:PAGE_MODEL_REFERENCE,
		    finprodId:finprodId
		});//计划类货币
	}else if (finprodType2 == PINAN_ASSET_TYPE_F11) {
        label = 'FinStockInfo002.refrence';
        pagePath = "invest/asset/stock/FinStockInfo002/init";
        Joyin.dialog(JoyinUtil.getLabel(label), basePath + pagePath, "95%",
            "90%", {
                pageModel : PAGE_MODEL_REFERENCE,
                finprodId : finprodId
            });
    }else if(finprodType2 == PINAN_ASSET_TYPE_F12){
        label='SysCommon.refrence';
        pagePath = "invest/asset/bond/FinBondTargetAsset002/init";
        Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
            "95%", "90%",{
            pageModel:PAGE_MODEL_REFERENCE,
            finprodId:finprodId
        });//债券借贷委托查看
    }else if(finprodType2 == PINAN_ASSET_TYPE_F12){
        label='SysCommon.refrence';
        pagePath = "invest/entrust/etrirs/EtrIRS002/init";
        Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
            "95%", "90%",{
            pageModel:PAGE_MODEL_REFERENCE,
            finprodId:finprodId
        });//一级投标债的信息查看
    }else if(finprodType2 == PINAN_ASSET_TYPE_F17){
        label='FinTenderBondTargetAsset002.title';
        pagePath = "invest/asset/tenderbond/FinTenderBondTargetAsset002/init";
        Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
            "95%", "90%",{
                pageModel:PAGE_MODEL_REFERENCE,
                finprodId:finprodId
            });
    }else if(finprodType2 == PINAN_ASSET_TYPE_F19){
        label='FinProjectNetTargetAsset002.title';
        pagePath = "invest/asset/projectnet/FinProjectNetTargetAsset002/init";
        Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
            "95%", "90%",{
                pageModel:PAGE_MODEL_REFERENCE,
                finprodId:finprodId
            });//权益类资产查看
    }
}

$(function(){
    /**
     * 获取查询参数
     */
    var getPageParams = function() {
    	var obj=$.serializeToObject('#TrdCommandDispatcher001_searchForm');
    	obj.vdateBegin=$('#TrdCommandDispatcher001_vdate').startDate();
    	obj.vdateEnd=$('#TrdCommandDispatcher001_vdate').endDate();
    	obj.mdateBegin=$('#TrdCommandDispatcher001_mdate').startDate();
    	obj.mdateEnd=$('#TrdCommandDispatcher001_mdate').endDate();
//    	obj.finprodId=$('#TrdCommandDispatcher001_finprodId_hidden').val();
    	if(!$("#TrdCommandDispatcher001_finprodType2").val() == ''){
    		obj.finprodType2=$("#TrdCommandDispatcher001_finprodType2").val().join(",");
    	}
    	if(!$("#TrdCommandDispatcher001_assetType1").val() == ''){
    		var assetType1=$("#TrdCommandDispatcher001_assetType1").val().split(",").join(",");
    		obj.assetType1 = (assetType1.charAt(0) == ',') ? assetType1.substring(1, assetType1.length) : assetType1;
    	}
        if($('#TrdCommandDispatcher001_entrustStatus').val()){
            obj.entrustStatus = $('#TrdCommandDispatcher001_entrustStatus').val().join(',');
        }
    	//利率互换交易方向特殊处理  后台转换
        if ($("#TrdCommandDispatcher001_ps").val() && $('#TrdCommandDispatcher001_ps').select2('data')[0].col ==IRS_TYPE) {
        	obj.ps = IRS_TYPE+obj.ps;
        }
    	return obj;
    };
    
    /**
     * 定义数据表格
     */
    var table=$("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        dblclick:function(table, data) {
            data.pageModel=PAGE_MODEL_REFERENCE;
            entrustId = data.entrustId;
            tradeMarket = data.tradeMarket;
            purchModle  = data.purchModle;
            //交易所（上交和深交）的指令(可转债网下申购亦是场内交易)
            if((tradeMarket == TRADE_PLACE_03 || tradeMarket == TRADE_PLACE_04)&&data.finprodType2 !=PINAN_ASSET_TYPE_F17) {
                label = 'EtrExchangeTrade001.detail';
                var entrustType = data.entrustType;
                if(entrustType == ENTRUST_BUSS_TYPE_22 || entrustType == ENTRUST_BUSS_TYPE_21
                    || entrustType == ENTRUST_BUSS_TYPE_20 || entrustType == ENTRUST_BUSS_TYPE_19
                    || entrustType == ENTRUST_BUSS_TYPE_18 || entrustType == ENTRUST_BUSS_TYPE_17) {
                    pagePath = "invest/entrust/etragrereptrade/EtrAgreRepTrade001/init";
					label = "EtrAgreRepTrade001.detail";
                    Joyin.dialog(JoyinUtil.getLabel(label), basePath + pagePath,
                        "95%", "90%", {
                            pageModel: PAGE_MODEL_REFERENCE,
                            entrustId: entrustId,
                        });//交易所指令详情
                }else  //场内可转债网下申购
                if(purchModle == '02'){
                    label='EtrConBond002.dblclick';
                    pagePath = "invest/entrust/etrconbond/EtrConBond002/init";
                    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                        "95%", "90%",{
                            pageModel:PAGE_MODEL_REFERENCE,
                            entrustId:entrustId
                        });
                }
                else {
					if(data.entrustClass==ENTRUST_CLASS_02){
						pagePath = "invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/init";
					}else{
						pagePath = "invest/entrust/etrexchangetrade/EtrExchangeTrade001/init";
					}
                    Joyin.dialog(JoyinUtil.getLabel(label), basePath + pagePath,
                        "95%", "90%", {
                            pageModel: PAGE_MODEL_REFERENCE,
                            entrustId: entrustId,
                        });//交易所指令详情
                }
            }
            //银行间等指令
            else {
            //债券委托详情
            if(data.finprodType2 == PINAN_ASSET_TYPE_F01 && data.ps != PS_T04 && data.ps != PS_T49){
        		label='EtrInvestBondInfo001.detail';
        	    pagePath = "invest/entrust/etrinvestbondinfo/EtrInvestBondInfo002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId,
        	     });//基金委托详情
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F02){
        		label='EtrFund002.detail';
        	    pagePath = "invest/entrust/etrfund/EtrFund002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId,
        	     });//计划类利率委托详情
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F03){
        		label='EtrPlanAssetR002.detail';
        	    pagePath = "invest/entrust/etrplanassetr/EtrPlanAssetR002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId,
        	     });//计划类净值委托详情
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F04){
        		label='EtrPlanAssetN002.detail';
        	    pagePath = "invest/entrust/etrplanassetn/EtrPlanAssetN002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId,
        	     });//存款委托详情
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F05){
        		label='EtrBrowDepoInfo002.detail';
        	    pagePath = "invest/entrust/etrbrowdepoinfo/EtrBrowDepoInfo002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId,
        	    	portfolioId:data.portfolioId
        	     });//项目类委托详情
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F06){
        		label='EtrProject002.detail';
        	    pagePath = "invest/entrust/etrproject/EtrProject002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId,
        	     });//回购委托详情
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F07){
        		label='EtrRepo002.detail';
        	    pagePath = "invest/entrust/etrrepo/EtrRepo002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId,
        	     });//拆借查看页面
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F08){
        		label='EtrLendSpecInfo002.dblclick';
        	    pagePath = "invest/entrust/etrlendspecinfo/EtrLendSpecInfo002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId
        	     });
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F09){
        		label='EtrPlanAssetC002.refence';
        	    pagePath = "invest/entrust/etrplanassetc/EtrPlanAssetC002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId
        	     });//计划类货币委托
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F10){
        		label='EtrBoutRepo002.refrence';
        	    pagePath = "invest/entrust/etrboutrepo/EtrBoutRepo002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId
        	     });//买断式回购委托
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F11){
                label='EtrStock002.refence';
                pagePath = "invest/entrust/etrstock/EtrStock002/init";
                Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                    "95%", "90%",{
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:entrustId
                    });//股票委托
            }else if(data.finprodType2 == PINAN_ASSET_TYPE_F13){
        		label='EtrWtSpotex.dblclick';
        	    pagePath = "invest/entrust/etrwtspotex/EtrWtSpotex002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId
        	     });//外汇即期委托
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F14){
        		label='EtrForwex002.refrence';
        	    pagePath = "invest/entrust/etrforwex/EtrForwex002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId
        	     });//外汇远期委托
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F15){
        		label='EtrWtFxswap002.refrence';
        	    pagePath = "invest/entrust/etrwtfxswap/EtrWtFxswap002/init";
        	    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
        	    	"95%", "90%",{
        	    	pageModel:PAGE_MODEL_REFERENCE,
        	    	entrustId:entrustId
        	     });//外汇掉期委托
        	}else if(data.finprodType2 == PINAN_ASSET_TYPE_F12){
                label='EtrSecLend.refrence';
                pagePath = "invest/entrust/etrseclend/EtrSecLend002/init";
                Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                    "95%", "90%",{
                    pageModel:PAGE_MODEL_REFERENCE,
                    entrustId:entrustId,
                    targetFinprodId:data.finprodId,
                    portfolioId:data.portfolioId
                });//债券借贷委托查看
            }else if(data.finprodType2 == PINAN_ASSET_TYPE_F16){
                label='EtrIRS.dblclick';
                pagePath = "invest/entrust/etrirs/EtrIRS002/init";
                Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                    "95%", "90%",{
                    pageModel:PAGE_MODEL_REFERENCE,
                    entrustId:entrustId,
                    targetFinprodId:data.finprodId,
                    portfolioId:data.portfolioId
                });//add by gejiaao start  一级投标债委托查看页面
            }else if(data.finprodType2 == PINAN_ASSET_TYPE_F17){
                label='EtrBondFirstLBidding002.dblclick';
                pagePath = "invest/entrust/etrbondfirstlbidding/EtrBondFirstLBidding002/init";
                Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                    "95%", "90%",{
                        pageModel:PAGE_MODEL_REFERENCE,
                        entrustId:entrustId
                    });
            }//可转债转股查看页面
          //add by gaopeng 20191123 start
			else if(data.finprodType2 == PINAN_ASSET_TYPE_F01 && data.ps == PS_T49){
				label='EtrBondToStock002.dblclick';
				pagePath = "invest/entrust/etrbondtostock/EtrBondToStock002/init";
				Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
					"95%", "90%",{
						pageModel:PAGE_MODEL_REFERENCE,
						entrustId:entrustId
					});
			}//add by gaopeng 20191123 end
			else if(data.finprodType2 == PINAN_ASSET_TYPE_F01 && data.ps == PS_T04){
				label='EtrConBond002.dblclick';
				pagePath = "invest/entrust/etrconbond/EtrConBond002/init";
				Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
					"95%", "90%",{
						pageModel:PAGE_MODEL_REFERENCE,
						entrustId:entrustId
					});
			} else  if (data.finprodType2 == PINAN_ASSET_TYPE_F36 ){//add by gejiaao end
				Joyin.dialog(JoyinUtil.getLabel("EtrInstruction002.dblclick"),
					basePath
					+ "invest/entrust/etrinstruction/EtrInstruction002/init",
					"95%", "80%", {
						pageModel:PAGE_MODEL_REFERENCE,
						entrustId:data.entrustId
					},
					function(){table.reload();});
                }else if(data.finprodType2 == PINAN_ASSET_TYPE_F19){
                    label='EtrProjectNet002.detail';
                    pagePath = "invest/entrust/etrprojectnet/EtrProjectNet002/init";
                    Joyin.dialog(JoyinUtil.getLabel(label),basePath+pagePath,
                        "95%", "90%",{
                            pageModel:PAGE_MODEL_REFERENCE,
                            entrustId:entrustId,
                        });//回购委托详情
                }
            }
        },
        cols:[
        	{field:'otherEntrustStatus', title: 'TrdCommandDispatcher001.entrustStatus',width:100, sortField:'entrustStatus'}
        	,{field:'entrustId', title: 'TrdCommandDispatcher001.entrustId',width:170}
        	,{field:'invManagerId', title: 'TrdCommandDispatcher001.invManagerId',width:100}
        	,{field:'trader', title: 'TrdCommandDispatcher001.trader',width:130}
        	,{
        		field:'portfolioId', 
        		title: 'TrdCommandDispatcher001.portfolioId',
                render : function(data, type, row, meta) {
        			var portfolioId = row.portfolioId;
        			if (portfolioId&&row.entrustClass==ENTRUST_CLASS_01){
                        portfolioId = '<a onclick="showPtlManagement(\'' + row.portfolioId
                            + '\')" class="pro-link"  style="cursor: pointer;">'+row.portfolioId+'</a>';
					} else {
                        portfolioId = null;
					}
                	return portfolioId;			
                },
                width:130
        	}
        	,{field:'portfolioName', title: 'TrdCommandDispatcher001.portfolioName',width:150}
        	,{field:'otherFinprodType2', title: 'TrdCommandDispatcher001.finprodType2',width:140, sortField:'finprodType2'}
        	,{field:'finprodType2' ,visible:false}
        	,{
        		field:'finprodMarketId', 
        		title: 'TrdCommandDispatcher001.finprodId',
        		render : function(data, type, row, meta) {
					if(row.finprodType2 != "F07"){
						if(row.finprodMarketId !=undefined){
							return "<a onclick=\"showDetailByFinprodId('"+row.finprodId+"','"+row.finprodType2+"')\" + style='cursor: pointer;'>"+row.finprodMarketId+"</a>";
						}
					}else{
						if(row.finprodMarketId !=undefined && row.xyFlag == '01') {
							 return "<a onclick=\"showDetailByFinprodId('"+row.finprodId+"','"+row.finprodType2+"')\" + style='cursor: pointer;'>"+row.finprodMarketId+"</a>";
						 }
					}
                },
                width:180
        	}
        	,{field:'finprodAbbr', title: 'TrdCommandDispatcher001.finprodName',width:160}
        	,{field:'otherPs', title: 'TrdCommandDispatcher001.ps',width:100, sortField:'ps'}
        	,{field:'otherTradeMarket',  title: 'TrdCommandDispatcher001.tradeMarket',width:130, sortField:'tradeMarket'}
        	,{field:'entrustAmt', title: 'TrdCommandDispatcher001.entrustAmt',type:'number', width:140}
            //add by  gejiaao satrt 列表项中委托额度后增加字段投标标位
            ,{field:'bidPrice', title: 'TrdCommandDispatcher001.bidPrice', width:140,sortable:false}
            //add by  gejiaao end
        	,{field:'finishAmt', title: 'TrdCommandDispatcher001.finshAmt',type:'number', width:140}
        	,{field:'otherCcy',  title: 'TrdCommandDispatcher001.ccy',width:120, sortField:'ccy'}
        	,{field:'entrustDateStr', title: 'TrdCommandDispatcher001.entrustDate',type:'date',sortField:'entrustDate'}
        	,{field:'vdateStr', title: 'TrdCommandDispatcher001.vdate',type:'date',sortField:'vdate'}
        	,{field:'mdateStr', title: 'TrdCommandDispatcher001.mdate',type:'date',sortField:'mdate'}
        	,{field:'remark', title: 'TrdCommandDispatcher001.remark',width:150}
        	,{field:'entrustStatus', visible:false}
        	,{field:'tradeId', visible:false}
        	,{field:'finprodId', visible:false}
        	,{field:'userName', visible:false}
			,{field:'bussBidDate', visible:false}
			,{field:'entrustType', visible:false}
            ,{field:'purchModle', visible:false}
        ]
    });
    /**
     * 导出事件
     */
    var exportExcel = function() {
        var paramObj = getPageParams();
        Joyin.exportExcel('export', paramObj);
    };
    /**
     * 画面加载完成
     */
    $(function() {
    	//自动分发变色
    	if(VDATE != null && VDATE != '' && MDATE != null && MDATE != ''){
    		if(new Date(SYS_DATE) >= new Date(VDATE) && new Date(SYS_DATE) <= new Date(MDATE)){
    			$('#TrdCommandDispatcher001_btn_autoDistribute').css("background","#fdb633d9")
    		}
    	}
    	
        // 绑定按钮事件
        $('#TrdCommandDispatcher001_btn_search').click(function() {
            table.load();
        });
        $('#TrdCommandDispatcher001_btn_distribute').click(function(){
        	if(table!=null && table!=''){
        		var tableData = table.getAllCheckedRows();
            }
        	if( ! tableData || tableData.length==0){
        		 Joyin.error(JoyinUtil.getMessage("BUSS_COM_E_0026"));
                 return;
            }
        	//得到选中的委托代码 资产代码 资产类型
        	var entrustIds="";
        	var finprodMarketIds = "";
        	var finprodType2s = "";
        	var entrustStatus="";
        	var userNames="";
			var bussBidDate="";
        	for(var i=0;i<tableData.length;i++){
        		entrustIds += tableData[i].entrustId + ",";
        		finprodMarketIds += tableData[i].finprodMarketId + ",";
        		finprodType2s += tableData[i].finprodType2 + ",";
        		entrustStatus += tableData[i].entrustStatus + ",";
        		userNames +=tableData[i].userName + ",";
				bussBidDate +=tableData[i].bussBidDate + ",";
        	}
        	//去掉拼接最后一位逗号
        	entrustIds = (entrustIds.substring(entrustIds.length - 1) == ',') ? entrustIds.substring(0, entrustIds.length - 1) : entrustIds;
        	finprodMarketIds = (finprodMarketIds.substring(finprodMarketIds.length - 1) == ',') ? finprodMarketIds.substring(0, finprodMarketIds.length - 1) : finprodMarketIds;
        	finprodType2s = (finprodType2s.substring(finprodType2s.length - 1) == ',') ? finprodType2s.substring(0, finprodType2s.length - 1) : finprodType2s;
        	entrustStatus = (entrustStatus.substring(entrustStatus.length - 1) == ',') ? entrustStatus.substring(0, entrustStatus.length - 1) : entrustStatus;
        	userNames = (userNames.substring(userNames.length - 1) == ',') ? userNames.substring(0, userNames.length - 1) : userNames;
			bussBidDate = (bussBidDate.substring(bussBidDate.length - 1) == ',') ? bussBidDate.substring(0, bussBidDate.length - 1) : bussBidDate;
        	var object = {finprodMarketIds: finprodMarketIds, finprodType2s:finprodType2s, entrustIds:entrustIds, userNames:userNames, bussBidDate:bussBidDate,entrustStatus:entrustStatus };
        	//分发的check
        	Joyin.ajaxJson(JoyinUtil.getRealPath('invest/trade/investtrade/commanddispatcher/TrdCommandDispatcher001/check'), {entrustId:entrustIds,entrustStatus:entrustStatus,operateType:'distribute'},function(result) {
        		Joyin.dialog(JoyinUtil.getLabel("TrdCommandDispatcher001.selectUser"),
                		JoyinUtil.getRealPath("invest/trade/investtrade/commanddispatcher/TrdCommandDispatcher002/init"),
                				'45%', '65%',object,function(){table.reload();});
            });
        	
        });
        
        //自动分发事件
        $('#TrdCommandDispatcher001_btn_autoDistribute').click(function(){

        	Joyin.dialog(JoyinUtil.getLabel("TrdCommandDispatcher003.autodispense"),
            		JoyinUtil.getRealPath("invest/trade/investtrade/commanddispatcher/TrdCommandDispatcher003/init"),
            				'60%', '70%',"",function(){table.reload();});

        	
        });
        //分发权限配置
		$('#TrdCommandDispatcher001_btn_autoDistributePower').click(function(){

            Joyin.dialog(JoyinUtil.getLabel("TrdCommandDispatcher004.authConfig"),
                JoyinUtil.getRealPath("invest/trade/investtrade/commanddispatcher/TrdCommandDispatcher004/init"),
                '60%', '70%',"",function(){table.reload();});
		});
        //导出
        $('#TrdCommandDispatcher001_btn_export').click(function() {
            exportExcel();
        });
        
      //平安需求变更-指令分发定时自动刷新 sjj add 2019/04/03 start
        function myrefresh()
        {
    		table.reload();
        }
        setInterval(function(){myrefresh()},30000); 
        //平安需求变更-指令分发定时自动刷新 sjj add 2019/04/03 end
       
    });
}());