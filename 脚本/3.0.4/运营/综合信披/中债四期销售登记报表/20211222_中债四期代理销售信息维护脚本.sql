--代理销售机构信息表创建
drop table CHB_AGENT_INSTITUTION_INFO;
create table CHB_AGENT_INSTITUTION_INFO(
  AGENT_INSTITUTION_CODE	VARCHAR2(32) NOT NULL
  ,AGENT_INSTITUTION_NAME	VARCHAR2(80)
  ,ISSUE_PARTY_ID	VARCHAR2(6) NOT NULL
  ,STATUS	VARCHAR2(2)
  ,ENCLOSURE_NUM	VARCHAR2(2)
  ,ENCLOSURE_ADDRESS	VARCHAR2(200)
  ,ENCLOSURE_CHANGE_FLAG VARCHAR2(2)
  ,FILE_NO VARCHAR2(600)
  ,DATA_DATE	DATE
  ,REMARK	VARCHAR2(200)
  ,CREATE_DEPT	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_USER	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(32)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_AGENT_INSTITUTION_INFO is '代理销售机构信息表';
comment on column CHB_AGENT_INSTITUTION_INFO.AGENT_INSTITUTION_CODE is '代销机构代码 ';
comment on column CHB_AGENT_INSTITUTION_INFO.AGENT_INSTITUTION_NAME is '代销机构名称 ';
comment on column CHB_AGENT_INSTITUTION_INFO.ISSUE_PARTY_ID is '发行机构代码 ';
comment on column CHB_AGENT_INSTITUTION_INFO.STATUS is '数据状态 ';
comment on column CHB_AGENT_INSTITUTION_INFO.ENCLOSURE_NUM is '附件个数 ';
comment on column CHB_AGENT_INSTITUTION_INFO.ENCLOSURE_ADDRESS is '附件存放目录（包括文件名称）';
comment on column CHB_AGENT_INSTITUTION_INFO.ENCLOSURE_CHANGE_FLAG is '附件修改标志';
comment on column CHB_AGENT_INSTITUTION_INFO.FILE_NO is '附件代码';
comment on column CHB_AGENT_INSTITUTION_INFO.DATA_DATE is '数据日期 ';
comment on column CHB_AGENT_INSTITUTION_INFO.REMARK is '备注 ';
comment on column CHB_AGENT_INSTITUTION_INFO.CREATE_DEPT is '创建部门 ';
comment on column CHB_AGENT_INSTITUTION_INFO.ORG_CODE is '机构代码 ';
comment on column CHB_AGENT_INSTITUTION_INFO.CREATE_USER is '创建人 ';
comment on column CHB_AGENT_INSTITUTION_INFO.CREATE_TIME is '创建时间 ';
comment on column CHB_AGENT_INSTITUTION_INFO.UPDATE_USER is '更新人 ';
comment on column CHB_AGENT_INSTITUTION_INFO.UPDATE_TIME is '更新时间 ';
alter table CHB_AGENT_INSTITUTION_INFO
  add primary key (AGENT_INSTITUTION_CODE);

  --委托销售产品编码信息表创建
drop table CHB_AGENT_PRD_INFO;
create table CHB_AGENT_PRD_INFO(
   AGENT_INSTITUTION_CODE VARCHAR2(32) NOT NULL
  ,ISSUE_PARTY_ID	VARCHAR2(6) NOT NULL
  ,PROD_ID   VARCHAR2(150)
  ,PROD_NAME VARCHAR2(300)
  ,PROD_REGIST_CODE VARCHAR2(50)
  ,STATUS	VARCHAR2(2)
  ,DATA_DATE	DATE
  ,REMARK	VARCHAR2(200)
  ,CREATE_DEPT	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_USER	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(32)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_AGENT_PRD_INFO is '委托销售产品编码信息表';
comment on column CHB_AGENT_PRD_INFO.AGENT_INSTITUTION_CODE is '代销机构代码 ';
comment on column CHB_AGENT_PRD_INFO.ISSUE_PARTY_ID is '发行机构代码 ';
comment on column CHB_AGENT_PRD_INFO.PROD_ID is '理财产品代码 ';
comment on column CHB_AGENT_PRD_INFO.PROD_NAME is '理财产品名称 ';
comment on column CHB_AGENT_PRD_INFO.PROD_REGIST_CODE is '产品登记编码 ';
comment on column CHB_AGENT_PRD_INFO.STATUS is '数据状态';
comment on column CHB_AGENT_PRD_INFO.DATA_DATE is '数据日期 ';
comment on column CHB_AGENT_PRD_INFO.REMARK is '备注 ';
comment on column CHB_AGENT_PRD_INFO.CREATE_DEPT is '创建部门 ';
comment on column CHB_AGENT_PRD_INFO.ORG_CODE is '机构代码 ';
comment on column CHB_AGENT_PRD_INFO.CREATE_USER is '创建人 ';
comment on column CHB_AGENT_PRD_INFO.CREATE_TIME is '创建时间 ';
comment on column CHB_AGENT_PRD_INFO.UPDATE_USER is '更新人 ';
comment on column CHB_AGENT_PRD_INFO.UPDATE_TIME is '更新时间 ';
alter table CHB_AGENT_PRD_INFO
  add primary key (AGENT_INSTITUTION_CODE,PROD_REGIST_CODE);
  
  --菜单配置
DELETE FROM SYS_PAGE WHERE PAGE_ID in ('AgentTabManage001','AgentInstitution001','AgentInstitution002','AgentInstitution003','AgentPrdInfo001','AgentPrdInfo002');
delete from SYS_MENU where MENU_ID = 'REPORT_MAINTAIN_001_004_008';
INSERT INTO SYS_MENU (MENU_ID,SYSTEM_CODE,ICON,PARENT_MENU_ID,PAGE_ID,ORDER_NO,IS_SPREAD,REMARK,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME)values('REPORT_MAINTAIN_001_004_008','REP',null,'REPORT_MAINTAIN_001_004','AgentTabManage001',8,02,'代理销售信息维护','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('AgentTabManage001','REP','/report/submitmaintenance/agenttabmanage/AgentTabManage001','report/submitmaintenance/agenttabmanage/AgentTabManage001','REPORT_MAINTAIN_001_004_008',null,null,'02','01','代理销售信息维护','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('AgentInstitution001','REP','/report/submitmaintenance/agentinstitution/AgentInstitution001','report/submitmaintenance/agentinstitution/AgentInstitution001',null,null,null,'02','01','代理销售机构信息','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('AgentInstitution002','REP','/report/submitmaintenance/agentinstitution/AgentInstitution002','report/submitmaintenance/agentinstitution/AgentInstitution002',null,null,null,'02','01','代理销售机构信息新建','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('AgentInstitution003','REP','/report/submitmaintenance/agentinstitution/AgentInstitution003','report/submitmaintenance/agentinstitution/AgentInstitution003',null,null,null,'02','01','附件上传','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('AgentPrdInfo001','REP','/report/submitmaintenance/agentprdinfo/AgentPrdInfo001','report/submitmaintenance/agentprdinfo/AgentPrdInfo001',null,null,null,'02','01','委托销售产品编码信息','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('AgentPrdInfo002','REP','/report/submitmaintenance/agentprdinfo/AgentPrdInfo002','report/submitmaintenance/agentprdinfo/AgentPrdInfo002',null,null,null,'02','01','委托销售产品编码信息新建','system',null,sysdate,'system',sysdate);

--按钮工作流配置
DELETE FROM SYS_WFL_BUSINESS_MODULE WHERE PAGE_ID = 'AgentPrdInfo001';
DELETE FROM SYS_WFL_BUSINESS_MODULE WHERE PAGE_ID = 'AgentPrdInfo002';
DELETE FROM SYS_WFL_BUSINESS_MODULE WHERE PAGE_ID = 'AgentInstitution001';
DELETE FROM SYS_WFL_BUSINESS_MODULE WHERE PAGE_ID = 'AgentInstitution002';
DELETE FROM SYS_WFL_BUSINESS_MODULE WHERE PAGE_ID = 'AgentInstitution003';
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('AgentInstitution003','AgentInstitution003_btn_submit','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('AgentInstitution002','AgentInstitution001_btn_delete','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('AgentInstitution002','AgentInstitution002_btn_submit','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('AgentInstitution001','AgentInstitution001_btn_delete','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('AgentPrdInfo002','AgentPrdInfo001_btn_delete','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('AgentPrdInfo002','AgentPrdInfo002_btn_submit','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('AgentPrdInfo001','AgentPrdInfo001_btn_delete','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
DELETE FROM SYS_PAGE_ELEMENT WHERE PAGE_ID = 'AgentInstitution001';
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentInstitution001','AgentInstitution001_btn_add','01','01',null,'新建','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentInstitution001','AgentInstitution001_btn_delete','01','01',null,'删除','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentInstitution001','AgentInstitution001_btn_import','01','01',null,'导入','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentInstitution001','AgentInstitution001_btn_importFile','01','01',null,'销售文件上传','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentInstitution001','AgentInstitution001_btn_reset','01','01',null,'重置','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentInstitution001','AgentInstitution001_btn_search','01','01',null,'查询','system',null,sysdate,'system',sysdate,'C',null);
DELETE FROM SYS_PAGE_ELEMENT WHERE PAGE_ID = 'AgentPrdInfo001';
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentPrdInfo001','AgentPrdInfo001_btn_add','01','01',null,'新建','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentPrdInfo001','AgentPrdInfo001_btn_delete','01','01',null,'删除','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentPrdInfo001','AgentPrdInfo001_btn_import','01','01',null,'导入','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentPrdInfo001','AgentPrdInfo001_btn_importFile','01','01',null,'销售文件上传','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentPrdInfo001','AgentPrdInfo001_btn_reset','01','01',null,'重置','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('AgentPrdInfo001','AgentPrdInfo001_btn_search','01','01',null,'查询','system',null,sysdate,'system',sysdate,'C',null);

--代理销售机构信息新建check
DELETE FROM CHB_DATACHECK_RULE_INV WHERE PAGE_ID = 'AgentInstitution002';
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_INSTITUTION_INFO-2021-1119-001','AgentInstitution002','isNotEmpty','issuePartyId',null,null,'N','发行机构代码不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_INSTITUTION_INFO-2021-1119-002','AgentInstitution002','isNotEmpty','agentInstitutionCode',null,null,'N','代理销售机构代码不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
insert into CHB_DATACHECK_RULE_INV (CHECKRULEID, PAGE_ID, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS, CHECKCLASS)
values ('CHB_AGENT_INSTITUTION_INFO-2021-1119-003', 'AgentInstitution002', 'isCustomizedStringForWhitelist', 'agentInstitutionCode', null, '^([CZ][0-9]{5})$', 'Y','代理销售机构代码填写错误。', 'E', 'com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_INSTITUTION_INFO-2021-1119-004','AgentInstitution002','isNotEmpty','agentInstitutionName',null,null,'N','代销机构名称不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_INSTITUTION_INFO-2021-1119-005','AgentInstitution002','lessThanCharLength','agentInstitutionName',null,'61','Y','代销机构名称不能超过80字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_INSTITUTION_INFO-2021-1119-006','AgentInstitution002','lessThanCharLength','remark',null,'61','Y','备注不能超过200字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

--委托销售产品编码信息新建check
DELETE FROM CHB_DATACHECK_RULE_INV WHERE PAGE_ID = 'AgentPrdInfo002';
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_PRD_INFO-2021-1119-001','AgentPrdInfo002','isNotEmpty','issuePartyId',null,null,'N','发行机构代码不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_PRD_INFO-2021-1119-002','AgentPrdInfo002','isNotEmpty','agentInstitutionCode',null,null,'N','代理销售机构代码不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_PRD_INFO-2021-1119-004','AgentPrdInfo002','isNotEmpty','prodRegistCode',null,null,'N','产品登记编码不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_AGENT_PRD_INFO-2021-1119-005','AgentPrdInfo002','lessThanCharLength','remark',null,'61','Y','备注不能超过200字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

--批处理配置
DELETE FROM SYS_BATCH_TASK WHERE TASK_ID = '3061';
insert into SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, IS_BATCH_START_TARGET)
values ('3061', 'SVR', '代理销售信息登记报表生成', null, 'SvrAgentManageTaskService', 3200, 'system', null, sysdate, 'system', sysdate, null);

--代理销售信息登记获取报送截止日配置
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE = '40';
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_CHECK_INTERF_TYPE' and ITEM_CODE = 'CHB_AGENT_INSTITUTION_SUB';
insert into SYS_DATADICT_LIST VALUES ('SVR_REPORT_CODE','40','代理销售机构信息登记','40','0','system',null,sysdate,'system',sysdate,null);
insert into SYS_DATADICT_LIST VALUES ('SVR_CHECK_INTERF_TYPE','CHB_AGENT_INSTITUTION_SUB','代理销售机构信息登记','20','0','system',null,sysdate,'system',sysdate,null);
DELETE FROM CHB_REG_TIME_POINTS WHERE CHB_REG_TIME_POINTS.SEQNO = '40';
INSERT INTO CHB_REG_TIME_POINTS VALUES('40',null,'40',null,'03','03','02','业务发生后N个工作日','1','1','12','4','E',null,null,'qq2',sysdate,'system',sysdate,null);

DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE = '41';
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_CHECK_INTERF_TYPE' and ITEM_CODE = 'CHB_AGENT_PRD_SUB';
insert into SYS_DATADICT_LIST VALUES ('SVR_REPORT_CODE','41','委托销售产品编码信息登记','41','0','system',null,sysdate,'system',sysdate,null);
insert into SYS_DATADICT_LIST VALUES ('SVR_CHECK_INTERF_TYPE','CHB_AGENT_PRD_SUB','委托销售产品编码信息登记','20','0','system',null,sysdate,'system',sysdate,null);
DELETE FROM CHB_REG_TIME_POINTS WHERE CHB_REG_TIME_POINTS.SEQNO = '41';
INSERT INTO CHB_REG_TIME_POINTS VALUES('41',null,'41',null,'03','03','02','业务发生后N个工作日','1','1','12','4','E',null,null,'qq2',sysdate,'system',sysdate,null);


--20220106代理销售机构信息数据状态枚举值
DELETE FROM SYS_DATADICT WHERE DICT_CODE = 'AGENT_STATUS';
insert into SYS_DATADICT (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('AGENT_STATUS', 'SYS', '代理销售机构信息数据状态', 'AGENT_STATUS', '02', 'system', null, SYSDATE, 'system', SYSDATE);
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'AGENT_STATUS';
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('AGENT_STATUS', 'D', '删除', 1, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('AGENT_STATUS', 'E', '有效', 2, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);