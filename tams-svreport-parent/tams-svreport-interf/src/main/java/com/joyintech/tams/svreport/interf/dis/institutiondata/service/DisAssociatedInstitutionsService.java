package com.joyintech.tams.svreport.interf.dis.institutiondata.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.svreport.interf.dis.institutiondata.dao.DisAssociatedInstitutionsMapper;
import com.joyintech.tams.svreport.interf.dis.institutiondata.dto.DisAssociatedInstitutions;

/**
 * 关联机构数据接口入库
 * @author ChuFengjie
 * @version 1.0 2019年5月7日
 * @see DisAssociatedInstitutionsService
 * @since 1.7
 */
@Service
public  class DisAssociatedInstitutionsService extends FileImpService  {
	 /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 长sql最大一次插入或者更新条数
     */
    @Value("${application.reportInsertLongCount}")
    public int reportInsertLongCount;

    
    @Autowired
	private DisAssociatedInstitutionsMapper disAssociatedInstitutionsMapper;

    @Autowired
    private BatchInsertService batchInsertService;

	public String saveData(List<Map<String, String>> listData,String saveFlag,String dateSource) {
        if (StringUtil.isEmptyList(listData)) {
            logger.info("saveData方法参数listData为空。");
            return "success";
        }
        // 解析文件数据到dto
        DisAssociatedInstitutions dto = null;
        List<DisAssociatedInstitutions> dtoList = new ArrayList<DisAssociatedInstitutions>();
        for(Map<String, String> data : listData){
            //将map转换为dto
        	try{
        		 dto = dataConversionEntity(data);
                 dtoList.add(dto);
        	}catch(Exception e){
				logger.error("解析数据错误：",e.getMessage(),e);
           		return  "fail";
			}
           
        }
        //存表
        int success = this.mergeRepProdInfoByPart(dtoList);
        
        logger.info("saveData方法成功存入数据到REP_RELORG：" + success + "条");
        return "success";
	}

    /**
     * 分批存表
     * 
     * @param dtoList
     * @return
     * @see
     * @since 1.0
     */
    private int mergeRepProdInfoByPart(List<DisAssociatedInstitutions> dtoList) {
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertLongCount;
        //插入list大小
        int dataCount = dtoList.size();
        //删除操作
        if(dataCount <= maxDataCountOneTime){
         //若需要删除的数据比最大删除的数据少，则直接全部删除
          disAssociatedInstitutionsMapper.deleteByModel(dtoList);	     	
        }else{
         //数据量大的时候分批删除
         List<DisAssociatedInstitutions> DataListDelete = new ArrayList<DisAssociatedInstitutions>();
         DataListDelete.addAll(dtoList);	
         int part = dataCount / maxDataCountOneTime; // 分批数   
         for (int i = 0; i < part; i++ ) {
         	disAssociatedInstitutionsMapper.deleteByModel(DataListDelete.subList(0, maxDataCountOneTime));
         	DataListDelete.subList(0, maxDataCountOneTime).clear();
         }
         // 最后剩下的数据
         if (CollectionUtils.isNotEmpty(DataListDelete)) {
         	disAssociatedInstitutionsMapper.deleteByModel(DataListDelete);
         } 
         logger.debug("关联机构数据删除结束，共删除："+dataCount+"条");
        }
        //插入数据
        //若需要插入的数据比最大插入的数据少，则直接全部插入
        if (dataCount <= maxDataCountOneTime) {       	
        	disAssociatedInstitutionsMapper.insertByModel(dtoList);
            return dataCount;
        }   
        
        // 数据量大的时候分批处理
        List<DisAssociatedInstitutions> newDataList = new ArrayList<DisAssociatedInstitutions>();
        newDataList.addAll(dtoList);
              
       try {
           //sqlloader插入
           ImpTable table = new ImpTable("REP_RELORG");
           table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
           // 临时文件存放目录
           String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

           batchInsertService.exeBatchldr(tmpPath, newDataList, table);
       } catch (Exception e) {
          logger.error("sqlloader插入失败{}",e.getMessage());
           return 0;
       }
        return dataCount;
    }

	
	/**
	 * map转换成实体类
	 * @param map
	 * @return
	 */
    DisAssociatedInstitutions dataConversionEntity(Map<String, String> map){
    	DisAssociatedInstitutions dto = new DisAssociatedInstitutions();
		dto.setRelorgId(map.get("RELORG_ID"));//关联机构代码
		dto.setCreateUser("interf");//创建人
		dto.setCreateDept(map.get(""));//创建部门
		dto.setUpdateUser("interf");//更新人		
		 try {
	            if(StringUtil.isNotEmpty(map.get("CREATE_TIME"))){
	                dto.setCreateTime(DateUtil.parseDate(map.get("CREATE_TIME"),new String[] {DateUtil.DEFAULT_DATETIME_FORMAT_ODS}));
	            }else{
	                dto.setCreateTime(new Date());
	            }
	            if(StringUtil.isNotEmpty(map.get("UPDATE_TIME"))){
	                dto.setUpdateTime(DateUtil.parseDate(map.get("UPDATE_TIME"),new String[] {DateUtil.DEFAULT_DATETIME_FORMAT_ODS}));
	            }else{
	                dto.setUpdateTime(new Date());
	            }
	        } catch (ParseException e) {
	            logger.error("transMap2Dto方法转换CreateTime或UpdateTime异常");
	            logger.error(e.getMessage(),e);
	        }
		return dto;
		
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public String importDB(List<Map<String, String>> dataList, InterfFile file, String date) {
        String rs = "success";
        // 对数据list进行处理
        if (!CollectionUtils.isEmpty(dataList)) {
            //接口类型
            String inerfType = InterfDefine.关联机构信息.getCode();
            //检查数据类型
            String dataType = InterfDefine.关联机构信息.getCode();
            //重复性检查的keys
            String[] checkEuplicatedKeys = {"RELORG_ID"};
            List<Map<String, String>>  listA = super.checkData(dataList, file.getFilename(), inerfType, dataType,null, checkEuplicatedKeys);
//            dataList.addAll(listA);// listA为错误数据，在checkData时被remove，需要重新放回dataList
            // 数据入库
            ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(
                InterfDefine.关联机构信息.getCode());
            if (interfConfig == null) {
                logger.info("CHB_INTERF_CONFIG表中无配置项！");
                return "fail";
            }
            String saveFlag = interfConfig.getContentType();
            rs = saveData(dataList, saveFlag, file.getDataSource());
        }
        return rs;
    }
}
