update CFG_DATATABLE_SHOW set COLCODE = 'brokerEntrustId' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' AND SPCDCODE IN ( 'EX012' ) and EFFERANGE = 0 and RANGEID = 'COMM' and COLCODE = 'entrustId';
-- 修改表头天数单位
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","unit":"day"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and COLCODE in('repoTerm', 'capDays');

update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX001' and COLCODE = 'entrustAmt';
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"3","unit":"yuan"}' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'F02EX001' and COLCODE = 'unitPrice';

update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "right","dataType":"amount","precision":"2","supportSearch":"精确"}' where  PAGECODE = 'EtrExchangeTradeJj' and DTCODE = 'ETRINFOS' AND SPCDCODE IN ( '999999' ) and EFFERANGE = 0 and RANGEID = 'COMM' and COLCODE!='businessModelStr' and COLCODE = 'price';
update CFG_DATATABLE_SHOW set EXTENDCFG = '{"colAlign": "right","dataType":"amount","precision":"2","supportSearch":"精确"}' where  PAGECODE = 'EtrExchangeTradeJj' and DTCODE = 'ETRINFOS' AND SPCDCODE IN ( '999999' ) and EFFERANGE = 0 and RANGEID = 'COMM' and COLCODE!='businessModelStr' and COLCODE = 'entrustAmt';

