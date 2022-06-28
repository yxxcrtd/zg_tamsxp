$(function() {
    $('#RegTimePoints002_btn_save').on('click',function(){
        // debugger
        var submitPointBond = $('#submitPointBond').val();
        var submitPointBank = $('#submitPointBank').val();

        var object = $.serializeToObject('#submitForm',true);

        if(submitPointBond&&!(submitPointBond<24&&submitPointBond>=0)){
            Joyin.alert("行内报送时点要求不能为空,且必须为0-23之间的数字",2);
            return;
        }

        if(submitPointBank&&!(submitPointBank<24&&submitPointBank>=0)){
            Joyin.alert("监管报送时点要求不能为空,且必须为0-23之间的数字",2);
            return;
        }

        //行内报送时点要求 超出 中债报送时点要求
        if(submitPointBank&&(submitPointBond - submitPointBank > 0)){
            Joyin.confirm("填写的行内报送时点要求超出中债报送时点要求范围，可能会导致报送不及时，请确认是否保存？", function(){
                Joyin.ajaxJson(basePath + 'svreport/system/regtimepoints/RegTimePoints002/save',object,function(result){
                    layui.layer.closeAll();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            });
        }else{
            Joyin.ajaxJson(basePath + 'svreport/system/regtimepoints/RegTimePoints002/save',object,function(result){
                layui.layer.closeAll();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            });
        }


    })
}())

function holderTypeChange(isperson){
    // 机构证件类别


//	$("#holderSex").attr("required",'required');
//	$("#holderSex").removeAttr("disabled");
}