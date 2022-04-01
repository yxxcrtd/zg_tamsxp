drop table SETTLE_DATA_TOTAL_PLEDGE_MATCH cascade constraints;

/*==============================================================*/
/* Table: SETTLE_DATA_TOTAL_PLEDGE_MATCH                        */
/*==============================================================*/
create table SETTLE_DATA_TOTAL_PLEDGE_MATCH 
(
   SEQ_NO               VARCHAR(32)          not null,
   ENTRUNST_TRADE_ID    VARCHAR(32)          not null,
   PLEDGE_FINPROD_ID    VARCHAR(50)          not null,
   BRANCH               NUMERIC(10)          not null,
   MATCH_CLASS          VARCHAR(50)          not null,
   MATCH_TYPE           VARCHAR(50),
   MARCH_QTY            NUMERIC(30,4),
   MARCH_AMT            NUMERIC(30, 2),
   MARCH_QTY_EN         NUMERIC(30,4),
   MARCH_AMT_EN         NUMERIC(30, 2),
   DISPLACE_DIRECTION 	VARCHAR(50),
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_SETTLE_DATA_TOTAL_PLEDGE_MA primary key (SEQ_NO, ENTRUNST_TRADE_ID, PLEDGE_FINPROD_ID, BRANCH, MATCH_CLASS)
);

comment on table SETTLE_DATA_TOTAL_PLEDGE_MATCH is
'汇总成交质押品匹配结果表';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.SEQ_NO is
'汇总成交数据汇总的流水号';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.ENTRUNST_TRADE_ID is
'指令编号';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.PLEDGE_FINPROD_ID is
'质押金融产品代码';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.BRANCH is
'分支序号';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.MATCH_CLASS is
'匹配方式，手工匹配，自动匹配';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.MATCH_TYPE is
'目标类型';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.MARCH_QTY is
'已匹配份额';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.MARCH_AMT is
'已匹配金额';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.MARCH_QTY_EN is
'指令已匹配份额';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.MARCH_AMT_EN is
'指令已匹配金额';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.DISPLACE_DIRECTION is
'置换方向，中途换券、解除质押时登录';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.CREATE_USER is
'创建人';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.CREATE_DEPT is
'创建部门';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.CREATE_TIME is
'创建时间';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.UPDATE_USER is
'更新人';

comment on column SETTLE_DATA_TOTAL_PLEDGE_MATCH.UPDATE_TIME is
'更新时间';
