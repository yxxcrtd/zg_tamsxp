/***** 修改月度报表产品头寸表 *****/
alter table CHB_MONTH_PRD_POSITION modify  COLUMN  CCY   varchar(3) COMMENT  '募集币种';
alter table CHB_MONTH_PRD_POSITION modify  COLUMN  IVSTORIENTATION   varchar(2) COMMENT  '投资性质 01固定收益类/02权益类/03商品及金融衍生品类/04混合类';
alter table CHB_MONTH_PRD_POSITION modify  COLUMN  OPENDURATION  varchar(2) COMMENT  '开放期限 01每日/02 184天以上/03 其他';


alter table CHB_MONTH_PRD_POSITION add FUNCTIONAL_CURRENCY VARCHAR(3)  COMMENT '本位币';
alter table CHB_MONTH_PRD_POSITION add IS_TRANSFER VARCHAR(2)  COMMENT '是否母行划转产品,01是/02否';
alter table CHB_MONTH_PRD_POSITION add IS_NOTICE VARCHAR(2)  COMMENT  '是否完全符合《通知》要求,01是/02否';
alter table CHB_MONTH_PRD_POSITION modify   COLUMN  YIELD_E DECIMAL(30,14)  COMMENT   '收益率%';
alter table CHB_MONTH_PRD_POSITION modify   COLUMN TERMDAY DECIMAL(10,2)   COMMENT  '期限(天)';
alter table CHB_MONTH_PRD_POSITION modify   COLUMN  TERMTYPE2 VARCHAR(20)  COMMENT  '期限品种2 01 T+0产品/02 7天（含）以内/03 7天-1个月（含）/04 1-3个月（含）/05 3-6 25个月(含)/06 6-12个月（含）/08 1-3年（含）/09 3年以上';

/***** 修改月度报表资产头寸表 *****/
alter table CHB_MONTH_ASSET_POSITION modify   COLUMN  TERMS DECIMAL (30,14) COMMENT '剩余期限';
alter table CHB_MONTH_ASSET_POSITION modify   COLUMN  SUNASSETTYPE VARCHAR(4) COMMENT '子公司月报资产一级分类';
alter table CHB_MONTH_ASSET_POSITION modify   COLUMN  SUNASSETTYPE2 VARCHAR(6) COMMENT '子公司月报资产二级分类';


/***** 新增子公司月报主表 *****/
create table chb_month_report_sub
(
    REPORTUUID             char(36)    not null comment '主键'
        primary key,
    TERMID                 varchar(20) null comment '期数',
    REPORTTYPE             varchar(20) null comment '报表类型：MONTH-月报',
    STARTDATE              datetime    null comment '开始日期',
    ENDDATE                datetime    null comment '结束日期',
    ISSUE_PARTY_ID         varchar(50) null comment '发行机构代码',
    ISSUE_PARTY_NAME       varchar(50) null comment '发行机构名称',
    REPORTFREQU            varchar(2)  null comment '报表频率',
    SUBMIT_TIME            datetime    null comment '报送日期',
    CHB_SUBMIT_DEADLINE    datetime    null comment '中债报送截止日',
    SUBMIT_DEADLINE        datetime    null comment '行内报送截止日',
    SUBMIT_FEEDBACK_STATUS varchar(50) null comment '返回信息',
    STATUS                 varchar(50) null comment '有效状态',
    SEND_STATUS            varchar(20) null comment '报送状态',
    STATUS_DATE            datetime    null comment '数据日期',
    CREATE_USER            varchar(20) null comment '创建人',
    CREATE_TIME            datetime    null comment '创建时间',
    UPDATE_USER            varchar(20) null comment '更新人',
    UPDATE_TIME            datetime    null comment '更新时间',
    ORG_CODE               varchar(20) null comment '机构代码',
    DEPT_CODE              varchar(20) null comment '部门代码',
    CREATE_DEPT            varchar(32) null comment '创建部门'
)
    comment '子公司月报主表';
	
create table chb_month_report_sub_his
(
    REPORTUUID             char(36)    not null comment '主键'
        primary key,
    TERMID                 varchar(20) null comment '期数',
    REPORTTYPE             varchar(20) null comment '报表类型：MONTH-月报',
    STARTDATE              datetime    null comment '开始日期',
    ENDDATE                datetime    null comment '结束日期',
    ISSUE_PARTY_ID         varchar(50) null comment '发行机构代码',
    ISSUE_PARTY_NAME       varchar(50) null comment '发行机构名称',
    REPORTFREQU            varchar(2)  null comment '报表频率',
    SUBMIT_TIME            datetime    null comment '报送日期',
    CHB_SUBMIT_DEADLINE    datetime    null comment '中债报送截止日',
    SUBMIT_DEADLINE        datetime    null comment '行内报送截止日',
    SUBMIT_FEEDBACK_STATUS varchar(50) null comment '返回信息',
    STATUS                 varchar(50) null comment '有效状态',
    SEND_STATUS            varchar(20) null comment '报送状态',
    STATUS_DATE            datetime    null comment '数据日期',
    CREATE_USER            varchar(20) null comment '创建人',
    CREATE_TIME            datetime    null comment '创建时间',
    UPDATE_USER            varchar(20) null comment '更新人',
    UPDATE_TIME            datetime    null comment '更新时间',
    ORG_CODE               varchar(20) null comment '机构代码',
    DEPT_CODE              varchar(20) null comment '部门代码',
    CREATE_DEPT            varchar(32) null comment '创建部门'
)
    comment '子公司月报主表' ;

create table chb_month_report_sub_template
(
    TEMPUUID     char(36)     not null comment '主键'
        primary key,
    TEMPLATEID   char(36)     null comment '模版编号',
    PROPERTYID   char(36)     null comment '属性编号',
    PROPERTYCODE varchar(20)  null comment '属性代码',
    PROPERNAME   varchar(100) null comment '属性名称',
    ORDERNO      bigint       null comment '排序',
    REPORTTYPE   varchar(10)  null comment '报表类型：PROD-产品，BALANCE-资产',
    REPORTCYCLE  varchar(2)   null comment '报表周期：D-日，WE-周报，M-月报，S-季，Y-年',
    STATUS       varchar(2)   null comment '状态'
)
    comment '子公司月报模版表';	
	
	create table chb_month_report_sub_detail
(
    DETAILUUID     char(36)        not null
        primary key,
    TERMID         varchar(20)     null comment '期数',
    REPORTTYPE     varchar(10)     null comment '报表类型：PROD-产品，ASSET-资产',
    PROPERTYCODE   varchar(20)     null comment '属性代码',
    BALANCE        decimal(24, 6)  null comment '余额',
    TERMS          decimal(30, 14) null comment '加权价格/剩余期限',
    BALANCE_ACC    decimal(24, 6)  null comment '符合《通知》余额',
    TERMS_ACC      decimal(30, 14) null comment '符合《通知》加权价格',
    BALANCE_INC    decimal(24, 6)  null comment '不符合《通知》余额',
    TERMS_INC      decimal(30, 14) null comment '不符合《通知》加权价格',
    BALANCE_BAN    decimal(24, 6)  null comment '母行余额',
    PRODCOUNTS_BAN int             null comment '母行只数',
    TERMS_BAN      decimal(30, 14) null comment '母行加权价格/剩余期限',
    BALANCE_SUB    decimal(24, 6)  null comment '子公司余额',
    PRODCOUNTS_SUB int             null comment '子公司只数',
    TERMS_SUB      decimal(30, 14) null comment '子公司加权价格/剩余期限',
    ORG_CODE       varchar(20)     null comment '机构代码',
    DEPT_CODE      varchar(20)     null comment '部门代码',
    REPORTUUID     char(36)        null comment '月报主表主键'
)
    comment '子公司月报明细表';