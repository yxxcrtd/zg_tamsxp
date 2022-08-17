package com.joyintech.tams.svreport.chb.liquidityriskregist.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.svreport.chb.liquidityriskreg.dto.LiquidityRiskRegist001Dto;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 流动性风险信息登记
 * 〈功能详细描述〉
 * @author wangyaning
 * @version 1.6.0 2022年08月15日
 * @see LiquidityRiskRegistMService
 * @since 1.0
 */
@Service
public class LiquidityRiskRegistMService extends BaseService<LiquidityRiskRegist001Dto> {

    /**
     *
     * 业务画面查询事件
     *
     * @return
     * @see
     * @since 1.0
     */
    public Page<LiquidityRiskRegist001Dto> searchPage(Map<String, Object> param) {
        return restTemplate.postForPage(
                SvrServiceApplication.CHB_SERVICE_NAME + "/LiquidityRiskRegist/findPage",param,
                LiquidityRiskRegist001Dto.class);
    }

    /**
     * 手工处理功能
     *
     * @return 提示信息
     * @param map
     *            条件map
     * @seea
     * @since 1.0
     */
    public void updateSumStaus(Map<String, Object> map) {
        restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/LiquidityRiskRegist/updateSumStaus",map,
                Integer.class);
    }

    /**
     * 取消手工处理功能
     *
     * @return 提示信息
     * @param map
     *            条件map
     * @seea
     * @since 1.0
     */
    public void cancelMannul(Map<String, Object> map) {
        restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/LiquidityRiskRegist/cancelMannul",map,
                Integer.class);
    }

}
