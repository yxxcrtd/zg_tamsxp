DELETE FROM INTERF_POSITION_CONFIG WHERE TRADE_PLAT = 'TRD_MANUAL_SETTLE';

/*场内指令现券接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F01', 'T01', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内现券接口-非可转债-手工交收-买入', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F01', 'T02', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内现券接口-非可转债-手工交收-卖出', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F01-B14', 'T01', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内现券接口-可转债-手工交收-买入', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F01-B14', 'T02', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内现券接口-可转债-手工交收-卖出', 'Y');

/*质押式协议回购接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F02-H01', 'T96', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-正回购-提前购回', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F02-H01', 'T56', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-正回购-到期续做', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F02-H01', '03', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-正回购-首期', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F02-H01', 'T93', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-正回购-解除质押', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F02-H01', 'T94', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-正回购-中途换券', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F02-H01', 'T95', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-正回购-到期购回', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F03-H01', 'T96', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-逆回购-提前购回', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F03-H01', 'T56', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-逆回购-到期续做', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F03-H01', '04', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-逆回购-首期', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F03-H01', 'T93', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-逆回购-解除质押', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F03-H01', 'T94', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-逆回购-中途换券', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_MANUAL_SETTLE', 'F03-H01', 'T95', 'STL06', 'TRD', 'com.joyintech.service.trade.manualsettle.dto.ManualSettleDto', 'REPO002', 'system', null, sysdate, 'system', sysdate, '场内协议回购接口-手工交收-逆回购-到期购回', 'Y');
