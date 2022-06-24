/**
 * 新增画面：理财子公司月度统计表
 */
delete from SYS_PAGE where PAGE_ID = 'SvrSubsidiaMonthReport001';
INSERT INTO sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
VALUES ('SvrSubsidiaMonthReport001', 'SVR', '/chb/subsidiamonthreport/ChbSubsidiaMonthReport001', 'report/chb/subsidiamonthreport/ChbSubsidiaMonthReport001', 'SVR_003_006_010', null, null, '01', '01', '理财子公司月度报表登记', 'system', null, '2021-05-13 10:30:20', 'system', '2021-05-13 10:30:20');
