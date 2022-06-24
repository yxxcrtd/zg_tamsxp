/**
 * 新增：理财子公司月度统计报送时点
 */
DELETE FROM CHB_REG_TIME_POINTS WHERE SEQNO = '82';
INSERT INTO CHB_REG_TIME_POINTS VALUES ('82', null, '27', null, '02', null, '01', '每月的第N个自然日前', 7.00, 7.00, 12.00, 4.00, 'E', null, null, 'system', TO_DATE('2021-04-08', 'YYYY-MM-DD'), 'system', TO_DATE('2021-04-08', 'YYYY-MM-DD'), null);