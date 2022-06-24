DELETE FROM CHB_DATACHECK_RULE_INV WHERE CHECKRULEID LIKE 'BOK_SUBJECT-KMDY-20220507%';

-- check校验
INSERT INTO CHB_DATACHECK_RULE_INV  VALUES ('BOK_SUBJECT-KMDY-20220507-0001-01','ChbBokSubject002','lessThanCharLength','subjectNo',NULL,'33','N','科目号不可为空,且长度不能超过32个字符','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-02','ChbBokSubject002','lessThanCharLength','subjectName',NULL,'201','N','科目名称不可为空,且长度不能超过200个字符','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-03','ChbBokSubject002','isNotEmpty','fsubjectId',NULL,NULL,'N','上级科目号不可为空','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-04','ChbBokSubject002','inDatadict','subjectType','==','SUBJECT_TYPE','N','科目类型不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-05','ChbBokSubject002','inDatadict','subjectLevel','==','SUBJECT_LEVEL','N','科目级别不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-06','ChbBokSubject002','inDatadict','balFlag','==','BAL_FLAG','N','科目方向不可为空，且必须在值域范围内','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');


INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-07','ChbBokSubject002','isCustomizedString','subjectNo',NULL,'^[\u4e00-\u9fa5\ue815-\ue864\uff01-\uff5e\uffe0-\uffe5\u3000-\u3003\u3005-\u3017\u301d-\u301e\u2010\u2013-\u2016\u2018-\u2019\u201c-\u201d\u2025-\u2026\u2030\u2032-\u2033\u2035\u203b\u00b7\u0030-\u0039\u0041-\u005a\u0061-\u007a\u2160-\u216b\u0020-\u0026\u0028-\u007B\u007D\u007E]{0,}$','Y','科目号必须填写白名单中的文字','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-08','ChbBokSubject002','isCustomizedString','subjectName',NULL,'^[\u4e00-\u9fa5\ue815-\ue864\uff01-\uff5e\uffe0-\uffe5\u3000-\u3003\u3005-\u3017\u301d-\u301e\u2010\u2013-\u2016\u2018-\u2019\u201c-\u201d\u2025-\u2026\u2030\u2032-\u2033\u2035\u203b\u00b7\u0030-\u0039\u0041-\u005a\u0061-\u007a\u2160-\u216b\u0020-\u0026\u0028-\u007B\u007D\u007E]{0,}$','Y','科目名称必须填写白名单中的文字','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

INSERT INTO CHB_DATACHECK_RULE_INV VALUES ('BOK_SUBJECT-KMDY-20220507-0001-09','ChbBokSubject002','isCustomizedString','remark',NULL,'^[\u4e00-\u9fa5\ue815-\ue864\uff01-\uff5e\uffe0-\uffe5\u3000-\u3003\u3005-\u3017\u301d-\u301e\u2010\u2013-\u2016\u2018-\u2019\u201c-\u201d\u2025-\u2026\u2030\u2032-\u2033\u2035\u203b\u00b7\u0030-\u0039\u0041-\u005a\u0061-\u007a\u2160-\u216b\u0020-\u0026\u0028-\u007B\u007D\u007E]{0,}$','Y','备注必须填写白名单中的文字','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');

