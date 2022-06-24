
declare
num1   number;
begin
select count(*) into num1 from user_ind_columns where index_name=upper('PK_MST_EXCHARGE_QUOTE') AND TABLE_NAME = 'MST_EXCHARGE_QUOTE';
if 	num1 > 0   then execute immediate 'DROP INDEX PK_MST_EXCHARGE_QUOTE';
end if;
end;

