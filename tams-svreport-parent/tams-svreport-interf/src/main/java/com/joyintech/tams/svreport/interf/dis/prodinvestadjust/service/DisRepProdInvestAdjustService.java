/*
 * 文件名：DisRepProdInvestAdjustService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：信息披露系统V1.0
 * 修改人：ZhangYangFei
 * 修改时间：2019年5月7日
 * 修改内容：新建
 * 系统名称：信息披露系统V1.0
 */
 
package com.joyintech.tams.svreport.interf.dis.prodinvestadjust.service;

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
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.svreport.interf.dis.common.DisInterfDecimalUtil;
import com.joyintech.tams.svreport.interf.dis.prodinvestadjust.dao.DisRepProdInvestAdjustMapper;
import com.joyintech.tams.svreport.interf.dis.prodinvestadjust.dto.DisRepProdInvestAdjust;


/**
 * 产品资产调整接口
 *      产品资产调整数据，存入REP_PROD_INVEST_ADJUST
 *      
 * @author ZhangYangFei
 * @version 1.0 2019年5月7日
 * @see DisRepProdInvestAdjust
 * @since 1.0
 */
@Service
public class DisRepProdInvestAdjustService extends FileImpService {
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 数据批量一次性insert条数
     */
    @Value("${application.reportInsertCount}")
    public int reportInsertCount;
    
    @Autowired
    private DisRepProdInvestAdjustMapper disRepProdInvestAdjustMapper;

    @Autowired
    private BatchInsertService batchInsertService;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
        if (StringUtil.isEmptyList(listData)) {
            logger.info("saveData方法参数listData为空。");
            return "success";
        }
        // 解析文件数据到dto
        DisRepProdInvestAdjust dto = null;
        List<DisRepProdInvestAdjust> dtoList = new ArrayList<DisRepProdInvestAdjust>();
        // modify by convertMapToEntity yangxianjiu 2022-01-19 start
        dtoList = (List<DisRepProdInvestAdjust>) convertMapToEntity(listData,DisRepProdInvestAdjust.class, dataSource);
        dtoList.stream().forEach(t->{
            t.setId(IDGenerator.generate());//ID
            t.setInputType("");//导入方法
            t.setInputUser("");//导入用户
            t.setAdapterId("0");//适配器ID
        });
        // modify by convertMapToEntity yangxianjiu 2022-01-19 end
//        for(Map<String, String> data : listData){
//            //将map转换为dto
//        	try{
//        		 dto = transMap2Dto(data);
//                 dtoList.add(dto);
//        	}catch(Exception e){
//        		logger.error("解析数据错误：",e.getMessage(),e);
//           		return  "fail";
//        	}
//        }
        //存表
        int success = this.deleteAndInsertRepProdInvestAdjustByPart(dtoList);
        logger.info("saveData方法成功存入数据到REP_PROD_INVEST_ADJUST：" + success + "条");
        return "success";
    }

    /**
     * 将map转换为dto
     * 
     * @param data
     * @return
     * @see
     * @since 1.0
     */
    private DisRepProdInvestAdjust transMap2Dto(Map<String, String> data) {
        DisRepProdInvestAdjust dto = new DisRepProdInvestAdjust();
        dto.setId(IDGenerator.generate());//ID
        dto.setProdId(data.get("PROD_ID"));//产品代码
        dto.setInvestRange(data.get("INVEST_RANGE"));//投资范围
        dto.setInvestType(data.get("INVEST_TYPE"));//投资种类
        dto.setAdjustDate(DateUtil.parse(data.get("ADJUST_DATE")));//调整日期
        dto.setRaiseMax(DisInterfDecimalUtil.str2BigDecimal(data.get("RAISE_MAX")));//募集金额上限
        dto.setStatus(data.get("STATUS"));//状态
        
        dto.setInputType("");//导入方法
        dto.setInputUser("");//导入用户
        dto.setAdapterId("0");//适配器ID
        dto.setCreateUser("interf");
        dto.setCreateDept("");
        dto.setUpdateUser("interf");
        try {
            if(StringUtil.isNotEmpty(data.get("CREATE_TIME"))){
                dto.setCreateTime(DateUtil.parseDate(data.get("CREATE_TIME"),new String[] {DateUtil.DEFAULT_DATETIME_FORMAT_ODS}));
            }else{
                dto.setCreateTime(new Date());
            }
            if(StringUtil.isNotEmpty(data.get("UPDATE_TIME"))){
                dto.setUpdateTime(DateUtil.parseDate(data.get("UPDATE_TIME"),new String[] {DateUtil.DEFAULT_DATETIME_FORMAT_ODS}));
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
     * 存表
     *      全删全插（删除条件：ADJUST_DATE）
     * 
     * @param dtoList
     * @return
     * @see
     * @since 1.0
     */
    private int deleteAndInsertRepProdInvestAdjustByPart(List<DisRepProdInvestAdjust> dtoList) {
        //全量删除
        int delCount = disRepProdInvestAdjustMapper.deleteRepProdInvestAdjustByAdjustDate(dtoList.get(0));
        logger.info("deleteAndInsertRepProdInvestAdjustByPart方法删除REP_PROD_INVEST_ADJUST数据：" + delCount + "条");
        
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertCount;
        //插入list大小
        int dataCount = dtoList.size();
        //若需要插入的数据比最大插入的数据少，则直接全部插入
        if (dataCount <= maxDataCountOneTime) {
            dataCount = disRepProdInvestAdjustMapper.insertRepProdInvestAdjustList(dtoList);
            return dataCount;
        }
        
        List<DisRepProdInvestAdjust> newDataList = new ArrayList<DisRepProdInvestAdjust>();
        newDataList.addAll(dtoList);
        try {
            //sqlloader插入
            ImpTable table = new ImpTable("REP_PROD_INVEST_ADJUST");
            table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
            // 临时文件存放目录
            String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

            batchInsertService.exeBatchldr(tmpPath, newDataList, table);
        }catch (Exception e) {
            logger.error("gsqlloader插入异常，异常内容：" + e.getMessage(),e);
            return 0;
        }
        return dataCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String importDB(List<Map<String, String>> dataList, InterfFile interfFile, String date) {
        String rs = "success";
        // 对数据list进行处理
        if (!CollectionUtils.isEmpty(dataList)) {
            checkSaveData(dataList, interfFile);
            // 数据入库
            ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(
                InterfDefine.产品资产调整数据.getCode());
            if (interfConfig == null) {
                logger.info("CHB_INTERF_CONFIG表中无配置项！");
                return "fail";
            }
            String saveFlag = interfConfig.getContentType();
            rs = saveData(dataList, saveFlag, interfFile.getDataSource());
        }
        return rs;
    }
    
    /**
     * 数据校验
     * 
     * @param dataList 入库的数据
     * @param interfFile 文件名
     * @see
     * @since 1.0
     */
    private void checkSaveData(List<Map<String, String>> dataList, InterfFile interfFile) {
        //接口类型
        String inerfType = InterfDefine.产品资产调整数据.getCode();
        //检查数据类型
        String dataType = InterfDefine.产品资产调整数据.getCode();
        //重复性检查的keys
        String[] checkEuplicatedKeys = {"PROD_ID","ADJUST_DATE","INVEST_RANGE","INVEST_TYPE"};
        String filename = interfFile == null ? null : interfFile.getFilename();
        List<Map<String, String>>  listA = super.checkData(dataList, filename, inerfType, dataType,null, checkEuplicatedKeys);
//        dataList.addAll(listA);// listA为错误数据，在checkData时被remove，需要重新放回dataList
    }
}
