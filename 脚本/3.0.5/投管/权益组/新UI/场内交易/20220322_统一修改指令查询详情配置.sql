-- 查询列表金额3位 数量两位
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","supportSearch":"精确"}' WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' and COLCODE = 'entrustNum';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","supportSearch":"精确"}' WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' and COLCODE = 'etrPrice';
-- 债券 - 大宗指令
-- 	 净价：	3	元
--   全价：	3	元
--   到期收益率：	4	%
--   数量：	0	张
--   金额：	2	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'allPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'matureYield';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'entrustAmt';

-- 债券 -协议现券
--   净价：	3	元
--   全价：	3	元
--   到期收益率：	4	%
--   数量：	0	张
--   金额：	2	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX016' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX016' and COLCODE = 'allPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX016' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX016' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX016' and COLCODE = 'matureYield';

-- 债券 -行权
--   数量：	0	张
--   金额：	2	元
--   价格：	2	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX003' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX003' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX003' and COLCODE = 'unitPrice';

-- 债券 - 债转股
--   指令数量：	0	张
--   指令价格：	2	元
--   可转股数量：	4	股
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX004' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX004' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"gu"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX004' and COLCODE = 'expConNum';

-- 债券 - 质押券出入库 - 修改 标准券数量 = 指令数量 * 折算率
--   指令数量：    0	张
--   折算率：      2	%
--   标准券数量：  3	张
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX005' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"%"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX005' and COLCODE = 'pledgeRatio';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX005' and COLCODE = 'pledgeStandardNumber';
-- 添加一个 ：资金账户


-- 债券 - 配债
--   指令数量：	0	张
--   指令金额：	2	元
--   指令价格：	2	元
--   可转股数量：	4	张
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX006' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX006' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX006' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX006' and COLCODE = 'expConNum';

-- 债券 - 可转股申购
--   指令数量：	0	张
--   指令金额：	2	元
--   指令价格：	2	元
--   可转股数量：	4	张
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX007' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX007' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX007' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX007' and COLCODE = 'expConNum';

-- 股票 - 竞价交易
--   指令数量：	0	股
--   指令金额：	2	元
--   指令价格：	4	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"gu"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F11EX001' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F11EX001' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F11EX001' and COLCODE = 'unitPrice';

-- 股票 - 大宗交易
--   指令数量：	0	股
--   指令金额：	2	元
--   指令价格：	3	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"gu"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F11EX002' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F11EX002' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F11EX002' and COLCODE = 'unitPrice';

-- 股票 - 配股
--   指令数量：	0	股
--   指令金额：	2	元
--   指令价格：	2	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"gu"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX008' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX008' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX008' and COLCODE = 'unitPrice';

-- 股票 - 新股申购
--   指令数量：	0	股
--   指令金额：	2	元
--   指令价格：	2	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"gu"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX009' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX009' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX009' and COLCODE = 'unitPrice';

-- 基金 - 竞价交易
--   指令数量：	0	份
--   指令金额：	2	元
--   指令价格：	2	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"fen"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX001' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX001' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX001' and COLCODE = 'unitPrice';

-- 基金 - 大宗交易
--   指令数量：	0	份
--   指令金额：	2	元
--   指令价格：	3	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"fen"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX002' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX002' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX002' and COLCODE = 'unitPrice';

-- 质押式回购 - 竞价交易
--   指令数量：	0	份
--   指令金额：	2	元
--   回购利率：	2	%
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"fen"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F07EX001' and COLCODE = 'pledgeStandardNumber';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F07EX001' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F07EX001' and COLCODE = 'unitPrice';

-- 协议回购 - 首期
--   回购金额：	2	元
--   回购利率：    4	%
--   到期结算金额：2	元
--   折算率：	2 	%
--   质押数量：    0	张
--   券面总额：    0	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX010' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX010' and COLCODE = 'pledStrdPrinAmt';

-- 协议回购 - 提前回购
-- 	回购期限			天
-- 	占款天数：		天
--   回购金额：	2	元
--   回购利率：    4	%
--   回购利息：		 2  元
--   到期结算金额：2	元
--   折算率：	2 	%
--   质押数量：    0	张
--   券面总额：    0	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","unit":"day"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'repoTerm';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","unit":"day"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'capDays';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'mIntAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'mTradeAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'pledgeRatio';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'pledgeNumber';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'pledStrdPrinAmt';

-- 协议回购 - 到期回购
--   回购金额：	2	元
--   回购利率：    4	%
--   回购利息：    2	元
--   到期结算金额	2	元
-- 	回购利息：		 2  元
--   原折算率：	2	%
--   原质押数量:   0	张
--   原全面总额：  0	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{{"colAlign": "left","unit":"day"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'repoTerm';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{{"colAlign": "left","unit":"day"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'capDays';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'mIntAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'mTradeAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'pledgeRatio';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'pledgeNumber';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX012' and COLCODE = 'pledStrdPrinAmt';

-- 协议回购 - 到期续作
--   回购金额：	2	元
--   回购利率：    4	%
--   回购利息：    2	元
--   到期结算金额	2	元
-- 	回购利息：		 2  元
--   原折算率：	2	%
--   原质押数量:   0	张
--   原全面总额：  0	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{{"colAlign": "left","unit":"day"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'repoTerm';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{{"colAlign": "left","unit":"day"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'capDays';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'mIntAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'mTradeAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'pledgeRatio';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'pledgeNumber';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX013' and COLCODE = 'pledStrdPrinAmt';

-- 协议回购 - 中途换券
--   回购金额：	2	元
--   回购利率：    4	%
--   回购利息：    2	元
--   到期结算金额	2	元
--   原折算率：	2	%
--   原质押数量:   0	张
--   原全面总额：  0	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX014' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX014' and COLCODE = 'pledgeRatio';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX014' and COLCODE = 'pledgeNumber';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX014' and COLCODE = 'pledStrdPrinAmt';

update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX014' and COLCODE = 'oldPledgeRatio';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX014' and COLCODE = 'oldPledgeNumber';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX014' and COLCODE = 'oldPledStrdPrinAmt';

-- 协议回购 - 解除质押
--   指令数量：	0	张
--   回购金额：	2	元
--   券面金额：	2	元
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX015' and COLCODE = 'pledgeNumber';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX015' and COLCODE = 'pledStrdPrinAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX015' and COLCODE = 'entrustAmt';

update CFG_READONLY_FORM_SHOW set COLNAME = '对手席位' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F11EX002' and COLCODE = 'counterSeat';
update CFG_READONLY_FORM_SHOW set COLNAME = '对手席位' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX002' and COLCODE = 'counterSeat';
update CFG_READONLY_FORM_SHOW set COLNAME = '对手席位' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F11EX002' and COLCODE = 'counterSeat';
delete from CFG_READONLY_FORM_SHOW  WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX007' and COLCODE = 'expConNum';
update CFG_READONLY_FORM_SHOW set COLCODE = 'conNames' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX007' and COLCODE = 'conName';
delete from CFG_READONLY_FORM_SHOW WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX005' and COLCODE = 'secAcctId';
delete from CFG_READONLY_FORM_SHOW WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX005' and COLCODE = 'dealCurAcct';
INSERT INTO CFG_READONLY_FORM_SHOW(PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX005', 'dealCurAcct', '资金账户', NULL, '10', '{"colAlign": "left"}', '1', '0', NULL, NULL);
update CFG_READONLY_FORM_SHOW set COLCODE ='dealCurAcct' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX004' and COLCODE='secAcctId';



-- 指令分发- 新股申购
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"gu"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX009' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX009' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX009' and COLCODE = 'unitPrice';

-- 指令分发- 配股
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"gu"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX008' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX008' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX008' and COLCODE = 'unitPrice';

delete  from CFG_READONLY_FORM_SHOW WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and  COLCODE = 'addCla';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX011', 'addCla', '补充协议', NULL, '25', '{"colAlign": "left"}', '1', '0', NULL, NULL);
update CFG_READONLY_FORM_SHOW set COLNAME='对手方交易员' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX011' and COLCODE = 'counterTrader';




