(function () {
    function query(ele){
        return $("#CfetsTrdRedeem001_bodydiv " + ele);
    }

    var riskResultdata;
    var initFlag = false;
    var cluflag;
    var businessId;
    var fprice;

    var businessModelReulst="";
    var entityBussCombDistritList;


    CfetsTrdRedeem001 = {

    }
    function getSearchParams() {
        var object = $.serializeToObject("#searchForm");
        return object;
    }
    function getBussCombDistribtTableParams() {
       var entityBussCombDistritList=ptlAllocatios||[];

       return entityBussCombDistritList;
    }
    var colsCombDistribt = [
        {
            field: 'portfolioId', width: 290, title: '组合代码',  sortable: false
            ,disabled:true
        }
        ,{
            field: 'portfolioName', width: 290, title: '组合名称',  sortable: false
            ,disabled:true
        }
        ,{
            field: 'secManageAcctId', width: 280, title: '证券账户',  sortable: false
            ,disabled:true
        }
       ,{
           field: 'shareAmt', width: 280, title: '账户持仓（万元）',  sortable: false
           ,disabled:true
           ,type: 'number'
           ,digit: 2
       }


    ]

    var table = query("#bussCombDistribtTable").joyintable({
        data:getBussCombDistribtTableParams(),
        cols: colsCombDistribt,
        page: true,
        addToLast: true,
        searchOnInit: true,
        checkbox: false,
        height: 290
    });

    var init = function () {
   debugger;
    }
    init();
   //绑定资产代码方法
   query("#finprodMarketId").change(function () {
   debugger;
       var data = query("#finprodMarketId").select2('data')[0];
       if (data) {
          query("#finprodMarketId").val(data.id);
          query("#finprodIdhid").val(data.object.finprodId);
          query("#ccy").val(data.object.ccy).trigger('change').trigger('blur');
          query("#theoreticalExerciseDate").val(data.object.exerciseDate);
          query("#actSetteDate").val(data.object.actExerciseDate);
          query("#finprodAbbr").val(data.text);
          query("#exercisePrice").val(data.object.price);
      }
      Joyin.ajaxJson(basePath + 'cfets/trdredeem/CfetsTrdRedeem001/queryCfetsBussCombDisList/'+data.object.finprodId, data.object.finprodId, function (result) {
            var data = result.data;
            if (data) {
                var shareAmtSum;
                entity_BussCombDistritList = data;
                $.each(entity_BussCombDistritList, function (index, item) {
                  shareAmtSum=JoyinMath.doAdd(shareAmtSum, item.shareAmt);
                })
                table.clear();
                table.addRow(entity_BussCombDistritList);
                query("#shareAmt").val(shareAmtSum);
            }
      });
    });
    function saveOrSubmit(operatorType) {
        var object = $.serializeToObject('#searchForm');
        object.operatorType = operatorType;
        object.pageId = "CfetsTrdRedeem001";
        var confirmMessage="是否确认提交?";
        if (operatorType === 'save') {
            confirmMessage="是否确认保存?";
        }
        Joyin.confirm(confirmMessage,
            function () {
                //保存 走流程
                if (operatorType === 'save') {
                    WorkFlow.doSaveList(basePath +'cfets/trdredeem/CfetsTrdRedeem001/save', object, "CfetsTrdRedeem001_btn_save", "CfetsTrdRedeem001",
                        function () {
                            var index = window.parent.layer.getFrameIndex(window.name);
                            window.parent.layer.close(index);
                        })
                }
                //提交 走流程
                else if (operatorType === 'submit') {
                     Joyin.ajaxJson(basePath + 'cfets/trdredeem/CfetsTrdRedeem001/checkByHome', object, function(result1) {
                      WorkFlow.doSubmitList(basePath + 'cfets/trdredeem/CfetsTrdRedeem001/save', object, "CfetsTrdRedeem001_btn_submit", "CfetsTrdRedeem001",
                         function () {
                             var index = window.parent.layer.getFrameIndex(window.name);
                             window.parent.layer.close(index);
                         })
                     })
                }
        })
    }
    // 绑定提交按钮
    $('#CfetsTrdRedeem001_btn_submit').click(function () {
        saveOrSubmit('submit');
    });

    // 绑定保存按钮
    $('#CfetsTrdRedeem001_btn_save').click(function () {
        saveOrSubmit('save');
    });

}());
