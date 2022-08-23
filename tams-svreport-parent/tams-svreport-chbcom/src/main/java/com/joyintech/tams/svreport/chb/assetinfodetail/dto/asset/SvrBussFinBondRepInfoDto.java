/*
 * 文件名：BussFinBondRepInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年10月23日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset;

import java.math.BigDecimal;

import com.joyintech.framework.validation.annotation.Length;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RepAssetBond;
import com.joyintech.tams.svreport.chb.assetinfodetail.validator.asset.SvrBussFinBondRepInfoValidator;

/**
 *  债券类中债报送DTO
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年10月23日
 * @see SvrBussFinBondRepInfoDto
 * @since 1.0
 */
@ValidLogic(targetClass = SvrBussFinBondRepInfoValidator.class , groups = {ValidateGroupEdit.class})
public class SvrBussFinBondRepInfoDto extends RepAssetBond{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 金融产品类型（投管分类），债券、理财产品、基金、理财产品模板、理财产品分层
     */
    private String finprodType2;
    
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
     * 是否投向金融资产投资公司或其附属机构发行的私募股权投资基金
     */
    private String repPrivatefundFlag;

    /**
     * 公共PPP
     */
    private String repPppflag;

    /**
     * 主体评级
     */
    private String repPartyGrade;

    public String getRepPartyGrade() {
        return repPartyGrade;
    }
    public void setRepPartyGrade(String repPartyGrade) {
        this.repPartyGrade = repPartyGrade;
    }

    /**
     * 是否小微企业信贷资产流转和收益权转让
     */
    private String repSmallcredit;

    /**
     * 是否投向投向市场化债转股相关产品的部分
     */
    private String repBondtostockFlag;

    /**
     * 是否投向金融资产投资公司发行的私募资产管理产品
     */
    private String repPrivateprdFlag;

    /**
     * 人行机构规模
     */
    private String repInstitSize;

    /**
     * 受让形式
     */
    private String repAssignee;

    /**
     * 交易场所
     */
    private String tradeMarket;


    /**
     * 债卷评级
     */
    private String secMainGrade;

    /**
     * 永续债属性
     *
     */
   private  String  isPerpetual;

    /**
     * 二级资产负债类别
     *
     */
   private   String  assetType2;

    /**
     * 代码
     * @return
     */
    private String assetId;

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
     * 是否非标转标资产
     */
    private String isFbzb;

    private String repIsFbzb;
    /**
     * 是否专项债券
     */
    private String bankSecZxzq;

    
    /**
     * 发行人代码
     */
    private String chbIssuerId;
    
    /**
     * 是否永续债
     */
    private String perpetualFlag;
    
    /**
     * 是否含权债
     */
    private String optionFlag;
    
    /**
     * 是否商业银行可转债
     */
    private String bankSecKzzq;
    
    /**
     * 结构化业务类型
     */
    private String constracteBusiType;
    
    /**
     * 结构化层级
     */
    private String constracteGrade;
    
    /**
     * 结构化杠杆倍数
     */
    private BigDecimal constracteHeaverTimes;

    /**
     * 票面利率
     */
    private BigDecimal assetYield;

    /**
     * 资产名称
     */
    private String assetName;

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType2() {
        return assetType2;
    }

    public void setAssetType2(String assetType2) {
        this.assetType2 = assetType2;
    }

    public String getIsPerpetual() {
        return isPerpetual;
    }

    public void setIsPerpetual(String isPerpetual) {
        this.isPerpetual = isPerpetual;
    }


    public String getSecMainGrade() {
        return secMainGrade;
    }

    public void setSecMainGrade(String secMainGrade) {
        this.secMainGrade = secMainGrade;
    }

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
     * 获取 finprodType2
     * @return finprodType2.
     */
    public String getFinprodType2() {
        return finprodType2;
    }


    
    /**
     * 设置 finprodType2
     * @param finprodType2 finprodType2
     */
    public void setFinprodType2(String finprodType2) {
        this.finprodType2 = finprodType2;
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


    @Length(fieldName = "BussFinBondRepInfo.repRegistorgComment", max = 256, groups = {ValidateGroupEdit.class})
    public String getRepRegistorgComment(){
        return super.getRepRegistorgComment();
    }

    public String getRepPrivatefundFlag() {
        return repPrivatefundFlag;
    }

    public void setRepPrivatefundFlag(String repPrivatefundFlag) {
        this.repPrivatefundFlag = repPrivatefundFlag;
    }

    public String getRepPppflag() {
        return repPppflag;
    }

    public void setRepPppflag(String repPppflag) {
        this.repPppflag = repPppflag;
    }

    public String getRepSmallcredit() {
        return repSmallcredit;
    }

    public void setRepSmallcredit(String repSmallcredit) {
        this.repSmallcredit = repSmallcredit;
    }

    public String getRepBondtostockFlag() {
        return repBondtostockFlag;
    }

    public void setRepBondtostockFlag(String repBondtostockFlag) {
        this.repBondtostockFlag = repBondtostockFlag;
    }

    public String getRepPrivateprdFlag() {
        return repPrivateprdFlag;
    }

    public void setRepPrivateprdFlag(String repPrivateprdFlag) {
        this.repPrivateprdFlag = repPrivateprdFlag;
    }

    public String getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(String tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    public String getRepInstitSize() {
        return repInstitSize;
    }

    public void setRepInstitSize(String repInstitSize) {
        this.repInstitSize = repInstitSize;
    }

    public String getRepAssignee() {
        return repAssignee;
    }

    public void setRepAssignee(String repAssignee) {
        this.repAssignee = repAssignee;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
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

    public String getIsFbzb() {
        return isFbzb;
    }

    public void setIsFbzb(String isFbzb) {
        this.isFbzb = isFbzb;
    }

    public String getBankSecZxzq() {
        return bankSecZxzq;
    }

    public void setBankSecZxzq(String bankSecZxzq) {
        this.bankSecZxzq = bankSecZxzq;
    }

    public String getRepIsFbzb() {
        return repIsFbzb;
    }

    public void setRepIsFbzb(String repIsFbzb) {
        this.repIsFbzb = repIsFbzb;
    }
    
	public String getChbIssuerId() {
		return chbIssuerId;
	}
	
	public void setChbIssuerId(String chbIssuerId) {
		this.chbIssuerId = chbIssuerId;
	}
	
	public String getPerpetualFlag() {
		return perpetualFlag;
	}
	
	public void setPerpetualFlag(String perpetualFlag) {
		this.perpetualFlag = perpetualFlag;
	}
	
	public String getOptionFlag() {
		return optionFlag;
	}
	
	public void setOptionFlag(String optionFlag) {
		this.optionFlag = optionFlag;
	}
	
	public String getBankSecKzzq() {
		return bankSecKzzq;
	}
	
	public void setBankSecKzzq(String bankSecKzzq) {
		this.bankSecKzzq = bankSecKzzq;
	}
	
	public String getConstracteBusiType() {
		return constracteBusiType;
	}
	
	public void setConstracteBusiType(String constracteBusiType) {
		this.constracteBusiType = constracteBusiType;
	}
	
	public String getConstracteGrade() {
		return constracteGrade;
	}
	
	public void setConstracteGrade(String constracteGrade) {
		this.constracteGrade = constracteGrade;
	}
	
	public BigDecimal getConstracteHeaverTimes() {
		return constracteHeaverTimes;
	}	
	
	public void setConstracteHeaverTimes(BigDecimal constracteHeaverTimes) {
		this.constracteHeaverTimes = constracteHeaverTimes;
	}

    public BigDecimal getAssetYield() {
        return assetYield;
    }

    public void setAssetYield(BigDecimal assetYield) {
        this.assetYield = assetYield;
    }
}
