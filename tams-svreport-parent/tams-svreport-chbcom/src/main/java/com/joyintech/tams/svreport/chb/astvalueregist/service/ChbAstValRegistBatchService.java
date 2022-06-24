package com.joyintech.tams.svreport.chb.astvalueregist.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService;
import com.joyintech.tams.common.base.entity.ChbAstValueSub;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.svreport.chb.astvalueregist.dao.ChbAstValCBMapper;
import com.joyintech.tams.svreport.chb.datacheck.beforeinsert.service.ChbCommonCheckService;
import com.joyintech.tams.svreport.interf.chb.assetbasis.service.SvrAssetBasisInterf;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:资产估值登记批处理
 * @since 1.7.0
 * @version 1.7.0
 * @作者：weiyuqiang
 * @创建时间：2019年5月30日
 * @修改记录：
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbAstValRegistBatchService extends SvrCBBaseService {

	@Autowired
	private ChbAstValCBMapper chbAstValCBMapper;
	
	@Autowired
	private SvrAssetBasisInterf svrAssetBasisInterf;

    /**
     * 入库前校验用Service
     */
    @Autowired
    private ChbCommonCheckService chbCommonCheckService;
	

	public void createRegist(Date batchTime,String InstitutionCode){
		// TODO Auto-generated method stub
		// 设置批处理bean
		setBatchBeanName(BatchBeanName.资产估值信息登记报送);

		logInfoBatchStart();
		List<String> assets = new ArrayList<String>();
		// 根据接口估值表生成估值信息
		createValueInfo(batchTime,assets,InstitutionCode);
		// 根据估值信息表生成估值登记
		createRegistAction(batchTime,assets,InstitutionCode);
		// }

		logInfoBatchEnd();
	}

	// 根据接口估值表生成估值信息
	public void createValueInfo(Date batchTime,List<String> assets,String InstitutionCode) {
		String isBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_CHL_BTM);
		List<Date> isUpdate = chbAstValCBMapper.queryUpdateDate(DateUtil.format(batchTime),isBtm);
		if (CollectionUtils.isEmpty(isUpdate))
			return;
		for (Date date : isUpdate) {
			svrAssetBasisInterf.genAssetValueInfo(date,InstitutionCode);
		}
	}
	
	// 根据估值信息表生成估值登记
	public void createRegistAction(Date batchTime,List<String> assets,String InstitutionCode) {
		String isBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_CHL_BTM);
		List<Date> isUpdate = chbAstValCBMapper.queryUpdateDate(DateUtil.format(batchTime),isBtm);
		if (CollectionUtils.isEmpty(isUpdate))
			return;
		for (Date posDate : isUpdate) {

			// 得到底层资产持仓信息
			List<Date> dateList = null;
			Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();

			// 删除数据状态为“新建”，报送状态为“未报送”的登记
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("valDate", DateUtil.format(posDate));
			para.put("sendStatus", "01");
			para.put("InstitutionCode", InstitutionCode);
			chbAstValCBMapper.deleteRegist(para);
			Date chbSubmitDeadline = null;
			Date submitDeadline = null;

			// 数据状态为“修改、删除”，报送状态为“未报送”的登记
			 List<ChbAstValueSub> updateOrDelList =
			 chbAstValCBMapper.queryUpdateOrDelList(DateUtil.format(posDate),InstitutionCode);
			 // add by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 start
			 updateOrDelList = chbCommonCheckService.checkData(updateOrDelList, ChbCheckTableInfo.资产估值信息登记表SUB);
	         // add by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 end
			 for(ChbAstValueSub sub:updateOrDelList){
				 sub.setRegistSeqno(IDGenerator.generate(36));
				 // 报送流水号
				 sub.setSumitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
				 //sub.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
				 sub.setIssuePartyId(CommonUtil.selectBankCode());
//				 sub.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
				 sub.setCreateUser(CommonUtil.getUserName());
				 sub.setUpdateUser(CommonUtil.getUserName());
				 //数据日期
				 sub.setStatusDate(batchTime);

				 dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资产估值信息登记.getValue(),
						 sub.getStatus(), dateMap, posDate);
				 if (CollectionUtils.isNotEmpty(dateList)) {
					 sub.setChbSubmitDeadline(dateList.get(0));
					 sub.setSubmitDeadline(dateList.get(1));
				 }
				 sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
				 chbAstValCBMapper.insertSub(sub);
			 }

			
			//生成数据状态为“新建”，报送状态为“未报送”的登记
//			chbAstValCBMapper.queryGenerateListAndInsert(DateUtil.format(posDate), batchTime, chbSubmitDeadline,
//					submitDeadline,InstitutionCode);
	        // modify by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 start
			List<ChbAstValueSub> insertDataList = chbAstValCBMapper.queryInsertGenerateList(DateUtil.format(posDate), batchTime, chbSubmitDeadline,
                    submitDeadline,InstitutionCode);
	        insertDataList = chbCommonCheckService.checkData(insertDataList, ChbCheckTableInfo.资产估值信息登记表SUB);
//			chbAstValCBMapper.queryGenerateListAndInsert(DateUtil.format(posDate), batchTime, chbSubmitDeadline,
//					submitDeadline);
            for(ChbAstValueSub sub : insertDataList) {
				// 报送流水号
				sub.setSumitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
				dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资产估值信息登记.getValue(),
						sub.getStatus(), dateMap, posDate);
				if (CollectionUtils.isNotEmpty(dateList)) {
					sub.setChbSubmitDeadline(dateList.get(0));
					sub.setSubmitDeadline(dateList.get(1));
				}
                chbAstValCBMapper.insertSub(sub);
            }
	        // modify by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 end
//			chbAstValCBMapper.queryGenerateListAndInsert(DateUtil.format(posDate), batchTime, chbSubmitDeadline,
//					submitDeadline,InstitutionCode);
		}
	}

	/**
	 * 功能:数据变更接口统一调度入口
	 * 
	 * @since 1.7.0
	 * @version 1.7.0
	 * @author：weiyuqiang 2019年5月9日
	 * @param executeDate
	 * @param prodList
	 * @param assetList
	 * @throws BusinessException
	 * @see com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService#execute(java.util.Date,
	 *      java.util.List, java.util.List)
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