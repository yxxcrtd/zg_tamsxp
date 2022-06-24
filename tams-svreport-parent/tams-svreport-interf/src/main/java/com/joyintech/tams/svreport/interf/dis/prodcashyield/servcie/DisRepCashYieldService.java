/*
 * 文件名：DisRepCashYieldService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：信息披露系统V1.0
 * 修改人：ZhangYangFei
 * 修改时间：2019年5月7日
 * 修改内容：新建
 * 系统名称：信息披露系统V1.0
 */
 
package com.joyintech.tams.svreport.interf.dis.prodcashyield.servcie;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.base.entity.RepCashYield;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.svreport.interf.dis.prodcashyield.dao.DisRepCashYieldMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品费率接口
 *      解析产品费率数据，存入REP_CASH_YIELD
 * @author ZhangYangFei
 * @version 1.0 2019年5月7日
 * @see DisRepCashYieldService
 * @since 1.0
 */
@Service
public class DisRepCashYieldService extends FileImpService{
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 数据批量一次性insert条数
     */
    @Value("${application.reportInsertCount}")
    public int reportInsertCount;
    
    /**
     * 数据批量一次查询或者删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public int reportSelectOrDeleteCount;
    
    @Autowired
    private DisRepCashYieldMapper disRepCashYieldMapper;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
        if (StringUtil.isEmptyList(listData)) {
            logger.info("saveData方法参数listData为空。");
            return "success";
        }
        logger.info("----产品费率入库，开始-----");
        List<RepCashYield> listNE = null;
        if(listData!=null&&listData.size()>0){
            // 解析文件数据到dto
            listNE = (List<RepCashYield>) convertMapToEntity(listData, RepCashYield.class, dataSource);
        }
        int success = 0;
        if (listNE != null && listNE.size() > 0) {
            success = this.deleteAndInsertRepCashYieldByPart(listNE);
        }
        logger.info("saveData方法成功存入数据到REP_CASH_YIELD：" + success + "条");
        logger.info("----产品费率入库，结束-----");
        return "success";
    }

//    /**
//     * 将map转换为dto
//     *
//     * @param data
//     * @return
//     * @see
//     * @since 1.0
//     */
//    private RepCashYield transMap2Dto(Map<String, String> data) {
//        RepCashYield dto = new RepCashYield();
//        dto.setBusiId(data.get("PROD_ID"));// 产品代码
//        dto.setCashId(IDGenerator.generate());// 费用代码-现金流代码
//        dto.setCashName(data.get("FEE_NAME"));// 费用名称-现金流名称
//        dto.setCashType(data.get("FEE_TYPE_1"));// 费用类型-现金流类型
////        dto.setCashType2(data.get("FEE_TYPE_2"));// 费用二级类型-现金流二级类型
//        dto.setCashType2(data.get("CASH_TYPE_2"));// 取【现金流二级类型】  zhangyf 20191123
//        dto.setRate(DisInterfDecimalUtil.str2BigDecimal(data.get("FEE_RATE")));// 费率-费率/收益率
//        dto.setStartDate(DateUtil.parse(data.get("START_DATE")));// 启用日
//        dto.setRemark(data.get("REMARK"));// 备注
//        dto.setStatus(data.get("STATUS"));
//
//        dto.setInputType("");// 导入方法
//        dto.setInputUser("");// 导入用户
//        dto.setAdapterId("0");// 适配器ID
//        dto.setCreateUser("interf");
//        dto.setCreateDept(data.get(""));
//        dto.setUpdateUser("interf");
//
//        /**add by yinya 新增产品费率信息：银行收取比例（浮动管理费选填） on 20211014 start**/
//        if (StringUtil.isNotEmpty(data.get("CHARGE_RATIO"))) {
//            dto.setChargeRatio(DisInterfDecimalUtil.str2BigDecimal(data.get("CHARGE_RATIO")));//最少追加金额
//        }
//        /**add by yinya 新增产品费率信息：银行收取比例（浮动管理费选填） on 20211014 end**/
//        try {
//            if(StringUtil.isNotEmpty(data.get("CREATE_TIME"))){
//                dto.setCreateTime(DateUtil.parseDate(data.get("CREATE_TIME"),new String[] {DateUtil.DEFAULT_DATETIME_FORMAT_ODS}));
//            }else{
//                dto.setCreateTime(new Date());
//            }
//            if(StringUtil.isNotEmpty(data.get("UPDATE_TIME"))){
//                dto.setUpdateTime(DateUtil.parseDate(data.get("UPDATE_TIME"),new String[] {DateUtil.DEFAULT_DATETIME_FORMAT_ODS}));
//            }else{
//                dto.setUpdateTime(new Date());
//            }
//        } catch (ParseException e) {
//            logger.error("transMap2Dto方法转换CreateTime或UpdateTime异常");
//            logger.error(e.getMessage(),e);
//        }
//        return dto;
//    }

    /**
     * 存表
     *      全删全插（删除条件：CASH_TYPE、START_DATE）
     * @param dtoList
     * @return
     * @see
     * @since 1.0
     */
    private int deleteAndInsertRepCashYieldByPart(List<RepCashYield> dtoList) {
        //删除
//        int delCount = disRepCashYieldMapper.deleteRepCashYieldByStartDateAndCashType(dtoList.get(0));
//        logger.info("deleteAndInsertRepCashYieldByPart方法删除REP_CASH_YIELD数据：" + delCount + "条");
        
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertCount;
        //插入list大小
        int dataCount = dtoList.size();
        //若需要插入的数据比最大插入的数据少，则直接全部插入
        if (dataCount <= maxDataCountOneTime) {
            disRepCashYieldMapper.mergeRepCashYield(dtoList);
            return dataCount;
        }
        
        List<RepCashYield> newDataList = new ArrayList<RepCashYield>();
        newDataList.addAll(dtoList);
        
        // 分批存入
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            disRepCashYieldMapper.mergeRepCashYield(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            disRepCashYieldMapper.mergeRepCashYield(newDataList);
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
            // 数据检查
            checkSaveData(dataList, interfFile);
            // 数据入库
            ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(
                InterfDefine.产品费率数据.getCode());
            if (interfConfig == null) {
                logger.info("CHB_INTERF_CONFIG表中无配置项！");
                return "fail";
            }
            String saveFlag = interfConfig.getContentType();
            rs = saveData(dataList,saveFlag, interfFile.getDataSource());
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
        String inerfType = InterfDefine.产品费率数据.getCode();
        //检查数据类型
        String dataType = InterfDefine.产品费率数据.getCode();
        //重复性检查的keys
        String[] checkEuplicatedKeys = {"BUSI_ID","CASH_TYPE2","START_DATE"};
        String filename = interfFile == null ? null : interfFile.getFilename();
        List<Map<String, String>>  listA = super.checkData(dataList, filename, inerfType, dataType,null, checkEuplicatedKeys);
//        dataList.addAll(listA);// listA为错误数据，在checkData时被remove，需要重新放回dataList
    }
}
