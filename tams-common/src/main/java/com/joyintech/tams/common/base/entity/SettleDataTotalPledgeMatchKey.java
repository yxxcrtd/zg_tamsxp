package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

public class SettleDataTotalPledgeMatchKey extends BaseEntity {
    /**
     * 汇总成交数据汇总的流水号
     */
    private String seqNo;

    /**
     * 指令编号
     */
    private String entrunstTradeId;

    /**
     * 质押金融产品代码
     */
    private String pledgeFinprodId;

    /**
     * 分支序号
     */
    private Long branch;

    /**
     * 匹配方式，手工匹配，自动匹配
     */
    private String matchClass;

    /**
     * 取得 汇总成交数据汇总的流水号
     * @return 汇总成交数据汇总的流水号
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * 设置 汇总成交数据汇总的流水号
     * @param seqNo 汇总成交数据汇总的流水号
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    /**
     * 取得 指令编号
     * @return 指令编号
     */
    public String getEntrunstTradeId() {
        return entrunstTradeId;
    }

    /**
     * 设置 指令编号
     * @param entrunstTradeId 指令编号
     */
    public void setEntrunstTradeId(String entrunstTradeId) {
        this.entrunstTradeId = entrunstTradeId == null ? null : entrunstTradeId.trim();
    }

    /**
     * 取得 质押金融产品代码
     * @return 质押金融产品代码
     */
    public String getPledgeFinprodId() {
        return pledgeFinprodId;
    }

    /**
     * 设置 质押金融产品代码
     * @param pledgeFinprodId 质押金融产品代码
     */
    public void setPledgeFinprodId(String pledgeFinprodId) {
        this.pledgeFinprodId = pledgeFinprodId == null ? null : pledgeFinprodId.trim();
    }

    /**
     * 取得 分支序号
     * @return 分支序号
     */
    public Long getBranch() {
        return branch;
    }

    /**
     * 设置 分支序号
     * @param branch 分支序号
     */
    public void setBranch(Long branch) {
        this.branch = branch;
    }

    /**
     * 取得 匹配方式，手工匹配，自动匹配
     * @return 匹配方式，手工匹配，自动匹配
     */
    public String getMatchClass() {
        return matchClass;
    }

    /**
     * 设置 匹配方式，手工匹配，自动匹配
     * @param matchClass 匹配方式，手工匹配，自动匹配
     */
    public void setMatchClass(String matchClass) {
        this.matchClass = matchClass == null ? null : matchClass.trim();
    }
}