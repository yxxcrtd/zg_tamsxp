update CHB_DATACHECK_RULE set checkmethod='isValidDate' where    DATATYPE='CHB_EMPLOYEE_INFO' and CHB_DATACHECK_RULE.SRCPROPERTY ='FIRSTTIME2WORK' and checkmethod='isValidDate3';
update CHB_DATACHECK_RULE set checkmethod='isValidDate' where    DATATYPE='CHB_EMPLOYEE_INFO' and CHB_DATACHECK_RULE.SRCPROPERTY ='FIRSTTIME2FINWORK' and checkmethod='isValidDate3';
