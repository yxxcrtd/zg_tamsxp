/* -------------------------------- CHB_EQU_HOLD -----------------------*/
drop index CHB_EQU_HOLD_UNIQUE_INDEX;
drop index I_CHB_EQU_HOLD_1;
drop index I_CHB_EQU_HOLD_2;
drop index I_CHB_EQU_HOLD_3;

CREATE INDEX
    I_CHB_EQU_HOLD_1
ON
    CHB_EQU_HOLD
    (
        REGIST_BANK_CODE
    );
  
CREATE INDEX
    I_CHB_EQU_HOLD_2
ON
    CHB_EQU_HOLD
    (
        INDENTIFY_MARK
    );

CREATE INDEX
    I_CHB_EQU_HOLD_3
ON
    CHB_EQU_HOLD
    (
        STATUS
    );

CREATE INDEX
    CHB_EQU_HOLD_UNIQUE_INDEX
ON
    CHB_EQU_HOLD
    (
        REGIST_BANK_CODE,
        IDENTIFY_NUMBER,
        HOLDER_NAME,
        PERSONAL_IDENTIFY_TYPE,
        INSTITUTION_IDENTIFY_TYPE,
        OTHER_IDENTIFY_NAME
    );

/* -------------------------------- CHB_EQU_SUB -----------------------*/
drop index CHB_EQU_HOLD_INDEX_BANK;
drop index CHB_EQU_HOLD_INDEX_INSTITUTION;
drop index CHB_EQU_HOLD_INDEX_NAME;
drop index CHB_EQU_HOLD_INDEX_NUMBER;
drop index CHB_EQU_HOLD_INDEX_OTHER;
drop index CHB_EQU_HOLD_INDEX_PERSONAL;
drop index CHB_EQU_HOLD_INDEX_SUB;
drop index CHB_EQU_SUB_UNIQUE_INDEX;
drop index I_CHB_EQU_SUB_1;
drop index I_CHB_EQU_SUB_2;
drop index I_EQU_BATCH;

CREATE INDEX
    CHB_EQU_HOLD_INDEX_BANK
ON
    CHB_EQU_SUB
    (
        REGIST_BANK_CODE
    );

CREATE INDEX
    CHB_EQU_HOLD_INDEX_INSTITUTION
ON
    CHB_EQU_SUB
    (
        INSTITUTION_IDENTIFY_TYPE
    );

CREATE INDEX
    CHB_EQU_HOLD_INDEX_NAME
ON
    CHB_EQU_SUB
    (
        HOLDER_NAME
    );

CREATE INDEX
    CHB_EQU_HOLD_INDEX_NUMBER
ON
    CHB_EQU_SUB
    (
        IDENTIFY_NUMBER
    );

CREATE INDEX
    CHB_EQU_HOLD_INDEX_OTHER
ON
    CHB_EQU_SUB
    (
        OTHER_IDENTIFY_NAME
    );

CREATE INDEX
    CHB_EQU_HOLD_INDEX_PERSONAL
ON
    CHB_EQU_SUB
    (
        PERSONAL_IDENTIFY_TYPE
    );

CREATE INDEX
    CHB_EQU_HOLD_INDEX_SUB
ON
    CHB_EQU_SUB
    (
        SUMIT_SEQ
    );

CREATE INDEX
    CHB_EQU_SUB_UNIQUE_INDEX
ON
    CHB_EQU_SUB
    (
        REGIST_BANK_CODE,
        IDENTIFY_NUMBER,
        HOLDER_NAME,
        PERSONAL_IDENTIFY_TYPE,
        INSTITUTION_IDENTIFY_TYPE,
        OTHER_IDENTIFY_NAME
    );

CREATE INDEX
    I_CHB_EQU_SUB_1
ON
    CHB_EQU_SUB
    (
        REGIST_BANK_CODE,
        INDENTIFY_MARK
    );

CREATE INDEX
    I_CHB_EQU_SUB_2
ON
    CHB_EQU_SUB
    (
        SUBMIT_STATUS
    );

CREATE INDEX
    I_EQU_BATCH
ON
    CHB_EQU_SUB
    (
        SUBMIT_STATUS,
        STATUS,
        CHECK_SEQNO
    );


/* -------------------------------- CHB_EQU_HOLD_CHECK -----------------------*/
drop index CHB_EQU_HOLD_CK_UNIQUE_INDEX;
CREATE INDEX
    CHB_EQU_HOLD_CK_UNIQUE_INDEX
ON
    CHB_EQU_HOLD_CHECK
    (
        REGIST_BANK_CODE,
        IDENTIFY_NUMBER,
        HOLDER_NAME,
        PERSONAL_IDENTIFY_TYPE,
        INSTITUTION_IDENTIFY_TYPE,
        OTHER_IDENTIFY_NAME
    );


    drop index CHB_EQU_HOLD_CK_UNIQUE_INDEX;

create index CHB_EQU_HOLD_CK_UNIQUE_INDEX on CHB_EQU_HOLD_CHECK (REGIST_BANK_CODE, IDENTIFY_NUMBER,org_indentify_mark, HOLDER_NAME, PERSONAL_IDENTIFY_TYPE);
/* -------------------------------- CHB_INVESTHOLD_INFO -----------------------*/
drop index I_CHB_INVESTHOLD_HOLD_DATE;
drop index I_CHB_INVESTHOLD_INFO;
drop index I_CHB_INVESTHOLD_INFO_2;
drop index I_CHB_INVESTHOLD_INFO_3;

CREATE INDEX
    I_CHB_INVESTHOLD_HOLD_DATE
ON
    CHB_INVESTHOLD_INFO
    (
        HOLD_DATE
    );

CREATE INDEX
    I_CHB_INVESTHOLD_INFO
ON
    CHB_INVESTHOLD_INFO
    (
        REGIST_BANK_CODE,
        INDENTIFY_MARK,
        HOLD_DATE,
        PRODID
    );

CREATE INDEX
    I_CHB_INVESTHOLD_INFO_2
ON
    CHB_INVESTHOLD_INFO
    (
        REGIST_BANK_CODE,
        INDENTIFY_MARK,
        PRD_REGIST_CODE,
        HOLD_DATE,
        CCY
    );

CREATE INDEX
    I_CHB_INVESTHOLD_INFO_3
ON
    CHB_INVESTHOLD_INFO
    (
        STATUS
    );

/* -------------------------------- CHB_INVESTHOLD_INFO_SUB -----------------------*/
drop index I_CHB_INVESTHOLDSUB_HOLD_DATE;
drop index I_CHB_INVESTHOLD_INFO_SUB;
drop index I_CHB_INVESTHOLD_INFO_SUB_2;
drop index I_CHB_INVESTHOLD_INFO_SUB_3;
drop index I_INVEST_BATCH;

CREATE INDEX
    I_CHB_INVESTHOLDSUB_HOLD_DATE
ON
    CHB_INVESTHOLD_INFO_SUB
    (
        HOLD_DATE
    );

CREATE INDEX
    I_CHB_INVESTHOLD_INFO_SUB
ON
    CHB_INVESTHOLD_INFO_SUB
    (
        REGIST_BANK_CODE,
        INDENTIFY_MARK,
        HOLD_DATE,
        PRODID
    );

CREATE INDEX
    I_CHB_INVESTHOLD_INFO_SUB_2
ON
    CHB_INVESTHOLD_INFO_SUB
    (
        REGIST_BANK_CODE,
        INDENTIFY_MARK,
        PRD_REGIST_CODE,
        HOLD_DATE,
        CCY,
        SEND_STATUS
    );

/* -------------------------------- CHB_INVESTHOLD_INFO_CHECK -----------------------*/
drop index CHB_INVESTHOLD_INFO_CHECK_2;
CREATE INDEX
    CHB_INVESTHOLD_INFO_CHECK_2
ON
    CHB_INVESTHOLD_INFO_CHECK
    (
        OPERATE_STATUS,
        HOLD_DATE
    );




















































































































































































































































































































































































