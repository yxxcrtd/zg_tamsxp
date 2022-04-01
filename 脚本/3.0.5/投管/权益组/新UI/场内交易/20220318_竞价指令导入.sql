delete from sys_wfl_business_module where PAGE_ID = 'EtrExchangeTradeCommon004' and ELEMENT_ID = 'EtrExchangeImport_btn_submit';
insert into SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeCommon004', 'EtrExchangeImport_btn_submit', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate);