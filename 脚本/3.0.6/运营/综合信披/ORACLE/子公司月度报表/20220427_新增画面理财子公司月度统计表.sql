/**
 * 新增画面：理财子公司月度统计表
 */
delete from SYS_PAGE where PAGE_ID = 'SvrSubsidiaMonthReport001';
INSERT INTO SYS_PAGE (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) 
VALUES ('SvrSubsidiaMonthReport001', 'SVR', '/chb/subsidiamonthreport/ChbSubsidiaMonthReport001', 'report/chb/subsidiamonthreport/ChbSubsidiaMonthReport001', 'REPORT_003005002', null, null, '01', '01', '理财子公司月度统计表', 'system', null, TO_TIMESTAMP('2019-02-27 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'system', TO_TIMESTAMP('2019-02-27 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
