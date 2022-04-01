ALTER TABLE ETR_ENTRUST ADD FINISH_DATE TIMESTAMP;
COMMENT ON COLUMN ETR_ENTRUST.FINISH_DATE  IS '指令完成时间';

--  当更新指令状态为完成时同时更新完成日期为平台日
drop trigger T_UPDATE_ETR_ENTRUST;
create or replace trigger T_UPDATE_ETR_ENTRUST
    before update on ETR_ENTRUST
    for each row
begin
    if :new.ENTRUST_STATUS='04' then
        select CUR_DATE into :new.FINISH_DATE from  SYS_DATE;
    end if;
end;
/