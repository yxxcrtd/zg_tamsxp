--场内大宗交易指令
delete from SYS_WFL_BUSINESS_MODULE where ELEMENT_ID IN ('EtrExchangeTradeFjyywzl001_btn_submit','EtrExchangeTradeFjyywzl001_btn_save');
INSERT INTO SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG) VALUES ('EtrExchangeTradeCommon004', 'EtrExchangeTradeFjyywzl001_btn_submit', 'oneApproval', 'system', null, TIMESTAMP '2019-05-24 00:00:00', 'system', TIMESTAMP '2021-12-23 00:00:00', 'ALL', '02');
INSERT INTO SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG) VALUES ('EtrExchangeTradeFjyywzl001', 'EtrExchangeTradeFjyywzl001_btn_submit', 'oneApproval', 'system', null, TIMESTAMP '2019-05-24 00:00:00', 'system', TIMESTAMP '2021-12-23 00:00:00', 'ALL', '02');
INSERT INTO SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG) VALUES ('EtrExchangeTradeCommon004', 'EtrExchangeTradeFjyywzl001_btn_save', 'oneApproval', 'system', null, TIMESTAMP '2019-05-24 00:00:00', 'system', TIMESTAMP '2021-12-23 00:00:00', 'ALL', '02');

--菜单配置
delete from sys_page where page_id = 'EtrExchangeTradeFjyywzl001';
INSERT INTO SYS_PAGE (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('EtrExchangeTradeFjyywzl001', 'ETR', 'home.html#/instructions/notradeorder?a=a', 'etrexchange/common/etrExchangeTrade001', null, null, null, '02', '01', '非交易业务指令', 'system', null, TIMESTAMP '2021-12-23 14:05:17', 'system', TIMESTAMP '2021-12-23 14:05:17');

delete from SYS_MENU where page_id = 'EtrExchangeTradeFjyywzl001';
INSERT INTO SYS_MENU (MENU_ID, SYSTEM_CODE, ICON, PARENT_MENU_ID, PAGE_ID, ORDER_NO, IS_SPREAD, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SH_DEAL_MANAGEMENT_001_001_009', 'ETR', null, 'SH_DEAL_MANAGEMENT_001_001', 'EtrExchangeTradeFjyywzl001', 9, '02', '非交易业务指令', 'system', null, TIMESTAMP '2021-06-04 17:05:05', 'system', TIMESTAMP '2021-09-16 00:00:00');

