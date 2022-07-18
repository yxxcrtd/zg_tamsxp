alter table  chb_equdet add TRADE_SEQ  VARCHAR(30) COMMENT '交易序列号';
alter table  chb_equdet add FUND_ACC_CODE  VARCHAR(30) COMMENT '关联活期存款账号开户行代码';
alter table  chb_equdet add FUND_ACC_NAME  VARCHAR(200) COMMENT '关联活期存款账号开户行名称';

alter table  chb_equdet_his add TRADE_SEQ  VARCHAR(30) COMMENT '交易序列号';
alter table  chb_equdet_his add FUND_ACC_CODE  VARCHAR(30) COMMENT '关联活期存款账号开户行代码';
alter table  chb_equdet_his add FUND_ACC_NAME  VARCHAR(200) COMMENT '关联活期存款账号开户行名称';


alter table  chb_equdet_sub_his add TRADE_SEQ  VARCHAR(30) COMMENT '交易序列号';
alter table  chb_equdet_sub_his add FUND_ACC_CODE  VARCHAR(30) COMMENT '关联活期存款账号开户行代码';
alter table  chb_equdet_sub_his add FUND_ACC_NAME  VARCHAR(200) COMMENT '关联活期存款账号开户行名称';


alter table  chb_equdet_sub add TRADE_SEQ  VARCHAR(30) COMMENT '交易序列号';
alter table  chb_equdet_sub add FUND_ACC_CODE  VARCHAR(30) COMMENT '关联活期存款账号开户行代码';
alter table  chb_equdet_sub add FUND_ACC_NAME  VARCHAR(200) COMMENT '关联活期存款账号开户行名称';

delete from sys_datadict_list where DICT_CODE='SVR_CHANNEL' and ITEM_CODE in('07','08','99');
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_CHANNEL', '07', '银联交易', 7, '0', 'system', null, '2021-09-17 10:43:45', 'system', '2021-09-17 10:43:45', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_CHANNEL', '08', '第三方支付', 8, '0', 'system', null, '2021-09-17 10:43:45', 'system', '2021-09-17 10:43:45', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_CHANNEL', '99', '其他', 9, '0', 'system', null, '2021-09-17 10:43:45', 'system', '2021-09-17 10:43:45', null);


/**投资者明细新增三个字段的check*/
delete from chb_datacheck_rule  where  DATATYPE='CHB_EQUDET' AND SRCPROPERTY='tradeSeq';
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_tradSeqno_000000000003', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'tradeSeq', null, '^[\\u4e00-\\u9fef\\u3400-\\u4db5\\x{00020000}-\\x{0002a6d6}\\x{0002a700}-\\x{0002b734}\\x{0002b740}-\\x{0002b81d}\\x{0002b820}-\\x{0002cea1}\\x{0002ceb0}-\\x{0002ebe0}\\uf900-\\ufad9\\ue815-\\ue86f\\u00b7\\u0021-\\u0026\\u0028-\\u007B\\u007D\\u007E\\u2000-\\u2040\\u2160-\\u216B\\u3000-\\u301F\\uFF00-\\uFF65\\uFFE0-\\uFFEF]{0,}$', 'Y', '交易序列号必须填写白名单中的文字。', 'E');
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_tradSeqno_000000000001', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isNotEmpty', 'tradeSeq', null, null, 'N', '交易序列号要素不可为空。', 'E');
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_tradSeqno_000000000002', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'lessThanCharLength', 'tradeSeq', null, '30', 'Y', '交易序列号过长。', 'E');

delete from chb_datacheck_rule  where  DATATYPE='CHB_EQUDET' AND SRCPROPERTY='fundAccCode';
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_fundAccCode_0000000003', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'fundAccCode', null, '^[\\u4e00-\\u9fef\\u3400-\\u4db5\\x{00020000}-\\x{0002a6d6}\\x{0002a700}-\\x{0002b734}\\x{0002b740}-\\x{0002b81d}\\x{0002b820}-\\x{0002cea1}\\x{0002ceb0}-\\x{0002ebe0}\\uf900-\\ufad9\\ue815-\\ue86f\\u00b7\\u0021-\\u0026\\u0028-\\u007B\\u007D\\u007E\\u2000-\\u2040\\u2160-\\u216B\\u3000-\\u301F\\uFF00-\\uFF65\\uFFE0-\\uFFEF]{0,}$', 'Y', '关联活期存款账号开户行代码必须填写白名单中的文字。', 'E');
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_fundAccCode_0000000001', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isNotEmpty', 'fundAccCode', null, null, 'N', '关联活期存款账号开户行代码不可为空。', 'E');
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_fundAccCode_0000000002', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'lessThanCharLength', 'fundAccCode', null, '30', 'Y', '关联活期存款账号开户行代码过长。', 'E');

delete from chb_datacheck_rule  where  DATATYPE='CHB_EQUDET' AND SRCPROPERTY='fundAccName';
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_fundAccName_0000000004', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'fundAccName', null, '^[\\u4e00-\\u9fef\\u3400-\\u4db5\\x{00020000}-\\x{0002a6d6}\\x{0002a700}-\\x{0002b734}\\x{0002b740}-\\x{0002b81d}\\x{0002b820}-\\x{0002cea1}\\x{0002ceb0}-\\x{0002ebe0}\\uf900-\\ufad9\\ue815-\\ue86f\\u00b7\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E\\u2000-\\u2040\\u2160-\\u216B\\u3000-\\u301F\\uFF00-\\uFF65\\uFFE0-\\uFFEF]{0,}$', 'Y', '关联活期存款账号开户行名称必须填写白名单中的文字。', 'E');
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_fundAccName_0000000001', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isNotEmpty', 'fundAccName', null, null, 'N', '关联活期存款账号开户行名称不可为空。', 'E');
INSERT INTO chb_datacheck_rule (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS) VALUES ('equityDetails_fundAccName_0000000002', 'CHB_EQUDET', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'lessThanCharLength', 'fundAccName', null, '200', 'Y', '关联活期存款账号开户行名称过长。', 'E');
