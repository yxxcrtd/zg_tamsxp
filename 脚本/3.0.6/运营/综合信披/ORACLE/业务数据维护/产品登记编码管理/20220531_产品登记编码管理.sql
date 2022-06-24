
declare
num1   number;
begin
SELECT COUNT(*) into num1 FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'CHB_PRDRESIST_CODE' and column_name = 'PROD_INS_ID';

if 	num1 = 0   then execute immediate 'ALTER TABLE CHB_PRDRESIST_CODE ADD PROD_INS_ID VARCHAR2(100)';
end if;

end;
/
COMMENT ON COLUMN CHB_PRDRESIST_CODE.PROD_INS_ID is '行内识别码';


update CHB_PRDRESIST_CODE set PROD_INS_ID = PRODID;

