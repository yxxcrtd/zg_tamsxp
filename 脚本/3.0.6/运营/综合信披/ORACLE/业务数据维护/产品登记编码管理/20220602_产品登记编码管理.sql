alter table CHB_PRDRESIST_CODE drop primary key;

alter table CHB_PRDRESIST_CODE add primary key(ORG_CODE,PROD_INS_ID);

alter table CHB_PRDRESIST_CODE modify PRODID varchar2(100) null;