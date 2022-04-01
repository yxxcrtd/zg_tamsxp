/**指令表新增 投管业务类型**/
alter table T_INSTRUCTION add BUSI_TYPE_TG varchar2(50);

comment on column T_INSTRUCTION.BUSI_TYPE_TG is '投管业务类型';