delete from SYS_WFL_BUSINESS_MODULE where PAGE_ID='ChbExchangeQuotationInfo002' AND ELEMENT_ID='ChbExchangeQuotationInfo001_btn_delete' AND INSTITUTION_CODE='ALL';
INSERT INTO SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG) VALUES ('ChbExchangeQuotationInfo002', 'ChbExchangeQuotationInfo001_btn_delete', 'oneApproval', 'system', null, sysdate, 'system', sysdate, 'ALL', '02');
delete from SYS_WFL_BUSINESS_MODULE where PAGE_ID='ChbExchangeQuotationInfo002' AND ELEMENT_ID='ChbExchangeQuotationInfo002_btn_submit' AND INSTITUTION_CODE='ALL';
INSERT INTO SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, IS_SEND_UORG) VALUES ('ChbExchangeQuotationInfo002', 'ChbExchangeQuotationInfo002_btn_submit', 'oneApproval', 'system', null, sysdate, 'system', sysdate, 'ALL', '02');

