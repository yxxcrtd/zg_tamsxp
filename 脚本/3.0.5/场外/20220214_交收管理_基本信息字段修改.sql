update cfg_readonly_form_show set colname ='单位面值' where pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo'  and spcdcode='04' and colcode='intPrinAmt';
update cfg_readonly_form_show set colname ='单位还本面值' where pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo'  and spcdcode='04' and colcode='prinAmt';
delete from cfg_readonly_form_show where  pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo'  and spcdcode='04,M' and colcode='prinAmtTheory';
update cfg_readonly_form_show set colname ='理论交收金额',colcode='totalAmtTheory' where pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo'  and spcdcode='04,M' and colcode='intAmtTheory';
delete from cfg_datatable_show where  pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo'  and spcdcode='04,M,P' and colcode='intAmtTheoryd';
update cfg_datatable_show set colname ='理论交收金额（元）',colcode='totalAmtTheory' where pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo'  and spcdcode='04,M,P' and colcode='prinAmtTheoryd';
update cfg_datatable_show set colname ='券面总额（万元）' where pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo'  and spcdcode='04,M,P' and colcode='qtyd';
