package com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.dto;

import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.tams.common.base.entity.MutiptlPortfolioTree;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

/**
 * 对账参数设置DTO
 */
public class MutiptlPortfolioTreeDto extends MutiptlPortfolioTree implements ITableRow {

    /**
     * 画面选中的项
     */
    private transient List<MutiptlPortfolioTreeDto> selectedItem;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 元素ID
     */
    private String elementId;

    public List<MutiptlPortfolioTreeDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<MutiptlPortfolioTreeDto> selectedItem) {
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


}
