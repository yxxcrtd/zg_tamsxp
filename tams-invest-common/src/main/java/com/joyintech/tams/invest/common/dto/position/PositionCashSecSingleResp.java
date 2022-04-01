package com.joyintech.tams.invest.common.dto.position;

import com.joyintech.tams.business.common.pojo.BaseMap;

import java.util.List;

/**
 * 组合数据集
 */
public class PositionCashSecSingleResp {

    private String portfolioId; // 组合代码

    List<BaseMap>  baseMapList;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public List<BaseMap> getBaseMapList() {
        return baseMapList;
    }

    public void setBaseMapList(List<BaseMap> baseMapList) {
        this.baseMapList = baseMapList;
    }
}
