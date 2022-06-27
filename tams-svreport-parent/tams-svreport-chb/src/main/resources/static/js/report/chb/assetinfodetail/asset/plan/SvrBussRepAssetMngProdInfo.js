/**
 * 债券类资产中债报送组件JS
 * @auth zhangnian
 */
(function(){

    var getElementById = function(id) {
        return $('#' + BussRepAssetMngProdInfo_entityName + '_' + id);
    };
    if(getElementById('finprodType2').val() == FINPROD_TYPE_TG_F18){
        getElementById('repPrivateprdFlag_div').show();
        getElementById('repBondtostockFlag_div').show();
        getElementById('project_other_div').show();
    }
    $(function() {


        var startAssetMng = function() {
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


        /**
         * 是否有预期收益率改变时执行的方法
         */

        var  rateFlagChangeType=function(){
            if( getElementById('rateFlag').val()=='02'){
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
        var initAssetMng = function(){
            var assetTvalueAssetMng=$('#assetType2').val();
            //资产管理产品
            if(assetTvalueAssetMng=='1701'||assetTvalueAssetMng=='1703'||
                assetTvalueAssetMng=='1704'||assetTvalueAssetMng=='1706'||assetTvalueAssetMng=='1702'){
                $("#svrBussRepAssetMngProdInfoDto_financialComissue").val('').trigger("change");
                $("#svrBussRepAssetMngProdInfoDto_financialComissue").attr("disabled",true);
                $("#svrBussRepAssetMngProdInfoDto_content_one_div").hide();
            }else{
                $("#svrBussRepAssetMngProdInfoDto_financialComissue").attr("disabled",false);
                $("#svrBussRepAssetMngProdInfoDto_content_one_div").show();
                getElementById('financialProductFlag').trigger('change');
            }
        }
        initAssetMng();
        startAssetMng();

        //是否预期收益率change
        getElementById('rateFlag').change(function(){
            rateFlagChangeType();
        });
        getElementById('financialProductFlag').on('change', function () {
             if(getElementById('financialProductFlag').val()=="01") {
                $("#svrBussRepAssetMngProdInfoDto_prodRegisterCode").prop("required",true).trigger('change');
                $("#svrBussRepAssetMngProdInfoDto_prodRegisterCode").attr("disabled",false).trigger('change');
             }else {
                $("#svrBussRepAssetMngProdInfoDto_prodRegisterCode").attr("required",false).trigger('change');
                $("#svrBussRepAssetMngProdInfoDto_prodRegisterCode").val('').attr("disabled",true).trigger('change');
             }
             startAssetMng();
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