delete from cfg_readonly_form_show where pagecode='IbuQuoteBond' and dtcode='ETRDETINFO'  and spcdcode='02';
insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'entrustDate', '指令日期', null, 1, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'portfolioId', '组合代码', null, 2, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'portfolioName', '组合名称', null, 3, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'businessModel', '业务模式', null, 4, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'finprodMarketId', '资产代码', null, 5, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'finprodAbbr', '资产简称', null, 6, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'tradeMarketStr', '交易场所', null, 7, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'psStr', '交易方向', null, 8, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'settleSpreedStr', '清算速度', null, 9, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'priceModelStr', '价格模式', null, 10, '{"colAlign": "left"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'secAcctName', '证券账户', null, 11, null, 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'tradeFinishPercent', '币种', null, 12, null, 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'entrustNum', '券面总额', null, 13, '{"colAlign": "left","dataType":"amount","precision":"2","unit":"wanYuan"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'ourEntNum', '本方券券面总额', null, 14, '{"colAlign": "left","dataType":"amount","precision":"2","unit":"wanYuan"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'lendinEntNum', '融入券券面总额', null, 15, '{"colAlign": "left","dataType":"amount","precision":"2","unit":"wanYuan"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'price', '净价', null, 16, '{"colAlign": "left","dataType":"amount","precision":"4","unit":"yuan"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'unitFprice', '全价', null, 17, '{"colAlign": "left","dataType":"amount","precision":"4","unit":"yuan"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'fpriceAmt', '结算金额', null, 18, '{"colAlign": "left","dataType":"amount","precision":"2","unit":"yuan"}', 1, 0, null, null);

insert into cfg_readonly_form_show (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, EXTENDCFG, STATUS, REMARK1, REMARK2, REMARK3)
values ('IbuQuoteBond', 'ETRDETINFO', '02', 'remark', '备注', null, 19, '{"colAlign": "left","dataType":"amount","precision":"2"}', 1, 0, null, null);
