--场内交易指令导入页面
delete from SYS_PAGE where PAGE_ID = 'EtrExchangeTradeCommonImport';
INSERT INTO SYS_PAGE (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('EtrExchangeTradeCommonImport', 'ETR', '/etrexchange/common/EtrExchangeTradeCommonImport', 'etrexchange/common/EtrExchangeTradeCommonImport', null, null, null, '02', '01', '场内交易指令导入页面', 'system', null, TIMESTAMP '2021-05-20 16:13:21', 'system', TIMESTAMP '2021-09-16 00:00:00');
