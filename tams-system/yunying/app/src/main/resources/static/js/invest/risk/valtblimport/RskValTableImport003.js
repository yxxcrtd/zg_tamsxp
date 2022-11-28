
/**
 * 上传文件
 */
(function() {

    /**
     * 保存或提交
     * @param operateType 操作类型（save，submit）
     * @returns
     */
    function saveOrSubmit(operateType) {
        /**
         * 获取列表参数
         */
        var object = $.serializeToObject('#submitForm', false);
        //add by zhoubin 20220518 start
        if (FileCommon.getUploadFileInfo().length == 0) {
            Joyin.error("请选择文件上传");
            return;
        }
        if (!FileCommon || !FileCommon.getUploadFileInfo()) {
            Joyin.error(JoyinUtil.getMessage("BUSS_COM_E_0054"));
            return;
        }
        //add by zhoubin 20220518 end
        if(FileCommon) {
            object.fileArray = FileCommon.getUploadFileInfo();
            if(object.fileArray && _.isArray(object.fileArray)){
                var flag=true;
                var flagDupPrd = false;
                var prdmap = new Map();
                $.each(object.fileArray, function(index,obj){
                    obj.seqNo = obj.fileNo;
                    var index=obj.srcName.lastIndexOf('.');
                    var ext = obj.srcName.substr(index+1);
                    var prdName = obj.srcName.substr(0,index);
                    if(prdmap.has(prdName)){
                        flagDupPrd = true;
                    }

                    obj.targetFile = obj.fileName;
                });
                if(flagDupPrd){
                    Joyin.error("一个申报产品，只能上传一个zip文件！");
                    return;
                }
            }
        }
        object.uploadArrStr=JSON.parse(dataArrStr);
        var x=object.fileArray[0].targetFile;

        $.ajaxSettings.async = false;
        var print=[];
        for(var info in object.fileArray){
                Joyin.ajaxJson(basePath + 'invest/risk/valtblimport/RskValTableImport002/initValTableInfo',
                    {ptlId:object.fileArray[info].srcName,valTableFileName:object.fileArray[info].targetFile}, function(result) {
                        x=result[0].fileInfoId;
                        Joyin.ajax(basePath + 'invest/risk/valtblimport/RskValTableImport002/selectLog', {infoName:x},function (result2){
                            print.push(result2);
                            console.log(print);
                            //rskvalsumnameTable1.load();
                            rskvalsumnameTable1.addRow(result2[0]);
                            rskvalsumnameTable1.reload();
                            if(print.length==object.fileArray.length){
                                rskvalsumnameTable1.addRow(result2[0]);
                                rskvalsumnameTable1.reload();
                                var sout="";
                                for( let i=0;i<print.length;i++){
                                    sout+=print[i];
                                }

                                // Joyin.alert(sout)



                            }


                        })
                    })
            }
            console.log("=================" + print);





        /*Joyin.confirm(JoyinUtil.getMessage("COMMON_C_0001", JoyinUtil
            .getLabel("SysCommon.submit")),    // 上传成功后回调预览接口
            function (){
                var print=[];
            for(info in object.fileArray){
                Joyin.ajaxJson(basePath + 'invest/risk/valtblimport/RskValTableImport002/initValTableInfo',
                    {ptlId:object.fileArray[info].srcName,valTableFileName:object.fileArray[info].targetFile}, function(result) {
                    x=result[0].fileInfoId;
                        Joyin.ajax(basePath + 'invest/risk/valtblimport/RskValTableImport002/selectLog', {infoName:x},function (result2){
                            print.push(result2);
                            Joyin.alert(result2);

                        })
                    })
            }


            }


            );*/
    }

    //控件加载完成后
    window.componentLoadCallback = function(isDone, pagePath, params){
    }
    /**
     * 画面加载完成
     */
    $(function() {
        // 绑定按钮事件
        $('#SvrPerProDeclareReg002_btn_submit').click(function() {
            saveOrSubmit('submit');
        });
    });
}());

var rskvalsumnameTable1 = $("#rskvalstructureTable").joyintable({
    page: false,
    checkbox:false,
    cols:[
        {field:'remark', title: '日志', width:200,sortable: false}
    ]
});
