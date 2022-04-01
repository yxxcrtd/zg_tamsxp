/**
 * Created by Administrator on 2017/8/14 0014.
 */
// By zhengyan
var getPageParams=function() {
	var repResult = $('#DebtSubmitInfo002_repResult').val();
	if (repResult) {
		repResult = repResult.join();
	}
	return {
        // 报送结果
        repResult : repResult,
        //唯一标识
        uniqueIdenti : $('#DebtSubmitInfo002_uniqueIdenti').val(),
        reportNo : reportNo,
        repName : repName,
        /***三期CHB_SUBMIT_STATUS去除改造  20220310**/
        repCode : repCode
        /***三期CHB_SUBMIT_STATUS去除改造  20220310**/
    };
}
var debtSubmitInfo002 = function() {
    return{
        getPageParams : getPageParams,
        table : $('#dataTable').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return debtSubmitInfo002.getPageParams();
            }
        },
        checkbox : false,
        searchOnInit: true, //初始化的时候就请求后台数据
        cols: [
            { title: "报表名称", field: 'repName',width:120},
            { title: "报送文件ID", field: 'repFileId',width:115 },
            { title: "报送流水号", field: 'seqNo'},
            { title: "唯一标识", field: 'uniqueIdenti',width:115 },
            { title: "报送时间", field: 'repDate',type: 'datetime',width:100},
            { title: "报送结果",width:95, field: 'repResult',
                render: function (data, type, row, meta) {
                    if(!row.repResult){
                        return "";
                    }
                    if(row.repResult=="01"){
                        return "<span class='debt-suc'>发送成功</span>";
                    }
                    if(row.repResult=="02"){
                        return "<span class='debt-fail'>发送失败</span>";
                    }
                    if(row.repResult=="03"){
                        return "<span class='debt-pass'>中债通过</span>";
                    }
                    if(row.repResult=="04"){
                        return "<span class='debt-return'>中债退回</span>";
                    }
                }
            },
            { title: "反馈信息",field: 'repFailedInfo',width:120}]
    })
    }
}();

$(function(){
	 // 导出按钮点击事件
	$('#DebtSubmitInfo002_btn_export').hide();
    $('#DebtSubmitInfo002_btn_exportFeedBackData').click(function() {
      var expArr=debtSubmitInfo002.table.getAllCheckedRows();
      if(!expArr || expArr.length ==0){
          Joyin.exportExcel("export",getPageParams());
      }
      else{
         var keyList = ""; 
         $.each(expArr, function(i, row) {
             keyList = keyList+row.repName + '|' + row.seqNo +'|'+row.repFailedInfo+",";
         });
         var param={};
         param.expKeys = keyList.substring(0,keyList.length-1);
         Joyin.exportExcel("exportSelect",param);           
      }
    });
})

$(document).ready(function() {
 // 查询按钮点击事件
    $('#DebtSubmitInfo002_btn_search').click(function() {
        debtSubmitInfo002.table.load();
    });
});
