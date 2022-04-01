$(function() {  
    var  addSonNode = function(){
        var parms = {
                parTreeId:parTreeId,
                treeName:$('#treeName').val()
                
            };
        Joyin.ajax('addSonNode',
                parms,function(result){
                    //点击保存后关闭窗口
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
    }   
    $(function() {
        $('#MultiPortfolioMaintenance004_btn_save').click(function() {
            addSonNode();
        });
        
    });
});
        
        
        
                    
                
                
           