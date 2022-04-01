$(function () {
//    var myDefineData=[
//            ["待处理", "是", "Edinburgh", "5421", "2011/04/25", "$320,800", "qqq", "ssss", "xzzz", "zzz", "zzz", "zzz", "zz", "ssss", "111", "222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222"],
//            ["有效", "否", "Tokyo", "8422", "2011/07/25", "$170,750", "qqq", "ssss", "xzzz", "zzz", "zzz", "zzz", "zz", "ssss", "111", "222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222"],
//            ["修改", "是", "San Francisco", "1562", "2009/01/12", "$86,000", "qqq", "ssss", "xzzz", "zzz", "zzz", "zzz", "zz", "ssss", "111", "222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222"],
//            ["有效", "是", "Edinburgh", "6224", "2012/03/29", "$433,060", "qqq", "ssss", "xzzz", "zzz", "zzz", "zzz", "zz", "ssss", "111", "222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222"],
//            ["待处理", "是", "Tokyo", "5407", "2008/11/28", "$162,700", "qqq", "ssss", "xzzz", "zzz", "zzz", "zzz", "zz", "ssss", "111", "222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222"],
//            ["有效", "是", "New York", "4804", "2012/12/02", "$372,000", "qqq", "ssss", "xzzz", "zzz", "zzz", "zzz", "zz", "ssss", "111", "222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222", "333", "555", "444", "11111", "22222"],
//    ];
//    var joyTable = function (option) {
//        var defaultOption = {
//            data: myDefineData,
//            language: {
//                "sProcessing": "处理中...",
//                "sLengthMenu": " _MENU_ 条/页",
//                "sZeroRecords": "没有匹配结果",
//                "sInfo": "共 _TOTAL_ 条数据，",//
//                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
//                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
//                "sInfoPostFix": "",
//                "sSearch": "搜索:",
//                "sUrl": "",
//                "sEmptyTable": "表中数据为空",
//                "sLoadingRecords": "载入中...",
//                "sInfoThousands": ",",
//                "dom": "<'row'<'col-sm-6'f>>" +
//                "<'row'<'col-sm-12'tr>>" +
//                "<'row'<'col-sm-5 mytestr'il <'clear'>><'col-sm-7'p>>",
//                "oPaginate": {
//                    "sFirst": "|<",
//                    "sPrevious": "<",
//                    "sNext": ">",
//                    "sLast": ">|"
//                },
//                "oAria": {
//                    "sSortAscending": ": 以升序排列此列",
//                    "sSortDescending": ": 以降序排列此列"
//                }
//            },
//            "dom": "<'row'<'col-sm-12'tr>>" +
//            "<'row'<'col-sm-5 mytestr' il <'clear'>><'col-sm-7'p>>",
//
//            "pagingType": "full_numbers",
//            scrollX: true
//        }
//        var tableId = option.tableId;
//        option.tableId = null;
//        option.drawCallback = function (settings) {
//            // 取消全选
//            var api = this.api();
//            var table = $(this);
//            var id = table.attr("id") + "_wrapper";
//            var checkbox = $("#" + id + " .dataTables_scrollHead table thead tr th:first");
//            if (checkbox.hasClass("joy-table-th-checkbox")) {
//                return;
//            }
//            $('#' + tableId + ' tbody').on('click', 'tr td:first', function () {
//                var parent = $(this).parent();
//                var hasSelectedCount = api.rows({selected: true}).count();
//                var count = api.rows().count();
//                if ($(parent).hasClass("selected")) {
//                    hasSelectedCount -= 1;
//                } else {
//                    hasSelectedCount += 1;
//                }
//
//                if (hasSelectedCount == count) {
//                    checkbox.addClass("selected-checkbox")
//                } else {
//                    checkbox.removeClass("selected-checkbox")
//                }
//            });
//
//            checkbox.addClass("joy-table-th-checkbox");
//            checkbox.on("click", function () {
//                if (checkbox.hasClass("selected-checkbox")) {
//                    checkbox.removeClass("selected-checkbox")
//                    api.rows().deselect();
//                } else {
//                    checkbox.addClass("selected-checkbox")
//                    api.rows().select();
//                }
//            })
//        }
//
//        if (!option.select) {
//            option.select = {
//                style: 'multi',
//                selector: 'td:first-child',
//                blurable: false
//            }
//        }
//
//        if (!option.columnDefs) {
//            option.columnDefs=
//                [{
//                    orderable: false,
//                    className: 'select-checkbox',
//                    targets: 0
//                }]
//        }
//
//        option = $.extend(true, defaultOption, option);
//        $("#" + tableId).dataTable(option);
//    }
    var tableInitFlag=true;
    //$(".form_date").datetimepicker({
        //format: 'yyyy-mm-dd',
        //"minView": 2,
        //autoclose: true
    //});
//    $('#joyTab a').click(function (e) {
//        e.preventDefault()
//        $(this).tab('show')
//    })
//    for(var i=1;i<=15;i++){
//        joyTable({
//            tableId: "table"+i,
//            columns: [
//                {title: "报表名称"},
//                {title: "资产代码/产品代码"},
//                {title: "报送状态"},
//                {title: "报送日期"},
//                {title: "报送截止日"}
//            ]
//        });
//    }
    var color = ['rgb(168,222,100)','rgb(251,247,134)', 'rgb(255,153,153)'];
    var xmlData={};
    var indexTotal = $(".layui-this").eq(0).attr("lay-id");
    readConfig=function(){
        Joyin.ajaxSync('readConfig', {indexTotal : indexTotal}, function(result){
//            for(var item in result){
//                if(item=="top"){
//                    xmlData.data1=result[item];
//                }
//                else
//                var data=result[item];
//            }
            //把读取到的配置文件赋值
        	xmlData.tops = result["tops"];
        	xmlData.mids = result["mids"];
            /*xmlData.data1=result["tops"][0];
            xmlData.data2=result["mids"][0];*/
            var resultHtml=$("#colorTitle")[0].innerHTML;
            resultHtml=resultHtml.replace(/75/g,"TOP");
            resultHtml=resultHtml.replace(/50/g,"MID");
            resultHtml=resultHtml.replace(/TOP/g,xmlData.tops[0]);
            resultHtml=resultHtml.replace(/MID/g,xmlData.mids[0]);
            
            $("#colorTitle")[0].innerHTML=resultHtml;
            });
    };
    readConfig();
    var curSelectedIndex = 0;
    var xAxisData = ["产品申\n报登记", "产品发\n行登记","产品募集期\n总量登记",
                     "产品存续\n期登记", "产品终\n止登记", "资产要\n素登记", "交易信\n息登记", "资产估\n值登记",
                     "资管计划底\n层持仓登记",  "银行月度\n统计表登记" ,"销售结算资金\n交易信息表","理财业务销售\n数据月度统计表","投资者持有\n信息登记","投资者\n信息登记","投资者明细\n信息登记",
                     "总部或分支机构\n信息登记", "销售人员身份\n信息登记","销售人员奖惩\n投诉信息登记","代理销售\n机构信息登记","委托销售产品\n编码信息登记"
                    ];
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: function (param) {
                return "延期数量：" + param.data.num + "<br/>" + "合格率：" + param.data.value + "%"
            }
        },
        xAxis: [{
            data: xAxisData,
            axisLabel: {
                interval: 0,
                textStyle: {
                    color: function (param) {
                        return param == xAxisData[curSelectedIndex] ? "#0e86fe" : "black";
                    },
                    fontSize:'9.5'
                }
            },
            position: 'bottom', triggerEvent: true
        }],
        yAxis: {
            name :"合格率%",
            axisLine: {
                show: "100%"
            },
            max:100,
            min: 0,
            interval :25,
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#999'
                }
            },
            position: 'right',
            splitNumber: 4
        },
        grid: {show: true, top: 10, left: 0, bottom: 35},
//        animationDelayUpdate: function (idx) {
//            // 越往后的数据延迟越大
//            return idx * 30;
//        },
        series: [
            {
                name: '',
                type: 'bar',
                barMaxWidth: 20,
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: true,
                        position: 'center',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (param) {
                        	var index = param.dataIndex
                            if(param.data.value>= xmlData.tops[index]){
                                return color[0];
                            }
                            else if(param.data.value>=xmlData.mids[index]){
                                return color[1];
                            }
                            else{
                                return color[2];
                            }
                            
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [{value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0}, {value: 0,num: 0},
                       {value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0},
                       {value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0},
                       {value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0},{value: 0, num: 0}
                    , {value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0},{value: 0, num: 0}
                ]
            }
        ]
    };
    var option1 = {
        tooltip: {
            trigger: 'item',
            formatter: function (param) {
                return "未报送：" + param.data["value"] + "<br/>" + "合格率：" + param.data.rate + "%"
            }
        },
        xAxis: [{
            data: ['','','','','','','','','','','','','','','','','','','',''],
            axisLabel: {
                interval: 0,
                textStyle: {
                    color: function (param) {
                        return param == xAxisData[curSelectedIndex] ? "#0e86fe" : "black";
                    }
                }
            },boundaryGap:[0,0],
            position: 'bottom', triggerEvent: true,
            axisLine:{
                show:false
            },
            axisTick:{
                lineStyle:{
                    width:0
                }
            }
        }],
        yAxis: {
            show:false,
            axisLine: {
                show: false
            },
            max: 30,
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#999'
                }
            }, position: 'right'
        },
        grid: {show: false, top: 10, left: 0, bottom: 35},
        series: [
            {
                name: '',
                type: 'bar',
                avoidLabelOverlap: false,
                barMaxWidth: 20,
                label: {
                    normal: {
                        show: false,
                        position: 'center',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (param) {
                            return param.dataIndex==curSelectedIndex?"#0e86fe":"white"
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
//                {value: 2}, {value: 2}, {value: 2}, {
//                    value: 2,
//                }, {value: 2}, {value: 2}, {value: 2}, {value: 2}
//                    , {value: 2}, {value: 2}, {value: 2, rate: 2}, {value: 2}
                ]
            }
        ]
    };
    
    var curSelectedIndexPbc = 0;
    var xAxisDataPbc = ["PPB1资产池\n基本信息", "PIB1产品\n基本信息","PIB2产品\n起始募集信息",
                     "PIE1产品\n终止信息",  "PPE1资产池\n终止信息", "PCD1产品资金\n募集信息", "PVD1资产\n负债信息",
                    "MI统计\n模板数据", "PIB产品\n基本信息", "PIE产品\n终止信息" ];
    var optionpbc = {
        tooltip: {
            trigger: 'item',
            formatter: function (param) {
                return "延期数量：" + param.data.num + "<br/>" + "合格率：" + param.data.value + "%"
            }
        },
        xAxis: [{
            data: xAxisDataPbc,
            axisLabel: {
                interval: 0,
                textStyle: {
                    color: function (param) {
                        return param == xAxisDataPbc[curSelectedIndexPbc] ? "#0e86fe" : "black";
                    }
                }
            },
            position: 'bottom', triggerEvent: true
        }],
        yAxis: {
            name :"合格率%",
            axisLine: {
                show: "100%"
            },
            max:100,
            min: 0,
            interval :25,
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#999'
                }
            },
            position: 'right',
            splitNumber: 4
        },
        grid: {show: true,left: 10, top: 10, bottom: 35},
//        animationDelayUpdate: function (idx) {
//            // 越往后的数据延迟越大
//            return idx * 30;
//        },
        series: [
            {
                name: '',
                type: 'bar',
                barMaxWidth: 40,
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: true,
                        position: 'center',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (param) {
                        	var index = param.dataIndex
                            if(param.data.value>= xmlData.tops[index]){
                                return color[0];
                            }
                            else if(param.data.value>=xmlData.mids[index]){
                                return color[1];
                            }
                            else{
                                return color[2];
                            }
                            
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [{value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0}, {value: 0,num: 0},
                       {value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0}, {value: 0, num: 0}
                        , {value: 0, num: 0}, {value: 0, num: 0}
                      
                    /*   {value: 0, num: 0}, {value: 0, num: 0}*/
                ]
            }
        ]
    };
    var option1pbc = {
        tooltip: {
            trigger: 'item',
            formatter: function (param) {
                return "未报送：" + param.data["value"] + "<br/>" + "合格率：" + param.data.rate + "%"
            }
        },
        xAxis: [{
            data: ['','','','','','','','','',''],
            axisLabel: {
                interval: 0,
                textStyle: {
                    color: function (param) {
                        return param == xAxisDataPbc[curSelectedIndexPbc] ? "#0e86fe" : "black";
                    }
                }
            },boundaryGap:[0,0],
            position: 'bottom', triggerEvent: true,
            axisLine:{
                show:false
            },
            axisTick:{
                lineStyle:{
                    width:0
                }
            }
        }],
        yAxis: {
            show:false,
            axisLine: {
                show: false
            },
            max: 30,
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#999'
                }
            }, position: 'right'
        },
        grid: {show: false,left: 0, top: 10, bottom: 35},
        series: [
            {
                name: '',
                type: 'bar',
                avoidLabelOverlap: false,
                barMaxWidth: 20,
                label: {
                    normal: {
                        show: false,
                        position: 'center',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (param) {
                            return param.dataIndex==curSelectedIndexPbc?"#0e86fe":"white"
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
//                		{value: 2}, {value: 2}, {value: 2}, {value: 2,},
//                 {value: 2}, {value: 2}, {value: 2}, {value: 2}
                    
                ]
            }
        ]
    };
    var pieDivs = $("[pie-type='CHB']");
    var pie1 = echarts.init(pieDivs[0])
    pie1.setOption(option, true);
    var pie2 = echarts.init(pieDivs[1]);
    pie2.setOption(option1, true);
//    $(".tab-pane").hide();
    pie1.on('click', function (params) {
        if (params.componentType == "xAxis") {
            var index = xAxisData.indexOf(params.value);
            if (index !== undefined) {;
                //$(".tab-pane").hide();
                //$("#tab"+(index+1)).show();
                //
            	 var resultHtml=$("#colorTitle")[0].innerHTML;
            	 var top = $('#top').text().substring(1,3)
            	 var mid = $('#end').text().substring(1,3)
            	 var topReg = '/'+top+'/g';
            	 var midReg = '/'+mid+'/g'
                 resultHtml=resultHtml.replace(eval(topReg),"TOP");
                 resultHtml=resultHtml.replace(eval(midReg),"MID");
                 resultHtml=resultHtml.replace(/TOP/g,xmlData.tops[index]);
                 resultHtml=resultHtml.replace(/MID/g,xmlData.mids[index]);
                 $("#colorTitle")[0].innerHTML=resultHtml;
                 
                $(".tab-pane.active").show();
                curSelectedIndex = index;
                pie1.setOption(option, true);
                pie2.setOption(option1, true);
                if($('#searchForm').joyinvalid()) {
                    tableInitFlag=true;
                    //gridTitle = xAxisData[curSelectedIndex];
                    //delayVerification.table.$element.dataTable(this.options);
                    //delayVerification.table.options.columns[2].title=gridTitle;
                    if(delayVerification.table.table){
                        delayVerification.table.reload();
                    }
                    else{
                        delayVerification.table.load();
                    }
                }
            }
        }
    
    });
    var pbcPie1 = $("#PBC-PIE-1")[0];
    // 重新设置宽度与高度，否则界面显示会有问题
    pbcPie1.style.width=window.innerWidth+'px';
    pbcPie1.style.height=pbcPie1.parentNode.style.height;
    var pie1pbc = echarts.init(pbcPie1);
    pie1pbc.setOption(optionpbc, true);
    var pbcPie2 = $("#PBC-PIE-2")[0];
    pbcPie2.style.width=window.innerWidth+'px';
    pbcPie2.style.height='10px';
    var pie2pbc = echarts.init(pbcPie2);
    pie2pbc.setOption(option1pbc, true);
//    $(".tab-pane").hide();
    pie1pbc.on('click', function (params) {
        if (params.componentType == "xAxis") {
            var index = xAxisDataPbc.indexOf(params.value);
            if (index !== undefined) {
                //$(".tab-pane").hide();
                //$("#tab"+(index+1)).show();
                //
            	 var resultHtml=$("#colorTitlePbc")[0].innerHTML;
            	 var top = $('#topPbc').text().substring(1,3)
            	 var mid = $('#endPbc').text().substring(1,3)
            	 var topReg = '/'+top+'/g';
            	 var midReg = '/'+mid+'/g'
                 resultHtml=resultHtml.replace(eval(topReg),"TOP");
                 resultHtml=resultHtml.replace(eval(midReg),"MID");
                 resultHtml=resultHtml.replace(/TOP/g,xmlData.tops[index]);
                 resultHtml=resultHtml.replace(/MID/g,xmlData.mids[index]);
                 $("#colorTitlePbc")[0].innerHTML=resultHtml;
//               
//                 $("#tabpan"+indexTotal).show();
                 $(".tab-pane.pbc").show();
                 $("[pie-type='PBC']").show();
                 curSelectedIndexPbc = index;
                pie1pbc.setOption(optionpbc, true);
                pie2pbc.setOption(option1pbc, true);
                if($('#searchForm').joyinvalid()) {
                    tableInitFlag=true;
                    //gridTitle = xAxisData[curSelectedIndex];
                    //delayVerification.table.$element.dataTable(this.options);
                    //delayVerification.table.options.columns[2].title=gridTitle;
                    if(delayVerification.table.table){
                        delayVerification.table.reload();
                    }
                    else{
                        delayVerification.table.load();
                    }
                }
            }
        }
    
    });
	// tab选项卡切换
//    var indexTotal='01';
	$("[lay-tab-id]").click(function(){
		indexTotal = this.getAttribute("lay-tab-id");
        $(".tab-pane.active").hide();
		$(".tab-pane").hide();
		$("#tabpan"+indexTotal).show();
		if(indexTotal=='02'){
        	$(".tab-pane.pbc").show();
        	$("[pie-type='PBC']").show();
        }else{
        	$(".tab-pane.active").show();
        }
		// 重新设置当前标签
		var $index=$(this).index();
		if(delayVerification.table.table){
            delayVerification.table.reload();
        }
        else{
            delayVerification.table.load();
        }

         readConfig();
	});
    window.onresize = function () {
        pie1.resize();
        pie2.resize();
        pie1pbc.resize();
        pie2pbc.resize();
    };
    window.delayVerification = function() {
        return {
            /**
             * 初始化的函数
             */
            init : function(){
                //防止报出时间为空的信息
                setTimeout(function() {delayVerification.searchData();},500);
                var isShow = $('#text').text();
                if('CHB' == isShow){
                	$('#rate02').hide();
                	$("#rate01").click();
                }else if('PBC' == isShow){
                	$('#rate01').hide();
                	$("#rate02").click();
                }else if('ALL' == isShow){
                	$("#rate01").click();
                }
                
                
            },
            getPageParams : function() {
                var isNotCount="01";
                if(!$('#isNotCount')[0].checked){
                    isNotCount="02";
                }
                return {
                    // 查询日期开始
                    searchDateStart : $('#searchDate').startDate(),
                    // 查询日期结束
                    searchDateEnd : $('#searchDate').endDate(),
                    //是否统计手工处理的数据
                    isNotCount : isNotCount,
                    orgCode : $('#orgCode').val()==null?null:$('#orgCode').val().join(","),
                };
            },
            searchData : function () {
                var param=delayVerification.getPageParams();
                if(param.searchDateStart==''||param.searchDateStart==null||param.searchDateEnd==''||param.searchDateEnd==null){
                    Joyin.error("查询日期不可为空！");
                    return;
                }
                var isShow = $('#text').text();
                if('PBC' != isShow){
	                Joyin.ajax('searchDate', param, function(result) {
	                    for (var index in result){
	                        var data=result[index];
	                        option.series[0].data[index-1].value=Math.round(data.rate);
	                        option.series[0].data[index-1].num=parseInt(data.unSubmitNum);
	                        
	                    }
	                    pie1.setOption(option, true);
	                    if(tableInitFlag){
	                        if(delayVerification.table.table){
	                            delayVerification.table.reload();
	                        }
	                        else{
	                            delayVerification.table.load();
	                            $(".tab-pane.active").show();
	                        }
	                    }
	                });
                }
                if('CHB' != isShow){
                	Joyin.ajax('searchDatePbc', param, function(result) {
                		for (var index in result){
                			var data=result[index];
                			optionpbc.series[0].data[index-1].value=Math.round(data.rate);
                			optionpbc.series[0].data[index-1].num=parseInt(data.unSubmitNum);
                			
                		}
                		pie1pbc.setOption(optionpbc, true);
                		if(tableInitFlag){
                			if(delayVerification.table.table){
                				delayVerification.table.reload();
                			}
                			else{
                				delayVerification.table.load();
                				if(indexTotal=='02'){
                					$(".tab-pane.pbc").show();
                					$("[pie-type='PBC']").show();
                				}else{
                					$(".tab-pane.active").show();
                				}
                			}
                		}
                	});
                }
                //pie1.setOption(option, true);
            },
            table : $('#table0').joyintable({
                ajax: {
                    url: 'searchTable',
                    type: 'POST',
                    data: function() {
                        var parm={};
                        parm=delayVerification.getPageParams();
                        if(parm.searchDateStart==''||parm.searchDateStart==null||parm.searchDateEnd==''||parm.searchDateEnd==null){
                            Joyin.error("查询日期不可为空！");
                            return;
                        }
                        if(indexTotal=='02'){
                           parm.index=curSelectedIndexPbc+1;
                           parm.indexTotal='02';
                        }else{
                           parm.index=curSelectedIndex+1;
                           parm.indexTotal='01';
                        }
                        return parm;
                    },
                },
                checkbox : false,
                rowno: false, // 显示行号
                searchOnInit: false, //初始化的时候就请求后台数据
                scrollX:true,
                //    scrollCollapse: true,
                cols: [
                        {title: "报表名称", field: "repName",width:'20%'},
                        {title: "唯一标识", field: "code",width:'20%'},
                        {title: "报送状态", field: "submitStatus",width:'20%'},
                        {title: "报送日期", field: "submitTime",width:'20%'},
                        {title: "报送截止日", type: 'date', field: "submitDeadline",width:'20%'}
                    ]
            }),
            //searchTable : function (params) {},
        }
    }();
    $(function() {
        delayVerification.init();
        // 查询按钮点击事件
        $('#DelayVerification001_btn_search').bind("click",delayVerification.searchData);
    }); 
}())