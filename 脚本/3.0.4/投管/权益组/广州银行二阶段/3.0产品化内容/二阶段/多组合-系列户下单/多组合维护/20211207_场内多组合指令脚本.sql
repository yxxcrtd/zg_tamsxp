delete from sys_para where PARA_CODE = 'IS_NEED_EXCHANGE_MULTI_PTL';
insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, UPDATE_FLG)
values ('INV', 'IS_NEED_EXCHANGE_MULTI_PTL', '02', '01', null, '场内交易指令是否开启多组合指令。01:是,02:否', 'system', null, sysdate, 'admin', sysdate, null, '01');


delete from sys_page where PAGE_ID = 'EtrExchangeTradeMulti001';
insert into sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMulti001', 'ETR', '/invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001', 'invest/entrust/etrexchangetrade/EtrExchangeTradeMulti001', null, null, null, '02', '01', '场内交易多组合指令页面', 'system', null, sysdate, 'system', sysdate);



delete from sys_datadict where DICT_CODE = 'MUL_DISTRI_CLASS';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('MUL_DISTRI_CLASS', 'TG', '场内多组合分配方式', null, '02', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE = 'MUL_DISTRI_CLASS' and ITEM_CODE in ('01','02','03','04','05','06','07','08','09','10');
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '01', '单笔数量', 1, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '02', '单笔金额', 2, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '03', '总数量', 3, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '04', '总金额', 4, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '05', '持仓数量比例', 5, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '06', '可用金额比例', 6, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '07', '可用标准券比例', 7, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '08', '产品净资产比例', 8, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '09', '目标可用金额', 9, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_DISTRI_CLASS', '10', '目标标准券使用率', 10, '0', 'system', null, sysdate, 'system', sysdate, null);



delete from sys_datadict where DICT_CODE = 'WEIGH_TYPE';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('WEIGH_TYPE', 'TG', '权重方式', null, '02', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE = 'WEIGH_TYPE' and ITEM_CODE in ('01','02','03','04','05');
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('WEIGH_TYPE', '01', '平均分配', 1, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('WEIGH_TYPE', '02', '组合净资产比例', 2, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('WEIGH_TYPE', '03', '组合可用金额比例', 3, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('WEIGH_TYPE', '04', '组合持仓数量比例', 4, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('WEIGH_TYPE', '05', '自定义', 5, '0', 'system', null, sysdate, 'system', sysdate, null);



delete from sys_datadict where DICT_CODE = 'MUL_TARGET_UNIT';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('MUL_TARGET_UNIT', 'TG', '目标值单位', null, '02', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE = 'MUL_TARGET_UNIT' and ITEM_CODE in ('01','02','03','04','05','06','07');
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_TARGET_UNIT', '01', '股', 1, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_TARGET_UNIT', '02', '份', 2, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_TARGET_UNIT', '03', '张', 3, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_TARGET_UNIT', '04', '手', 4, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_TARGET_UNIT', '05', '%', 5, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_TARGET_UNIT', '06', '元', 4, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('MUL_TARGET_UNIT', '07', '万元', 5, '0', 'system', null, sysdate, 'system', sysdate, null);


delete from sys_datadict_list where DICT_CODE = 'ENTRUST_CLASS' and ITEM_CODE in ('02');
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_CLASS', '02', '多组合单资产指令', 2, '0', 'system', null, sysdate, 'system', sysdate, null);

delete from sys_datadict_list where DICT_CODE = 'PS' and ITEM_CODE in ('T100');
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PS', 'T100', '调整到', 126, '0', 'system', null, sysdate, 'system', sysdate, null);


delete from sys_page_dictlist where PAGE_ID = 'EtrExchangeTradeMulti001';
insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeTradeMulti001', '999999', 'INV_AIM', 'AC', null, null, 'admin', null, sysdate, 'admin', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeTradeMulti001', '999999', 'INV_AIM', 'FVOCI', null, null, 'admin', null, sysdate, 'admin', sysdate, null);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('EtrExchangeTradeMulti001', '999999', 'INV_AIM', 'FVTPL', null, null, 'admin', null, sysdate, 'admin', sysdate, null);



drop table MUTIPTL_PORTFOLIO_TREE cascade constraints;

/*==============================================================*/
/* Table: MUTIPTL_PORTFOLIO_TREE                                */
/*==============================================================*/
create table MUTIPTL_PORTFOLIO_TREE 
(
   TREE_ID              VARCHAR(32)          not null,
   TREE_NAME            VARCHAR(200),
   PAR_TREE_ID          VARCHAR(32)          not null,
   INPUT_TYPE           VARCHAR(50),
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MUTIPTL_PORTFOLIO_TREE primary key (TREE_ID)
);

comment on table MUTIPTL_PORTFOLIO_TREE is
'多组合树';

comment on column MUTIPTL_PORTFOLIO_TREE.TREE_ID is
'多组合树代码';

comment on column MUTIPTL_PORTFOLIO_TREE.TREE_NAME is
'多组合树名称';

comment on column MUTIPTL_PORTFOLIO_TREE.PAR_TREE_ID is
'多组合树父代码';

comment on column MUTIPTL_PORTFOLIO_TREE.INPUT_TYPE is
'录入方式,画面,自动生成';

comment on column MUTIPTL_PORTFOLIO_TREE.CREATE_USER is
'创建人';

comment on column MUTIPTL_PORTFOLIO_TREE.CREATE_DEPT is
'创建部门';

comment on column MUTIPTL_PORTFOLIO_TREE.CREATE_TIME is
'创建时间';

comment on column MUTIPTL_PORTFOLIO_TREE.UPDATE_USER is
'更新人';

comment on column MUTIPTL_PORTFOLIO_TREE.UPDATE_TIME is
'更新时间';


/*WFL_ETR_ENTRUST_ADD 增加权重方式,分配方式,目标值,目标值单位*/
alter table WFL_ETR_ENTRUST_ADD add WEIGH_TYPE varchar2(50);
COMMENT ON COLUMN WFL_ETR_ENTRUST_ADD.WEIGH_TYPE IS '权重方式';

alter table WFL_ETR_ENTRUST_ADD add MUL_DISTRI_CLASS varchar2(50);
COMMENT ON COLUMN WFL_ETR_ENTRUST_ADD.MUL_DISTRI_CLASS IS '分配方式';

alter table WFL_ETR_ENTRUST_ADD add MUL_TARGET NUMBER(30, 14);
COMMENT ON COLUMN WFL_ETR_ENTRUST_ADD.MUL_TARGET IS '目标值';

alter table WFL_ETR_ENTRUST_ADD add MUL_UNIT varchar2(50);
COMMENT ON COLUMN WFL_ETR_ENTRUST_ADD.MUL_UNIT IS '目标值单位';

/*ETR_ENTRUST_ADD 增加权重方式,分配方式,目标值,目标值单位*/
alter table ETR_ENTRUST_ADD add WEIGH_TYPE varchar2(50);
COMMENT ON COLUMN ETR_ENTRUST_ADD.WEIGH_TYPE IS '权重方式';

alter table ETR_ENTRUST_ADD add MUL_DISTRI_CLASS varchar2(50);
COMMENT ON COLUMN ETR_ENTRUST_ADD.MUL_DISTRI_CLASS IS '分配方式';

alter table ETR_ENTRUST_ADD add MUL_TARGET NUMBER(30, 14);
COMMENT ON COLUMN ETR_ENTRUST_ADD.MUL_TARGET IS '目标值';

alter table ETR_ENTRUST_ADD add MUL_UNIT varchar2(50);
COMMENT ON COLUMN ETR_ENTRUST_ADD.MUL_UNIT IS '目标值单位';


drop table MUTIPTL_PORTFOLIO_DETAIL cascade constraints;

/*==============================================================*/
/* Table: MUTIPTL_PORTFOLIO_DETAIL                              */
/*==============================================================*/
create table MUTIPTL_PORTFOLIO_DETAIL 
(
   SEQ_NO               VARCHAR(32)          not null,
   TREE_ID              VARCHAR(32)          not null,
   PORTFOLIO_ID         VARCHAR(32)          not null,
   ACCT_NO              VARCHAR(32)          not null,
   CCY                  VARCHAR(50),
   EFF_DATE             DATE,
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MUTIPTL_PORTFOLIO_DETAIL primary key (SEQ_NO)
);

comment on table MUTIPTL_PORTFOLIO_DETAIL is
'多组合明细';

comment on column MUTIPTL_PORTFOLIO_DETAIL.SEQ_NO is
'序号';

comment on column MUTIPTL_PORTFOLIO_DETAIL.TREE_ID is
'多组合树代码';

comment on column MUTIPTL_PORTFOLIO_DETAIL.PORTFOLIO_ID is
'组合代码';

comment on column MUTIPTL_PORTFOLIO_DETAIL.ACCT_NO is
'资金账户';

comment on column MUTIPTL_PORTFOLIO_DETAIL.CCY is
'币种';

comment on column MUTIPTL_PORTFOLIO_DETAIL.EFF_DATE is
'生效日';

comment on column MUTIPTL_PORTFOLIO_DETAIL.CREATE_USER is
'创建人';

comment on column MUTIPTL_PORTFOLIO_DETAIL.CREATE_DEPT is
'创建部门';

comment on column MUTIPTL_PORTFOLIO_DETAIL.CREATE_TIME is
'创建时间';

comment on column MUTIPTL_PORTFOLIO_DETAIL.UPDATE_USER is
'更新人';

comment on column MUTIPTL_PORTFOLIO_DETAIL.UPDATE_TIME is
'更新时间';


/*委托指令明细流程表*/
drop table WFL_ETR_ENTRUST_DETAIL cascade constraints;

/*==============================================================*/
/* Table: WFL_ETR_ENTRUST_DETAIL                                */
/*==============================================================*/
create table WFL_ETR_ENTRUST_DETAIL 
(
   ENTRUST_ID           VARCHAR(32)          not null,
   PORTFOLIO_ID         VARCHAR(32)          not null,
   DIS_RATE             NUMERIC(30, 14),
   ENTRUST_AMT          NUMERIC(30, 2),
   ENTRUST_NUM          NUMERIC(30, 4),
   TARGET_TYPE          VARCHAR(50),
   WORKFLOW_STATUS      VARCHAR(50)          not null,
   WORKFLOW_ID          VARCHAR(32),
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_WFL_ETR_ENTRUST_DETAIL primary key (ENTRUST_ID, PORTFOLIO_ID, WORKFLOW_STATUS)
);

comment on table WFL_ETR_ENTRUST_DETAIL is
'委托指令明细流程表';

comment on column WFL_ETR_ENTRUST_DETAIL.ENTRUST_ID is
'委托代码';

comment on column WFL_ETR_ENTRUST_DETAIL.PORTFOLIO_ID is
'组合代码';

comment on column WFL_ETR_ENTRUST_DETAIL.DIS_RATE is
'分配比例';

comment on column WFL_ETR_ENTRUST_DETAIL.ENTRUST_AMT is
'委托金额，交易金额';

comment on column WFL_ETR_ENTRUST_DETAIL.ENTRUST_NUM is
'委托数量，数量或者券面总额/100';

comment on column WFL_ETR_ENTRUST_DETAIL.TARGET_TYPE is
'目标类型，金额，数量';

comment on column WFL_ETR_ENTRUST_DETAIL.WORKFLOW_STATUS is
'数据状态';

comment on column WFL_ETR_ENTRUST_DETAIL.WORKFLOW_ID is
'工作流代码';

comment on column WFL_ETR_ENTRUST_DETAIL.CREATE_USER is
'创建人';

comment on column WFL_ETR_ENTRUST_DETAIL.CREATE_DEPT is
'创建部门';

comment on column WFL_ETR_ENTRUST_DETAIL.CREATE_TIME is
'创建时间';

comment on column WFL_ETR_ENTRUST_DETAIL.UPDATE_USER is
'更新人';

comment on column WFL_ETR_ENTRUST_DETAIL.UPDATE_TIME is
'更新时间';
