$(function(){
    /**
     * 定义数据表格
     */
    var tableA=$("#dataTable").joyintable({
        ajax : {
            url : JoyinUtil.getRealPath('chb/assettypesetting/AssetTypeSetting002/findExPage'),
            type : 'POST',
            data : function() {
                return {
                    typeuuid: $('#typeuuid').val()
                };
            }
        },
        loadonce:true,
        page:true,
        checkbox : false,
        height:'225px',
        searchOnInit : true,
        rowno:true,
        cols:[
             {field:'chbAssetType', width:400, title: '资产/负债类别',sortable:false, edit:true, editOption: {id: 'chbAssetTypeSelect',type:'select',result: SVR_ASSETDEBT_TYPE,childrenIds:'chbAssetType2Select'}},
             {field:'chbAssetType2', width:400, title: '资产/负债二级类别',sortable:false, edit:true, editOption: {id: 'chbAssetType2Select',type:'select',result: SVR_ASSET_TYPE_SECOND_LEVEL,parentIds:'chbAssetTypeSelect'}}
            ,{field:'operation', title: 'SysCommon.operation',sortable:false, type:'action', buttons: ['delt']}
        ],
        addbtn:true
    });

    /**
     * 表格绑定删除事件
     */
    tableA.bind('click', '.delt', function(data, index) {
        tableA.removeRow(data);
    });

    /**
     * 进行保存和提交操作
     * @param operateType 操作类型
     * @returns
     */
    function saveOrSubmit(operateType) {
        var object = $.serializeToObject('#submitForm', true);
        var selectItem = tableA.getAllRows();
        object.selectedItem1 = selectItem;
        object.operateType = operateType;
        object.pageModel = pageModel;
        Joyin.ajaxJson(basePath + '/chb/assettypesetting/AssetTypeSetting002/check', object, function(result) {
            if(operateType === 'save'){
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.save")), function() {
                    WorkFlow.doSaveList(basePath + '/chb/assettypesetting/AssetTypeSetting002/save', object, "AssetTypeSetting002_btn_save");
                });
            }else{
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.submit")), function() {
                    WorkFlow.doSubmitList(basePath + '/chb/assettypesetting/AssetTypeSetting002/save', object, "AssetTypeSetting002_btn_submit");
                });
            }
        });
    }

    /**
     * 初始化
     */
    var init = function(){
        $('#AssetTypeSetting002_btn_save').hide()
        $('#AssetTypeSetting002_btn_othersave').hide()
        if(pageModel==PAGE_MODEL_EDIT){
            $('#assetType1').prop("disabled",true);
            $('#assetType2').prop("disabled",true);
            $('#assetType3').prop("disabled",true);
        }
    };

    /**
     * 画面加载完成
     */
    $(function() {
        // 绑定按钮事件
        init();
        $('#AssetTypeSetting002_btn_save').click(function() {
            saveOrSubmit('save');
        });
        $('#AssetTypeSetting002_btn_submit').click(function() {
            saveOrSubmit('submit');
        });
    });
});
