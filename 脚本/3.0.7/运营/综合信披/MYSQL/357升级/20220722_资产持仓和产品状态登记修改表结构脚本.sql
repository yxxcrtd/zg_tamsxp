alter table CHB_AST_POS_SUB modify   COLUMN  AMT DECIMAL(30,2)  COMMENT   '金额';
alter table CHB_AST_POS_SUB modify   COLUMN  CNY_AMT DECIMAL(30,2)  COMMENT   '折算人民币金额';
alter table CHB_AST_POS_SUB modify   COLUMN  FLOAT_INGPL DECIMAL(30,2)  COMMENT   '公允价值';
alter table CHB_AST_POS_SUB modify   COLUMN  CNY_FLOAT_INGPL DECIMAL(30,2)  COMMENT   '折算人民币公允价值';
alter table CHB_AST_POS_SUB modify   COLUMN  UNIT_VALUE_CPRICE DECIMAL(30,4)  COMMENT   '单位估值（净价）';
alter table CHB_AST_POS_SUB modify   COLUMN  UNIT_VALUE_FPRICE DECIMAL(30,4)  COMMENT   '单位估值（全价）';
alter table CHB_AST_POS_SUB modify   COLUMN  QTY DECIMAL(30,5)  COMMENT   '数量';

alter table chb_prd_status_sub modify   COLUMN  PRD_TOTAL_AMT DECIMAL(15,2)  COMMENT   '理财产品总资产金额（元）';
alter table chb_prd_status_sub modify   COLUMN  PRD_LEVERAGE DECIMAL(8,5)  COMMENT   '理财产品杠杆率（%）';
