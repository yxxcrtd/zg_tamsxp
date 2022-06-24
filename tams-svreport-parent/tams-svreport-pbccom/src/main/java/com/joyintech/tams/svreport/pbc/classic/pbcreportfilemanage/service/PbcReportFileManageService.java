package com.joyintech.tams.svreport.pbc.classic.pbcreportfilemanage.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.svreport.seq.service.SysSeqService;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileNewService;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileService;
import com.joyintech.tams.svreport.pbc.classic.pbcNewDat.dao.PbcNewPrdDatFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.pbc.classic.data.dao.PbcDataFileMapper;
import com.joyintech.tams.svreport.pbc.classic.data.dao.PbcDatfileDetailRelationMapper;
import com.joyintech.tams.svreport.pbc.classic.data.dto.PbcDataFile;
import com.joyintech.tams.svreport.pbc.classic.data.dto.PbcDatfileDetailRelation;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileHelper;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.service.PbcPrdAccountSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdaccountendsub.service.PbcPrdAccountEndSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdassetdebt.service.PbcPrdAssetDebtSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosub.service.PbcPrdBaseinfoSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.service.PbcPrdCashRaiseSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsub.serivce.PrdBaseinfoEndSubService001;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.service.PbcPrdInitRaiseSubService001;

/**
 * <p>
 * Title: PbcReportFileManageService<／p>
 * <p>
 * Description: 人行报送文件管理Service<／p>
 * <p>
 * Company: 安徽兆尹信息科技股份有限公司<／p>
 * 
 * @author gaojian
 * @date 2019年6月12日
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class PbcReportFileManageService extends CommonExportService<PbcDataFile> {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(PbcReportFileManageService.class);

	@Autowired
	public PbcClassicsDataFileNewService pbcClassicsDataFileNewService;

	@Autowired
	private PbcDataFileMapper pbcDataFileMapper;
	@Autowired
    private PbcPrdBaseinfoSubService001 pbcPrdBaseinfoSubService001;
	@Autowired
    private PbcPrdAccountSubService001 pbcPrdAccountSubService001;
	@Autowired
    private PbcPrdInitRaiseSubService001 pbcPrdInitRaiseSubService001;
	@Autowired
    private PrdBaseinfoEndSubService001 prdBaseinfoEndSubService001;
	@Autowired
    private PbcPrdAccountEndSubService001 pbcPrdAccountEndSubService001;
	@Autowired
    private PbcPrdCashRaiseSubService001 pbcPrdCashRaiseSubService001;
	@Autowired
    private PbcPrdAssetDebtSubService001 pbcPrdAssetDebtSubService001;
	@Autowired
	private PbcDatfileDetailRelationMapper pbcDatfileDetailRelationMapper;
	@Autowired
	private PbcClassicsDataFileHelper pbcClassicsDataFileHelper;
	
	/**
	 * 生成文件标识码下拉框初始值
	 * 
	 * @return 文件标识码下拉框初始值
	 */
	public List<SelectOptionDto> getFileCode() {
		Map<String, String> fileCodeMap = pbcClassicsDataFileHelper.getFileCodeMap();
		List<SelectOptionDto> resultList = new ArrayList<>();
		for (String key : fileCodeMap.keySet()) {
			SelectOptionDto resultDto = new SelectOptionDto();
			resultDto.setId(key);
			resultDto.setText(fileCodeMap.get(key));
			resultList.add(resultDto);
		}
		return resultList;
	}

	/**
	 * 根据页面传入条件查询结果
	 * 
	 * @param param 页面查询条件
	 * @return 查询结果
	 */
	public Page<PbcDataFile> findPage(Map<String, Object> param) {
		String fileCodeIds = (String) param.get("fileCodeIds");
		List<String> fileCodeList = pbcClassicsDataFileHelper.buildFileCodeList(fileCodeIds);
		param.put("fileCodeList", fileCodeList);
		int pagenum = StringUtil.integerValueOf(""+param.get(GlobalConstant.PARAM_PAGE), 1);
        int length = StringUtil.integerValueOf(""+param.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
		Page<PbcDataFile> page = queryForPage(pagenum,length,()->pbcDataFileMapper.queryPage(param));
		dealResult(page);
		return page;
	}
	
	/**
	 * 获取平台日期前一日 (用于生成日期初始化)
	 * @return 平台日期前一日
	 */
	public AjaxResult getYesterDayDate(){
        AjaxResult result = new AjaxResult();
        result.setData(com.joyintech.tams.report.interf.common.constant.DateUtil.getYesterDayDate());
        return result;
    }

	/**
	 * 对分页查询结果进行处理
	 * 
	 * @param page 原始分页查询结果
	 */
	private void dealResult(Page<PbcDataFile> page) {
		List<PbcDataFile> pbcDataFileList = page.getRoot();
		for (PbcDataFile pbcDataFile : pbcDataFileList) {
			// 前台显示的日期类型格式为yyyy-MM-dd
			pbcDataFile.setcDateStr(DateUtil.format(pbcDataFile.getCdate()));
			String filePath = pbcDataFile.getFilepath();
			String[] filePathSplit = filePath.split("/");
			String zipName = filePathSplit[filePathSplit.length - 1];
//			// 生成日期转为yyyyMMdd形式字符串
//			String cDateStr = DateUtil.format(pbcDataFile.getCdate(), DateUtil.DEFAULT_OTHER_FORMAT);
//			StringBuilder stringBuilder = new StringBuilder();
//			String zipName = stringBuilder
//					// 文件标志码
//					.append(pbcDataFile.getFilecode())
//					// 金融机构总行机构代码
//					.append(pbcDataFile.getHeadOrgcode())
//					// 生成日期
//					.append(cDateStr)
//					// 以日为周期的文件顺序号
//					.append(pbcDataFile.getFileseqno())
//					// zip文件后缀
//					.append(".zip").toString();
			pbcDataFile.setZipName(zipName);
		}
	}
	
	/**
     * 检查选择的压缩文件是否存在
     * @return 不存在的文件清单
     */
	public AjaxResult checkFileExists(Map<String, Object> param) {
		AjaxResult result = new AjaxResult();
		StringBuilder stringBuilder = new StringBuilder();
		List<PbcDataFile> pbcDataFileList = new ArrayList<PbcDataFile>(16);
		try {
			pbcDataFileList = JsonUtils.json2List(param.get("keyList").toString(), PbcDataFile.class);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		for (PbcDataFile pbcDataFile : pbcDataFileList) {
			File file = new SafelyFile(pbcDataFile.getFilepath());
			if (!file.exists()) {
				String filePath = pbcDataFile.getFilepath();
				String[] filePathSplit = filePath.split("/");
				String zipName = filePathSplit[filePathSplit.length - 1];
				stringBuilder.append(zipName).append(",");
			}
		}
		// 删除最后一个字符“,”
		if (stringBuilder.length() > 0) {
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		result.setData(stringBuilder.toString());
		return result;
	}

	/**
	 * 下载压缩文件
	 * @param param 页面参数
	 * @return 压缩文件
	 */
	public ResponseEntity<byte[]> download(Map<String, Object> param) {
		List<PbcDataFile> pbcDataFileList = new ArrayList<PbcDataFile>(16);
		try {
			pbcDataFileList = JsonUtils.json2List(param.get("keyList").toString(), PbcDataFile.class);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		List<File> sourceFiles = new ArrayList<File>(16);
		int index = 0;
		for (PbcDataFile pbcDataFile : pbcDataFileList) {
			File file;
			//delete by wangyaning 20220511 GDLCJGBS-561 start
//			if(PbcClassicsDataFileHelper.DATAFILE_TYPE_PPI.equals(pbcDataFile.getFilecode())){
//				Map<String,Object> map = new HashMap<>();
//				index ++;
//				//碰到PPI类型文件重新组装
//				map.put("index",String.valueOf(index));
//				pbcClassicsDataFileHelper.setSysSeqService(SpringContextUtil.getBean(SysSeqService.class));
//				file = new SafelyFile(pbcClassicsDataFileNewService.genDataFile(PbcClassicsDataFileHelper.DATAFILE_TYPE_PPI,pbcDataFile.getFileid(),new Date(),map));
//			}else{
				file = new SafelyFile(pbcDataFile.getFilepath());
//			}
			//delete by wangyaning 20220511 GDLCJGBS-561 end
			sourceFiles.add(file);
		}
		// 只有一个文件,直接导出,且不能删除源文件
		if (sourceFiles.size() == 1) {
			return ExportUtil.export(sourceFiles.get(0).getName(), sourceFiles.get(0), false);
		}
		// 如果生成文件数量大于1,需要压缩之后导出
		File outFile = null;
		if (sourceFiles.size() > 1) {
			String exportFilePath = SvrDataUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
			String zipFileName = "人行报送文件" + DateUtil.getCurrentDate() + ".zip";
			zipFileName = zipFileName.replaceAll(":", "-");
			outFile = new SafelyFile(exportFilePath + File.separator + zipFileName);
            // 导出路径不存在则重新生成
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
			try {
				ZipFileUtil.compressZip(outFile, false, sourceFiles.toArray(new SafelyFile[] {}));
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
//				// 压缩文件失败后，删除所有临时文件
//				for (File file : sourceFiles) {
//					if (file.exists()) {
//						file.delete();
//					}
//				}
			}
			return ExportUtil.export(outFile.getName(), outFile, true);
		}
		return null;
	}
	
	/**
	 * 检查选择的压缩文件是否已手工处理
	 * @param param
	 * @return 未手工处理的压缩包列表
	 * @throws IOException
	 */
	public List<PbcDataFile> checkFileSend(Map<String, Object> param) throws IOException {
		// 文件（zip）列表
		List<PbcDataFile> dataFiles = JsonUtils.json2List(param.get("keyList").toString(), PbcDataFile.class);
		Map<String, Object> searchParam = new HashMap<String, Object>();
		searchParam.put("sendStatus", DictCodeListExt2.报送状态_手工处理.getValue());
		searchParam.put("status", DictCodeListExt2.数据状态_有效.getValue());
		// 未手工处理的压缩包列表
		List<PbcDataFile> unSendDataFiles = new ArrayList<PbcDataFile>();
		for (PbcDataFile pbcDataFile : dataFiles) {
			searchParam.put("fileid", pbcDataFile.getFileid());
			/*
			// 根据fileid查询文件明细表和关系表是否有手工处理记录
			List<PbcDatfileDetailRelation> relationList = pbcDatfileDetailRelationMapper
					.selectSendRelationByFileid(searchParam);
			// 未查询到数据，说明没有手工处理
			if (CollectionUtils.isEmpty(relationList)) {
				unSendDataFiles.add(pbcDataFile);
			}
			*/
			
			// 根据fileid查询文件明细表和关系表是否有记录
			List<PbcDatfileDetailRelation> relationList = pbcDatfileDetailRelationMapper
					.selectSendRelationByFileid(searchParam);
			// 查询到数据，说明未手工处理（手工处理完成后，会将关系表的数据清除）
			if (!CollectionUtils.isEmpty(relationList)) {
				unSendDataFiles.add(pbcDataFile);
			}
		}
		return unSendDataFiles;
	}
	
	/**
	 * 手工处理：批量处理业务表的发送状态
	 * @param param
	 * @throws IOException 
	 */
	public void doManul(Map<String, Object> param) throws IOException {
		// 文件（zip）列表
		List<PbcDataFile> dataFiles = JsonUtils.json2List(param.get("sendKeyList").toString(), PbcDataFile.class);
		// 各业务表主键集合
		List<String> PIB1keys = new ArrayList<String>();
		List<String> PPB1keys = new ArrayList<String>();
		List<String> PIB2keys = new ArrayList<String>();
		List<String> PIE1keys = new ArrayList<String>();
		List<String> PPE1keys = new ArrayList<String>();
		List<String> PCD1keys = new ArrayList<String>();
		List<String> PVD1keys = new ArrayList<String>();

		List<String> AF12keys = new ArrayList<String>();
		List<String> AF22keys = new ArrayList<String>();
		List<String> AF32keys = new ArrayList<String>();
		List<String> AF43keys = new ArrayList<String>();
		List<String> AF53keys = new ArrayList<String>();
		// 明细文件编号集合
//		List<PbcDatfileDetailRelation> detailids = new ArrayList<PbcDatfileDetailRelation>();
		List<String> detailids = new ArrayList<String>();
		// 遍历文件（zip）列表，组装各业务表主键集合
		Map<String, Object> searchparam = new HashMap<String, Object>();
		for (PbcDataFile pbcDataFile : dataFiles) {
			searchparam.put("fileid", pbcDataFile.getFileid());
			// 根据fileid查询文件明细表（dat）
			List<PbcDatfileDetailRelation> relationList = pbcDatfileDetailRelationMapper.selectRelationByFileid(searchparam);
			if(!CollectionUtils.isEmpty(relationList)) {
				// 遍历文件明细表（dat），获取不同业务表的主键集合
				for(PbcDatfileDetailRelation relation : relationList) {
					if (!detailids.contains(relation.getDetailid())) {
						detailids.add(relation.getDetailid());
					}
					switch (relation.getDetailcode()) {
						// 产品基本信息
						case "PIB1":
							PIB1keys.add(relation.getBusiId());
							break;
						// 资产池基本信息
						case "PPB1":
							PPB1keys.add(relation.getBusiId());
							break;
						// 产品起始募集信息
						case "PIB2":
							PIB2keys.add(relation.getBusiId());
							break;
						// 产品终止信息
						case "PIE1":
							PIE1keys.add(relation.getBusiId());
							break;
						// 资产池终止登记
						case "PPE1":
							PPE1keys.add(relation.getBusiId());
							break;
						// 产品资金募集信息
						case "PCD1":
							PCD1keys.add(relation.getBusiId());
							break;
						// 资产负债信息
						case "PVD1":
							PVD1keys.add(relation.getBusiId());
							break;
						// 产品或资产池信息修改内容
						case "AF12":
							AF12keys.add(relation.getBusiId());
							break;
						// 产品起始募集信息修改内容
						case "AF22":
							AF22keys.add(relation.getBusiId());
							break;
						// 产品终止信息修改内
						case "AF32":
							AF32keys.add(relation.getBusiId());
							break;
						// 资金募集信息修改内容
						case "AF43":
							AF43keys.add(relation.getBusiId());
							break;
						// 资产负债信息
						case "AF53":
							AF53keys.add(relation.getBusiId());
							break;
					}
				}
			}
		}
		
		// 手工处理
		Map<String, Object> doManulParam = new HashMap<String, Object>();
		doManulParam.put("flag","dat");
		int part = 1; //分批处理
		int partSize = 500;//每批处理的数据条数
		// 产品基本信息
		if(!CollectionUtils.isEmpty(PIB1keys)) {
//			doManulParam.put("keyList", PIB1keys);
//			pbcPrdBaseinfoSubService001.doManul(doManulParam);
			part = PIB1keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",PIB1keys.subList(0, partSize));
				pbcPrdBaseinfoSubService001.doManul(doManulParam);
				PIB1keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(PIB1keys)) {
	        	doManulParam.put("keyList",PIB1keys);
	        	pbcPrdBaseinfoSubService001.doManul(doManulParam);
	        }
		}
		// 资产池基本信息
		if(!CollectionUtils.isEmpty(PPB1keys)) {
//			doManulParam.put("keyList", PPB1keys);
//			pbcPrdAccountSubService001.doManul(doManulParam);
			part = PPB1keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",PPB1keys.subList(0, partSize));
				pbcPrdAccountSubService001.doManul(doManulParam);
				PPB1keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(PPB1keys)) {
	        	doManulParam.put("keyList",PPB1keys);
	        	pbcPrdAccountSubService001.doManul(doManulParam);
	        }
		}
		// 产品起始募集信息
		if(!CollectionUtils.isEmpty(PIB2keys)) {
//			doManulParam.put("keyList", PIB2keys);
//			pbcPrdInitRaiseSubService001.doManul(doManulParam);
			part = PIB2keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",PIB2keys.subList(0, partSize));
				pbcPrdInitRaiseSubService001.doManul(doManulParam);
				PIB2keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(PIB2keys)) {
	        	doManulParam.put("keyList",PIB2keys);
	        	pbcPrdInitRaiseSubService001.doManul(doManulParam);
	        }
		}
		// 产品终止信息
		if(!CollectionUtils.isEmpty(PIE1keys)) {
//			doManulParam.put("keyList", PIE1keys);
//			prdBaseinfoEndSubService001.doManul(doManulParam);
			part = PIE1keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",PIE1keys.subList(0, partSize));
				prdBaseinfoEndSubService001.doManul(doManulParam);
				PIE1keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(PIE1keys)) {
	        	doManulParam.put("keyList",PIE1keys);
	        	prdBaseinfoEndSubService001.doManul(doManulParam);
	        }
		}
		// 资产池终止登记
		if(!CollectionUtils.isEmpty(PPE1keys)) {
//			doManulParam.put("keyList", PPE1keys);
//			pbcPrdAccountEndSubService001.doManul(doManulParam);
			part = PPE1keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",PPE1keys.subList(0, partSize));
				pbcPrdAccountEndSubService001.doManul(doManulParam);
				PPE1keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(PPE1keys)) {
	        	doManulParam.put("keyList",PPE1keys);
	        	pbcPrdAccountEndSubService001.doManul(doManulParam);
	        }
		}
		// 产品资金募集信息
		if(!CollectionUtils.isEmpty(PCD1keys)) {
//			doManulParam.put("keyList", PCD1keys);
//			pbcPrdCashRaiseSubService001.doManul(doManulParam);
			part = PCD1keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",PCD1keys.subList(0, partSize));
				pbcPrdCashRaiseSubService001.doManul(doManulParam);
				PCD1keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(PCD1keys)) {
	        	doManulParam.put("keyList",PCD1keys);
	        	pbcPrdCashRaiseSubService001.doManul(doManulParam);
	        }
		}
		// 资产负债信息
		if(!CollectionUtils.isEmpty(PVD1keys)) {
//			doManulParam.put("keyList", PVD1keys);
//			pbcPrdAssetDebtSubService001.doManul(doManulParam);
			part = PVD1keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",PVD1keys.subList(0, partSize));
				pbcPrdAssetDebtSubService001.doManul(doManulParam);
				PVD1keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(PVD1keys)) {
	        	doManulParam.put("keyList",PVD1keys);
	        	pbcPrdAssetDebtSubService001.doManul(doManulParam);
	        }
		}
		
		// 产品或资产池信息修改内容
		if(!CollectionUtils.isEmpty(AF12keys)) {
//			doManulParam.put("keyList", AF12keys);
//			pbcPrdBaseinfoSubService001.doManul(doManulParam);
//			pbcPrdAccountSubService001.doManul(doManulParam);
			part = AF12keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",AF12keys.subList(0, partSize));
				pbcPrdBaseinfoSubService001.doManul(doManulParam);
				pbcPrdAccountSubService001.doManul(doManulParam);
				AF12keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(AF12keys)) {
	        	doManulParam.put("keyList",AF12keys);
	        	pbcPrdBaseinfoSubService001.doManul(doManulParam);
				pbcPrdAccountSubService001.doManul(doManulParam);
	        }
		}
		// 产品起始募集信息修改内容
		if(!CollectionUtils.isEmpty(AF22keys)) {
//			doManulParam.put("keyList", AF22keys);
//			pbcPrdInitRaiseSubService001.doManul(doManulParam);
			part = AF22keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",AF22keys.subList(0, partSize));
				pbcPrdInitRaiseSubService001.doManul(doManulParam);
				AF22keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(AF22keys)) {
	        	doManulParam.put("keyList",AF22keys);
	        	pbcPrdInitRaiseSubService001.doManul(doManulParam);
	        }
		}
		// 产品终止信息修改内
		if(!CollectionUtils.isEmpty(AF32keys)) {
//			doManulParam.put("keyList", AF32keys);
//			prdBaseinfoEndSubService001.doManul(doManulParam);
			part = AF43keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",AF43keys.subList(0, partSize));
				prdBaseinfoEndSubService001.doManul(doManulParam);
				AF43keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(AF43keys)) {
	        	doManulParam.put("keyList",AF43keys);
	        	prdBaseinfoEndSubService001.doManul(doManulParam);
	        }
		}
		// 资金募集信息修改内容
		if(!CollectionUtils.isEmpty(AF43keys)) {
//			doManulParam.put("keyList", AF43keys);
//			pbcPrdCashRaiseSubService001.doManul(doManulParam);
			part = AF43keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",AF43keys.subList(0, partSize));
				pbcPrdCashRaiseSubService001.doManul(doManulParam);
				AF43keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(AF43keys)) {
	        	doManulParam.put("keyList",AF43keys);
	        	pbcPrdCashRaiseSubService001.doManul(doManulParam);
	        }
			
		}
		// 资产负债信息
		if(!CollectionUtils.isEmpty(AF53keys)) {
//			doManulParam.put("keyList", AF53keys);
//			pbcPrdAssetDebtSubService001.doManul(doManulParam);
			part = AF53keys.size() / partSize;
			for (int i = 0; i < part; i++ ) {
				doManulParam.put("keyList",AF53keys.subList(0, partSize));
				pbcPrdAssetDebtSubService001.doManul(doManulParam);
				AF53keys.subList(0, partSize).clear();
	        }
			// 最后剩下的数据
	        if (!CollectionUtils.isEmpty(AF53keys)) {
	        	doManulParam.put("keyList",AF53keys);
	        	pbcPrdAssetDebtSubService001.doManul(doManulParam);
	        }
		}
		
		// 报送状态更新完成后,清空当前zip包下包含任意人行单产品报表DAT文件明细表关联 (设计变更 zhangyangfei 20191210)
		for (String detailid : detailids) {
			// 物理删除 【人行单产品报表DAT文件明细表关联】
			PbcDatfileDetailRelation detailiddto = new PbcDatfileDetailRelation();
			detailiddto.setDetailid(detailid);
			pbcDatfileDetailRelationMapper.deleteByDetailid(detailiddto);
		}
		
	}

}
