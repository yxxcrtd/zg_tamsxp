package com.joyintech.tams.svreport.chb.botastpositionregist.service;

import java.util.*;
import java.util.stream.Collectors;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.report.interf.common.constant.InterfMappingConstants;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService;
import com.joyintech.tams.common.base.dao.ChbBotAstPositionSubMapper;
import com.joyintech.tams.common.base.entity.ChbBotAstPositionSub;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.chb.util.ToolsUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.svreport.chb.botastpositionregist.dao.ChbBotAstPosCBMapper;
import com.joyintech.tams.svreport.chb.datacheck.beforeinsert.service.ChbCommonCheckService;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:底层持仓登记批处理
 * @since 1.7.0
 * @version 1.7.0
 * @作者：weiyuqiang
 * @创建时间：2019年5月30日
 * @修改记录：
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbBotAstPosRegistBatchService  extends SvrCBBaseService{

    @Autowired
    private ChbBotAstPosCBMapper chbBotAstPosCBMapper;
    
    @Autowired
    private ChbBotAstPositionSubMapper chbBotAstPositionSubMapper;

    /**
     * 入库前校验用Service
     */
    @Autowired
    private ChbCommonCheckService chbCommonCheckService;
	/**
	 * 映射配置mapper
	 */
	@Autowired
	private ChbTypeMappingExMapper chbTypeMappingExMapper;
    
	public void createRegist(Date batchTime,String InstitutionCode){
        // 设置批处理bean
        setBatchBeanName(BatchBeanName.底层持仓信息登记报送);

        logInfoBatchStart();

        createRegistAction(batchTime,InstitutionCode);
		logInfoBatchEnd();
	}
	
	private void createRegistAction(Date batchTime,String InstitutionCode){
		List<Date> isUpdate = chbBotAstPosCBMapper.queryUpdateDate(DateUtil.format(batchTime));
		if(CollectionUtils.isEmpty(isUpdate))
			return;
		//取得系统配置的科目信息
		List<ChbTypeMappingEx>  subjectNoMappingList = chbTypeMappingExMapper
				.selectByMappingcode(InterfMappingConstants.BOTTON_SUBJECT_INVEST_TYPE_NAME);
		List<String> subjectNoList = new ArrayList<String>();
		for(ChbTypeMappingEx mapping: subjectNoMappingList){
			// 获取源属性1：科目号
			subjectNoList.add(mapping.getSrcfield1());
		}
		for(Date posDate : isUpdate){
			//每周日或每月最后一天生成登记
			if(!isLastDay(posDate)) continue;

			//得到底层资产持仓信息
			String isChbBtm = "01";//通道是否穿透，默认“是”
			isChbBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_CHL_BTM);
	    	Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();

			//删除数据
			Map<String,Object> para = new HashMap<String,Object>();
			para.put("posDate", DateUtil.format(posDate));
			para.put("sendStatus", "01");
			para.put("InstitutionCode",InstitutionCode);
			chbBotAstPosCBMapper.delete(para);
			List<ChbBotAstPositionSub> updateOrDelList = chbBotAstPosCBMapper.queryUpdateOrDelList(DateUtil.format(posDate), isChbBtm,InstitutionCode);
			List<ChbBotAstPositionSub> updateList = updateOrDelList.stream().filter(x -> StringUtil.equals(x.getStatus(), "02")).collect(Collectors.toList());
			List<ChbBotAstPositionSub> delList = updateOrDelList.stream().filter(x -> StringUtil.equals(x.getStatus(), "03")).collect(Collectors.toList());
            // add by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 start
			updateList = chbCommonCheckService.checkData(updateList, ChbCheckTableInfo.底层资产持仓信息登记表SUB);
            // add by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 end
			updateList.addAll(delList);
			for (ChbBotAstPositionSub sub : updateList) {
				sub.setRegistSeqno(IDGenerator.generate(36));
				// 报送流水号
				sub.setSumitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
                sub.setCreateUser(CommonUtil.getUserName());
                sub.setUpdateUser(CommonUtil.getUserName());
				sub.setUpdateTime(new Date());
				sub.setCreateTime(new Date());
				// 数据日期
				sub.setStatusDate(batchTime);

				List<Date>  dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资管计划底层持仓信息登记.getValue(),
						sub.getStatus(), dateMap,posDate);
				if (CollectionUtils.isNotEmpty(dateList)) {
					sub.setChbSubmitDeadline(dateList.get(0));
					sub.setSubmitDeadline(dateList.get(1));
				}

				sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
				chbBotAstPositionSubMapper.insert(sub);
			}

            
            //生成数据状态为“新建”，报送状态为“未报送”的登记
            // modify by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 start
            List<ChbBotAstPositionSub> insertDataList = chbBotAstPosCBMapper.queryInsertGenerateList(DateUtil.format(posDate), isChbBtm, ToolsUtil.getCurrentTime(batchTime),
                    null, null,InstitutionCode,subjectNoList);

			for (ChbBotAstPositionSub sub:insertDataList) {
				List<Date>  dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资管计划底层持仓信息登记.getValue(),
						sub.getStatus(), dateMap,posDate);
				if (CollectionUtils.isNotEmpty(dateList)) {
					sub.setChbSubmitDeadline(dateList.get(0));
					sub.setSubmitDeadline(dateList.get(1));
				}
			}


            insertDataList = chbCommonCheckService.checkData(insertDataList, ChbCheckTableInfo.底层资产持仓信息登记表SUB);

            for(ChbBotAstPositionSub sub : insertDataList) {
				// 报送流水号
				sub.setSumitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
                chbBotAstPositionSubMapper.insert(sub);
            }
            // modify by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 end
			
			/*chbBotAstPosCBMapper.queryGenerateListAndInsert(DateUtil.format(posDate), isChbBtm, ToolsUtil.getCurrentTime(batchTime),
					chbSubmitDeadline, submitDeadline,InstitutionCode);*/
		}
	}
    
	/*
	 * 判断是否为周末或者月末
	 */
	public boolean isLastDay(Date date) {
		boolean rs = false;
 	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));//加一天
	    if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {//1号
	    	rs =  true;
	    }
	    if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {//周1
	    	rs =  true;
	    }
	    return rs;
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
        	createRegist(executeDate,null);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
	
}