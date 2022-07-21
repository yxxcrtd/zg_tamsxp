/*
 * 文件名：BussRepDepositInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年9月17日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset;

import com.joyintech.framework.validation.annotation.Length;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RepAssetBankDeposit;
import com.joyintech.tams.common.base.entity.RepAssetDept;
import com.joyintech.tams.svreport.chb.assetinfodetail.validator.asset.SvrBussRepDepositInfoValidator;

import java.math.BigDecimal;
import java.util.List;

/**
 * 存款类中债报送信息DTO
 * @author zhangnian
 * @version 1.0 2018年9月17日
 * @see SvrBussRepDepositInfoDto
 * @since 1.0
 */
@ValidLogic(targetClass = SvrBussRepDepositInfoValidator.class , groups = {ValidateGroupEdit.class})
public class SvrBussRepDepositInfoDto extends RepAssetBankDeposit{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;
    /**
     * 二级资产类型
     */
    private String type6;
    /**
     * 资产/负债类别
     */
    private String repAssetType;
    
    /**
     * 交易流通场所
     */
    private String repTradeMarket;
    
    /**
     * 币种
     */
    private String repCcy;


    /**
     * 新加字段资金存入银行
     */
    private  String  bankName;

    /**
     * 新加字段存款金额
     */
    private BigDecimal  amt;

    /**
     * 新加字段年利率
     */
    private BigDecimal assetYield;

    /**
     * 新加字段计息基础
     */
    private String basis;

    /**
     * 新人行资产一级分类
     */
    private String repPbcAssetType1;

    /**
     * 新人行资产二级分类
     */
    private String repPbcAssetType2;

    /**
     * 新人行资产三级分类
     */
    private String repPbcAssetType3;

    /**
     * 存款账号
     */
    private String repAccountNo;

    /**
     * 存/借款机构
     */
    private String financie;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getAssetYield() {
        return assetYield;
    }

    public void setAssetYield(BigDecimal assetYield) {
        this.assetYield = assetYield;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    /**
     * 资产负债报送信息entity
     */
    private RepAssetDept repAssetDept;
    
    private List<RepAssetDept> repAssetDeptList;
    
    /**
     * 存款资产报送信息entity
     */
    private RepAssetBankDeposit repAssetBankDeposit;




    /**
     * 获取 repAssetType
     * @return repAssetType.
     */
    public String getRepAssetType() {
        return repAssetType;
    }

    
    /**
     * 设置 repAssetType
     * @param repAssetType repAssetType
     */
    public void setRepAssetType(String repAssetType) {
        this.repAssetType = repAssetType;
    }

    


    
    /**
     * 获取 repAssetDept
     * @return repAssetDept.
     */
    public RepAssetDept getRepAssetDept() {
        return repAssetDept;
    }


    
    /**
     * 设置 repAssetDept
     * @param repAssetDept repAssetDept
     */
    public void setRepAssetDept(RepAssetDept repAssetDept) {
        this.repAssetDept = repAssetDept;
    }


    
    /**
     * 获取 repAssetBankDeposit
     * @return repAssetBankDeposit.
     */
    public RepAssetBankDeposit getRepAssetBankDeposit() {
        return repAssetBankDeposit;
    }


    
    /**
     * 设置 repAssetBankDeposit
     * @param repAssetBankDeposit repAssetBankDeposit
     */
    public void setRepAssetBankDeposit(RepAssetBankDeposit repAssetBankDeposit) {
        this.repAssetBankDeposit = repAssetBankDeposit;
    }


    
    /**
     * 获取 repAssetDeptList
     * @return repAssetDeptList.
     */
    public List<RepAssetDept> getRepAssetDeptList() {
        return repAssetDeptList;
    }
    
    /**
     * 设置 repAssetDeptList
     * @param repAssetDeptList repAssetDeptList
     */
    public void setRepAssetDeptList(List<RepAssetDept> repAssetDeptList) {
        this.repAssetDeptList = repAssetDeptList;
    }


    
    /**
     * 获取 type6
     * @return type6.
     */
    public String getType6() {
        return type6;
    }


    
    /**
     * 设置 type6
     * @param type6 type6
     */
    public void setType6(String type6) {
        this.type6 = type6;
    }


    
    /**
     * 获取 repTradeMarket
     * @return repTradeMarket.
     */
    public String getRepTradeMarket() {
        return repTradeMarket;
    }


    
    /**
     * 设置 repTradeMarket
     * @param repTradeMarket repTradeMarket
     */
    public void setRepTradeMarket(String repTradeMarket) {
        this.repTradeMarket = repTradeMarket;
    }


    
    /**
     * 获取 repCcy
     * @return repCcy.
     */
    public String getRepCcy() {
        return repCcy;
    }


    
    /**
     * 设置 repCcy
     * @param repCcy repCcy
     */
    public void setRepCcy(String repCcy) {
        this.repCcy = repCcy;
    }
    
    @Length(fieldName = "BussRepDepositInfo.repTarget", max = 256, groups = {ValidateGroupEdit.class})
    public String getRepTarget(){
        return super.getRepTarget();
    }

    public String getRepPbcAssetType1() {
        return repPbcAssetType1;
    }

    public void setRepPbcAssetType1(String repPbcAssetType1) {
        this.repPbcAssetType1 = repPbcAssetType1;
    }

    public String getRepPbcAssetType2() {
        return repPbcAssetType2;
    }

    public void setRepPbcAssetType2(String repPbcAssetType2) {
        this.repPbcAssetType2 = repPbcAssetType2;
    }

    public String getRepPbcAssetType3() {
        return repPbcAssetType3;
    }

    public void setRepPbcAssetType3(String repPbcAssetType3) {
        this.repPbcAssetType3 = repPbcAssetType3;
    }

    public String getRepAccountNo() {
        return repAccountNo;
    }

    public void setRepAccountNo(String repAccountNo) {
        this.repAccountNo = repAccountNo;
    }

    public String getFinancie() {
        return financie;
    }

    public void setFinancie(String financie) {
        this.financie = financie;
    }
}
