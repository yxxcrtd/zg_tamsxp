package com.joyintech.tams.svreport.interf.chb.investtrade.service;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.joyintech.framework.util.CacheUtil;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
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

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.entity.ChbMstCounterParty;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.interf.chb.investtrade.dao.ChbMstCounterPartyInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

import javax.annotation.Resource;

/**
 * 功能:交易对手文件解析方式入库
 * @since 1.7.0
 * @version 1.7.0
 * @作者：liuyu
 * @创建时间：2019年4月29日
 * @修改记录：
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrMstCounterPartFileImpService extends FileImpService {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 接口类型-交易对手
	 */
	private static final String INTERFTYPE_CHB_MST_COUNTERPART = "CHB_MST_COUNTERPART";
	
	/**
	 * 检查数据类型-交易对手
	 */
	private static final String DATATYPE_CHB_COUNTER_PARTY = "CHB_COUNTER_PARTY";
	
	/**
	 * 重复性检查字段
	 */
	private static final String[] DUPLICATIED_LIST = {"COUNTER_ID"};
	
	/**
	 * 使用sqlloader入库的条数限制
	 */
	private static final int SQLLOADER_MAX_SIZE = 1000;
	
	/**
	 * mybatis的sqlSession管理核心
	 */
	@Autowired
	private SqlSessionTemplate template;
	
	/**
	 * 交易对手接入mapper
	 */
	@Autowired
	private ChbMstCounterPartyInterfMapper chbMstCounterPartyInterfMapper;
	
	/**
	 * 中债基础mapper
	 */
	@Autowired
	private BaseMapper baseMapper;
    /**
     * SqlLoader接入
     */
    @Resource
    private BatchInsertService batchInsertService;
	
	/**
	 * 交易对手表格名称
	 * 
	 */
	private static final String  MST_MST_COUNTER_TABLE_NAME = "CHB_MST_COUNTER_PARTY";
	/**
     * 业务主键
     */
    private static final String BUSSINESSKEY = "COUNTER_ID";
	
	/**
	 * 上游接口解析读取文件最大线程数
	 */
	@Value("${application.interfMaxCorePoolSize}")
	private int interfMaxCorePoolSize;
	
	/**
	 * 上游接口解析读取文件处理，单次sqlldr处理最大条数
	 */
	@Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
	private int reportInterfOneTimeSqlLdrDataCount;
	
    /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
    private int reportInterfOneTimeSqlLdrCommitCount;
	
	/**
	 * sqlldr用数据库连接名称
	 */
	@Value("${spring.datasource.reportSqlldrDatabaseName}")
    private String reportSqlldrDatabaseName;
	
	   /**
     * 数据批量一次性insert条数(单条数据字段较多)
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;
	
	/**
	 * 日期格式化
	 */
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
     * 功能:从交易对手信息接口获取交易对手信息
     * 描述：
     *      1.调用共通方法传入接口文件
     *      2.文件处理
     * @since 1.7.0
     * @version 1.7.0
     * @author：liuyu 2019年5月6日
     * @param currentDate 解析文件日期
     * @return 
     * String
     */
	@SuppressWarnings("unchecked")
	public String getAssetInfoByInterf(String currentDate) {
		// 调用共通方法传入接口文件
	    List<InterfFile> xmlFileList = queryAndDownloadFiles(currentDate, null);
        for (InterfFile interfFile : xmlFileList){
            if (interfFile != null
                    && StringUtil.isNotEmpty(interfFile.getFilename())){
                // 文件解析
                List<Map<String, String>>  counterPartyInfoList = new ArrayList<Map<String, String>>();
                try {
                    // 解析文件
                    Map<String, Object> map = parseFile(interfFile);
                    counterPartyInfoList = (List<Map<String, String>>) map.get("dataList");
                    String result = (String) map.get("remoteDir");
                    if ("fail".equals(result)){
                        return "fail";
                    }
                } catch (FileNotFoundException e) {
                    logger.error("交易对手信息解析错误！",e.getMessage(),e);
                    return "fail";
                }
                // 文件处理
                String saveResult = importDB(counterPartyInfoList,interfFile, currentDate);
                if ("fail".equals(saveResult)){
                    return "fail";
                }
            } else {
                return "fail";
            }
        }
        return "success";
	}
	
	
	
	

	/**
	 * 功能:文件处理方法
     * 描述： 
     *      1.parseFile --将文件转换为Map<String,String>
     *      2.checkData --校验数据
     *      3.saveData --入库
	 * @since 1.7.0
	 * @version 1.7.0
	 * @author：liuyu 2019年5月6日
	 * @param fileLists 
	 * @see com.joyintech.tams.common.FileImpService#saveDataBySteps(java.util.List)
	 */
	@Override
	public String importDB(List<Map<String, String>> listData,InterfFile interfFile, String date) {
	    String remoteDir = "success";
		// 添加待处理的数据
		List<Map<String,String>> listError = checkData(listData, interfFile.getFilename(),
		 		INTERFTYPE_CHB_MST_COUNTERPART,DATATYPE_CHB_COUNTER_PARTY,BUSSINESSKEY, DUPLICATIED_LIST);
		//有效数据入库 
        List<Map<String, String>> allData = new ArrayList<Map<String,String>>();
        allData.addAll(listData);
        String listDataResult = "success";
		// 读取系统参数
	    if(CollectionUtils.isEmpty(listData)){
	        return "success";
	    }
	    //待处理数据入库
	    ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.交易对手接口数据.getCode());
	        if (interfConfig == null){
	            logger.info("CHB_INTERF_CONFIG表中无配置项！");
	            return "fail";
	        }
		String saveFlag = interfConfig.getContentType();
		// 待处理数据入库
		if(allData.size() != 0){
            listDataResult = saveData(allData, saveFlag,interfFile.getDataSource());
        }
		if("success".equals(listDataResult)){
            remoteDir = "success";
        }else{
            remoteDir = "fail";
        }
        return remoteDir;
	}
	
	/**
     * 功能:数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：liuyu 2019年5月6日 
     * @param listData 数据
     * @param saveFlag:F-当日全删全插，I－增量(默认) 交易对手采用混合 新增方式  全部使用mergeinto方式
     * @see com.joyintech.tams.common.FileImpService#saveData()
     */
	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag,String dataSource) {
	    String result = "success";
        // 临时文件存放目录
        String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
        List<ChbMstCounterParty> mstCounterPartyList = new ArrayList<>();
        // 转换成dto对象
        // modify by convertMapToEntity yangxianjiu 2022-01-19 start
//        convertMapToEntity(listData, mstCounterPartyList);
        mstCounterPartyList = (List<ChbMstCounterParty>) convertMapToEntity(listData,ChbMstCounterParty.class, dataSource);
        mstCounterPartyList.stream().forEach(dto->{
            // 设置uuid
            dto.setUuid(UUID.randomUUID().toString());
        });
        // modify by convertMapToEntity yangxianjiu 2022-01-19 end
        List<ChbMstCounterParty> insertList = new LinkedList<>();
        List<ChbMstCounterParty> updateList = new LinkedList<>();
        separatorList(mstCounterPartyList,insertList,updateList);
        // 更新和删除
        result = update(updateList, saveFlag);
        // 插入
        result = insert(insertList, saveFlag);
        return result;
	}
	/**
	 * 
	* 将数据分为要插入数据和更新数据.
	* @author liuyu
	* @time 2019年5月18日 下午4:08:32
	* @param mstCounterPartyList
	* @param insertList
	* @param updateList
	 */
	private void separatorList(List<ChbMstCounterParty> infoList, List<ChbMstCounterParty> insertList, List<ChbMstCounterParty> updateList) {
		List<List<ChbMstCounterParty>> lists = ListUtils.partition(infoList, 100);
		List<String> counterId = new ArrayList<String>();
		for(List<ChbMstCounterParty> sublist : lists){
			counterId.addAll(chbMstCounterPartyInterfMapper.getExists(sublist));
		}
	    for (ChbMstCounterParty mstCounterParty:infoList) {
	        if(counterId.contains(mstCounterParty.getCounterId())){
	            updateList.add(mstCounterParty);
	        // 数据状态为D的不能添加
	        }else {
	            insertList.add(mstCounterParty);
	        }
        }
    }

    /**
	    * 功能：批量插入
	    * @since 1.7.0
	    * @version 1.7.0
	    * @author liuyu
	    * @param mstCountPartInfoList
	    * @param saveFlag F-当日全删全插，I－增量(默认)
	    */
	    private String insert(List<ChbMstCounterParty> mstCountPartInfoList, String saveFlag) {


                 if (mstCountPartInfoList.size()<1000) {
                     SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                     try {
                     int count = 0;
                     logger.debug("交易对手插入开始--------------" + new Date());
                     ChbMstCounterPartyInterfMapper batchMapper = session.getMapper(ChbMstCounterPartyInterfMapper.class);
                     int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
                     int batchLastIndex = batchCount;//每批最后一个的下标
                     for(int index = 0; index < mstCountPartInfoList.size();){
                         if(batchLastIndex > mstCountPartInfoList.size()-1){
                             batchLastIndex = mstCountPartInfoList.size();
                             batchMapper.insertBatch(mstCountPartInfoList.subList(index, batchLastIndex));
                             break;//数据插入完毕,退出循环
                         }else{
                             batchMapper.insertBatch(mstCountPartInfoList.subList(index, batchLastIndex));
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
                     logger.debug("交易对手插入结束--------------" + new Date());
                     session.commit();
                     // 清理缓存，防止溢出
                     session.clearCache();
                 }catch(Exception e){
                     logger.error("数据插入出错:",e.getMessage(),e);
                     return "fail";
                 } finally {
                     session.close();
                 }

                 } else {
                     //sqlloader插入
                     ImpTable table = new ImpTable("CHB_MST_COUNTER_PARTY");
                     table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
                     // 临时文件存放目录
                     String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

                     batchInsertService.exeBatchldr(tmpPath, mstCountPartInfoList, table);
                 }


	         logger.debug("交易对手插入结束--------------" + new Date());
	         return "success";
	    }
    
   /**
    * 功能：增量方式入库
    * @since 1.7.0
    * @version 1.7.0
    * @author liuyu
    * @param mstCountPartInfoList
    * @param saveFlag F-当日全删全插，I－增量(默认)
    */
    private String update(List<ChbMstCounterParty> mstCountPartInfoList, String saveFlag) {
    	 SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    	 try {
             int count = 0;
             logger.debug("交易对手插入开始--------------" + new Date());
             ChbMstCounterPartyInterfMapper batchMapper = session.getMapper(ChbMstCounterPartyInterfMapper.class);
             int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
             int batchLastIndex = batchCount;//每批最后一个的下标
             for(int index = 0; index < mstCountPartInfoList.size();){
                 if(batchLastIndex > mstCountPartInfoList.size()-1){
                     batchLastIndex = mstCountPartInfoList.size();
                     batchMapper.updateBachByStatus(mstCountPartInfoList.subList(index, batchLastIndex));
                     break;//数据插入完毕,退出循环
                 }else{
                     batchMapper.updateBachByStatus(mstCountPartInfoList.subList(index, batchLastIndex));
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
             logger.debug("交易对手插入结束--------------" + new Date());
             session.commit();
             // 清理缓存，防止溢出
             session.clearCache();
         }catch(Exception e){
             logger.error("数据插入出错:",e.getMessage(),e);
             return "fail";
         }
         finally {
             session.close();
         }
         logger.debug("交易对手插入结束--------------" + new Date());
         return "success";
	}





	/**
     * 功能:数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：liuyu 2019年5月6日 
     * @param mstCountPartInfoList 交易对手信息
     * @param saveFlag:0-当日全删全插，1－增量(默认)
     */
    public void insertDataBatch(List<ChbMstCounterParty> mstCountPartInfoList,int saveFlag) {
    	// 增量不执行
    	if(saveFlag == 1)
    		return ;
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("交易对手插入开始--------------" + df.format(new Date()));
            ChbMstCounterPartyInterfMapper batchMapper = session.getMapper(ChbMstCounterPartyInterfMapper.class);
            for (ChbMstCounterParty chbMstCounterPartyDto : mstCountPartInfoList) {
                batchMapper.insert(chbMstCounterPartyDto);
                count++;
                if (count % 1000 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                    logger.debug("交易对手已插入条数-----------" + count + df.format(new Date()));
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        finally {
            session.close();
        }
        logger.debug("投资交易插入结束--------------" + df.format(new Date()));
    }
    
    /**
     * 功能:数据入库前删除历史数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：liuyu 2019年5月6日 
     * @param mstCountPartInfoList 交易对手信息
     * @param saveFlag:F-当日全删全插，I－增量(默认)
     */
    public void deleteDataBatch(List<ChbMstCounterParty> mstCountPartInfoList,String saveFlag) {
    	SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            logger.debug("交易对手删除开始--------------" + new Date());
            // 全量直接清表
            if (com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ALL.equals(saveFlag)){
            	chbMstCounterPartyInterfMapper.deleteAllData();
            } else if (com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ADD.equals(saveFlag)){// 增量
            	chbMstCounterPartyInterfMapper.deleteByUpdateTime(false);
            } else {
                logger.error("无法识别全增量类型！");
            }
            logger.debug("交易对手删除结束--------------" + new Date());
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
     *
     * @param infoList 传入的listMap
     * @param entities 转换后的list
     * @param listCloumn 表列名
     * @see
     * @since 1.0
     */
    private List<ChbMstCounterParty> convertMapToEntity(List<Map<String, String>> infoList,
                                     List<ChbMstCounterParty> entities) {
        List<String> listCloumn = baseMapper.getTableColumnList(MST_MST_COUNTER_TABLE_NAME);
        for (Map<String, String> map : infoList) {
        	ChbMstCounterParty chbMstCounterParty = new ChbMstCounterParty();
            for (String string : listCloumn) {
                String value = map.get(string);
                 Field field = ReflectionUtils.findField(chbMstCounterParty.getClass(),
                        this.subBlank(StringUtil.lowerCase(string)));
//                if(field==null){
//                  System.out.println(string);
//                  continue;
//                }
                field.setAccessible(true);
                 Type type = field.getGenericType();
                if ("java.math.BigDecimal".equals(type.getTypeName())) {
                    try {
                        field.set(chbMstCounterParty,
                            (value == null || "".equals(value)) ? BigDecimal.ZERO : new BigDecimal(
                                value));
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }
                else if ("java.lang.Integer".equals(type.getTypeName())) {
                    try {
                        field.set(chbMstCounterParty,
                            (value == null || "".equals(value)) ? 0 : Integer.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }
                else if ("java.lang.Short".equals(type.getTypeName())) {
                    try {
                        field.set(chbMstCounterParty,
                            (value == null || "".equals(value)) ? 0 : Short.valueOf(value));
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }
                else if ("java.util.Date".equals(type.getTypeName())) {
                    if (value != null&&""!=value) {
                        try {
                            field.set(chbMstCounterParty, DateUtil.parse(value));
                        } catch ( IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                            return entities;
                        }
                    }
                }
                else {
                    try {
                        field.set(chbMstCounterParty, (value == null || "".equals(value)) ? "" : value);
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }
            }
            // 创建人
            chbMstCounterParty.setCreateUser(CommonUtil.getUserName());
            // 创建部门
            chbMstCounterParty.setCreateDept(CommonUtil.getDeptCode());
            // 设置uuid
            chbMstCounterParty.setUuid(UUID.randomUUID().toString());
        
            entities.add(chbMstCounterParty);
        }
        return entities;
    }

}
