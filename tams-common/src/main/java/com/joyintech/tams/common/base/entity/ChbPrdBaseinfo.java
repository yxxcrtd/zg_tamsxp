package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;

import java.math.BigDecimal;
import java.util.Date;

public class ChbPrdBaseinfo extends ChbPrdBaseinfoKey {
	//交易日历
	private String calendarId;
	
	public String getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	private String finprodid;
	private String finprodtype2;
	/**
     * 受托职责（01 主动管理、02 被动管理）
     */
    private String fiduciaryDuty;
    
    /**
     * 转让类型（01 收益权转让、99 其他）
     */
    private String transferType;
    
    /**
     * 主键，自动生成
     */
    private String produuid;

    /**
     * 父产品代码
     */
    private String fprodId;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 产品名称
     */
    private String prodName;

    /**
     * 产品登记编码
     */
    private String regCode;

    /**
     * 信息缺失项 无缺失、缺失中债申报、缺失中债发行
     */
    private String lacks;

    /**
     * 行内标识码
     */
    private String prodInsId;

    /**
     * 产品收益类型  01 保证收益/02 保本浮动收益/03 非保本浮动收益  
     */
    private String profitType;

    /**
     * 投资者类型
     */
    private String investorType;

    /**
     * 发行要素维护完整
     */
    private String isIntact;

    /**
     * 数据状态  A 新建/ E 有效/D删除
     */
    private String status;

    /**
     * 计息基础 A/360, A/365  
     */
    private String basic;

  

    /**
     * 标识哪种产品，净值N，货币M，利率R，客户周期C
     */
    private String prodtype;

   

    /**
     * 是否代销，01/02
     */
    private String isagency;

    /**
     * 目标客户类型,01 私人银行客户专属/02 机构客户专属/03 一般个人客户/04 金融同业专属/05 高资产净值客户
     */
    private String saletarget;

    /**
     * 存续状态  存续01，终止02，未起息03  
     */
    private String durationflag;

    /**
     * 到期日
     */
    private Date mdate;

    /**
     * 起息日
     */
    private Date vdate;

    /**
     * 提前终止日
     */
    private Date terminatedate;

    /**
     * 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     */
    private String ivstorientation;

    /**
     * 开放期业务说明
     */
    private String openBussRemark;

    /**
     * 募集起始日期（从）
     */
    private Date raisePstrdate;

    /**
     * 募集起始日期（到）
     */
    private Date raisePenddate;

    /**
     * 报备状态
     */
    private String declareStatus;

    /**
     * 发行状态  确认发行/发行成功/发行失败/不发行  
     */
    private String issuingStatus;

    /**
     * 产品风险等级  01 一级（低）/02 二级（中低）/03 三级（中）/04 四级（中高）/05 五级（高）  
     */
    private String riskLevel;

    /**
     * 产品期限01 T+0产品/02 7天（含）以内/03 7天-1个月（含）/04 1-3个月（含）/05 3-6 25个月(含)/06 6-12个月（含）/07 1-3年（含）/08 3年以上
     */
    private String termtype;

    /**
     * 募集币种
     */
    private String raiseCcy;

    /**
     * 募集起始日期
     */
    private Date raiseVdate;

    /**
     * 募集结束日期
     */
    private Date raiseEnddate;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 产品品牌
     */
    private String prodBrand;

    /**
     * 产品期次
     */
    private BigDecimal prodStage;

    /**
     * 产品设计人姓名
     */
    private String designerName;

    /**
     * 产品设计人身份证号
     */
    private String designerId;

    /**
     * 产品审批人姓名
     */
    private String approverName;

    /**
     * 产品审批人身份证号
     */
    private String approverId;

    /**
     * 投资经理姓名
     */
    private String managerName;

    /**
     * 投资经理身份证号
     */
    private String managerId;

    /**
     * 会计核算方式  01 表内/02 表外  
     */
    private String bookMode;

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
     * 资金投向地区  01 境内/02 境外/03 境内和境外  
     */
    private String investArae;

    /**
     * 产品投资国家或地区参考《世界各国和地区名称代码》
     */
    private String investCountry;

    /**
     * 理财业务服务模式01 综合理财服务/02 理财顾问服务/99 其他
     */
    private String finanSerModel;

    /**
     * 产品运作模式  01 封闭式净值型/02 封闭式非净值型/03 开放式净值型/04 开放式非净值型  
     */
    private String operateMode;

    /**
     * 产品资产配置方式01 单一资产配置（一对一）/02 资产组合配置（一对多）/03 其他配置（多对多）
     */
    private String cmmode;

    /**
     * 产品管理模式01银行为实际管理人/02 其他机构为实际管理人/03 资产管理公司/04 特殊目的载体（SPV）
     */
    private String pmmode;

    /**
     * 实际管理人名称
     */
    private String mname;

    /**
     * 投资资产类型  01 结构性投资类/02 货币市场工具类/03 债券类/04 非标准化债权类/05 股票类/06 股权类/07 另类投资类/08 混合类/09代客境外投资类/10 银行存款类/11 理财直接融资工具类/12 新增可投资资产类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类  
     */
    private String invAssetType;

    /**
     * 合作模式  01 独立运作/02 银信/03 银保/04 银基/05 银证/07 混合类/99 其他  
     */
    private String contMode;

    /**
     * 合作机构
     */
    private String cooperationName;

    /**
     * 结构性产品挂钩标的01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    private String conPrdTraget;

    /**
     * 是否有预期收益率01 是/02 否
     */
    private String isYield;

    /**
     * 是否有收益率测算依据  01 是/02 否  
     */
    private String isYieldMeasBasis;

    /**
     * 投资资产种类及比例
     */
    private String invTypeRat;

    /**
     * 投资者风险偏好01 保守型/02 稳健型/03 平衡型/04 成长型/05 进取型
     */
    private String riskType;

    /**
     * 预计客户最高年收益率%
     */
    private BigDecimal highYield;

    /**
     * 预计客户最低年收益率%
     */
    private BigDecimal lowYield;

    /**
     * 产品销售地区
     */
    private String saleArea;

    /**
     * 起点销售金额
     */
    private BigDecimal saleAmtStr;

    /**
     * 兑付本金币种
     */
    private String cashPrinCcy;

    /**
     * 兑付收益币种
     */
    private String cashProfCcy;

    /**
     * 计划募集金额（元）
     */
    private BigDecimal raiseAmtPlan;

    /**
     * 销售手续费率%
     */
    private BigDecimal saleFeeRate;

    /**
     * 投资本金到账日
     */
    private String investPrinDate;

    /**
     * 投资收益到账日
     */
    private String investProfDate;

    /**
     * 托管费率
     */
    private BigDecimal truFeeRate;

    /**
     * 境内托管机构代码
     */
    private String inDepCode;

    /**
     * 境内托管机构名称
     */
    private String inDepName;

    /**
     * 境外托管机构国别
     */
    private String outDepCtry;

    /**
     * 境外托管机构名称
     */
    private String outDepName;

    /**
     * 发行机构提前终止权标识
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
     * 产品定价方式
     */
    private String pricedMode;

    /**
     * 产品起始日期
     */
    private Date prdVdate;

    /**
     * 产品终止日期
     */
    private Date prdMdate;

    /**
     * 平均预计客户年收益率
     */
    private BigDecimal expAvgYield;

    /**
     * 是否结构化分层产品
     */
    private String isConstracte;

    /**
     * 层级类别  11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五  
     */
    private String gradeType;

    /**
     * 收益率分档情况说明
     */
    private String yieldComment;

    /**
     *   开放模式01 有规律开放/02 无规律开放
     */
    private String openMode;

    /**
     * 规律开放周期
     */
    private String openCircle;

    /**
     * 其他规律开放周期（天）
     */
    private BigDecimal openCircleDays;

    /**
     * 无规律开放说明
     */
    private String ruleComment;

    /**
     * 首次开放周期起始日
     */
    private Date firstOpenDay;

    /**
     * 节假日是否开放
     */
    private String holidayOnFlag;

    /**
     * 平均开放次数（年化）
     */
    private BigDecimal avgOpenCount;

    /**
     * 开放期业务01申赎皆可/02 仅可申购无限制/ 03仅可赎回无限制 /04仅可申购有限制/05仅可赎回有限制 /99其他
     */
    private String openBuss;

    /**
     * 预计最高收益率-发行
     */
    private BigDecimal highYieldIssue;

    /**
     * 预计最低收益率-发行
     */
    private BigDecimal lowYieldIssue;

    /**
     * 产品增信形式
     */
    private String creditMode;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 开放模式申
     */
    private String opentypep;

    /**
     * 开放天数申
     */
    private String opendayp;

    /**
     * 开放周期申
     */
    private String opencyclep;

    /**
     * 开放周期单位
     */
    private String opencycleunitp;

    /**
     * 周申
     */
    private String weekp;

    /**
     * 几号申
     */
    private String dayp;

    /**
     * 第几工作日申
     */
    private String workdayp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 募集方式
     */
    private String raiseType;

    /**
     * 管理方式
     */
    private String manageType;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 经办人
     */
    private String agent;

    //新增
    private String excFinSector;//是否金融同业专属
    private String isShortTerm;//是否设置最短持有期限
    private BigDecimal shortTerm;//最短持有期限（天）
    private String isFreeRedem;//最短持有期后是否自由赎回
    private String cashManaCate;//是否现金管理类
    private BigDecimal yield;//业绩比较基准%
    private BigDecimal investFeeRate;//投资管理费率%
    //新增字段 CUSTOMER_TYPE 客户类型  支持多选，多选时用英文“,”分隔
    //  3-住户，4-广义政府，5-非金融企业，6-金融机构（实体），7-特定目的载体 ，8-境外  --人行报表
    private String customerType;//最短持有期后是否自由赎回


    /***
     * 产品特殊属性
     */
    private  String  prdSpecProperty;


    /**
     * 业绩比较基准下限
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal yieldLow;

    /**
     * 业绩比较基准上限
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal yieldHigh;

    /**
     * 业绩比较基准说明
     */
    private String yieldRemark;

    /**
     * 资金托管账号
     */
    private String busiAcctNo;

    /**
     * 资金托管账户
     */
    private String acctName;

    /**
     * 是否母行划转产品
     */
    private String isTransfer;

    /**
     * 是否合规新产品
     */
    private String isFhxgcp;

    /**
     * 是否养老理财产品
     */
    private String isYllccp;

    /**
     * 分级比例
     */
    private  BigDecimal gradProportion;

    /**
     * 以财产权信托名义开展的资金信托标识
     */
    private  String zjxtFlag;
    // add by wubingbing 20220528 start
    /**
     * 现金管理类产品-是否_中债完全符合
     */
    private String manaCateFit;
    /**
     * 状态备注
     */
    private String issueStatusRemark;
    /**
     * 定期开放周期(天)
     */
    private BigDecimal fitCircleDays;
    // add by wubingbing 20220528 end

    public String getPrdSpecProperty() {
        return prdSpecProperty;
    }

    public void setPrdSpecProperty(String prdSpecProperty) {
        this.prdSpecProperty = prdSpecProperty;
    }

    public BigDecimal getGradProportion() {
        return gradProportion;
    }

    public void setGradProportion(BigDecimal gradProportion) {
        this.gradProportion = gradProportion;
    }

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer;
    }

    public String getIsFhxgcp() {
        return isFhxgcp;
    }

    public void setIsFhxgcp(String isFhxgcp) {
        this.isFhxgcp = isFhxgcp;
    }

    public String getIsYllccp() {
        return isYllccp;
    }

    public void setIsYllccp(String isYllccp) {
        this.isYllccp = isYllccp;
    }

    /**
     * 主键，自动生成
     * @return PRODUUID 主键，自动生成
     */
    public String getProduuid() {
        return produuid;
    }

    /**
     * 主键，自动生成
     * @param produuid 主键，自动生成
     */
    public void setProduuid(String produuid) {
        this.produuid = produuid == null ? null : produuid.trim();
    }

    /**
     * 父产品代码
     * @return FPROD_ID 父产品代码
     */
    public String getFprodId() {
        return fprodId;
    }

    /**
     * 父产品代码
     * @param fprodId 父产品代码
     */
    public void setFprodId(String fprodId) {
        this.fprodId = fprodId == null ? null : fprodId.trim();
    }

    /**
     * 产品代码
     * @return PROD_ID 产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 产品代码
     * @param prodId 产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 机构代码
     * @return ORG_CODE 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 产品名称
     * @return PROD_NAME 产品名称
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 产品名称
     * @param prodName 产品名称
     */
    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    /**
     * 产品登记编码
     * @return REG_CODE 产品登记编码
     */
    public String getRegCode() {
        return regCode;
    }

    /**
     * 产品登记编码
     * @param regCode 产品登记编码
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode == null ? null : regCode.trim();
    }

    /**
     * 信息缺失项 无缺失、缺失中债申报、缺失中债发行
     * @return LACKS 信息缺失项 无缺失、缺失中债申报、缺失中债发行
     */
    public String getLacks() {
        return lacks;
    }

    /**
     * 信息缺失项 无缺失、缺失中债申报、缺失中债发行
     * @param lacks 信息缺失项 无缺失、缺失中债申报、缺失中债发行
     */
    public void setLacks(String lacks) {
        this.lacks = lacks == null ? null : lacks.trim();
    }

    /**
     * 行内标识码
     * @return PROD_INS_ID 行内标识码
     */
    public String getProdInsId() {
        return prodInsId;
    }

    /**
     * 行内标识码
     * @param prodInsId 行内标识码
     */
    public void setProdInsId(String prodInsId) {
        this.prodInsId = prodInsId == null ? null : prodInsId.trim();
    }

    /**
     * 产品收益类型  01 保证收益/02 保本浮动收益/03 非保本浮动收益  
     * @return PROFIT_TYPE 产品收益类型  01 保证收益/02 保本浮动收益/03 非保本浮动收益  
     */
    public String getProfitType() {
        return profitType;
    }

    /**
     * 产品收益类型  01 保证收益/02 保本浮动收益/03 非保本浮动收益  
     * @param profitType 产品收益类型  01 保证收益/02 保本浮动收益/03 非保本浮动收益  
     */
    public void setProfitType(String profitType) {
        this.profitType = profitType == null ? null : profitType.trim();
    }

    /**
     * 投资者类型
     * @return INVESTOR_TYPE 投资者类型
     */
    public String getInvestorType() {
        return investorType;
    }

    /**
     * 投资者类型
     * @param investorType 投资者类型
     */
    public void setInvestorType(String investorType) {
        this.investorType = investorType == null ? null : investorType.trim();
    }

    /**
     * 发行要素维护完整
     * @return IS_INTACT 发行要素维护完整
     */
    public String getIsIntact() {
        return isIntact;
    }

    /**
     * 发行要素维护完整
     * @param isIntact 发行要素维护完整
     */
    public void setIsIntact(String isIntact) {
        this.isIntact = isIntact == null ? null : isIntact.trim();
    }

    /**
     * 数据状态  A 新建/ E 有效/D删除
     * @return STATUS 数据状态  A 新建/ E 有效/D删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 数据状态  A 新建/ E 有效/D删除
     * @param status 数据状态  A 新建/ E 有效/D删除
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 计息基础 A/360, A/365  
     * @return BASIC 计息基础 A/360, A/365  
     */
    public String getBasic() {
        return basic;
    }

    /**
     * 计息基础 A/360, A/365  
     * @param basic 计息基础 A/360, A/365  
     */
    public void setBasic(String basic) {
        this.basic = basic == null ? null : basic.trim();
    }

    

    /**
     * 标识哪种产品，净值N，货币M，利率R，客户周期C
     * @return PRODTYPE 标识哪种产品，净值N，货币M，利率R，客户周期C
     */
    public String getProdtype() {
        return prodtype;
    }

    /**
     * 标识哪种产品，净值N，货币M，利率R，客户周期C
     * @param prodtype 标识哪种产品，净值N，货币M，利率R，客户周期C
     */
    public void setProdtype(String prodtype) {
        this.prodtype = prodtype == null ? null : prodtype.trim();
    }

    /**
     * 是否代销，01/02
     * @return ISAGENCY 是否代销，01/02
     */
    public String getIsagency() {
        return isagency;
    }

    /**
     * 是否代销，01/02
     * @param isagency 是否代销，01/02
     */
    public void setIsagency(String isagency) {
        this.isagency = isagency == null ? null : isagency.trim();
    }

    /**
     * 目标客户类型,01 私人银行客户专属/02 机构客户专属/03 一般个人客户/04 金融同业专属/05 高资产净值客户
     * @return SALETARGET 目标客户类型,01 私人银行客户专属/02 机构客户专属/03 一般个人客户/04 金融同业专属/05 高资产净值客户
     */
    public String getSaletarget() {
        return saletarget;
    }

    /**
     * 目标客户类型,01 私人银行客户专属/02 机构客户专属/03 一般个人客户/04 金融同业专属/05 高资产净值客户
     * @param saletarget 目标客户类型,01 私人银行客户专属/02 机构客户专属/03 一般个人客户/04 金融同业专属/05 高资产净值客户
     */
    public void setSaletarget(String saletarget) {
        this.saletarget = saletarget == null ? null : saletarget.trim();
    }

    /**
     * 存续状态  存续01，终止02，未起息03  
     * @return DURATIONFLAG 存续状态  存续01，终止02，未起息03  
     */
    public String getDurationflag() {
        return durationflag;
    }

    /**
     * 存续状态  存续01，终止02，未起息03  
     * @param durationflag 存续状态  存续01，终止02，未起息03  
     */
    public void setDurationflag(String durationflag) {
        this.durationflag = durationflag == null ? null : durationflag.trim();
    }

    /**
     * 到期日
     * @return MDATE 到期日
     */
    public Date getMdate() {
        return mdate;
    }

    /**
     * 到期日
     * @param mdate 到期日
     */
    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public Date getVdate() {
        return vdate;
    }

    /**
     * 起息日
     * @param vdate 起息日
     */
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    /**
     * 提前终止日
     * @return TERMINATEDATE 提前终止日
     */
    public Date getTerminatedate() {
        return terminatedate;
    }

    /**
     * 提前终止日
     * @param terminatedate 提前终止日
     */
    public void setTerminatedate(Date terminatedate) {
        this.terminatedate = terminatedate;
    }

    /**
     * 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     * @return IVSTORIENTATION 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     */
    public String getIvstorientation() {
        return ivstorientation;
    }

    /**
     * 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     * @param ivstorientation 投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
     */
    public void setIvstorientation(String ivstorientation) {
        this.ivstorientation = ivstorientation == null ? null : ivstorientation.trim();
    }

    /**
     * 开放期业务说明
     * @return OPEN_BUSS_REMARK 开放期业务说明
     */
    public String getOpenBussRemark() {
        return openBussRemark;
    }

    /**
     * 开放期业务说明
     * @param openBussRemark 开放期业务说明
     */
    public void setOpenBussRemark(String openBussRemark) {
        this.openBussRemark = openBussRemark == null ? null : openBussRemark.trim();
    }

    /**
     * 募集起始日期（从）
     * @return RAISE_PSTRDATE 募集起始日期（从）
     */
    public Date getRaisePstrdate() {
        return raisePstrdate;
    }

    /**
     * 募集起始日期（从）
     * @param raisePstrdate 募集起始日期（从）
     */
    public void setRaisePstrdate(Date raisePstrdate) {
        this.raisePstrdate = raisePstrdate;
    }

    /**
     * 募集起始日期（到）
     * @return RAISE_PENDDATE 募集起始日期（到）
     */
    public Date getRaisePenddate() {
        return raisePenddate;
    }

    /**
     * 募集起始日期（到）
     * @param raisePenddate 募集起始日期（到）
     */
    public void setRaisePenddate(Date raisePenddate) {
        this.raisePenddate = raisePenddate;
    }

    /**
     * 报备状态
     * @return DECLARE_STATUS 报备状态
     */
    public String getDeclareStatus() {
        return declareStatus;
    }

    /**
     * 报备状态
     * @param declareStatus 报备状态
     */
    public void setDeclareStatus(String declareStatus) {
        this.declareStatus = declareStatus == null ? null : declareStatus.trim();
    }

    /**
     * 发行状态  确认发行/发行成功/发行失败/不发行  
     * @return ISSUING_STATUS 发行状态  确认发行/发行成功/发行失败/不发行  
     */
    public String getIssuingStatus() {
        return issuingStatus;
    }

    /**
     * 发行状态  确认发行/发行成功/发行失败/不发行  
     * @param issuingStatus 发行状态  确认发行/发行成功/发行失败/不发行  
     */
    public void setIssuingStatus(String issuingStatus) {
        this.issuingStatus = issuingStatus == null ? null : issuingStatus.trim();
    }

    /**
     * 产品风险等级  01 一级（低）/02 二级（中低）/03 三级（中）/04 四级（中高）/05 五级（高）  
     * @return RISK_LEVEL 产品风险等级  01 一级（低）/02 二级（中低）/03 三级（中）/04 四级（中高）/05 五级（高）  
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * 产品风险等级  01 一级（低）/02 二级（中低）/03 三级（中）/04 四级（中高）/05 五级（高）  
     * @param riskLevel 产品风险等级  01 一级（低）/02 二级（中低）/03 三级（中）/04 四级（中高）/05 五级（高）  
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel == null ? null : riskLevel.trim();
    }

    /**
     * 产品期限01 T+0产品/02 7天（含）以内/03 7天-1个月（含）/04 1-3个月（含）/05 3-6 25个月(含)/06 6-12个月（含）/07 1-3年（含）/08 3年以上
     * @return TERMTYPE 产品期限01 T+0产品/02 7天（含）以内/03 7天-1个月（含）/04 1-3个月（含）/05 3-6 25个月(含)/06 6-12个月（含）/07 1-3年（含）/08 3年以上
     */
    public String getTermtype() {
        return termtype;
    }

    /**
     * 产品期限01 T+0产品/02 7天（含）以内/03 7天-1个月（含）/04 1-3个月（含）/05 3-6 25个月(含)/06 6-12个月（含）/07 1-3年（含）/08 3年以上
     * @param termtype 产品期限01 T+0产品/02 7天（含）以内/03 7天-1个月（含）/04 1-3个月（含）/05 3-6 25个月(含)/06 6-12个月（含）/07 1-3年（含）/08 3年以上
     */
    public void setTermtype(String termtype) {
        this.termtype = termtype == null ? null : termtype.trim();
    }

    /**
     * 募集币种
     * @return RAISE_CCY 募集币种
     */
    public String getRaiseCcy() {
        return raiseCcy;
    }

    /**
     * 募集币种
     * @param raiseCcy 募集币种
     */
    public void setRaiseCcy(String raiseCcy) {
        this.raiseCcy = raiseCcy == null ? null : raiseCcy.trim();
    }

    /**
     * 募集起始日期
     * @return RAISE_VDATE 募集起始日期
     */
    public Date getRaiseVdate() {
        return raiseVdate;
    }

    /**
     * 募集起始日期
     * @param raiseVdate 募集起始日期
     */
    public void setRaiseVdate(Date raiseVdate) {
        this.raiseVdate = raiseVdate;
    }

    /**
     * 募集结束日期
     * @return RAISE_ENDDATE 募集结束日期
     */
    public Date getRaiseEnddate() {
        return raiseEnddate;
    }

    /**
     * 募集结束日期
     * @param raiseEnddate 募集结束日期
     */
    public void setRaiseEnddate(Date raiseEnddate) {
        this.raiseEnddate = raiseEnddate;
    }

    /**
     * 模板名称
     * @return TEMPLATE_NAME 模板名称
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * 模板名称
     * @param templateName 模板名称
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    /**
     * 产品品牌
     * @return PROD_BRAND 产品品牌
     */
    public String getProdBrand() {
        return prodBrand;
    }

    /**
     * 产品品牌
     * @param prodBrand 产品品牌
     */
    public void setProdBrand(String prodBrand) {
        this.prodBrand = prodBrand == null ? null : prodBrand.trim();
    }

    /**
     * 产品期次
     * @return PROD_STAGE 产品期次
     */
    public BigDecimal getProdStage() {
        return prodStage;
    }

    /**
     * 产品期次
     * @param prodStage 产品期次
     */
    public void setProdStage(BigDecimal prodStage) {
        this.prodStage = prodStage;
    }

    /**
     * 产品设计人姓名
     * @return DESIGNER_NAME 产品设计人姓名
     */
    public String getDesignerName() {
        return designerName;
    }

    /**
     * 产品设计人姓名
     * @param designerName 产品设计人姓名
     */
    public void setDesignerName(String designerName) {
        this.designerName = designerName == null ? null : designerName.trim();
    }

    /**
     * 产品设计人身份证号
     * @return DESIGNER_ID 产品设计人身份证号
     */
    public String getDesignerId() {
        return designerId;
    }

    /**
     * 产品设计人身份证号
     * @param designerId 产品设计人身份证号
     */
    public void setDesignerId(String designerId) {
        this.designerId = designerId == null ? null : designerId.trim();
    }

    /**
     * 产品审批人姓名
     * @return APPROVER_NAME 产品审批人姓名
     */
    public String getApproverName() {
        return approverName;
    }

    /**
     * 产品审批人姓名
     * @param approverName 产品审批人姓名
     */
    public void setApproverName(String approverName) {
        this.approverName = approverName == null ? null : approverName.trim();
    }

    /**
     * 产品审批人身份证号
     * @return APPROVER_ID 产品审批人身份证号
     */
    public String getApproverId() {
        return approverId;
    }

    /**
     * 产品审批人身份证号
     * @param approverId 产品审批人身份证号
     */
    public void setApproverId(String approverId) {
        this.approverId = approverId == null ? null : approverId.trim();
    }

    /**
     * 投资经理姓名
     * @return MANAGER_NAME 投资经理姓名
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * 投资经理姓名
     * @param managerName 投资经理姓名
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    /**
     * 投资经理身份证号
     * @return MANAGER_ID 投资经理身份证号
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * 投资经理身份证号
     * @param managerId 投资经理身份证号
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    /**
     * 会计核算方式  01 表内/02 表外  
     * @return BOOK_MODE 会计核算方式  01 表内/02 表外  
     */
    public String getBookMode() {
        return bookMode;
    }

    /**
     * 会计核算方式  01 表内/02 表外  
     * @param bookMode 会计核算方式  01 表内/02 表外  
     */
    public void setBookMode(String bookMode) {
        this.bookMode = bookMode == null ? null : bookMode.trim();
    }

    /**
     * 业务联络人姓名
     * @return LIAISON_NAME 业务联络人姓名
     */
    public String getLiaisonName() {
        return liaisonName;
    }

    /**
     * 业务联络人姓名
     * @param liaisonName 业务联络人姓名
     */
    public void setLiaisonName(String liaisonName) {
        this.liaisonName = liaisonName == null ? null : liaisonName.trim();
    }

    /**
     * 业务联络人座机
     * @return LIAISON_TEL 业务联络人座机
     */
    public String getLiaisonTel() {
        return liaisonTel;
    }

    /**
     * 业务联络人座机
     * @param liaisonTel 业务联络人座机
     */
    public void setLiaisonTel(String liaisonTel) {
        this.liaisonTel = liaisonTel == null ? null : liaisonTel.trim();
    }

    /**
     * 业务联络人手机
     * @return LIAISON_MOBILE 业务联络人手机
     */
    public String getLiaisonMobile() {
        return liaisonMobile;
    }

    /**
     * 业务联络人手机
     * @param liaisonMobile 业务联络人手机
     */
    public void setLiaisonMobile(String liaisonMobile) {
        this.liaisonMobile = liaisonMobile == null ? null : liaisonMobile.trim();
    }

    /**
     * 业务联络人邮箱
     * @return LIAISON_EMAIL 业务联络人邮箱
     */
    public String getLiaisonEmail() {
        return liaisonEmail;
    }

    /**
     * 业务联络人邮箱
     * @param liaisonEmail 业务联络人邮箱
     */
    public void setLiaisonEmail(String liaisonEmail) {
        this.liaisonEmail = liaisonEmail == null ? null : liaisonEmail.trim();
    }

    /**
     * 资金投向地区  01 境内/02 境外/03 境内和境外  
     * @return INVEST_ARAE 资金投向地区  01 境内/02 境外/03 境内和境外  
     */
    public String getInvestArae() {
        return investArae;
    }

    /**
     * 资金投向地区  01 境内/02 境外/03 境内和境外  
     * @param investArae 资金投向地区  01 境内/02 境外/03 境内和境外  
     */
    public void setInvestArae(String investArae) {
        this.investArae = investArae == null ? null : investArae.trim();
    }

    /**
     * 产品投资国家或地区参考《世界各国和地区名称代码》
     * @return INVEST_COUNTRY 产品投资国家或地区参考《世界各国和地区名称代码》
     */
    public String getInvestCountry() {
        return investCountry;
    }

    /**
     * 产品投资国家或地区参考《世界各国和地区名称代码》
     * @param investCountry 产品投资国家或地区参考《世界各国和地区名称代码》
     */
    public void setInvestCountry(String investCountry) {
        this.investCountry = investCountry == null ? null : investCountry.trim();
    }

    /**
     * 理财业务服务模式01 综合理财服务/02 理财顾问服务/99 其他
     * @return FINAN_SER_MODEL 理财业务服务模式01 综合理财服务/02 理财顾问服务/99 其他
     */
    public String getFinanSerModel() {
        return finanSerModel;
    }

    /**
     * 理财业务服务模式01 综合理财服务/02 理财顾问服务/99 其他
     * @param finanSerModel 理财业务服务模式01 综合理财服务/02 理财顾问服务/99 其他
     */
    public void setFinanSerModel(String finanSerModel) {
        this.finanSerModel = finanSerModel == null ? null : finanSerModel.trim();
    }

    /**
     * 产品运作模式  01 封闭式净值型/02 封闭式非净值型/03 开放式净值型/04 开放式非净值型  
     * @return OPERATE_MODE 产品运作模式  01 封闭式净值型/02 封闭式非净值型/03 开放式净值型/04 开放式非净值型  
     */
    public String getOperateMode() {
        return operateMode;
    }

    /**
     * 产品运作模式  01 封闭式净值型/02 封闭式非净值型/03 开放式净值型/04 开放式非净值型  
     * @param operateMode 产品运作模式  01 封闭式净值型/02 封闭式非净值型/03 开放式净值型/04 开放式非净值型  
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode == null ? null : operateMode.trim();
    }

    /**
     * 产品资产配置方式01 单一资产配置（一对一）/02 资产组合配置（一对多）/03 其他配置（多对多）
     * @return CMMODE 产品资产配置方式01 单一资产配置（一对一）/02 资产组合配置（一对多）/03 其他配置（多对多）
     */
    public String getCmmode() {
        return cmmode;
    }

    /**
     * 产品资产配置方式01 单一资产配置（一对一）/02 资产组合配置（一对多）/03 其他配置（多对多）
     * @param cmmode 产品资产配置方式01 单一资产配置（一对一）/02 资产组合配置（一对多）/03 其他配置（多对多）
     */
    public void setCmmode(String cmmode) {
        this.cmmode = cmmode == null ? null : cmmode.trim();
    }

    /**
     * 产品管理模式01银行为实际管理人/02 其他机构为实际管理人/03 资产管理公司/04 特殊目的载体（SPV）
     * @return PMMODE 产品管理模式01银行为实际管理人/02 其他机构为实际管理人/03 资产管理公司/04 特殊目的载体（SPV）
     */
    public String getPmmode() {
        return pmmode;
    }

    /**
     * 产品管理模式01银行为实际管理人/02 其他机构为实际管理人/03 资产管理公司/04 特殊目的载体（SPV）
     * @param pmmode 产品管理模式01银行为实际管理人/02 其他机构为实际管理人/03 资产管理公司/04 特殊目的载体（SPV）
     */
    public void setPmmode(String pmmode) {
        this.pmmode = pmmode == null ? null : pmmode.trim();
    }

    /**
     * 实际管理人名称
     * @return MNAME 实际管理人名称
     */
    public String getMname() {
        return mname;
    }

    /**
     * 实际管理人名称
     * @param mname 实际管理人名称
     */
    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    /**
     * 投资资产类型  01 结构性投资类/02 货币市场工具类/03 债券类/04 非标准化债权类/05 股票类/06 股权类/07 另类投资类/08 混合类/09代客境外投资类/10 银行存款类/11 理财直接融资工具类/12 新增可投资资产类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类  
     * @return INV_ASSET_TYPE 投资资产类型  01 结构性投资类/02 货币市场工具类/03 债券类/04 非标准化债权类/05 股票类/06 股权类/07 另类投资类/08 混合类/09代客境外投资类/10 银行存款类/11 理财直接融资工具类/12 新增可投资资产类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类  
     */
    public String getInvAssetType() {
        return invAssetType;
    }

    /**
     * 投资资产类型  01 结构性投资类/02 货币市场工具类/03 债券类/04 非标准化债权类/05 股票类/06 股权类/07 另类投资类/08 混合类/09代客境外投资类/10 银行存款类/11 理财直接融资工具类/12 新增可投资资产类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类  
     * @param invAssetType 投资资产类型  01 结构性投资类/02 货币市场工具类/03 债券类/04 非标准化债权类/05 股票类/06 股权类/07 另类投资类/08 混合类/09代客境外投资类/10 银行存款类/11 理财直接融资工具类/12 新增可投资资产类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类  
     */
    public void setInvAssetType(String invAssetType) {
        this.invAssetType = invAssetType == null ? null : invAssetType.trim();
    }

    /**
     * 合作模式  01 独立运作/02 银信/03 银保/04 银基/05 银证/07 混合类/99 其他  
     * @return CONT_MODE 合作模式  01 独立运作/02 银信/03 银保/04 银基/05 银证/07 混合类/99 其他  
     */
    public String getContMode() {
        return contMode;
    }

    /**
     * 合作模式  01 独立运作/02 银信/03 银保/04 银基/05 银证/07 混合类/99 其他  
     * @param contMode 合作模式  01 独立运作/02 银信/03 银保/04 银基/05 银证/07 混合类/99 其他  
     */
    public void setContMode(String contMode) {
        this.contMode = contMode == null ? null : contMode.trim();
    }

    /**
     * 合作机构
     * @return COOPERATION_NAME 合作机构
     */
    public String getCooperationName() {
        return cooperationName;
    }

    /**
     * 合作机构
     * @param cooperationName 合作机构
     */
    public void setCooperationName(String cooperationName) {
        this.cooperationName = cooperationName == null ? null : cooperationName.trim();
    }

    /**
     * 结构性产品挂钩标的01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     * @return CON_PRD_TRAGET 结构性产品挂钩标的01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    public String getConPrdTraget() {
        return conPrdTraget;
    }

    /**
     * 结构性产品挂钩标的01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     * @param conPrdTraget 结构性产品挂钩标的01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    public void setConPrdTraget(String conPrdTraget) {
        this.conPrdTraget = conPrdTraget == null ? null : conPrdTraget.trim();
    }

    /**
     * 是否有预期收益率01 是/02 否
     * @return IS_YIELD 是否有预期收益率01 是/02 否
     */
    public String getIsYield() {
        return isYield;
    }

    /**
     * 是否有预期收益率01 是/02 否
     * @param isYield 是否有预期收益率01 是/02 否
     */
    public void setIsYield(String isYield) {
        this.isYield = isYield == null ? null : isYield.trim();
    }

    /**
     * 是否有收益率测算依据  01 是/02 否  
     * @return IS_YIELD_MEAS_BASIS 是否有收益率测算依据  01 是/02 否  
     */
    public String getIsYieldMeasBasis() {
        return isYieldMeasBasis;
    }

    /**
     * 是否有收益率测算依据  01 是/02 否  
     * @param isYieldMeasBasis 是否有收益率测算依据  01 是/02 否  
     */
    public void setIsYieldMeasBasis(String isYieldMeasBasis) {
        this.isYieldMeasBasis = isYieldMeasBasis == null ? null : isYieldMeasBasis.trim();
    }

    /**
     * 投资资产种类及比例
     * @return INV_TYPE_RAT 投资资产种类及比例
     */
    public String getInvTypeRat() {
        return invTypeRat;
    }

    /**
     * 投资资产种类及比例
     * @param invTypeRat 投资资产种类及比例
     */
    public void setInvTypeRat(String invTypeRat) {
        this.invTypeRat = invTypeRat == null ? null : invTypeRat.trim();
    }

    /**
     * 投资者风险偏好01 保守型/02 稳健型/03 平衡型/04 成长型/05 进取型
     * @return RISK_TYPE 投资者风险偏好01 保守型/02 稳健型/03 平衡型/04 成长型/05 进取型
     */
    public String getRiskType() {
        return riskType;
    }

    /**
     * 投资者风险偏好01 保守型/02 稳健型/03 平衡型/04 成长型/05 进取型
     * @param riskType 投资者风险偏好01 保守型/02 稳健型/03 平衡型/04 成长型/05 进取型
     */
    public void setRiskType(String riskType) {
        this.riskType = riskType == null ? null : riskType.trim();
    }

    /**
     * 预计客户最高年收益率%
     * @return HIGH_YIELD 预计客户最高年收益率%
     */
    public BigDecimal getHighYield() {
        return highYield;
    }

    /**
     * 预计客户最高年收益率%
     * @param highYield 预计客户最高年收益率%
     */
    public void setHighYield(BigDecimal highYield) {
        this.highYield = highYield;
    }

    /**
     * 预计客户最低年收益率%
     * @return LOW_YIELD 预计客户最低年收益率%
     */
    public BigDecimal getLowYield() {
        return lowYield;
    }

    /**
     * 预计客户最低年收益率%
     * @param lowYield 预计客户最低年收益率%
     */
    public void setLowYield(BigDecimal lowYield) {
        this.lowYield = lowYield;
    }

    /**
     * 产品销售地区
     * @return SALE_AREA 产品销售地区
     */
    public String getSaleArea() {
        return saleArea;
    }

    /**
     * 产品销售地区
     * @param saleArea 产品销售地区
     */
    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea == null ? null : saleArea.trim();
    }

    /**
     * 起点销售金额
     * @return SALE_AMT_STR 起点销售金额
     */
    public BigDecimal getSaleAmtStr() {
        return saleAmtStr;
    }

    /**
     * 起点销售金额
     * @param saleAmtStr 起点销售金额
     */
    public void setSaleAmtStr(BigDecimal saleAmtStr) {
        this.saleAmtStr = saleAmtStr;
    }

    /**
     * 兑付本金币种
     * @return CASH_PRIN_CCY 兑付本金币种
     */
    public String getCashPrinCcy() {
        return cashPrinCcy;
    }

    /**
     * 兑付本金币种
     * @param cashPrinCcy 兑付本金币种
     */
    public void setCashPrinCcy(String cashPrinCcy) {
        this.cashPrinCcy = cashPrinCcy == null ? null : cashPrinCcy.trim();
    }

    /**
     * 兑付收益币种
     * @return CASH_PROF_CCY 兑付收益币种
     */
    public String getCashProfCcy() {
        return cashProfCcy;
    }

    /**
     * 兑付收益币种
     * @param cashProfCcy 兑付收益币种
     */
    public void setCashProfCcy(String cashProfCcy) {
        this.cashProfCcy = cashProfCcy == null ? null : cashProfCcy.trim();
    }

    /**
     * 计划募集金额（元）
     * @return RAISE_AMT_PLAN 计划募集金额（元）
     */
    public BigDecimal getRaiseAmtPlan() {
        return raiseAmtPlan;
    }

    /**
     * 计划募集金额（元）
     * @param raiseAmtPlan 计划募集金额（元）
     */
    public void setRaiseAmtPlan(BigDecimal raiseAmtPlan) {
        this.raiseAmtPlan = raiseAmtPlan;
    }

    /**
     * 销售手续费率%
     * @return SALE_FEE_RATE 销售手续费率%
     */
    public BigDecimal getSaleFeeRate() {
        return saleFeeRate;
    }

    /**
     * 销售手续费率%
     * @param saleFeeRate 销售手续费率%
     */
    public void setSaleFeeRate(BigDecimal saleFeeRate) {
        this.saleFeeRate = saleFeeRate;
    }

    /**
     * 投资本金到账日
     * @return INVEST_PRIN_DATE 投资本金到账日
     */
    public String getInvestPrinDate() {
        return investPrinDate;
    }

    /**
     * 投资本金到账日
     * @param investPrinDate 投资本金到账日
     */
    public void setInvestPrinDate(String investPrinDate) {
        this.investPrinDate = investPrinDate == null ? null : investPrinDate.trim();
    }

    /**
     * 投资收益到账日
     * @return INVEST_PROF_DATE 投资收益到账日
     */
    public String getInvestProfDate() {
        return investProfDate;
    }

    /**
     * 投资收益到账日
     * @param investProfDate 投资收益到账日
     */
    public void setInvestProfDate(String investProfDate) {
        this.investProfDate = investProfDate == null ? null : investProfDate.trim();
    }

    /**
     * 托管费率
     * @return TRU_FEE_RATE 托管费率
     */
    public BigDecimal getTruFeeRate() {
        return truFeeRate;
    }

    /**
     * 托管费率
     * @param truFeeRate 托管费率
     */
    public void setTruFeeRate(BigDecimal truFeeRate) {
        this.truFeeRate = truFeeRate;
    }

    /**
     * 境内托管机构代码
     * @return IN_DEP_CODE 境内托管机构代码
     */
    public String getInDepCode() {
        return inDepCode;
    }

    /**
     * 境内托管机构代码
     * @param inDepCode 境内托管机构代码
     */
    public void setInDepCode(String inDepCode) {
        this.inDepCode = inDepCode == null ? null : inDepCode.trim();
    }

    /**
     * 境内托管机构名称
     * @return IN_DEP_NAME 境内托管机构名称
     */
    public String getInDepName() {
        return inDepName;
    }

    /**
     * 境内托管机构名称
     * @param inDepName 境内托管机构名称
     */
    public void setInDepName(String inDepName) {
        this.inDepName = inDepName == null ? null : inDepName.trim();
    }

    /**
     * 境外托管机构国别
     * @return OUT_DEP_CTRY 境外托管机构国别
     */
    public String getOutDepCtry() {
        return outDepCtry;
    }

    /**
     * 境外托管机构国别
     * @param outDepCtry 境外托管机构国别
     */
    public void setOutDepCtry(String outDepCtry) {
        this.outDepCtry = outDepCtry == null ? null : outDepCtry.trim();
    }

    /**
     * 境外托管机构名称
     * @return OUT_DEP_NAME 境外托管机构名称
     */
    public String getOutDepName() {
        return outDepName;
    }

    /**
     * 境外托管机构名称
     * @param outDepName 境外托管机构名称
     */
    public void setOutDepName(String outDepName) {
        this.outDepName = outDepName == null ? null : outDepName.trim();
    }

    /**
     * 发行机构提前终止权标识
     * @return TERM_RIGHT_FLAG 发行机构提前终止权标识
     */
    public String getTermRightFlag() {
        return termRightFlag;
    }

    /**
     * 发行机构提前终止权标识
     * @param termRightFlag 发行机构提前终止权标识
     */
    public void setTermRightFlag(String termRightFlag) {
        this.termRightFlag = termRightFlag == null ? null : termRightFlag.trim();
    }

    /**
     * 客户赎回权标识
     * @return CUS_REDEEM_FLAG 客户赎回权标识
     */
    public String getCusRedeemFlag() {
        return cusRedeemFlag;
    }

    /**
     * 客户赎回权标识
     * @param cusRedeemFlag 客户赎回权标识
     */
    public void setCusRedeemFlag(String cusRedeemFlag) {
        this.cusRedeemFlag = cusRedeemFlag == null ? null : cusRedeemFlag.trim();
    }

    /**
     * 产品增信标识
     * @return CREDIT_FLAG 产品增信标识
     */
    public String getCreditFlag() {
        return creditFlag;
    }

    /**
     * 产品增信标识
     * @param creditFlag 产品增信标识
     */
    public void setCreditFlag(String creditFlag) {
        this.creditFlag = creditFlag == null ? null : creditFlag.trim();
    }

    /**
     * 产品增信机构类型
     * @return CREDIT_TYPE 产品增信机构类型
     */
    public String getCreditType() {
        return creditType;
    }

    /**
     * 产品增信机构类型
     * @param creditType 产品增信机构类型
     */
    public void setCreditType(String creditType) {
        this.creditType = creditType == null ? null : creditType.trim();
    }

    /**
     * 产品定价方式
     * @return PRICED_MODE 产品定价方式
     */
    public String getPricedMode() {
        return pricedMode;
    }

    /**
     * 产品定价方式
     * @param pricedMode 产品定价方式
     */
    public void setPricedMode(String pricedMode) {
        this.pricedMode = pricedMode == null ? null : pricedMode.trim();
    }

    /**
     * 产品起始日期
     * @return PRD_VDATE 产品起始日期
     */
    public Date getPrdVdate() {
        return prdVdate;
    }

    /**
     * 产品起始日期
     * @param prdVdate 产品起始日期
     */
    public void setPrdVdate(Date prdVdate) {
        this.prdVdate = prdVdate;
    }

    /**
     * 产品终止日期
     * @return PRD_MDATE 产品终止日期
     */
    public Date getPrdMdate() {
        return prdMdate;
    }

    /**
     * 产品终止日期
     * @param prdMdate 产品终止日期
     */
    public void setPrdMdate(Date prdMdate) {
        this.prdMdate = prdMdate;
    }

    /**
     * 平均预计客户年收益率
     * @return EXP_AVG_YIELD 平均预计客户年收益率
     */
    public BigDecimal getExpAvgYield() {
        return expAvgYield;
    }

    /**
     * 平均预计客户年收益率
     * @param expAvgYield 平均预计客户年收益率
     */
    public void setExpAvgYield(BigDecimal expAvgYield) {
        this.expAvgYield = expAvgYield;
    }

    /**
     * 是否结构化分层产品
     * @return IS_CONSTRACTE 是否结构化分层产品
     */
    public String getIsConstracte() {
        return isConstracte;
    }

    /**
     * 是否结构化分层产品
     * @param isConstracte 是否结构化分层产品
     */
    public void setIsConstracte(String isConstracte) {
        this.isConstracte = isConstracte == null ? null : isConstracte.trim();
    }

    /**
     * 层级类别  11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五  
     * @return GRADE_TYPE 层级类别  11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五  
     */
    public String getGradeType() {
        return gradeType;
    }

    /**
     * 层级类别  11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五  
     * @param gradeType 层级类别  11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五  
     */
    public void setGradeType(String gradeType) {
        this.gradeType = gradeType == null ? null : gradeType.trim();
    }

    /**
     * 收益率分档情况说明
     * @return YIELD_COMMENT 收益率分档情况说明
     */
    public String getYieldComment() {
        return yieldComment;
    }

    /**
     * 收益率分档情况说明
     * @param yieldComment 收益率分档情况说明
     */
    public void setYieldComment(String yieldComment) {
        this.yieldComment = yieldComment == null ? null : yieldComment.trim();
    }

    /**
     *   开放模式01 有规律开放/02 无规律开放
     * @return OPEN_MODE   开放模式01 有规律开放/02 无规律开放
     */
    public String getOpenMode() {
        return openMode;
    }

    /**
     *   开放模式01 有规律开放/02 无规律开放
     * @param openMode   开放模式01 有规律开放/02 无规律开放
     */
    public void setOpenMode(String openMode) {
        this.openMode = openMode == null ? null : openMode.trim();
    }

    /**
     * 规律开放周期
     * @return OPEN_CIRCLE 规律开放周期
     */
    public String getOpenCircle() {
        return openCircle;
    }

    /**
     * 规律开放周期
     * @param openCircle 规律开放周期
     */
    public void setOpenCircle(String openCircle) {
        this.openCircle = openCircle == null ? null : openCircle.trim();
    }

    /**
     * 其他规律开放周期（天）
     * @return OPEN_CIRCLE_DAYS 其他规律开放周期（天）
     */
    public BigDecimal getOpenCircleDays() {
        return openCircleDays;
    }

    /**
     * 其他规律开放周期（天）
     * @param openCircleDays 其他规律开放周期（天）
     */
    public void setOpenCircleDays(BigDecimal openCircleDays) {
        this.openCircleDays = openCircleDays;
    }

    /**
     * 无规律开放说明
     * @return RULE_COMMENT 无规律开放说明
     */
    public String getRuleComment() {
        return ruleComment;
    }

    /**
     * 无规律开放说明
     * @param ruleComment 无规律开放说明
     */
    public void setRuleComment(String ruleComment) {
        this.ruleComment = ruleComment == null ? null : ruleComment.trim();
    }

    /**
     * 首次开放周期起始日
     * @return FIRST_OPEN_DAY 首次开放周期起始日
     */
    public Date getFirstOpenDay() {
        return firstOpenDay;
    }

    /**
     * 首次开放周期起始日
     * @param firstOpenDay 首次开放周期起始日
     */
    public void setFirstOpenDay(Date firstOpenDay) {
        this.firstOpenDay = firstOpenDay;
    }

    /**
     * 节假日是否开放
     * @return HOLIDAY_ON_FLAG 节假日是否开放
     */
    public String getHolidayOnFlag() {
        return holidayOnFlag;
    }

    /**
     * 节假日是否开放
     * @param holidayOnFlag 节假日是否开放
     */
    public void setHolidayOnFlag(String holidayOnFlag) {
        this.holidayOnFlag = holidayOnFlag == null ? null : holidayOnFlag.trim();
    }

    /**
     * 平均开放次数（年化）
     * @return AVG_OPEN_COUNT 平均开放次数（年化）
     */
    public BigDecimal getAvgOpenCount() {
        return avgOpenCount;
    }

    /**
     * 平均开放次数（年化）
     * @param avgOpenCount 平均开放次数（年化）
     */
    public void setAvgOpenCount(BigDecimal avgOpenCount) {
        this.avgOpenCount = avgOpenCount;
    }

    /**
     * 开放期业务01申赎皆可/02 仅可申购无限制/ 03仅可赎回无限制 /04仅可申购有限制/05仅可赎回有限制 /99其他
     * @return OPEN_BUSS 开放期业务01申赎皆可/02 仅可申购无限制/ 03仅可赎回无限制 /04仅可申购有限制/05仅可赎回有限制 /99其他
     */
    public String getOpenBuss() {
        return openBuss;
    }

    /**
     * 开放期业务01申赎皆可/02 仅可申购无限制/ 03仅可赎回无限制 /04仅可申购有限制/05仅可赎回有限制 /99其他
     * @param openBuss 开放期业务01申赎皆可/02 仅可申购无限制/ 03仅可赎回无限制 /04仅可申购有限制/05仅可赎回有限制 /99其他
     */
    public void setOpenBuss(String openBuss) {
        this.openBuss = openBuss == null ? null : openBuss.trim();
    }

    /**
     * 预计最高收益率-发行
     * @return HIGH_YIELD_ISSUE 预计最高收益率-发行
     */
    public BigDecimal getHighYieldIssue() {
        return highYieldIssue;
    }

    /**
     * 预计最高收益率-发行
     * @param highYieldIssue 预计最高收益率-发行
     */
    public void setHighYieldIssue(BigDecimal highYieldIssue) {
        this.highYieldIssue = highYieldIssue;
    }

    /**
     * 预计最低收益率-发行
     * @return LOW_YIELD_ISSUE 预计最低收益率-发行
     */
    public BigDecimal getLowYieldIssue() {
        return lowYieldIssue;
    }

    /**
     * 预计最低收益率-发行
     * @param lowYieldIssue 预计最低收益率-发行
     */
    public void setLowYieldIssue(BigDecimal lowYieldIssue) {
        this.lowYieldIssue = lowYieldIssue;
    }

    /**
     * 产品增信形式
     * @return CREDIT_MODE 产品增信形式
     */
    public String getCreditMode() {
        return creditMode;
    }

    /**
     * 产品增信形式
     * @param creditMode 产品增信形式
     */
    public void setCreditMode(String creditMode) {
        this.creditMode = creditMode == null ? null : creditMode.trim();
    }

    /**
     * 发行机构代码
     * @return ISSUE_PARTY_ID 发行机构代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 发行机构代码
     * @param issuePartyId 发行机构代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 开放模式申
     * @return OPENTYPEP 开放模式申
     */
    public String getOpentypep() {
        return opentypep;
    }

    /**
     * 开放模式申
     * @param opentypep 开放模式申
     */
    public void setOpentypep(String opentypep) {
        this.opentypep = opentypep == null ? null : opentypep.trim();
    }

    /**
     * 开放天数申
     * @return OPENDAYP 开放天数申
     */
    public String getOpendayp() {
        return opendayp;
    }

    /**
     * 开放天数申
     * @param opendayp 开放天数申
     */
    public void setOpendayp(String opendayp) {
        this.opendayp = opendayp == null ? null : opendayp.trim();
    }

    /**
     * 开放周期申
     * @return OPENCYCLEP 开放周期申
     */
    public String getOpencyclep() {
        return opencyclep;
    }

    /**
     * 开放周期申
     * @param opencyclep 开放周期申
     */
    public void setOpencyclep(String opencyclep) {
        this.opencyclep = opencyclep == null ? null : opencyclep.trim();
    }

    /**
     * 开放周期单位
     * @return OPENCYCLEUNITP 开放周期单位
     */
    public String getOpencycleunitp() {
        return opencycleunitp;
    }

    /**
     * 开放周期单位
     * @param opencycleunitp 开放周期单位
     */
    public void setOpencycleunitp(String opencycleunitp) {
        this.opencycleunitp = opencycleunitp == null ? null : opencycleunitp.trim();
    }

    /**
     * 周申
     * @return WEEKP 周申
     */
    public String getWeekp() {
        return weekp;
    }

    /**
     * 周申
     * @param weekp 周申
     */
    public void setWeekp(String weekp) {
        this.weekp = weekp == null ? null : weekp.trim();
    }

    /**
     * 几号申
     * @return DAYP 几号申
     */
    public String getDayp() {
        return dayp;
    }

    /**
     * 几号申
     * @param dayp 几号申
     */
    public void setDayp(String dayp) {
        this.dayp = dayp == null ? null : dayp.trim();
    }

    /**
     * 第几工作日申
     * @return WORKDAYP 第几工作日申
     */
    public String getWorkdayp() {
        return workdayp;
    }

    /**
     * 第几工作日申
     * @param workdayp 第几工作日申
     */
    public void setWorkdayp(String workdayp) {
        this.workdayp = workdayp == null ? null : workdayp.trim();
    }

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 部门代码
     * @return DEPT_CODE 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 工作流代码
     * @return WORKFLOW_ID 工作流代码
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 工作流代码
     * @param workflowId 工作流代码
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 数据来源
     * @return DATA_SOURCE 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 数据来源
     * @param dataSource 数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    /**
     * 募集方式
     * @return RAISE_TYPE 募集方式
     */
    public String getRaiseType() {
        return raiseType;
    }

    /**
     * 募集方式
     * @param raiseType 募集方式
     */
    public void setRaiseType(String raiseType) {
        this.raiseType = raiseType == null ? null : raiseType.trim();
    }

    /**
     * 管理方式
     * @return MANAGE_TYPE 管理方式
     */
    public String getManageType() {
        return manageType;
    }

    /**
     * 管理方式
     * @param manageType 管理方式
     */
    public void setManageType(String manageType) {
        this.manageType = manageType == null ? null : manageType.trim();
    }

    /**
     * 更新人
     * @return UPDATE_USER 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 更新时间
     * @return UPDATE_TIME 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 创建人
     * @return CREATE_USER 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 经办人
     * @return AGENT 经办人
     */
    public String getAgent() {
        return agent;
    }

    /**
     * 经办人
     * @param agent 经办人
     */
    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

	public String getFinprodtype2() {
		return finprodtype2;
	}

	public void setFinprodtype2(String finprodtype2) {
		this.finprodtype2 = finprodtype2;
	}

	public String getFinprodid() {
		return finprodid;
	}

	public void setFinprodid(String finprodid) {
		this.finprodid = finprodid;
	}

	public String getFiduciaryDuty() {
		return fiduciaryDuty;
	}

	public void setFiduciaryDuty(String fiduciaryDuty) {
		this.fiduciaryDuty = fiduciaryDuty;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}


	public String getExcFinSector() {
		return excFinSector;
	}
	public void setExcFinSector(String excFinSector) {
		this.excFinSector = excFinSector;
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
	public BigDecimal getYield() {
		return yield;
	}
	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}
	public BigDecimal getInvestFeeRate() {
		return investFeeRate;
	}
	public void setInvestFeeRate(BigDecimal investFeeRate) {
		this.investFeeRate = investFeeRate;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType == null ? null : customerType.trim();
	}
	/**
     * 取得 业绩比较基准下限
     * @return 业绩比较基准下限
     */
    public BigDecimal getYieldLow() {
        return yieldLow;
    }

    /**
     * 设置 业绩比较基准下限
     * @param yieldLow 业绩比较基准下限
     */
    public void setYieldLow(BigDecimal yieldLow) {
        this.yieldLow = yieldLow;
    }

    /**
     * 取得 业绩比较基准上限
     * @return 业绩比较基准上限
     */
    public BigDecimal getYieldHigh() {
        return yieldHigh;
    }

    /**
     * 设置 业绩比较基准上限
     * @param yieldHigh 业绩比较基准上限
     */
    public void setYieldHigh(BigDecimal yieldHigh) {
        this.yieldHigh = yieldHigh;
    }

    /**
     * 取得 业绩比较基准说明
     * @return 业绩比较基准说明
     */
    public String getYieldRemark() {
        return yieldRemark;
    }

    /**
     * 设置 业绩比较基准说明
     * @param yieldRemark 业绩比较基准说明
     */
    public void setYieldRemark(String yieldRemark) {
        this.yieldRemark = yieldRemark == null ? null : yieldRemark.trim();
    }

    /**
     * 取得 资金托管账号
     * @return 资金托管账号
     */
    public String getBusiAcctNo() {
        return busiAcctNo;
    }

    /**
     * 设置 资金托管账号
     * @param busiAcctNo 资金托管账号
     */
    public void setBusiAcctNo(String busiAcctNo) {
        this.busiAcctNo = busiAcctNo == null ? null : busiAcctNo.trim();
    }

    /**
     * 取得 资金托管账户
     * @return 资金托管账户
     */
    public String getAcctName() {
        return acctName;
    }

    /**
     * 设置 资金托管账户
     * @param acctName 资金托管账户
     */
    public void setAcctName(String acctName) {
        this.acctName = acctName == null ? null : acctName.trim();
    }

    public String getZjxtFlag() {
        return zjxtFlag;
    }

    public void setZjxtFlag(String zjxtFlag) {
        this.zjxtFlag = zjxtFlag;
    }

    public String getManaCateFit() {
        return manaCateFit;
    }

    public void setManaCateFit(String manaCateFit) {
        this.manaCateFit = manaCateFit;
    }

    public String getIssueStatusRemark() {
        return issueStatusRemark;
    }

    public void setIssueStatusRemark(String issueStatusRemark) {
        this.issueStatusRemark = issueStatusRemark;
    }

    public BigDecimal getFitCircleDays() {
        return fitCircleDays;
    }

    public void setFitCircleDays(BigDecimal fitCircleDays) {
        this.fitCircleDays = fitCircleDays;
    }
}