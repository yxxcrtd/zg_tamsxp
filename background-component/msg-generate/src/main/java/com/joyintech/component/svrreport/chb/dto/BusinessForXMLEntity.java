/*
 * 文件名：Business101Entity.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：刘溶容-Dosgi.requiredJavaVersion=1.8
 * 修改时间：2016年11月7日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.component.svrreport.chb.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


/**
 * 101业务Entity
 * 
 * @author 刘溶容-Dosgi.requiredJavaVersion=1.8
 * @version 1.0 2016年11月7日
 * @see
 * @since 1.0
 */
@XmlRootElement(name = "xbrli:xbrl")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessForXMLEntity extends XbrliXbrlEntity {

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:FilingRegistrationTuple")
    private List<BaseInfoEntity> filingRegistrationTuple;

    /**
     * 子节点
     * 增加公私募产品申报主标签 @author zhaoyongsheng 20201028
     */
    @XmlElement(name = "wemax:FilingRegistrationElementsOfPublicOrPrivateProductTuple")
    private List<BaseInfoEntity> filingRegistrationElementsOfPublicOrPrivateProductTuple;
    /**
     * 子节点
     */
    @XmlElement(name = "wemax:IssueRegistrationForAgencyAndPrivateBankTuple")
    private List<BaseInfoEntity> issueRegistrationForAgencyAndPrivateBankTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:IssueRegistrationTuple")
    private List<BaseInfoEntity> issueRegistrationTuple;
    /**
     * 子节点
     * 增加公私募产品发行主标签 @author zhaoyongsheng 20201028
     */
    @XmlElement(name = "wemax:IssueRegistrationForPublicOrPrivateProductTuple")
    private List<BaseInfoEntity> issueRegistrationForPublicOrPrivateProductTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:InitialSubscriptionRegistrationTuple")
    private List<BaseInfoEntity> initialSubscriptionRegistrationTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:SubsequentSubscriptionRegistrationTuple")
    private List<BaseInfoEntity> subsequentSubscriptionRegistrationTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:AssetsAndLiabilitiesRegistrationTuple")
    private List<BaseInfoEntity> assetsAndLiabilitiesRegistrationTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:TransactionRegistrationTuple")
    private List<BaseInfoEntity> transactionRegistrationTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:ValuationRegistrationTuple")
    private List<BaseInfoEntity> valuationRegistrationTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:AssetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple")
    private List<BaseInfoEntity> assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple;

    /**
     * 子节点
     * 增加底层持仓主标签 @author zhaoyongsheng 20201028
     */
    @XmlElement(name = "wemax:UnderlyingAssetsLiabilitiesRegistrationTuple")
    private List<BaseInfoEntity> underlyingAssetsLiabilitiesRegistrationTuple;

    
    /**
     * 子节点
     */
    @XmlElement(name = "wemax:TerminationRegistrationTuple")
    private List<BaseInfoEntity> terminationRegistrationTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:PractitionerRegistrationTuple")
    private List<BaseInfoEntity> practitionerRegistrationTuple;

    /***0722升级**/
    /**
     * 子节点
     */
    @XmlElement(name = "wemax:ProductStatusRegistrationTuple")
    private List<BaseInfoEntity> ProductStatusRegistrationTuple;

    /**
     * 子节点
     */
    @XmlElement(name = "wemax:ProductInvestedAssetsLiabilitiesRegistrationTuple")
    private List<BaseInfoEntity> ProductInvestedAssetsLiabilitiesRegistrationTuple;
    /***0722升级**/

    /**
     * 子节点(分类总量)
     */
    @XmlElement(name = "wemax:InvestorsSummationOfDiffenrentTypesInformationRegistrationTuple")
    private List<BaseInfoEntity> investorsSummationOfDiffenrentTypesInformationRegistrationTuple;

    /**
     * 投资者身份信息登记
     */
    @XmlElement(name = "wemax:InvestorsInformationRegistrationCSVDataExplanatory")
    private CommonNodeEntity investorsInformationRegistrationCSVDataExplanatory;

    /**
     * 投资者持有信息登记
     */
    @XmlElement(name = "wemax:InvestorsInvestedInformationRegistrationCsvDataExplanatory")
    private CommonNodeEntity investorsInvestedInformationRegistrationCsvDataExplanatory;

    
    /**
     * 获取 investorsInvestedInformationRegistrationCsvDataExplanatory
     * @return investorsInvestedInformationRegistrationCsvDataExplanatory.
     */
    public CommonNodeEntity getInvestorsInvestedInformationRegistrationCsvDataExplanatory() {
        return investorsInvestedInformationRegistrationCsvDataExplanatory;
    }

    
    /**
     * 设置 investorsInvestedInformationRegistrationCsvDataExplanatory
     * @param investorsInvestedInformationRegistrationCsvDataExplanatory investorsInvestedInformationRegistrationCsvDataExplanatory
     */
    public void setInvestorsInvestedInformationRegistrationCsvDataExplanatory(CommonNodeEntity investorsInvestedInformationRegistrationCsvDataExplanatory) {
        this.investorsInvestedInformationRegistrationCsvDataExplanatory = investorsInvestedInformationRegistrationCsvDataExplanatory;
    }

    /**
     * 业务明细标签
     */
    @XmlElement(name = "wemax:InvestorsBusinessDetailsInformationRegistrationCsvDataExplanatory")
    private CommonNodeEntity investorsBusinessDetailsInformationRegistrationCsvDataExplanatory;

    /**
     * 获取 filingRegistrationTuple
     * 
     * @return filingRegistrationTuple.
     */
    public List<BaseInfoEntity> getFilingRegistrationTuple() {
        return filingRegistrationTuple;
    }

    /**
     * 设置 filingRegistrationTuple
     * 
     * @param filingRegistrationTuple filingRegistrationTuple
     */
    public void setFilingRegistrationTuple(List<BaseInfoEntity> filingRegistrationTuple) {
        this.filingRegistrationTuple = filingRegistrationTuple;
    }

    /**
     * 获取 issueRegistrationForAgencyAndPrivateBankTuple
     * 
     * @return issueRegistrationForAgencyAndPrivateBankTuple.
     */
    public List<BaseInfoEntity> getIssueRegistrationForAgencyAndPrivateBankTuple() {
        return issueRegistrationForAgencyAndPrivateBankTuple;
    }

    /**
     * 设置 issueRegistrationForAgencyAndPrivateBankTuple
     * 
     * @param issueRegistrationForAgencyAndPrivateBankTuple
     *            issueRegistrationForAgencyAndPrivateBankTuple
     */
    public void setIssueRegistrationForAgencyAndPrivateBankTuple(List<BaseInfoEntity> issueRegistrationForAgencyAndPrivateBankTuple) {
        this.issueRegistrationForAgencyAndPrivateBankTuple = issueRegistrationForAgencyAndPrivateBankTuple;
    }

    /**
     * 获取 issueRegistrationTuple
     * 
     * @return issueRegistrationTuple.
     */
    public List<BaseInfoEntity> getIssueRegistrationTuple() {
        return issueRegistrationTuple;
    }

    /**
     * 设置 issueRegistrationTuple
     * 
     * @param issueRegistrationTuple issueRegistrationTuple
     */
    public void setIssueRegistrationTuple(List<BaseInfoEntity> issueRegistrationTuple) {
        this.issueRegistrationTuple = issueRegistrationTuple;
    }

    /**
     * 获取 initialSubscriptionRegistrationTuple
     * 
     * @return initialSubscriptionRegistrationTuple.
     */
    public List<BaseInfoEntity> getInitialSubscriptionRegistrationTuple() {
        return initialSubscriptionRegistrationTuple;
    }

    /**
     * 设置 initialSubscriptionRegistrationTuple
     * 
     * @param initialSubscriptionRegistrationTuple initialSubscriptionRegistrationTuple
     */
    public void setInitialSubscriptionRegistrationTuple(List<BaseInfoEntity> initialSubscriptionRegistrationTuple) {
        this.initialSubscriptionRegistrationTuple = initialSubscriptionRegistrationTuple;
    }

    /**
     * 获取 subsequentSubscriptionRegistrationTuple
     * 
     * @return subsequentSubscriptionRegistrationTuple.
     */
    public List<BaseInfoEntity> getSubsequentSubscriptionRegistrationTuple() {
        return subsequentSubscriptionRegistrationTuple;
    }

    /**
     * 设置 subsequentSubscriptionRegistrationTuple
     * 
     * @param subsequentSubscriptionRegistrationTuple subsequentSubscriptionRegistrationTuple
     */
    public void setSubsequentSubscriptionRegistrationTuple(List<BaseInfoEntity> subsequentSubscriptionRegistrationTuple) {
        this.subsequentSubscriptionRegistrationTuple = subsequentSubscriptionRegistrationTuple;
    }

    /**
     * 获取 assetsAndLiabilitiesRegistrationTuple
     * 
     * @return assetsAndLiabilitiesRegistrationTuple.
     */
    public List<BaseInfoEntity> getAssetsAndLiabilitiesRegistrationTuple() {
        return assetsAndLiabilitiesRegistrationTuple;
    }

    /**
     * 设置 assetsAndLiabilitiesRegistrationTuple
     * 
     * @param assetsAndLiabilitiesRegistrationTuple assetsAndLiabilitiesRegistrationTuple
     */
    public void setAssetsAndLiabilitiesRegistrationTuple(List<BaseInfoEntity> assetsAndLiabilitiesRegistrationTuple) {
        this.assetsAndLiabilitiesRegistrationTuple = assetsAndLiabilitiesRegistrationTuple;
    }

    /**
     * 获取 transactionRegistrationTuple
     * 
     * @return transactionRegistrationTuple.
     */
    public List<BaseInfoEntity> getTransactionRegistrationTuple() {
        return transactionRegistrationTuple;
    }

    /**
     * 设置 transactionRegistrationTuple
     * 
     * @param transactionRegistrationTuple transactionRegistrationTuple
     */
    public void setTransactionRegistrationTuple(List<BaseInfoEntity> transactionRegistrationTuple) {
        this.transactionRegistrationTuple = transactionRegistrationTuple;
    }

    /**
     * 获取 valuationRegistrationTuple
     * 
     * @return valuationRegistrationTuple.
     */
    public List<BaseInfoEntity> getValuationRegistrationTuple() {
        return valuationRegistrationTuple;
    }

    /**
     * 设置 valuationRegistrationTuple
     * 
     * @param valuationRegistrationTuple valuationRegistrationTuple
     */
    public void setValuationRegistrationTuple(List<BaseInfoEntity> valuationRegistrationTuple) {
        this.valuationRegistrationTuple = valuationRegistrationTuple;
    }

    /**
     * 获取 assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple
     * 
     * @return assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple.
     */
    public List<BaseInfoEntity> getAssetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple() {
        return assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple;
    }

    /**
     * 设置 assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple
     * 
     * @param assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple
     *            assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple
     */
    public void setAssetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple(List<BaseInfoEntity> assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple) {
        this.assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple = assetManagementPlansUnderlyingAssetsLiabilitiesRegistrationTuple;
    }

    /**
     * 获取 terminationRegistrationTuple
     * 
     * @return terminationRegistrationTuple.
     */
    public List<BaseInfoEntity> getTerminationRegistrationTuple() {
        return terminationRegistrationTuple;
    }

    /**
     * 设置 terminationRegistrationTuple
     * 
     * @param terminationRegistrationTuple terminationRegistrationTuple
     */
    public void setTerminationRegistrationTuple(List<BaseInfoEntity> terminationRegistrationTuple) {
        this.terminationRegistrationTuple = terminationRegistrationTuple;
    }

    /**
     * 获取 practitionerRegistrationTuple
     * 
     * @return practitionerRegistrationTuple.
     */
    public List<BaseInfoEntity> getPractitionerRegistrationTuple() {
        return practitionerRegistrationTuple;
    }

    /**
     * 设置 practitionerRegistrationTuple
     * 
     * @param practitionerRegistrationTuple practitionerRegistrationTuple
     */
    public void setPractitionerRegistrationTuple(List<BaseInfoEntity> practitionerRegistrationTuple) {
        this.practitionerRegistrationTuple = practitionerRegistrationTuple;
    }

    /**
     * 获取 investorsSummationOfDiffenrentTypesInformationRegistrationTuple
     * 
     * @return investorsSummationOfDiffenrentTypesInformationRegistrationTuple.
     */
    public List<BaseInfoEntity> getInvestorsSummationOfDiffenrentTypesInformationRegistrationTuple() {
        return investorsSummationOfDiffenrentTypesInformationRegistrationTuple;
    }

    /**
     * 设置 investorsSummationOfDiffenrentTypesInformationRegistrationTuple
     * 
     * @param investorsSummationOfDiffenrentTypesInformationRegistrationTuple
     *            investorsSummationOfDiffenrentTypesInformationRegistrationTuple
     */
    public void setInvestorsSummationOfDiffenrentTypesInformationRegistrationTuple(List<BaseInfoEntity> investorsSummationOfDiffenrentTypesInformationRegistrationTuple) {
        this.investorsSummationOfDiffenrentTypesInformationRegistrationTuple = investorsSummationOfDiffenrentTypesInformationRegistrationTuple;
    }

    /**
     * 获取 investorsInformationRegistrationCSVDataExplanatory
     * 
     * @return investorsInformationRegistrationCSVDataExplanatory.
     */
    public CommonNodeEntity getInvestorsInformationRegistrationCSVDataExplanatory() {
        return investorsInformationRegistrationCSVDataExplanatory;
    }

    /**
     * 设置 investorsInformationRegistrationCSVDataExplanatory
     * 
     * @param investorsInformationRegistrationCSVDataExplanatory
     *            investorsInformationRegistrationCSVDataExplanatory
     */
    public void setInvestorsInformationRegistrationCSVDataExplanatory(CommonNodeEntity investorsInformationRegistrationCSVDataExplanatory) {
        this.investorsInformationRegistrationCSVDataExplanatory = investorsInformationRegistrationCSVDataExplanatory;
    }

    /**
     * 获取 investorsBusinessDetailsInformationRegistrationCsvDataExplanatory
     * 
     * @return investorsBusinessDetailsInformationRegistrationCsvDataExplanatory.
     */
    public CommonNodeEntity getInvestorsBusinessDetailsInformationRegistrationCsvDataExplanatory() {
        return investorsBusinessDetailsInformationRegistrationCsvDataExplanatory;
    }

    /**
     * 设置 investorsBusinessDetailsInformationRegistrationCsvDataExplanatory
     * 
     * @param investorsBusinessDetailsInformationRegistrationCsvDataExplanatory
     *            investorsBusinessDetailsInformationRegistrationCsvDataExplanatory
     */
    public void setInvestorsBusinessDetailsInformationRegistrationCsvDataExplanatory(CommonNodeEntity investorsBusinessDetailsInformationRegistrationCsvDataExplanatory) {
        this.investorsBusinessDetailsInformationRegistrationCsvDataExplanatory = investorsBusinessDetailsInformationRegistrationCsvDataExplanatory;
    }


	public List<BaseInfoEntity> getFilingRegistrationElementsOfPublicOrPrivateProductTuple() {
		return filingRegistrationElementsOfPublicOrPrivateProductTuple;
	}


	public void setFilingRegistrationElementsOfPublicOrPrivateProductTuple(
			List<BaseInfoEntity> filingRegistrationElementsOfPublicOrPrivateProductTuple) {
		this.filingRegistrationElementsOfPublicOrPrivateProductTuple = filingRegistrationElementsOfPublicOrPrivateProductTuple;
	}


	public List<BaseInfoEntity> getIssueRegistrationForPublicOrPrivateProductTuple() {
		return issueRegistrationForPublicOrPrivateProductTuple;
	}


	public void setIssueRegistrationForPublicOrPrivateProductTuple(
			List<BaseInfoEntity> issueRegistrationForPublicOrPrivateProductTuple) {
		this.issueRegistrationForPublicOrPrivateProductTuple = issueRegistrationForPublicOrPrivateProductTuple;
	}


	public List<BaseInfoEntity> getUnderlyingAssetsLiabilitiesRegistrationTuple() {
		return underlyingAssetsLiabilitiesRegistrationTuple;
	}


	public void setUnderlyingAssetsLiabilitiesRegistrationTuple(
			List<BaseInfoEntity> underlyingAssetsLiabilitiesRegistrationTuple) {
		this.underlyingAssetsLiabilitiesRegistrationTuple = underlyingAssetsLiabilitiesRegistrationTuple;
	}

    public List<BaseInfoEntity> getProductStatusRegistrationTuple() {
        return ProductStatusRegistrationTuple;
    }

    public void setProductStatusRegistrationTuple(List<BaseInfoEntity> productStatusRegistrationTuple) {
        ProductStatusRegistrationTuple = productStatusRegistrationTuple;
    }

    public List<BaseInfoEntity> getProductInvestedAssetsLiabilitiesRegistrationTuple() {
        return ProductInvestedAssetsLiabilitiesRegistrationTuple;
    }

    public void setProductInvestedAssetsLiabilitiesRegistrationTuple(List<BaseInfoEntity> productInvestedAssetsLiabilitiesRegistrationTuple) {
        ProductInvestedAssetsLiabilitiesRegistrationTuple = productInvestedAssetsLiabilitiesRegistrationTuple;
    }
}
