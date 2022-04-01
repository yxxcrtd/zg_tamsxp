/**
 * 买断式回购多对多指令新建
 */
(function() {
    var getriskdata = function() {
        var selectedItem = $.serializeToObject('#searchForm').selectedItem;
        if (selectedItem.length > 0) {
            var list = [];
            var object = {
                tradeDirection : [ 'ps', 'EtrBoutRepo002.ps' ],
                portfolioCode : [ 'portfolioId', 'EtrBoutRepo002.portfolioId' ],
                tradeAmount : [ 'entrustAmt', 'EtrBoutRepo002.entrustAmt' ],
                assetType : [ 'assetType1', 'EtrBoutRepo002.assetType1' ],
                data : list
            };
            for ( var index in selectedItem) {
                var item = selectedItem[index];
                var trdProductDeal = {};
                trdProductDeal.tradeAmt = item.entrustAmt;
                trdProductDeal.unitCprice  = item.price;
                var obj = {
                    tradeDirection : item.ps,
                    timePoint : MY_SUP_TIME_TYPE_02,
                    valDate : sysDate,
                    portfolioCode : item.portfolioId,
                    tradeAmount : item.entrustAmt,
                    assetType : item.ps == ps_04 ? fintype_nhg : fintype_zhg,
                    isEnabled : '01',
                    fullDataFlag : '1',
                    repoTerm:item.repoTerm,
                    vdate:item.vdate,
                    mdate:item.mdate,
                    trdProductDeal : trdProductDeal
                };
                list.push(obj);
            }
            return object;
        }
        // if (selectedItem.length > 0) {
        //     var objects = new Array();
        //     for ( var index in selectedItem) {
        //         var item = selectedItem[index];
        //         var trdProductDeal = {};
        //         trdProductDeal.tradeAmt = item.entrustAmt;
        //         trdProductDeal.unitCprice  = item.price;
        //         if (item.ps == ps_03) {
        //             var obj = {
        //                 tradeDirection : item.ps,
        //                 timePoint : MY_SUP_TIME_TYPE_02,
        //                 valDate : sysDate,
        //                 portfolioCode : item.portfolioId,
        //                 tradeAmount : item.entrustAmt,
        //                 assetType : fintype_zhg,
        //                 isEnabled : '01',
        //                 fullDataFlag : '1',
        //                 trdProductDeal : trdProductDeal
        //             }
        //             var object = {
        //                 tradeDirection : [ 'ps', 'EtrBoutRepo002.ps' ],
        //                 portfolioCode : [ 'portfolioId', 'EtrBoutRepo002.portfolioId' ],
        //                 tradeAmount : [ 'entrustAmt', 'EtrBoutRepo002.entrustAmt' ],
        //                 assetType : [ 'assetType1', 'EtrBoutRepo002.assetType1' ],
        //                 data : obj
        //             }
        //         } else if (item.ps == ps_04) {
        //             var obj = {
        //                 tradeDirection : item.ps,
        //                 timePoint : MY_SUP_TIME_TYPE_02,
        //                 valDate : sysDate,
        //                 portfolioCode : item.portfolioId,
        //                 tradeAmount : item.entrustAmt,
        //                 assetType : fintype_nhg,
        //                 isEnabled : '01',
        //                 fullDataFlag : '1',
        //                 trdProductDeal : trdProductDeal
        //             }
        //             var object = {
        //                 tradeDirection : [ 'ps', 'EtrBoutRepo002.ps' ],
        //                 portfolioCode : [ 'portfolioId', 'EtrBoutRepo002.portfolioId' ],
        //                 tradeAmount : [ 'entrustAmt', 'EtrBoutRepo002.entrustAmt' ],
        //                 assetType : [ 'assetType1', 'EtrBoutRepo002.assetType1' ],
        //                 data : obj
        //             }
        //         } else {
        //             var obj = {
        //                 tradeDirection : item.ps,
        //                 timePoint : MY_SUP_TIME_TYPE_02,
        //                 valDate : sysDate,
        //                 portfolioCode : item.portfolioId,
        //                 tradeAmount : item.entrustNum,
        //                 assetType : fintype_zhg,
        //                 isEnabled : '01',
        //                 fullDataFlag : '1',
        //                 trdProductDeal : trdProductDeal
        //             }
        //             var object = {
        //                 tradeDirection : [ 'ps', 'EtrBoutRepo002.ps' ],
        //                 portfolioCode : [ 'portfolioId', 'EtrBoutRepo002.portfolioId' ],
        //                 tradeAmount : [ 'entrustAmt', 'EtrBoutRepo002.entrustAmt' ],
        //                 assetType : [ 'assetType1', 'EtrBoutRepo002.assetType1' ],
        //                 data : obj
        //             }
        //         }
        //         objects.push(object);
        //     }
        //     return objects;
        // }
    }
    // 初始化委托列表
    /**
     * 定义数据表格
     */
    var cols = [ {
        field : 'indexId',
        title : 'EtrFund003.indexId',
        width : 200,
        sortField : 'indexId',
        sortable: false
    }// 序号
    , {
        field : 'portfolioId',
        title : 'EtrBoutRepo001.portfolioId',
        width : 200,
        sortField : 'portfolioId',
        sortable: false
    }// 组合代码
    , {
        field : 'vdate',
        title : 'EtrBoutRepo001.vdate',
        width : 200,
        sortField : 'vdate',
        type : 'date',
        sortable: false
    }// 委托开始日
    , {
        field : 'mdate',
        title : 'EtrBoutRepo001.mdate',
        width : 200,
        sortField : 'mdate',
        type : 'date',
        sortable: false
    }// 委托终止日
    , {
        field : 'ps',
        visible : false,
        sortable: false
    }, {
        field : 'busPs',
        title : 'EtrBoutRepo001.ps',
        width : 200,
        sortField : 'ps',
        sortable: false
    }// 交易方向
    , {
        field : 'finprodId',
        title : 'EtrBoutRepo001.finprodId',
        width : 200,
        sortField : 'finprodId',
        visible : false,
        sortable: false
    }// 资产代码
    , {
        field : 'finprodMarketId',
        title : 'EtrBoutRepo001.finprodId',
        width : 200,
        sortField : 'finprodMarketId',
        sortable: false
    }// 资产代码
    , {
        field : 'finprodName',
        title : 'EtrBoutRepo001.finprodName',
        width : 200,
        sortField : 'finprodName',
        sortable: false
    }// 资产名称
    , {
        field : 'price',
        title : 'EtrBoutRepo001.price',
        width : 200,
        sortField : 'price',
        type : 'number',
        sortable: false
    }// 回购利率
    , {
        field : 'priceModelStr',
        title : 'EtrStock001.priceModel',
        width : 200,
        sortField : 'priceModel',
        sortable: false
    }// 价格模式名称
    , {
        field : 'priceModel',
        visible : false,
        sortable: false
    }// 价格模式
    , {
        field : 'firstprice',
        title : 'EtrBoutRepo003.firstprice',
        width : 200,
        sortField : 'firstprice',
        sortable: false
    }// 首次净价（最小）
    , {
        field : 'firstprice2',
        title : 'EtrBoutRepo003.firstprice2',
        width : 200,
        sortField : 'firstprice2',
        sortable: false
    }// 首次净价（最大）
    , {
        field : 'ccy',
        visible : false,
        sortable: false
    }// 币种
    , {
        field : 'busCcy',
        title : 'EtrBoutRepo001.ccy',
        width : '10%',
        sortField : "ccy",
        sortable: false
    }// 币种名称
    , {
        field : 'settleSpreedStr',
        title : 'EtrBoutRepo002.settleSpreed',
        width : 200,
        sortField : 'settleSpreed',
        sortable: false
    }// 清算速度
    , {
        field : 'settleSpreed',
        visible : false,
        sortable: false
    }// 清算速度名称
    , {
        field : 'repoTerm',
        title : 'EtrBoutRepo003.repoTerm',
        width : 200,
        sortField : 'repoTerm',
        sortable: false
    }// 回购期限（最小）
    , {
        field : 'repoTerm2',
        title : 'EtrBoutRepo003.repoTerm2',
        width : 200,
        sortField : 'repoTerm2',
        sortable: false
    }// 回购期限（最大）
    , {
        field : 'busTermType',
        title : 'EtrBoutRepo001.termType',
        width : 200,
        sortField : 'termType',
        sortable: false
    }// 期限品种名称
    , {
        field : 'termType',
        visible : false,
        sortable: false
    }// 期限品种
    , {
        field : 'calendarId',
        title : 'EtrBoutRepo002.calendarId',
        width : 120,
        sortField : 'calendarId',
        visible : false,
        sortable: false
    }// 交易日历
    , {
        field : 'calendarIdName',
        title : 'EtrBoutRepo002.calendarId',
        width : 120,
        sortField : 'calendarId',
        sortable: false
    }// 交易日历名称
    , {
        field : 'counterId',
        title : 'etrInvestPersonInfo.counterId',
        width : 120,
        sortField : 'counterId',
        visible : false,
        sortable: false
    }// 交易对手
    , {
        field : 'counterIdName',
        title : 'etrInvestPersonInfo.counterId',
        width : 120,
        sortable: false
    }// 交易对手名称
    , {
        field : 'tradeMarket',
        visible : false,
        sortable: false
    }, {
        field : 'tradeMarketStr',
        title : 'EtrFund002.tradePlat',
        width : 200,
        sortField : 'tradeMarket',
        sortable: false
    }// 交易场所
    , {
        field : 'securitiesCust',
        title : 'EtrStock002.secAcctId',
        width : 200,
        sortField : 'securitiesCust',
        visible : false,
        sortable: false
    }// 证券账户
    , {
        field : 'securitiesCustName',
        title : 'EtrStock002.secAcctId',
        width : 200,
        sortField : 'securitiesCust',
        sortable: false
    }// 证券账户名称
    , {
        field : 'entrustAmt',
        title : 'EtrBoutRepo002.entrustAmt',
        width : 200,
        sortField : 'entrustAmt',
        sortable: false
    }// 券面总额
    , {
        field : 'proAvailableCash',
        title : 'EtrStock003.proAvailableCash',
        width : 200,
        sortField : 'proAvailableCash',
        type : 'money',
        sortable: false
    }// 指令可用现金
    , {
        field : 'assetAvailablePosition',
        title : 'EtrStock003.assetAvailablePosition',
        width : 200,
        sortField : 'assetAvailablePosition',
        type : 'number',
        sortable: false
    }// 指令可用持仓
    , {
        field : 'chlId',
        title : 'EtrFund003.chlId',
        width : 200,
        sortfield : 'chlId',
        visible : false,
        sortable: false
    }// 通道
    , {
        field : 'chlIdName',
        title : 'invest.channelId',
        width : 200,
        sortfield : 'chlIdName',
        sortable: false
    }// 通道名称
    , {
        field : 'remark',
        title : 'EtrStock003.remark',
        width : 200,
        sortfield : 'remark',
        sortable: false
    }// 备注
    , {
        field : 'fileArray',
        title : 'EtrStock003.fileArray',
        width : 150,
        visible : false,
        sortable: false
    }, {
        field : 'businessId',
        width : 150,
        visible : false,
        sortable: false
    }, {
        field : 'fileArrayName',
        title : 'EtrStock003.fileArray',
        type : 'text',
        width : 150,
        sortable: false
    }, {
        field : 'operation1',
        title : 'SysCommon.operation',
        sortable: false,
        render : function(data, type, row, meta) {
            return '<a class="layui-btn layui-btn-sm uploadFile" >上传附件</a> ';
        },
        width : 150
    } ]
    var table = $("#dataTable").joyintable(
            {
                data : etrBoutRepoDtoList == null || etrBoutRepoDtoList == '' ? null : JSON
                        .parse(etrBoutRepoDtoList),
                cols : cols,
                loadonce : false,
                page: false,
                filter: false,
                checkbox : false,
                height : 290,
                addbtn : true,

            });
    // 绑定上传事件
    table.bind('click', '.uploadFile', function(data) {
        data.operateType = "edit";
        data.pageModel = PAGE_MODEL_EDIT;
        var object = {};
        object.rowData = JSON.stringify(data);
        object.businessId = data.businessId;
        Joyin.dialog("上传附件", JoyinUtil.getRealPath("component/entrust/EtrListUploadFile/init"),
                "88%", "85%", object, function(result) {
                    var fileArrayName = '';
                    if (result && result.fileArray && _.isArray(result.fileArray)
                            && result.fileArray.length > 0) {
                        for ( var index in result.fileArray) {
                            fileArrayName += result.fileArray[index].srcName + '，';
                        }
                        result.fileArrayName = fileArrayName.substr(0, fileArrayName.length - 1);
                    }
                    table.renderRow(data.LAY_TABLE_INDEX, result);
                });
    });
    /**
     * 保存或提交
     * 
     * @param operateType
     *            操作类型（save，submit）
     * @returns
     */
    function saveOrSubmit(operateType) {
        // 校验数据 校验表格
        var tableData = table.getAllRows();
        if (tableData.length == 0) {
            Joyin.alert("委托列表至少有一条数据！", 2);
            return false;
        }
        /**
         * 获取列表参数
         */
        var object = $.serializeToObject('#searchForm', true);
        object.operateType = operateType;
        object.pageModel = pageModel;
        var objects = object.selectedItem;
        for ( var index in objects) {
            var temp = objects[index];
            temp.operateType = operateType;
            temp.pageModel = pageModel;
            temp.riskTrialRetDto = object.riskTrialRetDto;
            if (operateType === 'submit') {
                temp.elementId = 'EtrBoutRepo002_btn_submit';
            }
        }
        Joyin.ajaxJson(basePath + 'invest/entrust/etrboutrepo/EtrBoutRepo003/check', objects,
                function(result) {
                    Joyin.confirm(result.data, function() {
                        if (operateType === 'submit') {
                            WorkFlow.doSaveList(basePath
                                    + 'invest/entrust/etrboutrepo/EtrBoutRepo003/save', objects,
                                    "EtrBoutRepo002_btn_submit", "EtrBoutRepo002");
                        }
                    });
                });
    }
    $(function() {
        if (etrBoutRepoDtoList) {
            $.each(JSON.parse(etrBoutRepoDtoList), function(id, value) {

            });

            table.reload({
                data : JSON.parse(etrBoutRepoDtoList)
            });
        }
        /**
         * 提交
         */
        $('#EtrBoutRepo003_btn_submit').click(function() {
            saveOrSubmit('submit');
        });
        /**
         * 风险试算
         */
        $('#EtrBoutRepo003_btn_riskcal').click(function() {
            //window.BaseRiskMathComm.initBaseRiskMathCommPage(getriskdata());
            // $("#riskMathFlag").val("02");
            var list = $.serializeToObject('#searchForm', true).selectedItem;
            var objectList = [];
            for(var i in list){
                var temp = {};
                temp.portfolioId = list[i].portfolioId;
                temp.ps = list[i].ps;
                temp.ccy = list[i].ccy;
                temp.entrustAmt = list[i].entrustAmt;
                temp.tradeMarket = list[i].tradeMarket;
                temp.price = list[i].price;
                temp.groupCode = 'F10_0_'+i;//格式类型+委托/交易+序号
                temp.repoTerm = list[i].repoTerm;
                temp.vdate = list[i].vdateText,
                    temp.mdate=list[i].mdateText,
                    objectList.push(temp);
            }
            window.BaseRiskMathComm.initBaseRiskMathRedis(basePath + 'riskrds/interf/batchRiskTrialRds/0/F10',
                objectList, getriskdata());
        });
    })

}());