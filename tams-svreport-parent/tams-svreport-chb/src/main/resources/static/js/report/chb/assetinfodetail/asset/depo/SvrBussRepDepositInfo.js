/**
 * 存款资产中债报送信息组件JS
 * @auth zhangnian
 */
(function(){
    
    var getElementById = function(id) {
        return $('#' + entityName_BussRepDepositInfo + '_' + id);
    };

    function byRepDepoType() {
        if (getElementById('repDepoType').val() == SVR_DEPO_TYPE_06) {
            getElementById('repTargetType').attr('disabled', false).trigger('change');
            getElementById('repTarget').attr('disabled', false).trigger('change');
        } else if(getElementById('repDepoType').val() != null){
            getElementById('repTargetType').val('').trigger('change');
            getElementById('repTarget').val('').trigger('change');
            getElementById('repTargetType').attr('disabled', true).trigger('change');
            getElementById('repTarget').attr('disabled', true).trigger('change');
        }
    }

    function byRepAssetType() {
        if (getElementById('repAssetType').val() == SVR_ASSET_TYPE_SECOND_LEVEL_1002 || getElementById('repAssetType').val() == SVR_ASSET_TYPE_SECOND_LEVEL_1003) {
            getElementById('repDepoType').attr('disabled', false).trigger('change');
        } else if(getElementById('repAssetType').val() != null){
            getElementById('repDepoType').val('').trigger('change');
            getElementById('repDepoType').attr('disabled', true).trigger('change');
        }
    }

    $(function(){
    	//本行存款、他行存款change事件
        // getElementById('repAssetType').change(function(){
        //     byRepAssetType();
        // });
        // //是否为结构性存款change事件
        // getElementById('repDepoType').change(function(){
        //     byRepDepoType();
        // });
        var starDeposit = function() {
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
            // $('.must').each(function() {
            //     $(this).remove();
            // })
            $('*[required]').each(function() {
                var $el = $(this);
                var $target = _findPrevLabel($el, 3);
                if ($target.find('> .must').length === 0) {
                    $target.append($('<i class="must">*</i>'));
                }
            });
        }

        //编辑按钮新进的时候，初始化星星和联动关系
        var  initRepDeposit= function(){
            var assetValuePosit=$('#assetType2').val();
            if(assetValuePosit=='1004'||assetValuePosit=='1005'){
                //存款类型置灰不可修改
                $("#svrBussRepDepositInfoDto_repDepoType").val('').attr("disabled",true);
            }else{
                $("#svrBussRepDepositInfoDto_repDepoType").attr("disabled",false);
            }
            if(assetValuePosit=='1002'||assetValuePosit=='1004'){
               //资金存入银行置灰不可修改
                $("#svrBussRepDepositInfoDto_bankName").val('').attr("disabled",true);
            }else{
                $("#svrBussRepDepositInfoDto_bankName").attr("disabled",false);
            }
        }
        starDeposit();
        initRepDeposit();

        //存款类型联动
        $("#svrBussRepDepositInfoDto_repDepoType").change(function(){
         var  depoType=$("#svrBussRepDepositInfoDto_repDepoType").val();
         if(depoType!='06'){
             //结构性存款挂钩标的类别/结构性存款挂钩标。该字段清空，且置灰不可修改
             $("#svrBussRepDepositInfoDto_repTargetType").val('').attr('disabled', true).trigger('change');
             $("#svrBussRepDepositInfoDto_repTarget").val('').attr("disabled",true);
         }else{
             $("#svrBussRepDepositInfoDto_repTargetType").attr("disabled",false);
             $("#svrBussRepDepositInfoDto_repTarget").attr("disabled",false);
         }
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