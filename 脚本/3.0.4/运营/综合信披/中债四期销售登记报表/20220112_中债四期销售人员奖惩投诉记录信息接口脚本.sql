delete from sys_batch_task  where task_id='3093';
Insert into sys_batch_task (TASK_ID,SYSTEM_CODE,TASK_NAME,FTASK_ID,BEAN_NAME,TASK_PRI,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,IS_BATCH_START_TARGET) 
values ('3093','SVR','销售人员奖惩投诉记录信息接口数据接入',null,'svrSaleIRewPenaInterfTaskService',3000,'system',null,sysdate,'system',sysdate,null);

delete  from  CHB_INTERF_CONFIG  where  INTERF_ID='CHB_SALER_REW_PENA_INFO';
Insert into CHB_INTERF_CONFIG (INTERF_ID,LAST_UPDATETIME,REMARK,INTERF_NAME,CONTENT_TYPE,CONTENT_SRC,REF_REPORT,SYS_SCOPE,STATUS) 
values ('CHB_SALER_REW_PENA_INFO',sysdate,'销售人员奖惩投诉记录信息接口数据',null,'I','T',null,null,'E');


DELETE  FROM  chb_datacheck_rule  WHERE DATATYPE='CHB_SALER_REW_PENA_INFO';
Insert into  CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('CHB_SALE_REW_PENA_INFO-2022-11dx-001','CHB_SALER_REW_PENA_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_INST_CODE',null,null,'N','销售机构代码不可为空。','E');
Insert into  CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('CHB_SALE_REW_PENA_INFO-2022-11dx-002','CHB_SALER_REW_PENA_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','SALER_INS_NO',null,null,'N','内部编号不可为空。','E');
Insert into  CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('CHB_SALE_REW_PENA_INFO-2022-11dx-003','CHB_SALER_REW_PENA_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','SALER_REW_PENA_TYEP','==','SALER_REW_PENA_TYEP','N','记录分类不可为空或不在值域范围内。','E');
Insert into  CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('CHB_SALE_REW_PENA_INFO-2022-11dx-004','CHB_SALER_REW_PENA_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_REW_PENA_NAME',null,'101','Y','奖惩名称不能超过100字符。','E');
Insert into  CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('CHB_SALE_REW_PENA_INFO-2022-11dx-005','CHB_SALER_REW_PENA_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_REW_PENA_COMMENTS',null,'401','Y','奖惩说明不能超过400字符。','E');
Insert into  CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('CHB_SALE_REW_PENA_INFO-2022-11dx-006','CHB_SALER_REW_PENA_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_REW_PENA_INST_NAME',null,'201','Y','奖惩单位具体名称不能超过200字符。','E');
Insert into  CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('CHB_SALE_REW_PENA_INFO-2022-11dx-007','CHB_SALER_REW_PENA_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_COMPLAIN_DETAIL',null,'401','Y','投诉详情不能超过400字符。','E');
Insert into  CHB_DATACHECK_RULE (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS)
values ('CHB_SALE_REW_PENA_INFO-2022-11dx-008','CHB_SALER_REW_PENA_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','SALER_COMPLAIN_RESULT',null,'401','Y','投诉处理结果不能超过400字符。','E');


delete from  sys_datadict_list  where dict_code='UPINTERF_FILE_TYPE' and item_code in('38');
Insert into sys_datadict_list (DICT_CODE,ITEM_CODE,ITEM_VALUE,ORDER_NO,DEFAULT_VALUE,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,INSTITUTION_CODE)
values ('UPINTERF_FILE_TYPE','38','销售人员奖惩投诉记录信息表',38,'0','system',null,sysdate,'system',sysdate,null);