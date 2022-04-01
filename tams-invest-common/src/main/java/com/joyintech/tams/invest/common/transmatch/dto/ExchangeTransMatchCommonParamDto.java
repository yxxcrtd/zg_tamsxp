package com.joyintech.tams.invest.common.transmatch.dto;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.common.base.entity.SettleTransDataTotalMatch;

import java.util.List;

public class ExchangeTransMatchCommonParamDto extends BaseEntity {

    /**
     * 待处理数据列表
     */
    private List<ExchangeTransMatchCommonDto> paramDataList;

    public List<ExchangeTransMatchCommonDto> getParamDataList() {
        return paramDataList;
    }

    public void setParamDataList(List<ExchangeTransMatchCommonDto> paramDataList) {
        this.paramDataList = paramDataList;
    }

    /**
     * 待撤销数据列表
     */
    private List<SettleTransDataTotalMatch> revokeDataList;

    public List<SettleTransDataTotalMatch> getRevokeDataList() {
        return revokeDataList;
    }

    public void setRevokeDataList(List<SettleTransDataTotalMatch> revokeDataList) {
        this.revokeDataList = revokeDataList;
    }

    private List<String> needUpdateStatusEntrustIdList;

    public List<String> getNeedUpdateStatusEntrustIdList() {
        return needUpdateStatusEntrustIdList;
    }

    public void setNeedUpdateStatusEntrustIdList(List<String> needUpdateStatusEntrustIdList) {
        this.needUpdateStatusEntrustIdList = needUpdateStatusEntrustIdList;
    }
}
