package com.joyintech.tams.svreport.interf.dis.basiccalendardata.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.svreport.interf.dis.basiccalendardata.dao.DisBasicCalendarDataMapper;
import com.joyintech.tams.svreport.interf.dis.basiccalendardata.dto.DisBasicCalendarData;

/**
 * 基础日历信息数据接口入库
 * @author ChuFengjie
 * @version 1.0 2019年5月7日
 * @see DisBasicCalendarDataService
 * @since 1.7
 */
@Service
public  class DisBasicCalendarDataService extends FileImpService {
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
	private DisBasicCalendarDataMapper disBasicCalendarDataMapper;

	public String saveData(List<Map<String, String>> listData,String saveFlag,String dataSource) {
        if (StringUtil.isEmptyList(listData)) {
            logger.info("saveData方法参数listData为空。");
            return null;
        }
        // 解析文件数据到dto
        DisBasicCalendarData dto = null;
        List<DisBasicCalendarData> dtoList = new ArrayList<DisBasicCalendarData>();
        // modify by convertMapToEntity yangxianjiu 2022-01-19 start
        dtoList = (List<DisBasicCalendarData>) convertMapToEntity(listData,DisBasicCalendarData.class, dataSource);
        // modify by convertMapToEntity yangxianjiu 2022-01-19 end
//        for(Map<String, String> data : listData){
//            //将map转换为dto
//        	try{
//        	    dto = dataConversionEntity(data);
//                dtoList.add(dto);
//        	}
//        	catch(Exception e){
//				logger.error("解析数据错误：",e.getMessage(),e);
//           		return  "fail";
//			}
//        }
        //存表
        int success = this.mergeRepProdInfoByPart(dtoList);
        
        logger.info("saveData方法成功存入数据到MST_TRADE_CALENDAR：" + success + "条");
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
    private int mergeRepProdInfoByPart(List<DisBasicCalendarData> dtoList) {
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertLongCount;
        //插入list大小
        int dataCount = dtoList.size();
        //插入数据
        //若需要插入的数据比最大插入的数据少，则直接全部插入
        if (dataCount <= maxDataCountOneTime) {       	
        	disBasicCalendarDataMapper.mergeByModel(dtoList);
            return dataCount;
        }           
        // 数据量大的时候分批处理
        List<DisBasicCalendarData> newDataList = new ArrayList<DisBasicCalendarData>();
        newDataList.addAll(dtoList);
              
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
        	disBasicCalendarDataMapper.mergeByModel(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	disBasicCalendarDataMapper.mergeByModel(newDataList);
        }
        return dataCount;
    }

	
	/**
	 * map转换成实体类
	 * @param map
	 * @return
	 */
    DisBasicCalendarData dataConversionEntity(Map<String, String> map){
    	DisBasicCalendarData dto = new DisBasicCalendarData();
    	dto.setTradeCalendarId(map.get("TRADE_CALENDAR_ID"));//交易日历代码
		dto.setCalendarId(map.get("CALENDAR_ID"));//日历编号
		dto.setCreateUser("interf");//创建人
		dto.setCreateDept("");//创建部门
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
            String inerfType = InterfDefine.基础日历信息数据.getCode();
            //检查数据类型
            String dataType = InterfDefine.基础日历信息数据.getCode();
            //重复性检查的keys
            String[] checkEuplicatedKeys = {"TRADE_CALENDAR_ID","CALENDAR_ID"};
            List<Map<String, String>>  listA = super.checkData(dataList, file.getFilename(), inerfType, dataType,null, checkEuplicatedKeys);
//            dataList.addAll(listA);// listA为错误数据，在checkData时被remove，需要重新放回dataList
            // 数据入库
            ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(
                InterfDefine.基础日历信息数据.getCode());
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
