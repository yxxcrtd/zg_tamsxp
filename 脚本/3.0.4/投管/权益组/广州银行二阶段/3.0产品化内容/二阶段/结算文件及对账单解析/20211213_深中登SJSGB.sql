drop table SZD_SJSGB cascade constraints;

/*==============================================================*/
/* Table: SZD_SJSGB                                             */
/*==============================================================*/
create table SZD_SJSGB 
(
   SEQNO                VARCHAR(32)          not null,
   IMPORT_DATE          DATE,
   DATA_FILE            VARCHAR(256),
   GBLB                 VARCHAR(2),
   GBDM1                VARCHAR(10),
   GBDM2                VARCHAR(20),
   GBDM3                VARCHAR(30),
   GBSL                 NUMBER(17,2),
   GBRQ1                DATE,
   GBRQ2                DATE,
   GBBL                 NUMBER(19,13),
   GBJE1                NUMBER(18,3),
   GBJE2                NUMBER(18,3),
   GBZD                 VARCHAR(100),
   GBBZ1                VARCHAR(1),
   GBBZ2                VARCHAR(1),
   GBBZSM               VARCHAR(500),
   GBSL2                NUMBER(15,2),
   GBBL2                NUMBER(15,2),
   GBRQ3                DATE,
   GBRQ4                DATE,
   GBRQ                 DATE,
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_SZD_SJSGB primary key (SEQNO)
);

comment on table SZD_SJSGB is
'深中登SJSGB';

comment on column SZD_SJSGB.SEQNO is
'流水号';

comment on column SZD_SJSGB.IMPORT_DATE is
'导入日期';

comment on column SZD_SJSGB.DATA_FILE is
'数据文件';

comment on column SZD_SJSGB.GBLB is
'广播类别';

comment on column SZD_SJSGB.GBDM1 is
'代码1';

comment on column SZD_SJSGB.GBDM2 is
'代码2';

comment on column SZD_SJSGB.GBDM3 is
'代码3';

comment on column SZD_SJSGB.GBSL is
'数量';

comment on column SZD_SJSGB.GBRQ1 is
'日期1';

comment on column SZD_SJSGB.GBRQ2 is
'日期2';

comment on column SZD_SJSGB.GBBL is
'比例';

comment on column SZD_SJSGB.GBJE1 is
'金额1';

comment on column SZD_SJSGB.GBJE2 is
'金额2';

comment on column SZD_SJSGB.GBZD is
'广播字段';

comment on column SZD_SJSGB.GBBZ1 is
'标志1';

comment on column SZD_SJSGB.GBBZ2 is
'标志2';

comment on column SZD_SJSGB.GBBZSM is
'备注说明';

comment on column SZD_SJSGB.GBSL2 is
'数量2';

comment on column SZD_SJSGB.GBBL2 is
'比例2';

comment on column SZD_SJSGB.GBRQ3 is
'日期3';

comment on column SZD_SJSGB.GBRQ4 is
'日期4';

comment on column SZD_SJSGB.GBRQ is
'广播日期';

comment on column SZD_SJSGB.CREATE_USER is
'创建人';

comment on column SZD_SJSGB.CREATE_DEPT is
'创建部门';

comment on column SZD_SJSGB.CREATE_TIME is
'创建时间';

comment on column SZD_SJSGB.UPDATE_USER is
'更新人';

comment on column SZD_SJSGB.UPDATE_TIME is
'更新时间';
