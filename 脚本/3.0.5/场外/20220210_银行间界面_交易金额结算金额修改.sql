update cfg_datatable_show set  colname='交易金额（元）' where pagecode='CfetsEtrDispatcher' and dtcode='CfetsEtrDispatcherExeInfo' and colcode='cpriceAmt';
update cfg_datatable_show set  colname='结算金额（元）' where pagecode='CfetsEtrDispatcher' and dtcode='CfetsEtrDispatcherExeInfo' and colcode='fpriceAmt';
update cfg_datatable_show set  colname='交易金额（元）' where pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo' and colcode='cpriceAmt';
update cfg_readonly_form_show set  colname='交易金额' where pagecode='cfetsManualSettleQuery' and dtcode='CfetsManualSettleInfo' and colcode='cpriceAmt';
