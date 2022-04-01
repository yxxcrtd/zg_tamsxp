package com.joyintech.tams.invest.common.dto.etr;

import com.joyintech.tams.common.base.entity.EtrCashPositionFlow;

import java.util.List;

/**
 * @program: microservice
 * @description: 组合现金流水
 * @author: 强成西
 * @create: 2018-09-30 10:25
 **/
public class EtrCashPositionFlowDto extends EtrCashPositionFlow {
    /**
     * 通道金融产品代码
     */
    private String finprodId;

    /**
     * 账户类型
     */
    private List<String> acctType;

    /**
     * 是否添加账户类型条件
     */
    private boolean needAcctType;

    private String splitType;

    /**
     * 数据已入新头寸，Y：已入
     */
    private String isExistNew;

    public String getSplitType() {
		return splitType;
	}

	public void setSplitType(String splitType) {
		this.splitType = splitType;
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
     * 获取 acctType
     *
     * @return acctType
     */
    public List<String> getAcctType() {
        return acctType;
    }

    /**
     * 设置 acctType
     *
     * @param acctType acctType
     */
    public void setAcctType(List<String> acctType) {
        this.acctType = acctType;
    }

    /**
     * 获取 needAcctType
     *
     * @return needAcctType
     */
    public boolean isNeedAcctType() {
        return needAcctType;
    }

    /**
     * 设置 needAcctType
     *
     * @param needAcctType needAcctType
     */
    public void setNeedAcctType(boolean needAcctType) {
        this.needAcctType = needAcctType;
    }

    /**
     * 数据已入新头寸，Y：已入
     */
    public String getIsExistNew() {
        return isExistNew;
    }

    /**
     * 数据已入新头寸，Y：已入
     */
    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }
}
