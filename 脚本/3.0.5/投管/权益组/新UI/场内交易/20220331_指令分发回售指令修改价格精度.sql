--回售价格 - 价格改为三位
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX003' AND COLCODE = 'unitPrice';
-- 添加行权竞价
delete from CFG_READONLY_FORM_SHOW where  PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = 'EX003' and COLCODE = 'unitPrice';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('CfetsEtrDispatcher', 'CfetsEtrDispatcherInfo', 'EX003', 'unitPrice', '行权净价', NULL, '14', '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}', '1', '0', NULL, NULL);