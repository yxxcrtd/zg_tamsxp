package com.joyintech.tams.svreport.chb.assetinfodetail.dto;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.ChbAssetInfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.*;
import com.joyintech.tams.svreport.chb.assetinfodetail.validator.AssetInfoDetailValidator;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import com.joyintech.workflow.engine.dto.ProcessEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 描述:CHB_ASSET_INFO_SUB表的实体类,用于前台分类展示
 * DateUtil
 * @version
 * @author: Administrator
 * @创建时间: 2019-01-21
 */
@TableName("CHB_ASSET_INFO")
@PageId("AssetInfoRegist002")
@ValidLogic(targetClass = AssetInfoDetailValidator.class, groups = { ValidateGroupEdit.class,
		ValidateGroupDelete.class })
public class AssetInfoDetailDto extends ChbAssetInfo implements ITableRow,ProcessEntity {
	
	
	private  String dataStatus;

	private String vDateStr;

	private String mDateStr;

	private String  errMsg;

	/**
	 * 操作类型
	 */
	private String operateType;

	/**
	 * 要素id
	 */
	@PrimaryKey(Field.PK_1)
	private String elementId;

	/**
	 * 目的和意义以及可能会用到的地方
	 */
	private String pageModel;

	/**
	 * 选择数据
	 */
	private List<AssetInfoDetailDto> selectedItem;
	private String amt01;
	private String vdate01;
	private String mdate01;
	private String assetYield01;
	private String basis01;

	private String assetId03;
	private String assetName03;
	private String industryLevel203;

	private String vdate02;
	private String mdate02;
	private String rivalParty02;
	private String assetYield02;
	private String basis02;

	private String isResale04;
	private String assetName04;
	private String amt04;
	private String vdate04;
	private String mdate04;
	private String rateFlag04;
	private String assetYield04;
	private String intpayCycle04;
	private String basis04;
	private String financie04;
	private String fcrGradeOut04;
	private String financierType104;
	private String financierType204;
	private String financierType304;
	private String financeProj04;
	private String industryLevel2Type04;
	private String industryFlag04;
	private String monitType04;
	private String monTypeComm04;
	private String guaType04;
	private String pledgeType04;
	private String pledgeValue04;
	private String natureGuarantee04;
	private String relationFinan04;
	private String guaTypeRemark04;
	private String outGrade04;
	private String finOrgCode04;
	private String proIndustryLevel204;

	private String isResale05;
	private String amt05;
	private String vdate05;
	private String mdate05;
	private String industryShares205;
	private String assetYield05;

	private String assetId06;
	private String assetName06;
	private String industryShares206;
	private String investStage06;
	private String sharesType106;
	private String sharesType306;
	private String sharesType206;
	private String deptEquity06;

	private String assetName07;

	private String countryArea08;
	private String assetName08;
	private String assetId08;
	private String issuePartyName08;
	private String industryLevel208;
	private String vdate08;
	private String mdate08;
	private String assetYield08;
	private String intpayCycle08;
	private String guaTypeRemark08;

	private String countryArea09;
	private String vdate09;
	private String mdate09;
	private String rivalParty09;
	private String assetYield09;
	private String basis09;

	private String countryArea10;
	private String assetId10;
	private String assetName10;
	private String issuePartyName10;
	private String industryShares210;

	private String countryArea11;
	private String vdate11;
	private String mdate11;
	private String assetYield11;
	private String intpayCycle11;

	private String assetName12;
	private String financialComissue12;
	private String managerId12;
	private String trusteeId12;
	private String amt12;
	private String truActType12;
	private String investType12;
	private String capUseInduLevel212;
	private String planSdate12;
	private String planEdate12;
	private String proProperty12;
	private String rateFlag12;
	private String maxRate12;
	private String minRate12;
	private String mgrFeeRate12;
	private String truFeeRate12;
	private String tradeFeeRate12;
	private String agencyFeeRate12;
	private String otherFeeRate12;

	private String countryArea13;
	private String assetName13;
	private String termDate13;
	private String assetValue13;
	private String assetYield13;

	private String assetName14;
	private String amt14;
	private String vdate14;
	private String mdate14;
	private String countryArea14;
	private String rateFlag14;
	private String assetYield14;
	private String intpayCycle14;
	private String financie14;
	private String finOrgCode14;
	private String fcrGradeOut14;
	private String financierType114;
	private String financierType214;
	private String financierType314;
	private String financeProj14;
	private String industryLevel2Type14;
	private String proIndustryLevel214;
	private String industryFlag14;
	private String monitType14;
	private String monTypeComm14;
	private String outGrade14;
	private String guaType14;
	private String pledgeType14;
	private String pledgeValue14;
	private String natureGuarantee14;
	private String relationFinan14;
	private String guaTypeRemark14;
	private String deptEquity14;

	private String countryArea18;
	private String assetName18;
	private String vdate18;
	private String mdate18;
	private String assetValue18;
	private String assetYield18;

	private String assetIdL01;
	private String assetNameL01;
	private String industryShares2L01;
	private String financialComissueL01;
	private String investStageL01;
	private String sharesType1L01;
	private String sharesType3L01;
	private String sharesType2L01;

	private String assetName16;
	private String assetId16;
	private String managerId16;
	private String trusteeId16;
	private String amt16;
	private String truActType16;
	private String investType16;
	private String capUseInduLevel216;
	private String planSdate16;
	private String planEdate16;
	private String proProperty16;
	private String rateFlag16;
	private String maxRate16;
	private String minRate16;
	private String mgrFeeRate16;
	private String truFeeRate16;
	private String tradeFeeRate16;
	private String agencyFeeRate16;
	private String otherFeeRate16;

	private String countryArea17;
	private String assetName17;
	private String termDate17;
	private String assetYield17;

	private String orgData;

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getvDateStr() {
		return vDateStr;
	}

	public void setvDateStr(String vDateStr) {
		this.vDateStr = vDateStr;
	}

	public String getmDateStr() {
		return mDateStr;
	}

	public void setmDateStr(String mDateStr) {
		this.mDateStr = mDateStr;
	}

	private SvrBussFinBondRepInfoDto svrBussFinBondRepInfoDto;

	private SvrBussRepDepositInfoDto svrBussRepDepositInfoDto;

	private SvrBussRepDlRepoInfoDto svrBussRepDlRepoInfoDto;

	private SvrBussRepPlacementInfoDto svrBussRepPlacementInfoDto;

	private  SvrBussQDIISharesInfoDto    svrBussQDIISharesInfoDto;

	private SvrBussRepAssetAstBillInfoDto svrBussRepAssetAstBillInfoDto;

	private SvrBussRepAssetCreditNonstdInfoDto svrBussRepAssetCreditNonstdInfoDto;

    private  SvrBussRepAssetMngProdInfoDto  svrBussRepAssetMngProdInfoDto;

    private  SvrBussRepEquityInfoDto   svrBussRepEquityInfoDto;

	private  SvrBussRepFinanciallInfoDto   svrBussRepFinanciallInfoDto;

	private  SvrBussRepFundInfoDto   svrBussRepFundInfoDto;

	private  SvrBussRepNobleMetalInfoDto   svrBussRepNobleMetalInfoDto;

	private  SvrBussRepOtherInfoDto   svrBussRepOtherInfoDto;

	private  SvrBussRepOtherInvestTargetInfoDto   svrBussRepOtherInvestTargetInfoDto;

	private  SvrBussRepOthKindInfoDto   svrBussRepOthKindInfoDto;

	private  SvrBussRepOutInvestAgrInfoDto   svrBussRepOutInvestAgrInfoDto;

	private  SvrBussRepQDIIBondInfoDto   svrBussRepQDIIBondInfoDto;

	private  SvrBussRepQDIIdemolitionInfoDto   svrBussRepQDIIdemolitionInfoDto;

	private  SvrBussRepQDIIStructureInfoDto   svrBussRepQDIIStructureInfoDto;


	public SvrBussQDIISharesInfoDto getSvrBussQDIISharesInfoDto() {
		return svrBussQDIISharesInfoDto;
	}

	public void setSvrBussQDIISharesInfoDto(SvrBussQDIISharesInfoDto svrBussQDIISharesInfoDto) {
		this.svrBussQDIISharesInfoDto = svrBussQDIISharesInfoDto;
	}

	public SvrBussRepAssetAstBillInfoDto getSvrBussRepAssetAstBillInfoDto() {
		return svrBussRepAssetAstBillInfoDto;
	}

	public void setSvrBussRepAssetAstBillInfoDto(SvrBussRepAssetAstBillInfoDto svrBussRepAssetAstBillInfoDto) {
		this.svrBussRepAssetAstBillInfoDto = svrBussRepAssetAstBillInfoDto;
	}

	public SvrBussRepAssetCreditNonstdInfoDto getSvrBussRepAssetCreditNonstdInfoDto() {
		return svrBussRepAssetCreditNonstdInfoDto;
	}

	public void setSvrBussRepAssetCreditNonstdInfoDto(SvrBussRepAssetCreditNonstdInfoDto svrBussRepAssetCreditNonstdInfoDto) {
		this.svrBussRepAssetCreditNonstdInfoDto = svrBussRepAssetCreditNonstdInfoDto;
	}

	public SvrBussRepAssetMngProdInfoDto getSvrBussRepAssetMngProdInfoDto() {
		return svrBussRepAssetMngProdInfoDto;
	}

	public void setSvrBussRepAssetMngProdInfoDto(SvrBussRepAssetMngProdInfoDto svrBussRepAssetMngProdInfoDto) {
		this.svrBussRepAssetMngProdInfoDto = svrBussRepAssetMngProdInfoDto;
	}

	public SvrBussRepEquityInfoDto getSvrBussRepEquityInfoDto() {
		return svrBussRepEquityInfoDto;
	}

	public void setSvrBussRepEquityInfoDto(SvrBussRepEquityInfoDto svrBussRepEquityInfoDto) {
		this.svrBussRepEquityInfoDto = svrBussRepEquityInfoDto;
	}

	public SvrBussRepFinanciallInfoDto getSvrBussRepFinanciallInfoDto() {
		return svrBussRepFinanciallInfoDto;
	}

	public void setSvrBussRepFinanciallInfoDto(SvrBussRepFinanciallInfoDto svrBussRepFinanciallInfoDto) {
		this.svrBussRepFinanciallInfoDto = svrBussRepFinanciallInfoDto;
	}

	public SvrBussRepFundInfoDto getSvrBussRepFundInfoDto() {
		return svrBussRepFundInfoDto;
	}

	public void setSvrBussRepFundInfoDto(SvrBussRepFundInfoDto svrBussRepFundInfoDto) {
		this.svrBussRepFundInfoDto = svrBussRepFundInfoDto;
	}

	public SvrBussRepNobleMetalInfoDto getSvrBussRepNobleMetalInfoDto() {
		return svrBussRepNobleMetalInfoDto;
	}

	public void setSvrBussRepNobleMetalInfoDto(SvrBussRepNobleMetalInfoDto svrBussRepNobleMetalInfoDto) {
		this.svrBussRepNobleMetalInfoDto = svrBussRepNobleMetalInfoDto;
	}

	public SvrBussRepOtherInfoDto getSvrBussRepOtherInfoDto() {
		return svrBussRepOtherInfoDto;
	}

	public void setSvrBussRepOtherInfoDto(SvrBussRepOtherInfoDto svrBussRepOtherInfoDto) {
		this.svrBussRepOtherInfoDto = svrBussRepOtherInfoDto;
	}

	public SvrBussRepOtherInvestTargetInfoDto getSvrBussRepOtherInvestTargetInfoDto() {
		return svrBussRepOtherInvestTargetInfoDto;
	}

	public void setSvrBussRepOtherInvestTargetInfoDto(SvrBussRepOtherInvestTargetInfoDto svrBussRepOtherInvestTargetInfoDto) {
		this.svrBussRepOtherInvestTargetInfoDto = svrBussRepOtherInvestTargetInfoDto;
	}

	public SvrBussRepOthKindInfoDto getSvrBussRepOthKindInfoDto() {
		return svrBussRepOthKindInfoDto;
	}

	public void setSvrBussRepOthKindInfoDto(SvrBussRepOthKindInfoDto svrBussRepOthKindInfoDto) {
		this.svrBussRepOthKindInfoDto = svrBussRepOthKindInfoDto;
	}

	public SvrBussRepOutInvestAgrInfoDto getSvrBussRepOutInvestAgrInfoDto() {
		return svrBussRepOutInvestAgrInfoDto;
	}

	public void setSvrBussRepOutInvestAgrInfoDto(SvrBussRepOutInvestAgrInfoDto svrBussRepOutInvestAgrInfoDto) {
		this.svrBussRepOutInvestAgrInfoDto = svrBussRepOutInvestAgrInfoDto;
	}

	public SvrBussRepQDIIBondInfoDto getSvrBussRepQDIIBondInfoDto() {
		return svrBussRepQDIIBondInfoDto;
	}

	public void setSvrBussRepQDIIBondInfoDto(SvrBussRepQDIIBondInfoDto svrBussRepQDIIBondInfoDto) {
		this.svrBussRepQDIIBondInfoDto = svrBussRepQDIIBondInfoDto;
	}

	public SvrBussRepQDIIdemolitionInfoDto getSvrBussRepQDIIdemolitionInfoDto() {
		return svrBussRepQDIIdemolitionInfoDto;
	}

	public void setSvrBussRepQDIIdemolitionInfoDto(SvrBussRepQDIIdemolitionInfoDto svrBussRepQDIIdemolitionInfoDto) {
		this.svrBussRepQDIIdemolitionInfoDto = svrBussRepQDIIdemolitionInfoDto;
	}

	public SvrBussRepQDIIStructureInfoDto getSvrBussRepQDIIStructureInfoDto() {
		return svrBussRepQDIIStructureInfoDto;
	}

	public void setSvrBussRepQDIIStructureInfoDto(SvrBussRepQDIIStructureInfoDto svrBussRepQDIIStructureInfoDto) {
		this.svrBussRepQDIIStructureInfoDto = svrBussRepQDIIStructureInfoDto;
	}

	public SvrBussFinBondRepInfoDto getSvrBussFinBondRepInfoDto() {
		return svrBussFinBondRepInfoDto;
	}

	public void setSvrBussFinBondRepInfoDto(SvrBussFinBondRepInfoDto svrBussFinBondRepInfoDto) {
		this.svrBussFinBondRepInfoDto = svrBussFinBondRepInfoDto;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getDataStatusStr() {
		if(StringUtil.isNotEmpty(this.getDataStatus())){
			return this.getDataStatus() + " " + SysDataUtil.getNameFromDataDict(DictCode.中债数据状态, this.getDataStatus());
		}
		return this.getDataStatus();
	}


	public SvrBussRepDepositInfoDto getSvrBussRepDepositInfoDto() {
		return svrBussRepDepositInfoDto;
	}

	public void setSvrBussRepDepositInfoDto(SvrBussRepDepositInfoDto svrBussRepDepositInfoDto) {
		this.svrBussRepDepositInfoDto = svrBussRepDepositInfoDto;
	}

	public SvrBussRepDlRepoInfoDto getSvrBussRepDlRepoInfoDto() {
		return svrBussRepDlRepoInfoDto;
	}

	public void setSvrBussRepDlRepoInfoDto(SvrBussRepDlRepoInfoDto svrBussRepDlRepoInfoDto) {
		this.svrBussRepDlRepoInfoDto = svrBussRepDlRepoInfoDto;
	}

	/**
	 * 资产负债类别
	 * @return
	 */
	public String getAssetTypeStr() {
		if(StringUtil.isNotEmpty(this.getAssetType())) {
			return this.getAssetType() + " " + SysDataUtil.getNameFromDataDict(DictCode.资产负债一级类别, this.getAssetType());
		}
		return this.getAssetType();
	}
	/**
	 * 资产_负债二级类别
	 * @return
	 */
	public String getAssetType2Str() {
		if(StringUtil.isNotEmpty(this.getAssetType2())) {
			return this.getAssetType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.资产_负债二级类别, this.getAssetType2());
		}
		return this.getAssetType2();
	}
	/**
	 * 资产一级类型
	 * @return
	 */
	public String getOrgAssetType1Str() {
		if(StringUtil.isNotEmpty(this.getOrgAssetType1())) {
			return this.getOrgAssetType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.中债_上游资产一级分类, this.getOrgAssetType1());
		}
		return this.getOrgAssetType1();
	}
	/**
	 * 资产二级类型
	 * @return
	 */
	public String getOrgAssetType2Str() {
		if(StringUtil.isNotEmpty(this.getOrgAssetType2())) {
			return this.getOrgAssetType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.中债_上游资产二级分类, this.getOrgAssetType2());
		}
		return this.getOrgAssetType2();
	}


//	/**
//	 * 资产二级类型
//	 * @return
//	 */
//	public String getStatusStr() {
//		return SysDataUtil.getNameFromDataDict(DictCode.中债对应上游资产二级类型, this.getOrgAssetType2());
//	}


	public SvrBussRepPlacementInfoDto getSvrBussRepPlacementInfoDto() {
		return svrBussRepPlacementInfoDto;
	}

	public void setSvrBussRepPlacementInfoDto(SvrBussRepPlacementInfoDto svrBussRepPlacementInfoDto) {
		this.svrBussRepPlacementInfoDto = svrBussRepPlacementInfoDto;
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

	public List<AssetInfoDetailDto> getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(List<AssetInfoDetailDto> selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getPageModel() {
		return pageModel;
	}

	public void setPageModel(String pageModel) {
		this.pageModel = pageModel;
	}
	public String getvdateStr(){
		return null!=getVdate()?DateUtil.format(getVdate()):null;
	}
	public String getmdateStr(){
		return null!=getMdate()?DateUtil.format(getMdate()):null;
	}

	public String getFmdateStr(){
		return null!=getFmdate()?DateUtil.format(getFmdate()):null;
	}
	public String getExerciseDateStr(){
		return null!=getExerciseDate()?DateUtil.format(getExerciseDate()):null;
	}
	public String getFirstExerciseDateStr(){
		return null!=getFirstExerciseDate()?DateUtil.format(getFirstExerciseDate()):null;
	}
	public String getFirstReprceDateStr(){
		return null!=getFirstReprceDate()?DateUtil.format(getFirstReprceDate()):null;
	}

	public String getStatusStr() {
		if(StringUtil.isNotEmpty(this.getStatus())) {
			return this.getStatus() + " " + SysDataUtil.getNameFromDataDict(DictCode.中债数据状态, getStatus());
		}
		return this.getStatus();
	}

	// updateFlg 02 不展示  01 显示
	public String getUpdateFlg() {
		if(!StringUtil.isEmpty(dataStatus) && StringUtil.equals(DictCodeListExt3.中债数据状态_删除.getValue(), dataStatus)){
			return "02";
		}else{
			return "01";
		}
	}
	public String getTradeMarketStr(){
		if(StringUtil.isNotEmpty(getTradeMarket())) {
			return getTradeMarket() + " " + SysDataUtil.getNameFromDataDict(DictCode.交易流通场所, getTradeMarket());
		}
		return getTradeMarket();
	}
	/**
	 * 资产三级类型
	 * @return
	 */
	public String getOrgAssetType3Str() {
		if(StringUtil.isNotEmpty(this.getOrgAssetType3())) {
			return this.getOrgAssetType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.中债_上游资产三级分类, this.getOrgAssetType3());
		}
		return this.getOrgAssetType3();
	}
	public String getOrgAssetType4Str() {
		if(StringUtil.isNotEmpty(this.getOrgAssetType4())) {
			return this.getOrgAssetType4() + " " + SysDataUtil.getNameFromDataDict(DictCode.中债_上游资产四级分类, this.getOrgAssetType4());
		}
		return this.getOrgAssetType4();
	}
	public String getOrgAssetType5Str() {
		if(StringUtil.isNotEmpty(this.getOrgAssetType5())) {
			return this.getOrgAssetType5() + " " + SysDataUtil.getNameFromDataDict(DictCode.中债_上游资产五级分类, this.getOrgAssetType5());
		}
		return  this.getOrgAssetType5();
	}
	public String getCcyStr() {
		if(StringUtil.isNotEmpty(this.getCcy())) {
			return this.getCcy() + " " + SysDataUtil.getNameFromDataDict(DictCode.币种, this.getCcy());
		}
		return this.getCcy();
	}
	public String getbasisStr() {
		if(StringUtil.isNotEmpty(this.getBasis())) {
			return this.getBasis() + " " + SysDataUtil.getNameFromDataDict(DictCode.计息基础_中债, this.getBasis());
		}
		return this.getBasis();
	}
	public String getDepoTypeStr() {
		if(StringUtil.isNotEmpty(this.getDepoType())) {
			return this.getDepoType() + " " + SysDataUtil.getNameFromDataDict(DictCode.存款类型, this.getDepoType());
		}
		return this.getDepoType();
	}
	public String getTargetTypeStr() {
		if(StringUtils.isNotEmpty(this.getTargetType())){
			StringBuilder sb = new StringBuilder();
			String[] targetTypes = this.getTargetType().split(",");
			Stream.of(targetTypes).forEach(e->sb.append(e + " " + SysDataUtil.getNameFromDataDict(DictCode.标的类别, e)).append(","));
			return sb.substring(0,sb.lastIndexOf(","));
		}
		return null;
	}
	public String getChbSectypeStr() {
		if(StringUtil.isNotEmpty(this.getChbSectype())) {
			return this.getChbSectype() + " " + SysDataUtil.getNameFromDataDict(DictCode.具体类别, this.getChbSectype());
		}
		return this.getChbSectype();
	}
	public String getIssueModeStr() {
		if(StringUtil.isNotEmpty(this.getIssueMode())) {
			return this.getIssueMode() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行方式, this.getIssueMode());
		}
		return this.getIssueMode();
	}
	public String getSecMainGradeStr() {
		if(StringUtil.isNotEmpty(this.getSecMainGrade())) {
			return this.getSecMainGrade() + " " + SysDataUtil.getNameFromDataDict(DictCode.主体评级_中债, this.getSecMainGrade());
		}
		return this.getSecMainGrade();
	}
	public String getPartyType1Str() {
		if(StringUtil.isNotEmpty(this.getPartyType1())) {
			return this.getPartyType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, this.getPartyType1());
		}
		return this.getPartyType1();
	}
	public String getPartyType2Str() {
		if(StringUtil.isNotEmpty(this.getPartyType2())) {
			return this.getPartyType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, this.getPartyType2());
		}
		return this.getPartyType2();
	}
	public String getPartyType3Str() {
		if(StringUtil.isNotEmpty(this.getPartyType3())) {
			return this.getPartyType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按技术领域划分, this.getPartyType3());
		}
		return this.getPartyType3();
	}
	public String getIndustryLevel2Str() {
		if(StringUtil.isNotEmpty(this.getIndustryLevel2())) {
			return this.getIndustryLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, this.getIndustryLevel2());
		}
		return this.getIndustryLevel2();
	}
	public String getRegistOrgStr() {
		if(StringUtil.isNotEmpty(this.getRegistOrg())) {
			return this.getRegistOrg() + " " + SysDataUtil.getNameFromDataDict(DictCode.登记托管机构, this.getRegistOrg());
		}
		return this.getRegistOrg();
	}
	public String getRivalPartyTypeStr() {
		if(StringUtil.isNotEmpty(this.getRivalPartyType())) {
			return this.getRivalPartyType() + " " + SysDataUtil.getNameFromDataDict(DictCode.对手方类型, this.getRivalPartyType());
		}
		return this.getRivalPartyType();
	}
	public String getTargetType1Str() {
		if(StringUtil.isNotEmpty(this.getTargetType1())) {
			return this.getTargetType1() + " " +  SysDataUtil.getNameFromDataDict(DictCode.回购标的类别, this.getTargetType1());
		}
		return this.getTargetType1();
	}
	public String getProfitTypeStr() {
		if(StringUtil.isNotEmpty(this.getProfitType())) {
			return this.getProfitType() + " " +  SysDataUtil.getNameFromDataDict(DictCode.收受益权类型, this.getProfitType());
		}
		return this.getProfitType();
	}
	public String getIsResaleStr() {
		if(StringUtil.isNotEmpty(this.getIsResale())) {
			return this.getIsResale() + " " +  SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsResale());
		}
		return this.getIsResale();
	}
	public String getRateFlagStr() {
		if(StringUtil.isNotEmpty(this.getRateFlag())) {
			return this.getRateFlag() + " " +  SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getRateFlag());
		}
		return this.getRateFlag();
	}
	public String getCouponSpeciesStr() {
		if(StringUtil.isNotEmpty(this.getCouponSpecies())) {
			return this.getCouponSpecies() + " " +  SysDataUtil.getNameFromDataDict(DictCode.计息类型, this.getCouponSpecies());
		}
		return this.getCouponSpecies();
	}
	public String getInterestRuleFlagStr() {
		if(StringUtil.isNotEmpty(this.getInterestRuleFlag())) {
			return this.getInterestRuleFlag() + " " +  SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getInterestRuleFlag());
		}
		return this.getInterestRuleFlag();
	}
	public String getIntpayRuleStr() {
		if(StringUtil.isNotEmpty(this.getIntpayRule())) {
			return this.getIntpayRule() + " " +  SysDataUtil.getNameFromDataDict(DictCode.利息分布方式, this.getIntpayRule());
		}
		return this.getIntpayRule();
	}
	public String getBasisTypeStr() {
		if(StringUtil.isNotEmpty(this.getBasisType())) {
			return this.getBasisType() + " " +  SysDataUtil.getNameFromDataDict(DictCode.基准利率, this.getBasisType());
		}
		return this.getBasisType();
	}
	public String getFloatFactorFlagStr() {
		if(StringUtil.isNotEmpty(this.getFloatFactorFlag())) {
			return this.getFloatFactorFlag() + " " +  SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getFloatFactorFlag());
		}
		return this.getFloatFactorFlag();
	}
	public String getStructGradeStr() {
		if(StringUtil.isNotEmpty(this.getStructGrade())) {
			return this.getStructGrade() + " " +  SysDataUtil.getNameFromDataDict(DictCode.结构档次, this.getStructGrade());
		}
		return this.getStructGrade();
	}
	public String getRepaymentTypeStr() {
		if(StringUtil.isNotEmpty(this.getRepaymentType())) {
			return this.getRepaymentType() + " " +  SysDataUtil.getNameFromDataDict(DictCode.还本方式, this.getRepaymentType());
		}
		return this.getRepaymentType();
	}
	public String getPeriodRepaymentFlagStr() {
		if(StringUtil.isNotEmpty(this.getPeriodRepaymentFlag())) {
			return this.getPeriodRepaymentFlag() + " " +  SysDataUtil.getNameFromDataDict(DictCode.分期还本条款标识, this.getPeriodRepaymentFlag());
		}
		return this.getPeriodRepaymentFlag();
	}
	public String getFinancierType1Str() {
		if(StringUtil.isNotEmpty(this.getFinancierType1())) {
			return this.getFinancierType1() + " " +  SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, this.getFinancierType1());
		}
		return this.getFinancierType1();
	}
	public String getFinancierType2Str() {
		if(StringUtil.isNotEmpty(this.getFinancierType2())) {
			return this.getFinancierType2() + " " +  SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, this.getFinancierType2());
		}
		return this.getFinancierType2();
	}
	public String getFinancierType3Str() {
		if(StringUtil.isNotEmpty(this.getFinancierType3())) {
			return this.getFinancierType3() + " " +  SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按技术领域划分, this.getFinancierType3());
		}
		return this.getFinancierType3();
	}
	public String getProIndustryLevel2Str() {
		if(StringUtil.isNotEmpty(this.getProIndustryLevel2())) {
			return this.getProIndustryLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, this.getProIndustryLevel2());
		}
		return this.getProIndustryLevel2();
	}
	public String getIndustryFlagStr() {
		if(StringUtil.isNotEmpty(this.getIndustryFlag())) {
			return this.getIndustryFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIndustryFlag());
		}
		return this.getIndustryFlag();
	}
	public String getMonitTypeStr() {
		if(StringUtil.isNotEmpty(this.getMonitType())) {
			return this.getMonitType() + " " + SysDataUtil.getNameFromDataDict(DictCode.重点监控行业和领域类别, this.getMonitType());
		}
		return this.getMonitType();
	}
	public String getGuaTypeStr() {
		if(StringUtil.isNotEmpty(this.getGuaType())) {
			return this.getGuaType() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保方式, this.getGuaType());
		}
		return this.getGuaType();
	}
	public String getPledgeTypeStr() {
		if(StringUtil.isNotEmpty(this.getPledgeType())) {
			return this.getPledgeType() + " " + SysDataUtil.getNameFromDataDict(DictCode.抵质押物类型, this.getPledgeType());
		}
		return this.getPledgeType();
	}
	public String getNatureGuaranteeStr() {
		if(StringUtil.isNotEmpty(this.getNatureGuarantee())) {
			return this.getNatureGuarantee() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保性质, this.getNatureGuarantee());
		}
		return this.getNatureGuarantee();
	}
	public String getRelationFinanStr() {
		if(StringUtil.isNotEmpty(this.getRelationFinan())) {
			return this.getRelationFinan() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保人与融资人关系, this.getRelationFinan());
		}
		return this.getRelationFinan();
	}
	public String getFinancierGradeStr() {
		if(StringUtil.isNotEmpty(this.getFinancierGrade())) {
			return this.getFinancierGrade() + " " + SysDataUtil.getNameFromDataDict(DictCode.资产评级, this.getFinancierGrade());
		}
		return this.getFinancierGrade();
	}
	public String getOutGradeStr() {
		if(StringUtil.isNotEmpty(this.getOutGrade())) {
			return this.getOutGrade() + " " + SysDataUtil.getNameFromDataDict(DictCode.资产评级, this.getOutGrade());
		}
		return this.getOutGrade();
	}
	public String getContainTypeStr() {
		if(StringUtil.isNotEmpty(this.getContainType())) {
			return this.getContainType() + " " + SysDataUtil.getNameFromDataDict(DictCode.含权类型, this.getContainType());
		}
		return this.getContainType();
	}
	public String getExerciseTypeStr() {
		if(StringUtil.isNotEmpty(this.getExerciseType())) {
			return this.getExerciseType() + " " + SysDataUtil.getNameFromDataDict(DictCode.资产行权方式, this.getExerciseType());
		}
		return this.getExerciseType();
	}
	public String getPerpetualClauseTypeStr() {
		if(StringUtil.isNotEmpty(this.getPerpetualClauseType())) {
			return this.getPerpetualClauseType() + " " + SysDataUtil.getNameFromDataDict(DictCode.永续条款类型, this.getPerpetualClauseType());
		}
		return this.getPerpetualClauseType();
	}
	public String getDeferredInterestTypeStr() {
		if(StringUtil.isNotEmpty(this.getDeferredInterestType())) {
			return this.getDeferredInterestType() + " " + SysDataUtil.getNameFromDataDict(DictCode.利息递延条款类型, this.getDeferredInterestType());
		}
		return this.getDeferredInterestType();
	}
	public String getDeferredInterestIntStr() {
		if(StringUtil.isNotEmpty(this.getDeferredInterestInt())) {
			return this.getDeferredInterestInt() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getDeferredInterestInt());
		}
		return this.getDeferredInterestInt();
	}
	public String getPartRedemFlagStr() {
		if(StringUtil.isNotEmpty(this.getPartRedemFlag())) {
			return this.getPartRedemFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getPartRedemFlag());
		}
		return this.getPartRedemFlag();
	}
	public String getFinAreaStr() {
		if(StringUtil.isNotEmpty(this.getFinArea())) {
			return this.getFinArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.行政区划_省市县, this.getFinArea());
		}
		return this.getFinArea();
	}
	public String getIsRightStr() {
		if(StringUtil.isNotEmpty(this.getIsRight())) {
			return this.getIsRight() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsRight());
		}
		return this.getIsRight();
	}
	public String getNotstandSecTypeStr() {
		if(StringUtil.isNotEmpty(this.getNotstandSecType())) {
			return this.getNotstandSecType() + " " + SysDataUtil.getNameFromDataDict(DictCode.票据类信用证类资产类型, this.getNotstandSecType());
		}
		return this.getNotstandSecType();
	}
	public String getIsBzhpjStr() {
		if(StringUtil.isNotEmpty(this.getIsBzhpj())) {
			return this.getIsBzhpj() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsBzhpj());
		}
		return this.getIsBzhpj();
	}
	public String getIndustryShares2Str() {
		if(StringUtil.isNotEmpty(this.getIndustryShares2())) {
			return this.getIndustryShares2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, this.getIndustryShares2());
		}
		return this.getIndustryShares2();
	}
	public String getSharesTypeStr() {
		if(StringUtil.isNotEmpty(this.getSharesType())) {
			return this.getSharesType() + " " + SysDataUtil.getNameFromDataDict(DictCode.股票类型, this.getSharesType());
		}
		return this.getSharesType();
	}
	public String getInvestStageStr() {
		if(StringUtil.isNotEmpty(this.getInvestStage())) {
			return this.getInvestStage() + " " + SysDataUtil.getNameFromDataDict(DictCode.投资阶段, this.getInvestStage());
		}
		return this.getInvestStage();
	}
	public String getSharesType1Str() {
		if(StringUtil.isNotEmpty(this.getSharesType1())) {
			return this.getSharesType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, this.getSharesType1());
		}
		return this.getSharesType1();
	}
	public String getSharesType3Str() {
		if(StringUtil.isNotEmpty(this.getSharesType3())) {
			return this.getSharesType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按技术领域划分, this.getSharesType3());
		}
		return this.getSharesType3();
	}
	public String getSharesType2Str() {
		if(StringUtil.isNotEmpty(this.getSharesType2())) {
			return this.getSharesType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, this.getSharesType2());
		}
		return this.getSharesType2();
	}
	public String getIsPledgeStr() {
		if(StringUtil.isNotEmpty(this.getIsPledge())) {
			return this.getIsPledge() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsPledge());
		}
		return this.getIsPledge();
	}
	public String getDeptEquityStr() {
		if(StringUtil.isNotEmpty(this.getDeptEquity())) {
			return this.getDeptEquity() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getDeptEquity());
		}
		return this.getDeptEquity();
	}
	public String getTargetType3Str() {
		if(StringUtil.isNotEmpty(this.getTargetType3())) {
			return this.getTargetType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.标的类别, this.getTargetType3());
		}
		return this.getTargetType3();
	}
	public String getHoldPurposeStr() {
		if(StringUtil.isNotEmpty(this.getHoldPurpose())) {
			return this.getHoldPurpose() + " " + SysDataUtil.getNameFromDataDict(DictCode.持有目的, this.getHoldPurpose());
		}
		return this.getHoldPurpose();
	}
	public String getCountryAreaStr() {
		if(StringUtil.isNotEmpty(this.getCountryArea())) {
			return this.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, this.getCountryArea());
		}
		return this.getCountryArea();
	}
	public String getPartyGradeStr() {
		if(StringUtil.isNotEmpty(this.getPartyGrade())) {
			return this.getPartyGrade() + " " + SysDataUtil.getNameFromDataDict(DictCode.资产评级, this.getPartyGrade());
		}
		return this.getPartyGrade();
	}
	public String getFinancialProductFlagStr() {
		if(StringUtil.isNotEmpty(this.getFinancialProductFlag())) {
			return this.getFinancialProductFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getFinancialProductFlag());
		}
		return this.getFinancialProductFlag();
	}
	public String getFinancialComissueStr() {
		if(StringUtil.isNotEmpty(this.getFinancialComissue())) {
			return this.getFinancialComissue() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getFinancialComissue());
		}
		return this.getFinancialComissue();
	}
	public String getTruActTypeStr() {
		if(StringUtil.isNotEmpty(this.getTruActType())) {
			return this.getTruActType() + " " + SysDataUtil.getNameFromDataDict(DictCode.资金实际投向, this.getTruActType());
		}
		return this.getTruActType();
	}
	public String getCapUseInduLevel2Str() {
		if(StringUtil.isNotEmpty(this.getCapUseInduLevel2())) {
			return this.getCapUseInduLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, this.getCapUseInduLevel2());
		}
		return this.getCapUseInduLevel2();
	}
	public String getProPropertyStr() {
		if(StringUtil.isNotEmpty(this.getProProperty())) {
			return this.getProProperty() + " " + SysDataUtil.getNameFromDataDict(DictCode.资管计划属性, this.getProProperty());
		}
		return this.getProProperty();
	}
	public String getBuyStructStr() {
		if(StringUtil.isNotEmpty(this.getBuyStruct())) {
			return this.getBuyStruct() + " " + SysDataUtil.getNameFromDataDict(DictCode.购买结构, this.getBuyStruct());
		}
		return this.getBuyStruct();
	}
	public String getManageTypeStr() {
		if(StringUtil.isNotEmpty(this.getManageType())) {
			return this.getManageType() + " " + SysDataUtil.getNameFromDataDict(DictCode.管理方式, this.getManageType());
		}
		return this.getManageType();
	}
	public String getInAssetTypeStr() {
		if(StringUtil.isNotEmpty(this.getInAssetType())) {
			return this.getInAssetType() + " " + SysDataUtil.getNameFromDataDict(DictCode.行内资产类别, this.getInAssetType());
		}
		return this.getInAssetType();
	}
	public String getRegistOrg1Str() {
		if(StringUtil.isNotEmpty(this.getRegistOrg1())) {
			return this.getRegistOrg1() + " " + SysDataUtil.getNameFromDataDict(DictCode.登记备案机构, this.getRegistOrg1());
		}
		return this.getRegistOrg1();
	}
	public String getProfitFlagStr() {
		if(StringUtil.isNotEmpty(this.getProfitFlag())) {
			return this.getProfitFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getProfitFlag());
		}
		return this.getProfitFlag();
	}
	public String getIsGovernFundStr() {
		if(StringUtil.isNotEmpty(this.getIsGovernFund())) {
			return this.getIsGovernFund() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsGovernFund());
		}
		return this.getIsGovernFund();
	}
	public String getGovernInvestFundStr() {
		if(StringUtil.isNotEmpty(this.getGovernInvestFund())) {
			return this.getGovernInvestFund() + " " + SysDataUtil.getNameFromDataDict(DictCode.政府投资基金投向, this.getGovernInvestFund());
		}
		return this.getGovernInvestFund();
	}
	public String getIsDepositFlagStr() {
		if(StringUtil.isNotEmpty(this.getIsDepositFlag())) {
			return this.getIsDepositFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsDepositFlag());
		}
		return this.getIsDepositFlag();
	}
	public String getPppflagStr() {
		if(StringUtil.isNotEmpty(this.getPppflag())) {
			return this.getPppflag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getPppflag());
		}
		return this.getPppflag();
	}
	public String getBankSecZxzqStr() {
		if(StringUtil.isNotEmpty(this.getBankSecZxzq())) {
			return this.getBankSecZxzq() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getBankSecZxzq());
		}
		return this.getBankSecZxzq();
	}
	public String getIsBankCapStr() {
		if(StringUtil.isNotEmpty(this.getIsBankCap())) {
			return this.getIsBankCap() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsBankCap());
		}
		return this.getIsBankCap();
	}
	public String getBankCapitalStr() {
		if(StringUtil.isNotEmpty(this.getBankCapital())) {
			return this.getBankCapital() + " " + SysDataUtil.getNameFromDataDict(DictCode.银行资本补充工具, this.getBankCapital());
		}
		return this.getBankCapital();
	}
	public String getBondtostockFlagStr() {
		if(StringUtil.isNotEmpty(this.getBondtostockFlag())) {
			return this.getBondtostockFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getBondtostockFlag());
		}
		return this.getBondtostockFlag();
	}
	public String getPrivatefundFlagStr() {
		if(StringUtil.isNotEmpty(this.getPrivatefundFlag())) {
			return this.getPrivatefundFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getPrivatefundFlag());
		}
		return this.getPrivatefundFlag();
	}
	public String getIsunlistedStr() {
		if(StringUtil.isNotEmpty(this.getIsunlisted())) {
			return this.getIsunlisted() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsunlisted());
		}
		return this.getIsunlisted();
	}
	public String getPrivateprdFlagStr() {
		if(StringUtil.isNotEmpty(this.getPrivateprdFlag())) {
			return this.getPrivateprdFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getPrivateprdFlag());
		}
		return this.getPrivateprdFlag();
	}
	public String getPrivateFlagStr() {
		if(StringUtil.isNotEmpty(this.getPrivateFlag())) {
			return this.getPrivateFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getPrivateFlag());
		}
		return this.getPrivateFlag();
	}
	public String getPbcAssetType1Str() {
		if(StringUtil.isNotEmpty(this.getPbcAssetType1())) {
			return this.getPbcAssetType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.人行资产一级分类, this.getPbcAssetType1());
		}
		return this.getPbcAssetType1();
	}
	public String getPbcAssetType2Str() {
		if(StringUtil.isNotEmpty(this.getPbcAssetType2())) {
			return this.getPbcAssetType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.人行资产二级分类, this.getPbcAssetType2());
		}
		return this.getPbcAssetType2();
	}
	public String getPbcAssetType3Str() {
		if(StringUtil.isNotEmpty(this.getPbcAssetType3())) {
			return this.getPbcAssetType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.人行资产三级分类, this.getPbcAssetType3());
		}
		return this.getPbcAssetType3();
	}
	public String getIsConstracteBusiStr() {
		if(StringUtil.isNotEmpty(this.getIsConstracteBusi())) {
			return this.getIsConstracteBusi() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsConstracteBusi());
		}
		return this.getIsConstracteBusi();
	}
	public String getConstracteBusiTypeStr() {
		if(StringUtil.isNotEmpty(this.getConstracteBusiType())) {
			return this.getConstracteBusiType() + " " + SysDataUtil.getNameFromDataDict(DictCode.结构化业务类型, this.getConstracteBusiType());
		}
		return this.getConstracteBusiType();
	}
	public String getConstracteGradeStr() {
		if(StringUtil.isNotEmpty(this.getConstracteGrade())) {
			return this.getConstracteGrade() + " " + SysDataUtil.getNameFromDataDict(DictCode.结构化层级, this.getConstracteGrade());
		}
		return this.getConstracteGrade();
	}
	public String getAmt01() {
		return amt01;
	}

	public void setAmt01(String amt01) {
		this.amt01 = amt01;
	}

	public String getVdate01() {
		return vdate01;
	}

	public void setVdate01(String vdate01) {
		this.vdate01 = vdate01;
	}

	public String getMdate01() {
		return mdate01;
	}

	public void setMdate01(String mdate01) {
		this.mdate01 = mdate01;
	}

	public String getAssetYield01() {
		return assetYield01;
	}

	public void setAssetYield01(String assetYield01) {
		this.assetYield01 = assetYield01;
	}

	public String getBasis01() {
		return basis01;
	}

	public void setBasis01(String basis01) {
		this.basis01 = basis01;
	}

	public String getAssetId03() {
		return assetId03;
	}

	public void setAssetId03(String assetId03) {
		this.assetId03 = assetId03;
	}

	public String getAssetName03() {
		return assetName03;
	}

	public void setAssetName03(String assetName03) {
		this.assetName03 = assetName03;
	}

	public String getIndustryLevel203() {
		return industryLevel203;
	}

	public void setIndustryLevel203(String industryLevel203) {
		this.industryLevel203 = industryLevel203;
	}

	public String getVdate02() {
		return vdate02;
	}

	public void setVdate02(String vdate02) {
		this.vdate02 = vdate02;
	}

	public String getMdate02() {
		return mdate02;
	}

	public void setMdate02(String mdate02) {
		this.mdate02 = mdate02;
	}

	public String getRivalParty02() {
		return rivalParty02;
	}

	public void setRivalParty02(String rivalParty02) {
		this.rivalParty02 = rivalParty02;
	}

	public String getAssetYield02() {
		return assetYield02;
	}

	public void setAssetYield02(String assetYield02) {
		this.assetYield02 = assetYield02;
	}

	public String getBasis02() {
		return basis02;
	}

	public void setBasis02(String basis02) {
		this.basis02 = basis02;
	}

	public String getIsResale04() {
		return isResale04;
	}

	public void setIsResale04(String isResale04) {
		this.isResale04 = isResale04;
	}

	public String getAssetName04() {
		return assetName04;
	}

	public void setAssetName04(String assetName04) {
		this.assetName04 = assetName04;
	}

	public String getAmt04() {
		return amt04;
	}

	public void setAmt04(String amt04) {
		this.amt04 = amt04;
	}

	public String getVdate04() {
		return vdate04;
	}

	public void setVdate04(String vdate04) {
		this.vdate04 = vdate04;
	}

	public String getMdate04() {
		return mdate04;
	}

	public void setMdate04(String mdate04) {
		this.mdate04 = mdate04;
	}

	public String getRateFlag04() {
		return rateFlag04;
	}

	public void setRateFlag04(String rateFlag04) {
		this.rateFlag04 = rateFlag04;
	}

	public String getAssetYield04() {
		return assetYield04;
	}

	public void setAssetYield04(String assetYield04) {
		this.assetYield04 = assetYield04;
	}

	public String getIntpayCycle04() {
		return intpayCycle04;
	}

	public void setIntpayCycle04(String intpayCycle04) {
		this.intpayCycle04 = intpayCycle04;
	}

	public String getBasis04() {
		return basis04;
	}

	public void setBasis04(String basis04) {
		this.basis04 = basis04;
	}

	public String getFinancie04() {
		return financie04;
	}

	public void setFinancie04(String financie04) {
		this.financie04 = financie04;
	}

	public String getFcrGradeOut04() {
		return fcrGradeOut04;
	}

	public void setFcrGradeOut04(String fcrGradeOut04) {
		this.fcrGradeOut04 = fcrGradeOut04;
	}

	public String getFinancierType104() {
		return financierType104;
	}

	public void setFinancierType104(String financierType104) {
		this.financierType104 = financierType104;
	}

	public String getFinancierType204() {
		return financierType204;
	}

	public void setFinancierType204(String financierType204) {
		this.financierType204 = financierType204;
	}

	public String getFinancierType304() {
		return financierType304;
	}

	public void setFinancierType304(String financierType304) {
		this.financierType304 = financierType304;
	}

	public String getFinanceProj04() {
		return financeProj04;
	}

	public void setFinanceProj04(String financeProj04) {
		this.financeProj04 = financeProj04;
	}

	public String getIndustryLevel2Type04() {
		return industryLevel2Type04;
	}

	public void setIndustryLevel2Type04(String industryLevel2Type04) {
		this.industryLevel2Type04 = industryLevel2Type04;
	}

	public String getIndustryFlag04() {
		return industryFlag04;
	}

	public void setIndustryFlag04(String industryFlag04) {
		this.industryFlag04 = industryFlag04;
	}

	public String getMonitType04() {
		return monitType04;
	}

	public void setMonitType04(String monitType04) {
		this.monitType04 = monitType04;
	}

	public String getMonTypeComm04() {
		return monTypeComm04;
	}

	public void setMonTypeComm04(String monTypeComm04) {
		this.monTypeComm04 = monTypeComm04;
	}

	public String getGuaType04() {
		return guaType04;
	}

	public void setGuaType04(String guaType04) {
		this.guaType04 = guaType04;
	}

	public String getPledgeType04() {
		return pledgeType04;
	}

	public void setPledgeType04(String pledgeType04) {
		this.pledgeType04 = pledgeType04;
	}

	public String getPledgeValue04() {
		return pledgeValue04;
	}

	public void setPledgeValue04(String pledgeValue04) {
		this.pledgeValue04 = pledgeValue04;
	}

	public String getNatureGuarantee04() {
		return natureGuarantee04;
	}

	public void setNatureGuarantee04(String natureGuarantee04) {
		this.natureGuarantee04 = natureGuarantee04;
	}

	public String getRelationFinan04() {
		return relationFinan04;
	}

	public void setRelationFinan04(String relationFinan04) {
		this.relationFinan04 = relationFinan04;
	}

	public String getGuaTypeRemark04() {
		return guaTypeRemark04;
	}

	public void setGuaTypeRemark04(String guaTypeRemark04) {
		this.guaTypeRemark04 = guaTypeRemark04;
	}

	public String getOutGrade04() {
		return outGrade04;
	}

	public void setOutGrade04(String outGrade04) {
		this.outGrade04 = outGrade04;
	}


	public String getProIndustryLevel204() {
		return proIndustryLevel204;
	}

	public void setProIndustryLevel204(String proIndustryLevel204) {
		this.proIndustryLevel204 = proIndustryLevel204;
	}

	public String getIsResale05() {
		return isResale05;
	}

	public void setIsResale05(String isResale05) {
		this.isResale05 = isResale05;
	}

	public String getAmt05() {
		return amt05;
	}

	public void setAmt05(String amt05) {
		this.amt05 = amt05;
	}

	public String getVdate05() {
		return vdate05;
	}

	public void setVdate05(String vdate05) {
		this.vdate05 = vdate05;
	}

	public String getMdate05() {
		return mdate05;
	}

	public void setMdate05(String mdate05) {
		this.mdate05 = mdate05;
	}

	public String getIndustryShares205() {
		return industryShares205;
	}

	public void setIndustryShares205(String industryShares205) {
		this.industryShares205 = industryShares205;
	}

	public String getAssetYield05() {
		return assetYield05;
	}

	public void setAssetYield05(String assetYield05) {
		this.assetYield05 = assetYield05;
	}

	public String getAssetId06() {
		return assetId06;
	}

	public void setAssetId06(String assetId06) {
		this.assetId06 = assetId06;
	}

	public String getAssetName06() {
		return assetName06;
	}

	public void setAssetName06(String assetName06) {
		this.assetName06 = assetName06;
	}

	public String getIndustryShares206() {
		return industryShares206;
	}

	public void setIndustryShares206(String industryShares206) {
		this.industryShares206 = industryShares206;
	}

	public String getInvestStage06() {
		return investStage06;
	}

	public void setInvestStage06(String investStage06) {
		this.investStage06 = investStage06;
	}

	public String getSharesType106() {
		return sharesType106;
	}

	public void setSharesType106(String sharesType106) {
		this.sharesType106 = sharesType106;
	}

	public String getSharesType306() {
		return sharesType306;
	}

	public void setSharesType306(String sharesType306) {
		this.sharesType306 = sharesType306;
	}

	public String getSharesType206() {
		return sharesType206;
	}

	public void setSharesType206(String sharesType206) {
		this.sharesType206 = sharesType206;
	}

	public String getDeptEquity06() {
		return deptEquity06;
	}

	public void setDeptEquity06(String deptEquity06) {
		this.deptEquity06 = deptEquity06;
	}

	public String getAssetName07() {
		return assetName07;
	}

	public void setAssetName07(String assetName07) {
		this.assetName07 = assetName07;
	}

	public String getCountryArea08() {
		return countryArea08;
	}

	public void setCountryArea08(String countryArea08) {
		this.countryArea08 = countryArea08;
	}

	public String getAssetName08() {
		return assetName08;
	}

	public void setAssetName08(String assetName08) {
		this.assetName08 = assetName08;
	}

	public String getAssetId08() {
		return assetId08;
	}

	public void setAssetId08(String assetId08) {
		this.assetId08 = assetId08;
	}

	public String getIssuePartyName08() {
		return issuePartyName08;
	}

	public void setIssuePartyName08(String issuePartyName08) {
		this.issuePartyName08 = issuePartyName08;
	}

	public String getIndustryLevel208() {
		return industryLevel208;
	}

	public void setIndustryLevel208(String industryLevel208) {
		this.industryLevel208 = industryLevel208;
	}

	public String getVdate08() {
		return vdate08;
	}

	public void setVdate08(String vdate08) {
		this.vdate08 = vdate08;
	}

	public String getMdate08() {
		return mdate08;
	}

	public void setMdate08(String mdate08) {
		this.mdate08 = mdate08;
	}

	public String getAssetYield08() {
		return assetYield08;
	}

	public void setAssetYield08(String assetYield08) {
		this.assetYield08 = assetYield08;
	}

	public String getIntpayCycle08() {
		return intpayCycle08;
	}

	public void setIntpayCycle08(String intpayCycle08) {
		this.intpayCycle08 = intpayCycle08;
	}

	public String getGuaTypeRemark08() {
		return guaTypeRemark08;
	}

	public void setGuaTypeRemark08(String guaTypeRemark08) {
		this.guaTypeRemark08 = guaTypeRemark08;
	}

	public String getCountryArea09() {
		return countryArea09;
	}

	public void setCountryArea09(String countryArea09) {
		this.countryArea09 = countryArea09;
	}

	public String getVdate09() {
		return vdate09;
	}

	public void setVdate09(String vdate09) {
		this.vdate09 = vdate09;
	}

	public String getMdate09() {
		return mdate09;
	}

	public void setMdate09(String mdate09) {
		this.mdate09 = mdate09;
	}

	public String getRivalParty09() {
		return rivalParty09;
	}

	public void setRivalParty09(String rivalParty09) {
		this.rivalParty09 = rivalParty09;
	}

	public String getAssetYield09() {
		return assetYield09;
	}

	public void setAssetYield09(String assetYield09) {
		this.assetYield09 = assetYield09;
	}

	public String getBasis09() {
		return basis09;
	}

	public void setBasis09(String basis09) {
		this.basis09 = basis09;
	}

	public String getCountryArea10() {
		return countryArea10;
	}

	public void setCountryArea10(String countryArea10) {
		this.countryArea10 = countryArea10;
	}

	public String getAssetId10() {
		return assetId10;
	}

	public void setAssetId10(String assetId10) {
		this.assetId10 = assetId10;
	}

	public String getAssetName10() {
		return assetName10;
	}

	public void setAssetName10(String assetName10) {
		this.assetName10 = assetName10;
	}

	public String getIssuePartyName10() {
		return issuePartyName10;
	}

	public void setIssuePartyName10(String issuePartyName10) {
		this.issuePartyName10 = issuePartyName10;
	}

	public String getIndustryShares210() {
		return industryShares210;
	}

	public void setIndustryShares210(String industryShares210) {
		this.industryShares210 = industryShares210;
	}

	public String getCountryArea11() {
		return countryArea11;
	}

	public void setCountryArea11(String countryArea11) {
		this.countryArea11 = countryArea11;
	}

	public String getVdate11() {
		return vdate11;
	}

	public void setVdate11(String vdate11) {
		this.vdate11 = vdate11;
	}

	public String getMdate11() {
		return mdate11;
	}

	public void setMdate11(String mdate11) {
		this.mdate11 = mdate11;
	}

	public String getAssetYield11() {
		return assetYield11;
	}

	public void setAssetYield11(String assetYield11) {
		this.assetYield11 = assetYield11;
	}

	public String getIntpayCycle11() {
		return intpayCycle11;
	}

	public void setIntpayCycle11(String intpayCycle11) {
		this.intpayCycle11 = intpayCycle11;
	}

	public String getAssetName12() {
		return assetName12;
	}

	public void setAssetName12(String assetName12) {
		this.assetName12 = assetName12;
	}

	public String getFinancialComissue12() {
		return financialComissue12;
	}

	public void setFinancialComissue12(String financialComissue12) {
		this.financialComissue12 = financialComissue12;
	}

	public String getManagerId12() {
		return managerId12;
	}

	public void setManagerId12(String managerId12) {
		this.managerId12 = managerId12;
	}

	public String getTrusteeId12() {
		return trusteeId12;
	}

	public void setTrusteeId12(String trusteeId12) {
		this.trusteeId12 = trusteeId12;
	}

	public String getAmt12() {
		return amt12;
	}

	public void setAmt12(String amt12) {
		this.amt12 = amt12;
	}

	public String getTruActType12() {
		return truActType12;
	}

	public void setTruActType12(String truActType12) {
		this.truActType12 = truActType12;
	}

	public String getInvestType12() {
		return investType12;
	}

	public void setInvestType12(String investType12) {
		this.investType12 = investType12;
	}

	public String getCapUseInduLevel212() {
		return capUseInduLevel212;
	}

	public void setCapUseInduLevel212(String capUseInduLevel212) {
		this.capUseInduLevel212 = capUseInduLevel212;
	}

	public String getPlanSdate12() {
		return planSdate12;
	}

	public void setPlanSdate12(String planSdate12) {
		this.planSdate12 = planSdate12;
	}

	public String getPlanEdate12() {
		return planEdate12;
	}

	public void setPlanEdate12(String planEdate12) {
		this.planEdate12 = planEdate12;
	}

	public String getProProperty12() {
		return proProperty12;
	}

	public void setProProperty12(String proProperty12) {
		this.proProperty12 = proProperty12;
	}

	public String getRateFlag12() {
		return rateFlag12;
	}

	public void setRateFlag12(String rateFlag12) {
		this.rateFlag12 = rateFlag12;
	}

	public String getMaxRate12() {
		return maxRate12;
	}

	public void setMaxRate12(String maxRate12) {
		this.maxRate12 = maxRate12;
	}

	public String getMinRate12() {
		return minRate12;
	}

	public void setMinRate12(String minRate12) {
		this.minRate12 = minRate12;
	}

	public String getMgrFeeRate12() {
		return mgrFeeRate12;
	}

	public void setMgrFeeRate12(String mgrFeeRate12) {
		this.mgrFeeRate12 = mgrFeeRate12;
	}

	public String getTruFeeRate12() {
		return truFeeRate12;
	}

	public void setTruFeeRate12(String truFeeRate12) {
		this.truFeeRate12 = truFeeRate12;
	}

	public String getTradeFeeRate12() {
		return tradeFeeRate12;
	}

	public void setTradeFeeRate12(String tradeFeeRate12) {
		this.tradeFeeRate12 = tradeFeeRate12;
	}

	public String getAgencyFeeRate12() {
		return agencyFeeRate12;
	}

	public void setAgencyFeeRate12(String agencyFeeRate12) {
		this.agencyFeeRate12 = agencyFeeRate12;
	}

	public String getOtherFeeRate12() {
		return otherFeeRate12;
	}

	public void setOtherFeeRate12(String otherFeeRate12) {
		this.otherFeeRate12 = otherFeeRate12;
	}

	public String getCountryArea13() {
		return countryArea13;
	}

	public void setCountryArea13(String countryArea13) {
		this.countryArea13 = countryArea13;
	}

	public String getAssetName13() {
		return assetName13;
	}

	public void setAssetName13(String assetName13) {
		this.assetName13 = assetName13;
	}

	public String getTermDate13() {
		return termDate13;
	}

	public void setTermDate13(String termDate13) {
		this.termDate13 = termDate13;
	}

	public String getAssetValue13() {
		return assetValue13;
	}

	public void setAssetValue13(String assetValue13) {
		this.assetValue13 = assetValue13;
	}

	public String getAssetYield13() {
		return assetYield13;
	}

	public void setAssetYield13(String assetYield13) {
		this.assetYield13 = assetYield13;
	}

	public String getAssetName14() {
		return assetName14;
	}

	public void setAssetName14(String assetName14) {
		this.assetName14 = assetName14;
	}

	public String getAmt14() {
		return amt14;
	}

	public void setAmt14(String amt14) {
		this.amt14 = amt14;
	}

	public String getVdate14() {
		return vdate14;
	}

	public void setVdate14(String vdate14) {
		this.vdate14 = vdate14;
	}

	public String getMdate14() {
		return mdate14;
	}

	public void setMdate14(String mdate14) {
		this.mdate14 = mdate14;
	}

	public String getCountryArea14() {
		return countryArea14;
	}

	public void setCountryArea14(String countryArea14) {
		this.countryArea14 = countryArea14;
	}

	public String getRateFlag14() {
		return rateFlag14;
	}

	public void setRateFlag14(String rateFlag14) {
		this.rateFlag14 = rateFlag14;
	}

	public String getAssetYield14() {
		return assetYield14;
	}

	public void setAssetYield14(String assetYield14) {
		this.assetYield14 = assetYield14;
	}

	public String getIntpayCycle14() {
		return intpayCycle14;
	}

	public void setIntpayCycle14(String intpayCycle14) {
		this.intpayCycle14 = intpayCycle14;
	}

	public String getFinancie14() {
		return financie14;
	}

	public void setFinancie14(String financie14) {
		this.financie14 = financie14;
	}

	public String getFinOrgCode04() {
		return finOrgCode04;
	}

	public void setFinOrgCode04(String finOrgCode04) {
		this.finOrgCode04 = finOrgCode04;
	}

	public String getFinOrgCode14() {
		return finOrgCode14;
	}

	public void setFinOrgCode14(String finOrgCode14) {
		this.finOrgCode14 = finOrgCode14;
	}

	public String getFcrGradeOut14() {
		return fcrGradeOut14;
	}

	public void setFcrGradeOut14(String fcrGradeOut14) {
		this.fcrGradeOut14 = fcrGradeOut14;
	}

	public String getFinancierType114() {
		return financierType114;
	}

	public void setFinancierType114(String financierType114) {
		this.financierType114 = financierType114;
	}

	public String getFinancierType214() {
		return financierType214;
	}

	public void setFinancierType214(String financierType214) {
		this.financierType214 = financierType214;
	}

	public String getFinancierType314() {
		return financierType314;
	}

	public void setFinancierType314(String financierType314) {
		this.financierType314 = financierType314;
	}

	public String getFinanceProj14() {
		return financeProj14;
	}

	public void setFinanceProj14(String financeProj14) {
		this.financeProj14 = financeProj14;
	}

	public String getIndustryLevel2Type14() {
		return industryLevel2Type14;
	}

	public void setIndustryLevel2Type14(String industryLevel2Type14) {
		this.industryLevel2Type14 = industryLevel2Type14;
	}

	public String getProIndustryLevel214() {
		return proIndustryLevel214;
	}

	public void setProIndustryLevel214(String proIndustryLevel214) {
		this.proIndustryLevel214 = proIndustryLevel214;
	}

	public String getIndustryFlag14() {
		return industryFlag14;
	}

	public void setIndustryFlag14(String industryFlag14) {
		this.industryFlag14 = industryFlag14;
	}

	public String getMonitType14() {
		return monitType14;
	}

	public void setMonitType14(String monitType14) {
		this.monitType14 = monitType14;
	}

	public String getMonTypeComm14() {
		return monTypeComm14;
	}

	public void setMonTypeComm14(String monTypeComm14) {
		this.monTypeComm14 = monTypeComm14;
	}

	public String getOutGrade14() {
		return outGrade14;
	}

	public void setOutGrade14(String outGrade14) {
		this.outGrade14 = outGrade14;
	}

	public String getGuaType14() {
		return guaType14;
	}

	public void setGuaType14(String guaType14) {
		this.guaType14 = guaType14;
	}

	public String getPledgeType14() {
		return pledgeType14;
	}

	public void setPledgeType14(String pledgeType14) {
		this.pledgeType14 = pledgeType14;
	}

	public String getPledgeValue14() {
		return pledgeValue14;
	}

	public void setPledgeValue14(String pledgeValue14) {
		this.pledgeValue14 = pledgeValue14;
	}

	public String getNatureGuarantee14() {
		return natureGuarantee14;
	}

	public void setNatureGuarantee14(String natureGuarantee14) {
		this.natureGuarantee14 = natureGuarantee14;
	}

	public String getRelationFinan14() {
		return relationFinan14;
	}

	public void setRelationFinan14(String relationFinan14) {
		this.relationFinan14 = relationFinan14;
	}

	public String getGuaTypeRemark14() {
		return guaTypeRemark14;
	}

	public void setGuaTypeRemark14(String guaTypeRemark14) {
		this.guaTypeRemark14 = guaTypeRemark14;
	}

	public String getDeptEquity14() {
		return deptEquity14;
	}

	public void setDeptEquity14(String deptEquity14) {
		this.deptEquity14 = deptEquity14;
	}

	public String getCountryArea18() {
		return countryArea18;
	}

	public void setCountryArea18(String countryArea18) {
		this.countryArea18 = countryArea18;
	}

	public String getAssetName18() {
		return assetName18;
	}

	public void setAssetName18(String assetName18) {
		this.assetName18 = assetName18;
	}

	public String getVdate18() {
		return vdate18;
	}

	public void setVdate18(String vdate18) {
		this.vdate18 = vdate18;
	}

	public String getMdate18() {
		return mdate18;
	}

	public void setMdate18(String mdate18) {
		this.mdate18 = mdate18;
	}

	public String getAssetValue18() {
		return assetValue18;
	}

	public void setAssetValue18(String assetValue18) {
		this.assetValue18 = assetValue18;
	}

	public String getAssetYield18() {
		return assetYield18;
	}

	public void setAssetYield18(String assetYield18) {
		this.assetYield18 = assetYield18;
	}

	public String getAssetIdL01() {
		return assetIdL01;
	}

	public void setAssetIdL01(String assetIdL01) {
		this.assetIdL01 = assetIdL01;
	}

	public String getAssetNameL01() {
		return assetNameL01;
	}

	public void setAssetNameL01(String assetNameL01) {
		this.assetNameL01 = assetNameL01;
	}

	public String getIndustryShares2L01() {
		return industryShares2L01;
	}

	public void setIndustryShares2L01(String industryShares2L01) {
		this.industryShares2L01 = industryShares2L01;
	}

	public String getFinancialComissueL01() {
		return financialComissueL01;
	}

	public void setFinancialComissueL01(String financialComissueL01) {
		this.financialComissueL01 = financialComissueL01;
	}

	public String getInvestStageL01() {
		return investStageL01;
	}

	public void setInvestStageL01(String investStageL01) {
		this.investStageL01 = investStageL01;
	}

	public String getSharesType1L01() {
		return sharesType1L01;
	}

	public void setSharesType1L01(String sharesType1L01) {
		this.sharesType1L01 = sharesType1L01;
	}

	public String getSharesType3L01() {
		return sharesType3L01;
	}

	public void setSharesType3L01(String sharesType3L01) {
		this.sharesType3L01 = sharesType3L01;
	}

	public String getSharesType2L01() {
		return sharesType2L01;
	}

	public void setSharesType2L01(String sharesType2L01) {
		this.sharesType2L01 = sharesType2L01;
	}

	public String getAssetName16() {
		return assetName16;
	}

	public void setAssetName16(String assetName16) {
		this.assetName16 = assetName16;
	}

	public String getAssetId16() {
		return assetId16;
	}

	public void setAssetId16(String assetId16) {
		this.assetId16 = assetId16;
	}

	public String getManagerId16() {
		return managerId16;
	}

	public void setManagerId16(String managerId16) {
		this.managerId16 = managerId16;
	}

	public String getTrusteeId16() {
		return trusteeId16;
	}

	public void setTrusteeId16(String trusteeId16) {
		this.trusteeId16 = trusteeId16;
	}

	public String getAmt16() {
		return amt16;
	}

	public void setAmt16(String amt16) {
		this.amt16 = amt16;
	}

	public String getTruActType16() {
		return truActType16;
	}

	public void setTruActType16(String truActType16) {
		this.truActType16 = truActType16;
	}

	public String getInvestType16() {
		return investType16;
	}

	public void setInvestType16(String investType16) {
		this.investType16 = investType16;
	}

	public String getCapUseInduLevel216() {
		return capUseInduLevel216;
	}

	public void setCapUseInduLevel216(String capUseInduLevel216) {
		this.capUseInduLevel216 = capUseInduLevel216;
	}

	public String getPlanSdate16() {
		return planSdate16;
	}

	public void setPlanSdate16(String planSdate16) {
		this.planSdate16 = planSdate16;
	}

	public String getPlanEdate16() {
		return planEdate16;
	}

	public void setPlanEdate16(String planEdate16) {
		this.planEdate16 = planEdate16;
	}

	public String getProProperty16() {
		return proProperty16;
	}

	public void setProProperty16(String proProperty16) {
		this.proProperty16 = proProperty16;
	}

	public String getRateFlag16() {
		return rateFlag16;
	}

	public void setRateFlag16(String rateFlag16) {
		this.rateFlag16 = rateFlag16;
	}

	public String getMaxRate16() {
		return maxRate16;
	}

	public void setMaxRate16(String maxRate16) {
		this.maxRate16 = maxRate16;
	}

	public String getMinRate16() {
		return minRate16;
	}

	public void setMinRate16(String minRate16) {
		this.minRate16 = minRate16;
	}

	public String getMgrFeeRate16() {
		return mgrFeeRate16;
	}

	public void setMgrFeeRate16(String mgrFeeRate16) {
		this.mgrFeeRate16 = mgrFeeRate16;
	}

	public String getTruFeeRate16() {
		return truFeeRate16;
	}

	public void setTruFeeRate16(String truFeeRate16) {
		this.truFeeRate16 = truFeeRate16;
	}

	public String getTradeFeeRate16() {
		return tradeFeeRate16;
	}

	public void setTradeFeeRate16(String tradeFeeRate16) {
		this.tradeFeeRate16 = tradeFeeRate16;
	}

	public String getAgencyFeeRate16() {
		return agencyFeeRate16;
	}

	public void setAgencyFeeRate16(String agencyFeeRate16) {
		this.agencyFeeRate16 = agencyFeeRate16;
	}

	public String getOtherFeeRate16() {
		return otherFeeRate16;
	}

	public void setOtherFeeRate16(String otherFeeRate16) {
		this.otherFeeRate16 = otherFeeRate16;
	}

	public String getCountryArea17() {
		return countryArea17;
	}

	public void setCountryArea17(String countryArea17) {
		this.countryArea17 = countryArea17;
	}

	public String getAssetName17() {
		return assetName17;
	}

	public void setAssetName17(String assetName17) {
		this.assetName17 = assetName17;
	}

	public String getTermDate17() {
		return termDate17;
	}

	public void setTermDate17(String termDate17) {
		this.termDate17 = termDate17;
	}

	public String getAssetYield17() {
		return assetYield17;
	}

	public void setAssetYield17(String assetYield17) {
		this.assetYield17 = assetYield17;
	}

	public String getOrgData() {
		return orgData;
	}

	public void setOrgData(String orgData) {
		this.orgData = orgData;
	}

	public String getEquityQuitStr() {
		return DateUtil.format(super.getEquityQuit());
	}

}