/*
 * 文件名：EtrInvestInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：huwei
 * 修改时间：2018年8月30日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.common.dto;

import com.joyintech.tams.common.base.entity.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 投资委托信息dto 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author huwei
 * @version 1.0 2018年8月30日
 * @see BaseEtrInvestInfoDto
 * @since 1.0
 */
public class BaseEtrInvestInfoDto {

    /**
     * 委托指令dto
     */
    private EtrEntrust etrEntrust;
    /**
     * 委托指令附属表dto
     */
    private EtrEntrustAdd etrEntrustAdd;
    /**
     * 多组合指令dto 结果
     */
    List<BaseEtrInvestInfoDto> baseEtrInvestInfoDtoList;
    /**
     * 委托指令回购质押品list
     */
    private List<EtrPledge> etrPledgeList;

    /**
     * 委托指令分发list
     */
    private List<EtrDistribute> etrDistributeList;

    /**
     * 委托完成状况表list
     */
    private List<EtrFinishStatus> etrFinishStatusList;

    /**
     * 投标标位表dto
     */
    private List<EtrEntrustBidPrice> bidPriceList;

    /**
     * 委托指令明细
     */
    private List<EtrEntrustDetail> etrEntrustDetailList;
    /**
     * 清算速度
     */
    private String settleSpeedStr;

    /**
     * 金融产品名称
     */
    private String finprodName;

    /**
     * 金融产品代码
     */
    private String finprodId;

    /**
     * 市场代码
     */
    private String finprodMarketId;

    /**
     * 通道代码
     */
    private String chlId;

    private BigDecimal avaStandardStockPosition;

    /**
     * 转入资产代码
     */
    private String changeToFinprodMarketId;

    /**
     * 转入资产名称
     */
    private String changeToFinprodName;

    /**
     * 其他附加参数
     * @return map
     */
    private Map<String,Object> othInfo;

    /**
     * 流程id
     */
    private String workFlowId;

    /**
     * 附件
     */
    private MstFileInputParameter mstFileInputParameter;

    private EtrEntrustWw etrEntrustWw;

    private EtrEntrustWwDetail etrEntrustWwDetail;

    public EtrEntrustWw getEtrEntrustWw() {
        return etrEntrustWw;
    }

    public void setEtrEntrustWw(EtrEntrustWw etrEntrustWw) {
        this.etrEntrustWw = etrEntrustWw;
    }

    public EtrEntrustWwDetail getEtrEntrustWwDetail() {
        return etrEntrustWwDetail;
    }

    public void setEtrEntrustWwDetail(EtrEntrustWwDetail etrEntrustWwDetail) {
        this.etrEntrustWwDetail = etrEntrustWwDetail;
    }

    public List<EtrEntrustWwDetail> getEtrEntrustWwDetailList() {
        return etrEntrustWwDetailList;
    }

    public void setEtrEntrustWwDetailList(List<EtrEntrustWwDetail> etrEntrustWwDetailList) {
        this.etrEntrustWwDetailList = etrEntrustWwDetailList;
    }

    private List<EtrEntrustWwDetail> etrEntrustWwDetailList;

    public MstFileInputParameter getMstFileInputParameter() {
        return mstFileInputParameter;
    }

    public void setMstFileInputParameter(MstFileInputParameter mstFileInputParameter) {
        this.mstFileInputParameter = mstFileInputParameter;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public Map<String, Object> getOthInfo() {
        return othInfo;
    }

    public void setOthInfo(Map<String, Object> othInfo) {
        this.othInfo = othInfo;
    }

    public List<BaseEtrInvestInfoDto> getBaseEtrInvestInfoDtoList() {
        return baseEtrInvestInfoDtoList;
    }

    public void setBaseEtrInvestInfoDtoList(List<BaseEtrInvestInfoDto> baseEtrInvestInfoDtoList) {
        this.baseEtrInvestInfoDtoList = baseEtrInvestInfoDtoList;
    }

    /**
     * 取得 通道代码
     * @return 通道代码
     */
    public String getChlId() {
        return chlId;
    }

    /**
     * 设置 通道代码
     * @param chlId 通道代码
     */
    public void setChlId(String chlId) {
        this.chlId = chlId == null ? null : chlId.trim();
    }

    /**
     * 获取 etrEntrust
     *
     * @return etrEntrust.
     */
    public EtrEntrust getEtrEntrust() {
        return etrEntrust;
    }

    /**
     * 设置 etrEntrust
     *
     * @param etrEntrust etrEntrust
     */
    public void setEtrEntrust(EtrEntrust etrEntrust) {
        this.etrEntrust = etrEntrust;
    }

    /**
     * 获取 etrEntrustAdd
     *
     * @return etrEntrustAdd.
     */
    public EtrEntrustAdd getEtrEntrustAdd() {
        return etrEntrustAdd;
    }

    /**
     * 设置 etrEntrustAdd
     *
     * @param etrEntrustAdd etrEntrustAdd
     */
    public void setEtrEntrustAdd(EtrEntrustAdd etrEntrustAdd) {
        this.etrEntrustAdd = etrEntrustAdd;
    }

    /**
     * 获取 etrPledgeList
     *
     * @return etrPledgeList.
     */
    public List<EtrPledge> getEtrPledgeList() {
        return etrPledgeList;
    }

    /**
     * 设置 etrPledgeList
     *
     * @param etrPledgeList etrPledgeList
     */
    public void setEtrPledgeList(List<EtrPledge> etrPledgeList) {
        this.etrPledgeList = etrPledgeList;
    }

    /**
     * 获取 etrDistributeList
     *
     * @return etrDistributeList.
     */
    public List<EtrDistribute> getEtrDistributeList() {
        return etrDistributeList;
    }

    /**
     * 设置 etrDistributeList
     *
     * @param etrDistributeList etrDistributeList
     */
    public void setEtrDistributeList(List<EtrDistribute> etrDistributeList) {
        this.etrDistributeList = etrDistributeList;
    }

    /**
     * 获取 etrFinishStatusList
     *
     * @return etrFinishStatusList.
     */
    public List<EtrFinishStatus> getEtrFinishStatusList() {
        return etrFinishStatusList;
    }

    /**
     * 设置 etrFinishStatusList
     *
     * @param etrFinishStatusList etrFinishStatusList
     */
    public void setEtrFinishStatusList(List<EtrFinishStatus> etrFinishStatusList) {
        this.etrFinishStatusList = etrFinishStatusList;
    }

    /**
     * 获取 settleSpeedStr
     *
     * @return settleSpeedStr.
     */
    public String getSettleSpeedStr() {
        return settleSpeedStr;
    }

    /**
     * 设置 settleSpeedStr
     *
     * @param settleSpeedStr settleSpeedStr
     */
    public void setSettleSpeedStr(String settleSpeedStr) {
        this.settleSpeedStr = settleSpeedStr;
    }

    /**
     * 获取 finprodName
     *
     * @return finprodName.
     */
    public String getFinprodName() {
        return finprodName;
    }

    /**
     * 设置 finprodName
     *
     * @param finprodName finprodName
     */
    public void setFinprodName(String finprodName) {
        this.finprodName = finprodName;
    }

    /**
     * 获取 bidPriceList
     * @return bidPriceList.
     */
    public List<EtrEntrustBidPrice> getBidPriceList() {
        return bidPriceList;
    }
    /**
     * 获取 finprodMarketId
     *
     * @return finprodMarketId
     */
    public String getFinprodMarketId() {
        return finprodMarketId;
    }

    /**
     * 设置 bidPriceList
     * @param bidPriceList bidPriceList
     */
    public void setBidPriceList(List<EtrEntrustBidPrice> bidPriceList) {
        this.bidPriceList = bidPriceList;
    }



    /**
     * 设置 finprodMarketId
     *
     * @param finprodMarketId finprodMarketId
     */
    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }

    public BigDecimal getAvaStandardStockPosition() {
        return avaStandardStockPosition;
    }

    public void setAvaStandardStockPosition(BigDecimal avaStandardStockPosition) {
        this.avaStandardStockPosition = avaStandardStockPosition;
    }

    /**
     * 获取 finprodId
     *
     * @return finprodId
     */
    public String getFinprodId() {
        return finprodId;
    }

    /**
     * 设置 finprodId
     *
     * @param finprodId finprodId
     */
    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }

    /**
     * 获取 changeToFinprodMarketId
     *
     * @return changeToFinprodMarketId
     */
    public String getChangeToFinprodMarketId() {
        return changeToFinprodMarketId;
    }

    /**
     * 设置 changeToFinprodMarketId
     *
     * @param changeToFinprodMarketId changeToFinprodMarketId
     */
    public void setChangeToFinprodMarketId(String changeToFinprodMarketId) {
        this.changeToFinprodMarketId = changeToFinprodMarketId;
    }

    /**
     * 获取 changeToFinprodName
     *
     * @return changeToFinprodName
     */
    public String getChangeToFinprodName() {
        return changeToFinprodName;
    }

    public List<EtrEntrustDetail> getEtrEntrustDetailList() {
        return etrEntrustDetailList;
    }

    public void setEtrEntrustDetailList(List<EtrEntrustDetail> etrEntrustDetailList) {
        this.etrEntrustDetailList = etrEntrustDetailList;
    }

    /**
     * 设置 changeToFinprodName
     *
     * @param changeToFinprodName changeToFinprodName
     */
    public void setChangeToFinprodName(String changeToFinprodName) {
        this.changeToFinprodName = changeToFinprodName;
    }

}
