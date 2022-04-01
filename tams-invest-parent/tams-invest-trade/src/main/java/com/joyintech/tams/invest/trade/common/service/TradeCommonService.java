package com.joyintech.tams.invest.trade.common.service;

import com.joyintech.calculation.util.ArithmeticUtil;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.FinCashPlanMapper;
import com.joyintech.tams.common.base.dao.FinProductAddMapper;
import com.joyintech.tams.common.base.dao.FinProductMapper;
import com.joyintech.tams.common.base.dao.TrdProductDealMapper;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.component.business.bond.BusiFinExercisePlanService;
import com.joyintech.tams.component.business.common.service.DateCalService;
import com.joyintech.tams.component.business.core.dto.WflValidInputParameter;
import com.joyintech.tams.component.business.core.service.MstWorkflowCommonService;
import com.joyintech.tams.component.business.trade.dto.BussTrdTransfenpeiDto;
import com.joyintech.tams.constant.*;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.dto.common.PledgeBondInfoDto;
import com.joyintech.tams.invest.common.dto.period.common.BussTrdTransferOrderDto;
import com.joyintech.tams.invest.common.risk.service.RiskCreditListCommonValidatorService;
import com.joyintech.tams.invest.common.trade.AllTradeCommonUtil;
import com.joyintech.tams.invest.common.trade.dao.CreditLinesDealsMapper;
import com.joyintech.tams.invest.common.trade.dao.TrdCommonBaseMapper;
import com.joyintech.tams.invest.common.trade.dto.CreditLinesInputDto;
import com.joyintech.tams.invest.interf.dto.CusdepOrderDto;
import com.joyintech.tams.invest.interf.service.InterfComponentService;
import com.joyintech.tams.invest.trade.bond.dto.TrdBondInvestTradeDto;
import com.joyintech.tams.invest.trade.bondlend.dto.TrdBondLendInvestTradeDto;
import com.joyintech.tams.invest.trade.bondtostock.dto.TrdBondToStock001Dto;
import com.joyintech.tams.invest.trade.buyoutrepo.dto.TrdOutRightRepoTrade002Dto;
import com.joyintech.tams.invest.trade.common.dto.EtrInvestInfoDto;
import com.joyintech.tams.invest.trade.conbond.dto.TrdConBond003Dto;
import com.joyintech.tams.invest.trade.conbond.dto.TrdConBondDto;
import com.joyintech.tams.invest.trade.fund.dto.TrdFundInvestTradeConfirmDto;
import com.joyintech.tams.invest.trade.fund.dto.TrdFundInvestTradeDto;
import com.joyintech.tams.invest.trade.lend.dto.TrdLendInvestTradeDto;
import com.joyintech.tams.invest.trade.manualsettle.dto.ManualSettleDto;
import com.joyintech.tams.invest.trade.planccy.dto.TrdPlanCcyInvestTradeConfirmDto;
import com.joyintech.tams.invest.trade.planccy.dto.TrdPlanCcyInvestTradeDto;
import com.joyintech.tams.invest.trade.plannet.dto.TrdPlanNetInvestTradeConfirmDto;
import com.joyintech.tams.invest.trade.plannet.dto.TrdPlanNetInvestTradeDto;
import com.joyintech.tams.invest.trade.primarybiddingmanage.dto.BidOrderBidPriceDto;
import com.joyintech.tams.invest.trade.primarybiddingmanage.dto.PrimaryBiddingManageDto;
import com.joyintech.tams.invest.trade.primarybiddingmanage.dto.PrimaryBiddingPortfolioDto;
import com.joyintech.tams.invest.trade.primarybiddingmanage.dto.TrdBiddingManage003Dto;
import com.joyintech.tams.invest.trade.project.dto.TrdProjectInvestTrade002Dto;
import com.joyintech.tams.invest.trade.projectnet.dto.TrdProjectNetInvestTradeConfirmDto;
import com.joyintech.tams.invest.trade.projectnet.dto.TrdProjectNetInvestTradeDto;
import com.joyintech.tams.invest.trade.stockdeal.dto.TrdStockDto;
import com.joyintech.tams.invest.trade.trddepositinvesttrade.dto.TrdDepositInvestTrade001Dto;
import com.joyintech.tams.invest.trade.trdplanrateinvesttrade.dto.TrdPlanRateInvestTradeDto;
import com.joyintech.tams.invest.trade.trdrepotrade.dto.TrdRepoTrade002Dto;
import com.joyintech.tams.systemtools.interf.IPlatformDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @program: tams-invest-parent
 * @description: 交易类共通Service
 * @author: 强成西
 * @create: 2018-12-04 11:54
 **/
@Service
public class TradeCommonService extends BaseService {
    
    public static final String 金融产品代码 = "FINPROD_ID";
	/**
     * mstWorkflowCommonService工作流组件
     */
    @Autowired(required=false)
    private MstWorkflowCommonService mstWorkflowCommonService;
	
    @Autowired
    private TrdCommonBaseMapper trdCommonBaseMapper;
    @Autowired
    private CreditLinesDealsMapper creditLinesDealsMapper;
    @Autowired
    private IPlatformDate iPlatformDate;
    @Autowired
    private FinProductAddMapper finProductAddMapper;
    /**
     * 向其它系统发送划款报文
     */
    @Autowired
    private InterfComponentService interfComponentService;

    @Autowired
    private BusiFinExercisePlanService busiFinExercisePlanService;
    /**
     * DateCalService
     */
    @Autowired(required = false)
    private DateCalService dateCalService;

    @Autowired
    private FinCashPlanMapper finCashPlanMapper;

    @Autowired
    private RiskCreditListCommonValidatorService riskCreditListCommonValidatorService;

    @Autowired(required = false)
    private FinProductMapper finProductMapper;


    @Autowired
    private TrdProductDealMapper trdProductDealMapper;
    
    /**
     * 查询生效的委托
     * @param entrustId
     * @return
     */
    public EtrInvestInfoDto findEtrInfo(String entrustId) {
        return restTemplate.postForObject(ServiceApplication.TRADE_SERVICE_NAME + "/common/trdcommonbase/findEtrInfo", entrustId,
                EtrInvestInfoDto.class);
    }

    /**
     * 查询交易员
     * @param entrustId 委托代码
     * @return 交易员
     * @see
     * @since 1.0
     */
    public String getTrader(String entrustId){
        Map<String,Object> map = new HashMap<>();
        map.put("entrustId", entrustId);
        map.put("entrustStatus", DictCodeList.委托状态_已接收.getValue());
        return restTemplate.postForObject(ServiceApplication.TRADE_SERVICE_NAME
                + "/TrdPlanRateInvestTrade/getTrader",map,String.class);
    }
    /**
     * 划款信息向其它系统发送报文信息
     * @param entity 划款组建对应的实体类
     * @see
     * @since 1.0
     */
    public void sendCusdepOrder(List<BussTrdTransferOrderDto> entity){
        if (StringUtil.equals(CacheUtil.getSysParam("interfFlag_send_orders"), "01") && entity != null) {
            entity.forEach(item -> {
                if(StringUtil.equals(DictCodeListExt1.收支方向_支出.getValue(), item.getPs())){
                    CusdepOrderDto cusdepOrderDto = interfComponentService.sendCusdepRequest(item.getOrderId(), StringUtil.equals(item.getPayType(), 
                                                    DictCodeListExt1.平安划款类型_DVP划款.getValue()), "1");
                    if(cusdepOrderDto == null || cusdepOrderDto.getErrMsg() != null 
                        || StringUtil.equals(cusdepOrderDto.isResult(), false)){
                        throw new BusinessException("请求发送异常...");
                    }
                }
            });
        }
    }
    
    /**
     * huwenbin 
     * 指令接收交易录单验证数据已存在流程中
     * @param entrustId
     * @param pageId
     * @return
     */
    public String check(String entrustId, String pageId){
    	WflValidInputParameter parameter = new WflValidInputParameter();
        parameter.setBusinessDataPk3(entrustId);
        parameter.setPageId(pageId);
        if(null != mstWorkflowCommonService && 
            mstWorkflowCommonService.isInWflProcess(parameter)){
        	return "已存在流程,不能操作！";
        }
        else{
        	return null;
        }
    }
    /**
     * wangguiqi
     * 指令接收交易录单根据委托代码查询委托金额
     * @param entrustId
     * @return BigDecimal
     */
    public BigDecimal getEntrustAmtByEntrustId(String entrustId) {
    	BigDecimal entrustNum = BigDecimal.ZERO;
    	entrustNum = restTemplate.postForObject(ServiceApplication.TRADE_SERVICE_NAME + "/common/trdcommonbase/getEntrustAmtByEntrustId", entrustId,
    			BigDecimal.class);
		return entrustNum;
    }
    /**
     * wangguiqi
     * 指令接收交易录单根据委托代码查询流程中已录入金额
     * @param entrustId
     * @return BigDecimal
     */
    public BigDecimal getWflPrinAmt(String entrustId, String finProductType, String invAim) {
    	BigDecimal wflPrinAmt = BigDecimal.ZERO;
        Map<String,Object> map = new HashMap<>();
        map.put("entrustId", entrustId);
        map.put("type", finProductType);
        map.put("invAim", invAim);
    	wflPrinAmt = restTemplate.postForObject(ServiceApplication.TRADE_SERVICE_NAME + "/common/trdcommonbase/getWflPrinAmt", map,
    			BigDecimal.class);
		return wflPrinAmt;
    }

    /**
     * 获取下一个工作日
     *
     * @param parameters 画面参数
     * @return 下一个工作日
     * @see
     * @since 1.0
     */
    public String getNextWorkDay(Map<String, Object> parameters) {
        String tradeDateStr = (String)parameters.get("tradeDate");
        String calendarIdStr = (String)parameters.get("calendarId");
        if (StringUtil.isBlank(tradeDateStr) || StringUtil.isBlank(calendarIdStr)) {
            return null;
        }

        if (dateCalService != null) {
            Date tradeDate = DateUtil.parse((String)parameters.get("tradeDate"));
            List<String> holiday = dateCalService.getDealCalendarHoliday(tradeDate,
                    DateUtil.addDays(tradeDate, 31), (String)parameters.get("calendarId"));
            String settleSpreed = (String)parameters.get("settleSpreed");
            Date tradingDay = dateCalService.calculationTradingDay(tradeDate, Integer.parseInt(StringUtil.isEmpty(settleSpreed)?"0":settleSpreed), holiday, "1");
            return DateUtil.format(tradingDay);
        }

        return null;
    }

    /**
     * check是否流程中，目前只有金融产品代码在汇总表是否存在
     * @param findKey
     * @param key类型
     * @param 是否汇总表
     * @return
     */
    public List<String> checkWflData(String findKey, String key类型, boolean 是否汇总表) {
        if (金融产品代码.equals(key类型)) {
            if (是否汇总表) {
                return trdCommonBaseMapper.checkTotleWflData(findKey);
            }
        }
        return null;
    }

    public List<BigDecimal> selectProAvailableCash(String entrustId){
        Map<String,Object> map = new HashMap<>();
        map.put("entrustId",entrustId);
        return trdCommonBaseMapper.selectProAvailableCash(map);
    }

    /**
     * 查询当天是否为资产收息日或到期日
     * @param trdProductDeal
     * @return
     */
    public int getPayTrad(Map<String, Object> map) {
        return finCashPlanMapper.getPayTrad(map);
    }
    /**
     * 查询转仓日前一天(其他应收款-利息)科目余额是否为0
     * @param trdProductDeal
     * @return
     */
    public Map<String, Object> getYSKLXBalance(Map<String, Object> map) {
        return trdCommonBaseMapper.getYSKLXBalance(map);
    }

    /**
     * 查询理论结息日有没有待处理和流程中的资产存续交易
     * @param params
     * @return
     */
    public int selectTrdOnPayDate(Map<String, Object> params) {
        return trdCommonBaseMapper.selectTrdOnPayDate(params);
    }

    // add by etliu 20210421 权益类资产相关代码合并 start
    /**
     * chaowei
     * 指令接收交易录单根据委托代码查询流程中已录入金额
     * @param finprod_id
     * @return BigDecimal
     */
    public BigDecimal queryWflPrinAmtByAssetId(String finprod_id) {
        BigDecimal wflPrinAmt = BigDecimal.ZERO;
        Map<String,Object> map = new HashMap<>();
        map.put("finprod_id", finprod_id);
        wflPrinAmt = restTemplate.postForObject(ServiceApplication.TRADE_SERVICE_NAME + "/common/trdcommonbase/queryWflPrinAmtByAssetId", map,
                BigDecimal.class);
        return wflPrinAmt;
    }
    // add by etliu 20210421 权益类资产相关代码合并 end

    // add by etliu 20210129 start
    /**
     * 检查画面选择的成交编号是否存在对应的报价
     * 目前债券二级交易和回购交易画面会用到
     *
     * @return 是否存在对应的报价
     * @see
     * @since 1.0
     */
    public String checkExecIdHasQuote(String execId) {
        return restTemplate.getForObject(ServiceApplication.BANKUPLINK_SERVICE_NAME
                        + "/CfetsQuotedCommon/checkExecIdHasQuote?execId="
                        + execId,
                String.class);
    }
    // add by etliu 20210129 end

    /**
     * 根据组合ID查询母组合ID
     * @param portfolioId
     * @return
     */
    public String selectParentPortfolioIdByPortfolioId(String portfolioId) {
        if (StringUtil.isBlank(portfolioId)) {
            return null;
        }
        return restTemplate.postForObject(ServiceApplication.TRADE_SERVICE_NAME + "/common/trdcommonbase/selectParentPortfolioIdByPortfolioId", portfolioId,
                String.class);
    }

    public AjaxResult executeOccupyCreditLines(Object object, String status, String occupyType, String method){
        AjaxResult ar = new AjaxResult();
        if (StringUtil.equals(BussinessDictCodeList.监控授信开关_关.getValue(), CacheUtil.getSysParam(BussinessConstant.ISCREDIT))){
            return ar;
        }
        List<CreditLinesInputDto> inputDtoList = new ArrayList<>();
        if (object != null){
            // 构建接口参数
            generateInputDtoList(object,status,method,inputDtoList);
            if (inputDtoList.size() > 0){
                if ("check".equals(method)){
                    ar = AllTradeCommonUtil.checkBeforeTradeComplete(inputDtoList);
                }else {
                    AllTradeCommonUtil.handleBeforeTradeComplete(inputDtoList,occupyType);
                }
            }
        }
        return ar;
    }
    /**
     * 生成调用接口list参数
     * @param object
     * @param status
     * @param method
     * @param inputDtoList
     */
    private void generateInputDtoList(Object object, String status, String method, List<CreditLinesInputDto> inputDtoList) {
        // 当传入参数为 List 对象时，回调生成List
        if (object instanceof  List<?>){
            for (Object obj : (List<?>)object){
                generateInputDtoList(obj,status,method,inputDtoList);
            }
        }else if (object instanceof String && method.equals("close")){
            // 指令关闭
            CreditLinesInputDto creditLinesInputDto = new CreditLinesInputDto();
            creditLinesInputDto.setTradeId(String.valueOf(object));
            creditLinesInputDto.setTradeStatus(status);
            inputDtoList.add(creditLinesInputDto);
        }else {
            CreditLinesInputDto creditLinesInputDto = generateInputDto(object,status,method);
            if (creditLinesInputDto != null){
                if (!StringUtil.isEmptyList(creditLinesInputDto.getInputDtoList())){
                    inputDtoList.addAll(creditLinesInputDto.getInputDtoList());
                }else {
                    inputDtoList.add(creditLinesInputDto);
                }

            }
        }
    }

    private CreditLinesInputDto generateInputDto(Object object, String status, String method) {
        CreditLinesInputDto creditLinesInputDto = null;
        // 债券
        if (object instanceof TrdBondInvestTradeDto){
            TrdBondInvestTradeDto inputDto = (TrdBondInvestTradeDto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            creditLinesInputDto.setTradeStatus(status);
            // 债券卖出取交割日
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_卖出.getValue())
                    || (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_行权_回售.getValue()))){
                creditLinesInputDto.setPs(DictCodeList.交易方向_卖出.getValue());
                creditLinesInputDto.setTradeDate(inputDto.getSettleDate());
            }
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
            }
            // 多组合和单组合的金额信息和交易编号取值不同
//            if (StringUtil.isEmptyList(inputDto.getBussTrdTransfenpeiDto())){
                creditLinesInputDto.setTradeId(inputDto.getTotleTradeId());
                creditLinesInputDto.setTradeAmt(inputDto.getTotalFaceValue());
//            }else {
//                creditLinesInputDto.setTradeId(inputDto.getTotleTradeId());
//                BigDecimal tradeAmt = BigDecimal.ZERO;
//                for (BussTrdTransfenpeiDto item : inputDto.getBussTrdTransfenpeiDto()){
//                    tradeAmt = tradeAmt.add(item.getTradeAmt());
//                }
//                creditLinesInputDto.setTradeAmt(tradeAmt);
//                creditLinesInputDto.setShareAmt(ArithmeticUtil.divide(tradeAmt,100,2));
//            }
            //最新面值
            BigDecimal newFaceValue = creditLinesDealsMapper.getFaceValue(creditLinesInputDto);
            if (newFaceValue == null){
                newFaceValue = new BigDecimal(100);
            }
            if (creditLinesInputDto.getShareAmt() != null) {
                creditLinesInputDto.setTradeAmt(newFaceValue.multiply(creditLinesInputDto.getShareAmt()));
            }else if (creditLinesInputDto.getTradeAmt() != null){
                creditLinesInputDto.setTradeAmt(newFaceValue.multiply(ArithmeticUtil.divide(creditLinesInputDto.getTradeAmt(),100,2)));
            }
        }
        //可转债申购
        if (object instanceof TrdConBondDto){
            TrdConBondDto inputDto = (TrdConBondDto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setTradeId(inputDto.getBusiCode());
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setTradeStatus(status);
            // 申购日期
            creditLinesInputDto.setTradeDate(inputDto.getPurchDate());
            creditLinesInputDto.setShareAmt(null);
            creditLinesInputDto.setTradeAmt(inputDto.getTradeAmt());
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
            }
            //最新面值
            BigDecimal newFaceValue = creditLinesDealsMapper.getFaceValue(creditLinesInputDto);
            if (newFaceValue == null){
                newFaceValue = new BigDecimal(100);
            }
            if (creditLinesInputDto.getShareAmt() != null) {
                creditLinesInputDto.setTradeAmt(newFaceValue.multiply(creditLinesInputDto.getShareAmt()));
            }else if (creditLinesInputDto.getTradeAmt() != null){
                creditLinesInputDto.setTradeAmt(newFaceValue.multiply(ArithmeticUtil.divide(creditLinesInputDto.getTradeAmt(),100,2)));
            }
        }
        //可转债申购确认
//        if (object instanceof TrdConBond003Dto){
//            TrdConBond003Dto inputDto = (TrdConBond003Dto)object;
//
//            List<CreditLinesInputDto> inputDtoList = new ArrayList<>();
//            if (DictCodeListExt1.交易状态_初始.getValue().equals(status)){
//                // 撤销前置交易
//                CreditLinesInputDto creditInputDto = new CreditLinesInputDto();
//                creditInputDto.setTradeId(inputDto.getbTradeId());
//                creditInputDto.setTradeStatus(DictCodeListExt1.交易状态_撤销.getValue());
//                creditInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
//                creditInputDto.setPs(DictCodeList.交易方向_买入.getValue());
//                creditInputDto.setTradeDate(iPlatformDate.getSysDate());
//                creditInputDto.setFinprodType2("ManualSettle");
//                inputDtoList.add(creditInputDto);
//            }
//            // 重新实占
//            creditLinesInputDto = new CreditLinesInputDto();
//            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
//            creditLinesInputDto.setTradeId(inputDto.getBusiCode());
//            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
//            creditLinesInputDto.setTradeStatus(status);
//            creditLinesInputDto.setEntrustId(inputDto.getbTradeId());
//            creditLinesInputDto.setPs(DictCodeList.交易方向_买入.getValue());
//            creditLinesInputDto.setFinprodType2(DictCodeListExt2.共通业务类型_可转债申购交易.getValue());
//            // 申购日期
//            creditLinesInputDto.setTradeDate(iPlatformDate.getSysDate());
//            creditLinesInputDto.setShareAmt(null);
//            creditLinesInputDto.setTradeAmt(inputDto.getWinlotteryAmt());
//            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
//                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
//            }
//            //最新面值
//            BigDecimal newFaceValue = creditLinesDealsMapper.getFaceValue(creditLinesInputDto);
//            if (newFaceValue == null){
//                newFaceValue = new BigDecimal(100);
//            }
//            if (creditLinesInputDto.getShareAmt() != null) {
//                creditLinesInputDto.setTradeAmt(newFaceValue.multiply(creditLinesInputDto.getShareAmt()));
//            }else if (creditLinesInputDto.getTradeAmt() != null){
//                creditLinesInputDto.setTradeAmt(newFaceValue.multiply(ArithmeticUtil.divide(creditLinesInputDto.getTradeAmt(),100,2)));
//            }
//            inputDtoList.add(creditLinesInputDto);
//            creditLinesInputDto.setInputDtoList(inputDtoList);
//        }
        //可转债转股
        if (object instanceof TrdBondToStock001Dto){
            TrdBondToStock001Dto inputDto = (TrdBondToStock001Dto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setTradeId(inputDto.getBusiCode());
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setTradeStatus(status);
            // 资金到账日
            creditLinesInputDto.setTradeDate(inputDto.getSettleDate());
            creditLinesInputDto.setPs(DictCodeList.交易方向_卖出.getValue());
            //占用券面
            creditLinesInputDto.setTradeAmt(inputDto.getShareAmt());
            creditLinesInputDto.setCounterId(inputDto.getCounterId());

            List<CreditLinesInputDto> inputDtoList = new ArrayList<>();
            inputDtoList.add(creditLinesInputDto);

            CreditLinesInputDto creditLinesInputDto1 = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto1,creditLinesInputDto);
            creditLinesInputDto1.setPs(DictCodeList.交易方向_买入.getValue());
            creditLinesInputDto1.setShareAmt(inputDto.getConNum());
            creditLinesInputDto1.setTradeAmt(inputDto.getConNum().multiply(inputDto.getConPrice()));
            creditLinesInputDto1.setFinprodId("F10_"+inputDto.getFinprodMarketId());
            creditLinesInputDto1.setFinprodType2(DictCodeListExt1.金融产品类型_投管用_股票.getValue());
            inputDtoList.add(creditLinesInputDto1);

            creditLinesInputDto.setInputDtoList(inputDtoList);
        }
        // 债券一级市场中标结果维护
        if (object instanceof TrdBiddingManage003Dto){
            TrdBiddingManage003Dto inputDto = (TrdBiddingManage003Dto) object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_预占.getValue());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            creditLinesInputDto.setTradeAmt(inputDto.getWinPrinAmt());
            creditLinesInputDto.setTradeId(inputDto.getBidId());
            creditLinesInputDto.setTradeDate(inputDto.getBidDate());
            creditLinesInputDto.setFinprodType2(DictCodeListExt2.金融产品类型_投管用_一级投标债券.getValue());
            creditLinesInputDto.setPs(DictCodeList.交易方向_买入.getValue());
            creditLinesInputDto.setTradeStatus(status);
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
            }
            if (!StringUtil.isEmptyList(inputDto.getBidOrderBidPriceList())){
                List<CreditLinesInputDto> inputDtoList = new ArrayList<>();
                Set set = new HashSet();
                for (BidOrderBidPriceDto item : inputDto.getBidOrderBidPriceList()){
                    if(set.contains(item.getEntrustId())){
                        continue;
                    }
                    set.add(item.getEntrustId());
                    CreditLinesInputDto dto = new CreditLinesInputDto();
                    BeanUtil.copyPropertiesIgnoreNull(dto,creditLinesInputDto);
                    dto.setTradeAmt(BigDecimal.ZERO);
                    dto.setTradeId(item.getEntrustId());
                    for(BidOrderBidPriceDto item1 : inputDto.getBidOrderBidPriceList()){
                        if(item1.getEntrustId().equals(item.getEntrustId())){
                            if (item1.getWinPrinAmt() != null){
                                dto.setTradeAmt(item1.getWinPrinAmt().add(dto.getTradeAmt()));
                            }else {
                                dto.setTradeAmt(BigDecimal.ZERO.add(dto.getTradeAmt()));
                            }
                        }
                    }
                    if (dto.getTradeAmt().compareTo(BigDecimal.ZERO) >= 0){
                        inputDtoList.add(dto);
                    }
                }
                creditLinesInputDto.setInputDtoList(inputDtoList);
            }
        }
        // 债券一级市场汇总投标确认
//        if (object instanceof PrimaryBiddingManageDto){
//            PrimaryBiddingManageDto inputDto = (PrimaryBiddingManageDto) object;
//            creditLinesInputDto = new CreditLinesInputDto();
//            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
//            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
//            creditLinesInputDto.setTradeAmt(inputDto.getAdjustBallotnum());
//            creditLinesInputDto.setTradeId(inputDto.getBidId());
//            creditLinesInputDto.setTradeDate(inputDto.getBiddate());
//            creditLinesInputDto.setFinprodType2(DictCodeListExt2.金融产品类型_投管用_一级投标债券.getValue());
//            creditLinesInputDto.setPs(DictCodeList.交易方向_买入.getValue());
//            creditLinesInputDto.setTradeStatus(status);
//            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo002Dto())){
//                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo002Dto().getCounterId());
//            }
//            if (!StringUtil.isEmptyList(inputDto.getPrimaryBiddingPortfolioDtolist())){
//                List<CreditLinesInputDto> inputDtoList = new ArrayList<>();
//                for (PrimaryBiddingPortfolioDto item : inputDto.getPrimaryBiddingPortfolioDtolist()){
//                    CreditLinesInputDto dto = new CreditLinesInputDto();
//                    BeanUtil.copyPropertiesIgnoreNull(dto,creditLinesInputDto);
//                    dto.setTradeId(item.getEntrustId());
//                    if (item.getAllBidPrinAmt() != null){
//                        dto.setTradeAmt(item.getAllBidPrinAmt().subtract(item.getBidPrinAmt() == null ? BigDecimal.ZERO : item.getBidPrinAmt()));
//                    }else {
//                        dto.setTradeAmt(BigDecimal.ZERO);
//                    }
//                    if (dto.getTradeAmt().compareTo(BigDecimal.ZERO) >= 0){
//                        inputDtoList.add(dto);
//                    }
//                }
//                creditLinesInputDto.setInputDtoList(inputDtoList);
//            }
//        }
        //债券借贷
        if (object instanceof TrdBondLendInvestTradeDto){
            TrdBondLendInvestTradeDto inputDto = (TrdBondLendInvestTradeDto)object;
            if (StringUtil.equals(inputDto.getPs(), DictCodeList.回购拆借方向_融入.getValue())){
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
//                creditLinesInputDto.setTradeAmt(ArithmeticUtil.multiply(inputDto.getShareAmt(),new BigDecimal(10000)));
//                creditLinesInputDto.setShareAmt(ArithmeticUtil.multiply(inputDto.getShareAmt(),new BigDecimal(10000)));
                creditLinesInputDto.setTradeAmt(inputDto.getShareAmt());
                creditLinesInputDto.setShareAmt(inputDto.getShareAmt());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                creditLinesInputDto.setTgBusiType(DictCodeListExt2.平安资产类型_债券借贷.getValue());
                creditLinesInputDto.setPsPayment(inputDto.getPs());
                creditLinesInputDto.setPs(DictCodeList.交易方向_买入.getValue());
                creditLinesInputDto.setFinprodId(inputDto.getFinprodId());
                creditLinesInputDto.setTradeStatus(status);
                creditLinesInputDto.setPledgeBondInfoDtoList(inputDto.getBussRepoSec().getPledgeBondList());
                long termDays = DateUtil.getBetweenDayNumber(inputDto.getVdate(),inputDto.getMdate());
                creditLinesInputDto.setTerm(termDays);
                creditLinesInputDto.setMdate(inputDto.getMdate());
                //最新面值
                BigDecimal newFaceValue = creditLinesDealsMapper.getFaceValue(creditLinesInputDto);
                if (newFaceValue == null){
                    newFaceValue = new BigDecimal(100);
                }
                if (creditLinesInputDto.getShareAmt() != null) {
                    creditLinesInputDto.setTradeAmt(newFaceValue.multiply(ArithmeticUtil.divide(creditLinesInputDto.getShareAmt(),100,2)));
                }else if (creditLinesInputDto.getTradeAmt() != null){
                    creditLinesInputDto.setTradeAmt(newFaceValue.multiply(ArithmeticUtil.divide(creditLinesInputDto.getTradeAmt(),100,2)));
                }
                creditLinesInputDto.setFinprodId("");
            }
        }

        // 基金
        if (object instanceof TrdFundInvestTradeDto){
            TrdFundInvestTradeDto inputDto = (TrdFundInvestTradeDto)object;
            if (StringUtil.equals(DictCodeList.交易方向_申购.getValue(),inputDto.getPs())
                    ||StringUtil.equals(DictCodeList.交易方向_认购.getValue(),inputDto.getPs())){
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeStatus(status);
                //最新净值
                BigDecimal lastPrice = creditLinesDealsMapper.getLastPrice(inputDto.getFinprodId(),iPlatformDate.getSysDate());
                if (lastPrice == null){
                    lastPrice = BigDecimal.ONE;
                }
                if("24".equals(inputDto.getType10())){
                    if ("01".equals(inputDto.getTrmFundType())) {
                        lastPrice = new BigDecimal(100);
                    }else if("05".equals(inputDto.getTrmFundType())){
                        lastPrice = new BigDecimal(0.01);
                    }else{
                        lastPrice = BigDecimal.ONE;
                    }
                }
                creditLinesInputDto.setNetPrice(lastPrice);
                creditLinesInputDto.setShareAmt(ArithmeticUtil.divide(creditLinesInputDto.getTradeAmt(),lastPrice,2));
            }
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_买入.getValue())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                creditLinesInputDto.setTradeStatus(status);
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
                List<CreditLinesInputDto> list = new ArrayList<>();
                CreditLinesInputDto dto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(dto,creditLinesInputDto);
                dto.setPs(DictCodeList.交易方向_申购.getValue());
                list.add(dto);
                CreditLinesInputDto dto1 = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(dto1,creditLinesInputDto);
                dto1.setEntrustId(dto1.getTradeId());
                dto1.setPs(DictCodeList.交易方向_申购确认.getValue());
                dto1.setIsjijinmairu("true");
                dto1.setIsSpecial("true");
                list.add(dto1);
                creditLinesInputDto.setInputDtoList(list);
            }
            if (StringUtil.equals(DictCodeListExt2.交易方向_转换赎回申请.getValue(),inputDto.getPs())
                    || StringUtil.equals(DictCodeListExt2.交易方向_转换申请.getValue(),inputDto.getPs())){
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setTradeId(inputDto.getTotleTradeId());
                creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeStatus(status);
                creditLinesInputDto.setPs(DictCodeList.交易方向_申购.getValue());
                creditLinesInputDto.setFinprodId(DictCodeListExt1.金融产品类型_投管用_基金.getValue().concat("_"+inputDto.getChangeToFinprodMarketId()));
                //最新转出净值
                BigDecimal lastPrice = creditLinesDealsMapper.getLastPrice(inputDto.getFinprodId(),iPlatformDate.getSysDate());
                if (lastPrice == null){
                    lastPrice = BigDecimal.ONE;
                }
                creditLinesInputDto.setNetPrice(lastPrice);
                creditLinesInputDto.setTradeAmt(ArithmeticUtil.multiply(creditLinesInputDto.getShareAmt(),lastPrice));
                if (StringUtil.equals(DictCodeListExt2.交易方向_转换申请.getValue(),inputDto.getPs()) && status.equals(DictCodeListExt1.交易状态_撤销.getValue())){
                    creditLinesInputDto.setTradeId(inputDto.getTradeId());
                }
            }
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_申购确认.getValue())
                    ||StringUtil.equals(DictCodeList.交易方向_认购确认.getValue(),inputDto.getPs())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setEntrustId(inputDto.getbTradeId());
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                creditLinesInputDto.setTradeStatus(status);
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
                creditLinesInputDto.setIsSpecial("true");
            }
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回确认.getValue())
                    || StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回到账.getValue())
                    || StringUtil.equals(DictCodeList.交易方向_卖出.getValue(),inputDto.getPs())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                creditLinesInputDto.setTradeStatus(status);
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
            }
            if (StringUtil.equals(inputDto.getPs(),DictCodeListExt2.交易方向_转换确认.getValue()) && status.equals(DictCodeListExt1.交易状态_撤销.getValue())){
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeStatus(status);
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
            }
        }
        // 基金
        if (object instanceof TrdFundInvestTradeConfirmDto){
            TrdFundInvestTradeConfirmDto inputDto = (TrdFundInvestTradeConfirmDto)object;
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回确认.getValue())
                    || StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回到账.getValue())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                creditLinesInputDto.setTradeStatus(status);
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
            }
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_申购确认.getValue())
                    ||StringUtil.equals(DictCodeList.交易方向_认购确认.getValue(),inputDto.getPs())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setEntrustId(inputDto.getbTradeId());
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                creditLinesInputDto.setTradeStatus(status);
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
                creditLinesInputDto.setIsSpecial("true");
            }
            if (StringUtil.equals(inputDto.getPs(),DictCodeListExt2.交易方向_转换赎回确认.getValue())) {
                List<CreditLinesInputDto> inputDtoList = new ArrayList<>();
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setTradeId(inputDto.getTotleTradeId());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeStatus(status);
                // 更新实占转入基金额度
                CreditLinesInputDto dto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(dto,creditLinesInputDto);
                dto.setEntrustId(inputDto.getbTradeId());
                dto.setPs(DictCodeList.交易方向_申购.getValue());
                dto.setFinprodId(DictCodeListExt1.金融产品类型_投管用_基金.getValue().concat("_"+inputDto.getChangeToFinprodMarketId()));
                // 更新实占转入基金额度 =（转出基金份额*单位净值）*风险系数
                BigDecimal feeAmtPay = BigDecimal.ZERO;
                if (inputDto.getFeeAmtPay() != null){
                    feeAmtPay = inputDto.getFeeAmtPay();
                }
                //最新转出基金净值
                BigDecimal lastPriceOut = creditLinesDealsMapper.getLastPrice(inputDto.getFinprodId(),iPlatformDate.getSysDate());
                if (lastPriceOut == null){
                    lastPriceOut = BigDecimal.ONE;
                }
                // 转出基金净值
                dto.setNetPrice(lastPriceOut);
                // 转出基金份额
                dto.setShareAmt(inputDto.getShareAmt());
                // 更新实占转入基金额度 =（转出基金份额*单位净值）*风险系数 - 赎回费
                dto.setTradeAmt(ArithmeticUtil.multiply(dto.getShareAmt(),lastPriceOut).subtract(feeAmtPay));
                inputDtoList.add(dto);
                // 释放转出基金额度
                CreditLinesInputDto dto1 = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(dto1,dto);
                dto1.setFinprodId(inputDto.getFinprodId());
                dto1.setEntrustId("");
                dto1.setPs(DictCodeList.交易方向_赎回确认.getValue());
                inputDtoList.add(dto1);
                // 将转入转出作为list传入接口
                creditLinesInputDto.setInputDtoList(inputDtoList);
            }
        }

        // 计划类货币型
        if (object instanceof TrdPlanCcyInvestTradeDto){
            TrdPlanCcyInvestTradeDto inputDto = (TrdPlanCcyInvestTradeDto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
            }
            creditLinesInputDto.setTradeStatus(status);
            //最新净值
            BigDecimal lastPrice = creditLinesDealsMapper.getLastPrice(inputDto.getFinprodId(),iPlatformDate.getSysDate());
            if (lastPrice == null){
                lastPrice = BigDecimal.ONE;
            }
            creditLinesInputDto.setNetPrice(lastPrice);
            creditLinesInputDto.setShareAmt(ArithmeticUtil.divide(creditLinesInputDto.getTradeAmt(),lastPrice,2));
        }

        if (object instanceof TrdPlanCcyInvestTradeConfirmDto){
            TrdPlanCcyInvestTradeConfirmDto inputDto = (TrdPlanCcyInvestTradeConfirmDto)object;
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回确认.getValue())
                    || StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回到账.getValue())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getbTradeId());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeStatus(status);
                // 多组合和单组合的金额信息和交易编号取值不同
                if (StringUtil.isEmptyList(inputDto.getBussTrdTransfenpeiDto())){
                    creditLinesInputDto.setTradeAmt(inputDto.getTradeAmt());
                    creditLinesInputDto.setShareAmt(inputDto.getShareAmt());
                }else {
                    creditLinesInputDto.setTradeId(inputDto.getTradeId());
                    BigDecimal tradeAmt = BigDecimal.ZERO;
                    BigDecimal shareAmt = BigDecimal.ZERO;
                    for (BussTrdTransfenpeiDto item : inputDto.getBussTrdTransfenpeiDto()){
                        tradeAmt = tradeAmt.add(item.getTradeAmt() == null ?  BigDecimal.ZERO : item.getTradeAmt());
                        shareAmt = shareAmt.add(item.getShareAmt() == null ?  BigDecimal.ZERO : item.getShareAmt());
                    }
                    creditLinesInputDto.setTradeAmt(tradeAmt);
                    creditLinesInputDto.setShareAmt(shareAmt);
                }
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
            }
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_申购确认.getValue())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getbTradeId());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeStatus(status);
                // 多组合和单组合的金额信息和交易编号取值不同
                if (StringUtil.isEmptyList(inputDto.getBussTrdTransfenpeiDto())){
                    creditLinesInputDto.setTradeAmt(inputDto.getTradeAmt());
                    creditLinesInputDto.setShareAmt(inputDto.getShareAmt());
                }else {
                    creditLinesInputDto.setTradeId(inputDto.getTradeId());
                    BigDecimal tradeAmt = BigDecimal.ZERO;
                    BigDecimal shareAmt = BigDecimal.ZERO;
                    for (BussTrdTransfenpeiDto item : inputDto.getBussTrdTransfenpeiDto()){
                        tradeAmt = tradeAmt.add(item.getTradeAmt() == null ?  BigDecimal.ZERO : item.getTradeAmt());
                        shareAmt = shareAmt.add(item.getShareAmt() == null ?  BigDecimal.ZERO : item.getShareAmt());
                    }
                    creditLinesInputDto.setTradeAmt(tradeAmt);
                    creditLinesInputDto.setShareAmt(shareAmt);
                }
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
                creditLinesInputDto.setIsSpecial("true");
            }

        }

        // 计划类净值型
        if (object instanceof TrdPlanNetInvestTradeDto){
            TrdPlanNetInvestTradeDto inputDto = (TrdPlanNetInvestTradeDto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
            creditLinesInputDto.setTradeStatus(status);
            // 多组合和单组合的金额信息和交易编号取值不同
            if (StringUtil.isEmptyList(inputDto.getBussTrdTransfenpeiDto())){
                creditLinesInputDto.setTradeAmt(inputDto.getTradeAmt());
                creditLinesInputDto.setShareAmt(inputDto.getShareAmt());
            }else {
                creditLinesInputDto.setTradeId(inputDto.getTradeId());
                BigDecimal tradeAmt = BigDecimal.ZERO;
                BigDecimal shareAmt = BigDecimal.ZERO;
                for (BussTrdTransfenpeiDto item : inputDto.getBussTrdTransfenpeiDto()){
                    tradeAmt = tradeAmt.add(item.getTradeAmt() == null ?  BigDecimal.ZERO : item.getTradeAmt());
                    shareAmt = shareAmt.add(item.getShareAmt() == null ?  BigDecimal.ZERO : item.getShareAmt());
                }
                creditLinesInputDto.setTradeAmt(tradeAmt);
                creditLinesInputDto.setShareAmt(shareAmt);
            }
            //最新净值
            BigDecimal lastPrice = creditLinesDealsMapper.getLastPrice(inputDto.getFinprodId(),iPlatformDate.getSysDate());
            if (lastPrice == null){
                lastPrice = BigDecimal.ONE;
            }
            creditLinesInputDto.setNetPrice(lastPrice);
            creditLinesInputDto.setShareAmt(ArithmeticUtil.divide(creditLinesInputDto.getTradeAmt(),lastPrice,2));
        }
        //计划类净值型赎回确认
        if (object instanceof TrdPlanNetInvestTradeConfirmDto){
            TrdPlanNetInvestTradeConfirmDto inputDto = (TrdPlanNetInvestTradeConfirmDto)object;
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回确认.getValue())
                    || StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回到账.getValue())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getbTradeId());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeStatus(status);
                // 多组合和单组合的金额信息和交易编号取值不同
                if (StringUtil.isEmptyList(inputDto.getBussTrdTransfenpeiDto())){
                    creditLinesInputDto.setTradeAmt(inputDto.getTradeAmt());
                    creditLinesInputDto.setShareAmt(inputDto.getShareAmt());
                }else {
                    creditLinesInputDto.setTradeId(inputDto.getTradeId());
                    BigDecimal tradeAmt = BigDecimal.ZERO;
                    BigDecimal shareAmt = BigDecimal.ZERO;
                    for (BussTrdTransfenpeiDto item : inputDto.getBussTrdTransfenpeiDto()){
                        tradeAmt = tradeAmt.add(item.getTradeAmt() == null ?  BigDecimal.ZERO : item.getTradeAmt());
                        shareAmt = shareAmt.add(item.getShareAmt() == null ?  BigDecimal.ZERO : item.getShareAmt());
                    }
                    creditLinesInputDto.setTradeAmt(tradeAmt);
                    creditLinesInputDto.setShareAmt(shareAmt);
                }
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
            }
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_申购确认.getValue())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getbTradeId());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeStatus(status);
                // 多组合和单组合的金额信息和交易编号取值不同
                if (StringUtil.isEmptyList(inputDto.getBussTrdTransfenpeiDto())){
                    creditLinesInputDto.setTradeAmt(inputDto.getTradeAmt());
                    creditLinesInputDto.setShareAmt(inputDto.getShareAmt());
                }else {
                    creditLinesInputDto.setTradeId(inputDto.getTradeId());
                    BigDecimal tradeAmt = BigDecimal.ZERO;
                    BigDecimal shareAmt = BigDecimal.ZERO;
                    for (BussTrdTransfenpeiDto item : inputDto.getBussTrdTransfenpeiDto()){
                        tradeAmt = tradeAmt.add(item.getTradeAmt() == null ?  BigDecimal.ZERO : item.getTradeAmt());
                        shareAmt = shareAmt.add(item.getShareAmt() == null ?  BigDecimal.ZERO : item.getShareAmt());
                    }
                    creditLinesInputDto.setTradeAmt(tradeAmt);
                    creditLinesInputDto.setShareAmt(shareAmt);
                }
                //净值
                creditLinesInputDto.setNetPrice(inputDto.getUnitCprice());
                creditLinesInputDto.setIsSpecial("true");
            }
        }
        // 项目类
        if (object instanceof TrdProjectInvestTrade002Dto){
            TrdProjectInvestTrade002Dto inputDto = (TrdProjectInvestTrade002Dto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            creditLinesInputDto.setFinprodType2(DictCodeListExt1.金融产品类型_投管用_项目类资产.getValue());
            creditLinesInputDto.setTradeDate(DateUtil.parse(inputDto.getTradeDate()));
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
            creditLinesInputDto.setTradeStatus(status);
            // 多组合和单组合的金额信息和交易编号取值不同
            if (StringUtil.isEmptyList(inputDto.getBussTrdTransfenpeiDto())){
                creditLinesInputDto.setTradeId(inputDto.getBizCode());
                creditLinesInputDto.setTradeAmt(inputDto.getPrinAmt());
            }else {
                creditLinesInputDto.setTradeId(inputDto.getTradeId());
                BigDecimal tradeAmt = BigDecimal.ZERO;
                for (BussTrdTransfenpeiDto item : inputDto.getBussTrdTransfenpeiDto()){
                    tradeAmt = tradeAmt.add(item.getPrinAmt() == null ?  BigDecimal.ZERO : item.getPrinAmt());
                }
                creditLinesInputDto.setTradeAmt(tradeAmt);
            }
        }
        // 权益类资产
        if (object instanceof TrdProjectNetInvestTradeDto){
            TrdProjectNetInvestTradeDto inputDto = (TrdProjectNetInvestTradeDto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            creditLinesInputDto.setFinprodType2(DictCodeListExt3.金融产品类型_投管用_权益类资产.getValue());
            creditLinesInputDto.setTradeDate(inputDto.getTradeDate());
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
            creditLinesInputDto.setTradeStatus(status);

            if (creditLinesInputDto.getTradeAmt() == null){
                //最新净值
                BigDecimal lastPrice = creditLinesDealsMapper.getLastPrice(inputDto.getFinprodId(),iPlatformDate.getSysDate());
                if (lastPrice == null){
                    lastPrice = BigDecimal.ONE;
                }
                creditLinesInputDto.setNetPrice(lastPrice);
                creditLinesInputDto.setTradeAmt(ArithmeticUtil.multiply(creditLinesInputDto.getShareAmt(),lastPrice));
            }

        }
        // 权益类资产 赎回确认
        if (object instanceof TrdProjectNetInvestTradeConfirmDto){
            TrdProjectNetInvestTradeConfirmDto inputDto = (TrdProjectNetInvestTradeConfirmDto)object;
            if (StringUtil.equals(inputDto.getPs(),DictCodeList.交易方向_赎回确认.getValue())) {
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
                creditLinesInputDto.setFinprodType2(DictCodeListExt3.金融产品类型_投管用_权益类资产.getValue());
                creditLinesInputDto.setTradeDate(inputDto.getTradeDate());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                creditLinesInputDto.setTradeStatus(status);
                if (creditLinesInputDto.getTradeAmt() == null){
                    //最新净值
                    BigDecimal lastPrice = creditLinesDealsMapper.getLastPrice(inputDto.getFinprodId(),iPlatformDate.getSysDate());
                    if (lastPrice == null){
                        lastPrice = BigDecimal.ONE;
                    }
                    creditLinesInputDto.setNetPrice(lastPrice);
                    creditLinesInputDto.setTradeAmt(ArithmeticUtil.multiply(creditLinesInputDto.getShareAmt(),lastPrice));
                }
            }
        }
        // 股票
        if (object instanceof TrdStockDto){
            TrdStockDto inputDto = (TrdStockDto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
            creditLinesInputDto.setTradeStatus(status);
        }
        // 存款
        if (object instanceof TrdDepositInvestTrade001Dto){
            TrdDepositInvestTrade001Dto inputDto = (TrdDepositInvestTrade001Dto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            creditLinesInputDto.setFinprodType2(DictCodeListExt1.金融产品类型_投管用_存款.getValue());
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo002Dto()))
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo002Dto().getCounterId());
            creditLinesInputDto.setTradeStatus(status);
            // 多组合和单组合的金额信息和交易编号取值不同
            if (StringUtil.isEmptyList(inputDto.getBussTrdTransfenpeiDto())){
                creditLinesInputDto.setTradeAmt(inputDto.getPrinAmt());
            }else {
                creditLinesInputDto.setTradeId(inputDto.getTradeId());
                BigDecimal tradeAmt = BigDecimal.ZERO;
                for (BussTrdTransfenpeiDto item : inputDto.getBussTrdTransfenpeiDto()){
                    tradeAmt = tradeAmt.add(item.getPrinAmt());
                }
                creditLinesInputDto.setTradeAmt(tradeAmt);
            }
        }

        // 计划类利率型
        if (object instanceof TrdPlanRateInvestTradeDto){
            TrdPlanRateInvestTradeDto inputDto = (TrdPlanRateInvestTradeDto)object;
            creditLinesInputDto = new CreditLinesInputDto();
            BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
            creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
            creditLinesInputDto.setTradeId(inputDto.getTradeId() == null ? inputDto.getTotleTradeId() : inputDto.getTradeId());
            creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
            if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo002Dto()))
                creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo002Dto().getCounterId());
            creditLinesInputDto.setTradeStatus(status);
            creditLinesInputDto.setTradeAmt(inputDto.getPrinAmt());
        }
        // 质押式回购
        if (object instanceof TrdRepoTrade002Dto){
            TrdRepoTrade002Dto inputDto = (TrdRepoTrade002Dto)object;
            if (StringUtil.equals(inputDto.getPs(), DictCodeList.回购拆借方向_逆回购.getValue())){
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setTradeAmt(inputDto.getPrinAmt());
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
                creditLinesInputDto.setFinprodType2(DictCodeListExt1.金融产品类型_投管用_债券质押式逆回购.getValue());
                //质押式回购交易授信标签  按无标的处理
                //creditLinesInputDto.setFinprodId(creditLinesInputDto.getTradeId());
                creditLinesInputDto.setTgBusiType(DictCodeList.平安资产类型_质押式回购.getValue());
                creditLinesInputDto.setPsPayment(inputDto.getRepoType());
                creditLinesInputDto.setPs(DictCodeList.交易方向_买入.getValue());
                creditLinesInputDto.setSaleCode(inputDto.getPs());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo()))
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                creditLinesInputDto.setTradeStatus(status);
                // modify by etliu 20210128 start
//                List<PledgeBondInfoDto> pledgeBondInfoDtoList = inputDto.getBussRepoSec().getPledgeBondList();
                List<PledgeBondInfoDto> pledgeBondInfoDtoList = null;
                if (inputDto.getBussRepoSec() != null) {
                    pledgeBondInfoDtoList = inputDto.getBussRepoSec().getPledgeBondList();
                }
                // modify by etliu 20210128 end
                long termDays = DateUtil.getBetweenDayNumber(inputDto.getVdate(),inputDto.getMdate());
                creditLinesInputDto.setTerm(termDays);
                creditLinesInputDto.setRealTerm(inputDto.getTerm());
                creditLinesInputDto.setMdate(inputDto.getMdate());
                if (!StringUtil.isEmptyList(pledgeBondInfoDtoList)){
                    List<String> assetTypeList = Arrays.asList(CacheUtil.getSysParam(BussinessConstant.CREDIT_ASSET_TYPE_F03).split(","));
                    // 质押券面总额
                    //BigDecimal totalAmt = pledgeBondInfoDtoList.stream().map(PledgeBondInfoDto::getPledgePrin).reduce(BigDecimal.ZERO,BigDecimal::add);
                    BigDecimal totalAmt= BigDecimal.ZERO;
                    // 需要扣减额度的券面总额
                    BigDecimal supAmt = BigDecimal.ZERO;
                    for (PledgeBondInfoDto pledgeBondInfoDto : pledgeBondInfoDtoList) {
                        BigDecimal divide = ArithmeticUtil.divide(ArithmeticUtil.multiply(pledgeBondInfoDto.getPledgePrin(), pledgeBondInfoDto.getPledgeRatio()), new BigDecimal(100), 2);
                        totalAmt = ArithmeticUtil.doAdd(totalAmt,divide);
                        if(riskCreditListCommonValidatorService.checkCallCredit(pledgeBondInfoDto.getFinprodId())){
                            supAmt = ArithmeticUtil.doAdd(supAmt, divide);
                        }
                    }
                    /*BigDecimal supAmt = pledgeBondInfoDtoList.stream().filter(item ->{
                        // 查询资产类型
                        FinProductTypeKey key = new FinProductTypeKey();
                        key.setFinprodId(item.getFinprodId());
                        key.setBranch(0L);
                        FinProductType finProductType = creditLinesDealsMapper.selectByPrimaryKey(key);
                        //FinProductAdd finProductAdd = finProductAddMapper.selectByPrimaryKey(item.getFinprodId());
                        String astType = finProductType.getType10();
                        if (StringUtil.equals(finProductAdd.getIsSecBond(),DictCodeList.是否_是.getValue())){
                            astType = astType.concat("_") + DictCodeList.是否_是.getValue();
                        }
                        // 资产类型在【不扣减授信参数】列表
                        if (assetTypeList.contains(astType)){
                            return false;
                        }
                        return true;
                    }).map(PledgeBondInfoDto::getPledgePrin).reduce(BigDecimal.ZERO,BigDecimal::add);*/

                    BigDecimal tradeAmt = supAmt.divide(totalAmt, RoundingMode.HALF_UP).multiply(inputDto.getPrinAmt()).setScale(2,RoundingMode.HALF_UP);
                    creditLinesInputDto.setTradeAmt(tradeAmt);

                }
            }
        }
        // 拆借
        if (object instanceof TrdLendInvestTradeDto){
            TrdLendInvestTradeDto inputDto = (TrdLendInvestTradeDto)object;
            if (StringUtil.equals(inputDto.getBusiPs(), DictCodeList.回购拆借方向_拆出.getValue())){
                creditLinesInputDto = new CreditLinesInputDto();
                creditLinesInputDto.setTradeId(inputDto.getTradeId());
                creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
                creditLinesInputDto.setFinprodId("");
                creditLinesInputDto.setTradeDate(inputDto.getTradeDate());
                creditLinesInputDto.setTradeStatus(status);
                creditLinesInputDto.setFinprodType2(DictCodeListExt1.金融产品类型_投管用_拆出.getValue());
                creditLinesInputDto.setTgBusiType(DictCodeList.平安资产类型_拆借.getValue());
                creditLinesInputDto.setPsPayment(inputDto.getBusiPs());
                creditLinesInputDto.setPs(DictCodeList.交易方向_买入.getValue());
                if(StringUtil.isNotEmpty(inputDto.getTrdInvestPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdInvestPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeAmt(inputDto.getFinTradeProduct().getPrin());
                creditLinesInputDto.setShareAmt(inputDto.getFinTradeProduct().getPrin());
            }
        }
        // 买断式回购
        if (object instanceof TrdOutRightRepoTrade002Dto){
            TrdOutRightRepoTrade002Dto inputDto = (TrdOutRightRepoTrade002Dto)object;
            if (StringUtil.equals(inputDto.getPs(), DictCodeList.回购拆借方向_逆回购.getValue())){
                // 先判断标的券类型是否需要扣减授信额度
                List<String> assetTypeList = Arrays.asList(CacheUtil.getSysParam(BussinessConstant.CREDIT_ASSET_TYPE_F03).split(","));
                // 查询资产类型
                FinProductTypeKey key = new FinProductTypeKey();
                key.setFinprodId(inputDto.getFinprodId());
                key.setBranch(0L);
                FinProductType finProductType = creditLinesDealsMapper.selectByPrimaryKey(key);
                FinProductAdd finProductAdd = finProductAddMapper.selectByPrimaryKey(inputDto.getFinprodId());
                String astType = finProductType.getType10();
                if (StringUtil.equals(finProductAdd.getIsSecBond(),DictCodeList.是否_是.getValue())){
                    astType = astType.concat("_") + DictCodeList.是否_是.getValue();
                }
                // 资产类型在【不扣减授信参数】列表
                if (assetTypeList.contains(astType)){
                    return null;
                }
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setEntrustId(inputDto.getEntrustId());
                creditLinesInputDto.setTradeStatus(status);
                creditLinesInputDto.setTgBusiType(DictCodeList.平安资产类型_买断式回购.getValue());
                creditLinesInputDto.setPs(DictCodeList.交易方向_买入.getValue());
                creditLinesInputDto.setPsPayment(inputDto.getPs());
                creditLinesInputDto.setFinprodId(inputDto.getFinprodId());
                if(StringUtil.isNotEmpty(inputDto.getTrdOutRightRepoPersonInfo())){
                    creditLinesInputDto.setCounterId(inputDto.getTrdOutRightRepoPersonInfo().getCounterId());
                }
                creditLinesInputDto.setTradeId(inputDto.getTradeId());
                creditLinesInputDto.setTradeAmt(inputDto.getSecTotalPrin());
                //最新面值
                BigDecimal newFaceValue = creditLinesDealsMapper.getFaceValue(creditLinesInputDto);
                if (newFaceValue == null){
                    newFaceValue = new BigDecimal(100);
                }
                if (creditLinesInputDto.getShareAmt() != null) {
                    creditLinesInputDto.setTradeAmt(newFaceValue.multiply(creditLinesInputDto.getShareAmt()));
                }else if (creditLinesInputDto.getTradeAmt() != null){
                    creditLinesInputDto.setTradeAmt(newFaceValue.multiply(ArithmeticUtil.divide(creditLinesInputDto.getTradeAmt(),100,2)));
                }
                creditLinesInputDto.setFinprodId("");
                List<CreditLinesInputDto> inputDtoList = new ArrayList<>();
                if("01".equals(status)){
                    CreditLinesInputDto dto1 = new CreditLinesInputDto();
                    BeanUtil.copyPropertiesIgnoreNull(dto1,creditLinesInputDto);
                    inputDtoList.add(dto1);

                    CreditLinesInputDto dto2 = new CreditLinesInputDto();
                    BeanUtil.copyPropertiesIgnoreNull(dto2,creditLinesInputDto);
                    dto2.setZhiYaQuan(inputDto.getFinprodId());
                    inputDtoList.add(dto2);
                    creditLinesInputDto.setInputDtoList(inputDtoList);
                }
            }
        }
        // 手工交收
        if (object instanceof ManualSettleDto){
            ManualSettleDto inputDto = (ManualSettleDto)object;
            if (DictCodeListExt3.交收状态_交收失败.getValue().equals(inputDto.getPageModel())){
                creditLinesInputDto = new CreditLinesInputDto();
                BeanUtil.copyPropertiesIgnoreNull(creditLinesInputDto,inputDto);
                creditLinesInputDto.setCreditCallType(DictCodeListRsk.授信占用类别_实占.getValue());
                creditLinesInputDto.setTradeId(inputDto.getOrderId());
                creditLinesInputDto.setTradeStatus(status);
                creditLinesInputDto.setTradeAmt(inputDto.getSettleAmt());
                creditLinesInputDto.setShareAmt(inputDto.getSettleNum());
                creditLinesInputDto.setTradeDate(inputDto.getTradeDate());
                creditLinesInputDto.setPs(DictCodeList.交易方向_买入.getValue());
                creditLinesInputDto.setFinprodType2("ManualSettle");
            }
        }
        boolean isCallCreditInterf = checkCallCreditInterf(creditLinesInputDto,status,method);
        if (isCallCreditInterf){
            return creditLinesInputDto;
        }
        return null;
    }

    private boolean checkCallCreditInterf(CreditLinesInputDto creditLinesInputDto, String status, String method) {
        if (creditLinesInputDto == null){
            return false;
        }
        // 买入、申购，提交到流程中，占用授信
        if ("doInsert".equals(method) || "doUpdate".equals(method)){
            if (DictCodeList.交易方向_买入.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeList.交易方向_申购.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeListExt2.交易方向_认购.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeListExt2.交易方向_配股.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeListExt2.交易方向_转换赎回申请.getValue().equals(creditLinesInputDto.getPs())){
                return true;
            }
        }
        if ("doComplete".equals(method)){
            // 撤销生效时，下列交易方向调用
            if (DictCodeListExt1.交易状态_撤销.getValue().equals(status)){
                if (DictCodeList.交易方向_买入.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeList.交易方向_卖出.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeList.交易方向_申购.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeList.交易方向_申购确认.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeListExt2.交易方向_认购.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeList.交易方向_认购确认.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeListExt2.交易方向_配股.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeList.交易方向_赎回确认.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeListExt2.交易方向_转换申请.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeListExt2.交易方向_转换确认.getValue().equals(creditLinesInputDto.getPs())){
                    return true;
                }
            }else {
                // 交易生效时，下列交易方向生效
                if (DictCodeList.交易方向_卖出.getValue().equals(creditLinesInputDto.getPs())){
                    return true;
                }
                // 宁波个性化，基金赎回确认时就要释放额度

                if (DictCodeList.交易方向_赎回确认.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeListExt2.交易方向_转换赎回确认.getValue().equals(creditLinesInputDto.getPs())){
                    return true;
                }
                if(DictCodeList.交易方向_申购确认.getValue().equals(creditLinesInputDto.getPs())
                        || DictCodeList.交易方向_认购确认.getValue().equals(creditLinesInputDto.getPs()))return true;

            }
        }
        // 买入、申购，退回时，释放授信
        if ("doBack".equals(method)){
            //add by meifaxin 20211122 撤销交易 审批退回  不释放授信 start
            if(selectTrade(creditLinesInputDto.getTradeId())!=null){ //区分新建和撤销交易退回
                return false;
            }
            //add by meifaxin 20211122 撤回交易 审批退回  不释放授信 end
            if (DictCodeList.交易方向_买入.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeList.交易方向_申购.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeList.交易方向_申购确认.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeListExt2.交易方向_配股.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeListExt2.交易方向_认购.getValue().equals(creditLinesInputDto.getPs())){
                return true;
            }
        }
        if ("check".equals(method)){
            if (DictCodeList.交易方向_买入.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeList.交易方向_申购.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeListExt2.交易方向_认购.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeListExt2.交易方向_配股.getValue().equals(creditLinesInputDto.getPs())
                    || DictCodeListExt2.交易方向_转换赎回申请.getValue().equals(creditLinesInputDto.getPs())){
                return true;
            }
        }
        return false;
    }

    /**
     * 查询生效业务模式字段
     * @param entrustId
     * @return
     */
    public String findEtrWordInfo(String entrustId) {
        return restTemplate.postForObject(ServiceApplication.TRADE_SERVICE_NAME + "/common/trdcommonbase/findEtrWordInfo", entrustId,
                String.class);
    }

    /**
     *
     * @param paramMap
     * @return
     */

    public Map<String,Object>  getSppiInfoByY1(Map<String, Object> paramMap){
        Map<String,Object> result = new HashMap<>();
        String finprodId = paramMap.get("finprodId").toString();
        if(!StringUtil.isNotEmpty(paramMap.get("price"))){
            result.put("result","0");
            return result;
        }
        BigDecimal price = new BigDecimal(paramMap.get("price").toString());
        Date platDate = DateUtil.platDateFromCache();

        Boolean sinal = true;
        //1 查询资产是否有行权计划
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("finprodId", finprodId);
        param.put("psT03", DictCodeList.交易方向_行权_回售.getValue());
        param.put("cashType2", DictCodeListExt1.现金流二级类型_本金.getValue());
        param.put("execType01", DictCodeList.行权状态_未行权.getValue());
        param.put("execType02", DictCodeList.行权状态_已行权.getValue());
        List<FinExercisePlan> finExercisePlanList = busiFinExercisePlanService.getfinExercisePlanByProductId(param);
        if(finExercisePlanList==null||finExercisePlanList.size()==0){
            result.put("result","0");
            return result;
        }else if((StringUtil.equals(finExercisePlanList.get(0).getExerciseType(),"01")||StringUtil.equals(finExercisePlanList.get(0).getExerciseType(),"02"))&&DateUtil.compareDate(platDate,finExercisePlanList.get(0).getActExerciseDate())<=0){

        }else{
            result.put("result","0");
            return result;
        }

        //2 查询sppi中债中的使权不重大的购入价条件（小于第二个数字）
        String bidPriceCon = restTemplate.getForObject(ServiceApplication.ASSET_SERVICE_NAME + "/SppiTest001/getSppiInfoByY1?finprodId="
                + finprodId, String.class);
        if(StringUtil.isNotEmpty(bidPriceCon)) {
            try{
                bidPriceCon = bidPriceCon.split(",")[1];
            }catch (Exception e){
                result.put("result","1");
                return result;
            }
            BigDecimal bidPriceCon2 = new BigDecimal(bidPriceCon);
            if (price.compareTo(bidPriceCon2) < 0) {
                result.put("result","2");
                result.put("bidPriceCon",bidPriceCon2);
                return result;
            }
        }
        result.put("result","0");
        return result;
    }

    /**
     * 根据金融产品代码查询金融产品信息
     * @param finprodId 金融产品代码
     * @return 金融产品信息
     */
    public FinProduct selectFinProduct(String finprodId) {
        return finProductMapper.selectByPrimaryKey(finprodId);
    }
    /**
     * 查询交易
     * @param tradeId 交易id
     * @return 交易信息
     */
    public TrdProductDeal selectTrade(String tradeId) {
        return trdProductDealMapper.selectByPrimaryKey(tradeId);
    }
}
