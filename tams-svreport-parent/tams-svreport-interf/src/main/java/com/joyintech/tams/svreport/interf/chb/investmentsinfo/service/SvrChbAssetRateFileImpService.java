package com.joyintech.tams.svreport.interf.chb.investmentsinfo.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.base.entity.ChbAssetRate;
import com.joyintech.tams.common.base.entity.MstBondCreditRating;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.investmentsinfo.dao.ChbAssetRateInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
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
 * @Description 资产利率基本信息接口
 * @author zcw
 * @version 2021年11月10日
 * @since
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SvrChbAssetRateFileImpService extends FileImpService {

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

	@Autowired
	private SqlSessionTemplate template;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ChbAssetRateInterfMapper chbAssetRateInterfMapper;

	@Autowired
	private BatchInsertService batchInsertService;

	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
		String result = "";
		logger.debug("资产利率基本信息检查----开始-----:" +  df.format(new Date()));
		checkData(listData, interfFile.getFilename(), InterfDefine.资产利率基本信息接口数据.getCode(),InterfDefine.资产利率基本信息接口数据.getCode(), null,"ASSET_ID", "CHANGE_DATE", "CHANGE_TYPE");
		logger.debug("资产利率基本信息检查----结束-----:" +  df.format(new Date()));
		if(CollectionUtils.isEmpty(listData)){
			return "success";
		}
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.资产利率基本信息接口数据.getCode());
		if (interfConfig == null){
			logger.info("CHB_INTERF_CONFIG表中无配置项！");
			return "fail";
		}
		String saveFlag = interfConfig.getContentType();
		logger.debug("批量入库资产利率基本信息----开始-----:" +  df.format(new Date()));
		result = saveData(listData,saveFlag,interfFile.getDataSource());
		logger.debug("批量入库资产利率基本信息----开始-----:" +  df.format(new Date()));
		// 更新系统配置时间
		ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.资产利率基本信息接口数据.getCode());
		return result;
	}

	@Override
	public String saveData(List<Map<String, String>> chbAssetRateList, String saveFlag,String dataSource) {
		String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 解析成功或失败的标识
		boolean success = true;

		logger.debug("资产利率基本信息map转dto----开始-----:" +  df.format(new Date()));
		List<ChbAssetRate> listNE = (List<ChbAssetRate>) convertMapToEntity(chbAssetRateList, ChbAssetRate.class, dataSource);
		listNE.stream().forEach(item->{
			item.setSeqno(IDGenerator.generate(36));//主键
		});
		logger.debug("资产利率基本信息map转dto----结束-----:" +  df.format(new Date()));
		if(!CollectionUtils.isEmpty(listNE)){
			if(InterfConstants.SAVEFLAG_ADD.equals(saveFlag)){
				mergeIntoData(listNE);
			}else if (chbAssetRateList.size() <= 1000) {
				// 全量方式先删后插
				// 数据小于1000直接入库
				deleteAllData();
				insertBatch(listNE);
			} else {
				// 大于1000使用sqlloader入库
				try {
					//sqlloader插入
					ImpTable table = new ImpTable("CHB_ASSET_RATE");
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
		return "success";
	}

	private void deleteAllData() {
		chbAssetRateInterfMapper.deleteTodayData();
	}

	/**
	 * 将集合均分成几个大小一致的子集合
	 * @param 集合
	 * @param 子集合大小
	 * @return
	 */
	public static List<List<ChbAssetRate>>  createList(List<ChbAssetRate> targe,int size) {
		List<List<ChbAssetRate>> listArr = new ArrayList<List<ChbAssetRate>>();
		//获取被拆分的数组个数
		int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
		for(int i=0;i<arrSize;i++) {
			List<ChbAssetRate> sub = new ArrayList<ChbAssetRate>();
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

	private void insertBatch(List<ChbAssetRate> chbAssetRatelist) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		//批量插入mapper
		ChbAssetRateInterfMapper chbAssetRateInterfMapper =  session.getMapper(ChbAssetRateInterfMapper.class);
		try{
			int count = 0;
			for(ChbAssetRate sch : chbAssetRatelist){
				chbAssetRateInterfMapper.insert(sch);
				count++;
				if(count % 1000==0){
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
			session.clearCache();
		} finally {
			session.close();
		}
	}

	private void mergeIntoData(List<ChbAssetRate> chbAssetRatelist) {
		logger.debug("批量插入资产利率基本信息----开始-----:" +  df.format(new Date()));
		int singalCount = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?
				"100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));
		List<List<ChbAssetRate>> bigList = createList(chbAssetRatelist,singalCount);
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		//批量插入mapper
		ChbAssetRateInterfMapper chbAssetRateInterfMapper =  session.getMapper(ChbAssetRateInterfMapper.class);
		try{
			int count = 0;
			for(List<ChbAssetRate> sch : bigList){
				chbAssetRateInterfMapper.mergeChbAssetRateData(sch);
				count++;
				if(count % 10==0){
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
			session.clearCache();
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		logger.debug("批量插入资产利率基本信息----结束-----:" +  df.format(new Date()));
	}
}