(function() {
    var getPageParams = function() {
        var prodId=$('#prodId').val();
        var regCode=$('#regCode').val();
        var solutions=$('#solutions').val();
        var status=$('#status').val();
        var occurDate=$('#occurDate').val();
        var replyDate=$('#replyDate').val();
        if (prodId==null || prodId=='') {
            prodId='';
        }
        if (regCode==null || regCode=='') {
            regCode='';
        }
        if (solutions==null || solutions=='') {
            solutions='';
        }
        if (status==null || status=='') {
            status='';
        }
        if (occurDate==null || occurDate=='') {
            occurDate='';
        }
        if (replyDate==null || replyDate=='') {
            replyDate='';
        }
        return {

            //产品代码
            prodIdList : prodId==''?null:prodId.join(","),
            //产品登记编码
            regCodeList :  regCode==''?null:regCode.join(","),
            //流动性风险应对措施
            solutionsList : solutions==''?null:solutions.join(","),
            //数据状态
            statusList : status==''?null:status.join(","),
            //风险发生日期
            occurDateStart : occurDate==''?null: $('#occurDate').startDate(),
            occurDateEnd : occurDate==''?null: $('#occurDate').endDate(),
            //风险应对日期
            replyDateStart : replyDate==''?null: $('#replyDate').startDate(),
            replyDateEnd : replyDate==''?null: $('#replyDate').endDate()
        };
    };
    var table = $('#LiquidityRiskInfo001Table').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        cols: [
            { title: "主键", field:'seqno',visible:false,width:'0%'},
            { title: "状态", field:'statusValid',visible:false,width:'0%'},
            { title: "数据状态", field:'statusStr',width:'6%',sortField:'status'},
            { title: "产品代码", field:'prodId',width:'8%',sortable: false},
            { title: "产品登记编码", field:'regCode',width:'12%',sortable: false},
            { title: "流动性风险应对措施", field:'solutionsStr',width:'20%',sortable: false},
            { title: "风险发生日期", field:'occurDateStr',width:'8%',sortField:'occur_date'},
            { title: "风险应对日期", field:'replyDateStr',width:'8%',sortField:'reply_date'},
            { title: "风险事件描述", field:'described',width:'20%',sortable: false},
            { title: "风险处理结果", field:'disposeResult', width:'20%',sortable: false},
            { title: "备注", field:'remark', width:'25%',sortable: false},
            { title: "错误信息", field:'errMsg', width:'25%',sortable: false},
            { title: "更新时间", field:'updateTimeStr', width:'12%',sortField:'update_time'},
            { title: "更新人", field:'updateUser', width:'10%',sortable: false},
            {field: 'operation', title: "操作",type: 'action',buttons: ['edit','delt'] ,width:'5%'}

        ]
    });

    function init(){
        $('#occurDate').val('');
        $('#replyDate').val('');
        setTimeout(function() {
            if($('#searchForm').joyinvalid()) {
                table.load();
            }
        },100);
    };

    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data) {
        data.pageModel = PAGE_MODEL_EDIT;
        data.operateType = 'edit';
        Joyin.dialog(JoyinUtil.getLabel("流动性风险应对情况信息维护修改"),
            basePath + "chb/liquidityriskinfo/LiquidityRiskInfo002/init",//打开新界面
            "70%", "85%", {pageModel:PAGE_MODEL_EDIT,
                seqno: data.seqno, selectedItem: [data]
            },function(){
                table.reload();//回调函数，表格刷新
            });
    });

    table.bind('click', '.delt', function(data, index) {
        var status= data.statusValid;
        if(status=='D'){
            layer.msg("删除状态的数据不支持删除！");
            return;
        }
        Joyin.confirm("确认删除吗?", function(){
            Joyin.ajax('delete', {keys:data.seqno}, function(result) {
                table.reload();
            });
        });
    });

    
    $(function() {
        init();
        //新建按钮点击事件
        $('#LiquidityRiskInfo001_btn_add').click(function() {
            Joyin.dialog(JoyinUtil.getLabel("流动性风险应对情况信息维护新建"),
                basePath + "chb/liquidityriskinfo/LiquidityRiskInfo002/init",
                "70%", "85%", {pageModel:PAGE_MODEL_NEW});
        });
        // 查询按钮点击事件
        $('#LiquidityRiskInfo001_btn_search').click(function() {
            if($('#searchForm').joyinvalid()) {
                table.load();
                initFlag=false;
            }
        });
        // 删除按钮点击事件
        $("#LiquidityRiskInfo001_btn_delete").click(function(){
            if(!table.isSearched()) {
                return;
            }
            var selArr = table.getAllCheckedRows();
            if (!selArr || selArr.length == 0) {
                Joyin.error("请从列表中选择数据");
                return;
            }
            var rowDatas = [];
            $(selArr).each(function(i, val) {
                rowDatas.push(val.seqno);
            });
            Joyin.confirm("确认要删除吗?",function() {
                Joyin.ajax(basePath + 'chb/liquidityriskinfo/LiquidityRiskInfo001/delete', {keys:rowDatas.join(',')}, function() {
                    $("#LiquidityRiskInfo001_btn_search").trigger("click");
                });
            });
        });
        // 导入按钮点击事件
        $('#LiquidityRiskInfo001_btn_import').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
                Joyin.uploadFile(function(fileName) {
                    Joyin.ajax(basePath + 'chb/liquidityriskinfo/LiquidityRiskInfo001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                        Joyin.ajax(basePath + 'chb/liquidityriskinfo/LiquidityRiskInfo001/importFile', {fileName: fileName});
                    });
                },function() {
                    table.reload();
                });
            });
        });
        // 导出按钮点击事件
        $('#LiquidityRiskInfo001_btn_export').click(function() {
            if (table != null && table != '') {
                // 是否有选中行
                var selArr = table.getAllCheckedRows();
                var datas = JSON.stringify(selArr);
            }
            if (!selArr || selArr.length == 0) {
                // 所有表格数据
                var paramObj = getPageParams();
                Joyin.exportExcel('export', paramObj);
            } else {
                Joyin.exportExcel('export', {
                    datas : datas
                });
            }
        });


    });
}());