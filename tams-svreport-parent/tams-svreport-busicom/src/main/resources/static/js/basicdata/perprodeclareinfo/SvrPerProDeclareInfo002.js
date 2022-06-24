/**
 * 中债报备信息共通业务组件
 * @auth zhangliang
 */
(function(){
    var existsProdInsId = false;
    var existsProdStageAndProdName = false;
    $('#prodInsId').blur(function (){
        var prodInsId = $('#prodInsId').val();
        if(prodInsId!='' && prodInsId !=null){
            Joyin.ajaxJson(basePath+'basicdata/perprodeclareinfo/SvrPerProDeclareInfo002/selectChbPrdDeclareByProdInsId?prodInsId='+prodInsId,{},function (result){
            if(result>0){
                Joyin.alert("行内识别已存在",2);
                existsProdInsId = true;
            }else{
                existsProdInsId = false;
            }
            })
        }
    });
   $('#prodStage').blur(function (){
       var prodStage = $('#prodStage').val();
       var prodName = $('#prodName').val();
       if(prodStage !=''&&prodStage!=null &&prodName !=''&&prodName!=null){
           Joyin.ajaxJson(basePath+'basicdata/perprodeclareinfo/SvrPerProDeclareInfo002/selectChbPrdDeclareByProdStageAndProdName?prodStage='+prodStage+'&prodName='+prodName,{},function (result){
               if(result>0){
                   Joyin.alert("产品名称和产品期次已存在",2);
                   existsProdStageAndProdName = true;
               }else{
                   existsProdStageAndProdName = false;
               }
           })
       }
   });
    $('#prodName').blur(function (){
        var prodStage = $('#prodStage').val();
        var prodName = $('#prodName').val();
        if(prodStage !=''&&prodStage!=null &&prodName !=''&&prodName!=null){
            Joyin.ajaxJson(basePath+'basicdata/perprodeclareinfo/SvrPerProDeclareInfo002/selectChbPrdDeclareByProdStageAndProdName?prodStage='+prodStage+'&prodName='+prodName,{},function (result){
                if(result>0){
                    Joyin.alert("产品名称和产品期次已存在",2);
                    existsProdStageAndProdName = true;
                }else{
                    existsProdStageAndProdName = false;
                }
            })
        }
    });
    /**
     * 添加必填标志
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
            });
            $('*[required]').each(function() {
                var $el = $(this);
                var $target = _findPrevLabel($el, 3);
                if ($target.find('> .must').length === 0) {
                    $target.append($('<i class="must">*</i>'));
                }
            });
        }
    };
    /**
     * 保存或提交按钮执行事件
     */
    var onSaveOrSubmitAction = function(operateType){
        if(existsProdInsId){
            Joyin.alert("行内识别已存在",2);
            return false;
        }
        if(existsProdStageAndProdName){
            Joyin.alert("产品名称和产品期次已存在",2);
            return false;
        }
        var object = $.serializeToObject('#submitForm',true);
        object.operateType = operateType;
        object.pageModel = pageModel;
        // 产品投资国家或地区
        if(object.investCountry){
            object.investCountry = object.investCountry.join(",");
        }
        // 境外托管机构国别
        if(object.outDepCtry){
            object.outDepCtry = object.outDepCtry.join(",");
        }
        // 投资者风险偏好
        if(object.riskType){
            object.riskType = object.riskType.join(",");
        }
        // 产品销售区域
        if(object.saleArea){
            object.saleArea = object.saleArea.join(",");
        }
        // 产品增信机构类型
        if(object.creditType){
            object.creditType = object.creditType.join(",");
        }
        // 产品特殊属性
        if (object.prdSpecProperty){
            object.prdSpecProperty = object.prdSpecProperty.join(",");
        }

        // 产品增信标识
        if(object.creditFlag && object.creditFlag === NO){
            object.creditFlag = HAS_NO;
        }
        // 发行机构提前终止权标识
        if(object.termRightFlag && object.termRightFlag === NO){
            object.termRightFlag = HAS_NO;
        }
        //客户赎回权标识
        if(object.cusRedeemFlag && object.cusRedeemFlag === NO){
            object.cusRedeemFlag = HAS_NO;
        }
        //判断发行机构代码是否已经维护 add by leipeng 20200727
        if(!object.issuePartyId){
            Joyin.alert("请维护当前机构的发行机构代码",2);
            return ;
        }
        Joyin.ajaxJson(basePath + 'basicdata/perprodeclareinfo/SvrPerProDeclareInfo002/check',object, function(result) {
            // Joyin.confirm(result.data, function() {
            //     if(operateType === 'save') {
            //         WorkFlow.doSaveList(basePath + 'basicdata/perprodeclareinfo/SvrPerProDeclareInfo002/save', object, "SvrPerProDeclareInfo002_btn_save");
            //     } else {
            //         WorkFlow.doSubmitList(basePath + 'basicdata/perprodeclareinfo/SvrPerProDeclareInfo002/save', object, "SvrPerProDeclareInfo002_btn_submit");
            //     }
            // })
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.submit")), function() {
                WorkFlow.doSubmitList(basePath + 'basicdata/perprodeclareinfo/SvrPerProDeclareInfo002/save', object, "SvrPerProDeclareInfo002_btn_submit");
            });
            // Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.submit")), function() {
            //     WorkFlow.doSubmitList(basePath + 'basicdata/perprodeclareinfo/SvrPerProDeclareInfo002/save', object, "SvrPerProDeclareInfo002_btn_submit");
            // });
        })

    };


    //资金投向地区联动
    $("#investArae").change(function(){
        var repInvestArea = $("#investArae").val();
        if(repInvestArea == saleAreaDeclareIn){
            // 产品投资国家或地区
            $("#investCountry").val('').trigger('change');
            $("#investCountry").attr('disabled',true);
            // 境外托管机构国别
            $("#outDepCtry").val('').trigger('change');
            $("#outDepCtry").attr('disabled',true);
            // 境外托管机构名称
            $("#outDepName").val('').trigger('change');
            $("#outDepName").attr('disabled',true);
        }else{
            $("#investCountry").attr('disabled',false);
            $("#outDepCtry").attr('disabled',false);
            $("#outDepName").attr('disabled',false);
        }
        if(repInvestArea == saleAreaDeclareOut){
            // 理财业务服务模式
            $("#finanSerModel").val('').trigger('change');
            $("#finanSerModel").attr('disabled',true);
            // 境内托管机构名称
            $("#inDepName").val('').trigger('change');
            $("#inDepName").attr('disabled',true);
            // 境内托管机构代码
            $("#inDepCode").val('').trigger('change');
            $("#inDepCode").attr('disabled',true);
        }else{
            $("#finanSerModel").attr('disabled',false);
            $("#inDepName").attr('disabled',false);
            $("#inDepCode").attr('disabled',false);
        }
    });
    // 产品管理模式联动
    $("#pmmode").change(function(){
        var pmmode = $("#pmmode").val();
        if(pmmode == prodManageModeBank || pmmode == prodManageModeSpv){
            // 实际管理人名称
            $("#mname").val('').trigger('change');
            $("#mname").attr("disabled",true);
        }else{
            // 实际管理人名称
            $("#mname").attr("disabled",false);
        }
    });
    // 设置最短期限联动
    $("#isShortTerm").change(function(){
        var isShortTerm = $("#isShortTerm").val();
        if(isShortTerm == isShortTerm_no){
            // 是否设置最短持有期限
            $("#shortTerm").val('').trigger('change');
            $("#shortTerm").attr("disabled",true);
            // 最短持有期后是否自由赎回
            $("#isFreeRedem").val('').trigger('change');
            $("#isFreeRedem").attr("disabled",true);
        }else{
            $("#shortTerm").attr("disabled",false);
            $("#isFreeRedem").attr("disabled",false);
        }
    });
    // 募集币种联动
    // $("#raiseCcy").change(function(){
    //     var repRaiseCcy = $("#raiseCcy").val();
    //     if(repRaiseCcy){
    //         // 兑付本金币种
    //         $("#cashPrinCcy").val(repRaiseCcy).trigger('change');
    //         // 兑付收益币种
    //         $("#cashProfCcy").val(repRaiseCcy).trigger('change');
    //     }
    // });

    // 合作模式联动
    $("#contMode").change(function(){
        var repContMode = $("#contMode").val();
        if(repContMode == copModeSigRun){
            // 合作机构名称
            $("#cooperationName").val('').trigger('change');
            $("#cooperationName").attr("disabled",true);
        }else{
            // 合作机构名称
            $("#cooperationName").attr("disabled",false);
        }
    });

    // 产品增信标识 机构 联动
    var repCreditFlagChange = function(){
        var repCreditFlag = $("#creditFlag")[0].checked;
        if(!repCreditFlag){
            // 产品增信机构类型
            $("#creditType").val('').trigger('change');
            $("#creditType").attr("disabled",true);
            // 产品增信形式
            $("#creditMode").val('').trigger('change');
            $("#creditMode").attr("disabled",true);
        }else{
            $("#creditType").attr("disabled",false);
            $("#creditMode").attr("disabled",false);
        }
    };
    // 产品运作模式 模式联动
    $("#operateMode").change(function(){
        var repOperateMode = $("#operateMode").val();
        if(repOperateMode == prodRunModeCloNN || repOperateMode == prodRunModeCloN){
            // 是否设置最短持有期限
            $("#isShortTerm").val('').trigger('change');
            $("#isShortTerm").attr("disabled",true);
            // 最短持有期限(天)
            $("#shortTerm").val('').trigger('change');
            $("#shortTerm").attr("disabled",true);
            // 最短持有期后是否自由赎回
            $("#isFreeRedem").val('').trigger('change');
            $("#isFreeRedem").attr("disabled",true);
            //add by yujiaping 2020-12-11 TISHKBS-1673 产品申报登记要素管理-产品运作模式为封闭式的置灰 是否现金管理类start
            $('#cashManaCate').val('').trigger('change').attr('disabled',true);
            //add by yujiaping 2020-12-11 TISHKBS-1673 产品申报登记要素管理-产品运作模式为封闭式的置灰 是否现金管理类end
        }else{
            // 是否设置最短持有期限
            $("#isShortTerm").attr("disabled",false);
            //
            $('#cashManaCate').attr('disabled', false);
        }
    });
    /**
     * 初始化
     */
    var svrPerProDeclareInfo002Init = function(){
        // // 兑付本金币种
        // $("#cashPrinCcy").attr("disabled",true);
        // // 兑付收益币种
        // $("#cashProfCcy").attr("disabled",true);
        //编辑页面行内标识码不可修改
        if(pageModel == PAGE_MODEL_EDIT){
            $("#prodInsId").attr("disabled",true);
        }
        // 资金投向地区为境内
        var repInvestArea = $("#investArae").val();
        // 境内
        if(repInvestArea != saleAreaDeclareInOut && repInvestArea != saleAreaDeclareOut){
            // 产品投资国家或地区
            $("#investCountry").val('').trigger('change');
            $("#investCountry").attr('disabled',true);
            // 境外托管机构国别
            $("#outDepCtry").val('').trigger('change');
            $("#outDepCtry").attr('disabled',true);
            //境外托管机构名称
            $("#outDepName").val('').trigger('change');
            $("#outDepName").attr('disabled',true);
        }
        // 境外
        if(repInvestArea != saleAreaDeclareIn && repInvestArea != saleAreaDeclareInOut){
            // 理财业务服务模式
            $("#finanSerModel").val('').trigger('change');
            $("#finanSerModel").attr('disabled',true);
            // 境内托管机构名称
            $("#inDepName").val('').trigger('change');
            $("#inDepName").attr('disabled',true);
            // 境内托管机构代码
            $("#inDepCode").val('').trigger('change');
            $("#inDepCode").attr('disabled',true);
        }
        // 产品运作模式
        var repOperateMode = $("#operateMode").val();
        // 产品运作模式 封闭式净值型 和 封闭式非净值型 的产品，此要素必须为空
        if(repOperateMode != prodRunModeOpeN && repOperateMode != prodRunModeOpeNN){
            // 是否设置最短持有期限
            $("#isShortTerm").val('').trigger('change');
            $("#isShortTerm").attr("disabled",true);
            // 最短持有期限 (天)
            $("#shortTerm").val('').trigger('change');
            $("#shortTerm").attr("disabled",true);
            // 最短持有期后是否自由赎回
            $("#isFreeRedem").val('').trigger('change');
            $("#isFreeRedem").attr("disabled",true);
        }
        // 产品增信标识
        var repCreditFlag = $("#creditFlag")[0].checked;
        if(!repCreditFlag){
            // 产品增信机构类型 产品增信标识 02 无 时，该要素不可填；
            $("#creditType").val('').trigger('change');
            $("#creditType").attr("disabled",true);
            // 产品增信形式 产品增信标识 02 无 时，该要素不可填;
            $("#creditMode").val('').trigger('change');
            $("#creditMode").attr("disabled",true);
        }
        // 合作模式
        var repContMode = $("#contMode").val();
        if(repContMode == copModeSigRun || !repContMode){
            // 合作机构名称 当 合作模式 要素选择 独立运作 时，此要素不可填
            $("#cooperationName").val('').trigger('change');
            $("#cooperationName").attr("disabled",true);
        }
        // 是否设置最短持有期限 联动
        var isShortTerm = $("#isShortTerm").val();
        if(isShortTerm != isShortTerm_yes){
            // 最短持有期限（天） 是否设置最短持有期限 的选项为 是 时，可编辑，否则不可填；
            $("#shortTerm").val('').trigger('change');
            $("#shortTerm").attr("disabled",true);
            // 最短持有期后是否自由赎回 是否设置最短持有期限 的选项为 是 时，必填，否则不可填；
            $("#isFreeRedem").val('').trigger('change');
            $("#isFreeRedem").attr("disabled",true);
        }
        // 产品管理模式
        var repPmmode = $("#pmmode").val();
        if(repPmmode != prodManageModeActM && repPmmode != prodManageModeOthO){
            // 实际管理人名称 当 产品管理模式 要素选择 银行为实际管理人 或 特殊目的载体（SPV） 时，
            // 此要素必须为空;
            $("#mname").val('').trigger('change');
            $("#mname").attr("disabled",true);
        }
        // 系统参数SVR_IS_SUBSIDIARY 01 是子公司时   ‘新老产品标记’置灰置空
        if(svrIsSubsidiary == '01'){
            // 新老产品标记
            $("#isFhxgcp").attr("disabled",true);
        }else{
            $("#isFhxgcp").attr("disabled",false);
        }
    };

    $(function() {
        $('#btnSave_wfl').hide();
        star();
        // 产品增信标识
        repCreditFlagChange();
        layui.form.on('switch(repCreditFlagFilter)', repCreditFlagChange);
        svrPerProDeclareInfo002Init();
        // 保存按钮执行事件
        $('#SvrPerProDeclareInfo002_btn_save').click(function(){
            onSaveOrSubmitAction('save');
        });
        // 提交按钮执行事件
        $('#SvrPerProDeclareInfo002_btn_submit').click(function(){
            onSaveOrSubmitAction('submit');
        });

        // 规则按钮，显示该字段如何显示
        $('#repInvTypeRat_showRule').click(function(){
            Joyin.alert(JoyinUtil.getLabel("SvrPerProDeclareInfo002.repInvTypeRat.showRule"));
        });

        // 规则按钮，显示该字段如何显示
        $('#liaisonTel_showRule').click(function(){
            Joyin.alert(JoyinUtil.getLabel("SvrPerProDeclareInfo002.liaisonTel.showRule"));
        });
    });
}());
