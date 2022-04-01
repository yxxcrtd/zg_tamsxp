DELETE FROM sys_datadict_list where DICT_CODE = 'BUSI_SCENE_CALSS' AND ITEM_CODE in('060102','060103');
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('BUSI_SCENE_CALSS', '060102', '股票-新股中签-确认', 165, '0', 'system', null, sysdate, 'system', sysdate);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('BUSI_SCENE_CALSS', '060103', '股票-新股中签-通知', 166, '0', 'system', null, sysdate, 'system', sysdate);

