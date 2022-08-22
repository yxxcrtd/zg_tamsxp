$(function() {
    var getPageParams = function() {
        return {
            taskId : $('#taskId').val(),
            date : $('#date').val()
        };
    };
    var table = $('#autBatchProResultQuery001').joyintable({
        ajax: {
            url: 'searchPage',
            type: 'POST',
            data: function() {
                return getPageParams();
            }
        },
        //searchOnInit: false, //初始化的时候就请求后台数据
        checkbox : false,
        cols: [
            { title: "任务代码", field:'taskId',width:106},
            { title: "任务名称", field:'taskName',width:240},
            { title: "批处理执行开始时间", field:'startTime',type:'datetime',width:170},
            { title: "批处理执行结束时间", field:'endTime',type:'datetime',width:170},
            { title: "批处理执行结果", field:'batchResult', sortable:false}
            ]
    });

    // 查询按钮点击事件
    $('#AutBatchProResultQuery001_btn_search').click(function(){
        if($('#AutBatchProResultQuery001_searchForm').joyinvalid()) {
            table.load();
        }
    });

});