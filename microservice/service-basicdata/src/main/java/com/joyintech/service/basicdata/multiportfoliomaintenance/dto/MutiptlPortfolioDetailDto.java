package com.joyintech.service.basicdata.multiportfoliomaintenance.dto;

import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.tams.common.base.entity.MutiptlPortfolioDetail;
import com.joyintech.tams.common.base.entity.MutiptlPortfolioTree;

import java.util.List;

/**
 * 组合账户
 */
public class MutiptlPortfolioDetailDto extends MutiptlPortfolioDetail implements ITableRow {
    /**
     * 组合名称
     */
    private String portfolioName;
    /**
     * 资金账户
     */
    private String acctName;
    /**
     * 画面选中的项
     */
    private transient List<MutiptlPortfolioDetailDto> selectedItem;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 元素ID
     */
    private String elementId;

    public List<MutiptlPortfolioDetailDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<MutiptlPortfolioDetailDto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }
}
