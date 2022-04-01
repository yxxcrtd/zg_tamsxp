/**  
 * @Project     service-customer-report  
 * @File        InvestmentsInfoService.java  
 * @Package     com.joyintech.service.report.regulatoryreporting.investmentsinfo.service  
 * @Version     V1.0  
 * @Date        2019年8月7日 下午1:58:19  
 * @Author      高健  
 * Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.  
 */  
    
package com.joyintech.service.report.regulatoryreporting.investmentsinfo.service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

import java.text.SimpleDateFormat;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dto.InvestmentBusinessCounterDetailsDto;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dao.OverallInfoMapper;
import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dao.TermStructureOfBusinessAndCostBenefitMapper;
import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dto.OverallInfoDto;
import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dao.InvestmentBusinessDetailsMapper;
import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dto.InvestmentBusinessDetailsDto;



/**  
 * @Description TODO  
 * @ClassName   InvestmentsInfoService  
 * @Date        2019年8月7日 下午1:58:19  
 * @Author      高健  
 * Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.  
 */
@Service
public class InvestmentsInfoService {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(InvestmentsInfoService.class);

	@Autowired
	private InvestmentBusinessDetailsMapper investmentBusinessDetailsMapper;
	@Autowired
	private TermStructureOfBusinessAndCostBenefitMapper termStructureOfBusinessAndCostBenefitMapper;
	
	@Autowired
	private OverallInfoMapper overallInfoMapper;
	// Map<单元格， 单元格需要存储数据的标识组合> 表一表六用
	private static Map<String, String> porpertyMap_1 = new HashMap<String, String>(1 << 6);
	// 存储属性代码和展示代码的映射关系
	private static Map<String, String> porpertyMap_2 = new HashMap<String, String>(1 << 7);
	private static Map<String, String> tablePorpertyMap_1 = new HashMap<String, String>(1 << 6);

	private static Map<String, String> tablePorpertyMap_2 = new HashMap<String, String>(1 << 7);
	private static Map<String, String> tablePorpertyMap_4_1 = new HashMap<String, String>();
	private static Map<String, String> tablePorpertyMap_4_2 = new HashMap<String, String>();
	private static Map<String, String> tablePorpertyMap_4_3 = new HashMap<String, String>();
	private static Map<String, String> tablePorpertyMap_4_4 = new HashMap<String, String>();

	private static Map<String, String> tablePorpertyMap_7_1 = new HashMap<String, String>();



	//资产类型和属性代码映射关系（穿透用）
	private static Map<String, String> assetTypeMap_2 = new HashMap<String, String>();
	// 存储属性代码和展示代码的映射关系
	private static Map<String, String> porpertyMap_4_1 = new HashMap<String, String>();
	private static Map<String, String> porpertyMap_4_2 = new HashMap<String, String>();
	private static Map<String, String> porpertyMap_4_3 = new HashMap<String, String>();
	private static Map<String, String> porpertyMap_4_4 = new HashMap<String, String>();
	
	// 存储值相同的单元格对应情况
	private static Map<String, String> sameValueMap = new HashMap<String, String>();
	private static Map<String, String> table8ValueMap = new HashMap<String, String>();
	static {
		tablePorpertyMap_1.put("t1_cur12_amt", "ASSETAMT1_1_6+ASSETAMT1_2_3+ASSETAMT1_3_3+ASSETAMT1_4_14+ASSETAMT1_5_17+ASSETAMT1_6_5+ASSETAMT1_7_8+ASSETAMT1_8_11+ASSETAMT1_9_4+ASSETAMT1_10_7+ASSETAMT1_12_9+ASSETAMT1_14_8");
		tablePorpertyMap_1.put("t1_last12_amt",  "ASSETAMT1_1_6+ASSETAMT1_2_3+ASSETAMT1_3_3+ASSETAMT1_4_14+ASSETAMT1_5_17+ASSETAMT1_6_5+ASSETAMT1_7_8+ASSETAMT1_8_11+ASSETAMT1_9_4+ASSETAMT1_10_7+ASSETAMT1_12_9+ASSETAMT1_14_8");
		tablePorpertyMap_1.put("t1_cur13_amt", "ASSETAMT1_13_1_1+ASSETAMT1_13_2_1+ASSETAMT1_13_3_1+ASSETAMT1_13_4_1+ASSETAMT1_13_5_1+ASSETAMT1_13_6_1");
		tablePorpertyMap_1.put("t1_last13_amt", "ASSETAMT1_13_1_1+ASSETAMT1_13_2_1+ASSETAMT1_13_3_1+ASSETAMT1_13_4_1+ASSETAMT1_13_5_1+ASSETAMT1_13_6_1");
		tablePorpertyMap_1.put("t1_cur14_amt", "ASSETAMT1_13_1_2+ASSETAMT1_13_2_2+ASSETAMT1_13_3_2+ASSETAMT1_13_4_2+ASSETAMT1_13_5_2+ASSETAMT1_13_6_2");
		tablePorpertyMap_1.put("t1_last14_amt", "ASSETAMT1_13_1_2+ASSETAMT1_13_2_2+ASSETAMT1_13_3_2+ASSETAMT1_13_4_2+ASSETAMT1_13_5_2+ASSETAMT1_13_6_2");
		tablePorpertyMap_1.put("t1_cur26_amt", "ASSETAMT1_13_1_1");
		tablePorpertyMap_1.put("t1_last26_amt",  "ASSETAMT1_13_1_1");
		tablePorpertyMap_1.put("t1_cur27_amt", "ASSETAMT1_13_2_1");
		tablePorpertyMap_1.put("t1_last27_amt",  "ASSETAMT1_13_2_1");
		tablePorpertyMap_1.put("t1_cur28_amt", "ASSETAMT1_13_3_1+ASSETAMT1_11_7");
		tablePorpertyMap_1.put("t1_last28_amt",  "ASSETAMT1_13_3_1+ASSETAMT1_11_7");
		tablePorpertyMap_1.put("t1_cur29_amt", "ASSETAMT1_13_4_1+ASSETAMT1_13_5_1+ASSETAMT1_13_6_1");
		tablePorpertyMap_1.put("t1_last29_amt", "ASSETAMT1_13_4_1+ASSETAMT1_13_5_1+ASSETAMT1_13_6_1");
		tablePorpertyMap_1.put("t1_cur31_amt", "ASSETAMT1_13_1_2");
		tablePorpertyMap_1.put("t1_last31_amt",  "ASSETAMT1_13_1_2");
		tablePorpertyMap_1.put("t1_cur32_amt", "ASSETAMT1_13_2_2");
		tablePorpertyMap_1.put("t1_last32_amt",  "ASSETAMT1_13_2_2");
		tablePorpertyMap_1.put("t1_cur33_amt", "ASSETAMT1_13_3_2");
		tablePorpertyMap_1.put("t1_last33_amt",  "ASSETAMT1_13_3_2");
		tablePorpertyMap_1.put("t1_cur34_amt", "ASSETAMT1_13_4_2+ASSETAMT1_13_5_2+ASSETAMT1_13_6_2");
		tablePorpertyMap_1.put("t1_last34_amt", "ASSETAMT1_13_4_2+ASSETAMT1_13_5_2+ASSETAMT1_13_6_2");

		tablePorpertyMap_1.put("t6_cur6_amt", "ASSETAMT1_4_14");
		tablePorpertyMap_1.put("t6_last6_amt", "ASSETAMT1_4_14");
		tablePorpertyMap_1.put("t6_cur13_amt", "ASSETAMT1_3_1+ASSETAMT1_3_2+ASSETAMT1_2_1+ASSETAMT1_2_2");
		tablePorpertyMap_1.put("t6_last13_amt", "ASSETAMT1_3_1+ASSETAMT1_3_2+ASSETAMT1_2_1+ASSETAMT1_2_2");
		tablePorpertyMap_1.put("t6_cur14_amt", "ASSETAMT1_3_1");
		tablePorpertyMap_1.put("t6_last14_amt", "ASSETAMT1_3_1");
		tablePorpertyMap_1.put("t6_cur15_amt", "ASSETAMT1_3_2");
		tablePorpertyMap_1.put("t6_last15_amt", "ASSETAMT1_3_2");
		tablePorpertyMap_1.put("t6_cur16_amt", "ASSETAMT1_2_1+ASSETAMT1_2_2");
		tablePorpertyMap_1.put("t6_last16_amt", "ASSETAMT1_2_1+ASSETAMT1_2_2");
		tablePorpertyMap_1.put("t6_cur17_amt", "ASSETAMT1_5_17");
		tablePorpertyMap_1.put("t6_last17_amt", "ASSETAMT1_5_17");
		tablePorpertyMap_1.put("t6_cur18_amt", "ASSETAMT1_6_5");
		tablePorpertyMap_1.put("t6_last18_amt", "ASSETAMT1_6_5");

		tablePorpertyMap_1.put("t6_cur20_amt", "ASSETAMT1_11_7");
		tablePorpertyMap_1.put("t6_last20_amt", "ASSETAMT1_11_7");
		tablePorpertyMap_1.put("t6_cur21_amt", "ASSETAMT1_12_9");
		tablePorpertyMap_1.put("t6_last21_amt", "ASSETAMT1_12_9");

		tablePorpertyMap_1.put("t6_cur23_amt", "ASSETAMT1_13_1");
		tablePorpertyMap_1.put("t6_last23_amt", "ASSETAMT1_13_1");

		tablePorpertyMap_1.put("t6_cur24_amt", "ASSETAMT1_13_2+ASSETAMT1_13_3+ASSETAMT1_13_4");
		tablePorpertyMap_1.put("t6_last24_amt", "ASSETAMT1_13_2+ASSETAMT1_13_3+ASSETAMT1_13_4");
		tablePorpertyMap_1.put("t6_cur25_amt", "ASSETAMT1_13_5");
		tablePorpertyMap_1.put("t6_last25_amt", "ASSETAMT1_13_5");

		tablePorpertyMap_1.put("t6_cur26_amt", "ASSETAMT1_13_6");
		tablePorpertyMap_1.put("t6_last26_amt","ASSETAMT1_13_6");

		tablePorpertyMap_1.put("t6_cur27_amt", "ASSETAMT1_7_8");
		tablePorpertyMap_1.put("t6_last27_amt", "ASSETAMT1_7_8");
		tablePorpertyMap_1.put("t6_cur28_amt", "ASSETAMT1_4_14+ASSETAMT1_3_1+ASSETAMT1_3_2+ASSETAMT1_2_1+ASSETAMT1_2_2+ASSETAMT1_5_17+ASSETAMT1_6_5+ASSETAMT1_11_7+ASSETAMT1_12_9+ASSETAMT1_13_1+ASSETAMT1_13_2+ASSETAMT1_13_3+ASSETAMT1_13_4+ASSETAMT1_13_5+ASSETAMT1_13_6+ASSETAMT1_7_8");
		tablePorpertyMap_1.put("t6_last28_amt", "ASSETAMT1_4_14+ASSETAMT1_3_1+ASSETAMT1_3_2+ASSETAMT1_2_1+ASSETAMT1_2_2+ASSETAMT1_5_17+ASSETAMT1_6_5+ASSETAMT1_11_7+ASSETAMT1_12_9+ASSETAMT1_13_1+ASSETAMT1_13_2+ASSETAMT1_13_3+ASSETAMT1_13_4+ASSETAMT1_13_5+ASSETAMT1_13_6+ASSETAMT1_7_8");

		tablePorpertyMap_1.put("t2_cur84_amt", "PRODSUM_MJFS");
		tablePorpertyMap_1.put("t2_last84_amt", "PRODSUM_MJFS");
        tablePorpertyMap_1.put("t2_cur86_amt", "PRODSUM_MJFS");
        tablePorpertyMap_1.put("t2_last86_amt", "PRODSUM_MJFS");

		tablePorpertyMap_2.put("t2_cur6_amt", "ASSETAMT1_1_6");
		tablePorpertyMap_2.put("t2_last6_amt", "ASSETAMT1_1_6");
		tablePorpertyMap_2.put("t2_cur7_amt", "ASSETAMT1_1_1");
		tablePorpertyMap_2.put("t2_last7_amt", "ASSETAMT1_1_1");
		tablePorpertyMap_2.put("t2_cur8_amt", "ASSETAMT1_1_2");
		tablePorpertyMap_2.put("t2_last8_amt", "ASSETAMT1_1_2");
		tablePorpertyMap_2.put("t2_cur9_amt", "ASSETAMT1_1_3");
		tablePorpertyMap_2.put("t2_last9_amt", "ASSETAMT1_1_3");
		tablePorpertyMap_2.put("t2_cur10_amt", "ASSETAMT1_1_4");
		tablePorpertyMap_2.put("t2_last10_amt", "ASSETAMT1_1_4");
		tablePorpertyMap_2.put("t2_cur11_amt", "ASSETAMT1_1_5");
		tablePorpertyMap_2.put("t2_last11_amt", "ASSETAMT1_1_5");
		tablePorpertyMap_2.put("t2_cur14_amt", "ASSETAMT1_2_3+ASSETAMT1_3_3");
		tablePorpertyMap_2.put("t2_last14_amt", "ASSETAMT1_2_3+ASSETAMT1_3_3");
		tablePorpertyMap_2.put("t2_cur15_amt", "ASSETAMT1_3_1");
		tablePorpertyMap_2.put("t2_last15_amt", "ASSETAMT1_3_1");
		tablePorpertyMap_2.put("t2_cur16_amt", "ASSETAMT1_3_2");
		tablePorpertyMap_2.put("t2_last16_amt", "ASSETAMT1_3_2");
		tablePorpertyMap_2.put("t2_cur17_amt", "ASSETAMT1_2_1");
		tablePorpertyMap_2.put("t2_last17_amt", "ASSETAMT1_2_1");
		tablePorpertyMap_2.put("t2_cur18_amt", "ASSETAMT1_2_2");
		tablePorpertyMap_2.put("t2_last18_amt", "ASSETAMT1_2_2");
		tablePorpertyMap_2.put("t2_cur21_amt", "ASSETAMT1_4_14");
		tablePorpertyMap_2.put("t2_last21_amt", "ASSETAMT1_4_14");
		tablePorpertyMap_2.put("t2_cur22_amt", "ASSETAMT1_4_1");
		tablePorpertyMap_2.put("t2_last22_amt", "ASSETAMT1_4_1");
		tablePorpertyMap_2.put("t2_cur23_amt", "ASSETAMT1_4_2");
		tablePorpertyMap_2.put("t2_last23_amt", "ASSETAMT1_4_2");
		tablePorpertyMap_2.put("t2_cur24_amt", "ASSETAMT1_4_3");
		tablePorpertyMap_2.put("t2_last24_amt", "ASSETAMT1_4_3");
		tablePorpertyMap_2.put("t2_cur25_amt", "ASSETAMT1_4_5");
		tablePorpertyMap_2.put("t2_last25_amt", "ASSETAMT1_4_5");
		tablePorpertyMap_2.put("t2_cur26_amt", "ASSETAMT1_4_4");
		tablePorpertyMap_2.put("t2_last26_amt", "ASSETAMT1_4_4");
		tablePorpertyMap_2.put("t2_cur27_amt", "ASSETAMT1_4_6");
		tablePorpertyMap_2.put("t2_last27_amt", "ASSETAMT1_4_6");
		tablePorpertyMap_2.put("t2_cur28_amt", "ASSETAMT1_4_7");
		tablePorpertyMap_2.put("t2_last28_amt", "ASSETAMT1_4_7");
		tablePorpertyMap_2.put("t2_cur29_amt", "ASSETAMT1_4_8");
		tablePorpertyMap_2.put("t2_last29_amt", "ASSETAMT1_4_8");
		tablePorpertyMap_2.put("t2_cur30_amt", "ASSETAMT1_4_9");
		tablePorpertyMap_2.put("t2_last30_amt", "ASSETAMT1_4_9");
		tablePorpertyMap_2.put("t2_cur31_amt", "ASSETAMT1_4_10");
		tablePorpertyMap_2.put("t2_last31_amt", "ASSETAMT1_4_10");
		tablePorpertyMap_2.put("t2_cur32_amt", "ASSETAMT1_4_11");
		tablePorpertyMap_2.put("t2_last32_amt", "ASSETAMT1_4_11");
		tablePorpertyMap_2.put("t2_cur40_amt", "ASSETAMT1_5_17");
		tablePorpertyMap_2.put("t2_last40_amt", "ASSETAMT1_5_17");
		tablePorpertyMap_2.put("t2_cur41_amt", "ASSETAMT1_5_1");
		tablePorpertyMap_2.put("t2_last41_amt", "ASSETAMT1_5_1");
		tablePorpertyMap_2.put("t2_cur42_amt", "ASSETAMT1_5_2");
		tablePorpertyMap_2.put("t2_last42_amt", "ASSETAMT1_5_2");
		tablePorpertyMap_2.put("t2_cur43_amt", "ASSETAMT1_5_3");
		tablePorpertyMap_2.put("t2_last43_amt", "ASSETAMT1_5_3");
		tablePorpertyMap_2.put("t2_cur44_amt", "ASSETAMT1_5_4");
		tablePorpertyMap_2.put("t2_last44_amt", "ASSETAMT1_5_4");
		tablePorpertyMap_2.put("t2_cur45_amt", "ASSETAMT1_5_5");
		tablePorpertyMap_2.put("t2_last45_amt", "ASSETAMT1_5_5");
		tablePorpertyMap_2.put("t2_cur46_amt", "ASSETAMT1_5_6");
		tablePorpertyMap_2.put("t2_last46_amt", "ASSETAMT1_5_6");
		//tablePorpertyMap_2.put("t2_cur47_amt", "ASSETAMT1_5_7");
		//tablePorpertyMap_2.put("t2_last47_amt", "ASSETAMT1_5_7");
		tablePorpertyMap_2.put("t2_cur48_amt", "ASSETAMT1_5_8");
		tablePorpertyMap_2.put("t2_last48_amt", "ASSETAMT1_5_8");
		tablePorpertyMap_2.put("t2_cur49_amt", "ASSETAMT1_5_9");
		tablePorpertyMap_2.put("t2_last49_amt", "ASSETAMT1_5_9");
		tablePorpertyMap_2.put("t2_cur50_amt", "ASSETAMT1_5_14");
		tablePorpertyMap_2.put("t2_last50_amt", "ASSETAMT1_5_14");
		tablePorpertyMap_2.put("t2_cur51_amt", "ASSETAMT1_5_15");
		tablePorpertyMap_2.put("t2_last51_amt", "ASSETAMT1_5_15");
		tablePorpertyMap_2.put("t2_cur52_amt", "ASSETAMT1_5_12");
		tablePorpertyMap_2.put("t2_last52_amt", "ASSETAMT1_5_12");
		tablePorpertyMap_2.put("t2_cur53_amt", "ASSETAMT1_5_13");
		tablePorpertyMap_2.put("t2_last53_amt", "ASSETAMT1_5_13");
		tablePorpertyMap_2.put("t2_cur54_amt", "ASSETAMT1_5_10");
		tablePorpertyMap_2.put("t2_last54_amt", "ASSETAMT1_5_10");
		tablePorpertyMap_2.put("t2_cur55_amt", "ASSETAMT1_5_16_a");
		tablePorpertyMap_2.put("t2_last55_amt", "ASSETAMT1_5_16_a");
		// 同G06-II 1.5.16 其他非标准化债权类投资穿透后期末余额-1.5.16.a 场内股票质押回购穿透后余额+1.5.7 委托债权穿透后余额  add by leipeng
		tablePorpertyMap_2.put("t2_cur57_amtfq", "ASSETAMT1_5_16+ASSETAMT1_5_7-AMT1_5_16_a");
		tablePorpertyMap_2.put("t2_last57_amtfq", "ASSETAMT1_5_16+ASSETAMT1_5_7-AMT1_5_16_a");
		// 同G06-II 1.5.16 其他非标准化债权类投资穿透后期末余额-1.5.16.a 场内股票质押回购穿透后余额+1.5.7 委托债权穿透后余额  add by leipeng

		tablePorpertyMap_2.put("t2_cur58_amt", "ASSETAMT1_6_5");
		tablePorpertyMap_2.put("t2_last58_amt", "ASSETAMT1_6_5");
		tablePorpertyMap_2.put("t2_cur59_amt", "ASSETAMT1_6_1");
		tablePorpertyMap_2.put("t2_last59_amt", "ASSETAMT1_6_1");
		tablePorpertyMap_2.put("t2_cur60_amt", "ASSETAMT1_6_2");
		tablePorpertyMap_2.put("t2_last60_amt", "ASSETAMT1_6_2");
		tablePorpertyMap_2.put("t2_cur63_amt", "ASSETAMT1_6_3");
		tablePorpertyMap_2.put("t2_last63_amt", "ASSETAMT1_6_3");
		tablePorpertyMap_2.put("t2_cur65_amt", "ASSETAMT1_6_4");
		tablePorpertyMap_2.put("t2_last65_amt", "ASSETAMT1_6_4");
		tablePorpertyMap_2.put("t2_cur66_amt", "ASSETAMT1_11_7");
		tablePorpertyMap_2.put("t2_last66_amt", "ASSETAMT1_11_7");
		tablePorpertyMap_2.put("t2_cur67_amt", "ASSETAMT1_11_1");
		tablePorpertyMap_2.put("t2_last67_amt", "ASSETAMT1_11_1");
		tablePorpertyMap_2.put("t2_cur68_amt", "ASSETAMT1_11_2");
		tablePorpertyMap_2.put("t2_last68_amt", "ASSETAMT1_11_2");
		tablePorpertyMap_2.put("t2_cur69_amt", "ASSETAMT1_11_3");
		tablePorpertyMap_2.put("t2_last69_amt", "ASSETAMT1_11_3");
		tablePorpertyMap_2.put("t2_cur70_amt", "ASSETAMT1_11_4");
		tablePorpertyMap_2.put("t2_last70_amt", "ASSETAMT1_11_4");
		tablePorpertyMap_2.put("t2_cur71_amt", "ASSETAMT1_11_5");
		tablePorpertyMap_2.put("t2_last71_amt", "ASSETAMT1_11_5");
		tablePorpertyMap_2.put("t2_cur72_amt", "ASSETAMT1_11_6");
		tablePorpertyMap_2.put("t2_last72_amt", "ASSETAMT1_11_6");
		tablePorpertyMap_2.put("t2_cur73_amt", "ASSETAMT1_7_8");
		tablePorpertyMap_2.put("t2_last73_amt", "ASSETAMT1_7_8");
		tablePorpertyMap_2.put("t2_cur74_amt", "ASSETAMT1_8_11");
		tablePorpertyMap_2.put("t2_last74_amt", "ASSETAMT1_8_11");
		tablePorpertyMap_2.put("t2_cur75_amt", "ASSETAMT1_9_4");
		tablePorpertyMap_2.put("t2_last75_amt", "ASSETAMT1_9_4");
		tablePorpertyMap_2.put("t2_cur76_amt", "ASSETAMT1_10_7");
		tablePorpertyMap_2.put("t2_last76_amt", "ASSETAMT1_10_7");
		tablePorpertyMap_2.put("t2_cur77_amt", "ASSETAMT1_15");
		tablePorpertyMap_2.put("t2_last77_amt", "ASSETAMT1_15");
		tablePorpertyMap_2.put("t2_cur78_amt", "ASSETAMT3_1_2");
		tablePorpertyMap_2.put("t2_last78_amt", "ASSETAMT3_1_2");
		tablePorpertyMap_2.put("t2_cur79_amt", "ASSETAMT3_1_1");
		tablePorpertyMap_2.put("t2_last79_amt", "ASSETAMT3_1_1");
		tablePorpertyMap_2.put("t2_cur82_amt", "ASSETAMT2_1_1");
		tablePorpertyMap_2.put("t2_last82_amt", "ASSETAMT2_1_1");
		tablePorpertyMap_2.put("t2_cur83_amt", "ASSETAMT2_1_2");
		tablePorpertyMap_2.put("t2_last83_amt", "ASSETAMT2_1_2");
		tablePorpertyMap_2.put("t2_cur86_amt", "ASSETAMT2_1_1+ASSETAMT2_1_2");
		tablePorpertyMap_2.put("t2_last86_amt", "ASSETAMT2_1_1+ASSETAMT2_1_2");

		tablePorpertyMap_2.put("t3_zq7_amt", "ASSETAMT1_4_1");
		tablePorpertyMap_2.put("t3_lzq7_amt", "ASSETAMT1_4_1");
		tablePorpertyMap_2.put("t3_zq8_amt", "ASSETAMT1_4_2");
		tablePorpertyMap_2.put("t3_lzq8_amt", "ASSETAMT1_4_2");
		tablePorpertyMap_2.put("t3_zq9_amt", "ASSETAMT1_4_3");
		tablePorpertyMap_2.put("t3_lzq9_amt", "ASSETAMT1_4_3");
		tablePorpertyMap_2.put("t3_zq10_amt", "ASSETAMT1_4_5");
		tablePorpertyMap_2.put("t3_lzq10_amt","ASSETAMT1_4_5");
		tablePorpertyMap_2.put("t3_zq11_amt", "ASSETAMT1_4_6");
		tablePorpertyMap_2.put("t3_lzq11_amt", "ASSETAMT1_4_6");
		tablePorpertyMap_2.put("t3_zq12_amt", "ASSETAMT1_4_4");
		tablePorpertyMap_2.put("t3_lzq12_amt","ASSETAMT1_4_4");
		tablePorpertyMap_2.put("t3_zq13_amt", "ASSETAMT1_4_10");
		tablePorpertyMap_2.put("t3_lzq13_amt", "ASSETAMT1_4_10");
		tablePorpertyMap_2.put("t3_zq14_amt", "ASSETAMT1_4_11");
		tablePorpertyMap_2.put("t3_lzq14_amt", "ASSETAMT1_4_11");


		tablePorpertyMap_4_1.put("01", "t4_amt1_1");
		tablePorpertyMap_4_1.put("02", "t4_amt1_2");
		tablePorpertyMap_4_1.put("03", "t4_amt1_3");
		tablePorpertyMap_4_1.put("04", "t4_amt1_4");
		tablePorpertyMap_4_1.put("05", "t4_amt1_5");
		tablePorpertyMap_4_1.put("06", "t4_amt1_6");
		tablePorpertyMap_4_1.put("07", "t4_amt1_7");
		tablePorpertyMap_4_1.put("10", "t4_amt1_10");

		tablePorpertyMap_4_2.put("01", "t4_amt2_1");
		tablePorpertyMap_4_2.put("02", "t4_amt2_2");
		tablePorpertyMap_4_2.put("03", "t4_amt2_3");
		tablePorpertyMap_4_2.put("04", "t4_amt2_4");
		tablePorpertyMap_4_2.put("05", "t4_amt2_5");
		tablePorpertyMap_4_2.put("06", "t4_amt2_6");
		tablePorpertyMap_4_2.put("07", "t4_amt2_7");
		tablePorpertyMap_4_2.put("08", "t4_amt2_8");
		tablePorpertyMap_4_2.put("09", "t4_amt2_9");
		tablePorpertyMap_4_2.put("10", "t4_amt2_10");



		tablePorpertyMap_4_3.put("01", "t4_amt3_1");
		tablePorpertyMap_4_3.put("02", "t4_amt3_2");
		tablePorpertyMap_4_3.put("03", "t4_amt3_3");
		tablePorpertyMap_4_3.put("04", "t4_amt3_4");
		tablePorpertyMap_4_3.put("05", "t4_amt3_5");
		tablePorpertyMap_4_3.put("06", "t4_amt3_6");
		tablePorpertyMap_4_3.put("07", "t4_amt3_7");
		tablePorpertyMap_4_3.put("08", "t4_amt3_8");
		tablePorpertyMap_4_3.put("09", "t4_amt3_9");
		tablePorpertyMap_4_3.put("10", "t4_amt3_10");

		tablePorpertyMap_4_4.put("1901", "t4_amt4_1");
		tablePorpertyMap_4_4.put("1902", "t4_amt4_2");


		tablePorpertyMap_7_1.put("01", "t7_amt1_1");
		tablePorpertyMap_7_1.put("02", "t7_amt1_2");
		tablePorpertyMap_7_1.put("03", "t7_amt1_3");
		tablePorpertyMap_7_1.put("04", "t7_amt1_4");
		tablePorpertyMap_7_1.put("05", "t7_amt1_5");
		tablePorpertyMap_7_1.put("06", "t7_amt1_6");
		tablePorpertyMap_7_1.put("07", "t7_amt1_7");
		tablePorpertyMap_7_1.put("08", "t7_amt1_8");
		tablePorpertyMap_7_1.put("09", "t7_amt1_9");
		tablePorpertyMap_7_1.put("10", "t7_amt1_10");
		tablePorpertyMap_7_1.put("11", "t7_amt1_11");
		tablePorpertyMap_7_1.put("12", "t7_amt1_12");
		tablePorpertyMap_7_1.put("13", "t7_amt1_13");
		tablePorpertyMap_7_1.put("14", "t7_amt1_14");
		tablePorpertyMap_7_1.put("15", "t7_amt1_15");







		porpertyMap_1.put("t1_cur13_amt", "ASSETAMT1_15_1_1-ASSETAMT1_15_2_1-ASSETAMT1_15_3_1-ASSETAMT1_15_4_1-ASSETAMT1_15_5_1-ASSETAMT1_15_6_1");
		porpertyMap_1.put("t1_last13_amt", "ASSETAMT1_15_1_1-ASSETAMT1_15_2_1-ASSETAMT1_15_3_1-ASSETAMT1_15_4_1-ASSETAMT1_15_5_1-ASSETAMT1_15_6_1");
		porpertyMap_1.put("t1_cur14_amt", "ASSETAMT1_15_1_2-ASSETAMT1_15_2_2-ASSETAMT1_15_3_2-ASSETAMT1_15_4_2-ASSETAMT1_15_5_2-ASSETAMT1_15_6_2");
		porpertyMap_1.put("t1_last14_amt", "ASSETAMT1_15_1_2-ASSETAMT1_15_2_2-ASSETAMT1_15_3_2-ASSETAMT1_15_4_2-ASSETAMT1_15_5_2-ASSETAMT1_15_6_2");
		porpertyMap_1.put("t1_cur15_amt", "ASSETAMT1_16_8");
		porpertyMap_1.put("t1_last15_amt", "ASSETAMT1_16_8");
		porpertyMap_1.put("t1_cur19_amt", "PRODJRTYZSLCCP");
		porpertyMap_1.put("t1_last19_amt", "PRODJRTYZSLCCP");
		porpertyMap_1.put("t6_cur35_amt", "ASSETAMT1_4_13-ASSETAMT1_2_1-ASSETAMT1_2_2");
		porpertyMap_1.put("t6_last35_amt", "ASSETAMT1_4_13-ASSETAMT1_2_1-ASSETAMT1_2_2");
		porpertyMap_1.put("t6_cur36_amt", "ASSETAMT1_4_a");
		porpertyMap_1.put("t6_last36_amt", "ASSETAMT1_4_a");
		porpertyMap_1.put("t6_cur37_amt", "ASSETAMT1_4_b");
		porpertyMap_1.put("t6_last37_amt", "ASSETAMT1_4_b");
		porpertyMap_1.put("t6_cur38_amt", "ASSETAMT1_4_c");
		porpertyMap_1.put("t6_last38_amt", "ASSETAMT1_4_c");
		porpertyMap_1.put("t6_cur42_amt", "ASSETAMT1_3_1");
		porpertyMap_1.put("t6_last42_amt", "ASSETAMT1_3_1");
		porpertyMap_1.put("t6_cur43_amt", "ASSETAMT1_3_2");
		porpertyMap_1.put("t6_last43_amt", "ASSETAMT1_3_2");
		porpertyMap_1.put("t6_cur44_amt", "ASSETAMT1_1_1");
		porpertyMap_1.put("t6_last44_amt", "ASSETAMT1_1_1");
		porpertyMap_1.put("t6_cur45_amt", "ASSETAMT1_5_3");
		porpertyMap_1.put("t6_last45_amt", "ASSETAMT1_5_3");
		porpertyMap_1.put("t6_cur46_amt", "ASSETAMT1_7_11_a-ASSETAMT1_7_11_b");
		porpertyMap_1.put("t6_last46_amt", "ASSETAMT1_7_11_a-ASSETAMT1_7_11_b");
		porpertyMap_1.put("t6_cur48_amt", "ASSETAMT1_8_5");
		porpertyMap_1.put("t6_last48_amt", "ASSETAMT1_8_5");
		porpertyMap_1.put("t6_cur50_amt", "ASSETAMT1_13_7-ASSETAMT1_14_9");
		porpertyMap_1.put("t6_last50_amt", "ASSETAMT1_13_7-ASSETAMT1_14_9");
		porpertyMap_1.put("t6_cur51_amt", "ASSETAMT1_16_8");
		porpertyMap_1.put("t6_last51_amt", "ASSETAMT1_16_8");
		porpertyMap_1.put("t6_cur54_amt", "ASSETAMT1_15_1");
		porpertyMap_1.put("t6_last54_amt", "ASSETAMT1_15_1");
		porpertyMap_1.put("t6_cur55_amt", "ASSETAMT1_15_2");
		porpertyMap_1.put("t6_last55_amt", "ASSETAMT1_15_2");
		porpertyMap_1.put("t6_cur56_amt", "ASSETAMT1_15_5");
		porpertyMap_1.put("t6_last56_amt", "ASSETAMT1_15_5");
		porpertyMap_1.put("t6_cur57_amt", "ASSETAMT1_15_3-ASSETAMT1_15_4-ASSETAMT1_15_6");
		porpertyMap_1.put("t6_last57_amt", "ASSETAMT1_15_3-ASSETAMT1_15_4-ASSETAMT1_15_6");
		porpertyMap_1.put("t6_cur58_amt", "ASSETAMT1_6_4-ASSETAMT1_7_12-ASSETAMT1_1_2-ASSETAMT1_1_3-ASSETAMT1_1_4-ASSETAMT1_1_5-ASSETAMT1_9_8-ASSETAMT1_10_11-ASSETAMT1_12_7");
		porpertyMap_1.put("t6_last58_amt", "ASSETAMT1_6_4-ASSETAMT1_7_12-ASSETAMT1_1_2-ASSETAMT1_1_3-ASSETAMT1_1_4-ASSETAMT1_1_5-ASSETAMT1_9_8-ASSETAMT1_10_11-ASSETAMT1_12_7");
		
//		porpertyMap_2.put("amt1_1", "");
		porpertyMap_2.put("amt1_1_1", "AMT1_1_1");
		porpertyMap_2.put("amt1_1_2", "AMT1_1_2");
		porpertyMap_2.put("amt1_1_3", "AMT1_1_3");
		porpertyMap_2.put("amt1_1_4", "AMT1_1_4");
		porpertyMap_2.put("amt1_1_5", "AMT1_1_5");
		porpertyMap_2.put("amt1_1_6", "");
//		porpertyMap_2.put("amt1_2", "");
		porpertyMap_2.put("amt1_2_1", "AMT1_3_1");
		porpertyMap_2.put("amt1_2_2", "AMT1_3_2");
		porpertyMap_2.put("amt1_2_3", "AMT1_2_1");
		porpertyMap_2.put("amt1_2_4", "AMT1_2_2");
		porpertyMap_2.put("amt1_2_5", "");
//		porpertyMap_2.put("amt1_3", "");
		porpertyMap_2.put("amt1_3_1", "AMT1_4_1");
		porpertyMap_2.put("amt1_3_2", "AMT1_4_2");
		porpertyMap_2.put("amt1_3_3", "AMT1_4_3");
		porpertyMap_2.put("amt1_3_4", "AMT1_4_4");
		porpertyMap_2.put("amt1_3_5", "AMT1_4_5");
		porpertyMap_2.put("amt1_3_6", "AMT1_4_6");
		porpertyMap_2.put("amt1_3_7", "AMT1_4_7");
		porpertyMap_2.put("amt1_3_8", "AMT1_4_8");
		porpertyMap_2.put("amt1_3_9", "AMT1_4_9");
		porpertyMap_2.put("amt1_3_10", "AMT1_4_10");
		porpertyMap_2.put("amt1_3_11", "AMT1_4_11");
		porpertyMap_2.put("amt1_3_12", "");
		porpertyMap_2.put("amt1_4", "AMT1_5_3");
//		porpertyMap_2.put("amt1_5", "AMT1_6_1+AMT1_6_2+AMT1_6_3");
		porpertyMap_2.put("amt1_5_1", "AMT1_6_1+AMT1_6_2");
		porpertyMap_2.put("amt1_5_2", "AMT1_6_3");
//		porpertyMap_2.put("amt1_6", "");
		porpertyMap_2.put("amt1_6_1", "AMT1_7_1");
		porpertyMap_2.put("amt1_6_2", "AMT1_7_2");
		porpertyMap_2.put("amt1_6_3", "AMT1_7_3");
		porpertyMap_2.put("amt1_6_4", "AMT1_7_4");
		porpertyMap_2.put("amt1_6_5", "AMT1_7_5");
		porpertyMap_2.put("amt1_6_6", "AMT1_7_6");//-融资融券收益权
		porpertyMap_2.put("amt1_6_7", "AMT1_7_7");
		porpertyMap_2.put("amt1_6_8", "AMT1_7_8");
		porpertyMap_2.put("amt1_6_9", "AMT1_7_9");
		porpertyMap_2.put("amt1_6_10", "AMT1_7_10");
		porpertyMap_2.put("amt1_6_11", "");
		porpertyMap_2.put("amt1_6_12", "AMT1_7_11-AMT1_7_11_a-AMT1_7_11_b");
//		porpertyMap_2.put("amt1_7", "");
		porpertyMap_2.put("amt1_7_1", "AMT1_8_1");
		porpertyMap_2.put("amt1_7_2", "AMT1_8_2");
		porpertyMap_2.put("amt1_7_2_1", "");
		porpertyMap_2.put("amt1_7_2_2", "");
		porpertyMap_2.put("amt1_7_3", "AMT1_8_3");
		porpertyMap_2.put("amt1_7_4", "AMT1_7_11_a+AMT1_7_11_b");
		porpertyMap_2.put("amt1_7_5", "");
		porpertyMap_2.put("amt1_7_6", "");
		porpertyMap_2.put("amt1_7_7", "AMT1_8_4");
//		porpertyMap_2.put("amt1_8", "");
		porpertyMap_2.put("amt1_8_1", "AMT1_13_1");
		porpertyMap_2.put("amt1_8_2", "AMT1_13_2");
		porpertyMap_2.put("amt1_8_3", "AMT1_13_3");
		porpertyMap_2.put("amt1_8_4", "AMT1_13_4");
		porpertyMap_2.put("amt1_8_5", "AMT1_13_5");
		porpertyMap_2.put("amt1_8_6", "AMT1_13_6");
//		porpertyMap_2.put("amt1_9", "");
		porpertyMap_2.put("amt1_9_1", "");
		porpertyMap_2.put("amt1_9_2", "");
		porpertyMap_2.put("amt1_9_3", "");
		porpertyMap_2.put("amt1_9_4", "");
		porpertyMap_2.put("amt1_9_5", "");
		porpertyMap_2.put("amt1_9_6", "");
		porpertyMap_2.put("amt1_9_7", "");
		porpertyMap_2.put("amt1_9_8", "");
		porpertyMap_2.put("amt1_9_9", "");
//		porpertyMap_2.put("amt1_10", "");
		porpertyMap_2.put("amt1_10_1", "");
		porpertyMap_2.put("amt1_10_2", "");
		porpertyMap_2.put("amt1_10_3", "");
		porpertyMap_2.put("amt1_10_4", "");
		porpertyMap_2.put("amt1_10_5", "");
		porpertyMap_2.put("amt1_11", "AMT1_9_8");
		porpertyMap_2.put("amt1_12", "AMT1_10_11");
		porpertyMap_2.put("amt1_13", "AMT1_11_4");
		porpertyMap_2.put("amt1_14", "AMT1_12_7");
		porpertyMap_2.put("amt1_14", "AMT1_12_7");
		porpertyMap_2.put("amt1_15", "AMT1_16_8");
		porpertyMap_2.put("amt1_16", "");
		porpertyMap_2.put("amt1_17", "AMT2_1_1");
		porpertyMap_2.put("amt1_18", "AMT2_1_2");
		porpertyMap_2.put("amt1_19", "");
		porpertyMap_2.put("amt1_20", "");
		
		assetTypeMap_2.put("2101", "amt1_4");
		assetTypeMap_2.put("2201", "amt1_5_1");
		assetTypeMap_2.put("2299", "amt1_5_2");
		assetTypeMap_2.put("1106", "amt1_8_1");
		assetTypeMap_2.put("2401", "amt1_8_2");
		assetTypeMap_2.put("1303", "amt1_8_3");
		assetTypeMap_2.put("2403", "amt1_8_4");
		assetTypeMap_2.put("2402", "amt1_8_5");
		assetTypeMap_2.put("2499", "amt1_8_6");
		assetTypeMap_2.put("2601", "amt1_9_1");
		assetTypeMap_2.put("2607", "amt1_9_1");
		assetTypeMap_2.put("2602", "amt1_9_2");
		assetTypeMap_2.put("2604", "amt1_9_4");
		assetTypeMap_2.put("2616", "amt1_9_5");
		assetTypeMap_2.put("2618", "amt1_9_5");
		assetTypeMap_2.put("2619", "amt1_9_5");
		assetTypeMap_2.put("2620", "amt1_9_5");
		assetTypeMap_2.put("2610", "amt1_9_7");
		assetTypeMap_2.put("2611", "amt1_9_8");
		assetTypeMap_2.put("2605", "amt1_9_9");
		assetTypeMap_2.put("2606", "amt1_9_9");
		assetTypeMap_2.put("2617", "amt1_9_9");
		assetTypeMap_2.put("2614", "amt1_9_9");
		assetTypeMap_2.put("2699", "amt1_9_9");
		assetTypeMap_2.put("2613", "amt1_10_2");
		assetTypeMap_2.put("2612", "amt1_10_4");
		assetTypeMap_2.put("07", "amt1_11");
		assetTypeMap_2.put("08", "amt1_12");
		assetTypeMap_2.put("09", "amt1_12");
		assetTypeMap_2.put("10", "amt1_12");
		assetTypeMap_2.put("11", "amt1_12");
		assetTypeMap_2.put("1599", "amt1_12");
		assetTypeMap_2.put("13", "amt1_13");
		assetTypeMap_2.put("18", "amt1_14");
		assetTypeMap_2.put("16", "amt1_15");
		assetTypeMap_2.put("1901", "amt1_17");
		assetTypeMap_2.put("1902", "amt1_18");
		
		porpertyMap_4_1.put("01", "t4_amt1_1");
		porpertyMap_4_1.put("02", "t4_amt1_2");
		porpertyMap_4_1.put("03", "t4_amt1_3");
		porpertyMap_4_1.put("04", "t4_amt1_4");
		porpertyMap_4_1.put("05", "t4_amt1_5");
		porpertyMap_4_1.put("06", "t4_amt1_6");
		porpertyMap_4_1.put("07", "t4_amt1_7");
		
		porpertyMap_4_2.put("01", "t4_amt2_1");
		porpertyMap_4_2.put("02", "t4_amt2_2");
		porpertyMap_4_2.put("03", "t4_amt2_3");
		porpertyMap_4_2.put("04", "t4_amt2_4");
		porpertyMap_4_2.put("05", "t4_amt2_5");
		porpertyMap_4_2.put("06", "t4_amt2_6");
		porpertyMap_4_2.put("07", "t4_amt2_7");
		
		porpertyMap_4_3.put("01", "t4_amt3_1");
		porpertyMap_4_3.put("02", "t4_amt3_2");
		porpertyMap_4_3.put("03", "t4_amt3_3");
		porpertyMap_4_3.put("04", "t4_amt3_4");
		porpertyMap_4_3.put("05", "t4_amt3_5");
		porpertyMap_4_3.put("06", "t4_amt3_6");
		porpertyMap_4_3.put("07", "t4_amt3_7");
		
		porpertyMap_4_4.put("1901", "t4_amt4_1");
		porpertyMap_4_4.put("1902", "t4_amt4_2");
		
		// 表六C32 = 表一E12
		sameValueMap.put("t6_cur29_amt", "t1_cur12_amt");
		// 表六D32 = 表一F12
		sameValueMap.put("t6_last29_amt", "t1_last12_amt");
		// 表六C33 = 表一E13
		sameValueMap.put("t6_cur30_amt", "t1_cur13_amt");
		// 表六D33 = 表一F13
		sameValueMap.put("t6_last30_amt", "t1_last13_amt");
		// 表六C34 = 表一E14
		sameValueMap.put("t6_cur31_amt", "t1_cur14_amt");
		// 表六D34 = 表一F14
		sameValueMap.put("t6_last31_amt", "t1_last14_amt");





		// 表六C32 = 表一E12
		table8ValueMap.put("t1_cur17_amt", "t8_cur17_amt");
		table8ValueMap.put("t1_last17_amt", "t8_last17_amt");
		table8ValueMap.put("t1_cur12_amt", "t8_cur12_amt");
		table8ValueMap.put("t1_last12_amt", "t8_last12_amt");
		table8ValueMap.put("t1_cur13_amt", "t8_cur13_amt");
		table8ValueMap.put("t1_last13_amt", "t8_last13_amt");
		table8ValueMap.put("t2_cur21_amt", "t8_cur21_amt");
		table8ValueMap.put("t2_last21_amt", "t8_last21_amt");
		table8ValueMap.put("t2_cur31_amt", "t8_cur31_amt");
		table8ValueMap.put("t2_last31_amt", "t8_last31_amt");
		table8ValueMap.put("t8_cur01_amt", "t8_cur01_amt");
		table8ValueMap.put("t8_last01_amt", "t8_last01_amt");
		table8ValueMap.put("t8_cur02_amt", "t8_cur02_amt");
		table8ValueMap.put("t8_last02_amt", "t8_last02_amt");
		table8ValueMap.put("t2_cur40_amt", "t8_cur40_amt");
		table8ValueMap.put("t2_last40_amt", "t8_last40_amt");
		table8ValueMap.put("t8_cur03_amt", "t8_cur03_amt");
		table8ValueMap.put("t8_last03_amt", "t8_last03_amt");
		table8ValueMap.put("t2_cur58_amt", "t8_cur58_amt");
		table8ValueMap.put("t2_last58_amt", "t8_last58_amt");

		table8ValueMap.put("t6_cur24_amt", "t8_cur24_amt");
		table8ValueMap.put("t6_last24_amt", "t8_last24_amt");
		table8ValueMap.put("t6_cur23_amt", "t8_cur23_amt");
		table8ValueMap.put("t6_last23_amt", "t8_last23_amt");
		table8ValueMap.put("thamt1_1_11", "t8_thamt1_1_11");
		table8ValueMap.put("t3_40_amt", "t8_40_amt");
		table8ValueMap.put("t3_44_amt", "t8_44_amt");
		table8ValueMap.put("t3_45_amt", "t8_45_amt");
		table8ValueMap.put("t4_amt2_10", "t8_amt2_10");
		table8ValueMap.put("t4_amt1_10", "t8_amt1_10");




	}

	/**  
	 * @Description 查询导出数据  
	 * @Author      高健  
	 * @Date        2019年8月7日 下午1:59:46  
	 * @param @param pageParam
	 * @param @return 参数  
	 * @return Map<Object,Object> 返回类型   
	 * @throws  
	 */  
	public Map<String, Object> createData(Map<String, Object> pageParam) {

		Map<String,Object> data  = new HashMap<String,Object>();
		List<InvestmentBusinessDetailsDto> investmentBusinessDetailsDtoList = new ArrayList<InvestmentBusinessDetailsDto>();
		//investmentBusinessDetailsDtoList.addAll(queryTable1And6Info(pageParam));
		investmentBusinessDetailsDtoList.addAll(queryTable1And2And6Info(pageParam));
		//investmentBusinessDetailsDtoList.addAll(getInvestmentBusinessDetails(pageParam));
		investmentBusinessDetailsDtoList.addAll(queryTable2And3Info(pageParam));
		//新增表5和新附表相关计算方法 add by leipeng 20220304
		investmentBusinessDetailsDtoList.addAll(queryTable5And8And6Info(pageParam));
		//新增表5和新附表相关计算方法 add by leipeng 20220304

		investmentBusinessDetailsDtoList.addAll(queryTable3OtherInfo(pageParam));
		investmentBusinessDetailsDtoList.addAll(queryTable4Info(pageParam));
		investmentBusinessDetailsDtoList.addAll(queryTable7Info(pageParam));
		for(InvestmentBusinessDetailsDto detail : investmentBusinessDetailsDtoList){
			if (data.get(detail.getPropertycode()) != null) {
				// 相同key值需要把数据合并
				detail = mergeSameDto((InvestmentBusinessDetailsDto) data.get(detail.getPropertycode()) , detail);
			}
			data.put(detail.getPropertycode(), divideBillion(detail));
		}

		/***附表8保留一位小数特殊处理*/
		for(Map.Entry<String,String> dto : table8ValueMap.entrySet()){
			data.put(dto.getValue(),divideBillionEx((InvestmentBusinessDetailsDto)data.get(dto.getKey())));
		}


		//登录机构
		InvestmentBusinessDetailsDto reportDto=new InvestmentBusinessDetailsDto();
		reportDto.setPropertycode("report");
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
        	String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
            String orgName=CacheUtil.getAppCacheInstitutionCodeName(org).toString();
			reportDto.setOrgName(orgName);
		}else{
			//银行模式
			String orgName=overallInfoMapper.selectBankModeOrgName();
			reportDto.setOrgName(orgName);
		}
		//数据时点
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		// 本季末期数
		String termId = exportYear + exportQuarter;
		String cdateStr = exportYear + exportQuarter+"01";
		Date cdate=new Date();
		try {
			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cdate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到本季末
		String reportDate = DateUtil.format(calendar.getTime(), "yyyy-MM-dd");
		reportDto.setReportDate(reportDate);
		data.put("report",reportDto);
		return data;
	}
	public List<InvestmentBusinessCounterDetailsDto>  createTable7OtherInfo(Map<String, Object> pageParam) {
		//针对表７-2
		List<InvestmentBusinessCounterDetailsDto> list=queryTable7OtherInfo(pageParam);
		return list;
	}
	public List<InvestmentBusinessDetailsDto>  createTable3AddData(Map<String, Object> pageParam) {
		//针对表3附表
		List<InvestmentBusinessDetailsDto> list=queryTable3AddList(pageParam);

		return list;
	}



	/**
	 * @Description 查询表一数据并组装为Map
	 * @Author
	 * @Date      2021-01-01
	 * @param @param pageParam
	 * @param @return 参数
	 * @return Map<Object,Object> 返回类型
	 * @throws
	 */
	private Map<String, BigDecimal> createTable1Data(Map<String, Object> pageParam) {
		Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>(1 << 4);
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		// 本季末期数
		String termId = exportYear + exportQuarter;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		// 本季末参数
		paraMap.put("termId", termId);
		List<OverallInfoDto> overallInfoList = overallInfoMapper.queryOverallInfoList(paraMap);
		//表6中AA+(含以上)
		paraMap.put("rating", '1');
		OverallInfoDto overallInfoDto1 = overallInfoMapper.queryOverallInfoByRating(paraMap);
		//表6中其它Ａ级
		paraMap.put("rating", '2');
		OverallInfoDto overallInfoDto2 = overallInfoMapper.queryOverallInfoByRating(paraMap);
		//表6中B级及以下
		paraMap.put("rating", '3');
		OverallInfoDto overallInfoDto3 = overallInfoMapper.queryOverallInfoByRating(paraMap);
		//表6中无评级
		paraMap.put("rating", '4');
		OverallInfoDto overallInfoDto4 = overallInfoMapper.queryOverallInfoByRating(paraMap);

		// 上季末参数
		String lastTermId = getLastQuarter(termId);
		paraMap.put("termId", lastTermId);
		List<OverallInfoDto> lastOverallInfoList = overallInfoMapper.queryOverallInfoList(paraMap);
		//AA+(含以上)
		paraMap.put("rating", '1');
		OverallInfoDto lastOverallInfoDto1 = overallInfoMapper.queryOverallInfoByRating(paraMap);
		//其它Ａ级
		paraMap.put("rating", '2');
		OverallInfoDto lastOverallInfoDto2 = overallInfoMapper.queryOverallInfoByRating(paraMap);
		//B级及以下
		paraMap.put("rating", '3');
		OverallInfoDto lastOverallInfoDto3 = overallInfoMapper.queryOverallInfoByRating(paraMap);
		//无评级
		paraMap.put("rating", '4');
		OverallInfoDto lastOverallInfoDto4 = overallInfoMapper.queryOverallInfoByRating(paraMap);



		// 本季末已组装数据
		Map<String, BigDecimal> currMap = assembleMap(overallInfoList);
		// 上季末已组装数据
		Map<String, BigDecimal> lastMap = assembleMap(lastOverallInfoList);
		// 表一E12单元格直接投资为减法运算，单独提取出来
		/*resultMap.put("t1_cur12_amt", queryMoney(currMap, "ASSETAMT1_17")
				.subtract(queryMoney(currMap, "ASSETAMT1_16_8"))
				.subtract(queryMoney(currMap, "ASSETAMT1_15_7")));*/
		// 表一F12单元格直接投资为减法运算，单独提取出来
		/*resultMap.put("t1_last12_amt", queryMoney(lastMap, "ASSETAMT1_17")
				.subtract(queryMoney(lastMap, "ASSETAMT1_16_8"))
				.subtract(queryMoney(lastMap, "ASSETAMT1_15_7")));*/
		// 表六C7数据
		resultMap.put("t6_cur7_amt", overallInfoDto2==null?BigDecimal.ZERO:overallInfoDto1.getBalance());
		// 表六D7数据
		resultMap.put("t6_last7_amt", lastOverallInfoDto2==null?BigDecimal.ZERO:lastOverallInfoDto1.getBalance());
		// 表六C8数据
		resultMap.put("t6_cur8_amt", overallInfoDto2==null?BigDecimal.ZERO:overallInfoDto2.getBalance());
		// 表六D8数据
		resultMap.put("t6_last8_amt", lastOverallInfoDto2==null?BigDecimal.ZERO:lastOverallInfoDto2.getBalance());
		//  表六C9数据
		resultMap.put("t6_cur9_amt", overallInfoDto3==null?BigDecimal.ZERO:overallInfoDto3.getBalance());
		//  表六D9数据
		resultMap.put("t6_last9_amt", lastOverallInfoDto3==null?BigDecimal.ZERO:lastOverallInfoDto3.getBalance());
		// 表六C10
		resultMap.put("t6_cur10_amt",overallInfoDto4==null?BigDecimal.ZERO:overallInfoDto4.getBalance());
		// 表六D10
		resultMap.put("t6_last10_amt", lastOverallInfoDto4==null?BigDecimal.ZERO:lastOverallInfoDto4.getBalance());

		// 表一、表六其余单元格赋值
		for (String key : tablePorpertyMap_1.keySet()) {
			String[] valueList = tablePorpertyMap_1.get(key).split("\\+");
			int length = valueList.length;
			BigDecimal money = BigDecimal.ZERO;
			// 本季末数额
			if (key.contains("cur")) {
				for (int i = 0; i < length; i ++) {
					money = money.add(queryMoney(currMap, valueList[i]));
				}
				// 上季末数额
			} else if (key.contains("last")) {
				for (int i = 0; i < length; i ++) {
					money = money.add(queryMoney(lastMap, valueList[i]));
				}
			}
			resultMap.put(key, money);
		}
        //表一E17合计赋值
        resultMap.put("t1_cur17_amt", resultMap.get("t1_cur12_amt")
                .add(resultMap.get("t1_cur13_amt"))
                .add(resultMap.get("t1_cur14_amt")));

		//表一F17合计赋值
        resultMap.put("t1_last17_amt", resultMap.get("t1_last12_amt")
                .add(resultMap.get("t1_last13_amt"))
                .add(resultMap.get("t1_last14_amt")));

		//附表新增计算逻辑 add by leipeng 20220304
		resultMap.put("t8_cur01_amt", resultMap.get("t1_cur13_amt")
				.add(resultMap.get("t1_cur14_amt")));
		resultMap.put("t8_last01_amt", resultMap.get("t1_last13_amt")
				.add(resultMap.get("t1_last14_amt")));
		//附表新增计算逻辑 add by leipeng 20220304

		// 值相同单元格赋值
		for (String key : sameValueMap.keySet()) {
			resultMap.put(key, resultMap.get(sameValueMap.get(key)));
		}
		return resultMap;
	}

	/**
	 * @Description 查询表二数据并组装为Map
	 * @Author      高健
	 * @Date        2019年8月7日 下午3:41:08
	 * @param @param pageParam
	 * @param @return 参数
	 * @return Map<Object,Object> 返回类型
	 * @throws
	 */
	private Map<String, BigDecimal> createTable2Data(Map<String, Object> pageParam) {
		Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>(1 << 4);
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		// 本季末期数
		String termId = exportYear + exportQuarter;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		// 本季末参数
		paraMap.put("termId", termId);
		List<OverallInfoDto> overallInfoList = overallInfoMapper.queryBottomOverallInfoList(paraMap);
        //AA+(含以上)
		paraMap.put("rating", '1');
		OverallInfoDto overallInfoDto1 = overallInfoMapper.queryBottomOverallInfoByRating(paraMap);
		//其它Ａ级
		paraMap.put("rating", '2');
		OverallInfoDto overallInfoDto2 = overallInfoMapper.queryBottomOverallInfoByRating(paraMap);
		//B级及以下
		paraMap.put("rating", '3');
		OverallInfoDto overallInfoDto3 = overallInfoMapper.queryBottomOverallInfoByRating(paraMap);
		//无评级
		paraMap.put("rating", '4');
		OverallInfoDto overallInfoDto4 = overallInfoMapper.queryBottomOverallInfoByRating(paraMap);

		//定向增发

		//优先股
		OverallInfoDto overallInfoDtoYx = overallInfoMapper.queryBottomOverallInfoYx(paraMap);

		// 上季末参数
		String lastTermId = getLastQuarter(termId);
		paraMap.put("termId", lastTermId);
		List<OverallInfoDto> lastOverallInfoList = overallInfoMapper.queryBottomOverallInfoList(paraMap);
		//AA+(含以上)
		paraMap.put("rating", '1');
		OverallInfoDto lastOverallInfoDto1 = overallInfoMapper.queryBottomOverallInfoByRating(paraMap);
		//其它Ａ级
		paraMap.put("rating", '2');
		OverallInfoDto lastOverallInfoDto2 = overallInfoMapper.queryBottomOverallInfoByRating(paraMap);
		//B级及以下
		paraMap.put("rating", '3');
		OverallInfoDto lastOverallInfoDto3 = overallInfoMapper.queryBottomOverallInfoByRating(paraMap);
		//无评级
		paraMap.put("rating", '4');
		OverallInfoDto lastOverallInfoDto4 = overallInfoMapper.queryBottomOverallInfoByRating(paraMap);
		//定向增发


		//优先股
		OverallInfoDto lastOverallInfoDtoYx = overallInfoMapper.queryBottomOverallInfoYx(paraMap);


		// 本季末已组装数据
		Map<String, BigDecimal> currMap = assembleMap(overallInfoList);
		// 上季末已组装数据
		Map<String, BigDecimal> lastMap = assembleMap(lastOverallInfoList);
		// 表二B36
		resultMap.put("t2_cur36_amt", overallInfoDto1==null?BigDecimal.ZERO:overallInfoDto1.getBalance());
		// 表二C36
		resultMap.put("t2_last36_amt", lastOverallInfoDto1==null?BigDecimal.ZERO:lastOverallInfoDto1.getBalance());
		// 表二B37数据
		resultMap.put("t2_cur37_amt", overallInfoDto2==null?BigDecimal.ZERO:overallInfoDto2.getBalance());
		// 表二C37数据
		resultMap.put("t2_last37_amt", lastOverallInfoDto2==null?BigDecimal.ZERO:lastOverallInfoDto2.getBalance());
		//  表二B38数据
		resultMap.put("t2_cur38_amt", overallInfoDto3==null?BigDecimal.ZERO:overallInfoDto3.getBalance());
		//  表二C38数据
		resultMap.put("t2_last38_amt", lastOverallInfoDto3==null?BigDecimal.ZERO:lastOverallInfoDto3.getBalance());
		// 表二B39
		resultMap.put("t2_cur39_amt",overallInfoDto4==null?BigDecimal.ZERO:overallInfoDto4.getBalance());
		// 表二C39
		resultMap.put("t2_last39_amt", lastOverallInfoDto4==null?BigDecimal.ZERO:lastOverallInfoDto4.getBalance());

		//  表二B59数据
		resultMap.put("t2_cur62_amt", overallInfoDtoYx==null?BigDecimal.ZERO:overallInfoDtoYx.getBalance());
		//  表二C59数据
		resultMap.put("t2_last62_amt", lastOverallInfoDtoYx==null?BigDecimal.ZERO:lastOverallInfoDtoYx.getBalance());

		// 表二其余单元格赋值
		for (String key : tablePorpertyMap_2.keySet()) {
			String[] valueList = tablePorpertyMap_2.get(key).split("\\+");
			int length = valueList.length;
			BigDecimal money = BigDecimal.ZERO;
			// 本季末数额
			if (key.contains("cur")) {
				for (int i = 0; i < length; i ++) {
					money = money.add(queryMoney(currMap, valueList[i]));
				}
				// 上季末数额
			} else if (key.contains("last")) {
				for (int i = 0; i < length; i ++) {
					money = money.add(queryMoney(lastMap, valueList[i]));
				}
			} else if (key.contains("_zq")) {
				for (int i = 0; i < length; i ++) {
					money = money.add(queryMoney(currMap, valueList[i]));
				}
			}else if (key.contains("_lzq")) {
				for (int i = 0; i < length; i ++) {
					money = money.add(queryMoney(lastMap, valueList[i]));
				}
		    }

			resultMap.put(key, money);
		}


		// 附表  7.评级在AA+（不含）以下的非金融企业债 add by leipeng 20220304
		resultMap.put("t8_cur02_amt", resultMap.get("t2_cur37_amt").add(resultMap.get("t2_cur38_amt")).add(resultMap.get("t2_cur39_amt")));
		resultMap.put("t8_last02_amt", resultMap.get("t2_last37_amt").add(resultMap.get("t2_last38_amt")).add(resultMap.get("t2_last39_amt")));
		// 附表  7.评级在AA+（不含）以下的非金融企业债add by leipeng 20220304

		// 附表  9.底层资产无法一一对应的非标债权资产投资余额 add by leipeng 20220304
		resultMap.put("t8_cur03_amt", resultMap.get("t2_cur41_amt").add(resultMap.get("t2_cur42_amt")).add(resultMap.get("t2_cur46_amt"))
				.add(resultMap.get("t2_cur48_amt")).add(resultMap.get("t2_cur51_amt")));
		resultMap.put("t8_last03_amt", resultMap.get("t2_last41_amt").add(resultMap.get("t2_last42_amt")).add(resultMap.get("t2_last46_amt"))
				.add(resultMap.get("t2_last48_amt")).add(resultMap.get("t2_last51_amt")));
		// 附表  9.底层资产无法一一对应的非标债权资产投资余额 add by leipeng 20220304


		//针对表二B57和C57数据
		resultMap.put("t2_cur57_amt", ArithmeticUtil.subtract(resultMap.get("t2_cur57_amtfq"), resultMap.get("t2_cur55_amt")));
		resultMap.put("t2_last57_amt", ArithmeticUtil.subtract(resultMap.get("t2_last57_amtfq"), resultMap.get("t2_last55_amt")));

		return resultMap;
	}
	/**
	 * @Description 查询表二备注数据并组装为Map
	 * @Author      高健
	 * @Date        2019年8月7日 下午3:41:08
	 * @param @param pageParam
	 * @param @return 参数
	 * @return Map<Object,Object> 返回类型
	 * @throws
	 */
	private Map<String, String> createTable2Remark(Map<String, Object> pageParam) {
		Map<String, String> resultRemarkMap = new HashMap<String, String>();
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");

		String cdateStr = exportYear + exportQuarter+"01";
		Date cdate=new Date();
		try {
			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cdate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到本季末
		Date endDate=calendar.getTime();


		// 本季末期数
		String termId = exportYear + exportQuarter;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		// 本季末参数
		paraMap.put("termId", termId);
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		//备注
		paraMap.put("assettype2", "1299");
		paraMap.put("endDate",endDate);
		paraMap.put("svrischlbtm", CacheUtil.getSysParam("SVR_IS_CHL_BTM").toString());
		OverallInfoDto reamrk57 = overallInfoMapper.queryBottomRemarkInfoByInfo(paraMap);
		paraMap.put("assettype2", "1399");
		OverallInfoDto reamrk65 = overallInfoMapper.queryBottomRemarkInfoByInfo(paraMap);
		paraMap.put("assettype2", "2499");
		OverallInfoDto reamrk72 = overallInfoMapper.queryBottomRemarkInfoByInfo(paraMap);
		//备注
		if(null!=reamrk57 && StringUtil.isNotEmpty(reamrk57)){
			resultRemarkMap.put("t2_57_remark", reamrk57.getRemark());
		}else{
			resultRemarkMap.put("t2_57_remark", null);
		}
		if(null!=reamrk65 && StringUtil.isNotEmpty(reamrk65)){
			resultRemarkMap.put("t2_65_remark", reamrk65.getRemark());
		}else{
			resultRemarkMap.put("t2_65_remark", null);
		}
		if(null!=reamrk72 && StringUtil.isNotEmpty(reamrk72)){
			resultRemarkMap.put("t2_72_remark", reamrk72.getRemark());
		}else{
			resultRemarkMap.put("t2_72_remark", null);
		}
		return resultRemarkMap;
	}
	/**
	 * @Description 查询表六备注数据并组装为Map
	 * @Author      高健
	 * @Date        2019年8月7日 下午3:41:08
	 * @param @param pageParam
	 * @param @return 参数
	 * @return Map<Object,Object> 返回类型
	 * @throws
	 */
	private Map<String, String> createTable6Remark(Map<String, Object> pageParam) {
		Map<String, String> resultRemarkMap = new HashMap<String, String>();
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		// 本季末期数
		String termId = exportYear + exportQuarter;
		String cdateStr = exportYear + exportQuarter+"01";
		Date cdate=new Date();
		try {
			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cdate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到本季末
		Date endDate=calendar.getTime();


		Map<String, Object> paraMap = new HashMap<String, Object>();
		// 本季末参数
		paraMap.put("termId", termId);
		List<String> assetType2List16 = new ArrayList<>();
		assetType2List16.add("2501");
		assetType2List16.add("2502");
		//备注
		paraMap.put("assetType2List", assetType2List16);
		paraMap.put("endDate",endDate);
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		paraMap.put("svrischlbtm", CacheUtil.getSysParam("SVR_IS_CHL_BTM").toString());

		OverallInfoDto reamrk16 = overallInfoMapper.queryRemarkInfoByInfo(paraMap);
		//备注
		if(null!=reamrk16 && StringUtil.isNotEmpty(reamrk16)){
			resultRemarkMap.put("t6_16_remark", reamrk16.getRemark());
		}else{
			resultRemarkMap.put("t6_16_remark", null);
		}
		//
		List<String> assetType2List27 = new ArrayList<>();
		assetType2List27.add("1401");
		assetType2List27.add("1402");
		assetType2List27.add("1403");
		assetType2List27.add("1404");
		assetType2List27.add("1405");
		assetType2List27.add("1406");
		assetType2List27.add("1499");
		//备注
		paraMap.put("assetType2List", assetType2List27);
		OverallInfoDto reamrk27 = overallInfoMapper.queryRemarkInfoByInfo(paraMap);
		if(null!=reamrk27 && StringUtil.isNotEmpty(reamrk27)){
			resultRemarkMap.put("t6_27_remark", reamrk27.getRemark());
		}else{
			resultRemarkMap.put("t6_27_remark", null);
		}
		return resultRemarkMap;
	}
	/**  
	 * @Description 获取上季末月份  
	 * @Author      高健  
	 * @Date        2019年8月7日 下午4:02:30  
	 * @param @param selMonth yyyyMM
	 * @param @return 参数  
	 * @return String 返回类型   yyyyMM
	 * @throws  
	 */  
	private String getLastQuarter(String selMonth) {
		String termId = null;
		try {
			Date d = DateUtils.parseDate(selMonth, new String[] {"yyyyMM"});
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(d);
		    calendar.add(Calendar.MONTH, -3);
		    termId = DateUtil.format(calendar.getTime(), "yyyyMM");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		return termId;
	}
	
	/**  
	 * @Description TODO  
	 * @Author      高健  
	 * @Date        2019年8月7日 下午4:06:40  
	 * @param @param overallInfoList
	 * @param @return 参数  
	 * @return Map<String,BigDecimal> 返回类型   
	 * @throws  
	 */  
	private Map<String, BigDecimal> assembleMap(List<OverallInfoDto> overallInfoList) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>(1 << 4);
		for (OverallInfoDto overallInfoDto : overallInfoList) {
			// 资管机构主动管理产品类只需要属性代码作为唯一标识
//			if (StringUtil.equals("AMT1_16_8", overallInfoDto.getPropertyCode())) {
//				map.put(overallInfoDto.getPropertyCode(), overallInfoDto.getBalance());
//			} else {
				map.put(overallInfoDto.getReportType() + overallInfoDto.getPropertyCode(), overallInfoDto.getBalance());
//			}
		}
		return map;
	}


	/**
	 * @Description 查询表五数据并组装为Map
	 * @Author      高健
	 * @Date         add by leipeng 20220304
	 * @param @param pageParam
	 * @param @return 参数
	 * @return Map<Object,Object> 返回类型
	 * @throws
	 */
	private List<Map<String,Object>> createTable5Data(Map<String, Object> pageParam) {
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		// 本季末期数
		String termId = exportYear + exportQuarter;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		// 本季末参数
		paraMap.put("termId", termId);
		// 上季末参数
		String lastTermId = getLastQuarter(termId);
		paraMap.put("lastTermId", lastTermId);
		List<Map<String,Object>> overallInfoList = overallInfoMapper.createTable5Data(paraMap);
		return overallInfoList;
	}


	/**
	 * 查询表二数据
	 * @param pageParam
	 * @return 
	 */
	private List<InvestmentBusinessDetailsDto> getInvestmentBusinessDetails(Map<String, Object> pageParam) {
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMM");
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		String cdateStr = exportYear + exportQuarter+"01";
		Date cdate=new Date();
		try {
			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(cdate);
	    calendar.add(Calendar.MONTH, 1);
	    calendar.set(Calendar.DAY_OF_MONTH, 0);
	    //取到本季末
	    Date endDate=calendar.getTime();
		calendar.add(Calendar.MONTH, -3);
		calendar.add(Calendar.MONTH, 1);
	    calendar.set(Calendar.DAY_OF_MONTH, 0);
		 //取到上季末
		Date lastEndDate=calendar.getTime();
		String termid=sdf.format(cdate);
		calendar.setTime(cdate);
		calendar.add(Calendar.MONTH, -3);
		Date lastDate=calendar.getTime();
		String lastTermid=sdf.format(lastDate);
		List<InvestmentBusinessDetailsDto> allList=investmentBusinessDetailsMapper.selectAllMonthDetailAssetByTermid(termid, lastTermid);
		Map<String,InvestmentBusinessDetailsDto> dtoMap=new HashMap<String,InvestmentBusinessDetailsDto>();
		for(InvestmentBusinessDetailsDto single:allList){
			dtoMap.put(single.getPropertycode(), single);
		}
		List<InvestmentBusinessDetailsDto> otherList=investmentBusinessDetailsMapper.selectOtherData(cdate, lastDate, termid, lastTermid);
		Map<String,InvestmentBusinessDetailsDto> otherdtoMap=new HashMap<String,InvestmentBusinessDetailsDto>();
		for(InvestmentBusinessDetailsDto single:otherList){
			otherdtoMap.put(single.getPropertycode(), single);
		}
		 //查询资管计划
		List<InvestmentBusinessDetailsDto> plans=investmentBusinessDetailsMapper.selectAssetsManagementPlanData(endDate);
		Map<String,InvestmentBusinessDetailsDto> bottomAssetsMap=new HashMap<String,InvestmentBusinessDetailsDto>();
		for(InvestmentBusinessDetailsDto single:plans){
			if(single.getAssetcode()!=null){
				List<InvestmentBusinessDetailsDto> dtos =investmentBusinessDetailsMapper.selectBottomAssetsData(endDate, single.getAssetcode());
				BigDecimal allnum=BigDecimal.ZERO;
				for(InvestmentBusinessDetailsDto dto:dtos){
					allnum=allnum.add(dto.getSessionendNum());
				}
				for(InvestmentBusinessDetailsDto dto:dtos){
					dto.setSessionendNum(dto.getSessionendNum().divide(allnum,17,RoundingMode.HALF_UP).multiply(single.getSessionendNum()));
					if(bottomAssetsMap.containsKey(dto.getAssettype2())){
						InvestmentBusinessDetailsDto mapdto=bottomAssetsMap.get(dto.getAssettype2());
						mapdto.setSessionendNum(mapdto.getSessionendNum().add(dto.getSessionendNum()));
					}else{
						bottomAssetsMap.put(dto.getAssettype2(), dto);
					}
				}
				
			}
		}
		List<InvestmentBusinessDetailsDto> lastplans=investmentBusinessDetailsMapper.selectAssetsManagementPlanData(lastEndDate);
		for(InvestmentBusinessDetailsDto single:lastplans){
			if(single.getAssetcode()!=null){
				List<InvestmentBusinessDetailsDto> dtos =investmentBusinessDetailsMapper.selectBottomAssetsData(lastEndDate, single.getAssetcode());
				BigDecimal allnum=BigDecimal.ZERO;
				for(InvestmentBusinessDetailsDto dto:dtos){
					allnum=allnum.add(dto.getSessionendNum());
				}
				for(InvestmentBusinessDetailsDto dto:dtos){
					dto.setLastSessionendNum(dto.getSessionendNum().divide(allnum,17,RoundingMode.HALF_UP).multiply(single.getSessionendNum()));
					if(bottomAssetsMap.containsKey(dto.getAssettype2())){
						InvestmentBusinessDetailsDto mapdto=bottomAssetsMap.get(dto.getAssettype2());
						if(mapdto.getLastSessionendNum()!=null) {
						    mapdto.setLastSessionendNum(mapdto.getLastSessionendNum().add(dto.getLastSessionendNum()));
						}else {
						    mapdto.setLastSessionendNum(dto.getLastSessionendNum());
						}
					}else{
						dto.setSessionendNum(null);
						bottomAssetsMap.put(dto.getAssettype2(), dto);
					}
				}
				
			}
		}
		Map<String,InvestmentBusinessDetailsDto> bottomResultMap=new HashMap<String,InvestmentBusinessDetailsDto>();
		for(String key:bottomAssetsMap.keySet()){
			InvestmentBusinessDetailsDto value=bottomAssetsMap.get(key);
			if("1002".equals(key)||"1003".equals(key)||"1004".equals(key)||"1005".equals(key)){
				if(bottomResultMap.containsKey("amt1_1_6")){
					InvestmentBusinessDetailsDto dto=bottomResultMap.get("amt1_1_6");
					dto.setSessionendNum(dto.getSessionendNum().add(value.getSessionendNum()));
					if(value.getLastSessionendNum()!=null) {
					    if(dto.getLastSessionendNum()!=null) {
					        dto.setLastSessionendNum(dto.getLastSessionendNum().add(value.getLastSessionendNum()));
					    }else {
					        dto.setLastSessionendNum(value.getLastSessionendNum());
					    }
					}
				}else{
					InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
					dto.setSessionendNum(value.getSessionendNum());
					dto.setLastSessionendNum(value.getLastSessionendNum());
					dto.setPropertycode("amt1_1_6");
					bottomResultMap.put("amt1_1_6", dto);
				}
			}else if("1107".equals(key)||"1108".equals(key)||"2501".equals(key)||"2502".equals(key)){
				if(bottomResultMap.containsKey("amt1_2_5")){
					InvestmentBusinessDetailsDto dto=bottomResultMap.get("amt1_2_5");
					dto.setSessionendNum(dto.getSessionendNum().add(value.getSessionendNum()));
					if(value.getLastSessionendNum()!=null) {
                        if(dto.getLastSessionendNum()!=null) {
                            dto.setLastSessionendNum(dto.getLastSessionendNum().add(value.getLastSessionendNum()));
                        }else {
                            dto.setLastSessionendNum(value.getLastSessionendNum());
                        }
                    }
				}else{
					InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
					dto.setSessionendNum(value.getSessionendNum());
					dto.setLastSessionendNum(value.getLastSessionendNum());
					dto.setPropertycode("amt1_2_5");
					bottomResultMap.put("amt1_2_5", dto);
				}
			}else if("1101".equals(key)||"1110".equals(key)||"1109".equals(key)||"1102".equals(key)
					||"1111".equals(key)||"1112".equals(key)||"1113".equals(key)||"1114".equals(key)
					||"1115".equals(key)||"1116".equals(key)||"1117".equals(key)){
				if(bottomResultMap.containsKey("amt1_3_12")){
					InvestmentBusinessDetailsDto dto=bottomResultMap.get("amt1_3_12");
					dto.setSessionendNum(dto.getSessionendNum().add(value.getSessionendNum()));
					if(value.getLastSessionendNum()!=null) {
                        if(dto.getLastSessionendNum()!=null) {
                            dto.setLastSessionendNum(dto.getLastSessionendNum().add(value.getLastSessionendNum()));
                        }else {
                            dto.setLastSessionendNum(value.getLastSessionendNum());
                        }
                    }
				}else{
					InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
					dto.setSessionendNum(value.getSessionendNum());
					dto.setLastSessionendNum(value.getLastSessionendNum());
					dto.setPropertycode("amt1_3_12");
					bottomResultMap.put("amt1_3_12", dto);
				}
			}else if("1201".equals(key)||"1210".equals(key)||"1202".equals(key)||"1203".equals(key)
					||"1204".equals(key)||"1205".equals(key)||"1206".equals(key)||"1207".equals(key)
					||"1208".equals(key)||"1209".equals(key)||"1299".equals(key)){
				if(bottomResultMap.containsKey("amt1_6_11")){
					InvestmentBusinessDetailsDto dto=bottomResultMap.get("amt1_6_11");
					dto.setSessionendNum(dto.getSessionendNum().add(value.getSessionendNum()));
					if(value.getLastSessionendNum()!=null) {
                        if(dto.getLastSessionendNum()!=null) {
                            dto.setLastSessionendNum(dto.getLastSessionendNum().add(value.getLastSessionendNum()));
                        }else {
                            dto.setLastSessionendNum(value.getLastSessionendNum());
                        }
                    }
				}else{
					InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
					dto.setSessionendNum(value.getSessionendNum());
					dto.setLastSessionendNum(value.getLastSessionendNum());
					dto.setPropertycode("amt1_6_11");
					bottomResultMap.put("amt1_6_11", dto);
				}
			}else if("1301".equals(key)||"1305".equals(key)||"1302".equals(key)||"1399".equals(key)){
				if(bottomResultMap.containsKey("amt1_7_6")){
					InvestmentBusinessDetailsDto dto=bottomResultMap.get("amt1_7_6");
					dto.setSessionendNum(dto.getSessionendNum().add(value.getSessionendNum()));
					if(value.getLastSessionendNum()!=null) {
                        if(dto.getLastSessionendNum()!=null) {
                            dto.setLastSessionendNum(dto.getLastSessionendNum().add(value.getLastSessionendNum()));
                        }else {
                            dto.setLastSessionendNum(value.getLastSessionendNum());
                        }
                    }
				}else{
					InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
					dto.setSessionendNum(value.getSessionendNum());
					dto.setLastSessionendNum(value.getLastSessionendNum());
					dto.setPropertycode("amt1_7_6");
					bottomResultMap.put("amt1_7_6", dto);
				}
			}else {
				if(assetTypeMap_2.containsKey(value.getAssettype())){
					if(bottomResultMap.containsKey(assetTypeMap_2.get(value.getAssettype()))){
						InvestmentBusinessDetailsDto dto=bottomResultMap.get(assetTypeMap_2.get(value.getAssettype()));
						dto.setSessionendNum(dto.getSessionendNum().add(value.getSessionendNum()));
						if(value.getLastSessionendNum()!=null) {
	                        if(dto.getLastSessionendNum()!=null) {
	                            dto.setLastSessionendNum(dto.getLastSessionendNum().add(value.getLastSessionendNum()));
	                        }else {
	                            dto.setLastSessionendNum(value.getLastSessionendNum());
	                        }
	                    }
					}else{
						InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
						dto.setSessionendNum(value.getSessionendNum());
						dto.setLastSessionendNum(value.getLastSessionendNum());
						dto.setPropertycode(assetTypeMap_2.get(value.getAssettype()));
						bottomResultMap.put(assetTypeMap_2.get(value.getAssettype()), dto);
					}
				}
				if(assetTypeMap_2.containsKey(key)){
					if(bottomResultMap.containsKey(assetTypeMap_2.get(key))){
						InvestmentBusinessDetailsDto dto=bottomResultMap.get(assetTypeMap_2.get(key));
						dto.setSessionendNum(dto.getSessionendNum().add(value.getSessionendNum()));
						if(value.getLastSessionendNum()!=null) {
	                        if(dto.getLastSessionendNum()!=null) {
	                            dto.setLastSessionendNum(dto.getLastSessionendNum().add(value.getLastSessionendNum()));
	                        }else {
	                            dto.setLastSessionendNum(value.getLastSessionendNum());
	                        }
	                    }
					}else{
						InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
						dto.setSessionendNum(value.getSessionendNum());
						dto.setLastSessionendNum(value.getLastSessionendNum());
						dto.setPropertycode(assetTypeMap_2.get(key));
						bottomResultMap.put(assetTypeMap_2.get(key), dto);
					}
				}
			}
		}
		List<InvestmentBusinessDetailsDto> resultList=new ArrayList<InvestmentBusinessDetailsDto>();
		for(String key:porpertyMap_2.keySet()){
			String value=porpertyMap_2.get(key);
			if(StringUtil.isNotEmpty(value)){
				if(value.contains("+")){
					InvestmentBusinessDetailsDto model=new InvestmentBusinessDetailsDto();
					model.setPropertycode(key);
					model.setSessionendNum(BigDecimal.ZERO);
					model.setLastSessionendNum(BigDecimal.ZERO);
					model.setNonperformingAssetNum(BigDecimal.ZERO);
					model.setLastNonperformingAssetNum(BigDecimal.ZERO);
					String [] valueStrs=value.split("\\+");
					for(String va:valueStrs){
						if(dtoMap.containsKey(va)){
							model.setSessionendNum(model.getSessionendNum().add(dtoMap.get(va).getSessionendNum()));
							model.setLastSessionendNum(model.getLastSessionendNum().add(dtoMap.get(va).getLastSessionendNum()));
							model.setNonperformingAssetNum(model.getNonperformingAssetNum().add(dtoMap.get(va).getNonperformingAssetNum()));
							model.setLastNonperformingAssetNum(model.getLastNonperformingAssetNum().add(dtoMap.get(va).getLastNonperformingAssetNum()));
						}
					}
					if(bottomResultMap.containsKey(key)){
						model.setSessionendNum(model.getSessionendNum().add(bottomResultMap.get(key).getSessionendNum()));
						if(bottomResultMap.get(key).getLastSessionendNum()!=null) {
                            if(model.getLastSessionendNum()!=null) {
                                model.setLastSessionendNum(model.getLastSessionendNum().add(bottomResultMap.get(key).getLastSessionendNum()));
                            }else {
                                model.setLastSessionendNum(bottomResultMap.get(key).getLastSessionendNum()); 
                            }
                        }
					}
					resultList.add(model);
				}else if(value.contains("-")){
					InvestmentBusinessDetailsDto model=new InvestmentBusinessDetailsDto();
					model.setPropertycode(key);
					model.setSessionendNum(BigDecimal.ZERO);
					model.setLastSessionendNum(BigDecimal.ZERO);
					model.setNonperformingAssetNum(BigDecimal.ZERO);
					model.setLastNonperformingAssetNum(BigDecimal.ZERO);
					String [] valueStrs=value.split("-");
					for(int i=0;i<valueStrs.length; i++){
						String va=valueStrs[i];
						if(dtoMap.containsKey(va)){
							if(i==0){
								model.setSessionendNum(model.getSessionendNum().add(dtoMap.get(va).getSessionendNum()));
								model.setLastSessionendNum(model.getLastSessionendNum().add(dtoMap.get(va).getLastSessionendNum()));
								model.setNonperformingAssetNum(model.getNonperformingAssetNum().add(dtoMap.get(va).getNonperformingAssetNum()));
								model.setLastNonperformingAssetNum(model.getLastNonperformingAssetNum().add(dtoMap.get(va).getLastNonperformingAssetNum()));
							}else{
								model.setSessionendNum(model.getSessionendNum().subtract(dtoMap.get(va).getSessionendNum()));
								model.setLastSessionendNum(model.getLastSessionendNum().subtract(dtoMap.get(va).getLastSessionendNum()));
								model.setNonperformingAssetNum(model.getNonperformingAssetNum().subtract(dtoMap.get(va).getNonperformingAssetNum()));
								model.setLastNonperformingAssetNum(model.getLastNonperformingAssetNum().subtract(dtoMap.get(va).getLastNonperformingAssetNum()));
							}
						}
					}
					if(bottomResultMap.containsKey(key)){
						model.setSessionendNum(model.getSessionendNum().add(bottomResultMap.get(key).getSessionendNum()));
						if(bottomResultMap.get(key).getLastSessionendNum()!=null) {
						    if(model.getLastSessionendNum()!=null) {
						        model.setLastSessionendNum(model.getLastSessionendNum().add(bottomResultMap.get(key).getLastSessionendNum()));
						    }else {
						        model.setLastSessionendNum(bottomResultMap.get(key).getLastSessionendNum()); 
						    }
						}
					}
					resultList.add(model);
				}else{
					if(dtoMap.containsKey(value)){
						InvestmentBusinessDetailsDto model=dtoMap.get(value);
						model.setPropertycode(key);
						if(key.equals("amt1_6_6")){
							if(otherdtoMap.containsKey(key)){
								InvestmentBusinessDetailsDto othermodel=otherdtoMap.get(key);
								model.setSessionendNum(model.getSessionendNum().subtract(othermodel.getSessionendNum()==null?BigDecimal.ZERO:othermodel.getSessionendNum()));
								model.setLastSessionendNum(model.getLastSessionendNum().subtract(othermodel.getLastSessionendNum()==null?BigDecimal.ZERO:othermodel.getLastSessionendNum()));
							}
						}
						if(bottomResultMap.containsKey(key)){
							model.setSessionendNum(model.getSessionendNum().add(bottomResultMap.get(key).getSessionendNum()));
							if(bottomResultMap.get(key).getLastSessionendNum()!=null) {
							    if(model.getLastSessionendNum()!=null) {
							        model.setLastSessionendNum(model.getLastSessionendNum().add(bottomResultMap.get(key).getLastSessionendNum()));
							    }else {
							        model.setLastSessionendNum(bottomResultMap.get(key).getLastSessionendNum()); 
							    }
							}
						}
						resultList.add(model);
					}else {
					    if(bottomResultMap.containsKey(key)){
    					    InvestmentBusinessDetailsDto model=new InvestmentBusinessDetailsDto();
    	                    model.setPropertycode(key);
    	                    model.setSessionendNum(BigDecimal.ZERO);
    	                    model.setLastSessionendNum(BigDecimal.ZERO);
    	                    model.setNonperformingAssetNum(BigDecimal.ZERO);
    	                    model.setLastNonperformingAssetNum(BigDecimal.ZERO);
    					        model.setSessionendNum(model.getSessionendNum().add(bottomResultMap.get(key).getSessionendNum()));
    					        if(bottomResultMap.get(key).getLastSessionendNum()!=null) {
    					            if(model.getLastSessionendNum()!=null) {
    					                model.setLastSessionendNum(model.getLastSessionendNum().add(bottomResultMap.get(key).getLastSessionendNum()));
    					            }else {
    					                model.setLastSessionendNum(bottomResultMap.get(key).getLastSessionendNum()); 
    					            }
    					        }
    					        resultList.add(model);
					    }
					}
				}
			}else{
			 if(key.equals("amt1_1_6")){
				 if(bottomResultMap.containsKey("amt1_1_6")){
					 InvestmentBusinessDetailsDto dto= bottomResultMap.get("amt1_1_6");
					 dto.setPropertycode(key);
					 resultList.add(dto);
				 }
			 }else if(key.equals("amt1_2_5")){
				 if(bottomResultMap.containsKey("amt1_2_5")){
					 InvestmentBusinessDetailsDto dto= bottomResultMap.get("amt1_2_5");
					 dto.setPropertycode(key);
					 resultList.add(dto);
				 }
			 }else if(key.equals("amt1_3_12")){
				 if(bottomResultMap.containsKey("amt1_3_12")){
					 InvestmentBusinessDetailsDto dto= bottomResultMap.get("amt1_3_12");
					 dto.setPropertycode(key);
					 resultList.add(dto);
				 }
			 }else if(key.equals("amt1_6_11")){
				 if(bottomResultMap.containsKey("amt1_6_11")){
					 InvestmentBusinessDetailsDto dto= bottomResultMap.get("amt1_6_11");
					 dto.setPropertycode(key);
					 resultList.add(dto);
				 }
			 }else if(key.equals("amt1_7_6")){
				 if(bottomResultMap.containsKey("amt1_7_6")){
					 InvestmentBusinessDetailsDto dto= bottomResultMap.get("amt1_7_6");
					 dto.setPropertycode(key);
					 resultList.add(dto);
				 }
			 }else if(key.equals("amt1_7_5")){
				 if(otherdtoMap.containsKey("amt1_6_6")){
					 InvestmentBusinessDetailsDto dto= otherdtoMap.get("amt1_6_6");
					 dto.setPropertycode(key);
					 resultList.add(dto);
				 }
				}
			 else if(key.equals("amt1_7_2_1")){
				 if(otherdtoMap.containsKey(key)){
					 resultList.add(otherdtoMap.get(key));
				 }
			 }else if(key.equals("amt1_7_2_2")){
				 if(otherdtoMap.containsKey(key)){
					 resultList.add(otherdtoMap.get(key));
				 }
			 }else if(key.equals("amt1_10_1")){
				 if(otherdtoMap.containsKey(key)){
					 resultList.add(otherdtoMap.get(key));
				 }
			 }else if(key.equals("amt1_19")){
				 if(otherdtoMap.containsKey(key)){
					 resultList.add(otherdtoMap.get(key));
				 }
			 }else {
			     if(bottomResultMap.containsKey(key)){
                     InvestmentBusinessDetailsDto dto= bottomResultMap.get(key);
                     dto.setPropertycode(key);
                     resultList.add(dto);
                 }
			 }
			}
		
		}
		
		// 增加表三公募基金季末数额
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto = investmentBusinessDetailsMapper.queryDiffAsset(cdate, lastDate);
		BigDecimal sessionendNum = dtoMap.get("AMT1_13_1") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_1").getSessionendNum() 
				.add(dtoMap.get("AMT1_13_2") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_2").getSessionendNum())
				.add(dtoMap.get("AMT1_13_3") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_3").getSessionendNum())
				.add(dtoMap.get("AMT1_13_4") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_4").getSessionendNum())
				.add(dtoMap.get("AMT1_13_5") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_5").getSessionendNum())
				.add(dtoMap.get("AMT1_13_6") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_6").getSessionendNum());
		BigDecimal lastSessionendNum = dtoMap.get("AMT1_13_1") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_1").getLastSessionendNum()
				.add(dtoMap.get("AMT1_13_2") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_2").getLastSessionendNum())
				.add(dtoMap.get("AMT1_13_3") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_3").getLastSessionendNum())
				.add(dtoMap.get("AMT1_13_4") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_4").getLastSessionendNum())
				.add(dtoMap.get("AMT1_13_5") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_5").getLastSessionendNum())
				.add(dtoMap.get("AMT1_13_6") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_13_6").getLastSessionendNum());
		investmentBusinessDetailsDto.setSessionendNum(sessionendNum);
		investmentBusinessDetailsDto.setLastSessionendNum(lastSessionendNum);
		resultList.add(investmentBusinessDetailsDto);
		// 增加表三其他（请列明）金融业本季末数额和上季末数额
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto1 = new InvestmentBusinessDetailsDto();
		investmentBusinessDetailsDto1.setPropertycode("thamt1_1_10");
		BigDecimal otherAmount = dtoMap.get("AMT1_1_1") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_1").getSessionendNum()
				.add(dtoMap.get("AMT1_1_2") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_2").getSessionendNum())
				.add(dtoMap.get("AMT1_1_3") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_3").getSessionendNum())
				.add(dtoMap.get("AMT1_1_4") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_4").getSessionendNum())
				.add(dtoMap.get("AMT1_1_5") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_5").getSessionendNum())
				.add(dtoMap.get("AMT1_3_1") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_3_1").getSessionendNum())
				.add(dtoMap.get("AMT1_3_2") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_3_2").getSessionendNum())
				.add(dtoMap.get("AMT1_2_1") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_2_1").getSessionendNum())
				.add(dtoMap.get("AMT1_2_2") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_2_2").getSessionendNum())
				.add(dtoMap.get("AMT1_9_8") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_9_8").getSessionendNum());
		BigDecimal lastOtherAmount = dtoMap.get("AMT1_1_1") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_1").getLastSessionendNum()
				.add(dtoMap.get("AMT1_1_2") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_2").getLastSessionendNum())
				.add(dtoMap.get("AMT1_1_3") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_3").getLastSessionendNum())
				.add(dtoMap.get("AMT1_1_4") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_4").getLastSessionendNum())
				.add(dtoMap.get("AMT1_1_5") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_1_5").getLastSessionendNum())
				.add(dtoMap.get("AMT1_3_1") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_3_1").getLastSessionendNum())
				.add(dtoMap.get("AMT1_3_2") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_3_2").getLastSessionendNum())
				.add(dtoMap.get("AMT1_2_1") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_2_1").getLastSessionendNum())
				.add(dtoMap.get("AMT1_2_2") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_2_2").getLastSessionendNum())
				.add(dtoMap.get("AMT1_9_8") == null ? BigDecimal.ZERO : dtoMap.get("AMT1_9_8").getLastSessionendNum());
		investmentBusinessDetailsDto1.setOtherAmount(otherAmount);
		investmentBusinessDetailsDto1.setLastOtherAmount(lastOtherAmount);
		resultList.add(investmentBusinessDetailsDto1);
		return resultList;
	}

	/**
	 * 组装表四数据到实体类
	 * @param pageParam
	 * @return
	 */
	private List<InvestmentBusinessDetailsDto> queryTable4Info(Map<String, Object> pageParam) {
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		String cdateStr = exportYear + exportQuarter+"01";
		Date cdate=new Date();
		try {
			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cdate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到本季末
		Date endDate=calendar.getTime();


		calendar.add(Calendar.MONTH, -3);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到上季末
		Date lastEndDate=calendar.getTime();

		//今年年初
		calendar.setTime(cdate);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date yearStartDate=calendar.getTime();
		//上一季度年初
		Calendar calendarlastQ = Calendar.getInstance();
		calendarlastQ.setTime(lastEndDate);
		calendarlastQ.set(Calendar.MONTH, 0);
		calendarlastQ.set(Calendar.DAY_OF_MONTH, 1);
		Date yearStartDateLastQ=calendarlastQ.getTime();


		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		paraMap.put("endDate",endDate);
		List<InvestmentBusinessDetailsDto> allList=new ArrayList<InvestmentBusinessDetailsDto>();
		List<InvestmentBusinessDetailsDto> prodList =termStructureOfBusinessAndCostBenefitMapper.selectProductBalanceByInfo(paraMap);
		paraMap.put("endDate",lastEndDate);
		List<InvestmentBusinessDetailsDto> lastprodList =termStructureOfBusinessAndCostBenefitMapper.selectProductBalanceByInfo(paraMap);

		//资金来源的平均年化资金成本
		List<InvestmentBusinessDetailsDto> prodRateList=null;
		List<InvestmentBusinessDetailsDto> lastprodRateList=null;

		String valcalc=CacheUtil.getSysParam("SVR_VALCALC_FLAG").toString();
		if(StringUtil.isNotEmpty(valcalc) && valcalc.equals("01")){
			//公式一
			//本季度末

			paraMap.put("valconfig", CacheUtil.getSysParam("SVR_VALCONFIG_FLAG").toString());

			Calendar calendarlast = Calendar.getInstance();
			calendarlast.setTime(endDate);
			calendarlast.add(Calendar.YEAR, -1);
			calendarlast.add(Calendar.MONTH, 1);
			calendarlast.set(Calendar.DAY_OF_MONTH, 0);
			//上年今日
			Date lastYearDate=calendarlast.getTime();

			paraMap.put("endDate", endDate);
			//paraMap.put("lastYearDate", lastYearDate);
			//今年年初
			paraMap.put("yearStartDate", yearStartDate);

			prodRateList =termStructureOfBusinessAndCostBenefitMapper.selectProductAvgRateInfo1(paraMap);

			//上季度末
			Calendar calendarlastlast = Calendar.getInstance();
			calendarlastlast.setTime(lastEndDate);
			calendarlastlast.add(Calendar.YEAR, -1);
			calendarlastlast.add(Calendar.MONTH, 1);
			calendarlastlast.set(Calendar.DAY_OF_MONTH, 0);
			//上年今日
			Date lastlastYearDate=calendarlastlast.getTime();
			paraMap.put("endDate", lastEndDate);
			paraMap.put("yearStartDate", yearStartDateLastQ);
			lastprodRateList =termStructureOfBusinessAndCostBenefitMapper.selectProductAvgRateInfo1(paraMap);

		}else{
			//公式二
			//本季度末
			paraMap.put("valconfig", CacheUtil.getSysParam("SVR_VALCONFIG_FLAG").toString());

			Calendar calendarlast = Calendar.getInstance();
			calendarlast.setTime(endDate);
			calendarlast.add(Calendar.YEAR, -1);
			calendarlast.add(Calendar.MONTH, 1);
			calendarlast.set(Calendar.DAY_OF_MONTH, 0);
			//上年今日
			Date lastYearDate=calendarlast.getTime();

			paraMap.put("endDate", endDate);
			paraMap.put("lastEndDate", lastEndDate);
			//paraMap.put("lastYearDate", lastYearDate);
			paraMap.put("yearStartDate", yearStartDate);
            prodRateList =termStructureOfBusinessAndCostBenefitMapper.selectProductAvgRateInfo2(paraMap);
			//上季度末
			Calendar calendarlastlast = Calendar.getInstance();
			calendarlastlast.setTime(lastEndDate);
			calendarlastlast.add(Calendar.YEAR, -1);
			calendarlastlast.add(Calendar.MONTH, 1);
			calendarlastlast.set(Calendar.DAY_OF_MONTH, 0);
			//上年今日
			Date lastlastYearDate=calendarlastlast.getTime();

			Calendar calendarlastEndDate= Calendar.getInstance();
			calendarlastEndDate.setTime(lastEndDate);
			calendarlastEndDate.add(Calendar.MONTH, -3);
			calendarlastEndDate.add(Calendar.MONTH, 1);
			calendarlastEndDate.set(Calendar.DAY_OF_MONTH, 0);
			Date lastlastEndDate=calendarlastEndDate.getTime();

			paraMap.put("endDate", lastEndDate);
			paraMap.put("lastEndDate", lastlastEndDate);
			//paraMap.put("lastYearDate", lastlastYearDate);
			paraMap.put("yearStartDate", yearStartDateLastQ);

			lastprodRateList =termStructureOfBusinessAndCostBenefitMapper.selectProductAvgRateInfo2(paraMap);

		}

		Map<String,InvestmentBusinessDetailsDto> prodMap=new HashMap<String,InvestmentBusinessDetailsDto>();
		for(InvestmentBusinessDetailsDto single:prodList){
			prodMap.put(single.getTermType(), single);
		}
		for(InvestmentBusinessDetailsDto single:lastprodList){
			if(prodMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=prodMap.get(single.getTermType());
				dto.setLastSessionendBalance(single.getSessionendBalance());
			}else{
				single.setLastSessionendBalance(single.getSessionendBalance());
				single.setSessionendBalance(BigDecimal.ZERO);
				prodMap.put(single.getTermType(), single);
			}
		}

		for(InvestmentBusinessDetailsDto single:prodRateList){
			if(prodMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=prodMap.get(single.getTermType());
				dto.setSessionendYield(single.getSessionendYield());
			}else{
				prodMap.put(single.getTermType(), single);
			}
		}
		for(InvestmentBusinessDetailsDto single:lastprodRateList){
			if(prodMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=prodMap.get(single.getTermType());
				dto.setLastSessionendYield(single.getSessionendYield());
			}else{
				single.setLastSessionendYield(single.getSessionendYield());
				single.setSessionendYield(BigDecimal.ZERO);
				prodMap.put(single.getTermType(), single);
			}
		}




		//资金运用
		Map<String,InvestmentBusinessDetailsDto> assetMap=new HashMap<String,InvestmentBusinessDetailsDto>();

		paraMap.put("execonfig", CacheUtil.getSysParam("SEC_ASSETS_MDATE_CONF").toString());
		paraMap.put("endDate",endDate);
		paraMap.put("svrischlbtm", CacheUtil.getSysParam("SVR_IS_CHL_BTM").toString());
		List<InvestmentBusinessDetailsDto> assetBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectAssetBalanceByInfo(paraMap);
		paraMap.put("endDate",lastEndDate);

		List<InvestmentBusinessDetailsDto> lastassetBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectAssetBalanceByInfo(paraMap);
		//资金运用的“'平均年化收益水平”
		List<InvestmentBusinessDetailsDto> assetRateList=null;
		List<InvestmentBusinessDetailsDto> lastassetRateList=null;

		if(StringUtil.isNotEmpty(valcalc) && valcalc.equals("01")) {
			//本季度末

			paraMap.put("endDate", endDate);
			paraMap.put("lastEndDate", lastEndDate);
			paraMap.put("yearStartDate", yearStartDate);
			assetRateList =termStructureOfBusinessAndCostBenefitMapper.selectAssetRateByInfo1(paraMap);
			//上季度末

			calendar.add(Calendar.MONTH, -3);
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			//取到上季末
			Date lastlastEndDate=calendar.getTime();
			paraMap.put("endDate", lastEndDate);
			paraMap.put("lastEndDate", lastlastEndDate);
			paraMap.put("yearStartDate", yearStartDateLastQ);
			lastassetRateList =termStructureOfBusinessAndCostBenefitMapper.selectAssetRateByInfo1(paraMap);
		}else{
			//本季度末

			paraMap.put("endDate", endDate);
			paraMap.put("lastEndDate", lastEndDate);
			paraMap.put("yearStartDate", yearStartDate);
			assetRateList =termStructureOfBusinessAndCostBenefitMapper.selectAssetRateByInfo2(paraMap);
			//上季度末
			Calendar calendarlastEndDate= Calendar.getInstance();
			calendarlastEndDate.setTime(lastEndDate);
			calendarlastEndDate.add(Calendar.MONTH, -3);
			calendarlastEndDate.add(Calendar.MONTH, 1);
			calendarlastEndDate.set(Calendar.DAY_OF_MONTH, 0);
			Date lastlastEndDate=calendarlastEndDate.getTime();

			paraMap.put("endDate", lastEndDate);
			paraMap.put("lastEndDate", lastlastEndDate);
			paraMap.put("yearStartDate", yearStartDateLastQ);
			lastassetRateList =termStructureOfBusinessAndCostBenefitMapper.selectAssetRateByInfo2(paraMap);

		}
		for(InvestmentBusinessDetailsDto single:assetBalanceList){
			assetMap.put(single.getTermType(), single);
		}
		for(InvestmentBusinessDetailsDto single:lastassetBalanceList){
			if(assetMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=assetMap.get(single.getTermType());
				dto.setLastSessionendBalance(single.getSessionendBalance());
			}else{
				single.setLastSessionendBalance(single.getSessionendBalance());
				single.setSessionendBalance(BigDecimal.ZERO);
				assetMap.put(single.getTermType(), single);
			}
		}
		for(InvestmentBusinessDetailsDto single:assetRateList){
			if(assetMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=assetMap.get(single.getTermType());
				dto.setSessionendYield(single.getSessionendYield());
			}else{
				assetMap.put(single.getTermType(), single);
			}
		}
		for(InvestmentBusinessDetailsDto single:lastassetRateList){
			if(assetMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=assetMap.get(single.getTermType());
				dto.setLastSessionendYield(single.getSessionendYield());
			}else{
				single.setLastSessionendYield(single.getSessionendYield());
				single.setSessionendYield(BigDecimal.ZERO);
				assetMap.put(single.getTermType(), single);
			}
		}
		//资金运用的非标债权
		Map<String,InvestmentBusinessDetailsDto> assetNSMap=new HashMap<String,InvestmentBusinessDetailsDto>();
		paraMap.put("endDate",endDate);
		List<InvestmentBusinessDetailsDto> assetNSBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectNSAssetBalanceByInfo(paraMap);
		paraMap.put("endDate",lastEndDate);
		List<InvestmentBusinessDetailsDto> lastassetNSBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectNSAssetBalanceByInfo(paraMap);
		//资金运用的“'平均年化收益水平”
		List<InvestmentBusinessDetailsDto> assetNSRateList=null;
		List<InvestmentBusinessDetailsDto> lastassetNSRateList=null;

		if(StringUtil.isNotEmpty(valcalc) && valcalc.equals("01")) {
			//本季度末

			paraMap.put("endDate", endDate);
			paraMap.put("lastEndDate", lastEndDate);
			paraMap.put("yearStartDate", yearStartDate);

			assetNSRateList =termStructureOfBusinessAndCostBenefitMapper.selectNSAssetRateByInfo1(paraMap);


			//上上季度末
			Calendar calendarlastEndDate= Calendar.getInstance();
			calendarlastEndDate.setTime(lastEndDate);
			calendarlastEndDate.add(Calendar.MONTH, -3);
			calendarlastEndDate.add(Calendar.MONTH, 1);
			calendarlastEndDate.set(Calendar.DAY_OF_MONTH, 0);
			Date lastlastEndDate=calendarlastEndDate.getTime();

			paraMap.put("endDate", lastEndDate);
			paraMap.put("lastEndDate", lastlastEndDate);
			paraMap.put("yearStartDate", yearStartDateLastQ);

			lastassetNSRateList =termStructureOfBusinessAndCostBenefitMapper.selectNSAssetRateByInfo1(paraMap);
		}else{
			//本季度末

			paraMap.put("endDate", endDate);
			paraMap.put("lastEndDate", lastEndDate);
			paraMap.put("yearStartDate", yearStartDate);

			assetNSRateList =termStructureOfBusinessAndCostBenefitMapper.selectNSAssetRateByInfo2(paraMap);
			//上季度末

			//上季度末
			Calendar calendarlastEndDate= Calendar.getInstance();
			calendarlastEndDate.setTime(lastEndDate);
			calendarlastEndDate.add(Calendar.MONTH, -3);
			calendarlastEndDate.add(Calendar.MONTH, 1);
			calendarlastEndDate.set(Calendar.DAY_OF_MONTH, 0);
			Date lastlastEndDate=calendarlastEndDate.getTime();

			paraMap.put("endDate", lastEndDate);
			paraMap.put("lastEndDate", lastlastEndDate);
			paraMap.put("yearStartDate", yearStartDateLastQ);

			lastassetNSRateList =termStructureOfBusinessAndCostBenefitMapper.selectNSAssetRateByInfo2(paraMap);

		}

		for(InvestmentBusinessDetailsDto single:assetNSBalanceList){
			assetNSMap.put(single.getTermType(), single);
		}
		for(InvestmentBusinessDetailsDto single:lastassetNSBalanceList){
			if(assetNSMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=assetNSMap.get(single.getTermType());
				dto.setLastSessionendBalance(single.getSessionendBalance());
			}else{
				single.setLastSessionendBalance(single.getSessionendBalance());
				single.setSessionendBalance(BigDecimal.ZERO);
				assetNSMap.put(single.getTermType(), single);
			}
		}

		for(InvestmentBusinessDetailsDto single:assetNSRateList){
			if(assetNSMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=assetNSMap.get(single.getTermType());
				dto.setSessionendYield(single.getSessionendYield());
				dto.setLastSessionendYield(single.getLastSessionendYield());
			}else{
				assetNSMap.put(single.getTermType(), single);
			}
		}
		for(InvestmentBusinessDetailsDto single:lastassetNSRateList){
			if(assetNSMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=assetNSMap.get(single.getTermType());
				dto.setLastSessionendYield(single.getSessionendYield());
			}else{
				single.setLastSessionendYield(single.getSessionendYield());
				single.setSessionendYield(BigDecimal.ZERO);
				assetNSMap.put(single.getTermType(), single);
			}
		}
		for(String key:prodMap.keySet()){
			String propertycode=tablePorpertyMap_4_1.get(key);
			InvestmentBusinessDetailsDto dto=prodMap.get(key);
			if(dto!=null){
				dto.setPropertycode(propertycode);
				allList.add(dto);
			}
		}
		for (String key : tablePorpertyMap_4_1.keySet()) {
			boolean isexist=false;
			if(prodMap.containsKey(key) && prodMap.get(key)!=null){
				isexist=true;
			}
			if(!isexist){
				String value = tablePorpertyMap_4_1.get(key);
				InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
				dto.setPropertycode(value);
				allList.add(dto);
			}
		}
		for(String key:assetMap.keySet()){
			String propertycode=tablePorpertyMap_4_2.get(key);
			InvestmentBusinessDetailsDto dto=assetMap.get(key);
			if(dto!=null){
				dto.setPropertycode(propertycode);
				allList.add(dto);
			}
		}
		for (String key : tablePorpertyMap_4_2.keySet()) {
			boolean isexist=false;
			if(assetMap.containsKey(key) && assetMap.get(key)!=null){
				isexist=true;
			}
			if(!isexist){
				String value = tablePorpertyMap_4_2.get(key);
				InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
				dto.setPropertycode(value);
				allList.add(dto);
			}
		}
		for(String key:assetNSMap.keySet()){
			String propertycode=tablePorpertyMap_4_3.get(key);
			InvestmentBusinessDetailsDto dto=assetNSMap.get(key);
			if(dto!=null){
				dto.setPropertycode(propertycode);
				allList.add(dto);
			}
		}
		for (String key : tablePorpertyMap_4_3.keySet()) {
			boolean isexist=false;
			if(assetNSMap.containsKey(key) && assetNSMap.get(key)!=null){
				isexist=true;
			}
			if(!isexist){
				String value = tablePorpertyMap_4_3.get(key);
				InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
				dto.setPropertycode(value);
				allList.add(dto);
			}
		}

		Map<String,InvestmentBusinessDetailsDto> incurDebtsMap=new HashMap<String,InvestmentBusinessDetailsDto>();
		paraMap.put("endDate", endDate);
		List<InvestmentBusinessDetailsDto> incurDebtsBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectIncurDebtsBalanceByInfo(paraMap);
		paraMap.put("endDate", lastEndDate);
		List<InvestmentBusinessDetailsDto> lastincurDebtsBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectIncurDebtsBalanceByInfo(paraMap);
		paraMap.put("endDate", endDate);
		List<InvestmentBusinessDetailsDto> incurDebtsRateList =termStructureOfBusinessAndCostBenefitMapper.selectIncurDebtsRateByInfo(paraMap);
		paraMap.put("endDate", lastEndDate);
		List<InvestmentBusinessDetailsDto> lastincurDebtsRateList =termStructureOfBusinessAndCostBenefitMapper.selectIncurDebtsRateByInfo(paraMap);

		for(InvestmentBusinessDetailsDto single:incurDebtsBalanceList){
			incurDebtsMap.put(single.getTermType(), single);
		}
		for(InvestmentBusinessDetailsDto single:lastincurDebtsBalanceList){
			if(incurDebtsMap.containsKey(single.getTermType())){
				single.setLastSessionendBalance(single.getSessionendBalance());
			}else{
				single.setLastSessionendBalance(single.getSessionendBalance());
				single.setSessionendBalance(BigDecimal.ZERO);
				incurDebtsMap.put(single.getTermType(), single);
			}
		}
		for(InvestmentBusinessDetailsDto single:incurDebtsRateList){
			if(incurDebtsMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=incurDebtsMap.get(single.getTermType());
				dto.setSessionendYield(single.getSessionendYield());
			}else{
				incurDebtsMap.put(single.getTermType(), single);
			}
		}
		for(InvestmentBusinessDetailsDto single:lastincurDebtsRateList){
			if(incurDebtsMap.containsKey(single.getTermType())){
				InvestmentBusinessDetailsDto dto=incurDebtsMap.get(single.getTermType());
				dto.setLastSessionendYield(single.getSessionendYield());
			}else{
				single.setLastSessionendYield(single.getSessionendYield());
				single.setSessionendYield(BigDecimal.ZERO);
				incurDebtsMap.put(single.getTermType(), single);
			}
		}

		for(String key:incurDebtsMap.keySet()){
			String propertycode=tablePorpertyMap_4_4.get(key);
			InvestmentBusinessDetailsDto dto=incurDebtsMap.get(key);
			if(dto!=null){
				dto.setPropertycode(propertycode);
				allList.add(dto);
			}
		}
		for (String key : tablePorpertyMap_4_4.keySet()) {
			boolean isexist=false;
			if(incurDebtsMap.containsKey(key) && incurDebtsMap.get(key)!=null){
				isexist=true;
			}
			if(!isexist){
				String value = tablePorpertyMap_4_4.get(key);
				InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
				dto.setPropertycode(value);
				allList.add(dto);
			}
		}
		//收益率合并计算
		doSumAvgYeild(allList);

		return allList;

	}

	/**
	 * 数据计算修改.合计项目直接使用固定公式
	 * ∑（本季末余额*年初至本季末累计年化收益率）/本季末余额合计
	 * @param dtoList
	 */
	private void doSumAvgYeild(List<InvestmentBusinessDetailsDto> dtoList){
		//拆分所有的
		BigDecimal amt1 = BigDecimal.ZERO;
		BigDecimal amt1Last = BigDecimal.ZERO;
		BigDecimal amt2 = BigDecimal.ZERO;
		BigDecimal amt2Last = BigDecimal.ZERO;
		BigDecimal amt3 = BigDecimal.ZERO;
		BigDecimal amt3Last = BigDecimal.ZERO;

		for(InvestmentBusinessDetailsDto dto : dtoList){
			if(!dto.getPropertycode().endsWith("_10")){
				if(dto.getPropertycode().startsWith("t4_amt1")){
					amt1 = amt1.add(IsNotZeroBigDecimal(dto,"01")?dto.getSessionendBalance().multiply(dto.getSessionendYield()):BigDecimal.ZERO);
					amt1Last = amt1Last.add(IsNotZeroBigDecimal(dto,"02")?dto.getLastSessionendBalance().multiply(dto.getLastSessionendYield()):BigDecimal.ZERO);
				}else if(dto.getPropertycode().startsWith("t4_amt2")){
					amt2 = amt2.add(IsNotZeroBigDecimal(dto,"01")?dto.getSessionendBalance().multiply(dto.getSessionendYield()):BigDecimal.ZERO);
					amt2Last = amt2Last.add(IsNotZeroBigDecimal(dto,"02")?dto.getLastSessionendBalance().multiply(dto.getLastSessionendYield()):BigDecimal.ZERO);
				}else if(dto.getPropertycode().startsWith("t4_amt3")){
					amt3 = amt3.add(IsNotZeroBigDecimal(dto,"01")?dto.getSessionendBalance().multiply(dto.getSessionendYield()):BigDecimal.ZERO);
					amt3Last = amt3Last.add(IsNotZeroBigDecimal(dto,"02")?dto.getLastSessionendBalance().multiply(dto.getLastSessionendYield()):BigDecimal.ZERO);
				}
			}
		}
		/***汇总项目重新计算*/
		for(InvestmentBusinessDetailsDto dto : dtoList){
			if(dto.getPropertycode().endsWith("_10")){
				if(dto.getPropertycode().startsWith("t4_amt1")){
					dto.setSessionendYield(null!=dto.getSessionendBalance()&&dto.getSessionendBalance().compareTo(BigDecimal.ZERO)!=0?
							amt1.divide(dto.getSessionendBalance(),4,BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
					dto.setLastSessionendYield(null!=dto.getLastSessionendBalance()&&dto.getLastSessionendBalance().compareTo(BigDecimal.ZERO)!=0?
							amt1Last.divide(dto.getLastSessionendBalance(),4,BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
				}else if(dto.getPropertycode().startsWith("t4_amt2")){
					dto.setSessionendYield(null!=dto.getSessionendBalance()&&dto.getSessionendBalance().compareTo(BigDecimal.ZERO)!=0?
							amt2.divide(dto.getSessionendBalance(),4,BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
					dto.setLastSessionendYield(null!=dto.getLastSessionendBalance()&&dto.getLastSessionendBalance().compareTo(BigDecimal.ZERO)!=0?
							amt2Last.divide(dto.getLastSessionendBalance(),4,BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
				}else if(dto.getPropertycode().startsWith("t4_amt3")){
					dto.setSessionendYield(null!=dto.getSessionendBalance()&&dto.getSessionendBalance().compareTo(BigDecimal.ZERO)!=0?
							amt3.divide(dto.getSessionendBalance(),4,BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
					dto.setLastSessionendYield(null!=dto.getLastSessionendBalance()&&dto.getLastSessionendBalance().compareTo(BigDecimal.ZERO)!=0?
							amt3Last.divide(dto.getLastSessionendBalance(),4,BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
				}
			}
		}

	}


	/**
	 * 判断是否为空或者为0
	 * @return
	 */
	public static boolean IsNotZeroBigDecimal(InvestmentBusinessDetailsDto dto,String code){
		boolean flag = false;
			if("01".equals(code)){
				if(null!=dto.getSessionendBalance()&&dto.getSessionendBalance().compareTo(BigDecimal.ZERO)!=0
						&&null!=dto.getSessionendYield()&&dto.getSessionendYield().compareTo(BigDecimal.ZERO)!=0){
					flag = true;
				}
			}else if("02".equals(code)){
				if(null!=dto.getLastSessionendBalance()&&dto.getLastSessionendBalance().compareTo(BigDecimal.ZERO)!=0
						&&null!=dto.getLastSessionendYield()&&dto.getLastSessionendYield().compareTo(BigDecimal.ZERO)!=0){
					flag = true;
				}
			}
		return flag;
	}
	/**
	 * 组装表7-1数据到实体类
	 * @param pageParam
	 * @return
	 */
	private List<InvestmentBusinessDetailsDto> queryTable7Info(Map<String, Object> pageParam) {
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		String cdateStr = exportYear + exportQuarter+"01";
		Date cdate=new Date();
		try {
			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cdate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到本季末
		Date endDate=calendar.getTime();

		calendar.add(Calendar.MONTH, -3);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到上季末
		Date lastEndDate=calendar.getTime();
		//今年年初
		calendar.setTime(cdate);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date yearStartDate=calendar.getTime();
		//上一季度年初
		Calendar calendarlastQ = Calendar.getInstance();
		calendarlastQ.setTime(lastEndDate);
		calendarlastQ.set(Calendar.MONTH, 0);
		calendarlastQ.set(Calendar.DAY_OF_MONTH, 1);
		Date yearStartDateLastQ=calendarlastQ.getTime();

		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		List<InvestmentBusinessDetailsDto> allList=new ArrayList<InvestmentBusinessDetailsDto>();

		String valcalc=CacheUtil.getSysParam("SVR_VALCALC_FLAG").toString();
		//资金运用
		Map<String,InvestmentBusinessDetailsDto> assetMap=new HashMap<String,InvestmentBusinessDetailsDto>();
		paraMap.put("endDate",endDate);
		List<InvestmentBusinessDetailsDto> financeAssetBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectFinanceAssetBalanceByInfo(paraMap);
		paraMap.put("endDate",lastEndDate);
		List<InvestmentBusinessDetailsDto> lastFinanceAssetBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectFinanceAssetBalanceByInfo(paraMap);
		//资金运用的“'平均年化收益水平”
		List<InvestmentBusinessDetailsDto> financeAssetRateList=null;
		List<InvestmentBusinessDetailsDto> lastFinanceAssetRateList=null;

		if(StringUtil.isNotEmpty(valcalc) && valcalc.equals("01")) {
			//公式一
			//本季度末


			paraMap.put("endDate", endDate);
			paraMap.put("lastEndDate", lastEndDate);
			paraMap.put("yearStartDate", yearStartDate);

			financeAssetRateList =termStructureOfBusinessAndCostBenefitMapper.selectFinanceAssetRateByInfo1(paraMap);
			//上上季度末
			Calendar calendarlastEndDate= Calendar.getInstance();
			calendarlastEndDate.setTime(lastEndDate);
			calendarlastEndDate.add(Calendar.MONTH, -3);
			calendarlastEndDate.add(Calendar.MONTH, 1);
			calendarlastEndDate.set(Calendar.DAY_OF_MONTH, 0);
			Date lastlastEndDate=calendarlastEndDate.getTime();
			paraMap.put("endDate", lastEndDate);
			paraMap.put("lastEndDate", lastlastEndDate);
			paraMap.put("yearStartDate", yearStartDateLastQ);

			lastFinanceAssetRateList =termStructureOfBusinessAndCostBenefitMapper.selectFinanceAssetRateByInfo1(paraMap);
		}else{
			//公式二
			//本季度末


			paraMap.put("endDate", endDate);
			paraMap.put("lastEndDate", lastEndDate);
			paraMap.put("yearStartDate", yearStartDate);

			financeAssetRateList =termStructureOfBusinessAndCostBenefitMapper.selectFinanceAssetRateByInfo2(paraMap);
			//上季度末
			Calendar calendarlastEndDate= Calendar.getInstance();
			calendarlastEndDate.setTime(lastEndDate);
			calendarlastEndDate.add(Calendar.MONTH, -3);
			calendarlastEndDate.add(Calendar.MONTH, 1);
			calendarlastEndDate.set(Calendar.DAY_OF_MONTH, 0);
			Date lastlastEndDate=calendarlastEndDate.getTime();

			paraMap.put("endDate", lastEndDate);
			paraMap.put("lastEndDate", lastlastEndDate);
			paraMap.put("yearStartDate", yearStartDateLastQ);

			lastFinanceAssetRateList =termStructureOfBusinessAndCostBenefitMapper.selectFinanceAssetRateByInfo2(paraMap);

		}
		for(InvestmentBusinessDetailsDto single:financeAssetBalanceList){
			assetMap.put(single.getIndustryType(), single);
		}
		for(InvestmentBusinessDetailsDto single:lastFinanceAssetBalanceList){
			if(assetMap.containsKey(single.getIndustryType())){
				InvestmentBusinessDetailsDto dto=assetMap.get(single.getIndustryType());
				dto.setLastSessionendBalance(single.getSessionendBalance());
			}else{
				single.setLastSessionendBalance(single.getSessionendBalance());
				single.setSessionendBalance(BigDecimal.ZERO);
				assetMap.put(single.getIndustryType(), single);
			}
		}
		for(InvestmentBusinessDetailsDto single:financeAssetRateList){
			if(assetMap.containsKey(single.getIndustryType())){
				InvestmentBusinessDetailsDto dto=assetMap.get(single.getIndustryType());
				dto.setSessionendYield(single.getSessionendYield());
			}else{
				assetMap.put(single.getIndustryType(), single);
			}
		}
		for(InvestmentBusinessDetailsDto single:lastFinanceAssetRateList){
			if(assetMap.containsKey(single.getIndustryType())){
				InvestmentBusinessDetailsDto dto=assetMap.get(single.getIndustryType());
				dto.setLastSessionendYield(single.getSessionendYield());
			}else{
				single.setLastSessionendYield(single.getSessionendYield());
				single.setSessionendYield(BigDecimal.ZERO);
				assetMap.put(single.getIndustryType(), single);
			}
		}

		for(String key:assetMap.keySet()){
			String propertycode=tablePorpertyMap_7_1.get(key);
			InvestmentBusinessDetailsDto dto=assetMap.get(key);
			if(dto!=null){
				dto.setPropertycode(propertycode);
				allList.add(dto);
			}
		}
		for (String key : tablePorpertyMap_7_1.keySet()) {
			boolean isexist=false;
			if(assetMap.containsKey(key) && assetMap.get(key)!=null){
				isexist=true;
			}
			if(!isexist){
				String value = tablePorpertyMap_7_1.get(key);
				InvestmentBusinessDetailsDto dto=new InvestmentBusinessDetailsDto();
				dto.setPropertycode(value);
				allList.add(dto);
			}
		}
		return allList;

	}

	/**
	 * 组装表7-2数据到实体类
	 * @param pageParam
	 * @return
	 */
	private List<InvestmentBusinessCounterDetailsDto> queryTable7OtherInfo(Map<String, Object> pageParam) {
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		String cdateStr = exportYear + exportQuarter+"01";
		Date cdate=new Date();
		try {
			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cdate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到本季末
		Date endDate=calendar.getTime();

		calendar.add(Calendar.MONTH, -3);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		//取到上季末
		Date lastEndDate=calendar.getTime();

		List<InvestmentBusinessCounterDetailsDto> returnAllList=new ArrayList<InvestmentBusinessCounterDetailsDto>();
		Map<Integer, InvestmentBusinessCounterDetailsDto> porpertyDtoMap = new HashMap<Integer, InvestmentBusinessCounterDetailsDto>();



		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		paraMap.put("endDate", endDate);
		paraMap.put("lastEndDate", lastEndDate);
		List<InvestmentBusinessCounterDetailsDto> selectCounterBalanceList =new ArrayList<InvestmentBusinessCounterDetailsDto>();

		paraMap.put("industryType", "XT");
		List<InvestmentBusinessCounterDetailsDto> xtCounterBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectCounterBalanceListByInfo(paraMap);
		selectCounterBalanceList.addAll(xtCounterBalanceList);
		paraMap.put("industryType", "ZQQT");
		List<InvestmentBusinessCounterDetailsDto> zqqtCounterBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectCounterBalanceListByInfo(paraMap);
		selectCounterBalanceList.addAll(zqqtCounterBalanceList);
		paraMap.put("industryType", "ZQJJ");
		List<InvestmentBusinessCounterDetailsDto> zqjjCounterBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectCounterBalanceListByInfo(paraMap);
		selectCounterBalanceList.addAll(zqjjCounterBalanceList);

		paraMap.put("industryType", "YH");
		List<InvestmentBusinessCounterDetailsDto> yhCounterBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectCounterBalanceListByInfo(paraMap);
		selectCounterBalanceList.addAll(yhCounterBalanceList);

		paraMap.put("industryType", "BX");
		List<InvestmentBusinessCounterDetailsDto> bxCounterBalanceList =termStructureOfBusinessAndCostBenefitMapper.selectCounterBalanceListByInfo(paraMap);
		selectCounterBalanceList.addAll(bxCounterBalanceList);

		 Integer returnIndex=0;
		for(InvestmentBusinessCounterDetailsDto dto:selectCounterBalanceList){
			if(porpertyDtoMap.containsKey(dto.getPropertycode())){
				//存在
				InvestmentBusinessCounterDetailsDto existDto=porpertyDtoMap.get(dto.getPropertycode());
				if(dto.getPropertytype().equals("XT")){
					existDto.setXtCounterName(dto.getCounterName());
					existDto.setXtSessionendNum(dto.getSessionendNum());
					existDto.setXtLastSessionendNum(dto.getLastSessionendNum());
					existDto.setXtZdSessionendNum(dto.getZdSessionendNum());
					existDto.setXtFbSessionendNum(dto.getFbSessionendNum());

				}else if(dto.getPropertytype().equals("ZQQT")){
					existDto.setZqqhCounterName(dto.getCounterName());
					existDto.setZqqhSessionendNum(dto.getSessionendNum());
					existDto.setZqqhLastSessionendNum(dto.getLastSessionendNum());
					existDto.setZqqhZdSessionendNum(dto.getZdSessionendNum());
					existDto.setZqqhFbSessionendNum(dto.getFbSessionendNum());
				}else if(dto.getPropertytype().equals("ZQJJ")){
					existDto.setZqjjCounterName(dto.getCounterName());
					existDto.setZqjjSessionendNum(dto.getSessionendNum());
					existDto.setZqjjLastSessionendNum(dto.getLastSessionendNum());
					existDto.setZqjjZdSessionendNum(dto.getZdSessionendNum());
					existDto.setZqjjFbSessionendNum(dto.getFbSessionendNum());
				}else if(dto.getPropertytype().equals("YH")){
					existDto.setYhCounterName(dto.getCounterName());
					existDto.setYhSessionendNum(dto.getSessionendNum());
					existDto.setYhLastSessionendNum(dto.getLastSessionendNum());
					existDto.setYhZdSessionendNum(dto.getZdSessionendNum());
					existDto.setYhFbSessionendNum(dto.getFbSessionendNum());
				}else if(dto.getPropertytype().equals("BX")){
					existDto.setBxCounterName(dto.getCounterName());
					existDto.setBxSessionendNum(dto.getSessionendNum());
					existDto.setBxLastSessionendNum(dto.getLastSessionendNum());
					existDto.setBxZdSessionendNum(dto.getZdSessionendNum());
					existDto.setBxFbSessionendNum(dto.getFbSessionendNum());
				}
			}else{
				//不存在
				returnIndex=returnIndex+1;
				InvestmentBusinessCounterDetailsDto newDto=new InvestmentBusinessCounterDetailsDto();
				newDto.setPropertycode(returnIndex);
				if(dto.getPropertytype().equals("XT")){
					newDto.setXtCounterName(dto.getCounterName());
					newDto.setXtSessionendNum(dto.getSessionendNum());
					newDto.setXtLastSessionendNum(dto.getLastSessionendNum());
					newDto.setXtZdSessionendNum(dto.getZdSessionendNum());
					newDto.setXtFbSessionendNum(dto.getFbSessionendNum());

				}else if(dto.getPropertytype().equals("ZQQT")){
					newDto.setZqqhCounterName(dto.getCounterName());
					newDto.setZqqhSessionendNum(dto.getSessionendNum());
					newDto.setZqqhLastSessionendNum(dto.getLastSessionendNum());
					newDto.setZqqhZdSessionendNum(dto.getZdSessionendNum());
					newDto.setZqqhFbSessionendNum(dto.getFbSessionendNum());
				}else if(dto.getPropertytype().equals("ZQJJ")){
					newDto.setZqjjCounterName(dto.getCounterName());
					newDto.setZqjjSessionendNum(dto.getSessionendNum());
					newDto.setZqjjLastSessionendNum(dto.getLastSessionendNum());
					newDto.setZqjjZdSessionendNum(dto.getZdSessionendNum());
					newDto.setZqjjFbSessionendNum(dto.getFbSessionendNum());
				}else if(dto.getPropertytype().equals("YH")){
					newDto.setYhCounterName(dto.getCounterName());
					newDto.setYhSessionendNum(dto.getSessionendNum());
					newDto.setYhLastSessionendNum(dto.getLastSessionendNum());
					newDto.setYhZdSessionendNum(dto.getZdSessionendNum());
					newDto.setYhFbSessionendNum(dto.getFbSessionendNum());
				}else if(dto.getPropertytype().equals("BX")){
					newDto.setBxCounterName(dto.getCounterName());
					newDto.setBxSessionendNum(dto.getSessionendNum());
					newDto.setBxLastSessionendNum(dto.getLastSessionendNum());
					newDto.setBxZdSessionendNum(dto.getZdSessionendNum());
					newDto.setBxFbSessionendNum(dto.getFbSessionendNum());
				}
				porpertyDtoMap.put(returnIndex,newDto);
				returnAllList.add(newDto);
			}
		}

        for(InvestmentBusinessCounterDetailsDto detail : returnAllList){

            divideBillionOfCounter(detail);
        }
		return returnAllList;

	}

	/**
	 * @Description 组装表一、表六数据到实体类  
	 * @Author      高健  
	 * @Date        2019年8月8日 上午11:53:42  
	 * @param @param pageParam
	 * @param @return 参数  
	 * @return List<InvestmentBusinessDetailsDto> 返回类型   
	 * @throws  
	 */  
	private List<InvestmentBusinessDetailsDto> queryTable1And6Info(Map<String, Object> pageParam) {
		Map<String, BigDecimal> map = createTable1Data(pageParam);
		List<InvestmentBusinessDetailsDto> InvestmentBusinessDetailsList = new ArrayList<InvestmentBusinessDetailsDto>();
		for (String key : map.keySet()) {
			InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
			investmentBusinessDetailsDto.setPropertycode(key);
			// 本季末余额
			if (key.contains("cur")) {
				investmentBusinessDetailsDto.setTqeAmount(map.get(key));
				// 上季末数额
			} else if (key.contains("last")) {
				investmentBusinessDetailsDto.setTlqeAmount(map.get(key));
			}
			InvestmentBusinessDetailsList.add(investmentBusinessDetailsDto);
		}
		return InvestmentBusinessDetailsList;
	}
	/**
	 * @Description 组装表一、表二、表六数据到实体类
	 * @Author
	 * @Date        2019年8月8日 上午11:53:42
	 * @param @param pageParam
	 * @param @return 参数
	 * @return List<InvestmentBusinessDetailsDto> 返回类型
	 * @throws
	 */
	private List<InvestmentBusinessDetailsDto> queryTable1And2And6Info(Map<String, Object> pageParam) {
		Map<String, BigDecimal> map = createTable1Data(pageParam);
		Map<String, String > reamrkMap = createTable6Remark(pageParam);

		List<InvestmentBusinessDetailsDto> InvestmentBusinessDetailsList = new ArrayList<InvestmentBusinessDetailsDto>();
		for (String key : map.keySet()) {
			InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
			investmentBusinessDetailsDto.setPropertycode(key);
			// 本季末余额
			if (key.contains("cur")) {
				investmentBusinessDetailsDto.setTqeAmount(map.get(key));
				// 上季末数额
			} else if (key.contains("last")) {
				investmentBusinessDetailsDto.setTlqeAmount(map.get(key));
			}
			InvestmentBusinessDetailsList.add(investmentBusinessDetailsDto);
		}
		for (String key : reamrkMap.keySet()) {
			InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
			investmentBusinessDetailsDto.setPropertycode(key);
			//备注
			if (key.contains("remark")) {
				investmentBusinessDetailsDto.setRemark(reamrkMap.get(key));
			}
			InvestmentBusinessDetailsList.add(investmentBusinessDetailsDto);
		}
		return InvestmentBusinessDetailsList;
	}

	/**
	 * @Description 组装表5数据到实体类
	 * @Author
	 * @Date        add by leipeng 20220304
	 * @param @param pageParam
	 * @param @return 参数
	 * @return List<InvestmentBusinessDetailsDto> 返回类型
	 * @throws
	 */
	private List<InvestmentBusinessDetailsDto> queryTable5And8And6Info(Map<String, Object> pageParam) {
		List<Map<String,Object>> ListMap = createTable5Data(pageParam);

		//为了保证页面正常展示.所有数据key都列出来
		String keyCodes = "01_01_F01|01_02_F01|01_03_F01|01_99_F01|01_01_F06|01_02_F06|01_03_F06|01_99_F06|02_01_F01|02_02_F01|02_03_F01|02_99_F01|02_01_F06|02_02_F06|02_03_F06|02_99_F06|99_01_F01|99_02_F01|99_03_F01|99_99_F01|99_01_F06|99_02_F06|99_03_F06|99_99_F06";
		Set<String> codeSet = new HashSet<>(Arrays.asList(keyCodes.split("\\|")));

		List<InvestmentBusinessDetailsDto> InvestmentBusinessDetailsList = new ArrayList<InvestmentBusinessDetailsDto>();


		InvestmentBusinessDetailsDto sumDto = new InvestmentBusinessDetailsDto();
		sumDto.setPropertycode("sum");
		BigDecimal valueA = BigDecimal.ZERO;
		BigDecimal valueB = BigDecimal.ZERO;
		BigDecimal valueALast = BigDecimal.ZERO;
		BigDecimal valueBLast = BigDecimal.ZERO;
		BigDecimal valueB01 = BigDecimal.ZERO;
		BigDecimal valueBlast01 = BigDecimal.ZERO;

		//遍历查询出来的月报数据信息
		for (Map<String,Object> map: ListMap) {
			InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
			investmentBusinessDetailsDto.setPropertycode(map.get("VALCODE").toString());
			//如果查到了数据就将set集合中的删除掉
			codeSet.remove(map.get("VALCODE").toString());
			//表五计算用 投资余额 -穿透前
			investmentBusinessDetailsDto.setValueA((BigDecimal) map.get("VALUEA"));
			//表五计算用 投资余额 -穿透后
			investmentBusinessDetailsDto.setValueB((BigDecimal) map.get("VALUEB"));
			//表五计算用 投资余额 -上季 -穿透前
			investmentBusinessDetailsDto.setValueALast((BigDecimal) map.get("VALUEALAST"));
			//表五计算用 投资余额 -上季 -穿透后
			investmentBusinessDetailsDto.setValueBLast((BigDecimal) map.get("VALUEBLAST"));
			//表五计算用 平均杠杆率 -穿透前
			investmentBusinessDetailsDto.setAvgRate1((BigDecimal) map.get("AVGRATE1"));
			//表五计算用 平均杠杆率 -穿透后
			investmentBusinessDetailsDto.setAvgRate2((BigDecimal) map.get("AVGRATE2"));
			//表五计算用 平均杠杆率 -穿透前 - 上季
			investmentBusinessDetailsDto.setAvgRate1Last((BigDecimal) map.get("AVGRATE1LAST"));
			//表五计算用 平均杠杆率 -穿透后 - 上季
			investmentBusinessDetailsDto.setAvgRate2Last((BigDecimal) map.get("AVGRATE2LAST"));
			//表五计算用 杠杆倍数最高值 -本机
			investmentBusinessDetailsDto.setHeaverTimes1((BigDecimal) map.get("HEAVERTIMES1"));
			//表五计算用 杠杆倍数最高值 -上季
			investmentBusinessDetailsDto.setHeaverTimes2((BigDecimal) map.get("HEAVERTIMES2"));
			//表五计算用 信贷资产支持证券
			investmentBusinessDetailsDto.setValueB01((BigDecimal) map.get("VALUEB01"));
			//表五计算用 信贷资产支持证券 - 上季
			investmentBusinessDetailsDto.setValueBlast01((BigDecimal) map.get("VALUEBLAST01"));
			//表五计算用 资产支持专项计划
			investmentBusinessDetailsDto.setValueB02((BigDecimal) map.get("VALUEB02"));
			//表五计算用 资产支持专项计划 - 上季
			investmentBusinessDetailsDto.setValueBlast02((BigDecimal) map.get("VALUEBLAST02"));

			//同级合计数据  F01和F06的合计数据需要分开
			valueA = valueA.add(null!=investmentBusinessDetailsDto.getValueA()&&investmentBusinessDetailsDto.getPropertycode().contains("F06")?investmentBusinessDetailsDto.getValueA():BigDecimal.ZERO);
			valueB = valueB.add(null!=investmentBusinessDetailsDto.getValueB()&&investmentBusinessDetailsDto.getPropertycode().contains("F01")?investmentBusinessDetailsDto.getValueB():BigDecimal.ZERO);
			valueALast = valueALast.add(null!=investmentBusinessDetailsDto.getValueALast()&&investmentBusinessDetailsDto.getPropertycode().contains("F06")?investmentBusinessDetailsDto.getValueALast():BigDecimal.ZERO);
			valueBLast = valueBLast.add(null!=investmentBusinessDetailsDto.getValueBLast()&&investmentBusinessDetailsDto.getPropertycode().contains("F01")?investmentBusinessDetailsDto.getValueBLast():BigDecimal.ZERO);
			valueB01 = valueB01.add(null!=investmentBusinessDetailsDto.getValueB01()&&investmentBusinessDetailsDto.getPropertycode().contains("F01")?investmentBusinessDetailsDto.getValueB01():BigDecimal.ZERO);
			valueBlast01 = valueBlast01.add(null!=investmentBusinessDetailsDto.getValueBlast01()&&investmentBusinessDetailsDto.getPropertycode().contains("F01")?investmentBusinessDetailsDto.getValueBlast01():BigDecimal.ZERO);
			InvestmentBusinessDetailsList.add(investmentBusinessDetailsDto);
		}
		//将没有数据的分类进行初始化
		for (String code : codeSet){
			InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
			investmentBusinessDetailsDto.setPropertycode(code);
			InvestmentBusinessDetailsList.add(investmentBusinessDetailsDto);

		}
		//附表银保监局	城商行 字段

		//合计数据添加
		sumDto.setValueA(valueA);
		sumDto.setValueB(valueB);
		sumDto.setValueALast(valueALast);
		sumDto.setValueBLast(valueBLast);
		sumDto.setValueB01(valueB01);
		sumDto.setValueBlast01(valueBlast01);
		InvestmentBusinessDetailsList.add(sumDto);
		//附表的机构编码及银监编号
		InvestmentBusinessDetailsDto detailsDto = investmentBusinessDetailsMapper.queryOrgName();
		InvestmentBusinessDetailsList.add(detailsDto);

		return InvestmentBusinessDetailsList;
	}
	/**
	 * @Description 组装表二、表三数据到实体类
	 * @Author
	 * @Date        2019年8月8日 上午11:53:42
	 * @param @param pageParam
	 * @param @return 参数
	 * @return List<InvestmentBusinessDetailsDto> 返回类型
	 * @throws
	 */
	private List<InvestmentBusinessDetailsDto> queryTable2And3Info(Map<String, Object> pageParam) {
		Map<String, BigDecimal> map = createTable2Data(pageParam);
		Map<String, String > reamrkMap = createTable2Remark(pageParam);

		List<InvestmentBusinessDetailsDto> InvestmentBusinessDetailsList = new ArrayList<InvestmentBusinessDetailsDto>();
		for (String key : map.keySet()) {
			InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
			investmentBusinessDetailsDto.setPropertycode(key);
			// 本季末余额
			if (key.contains("cur")) {
				investmentBusinessDetailsDto.setTqeAmount(map.get(key));
				// 上季末数额
			} else if (key.contains("last")) {
				investmentBusinessDetailsDto.setTlqeAmount(map.get(key));
			}else if (key.contains("_zq")) {
				investmentBusinessDetailsDto.setSessionendNum(map.get(key));

			}else if (key.contains("_lzq")) {
				investmentBusinessDetailsDto.setLastSessionendNum(map.get(key));

			}
			InvestmentBusinessDetailsList.add(investmentBusinessDetailsDto);
		}
		for (String key : reamrkMap.keySet()) {
			InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
			investmentBusinessDetailsDto.setPropertycode(key);
			//备注
			if (key.contains("remark")) {
				investmentBusinessDetailsDto.setRemark(reamrkMap.get(key));
			}
			InvestmentBusinessDetailsList.add(investmentBusinessDetailsDto);
		}
		return InvestmentBusinessDetailsList;
	}

	/**  
	 * @Description 根据键值查询金额，为空返回 0   
	 * @Author      高健  
	 * @Date        2019年8月8日 上午9:46:01  
	 * @param @return 参数  
	 * @return BigDecimal 返回类型   
	 * @throws  
	 */  
    private BigDecimal queryMoney(Map<String, BigDecimal> map, String key) {
		BigDecimal dec = BigDecimal.ZERO;
		//计算方法改造.要求减法一定放在最后面
		String[] values = key.split("\\-");
		if(values.length>1){
			dec = map.get(values[0]) == null ? BigDecimal.ZERO : map.get(values[0]);
			for(int i = 1; i< values.length;i++){
				dec = dec.subtract(map.get(values[i]) == null ? BigDecimal.ZERO : map.get(values[i]));
			}
		}else{
			dec = map.get(key) == null ? BigDecimal.ZERO : map.get(key);
		}

		return dec;
	}
	
	public InvestmentBusinessDetailsDto divideBillion(InvestmentBusinessDetailsDto detail) {
		BigDecimal billion = new BigDecimal(10000);
		if(detail.getSessionendNum()!=null)//期末余额
			detail.setSessionendNum(detail.getSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		if(detail.getLastSessionendNum()!=null)//期末余额（穿透后）
			detail.setLastSessionendNum(detail.getLastSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		if(detail.getNonperformingAssetNum()!=null)//本期银行收益
			detail.setNonperformingAssetNum(detail.getNonperformingAssetNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		if(detail.getLastNonperformingAssetNum()!=null)//本期客户收益
			detail.setLastNonperformingAssetNum(detail.getLastNonperformingAssetNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		if(detail.getTqeAmount()!=null)//本期募集金额
			detail.setTqeAmount(detail.getTqeAmount().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		if(detail.getTlqeAmount()!=null)//本期总募集金额
			detail.setTlqeAmount(detail.getTlqeAmount().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		if (detail.getNonsStandardAmount() != null) { // 非标准化债权类投资本季末数额
			detail.setNonsStandardAmount(detail.getNonsStandardAmount().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastNonsStandardAmount() != null) { // 非标准化债权类投资上季末数额
			detail.setLastNonsStandardAmount(detail.getLastNonsStandardAmount().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getIndInvestmentAmount() != null) { // 产业投资基金上季末数额
			detail.setIndInvestmentAmount(detail.getIndInvestmentAmount().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastIndInvestmentAmount() != null) { // 产业投资基金上季末数额
			detail.setLastIndInvestmentAmount(detail.getLastIndInvestmentAmount().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getOtherAmount() != null) { // 其他本季末数额
			detail.setOtherAmount(detail.getOtherAmount().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastOtherAmount() != null) { // 其他上季末数额
			detail.setLastOtherAmount(detail.getLastOtherAmount().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getSessionendBalance() != null) { // 表四本季末数额余额
			detail.setSessionendBalance(detail.getSessionendBalance().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getSessionendYield() != null) { // 表四 本季末平均年化收益水平 收益率
			detail.setSessionendYield(detail.getSessionendYield().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastSessionendBalance() != null) { // 表四上季末数额余额
			detail.setLastSessionendBalance(detail.getLastSessionendBalance().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastSessionendYield() != null) { // 表四 上季末平均年化收益水平 收益率
			detail.setLastSessionendYield(detail.getLastSessionendYield().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getTotleBalance() != null) { // 表三总计本季末余额
			detail.setTotleBalance(detail.getTotleBalance().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLasttotleBalance() != null) { // 表三总计上季末余额
			detail.setLasttotleBalance(detail.getLasttotleBalance().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
		}

		//表五计算用 投资余额 -穿透前
		detail.setValueA(divideTenThousand(detail.getValueA()));
		//表五计算用 投资余额 -穿透后
		detail.setValueB(divideTenThousand(detail.getValueB()));
		//表五计算用 投资余额 -上季 -穿透前
		detail.setValueALast(divideTenThousand(detail.getValueALast()));
		//表五计算用 投资余额 -上季 -穿透后
		detail.setValueBLast(divideTenThousand(detail.getValueBLast()));
		//表五计算用 平均杠杆率 -穿透前
		detail.setAvgRate1(multiplyHundred(detail.getAvgRate1()));
		//表五计算用 平均杠杆率 -穿透后
		detail.setAvgRate2(multiplyHundred(detail.getAvgRate2()));
		//表五计算用 平均杠杆率 -穿透前 - 上季
		detail.setAvgRate1Last(multiplyHundred(detail.getAvgRate1Last()));
		//表五计算用 平均杠杆率 -穿透后 - 上季
		detail.setAvgRate2Last(multiplyHundred(detail.getAvgRate2Last()));
		//表五计算用 杠杆倍数最高值 -本机
		detail.setHeaverTimes1(multiplyHundred(detail.getHeaverTimes1()));
		//表五计算用 杠杆倍数最高值 -上季
		detail.setHeaverTimes2(multiplyHundred(detail.getHeaverTimes2()));
		//表五计算用 信贷资产支持证券
		detail.setValueB01(divideTenThousand(detail.getValueB01()));
		//表五计算用 信贷资产支持证券 - 上季
		detail.setValueBlast01(divideTenThousand(detail.getValueBlast01()));
		//表五计算用 资产支持专项计划
		detail.setValueB02(divideTenThousand(detail.getValueB02()));
		//表五计算用 资产支持专项计划 - 上季
		detail.setValueBlast02(divideTenThousand(detail.getValueBlast02()));

		return detail;
	}
	public static BigDecimal divideTenThousand(BigDecimal num){
    	BigDecimal beo = null;
		BigDecimal billion = new BigDecimal("10000");
		if(null!=num){
			beo = num.divide(billion, 2, BigDecimal.ROUND_HALF_UP);
		}
    	return beo;
	}



	public InvestmentBusinessDetailsDto divideBillionEx(InvestmentBusinessDetailsDto detail) {
		BigDecimal billion = BigDecimal.ONE;
		if(detail.getSessionendNum()!=null)//期末余额
			detail.setSessionendNum(detail.getSessionendNum().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		if(detail.getLastSessionendNum()!=null)//期末余额（穿透后）
			detail.setLastSessionendNum(detail.getLastSessionendNum().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		if(detail.getNonperformingAssetNum()!=null)//本期银行收益
			detail.setNonperformingAssetNum(detail.getNonperformingAssetNum().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		if(detail.getLastNonperformingAssetNum()!=null)//本期客户收益
			detail.setLastNonperformingAssetNum(detail.getLastNonperformingAssetNum().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		if(detail.getTqeAmount()!=null)//本期募集金额
			detail.setTqeAmount(detail.getTqeAmount().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		if(detail.getTlqeAmount()!=null)//本期总募集金额
			detail.setTlqeAmount(detail.getTlqeAmount().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		if (detail.getNonsStandardAmount() != null) { // 非标准化债权类投资本季末数额
			detail.setNonsStandardAmount(detail.getNonsStandardAmount().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastNonsStandardAmount() != null) { // 非标准化债权类投资上季末数额
			detail.setLastNonsStandardAmount(detail.getLastNonsStandardAmount().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getIndInvestmentAmount() != null) { // 产业投资基金上季末数额
			detail.setIndInvestmentAmount(detail.getIndInvestmentAmount().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastIndInvestmentAmount() != null) { // 产业投资基金上季末数额
			detail.setLastIndInvestmentAmount(detail.getLastIndInvestmentAmount().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getOtherAmount() != null) { // 其他本季末数额
			detail.setOtherAmount(detail.getOtherAmount().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastOtherAmount() != null) { // 其他上季末数额
			detail.setLastOtherAmount(detail.getLastOtherAmount().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getSessionendBalance() != null) { // 表四本季末数额余额
			detail.setSessionendBalance(detail.getSessionendBalance().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getSessionendYield() != null) { // 表四 本季末平均年化收益水平 收益率
			detail.setSessionendYield(detail.getSessionendYield().multiply(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastSessionendBalance() != null) { // 表四上季末数额余额
			detail.setLastSessionendBalance(detail.getLastSessionendBalance().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLastSessionendYield() != null) { // 表四 上季末平均年化收益水平 收益率
			detail.setLastSessionendYield(detail.getLastSessionendYield().multiply(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getTotleBalance() != null) { // 表三总计本季末余额
			detail.setTotleBalance(detail.getTotleBalance().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}
		if (detail.getLasttotleBalance() != null) { // 表三总计上季末余额
			detail.setLasttotleBalance(detail.getLasttotleBalance().divide(billion, 1, BigDecimal.ROUND_HALF_UP));
		}

		return detail;
	}

	/***
	 * 表5的结构化
	 * @param num
	 * @return
	 */
	public static BigDecimal multiplyHundred(BigDecimal num){
		BigDecimal beo = null;

		if(null!=num){
			beo = num.multiply(BigDecimal.ONE).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return beo;
	}

    public InvestmentBusinessCounterDetailsDto divideBillionOfCounter(InvestmentBusinessCounterDetailsDto detail) {
        BigDecimal billion = new BigDecimal(10000);
        if(detail.getXtSessionendNum()!=null){
            detail.setXtSessionendNum(detail.getXtSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getXtLastSessionendNum()!=null){
            detail.setXtLastSessionendNum(detail.getXtLastSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getXtZdSessionendNum()!=null){
            detail.setXtZdSessionendNum(detail.getXtZdSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getXtFbSessionendNum()!=null){
            detail.setXtFbSessionendNum(detail.getXtFbSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getZqqhSessionendNum()!=null){
            detail.setZqqhSessionendNum(detail.getZqqhSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getZqqhLastSessionendNum()!=null){
            detail.setZqqhLastSessionendNum(detail.getZqqhLastSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getZqqhZdSessionendNum()!=null){
            detail.setZqqhZdSessionendNum(detail.getZqqhZdSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getZqqhFbSessionendNum()!=null){
            detail.setZqqhFbSessionendNum(detail.getZqqhFbSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getZqjjSessionendNum()!=null){
            detail.setZqjjSessionendNum(detail.getZqjjSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getZqjjLastSessionendNum()!=null){
            detail.setZqjjLastSessionendNum(detail.getZqjjLastSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getZqjjZdSessionendNum()!=null){
            detail.setZqjjZdSessionendNum(detail.getZqjjZdSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getZqjjFbSessionendNum()!=null){
            detail.setZqjjFbSessionendNum(detail.getZqjjFbSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getYhSessionendNum()!=null){
            detail.setYhSessionendNum(detail.getYhSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getYhLastSessionendNum()!=null){
            detail.setYhLastSessionendNum(detail.getYhLastSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getYhZdSessionendNum()!=null){
            detail.setYhZdSessionendNum(detail.getYhZdSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getYhFbSessionendNum()!=null){
            detail.setYhFbSessionendNum(detail.getYhFbSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getBxSessionendNum()!=null){
            detail.setBxSessionendNum(detail.getBxSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getBxLastSessionendNum()!=null){
            detail.setBxLastSessionendNum(detail.getBxLastSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getBxZdSessionendNum()!=null){
            detail.setBxZdSessionendNum(detail.getBxZdSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        if(detail.getBxFbSessionendNum()!=null){
            detail.setBxFbSessionendNum(detail.getBxFbSessionendNum().divide(billion, 2, BigDecimal.ROUND_HALF_UP));
        }
        return detail;
    }


    /**
	 * @Description 查询表三剩余数据
	 * @param @param pageParam
	 * @param @return 参数
	 * @return List<InvestmentBusinessDetailsDto> 返回类型
	 * @throws
	 */
	private List<InvestmentBusinessDetailsDto> queryTable3OtherInfo(Map<String, Object> pageParam) {
		List<InvestmentBusinessDetailsDto> investmentBusinessDetailsDtoList = new ArrayList<InvestmentBusinessDetailsDto>();
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		String startDateStr = exportYear + "-" + exportQuarter + "-01";
		Date startDate=new Date();
		try {
			startDate = DateUtil.parseDate(startDateStr,"yyyy-MM-dd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MONTH, -3);
		Date lastDate=calendar.getTime();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		paraMap.put("startDate",startDate);
		paraMap.put("lastStartDate",lastDate);
		List<OverallInfoDto> overallInfoDtoList = overallInfoMapper.queryBottomIndustryOverallInfoList(paraMap);
		//表3备注
		String cdateStr = exportYear + exportQuarter+"01";
		Date cdate=new Date();
		try {
			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(cdate);
		calendar1.add(Calendar.MONTH, 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 0);
		//取到本季末
		Date endDate=calendar1.getTime();
		paraMap.put("startDate",startDate);
		paraMap.put("endDate",endDate);
		paraMap.put("svrischlbtm", CacheUtil.getSysParam("SVR_IS_CHL_BTM").toString());
		List<OverallInfoDto> overallInfoDtoRemarkList = overallInfoMapper.queryBottomRemarkInfoByIndustry(paraMap);

		// Map<行业分类:产品形式, 余额>
		Map<String, BigDecimal> porpertyAmoutMap = new HashMap<String, BigDecimal>(1 << 8);
		Map<String, String> resultRemarkMap = new HashMap<String, String>();

		for (OverallInfoDto overallInfoDto : overallInfoDtoList) {
			porpertyAmoutMap.put(overallInfoDto.getPropertyCode() + overallInfoDto.getReportType(), overallInfoDto.getBalance());
		}
		for (OverallInfoDto overallInfoDto : overallInfoDtoRemarkList) {
			resultRemarkMap.put(overallInfoDto.getPropertyCode() + overallInfoDto.getReportType(), overallInfoDto.getRemark());
		}
		for (int i = 1; i < 21; i ++) {
			String porperty = "thamt1_1_";
			String flag = "";
			if (i < 10) {
				flag = "0" + i;
			} else {
				flag =  i + "";
			}
			porperty += flag;
			InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
			investmentBusinessDetailsDto.setPropertycode(porperty);
			investmentBusinessDetailsDto.setSessionendNum(porpertyAmoutMap.get(flag + "BONDCUR") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(flag + "BONDCUR"));
			investmentBusinessDetailsDto.setLastSessionendNum(porpertyAmoutMap.get(flag + "BONDLAST") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(flag + "BONDLAST"));
			investmentBusinessDetailsDto.setNonsStandardAmount(porpertyAmoutMap.get(flag + "NOSTABONDCUR") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(flag + "NOSTABONDCUR"));
			investmentBusinessDetailsDto.setLastNonsStandardAmount(porpertyAmoutMap.get(flag + "NOSTABONDLAST") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(flag + "NOSTABONDLAST"));
			investmentBusinessDetailsDto.setOtherAmount(porpertyAmoutMap.get(flag + "OTHERCUR") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(flag + "OTHERCUR"));
			investmentBusinessDetailsDto.setLastOtherAmount(porpertyAmoutMap.get(flag + "OTHERLAST") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(flag + "OTHERLAST"));
			investmentBusinessDetailsDto.setRemark(resultRemarkMap.get(flag + "OTHERREMARK") == null ?"": resultRemarkMap.get(flag + "OTHERREMARK"));
			investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto);
		}
		//计算合计值
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto33=new InvestmentBusinessDetailsDto();
		investmentBusinessDetailsDto33.setPropertycode("t3_33_amt");
		for(InvestmentBusinessDetailsDto item:investmentBusinessDetailsDtoList){
			investmentBusinessDetailsDto33.setSessionendNum(addMoney(investmentBusinessDetailsDto33.getSessionendNum(),item.getSessionendNum()));
			investmentBusinessDetailsDto33.setLastSessionendNum(addMoney(investmentBusinessDetailsDto33.getLastSessionendNum(),item.getLastSessionendNum()));
			investmentBusinessDetailsDto33.setNonsStandardAmount(addMoney(investmentBusinessDetailsDto33.getNonsStandardAmount(),item.getNonsStandardAmount()));
			investmentBusinessDetailsDto33.setLastNonsStandardAmount(addMoney(investmentBusinessDetailsDto33.getLastNonsStandardAmount(),item.getLastNonsStandardAmount()));
			investmentBusinessDetailsDto33.setOtherAmount(addMoney(investmentBusinessDetailsDto33.getOtherAmount(),item.getOtherAmount()));
			investmentBusinessDetailsDto33.setLastOtherAmount(addMoney(investmentBusinessDetailsDto33.getLastOtherAmount(),item.getLastOtherAmount()));
		}
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto33);
		paraMap.put("startDate", startDate);
		paraMap.put("lastStartDate", lastDate);
		paraMap.put("endDate",endDate);
		paraMap.put("svrischlbtm", CacheUtil.getSysParam("SVR_IS_CHL_BTM").toString());
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto36= investmentBusinessDetailsMapper.queryBottomDiffAsset(paraMap);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto36);
		// 本季末参数
		paraMap.put("startDate", startDate);
		paraMap.put("lastStartDate", lastDate);
		paraMap.put("endDate",endDate);
		paraMap.put("modetype", "40");

		String[] deCode = CacheUtil.getSysParam("SVR_EXCESS_PRODUCTION_CAPACITY").split(";");

		paraMap.put("INDUSTRY1", deCode.length>=1?deCode[0]:"");
		paraMap.put("INDUSTRY2", deCode.length>=2?deCode[1]:"");
		paraMap.put("INDUSTRY3", deCode.length>=3?deCode[2]:"");
		paraMap.put("INDUSTRY4", deCode.length>=4?deCode[3]:"");


		InvestmentBusinessDetailsDto investmentBusinessDetailsDto40= investmentBusinessDetailsMapper.queryBottomInfoAmountByModeType(paraMap);
		paraMap.put("modetype", "41");
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto41= investmentBusinessDetailsMapper.queryBottomInfoAmountByModeType(paraMap);
		paraMap.put("modetype", "42");
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto42= investmentBusinessDetailsMapper.queryBottomInfoAmountByModeType(paraMap);
		paraMap.put("modetype", "43");
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto43= investmentBusinessDetailsMapper.queryBottomInfoAmountByModeType(paraMap);
		paraMap.put("modetype", "44");
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto44= investmentBusinessDetailsMapper.queryBottomInfoAmountByModeType(paraMap);
		paraMap.put("modetype", "45");
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto45= investmentBusinessDetailsMapper.queryBottomInfoAmountByModeType(paraMap);

		paraMap.remove("INDUSTRY1");
		paraMap.remove("INDUSTRY2");
		paraMap.remove("INDUSTRY3");
		paraMap.remove("INDUSTRY4");
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto40);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto41);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto42);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto43);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto44);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto45);
		for(InvestmentBusinessDetailsDto it:investmentBusinessDetailsDtoList){
			it.setTotleBalance(addAllMoney(it.getSessionendNum(),it.getNonsStandardAmount(),it.getIndInvestmentAmount(),it.getOtherAmount()));
			it.setLasttotleBalance(addAllMoney(it.getLastSessionendNum(),it.getLastNonsStandardAmount(),it.getLastIndInvestmentAmount(),it.getLastOtherAmount()));
		}
		//表三C15和E15的数据
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto15=investmentBusinessDetailsMapper.queryBottomInfoAmountFjr(paraMap);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto15);
		//表３附表合计
		InvestmentBusinessDetailsDto investmentBusinessDetailsDtot3AddSum= investmentBusinessDetailsMapper.queryBottomTable3AddSum(paraMap);
		investmentBusinessDetailsDtot3AddSum.setTotleBalance(investmentBusinessDetailsDtot3AddSum.getSessionendNum()
				.add(investmentBusinessDetailsDtot3AddSum.getNonsStandardAmount())
				.add(investmentBusinessDetailsDtot3AddSum.getIndInvestmentAmount())
				.add(investmentBusinessDetailsDtot3AddSum.getOtherAmount())
		);
		investmentBusinessDetailsDtot3AddSum.setLasttotleBalance(investmentBusinessDetailsDtot3AddSum.getLastSessionendNum()
				.add(investmentBusinessDetailsDtot3AddSum.getLastNonsStandardAmount())
				.add(investmentBusinessDetailsDtot3AddSum.getLastIndInvestmentAmount())
				.add(investmentBusinessDetailsDtot3AddSum.getLastOtherAmount())
		);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDtot3AddSum);


		return investmentBusinessDetailsDtoList;
	}
	private List<InvestmentBusinessDetailsDto> queryTable3AddList(Map<String, Object> pageParam) {
		List<InvestmentBusinessDetailsDto> investmentBusinessDetailsDtoList = new ArrayList<InvestmentBusinessDetailsDto>();
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");
		String startDateStr = exportYear + "-" + exportQuarter + "-01";
		Date startDate=new Date();
		try {
			startDate = DateUtil.parseDate(startDateStr,"yyyy-MM-dd");
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MONTH, -3);
		Date lastDate=calendar.getTime();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(MultilevelDataAuthorityUtil.isEnabled()) {
			//联社模式,查询登录人的机构
			String org =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
			paraMap.put("org",org);
		}
		// 本季末参数
		paraMap.put("startDate", startDate);
		paraMap.put("lastStartDate", lastDate);
		paraMap.put("rule",(String) pageParam.get("rule"));

		List<OverallInfoDto> overallInfoDtoList = overallInfoMapper.queryBottomTable3AddInfoList(paraMap);
		// Map<行业分类:产品形式, 余额>
		Map<String, BigDecimal> porpertyAmoutMap = new HashMap<String, BigDecimal>(1 << 8);
		for (OverallInfoDto overallInfoDto : overallInfoDtoList) {
			porpertyAmoutMap.put(overallInfoDto.getPropertyCode() + overallInfoDto.getReportType(), overallInfoDto.getBalance());
		}
		for (OverallInfoDto overallInfoDto : overallInfoDtoList) {
			String porperty =overallInfoDto.getPropertyCode();
			boolean isExist=false;
		    if(StringUtil.isNotEmpty(porperty)){
				for(InvestmentBusinessDetailsDto item:investmentBusinessDetailsDtoList){
					if(porperty.equals(item.getPropertycode())){
						isExist=true;
						break;
					}
				}
			}
		    if(!isExist){
				InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
				investmentBusinessDetailsDto.setPropertycode(porperty);
				investmentBusinessDetailsDto.setSessionendNum(porpertyAmoutMap.get(porperty + "BONDCUR") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(porperty + "BONDCUR"));
				investmentBusinessDetailsDto.setLastSessionendNum(porpertyAmoutMap.get(porperty + "BONDLAST") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(porperty + "BONDLAST"));
				investmentBusinessDetailsDto.setNonsStandardAmount(porpertyAmoutMap.get(porperty + "NOSTABONDCUR") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(porperty + "NOSTABONDCUR"));
				investmentBusinessDetailsDto.setLastNonsStandardAmount(porpertyAmoutMap.get(porperty + "NOSTABONDLAST") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(porperty + "NOSTABONDLAST"));
				investmentBusinessDetailsDto.setIndInvestmentAmount(porpertyAmoutMap.get(porperty + "INDINCUR") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(porperty + "INDINCUR"));
				investmentBusinessDetailsDto.setLastIndInvestmentAmount(porpertyAmoutMap.get(porperty + "INDINLAST") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(porperty + "INDINLAST"));
				investmentBusinessDetailsDto.setOtherAmount(porpertyAmoutMap.get(porperty + "OTHERCUR") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(porperty + "OTHERCUR"));
				investmentBusinessDetailsDto.setLastOtherAmount(porpertyAmoutMap.get(porperty + "OTHERLAST") == null ? BigDecimal.ZERO : porpertyAmoutMap.get(porperty + "OTHERLAST"));
				investmentBusinessDetailsDto.setTotleBalance(investmentBusinessDetailsDto.getSessionendNum()
						.add(investmentBusinessDetailsDto.getNonsStandardAmount())
						.add(investmentBusinessDetailsDto.getIndInvestmentAmount())
						.add(investmentBusinessDetailsDto.getOtherAmount())
				);
				investmentBusinessDetailsDto.setLasttotleBalance(investmentBusinessDetailsDto.getLastSessionendNum()
						.add(investmentBusinessDetailsDto.getLastNonsStandardAmount())
						.add(investmentBusinessDetailsDto.getLastIndInvestmentAmount())
						.add(investmentBusinessDetailsDto.getLastOtherAmount())
				);
				divideBillion(investmentBusinessDetailsDto);
				investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDto);
			}
		}
		//小计
		InvestmentBusinessDetailsDto investmentBusinessDetailsDtoxj= investmentBusinessDetailsMapper.queryBottomTable3AddXj(paraMap);
		investmentBusinessDetailsDtoxj.setTotleBalance(investmentBusinessDetailsDtoxj.getSessionendNum()
				.add(investmentBusinessDetailsDtoxj.getNonsStandardAmount())
				.add(investmentBusinessDetailsDtoxj.getIndInvestmentAmount())
				.add(investmentBusinessDetailsDtoxj.getOtherAmount())
		);
		investmentBusinessDetailsDtoxj.setLasttotleBalance(investmentBusinessDetailsDtoxj.getLastSessionendNum()
				.add(investmentBusinessDetailsDtoxj.getLastNonsStandardAmount())
				.add(investmentBusinessDetailsDtoxj.getLastIndInvestmentAmount())
				.add(investmentBusinessDetailsDtoxj.getLastOtherAmount())
		);
		divideBillion(investmentBusinessDetailsDtoxj);
		investmentBusinessDetailsDtoList.add(investmentBusinessDetailsDtoxj);
		return investmentBusinessDetailsDtoList;
	}

	/**  
	 * @Description 正对propertycode相同的实体进行金额的合并  
	 * @Author      高健  
	 * @Date        2019年8月10日 上午11:48:16  
	 * @param @param dto1
	 * @param @param dto2
	 * @param @return 参数  
	 * @return InvestmentBusinessDetailsDto 返回类型   
	 * @throws  
	 */  
	private InvestmentBusinessDetailsDto mergeSameDto(InvestmentBusinessDetailsDto dto1, InvestmentBusinessDetailsDto dto2) {
		InvestmentBusinessDetailsDto investmentBusinessDetailsDto = new InvestmentBusinessDetailsDto();
		investmentBusinessDetailsDto.setPropertycode(dto1.getPropertycode());
		investmentBusinessDetailsDto.setTqeAmount(addMoneySum(dto1.getTqeAmount(), dto2.getTqeAmount()));
		investmentBusinessDetailsDto.setTlqeAmount(addMoneySum(dto1.getTlqeAmount(), dto2.getTlqeAmount()));
		investmentBusinessDetailsDto.setSessionendNum(addMoneySum(dto1.getSessionendNum(), dto2.getSessionendNum()));
		investmentBusinessDetailsDto.setNonperformingAssetNum(addMoneySum(dto1.getNonperformingAssetNum(), dto2.getNonperformingAssetNum()));
		investmentBusinessDetailsDto.setLastSessionendNum(addMoneySum(dto1.getLastSessionendNum(), dto2.getLastSessionendNum()));
		investmentBusinessDetailsDto.setLastNonperformingAssetNum(addMoneySum(dto1.getLastNonperformingAssetNum(), dto2.getLastNonperformingAssetNum()));
		investmentBusinessDetailsDto.setNonsStandardAmount(addMoneySum(dto1.getNonsStandardAmount(), dto2.getNonsStandardAmount()));
		investmentBusinessDetailsDto.setLastNonsStandardAmount(addMoneySum(dto1.getLastNonsStandardAmount(), dto2.getLastNonsStandardAmount()));
		investmentBusinessDetailsDto.setIndInvestmentAmount(addMoneySum(dto1.getIndInvestmentAmount(), dto2.getIndInvestmentAmount()));
		investmentBusinessDetailsDto.setLastIndInvestmentAmount(addMoneySum(dto1.getLastIndInvestmentAmount(), dto2.getLastIndInvestmentAmount()));
		investmentBusinessDetailsDto.setOtherAmount(addMoneySum(dto1.getOtherAmount(), dto2.getOtherAmount()));
		investmentBusinessDetailsDto.setLastOtherAmount(addMoneySum(dto1.getLastOtherAmount(), dto2.getLastOtherAmount()));
		return investmentBusinessDetailsDto;
	}
	
	/**  
	 * @Description 相同属性相对应金额相加  （第一个值需要乘以 100000000）
	 * @Author      高健  
	 * @Date        2019年8月10日 下午1:34:27  
	 * @param @param num1 
	 * @param @param num2
	 * @param @return 参数  
	 * @return BigDecimal 返回类型   
	 * @throws  
	 */  
	private BigDecimal addMoney(BigDecimal num1, BigDecimal num2) {
		//BigDecimal billion = new BigDecimal(100000000);
		if (null == num1) {
			num1 = BigDecimal.ZERO;
		}
		if (null == num2) {
			num2 = BigDecimal.ZERO;
		}
		//return num1.multiply(billion).add(num2);
		return num1.add(num2);
	}
	private BigDecimal addMoneySum(BigDecimal num1, BigDecimal num2) {
		BigDecimal billion = new BigDecimal(10000);
		if (null == num1) {
			num1 = BigDecimal.ZERO;
		}
		if (null == num2) {
			num2 = BigDecimal.ZERO;
		}
		return num1.multiply(billion).add(num2);
	}
	private BigDecimal addAllMoney(BigDecimal num1, BigDecimal num2, BigDecimal num3, BigDecimal num4) {
		if (null == num1) {
			num1 = BigDecimal.ZERO;
		}
		if (null == num2) {
			num2 = BigDecimal.ZERO;
		}
		if (null == num3) {
			num3 = BigDecimal.ZERO;
		}
		if (null == num4) {
			num4 = BigDecimal.ZERO;
		}
		return num1.add(num2).add(num3).add(num4);
	}
	
 }
