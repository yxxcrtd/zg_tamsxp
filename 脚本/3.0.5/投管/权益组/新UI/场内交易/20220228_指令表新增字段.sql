
/**指令表新增字段**/
alter table T_INSTRUCTION add CREATE_TIME DATE;
comment on column T_INSTRUCTION.CREATE_TIME is '创建时间';

alter table T_INSTRUCTION add UPDATE_TIME DATE;
comment on column T_INSTRUCTION.UPDATE_TIME is '修改时间';


/**历史指令表新增字段**/
alter table T_INSTRUCTION_HISTORY add CREATE_TIME DATE;
comment on column T_INSTRUCTION_HISTORY.CREATE_TIME is '创建时间';

alter table T_INSTRUCTION_HISTORY add UPDATE_TIME DATE;
comment on column T_INSTRUCTION_HISTORY.UPDATE_TIME is '修改时间';

