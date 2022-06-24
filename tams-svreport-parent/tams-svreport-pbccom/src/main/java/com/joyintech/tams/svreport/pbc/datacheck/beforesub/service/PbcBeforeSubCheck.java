/*
 * 文件名：BeforeBatchCheck.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Administrator
 * 修改时间：2019年4月16日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.pbc.datacheck.beforesub.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.pbc.datacheck.beforesub.dao.PbcBeforeSubCheckMapper;
import com.joyintech.tams.svreport.pbc.datacheck.beforesub.dto.PbcBeforeSubCheckDto;
import com.joyintech.tools.collectors.JoyinCollectors;

/**
 * 人行报表报送前检查
 * 
 * @author yuluan
 * @version 1.0 2019年6月16日
 * @see PbcBeforeSubCheck
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PbcBeforeSubCheck extends SvrCBBaseService{
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PbcBeforeSubCheckMapper pbcBeforeSubCheckMapper;
    
    @Autowired
    private CheckReport001Mapper checkReport001Mapper;
    
    @Autowired
    private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
    
    @Autowired
    private PbcBeforeSubErrorCheck pbcBeforeSubErrorCheck;
    /**
     * 
     * 检查总表
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */
    public int checkBeforeSub(Date checkdate) {
        //检查结果
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();

        // 删除检查表数据
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品基本信息.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品终止信息.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_新人行产品基本信息.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_新人行产品终止信息.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品资金募集信息.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_人行月度统计报表.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_资产负债信息.getValue(),checkdate,null);
       
        //增加例外情况过滤
        List<ChbCheckRuleout> listPrdBaseInfoRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.PBC_PRD_BASEINFO_SUB);
        Map<String, List<ChbCheckRuleout>> mapPrdBaseInfoRuleout = listPrdBaseInfoRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //增加例外情况过滤
        List<ChbCheckRuleout> listPrdEndRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.PBC_PRD_BASEINFO_END_SUB);
        Map<String, List<ChbCheckRuleout>> mapPrdEndRuleout = listPrdEndRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //增加例外情况过滤
        List<ChbCheckRuleout> listPrdRaiseRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.PBC_PROD_RAISING_SUB);
        Map<String, List<ChbCheckRuleout>> mapPrdRaiseRuleout = listPrdRaiseRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //报送前强制的错误检查
        resultList.addAll(pbcBeforeSubErrorCheck.checkPrdBaseInfoSub(checkdate,null,mapPrdBaseInfoRuleout));
        
        //警告逻辑检查
         resultList.addAll(checkPbcProdBaseInfoSub(checkdate,mapPrdBaseInfoRuleout));
         resultList.addAll(checkPbcProdBaseInfoEndSub(checkdate,mapPrdEndRuleout));
         //ZGBSEI-445  【新人行升级】功能遗漏：新人行升级未考虑延时报送检查功能
        resultList.addAll(checkPbcProdBaseInfoSubNew(checkdate,mapPrdBaseInfoRuleout));
        resultList.addAll(checkPbcProdBaseInfoEndSubNew(checkdate,mapPrdEndRuleout));

         resultList.addAll(checkPbcPrdRaisingSub(checkdate,mapPrdRaiseRuleout));
         resultList.addAll(checkPbcReport(checkdate));
         resultList.addAll(checkPbcPbcPrdAssetDebtSub(checkdate));
        
        //报错检查异常数据
        int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
		int batchLastIndex = batchCount;// 每批最后一个的下标
		for (int index = 0; index < resultList.size();) {
			if (batchLastIndex > resultList.size() - 1) {
				batchLastIndex = resultList.size();
				checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
				break;// 数据删除完毕,退出循环
			} else {
				checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
				index = batchLastIndex;// 设置下一批下标
				batchLastIndex = index + batchCount;
			}
		}
        return resultList.size(); 
        
    }


  
    /**
     * 
     * 人行产品基本信息检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */    
    public List<CheckReport001Dto> checkPbcProdBaseInfoSub(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout) {
    	 logger.info("人行产品基本信息检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        //查找募集登记检查数据
        List<PbcBeforeSubCheckDto> checklist = pbcBeforeSubCheckMapper.selectPbcPrdBaseInfoSubCheck(checkdate);
        for ( PbcBeforeSubCheckDto m:checklist ) {
        	//增加例外情况过滤
        	if(mapRuleout.containsKey(m.getProdid())){
        		continue;
        	}
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_产品基本信息.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_产品基本信息.getCodeName());
            chbReportCheck.setValidKey("产品代码:"+m.getProdid());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";
            if ( m.getProdprofit1().compareTo(BigDecimal.ONE)<0 ) {//不合格
            	warnInfo += "预期最高收益率小于1%!";
            } 
            if ( m.getProdprofit2().compareTo(BigDecimal.ONE)<0 ) {//不合格
            	warnInfo += "预期最低收益率小于1%!";
            }  
            if(!warnInfo.equals("")){
            	chbReportCheck.setWarnInfo(warnInfo);
            	resultList.add(chbReportCheck);
            }
        }
        
        logger.info("人行产品基本信息检查结束");
        return resultList;

    }


    /**
     *ZGBSEI-445  【新人行升级】功能遗漏：新人行升级未考虑延时报送检查功能
     * 人行产品基本信息检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */
    public List<CheckReport001Dto> checkPbcProdBaseInfoSubNew(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout) {
        logger.info("人行产品基本信息检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        //查找募集登记检查数据
        List<PbcBeforeSubCheckDto> checklist = pbcBeforeSubCheckMapper.selectPbcPrdBaseInfoNewSubCheck(checkdate);
        for ( PbcBeforeSubCheckDto m:checklist ) {
            //增加例外情况过滤
            if(mapRuleout.containsKey(m.getProdid())){
                continue;
            }
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_新人行产品基本信息.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_新人行产品基本信息.getCodeName());
            chbReportCheck.setValidKey("产品代码:"+m.getProdid());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                    DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";
            if ( m.getProdprofit1().compareTo(BigDecimal.ONE)<0 ) {//不合格
                warnInfo += "预期最高收益率小于1%!";
            }
            if ( m.getProdprofit2().compareTo(BigDecimal.ONE)<0 ) {//不合格
                warnInfo += "预期最低收益率小于1%!";
            }
            if(!warnInfo.equals("")){
                chbReportCheck.setWarnInfo(warnInfo);
                resultList.add(chbReportCheck);
            }
        }

        logger.info("人行产品基本信息检查结束");
        return resultList;

    }
    
    /**
     * 
     * 人行产品终止信息登记检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */    
    public List<CheckReport001Dto> checkPbcProdBaseInfoEndSub(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout) {
    	 logger.info("人行产品终止信息登记检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        //查找募集登记检查数据
        List<PbcBeforeSubCheckDto> checklist = pbcBeforeSubCheckMapper.selectPbcPrdBaseInfoEndSubCheck(checkdate);
        for ( PbcBeforeSubCheckDto m:checklist ) {
        	//增加例外情况过滤
        	if(mapRuleout.containsKey(m.getProdid())){
        		continue;
        	}
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_产品终止信息.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_产品终止信息.getCodeName());
            chbReportCheck.setValidKey("产品代码:"+m.getProdid()+" 币种:"+m.getCcy());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";
            if ( m.getProdprofit1().compareTo(BigDecimal.ZERO)<=0 ) {//客户收益
            	warnInfo += "机构实现收入及折人民币金额为零或负数!";
            } 
            if ( m.getCustprofit1().compareTo(BigDecimal.ONE)<0 ) {//不合格
            	warnInfo += "兑付客户收益率小于1%";
            }  
            if(!warnInfo.equals("")){
            	chbReportCheck.setWarnInfo(warnInfo);
            	resultList.add(chbReportCheck);
            }
        }
        
        logger.info("人行产品终止信息登记检查结束");
        return resultList;
        
    }

    /**
     *ZGBSEI-445  【新人行升级】功能遗漏：新人行升级未考虑延时报送检查功能
     * 人行产品终止信息登记检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */
    public List<CheckReport001Dto> checkPbcProdBaseInfoEndSubNew(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout) {
        logger.info("人行产品终止信息登记检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        //查找募集登记检查数据
        List<PbcBeforeSubCheckDto> checklist = pbcBeforeSubCheckMapper.selectPbcPrdBaseInfoNewEndSubCheck(checkdate);
        for ( PbcBeforeSubCheckDto m:checklist ) {
            //增加例外情况过滤
            if(mapRuleout.containsKey(m.getProdid())){
                continue;
            }
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_新人行产品终止信息.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_新人行产品终止信息.getCodeName());
            chbReportCheck.setValidKey("产品代码:"+m.getProdid()+" 币种:"+m.getCcy());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                    DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";
            if ( m.getProdprofit1().compareTo(BigDecimal.ZERO)<=0 ) {//客户收益
                warnInfo += "机构实现收入及折人民币金额为零或负数!";
            }
            if ( m.getCustprofit1().compareTo(BigDecimal.ONE)<0 ) {//不合格
                warnInfo += "兑付客户收益率小于1%";
            }
            if(!warnInfo.equals("")){
                chbReportCheck.setWarnInfo(warnInfo);
                resultList.add(chbReportCheck);
            }
        }

        logger.info("人行产品终止信息登记检查结束");
        return resultList;

    }

    /**
     * 
     * 人行产品资金募集信息登记检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */    
    public List<CheckReport001Dto> checkPbcPrdRaisingSub(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout) {
    	 logger.info("人行产品资金募集信息登记检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        //查找募集登记检查数据
        List<PbcBeforeSubCheckDto> checklist = pbcBeforeSubCheckMapper.selectPbcPrdRaisingSubCheck(checkdate);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        for ( PbcBeforeSubCheckDto m:checklist ) {
        	//增加例外情况过滤
        	if(mapRuleout.containsKey(m.getProdid())){
        		continue;
        	}
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_产品资金募集信息.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_产品资金募集信息.getCodeName());
            chbReportCheck.setValidKey("产品代码:"+m.getProdid());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";

            //delete by wangyaning 20220510 start LCZCP-32
//            String c1=m.getCustomerType();
//            String c2=m.getCustomerType1();
//            if(c1!=null){
//            	String [] c1s=c1.split(",");
//            	for(String  str:c1s){
//            		if(c2==null||!c2.contains(str)){
//            			warnInfo += "客户类型与产品基本信息的客户类型不相符!";
//            			break;
//            		}
//            	}
//            }else{
//            	if(c2!=null){
//            		warnInfo += "客户类型与产品基本信息的客户类型不相符!";
//            	}
//            }
            //delete by wangyaning 20220510 end LCZCP-32
            
            if(!sdf.format(m.getVdate()).equals(sdf.format(m.getRaisingDate()))&&(m.getOperateMode().equals("4")||m.getOperateMode().equals("5"))){
	            if (m.getProdprofit1()!=null && m.getProdprofit1().compareTo(BigDecimal.ZERO)>0 ) {//客户收益
	            	warnInfo += "封闭式产品报送了当期申购数据，请核实是否为特殊情况!";
	            } 
            }
            if(!warnInfo.equals("")){
            	chbReportCheck.setWarnInfo(warnInfo);
            	resultList.add(chbReportCheck);
            }
        }
        
        logger.info("人行产品资金募集信息登记检查结束");
        return resultList;
        
    }
    /**
     * 
     * 人行月报检检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */    
    public List<CheckReport001Dto> checkPbcReport(Date checkdate) {
    	 logger.info("人行月报检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        Calendar calendar = Calendar.getInstance();
	    calendar.setTime(checkdate);
        calendar.add(Calendar.MONTH, 1);
	    calendar.set(Calendar.DAY_OF_MONTH, 0);
	    Date endate=calendar.getTime();
        //查找募集登记检查数据
        List<PbcBeforeSubCheckDto> checklist = pbcBeforeSubCheckMapper.selectPbcAssetDebt(endate,checkdate);
        //不合格的数据加入到不合格列表
        CheckReport001Dto chbReportCheck = new CheckReport001Dto();
        chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
//        chbReportCheck.setRefseqno(m.getRegistseqno());
        chbReportCheck.setRepCode(DictCodeListExt2.报表名称_人行月度统计报表.getValue());
        chbReportCheck.setRepName(DictCodeListExt2.报表名称_人行月度统计报表.getCodeName());
        String warnInfo="";
        String assetcodes="";
        for ( PbcBeforeSubCheckDto m:checklist ) {
           
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            assetcodes +=m.getAssetcode()+",";
            warnInfo+=m.getAssetcode()+":人行资产类型字段为空！";
            
        }
        if(!warnInfo.equals("")){
        	chbReportCheck.setValidKey("资产代码:"+assetcodes);
        	chbReportCheck.setWarnInfo(warnInfo);
        	resultList.add(chbReportCheck);
        }
        logger.info("人行月报检查结束");
        return resultList;
        
    }
    /**
     * 
     * 人行资产负债信息检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */    
    public List<CheckReport001Dto> checkPbcPbcPrdAssetDebtSub(Date checkdate) {
    	 logger.info("人行资产负债信息检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        Calendar calendar = Calendar.getInstance();
	    calendar.setTime(checkdate);
        calendar.add(Calendar.MONTH, 1);
	    calendar.set(Calendar.DAY_OF_MONTH, 0);
	    Date endate=calendar.getTime();
        //查找募集登记检查数据
        List<PbcBeforeSubCheckDto> checklist = pbcBeforeSubCheckMapper.selectPbcAssetDebt(endate,checkdate);
        for ( PbcBeforeSubCheckDto m:checklist ) {
        	
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_资产负债信息.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_资产负债信息.getCodeName());
            chbReportCheck.setValidKey("资产代码:"+m.getAssetcode());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";
            warnInfo+=m.getAssetcode()+"人行资产类型字段为空！";
            
            if(!warnInfo.equals("")){
            	chbReportCheck.setWarnInfo(warnInfo);
            	resultList.add(chbReportCheck);
            }
        }
        
        logger.info("人行资产负债信息检查结束");
        return resultList;
        
    }
    /**
     * 功能:数据变更接口统一调度入口
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月9日
     * @param executeDate
     * @param prodList
     * @param assetList
     * @throws BusinessException 
     * @see com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService#execute(java.util.Date, java.util.List, java.util.List)
     */
    @Override
    public void execute(Date executeDate, List<String> prodList, List<String> assetList,Map<String,List<String>> param) throws BusinessException {
        try {
            // 设置批处理bean
            setBatchBeanName(BatchBeanName.数据检查);
            // 批处理开始
            logInfoBatchStart();
            checkBeforeSub(executeDate);
            // 批处理结束
            logInfoBatchEnd();
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }
   
}
