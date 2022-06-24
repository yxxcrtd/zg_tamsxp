(function() {
	var getSearchParams = function() {
		return {
			// 资产/负债类别
			assetType : $('#assetType').val() == null ? null : $(
				'#assetType').val().join(","),
			// 资产/负债二级类别
			assetType2 : $('#assetType2').val() == null ? null : $(
				'#assetType2').val().join(","),
			// 资产一级分类
			orgAssetType1 : $('#orgAssetType1').val() == null ? null : $(
				'#orgAssetType1').val().join(","),
			//资产二级分类
			orgAssetType2 : $('#orgAssetType2').val() == null ? null : $(
				'#orgAssetType2').val().join(","),
			// 行内资产编码
			assetCode : $('#assetCode').val() == null ? null : $(
				'#assetCode').val().join(","),
			//反馈状态
			dataStatus: $('#dataStatus').val()
		}
	};

	/**
	 * 定义数据表格
	 */
	var table = $('#dataTable').joyintable({
		ajax: {
			url: 'searchPage',
			type: 'POST',
			data: function() {
				return getSearchParams();
			}
		},
		//add by yjp ZGBSEI-188增加双击查看 20210811 start
		/**
		 * 双击查看
		 */
		dblclick: function (table, data) {
			Joyin.dialog(JoyinUtil.getLabel("FinStockInfo002.title"),
				basePath + "chb/assetinfodetail/AssetInfoDetail002/init",
				"99%", "99%", {
					pageModel: PAGE_MODEL_REFERENCE,
					assetUuid: data.assetUuid,
					dataStatus:data.dataStatus
				});
		},
		//add by yjp ZGBSEI-188增加双击查看 20210811 end
		rowno:false,
		cols:[
			{ field: 'assetUuid',width:0,sortField:'',visible:false},
			{ title: "数据状态", field: 'dataStatusStr',width:120 ,sortField:'dataStatus'},
			{ title: "资产代码", field: 'assetCode',width:180 ,sortField:'asset_code'},
			{ title: "资产名称", field: 'assetName',width:300 ,sortField:'asset_name'},
			{ title: "资产一级类型",field:'orgAssetType1Str', width:120,sortField:'org_asset_type1'},
			{ title: '资产二级分类', field:'orgAssetType2Str', width:120,sortField:'org_asset_type2'},
			{ title: "起息日",width:100,sortField:'vDateStr', field: 'vDateStr'},
			{ title: "到期日",width:100,sortField:'mDateStr', field: 'mDateStr'},
			{ title: '资产/负债一级类别', field:'assetTypeStr', width:180,sortField:'asset_type'},
			{ title: '资产/负债二级类别', field:'assetType2Str', width:130,sortField:'asset_type2'},
			{ title: '错误信息', field:'errMsg', width:130,sortable:false},
			{ field:'action', title: '操作' , type:'action',buttons: ['edit', 'delt'] , width:100}
		]
	});

	/**
	 * 绑定编辑事件
	 */
	table.bind('click', '.edit', function(data, index) {
		data.operateType = "edit";
		data.pageModel="edit";
		Joyin.dialog(JoyinUtil.getLabel("资产基本信息修改"),
			basePath + "chb/assetinfodetail/AssetInfoDetail002/init",
			"95%", "90%", {pageModel:PAGE_MODEL_EDIT,
				assetUuid:data.assetUuid,dataStatus:data.dataStatus},function(){table.reload();});
		// });
	});
	/**
	 * 绑定删除事件
	 */
	table.bind('click', '.delt', function(data, index) {
		var object = {selectedItem: [data],operateType:"delete"};
		Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
			WorkFlow.doDeleteList(basePath + 'chb/assetinfodetail/AssetInfoDetail001/delete', object, "AssetInfoDetail001_btn_delete", "AssetInfoDetail002",function(){table.reload();});
		});
	});

	$(function() {
		// 查询按钮点击事件
		$('#AssetInfoDetail001_btn_search').click(function() {
			table.load();
		});

		$('#AssetInfoDetail001_btn_add').click(function(){
			Joyin.dialog(JoyinUtil.getLabel("资产基本信息新增"),
				basePath + "chb/assetinfodetail/AssetInfoDetail002/init",
				"95%", "90%", {pageModel:PAGE_MODEL_NEW,},
				function(){table.reload();});
		});

		// 导入按钮
		$('#AssetInfoDetail001_btn_import').click(function() {
			Joyin.confirm("请确认是否要导入数据？", function() {
				var options = {};
				options.pageId = file;
				options.exts = 'zip|xls|xlsx';
				Joyin.uploadFile(options,function(fileName) {
					Joyin.ajax(basePath + 'chb/assetinfodetail/AssetInfoDetail001/checkConfirmMsgForImportData', {fileName: fileName,tableName:'CHB_ASSET_INFO'}, function(result) {
						if ($.isArray(result.joyinMessages) && result.joyinMessages.length > 0) {
							var joyinMessages = "";
							$.each(result.joyinMessages, function(i, joyinMessage){
								joyinMessages += joyinMessage.message + "<br />"
							});
							Joyin.confirm(joyinMessages, function(){
								Joyin.ajax(basePath + 'chb/assetinfodetail/AssetInfoDetail001/importFile', {fileName: fileName,tableName:'CHB_ASSET_INFO'},function () {
									table.reload();
								});
							});
						}else{
							Joyin.ajax(basePath + 'chb/assetinfodetail/AssetInfoDetail001/importFile', {fileName: fileName,tableName:'CHB_ASSET_INFO'},function () {
								table.reload();
							});
						}
					});
				},function() {
					table.reload();
				});
			});
		});

		// 导出按钮
		$('#AssetInfoDetail001_btn_export').click(function(){
			var paramObj = getSearchParams();
			Joyin.exportExcel('export', paramObj);
		})

	});
}());


