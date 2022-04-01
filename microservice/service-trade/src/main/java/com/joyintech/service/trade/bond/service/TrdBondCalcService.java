/*
 * 文件名：TrdBondInvestTrade001Service.java 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved. 描述：理财资管管理系统V2.0 修改人：Zane.Y 修改时间：2018年9月19日
 * 修改内容：新建 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.service.trade.bond.service;

import static com.joyintech.tams.invest.common.asset.service.BondAssetCalcService.toLocalDate;
import static com.joyintech.tams.invest.common.asset.service.BondAssetCalcService.toSecInfoModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.calculation.CalculatorFactory;
import com.joyintech.calculation.bonds.PaperIrReset;
import com.joyintech.calculation.bonds.SecIntSchedule;
import com.joyintech.calculation.bonds.SecPrinSchedule;
import com.joyintech.calculation.bonds.constant.Basis;
import com.joyintech.calculation.bonds.constant.CurrIntCalMethod;
import com.joyintech.calculation.bonds.constant.DiscountScheme;
import com.joyintech.calculation.bonds.constant.FlowCashScheme;
import com.joyintech.calculation.bonds.constant.IntRateType;
import com.joyintech.calculation.bonds.constant.NextIntCalMethod;
import com.joyintech.calculation.bonds.constant.Variety;
import com.joyintech.calculation.shell.AccurIntCalculator;
import com.joyintech.calculation.shell.AccurIntForSecheduleCalculator;
import com.joyintech.calculation.shell.PriceByYieldCalculator;
import com.joyintech.calculation.shell.YieldByPriceCalculator;
import com.joyintech.calculation.shell.param.AccurIntCalInParameter;
import com.joyintech.calculation.shell.param.AccurIntCalOutParameter;
import com.joyintech.calculation.shell.param.PriceYieldInParameter;
import com.joyintech.calculation.shell.param.PriceYieldOutParameter;
import com.joyintech.calculation.shell.param.SecInfoModel;
import com.joyintech.calculation.shell.param.YieldPriceInParameter;
import com.joyintech.calculation.shell.param.YieldPriceOutParameter;
import com.joyintech.calculation.util.BasicUtil;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.service.trade.bond.dto.TrdBondInvestTradeDto;
import com.joyintech.tams.common.base.entity.FinCashCalcRule;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.trade.dto.FinCashPlanDto;
import com.joyintech.tams.invest.common.trade.dto.SecInfoModelDto;

/**
 * 债券计算
 *
 * @author FUXJ
 * @version 1.0 2018年11月29日
 * @see TrdBondCalcService
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
@Service
public class TrdBondCalcService extends BaseService{
	
	@Autowired
    private TrdBondInvestTradeService trdBondInvestTradeService;
	
	/**
     * 1.7计算按钮点击事件
     *
     * @param inputDto 画面参数
     * @return 计算结果
     * @see
     * @since 1.0
     */
    public AjaxResult calculateData(TrdBondInvestTradeDto inputDto) {
    	 AjaxResult ajaxResult = new AjaxResult();
    	// 校验输入参数
         List<ValidMessage> errorList = this.validCalcAmt(inputDto);
         if (!StringUtil.isEmptyList(errorList)) {
             ajaxResult.setErrorList(errorList);
             return ajaxResult;
         }

         // 计算逻辑
         this.calcAmt(inputDto);
         ajaxResult.setData(inputDto);
    	 return ajaxResult;
    }
    /**
     * 1.7计算按钮点击事件 检查输入参数是否合法
     *
     * @param dto 画面输入参数
     * @return 检查结果
     * @see
     * @since 1.0
     */
    private List<ValidMessage> validCalcAmt(TrdBondInvestTradeDto dto) {
        List<ValidMessage> validMessageList = new ArrayList<ValidMessage>();
        // 1.交割日
        if (dto.getSettleDate() == null) {
            ValidMessage validMessage = new ValidMessage("settleDate","交割日期不能为空");
            validMessageList.add(validMessage);
        }
        // 2.资产代码
        if (StringUtil.isBlank(dto.getFinprodId())) {
        	 ValidMessage validMessage = new ValidMessage("finprodId","资产代码不能为空");
            validMessageList.add(validMessage);
        }
        // 投资目的
//        if (StringUtil.isBlank(dto.getInvAim())) {
//        	ValidMessage validMessage = new ValidMessage("invAim","投资目的不能为空");
//            validMessageList.add(validMessage);
//        }
        // 3.劵面总额
        if (dto.getTradeAmt() == null) {
        	ValidMessage validMessage = new ValidMessage("tradeAmt","劵面总额不能为空");
            validMessageList.add(validMessage);
        }

        if (StringUtil.isBlank(dto.getCalculateField())) {
        	ValidMessage validMessage = new ValidMessage("calculateField","计算类型不能为空");
            validMessageList.add(validMessage);
        }
        else {
            switch (dto.getCalculateField()) {
            case "unitCprice":
                if (dto.getUnitCprice() == null) {
                	ValidMessage validMessage = new ValidMessage("unitCprice","净价不能为空");
                    validMessageList.add(validMessage);
                }
                break;
            case "fpriceAmt":
                if (dto.getFpriceAmt() == null) {
                	ValidMessage validMessage = new ValidMessage("fpriceAmt","全价不能为空");
                    validMessageList.add(validMessage);
                }
                break;
            case "yield":
                if (dto.getYield() == null) {
                	ValidMessage validMessage = new ValidMessage("yield","到期收益率为空");
                    validMessageList.add(validMessage);
                }
                break;
            case "exerYield":
                if (dto.getExerYield() == null) {
                    ValidMessage validMessage = new ValidMessage("exerYield","行权收益率为空");
                    validMessageList.add(validMessage);
                }
                    // 根据行权收益率反算其他值的情况，若下一行权日净价未维护，则提示错误消息
                    else {
                        try {
                            Map<String, Object> tempParamMap = new HashMap<String, Object>();
                            tempParamMap.put("finprodId", dto.getFinprodId());
                            tempParamMap.put("ps", dto.getPs());
                            // d.获取债券基本资料信息
                            SecInfoModelDto secInfoModelDto = trdBondInvestTradeService.getSecInfoModel(tempParamMap);
                            if (secInfoModelDto != null && secInfoModelDto.getNextExeDate() != null
                                && secInfoModelDto.getNextExercisePrice() == null) {
                            	ValidMessage validMessage = new ValidMessage("exerYield","行权收益率为空");
                                validMessageList.add(validMessage);
                            }
                        }
                        catch (Exception e) {
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        // 债券到期日<=交割日时
        if (StringUtil.isNotEmpty(dto.getSettleDate()) && StringUtil.isNotEmpty(dto.getMdate())) {
            if (DateUtil.compareDate(dto.getMdate(), dto.getSettleDate()) <= 0) {
                validMessageList.add(new ValidMessage("settleDate", "交割日期必须小于资产到期日"));
            }
        }
        // 画面.交割日<债券起息日
		if (StringUtil.equals(dto.getTradeMarketMode(), DictCodeListExt1.交易市场_二级市场.getValue())
				&& StringUtil.isNotEmpty(dto.getVdate()) && StringUtil.isNotEmpty(dto.getSettleDate())) {
			if (DateUtil.compareDate(dto.getSettleDate(), dto.getVdate()) < 0) {
				validMessageList.add(new ValidMessage("settleDate", "交割日必须大于债券起息日"));
			}
		}
        return validMessageList;
    }
    
    private void calcAmt(TrdBondInvestTradeDto dto) {
        // 先计算融入券
        if (dto.getIntegrateTradeAmt() != null && dto.getIntegrateTradeAmt().compareTo(BigDecimal.ZERO) > 0) {
            dto.setPrinAmt(dto.getIntegrateTradeAmt());
            calc(dto, true);
        }
        else {
            dto.setIntegrateIntAmt(BigDecimal.ZERO);
            dto.setIntegrateCpriceAmt(BigDecimal.ZERO);
            dto.setIntegrateFpriceAmt(BigDecimal.ZERO);
        }
        dto.setPrinAmt(dto.getTradeAmt());
        calc(dto, false);
    }
    

    private void calc(TrdBondInvestTradeDto dto, boolean 计算融入券) {
        if (DateUtil.compareDate(dto.getSettleDate(), dto.getVdate()) < 0) {
            dto.setSettleDate(dto.getVdate());
        }

        // 如果交易场所为上交所、深交所以外的情况
        boolean isJiaoYiSuo = true;
        if (!StringUtil.equals(DictCodeListExt1.交易场所_上海证券交易所.getValue(), dto.getTradeMarket())
            && !StringUtil.equals(DictCodeListExt1.交易场所_深圳证券交易所.getValue(), dto.getTradeMarket())) {
            // // 应计利息根据后台估值核算中前一个自然日的数据计算，等于后台的应计利息总额/后台的债券持仓总数量
            // this.getUnitIntFromValution(dto);
            isJiaoYiSuo = false;
        }

        final int SCALE = 12;

        dto.setYield(ArithmeticUtil.divide(dto.getYield(), 100, 14));
        dto.setExerYield(ArithmeticUtil.divide(dto.getExerYield(), 100, 14));

        YieldByPriceCalculator yieldByPriceCalculator = new YieldByPriceCalculator();
        PriceByYieldCalculator priceByYieldCalculator = new PriceByYieldCalculator();
        YieldPriceInParameter yieldPriceInParameter = null;
        PriceYieldInParameter priceYieldInParameter = null;
        YieldPriceOutParameter yieldPriceOutParameter = null;
        PriceYieldOutParameter priceYieldOutParameter = null;

     // b.获取计息区间投资端利率报价信息（债券的利率重置信息）
        List<FinCashCalcRule> finCashCalcRuleList = trdBondInvestTradeService.selectBondsIntResetList(dto.getFinprodId());

        // c.获取债券的付息计划信息
        List<FinCashPlanDto> finCashPlanList = trdBondInvestTradeService.getFinCashPlan(dto.getFinprodId());
        
        
        List<SecIntSchedule> invScheduleList = new ArrayList<>();
        List<FinCashPlanDto> intSchList = new ArrayList<FinCashPlanDto>();
        for (FinCashPlanDto item : finCashPlanList) {
            if (StringUtil.equals(item.getCashType1(), DictCodeListExt1.现金流一级类型_利息.getValue())
                && item.getVdateUnadjust() != null) {
                SecIntSchedule schedule = new SecIntSchedule();
                schedule.setTheoryStartDate(toLocalDate(item.getVdateUnadjust()));
                schedule.setTheoryCutoffDate(toLocalDate(item.getMdateUnadjust()));
                schedule.setActualStartDate(toLocalDate(item.getVdate()));
                schedule.setActualCutoffDate(toLocalDate(item.getMdate()));
                schedule.setAmount(item.getIntAmt());
                invScheduleList.add(schedule);
                intSchList.add(item);
            }
        }

        // d.获取债券基本资料信息
        // 计算行权收益率，非行权时，取大于当日的行权日
        Map<String, Object> tempParamMap = new HashMap<String, Object>();
        tempParamMap.put("finprodId", dto.getFinprodId());
        tempParamMap.put("ps", dto.getPs());
        SecInfoModelDto secInfoModelDto = trdBondInvestTradeService.getSecInfoModel(tempParamMap);
 
        SecInfoModel secInfoModel = toSecInfoModel(secInfoModelDto);
        // 如果交易场所为上交所、深交所的情况
        if (isJiaoYiSuo) {
            // 付息债的计息基础使用A_365F
            if (StringUtil.equals(DictCodeList.息票品种_附息.getValue(), secInfoModelDto.getBondsType())) {
                secInfoModel.setBasis(Basis.A_365F);
            }
        }

        // e.获取债券的还本计划信息
        List<SecPrinSchedule> secPrinScheduleList = new ArrayList<SecPrinSchedule>();
        for (FinCashPlanDto item : finCashPlanList) {
            if (StringUtil.equals(item.getCashType1(), DictCodeListExt1.现金流一级类型_本金.getValue())) {
                SecPrinSchedule schedule = new SecPrinSchedule();
                schedule.setAmount(item.getIntAmt());
                schedule.setFlag(StringUtil.equals(item.getIsPayInt(), DictCodeList.是否_是.getValue()));
                schedule.setPrincipal(item.getPrinAmt());
                schedule.setInterestEndDate(toLocalDate(item.getMdate()));
                secPrinScheduleList.add(schedule);
            }
        }

        // List<PaperIrReset> paperIrResetList = bondsIntResetList.stream().map(item -> {
        // PaperIrReset ret = new PaperIrReset();
        // // BeanUtil.copyPropertiesIgnoreNull(ret, item);
        // ret.setBaseRateEffDate(BasicUtil.dateToLocal(item.getBaseRateEffDate()));
        // ret.setParRate(item.getParRate());
        // ret.setParRateEffDate(BasicUtil.dateToLocal(item.getParRateEffDate()));
        // return ret;
        // }).collect(Collectors.toList());
        // if (paperIrResetList != null && paperIrResetList.size() > 0) {
        // paperIrResetList.get(0).setBaseRateEffDate(
        // BasicUtil.dateToLocal(secInfoModelDto.getStartDate()));
        // }

        List<PaperIrReset> paperIrResetList = finCashCalcRuleList.stream().map(item -> {
            PaperIrReset ret = new PaperIrReset();
            // BeanUtil.copyPropertiesIgnoreNull(ret, item);
            ret.setBaseRateEffDate(BasicUtil.dateToLocal(item.getEffDate()));
            ret.setParRate(item.getYield());
            ret.setParRateEffDate(BasicUtil.dateToLocal(item.getEffDate()));
            return ret;
        }).collect(Collectors.toList());
        if (paperIrResetList != null && paperIrResetList.size() > 0) {
            paperIrResetList.get(0).setBaseRateEffDate(BasicUtil.dateToLocal(secInfoModelDto.getStartDate()));
        }
        if (paperIrResetList != null && paperIrResetList.size() > 1) {
            // 多重利率则计算按浮动利率算
            secInfoModel.setIntRateType(IntRateType.浮动利率);
        }
        yieldPriceInParameter = new YieldPriceInParameter();
        yieldPriceInParameter.setCalDate(toLocalDate(dto.getSettleDate()));
        yieldPriceInParameter.setSecInfoModel(secInfoModel);
        yieldPriceInParameter.setSecIntScheduleList(invScheduleList);
        yieldPriceInParameter.setSecPrinScheduleList(secPrinScheduleList);
        yieldPriceInParameter.setPaperIrResetList(paperIrResetList);
        priceYieldInParameter = new PriceYieldInParameter();
        priceYieldInParameter.setCalDate(toLocalDate(dto.getSettleDate()));
        priceYieldInParameter.setSecInfoModel(secInfoModel);
        priceYieldInParameter.setSecIntScheduleList(invScheduleList);
        priceYieldInParameter.setSecPrinScheduleList(secPrinScheduleList);
        priceYieldInParameter.setPaperIrResetList(paperIrResetList);
        yieldPriceInParameter.setCashScheme(FlowCashScheme.付息计划推算计息年度);
        priceYieldInParameter.setCashScheme(FlowCashScheme.付息计划推算计息年度);
        yieldPriceInParameter.setDisScheme(DiscountScheme.以剩余年限为单位);
        priceYieldInParameter.setDisScheme(DiscountScheme.以剩余年限为单位);
        if (StringUtil.equals(DictCodeList.息票品种_附息.getValue(), secInfoModelDto.getBondsType())) {
            if (isNotFomartSec(intSchList, secInfoModelDto)) {
                yieldPriceInParameter.setCashScheme(FlowCashScheme.理论付息区间);
                priceYieldInParameter.setCashScheme(FlowCashScheme.理论付息区间);
                yieldPriceInParameter.setDisScheme(DiscountScheme.以付息区间为单位);
                priceYieldInParameter.setDisScheme(DiscountScheme.以付息区间为单位);
            }
            else {
                yieldPriceInParameter.setCashScheme(FlowCashScheme.付息计划推算计息年度);
                priceYieldInParameter.setCashScheme(FlowCashScheme.付息计划推算计息年度);
                yieldPriceInParameter.setDisScheme(DiscountScheme.以剩余年限为单位);
                priceYieldInParameter.setDisScheme(DiscountScheme.以剩余年限为单位);
            }
        }
        yieldPriceInParameter.setCalMethod(secInfoModel.getCalMethod());
        priceYieldInParameter.setCalMethod(secInfoModel.getCalMethod());
        // 后续区间利息计算方案
        if (IntRateType.浮动利率.equals(secInfoModel.getIntRateType())) {
            priceYieldInParameter.setNextIntMethod(NextIntCalMethod.按实际利率重置分段计息计算);
            yieldPriceInParameter.setNextIntMethod(NextIntCalMethod.按实际利率重置分段计息计算);

            yieldPriceInParameter.setCurrIntMethod(CurrIntCalMethod.按实际利率重置分段计息计算);
            priceYieldInParameter.setCurrIntMethod(CurrIntCalMethod.按实际利率重置分段计息计算);
        }
        else {
            priceYieldInParameter.setNextIntMethod(NextIntCalMethod.使用计算日最新最新票面计算);
            yieldPriceInParameter.setNextIntMethod(NextIntCalMethod.使用计算日最新最新票面计算);

            yieldPriceInParameter.setCurrIntMethod(CurrIntCalMethod.使用区间起始日前最新票面利率计算);
            priceYieldInParameter.setCurrIntMethod(CurrIntCalMethod.使用区间起始日前最新票面利率计算);
        }

        // 百元面值应计利息
        BigDecimal accurInt = BigDecimal.ZERO;
        // 交易方式为“一级市场”时，单位应计利息=应计利息总额=0
        if (StringUtil.equals(dto.getTradeMarketMode(), DictCodeListExt1.交易市场_一级市场.getValue())) {
            dto.setUnitInt(accurInt);
            dto.setIntAmt(BigDecimal.ZERO);
            if (计算融入券) {
                dto.setIntegrateIntAmt(BigDecimal.ZERO);
            }
        }
        // 交易方式为“二级市场”时，单位应计利息 =e取得的百元面值应计利息/100*a取得的当前债券面额
        else if (StringUtil.equals(dto.getTradeMarketMode(), DictCodeListExt1.交易市场_二级市场.getValue())) {
            // a.当前债券面额取得（根据还本计划计算）
            // BigDecimal curSecAmt = transactionBond001Mapper.getCurSecAmt(dto);
            // accurInt = ArithmeticUtil.round(BondsCalculation.calAccurInt(dto.getSettDate(),
            // bondsInfo,
            // bondsScheduleList, bondsIntResetList), SCALE, RoundingMode.HALF_UP);
            // 单位应计利息
            // if (dto.getUnitInt() == null) {
            // dto.setUnitInt(ArithmeticUtil.doMul(
            // ArithmeticUtil.doDiv(accurInt, new BigDecimal(100), SCALE), curSecAmt));
            // }
            // // 应计利息总额 = 单位应计利息 * 券面总额/100
            // dto.setIntAmt(ArithmeticUtil.doDiv(ArithmeticUtil.doMul(accurInt, dto.getPrinAmt()),
            // new BigDecimal(100), SCALE));

            // 交易所债券特殊处理start----------------------------------------------------------------
            Date tempSettleDate = dto.getSettleDate();
            // 如果交易场所为上交所、深交所的情况
            if (isJiaoYiSuo) {
                // 付息债、贴现债、零息债，交割日+1
                // if (StringUtil.equals(DictCodeList.息票品种_附息.getValue(), secInfoModelDto.getBondsType())
                // || StringUtil.equals(DictCodeList.息票品种_贴现.getValue(), secInfoModelDto.getBondsType())
                // || StringUtil.equals(DictCodeList.息票品种_零息.getValue(), secInfoModelDto.getBondsType())) {
                // add by 张年 20190909 start
                // 行权-回售 行权-赎回 交割日不加1
                if (!StringUtil.equals(dto.getPs(), DictCodeList.交易方向_行权_回售.getValue())
                    && !StringUtil.equals(dto.getPs(), DictCodeList.交易方向_行权_赎回.getValue())) {
                    tempSettleDate = DateUtil.addDay(tempSettleDate, 1);
                }
                // add by 张年 20190909 end
                // }
            }
            // 交易所债券特殊处理end----------------------------------------------------------------

            // a.当前债券面额取得（根据还本计划计算）
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("finprodId", dto.getFinprodId());
            // paraMap.put("settleDate", DateUtil.format(dto.getSettleDate()));
            paraMap.put("settleDate", DateUtil.format(tempSettleDate));
            BigDecimal curSecAmt = restTemplate.postForObject(
                ServiceApplication.TRADE_SERVICE_NAME + "/TrdBondInvestTrade/getCurSecAmt", paraMap, BigDecimal.class);

            AccurIntCalInParameter accurIntCalInParameter = new AccurIntCalInParameter();
            // 计算日
            // accurIntCalInParameter.setCalDate(this.toLocalDate(dto.getSettleDate()));
            accurIntCalInParameter.setCalDate(toLocalDate(tempSettleDate));
            if (paperIrResetList != null && paperIrResetList.size() > 1) {
                // 多重利率则计算按浮动利率算
                secInfoModel.setIntRateType(IntRateType.浮动利率);
            }
            // 债券基本资料
            accurIntCalInParameter.setSecInfoModel(secInfoModel);
            // 付息计划
            accurIntCalInParameter.setSecIntScheduleList(invScheduleList);
            // 利率重置信息
            accurIntCalInParameter.setPaperIrResetList(paperIrResetList);

            // 交易所债券特殊处理start----------------------------------------------------------------
            AccurIntCalOutParameter out = null;
            // 如果交易场所为上交所、深交所的情况
            if (isJiaoYiSuo) {
                // 付息债、贴现债、零息债，交割日+1
                // if (StringUtil.equals(DictCodeList.息票品种_附息.getValue(), secInfoModelDto.getBondsType())
                // || StringUtil.equals(DictCodeList.息票品种_贴现.getValue(), secInfoModelDto.getBondsType())
                // || StringUtil.equals(DictCodeList.息票品种_零息.getValue(), secInfoModelDto.getBondsType())) {
                // 付息计划截止日对应的利息
                Map<String, BigDecimal> intMap = new HashMap<>();
                if (null != invScheduleList && !invScheduleList.isEmpty()) {
                    for (SecIntSchedule intSche : invScheduleList) {
                        intMap.put(intSche.getActualCutoffDate().toString(), intSche.getAmount());
                    }
                }

                if (intMap.containsKey(accurIntCalInParameter.getCalDate().toString())
                    && !((Variety.贴现.equals(secInfoModel.getBondsType())) || Variety.零息.equals(secInfoModel.getBondsType()))) {
                    out = new AccurIntCalOutParameter();
                    out.setInterest(
                        intMap.get(accurIntCalInParameter.getCalDate().toString()).multiply(new BigDecimal("100")).divide(
                            curSecAmt, 12, RoundingMode.HALF_UP));
                    accurIntCalInParameter.setCalDate(accurIntCalInParameter.getCalDate());
                    AccurIntForSecheduleCalculator calculator2 = CalculatorFactory.createAccurIntForSecheduleCalculator();
                    if (accurIntCalInParameter.getPaperIrResetList() != null
                        && accurIntCalInParameter.getPaperIrResetList().size() > 1) {
                        accurIntCalInParameter.getSecInfoModel().setIntRateType(IntRateType.浮动利率);
                    }
                    out = calculator2.calculate(accurIntCalInParameter);
                }
                // }
            }
            // 交易所债券特殊处理end----------------------------------------------------------------

            if (out != null) {
                accurInt = ArithmeticUtil.round(out.getInterest(), SCALE, RoundingMode.HALF_UP);
            }
            else {
                // 从计算工厂中创建应计利息计算器
                AccurIntCalculator calc = CalculatorFactory.createAccurIntCalculator();
                // 计算利息
                AccurIntCalOutParameter accurIntCalOutParameter = calc.calculate(accurIntCalInParameter);
                accurInt = ArithmeticUtil.round(accurIntCalOutParameter.getInterest(), SCALE, RoundingMode.HALF_UP);
            }

            // 单位应计利息
            if (dto.getUnitInt() == null) {
                if (isJiaoYiSuo) {
                    dto.setUnitInt(
                        ArithmeticUtil.divide(ArithmeticUtil.multiply(accurInt, curSecAmt), new BigDecimal(100),
                            Integer.parseInt(StringUtils.isEmpty(CacheUtil.getSysParam(
                                "SEC_INT_SCALE_EXCHANGE")) ? "8" : CacheUtil.getSysParam("SEC_INT_SCALE_EXCHANGE")),
                            RoundingMode.HALF_UP));
                }
                else {
                    dto.setUnitInt(ArithmeticUtil.divide(ArithmeticUtil.multiply(accurInt, curSecAmt), new BigDecimal(100),
                        Integer.parseInt(StringUtils.isEmpty(
                            CacheUtil.getSysParam("SEC_INT_SCALE_BANK")) ? "12" : CacheUtil.getSysParam("SEC_INT_SCALE_BANK")),
                        RoundingMode.HALF_UP));
                }
            }
            // 应计利息总额 = 单位应计利息 * 券面总额/100
            if (计算融入券) {
                dto.setIntegrateIntAmt(ArithmeticUtil.divide(ArithmeticUtil.multiply(dto.getUnitInt(), dto.getPrinAmt()), 100, 2));
            }
            dto.setIntAmt(ArithmeticUtil.divide(ArithmeticUtil.multiply(dto.getUnitInt(), dto.getPrinAmt()), 100, 2));

            // add by wl 交易方向选择行权时，应计利息及利息总额为0
            if ((StringUtil.equals(DictCodeList.交易方向_行权_回售.getValue(), dto.getPs())
                 || StringUtil.equals(DictCodeList.交易方向_行权_赎回.getValue(), dto.getPs()))) {
                dto.setUnitInt(BigDecimal.ZERO);
                dto.setIntAmt(BigDecimal.ZERO);
                if (计算融入券) {
                    dto.setIntegrateIntAmt(BigDecimal.ZERO);
                }
            }
        }
        try {
            // 单位净价
            if (StringUtil.equals(dto.getCalculateField(), "unitCprice")) {
                dto.setCpriceAmt(ArithmeticUtil.divide(ArithmeticUtil.multiply(dto.getUnitCprice(), dto.getPrinAmt()),
                    new BigDecimal(100), 2));
                if (计算融入券) {
                    dto.setIntegrateCpriceAmt(dto.getCpriceAmt());
                }
                // 全价总额 = 净价总额+ 利息总额
                dto.setFpriceAmt(ArithmeticUtil.doAdd(dto.getIntAmt(), dto.getCpriceAmt()));
                if (计算融入券) {
                    dto.setIntegrateFpriceAmt(ArithmeticUtil.doAdd(dto.getIntAmt(), dto.getCpriceAmt()));
                }
                // round（全价总额/券面总额，10）*100
                // 全价 = 单位应计利息 + 单位净价 (保持横向的一致性)
                dto.setUnitFprice(ArithmeticUtil.doAdd(dto.getUnitInt(), dto.getUnitCprice()));
            }
            // 全价总额
            else if (StringUtil.equals(dto.getCalculateField(), "fpriceAmt")) {
                // 单位全价 = 全价总额 * 100 / 券面总额
                dto.setUnitFprice(
                    ArithmeticUtil.divide(ArithmeticUtil.multiply(dto.getFpriceAmt(), new BigDecimal(100)), dto.getPrinAmt(), 8));
                dto.setCpriceAmt(ArithmeticUtil.subtract(dto.getFpriceAmt(), dto.getIntAmt()));
                if (计算融入券) {
                    dto.setIntegrateCpriceAmt(dto.getCpriceAmt());
                }
                // round（净价总额/券面总额，10）*100
                // 单位净价 = 全价-单位应计利息 (保持横向的一致性)
                dto.setUnitCprice(ArithmeticUtil.subtract(dto.getUnitFprice(), dto.getUnitInt()));
            }
            switch (dto.getCalculateField()) {
                // 单位净价
                case "unitCprice":
                    // 全价总额
                case "fpriceAmt":
                    // 全价总额==>到期收益率
                    yieldPriceInParameter.setFullPrice(dto.getUnitFprice()/* dto.getFpriceAmt() */);
                    yieldPriceOutParameter = yieldByPriceCalculator.calculate(yieldPriceInParameter);
                    dto.setYield(yieldPriceOutParameter.getMatYield());
                    if (secInfoModelDto.getNextExeDate() != null) {
                        // 全价总额==>行权收益率
                        yieldPriceInParameter.setNextExeDate(toLocalDate(secInfoModelDto.getNextExeDate()));
                        yieldPriceInParameter.setNextExePrice(secInfoModelDto.getNextExercisePrice());
                        if (yieldPriceInParameter.getNextExePrice() != null) {
                            yieldPriceOutParameter = yieldByPriceCalculator.calculate(yieldPriceInParameter);
                            dto.setExerYield(yieldPriceOutParameter.getExeYield());
                        }
                        else {
                            dto.setExerYield(null);
                        }
                    }
                    break;
                // 到期收益率
                case "yield":
                    // 到期收益率==>全价总额
                    priceYieldInParameter.setMatYield(dto.getYield());
                    priceYieldOutParameter = priceByYieldCalculator.calculate(priceYieldInParameter);
                    // dto.setFpriceAmt(priceYieldOutParameter.getFullPrice());
                    dto.setUnitFprice(priceYieldOutParameter.getFullPrice());
                    // 全价总额==>行权收益率
                    if (secInfoModelDto.getNextExeDate() != null) {
                        yieldPriceInParameter.setFullPrice(dto.getUnitFprice()/* dto.getFpriceAmt() */);
                        yieldPriceInParameter.setNextExeDate(toLocalDate(secInfoModelDto.getNextExeDate()));
                        yieldPriceInParameter.setNextExePrice(secInfoModelDto.getNextExercisePrice());
                        if (yieldPriceInParameter.getNextExePrice() != null) {
                            yieldPriceOutParameter = yieldByPriceCalculator.calculate(yieldPriceInParameter);
                            dto.setExerYield(yieldPriceOutParameter.getMatYield());
                        }
                        else {
                            dto.setExerYield(null);
                        }
                    }
                    break;
                // 行权收益率
                case "exerYield":
                    // 行权收益率==>全价总额
                    if (secInfoModelDto.getNextExeDate() != null) {
                        priceYieldInParameter.setMatYield(dto.getExerYield());
                        yieldPriceInParameter.setNextExeDate(toLocalDate(secInfoModelDto.getNextExeDate()));
                        yieldPriceInParameter.setNextExePrice(secInfoModelDto.getNextExercisePrice());
                        priceYieldOutParameter = priceByYieldCalculator.calculate(priceYieldInParameter);
                        dto.setFpriceAmt(priceYieldOutParameter.getFullPrice());
                        if (计算融入券) {
                            dto.setIntegrateFpriceAmt(priceYieldOutParameter.getFullPrice());
                        }
                        // 全价总额==>到期收益率
                        yieldPriceInParameter.setFullPrice(dto.getFpriceAmt());
                        yieldPriceOutParameter = yieldByPriceCalculator.calculate(yieldPriceInParameter);
                        dto.setYield(yieldPriceOutParameter.getMatYield());
                    }
                    break;
                default:
                    break;
            }
            // 到期收益率 或 行权收益率
            if (StringUtil.equals(dto.getCalculateField(), "yield") || StringUtil.equals(dto.getCalculateField(), "exerYield")) {
                // 全价总额 = 单位全价 * 券面总额/100
                dto.setFpriceAmt(ArithmeticUtil.divide(ArithmeticUtil.multiply(dto.getUnitFprice(), dto.getPrinAmt()),
                    new BigDecimal(100), 2));
                if (计算融入券) {
                    dto.setIntegrateFpriceAmt(dto.getFpriceAmt());
                }
                // 净价总额 = 全价总额 - 应计利息总额
                dto.setCpriceAmt(ArithmeticUtil.subtract(dto.getFpriceAmt(), dto.getIntAmt()));
                if (计算融入券) {
                    dto.setIntegrateCpriceAmt(dto.getCpriceAmt());
                }
                // 单位净价 = round（净价总额/券面总额，10）*100
                // 单位净价 = 全价 - 单位应计利息
                dto.setUnitCprice(ArithmeticUtil.subtract(dto.getUnitFprice(), dto.getUnitInt()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (dto.getYield() != null) {
            dto.setYield(ArithmeticUtil.multiply(dto.getYield(), new BigDecimal(100)));
        }
        if (dto.getExerYield() != null) {
            dto.setExerYield(ArithmeticUtil.multiply(dto.getExerYield(), new BigDecimal(100)));
        }
        
    }

    public static boolean isNotFomartSec(List<FinCashPlanDto> secIntScheduleList, SecInfoModelDto secInfoModelDto) {
        // 根据【实际起始日】排序付息计划
        Collections.sort(secIntScheduleList, (o1, o2) -> o1.getVdateUnadjust().compareTo(o2.getVdateUnadjust()));

        if (StringUtil.equals(DictCodeList.频率_月.getValue(), secInfoModelDto.getYearIntRate())) {
            for (FinCashPlanDto she : secIntScheduleList) {
                Date mDate = DateUtil.addMonthDate(she.getVdateUnadjust(), 1);
                if (mDate.compareTo(she.getMdateUnadjust()) != 0) {
                    return false;
                }
            }
        }
        else if (StringUtil.equals(DictCodeList.频率_季.getValue(), secInfoModelDto.getYearIntRate())) {
            for (FinCashPlanDto she : secIntScheduleList) {
                Date mDate = DateUtil.addMonthDate(she.getVdateUnadjust(), 3);
                if (mDate.compareTo(she.getMdateUnadjust()) != 0) {
                    return false;
                }
            }
        }
        else if (StringUtil.equals(DictCodeList.频率_半年.getValue(), secInfoModelDto.getYearIntRate())) {
            for (FinCashPlanDto she : secIntScheduleList) {
                Date mDate = DateUtil.addMonthDate(she.getVdateUnadjust(), 6);
                if (mDate.compareTo(she.getMdateUnadjust()) != 0) {
                    return false;
                }
            }
        }
        else {
            for (FinCashPlanDto she : secIntScheduleList) {
                Date mDate = DateUtil.addYear(she.getVdateUnadjust(), 1);
                if (mDate.compareTo(she.getMdateUnadjust()) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
