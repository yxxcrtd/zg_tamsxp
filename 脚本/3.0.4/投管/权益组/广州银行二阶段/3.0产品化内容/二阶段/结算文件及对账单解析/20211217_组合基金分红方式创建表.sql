drop table PORTFOLIO_FUND_BONUS_MODE cascade constraints;

/*==============================================================*/
/* Table: PORTFOLIO_FUND_BONUS_MODE                             */
/*==============================================================*/
create table PORTFOLIO_FUND_BONUS_MODE
(
    FINPROD_ID           VARCHAR(50)          not null,
    PORTFOLIO_ID         VARCHAR(32)          not null,
    BONUS_MODE           VARCHAR(50)          not null,
    SET_DATE             DATE                 not null,
    CREATE_USER          VARCHAR(20)          not null,
    CREATE_DEPT          VARCHAR(32),
    CREATE_TIME          TIMESTAMP            not null,
    UPDATE_USER          VARCHAR(20)          not null,
    UPDATE_TIME          TIMESTAMP            not null,
    constraint PK_PORTFOLIO_FUND_BONUS_MODE primary key (FINPROD_ID, PORTFOLIO_ID, BONUS_MODE, SET_DATE)
);

comment on table PORTFOLIO_FUND_BONUS_MODE is
'组合基金分红方式';

comment on column PORTFOLIO_FUND_BONUS_MODE.FINPROD_ID is
'金融产品代码';

comment on column PORTFOLIO_FUND_BONUS_MODE.PORTFOLIO_ID is
'分支序号';

comment on column PORTFOLIO_FUND_BONUS_MODE.BONUS_MODE is
'业务类型
01：回售
02：标准券出入库
03：可转债信用申购
04：可转债转股
05：配债
06：配股
07：新股申购';

comment on column PORTFOLIO_FUND_BONUS_MODE.SET_DATE is
'申报起始日';

comment on column PORTFOLIO_FUND_BONUS_MODE.CREATE_USER is
'创建人';

comment on column PORTFOLIO_FUND_BONUS_MODE.CREATE_DEPT is
'创建部门';

comment on column PORTFOLIO_FUND_BONUS_MODE.CREATE_TIME is
'创建时间';

comment on column PORTFOLIO_FUND_BONUS_MODE.UPDATE_USER is
'更新人';

comment on column PORTFOLIO_FUND_BONUS_MODE.UPDATE_TIME is
'更新时间';
