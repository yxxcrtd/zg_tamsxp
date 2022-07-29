alter table CHB_PRDRESIST_CODE drop primary key;
alter table CHB_PRDRESIST_CODE add constraint PK_test primary key(ORG_CODE,PROD_INS_ID);
alter table CHB_PRDRESIST_CODE drop constraint PK_test;