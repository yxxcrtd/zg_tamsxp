alter table CHB_AST_POS_SUB modify   AMT NUMBER(30,2);  
alter table CHB_AST_POS_SUB modify     CNY_AMT NUMBER(30,2);  
alter table CHB_AST_POS_SUB modify     FLOAT_INGPL NUMBER(30,2);  
alter table CHB_AST_POS_SUB modify     CNY_FLOAT_INGPL NUMBER(30,2); 
alter table CHB_AST_POS_SUB modify     UNIT_VALUE_CPRICE NUMBER(30,4); 
alter table CHB_AST_POS_SUB modify     UNIT_VALUE_FPRICE NUMBER(30,4);  
alter table CHB_AST_POS_SUB modify     QTY NUMBER(30,5);  

alter table chb_prd_status_sub modify     PRD_TOTAL_AMT NUMBER(15,2); 
alter table chb_prd_status_sub modify     PRD_LEVERAGE NUMBER(8,5); 