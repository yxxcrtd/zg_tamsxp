/*
 * 文件名：ChbPrdDeclareSubDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月24日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
 
package com.joyintech.component.svrreport.chb.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.component.svrreport.chb.contant.annotate.RateFormat;

/**
 * 产品申报登记DTO
 *  
 * @author 王波
 * @version 1.0 2020年9月24日
 * @see ChbPrdDeclareSubDto
 * @since 1.0
 */
public class ChbPrdDeclareSubDto {

    /**
     * 附件路径
     */
    private String attachFilePath;

    /**
     * 登记序列
     */
    private String registSeqno;
    /**
     * 报送流水号
     */
    private String sumitSeq;
    /**
     * 是否合规新产品-新老产品标记（01：新产品 02：老产品）
     */
    private String isFhxgcp;
    /**
     * 取得 登记序列
     * @return 登记序列
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 设置 登记序列
     * @param registSeqno 登记序列
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }
    /**
     * 产品代码
     */

    private String prodId;
	/**
     * 产品名称
     */
    private String prodName;

    /**
     * 行内标识码
     */
    private String prodInsId;

    /**
     * 产品品牌
     */
    private String prodBrand;
    /**
     * 是否金融同业专属
     */
    private String excFinSector;
    /**
     * 是否设置最短持有期限
     */

    private String isShortTerm;
    /**
     * 最短持有期限(天)
     */
    private BigDecimal shortTerm;
    /**
     * 最短持有期后是否自由赎回
     */

    private String isFreeRedem;
    /**
     * 是否现金管理类
     */
    private String cashManaCate;
    /**
     * 产品期次
     */
    private Integer prodStage;
    /**
     * 投资管理费率%
     */
    @RateFormat
    private BigDecimal investFeeRate;
    /**
     * 反馈状态
     */
    private String submitFeedbackStatus;
    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 产品审批人身份证号
     */
    private String approverId;

    /**
     * 产品审批人姓名
     */
    private String approverName;

    /**
     * 产品设计人身份证号
     */
    private String designerId;
    /**
     * 业绩比较基准%
     */
    @RateFormat
    private BigDecimal yield;


    /**
     * 产品设计人姓名
     */
    private String designerName;

    /**
     * 投资经理身份证号
     */
    private String managerId;

    /**
     * 投资经理姓名
     */
    private String managerName;

    /**
     * 业务联络人姓名
     */
    private String liaisonName;

    /**
     * 业务联络人座机
     */
    private String liaisonTel;

    /**
     * 业务联络人手机
     */
    private String liaisonMobile;

    /**
     * 业务联络人邮箱
     */
    private String liaisonEmail;

    /**
     * 产品收益类型
     */
    private String profitType;

    /**
     * 产品期限
     */
    private String termtype;

    /**
     * 投资者类型
     */
    private String investorType;

    /**
     * 资金投向地区
     */
    private String investArae;

    /**
     * 资金性质
     */
    private String ivstorientation;

    /**
     * 产品投资国家或地区
     */
    private String investCountry;
    /**
     * 募集方式 01公募 02私募
     */

    private String raiseType;
    /**
     * 理财业务服务模式
     */
    private String finanSerModel;

    /**
     * 产品运作模式
     */
    private String operateMode;

    /**
     * 会计核算方式
     */
    private String bookMode;

    /**
     * 产品资产配置方式
     */
    private String cmmode;

    /**
     * 产品管理模式
     */
    private String pmmode;

    /**
     * 实际管理人员名称
     */
    private String mname;

    /**
     * 产品定价方式
     */
    private String pricedMode;

    /**
     * 投资资产类型
     */
    private String invAssetType;

    /**
     * 结构性产品挂钩标的
     */
    private String conPrdTraget;

    /**
     * 合作模式
     */
    private String contMode;

    /**
     * 合作机构名称
     */
    private String cooperationName;

    /**
     * 投资资产种类及比例
     */
    private String invTypeRat;

    /**
     * 是否有预期收益率
     */
    private String isYield;

    /**
     * 预计客户最高年收益率
     */
    private BigDecimal highYield;

    /**
     * 预计客户最低年收益率
     */
    private BigDecimal lowYield;

    /**
     * 是否有收益率测算依据
     */
    private String isYieldMeasBasis;

    /**
     * 投资者风险偏好
     */
    private String riskType;

    /**
     * 产品销售区域
     */
    private String saleArea;

    /**
     * 募集币种
     */
    private String raiseCcy;

    /**
     * 兑付本金币种
     */
    private String cashPrinCcy;

    /**
     * 兑付收益币种
     */
    private String cashProfCcy;

    /**
     * 起点销售金额
     */
    private BigDecimal saleAmtStr;

    /**
     * 计划募集金额
     */
    private BigDecimal raiseAmtPlan;

    /**
     * 募集起始日期（从）
     */
    private Date raisePstrdate;

    /**
     * 募集起始日期（到）
     */
    private Date raisePenddate;

    /**
     * 投资本金到账日
     */
    private String investPrinDate;

    /**
     * 投资收益到账日
     */
    private String investProfDate;

    /**
     * 销售手续费
     */
    @RateFormat
    private BigDecimal saleFeeRate;

    /**
     * 境内托管机构名称
     */
    private String inDepName;

    /**
     * 境内托管机构代码
     */
    private String inDepCode;

    /**
     * 境外托管机构国别
     */
    private String outDepCtry;

    /**
     * 境外托管机构名称
     */
    private String outDepName;

    /**
     * 托管费率
     */
    @RateFormat
    private BigDecimal truFeeRate;

    /**
     * 产品风险等级
     */
    private String riskLevel;

    /**
     * 发型机构提前终止权标识
     */
    private String termRightFlag;

    /**
     * 客户赎回权标识
     */
    private String cusRedeemFlag;

    /**
     * 产品增信标识
     */
    private String creditFlag;

    /**
     * 产品增信机构类型
     */
    private String creditType;

    /**
     * 产品增信形式
     */
    private String creditMode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 九大附件
     */
    private String nineFile;

    /**
     * 文件代码
     */
    private String fileNo;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 预计登记日期
     */
    private Date preRegDate;

    /**
     * 预计审批日期
     */
    private Date preAppDate;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 数据日期
     */
    private Date statusDate;


    /***1217升级**/

    /**
     * 产品特殊属性
     */
    private String prdSpecProperty;

    public String getPrdSpecProperty() {
        return prdSpecProperty;
    }

    public void setPrdSpecProperty(String prdSpecProperty) {
        this.prdSpecProperty = prdSpecProperty;
    }

    /***1217升级**/

    public String getAttachFilePath() {
        return attachFilePath;
    }

    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }

    /**
     * 取得 产品名称
     * @return 产品名称
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 设置 产品名称
     * @param prodName 产品名称
     */
    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    /**
     * 取得 行内标识码
     * @return 行内标识码
     */
    public String getProdInsId() {
        return prodInsId;
    }

    /**
     * 设置 行内标识码
     * @param prodInsId 行内标识码
     */
    public void setProdInsId(String prodInsId) {
        this.prodInsId = prodInsId == null ? null : prodInsId.trim();
    }

    /**
     * 取得 产品品牌
     * @return 产品品牌
     */
    public String getProdBrand() {
        return prodBrand;
    }

    /**
     * 设置 产品品牌
     * @param prodBrand 产品品牌
     */
    public void setProdBrand(String prodBrand) {
        this.prodBrand = prodBrand == null ? null : prodBrand.trim();
    }

    /**
     * 取得 产品期次
     * @return 产品期次
     */
    public Integer getProdStage() {
        return prodStage;
    }

    /**
     * 设置 产品期次
     * @param prodStage 产品期次
     */
    public void setProdStage(Integer prodStage) {
        this.prodStage = prodStage;
    }

    /**
     * 取得 发行机构代码
     * @return 发行机构代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 设置 发行机构代码
     * @param issuePartyId 发行机构代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 取得 产品审批人身份证号
     * @return 产品审批人身份证号
     */
    public String getApproverId() {
        return approverId;
    }

    /**
     * 设置 产品审批人身份证号
     * @param approverId 产品审批人身份证号
     */
    public void setApproverId(String approverId) {
        this.approverId = approverId == null ? null : approverId.trim();
    }

    /**
     * 取得 产品审批人姓名
     * @return 产品审批人姓名
     */
    public String getApproverName() {
        return approverName;
    }

    /**
     * 设置 产品审批人姓名
     * @param approverName 产品审批人姓名
     */
    public void setApproverName(String approverName) {
        this.approverName = approverName == null ? null : approverName.trim();
    }

    /**
     * 取得 产品设计人身份证号
     * @return 产品设计人身份证号
     */
    public String getDesignerId() {
        return designerId;
    }

    /**
     * 设置 产品设计人身份证号
     * @param designerId 产品设计人身份证号
     */
    public void setDesignerId(String designerId) {
        this.designerId = designerId == null ? null : designerId.trim();
    }

    /**
     * 取得 产品设计人姓名
     * @return 产品设计人姓名
     */
    public String getDesignerName() {
        return designerName;
    }

    /**
     * 设置 产品设计人姓名
     * @param designerName 产品设计人姓名
     */
    public void setDesignerName(String designerName) {
        this.designerName = designerName == null ? null : designerName.trim();
    }

    /**
     * 取得 投资经理身份证号
     * @return 投资经理身份证号
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * 设置 投资经理身份证号
     * @param managerId 投资经理身份证号
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    /**
     * 取得 投资经理姓名
     * @return 投资经理姓名
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * 设置 投资经理姓名
     * @param managerName 投资经理姓名
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    /**
     * 取得 业务联络人姓名
     * @return 业务联络人姓名
     */
    public String getLiaisonName() {
        return liaisonName;
    }

    /**
     * 设置 业务联络人姓名
     * @param liaisonName 业务联络人姓名
     */
    public void setLiaisonName(String liaisonName) {
        this.liaisonName = liaisonName == null ? null : liaisonName.trim();
    }

    /**
     * 取得 业务联络人座机
     * @return 业务联络人座机
     */
    public String getLiaisonTel() {
        return liaisonTel;
    }

    /**
     * 设置 业务联络人座机
     * @param liaisonTel 业务联络人座机
     */
    public void setLiaisonTel(String liaisonTel) {
        this.liaisonTel = liaisonTel == null ? null : liaisonTel.trim();
    }

    /**
     * 取得 业务联络人手机
     * @return 业务联络人手机
     */
    public String getLiaisonMobile() {
        return liaisonMobile;
    }

    /**
     * 设置 业务联络人手机
     * @param liaisonMobile 业务联络人手机
     */
    public void setLiaisonMobile(String liaisonMobile) {
        this.liaisonMobile = liaisonMobile == null ? null : liaisonMobile.trim();
    }

    /**
     * 取得 业务联络人邮箱
     * @return 业务联络人邮箱
     */
    public String getLiaisonEmail() {
        return liaisonEmail;
    }

    /**
     * 设置 业务联络人邮箱
     * @param liaisonEmail 业务联络人邮箱
     */
    public void setLiaisonEmail(String liaisonEmail) {
        this.liaisonEmail = liaisonEmail == null ? null : liaisonEmail.trim();
    }

    /**
     * 取得 产品收益类型
     * @return 产品收益类型
     */
    public String getProfitType() {
        return profitType;
    }

    /**
     * 设置 产品收益类型
     * @param profitType 产品收益类型
     */
    public void setProfitType(String profitType) {
        this.profitType = profitType == null ? null : profitType.trim();
    }

    /**
     * 取得 产品期限
     * @return 产品期限
     */
    public String getTermtype() {
        return termtype;
    }

    /**
     * 设置 产品期限
     * @param term 产品期限
     */
    public void setTermtype(String termtype) {
        this.termtype = termtype == null ? null : termtype.trim();
    }

    /**
     * 取得 投资者类型
     * @return 投资者类型
     */
    public String getInvestorType() {
        return investorType;
    }

    /**
     * 设置 投资者类型
     * @param investorType 投资者类型
     */
    public void setInvestorType(String investorType) {
        this.investorType = investorType == null ? null : investorType.trim();
    }

    /**
     * 取得 资金投向地区
     * @return 资金投向地区
     */
    public String getInvestArae() {
        return investArae;
    }

    /**
     * 设置 资金投向地区
     * @param investArae 资金投向地区
     */
    public void setInvestArae(String investArae) {
        this.investArae = investArae == null ? null : investArae.trim();
    }

    /**
     * 取得 产品投资国家或地区
     * @return 产品投资国家或地区
     */
    public String getInvestCountry() {
        return investCountry;
    }

    /**
     * 设置 产品投资国家或地区
     * @param investCountry 产品投资国家或地区
     */
    public void setInvestCountry(String investCountry) {
        this.investCountry = investCountry == null ? null : investCountry.trim();
    }

    /**
     * 取得 理财业务服务模式
     * @return 理财业务服务模式
     */
    public String getFinanSerModel() {
        return finanSerModel;
    }

    /**
     * 设置 理财业务服务模式
     * @param finanSerModel 理财业务服务模式
     */
    public void setFinanSerModel(String finanSerModel) {
        this.finanSerModel = finanSerModel == null ? null : finanSerModel.trim();
    }

    /**
     * 取得 产品运作模式
     * @return 产品运作模式
     */
    public String getOperateMode() {
        return operateMode;
    }

    /**
     * 设置 产品运作模式
     * @param operateMode 产品运作模式
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode == null ? null : operateMode.trim();
    }

    /**
     * 取得 会计核算方式
     * @return 会计核算方式
     */
    public String getBookMode() {
        return bookMode;
    }

    /**
     * 设置 会计核算方式
     * @param bookMode 会计核算方式
     */
    public void setBookMode(String bookMode) {
        this.bookMode = bookMode == null ? null : bookMode.trim();
    }

    /**
     * 取得 产品资产配置方式
     * @return 产品资产配置方式
     */
    public String getCmmode() {
        return cmmode;
    }

    /**
     * 设置 产品资产配置方式
     * @param cmmode 产品资产配置方式
     */
    public void setCmmode(String cmmode) {
        this.cmmode = cmmode == null ? null : cmmode.trim();
    }

    /**
     * 取得 产品管理模式
     * @return 产品管理模式
     */
    public String getPmmode() {
        return pmmode;
    }

    /**
     * 设置 产品管理模式
     * @param pmmode 产品管理模式
     */
    public void setPmmode(String pmmode) {
        this.pmmode = pmmode == null ? null : pmmode.trim();
    }

    /**
     * 取得 实际管理人员名称
     * @return 实际管理人员名称
     */
    public String getMname() {
        return mname;
    }

    /**
     * 设置 实际管理人员名称
     * @param mname 实际管理人员名称
     */
    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    /**
     * 取得 产品定价方式
     * @return 产品定价方式
     */
    public String getPricedMode() {
        return pricedMode;
    }

    /**
     * 设置 产品定价方式
     * @param pricedMode 产品定价方式
     */
    public void setPricedMode(String pricedMode) {
        this.pricedMode = pricedMode == null ? null : pricedMode.trim();
    }

    /**
     * 取得 投资资产类型
     * @return 投资资产类型
     */
    public String getInvAssetType() {
        return invAssetType;
    }

    /**
     * 设置 投资资产类型
     * @param invAssetType 投资资产类型
     */
    public void setInvAssetType(String invAssetType) {
        this.invAssetType = invAssetType == null ? null : invAssetType.trim();
    }

    /**
     * 取得 结构性产品挂钩标的
     * @return 结构性产品挂钩标的
     */
    public String getConPrdTraget() {
        return conPrdTraget;
    }

    /**
     * 设置 结构性产品挂钩标的
     * @param conPrdTraget 结构性产品挂钩标的
     */
    public void setConPrdTraget(String conPrdTraget) {
        this.conPrdTraget = conPrdTraget == null ? null : conPrdTraget.trim();
    }

    /**
     * 取得 合作模式
     * @return 合作模式
     */
    public String getContMode() {
        return contMode;
    }

    /**
     * 设置 合作模式
     * @param contMode 合作模式
     */
    public void setContMode(String contMode) {
        this.contMode = contMode == null ? null : contMode.trim();
    }

    /**
     * 取得 合作机构名称
     * @return 合作机构名称
     */
    public String getCooperationName() {
        return cooperationName;
    }

    /**
     * 设置 合作机构名称
     * @param cooperationName 合作机构名称
     */
    public void setCooperationName(String cooperationName) {
        this.cooperationName = cooperationName == null ? null : cooperationName.trim();
    }

    /**
     * 取得 投资资产种类及比例
     * @return 投资资产种类及比例
     */
    public String getInvTypeRat() {
        return invTypeRat;
    }

    /**
     * 设置 投资资产种类及比例
     * @param invTypeRat 投资资产种类及比例
     */
    public void setInvTypeRat(String invTypeRat) {
        this.invTypeRat = invTypeRat == null ? null : invTypeRat.trim();
    }

    /**
     * 取得 是否有预期收益率
     * @return 是否有预期收益率
     */
    public String getIsYield() {
        return isYield;
    }

    /**
     * 设置 是否有预期收益率
     * @param isYield 是否有预期收益率
     */
    public void setIsYield(String isYield) {
        this.isYield = isYield == null ? null : isYield.trim();
    }

    /**
     * 取得 预计客户最高年收益率
     * @return 预计客户最高年收益率
     */
    public BigDecimal getHighYield() {
        return highYield;
    }

    /**
     * 设置 预计客户最高年收益率
     * @param highYield 预计客户最高年收益率
     */
    public void setHighYield(BigDecimal highYield) {
        this.highYield = highYield;
    }

    /**
     * 取得 预计客户最低年收益率
     * @return 预计客户最低年收益率
     */
    public BigDecimal getLowYield() {
        return lowYield;
    }

    /**
     * 设置 预计客户最低年收益率
     * @param lowYield 预计客户最低年收益率
     */
    public void setLowYield(BigDecimal lowYield) {
        this.lowYield = lowYield;
    }

    /**
     * 取得 是否有收益率测算依据
     * @return 是否有收益率测算依据
     */
    public String getIsYieldMeasBasis() {
        return isYieldMeasBasis;
    }

    /**
     * 设置 是否有收益率测算依据
     * @param isYieldMeasBasis 是否有收益率测算依据
     */
    public void setIsYieldMeasBasis(String isYieldMeasBasis) {
        this.isYieldMeasBasis = isYieldMeasBasis == null ? null : isYieldMeasBasis.trim();
    }

    /**
     * 取得 投资者风险偏好
     * @return 投资者风险偏好
     */
    public String getRiskType() {
        return riskType;
    }

    /**
     * 设置 投资者风险偏好
     * @param riskType 投资者风险偏好
     */
    public void setRiskType(String riskType) {
        this.riskType = riskType == null ? null : riskType.trim();
    }

    /**
     * 取得 产品销售区域
     * @return 产品销售区域
     */
    public String getSaleArea() {
        return saleArea;
    }

    /**
     * 设置 产品销售区域
     * @param saleArea 产品销售区域
     */
    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea == null ? null : saleArea.trim();
    }

    /**
     * 取得 募集币种
     * @return 募集币种
     */
    public String getRaiseCcy() {
        return raiseCcy;
    }

    /**
     * 设置 募集币种
     * @param raiseCcy 募集币种
     */
    public void setRaiseCcy(String raiseCcy) {
        this.raiseCcy = raiseCcy == null ? null : raiseCcy.trim();
    }

    /**
     * 取得 兑付本金币种
     * @return 兑付本金币种
     */
    public String getCashPrinCcy() {
        return cashPrinCcy;
    }

    /**
     * 设置 兑付本金币种
     * @param cashPrinCcy 兑付本金币种
     */
    public void setCashPrinCcy(String cashPrinCcy) {
        this.cashPrinCcy = cashPrinCcy == null ? null : cashPrinCcy.trim();
    }

    /**
     * 取得 兑付收益币种
     * @return 兑付收益币种
     */
    public String getCashProfCcy() {
        return cashProfCcy;
    }

    /**
     * 设置 兑付收益币种
     * @param cashProfCcy 兑付收益币种
     */
    public void setCashProfCcy(String cashProfCcy) {
        this.cashProfCcy = cashProfCcy == null ? null : cashProfCcy.trim();
    }

    /**
     * 取得 起点销售金额
     * @return 起点销售金额
     */
    public BigDecimal getSaleAmtStr() {
        return saleAmtStr;
    }

    /**
     * 设置 起点销售金额
     * @param saleAmtStr 起点销售金额
     */
    public void setSaleAmtStr(BigDecimal saleAmtStr) {
        this.saleAmtStr = saleAmtStr;
    }

    /**
     * 取得 计划募集金额
     * @return 计划募集金额
     */
    public BigDecimal getRaiseAmtPlan() {
        return raiseAmtPlan;
    }

    /**
     * 设置 计划募集金额
     * @param raiseAmtPlan 计划募集金额
     */
    public void setRaiseAmtPlan(BigDecimal raiseAmtPlan) {
        this.raiseAmtPlan = raiseAmtPlan;
    }

    /**
     * 取得 募集起始日期（从）
     * @return 募集起始日期（从）
     */
    public Date getRaisePstrdate() {
        return raisePstrdate;
    }

    /**
     * 设置 募集起始日期（从）
     * @param raisePstrdate 募集起始日期（从）
     */
    public void setRaisePstrdate(Date raisePstrdate) {
        this.raisePstrdate = raisePstrdate;
    }

    /**
     * 取得 募集起始日期（到）
     * @return 募集起始日期（到）
     */
    public Date getRaisePenddate() {
        return raisePenddate;
    }

    /**
     * 设置 募集起始日期（到）
     * @param raisePenddate 募集起始日期（到）
     */
    public void setRaisePenddate(Date raisePenddate) {
        this.raisePenddate = raisePenddate;
    }

    /**
     * 取得 投资本金到账日
     * @return 投资本金到账日
     */
    public String getInvestPrinDate() {
        return investPrinDate;
    }

    /**
     * 设置 投资本金到账日
     * @param investPrinDate 投资本金到账日
     */
    public void setInvestPrinDate(String investPrinDate) {
        this.investPrinDate = investPrinDate == null ? null : investPrinDate.trim();
    }

    /**
     * 取得 投资收益到账日
     * @return 投资收益到账日
     */
    public String getInvestProfDate() {
        return investProfDate;
    }

    /**
     * 设置 投资收益到账日
     * @param investProfDate 投资收益到账日
     */
    public void setInvestProfDate(String investProfDate) {
        this.investProfDate = investProfDate == null ? null : investProfDate.trim();
    }

    /**
     * 取得 销售手续费
     * @return 销售手续费
     */
    public BigDecimal getSaleFeeRate() {
        return saleFeeRate;
    }

    /**
     * 设置 销售手续费
     * @param saleFeeRate 销售手续费
     */
    public void setSaleFeeRate(BigDecimal saleFeeRate) {
        this.saleFeeRate = saleFeeRate;
    }

    /**
     * 取得 境内托管机构名称
     * @return 境内托管机构名称
     */
    public String getInDepName() {
        return inDepName;
    }

    /**
     * 设置 境内托管机构名称
     * @param inDepName 境内托管机构名称
     */
    public void setInDepName(String inDepName) {
        this.inDepName = inDepName == null ? null : inDepName.trim();
    }

    /**
     * 取得 境内托管机构代码
     * @return 境内托管机构代码
     */
    public String getInDepCode() {
        return inDepCode;
    }

    /**
     * 设置 境内托管机构代码
     * @param inDepCode 境内托管机构代码
     */
    public void setInDepCode(String inDepCode) {
        this.inDepCode = inDepCode == null ? null : inDepCode.trim();
    }

    /**
     * 取得 境外托管机构国别
     * @return 境外托管机构国别
     */
    public String getOutDepCtry() {
        return outDepCtry;
    }

    /**
     * 设置 境外托管机构国别
     * @param outDepCtry 境外托管机构国别
     */
    public void setOutDepCtry(String outDepCtry) {
        this.outDepCtry = outDepCtry == null ? null : outDepCtry.trim();
    }

    /**
     * 取得 境外托管机构名称
     * @return 境外托管机构名称
     */
    public String getOutDepName() {
        return outDepName;
    }

    /**
     * 设置 境外托管机构名称
     * @param outDepName 境外托管机构名称
     */
    public void setOutDepName(String outDepName) {
        this.outDepName = outDepName == null ? null : outDepName.trim();
    }

    /**
     * 取得 托管费率
     * @return 托管费率
     */
    public BigDecimal getTruFeeRate() {
        return truFeeRate;
    }

    /**
     * 设置 托管费率
     * @param truFeeRate 托管费率
     */
    public void setTruFeeRate(BigDecimal truFeeRate) {
        this.truFeeRate = truFeeRate;
    }

    /**
     * 取得 产品风险等级
     * @return 产品风险等级
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * 设置 产品风险等级
     * @param riskLevel 产品风险等级
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel == null ? null : riskLevel.trim();
    }

    /**
     * 取得 发型机构提前终止权标识
     * @return 发型机构提前终止权标识
     */
    public String getTermRightFlag() {
        return termRightFlag;
    }

    /**
     * 设置 发型机构提前终止权标识
     * @param termRightFlag 发型机构提前终止权标识
     */
    public void setTermRightFlag(String termRightFlag) {
        this.termRightFlag = termRightFlag == null ? null : termRightFlag.trim();
    }

    /**
     * 取得 客户赎回权标识
     * @return 客户赎回权标识
     */
    public String getCusRedeemFlag() {
        return cusRedeemFlag;
    }

    /**
     * 设置 客户赎回权标识
     * @param cusRedeemFlag 客户赎回权标识
     */
    public void setCusRedeemFlag(String cusRedeemFlag) {
        this.cusRedeemFlag = cusRedeemFlag == null ? null : cusRedeemFlag.trim();
    }

    /**
     * 取得 产品增信标识
     * @return 产品增信标识
     */
    public String getCreditFlag() {
        return creditFlag;
    }

    /**
     * 设置 产品增信标识
     * @param creditFlag 产品增信标识
     */
    public void setCreditFlag(String creditFlag) {
        this.creditFlag = creditFlag == null ? null : creditFlag.trim();
    }

    /**
     * 取得 产品增信机构类型
     * @return 产品增信机构类型
     */
    public String getCreditType() {
        return creditType;
    }

    /**
     * 设置 产品增信机构类型
     * @param creditType 产品增信机构类型
     */
    public void setCreditType(String creditType) {
        this.creditType = creditType == null ? null : creditType.trim();
    }

    /**
     * 取得 产品增信形式
     * @return 产品增信形式
     */
    public String getCreditMode() {
        return creditMode;
    }

    /**
     * 设置 产品增信形式
     * @param creditMode 产品增信形式
     */
    public void setCreditMode(String creditMode) {
        this.creditMode = creditMode == null ? null : creditMode.trim();
    }

    /**
     * 取得 备注
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 取得 九大附件
     * @return 九大附件
     */
    public String getNineFile() {
        return nineFile;
    }

    /**
     * 设置 九大附件
     * @param nineFile 九大附件
     */
    public void setNineFile(String nineFile) {
        this.nineFile = nineFile == null ? null : nineFile.trim();
    }

    /**
     * 取得 文件代码
     * @return 文件代码
     */
    public String getFileNo() {
        return fileNo;
    }

    /**
     * 设置 文件代码
     * @param fileNo 文件代码
     */
    public void setFileNo(String fileNo) {
        this.fileNo = fileNo == null ? null : fileNo.trim();
    }

    /**
     * 取得 工作流代码
     * @return 工作流代码
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 设置 工作流代码
     * @param workflowId 工作流代码
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 取得 预计登记日期
     * @return 预计登记日期
     */
    public Date getPreRegDate() {
        return preRegDate;
    }

    /**
     * 设置 预计登记日期
     * @param preRegDate 预计登记日期
     */
    public void setPreRegDate(Date preRegDate) {
        this.preRegDate = preRegDate;
    }

    /**
     * 取得 预计审批日期
     * @return 预计审批日期
     */
    public Date getPreAppDate() {
        return preAppDate;
    }

    /**
     * 设置 预计审批日期
     * @param preAppDate 预计审批日期
     */
    public void setPreAppDate(Date preAppDate) {
        this.preAppDate = preAppDate;
    }

    /**
     * 取得 报送状态
     * @return 报送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置 报送状态
     * @param sendStatus 报送状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 取得 数据日期
     * @return 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 设置 数据日期
     * @param statusDate 数据日期
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

	public String getExcFinSector() {
		return excFinSector;
	}

	public void setExcFinSector(String excFinSector) {
		this.excFinSector = excFinSector;
	}

	public String getRaiseType() {
		return raiseType;
	}

	public void setRaiseType(String raiseType) {
		this.raiseType = raiseType;
	}

	public String getIvstorientation() {
		return ivstorientation;
	}

	public void setIvstorientation(String ivstorientation) {
		this.ivstorientation = ivstorientation;
	}

	public BigDecimal getYield() {
		return yield;
	}

	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}

	public String getIsShortTerm() {
		return isShortTerm;
	}

	public void setIsShortTerm(String isShortTerm) {
		this.isShortTerm = isShortTerm;
	}

	public BigDecimal getShortTerm() {
		return shortTerm;
	}

	public void setShortTerm(BigDecimal shortTerm) {
		this.shortTerm = shortTerm;
	}

	public String getIsFreeRedem() {
		return isFreeRedem;
	}

	public void setIsFreeRedem(String isFreeRedem) {
		this.isFreeRedem = isFreeRedem;
	}

	public String getCashManaCate() {
		return cashManaCate;
	}

	public void setCashManaCate(String cashManaCate) {
		this.cashManaCate = cashManaCate;
	}

	public BigDecimal getInvestFeeRate() {
		return investFeeRate;
	}

	public void setInvestFeeRate(BigDecimal investFeeRate) {
		this.investFeeRate = investFeeRate;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getSubmitFeedbackStatus() {
		return submitFeedbackStatus;
	}

	public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
		this.submitFeedbackStatus = submitFeedbackStatus;
	}

	public String getSumitSeq() {
		return sumitSeq;
	}

	public void setSumitSeq(String sumitSeq) {
		this.sumitSeq = sumitSeq;
	}

    public String getIsFhxgcp() {
        return isFhxgcp;
    }

    public void setIsFhxgcp(String isFhxgcp) {
        this.isFhxgcp = isFhxgcp;
    }
}
