/*
 * 文件名：DisRepProdInvestAdjrateService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：信息披露系统V1.0
 * 修改人：ZhangYangFei
 * 修改时间：2019年5月7日
 * 修改内容：新建
 * 系统名称：信息披露系统V1.0
 */
 
package com.joyintech.tams.svreport.interf.dis.prodinvestadjrate.service;

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
import com.joyintech.tams.svreport.interf.dis.prodinvestadjrate.dao.DisRepProdInvestAdjrateMapper;
import com.joyintech.tams.svreport.interf.dis.prodinvestadjrate.dto.DisRepProdInvestAdjrate;


/**
 * 产品资产种类投资比例调整数据接口
 *      产品资产种类投资比例调整数据，存入REP_PROD_INVEST_ADJRATE
 *      
 * @author ZhangYangFei
 * @version 1.0 2019年5月7日
 * @see DisRepProdInvestAdjrate
 * @since 1.0
 */
@Service
public class DisRepProdInvestAdjrateService extends FileImpService {
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
    private DisRepProdInvestAdjrateMapper disRepProdInvestAdjrateMapper;

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
        DisRepProdInvestAdjrate dto = null;
        List<DisRepProdInvestAdjrate> dtoList = new ArrayList<DisRepProdInvestAdjrate>();
        // modify by convertMapToEntity yangxianjiu 2022-01-19 start
        dtoList = (List<DisRepProdInvestAdjrate>) convertMapToEntity(listData,DisRepProdInvestAdjrate.class, dataSource);
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
//        		dto = transMap2Dto(data);
//                dtoList.add(dto);
//        	}catch(Exception e){
//        		logger.error("解析数据错误：",e.getMessage(),e);
//           		return  "fail";
//        	}
//
//        }
        //存表
        int success = this.deleteAndInsertRepProdInvestAdjrateByPart(dtoList);
        logger.info("saveData方法成功存入数据到REP_PROD_INVEST_ADJRATE：" + success + "条");
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
    private DisRepProdInvestAdjrate transMap2Dto(Map<String, String> data) {
        DisRepProdInvestAdjrate dto = new DisRepProdInvestAdjrate();
        dto.setId(IDGenerator.generate());//ID
        dto.setProdId(data.get("PROD_ID"));//产品代码
        dto.setInvestType(data.get("INVEST_TYPE"));//投资种类
        dto.setRate(data.get("INVEST_RATIO"));//投资比例
        dto.setInvestAdjustDate(DateUtil.parse(data.get("ADJUST_DATE")));//投资调整日期
        dto.setInputType("");//导入方法
        dto.setInputUser("");//导入用户
        dto.setAdapterId("0");//适配器ID
        dto.setStatus(data.get("STATUS"));//状态
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
     *      全删全插（删除条件：INVEST_ADJUST_DATE）
     * 
     * @param dtoList
     * @return
     * @see
     * @since 1.0
     */
    private int deleteAndInsertRepProdInvestAdjrateByPart(List<DisRepProdInvestAdjrate> dtoList) {
        //全量删除
        int delCount = disRepProdInvestAdjrateMapper.deleteRepProdInvestAdjrateByInvestAdjustDate(dtoList.get(0));
        logger.info("deleteAndInsertRepProdInvestAdjrateByPart方法删除REP_PROD_INVEST_ADJRATE数据：" + delCount + "条");
        
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertCount;
        //插入list大小
        int dataCount = dtoList.size();
        //若需要插入的数据比最大插入的数据少，则直接全部插入
        if (dataCount <= maxDataCountOneTime) {
            dataCount = disRepProdInvestAdjrateMapper.insertRepProdInvestAdjrateList(dtoList);
            return dataCount;
        }
        
        List<DisRepProdInvestAdjrate> newDataList = new ArrayList<DisRepProdInvestAdjrate>();
        newDataList.addAll(dtoList);
        try {
            //sqlloader插入
            ImpTable table = new ImpTable("REP_PROD_INVEST_ADJRATE");
            table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
            // 临时文件存放目录
            String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

            batchInsertService.exeBatchldr(tmpPath, newDataList, table);

        } catch (Exception e) {
            logger.error("sqlloader插入异常：{}",e.getMessage(),e);
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
                InterfDefine.产品资产种类投资比例调整数据.getCode());
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
        String inerfType = InterfDefine.产品资产种类投资比例调整数据.getCode();
        //检查数据类型
        String dataType = InterfDefine.产品资产种类投资比例调整数据.getCode();
        //重复性检查的keys
        String[] checkEuplicatedKeys = {"PROD_ID","INVEST_ADJUST_DATE","INVEST_TYPE"};
        String filename = interfFile == null ? null : interfFile.getFilename();
        List<Map<String, String>>  listA = super.checkData(dataList, filename, inerfType, dataType,null, checkEuplicatedKeys);
//        dataList.addAll(listA);// listA为错误数据，在checkData时被remove，需要重新放回dataList
    }
}
