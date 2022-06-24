package com.joyintech.tams.svreport.interf.chb.assetindicator.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.svreport.interf.chb.assetindicator.dao.ChbAssetBasisChildInterfMapper;
import org.apache.commons.codec.digest.DigestUtils;
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

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.ChbAssetsBasisDataChildMapper;
import com.joyintech.tams.common.base.entity.ChbAssetsBasisDataChild;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.assetindicator.dao.ChbAssetBasisDataInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 母子资产基础指标数据接口
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrAssetBasisChildFileImpService extends FileImpService{
	
	public static final String AssetBasisDataTableName = InterfDefine.母子资产基础指标数据.getCode();
	
	public static final String ASSETBASISDATATABLENAME_2 = "CHB_ASSETS_BASIS_DATA_CHILD";
	
	
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
     * 数据批量一次查询或者删除条数
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChbAssetBasisChildInterfMapper chbAssetBasisInterfMapper;
	
	
	@Autowired
	private SqlSessionTemplate template;

	@Autowired
	private BatchInsertService batchInsertService;


	@SuppressWarnings("unchecked")
	@Override
	public String importDB(List<Map<String, String>> listData,InterfFile interfFile, String date) {
		// TODO Auto-generated method stub
		String remoteDir = "success";
		checkData(listData, interfFile.getFilename(), InterfDefine.母子资产基础指标数据.getCode(),
				InterfDefine.母子资产基础指标数据.getCode(),null, new String[]{"PROD_ID","ASSET_ID","VALUE_DATE"});
		//有效数据入库 
		List<Map<String, String>> allData = new ArrayList<Map<String,String>>();
		allData.addAll(listData);
//		allData.addAll(listError);
		String listDataResult = "success";
		//待处理数据入库
		com.joyintech.tams.common.base.entity.ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.母子资产基础指标数据.getCode());
        if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        
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


	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag,String dataSource) {
		boolean success = true;
		String type = "";
		String tmpPath =  SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		
		List<ChbAssetsBasisDataChild> assetList = new ArrayList<ChbAssetsBasisDataChild>();
		//转换实体类
		convertMapToEntity(listData, assetList, dataSource);
		

		if(InterfConstants.SAVEFLAG_ADD.equals(saveFlag)){
		//增量
			mergeData(assetList); 
			
		}else if(listData.size() <= 1000){
			//增量插入 小于100000正常入库 超过100000条使用sqlldr进行入库
			try{
				chbAssetBasisInterfMapper.deleteByUpdateTime();
				insertBasisData(assetList);
				success = true;
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				success = false;
			}
		}else{
			chbAssetBasisInterfMapper.deleteByUpdateTime();
			try {
				//sqlloader插入
				ImpTable table = new ImpTable("CHB_ASSETS_BASIS_DATA_CHILD");
				table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
				// 临时文件存放目录
				String path = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

				batchInsertService.exeBatchldr(path, assetList, table);
			} catch (Exception e) {
				logger.debug("查找sqlloder文件异常");
				logger.error(e.getMessage(), e);
				return "fail";
			}
		}
		return success?"success":"fail";
	}

	private void mergeData(List<ChbAssetsBasisDataChild> listNE) {
		 SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			try {
					 	int count = 0;
			            logger.debug("母子资产基础指标数据插入开始--------------" + new Date());
			            ChbAssetBasisDataInterfMapper batchMapper = session.getMapper(ChbAssetBasisDataInterfMapper.class);
			            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
			            int batchLastIndex = batchCount;//每批最后一个的下标
			            for(int index = 0; index < listNE.size();){
			                if(batchLastIndex > listNE.size()-1){
			                    batchLastIndex = listNE.size();
			                    batchMapper.insertChbAssetBasisData(listNE.subList(index, batchLastIndex));
			                    break;//数据插入完毕,退出循环
			                }else{
			                    batchMapper.insertChbAssetBasisData(listNE.subList(index, batchLastIndex));
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
			            session.commit();
			            // 清理缓存，防止溢出
			            session.clearCache();
			} finally {
				session.close();
			}
	 }

	private boolean excuteAssetBasisDataByStep(List<ChbAssetsBasisDataChild> ListData, String type,
			String tmpPath, ImpTable table) {
		// 定义新增数据list-验证不通过数据
		if (CollectionUtils.isNotEmpty(ListData)) {
			// 先删后插，根据插入数据的的业务主键 删除 底层资产持仓信息
			if (tmpPath == null) {
				// 插入资产基础指标数据
				insertBasisData(ListData);
			} else {
				createDataFile(tmpPath + "/CHB_CASSET_INDEX/CHB_CASSET_INDEX", ListData, table.getColumns());
			}
		}
		return CollectionUtils.isNotEmpty(ListData);
	}

	/**
	 * 根据联合主键(产品代码、资产代码、日期)批量删除资产基础指标数据
	 * @param listChbBotAstPosition
	 */
	private void deleteData(List<ChbAssetsBasisDataChild> list) {
		logger.debug("批量删除母子资产基础指标数据----开始-----:" + new Date());
		// 批量删除
		int singalCount =reportInsertLongCount;
		int deleteCount = list.size();
		if (deleteCount > 0) {
			if (deleteCount <= singalCount) {
				chbAssetBasisInterfMapper.deleteBatchList(list);
			} else {
				for (int i = 0; i < deleteCount / singalCount; i++) {
					chbAssetBasisInterfMapper.deleteBatchList(list.subList(0, singalCount));
					list.subList(0, singalCount).clear();
				}
				if (CollectionUtils.isNotEmpty(list)) {
					chbAssetBasisInterfMapper.deleteBatchList(list);
				}
			}
		}
		logger.debug("批量删除资产基础指标数据信息----结束-----:" + new Date());
	}

	private void insertBasisData(List<ChbAssetsBasisDataChild> list) {
		logger.debug("批量插入资产基础指标数据----开始-----:" + new Date());
    	SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    	//批量插入mapper
    	ChbAssetsBasisDataChildMapper batchMapper =  session.getMapper(ChbAssetsBasisDataChildMapper.class);
		try {
			int count = 0;
			for (ChbAssetsBasisDataChild assetBasisData : list) {
				batchMapper.insert(assetBasisData);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
			session.clearCache();
		} finally {
			session.close();
		}
		logger.debug("批量插入资产基础指标信息----结束-----:" +new Date());
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
    private void convertMapToEntity(List<Map<String, String>> infoList,
                                    List<ChbAssetsBasisDataChild> entities,
                                    String interType) {
        // 获取产品表的列
         List<String> listCloumn = chbAssetBasisInterfMapper.getTableColumnList(ASSETBASISDATATABLENAME_2);
        for (Map<String, String> map : infoList) {
        	ChbAssetsBasisDataChild chbAssetsBasisData = new ChbAssetsBasisDataChild();
            for (String string : listCloumn) {
                String value = map.get(string);
                Field field = ReflectionUtils.findField(chbAssetsBasisData.getClass(),
                    	this.subBlank(StringUtil.lowerCase(string)));
                field.setAccessible(true);
                Type type = field.getGenericType();
                if ("java.math.BigDecimal".equals(type.getTypeName())) {
                    try {
                        field.set(chbAssetsBasisData,
                            (value == null || "".equals(value)) ? BigDecimal.ZERO : new BigDecimal(
                                value));
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return ;
                    }
                }
                else if ("java.lang.Integer".equals(type.getTypeName())) {
                    try {
                        field.set(chbAssetsBasisData,
                            (value == null || "".equals(value)) ? 0 : Integer.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return ;
                    }
                }
                else if ("java.lang.Short".equals(type.getTypeName())) {
                    try {
                        field.set(chbAssetsBasisData,
                            (value == null || "".equals(value)) ? 0 : Short.valueOf(value));
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return ;
                    }
                }
                else if ("java.util.Date".equals(type.getTypeName())) {
                    if (value != null&&""!=value) {
                        try {
                            field.set(chbAssetsBasisData, DateUtil.parse(value));
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                            return ;
                        }
                    }
                }
                else {
                    try {
                        field.set(chbAssetsBasisData, (value == null || "".equals(value)) ? "" : value);
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return ;
                    }
                }
            }
            chbAssetsBasisData.setDataSource(interType);
            //判断是有效数据OR待处理数据并赋予STATUS
            if(map.get("ERRMSG") == null || "".equals(map.get("ERRMSG"))){
            	chbAssetsBasisData.setStatus("E");
            }else{
            	chbAssetsBasisData.setStatus("A");
            }
	            chbAssetsBasisData.setValId(IDGenerator.generate(36));
	            chbAssetsBasisData.setOrgCode(CommonUtil.selectBankCode());
	            chbAssetsBasisData.setCreateUser("system");
	            chbAssetsBasisData.setUpdateUser("system");
	            StringBuffer keyV = new StringBuffer();
	            keyV.append(map.get("PROD_ID")).append(map.get("ASSET_ID")).append(map.get("VALUE_DATE"));
	            chbAssetsBasisData.setMd5primarykey(DigestUtils.md5Hex( keyV.toString()));
	            entities.add(chbAssetsBasisData);
        }
    }




	
	
	

}
