$(function() {
    //跳转的页码
    var pageCount = 1;
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return {
            //反馈状态
            prodId: $('#prodId').val()==null?null:$('#prodId').val().join(","),
            assetCode: $('#assetCode').val()==null?null:$('#assetCode').val().join(","),
            //结束日（统计日期）
            enddate : $('#enddate').val(),
            flag:'01'
        }
    };

    var laydate = layui.laydate;
    laydate.render({
        elem: '#enddate',
        type: 'month',
        format: 'yyyyMM'

    });

    /**
     * 定义数据表格
     */
    var table=$("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return getSearchParams();
            }
        },
        cols:[
            { title: "主键", field: 'posuuid',sortable:false,visible:false},
            { title: "一级分类", width : 120, field: 'reportType1',sortField:'reportType1'},
            { title: "二级分类", width : 120, field: 'reportType2',sortField:'reportType2'},
            { title: "三级分类", width : 120, field: 'reportType3',sortField:'reportType3'},
            { title: "四级分类", width : 120, field: 'reportType4',sortField:'reportType4'},
            { title: "五级分类", width : 120, field: 'reportType5',sortField:'reportType5'},
            { title: "六级分类", width : 120, field: 'reportType6',sortField:'reportType6'},
            { title: "产品代码", width : 120, field: 'prodid',sortField:'prodid'},
            { title: "产品名称", width : 120, field: 'prodName',sortField:'prodName'},
            { title: "资产代码/科目号", width : 120, field: 'assetCode',sortField:'assetCode'},
            { title: "资产名称/科目名称", width : 120, field: 'assetName',sortField:'assetName'},
            { title: "资产起息日", width : 200, field: 'vdateStr', sortField:'vdate'},
            { title: "资产到期日", width : 200, field: 'mdateStr', sortField:'mdate'},
            { title: "最近开放计划结束日/行权日", width : 200, field: 'openplanStr',sortField:'openplan'},
            { title: "主体名称", width : 120, field: 'subjectName',sortField:'subjectName'},
            { title: "交易对手名称", width : 120, field: 'rivalParty',sortField:'rivalParty'},
            { title: "利率", width : 120, field: 'rateStr', type:'money',sortField:'rate'},
            { title: "币种", width : 120, field: 'ccyStr',sortField:'ccy'},
            { title: "行业分类", width : 120, field: 'industryStr',sortField:'industry'},
            { title: "企业规模分类", width : 120, field: 'partyType1Str',sortField:'partyType1'},
            { title: "除回购和拆借外贷款所属地区", width : 120, field: 'buyPlaceStr',sortField:'buyPlace'},
            { title: "成本", width : 120, field: 'costAmtStr', width : 120,sortField:'costAmt'},
            // { title: "应计利息", width : 120, field: 'intincexpStr', width : 120,sortField:'intincexp'},
            // { title: "利息调整", width : 120, field: 'intadjexpAddStr', width : 120,sortField:'intadjexpAdd'},
            { title: "公允价值变动", width : 120, field: 'floatIngplStr', width : 120,sortField:'floatIngpl'},
            { title: "减值", width : 120, field: 'lossAmtStr', width : 120,sortField:'lossAmt'},
            { title: "余额", width : 120, field: 'amtStr', width : 120,sortField:'amt'},
            { title: "投管一级分类", width : 120, field: 'orgAssetType1Str',sortField:'orgAssetType1'},
            { title: "投管二级分类", width : 120, field: 'orgAssetType2Str',sortField:'orgAssetType2'},
            { title: "投管三级分类", width : 120, field: 'orgAssetType3Str',sortField:'orgAssetType3'},
            { title: "资产负债剩余期限", width : 120, field: 'endDaysType',sortField:'endDays'},

        ]
    });


    /**
     * 导出
     */
    var exportExcel = function(flag) {
        var expArr=table.getAllCheckedRows();
        var param=getSearchParams();
        if(!param.enddate){
            Joyin.error("请选择统计日期进行导出！");
            return;
        }
        if(flag == '0'){
            Joyin.exportExcel("export",param);
        }else{
            if((!expArr || expArr.length ==0)){
                Joyin.error("请选择导出数据！");
                return;
            }
            var keyList = [];
            $.each(expArr, function(i, row) {
                keyList.push(row.posuuid);
            });
            param.keyList = keyList;
            Joyin.exportExcel('export', param);
        }

    };


    function init(){
    };
    init();

    $(function(){
        // 绑定按钮事件
        $('#PbcMonthReportDetailAsset001_btn_search').click(function() {
            var param=getSearchParams();
            if(!param.enddate){
                Joyin.error("请选择统计年月进行查询！");
                return;
            }else{
                table.load();
            }
        });

        $('#PbcMonthReportDetailAsset001_btn_export').click(function() {
            exportExcel(0);
        });
        $('#PbcMonthReportDetailAsset001_btn_exportCheck').click(function() {
            exportExcel(1);
        });


    });
}());