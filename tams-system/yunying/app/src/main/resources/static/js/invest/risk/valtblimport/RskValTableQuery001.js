$(function () {
    /**
     * 获取查询参数
     */
    var getPageParams = function() {
       /* var fileName=[];
        $("#fileName option:selected").each(function (){
            if($(this).val() != null || $(this).val() != ''){
                fileName.push($(this).text())
            }
        })
        return {
            fileName:fileName
        }*/
        var fileNameArry=[];
        var fileHeaderArry=[];
        var subOneCatArry=[];
        var subTwoCatArry=[];
        var result = $.serializeToObject('#submitForm')
        var fileName = $("#fileName option:selected").each(function (){
            if($(this).val() != null || $(this).val() != ''){
                fileNameArry.push($(this).text())
            }
        });
        var fileHeader = $("#fileHeader option:selected").each(function (){
            if($(this).val() != null || $(this).val() != ''){
                fileHeaderArry.push($(this).text())
            }
        });
       /* var subOneCat = $("#subOneCat option:selected").each(function (){
            if($(this).val() != null || $(this).val() != ''){
                subOneCatArry.push($(this).text())
            }
        });*/
        /*var subTwoCat = $("#subTwoCat option:selected").each(function (){
            if($(this).val() != null || $(this).val() != ''){
                subTwoCatArry.push($(this).text())
            }
        });*/
        var subTwoCat=$('#subTwoCat').val();
        var subOneCat=$('#subOneCat').val();
        var subCode = $('#subCode').val();
        result.fileName = fileNameArry?fileNameArry.join(','):'';
        result.fileHeader = fileHeaderArry?fileHeaderArry.join(','):'';
        result.subCode = subCode?subCode.join(','):'';
        result.subOneCat = subOneCat?subOneCat.join(','):'';
        result.subTwoCat = subTwoCat?subTwoCat.join(','):'';

        return result;

        /*var result = $.serializeToObject('#submitForm');
        if($("#ptlId").val() == null || $("#ptlId").val() == ''){
            result.ptlId = $("#ptlId").val();
        }
        else{
            result.ptlId = $("#ptlId").val().join(',');
        }
        return result;*/

    };
    var dataTable=$("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        checkbox:false,
        lazyload:true,
        searchOnInit: false,
        isMulti: true,
        cols: [[
            {field:'fileName', title: '估值表名称',width:'8%', rowspan: 2,sortable: false}
            ,{field:'fileHeader', title: '估值表表头', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'fileDate', title: '估值日期', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'capital', title: '实收资本', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'fundAssetValCost', title: '基金资产净值（成本）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'fundAssetValSmv', title: '基金资产净值（市值）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'assetTotalCost', title: '资产合计（成本）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'assetTotalSmv', title: '资产合计（市值）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'liabilityTotalCost', title: '负债合计（成本）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'liabilityTotalSmv', title: '负债合计（市值）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'todUnitVal', title: '今日单位净值', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'porfit', title: '万份收益',rowspan: 2,sortable: false,width:'10%'}
            ,{field:'subCode', title: '标识代码', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'subName', title: '标识名称', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'subOneCat', title: '资产/负债一级类别', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'subTwoCat', title: '资产/负债二级类别', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'quantity', title: '数量', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'ccy', title: '币种', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'securityCost', title: '成本（本币）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'interest', title: '利息（本币）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'dividend', title: '股利/红利（本币）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'intAdj', title: '利息调整（本币）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'devalue', title: '减值（本币）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'valInc', title: '估值增值（本币）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'lateInt', title: '逾期利息（本币）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'smv', title: '市值（本币）', rowspan: 2,sortable: false,width:'10%'}
            ,{field:'shadowPriVal', title: '影子价格估值（本币）', rowspan: 2,sortable: false,width:'10%'}
        ]],
        done:function(){
            $(".laytable-cell-group").css({'text-align':'center'})
        }
    });

    /**
     * 明细查看
     */
    dataTable.bind('click', '.config', function(data, index) {
        var param = {};
        param.ptlId = data.ptlId;
        param.valDate = data.valDate;
        Joyin.dialog(JoyinUtil.getLabel("估值表明细"),
            basePath + "invest/risk/valtblimport/RskValTableQuery001/detailedInit",//打开新界面
            "95%", "70%", {paramJsonStr:JSON.stringify(param)
            },function(){
                dataTable.reload();
            });
    });

    /**
     * 导出
     */
    var exportExcel = function() {
        var paramObj = getPageParams();//获取参数
        if(!paramObj.valDate){
            Joyin.alert("请选择查询日期！",7);
            return;
        }
        Joyin.exportExcel('export', paramObj);//导出
    };
    $(function () {
        $("#RskValTableQuery001_btn_search").click(function () {
            var paramObj = getPageParams();//获取参数

            if(!paramObj.fileDate){
                Joyin.alert("请选择查询日期！",7);
                return;
            }
            dataTable.load();
        });
        //绑定到处按钮
        $('#RskValTableQuery001_btn_export').click(function() {
            exportExcel();//导出
        });
    })
});
