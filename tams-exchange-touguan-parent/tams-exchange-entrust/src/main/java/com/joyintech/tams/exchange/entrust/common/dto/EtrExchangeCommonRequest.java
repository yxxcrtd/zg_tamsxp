package com.joyintech.tams.exchange.entrust.common.dto;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.tams.exchange.entrust.common.validator.EtrExchangeCommonValidator;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

public class EtrExchangeCommonRequest extends BaseEntity implements ProcessEntity {

    /**
     * 画面代码
     */
    private String pageId;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 画面代码List
     */
    private List<String> pageIdList;

    /**
     * 业务主键1
     */
    private String businessDataPk1;

    /**
     * 业务主键2
     */
    private String businessDataPk2;

    /**
     * 业务主键3
     */
    private String businessDataPk3;

    /**
     * 业务主键4
     */
    private String businessDataPk4;

    /**
     * 业务主键5
     */
    private String businessDataPk5;

    /**
     * 业务主键6
     */
    private String businessDataPk6;

    /**
     * 业务主键7
     */
    private String businessDataPk7;

    /**
     * 业务主键8
     */
    private String businessDataPk8;

    /**
     * 业务主键9
     */
    private String businessDataPk9;

    /**
     * 业务主键10
     */
    private String businessDataPk10;

    /**
     * 查询模式(查询流程中数据为：01，查询数据状态为：02)
     */
    private String searchMode;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public List<String> getPageIdList() {
        return pageIdList;
    }

    public void setPageIdList(List<String> pageIdList) {
        this.pageIdList = pageIdList;
    }

    public String getBusinessDataPk1() {
        return businessDataPk1;
    }

    public void setBusinessDataPk1(String businessDataPk1) {
        this.businessDataPk1 = businessDataPk1;
    }

    public String getBusinessDataPk2() {
        return businessDataPk2;
    }

    public void setBusinessDataPk2(String businessDataPk2) {
        this.businessDataPk2 = businessDataPk2;
    }

    public String getBusinessDataPk3() {
        return businessDataPk3;
    }

    public void setBusinessDataPk3(String businessDataPk3) {
        this.businessDataPk3 = businessDataPk3;
    }

    public String getBusinessDataPk4() {
        return businessDataPk4;
    }

    public void setBusinessDataPk4(String businessDataPk4) {
        this.businessDataPk4 = businessDataPk4;
    }

    public String getBusinessDataPk5() {
        return businessDataPk5;
    }

    public void setBusinessDataPk5(String businessDataPk5) {
        this.businessDataPk5 = businessDataPk5;
    }

    public String getBusinessDataPk6() {
        return businessDataPk6;
    }

    public void setBusinessDataPk6(String businessDataPk6) {
        this.businessDataPk6 = businessDataPk6;
    }

    public String getBusinessDataPk7() {
        return businessDataPk7;
    }

    public void setBusinessDataPk7(String businessDataPk7) {
        this.businessDataPk7 = businessDataPk7;
    }

    public String getBusinessDataPk8() {
        return businessDataPk8;
    }

    public void setBusinessDataPk8(String businessDataPk8) {
        this.businessDataPk8 = businessDataPk8;
    }

    public String getBusinessDataPk9() {
        return businessDataPk9;
    }

    public void setBusinessDataPk9(String businessDataPk9) {
        this.businessDataPk9 = businessDataPk9;
    }

    public String getBusinessDataPk10() {
        return businessDataPk10;
    }

    public void setBusinessDataPk10(String businessDataPk10) {
        this.businessDataPk10 = businessDataPk10;
    }
}
