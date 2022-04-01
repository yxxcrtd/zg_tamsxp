package com.joyintech.tams.settle.batch.dealrevoke.dto;

import com.joyintech.batch.batchbase.baseclass.entity.NeedOperateEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投管交易撤销参数
 * @author 何阳阳
 * @date 2020/8/3
 * @see DealRevokeParamDto
 * @since 1.0
 */
public class DealRevokeParamDto extends NeedOperateEntity {

    /**
     * 证券品种
     */
    private String secType;

    /**
     * 交易编号
     */
    private List<String> tradeIdList;

    /**
     * 汇总交易编号
     */
    private List<String> totalTradeIdList;

    /**
     * 组合代码
     */
    private List<String> portfolioIdList;

    /**
     * 交易日期
     */
    private Date tradeDate;

    private Map<String,String> tradeIdMap;

    private Map<String,String> totalTradeIdMap;

    public DealRevokeParamDto() {
    }

    public DealRevokeParamDto(String secType, Date tradeDate, List<String> portfolioIdList, List<String> tradeIdList, List<String> totalTradeIdList, Map<String, String> tradeIdMap, Map<String, String> totalTradeIdMap) {
        this.secType = secType;
        this.tradeIdList = tradeIdList;
        this.totalTradeIdList = totalTradeIdList;
        this.portfolioIdList = portfolioIdList;
        this.tradeDate = tradeDate;
        this.tradeIdMap = tradeIdMap;
        this.totalTradeIdMap = totalTradeIdMap;
    }

    public DealRevokeParamDto(String secType, Date tradeDate, List<String> portfolioIdList, List<String> tradeIdList, List<String> totalTradeIdList) {
        this.secType = secType;
        this.tradeDate = tradeDate;
        this.portfolioIdList = portfolioIdList;
        this.tradeIdList = tradeIdList;
        this.totalTradeIdList = totalTradeIdList;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public List<String> getTradeIdList() {
        return tradeIdList;
    }

    public void setTradeIdList(List<String> tradeIdList) {
        this.tradeIdList = tradeIdList;
    }

    public List<String> getTotalTradeIdList() {
        return totalTradeIdList;
    }

    public void setTotalTradeIdList(List<String> totalTradeIdList) {
        this.totalTradeIdList = totalTradeIdList;
    }

    public List<String> getPortfolioIdList() {
        return portfolioIdList;
    }

    public void setPortfolioIdList(List<String> portfolioIdList) {
        this.portfolioIdList = portfolioIdList;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Map<String, String> getTradeIdMap() {
        return tradeIdMap;
    }

    public void setTradeIdMap(Map<String, String> tradeIdMap) {
        this.tradeIdMap = tradeIdMap;
    }

    public Map<String, String> getTotalTradeIdMap() {
        return totalTradeIdMap;
    }

    public void setTotalTradeIdMap(Map<String, String> totalTradeIdMap) {
        this.totalTradeIdMap = totalTradeIdMap;
    }
}
