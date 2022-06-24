--此移植脚本目的是将CHB_SUBMIT_STATUS中的：报送反馈状态、报送编码、报送反馈消息移植到CHB_EQU_SUB、CHB_EQU_SUB_HIS、CHB_INVESTHOLD_INFO_SUB、CHB_INVESTHOLD_INFO_SUB_HIS中
--执行前先确认现场CHB_SUBMIT_STATUS表是否有归档或者历史表，如有以下脚本需要将CHB_SUBMIT_STATUS替换成相应历史表再执行
--执行前需要确认CHB_EQU_SUB、CHB_EQU_SUB_HIS、CHB_INVESTHOLD_INFO_SUB、CHB_INVESTHOLD_INFO_SUB_HIS中sumit_seq以及CHB_SUBMIT_STATUS中seqno不应有null数据

--身份登记表：报送反馈状态、报送编码、报送反馈消息 数据移植
update CHB_EQU_SUB aa set (aa.submit_feedback_status,aa.report_no,aa.submit_feedback_msg)
=
(select bb.submit_feedback_status,bb.report_no,bb.submit_feedback_msg from CHB_SUBMIT_STATUS bb where aa.sumit_seq = bb.seqno)
where EXISTS (select 1 from CHB_SUBMIT_STATUS cc where aa.sumit_seq = cc.seqno);

--身份登记历史表：报送反馈状态、报送编码、报送反馈消息 数据移植
update CHB_EQU_SUB_HIS aa set (aa.submit_feedback_status,aa.report_no,aa.submit_feedback_msg)
=
(select bb.submit_feedback_status,bb.report_no,bb.submit_feedback_msg from CHB_SUBMIT_STATUS bb where aa.sumit_seq = bb.seqno)
where EXISTS (select 1 from CHB_SUBMIT_STATUS cc where aa.sumit_seq = cc.seqno);

--持有登记表：报送反馈状态、报送编码、报送反馈消息 数据移植
update CHB_INVESTHOLD_INFO_SUB aa set (aa.submit_feedback_status,aa.report_no,aa.submit_feedback_msg)
=
(select bb.submit_feedback_status,bb.report_no,bb.submit_feedback_msg from CHB_SUBMIT_STATUS bb where aa.sumit_seq = bb.seqno)
where EXISTS (select 1 from CHB_SUBMIT_STATUS cc where aa.sumit_seq = cc.seqno);

--持有登记历史表：报送反馈状态、报送编码、报送反馈消息 数据移植
update CHB_INVESTHOLD_INFO_SUB_HIS aa set (aa.submit_feedback_status,aa.report_no,aa.submit_feedback_msg)
=
(select bb.submit_feedback_status,bb.report_no,bb.submit_feedback_msg from CHB_SUBMIT_STATUS bb where aa.sumit_seq = bb.seqno)
where EXISTS (select 1 from CHB_SUBMIT_STATUS cc where aa.sumit_seq = cc.seqno);
