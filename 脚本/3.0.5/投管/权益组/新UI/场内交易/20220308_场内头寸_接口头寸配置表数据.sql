/*先删除数据*/
delete from INTERF_POSITION_CONFIG where TRADE_PLAT='ETR_PAGE' or TRADE_PLAT='TRD_PAGE';
/*场内指令现券接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01', 'T01', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内指令现券接口-买入新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01', 'T02', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内指令现券接口-卖出新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01', 'T01', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内指令现券接口-买入修改', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01', 'T02', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内指令现券接口-卖出修改', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01', 'T03', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内指令现券接口-回售新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01', 'T03', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内指令现券接口-回售修改', 'Y');

/*场内指令可转债接口*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T01', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-买入新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T02', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-卖出新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T01', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-买入修改', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T02', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-卖出修改', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T03', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-回售新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T03', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-回售修改', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T04', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-申购新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T04', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-申购修改', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T97', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-配债新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T97', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-配债修改', 'Y');
/**INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F01-B14', 'T49', 'new', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-债转股新建', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('ETR_PAGE', 'F01-B14', 'T49', 'modify', 'ETR', 'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内指令可转债接口-债转股修改', 'Y');*/

/*场内交易现券接口-下单*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01', 'T01', '16', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内交易现券接口-买入下单', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01', 'T02', '16', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内交易现券接口-卖出下单', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01', 'T03', '16', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内交易现券接口-回售下单', 'Y');
/*场内交易现券接口-撤委托*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01', 'T01', '18', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内交易现券接口-买入撤委托', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01', 'T02', '18', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内交易现券接口-卖出撤委托', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01', 'T03', '18', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond002', 'system', null, sysdate, 'system', sysdate, '场内交易现券接口-回售撤委托', 'Y');

/*场内交易可转债接口-下单*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01-B14', 'T01', '16', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内交易可转债接口-买入下单', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01-B14', 'T02', '16', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内交易可转债接口-卖出下单', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01-B14', 'T03', '16', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内交易可转债接口-回售下单', 'Y');
/*INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F01-B14', 'T04', '16', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内交易可转债接口-申购下单', 'Y');*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F01-B14', 'T97', '16', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内交易可转债接口-配债下单', 'Y');
/*场内交易可转债接口-撤委托*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01-B14', 'T01', '18', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内交易可转债接口-买入撤委托', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01-B14', 'T02', '18', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内交易可转债接口-卖出撤委托', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE, ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, ENABLE) 
VALUES ('TRD_PAGE', 'F01-B14', 'T03', '18', 'TRD', 'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Bond003', 'system', null, sysdate, 'system', sysdate, '场内交易可转债接口-回售撤委托', 'Y');




/*基金*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F06', 'T01', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Fund002', 'system',
        null, sysdate, 'system', sysdate, '新建场内基金买入指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F06', 'T02', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Fund002', 'system',
        null, sysdate, 'system', sysdate, '新建场内基金卖出指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F06', 'T01', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Fund002', 'system',
        null, sysdate, 'system', sysdate, '修改场内基金买入指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F06', 'T02', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Fund002', 'system',
        null, sysdate, 'system', sysdate, '修改场内基金卖出指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F06', 'T01', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Fund002', 'system',
        null, sysdate, 'system', sysdate, '新建场内基金买入报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F06', 'T02', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Fund002', 'system',
        null, sysdate, 'system', sysdate, '新建场内基金卖出报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F06', 'T01', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Fund002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内基金买入报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F06', 'T02', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Fund002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内基金卖出报单', 'Y');

/*股票*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F10', 'T01', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '新建场内股票买入指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F10', 'T02', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '新建场内股票卖出指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F10', 'T46', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '新建场内股票配股指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F10', 'T04', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '新建场内股票申购指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F10', 'T01', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '修改场内股票买入指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F10', 'T02', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '修改场内股票卖出指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F10', 'T46', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '修改场内股票配股指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F10', 'T04', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '修改场内股票申购指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F10', 'T01', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '新建场内股票买入报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F10', 'T02', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '新建场内股票卖出报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F10', 'T46', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '新建场内股票配股报单', 'Y');

/*INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F10', 'T04', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '新建场内股票申购报单', 'Y');*/

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F10', 'T01', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '撤销场内股票买入报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F10', 'T02', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '撤销场内股票卖出报单', 'Y');
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F10', 'T46', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '撤销场内股票配股报单', 'Y');

/*INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F10', 'T04', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'Stock001', 'system',
        null, sysdate, 'system', sysdate, '撤销场内股票申购报单', 'Y');*/

/*质押式通用回购*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F02', '03', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '新建场内通用回购正回购指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F03', '04', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '新建场内通用回购逆回购指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F01', 'T68', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '新建场内通用回购入库指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F01', 'T69', 'new', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '新建场内通用回购出库指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F02', '03', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '修改场内通用回购正回购指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F03', '04', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '修改场内通用回购逆回购指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F01', 'T68', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '修改场内通用回购入库指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('ETR_PAGE', 'F01', 'T69', 'modify', 'ETR',
        'com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeSaveRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '修改场内通用回购出库指令', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F02', '03', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '新建场内通用回购正回购报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F03', '04', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '新建场内通用回购逆回购报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F01', 'T68', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '新建场内通用回购入库报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F01', 'T69', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '新建场内通用回购出库报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F02', '03', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '撤销场内通用回购正回购报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F03', '04', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '撤销场内通用回购逆回购报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F01', 'T68', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '撤销场内通用回购入库报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F01', 'T69', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO003', 'system',
        null, sysdate, 'system', sysdate, '撤销场内竞价回购出库报单', 'Y');

/*协议回购*/
INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', '03', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '新建场内协议回购正回购报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', '04', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '新建场内协议回购逆回购报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T93', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '新建场内协议回购解除质押报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T94', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '新建场内协议回购中途换券报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T95', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '新建场内协议回购到期购回报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T96', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '新建场内协议回购提前购回报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T56', '16', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '新建场内协议回购到期续做报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', '03', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内协议回购正回购报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', '04', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内协议回购逆回购报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T93', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内协议回购解除质押报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T94', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内协议回购中途换券报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T95', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内协议回购到期购回报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T96', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内协议回购提前购回报单', 'Y');

INSERT INTO INTERF_POSITION_CONFIG (TRADE_PLAT, FINPROD_TYPE, PS, BUSI_ACT, POSITION_TYPE,
                                                           ENTITY, INTERF_TYPE, CREATE_USER, CREATE_DEPT, CREATE_TIME,
                                                           UPDATE_USER, UPDATE_TIME, REMARK, ENABLE)
VALUES ('TRD_PAGE', 'F07-H01', 'T56', '18', 'TRD',
        'com.joyintech.tams.exchange.common.request.PositionInstructionRequest', 'REPO002', 'system',
        null, sysdate, 'system', sysdate, '撤销场内协议回购到期续做报单', 'Y');
