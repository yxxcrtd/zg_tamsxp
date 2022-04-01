update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2"}' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' and colcode = 'entrustAmt' and SPCDCODE != 'EX010';
-- 指令查询 修改利率单位
update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left","dataType":"amount","precision":"2","unit":"%"}' WHERE PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo' AND SPCDCODE = 'EX010' AND COLCODE ='unitPrice';

update CFG_DATATABLE_SHOW set COLCODE = 'accountName'  WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' and EFFERANGE = 0 and RANGEID = 'COMM' and STATUS = 1 and COLCODE = 'financeAccount';
update CFG_DATATABLE_SHOW set COLCODE = 'accountName'  WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' and EFFERANGE = 0 and RANGEID = 'COMM' and STATUS = 1 and COLCODE = 'financeAccount';
