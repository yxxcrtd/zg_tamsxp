
/**订单表新增字段**/
alter table T_ORDER add BUSI_TYPE_TG varchar2(50);
comment on column T_ORDER.BUSI_TYPE_TG is '投管业务类型';

alter table T_ORDER add EXTEND1 varchar2(50);
comment on column T_ORDER.EXTEND1 is '扩展信息1';

alter table T_ORDER add EXTEND2 varchar2(1024);
comment on column T_ORDER.EXTEND2 is '扩展信息2';

/**历史订单表新增字段**/
alter table T_ORDERHISTORY add BUSI_TYPE_TG varchar2(50);
comment on column T_ORDERHISTORY.BUSI_TYPE_TG is '投管业务类型';

alter table T_ORDERHISTORY add EXTEND1 varchar2(50);
comment on column T_ORDERHISTORY.EXTEND1 is '扩展信息1';

alter table T_ORDERHISTORY add EXTEND2 varchar2(1024);
comment on column T_ORDERHISTORY.EXTEND2 is '扩展信息2';

/**委托表新增字段**/
alter table T_ENTRUST add BUSI_TYPE_TG varchar2(50);
comment on column T_ENTRUST.BUSI_TYPE_TG is '投管业务类型';

alter table T_ENTRUST add EXTEND1 varchar2(50);
comment on column T_ENTRUST.EXTEND1 is '扩展信息1';

alter table T_ENTRUST add EXTEND2 varchar2(1024);
comment on column T_ENTRUST.EXTEND2 is '扩展信息2';

/**历史委托表新增字段**/
alter table T_ENTRUSTHISTORY add BUSI_TYPE_TG varchar2(50);
comment on column T_ENTRUSTHISTORY.BUSI_TYPE_TG is '投管业务类型';

alter table T_ENTRUSTHISTORY add EXTEND1 varchar2(50);
comment on column T_ENTRUSTHISTORY.EXTEND1 is '扩展信息1';

alter table T_ENTRUSTHISTORY add EXTEND2 varchar2(1024);
comment on column T_ENTRUSTHISTORY.EXTEND2 is '扩展信息2';

/**综合委托表新增字段**/
alter table T_INTEGRATED_ENTRUST add BUSI_TYPE_TG varchar2(50);
comment on column T_INTEGRATED_ENTRUST.BUSI_TYPE_TG is '投管业务类型';

alter table T_INTEGRATED_ENTRUST add EXTEND1 varchar2(50);
comment on column T_INTEGRATED_ENTRUST.EXTEND1 is '扩展信息1';

alter table T_INTEGRATED_ENTRUST add EXTEND2 varchar2(1024);
comment on column T_INTEGRATED_ENTRUST.EXTEND2 is '扩展信息2';

/**历史综合委托表新增字段**/
alter table T_INTEGRATED_ENTRUSTHISTORY add BUSI_TYPE_TG varchar2(50);
comment on column T_INTEGRATED_ENTRUSTHISTORY.BUSI_TYPE_TG is '投管业务类型';

alter table T_INTEGRATED_ENTRUSTHISTORY add EXTEND1 varchar2(50);
comment on column T_INTEGRATED_ENTRUSTHISTORY.EXTEND1 is '扩展信息1';

alter table T_INTEGRATED_ENTRUSTHISTORY add EXTEND2 varchar2(1024);
comment on column T_INTEGRATED_ENTRUSTHISTORY.EXTEND2 is '扩展信息2';

/**成交表新增字段**/
alter table T_TRADE add BUSI_TYPE_TG varchar2(50);
comment on column T_TRADE.BUSI_TYPE_TG is '投管业务类型';

alter table T_TRADE add EXTEND1 varchar2(50);
comment on column T_TRADE.EXTEND1 is '扩展信息1';

alter table T_TRADE add EXTEND2 varchar2(1024);
comment on column T_TRADE.EXTEND2 is '扩展信息2';

/**历史成交表新增字段**/
alter table T_TRADEHISTORY add BUSI_TYPE_TG varchar2(50);
comment on column T_TRADEHISTORY.BUSI_TYPE_TG is '投管业务类型';

alter table T_TRADEHISTORY add EXTEND1 varchar2(50);
comment on column T_TRADEHISTORY.EXTEND1 is '扩展信息1';

alter table T_TRADEHISTORY add EXTEND2 varchar2(1024);
comment on column T_TRADEHISTORY.EXTEND2 is '扩展信息2';
