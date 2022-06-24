package com.joyintech.tams.svreport.interf.chb.prodinfo.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dao.ChbPrdDeclareInfoMapper;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.base.entity.ChbPrdresistCodeKey;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.dao.ChbPrdresistCodeMapper;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.prodinfo.dao.ChbPrdBaseinfoInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 产品要素接口文件解析并入库
 * 
 * @author yuluan
 *
 */
@Service
public class SvrProdInfoFileImpService extends FileImpService {
	/**
	 * CHB_PRD_DEAL_NAME
	 */
	// private static final String CHB_PRD_BASEINFO = "CHB_PRD_BASEINFO";
	/**
	 * 业务主键
	 */
	private static final String BUSINESSKEY_CHB_PRD_BASEINFO = "PROD_ID";
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 数据批量一次性insert条数(单条数据字段较多)
	 */
	@Value("${application.reportInsertLongCount}")
	private int reportInsertLongCount;
	/**
	 * 上游接口文件编码格式
	 */
	@Value("${application.reportInterfFileEncoding}")
	public String reportInterfFileEncoding;
	/**
	 * 上游接口解析分割符
	 */
	@Value("${application.reportInterfSplitCharacter}")
	private String reportInterfSplitCharacter;

	/**
	 * 上游接口解析读取文件处理最大线程数
	 */
	@Value("${application.interfMaxCorePoolSize}")
	private int interfMaxCorePoolSize;
	/**
	 * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
	 */
	@Value("${application.reportInterfOneTimeSqlLdrDataCount}")
	private int reportInterfOneTimeSqlLdrDataCount;

	/**
	 * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
	 */
	@Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
	private int reportInterfOneTimeSqlLdrCommitCount;
	/**
	 * sqlldr用数据库连接
	 */
	@Value("${spring.datasource.reportSqlldrDatabaseName}")
	private String reportSqlldrDatabaseName;

	@Autowired
	private SqlSessionTemplate template;

	@Autowired
	private ChbPrdBaseinfoInterfMapper chbPrdBaseinfoInterfMapper;

	@Autowired
	private ChbPrdDeclareInfoMapper chbPrdDeclareInfoMapper;

    @Autowired
    private ChbPrdresistCodeMapper chbPrdresistCodeMapper;

	public void deleteByProdidBatch(List<ChbPrdBaseinfo> listNE) {
		int batchCount = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT) ? "100"
				: SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
		int batchLastIndex = batchCount;// 每批最后一个的下标
		logger.debug("产品要素删除开始--------------" + new Date());
		for (int index = 0; index < listNE.size();) {
			if (batchLastIndex > listNE.size() - 1) {
				batchLastIndex = listNE.size();
				chbPrdBaseinfoInterfMapper.deleteByProdid(listNE.subList(index, batchLastIndex));
				break;// 数据删除完毕,退出循环
			} else {
				chbPrdBaseinfoInterfMapper.deleteByProdid(listNE.subList(index, batchLastIndex));
				index = batchLastIndex;// 设置下一批下标
				batchLastIndex = index + batchCount;
			}
		}
		logger.debug("产品要素删除结束--------------" + new Date());
	}

	void insertInfoBatch(List<ChbPrdBaseinfo> listNE) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {

			int count = 0;
			logger.debug("产品要素插入开始--------------" + new Date());
			ChbPrdBaseinfoInterfMapper batchMapper = session.getMapper(ChbPrdBaseinfoInterfMapper.class);
			for (ChbPrdBaseinfo prod : listNE) {
				batchMapper.insertBymodel(prod);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					// 清理缓存，防止溢出
					session.clearCache();
					logger.debug("产品要素已插入条数-----------" + count + new Date());
				}
			}
			session.commit();
			// 清理缓存，防止溢出
			session.clearCache();
		} finally {
			session.close();
		}
		logger.debug("产品要素插入结束--------------" + new Date());
	}

	private void insertDataMerge(List<ChbPrdBaseinfo> assetInfoList) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			int count = 0;
			logger.debug("产品要素插入开始--------------" + new Date());
			ChbPrdBaseinfoInterfMapper batchMapper = session.getMapper(ChbPrdBaseinfoInterfMapper.class);
			int batchCount = reportInsertLongCount == 0 ? 50 : reportInsertLongCount;// 每批commit的个数
			int batchLastIndex = batchCount;// 每批最后一个的下标
			for (int index = 0; index < assetInfoList.size();) {
				if (batchLastIndex > assetInfoList.size() - 1) {
					batchLastIndex = assetInfoList.size();
					batchMapper.mergeInfoList(assetInfoList.subList(index, batchLastIndex));
					break;// 数据插入完毕,退出循环
				} else {
					batchMapper.mergeInfoList(assetInfoList.subList(index, batchLastIndex));
					index = batchLastIndex;// 设置下一批下标
					batchLastIndex = index + batchCount;
				}
				count++;
				// 每20次提交
				if (count % 20 == 0) {
					session.commit();
					// 清理缓存，防止溢出
					session.clearCache();
				}
			}
			session.commit();
			// 清理缓存，防止溢出
			session.clearCache();
		} finally {
			session.close();
		}
		logger.debug("产品要素插入结束--------------" + new Date());
	}

	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
		String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY,
				GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 转dto
		List<ChbPrdBaseinfo> chbPrdBaseinfoList  = (List<ChbPrdBaseinfo>) convertMapToEntity(listData,ChbPrdBaseinfo.class, dataSource);
		chbPrdBaseinfoInterfMapper.deleteByProdid(chbPrdBaseinfoList);
		chbPrdBaseinfoInterfMapper.deleteChbPrdBaseinfoErroData(chbPrdBaseinfoList);
		List<ChbPrdDeclareInfoDto> chbPrdDeclareInfoDtoList = chbPrdDeclareInfoMapper.selectByChbPrdDeclareInfoList(chbPrdBaseinfoList.stream().map(e -> e.getProdInsId()).collect(Collectors.toList()));
		for (ChbPrdBaseinfo chbPrdBaseinfo : chbPrdBaseinfoList) {
			for (ChbPrdDeclareInfoDto chbPrdDeclareInfoDto : chbPrdDeclareInfoDtoList) {
				if(chbPrdBaseinfo.getProdInsId().equals(chbPrdDeclareInfoDto.getProdInsId())){
					String prodName = chbPrdBaseinfo.getProdName();
					Date createTime = chbPrdBaseinfo.getCreateTime();
					String createUser = chbPrdBaseinfo.getCreateUser();
					Date updateTime = chbPrdBaseinfo.getUpdateTime();
					String updateUser = chbPrdBaseinfo.getUpdateUser();
					String source = chbPrdBaseinfo.getDataSource();
					BeanUtils.copyProperties(chbPrdDeclareInfoDto,chbPrdBaseinfo);
					chbPrdBaseinfo.setProdName(prodName);
					chbPrdBaseinfo.setCreateTime(createTime);
					chbPrdBaseinfo.setCreateUser(createUser);
					chbPrdBaseinfo.setUpdateTime(updateTime);
					chbPrdBaseinfo.setUpdateUser(updateUser);
					chbPrdBaseinfo.setDataSource(source);
					chbPrdBaseinfo.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
					break;
				}
			}
		}
		// 再加工
		chbPrdBaseinfoList.stream().forEach(dto->{
			dto.setProduuid(IDGenerator.generate(36));
			dto.setDataSource(dataSource);
			if (dto.getRegCode() == null && "".equals(dto.getRegCode())){
				ChbPrdresistCodeKey key = new ChbPrdresistCodeKey();
				key.setProdid(dto.getProdId());
				key.setFlag("2");
				ChbPrdresistCode chbPrdresistCode = chbPrdresistCodeMapper.selectByPrimaryKey(key);
				dto.setRegCode(chbPrdresistCode == null ? "" : chbPrdresistCode.getRegCode());// 产品登记编码
			}
			dto.setDurationflag("03");
		});
		// 更新产品登记编码表
		chbPrdBaseinfoInterfMapper.updateChbPrdresistCodes(chbPrdBaseinfoList);
		if (chbPrdBaseinfoList != null && chbPrdBaseinfoList.size() > 0) {
			if(InterfConstants.SAVEFLAG_ADD.equals(saveFlag)){
				insertDataMerge(chbPrdBaseinfoList);
			}else{

			}
		}
		return "success";
	}

	// @Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
//		List<Map<String, String>> listA =
		String result="success";
		checkDataCodeCheck(listData, interfFile.getFilename(), ChbCheckTableInfo.个人发行登记表INFO);
//		checkData(listData, interfFile.getFilename(),
//		InterfDefine.产品基本信息接口数据.getCode(), InterfDefine.产品基本信息接口数据.getCode(),
//		BUSINESSKEY_CHB_PRD_BASEINFO, BUSINESSKEY_CHB_PRD_BASEINFO);
//		listData.addAll(listA);
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.产品基本信息接口数据.getCode());
        if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        if(!listData.isEmpty()){
        	result=saveData(listData, saveFlag, interfFile.getDataSource());
        }
        // 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.产品基本信息接口数据.getCode());
		return result;
	}

	@Override
	public String importDB(List<ExcelValidDto> listExcel, List<Map<String, String>> listData, InterfFile interfFile, String date) {

		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.产品基本信息接口数据.getCode());
		if (interfConfig == null){
			logger.info("CHB_INTERF_CONFIG表中无配置项！");
			return "fail";
		}
		checkImportData(listExcel,listData, interfFile.getFilename(), ChbCheckTableInfo.个人发行登记表INFO,interfConfig.getCoverFlag(), listExcel == null? "I" : "D");

		String saveFlag = interfConfig.getContentType();
		String result = "";
		if(listData.size()>0){
			result=saveData(listData, listExcel == null?saveFlag:InterfConstants.SAVEFLAG_ADD, listExcel == null? "I" : "D");
		}
		// 更新系统配置时间
		ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.产品基本信息接口数据.getCode());
		return result;
	}
}
