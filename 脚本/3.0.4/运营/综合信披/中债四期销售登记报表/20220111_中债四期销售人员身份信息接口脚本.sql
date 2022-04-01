delete from sys_batch_task  where task_id='3092';
Insert into sys_batch_task (TASK_ID,SYSTEM_CODE,TASK_NAME,FTASK_ID,BEAN_NAME,TASK_PRI,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,IS_BATCH_START_TARGET) 
values ('3092','SVR','销售人员身份信息接入',null,'svrSaleIderInterfTaskService',3050,'system',null,sysdate,'system',sysdate,null);

delete  from  CHB_INTERF_CONFIG  where  INTERF_ID='CHB_SALER_IDEN_INFO';
Insert into CHB_INTERF_CONFIG (INTERF_ID,LAST_UPDATETIME,REMARK,INTERF_NAME,CONTENT_TYPE,CONTENT_SRC,REF_REPORT,SYS_SCOPE,STATUS) 
values ('CHB_SALER_IDEN_INFO',sysdate,'销售人员身份信息接口数据',null,'I','T',null,null,'E');


DELETE  FROM  chb_datacheck_rule  WHERE DATATYPE='CHB_SALER_IDEN_INFO';
Insert into chb_datacheck_rule (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-001','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_INS_NO',null,null,'N','内部编号不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-002','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_INS_NO',null,'61','Y','内部编号不能超过60字符。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-003','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_INST_CODE',null,null,'N','销售机构代码不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-004','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_NAME',null,null,'N','姓名不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-005','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_NAME',null,'201','Y','姓名不能超过200字符。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-006','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_SEX','==','SVR_SEX','N','性别不可为空或不在值域范围内。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-007','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_BIRTHDAY',null,null,'N','出生日期不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-008','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_CREDENT_TYPE','==','SALER_CREDENT_TYPE','N','证件类别不可为空或不在值域范围内。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-009','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_CREDENT_NO',null,null,'N','证件号码不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-010','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_CREDENT_NO',null,'31','Y','证件号码不能超过30字符。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-011','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_AREA_TYPE','==','SVR_BUY_PLACE','N','所属区域不可为空或不在值域范围内。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-012','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_ORG_CODE',null,null,'N','所属总部或分支机构不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-013','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_DEPT',null,null,'N','所属部门不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-014','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_DEPT',null,'41','Y','所属部门不能超过40字符。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-015','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_POSITION',null,null,'N','职位不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-016','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_POSITION',null,'41','Y','职位不能超过40字符。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-017','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_EDU_LEVEL','==','SALER_EDU_LEVEL','N','学历不可为空或不在值域范围内。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-018','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_DEGREE','==','SALER_DEGREE','N','学位不可为空或不在值域范围内。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-019','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_WORK_START_YMD',null,null,'N','首次参加工作时间不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-020','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_FINANCE_START_YMD',null,null,'N','首次从事金融工作时间不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-021','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_START_YMD',null,null,'N','首次从事理财销售时间不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-022','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_LEVLE','==','SALER_LEVLE','N','专业技术职称不可为空或不在值域范围内。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-023','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_CERTIFICATE_TYPE',null,null,'N','理财专业证书不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-024','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_RISK_LEVEL',null,null,'N','可销售产品风险等级不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-025','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_QUALIFICATION',null,null,'N','销售资质不可为空。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-026','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_QUALIFICATION',null,'401','Y','销售资质不能超过400字符。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-027','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_TEL',null,null,'N','办公电话不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE,CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-028','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'SALER_TEL', null, '^\d{3,4}-\d{7,8}(-\d{1,8})?', 'Y','办公电话填写格式错误。', 'E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-029','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_MOBILE',null,null,'N','移动电话不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE,CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-030','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'SALER_MOBILE', null, '^1(3|4|5|6|7|8|9)\d{9}$', 'Y','移动电话填写格式错误。', 'E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-031','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_MAIL',null,null,'N','邮箱不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE,CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-032','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'SALER_MAIL', null, '^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$', 'Y','邮箱填写格式错误。', 'E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-033','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_STATUS','==','SALER_STATUS','N','当前状态不可为空或不在值域范围内。','E');
Insert into CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('DDY_SALER_IDEN_INFO-2021-1119-034','CHB_SALER_IDEN_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','REMARK',null,'257','Y','备注不能超过256字符。','E');



delete from  sys_datadict_list  where dict_code='UPINTERF_FILE_TYPE' and item_code in('36','37');
Insert into sys_datadict_list (DICT_CODE,ITEM_CODE,ITEM_VALUE,ORDER_NO,DEFAULT_VALUE,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,INSTITUTION_CODE)
values ('UPINTERF_FILE_TYPE','36','总部或分支机构信息',36,'0','system',null,sysdate,'system',sysdate,null);
Insert into sys_datadict_list (DICT_CODE,ITEM_CODE,ITEM_VALUE,ORDER_NO,DEFAULT_VALUE,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,INSTITUTION_CODE)
values ('UPINTERF_FILE_TYPE','37','销售人员身份信息',37,'0','system',null,sysdate,'system',sysdate,null);
