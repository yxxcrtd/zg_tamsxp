/**
 * 债券类资产中债报送组件JS
 * @auth zhangnian
 */
(function(){


    $(function() {
        var starFinaciall = function() {
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
        var  initBussRepAssetAstBill= function(){
            $("#svrBussRepAssetAstBillInfoDto_privateFlag").val('02').trigger("change");
            var assetTvalueBill=$('#assetType2').val();
            if(assetTvalueBill=='1210'){
                //类型默认05置灰不可修改
                $("#svrBussRepAssetAstBillInfoDto_notstandSecType").val('05').trigger("change");
                $("#svrBussRepAssetAstBillInfoDto_notstandSecType").attr("disabled",true);
            }else if(assetTvalueBill=='1201'){
                $("#svrBussRepAssetAstBillInfoDto_notstandSecType").val('').trigger("change");
                $("#svrBussRepAssetAstBillInfoDto_notstandSecType").attr("disabled",false);
            }
        }
        $('#svrBussRepAssetAstBillInfoDto_isBzhpj').change(function() {
            if ($('#svrBussRepAssetAstBillInfoDto_isBzhpj').val()=="01") {
                 $("#svrBussRepAssetAstBillInfoDto_billCode").prop("required",true).trigger('change');
                 $("#svrBussRepAssetAstBillInfoDto_billCode").attr("disabled",false).trigger('change');
            }else {
                 $("#svrBussRepAssetAstBillInfoDto_billCode").prop("required",false).trigger('change');
                 $("#svrBussRepAssetAstBillInfoDto_billCode").val('').attr("disabled",true).trigger('change');
            }
            starFinaciall();
        })
        starFinaciall();
         initBussRepAssetAstBill();


    })
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