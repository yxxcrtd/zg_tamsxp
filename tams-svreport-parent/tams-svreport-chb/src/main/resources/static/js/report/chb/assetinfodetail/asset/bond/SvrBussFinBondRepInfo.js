/**
 * 债券类资产中债报送组件JS
 * @auth zhangnian
 */
(function(){
    
    var getElementById = function(id) {
        return $('#' + entityName_BussFinBondRepInfo + '_' + id);
    };
    if(getElementById('finprodType2').val() == FINPROD_TYPE_TG_F18){
        getElementById('repPrivateprdFlag_div').show();
        getElementById('repBondtostockFlag_div').show();
        getElementById('project_other_div').show();
    }
    $(function() {
    	$("#"+issuerElement).change(function() {
    		if($('#'+issuerElement).val() != null && $('#'+issuerElement).val() != ''){
    			var params = {'issuer': $('#'+issuerElement).val()};
                Joyin.ajaxJson(JoyinUtil.getRealPath('/component/report/asset/bond/BussFinBondRepInfo/getInfoByIssuer'), params, function (data) {
                    getElementById("repPartyType3").val(data.repPartyType3).trigger("change");
                    if(data.repPartyType2 == PARTY_TYPE2_01 || data.repPartyType2 == PARTY_TYPE2_02
                    		|| data.repPartyType2 == PARTY_TYPE2_03){
                    	getElementById("repPartyType2").val(data.repPartyType2).trigger("change");
                    }
                    else{
                    	getElementById("repPartyType2").val(PARTY_TYPE2_99).trigger("change");
                    }
                    // 20200819 发行人联动处理优化：若市场机构未接入该信息，则从快录的信息中获取 wangwei start
                    // if(getElementById("repPartyType1").val()==null||getElementById("repPartyType1").val()==""){
                    //     getElementById("repPartyType1").val(data.repPartyType1).trigger("change");
                    // }
                    // getElementById("repIndustry").val(data.repIndustry).trigger("change");
                    // getElementById("repIndustryLevel2").val(data.repIndustryLevel2).trigger("change");
                    // 发行机构类型（按规模划分）需要进行参数映射处理
                    if(getElementById("repPartyType1").val()==null||getElementById("repPartyType1").val()==""){
                        if (data.repPartyType1 == '01' || data.repPartyType1 == '02' || data.repPartyType1 == '03'){
                            getElementById("repPartyType1").val("01").trigger("change");
                        }else if(data.repPartyType1 == '04'){
                            getElementById("repPartyType1").val("02").trigger("change");
                        }else{
                            getElementById("repPartyType1").val("99").trigger("change");
                        }
                    }
                    // wind提供的机构信息存在所属行业为空的情况，该场合快录时取快录的机构所属行业信息
                    if(getElementById("repIndustry").val()==null||getElementById("repIndustry").val()==""){
                        getElementById("repIndustry").val(data.repIndustry).trigger("change");
                    }
                    if(getElementById("repIndustryLevel2").val()==null||getElementById("repIndustryLevel2").val()==""){
                        getElementById("repIndustryLevel2").val(data.repIndustryLevel2).trigger("change");
                    }
                    // 20200819 发行人联动处理优化：若市场机构未接入该信息，则从快录的信息中获取 wangwei end
                });
    		}
    		else{
//    			getElementById("repPartyType3").val('').trigger("change");
//                getElementById("repPartyType2").val('').trigger("change");
//                if(getElementById("repPartyType1").val()==null||getElementById("repPartyType1").val()==""){
//                    getElementById("repPartyType1").val('').trigger("change");
//                }
//                getElementById("repIndustry").val('').trigger("change");
//                getElementById("repIndustryLevel2").val('').trigger("change");
    		}
    	});
        getElementById('repPartyType1').change(function(){
            if(getElementById('repPartyType1').val()==SVR_PARTY_TYPE1_01){
                getElementById('repInstitSize_div').show();
            }else{
                getElementById('repInstitSize_div').val('').trigger("change");
                getElementById('repInstitSize_div').hide();
            }
        });
        /*getElementById('repAssetType').change(function(){
            if(getElementById('finprodType2').val() == FINPROD_TYPE_TG_F18){
                if(getElementById('repAssetType').val()) {
                    getElementById('content_one_div').nextAll().show();
                    getElementById('repAssetType_div').nextAll().show();
                }else{
                    getElementById('content_one_div').nextAll().hide();
                    getElementById('repAssetType_div').nextAll().hide();
                }
            }
        });*/
        $('#svrBussFinBondRepInfoDto_chbIssuerId').change(function(){
            // $("#fundType").val('').trigger('change');
            var svrBussFinBondRepInfoDto_chbIssuerId = $('#svrBussFinBondRepInfoDto_chbIssuerId').val();
            if(svrBussFinBondRepInfoDto_chbIssuerId ==''|| svrBussFinBondRepInfoDto_chbIssuerId==undefined){
                // $('#fundName').val('');
            }
            else{
                var data = {
                    chbIssuerId: $('#svrBussFinBondRepInfoDto_chbIssuerId').val()
                }
                //$('#fundName').val($('#finprodId').select2('data')[0].text).trigger('change');
                Joyin.ajaxJson(basePath + 'common/select/getIssuerInfo', data,
                    function (resultObj) {
                        //if (resultObj.fundType) {
                        // $('#fundName').val(resultObj.fundName).trigger('change');
                        // $("#fundType").val(resultObj.fundType).trigger('change');
                        //}
                        if (resultObj.partyType1!=null){
                            $('#svrBussFinBondRepInfoDto_repPartyType1').val(resultObj.partyType1).trigger('change');
                        } else {
                            $('#svrBussFinBondRepInfoDto_repPartyType1').val('').trigger('change');
                        }
                        if (resultObj.partyType2!=null){
                            $('#svrBussFinBondRepInfoDto_repPartyType2').val(resultObj.partyType2).trigger('change');
                        } else {
                            $('#svrBussFinBondRepInfoDto_repPartyType2').val('').trigger('change');
                        }
                        if (resultObj.partyType3!=null){
                            $('#svrBussFinBondRepInfoDto_repPartyType3').val(resultObj.partyType3).trigger('change');
                        } else {
                            $('#svrBussFinBondRepInfoDto_repPartyType3').val('').trigger('change');
                        }
                        if (resultObj.industry!=null){
                            $('#svrBussFinBondRepInfoDto_repIndustry').val(resultObj.industry).trigger('change');
                        } else {
                            $('#svrBussFinBondRepInfoDto_repIndustry').val('').trigger('change');
                        }
                        if (resultObj.industry2!=null){
                            $('#svrBussFinBondRepInfoDto_repIndustryLevel2').val(resultObj.industry2).trigger('change');
                        } else {
                            $('#svrBussFinBondRepInfoDto_repIndustryLevel2').val('').trigger('change');
                        }

                    });
            }
        });
    })
}());