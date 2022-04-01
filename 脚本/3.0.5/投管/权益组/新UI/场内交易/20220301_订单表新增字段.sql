
/**订单表新增字段**/
alter table T_ORDER add CON_NUM NUMBER(30, 4);
comment on column T_ORDER.CON_NUM is '可转债数量';

alter table T_ORDER add ENTRUST_CLASS varchar2(50);
comment on column T_ORDER.ENTRUST_CLASS is '委托指令类别：01-单组合，02-多组合';


/**历史订单表新增字段**/
alter table T_ORDERHISTORY add CON_NUM NUMBER(30, 4);
comment on column T_ORDERHISTORY.CON_NUM is '可转债数量';

alter table T_ORDERHISTORY add ENTRUST_CLASS varchar2(50);
comment on column T_ORDERHISTORY.ENTRUST_CLASS is '委托指令类别：01-单组合，02-多组合';


/**委托表新增字段**/
alter table T_ENTRUST add CON_NUM NUMBER(30, 4);
comment on column T_ENTRUST.CON_NUM is '可转债数量';

alter table T_ENTRUST add ENTRUST_CLASS varchar2(50);
comment on column T_ENTRUST.ENTRUST_CLASS is '委托指令类别：01-单组合，02-多组合';


/**历史委托表新增字段**/
alter table T_ENTRUSTHISTORY add CON_NUM NUMBER(30, 4);
comment on column T_ENTRUSTHISTORY.CON_NUM is '可转债数量';

alter table T_ENTRUSTHISTORY add ENTRUST_CLASS varchar2(50);
comment on column T_ENTRUSTHISTORY.ENTRUST_CLASS is '委托指令类别：01-单组合，02-多组合';

/**大宗委托表新增字段**/
alter table T_INTEGRATED_ENTRUST add ENTRUST_CLASS varchar2(50);
comment on column T_INTEGRATED_ENTRUST.ENTRUST_CLASS is '委托指令类别：01-单组合，02-多组合';

/**历史大宗委托表新增字段**/
alter table T_INTEGRATED_ENTRUSTHISTORY add ENTRUST_CLASS varchar2(50);
comment on column T_INTEGRATED_ENTRUSTHISTORY.ENTRUST_CLASS is '委托指令类别：01-单组合，02-多组合';

/**成交表新增字段**/
alter table T_TRADE add ENTRUST_CLASS varchar2(50);
comment on column T_TRADE.ENTRUST_CLASS is '委托指令类别：01-单组合，02-多组合';

/**历史成交表新增字段**/
alter table T_TRADEHISTORY add ENTRUST_CLASS varchar2(50);
comment on column T_TRADEHISTORY.ENTRUST_CLASS is '委托指令类别：01-单组合，02-多组合';

