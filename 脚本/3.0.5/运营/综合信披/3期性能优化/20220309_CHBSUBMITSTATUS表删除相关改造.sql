ALTER TABLE CHB_EQU_SUB ADD submit_feedback_status VARCHAR2(10);
ALTER TABLE CHB_EQU_SUB ADD report_no              VARCHAR2(20);
ALTER TABLE CHB_EQU_SUB ADD submit_feedback_msg    VARCHAR2(600);
comment on column CHB_EQU_SUB.submit_feedback_status
  is '报送反馈状态';
comment on column CHB_EQU_SUB.report_no
  is '报送编码';
comment on column CHB_EQU_SUB.submit_feedback_msg
  is '报送反馈消息';

ALTER TABLE CHB_EQU_SUB_HIS ADD submit_feedback_status VARCHAR2(10);
ALTER TABLE CHB_EQU_SUB_HIS ADD report_no              VARCHAR2(20);
ALTER TABLE CHB_EQU_SUB_HIS ADD submit_feedback_msg    VARCHAR2(600);
comment on column CHB_EQU_SUB_HIS.submit_feedback_status
  is '报送反馈状态';
comment on column CHB_EQU_SUB_HIS.report_no
  is '报送编码';
comment on column CHB_EQU_SUB_HIS.submit_feedback_msg
  is '报送反馈消息';

ALTER TABLE CHB_INVESTHOLD_INFO_SUB ADD submit_feedback_status VARCHAR2(10);
ALTER TABLE CHB_INVESTHOLD_INFO_SUB ADD report_no              VARCHAR2(20);
ALTER TABLE CHB_INVESTHOLD_INFO_SUB ADD submit_feedback_msg    VARCHAR2(600);
comment on column CHB_INVESTHOLD_INFO_SUB.submit_feedback_status
  is '报送反馈状态';
comment on column CHB_INVESTHOLD_INFO_SUB.report_no
  is '报送编码';
comment on column CHB_INVESTHOLD_INFO_SUB.submit_feedback_msg
  is '报送反馈消息';

ALTER TABLE CHB_INVESTHOLD_INFO_SUB_HIS ADD submit_feedback_status VARCHAR2(10);
ALTER TABLE CHB_INVESTHOLD_INFO_SUB_HIS ADD report_no              VARCHAR2(20);
ALTER TABLE CHB_INVESTHOLD_INFO_SUB_HIS ADD submit_feedback_msg    VARCHAR2(600);
comment on column CHB_INVESTHOLD_INFO_SUB_HIS.submit_feedback_status
  is '报送反馈状态';
comment on column CHB_INVESTHOLD_INFO_SUB_HIS.report_no
  is '报送编码';
comment on column CHB_INVESTHOLD_INFO_SUB_HIS.submit_feedback_msg
  is '报送反馈消息';




CREATE OR REPLACE VIEW CHB_VIEW_EQU_SUB AS
(SELECT
              INDENTIFY_MARK,
              ORG_INDENTIFY_MARK,
              REGIST_BANK_CODE,
              IS_BELONG_BANK,
              THE_BANK_NAME,
              THE_BANK_CODE,
              HOLDER_MARK,
              HOLDER_REGION,
              HOLDER_TYPE,
              PERSONAL_IDENTIFY_TYPE,
              INSTITUTION_IDENTIFY_TYPE,
              OTHER_IDENTIFY_NAME,
              IDENTIFY_NUMBER,
              SPV_CAP_ACC_BANK,
              OTHER_CAP_ACC_BANK,
              SPV_REGIST_CODE,
              HOLDER_DATE,
              HOLDER_NAME,
              HOLDER_SEX,
              HOLDER_RISK_PREFER,
              HOLDER_CELLPHONE,
              HOLDER_TELEPHONE,
              HOLDER_EMAIL,
              TYPE,
              REMARK,
              SUBMIT_STATUS,
              SUBMIT_TIME,
              CHB_SUBMIT_DEADLINE,
              SUBMIT_DEADLINE,
              STATUS,
              DEPT_CODE,
              UPDATE_USER,
              UPDATE_TIME,
              CREATE_USER,
              CREATE_TIME,
              SUMIT_SEQ,
              DELETE_RES,
              ORG_CODE,
              CREATE_DEPT,
              CHECK_SEQNO,
        FUND_ACC_PLACE,
        HOLDER_CLASSIFY,
        submit_feedback_status,
        report_no,
        submit_feedback_msg
          FROM CHB_EQU_SUB
          WHERE SUBMIT_STATUS IN ('02','03')
          UNION ALL
            SELECT
              INDENTIFY_MARK,
              ORG_INDENTIFY_MARK,
              REGIST_BANK_CODE,
              IS_BELONG_BANK,
              THE_BANK_NAME,
              THE_BANK_CODE,
              HOLDER_MARK,
              HOLDER_REGION,
              HOLDER_TYPE,
              PERSONAL_IDENTIFY_TYPE,
              INSTITUTION_IDENTIFY_TYPE,
              OTHER_IDENTIFY_NAME,
              IDENTIFY_NUMBER,
              SPV_CAP_ACC_BANK,
              OTHER_CAP_ACC_BANK,
              SPV_REGIST_CODE,
              HOLDER_DATE,
              HOLDER_NAME,
              HOLDER_SEX,
              HOLDER_RISK_PREFER,
              HOLDER_CELLPHONE,
              HOLDER_TELEPHONE,
              HOLDER_EMAIL,
              TYPE,
              REMARK,
              SUBMIT_STATUS,
              SUBMIT_TIME,
              CHB_SUBMIT_DEADLINE,
              SUBMIT_DEADLINE,
              STATUS,
              DEPT_CODE,
              UPDATE_USER,
              UPDATE_TIME,
              CREATE_USER,
              CREATE_TIME,
              SUMIT_SEQ,
              DELETE_RES,
              ORG_CODE,
              CREATE_DEPT,
              CHECK_SEQNO,
        FUND_ACC_PLACE,
        HOLDER_CLASSIFY,
        submit_feedback_status,
        report_no,
        submit_feedback_msg
          FROM CHB_EQU_SUB_HIS
            WHERE SUBMIT_STATUS IN ('02', '03'));
