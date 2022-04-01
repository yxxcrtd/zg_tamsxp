update CFG_DATATABLE_SHOW set SEQNO = 15 where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'DAYREGQUOTAINFO' and COLCODE = 'statisticalTimes';
update CFG_DATATABLE_SHOW set SEQNO = 16 where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'DAYREGQUOTAINFO' and COLCODE = 'quoteId';
update CFG_DATATABLE_SHOW set SEQNO = 17 where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'DAYREGQUOTAINFO' and COLCODE = 'trdId';
delete from CFG_DATATABLE_SHOW where PAGECODE = 'IbuQuoteRepo' and DTCODE = 'DAYREGQUOTAINFO' and COLCODE = 'dealDateStr';
INSERT INTO CFG_DATATABLE_SHOW (PAGECODE, DTCODE, SPCDCODE, COLCODE, COLNAME, DICTCODE, SEQNO, DISPLAYED, FROZEN, SORT, MAXLEN, EXTENDCFG, EFFERANGE, RANGEID, STATUS, REMARK1, REMARK2, REMARK3, ISKEY) VALUES ('IbuQuoteRepo', 'DAYREGQUOTAINFO', '999999', 'dealDateStr', '成交日期', 'dealDate', 14, 1, 0, 1, 100, '{"colAlign": "right","dataType":"date","supportSearch":"精确"}', 0, 'COMM', 1, 0, null, null, 0);
