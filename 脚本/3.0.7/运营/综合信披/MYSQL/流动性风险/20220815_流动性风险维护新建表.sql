CREATE TABLE IF NOT EXISTS LIQUIDITY_RISK_INFO
(
    SEQNO          CHAR(36)     not null comment '主键',
    PROD_ID        varchar(150) null comment '产品代码',
    REG_CODE       varchar(50)  null comment '产品登记编码',
    SOLUTIONS      varchar(4)   null comment '流动性风险应对措施',
    OCCUR_DATE     DATE         null comment '风险发生日期',
    REPLY_DATE     DATE         null comment '风险应对日期',
    DESCRIBED      varchar(256) null comment '风险事件描述',
    DISPOSE_RESULT varchar(256) null comment '风险处理结果',
    REMARK         varchar(256) null comment '备注',
    ERR_MSG        text         null comment '错误信息',
    DATA_SOURCE    varchar(2)   null comment '数据来源',
    STATUS         varchar(2)   null comment '数据状态 D:删除 A:待处理 E:有效',
    ORG_CODE       varchar(20)  null comment '机构代码',
    DEPT_CODE      varchar(20)  null comment '部门代码',
    CREATE_USER    varchar(20)  null comment '创建人',
    CREATE_TIME    datetime     null comment '创建时间',
    UPDATE_USER    varchar(20)  null comment '更新人',
    UPDATE_TIME    datetime     null comment '更新时间',
    CONSTRAINT  UNIQUE_LIQUIDITY_RISK_INFO UNIQUE (REG_CODE, SOLUTIONS, OCCUR_DATE, REPLY_DATE,DESCRIBED, DISPOSE_RESULT)
    )
    comment '流动性风险维护表' collate = utf8_bin;

DELETE FROM SYS_BATCH_TASK WHERE TASK_ID='3063';
INSERT INTO SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('3063', 'SVR', '流动性风险应对情况信息接口数据接入', null, 'svrLiquidityRiskInfoInterfTaskService', 3200, 'system', null, NOW(), 'system', NOW());
DELETE FROM CHB_INTERF_CONFIG WHERE INTERF_ID='LIQUIDITY_RISK_INFO';
INSERT INTO CHB_INTERF_CONFIG (INTERF_ID, LAST_UPDATETIME, REMARK, INTERF_NAME, CONTENT_TYPE, CONTENT_SRC, REF_REPORT, SYS_SCOPE, STATUS) VALUES ('LIQUIDITY_RISK_INFO', NOW(), '流动性风险应对情况信息接口数据', null, 'I', 'T', null, '110', 'E');