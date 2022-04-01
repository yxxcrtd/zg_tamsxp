/**
 * Created by Administrator on 2017/8/14 0014.
 */
// By zhengyan
var debtSubmitInfo = function() {
    return{
        getPageParams : function() {
            var repName=$('#repName').val();
            if (repName){
                repName = repName.join();
            }
            return {
                // 报表名称
                repName : repName,
                // 报送日期开始
                repDateStart : $('#reportDate').startDate(),
                // 报送日期结束
                repDateEnd : $('#reportDate').endDate()
            };
        },
        table : $('#debtSubmitInfo').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return debtSubmitInfo.getPageParams();
            }
        },
        //双击显示详情页
        dblclick:function(table, data) {
        	var param={};
            param.reportNo=data.reportNo;
            param.repName=data.repName;
            /***三期CHB_SUBMIT_STATUS去除改造  20220310**/
            param.repCode=data.repCode;
        	Joyin.dialog("反馈信息详情页",detailUrl,'900px','90%',param);
        },
        checkbox : false,
        cols: [
            {field: 'reportNo',visible:false},
            {field: 'repCode',visible:false},
            /***三期CHB_SUBMIT_STATUS去除改造  20220310**/
            { title: "报表名称", field: 'repName',width:170},
            { title: "报送文件ID", field: 'repFileId',width:115 },
            { title: "报送时间", field: 'repDate',type: 'datetime',width:100},
            { title: "报送结果",width:100, field: 'repResult',
                render: function (data, type, row, meta) {
                    if(!row.repResult){
                        return "";
                    }
                    if(row.repResult=="01"){
                        return "<span class='debt-suc'>发送成功</span>";
                    }
                    if(row.repResult=="02"){
                        return "<span class='debt-pass'>报送通过</span>";
                    }
                    if(row.repResult=="03"){
                        return "<span class='debt-fail'>报送失败</span>";
                    }
                    if(row.repResult=="04"){
                        return "<span class='debt-return'>部分通过</span>";
                    }
                }
            },
            { title: "反馈信息",field: 'repFailedInfo', sortable:false},
            //{field:'operation', title: '操作', type:'action', buttons: ['edit']}
            ]
    })
    }
}();

$(document).ready(function() {
 // 查询按钮点击事件
    $('#DebtSubmitInfo001_btn_search').click(function() {
        if($('#searchForm').joyinvalid()) {
            debtSubmitInfo.table.load();
        }
    });
    /*debtSubmitInfo.table.bind('click', '.edit', function(data, index) {
        var param={};
        param.reportNo=data.reportNo;
        Joyin.dialog("反馈信息详情页",detailUrl,'900px','90%',param);
    });*/
});
