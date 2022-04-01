/**
 * 交易所指令风险试算JS
 */

$(function() {
    
    var cols = [
                {field:'portfolioCode', visible: false}
                ,{field:'kriCode', visible: false}
                ,{field:'kriStatusCn', width:70, title: 'BaseRiskMathComm.currStatus',sortable:false,
                    render: function (data, type, row, meta) {
                        if(row.kriStatus == 0){
                            return "<span>正常</span>";
                        }
                        if(row.kriStatus == 1){
                            return "<span style='color:orange'>预警</span>";
                        }
                        if(row.kriStatus == 2){
                            return "<span style='color:#F7580D'>审批</span>";
                        }
                        if(row.kriStatus == 3){
                            return "<span style='color:#F71A0D'>违规</span>";
                        }
                        if(row.kriStatus == 4){
                            return "<span style='color:#951F1F'>异常</span>";
                        }
                        return row.kriStatus;
                    }
                }
                ,{field:'kriName',  title: 'BaseRiskMathComm.indName', width:250,sortable:false}
                ,{field:'kriTypeCn',  title: 'BaseRiskMathComm.indType', width:100,sortable:false}
                ,{field:'portfolioName',  title: 'BaseRiskMathComm.portfolioName', width:150,sortable:false}
                ,{field:'assetName',  title: 'BaseRiskMathComm.assetName', width:150,sortable:false}
                ,{field:'warningLine',  title: 'BaseRiskMathComm.wthreshold', width:150,sortable:false}
                ,{field:'approveLine',  title: 'BaseRiskMathComm.approveold', width:150,sortable:false}
                ,{field:'forbiddenLn',  title: 'BaseRiskMathComm.vthreshold', width:150,sortable:false}
                ,{field:'kriValue',  title: 'BaseRiskMathComm.currValue',width:150,sortable:false,render: function(data, type, row, meta){
                        if(data&&row.kriType.indexOf("FORBID_")!=-1){
                            return forbidVal(data,row.kriType);
                        }
                        return data;
                    }}
                ,{field:'oldValue',  title: 'BaseRiskMathComm.preValue',width:150,sortable:false/*,render: transferSupVal*/}
                ,{field:'kriTrend',  title: 'BaseRiskMathComm.kriTrend',width:70}
                ,{field:'kriStatus' ,visible: false}
                ,{field:'totalConclu', visible: false}
            ];
    
    var table = $("#baseRiskMathTable").joyintable({
        data: (!parent.EtrExchangeTradeMain || parent.EtrExchangeTradeMain.currentEtrTypeTab == '0')?(parent.EtrExchangeTrade001.riskResultdata==null?[]:parent.EtrExchangeTrade001.riskResultdata):(parent.EtrExchangeTradeMulti001.riskResultdata==null?[]:parent.EtrExchangeTradeMulti001.riskResultdata),
        cols : cols,
        searchOnInit:true,
        checkbox : false,
        height : 200,
        page:false,
        done: function(table, allData){
            if(null!=parent.EtrExchangeTrade001.cluflag && !parent.EtrExchangeTrade001.cluflag){
                $("#riskTrialSpan").html("&nbsp;&nbsp;"+JoyinUtil.getLabel("BaseRiskMathComm.noclusion"))

            }
            else{
                $("#riskTrialSpan").html("&nbsp;&nbsp;"+JoyinUtil.getLabel("BaseRiskMathComm.clusion")) 
            }
        }
    });  
    
    //解析禁投项
    function forbidVal(data,indType) {
        var wg=data;
        if(!wg||wg==''){
            return wg;
        }
        if(indType.indexOf("FORBID_")!=-1){
            /* 禁止型 */
            var secCell=false;
            if(wg.indexOf("||")!=-1){
                secCell=true;
            }
            var wgArr=wg.split(",");
            var wgStr='';
            for(var m=0;m<wgArr.length;m++){
                if(wgArr[m]!=null&&wgArr[m]!='') {
                    if(secCell) {
                        var wgArrSub = wgArr[m].split("||");
                        if(wgArrSub.length>1)
                            wgStr += wgArrSub[1]+",";
                        else
                            wgStr += wgArrSub[0]+",";
                    }else{
                        wgStr += wgArr[m]+",";
                    }
                }
            }
            wg=wgStr.substring(0,wgStr.length-1);
        }
        return wg;
    }
    
    $(function() {
       $("#EtrExchangeTradeRiskcal_btn_cancel").click(function() {
           layer.close(-1);
       });
              
    });
}());
