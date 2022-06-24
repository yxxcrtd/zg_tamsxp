package com.joyintech.tams.svreport.interf.dis.prodbonusregister.service;

import java.math.BigDecimal;
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
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.svreport.interf.dis.prodbonusregister.dao.DisProdBondsRegistrationMapper;
import com.joyintech.tams.svreport.interf.dis.prodbonusregister.dto.DisProdBondsRegistration;

/**
 * 产品分红登记接口入库
 * @author ChuFengjie
 * @version 1.0 2019年5月6日
 * @see DisProdBondsRegistrationService
 * @since 1.7
 */
@Service
public  class DisProdBondsRegistrationService extends FileImpService  {
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
	private DisProdBondsRegistrationMapper prodBondsRegistrationMapper;

	public String saveData(List<Map<String, String>> listData,String saveFlag, String dataSource) {
		if (StringUtil.isEmptyList(listData)) {
            logger.info("saveData方法参数listData为空。");
            return "success";
        }
        
        List<DisProdBondsRegistration> dtoList = new ArrayList<DisProdBondsRegistration>();
		// modify by convertMapToEntity yangxianjiu 2022-01-19 start
		dtoList = (List<DisProdBondsRegistration>) convertMapToEntity(listData,DisProdBondsRegistration.class, dataSource);
		dtoList.stream().forEach(dto->{
			dto.setId(IDGenerator.generate(36));//ID主键
			dto.setInputType("1");//导入方法
			dto.setAdapterId("1");//适配器ID
		});

//        for(Map<String, String> data : listData){
//        	// 解析文件数据到dto
//    		DisProdBondsRegistration dto = new DisProdBondsRegistration();
//            //将map转换为dto
//        	try{
////        		 dto = dataConversionEntity(data);
//        		dto.setId(IDGenerator.generate(36));//ID主键
//        		dto.setProdId(data.get("PROD_ID"));//产品代码
//        		dto.setBonusRegisterDate(DateUtil.parse(data.get("BONUS_REGISTER_DATE")));//分红登记日
//        		dto.setBonusDealtDate(DateUtil.parse(data.get("BONUS_DEALT_DATE")));//分红下发日
//        		dto.setUnitBonusAmt(new BigDecimal(data.get("UNIT_BONUS_AMT")));//单位分红金额
//        		dto.setInputType("1");//导入方法
//        		dto.setInputUser("interf");//导入用户
//        		dto.setAdapterId("1");//适配器ID
//        		dto.setCreateUser("interf");//创建人
//        		dto.setCreateDept(data.get(""));//创建部门
////        		dto.setRemark(map.get("REMARK"));//备注
//				if (StringUtil.isNotEmpty(data.get("CREATE_TIME"))) {
//					dto.setCreateTime(DateUtil.parseDate(data.get("CREATE_TIME"),
//							new String[] { DateUtil.DEFAULT_DATETIME_FORMAT_ODS }));
//				} else {
//					dto.setCreateTime(new Date());
//				}
//				if (StringUtil.isNotEmpty(data.get("UPDATE_TIME"))) {
//					dto.setUpdateTime(DateUtil.parseDate(data.get("UPDATE_TIME"),
//							new String[] { DateUtil.DEFAULT_DATETIME_FORMAT_ODS }));
//				} else {
//					dto.setUpdateTime(new Date());
//				}
//                 dtoList.add(dto);
//        	}catch(Exception  e){
//        		logger.error("解析数据错误：",e.getMessage(),e);
//        		return  "fail";
//        	}
//        }
		// modify by convertMapToEntity yangxianjiu 2022-01-19 end
        //存表
        int success = this.mergeRepProdInfoByPart(dtoList);
        
        logger.info("saveData方法成功存入数据到REP_PROD_BONUS_REGISTER：" + success + "条");
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
    private int mergeRepProdInfoByPart(List<DisProdBondsRegistration> dtoList) {
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertLongCount;
        //插入list大小
        int dataCount = dtoList.size();
        //若需要插入的数据比最大插入的数据少，则直接全部插入
        if (dataCount <= maxDataCountOneTime) {
        	prodBondsRegistrationMapper.mergeRepProdInfo(dtoList);
            return dataCount;
        }
        
        List<DisProdBondsRegistration> newDataList = new ArrayList<DisProdBondsRegistration>();
        newDataList.addAll(dtoList);
        
        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
        	prodBondsRegistrationMapper.mergeRepProdInfo(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	prodBondsRegistrationMapper.mergeRepProdInfo(newDataList);
        }
        return dataCount;
    }

	
	/**
	 * map转换成实体类
	 * @param map
	 * @return
	 */
	DisProdBondsRegistration dataConversionEntity(Map<String, String> map){
		DisProdBondsRegistration dto = new DisProdBondsRegistration();
		dto.setId(IDGenerator.generate(36));//ID主键
		dto.setProdId(map.get("PROD_ID"));//产品代码
		dto.setBonusRegisterDate(DateUtil.parse(map.get("BONUS_REGISTER_DATE")));//分红登记日
		dto.setBonusDealtDate(DateUtil.parse(map.get("BONUS_DEALT_DATE")));//分红下发日
		dto.setUnitBonusAmt(new BigDecimal(map.get("UNIT_BONUS_AMT")));//单位分红金额
		dto.setInputType("1");//导入方法
		dto.setInputUser("interf");//导入用户
		dto.setAdapterId("1");//适配器ID
		dto.setCreateUser("interf");//创建人
		dto.setCreateDept(map.get(""));//创建部门
//		dto.setRemark(map.get("REMARK"));//备注
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

	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
        String rs = "success";
        // 对数据list进行处理
        if (!CollectionUtils.isEmpty(listData)) {
            //接口类型
            String inerfType = InterfDefine.产品分红登记.getCode();
            //检查数据类型
            String dataType = InterfDefine.产品分红登记.getCode();
            //重复性检查的keys
            String[] checkEuplicatedKeys = {"PROD_ID","BONUS_REGISTER_DATE"};
            List<Map<String, String>>  listA = super.checkData(listData, interfFile.getFilename(), inerfType, dataType,null, checkEuplicatedKeys);
//            listData.addAll(listA);// listA为错误数据，在checkData时被remove，需要重新放回dataList
            // 数据入库
            ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(
                InterfDefine.产品分红登记.getCode());
            if (interfConfig == null) {
                logger.info("CHB_INTERF_CONFIG表中无配置项！");
                return "fail";
            }
            String saveFlag = interfConfig.getContentType();
            rs = saveData(listData,saveFlag, interfFile.getDataSource());
        }
        return rs;
	}	

}
