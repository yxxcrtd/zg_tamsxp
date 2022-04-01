update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'allPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'matureYield';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'entrustNum';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F01EX002' and COLCODE = 'entrustAmt';

update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX005' and COLCODE = 'pledgeStandardNumber';

update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F01EX001' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"yuan"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F01EX001' and COLCODE = 'allPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"4","unit":"%"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F01EX001' and COLCODE = 'matureYield';
-- 股票 - 配股
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"gu"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX008' and COLCODE = 'entrustNum';
-- 股票 新股申购
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"gu"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX009' and COLCODE = 'entrustNum';


-- 质押式回购 - 竞价交易
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"%"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F07EX001' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F07EX001' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"0","unit":"zhang"}' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'F07EX001' and COLCODE = 'pledgeStandardNumber';

-- 修改资金账户
update CFG_READONLY_FORM_SHOW set COLCODE = 'dealCurAcct' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLCODE = 'secAcctId';
-- 基金 竞价交易
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX001' and COLCODE = 'dealCurAcct';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'F02EX001', 'dealCurAcct', '资金账户', NULL, '10', '{"colAlign": "left"}', '1', '0', NULL, NULL);
-- 基金 大宗交易
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX002' and COLCODE = 'dealCurAcct';
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX002' and COLCODE = 'declaTypeStr';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'F02EX002', 'dealCurAcct', '资金账户', NULL, '10', '{"colAlign": "left"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'F02EX002', 'declaTypeStr', '申报类型', NULL, '18', '{"colAlign": "left"}', '1', '0', NULL, NULL);