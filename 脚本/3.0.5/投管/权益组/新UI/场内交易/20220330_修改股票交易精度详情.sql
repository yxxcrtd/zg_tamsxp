-- 指令分发 - tab数量取整
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherDealInfo' and COLCODE = 'tradedVolume';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherExeInfo' and COLCODE = 'entrustVolume';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherExeInfo' and COLCODE = 'tradedVolume';

-- 指令查询 - tab数量取整
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' and COLCODE = 'tradedVolume';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' and COLCODE = 'entrustVolume';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' and COLCODE = 'tradedVolume';

-- 修改价格模式 目标类型
update CFG_READONLY_FORM_SHOW set COLCODE = 'otherPriceModel' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLNAME = '价格模式' and COLCODE = 'priceModel';
update CFG_READONLY_FORM_SHOW set COLCODE = 'otherTargetType' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLNAME = '目标类型' and COLCODE = 'targetType';
-- 修改股票 大宗、竞价指令
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE in( 'F11EX002','F11EX001') and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE in( 'F11EX002','F11EX001') and COLCODE = 'unitPrice';

