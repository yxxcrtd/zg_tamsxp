alter table etr_cash_position_flow add is_Exist_New  varchar2(2);
comment on column etr_cash_position_flow.is_Exist_New is '数据已入新头寸，Y：已入';
alter table etr_cash_position add is_Exist_New  varchar2(2);
comment on column etr_cash_position.is_Exist_New is '数据已入新头寸，Y：已入';
alter table etr_sec_position_flow add is_Exist_New  varchar2(2);
comment on column etr_sec_position_flow.is_Exist_New is '数据已入新头寸，Y：已入';
alter table etr_sec_position add is_Exist_New  varchar2(2);
comment on column etr_sec_position.is_Exist_New is '数据已入新头寸，Y：已入';

alter table ptl_cash_position_flow add is_Exist_New  varchar2(2);
comment on column ptl_cash_position_flow.is_Exist_New is '数据已入新头寸，Y：已入';
alter table ptl_cash_position add is_Exist_New  varchar2(2);
comment on column ptl_cash_position.is_Exist_New is '数据已入新头寸，Y：已入';
alter table ptl_sec_position_flow add is_Exist_New  varchar2(2);
comment on column ptl_sec_position_flow.is_Exist_New is '数据已入新头寸，Y：已入';
alter table ptl_sec_position add is_Exist_New  varchar2(2);
comment on column ptl_sec_position.is_Exist_New is '数据已入新头寸，Y：已入';
