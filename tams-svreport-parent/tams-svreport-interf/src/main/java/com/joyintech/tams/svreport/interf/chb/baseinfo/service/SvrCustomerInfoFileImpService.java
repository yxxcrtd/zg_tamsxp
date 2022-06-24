package com.joyintech.tams.svreport.interf.chb.baseinfo.service;

import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.base.entity.MstCustomerInfo;
import com.joyintech.tams.common.base.entity.MstCustomerInfoAddWithBLOBs;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.baseinfo.dao.SvrMstCustomerInfoAddImpMapper;
import com.joyintech.tams.svreport.interf.chb.baseinfo.dao.SvrMstCustomerInfoImpMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
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

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 市场机构信息文件接口
 * @author jiangxiao
 * @version 1.7 2019年5月10日
 * @since 1.7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrCustomerInfoFileImpService extends FileImpService {

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
    
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	@Autowired
	private SqlSessionTemplate template;
    
    @Autowired
    private SvrMstCustomerInfoAddImpMapper svrMstCustomerInfoAddImpMapper;
    
    @Autowired
    private SvrMstCustomerInfoImpMapper svrMstCustomerInfoImpMapper;
	
	@SuppressWarnings("unused")
	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile fileList, String date) {
		logger.debug("市场机构数据检查---开始-----:" +  df.format(new Date()));
		List<Map<String, String>> listErrorInfo = checkData(listData, fileList.getFilename(), InterfDefine.市场机构数据.getCode(),InterfDefine.市场机构数据.getCode(), "CUSTOMER_ID");
		logger.debug("市场机构数据检查----结束-----:" +  df.format(new Date()));
		if(CollectionUtils.isEmpty(listData)){
			return "success";
		}
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.市场机构数据.getCode());
		if (interfConfig == null) {
			logger.info("CHB_INTERF_CONFIG表中无配置项！");
			return "fail";
		}
		String saveFlag = interfConfig.getContentType();
		logger.debug("市场机构数据入库---开始-----:" +  df.format(new Date()));
		String result = saveData(listData, saveFlag, null);
		logger.debug("市场机构数据入库---结束-----:" +  df.format(new Date()));
		// 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.市场机构数据.getCode());
		return result;
	}
    
	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
		logger.debug("市场机构数据入add表---开始-----:" +  df.format(new Date()));
		boolean stepadd = excuteCustomerInfoAddByStep(saveFlag,listData, null, null, null, null, null, dataSource);
		logger.debug("市场机构数据入add表---结束-----:" +  df.format(new Date()));
		logger.debug("市场机构数据入info表---开始-----:" +  df.format(new Date()));
		boolean stepinfo = excuteCustomerInfoByStep(saveFlag,listData, null, null, null, null, null, dataSource);
		logger.debug("市场机构数据入info表---结束-----:" +  df.format(new Date()));
		boolean success = (stepadd&&stepinfo);
		return success ? "success":"fail";
	}

	private boolean excuteCustomerInfoAddByStep(String saveFlag,List<Map<String, String>> list, String type, String[] result,
			String tmpPath, ImpTable table, ImpTable subTable, String dataSource) {
		// 定义新增数据list-验证通过数据
		List<MstCustomerInfoAddWithBLOBs> listNE = new ArrayList<MstCustomerInfoAddWithBLOBs >();
		logger.debug("市场机构数据add表转dto---开始-----:" +  df.format(new Date()));
		// modify by convertMapToEntity yangxianjiu 2022-01-19 start
//		listNE = dataConversionEntity(list);
		listNE = (List<MstCustomerInfoAddWithBLOBs>) convertMapToEntity(list,MstCustomerInfoAddWithBLOBs.class, dataSource);
        // modify by convertMapToEntity yangxianjiu 2022-01-19 end
		logger.debug("市场机构数据add表转dto---结束-----:" +  df.format(new Date()));
		if (listNE != null && listNE.size() > 0) {
			mergeAddData(listNE);
		}
		return (CollectionUtils.isNotEmpty(listNE));
	}
	
	@SuppressWarnings("unused")
	private void deleteAllAddData() {
		svrMstCustomerInfoAddImpMapper.deleteTodayData();
	}

	private void mergeAddData(List<MstCustomerInfoAddWithBLOBs> listNE) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("市场机构add表插入开始--------------" + new Date());
            SvrMstCustomerInfoAddImpMapper batchMapper = session.getMapper(SvrMstCustomerInfoAddImpMapper.class);
            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            for(int index = 0; index < listNE.size();){
                if(batchLastIndex > listNE.size()-1){
                    batchLastIndex = listNE.size();
                    batchMapper.mergeMstCustomerInfoData(listNE.subList(index, batchLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    batchMapper.mergeMstCustomerInfoData(listNE.subList(index, batchLastIndex));
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
            logger.debug("市场机构add表插入结束--------------" + new Date());
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }catch(Exception e){
            logger.error("数据插入出错:",e.getMessage(),e);
        }
        finally {
            session.close();
        }
        logger.debug("市场机构add表插入结束--------------" + new Date());
	}

	private boolean excuteCustomerInfoByStep(String saveFlag,List<Map<String, String>> list, String type, String[] result,
			String tmpPath, ImpTable table, ImpTable subTable, String dataSource) {
		// 定义新增数据list
		List<MstCustomerInfo> listInfoE = new ArrayList<MstCustomerInfo>();
		logger.debug("市场机构数据info表转dto---开始-----:" +  df.format(new Date()));
//		listInfoE = dataConversionEntityInfo(list);
        listInfoE = (List<MstCustomerInfo>) convertMapToEntity(list,MstCustomerInfo.class, dataSource);
        for(MstCustomerInfo mstCustomerInfo:listInfoE){
            // 法人
            mstCustomerInfo.setCustomerType("01");
        }
		logger.debug("市场机构数据info表转dto---结束-----:" +  df.format(new Date()));
		if (listInfoE != null && listInfoE.size() > 0) {
			mergeInfoData(listInfoE);
		}
		return (CollectionUtils.isNotEmpty(listInfoE));
	}
	
	@SuppressWarnings("unused")
	private void deleteAllInfoData() {
		svrMstCustomerInfoImpMapper.deleteTodayData();
		
	}

	private void mergeInfoData(List<MstCustomerInfo> listInfoE) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("市场机构info表插入开始--------------" + new Date());
            SvrMstCustomerInfoImpMapper batchMapper = session.getMapper(SvrMstCustomerInfoImpMapper.class);
            int batchCount = reportInsertLongCount==0?500:reportInsertLongCount;//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            for(int index = 0; index < listInfoE.size();){
                if(batchLastIndex > listInfoE.size()-1){
                    batchLastIndex = listInfoE.size();
                    batchMapper.mergeMstCustomerInfoData(listInfoE.subList(index, batchLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    batchMapper.mergeMstCustomerInfoData(listInfoE.subList(index, batchLastIndex));
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
            logger.debug("市场机构info表插入结束--------------" + new Date());
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }catch(Exception e){
            logger.error("数据插入出错:",e.getMessage(),e);
        }
        finally {
            session.close();
        }
        logger.debug("市场机构info表插入结束--------------" + new Date());
		
	}

	private List<MstCustomerInfo> dataConversionEntityInfo(List<Map<String, String>> dataList) {
    	List<MstCustomerInfo> rs = new ArrayList<MstCustomerInfo>();
        for (Map<String, String> map : dataList) {
        	MstCustomerInfo t = new MstCustomerInfo();
        	t.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            for (String key : map.keySet()) {
            	if(key==null||key.equals("ERRMSG")){//待处理的数据
            		t.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
            		continue;
            	}
                String value = map.get(key);
                Field field = ReflectionUtils.findField(MstCustomerInfo.class,
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
            // 法人
            t.setCustomerType("01");
            rs.add(t);
        }
        return rs;
	}

	private List<MstCustomerInfoAddWithBLOBs> dataConversionEntity(List<Map<String, String>> dataList) {
		// 获取产品表的列
		List<MstCustomerInfoAddWithBLOBs> rs = new ArrayList<MstCustomerInfoAddWithBLOBs>();
        for (Map<String, String> map : dataList) {
            MstCustomerInfoAddWithBLOBs t = new MstCustomerInfoAddWithBLOBs();
        	t.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            for (String key : map.keySet()) {
            	if(key==null||key.equals("ERRMSG")){//待处理的数据
            		t.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
            		continue;
            	}
                String value = map.get(key);
                Field field = ReflectionUtils.findField(MstCustomerInfoAddWithBLOBs.class,
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

	@SuppressWarnings("unused")
	private void deleteCustomerAdd(List<MstCustomerInfoAddWithBLOBs> addlist){
		int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
		int batchLastIndex = batchCount;// 每批最后一个的下标
		logger.debug("市场机构删除开始--------------"+new Date());
		for (int index = 0; index < addlist.size();) {
			if (batchLastIndex > addlist.size() - 1) {
				batchLastIndex = addlist.size();
				svrMstCustomerInfoAddImpMapper.deleteCustomerAddBatch(addlist.subList(index, batchLastIndex));
				break;// 数据插入完毕,退出循环
			} else {
				svrMstCustomerInfoAddImpMapper.deleteCustomerAddBatch(addlist.subList(index, batchLastIndex));
				index = batchLastIndex;// 设置下一批下标
				batchLastIndex = index + batchCount;
			}
		}
		logger.debug("市场机构删除结束--------------"+new Date());
	}
	
	@SuppressWarnings("unused")
	private void deleteCustomerInfo(List<MstCustomerInfo> infolist){
		int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
		int batchLastIndex = batchCount;// 每批最后一个的下标
		logger.debug("市场机构删除开始--------------"+new Date());
		for (int index = 0; index < infolist.size();) {
			if (batchLastIndex > infolist.size() - 1) {
				batchLastIndex = infolist.size();
				svrMstCustomerInfoImpMapper.deleteCustomerInfoBatch(infolist.subList(index, batchLastIndex));
				break;// 数据插入完毕,退出循环
			} else {
				svrMstCustomerInfoImpMapper.deleteCustomerInfoBatch(infolist.subList(index, batchLastIndex));
				index = batchLastIndex;// 设置下一批下标
				batchLastIndex = index + batchCount;
			}
		}
		logger.debug("市场机构删除结束--------------"+new Date());
	}
	
	@SuppressWarnings("unused")
	private void insertCustomerAdd(List<MstCustomerInfoAddWithBLOBs> addlist){
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			int count = 0;
			logger.debug("市场机构ADD表插入开始--------------"+new Date());
			SvrMstCustomerInfoAddImpMapper batchMapper = session.getMapper(SvrMstCustomerInfoAddImpMapper.class);
			for (MstCustomerInfoAddWithBLOBs dto : addlist) {
				batchMapper.insert(dto);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					// 清理缓存，防止溢出
					session.clearCache();
					logger.debug("已插入条数-----------"+count+"---------"+df.format(new Date()));
				}
			}
			session.commit();
			// 清理缓存，防止溢出
			session.clearCache();
			logger.debug("已插入条数-----------"+count+"---------"+df.format(new Date()));
		} finally {
			session.close();
		}
		logger.debug("市场机构ADD表插入结束--------------"+df.format(new Date()));
	}
	
	@SuppressWarnings("unused")
	private void insertCustomerInfo(List<MstCustomerInfo> infolist){
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			int count = 0;
			logger.debug("市场机构info表插入开始--------------"+new Date());
			SvrMstCustomerInfoImpMapper batchMapper = session.getMapper(SvrMstCustomerInfoImpMapper.class);
			for (MstCustomerInfo dto : infolist) {
				batchMapper.insertCustomerInfo(dto);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					// 清理缓存，防止溢出
					session.clearCache();
					logger.debug("已插入条数-----------"+count+"---------"+df.format(new Date()));
				}
			}
			session.commit();
			// 清理缓存，防止溢出
			session.clearCache();
			logger.debug("已插入条数-----------"+count+"---------"+df.format(new Date()));
		} finally {
			session.close();
		}
		logger.debug("市场机构info表插入结束--------------"+df.format(new Date()));
	}
}
