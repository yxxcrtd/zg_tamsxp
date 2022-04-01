(function() {
	var assetType;
	var assetTypeflag=true;
	var init=function() {
//		setTimeout(function() {
//        }, 500);
        if(pageModel!=PAGE_MODEL_NEW){
            $("#assetCode").attr('disabled',true);
        }
        if(pageModel == PAGE_MODEL_EDIT){
        	//贷款种类change事件
    		loansTypeChangeAction();
        	//行业信息change事件
    		industryInfoChangeAction();
        	//借款人类型change事件
    		borrowerTypeChangeAction();
        	//贷款状态change事件
    		loanStateChangeAction();
    		//资产代码change事件
    		assetCodeChangeAction();
        }
        $("#selectedColumns").html("");
//        if(filterId){
//        	setData(filterId);
//        }
		
    };
    
    /**
     * 保存或提交
     * @param operateType 操作类型（save，submit）
     * @returns
     */
    function saveOrSubmit(operateType) {
    	var object = $.serializeToObject('#submitForm');
    	// 基础资产投向分类
        if (object.assetsInvest) {
            object.assetsInvest = object.assetsInvest.join(';');
        }

        object.operateType = operateType;
        object.pageModel = pageModel;
        //check一下比例         start
        if(assetTypeflag){
        	 var eachProportion=object.eachProportion;
             var assetsInvest=object.assetsInvest;
             if(eachProportion !== null){
             var arr=eachProportion.split(':');
             //基础资产投向分类修复 zc add by start
             if(assetsInvest == null){
				 Joyin.alert("基础资产投向分类不能为空！",7);
			 }else {
				 var assetarr=assetsInvest.split(';');
			 }
             //基础资产投向分类修复 zc add by start
             //此时应该包含比例符号
             if(assetarr.length>1){
             	if(eachProportion.indexOf(':')<0){
             		//则格式不正确
               	  Joyin.alert("各比例字段格式不正确或比例之和不等于100！",7);
       			   return;        	
             	}
             }
             //各比例输入个数跟基础资产投向分类选择的不一致，请确认！
             if(arr.length !==assetarr.length){
             	 Joyin.alert("各比例输入个数跟基础资产投向分类选择的不一致，请确认！",7);
     			   return;  
             }
             	//判断是否为数字
             	for(var i=0;i<arr.length;i++){
             		if(isNaN(arr[i])){
             			Joyin.alert("各比例字段格式不正确或比例之和不等于100！!",7);
             			 return;
             		}
             	}
             	var  sum=0;
             	for(var i=0;i<arr.length;i++){
             		sum=Number(arr[i])+Number(sum);
             	}
             	if(sum !== 100){
             		 Joyin.alert("各比例字段格式不正确或比例之和不等于100！",7);
             		 return;
             	}
             
             }
        }
        //check一下比例         end
        Joyin.ajaxJson("check",object,function(result){
            if(result){
                Joyin.ajaxJson("doSave",object,function(result){
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            }
        })
    }
    
    /**
     * 贷款种类change事件
     */
    var loansTypeChangeAction = function(){
    	var loansType = $('#loansType').val();
    	//1 当“贷款种类”选择“转让贷款”时,贷款转让方信息、贷款转让折扣率（%）   必填
    	if(loansType === SVR_LOAN_TYPE_2){//贷款种类_转让贷款
        	$('#loanTransferorId').prop('disabled',false);
        	$('#loanTransferorName').prop('disabled',false);
        	$('#loanContractOrgId').prop('disabled',false);
        	$('#loanContractOrgName').prop('disabled',false);
        	$('#loanContractOrgProvince').prop('disabled',false);
        	$('#loanContractOrgCity').prop('disabled',false);
        	$('#loanContractOrgCounty').prop('disabled',false);
        	$('#loanTransferorRate').prop('disabled',false);
        }else{
        	$('#loanTransferorId').val('').trigger('change').attr('disabled',true);
        	$('#loanTransferorName').val('').trigger('change').attr('disabled',true);
        	$('#loanContractOrgId').val('').trigger('change').attr('disabled',true);
        	$('#loanContractOrgName').val('').trigger('change').attr('disabled',true);
        	$('#loanContractOrgProvince').val('').trigger('change').attr('disabled',true);
        	$('#loanContractOrgCity').val('').trigger('change').attr('disabled',true);
        	$('#loanContractOrgCounty').val('').trigger('change').attr('disabled',true);
        	$('#loanTransferorRate').val('').trigger('change').attr('disabled',true);
        }
    	
    	//2   当贷款种类为“其他债权”时,贷款借据编码、贷款产品类别、贷款质量、贷款状态   置灰不可填
    	if(loansType === SVR_LOAN_TYPE_3){//贷款种类其他债权收益权
    		$('#loanBasisId').val('').trigger('change').attr('disabled',true);
    		$('#loanPrdType').val(SVR_LOAN_PEOD_TYPE_F99).trigger("change").prop('disabled', true);
    		$('#loanQuality').val(SVR_LOAN_PEOD_TYPE_F99).trigger("change").prop('disabled', true);
    		$('#loanState').val(SVR_LOAN_PEOD_TYPE_F99).trigger("change").prop('disabled', true);
        }else{
        	$('#loanBasisId').prop('disabled',false);
        	$('#loanPrdType').prop('disabled',false);
        	$('#loanQuality').prop('disabled',false);
        	$('#loanState').prop('disabled',false);
        }
    }
    
    /**
     * 行业信息change事件
     */
    var industryInfoChangeAction = function(){
    	var industryInfo = $('#industryInfo').val();
    	//1 当行业信息为“境外”时，借款人国别 必填，
    	if(industryInfo === SVR_INDUSTRY_DOOR_CODE_2){//行业门内编码_非居民
        	$('#borrowerNationality').prop('disabled',false);
//        	$('#borrowerProvince').val('').trigger('change').attr('disabled',true);
//        	$('#borrowerCity').val('').trigger('change').attr('disabled',true);
//        	$('#borrowerCounty').val('').trigger('change').attr('disabled',true);
        }else{
        	$('#borrowerNationality').val('').trigger('change').attr('disabled',true);
//        	$('#borrowerProvince').prop('disabled',false);
//        	$('#borrowerCity').prop('disabled',false);
//        	$('#borrowerCounty').prop('disabled',false);
        }
    	//modify by yanya on 20210526
		if(industryInfo === SVR_INDUSTRY_DOOR_CODE_2 || industryInfo === SVR_INDUSTRY_DOOR_CODE_1 ) {//行业门内编码_非居民 或者 行业门内编码_境内个人
			$('#borrowerProvince').val('').trigger('change').attr('disabled',true);//借款人地区(省)必须为空
			$('#borrowerCity').val('').trigger('change').attr('disabled',true);//借款人地区(市)必须为空
			$('#borrowerCounty').val('').trigger('change').attr('disabled',true);//借款人地区(县)必须为空
		}
		else {
			$('#borrowerProvince').val('').trigger('change').attr('disabled',false);//借款人地区(省)
			$('#borrowerCity').val('').trigger('change').attr('disabled',false);//借款人地区(市)
			$('#borrowerCounty').val('').trigger('change').attr('disabled',false);//借款人地区(县)
		}
    }
    
    /**
     * 借款人类型change事件
     */
    var borrowerTypeChangeAction = function(){
    	var borrowerType = $('#borrowerType').val();
//    	//1   当借款人类型不为“住户”或者行业信息不为“境外”时，借款人地区(省市县) 必填
//    	if(borrowerType === SVR_DEPT_TYPE_1){//借款人类型_住户
//        	$('#borrowerProvince').val('').trigger('change').attr('disabled',true);
//        	$('#borrowerCity').val('').trigger('change').attr('disabled',true);
//        	$('#borrowerCounty').val('').trigger('change').attr('disabled',true);
//        }else{
//        	$('#borrowerProvince').prop('disabled',false);
//        	$('#borrowerCity').prop('disabled',false);
//        	$('#borrowerCounty').prop('disabled',false);
//        }
    	
    	//2    借款人类型为“1-住户、2-广义政府、5-特定目的载体、6-境外”时,企业出资人经济成分(一二三级) 置灰不可填
    	if(borrowerType === SVR_DEPT_TYPE_1 || borrowerType === SVR_DEPT_TYPE_2
    			|| borrowerType === SVR_DEPT_TYPE_5 || borrowerType === SVR_DEPT_TYPE_6){
        	$('#enterpriseInvestorTypeFirst').val('').trigger('change').attr('disabled',true);
        	$('#enterpriseInvestorTypeSec').val('').trigger('change').attr('disabled',true);
        	$('#enterpriseInvestorTypeThird').val('').trigger('change').attr('disabled',true);
        }else{
        	$('#enterpriseInvestorTypeFirst').prop('disabled',false);
        	$('#enterpriseInvestorTypeSec').prop('disabled',false);
        	$('#enterpriseInvestorTypeThird').prop('disabled',false);
        }
    	
    	//3    当借款人类型为“1-住户、6-境外”时,企业规模   置灰不可填
    	if(borrowerType === SVR_DEPT_TYPE_1 || borrowerType === SVR_DEPT_TYPE_6){
        	$('#enterpriseScale').val('').trigger('change').attr('disabled',true);
        }else{
        	$('#enterpriseScale').prop('disabled',false);
        }
    	
    	// 当资产类别 =“除回购和拆借外贷款”时，
    	// 1、借款人类型为“1-住户”时，行业信息联动带出“1-境内个人”，且置灰不允许修改；实际上，行业信息未联动带出且没有置灰。
    	// 2、借款人类型为“6-境外”时，行业信息联动带出“2-非居民”，且置灰不允许修改；实际上，行业信息未联动带出且没有置灰。
    	// 3、当借款人类型为“1-住户、6-境外”时，借款人地区（省）、借款人地区（市）、借款人地区（县）置灰不可填；实际未置灰可填。
    	if($('#assetType').val() && '08' === $('#assetType').val()){
    		if(borrowerType === SVR_DEPT_TYPE_1){
    			$('#industryInfo').val('1').trigger('change').attr('disabled',true);
    		}else if(borrowerType === SVR_DEPT_TYPE_6){
    			$('#industryInfo').val('2').trigger('change').attr('disabled',true);
    		}else{
    			$('#industryInfo').prop('disabled',false);
    		}
    		if(borrowerType === SVR_DEPT_TYPE_1 || borrowerType === SVR_DEPT_TYPE_6){
    			$('#borrowerProvince').val('').trigger('change').attr('disabled',true);
    			$('#borrowerCity').val('').trigger('change').attr('disabled',true);
    			$('#borrowerCounty').val('').trigger('change').attr('disabled',true);
    		}else{
    			$('#borrowerProvince').prop('disabled',false);
    			$('#borrowerCity').prop('disabled',false);
    			$('#borrowerCounty').prop('disabled',false);
    		}
    	}else{
    		$('#industryInfo').prop('disabled',false);
    		$('#borrowerProvince').prop('disabled',false);
			$('#borrowerCity').prop('disabled',false);
			$('#borrowerCounty').prop('disabled',false);
    	}
    }
    
    /**
     * 贷款状态change事件
     */
    var loanStateChangeAction = function(){
    	var loanState = $('#loanState').val();
    	//若“贷款状态”为“正常”，贷款展期到期日期   置灰不可填
    	if(loanState === SVR_LOAN_STATUS_FS01){//贷款状态_正常
    		$('#loanExtensionDueDate').val('').trigger('change').attr('disabled',true);
        }else{
        	$('#loanExtensionDueDate').prop('disabled',false);
        }
    }
    
    /**
	 * 贷款展期到期日期联动事件
	 */
	var loanExtensionDueDateChangeAction = function(){
		var loanMdate = $('#loanMdate').val();
		var loanExtensionDueDate = $('#loanExtensionDueDate').val();
		var loanState = $('#loanState').val();
		if (loanMdate && loanExtensionDueDate && loanState === SVR_LOAN_STATUS_FS03){
			var days = (Date.ofDate(mdate) - Date.ofDate(vdate))/(24*60*60*1000);
			if(days<=0){
				Joyin.error(JoyinUtil.getMessage("REPORT_COMMON_E_0059",JoyinUtil.getLabel("OperationalLoanInfo002.loanExtensionDueDate"),
						JoyinUtil.getLabel("OperationalLoanInfo002.loanMdate")));
				$('#mdate').val('');	
				return;
			}
		}
	}
	
	/**
     * 资产代码change事件
     */
    var assetCodeChangeAction = function(){
    	var assetCode = $('#assetCode').val();
    	if(assetCode){
    		Joyin.ajax('queryPbcAssetType', {assetCode : assetCode}, function(result) {
    			assetType = result;
				var syqDiv = document.getElementById('syqDiv');
				var loannDiv =document.getElementById("loannDiv");

				var syqFlag = true;
				if (assetType === 'Q01'){
					$('#assetType').val("01").trigger('change');
				}else if (assetType === 'Q02'){
					$('#assetType').val("02").trigger('change');
				}else if (assetType === 'Q03'){
					$('#assetType').val("03").trigger('change');
				}else if (assetType === 'Q04'){
					$('#assetType').val("04").trigger('change');
				}else if (assetType === 'Q05'){
					$('#assetType').val("05").trigger('change');
				}else if (assetType === 'Q06'){
					$('#assetType').val("06").trigger('change');
				}else if (assetType === 'Q07'){
					$('#assetType').val("07").trigger('change');
				}else{
					$('#assetType').val("08").trigger('change');
					syqFlag = false;
					assetTypeflag = false;
				}
				if (syqFlag){
					syqDiv.style.display="block";
					loannDiv.style.display="none";
				}else{
					syqDiv.style.display="none";
					loannDiv.style.display="block";
				}

    	    	// if(assetType === 'P06'){
    	    	// 	$('#assetType').val("01").trigger('change');
				//
				//
				// 	syqDiv.style.display="block";
				// 	loannDiv.style.display="none";
    	    	// }else{
				// 	$('#assetType').val("02").trigger('change');
				//
				//
				// 	syqDiv.style.display="none";
				// 	loannDiv.style.display="block";
    	    	// }
    		});
    	}
    }
    
    $(function() {
    	init();
    	
    	//资产代码change事件
    	$('#assetCode').change(function(){
    		assetCodeChangeAction();
    	});
    	
    	//贷款种类change事件
    	$('#loansType').change(function(){
    		loansTypeChangeAction();
    	});

    	//行业信息change事件
    	$('#industryInfo').change(function(){
    		industryInfoChangeAction();
    	});
    	
    	//借款人类型change事件
    	$('#borrowerType').change(function(){
    		borrowerTypeChangeAction();
    	});
    	
    	//贷款状态change事件
    	$('#loanState').change(function(){
    		loanStateChangeAction();
    		loanExtensionDueDateChangeAction();
    	});
    	
    	// 贷款展期到期日期联动事件
		$('#loanMdate').change(function(){
			loanExtensionDueDateChangeAction();
		});
		$('#loanExtensionDueDate').change(function(){
			loanExtensionDueDateChangeAction();
		});
    	
        // 按钮事件
        $('#OperationalLoanInfo002_btn_submit').click(function() {
            saveOrSubmit('submit');
        });
    });
}());
