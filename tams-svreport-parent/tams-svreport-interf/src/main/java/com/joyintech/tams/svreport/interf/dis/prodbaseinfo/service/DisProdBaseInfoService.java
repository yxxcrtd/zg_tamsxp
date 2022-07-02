/*
 * 文件名：ProdBaseInfoService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：信息披露系统V1.0
 * 修改人：ZhangYangFei
 * 修改时间：2019年5月6日
 * 修改内容：新建
 * 系统名称：信息披露系统V1.0
 */
 
package com.joyintech.tams.svreport.interf.dis.prodbaseinfo.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.common.base.dao.ChbPrdresistCodeMapper;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.base.entity.ChbPrdresistCodeKey;
import com.joyintech.tams.svreport.interf.dis.common.DisInterfDecimalUtil;
import com.joyintech.tams.svreport.interf.dis.prodbaseinfo.dao.DisRepProdInfoMapper;
import com.joyintech.tams.svreport.interf.dis.prodbaseinfo.dto.DisRepProdInfo;


/**
 * 产品基本信息接口
 *      解析产品基本信息接口数据，存入REP_PROD_INFO
 * @author ZhangYangFei
 * @version 1.0 2019年5月6日
 * @see ProdBaseInfoService
 * @since 1.0
 */
@Service
public class DisProdBaseInfoService extends FileImpService {
    
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 长sql最大一次插入或者更新条数
     */
    @Value("${application.reportInsertLongCount}")
    public int reportInsertLongCount;
    
    /**
     * 业务主键
     */
    private static final String BUSINESSKEY_DIS_PRD_BASEINFO = "PROD_ID";
    
    @Autowired
    private DisRepProdInfoMapper disRepProdInfoMapper;
    
    @Autowired
    private ChbPrdresistCodeMapper chbPrdresistCodeMapper;
    /**
     * {@inheritDoc}
     */
    @Override
    public String saveData(List<Map<String, String>> listData, String saveFlag,String dateSource) {
        if (StringUtil.isEmptyList(listData)) {
            logger.info("saveData方法参数listData为空。");
            return "success";
        }
        // 解析文件数据到dto
        DisRepProdInfo dto = null;
        List<DisRepProdInfo> dtoList = new ArrayList<DisRepProdInfo>();
        for(Map<String, String> data : listData){
            //将map转换为dtosvrProdInfoInterfTaskService
        	try{
        		   dto = transMap2Dto(data);
                   dtoList.add(dto);
        	}catch(Exception e){
				logger.error("解析数据错误：",e.getMessage(),e);
           		return  "fail";
			}
         
        }
        //存表
        int success = this.mergeRepProdInfoByPart(dtoList);
        
        logger.info("saveData方法成功存入数据到REP_PROD_INFO：" + success + "条");
        return "success";
    }

    /**
     * 将map转换为dto
     * 
     * @param data
     * @return
     * @see
     * @since 1.0
     */
    private DisRepProdInfo transMap2Dto(Map<String, String> data) {
        DisRepProdInfo dto = new DisRepProdInfo();
        dto.setProdId(data.get("PROD_ID"));//产品代码
		if (StringUtil.isEmpty(data.get("REG_CODE"))) {
			// 根据产品代码去chb_prdresist_code表查询REG_CODE并赋值给产品基本信息
			ChbPrdresistCodeKey key = new ChbPrdresistCodeKey();
			key.setProdid(dto.getProdId());
			key.setFlag("2");
			ChbPrdresistCode chbPrdresistCode = chbPrdresistCodeMapper.selectByPrimaryKey(key);
			dto.setProdRe(chbPrdresistCode == null ? "" : chbPrdresistCode.getRegCode());// 产品登记编码
		} else {
			dto.setProdRe(data.get("REG_CODE"));// 产品登记编码
		}
        dto.setProdName(data.get("PROD_NAME"));//产品名称
        dto.setProdAbbr(data.get("PROD_ABBR"));//产品简称
        dto.setRaiseMin(data.get("RAISE_MIN"));//募集下限
        dto.setRaiseMax(data.get("RAISE_MAX"));//募集上限
        dto.setRaiseStrdate(DateUtil.parse(data.get("RAISE_VDATE")));//募集开始日
        dto.setRaiseEnddate(DateUtil.parse(data.get("RAISE_ENDDATE")));//募集结束日
        dto.setRaiseCcy(data.get("RAISE_CCY"));//募集币种
        dto.setSetDate(DateUtil.parse(data.get("VDATE")));//产品成立日
        dto.setVdate(DateUtil.parse(data.get("VDATE")));//产品起息日
        dto.setFirstOpenDay(DateUtil.parse(data.get("FIRST_OPEN_DAY")));//首次开放日
        dto.setMdate(DateUtil.parse(data.get("MDATE")));//产品到期日
        dto.setActMdate(DateUtil.parse(data.get("ACT_MDATE")));//实际到期日
        dto.setTerm(data.get("TERM"));//产品期限
        dto.setWindupDate(DateUtil.parse(data.get("WINDUP_DATE")));//产品清盘日
        
        // 发行方式
		if (!StringUtil.isEmpty(data.get("OPERATE_MODE"))) {
			if (StringUtil.equals(data.get("OPERATE_MODE"), DictCodeListExt2.产品运作模式_封闭式净值型.getValue())
					|| StringUtil.equals(data.get("OPERATE_MODE"), DictCodeListExt2.产品运作模式_封闭式非净值型.getValue())) {
				dto.setIssueMode(DictCodeList.开放类型_封闭式.getValue());
			} else if (StringUtil.equals(data.get("OPERATE_MODE"), DictCodeListExt2.产品运作模式_开放式净值型.getValue())
					|| StringUtil.equals(data.get("OPERATE_MODE"), DictCodeListExt2.产品运作模式_开放式非净值型.getValue())) {
				dto.setIssueMode(DictCodeList.开放类型_开放式.getValue());
			}
		}
        
        dto.setProfitType(data.get("PROFIT_TYPE"));//收益类型
        
		// N 净值/M 货币/R 利率
		// 01 净值型/02 预期收益型/03 货币型
        if (StringUtil.equals(data.get("PRODTYPE"), "N")) {
        	dto.setProdType("01");//产品类型
        }else if (StringUtil.equals(data.get("PRODTYPE"), "M")) {
        	dto.setProdType("03");//产品类型
        }else if (StringUtil.equals(data.get("PRODTYPE"), "R")) {
        	dto.setProdType("02");//产品类型
        }
        
        dto.setIssueStatus(data.get("ISSUING_STATUS"));//发行状态
        dto.setRaiseType(data.get("RAISE_TYPE"));//募集方式
        dto.setRiskLevel(data.get("RISK_LEVEL"));//产品风险等级
        dto.setRaiseAmt(DisInterfDecimalUtil.str2BigDecimal(data.get("RAISE_AMT_PLAN")));//募集金额
        dto.setOpenMode(data.get("OPEN_MODE"));//开放模式
        dto.setInvestProfDate(data.get("INVEST_PROF_DATE"));//投资收益到账日
        dto.setInvestPrinDate(data.get("INVEST_PRIN_DATE"));//投资本金到账日
        dto.setIsIndepeAcc(data.get("IS_INDEPE_ACC"));//是否独立开户
        dto.setProdInvestType(data.get("IVSTORIENTATION"));//投资范围/投资性质
        dto.setRevealChannel(data.get("REVEAL_CHANNEL"));//披露渠道
        dto.setDealCal(data.get("DEAL_CAL"));//交易日历
        dto.setFinaType(data.get("FINA_TYPE"));//理财类型
        dto.setTruAccount(data.get("TRU_ACCOUNT"));//托管人账号
        dto.setAcctChnname(data.get("ACCT_CHNNAME"));//托管人名称
        dto.setTruFeeRage(ArithmeticUtil.divide(DisInterfDecimalUtil.str2BigDecimal(data.get("TRU_FEE_RATE")), new BigDecimal("100"), 6));//托管费率，例如：5%，填写5，需要转换为小数
        dto.setTruAccountNum("");//托管账户账号//暂时不取，没有用到
        dto.setTruAccountName("");//托管账户户名//暂时不取，没有用到
        dto.setProdTermType(data.get("TERMTYPE"));//产品期限类型
        dto.setBasis(data.get("BASIC"));//计息基础
        dto.setNatureInvest(data.get("IVSTORIENTATION"));//投资性质
        dto.setTargetSale(data.get("TARGET_SALE"));//销售对象
        dto.setIsLayer(data.get("IS_LAYER"));//是否分层
        dto.setPerformCompare(ArithmeticUtil.divide(DisInterfDecimalUtil.str2BigDecimal(data.get("YIELD")), new BigDecimal("100"), 6)+"");//业绩比较基准,例如：5%，该字段为5，需要转换为小数
        dto.setProManager(data.get("MNAME"));//管理人
        dto.setProCustodian(data.get("ACCT_CHNNAME"));//托管人名称
        dto.setHugeRedeemThreshold(DisInterfDecimalUtil.str2BigDecimal(data.get("HUGE_REDEEM_THRESHOLD")));//巨额赎回阀值
        dto.setSaleAmtStr(DisInterfDecimalUtil.str2BigDecimal(data.get("SALE_AMT_STR")));//起点销售金额
        dto.setOperateMode(data.get("OPERATE_MODE"));//产品运作模式
        dto.setfProdId(data.get("PROD_ID"));//源系统金融产品代码
        
        dto.setStatus(data.get("STATUS"));
        dto.setCreateUser("interf");
        dto.setCreateDept("");
        dto.setUpdateUser("interf");

        /**add by yinya 新增产品基本信息：分红方式、最少追加金额 on 20211014 start**/
        dto.setBonusMode(data.get("BONUS_MODE"));
        if (StringUtil.isNotEmpty(data.get("LOWEST_AMT"))) {
            dto.setLowestAmt(DisInterfDecimalUtil.str2BigDecimal(data.get("LOWEST_AMT")));//最少追加金额
        }
        /**add by yinya 新增产品基本信息：分红方式、最少追加金额 on 20211014 end**/
        try{
            if(StringUtil.isNotEmpty(data.get("CREATE_TIME"))){
                dto.setCreateTime(DateUtil.parseDate(data.get("CREATE_TIME"),new String[] {DateUtil.DEFAULT_DATETIME_FORMAT_ODS}));
            }else{
                dto.setCreateTime(new Date());
            }
            if(StringUtil.isNotEmpty(data.get("UPDATE_TIME"))){
                    dto.setUpdateTime(DateUtil.parseDate(data.get("UPDATE_TIME"),new String[] {DateUtil.DEFAULT_DATETIME_FORMAT_ODS}));
            }else{
                dto.setUpdateTime(new Date());
            }
        } catch (ParseException e) {
            logger.error("transMap2Dto方法转换CreateTime或UpdateTime异常");
            logger.error(e.getMessage(),e);
        }
        return dto;
       }

    /**
     * 分批存表
     * 
     * @param dtoList
     * @return
     * @see
     * @since 1.0
     */
    private int mergeRepProdInfoByPart(List<DisRepProdInfo> dtoList) {
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertLongCount;
        //插入list大小
        int dataCount = dtoList.size();
        //若需要插入的数据比最大插入的数据少，则直接全部插入
        if (dataCount <= maxDataCountOneTime) {
            disRepProdInfoMapper.mergeRepProdInfo(dtoList);
            return dataCount;
        }
        
        List<DisRepProdInfo> newDataList = new ArrayList<DisRepProdInfo>();
        newDataList.addAll(dtoList);
        
        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            disRepProdInfoMapper.mergeRepProdInfo(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            disRepProdInfoMapper.mergeRepProdInfo(newDataList);
        }
        return dataCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
        if(CollectionUtils.isEmpty(listData)){
            return "success";
        }
        // 数据检查
        checkData(listData, interfFile.getFilename(), InterfDefine.产品基本信息接口数据.getCode(),
            InterfDefine.数据检查类型_产品基本信息接口.getCode(), BUSINESSKEY_DIS_PRD_BASEINFO,
            BUSINESSKEY_DIS_PRD_BASEINFO);
        ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(
            InterfDefine.产品基本信息接口数据.getCode());
        if (interfConfig == null) {
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        return saveData(listData, saveFlag, interfFile.getDataSource());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String importDB(List<ExcelValidDto> listExcel,List<Map<String, String>> listData, InterfFile interfFile, String date) {
        if(CollectionUtils.isEmpty(listData)){
            return "success";
        }
        // 数据检查
        checkData(listData, interfFile.getFilename(), InterfDefine.产品基本信息接口数据.getCode(),
                InterfDefine.数据检查类型_产品基本信息接口.getCode(), BUSINESSKEY_DIS_PRD_BASEINFO,
                BUSINESSKEY_DIS_PRD_BASEINFO);
        ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(
                InterfDefine.产品基本信息接口数据.getCode());
        if (interfConfig == null) {
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        return saveData(listData, saveFlag, interfFile.getDataSource());
    }
}
