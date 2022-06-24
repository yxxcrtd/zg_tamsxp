/*
 * 文件名：BeforeBatchCheck.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Administrator
 * 修改时间：2019年4月16日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.chb.datacheck.beforesub.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
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
import com.joyintech.tams.svreport.chb.common.SvrCustomerNumCheckService;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.dao.ChbBeforeSubCheckMapper;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.dto.ChbBeforeSubCheckDto;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 中债一期、二期报送前检查
 * 
 * @author chenxc
 * @version 1.0 2019年4月16日
 * @see BeforeSubCheck
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BeforeSubCheck extends SvrCBBaseService{
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChbBeforeSubCheckMapper chbBeforeSubCheckMapper;
    
    @Autowired
    private CheckReport001Mapper checkReport001Mapper;
    
    @Autowired
    private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
    
    @Autowired
    private BeforeSubErrorCheck beforeSubErrorCheck;

    @Autowired
    private SvrCustomerNumCheckService svrCustomerNumCheckService;
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
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品终止登记.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_募集总量登记.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品存续期登记.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品发行登记.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_资产交易信息登记.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_资管计划底层持仓信息登记.getValue(),checkdate,null);
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_银行月度统计表.getValue(),checkdate,null);
       
        //增加例外情况过滤
        List<ChbCheckRuleout> listPrdEndRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_PRD_END_SUB);
        Map<String, List<ChbCheckRuleout>> mapPrdEndRuleout = listPrdEndRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //增加例外情况过滤
        List<ChbCheckRuleout> listTotalRaiseRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_PRD_RAISETOT_SUB);
        Map<String, List<ChbCheckRuleout>> mapTotalRaiseRuleout = listTotalRaiseRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //增加例外情况过滤
        List<ChbCheckRuleout> listPrdDurationRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_PRD_DEAL_SUB);
        Map<String, List<ChbCheckRuleout>> mapPrdDurationRuleout = listPrdDurationRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //增加例外情况过滤
        List<ChbCheckRuleout> listPrdIssueRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_PRD_ISSUE_SUB);
        Map<String, List<ChbCheckRuleout>> mapPrdIssueRuleout = listPrdIssueRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //增加例外情况过滤
        List<ChbCheckRuleout> listInvestTradeRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_INVEST_TRADE_SUB);
        Map<String, List<ChbCheckRuleout>> mapInvestTradeRuleout = listInvestTradeRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //增加例外情况过滤
        List<ChbCheckRuleout> listBomAstRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_BOT_AST_POS_SUB);
        Map<String, List<ChbCheckRuleout>> mapBomAstRuleout = listBomAstRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //增加例外情况过滤
        List<ChbCheckRuleout> listMonthReportRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_MONTH_REPORT);
        Map<String, List<ChbCheckRuleout>> mapMonthReportRuleout = listMonthReportRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        
        
        //报送前强制的错误检查
        resultList.addAll(beforeSubErrorCheck.checkProdDurationReg(checkdate,null,mapPrdDurationRuleout));
        resultList.addAll(beforeSubErrorCheck.checkProdIssueReg(checkdate,null,mapPrdIssueRuleout));
        resultList.addAll(beforeSubErrorCheck.checkProdEndReg(checkdate,null,mapPrdEndRuleout));
        resultList.addAll(beforeSubErrorCheck.checkInvestTradeReg(checkdate,null,mapInvestTradeRuleout));
        /**start  中债一二期报送前检查强制校验检查  add  by ***  20201010 */
        //产品申报登记
        resultList.addAll(beforeSubErrorCheck.checkProdDeclareSub(checkdate,null,mapInvestTradeRuleout));
        /**end  中债一二期报送前检查强制校验检查  add  by ***  20201010 */
        resultList.addAll(beforeSubErrorCheck.checkProdRaiseTotalReg(checkdate,null,mapInvestTradeRuleout));

        //警告逻辑检查
        resultList.addAll(checkProdEndReg(checkdate,mapPrdEndRuleout,null));
        // resultList.addAll(checkProdRaiseTotalReg(checkdate,mapTotalRaiseRuleout));
        resultList.addAll(checkInvestReg(checkdate,mapInvestTradeRuleout,null));
        resultList.addAll(checkBotAstReg(checkdate,mapBomAstRuleout,null));
        resultList.addAll(checkProdMonthReport(checkdate,mapMonthReportRuleout,null));
        
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
     * 终止登记检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */
    public List<CheckReport001Dto> checkProdEndReg(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout,List<String> keys) {
    	logger.info("终止登记检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();

        //查找募集登记检查数据
        List<ChbBeforeSubCheckDto> checklist = chbBeforeSubCheckMapper.selectProdEndRegCheck(checkdate,keys);
        for ( ChbBeforeSubCheckDto m:checklist ) {
        	//增加例外情况过滤
        	if(mapRuleout.containsKey(m.getProdid())){
        		continue;
        	}
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_产品终止登记.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_产品终止登记.getCodeName());
            chbReportCheck.setValidKey("产品代码:"+m.getProdid());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";
            if ( m.getRegcounts()<=0 ) {//不合格
            	warnInfo=warnInfo+"产品未进行交易登记!";
//                chbReportCheck.setWarnInfo("产品不存在已报送的卖出投资交易!");
//                resultList.add(chbReportCheck);
            }
            
            if ( m.getDiffrate1().compareTo(BigDecimal.valueOf(0.01))>0 ) {
            	warnInfo=warnInfo+"产品出清率过大!";
//                chbReportCheck.setWarnInfo("产品出清率过大!");
//                resultList.add(chbReportCheck);
            }
            
            if ( m.getHoldqty().abs().compareTo(BigDecimal.ZERO)>0 ) {
            	warnInfo=warnInfo+"持仓未出清!";
//                chbReportCheck.setWarnInfo("持仓未出清!");
//                resultList.add(chbReportCheck);
            }
            
            if ( m.getCashamt().compareTo(BigDecimal.ZERO)==0 ) {
            	warnInfo=warnInfo+"现金无变化!";
//                chbReportCheck.setWarnInfo("现金无变化!");
//                resultList.add(chbReportCheck);
            }
            
//            if ( m.getBankprofit1().subtract(m.getBankprofit2()).compareTo(BigDecimal.ZERO)!=0 ) {
//            	warnInfo=warnInfo+"存续期登记的银行收入与终止登记的银行收入不相等!";
////                chbReportCheck.setWarnInfo("存续期登记的银行收入与终止登记的银行收入不相等!");
////                resultList.add(chbReportCheck);
//            }

//            if ( m.getBankprofit1().subtract(m.getBankprofit2()).compareTo(BigDecimal.ZERO)!=0 ) {
//                chbReportCheck.setWarnInfo("存续期登记的银行收入与终止登记的银行收入不相等!");
//                resultList.add(chbReportCheck);
//            }
            
//            if ( m.getCustyield().subtract(m.getProdyield()).abs().compareTo(BigDecimal.valueOf(1))<0
//                ||m.getCustyield().subtract(m.getProdyield()).abs().compareTo(BigDecimal.valueOf(8))>0) {
//            	warnInfo=warnInfo+"客户实际年化收益率和产品实际年化收益率偏差过大[1%-8%]!";
////                chbReportCheck.setWarnInfo("客户实际年化收益率和产品实际年化收益率偏差过大[1%-8%]!");
////                resultList.add(chbReportCheck);
//            }

//            if ( m.getCustyield().subtract(m.getProdyield()).compareTo(new BigDecimal(0.01))<0 
//                ||m.getCustyield().subtract(m.getProdyield()).compareTo(new BigDecimal(0.08))>0) {
//            	warnInfo=warnInfo+"客户实际年化收益率和产品实际年化收益率偏差过大!";
//                chbReportCheck.setWarnInfo("客户实际年化收益率和产品实际年化收益率偏差过大!");
//                resultList.add(chbReportCheck);
//            }

//            // 产品自动持仓金额=募集份额+投资交易发生金额之和（注意交易方向）
//            // 封闭式产品的兑付客户总金额（元）=兑付客户份额+兑付客户总收益
//            if ( m.getDiffrate2().abs().compareTo( BigDecimal.valueOf(0.01))>0 ) {
//            	warnInfo=warnInfo+"产品的【银行实际实现收入（元）+兑付客户总金额（元）】与产品自动持仓金额的偏差不在[1%]之内!";
////                chbReportCheck.setWarnInfo("产品的【银行实际实现收入（元）+兑付客户总金额（元）】与产品自动持仓金额的偏差过大[1%]!");
////                resultList.add(chbReportCheck);
//            }
            
//            if ( m.getCustprofit1().compareTo(BigDecimal.ZERO)==0 ) {
//            	warnInfo=warnInfo+"兑付客户收益为0";
////                chbReportCheck.setWarnInfo("兑付客户收益为0");
////                resultList.add(chbReportCheck);
//            }
            
            if ( m.getCustyield().compareTo(m.getProdyield())==1 ) {
            	warnInfo=warnInfo+"收益率不准确";
            }
            
            if(!warnInfo.equals("")){
            	chbReportCheck.setWarnInfo(warnInfo);
            	resultList.add(chbReportCheck);
            }

        }
        logger.info("终止登记检查结束");
        return resultList;
    }

    /**
     * 
     * 募集总量登记检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */
    public List<CheckReport001Dto> checkProdRaiseTotalReg(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout) {
    	 logger.info("募集总量登记检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();

//        //查找募集登记检查数据
//        List<ChbBeforeSubCheckDto> checklist = chbBeforeSubCheckMapper.selectRaiseRegCheck(checkdate);
//        for ( ChbBeforeSubCheckDto m:checklist ) {
//        	//增加例外情况过滤
//        	if(mapRuleout.containsKey(m.getProdid())){
//        		continue;
//        	}
//            //不合格的数据加入到不合格列表
//            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
//            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
//            chbReportCheck.setRefseqno(m.getRegistseqno());
//            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_募集总量登记.getValue());
//            chbReportCheck.setRepName(DictCodeListExt2.报表名称_募集总量登记.getCodeName());
//            chbReportCheck.setValidKey("产品代码:"+m.getProdid());
//            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
//                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
//            chbReportCheck.setDataTime(checkdate);
//
//            if ( m.getRegcounts()<=0 ) {//不合格
//                chbReportCheck.setWarnInfo("产品的发行登记未报送!");
//                resultList.add(chbReportCheck);
//            }
//        }
        //设置系统参数
        String svrCusNumCheck = SvrDataUtil.getSysParam("SAVE_CUS_NUM_CHECK_01");
        //当系统参数设置为【是否做客户数检查 = 01：是】的时候，进行以下处理：
        List<CheckReport001Dto> listcheck=new ArrayList<>();
        if(StringUtil.equals("01",svrCusNumCheck)){
             listcheck=svrCustomerNumCheckService.checkProdRaiseTotalRegCusNum(checkdate);
        }
        logger.info("募集总量登记检查结束");
        if(listcheck!=null){
            resultList.addAll(listcheck);
        }
        return resultList;
        
    }
    
    /**
     * 
     * 资产交易登记检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */    
    public List<CheckReport001Dto> checkInvestReg(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout,List<String> keys) {
    	logger.info("资产交易登记检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        //资产交易检查数据
        List<ChbBeforeSubCheckDto> checklist = chbBeforeSubCheckMapper.selectInvestRegCheck(checkdate,keys);
        for ( ChbBeforeSubCheckDto m:checklist ) {
        	//增加例外情况过滤
        	if(mapRuleout.containsKey(m.getProdid())){
        		continue;
        	}
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setUniqueKey(m.getProdid()+GlobalConstant.COMMA+m.getAssetcode());
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_资产交易信息登记.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_资产交易信息登记.getCodeName());
            chbReportCheck.setValidKey("产品代码:"+m.getProdid() + (StringUtil.isNotEmpty(m.getAssetcode()) ? (" 资产代码:"+ m.getAssetcode()): ""));
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";
//            if ( m.getRegcounts()<=0 ) {//不合格
//            	warnInfo=warnInfo+"对应的资产未登记!";
////                chbReportCheck.setWarnInfo("对应的资产未报送!");
////                resultList.add(chbReportCheck);
//            }
            
            if ( m.getHoldqty().compareTo(BigDecimal.ZERO)<0 ) {
            	warnInfo=warnInfo+"存在持仓为负!";
//                chbReportCheck.setWarnInfo("持仓未出清!");
//                resultList.add(chbReportCheck);
            }
            
            if (m.getMaxprice().compareTo(BigDecimal.ZERO) !=0 && m.getMinprice().compareTo(BigDecimal.ZERO) !=0 && m.getMaxprice().compareTo(m.getMinprice().multiply(new BigDecimal(9)))>0 ) {
            	warnInfo=warnInfo+"同一个产品和同一个资产不同的交易，最高交易单价高于9倍最低交易单价!";
//                chbReportCheck.setWarnInfo("同一个产品和同一个资产不同的交易，最高交易单价高于9倍最低交易单价!");
//                resultList.add(chbReportCheck);
            }
            if(!warnInfo.equals("")){
            	chbReportCheck.setWarnInfo(warnInfo);
            	resultList.add(chbReportCheck);
            }
            
        }
        logger.info("资产交易登记检查结束");
        return resultList;
        
    }
    
    /**
     * 
     * 底层资产持仓检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */    
    public List<CheckReport001Dto> checkBotAstReg(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout,List<String> checkKeys) {
    	logger.info("底层资产持仓检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();

        //查找募集登记检查数据
        List<ChbBeforeSubCheckDto> checklist = chbBeforeSubCheckMapper.selectBotAstRegCheck(checkdate,checkKeys);
        for ( ChbBeforeSubCheckDto m:checklist ) {
        	//增加例外情况过滤
        	if(mapRuleout.containsKey(m.getAssetcode())){
        		continue;
        	}
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_资管计划底层持仓信息登记 .getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_资管计划底层持仓信息登记.getCodeName());
            chbReportCheck.setValidKey("底层资产/负债编码:"+m.getAssetcode());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);
            String warnInfo="";
//            if ( m.getRegcounts()<=0 ) {//不合格
//            	warnInfo=warnInfo+"对应的资产未报送!";
////                chbReportCheck.setWarnInfo("对应的资产未报送!");
////                resultList.add(chbReportCheck);
//            }
            
            if ( m.getDiffrate1().compareTo( BigDecimal.valueOf(0.5))<0
                || m.getDiffrate1().compareTo(BigDecimal.valueOf(2))>0 ) {
            	warnInfo=warnInfo+"资管计划底层金额偏差过大!";
//                chbReportCheck.setWarnInfo("资管计划底层金额偏差过大!");
//                resultList.add(chbReportCheck);
            }
            if(m.getVdate()!=null&&m.getMdate()!=null){
            	if ( m.getPosdate().compareTo(m.getVdate())<0 
            			|| m.getPosdate().compareTo(m.getMdate())>0 ) {
            		warnInfo=warnInfo+"资产未在期限内!";
//                chbReportCheck.setWarnInfo("资产未在期限内!");
            	}
            }
            if(!warnInfo.equals("")){
            	chbReportCheck.setWarnInfo(warnInfo);
            	resultList.add(chbReportCheck);
            }
            
        }
        logger.info("底层资产持仓检查结束");
        return resultList;
        
    }
    
    /**
     * 
     * 银行月度报表检查
     * @param checkdate
     * @return
     * @see
     * @since 1.0
     */    
    public List<CheckReport001Dto> checkProdMonthReport(Date checkdate,Map<String, List<ChbCheckRuleout>> mapRuleout,List<String> keys) {
    	 logger.info("银行月度报表检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();

        //查找募集登记检查数据
        //将日期改到上个月月末
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkdate);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date lastday = calendar.getTime();
        List<ChbBeforeSubCheckDto> checklist = chbBeforeSubCheckMapper.selectProdMonthReportCheck(lastday,keys);
        for ( ChbBeforeSubCheckDto m:checklist ) {
            //不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_银行月度统计表 .getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_银行月度统计表.getCodeName());
            chbReportCheck.setValidKey("月报期数:"+m.getTermid());
            chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);

            if ( m.getBankprofit1().compareTo(m.getBankprofit2()) !=0 ) {//不合格
                chbReportCheck.setWarnInfo("月度统计表与存续期登记的银行实现收益的数据不一致!");
                resultList.add(chbReportCheck);
            }                           
        }
        logger.info("银行月度报表检查结束");
        return resultList;
        
    }

    /**
     * 理财子公司月度报表检查
     *
     * @param checkdate 校验日期
     * @param mapRuleout 校验数据
     * @return 校验结果
     * @see
     * @since 1.0
     */
    public List<CheckReport001Dto> checkProdSubsidiaMonthReport(Date checkdate,
                                                                Map<String, List<ChbCheckRuleout>> mapRuleout) {
        logger.info("理财子公司月度报表检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        // 查找募集登记检查数据
        // 将日期改到上个月月末
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkdate);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date lastday = calendar.getTime();
        List<ChbBeforeSubCheckDto> checklist = chbBeforeSubCheckMapper.selectProdMonthReportCheck(lastday,null);
        for (ChbBeforeSubCheckDto m : checklist) {
            // 不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_理财子公司月度统计表.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_理财子公司月度统计表.getCodeName());
            chbReportCheck.setValidKey("月报期数:" + m.getTermid());
            chbReportCheck.setCheckResult(
                    SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);

            if (m.getBankprofit1().compareTo(m.getBankprofit2()) != 0) {// 不合格
                chbReportCheck.setWarnInfo("理财子公司月度统计表与存续期登记的银行实现收益的数据不一致!");
                resultList.add(chbReportCheck);
            }
        }
        logger.info("理财子公司月度报表检查结束");
        return resultList;
    }



    /**
     * 理财产品转型情况月度报表检查
     *
     * @param checkdate 校验日期
     * @param mapRuleout 校验数据
     * @return 校验结果
     * @see
     * @since 1.0
     */
    public List<CheckReport001Dto> checkProdTransMonthReport(Date checkdate,
                                                                Map<String, List<ChbCheckRuleout>> mapRuleout, List<String> checkKeys) {
        logger.info("理财产品转型情况月度报表检查开始");
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        // 查找募集登记检查数据
        // 将日期改到上个月月末
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkdate);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date lastday = calendar.getTime();
        List<ChbBeforeSubCheckDto> checklist = chbBeforeSubCheckMapper.selectProdMonthReportCheck(lastday, checkKeys);
        for (ChbBeforeSubCheckDto m : checklist) {
            // 不合格的数据加入到不合格列表
            CheckReport001Dto chbReportCheck = new CheckReport001Dto();
            chbReportCheck.setCheckSeqno(IDGenerator.generate(32));
            chbReportCheck.setRefseqno(m.getRegistseqno());
            chbReportCheck.setRepCode(DictCodeListExt2.报表名称_理财产品转型情况月度统计表.getValue());
            chbReportCheck.setRepName(DictCodeListExt2.报表名称_理财产品转型情况月度统计表.getCodeName());
            chbReportCheck.setValidKey("月报期数:" + m.getTermid());
            chbReportCheck.setCheckResult(
                    SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, DictCodeListExt2.检查结果_存在预警信息项.getCodeName()));
            chbReportCheck.setDataTime(checkdate);

            if (m.getBankprofit1().compareTo(m.getBankprofit2()) != 0) {// 不合格
                chbReportCheck.setWarnInfo("理财产品转型情况月度统计表与存续期登记的银行实现收益的数据不一致!");
                resultList.add(chbReportCheck);
            }
        }
        logger.info("理财产品转型情况月度报表检查结束");
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
        // 设置批处理bean
        setBatchBeanName(BatchBeanName.数据检查);
        // 批处理开始
        logInfoBatchStart();
        checkBeforeSub(executeDate);
        // 批处理结束
        logInfoBatchEnd();
    }


}
