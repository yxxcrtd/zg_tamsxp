delete from sys_datadict where DICT_CODE = 'EXCHANGE_TYPE' and SYSTEM_CODE = 'SYS';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EXCHANGE_TYPE', 'SYS', '交换机类型', 'EXCHANGE_TYPE', '02', 'system', null, sysdate, 'system', sysdate);
