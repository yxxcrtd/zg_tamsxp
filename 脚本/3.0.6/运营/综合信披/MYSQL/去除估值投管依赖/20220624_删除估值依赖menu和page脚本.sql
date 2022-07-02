-- 删除非综合信披菜单
delete from sys_menu where MENU_ID = 'PRODUCT_001';
delete from sys_menu where MENU_ID = 'DEAL_001';
delete from sys_menu where MENU_ID = 'ASSET_MANAGEMENT_001';
delete from sys_menu where MENU_ID = 'VALUATION_001';
delete from sys_menu where MENU_ID = 'ACCOUTING_001';
delete from sys_menu where MENU_ID = 'INFOSEARCH_001';
delete from SYS_MENU where MENU_ID in('SYSTEM_001_002_003','SYSTEM_001_002_006');
-- 修改综合信披批处理启动类
delete from SYS_MENU where MENU_ID = 'SYSTEM_001_003_003';
INSERT INTO SYS_MENU (MENU_ID, SYSTEM_CODE, ICON, PARENT_MENU_ID, PAGE_ID, ORDER_NO, IS_SPREAD, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SYSTEM_001_003_003', 'SYS', null, 'SYSTEM_001_003', 'SysBatchStart001', 6, '02', '批处理启动', 'system', null, TIMESTAMP '2021-06-04 17:05:05', 'system', TIMESTAMP '2021-09-16 00:00:00');
delete from SYS_PAGE where PAGE_ID = 'SysBatchStart001';
INSERT INTO SYS_PAGE (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SysBatchStart001', 'SYS', '/system/batch/batchstart/SysBatchStart001', 'system/batch/batchstart/SysBatchStart001', 'SYSTEM_001_003_003', null, null, '01', '02', '批处理启动', 'system', null, TIMESTAMP '2019-05-24 00:00:00', 'system', TIMESTAMP '2021-09-16 00:00:00');
-- 删除不属于综合信披菜单
delete from sys_menu where MENU_ID = 'PRODUCT_001';
delete from sys_menu where MENU_ID = 'DEAL_001';
delete from sys_menu where MENU_ID = 'ASSET_MANAGEMENT_001';
delete from sys_menu where MENU_ID = 'VALUATION_001';
delete from sys_menu where MENU_ID = 'ACCOUTING_001';
delete from sys_menu where MENU_ID = 'INFOSEARCH_001';