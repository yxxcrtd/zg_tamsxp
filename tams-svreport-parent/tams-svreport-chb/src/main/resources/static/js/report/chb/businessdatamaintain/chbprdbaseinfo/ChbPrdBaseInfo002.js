(function() {
    var prodIdInit = $('#prodId').val();
    var prodNo = '';
    var flag=false;
    if(''==prodIdInit||null==prodIdInit){
        //新建页面
        $('#issuePartyId').val(issuePartyId_New);
        $('#prodId').blur(function (){
            if($('#prodInsId').val()=='' && this.value!=''){
                layer.msg('请先录入对应的行内标识码，以指定申报信息。')
            }/*else if($('#regCode').val()!=''&&$('#prodId').val()!=prodNo){
                layer.msg('如果想修改申报时候的行内标识码，请同步维护产品登记编码管理信息！')
            }*/
        });

    }else{
        //修改页面
        $("#prodId").attr("disabled","disabled");
        $("#prodName").attr("disabled","disabled");
        $("#issuePartyId").attr("disabled","disabled");
        $('#ivstorientation_div').show();
        $('#operateMode_div').show();
    }
    var init = 0;
    var initProdInsId = '';
    $('#prodInsId').change(function(){

        var prodInsId = $('#prodInsId').val();
        if(init == 0){
            initProdInsId = prodInsId;
        }

        if(init>0){
            if(''!=prodInsId && null !=prodInsId){
                // 查询产品申报信息
                Joyin.ajaxJson(basePath+'/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002/selectChbPrdDeclareByProdInsId?prodInsId='+prodInsId,{},function (result){
                    $('#ivstorientation').val(result.ivstorientation).trigger('change');
                    $('#operateMode').val(result.operateMode).trigger('change');
                    $('#ivstorientation_div').show();
                    $('#operateMode_div').show();

                })
                //查询产品登记信息
                Joyin.ajaxJson(basePath+'/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002/selectRegCodeByProdId?prodId='+prodInsId,{},function (result){
                    if(result['REG_CODE']!='' && result['REG_CODE']!=null){
                        $('#regCode').val(result['REG_CODE']);
                        $('#prodName').val(result['PRODNAME']);
                        $('#prodId').val(result['PRODID']);
                        prodNo = result['PRODID'];
                    }else{
                        prodNo = '';
                        $('#regCode').val('');
                        $('#prodName').val('');
                        $('#prodId').val('');
                        layer.msg('请先维护产品登记编码信息！')
                    }
                })
                if(initProdInsId != prodInsId){
                    // 校验产品是否存在
                    Joyin.ajaxJson(basePath+'/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002/selectByKey?prodId='+prodInsId,{},function (result){
                        if(result !=null &&  result[0]!=null){
                            if(result[0]['STATUS']=='D'){
                                Joyin.confirm('存在已删除的产品基本信息，是否覆盖？',function (){
                                    flag = false;
                                },function (){},function () {
                                    flag = true;
                                })
                            }else{
                                flag = true;
                                layer.msg('产品已存在、如需修改，请已修改模式进行信息修改！');
                            }
                        }else{
                            flag = false;
                        }
                    })
                }else{
                    flag = false;
                }
            }else{
                prodNo = '';
                $('#regCode').val('');
                $('#prodId').val('');
                $('#prodName').val('');
                $('#ivstorientation').val('');
                $('#ivstorientation_div').hide();
                $('#operateMode_div').hide();

            }
        }
        init+=1;
    });
    $("#operateMode").change(function (){
        var operateMode = $('#operateMode').val();
        if(operateMode=='01'||operateMode=='02'){
            $('#openMode').val('').trigger('change').attr('disabled','disabled');
            $('#openCircle').val('').trigger('change').attr('disabled','disabled');
        }else{
            $('#openMode').removeAttr('disabled');
            $('#openCircle').removeAttr('disabled');
        }
    })
    $('#isConstracte').change(function (){
        var isConstracte = $('#isConstracte').val();
        if(isConstracte=='02'){
            $('#gradProportion').val('').attr('disabled','disabled');

        }else{
            $('#gradProportion').removeAttr('disabled');
        }

    });
    $('#openMode').change(function (){
        var openMode = $('#openMode').val();
        if(openMode=='02'){
            $('#openCircle').val('').trigger('change').attr('disabled','disabled');
        }else{
            $('#openCircle').removeAttr('disabled');
        }

    });
    $('#openCircle').change(function (){
        var openCircle = $('#openCircle').val();
        if(openCircle!='99'){
            $('#openCircleDays').val('').attr('disabled','disabled');
        }else{
            $('#openCircleDays').removeAttr('disabled');
        }
    });
    $('#openBuss').change(function (){
        var openBuss = $('#openBuss').val();
        if(openBuss!='99'){
            $('#openBussRemark').val('').attr('disabled','disabled');
        }else{
            $('#openBussRemark').removeAttr('disabled');
        }
    });
    function saveOrSubmit(operateType) {
        var object = $.serializeToObject('#submitForm');
        object.operateType = operateType;
        object.pageModel = pageModel;
        if($('#prodInsId').val()==''||$('#prodInsId')==null){
            layer.msg('请先录入对应的行内标识码，以指定申报信息。')
            return;
        }
        if($('#regCode').val()==''||$('#regCode').val()==null){
            layer.msg('请先维护产品登记编码信息！')
            return;
        }
        if(flag){
            layer.msg('产品已存在、如需修改，请已修改模式进行信息修改！');
            return;
        }
        Joyin.ajaxJson(basePath + '/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002/check', object, function(result) {
           //提交工作流
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.submit")), function() {
                    WorkFlow.doSubmitList(basePath + '/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002/save', object, "CchbPrdBaseInfo002_btn_submit");
            });
        });
    };

    /**
     * 画面加载完成
     */
    $(function () {
        if(pageModel === PAGE_MODEL_EDIT){
            $('#btnSave_wfl').hide();
        }
        // 绑定按钮事件
        $('#CchbPrdBaseInfo002_btn_submit').click(function() {
            saveOrSubmit("submit");
        });
        
    });
}());
