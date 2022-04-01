--场内大宗交易指令
delete from sys_page where page_id = 'EtrExchangeBlockTrade';
INSERT INTO SYS_PAGE (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('EtrExchangeBlockTrade', 'ETR', 'home.html#/instructions/tradingorder?a=a', 'etrexchange/common/etrExchangeTrade001', null, null, null, '02', '01', '大宗交易指令', 'system', null, TIMESTAMP '2021-12-23 14:05:17', 'system', TIMESTAMP '2021-12-23 14:05:17');


--场内大宗交易指令
delete from SYS_MENU where page_id = 'EtrExchangeBlockTrade';
INSERT INTO SYS_MENU (MENU_ID, SYSTEM_CODE, ICON, PARENT_MENU_ID, PAGE_ID, ORDER_NO, IS_SPREAD, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SH_DEAL_MANAGEMENT_001_001_006', 'ETR', null, 'SH_DEAL_MANAGEMENT_001_001', 'EtrExchangeBlockTrade', 5, '02', '大宗交易指令', 'system', null, TIMESTAMP '2021-06-04 17:05:05', 'system', TIMESTAMP '2021-09-16 00:00:00');


--场内大宗交易指令
delete from SYS_WFL_BUSINESS_MODULE  where page_id IN ('EtrExchangeBlockTrade','EtrExchangeTradeCommon004') and ELEMENT_ID IN （'EtrExchangeBlockTrade_btn_submit','EtrExchangeBlockTrade_btn_save') ;
INSERT INTO SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG) VALUES ('EtrExchangeBlockTrade', 'EtrExchangeBlockTrade_btn_submit', 'oneApproval', 'system', null, TIMESTAMP '2019-05-24 00:00:00', 'system', TIMESTAMP '2021-12-23 00:00:00', 'ALL', '02');
INSERT INTO SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG) VALUES ('EtrExchangeTradeCommon004', 'EtrExchangeBlockTrade_btn_submit', 'oneApproval', 'system', null, TIMESTAMP '2019-05-24 00:00:00', 'system', TIMESTAMP '2021-12-23 00:00:00', 'ALL', '02');
INSERT INTO SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG) VALUES ('EtrExchangeTradeCommon004', 'EtrExchangeBlockTrade_btn_save', 'oneApproval', 'system', null, TIMESTAMP '2019-05-24 00:00:00', 'system', TIMESTAMP '2021-12-23 00:00:00', 'ALL', '02');


 