/**
 * 其他资产中债报送组件JS
 * @auth zhangnian
 */
(function(){

    var getElementById = function(id) {
        return $('#' + entityName_BussRepOtherInvestTargetInfo + '_' + id);
    };

    var  otherrepRateFlagChangeType=function(){
        var  otherrepRateFlagval=getElementById('rateFlag').val();
        if(otherrepRateFlagval=='02'){
            getElementById('assetYield').val('').trigger("change");
            getElementById('assetYield').attr('disabled',true);
        }else{
            getElementById('assetYield').attr('disabled',false);
        }
    }

    var  otherindustryFlagChangeType=function(){
        var  otherindustryFlag=getElementById('industryFlag').val();
        if(otherindustryFlag=='02'){
            getElementById('monitType').val('').trigger("change");
            getElementById('monitType').attr('disabled',true);

            getElementById('monTypeComm').val('').trigger("change");
            getElementById('monTypeComm').attr('disabled',true);
        }else{
            getElementById('monitType').attr('disabled',false);
            getElementById('monTypeComm').attr('disabled',false);
        }
    }

    function inAssetTypeChangeType(){
        var  inAssetType=getElementById('inAssetType').val();
        if(inAssetType=='99'){
            getElementById('inAssetTypeCmm').attr('disabled',false);
        }else{
            getElementById('inAssetTypeCmm').val('').trigger("change");
            getElementById('inAssetTypeCmm').attr('disabled',true);

        }
    }


    //担保方式事件
    function otherbyRepGuaType() {
        if(getElementById('guaType').val() == '01' || getElementById('guaType').val() == '02'||
            getElementById('guaType').val() == '05' || getElementById('guaType').val() == '99'){
            //抵质押物类型
            getElementById('pledgeType').val('').attr('disabled', true).trigger('change');
            getElementById('pledgeValue').val('').attr('disabled', true).trigger('change');
        }else{
            //抵质押物类型
            getElementById('pledgeType').attr('disabled', false).trigger('change');
            getElementById('pledgeValue').attr('disabled', false).trigger('change');
        }
        if(getElementById('guaType').val() == '03' || getElementById('guaType').val() == '04'||
            getElementById('guaType').val() == '05' || getElementById('guaType').val() == '99'){
            getElementById('natureGuarantee').val('').attr('disabled', true).trigger('change');
            getElementById('relationFinan').val('').attr('disabled', true).trigger('change');
        }else{
            getElementById('natureGuarantee').attr('disabled', false).trigger('change');
            getElementById('relationFinan').attr('disabled', false).trigger('change');
        }
        if(getElementById('guaType').val()==''){
            getElementById('guaTypeRemark').val('').attr('disabled', true).trigger('change');
        }else{
            getElementById('guaTypeRemark').attr('disabled', false);
        }

        var repAssetType = $('#assetType2').val();
        if(repAssetType =='2299' || repAssetType =='1599' || repAssetType=='1399'){
            getElementById('guaType').val('').attr('disabled', true);
        }else{
            getElementById('guaType').val('').attr('disabled', false);
        }
    }

    /**
     * 担保人与融资人关系
     */
    function otherRelationFinan() {
        var repAssetType = $('#assetType2').val();
        if(repAssetType != '1299' && repAssetType != ''){
            getElementById('relationFinan').val('').attr('disabled', true);
        }else{
            if(getElementById('guaType').val()==''){
                getElementById('relationFinan').val('').attr('disabled', true);
            }
        }
    }

    /**
     * 抵质押物价值（元）
     */
    function otherPledgeValue() {
        var repAssetType = $('#assetType2').val();
        if(repAssetType != '1299' && repAssetType != ''){
            getElementById('pledgeValue').attr('disabled', true);
        }else{
            if(getElementById('guaType').val()==''){
                getElementById('pledgeValue').attr('disabled', true);
            }
        }
    }

    /**
     * 担保性质
     */
    function otherNatureGuarantee() {
        var repAssetType = $('#assetType2').val();
        if(repAssetType != '1299' && repAssetType != ''){
            getElementById('natureGuarantee').val('').attr('disabled', true);
        }else{
            if(getElementById('guaType').val()==''){
                getElementById('natureGuarantee').val('').attr('disabled', true);
            }
        }
    }

    /**
     * 是否为债转股
     */
    function otherDeptEquity() {
        var repAssetType = $('#assetType2').val();
        if(repAssetType=='1299' || repAssetType=='2299' || repAssetType=='1599'){
            getElementById('deptEquity').val('').attr('disabled', true);
        }
    }

    $(function() {
        otherrepRateFlagChangeType();
        otherindustryFlagChangeType();
        otherbyRepGuaType();
        otherRelationFinan();
        otherPledgeValue();
        otherNatureGuarantee();
        otherDeptEquity();
        //是否有预期收益率
        getElementById('rateFlag').change(function(){
            otherrepRateFlagChangeType();
        });
        //行内资产类别说明
        getElementById('inAssetType').change(function(){
            inAssetTypeChangeType();
        });

        //项目是否属于重点监控行业和领域
        getElementById('industryFlag').change(function(){
            otherindustryFlagChangeType();
        });

        //担保方式事件 中债新增 20200407 end
        getElementById('guaType').change(function(){
            otherbyRepGuaType();
        });

        //担保人与融资人关系  20220621
        getElementById('relationFinan').change(function(){
            otherRelationFinan();
        });

        //抵质押物价值（元）  20220621
        getElementById('pledgeValue').change(function(){
            otherPledgeValue();
        });

        //担保性质  20220621
        getElementById('natureGuarantee').change(function(){
            otherNatureGuarantee();
        });

        //是否为债转股  20220621
        getElementById('deptEquity').change(function(){
            otherDeptEquity();
        });
    });

}());