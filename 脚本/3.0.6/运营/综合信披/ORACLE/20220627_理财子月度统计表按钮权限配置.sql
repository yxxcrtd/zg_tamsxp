DELETE FROM SYS_PAGE_ELEMENT WHERE PAGE_ID = 'SvrSubsidiaMonthReport001';
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrSubsidiaMonthReport001','ChbSubsidiaMonthReport001_btn_manual','01','01',null,'手工处理','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrSubsidiaMonthReport001','ChbSubsidiaMonthReport001_btn_cancelManual','01','01',null,'取消手工处理','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrSubsidiaMonthReport001','ChbSubsidiaMonthReport001_btn_chbexport','01','01',null,'导出中债EXCEL','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrSubsidiaMonthReport001','ChbSubsidiaMonthReport001_btn_recal','01','01',null,'重新计算','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrSubsidiaMonthReport001','SvrSubsidiaMonthReport001_btn_search','01','01',null,'查询','system',null,sysdate,'system',sysdate,'C',null);
INSERT INTO SYS_PAGE_ELEMENT VALUES('SvrSubsidiaMonthReport001','SvrSubsidiaMonthReport001_btn_reset','01','01',null,'重置','system',null,sysdate,'system',sysdate,'C',null);