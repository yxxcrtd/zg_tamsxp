drop table SETTLE_TRANS_DATA_TOTAL_PLEDGE cascade constraints;

/*==============================================================*/
/* Table: SETTLE_TRANS_DATA_TOTAL_PLEDGE                        */
/*==============================================================*/
create table SETTLE_TRANS_DATA_TOTAL_PLEDGE 
(
   TRADE_ID             VARCHAR(32)          not null,
   BRANCH               NUMERIC(10)          not null,
   PLEDGE_FINPROD_ID    VARCHAR(50)          not null,
   PLEDGE_FACE_VALUE    NUMERIC(30, 2),
   PLEDGE_NUMBER        NUMERIC(30, 2),
   PLEDGE_RATIO         NUMERIC(30, 14),
   PLEDGE_AMT           NUMERIC(30, 2),
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   "DISPLACE DIRECTION" VARCHAR(50),
   constraint PK_SETTLE_TRANS_DATA_TOTAL_PLE primary key (TRADE_ID, BRANCH, PLEDGE_FINPROD_ID)
);

comment on table SETTLE_TRANS_DATA_TOTAL_PLEDGE is
'汇总成交质押品信息表';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.TRADE_ID is
'流水号';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.BRANCH is
'分支序号';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.PLEDGE_FINPROD_ID is
'质押金融产品代码';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.PLEDGE_FACE_VALUE is
'质押面值';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.PLEDGE_NUMBER is
'质押数量';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.PLEDGE_RATIO is
'质押率';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.PLEDGE_AMT is
'质押金额';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.CREATE_USER is
'创建人';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.CREATE_DEPT is
'创建部门';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.CREATE_TIME is
'创建时间';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.UPDATE_USER is
'更新人';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE.UPDATE_TIME is
'更新时间';

comment on column SETTLE_TRANS_DATA_TOTAL_PLEDGE."DISPLACE DIRECTION" is
'置换方向，中途换券、解除质押时登录';
