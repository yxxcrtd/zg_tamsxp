select * from SYS_BATCH_TASK where TASK_ID='3118';

-- 销售结算资金交易数据自动检查报送批处理  增加
delete from SYS_BATCH_TASK where TASK_ID='3118';

INSERT INTO SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, IS_BATCH_START_TARGET) VALUES ('3118', 'SVR', '销售结算资金交易数据自动检查报送批处理', null, 'salesTransactionRegistAutoSendCBService', 3200, 'system', null, now(), 'system', now(), null);


-- 校验销售结算资金账号 白名单
delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000040' and DATATYPE='CHB_SALER_STL_DEAL_INFO';
INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000040', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist',
        'ACCOUNT_NO', null, '^[\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$',
        'Y',
        '销售结算资金账号只能填写白名单内的阿拉伯数字，英文字母，半角符号，全角符号。', 'E');


-- 销售结算资金账户名称  白名单
delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000041' and DATATYPE='CHB_SALER_STL_DEAL_INFO';
INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000041', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedString', 'ACCOUNT_NAME', null,
        '^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$',
        'Y', '销售结算资金账户名称必须填写白名单中的文字。', 'E');

-- 销售结算资金交易流水号  白名单
delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000042' and DATATYPE='CHB_SALER_STL_DEAL_INFO';

INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000042', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedString', 'TRADE_NO', null,
        '^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$',
        'Y', '销售结算资金交易流水号必须填写白名单中的文字。', 'E');


-- 销售结算资金交易对手方账号 白名单
delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000043' and DATATYPE='CHB_SALER_STL_DEAL_INFO';


INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000043', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist',
        'TRADE_COUNTER_ACC_NO', null,
        '^[\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$', 'Y',
        '销售结算资金交易对手方账号只能填写白名单内的阿拉伯数字，英文字母，半角符号，全角符号。', 'E');

-- 销售结算资金交易对手方账户名称 白名单

delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000044' and DATATYPE='CHB_SALER_STL_DEAL_INFO';

INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000044', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedString',
        'TRADE_COUNTER_ACC_NAME', null,
         '^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$',
        'Y', '销售结算资金交易对手方账户名称必须填写白名单中的文字。', 'E');


-- 销售结算资金交易对手方账户开户行名称 白名单
delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000045' and DATATYPE='CHB_SALER_STL_DEAL_INFO';

INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000045', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedString',
        'TRADE_COUNTER_ACC_BANK_NAME', null,
        '^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$',
        'Y', '销售结算资金交易对手方账户开户行名称必须填写白名单中的文字。', 'E');

-- 销售结算资金交易对手方账户开户行号 白名单

delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000046' and DATATYPE='CHB_SALER_STL_DEAL_INFO';

INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000046', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedString',
        'TRADE_COUNTER_ACC_BANK_NO', null,
        '^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$',
        'Y', '销售结算资金交易对手方账户开户行号必须填写白名单中的文字。', 'E');

-- 销售结算资金交易日期格式
delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000027' and DATATYPE='CHB_SALER_STL_DEAL_INFO';
INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000027', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isValidDate3', 'TRADE_VDATE', '==',
        'yyyy-MM-dd', 'Y', '销售结算资金交易日期必须为日期格式（yyyy-MM-dd）。', 'E');

-- 销售结算资金交易附言 白名单
delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000047' and DATATYPE='CHB_SALER_STL_DEAL_INFO';

INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000047', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedString',
        'POSTSCRIPT', null,
         '^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$',
        'Y', '销售结算资金交易附言必须填写白名单中的文字。', 'E');

-- 备注 白名单
delete from CHB_DATACHECK_RULE where CHECKRULEID='CHB_SALER_STL_DEAL_INFO-000000000048' and DATATYPE='CHB_SALER_STL_DEAL_INFO';

INSERT INTO CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE,
                                NULLABLE, REMARK, STATUS)
VALUES ('CHB_SALER_STL_DEAL_INFO-000000000048', 'CHB_SALER_STL_DEAL_INFO',
        'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedString',
        'REMARK', null,
        '^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$',
        'Y', '备注必须填写白名单中的文字。', 'E');


