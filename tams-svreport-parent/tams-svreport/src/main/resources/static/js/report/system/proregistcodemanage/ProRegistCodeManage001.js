(function() {
    var getPageParams = function() {
        var regCode = $('#ProRegistCodeManage001_regCode').val();
        if(regCode==='为空'){
            regCode = null;
        }
        if (regCode) {
            regCode = regCode.join();
        }
        var orgCode = $("#orgCode").val();
        if (orgCode) {
            orgCode = orgCode.join();
        }
        return {
            // 产品
            prodname : $('#ProRegistCodeManage001_prod').val(),
            //投资者类型
            investor : $('#ProRegistCodeManage001_investor').val(),
            //产品登记编码
            regCode : regCode,
            //起息日开始
            //  vdateStart : $('#ProRegistCodeManage001_vdate').startDate(),
            //起息日结束
            // vdateEnd : $('#ProRegistCodeManage001_vdate').endDate(),
//            vdate : $('#ProRegistCodeManage001_vdate').val(),
            //到期日开始
            //  mdateStart : $('#ProRegistCodeManage001_mdate').startDate(),
            //到期日结束
            //  mdateEnd : $('#ProRegistCodeManage001_mdate').endDate(),
//            mdate : $('#ProRegistCodeManage001_mdate').val()
            //登记编码是否为空
            isNotNull : $("#ProRegistCodeManage001_isNotNull").prop("checked"),
            // raisevdateStart : $('#raise_vdate').startDate(),
            // raisevdateEnd : $('#raise_vdate').endDate(),

            orgCode : orgCode,
            //发送状态
            //sendStatus : $('#ProRegistCodeManage001_sendStatus').val()
        };
    };
    var table = $('#ProRegistCodeManage001_proRegistCodeManage').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        dblclick: function (table, data) {
            Joyin.dialog("产看产品登记编码",
                basePath + "svreport/system/proregistcodemanage/ProRegistCodeManage003/init",
                "99%", "99%", {
                    pageModel: PAGE_MODEL_REFERENCE,
                    prodInsId: data.prodInsId
                });
        },
        cols: [
            { title: "产品代码", field:'prodId',width:'12%'},
            { title: "行内标识码", field:'prodInsId',width:'12%',visible:false},
            { title: "产品名称", field:'prodName',width:'15%'},
            { title: "产品登记编码", field:'regCode',width:'10%'},
            { title: "机构代码", field:'orgCode', visible:false,width:'12%'},
            { title: "起息日", field:'vdate',width:'8%', visible:false},
            { title: "到期日", field:'mdate',width:'8%', visible:false},
            { title: "产品运作模式", field:'operateMode',width:'10%'},
            { title: "募集开始日", field:'raiseVdate', visible: isSvr,width:'8%'},
            { title: "募集起始日期（从）", field:'raisePstrDate', visible: isSvr,width:'10%'},
            { title: "募集起始日期（到）", field:'raisePendDate', visible: isSvr,width:'10%'},
            { title: "发送状态", field:'sendStatusStr',  visible: isSendStatusTableShow === YES,
                render : function(data, type, row, meta) {
                    //alert(isSendStatusTableLinkShow+'*'+row.sendStatusStr+'*');
                    if (isSendStatusTableLinkShow === 'N') {
                        return row.sendStatusStr;
                    }
                    return "<a  class='pro-link sendStatusLink' style='cursor: pointer;'>"+row.sendStatusStr+"</ a>";
                },sortField:"sendStatus"},
            { title: "发送备注", field:'sendRemark',  visible: isSendRemarkTableShow === YES}

        ]
    });

    // 发送状态link页面，各系统酌情使用
    table.bind('click','.sendStatusLink', function(row) {
        console.log(row.prodId);
        var label='';
        var pagePath='';
        pagePath = "svreport/system/proregistcodemanage/ProRegistCodeManage002/init";
        Joyin.dialog(JoyinUtil.getLabel(label), basePath + pagePath,
            "95%", "90%", {
                pageModel: PAGE_MODEL_REFERENCE,
                prodId: row.prodId
            });
    });
    /*
     * 页面的初始化函数 dingzhipeng add
     */
    function init(){
        $('#ProRegistCodeManage001_vdate').val('');
        $('#ProRegistCodeManage001_mdate').val('');
        if(!isSvr){
            $("#raiseDiv").hide();
        }
        if(isFromMain=="01"){
            $("#ProRegistCodeManage001_isNotNull").prop("checked",true);
            $(".layui-form-checkbox").addClass("layui-form-checked");
            $("#ProRegistCodeManage001_regCode").prop('disabled',true).select2('destroy').textSelect();
            setTimeout(function() {
                if($('#searchForm').joyinvalid()) {
                    table.load();
                }
            },100);
        }
    };

    /**
     * 发送销售系统接口
     */
    var sendRegistCodeToSale = function(){
        if(table!=null && table!=''){
            var selArr = table.getAllCheckedRows();
            //判断是否选中元素
            if( selArr != null &&  selArr.length > 0 ){
                for (var i = 1; i<= selArr.length; i ++) {
                    if('01' == selArr[i-1].sendStatus){
                        Joyin.error("列表中选择的"+ i +"行数据是发送成功的数据，不能再发送!");
                        return;
                    }
                }
                var data = {};
                data.selectedItem = selArr;
                data.operateType = 'sendRegistCode';
                Joyin.ajaxJson(basePath + '/interf/sale/send/SendToSaleInterf001/sendRegistCodeToSale', data,
                    function(result) {
                        var finprodIdStr = "";
                        $.each(selArr, function(idx,v) {
                            finprodIdStr = finprodIdStr + v.finprodId + ",";
                        });
                    }
                );
            }else{
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
        }else{
            Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
            return;
        }
    }
    /**
     * 新建
     */
    function showDialog() {
        var data={"operateType":"add"};
        Joyin.dialog('产品登记编码新建',basePath+'svreport/system/proregistcodemanage/ProRegistCodeManage003/init', '98%', '95%',data,function(){table.reload();});
    }

    $(function() {
        init();
        //新建按钮点击事件
        $('#ProRegistCodeManage001_btn_add').click(function() {
            showDialog();
        });
        // 查询按钮点击事件
        $('#ProRegistCodeManage001_btn_search').click(function() {
            if($('#searchForm').joyinvalid()) {
                table.load();
                initFlag=false;
            }
        });
        // 删除按钮点击事件
        $("#ProRegistCodeManage001_btn_delete").click(function(){
            if(!table.isSearched()) {
                return;
            }
            var selArr = table.getAllCheckedRows();
            if (!selArr || selArr.length == 0) {
                Joyin.error("请从列表中选择数据");
                return;
            }
            var rowDatas = [];
            var prdRegistCodes = "";
            var prodIds= "";
            $(selArr).each(function(i, val) {
                rowDatas.push(val.prodId+"|"+val.orgCode);
                prodIds += val.prodId+",";
                prdRegistCodes +=val.regCode+",";
            });
            Joyin.ajax(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage001/deleteOrEditCheck',{prdRegistCodes:prdRegistCodes,prodIds:prodIds},function(result){
                Joyin.confirm(result.data,function() {
                    Joyin.ajaxJson(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage001/deleteRegCode', {deleteKeys:rowDatas.join(',')}, function() {
                        $("#ProRegistCodeManage001_btn_search").trigger("click");
                    });
                });
            });
        });
        // 导入按钮点击事件
        $('#ProRegistCodeManage001_btn_import').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
                Joyin.uploadFile(function(fileName) {
                    Joyin.ajax(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                        Joyin.ajax(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage001/importFile', {fileName: fileName});
                    });
                },function() {
                    table.reload();
                });
            });
        });
        // 导出按钮点击事件
        $('#ProRegistCodeManage001_btn_export').click(function() {
            var param ={};
            var rows = table.getAllCheckedRows();
            if(rows.length > 0) {
                var keyList = [];
                $.each(rows, function(i, row) {
                    keyList.push(row.orgCode + '|' + row.prodId);
                });
                param.keys = keyList.join(',');
            }
            else{
                //有勾选优先勾选数据
                param = getPageParams();
            }
            Joyin.exportExcel("export",param);
        });

        // 导入报送按钮点击事件
        $('#ProRegistCodeManage001_btn_importnew').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
                var options = {};
                options.pageId = "ProRegistCodeManage001_2";
                Joyin.uploadFile(function(fileName) {
                    Joyin.ajax('checkConfirmMsgForImportData', {fileName: fileName}, function() {
                        Joyin.ajax('importFile', {fileName: fileName});
                    },function() {
                        $('iframe').contents().find("#loading").hide();
                    });
                },function() {
                    table.reload();
                });
            });
        });

        // 发送按钮点击事件
        $('#ProRegistCodeManage001_btn_sendSale').click(function() {
            sendRegistCodeToSale();
        });
        //登记编码为空点击事件
        var form = layui.form;
        form.on('checkbox(registCode)',function(data) {

            var isNotNull = $("#ProRegistCodeManage001_isNotNull").prop("checked");
            if(isNotNull==true){
                $("#ProRegistCodeManage001_regCode").val("为空").trigger("change");
                $("#ProRegistCodeManage001_regCode").prop('disabled',true);
            }else{
                $("#ProRegistCodeManage001_regCode").val("").trigger("change");
                $("#ProRegistCodeManage001_regCode").prop('disabled',false);
            }
            $("#ProRegistCodeManage001_regCode").select2('destroy').textSelect();
        });
    });
}());