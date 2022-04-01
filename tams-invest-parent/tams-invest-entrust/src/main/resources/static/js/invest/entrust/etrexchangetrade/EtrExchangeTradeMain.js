/**
 * 主页面JS
 */   

var correctData;

var currentEtrTypeTab = "0";

  /**
   * 指令提交或者保存
   */

  function saveOrSubmit(operateType) {
      var s;
      var object = {};
      var riskdata = {};
      if(currentEtrTypeTab == '0') {
          object = EtrExchangeTrade001.getSearchParams();
          if(object.entrustPrice&&object.entrustPrice<0){
              checkMessage(object.entrustPriceLabel);
              return;
          }
          if(object.entrustNum&&object.entrustNum<0){
              checkMessage(object.entrustNumLabel);
              return;
          }
          if(object.entrustAmt&&object.entrustAmt<0){
              checkMessage(object.entrustAmtLabel);
              return;
          }
          if(object.toShareAvailableNum&&object.toShareAvailableNum<0){
              checkMessage(object.toShareAvailableNumLabel);
              return;
          }
      }
      else {
          object = EtrExchangeTradeMulti001.getSearchParams();
      }

      object.operateType = operateType;
      //object.pageModel = pageModel;

      if(currentEtrTypeTab == '0') {
          riskdata = EtrExchangeTrade001.getriskdata(object);
          if(BaseRiskMathComm && EtrExchangeTrade001.isNeedRiskcal()) {
              s = BaseRiskMathComm.saveOrSubmitCheck(operateType);
              if(!s && s != ''){
                  return;
              }
              if (window.BaseRiskMathComm.isCalcConditionChanged(riskdata)){
                  return;
              }
          }
      }
      else {
          riskdata = EtrExchangeTradeMulti001.getRiskData(object);
          var riskCheckData = EtrExchangeTradeMulti001.getRiskCheckData(object);
          if(BaseRiskMathComm && EtrExchangeTradeMulti001.isNeedRiskcal()) {
              s = BaseRiskMathComm.saveOrSubmitCheck(operateType);
              if(!s && s != ''){
                  return;
              }
              if (window.BaseRiskMathComm.isCalcConditionChanged(riskCheckData)){
                  return;
              }
          }
      }

      //验证
      if(!checkDbClickData(object)){
          return;
      }
      if(currentEtrTypeTab == '0') {
          //TODO 风险和授信
//      Joyin.ajaxJson(basePath + 'invest/entrust/EtrCommonController/check', object, function(result1) {
          Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTrade001/check',object,
              function(result) {
                  Joyin.confirm(result.data,function() {
                      if (object.operateType === 'save') {
                          WorkFlow.doSaveList(basePath+'/invest/entrust/etrexchangetrade/EtrExchangeTrade001/save',object,"EtrExchangeTrade001_btn_save",null,function() {
                              if (EtrExchangeTrade001) {
                                  EtrExchangeTrade001.doReset(true);
                              }
                              //报错后风险试算标识置为01
                              // if(BaseRiskMathComm) {
                              //     BaseRiskMathComm.resetRiskMathFlag();
                              // }
                              JoyinUtil.disableButton("EtrExchangeTrade001_btn_save", false);
                          })
                      }else {
                          WorkFlow.doSubmitList(basePath+'/invest/entrust/etrexchangetrade/EtrExchangeTrade001/save',object,"EtrExchangeTrade001_btn_submit",null,function() {
                              if (EtrExchangeTrade001) {
                                  EtrExchangeTrade001.doReset(true);
                              }
                              //提交后风险试算标识置为01
                              // if(BaseRiskMathComm) {
                              //     BaseRiskMathComm.resetRiskMathFlag();
                              // }
                              JoyinUtil.disableButton("EtrExchangeTrade001_btn_submit", false);
                          })
                      }
                  });
              });
//      })
      }
      else {
          //TODO 风险和授信
//      Joyin.ajaxJson(basePath + 'invest/entrust/EtrCommonController/check', object, function(result1) {
          Joyin.ajaxJson(basePath + '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/check',object,
              function(result) {
                  Joyin.confirm(result.data,function() {
                      if (object.operateType === 'save') {
                          WorkFlow.doSaveList(basePath+'/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/save',object,"EtrExchangeTrade001_btn_save",null,function() {
                              if (EtrExchangeTradeMulti001) {
                                  EtrExchangeTradeMulti001.doReset(true);
                              }
                              JoyinUtil.disableButton("EtrExchangeTrade001_btn_save", false);
                          })
                      }else {
                          WorkFlow.doSubmitList(basePath+'/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001/save',object,"EtrExchangeTrade001_btn_submit",null,function() {
                              if (EtrExchangeTradeMulti001) {
                                  EtrExchangeTradeMulti001.doReset(true);
                              }
                              JoyinUtil.disableButton("EtrExchangeTrade001_btn_submit", false);
                          })
                      }
                  });
              });
//      })
      }
  }
  

  /**
   * 风险试算
   * @param url
   * @param object
   * @param riskdata
   * @returns
   */
  function doRiskcal() {
      if(currentEtrTypeTab == '0') {
          var object = EtrExchangeTrade001.getSearchParams();
          if(object && EtrExchangeTrade001.isNeedRiskcal()) {
              var riskdata = EtrExchangeTrade001.getriskdata(object);
              var riskInterfUrl = EtrExchangeTrade001.getRiskInterfUrl();
              if(riskdata && riskInterfUrl) {
                  window.BaseRiskMathComm.initBaseRiskMathRedis(riskInterfUrl, object,riskdata,riskMathRedisCallback);
              }
          }
      }
      else {
          var object = EtrExchangeTradeMulti001.getSearchParams();
          if(object && EtrExchangeTradeMulti001.isNeedRiskcal()) {
              var riskCheckData = EtrExchangeTradeMulti001.getRiskCheckData(object);
              var riskData = EtrExchangeTradeMulti001.getRiskData(object);
              var riskInterfUrl = EtrExchangeTradeMulti001.getRiskInterfUrl();
              if(riskData && riskInterfUrl) {
                  window.BaseRiskMathComm.initBaseRiskMathRedis(riskInterfUrl, riskData,riskCheckData,riskMathRedisCallback);
              }
          }
      }

  }
  /**
   * 风险试算的回调函数
   * @param object
   * @param cluflag
   * @returns
   */
  function riskMathRedisCallback(object,cluflag) {
      if(currentEtrTypeTab == '0') {
          EtrExchangeTrade001.riskResultdata = object;
          EtrExchangeTrade001.cluflag = cluflag;
      }
      else {
          EtrExchangeTradeMulti001.riskResultdata = object;
          EtrExchangeTradeMulti001.cluflag = cluflag;
      }

      Joyin.dialog(JoyinUtil.getLabel('EtrExchangeTradeRiskcal'), '/invest/entrust/etrexchangetrade/EtrExchangeTradeRiskcal/init', '60%', '75%',null);
  }

  /**
   * 双击校验后，将风险试算标识重置
   * @returns
   */
  function dbclickRefreshRisk(){
      // if(BaseRiskMathComm) {
      //     BaseRiskMathComm.resetRiskMathFlag();
      // }
  }
  
  /**
   * 验证剩余持仓符合零股是否全部卖出
   */
  var checkSalesOfSmallShares = function(params) {
	Joyin.confirm("剩余可用数量存在零股，是否卖出零股？", function() {
		EtrExchangeTrade001.setTotalSharesNum(params);
	});
  }

/**
 * 验证双击事件数据是否符合check规则
 */

var checkDbClickData = function(params){
	var tradePlat = params.tradePlat;//数据列表参数没有交易平台；
	var tradeMarket = params.tradeMarket;
	if(!tradePlat){//交易平台从参数左侧参数抽取
		var object = EtrExchangeTrade001.getSearchParams();
		if(object){
			tradePlat = object.tradePlat;
		}
	}
	if(!tradeMarket){//交易场所从参数左侧参数抽取
		var object = EtrExchangeTrade001.getSearchParams();
		if(object){
			tradeMarket = object.tradeMarket;
		}
	}
    var entrustType = params.entrustType;//只有右下角的指令列表有指令业务类型,其他列表没有指令业务类型，不可作为判断条件！
    var finprodType2 = params.finprodType2;
    if(entrustType && finprodType2){
        if(entrustType == "01" || entrustType == "02"
            || entrustType == "07" || entrustType == "09"
            || entrustType == "10" || entrustType == "11"
            || entrustType == "12" || entrustType == "13"){//竞价撮合平台  债券、股票、基金
            if(finprodType2 != FINPROD_TYPE_TG_F01 && finprodType2 != FINPROD_TYPE_TG_F06 && finprodType2 != FINPROD_TYPE_TG_F10 && finprodType2 != FINPROD_TYPE_TG_F02 && finprodType2 != FINPROD_TYPE_TG_F03 &&  finprodType2 != FINPROD_TYPE_TG_ZYSHG){
                Joyin.error("竞价交易仅支持债券、股票、基金、质押式回购交易，该资产类型不匹配!");
                return false;
            }
        }else if(entrustType == "03" || entrustType == "04" || entrustType == "05" || entrustType == "06"){//大宗交易平台 股票、基金、债券、质押式正回购、质押式逆回购
            if(finprodType2 != FINPROD_TYPE_TG_F06 && finprodType2 != FINPROD_TYPE_TG_F10 && finprodType2 != FINPROD_TYPE_TG_F01 && finprodType2 != FINPROD_TYPE_TG_F02 && finprodType2 != FINPROD_TYPE_TG_F03 &&  finprodType2 != FINPROD_TYPE_TG_ZYSHG){
                Joyin.error("大宗交易仅支持股票、基金、债券、质押式回购交易，该资产类型不匹配!");
                return false;
            }
        }
        else if(entrustType == "08") {
            if(finprodType2 != FINPROD_TYPE_TG_F01) {
                Joyin.error("固定收益仅支持债券交易，该资产类型不匹配!");
                return false;
            }
        }
    }else if(finprodType2){
    	if(tradePlat=="02" && finprodType2 != FINPROD_TYPE_TG_F01 && finprodType2 != FINPROD_TYPE_TG_F06 && finprodType2 != FINPROD_TYPE_TG_F10 && finprodType2 != FINPROD_TYPE_TG_F02 && finprodType2 != FINPROD_TYPE_TG_F03 &&  finprodType2 != FINPROD_TYPE_TG_ZYSHG){
            Joyin.error("竞价交易仅支持债券、股票、基金、质押式回购交易，该资产类型不匹配!");
            return false;
        }else if((tradePlat=="04" || tradePlat=="05") && finprodType2 != FINPROD_TYPE_TG_F06 && finprodType2 != FINPROD_TYPE_TG_F10 && finprodType2 != FINPROD_TYPE_TG_F01 && finprodType2 != FINPROD_TYPE_TG_F02 && finprodType2 != FINPROD_TYPE_TG_F03 && finprodType2 != FINPROD_TYPE_TG_ZYSHG){
            Joyin.error("大宗交易仅支持股票、基金、债券、质押式回购交易，该资产类型不匹配!");
            return false;
        }else if(tradePlat=="03" && finprodType2 != FINPROD_TYPE_TG_F01) {
            Joyin.error("选择的品种不支持固定收益平台!");
            return false;
        }else if(tradePlat=="03" && finprodType2 == FINPROD_TYPE_TG_F01){
		    if (tradeMarket == "04") {//04  深圳证券交易所
				Joyin.error("深圳证券交易所没有固定收益平台!");
				return false;
			}
        }
    }
    
    return true;
}

 //双击check
var dbclickCheck = function(data){
    if(data.entrustStatus != ENTRUST_STATUS_05&&data.entrustStatus != ENTRUST_STATUS_07){
        Joyin.error(data.entrustStatusString+"的指令不允许修改!");
        return false;
    }
    //验证数据
    if(!checkDbClickData(data)){
        return false;
    }

    return true;
}
//关闭委托
var closeEtr = function(){
    if(EtrExchangeTrade002){
        var selArr = EtrExchangeTrade002.getAllCheckedRows();;
    }
    if (!selArr || selArr.length == 0) {
        Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
        return;
    }else {
        for(var i=0; i<selArr.length; i++){
            if(selArr[i].entrustStatus == '02'){
                Joyin.error('不能对待接收的数据进行关闭操作');
                return;
            }
            if(selArr[i].entrustStatus == '04'){
                Joyin.error('不能对已完成的数据进行关闭操作');
                return;
            }
            if(selArr[i].entrustStatus == '06'){
                Joyin.error('已关闭状态的数据不允许再次关闭');
                return;
            }

            if(selArr[i].entrustStatus == '08'){
                Joyin.error("流程中的指令不允许关闭");
                return ;
            }
        }
        var object = {selectedItem: selArr, operateType:"close"};
        Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade003/closeCheck', object, function(result) {
            Joyin.confirm(JoyinUtil.getMessage("ETR_C_0002"), function(){
                Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade003/entrustClose', object, function(result) {
                    EtrExchangeTrade003.myrefresh();
                });
            });
        });
    }
}

/**
 * 
 * @returns
 */
function doReset() {
    if(currentEtrTypeTab == '0') {
        if (EtrExchangeTrade001) {
            Joyin.confirm('重置后会清空页面，确认重置？', function () {
                EtrExchangeTrade001.doReset();
                //重置风险试算标识置为01
                // if(BaseRiskMathComm) {
                //     BaseRiskMathComm.resetRiskMathFlag();
                // }
            });
        }
    }
    else {
        if (EtrExchangeTradeMulti001) {
            Joyin.confirm('重置后会清空页面，确认重置？', function () {
                EtrExchangeTradeMulti001.doReset();
                //重置风险试算标识置为01
                // if(BaseRiskMathComm) {
                //     BaseRiskMathComm.resetRiskMathFlag();
                // }
            });
        }
    }

}


//撤销委托
var revokeEtr = function(){
    if(EtrExchangeTrade002){
        var selArr = EtrExchangeTrade002.getAllCheckedRows();
    }

    if (!selArr || selArr.length == 0) {
        Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
        return;
    }else {
        for(var i=0; i<selArr.length; i++) {
            if (selArr[i].entrustStatus == '03') {
                Joyin.error('不能对已接收的数据进行撤回操作');
                return;
            }
            if (selArr[i].entrustStatus == '04') {
                Joyin.error('不能对已完成的数据进行撤回操作');
                return;
            }
            if (selArr[i].entrustStatus == '05') {
                Joyin.error('不能对已退回的数据进行撤回操作');
                return;
            }
            if (selArr[i].entrustStatus == '06') {
                Joyin.error('不能对已关闭的数据进行撤回操作');
                return;
            }
            if (selArr[i].entrustStatus == '07') {
                Joyin.error('已撤回的指令不允许再次撤回');
                return;
            }

            if (selArr[i].entrustStatus == '08') {
                Joyin.error("流程中的指令不允许撤回");
                return;
            }
        }
    }
        var object = {selectedItem: selArr, operateType:"revoke"};
        Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade003/revokeCheck', object, function(result) {
            Joyin.confirm(JoyinUtil.getMessage("BUSS_COM_C_0032","撤回"), function(){
                Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade003/entrustRevoke', object, function(result) {
                    EtrExchangeTrade003.myrefresh();
                });
            });
        });
}

function hideLeftTopPanel(){
    $("div.left_bottom_panel").each(function(i,v){
        $(v).css("display","none");
    });
    $("div.top_panel").each(function(i,v){
        $(v).css("display","none");
    });
    $("#enlarge").hide();
    $("#right_bottom_panel_dev").css("float","left");
    $("#right_bottom_panel_dev").css("width","95%");
    $("#bottom_panel_dev").css("height","450px");
    $("#right_bottom_panel_dev").css("height","420px");
}

function showLeftTopPanel(){
    $("div.left_bottom_panel").each(function(i,v){
        $(v).css("display","inline");
    });
    $("div.top_panel").each(function(i,v){
        $(v).css("display","inline");
    });
    $("#enlarge").show();
}


// 指令导入
var importEtr = function() {
    var currentTab=$('div.left_bottom_panel').find('iframe:eq(0)').contents().find("ul#tradePlatform_ul_tab li[class='layui-this']").attr('lay-id');
	Joyin.uploadFile({pageId :SHARES_TRADE_PLATFORM_04==currentTab?'EtrExchangeDzImport':SHARES_TRADE_PLATFORM_03==currentTab?'EtrExchangeGsImport':'EtrExchangeImport'}, function(fileName) {
        Joyin.ajaxJson(basePath + 'invest/entrust/etrexchangetrade/EtrExchangeTrade001/checkImportData', {fileName: fileName,currentTab:currentTab}, function(checkResult) {
            setTimeout( function () {
                if(checkResult.data.errorMsg){
                    Joyin.error(checkResult.data.errorMsg);
                    return;
                }
                Joyin.confirm(checkResult.data.confirmMsg,function() {
                    correctData = checkResult.data.correctData;
                    if(typeof correctData === 'string') {
                    	correctData = JSON.parse(correctData);
                    	if(correctData != null && correctData.length > 0){
                    		var data = [];
                    		for(var i in correctData){
                    			data.push(JSON.parse(correctData[i]));
                    		}
                    		correctData = data;
                    	}
                    }
                    var label=SHARES_TRADE_PLATFORM_04==currentTab?JoyinUtil.getLabel("EtrExchangeTradeImport.dzImport"):SHARES_TRADE_PLATFORM_03==currentTab?JoyinUtil.getLabel("EtrExchangeTradeImport.gsImport"):JoyinUtil.getLabel("EtrExchangeTradeImport.jjImport");
                    Joyin.dialog(label,
                        JoyinUtil.getRealPath("/invest/entrust/etrexchangetrade/EtrExchangeImport/init"),
                        "98%", "95%", {currentTab:currentTab},
                        function(){EtrExchangeTrade003.myrefresh();});
                });
            },1000);
        });
    },function() {
        table.reload();
    });
}

/***
 * 绑定单组合或多组合平台TAB切换事件
 */
var setZlPlatform =function(isMulti,deleteOther) {
    currentEtrTypeTab = isMulti=='Y'?1:0;
    isMulti = isMulti=='Y'?true:false;
    layui.use(['element'], function() {
        var element = layui.element;
        if(currentEtrTypeTab) {
            element.tabChange('zlPlatform', currentEtrTypeTab);
        }
        if(deleteOther) {
            var tabtitle = $(".layui-tab-title li");
            $.each(tabtitle, function (i) {
                var layId = $(this).attr("lay-id");
                if(isMulti && layId == '0') {
                    element.tabDelete('zlPlatform', layId);
                }
                else if(!isMulti &&  layId == '1') {
                    element.tabDelete('zlPlatform', layId);
                }
                else if(!layId) {
                    element.tabDelete('zlPlatform', layId);
                }
            });
        }
    });
}


$(function() {

    layui.use(['element'], function() {
        // 单组合和多组合指令页面切换
        var element = layui.element;
        element.on('tab(zlPlatform)', function(data) {
            currentEtrTypeTab = data.index;
            if(currentEtrTypeTab == 0) {
                $("#singlePtlDiv").show();
                $("#multiPtlDiv").hide();
            }
            else {
                $("#multiPtlDiv").show();
                $("#singlePtlDiv").hide();
            }
        });

    });

    //add by meifaxin 20211027 上下左右拖拽start
    Split(['#left_bottom_panel_dev', '#right_bottom_panel_dev'], {
        sizes: [42, 58],
        minSize: 100,
        direction: 'horizontal',
    });

    Split(['#top_panel_dev', '#bottom_panel_dev'], {
        sizes: [50, 50],
        minSize: 100,
        direction: 'vertical',
    });
    var defaultStyle = "";
    $("#enlarge").click(function () {
        enlarge_narrow("enlarge");
    });
    $("#narrow").click(function () {
        enlarge_narrow("narrow");
    });
    function enlarge_narrow(flag) {
        let rightDiv = $("#right_bottom_panel_dev");
        if(!rightDiv.attr("flag") || rightDiv.attr("flag") === "1"){
            defaultStyle = rightDiv.attr("style");
            rightDiv.attr("style", "position: absolute; left: 0px; top:0px; width: 100%; height:100%;background: #fff; z-index: 100");
            rightDiv.attr("flag","0");
        }else{
            rightDiv.attr("style", " float:right; width:57.5%; height:100%; display: inline-block;");
            rightDiv.attr("flag","1");
        }
        if(flag === "narrow"){
            $("#enlarge").show();
            $("#narrow").hide();
        }else{
            $("#enlarge").hide();
            $("#narrow").show();
        }
    }
    //add by meifaxin 20211027 上下左右拖拽 end
	 // 绑定提交按钮
    $('#EtrExchangeTrade001_btn_submit').click(function() {
        saveOrSubmit('submit');
    });
    
    // 绑定保存按钮
    $('#EtrExchangeTrade001_btn_save').click(function() {
        saveOrSubmit('save');
    });
    
    // 绑定取消按钮
    $('#EtrExchangeTrade001_btn_reset').click(function() {
        doReset();
    });
    
    // 绑定风险按钮
    $('#EtrExchangeTrade001_btn_riskcal').click(function() {
        doRiskcal();
    });
    if(pageModel==PAGE_MODEL_REFERENCE || pageModel==PAGE_MODEL_EDIT){

        setZlPlatform(isMulti,true);
    }

}());

var getIsNeedMutiPtl = function () {
    return isNeedMutiPtl;
}

var dialogSelf = {}
dialogSelf.flag="acd";
dialogSelf.dialog=function(url, param){
    EtrShowFinPlanAssetDetailInfo.searchDetail(param,url);
}

parent.EtrExchangeTradeMain = {
    dialogSelf:dialogSelf
}
EtrExchangeTradeMain = {
    dialogSelf:dialogSelf,
    currentEtrTypeTab:currentEtrTypeTab
}
