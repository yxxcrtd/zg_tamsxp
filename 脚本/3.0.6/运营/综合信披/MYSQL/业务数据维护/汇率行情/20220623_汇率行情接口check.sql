DELETE FROM CHB_DATACHECK_RULE WHERE CHECKRULEID like 'MST_EXCHARGE_QUOTE-20220623%';
 
INSERT INTO CHB_DATACHECK_RULE VALUES('MST_EXCHARGE_QUOTE-20220623-0001','MST_EXCHARGE_QUOTE','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isValidDate3','TRADE_DATE','==','yyyy-MM-dd','Y','交易日期格式必须为yyyyMMdd或yyyy-MM-dd','E');