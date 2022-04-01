DELETE FROM INTERF_POSITION_CONFIG WHERE TRADE_PLAT = 'TRD_TRADE_REPORT';

/*场内指令现券接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F01', 'T01', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内现券接口-非可转债-成交回报-买入', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F01', 'T02', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内现券接口-非可转债-成交回报-卖出', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F01-B14', 'T01', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内现券接口-可转债-成交回报-买入', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F01-B14', 'T02', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内现券接口-可转债-成交回报-卖出', 'Y');

/*基金接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F06', 'T01', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'Fund002', 'system', null, sysdate, 'system', sysdate, '场内基金接口-成交回报-买入', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F06', 'T02', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'Fund002', 'system', null, sysdate, 'system', sysdate, '场内基金接口-成交回报-卖出', 'Y');

/*股票接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F10', 'T01', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'Stock001', 'system', null, sysdate, 'system', sysdate, '场内股票接口-成交回报-买入', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F10', 'T02', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'Stock001', 'system', null, sysdate, 'system', sysdate, '场内股票接口-成交回报-卖出', 'Y');

/*质押式通用回购接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F01', 'T68', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO003', 'system', null, sysdate, 'system', sysdate, '场内质押式通用回购接口-成交回报-入库', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F01', 'T69', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO003', 'system', null, sysdate, 'system', sysdate, '场内质押式通用回购接口-成交回报-出库', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F02-H02', '03', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO003', 'system', null, sysdate, 'system', sysdate, '场内质押式通用回购接口-正回购-成交回报-首期', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F03-H02', '04', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO003', 'system', null, sysdate, 'system', sysdate, '场内质押式通用回购接口-逆回购-成交回报-首期', 'Y');

/*质押式协议回购接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F02-H01', 'T96', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-正回购-提前购回', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F02-H01', 'T56', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-正回购-到期续做', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F02-H01', '03', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-正回购-首期', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F02-H01', 'T93', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-正回购-解除质押', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F02-H01', 'T94', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-正回购-中途换券', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F02-H01', 'T95', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-正回购-到期购回', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F03-H01', 'T96', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-逆回购-提前购回', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F03-H01', 'T56', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-逆回购-到期续做', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F03-H01', '04', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-逆回购-首期', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F03-H01', 'T93', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-逆回购-解除质押', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F03-H01', 'T94', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-逆回购-中途换券', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_TRADE_REPORT', 'F03-H01', 'T95', '17', 'TRD', 'com.joyintech.service.trade.entrustmsgbymq.dto.TrdTempDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-成交回报-逆回购-到期购回', 'Y');
