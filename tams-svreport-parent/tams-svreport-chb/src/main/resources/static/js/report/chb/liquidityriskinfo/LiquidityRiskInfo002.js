

//新建
var doSubmit = function() {
    var object = $.serializeToObject('#submitForm', true);
    //新建
    Joyin.ajaxJson(basePath + 'chb/liquidityriskinfo/LiquidityRiskInfo002/insert',
        object,
        function (result) {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
}

/**
 * 初始化
 */
var init = function(){
    $('#LiquidityRiskInfo002_btn_save').hide()
    $('#LiquidityRiskInfo002_btn_othersave').hide()
};

$(function() {
    init();
    $('#LiquidityRiskInfo002_btn_submit').click(function() {
        doSubmit();
    });
    $('#prodId').change(function(){
        prodId();
    });
});



var prodId =function(){
    $('#regCode').val('');
    var prodId = $('#prodId').val();
    if(prodId){
        Joyin.ajax(basePath + "svreport/system/proregistcodemanage/ProRegistCodeManage003/selectCode",{prodId:prodId},
            function(result){
                if(result.data){
                    $('#regCode').val(result.data.REGCODE);
                }
            });
    }
}




