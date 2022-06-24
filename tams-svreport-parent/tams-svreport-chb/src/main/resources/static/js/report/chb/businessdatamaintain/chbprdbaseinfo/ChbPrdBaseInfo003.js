(function(){
	
	/**
     * 定义数据表格
     */
    var table=$("#CchbPrdBaseInfo003_dataTable").joyintable({
    	ajax: {
            url: JoyinUtil.getRealPath('chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo003/queryForList'),
            type: 'POST',
            data: function() {
                return {prodIdStr : prodIdStr};
            }
        },
        searchOnInit : true,
        page: false,
        loadonce: true,
        rowno: true,
        cols:[
			{field:'prodId', sortable:false, width:100, title: 'CchbPrdBaseInfo001.prodId'}
			,{field:'prodName',sortable:false,width:100,  title: 'CchbPrdBaseInfo001.prodName'}
			,{field:'issuingStatusStr', sortable:false, width:100,title: 'CchbPrdBaseInfo001.issuingStatus'}
			,{field:'issuingStatusNew', title: 'CchbPrdBaseInfo001.issuingStatusNew', width:100, edit : true, editOption:{type:'select',result: SVR_ISSUING_STATUS}}
			,{field:'issueStatusRemark', title: 'CchbPrdBaseInfo001.issueStatusRemark', width:200, edit : true}
        ]
    });

    /**
	 * 提交按钮执行事件
	 */
	var onSaveOrSubmitAction = function(operateType){
		var object = $.serializeToObject('#issueStatusForm', true);
        object.operateType = operateType;
        object.pageModel = pageModel;
        Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo003/check',object, function(result) {
            Joyin.confirm(result.data, function() {
                Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo003/updateIssuingStatus', object, function (result){
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            });
        })
	}

	$(function() {
        // 提交按钮执行事件
        $('#CchbPrdBaseInfo003_btn_submit').click(function(){
        	onSaveOrSubmitAction('submit');
        })
	});
}());