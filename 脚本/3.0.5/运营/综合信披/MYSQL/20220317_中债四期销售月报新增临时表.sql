DROP TABLE CHB_MONTH_COUNT_TMP;
create table CHB_MONTH_COUNT_TMP(
                                    prdRegistCode	VARCHAR(20)
    ,institutionCode	VARCHAR(20)
    ,areaCode 	VARCHAR(50)
    ,holderType	VARCHAR(2)
    ,ccy	        VARCHAR(4)
    ,amount	DOUBLE(38,10)
    ,qty	        DOUBLE(38,10)
    ,cnyAmt       DOUBLE(38,10)
  )
    comment='理财业务销售数据月报临时表';
