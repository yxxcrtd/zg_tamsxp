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
        var  initBussRepEquity = function(){
            var assetTvalueEquity=$('#assetType2').val();
            //权益类资产
            if(assetTvalueEquity=='1305'||assetTvalueEquity=='1302'){
                $("#svrBussRepEquityInfoDto_equityQuit").val('').trigger("change");
                $("#svrBussRepEquityInfoDto_equityQuit").attr("disabled",true);
            }else if(assetTvalueEquity=='1301'){
                $("#svrBussRepEquityInfoDto_equityQuit").attr("disabled",false);
            }
        }
        var  sharesTypeChangeType= function(){
             // 股票类型”选择“02 优先股”时必填，否则置灰不可填
            var assetTvalueEquity=$('#assetType2').val();
            //权益类资产
            if(assetTvalueEquity=='1305'||assetTvalueEquity=='1302' || assetTvalueEquity=='1301') {
                if ($("#svrBussRepEquityInfoDto_sharesType").val() != '02') {
                    $('#isBankCap').attr('disabled', true);
                    $("#isBankCap").val('').trigger("change");
                } else {
                    $('#isBankCap').attr('disabled', false);
                }
            }
        }

        starFinaciall();
        initBussRepEquity();
        $("#svrBussRepEquityInfoDto_sharesType").change(function(){
            sharesTypeChangeType();
        });
        if(pageModel==PAGE_MODEL_EDIT){
         $("#svrBussRepEquityInfoDto_financie").attr("disabled",true);
        }
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