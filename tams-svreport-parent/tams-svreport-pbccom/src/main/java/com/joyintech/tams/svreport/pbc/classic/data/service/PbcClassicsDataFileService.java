/**
 * 文件名：PbcClassicsService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chenxc
 * 修改时间：2019年6月10日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.pbc.classic.data.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.system.institution.dto.SysInstitutionDto;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.system.instregbankcode.dao.SysInstRegBankCode001Mapper;
import com.joyintech.tams.report.system.instregbankcode.dto.SysInstRegBankCode001Dto;
import com.joyintech.tams.report.system.instregbankcode.service.SysInstRegBankCode001Service;
import com.joyintech.tams.svreport.pbc.assetdebt.dao.PbcReportAssetDebtDetailMapper;
import com.joyintech.tams.svreport.pbc.assetdebt.dto.PbcReportAssetDebtDetailDto;
import com.joyintech.tams.svreport.pbc.assetposition.constants.PbcAssetPositionConstants;
import com.joyintech.tams.svreport.pbc.classic.data.dao.PbcDataFileDetailMapper;
import com.joyintech.tams.svreport.pbc.classic.data.dao.PbcDataFileMapper;
import com.joyintech.tams.svreport.pbc.classic.data.dao.PbcDatfileDetailRelationMapper;
import com.joyintech.tams.svreport.pbc.classic.data.dto.PbcDataFile;
import com.joyintech.tams.svreport.pbc.classic.data.dto.PbcDataFileDetail;
import com.joyintech.tams.svreport.pbc.classic.data.dto.PbcDatfileDetailRelation;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dao.PbcPrdAccountSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dto.PbcPrdAccountSub;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.service.PbcPrdAccountSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdaccountendsub.dao.PbcPrdAccountEndSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdaccountendsub.dto.PbcPrdAccountEndSub;
import com.joyintech.tams.svreport.pbc.classic.prdaccountendsub.service.PbcPrdAccountEndSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdassetdebt.service.PbcPrdAssetDebtSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosub.dao.PbcPrdBaseinfoSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosub.dto.PbcPrdBaseinfoSub;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosub.service.PbcPrdBaseinfoSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.dao.PbcProdRaiseSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.dto.PbcProdRaiseSub;
import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.service.PbcPrdCashRaiseSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsub.dao.PbcPrdBaseinfoEndSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsub.dto.PbcPrdBaseinfoEndSub;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsub.serivce.PrdBaseinfoEndSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.dao.PbcInitProdRaisingSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.dto.PbcInitProdRaisingSub;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.service.PbcPrdInitRaiseSubService001;
import com.joyintech.tams.svreport.pbc.common.constants.PbcReportType;
import com.joyintech.tams.svreport.pbc.common.util.PbccomUtil;
import com.joyintech.tams.svreport.pbc.common.util.SftpUtil;
import com.joyintech.tams.svreport.pbc.report.dao.PbcMonthReportMapper;
import com.joyintech.tams.svreport.pbc.report.dto.PbcLoanDetailInfoSub;
import com.joyintech.tams.svreport.pbc.report.dto.PbcMonthReport;
import com.joyintech.tools.collectors.JoyinCollectors;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2019年6月10日
 * @see PbcClassicsDataFileService
 * @since 1.0
 */
@Service
public class PbcClassicsDataFileService {
	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(PbcClassicsDataFileService.class);

	public static final String APPLY_TYPE_1="1-产品基本信息";
	public static final String APPLY_TYPE_2="2-产品终止信息";
	public static final String APPLY_TYPE_3="3-资金池基本信息";
	public static final String APPLY_TYPE_4="4-资金池终止信息";
	public static final String APPLY_TYPE_5="5-产品起始募集信息";
	public static final String APPLY_TYPE_6="6-产品资金募集信息";
	public static final String APPLY_TYPE_7="7-资产负债信息";
	private Map<String,String> bankNameMap = new HashMap<String,String>();
	private Map<String,String> pbcBankCodeMap = new HashMap<String,String>();

	@Autowired
	private SysInstRegBankCode001Service sysInstRegBankCode001Service;

	@Autowired
	private SysInstRegBankCode001Mapper sysInstRegBankCode001Mapper;

	@Autowired
	private PbcPrdAccountSubMapper pbcPrdAccountSubMapper;

	@Autowired
	private PbcPrdBaseinfoSubMapper pbcPrdBaseinfoSubMapper;

	@Autowired
	private PbcInitProdRaisingSubMapper pbcInitProdRaisingSubMapper;

	@Autowired
	private PbcPrdBaseinfoEndSubMapper pbcPrdBaseinfoEndSubMapper;

	@Autowired
	private PbcPrdAccountEndSubMapper pbcPrdAccountEndSubMapper;

	@Autowired
	private PbcProdRaiseSubMapper pbcProdRaiseSubMapper;

	@Autowired
	private PbcMonthReportMapper pbcMonthReportMapper;

	@Autowired
	private PbcReportAssetDebtDetailMapper pbcReportAssetDebtDetailMapper;

	@Autowired
	private ChbTypeMappingExMapper chbTypeMappingExMapper;

	@Autowired
	private PbcDataFileMapper pbcDataFileMapper;
	@Autowired
	private PbcDatfileDetailRelationMapper pbcDatfileDetailRelationMapper;

	@Autowired
	private PbcDataFileDetailMapper pbcDataFileDetailMapper;

	@Autowired
	private PbcPrdAccountSubService001 pbcPrdAccountSubService001;
	@Autowired
	private PbcPrdAccountEndSubService001 pbcPrdAccountEndSubService001;
	@Autowired
	private PbcPrdBaseinfoSubService001 pbcPrdBaseinfoSubService001;
	@Autowired
	private PbcPrdCashRaiseSubService001 pbcPrdCashRaiseSubService001;
	@Autowired
	private PrdBaseinfoEndSubService001 prdBaseinfoEndSubService001;
	@Autowired
	private PbcPrdInitRaiseSubService001 pbcPrdInitRaiseSubService001;
	@Autowired
	private PbcPrdAssetDebtSubService001 pbcPrdAssetDebtSubService001;

	@Autowired
	private static PbccomUtil pbccomUtil;
	@Autowired
	public void setPbccomUtil(PbccomUtil pbccomUtil) {
		PbcClassicsDataFileService.pbccomUtil = pbccomUtil;
	}

	/**
	 * 删除上日及本日未处理（未发送）业务数据
	 * @param batchDate 跑批日期
	 * @return
	 */
	public String deleteUnSendDataFile(Date batchDate) {
		// 1.查询上日及本日生成的dat文件留痕信息，获得未发送状态数据
		Map<String, Object> searchParam = new HashMap<String, Object>();
		searchParam.put("batchDate", batchDate);
		searchParam.put("sendStatus",DictCodeListExt2.报送状态_未报送.getValue());
		searchParam.put("status",DictCodeListExt2.数据状态_有效.getValue());
		List<PbcDatfileDetailRelation> fileList = pbcDatfileDetailRelationMapper
				.selectUnSendDatafileAfterYesterday(searchParam);
		if (CollectionUtils.isEmpty(fileList)) {
			return null;
		}
		// 2.遍历生成文件list，删除未发送状态数据的留痕记录
		// 已被删除的zip
		List<String> deleteZipKeys = new ArrayList<String>();
		PbcDataFile deleteZip;
		// 已被删除的dat
		List<String> deleteDatKeys = new ArrayList<String>();
		for (PbcDatfileDetailRelation dto : fileList) {
			// 2.1.逻辑删除 【人行单产品报表DAT文件表】 文件代码 = 查询出的【文件代码】
			if (!deleteZipKeys.contains(dto.getFileid())) {
				deleteZip = new PbcDataFile();
				deleteZip.setFileid(dto.getFileid());
				deleteZip.setStatus(DictCodeListExt2.数据状态_删除.getValue());
				deleteZip.setUpdateUser("system");
				deleteZip.setUpdateTime(new Date());
				int updateCount = pbcDataFileMapper.updateByPrimaryKeySelective(deleteZip);
				if (updateCount > 0) {
					deleteZipKeys.add(dto.getFileid());
				}
			}
			// 2.2.物理删除 【人行单产品报表DAT文件明细表】 文件代码 = 查询出的【明细文件编号】
			if (!deleteDatKeys.contains(dto.getDetailid())) {
				int delCount = pbcDataFileDetailMapper.deleteByPrimaryKey(dto.getDetailid());
				if (delCount > 0) {
					// 2.3.物理删除 【人行单产品报表DAT文件明细表关联】 逻辑为:关联人行单产品报表DAT文件明细表.文件代码=查询出的【明细文件编号】
					pbcDatfileDetailRelationMapper.deleteByDetailid(dto);
					deleteDatKeys.add(dto.getDetailid());
				}
			}
		}

		// 3.删除上日及本日dat全部为空文件的zip
		// 查询所有zip文件及其dat文件的id集合
		List<PbcDatfileDetailRelation> allfileList = pbcDatfileDetailRelationMapper.selectAllDatafileAfterYesterday(searchParam);
		List<PbcDatfileDetailRelation> relations;
		for (PbcDatfileDetailRelation file : allfileList) {
			searchParam.put("detailids", Arrays.asList(file.getDetailid().split(",")));
			// 查询明细表id集合是否在关系表有数据
			relations = pbcDatfileDetailRelationMapper.selectRelationByDetailids(searchParam);
			// 如果没有数据，则说明dat文件都是空的，要删除zip和dat的记录
			if (CollectionUtils.isEmpty(relations) && !deleteZipKeys.contains(file.getFileid())) {
				deleteZip = new PbcDataFile();
				deleteZip.setFileid(file.getFileid());
				deleteZip.setStatus(DictCodeListExt2.数据状态_删除.getValue());
				deleteZip.setUpdateUser("system");
				deleteZip.setUpdateTime(new Date());
				int updateCount = pbcDataFileMapper.updateByPrimaryKeySelective(deleteZip);
				if (updateCount > 0) {
					deleteZipKeys.add(file.getFileid());
					for (String detailid : Arrays.asList(file.getDetailid().split(","))) {
						pbcDataFileDetailMapper.deleteByPrimaryKey(detailid);
					}
				}
			}
		}
		// ※是否删除服务器文件待确认 TODO
		return null;
	}
	/**
	 * 生成数据文件
	 * @param reportType，报表类型，如果为空，则生成所有
	 * @param listProds，产品列表，如果为空，则生成所有
	 * @param ，生成日期，如果为空，则为当前平台时间
	 * @see
	 * @since 1.0
	 */
	public String genDataFile(String reportType,  List<String> listProds,Date genDate, Map<String, Object> param) {
		initOrgcode();
		Map<String, Object> searchParam = new HashMap<String, Object>();
		if(!CollectionUtils.isEmpty(listProds)){
			List<String> pbcPrdList = new ArrayList<String>();
			List<String> pbcAccoutList = new ArrayList<String>();
			List<PbcPrdBaseinfoSub> prodInfoList = pbcPrdBaseinfoSubMapper.queryAllPbcPrdBaseInfo(null);
			for(PbcPrdBaseinfoSub prd :prodInfoList){
				if(listProds.contains(prd.getProdId())){
					if(prd.getPbcProdId()!=null)
						pbcPrdList.add(prd.getPbcProdId());
					if(prd.getAccountId()!=null)
						pbcAccoutList.add(prd.getAccountId());
				}
			}
			searchParam.put("pbcProdIdList", pbcPrdList);
			searchParam.put("pbcAccountIdList", pbcAccoutList);
		}
		List<String> zipFileName = new ArrayList<String>();

		// 是否手工生成标志
		boolean manualFlag = false;
		/*手工生成的情况 add by gaojian 2019-06-17 begin*/
		if (null != param) {
			searchParam = param;
			manualFlag = true;
		}
		/*手工生成的情况 add by gaojian 2019-06-17 end*/
		searchParam.put("sendStatusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送状态_未报送.getValue()));

		// 设计变更：删除数据查询条件[更新日期=批处理日期] zhangyf 20191107 start
		// searchParam.put("updateTime",DateUtil.format(genDate));
		// 设计变更：删除数据查询条件[更新日期=批处理日期] zhangyf 20191107 end

		searchParam.put("submitadd",SvrDataUtil.getSysParam("SVR_PBC_DAT_SUMBITTIME"));
		searchParam.put("flag","dat");
		if ( genDate==null )
			genDate = new Date();//平台时间
		if ( StringUtils.isEmpty(reportType) || PbcClassicsDataFileHelper.DATAFILE_TYPE_PPI.equals(reportType) ) {
			zipFileName.addAll(genPPIFile(searchParam,genDate,manualFlag));
		}
		if ( StringUtils.isEmpty(reportType) || PbcClassicsDataFileHelper.DATAFILE_TYPE_PCD.equals(reportType) ) {
			zipFileName.addAll(genPCDFile(searchParam,genDate,manualFlag));
		}
		if ( StringUtils.isEmpty(reportType) || PbcClassicsDataFileHelper.DATAFILE_TYPE_PVD.equals(reportType) ) {
			zipFileName.addAll(genPVDFile(searchParam,genDate,manualFlag));
		}
		if ( StringUtils.isEmpty(reportType) || PbcClassicsDataFileHelper.DATAFILE_TYPE_AF1.equals(reportType) ) {
			zipFileName.addAll(genAF1File(searchParam,genDate,manualFlag));
		}
		if ( StringUtils.isEmpty(reportType) || PbcClassicsDataFileHelper.DATAFILE_TYPE_AF2.equals(reportType) ) {
			zipFileName.addAll(genAF2File(searchParam,genDate,manualFlag));
		}
		if ( StringUtils.isEmpty(reportType) || PbcClassicsDataFileHelper.DATAFILE_TYPE_AF3.equals(reportType) ) {
			zipFileName.addAll(genAF3File(searchParam,genDate,manualFlag));
		}
		if ( StringUtils.isEmpty(reportType) || PbcClassicsDataFileHelper.DATAFILE_TYPE_AF4.equals(reportType) ) {
			zipFileName.addAll(genAF4File(searchParam,genDate,manualFlag));
		}
		if ( StringUtils.isEmpty(reportType) || PbcClassicsDataFileHelper.DATAFILE_TYPE_AF5.equals(reportType) ) {
			zipFileName.addAll(genAF5File(searchParam,genDate,manualFlag));
		}
		String finalZipFile = "";
		/*手工生成的文件再次压缩  add by gaojia 2019-06-18 begin*/
		if (manualFlag) {
			finalZipFile = genFinalCompressFile(zipFileName);
		}
		/*手工生成的文件再次压缩  add by gaojia 2019-06-18 end*/
		return finalZipFile;

	}
	/**
	 * 根据映射表取申请单名称及说明
	 * @param type
	 * @return
	 */
	String [] getRequestNameAndRemark(String type){
		List<ChbTypeMappingEx>   subjectNoMappingList = chbTypeMappingExMapper
				.selectByMappingcode(PbcAssetPositionConstants.MAPPINGCODE_PBCREPORT_DAT_REQUEST_NAME);
		String [] aa=new String [2];
		for(ChbTypeMappingEx single:subjectNoMappingList){
			if(single.getSrcfield1().equals(type)){
				aa =single.getSrcfield().split("\\|");
				return aa;
			}
		}
		return null;
	}
	public List<String> genPPIFile(Map<String, Object> searchParam,Date genDate, boolean manualFlag ) {
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_新建.getValue()));

		// 【dat文件生成留痕】文件和数据的主键集合map（key="pathfilenameDatakeylist"，value=文件全名和文件内数据主键list）
		Map<String,Object> fileDataMap = new HashMap<String,Object>();

		//记录序列号
		Map<String,List<String>> seqMap = new HashMap<String,List<String>>();
		//生成dat文件
		List<String> filesPPB1 = genPPB1(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesPIB1 = genPIB1(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesPIB2 = genPIB2(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesPIE1 = genPIE1(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesPPE1 = genPPE1(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		//去重
		Set<String> reslut = new HashSet<String>();
		reslut.addAll(filesPPB1);
		reslut.addAll(filesPIB1);
		reslut.addAll(filesPIB2);
		reslut.addAll(filesPIE1);
		reslut.addAll(filesPPE1);
		//生成压缩包
		List<String> zipFileList = genCompressFile(reslut,manualFlag);
		//非手工生成文件信息入库
		if (!manualFlag) {
			importFileData(reslut,genDate,fileDataMap);
		}
		return zipFileList;
	}

	public List<String> genPCDFile(Map<String, Object> searchParam,Date genDate, boolean manualFlag ) {
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_新建.getValue()));
		// 人行单产品报表DAT文件明细表关联List
		Map<String,Object> fileDataMap = new HashMap<String,Object>();
		//记录序列号
		Map<String,List<String>> seqMap = new HashMap<String,List<String>>();
		//生成dat文件
		List<String> filesPCD1 = genPCD1(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		//去重
		Set<String> reslut = new HashSet<String>();
		reslut.addAll(filesPCD1);
		//生成压缩包
		List<String> zipFileList = genCompressFile(reslut,manualFlag);
		//非手工生成文件信息入库
		if (!manualFlag) {
			importFileData(reslut,genDate,fileDataMap);
		}
		return zipFileList;
	}

	public List<String> genPVDFile(Map<String, Object> searchParam,Date genDate, boolean manualFlag ) {
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_新建.getValue()));
		// 人行单产品报表DAT文件明细表关联List
		Map<String,Object> fileDataMap = new HashMap<String,Object>();
		//记录序列号
		Map<String,List<String>> seqMap = new HashMap<String,List<String>>();
		//生成dat文件
		List<String> filesPVD1 = genPVD1(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesPVD2 = genPVD2(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesPVD3 = genPVD3(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		//去重
		Set<String> reslut = new HashSet<String>();
		reslut.addAll(filesPVD1);
		reslut.addAll(filesPVD2);
		reslut.addAll(filesPVD3);
		//生成压缩包
		List<String> zipFileList = genCompressFile(reslut,manualFlag);
		//非手工生成文件信息入库
		if (!manualFlag) {
			importFileData(reslut,genDate,fileDataMap);
		}
		return zipFileList;
	}

	public List<String> genAF1File(Map<String, Object> searchParam,Date genDate, boolean manualFlag ) {
		// 人行单产品报表DAT文件明细表关联List
		Map<String,Object> fileDataMap = new HashMap<String,Object>();
		//记录序列号
		Map<String,List<String>> seqMap = new HashMap<String,List<String>>();
		//生成dat文件
		List<String> filesAF11 = genAF11(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesAF12 = genAF12(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		//去重
		Set<String> reslut = new HashSet<String>();
		reslut.addAll(filesAF11);
		reslut.addAll(filesAF12);
		//生成压缩包
		List<String> zipFileList = genCompressFile(reslut,manualFlag);
		//非手工生成文件信息入库
		if (!manualFlag) {
			importFileData(reslut,genDate,fileDataMap);
		}
		return zipFileList;
	}

	public List<String> genAF2File(Map<String, Object> searchParam,Date genDate, boolean manualFlag ) {
		// 人行单产品报表DAT文件明细表关联List
		Map<String,Object> fileDataMap = new HashMap<String,Object>();
		//记录序列号
		Map<String,List<String>> seqMap = new HashMap<String,List<String>>();
		//生成dat文件
		List<String> filesAF21 = genAF21(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesAF22 = genAF22(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		//去重
		Set<String> reslut = new HashSet<String>();
		reslut.addAll(filesAF21);
		reslut.addAll(filesAF22);
		//生成压缩包
		List<String> zipFileList = genCompressFile(reslut,manualFlag);
		//非手工生成文件信息入库
		if (!manualFlag) {
			importFileData(reslut,genDate,fileDataMap);
		}
		return zipFileList;
	}

	public List<String> genAF3File(Map<String, Object> searchParam,Date genDate, boolean manualFlag ) {
		// 人行单产品报表DAT文件明细表关联List
		Map<String,Object> fileDataMap = new HashMap<String,Object>();
		//记录序列号
		Map<String,List<String>> seqMap = new HashMap<String,List<String>>();
		//生成dat文件
		List<String> filesAF31 = genAF31(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesAF32 = genAF32(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		//去重
		Set<String> reslut = new HashSet<String>();
		reslut.addAll(filesAF31);
		reslut.addAll(filesAF32);
		//生成压缩包
		List<String> zipFileList = genCompressFile(reslut,manualFlag);
		//非手工生成文件信息入库
		if (!manualFlag) {
			importFileData(reslut,genDate,fileDataMap);
		}
		return zipFileList;
	}

	public List<String> genAF4File(Map<String, Object> searchParam,Date genDate, boolean manualFlag ) {
		// 人行单产品报表DAT文件明细表关联List
		Map<String,Object> fileDataMap = new HashMap<String,Object>();
		//记录序列号
		Map<String,List<String>> seqMap = new HashMap<String,List<String>>();
		//生成dat文件
		List<String> filesAF41 = genAF41(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesAF42 = genAF42(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesAF43 = genAF43(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		//去重
		Set<String> reslut = new HashSet<String>();
		reslut.addAll(filesAF41);
		reslut.addAll(filesAF42);
		reslut.addAll(filesAF43);
		//生成压缩包
		List<String> zipFileList = genCompressFile(reslut,manualFlag);
		//非手工生成文件信息入库
		if (!manualFlag) {
			importFileData(reslut,genDate,fileDataMap);
		}
		return zipFileList;
	}

	public List<String> genAF5File(Map<String, Object> searchParam,Date genDate, boolean manualFlag ) {
		// 人行单产品报表DAT文件明细表关联List
		Map<String,Object> fileDataMap = new HashMap<String,Object>();
		//记录序列号
		Map<String,List<String>> seqMap = new HashMap<String,List<String>>();
		//生成dat文件
		List<String> filesAF51 = genAF51(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesAF52 = genAF52(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		List<String> filesAF53 = genAF53(searchParam,genDate,seqMap,manualFlag,fileDataMap);
		//去重
		Set<String> reslut = new HashSet<String>();
		reslut.addAll(filesAF51);
		reslut.addAll(filesAF52);
		reslut.addAll(filesAF53);
		//生成压缩包
		List<String> zipFileList = genCompressFile(reslut,manualFlag);
		//非手工生成文件信息入库
		if (!manualFlag) {
			importFileData(reslut,genDate,fileDataMap);
		}
		return zipFileList;
	}

	/**
	 * 获取总行机构代码,14位
	 * @return
	 * @see
	 * @since 1.0
	 */
	public String getHeadBankCode() {
		return null;
	}

	//资产池基本信息
	private List<String> genPPB1(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//PPB1查询PBC_PRD_ACCOUNT_SUB，新建未报送的数据
		List<PbcPrdAccountSub> subList = pbcPrdAccountSubMapper.searchPage(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcPrdAccountSub sub :subList){
			if(sub.getIssuePartyId()==null)
				continue;
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(sub.getIssuePartyId())==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(sub.getIssuePartyId());
			}
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//【dat文件生成留痕】数据的key
			data.put("datakey",sub.getRegistSeqno());
			data.put("pbcaccountid", sub.getPbcAccountId());
			data.put("issuepartyid", sub.getIssuePartyId());
			data.put("accountname", sub.getAccountName());
			data.put("vdate", DateUtil.format(sub.getVdate()));
			dataList.add(data);
			map.put(sub.getIssuePartyId(),dataList);
		}

		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PPI,"PPB1",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//        if(!CollectionUtils.isEmpty(subList))
//        	pbcPrdAccountSubService001.doManul(searchParam);
		return result;
	}

	//产品基本信息
	private List<String> genPIB1(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,
								 Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//PPB1查询PBC_PRD_BASEINFO_SUB，新建未报送的数据
		List<PbcPrdBaseinfoSub> subList = pbcPrdBaseinfoSubMapper.searchPage(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcPrdBaseinfoSub sub :subList){
			/**add  by  dongxiangyang  新增产品报送字段逻辑 20200117  start*/
			if(sub.getSubProdId()!=null){
				sub.setProdId(sub.getSubProdId());
			}
			/**add  by  dongxiangyang  新增产品报送字段逻辑 20200117  end*/
			if(sub.getIssuePartyId()==null)
				continue;
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(sub.getIssuePartyId())==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(sub.getIssuePartyId());
			}
			//组装数据
			//1
			LinkedHashMap<String,String> data1= new LinkedHashMap<String,String>();
			//【dat文件生成留痕】数据的key
			data1.put("datakey",sub.getRegistSeqno());
			data1.put("type", "1");
			data1.put("pbc_prod_id", sub.getPbcProdIdShow());
			data1.put("issue_party_id", sub.getIssuePartyId());
			data1.put("pbc_prod_type", sub.getPbcProdType());
			data1.put("prod_name", sub.getProdName());
			data1.put("prod_brand", sub.getProdBrand());
			data1.put("prod_stage", toString(sub.getProdStage()));
			data1.put("prod_id", sub.getProdId());
			data1.put("raise_type", sub.getRaiseType());
			data1.put("manage_type", sub.getManageType());
			data1.put("operate_mode", sub.getOperateMode());
			data1.put("ivstorientation", sub.getIvstorientation());
			data1.put("busi_model", sub.getBusiModel());
			data1.put("income_guaran_ident", sub.getIncomeGuaranIdent());
			data1.put("prin_guaran_ident", sub.getPrinGuaranIdent());
			data1.put("high_yield_issue", toString(sub.getHighYieldIssue()));
			data1.put("low_yield_issue", toString(sub.getLowYieldIssue()));
			data1.put("raise_vdate", DateUtil.format(sub.getRaiseVdate()));
			data1.put("raise_enddate", DateUtil.format(sub.getRaiseEnddate()));
			data1.put("term_right_flag", sub.getTermRightFlag());
			data1.put("cus_redeem_flag", sub.getCusRedeemFlag());
			data1.put("credit_flag", sub.getCreditFlag());
			data1.put("in_dep_code", sub.getInDepCode());
			data1.put("out_dep_ctry", sub.getOutDepCtry());
			data1.put("out_dep_name", sub.getOutDepName());
			data1.put("vdate", DateUtil.format(sub.getVdate()));
			data1.put("mdate", DateUtil.format(sub.getMdate()));
			data1.put("account_id", sub.getAccountId());
			data1.put("fiduciary_duty", sub.getFiduciaryDuty());
			data1.put("cont_mode", sub.getContMode());
			data1.put("is_constracte", sub.getIsConstracte());
			data1.put("transfer_type", sub.getTransferType());
			data1.put("identi_flag", sub.getIdentiFlag());
			data1.put("cash_mana_cate", sub.getCashManaCate());
			data1.put("cross_border_flag", sub.getCrossBorderFlag());
			data1.put("zjxt_flag", sub.getZjxtFlag());
			dataList.add(data1);
			//2-币种信息
			if(sub.getRaiseCcy()!=null){
				for(String raiseCcy :StringUtil.splitToList(",", sub.getRaiseCcy())){
					LinkedHashMap<String,String> data21= new LinkedHashMap<String,String>();
					data21.put("type", "2");
					data21.put("pbc_prod_id", sub.getPbcProdIdShow());
					data21.put("ccy_type", "1");
					data21.put("raise_ccy", raiseCcy);
					dataList.add(data21);
				}
			}
			if(sub.getCashPrinCcy()!=null){
				for(String cashPrinCcy :StringUtil.splitToList(",", sub.getCashPrinCcy())){
					LinkedHashMap<String,String> data22= new LinkedHashMap<String,String>();
					data22.put("type", "2");
					data22.put("pbc_prod_id", sub.getPbcProdIdShow());
					data22.put("ccy_type", "2");
					data22.put("cash_prin_ccy", cashPrinCcy);
					dataList.add(data22);
				}

			}
			if(sub.getCashProfCcy()!=null){
				for(String cashProfCcy :StringUtil.splitToList(",", sub.getCashProfCcy())){
					LinkedHashMap<String,String> data23= new LinkedHashMap<String,String>();
					data23.put("type", "2");
					data23.put("pbc_prod_id", sub.getPbcProdIdShow());
					data23.put("ccy_type", "3");
					data23.put("cash_prof_ccy", cashProfCcy);
					dataList.add(data23);
				}
			}
			//3-客户类型
			if(sub.getCustomerType()!=null){
				for(String customerType :StringUtil.splitToList(",", sub.getCustomerType())){
					LinkedHashMap<String,String> data3= new LinkedHashMap<String,String>();
					data3.put("type", "3");
					data3.put("pbc_prod_id", sub.getPbcProdIdShow());
					data3.put("customer_type", customerType);
					dataList.add(data3);
				}
			}
			//4-增信机构类型
			if(sub.getCreditType()!=null){
				for(String creditType :StringUtil.splitToList(",", sub.getCreditType())){
					LinkedHashMap<String,String> data4= new LinkedHashMap<String,String>();
					data4.put("type", "4");
					data4.put("pbc_prod_id", sub.getPbcProdIdShow());
					data4.put("credit_type", creditType);
					dataList.add(data4);
				}
			}
			//5-增信形式
			if(sub.getCreditMode()!=null){
				for(String creditMode :StringUtil.splitToList(",", sub.getCreditMode())){
					LinkedHashMap<String,String> data5= new LinkedHashMap<String,String>();
					data5.put("type", "5");
					data5.put("pbc_prod_id", sub.getPbcProdIdShow());
					data5.put("credit_mode", creditMode);
					dataList.add(data5);
				}
			}
			map.put(sub.getIssuePartyId(),dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PPI,"PIB1",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//        if(!CollectionUtils.isEmpty(subList))
//        	pbcPrdBaseinfoSubService001.doManul(searchParam);

		return result;

	}

	//产品起始募集信息
	private List<String> genPIB2(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap,boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		List<PbcInitProdRaisingSub> doManulList = new ArrayList<PbcInitProdRaisingSub>();
		//PIB2查询PBC_INIT_PROD_RAISING_SUB，新建未报送的数据
		//不按维度
		doManulList = pbcInitProdRaisingSubMapper.searchPage(searchParam);
//		doManulList.addAll(subList);

		//以buyPlace,ccy维度
//		searchParam.put("buyPlace", "000000");
//		searchParam.put("ccy", "BWB");
//		subList.addAll(pbcInitProdRaisingSubMapper.selectByDimension(searchParam));
//		//以buyPlace维度
//		searchParam.remove("ccy");
//		subList.addAll(pbcInitProdRaisingSubMapper.selectByDimension(searchParam));
//		//以ccy维度
//		searchParam.remove("buyPlace");
//		searchParam.put("ccy", "BWB");
//		subList.addAll(pbcInitProdRaisingSubMapper.selectByDimension(searchParam));
//		//清除参数
//		searchParam.remove("ccy");
		// add by zhangmingming 20200216 将原来多次查询改为一次查询并排序 start
		List<PbcInitProdRaisingSub> subList = pbcInitProdRaisingSubMapper.selectPIB2All(searchParam);
		//  add by zhangmingming 20200216 将原来多次查询改为一次查询并排序 end
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcInitProdRaisingSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(issuePartyId);
			}
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//【dat文件生成留痕】数据的key
			data.put("datakey",sub.getRegistSeqno() == null ? UUID.randomUUID().toString() : sub.getRegistSeqno());
			data.put("pbc_prod_id", sub.getPbcProdId());
			data.put("buy_place", sub.getBuyPlace());
			data.put("customer_type1", sub.getCustomerType1());
			data.put("ccy", sub.getCcy());
			data.put("init_raising_amtsum",toString("BWB".equals(sub.getCcy())?sub.getInitRaisingAmtsumcny():sub.getInitRaisingAmtsum()));
			data.put("init_raising_amtsumcny", toString(sub.getInitRaisingAmtsumcny()));
			data.put("init_raising_qty", toString(sub.getInitRaisingQty()));
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PPI,"PIB2",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//		if(!CollectionUtils.isEmpty(doManulList))
//			pbcPrdInitRaiseSubService001.doManul(searchParam);
		return result;
	}


	//产品终止信息
	private List<String> genPIE1(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap,boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//PIE1查询PBC_PRD_BASEINFO_END_SUB，新建未报送的数据
		searchParam.put("nyield", "000000");
		List<PbcPrdBaseinfoEndSub> subList = pbcPrdBaseinfoEndSubMapper.searchPage(searchParam);
		searchParam.remove("nyield");
		List<PbcPrdBaseinfoEndSub> doManulList  = new ArrayList<PbcPrdBaseinfoEndSub>();
		doManulList.addAll(subList);
		//兑付客户收益率
		subList.addAll(pbcPrdBaseinfoEndSubMapper.selectByDimension(searchParam));
		//按币种维度
		searchParam.put("ccy", "BWB");
		subList.addAll(pbcPrdBaseinfoEndSubMapper.selectByDimension(searchParam));
		searchParam.remove("ccy");
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("pbcProdId", sub.getPbcProdId());
		List<PbcPrdBaseinfoSub> infos =pbcPrdBaseinfoSubMapper.searchPage(param);
		Map<String,List<PbcPrdBaseinfoSub>> infosMap = infos.stream().collect(
				JoyinCollectors.groupingBy(
						x -> x.getPbcProdId()));

		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		List<PbcPrdBaseinfoSub>  singleinfos;
		for(PbcPrdBaseinfoEndSub sub :subList){
			singleinfos=infosMap.get(sub.getPbcProdId());
			if(singleinfos==null||singleinfos.size()==0||singleinfos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = singleinfos.get(0).getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(issuePartyId);
			}
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//【dat文件生成留痕】数据的key
			data.put("datakey",sub.getRegistSeqno() == null ? UUID.randomUUID().toString() : sub.getRegistSeqno());
			data.put("pbc_prod_id", sub.getPbcProdId());
			data.put("mdate", DateUtil.format(sub.getMdate()));
			data.put("ccy", sub.getCcy());
			data.put("profit",toString("BWB".equals(sub.getCcy())?sub.getProfitCny():sub.getProfit()));
			data.put("profit_cny", toString(sub.getProfitCny()));
			data.put("custom_profit", toString("BWB".equals(sub.getCcy())?sub.getCustomProfitCny():sub.getCustomProfit()));
			data.put("custom_profit_cny", toString(sub.getCustomProfitCny()));
			//过滤searchPage方法查出的数据，不需要兑付客户收益率
			data.put("cus_year_yield", sub.getCcy()==null?toString(sub.getCusYearYield()):"");
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PPI,"PIE1",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//       if(!CollectionUtils.isEmpty(doManulList))
//    	   prdBaseinfoEndSubService001.doManul(searchParam);
		return result;
	}

	//资产池终止信息
	private List<String> genPPE1(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap,boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//PPE1查询PBC_PRD_ACCOUNT_END_SUB，新建未报送的数据
		List<PbcPrdAccountEndSub> subList = pbcPrdAccountEndSubMapper.searchPage(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcPrdAccountEndSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcAccountId", sub.getPbcAccountId());
			List<PbcPrdAccountSub> infos = pbcPrdAccountSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(issuePartyId);
			}
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//【dat文件生成留痕】数据的key
			data.put("datakey",sub.getRegistSeqno());
			data.put("pbc_account_id", sub.getPbcAccountId());
			data.put("mdate", DateUtil.format(sub.getMdate()));
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PPI,"PPE1",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//        if(!CollectionUtils.isEmpty(subList))
//        	pbcPrdAccountEndSubService001.doManul(searchParam);
		return result;
	}

	//产品资金募集信息
	private List<String> genPCD1(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<String> result = new ArrayList<String>();
		//PCD1查询PBC_PROD_RAISING_SUB，新建未报送的数据
		List<PbcProdRaiseSub> subList = pbcProdRaiseSubMapper.searchPageForOnePrd(searchParam);
		List<PbcProdRaiseSub> doManulList  = new ArrayList<PbcProdRaiseSub>();
		doManulList.addAll(subList);
		//查询收益率
		searchParam.put("yield", "yield");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension(searchParam));
		searchParam.remove("yield");
		//按币种，区域维度
		searchParam.put("buyPlace", "000000");
		searchParam.put("ccy", "BWB");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension(searchParam));
		//按币种维度
		searchParam.remove("buyPlace");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension(searchParam));
		//按区域维度
		searchParam.remove("ccy");
		searchParam.put("buyPlace", "000000");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension(searchParam));
		//清除参数
		searchParam.remove("buyPlace");
		searchParam.put("nyield", "000000");
		searchParam.put("yield", "000000");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension2(searchParam));// 净值型产品的单位净值-1：原币

		searchParam.put("ccy", "BWB");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension2(searchParam));// 净值型产品的单位净值-2：BWB
		searchParam.remove("ccy");

		//清除参数
		searchParam.remove("nyield");
		searchParam.remove("yield");
		subList = subList.stream().filter(item -> item.getPbcProdId() != null).sorted(Comparator.comparing(PbcProdRaiseSub::getPbcProdId)).collect(Collectors.toList());
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcProdRaiseSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String key = infos.get(0).getIssuePartyId()+"_"+ DateUtil.format(sub.getRaisingDate());
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(key)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(key);
			}
			// 净值型标识
			boolean netValueFlag = StringUtil.isEmpty(sub.getBuyPlace()) && StringUtil.isNotEmpty(sub.getCcy());
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//【dat文件生成留痕】数据的key
			data.put("datakey",sub.getRegistSeqno() == null ? UUID.randomUUID().toString() : sub.getRegistSeqno());
			data.put("RAISING_DATE", DateUtil.format(sub.getRaisingDate()));
			data.put("PBC_PROD_ID", sub.getPbcProdId());
			data.put("BUY_PLACE", sub.getBuyPlace());
			data.put("CUSTOMER_TYPE1", sub.getCustomerType1());
			data.put("CCY", sub.getCcy());
			data.put("RAISING_AMTSUM", netValueFlag ? "" : toString("BWB".equals(sub.getCcy())?sub.getRaisingAmtsumCny():sub.getRaisingAmtsum()));
			data.put("RAISING_AMTSUM_CNY", netValueFlag ? "" : toString(sub.getRaisingAmtsumCny()));
			data.put("RAISING_QTY",netValueFlag ? "" : toString(sub.getRaisingQty()));
			data.put("REDAMT",  netValueFlag ? "" : toString("BWB".equals(sub.getCcy())?sub.getRedamtCny():sub.getRedamt()));
			data.put("REDAMT_CNY",netValueFlag ? "" : toString(sub.getRedamtCny()));
			data.put("REDQTY", netValueFlag ? "" : toString(sub.getRedqty()));
			data.put("ENDAMTSUM", netValueFlag ? "" : toString("BWB".equals(sub.getCcy())?sub.getEndamtsumCny(): sub.getEndamtsum()));
			data.put("ENDAMTSUM_CNY", netValueFlag ? "" : toString(sub.getEndamtsumCny()));
			data.put("ENDQTY", netValueFlag ? "" : toString(sub.getEndqty()));
        	/*
        	data.put("PROD_VALUE", toString(sub.getProdValue()));
        	data.put("PROD_VALUE_CNY",toString(sub.getProdValueCny()));
        	data.put("UNIT_VALUE_TOTAL", toString(sub.getUnitValueTotal()));
        	data.put("UNIT_VALUE_TOTAL_CNY", toString(sub.getUnitValueTotalCny()));
        	data.put("PROD_HIGH_YIELD", toString(sub.getProdHighYield()));
        	data.put("PROD_LOW_YIELD", toString(sub.getProdLowYield()));
        	*/
			data.put("PROD_VALUE", netValueFlag ? toString(sub.getProdValue()) : "");
			data.put("PROD_VALUE_CNY",netValueFlag ? toString(sub.getProdValueCny()) : "");
			data.put("UNIT_VALUE_TOTAL", netValueFlag ? toString(sub.getUnitValueTotal()) : "");
			data.put("UNIT_VALUE_TOTAL_CNY", netValueFlag ? toString(sub.getUnitValueTotalCny()) : "");
			data.put("PROD_HIGH_YIELD", sub.getCcy()==null?toString(sub.getProdHighYield()):"");
			data.put("PROD_LOW_YIELD", sub.getCcy()==null?toString(sub.getProdLowYield()):"");

			dataList.add(data);
			map.put(key,dataList);
		}
		//循环发行机构分组
		for (String key:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(key);
			String[] str = StringUtil.split(key, "_");
			String orgCode = str[0];
			Date date = DateUtil.parse(str[1]);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PCD,"PCD1",date,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//        if(!CollectionUtils.isEmpty(doManulList))
//        	pbcPrdCashRaiseSubService001.doManul(searchParam);
		return result;
	}

	//资产负债信息
	private List<String> genPVD1(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("reporttypeList",StringUtil.splitToList(",", PbcReportType.PBC_REPORT_ONEPRD_ASSETDEBT));
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcMonthReport> subList = pbcMonthReportMapper.searchPageForOnePrd(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		// 查询已配置的科目号
		List<ChbTypeMappingEx>  subjectNoMappingList = chbTypeMappingExMapper
				.selectByMappingcode(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETDATA);
		Map<String,String> mappingMap = new HashMap<String,String>();
		for(ChbTypeMappingEx dto : subjectNoMappingList){
			mappingMap.put(dto.getSrcfield1(), dto.getObjfield1());
		}
		//【dat文件生成留痕】
		List<String> subKeys = new ArrayList<String>();
		for(PbcMonthReport sub :subList){
			List<PbcReportAssetDebtDetailDto> details = pbcReportAssetDebtDetailMapper.selectByReportuuid(sub.getReportuuid());
			if(details.size()==0)
				continue;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcAccountId", sub.getPbcAccountId());
			List<PbcPrdAccountSub> infos = pbcPrdAccountSubMapper.searchPage(param);
			if(infos.isEmpty()) continue;
			String key = infos.get(0).getIssuePartyId()+"_"+ DateUtil.format(sub.getEnddate());
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(key)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(key);
			}
			for(PbcReportAssetDebtDetailDto detail :details){
				//组装数据
				if(mappingMap.get(detail.getPropertycode())==null) continue;
				if(!StringUtil.isNotEmpty(detail.getCcyAmount())) continue;
				LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
				//【dat文件生成留痕】数据的key
				if(!subKeys.contains(sub.getReportuuid())) {
					data.put("datakey",sub.getReportuuid());
					subKeys.add(sub.getReportuuid());
				}
				data.put("date", DateUtil.format(sub.getEnddate()));
				data.put("PbcAccountId", sub.getPbcAccountId());
				data.put("type", mappingMap.get(detail.getPropertycode()));
				data.put("ccy", "BWB");
				data.put("total1", toString(detail.getTotal()));
				data.put("total2", toString(detail.getTotal()));
				dataList.add(data);

				//币种维度
				if(StringUtil.isNotEmpty(detail.getCcyAmount())){
					for(String ccyAmout :StringUtil.splitToList( ";",detail.getCcyAmount())){
						if(StringUtil.isNotEmpty(ccyAmout)){
							List<String> ccyAmoutList = StringUtil.splitToList(",",ccyAmout);
							String ccy =toString(ccyAmoutList.get(0));
							String amount = toString(ccyAmoutList.get(1));
							String amountCny =toString(ccyAmoutList.get(2));
							LinkedHashMap<String,String> dataC= new LinkedHashMap<String,String>();
							dataC.put("date", DateUtil.format(sub.getEnddate()));
							dataC.put("PbcAccountId", sub.getPbcAccountId());
							dataC.put("type", mappingMap.get(detail.getPropertycode()));
							dataC.put("ccy", ccy);
							dataC.put("total1", amount);
							dataC.put("total2", amountCny);
							dataList.add(dataC);
						}
					}
				}

			}
			dataList.sort(new Comparator<LinkedHashMap<String,String>>(){
				@Override
				public int compare(LinkedHashMap<String,String> o1,LinkedHashMap<String,String> o2){
					int result=0;
					if(o1==null&&o2==null){
						return result;
					}else if(o1==null&&o2!=null){
						result=-1;
					}else if(o1 !=null&&o2==null){
						result=1;
					}else{
						if(o1.get("PbcAccountId")==null&&o2.get("PbcAccountId")==null){
							result=o1.get("type").compareTo(o2.get("type"));
						}else if(o1.get("PbcAccountId")==null&&o2.get("PbcAccountId")!=null){
							result=-1;
						}else if(o1.get("PbcAccountId")!=null&&o2.get("PbcAccountId")==null){
							result=1;
						}else{
							result=o1.get("PbcAccountId").compareTo(o2.get("PbcAccountId"));
							if(result==0){
								result=o1.get("type").compareTo(o2.get("type"));
							}
						}
					}
					return result;
				}
			});
			map.put(key,dataList);
		}
		//循环发行机构分组
		for (String key:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(key);
			String[] str = StringUtil.split(key, "_");
			String orgCode = str[0];
			Date date = DateUtil.parse(str[1]);
			//月报的压缩包日期使用月末日期
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PVD,"PVD1",date,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//        if(!CollectionUtils.isEmpty(subList))
//        	pbcPrdAssetDebtSubService001.doManul(searchParam);
		return result;
	}

	//贷款明细信息
	private List<String> genPVD2(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("reporttypeList",StringUtil.splitToList(",", PbcReportType.PBC_REPORT_ONEPRD_ASSETDEBT));
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcMonthReport> subList = pbcMonthReportMapper.searchPageForOnePrd(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcMonthReport sub :subList){
			List<PbcLoanDetailInfoSub> details = pbcReportAssetDebtDetailMapper.selectDKByReportuuid(sub.getReportuuid());
			if(details.size()==0)
				continue;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcAccountId", sub.getPbcAccountId());
			List<PbcPrdAccountSub> infos = pbcPrdAccountSubMapper.searchPage(param);
			if(infos.isEmpty()) continue;
			String key = infos.get(0).getIssuePartyId()+"_"+ DateUtil.format(sub.getEnddate());
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(key)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(key);
			}
			for(PbcLoanDetailInfoSub detail :details){
				//组装数据
//        		if(!StringUtil.isNotEmpty(detail.getCcyAmount())) continue;
				LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
				data.put("date", DateUtil.format(sub.getEnddate()));//日期
				data.put("pbcAccountId", sub.getPbcAccountId());//资产池代码
				data.put("loansType", detail.getLoansType());//贷款种类
				data.put("loanTransferorId", detail.getLoanTransferorId());//贷款转让方机构代码
				data.put("loanContractOrgId", detail.getLoanContractOrgId());//贷款合同原始发放机构代码
				data.put("loanContractOrgCounty", detail.getLoanContractOrgCounty());//贷款合同原始发放机构所在地代码
				data.put("borrowerType", detail.getBorrowerType());//借款人类型
				data.put("borrowerCounty", detail.getBorrowerCounty());//地区代码
				data.put("borrowerId", detail.getBorrowerId());//借款人代码
				data.put("industryInfo", detail.getIndustryInfo());//行业信息
				data.put("enterpriseInvestorType", detail.getEnterpriseInvestorType());//企业出资人经济成分
				data.put("enterpriseScale", detail.getEnterpriseScale());//企业规模
				data.put("loanBasisId", detail.getLoanBasisId());//贷款借据编码
				data.put("loanPrdType", detail.getLoanPrdType());//贷款产品类别
				data.put("loanActualInvest", detail.getLoanActualInvest());//贷款实际投向
				data.put("loanVdate", DateUtil.format(detail.getLoanVdate(), DateUtil.DEFAULT_DATE_FORMAT_EN));//贷款发放日期
				data.put("loanMdate", DateUtil.format(detail.getLoanMdate(), DateUtil.DEFAULT_DATE_FORMAT_EN));//贷款到期日期
				data.put("loanExtensionDueDate", DateUtil.format(detail.getLoanExtensionDueDate(), DateUtil.DEFAULT_DATE_FORMAT_EN));//贷款展期到期日期
				data.put("rateFixed", detail.getRateFixed());//利率是否固定
				data.put("rateLevel", toString(detail.getRateLevel()));//利率水平
				data.put("loanGuaranteeMode", detail.getLoanGuaranteeMode());//贷款担保方式
				data.put("loanQuality", detail.getLoanQuality());//贷款质量
				data.put("loanState", detail.getLoanState());//贷款状态
				data.put("loanTransferorRate", toString(detail.getLoanTransferorRate()));//贷款转让折扣率
				data.put("origCcy", detail.getOrigCcy());//原始合同币种代码
				data.put("origContractAmount", toString(detail.getOrigContractAmount()));//原始合同金额
				data.put("origContractAmountCny", toString(detail.getOrigContractAmountCny()));//原始合同金额折人民币
				data.put("amtCcy", detail.getAmtCcy());//贷款余额币种代码
				data.put("contractAmount", toString(detail.getContractAmount()));//贷款余额
				data.put("contractAmountCny", toString(detail.getContractAmountCny()));//贷款余额折人民币

				dataList.add(data);
			}
			map.put(key,dataList);
		}
		//循环发行机构分组
		for (String key:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(key);
			String[] str = StringUtil.split(key, "_");
			String orgCode = str[0];
			Date date = DateUtil.parse(str[1]);
			//月报的压缩包日期使用月末日期
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PVD,"PVD2",date,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		return result;
	}

	//股权及其他股权资产中资金信托、理财产品投资明细信息
	private List<String> genPVD3(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("reporttypeList",StringUtil.splitToList(",", PbcReportType.PBC_REPORT_ONEPRD_ASSETDEBT));
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcMonthReport> subList = pbcMonthReportMapper.searchPageForOnePrd(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcMonthReport sub :subList){
			List<PbcLoanDetailInfoSub> details = pbcReportAssetDebtDetailMapper.selectXTByReportuuid(sub.getReportuuid());
			if(details.size()==0)
				continue;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcAccountId", sub.getPbcAccountId());
			List<PbcPrdAccountSub> infos = pbcPrdAccountSubMapper.searchPage(param);
			if(infos.isEmpty()) continue;
			String key = infos.get(0).getIssuePartyId()+"_"+ DateUtil.format(sub.getEnddate());
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(key)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(key);
			}
			for(PbcLoanDetailInfoSub detail :details){
				//组装数据
//        		if(!StringUtil.isNotEmpty(detail.getCcyAmount())) continue;
				LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
				data.put("date", DateUtil.format(sub.getEnddate()));//日期
				data.put("pbcAccountId", sub.getPbcAccountId());//资产池代码
				data.put("stockType", detail.getStockType());//股权种类
				data.put("capitalTrustOrgCode", detail.getCapitalTrustOrgCode());//产品发起机构编码
				data.put("pobcassetid", detail.getPobcassetid());//人行资金信托代码
				data.put("ccy", detail.getCcy());//币种代码
				data.put("capitalTrustAmount", toString(detail.getCapitalTrustAmount()));//资金信托产品余额
				data.put("capitalTrustAmountCny", toString(detail.getCapitalTrustAmountCny()));//资金信托产品余额折人民币

				dataList.add(data);
			}
			map.put(key,dataList);
		}
		//循环发行机构分组
		for (String key:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(key);
			String[] str = StringUtil.split(key, "_");
			String orgCode = str[0];
			Date date = DateUtil.parse(str[1]);
			//月报的压缩包日期使用月末日期
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_PVD,"PVD3",date,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		return result;
	}

	//资产池、产品基本信息修改申请单
	private List<String> genAF11(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		//资产
		List<PbcPrdAccountSub> subList = pbcPrdAccountSubMapper.searchPage(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map1=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcPrdAccountSub sub :subList){
			String issuePartyId = sub.getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map1.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				continue;
			}
			//组装数据
			//申请单
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			String name=getRequestNameAndRemark(APPLY_TYPE_3)==null?"":getRequestNameAndRemark(APPLY_TYPE_3)[0];
			String remark=getRequestNameAndRemark(APPLY_TYPE_3)==null?"":getRequestNameAndRemark(APPLY_TYPE_3)[1];
			data.put("apply_name", getBankName(issuePartyId) + name);
			data.put("apply_date", DateUtil.format(sub.getStatusDate()));
			data.put("apply_type", "3");
			data.put("issue_party_id", issuePartyId);
			data.put("remark",remark);
			dataList.add(data);
			map1.put(issuePartyId,dataList);
		}

		//产品
		List<PbcPrdBaseinfoSub> subList2 = pbcPrdBaseinfoSubMapper.searchPage(searchParam);
		Map<String,List<LinkedHashMap<String,String>>> map2=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcPrdBaseinfoSub sub :subList2){
			String issuePartyId = sub.getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map2.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				continue;
			}
			//组装数据
			//申请单
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			String name=getRequestNameAndRemark(APPLY_TYPE_1)==null?"":getRequestNameAndRemark(APPLY_TYPE_1)[0];
			String remark=getRequestNameAndRemark(APPLY_TYPE_1)==null?"":getRequestNameAndRemark(APPLY_TYPE_1)[1];
			data.put("apply_name", getBankName(issuePartyId) + name);
			data.put("apply_date", DateUtil.format(sub.getStatusDate()));
			data.put("apply_type", "1");
			data.put("issue_party_id", issuePartyId);
			data.put("remark",remark);
			dataList.add(data);
			map2.put(issuePartyId,dataList);
		}
		Map<String,List<LinkedHashMap<String,String>>> resultMap=new HashMap<String,List<LinkedHashMap<String,String>>>();
		//map2中多的
		for(String key : map2.keySet()){
			if(map1.get(key)==null)
				resultMap.put(key, map2.get(key));
		}
		//map和map2重复的合并
		for(String key : map1.keySet()){
			if(map2.get(key)!=null){
				List<LinkedHashMap<String,String>> list =map1.get(key);
				list.addAll(map2.get(key));
				resultMap.put(key, list);
			}else{
				resultMap.put(key,map1.get(key));
			}
		}
		//循环发行机构分组
		for (String orgCode:resultMap.keySet() ) {
			List<LinkedHashMap<String,String>>prods = resultMap.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF1,"AF11",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		return result;
	}

	//资产池、产品基本信息修改
	private List<String> genAF12(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		//资产
		List<PbcPrdAccountSub> subList = pbcPrdAccountSubMapper.searchPage(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcPrdAccountSub sub :subList){
			String issuePartyId = sub.getIssuePartyId();
			Map<String, Object> op = new HashMap<String, Object>();
			op.put("sendStatusList",
					StringUtil.splitToList(",",DictCodeListExt2.报送状态_手工处理.getValue()+","+DictCodeListExt2.报送状态_接口报送.getValue()));
			op.put("pbcAccountIdList",Arrays.asList(sub.getPbcAccountId()));
			op.put("issuePartyId",issuePartyId);
			List<PbcPrdAccountSub> oList = pbcPrdAccountSubMapper.searchPage(op);
			if(oList.size()==0)
				continue;
			PbcPrdAccountSub oSub = oList.get(0);
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(issuePartyId);
			}
			//组装数据
			if(!sub.getAccountName().equals(oSub.getAccountName())){
				LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
				//【dat文件生成留痕】数据的key
				data.put("datakey",sub.getRegistSeqno());
				data.put("pbcaccountid", sub.getPbcAccountId());
				data.put("updateType","103");
				data.put("before",oSub.getAccountName());
				data.put("now",sub.getAccountName());
				dataList.add(data);
			}
			//组装数据
			if(!DateUtil.format(sub.getVdate()).equals(DateUtil.format(oSub.getVdate()))){
				LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
				//【dat文件生成留痕】数据的key
				data.put("datakey",sub.getRegistSeqno());
				data.put("pbcaccountid", sub.getPbcAccountId());
				data.put("updateType","104");
				data.put("before",DateUtil.format(oSub.getVdate()));
				data.put("now",DateUtil.format(sub.getVdate()));
				dataList.add(data);
			}
			map.put(issuePartyId,dataList);
		}

		//产品
		searchParam.put("datFlag","01");
		List<PbcPrdBaseinfoSub> subList2 = pbcPrdBaseinfoSubMapper.searchPage(searchParam);
		//【dat文件生成留痕】
		List<String> subKeys = new ArrayList<String>();
		for(PbcPrdBaseinfoSub sub :subList2){
			String issuePartyId = sub.getIssuePartyId();
			Map<String, Object> op = new HashMap<String, Object>();
			op.put("sendStatusList",
					StringUtil.splitToList(",",DictCodeListExt2.报送状态_手工处理.getValue()+","+DictCodeListExt2.报送状态_接口报送.getValue()));
			op.put("pbcProdIdList",Arrays.asList(sub.getPbcProdId()));
			op.put("issuePartyId",issuePartyId);
			op.put("datFlag","01");
			List<PbcPrdBaseinfoSub> oList = pbcPrdBaseinfoSubMapper.searchPage(op);
			if(oList.size()==0)
				continue;
			PbcPrdBaseinfoSub oSub = oList.get(0);
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(issuePartyId);
			}
			//组装数据
			Map<String,String> prdMap  = prdToMap(sub);
			Map<String,String> oPrdMap  = prdToMap(oSub);
			List<ChbTypeMappingEx>  subjectNoMappingList = chbTypeMappingExMapper
					.selectByMappingcode(PbcAssetPositionConstants.MAPPINGCODE_PBCPRD_UPDATETYPE);
			for(ChbTypeMappingEx dto : subjectNoMappingList){
				String updateType = dto.getSrcfield1();
				String column = dto.getObjfield1();
				if(!toString(prdMap.get(column)).equals(toString(oPrdMap.get(column)))){
					LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
					//【dat文件生成留痕】数据的key
					if(!subKeys.contains(sub.getRegistSeqno())) {
						data.put("datakey",sub.getRegistSeqno());
						subKeys.add(sub.getRegistSeqno());
					}
					data.put("pbcaccountid", sub.getPbcProdId());
					data.put("updateType",updateType);
					data.put("before",oPrdMap.get(column));
					data.put("now",prdMap.get(column));
					dataList.add(data);
				}
			}
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF1,"AF12",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//        if(!CollectionUtils.isEmpty(subList))
//        	pbcPrdAccountSubService001.doManul(searchParam);
//        if(!CollectionUtils.isEmpty(subList2))
//        	pbcPrdBaseinfoSubService001.doManul(searchParam);
		return result;
	}

	private Map<String,String> prdToMap(PbcPrdBaseinfoSub sub){
		Map<String,String> data = new HashMap<String,String>();
		data.put("pbc_prod_type", sub.getPbcProdType());
		data.put("prod_name", sub.getProdName());
		data.put("prod_brand", sub.getProdBrand());
		data.put("prod_stage", toString(sub.getProdStage()));
		data.put("prod_id", sub.getProdId());
		data.put("raise_type", sub.getRaiseType());
		data.put("manage_type", sub.getManageType());
		data.put("operate_mode", sub.getOperateMode());
		data.put("ivstorientation", sub.getIvstorientation());
		data.put("busi_model", sub.getBusiModel());
		data.put("income_guaran_ident", sub.getIncomeGuaranIdent());
		data.put("prin_guaran_ident", sub.getPrinGuaranIdent());
		data.put("high_yield_issue", toString(sub.getHighYieldIssue()));
		data.put("low_yield_issue", toString(sub.getLowYieldIssue()));
		data.put("raise_vdate", DateUtil.format(sub.getRaiseVdate()));
		data.put("raise_enddate", DateUtil.format(sub.getRaiseEnddate()));
		data.put("term_right_flag", sub.getTermRightFlag());
		data.put("cus_redeem_flag", sub.getCusRedeemFlag());
		data.put("credit_flag", sub.getCreditFlag());
		data.put("in_dep_code", sub.getInDepCode());
		data.put("out_dep_ctry", sub.getOutDepCtry());
		data.put("out_dep_name", sub.getOutDepName());
		data.put("vdate", DateUtil.format(sub.getVdate()));
		data.put("mdate", DateUtil.format(sub.getMdate()));
		data.put("fiduciary_duty", sub.getFiduciaryDuty());
		data.put("cont_mode", sub.getContMode());
		data.put("is_constracte", sub.getIsConstracte());
		data.put("transfer_type", sub.getTransferType());
		data.put("identi_flag", sub.getIdentiFlag());
		data.put("cash_mana_cate", sub.getCashManaCate());
		data.put("cross_border_flag", sub.getCrossBorderFlag());
		data.put("zjxt_flag", sub.getZjxtFlag());
		data.put("customer_type", sub.getCustomerType());
		data.put("raise_ccy", sub.getRaiseCcy());
		data.put("cash_prin_ccy", sub.getCashPrinCcy());
		data.put("cash_prof_ccy", sub.getCashProfCcy());
		data.put("credit_mode", sub.getCreditMode());
		data.put("credit_type", sub.getCreditType());
		return data;
	}


	private List<String> genAF21(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//PIB2查询PBC_INIT_PROD_RAISING_SUB，新建未报送的数据
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		List<PbcInitProdRaisingSub> subList = pbcInitProdRaisingSubMapper.searchPage(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcInitProdRaisingSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				continue;
			}
			//组装数据
			//申请单
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			String name=getRequestNameAndRemark(APPLY_TYPE_5)==null?"":getRequestNameAndRemark(APPLY_TYPE_5)[0];
			String remark=getRequestNameAndRemark(APPLY_TYPE_5)==null?"":getRequestNameAndRemark(APPLY_TYPE_5)[1];
			data.put("apply_name", getBankName(issuePartyId) + name);
			data.put("apply_date", DateUtil.format(sub.getStatusDate()));
			data.put("apply_type", "5");
			data.put("issue_party_id", issuePartyId);
			data.put("remark",remark);
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF2,"AF21",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		return result;
	}

	private List<String> genAF22(Map<String, Object> searchParam, Date genDate, Map<String, List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("statusList", StringUtil.splitToList(",", DictCodeListExt2.报送数据状态_修改.getValue()));
		// 不按维度
		List<PbcInitProdRaisingSub> subList = pbcInitProdRaisingSubMapper.searchPage(searchParam);
		List<PbcInitProdRaisingSub> doManulList  = new ArrayList<PbcInitProdRaisingSub>();
		doManulList.addAll(subList);
		// 以buyPlace,ccy维度
		searchParam.put("buyPlace", "000000");
		searchParam.put("ccy", "BWB");
		subList.addAll(pbcInitProdRaisingSubMapper.selectByDimension(searchParam));
		// 以buyPlace维度
		searchParam.remove("ccy");
		subList.addAll(pbcInitProdRaisingSubMapper.selectByDimension(searchParam));
		// 以ccy维度
		searchParam.remove("buyPlace");
		searchParam.put("ccy", "BWB");
		subList.addAll(pbcInitProdRaisingSubMapper.selectByDimension(searchParam));
		// 清除参数
		searchParam.remove("ccy");
		// 按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String, List<LinkedHashMap<String, String>>> map = new HashMap<String, List<LinkedHashMap<String, String>>>();
		for (PbcInitProdRaisingSub sub : subList) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if (infos.size() == 0 || infos.get(0).getIssuePartyId() == null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			List<LinkedHashMap<String, String>> dataList = null;
			if (map.get(issuePartyId) == null) {
				dataList = new ArrayList<LinkedHashMap<String, String>>();
			} else {
				dataList = (List<LinkedHashMap<String, String>>) map.get(issuePartyId);
			}
			// 组装数据
			LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
			//【dat文件生成留痕】数据的key
			data.put("datakey",sub.getRegistSeqno() == null ? UUID.randomUUID().toString() : sub.getRegistSeqno());
			data.put("pbc_prod_id", sub.getPbcProdId());
			data.put("buy_place", sub.getBuyPlace());
			data.put("customer_type1", sub.getCustomerType1());
			data.put("ccy", sub.getCcy());
			data.put("init_raising_amtsum", toString("BWB".equals(sub.getCcy()) ? sub.getInitRaisingAmtsumcny()
					: sub.getInitRaisingAmtsum()));
			data.put("init_raising_amtsumcny", toString(sub.getInitRaisingAmtsumcny()));
			data.put("init_raising_qty", toString(sub.getInitRaisingQty()));
			dataList.add(data);
			map.put(issuePartyId, dataList);
		}
		// 循环发行机构分组
		for (String orgCode : map.keySet()) {
			List<LinkedHashMap<String, String>> prods = map.get(orgCode);
			result.addAll(PbcClassicsDataFileHelper.genFile(prods, PbcClassicsDataFileHelper.DATAFILE_TYPE_AF2, "AF22",
					genDate, getOrgCode(orgCode), seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//		if(!CollectionUtils.isEmpty(doManulList))
//			pbcPrdInitRaiseSubService001.doManul(searchParam);
		return result;
	}

	private List<String> genAF31(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//PIE1查询PBC_PRD_BASEINFO_END_SUB，新建未报送的数据
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		List<PbcPrdBaseinfoEndSub> subList = pbcPrdBaseinfoEndSubMapper.searchPage(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcPrdBaseinfoEndSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				continue;
			}
			//组装数据
			//申请单
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			String name=getRequestNameAndRemark(APPLY_TYPE_2)==null?"":getRequestNameAndRemark(APPLY_TYPE_2)[0];
			String remark=getRequestNameAndRemark(APPLY_TYPE_2)==null?"":getRequestNameAndRemark(APPLY_TYPE_2)[1];
			data.put("apply_name", getBankName(issuePartyId) + name);
			data.put("apply_date", DateUtil.format(sub.getStatusDate()));
			data.put("apply_type", "2");
			data.put("issue_party_id", issuePartyId);
			data.put("remark",remark);
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF3,"AF31",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}

		return result;
	}

	private List<String> genAF32(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//PIE1查询PBC_PRD_BASEINFO_END_SUB，新建未报送的数据
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		List<PbcPrdBaseinfoEndSub> subList = pbcPrdBaseinfoEndSubMapper.searchPage(searchParam);
		List<PbcPrdBaseinfoEndSub> doManulList  = new ArrayList<PbcPrdBaseinfoEndSub>();
		doManulList.addAll(subList);
		//兑付客户收益率
		subList.addAll(pbcPrdBaseinfoEndSubMapper.selectByDimension(searchParam));
		//按币种维度
		searchParam.put("ccy", "BWB");
		subList.addAll(pbcPrdBaseinfoEndSubMapper.selectByDimension(searchParam));
		searchParam.remove("ccy");
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcPrdBaseinfoEndSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(issuePartyId);
			}
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			//【dat文件生成留痕】数据的key
			data.put("datakey",sub.getRegistSeqno() == null ? UUID.randomUUID().toString() : sub.getRegistSeqno());
			data.put("pbc_prod_id", sub.getPbcProdId());
			data.put("mdate", DateUtil.format(sub.getMdate()));
			data.put("ccy", sub.getCcy());
			data.put("profit",toString("BWB".equals(sub.getCcy())?sub.getProfitCny():sub.getProfit()));
			data.put("profit_cny", toString(sub.getProfitCny()));
			data.put("custom_profit", toString("BWB".equals(sub.getCcy())?sub.getCustomProfitCny():sub.getCustomProfit()));
			data.put("custom_profit_cny", toString(sub.getCustomProfitCny()));
			//过滤searchPage方法查出的数据，不需要兑付客户收益率
			data.put("cus_year_yield", sub.getCcy()==null?toString(sub.getCusYearYield()):"");
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF3,"AF32",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//    	if(!CollectionUtils.isEmpty(doManulList))
//    		prdBaseinfoEndSubService001.doManul(searchParam);
		return result;
	}


	private List<String> genAF41(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//查询PBC_PROD_RAISING_SUB，新建未报送的数据
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcProdRaiseSub> subList = pbcProdRaiseSubMapper.searchPageForOnePrd(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcProdRaiseSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			param.put("datFlag","01");
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				continue;
			}
			//组装数据
			//申请单
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			String name=getRequestNameAndRemark(APPLY_TYPE_6)==null?"":getRequestNameAndRemark(APPLY_TYPE_6)[0];
			String remark=getRequestNameAndRemark(APPLY_TYPE_6)==null?"":getRequestNameAndRemark(APPLY_TYPE_6)[1];
			data.put("apply_name", getBankName(issuePartyId) + name);
			data.put("apply_date", DateUtil.format(genDate));
			data.put("apply_type", "6");
			data.put("issue_party_id", issuePartyId);
			data.put("remark",remark);
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF4,"AF41",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		return result;
	}

	private List<String> genAF42(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//查询PBC_PROD_RAISING_SUB，新建未报送的数据
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcProdRaiseSub> subList = pbcProdRaiseSubMapper.searchPageForOnePrd(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		List<String> exits = new ArrayList<String>();
		for(PbcProdRaiseSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			param.put("datFlag","01");
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			//AF42每个机构，每个产品只需要一条
			String eKey=issuePartyId+"-"+sub.getPbcProdId();
			if(exits.contains(eKey)){
				continue;
			}else{
				exits.add(eKey);
			}

			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(issuePartyId);
			}
			//组装数据
			//申请单
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			String name=getRequestNameAndRemark(APPLY_TYPE_6)==null?"":getRequestNameAndRemark(APPLY_TYPE_6)[0];
//        	String remark=getRequestNameAndRemark(APPLY_TYPE_6)==null?"":getRequestNameAndRemark(APPLY_TYPE_6)[1];
			data.put("apply_name", getBankName(issuePartyId) + name);
			data.put("apply_date", DateUtil.format(sub.getRaisingDate()));
			data.put("pbc_prod_id", sub.getPbcProdId());
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF4,"AF42",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		return result;
	}

	private List<String> genAF43(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		//查询PBC_PROD_RAISING_SUB，新建未报送的数据
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcProdRaiseSub> subList = pbcProdRaiseSubMapper.searchPageForOnePrd(searchParam);
		List<PbcProdRaiseSub> doManulList  = new ArrayList<PbcProdRaiseSub>();
		doManulList.addAll(subList);
		//查询收益率
		searchParam.put("yield", "yield");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension(searchParam));
		searchParam.remove("yield");
		//按币种，区域维度
		searchParam.put("buyPlace", "000000");
		searchParam.put("ccy", "BWB");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension(searchParam));
		//按币种维度
		searchParam.remove("buyPlace");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension(searchParam));
		//按区域维度
		searchParam.remove("ccy");
		searchParam.put("buyPlace", "000000");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension(searchParam));
		//清除参数
		searchParam.remove("buyPlace");
		searchParam.put("nyield", "000000");
		searchParam.put("yield", "000000");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension2(searchParam));// 净值型产品的单位净值-1：原币

		searchParam.put("ccy", "BWB");
		subList.addAll(pbcProdRaiseSubMapper.selectByDimension2(searchParam));// 净值型产品的单位净值-2：BWB
		searchParam.remove("ccy");
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		for(PbcProdRaiseSub sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcProdId", sub.getPbcProdId());
			param.put("datFlag","01");
			List<PbcPrdBaseinfoSub> infos = pbcPrdBaseinfoSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId();
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(issuePartyId)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(issuePartyId);
			}
			//组装数据
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			// 净值型标识
			boolean netValueFlag = StringUtil.isEmpty(sub.getBuyPlace()) && StringUtil.isNotEmpty(sub.getCcy());
			//【dat文件生成留痕】数据的key
			data.put("datakey",sub.getRegistSeqno() == null ? UUID.randomUUID().toString() : sub.getRegistSeqno());
			data.put("RAISING_DATE", DateUtil.format(sub.getRaisingDate()));
			data.put("PBC_PROD_ID", sub.getPbcProdId());
			data.put("BUY_PLACE", sub.getBuyPlace());
			data.put("CUSTOMER_TYPE1", sub.getCustomerType1());
			data.put("CCY", sub.getCcy());
			data.put("RAISING_AMTSUM", toString("BWB".equals(sub.getCcy())?sub.getRaisingAmtsumCny():sub.getRaisingAmtsum()));
			data.put("RAISING_AMTSUM_CNY", toString(sub.getRaisingAmtsumCny()));
			data.put("RAISING_QTY",toString(sub.getRaisingQty()));
			data.put("REDAMT",  toString("BWB".equals(sub.getCcy())?sub.getRedamtCny():sub.getRedamt()));
			data.put("REDAMT_CNY",toString(sub.getRedamtCny()));
			data.put("REDQTY", toString(sub.getRedqty()));
			data.put("ENDAMTSUM", toString("BWB".equals(sub.getCcy())?sub.getEndamtsumCny(): sub.getEndamtsum()));
			data.put("ENDAMTSUM_CNY", toString(sub.getEndamtsumCny()));
			data.put("ENDQTY", toString(sub.getEndqty()));
			data.put("PROD_VALUE", netValueFlag ? toString(sub.getProdValue()) : "");
			data.put("PROD_VALUE_CNY",netValueFlag ? toString(sub.getProdValueCny()) : "");
			data.put("UNIT_VALUE_TOTAL", netValueFlag ? toString(sub.getUnitValueTotal()) : "");
			data.put("UNIT_VALUE_TOTAL_CNY", netValueFlag ? toString(sub.getUnitValueTotalCny()) : "");
			data.put("PROD_HIGH_YIELD", sub.getCcy()==null?toString(sub.getProdHighYield()):"");
			data.put("PROD_LOW_YIELD", sub.getCcy()==null?toString(sub.getProdLowYield()):"");
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String orgCode:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(orgCode);
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF4,"AF43",genDate,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//改为手工处理状态
		//手工处理
//    	if(!CollectionUtils.isEmpty(doManulList))
//    		pbcPrdCashRaiseSubService001.doManul(searchParam);
		return result;
	}

	private List<String> genAF51(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		searchParam.put("reporttypeList",StringUtil.splitToList(",", PbcReportType.PBC_REPORT_ONEPRD_ASSETDEBT));
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcMonthReport> subList = pbcMonthReportMapper.searchPageForOnePrd(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();

		for(PbcMonthReport sub :subList){
			List<PbcReportAssetDebtDetailDto> details = pbcReportAssetDebtDetailMapper.selectByReportuuid(sub.getReportuuid());
			if(details.size()==0)
				continue;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcAccountId", sub.getPbcAccountId());
			List<PbcPrdAccountSub> infos = pbcPrdAccountSubMapper.searchPage(param);
			String issuePartyId = infos.get(0).getIssuePartyId() + "_" + DateUtil.format(sub.getEnddate());
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(infos.get(0).getIssuePartyId())==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				continue;
			}
			//组装数据
			//申请单
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			String name=getRequestNameAndRemark(APPLY_TYPE_7)==null?"":getRequestNameAndRemark(APPLY_TYPE_7)[0];
			String remark=getRequestNameAndRemark(APPLY_TYPE_7)==null?"":getRequestNameAndRemark(APPLY_TYPE_7)[1];
			data.put("apply_name", getBankName(infos.get(0).getIssuePartyId()) + name);
			data.put("apply_date", DateUtil.format(genDate));
			data.put("apply_type", "7");
			data.put("issue_party_id", infos.get(0).getIssuePartyId());
			data.put("remark",remark);
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String key:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(key);
			String[] str = StringUtil.split(key, "_");
			String orgCode = str[0];
			Date date = DateUtil.parse(str[1]);
			//月报的压缩包日期使用月末日期
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF5,"AF51",date,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		return result;
	}

	private List<String> genAF52(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		searchParam.put("reporttypeList",StringUtil.splitToList(",", PbcReportType.PBC_REPORT_ONEPRD_ASSETDEBT));
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcMonthReport> subList = pbcMonthReportMapper.searchPageForOnePrd(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();

		List<String> exits = new ArrayList<String>();
		for(PbcMonthReport sub :subList){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcAccountId", sub.getPbcAccountId());
			List<PbcPrdAccountSub> infos = pbcPrdAccountSubMapper.searchPage(param);
			if(infos.size()==0||infos.get(0).getIssuePartyId()==null)
				continue;
			String issuePartyId = infos.get(0).getIssuePartyId()+"_"+DateUtil.format(sub.getEnddate());
			//AF42每个机构，每个产品只需要一条
			String eKey=infos.get(0).getIssuePartyId()+"-"+sub.getPbcAccountId();
			if(exits.contains(eKey)){
				continue;
			}else{
				exits.add(eKey);
			}

			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(infos.get(0).getIssuePartyId())==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(infos.get(0).getIssuePartyId());
			}
			//组装数据
			//申请单
			LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
			String name=getRequestNameAndRemark(APPLY_TYPE_7)==null?"":getRequestNameAndRemark(APPLY_TYPE_7)[0];
//        	String remark=getRequestNameAndRemark(APPLY_TYPE_7)==null?"":getRequestNameAndRemark(APPLY_TYPE_7)[1];
			data.put("apply_name", getBankName(infos.get(0).getIssuePartyId()) + name);
			data.put("apply_date", DateUtil.format(sub.getEnddate()));
			data.put("pbc_account_id", sub.getPbcAccountId());
			dataList.add(data);
			map.put(issuePartyId,dataList);
		}
		//循环发行机构分组
		for (String key:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(key);
			String[] str = StringUtil.split(key, "_");
			String orgCode = str[0];
			Date date = DateUtil.parse(str[1]);
			//月报的压缩包日期使用月末日期
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF5,"AF52",date,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		return result;
	}

	private List<String> genAF53(Map<String, Object> searchParam,Date genDate,Map<String,List<String>> seqMap, boolean manualFlag,Map<String,Object> fileDataMap) {
		List<String> result = new ArrayList<String>();
		searchParam.put("statusList",
				StringUtil.splitToList(",",DictCodeListExt2.报送数据状态_修改.getValue()));
		searchParam.put("reporttypeList",StringUtil.splitToList(",", PbcReportType.PBC_REPORT_ONEPRD_ASSETDEBT));
		List<String> actualDateList = new ArrayList<String>(1<<1);
		if (manualFlag) {
			String startDateStr = (String)searchParam.get("startDate");
			String endDateStr = (String)searchParam.get("endDate");
			Date startDate = DateUtil.parse(startDateStr);
			Date endDate = DateUtil.parse(endDateStr);
			// 起始日期上个月的月末
			String actualStartDate = DateUtil.getLastDayOfLayMonth(startDate);
			// 结束日期上个月的月末
			String actualEndDate = DateUtil.getLastDayOfLayMonth(endDate);
			actualDateList.add(actualStartDate);
			actualDateList.add(actualEndDate);
			searchParam.put("actualDateList", actualDateList);

		}
		/* 手工生成需要生成查询日期范围包含月的上月数据 add by gaojian 2019-06-24 begin*/
		List<PbcMonthReport> subList = pbcMonthReportMapper.searchPageForOnePrd(searchParam);
		//按发行机构分组：Map<发行机构,产品List<Map<生成文件的属性键值对>>>
		Map<String,List<LinkedHashMap<String,String>>> map=new HashMap<String,List<LinkedHashMap<String,String>>>();
		// 查询已配置的科目号
		List<ChbTypeMappingEx>  subjectNoMappingList = chbTypeMappingExMapper
				.selectByMappingcode(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETDATA);
		Map<String,String> mappingMap = new HashMap<String,String>();
		for(ChbTypeMappingEx dto : subjectNoMappingList){
			mappingMap.put(dto.getSrcfield1(), dto.getObjfield1());
		}
		//【dat文件生成留痕】
		List<String> subKeys = new ArrayList<String>();
		for(PbcMonthReport sub :subList){
			List<PbcReportAssetDebtDetailDto> details = pbcReportAssetDebtDetailMapper.selectByReportuuid(sub.getReportuuid());
			if(details.size()==0)
				continue;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pbcAccountId", sub.getPbcAccountId());
			List<PbcPrdAccountSub> infos = pbcPrdAccountSubMapper.searchPage(param);
			if(infos.isEmpty()) continue;
			String key = infos.get(0).getIssuePartyId()+"_"+ DateUtil.format(sub.getEnddate());
			List<LinkedHashMap<String,String>> dataList = null;
			if(map.get(key)==null){
				dataList = new ArrayList<LinkedHashMap<String,String>>();
			}else{
				dataList =(List<LinkedHashMap<String,String>>)map.get(key);
			}
			for(PbcReportAssetDebtDetailDto detail :details){
				//组装数据
				if(mappingMap.get(detail.getPropertycode())==null) continue;
				if(!StringUtil.isNotEmpty(detail.getCcyAmount())) continue;
				LinkedHashMap<String,String> data= new LinkedHashMap<String,String>();
				//【dat文件生成留痕】数据的key
				if(!subKeys.contains(sub.getReportuuid())) {
					data.put("datakey",sub.getReportuuid());
					subKeys.add(sub.getReportuuid());
				}
				data.put("date", DateUtil.format(sub.getEnddate()));
				data.put("PbcAccountId", sub.getPbcAccountId());
				data.put("type", mappingMap.get(detail.getPropertycode()));
				data.put("ccy", "BWB");
				data.put("total1", toString(detail.getTotal()));
				data.put("total2", toString(detail.getTotal()));
				dataList.add(data);

				//币种维度
				if(StringUtil.isNotEmpty(detail.getCcyAmount())){
					for(String ccyAmout :StringUtil.splitToList( ";",detail.getCcyAmount())){
						if(StringUtil.isNotEmpty(ccyAmout)){
							List<String> ccyAmoutList = StringUtil.splitToList(",",ccyAmout);
							String ccy =toString(ccyAmoutList.get(0));
							String amount = toString(ccyAmoutList.get(1));
							String amountCny =toString(ccyAmoutList.get(2));
							LinkedHashMap<String,String> dataC= new LinkedHashMap<String,String>();
							dataC.put("date", DateUtil.format(sub.getEnddate()));
							dataC.put("PbcAccountId", sub.getPbcAccountId());
							dataC.put("type", mappingMap.get(detail.getPropertycode()));
							dataC.put("ccy", ccy);
							dataC.put("total1", amount);
							dataC.put("total2", amountCny);
							dataList.add(dataC);
						}
					}
				}
			}
			map.put(key,dataList);
		}
		//循环发行机构分组
		for (String key:map.keySet() ) {
			List<LinkedHashMap<String,String>>prods = map.get(key);
			String[] str = StringUtil.split(key, "_");
			String orgCode = str[0];
			Date date = DateUtil.parse(str[1]);
			//月报的压缩包日期使用月末日期
			result.addAll(
					PbcClassicsDataFileHelper.genFile(prods,PbcClassicsDataFileHelper.DATAFILE_TYPE_AF5,"AF53",date,getOrgCode(orgCode),seqMap,manualFlag,fileDataMap));
		}
		//更新为手工处理
//        if(!CollectionUtils.isEmpty(subList))
//        	pbcPrdAssetDebtSubService001.doManul(searchParam);
		return result;
	}


	private String getOrgCode(String issuePartyId){
		return pbcBankCodeMap.get(issuePartyId);
	}

	private String getBankName(String issuePartyId){
		return bankNameMap.get(issuePartyId);

	}

	private void initOrgcode(){
		List<SysInstRegBankCode001Dto> listCode= sysInstRegBankCode001Mapper.queryForPage(null);
		List<SysInstitutionDto> listName = sysInstRegBankCode001Service.selectAllInstitutionList();
		Map<String,String> bankNameMapTemp = new HashMap<String,String>();
		for(SysInstitutionDto dto : listName){
			bankNameMapTemp.put(dto.getInstitutionCode(), dto.getInstitutionName());
		}
		for(SysInstRegBankCode001Dto dto :listCode){
			pbcBankCodeMap.put(dto.getRegistBankCode(),dto.getPbcBankCode());
			bankNameMap.put(dto.getRegistBankCode(),bankNameMapTemp.get(dto.getOrgCode()));
		}
	}


	private String  toString(Object o) {
		if(o==null){
			return "";
		}else{
			return o.toString();
		}
	}

	private List<String> genCompressFile(Set<String>  files, boolean manualFlag) {
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
				// 手工生成的文件在压缩之后删除
				if (manualFlag) {
					//压缩删除文件
					ZipFileUtil.compressZip(outFile, true, allFiles);
					//删除文件夹
					new SafelyFile(folderPath).delete();
				} else {
					ZipFileUtil.compressZip(outFile, false, allFiles);//压缩文件
				}
//                new File(folderPath).delete();//删除文件夹
			}catch (Exception e) {
				logger.error(e.getMessage(),e);
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
				logger.error(e.getMessage(),e);
			}
		}
		return zipFileList;

	}

	public void importFileData(Set<String> files,Date genDate,Map<String,Object> fileDataMap){

		// 【dat文件生成留痕】
		// 数据key集合
		List<String> dataValueList;
		// 组装入库数据对象
		PbcDatfileDetailRelation relation;
		// 组装入库数据对象list
		List<PbcDatfileDetailRelation> relationList;
		//key=目录名文件名，value=文件内数据的key集合
		Map<String,List<String>> pathfilenameDatakeylistMap;
		// 批量入库每次插入数据量
		int insertBatchCount = PbcClassicsDataFileHelper.COMMIT_CONSTANTS;

		// 遍历目录名（zip）集合，获取每个目录（zip）的信息
		for(String folderPath : files){
			String flieName = folderPath.substring(folderPath.lastIndexOf("/")+1,folderPath.length());
			List<String> dataInfo = StringUtil.splitToList("_", flieName);
			PbcDataFile fileInfo = new PbcDataFile();
			String fileid = IDGenerator.generate(36);
			Date cdate = DateUtil.parse(dataInfo.get(2));
			String filecode = dataInfo.get(0);
			String fileseqno = dataInfo.get(3);
			fileInfo.setFileid(fileid);
			fileInfo.setFilecode(dataInfo.get(0));
			fileInfo.setHeadOrgcode(dataInfo.get(1));
			fileInfo.setOrgCode(CommonUtil.selectPbcBankCodeBack(dataInfo.get(1)));
			fileInfo.setCdate(genDate);
			fileInfo.setFileseqno(fileseqno);
			fileInfo.setFilepath(folderPath.replace("_", "")+".zip");
			fileInfo.setStatus(DictCodeListExt2.数据状态_有效.getValue());
			fileInfo.setCreateUser("system");
			fileInfo.setUpdateUser("system");
			pbcDataFileMapper.insert(fileInfo);
			PbcClassicsDataFileHelper.init();
			//遍历每个目录（zip），获取其中每个文件信息
			for(String detailcode : PbcClassicsDataFileHelper.DATAMAP.get(filecode)){
				PbcDataFileDetail fileDetail = new PbcDataFileDetail();
				fileDetail.setDetailid(IDGenerator.generate(36));
				fileDetail.setDetailcode(detailcode);
				fileDetail.setFileid(fileid);
				fileDetail.setFilecode(filecode);
				fileDetail.setCdate(genDate);
				fileDetail.setFileseqno(fileseqno);
				fileDetail.setFilepath(folderPath+"/"+detailcode + ".dat");
				fileDetail.setCreateUser("system");
				fileDetail.setUpdateUser("system");
				pbcDataFileDetailMapper.insert(fileDetail);

				// 【dat文件生成留痕】入库：人行单产品报表DAT文件明细表关联 zhangyf 20191107 start
				if (null != fileDataMap && null != fileDataMap.get("pathfilenameDatakeylist")) {
					pathfilenameDatakeylistMap = ((Map<String, List<String>>) fileDataMap.get("pathfilenameDatakeylist"));
					if(null != pathfilenameDatakeylistMap) {
						dataValueList = pathfilenameDatakeylistMap.get(fileDetail.getFilepath());
						if (!CollectionUtils.isEmpty(dataValueList)) {
							relationList = new ArrayList<PbcDatfileDetailRelation>();
							Map<String, String> dataKeyMap = new HashMap<String, String>();
							for (String datakey : dataValueList) {
								if(!StringUtil.isEmpty(datakey)) {
									relation = new PbcDatfileDetailRelation();
									relation.setDetailid(fileDetail.getDetailid());
									relation.setFilecode(fileDetail.getFilecode());
									relation.setGendate(genDate);
									relation.setBusiId(datakey);
									relationList.add(relation);
								}
							}

							// 批量入库
							if (relationList.size() <= insertBatchCount) {
								pbcDatfileDetailRelationMapper.insertList(relationList);
							} else {
								List<PbcDatfileDetailRelation> newDataList = new ArrayList<PbcDatfileDetailRelation>();
								newDataList.addAll(relationList);
								// 分批
								int part = relationList.size() / insertBatchCount; // 分批数
								for (int i = 0; i < part; i++) {
									pbcDatfileDetailRelationMapper.insertList(newDataList.subList(0, insertBatchCount));
									newDataList.subList(0, insertBatchCount).clear();
								}
								// 最后剩下的数据
								if (!CollectionUtils.isEmpty(newDataList)) {
									pbcDatfileDetailRelationMapper.insertList(newDataList);
								}
							}
						}
					}
				}
				// 【dat文件生成留痕】入库：人行单产品报表DAT文件明细表关联 zhangyf 20191107 end
			}
		}
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
		String finalZipPath = SvrDataUtil.getSysParam("SVR_PBC_DAT_FILEPATH") + "temporary" + File.separator + "手工生成人行数据文件" + dateStr + ".zip";
		File outFile =  new SafelyFile(finalZipPath);
		for (int i = 0; i < size; i ++) {
			allFiles[i] = new SafelyFile(zipFileName.get(i));
		}
		// 压缩文件并删除原文件
		try {
			ZipFileUtil.compressZip(outFile, true, allFiles);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}

		return finalZipPath;
	}

}
