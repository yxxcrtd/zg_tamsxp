--修改组合代码字段长度
alter table T_ENTRUST modify (PORTFOLIO_ID varchar(200));
alter table T_ENTRUSTHISTORY modify (PORTFOLIO_ID varchar(200));
alter table T_INTEGRATED_ENTRUST modify (PORTFOLIO_ID varchar(200));
alter table T_INTEGRATED_ENTRUSTHISTORY modify (PORTFOLIO_ID varchar(200));
alter table T_INSTRUCTION modify (PORTFOLIO_ID varchar(200));
alter table T_INSTRUCTION_HISTORY modify (PORTFOLIO_ID varchar(200));
alter table T_TRADE modify (PORTFOLIO_ID varchar(200));
alter table T_TRADEHISTORY modify (PORTFOLIO_ID varchar(200));