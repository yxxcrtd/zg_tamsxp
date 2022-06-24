/**
 * 分红计划组件
 */
(function(){
	/**
     * 画星星
     */
    var starBussRep = function() {
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
        if (typeof pageModel !== 'undefined'
                && (pageModel === PAGE_MODEL_EDIT || pageModel === PAGE_MODEL_NEW)) {
            $('.must').each(function() {
                $(this).remove();
            })
            $('*[required]').each(function() {
                var $el = $(this);
                var $target = _findPrevLabel($el, 3);
                if ($target.find('> .must').length === 0) {
                    $target.append($('<i class="must">*</i>'));
                }
            });
        }
    }
	
	var getElementById = function(id) {
		return $('#' + BussRepFundInfoentityName + '_' + id);
	};

    var  isGovernFundChangeType=function(){
		if(getElementById('isGovernFund').val()=='02'){
			getElementById('governInvestFund').val('').trigger("change");
			getElementById('governInvestFund').attr('disabled', true)
		}else{
			getElementById('governInvestFund').attr('disabled', false);
		}
	};

    var  initFundAssetType2=function(){
		var assetTvalueFund=$('#assetType2').val();
		//公募基金/私募基金
		if(assetTvalueFund=='2607'||assetTvalueFund=='2610'||assetTvalueFund=='2613'||
			assetTvalueFund=='2611'||assetTvalueFund=='2612'||assetTvalueFund=='2617'){
			$("#svrBussRepFundInfoDto_isGovernFund").val('').trigger("change");
			$("#svrBussRepFundInfoDto_isGovernFund").attr("disabled",false);
		}else{
			$("#svrBussRepFundInfoDto_isGovernFund").val('').trigger("change");
			$("#svrBussRepFundInfoDto_isGovernFund").attr("disabled",true);
		}
	}

	var  initprofitFlag=function(){
		var assetTvalueFund=$('#assetType2').val();
		//公募基金/私募基金
		if(assetTvalueFund=='1106'||assetTvalueFund=='2401'||assetTvalueFund=='1303'||
			assetTvalueFund=='2403'||assetTvalueFund=='2402'||assetTvalueFund=='2499'){
			$("#svrBussRepFundInfoDto_profitFlag").val('').trigger("change");
			$("#svrBussRepFundInfoDto_profitFlag").attr("disabled",false);
		}else{
			$("#svrBussRepFundInfoDto_profitFlag").val('').trigger("change");
			$("#svrBussRepFundInfoDto_profitFlag").attr("disabled",true);
		}
	}


	$(function() {

		isGovernFundChangeType();
		initprofitFlag();
		initFundAssetType2();
		starBussRep();
		getElementById('isGovernFund').change(function(){
			isGovernFundChangeType();
		});

		// 管理人
		$('#svrBussRepFundInfoDto_chbManagerId').change(function(){
			// $("#fundType").val('').trigger('change');
			var svrBussRepFundInfoDto_chbManagerId = $('#svrBussRepFundInfoDto_chbManagerId').val();
			if(svrBussRepFundInfoDto_chbManagerId ==''|| svrBussRepFundInfoDto_chbManagerId==undefined){
				// $('#fundName').val('');
			}
			else{
				var data = {
					chbManagerId: $('#svrBussRepFundInfoDto_chbManagerId').val()
				}
				//$('#fundName').val($('#finprodId').select2('data')[0].text).trigger('change');
				Joyin.ajaxJson(basePath + 'common/select/getManagerInfo', data,
					function (resultObj) {
						//if (resultObj.fundType) {
						// $('#fundName').val(resultObj.fundName).trigger('change');
						// $("#fundType").val(resultObj.fundType).trigger('change');

						//}
						// console.log($('#svrBussRepFundInfoDto_chbManagerId').val());
						// console.log(resultObj);
						if (resultObj.partyType1!=null){
							$('#svrBussRepFundInfoDto_sharesType1').val(resultObj.partyType1).trigger('change');
						} else {
							$('#svrBussRepFundInfoDto_sharesType1').val('').trigger('change');
						}
						if (resultObj.partyType2!=null){
							$('#svrBussRepFundInfoDto_sharesType2').val(resultObj.partyType2).trigger('change');
						} else {
							$('#svrBussRepFundInfoDto_sharesType2').val('').trigger('change');
						}
						if (resultObj.partyType3!=null){
							$('#svrBussRepFundInfoDto_sharesType3').val(resultObj.partyType3).trigger('change');
						} else {
							$('#svrBussRepFundInfoDto_sharesType3').val('').trigger('change');
						}

					});
			}
		});

	});

}());