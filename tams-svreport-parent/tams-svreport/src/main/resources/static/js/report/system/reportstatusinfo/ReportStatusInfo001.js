/**
 * Created by Administrator on 2021/5/10 0014.
 */
var ReportStatusInfo001 = function() {
    return{
        getPageParams : function() {
            var obj = $.serializeToObject('#searchForm');
            obj.startDate = $('#reportDate').startDate();
            obj.endDate = $('#reportDate').endDate();
            $('#registSeqNo').val("");
            obj.repStatus =  $('#repStatus').val();
            return obj;
        },
        table : $('#ReportStatusTable').joyintable({
            ajax: {
                url: 'searchPage',
                type: 'POST',
                data: function() {
                    return ReportStatusInfo001.getPageParams();
                }
            },
            searchOnInit:true,
            checkbox : false,
            cols: [
                { title: "报送类型", field: 'repType', visible:false},
                { title: "报送状态", field: 'repStatus', visible:false},
                { title: "反馈状态", field: 'feedbackStatus', visible:false},
                { title: "数据状态", field: 'status', visible:false},
                { title: "报送流水号", field: 'repSeqNo',visible:false},
                { title: "登记序列", field: 'registSeqNo',visible:false},
                { title: "数据状态", field: 'statusName',sortField:'status',width:150},
                { title: "报送类型", field: 'repTypeName',sortField:'repType',width:150},
                { title: "唯一标识符", field: 'uniqueCode',sortField:'uniqueCode',width:450},
                { title: "报送日期", field: 'repDate',type: 'datetime',sortField:'repDate',width:150},
                { title: "报送状态", field: 'repStatusName',sortField:'repStatus',width:150},
                { title: "反馈状态", field: 'feedbackStatusName',sortField:'feedbackStatus',width:150},
                { title: "反馈消息", field: 'feedbackMsg',sortField:'feedbackMsg',width:150}
            ]
        })
    }
}();

$(document).ready(function() {
 // 查询按钮点击事件
    $('#ReportStatusInfo001_btn_search').click(function() {
        if($('#searchForm').joyinvalid()) {
            ReportStatusInfo001.table.load();
        }
    });
});
