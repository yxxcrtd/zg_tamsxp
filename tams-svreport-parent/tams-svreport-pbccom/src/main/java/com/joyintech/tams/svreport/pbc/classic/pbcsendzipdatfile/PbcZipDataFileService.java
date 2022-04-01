/**
 * 文件名：PbcClassicsService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V1.8
 * 修改人：meifaxin
 * 修改时间：2021年8月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V1.8
 */
 
package com.joyintech.tams.svreport.pbc.classic.pbcsendzipdatfile;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.dao.PbcSendZipDatFileMapper;
import com.joyintech.tams.common.base.dao.PbcProdAssetpoolCodeMapper;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import com.joyintech.tams.common.base.entity.PbcProdAssetpoolCode;
import com.joyintech.tams.common.base.entity.PbcProdAssetpoolCodeKey;
import com.joyintech.tams.common.base.entity.PbcSendZipDatFile;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.system.instregbankcode.dao.SysInstRegBankCode001Mapper;
import com.joyintech.tams.report.system.instregbankcode.dto.SysInstRegBankCode001Dto;
import com.joyintech.tams.svreport.pbc.assetdebtdat.dao.*;
import com.joyintech.tams.svreport.pbc.assetdebtdat.dto.*;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsUtil;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dao.PbcPrdBaseinfoSubDatMapper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dto.PbcPrdBaseinfoSubDat;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubnew.dao.PbcPrdBaseinfoSubNewMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsubdat.dao.PbcPrdBaseinfoEndSubDatMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsubdat.dto.PbcPrdBaseinfoEndSubDat;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsubnew.dao.PbcPrdBaseinfoEndSubNewMapper;
import com.joyintech.tams.svreport.pbc.common.dto.DatDownloadDto;
import com.joyintech.tams.svreport.pbc.common.util.SftpUtil;
import com.joyintech.tams.svreport.pbc.report.dao.PbcMonthReportMapper;
import com.joyintech.tams.svreport.pbc.report.dto.PbcMonthReport;
import com.joyintech.tams.svreport.pbc.report.service.PbcMonthReport001Service;
import com.joyintech.tams.systemtools.util.CommonUtil;
import groovy.util.MapEntry;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2021年8月6日
 * @see PbcZipDataFileService
 * @since 1.0
 */
@Service
public class PbcZipDataFileService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private  ChbTypeMappingExMapper chbTypeMappingExMapper;
    @Autowired
    private PbcReportProdDatMapper pbcReportProdDatMapper;
	@Autowired
	private PbcReportDebtTermDatDetailMapper pbcReportDebtTermDatDetailMapper;
	@Autowired
	private PbcSendZipDatFileMapper pbcSendZipDatFileMapper;
	@Autowired
	private PbcMonthReportMapper pbcMonthReportMapper;
    private  List<ChbTypeMappingEx> chbTypeMappingExList=null;

	@Autowired
	private SysInstRegBankCode001Mapper sysInstRegBankCode001Mapper;

	@Autowired
	private PbcPrdBaseinfoSubDatMapper pbcPrdBaseinfoSubDatMapper;

	@Autowired
	private PbcPrdBaseinfoEndSubDatMapper pbcPrdBaseinfoEndSubDatMapper;

	@Autowired
	private PbcReportAssetIncomeDatDetailMapper pbcReportAssetIncomeDatDetailMapper;

	@Autowired
	private PbcReportCompanyDatDetailMapper pbcReportCompanyDatDetailMapper;

	@Autowired
	private PbcReportAssetDebtDatDetailMapper pbcReportAssetDebtDatDetailMapper;

	@Autowired
	private PbcReportAreaDatDetailMapper pbcReportAreaDatDetailMapper;

	@Autowired
	private PbcProdAssetpoolCodeMapper pbcProdAssetpoolCodeMapper;

	@Autowired
	private PbcPrdBaseinfoSubNewMapper pbcPrdBaseinfoSubNewMapper;

	@Autowired
	private PbcPrdBaseinfoEndSubNewMapper pbcPrdBaseinfoEndSubNewMapper;

	@Autowired
	private PbcMonthReport001Service pbcMonthReport001Service;

	@Autowired
	private PbcReportAssetConfigDatMapper pbcReportAssetConfigDatMapper;

	public final static String PBC_PROD_BASEINFO_SEQCODE = "PBCPRDBASEINFO";

	/**
     * 生成数据文件
     * @see
     * @since 1.0
     */
    public AjaxResult genDataFile(Map<String,Set<String>> paramMap, Map<String, PbcSendZipDatFile> zipInfoMap) {
    	List<String> zipFileName = new ArrayList<String>();
		AjaxResult ajaxResult = new AjaxResult();
		List<ValidMessage> errorList = new ArrayList<>();
//		initChbExData();
		chbTypeMappingExList=initReportConfig();
		//去重
		Set<String> reslut = new HashSet<String>();
    	//zipDat更新数据
		List<PbcSendZipDatFile> zipDatFileList=new ArrayList<>();
		//获取压缩包传进来时候的状态
		Map<String,String> dataStatus = new HashMap<>();
		for(Map.Entry<String, PbcSendZipDatFile> entity :zipInfoMap.entrySet()){
			dataStatus.put(entity.getValue().getStatusVdate(),entity.getValue().getZipSendStatus());
		}
		List<PbcSendZipDatFile> baseZipDatFileList=new ArrayList<>();
		//pbcReport更新数据
		List<DatDownloadDto> datDownloadDtos = new ArrayList<>();
		Date date= DateUtil.parse(DateUtil.formatDateTime(new Date()));
		String statusVdate = "";
		for (Map.Entry<String, Set<String>> entry : paramMap.entrySet()) {  //循环datZipCode
			String datZipCode=entry.getKey();
			Set<String> set=entry.getValue();
			PbcSendZipDatFile pbcSendZipDatFile=zipInfoMap.get(datZipCode);
			statusVdate = pbcSendZipDatFile.getStatusVdate();
			String datZipCodePath="";
			String reportuuid=pbcSendZipDatFile.getReportuuid();
			Set<String> rst = new HashSet<String>();
			for(String datType:set) {  //生成dat文件
				if(DictCodeListExt2.人行报送明细类型_产品基本信息.getValue().equals(datType)){
					DatDownloadDto datDownloadDto = genReportPrdBaseInfoFileDat(pbcSendZipDatFile, datType);
					datDownloadDtos.add(datDownloadDto);
					if(CollectionUtil.isNotEmpty(datDownloadDto.getValidMessages())){
						errorList.addAll(datDownloadDto.getValidMessages());
						ajaxResult.setMessageType(MessageType.警告消息);
					}else{
						if(StringUtil.isNotEmpty(datDownloadDto.getUrl())){
							rst.add(datDownloadDto.getUrl());
							datZipCodePath = datDownloadDto.getUrl();
						}
					}
				}else if(DictCodeListExt2.人行报送明细类型_产品终止信息.getValue().equals(datType)){
					DatDownloadDto datDownloadDto = genReportPrdEndInfoFileDat(pbcSendZipDatFile,datType);
					datDownloadDtos.add(datDownloadDto);
					if(CollectionUtil.isNotEmpty(datDownloadDto.getValidMessages())){
						errorList.addAll(datDownloadDto.getValidMessages());
						ajaxResult.setMessageType(MessageType.警告消息);
					}else{
						if(StringUtil.isNotEmpty(datDownloadDto.getUrl())){
							rst.add(datDownloadDto.getUrl());
							datZipCodePath = datDownloadDto.getUrl();
						}
					}
				}else if (DictCodeListExt2.人行报送明细类型_募集余额报表.getValue().equals(datType)
						||DictCodeListExt2.人行报送明细类型_募集兑付报表.getValue().equals(datType)
						||DictCodeListExt2.人行报送明细类型_只数情况报表.getValue().equals(datType)
						||DictCodeListExt2.人行报送明细类型_募集余额报表分地区报表.getValue().equals(datType)
						||DictCodeListExt2.人行报送明细类型_提前及延期兑付报表.getValue().equals(datType)
						||DictCodeListExt2.人行报送明细类型_到期未兑付报表.getValue().equals(datType)
						||DictCodeListExt2.人行报送明细类型_存续产品分期限募集余额报表.getValue().equals(datType)
						||DictCodeListExt2.人行报送明细类型_新发产品分期限募集金额报表.getValue().equals(datType)
					) {
					datZipCodePath=genReport1_1_6_3_2FileDat(pbcSendZipDatFile,datType);
					rst.add(datZipCodePath);
				}else if(DictCodeListExt2.人行报送明细类型_资管产品资产负债剩余期限统计.getValue().equals(datType)){
					datZipCodePath=genReport3_3FileDat(pbcSendZipDatFile,datType);
					rst.add(datZipCodePath);
				}else if(DictCodeListExt2.人行报送明细类型_资管产品资产负债统计.getValue().equals(datType)){
					//2_1
					datZipCodePath = genReport2_1FileDat(pbcSendZipDatFile, datType);
					if(StringUtil.isNotEmpty(datZipCodePath)){
						rst.add(datZipCodePath);
					}
				}else if(DictCodeListExt2.人行报送明细类型_除回购和拆借外贷款分行业企业规模统计.getValue().equals(datType)
						||DictCodeListExt2.人行报送明细类型_企业债券分行业企业规模统计.getValue().equals(datType)){
					//2_2 2_5
					datZipCodePath = genReport2_2_2_5FileDat(pbcSendZipDatFile, datType);
					if(StringUtil.isNotEmpty(datZipCodePath)){
						rst.add(datZipCodePath);
					}
				}else if(DictCodeListExt2.人行报送明细类型_除回购和拆借外贷款分地区统计表.getValue().equals(datType)){
					//2_3
					datZipCodePath = genReport2_3FileDat(pbcSendZipDatFile, datType);
					if(StringUtil.isNotEmpty(datZipCodePath)){
						rst.add(datZipCodePath);
					}
				}else if(DictCodeListExt2.人行报送明细类型_资产收益权按基础资产投向分类统计报表.getValue().equals(datType)){
					//2_4
					datZipCodePath = genReport2_4FileDat(pbcSendZipDatFile, datType);
					if(StringUtil.isNotEmpty(datZipCodePath)){
						rst.add(datZipCodePath);
					}
				}else if(DictCodeListExt2.人行报送明细类型_资管产品资产配置情况表.getValue().equals(datType)){
					//2_3
					datZipCodePath = genReport4_1FileDat(pbcSendZipDatFile, datType);
					if(StringUtil.isNotEmpty(datZipCodePath)){
						rst.add(datZipCodePath);
					}
				}
				//添加生成更新表状态的list
				if(CollectionUtil.isEmpty(errorList)){
					if(!DictCodeListExt2.人行报送明细类型_产品基本信息.getValue().equals(datType) ||
							!DictCodeListExt2.人行报送明细类型_产品终止信息.getValue().equals(datType)){
						PbcSendZipDatFile zipDatFile = getPbcSendZipDatFile(datZipCode,SvrDataUtil.getSysParam("SVR_PBC_DAT_FILEPATH"),date,datType,reportuuid,DictCodeListExt2.人行报送类型_产品信息.getValue());
						zipDatFileList.add(zipDatFile);
					}else{
						PbcSendZipDatFile zipDatFile = getPbcSendZipDatFile(datZipCode,SvrDataUtil.getSysParam("SVR_PBC_DAT_FILEPATH"),date,pbcSendZipDatFile.getDatZipType(),reportuuid,DictCodeListExt2.人行报送类型_统计模板数据.getValue());
						baseZipDatFileList.add(zipDatFile);

					}
				}
			}
			if(CollectionUtil.isNotEmpty(errorList)){
				ajaxResult.setErrorList(errorList);
				continue;
			}
			reslut.addAll(rst);
		}
		//生成压缩包
		if(reslut.size() > 0){
			List<String> zipFileList = genCompressFile(reslut);
			zipFileName.addAll(zipFileList);
			String finalZipFile = genFinalCompressFile(zipFileName);
			ajaxResult.setData(finalZipFile);
		}
		//更新数据状态
		updateFileDatAndPbcReport(zipDatFileList,date,statusVdate,dataStatus);
		//更新产本基本和终止信息状态
		updateBaseFileDatAndPbcReport(baseZipDatFileList,datDownloadDtos);
		return ajaxResult;

    }

	private String genReport2_4FileDat(PbcSendZipDatFile pbcSendZipDatFile, String datType) {
		List<PbcReportAssetIncomeDatDetail> subList = pbcReportAssetIncomeDatDetailMapper
				.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(), datType);
		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<ChbTypeMappingEx> exList=getReportRowAndColumnNum(datType);
		for(PbcReportAssetIncomeDatDetail sub :subList){  //循环报表数据
			String propertycode=sub.getPropertycode();
			//查询属性对应的匹配关系数据
			List<ChbTypeMappingEx> propertyCodeFiledList=exList.stream().filter(
					propertyCodeEx -> propertycode.equals(propertyCodeEx.getSrcfield())).collect(Collectors.toList());
			for(ChbTypeMappingEx ex:propertyCodeFiledList) {
				Object   filedValue=null;
				String fieldName = "";
				//get[源属性1]()   取得报表的对应单元的数值
				try {
					Field field = sub.getClass().getDeclaredField(ex.getSrcfield1());
					fieldName = field.getName();
					//设置对象的访问权限，保证对private的属性的访问
					field.setAccessible(true);
					filedValue= field.get(sub);
				} catch (Exception e) {
					return null;
				}
				//数值当为空、或者0的情况下
				if(null==filedValue||filedValue.equals("0")){
					continue;
				}
				//组装数据
				LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
				data.put("date", DateUtil.getLastDayOfThisMonth(pbcSendZipDatFile.getStatusVdate()));
				data.put("issuePartyId", pbcSendZipDatFile.getIssuePartyId());
				data.put("prodVariety", pbcSendZipDatFile.getProdVariety());
				data.put("rowColumn", getRowAndColumn(ex.getObjfield1(),ex.getObjfield2()));
				data.put("filedValue", dealDataBaseForDat(filedValue,fieldName,datType));
				dataList.add(data);
			}
		}
		return PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList);
	}

	private String genReport2_3FileDat(PbcSendZipDatFile pbcSendZipDatFile, String datType) {
		List<PbcReportAreaDatDetailDto> subList = pbcReportAreaDatDetailMapper
				.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(), datType);
		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<ChbTypeMappingEx> exList=getReportRowAndColumnNum(datType);
		for(PbcReportAreaDatDetailDto sub :subList){  //循环报表数据
			String propertycode=sub.getPropertycode();
			//查询属性对应的匹配关系数据
			List<ChbTypeMappingEx> propertyCodeFiledList=exList.stream().filter(
					propertyCodeEx -> propertycode.equals(propertyCodeEx.getSrcfield())).collect(Collectors.toList());
			for(ChbTypeMappingEx ex:propertyCodeFiledList) {
				Object   filedValue=null;
				String fieldName = "";
				//get[源属性1]()   取得报表的对应单元的数值
				try {
					Field field = sub.getClass().getDeclaredField(ex.getSrcfield1());
					fieldName = field.getName();
					//设置对象的访问权限，保证对private的属性的访问
					field.setAccessible(true);
					filedValue= field.get(sub);
				} catch (Exception e) {
					return null;
				}
				//数值当为空、或者0的情况下
				if(null==filedValue||filedValue.equals("0")){
					continue;
				}
				//组装数据
				LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
				data.put("date", DateUtil.getLastDayOfThisMonth(pbcSendZipDatFile.getStatusVdate()));
				data.put("issuePartyId", pbcSendZipDatFile.getIssuePartyId());
				data.put("prodVariety", pbcSendZipDatFile.getProdVariety());
				data.put("rowColumn", getRowAndColumn(ex.getObjfield1(),ex.getObjfield2()));
				data.put("filedValue", dealDataBaseForDat(filedValue,fieldName,datType));
				dataList.add(data);
			}
		}
		return PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList);
	}

	private String genReport4_1FileDat(PbcSendZipDatFile pbcSendZipDatFile, String datType) {
		List<PbcReportAssetConfigDatDto> subList = pbcReportAssetConfigDatMapper
				.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(), datType);
		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<ChbTypeMappingEx> exList=getReportRowAndColumnNum(datType);
		for(PbcReportAssetConfigDatDto sub :subList){  //循环报表数据
			String propertycode=sub.getPropertycode();
			//查询属性对应的匹配关系数据
			List<ChbTypeMappingEx> propertyCodeFiledList=exList.stream().filter(
					propertyCodeEx -> propertycode.equals(propertyCodeEx.getSrcfield())).collect(Collectors.toList());
			for(ChbTypeMappingEx ex:propertyCodeFiledList) {
				Object   filedValue=null;
				String fieldName = "";
				//get[源属性1]()   取得报表的对应单元的数值
				try {
					Field field = sub.getClass().getDeclaredField(ex.getSrcfield1());
					fieldName = field.getName();
					//设置对象的访问权限，保证对private的属性的访问
					field.setAccessible(true);
					filedValue= field.get(sub);
				} catch (Exception e) {
					return null;
				}
				//数值当为空、或者0的情况下
				if(null==filedValue||filedValue.equals("0")){
					continue;
				}
				//组装数据
				LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
				data.put("date", DateUtil.getLastDayOfThisMonth(pbcSendZipDatFile.getStatusVdate()));
				data.put("issuePartyId", pbcSendZipDatFile.getIssuePartyId());
				data.put("prodVariety", pbcSendZipDatFile.getProdVariety());
				data.put("rowColumn", getRowAndColumn(ex.getObjfield1(),ex.getObjfield2()));
				data.put("filedValue", dealDataBaseForDat(filedValue,fieldName,datType));
				dataList.add(data);
			}
		}
		return PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList);
	}

	private String genReport2_2_2_5FileDat(PbcSendZipDatFile pbcSendZipDatFile, String datType) {
		List<PbcReportCompanyDatDetailDto> subList = pbcReportCompanyDatDetailMapper
				.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(), datType);
		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<ChbTypeMappingEx> exList=getReportRowAndColumnNum(datType);
		for(PbcReportCompanyDatDetailDto sub :subList){  //循环报表数据
			String propertycode=sub.getPropertycode();
			//查询属性对应的匹配关系数据
			List<ChbTypeMappingEx> propertyCodeFiledList=exList.stream().filter(
					propertyCodeEx -> propertycode.equals(propertyCodeEx.getSrcfield())).collect(Collectors.toList());
			for(ChbTypeMappingEx ex:propertyCodeFiledList) {
				Object   filedValue=null;
				String fieldName = "";
				//get[源属性1]()   取得报表的对应单元的数值
				try {
					Field field = sub.getClass().getDeclaredField(ex.getSrcfield1());
					fieldName = field.getName();
					//设置对象的访问权限，保证对private的属性的访问
					field.setAccessible(true);
					filedValue= field.get(sub);
				} catch (Exception e) {
					return null;
				}
				//数值当为空、或者0的情况下
				if(null==filedValue||filedValue.equals("0")){
					continue;
				}
				//组装数据
				LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
				data.put("date", DateUtil.getLastDayOfThisMonth(pbcSendZipDatFile.getStatusVdate()));
				data.put("issuePartyId", pbcSendZipDatFile.getIssuePartyId());
				data.put("prodVariety", pbcSendZipDatFile.getProdVariety());
				data.put("rowColumn", getRowAndColumn(ex.getObjfield1(),ex.getObjfield2()));
				data.put("filedValue", dealDataBaseForDat(filedValue,fieldName,datType));
				dataList.add(data);
			}
		}
		return PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList);
	}

	private String genReport2_1FileDat(PbcSendZipDatFile pbcSendZipDatFile, String datType) {
		List<PbcReportAssetDebtDatDetailDto> subList = pbcReportAssetDebtDatDetailMapper
				.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(), datType);
		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<ChbTypeMappingEx> exList=getReportRowAndColumnNum(datType);
		for(PbcReportAssetDebtDatDetailDto sub :subList){  //循环报表数据
			String propertycode=sub.getPropertycode();
			//查询属性对应的匹配关系数据
			List<ChbTypeMappingEx> propertyCodeFiledList=exList.stream().filter(
					propertyCodeEx -> propertycode.equals(propertyCodeEx.getSrcfield())).collect(Collectors.toList());
			for(ChbTypeMappingEx ex:propertyCodeFiledList) {
				Object   filedValue=null;
				String fieldName = "";
				//get[源属性1]()   取得报表的对应单元的数值
				try {
					Field field = sub.getClass().getDeclaredField(ex.getSrcfield1());
					fieldName = field.getName();
					//设置对象的访问权限，保证对private的属性的访问
					field.setAccessible(true);
					filedValue= field.get(sub);
				} catch (Exception e) {
					return null;
				}
				//数值当为空、或者0的情况下
				if(null==filedValue||filedValue.equals("0")){
					continue;
				}
				//组装数据
				LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
				data.put("date", DateUtil.getLastDayOfThisMonth(pbcSendZipDatFile.getStatusVdate()));
				data.put("issuePartyId", pbcSendZipDatFile.getIssuePartyId());
				data.put("prodVariety", pbcSendZipDatFile.getProdVariety());
				data.put("rowColumn", getRowAndColumn(ex.getObjfield1(),ex.getObjfield2()));
				data.put("filedValue", dealDataBaseForDat(filedValue,fieldName,datType));
				dataList.add(data);
			}
		}
		return PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList);
	}

	//产品基本信息dat
	private DatDownloadDto genReportPrdBaseInfoFileDat(PbcSendZipDatFile pbcSendZipDatFile, String datType) {
		DatDownloadDto datDownloadDto = new DatDownloadDto();
		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<PbcPrdBaseinfoSubDat> pbcPrdBaseinfoSubDats = pbcPrdBaseinfoSubDatMapper.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode());
		if(CollectionUtil.isEmpty(pbcPrdBaseinfoSubDats)){
			datDownloadDto.setUrl(PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList));
			return datDownloadDto;
		}
		List<String> innerProdIds = new ArrayList<>();
		for (PbcPrdBaseinfoSubDat pbcPrdBaseinfoSubDat : pbcPrdBaseinfoSubDats) {
			String prodId = pbcPrdBaseinfoSubDat.getProdId();
			if(StrUtil.isNotEmpty(prodId) && !innerProdIds.contains(prodId)){
				innerProdIds.add(prodId);
			}
		}

		List<PbcProdAssetpoolCode> pbcProdAssetpoolCodes = new ArrayList<>();
		if(CollectionUtil.isNotEmpty(innerProdIds)){
			pbcProdAssetpoolCodes = pbcProdAssetpoolCodeMapper.selectByInsideProdList(innerProdIds);
		}
		List<PbcPrdBaseinfoSubDat> pbcPrdBaseinfoSubDatsNew = new ArrayList<>();
		List<String> innerProdIdsNew = new ArrayList<>();
		String model = CacheUtil.getSysParam("PBC_REPORT_MODEL");
		logger.info("获取新人行报送模式配置参数:{}",model);
		String pbcReportModel =  StrUtil.isEmpty(model) ? "01" : model;
		List<String> noPbcIdsList = new ArrayList<>();
		Map<String,String> innerProdIdMapping = new HashMap<>();
		if("01".equals(pbcReportModel)){
			for (PbcPrdBaseinfoSubDat pbcPrdBaseinfoSubDat : pbcPrdBaseinfoSubDats) {
				Optional<PbcProdAssetpoolCode> first = pbcProdAssetpoolCodes.stream().filter(pbcProdAssetpoolCode ->
						pbcPrdBaseinfoSubDat.getProdId().equals(pbcProdAssetpoolCode.getInsideProdId())).findFirst();
				if(first.isPresent()){
					PbcProdAssetpoolCode prodAssetpoolCode = first.get();
					if(StrUtil.isNotEmpty(prodAssetpoolCode.getPbcCode())){
						innerProdIdsNew.add(pbcPrdBaseinfoSubDat.getProdId());
						pbcPrdBaseinfoSubDatsNew.add(pbcPrdBaseinfoSubDat);
						innerProdIdMapping.put(pbcPrdBaseinfoSubDat.getProdId(),prodAssetpoolCode.getPbcCode());
					}else{
						noPbcIdsList.add(pbcPrdBaseinfoSubDat.getProdId());
					}
				}else{
					noPbcIdsList.add(pbcPrdBaseinfoSubDat.getProdId());
				}
			}
			if(CollectionUtil.isNotEmpty(noPbcIdsList)){
				for (String ids : noPbcIdsList) {
					DatDownloadDto.newErrorMsg(datDownloadDto,ids);
				}
				return datDownloadDto;
			}
		}else{
			for (PbcPrdBaseinfoSubDat subDat : pbcPrdBaseinfoSubDats) {
				String prodId = subDat.getProdId();
				if(StrUtil.isNotEmpty(prodId)){
					Optional<PbcProdAssetpoolCode> first = pbcProdAssetpoolCodes.stream().filter(pbcProdAssetpoolCode ->
							prodId.equals(pbcProdAssetpoolCode.getInsideProdId())).findFirst();
					if(first.isPresent() && StrUtil.isNotEmpty(first.get().getPbcCode())){
						innerProdIdsNew.add(subDat.getProdId());
						pbcPrdBaseinfoSubDatsNew.add(subDat);
						innerProdIdMapping.put(prodId,first.get().getPbcCode());
					}else{
						if(StrUtil.isNotEmpty(subDat.getIssuePartyId())){
							SysInstRegBankCode001Dto sysInstRegBankCode001Dto = sysInstRegBankCode001Mapper.selectRegistBankCode(subDat.getIssuePartyId());
							logger.info("查询的机构代码信息为:{}",sysInstRegBankCode001Dto == null ? "" : sysInstRegBankCode001Dto.getRegistBankCode());
							if(sysInstRegBankCode001Dto != null && StrUtil.isNotEmpty(sysInstRegBankCode001Dto.getRegistBankCode())){
								String prodProfitType = "03";
								if(subDat.getBusiModel() == null || !"3".equals(subDat.getBusiModel())){
									prodProfitType = "other";
								}
								String pbcProdIdExternal = PbcClassicsUtil.getPbcProdId(sysInstRegBankCode001Dto.getRegistBankCode(),
										prodProfitType, PBC_PROD_BASEINFO_SEQCODE,
										subDat.getVdate());
								if(StrUtil.isNotEmpty(pbcProdIdExternal)){
									subDat.setPbcProdId(pbcProdIdExternal);
									innerProdIdMapping.put(prodId, pbcProdIdExternal);
								}else{
									ValidMessage message = new ValidMessage();
									message.setMessage(prodId + "生成人行代码失败");
									List<ValidMessage> validMessages = datDownloadDto.getValidMessages();
									if(CollectionUtils.isNotEmpty(validMessages)){
										validMessages.addAll(Collections.singletonList(message));
										datDownloadDto.setValidMessages(validMessages);
									}else{
										datDownloadDto.setValidMessages(Collections.singletonList(message));
									}
									return datDownloadDto;
								}
							}else{
								ValidMessage message = new ValidMessage();
								message.setMessage("发行机构代码" + subDat.getIssuePartyId() + "未匹配到登记银行代码！");
								List<ValidMessage> validMessages = datDownloadDto.getValidMessages();
								if(CollectionUtils.isNotEmpty(validMessages)){
									validMessages.addAll(Collections.singletonList(message));
									datDownloadDto.setValidMessages(validMessages);
								}else{
									datDownloadDto.setValidMessages(Collections.singletonList(message));
								}
								return datDownloadDto;
							}
						}
						innerProdIdsNew.add(subDat.getProdId());
						pbcPrdBaseinfoSubDatsNew.add(subDat);
					}
				}
			}
		}
		datDownloadDto.setProdIds(innerProdIdsNew);
		datDownloadDto.setInnerProdIdMapping(innerProdIdMapping);
		datDownloadDto.setPbcPrdBaseinfoSubDats(pbcPrdBaseinfoSubDatsNew);
		for(PbcPrdBaseinfoSubDat subDat :pbcPrdBaseinfoSubDatsNew){
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//序号
			data.put("number","1");
			//产品代码
			data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
			//发行机构代码
			data.put("issuePartyId",subDat.getIssuePartyId());
			//产品名称
			data.put("prodName",subDat.getProdName());
			//产品品种
			data.put("pbcProdType",subDat.getPbcProdType());
			//产品类型
			data.put("ivstorientation",subDat.getIvstorientation());
			//产品品牌
			data.put("prodBrand",subDat.getProdBrand());
			//产品期次
			data.put("prodStage",subDat.getProdStage() == null ? null : subDat.getProdStage().toPlainString());
			//发行机构内部产品代码
			data.put("prodId",subDat.getProdId());
			//募集方式
			data.put("raiseType",subDat.getRaiseType());
			//管理方式
			data.put("manageType",subDat.getManageType());
			//运行方式
			data.put("operateMode",subDat.getOperateMode());
			//募集起始日期
			data.put("raiseVdate",subDat.getRaiseVdate() == null ? "" : DateUtil.format(subDat.getRaiseVdate(),DateUtil.DEFAULT_DATE_FORMAT_EN));
			//募集结束日期
			data.put("raiseEnddate",subDat.getRaiseEnddate() == null ? "" : DateUtil.format(subDat.getRaiseEnddate(),DateUtil.DEFAULT_DATE_FORMAT_EN));
			//发行机构提前终止权标识
			data.put("termRightFlag",subDat.getTermRightFlag());
			//客户赎回权标识
			data.put("cusRedeemFlag",subDat.getCusRedeemFlag());
			//产品增信标识
			data.put("creditFlag",subDat.getCreditFlag());
			//境内托管机构代码
			data.put("inDepCode",subDat.getInDepCode());
			//境外托管机构国别
			data.put("outDepCtry",subDat.getOutDepCtry());
			//境外托管机构名称
			data.put("outDepName",subDat.getOutDepName());
			//产品起始日期
			data.put("vdate",subDat.getVdate() == null ? "" : DateUtil.format(subDat.getVdate(),DateUtil.DEFAULT_DATE_FORMAT_EN));
			//产品预计终止日期
			data.put("mdate",subDat.getMdate() == null ? "" : DateUtil.format(subDat.getMdate(),DateUtil.DEFAULT_DATE_FORMAT_EN));
			//受托职责
			data.put("fiduciaryDuty",subDat.getFiduciaryDuty());
			//分级产品标识
			data.put("isConstracte",subDat.getIsConstracte());
			//收益权转让产品标识
			data.put("transferType",subDat.getTransferType());
			//货币市场基金标识
			data.put("isMarketFund",subDat.getIsMarketFund());
			/*//其他产品标识1
			data.put("other1","");
			//其他产品标识2
			data.put("other2","");*/
			//跨境理财通标识
			data.put("crossBorderFlag",subDat.getCrossBorderFlag());
			//以财产权信托名义开展的资金信托标识
			data.put("zjxtFlag",subDat.getZjxtFlag());
			//其他产品标识3
			data.put("other3","");
			dataList.add(data);
		}
		for (PbcPrdBaseinfoSubDat subDat : pbcPrdBaseinfoSubDatsNew) {
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//序号
			data.put("number","2");
			//产品代码
			data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
			//币种种类1-募集资金币种
			//2-兑付本金币种
			//3-兑付收益币种
			data.put("ccyType","1");
			//币种代码
			data.put("ccyCode",subDat.getRaiseCcy());
			dataList.add(data);
			//组装数据
			LinkedHashMap<String,String> data1= new LinkedHashMap<String,String>();
			//序号
			data1.put("number","2");
			//产品代码
			data1.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
			//币种种类1-募集资金币种
			//2-兑付本金币种
			//3-兑付收益币种
			data1.put("ccyType","2");
			//币种代码
			data1.put("ccyCode",subDat.getCashPrinCcy());
			dataList.add(data1);
			//组装数据
			LinkedHashMap<String,String> data2 = new LinkedHashMap<String,String>();
			//序号
			data2.put("number","2");
			//产品代码
			data2.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
			//币种种类1-募集资金币种
			//2-兑付本金币种
			//3-兑付收益币种
			data2.put("ccyType","3");
			//币种代码
			data2.put("ccyCode",subDat.getCashProfCcy());
			dataList.add(data2);
		}
		for (PbcPrdBaseinfoSubDat subDat : pbcPrdBaseinfoSubDatsNew) {
			String creditFlag = subDat.getCreditFlag();
			if(StrUtil.isNotEmpty(creditFlag) && "1".equals(creditFlag)){


				//增信机构类型
				String creditType = subDat.getCreditType();
				if(StrUtil.isNotEmpty(creditType)){
					String[] split = creditType.split(",");
					if(split.length > 0){
						for (int i = 0;i < split.length; i++) {
							//组装数据
							LinkedHashMap<String,String> data = new LinkedHashMap<String,String>();
							//序号
							data.put("number","3");
							//产品代码
							data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
							data.put("creditType",split[i]);
							dataList.add(data);
						}
					}else{
						//组装数据
						LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
						//序号
						data.put("number","3");
						//产品代码
						data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
						data.put("creditType",subDat.getCreditType());
						dataList.add(data);
					}
				}else{
					//组装数据
					LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
					//序号
					data.put("number","3");
					//产品代码
					data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
					data.put("creditType",subDat.getCreditType());
					dataList.add(data);
				}

			}
		}
		for (PbcPrdBaseinfoSubDat subDat : pbcPrdBaseinfoSubDatsNew) {
			String creditFlag = subDat.getCreditFlag();
			if(StrUtil.isNotEmpty(creditFlag) && "1".equals(creditFlag)){
				String creditMode = subDat.getCreditMode();
				//3：内外增级
				if(StrUtil.isNotEmpty(creditMode) && "3".equals(creditMode)){
					//组装数据
					LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
					//序号
					data.put("number","4");
					//产品代码
					data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
					//增信形式
					data.put("creditMode","1");
					dataList.add(data);
					//组装数据
					LinkedHashMap<String,String> data1 = new LinkedHashMap<String,String>();
					//序号
					data1.put("number","4");
					//产品代码
					data1.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
					//增信形式
					data1.put("creditMode","2");
					dataList.add(data1);
				}else{
					//组装数据
					LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
					//序号
					data.put("number","4");
					//产品代码
					data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
					//增信形式
					data.put("creditMode",subDat.getCreditMode());
					dataList.add(data);
				}
			}
		}
		datDownloadDto.setUrl(PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList));
		return datDownloadDto;
	}
	//产品终止信息dat
	private DatDownloadDto genReportPrdEndInfoFileDat(PbcSendZipDatFile pbcSendZipDatFile, String datType) {
		DatDownloadDto datDownloadDto = new DatDownloadDto();
		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<PbcPrdBaseinfoEndSubDat> pbcPrdBaseinfoEndSubDats = pbcPrdBaseinfoEndSubDatMapper.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode());
		if(CollectionUtil.isEmpty(pbcPrdBaseinfoEndSubDats)){
			datDownloadDto.setUrl(PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList));
			return datDownloadDto;
		}
		List<String> innerProdIds = new ArrayList<>();
		for (PbcPrdBaseinfoEndSubDat pbcPrdBaseinfoSubDat : pbcPrdBaseinfoEndSubDats) {
			String prodId = pbcPrdBaseinfoSubDat.getProdId();
			if(StrUtil.isNotEmpty(prodId) && !innerProdIds.contains(prodId)){
				innerProdIds.add(prodId);
			}
		}
		List<PbcProdAssetpoolCode> pbcProdAssetpoolCodes = new ArrayList<>();
		if(CollectionUtil.isNotEmpty(innerProdIds)){
			pbcProdAssetpoolCodes = pbcProdAssetpoolCodeMapper.selectByInsideProdList(innerProdIds);
		}
		Map<String,String> innerProdIdMapping = new HashMap<>();
		List<PbcPrdBaseinfoEndSubDat> pbcPrdBaseinfoSubDatsNew = new ArrayList<>();
		List<String> innerProdIdsNew = new ArrayList<>();
		for (PbcPrdBaseinfoEndSubDat pbcPrdBaseinfoSubDat : pbcPrdBaseinfoEndSubDats) {
			if(CollectionUtils.isNotEmpty(pbcProdAssetpoolCodes)){
				Optional<PbcProdAssetpoolCode> first = pbcProdAssetpoolCodes.stream().filter(pbcProdAssetpoolCode ->
						pbcPrdBaseinfoSubDat.getProdId().equals(pbcProdAssetpoolCode.getInsideProdId())).findFirst();
				if(first.isPresent()&&StrUtil.isNotEmpty(first.get().getPbcCode())){
					innerProdIdsNew.add(pbcPrdBaseinfoSubDat.getProdId());
					pbcPrdBaseinfoSubDatsNew.add(pbcPrdBaseinfoSubDat);
					innerProdIdMapping.put(pbcPrdBaseinfoSubDat.getProdId(),first.get().getPbcCode());
				}
			}
		}
		datDownloadDto.setProdIds(innerProdIdsNew);
		datDownloadDto.setInnerProdIdMapping(innerProdIdMapping);
		datDownloadDto.setPbcPrdBaseinfoEndSubDats(pbcPrdBaseinfoSubDatsNew);
		for (PbcPrdBaseinfoEndSubDat subDat : pbcPrdBaseinfoSubDatsNew) {
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//序号
			data.put("number","1");
			//产品代码
			data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
			//产品实际终止日期
			data.put("mDate",subDat.getMdate() == null ? "" : DateUtil.format(subDat.getMdate(),DateUtil.DEFAULT_DATE_FORMAT_EN));
			//兑付客户收益率
			data.put("cusYearYield",subDat.getCusYearYield() == null ? null : subDat.getCusYearYield().toPlainString());
			dataList.add(data);
		}
		for (PbcPrdBaseinfoEndSubDat subDat : pbcPrdBaseinfoSubDatsNew) {
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//序号
			data.put("number","2");
			//产品代码
			data.put("pbcProdId",innerProdIdMapping.get(subDat.getProdId()));
			//币种代码
			data.put("mDate",subDat.getCcy());
			//兑付客户收益
			data.put("customProfit",subDat.getCustomProfit() == null ? null : subDat.getCustomProfit().toPlainString());
			//兑付客户收益转人民币
			data.put("customProfitCny",subDat.getCustomProfitCny() == null ? null : subDat.getCustomProfitCny().toPlainString());
			dataList.add(data);
		}
		datDownloadDto.setUrl(PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList));
		return datDownloadDto;
	}
    //报表1_1到1_6,3_1到3_2dat
    private String genReport1_1_6_3_2FileDat(PbcSendZipDatFile pbcSendZipDatFile,String datType) {
		List<PbcReportProdDatDto> subList = pbcReportProdDatMapper.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(),datType);

		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<ChbTypeMappingEx> exList=getReportRowAndColumnNum(datType);
		for(PbcReportProdDatDto sub :subList){  //循环报表数据
			String propertycode=sub.getPropertyCode();
			//查询属性对应的匹配关系数据
			List<ChbTypeMappingEx> propertyCodeFiledList=exList.stream().filter(
					propertyCodeEx -> propertycode.equals(propertyCodeEx.getSrcfield())).collect(Collectors.toList());
			for(ChbTypeMappingEx ex:propertyCodeFiledList) {
					Object   filedValue=null;
				    String fieldName = "";
					//get[源属性1]()   取得报表的对应单元的数值
					try {
						Field field = sub.getClass().getDeclaredField(ex.getSrcfield1());
						fieldName = field.getName();
						//设置对象的访问权限，保证对private的属性的访问
						field.setAccessible(true);
						filedValue= field.get(sub);
					} catch (Exception e) {
						return null;
					}
				     //数值为空或者0的情况下跳过
					if(!(StringUtil.isNotEmpty(filedValue)&&!new BigDecimal("0").equals(filedValue))){
                         continue;
					}
					//组装数据
					LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
					data.put("date", DateUtil.getLastDayOfThisMonth(pbcSendZipDatFile.getStatusVdate()));
					data.put("issuePartyId", pbcSendZipDatFile.getIssuePartyId());
					data.put("prodVariety", pbcSendZipDatFile.getProdVariety());
					data.put("rowColumn", getRowAndColumn(ex.getObjfield1(),ex.getObjfield2()));
					data.put("filedValue", dealDataBaseForDat(filedValue,fieldName,datType));
					dataList.add(data);
			}
		}
		//生成dat文件
		String result=PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList);
		//更新数据状态
        return result;
    }

	//报表3_3dat
	private String genReport3_3FileDat(PbcSendZipDatFile pbcSendZipDatFile,String datType) {
		List<PbcReportDebtTermDatDetail> subList = pbcReportDebtTermDatDetailMapper.selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(),datType);

		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		List<LinkedHashMap<String,String>> dataList =new ArrayList<>();
		List<ChbTypeMappingEx> exList=getReportRowAndColumnNum(datType);
		for(PbcReportDebtTermDatDetail sub :subList){  //循环报表数据
			String propertycode=sub.getPropertycode();
			//查询属性对应的匹配关系数据
			List<ChbTypeMappingEx> propertyCodeFiledList=exList.stream().filter(
					propertyCodeEx -> propertycode.equals(propertyCodeEx.getSrcfield())).collect(Collectors.toList());
			for(ChbTypeMappingEx ex:propertyCodeFiledList) {
				Object   filedValue=null;
				String fieldName = "";
				//get[源属性1]()   取得报表的对应单元的数值
				try {
					Field field = sub.getClass().getDeclaredField(ex.getSrcfield1());
					fieldName = field.getName();
					//设置对象的访问权限，保证对private的属性的访问
					field.setAccessible(true);
					filedValue= field.get(sub);
				} catch (Exception e) {
					return null;
				}
				//数值为空或者0的情况下跳过
				if(!(StringUtil.isNotEmpty(filedValue)&&!new BigDecimal("0").equals(filedValue))){
					continue;
				}
				//组装数据
				LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
				data.put("date", DateUtil.getLastDayOfThisMonth(pbcSendZipDatFile.getStatusVdate()));
				data.put("issuePartyId", pbcSendZipDatFile.getIssuePartyId());
				data.put("prodVariety", pbcSendZipDatFile.getProdVariety());
				data.put("rowColumn", getRowAndColumn(ex.getObjfield1(),ex.getObjfield2()));
				data.put("filedValue", dealDataBaseForDat(filedValue,fieldName,datType));
				dataList.add(data);
			}
		}
		String result=PbcZipDataFileHelper.genFile(pbcSendZipDatFile,datType,dataList);
		return result;
	}

    private List<String> genCompressFile(Set<String>  files) {
        String rootdir = SvrDataUtil.getSysParam("SVR_PBC_DAT_FILEPATH");
        String switchs = SvrDataUtil.getSysParam("SVR_PBC_DAT_SWITCH");
    	/*将生成压缩文件的地址存入List并返回 add by gaojian 2019-06-18 begin*/
    	List<String> zipFileList = new ArrayList<String>(16);
    	/*将生成压缩文件的地址存入List并返回 add by gaojian 2019-06-18 end*/
    	for(String folderPath : files){
    		  String zipFileName = folderPath.replace("_", "")+".zip";
    		  zipFileList.add(zipFileName);
    		  File outFile =  new SafelyFile(zipFileName);
    		  File[] allFiles = new SafelyFile(folderPath).listFiles();
    		  if(outFile.exists()){
    			  outFile.delete();
    		  }
    		  try {
				  ZipFileUtil.compressZip(outFile, true, allFiles);//压缩文件
                new File(folderPath).delete();//删除文件夹
              }catch (Exception e) {
            	  e.printStackTrace();
              }
    		  try {
    			  if(StringUtil.equals("open", switchs)){
    				  String userName=SvrDataUtil.getSysParam("RECEPTION_TAMS_NAME");
    				  String ftpIp=SvrDataUtil.getSysParam("RECEPTION_TAMS_IP");
    				  Integer ftpPort=Integer.valueOf(SvrDataUtil.getSysParam("RECEPTION_TAMS_PORT"));
    				  String password=SvrDataUtil.getSysParam("RECEPTION_TAMS_PWD");
    				  if(userName!=null&&ftpIp!=null&&ftpPort!=null&&password!=null){
    					  SftpUtil.SFTPconnect(userName,ftpIp,ftpPort,password);
//	                  	  SftpUtil.SFTPconnect(pbccomUtil.getConfig("DEPOSIT_TAMS_NAME"),pbccomUtil.getConfig("DEPOSIT_TAMS_IP"),Integer.valueOf(pbccomUtil.getConfig("DEPOSIT_TAMS_PORT")),pbccomUtil.getConfig("DEPOSIT_TAMS_PWD"));
    					  SftpUtil.putFile(outFile.getAbsolutePath(), rootdir);
    				  }
    			  }
              }
              catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
    	}
    	return zipFileList;

    }
    

    /**
     * 将手工生成的压缩文件进一步压缩，并返回最终文件路径
     * @param zipFileName 手工生成的文件路径列表
     * @return 最终文件路径
     */
    private String genFinalCompressFile(List<String> zipFileName) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        int size = zipFileName.size();
        // 未生辰数据文件,不需要继续压缩
        if (size < 1) {
        	return "";
        }
        File[] allFiles = new SafelyFile[size];
        // 最终文件路径
    	String finalZipPath = SvrDataUtil.getSysParam("SVR_PBC_DAT_FILEPATH")  + "手工生成人行数据文件" + dateStr + ".zip";
    	File outFile =  new SafelyFile(finalZipPath);
    	for (int i = 0; i < size; i ++) {
    		allFiles[i] = new SafelyFile(zipFileName.get(i));
    	}
    	// 压缩文件并删除原文件
    	try {
			ZipFileUtil.compressZip(outFile, true, allFiles);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return finalZipPath;
    }

	/**
	 * 报表根据类型和列获取行号和列号
	 * @param reportType
	 * @return
	 */
	private List<ChbTypeMappingEx>  getReportRowAndColumnNum(String reportType){
		// 根据类型筛选
		List<ChbTypeMappingEx> exList = chbTypeMappingExList.stream()
				.filter(ex -> reportType.equals(ex.getMappingcode()))
				.collect(Collectors.toList());
		//行号-列号
		return exList;
	}

	/**
	 * 报表配置初始化
	 * @return
	 */
	private  List<ChbTypeMappingEx>  initReportConfig(){
		List<ChbTypeMappingEx>  HoldTypeMappingList = chbTypeMappingExMapper.selectByMappingname(DictCodeListExt2.人行报送类型_统计模板数据.getValue());
		return HoldTypeMappingList;
	}


    //行编号-列编号，例如：300000-A
	private String getRowAndColumn(String row,String column){
		StringBuilder strBuilder=new StringBuilder();
		strBuilder.append(row).append("-").append(column);
		return strBuilder.toString();
	}

	//初始化配置表数据
	private String  initChbExData(){
		String[] reportTypeList={"1_1","1_2","1_3","1_4","1_5","1_6",
				"2_1","2_2","2_3","2_4","2_5","3_1","3_2","3_3"};
//		String[] reportTypeList={"1_1"};
		List<String> deleteSqlList=new ArrayList<>();
		for(String reportType:reportTypeList ) {
			File file = new File("D:\\workCode\\ZGBS-CPtams\\tams-svreport-parent\\tams-svreport\\src\\main\\resources\\tpl\\pbc\\PbcDatReport_"+reportType+".xls");
//			File file = new File("D:\\2_1.xlsx");
			try {
				List<ChbTypeMappingEx> list = PbcZExcelDefineUtil.readToList(reportType, file, 0);
				for (ChbTypeMappingEx chbTypeMappingEx : list) {
					ChbTypeMappingEx typeMappingEx = chbTypeMappingExMapper.selectBySrcfieldSrcfield1(chbTypeMappingEx.getSrcfield(), chbTypeMappingEx.getSrcfield1());
					if (typeMappingEx == null) {
						chbTypeMappingExMapper.insertSelective(chbTypeMappingEx);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	//数据报送文件管理表PBC_SEND_ZIP_DAT_FILE
    private PbcSendZipDatFile getPbcSendZipDatFile(String ZipCode,String datZipPath,Date date,String datType,String reportuuid,String datZipType){
		PbcSendZipDatFile datFile=new PbcSendZipDatFile();
		datFile.setDatZipDatType(datType);
		datFile.setReportuuid(reportuuid);
		datFile.setDatZipCode(ZipCode);
		datFile.setDatZipPath(datZipPath);
		datFile.setDatZipStatus(DictCodeListExt3.zip包状态_已使用.getValue());
		datFile.setReturnZipSendStatus(DictCodeListExt2.人行报送反馈状态_人行通过.getValue());
		datFile.setSubmitTime(date);
		datFile.setZipDownloadTime(date);
		datFile.setZipSendStatus(DictCodeListExt2.报送状态_手工处理.getValue());
		datFile.setDatZipType(datZipType);
		return datFile;
	}
	//更新数据状态
	private  void updateFileDatAndPbcReport(List<PbcSendZipDatFile> datFileList,Date date,String statusVdate,Map<String,String> termidMap){
		if(!CollectionUtils.isEmpty(datFileList)){
			//更新PBC_SEND_ZIP_DAT_FILE
			pbcSendZipDatFileMapper.updateByZipCodeAndZipType(datFileList);
			for(Map.Entry<String,String> dto :termidMap.entrySet()){
				if(DictCodeListExt2.报送状态_未报送.getValue().equals(dto.getValue())){
					List<PbcMonthReport> reportList  = new ArrayList<PbcMonthReport>();
					PbcMonthReport report = new PbcMonthReport();
					report.setTermid(dto.getKey());
					report.setOrgCode(CommonUtil.getOrgCode());
					reportList.add(report);
					pbcMonthReport001Service.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), reportList);
				}
			}
			//更新报表
			pbcMonthReportMapper.updatePbcReportSendInfo(DictCodeListExt2.报送状态_手工处理.getValue(), date, DictCodeListExt2.人行报送反馈状态_人行通过.getValue(), datFileList);

		}
	}
	//更新数据状态
	private  void updateBaseFileDatAndPbcReport(List<PbcSendZipDatFile> datFileList,List<DatDownloadDto> datDownloadDtos){
		//更新报送文件管理表PBC_SEND_ZIP_DAT_FILE
		if(CollectionUtils.isNotEmpty(datFileList)){
			//更新PBC_SEND_ZIP_DAT_FILE
			pbcSendZipDatFileMapper.updateByZipCodeAndZipType(datFileList);
		}
		if(CollectionUtils.isNotEmpty(datDownloadDtos)){
			for (DatDownloadDto datDownloadDto : datDownloadDtos) {
				List<PbcPrdBaseinfoSubDat> baseinfoSubDats = datDownloadDto.getPbcPrdBaseinfoSubDats();
				List<PbcPrdBaseinfoEndSubDat> baseinfoEndSubDats = datDownloadDto.getPbcPrdBaseinfoEndSubDats();
				Map<String, String> newPbcId = datDownloadDto.getInnerProdIdMapping();
				List<String> prodIds = datDownloadDto.getProdIds();
				//更新产品基本信息DAT表
				if(CollectionUtils.isNotEmpty(baseinfoSubDats)) {
					for (PbcPrdBaseinfoSubDat baseinfoSubDat : baseinfoSubDats) {
						if(StrUtil.isNotEmpty(baseinfoSubDat.getDatZipCode())){
							//人行产品代码
							baseinfoSubDat.setPbcProdId("");
							baseinfoSubDat.setSendStatus(DictCodeListExt2.报送状态_手工处理.getValue());
							baseinfoSubDat.setSubmitFeedbackStatus(DictCodeListExt2.人行报送反馈状态_人行通过.getValue());
							baseinfoSubDat.setSubmitTime(new Date());
							pbcPrdBaseinfoSubDatMapper.updateByDatZipCode(baseinfoSubDat);
						}
					}
				}
				//更新产品终止信息DAT表
				if(CollectionUtils.isNotEmpty(baseinfoEndSubDats)) {
					for (PbcPrdBaseinfoEndSubDat baseinfoSubDat : baseinfoEndSubDats) {
						if(StrUtil.isNotEmpty(baseinfoSubDat.getDatZipCode())){
							baseinfoSubDat.setSendStatus(DictCodeListExt2.报送状态_手工处理.getValue());
							baseinfoSubDat.setSubmitFeedbackStatus(DictCodeListExt2.人行报送反馈状态_人行通过.getValue());
							baseinfoSubDat.setSubmitTime(new Date());
							pbcPrdBaseinfoEndSubDatMapper.updateByDatZipCode(baseinfoSubDat);
						}
					}
				}
				//更新产品基本信息表 PBC_PRD_BASEINFO_SUB  终止信息表PBC_PRD_BASEINFO_END_SUB
				if(CollectionUtils.isNotEmpty(prodIds)) {
					pbcPrdBaseinfoSubNewMapper.updateByProdIds(prodIds);
					pbcPrdBaseinfoEndSubNewMapper.updateByProdIds(prodIds);
				}
				//更新人行产品资产池代码管理表（PBC_PROD_ASSETPOOL_CODE）
				if(newPbcId != null && newPbcId.size() > 0) {
					Set<Map.Entry<String, String>> entries = newPbcId.entrySet();
					for (Map.Entry<String, String> entry : entries) {
						String prodId = entry.getKey();
						String pbcProdId = entry.getValue();
						PbcProdAssetpoolCodeKey codeKey = new PbcProdAssetpoolCodeKey();
						codeKey.setInsideProdId(prodId);
						codeKey.setCodeType(DictCodeListExt3.代码类型_产品.getValue());
						PbcProdAssetpoolCode pbcProdAssetpoolCode = pbcProdAssetpoolCodeMapper.selectByPrimaryKey(codeKey);
						if(pbcProdAssetpoolCode == null){
							PbcProdAssetpoolCode prodAssetpoolCode = new PbcProdAssetpoolCode();
							prodAssetpoolCode.setInsideProdId(prodId);
							prodAssetpoolCode.setPbcCode(pbcProdId);
							prodAssetpoolCode.setCodeType(DictCodeListExt3.代码类型_产品.getValue());
							prodAssetpoolCode.setStatus("E");
							prodAssetpoolCode.setCreateUser(SecurityUtil.getLoginUser().getUserName());
							prodAssetpoolCode.setOrgCode(SecurityUtil.getLoginUser().getOrgCode());
							prodAssetpoolCode.setCreateTime(new Date());
							prodAssetpoolCode.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
							prodAssetpoolCode.setUpdateTime(new Date());
							pbcProdAssetpoolCodeMapper.insert(prodAssetpoolCode);
						}else{
							pbcProdAssetpoolCode.setPbcCode(pbcProdId);
							pbcProdAssetpoolCode.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
							pbcProdAssetpoolCode.setUpdateTime(new Date());
							pbcProdAssetpoolCodeMapper.updateByPrimaryKey(pbcProdAssetpoolCode);
						}
					}

				}
			}
		}
	}
    //取出来的数据库值除以10000成真实的值
	public String dealDataBaseForDat(Object data,String feildName,String reportType) {
		//1_3为只数统计表，不需要除以10000
		if("1_3".equals(reportType)){
			return data == null ? null : data.toString();
		}
		//1_5只数，不需要除以10000
		if("1_5".equals(reportType) && feildName.equals("prodcounts")){
			return data == null ? null : data.toString();
		}
		//1_6只数，不需要除以10000
		if("1_6".equals(reportType) && feildName.equals("prodcounts")){
			return data == null ? null : data.toString();
		}
		BigDecimal bigDecimal = new BigDecimal(data.toString());
		BigDecimal tenthousand = new BigDecimal(10000);
		if (data != null) {
			bigDecimal = bigDecimal.divide(tenthousand, 6, BigDecimal.ROUND_HALF_UP);
		}
		return bigDecimal.stripTrailingZeros().toPlainString();
	}


}
