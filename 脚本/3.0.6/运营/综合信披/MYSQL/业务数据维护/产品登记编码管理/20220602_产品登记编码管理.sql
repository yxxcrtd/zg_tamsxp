alter table CHB_PRDRESIST_CODE drop primary key;
alter table CHB_PRDRESIST_CODE modify PRODID VARCHAR(100) COMMENT '产品代码';
alter table CHB_PRDRESIST_CODE add primary key(ORG_CODE,PROD_INS_ID);
