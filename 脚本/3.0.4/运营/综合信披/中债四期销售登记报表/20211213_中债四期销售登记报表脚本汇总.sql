--各表增加字段
ALTER TABLE CHB_EQU_HOLD ADD FUND_ACC_PLACE VARCHAR2(10);
comment on column CHB_EQU_HOLD.FUND_ACC_PLACE is '账号开户所在地';
ALTER TABLE CHB_EQU_HOLD ADD HOLDER_CLASSIFY VARCHAR2(10);
comment on column CHB_EQU_HOLD.HOLDER_CLASSIFY is '投资者类型:01 不特定社会公众; 02 合格投资者';

ALTER TABLE CHB_EQU_HOLD_HIS ADD FUND_ACC_PLACE VARCHAR2(10);
comment on column CHB_EQU_HOLD_HIS.FUND_ACC_PLACE is '账号开户所在地';
ALTER TABLE CHB_EQU_HOLD_HIS ADD HOLDER_CLASSIFY VARCHAR2(10);
comment on column CHB_EQU_HOLD_HIS.HOLDER_CLASSIFY is '投资者类型:01 不特定社会公众; 02 合格投资者';

ALTER TABLE CHB_EQU_HOLD_CHECK ADD FUND_ACC_PLACE VARCHAR2(10);
comment on column CHB_EQU_HOLD_CHECK.FUND_ACC_PLACE is '账号开户所在地';
ALTER TABLE CHB_EQU_HOLD_CHECK ADD HOLDER_CLASSIFY VARCHAR2(10);
comment on column CHB_EQU_HOLD_CHECK.HOLDER_CLASSIFY is '投资者类型:01 不特定社会公众; 02 合格投资者';

ALTER TABLE CHB_EQU_SUB ADD FUND_ACC_PLACE VARCHAR2(10);
comment on column CHB_EQU_SUB.FUND_ACC_PLACE is '账号开户所在地';
ALTER TABLE CHB_EQU_SUB ADD HOLDER_CLASSIFY VARCHAR2(10);
comment on column CHB_EQU_SUB.HOLDER_CLASSIFY is '投资者类型:01 不特定社会公众; 02 合格投资者';

ALTER TABLE CHB_EQU_SUB_HIS ADD FUND_ACC_PLACE VARCHAR2(10);
comment on column CHB_EQU_SUB_HIS.FUND_ACC_PLACE is '账号开户所在地';
ALTER TABLE CHB_EQU_SUB_HIS ADD HOLDER_CLASSIFY VARCHAR2(10);
comment on column CHB_EQU_SUB_HIS.HOLDER_CLASSIFY is '投资者类型:01 不特定社会公众; 02 合格投资者';

ALTER TABLE CHB_EQUDET_SUB ADD ORG_INDENTIFY_MARK VARCHAR2(30);
comment on column CHB_EQUDET_SUB.ORG_INDENTIFY_MARK is '合并前识别标识';

ALTER TABLE CHB_EQU_HOLD ADD CHB_EQUDET_SUB_HIS VARCHAR2(30);
comment on column CHB_EQU_HOLD.CHB_EQUDET_SUB_HIS is '合并前识别标识';

drop table CHB_SALER_STL_DEAL_INFO;
create table CHB_SALER_STL_DEAL_INFO(
  REGIST_SEQNO	VARCHAR2(36) NOT NULL
  ,DATA_STATUS	VARCHAR2(10)
  ,INSTITUTION_CODE	VARCHAR2(20)
  ,PRD_REGIST_CODE	VARCHAR2(20)
  ,ACCOUNT_NO	VARCHAR2(60)
  ,ACCOUNT_NAME	VARCHAR2(200)
  ,TRADE_NO	VARCHAR2(100)
  ,TRADE_PS	VARCHAR2(4)
  ,TRADE_COUNTER_ACC_NO	VARCHAR2(60)
  ,TRADE_COUNTER_ACC_NAME	VARCHAR2(200)
  ,TRADE_COUNTER_ACC_BANK_NAME	VARCHAR2(200)
  ,TRADE_COUNTER_ACC_BANK_NO	VARCHAR2(60)
  ,TRADE_CCY	VARCHAR2(20)
  ,TRADE_AMT	NUMBER(30,8)
  ,TRADE_VDATE	DATE
  ,TRADE_TIME	VARCHAR2(6)
  ,POSTSCRIPT	VARCHAR2(600)
  ,REMARK	VARCHAR2(1000)
  ,RECEPT_DATE	DATE
  ,CREATE_USER	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_DEPT	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(20)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_STL_DEAL_INFO is '销售结算资金交易信息表';
comment on column CHB_SALER_STL_DEAL_INFO.REGIST_SEQNO is '主键uuid ';
comment on column CHB_SALER_STL_DEAL_INFO.DATA_STATUS is '数据状态 ';
comment on column CHB_SALER_STL_DEAL_INFO.INSTITUTION_CODE is '提供销售结算资金服务机构代码 ';
comment on column CHB_SALER_STL_DEAL_INFO.PRD_REGIST_CODE is '产品登记编码 ';
comment on column CHB_SALER_STL_DEAL_INFO.ACCOUNT_NO is '销售结算资金账号 ';
comment on column CHB_SALER_STL_DEAL_INFO.ACCOUNT_NAME is '销售结算资金账户名称 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_NO is '销售结算资金交易流水号 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_PS is '销售结算资金流动方向 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_COUNTER_ACC_NO is '销售结算资金交易对手方账号 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_COUNTER_ACC_NAME is '销售结算资金交易对手方账户名称 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_COUNTER_ACC_BANK_NAME is '销售结算资金交易对手方开户行名称 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_COUNTER_ACC_BANK_NO is '销售结算资金交易对手方账户开户行号 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_CCY is '销售结算资金交易币种 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_AMT is '销售结算资金交易金额 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_VDATE is '销售结算资金交易日期 ';
comment on column CHB_SALER_STL_DEAL_INFO.TRADE_TIME is '销售结算资金交易时间 ';
comment on column CHB_SALER_STL_DEAL_INFO.POSTSCRIPT is '销售结算资金交易附言 ';
comment on column CHB_SALER_STL_DEAL_INFO.REMARK is '备注 ';
comment on column CHB_SALER_STL_DEAL_INFO.RECEPT_DATE is '数据接入日期 ';
comment on column CHB_SALER_STL_DEAL_INFO.CREATE_USER is '创建人 ';
comment on column CHB_SALER_STL_DEAL_INFO.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_STL_DEAL_INFO.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_STL_DEAL_INFO.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_STL_DEAL_INFO.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_STL_DEAL_INFO.UPDATE_TIME is '更新时间 ';
  
DROP TABLE CHB_SALER_STL_DEA_ERRODATA;
create table CHB_SALER_STL_DEA_ERRODATA(
     REGIST_SEQNO            VARCHAR2(36) not null
	,INSTITUTION_CODE	VARCHAR2(40)
	,PRD_REGIST_CODE	VARCHAR2(40)
	,ACCOUNT_NO	VARCHAR2(120)
	,TRADE_PS	VARCHAR2(8)
	,TRADE_NO	VARCHAR2(200)
	,TRADE_VDATE	DATE
	,DATA_STATUS	VARCHAR2(10)
	,ORG_DATA	CLOB
	,ERR_MSG	CLOB
	,WORKFLOW_ID	VARCHAR2(20)
	,ORG_CODE	VARCHAR2(20)
	,DEPT_CODE	VARCHAR2(32)
	,CREATE_DEPT	VARCHAR2(32)
	,UPDATE_USER	VARCHAR2(20)
	,CREATE_USER	VARCHAR2(32)
	,UPDATE_TIME	TIMESTAMP(6)
	,CREATE_TIME	TIMESTAMP(6)
);

comment on table CHB_SALER_STL_DEA_ERRODATA is '销售结算资金交易信息错误信息表';
comment on column CHB_SALER_STL_DEA_ERRODATA.REGIST_SEQNO IS '主键 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.INSTITUTION_CODE IS '提供销售结算资金服务机构代码 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.PRD_REGIST_CODE IS '产品登记编码 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.ACCOUNT_NO IS '销售结算资金账号 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.TRADE_PS IS '销售结算资金流动方向 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.TRADE_NO IS '销售结算资金交易流水号 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.TRADE_VDATE IS '销售结算资金交易日期 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.DATA_STATUS IS '数据状态 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.ORG_DATA IS '原始数据 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.ERR_MSG IS '错误信息 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.WORKFLOW_ID IS '工作流代码 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.ORG_CODE IS '机构代码 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.DEPT_CODE IS '部门代码 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.CREATE_DEPT IS '创建部门 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.UPDATE_USER IS '更新人 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.CREATE_USER IS '创建人 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.UPDATE_TIME IS '更新时间 ';
comment on column CHB_SALER_STL_DEA_ERRODATA.CREATE_TIME IS '创建时间 ';



DROP TABLE CHB_SALER_STL_DEAL_SUB;
create table CHB_SALER_STL_DEAL_SUB(
  REGIST_SEQNO	VARCHAR2(36) NOT NULL
  ,DATA_STATUS	VARCHAR2(10)
  ,INSTITUTION_CODE	VARCHAR2(20)
  ,PRD_REGIST_CODE	VARCHAR2(20)
  ,ACCOUNT_NO	VARCHAR2(60)
  ,ACCOUNT_NAME	VARCHAR2(200)
  ,TRADE_NO	VARCHAR2(100)
  ,TRADE_PS	VARCHAR2(4)
  ,TRADE_COUNTER_ACC_NO	VARCHAR2(60)
  ,TRADE_COUNTER_ACC_NAME	VARCHAR2(200)
  ,TRADE_COUNTER_ACC_BANK_NAME	VARCHAR2(200)
  ,TRADE_COUNTER_ACC_BANK_NO	VARCHAR2(60)
  ,TRADE_CCY	VARCHAR2(20)
  ,TRADE_AMT	NUMBER(30,8)
  ,TRADE_VDATE	DATE
  ,TRADE_TIME	VARCHAR2(6)
  ,POSTSCRIPT	VARCHAR2(600)
  ,REMARK	VARCHAR2(1000)
  ,CHB_SUBMIT_DEADLINE  TIMESTAMP(6)
  ,SUBMIT_DEADLINE  TIMESTAMP(6)
  ,STATUS_DATE  DATE
  ,SUBMIT_TIME  TIMESTAMP(6)
  ,SUMIT_SEQ   VARCHAR2(50)
  ,SEND_STATUS  VARCHAR2(2)
  ,SUBMIT_FEEDBACK_STATUS  VARCHAR2(50)
  ,SUBMIT_FEEDBACK_MSG VARCHAR2(600)
  ,CREATE_USER	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_DEPT	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(20)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_STL_DEAL_SUB is '销售结算资金交易信息登记表';
comment on column CHB_SALER_STL_DEAL_SUB.REGIST_SEQNO is '主键uuid ';
comment on column CHB_SALER_STL_DEAL_SUB.DATA_STATUS is '数据状态 ';
comment on column CHB_SALER_STL_DEAL_SUB.INSTITUTION_CODE is '提供销售结算资金服务机构代码 ';
comment on column CHB_SALER_STL_DEAL_SUB.PRD_REGIST_CODE is '产品登记编码 ';
comment on column CHB_SALER_STL_DEAL_SUB.ACCOUNT_NO is '销售结算资金账号 ';
comment on column CHB_SALER_STL_DEAL_SUB.ACCOUNT_NAME is '销售结算资金账户名称 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_NO is '销售结算资金交易流水号 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_PS is '销售结算资金流动方向 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_COUNTER_ACC_NO is '销售结算资金交易对手方账号 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_COUNTER_ACC_NAME is '销售结算资金交易对手方账户名称 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_COUNTER_ACC_BANK_NAME is '销售结算资金交易对手方开户行名称 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_COUNTER_ACC_BANK_NO is '销售结算资金交易对手方账户开户行号 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_CCY is '销售结算资金交易币种 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_AMT is '销售结算资金交易金额 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_VDATE is '销售结算资金交易日期 ';
comment on column CHB_SALER_STL_DEAL_SUB.TRADE_TIME is '销售结算资金交易时间 ';
comment on column CHB_SALER_STL_DEAL_SUB.POSTSCRIPT is '销售结算资金交易附言 ';
comment on column CHB_SALER_STL_DEAL_SUB.REMARK is '备注 ';
comment on column CHB_SALER_STL_DEAL_SUB.CHB_SUBMIT_DEADLINE is '中债报送截止日 ';
comment on column CHB_SALER_STL_DEAL_SUB.SUBMIT_DEADLINE is '报送截止日 ';
comment on column CHB_SALER_STL_DEAL_SUB.STATUS_DATE is '数据日期 ';
comment on column CHB_SALER_STL_DEAL_SUB.SUBMIT_TIME is '报送时间 ';
comment on column CHB_SALER_STL_DEAL_SUB.SUMIT_SEQ is '报送流水号 ';
comment on column CHB_SALER_STL_DEAL_SUB.SEND_STATUS is '报送状态 ';
comment on column CHB_SALER_STL_DEAL_SUB.SUBMIT_FEEDBACK_STATUS is '反馈状态 ';
comment on column CHB_SALER_STL_DEAL_SUB.SUBMIT_FEEDBACK_MSG is '报送反馈消息 ';
comment on column CHB_SALER_STL_DEAL_SUB.CREATE_USER is '创建人 ';
comment on column CHB_SALER_STL_DEAL_SUB.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_STL_DEAL_SUB.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_STL_DEAL_SUB.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_STL_DEAL_SUB.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_STL_DEAL_SUB.UPDATE_TIME is '更新时间 ';


DROP TABLE CHB_SALER_STL_DEAL_SUB_HIS;
create table CHB_SALER_STL_DEAL_SUB_HIS(
  HIS_NO	VARCHAR2(36) NOT NULL
  ,DATA_STATUS	VARCHAR2(10)
  ,INSTITUTION_CODE	VARCHAR2(20)
  ,PRD_REGIST_CODE	VARCHAR2(20)
  ,ACCOUNT_NO	VARCHAR2(60)
  ,ACCOUNT_NAME	VARCHAR2(200)
  ,TRADE_NO	VARCHAR2(100)
  ,TRADE_PS	VARCHAR2(4)
  ,TRADE_COUNTER_ACC_NO	VARCHAR2(60)
  ,TRADE_COUNTER_ACC_NAME	VARCHAR2(200)
  ,TRADE_COUNTER_ACC_BANK_NAME	VARCHAR2(200)
  ,TRADE_COUNTER_ACC_BANK_NO	VARCHAR2(60)
  ,TRADE_CCY	VARCHAR2(20)
  ,TRADE_AMT	NUMBER(30,8)
  ,TRADE_VDATE	DATE
  ,TRADE_TIME	VARCHAR2(6)
  ,POSTSCRIPT	VARCHAR2(600)
  ,REMARK	VARCHAR2(1000)
  ,CHB_SUBMIT_DEADLINE  TIMESTAMP(6)
  ,SUBMIT_DEADLINE  TIMESTAMP(6)
  ,STATUS_DATE  DATE
  ,SUBMIT_TIME  TIMESTAMP(6)
  ,SUMIT_SEQ   VARCHAR2(50)
  ,SEND_STATUS  VARCHAR2(2)
  ,SUBMIT_FEEDBACK_STATUS  VARCHAR2(50)
  ,SUBMIT_FEEDBACK_MSG VARCHAR2(600)
  ,CREATE_USER	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_DEPT	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(20)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_STL_DEAL_SUB_HIS is '销售结算资金交易信息登记履历表';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.HIS_NO is '履历番号 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.DATA_STATUS is '数据状态 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.INSTITUTION_CODE is '提供销售结算资金服务机构代码 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.PRD_REGIST_CODE is '产品登记编码 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.ACCOUNT_NO is '销售结算资金账号 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.ACCOUNT_NAME is '销售结算资金账户名称 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_NO is '销售结算资金交易流水号 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_PS is '销售结算资金流动方向 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_COUNTER_ACC_NO is '销售结算资金交易对手方账号 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_COUNTER_ACC_NAME is '销售结算资金交易对手方账户名称 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_COUNTER_ACC_BANK_NAME is '销售结算资金交易对手方开户行名称 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_COUNTER_ACC_BANK_NO is '销售结算资金交易对手方账户开户行号 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_CCY is '销售结算资金交易币种 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_AMT is '销售结算资金交易金额 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_VDATE is '销售结算资金交易日期 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.TRADE_TIME is '销售结算资金交易时间 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.POSTSCRIPT is '销售结算资金交易附言 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.REMARK is '备注 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.CHB_SUBMIT_DEADLINE is '中债报送截止日 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.SUBMIT_DEADLINE is '报送截止日 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.STATUS_DATE is '数据日期 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.SUBMIT_TIME is '报送时间 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.SUMIT_SEQ is '报送流水号 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.SEND_STATUS is '报送状态 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.SUBMIT_FEEDBACK_STATUS is '反馈状态 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.SUBMIT_FEEDBACK_MSG is '报送反馈消息 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.CREATE_USER is '创建人 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_STL_DEAL_SUB_HIS.UPDATE_TIME is '更新时间 ';

DROP TABLE CHB_SALE_DEAL_SUM;
create table CHB_SALE_DEAL_SUM(
  PRD_REGIST_CODE VARCHAR2(20) NOT NULL
  ,PROD_ID	VARCHAR2(36)
  ,INSTITUTION_CODE	VARCHAR2(20) NOT NULL
  ,AREA_CODE	VARCHAR2(50) NOT NULL
  ,HOLDER_TYPE	CHAR(2 BYTE) NOT NULL
  ,CCY	CHAR(3 BYTE)
  ,ISAGENCY	CHAR(2 BYTE)
  ,DAY_SALE_AMOUNT_SUM	NUMBER(36,8)
  ,DAY_VALUE_AMOUNT_SUM	NUMBER(36,8)
  ,PROD_VALUE	NUMBER(36,8)
  ,DAY_SALE_QTY_SUM	NUMBER(36,8)
  ,DAY_VALUE_QTY_SUM	NUMBER(36,8)
  ,SALE_TOTAL_BY_QTY	NUMBER(36,8)
  ,VALUE_TOTAL_BY_QTY	NUMBER(30,8)
  ,HOLDER_NUM	NUMBER(18,5)
  ,HOLDER_BALANCE_AMT	NUMBER(36,8)
  ,REMARK	VARCHAR2(1000)
  ,CREATE_USER	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_DEPT	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(20)
  ,UPDATE_TIME	TIMESTAMP(6)
  ,EQUITY_CONF_DATE DATE
);
  
comment on table CHB_SALE_DEAL_SUM is '理财业务销售数据基础汇总表';
comment on column CHB_SALE_DEAL_SUM.PRD_REGIST_CODE is '产品登记编码 ';
comment on column CHB_SALE_DEAL_SUM.PROD_ID is '产品代码 ';
comment on column CHB_SALE_DEAL_SUM.INSTITUTION_CODE is '销售机构代码 ';
comment on column CHB_SALE_DEAL_SUM.AREA_CODE is '地区类型 ';
comment on column CHB_SALE_DEAL_SUM.HOLDER_TYPE is '投资者类型 ';
comment on column CHB_SALE_DEAL_SUM.CCY is '币种 ';
comment on column CHB_SALE_DEAL_SUM.ISAGENCY is '是否代销 ';
comment on column CHB_SALE_DEAL_SUM.DAY_SALE_AMOUNT_SUM is '汇总-总销售金额 ';
comment on column CHB_SALE_DEAL_SUM.DAY_VALUE_AMOUNT_SUM is '汇总-净销售金额 ';
comment on column CHB_SALE_DEAL_SUM.PROD_VALUE is '确认净值 ';
comment on column CHB_SALE_DEAL_SUM.DAY_SALE_QTY_SUM is '汇总-总销售份额 ';
comment on column CHB_SALE_DEAL_SUM.DAY_VALUE_QTY_SUM is '汇总-净销售份额 ';
comment on column CHB_SALE_DEAL_SUM.SALE_TOTAL_BY_QTY is '汇总-总销售金额（份额*净值） ';
comment on column CHB_SALE_DEAL_SUM.VALUE_TOTAL_BY_QTY is '汇总-净销售金额（份额*净值） ';
comment on column CHB_SALE_DEAL_SUM.HOLDER_NUM is '投资者数量 ';
comment on column CHB_SALE_DEAL_SUM.HOLDER_BALANCE_AMT is '持有余额 ';
comment on column CHB_SALE_DEAL_SUM.REMARK is '备注 ';
comment on column CHB_SALE_DEAL_SUM.CREATE_USER is '创建人 ';
comment on column CHB_SALE_DEAL_SUM.ORG_CODE is '机构代码 ';
comment on column CHB_SALE_DEAL_SUM.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALE_DEAL_SUM.CREATE_TIME is '创建时间 ';
comment on column CHB_SALE_DEAL_SUM.UPDATE_USER is '更新人 ';
comment on column CHB_SALE_DEAL_SUM.UPDATE_TIME is '更新时间 ';
comment on column CHB_SALE_DEAL_SUM.EQUITY_CONF_DATE is '业务确认日期 ';

DROP TABLE CHB_SALE_MONTH_REPORT_DETAIL;
create table CHB_SALE_MONTH_REPORT_DETAIL(
	DETAILUUID	VARCHAR2(36） NOT NULL
	,REPORTUUID	VARCHAR2(36）
	,REPORTTYPE	VARCHAR2(20）
	,PRD_REGIST_CODE	VARCHAR2(20）
	,IS_CONSIGN	CHAR(2)
	,INSTITUTION_CODE	VARCHAR2(20）
	,AREA_CODE	VARCHAR2(50)
	,HOLDER_TYPE	CHAR(2)
	,HOLDER_NUM	NUMBER(18,5)
	,SALE_AMOUNT_TOTAL	NUMBER(38,10)
	,VALUE_AMOUNT_TOTAL	NUMBER(38,10)
	,BALANCE_AMT            NUMBER(38,10)
	,BALANCE_SHARES 	NUMBER(38,10)
	,SALE_SHARES_TOTAL	NUMBER(38,10)
	,VALUE_SHARES_TOTAL     NUMBER(38,10)
	,ORG_CODE       VARCHAR2(20)
	,DEPT_CODE      VARCHAR2(20)
	,CREATE_DEPT    VARCHAR2(32)
	,UPDATE_USER	VARCHAR2(20)
	,CREATE_USER	VARCHAR2(32)
	,UPDATE_TIME	TIMESTAMP(6)
	,CREATE_TIME	TIMESTAMP(6)
);

comment on table CHB_SALE_MONTH_REPORT_DETAIL is '理财业务销售数据月报明细表';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.DETAILUUID IS '主键 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.REPORTUUID IS '关联REPORT外键 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.REPORTTYPE IS '报表类型：1_1分投资者类型,1_2分地区类型,1_3分销售渠道 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.PRD_REGIST_CODE IS '产品登记编码 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.IS_CONSIGN IS '是否代销 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.INSTITUTION_CODE IS '销售机构代码 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.AREA_CODE IS '地区类型 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.HOLDER_TYPE IS '投资者类型 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.HOLDER_NUM IS '投资者数量 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.SALE_AMOUNT_TOTAL IS '总销售金额 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.VALUE_AMOUNT_TOTAL IS '净销售金额 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.BALANCE_AMT IS '持有余额 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.BALANCE_SHARES IS '持有份额 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.SALE_SHARES_TOTAL IS '总销售份额 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.VALUE_SHARES_TOTAL IS '净销售份额 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.ORG_CODE IS '机构代码 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.DEPT_CODE IS '部门代码 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.CREATE_DEPT IS '创建部门 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.UPDATE_USER IS '更新人 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.CREATE_USER IS '创建人 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.UPDATE_TIME IS '更新时间 ';
comment on column CHB_SALE_MONTH_REPORT_DETAIL.CREATE_TIME IS '创建时间 ';
alter table  CHB_SALE_MONTH_REPORT_DETAIL  add  primary key（DETAILUUID）;


DROP TABLE CHB_SALE_MONTH_REPORT;
CREATE TABLE
    CHB_SALE_MONTH_REPORT
    (
        TERMID VARCHAR2(20),
        REPORTTYPE VARCHAR2(20),
        REPROT_NAME VARCHAR2(100 BYTE),
        REPORT_DATE DATE,
        STARTDATE DATE,
        ENDDATE DATE,
        ISSUE_PARTY_ID VARCHAR2(50),
        ISSUE_PARTY_NAME VARCHAR2(50),
        REPORTFREQU VARCHAR2(2),
        SUBMIT_TIME DATE,
        CHB_SUBMIT_DEADLINE DATE,
        SUBMIT_DEADLINE DATE,
        SUBMIT_FEEDBACK_STATUS VARCHAR2(50),
        DATA_STATUS VARCHAR2(50),
        REPORTUUID CHAR(36) NOT NULL,
        SEND_STATUS VARCHAR2(20),
        STATUS_DATE DATE,
        CREATE_USER VARCHAR2(20),
        CREATE_TIME TIMESTAMP(6),
        UPDATE_USER VARCHAR2(20),
        UPDATE_TIME TIMESTAMP(6),
        ORG_CODE VARCHAR2(20),
        DEPT_CODE VARCHAR2(20),
        CREATE_DEPT VARCHAR2(32),
        CONSTRAINT CHB_SALE_MONTH_REPORT_PK PRIMARY KEY (REPORTUUID)
    );
COMMENT ON TABLE CHB_SALE_MONTH_REPORT
IS
    '月报表';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.TERMID
IS
    '期数';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.REPORTTYPE
IS
    '报表类型：1_1 ：分投资者类型，1_2 ：分地区类型，1_3 : 分销售渠道';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.REPROT_NAME
IS
    '报表名称';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.REPORT_DATE
IS
    '制表日期';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.STARTDATE
IS
    '开始日期';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.ENDDATE
IS
    '结束日期';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.ISSUE_PARTY_ID
IS
    '发行机构代码';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.ISSUE_PARTY_NAME
IS
    '发行机构名称';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.REPORTFREQU
IS
    '报表频率';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.SUBMIT_TIME
IS
    '报送日期';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.CHB_SUBMIT_DEADLINE
IS
    '中债报送截止日';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.SUBMIT_DEADLINE
IS
    '行内报送截止日';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.SUBMIT_FEEDBACK_STATUS
IS
    '返回信息';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.DATA_STATUS
IS
    '有效状态';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.SEND_STATUS
IS
    '报送状态';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.STATUS_DATE
IS
    '数据日期';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.CREATE_USER
IS
    '创建人';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.CREATE_TIME
IS
    '创建时间';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.UPDATE_USER
IS
    '更新人';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.UPDATE_TIME
IS
    '更新时间';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.ORG_CODE
IS
    '机构代码';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.DEPT_CODE
IS
    '部门代码';
COMMENT ON COLUMN CHB_SALE_MONTH_REPORT.CREATE_DEPT
IS
    '创建部门';
    
    
DELETE FROM SYS_PARA WHERE PARA_CODE = 'HAS_HOLDER_CLASSIFY';
insert into SYS_PARA (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, UPDATE_FLG)
values ('SVR', 'HAS_HOLDER_CLASSIFY', '01', null, '三期是否校验投资者分类字段', '01 验证字段,02不验证字段', 'system', null, SYSDATE, 'system', SYSDATE, null, '01');

  
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'UPINTERF_FILE_TYPE' AND ITEM_CODE = '35';
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('UPINTERF_FILE_TYPE', '35', '销售结算资金交易信息表', 35, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);

DELETE FROM CHB_INTERF_CONFIG WHERE INTERF_ID = 'CHB_SALER_STL_DEAL_INFO';
insert into CHB_INTERF_CONFIG (INTERF_ID, LAST_UPDATETIME, REMARK, INTERF_NAME, CONTENT_TYPE, CONTENT_SRC, REF_REPORT, SYS_SCOPE, STATUS)
values ('CHB_SALER_STL_DEAL_INFO', DATE '2021-01-01', '销售结算资金交易信息表报送数据', null, 'F', 'T', '', '110', 'E');

DELETE FROM SYS_BATCH_TASK WHERE TASK_ID = '3053';
insert into SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, IS_BATCH_START_TARGET)
values ('3053', 'SVR', '销售结算资金交易信息表报送接口数据接入', null, 'svrChbSalerStlDealInfoInterfTastService', 3000, 'system', null, sysdate, 'system', sysdate, null);

--销售结算资金流动方向枚举值
DELETE FROM SYS_DATADICT WHERE DICT_CODE = 'TRADE_PS';
insert into SYS_DATADICT (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('TRADE_PS', 'SYS', '销售结算资金流动方向 01流出/02流入', 'TRADE_PS', '02', 'system', null, SYSDATE, 'system', SYSDATE);
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'TRADE_PS';
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('TRADE_PS', '01', '流出', 1, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('TRADE_PS', '02', '流入', 2, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);

--批处理配置
DELETE FROM SYS_BATCH_TASK WHERE TASK_ID = '3054';
insert into SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, IS_BATCH_START_TARGET)
values ('3054', 'SVR', '销售结算资金交易登记报表生成', null, 'svrSalesTransactionInterfTaskService', 3200, 'system', null, sysdate, 'system', sysdate, null);

--销售结算资金交易信息登记获取报送截止日配置
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE = '34';
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_CHECK_INTERF_TYPE' and ITEM_CODE = 'CHB_SALER_STL_DEAL_SUB';
insert into SYS_DATADICT_LIST VALUES ('SVR_REPORT_CODE','34','销售结算资金交易信息登记','34','0','system',null,sysdate,'system',sysdate,null);
insert into SYS_DATADICT_LIST VALUES ('SVR_CHECK_INTERF_TYPE','CHB_SALER_STL_DEAL_SUB','销售结算资金交易信息登记','20','0','system',null,sysdate,'system',sysdate,null);
DELETE FROM CHB_REG_TIME_POINTS WHERE CHB_REG_TIME_POINTS.SEQNO = '34';
INSERT INTO CHB_REG_TIME_POINTS VALUES('34',null,'34',null,'03','03','02','业务发生后N个工作日','1','1','12','4','E',null,null,'qq2',sysdate,'system',sysdate,null);

--销售结算资金交易信息维护菜单配置
DELETE FROM SYS_PAGE WHERE PAGE_ID = 'SalesTransactionMaintenance001';
DELETE FROM SYS_PAGE WHERE PAGE_ID = 'SalesTransactionMaintenance002';
delete from SYS_MENU where MENU_ID = 'REPORT_MAINTAIN_001_004_010';
INSERT INTO SYS_MENU (MENU_ID,SYSTEM_CODE,ICON,PARENT_MENU_ID,PAGE_ID,ORDER_NO,IS_SPREAD,REMARK,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME)values('REPORT_MAINTAIN_001_004_010','REP',null,'REPORT_MAINTAIN_001_004','SalesTransactionMaintenance001',10,02,'销售结算资金交易信息维护','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SalesTransactionMaintenance001','REP','/report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001','report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001','REPORT_MAINTAIN_001_004_010',null,null,'02','01','销售结算资金交易信息维护','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SalesTransactionMaintenance002','REP','/report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002','report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002',null,null,null,'02','01','销售结算资金交易信息维护新建','system',null,sysdate,'system',sysdate);

--销售结算资金交易信息维护按钮配置
DELETE FROM SYS_PAGE_ELEMENT WHERE PAGE_ID = 'SalesTransactionMaintenance002';
DELETE FROM SYS_PAGE_ELEMENT WHERE PAGE_ID = 'SalesTransactionMaintenance001';
INSERT INTO SYS_PAGE_ELEMENT VALUES('SalesTransactionMaintenance001','SalesTransactionMaintenance001_btn_add','01','01',null,'新建','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SalesTransactionMaintenance001','SalesTransactionMaintenance001_btn_import','01','01',null,'导入','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SalesTransactionMaintenance001','SalesTransactionMaintenance001_btn_delete','01','01',null,'删除','system',null,sysdate,'system',sysdate,'C',null);
--销售结算资金交易信息维护按钮工作流配置
DELETE FROM SYS_WFL_BUSINESS_MODULE WHERE PAGE_ID = 'SalesTransactionMaintenance002';
DELETE FROM SYS_WFL_BUSINESS_MODULE WHERE PAGE_ID = 'SalesTransactionMaintenance001';
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('SalesTransactionMaintenance002','SalesTransactionMaintenance002_btn_submit','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('SalesTransactionMaintenance002','SalesTransactionMaintenance002_btn_save','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('SalesTransactionMaintenance001','SalesTransactionMaintenance001_btn_delete','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');
INSERT INTO SYS_WFL_BUSINESS_MODULE VALUES ('SalesTransactionMaintenance002','SalesTransactionMaintenance001_btn_delete','noneApproval','system',null,sysdate,'system',sysdate,'ALL','02');

--销售结算资金交易信息登记菜单配置
delete from SYS_MENU where MENU_ID = 'REPORT_REGIST_001_002_012';
delete from SYS_PAGE where PAGE_ID = 'SalesTransactionMaintenanceRegist001';
delete from SYS_PAGE where PAGE_ID = 'SalesTransactionMaintenanceRegist002';
INSERT INTO SYS_MENU (MENU_ID,SYSTEM_CODE,ICON,PARENT_MENU_ID,PAGE_ID,ORDER_NO,IS_SPREAD,REMARK,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME)values('REPORT_REGIST_001_002_012','SVR',null,'REPORT_REGIST_001_002','SalesTransactionMaintenanceRegist001',12,02,'销售结算资金交易登记','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SalesTransactionMaintenanceRegist001','REP','/report/submitmanagement/salestransactionmaintenanceregist/SalesTransactionMaintenanceRegist001','report/submitmanagement/salestransactionmaintenanceregist/SalesTransactionMaintenanceRegist001','REPORT_REGIST_001_002_012',null,null,'02','02','销售结算资金交易登记','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SalesTransactionMaintenanceRegist002','REP','/report/submitmanagement/salestransactionmaintenanceregist/SalesTransactionMaintenanceRegist002','report/submitmanagement/salestransactionmaintenanceregist/SalesTransactionMaintenanceRegist002',null,null,null,'02','02','销售结算资金交易登记','system',null,sysdate,'system',sysdate);

--销售结算资金交易信息登记按钮配置
DELETE FROM SYS_PAGE_ELEMENT WHERE PAGE_ID = 'SalesTransactionMaintenanceRegist001';
INSERT INTO SYS_PAGE_ELEMENT VALUES('SalesTransactionMaintenanceRegist001','SalesTransactionMaintenanceRegist001_btn_manual','01','01',null,'手工处理','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SalesTransactionMaintenanceRegist001','SalesTransactionMaintenanceRegist001_btn_canManual','01','01',null,'取消手工处理','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SalesTransactionMaintenanceRegist001','SalesTransactionMaintenanceRegist001_btn_chbexport','01','01',null,'导出中债','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SalesTransactionMaintenanceRegist001','SalesTransactionMaintenanceRegist001_btn_search','01','01',null,'查询','system',null,sysdate,'system',sysdate,'C',null);
--销售月报每日明细汇总批处理配置
DELETE FROM SYS_BATCH_TASK WHERE TASK_ID = '3058';
insert into SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, IS_BATCH_START_TARGET)
values ('3058', 'SVR', '理财业务销售数据月度统计表日头寸批处理', null, 'svrDayCollectInterfTaskService', 3150, 'system', null, sysdate, 'system', sysdate, null);

--销售身份信息其他信息修复批处理配置
DELETE FROM SYS_BATCH_TASK WHERE TASK_ID = '3059';
insert into SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, IS_BATCH_START_TARGET)
values ('3059', 'SVR', '理财业务销售数据月度统计表身份信息修复批处理', null, 'svrIdentityRepairInterfTaskService', 3100, 'system', null, sysdate, 'system', sysdate, null);

--投资者分类枚举值
DELETE from SYS_DATADICT WHERE DICT_CODE =  'HOLDER_CLASSIFY';
delete from SYS_DATADICT_LIST WHERE DICT_CODE = 'HOLDER_CLASSIFY';
INSERT INTO SYS_DATADICT VALUES ('HOLDER_CLASSIFY','REP','投资者分类',null,'02','system',null,SYSDATE,'system',SYSDATE);
INSERT INTO SYS_DATADICT_LIST VALUES ('HOLDER_CLASSIFY','01','不特定社会公众','1','0','system',null,sysdate,'system',sysdate,null);
INSERT INTO SYS_DATADICT_LIST VALUES ('HOLDER_CLASSIFY','02','合格投资者','2','0','system',null,sysdate,'system',sysdate,null);

--销售信息月度统计表生成批处理配置
DELETE FROM SYS_BATCH_TASK WHERE TASK_ID = '3060';
insert into SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, IS_BATCH_START_TARGET)
values ('3060', 'SVR', '理财业务销售数据月度统计表报表生成', null, 'svrMonthCollectInterfTaskService', 3200, 'system', null, sysdate, 'system', sysdate, null);

--理财业务销售数据月度统计表菜单配置
delete from SYS_PAGE where PAGE_ID = 'SellMonthReport001';
delete from SYS_MENU where MENU_ID = 'REPORT_REGIST_001_002_013';
INSERT INTO SYS_MENU (MENU_ID,SYSTEM_CODE,ICON,PARENT_MENU_ID,PAGE_ID,ORDER_NO,IS_SPREAD,REMARK,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME)values('REPORT_REGIST_001_002_013','SVR',null,'REPORT_REGIST_001_002','SellMonthReport001',13,02,'理财业务销售数据月度统计表','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SellMonthReport001','REP','/chb/monthreport/SellMonthReport001','report/chb/monthreport/SellMonthReport001','REPORT_REGIST_001_002_013',null,null,'01','01','理财业务销售数据月度统计表','system',null,sysdate,'system',sysdate);

--理财业务销售数据月度统计表按钮配置
DELETE FROM SYS_PAGE_ELEMENT WHERE PAGE_ID = 'SellMonthReport001';
INSERT INTO SYS_PAGE_ELEMENT VALUES('SellMonthReport001','SellMonthReport001_btn_manual','01','01',null,'手工处理','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SellMonthReport001','SellMonthReport001_btn_cancelManual','01','01',null,'取消手工处理','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SellMonthReport001','SellMonthReport001_btn_chbexport','01','01',null,'导出中债','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SellMonthReport001','SellMonthReport001_btn_search','01','01',null,'查询','system',null,sysdate,'system',sysdate,'C',null);
--销售信息月度统计表批处理生成报送截止日配置
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE = '36';
insert into SYS_DATADICT_LIST VALUES ('SVR_REPORT_CODE','36','销售信息月度统计表','36','0','system',null,sysdate,'system',sysdate,null);
DELETE FROM CHB_REG_TIME_POINTS WHERE CHB_REG_TIME_POINTS.SEQNO = '36';
INSERT INTO CHB_REG_TIME_POINTS VALUES('36',null,'36',null,'03','03','02','业务发生后下个月1号的第N个工作日','7','7','12','4','E',null,null,'qq2',sysdate,'system',sysdate,null);

--销售月表投资者类型枚举值
DELETE FROM SYS_DATADICT WHERE DICT_CODE = 'CUST_TYPE';
insert into SYS_DATADICT (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('CUST_TYPE', 'SYS', '销售月表投资者类型', 'CUST_TYPE', '02', 'system', null, SYSDATE, 'system', SYSDATE);
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'CUST_TYPE';
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '01', '非合格投资者', 1, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '02', '合格投资者', 2, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '03', '银行', 3, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '04', '信托公司', 4, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '05', '证券公司', 5, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '06', '基金公司', 6, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '07', '保险公司', 7, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '08', '期货公司', 8, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '09', '其他金融机构', 9, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '10', 'SPV', 10, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('CUST_TYPE', '99', '其他机构和非法人组织', 99, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);

--销售结算报表类型枚举值
DELETE FROM SYS_DATADICT WHERE DICT_CODE = 'SALE_REPORT_TYPE';
insert into SYS_DATADICT (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('SALE_REPORT_TYPE', 'SYS', '销售结算报表类型', 'SALE_REPORT_TYPE', '02', 'system', null, SYSDATE, 'system', SYSDATE);
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SALE_REPORT_TYPE';
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('SALE_REPORT_TYPE', '1_1', '分投资者类型', 1, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('SALE_REPORT_TYPE', '1_2', '分地区类型', 2, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);
insert into SYS_DATADICT_LIST (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('SALE_REPORT_TYPE', '1_3', '分销售渠道', 3, '0', 'system', null, SYSDATE, 'system', SYSDATE, null);

--销售结算资金交易信息表报送数据接入check脚本
DELETE FROM CHB_DATACHECK_RULE WHERE DATATYPE = 'CHB_SALER_STL_DEAL_INFO';
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000001','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','INSTITUTION_CODE','','','N','提供销售结算资金服务机构代码要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000002','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','INSTITUTION_CODE','','7','Y','提供销售结算资金服务机构代码不能超过6字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000003','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','PRD_REGIST_CODE','','','N','产品登记编码要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000004','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','PRD_REGIST_CODE','','16','Y','产品登记编码不能超过15字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000005','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','ACCOUNT_NO','','','N','销售结算资金账号要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000006','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','ACCOUNT_NO','','61','Y','销售结算资金账号不能超过60字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000007','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','ACCOUNT_NAME','','','N','销售结算资金账户名称要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000008','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','ACCOUNT_NAME','','201','Y','销售结算资金账户名称不能超过200字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000009','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_NO','','','N','销售结算资金交易流水号要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000010','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_NO','','61','Y','销售结算资金交易流水号不能超过60字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000011','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_PS','','','N','销售结算资金流动方向要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000012','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','TRADE_PS','==','TRADE_PS','Y','销售结算资金流动方向不再值域范围内','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000013','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_COUNTER_ACC_NO','','','N','销售结算资金交易对手方账号要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000014','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_COUNTER_ACC_NO','','61','Y','销售结算资金交易对手方账号不能超过60字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000015','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_COUNTER_ACC_NAME','','','N','销售结算资金交易对手方账户名称要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000016','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_COUNTER_ACC_NAME','','201','Y','销售结算资金交易对手方账户名称不能超过200字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000017','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_COUNTER_ACC_BANK_NAME','','','N','销售结算资金交易对手方开户行名称要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000018','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_COUNTER_ACC_BANK_NAME','','201','Y','销售结算资金交易对手方开户行名称不能超过200字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000019','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_COUNTER_ACC_BANK_NO','','','N','销售结算资金交易对手方账户开户行号要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000020','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_COUNTER_ACC_BANK_NO','','13','Y','销售结算资金交易对手方账户开户行号不能超过12字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000021','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_CCY','','','N','销售结算资金交易币种要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000022','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','TRADE_CCY','==','CURRENCY_CHB','Y','销售结算资金交易币种不在值域范围内','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000023','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_AMT','','','N','销售结算资金交易金额要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000024','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNumber2','TRADE_AMT','>=','15,2','Y','销售结算资金交易金额必须为n(15,2)格式。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000025','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','compareValueAsNumber','TRADE_AMT','>','0','Y','销售结算资金交易金额必须大于0','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000026','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_VDATE','','','N','销售结算资金交易日期要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000027','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isValidDate2','TRADE_VDATE','==','yyyy-MM-dd','Y','销售结算资金交易日期必须为日期格式（YYYY-MM-DD）。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000028','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_TIME','','','N','销售结算资金交易时间要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000029','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isValidDate2','TRADE_TIME','','HHmmss','Y','销售结算资金交易时间必须是正常时间','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000030','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','POSTSCRIPT','','201','Y','销售结算资金交易附言不能超过200字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000000031','CHB_SALER_STL_DEAL_INFO','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','REMARK','','201','Y','备注不能超过200字符','E');
DELETE FROM CHB_DATACHECK_RULE WHERE CHECKRULEID in ('CHB_SALER_STL_DEAL_INFO-000000010004','CHB_SALER_STL_DEAL_INFO-000000010005');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000010004', 'CHB_SALER_STL_DEAL_INFO', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'PRD_REGIST_CODE', null, '^([CZ][0-9]{7}[A]?[0-9]{6})$', 'Y', '产品登记编码要素格式不对。正确的格式：6 位金融机构代码；2 位年度码；1 位标识码(可为空);6 位顺序码。', 'E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_INFO-000000010005', 'CHB_SALER_STL_DEAL_INFO', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'PRD_REGIST_CODE', null, '^([CZ][0-9]{5})$', 'Y', '金融机构代码格式不对。请输入正确的金融机构代码', 'E');


--销售结算资金交易信息维护check脚本
DELETE FROM CHB_DATACHECK_RULE_INV WHERE PAGE_ID = 'SalesTransactionMaintenance002';
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-001','SalesTransactionMaintenance002','isNotEmpty','prdRegistCode',null,null,'N','产品登记编码不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-002','SalesTransactionMaintenance002','isNotEmpty','institutionCode',null,null,'N','提供销售结算资金服务机构代码不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-003','SalesTransactionMaintenance002','lessThanCharLength','institutionCode',null,'7','Y','提供销售结算资金服务机构代码不能超过6字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-004','SalesTransactionMaintenance002','lessThanCharLength','prdRegistCode',null,'16','Y','产品登记编码不能超过15字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-005','SalesTransactionMaintenance002','isNotEmpty','accountNo',null,null,'N','销售结算资金账号不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-006','SalesTransactionMaintenance002','lessThanCharLength','accountNo',null,'61','Y','销售结算资金账号不能超过60字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-007','SalesTransactionMaintenance002','isNotEmpty','accountName',null,null,'N','销售结算资金账户名称不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-008','SalesTransactionMaintenance002','lessThanCharLength','accountName',null,'201','Y','销售结算资金账户名称不能超过200字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-009','SalesTransactionMaintenance002','isNotEmpty','tradeNo',null,null,'N','销售结算资金交易流水号不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-010','SalesTransactionMaintenance002','lessThanCharLength','tradeNo',null,'61','Y','销售结算资金交易流水号不能超过60字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-012','SalesTransactionMaintenance002','isNotEmpty','tradePs','==','TRADE_PS','Y','销售结算资金流动方向不可为空或不再值域范围内。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-013','SalesTransactionMaintenance002','isNotEmpty','tradeCounterAccNo',null,null,'N','销售结算资金交易对手方账号不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-014','SalesTransactionMaintenance002','lessThanCharLength','tradeCounterAccNo',null,'61','Y','销售结算资金交易对手方账号不能超过60字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-015','SalesTransactionMaintenance002','isNotEmpty','tradeCounterAccName',null,null,'N','销售结算资金交易对手方账户名称不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-016','SalesTransactionMaintenance002','lessThanCharLength','tradeCounterAccName',null,'201','Y','销售结算资金交易对手方账户名称不能超过200字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-017','SalesTransactionMaintenance002','isNotEmpty','tradeCounterAccBankName',null,null,'N','销售结算资金交易对手方开户行名称不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-018','SalesTransactionMaintenance002','lessThanCharLength','tradeCounterAccBankName',null,'201','Y','销售结算资金交易对手方开户行名称不能超过200字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-019','SalesTransactionMaintenance002','isNotEmpty','tradeCounterAccBankNo',null,null,'N','销售结算资金交易对手方账户开户行号不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-020','SalesTransactionMaintenance002','lessThanCharLength','tradeCounterAccBankNo',null,'13','Y','销售结算资金交易对手方账户开户行号不能超过12字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-022','SalesTransactionMaintenance002','isNotEmpty','tradeCcy','==','CURRENCY_CHB','Y','销售结算资金交易币种不可为空或不在值域范围内。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-023','SalesTransactionMaintenance002','isNotEmpty','tradeAmt',null,null,'N','销售结算资金交易金额不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-024','SalesTransactionMaintenance002','isNumber2','tradeAmt','>=','15,2','Y','销售结算资金交易金额必须为n(15,2)格式。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-025','SalesTransactionMaintenance002','compareValueAsNumber','tradeAmt','>','0','Y','销售结算资金交易金额必须大于0。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-026','SalesTransactionMaintenance002','isNotEmpty','tradeVdate',null,null,'N','销售结算资金交易日期不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-027','SalesTransactionMaintenance002','isValidDate2','tradeVdate',null,'yyyy-MM-dd','Y','销售结算资金交易日期必须为日期格式（YYYY-MM-DD）。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-028','SalesTransactionMaintenance002','isNotEmpty','tradeTime',null,null,'N','销售结算资金交易时间不可为空。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-029','SalesTransactionMaintenance002','lessThanCharLength','postscript',null,'201','Y','销售结算资金交易附言不能超过200字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
Insert into CHB_DATACHECK_RULE_INV (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-030','SalesTransactionMaintenance002','lessThanCharLength','remark',null,'201','Y','备注不能超过200字符。','E','com.joyintech.service.chb.common.service.ChbBaseCheckLib');
insert into CHB_DATACHECK_RULE_INV (CHECKRULEID, PAGE_ID, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS, CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-031', 'SalesTransactionMaintenance002', 'isCustomizedStringForWhitelist', 'prdRegistCode', null, '^([CZ][0-9]{7}[A]?[0-9]{6})$', 'Y', '产品登记编码填写错误。正确的格式：6 位提供销售结算资金服务机构代码；2 位年度码；1 位标识码(可为空);6 位顺序码。', 'E', 'com.joyintech.service.chb.common.service.ChbBaseCheckLib');
insert into CHB_DATACHECK_RULE_INV (CHECKRULEID, PAGE_ID, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS, CHECKCLASS)
values ('SALES_TRANSCATION_MAINTENANCE_RULE_PRD_REGIST_CODE-2021-1119-032', 'SalesTransactionMaintenance002', 'isCustomizedStringForWhitelist', 'institutionCode', null, '^([CZ][0-9]{5})$', 'Y','提供销售结算资金服务机构代码填写错误。', 'E', 'com.joyintech.service.chb.common.service.ChbBaseCheckLib');

--销售结算资金交易信息登记check脚本
DELETE FROM CHB_DATACHECK_RULE WHERE DATATYPE = 'CHB_SALER_STL_DEAL_SUB';
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000001','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','INSTITUTION_CODE','','','N','提供销售结算资金服务机构代码要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000002','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','INSTITUTION_CODE','','7','Y','提供销售结算资金服务机构代码不能超过6字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000003','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','PRD_REGIST_CODE','','','N','产品登记编码要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000004','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','PRD_REGIST_CODE','','16','Y','产品登记编码不能超过15字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000005','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','ACCOUNT_NO','','','N','销售结算资金账号要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000006','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','ACCOUNT_NO','','61','Y','销售结算资金账号不能超过60字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000007','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','ACCOUNT_NAME','','','N','销售结算资金账户名称要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000008','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','ACCOUNT_NAME','','201','Y','销售结算资金账户名称不能超过200字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000009','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_NO','','','N','销售结算资金交易流水号要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000010','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_NO','','61','Y','销售结算资金交易流水号不能超过60字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000011','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_PS','','','N','销售结算资金流动方向要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000012','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','TRADE_PS','==','TRADE_PS','Y','销售结算资金流动方向不再值域范围内','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000013','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_COUNTER_ACC_NO','','','N','销售结算资金交易对手方账号要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000014','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_COUNTER_ACC_NO','','61','Y','销售结算资金交易对手方账号不能超过60字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000015','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_COUNTER_ACC_NAME','','','N','销售结算资金交易对手方账户名称要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000016','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_COUNTER_ACC_NAME','','201','Y','销售结算资金交易对手方账户名称不能超过200字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000017','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_COUNTER_ACC_BANK_NAME','','','N','销售结算资金交易对手方开户行名称要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000018','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_COUNTER_ACC_BANK_NAME','','201','Y','销售结算资金交易对手方开户行名称不能超过200字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000019','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_COUNTER_ACC_BANK_NO','','','N','销售结算资金交易对手方账户开户行号要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000020','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','TRADE_COUNTER_ACC_BANK_NO','','13','Y','销售结算资金交易对手方账户开户行号不能超过12字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000021','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_CCY','','','N','销售结算资金交易币种要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000022','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','inDatadict','TRADE_CCY','==','CURRENCY_CHB','Y','销售结算资金交易币种不在值域范围内','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000023','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_AMT','','','N','销售结算资金交易金额要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000025','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','compareValueAsNumber','TRADE_AMT','>','0','Y','销售结算资金交易金额必须大于0','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000026','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_VDATE','','','N','销售结算资金交易日期要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000027','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isValidDate2','TRADE_VDATE','==','yyyy-MM-dd','Y','销售结算资金交易日期必须为日期格式（YYYY-MM-DD）。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000028','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isNotEmpty','TRADE_TIME','','','N','销售结算资金交易时间要素不可为空。','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000029','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','isValidDate2','TRADE_TIME','','HHmmss','Y','销售结算资金交易时间必须是正常时间','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000030','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','POSTSCRIPT','','201','Y','销售结算资金交易附言不能超过200字符','E');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000000031','CHB_SALER_STL_DEAL_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','lessThanCharLength','REMARK','','201','Y','备注不能超过200字符','E');
DELETE FROM CHB_DATACHECK_RULE WHERE CHECKRULEID in ('CHB_SALER_STL_DEAL_SUB-0000000010004','CHB_SALER_STL_DEAL_SUB-0000000010005');
insert into CHB_DATACHECK_RULE (CHECKRULEID, DATATYPE, CHECKCLASS, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS)
values ('CHB_SALER_STL_DEAL_SUB-0000000010004', 'CHB_SALER_STL_DEAL_SUB', 'com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib', 'isCustomizedStringForWhitelist', 'PRD_REGIST_CODE', null, '^([CZ][0-9]{7}[A]?[0-9]{6})$', 'Y', '产品登记编码要素格式不对。正确的格式：6 位金融机构代码；2 位年度码；1 位标识码(可为空);6 位顺序码。', 'E');

--更改投资者明细与投资者信息到中债三四期菜单
delete from SYS_MENU where PAGE_ID = 'EquityInfo001';
delete from SYS_MENU where PAGE_ID = 'SvrEquityBusinessDetails001';
INSERT INTO SYS_MENU (MENU_ID,SYSTEM_CODE,ICON,PARENT_MENU_ID,PAGE_ID,ORDER_NO,IS_SPREAD,REMARK,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME)values('REPORT_MAINTAIN_001_003_009','REP',null,'REPORT_MAINTAIN_001_003','EquityInfo001',9,02,'投资者身份信息','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_MENU (MENU_ID,SYSTEM_CODE,ICON,PARENT_MENU_ID,PAGE_ID,ORDER_NO,IS_SPREAD,REMARK,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME)values('REPORT_MAINTAIN_001_003_010','SVR',null,'REPORT_MAINTAIN_001_003','SvrEquityBusinessDetails001',10,02,'投资者明细信息','system',null,sysdate,'system',sysdate);

--20211129_中债四期相关维护脚本
delete FROM SYS_PARA WHERE PARA_CODE = 'COLOR_CONFIG_mid_list';
insert into SYS_PARA (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, UPDATE_FLG)
values ('SVR', 'COLOR_CONFIG_mid_list', '69,72,73,18,75,76,77,78,79,80,80,80,40,60,60', '01', null, '延迟报送检查画面的配置参数(mids)', 'system', null, SYSDATE, 'admin',SYSDATE, null, '01');

delete FROM SYS_PARA WHERE PARA_CODE = 'COLOR_CONFIG_top_list';
insert into SYS_PARA (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, UPDATE_FLG)
values ('SVR', 'COLOR_CONFIG_top_list', '81,82,83,84,85,86,87,88,89,90,90,90,65,95,95', '01', null, '延迟报送检查画面的配置参数(tops)', 'system', null, SYSDATE, 'admin',SYSDATE, null, '01');


