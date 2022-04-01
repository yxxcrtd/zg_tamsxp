DELETE FROM CFG_READONLY_FORM_SHOW WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLCODE = 'dealCurAcct' and SPCDCODE = 'F11EX002';
DELETE FROM CFG_READONLY_FORM_SHOW WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLCODE = 'declaTypeStr' and SPCDCODE = 'F11EX002';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'F11EX002', 'dealCurAcct', '资金账户', NULL, '10', '{"colAlign": "left"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'F11EX002', 'declaTypeStr', '申报类型', NULL, '11', '{"colAlign": "left"}', '1', '0', NULL, NULL);

DELETE FROM CFG_READONLY_FORM_SHOW WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLCODE = 'dealCurAcct' and SPCDCODE = 'F11EX001';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'F11EX001', 'dealCurAcct', '资金账户', NULL, '10', '{"colAlign": "left"}', '1', '0', NULL, NULL);


-- 指令分发
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherDealInfo' AND SPCDCODE IN ( 'EX010' ) and COLCODE = 'tradedVolume';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherExeInfo' AND SPCDCODE IN ( 'EX010' ) and COLCODE = 'entrustVolume';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherExeInfo' AND SPCDCODE IN ( 'EX010' ) and COLCODE = 'tradedVolume';

-- 指令查询
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' AND SPCDCODE IN ( 'EX001', 'EX004' ) and COLCODE = 'tradedVolume';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' AND SPCDCODE IN ( 'EX001','EX004' ) and COLCODE = 'entrustVolume';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' AND SPCDCODE IN ( 'EX001','EX004' ) and COLCODE = 'tradedVolume';


update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLCODE = 'unitPrice' and COLNAME = '指令价格' and EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' ;

update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and COLCODE = 'unitPrice' and COLNAME = '指令价格';

update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherExeInfo' and COLCODE in( 'tradedVolume', 'entrustVolume');
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0"}' WHERE PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherDealInfo'  and COLCODE in( 'tradedVolume', 'entrustVolume')

