ALTER TABLE wfl_etr_entrust DROP COLUMN BUSINESS_MODEL;
ALTER TABLE wfl_etr_entrust add (BUSINESS_MODEL varchar2(50));
COMMENT ON COLUMN wfl_etr_entrust.BUSINESS_MODEL IS '业务模式';