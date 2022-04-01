/*
 * 文件名：EtrAgreRepTrade002Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：LBY
 * 修改时间：2020年9月16日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.entrust.etragrereptrade.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.entrust.etragrereptrade.dto.EtrAgreRepTrade002Dto;
import com.joyintech.tams.invest.entrust.etragrereptrade.dto.OnWayRep002Dto;
import com.joyintech.tams.invest.entrust.etragrereptrade.dto.UnOpenQuota002Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  交易所指令交易（债券股票回购基金场内指令交易）
 * @author chendijun
 * @version 1.0 2020年7月1日
 * @see EtrAgreRepTrade002Service
 * @since 1.0
 */
@Service
public class EtrAgreRepTrade002Service extends CommonExportService<EtrAgreRepTrade002Dto> {

    public static String queryrRepurchaseQuote = "/joyin/entrust/integrated/repurchase_quote";

    public static String queryIntegratedTick = "/joyin/quote/tick";
    /**
     BPB	上海协议回购成交申报		    正回购
     BPC	上海协议回购到期续做申报		到期续做
     BPD	上海协议回购解除质押		    接触质押
     BPE	上海协议回购换券申报		    中途换券
     BPF	上海协议回购提前终止申报		提前购回
     */
    private static final String SB_SHXYHGCJ = "BPB";
    private static final String SB_SHXYHGDQXZ = "BPC";
    private static final String SB_SHXYHGJCZY = "BPD";
    private static final String SB_SHXYHGHJSB = "BPE";
    private static final String SB_SHXYHGTQZZSB = "BPF";


    // yyyy-MM-dd HH:mm:ss
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

//    @Autowired
//    private FinProductCommonBatchService finProductCommonBatchService;
    /**
     * 分页查询产品持仓信息
     * <功能详细描述>
     * @param param 查询参数
     * @return 分页结果
     * @see
     * @since 1.0
     */
    public Page<EtrAgreRepTrade002Dto> findPositionsPage(Map<String, Object> param) {
        try {
            param.put("userId", SecurityUtil.getLoginUser().getUserName());
            return restTemplate.postForPage(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrAgreRepTrade/findPositionsPage",
                param, EtrAgreRepTrade002Dto.class);
        }
        catch (Exception ex){
            return new Page<EtrAgreRepTrade002Dto>();
        }
    }



    /**
     * 查询证券代码联想框文本框
     *
     * @param parameters 画面参数
     * @return 证券代码联想框文本框
     * @see
     * @since 1.0
     */
    public Page<SelectOptionDto> getSecAcctId(Map<String, Object> parameters) {
        Page<SelectOptionDto> tempList = restTemplate.postForPage(
            ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/getSecAcctId",
            parameters, SelectOptionDto.class);
        return tempList;
    }

    /**
     * 查询上交所和深交所标准券数量
     *
     * @param pageParam 页面参数
     * @return 上交所和深交所标准券数量
     * @see
     * @since 1.0
     */
    public Map<String, String> loadSecNum(Map<String, Object> pageParam) {
        Map<String, String> map = (Map<String, String>)restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/loadSecNum",
                pageParam, Map.class);
        return map;
    }
    public Page<OnWayRep002Dto> findOnWayRepPage(Map<String, Object> param) {
        try {
            param.put("userId", SecurityUtil.getLoginUser().getUserName());
            return restTemplate.postForPage(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrAgreRepTrade/findOnWayRepPage",
                param, OnWayRep002Dto.class);
        }
        catch (Exception ex){
            return new Page<OnWayRep002Dto>();
        }
    }

    /**
     * 调用非公开行情接口
     * @param map
     * @return
     */
    public Page<UnOpenQuota002Dto> findUnOpenQuotaPage(Map<String, Object> map) {
        if (SecurityUtil.getLoginUser() != null) {
            map.put("userId", SecurityUtil.getLoginUser().getUserName());
        }
        Page<UnOpenQuota002Dto> page = new Page<UnOpenQuota002Dto>();
        List<UnOpenQuota002Dto> list = new ArrayList<UnOpenQuota002Dto>();
        List<Map<String, Object>> repurchaseQuotes = new ArrayList<>();
        //接行情数据  start
        String param = "?action_in=1";
        //actionIn   0：公开行情查询 1:非公开行情查询
        // entrustProp 非必填
        Map quoteMap = restTemplate.getForObject(ServiceApplication.EXCHANGE_SERVICE_NAME + queryrRepurchaseQuote + param, Map.class);
        //http://192.168.30.13:8091/joyin/entrust/integrated/repurchase_quote?action_in=0&account_id=12345678:26769948&entrust_prop=BPG
        //Map quoteMap = simpleRestTemplate.getForObject("http://192.168.90.119:8080/tams/service" + queryrRepurchaseQuote + param, Map.class);
        if (quoteMap != null && quoteMap.get("data") != null) {
            List<Map<String, Object>> quoteList = (List<Map<String, Object>>) quoteMap.get("data");
            if (!StringUtil.isEmptyList(quoteList)) {
                repurchaseQuotes.addAll(quoteList);
            }
        }
        else {
            return page;
        }
        //接行情数据 end
        //去重
        HashSet<String> hSet = new HashSet<>();
        List<Map<String, Object>> newRepurchaseQuotes = new ArrayList<>();
        List<String> stockCodes = new ArrayList<>();
        repurchaseQuotes.forEach(temp->{
            if(StringUtil.isNotEmpty(temp.get("cbpconferId"))&&hSet.add(temp.get("cbpconferId").toString())){
                newRepurchaseQuotes.add(temp);
                // 质押券代码
                if (StringUtil.isNotEmpty(temp.get("stockCode"))) {
                    stockCodes.add(temp.get("stockCode").toString());
                }
            }
        });
        hSet.clear();repurchaseQuotes.clear();
        List<Map<String,Object>> faceValues = new ArrayList<>();
        //封装dto
        //处理标的和面值
        Map<String,Object> tempPara = new HashMap<String, Object>();
        tempPara.put("finprodMarketIdList",stockCodes);
        tempPara.put("tradeDate",DateUtil.getSysDate());
        if (stockCodes.size() > 0) {
            faceValues.addAll(restTemplate.postForObject(
                    ServiceApplication.BASICDATA_SERVICE_NAME + "/MstSecuritiesAccountInfo/getFaceValueList", tempPara,
                    List.class));
        }

        newRepurchaseQuotes.forEach(tmp -> {
            UnOpenQuota002Dto dto = new UnOpenQuota002Dto();
            //资金账号
            ///dto.setAcctNo(model.getId());
//            if(StringUtil.isNotEmpty(tmp.get("account_id").toString())){
//                dto.setAcctNo(tmp.get("account_id").toString());
//            }
            // 请求号
            if (StringUtil.isNotEmpty(tmp.get("cbpconferId"))) {
                dto.setReqOnId(tmp.get("cbpconferId").toString());
            }
            // 请求类型
            /**
             BPB	上海协议回购成交申报		    正回购
             BPC	上海协议回购到期续做申报		到期续做
             BPD	上海协议回购解除质押		    接触质押
             BPE	上海协议回购换券申报		    中途换券
             BPF	上海协议回购提前终止申报		提前购回
             */
            String entrustProp = tmp.get("entrustProp") == null ? null : tmp.get("entrustProp").toString();
            if (StringUtil.isNotEmpty(entrustProp)) {
                //BPB	上海协议回购成交申报		    正回购
                if (StringUtil.equals(SB_SHXYHGCJ, entrustProp)) {
                    dto.setReqType(DictCodeListExt3.交易方向_正回购.getValue());
                }
                //BPC	上海协议回购到期续做申报		到期续做
                else if (StringUtil.equals(SB_SHXYHGDQXZ, entrustProp)) {
                    dto.setReqType(DictCodeListExt3.交易方向_到期续做.getValue());
                }
                //BPD	上海协议回购解除质押		    接触质押
                else if (StringUtil.equals(SB_SHXYHGJCZY, entrustProp)) {
                    dto.setReqType(DictCodeListExt3.交易方向_解除质押.getValue());
                }
                //BPE	上海协议回购换券申报		    中途换券
                else if (StringUtil.equals(SB_SHXYHGHJSB, entrustProp)) {
                    dto.setReqType(DictCodeListExt3.交易方向_中途换券.getValue());
                }
                //BPF	上海协议回购提前终止申报		提前购回
                else if (StringUtil.equals(SB_SHXYHGTQZZSB, entrustProp)) {
                    dto.setReqType(DictCodeListExt3.交易方向_提前购回.getValue());
                }
                else{
                    return;
                }
            }
            // 协议回购质权人类型
            if (StringUtil.isNotEmpty(tmp.get("bprsrcStatus"))) {
                dto.setRedoType(tmp.get("bprsrcStatus").toString());
            }
            // 质押券代码
            if (StringUtil.isNotEmpty(tmp.get("stockCode"))) {
                dto.setRepSecId(tmp.get("stockCode").toString());
            }
            // 质押券名称
            if (StringUtil.isNotEmpty(tmp.get("stockName"))) {
                dto.setPledgeStampName(tmp.get("stockName").toString());
            }
            // 原质押代码  忽略 【属于 重复字段】
            // 质押数量
            if (StringUtil.isNotEmpty(tmp.get("impawnAmount"))) {
                dto.setRepSecNum(BigDecimal.valueOf(Double.valueOf(tmp.get("impawnAmount").toString())));
            }
            // 质押券面总额合计 = 质押数量 * 最新面值 [FinProductCommonBatchService.getFaceValue(String finprodId, Date happenDate)]
            if (StringUtil.isNotEmpty(dto.getRepSecNum())) {
                faceValues.forEach(item ->{
                    if(StringUtil.isNotEmpty(tmp.get("stockCode"))&&StringUtil.isNotEmpty(item.get("finprodMarketId"))&&StringUtil.isNotEmpty(item.get("faceValue"))
                            &&StringUtil.equals(tmp.get("stockCode").toString(),item.get("finprodMarketId").toString())){
                        dto.setPledgeStampBalance(dto.getRepSecNum().multiply(new BigDecimal(item.get("faceValue").toString())));
                    }
                });
            }
            // 折算比例
            if (StringUtil.isNotEmpty(tmp.get("funderRatio"))) {
                dto.setConRatio(BigDecimal.valueOf(Double.valueOf(tmp.get("funderRatio").toString())));
            }
            // 回购金额 = 质押券面总额合计*折算比例
            if (StringUtil.isNotEmpty(tmp.get("impawnBalance"))) {
                dto.setTradeAmt(BigDecimal.valueOf(Double.valueOf(tmp.get("impawnBalance").toString())));
            }
            else if (StringUtil.isNotEmpty(dto.getPledgeStampBalance()) && StringUtil.isNotEmpty(dto.getConRatio())) {
                dto.setTradeAmt(dto.getPledgeStampBalance().multiply(dto.getConRatio()).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
            // 回购利率
            if (StringUtil.isNotEmpty(tmp.get("expireYearRate"))) {
                dto.setYield(BigDecimal.valueOf(Double.valueOf(tmp.get("expireYearRate").toString())));
            }
            // 回购期限
            if (StringUtil.isNotEmpty(tmp.get("repoTerm"))) {
                dto.setTerm(Integer.valueOf(tmp.get("repoTerm").toString()));
            }
            // 实际占款天数
            if (StringUtil.isNotEmpty(tmp.get("fundUsedDays"))) {
                dto.setOccupyDays(Integer.valueOf(tmp.get("fundUsedDays").toString()));
            }
            // 首次结算日
            if (StringUtil.isNotEmpty(tmp.get("firstExchdate"))) {
                dto.setFristSettleDate(DateUtil.format(DateUtil.parse(tmp.get("firstExchdate").toString())));
            }
            // 回购到期日
            if (StringUtil.isNotEmpty(tmp.get("repurchaseDate"))) {
                dto.setBackDate(DateUtil.format(DateUtil.parse(tmp.get("repurchaseDate").toString())));
            }
            // 到期结算日
            if (StringUtil.isNotEmpty(tmp.get("settleEndDate"))) {
                dto.setExpireSettleDate(DateUtil.format(DateUtil.parse(tmp.get("settleEndDate").toString())));
            }
            // 原质押券代码
            if (StringUtil.isNotEmpty(tmp.get("oldImpawnCode"))) {
                dto.setOrigPledgeStampCode(tmp.get("oldImpawnCode").toString());
            }
            // 回购利息
            if (StringUtil.isNotEmpty(tmp.get("profit"))) {
                dto.setIntAmt(BigDecimal.valueOf(Double.valueOf(tmp.get("profit").toString())));
            }
            // 到期结算金额
            if (StringUtil.isNotEmpty(tmp.get("settleBalance"))) {
                dto.setExpireSettleAmt(BigDecimal.valueOf(Double.valueOf(tmp.get("settleBalance").toString())));
            }
            // 成交编号
            if (StringUtil.isNotEmpty(tmp.get("origCbpconferId"))) {
                dto.setTradeId(tmp.get("origCbpconferId").toString());
            }
            // 发起方交易员代码
            if (StringUtil.isNotEmpty(tmp.get("oppoTraderId"))) {
                dto.setSponsorTraderCode(tmp.get("oppoTraderId").toString());
            }
            // 发起方交易商代码
            if (StringUtil.isNotEmpty(tmp.get("oppoAgency"))) {
                dto.setSponsorDealerCode(tmp.get("oppoAgency").toString());
            }
            // 本方交易员代码
            if (StringUtil.isNotEmpty(tmp.get("traderId"))) {
                dto.setTraderCode(tmp.get("traderId").toString());
            }
            // 补充协议
            if (StringUtil.isNotEmpty(tmp.get("remark"))) {
                dto.setAddCla(tmp.get("remark").toString());
            }

            // 回购利率和折算率应该乘以100
            if (dto.getYield() != null) {
                dto.setYield(dto.getYield());
            }
            if (dto.getConRatio() != null) {
                dto.setConRatio(ArithmeticUtil.multiply(dto.getConRatio(), BigDecimal.valueOf(100)));
            }

            list.add(dto);
         });
        page.setRoot(list);
        return page;
    }
    /**
     * getptlAcctNo
     * @param param
     * @return
     */
    public List<SelectOptionDto> getptlAcctNo(Map<String, Object> param) {
      return restTemplate.postForList(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrAgreRepTrade/getptlAcctNo",
                    param, SelectOptionDto.class);
    }

    /**
     * 过滤重复的协议行情[请求号作为主键]
     * @param list
     * @return
     */
    private List<UnOpenQuota002Dto> getRemovedRepeatUnOpenQuotas(List<UnOpenQuota002Dto> list) {
        HashMap<String,UnOpenQuota002Dto> tempMap = new HashMap<String,UnOpenQuota002Dto>();
        for (UnOpenQuota002Dto c : list) {
            String key = c.getReqOnId();
            if(tempMap.containsKey(key)){
                continue;
            }else{
                tempMap.put(key, c);
            }
        }
        List<UnOpenQuota002Dto> tempList = new ArrayList<UnOpenQuota002Dto>();
        for(String key : tempMap.keySet()){
            tempList.add(tempMap.get(key));
        }
        return tempList;
    }
    /**
     * 查询标的代码
     * @return
     */
    public List<SelectOptionDto> getFinprodIdByMarketId(Map<String, Object> map) {
        return restTemplate.postForList(
                ServiceApplication.BASICDATA_SERVICE_NAME + "/MstSecuritiesAccountInfo/getFinprodIdByMarketId", map,
                SelectOptionDto.class);
    }
}
