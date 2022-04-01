
----增加工作流模板
delete from sys_wfl_business_module where PAGE_ID = 'EtrExchangeAgreRepTrade001' and ELEMENT_ID = 'EtrExchangeAgreRepTrade001_btn_save';
insert into sys_wfl_business_module (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG)
values ('EtrExchangeAgreRepTrade001', 'EtrExchangeAgreRepTrade001_btn_save', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate, 'ALL', '02');

delete from sys_wfl_business_module where PAGE_ID = 'EtrExchangeAgreRepTrade001' and ELEMENT_ID = 'EtrExchangeAgreRepTrade001_btn_submit';
insert into sys_wfl_business_module (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG)
values ('EtrExchangeAgreRepTrade001', 'EtrExchangeAgreRepTrade001_btn_submit', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate, 'ALL', '02');

----增加页面配置
delete from sys_page where page_id = 'EtrExchangeAgreRepTrade001';
INSERT INTO SYS_PAGE (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('EtrExchangeAgreRepTrade001', 'ETR', 'home.html#/instructions/repurchaseorder?a=a', 'etrexchange/common/etrExchangeTrade001', null, null, null, '02', '01', '协议回购指令', 'system', null, TIMESTAMP '2021-12-23 14:05:17', 'system', TIMESTAMP '2021-12-23 14:05:17');

delete from SYS_MENU where page_id = 'EtrExchangeAgreRepTrade001';
INSERT INTO SYS_MENU (MENU_ID, SYSTEM_CODE, ICON, PARENT_MENU_ID, PAGE_ID, ORDER_NO, IS_SPREAD, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SH_DEAL_MANAGEMENT_001_001_008', 'ETR', null, 'SH_DEAL_MANAGEMENT_001_001', 'EtrExchangeAgreRepTrade001', 8, '02', '协议回购指令', 'system', null, TIMESTAMP '2021-06-04 17:05:05', 'system', TIMESTAMP '2021-09-16 00:00:00');

