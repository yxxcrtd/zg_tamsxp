package com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubnew.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileHelper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dao.PbcPrdBaseinfoSubDatMapper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubnew.dao.PbcPrdBaseinfoSubNewMapper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubnew.dto.PbcPrdBaseinfoSubNew;
import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.service.PbcPrdCashRaiseSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.dao.PbcInitProdRaisingSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.dto.PbcInitProdRaisingSubVo;
import com.joyintech.tams.svreport.pbc.common.enums.PbcDictCodeEnums;
import com.joyintech.tams.svreport.pbc.datacheck.beforesub.service.PbcBeforeSubCheck;
import com.joyintech.tams.svreport.pbc.datacheck.beforesub.service.PbcBeforeSubErrorCheck;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 	产品基本信息service
 * @author liuyu
 *
 */
@Service
@Transactional
public class PbcPrdBaseinfoSubNew001Service extends CommonExportService<PbcPrdBaseinfoSubNew> {
	/**
	 * 产品基本信息mapper
	 */
	@Autowired
	private PbcPrdBaseinfoSubNewMapper pbcPrdBaseinfoSubNewMapper;

	@Autowired
	private PbcPrdBaseinfoSubDatMapper pbcPrdBaseinfoSubDatMapper;
	@Autowired
    private CheckReport001Mapper checkReport001Mapper;
	 @Autowired
    private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
    @Autowired
    private PbcBeforeSubErrorCheck pbcBeforeSubErrorCheck;
    @Autowired
    private PbcBeforeSubCheck pbcBeforeSubCheck;

    @Autowired
    private PbcInitProdRaisingSubMapper pbcInitProdRaisingSubMapper;

    @Autowired
	private PbcPrdCashRaiseSubService001 pbcPrdCashRaiseSubService001;

    private DecimalFormat df = new DecimalFormat("0.00000");
    private DecimalFormat dt = new DecimalFormat("0.00");
	/**
	 * 日志.
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询产品资金募集信息
	 *
	 * @param searchParam
	 * @return
	 */
	public Page<PbcPrdBaseinfoSubNew> searchPage(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		//add by leipeng 20200628
//		if(CommonUtil.isBAmodel()){
			searchParam.put("model_type", "01");
//		}
		int pagenum = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_PAGE), 1);
        int length = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
		Page<PbcPrdBaseinfoSubNew> page = queryForPage(pagenum,length,()->pbcPrdBaseinfoSubNewMapper.searchPage(searchParam));
		dealResult(page);
		return page;

	}


	/**
	 * 人行产品代码下拉框初始值
	 * @param param
	 *
	 * @return 人行产品代码下拉框初始值
	 */
	public List<SelectOptionDto> getPbcProdId(Map<String, Object> param) {
		List<PbcPrdBaseinfoSubNew> bcPrdBaseinfoSubList = pbcPrdBaseinfoSubNewMapper.queryAllPbcPrdBaseInfo(param);
		List<SelectOptionDto> resultList = new ArrayList<>();
		for (PbcPrdBaseinfoSubNew pbcPrdBaseinfoSub : bcPrdBaseinfoSubList) {
			SelectOptionDto resultDto = new SelectOptionDto();
			resultDto.setId(pbcPrdBaseinfoSub.getPbcProdId());
			resultDto.setText(pbcPrdBaseinfoSub.getProdName());
			resultList.add(resultDto);
		}
		return resultList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void beforeExprot() {
		super.beforeExprot();
		Map<String, String> cacheValue = CacheUtil.getAppCache(GlobalConstant.SYS_PARA_KEY, Map.class);
		cacheValue.put(GlobalConstant.EXPORT_EXCEL_TYPE, DictCodeList.是否_中债_否.getValue());
		CacheUtil.putAppCache(GlobalConstant.SYS_PARA_KEY, cacheValue);
	}
	/**
	 * 查询结果处理
	 * @param page
	 */
	private void dealResult(Page<PbcPrdBaseinfoSubNew> page) {
		List<PbcPrdBaseinfoSubNew> resultList = page.getRoot();
		for(PbcPrdBaseinfoSubNew result : resultList){
//			result.setStatus(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, result.getStatus()));
//			result.setSendStatus(SysDataUtil.getNameFromDataDict(DictCode.报送状态, result.getSendStatus()));
//			result.setSubmitFeedbackStatus(SysDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态,result.getSubmitFeedbackStatus()));
		    if (StringUtil.isNotEmpty(result.getIvstorientation())){
		        result.setIvstorientation(
		        		SysDataUtil.getNameFromDataDict(DictCode.新人行产品类型,result.getIvstorientation()));
		    }
            if (StringUtil.isNotEmpty(result.getPbcProdType())){
            	// modify by zhangliang 20220319 华夏个性化，“产品品种”字段应按需求默认为1-银行非保本理财 start
				result.setPbcProdType(PbcDictCodeEnums.银行非保本理财.getName());
                // result.setPbcProdType( SysDataUtil.getNameFromDataDict(DictCode.人行产品种类,result.getPbcProdType()));
				// modify by zhangliang 20220319 华夏个性化，“产品品种”字段应按需求默认为1-银行非保本理财 end
            }
            if (StringUtil.isNotEmpty(result.getCustomerType())){
                result.setCustomerType( SysDataUtil.getNameFromDataDict(DictCode.人行客户类型,result.getCustomerType()));
            }
            if (StringUtil.isNotEmpty(result.getRaiseType())){
			result.setRaiseType( SysDataUtil.getNameFromDataDict(DictCode.新人行募集方式,result.getRaiseType()));
            }
            if (StringUtil.isNotEmpty(result.getManageType())){
                result.setManageType( SysDataUtil.getNameFromDataDict(DictCode.人行管理类型,result.getManageType()));
            }
            if (StringUtil.isNotEmpty(result.getFiduciaryDuty())){
                result.setFiduciaryDuty( SysDataUtil.getNameFromDataDict(DictCode.人行管理方式,result.getFiduciaryDuty()));
            }
            if (StringUtil.isNotEmpty(result.getOperateMode())){
                result.setOperateMode(result.getOperateMode() + StringUtil.SPACE + SysDataUtil.getNameFromDataDict(DictCode.新人行产品运作模式,result.getOperateMode()));
            }
            if (StringUtil.isNotEmpty(result.getContMode())){
                result.setContMode(SysDataUtil.getNameFromDataDict(DictCode.人行合作模式,result.getContMode()));
            }
            if (StringUtil.isNotEmpty(result.getBusiModel())){
                result.setBusiModel( SysDataUtil.getNameFromDataDict(DictCode.人行业务模式,result.getBusiModel()));
            }
            if (StringUtil.isNotEmpty(result.getIncomeGuaranIdent())){
                result.setIncomeGuaranIdent(StringUtil.SPACE + SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getIncomeGuaranIdent()));
            }
            if (StringUtil.isNotEmpty(result.getPrinGuaranIdent())){
                result.setPrinGuaranIdent( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getPrinGuaranIdent()));
            }
            if (StringUtil.isNotEmpty(result.getTermRightFlag())){
                result.setTermRightFlag( SysDataUtil.getNameFromDataDict(DictCode.人行有无,result.getTermRightFlag()));
            }
            if (StringUtil.isNotEmpty(result.getCusRedeemFlag())){
                result.setCusRedeemFlag( SysDataUtil.getNameFromDataDict(DictCode.人行有无,result.getCusRedeemFlag()));
            }
            if (StringUtil.isNotEmpty(result.getCreditFlag())){
                result.setCreditFlag( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getCreditFlag()));
            }


			if (StringUtil.isNotEmpty(result.getCreditType())){
				//多个用,切开
				String [] creditModes=result.getCreditType().split(GlobalConstant.COMMA);
				String creditMode="";
				int i=0;
				for(String single:creditModes){
					i++;
					creditMode=creditMode+single+"-"+SysDataUtil.getNameFromDataDict(DictCode.新人行增信机构类型,single);
					if(i<creditModes.length){
						creditMode=creditMode+GlobalConstant.COMMA;
					}
				}
				result.setCreditType(creditMode);
			}

            if (StringUtil.isNotEmpty(result.getCreditMode())){
            	//多个用,切开
            	String [] creditModes=result.getCreditMode().split(GlobalConstant.COMMA);
            	String creditMode="";
            	int i=0;
            	for(String single:creditModes){
            		i++;
            		creditMode=creditMode+single+"-"+SysDataUtil.getNameFromDataDict(DictCode.人行增信形式,single);
            		if(i<creditModes.length){
            			creditMode=creditMode+GlobalConstant.COMMA;
            		}
            	}
                result.setCreditMode(creditMode);
            }
            if (StringUtil.isNotEmpty(result.getIsConstracte())){
                result.setIsConstracte( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getIsConstracte()));
            }
            if (StringUtil.isNotEmpty(result.getTransferType())){
                result.setTransferType( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getTransferType()));
            }
            if (StringUtil.isNotEmpty(result.getIdentiFlag())){
                result.setIdentiFlag( SysDataUtil.getNameFromDataDict(DictCode.人行理财产品发起机构标识,result.getIdentiFlag()));
            }
            if(result.getOutDepCtry()!=null){
				List<String> outDepCtrys=StringUtil.splitToList(GlobalConstant.COMMA, result.getOutDepCtry());
				result.setOutDepCtry("");
				for(String outDepCtry : outDepCtrys){
					result.setOutDepCtry(result.getOutDepCtry()+","+outDepCtry+" "+SvrDataUtil.getNameFromDataDict(DictCode.所属国家或地区, outDepCtry));
				}
				result.setOutDepCtry(result.getOutDepCtry().substring(1));
			}
			// 境内托管机构名称
            if(result.getInDepName()!=null){
                result.setInDepName(result.getInDepName().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.境内托管机构名称, result.getInDepName()));
            }
			if (StringUtil.isNotEmpty(result.getCrossBorderFlag())){
				result.setCrossBorderFlag( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getCrossBorderFlag()));
			}
			if (StringUtil.isNotEmpty(result.getZjxtFlag())){
				result.setZjxtFlag( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getZjxtFlag()));
			}
		}
	}




	private void dealResult(List<PbcPrdBaseinfoSubNew> listDetail) {
		int count = 1;
		for(PbcPrdBaseinfoSubNew result : listDetail){
			result.setNum(count++);
		    if (StringUtil.isNotEmpty(result.getIvstorientation())){
		        result.setIvstorientation(result.getIvstorientation()+"-"+
		        		SysDataUtil.getNameFromDataDict(DictCode.新人行产品类型,result.getIvstorientation()));
		    }
            if (StringUtil.isNotEmpty(result.getPbcProdType())){
                result.setPbcProdType( result.getPbcProdType() + "-" + "银行非保本理财");
//						SysDataUtil.getNameFromDataDict(DictCode.人行产品种类,result.getPbcProdType()));
            }
            if (StringUtil.isNotEmpty(result.getCustomerType())){
                result.setCustomerType( result.getCustomerType() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行客户类型,result.getCustomerType()));
            }
            if (StringUtil.isNotEmpty(result.getRaiseType())){
			result.setRaiseType( result.getRaiseType() + "-" +
					SysDataUtil.getNameFromDataDict(DictCode.新人行募集方式,result.getRaiseType()));
            }
            if (StringUtil.isNotEmpty(result.getManageType())){
                result.setManageType( result.getManageType() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行管理类型,result.getManageType()));
            }
            if (StringUtil.isNotEmpty(result.getFiduciaryDuty())){
                result.setFiduciaryDuty( result.getFiduciaryDuty() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行管理方式,result.getFiduciaryDuty()));
            }
            if (StringUtil.isNotEmpty(result.getOperateMode())){
                result.setOperateMode(result.getOperateMode() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.新人行产品运作模式,result.getOperateMode()));
            }
            if (StringUtil.isNotEmpty(result.getContMode())){
                result.setContMode(result.getContMode() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行合作模式,result.getContMode()));
            }
            if (StringUtil.isNotEmpty(result.getBusiModel())){
                result.setBusiModel( result.getBusiModel() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行业务模式,result.getBusiModel()));
            }


            if (StringUtil.isNotEmpty(result.getIncomeGuaranIdent())){
                result.setIncomeGuaranIdent(result.getIncomeGuaranIdent() + "-" +
						StringUtil.SPACE + SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getIncomeGuaranIdent()));
            }
            if (StringUtil.isNotEmpty(result.getPrinGuaranIdent())){
                result.setPrinGuaranIdent( result.getPrinGuaranIdent() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getPrinGuaranIdent()));
            }
			if (StringUtil.isNotEmpty(result.getIsMarketFund())){
				result.setIsMarketFund( result.getIsMarketFund() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getIsMarketFund()));
			}
            if (StringUtil.isNotEmpty(result.getTermRightFlag())){
                result.setTermRightFlag( result.getTermRightFlag() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行有无,result.getTermRightFlag()));
            }
            if (StringUtil.isNotEmpty(result.getCusRedeemFlag())){
                result.setCusRedeemFlag( result.getCusRedeemFlag() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行有无,result.getCusRedeemFlag()));
            }
            if (StringUtil.isNotEmpty(result.getCreditFlag())){
                result.setCreditFlag( result.getCreditFlag() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getCreditFlag()));
            }
			if (StringUtil.isNotEmpty(result.getCreditType())){
				//多个用,切开
				String [] creditModes=result.getCreditType().split(GlobalConstant.COMMA);
				String creditMode="";
				int i=0;
				for(String single:creditModes){
					i++;
					creditMode=creditMode+ single + "-" +SysDataUtil.getNameFromDataDict(DictCode.新人行增信机构类型,single);
					if(i<creditModes.length){
						creditMode= creditMode+GlobalConstant.COMMA;
					}
				}
				result.setCreditType(creditMode);
			}
            if (StringUtil.isNotEmpty(result.getCreditMode())){
            	//多个用,切开
            	String [] creditModes=result.getCreditMode().split(GlobalConstant.COMMA);
            	String creditMode="";
            	int i=0;
            	for(String single:creditModes){
            		i++;
            		creditMode=creditMode+ single + "-" +SysDataUtil.getNameFromDataDict(DictCode.人行增信形式,single);
            		if(i<creditModes.length){
            			creditMode= creditMode+GlobalConstant.COMMA;
            		}
            	}
                result.setCreditMode(creditMode);
            }
            if (StringUtil.isNotEmpty(result.getIsConstracte())){
                result.setIsConstracte(result.getIsConstracte() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getIsConstracte()));
            }
            if (StringUtil.isNotEmpty(result.getTransferType())){
                result.setTransferType(result.getTransferType() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getTransferType()));
            }
            if (StringUtil.isNotEmpty(result.getIdentiFlag())){
                result.setIdentiFlag( result.getIdentiFlag() + "-" +
						SysDataUtil.getNameFromDataDict(DictCode.人行理财产品发起机构标识,result.getIdentiFlag()));
            }
            if(result.getOutDepCtry()!=null){
				List<String> outDepCtrys=StringUtil.splitToList(GlobalConstant.COMMA, result.getOutDepCtry());
				result.setOutDepCtry("");
				for(String outDepCtry : outDepCtrys){
					result.setOutDepCtry(result.getOutDepCtry()+","+ outDepCtry);
				}
				result.setOutDepCtry(result.getOutDepCtry().substring(1));
			}
			// 境内托管机构名称
            if(result.getInDepName()!=null){
                result.setInDepName(result.getInDepName().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.境内托管机构名称, result.getInDepName()));
            }
            //募集资金币种
            if (StringUtil.isNotEmpty(result.getRaiseCcy())){
            	String raiseCcyStr= getRaiseCcy(result.getRaiseCcy());
            	result.setRaiseCcy(result.getRaiseCcy()+" "+raiseCcyStr);
            }
            //兑付本金币种
            if (StringUtil.isNotEmpty(result.getCashPrinCcy())){
            	String raiseCcyStr= getRaiseCcy(result.getCashPrinCcy());
            	result.setCashPrinCcy(result.getCashPrinCcy()+" "+raiseCcyStr);
            }
            //兑付收益币种
            if (StringUtil.isNotEmpty(result.getCashProfCcy())){
            	String raiseCcyStr= getRaiseCcy(result.getCashProfCcy());
            	result.setCashProfCcy(result.getCashProfCcy()+" "+raiseCcyStr);
            }
            //预计客户最高年收益率
            if (result.getHighYieldIssue() != null && BigDecimal.ZERO.compareTo(result.getHighYieldIssue()) != 0) {
            	result.setHighYieldIssueToString(df.format(result.getHighYieldIssue()));
            }
            //预计客户最低年收益率
            if (result.getLowYieldIssue() != null && BigDecimal.ZERO.compareTo(result.getLowYieldIssue()) != 0) {
            	result.setLowYieldIssueToString(df.format(result.getLowYieldIssue()));
            }
            
            //日期转String，用于excel数据填充
            if (StringUtil.isNotEmpty(result.getRaiseEnddate())){
            	String date= DateUtil.format(result.getRaiseEnddate());
            	result.setRaiseEnddate01(date);
            }
            if (StringUtil.isNotEmpty(result.getRaiseVdate())){
            	String date= DateUtil.format(result.getRaiseVdate());
            	result.setRaiseVdate01(date);
            }
            //产品预计终止日期
            if (StringUtil.isNotEmpty(result.getMdate())){
            	String date= DateUtil.format(result.getMdate());
            	result.setMdate01(date);
            }
            //产品起始日期
            if (StringUtil.isNotEmpty(result.getVdate())){
            	String date= DateUtil.format(result.getVdate());
            	result.setVdate01(date);
            }
			if (StringUtil.isNotEmpty(result.getCrossBorderFlag())){
				result.setCrossBorderFlag(result.getCrossBorderFlag() + "-" + SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getCrossBorderFlag()));
			}
			if (StringUtil.isNotEmpty(result.getZjxtFlag())){
				result.setZjxtFlag(result.getZjxtFlag() + "-" + SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getZjxtFlag()));
			}
		}
	}

	public String getRaiseCcy(String raiseCcy){
		String ccyStr="";
		if(raiseCcy.indexOf(",") > 0){
        	//获取募集币种，以，分割
        	 String[] strArr = raiseCcy.split(",");
        	 for (int i = 0; i < strArr.length; i++) {

        		 String ccy=SysDataUtil.getNameFromDataDict(DictCode.币种_中债,strArr[i]);
        		 //拼接币种，使用&分隔
        		 if(i==0){
        			 ccyStr=ccy;
        		 }else{
        			 ccyStr="&"+ccy;
        		 }
             }
        }else{
        	ccyStr=SysDataUtil.getNameFromDataDict(DictCode.币种_中债,raiseCcy);
        }
		return ccyStr;
	}
	
	/*public String dateString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(date);
		return dateString;
	}*/

	/**
	 * 查询参数处理
	 * @param searchParam
	 */
	private void deaSearchParam(Map<String, Object> searchParam) {
		if(searchParam.containsKey("keyList")){
			searchParam.put("keyList",
		            StringUtil.splitToList(",",(String) searchParam.get("keyList")));
		}
		searchParam.put("statusList",
	            StringUtil.splitToList(",",(String) searchParam.get("status")));
		searchParam.put("sendStatusList",
	            StringUtil.splitToList(",",(String) searchParam.get("sendStatus")));
		searchParam.put("submitFeedbackStatusList",
	            StringUtil.splitToList(",",(String) searchParam.get("submitFeedbackStatus")));
		searchParam.put("submitFeedbackStatusListExp",
				StringUtil.splitToList(",",(String) searchParam.get("submitFeedbackStatusExp")));
		searchParam.put("operateModeList",
	            StringUtil.splitToList(",",(String) searchParam.get("operateMode")));
		searchParam.put("ivstorientationList",
	            StringUtil.splitToList(",",(String) searchParam.get("ivstorientation")));
        if (StringUtil.isNotEmpty(searchParam.get("prodId"))){
            searchParam.put("prodIdList",
                    StringUtil.splitToList(",",(String) searchParam.get("prodId")));
        } else {
            searchParam.put("prodIdList",null);
        }
        if (StringUtil.isNotEmpty(searchParam.get("pbcProdId") )){
            searchParam.put("pbcProdIdList",
                    StringUtil.splitToList(",",(String) searchParam.get("pbcProdId")));
        } else {
            searchParam.put("pbcProdIdList",null);
        }
        if (StringUtil.isNotEmpty(searchParam.get("pbcAccountId"))){
            searchParam.put("pbcAccountIdList",
                    StringUtil.splitToList(",",(String) searchParam.get("pbcAccountId")));
        } else {
            searchParam.put("pbcAccountIdList",null);
        }
	}

	/**
	 * 设置导出文件参数
	 */
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		exportParam.config(
	            "/com/joyintech/tams/svreport/pbc/classic/prdbaseinfosubnew/conf/export_conf.xml")
	            .dataSource(DataSourceType.MASERT);
	}
	/**
	 * 查询要导出的数据
	 */
	@Override
	public Page<PbcPrdBaseinfoSubNew> selectPageData(Map<String, Object> selectParam) {
			return searchPage(selectParam);
	}

	/**
	 * 手工处理
	 * @param updateList
	 */
	public void doManul(Map<String,Object> map) {
		logger.debug("手工处理开始:------------------------------"+new Date());
		String flag = map.get("flag").toString();
		List<PbcPrdBaseinfoSubNew> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的手工处理
			try {
				infoList = JsonUtils.json2List(JsonUtils.object2Json(map.get("infoList")),PbcPrdBaseinfoSubNew.class);
			} catch (IOException e) {
				e.printStackTrace();
				logger.debug("数据转换异常",e.getMessage(),e);
				return ;
			}
		}else{
			Map<String,Object> pageParam;
			if("dat".equals(flag)){
				infoList = pbcPrdBaseinfoSubNewMapper.searchPage(map);
			}else{
				pageParam = RequestUtil.getPageParam();
				deaSearchParam(pageParam);
				infoList = pbcPrdBaseinfoSubNewMapper.searchPage(pageParam);
			}

		}
		// His插入
		Iterator<PbcPrdBaseinfoSubNew> it = infoList.iterator();
		while(it.hasNext()){
			PbcPrdBaseinfoSubNew info = it.next();
			if(SysDataUtil.getNameFromDataDict(DictCode.报送状态, "03").equals(info.getSendStatus())){
				it.remove();
			}
		}
		if(infoList.isEmpty())
			return ;
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		map.put("updateStatus",DictCodeListExt2.报送状态_手工处理.getValue());
		map.put("sendStatus",DictCodeListExt2.报送状态_未报送.getValue());
		map.put("zipStatus", DictCodeListExt3.zip包状态_已使用.getValue());
		map.put("returnStatus", DictCodeListExt2.人行报送反馈状态_人行通过.getValue());
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcPrdBaseinfoSubNew> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				// 历史表插入
				pbcPrdBaseinfoSubNewMapper.insertHisBatchBySendStatus(list1);
				// 原始表删除
				pbcPrdBaseinfoSubNewMapper.deleteBatchBySendStatus(list1);
				// 原始表更新状态
				pbcPrdBaseinfoSubNewMapper.doManul(list1,map.get("submitadd")==null?null:map.get("submitadd").toString());
				//更新DAT 表
				pbcPrdBaseinfoSubNewMapper.updateBaseSubDat(list1);

				pbcPrdBaseinfoSubNewMapper.updateZipDatStatus(map);
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcPrdBaseinfoSubNew> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			// 历史表插入
			pbcPrdBaseinfoSubNewMapper.insertHisBatchBySendStatus(list2);
			// 原始表删除
			pbcPrdBaseinfoSubNewMapper.deleteBatchBySendStatus(list2);
			// 原始表更新状态
			pbcPrdBaseinfoSubNewMapper.doManul(list2,map.get("submitadd")==null?null:map.get("submitadd").toString());
			//更新DAT 表
			pbcPrdBaseinfoSubNewMapper.updateBaseSubDat(list2);
			//找到不包含未报送的zip数据,修改zip状态为手工处理
			pbcPrdBaseinfoSubNewMapper.updateZipDatStatus(map);

		}
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 end */
		logger.debug("手工处理结束:------------------------------"+new Date());

	}
	/**
	 * 取消手工处理
	 * @param updateList
	 */
	@SuppressWarnings("unchecked")
	public String doCancelManul(Map<String,Object> map) {
		logger.debug("取消手工处理开始:------------------------------"+new Date());
		String flag = map.get("flag").toString();
		List<PbcPrdBaseinfoSubNew> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的取消手工处理
			infoList  = (List<PbcPrdBaseinfoSubNew>) map.get("infoList");
		}else{
			// 查询方式
//			Map<String,Object> pageParam = RequestUtil.getPageParam();
//			infoList = searchPage(map).getRoot();
			deaSearchParam(map);
			infoList = pbcPrdBaseinfoSubNewMapper.searchPage(map);
		}
		if(infoList.isEmpty())
			return "success";
		//找sub表有没有未报送的数据
		List<String> unregList = pbcPrdBaseinfoSubNewMapper.selectRegistSeqnoList(infoList,"01");
		if(!CollectionUtils.isEmpty(unregList)){
			return "fail";
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;

		map.put("updateStatus",DictCodeListExt2.报送状态_未报送.getValue());
		map.put("sendStatus",DictCodeListExt2.报送状态_未报送.getValue());
		map.put("zipStatus", DictCodeListExt3.zip包状态_可编辑.getValue());

		for ( int i=0;i<grps;i++ ) {
			List<PbcPrdBaseinfoSubNew> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				// 更新状态
				pbcPrdBaseinfoSubNewMapper.cancelManul(list1);

				pbcPrdBaseinfoSubDatMapper.cancelManul(list1);
				// His表数据插入sub表
				pbcPrdBaseinfoSubNewMapper.insetBatchSubFromHisByUpdateTime(list1);
				// 删除his表中移走的数据
				pbcPrdBaseinfoSubNewMapper.deleteBatchSubFromHisByUpdateTime(list1);

				//找到不包含手工处理的zip数据,修改zip状态为未报送
				pbcPrdBaseinfoSubNewMapper.updateZipDatStatusUn(map);
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcPrdBaseinfoSubNew> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			// 更新状态
			pbcPrdBaseinfoSubNewMapper.cancelManul(list2);
			pbcPrdBaseinfoSubDatMapper.cancelManul(list2);
			// His表数据插入sub表
			pbcPrdBaseinfoSubNewMapper.insetBatchSubFromHisByUpdateTime(list2);
			// 删除his表中移走的数据
			pbcPrdBaseinfoSubNewMapper.deleteBatchSubFromHisByUpdateTime(list2);

			//找到不包含手工处理的zip数据,修改zip状态为未报送
			pbcPrdBaseinfoSubNewMapper.updateZipDatStatusUn(map);
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 end */
		logger.debug("取消手工处理结束:------------------------------"+new Date());
		return "success";
	}

    /**
     * 数据检查
     * @param checkKeys 检查数据的关键字
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> checkData(Map<String, Object> param){

        List<PbcPrdBaseinfoSubNew> equityInfoRegistList = new ArrayList<PbcPrdBaseinfoSubNew>();
        param.put("submitStatus", DictCodeListExt2.报送状态_未报送.getValue());
        param.put("deptCode", CommonUtil.getDeptCode());
        List<String> keyList = new ArrayList<String>();
        if(param.get("checkKeys")!=null){
        	keyList=StringUtil.splitToList(GlobalConstant.COMMA, (String)param.get("checkKeys"));
        	param.put("registList", keyList);
        }
        Map<String,PbcPrdBaseinfoSubNew> repmap = new HashMap<String,PbcPrdBaseinfoSubNew>();
        deaSearchParam(param);
        equityInfoRegistList= pbcPrdBaseinfoSubNewMapper.searchPage(param);
        List<String> checkKeys = new ArrayList<String>();
        List<ExcelValidDto> checkResult = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(equityInfoRegistList)){
			repmap = equityInfoRegistList.stream().collect(Collectors.toMap(PbcPrdBaseinfoSubNew::getRegistSeqno,(p)->p,(key1,key2)->key1));
			for(PbcPrdBaseinfoSubNew dto : equityInfoRegistList){
				checkKeys.add(dto.getRegistSeqno());
			}
		}else{
			if (CollectionUtils.isEmpty(equityInfoRegistList)) {
	            checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "无报送数据",null));
	            return checkResult;
	        }
		}
        // 调用检查方法
		Map<String,CheckReport001Dto> checkmap = new HashMap<String,CheckReport001Dto>();
			if(CollectionUtils.isNotEmpty(checkKeys)){
				List<CheckReport001Dto> resultlist = check(checkKeys);
				checkmap = resultlist.stream().collect(Collectors.toMap(CheckReport001Dto::getRefseqno,(p)->p,(key1,key2)->key1));
				for(String key : checkKeys){
					if(checkmap.get(key)!=null){
						ExcelValidDto exc=new ExcelValidDto();
						exc.setCheckResult(checkmap.get(key).getCheckResult());
						exc.setErrorInfo(checkmap.get(key).getErrorInfo());
						exc.setWarnInfo(checkmap.get(key).getWarnInfo());
						exc.setValidKey(checkmap.get(key).getValidKey());
						checkResult.add(exc);
					} else {
						ExcelValidDto exc=new ExcelValidDto();
						exc.setValidKey("产品代码:"+repmap.get(key).getProdId());
						exc.setCheckResult("01");
						checkResult.add(exc);
					}
				}
			}


        return checkResult;
    }
    /**
	 * @Description 检查登记表中小于今天所有未报送的数据
	 * @return
	 * @return List<CheckReport001Dto>
	 * @version 1.7 2019年6月24日
	 */
	private List<CheckReport001Dto> check(List<String> checkKeys){
		// 数据日期为系统时间
		Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		// 删除今天所有已检查的数据
		checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_新人行产品基本信息.getValue(),checkdate,checkKeys);
		//增加例外情况过滤
        List<ChbCheckRuleout> listRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.PBC_PRD_BASEINFO_SUB);
        Map<String, List<ChbCheckRuleout>> mapRuleout = listRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //检查数据
        List<CheckReport001Dto> resultList =new ArrayList<CheckReport001Dto>();
        resultList.addAll(pbcBeforeSubErrorCheck.checkPrdBaseInfoSubNew(checkdate,null,mapRuleout));
        resultList.addAll(pbcBeforeSubCheck.checkPbcProdBaseInfoSubNew(checkdate, mapRuleout));
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



	public List<PbcPrdBaseinfoSubNew> searchByCondition(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		//searchParam.put("sendStatus", DictCodeListExt2.报送状态_未报送.getValue());
		/**TISHKBS-2373  CLONE - UAT-人行产品基本信息,不勾选数据导出，导出全部数据，应该只导出有起始募集的数据***/
		/**当全选时.直接连接查询起始募集信息.如果没有.则不导出***/

		List<PbcPrdBaseinfoSubNew> reportList = pbcPrdBaseinfoSubNewMapper.searchPage(searchParam);
		return reportList;

	}


	public List<PbcPrdBaseinfoSubNew> searchByExcel(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		List<PbcPrdBaseinfoSubNew> reportList = pbcPrdBaseinfoSubNewMapper.searchPage(searchParam);
		//数据去除多法人字段
		reportList = reportList.stream().map(item -> {
			item.setBelongInstitutionCode(null);
			return item;
		}).collect(toList());
		dealResult(reportList);

		return reportList;

	}

	//导出人行Excel
	@SuppressWarnings("unchecked")
	public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam,String datas) throws IOException {

		deaSearchParam(selectParam);
    	// 导出文件
        List<File> sourceFiles = new ArrayList<>();

    	@SuppressWarnings("unchecked")
    	List<PbcPrdBaseinfoSubNew> dataList=new ArrayList<PbcPrdBaseinfoSubNew>();
    	if (datas != null){
        	dataList=JsonUtils.json2List(datas,PbcPrdBaseinfoSubNew.class);
    	}else{
    		//不选报表导出所有未报送的记录
    		dataList= searchByCondition(selectParam);

    	}

    	Map<String, PbcPrdBaseinfoSubNew> dataParam = new HashMap<String, PbcPrdBaseinfoSubNew>();
    	// 处理勾选数据,用pbcProdId做key值存入  dataParam中
    	for (int i =0 ;i < dataList.size();i++){
    		if(null!=dataParam.get(dataList.get(i).getPbcProdId())){

				String sendStatus=dataList.get(i).getSendStatus();
				String status=dataList.get(i).getSubmitFeedbackStatus();
				if(sendStatus.equals(DictCodeListExt2.报送状态_未报送.getValue())
						&& (!status.equals(DictCodeListExt2.人行报送反馈状态_发送成功.getValue())
							||!status.equals(DictCodeListExt2.人行报送反馈状态_人行通过.getValue()))){
					dataParam.put(dataList.get(i).getPbcProdId(), dataList.get(i));
				}
			}else{
					dataParam.put(dataList.get(i).getPbcProdId(), dataList.get(i));
			}

    	}
    	// excel计数器
		int count = 1;
		Format f2 = new DecimalFormat("00");
    	Map<String, Integer> nameMap = new HashMap<String, Integer>();
    	String template="";
    	//生成人行Excel
    	for(Entry<String, PbcPrdBaseinfoSubNew> dto : dataParam.entrySet()){

    		Map<String,Object> data  = new HashMap<String,Object>();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	String pbcProdId = dto.getValue().getPbcProdId();
	    	//模板名
	    	String name=dto.getValue().getPbcCode();
	    	if(StringUtils.isEmpty(name)){
	    		name = pbcProdId;
	    	}
	    	//param.put("registSeqno", dto.getRegistSeqno());
	    	param.put("pbcProdId", dto.getValue().getPbcProdIdShow());
	    	param.put("sendStatus", dto.getValue().getSendStatus());	/*报送状态*/
	    	param.put("status", dto.getValue().getStatus());			/*数据状态*/
	    	param.put("statusDate", dto.getValue().getStatusDate());
	    	deaSearchParam(param);
	    	List<PbcPrdBaseinfoSubNew> listDetail=new ArrayList<PbcPrdBaseinfoSubNew>();
	    	listDetail=pbcPrdBaseinfoSubNewMapper.searchPage(param);
	    	/**  add by   dongxiangyang 关于新字段判断逻辑(对原逻辑不影响) 20200117 start */
	    	for(PbcPrdBaseinfoSubNew  sub:listDetail){
	    		if(sub.getSubProdId()!=null){
		    		sub.setProdId(sub.getSubProdId());
	    		}
	    	}
	    	/**  add by   dongxiangyang 关于新字段判断逻辑(对原逻辑不影响) 20200117 end */
	    	dealResult(listDetail);
	    	if (!CollectionUtils.isEmpty(listDetail)) {
	            for (PbcPrdBaseinfoSubNew detail : listDetail) {
	                // 处理数据,将sheet1 产品基本信息中的明细数据存入data中
	                data.put("data", detail);
	            }
	        }

	    	List<PbcInitProdRaisingSubVo> listDetailSheet2 = new ArrayList<PbcInitProdRaisingSubVo>();
	    	//根据产品币种，判断使用人民币/外币模板
	    	if(StringUtil.isNotEmpty(dto.getValue().getRaiseCcy())&&dto.getValue().getRaiseCcy().equals("CNY")){
	    		template="PbcReport_PrdBaseinfo_CNY";
	    		listDetailSheet2 = pbcInitProdRaisingSubMapper.searchListDetailCNY(param);
	    	}else{
	    		template="PbcReport_PrdBaseinfo_Oth";
	    		listDetailSheet2 = pbcInitProdRaisingSubMapper.searchListDetailOth(param);
	    	}

	    	if (!CollectionUtils.isEmpty(listDetailSheet2)) {
	            for (PbcInitProdRaisingSubVo detailVo : listDetailSheet2) {
	                // 处理数据，将sheet2 起始募集信息中的明细数据存入data中
	            	pbcPrdCashRaiseSubService001.dealCashRaiseDateListForExport(detailVo);
	            	String code="";
	            	if(detailVo.getCode() != null){
	            		code=detailVo.getCode();
	            	}else{
	            		code="buyplace";
	            	}
	                data.put(code, detailVo);
	            }
	        }

	    	//String fileName = "PIB_"+pbcProdId+"_"+DateUtil.format(new Date(), "yyyyMMdd")+"-"+f2.format(count)+".xls";
	    	if (nameMap.get(name) != null) {
	    		nameMap.put(name, nameMap.get(name) + 1);
	    		name = name  + "(" + nameMap.get(name) + ")";
	    	} else {
	    		nameMap.put(name, 1);
	    	}
	    	String fileName = "PBI-"+name+".xls";
	    	File depositfile = TisUtil.createFile(data, "pbc/" + template, fileName);
	    	sourceFiles.add(depositfile);
	    	count++;
		}

    	 // 如果没有未报送的记录，生成空模板
		if (sourceFiles.isEmpty()){
			//String fileName = "PIB_"+DateUtil.format(new Date(), "yyyyMMdd")+"-"+f2.format(count)+".xls";
			String fileName = "产品基本信息("+DateUtil.format(new Date(), "yyyy-MM-dd")+")"+".xls";
    		Map<String,Object> data  = new HashMap<String,Object>();
			File depositfile = TisUtil.createFile(data, "pbc/" +"PbcReport_PrdBaseinfo_CNY", fileName);
			sourceFiles.add(depositfile);
		}

		File outFile = null;
		//生成压缩包
		if(sourceFiles.size() > 1) {
            String exportFilePath = SvrDataUtil.getSysParam(
                GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
            //压缩包命名规范
            String zipFileName = "PbocPrdinfo("+DateUtil.format(new Date(), "yyyy-MM-dd")+").zip";
            outFile = new SafelyFile(exportFilePath + File.separator + zipFileName);
            // 导出路径不存在则重新生成
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            try {
                ZipFileUtil.compressZip(outFile, true, sourceFiles.toArray(new SafelyFile[] {}));
            }
            catch (Exception e) {
                // 压缩文件失败后，删除所有临时文件
                for (File file : sourceFiles) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
                logger.error(e.getMessage(),e);
            }
        } else{//单个文件
            outFile = sourceFiles.get(0);
        }
		return ExportUtil.export(outFile.getName(), outFile, true);
    }


}
