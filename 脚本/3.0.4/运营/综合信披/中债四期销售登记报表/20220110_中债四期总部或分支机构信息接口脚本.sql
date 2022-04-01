delete from sys_batch_task  where task_id='3091';
Insert into sys_batch_task (TASK_ID,SYSTEM_CODE,TASK_NAME,FTASK_ID,BEAN_NAME,TASK_PRI,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,IS_BATCH_START_TARGET) 
values ('3091','SVR','总部或分支机构信息接入',null,'svrSaleInstInterfTaskService',3050,'system',null,sysdate,'system',sysdate,null);

delete  from  CHB_INTERF_CONFIG  where  INTERF_ID='CHB_SALER_INST_INFO';
Insert into CHB_INTERF_CONFIG (INTERF_ID,LAST_UPDATETIME,REMARK,INTERF_NAME,CONTENT_TYPE,CONTENT_SRC,REF_REPORT,SYS_SCOPE,STATUS) 
values ('CHB_SALER_INST_INFO',sysdate,'总部或分支机构信息接口数据',null,'I','T',null,null,'E');


DELETE  FROM  chb_datacheck_rule  WHERE DATATYPE='CHB_SALER_INST_INFO';
Insert into chb_datacheck_rule (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS) values ('CHB_SALER_INST_INFO-2022-0110-111','CHB_SALER_INST_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_INST_CODE',null,null,'N','销售机构代码不可为空。','E');
Insert into chb_datacheck_rule (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS) values ('CHB_SALER_INST_INFO-2022-0110-112','CHB_SALER_INST_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_ORG_CODE',null,null,'N','总部或分支机构编码不可为空。','E');
Insert into chb_datacheck_rule (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS) values ('CHB_SALER_INST_INFO-2022-0110-113','CHB_SALER_INST_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_ORG_CODE',null,'21','Y','总部或分支机构编码不能超过20字符。','E');
Insert into chb_datacheck_rule (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS) values ('CHB_SALER_INST_INFO-2022-0110-114','CHB_SALER_INST_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_ORG_NAME',null,null,'N','总部或分支机构名称不可为空。','E');
Insert into chb_datacheck_rule (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS) values ('CHB_SALER_INST_INFO-2022-0110-115','CHB_SALER_INST_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_ORG_NAME',null,'201','Y','总部或分支机构名称不能超过200字符。','E');
Insert into chb_datacheck_rule (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS) values ('CHB_SALER_INST_INFO-2022-0110-118','CHB_SALER_INST_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_ORG_LEVEL','==','SALER_STATUS','Y','机构层级不可为空或不在值域范围内。','E');
