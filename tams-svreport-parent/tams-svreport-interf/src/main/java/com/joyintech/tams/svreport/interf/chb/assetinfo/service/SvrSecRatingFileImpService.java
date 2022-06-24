package com.joyintech.tams.svreport.interf.chb.assetinfo.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.MstBondCreditRatingMapper;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.base.entity.MstBondCreditRating;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.assetinfo.dao.SvrMstBondCreditRatingMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Description 债券评级信息文件接口
 * @author jiangxiao
 * @version 1.7 2019年5月10日
 * @since 1.7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrSecRatingFileImpService extends FileImpService {

	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
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
	/**
	 * 数据批量一次性insert条数(单条数据字段较多)
	 */
	@Value("${application.reportInsertLongCount}")
	private int reportInsertLongCount;

	@Autowired
	private SvrMstBondCreditRatingMapper svrMstBondCreditRatingMapper;

	@Autowired
	private MstBondCreditRatingMapper mstBondCreditRatingMapper;

	@Autowired
	private SqlSessionTemplate template;

	@Autowired
	private BatchInsertService batchInsertService;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
		// 解析成功或失败的标识
		boolean success = true;
		String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		logger.debug("汇率数据转dto---开始-----:" +  df.format(new Date()));
		List<MstBondCreditRating> listNE = (List<MstBondCreditRating>) convertMapToEntity(listData,MstBondCreditRating.class, dataSource);
		logger.debug("汇率数据转dto---结束-----:" +  df.format(new Date()));
		if(!CollectionUtils.isEmpty(listNE)) {
			// 增量模式根据业务主键更新
			if (InterfConstants.SAVEFLAG_ADD.equals(saveFlag)) {
				mergeSecRating(listNE);
			} else {
				Map<Date, List<MstBondCreditRating>> mstBondCreditRatingMap = listNE.stream().collect(JoyinCollectors.groupingBy(MstBondCreditRating::getGradeDate));
				Set<Date> dateSet = mstBondCreditRatingMap.keySet();
				deleteAllData(dateSet);
				// 数据小于1000直接入库
				if (listNE.size() <= 1000) {
					insertSecRating(listNE);
				} else {// 大于1000使用sqlloader入库
					try {
						//sqlloader插入
						ImpTable table = new ImpTable("MST_BOND_CREDIT_RATING");
						table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
						// 临时文件存放目录
						String path = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

						batchInsertService.exeBatchldr(path, listNE, table);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						return "fail";
					}
				}
			}
		}
		return "success";
	}

	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile,String date) {
		String remoteDir = "";
		String result = "";
		logger.debug("数据转换---开始-----");
//		List<Map<String, String>> listDataConvert = new ArrayList<>();
//
//		for(Map<String, String> mapData : listData){
//			Map<String,String> mapDataConvert = new HashMap<>();
//			// 债券代码
//			mapDataConvert.put("O_CODE",mapData.get("SECID"));
//			// 评级日期
//			mapDataConvert.put("GRADE_DATE",mapData.get("PUBDATE"));
//			// 评级机构
//			mapDataConvert.put("GRADE_ORG_ID",mapData.get("RATINGORG"));
//			// 评级类型
//			mapDataConvert.put("O_TYPE",mapData.get("RATINGTYPE"));
//			// 长短期
//			mapDataConvert.put("SHORT_LONG_TERM",mapData.get("RATINGSUBTYPE"));
//			// 评级结果
//			mapDataConvert.put("GRADE_RESULT",mapData.get("RATING"));
//			// 评级结果
//			mapDataConvert.put("IS_RECOMMAND",mapData.get("IS_RECOMMAND"));
//			// 创建部门
//			if (StringUtil.isNotBlank(mapData.get("CREATE_DEPT"))){
//				mapDataConvert.put("CREATE_DEPT",mapData.get("CREATE_DEPT"));
//			}
//			// 创建人
//			if (StringUtil.isNotBlank(mapData.get("CREATE_USER"))){
//				mapDataConvert.put("CREATE_USER",mapData.get("CREATE_USER"));
//			}
//			// 创建时间
//			if (StringUtil.isNotBlank(mapData.get("UPDATE_TIME"))){
//				mapDataConvert.put("UPDATE_TIME",mapData.get("UPDATE_TIME"));
//			}
//			// 更新人
//			if (StringUtil.isNotBlank(mapData.get("UPDATE_USER"))){
//				mapDataConvert.put("UPDATE_USER",mapData.get("UPDATE_USER"));
//			}
//			// 更新时间
//			if (StringUtil.isNotBlank(mapData.get("UPDATE_TIME"))){
//				mapDataConvert.put("UPDATE_TIME",mapData.get("UPDATE_TIME"));
//			}
//			// 装入list
//			listDataConvert.add(mapDataConvert);
//		}
		logger.debug("数据转换---结束-----");

		logger.debug("评级数据检查---开始-----:" +  df.format(new Date()));
		@SuppressWarnings("unused")
		List<Map<String, String>> listError = checkData(listData, interfFile.getFilename(),
				"MST_BOND_CREDIT_RATING",
				InterfDefine.债券评级数据.getCode(),
				null,"O_CODE","GRADE_DATE","GRADE_ORG_ID","O_TYPE","SHORT_LONG_TERM");
		logger.debug("评级数据检查---结束-----:" +  df.format(new Date()));
		if(CollectionUtils.isEmpty(listData)){
			return "success";
		}
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.债券评级数据.getCode());
		if (interfConfig == null){
			logger.info("CHB_INTERF_CONFIG表中无配置项！");
			return "fail";
		}
		String saveFlag = interfConfig.getContentType();
		logger.debug("评级数据入库---开始-----:" +  df.format(new Date()));
		result = saveData(listData,saveFlag,null);
		logger.debug("评级数据入库---结束-----:" +  df.format(new Date()));
		// 更新系统配置时间
		ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.债券评级数据.getCode());
		if("fail".equals(result)||"fail".equals(remoteDir)){
			return "fail";
		}else{
			return "success";
		}
	}
//
//	private boolean excuteSecRatingByStep(String saveFlag, List<Map<String, String>> datalist, String[] result,
//			String tmpPath, ImpTable table, ImpTable subTable) {
//		// 定义新增数据list-验证通过数据
//		List<MstBondCreditRating> listNE = new ArrayList<MstBondCreditRating>();
//		logger.debug("评级数据转换dto---开始-----:" +  df.format(new Date()));
//		listNE = (List<MstBondCreditRating>) convertMapToEntity(datalist, MstBondCreditRating.class);
//		logger.debug("评级数据转换dto---结束-----:" +  df.format(new Date()));
//		if (listNE != null && listNE.size() > 0) {
//			// 删除评级(当日)
//		//	deleteAllData();
//			if (tmpPath == null) {
//				insertSecRating(listNE);
//			} else {
//				createDataFile(tmpPath + "/MST_BOND_CREDIT_RATING/MST_BOND_CREDIT_RATING", listNE, table.getColumns());
//				logger.debug("创建sqlloder文件");
//			}
//		}
//		return (CollectionUtils.isNotEmpty(listNE));
//	}

	private void insertSecRating(List<MstBondCreditRating> listNE) {
		int count = 0;
		logger.debug("批量插入评级信息----开始------:" +  df.format(new Date()));
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		SvrMstBondCreditRatingMapper batchMapper =  session.getMapper(SvrMstBondCreditRatingMapper.class);
		int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
		int batchLastIndex = batchCount;//每批最后一个的下标
		for(int index = 0; index < listNE.size();){
			if(batchLastIndex > listNE.size()-1){
				batchLastIndex = listNE.size();
				batchMapper.mergeSecRating(listNE.subList(index, batchLastIndex));
				break;//数据插入完毕,退出循环
			}else{
				batchMapper.mergeSecRating(listNE.subList(index, batchLastIndex));
				index = batchLastIndex;//设置下一批下标
				batchLastIndex = index + batchCount;
			}
			count ++;
			// 每20次提交
			if (count % 20 == 0) {
				session.commit();
				// 清理缓存，防止溢出
				session.clearCache();
			}
		}
		logger.debug("批量插入评级信息----结束-----:" +  df.format(new Date()));
		session.commit();
		// 清理缓存，防止溢出
		session.clearCache();

	}

	private void deleteAllData(Set<Date> dateSet){
		svrMstBondCreditRatingMapper.deleteTodayData(dateSet);
	}


	public void mergeSecRating(List<MstBondCreditRating> list) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			int count = 0;
			logger.debug("债券评级插入开始--------------" + new Date());
			SvrMstBondCreditRatingMapper batchMapper = session.getMapper(SvrMstBondCreditRatingMapper.class);
			int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
			int batchLastIndex = batchCount;//每批最后一个的下标
			for(int index = 0; index < list.size();){
				if(batchLastIndex > list.size()-1){
					batchLastIndex = list.size();
					batchMapper.mergeSecRating(list.subList(index, batchLastIndex));
					break;//数据插入完毕,退出循环
				}else{
					batchMapper.mergeSecRating(list.subList(index, batchLastIndex));
					index = batchLastIndex;//设置下一批下标
					batchLastIndex = index + batchCount;
				}
				count ++;
				// 每20次提交
				if (count % 20 == 0) {
					session.commit();
					// 清理缓存，防止溢出
					session.clearCache();
				}
			}
			logger.debug("债券评级插入结束--------------" + new Date());
			session.commit();
			// 清理缓存，防止溢出
			session.clearCache();
		}
		finally {
			session.close();
		}
	}

	/**
	 * 将map转换为entity格式
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<MstBondCreditRating> convertMapToEntity(List<Map<String, String>> dataList) {
		List<MstBondCreditRating> rs = new ArrayList<MstBondCreditRating>();
		for (Map<String, String> map : dataList) {
			MstBondCreditRating t = new MstBondCreditRating();
			t.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
			for (String key : map.keySet()) {
				if(key==null||key.equals("ERRMSG")){//待处理的数据
					t.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
					continue;
				}
				String value = map.get(key);
				Field field = ReflectionUtils.findField(MstBondCreditRating.class,
						subBlank(key.toLowerCase()));
				if(field==null)
					continue;
				field.setAccessible(true);
				Type type = field.getGenericType();
				if("java.lang.String".equals(type.getTypeName())){
					try {
						field.set(t, value);
					} catch ( IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					}
				}else if ("java.math.BigDecimal".equals(type.getTypeName())) {
					try {
						field.set(t,(value == null || "".equals(value)) ? BigDecimal.ZERO : new BigDecimal(value));
					} catch ( IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					}
				}
				else if ("java.lang.Integer".equals(type.getTypeName())) {
					try {
						field.set(t,(value == null || "".equals(value)) ? 0 : Integer.valueOf(value));
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					}
				}
				else if ("java.lang.Short".equals(type.getTypeName())) {
					try {
						field.set(t, (value == null || "".equals(value)) ? 0 : Short.valueOf(value));
					} catch ( IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					}
				}
				else if ("java.lang.Long".equals(type.getTypeName())) {
					try {
						field.set(t, (value == null || "".equals(value)) ? 0 : Long.valueOf(value));
					} catch ( IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					}
				}
				else if ("java.util.Date".equals(type.getTypeName())) {
					if (value != null&&""!=value) {
						try {
							field.set(t, DateUtil.parse(value));
						} catch ( IllegalAccessException e) {
							logger.error(e.getMessage(),e);
						}
					}
				} else{
					try {
						field.set(t, value);
					} catch ( IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
			if(t.getCreateUser()==null){
				t.setCreateUser("system");
			}
			// 创建人
			t.setCreateUser(CommonUtil.getUserName());
			// 创建部门
			t.setCreateDept(CommonUtil.getDeptCode());
			// 更新人
			t.setUpdateUser(CommonUtil.getUserName());
			rs.add(t);
		}
		return rs;
	}

	private List<MstBondCreditRating> dataConversionEntity(List<Map<String, String>> datamapList) {
		List<MstBondCreditRating> rs = new ArrayList<>();
		for(Map<String, String> map : datamapList){
			MstBondCreditRating dto = new MstBondCreditRating();
			dto.setoCode(map.get("O_CODE"));//债券代码（市场代码）
			dto.setGradeDate(DateUtil.parse(map.get("GRADE_DATE")));//评级日期
			dto.setGradeOrgId(map.get("GRADE_ORG_ID"));//评级机构
			dto.setoType(map.get("O_TYPE"));//评级类型,01:主体评级、02:债项评级
			dto.setShortLongTerm(map.get("SHORT_LONG_TERM"));//长短期,01:短期 02:长期
			dto.setGradeResult(map.get("GRADE_RESULT"));//评级结果
			dto.setIsRecommand(map.get("IS_RECOMMAND"));//是否推荐,01是02否
			if(StringUtils.isNotBlank(map.get("CREATE_DEPT"))){
				dto.setCreateDept(map.get("CREATE_DEPT"));//创建部门
			}
			if(StringUtils.isNotBlank(map.get("CREATE_TIME"))) {
				dto.setCreateTime(DateUtil.parse(map.get("CREATE_TIME")));//创建时间
			}
			if(StringUtils.isNotBlank(map.get("CREATE_USER"))){
				dto.setCreateUser(map.get("CREATE_USER"));//创建人
			}
			if(StringUtils.isNotBlank(map.get("UPDATE_TIME"))) {
				dto.setUpdateTime(DateUtil.parse(map.get("UPDATE_TIME")));//更新时间
			}
			if(StringUtils.isNotBlank(map.get("UPDATE_USER"))) {
				dto.setUpdateUser(map.get("UPDATE_USER"));//更新人
			}
			rs.add(dto);
		}
		return rs;
	}
}
