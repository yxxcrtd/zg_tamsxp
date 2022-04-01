update CFG_READONLY_FORM_SHOW set COLCODE = 'otherAssetType' where  PAGECODE = 'FieldEtrDispatcher' AND DTCODE = 'FieldEtrDispatcherInfo'  and COLCODE ='assetType';

update CFG_DATATABLE_SHOW set COLCODE = 'entrustBsStr' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' and COLNAME = '委托方向' and COLCODE = 'direction';
update CFG_DATATABLE_SHOW set COLCODE = 'marketStr' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' and COLNAME = '交易场所' and COLCODE = 'market';
update CFG_DATATABLE_SHOW set COLCODE = 'entrustStatusStr' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherExeInfo' and COLNAME = '委托状态' and COLCODE = 'entrustStatus';

update CFG_DATATABLE_SHOW set COLCODE = 'entrustBsStr' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' and COLNAME = '委托方向' and COLCODE = 'entrustBs';
update CFG_DATATABLE_SHOW set COLCODE = 'marketStr' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' and COLNAME = '交易场所' and COLCODE = 'market';

update CFG_DATATABLE_SHOW set COLCODE = 'stockCode' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' and COLNAME = '资产代码' and COLCODE = 'finprodMarketId';
update CFG_DATATABLE_SHOW set COLCODE = 'stockName' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherDealInfo' and COLNAME = '资产名称' and COLCODE = 'finprodAbbr';

update CFG_READONLY_FORM_SHOW set EXTENDCFG = '{"colAlign": "left"}' where PAGECODE = 'FieldEtrDispatcher' and DTCODE = 'FieldEtrDispatcherInfo' and SPCDCODE = 'EX004' and COLCODE = 'dealCurAcct';