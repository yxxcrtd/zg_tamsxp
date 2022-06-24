/**
 * 销售结算资金交易信息维护
 */
(function(){
    /**
     * 获取查询参数
     */
    var searchform =function(){
        var param = $.serializeToObject('#searchForm');
        return param;
    };

    /**
     * 新建
     */
    function showDialog() {
        var data={"operateType":"add"};
        Joyin.dialog(JoyinUtil.getLabel('代理销售机构信息新建'),basePath+'report/submitmaintenance/agentinstitution/AgentInstitution002/init', '98%', '95%',data,function(){table.reload();});
    }
    
    /**
     * 定义数据表格
     */
    var table=$("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return searchform();
            }
        },
        dblclick:function(table, data) {
            data.operateType = "dblclick";
            data.pageModel=PAGE_MODEL_REFERENCE;
            Joyin.dialog(JoyinUtil.getLabel("代理销售机构信息查看"),
                    JoyinUtil.getRealPath("report/submitmaintenance/agentinstitution/AgentInstitution002/init"),
                    "98%", "95%", data,
                    function(){table.reload();});
        },
        cols:[
             {field:'statusStr', title: '数据状态', width:'100',sortField:'status'}
            ,{field:'issuePartyId', title: '发行机构代码', width:'150',sortField:'issuePartyId'}
            ,{field:'agentInstitutionCode', title: '代理销售机构代码', width:'200',sortField:'agentInstitutionCode'}
            ,{field:'agentInstitutionName', title: '代理销售机构名称', width:'200',sortField:'agentInstitutionName'}
            ,{field:'remark', title: '备注', width:'200',sortField:'remark'}
            ,{field:'enclosureChangeFlag', title: '附件修改标志',visible : false}
            ,{field:'fileNo', title: '附件代码',visible : false}
            ,{field:'enclosureNum', title: '附件个数', width:'200',sortField:'enclosureNum'}
            ,{title : '操作附件',field : 'operation',type : 'action',width : 150
               ,buttons : [  {text : '&#xe67c;',clas : 'upload',title : '上传'}
               ,{text : '&#xe640;',clas : 'deltAy',title : '删除'}
               , {text : '&#xe601;',clas : 'download',title : '下载'}
               , {text : '&#xe615;',clas : 'fileView',title : '查看'}
               ,{field: 'sumitSeq',visible:false} ]}
            ,{field:'action', title: '操作' , type:'action',buttons: ['edit', 'delt'] , width:'150'}
        ],
    });

        //附件上传
      	table.bind('click', '.upload', function(data, index) {
      		var dataArr=[];
      		var dataArrStr;
      			for(var i = 0; i < 1; i++){
      				var dataSrc={
      						fileNo:data.fileNo,
      						agentInstitutionCode:data.agentInstitutionCode,
      						enclosureChangeFlag :data.enclosureChangeFlag
      				}
      				dataArr[i]=dataSrc;
      			}

      		dataArrStr=JSON.stringify(dataArr);
      		Joyin.confirm("请确认是否要导入数据？", function() {

      			var flag=false;
      			if(data.fileNo && data.fileNo != null){
      				flag=true;
      			}
      			if(flag){
      				Joyin.confirm('所选数据已存在附件，是否覆盖？',
      						function(){
      					Joyin.dialog(JoyinUtil.getLabel("代理销售机构信息附件上传_修改"), basePath
      		                    + "report/submitmaintenance/agentinstitution/AgentInstitution003/init", "70%",
      		                    "60%", {
      		                        pageModel : PAGE_MODEL_NEW,
      		                        businessId : data.agentInstitutionCode,
      		                        dataArrStr : dataArrStr
      		                    },function(){
      		                    	table.reload();
      		                    });
      				})
      			}else{
      				Joyin.dialog(JoyinUtil.getLabel("代理销售机构信息附件上传"), basePath
      	                    + "report/submitmaintenance/agentinstitution/AgentInstitution003/init", "70%",
      	                    "60%", {
      	                        pageModel : PAGE_MODEL_NEW,
      	                        businessId : data.agentInstitutionCode,
      	                        dataArrStr : dataArrStr
      	                    },function(){
      	                    	table.reload();
      	                    });
      			}
              });
      	});

       //附件删除
    	table.bind('click', '.deltAy', function(data, index) {
    	    var object = {selectedItem: [data], operateType:"deleteFile"};
    		if (!data.fileNo || data.fileNo == null) {
    			Joyin.error("未上传附件，不能删除！");
    			return;
    		} else {
               Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/deleteFileCheck', object, function(result1) {
    			Joyin.confirm("请确认是否要删除已存在的附件？", function() {
    				Joyin.ajax('deltFile', {
    					agentInstitutionCode : data.agentInstitutionCode,
    					fileNo:data.fileNo,
    					enclosureChangeFlag : data.enclosureChangeFlag
    				}, function() {
    					table.reload();
    				})
    			});
    		   });
    		}
    	});

            //附件查看
        	table.bind('click', '.fileView', function(data, index) {
        		$.post('getFileList', {fileNo:data.fileNo}, function(str){
        			  layer.open({
        			    type: 0,
        			    content:decodeURIComponent(str), //注意，如果str是object，那么需要字符拼接。
        			    area: ['300px', '300px'],
        			    skin: 'layui-layer-lan',
        			    offset: 'rb'
        			  });
        			});
        	});

            //附件下载
        	table.bind('click', '.download', function(data, index) {
        		if (!data.fileNo || data.fileNo == null) {
        			Joyin.error("未上传附件，不能下载！");
        			return;
        		}
        		parm = {};
        		var url = JoyinUtil.getRealPath('/framework/download/' + data.fileNo);
        		Joyin.ajax(url, parm, function downloadFileByForm() {
        			Joyin.exportExcel('/framework/download/' + data.fileNo, parm);
        		},
        		function errorDownload(){
        			Joyin.error("文件未找到！");
        		}
        		);
        	});

        	                /**
                    		 * 附件批量上传
                    		 */
                    		$('#AgentInstitution001_btn_importFile').click(function() {
                    			var uploadArr = table.getAllCheckedRows();
                    			var uploadArrStr=JSON.stringify(uploadArr);
                    			var dataArr=[];
                    			var dataArrStr;
                    			if(!uploadArr || uploadArr.length == 0){
                    				Joyin.error("请选择要上传的数据！");
                    				return;
                    			}else{
                    				for(var i = 0; i < uploadArr.length; i++){
                    					var data={
                    							fileNo:uploadArr[i].fileNo,
                    							agentInstitutionCode:uploadArr[i].agentInstitutionCode
                    					}
                    					dataArr[i]=data;
                    				}
                    				dataArrStr=JSON.stringify(dataArr);
                    				Joyin.confirm("请确认是否要导入数据？", function() {
                    					var flag=false;
                    					for(var i = 0; i < uploadArr.length; i++){
                    						if(uploadArr[i].fileNo && uploadArr[i].fileNo != null){
                    							flag=true;
                    						}
                    					}
                    					if(flag){
                    						Joyin.confirm('所选产品已存在附件，是否覆盖？',
                    								function(){
                    							Joyin.dialog(JoyinUtil.getLabel("代理销售机构信息附件上传_修改"), basePath
                    				                    + "report/submitmaintenance/agentinstitution/AgentInstitution003/init", "70%",
                    				                    "60%", {
                    				                        pageModel : PAGE_MODEL_NEW,
                    				                        businessId : uploadArr.agentInstitutionCode,
                    				                        dataArrStr : dataArrStr
                    				                    },function(){
                    				                    	table.reload();
                    				                    });
                    						})
                    					}else{
                    						Joyin.dialog(JoyinUtil.getLabel("代理销售机构信息附件上传_修改"), basePath
                    			                    + "report/submitmaintenance/agentinstitution/AgentInstitution003/init", "70%",
                    			                    "60%", {
                    			                        pageModel : PAGE_MODEL_NEW,
                    			                        businessId : uploadArr.agentInstitutionCode,
                    			                        dataArrStr : dataArrStr
                    			                    },function(){
                    			                    	table.reload();
                    			                    });
                    					}
                    		        });
                    			}
                    		});





/**
 * 导出表格
 */
    var exportExcel = function() {
        if (table != null && table != '') {
            var selArr = table.getAllCheckedRows();
            var datas = JSON.stringify(selArr);
        }
        if (!selArr || selArr.length == 0) {
            var paramObj = searchform();
            Joyin.exportExcel('export', paramObj);
        } else {
            Joyin.exportExcel('export', {
                datas : datas
            });
        }
    };

    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.operateType = "edit";
        data.pageModel=PAGE_MODEL_EDIT;
        Joyin.dialog(JoyinUtil.getLabel("代理销售机构信息编辑"),
                JoyinUtil.getRealPath("report/submitmaintenance/agentinstitution/AgentInstitution002/init"),
                "98%", "95%", data,
           function(){table.reload();});
    });
    /**
     * 绑定删除事件
     */
    table.bind('click', '.delt', function(data, index) {
                    if(data.status !="E"){
                        return Joyin.error("只能删除有效数据，请重新选择");
                    }
        var object = {selectedItem: [data], operateType:"delete"};
        Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/check', object, function(result) {
          Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/deleteCheck', object, function(result1) {
            Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                 Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/delete', object, function() {
                          $('#AgentInstitution001_btn_search').trigger("click");
                 });
            });
          });
        });
    });
    /**
     * 编辑按钮事件
     */
    $(function () {
        // 绑定重置按钮事件
            $('#AgentInstitution001_btn_reset').click(function(){
                window.location.reload();
            });
        /**
         * 查询按钮
         */
        $('#AgentInstitution001_btn_search').click(function() {
            table.load();
            $('#AgentInstitution001_btn_delete').show();
        });
        /**
         * 导出按钮
         */
        $('#AgentInstitution001_btn_export').click(function() {
            exportExcel();
        });
        /**
         * 新建按钮
         */
        $('#AgentInstitution001_btn_add').click(function() {
            showDialog();
        });

    // 导入按钮点击事件
    $('#AgentInstitution001_btn_import').click(function() {
        Joyin.confirm("请确认是否要导入数据？", function() {
        var options = {};
        options.pageId = "AgentInstitution";
            Joyin.uploadFile(options,function(fileName) {
                Joyin.ajax(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/checkConfirmMsgForImportData', {fileName: fileName}, function() {
                    Joyin.ajax(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/importFile', {fileName: fileName});
                });
            },function() {
                table.reload();
            });
        });
    });

        /**
         * 批量删除按钮
         */
        $('#AgentInstitution001_btn_delete').click(function() {
            if(table!=null && table!=''){
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            }
            for(var i=0;i<selArr.length;i++){
                  if(selArr[i].status !="E"){
                        return Joyin.error("只能删除有效数据，请重新选择");
                  }
            }
            var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/check', object, function(result) {
              Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/deleteCheck', object, function(result1) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                      Joyin.ajaxJson(basePath + 'report/submitmaintenance/agentinstitution/AgentInstitution001/delete', object, function() {
                                          $('#AgentInstitution001_btn_search').trigger("click");
                      });
                });
              });
            });
        });
    });
}());