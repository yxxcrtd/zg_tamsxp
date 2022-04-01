package com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.dto;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.tams.common.base.entity.MutiptlPortfolioDetail;

import java.util.List;

/**
 * 组合账户
 */
/**
 * 对账参数设置DTO
 */
@PageId("MultiPortfolioMaintenance002")
public class MutiptlPortfolioDetailDto extends MutiptlPortfolioDetail implements ITableRow {

    private String portfolioName;

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

}
