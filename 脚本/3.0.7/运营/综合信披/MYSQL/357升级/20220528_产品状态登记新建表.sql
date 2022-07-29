
CREATE TABLE IF NOT EXISTS CHB_PRD_STATUS_SUB
(
    REGIST_SEQNO           char(36)       not null comment '登记序列' primary key,
    PROD_ID                varchar(150)   null comment '产品代码',
    ISSUE_PARTY_ID         varchar(50)    null comment '发行机构代码',
    PRD_REGIST_CODE        varchar(20)    null comment '产品登记编码',
    PRD_TOTAL_AMT          decimal(18, 3) null comment '理财产品总资产金额（元）',
    PRD_LEVERAGE           decimal(15, 7) null comment '理财产品杠杆率（%）',
    PRD_STATUS_DATE        datetime       null comment '产品状态统计日',
    TERMID                 varchar(50)    null comment '期数',
    REMARK                 varchar(300)   null comment '备注',
    SEND_STATUS            varchar(2)     null comment '报送状态',
    STATUS_DATE            datetime       null comment '数据日期',
    STATUS                 varchar(2)     null comment '数据状态',
    ERR_MSG                 text     null comment '错误信息',
    CHB_SUBMIT_DEADLINE    datetime       null comment '报送截止日',
    SUBMIT_DEADLINE        datetime       null comment '行内报送截止日',
    SUBMIT_TIME            datetime       null comment '报送日期',
    SUBMIT_FEEDBACK_STATUS varchar(50)    null comment '反馈状态',
    SUBMIT_SEQ             varchar(50)    null comment '报送流水号',
    SUBMIT_FEEDBACK_MSG    varchar(600)   null comment '反馈信息',
    CREATE_DEPT            varchar(32)    null comment '创建部门',
    CREATE_USER            varchar(20)    null comment '创建人',
    CREATE_TIME            datetime       null comment '创建时间',
    UPDATE_USER            varchar(20)    null comment '更新人',
    UPDATE_TIME            datetime       null comment '更新时间'

    )
    comment '产品状态登记表' collate = utf8_bin;