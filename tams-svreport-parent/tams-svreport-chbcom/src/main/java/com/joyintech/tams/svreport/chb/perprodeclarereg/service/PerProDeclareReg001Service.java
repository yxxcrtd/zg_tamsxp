package com.joyintech.tams.svreport.chb.perprodeclarereg.service;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import com.joyintech.component.svrreport.chb.contant.ChbConstant;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.base.dto.UploadFile;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.*;
import com.joyintech.system.common.listener.UploadListener;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.service.SvrCommonExportService;
import com.joyintech.tams.report.common.util.DateCalcUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubErrorCheck;
import com.joyintech.tams.svreport.chb.perprodeclarereg.dao.ChbPrdDeclareSubHisMapper;
import com.joyintech.tams.svreport.chb.perprodeclarereg.dao.PerProDeclareReg001Mapper;
import com.joyintech.tams.svreport.chb.perprodeclarereg.dto.ChbPrdDeclareRegFileCode;
import com.joyintech.tams.svreport.chb.perprodeclarereg.dto.PerProDeclareRegDto;
import com.joyintech.tams.svreport.chb.prodraisetotalregist.dto.RaiseTotalRegist001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.BindException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

@Service(value="svrPerProDeclareReg001Service")
@Transactional(rollbackFor = Exception.class)
public class PerProDeclareReg001Service extends SvrCommonExportService<PerProDeclareRegDto> {
	@Autowired
	private PerProDeclareReg001Mapper perProDeclareReg001Mapper;
	@Autowired
	private ChbPrdDeclareSubHisMapper chbPrdDeclareSubHisMapper;
	@Autowired
	private UploadListener  uploadListener;
	
    @Autowired
    private BeforeSubErrorCheck beforeSubErrorCheck;

	@Autowired
	private CheckReport001Mapper checkReport001Mapper;

	@Autowired
	private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
	/**
	 * 中债基础数据mapper
	 */
	@Autowired
	private BaseMapper baseMapper;

 	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 白名单校验正则表达式
	 */
	private static final String whitePattern = "^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$";

	/**
	 *
	 * @param pageParam
	 * @param sendChbFlag 是否直连发送：直连发送数据字典不需要转成code value形式；手工报送则需要
	 * @return
	 */
	public Page<PerProDeclareRegDto> selectForPage(Map<String, Object> pageParam, boolean sendChbFlag) {
		dealSearchParam(pageParam);
		pageParam.put("flag", "2");
		//add by leipeng 20200628
		if(CommonUtil.isBAmodel()){
			pageParam.put("model_type", "01");

		}
		if (null != pageParam.get(GlobalConstant.PARAM_PAGE)
				&& null != pageParam.get(GlobalConstant.PARAM_LENGTH)) {
			int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
	        int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
			Page<PerProDeclareRegDto> page = queryForPage(
					pagenum,legth,()->perProDeclareReg001Mapper.selectAllData(pageParam));
			dealDataList(page.getRoot(), sendChbFlag);
			return page;
		} else {
			Page<PerProDeclareRegDto> page = queryForPage(PerProDeclareReg001Mapper.class,"selectAllData",pageParam);
			dealDataList(page.getRoot(), sendChbFlag);
			return page;
		}
		

	}

	@SuppressWarnings("unused")
    private void dealSearchParam(Map<String, Object> pageParam) {
		// 选择行数据时
		 if (pageParam.containsKey("keyList")) {
			String keyList = pageParam.get("keyList").toString();
			try {
				List<PerProDeclareRegDto> perProDeclareList = JsonUtils.json2List(keyList,
						PerProDeclareRegDto.class);
				pageParam.put("perProDeclareList", perProDeclareList);
			} catch (IOException e) {
	            logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			String prodName = (String) pageParam.get("prodName");
			pageParam.put("prodname", prodName);
			String prodInsId=(String) pageParam.get("prodInsId");
			String prodId = (String) pageParam.get("prodId");
			// 数据状态
			String status = (String) pageParam.get("status");
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			
			// 将数据状态当做多选下拉框处理
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
			// 将行内标识码当做多选下拉框处理
			if (StringUtil.isNotBlank(prodInsId)) {
							pageParam.put("prodInsIdList", StringUtil.splitToList(GlobalConstant.COMMA, prodInsId));
			}
			// 将产品代码当做多选下拉框处理
			if (StringUtil.isNotBlank(prodId)) {
							pageParam.put("prodIdList", StringUtil.splitToList(GlobalConstant.COMMA, prodId));
			}
//			System.out.println(" -------- "+pageParam.get("prodIdList"));
			// 将报送状态当做多选下拉框处理
			if (StringUtil.isNotBlank(sendStatus)) {
				pageParam.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
			}
//			System.out.println(" -------- "+pageParam.get("sendStatusList"));
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");
			// 将反馈状态当做多选下拉框处理
			if (StringUtil.isNotBlank(submitFeedbackStatus)) {
				pageParam.put("submitFeedbackStatusList",
						StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
			}
			String submitStatus = (String) pageParam.get("submitStatus");
            
			pageParam.put("submitDeadLineStart",
					DateCalcUtil.getStartOfDay((String) pageParam.get("submitDeadLineStart")));
			pageParam.put("submitDeadLineEnd", DateCalcUtil.getEndOfDay((String) pageParam.get("submitDeadLineEnd")));
			
			pageParam.put("raise_startdateStart",
					DateCalcUtil.getStartOfDay((String) pageParam.get("raise_startdateStart")));
			pageParam.put("raise_startdateEnd", DateCalcUtil.getEndOfDay((String) pageParam.get("raise_startdateEnd")));
			
			pageParam.put("statusDateStart",
					DateCalcUtil.getStartOfDay((String) pageParam.get("statusDateStart")));
			pageParam.put("statusDateEnd", DateCalcUtil.getEndOfDay((String) pageParam.get("statusDateEnd")));
			
			pageParam.put("submitDateStart",
					DateCalcUtil.getStartOfDay((String) pageParam.get("submitDateStart")));
			pageParam.put("submitDateEnd", DateCalcUtil.getEndOfDay((String) pageParam.get("submitDateEnd")));

		}
	}

	public void dealDataList(List<PerProDeclareRegDto> root, boolean flag) {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		root.forEach(item -> {
			if(item.getSubmitFeedbackStatus()!=null){
				item.setSubmitFeedbackStatus(SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态, item.getSubmitFeedbackStatus()));
			}
			if(item.getRaisePstrdate()!=null){
				item.setRaisePstrDateSTR(format.format(item.getRaisePstrdate()));
			}
			if(item.getRaisePenddate()!=null){
				item.setRaisePendDateSTR(format.format(item.getRaisePenddate()));
			}
			if(item.getStatus()!=null){
				item.setStatusSTR(SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, item.getStatus()));
			}
			if(item.getSendStatus()!=null){
				item.setSendStatusSTR(SvrDataUtil.getNameFromDataDict(DictCode.报送状态, item.getSendStatus()));
			}
			if(StringUtil.isNotEmpty(item.getProfitType())){
				item.setProfitType(item.getProfitType().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品收益类型, item.getProfitType()));
			}
			if(StringUtil.isNotEmpty(item.getTermtype())){
				item.setTermtype(item.getTermtype().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品期限, item.getTermtype()));
			}
			if(StringUtil.isNotEmpty(item.getInvestArae())){
				item.setInvestArae(item.getInvestArae().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.资金投向地区, item.getInvestArae()));
			}
		    if(StringUtil.isNotEmpty(item.getInvestCountry())){
		    	List<String> investCountrys=StringUtil.splitToList(GlobalConstant.COMMA, item.getInvestCountry());
				item.setInvestCountry("");
				for(String investCountry : investCountrys){
					item.setInvestCountry(item.getInvestCountry()+","+investCountry+" "+SvrDataUtil.getNameFromDataDict(DictCode.所属国家或地区, investCountry));
				}
				item.setInvestCountry(item.getInvestCountry().substring(1));
		    }
			
			if(StringUtil.isNotEmpty(item.getFinanSerModel())){
				item.setFinanSerModel(item.getFinanSerModel().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.理财业务服务模式, item.getFinanSerModel()));			
			}
			if(StringUtil.isNotEmpty(item.getOperateMode())){
				item.setOperateMode(item.getOperateMode().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品运作模式, item.getOperateMode()));
			}
			if(StringUtil.isNotEmpty(item.getBookMode())){
				item.setBookMode(item.getBookMode().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.会计核算方式_中债, item.getBookMode()));
			}
			if(StringUtil.isNotEmpty(item.getCmmode())){
				item.setCmmode(item.getCmmode().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品资产配置方式_中债, item.getCmmode()));
			}
			if(StringUtil.isNotEmpty(item.getPmmode())){
				item.setPmmode(item.getPmmode().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.管理模式, item.getPmmode()));
			}
			if(StringUtil.isNotEmpty(item.getPricedMode())){
				item.setPricedMode(item.getPricedMode().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品定价方式, item.getPricedMode()));
			}
			if(StringUtil.isNotEmpty(item.getInvAssetType())){
				item.setInvAssetType(item.getInvAssetType().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.资金实际投向, item.getInvAssetType()));
			}
			if(StringUtil.isNotEmpty(item.getConPrdTraget())){
				List<String> conPrdTragets=StringUtil.splitToList(GlobalConstant.COMMA, item.getConPrdTraget());
				item.setConPrdTraget("");
				for(String conPrdTraget : conPrdTragets){
					item.setConPrdTraget(item.getConPrdTraget()+","+conPrdTraget+" "+SvrDataUtil.getNameFromDataDict(DictCode.标的类别, conPrdTraget));
				}
				item.setConPrdTraget(item.getConPrdTraget().substring(1));
			}
			if(StringUtil.isNotEmpty(item.getContMode())){
				item.setContMode(item.getContMode().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.合作模式, item.getContMode()));
			}
			if(StringUtil.isNotEmpty(item.getRiskType())){
				List<String> riskTypeS=StringUtil.splitToList(GlobalConstant.COMMA, item.getRiskType());
				item.setRiskType("");
				for(String riskType : riskTypeS){
					item.setRiskType(item.getRiskType()+","+riskType+" "+SvrDataUtil.getNameFromDataDict(DictCode.投资者风险偏好, riskType));
				}
				item.setRiskType(item.getRiskType().substring(1));
			}
			if(StringUtil.isNotEmpty(item.getInvestPrinDate())){
				item.setInvestPrinDate(item.getInvestPrinDate().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.投资本金_收益_到账日_中债, item.getInvestPrinDate()));
			}
			if(StringUtil.isNotEmpty(item.getInvestProfDate())){
				item.setInvestProfDate(item.getInvestProfDate().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.投资本金_收益_到账日_中债, item.getInvestProfDate()));
			}
			if(StringUtil.isNotEmpty(item.getRiskLevel())){
				item.setRiskLevel(item.getRiskLevel().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品风险等级_申报, item.getRiskLevel()));
			}
			if(StringUtil.isNotEmpty(item.getTermRightFlag())){
				item.setTermRightFlag(item.getTermRightFlag().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.有无_中债, item.getTermRightFlag()));
			}
			if(StringUtil.isNotEmpty(item.getCusRedeemFlag())){
				item.setCusRedeemFlag(item.getCusRedeemFlag().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.有无_中债, item.getCusRedeemFlag()));
			}
			if(StringUtil.isNotEmpty(item.getCreditFlag())){
				item.setCreditFlag(item.getCreditFlag().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.有无_中债, item.getCreditFlag()));
			}
			if(StringUtil.isNotEmpty(item.getCreditType())){
				List<String> creditTypes=StringUtil.splitToList(GlobalConstant.COMMA, item.getCreditType());
				String crediType="";
				for(String creditType : creditTypes){
					crediType=crediType+","+creditType+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品增信机构类型, creditType);
				}
				item.setCreditType(crediType.substring(1));
			}
			
			if(StringUtil.isNotEmpty(item.getCreditMode())){
				item.setCreditMode(item.getCreditMode()+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品增信形式, item.getCreditMode()));
			}
			if(StringUtil.isNotEmpty(item.getIsYield())){
				item.setIsYield(item.getIsYield().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.有无_中债, item.getIsYield()));
			}
			if(StringUtil.isNotEmpty(item.getIsYieldMeasBasis())){
				item.setIsYieldMeasBasis(item.getIsYieldMeasBasis().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.是否_中债, item.getIsYieldMeasBasis()));
			}
			if(StringUtil.isNotEmpty(item.getSaleArea())){
				List<String> saleAreas=StringUtil.splitToList(GlobalConstant.COMMA, item.getSaleArea());
				item.setSaleArea("");
				for(String saleArea : saleAreas){
					item.setSaleArea(item.getSaleArea()+","+saleArea+" "+SvrDataUtil.getNameFromDataDict(DictCode.销售区域_中债, saleArea));
				}
				item.setSaleArea(item.getSaleArea().substring(1));
			}
			
			if(StringUtil.isNotEmpty(item.getOutDepCtry())){
				List<String> outDepCtrys=StringUtil.splitToList(GlobalConstant.COMMA, item.getOutDepCtry());
				item.setOutDepCtry("");
				for(String outDepCtry : outDepCtrys){
					item.setOutDepCtry(item.getOutDepCtry()+","+outDepCtry+" "+SvrDataUtil.getNameFromDataDict(DictCode.所属国家或地区, outDepCtry));
				}
				item.setOutDepCtry(item.getOutDepCtry().substring(1));
			}
			
			if(item.getRaiseCcy()!=null && flag){
				List<String> raiseCCYs=StringUtil.splitToList(GlobalConstant.COMMA, item.getRaiseCcy());
				item.setRaiseCcy("");
				for(String raiseCCY : raiseCCYs){
					item.setRaiseCcy(item.getRaiseCcy()+","+SvrDataUtil.getNameFromDataDict(DictCode.币种_中债, raiseCCY));
				}
				item.setRaiseCcy(item.getRaiseCcy().substring(1));
			}
			
			if(item.getCashPrinCcy()!=null && flag){
				List<String> cashPrinCCYs=StringUtil.splitToList(GlobalConstant.COMMA, item.getCashPrinCcy());
				item.setCashPrinCcy("");
				for(String cashPrinCCY : cashPrinCCYs){
					item.setCashPrinCcy(item.getCashPrinCcy()+","+SvrDataUtil.getNameFromDataDict(DictCode.币种_中债, cashPrinCCY));
				}
				item.setCashPrinCcy(item.getCashPrinCcy().substring(1));
			}
			
			if(item.getCashProfCcy()!=null && flag){
				List<String> cashProfCCYs=StringUtil.splitToList(GlobalConstant.COMMA, item.getCashProfCcy());
				item.setCashProfCcy("");
				for(String cashProfCCY : cashProfCCYs){
					item.setCashProfCcy(item.getCashProfCcy()+","+SvrDataUtil.getNameFromDataDict(DictCode.币种_中债, cashProfCCY));
				}
				item.setCashProfCcy(item.getCashProfCcy().substring(1));
			}
			
			//募集方式
			if(StringUtil.isNotEmpty(item.getRaiseType())){
				item.setRaiseType(item.getRaiseType().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.募集方式_中债, item.getRaiseType()));
			}
			//投资性质
			if(StringUtil.isNotEmpty(item.getIvstorientation())){
				item.setIvstorientation(item.getIvstorientation().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.投资性质, item.getIvstorientation()));
			}
			//是否金融同业专属
			if(StringUtil.isNotEmpty(item.getExcFinSector())){
				item.setExcFinSector(item.getExcFinSector().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.是否_中债, item.getExcFinSector()));
			}
			//是否设置最短持有期限
			if(StringUtil.isNotEmpty(item.getIsShortTerm())){
				item.setIsShortTerm(item.getIsShortTerm().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.是否_中债, item.getIsShortTerm()));
			}
			//最短持有期后是否自由赎回
			if(StringUtil.isNotEmpty(item.getIsFreeRedem())){
				item.setIsFreeRedem(item.getIsFreeRedem().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.是否_中债_期限, item.getIsFreeRedem()));
			}
			//是否现金管理类
			if(StringUtil.isNotEmpty(item.getCashManaCate())){
				item.setCashManaCate(item.getCashManaCate().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.是否_中债, item.getCashManaCate()));
			}
			// 境内托管机构名称
            if(StringUtil.isNotEmpty(item.getInDepName())){
                item.setInDepName(item.getInDepName().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.境内托管机构名称, item.getInDepName()));
            }
            // 新老产品标记
			if(StringUtil.isNotEmpty(item.getIsFhxgcp())){
				item.setIsFhxgcp(item.getIsFhxgcp().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.新老产品标记, item.getIsFhxgcp()));
			}
			//产品特殊属性
			if(StringUtil.isNotEmpty(item.getPrdSpecProperty())){
				List<String> prdSpecPropertys=StringUtil.splitToList(GlobalConstant.COMMA, item.getPrdSpecProperty());
				item.setPrdSpecProperty("");
				for(String prdSpecProperty : prdSpecPropertys){
					item.setPrdSpecProperty(item.getPrdSpecProperty()+";"+prdSpecProperty+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品特殊属性, prdSpecProperty));
				}
				item.setPrdSpecProperty(item.getPrdSpecProperty().substring(1));
			}
		});
	}

	public List<PerProDeclareRegDto> selectAllList(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		pageParam.put("flag", "1");
		List<PerProDeclareRegDto> dataList =  (List<PerProDeclareRegDto>) pageParam.get("perProDeclareList");

		List<PerProDeclareRegDto> dtoList = new ArrayList<PerProDeclareRegDto>();
		//this.selectAllDataBatch(dataList, dtoList);
//		investorsHoldInfo001Mapper.selectAllData(pageParam);
		dtoList=perProDeclareReg001Mapper.selcteDataByList(dataList);
		dealDataList(dtoList, false);
		return dtoList;
	}

	
	
	
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		Map<String, Object> pageParam = exportParam.getSelectParam();
		String pageIds = (String) pageParam.get("pageIds");
		if (StringUtil.isNotEmpty(pageIds)) {
			List<String> pageNames = Arrays.asList(pageIds.split(GlobalConstant.COMMA));
			pageParam.replace("pageIds", pageIds, pageNames);
		} else {
			pageParam.replace("pageIds", pageIds, null);
		}

		// 设置参数
		dealSearchParam(pageParam);
		pageParam.put("flag", "1");
		//无查询参数的情况
		exportParam.config("/com/joyintech/tams/svreport/chb/perprodeclarereg/conf/export_conf.xml")
		.select(() -> dealDataList(perProDeclareReg001Mapper.selectAllData(pageParam), false));
	}

    public List<PerProDeclareRegDto> selectAllData(Map<String, Object> map){
        return perProDeclareReg001Mapper.selectAllData(map);
    }
    
    @Override
    public com.joyintech.framework.grid.Page<PerProDeclareRegDto> selectPageData(Map<String, Object> pageParam) {
        List<PerProDeclareRegDto> data =  restTemplate.postForList(
                    SvrServiceApplication.CHB_SERVICE_NAME + "/PerProDeclareReg/selectAllData",pageParam,
                    PerProDeclareRegDto.class);
        dealDataList(data,false);
        com.joyintech.framework.grid.Page<PerProDeclareRegDto> page = new com.joyintech.framework.grid.Page<PerProDeclareRegDto>();
        page.setRoot(data);
        //page.setTotal(data.size());
		if(!CollectionUtils.isEmpty(page.getRoot())){
			for(PerProDeclareRegDto dto : page.getRoot()){
				dto.setBelongInstitutionCode(null);
			}
		}
		int pageSize = StringUtil.integerValueOf(pageParam.get(GlobalConstant.PARAM_LENGTH).toString(), StringUtil.integerValueOf(CacheUtil.getSysParam(GlobalConstant.EXPORT_EXCEL_ROW_COUNT),50000));

		if(data!=null && data.size() > 0){
			page.setTotal(data.size()/pageSize+1);
		}else{
			page.setTotal(0);
		}

        return  page;
    }
	/**
     * 手动处理
     * @param map 
     */
	public void updateSumStaus(Map<String, Object> map) {
		dealSearchParam(map);
		List<PerProDeclareRegDto> proDeclareList=new ArrayList<PerProDeclareRegDto>();
		List<PerProDeclareRegDto> subList=new ArrayList<PerProDeclareRegDto>();
		//界面未勾选
		if(map.get("perProDeclareList")==null){
			proDeclareList = perProDeclareReg001Mapper.selectAllData(map);
		}else {
			//界面勾选
			@SuppressWarnings("unchecked")
			List<PerProDeclareRegDto> dataList =  (List<PerProDeclareRegDto>) map.get("perProDeclareList");
			// modify by zhangliang 20210309 手工处理与前台逻辑一致：只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理 start
//			subList=perProDeclareReg001Mapper.selcteDataByList(dataList);
//			for(PerProDeclareRegDto dto : subList){
//				if(DictCodeListExt2.报送状态_未报送.getValue().equals(dto.getSendStatus())){
//					proDeclareList.add(dto);
//				}
//			}
			// modify by zhangliang 20210309 手工处理与前台逻辑一致：只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理 end
			proDeclareList = perProDeclareReg001Mapper.selcteDataByList(dataList);
		}
		if(!CollectionUtils.isEmpty(proDeclareList)){
			// 每批处理的数据条数
			int partSize = 500;
			// 存放业务数据的临时list，数据量不超过partSize
			List<PerProDeclareRegDto> tmpList = new ArrayList<PerProDeclareRegDto>();
			// 分批次处理
			for (int j = 0; j < proDeclareList.size(); j++) {
				tmpList.add(proDeclareList.get(j));
				if (partSize == tmpList.size() || j == proDeclareList.size() - 1) {
					//找sub表有没有已报送的数据
					List<String> regList = perProDeclareReg001Mapper.selectRegistSeqnoList(tmpList,"02");
					//更新sub表数据（中债通过、手工处理）
					updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(),DictCodeListExt2.报送反馈状态_中债通过.getValue(), tmpList);
					if(!CollectionUtils.isEmpty(regList)){
						List<List<String>> regseqnolist = createStringList(regList,20);
						for(int i=0;i<regseqnolist.size();i++){
							//将已报送的数据移至his表 insert select 
							chbPrdDeclareSubHisMapper.insertSubHisBatch(regseqnolist.get(i));
							//将已报送的数据从sub表物理删除
							perProDeclareReg001Mapper.deleteRegistSeqnoList(regseqnolist.get(i));
						}
					}
					tmpList.clear();
				}
			}
		}
	}

	private List<List<String>> createStringList(List<String> targe,int size) {
		List<List<String>> listArr = new ArrayList<List<String>>();
		//获取被拆分的数组个数
		int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
		for(int i=0;i<arrSize;i++) {
			List<String> sub = new ArrayList<String>();
			//把指定索引数据放入到list中
			for(int j = i*size;j <= size*(i+1)-1;j++) {
				if(j <= targe.size()-1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(sub);
		}
		return listArr;
	}
	
	private void updateInterfSubmitAndSeq(String sendStatus, String submitFeeBackStatus ,List<PerProDeclareRegDto> proDeclareList) {
		if (StringUtil.isEmptyList(proDeclareList)) {
 			return;
 		}
		int maxDataCountOneTime = 10;
		int dataCount = proDeclareList.size();
		if (dataCount <= maxDataCountOneTime) {
			perProDeclareReg001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, submitFeeBackStatus,proDeclareList);
 			return;
 		}
		List<PerProDeclareRegDto> newDataList = new ArrayList<PerProDeclareRegDto>();
 		newDataList.addAll(proDeclareList);
 	    // 分批处理
 	    int part = dataCount / maxDataCountOneTime; // 分批数
 	   for (int i = 0; i < part; i++) {
			logger.info("单次更新登记表start===");
			perProDeclareReg001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,submitFeeBackStatus,
                newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("单次更新登记表end===");
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			logger.info("单次更新登记表start===");
			perProDeclareReg001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,submitFeeBackStatus, newDataList);
			logger.info("单次更新登记表end===");
		}
	}

	/**
 	 * 已手工处理的数据将报送状态改为未报送
 	 * 取消手工处理
 	 * @param upKey
 	 *            传入主键
 	 * @see
 	 * @since 1.0
 	 */
 	public String cancelMannul(Map<String, Object> map) {
 		dealSearchParam(map);
 		List<PerProDeclareRegDto> proDeclareList=new ArrayList<PerProDeclareRegDto>();
 		//界面未勾选
		if(map.get("perProDeclareList")==null){
			proDeclareList = perProDeclareReg001Mapper.selectAllData(map);
		}else {
			//界面勾选
			@SuppressWarnings("unchecked")
			List<PerProDeclareRegDto> dataList =  (List<PerProDeclareRegDto>) map.get("perProDeclareList");
			proDeclareList=perProDeclareReg001Mapper.selcteDataByList(dataList);
		}
		if(!CollectionUtils.isEmpty(proDeclareList)){
			//找sub表有没有未报送的数据
			List<String> unregList = perProDeclareReg001Mapper.selectRegistSeqnoList(proDeclareList,"01");
			if(!CollectionUtils.isEmpty(unregList)){
				return "fail";
			}
			//查询his表，取最新的已报送数据
			List<String> seqnolist = chbPrdDeclareSubHisMapper.selectByprodId(proDeclareList);
			//变更手工处理的数据（报送状态：未报送）
			List<List<PerProDeclareRegDto>> lists = ListUtils.partition(proDeclareList, 10);
			if(CollectionUtils.isNotEmpty(lists)){
				for(List<PerProDeclareRegDto> pro : lists){
					perProDeclareReg001Mapper.cancelMannultRegist(proDeclareList);
				}
			}
			if(!CollectionUtils.isEmpty(seqnolist)){
				List<List<String>> regList = createStringList(seqnolist, 20);
				for(int i=0;i<regList.size();i++){
					//将his表数据移至sub表
					perProDeclareReg001Mapper.insertSeletForSub(regList.get(i));
					//his表数据物理删除
					chbPrdDeclareSubHisMapper.deleteBySeqno(regList.get(i));
				}
			}
		}
		return "success";
 	}
	
	public AjaxResult uploadFile(Map<String, Object> map) throws ZipException, IOException {
 		
 		String srcName=(String) map.get("param[0][srcName]");
		String fileNo=(String) map.get("param[0][fileNo]");
		String fileProdId=srcName.substring(0, srcName.lastIndexOf("."));
		String targetFileSTR=(String) map.get("param[0][targetFile][absoluteFile]");
		String registSeqno=(String) map.get("registSeqno");
		String prodId=(String) map.get("prodId");
		File file=new SafelyFile(targetFileSTR);

		// add by zhangliang 20201015 验证九大附件 start
		// 1、校验各个文件夹是否有文档，根据“必填、选填”那一项校验；
		// 2、校验各个文件大小，只要存在最大的文件超出20M就提示；
		// 3、校验文件类型；
		StringBuffer fileCheckMsg = new StringBuffer();
		if(!fileProdId.equals(prodId)){
			fileCheckMsg.append("文件名与所选产品代码不匹配！");
			fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
		}

		int fileNum=0;
		// 记录文件信息
		Map<String, List<String>> fileExistsMap = new HashMap<>();

		try (
			ZipFile zipFile=new ZipFile(new SafelyFile(targetFileSTR),Charset.forName("GBK"));
		){
			List<String> folderNoList = ChbPrdDeclareRegFileCode.genFolderNoList();


			for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
				ZipEntry entry=e.nextElement();
				//    aaa.doc:表示文件
				if (entry.getName().indexOf("/") < 0){
					fileCheckMsg.append("包含不必要的文件或文件夹"+ entry.getName() + "！");
					fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
					continue;
				}
				//    09/bbb/aaa.doc:表示文件夹下存在文件夹
				if (entry.getName().indexOf("/") != entry.getName().lastIndexOf("/")){
					fileCheckMsg.append("包含不必要的文件或文件夹"+ entry.getName() + "！");
					fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
					continue;
				}
				//    09/:表示文件夹
				String folderName = entry.getName().substring(0, entry.getName().indexOf("/"));
				if (entry.getName().indexOf("/") + 1== entry.getName().length()){
					if (!folderNoList.contains(folderName)){
						fileCheckMsg.append("包含不必要的文件夹"+ folderName + "！");
						fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
					}
					continue;
				}
				//    09/aaa.doc:表示文件夹下文件
				if (entry.getName().indexOf("/") == entry.getName().lastIndexOf("/")){
					ChbPrdDeclareRegFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareRegFileCode.genInfoByFolderNo(folderName);
					if (chbPrdDeclareRegFileCode != null && entry.getSize() > chbPrdDeclareRegFileCode.getFileSize() * 1024 * 1024){
						fileCheckMsg.append("文件（" + entry.getName() + "）大小超过" + chbPrdDeclareRegFileCode.getFileSize() + "M。");
						fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
					}
					String fileType = entry.getName().substring(entry.getName().lastIndexOf(".") + 1, entry.getName().length());
					if (chbPrdDeclareRegFileCode != null && chbPrdDeclareRegFileCode.getFileType().indexOf(fileType) < 0){
						fileCheckMsg.append("文件（" + entry.getName() + "）扩展名不对。必须为以下的一种：" + chbPrdDeclareRegFileCode.getFileType() + "。" );
						fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
					}
					// add by zhangliang 20210315 上传九大附件时，需要对每个附件的名称进行白名单检查 start
					String fileName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1, entry.getName().lastIndexOf("."));
					if (!fileName.matches(whitePattern)){
						fileCheckMsg.append("文件（" + fileName + "）必须填写白名单中的文字。");
						fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
					}
					// add by zhangliang 20210315 上传九大附件时，需要对每个附件的名称进行白名单检查 end
					if (fileExistsMap.containsKey(folderName)){
						List<String> folderNameList = new ArrayList<>(fileExistsMap.get(folderName));
						folderNameList.add(entry.getName().substring(folderName.length() + 1));
						fileExistsMap.put(folderName, folderNameList);
					}
					else{
						fileExistsMap.put(folderName, Arrays.asList(entry.getName().substring(folderName.length() + 1)));
					}
				}
				// 统计文件个数
				if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()>0){
					fileNum++;
				}
			}
			for (String folderNo : folderNoList){
				if (!fileExistsMap.containsKey(folderNo) || StringUtil.isEmptyList(fileExistsMap.get(folderNo))){
					ChbPrdDeclareRegFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareRegFileCode.genInfoByFolderNo(folderNo);
					if (chbPrdDeclareRegFileCode.isNend()){
						fileCheckMsg.append(chbPrdDeclareRegFileCode.getFolderName() + "不可为空。");
						fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
					}
				}
			}

		} catch (Exception e) {
			throw new BusinessException(e);
		}

		if (fileCheckMsg.length() > 0){
			if(file.exists()){
				file.delete();
			}
			uploadListener.remove(fileNo);

			fileCheckMsg.append("上传失败！");
			return new AjaxResult(fileCheckMsg.toString(), MessageType.错误消息);
		}

		StringBuffer fileSb = new StringBuffer();
		for (String key : fileExistsMap.keySet()){
			fileSb.append(key);
			fileSb.append(GlobalConstant.COMMA);
			List<String> fileInfoList = fileExistsMap.get(key);
			for (String fileInfo : fileInfoList){
				fileSb.append(fileInfo);
				fileSb.append(GlobalConstant.COMMA);
			}
			fileSb = new StringBuffer(fileSb.substring(0, fileSb.length() - GlobalConstant.COMMA.length()));
			fileSb.append(GlobalConstant.SEMICOLON);
		}
		fileSb = new StringBuffer(fileSb.substring(0, fileSb.length() - GlobalConstant.SEMICOLON.length()));
		// add by zhangliang 20201015 验证九大附件 end

		PerProDeclareRegDto  perProDeclareRegDto=new PerProDeclareRegDto();
		perProDeclareRegDto.setRegistSeqno(registSeqno);
		perProDeclareRegDto.setFileNo(fileNo);
		perProDeclareRegDto.setFileNum(new BigDecimal(fileNum));
		perProDeclareRegDto.setNineFile(fileSb.toString());
		perProDeclareReg001Mapper.updateFileNo(perProDeclareRegDto);

		return new AjaxResult("附件上传成功！");
 		
 	}
	public void downloadFile(Map<String, Object> map) {
//		System.out.println("downloadFile   "+map);
		
       
	}

	public void deltFile(Map<String, Object> map) {
		String registSeqno=(String) map.get("registSeqno");
		String fileNo=(String) map.get("fileNo");
		UploadFile file=uploadListener.fetch(fileNo);
		uploadListener.remove(fileNo);
		if(file!=null && file.getTargetFile().exists()){
			file.getTargetFile().delete();
		}
		PerProDeclareRegDto perProDeclareRegDto=new PerProDeclareRegDto();
		perProDeclareRegDto.setRegistSeqno(registSeqno);
		perProDeclareRegDto.setFileNo("");
		perProDeclareRegDto.setFileNum(BigDecimal.ZERO);
		perProDeclareRegDto.setNineFile("");
		perProDeclareReg001Mapper.updateFileNo(perProDeclareRegDto);
	}

	public String getFileList(Map<String, Object> map) throws IOException {
		StringBuffer res=new StringBuffer();
        if(map.get("fileNo")==null){
			return URLEncoder.encode("附件不存在", "UTF-8").replace("+","%20");
		}
		String fileNo=(String) map.get("fileNo");
		
		UploadFile file=uploadListener.fetch(fileNo);
		if(file==null||file.getTargetFile()==null){
			return URLEncoder.encode("附件丢失", "UTF-8").replace("+","%20");
		}
		res.append(file.getFileName()+":   "+file.getSize()/1000+"K<br><br>");
		try(ZipFile zipFile=new ZipFile(file.getTargetFile(),Charset.forName("GBK"))){
			for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
			    ZipEntry entry=e.nextElement();
			   if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()>0){
				   res.append(entry.getName().substring(entry.getName().lastIndexOf("/")+1)+"<br>");
			   }
			}
		}
		catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
			return URLEncoder.encode("文件未找到", "UTF-8").replace("+","%20");
		}
		String msg = URLEncoder.encode(res.toString(), "UTF-8").replace("+","%20");
		return msg;
	}
	
	/**start  中债一二期报送直连  add by  wangzehui  20201012*/
	public Map<String,Object> submitBeforeCheck(Map<String, Object> param){
        List<ExcelValidDto> result=new ArrayList<ExcelValidDto>();
 		List<PerProDeclareRegDto> proDeclareList=new ArrayList<PerProDeclareRegDto>();
		Map<String,Object> returnMap = new HashMap<>();
		if(param.containsKey("checkKeys")){
			param.put("keyList", StringUtil.splitToList(GlobalConstant.COMMA, (String)param.get("checkKeys")));
		}
		// modify by zhangliang 20210315 直连报送全量查询 start
		dealSearchParam(param);
		param.put("flag", "2");
		//add by leipeng 20200628
		if(CommonUtil.isBAmodel()){
			param.put("model_type", "01");

		}
		proDeclareList = perProDeclareReg001Mapper.selectAllData(param);
		dealDataList(proDeclareList, false);
		// modify by zhangliang 20210315 直连报送全量查询 end
		if (CollectionUtils.isEmpty(proDeclareList)) {
			returnMap.put("excelValidDtoList", Arrays.asList(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "无报送数据", null)));
			return returnMap;
		}
        //进行强制数据检查
		Map<String, PerProDeclareRegDto> repmap = proDeclareList.stream().collect(Collectors.toMap(PerProDeclareRegDto::getRegistSeqno,(p)->p,(key1, key2)->key1));
		// 检查登记表中小于今天所有未报送的数据
		if(CollectionUtils.isNotEmpty(proDeclareList)){

			List<String> keys = proDeclareList.stream().map(string -> string.getRegistSeqno()).collect(Collectors.toList());
			// 调用检查方法
			List<CheckReport001Dto> resultlist = check(null,keys);
			Map<String,CheckReport001Dto> checkmap = resultlist.stream().collect(Collectors.toMap(CheckReport001Dto::getRefseqno,(p)->p,(key1,key2)->key1));
			for(PerProDeclareRegDto declareRegDto : proDeclareList){
				if(checkmap.get(declareRegDto.getRegistSeqno())!=null){
					ExcelValidDto exc=new ExcelValidDto();
					exc.setCheckResult(checkmap.get(declareRegDto.getRegistSeqno()).getCheckResult());
					exc.setErrorInfo(checkmap.get(declareRegDto.getRegistSeqno()).getErrorInfo());
					exc.setWarnInfo(checkmap.get(declareRegDto.getRegistSeqno()).getWarnInfo());
					exc.setValidKey(checkmap.get(declareRegDto.getRegistSeqno()).getValidKey());
					result.add(exc);
				}else{
					ExcelValidDto exc=new ExcelValidDto();
					exc.setValidKey("产品代码:"+repmap.get(declareRegDto.getRegistSeqno()).getProdId());
					exc.setCheckResult("01");
					result.add(exc);
				}
			}
			returnMap.put("excelValidDtoList",result);
		}
		returnMap.put("resultList",proDeclareList);
		return returnMap;
	}

	/**
	 * @Description 检查登记表中小于今天所有未报送的数据
	 * @return
	 * @return List<CheckReport001Dto>
	 * @version 1.7 2019年6月24日
	 */
	public List<CheckReport001Dto> check(Date batchDate,List<String> keys){
		Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		// 供跑批使用
		if (StringUtil.isNotEmpty(batchDate)){
			checkdate = batchDate;
		}
		// 数据日期为系统时间
		// 删除今天所有已检查的数据
		checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品申报登记.getValue(),checkdate,keys);
		//增加例外情况过滤
		List<ChbCheckRuleout> listRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_PRD_DECLARE_SUB);
		Map<String, List<ChbCheckRuleout>> mapRuleout = listRuleout.stream().collect(
				JoyinCollectors.groupingBy(x -> x.getProdId()));

		//检查数据
		//报送前强制的错误检查
		List<CheckReport001Dto> resultList = beforeSubErrorCheck.checkProdDeclareSub(checkdate,keys,mapRuleout);

		//报错检查异常数据
		int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
		int batchLastIndex = batchCount;// 每批最后一个的下标
		for (int index = 0; index < resultList.size();) {
			if (batchLastIndex > resultList.size() - 1) {
				batchLastIndex = resultList.size();
				checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
				break;// 数据删除完毕,退出循环
			} else {
				checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
				index = batchLastIndex;// 设置下一批下标
				batchLastIndex = index + batchCount;
			}
		}
		return resultList;
	}
	
	/**
	 *  查询行内标识码
	 * @param map 入参
	 * @return 行内标识码列表
	 */
	public List<SelectOptionDto> queryProdInsIdList(Map<String,Object> map){
		return perProDeclareReg001Mapper.queryProdInsIdList(map);
	}

	/**
	 * 九大附件批量上传校验
	 * @param map  入参
	 * @return 处理结果
	 * @throws IOException 异常信息
	 */
	public AjaxResult nineFileUploadCheck(Map<String, Object> map) throws IOException {
		String fileNo=(String) map.get("param[0][fileNo]");
		String targetFileSTR=(String) map.get("param[0][targetFile][absoluteFile]");
		File file=new SafelyFile(targetFileSTR);
		// 记录错误信息
		StringBuffer fileCheckMsg = new StringBuffer();
		// 记录文件信息
		Map<String, Map<String, List<String>>> prodFileExistsMap = new HashMap<>();
		// 获取上传的压缩文件
		ZipFile zipFile=new ZipFile(new SafelyFile(targetFileSTR),Charset.forName("GBK"));
		// 记录共同文件
		Map<String, List<String>> commonFileMap = new HashMap();

		// 遍历压缩包里面所有文件
		for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();) {
			ZipEntry entry = e.nextElement();
			if (entry.getName().indexOf("/") > -1) {
				fileCheckMsg.append("包含不必要的文件或文件夹" + entry.getName() + "！");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}
			if (entry.getName().indexOf("_") < 0) {
				fileCheckMsg.append("包含不必要的文件" + entry.getName() + "！");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}

			String prodId = "";
			String folderName = "";
			// 03_产品审批文件
			// 05_理财产品托管协议总行设定
			// ZK201016_02_可行性评估报告
			// ZK201016_07_产品说明书
			int start = entry.getName().indexOf("_");
			int end = entry.getName().lastIndexOf("_");
			if (start == end) {
				start = 0;
				folderName = entry.getName().substring(start, end);
			}else{
				prodId = entry.getName().substring(0, start);
				// 文件名:01、02....
				folderName = entry.getName().substring(start + 1, end);
			}

			ChbPrdDeclareRegFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareRegFileCode.genInfoByFolderNo(folderName);
			if (chbPrdDeclareRegFileCode == null){
				fileCheckMsg.append("文件名称（" + entry.getName() + "）不符合规范。");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}
			if (chbPrdDeclareRegFileCode != null && chbPrdDeclareRegFileCode.isComm() && StringUtil.isNotEmpty(prodId)) {
				fileCheckMsg.append("文件名称（" + entry.getName() + "）属于公共文件，命令不符合规范。");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}
			if (chbPrdDeclareRegFileCode != null && !chbPrdDeclareRegFileCode.isComm() && StringUtil.isEmpty(prodId)) {
				fileCheckMsg.append("文件名称（" + entry.getName() + "）不属于公共文件，命令不符合规范。");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}
			String fileType = entry.getName().substring(entry.getName().lastIndexOf(".") + 1, entry.getName().length());
			if (chbPrdDeclareRegFileCode != null && chbPrdDeclareRegFileCode.getFileType().indexOf(fileType) < 0) {
				fileCheckMsg.append("文件名称（" + entry.getName() + "）扩展名不对。必须为以下的一种：" + chbPrdDeclareRegFileCode.getFileType() + "。");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}
			if (chbPrdDeclareRegFileCode != null && entry.getSize() > chbPrdDeclareRegFileCode.getFileSize() * 1024 * 1024) {
				fileCheckMsg.append("文件名称（" + entry.getName() + "）大小不能超过" + chbPrdDeclareRegFileCode.getFileSize() + "M。");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}
			// add by zhangliang 20210315 上传九大附件时，需要对每个附件的名称进行白名单检查 start
			String singleFileName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1, entry.getName().lastIndexOf("."));
			if (!singleFileName.matches(whitePattern)){
				fileCheckMsg.append("文件名称（" + singleFileName + "）必须填写白名单中的文字。");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}
			// add by zhangliang 20210315 上传九大附件时，需要对每个附件的名称进行白名单检查 end

			String fileName = entry.getName();
			// 带产品代码前缀的文件，放到指定产品代码下
			if (StringUtil.isNotEmpty(prodId)){
				if (prodFileExistsMap.containsKey(prodId)){
					if (prodFileExistsMap.get(prodId).containsKey(folderName)){
						List<String> folderNameList = new ArrayList<>(prodFileExistsMap.get(prodId).get(folderName));
						folderNameList.add(fileName);
						prodFileExistsMap.get(prodId).put(folderName, folderNameList);
					}
					else{
						prodFileExistsMap.get(prodId).put(folderName, Arrays.asList(fileName));
					}
				}else{
					Map<String, List<String>> prodExistsMap = new HashMap<>();
					prodExistsMap.put(folderName, Arrays.asList(fileName));
					prodFileExistsMap.put(prodId, prodExistsMap);
				}
			}
			// 公共文件，需要放到每个产品代码下
			else{
				if (commonFileMap.containsKey(folderName)){
					List<String> folderNameList = new ArrayList<>(commonFileMap.get(folderName));
					folderNameList.add(fileName);
					commonFileMap.put(folderName, folderNameList);
				}
				else{
					commonFileMap.put(folderName, Arrays.asList(fileName));
				}
			}
		}

		// 公共文件，需要放到每个产品代码下
		for (String commonFolder : commonFileMap.keySet()){
			for(String prodId : prodFileExistsMap.keySet()){
				if (prodFileExistsMap.get(prodId).containsKey(commonFolder)){
					List<String> folderNameList = new ArrayList<>(prodFileExistsMap.get(prodId).get(commonFolder));
					folderNameList.addAll(commonFileMap.get(commonFolder));
					prodFileExistsMap.get(prodId).put(commonFolder, folderNameList);
				}
				else{
					prodFileExistsMap.get(prodId).put(commonFolder, commonFileMap.get(commonFolder));
				}
			}
		}

		// 检查数据是否已存在
		Map<String, Object> pageParam = new HashMap<>();
		pageParam.put("prodIdList", prodFileExistsMap.keySet());
		pageParam.put("sendStatusList", Arrays.asList(DictCodeListExt2.报送状态_未报送.getValue()));
		pageParam.put("statusList", Arrays.asList(DictCodeListExt2.报送数据状态_修改.getValue(), DictCodeListExt2.报送数据状态_新建.getValue()));
		List<PerProDeclareRegDto> subList = perProDeclareReg001Mapper.selectAllData(pageParam);

		// 产品是否不存在或状态是删除
		List<String> folderNoList = ChbPrdDeclareRegFileCode.genFolderNoList();
		Map<String, List<PerProDeclareRegDto>> subMap = subList.stream().collect(Collectors.groupingBy(PerProDeclareRegDto::getProdId));
		for(String prodId : prodFileExistsMap.keySet()){
			if (!subMap.containsKey(prodId)){
				fileCheckMsg.append("产品代码(" + prodId + ")必须满足发送状态为未报送且数据状态为新建或修改的记录。");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}
			// 已存在附件
			if (StringUtil.isNotEmpty(subMap.get(prodId).get(0).getFileNo())){
				fileCheckMsg.append("产品代码(" + subMap.get(prodId).get(0).getProdId() + ")已存在附件。");
				fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
				continue;
			}

			// 判断是否缺少必须文件
			Map<String, List<String>> foldNameMap = prodFileExistsMap.get(prodId);
			for (String folderNo : folderNoList){
				if (!foldNameMap.containsKey(folderNo) || StringUtil.isEmptyList(foldNameMap.get(folderNo))){
					ChbPrdDeclareRegFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareRegFileCode.genInfoByFolderNo(folderNo);
					if (chbPrdDeclareRegFileCode.isNend()){
						fileCheckMsg.append("产品代码(" + prodId + ")" + folderNo + "(" + chbPrdDeclareRegFileCode.getFolderName() + ")不可为空。");
						fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
					}
				}
			}
		}

		// 返回错误信息，删除上传附件及上传记录，处理结束
		if (fileCheckMsg.length() > 0){
			if(file.exists()){
				file.delete();
			}
			uploadListener.remove(fileNo);

			fileCheckMsg.append("上传失败！");
			return new AjaxResult(fileCheckMsg.toString(), MessageType.错误消息);
		}

		return new AjaxResult("上传中...");
	}

	/**
	 * 九大附件批量上传
	 * @param map  入参
	 * @return 处理结果
	 * @throws IOException 异常信息
	 */
	public AjaxResult nineFileUpload(Map<String, Object> map) throws IOException {
		String targetFileSTR=(String) map.get("param[0][targetFile]");
		// 记录文件信息
		Map<String, Map<String, List<String>>> prodFileExistsMap = new HashMap<>();
		// 获取上传的压缩文件
		ZipFile zipFile=new ZipFile(new SafelyFile(targetFileSTR),Charset.forName("GBK"));
		// 记录共同文件
		Map<String, List<String>> commonFileMap = new HashMap();

		// 遍历压缩包里面所有文件
		for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();) {
			ZipEntry entry = e.nextElement();

			String prodId = "";
			String folderName = "";
			// 03_产品审批文件
			// 05_理财产品托管协议总行设定
			// ZK201016_02_可行性评估报告
			// ZK201016_07_产品说明书
			int start = entry.getName().indexOf("_");
			int end = entry.getName().lastIndexOf("_");
			if (start == end) {
				start = 0;
				folderName = entry.getName().substring(start, end);
			}else{
				prodId = entry.getName().substring(0, start);
				// 文件名:01、02....
				folderName = entry.getName().substring(start + 1, end);
			}

			// String fileName = entry.getName().substring(folderName.length() + 1);
			String fileName = entry.getName();
			// 带产品代码前缀的文件，放到指定产品代码下
			if (StringUtil.isNotEmpty(prodId)){
				if (prodFileExistsMap.containsKey(prodId)){
					if (prodFileExistsMap.get(prodId).containsKey(folderName)){
						List<String> folderNameList = new ArrayList<>(prodFileExistsMap.get(prodId).get(folderName));
						folderNameList.add(fileName);
						prodFileExistsMap.get(prodId).put(folderName, folderNameList);
					}
					else{
						prodFileExistsMap.get(prodId).put(folderName, Arrays.asList(fileName));
					}
				}else{
					Map<String, List<String>> prodExistsMap = new HashMap<>();
					prodExistsMap.put(folderName, Arrays.asList(fileName));
					prodFileExistsMap.put(prodId, prodExistsMap);
				}
			}
			// 公共文件，需要放到每个产品代码下
			else{
				if (commonFileMap.containsKey(folderName)){
					List<String> folderNameList = new ArrayList<>(commonFileMap.get(folderName));
					folderNameList.add(fileName);
					commonFileMap.put(folderName, folderNameList);
				}
				else{
					commonFileMap.put(folderName, Arrays.asList(fileName));
				}
			}
		}

		// 公共文件，需要放到每个产品代码下
		for (String commonFolder : commonFileMap.keySet()){
			for(String prodId : prodFileExistsMap.keySet()){
				if (prodFileExistsMap.get(prodId).containsKey(commonFolder)){
					List<String> folderNameList = new ArrayList<>(prodFileExistsMap.get(prodId).get(commonFolder));
					folderNameList.addAll(commonFileMap.get(commonFolder));
					prodFileExistsMap.get(prodId).put(commonFolder, folderNameList);
				}
				else{
					prodFileExistsMap.get(prodId).put(commonFolder, commonFileMap.get(commonFolder));
				}
			}
		}

		// 本地路径
		String localPath = CacheUtil.getSysParam("upload_file_path") + File.separator;
		// 第一步：解压上传的压缩包
		ZipUtil.unzip(zipFile, new SafelyFile(localPath + "nineAttachFile"));

		// 第二步：循环产品代码生成需要的文件及文件夹
		for (String prodId : prodFileExistsMap.keySet()){
			Map<String, List<String>> foldNameMap = prodFileExistsMap.get(prodId);
			List<File> zipFiles = new ArrayList<>();
			for (String folderName : foldNameMap.keySet()){
				File folder = new SafelyFile(localPath + "nineAttachFile" + File.separator + folderName);
				//  生成文件夹
				if (!folder.exists()){
					folder.mkdir();
				}
				else{
					// 删除文件夹下所有文件
					this.deleteFile(folder, false);
				}
				// 解压的文件复制到各文件夹下
				for (String sonFile : foldNameMap.get(folderName)){
					FileUtil.copyFile(new SafelyFile(localPath + "nineAttachFile" + File.separator + sonFile), folder);
				}
				zipFiles.add(folder);
			}
			// 第三步：根据产品代码生成压缩包
			File [] zipFilesArr = zipFiles.toArray(new SafelyFile[zipFiles.size()]);
			ZipUtil.zip(new SafelyFile(localPath + prodId  + ChbConstant.FILE_ZIP), CharsetUtil.defaultCharset(), true, null, zipFilesArr);
		}

		// 第四步：刪除第一步和第二步生成的文件
		this.deleteFile(new SafelyFile(localPath + "nineAttachFile" + File.separator), true);

		// 第五步：记录文件路径
		for (String prodId : prodFileExistsMap.keySet()){
			// 拼接中债直连报文所需要的文件信息
			StringBuffer fileSb = new StringBuffer();
			BigDecimal fileNum = BigDecimal.ZERO;
			for (String foldName : prodFileExistsMap.get(prodId).keySet()){
				fileSb.append(foldName);
				fileSb.append(GlobalConstant.COMMA);
				List<String> fileInfoList = prodFileExistsMap.get(prodId).get(foldName);
				for (String fileInfo : fileInfoList){
					fileSb.append(fileInfo);
					fileSb.append(GlobalConstant.COMMA);
				}
				fileSb = new StringBuffer(fileSb.substring(0, fileSb.length() - GlobalConstant.COMMA.length()));
				fileSb.append(GlobalConstant.SEMICOLON);

				fileNum = fileNum.add(BigDecimal.ONE);
			}
			fileSb = new StringBuffer(fileSb.substring(0, fileSb.length() - GlobalConstant.SEMICOLON.length()));

			// 定义文件代码
			String sonFileNo = IDGenerator.generate(32);
			// 记录文件信息
			PerProDeclareRegDto  perProDeclareRegDto=new PerProDeclareRegDto();
			perProDeclareRegDto.setProdId(prodId);
			perProDeclareRegDto.setFileNo(sonFileNo);
			perProDeclareRegDto.setFileNum(fileNum);
			perProDeclareRegDto.setNineFile(fileSb.toString());
			perProDeclareReg001Mapper.updateFileNo(perProDeclareRegDto);
			// 记录文件关联表
			UploadFile fileInfo = new UploadFile();
			fileInfo.setTargetFile(new SafelyFile(localPath + prodId  + ChbConstant.FILE_ZIP));
			fileInfo.setFileNo(sonFileNo);
			uploadListener.upload(fileInfo);
		}

		return new AjaxResult("附件上传成功！");
	}

	/**
	 * 指定文件夹删除下面所有内容
	 * @param file 文件
	 * @param flag 是否删除根目录
	 */
	public static void deleteFile(File file, boolean flag){
		//判断文件不为null或文件目录存在
		if (file == null || !file.exists()){
			return;
		}
		//取得这个目录下的所有子文件对象
		File[] files = file.listFiles();
		//遍历该目录下的文件对象
		for (File f: files){
			//打印文件名
			String name = file.getName();
			//判断子目录是否存在子目录,如果是文件则删除
			if (f.isDirectory()){
				deleteFile(f, true);
			}else {
				f.delete();
			}
		}
		// 是否删除根目录
		if (flag){
			file.delete();
		}
	}
	
	/**
	 * 附件批量上传-产品版本（徽商合并过来）
	 * @param file 文件列表
	 */
	public AjaxResult nineFileUploadBatch(List<Map<String, Object>> fileList) throws ZipException, IOException{
		StringBuffer fileCheckMsg = new StringBuffer();
		for(Map<String, Object> map:fileList){
			String srcName=(String) map.get("srcName");
			String fileNo=(String) map.get("fileNo");
			String fileProdId=srcName.substring(0, srcName.lastIndexOf("."));
			String targetFileSTR=(String) map.get("targetFileSTR");
			List<String> prodidList=(List<String>) map.get("prodList");
//			String registSeqno=(String) map.get("registSeqno");
//			String prodId=(String) map.get("prodId");
			File file=new File(targetFileSTR);
			StringBuffer fileCheckMsg1 = new StringBuffer();
			//校验文件名与所选产品代码是否匹配
			Boolean flag=false;
			for(String prodid :prodidList){
				if(fileProdId.equals(prodid)){
					flag=true;
				}
			}
			if(!flag){
				fileCheckMsg1.append("文件名"+srcName+"与所选产品代码不匹配！");
				fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
			}
			int fileNum=0;
			// 记录文件信息
			Map<String, List<String>> fileExistsMap = new HashMap<>();
			try (
					ZipFile zipFile=new ZipFile(new File(targetFileSTR),Charset.forName("GBK"));
				){
				List<String> folderNoList = ChbPrdDeclareRegFileCode.genFolderNoList();
				for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
					ZipEntry entry=e.nextElement();
					//    aaa.doc:表示文件
					if (entry.getName().indexOf("/") < 0){
						fileCheckMsg1.append("包含不必要的文件或文件夹"+ entry.getName() + "！");
						fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
						continue;
					}
						//    09/bbb/aaa.doc:表示文件夹下存在文件夹
					if (entry.getName().indexOf("/") != entry.getName().lastIndexOf("/")){
						fileCheckMsg1.append("包含不必要的文件或文件夹"+ entry.getName() + "！");
						fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
						continue;
					}

					if (entry.getName().indexOf(",") > 0){
						fileCheckMsg1.append("文件中不得包含,字符"+ entry.getName() + "！");
						fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
						continue;
					}
					//    09/:表示文件夹
					String folderName = entry.getName().substring(0, entry.getName().indexOf("/"));
					if (entry.getName().indexOf("/") + 1== entry.getName().length()){
						if (!folderNoList.contains(folderName)){
							fileCheckMsg1.append("包含不必要的文件夹"+ folderName + "！");
							fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
						}
						continue;
					}
					//    09/aaa.doc:表示文件夹下文件
					if (entry.getName().indexOf("/") == entry.getName().lastIndexOf("/")){
						ChbPrdDeclareRegFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareRegFileCode.genInfoByFolderNo(folderName);
						if (chbPrdDeclareRegFileCode != null &&(
								entry.getSize() > chbPrdDeclareRegFileCode.getFileSize() * 1024 * 1024||  entry.getSize() == 0)){
							fileCheckMsg1.append("文件（" + entry.getName() + "）大小超过" + chbPrdDeclareRegFileCode.getFileSize() + "M或为0字节文件");
							fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
						}
						if(entry.getName().lastIndexOf(".")>=0){
							String fileType = entry.getName().substring(entry.getName().lastIndexOf(".") + 1, entry.getName().length());
							if (chbPrdDeclareRegFileCode != null && chbPrdDeclareRegFileCode.getFileType().indexOf(fileType) < 0){
								fileCheckMsg1.append("文件（" + entry.getName() + "）扩展名不对。必须为以下的一种：" + chbPrdDeclareRegFileCode.getFileType() + "。" );
								fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
							}
						}else{
							fileCheckMsg1.append("文件（" + entry.getName() + "）扩展名不对。必须为以下的一种：" + chbPrdDeclareRegFileCode.getFileType() + "。" );
							fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
							continue;
						}
						// add by zhangliang 20210315 上传九大附件时，需要对每个附件的名称进行白名单检查 start
						String fileName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1, entry.getName().lastIndexOf("."));
						if (!fileName.matches(whitePattern)){
							fileCheckMsg1.append("文件（" + fileName + "）必须填写白名单中的文字。");
							fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
						}
						// add by zhangliang 20210315 上传九大附件时，需要对每个附件的名称进行白名单检查 end
						if (fileExistsMap.containsKey(folderName)){
							List<String> folderNameList = new ArrayList<>(fileExistsMap.get(folderName));
							folderNameList.add(entry.getName().substring(folderName.length() + 1));
							fileExistsMap.put(folderName, folderNameList);
						}
						else{
							fileExistsMap.put(folderName, Arrays.asList(entry.getName().substring(folderName.length() + 1)));
						}
					}
					// 统计文件个数
					if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()>0){
						fileNum++;
					}
				}
				for (String folderNo : folderNoList){
					if (!fileExistsMap.containsKey(folderNo) || StringUtil.isEmptyList(fileExistsMap.get(folderNo))){
						ChbPrdDeclareRegFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareRegFileCode.genInfoByFolderNo(folderNo);
						if (chbPrdDeclareRegFileCode.isNend()){
							fileCheckMsg1.append(chbPrdDeclareRegFileCode.getFolderName() + "不可为空。");
							fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
						}
					}
				}

			} catch (Exception e) {
				throw new BusinessException(e);
			}
			if(fileCheckMsg1.length() > 0){
				if(file.exists()){
					file.delete();
				}
				uploadListener.remove(fileNo);
				fileCheckMsg.append(fileCheckMsg1);
			}
		}
		if(fileCheckMsg.length()>0){
			fileCheckMsg.append("上传失败！");
			return new AjaxResult(fileCheckMsg.toString(), MessageType.错误消息);
		}
		//校验通过，保存文件
		for(Map<String, Object> filemap:fileList){
			String srcName=(String) filemap.get("srcName");
			
			String fileNo=(String) filemap.get("fileNo");
			String fileProdId=srcName.substring(0, srcName.lastIndexOf("."));
			String targetFileSTR=(String) filemap.get("targetFileSTR");
//			String registSeqno=(String) map.get("registSeqno");
//			String prodId=(String) map.get("prodId");
			int fileNum=0;
			// 记录文件信息
			Map<String, List<String>> fileExistsMap1 = new HashMap<>();
			try (
				ZipFile zipFile=new ZipFile(new File(targetFileSTR),Charset.forName("GBK"));
			){
				List<String> folderNoList1 = ChbPrdDeclareRegFileCode.genFolderNoList();
				for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
					ZipEntry entry=e.nextElement();
					//    09/:表示文件夹
					String folderName = entry.getName().substring(0, entry.getName().indexOf("/"));
					//    09/aaa.doc:表示文件夹下文件
					if (entry.getName().indexOf("/") == entry.getName().lastIndexOf("/")){
						ChbPrdDeclareRegFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareRegFileCode.genInfoByFolderNo(folderName);
						if (fileExistsMap1.containsKey(folderName)){
							List<String> folderNameList = new ArrayList<>(fileExistsMap1.get(folderName));
							folderNameList.add(entry.getName().substring(folderName.length() + 1));
							fileExistsMap1.put(folderName, folderNameList);
						}
						else{
							fileExistsMap1.put(folderName, Arrays.asList(entry.getName().substring(folderName.length() + 1)));
						}
					}
					// 统计文件个数
					//修改为统计压缩包中文件夹个数  add by tuqirong 2022-01-26 start
					if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()==0){
						fileNum++;
					}
					//修改为统计压缩包中文件夹个数  add by tuqirong 2022-01-26 end
				}
				for (String folderNo : folderNoList1){
					if (!fileExistsMap1.containsKey(folderNo) || StringUtil.isEmptyList(fileExistsMap1.get(folderNo))){
						ChbPrdDeclareRegFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareRegFileCode.genInfoByFolderNo(folderNo);
						if (chbPrdDeclareRegFileCode.isNend()){
							fileCheckMsg.append(chbPrdDeclareRegFileCode.getFolderName() + "不可为空。");
							fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
						}
					}
				}

			} catch (Exception e) {
				throw new BusinessException(e);
			}
			StringBuffer fileSb = new StringBuffer();
			for (String key : fileExistsMap1.keySet()){
				fileSb.append(key);
				fileSb.append(GlobalConstant.COMMA);
				List<String> fileInfoList = fileExistsMap1.get(key);
				for (String fileInfo : fileInfoList){
					/**ZGBSSE-854  【中债直连】：申报登记，9大附件上传入库信息多余逗号导致直连报文有误***/
					if(StringUtil.isNotEmpty(fileInfo)){
						fileSb.append(fileInfo);
						fileSb.append(GlobalConstant.COMMA);
					}
				}
				fileSb = new StringBuffer(fileSb.substring(0, fileSb.length() - GlobalConstant.COMMA.length()));
				fileSb.append(GlobalConstant.SEMICOLON);
			}
			fileSb = new StringBuffer(fileSb.substring(0, fileSb.length() - GlobalConstant.SEMICOLON.length()));
			PerProDeclareRegDto  perProDeclareRegDto=new PerProDeclareRegDto();
//			perProDeclareRegDto.setRegistSeqno(registSeqno);
			perProDeclareRegDto.setProdId(fileProdId);
			perProDeclareRegDto.setFileNo(fileNo);
			perProDeclareRegDto.setFileNum(new BigDecimal(fileNum));
			perProDeclareRegDto.setNineFile(fileSb.toString());

			// 先查历史数据文件  将 历史文件删除
			String path = "";
			if(StringUtils.isNotEmpty(fileProdId)) {
				path = perProDeclareReg001Mapper.selectFileInfo(perProDeclareRegDto);
			}
			perProDeclareReg001Mapper.updateFileNo(perProDeclareRegDto);

			// 如果存在历史文件  将文件删除
			if(StringUtils.isNotEmpty(fileProdId) && StringUtil.isNotEmpty(path)){
				File file = new SafelyFile(path);
				if(file.exists()){
					file.delete();
				}
			}
		}
		return new AjaxResult("附件上传成功！");
	}
}
