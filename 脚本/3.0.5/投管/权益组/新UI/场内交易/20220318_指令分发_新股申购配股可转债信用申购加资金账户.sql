--
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'CfetsEtrDispatcher'and SPCDCODE IN ('EX007','EX008','EX009') and  COLCODE = 'trdAcctId';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3) VALUES ('CfetsEtrDispatcher', 'CfetsEtrDispatcherInfo', 'EX007', 'trdAcctId', '资金账户', null, 18, '{"colAlign": "left"}', 1, 0, null, null);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3) VALUES ('CfetsEtrDispatcher', 'CfetsEtrDispatcherInfo', 'EX008', 'trdAcctId', '资金账户', null, 18, '{"colAlign": "left"}', 1, 0, null, null);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3) VALUES ('CfetsEtrDispatcher', 'CfetsEtrDispatcherInfo', 'EX009', 'trdAcctId', '资金账户', null, 18, '{"colAlign": "left"}', 1, 0, null, null);

 