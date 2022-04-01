package com.joyintech.tams.invest.common.trade.dto;

import com.joyintech.tams.common.base.entity.TrdProductDealAddPre;
import com.joyintech.tams.common.base.entity.TrdProductDealPre;

import java.util.List;

public class PreTrdCommonDto {

    /**
     * 组合代码
     */
    private String portfolioId;

    /**
     * List<金融产品交易预录入Dto>
     */
    private List<TrdProductDealPre> trdProductDealPreList;

    /**
     * List<金融产品交易预录入附属表Dto>
     */
    private List<TrdProductDealAddPre> trdProductDealAddPreList;

    /**
     * List<金融产品交易预录入共通Dto>
     */
    private List<PreTrdCommonDto> preTrdCommonDtoList;
    /**
     *数据已入新头寸，Y：已入
     */
    private String isExistNew;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public List<TrdProductDealPre> getTrdProductDealPreList() {
        return trdProductDealPreList;
    }

    public void setTrdProductDealPreList(List<TrdProductDealPre> trdProductDealPreList) {
        this.trdProductDealPreList = trdProductDealPreList;
    }

    public List<TrdProductDealAddPre> getTrdProductDealAddPreList() {
        return trdProductDealAddPreList;
    }

    public void setTrdProductDealAddPreList(List<TrdProductDealAddPre> trdProductDealAddPreList) {
        this.trdProductDealAddPreList = trdProductDealAddPreList;
    }

    public List<PreTrdCommonDto> getPreTrdCommonDtoList() {
        return preTrdCommonDtoList;
    }

    public void setPreTrdCommonDtoList(List<PreTrdCommonDto> preTrdCommonDtoList) {
        this.preTrdCommonDtoList = preTrdCommonDtoList;
    }

    public String getIsExistNew() {
        return isExistNew;
    }

    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }
}
