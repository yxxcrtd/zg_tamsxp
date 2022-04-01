/**
 * 银行间交易费用设置
 */
(function() {
    /**
     * 画星星
     */
    var star = function() {
        // 加红星星
        // 查找label
        var _findPrevLabel = function(element, level) {
            var $el = element.prev();
            if (!$el.is('label') && level > 0) {
                $el = $el.find('label');
                if ($el.length === 0) {
                    return _findPrevLabel(element.parent(), level - 1);
                }
            }
            return $el;
        };

        // 非查看模式
        if (typeof pageModel !== 'undefined'
            && (pageModel === PAGE_MODEL_EDIT || pageModel === PAGE_MODEL_NEW)) {
            $('.must').each(function() {
                $(this).remove();
            })
            $('*[required]').each(function() {
                var $el = $(this);
                var $target = _findPrevLabel($el, 3);
                if ($target.find('> .must').length === 0) {
                    $target.append($('<i class="must">*</i>'));
                }
            });
        }
    }

    /**
     * 保存或提交
     *
     * @param operateType
     *            操作类型（save，submit）
     * @returns
     */
    function saveOrSubmit(operateType) {
        /**
         * 获取列表参数
         */
        var object = $.serializeToObject('#submitForm');
        object.operateType = operateType;
        object.pageModel = pageModel;
        if ($('#isMlti').next().hasClass('layui-form-onswitch')) {
            object.isMlti = 'Y';
        } else {
            object.isMlti = 'N';
        }

        // if(object.isMlti == ''){
        // object.isMlti = 'N';
        // }
        // if(object.isMlti != 'N'){
        // object.isMlti = 'Y';
        // }
        // 取得页面交易方向和期限品种的数据
        var ps = $('#ps').val();
        var psTemp = null;
        if (ps != null)
            psTemp = ps.join(",");
        object.ps = psTemp;
        var termType = $('#termType').val();
        var termTypeTemp = null;
        if (termType != null)
            termTypeTemp = termType.join(",");
        object.termType = termTypeTemp;
        var selectedItem = [];
        if (ps) {
            $.each(ps, function(i1, v1) {
                if (!termType) {
                    var mstDto = getDto(object, v1);
                    selectedItem.push(mstDto);
                } else {
                    $.each(termType, function(i2, v2) {
                        var mstDto = getDto(object, v1);
                        mstDto.termType = v2
                        selectedItem.push(mstDto);
                    });
                }
            })
        }
        var allData = [];
        // 组合pk1数据
        var mstDto = new Object();
        mstDto.feeType = object.feeType;
        mstDto.registOrg = object.registOrg;
        mstDto.tradeType = object.tradeType;
        mstDto.effDate = object.effDate;
        mstDto.isMlti = object.isMlti;
        //添加交易市场
        if($('#tradeMarketMode').val()){
            mstDto.tradeMarketMode = object.tradeMarketMode;
        }
        if ($("#workflowId").val() != null && $("#workflowId").val() != '') {
            mstDto.workflowId = $("#workflowId").val();
        }
        if (ps) {
            mstDto.ps = ps.join(",");
        }
        if(termType){
            mstDto.termType = termType.join(",");
        }
        object.selectedItem = selectedItem;
        allData.push(mstDto);
        // 将所有数据json化存入allData
        object.allData = JSON.stringify(allData);
        object.pageId = "MstBankTranFeeManage002";
        Joyin.ajaxJson(
            basePath
            + 'invest/basicdata/mstbanktranfeemanage/MstBankTranFeeManage002/check',
            object,
            function(result) {
                Joyin
                    .confirm(
                        result.data,
                        function() {
                            if (operateType === 'save') {
                                WorkFlow
                                    .doSaveList(
                                        basePath
                                        + 'invest/basicdata/mstbanktranfeemanage/MstBankTranFeeManage002/save',
                                        object,
                                        "MstBankTranFeeManage002_btn_save");
                            } else {
                                WorkFlow
                                    .doSubmitList(
                                        basePath
                                        + 'invest/basicdata/mstbanktranfeemanage/MstBankTranFeeManage002/save',
                                        object,
                                        "MstBankTranFeeManage002_btn_submit");
                            }
                        });
            });
    }
    var getDto = function(object, v1) {
        var mstDto = new Object();
        mstDto.chargeBasis = object.chargeBasis;
        mstDto.chargeRate = object.chargeRate;
        mstDto.chargeType = object.chargeType;
        mstDto.chargeValue = object.chargeValue;
        mstDto.detailSeqNo = object.detailSeqNo;
        mstDto.effDate = object.effDate;
        mstDto.feeMaxi = object.feeMaxi;
        mstDto.feeMini = object.feeMini;
        mstDto.feeType = object.feeType;
        mstDto.isMlti = object.isMlti;
        mstDto.operateType = object.operateType;
        mstDto.pageModel = object.pageModel;
        mstDto.registOrg = object.registOrg;
        mstDto.batchSeq = object.batchSeq;
        mstDto.tradeType = object.tradeType;
        //添加交易市场
        if($('#tradeMarketMode').val()){
            mstDto.tradeMarketMode = object.tradeMarketMode;
        }
        mstDto.ps = v1;
        if ($("#workflowId").val() != null && $("#workflowId").val() != '') {
            mstDto.workflowId = $("#workflowId").val();
        }
        if ($("#batchSeq").val() != null && $("#batchSeq").val() != '') {
            mstDto.batchSeq = $("#batchSeq").val();
        }
        return mstDto;
    }
    // add by wangguoxian 20190718 start
    /**
     * 交易品种改变时相关联动
     */
    var tradeTypeChng = function() {
        var tradeType = $('#tradeType').val();
        // modified by wanguoxian 20190717 start
        // 当交易品种为回购时
        if (tradeType == PINAN_ASSET_TYPE_F01) {
            // 当交易品种为债券时
            $('#termType').val('');
            $('#termType').attr("disabled", true);

            //交易市场解除置灰
            $('#tradeMarketMode').attr("disabled", false);
            $('#tradeMarketMode').attr("required", true).trigger('change');

            // 是否多券种置灰
            $('#isMlti').prop("checked",false).prop("disabled", true).trigger('change');
            $('#isMlti').next().find("em").text('否');
            $('#isMlti').next().removeClass("layui-form-onswitch");
            $('#isMlti').next().addClass('layui-disabled');
            $('#termType').attr("required", false).trigger('change');
        } else {
            $('#termType').attr("disabled", false);
            $('#termType').attr("required", true).trigger('change');

            //交易市场置灰
            $('#tradeMarketMode').val('');
            $('#tradeMarketMode').attr("disabled", true);
            $('#tradeMarketMode').attr("required", false).trigger('change');

            if(tradeType == PINAN_ASSET_TYPE_F07 ){
                if($('#chargeType').val()&&$('#chargeType').val()==CHARGE_KIND_01){
                	// 是否多券种置灰
                    $('#isMlti').prop("checked",false).prop("disabled", true).trigger('change');
                    $('#isMlti').next().find("em").text('否');
                    $('#isMlti').next().removeClass("layui-form-onswitch");
                    $('#isMlti').next().addClass('layui-disabled');
                }else{
                	// 是否多券种可选
                    $('#isMlti').next().removeClass('layui-disabled');
                    $('#isMlti').prop("disabled", false);
                    $('#isMlti').trigger('change');
                }
            }
            else {
                // 是否多券种置灰
                $('#isMlti').prop("checked",false).prop("disabled", true).trigger('change');
                $('#isMlti').next().find("em").text('否');
                $('#isMlti').next().removeClass("layui-form-onswitch");
                $('#isMlti').next().addClass('layui-disabled');
            }

        }
        // modified by wanguoxian 20190717 end
        star();
    }
    /**
     * 计费类型改变后联动处理
     */
    var chargeTypeChng = function() {
        var chargeType = $('#chargeType').val();
        // 若计费类型选择为固定费率
        if (chargeType == CHARGE_KIND_01) {
            // 最大金额，最小金额，计费基数可以输入
            $('#feeMaxi').attr("disabled", false);
            $('#feeMini').attr("disabled", false);
            $('#chargeBasis').attr("disabled", false);
            // 计费基数必填
            $('#chargeBasis').attr("required", true).trigger('change');
            // 费率值可编辑
            $('#chargeRate').attr("disabled", false);
            $('#chargeRate').attr("required", true).trigger('change');
            $("#chargeRateDiv").show();
            // 费用值置灰
            $('#chargeValue').val("");
            $('#chargeValue').attr("disabled", true);
            $('#chargeValue').attr("required", false).trigger('change');
            $("#chargeValueDiv").hide();
            // 是否多券种置灰
//            $('#isMlti').prop("checked",false).prop("disabled", true);
//            $('#isMlti').next().removeClass("layui-form-onswitch");
//            $('#isMlti').next().addClass('layui-disabled');
//            if($('#tradeType').val() == PINAN_ASSET_TYPE_F07 ){
//            	// 是否多券种可选
//                $('#isMlti').next().removeClass('layui-disabled');
//                $('#isMlti').prop("disabled", false);
//                $('#isMlti').trigger('change');
//            }else{
            	$('#isMlti').prop("checked",false).prop("disabled", true).trigger('change');
                $('#isMlti').next().find("em").text('否');
                $('#isMlti').next().removeClass("layui-form-onswitch");
                $('#isMlti').next().addClass('layui-disabled');
//            }
        } else {// 若计费类型选择为固定金额
            // 最大金额，最小金额，计费基数不可以输入
            $('#feeMaxi').val('');
            $('#feeMaxi').attr("disabled", true);
            $('#feeMini').val('');
            $('#feeMini').attr("disabled", true);
            $('#chargeBasis').val('');
            $('#chargeBasis').attr("disabled", true);
            $('#chargeBasis').attr("required", false).trigger('change');
            // 费率值置灰
            $('#chargeRate').val("");
            $('#chargeRate').attr("disabled", true);
            $('#chargeRate').attr("required", false).trigger('change');
            $("#chargeRateDiv").hide();
            // 费用值可编辑
            $('#chargeValue').attr("disabled", false);
            $('#chargeValue').attr("required", true).trigger('change');
            $("#chargeValueDiv").show();
            // modified by wanguoxian 20190717 start
            if ($('#tradeType').val() == PINAN_ASSET_TYPE_F01
                || $('#tradeType').val() == PINAN_ASSET_TYPE_F10) {
                // 是否多券种置灰
                $('#isMlti').prop("checked",false).prop("disabled", true).trigger('change');
                $('#isMlti').next().find("em").text('否');
                $('#isMlti').next().removeClass("layui-form-onswitch");
                $('#isMlti').next().addClass('layui-disabled');

            } else {
                // 是否多券种可选
                $('#isMlti').next().removeClass('layui-disabled');
                $('#isMlti').prop("disabled", false);
                $('#isMlti').trigger('change');
            }
            // modified by wanguoxian 20190717 end
        }
        star();
    }
    
    /**
     * 费用类型改变时相关联动
     */
    var feeTypeChng = function(init) {
        var feeType = $('#feeType').val();
        if (typeof init == 'undefined' || !init) {
            $('#registOrg').val('').trigger('change');
        }
        if (feeType == FEE_TYPE_B06) {
            $('#registOrg').attr("disabled", true);
            $('#registOrg').attr("required", false).trigger('change');
        } else {
             $('#registOrg').attr("disabled", false);
             $('#registOrg').attr("required", true).trigger('change');
        }
        star();
    }
    
    // add by wangguoxian 20190718 start
    var init = function() {
        // var wfl_operType = $("#wfl_operType").val();
        // modified by wangguoxian 20190718 start
        if (pageModel == PAGE_MODEL_EDIT) {
            tradeTypeChng();
        }
    	chargeTypeChng();
        // modified by wangguoxian 20190718 end
    //  修改编辑画面空间没有根据交易费用联动 20191109 add start
        feeTypeChng(true);
    //    20191109 add end
    };
    $(function() {
        init();
        // 按钮事件
        $('#MstBankTranFeeManage002_btn_save').click(function() {
            saveOrSubmit('save');
        });
        $('#MstBankTranFeeManage002_btn_submit').click(function() {
            saveOrSubmit('submit');
        });
        // modified by wangguoxian 20190718 start
        $('#tradeType').change(function() {
            tradeTypeChng();
        });
        $('#chargeType').change(function() {
            chargeTypeChng();
        });
        // modified by wangguoxian 20190718 end
        
        $('#feeType').change(function() {
        	feeTypeChng();
        });
    });
}());
