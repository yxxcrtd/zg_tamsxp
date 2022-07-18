create table  CHB_AST_POS_SUB(
REGIST_SEQNO                  CHAR(36)     not null,
PROD_ID                       VARCHAR2(150),
ISSUE_PARTY_ID                VARCHAR2(50),
PRD_REGIST_CODE               VARCHAR2(20),
TERMID                        VARCHAR2(20),
VALUE_DATE                    date,
ASSET_ID                      VARCHAR2(100),
POS_TYPE                      VARCHAR2(2),
INTER_LEVE_NO                 VARCHAR2(2),
INTER_ASSET_TYPE              VARCHAR2(2),
INTER_ASSET_CODE              VARCHAR2(200),
SUBJECT_NAME                  VARCHAR2(100),
AMT                           NUMBER(36,8) ,
CNY_AMT                       NUMBER(36,8),
FLOAT_INGPL                   NUMBER(36,8),
CNY_FLOAT_INGPL               NUMBER(36,8),
UNIT_VALUE_CPRICE             NUMBER(36,8),
UNIT_VALUE_FPRICE             NUMBER(36,8),
QTY                           NUMBER(36,8),
CCY                           CHAR(3),
REMARK                        VARCHAR2(300),
BALANCE_AMT                   NUMBER(36,8),
VALUE_REG_DATE                date ,
SEND_STATUS                   VARCHAR2(2),
STATUS_DATE                   date,
STATUS                        VARCHAR2(2),
ERR_MSG                       clob ,
SUBMIT_SEQ                    VARCHAR2(50) ,
SUBMIT_FEEDBACK_MSG           VARCHAR2(50) ,
CHB_SUBMIT_DEADLINE           TIMESTAMP (6),
SUBMIT_DEADLINE               TIMESTAMP (6) ,
SUBMIT_TIME                   TIMESTAMP (6) ,
SUBMIT_FEEDBACK_STATUS        VARCHAR2(50) ,
CREATE_DEPT                   VARCHAR2(32) ,
CREATE_USER                   VARCHAR2(20) ,
CREATE_TIME                   TIMESTAMP (6) ,
UPDATE_USER                   VARCHAR2(20) ,
UPDATE_TIME                   TIMESTAMP (6),
CONSTRAINT CHB_AST_POS_SUB PRIMARY KEY ("REGIST_SEQNO")
);
   COMMENT ON COLUMN "CHB_AST_POS_SUB".REGIST_SEQNO           IS '登记序列';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".PROD_ID                IS '产品代码';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".ISSUE_PARTY_ID         IS '发行机构代码';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".PRD_REGIST_CODE        IS '产品登记编码';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".TERMID                 IS '期数';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".VALUE_DATE             IS '持仓日期';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".ASSET_ID               IS '行内资产/负债编码';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".POS_TYPE               IS '持仓类别';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".INTER_LEVE_NO          IS '中间层数';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".INTER_ASSET_TYPE       IS '资产穿透情况';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".INTER_ASSET_CODE       IS '中间层行内资产/负债编码';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".SUBJECT_NAME           IS '会计科目名称';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".AMT                    IS '金额';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".CNY_AMT                IS '折算人民币金额';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".FLOAT_INGPL            IS '公允价值';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".CNY_FLOAT_INGPL        IS '折算人民币公允价值';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".UNIT_VALUE_CPRICE      IS '单位估值（净价）';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".UNIT_VALUE_FPRICE      IS '单位估值（全价）';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".QTY                    IS '数量';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".CCY                    IS '币种';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".REMARK                 IS '备注';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".BALANCE_AMT            IS '余额';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".VALUE_REG_DATE         IS '持仓登记日期';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".SEND_STATUS            IS '报送状态';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".STATUS_DATE            IS '数据日期';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".STATUS                 IS '数据状态';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".ERR_MSG                IS '错误信息';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".SUBMIT_SEQ             IS '报送流水号';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".SUBMIT_FEEDBACK_MSG    IS '报送反馈消息';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".CHB_SUBMIT_DEADLINE    IS '报送截止日';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".SUBMIT_DEADLINE        IS '行内报送截止日';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".SUBMIT_TIME            IS '报送日期';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".SUBMIT_FEEDBACK_STATUS IS '反馈状态';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".CREATE_DEPT            IS '创建部门';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".CREATE_USER            IS '创建人';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".CREATE_TIME            IS '创建时间';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".UPDATE_USER            IS '更新人';
   COMMENT ON COLUMN "CHB_AST_POS_SUB".UPDATE_TIME            IS '更新时间';


delete from sys_menu  where MENU_ID='SVR_003_005_005';
INSERT INTO sys_menu (MENU_ID, SYSTEM_CODE, ICON, PARENT_MENU_ID, PAGE_ID, ORDER_NO, IS_SPREAD, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
VALUES ('SVR_003_005_005', 'SVR', null, 'SVR_003_005', 'SvrChbAstPosRegist001', 5, null, '资产持仓登记', 'system', null, sysdate, 'system', sysdate);

delete from sys_page  where PAGE_ID='SvrChbAstPosRegist001';
INSERT INTO sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
VALUES ('SvrChbAstPosRegist001', 'SVR', '/chb/astposregist/ChbAstPosRegist001', 'report/chb/astposregist/ChbAstPosRegist001', 'SVR_003_005_005', null, null, '02', '01', '资产持仓登记', 'system', null, sysdate, 'system', sysdate);

delete  from sys_page_element where PAGE_ID='SvrChbAstPosRegist001';
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_cancelManual', '01', '01', null, '取消手工处理', 'system', null, sysdate, 'system', sysdate, 'C');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_chbexport', '01', '01', null, '导出中债Excel', 'system', null, sysdate, 'system', sysdate, 'COM');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_checkData', '01', '01', null, '数据检查', 'system', null, sysdate, 'system', sysdate, 'C');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_manual', '01', '01', null, '手工处理', 'system', null, sysdate, 'system', sysdate, 'C');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_reSubmit', '01', '01', null, '重新报送', 'system', null, sysdate, 'system', sysdate, 'C');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_search', '01', '01', null, '查询', 'system', null, sysdate, 'system', sysdate, 'COM');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_submit', '01', '01', null, '报送', 'system', null, sysdate, 'system', sysdate, 'C');



delete from sys_datadict where DICT_CODE='SVR_POS_TYPE';
INSERT INTO sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SVR_POS_TYPE', 'SVR', '中债持仓类型', 'SVR_POS_TYPE', '02', 'system', null, sysdate, 'system', sysdate);
delete from sys_datadict_list where DICT_CODE='SVR_POS_TYPE';
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '01', '现金及活期存款', 1, '0', 'system', null, sysdate, 'system', sysdate, null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '02', '登记系统资产', 2, '0', 'system', null, sysdate, 'system', sysdate, null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '03', '登记资产负债', 3, '0', 'system', null, sysdate, 'system', sysdate, null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '04', '其他资产', 4, '0', 'system', null, sysdate, 'system', sysdate, null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '05', '其他负债', 5, '0', 'system', null, sysdate, 'system', sysdate, null);

delete from sys_datadict where DICT_CODE='SVR_INTER_ASSET_TYPE';
INSERT INTO sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SVR_INTER_ASSET_TYPE', 'SVR', '资产穿透情况', 'SVR_INTER_ASSET_TYPE', '02', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE='SVR_INTER_ASSET_TYPE';
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_INTER_ASSET_TYPE', '01', '中间层资产（首层）', 1, '0', 'system', null, sysdate, 'system', sysdate, null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_INTER_ASSET_TYPE', '02', '中间层资产（非首层', 2, '0', 'system', null, sysdate, 'system', sysdate, null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_INTER_ASSET_TYPE', '03', '最底层资产', 3, '0', 'system', null, sysdate, 'system', sysdate, null);


delete from  sys_datadict_list where DICT_CODE='SVR_REPORT_CODE' and ITEM_CODE in ('45','46');
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
VALUES ('SVR_REPORT_CODE', '45', '产品状态登记', 45, '0', 'system', null, sysdate, 'system', sysdate, null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
VALUES ('SVR_REPORT_CODE', '46', '资产持仓登记', 46, '0', 'system', null, sysdate, 'system', sysdate, null);


delete from sys_datadict_list where DICT_CODE='SVR_CHECK_INTERF_TYPE' and ITEM_CODE='CHB_AST_POS_SUB';
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_CHECK_INTERF_TYPE', 'CHB_AST_POS_SUB', '资产持仓登记', 21, '0', 'system', null, sysdate, 'system', sysdate, null);
