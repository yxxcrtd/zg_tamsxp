$(function() {
    
    /**
     * 获取查询参数
     */
    var getSearchParams = function() {
        return {
            ptlId:$('#ptlId').val(),
            valTableFileName:$("#valTableFileName").val(),
            valDate:$('#valDate').val(),
            structMapId:$('#structMapId').val(),
            sumsubMapId:$('#sumsubMapId').val(),
            assetSubMapId:$('#assetSubMapId').val(),
            subjectCodeCol:$('#subjectCodeCol').val(),
            startRow:$('#startRow').val(),
            trimChars:$('#trimChars').val(),
            filterId:$('#filterId').val(),
            assetName:$('#ptlName').val()
            };
        };
    /**
     * 定义表头
     */
    var cols = [
                   {field:'isMatchStr', title: 'RskValTableImport001.isMatch', width:200,sortable: false}//是否匹配
                   ,{field:'subCode',  title: 'RskValTableImport001.subCode',width:200,sortable: false}
                  ,{field:'subName',  title: 'RskValTableImport001.subName',width:200,sortable: false}
                  ,{field:'securityQuantity',  title: 'RskValTableImport001.securityQuantity',width:150,type:'number',digit:8,sortable: false}
                  ,{field:'unitCost',  title: 'RskValTableImport001.unitCost',type:'number',digit:8,width:150,sortable: false}
                  ,{field:'securityCost',  title: 'RskValTableImport001.securityCost',width:150,type:'number',digit:8,sortable: false}
                  ,{field:'ctnwr',  title: 'RskValTableImport001.ctnwr',width:150,type:'number',digit:8,sortable: false}
                  ,{field:'mcp',  title: 'RskValTableImport001.mcp',width:150,type:'number',digit:8,sortable: false}
                  ,{field:'smv',  title: 'RskValTableImport001.smv',width:150,type:'number',digit:8,sortable: false}
                  ,{field:'mcr',  title: 'RskValTableImport001.mcr',width:150,type:'number',digit:8,digit:8,sortable: false}
                  ,{field:'valueAdded',  title: 'RskValTableImport001.valueAdded',width:150,sortable: false}
                  ,{field:'suspensionInfo',  title: 'RskValTableImport001.suspensionInfo',width:150,sortable: false}
                  ,{field:'equityInfo',  title: 'RskValTableImport001.equityInfo',width:150,sortable: false}
                   ,{field:'securityCostOri', title: 'RskValTableImport001.securityCostOri',width:150,type:'number',digit:8,sortable: false}
                   ,{field:'smvOri', title: 'RskValTableImport001.smvOri',width:150,type:'number',digit:8,sortable: false}
                   ,{field:'valueAddedOri', title: 'RskValTableImport001.valueAddedOri',width:150,sortable: false}
                   ,{field:'ccy', title: 'RskValTableImport001.ccy',width:150,sortable: false}
                   ,{field:'exchangeRate', title: 'RskValTableImport001.exchangeRate',width:150,type:'number',digit:8,sortable: false}
                   ,{field:'valType',  title: 'RskValTableImport001.valType', visible : false}
                   ,{field:'isMatch',  visible : false}

       ];
    /**
     * 定义数据表格
     */
    var table=$("#valTable").joyintable({
            ajax: {
                url: JoyinUtil.getRealPath('invest/risk/valtblimport/RskValTableImport002/initValTableInfo'),
                type: 'POST',//请求类型
                dataType: 'json',
                data: function() {
                    return getSearchParams();//获取请求参数
                }//ajax请求数据
            },
        cols:cols,
        searchOnInit: true,
        loadonce:true,
        checkbox:false,
        done: function(table, allData){
            $('#RskValTableImport002_btn').show();
             //add by lpf 未匹配的科目红字展示 2021-03-23
            for(var i in table){
                 if(table[i].isMatch && table[i].isMatch=="N"){
                    $("tr[data-index='" + i + "']").attr({"style":"color:red"});
                 }
            }
        }
    });
    
    
    function saveOrSubmit(operateType) {
        if(table!=null && table!=''){
            var selArr = $("#valTable").data('joyintable').table.table.config.data;
        }
        if (!selArr || selArr.length == 0) {
            Joyin.error(JoyinUtil.getMessage("COMMON_E_0057"),JoyinUtil.getLabel("RskValTableImport002"));
            return;
        }
        // 1/3开头的科目未匹配给出提示：“请先维护资产科目的映射后再完成估值表导入”
        var goTo=false;
        for(var i in selArr){
             if(selArr[i].isMatch && selArr[i].isMatch=="N"){
                  if(selArr[i].subCode.substring(0,1)=="1" || selArr[i].subCode.substring(0,1)=="3"
                            ||(selArr[i].subCode.substring(0,1)=="2" && (selArr[i].subName.indexOf("回购")==0||selArr[i].subName.indexOf("融入")==0))){
                            goTo=true;
                            break;
                  }
             }
        }
        if(goTo){
           Joyin.confirm("请先维护资产科目的映射后再完成估值表导入,确认本次是否保存?",function() {
                var object = {selectedItem: selArr,ptlId:$('#ptlId').val(),valDate:$('#valDate').val(),operateType:operateType};
                 Joyin.ajaxJson(basePath + 'invest/risk/valtblimport/RskValTableImport002/check',object, function(result) {
                      if(result.valCount>0){
                           var msg = "估值表数据已存在,是否覆盖!";
                           Joyin.confirm(msg,function(){
                           　　　finalSubmit(object);
                       　　　});
                      }else{
                         finalSubmit(object);
                      }
                  })
             })

        }else{
            var object = {selectedItem: selArr,ptlId:$('#ptlId').val(),valDate:$('#valDate').val(),operateType:operateType};
                    Joyin.ajaxJson(basePath + 'invest/risk/valtblimport/RskValTableImport002/check',object, function(result) {
                        if(result.valCount>0){
                            var msg = "估值表数据已存在,是否覆盖!";
                            Joyin.confirm(msg,function(){
                                finalSubmit(object);
                            });
                        }else{
                            finalSubmit(object);
                        }
             })
        }
    };
    
    /**
     * 最终提交
     */
    var finalSubmit = function(object){
        var processIndex = showProcess($('#ptlId').val());
        Joyin.ajaxJson(basePath + 'invest/risk/valtblimport/RskValTableImport002/save',
                object,function(result){
                    setTimeout(function() {
                    layer.close(processIndex);
                    //点击保存后关闭窗口
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }, 1000);
                });
    }

    var  showProcess = function(barId){
        // 弹框进度条
        var processIndex = layer.open({
            type: 1
            ,title: false //不显示标题栏
            ,closeBtn: false
            ,area: '300px;'
            ,shade: 0.6
            ,id: 'JOY_process' //设定一个id，防止重复弹出
            ,moveType: 1 //拖拽模式，0或者1
            ,content: '<div class="layui-progress layui-progress-big" lay-showpercent="true" lay-filter="' + barId + '"><div class="layui-progress-bar" lay-percent="0%">0%</div></div>'
            ,success: function(layero){
                layero.css('background', 'transparent');
                // 开始进度条
                startProgressBar(layero, barId, function() {
                    //layer.close(processIndex);
                });
            }
        });
        return processIndex;
    }

    /**
     * 进度条开始
     * @callback 进度完成后的回调
     */
    function startProgressBar($progerssBar, barId, callback) {
        if($progerssBar && $progerssBar.length > 0) {
            var n = 0, timer = setInterval(function(){
                n = n + Math.random()*10|0;
                if(n>100){
                    n = 100;
                    clearInterval(timer);
                }
                layui.element.progress(barId, n+'%');
                $progerssBar.find('.layui-progress-bar').text(n+'%');
            }, 300+Math.random()*1000);
        }
    }

    $(function(){
        $('#RskValTableImport002_btn_save').click(function() {
            saveOrSubmit('save');
        });
    });
});
