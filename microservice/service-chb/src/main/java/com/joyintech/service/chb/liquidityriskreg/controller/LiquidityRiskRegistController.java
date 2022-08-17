package com.joyintech.service.chb.liquidityriskreg.controller;

import com.joyintech.framework.grid.Page;
import com.joyintech.tams.svreport.chb.liquidityriskreg.dto.LiquidityRiskRegist001Dto;
import com.joyintech.tams.svreport.chb.liquidityriskreg.service.LiquidityRiskRegist001Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 流动性风险信息登记
 * 〈功能详细描述〉
 * @author wangyaning
 * @version 1.6.0 2022年08月15日
 * @see LiquidityRiskRegistController
 * @since 1.0
 */
@RestController("LiquidityRiskRegistCtr_dtx")
@RequestMapping("/LiquidityRiskRegist")
public class LiquidityRiskRegistController {

	@Autowired
	private LiquidityRiskRegist001Service liquidityRiskRegist001Service;
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 业务画面查询事件
	 *
	 * @return
	 * @see
	 * @since 1.0
	 */
	@PostMapping("findPage/{page}/{length}")
	public Page<LiquidityRiskRegist001Dto> searchPage(@RequestBody Map<String, Object> param) {
		Page<LiquidityRiskRegist001Dto> page = liquidityRiskRegist001Service.searchPage(param);
		return page;
	}
	/**
	 * 手工处理功能
	 *
	 * @return 提示信息
	 * @param map 条件map
	 * @seea
	 * @since 1.0
	 */
	@PostMapping("updateSumStaus")
	public void updateSumStaus(@RequestBody Map<String, Object> map) {
		liquidityRiskRegist001Service.updateSumStaus(map);
	}

	/**
	 * 取消手工处理功能
	 *
	 * @return 提示信息
	 * @param map 条件map
	 * @seea
	 * @since 1.0
	 */
	@PostMapping("cancelMannul")
	public void cancelMannul(@RequestBody Map<String, Object> map) {
		liquidityRiskRegist001Service.cancelMannul(map);
	}

}
