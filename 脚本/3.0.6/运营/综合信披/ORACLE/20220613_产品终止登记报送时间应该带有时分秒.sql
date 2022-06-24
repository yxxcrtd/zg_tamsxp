alter table chb_prd_end_sub rename column SUBMIT_TIME to SUBMIT_TIME_TMP;
alter table chb_prd_end_sub add SUBMIT_TIME TIMESTAMP(6);
update chb_prd_end_sub set SUBMIT_TIME=trim(SUBMIT_TIME_TMP);
alter table chb_prd_end_sub drop column SUBMIT_TIME_TMP;
COMMENT ON COLUMN CHB_PRD_END_SUB.SUBMIT_FEEDBACK_MSG IS '报送时间';