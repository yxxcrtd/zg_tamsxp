--本币业务指令
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'CfetsEtrRepoInfo' and DTCODE = 'PLEDPOSITION' and COLCODE = 'firstNetPrice';
--本币业务交易
update CFG_DATATABLE_SHOW set COLNAME = '估价净价' where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'ETRDETINFOPLEDGE' and COLCODE = 'firstNetPrice';
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'PLEDGEINFO' and COLCODE = 'firstNetPrice';
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'PLEDGEADDUSED' and COLCODE = 'firstNetPrice';
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'PLEDGEADDUSED04' and COLCODE = 'firstNetPrice';
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'ETRDETINFO' and COLCODE = 'firstNetPrice';
--指令分发
update CFG_READONLY_FORM_SHOW set COLNAME = '估价净价' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and COLCODE = 'firstprice';
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'CfetsEtrDispatcher' and DTCODE = 'CfetsEtrDispatcherInfo' and COLCODE = 'firstNetPrice'  and SPCDCODE = '05,02,03';
--成交确认
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'CfetsTrdQuery' and DTCODE = 'CfetsTrdQueryIbuPledgeInfo' and COLCODE = 'firstNetPrice';
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'CfetsTrdQuery' and DTCODE = 'CfetsTrdQueryRegPledgeInfo' and COLCODE = 'firstNetPrice';
--交收管理
update CFG_DATATABLE_SHOW set COLNAME = '估价净价(元)' where PAGECODE = 'cfetsManualSettleQuery' and DTCODE = 'CfetsManualSettleInfo' and COLCODE = 'firstNetPrice';