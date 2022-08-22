/**流动性风险登记表**/
CREATE TABLE IF NOT EXISTS LIQUIDITY_RISK_SUB
(
    REGIST_SEQNO	   varchar(36)   null comment '登记序列',
    ITEM_ID                varchar(50)   not null comment '统计年月'  primary key,
    ITEM_NAME              varchar(200)  null comment '报表名称',
    STATUS	           varchar(10)   null comment '数据状态',
    STATUS_DATE            DATE           null comment '数据日期',
    CHB_SUBMIT_DEADLINE    datetime      null comment '中债报送截止日',
    SUBMIT_DEADLINE        datetime      null comment '行内报送截止日',
    SUBMIT_TIME            datetime      null comment '报送时间',
    SEND_STATUS            varchar(2)    null comment '报送状态',
    SUBMIT_FEEDBACK_STATUS varchar(50)   null comment '反馈状态',
    SUBMIT_FEEDBACK_MSG    varchar(600)  null comment '报送反馈消息',
    ORG_CODE	           varchar(20)   null comment '机构代码',
    DEPT_CODE	           varchar(20)   null comment '创建部门',
    CREATE_USER	           varchar(20)   null comment '创建人',
    CREATE_TIME  	   datetime      null comment '创建时间',
    UPDATE_USER	           varchar(20)   null comment '更新人',
    UPDATE_TIME	           datetime      null comment '更新时间'
    )
    comment '流动性风险登记表' collate = utf8_bin;
/**流动性风险登记明细表**/
CREATE TABLE IF NOT EXISTS LIQUIDITY_RISK_SUB_DETAIL
(
    ITEM_ID                varchar(50)   not null comment '统计年月',
    PROD_ID                varchar(150)  not null comment '产品代码',
    REG_CODE	           varchar(50)   not null comment '产品登记编码',
    SOLUTIONS              varchar(4)    not null comment '流动性风险应对措施',
    OCCUR_DATE             DATE      not null comment '风险发生日期',
    REPLY_DATE             DATE      not null comment '风险应对日期',
    DESCRIBED              varchar(256)    not null comment '风险事件描述',
    DISPOSE_RESULT         varchar(256)   not null comment '风险处理结果',
    REMARK                 varchar(256)   null comment '备注',
    ORG_CODE	           varchar(20)   null comment '机构代码',
    DEPT_CODE	           varchar(20)   null comment '创建部门',
    CREATE_USER	           varchar(20)   null comment '创建人',
    CREATE_TIME  	   datetime      null comment '创建时间',
    UPDATE_USER	           varchar(20)   null comment '更新人',
    UPDATE_TIME	           datetime      null comment '更新时间',
    PRIMARY KEY (ITEM_ID , PROD_ID, REG_CODE, SOLUTIONS, OCCUR_DATE, REPLY_DATE, DESCRIBED, DISPOSE_RESULT)
    )
    comment '流动性风险登记明细表' collate = utf8_bin;