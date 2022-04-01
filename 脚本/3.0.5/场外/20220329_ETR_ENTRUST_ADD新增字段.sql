--ETR_ENTRUST_ADD新增字段:强制完成金额
alter table ETR_ENTRUST_ADD drop column FORCED_FINISH_AMT;
alter table ETR_ENTRUST_ADD add (FORCED_FINISH_AMT NUMBER(30, 14));
comment on column ETR_ENTRUST_ADD.FORCED_FINISH_AMT is '强制完成金额';
--ETR_ENTRUST_ADD新增字段:已关闭额度
alter table ETR_ENTRUST_ADD drop column CLOSE_AMT;
alter table ETR_ENTRUST_ADD add (CLOSE_AMT NUMBER(30, 14));
comment on column ETR_ENTRUST_ADD.CLOSE_AMT is '已关闭额度';