package com.joyintech.tams.basicdata.perprodeclareinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.basicdata.perprodeclareinfo.validate.SvrPerProDeclareInfoValid;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * 产品申报登记信息 Dto
 *
 * @author yxj
 * @date 1.0 2022年03月07日
 * @see SvrPerProDeclareInfo001Dto
 * @since 1.0
 */
@PageId("SvrPerProDeclareInfo002")
@ValidLogic(targetClass = SvrPerProDeclareInfoValid.class, groups = {ValidateGroupEdit.class})
public class SvrPerProDeclareInfo001Dto extends BaseEntity implements ProcessEntity {
    /**
     * 状态: E,D
     */
    private String status;

    /**
     * 选中的数据
     */
    private transient List<SvrPerProDeclareInfo001Dto> selectedItem;

    /**
     * 画面Id
     */
    private String pageId;
    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 页面模式
     */
    private String pageModel;

    /**
     * elementId
     */
    private String elementId;

    /**
     * null
     */
    private String declareUuid;

    /**
     * 行内标识码
     */
    private String prodInsId;

    /**
     * 产品名称
     */
    private String prodName;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 产品品牌
     */
    private String prodBrand;

    /**
     * 产品期次
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal prodStage;

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
     * 是否金融同业专属
     */
    private String excFinSector;

    /**
     * 资金投向地区
     */
    private String investArae;

    /**
     * 产品投资国家或地区
     */
    private String investCountry;

    /**
     * 理财业务服务模式
     */
    private String finanSerModel;

    /**
     * 产品运作模式
     */
    private String operateMode;

    /**
     * 募集方式 01公募 02私募
     */
    private String raiseType;

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
     * 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     */
    private String ivstorientation;

    /**
     * 业绩比较基准%
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal yield;

    /**
     * 是否设置最短持有期限
     */
    private String isShortTerm;

    /**
     * 最短持有期限(天)
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
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
     * 产品销售区域
     */
    private String saleArea;

    /**
     * 起点销售金额
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal saleAmtStr;

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
     * 销售手续费
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal saleFeeRate;

    /**
     * 托管费率
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal truFeeRate;

    /**
     * 募集起始日期（从）
     */
    private Date raisePstrdate;

    /**
     * 募集起始日期（到）
     */
    private Date raisePenddate;

    /**
     * 计划募集金额
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal raiseAmtPlan;

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
     * 投资者风险偏好
     */
    private String riskType;

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
     * 投资管理费率%
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal investFeeRate;

    /**
     * 合作模式
     */
    private String contMode;

    /**
     * 合作机构名称
     */
    private String cooperationName;

    /**
     * 投资本金到账日
     */
    private String investPrinDate;

    /**
     * 投资收益到账日
     */
    private String investProfDate;

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
     * 投资资产种类及比例
     */
    private String invTypeRat;

    /**
     * 备注
     */
    private String remark;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * null
     */
    private String deptCode;

    /**
     * null
     */
    private String workflowId;

    /**
     * null
     */
    private String datasource;

    /**
     * 是否合规新产品-新老产品标记（01：新产品 02：老产品）
     */
    private String isFhxgcp;

    /**
     * 产品特殊属性
     */
    private String prdSpecProperty;
    /**
     * 错误信息
     */
    private String errMsg;

    private String fileNo;

    private BigDecimal fileNum;

    private String tenFile;
    /**
     * 有效状态
     */
    private String statusValid;

    private String orgData;

    // updateFlg 02 不展示  01 显示
    public String getUpdateFlg() {
        if(!StringUtil.isEmpty(statusValid) && StringUtil.equals(DictCodeList.维护数据状态_删除.getValue(), statusValid)){
            return "02";
        }else{
            return "01";
        }
    }

    public String getStatusValidStr() {
        return com.joyintech.framework.util.SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,
                getStatusValid());
    }

    public String getStatusValid() {
        return statusValid;
    }

    public void setStatusValid(String statusValid) {
        this.statusValid = statusValid;
    }
    /**
     * 取得 null
     * @return null
     */
    @PrimaryKey(PrimaryKey.Field.PK_1)
    public String getDeclareUuid() {
        return declareUuid;
    }

    /**
     * 设置 null
     * @param declareUuid null
     */
    public void setDeclareUuid(String declareUuid) {
        this.declareUuid = declareUuid == null ? null : declareUuid.trim();
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
    public BigDecimal getProdStage() {
        return prodStage;
    }

    /**
     * 设置 产品期次
     * @param prodStage 产品期次
     */
    public void setProdStage(BigDecimal prodStage) {
        this.prodStage = prodStage;
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

    public String getProfitTypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.产品收益类型,this.profitType);
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

    public String getTermtypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.产品期限,this.termtype);
    }

    /**
     * 设置 产品期限
     * @param termtype 产品期限
     */
    public void setTermtype(String termtype) {
        this.termtype = termtype == null ? null : termtype.trim();
    }

    /**
     * 取得 是否金融同业专属
     * @return 是否金融同业专属
     */
    public String getExcFinSector() {
        return excFinSector;
    }

    public String getExcFinSectorStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.是否_中债,this.excFinSector);
    }

    /**
     * 设置 是否金融同业专属
     * @param excFinSector 是否金融同业专属
     */
    public void setExcFinSector(String excFinSector) {
        this.excFinSector = excFinSector == null ? null : excFinSector.trim();
    }

    /**
     * 取得 资金投向地区
     * @return 资金投向地区
     */
    public String getInvestArae() {
        return investArae;
    }

    public String getInvestAraeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.资金投向地区,this.investArae);
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

    public String getInvestCountryStr() {
        if (this.getInvestCountry()!=null && (!this.getInvestCountry().equals("")) ){
            String[] split = this.getInvestCountry().split(",");
            if (split.length > 1 ){
                String investCountryStr = "";
                for (String str : split) {
                    investCountryStr += SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区,str) + ",";
                }
                return investCountryStr.substring(0,investCountryStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区,this.getInvestCountry());
        }
        return this.getInvestCountry();
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

    public String getFinanSerModelStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.理财业务服务模式,this.finanSerModel);
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

    public String getOperateModeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.产品运作模式,this.operateMode);
    }

    /**
     * 设置 产品运作模式
     * @param operateMode 产品运作模式
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode == null ? null : operateMode.trim();
    }

    /**
     * 取得 募集方式 01公募 02私募
     * @return 募集方式 01公募 02私募
     */
    public String getRaiseType() {
        return raiseType;
    }

    public String getRaiseTypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.募集方式,this.raiseType);
    }

    /**
     * 设置 募集方式 01公募 02私募
     * @param raiseType 募集方式 01公募 02私募
     */
    public void setRaiseType(String raiseType) {
        this.raiseType = raiseType == null ? null : raiseType.trim();
    }

    /**
     * 取得 产品资产配置方式
     * @return 产品资产配置方式
     */
    public String getCmmode() {
        return cmmode;
    }

    public String getCmmodeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.产品资产配置方式_中债,this.cmmode);
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

    public String getPmmodeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.产品管理模式,this.pmmode);
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

    public String getPricedModeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.定价方式,this.pricedMode);
    }

    /**
     * 设置 产品定价方式
     * @param pricedMode 产品定价方式
     */
    public void setPricedMode(String pricedMode) {
        this.pricedMode = pricedMode == null ? null : pricedMode.trim();
    }

    /**
     * 取得 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     * @return 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     */
    public String getIvstorientation() {
        return ivstorientation;
    }

    public String getIvstorientationStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.投资性质,this.ivstorientation);
    }

    /**
     * 设置 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     * @param ivstorientation 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     */
    public void setIvstorientation(String ivstorientation) {
        this.ivstorientation = ivstorientation == null ? null : ivstorientation.trim();
    }

    /**
     * 取得 业绩比较基准%
     * @return 业绩比较基准%
     */
    public BigDecimal getYield() {
        return yield;
    }

    /**
     * 设置 业绩比较基准%
     * @param yield 业绩比较基准%
     */
    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    /**
     * 取得 是否设置最短持有期限
     * @return 是否设置最短持有期限
     */
    public String getIsShortTerm() {
        return isShortTerm;
    }

    public String getIsShortTermStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.是否_中债,this.isShortTerm);
    }

    /**
     * 设置 是否设置最短持有期限
     * @param isShortTerm 是否设置最短持有期限
     */
    public void setIsShortTerm(String isShortTerm) {
        this.isShortTerm = isShortTerm == null ? null : isShortTerm.trim();
    }

    /**
     * 取得 最短持有期限(天)
     * @return 最短持有期限(天)
     */
    public BigDecimal getShortTerm() {
        return shortTerm;
    }

    /**
     * 设置 最短持有期限(天)
     * @param shortTerm 最短持有期限(天)
     */
    public void setShortTerm(BigDecimal shortTerm) {
        this.shortTerm = shortTerm;
    }

    /**
     * 取得 最短持有期后是否自由赎回
     * @return 最短持有期后是否自由赎回
     */
    public String getIsFreeRedem() {
        return isFreeRedem;
    }

    public String getIsFreeRedemStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.是否_中债_期限,this.isFreeRedem);
    }


    /**
     * 设置 最短持有期后是否自由赎回
     * @param isFreeRedem 最短持有期后是否自由赎回
     */
    public void setIsFreeRedem(String isFreeRedem) {
        this.isFreeRedem = isFreeRedem == null ? null : isFreeRedem.trim();
    }

    /**是否现金管理类
     * 取得
     * @return 是否现金管理类
     */
    public String getCashManaCate() {
        return cashManaCate;
    }

    public String getCashManaCateStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.是否,this.cashManaCate);
    }

    /**
     * 设置 是否现金管理类
     * @param cashManaCate 是否现金管理类
     */
    public void setCashManaCate(String cashManaCate) {
        this.cashManaCate = cashManaCate == null ? null : cashManaCate.trim();
    }

    /**
     * 取得 产品销售区域
     * @return 产品销售区域
     */
    public String getSaleArea() {
        return saleArea;
    }

    public String getSaleAreaStr() {
        if (this.getSaleArea()!=null && (!this.getSaleArea().equals("")) ){
            String[] split = this.getSaleArea().split(",");
            if (split.length > 1 ){
                String saleAreaStr = "";
                for (String str : split) {
                    saleAreaStr += SysDataUtil.getNameFromDataDict(DictCode.销售区域_中债,str) + ",";
                }
                return saleAreaStr.substring(0,saleAreaStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.销售区域_中债,this.getSaleArea());
        }
        return this.getSaleArea();
    }

    /**
     * 设置 产品销售区域
     * @param saleArea 产品销售区域
     */
    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea == null ? null : saleArea.trim();
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
     * 取得 募集币种
     * @return 募集币种
     */
    public String getRaiseCcy() {
        return raiseCcy;
    }

    // TODO 待验证
    public String getRaiseCcyStr() {
        if (this.getRaiseCcy()!=null && (!this.getRaiseCcy().equals("")) ){
            String[] split = this.getRaiseCcy().split(",");
            if (split.length > 1 ){
                String raiseCcyStr = "";
                for (String str : split) {
                    raiseCcyStr += SysDataUtil.getNameFromDataDict(DictCode.币种,str) + ",";
                }
                return raiseCcyStr.substring(0,raiseCcyStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.币种,this.getRaiseCcy());
        }
        return this.getRaiseCcy();
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

    // TODO 待验证
    public String getCashPrinCcyStr() {
        if (this.getCashPrinCcy()!=null && (!this.getCashPrinCcy().equals("")) ){
            String[] split = this.getCashPrinCcy().split(",");
            if (split.length > 1 ){
                String cashPrinCcyStr = "";
                for (String str : split) {
                    cashPrinCcyStr += SysDataUtil.getNameFromDataDict(DictCode.币种,str) + ",";
                }
                return cashPrinCcyStr.substring(0,cashPrinCcyStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.币种,this.getCashPrinCcy());
        }
        return this.getCashPrinCcy();
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

    // TODO 待验证
    public String getCashProfCcyStr() {
        if (this.getCashProfCcy()!=null && (!this.getCashProfCcy().equals("")) ){
            String[] split = this.getCashProfCcy().split(",");
            if (split.length > 1 ){
                String cashProfCcyStr = "";
                for (String str : split) {
                    cashProfCcyStr += SysDataUtil.getNameFromDataDict(DictCode.币种,str) + ",";
                }
                return cashProfCcyStr.substring(0,cashProfCcyStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.币种,this.getCashProfCcy());
        }
        return this.getCashProfCcy();
    }

    /**
     * 设置 兑付收益币种
     * @param cashProfCcy 兑付收益币种
     */
    public void setCashProfCcy(String cashProfCcy) {
        this.cashProfCcy = cashProfCcy == null ? null : cashProfCcy.trim();
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
     * 取得 募集起始日期（从）
     * @return 募集起始日期（从）
     */
    public Date getRaisePstrdate() {
        return raisePstrdate;
    }

    public String getRaisePstrdateStr() {
        return DateUtil.format(this.getRaisePstrdate());
    }

    /**
     * 设置 募集起始日期（从）
     * @param raisePstrdate 募集起始日期（从）
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
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

    public String getRaisePenddateStr() {
        return DateUtil.format(this.getRaisePenddate());
    }

    /**
     * 设置 募集起始日期（到）
     * @param raisePenddate 募集起始日期（到）
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setRaisePenddate(Date raisePenddate) {
        this.raisePenddate = raisePenddate;
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
     * 取得 境内托管机构名称
     * @return 境内托管机构名称
     */
    public String getInDepName() {
        return inDepName;
    }

    public String getInDepNameStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.境内托管机构名称,this.inDepName);
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

    public String getOutDepCtryStr() {
        if (this.getOutDepCtry()!=null && (!this.getOutDepCtry().equals("")) ){
            String[] split = this.getOutDepCtry().split(",");
            if (split.length > 1 ){
                String outDepCtryStr = "";
                for (String str : split) {
                    outDepCtryStr += SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区,str) + ",";
                }
                return outDepCtryStr.substring(0,outDepCtryStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区,this.getOutDepCtry());
        }
        return this.getOutDepCtry();
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
     * 取得 投资者风险偏好
     * @return 投资者风险偏好
     */
    public String getRiskType() {
        return riskType;
    }

    /**
     * 数据转换
     * @return
     */
    public String getRiskTypeStr() {
        if (this.getRiskType()!=null && (!this.getRiskType().equals("")) ){
            String[] split = this.getRiskType().split(",");
            if (split.length > 1 ){
                String riskTypeStr = "";
                for (String str : split) {
                    riskTypeStr += SysDataUtil.getNameFromDataDict(DictCode.投资者风险偏好,str) + ",";
                }
                return riskTypeStr.substring(0,riskTypeStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.投资者风险偏好,this.getRiskType());
        }
        return this.getRiskType();
    }

    /**
     * 设置 投资者风险偏好
     * @param riskType 投资者风险偏好
     */
    public void setRiskType(String riskType) {
        this.riskType = riskType == null ? null : riskType.trim();
    }

    /**
     * 取得 产品风险等级
     * @return 产品风险等级
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    public String getRiskLevelStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.产品风险等级,this.riskLevel);
    }

    /**
     * 设置 产品风险等级
     * @param riskLevel 产品风险等级
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel == null ? null : riskLevel.trim();
    }

    /**
     * 取得 发行机构提前终止权标识
     * @return 发行机构提前终止权标识
     */
    public String getTermRightFlag() {
        return termRightFlag;
    }

    public String getTermRightFlagStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.有无,this.termRightFlag);
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

    public String getCusRedeemFlagStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.有无,this.cusRedeemFlag);
    }

    /**
     * 设置 客户赎回权标识
     * @param cusRedeemFlag 客户赎回权标识
     */
    public void setCusRedeemFlag(String cusRedeemFlag) {
        this.cusRedeemFlag = cusRedeemFlag == null ? null : cusRedeemFlag.trim();
    }

    /**
     * 取得 投资管理费率%
     * @return 投资管理费率%
     */
    public BigDecimal getInvestFeeRate() {
        return investFeeRate;
    }

    /**
     * 设置 投资管理费率%
     * @param investFeeRate 投资管理费率%
     */
    public void setInvestFeeRate(BigDecimal investFeeRate) {
        this.investFeeRate = investFeeRate;
    }

    /**
     * 取得 合作模式
     * @return 合作模式
     */
    public String getContMode() {
        return contMode;
    }

    public String getContModeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.合作模式,this.contMode);
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
     * 取得 投资本金到账日
     * @return 投资本金到账日
     */
    public String getInvestPrinDate() {
        return investPrinDate;
    }

    public String getInvestPrinDateStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.投资本金_收益_到账日,this.investPrinDate);
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

    public String getInvestProfDateStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.投资本金_收益_到账日,this.investProfDate);
    }

    /**
     * 设置 投资收益到账日
     * @param investProfDate 投资收益到账日
     */
    public void setInvestProfDate(String investProfDate) {
        this.investProfDate = investProfDate == null ? null : investProfDate.trim();
    }

    /**
     * 取得 产品增信标识
     * @return 产品增信标识
     */
    public String getCreditFlag() {
        return creditFlag;
    }

    public String getCreditFlagStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.有无,this.creditFlag);
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

    // TODO 待验证
    public String getCreditTypeStr() {
        if (this.getCreditType()!=null && (!this.getCreditType().equals("")) ){
            String[] split = this.getCreditType().split(",");
            if (split.length > 1 ){
                String creditTypeStr = "";
                for (String str : split) {
                    creditTypeStr += SysDataUtil.getNameFromDataDict(DictCode.产品增信机构类型,str) + ",";
                }
                return creditTypeStr.substring(0,creditTypeStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.产品增信机构类型,this.getCreditType());
        }
        return this.getCreditType();
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

    public String getCreditModeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.产品增信形式,this.creditMode);
    }

    /**
     * 设置 产品增信形式
     * @param creditMode 产品增信形式
     */
    public void setCreditMode(String creditMode) {
        this.creditMode = creditMode == null ? null : creditMode.trim();
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
     * 取得 机构代码
     * @return 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 null
     * @param deptCode null
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 设置 null
     * @param workflowId null
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * 设置 null
     * @param datasource null
     */
    public void setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
    }

    /**
     * 取得 是否合规新产品-新老产品标记（01：新产品 02：老产品）
     * @return 是否合规新产品-新老产品标记（01：新产品 02：老产品）
     */
    public String getIsFhxgcp() {
        return isFhxgcp;
    }

    public String getIsFhxgcpStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.是否,this.isFhxgcp);
    }

    /**
     * 设置 是否合规新产品-新老产品标记（01：新产品 02：老产品）
     * @param isFhxgcp 是否合规新产品-新老产品标记（01：新产品 02：老产品）
     */
    public void setIsFhxgcp(String isFhxgcp) {
        this.isFhxgcp = isFhxgcp == null ? null : isFhxgcp.trim();
    }

    /**
     * 取得 产品特殊属性
     * @return 产品特殊属性
     */
    public String getPrdSpecProperty() {
        return prdSpecProperty;
    }

    public String getPrdSpecPropertyStr() {
        if (this.getPrdSpecProperty()!=null && (!this.getPrdSpecProperty().equals("")) ){
            String[] split = this.getPrdSpecProperty().split(",");
            if (split.length > 1 ){
                String prdSpecPropertyStr = "";
                for (String str : split) {
                    prdSpecPropertyStr += SysDataUtil.getNameFromDataDict(DictCode.产品特殊属性,str) + ",";
                }
                return prdSpecPropertyStr.substring(0,prdSpecPropertyStr.length()-1);
            }
            return SysDataUtil.getNameFromDataDict(DictCode.产品特殊属性,this.getPrdSpecProperty());
        }
        return this.getPrdSpecProperty();
    }

    /**
     * 设置 产品特殊属性
     * @param prdSpecProperty 产品特殊属性
     */
    public void setPrdSpecProperty(String prdSpecProperty) {
        this.prdSpecProperty = prdSpecProperty == null ? null : prdSpecProperty.trim();
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
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

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public List<SvrPerProDeclareInfo001Dto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<SvrPerProDeclareInfo001Dto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,this.status);
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public BigDecimal getFileNum() {
        return fileNum;
    }

    public void setFileNum(BigDecimal fileNum) {
        this.fileNum = fileNum;
    }

    public String getTenFile() {
        return tenFile;
    }

    public void setTenFile(String tenFile) {
        this.tenFile = tenFile;
    }

    public String getOrgData() {
        return orgData;
    }

    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }
}