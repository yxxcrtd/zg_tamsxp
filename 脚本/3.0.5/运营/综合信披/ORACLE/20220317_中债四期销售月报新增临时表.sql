DROP TABLE CHB_MONTH_COUNT_TMP;
create table CHB_MONTH_COUNT_TMP(
  prdRegistCode	VARCHAR2(20)
  ,institutionCode	VARCHAR2(20)
  ,areaCode 	VARCHAR2(50)
  ,holderType	VARCHAR2(2)
  ,ccy	        VARCHAR2(4)
  ,amount	NUMBER(38,10)
  ,qty	        NUMBER(38,10)
  ,cnyAmt       NUMBER(38,10)
);
comment on table CHB_MONTH_COUNT_TMP is '理财业务销售数据月报临时表';