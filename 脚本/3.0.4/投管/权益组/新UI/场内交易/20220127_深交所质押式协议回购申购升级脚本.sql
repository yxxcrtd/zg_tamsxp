----交易单位增加手
delete from sys_datadict_list where DICT_CODE = 'UNIT' and ITEM_CODE = '04';
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('UNIT', '04', '手', 4, '0', 'system', null, sysdate, 'system', sysdate, null);

----交易方向新增首期
delete from sys_datadict_list where DICT_CODE = 'PS' and ITEM_CODE = 'T92';
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PS', 'T92', '首期', 110, '0', 'system', null, sysdate, 'system', sysdate, null);

----增加EtrExchangeAgreRepTrade001的字典过滤
DELETE FROM sys_page_dictlist WHERE PAGE_ID = 'EtrExchangeAgreRepTrade001' AND DICT_ID = 'PS' and DICT_ITEM_VAL IN ('T92','T56','T93','T94','T95','T96');
insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T92', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T56', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T93', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T94', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T95', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T96', null, null, 'system', null, sysdate, 'system', sysdate, null);

DELETE FROM sys_page_dictlist WHERE PAGE_ID = 'EtrExchangeAgreRepTrade001' AND DICT_ID = 'TRADE_PLACE' and DICT_ITEM_VAL IN ('03','04');
insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'TRADE_PLACE', '03', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'TRADE_PLACE', '04', null, null, 'system', null, sysdate, 'system', sysdate, null);

DELETE FROM sys_page_dictlist WHERE PAGE_ID = 'EtrExchangeAgreRepTrade001' AND DICT_ID = 'BACK_LEND_DIRECTION' and DICT_ITEM_VAL IN ('03','04');
insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'BACK_LEND_DIRECTION', '03', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'BACK_LEND_DIRECTION', '04', null, null, 'system', null, sysdate, 'system', sysdate, null);

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

