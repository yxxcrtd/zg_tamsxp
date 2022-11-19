/**
 * 付息计划详情
 */
$(function() {
    var cols = [];
    cols = [ {field:'subCode',  title: 'RskValTableImport001.subCode',width:200,sortable: false}
        ,{field:'subName',  title: 'RskValTableImport001.subName',width:200,sortable: false}
        ,{field:'securityQuantity',  title: 'RskValTableImport001.securityQuantity',width:150,type:'number',digit:8,sortable: false}
        ,{field:'unitCost',  title: 'RskValTableImport001.unitCost',width:150,type:'number',width:150,digit:8,sortable: false}
        ,{field:'securityCost',  title: 'RskValTableImport001.securityCost',width:150,type:'number',digit:8,sortable: false}
        ,{field:'ctnwr',  title: 'RskValTableImport001.ctnwr',width:150,type:'number',digit:8,sortable: false}
        ,{field:'mcp',  title: 'RskValTableImport001.mcp',width:150,type:'number',digit:8,sortable: false}
        ,{field:'smv',  title: 'RskValTableImport001.smv',width:150,type:'number',digit:8,sortable: false}
        ,{field:'mcr',  title: 'RskValTableImport001.mcr',width:150,type:'number',digit:8,sortable: false}
        ,{field:'valueAdded',  title: 'RskValTableImport001.valueAdded',width:150,sortable: false}
        ,{field:'suspensionInfo',  title: 'RskValTableImport001.suspensionInfo',width:150,sortable: false}
        ,{field:'equityInfo',  title: 'RskValTableImport001.equityInfo',width:150,sortable: false}
        ,{field:'securityCostOri', title: 'RskValTableImport001.securityCostOri',width:150,type:'number',digit:8,sortable: false}
        ,{field:'smvOri', title: 'RskValTableImport001.smvOri',width:150,type:'number',digit:8,sortable: false}
        ,{field:'valueAddedOri', title: 'RskValTableImport001.valueAddedOri',width:150,sortable: false}
        ,{field:'ccy', title: 'RskValTableImport001.ccy',width:150,sortable: false}
        ,{field:'exchangeRate', title: 'RskValTableImport001.exchangeRate',width:150,type:'number',digit:8,sortable: false}
        ,{field:'valType',  title: 'RskValTableImport001.valType', visible : false}
    ];
    var options = {
        ajax: {
            url: JoyinUtil.getRealPath('/invest/risk/valtblimport/RskValTableQuery001/selectRskValTableDetaile'),
            type: 'POST',
            dataType: 'json',
            data: function () {
                return getPreviewParams();
            }
        },
        cols: cols,
        searchOnInit: true,
        loadonce: true,
        checkbox: false,
        page: true,
        contextmenu : false
    };
    $("#rskValTableQueryDetailTable").joyintable(options);
    /**
     * 获取参数
     * @returns
     */
    function getPreviewParams() {
        return JSON.parse($('#paramJsonStr').val());
    }
});