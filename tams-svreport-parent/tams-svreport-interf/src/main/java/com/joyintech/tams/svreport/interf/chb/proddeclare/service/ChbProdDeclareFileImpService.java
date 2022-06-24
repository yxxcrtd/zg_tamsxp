package com.joyintech.tams.svreport.interf.chb.proddeclare.service;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
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

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dao.ChbPrdDeclareInfoMapper;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;

/**
 * @Description 产品申报要素接入
 * @author yuluan
 * @version 1.0 2019年10月09日
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbProdDeclareFileImpService extends FileImpService{

	/**
	 * CHB_PRD_DECLARE_INFO
	 */
	
	 private static final String[] DUPLICATIED_LIST = {"PROD_INS_ID"};
	
	 /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;
    
    
	@Autowired
	private SqlSessionTemplate template;

	@Autowired
	private ChbPrdDeclareInfoMapper chbPrdDeclareInfoMapper;
	
//    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	
	@SuppressWarnings("unchecked")
	private boolean excuteProdDeclareInfoByStep(List<Map<String, String>> posList, String type, 
			String tmpPath, ImpTable table,String dataSource,String saveFlag) {
		// 定义新增数据list-验证不通过数据
		// modify by convertMapToEntity yangxianjiu 2022-01-19 start
//		Map<String,Object> dataMap = convertMapToEntity(posList,dataSource,saveFlag);
//		List<ChbPrdDeclareInfoDto> list = (List<ChbPrdDeclareInfoDto>) dataMap.get("list");
		List<ChbPrdDeclareInfoDto> list = (List<ChbPrdDeclareInfoDto>) convertMapToEntity(posList,ChbPrdDeclareInfoDto.class, dataSource);

		chbPrdDeclareInfoMapper.deleteChbPrdDeclareInfoByKey(list);
		chbPrdDeclareInfoMapper.deleteChbPrdDeclareInfoErroDataByKey(list);
		list.stream().forEach(dto->{
//			dto.setDatasource(dataSource);
			dto.setDeclareUuid(IDGenerator.generate(36));
		});
		// modify by convertMapToEntity yangxianjiu 2022-01-19 start
		if (CollectionUtils.isNotEmpty(list)) {
			// 先删后插,删除当日接入的持仓数据
			if (tmpPath == null) {
				// 插入申报信息
				insertDataMerge(list);
			} else {
				
				createDataFile(tmpPath + "/CHB_PRD_DECLARE/CHB_PRD_DECLARE", list, table.getColumns());
			}
		}
		return CollectionUtils.isNotEmpty(list);
	}
	

    /**
     * 将map转换为entity格式
     *
     * @param infoList 传入的listMap
     * @param entities 转换后的lis
     * @param listCloumn 表列名
     * @param interType 数据来源
     * @see
     * @since 1.0
     */
    private Map<String,Object> convertMapToEntity(List<Map<String, String>> dataList,String dataSource,String saveFlag) {
        // 获取产品表的列
    	List<ChbPrdDeclareInfoDto> list = new ArrayList<ChbPrdDeclareInfoDto>();
//    	Map<String,String> deleteList = new HashMap<String,String>();
        for (Map<String, String> map : dataList) {
//        	if(com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ALL.equals(saveFlag)){//全量
//        		deleteList.put(map.get("POS_DATE").toString(),null);
//        	}
        	ChbPrdDeclareInfoDto t = new ChbPrdDeclareInfoDto();
        	t.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());

            for (String key : map.keySet()) {
            	if(key==null)
            		continue;
            	if(key.equals("ERRMSG")){//待处理的数据
            		t.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
            		continue;
            	}
                String value = map.get(key);
                Field field = ReflectionUtils.findField(ChbPrdDeclareInfoDto.class,
                			subBlank(key.toLowerCase()));
                if(field==null)
                	continue;
                field.setAccessible(true);
                Type type = field.getGenericType();
                if("java.lang.String".equals(type.getTypeName())){
                    try {
                        field.set(t, (value == null || "".equals(value)) ? "" : value);
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                    }
                }else if(value != null && !"".equals(value)){
                    if ("java.math.BigDecimal".equals(type.getTypeName())) {
                        try {
                            field.set(t,new BigDecimal(value));
                        } catch ( IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                    else if ("java.lang.Integer".equals(type.getTypeName())) {
                        try {
                            field.set(t,Integer.valueOf(value));
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                    else if ("java.lang.Short".equals(type.getTypeName())) {
                        try {
                            field.set(t, Short.valueOf(value));
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
                    }
                }  else{
                	try {
                        field.set(t, value);
                    } catch ( Exception e) {
                    	logger.error(e.getMessage(),e);
                    }
                }
            }
            t.setDatasource(dataSource);
            t.setDeclareUuid(IDGenerator.generate(36));
            list.add(t);
        }
        Map<String,Object> rs = new HashMap<String,Object>();
        rs.put("list",list);
//        rs.put("deleteList",deleteList.keySet());
        return rs;
    }

	
	
	
	
	
	private void insertDataMerge(List<ChbPrdDeclareInfoDto> assetInfoList) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			int count = 0;
			logger.debug("产品申报要素插入开始--------------" + new Date());
			ChbPrdDeclareInfoMapper batchMapper = session.getMapper(ChbPrdDeclareInfoMapper.class);
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
		logger.debug("产品申报要素插入结束--------------" + new Date());
	}
    
    
	

	@Override
	public String saveData(List<Map<String, String>> posList, String saveFlag,String dataSource) {

		// 解析成功或失败的标识
		boolean success = true;
		String type = "";
		// 数据小于1000直接入库
		logger.info("----产品申报信息入库，开始-----");
		
		success = excuteProdDeclareInfoByStep(posList, type, null, null,dataSource,saveFlag);
		logger.info("----产品申报信息入库，结束-----");

		return success?"success":"fail";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String importDB(List<Map<String, String>> dataList,InterfFile file, String date) {
		String rs = "success";
		// 解析文件
		if(dataList==null){
			try {
				Map<String,Object> parseResult  = parseFile(file) ;
				dataList = (List<Map<String, String>>) parseResult.get("dataList");
				rs = (String) parseResult.get("remoteDir");
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		// 解析成功，对数据list进行处理
		if (!CollectionUtils.isEmpty(dataList)) {
//			List<Map<String, String>>  listA =
//			checkDataCodeCheck(dataList, file.getFilename(), ChbCheckTableInfo.个人产品申报登记INFO);
			checkData(dataList, file.getFilename(), InterfDefine.产品申报信息接口数据.getCode(),
							InterfDefine.产品申报信息接口数据.getCode(),null, DUPLICATIED_LIST);
//			dataList.addAll(listA);
			if(CollectionUtils.isEmpty(dataList)){
				return rs;
			}
			//全量
	        ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.产品申报信息接口数据.getCode());
	        if (interfConfig == null){
	            logger.info("CHB_INTERF_CONFIG表中无配置项！");
	            return "fail";
	        }
	        String saveFlag = interfConfig.getContentType();
			rs = saveData(dataList,saveFlag,file.getDataSource());
		}
		return rs;

	}

	/**
	 * 功能:文件处理方法
	 * 描述：
	 *      1.parseFile --将文件转换为Map<String,String>
	 *      2.checkData --校验数据
	 *      3.saveData --入库
	 * @since 1.7.0
	 * @version 1.7.0
	 * @author：weiyuqiang 2019年5月5日
	 * @see com.joyintech.tams.common.FileImpService#importDB(java.util.List)
	 */
	@Override
	public String importDB(List<ExcelValidDto> listExcel, List<Map<String, String>> investTradeList, InterfFile interfFile, String date) {
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.产品申报信息接口数据.getCode());
		if (interfConfig == null){
			logger.info("CHB_INTERF_CONFIG表中无配置项！");
			return "fail";
		}
		checkImportData(listExcel, investTradeList, interfFile.getFilename(), ChbCheckTableInfo.个人产品申报登记INFO,interfConfig.getCoverFlag(), listExcel == null? "I" : "D");
		if (CollectionUtils.isEmpty(investTradeList)){
			return "success";
		}
		String saveFlag = interfConfig.getContentType();
		String result= "";
		if(investTradeList.size()>0){
			result = saveData(investTradeList, listExcel == null?saveFlag: InterfConstants.SAVEFLAG_ADD, listExcel == null? "I" : "D");
		}
		// 更新系统配置时间
		ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.产品申报信息接口数据.getCode());
		return result;
	}
	
	
}
