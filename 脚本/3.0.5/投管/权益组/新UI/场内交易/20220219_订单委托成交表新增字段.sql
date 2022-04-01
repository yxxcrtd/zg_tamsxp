
/**订单表新增字段**/
alter table T_ORDER add PORTFOLIO_NAME varchar2(100);
comment on column T_ORDER.PORTFOLIO_NAME is '组合名称';

alter table T_ORDER add SEC_ACCT_ID varchar2(50);
comment on column T_ORDER.SEC_ACCT_ID is '证券账户';

alter table T_ORDER add SEC_ACCT_NAME varchar2(100);
comment on column T_ORDER.SEC_ACCT_NAME is '证券账户名称';

alter table T_ORDER add TRADE_PLAT varchar2(32);
comment on column T_ORDER.TRADE_PLAT is '交易平台';

alter table T_ORDER add FINPROD_TYPE2 varchar2(32);
comment on column T_ORDER.FINPROD_TYPE2 is '金融产品类型';

alter table T_ORDER add INV_MANAGER_ID varchar2(32);
comment on column T_ORDER.INV_MANAGER_ID is '投资经理';

alter table T_ORDER add PS varchar2(32);
comment on column T_ORDER.PS is '指令交易方向';

/**历史订单表新增字段**/
alter table T_ORDERHISTORY add PORTFOLIO_NAME varchar2(100);
comment on column T_ORDERHISTORY.PORTFOLIO_NAME is '组合名称';

alter table T_ORDERHISTORY add SEC_ACCT_ID varchar2(50);
comment on column T_ORDERHISTORY.SEC_ACCT_ID is '证券账户';

alter table T_ORDERHISTORY add SEC_ACCT_NAME varchar2(100);
comment on column T_ORDERHISTORY.SEC_ACCT_NAME is '证券账户名称';

alter table T_ORDERHISTORY add TRADE_PLAT varchar2(32);
comment on column T_ORDERHISTORY.TRADE_PLAT is '交易平台';

alter table T_ORDERHISTORY add FINPROD_TYPE2 varchar2(32);
comment on column T_ORDERHISTORY.FINPROD_TYPE2 is '金融产品类型';

alter table T_ORDERHISTORY add INV_MANAGER_ID varchar2(32);
comment on column T_ORDERHISTORY.INV_MANAGER_ID is '投资经理';

alter table T_ORDERHISTORY add PS varchar2(32);
comment on column T_ORDERHISTORY.PS is '指令交易方向';

/**委托表新增字段**/
alter table T_ENTRUST add PORTFOLIO_NAME varchar2(100);
comment on column T_ENTRUST.PORTFOLIO_NAME is '组合名称';

alter table T_ENTRUST add SEC_ACCT_ID varchar2(50);
comment on column T_ENTRUST.SEC_ACCT_ID is '证券账户';

alter table T_ENTRUST add SEC_ACCT_NAME varchar2(100);
comment on column T_ENTRUST.SEC_ACCT_NAME is '证券账户名称';

alter table T_ENTRUST add TRADE_PLAT varchar2(32);
comment on column T_ENTRUST.TRADE_PLAT is '交易平台';

alter table T_ENTRUST add FINPROD_TYPE2 varchar2(32);
comment on column T_ENTRUST.FINPROD_TYPE2 is '金融产品类型';

alter table T_ENTRUST add INV_MANAGER_ID varchar2(32);
comment on column T_ENTRUST.INV_MANAGER_ID is '投资经理';

alter table T_ENTRUST add PS varchar2(32);
comment on column T_ENTRUST.PS is '指令交易方向';

/**历史委托表新增字段**/
alter table T_ENTRUSTHISTORY add PORTFOLIO_NAME varchar2(100);
comment on column T_ENTRUSTHISTORY.PORTFOLIO_NAME is '组合名称';

alter table T_ENTRUSTHISTORY add SEC_ACCT_ID varchar2(50);
comment on column T_ENTRUSTHISTORY.SEC_ACCT_ID is '证券账户';

alter table T_ENTRUSTHISTORY add SEC_ACCT_NAME varchar2(100);
comment on column T_ENTRUSTHISTORY.SEC_ACCT_NAME is '证券账户名称';

alter table T_ENTRUSTHISTORY add TRADE_PLAT varchar2(32);
comment on column T_ENTRUSTHISTORY.TRADE_PLAT is '交易平台';

alter table T_ENTRUSTHISTORY add FINPROD_TYPE2 varchar2(32);
comment on column T_ENTRUSTHISTORY.FINPROD_TYPE2 is '金融产品类型';

alter table T_ENTRUSTHISTORY add INV_MANAGER_ID varchar2(32);
comment on column T_ENTRUSTHISTORY.INV_MANAGER_ID is '投资经理';

alter table T_ENTRUSTHISTORY add PS varchar2(32);
comment on column T_ENTRUSTHISTORY.PS is '指令交易方向';

/**综合委托表新增字段**/
alter table T_INTEGRATED_ENTRUST add PORTFOLIO_NAME varchar2(100);
comment on column T_INTEGRATED_ENTRUST.PORTFOLIO_NAME is '组合名称';

alter table T_INTEGRATED_ENTRUST add SEC_ACCT_ID varchar2(50);
comment on column T_INTEGRATED_ENTRUST.SEC_ACCT_ID is '证券账户';

alter table T_INTEGRATED_ENTRUST add SEC_ACCT_NAME varchar2(100);
comment on column T_INTEGRATED_ENTRUST.SEC_ACCT_NAME is '证券账户名称';

alter table T_INTEGRATED_ENTRUST add TRADE_PLAT varchar2(32);
comment on column T_INTEGRATED_ENTRUST.TRADE_PLAT is '交易平台';

alter table T_INTEGRATED_ENTRUST add FINPROD_TYPE2 varchar2(32);
comment on column T_INTEGRATED_ENTRUST.FINPROD_TYPE2 is '金融产品类型';

alter table T_INTEGRATED_ENTRUST add INV_MANAGER_ID varchar2(32);
comment on column T_INTEGRATED_ENTRUST.INV_MANAGER_ID is '投资经理';

alter table T_INTEGRATED_ENTRUST add PS varchar2(32);
comment on column T_INTEGRATED_ENTRUST.PS is '指令交易方向';

/**历史综合委托表新增字段**/
alter table T_INTEGRATED_ENTRUSTHISTORY add PORTFOLIO_NAME varchar2(100);
comment on column T_INTEGRATED_ENTRUSTHISTORY.PORTFOLIO_NAME is '组合名称';

alter table T_INTEGRATED_ENTRUSTHISTORY add SEC_ACCT_ID varchar2(50);
comment on column T_INTEGRATED_ENTRUSTHISTORY.SEC_ACCT_ID is '证券账户';

alter table T_INTEGRATED_ENTRUSTHISTORY add SEC_ACCT_NAME varchar2(100);
comment on column T_INTEGRATED_ENTRUSTHISTORY.SEC_ACCT_NAME is '证券账户名称';

alter table T_INTEGRATED_ENTRUSTHISTORY add TRADE_PLAT varchar2(32);
comment on column T_INTEGRATED_ENTRUSTHISTORY.TRADE_PLAT is '交易平台';

alter table T_INTEGRATED_ENTRUSTHISTORY add FINPROD_TYPE2 varchar2(32);
comment on column T_INTEGRATED_ENTRUSTHISTORY.FINPROD_TYPE2 is '金融产品类型';

alter table T_INTEGRATED_ENTRUSTHISTORY add INV_MANAGER_ID varchar2(32);
comment on column T_INTEGRATED_ENTRUSTHISTORY.INV_MANAGER_ID is '投资经理';

alter table T_INTEGRATED_ENTRUSTHISTORY add PS varchar2(32);
comment on column T_INTEGRATED_ENTRUSTHISTORY.PS is '指令交易方向';

/**成交表新增字段**/
alter table T_TRADE add PORTFOLIO_NAME varchar2(100);
comment on column T_TRADE.PORTFOLIO_NAME is '组合名称';

alter table T_TRADE add SEC_ACCT_ID varchar2(50);
comment on column T_TRADE.SEC_ACCT_ID is '证券账户';

alter table T_TRADE add SEC_ACCT_NAME varchar2(100);
comment on column T_TRADE.SEC_ACCT_NAME is '证券账户名称';

alter table T_TRADE add TRADE_PLAT varchar2(32);
comment on column T_TRADE.TRADE_PLAT is '交易平台';

alter table T_TRADE add FINPROD_TYPE2 varchar2(32);
comment on column T_TRADE.FINPROD_TYPE2 is '金融产品类型';

alter table T_TRADE add INV_MANAGER_ID varchar2(32);
comment on column T_TRADE.INV_MANAGER_ID is '投资经理';

alter table T_TRADE add PS varchar2(32);
comment on column T_TRADE.PS is '指令交易方向';

/**历史成交表新增字段**/
alter table T_TRADEHISTORY add PORTFOLIO_NAME varchar2(100);
comment on column T_TRADEHISTORY.PORTFOLIO_NAME is '组合名称';

alter table T_TRADEHISTORY add SEC_ACCT_ID varchar2(50);
comment on column T_TRADEHISTORY.SEC_ACCT_ID is '证券账户';

alter table T_TRADEHISTORY add SEC_ACCT_NAME varchar2(100);
comment on column T_TRADEHISTORY.SEC_ACCT_NAME is '证券账户名称';

alter table T_TRADEHISTORY add TRADE_PLAT varchar2(32);
comment on column T_TRADEHISTORY.TRADE_PLAT is '交易平台';

alter table T_TRADEHISTORY add FINPROD_TYPE2 varchar2(32);
comment on column T_TRADEHISTORY.FINPROD_TYPE2 is '金融产品类型';

alter table T_TRADEHISTORY add INV_MANAGER_ID varchar2(32);
comment on column T_TRADEHISTORY.INV_MANAGER_ID is '投资经理';

alter table T_TRADEHISTORY add PS varchar2(32);
comment on column T_TRADEHISTORY.PS is '指令交易方向';
