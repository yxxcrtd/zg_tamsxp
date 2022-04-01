-- 删除 清算速度\全价\本方券券面总额\融入券券面总额
delete from CFG_READONLY_FORM_SHOW where PAGECODE='CfetsEtrDispatcher' and DTCODE='CfetsEtrDispatcherInfo' and SPCDCODE='03' and COLCODE='otherSettleSpeed';
delete from CFG_READONLY_FORM_SHOW where PAGECODE='CfetsEtrDispatcher' and DTCODE='CfetsEtrDispatcherInfo' and SPCDCODE='03' and COLCODE='allPrice';
delete from CFG_READONLY_FORM_SHOW where PAGECODE='CfetsEtrDispatcher' and DTCODE='CfetsEtrDispatcherInfo' and SPCDCODE='03,02' and COLCODE='entrustOwnNum';
delete from CFG_READONLY_FORM_SHOW where PAGECODE='CfetsEtrDispatcher' and DTCODE='CfetsEtrDispatcherInfo' and SPCDCODE='03,02' and COLCODE='integrateSecAmount';
update CFG_READONLY_FORM_SHOW set COLNAME='行权净价' where PAGECODE='CfetsEtrDispatcher' and DTCODE='CfetsEtrDispatcherInfo' and SPCDCODE='03' and COLCODE='unitPrice';

-- 删除 清算速度\全价\本方券券面总额\融入券券面总额
delete from CFG_READONLY_FORM_SHOW where PAGECODE='IbuQuoteBond' and DTCODE='ETRDETINFO' and SPCDCODE='02' and COLCODE='settleSpreedStr';
delete from CFG_READONLY_FORM_SHOW where PAGECODE='IbuQuoteBond' and DTCODE='ETRDETINFO' and SPCDCODE='02' and COLCODE='unitFprice';
delete from CFG_READONLY_FORM_SHOW where PAGECODE='IbuQuoteBond' and DTCODE='ETRDETINFO' and SPCDCODE='02' and COLCODE='ourEntNum';
delete from CFG_READONLY_FORM_SHOW where PAGECODE='IbuQuoteBond' and DTCODE='ETRDETINFO' and SPCDCODE='02' and COLCODE='lendinEntNum';
update CFG_READONLY_FORM_SHOW set COLNAME='行权净价' where PAGECODE='IbuQuoteBond' and DTCODE='ETRDETINFO' and SPCDCODE='02' and COLCODE='price';

--修改指令价格为行权净价
update CFG_READONLY_FORM_SHOW set COLNAME='行权净价' where PAGECODE='CfetsEtrDispatcher' and DTCODE='CfetsEtrDispatcherInfo' and SPCDCODE='EX003' and COLCODE='unitPrice';
