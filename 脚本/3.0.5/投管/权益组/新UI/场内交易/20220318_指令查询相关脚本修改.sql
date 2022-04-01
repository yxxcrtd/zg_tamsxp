-- 指令价格两位小数

update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","supportSearch":"精确"}'  WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' AND SPCDCODE IN ( '999999' ) AND COLCODE = 'etrPrice';

 -- 修改币种为第20位
update CFG_DATATABLE_SHOW set SEQNO = 21 WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' AND SPCDCODE IN ( '999999' )  and COLCODE = 'otherEntrustDate';

update CFG_DATATABLE_SHOW set SEQNO = 22 WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' AND SPCDCODE IN ( '999999' )  and COLCODE = 'transmitTime';

update CFG_DATATABLE_SHOW set SEQNO = 23 WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' AND SPCDCODE IN ( '999999' ) and COLCODE = 'invManagerName';

update CFG_DATATABLE_SHOW set SEQNO = 24 WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' AND SPCDCODE IN ( '999999' )  and COLCODE = 'remark';

update CFG_DATATABLE_SHOW set SEQNO = 20 WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' AND SPCDCODE IN ( '999999' )  and COLCODE = 'ccyStr';



-- 批量修改价格、数量为两位
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLCODE = 'unitPrice' ;

update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLCODE = 'entrustNum' ;

-- 新股申购、配股、可转债信用申购指令信息均为显示资金账户字段，期望显示资金账户 配债
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX009' and COLCODE = 'secAcctId';
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX007' and COLCODE = 'secAcctId';
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX008' and COLCODE = 'secAcctId';
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX006' and COLCODE = 'secAcctId';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX009', 'secAcctId', '资金账户', NULL, '16', '{"colAlign": "left"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX007', 'secAcctId', '资金账户', NULL, '22', '{"colAlign": "left"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX008', 'secAcctId', '资金账户', NULL, '10', '{"colAlign": "left"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX006', 'secAcctId', '资金账户', NULL, '10', '{"colAlign": "left"}', '1', '0', NULL, NULL);

-- 协议现券
update CFG_READONLY_FORM_SHOW set  EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2"}' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX016' and COLCODE = 'matureYield';
update CFG_READONLY_FORM_SHOW set  EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3"}' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX016' and COLCODE = 'unitPrice';
update CFG_READONLY_FORM_SHOW set  EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3"}' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX016' and COLCODE = 'allPrice';
update CFG_READONLY_FORM_SHOW set  COLCODE = 'otherBusiModel' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX016' and COLCODE = 'busiModel';