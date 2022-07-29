/** add by shenpengfei on 20220516 产品发行登记 start */
ALTER TABLE  CHB_PRD_ISSUE_SUB ADD PERIODIC_INTERVAL_DAYS  DECIMAL(30,4) DEFAULT NULL COMMENT '定期开放周期 (天)';

ALTER TABLE  CHB_PRD_ISSUE_SUB_HIS ADD PERIODIC_INTERVAL_DAYS  DECIMAL(30,4) DEFAULT NULL COMMENT '定期开放周期 (天)';

ALTER TABLE  CHB_PRD_ISSUE_SUB_TMP ADD PERIODIC_INTERVAL_DAYS  DECIMAL(30,4) DEFAULT NULL COMMENT '定期开放周期 (天)';
/** add by shenpengfei on 20220516 产品发行登记 end */

/**产品发行登记-新增字段:定期开放周期(天) 界面配置**/
delete FROM chb_infomgr_result_config WHERE  mgrtable_id ='CHB_PRD_ISSUE_SUB_TMP';
INSERT INTO chb_infomgr_result_config VALUES ('8d25fdc8-d3e5-44f5-a4a0-dadd05269dcd', 'CHB_PRD_ISSUE_SUB_TMP', 'PROD_REGIST_CODE', '产品登记编码', '', 1, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('d0f6097a-bf97-4f25-a1e2-73d8c7532e0a', 'CHB_PRD_ISSUE_SUB_TMP', 'PROD_ID', '理财产品代码', '', 2, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('d70c1e19-0f0c-41e2-9a6b-9dce13fbeb92', 'CHB_PRD_ISSUE_SUB_TMP', 'ISSUE_PARTY_ID', '发行机构代码', '', 3, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('2bd6fdf1-ea50-498c-b750-4c6674733bc8', 'CHB_PRD_ISSUE_SUB_TMP', 'RAISE_VDATE', '募集起始日期', '', 4, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('664a54d8-870d-40d3-bd0e-39bad25f255c', 'CHB_PRD_ISSUE_SUB_TMP', 'RAISE_ENDDATE', '募集结束日期', '', 5, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('c9c56768-8954-454b-8062-b757d56cf852', 'CHB_PRD_ISSUE_SUB_TMP', 'PRD_VDATE', '产品起始日期', '', 6, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('3b21707e-9532-44e4-90db-2b17457b292c', 'CHB_PRD_ISSUE_SUB_TMP', 'PRD_MDATE', '产品终止日期', '', 7, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('6fb546d3-3cf3-49e9-a6db-a0c66acbbb77', 'CHB_PRD_ISSUE_SUB_TMP', 'MANAGE_TYPE', '管理方式', 'SVR_PRO_STYLE', 8, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('e6a081d7-057d-4ef7-a0b7-fe8c8c5703db', 'CHB_PRD_ISSUE_SUB_TMP', 'IS_CONSTRACTE', '是否为结构化（分级）产品', 'YES_NO', 9, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('30b1550a-8e60-4fba-b26e-72b6696d6ffc', 'CHB_PRD_ISSUE_SUB_TMP', 'GRAD_PROPORTION', '分级比例', '', 10, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('7cdea2df-7cbb-4626-a396-43d576c0ca68', 'CHB_PRD_ISSUE_SUB_TMP', 'YIELD_HIGH', '业绩比较基准上限%', '', 11, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('98757a99-e2d3-4632-970f-06f1f56d0ddc', 'CHB_PRD_ISSUE_SUB_TMP', 'YIELD_LOW', '业绩比较基准下限%', '', 12, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('b233de53-3326-4ec2-b643-8e8f65b7a522', 'CHB_PRD_ISSUE_SUB_TMP', 'YIELD_REMARK', '业绩比较基准说明', '', 13, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('6c3c339f-7f3e-4232-a46e-bcfd92e93b24', 'CHB_PRD_ISSUE_SUB_TMP', 'OPEN_MODE', '开放模式', 'SVR_OPEN_MODE', 14, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('d0ea179a-0902-4ce8-af7a-10cd489027ab', 'CHB_PRD_ISSUE_SUB_TMP', 'OPEN_CIRCLE', '规律开放周期', 'SVR_REGULAR_OPEN_CYCLE', 15, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('2f22b20b-e5fa-4220-b5d2-a57be71b4597', 'CHB_PRD_ISSUE_SUB_TMP', 'OPEN_CIRCLE_DAYS', '其他规律开放周期 (天)', '', 16, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('9b2d2e08-64c3-4ef0-2022-202205160001', 'CHB_PRD_ISSUE_SUB_TMP', 'PERIODIC_INTERVAL_DAYS', '定期开放周期 (天)', '', 17, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('fc62ffcd-c790-45da-9c7e-607d65a500da', 'CHB_PRD_ISSUE_SUB_TMP', 'RULE_COMMENT', '无规律开放说明', '', 18, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('9b2d2e08-64c3-4ef0-bfad-053bc0e0d130', 'CHB_PRD_ISSUE_SUB_TMP', 'FIRST_OPEN_DAY', '首次开放周期起始日', '', 19, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('354f5e63-2fa9-40d1-84ea-496dab014832', 'CHB_PRD_ISSUE_SUB_TMP', 'HOLIDAY_ON_FLAG', '节假日是否开放', 'YES_NO', 20, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('1adc8cff-bd19-4071-902c-5827793a3a7c', 'CHB_PRD_ISSUE_SUB_TMP', 'AVG_OPEN_COUNT', '平均开放次数（年化）', '', 21, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('6de52644-5711-4ef6-82f0-c193f996aea3', 'CHB_PRD_ISSUE_SUB_TMP', 'OPEN_BUSS', '开放期业务', 'SVR_OPEN_BUSS', 22, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('f56e221c-a832-4a71-99f5-83081353d856', 'CHB_PRD_ISSUE_SUB_TMP', 'OPEN_BUSS_REMARK', '开放期业务说明', '', 23, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('0fa37537-3f68-40c4-9c82-23242e7000c2', 'CHB_PRD_ISSUE_SUB_TMP', 'ROW_DATE', '数据日期', '', 24, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('ce8f369d-5c8c-4bda-9906-bdd0faad0b44', 'CHB_PRD_ISSUE_SUB_TMP', 'DATA_SOURCE', '数据来源', 'SVR_DATA_SOURCE', 25, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('250e4312-cf81-409c-8794-0621016785f5', 'CHB_PRD_ISSUE_SUB_TMP', 'STATUS', '数据状态', 'SVR_STATUS', 26, 'Y', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('436fb51a-8c49-44b3-ab5f-8d87ca5a763b', 'CHB_PRD_ISSUE_SUB_TMP', 'ORG_CODE', '机构代码', '', 27, 'N', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('1596e295-c401-4a6f-b42a-4c789798dda3', 'CHB_PRD_ISSUE_SUB_TMP', 'DEPT_CODE', '部门代码', '', 28, 'N', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('2bbcfe3f-6086-4608-ae29-f9792de41786', 'CHB_PRD_ISSUE_SUB_TMP', 'CREATE_USER', '创建人', '', 29, 'N', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('3a12199a-27e8-4edd-95d4-9d6106fe8b63', 'CHB_PRD_ISSUE_SUB_TMP', 'CREATE_DEPT', '创建部门', '', 30, 'N', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('93a5f1ec-3f3e-4a25-9af4-96b6a3047ecb', 'CHB_PRD_ISSUE_SUB_TMP', 'CREATE_TIME', '创建时间', '', 31, 'N', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('99fe904a-5418-4f06-beee-408cfa107bbb', 'CHB_PRD_ISSUE_SUB_TMP', 'UPDATE_USER', '更新人', '', 32, 'N', NULL);
INSERT INTO chb_infomgr_result_config VALUES ('8c4170dc-66b1-42fc-8da4-87f3bfeca226', 'CHB_PRD_ISSUE_SUB_TMP', 'UPDATE_TIME', '更新时间', '', 33, 'N', NULL);

/**产品发行登记-新增字段:定期开放周期(天) 校验规则**/
DELETE FROM chb_datacheck_rule WHERE checkruleid IN('ys8E4A49-6813-5A9E-2022-202205160001','ys8E4A49-6813-5A9E-2022-202205160002','ys214A49-6813-5A9E-2022-202205160003');
DELETE FROM chb_datacheck_condition WHERE checkconditionid ='ys214A49-6813-5A9E-2022-20220516000A';
INSERT INTO chb_datacheck_rule VALUES ('ys8E4A49-6813-5A9E-2022-202205160001', 'CHB_PRD_ISSUE_SUB', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isNumber', 'PERIODIC_INTERVAL_DAYS', NULL, '4,0', 'Y', '定期开放周期(天)必须为n..4格式。', 'E');
INSERT INTO chb_datacheck_rule VALUES ('ys8E4A49-6813-5A9E-2022-202205160002', 'CHB_PRD_ISSUE_SUB', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'compareValueAsNumber', 'PERIODIC_INTERVAL_DAYS', '>=', '0', 'Y', '定期开放周期(天)必须大于等于0。', 'E');
INSERT INTO chb_datacheck_condition VALUES ('ys214A49-6813-5A9E-2022-20220516000A', 'ys214A49-6813-5A9E-2022-202205160003', 'compareValue', 'OPEN_MODE', '==', '01', '当开放模式为有规律开放', 'E');
INSERT INTO chb_datacheck_rule VALUES ('ys214A49-6813-5A9E-2022-202205160003', 'CHB_PRD_ISSUE_SUB', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isNotEmpty', 'PERIODIC_INTERVAL_DAYS', NULL, NULL, 'N', '定期开放周期(天)要素必填。', 'E');

/**产品基本信息接口-新增字段:定期开放周期(天) 校验规则**/
DELETE FROM chb_datacheck_rule WHERE checkruleid IN('ys8E4A49-6813-info-2022-202205160001','ys8E4A49-6813-info-2022-202205160002','ys214A49-6813-info-2022-202205160003');
DELETE FROM chb_datacheck_condition WHERE checkconditionid ='ys214A49-6813-info-2022-20220516000A';
INSERT INTO chb_datacheck_rule VALUES ('ys8E4A49-6813-info-2022-202205160001', 'CHB_PRD_BASEINFO', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isNumber', 'FIT_CIRCLE_DAYS', NULL, '4,0', 'Y', '定期开放周期(天)必须为n..4格式。', 'E');
INSERT INTO chb_datacheck_rule VALUES ('ys8E4A49-6813-info-2022-202205160002', 'CHB_PRD_BASEINFO', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'compareValueAsNumber', 'FIT_CIRCLE_DAYS', '>=', '0', 'Y', '定期开放周期(天)必须大于等于0。', 'E');
INSERT INTO chb_datacheck_condition VALUES ('ys214A49-6813-info-2022-20220516000A', 'ys214A49-6813-info-2022-202205160003', 'compareValue', 'OPEN_MODE', '==', '01', '当开放模式为有规律开放', 'E');
INSERT INTO chb_datacheck_rule VALUES ('ys214A49-6813-info-2022-202205160003', 'CHB_PRD_BASEINFO', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isNotEmpty', 'FIT_CIRCLE_DAYS', NULL, NULL, 'N', '定期开放周期(天)要素必填。', 'E');


/**产品基本信息页面-新增字段:定期开放周期(天) 校验规则**/
DELETE FROM chb_datacheck_rule_inv WHERE checkruleid IN('CHB_PRD_BASEINFO-CPJBXX-20220716-0001-01','CHB_PRD_BASEINFO-CPJBXX-20220716-0001-02','CHB_PRD_BASEINFO-CPJBXX-20220716-0001-03');
DELETE FROM chb_datacheck_condition_inv WHERE checkconditionid ='CONDITION-CPJBXX-20220716-0001-01';
Insert into chb_datacheck_rule_inv (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_PRD_BASEINFO-CPJBXX-20220716-0001-01','CchbPrdBaseInfo002','isNumber2','fitCircleDays',null,'8,5','Y','定期开放周期(天)必须为n(4,0)格式。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into chb_datacheck_rule_inv (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_PRD_BASEINFO-CPJBXX-20220716-0001-02','CchbPrdBaseInfo002','compareValueAsNumber','fitCircleDays','>=','0','Y','定期开放周期(天)必须大于等于0。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into chb_datacheck_condition_inv (CHECKCONDITIONID,CHECKRULEID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,REMARK,STATUS)
values ('CONDITION-CPJBXX-20220716-0001-01','CHB_PRD_BASEINFO-CPJBXX-20220716-0001-03','inArray','openMode','==','01','当开放模式选择 “01有规律开放”时','E');
Insert into chb_datacheck_rule_inv (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('CHB_PRD_BASEINFO-CPJBXX-20220716-0001-03','CchbPrdBaseInfo002','isNotEmpty','fitCircleDays',null,null,'N','定期开放周期(天)要素必填；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
