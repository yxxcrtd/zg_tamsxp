$(function(){
	// 隐藏过滤按钮
	$("#SvrOperationalInfo002_btn_filter").hide();
	// 隐藏导出按钮
	$("#SvrOperationalInfo002_btn_export").hide();

	$(".btn-select-wrapper").css("display","none")
	// 更改数据状态重新查下结果
	function showTable() {
		SvrOperationalInfo002_table001.load();
	}
	var getPageParams = function() {
		var dataStatus = $('#status').val();
		var tableName = $("#SvrOperationalInfo002_tableName").val();
		var dataObject = $("#SvrOperationalInfo002_dataObject").val();
    	if (dataStatus) {
    		dataStatus = dataStatus.join();
    	} 
        return {
        	dataStatus : dataStatus,
        	tableName : tableName,
        	dataObject : dataObject,
        };
    };
    
	var SvrOperationalInfo002_table001 = $('#dataTable').joyintable({
        ajax: {
            url: basePath + "common/infomanagement/SvrOperationalInfo002/searchPage",
            type: 'POST',
            async: false,
            data: function() {
                return getPageParams();
            }
        },
        cols: [
            { title: "待维护表主键", field:'mgrTableId', visible: false},
            { title: "数据源表名", field:'mgrTableName'},
            { title: "数据源表中文名", field:'mgrTableChiName'},
            { title: "接口名称", field:'dataObject'},
            { title: "数据状态", field:'status'},
            { field:'operation', title: '操作', type:'action', buttons: ['edit', 'delt']}
        ]
    });
//	SvrOperationalInfo002_table001.load();
	// 新建按钮事件
	$("#SvrOperationalInfo002_btn_add").click(function() {
		Joyin.dialog(JoyinUtil.getLabel("数据源表新建"),
                basePath + "common/infomanagement/SvrOperationalInfo003/init",
                "40%", "50%", {pageModel:PAGE_MODEL_NEW},function(){
                	SvrOperationalInfo002_table001.reload();
                });
		}
	);
	
	// 行内编辑按钮点击事件
	SvrOperationalInfo002_table001.bind('click', '.edit', function(row) {
		var paramObj = {
        		mgrTableId : row.mgrTableId,
                pageModel : PAGE_MODEL_EDIT,
                mgrTableName : row.mgrTableName,
                mgrTableChiName : row.mgrTableChiName,
                dataObject : row.dataObject == undefined ? '' : row.dataObject
        }
		var status = row.status;
		if (status == '删除') {
			Joyin.confirm("所选数据为删除状态，是否修改为有效状态？", function() {
				Joyin.ajaxSync('updateTableStatus', {
					mgrTableId : row.mgrTableId,
					status : 'E'
				}, function(result) {
					if (result > 0) {
						layer.confirm('修改成功！', {
							btn: ['确定']
						},function(index) {
							layer.close();
							eidtData(paramObj);
							layer.close(index);
						});
					} else {
						layer.alert('修改失败！', {
							icon : 2,
							skin : 'layer-ext-moon'
						});
					}
				}
				);
			});
		} else {
			eidtData(paramObj);
		}
        
    });
	
	// 行内删除按钮点击事件
	SvrOperationalInfo002_table001.bind('click', '.delt', function(row) {
		var paramObj = {
        		mgrTableId : row.mgrTableId,
                pageModel : PAGE_MODEL_EDIT,
                mgrTableName : row.mgrTableName,
                mgrTableChiName : row.mgrTableChiName,
                dataObject : row.dataObject
        }
		var status = row.status;
		if (status == '删除') {
			layer.alert('该数已经为删除状态！', {
				icon : 2,
				skin : 'layer-ext-moon'
			});
		} else {
			Joyin.confirm("是否删除该条数据？", function() {
				Joyin.ajaxSync('updateTableStatus', {
					mgrTableId : row.mgrTableId,
					status : 'D'
				}, function(result) {
					if (result > 0) {
						layer.confirm('删除成功！', {
							btn: ['确定']
						},function(index) {
							layer.close();
							SvrOperationalInfo002_table001.reload();
							layer.close(index);
						});
					} else {
						layer.alert('删除失败！', {
							icon : 2,
							skin : 'layer-ext-moon'
						});
					}
				}
				);
			});
		}
		
	});
	
	var eidtData = function(paramObj) {
        var pageUrl = basePath + "common/infomanagement/SvrOperationalInfo004/init";
        Joyin.dialog("业务数据维护待维护表配置", pageUrl, '100%', '95%', paramObj, function() {
        	SvrOperationalInfo002_table001.reload();
        });
	}
	
	// 查询按钮事件
	$("#searchTable").click(function() {
		SvrOperationalInfo002_table001.load();
	});
});