/**
 * 产品登记编码新建
 */
(function() {
    /**
     * 获取表单参数
     */
        var informationform = function() {
            return $.serializeToObject('#informationForm');
        }
        if(pageModel!=2){
            $('#prodInsId').change(function (){
                var prodInsId = $('#prodInsId').val();
                if(prodInsId!=null && prodInsId !=''){
                    Joyin.ajax(basePath+"svreport/system/proregistcodemanage/ProRegistCodeManage003/getProdNameByProdInsId",{prodInsId:prodInsId},function (result){
                        $('#prodName').val(result);
                        $('#prodId').val(prodInsId);
                    });
                }else{
                    $('#prodName').val('');
                    $('#prodId').val('');
                }
            });
        }

        /*var regCode = function(){
        	var regCode = $('#regCode').val();
        	if(regCode){
    			Joyin.ajax(basePath + "svreport/system/proregistcodemanage/ProRegistCodeManage003/selectCode",{prodId:prodId,regCode:regCode},
            			function(result){
            		if(result.data){
            			Joyin.error(JoyinUtil.getMessage("不同的产品代码不能对应同一产品登记编码。"));
            			$('#regCode').val('');
            			return;
            		}
    			});
    		}
        }*/

        /**
         * 保存或提交
         * @param operateType 操作类型（save，submit）
         * @returns
         */
        function saveOrSubmit(operateType) {
        	var vDate = $('#vdate').val();
        	var mDate = $('#mdate').val();
        	var prodId = $('#prodId').val();
        	var prodInsId = $('#prodInsId').val();
        	var regCode = $('#regCode').val();
            var object = $.serializeToObject('#informationForm');
            object.operateType = operateType;
            object.pageModel = pageModel;
            if(regCode && regCode.length != 14 && regCode.length != 15){
        		Joyin.error(JoyinUtil.getMessage("产品登记编码只能是14位或15位字符，请重新输入。"));
        		$('#regCode').val('');
        		return;
        	}
            if(regCode){
            	Joyin.ajax(basePath + "svreport/system/proregistcodemanage/ProRegistCodeManage003/selectCode",{prodInsId:prodInsId,regCode:regCode},
            			function(result){
            		if(result.data){
            			Joyin.error(JoyinUtil.getMessage("不同的行内标识不能对应同一产品登记编码。"));
            			return;
            		}else{
            		    Joyin.ajax(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/checkInsertData1', {prodInsId:prodInsId,regCode:regCode}, function(result) {
　　　　　　　　　　　　　　　　  if (result && result.data && result.data[0]) {
                                    Joyin.error(result.data[0].message);
                                    return;
                             }else{
                                Joyin.ajax(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/selectRegCode',{prodInsId:prodInsId,regCode:regCode}, function(result) {
                                         if(result>0){
                                             Joyin.error(JoyinUtil.getMessage("已经在产品基本信息维护模块中使用了的产品登记编码信息，禁止修改。"));
                                             return false;
                                         }else{
                                             Joyin.ajax(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/checkInsertData', {prodInsId:prodInsId,regCode:regCode}, function(result) {
                                                 Joyin.ajaxJson(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/check', object, function(result) {
                                                     Joyin.confirm(result.data, function() {
                                                         if(operateType === 'save') {
                                                             WorkFlow.doSaveList(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/save', object, "ProRegistCodeManage003_btn_save");
                                                         }
                                                         else {
                                                             WorkFlow.doSubmitList(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/save', object, "ProRegistCodeManage003_btn_submit");
                                                         }
                                                     });
                                                 });
                                             });
                                         }
                                });

                         }
                      });
            		}
    			});
            	/* Joyin.ajax(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/checkInsertData', {prodId:prodId,regCode:regCode}, function(result) {
                     Joyin.ajaxJson(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/check', object, function(result) {
                         Joyin.confirm(result.data, function() {                	
                             if(operateType === 'save') {                    	
                                 WorkFlow.doSaveList(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/save', object, "ProRegistCodeManage003_btn_save");               
                             }
                             else {                   	
                                 WorkFlow.doSubmitList(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/save', object, "ProRegistCodeManage003_btn_submit");
                             	}
                         });
                     });
                   });*/
            }else{
            Joyin.ajaxJson(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/check', object, function(result) {
                Joyin.confirm(result.data, function() {                	
                    if(operateType === 'save') {                    	
                        WorkFlow.doSaveList(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/save', object, "ProRegistCodeManage003_btn_save");               
                    }
                    else {                   	
                        WorkFlow.doSubmitList(basePath + 'svreport/system/proregistcodemanage/ProRegistCodeManage003/save', object, "ProRegistCodeManage003_btn_submit");
                    	}
                });
            });
        }
   }
    
        $(function() {
            // 按钮事件
            $('#ProRegistCodeManage003_btn_save').click(function() {
                saveOrSubmit('save');
            });
            $('#ProRegistCodeManage003_btn_submit').click(function() {
                saveOrSubmit('submit');
            });
            $('#prodId').change(function(){
            	prodId();
    		});
            /*$('#regCode').change(function(){
            	regCode();
    		});*/
            
        });    
}());