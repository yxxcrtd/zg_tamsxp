-- 修改查询 操作记录表头： 业务编码 --> 指令代码
UPDATE CFG_DATATABLE_SHOW SET COLNAME = '指令代码' WHERE PAGECODE = 'CfetsEtrInfoQuery' AND DTCODE = 'CfetsEtrInfoOperateList' AND SPCDCODE IN ( '999999' ) AND EFFERANGE = 0 AND RANGEID = 'COMM' AND STATUS = 1 AND COLCODE = 'entrustId';

-- 修改 指令查询添加币种列

delete from CFG_DATATABLE_SHOW WHERE PAGECODE = 'FieldEtrInfoQuery' AND DTCODE = 'FieldEtrInfoQueryList' AND SPCDCODE IN ( '999999' ) AND EFFERANGE = 0 AND RANGEID = 'COMM' AND STATUS = 1 and COLCODE = 'ccyStr';
INSERT INTO CFG_DATATABLE_SHOW(PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,DISPLAYED,FROZEN,SORT,MAXLEN,EXTENDCFG,EFFERANGE,RANGEID,STATUS,REMARK1,REMARK2,REMARK3,ISKEY) VALUES ('FieldEtrInfoQuery', 'FieldEtrInfoQueryList', '999999', 'ccyStr', '币种', NULL, '24', '1', '0', '1', '100', '{"colAlign": "left","supportSearch":"模糊"}', '0', 'COMM', '1', '0', NULL, NULL, '0');

delete from CFG_DATATABLE_SHOW where PAGECODE = 'FieldEtrInfoQuery' and DTCODE = 'FieldEtrInfoQueryList' and SPCDCODE = '999999' and COLCODE = 'ccyStr' and COLNAME = '币种' and RANGEID = 'admin' and STATUS = '1';
INSERT INTO CFG_DATATABLE_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,DISPLAYED,FROZEN,SORT,MAXLEN,EXTENDCFG,EFFERANGE,RANGEID,STATUS,REMARK1,REMARK2,REMARK3,ISKEY) VALUES ('FieldEtrInfoQuery', 'FieldEtrInfoQueryList', '999999', 'ccyStr', '币种', 'ccy', '24', '1', '0', '1', '100', '{"colAlign": "left","supportSearch":"模糊"}', '1', 'admin', '1', '0', NULL, NULL, '0');
-- 指令查询详情 添加 资金账户
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX004' and COLCODE = 'secAcctId';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX004', 'secAcctId', '资金账户', NULL, '18', '{"colAlign": "left","dataType":"amount","precision":"4"}', '1', '0', NULL, NULL);

-- 指令查询 删除以下字段
-- 1债转股
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX004' and COLCODE = 'expConValue';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX004', 'expConValue', '转股价值', NULL, '16', '{"colAlign": "left","dataType":"amount","precision":"4"}', '1', '0', NULL, NULL);
-- 2配债
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX006'  and COLCODE = 'expConValue';
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX006'  and COLCODE = 'preb';
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX006'  and COLCODE = 'prec';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX006', 'expConValue', '转股价值', NULL, '17', '{"colAlign": "left","dataType":"amount","precision":"4"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX006', 'preb', '转股溢价率', NULL, '19', '{"colAlign": "left","dataType":"amount","precision":"4"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX006', 'prec', '纯债溢价率', NULL, '20', '{"colAlign": "left","dataType":"amount","precision":"4"}', '1', '0', NULL, NULL);

-- 3可转债申购
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX007' and COLCODE = 'expConValue';
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX007' and COLCODE = 'preb';
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX007' and COLCODE = 'prec';

INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX007', 'expConValue', '转股价值', NULL, '17', '{"colAlign": "left","dataType":"amount","precision":"4"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX007', 'preb', '转股溢价率', NULL, '19', '{"colAlign": "left","dataType":"amount","precision":"4"}', '1', '0', NULL, NULL);
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX007', 'prec', '纯债溢价率', NULL, '20', '{"colAlign": "left","dataType":"amount","precision":"4"}', '1', '0', NULL, NULL);



-- 指令查询 大宗交易 -> 修改详情显示
-- 修改投资目的 busiModel -> otherBusiModel
UPDATE CFG_READONLY_FORM_SHOW SET COLCODE = 'otherBusiModel' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'F01EX002' AND COLCODE = 'busiModel' AND COLNAME = '投资目的';
-- 修改对方席位 对方席位 -> 对手席位
UPDATE CFG_READONLY_FORM_SHOW SET COLNAME = '对手席位' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'F01EX002' AND COLCODE = 'counterSeat' AND COLNAME = '对方席位' ;
-- 修改指令金额、指令数量 小数位两位
UPDATE CFG_READONLY_FORM_SHOW SET EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2"}' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'F01EX002' AND COLCODE = 'entrustNum' AND COLNAME = '指令数量' ;
UPDATE CFG_READONLY_FORM_SHOW SET EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2"}' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'F01EX002' AND COLCODE = 'entrustAmt' AND COLNAME = '指令金额' ;

-- 协议现券指令缺少资金账户
delete from CFG_DATATABLE_SHOW where PAGECODE = 'EtrExchangeAgreeBond002' and DTCODE = 'ETRINFOS' and COLCODE = 'secAcctName' and SPCDCODE = '999999';
INSERT INTO CFG_DATATABLE_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,DISPLAYED,FROZEN,SORT,MAXLEN,EXTENDCFG,EFFERANGE,RANGEID,STATUS,REMARK1,REMARK2,REMARK3,ISKEY) VALUES ('EtrExchangeAgreeBond002', 'ETRINFOS', '999999', 'secAcctName', '资金账户', NULL, '17', '1', '0', '1', '100', '{"colAlign": "left","supportSearch":"模糊"}', '0', 'COMM', '1', '0', NULL, NULL, '0');
-- 组合代码 不可用操作
update CFG_DATATABLE_SHOW set STATUS = '0'
WHERE PAGECODE = 'EtrExchangeAgreeBond002' AND DTCODE = 'ETRINFOS'  AND SPCDCODE IN ( '999999' )  AND EFFERANGE = 1  AND COLCODE = 'portfolioId' AND STATUS = 1;


-- 指令查询 首期修改
-- 券面金额 - >	券面总额
UPDATE CFG_READONLY_FORM_SHOW SET COLNAME= '券面总额' WHERE PAGECODE= 'FieldEtrDispatcher' AND DTCODE= 'FieldEtrDispatcherInfo' AND SPCDCODE= 'EX010' AND COLCODE= 'pledStrdPrinAmt' AND COLNAME= '券面金额' ;
-- 到期结算金额 -> 到期结算额
UPDATE CFG_READONLY_FORM_SHOW SET COLNAME= '到期结算额' WHERE PAGECODE= 'FieldEtrDispatcher' AND DTCODE= 'FieldEtrDispatcherInfo' AND SPCDCODE= 'EX010' AND COLCODE= 'mTradeAmt' AND COLNAME= '到期结算金额' ;
-- 添加补充协议
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX010' and COLCODE = 'addCla';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX010', 'addCla', '补充协议', NULL, '25', '{"colAlign": "left"}', '1', '0', NULL, NULL);

-- 指令查询债券回购缺少 资金账户 项
delete from CFG_READONLY_FORM_SHOW where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX003' and COLCODE = 'secAcctId';
INSERT INTO CFG_READONLY_FORM_SHOW (PAGECODE,DTCODE,SPCDCODE,COLCODE,COLNAME,DICTCODE,SEQNO,EXTENDCFG,STATUS,REMARK1,REMARK2,REMARK3) VALUES ('FieldEtrDispatcher', 'FieldEtrDispatcherInfo', 'EX003', 'secAcctId', '资金账户', NULL, '16', '{"colAlign": "left"}', '1', '0', NULL, NULL);