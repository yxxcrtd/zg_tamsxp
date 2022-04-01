/**
 * 机构登记银行代码新建修改画面
 */
var pbcBankCodeErrFlag = false;
var SysInstRegBankCode002 = function() {
        return{
             init : function(){
                if(pageModel == PAGE_MODEL_EDIT){
                    $('#SysInstRegBankCode002_orgCode').prop("disabled", true);
                }
             },
             doSubmit : function(){
            	 if (pbcBankCodeErrFlag) {
            		 return;
            	 }
                 var param = {};
                 param.orgCode = $("#SysInstRegBankCode002_orgCode").val();
                 param.registBankCode = $("#SysInstRegBankCode002_registBankCode").val();
                 param.pbcBankCode = $("#SysInstRegBankCode002_pbcBankCode").val();
                 param.isFinSubCompany = $("#SysInstRegBankCode002_isFinSubCompany").val();
                 param.insitituteCode = $("#SysInstRegBankCode002_insitituteCode").val();
                 param.belogCodeCBRC = $("#SysInstRegBankCode002_belogCodeCBRC").val();
                 param.pageModel = pageModel;
                 param.operateType = 'save';
                 Joyin.ajax("checkData", param, function(result) {
                     if (result && result.statuCode == "200"){
                         Joyin.confirm(result.data, function(){
                             Joyin.ajax("saveData", param, function(result) {
                                 SysInstRegBankCode002.close();
                             });
                         });
                     }
                 });
             },
             close : function(){
                 var index = parent.layer.getFrameIndex(window.name);
                 parent.layer.close(index);
             }
        };
}();
$(function() {
    SysInstRegBankCode002.init();
    $("#SysInstRegBankCode002_BTN_submit").on('click', SysInstRegBankCode002.doSubmit);
});

function checkPbcBankCode() {
	var pbcBankCode = $("#SysInstRegBankCode002_pbcBankCode").val().trim();
	if (pbcBankCode.length != 14) {
		$("#pbcBankCodeErrText").html("总行机构代码只能是14位的数字和英文组合！");
		pbcBankCodeErrFlag= true;
	} else {
		$("#pbcBankCodeErrText").html("");
		pbcBankCodeErrFlag= false;
	}
}

function checkinsitituteCode() {
	var insitituteCode = $("#SysInstRegBankCode002_insitituteCode").val().trim();
	if (insitituteCode.length != 18) {
		$("#insitituteCodeErrText").html("社会信用代码只能是18位的数字或大写英文组合！");
		pbcBankCodeErrFlag= true;
	} else {
		$("#insitituteCodeErrText").html("");
		pbcBankCodeErrFlag= false;
	}
}
