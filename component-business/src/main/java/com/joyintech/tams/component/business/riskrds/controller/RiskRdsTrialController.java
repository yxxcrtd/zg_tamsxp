/*
 * 文件名：RiskRdsTrialController.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangrui1
 * 修改时间：2020年9月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.component.business.riskrds.controller;

import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.component.business.riskrds.constant.ModelConstant;
import com.joyintech.tams.component.business.riskrds.dto.InterfRiskRdsDto;
import com.joyintech.tams.component.business.riskrds.service.InterfRiskRdsService;
import com.joyintech.tams.component.business.riskrds.service.RiskRdsTrialService;
import com.joyintech.tams.invest.common.CommonUtil;
import com.joyintech.tams.invest.common.client.ClientData;
import com.joyintech.tams.invest.common.constantrds.BussinessDictCodeListRds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 风险试算Controller
 * 
 * @author zhangrui1
 * @version 1.0 2020年9月19日
 * @see RiskRdsTrialController
 * @since 1.0
 */
@Controller
@RequestMapping("/riskRdsTrial/interf")
public class RiskRdsTrialController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RiskRdsTrialService service;

    @Autowired
    private InterfRiskRdsService interfRiskRdsService;

    /**
     * 批量风险试算
     * 
     * @param dataList 批量数据
     * @param eventType 事件类型
     * @return 试算结果
     */
    @ResponseBody
    @RequestMapping("trialList/{pageId}/{eventType}")
    public String batchRiskTrialRds(@RequestBody List<Map<String, Object>> dataList,
                                    @PathVariable("pageId") String pageId,
                                    @PathVariable("eventType") String eventType) {
        if (CommonUtil.isType(CacheUtil.getSysParam("pageRedisFlag"),
            BussinessDictCodeListRds.是否_是)) {
            logger.info("==========批量试算接口<RSK011>事件类型:{} start==========", eventType);
            // 报文体为空\事件类型为空
            if (CollectionUtils.isEmpty(dataList) || StringUtil.isEmpty(eventType)) {
                throw new NullPointerException("请求报文或者请求参数为空");
            }
            ClientData rsp = service.sendList(pageId,
                BussinessDictCodeListRds.接口系统ID_风险试算批量接口.getValue(), dataList,
                map -> map.put(ModelConstant.INF_EVENTTYPE, eventType));
            logger.info("==========批量试算接口<RSK011>事件类型:{} end==========", eventType);
            return JsonUtils.object2Json(rsp);
        }
        else {
            logger.info("==========未启动风险试算==========");
            return null;
        }
    }

    /**
     * 风险接口统一调用入口(批量画面端)
     * @param dataMap
     * @param eventType  事件类型 0精确委托 1交易 2 拆分 3模糊委托
     * @param assetType  资产大类(对应投管为数据字典平安资产类型)
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("doInfBatchData/{pageId}/{eventType}/{assetType}")
    public String doInfBatchData(@RequestBody Map<String, Object> dataMap,
                                       @PathVariable("pageId") String pageId,
                                       @PathVariable("eventType") String eventType,
                                       @PathVariable("assetType") String assetType)
            throws IOException {
        try {
            if(CommonUtil.isType(CacheUtil.getSysParam("pageRedisFlag"), BussinessDictCodeListRds.是否_是)) {
                logger.info("==========前端交易或委托类转换接口:{} start==========", eventType);
                if (!StringUtil.isNotEmpty(dataMap) || dataMap.isEmpty()) {
                    throw new IllegalArgumentException("请求报文为空");
                }
                if (StringUtil.isEmpty(eventType)) {
                    throw new IllegalArgumentException("请求参数为空");
                }
                logger.info("==========调用共通方法sendRiskRdsDataCommon==========");
                InterfRiskRdsDto interfRiskRdsDto = new InterfRiskRdsDto();
                interfRiskRdsDto.setEventType(eventType);
                interfRiskRdsDto.setAssetType(assetType);
                List<Map<String, Object>> dataList=interfRiskRdsService.getBatchAllocation(dataMap,interfRiskRdsDto);
                ClientData rsp = service.sendList(pageId,
                        BussinessDictCodeListRds.接口系统ID_风险试算批量接口.getValue(), dataList,
                        map -> map.put(ModelConstant.INF_EVENTTYPE, eventType));
                return JsonUtils.object2Json(rsp);
            }else {
                logger.info("==========系统参数中未开启风险试算开关==========");
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info("==========交易或委托类转换接口 Exception==========\r\n {}", e.getMessage());
            return null;
        }
    }
}
