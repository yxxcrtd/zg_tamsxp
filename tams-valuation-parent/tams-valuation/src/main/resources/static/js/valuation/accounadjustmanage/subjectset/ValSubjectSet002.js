(function() {
    function saveOrSubmit() {
        getHands();
        var object = $.serializeToObject('#submitForm');
        object.pageModel = pageModel;
        Joyin.ajaxJson(basePath + 'valuation/accounadjustmanage/subjectset/ValSubjectSet002/check', object, function(result) {
            Joyin.confirm(result.data, function() {
                Joyin.ajaxJson(basePath + 'valuation/accounadjustmanage/subjectset/ValSubjectSet002/save', object, function(result) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            });
        });
    };

    /**组装科目辅助项JSON**/
    function getHands() {
        var checkbox = [];
        var str="";
        var fel="";
        $("select[name='handCodes']").each(function (i) {
            console.log($(this).css('display'));
            // if($(this).css('display')!='none'&&($(this).val() == '' || $(this).val() == undefined || $(this).val() == null)){
            //     Joyin.warn($(this).attr("title")+"不能为空");
            // }

            console.log($(this).val());
            if ($(this).val() != '' && $(this).val() != undefined && $(this).val() != null) {
                str = '{"handCode" :"' + $(this).attr('id') + '","handCodeValue" :"' + $(this).val() + '"}';
                checkbox.push(str);
                fel = fel + '' + $(this).attr('id') + '==' + "'" + $(this).val() + "'" + '&&';
            }
        });
        $('#handjson').val('['+checkbox+']');
        fel=fel.substr(0,fel.lastIndexOf("&&"));
        console.log(fel);
        $('#subjectHandFelCode').val(fel);



    }

    /**
     * 选择内部科目编码 联动查询已配置的辅助核算项 拼接_DIV找到与页面对应的下拉选项并展示
     */
    var sbjectInternalcodeChange = function() {
        reset();
        var booksetId = $('#booksetId').val();
        if (booksetId == '' || booksetId == undefined) {
            reset();
            return ;
        }
        var subjectInternalCode = $('#subjectInternalCode').val();

        if (subjectInternalCode == '' || subjectInternalCode == undefined) {
            reset();
            return ;
        }

        Joyin.ajax(basePath + 'valuation/accounadjustmanage/assistaccount/ValAssistAccounting001/findHandsByCondition',
            {booksetId: booksetId,subjectInternalCode:subjectInternalCode,status:"Y"}, function (data) {
                if (data) {
                    for (var i = 0, l = data.length; i < l; i++) {
                        $("#"+data[i].handCode+"_DIV").show();
                        $("#"+data[i].handCode).show();
                    }

                }
            })
    }

    function reset(){
        $("select[name='handCodes']").each(function () {
            //清空所有选项
            var id=$(this).attr("id");
            //每个下拉框的值 被保存该下拉框ID +"_O"的隐藏域中
            $(this).val( $("#"+id+"_O").val() ).trigger("change");
            $(this).hide();
        });

        $("div[name='handCodesdiv']").each(function () {
            //隐藏所有
            $(this).hide();
        });



    }

    /**
     * 画面加载完成
     */
    $(function () {
        if (typeof pageModel !== 'undefined'
            && (pageModel === PAGE_MODEL_EDIT)) {
            //账套和科目号不允许修改
            $("#booksetId").attr('disabled',"true");
            $("#subjectNo").attr('disabled',"true");
        }

        sbjectInternalcodeChange();
        // 绑定按钮事件
        $('#ValSubjectSet002_btn_submit').click(function() {
            saveOrSubmit();
        });
        
        //修改上级科目号时，自动添加到科目号中
        $("#fsubjectId").change(function(){
            $("#subjectNo").val($("#fsubjectId").val());
        });

        /**内部科目编码联动显示可见的辅助核算项**/
        $("#subjectInternalCode").change(function(){
            sbjectInternalcodeChange();
            layui.form.render();
        });

        $("#booksetId").change(function(){
            sbjectInternalcodeChange();
            layui.form.render();
        });



    });



}());