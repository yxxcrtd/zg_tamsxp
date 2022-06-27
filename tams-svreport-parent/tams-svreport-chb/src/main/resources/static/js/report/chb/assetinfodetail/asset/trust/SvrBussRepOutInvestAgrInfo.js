/**
 * 资产管理计划报送信息组件
 */
(function(){
    /**
     * 添加必填标志
     */
    var starinvest = function() {
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

    var getElementById = function(id) {
        return $('#' + entityName_SvrBussRepOutInvestAgrInfo + '_' + id);
    };
    /**
     * 是否有预期收益率改变时执行的方法
     */
    // function repContainExpectedYieldChang() {
    //     var repContainExpectedYield = getElementById('repContainExpectedYield').val();
    //     if(repContainExpectedYield==FLAG_YES){
    //         getElementById('repMaxExpectedYield').prop('required', true);
    //         getElementById('repMinExpectedYield').prop('required', true);
    //         star();
    //     }
    //     layui.form.render();
    // }

    /**
     * 交易流通市场改成其他交易流通场所时执行的方法
     */
    // function repTradeMarketChang() {
    //     var repTradeMarket = getElementById('repTradeMarket').val();
    //     if(repTradeMarket && repTradeMarket == TRADE_MARKET_99){
    //         getElementById('repRemark').prop('required', true);
    //         star();
    //     }
    //     else {
    //         getElementById('repRemark').prop('required', false);
    //         star();
    //     }
    //     layui.form.render();
    // }

    // var init = function(){
    //     repContainExpectedYieldChang();
    //     repTradeMarketChang();
    // }

    /**
     * 是否有预期收益率改变时执行的方法
     */
    // var repYieldFlagChang = function() {
    //     var repContainExpectedYield = getElementById("repContainExpectedYield")[0].checked;
    // 	if(!repContainExpectedYield){
    //         $("#"+entityName+"_repMaxExpectedYield").val("");
    //         $("#"+entityName+"_repMinExpectedYield").val("");
    // 		$("#"+entityName+"_repMaxExpectedYield").attr("disabled",true);
    // 		$("#"+entityName+"_repMinExpectedYield").attr("disabled",true);
    // 		getElementById('repMaxExpectedYield').prop('required', false);
    //         getElementById('repMinExpectedYield').prop('required', false).trigger('change');
    // 	}
    // 	else{
    // 		$("#"+entityName+"_repMaxExpectedYield").attr("disabled",false);
    // 		$("#"+entityName+"_repMinExpectedYield").attr("disabled",false);
    // 		getElementById('repMaxExpectedYield').prop('required', true);
    //         getElementById('repMinExpectedYield').prop('required', true).trigger('change');
    // 	}
    //     star();
    //     layui.form.render();
    // }

    // $(function() {
	// 	$("#"+entityName+"_repMaxExpectedYield").attr("disabled",true);
	// 	$("#"+entityName+"_repMinExpectedYield").attr("disabled",true);
    // 	layui.form.on('switch(repContainExpected)', function(data){
    //     	repYieldFlagChang();
	// 	});
    //     getElementById('repContainExpectedYield').on('change', function () {
    //         repContainExpectedYieldChang();
    //     });
    //     getElementById('repTradeMarket').on('change', function () {
    //         repTradeMarketChang();
    //     });
    //     repYieldFlagChang();
    // });


    $(function() {

        /**
         * 是否有预期收益率改变时执行的方法
         */

        var  investrateFlagChangeType=function(){
            if(getElementById('rateFlag').val()=='02'){
                //预期最高和最低收益率置灰不可修改
                getElementById('maxRate').val('').trigger("change");
                getElementById('maxRate').attr("disabled",true);

                getElementById('minRate').val('').trigger("change");
                getElementById('minRate').attr("disabled",true);
            }else{
                getElementById('maxRate').attr("disabled",false);
                getElementById('minRate').attr("disabled",false);
            }
        };

        investrateFlagChangeType();
        starinvest();

        //是否预期收益率change
        getElementById('rateFlag').change(function(){

            investrateFlagChangeType();
        });

    });
    var tips;
    $("label").on({
        mouseenter: function () {
            if(this.innerText.length>10){
                var that = this;
                tips = layer.tips("<span style='color:#000;'>" + this.innerText + "</span>", that, {
                    tips: [2, '#fff'],
                    time: 0,
                    area: 'auto',
                    maxWidth: 500
                });
            }
        },
        mouseleave:function(){
            layer.close(tips);
        }
    });

}());