package com.joyintech.tams.svreport.interf.chb.baseinfo.service;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.joyintech.tams.common.base.entity.ChbInvestTrade;
import com.joyintech.tams.common.base.entity.MstHolidayErrodataWithBLOBs;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.service.CommonCodeCheckBatchService;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.collections4.CollectionUtils;
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

import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.MstHoliday;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.baseinfo.dao.SvrMstHolidayImpMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * @Description 节假日信息文件接口
 * @author jiangxiao 
 * @version 1.7 2019年5月10日
 * @since 1.7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrHolidayFileImpService extends FileImpService {

	 /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 共通校验类
	 */
	@Autowired
	private BaseCommonCheckService baseCommonCheckService;
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
	private SqlSessionTemplate template;
    @Autowired
    private SvrMstHolidayImpMapper svrMstHolidayImpMapper;
    @Autowired
    private CommonCodeCheckBatchService commonCodeCheckBatchService;

	@Override
	public String importDB(List<Map<String, String>> listData,InterfFile interfFile, String date) {
		String result = "";
		logger.info("节假日数据check---开始-----:" +  DateUtil.format(new Date()));
		List<Map<String, String>> listError = checkData(listData, interfFile.getFilename(), InterfDefine.节假日数据.getCode(), InterfDefine.节假日数据.getCode(), null,"MARKET_CODE","CDATE");
		logger.info("节假日数据check查---结束-----:" +  DateUtil.format(new Date()));
		if(CollectionUtils.isEmpty(listData)){
			return "success";
		}
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.节假日数据.getCode());
        if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        logger.info("节假日数据save---开始-----:" +  DateUtil.format(new Date()));
		result = saveData(listData,saveFlag,null);
		logger.info("节假日数据save---结束-----:" +  DateUtil.format(new Date()));
		// 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.节假日数据.getCode());
		if("fail".equals(result)){
			return "fail";
		}else{
			return "success";
		}
	}
	@Override
	public String importDB(List<ExcelValidDto> listExcel, List<Map<String, String>> listData, InterfFile interfFile, String date) {

		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.节假日数据.getCode());
		if (interfConfig == null){
			logger.info("CHB_INTERF_CONFIG表中无配置项！");
			return "fail";
		}
		checkImportData(listExcel,listData, interfFile.getFilename(), ChbCheckTableInfo.节假日,interfConfig.getCoverFlag(), listExcel == null? "I" : "D");

		String saveFlag = interfConfig.getContentType();
		String result = "";
		if(listData.size()>0){
			result=saveData(listData, listExcel == null?saveFlag: InterfConstants.SAVEFLAG_ADD, listExcel == null? "I" : "D");
		}
		// 更新系统配置时间
		ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.节假日数据.getCode());
		return result;
	}
	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
		boolean isSucess=false;
		// 定义新增数据list-验证通过数据
		List<MstHoliday> listNE = null;
		logger.info("节假日数据map转dto---开始-----:" +  DateUtil.format(new Date()));
		if(CollectionUtils.isNotEmpty(listData)){
			listNE = (List<MstHoliday>) convertMapToEntity(listData, MstHoliday.class, dataSource);
		}
		logger.info("节假日数据map转dto---结束-----:" +  DateUtil.format(new Date()));

		logger.info("节假日数据入库---开始-----:" +  DateUtil.format(new Date()));
		if (listNE != null && listNE.size() > 0) {
			if(F.equals(saveFlag)){
				//全量
			}else{
				List<MstHolidayErrodataWithBLOBs> errDataList = Lists.newArrayList();
				listNE.forEach(e->{
					MstHolidayErrodataWithBLOBs errodataWithBLOBs = new MstHolidayErrodataWithBLOBs();
					errodataWithBLOBs.setCalendarId(e.getCalendarId());
					errodataWithBLOBs.setCdate(e.getCdate());
					errDataList.add(errodataWithBLOBs);
				});
				commonCodeCheckBatchService.deleteMstHolidayErrodata(errDataList);
				//增量
				isSucess = mergeData(listNE);
			}
		}
		logger.info("节假日数据入库---结束-----:" +  DateUtil.format(new Date()));
		return isSucess ? "success" : "fail";
	}

	/**
	 * 增量模式
	 * @param listNE
	 * @return
	 */
	private boolean mergeData(List<MstHoliday> listNE) {
		boolean  isSuccess=true;
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.info("节假日插入开始--------------" + new Date());
            SvrMstHolidayImpMapper batchMapper = session.getMapper(SvrMstHolidayImpMapper.class);
            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            for(int index = 0; index < listNE.size();){
                if(batchLastIndex > listNE.size()-1){
                    batchLastIndex = listNE.size();
                    batchMapper.mergeHolidayData(listNE.subList(index, batchLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    batchMapper.mergeHolidayData(listNE.subList(index, batchLastIndex));
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
            logger.info("节假日插入结束--------------" + new Date());
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }catch(Exception e){
            logger.error("数据插入出错:",e.getMessage(),e);
            isSuccess=false;
        }
        finally {
            session.close();
        }
        logger.info("节假日插入结束--------------" + new Date());
        return  isSuccess;
	}

}
