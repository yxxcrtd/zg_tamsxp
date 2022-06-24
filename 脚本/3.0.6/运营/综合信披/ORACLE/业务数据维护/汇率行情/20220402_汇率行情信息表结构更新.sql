
declare
num1   number;
num2   number;
num3   number;

begin
SELECT COUNT(*) into num1 FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'MST_EXCHARGE_QUOTE' and column_name = 'STATUS';
SELECT COUNT(*) into num2 FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'MST_EXCHARGE_QUOTE' and column_name = 'DATA_SOURCE';
SELECT COUNT(*) into num3 FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'MST_EXCHARGE_QUOTE' and column_name = 'ERR_MSG';
if 	num1 = 0   then execute immediate 'ALTER TABLE MST_EXCHARGE_QUOTE ADD STATUS VARCHAR2(20)';
end if;
if  num2 = 0   then execute immediate 'ALTER TABLE MST_EXCHARGE_QUOTE ADD DATA_SOURCE VARCHAR2(50)';
end if;
if  num3 = 0   then execute immediate 'ALTER TABLE MST_EXCHARGE_QUOTE ADD ERR_MSG CLOB';
end if;

end;
/
COMMENT  ON  COLUMN  MST_EXCHARGE_QUOTE.STATUS   is  '数据状态';
COMMENT  ON  COLUMN  MST_EXCHARGE_QUOTE.DATA_SOURCE   is  '数据来源';
COMMENT  ON  COLUMN  MST_EXCHARGE_QUOTE.ERR_MSG   is  '错误信息';