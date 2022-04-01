package com.joyintech.tams.exchange.service.trade.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.constant.ErrorCode;
import com.joyintech.tams.exchange.common.db.domain.Instrument;
import com.joyintech.tams.exchange.service.trade.management.service.InstrumentManagement;
import com.joyintech.tams.exchange.util.MasterUtil;


@RestController
@RequestMapping("/service/joyin")
//@Api(tags = "场内交易-合约数据查询接口")
public class InstrumentController {
    @Autowired
    private InstrumentManagement instrumentManagement;

    @RequestMapping(value = "/instrument/flush", method = RequestMethod.GET)
    public ResultBean instrumentFlush(@RequestParam(value = "market", required = false) String market,
                                      @RequestParam(value = "stock_code", required = false) String stockCode,
                                      @RequestParam(value = "page_no", required = false) String pageNo,
                                      @RequestParam(value = "count", required = false) String count) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(null);
    	}
        Map<String, Object> params = new HashMap<String, Object>();
        if (!Strings.isNullOrEmpty(market)) {
            params.put("market", market);
        }
        if (!Strings.isNullOrEmpty(stockCode)) {
            params.put("stock_code", stockCode);
        }
        if (!Strings.isNullOrEmpty(pageNo)) {
            params.put("page_no", pageNo);
        }
        if (!Strings.isNullOrEmpty(count)) {
            params.put("count", count);
        }
        return instrumentManagement.flushInstrumentData(params);
    }

    @RequestMapping(value = "/instrument/trdUnitLimitFlush", method = RequestMethod.GET)
    public ResultBean trdUnitLimitFlush() {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.getForObjectResultBean();
    	}
        instrumentManagement.trdUnitLimitFlush();
        return new ResultBean(ErrorCode.SUCCESS);
    }

    /**
     * * 最小价差查询接口
     *
     * @param market     市场
     * @param tradePlat  交易平台
     * @param assetType  资产一级分类
     * @param assetType1 资产二级分类
     * @param assetType2 资产三级分类
     * @return ResultBean
     */
    @RequestMapping(value = "/instrument/minPrice", method = RequestMethod.GET)
    public ResultBean minPrice(@RequestParam(value = "market") String market,
                               @RequestParam(value = "trade_plat", required = false, defaultValue = "02") String tradePlat,
                               @RequestParam(value = "asset_type") String assetType,
                               @RequestParam(value = "asset_type1", required = false) String assetType1,
                               @RequestParam(value = "asset_type2", required = false) String assetType2) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.getForObjectResultBean();
    	}
        Instrument instrument = new Instrument();
        instrument.setMarket(market);
        instrument.setAssetType(assetType);
        instrument.setAssetType1(assetType1);
        instrument.setSubStockType(assetType2);
        return instrumentManagement.minPrice(tradePlat, instrument);
    }

    /**
     * 交易单位及上下限查询
     *
     * @param market        市场
     * @param tradePlat     交易平台
     * @param assetType     资产一级分类
     * @param assetType1    资产二级分类
     * @param assetType2    资产三级分类
     * @param instructionPs 交易方向
     * @return ResultBean
     */
    @RequestMapping(value = "/instrument/minTrdUnitLimit", method = RequestMethod.GET)
    public ResultBean minTrdUnitLimit(@RequestParam(value = "market") String market,
                                      @RequestParam(value = "trade_plat", required = false, defaultValue = "02") String tradePlat,
                                      @RequestParam(value = "asset_type") String assetType,
                                      @RequestParam(value = "asset_type1", required = false) String assetType1,
                                      @RequestParam(value = "asset_type2", required = false) String assetType2,
                                      @RequestParam(value = "instruction_ps", required = false, defaultValue = "T01") String instructionPs) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.getForObjectResultBean();
    	}
        Instrument instrument = new Instrument();
        instrument.setMarket(market);
        instrument.setAssetType(assetType);
        instrument.setAssetType1(assetType1);
        instrument.setSubStockType(assetType2);
        return instrumentManagement.minTrdUnitLimit(tradePlat, instrument, instructionPs);
    }

    @RequestMapping(value = "/futuresInstrument/flush", method = RequestMethod.GET)
    public ResultBean FuturesInstrumentFlush() {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.getForObjectResultBean();
    	}
        return instrumentManagement.flushFuturesInstrumentData();
    }

    /**
     * 最下价差及交易上下限查询
     *
     * @param stockCode 合约代码
     * @param tradePlat 交易平台
     * @return ResultBean
     */
    @RequestMapping(value = "/instrument/minPrice/minTrdUnitLimit", method = RequestMethod.POST)
    public ResultBean queryMinPriceAndMinTrdUnitLimit(@RequestParam(value = "stock_code") String stockCode,
                                                      @RequestParam(value = "trade_plat", required = false) String tradePlat,
                                                      @RequestParam(value = "instruction_ps", required = false) String instructionPs) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(null);
    	}
        return instrumentManagement.queryMinPriceAndMinTrdUnitLimit(stockCode, tradePlat, instructionPs);
    }

//    @ApiOperation(value = "合约数据刷新", notes = "合约数据刷新")
//    @RequestMapping(value = "/instrument/flush", method = RequestMethod.POST)
//    public Message<Object> instrumentFlush(@RequestBody InstrumentFlushRequest instrumentFlushRequest) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        if (!Strings.isNullOrEmpty(instrumentFlushRequest.getMarket())) {
//            params.put("market", instrumentFlushRequest.getMarket());
//        }
//        if (!Strings.isNullOrEmpty(instrumentFlushRequest.getStockCode())) {
//            params.put("stock_code", instrumentFlushRequest.getStockCode());
//        }
//        if (!Strings.isNullOrEmpty(instrumentFlushRequest.getPageNo())) {
//            params.put("page_no", instrumentFlushRequest.getPageNo());
//        }
//        if (!Strings.isNullOrEmpty(instrumentFlushRequest.getCount())) {
//            params.put("count", instrumentFlushRequest.getCount());
//        }
//        return new Message<Object>(instrumentManagement.flushInstrumentData(params));
//    }
//
//    @ApiOperation(value = "最小价差查询", notes = "最小价差查询")
//    @RequestMapping(value = "/instrument/minPrice", method = RequestMethod.GET)
//    public Message<MinPrice> minPrice(@RequestBody @Valid MinPriceAndMinTrdUnitLimitQueryRequest queryRequest) {
//        Instrument instrument = new Instrument();
//        instrument.setMarket(queryRequest.getMarket());
//        instrument.setAssetType(queryRequest.getAssetType());
//        instrument.setAssetType1(queryRequest.getAssetType1());
//        instrument.setSubStockType(queryRequest.getAssetType2());
//        return new Message<MinPrice>(instrumentManagement.minPrice(queryRequest.getTradePlat(), instrument));
//    }
//
//    @ApiOperation(value = "交易单位及上下限查询", notes = "交易单位及上下限查询")
//    @RequestMapping(value = "/instrument/minTrdUnitLimit", method = RequestMethod.GET)
//    public Message<MinTrdUnitLimit> minTrdUnitLimit(@RequestBody @Valid MinPriceAndMinTrdUnitLimitQueryRequest queryRequest) {
//        Instrument instrument = new Instrument();
//        instrument.setMarket(queryRequest.getMarket());
//        instrument.setAssetType(queryRequest.getAssetType());
//        instrument.setAssetType1(queryRequest.getAssetType1());
//        instrument.setSubStockType(queryRequest.getAssetType2());
//        return new Message<MinTrdUnitLimit>(instrumentManagement.minTrdUnitLimit(queryRequest.getTradePlat(), instrument, queryRequest.getInstructionPs()));
//    }
//
//    @ApiOperation(value = "期货合约数据刷新", notes = "期货合约数据刷新")
//    @RequestMapping(value = "/futuresInstrument/flush", method = RequestMethod.POST)
//    public Message<Object> futuresInstrumentFlush() {
//        return new Message<Object>(instrumentManagement.flushFuturesInstrumentData());
//    }
//
//    /**
//     * 最小价差及交易上下限查询
//     *
//     * @param stockCode     合约代码
//     * @param tradePlat     交易平台
//     * @param instructionPs 交易方向
//     * @return Message
//     */
//    @ApiOperation(value = "最小价差及交易上下限查询", notes = "最小价差及交易上下限查询")
//    @RequestMapping(value = "/instrument/minPrice/minTrdUnitLimit", method = RequestMethod.POST)
//    public Message<MinPriceAndMinTrdUnitLimitBean> queryMinPriceAndMinTrdUnitLimit(@RequestParam(value = "stockCode") String stockCode,
//                                                                                   @RequestParam(value = "tradePlat", required = false) String tradePlat,
//                                                                                   @RequestParam(value = "instructionPs", required = false) String instructionPs) {
//        return new Message<MinPriceAndMinTrdUnitLimitBean>(instrumentManagement.queryMinPriceAndMinTrdUnitLimit(stockCode, tradePlat, instructionPs));
//    }
}
