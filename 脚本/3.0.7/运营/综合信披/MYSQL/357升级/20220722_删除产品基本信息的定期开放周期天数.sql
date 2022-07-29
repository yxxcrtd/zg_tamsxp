/**删除定期开放周期天数的校验*/
delete from  chb_datacheck_rule where DATATYPE='CHB_PRD_BASEINFO' and SRCPROPERTY='FIT_CIRCLE_DAYS' and CHECKMETHOD='isNotEmpty';
