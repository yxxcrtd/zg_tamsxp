delete from sys_page_dictlist where page_id in ('IbuQuoteBond002') and DICT_ID='DISTRIBUTION';
insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('IbuQuoteBond002', '999999', 'DISTRIBUTION', '139', null, null, 'admin', null, sysdate, 'admin', sysdate, null);
insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('IbuQuoteBond002', '999999', 'DISTRIBUTION', '143', null, null, 'admin', null, sysdate, 'admin',sysdate, null);
