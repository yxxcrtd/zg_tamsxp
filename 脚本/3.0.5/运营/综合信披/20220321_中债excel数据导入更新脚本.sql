-- 中债excle导入表名称修改
update chb_mgrtable_info set MGRTABLE_CHI_NAME='产品申报登记' where MGRTABLE_ID='CHB_PRD_DECLARE_SUB_TMP';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='产品发行登记' where MGRTABLE_ID='CHB_PRD_ISSUE_SUB_TMP';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='募集总量登记' where MGRTABLE_ID='CHB_PRD_RAISETOT_SUB_TMP';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='资管计划底层持仓信息登记' where MGRTABLE_ID='CHB_BOT_AST_POS_SUB_TMP';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='产品存续期登记' where MGRTABLE_ID='CHB_PRD_DEAL_SUB_TMP';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='产品终止登记' where MGRTABLE_ID='CHB_PRD_END_SUB_TMP';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='从业人员登记' where MGRTABLE_ID='CHB_EMPLOYEE_INFO';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='资产要素信息登记' where MGRTABLE_ID='CHB_ASSET_INFO_TMP';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='资产交易信息登记' where MGRTABLE_ID='CHB_INVEST_TRADE_SUB_TMP';
update chb_mgrtable_info set MGRTABLE_CHI_NAME='资产估值信息登记' where MGRTABLE_ID='CHB_AST_VAL_SUB_TMP';
