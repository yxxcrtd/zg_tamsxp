delete from sys_para where PARA_CODE='SVR_HX_ISUPGRADE';
INSERT INTO sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
VALUES ('SVR', 'SVR_HX_ISUPGRADE', '02', null, '01,02', '投资者明细是否升级报送：01 是，02否', 'system', null, sysdate, 'system', sysdate);
