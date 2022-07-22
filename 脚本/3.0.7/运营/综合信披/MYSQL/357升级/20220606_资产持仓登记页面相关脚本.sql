drop  table   if exists CHB_AST_POS_SUB;
create table  CHB_AST_POS_SUB(
REGIST_SEQNO                  CHAR(36)   not null comment   '登记序列' ,
PROD_ID                       VARCHAR(150)   null comment   '产品代码' ,
ISSUE_PARTY_ID                VARCHAR(50)    null comment   '发行机构代码' ,
PRD_REGIST_CODE               VARCHAR(20)    null comment   '产品登记编码' ,
TERMID                        VARCHAR(20)    null comment   '期数' ,
VALUE_DATE                    datetime       null comment   '持仓日期',
ASSET_ID                      VARCHAR(100)   null comment   '行内资产/负债编码' ,
POS_TYPE                      VARCHAR(2)     null comment   '持仓类别' ,
INTER_LEVE_NO                 VARCHAR(2)     null comment   '中间层数' ,
INTER_ASSET_TYPE              VARCHAR(2)     null comment   '资产穿透情况' ,
INTER_ASSET_CODE              VARCHAR(200)   null comment   '中间层行内资产/负债编码' ,
SUBJECT_NAME                  VARCHAR(100)   null comment   '会计科目名称' ,
AMT                           decimal(36,8)  null comment   '金额',
CNY_AMT                       decimal(36,8)  null comment   '折算人民币金额',
FLOAT_INGPL                   decimal(36,8)  null comment   '公允价值',
CNY_FLOAT_INGPL               decimal(36,8)  null comment   '折算人民币公允价值',
UNIT_VALUE_CPRICE             decimal(36,8)  null comment   '单位估值（净价）',
UNIT_VALUE_FPRICE             decimal(36,8)  null comment   '单位估值（全价）',
QTY                           decimal(36,8)  null comment   '数量',
CCY                           CHAR(3)        null comment   '币种' ,
REMARK                        VARCHAR(300)   null comment   '备注' ,
BALANCE_AMT                   decimal(36,8)  null comment   '余额',
VALUE_REG_DATE                datetime       null comment   '持仓登记日期',
SEND_STATUS                   VARCHAR(2)     null comment   '报送状态' ,
STATUS_DATE                   datetime       null comment   '数据日期',
STATUS                        VARCHAR(2)     null comment   '数据状态' ,
ERR_MSG                       longtext       null comment   '错误信息' ,
SUBMIT_SEQ                    VARCHAR(50)    null comment   '报送流水号' ,
SUBMIT_FEEDBACK_MSG           VARCHAR(50)    null comment   '报送反馈消息' ,
CHB_SUBMIT_DEADLINE           datetime       null comment   '报送截止日',
SUBMIT_DEADLINE               datetime       null comment   '行内报送截止日',
SUBMIT_TIME                   datetime       null comment   '报送日期',
SUBMIT_FEEDBACK_STATUS        VARCHAR(50)    null comment   '反馈状态' ,
CREATE_DEPT                   VARCHAR(32)    null comment   '创建部门' ,
CREATE_USER                   VARCHAR(20)    null comment   '创建人' ,
CREATE_TIME                   datetime       null comment   '创建时间',
UPDATE_USER                   VARCHAR(20)    null comment   '更新人' ,
UPDATE_TIME                   datetime       null comment   '更新时间',
primary key (REGIST_SEQNO)

)COMMENT='资产持仓登记表'
COLLATE='utf8_bin';


delete from sys_menu  where MENU_ID='REPORT_REGIST_001_001_014';
INSERT INTO sys_menu (MENU_ID, SYSTEM_CODE, ICON, PARENT_MENU_ID, PAGE_ID, ORDER_NO, IS_SPREAD, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
VALUES ('REPORT_REGIST_001_001_014', 'SVR', null, 'REPORT_REGIST_001_001', 'SvrChbAstPosRegist001', 10, null, '资产持仓登记', 'system', null, '2021-04-08 09:50:08', 'system', '2021-04-08 09:50:08');

delete from sys_page  where PAGE_ID='SvrChbAstPosRegist001';
INSERT INTO sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
VALUES ('SvrChbAstPosRegist001', 'SVR', '/chb/astposregist/ChbAstPosRegist001', 'report/chb/astposregist/ChbAstPosRegist001', 'REPORT_REGIST_001_001_014', null, null, '02', '01', '资产持仓登记', 'system', null, '2021-04-08 09:59:45', 'system', '2021-04-08 09:59:45');

delete  from sys_page_element where PAGE_ID='SvrChbAstPosRegist001';
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_cancelManual', '01', '01', null, '取消手工处理', 'system', null, '2020-07-03 16:26:39', 'system', '2020-07-03 16:26:39', 'C');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_chbexport', '01', '01', null, '导出中债Excel', 'system', null, '2020-07-03 16:26:39', 'system', '2020-07-03 16:26:39', 'COM');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_checkData', '01', '01', null, '数据检查', 'system', null, '2020-07-03 16:26:39', 'system', '2020-07-03 16:26:39', 'C');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_manual', '01', '01', null, '手工处理', 'system', null, '2020-07-03 16:26:39', 'system', '2020-07-03 16:26:39', 'C');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_reSubmit', '01', '01', null, '重新报送', 'system', null, '2022-02-23 15:21:24', 'system', '2022-02-23 15:21:24', 'C');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_search', '01', '01', null, '查询', 'system', null, '2020-07-03 16:26:39', 'system', '2020-07-03 16:26:39', 'COM');
INSERT INTO sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, ADMIN_FLAG) VALUES ('SvrChbAstPosRegist001', 'SvrChbAstPosRegist001_btn_submit', '01', '01', null, '报送', 'system', null, '2022-02-23 15:21:24', 'system', '2022-02-23 15:21:24', 'C');



delete from sys_datadict where DICT_CODE='SVR_POS_TYPE';
INSERT INTO sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SVR_POS_TYPE', 'SVR', '中债持仓类型', 'SVR_POS_TYPE', '02', 'system', null, '2021-03-30 20:46:55', 'system', '2021-03-30 20:46:55');
delete from sys_datadict_list where DICT_CODE='SVR_POS_TYPE';
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '01', '现金及活期存款', 1, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '02', '登记系统资产', 2, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '03', '登记资产负债', 3, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '04', '其他资产', 4, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_POS_TYPE', '05', '其他负债', 5, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);

delete from sys_datadict where DICT_CODE='SVR_INTER_ASSET_TYPE';
INSERT INTO sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('SVR_INTER_ASSET_TYPE', 'SVR', '资产穿透情况', 'SVR_INTER_ASSET_TYPE', '02', 'system', null, '2021-03-30 20:46:55', 'system', '2021-03-30 20:46:55');

delete from sys_datadict_list where DICT_CODE='SVR_INTER_ASSET_TYPE';
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_INTER_ASSET_TYPE', '01', '中间层资产（首层）', 1, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_INTER_ASSET_TYPE', '02', '中间层资产（非首层', 2, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_INTER_ASSET_TYPE', '03', '最底层资产', 3, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);


delete from  sys_datadict_list where DICT_CODE='SVR_REPORT_CODE' and ITEM_CODE in ('45','46');
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
VALUES ('SVR_REPORT_CODE', '45', '产品状态登记', 45, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
VALUES ('SVR_REPORT_CODE', '46', '资产持仓登记', 46, '0', 'system', null, '2019-03-05 00:00:00', 'system', '2019-03-05 00:00:00', null);


delete from sys_datadict_list where DICT_CODE='SVR_CHECK_INTERF_TYPE' and ITEM_CODE='CHB_AST_POS_SUB';
INSERT INTO sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE) VALUES ('SVR_CHECK_INTERF_TYPE', 'CHB_AST_POS_SUB', '资产持仓登记', 21, '0', 'system', null, '2022-02-23 15:21:22', 'system', '2022-02-23 15:21:22', null);
