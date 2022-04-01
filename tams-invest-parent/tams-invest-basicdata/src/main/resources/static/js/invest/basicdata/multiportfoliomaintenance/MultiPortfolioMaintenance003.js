$(function() {
    
    var  getAddParentNode = function(){
        var parms = {
                treeName:$('#treeName').val()
                
            };
        Joyin.ajax('addParentNode',
                parms,function(result){
                    //点击保存后关闭窗口
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
    }   
    $(function() {
        $('#MultiPortfolioMaintenance003_btn_save').click(function() {
            getAddParentNode();
        });
        
    });
    
    //父级名称
    $('#treeName').change(function() {
        checkLength('treeName',150);
    });
});
        
function checkLength(id, maxLength) {
    var length = $('#'+id).val().length;
    if (maxLength < length) {
        // lable名称，去必填星号
        var labelName = $('#' + id).parent().prev().text().replace('*', '');
        Joyin.error(JoyinUtil.getMessage(labelName + "的长度不能超过" + maxLength + "位！"));
        $('#'+id).val('');
    }
}
        
                    
                
                
           