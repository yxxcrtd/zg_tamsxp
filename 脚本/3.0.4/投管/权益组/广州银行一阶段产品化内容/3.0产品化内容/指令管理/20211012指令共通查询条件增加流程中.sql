--银行间债券一级指令
DELETE FROM SYS_PAGE_DICTLIST WHERE PAGE_ID = 'EtrBondFirstLBiddingIb001' AND DICT_ID= 'ENTRUST_STATUS' AND DICT_ITEM_VAL = '08';
insert into SYS_PAGE_DICTLIST (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrBondFirstLBiddingIb001', '999999', 'ENTRUST_STATUS', '08', '', '', 'system', '', sysdate, 'system', sysdate, '');
--场内债券一级指令
DELETE FROM SYS_PAGE_DICTLIST WHERE PAGE_ID = 'etrbondfirstlbiddingex001' AND DICT_ID= 'ENTRUST_STATUS' AND DICT_ITEM_VAL = '08';
insert into SYS_PAGE_DICTLIST (page_id, sub_page_id, dict_id, dict_item_val, dict_item_val_cnname, dict_item_val_default, create_user, create_dept, create_time, update_user, update_time, institution_code)
values ('etrbondfirstlbiddingex001', '999999', 'ENTRUST_STATUS', '08', '', '', 'system', '', sysdate, 'system', sysdate, '');
--场内可转债申购
DELETE FROM SYS_PAGE_DICTLIST WHERE PAGE_ID = 'EtrConBond001' AND DICT_ID= 'ENTRUST_STATUS' AND DICT_ITEM_VAL = '08';
insert into SYS_PAGE_DICTLIST (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrConBond001', '999999', 'ENTRUST_STATUS', '08', '', '', 'system', '', sysdate, 'system', sysdate, '');
