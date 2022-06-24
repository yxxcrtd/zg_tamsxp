
UPDATE SYS_PAGE SET IS_NEED_WORKFLOW = '01' WHERE PAGE_ID = 'SvrPerProDeclareInfo001';





DELETE FROM SYS_PAGE WHERE PAGE_ID = 'SvrPerProDeclareInfo003';
INSERT INTO SYS_PAGE (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
VALUES ('SvrPerProDeclareInfo003', 'REP', '/basicdata/perprodeclareinfo/SvrPerProDeclareInfo003', 'basicdata/perprodeclareinfo/SvrPerProDeclareInfo003', null, null, null, '02', '02', '产品申报登记信息附件上传', 'system', null, CURRENT_DATE, 'system', CURRENT_DATE);


DELIMITER ??
DROP PROCEDURE IF EXISTS schema_change??
CREATE PROCEDURE schema_change()
BEGIN
IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_schema = DATABASE()  AND table_name = 'CHB_PRD_DECLARE_INFO_ERRODATA' AND column_name = 'PROD_INS_ID') THEN

ALTER TABLE CHB_PRD_DECLARE_INFO_ERRODATA ADD PROD_INS_ID VARCHAR(100) COMMENT '行内标识码';

END IF;
IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_schema = DATABASE()  AND table_name = 'CHB_PRD_DECLARE_INFO' AND column_name = 'FILE_NO') THEN

ALTER TABLE CHB_PRD_DECLARE_INFO ADD FILE_NO VARCHAR(600) COMMENT '附件编号';

END IF;
IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_schema = DATABASE()  AND table_name = 'CHB_PRD_DECLARE_INFO' AND column_name = 'FILE_NUM') THEN

ALTER TABLE CHB_PRD_DECLARE_INFO ADD FILE_NUM DECIMAL(30,4) COMMENT '附件个数';

END IF;

IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_schema = DATABASE()  AND table_name = 'CHB_PRD_DECLARE_INFO' AND column_name = 'TEN_FILE') THEN

ALTER TABLE CHB_PRD_DECLARE_INFO ADD TEN_FILE TEXT COMMENT '十大附件';

END IF;

END??
DELIMITER ;

CALL schema_change();