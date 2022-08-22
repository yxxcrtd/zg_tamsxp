/**报送截止日配置**/
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE = '37';
insert into SYS_DATADICT_LIST VALUES ('SVR_REPORT_CODE','37','理财产品流动性风险应对情况月度统计表','37','0','system',null,now(),'system',now(),null);
DELETE FROM CHB_REG_TIME_POINTS WHERE CHB_REG_TIME_POINTS.SEQNO = '37';
INSERT INTO CHB_REG_TIME_POINTS VALUES('37',null,'37',null,'02',null,'01','每月的第N个工作日前','5','5','12','4','E',null,null,'qq2',now(),'system',now(),null);
/**批处理配置**/
DELETE FROM SYS_BATCH_TASK WHERE TASK_ID = '3023';
INSERT INTO SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('3023', 'SVR', '理财产品流动性风险应对情况月度统计表生成批处理', null, 'svrInLiquidityRiskInfoTaskService', 3300, 'system', null, now() , 'system', now());
/**登记页面菜单配置**/
delete from SYS_MENU where MENU_ID = 'REPORT_REGIST_001_001_015';
INSERT INTO SYS_MENU (MENU_ID, SYSTEM_CODE, ICON, PARENT_MENU_ID, PAGE_ID, ORDER_NO, IS_SPREAD, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('REPORT_REGIST_001_001_015', 'SVR', null, 'REPORT_REGIST_001_001', 'SvrLiquidityRiskRegist001', 14, null, '理财产品流动性风险应对情况月度统计表', 'system', null, now(), 'system', now());
delete from SYS_PAGE where PAGE_ID = 'SvrLiquidityRiskRegist001';
INSERT INTO SYS_PAGE (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SvrLiquidityRiskRegist001', 'SVR', '/chb/liquidityriskregist/LiquidityRiskRegist001', 'report/chb/liquidityriskregist/LiquidityRiskRegist001', 'REPORT_REGIST_001_001_015', null, null, '02', '01', '理财产品流动性风险应对情况月度统计表', 'system', null, now(), 'system', now());
/**联系人相关参数配置**/
delete from SYS_PARA where PARA_CODE = 'COUNTACT_NAME';
delete from SYS_PARA where PARA_CODE = 'COUNTACT_PHONE_NUM';
INSERT INTO SYS_PARA (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, UPDATE_FLG) VALUES ('SVR', 'COUNTACT_NAME', ' ', null, null, '联系人姓名', 'system', null, now(), 'system', now(), null, '02');
INSERT INTO SYS_PARA (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, UPDATE_FLG) VALUES ('SVR', 'COUNTACT_PHONE_NUM', ' ', null, null, '联系人电话', 'system', null, now(), 'system', now(), null, '02');
/**按钮配置**/
DELETE FROM SYS_PAGE_ELEMENT WHERE PAGE_ID = 'SvrLiquidityRiskRegist001';
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrLiquidityRiskRegist001','LiquidityRiskRegist001_btn_manual','01','01',null,'手工处理','system',null,now(),'system',now(),'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrLiquidityRiskRegist001','LiquidityRiskRegist001_btn_cancelManual','01','01',null,'取消手工处理','system',null,now(),'system',now(),'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrLiquidityRiskRegist001','LiquidityRiskRegist001_btn_chbexport','01','01',null,'导出中债EXCEL','system',null,now(),'system',now(),'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrLiquidityRiskRegist001','SvrLiquidityRiskRegist001_btn_search','01','01',null,'查询','system',null,now(),'system',now(),'C',null);