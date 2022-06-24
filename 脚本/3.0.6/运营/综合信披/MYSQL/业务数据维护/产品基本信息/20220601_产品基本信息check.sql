UPDATE CHB_DATACHECK_RULE_INV SET STATUS = 'D' WHERE CHECKRULEID = 'CHB_PRD_BASEINFO-CPJBXX-20220514-0001-33';


-- check前置条件
delete from CHB_DATACHECK_CONDITION_INV where CHECKCONDITIONID like 'CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601%';

INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-01','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-01','inArray','operateMode','==','03,04','当产品运作模式选择“开放式净值型”和“开放式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-02','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-02','inArray','operateMode','==','01,02','当产品运作模式选择“封闭式净值型”和“封闭式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-03','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-03','inArray','operateMode','==','03,04','当产品运作模式选择“开放式净值型”和“开放式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-04','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-04','inArray','operateMode','==','01,02','当产品运作模式选择“封闭式净值型”和“封闭式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-05','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-05','inArray','operateMode','==','03,04','当产品运作模式选择“开放式净值型”和“开放式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-06','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-06','inArray','operateMode','==','03,04','当产品运作模式选择“开放式净值型”和“开放式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-07','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-07','inArray','operateMode','==','01,02','当产品运作模式选择“封闭式净值型”和“封闭式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-08','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-08','inArray','operateMode','==','03,04','当产品运作模式选择“开放式净值型”和“开放式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-09','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-09','inArray','operateMode','==','01,02','当产品运作模式选择“封闭式净值型”和“封闭式非净值型”时，','E');
INSERT INTO CHB_DATACHECK_CONDITION_INV VALUES ('CHB_PRD_BASEINFO_CONDITION-CPJBXX-20220601-0001-10','CHB_PRD_BASEINFO-CPJBXX-20220601-0001-10','inArray','cashManaCate','==','02','当是否现金管理类选择否时，','E');

-- check校验
delete  from CHB_DATACHECK_RULE_INV where CHECKRULEID like 'CHB_PRD_BASEINFO-CPJBXX-20220601%';

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-01','CchbPrdBaseInfo002','isNotEmpty','firstOpenDay',NULL,NULL,'N','首次开放周期起始日要素必填；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-02','CchbPrdBaseInfo002','lessThanCharLength','firstOpenDay','==','0','Y','首次开放周期起始日要素必须为空；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-03','CchbPrdBaseInfo002','isNotEmpty','holidayOnFlag',NULL,NULL,'N','节假日是否开放要素必填；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-04','CchbPrdBaseInfo002','lessThanCharLength','holidayOnFlag','==','0','Y','节假日是否开放要素必须为空；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-05','CchbPrdBaseInfo002','compareValueAsNumber','avgOpenCount','>','0','N','平均开放次数（年化）数字必须大于0且不能超过366；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-06','CchbPrdBaseInfo002','compareValueAsNumber','avgOpenCount','<=','366','N','平均开放次数（年化）数字必须大于0且不能超过366；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-07','CchbPrdBaseInfo002','lessThanCharLength','avgOpenCount','==','0','Y','平均开放次数（年化）要素必须为空；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-08','CchbPrdBaseInfo002','inDatadict','openBuss','==','SVR_OPEN_BUSS','N','开放期业务要素必填，且必须在值域范围内；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-09','CchbPrdBaseInfo002','lessThanCharLength','openBuss','==','0','Y','开放期业务要素必须为空；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('CHB_PRD_BASEINFO-CPJBXX-20220601-0001-10','CchbPrdBaseInfo002','lessThanCharLength','manaCateFit','==','0','Y','现金管理类产品-是否完全符合要素必须为空；','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
