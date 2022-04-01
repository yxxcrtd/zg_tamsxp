--协议回购新增审批pageid
delete from sys_page where PAGE_ID = 'EtrExchangeAgreRepTrade002';
insert into sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeAgreRepTrade002', 'ETR', '/etrexchange/agreementrep/EtrExchangeAgreRepTrade002', 'etrexchange/agreementrep/EtrExchangeAgreRepTrade002', null, null, null, '02', '01', '协议回购指令页面', 'system', null, sysdate, 'system', sysdate);

DELETE FROM SYS_WFL_BUSINESS_MODULE where PAGE_ID='EtrExchangeAgreRepTrade002'  and ELEMENT_ID='EtrExchangeAgreRepTrade002_btn_submit';
INSERT INTO SYS_WFL_BUSINESS_MODULE(PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG)
VALUES ('EtrExchangeAgreRepTrade002', 'EtrExchangeAgreRepTrade002_btn_submit', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate, 'ALL', '02');

DELETE FROM SYS_WFL_BUSINESS_MODULE where PAGE_ID='EtrExchangeAgreRepTrade002'  and ELEMENT_ID='EtrExchangeAgreRepTrade002_btn_save';
INSERT INTO SYS_WFL_BUSINESS_MODULE(PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG)
VALUES ('EtrExchangeAgreRepTrade002', 'EtrExchangeAgreRepTrade002_btn_save', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate, 'ALL', '02');

----修改EtrExchangeAgreRepTrade001的字典过滤
DELETE FROM sys_page_dictlist WHERE PAGE_ID = 'EtrExchangeAgreRepTrade001' AND DICT_ID = 'PS';
insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', '03', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T56', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T93', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T94', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T96', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T92', null, null, 'system', null, sysdate, 'system', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeAgreRepTrade001', '999999', 'PS', 'T95', null, null, 'system', null, sysdate, 'system', sysdate, null);