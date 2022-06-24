-- check校验
delete from CHB_DATACHECK_RULE_INV where CHECKRULEID like 'MST_BOND_CREDIT_RATING-ZQPJ-20220507%';
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('MST_BOND_CREDIT_RATING-ZQPJ-20220507-0001-01','ChbMstBondCreditRating002','isNotEmpty','oCode',NULL,NULL,'N','债券代码不可为空','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('MST_BOND_CREDIT_RATING-ZQPJ-20220507-0001-02','ChbMstBondCreditRating002','inDatadict','oType','==','MAIN_GRADE_TYPE','N','评级对象不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('MST_BOND_CREDIT_RATING-ZQPJ-20220507-0001-03','ChbMstBondCreditRating002','inDatadict','gradeOrgId','==','GRADE_PARTY','N','评级机构不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('MST_BOND_CREDIT_RATING-ZQPJ-20220507-0001-04','ChbMstBondCreditRating002','inDatadict','shortLongTerm','==','LONG_SHORT_TERM','N','评级类型不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('MST_BOND_CREDIT_RATING-ZQPJ-20220507-0001-05','ChbMstBondCreditRating002','inDatadict','gradeResult','==','MAIN_GRADE_RESULT','N','评级结果不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('MST_BOND_CREDIT_RATING-ZQPJ-20220507-0001-06','ChbMstBondCreditRating002','inDatadict','source','==','GRADE_SOURCE_TYPE','N','评级来源不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('MST_BOND_CREDIT_RATING-ZQPJ-20220507-0001-07','ChbMstBondCreditRating002','isValidDate','gradeDate',NULL,'yyyy-MM-dd','N','评级日期不可为空，且必须为日期格式（YYYY-MM-DD）','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
