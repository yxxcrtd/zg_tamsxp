/*
 * 文件名：TrdRepoTradeService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：裴家俊
 * 修改时间：2018年12月18日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
package com.joyintech.service.trade.trdrepotrade.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.service.trade.trdrepotrade.dto.*;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.invest.common.dto.cstp.EtrEntrustExt;
import com.joyintech.tams.invest.common.dto.cstp.InterfTradRepoasstDto;
import com.joyintech.tams.invest.common.pagemenusplit.PageMenuSplitUtil;
import com.joyintech.tams.systemtools.dto.CommonAjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.service.trade.bond.dao.TrdBondInvestTradeMapper;
import com.joyintech.service.trade.cstp.dao.CstpSelectMapper;
import com.joyintech.service.trade.trdrepotrade.dao.TrdRepoTradeMapper;
import com.joyintech.tams.common.base.dao.FinProductMapper;
import com.joyintech.tams.common.base.dao.FinTradeProductMapper;
import com.joyintech.tams.common.base.dao.InterfOpicsTradRepoMapper;
import com.joyintech.tams.common.base.dao.MstCounterPartyMapper;
import com.joyintech.tams.common.base.dao.StlSecAcctInfoMapper;
import com.joyintech.tams.common.base.dao.TrdProductDealMapper;
import com.joyintech.tams.common.base.dao.TrdTradeFeeDetailMapper;
import com.joyintech.tams.common.base.entity.EtrFinishStatus;
import com.joyintech.tams.common.base.entity.FinProduct;
import com.joyintech.tams.common.base.entity.FinTradeProduct;
import com.joyintech.tams.common.base.entity.FinTradeProductKey;
import com.joyintech.tams.common.base.entity.InterfOpicsTradRepo;
import com.joyintech.tams.common.base.entity.InterfTradRepoasst;
import com.joyintech.tams.common.base.entity.MstCounterParty;
import com.joyintech.tams.common.base.entity.PtlSecPositionFlow;
import com.joyintech.tams.common.base.entity.TrdMatchInfo;
import com.joyintech.tams.common.base.entity.TrdProductDeal;
import com.joyintech.tams.common.base.entity.TrdTradeFeeDetail;
import com.joyintech.tams.common.base.entity.TrdTradeFeeDetailKey;
import com.joyintech.tams.common.base.entity.WflTrdProductDeal;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.component.base.entrust.BaseEtrInvestInfoService;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;
import com.joyintech.tams.invest.common.dto.BaseEtrInvestInfoDto;
import com.joyintech.tams.invest.common.dto.common.PledgeBondInfoDto;
import com.joyintech.tams.invest.common.trade.service.BussRepoSecService;
import com.joyintech.tools.collectors.JoyinCollectors;
/**
 * 回购交易微服务Service
 */
@Service
public class TrdRepoTradeService extends BaseService<TrdRepoTradeDto> {

    /**
     * 投资委托基础组件
     */
    @Autowired(required = false)
    private BaseEtrInvestInfoService baseEtrInvestInfoService;

    /**
     * FinProductMapper
     */
    @Autowired(required = false)
    private FinProductMapper finProductMapper;

    /**
     * 回购资产交易微服务TrdRepoTradeMapper
     */
    @Autowired
    private TrdRepoTradeMapper trdRepoTradeMapper;

    @Autowired
    private TrdProductDealMapper trdProductDealMapper;

    @Autowired
    private MstCounterPartyMapper mstCounterPartyMapper;

    @Autowired
    private FinTradeProductMapper finTradeProductMapper;

    /**
     * TrdTradeFeeDetailMapper
     */
    @Autowired
    private TrdTradeFeeDetailMapper trdTradeFeeDetailMapper;
    @Autowired
    private TrdBondInvestTradeMapper trdBondInvestTradeMapper;
    @Autowired
    private StlSecAcctInfoMapper stlSecAcctInfoMapper;
    @Autowired
    private InterfOpicsTradRepoMapper interfOpicsTradRepoMapper;
    @Autowired
    private BussRepoSecService bussRepoSecService;
    @Autowired
    private CstpSelectMapper cstpSelectMapper;

    /**
     * 回购资产交易查询画面，查询回购资产交易信息列表
     *
     * @param page      page
     * @param length    length
     * @param pageParam 参数
     * @return Page<TrdRepoTradeDto>
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    public Page<TrdRepoTradeDto> selectForPage(int page, int length,
                                               Map<String, Object> pageParam)
    {
        pageParam.put("YES_NO_YES", DictCodeList.是否_是.getValue());
        pageParam.put("YES_NO_NO", DictCodeList.是否_否.getValue());

        pageParam.put("VALID_STATUS_01", DictCodeListExt1.交易流程状态_流程中.getValue());
        pageParam.put("VALID_STATUS_02", DictCodeListExt1.交易流程状态_有效.getValue());
        pageParam.put("VALID_STATUS_03", DictCodeListExt1.交易流程状态_撤销.getValue());

        pageParam.put("FINPROD_TYPE_TG_F02", DictCodeListExt1.金融产品类型_投管用_债券质押式正回购.getValue());
        pageParam.put("FINPROD_TYPE_TG_F03", DictCodeListExt1.金融产品类型_投管用_债券质押式逆回购.getValue());

        pageParam.put("TRADE_STATUS_01", DictCodeListExt1.交易状态_初始.getValue());
        pageParam.put("TRADE_STATUS_03", DictCodeListExt1.交易状态_撤销.getValue());
        pageParam.put("PINAN_BUSINESS_TYPE_19", DictCodeListExt1.平安业务类型_逆回购.getValue());
        pageParam.put("PINAN_BUSINESS_TYPE_20", DictCodeListExt1.平安业务类型_正回购.getValue());
        pageParam.put("TRADE_STATUS_01", DictCodeListExt1.交易状态_初始.getValue());
        pageParam.put("TRADE_STATUS_03", DictCodeListExt1.交易状态_撤销.getValue());

        pageParam.put("ps_T22", BussinessDictCodeList.交易方向_收息到账.getValue());
        pageParam.put("ps_T23", BussinessDictCodeList.交易方向_还本到账.getValue());
        
        //update by zhangnian start 20200318
        //有回购类型查询条件是 不显示银行间的数据
        pageParam.put("TRADE_PLACE_01",DictCodeListExt1.交易场所_银行间.getValue());
        //update by zhangnian end   20200318

        // add by etliu 20210501 银行间场内场外拆分 start
        pageParam.put("defaultTradeMarketList", PageMenuSplitUtil.getTradeMarketList((String) pageParam.get("pageId")));
        // add by etliu 20210501 银行间场内场外拆分 end

        Page<TrdRepoTradeDto> queryPage = this.queryForPage(page, length,() -> this.trdRepoTradeMapper.selectForPage(pageParam));
        List<TrdRepoTradeDto> list = queryPage.getRoot();
        if (list != null && list.size() > 0) {
			for (TrdRepoTradeDto trd : list) {
				// 根据金融产品代码获取债券名称
                String tradeId = trd.getrTradeId();
                if(StringUtil.isEmpty(tradeId)){
                    tradeId = trd.getTradeId();
                }

				List<String> bondNames = trdRepoTradeMapper.getBondNames(tradeId);
				String bondName = "";
				if (bondNames != null && bondNames.size() > 0) {
					for (int i = 0; i < bondNames.size(); i++) {
						if (i == 0) {
							bondName = bondNames.get(i);
						}else{
							bondName = bondName + "," + bondNames.get(i);
						}
					}
				}
				// 设置交易名称
				trd.setBondName(bondName);
			}
			queryPage.setRoot(list);
		}
        
        return queryPage;
    }

    /**
     * 根据交易编号检索组合持仓流水（持仓类型为实际）
     *
     * @param bizCode 交易编号
     * @return List<PtlSecPositionFlowDto>
     * @see
     * @since 1.0
     */
    public List<PtlSecPositionFlow> selectPtlSecPositionFlow(String bizCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("bizCode", bizCode);
        param.put("POSITION_TYPE_04", DictCodeList.持仓类型_质押.getValue());
        return this.trdRepoTradeMapper.selectPtlSecPositionFlow(param);
    }

    /**
     * 根据工作流代码查询交易编号
     *
     * @param workflowId 工作流代码
     * @return 交易编号
     * @see
     * @since 1.0
     */
    public String selectTradeIdByWorkflowId(String workflowId) {
        return this.trdRepoTradeMapper.selectTradeIdByWorkflowId(workflowId);
    }

    /**
     * 根据交易编号查询流程中数据是否存在
     *
     * @param tradeId 交易编号
     * @return 流程中数据是否存在
     * @see
     * @since 1.0
     */
    public Boolean isInFlowDataExisted(String tradeId) {
        return this.trdRepoTradeMapper.selectInFlowDataCount(tradeId) > 0;
    }

    /**
     * 根据交易编号查询该交易是否已被撤销
     *
     * @param tradeId 交易编号
     * @return 该交易是否已被撤销
     * @see
     * @since 1.0
     */
    public Boolean isAlreadyRevoked(String tradeId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tradeId", tradeId);
        paramMap.put("YES_NO_YES", DictCodeList.是否_是.getValue());
        paramMap.put("TRADE_STATUS_03", DictCodeListExt1.交易流程状态_撤销.getValue());
        return this.trdRepoTradeMapper.selectRevokedDataCount(paramMap) > 0;
    }

    /**
     * 回购资产交易新建画面，查看模式：查询回购资产交易信息
     *
     * @param param param
     * @return 回购资产交易信息
     * @see
     * @since 1.0
     */
    public TrdRepoTradeReferenceDto getTrdRepoTradeDto(Map<String, Object> param) {
        param.put("TRADE_STATUS_01", DictCodeListExt1.交易状态_初始.getValue());
        param.put("TRADE_STATUS_03", DictCodeListExt1.交易状态_撤销.getValue());
        TrdRepoTradeReferenceDto resultDto = this.trdRepoTradeMapper.getTrdRepoTradeDto(param);
        // 清算速度
        if (!StringUtil.isNotEmpty(resultDto) || resultDto.getSettleSpeed() == null || resultDto.getSettleSpeed() == 0L) {
            resultDto.setSettleSpeedStr(DictCodeList.清算速度_T_0.getValue());
        } else {
            resultDto.setSettleSpeedStr(DictCodeList.清算速度_T_1.getValue());
        }
        // 交易手续费
        TrdTradeFeeDetailKey trdTradeFeeDetailKey = new TrdTradeFeeDetailKey();
        trdTradeFeeDetailKey.setTradeId(resultDto.getTradeId());
        trdTradeFeeDetailKey.setFeeType(BussinessDictCodeList.费用类型_交易手续费.getValue());
        TrdTradeFeeDetail trdTradeFeeDetail = trdTradeFeeDetailMapper.selectByPrimaryKey(trdTradeFeeDetailKey);
        if (trdTradeFeeDetail != null) {
            resultDto.setProcedureAmt(trdTradeFeeDetail.getFeeAmt());
        }

        // 结算费
        trdTradeFeeDetailKey.setFeeType(BussinessDictCodeList.费用类型_结算手续费.getValue());
        trdTradeFeeDetail = trdTradeFeeDetailMapper.selectByPrimaryKey(trdTradeFeeDetailKey);
        if (trdTradeFeeDetail != null) {
            resultDto.setSettleAmt(trdTradeFeeDetail.getFeeAmt());
        }

        // 佣金
        trdTradeFeeDetailKey.setFeeType(BussinessDictCodeList.费用类型_佣金.getValue());
        trdTradeFeeDetail = trdTradeFeeDetailMapper.selectByPrimaryKey(trdTradeFeeDetailKey);
        if (trdTradeFeeDetail != null) {
            resultDto.setCommissionAmt(trdTradeFeeDetail.getFeeAmt());
        }
        return resultDto;
    }

    /**
     * 根据组合代码查询证券托管户：联想框文本框
     *
     * @param param 画面参数
     * @return 证券托管户：联想框文本框
     * @see
     * @since 1.0
     */
    public List<SuggestDto> getSecManageAcctId(Map<String, Object> param) {
        Object finprodIdTemp = param.get("finprodId");
        Object finprodName = param.get("finprodName");
        String finProdType = DictCodeListExt1.金融产品类型_投管用_债券.getValue();
        if(StringUtil.isNotEmpty(finprodName)){
            param.put("finprodName",finprodName);
        }else{
            if(StringUtil.isNotEmpty(finprodIdTemp) && !StringUtil.equals(finprodIdTemp.toString().substring(0, 3), "F01")){
                param.put("finprodName",finProdType.concat(GlobalConstant.UNDER_LINE).concat(finprodIdTemp.toString()));
            }else{
                param.put("finprodName",finprodIdTemp);
            }
        }
        return this.queryForSuggest(() -> this.trdRepoTradeMapper.getSecManageAcctId(param),
                null);
    }

    /**
     * 查询投资委托信息
     *
     * @param entrustId 委托代码
     * @return 投资委托信息
     * @see
     * @since 1.0
     */
    public BaseEtrInvestInfoDto getEntrustInfo(String entrustId) {
        if (baseEtrInvestInfoService == null) {
            return new BaseEtrInvestInfoDto();
        }
        BaseEtrInvestInfoDto resultDto = baseEtrInvestInfoService.queryActiveMandate(entrustId);
        if (resultDto != null && resultDto.getEtrEntrust() != null) {
            // 委托数量中扣除已完成额度
            /*if (!StringUtil.isEmptyList(resultDto.getEtrFinishStatusList())) {
                BigDecimal 已完成额度 = BigDecimal.ZERO;
                for (EtrFinishStatus etrFinishStatus : resultDto.getEtrFinishStatusList()) {
                    已完成额度 = ArithmeticUtil.doAdd(etrFinishStatus.getFinishAmt(),已完成额度);
                }

                resultDto.getEtrEntrust().setEntrustAmt(
                        ArithmeticUtil.subtract(resultDto.getEtrEntrust().getEntrustAmt(), 已完成额度));
            }*/
            // 查询金融产品名称
            FinProduct finProduct = finProductMapper.selectByPrimaryKey(resultDto.getEtrEntrust().getFinprodId());
            if (finProduct != null) {
                resultDto.setFinprodName(finProduct.getFinprodName());
            }
        }
        if (resultDto != null && resultDto.getEtrEntrustAdd() != null) {
            // 清算速度
            if (resultDto.getEtrEntrustAdd().getSettleSpreed() == null
                    || resultDto.getEtrEntrustAdd().getSettleSpreed() == 0L) {
                resultDto.setSettleSpeedStr(DictCodeList.清算速度_T_0.getValue());
            } else {
                resultDto.setSettleSpeedStr(DictCodeList.清算速度_T_1.getValue());
            }
        }
        return resultDto;
    }

    /**
     * 组合代码、证券托管户、交易日、到期日改变时，查询可使用标准券额度
     *
     * @param params params
     * @return 可使用标准券额度
     * @see
     * @since 1.0
     */
    public BigDecimal getAvaStandardStockPosition(Map<String, Object> params) {
        params.put("LIBRARY_DIRECTION_01", DictCodeList.出入库方向_入库.getValue());
        params.put("LIBRARY_DIRECTION_02", DictCodeList.出入库方向_出库.getValue());
        BigDecimal pDecimal = new BigDecimal(0);
        if (StringUtil.isNotEmpty(this.trdRepoTradeMapper.getAvaStandardStockPosition(params))) {
            pDecimal = this.trdRepoTradeMapper.getAvaStandardStockPosition(params);
        }
        return pDecimal;
    }

    /**
     * 查询副总裁/总裁助理授权额度
     *
     * @param params params
     * @return 可使用标准券额度
     * @see
     * @since 1.0
     */
    public BigDecimal getVPOrPAuthAmount(Map<String, Object> params) {
        return this.trdRepoTradeMapper.getVPOrPAuthAmount(params);
    }

    /**
     * 查询正回购交易质押券信息
     *
     * @param selectParam selectParam
     * @return 正回购交易质押券信息
     * @see
     * @since 1.0
     */
    public List<TrdRepoTradeExportMailDto> selectFinDealPledge(Map<String, Object> selectParam) {
        selectParam.put("tradeIds", StringUtil.splitToList(GlobalConstant.COMMA,
                (String) selectParam.get("tradeId")));
        List<TrdRepoTradeExportMailDto> lstResult = new ArrayList<TrdRepoTradeExportMailDto>();

        List<Map<String, Object>> lstFinDealPledge = this.trdRepoTradeMapper.selectFinDealPledge(selectParam);
        Map<String, List<Map<String, Object>>> finDealPledgeMap = lstFinDealPledge.stream().
                collect(JoyinCollectors.groupingBy(x ->
                        (String) x.get("tradeId")
                ));

        int maxPledgeCount = 0;
        for (Map.Entry<String, List<Map<String, Object>>> entry : finDealPledgeMap.entrySet()) {
            TrdProductDeal trdProductDeal = trdProductDealMapper.selectByPrimaryKey(entry.getKey());
            MstCounterParty mstCounterParty = mstCounterPartyMapper.selectByPrimaryKey(trdProductDeal.getCounterId());
            FinTradeProductKey key = new FinTradeProductKey();
            key.setFinprodId(entry.getKey());
            key.setBranch(Long.valueOf((Integer) selectParam.get("branchNo")));
            FinTradeProduct finTradeProduct = finTradeProductMapper.selectByPrimaryKey(key);

            TrdRepoTradeExportMailDto result = new TrdRepoTradeExportMailDto();
            
            // 交易对手列显示交易对手和对手方交易员，以空格隔开；
            result.setCounter(mstCounterParty.getCounterName() + " " + (trdProductDeal.getCounterTrader() == null? "":trdProductDeal.getCounterTrader()));
            // 回购期限显示时，带上单位天
            result.setTermDay(finTradeProduct.getTerm() + "天");
            // 回购利率显示为百分数
            result.setRebackPrice(ArithmeticUtil.multiply(finTradeProduct.getIntRate(),
                    new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP).toPlainString() + "%");
            // 交易金额显示本金金额
            DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,##0");
            result.setPrinAmt(df2.format(ArithmeticUtil.round(finTradeProduct.getPrin(), 0,
                    RoundingMode.HALF_UP)));
            // 起息日、到期日显示为日期格式
            result.setVdate(DateUtil.format(finTradeProduct.getVdate(), DateUtil.DEFAULT_ANOTHER_FORMAT));
            result.setMdate(DateUtil.format(finTradeProduct.getMdate(), DateUtil.DEFAULT_ANOTHER_FORMAT));

            // 质押券信息List
            List<String> lstFinDealPledgeResult = new ArrayList<>();
            for (Map<String, Object> finDealPledge : entry.getValue()) {
                lstFinDealPledgeResult.add(finDealPledge.get("bondCode")
                        + " " + finDealPledge.get("bondAbbrName")
                        + ","
                        + (finDealPledge.get("prinAmt") == null ? 0 :
                        ArithmeticUtil.divide((BigDecimal) finDealPledge.get("prinAmt"),
                                new BigDecimal("100000000"), 8))
                        + "亿,折扣率" + (finDealPledge.get("ratio") == null ? 0 :
                        ArithmeticUtil.multiply((BigDecimal) finDealPledge.get("ratio"),
                                new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP).toPlainString() + "%"));
            }
            if (maxPledgeCount < entry.getValue().size()) {
                maxPledgeCount = entry.getValue().size();
            }
            result.setLstFinDealPledgeResult(lstFinDealPledgeResult);
            lstResult.add(result);
        }
        for (TrdRepoTradeExportMailDto a : lstResult) {
            a.setMaxPledgeCount(maxPledgeCount);
        }
        return lstResult;
    }



    /**
     * 查询债券cstp交易下拉框
     * @param param 输入参数
     * @return
     */
    public Page<SelectOptionDto> queryRepoCstpInfoList(int page, int length, Map<String, Object> param){
        param.put("tradeStatusInit", BussinessDictCodeList.交易状态_初始.getValue());
        param.put("noFlag", BussinessDictCodeList.是否_否.getValue());
        if(StringUtils.isNotBlank((String)param.get("search"))){
            param.put("param",param.get("search"));
        }
        Page<SelectOptionDto> pages = this.queryForPageForSelectReturn(page, length, () -> trdRepoTradeMapper.queryRepoCstpInfoList(param));
        return pages;
    }

    /**
     * 根据成交编号查询cstp交易信息
     * @param fedealno 成交编号
     * @param loginUserName 登录用户
     * @return cstp交易信息
     */
    public TrdRepoTradeCSTPDto queryRepoCstpInfoByNo(String fedealno,String loginUserName){
        TrdRepoTradeCSTPDto trdRepoTradeCSTPDto = new TrdRepoTradeCSTPDto();

        InterfOpicsTradRepoDto interfOpicsTradRepo = trdRepoTradeMapper.queryRepoCstpInfoByNo(fedealno);
        if (interfOpicsTradRepo != null){
            BeanUtil.copyPropertiesIgnoreNull(trdRepoTradeCSTPDto, interfOpicsTradRepo);

            if (StringUtil.equals(trdRepoTradeCSTPDto.getPs(), "RS")){
                trdRepoTradeCSTPDto.setPs(BussinessDictCodeList.回购拆借方向_正回购.getValue());
            }
            else if (StringUtil.equals(trdRepoTradeCSTPDto.getPs(), "RB")){
                trdRepoTradeCSTPDto.setPs(BussinessDictCodeList.回购拆借方向_逆回购.getValue());
            }

            if (StringUtil.equals(trdRepoTradeCSTPDto.getBasis(), "A/365")){
                trdRepoTradeCSTPDto.setBasis(BussinessDictCodeList.计息基础_A_365.getValue());
            }

            if (StringUtil.equals(trdRepoTradeCSTPDto.getMarket(), "IB")){
                trdRepoTradeCSTPDto.setMarket(BussinessDictCodeList.交易场所_银行间.getValue());
            }
            else if (StringUtil.equals(trdRepoTradeCSTPDto.getMarket(), "SH")){
                trdRepoTradeCSTPDto.setMarket(BussinessDictCodeList.交易场所_上海证券交易所.getValue());
            }
            else if (StringUtil.equals(trdRepoTradeCSTPDto.getMarket(), "SZ")){
                trdRepoTradeCSTPDto.setMarket(BussinessDictCodeList.交易场所_深圳证券交易所.getValue());
            }
            else {
                trdRepoTradeCSTPDto.setMarket(null);
            }
            // 交易对手代码转换
            // 查询报价信息
            EtrEntrustExt etrEntrustExt = trdBondInvestTradeMapper.queryRepoEntrustInfo(fedealno);
            if(null!=etrEntrustExt){
            	//有报价
            	trdRepoTradeCSTPDto.setIsOfferFlag("01");
                if(StringUtil.isNotEmpty(etrEntrustExt.getCpPartyId())){
                    trdRepoTradeCSTPDto.setSeller(trdBondInvestTradeMapper.selectCounterId(etrEntrustExt.getCpPartyId()));
                }
                trdRepoTradeCSTPDto.setSelltrader(etrEntrustExt.getCpTrader());
                trdRepoTradeCSTPDto.setPortfolioId(etrEntrustExt.getPortfolioId());
                trdRepoTradeCSTPDto.setEntrustId(etrEntrustExt.getEntrustId());
                trdRepoTradeCSTPDto.setBuycost(etrEntrustExt.getOuTrader());
            }else{
            	//无报价
            	trdRepoTradeCSTPDto.setIsOfferFlag("02");
            	trdRepoTradeCSTPDto.setBuycost(null);
            	trdRepoTradeCSTPDto.setSelltrader(null);
                /*if (StringUtil.isNotEmpty(trdRepoTradeCSTPDto.getSeller())) {
                    MstCounterParty mstCounterParty = trdBondInvestTradeMapper.queryMstCounterPartyByCounterName(trdRepoTradeCSTPDto.getSeller());
                    if (mstCounterParty != null) {
                        trdRepoTradeCSTPDto.setSeller(mstCounterParty.getCounterId());
                    }
                    else{
                        trdRepoTradeCSTPDto.setSeller(null);
                    }
                }*/
            }
            // 根据证券账户查询对应组合
            // 查询证券账户是否系统存在
            // 甲类户都是以"A"开头，乙类户可能证券账户信息那边暂时没有维护
            /*if (StringUtil.indexOf(trdRepoTradeCSTPDto.getOurdepositaccountnumber(), "A") == 0){
                trdRepoTradeCSTPDto.setOurdepositaccountnumber("A000000001");
            }// 乙类户
            else{
                // 根据证券账户查询关联组合
                if(StringUtil.isNotEmpty(trdRepoTradeCSTPDto.getOurdepositaccountnumber())){
                    List<PtlPortfolio> portfolioList = trdBondInvestTradeMapper.queryPortfolioIdBySecAcctId(null,trdRepoTradeCSTPDto.getOurdepositaccountnumber());
                    if (!StringUtil.isEmptyList(portfolioList)){
                        trdRepoTradeCSTPDto.setPortfolioId(portfolioList.get(0).getPortfolioId());
                    }
                }
            }*/
        }

        return trdRepoTradeCSTPDto;
    }

    /**
     * 根据成交单号查询回购抵押品关联
     *
     * @param fedealno 成交单号
     * @return 回购抵押品关联
     * @see
     * @since 1.0
     */
    public List<InterfTradRepoasst> queryRepoAssetByNo(String fedealno){
        return trdRepoTradeMapper.queryRepoAssetByNo(fedealno);
    }
    
    public FinProduct querySecinfo(Map<String, Object> param){
        return trdRepoTradeMapper.querySecinfo(param);
    }

    public Boolean isAllTradeAmtByFTradeId(Map<String, Object> map){
        String type = "";
        String fTradeId = (String) map.get("fTradeId");
        String tradeAmtStr =  (String) map.get("tradeAmt");
        String tradeType = (String) map.get("tradeType");
        String workflowId = (String) map.get("workflowId");
        String ps = (String) map.get("ps");
        if(StringUtils.equals(ps,BussinessDictCodeList.回购拆借方向_正回购.getValue())){
            type = BussinessDictCodeList.金融产品类型_债券质押式正回购.getValue();
        }else{
            type = BussinessDictCodeList.金融产品类型_债券质押式逆回购.getValue();
        }
        BigDecimal tradeAmt = new BigDecimal(Double.valueOf(tradeAmtStr));
        tradeAmt = com.joyintech.calculation.util.ArithmeticUtil.doAdd(2, RoundingMode.HALF_UP,tradeAmt, new BigDecimal(0));
        BigDecimal all = new BigDecimal(0);
        BigDecimal prinamt = new BigDecimal(0);
        if(StringUtils.isNotBlank(fTradeId)){
            Map<String,Object> ftrademap = new HashMap<>();
            //InterfOpicsTradRepo interfOpicsTradRepo = interfOpicsTradRepoMapper.selectByPrimaryKey(fTradeId);
            InterfOpicsTradRepo interfOpicsTradRepo = trdRepoTradeMapper.selectInterfOpicsTradRepo(fTradeId);
            ftrademap.put("fTradeId",fTradeId);
            BigDecimal amountByFtradeId = cstpSelectMapper.getAmountByFtradeId(ftrademap);
            if(StringUtils.equals(tradeType,BussinessDictCodeList.回购交易_质押式.getValue())){
                if (interfOpicsTradRepo != null && interfOpicsTradRepo.getRepoamt() != null) {
                    prinamt = interfOpicsTradRepo.getRepoamt().setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                 if(amountByFtradeId!=null){
                     prinamt = amountByFtradeId;
                 }
            }else{
                if (interfOpicsTradRepo != null && interfOpicsTradRepo.getFacetotalamt() != null) {
                    prinamt = interfOpicsTradRepo.getFacetotalamt().setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }

            //有效
            List<TrdProductDeal> trdProductDealByFTradeId = trdRepoTradeMapper.getTrdProductDealByFTradeId(fTradeId,type);
            //流程中
            List<WflTrdProductDeal> wftTrdProductDealByFTradeId = trdRepoTradeMapper.getWftTrdProductDealByFTradeId(fTradeId,type,workflowId);
            if(interfOpicsTradRepo!=null){
                for (TrdProductDeal deal:trdProductDealByFTradeId) {
//                    all = all.add(deal.getTradeAmt()).setScale(2,BigDecimal.ROUND_HALF_UP);
                   all = ArithmeticUtil.doAdd(all,deal.getTradeAmt());
                }
                for (WflTrdProductDeal deal:wftTrdProductDealByFTradeId) {
//                    all = all.add(deal.getTradeAmt()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    all = ArithmeticUtil.doAdd(all,deal.getTradeAmt());
                }
                if(tradeAmt!=null){
//                    all = all.add(tradeAmt).setScale(2,BigDecimal.ROUND_HALF_UP);
                    all = ArithmeticUtil.doAdd(all,tradeAmt);
                }
                //有效和流程中的总和大于接口的数据
                if(all.compareTo(prinamt)==1){
                    return false;
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取质押券其他信息
     * @param othInputMap 参数
     * @return 质押券信息
     */
    public PledgeBondInfoDto getPledgeBondInfoDto(Map<String, Object> othInputMap) {
        List<PledgeBondInfoDto> pledgeBondInfoDtos = bussRepoSecService.selectAvaPledgeBondDetailList(othInputMap);
        for(int i=0;i<pledgeBondInfoDtos.size();i++){
            PledgeBondInfoDto pledgeBondInfoDto=pledgeBondInfoDtos.get(i);
            //质押本方券券面总额
            if(StringUtils.equals(pledgeBondInfoDto.getHodingType(),DictCodeList.持仓类型_实际.getValue())){
                pledgeBondInfoDto.setPledgeBfPrin(pledgeBondInfoDto.getPrin());//质押本方券券面总额
                pledgeBondInfoDto.setAvaPledgeBfPrin(pledgeBondInfoDto.getAvaPledgePrin());//可质押本方券券面总额
            }else if(StringUtils.equals(pledgeBondInfoDto.getHodingType(),DictCodeList.持仓类型_融入.getValue())){
                pledgeBondInfoDto.setPledgeRrPrin(pledgeBondInfoDto.getPrin());//质押融入券券面总额
                pledgeBondInfoDto.setPledgeRrPrin(pledgeBondInfoDto.getAvaPledgePrin());//可质押融入券券面总额
            }
        }
        othInputMap.put("FINPROD_TYPE2_ZQ",BussinessDictCodeList.金融产品类型_投管用_债券.getValue());
        PledgeBondInfoDto pledgeBondInfoDto = trdRepoTradeMapper.getPledgeBondInfoDto(othInputMap);
        for (PledgeBondInfoDto dto:pledgeBondInfoDtos) {
            if(StringUtils.equals(dto.getPortfolioId(),pledgeBondInfoDto.getPortfolioId())&&
                StringUtils.equals(dto.getBondCode(),pledgeBondInfoDto.getBondCode())){
                pledgeBondInfoDto.setPledgeBfPrin(dto.getPledgeBfPrin());
                pledgeBondInfoDto.setPledgeRrPrin(dto.getPledgeRrPrin());
                pledgeBondInfoDto.setAvaPledgeBfPrin(dto.getAvaPledgeBfPrin());
                pledgeBondInfoDto.setAvaPledgeRrPrin(dto.getAvaPledgeRrPrin());
                break;
            }
        }
        return pledgeBondInfoDto;

    }

    /**
     * 获取换出质押券信息
     * @param parm 参数
     * @return 质押券信息
     */
    public Page<PledgeBondInfoDto> getOutPledgeInfo(int page, int length,Map<String, Object> parm) {
        return  this.queryForPageForSelectReturn(page, length, () -> trdRepoTradeMapper.getOutPledgeInfo(parm));
    }

    /**
     * 根据交易编号查询该交易是否为到期限续做交易
     *
     * @param tradeId 交易编号
     * @return 流该交易是否为到期限续做交易
     * @see
     * @since 1.0
     */
    public Boolean isDqTrdProductInfo(String tradeId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tradeId", tradeId);
        paramMap.put("PST56", DictCodeList.交易方向_到期续做.getValue());
        return this.trdRepoTradeMapper.isDqTrdProductInfo(paramMap) > 0;
    }
    public FinProduct getFinProduct(String finprodMarketId){
        return trdRepoTradeMapper.getFinProduct(finprodMarketId);
    }

    public BigDecimal queryLendinQty(Map<String, Object> params) {
        return trdRepoTradeMapper.queryLendinQty(params);
    }
    
    /** 查询委托的剩余额度
     * @param param 参数
     * @return 
     */
    public BigDecimal selectDoEntrustMoney(Map<String, Object> param){

        return trdRepoTradeMapper.selectDoEntrustMoney(param);
    }

    
    public List<TrdMatchInfo> selectPreTradeId(Map<String, Object> params) {
        return trdRepoTradeMapper.selectPreTradeId(params);
    }

    /**
     * add by yangwenjun 20201123 根据业务编号查询合作资产
     * @param tradeId
     * @return
     */
    public String getChlId(String tradeId) {
        return trdRepoTradeMapper.getChlId(tradeId);
    }

	 public List<FinProduct> selectFinProduct(Map<String, Object> params) {
        return trdRepoTradeMapper.selectFinProduct(params);
    }

    public List<InterfTradRepoasstDto> getMatchpledgeBondInfo(Map<String,Object> map){
        return trdRepoTradeMapper.getMatchpledgeBondInfo(map);
    }

    /**
     * 查询导出pdf数据 add by zengchengfa
     * @param param
     * @return
     * @throws Exception
     */
    public List<TrdRepoTradeExportPdfDto> selectExportPdf(Map<String, Object> param) throws Exception{
        if (StringUtil.isNotEmpty(param.get("selectedItem"))){
            List<String> selectedItem = StringUtil.splitToList(",",(String)param.get("selectedItem"));
            if(selectedItem.size() > 0){
                param.put("tradeIdList",selectedItem);
            }
        }
        param.put("defaultTradeMarketList", PageMenuSplitUtil.getTradeMarketList((String) param.get("pageId")));
        return trdRepoTradeMapper.selectExportPdf(param);
    }
}
