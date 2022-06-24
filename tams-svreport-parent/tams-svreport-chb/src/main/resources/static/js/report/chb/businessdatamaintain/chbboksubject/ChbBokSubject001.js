(function () {

    /**
     * 获取查询参数
     */
    var getPageParams = function() {
        return {
            fsubjectId: $('#fsubjectId').val()
            ,subjectLevel: $('#subjectLevel').val()
            ,balFlag: $('#balFlag').val()
            ,subjectNo: $('#subjectNo').val()
            ,subjectName: $('#subjectName').val()
            ,subjectType: $('#subjectType').val()
            ,status:$('#status').val()
        }
    };
	var table=$("#dataTable").joyintable({
        ajax: {
            url: 'findPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
		searchOnInit: false,
		cellMinWidth:100 ,
		cols: [
		    {field:'booksetId', visible: false },
		    {field:'subjectNo', width:100, title: 'ChbBokSubject.subjectNo'}
		    ,{field:'subjectName', width:150, title: 'ChbBokSubject.subjectName'}
		    ,{field:'fsubjectId', width:100, title: 'ChbBokSubject.fsubjectId'}
		    ,{field:'subjectTypeStr', width:100, sortField:"subjectType", title: 'ChbBokSubject.subjectType'}
		    ,{field:'subjectLevelStr', width:100, title: 'ChbBokSubject.subjectLevel'}
		    ,{field:'balFlagStr', width:100, title: 'ChbBokSubject.balFlag'}
		    ,{field:'dataSourceStr', width:100, title: 'ChbBokSubject.dataSource'}
		    ,{field:'statusValidStr', width:100, title: 'ChbBokSubject.status'}
		    ,{field:'remark', width:150, title: 'ChbBokSubject.remark'}
		    ,{field:'errMsg', width:150, title: 'ChbBokSubject.errMsg'}
		    ,{field:'updateTimeStr', width:100, title: 'ChbBokSubject.updateTime'}
		    ,{field:'updateUser', width:100, title: 'ChbBokSubject.updateUser'}
		    ,{field:'action', title: 'SysCommon.operation' , type:'action',buttons: ['edit', 'delt'] , width:100}
		]
	  });

    /**
     * 绑定编辑事件
     */
    table.bind('click', '.edit', function(data, index) {
        data.operateType='edit'
        data.pageModel=PAGE_MODEL_EDIT;
        Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbboksubject/chbboksubject002/check', data, function(result) {
            Joyin.confirm("请确认是否修改?", function(){
                Joyin.dialog(JoyinUtil.getLabel("ChbBokSubject002"),
                    basePath + "chb/businessdatamaintain/chbboksubject/chbboksubject002/init",
                    "98%", "95%",data,
                    function(){table.reload();});
            });
        });

    });

     /**
         * 绑定删除事件
         */
        table.bind('click', '.delt', function(data, index) {
        	var object = {selectedItem: [data], operateType:"delete"};
            Joyin.ajaxJson(basePath + '/chb/businessdatamaintain/chbboksubject/chbboksubject001/check', object, function(result) {
                Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                    WorkFlow.doDeleteList(basePath + '/chb/businessdatamaintain/chbboksubject/chbboksubject001/delete', object, "ChbBokSubject001_btn_delete", "ChbBokSubject002",function(){
                        table.reload();
                    });
                });
            });
        });
    var exportExcel = function(){
        //modify by yanya on 20210209 增加按照页面选择数据进行导出
        //Joyin.exportExcel('export',getSearchParams())
        var selArr = [];
        var datas = null;
        if (table != null && table != '') {
            selArr = table.getAllCheckedRows();
            datas = JSON.stringify(selArr);
        }
        if (!selArr || selArr.length == 0) {
            var paramObj = getPageParams();
            Joyin.exportExcel('export', paramObj);
        } else {
            Joyin.exportExcel('export', {
                datas : datas
            });
        }
    };



    /**
     * 画面加载完成
     */
    $(function() {
      //新建
      $('#ChbBokSubject001_btn_add').click(function() {
          Joyin.dialog(JoyinUtil.getLabel("SysCommon.create")+JoyinUtil.getLabel("ChbBokSubject002"),
                  basePath + "chb/businessdatamaintain/chbboksubject/chbboksubject002/init",
                  "95%", "80%", {pageModel:PAGE_MODEL_NEW});
      });
      // 绑定按钮事件
      $('#ChbBokSubject001_btn_search').click(function() {
          table.load();
          $('#ChbBokSubject001_btn_delete').show();
      });
      $('#ChbBokSubject001_btn_export').click(function() {
          exportExcel();
      });
      $('#ChbBokSubject001_btn_delete').click(function() {
          if(table!=null && table!=''){
              var selArr = table.getAllCheckedRows();
          }
          if (!selArr || selArr.length == 0) {
              Joyin.error(JoyinUtil.getMessage("没有选中数据"));
              return;
          }
          var object = {selectedItem: selArr, operateType:"delete"};
            Joyin.ajaxJson(basePath + 'chb/businessdatamaintain/chbboksubject/chbboksubject001/check', object, function(result) {
               Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001",JoyinUtil.getLabel("SysCommon.delete")), function(){
                   WorkFlow.doDeleteList(basePath + 'chb/businessdatamaintain/chbboksubject/chbboksubject001/delete', object, "ChbBokSubject001_btn_delete", "ChbBokSubject002",function(){
                       table.reload();
                   });
               });
           });
      });
      /**
       * 导入按钮
       */
      // $('#ChbBokSubject001_btn_import').click(function() {
      //     Joyin.uploadFile(function(fileName) {
      //         Joyin.ajax(basePath +"chb/businessdatamaintain/chbboksubject/chbboksubject001/checkImportFile", {
      //             fileName : fileName,
      //             tableName : "bok_subject"
      //         },function(result){
      //                 if(!result || !($.isArray(result) && result.length > 0)){
      //                     Joyin.ajax(
      //                         basePath + 'chb/businessdatamaintain/chbboksubject/chbboksubject001/importFileSave',
      //                         {fileName : fileName},function (data1) {
      //                             layui.layer.closeAll()
      //                             table.reload();
      //                         });
      //                 }
      //
      //         });
      //     });
      // });



        // 导入按钮
        $('#ChbBokSubject001_btn_import').click(function() {
            Joyin.confirm("请确认是否要导入数据？", function() {
                var options = {};
                options.pageId = "ChbBokSubject001";
                options.exts = 'xls|xlsx';
                Joyin.uploadFile(options,function(fileName) {
                    Joyin.ajax(basePath + 'chb/businessdatamaintain/chbboksubject/chbboksubject001/checkConfirmMsgForImportData', {fileName: fileName,tableName:'BOK_SUBJECT'}, function(result) {
                        if ($.isArray(result.joyinMessages) && result.joyinMessages.length > 0) {
                            var joyinMessages = "";
                            $.each(result.joyinMessages, function(i, joyinMessage){
                                joyinMessages += joyinMessage.message + "<br />"
                            });
                            Joyin.confirm(joyinMessages, function(){
                                Joyin.ajax(basePath + '/chb/businessdatamaintain/chbboksubject/chbboksubject001/importFileSave', {fileName: fileName,tableName:'BOK_SUBJECT'},function () {
                                    table.reload();
                                });
                            });
                        }else{
                            Joyin.ajax(basePath + '/chb/businessdatamaintain/chbboksubject/chbboksubject001/importFileSave', {fileName: fileName,tableName:'BOK_SUBJECT'},function () {
                                table.reload();
                            });
                        }
                    });
                },function() {
                    table.reload();
                });
            });
        });

    });
}());