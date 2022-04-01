$(function() {  
    var  getEdit = function(){
    	if($('#treeName').val() ==null || $('#treeName').val() ==""){
    		Joyin.alert('父级名称不能为空！');
			return;
    	}
        var parms = {
                treeName:$('#treeName').val(),
                treeId:$('#treeId').val()
                
            };
        Joyin.ajax('getEdit',
                parms,function(result){
                    //点击保存后关闭窗口
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
    }    
    $(function() {
        $('#MultiPortfolioMaintenance005_btn_save').click(function() {
            getEdit();
        });
        
    });
});
        
        
        
                    
                
                
           