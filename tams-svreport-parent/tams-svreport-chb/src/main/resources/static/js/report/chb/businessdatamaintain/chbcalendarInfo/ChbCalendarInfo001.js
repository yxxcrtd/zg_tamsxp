/**
 * 市场日历信息
 */
(function () {
    /**
     * 获取查询参数
     */
    var getSearchParams = function () {
        var startDate = $('#cdate').startDate();
        var endDate = $('#cdate').endDate();
        if ($('#calendarType').val()) {
            var calendarType = $('#calendarType').val().join(',');
        }
        return {
            calendarType: calendarType,
            ishDay: $('#ishDay').val(),
            status: $('#status').val(),
            startDate: startDate,
            endDate: endDate
        }
    };
    /**
     * 定义数据表格
     */
    var table = $("#dataTable").joyintable({
        ajax: {
            url: 'queryList', type: 'POST', data: function () {
                return getSearchParams();
            }
        }, cols: [
            {
                title: '数据状态', field: 'statusStr', width: 100
            },
            {
                title: '日期', field: 'cdate', type: 'date',width: 150
            }, {
                title: '市场日历类型', field: 'calendarName',width: 200
            }, {
                title: '是否节假日', field: 'isHolidayStr', width: 100
            }, {
                title: '错误信息', field: 'errMsg', width: 495, sortable: false
            }, {
                title: '更新时间', field: 'updateTimeStr', width: 200
            }, {
                title: '更新人', field: 'updateUser', width: 100, sortable: false
            }, {
                field: 'isHoliday', visible: false
            }, {
                field: 'calendarId', visible: false
            }]

    });
    /**
     * 导出
     */
    var exportExcel = function () {
        Joyin.exportExcel(basePath + "chb/businessdatamaintain/chbcalendarInfo/ChbCalendarInfo001/export", getSearchParams());
    };
    /**
     * 绑定按钮事件
     */
    $(function () {
        /**
         * 查询按钮
         */
        $('#ChbCalendarInfo001_btn_search').click(function () {
            table.load();
        });
        /**
         * 导出按钮
         */
        $('#ChbCalendarInfo001_btn_export').click(function () {
            exportExcel();
        });
        /**
         * 导入按钮
         */
        $('#ChbCalendarInfo001_btn_import').click(function () {
            Joyin.confirm("请确认是否要导入数据?", function () {
                Joyin.uploadFile(function (fileName) {
                    Joyin.ajax(basePath + 'chb/businessdatamaintain/chbcalendarInfo/ChbCalendarInfo001/checkConfirmMsgForImportData', {fileName: fileName}, function (result) {
                        if ($.isArray(result.joyinMessages) && result.joyinMessages.length > 0) {
                            var joyinMessages = "";
                            $.each(result.joyinMessages, function(i, joyinMessage){
                                joyinMessages += joyinMessage.message + "<br />"
                            });
                            Joyin.confirm(joyinMessages, function(){
                                Joyin.ajax(basePath + '/chb/businessdatamaintain/chbcalendarInfo/ChbCalendarInfo001/importFile', {fileName: fileName},function () {
                                    table.reload();
                                });
                            });
                        }else{
                            Joyin.ajax(basePath + '/chb/businessdatamaintain/chbcalendarInfo/ChbCalendarInfo001/importFile', {fileName: fileName},function () {
                                table.reload();
                            });
                        }
                    });
                });
            });
        });
        /**
         * 设为节假日
         */
        $('#ChbCalendarInfo001_btn_sethday').click(function () {
            if (table != null && table != '') {
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            } else {
                var count = 0;
                for (var i = 0; i < selArr.length; i++) {
                    if (selArr[i].isHoliday == yes) {
                        count++;
                    }
                }
                if (count != 0) {
                    layer.msg("请选择节假日标志为工作日的数据");
                } else {
                    Joyin.confirm("是否设置为节假日?", function () {
                        Joyin.ajaxJson(basePath + "chb/businessdatamaintain/chbcalendarInfo/ChbCalendarInfo001/updateIsHoliday", {list:selArr,isHoliday:'1'}, function () {
                            table.reload();
                        });
                    });
                }
            }
        });
        /**
         * 设为工作日
         */
        $('#ChbCalendarInfo001_btn_setwday').click(function () {
            if (table != null && table != '') {
                var selArr = table.getAllCheckedRows();
            }
            if (!selArr || selArr.length == 0) {
                Joyin.error(JoyinUtil.getMessage("COMMON_E_0002"));
                return;
            } else {
                var count = 0;
                for (var i = 0; i < selArr.length; i++) {
                    if (selArr[i].isHoliday == no) {
                        count++;
                    }
                }
                if (count != 0) {
                    layer.msg("请选择节假日标志为节假日的数据");
                } else {
                    Joyin.confirm("是否设置为工作日?", function () {
                        Joyin.ajaxJson(basePath + "chb/businessdatamaintain/chbcalendarInfo/ChbCalendarInfo001/updateIsHoliday", {list:selArr,isHoliday:'0'}, function () {
                            table.reload();
                        });
                    });
                }
            }

        });

    });
}());