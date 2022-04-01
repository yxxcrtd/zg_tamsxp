
drop table ETR_ENTRUST_DETAIL_BACKUP;

/*==============================================================*/
/* Table: ETR_ENTRUST_DETAIL_BACKUP                                    */
/*==============================================================*/
create table ETR_ENTRUST_DETAIL_BACKUP
(
    ENTRUST_ID           VARCHAR(32)          not null,
    PORTFOLIO_ID         VARCHAR(32)          not null,
    DIS_RATE             NUMERIC(30, 14),
    ENTRUST_AMT          NUMERIC(30, 2),
    ENTRUST_NUM          NUMERIC(30, 4),
    TARGET_TYPE          VARCHAR(50),
    CREATE_USER          VARCHAR(20)          not null,
    CREATE_DEPT          VARCHAR(32),
    CREATE_TIME          TIMESTAMP            not null,
    UPDATE_USER          VARCHAR(20)          not null,
    UPDATE_TIME          TIMESTAMP            not null,
    constraint ETR_ENTRUST_DETAIL_BACKUP primary key (ENTRUST_ID, PORTFOLIO_ID)
);

comment on table ETR_ENTRUST_DETAIL_BACKUP is
'委托指令明细备份表';

comment on column ETR_ENTRUST_DETAIL_BACKUP.ENTRUST_ID is
'委托代码';

comment on column ETR_ENTRUST_DETAIL_BACKUP.PORTFOLIO_ID is
'组合代码';

comment on column ETR_ENTRUST_DETAIL_BACKUP.DIS_RATE is
'分配比例';

comment on column ETR_ENTRUST_DETAIL_BACKUP.ENTRUST_AMT is
'委托金额，交易金额';

comment on column ETR_ENTRUST_DETAIL_BACKUP.ENTRUST_NUM is
'委托数量，数量或者券面总额/100';

comment on column ETR_ENTRUST_DETAIL_BACKUP.TARGET_TYPE is
'目标类型，金额，数量';

comment on column ETR_ENTRUST_DETAIL_BACKUP.CREATE_USER is
'创建人';

comment on column ETR_ENTRUST_DETAIL_BACKUP.CREATE_DEPT is
'创建部门';

comment on column ETR_ENTRUST_DETAIL_BACKUP.CREATE_TIME is
'创建时间';

comment on column ETR_ENTRUST_DETAIL_BACKUP.UPDATE_USER is
'更新人';

comment on column ETR_ENTRUST_DETAIL_BACKUP.UPDATE_TIME is
'更新时间';
