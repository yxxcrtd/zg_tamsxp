package com.joyintech.tams.invest.common.trade.service.impl.etrcash;

import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.EtrEntrust;
import com.joyintech.tams.common.base.entity.TrdFinProductBarter;
import com.joyintech.tams.common.base.entity.TrdProductDeal;
import com.joyintech.tams.common.base.entity.TrdProductDealAdd;
import com.joyintech.tams.constant.*;
import com.joyintech.tams.invest.common.dto.BaseTradeProductDealDto;
import com.joyintech.tams.invest.common.dto.StlPayOrdersDto;
import com.joyintech.tams.invest.common.dto.etr.EtrCashPositionFlowDto;
import com.joyintech.tams.invest.common.dto.etr.EtrPositionHoldDto;
import com.joyintech.tams.invest.common.dto.etr.EtrSecPositionFlowDto;
import com.joyintech.tams.invest.common.entrust.dto.EntrustCommonDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.joyintech.tams.invest.common.trade.service.impl.etrcash.EtrCommonCashPositionDealService.getSeqNo;

/**
 * @program: 2.1.1
 * @description:可转债：指令头寸更新
 * @author: gejiaao
 * @create: 2019-11-19 15:05
 **/
@Service
public class EtrConBondCashPositionDealService extends EtrCashPositionBaseService {

    static BigDecimal 债券面值 = new BigDecimal(100);


    public static void deal可转债EntrustStart(EntrustCommonDto entrustCommonDto, EtrPositionHoldDto etrPositionHoldDto) {
        if (entrustCommonDto != null) {
            EtrEntrust etrEntrust = entrustCommonDto.getEtrEntrust();
            String ps = etrEntrust.getPs();
            if(DictCodeList.交易方向_申购.getValue().equals(ps)){
                BigDecimal 委托全价 = gen委托全价(etrEntrust.getEntrustId(), entrustCommonDto.getEtrEntrust().getEntrustDate());
                // 委托生成的现金流水
                EtrCashPositionFlowDto etrCashPositionFlowDto = EtrCommonCashPositionDealService.getEtrCashPositionFlowDtoFromEtr(
                        entrustCommonDto);
                String acctId = gen账号(etrEntrust.getChlId(), etrEntrust.getCcy(),
                        entrustCommonDto.getEtrEntrustAdd().getSecAcctId(),etrEntrust.getPortfolioId(), entrustCommonDto.getEtrEntrustAdd().getPurchModle(), etrCashPositionFlowDto);
                // 减少委托券面总额*委托全价/100
                etrCashPositionFlowDto.setAmount(etrEntrust.getEntrustNum().negate().multiply(债券面值));
                etrCashPositionFlowDto.setAcctId(acctId);
                etrCashPositionFlowDto.setOffsetId(entrustCommonDto.getEtrEntrust().getEntrustId());
                etrCashPositionFlowDto.setCdate(etrEntrust.getEntrustDate());
                etrPositionHoldDto.getPtlCashPositionFlows().add(etrCashPositionFlowDto);
            }
           if (DictCodeListExt1.交易方向_债转股.getValue().equals(ps)) {
                // 减少委托券面总额/100
                EtrSecPositionFlowDto etrSecPositionFlowDto = EtrCommonCashPositionDealService.getEtrSecPositionFlowDtoFromEtr(
                    entrustCommonDto);
                etrSecPositionFlowDto.setAmount(etrEntrust.getEntrustNum().negate());
                etrPositionHoldDto.getPtlSecPositionFlows().add(etrSecPositionFlowDto);
            }
        }
    }
    
    /**
     * 获取委托全价
     * 
     * @param entrustId 委托代码
     * @param dealDate 日期
     * @return 委托全价
     */
    public static BigDecimal gen委托全价(String entrustId, Date dealDate) {
        EntrustCommonDto entrustCommonDto = EtrCommonCashPositionDealService.findEntrust(entrustId);
        // 价格模式_指定价格(DictCode.价格模式,"PRM01","指定价格"),
        // 价格模式_限价指令(DictCode.价格模式,"PRM02","限价指令"),
        // 价格模式_市价指令(DictCode.价格模式,"PRM03","市价指令"),
        if (StringUtil.equals(entrustCommonDto.getEtrEntrust().getPriceModel(), DictCodeListExt2.价格模式_指定价格.getValue())
            || StringUtil.equals(entrustCommonDto.getEtrEntrust().getPriceModel(), DictCodeListExt2.价格模式_限价指令.getValue())) {
            BigDecimal 委托净价 = entrustCommonDto.getEtrEntrust().getPrice();
            BigDecimal 委托全价 = null;
            if(委托净价==null){
                委托全价 = 债券面值;
            }
            else{
                EtrCommonCashPositionDealService etrCommonCashPositionDealService = SpringContextUtil.getBean(EtrCommonCashPositionDealService.class);
                BigDecimal 单位应计利息 = etrCommonCashPositionDealService.gen应计利息(entrustCommonDto.getEtrEntrust().getFinprodId(),dealDate);
                委托全价 = ArithmeticUtil.doAdd(单位应计利息,委托净价);
            }
            return 委托全价;
        }
        else {
            // 金融产品代码
            String finprodId = entrustCommonDto.getEtrEntrust().getFinprodId();
            Map<String, Object> map = new HashMap<>();
            map.put("finprodId", finprodId);
            map.put("dealDate", dealDate);
            BigDecimal 最新市价 = EtrCommonCashPositionDealService.find最新市价(map);
            if (最新市价 == null) {
                最新市价 = 债券面值;
            }
            return 最新市价;
        }
    }

    /**
     * 获取现金流水需要的账号
     *
     * @param chlId                  通道代码
     * @param ccy                    币种
     * @param secManageAcctId        证券托管户
     * @param portfolioId            组合代码
     * @param purchModle             申购方式
     * @param etrCashPositionFlowDto 流水dto
     * @return 账号
     */
    private static String gen账号(String chlId, String ccy, String secManageAcctId, String portfolioId, String purchModle,
                                EtrCashPositionFlowDto etrCashPositionFlowDto) {
        if (StringUtil.isNotEmpty(chlId)) {
            etrCashPositionFlowDto.setChlFinprodId(chlId);
            return EtrCommonCashPositionDealService.select通道资金账户(chlId);
        } else {
            // 交易所无通道
            etrCashPositionFlowDto.setChlFinprodId(null);
            //申购方式为网上申购 取资金账户，网下取组合托管户
            if (StringUtil.equals(purchModle,DictCodeListExt2.申购方式_线下申购.getValue())){
            	return EtrCommonCashPositionDealService.select组合托管户(portfolioId, ccy, DictCodeListExt2.资金账户类型_托管户.getValue());
            }else {
            	return EtrCommonCashPositionDealService.select资金账户(secManageAcctId, ccy);
            }
        }
    }

    /**
     * 可转债 以持仓数量（张数）统计
     */
    public static void deal可转债CashPosition(BaseTradeProductDealDto finProductTradeDto, DictCodeList 持仓类型,
                                           EtrPositionHoldDto etrPositionHoldDto) {
            if(StringUtil.equals(finProductTradeDto.getTrdProductDeal().getPs(), DictCodeListExt3.交易方向_债转股.getValue())){
//                交易生效（有委托）	增加股票指令可用持仓		增加股票指令可用持仓
                if(持仓类型.equals(DictCodeList.持仓类型_实际)){
                    TrdProductDeal deal = finProductTradeDto.getTrdProductDeal();
                    TrdProductDealAdd dealAdd = finProductTradeDto.getTrdProductDealAdd();
                    TrdFinProductBarter barterDeal = finProductTradeDto.getTrdFinProductBarterList().get(0);
                    List<StlPayOrdersDto> stlPayOrdersDtos = finProductTradeDto.getStlPayOrdersList();
                    // 通道代码
                    String chlId = finProductTradeDto.getTrdProductDeal().getChlId();
                    // 通道性质
                    String chlType = StringUtil.isEmpty(chlId) ? null : EtrCommonCashPositionDealService.queryChlType(chlId);
                    // 交易方向
                    String ps = finProductTradeDto.getTrdProductDeal().getPs();
                    // 委托代码
                    String entrustId = finProductTradeDto.getEntrustId();
                    // 证券账户
                    String secManageAcctId = finProductTradeDto.getTrdProductDeal().getSecManageAcctId();
                    // 交易日期
                    Date tradeDate = finProductTradeDto.getTrdProductDeal().getTradeDate();
                    
                    // 交易生成的持仓流水 (债券)
                    if(StringUtil.isEmpty(entrustId)){
                        EtrSecPositionFlowDto etrSecPositionFlowDtoBond = EtrCommonCashPositionDealService.getEtrSecPositionFlowDto(finProductTradeDto);
                        etrSecPositionFlowDtoBond.setAmount(deal.getShareAmt().negate());
                        etrSecPositionFlowDtoBond.setFinprodId(deal.getFinprodId());
                        etrSecPositionFlowDtoBond.setCdate(deal.getTradeDate());
                        etrPositionHoldDto.getPtlSecPositionFlows().add(etrSecPositionFlowDtoBond);
                    }
                    // 交易生成的持仓流水 (股票)
                    EtrSecPositionFlowDto etrSecPositionFlowDtoShare = EtrCommonCashPositionDealService.getEtrSecPositionFlowDto(finProductTradeDto);
                    etrSecPositionFlowDtoShare.setAmount(barterDeal.getShareAmt());
                    etrSecPositionFlowDtoShare.setFinprodId(barterDeal.getFinprodId());
                    etrSecPositionFlowDtoShare.setCdate(deal.getTradeDate());
                    etrPositionHoldDto.getPtlSecPositionFlows().add(etrSecPositionFlowDtoShare);
                    if(stlPayOrdersDtos!= null && stlPayOrdersDtos.size()>0){
                        // 交易生成的现金流水
                        EtrCashPositionFlowDto etrCashPositionFlowDto = EtrCommonCashPositionDealService.getEtrCashPositionFlowDto(
                                finProductTradeDto);
                        etrCashPositionFlowDto.setOrderId(finProductTradeDto.getTrdProductDeal().getTradeId());
                        etrCashPositionFlowDto.setAcctId(stlPayOrdersDtos.get(0).getMyAcctNo());
                        etrCashPositionFlowDto.setAmount(stlPayOrdersDtos.get(0).getSettleAmt());
                        etrCashPositionFlowDto.setCdate(stlPayOrdersDtos.get(0).getSettleDate());
                        etrPositionHoldDto.getPtlCashPositionFlows().add(etrCashPositionFlowDto);
                    }
                }
            }
            
            if(StringUtil.equals(finProductTradeDto.getTrdProductDeal().getPs(), DictCodeList.交易方向_申购确认.getValue())){
//              交易生效 增加债券持仓
              if(持仓类型.equals(DictCodeList.持仓类型_实际)){
                  TrdProductDeal deal = finProductTradeDto.getTrdProductDeal();
                  TrdProductDealAdd dealAdd = finProductTradeDto.getTrdProductDealAdd();
//                  TrdFinProductBarter barterDeal = finProductTradeDto.getTrdFinProductBarterList().get(0);
                  List<StlPayOrdersDto> stlPayOrdersDtos = finProductTradeDto.getStlPayOrdersList();
                  // 通道代码
                  String chlId = finProductTradeDto.getTrdProductDeal().getChlId();
                  // 通道性质
                  String chlType = StringUtil.isEmpty(chlId) ? null : EtrCommonCashPositionDealService.queryChlType(chlId);
                  // 交易方向
                  String ps = finProductTradeDto.getTrdProductDeal().getPs();
                  // 委托代码
                  String entrustId = finProductTradeDto.getEntrustId();
                  // 证券账户
                  String secManageAcctId = finProductTradeDto.getTrdProductDeal().getSecManageAcctId();
                  // 交易日期
                  Date tradeDate = finProductTradeDto.getTrdProductDeal().getTradeDate();
                  // 交易生成的持仓流水 (债券)
                  EtrSecPositionFlowDto etrSecPositionFlowDto = EtrCommonCashPositionDealService.getEtrSecPositionFlowDto(
                          finProductTradeDto);
                  etrSecPositionFlowDto.setAmount(deal.getShareAmt());
                  etrSecPositionFlowDto.setFinprodId(deal.getFinprodId());
                  etrSecPositionFlowDto.setCdate(deal.getTradeDate());
                  etrSecPositionFlowDto.setSecAcctId(deal.getSecManageAcctId());
                  etrPositionHoldDto.getPtlSecPositionFlows().add(etrSecPositionFlowDto);
                  /**add by taoran 20200303 委托申购确认更新实时现金 start*/
                  if(stlPayOrdersDtos!= null && stlPayOrdersDtos.size()>0 && StringUtil.isNotEmpty(finProductTradeDto.getEntrustId())
                		  && DictCodeListExt2.申购类型_普通申购.getValue().equals(finProductTradeDto.getPurchType())){
                      // 交易生成的现金流水
                      EtrCashPositionFlowDto etrCashPositionFlowDto = EtrCommonCashPositionDealService.getEtrCashPositionFlowDto(
                              finProductTradeDto);
                      etrCashPositionFlowDto.setOrderId(finProductTradeDto.getTrdProductDeal().getTradeId());
                      etrCashPositionFlowDto.setAcctId(gen账号(deal.getChlId(),deal.getCcy(),deal.getSecManageAcctId(), dealAdd.getPortfolioId(), dealAdd.getPurchModle(),etrCashPositionFlowDto));
                      etrCashPositionFlowDto.setAmount(finProductTradeDto.getSecAmount());		//实时现金 申购券面 - 中签券面
                      etrCashPositionFlowDto.setCdate(stlPayOrdersDtos.get(0).getSettleDate());
                      etrPositionHoldDto.getPtlCashPositionFlows().add(etrCashPositionFlowDto);
                  }
                  /**add by taoran 20200303 委托申购确认更新实时现金 end*/
                  
                  /**add by yujiaping 20210409 申购确认更新资金账户指令可用现金 end*/
                  else if(stlPayOrdersDtos!= null && stlPayOrdersDtos.size()>0
                		  && DictCodeListExt2.申购类型_普通申购.getValue().equals(finProductTradeDto.getPurchType())){                  
                      EtrCashPositionFlowDto etrCashPositionFlowDto = EtrCommonCashPositionDealService.getEtrCashPositionFlowDto(
                              finProductTradeDto);
                      etrCashPositionFlowDto.setOrderId(finProductTradeDto.getTrdProductDeal().getTradeId());
                      etrCashPositionFlowDto.setAcctId(gen账号(deal.getChlId(),deal.getCcy(),deal.getSecManageAcctId(), dealAdd.getPortfolioId(), dealAdd.getPurchModle(),etrCashPositionFlowDto));
//                      etrCashPositionFlowDto.setAmount(stlPayOrdersDtos.get(0).getSettleAmt().negate());
                      etrCashPositionFlowDto.setAmount(finProductTradeDto.getTrdProductDeal().getFpriceAmt().negate());
                      // 指令的现金流水的日期应该是确认日
					  //etrCashPositionFlowDto.setCdate(stlPayOrdersDtos.get(0).getSettleDate());
                      etrCashPositionFlowDto.setCdate(finProductTradeDto.getTrdProductDeal().getTradeDate());
					  etrPositionHoldDto.getPtlCashPositionFlows().add(etrCashPositionFlowDto);
                  }
                  /**add by yujiaping 20210409 申购确认更新资金账户指令可用现金 end*/
              }
          }

    }

    public static void deal可转债EntrustClose(EntrustCommonDto entrustCommonDto, EtrPositionHoldDto etrPositionHoldDto,String payType) {
        EtrEntrust etrEntrust = entrustCommonDto.getEtrEntrust();
        BigDecimal etrFinish = EtrCommonCashPositionDealService.getEtrFinishAmt(etrEntrust.getEntrustId());
        String ps = etrEntrust.getPs();
//        // 可用现金 关闭日增加剩余委托券面总额*委托全价/100
//        EtrCashPositionFlowDto etrCashPositionFlowDto = EtrCommonCashPositionDealService.getEtrCashPositionFlowDtoFromEtr(
//                entrustCommonDto);
//        String acctId = gen账号(etrEntrust.getChlId(), etrEntrust.getCcy(),
//                entrustCommonDto.getEtrEntrustAdd().getSecAcctId(), etrEntrust.getPortfolioId(), entrustCommonDto.getEtrEntrustAdd().getPurchModle(), etrCashPositionFlowDto);
//        // 关闭日增加剩余委托券面总额*委托全价/100
//        etrCashPositionFlowDto.setAmount(
//                EtrCommonCashPositionDealService.getEtrSurplusAmt(etrEntrust.getEntrustId(), etrEntrust.getPortfolioId()));
//        etrCashPositionFlowDto.setAcctId(acctId);
//        etrCashPositionFlowDto.setCdate(etrEntrust.getEntrustDate());
//        etrPositionHoldDto.getPtlCashPositionFlows().add(etrCashPositionFlowDto);
        if (DictCodeListExt1.交易方向_债转股.getValue().equals(ps)) {
            EtrSecPositionFlowDto etrSecPositionFlowDto = EtrCommonCashPositionDealService.getEtrSecPositionFlowDtoFromEtr(
                entrustCommonDto);
            // 可用持仓 关闭日增加剩余委托券面总额/100
            etrSecPositionFlowDto.setAmount(ArithmeticUtil.subtract(etrEntrust.getEntrustNum(), etrFinish));
            etrPositionHoldDto.getPtlSecPositionFlows().add(etrSecPositionFlowDto);
        }

        if (DictCodeList.交易方向_申购.getValue().equals(ps)) {//可转债申购对指令有影响 add by lijie2 20200825
            EtrCashPositionFlowDto etrCashPositionFlowDto = EtrCommonCashPositionDealService.getEtrCashPositionFlowDtoFromEtr(entrustCommonDto);
            etrCashPositionFlowDto.setOffsetId(etrEntrust.getEntrustId());
            //etrCashPositionFlowDto.setAcctId(EtrCommonCashPositionDealService.select资金账户(entrustCommonDto.getEtrEntrustAdd().getSecAcctId(),etrEntrust.getCcy()));
            String acctId = "";
            //add by huanghao 20201211 新增划款方式的判断，如果时逐笔划款则默认使用托管户，不适用通道账户。
            if(DictCodeListExt2.划款方式_逐笔划款.getValue().equals(payType)){
                acctId = gen账号("", etrEntrust.getCcy(),
                        entrustCommonDto.getEtrEntrustAdd().getSecAcctId(), etrEntrust.getPortfolioId(),
                        entrustCommonDto.getEtrEntrustAdd().getPurchModle(), etrCashPositionFlowDto);
            }else{
                acctId = gen账号(etrEntrust.getChlId(), etrEntrust.getCcy(),
                        entrustCommonDto.getEtrEntrustAdd().getSecAcctId(), etrEntrust.getPortfolioId(),
                        entrustCommonDto.getEtrEntrustAdd().getPurchModle(), etrCashPositionFlowDto);
            }
            etrCashPositionFlowDto.setAcctId(acctId);
            if(StringUtil.equals(entrustCommonDto.getEtrEntrustAdd().getPurchModle(), DictCodeListExt2.申购方式_线下申购)) {
            	etrCashPositionFlowDto.setAmount(ArithmeticUtil.subtract(etrEntrust.getEntrustNum().multiply(债券面值), etrFinish));//update by huanghao 指令金额采用指令数额*100算出
            }
            else if(StringUtil.isNotEmpty(etrEntrust.getPrice())){
            	etrCashPositionFlowDto.setAmount(ArithmeticUtil.subtract(etrEntrust.getEntrustNum().multiply(etrEntrust.getPrice()), etrFinish));//update by huanghao 指令金额采用指令数额*100算出
            }else{
            	etrCashPositionFlowDto.setAmount(ArithmeticUtil.subtract(etrEntrust.getEntrustNum().multiply(债券面值), etrFinish));
            }
            etrPositionHoldDto.getPtlCashPositionFlows().add(etrCashPositionFlowDto);
        }
    }
}
