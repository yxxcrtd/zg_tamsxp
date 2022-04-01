/*
 * 文件名：MstCaptialAccountDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：huwenbin
 * 修改时间：2018年8月3日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.basicdata.mstcaptialaccount.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.joyintech.framework.validation.annotation.Code;
import com.joyintech.tams.common.base.entity.StlCapacctInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import com.joyintech.framework.base.dto.UploadFile;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.grid.GridRowDto;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.Length;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.invest.basicdata.mstcaptialaccount.validator.MstCaptialAccountValidator;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.workflow.engine.dto.ProcessEntity;
/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author huwenbin
 * @version 1.0 2018年8月20日
 * @see MstCaptialAccountDto
 * @since 1.0
 */
@PageId("MstCaptialAccount002")
@ValidLogic(targetClass = MstCaptialAccountValidator.class, groups = {ValidateGroupEdit.class,
    ValidateGroupDelete.class})
public class MstCaptialAccountDto extends StlCapacctInfo implements ITableRow, ProcessEntity, GridRowDto{
    
    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 操作类型
     */
    private String operateType;
    
    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private String elementId;
    
    /**
     * 选中的数据
     */
    private transient List<MstCaptialAccountDto> selectedItem;
    
    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private String pageModel;
    
    /**
     * 文件名称
     */
    private String fileName;
    
    /**
     * 文件编号
     */
    private String fileNo;
    
    /**
     * 文件服务器路径
     */
    private String path;
    
    
    /**
     * 生效日
     */
    private Date effectDate;
    
    /**
     * 计息基础
     */
    private String basis;
    
    /**
     * 利率类型
     */
    private String rateType;
    
    /**
     * 利率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal rate;
    /**
     * 活期利率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal currentRate;
    
    /**
     * 基准ID
     */
    private String indexId;
    
    /**
     * 基准来源
     */
    private String indexSource;
    /**
     * 系数
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal coefficient;
    /**
     * 利差
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal spreadRate;
    
    /**
     * 账户类型
     */
    private String accountType;
    
    /**
     * 是否计提活期利息
     */
    private String isCalint;

    /**
     * 
     */
    private List<UploadFile> fileArray;
    
    /*双录要素 start */
    /**
     * 是否第一个审批节点
     */
    private String isFirstApprovalNode;
    
    /**
     * 工作流操作类型
     */
    private String wflOperType;
    /**
     * 币种-对比
     */
    private String ccyContrast;
    
    /**
     * 开户行-对比
     */
    private String bankDepContrast;
    
    /**
     * 大额支付系统号/网点号 -对比
     */
    private String largePaynoContrast;
    
    /*双录要素 end */
    /**
     * 获取 fileArray
     * @return fileArray.
     */
    public List<UploadFile> getFileArray() {
        return fileArray;
    }

    
    /**
     * 设置 fileArray
     * @param fileArray fileArray
     */
    public void setFileArray(List<UploadFile> fileArray) {
        this.fileArray = fileArray;
    }

    public String getCcyStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.币种, super.getCcy());
    }
    
    public String getFileNo() {
        return fileNo;
    }


    
    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }


    
    public String getPath() {
        return path;
    }


    
    public void setPath(String path) {
        this.path = path;
    }


    public String getFileName() {
        return fileName;
    }

    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    @Length(fieldName = "MstCaptialAccount002.accountId", max = 30, groups = {ValidateGroupEdit.class})
    @NotEmpty(fieldName = "MstCaptialAccount002.accountId", groups = {ValidateGroupEdit.class})
    @PrimaryKey(value=Field.PK_1)
    public String getBusiAcctNo() {
        return super.getBusiAcctNo();
    }
    
    @Override
    @NotEmpty(fieldName = "MstCaptialAccount001.accountName", groups = {ValidateGroupEdit.class})
    @Length(fieldName = "MstCaptialAccount001.accountName", max = 200, groups = {
        ValidateGroupEdit.class})
    public String getAcctName() {
        return super.getAcctName();
    }
    
    @Override
    @Length(fieldName = "MstCaptialAccount001.ccy", max = 50, groups = {ValidateGroupEdit.class})
    @NotEmpty(fieldName = "MstCaptialAccount001.ccy", groups = {ValidateGroupEdit.class})
    @PrimaryKey(value=Field.PK_3)
    public String getCcy() {
        return super.getCcy();
    }
    
    @Override
    @NotEmpty(fieldName = "MstCaptialAccount001.bankDep", groups = {ValidateGroupEdit.class})
    @Length(fieldName = "MstCaptialAccount001.bankDep", max = 50, groups = {
        ValidateGroupEdit.class})
    public String getBankDep() {
        return super.getBankDep();
    }
    
    @Override
//    @NotEmpty(fieldName = "MstCaptialAccount002.accountOutlet", groups = {ValidateGroupEdit.class})
    @Length(fieldName = "MstCaptialAccount002.accountOutlet", max = 20, groups = {
        ValidateGroupEdit.class})
    @Code(fieldName = "MstCaptialAccount002.accountOutlet", length = 20, groups = {
            ValidateGroupEdit.class})
    @PrimaryKey(value=Field.PK_2)
    public String getLargePayno() {
        return super.getLargePayno();
    }
    
    @Override
    /*@NotEmpty(fieldName = "MstCaptialAccount002.swiftCode", groups = {ValidateGroupEdit.class})*/
    @Length(fieldName = "MstCaptialAccount002.swiftCode", max = 50, groups = {
        ValidateGroupEdit.class})
    public String getSwiftCode() {
        return super.getSwiftCode();
    }
    
    @Length(fieldName = "MstCaptialAccount002.remark", max = 1000, groups = {
        ValidateGroupEdit.class})
    public String getRemark(){
        return super.getRemark();
    }
    
    public List<MstCaptialAccountDto> getSelectedItem() {
        return selectedItem;
    }



    
    public void setSelectedItem(List<MstCaptialAccountDto> selectedItem) {
        this.selectedItem = selectedItem;
    }



    public String getElementId() {
        return elementId;
    }


    
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }


    public String getOperateType() {
        return operateType;
    }

    
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    
    
    public String getPageModel() {
        return pageModel;
    }

    
    public void setPageModel(String pageModel) {
        this.pageModel = pageModel;
    }

    
    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getParentId() {
        return null;
    }

    @Override
    public String getRowId() {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public void setId(String arg0) {
        
    }

    @Override
    public void setLeaf(boolean arg0) {
        
    }

    @Override
    public void setName(String arg0) {
        
    }

    @Override
    public void setOrder(int arg0) {
        
    }

    @Override
    public void setParentId(String arg0) {
        
    }

    @Override
    public void setRowId(String arg0) {
        
    }


    
    /**
     * 获取 isFirstApprovalNode
     * @return isFirstApprovalNode.
     */
    public String getIsFirstApprovalNode() {
        return isFirstApprovalNode;
    }


    
    /**
     * 设置 isFirstApprovalNode
     * @param isFirstApprovalNode isFirstApprovalNode
     */
    public void setIsFirstApprovalNode(String isFirstApprovalNode) {
        this.isFirstApprovalNode = isFirstApprovalNode;
    }


    
    /**
     * 获取 ccyContrast
     * @return ccyContrast.
     */
    public String getCcyContrast() {
        return ccyContrast;
    }


    
    /**
     * 设置 ccyContrast
     * @param ccyContrast ccyContrast
     */
    public void setCcyContrast(String ccyContrast) {
        this.ccyContrast = ccyContrast;
    }


    
    /**
     * 获取 bankDepContrast
     * @return bankDepContrast.
     */
    public String getBankDepContrast() {
        return bankDepContrast;
    }


    
    /**
     * 设置 bankDepContrast
     * @param bankDepContrast bankDepContrast
     */
    public void setBankDepContrast(String bankDepContrast) {
        this.bankDepContrast = bankDepContrast;
    }


    
    /**
     * 获取 largePaynoContrast
     * @return largePaynoContrast.
     */
    public String getLargePaynoContrast() {
        return largePaynoContrast;
    }


    
    /**
     * 设置 largePaynoContrast
     * @param largePaynoContrast largePaynoContrast
     */
    public void setLargePaynoContrast(String largePaynoContrast) {
        this.largePaynoContrast = largePaynoContrast;
    }


    /**
     * 获取 wflOperType
     * @return wflOperType.
     */
    public String getWflOperType() {
        return wflOperType;
    }


    /**
     * 设置 wflOperType
     * @param wflOperType wflOperType
     */
    public void setWflOperType(String wflOperType) {
        this.wflOperType = wflOperType;
    }

    /**
     * 获取 effectDate
     * @return effectDate.
     */
    public Date getEffectDate() {
        return effectDate;
    }


    
    /**
     * 设置 effectDate
     * @param effectDate effectDate
     */
    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }


    
    /**
     * 获取 basis
     * @return basis.
     */
    public String getBasis() {
        return basis;
    }


    
    /**
     * 设置 basis
     * @param basis basis
     */
    public void setBasis(String basis) {
        this.basis = basis;
    }


    
    /**
     * 获取 rateType
     * @return rateType.
     */
    public String getRateType() {
        return rateType;
    }


    
    /**
     * 设置 rateType
     * @param rateType rateType
     */
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }


    
    /**
     * 获取 rate
     * @return rate.
     */
    public BigDecimal getRate() {
        return rate;
    }


    
    /**
     * 设置 rate
     * @param rate rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }


    
    /**
     * 获取 currentRate
     * @return currentRate.
     */
    public BigDecimal getCurrentRate() {
        return currentRate;
    }


    
    /**
     * 设置 currentRate
     * @param currentRate currentRate
     */
    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
    }


    
    /**
     * 获取 coefficient
     * @return coefficient.
     */
    public BigDecimal getCoefficient() {
        return coefficient;
    }


    
    /**
     * 设置 coefficient
     * @param coefficient coefficient
     */
    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }


    
    /**
     * 获取 spreadRate
     * @return spreadRate.
     */
    public BigDecimal getSpreadRate() {
        return spreadRate;
    }


    
    /**
     * 设置 spreadRate
     * @param spreadRate spreadRate
     */
    public void setSpreadRate(BigDecimal spreadRate) {
        this.spreadRate = spreadRate;
    }
    
    /**
     * 取得 账户性质
     * @return 账户性质
     */
    @NotEmpty(fieldName = "MstCaptialAccount002.accountNature",groups = {ValidateGroupEdit.class})
    public String getAccountNature() {
        return super.getAccountNature();
    }


    /**
     * 获取 indexId
     * @return indexId.
     */
    public String getIndexId() {
        return indexId;
    }


    /**
     * 设置 indexId
     * @param indexId indexId
     */
    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }


    /**
     * 获取 indexSource
     * @return indexSource.
     */
    public String getIndexSource() {
        return indexSource;
    }


    /**
     * 设置 indexSource
     * @param indexSource indexSource
     */
    public void setIndexSource(String indexSource) {
        this.indexSource = indexSource;
    }

    /**
     * 获取 accountType
     * @return accountType.
     */
    public String getAccountType() {
        return accountType;
    }
    
    /**
     * 设置 accountType
     * @param accountType accountType
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    
    /**
     * 获取 isCalint
     * @return isCalint.
     */
    public String getIsCalint() {
        return isCalint;
    }


    
    /**
     * 设置 isCalint
     * @param isCalint isCalint
     */
    public void setIsCalint(String isCalint) {
        this.isCalint = isCalint;
    }

}
