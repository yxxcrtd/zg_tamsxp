-- 金融产品交易汇总表_交收使用
create table TRD_PRODUCT_TEMP_DEAL_TOTAL
(
    TOTLE_TRADE_ID         VARCHAR2(32) not null constraint PK_TRD_PRODUCT_TEMP_DEAL_TOTAL primary key,
    BUSI_TYPE              VARCHAR2(50) not null,
    FINPROD_ID             VARCHAR2(50),
    FINPROD_TYPE           VARCHAR2(50),
    FINPROD_TYPE2          VARCHAR2(50),
    BRANCH                 NUMBER(10),
    TRADE_DATE             DATE,
    VDATE                  DATE,
    MDATE                  DATE,
    SETTLE_DATE            DATE,
    CCY                    VARCHAR2(50),
    UNIT_CPRICE            NUMBER(30, 14),
    UNIT_INT               NUMBER(30, 14),
    UNIT_FPRICE            NUMBER(30, 14),
    PAR_VALUE              NUMBER(30, 14),
    CPRICE_AMT             NUMBER(30, 2),
    PRIN_AMT               NUMBER(30, 2),
    INT_AMT                NUMBER(30, 2),
    FPRICE_AMT             NUMBER(30, 2),
    FEE_AMT_PAY            NUMBER(30, 2),
    TRADE_AMT              NUMBER(30, 2),
    FEE_AMT_UNPAY          NUMBER(30, 2),
    FEE_AMT                NUMBER(30, 2),
    PS                     VARCHAR2(50),
    YIELD                  NUMBER(30, 14),
    EXER_YIELD             NUMBER(30, 14),
    INV_AIM                VARCHAR2(50),
    TRADE_STATUS           VARCHAR2(50),
    P_TRADE_ID             VARCHAR2(32),
    IS_CANCEL              VARCHAR2(50),
    IS_CLEAN               VARCHAR2(50),
    CASH_ID                VARCHAR2(32),
    SEC_MANAGE_ACCT_ID     VARCHAR2(32),
    B_TRADE_ID             VARCHAR2(32),
    CHL_ID                 VARCHAR2(32),
    COUNTER_ID             VARCHAR2(32),
    IS_OUT_TRADE           VARCHAR2(50),
    TRADE_MARKET           VARCHAR2(50),
    TRADE_PLAT             VARCHAR2(50),
    TRADE_MARKET_MODE      VARCHAR2(50),
    TRADER                 VARCHAR2(32),
    COUNTER_TRADER         VARCHAR2(200),
    COUNTER_CONTACT        VARCHAR2(1000),
    F_TRADE_ID             VARCHAR2(32),
    PORTFOLIO_ID           VARCHAR2(32),
    PROD_ID                VARCHAR2(32),
    P_TOTLE_TRADE_ID       VARCHAR2(32),
    REMARK                 VARCHAR2(4000),
    CREATE_USER            VARCHAR2(20) not null,
    CREATE_DEPT            VARCHAR2(32),
    CREATE_TIME            TIMESTAMP(6) not null,
    UPDATE_USER            VARCHAR2(20) not null,
    UPDATE_TIME            TIMESTAMP(6) not null,
    CURRENT_SUB            NUMBER(30, 2),
    LAST_AMT               NUMBER(30, 2),
    LAST_RED               NUMBER(30, 2),
    LAST_AMT_SUB           NUMBER(30, 2),
    ACCOUNTING_AMT         NUMBER(30, 2),
    RED_PROFIT             NUMBER(30, 2),
    RED_COST               NUMBER(30, 2),
    DQPRIN_AMT             NUMBER(30, 2),
    PAY_DATE               DATE,
    REMAIN_PRIN            NUMBER(30, 2),
    TG_BUSI_TYPE           VARCHAR2(50),
    IS_OVERDUE_DEAL        VARCHAR2(50),
    SETTLE_SPEED           NUMBER(10),
    PAY_UNPAIDINT_AMT      NUMBER(30, 14),
    ISPAY_UNPAIDINT        VARCHAR2(50),
    CLEAR_TYPE             VARCHAR2(50),
    EFF_TIME               TIMESTAMP(6),
    DELIVERY_TYPE          VARCHAR2(50),
    M_DELIVERY_TYPE        VARCHAR2(50),
    RATE                   NUMBER(30, 14),
    REPO_TERM              NUMBER(10),
    TERM_TYPE              VARCHAR2(50),
    INSTITUTION_CODE       VARCHAR2(32),
    REG_DATE               DATE,
    EX_DEVIDENT_DATE       DATE,
    RED_SHARE_LISTING_DATE DATE,
    DEVIDENT_DATE          DATE,
    PER_DEVIDENT_AMT       NUMBER(30, 14),
    PER_DEVIDENT_CASH      NUMBER(30, 14),
    DEVIDENT_CASH          NUMBER(30, 2),
    DEVIDENT_AMT           NUMBER(30, 4),
    IF_DIVIDEND            VARCHAR2(32),
    DIVID_CCY              VARCHAR2(32),
    EXCHANGE_RATE          VARCHAR2(32),
    REPLACE_PRICE          NUMBER(30, 14),
    REG_DATE_AMT           NUMBER(30, 4),
    YESTERDAY_POSITION     NUMBER(30, 2),
    TRADE_AMT_HK           NUMBER(30, 2),
    SELL_SETTLE_RATE       NUMBER(30, 4),
    SHARE_AMT              NUMBER(30, 4),
    BUSINESS_MODEL         VARCHAR2(50)
);

comment on table TRD_PRODUCT_TEMP_DEAL_TOTAL is '金融产品交易汇总表_交收使用';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TOTLE_TRADE_ID is '汇总交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.BUSI_TYPE is '业务类型';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.FINPROD_ID is '金融产品代码';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.FINPROD_TYPE is '金融产品类型（估值核算），债券、理财产品、基金、理财产品模板、理财产品分层';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.FINPROD_TYPE2 is '金融产品类型（投管分类），债券、理财产品、基金、理财产品模板、理财产品分层';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.BRANCH is '分支序号';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TRADE_DATE is '交易日期';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.VDATE is '起息日';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.MDATE is '到期日';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.SETTLE_DATE is '交割日期';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CCY is '交易币种';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.UNIT_CPRICE is '单位净价';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.UNIT_INT is '单位利息';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.UNIT_FPRICE is '单位全价';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PAR_VALUE is '单位面值，债券100或者当前面值，其他资产1';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CPRICE_AMT is '净价总额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PRIN_AMT is '交易本金';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.INT_AMT is '利息总额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.FPRICE_AMT is '全价总额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.FEE_AMT_PAY is '随交易支付费用';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TRADE_AMT is '交易金额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.FEE_AMT_UNPAY is '未随交易支付费用';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.FEE_AMT is '总交易费用';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PS is '交易方向';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.YIELD is '到期收益率';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.EXER_YIELD is '行权收益率';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.INV_AIM is '投资目的';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TRADE_STATUS is '交易状态，初始、修改、撤销';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.P_TRADE_ID is '原交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.IS_CANCEL is '是否已撤销，默认“否”，在交易发生撤销时更新成“是”';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.IS_CLEAN is '是否结清，收息付费时用';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CASH_ID is '现金流代码，金融产品代码+金融产品类型+现金流二级类型，还本、付息、付费等时存对应计划的现金流代码';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.SEC_MANAGE_ACCT_ID is '证券管理户代码';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.B_TRADE_ID is '前置交易编号，记录该交易的前置交易编号。比如申购确认时存申购申请交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CHL_ID is '渠道;通道代码';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.COUNTER_ID is '交易对手';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.IS_OUT_TRADE is '是否外部交易，是、否';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TRADE_MARKET is '交易场所，银行间、上交所、深交所、柜面等';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TRADE_PLAT is '交易平台，银行间、上交所固定收益平台、上交所大宗交易平台、深交所大宗平台、深交所综合协议交易平台、其他等';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TRADE_MARKET_MODE is '交易市场，一级市场、二级市场';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TRADER is '交易员';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.COUNTER_TRADER is '对手方交易员';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.COUNTER_CONTACT is '对手方联系方式';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.F_TRADE_ID is '关联系统交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PORTFOLIO_ID is '投资组合代码';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PROD_ID is '理财产品代码';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.P_TOTLE_TRADE_ID is '上级汇总交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.REMARK is '备注';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CREATE_USER is '创建人';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CREATE_DEPT is '创建部门';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CREATE_TIME is '创建时间';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.UPDATE_USER is '更新人';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.UPDATE_TIME is '更新时间';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CURRENT_SUB is '本期认购';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.LAST_AMT is '上期余额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.LAST_RED is '上期赎回';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.LAST_AMT_SUB is '上期滚入余额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.ACCOUNTING_AMT is '到账资金';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.RED_PROFIT is '赎回收益';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.RED_COST is '赎回成本';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.DQPRIN_AMT is '到期本金';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PAY_DATE is '交付日';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.REMAIN_PRIN is '剩余名义本金';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TG_BUSI_TYPE is '投管业务类型';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.IS_OVERDUE_DEAL is '是否逾期交易';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.SETTLE_SPEED is '清算速度';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PAY_UNPAIDINT_AMT is '支付未支付利息';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.ISPAY_UNPAIDINT is '是否转让还本未支付利息';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.CLEAR_TYPE is '清算类型';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.EFF_TIME is '有效时间';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.DELIVERY_TYPE is '首期结算方式';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.M_DELIVERY_TYPE is '到期结算方式';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.RATE is '利率';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.REPO_TERM is '期限(天)';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TERM_TYPE is '期限品种';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.INSTITUTION_CODE is '机构代码';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.REG_DATE is '权益登记日';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.EX_DEVIDENT_DATE is '除权除息日';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.RED_SHARE_LISTING_DATE is '红股上市日';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.DEVIDENT_DATE is '派息日';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PER_DEVIDENT_AMT is '每股送转';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.PER_DEVIDENT_CASH is '每股派息';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.DEVIDENT_CASH is '分红金额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.DEVIDENT_AMT is '分红股本';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.IF_DIVIDEND is '是否含股利选择权';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.DIVID_CCY is '派息币种';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.EXCHANGE_RATE is '汇率(派息等)';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.REPLACE_PRICE is '以股代息价格';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.REG_DATE_AMT is '权益登记日份额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.YESTERDAY_POSITION is '前一日港股持仓市值';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.TRADE_AMT_HK is '实付金额(港元)';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.SHARE_AMT is '交易份额';
comment on column TRD_PRODUCT_TEMP_DEAL_TOTAL.BUSINESS_MODEL is '业务模式';

create index TRD_PROD_TEMP_DEAL_TOTAL_IND1 on TRD_PRODUCT_TEMP_DEAL_TOTAL (FINPROD_TYPE2, IS_CANCEL, TRADE_STATUS, PS);

create index TRD_PRD_TEMP_DEAL_TOTAL_IND2 on TRD_PRODUCT_TEMP_DEAL_TOTAL (IS_CLEAN, FINPROD_TYPE2, TRADE_STATUS, PS);

create index TRD_PRD_DEAL_TEMP_TOTAL_IND3 on TRD_PRODUCT_TEMP_DEAL_TOTAL (P_TOTLE_TRADE_ID);

create index TRD_PRD_DEAL_TEMP_TOTAL_IND4 on TRD_PRODUCT_TEMP_DEAL_TOTAL (TRADE_STATUS, IS_CANCEL, MDATE, PS, FINPROD_ID, IS_CLEAN, FINPROD_TYPE2,
                                                                          SEC_MANAGE_ACCT_ID, PORTFOLIO_ID, P_TRADE_ID, P_TOTLE_TRADE_ID);

create index TRD_PRD_DEAL_TEMP_TOTAL_IND5 on TRD_PRODUCT_TEMP_DEAL_TOTAL (FINPROD_TYPE2, BUSI_TYPE, TRADE_STATUS);

create index TRD_PRD_DEAL_TEMP_TOTAL_IND6 on TRD_PRODUCT_TEMP_DEAL_TOTAL (PS);



-- 金融产品交易汇总扩展_交收使用
create table PA_TRD_PROD_TEMP_DEAL_TOTAL
(
    TOTLE_TRADE_ID           VARCHAR2(32) not null constraint PK_PA_TRD_PROD_TEMP_DEAL_TOT primary key,
    BRANCH                   VARCHAR2(32),
    UNIT_INT                 NUMBER(30, 14),
    UNIT_PRIN                NUMBER(30, 14),
    PRIN                     NUMBER(30, 2),
    RATE                     NUMBER(30, 14),
    THEORY_AMT               NUMBER(30, 2),
    THEORY_INT               NUMBER(30, 2),
    THEORY_PRIN              NUMBER(30, 2),
    THEORY_PAY_DATE          DATE,
    INTEREST                 NUMBER(30, 2),
    PA_BUSS_TYPE             VARCHAR2(50),
    CURRENT_SUB              NUMBER(30, 2),
    LAST_AMT                 NUMBER(30, 2),
    LAST_RED                 NUMBER(30, 2),
    LAST_AMT_SUB             NUMBER(30, 2),
    ACCOUNTING_AMT           NUMBER(30, 2),
    REMAIN_PRIN_AMT          NUMBER(30, 2),
    REG_DATE                 DATE,
    BONUS_CFM_DATE           DATE,
    PAY_TYPE                 VARCHAR2(50),
    CREATE_USER              VARCHAR2(20) not null,
    CREATE_DEPT              VARCHAR2(32),
    CREATE_TIME              TIMESTAMP(6) not null,
    UPDATE_USER              VARCHAR2(20) not null,
    UPDATE_TIME              TIMESTAMP(6) not null,
    IS_BREAK                 VARCHAR2(30),
    FEEAMT                   NUMBER(30, 4),
    YESTERDAY_DEBT_VALUATION NUMBER(30, 4),
    PRICE_OUTDEBT_VALUATION  NUMBER(30, 4),
    DEPT_CODE                VARCHAR2(32),
    THEORY_PENALTY_INT       NUMBER(30, 4),
    REFUND_RATE              NUMBER(30, 4),
    REFUND_AMOUNT            NUMBER(30, 2)
);

comment on table PA_TRD_PROD_TEMP_DEAL_TOTAL is '金融产品交易汇总扩展_交收使用';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.TOTLE_TRADE_ID is '汇总交易编号';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.BRANCH is '分行';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.UNIT_INT is '单位应计利息';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.UNIT_PRIN is '单位本金';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.PRIN is '还本面额';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.RATE is '区间票面利率';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.THEORY_AMT is '理论本息总额';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.THEORY_INT is '理论应计利息';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.THEORY_PRIN is '理论还本金额';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.THEORY_PAY_DATE is '理论支付日';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.INTEREST is '应付利息';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.PA_BUSS_TYPE is '平安业务类型，产品成立、强增强减、产品到期、产品存续期、普通拆借、特定目的载体等';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.CURRENT_SUB is '本期认购';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.LAST_AMT is '上期余额';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.LAST_RED is '上期赎回';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.LAST_AMT_SUB is '上期滚入金额';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.ACCOUNTING_AMT is '到账资金';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.REMAIN_PRIN_AMT is '剩余本金';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.REG_DATE is '权益登记日';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.BONUS_CFM_DATE is '预计分红日';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.PAY_TYPE is '支付类型，交易方向为分红除权时填写，红利再投、现金分红';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.CREATE_USER is '创建人';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.CREATE_DEPT is '创建部门';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.CREATE_TIME is '创建时间';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.UPDATE_USER is '更新人';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.UPDATE_TIME is '更新时间';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.IS_BREAK is '是否违约，Y违约，N没违约';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.FEEAMT is '随交易回款前扣除的费用';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.YESTERDAY_DEBT_VALUATION is '昨日中债估值';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.PRICE_OUTDEBT_VALUATION is '成交价格脱离中债估值';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.DEPT_CODE is '交易所属部门，内部职能部门的主键';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.THEORY_PENALTY_INT is '理论罚息金额';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.REFUND_RATE is '返款费率';

comment on column PA_TRD_PROD_TEMP_DEAL_TOTAL.REFUND_AMOUNT is '返款金额';

create index PA_TRD_PRD_DEAL_TOTAL_IND1 on PA_TRD_PROD_TEMP_DEAL_TOTAL (PA_BUSS_TYPE);



-- 更新表字段
alter table trd_product_temp_deal_add add our_share_amt  NUMBER(30,4);
comment on column trd_product_temp_deal_add.our_share_amt is '本方券交易份额';

alter table trd_product_temp_deal_add add lendin_share_amt  NUMBER(30,4);
comment on column trd_product_temp_deal_add.lendin_share_amt is '融入券交易份额';



