update CFG_DATATABLE_SHOW set COLCODE = 'tradingDay' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' and COLNAME = '委托日期';
update CFG_DATATABLE_SHOW set COLCODE = 'tradeTime'  WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' and COLNAME = '委托时间';

update CFG_DATATABLE_SHOW set COLCODE = 'tradingDay' WHERE PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' and COLNAME = '成交日期';