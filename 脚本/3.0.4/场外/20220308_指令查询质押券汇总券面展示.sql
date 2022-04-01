-- 删除表格中的汇总券面
delete from CFG_DATATABLE_SHOW where PAGECODE = 'CfetsEtrDispatcher'  and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = '05,02,03' and COLCODE='totalPrinAmt' and STATUS = 1;
--增加表单中的汇总券面
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'CfetsEtrDispatcher'  and DTCODE = 'CfetsEtrDispatcherInfo' and SPCDCODE = '05,02,03' and COLCODE='totalPrinAmt' and STATUS = 1;
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3) VALUES ('CfetsEtrDispatcher', 'CfetsEtrDispatcherInfo', '05,02,03', 'totalPrinAmt', '汇总质押券折算总额', null, 1, '{"colAlign": "left","dataType":"amount","precision":"2","unit":"wanYuan"}', 1, 0, null, null);