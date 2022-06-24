-- check校验
INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-01','ChbBokSubject002','lessThanCharLength','subjectNo',NULL,'32','N','科目号不可为空,且长度不能超过32个字符','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-02','ChbBokSubject002','lessThanCharLength','subjectName',NULL,'200','N','科目名称不可为空,且长度不能超过200个字符','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-03','ChbBokSubject002','isNotEmpty','fsubjectId',NULL,NULL,'N','上级科目号不可为空','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-04','ChbBokSubject002','inDatadict','subjectType','==','SUBJECT_TYPE','N','科目类型不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-05','ChbBokSubject002','inDatadict','subjectLevel','==','SUBJECT_LEVEL','N','科目级别不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-06','ChbBokSubject002','inDatadict','balFlag','==','BAL_FLAG','N','科目方向不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
