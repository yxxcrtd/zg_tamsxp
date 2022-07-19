package com.joyintech.tams.svreport.chb.investtradereg.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.util.SvrMaptoEntityUtil;
import com.joyintech.tams.svreport.chb.botastpositionregist.dto.ChbBotAstPositionRegist001Dto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.service.SvrCommonExportService;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubCheck;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubErrorCheck;
import com.joyintech.tams.svreport.chb.investtradereg.dao.ChbInvestTradeSubHisMapper;
import com.joyintech.tams.svreport.chb.investtradereg.dao.InvestTradeRegist001Mapper;
import com.joyintech.tams.svreport.chb.investtradereg.dto.ChbInvestTradeSubHisDto;
import com.joyintech.tams.svreport.chb.investtradereg.dto.InvestTradeRegist001Dto;
import com.joyintech.tams.svreport.chb.prodpersonissue.dto.ProdPersonIssue001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;

@Service(value="svrInvestTradeRegist001Service")
@Transactional(rollbackFor = Exception.class)
public class InvestTradeRegist001Service extends SvrCommonExportService<InvestTradeRegist001Dto> {

	@Autowired
	private InvestTradeRegist001Mapper investTradeRegist001Mapper;
    @Autowired
    private ChbInvestTradeSubHisMapper chbInvestTradeSubHisMapper;
    @Autowired
    private CheckReport001Mapper checkReport001Mapper;
    @Autowired
    private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
    @Autowired
    private BeforeSubErrorCheck beforeSubErrorCheck;
    @Autowired
    private BeforeSubCheck beforeSubCheck;
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 长sql最大一次插入或者更新条数
     */
    @Value("${application.reportInsertLongCount}")
    public  int reportInsertLongCount;
    
    /**
     * sql最大一次最大删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public  int reportSelectOrDeleteCount;
    
    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportInsertCount}")
    public  int reportInsertCount;
    
    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportSelectOrDeleteCountForShenFen}")
    public  int reportSelectOrDeleteCountForShenFen;

    /**
     * sqlldr用数据库连接
     */
    @Value("${spring.datasource.reportSqlldrDatabaseName}")
    private String reportSqlldrDatabaseName;
    
    @Autowired
    private SqlSessionTemplate template;
	@Autowired
	private SvrMaptoEntityUtil svrMaptoEntityUtil;
	private String tableName = "CHB_INVEST_TRADE_SUB";

	/**
	 * 中债基础数据mapper
	 */
	@Autowired
	private BaseMapper baseMapper;
    
	public Page<InvestTradeRegist001Dto> selectForPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		
		//add by leipeng 20200628
		if(CommonUtil.isBAmodel()){
			pageParam.put("model_type", "01");
		}
		
        if (pageParam.get(GlobalConstant.PARAM_PAGE) != null
                && pageParam.get(GlobalConstant.PARAM_LENGTH) != null){
    		int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
            int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
    		Page<InvestTradeRegist001Dto> page = queryForPage(
    				pagenum,legth,()->investTradeRegist001Mapper.selectAllData(pageParam));
    		dealDataList(page.getRoot(),false);
    		return page;
        } else {
            Page<InvestTradeRegist001Dto> page = queryForPage(InvestTradeRegist001Mapper.class,"selectAllData",pageParam);
            dealDataList(page.getRoot(),false);
            return page;
            
        }
	}

	private void dealSearchParam(Map<String, Object> pageParam) {
		// 选择行数据时
		if (pageParam.containsKey("keyList")) {
			String keyList = pageParam.get("keyList").toString();
			try {
				List<InvestTradeRegist001Dto> investList = JsonUtils.json2List(keyList,
						InvestTradeRegist001Dto.class);
				pageParam.put("investList", investList);
			} catch (IOException e) {
	            logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 将产品代码当做多选下拉框处理
			String prodCode = (String) pageParam.get("prodCode");
			if (StringUtil.isNotBlank(prodCode)) {
				pageParam.put("prodIdList", StringUtil.splitToList(GlobalConstant.COMMA, prodCode));
			}
			//产品登记编码
			String prodRegCode = (String) pageParam.get("prodRegCode");
			if (StringUtil.isNotBlank(prodRegCode)) {
				pageParam.put("prodRegCodeList", StringUtil.splitToList(GlobalConstant.COMMA, prodRegCode));
			}
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 将报送状态当做多选下拉框处理
			if (StringUtil.isNotBlank(sendStatus)) {
				pageParam.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
			}
			// 行内交易编码
			String tradeCode = (String) pageParam.get("tradeCode");
			// 将行内交易编码当做多选下拉框处理
			if (StringUtil.isNotBlank(tradeCode)) {
				pageParam.put("tradeCodeList", StringUtil.splitToList(GlobalConstant.COMMA, tradeCode));
			}
			// 资产代码
			String assetCode = (String) pageParam.get("assetCode");
			// 将资产代码当做多选下拉框处理
			if (StringUtil.isNotBlank(assetCode)) {
				pageParam.put("assetCodeList", StringUtil.splitToList(GlobalConstant.COMMA, assetCode));
			}
			// 资产已登记
			String assetRegist = (String) pageParam.get("assetRegist");
			// 将资产已登记当做多选下拉框处理
			if (StringUtil.isNotBlank(assetRegist)) {
				pageParam.put("assetRegistList", StringUtil.splitToList(GlobalConstant.COMMA, assetRegist));
			}
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");
			// 将反馈状态当做多选下拉框处理
			if (StringUtil.isNotBlank(submitFeedbackStatus)) {
				pageParam.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
			}
			// 数据状态
			String status = (String) pageParam.get("status");
			// 将数据状态当做多选下拉框处理
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
		}
	}

	public List<InvestTradeRegist001Dto> selectForExport(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		@SuppressWarnings("unchecked")
		List<InvestTradeRegist001Dto> dataList =  (List<InvestTradeRegist001Dto>) pageParam.get("investList");

		List<InvestTradeRegist001Dto> dtoList = new ArrayList<InvestTradeRegist001Dto>();
		this.selectAllDataBatch(dataList, dtoList);
		return dtoList;
	}

	private void selectAllDataBatch(List<InvestTradeRegist001Dto> dataList, List<InvestTradeRegist001Dto> dtoList) {
		if (StringUtil.isEmptyList(dataList)) {
            return;
        }
        int maxDataCountOneTime = reportSelectOrDeleteCount;
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
        	dtoList.addAll(investTradeRegist001Mapper.selectAllDataByList(dataList));
            return;
        }

        List<InvestTradeRegist001Dto> newDataList = new ArrayList<InvestTradeRegist001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
        	dtoList.addAll(investTradeRegist001Mapper.selectAllDataByList(newDataList.subList(0, maxDataCountOneTime)));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	dtoList.addAll(investTradeRegist001Mapper.selectAllDataByList(newDataList));
        }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		Map<String, Object> pageParam = exportParam.getSelectParam();
		String pageIds = (String) pageParam.get("pageIds");
		if (StringUtil.isNotEmpty(pageIds)) {
			List<String> pageNames = Arrays.asList(pageIds.split(GlobalConstant.COMMA));
			pageParam.replace("pageIds", pageIds, pageNames);
		} else {
			pageParam.replace("pageIds", pageIds, null);
		}

		// 设置参数
		dealSearchParam(pageParam);
		//无查询参数的情况
		exportParam.config("/com/joyintech/tams/svreport/chb/investtradereg/conf/export_conf.xml")
		.select(() -> investTradeRegist001Mapper.selectAllData(pageParam));

	}
    
    public List<InvestTradeRegist001Dto> selectAllData(Map<String, Object> pageParam){
        return investTradeRegist001Mapper.selectAllData(pageParam);
    }
    @Override
    public com.joyintech.framework.grid.Page<InvestTradeRegist001Dto> selectPageData(Map<String, Object> pageParam) {
        List<InvestTradeRegist001Dto> data =  restTemplate.postForList(
                    SvrServiceApplication.CHB_SERVICE_NAME + "/InvestTradeRegist/selectAllData",pageParam,
                    InvestTradeRegist001Dto.class);
        com.joyintech.framework.grid.Page<InvestTradeRegist001Dto> page = new com.joyintech.framework.grid.Page<InvestTradeRegist001Dto>();
        page.setRoot(data);
		if(!CollectionUtils.isEmpty(page.getRoot())){
			for(InvestTradeRegist001Dto dto : page.getRoot()){
				dto.setBelongInstitutionCode(null);
			}
		}
//        page.setTotal(data.size());
		int pageSize = StringUtil.integerValueOf(pageParam.get(GlobalConstant.PARAM_LENGTH).toString(), StringUtil.integerValueOf(CacheUtil.getSysParam(GlobalConstant.EXPORT_EXCEL_ROW_COUNT),50000));
		if(data!=null && data.size() > 0){
			page.setTotal(data.size()/pageSize+1);
		}else{
			page.setTotal(0);
		}

        return  page;
    }
    /**
	/**
     * 配置项中非空处理
     * @param Count
     * @param flag
     * @return
     */
    public int parsToInt(String count,String flag){
   	 if(StringUtil.isNotEmpty(count)&&Integer.parseInt(count)<=0){
   		 if("1".equals(flag)){
   	         return 20;
   		 }
   		 else if("2".equals(flag)){
	         return 500;
   		 }
   		 else {
	         return 100;
   		 }
      }else{
        return Integer.parseInt(count);
      }
   }
    
    /**
     * 手动处理
     * @param map 
     */
	@SuppressWarnings("unchecked")
	public void updateSumStaus(Map<String, Object> map) {
		dealSearchParam(map);
		List<InvestTradeRegist001Dto> investTradeListOrg = new ArrayList<InvestTradeRegist001Dto>();
		//选择的手动处理
		investTradeListOrg = (List<InvestTradeRegist001Dto>) map.get("investList");
        if (StringUtil.isEmptyList(investTradeListOrg)) {
        	//未选中数据
            investTradeListOrg = investTradeRegist001Mapper.selectAllData(map);
		}
        List<InvestTradeRegist001Dto> investTradeList = new ArrayList<InvestTradeRegist001Dto>();
        for(InvestTradeRegist001Dto m : investTradeListOrg){
			// modify by zhangliang 20210309 手工处理与前台逻辑一致：只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理 start
//            // 手工处理只处理未报送的
//            if (DictCodeListExt2.报送状态_未报送.getValue().equals(m.getSendStatus())
//                    ||DictCodeListExt2.报送状态_未报送.getValue().equals(m.getSendStatusName())){
//                investTradeList.add(m);
//            }
			investTradeList.add(m);
			// modify by zhangliang 20210309 手工处理与前台逻辑一致：只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理 end
        }
        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> registSeqList = new ArrayList<String>();
//        List<String> tradeList = new ArrayList<>();
		for(InvestTradeRegist001Dto m : investTradeList){
			registSeqList.add(m.getRegistSeqno());
//			tradeList.add(m.getTradeCode());
		}
//        // 处理履历表数据和已报送的数据
//        dealRegHis(tradeList,true);
        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新反馈状态
        if (!StringUtil.isEmptyList(registSeqList)) {
            // 分批处理
            int maxDataCountOneTime = reportInsertCount;
            int dataCount = registSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
            	investTradeRegist001Mapper.updateSubmitStatusToOk(registSeqList,DictCodeListExt2.报送状态_手工处理.getValue());
            }
            else {
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(registSeqList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                	investTradeRegist001Mapper.updateSubmitStatusToOk(newDataList.subList(0, maxDataCountOneTime),DictCodeListExt2.报送状态_手工处理.getValue());
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                	investTradeRegist001Mapper.updateSubmitStatusToOk(newDataList,DictCodeListExt2.报送状态_手工处理.getValue());
                }
            }
        }
		this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), investTradeList, false,true);
	}
	/**
	 * @Description 分批次执行updateInterfSubmitAndSeq
     * @param sendStatus 报送状态
     * @param assetInfoList 资产要素list
     * @param isSendAllData 是否全量报送
     * @param isUpdateHis 是否更新履历表
	 * @return void
	 * @version 3.0 2019年1月24日
	 */
	private void updateInterfSubmitAndSeq(String sendStatus, List<InvestTradeRegist001Dto> investTradeList,
	        boolean isSendAllData,boolean isUpdateHis) {
		if (StringUtil.isEmptyList(investTradeList)) {
			return;
		}
		int maxDataCountOneTime = reportInsertCount;
		int dataCount = investTradeList.size();
		if (dataCount <= maxDataCountOneTime) {
            if (isUpdateHis){
                //已报送数据进留痕表
    		    investTradeRegist001Mapper.insertReportHis(investTradeList);
                //删除正式表已报送数据
                investTradeRegist001Mapper.deleteLastSubmitRegist(investTradeList);
            }
			investTradeRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, investTradeList);
			return;
		}

		// 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
		if (isSendAllData) {
		    logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表start===");
            if (isUpdateHis){
                //已报送数据进留痕表
                investTradeRegist001Mapper.insertReportHis(investTradeList);
                //删除正式表已报送数据
                investTradeRegist001Mapper.deleteLastSubmitRegist(investTradeList);
            }
		    investTradeRegist001Mapper.updateAllInterfSubmitAndSeqBatch(sendStatus);
		    logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表end===");
		    return;
		}

		List<InvestTradeRegist001Dto> newDataList = new ArrayList<InvestTradeRegist001Dto>();
		newDataList.addAll(investTradeList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			logger.info("单次更新登记表start===");
            if (isUpdateHis){
                //已报送数据进留痕表
                investTradeRegist001Mapper.insertReportHis(newDataList.subList(0, maxDataCountOneTime));
                //删除正式表已报送数据
                investTradeRegist001Mapper.deleteLastSubmitRegist(newDataList.subList(0, maxDataCountOneTime));
            }
			investTradeRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,
                newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("单次更新登记表end===");
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			logger.info("单次更新登记表start===");
            if (isUpdateHis){
                //已报送数据进留痕表
                investTradeRegist001Mapper.insertReportHis(newDataList);
                //删除正式表已报送数据
                investTradeRegist001Mapper.deleteLastSubmitRegist(newDataList);
            }
			investTradeRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
			logger.info("单次更新登记表end===");
		}
	}

	/**
	 * @Description 取消手工处理
	 * @param map
     * @return boolean 是否同时存在已登记和未登记的数据 
	 * @version 3.0 2019年1月24日
	 */
	@SuppressWarnings("unchecked")
	public boolean cancelMannul(Map<String, Object> map) {
		dealSearchParam(map);
		List<InvestTradeRegist001Dto> investTradeList = new ArrayList<InvestTradeRegist001Dto>();
		// 所有未登记的数据
        // 用于判断是否同时存在未报送和已报送的数据
		Map<String, Object> querymap = new HashMap<>();
		querymap.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, DictCodeListExt2.报送状态_未报送.getValue()));
        List<InvestTradeRegist001Dto> investTradeNotRegList = investTradeRegist001Mapper.selectAllData(querymap);
        Map<String,String> investTradeNotRegMap = investTradeNotRegList.stream().collect(Collectors.toMap(InvestTradeRegist001Dto::getTradeCode,
                InvestTradeRegist001Dto::getTradeCode,(key1,key2)->key2));
		//选择的手动处理
		investTradeList = (List<InvestTradeRegist001Dto>) map.get("investList");
        if (StringUtil.isEmptyList(investTradeList)) {
        	//未选中数据
        	map.put("sendStatus", "03");
        	investTradeList = investTradeRegist001Mapper.selectAllData(map);
		}
		// 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> registSeqList = new ArrayList<String>();
//        List<String> tradeList = new ArrayList<>();
		for(InvestTradeRegist001Dto m : investTradeList){
			registSeqList.add(m.getRegistSeqno());
//            tradeList.add(m.getTradeCode());
            // 如果没有该产品的数据则插入如果有则判断是否同时存在未报送和已报送
            // 只要有未报送的登记数据就不允许取消手工处理
            if (DictCodeListExt2.报送状态_手工处理.getValue().equals(m.getSendStatus())
                    && investTradeNotRegMap.get(m.getTradeCode()) != null){
                return true;
            }
		}
//        // 处理履历表数据和已报送的数据
//        dealRegHis(tradeList,false);
		if (!StringUtil.isEmptyList(registSeqList)) {
            // 分批处理
            int maxDataCountOneTime = reportInsertCount;
            int dataCount = registSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
                // 将履历表中履历号最大的数据挪回登记表
                investTradeRegist001Mapper.insertReportDataFromHis(investTradeList);
                // 删除履历表中移除数据
                investTradeRegist001Mapper.deleteLastSubmitHis(investTradeList);
            	investTradeRegist001Mapper.updateInvestTradeRegist(registSeqList);
            }
            else {
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(registSeqList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    // 将履历表中履历号最大的数据挪回登记表
                    investTradeRegist001Mapper.insertReportDataFromHis(investTradeList.subList(0, maxDataCountOneTime));
                    // 删除履历表中移除数据
                    investTradeRegist001Mapper.deleteLastSubmitHis(investTradeList.subList(0, maxDataCountOneTime));
                	investTradeRegist001Mapper.updateInvestTradeRegist(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                    investTradeList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    // 将履历表中履历号最大的数据挪回登记表
                    investTradeRegist001Mapper.insertReportDataFromHis(investTradeList);
                    // 删除履历表中移除数据
                    investTradeRegist001Mapper.deleteLastSubmitHis(investTradeList);
                	investTradeRegist001Mapper.updateInvestTradeRegist(newDataList);
                }
            }
        }
		return false;
	}
	/**
	 * 处理查询结果 <功能详细描述>
	 *
	 * @param list
	 *            待处理数据
	 * @param isExport
	 *            是否是导出
	 * @see
	 * @since 1.0
	 */
	public void dealDataList(List<InvestTradeRegist001Dto> list, boolean isExport) {
		list.forEach(item -> {
            item.setStatusName(SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态,item.getStatus()));
//            item.setStatus(item.getStatusName());
            item.setSendStatusName(SvrDataUtil.getNameFromDataDict(DictCode.报送状态,item.getSendStatus()));
//            item.setSendStatus(item.getSendStatusName());
            item.setSubmitFeedbackStatusName(SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态,item.getSubmitFeedbackStatus()));
            item.setSubmitFeedbackStatus(item.getSubmitFeedbackStatusName());
			if (StringUtil.isNotEmpty(item.getCcy())){
			    item.setCcyName(SvrDataUtil.getNameFromDataDict(DictCode.币种_中债, item.getCcy()));
			}
			if (StringUtil.isNotEmpty(item.getCurAssetType())){
				item.setCurAssetTypeName(item.getCurAssetType()+" "+SvrDataUtil.getNameFromDataDict(DictCode.资金流动类型,item.getCurAssetType()));
				item.setCurAssetType(item.getCurAssetTypeName());
			}
			if (item.getEndRate() != null){
				DecimalFormat df = new DecimalFormat("0.00000");
				// 到期收益率硬性展示5位小数
//				item.setEndRate(item.getEndRate().multiply(new BigDecimal(100)));
				item.setEndRateString(df.format(item.getEndRate()));
			}
			if(StringUtil.isNotEmpty(item.getAssetMethod())) {
				item.setAssetMethod(item.getAssetMethod()+" "+SvrDataUtil.getNameFromDataDict(DictCode.资产计量方式,item.getAssetMethod()));
			}
			if(StringUtil.isNotEmpty(item.getRivalPartyType())) {
				item.setRivalPartyType(item.getRivalPartyType()+" "+SvrDataUtil.getNameFromDataDict(DictCode.对手方类型,item.getRivalPartyType()));
			}
			/**0722升级* **/
			if(StringUtil.isNotEmpty(item.getRelatedTradeStatus())) {
				item.setRelatedTradeStatus(item.getRelatedTradeStatus()+" "+SvrDataUtil.getNameFromDataDict(DictCode.关联交易情况,item.getRelatedTradeStatus()));
			}
			/**0722升级* **/
			//银行模式时,资产数据取当前机构的产品登记编码
			if(!CommonUtil.isBAmodel()){
				item.setRegistBankCode(CommonUtil.selectBankCode());
			}
		});
	}    
	
	/**
	 * 功能:导出前格式化字段
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年1月25日
	 */
	@Override
    protected void beforeCreateExcel(List<InvestTradeRegist001Dto> contents) {
        // 格式化数据字典值
    	dealDataList(contents, false);
    }

    /**
     * 查询行内交易编码
     * @param pageParam 参数
     * @return 结果list
     * @see
     * @since 1.6.0
     */
    public List<SelectOptionDto> queryTradeNoCodeList(Map<String, Object> pageParam) {
        String orgCode = (String)pageParam.get("orgCode");
        List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
        if (StringUtil.isBlank(orgCode)) {
            orgCodeList = CommonUtil.getOrgCodeListForSearch();
        }
        pageParam.put("orgCodeList", orgCodeList);
        List<SelectOptionDto> dtoList = new ArrayList<SelectOptionDto>();
        //在所选的机构list不为空或者是用户为管理员时
        dtoList = investTradeRegist001Mapper.selectTradeNoCodeList(pageParam);
        return dtoList;
    }
    /**
     * 功能:处理履历表数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月21日
     * @param tradeList 交易编号列表
     * @param isManual 是否手工处理
     * void
     */
    @SuppressWarnings("unused")
    private void dealRegHis(List<String> tradeList,boolean isManual){
        // 手工处理
        if (isManual) {
            // 查询需要进入履历表的数据
            List<ChbInvestTradeSubHisDto> tradeRegHisList = new ArrayList<>();
            // 分批处理防止数据过多报错
            int queryCount = 200;
            int lastIndex = queryCount;
            for (int index = 0; index < tradeList.size();) {
                if (lastIndex > tradeList.size()) {
                    lastIndex = tradeList.size();
                }
                List<ChbInvestTradeSubHisDto> tradeRegHisListAdd = investTradeRegist001Mapper
                        .selectInvestTradeRegHisByTradeCode(tradeList.subList(index, lastIndex));
                if (CollectionUtils.isNotEmpty(tradeRegHisListAdd)) {
                    tradeRegHisList.addAll(tradeRegHisListAdd);
                }
                index = lastIndex;
                lastIndex += queryCount;
            }
            if (CollectionUtils.isNotEmpty(tradeRegHisList)) {
                // 登记表的seqno
                List<String> regseqnoList = new ArrayList<>();
                for (ChbInvestTradeSubHisDto investTradeSubHis : tradeRegHisList){
                    regseqnoList.add(investTradeSubHis.getHisNo());
                }
                // 已报送的数据进入履历表
                logger.debug("投资交易信息登记履历表插入数据：插入开始----------");
                int count = 0;
                SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                try {
                    ChbInvestTradeSubHisMapper batchMapper = session.getMapper(ChbInvestTradeSubHisMapper.class);
                    for (ChbInvestTradeSubHisDto trade : tradeRegHisList) {
                        batchMapper.insert(trade);
                        count++;
                        if (count % 1000 == 0) {
                            session.commit();
                            // 清理缓存，防止溢出
                            session.clearCache();
                        }
                    }
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                } catch (Exception e) {
                	logger.error(e.getMessage(),e);
                    session.rollback();
                    throw new BusinessException(e);
                } finally {
                    session.close();
                }
                logger.debug("投资交易信息登记履历表插入数据：插入结束----------");
                // 插入前先删除未报送的登记数据
                logger.debug("投资交易信息登记删除已报送数据：删除开始----------");
                // 数据批量一次删除条数,如果参数设置过大则强制定位50
                int batchDeleteCount = reportSelectOrDeleteCount;
                // 每批最后一个的下标
                int batchDeleteLastIndex = batchDeleteCount;
                for (int index = 0; index < regseqnoList.size();) {
                    if (batchDeleteLastIndex > regseqnoList.size()) {
                        batchDeleteLastIndex = regseqnoList.size();
                        investTradeRegist001Mapper.deleteByRegistseqno(
                                regseqnoList.subList(index, batchDeleteLastIndex));
                        index = batchDeleteLastIndex;// 设置下一批下标
                        batchDeleteLastIndex = index + batchDeleteCount;
                    }
                }
                logger.debug("投资交易信息登记删除已报送数据：删除结束----------");
            }
        } else {
            // 查询需要进入登记表的数据
            List<InvestTradeRegist001Dto> tradeRegList = new ArrayList<>();
            // 分批处理防止数据过多报错
            int queryCount = 200;
            int lastIndex = queryCount;
            for (int index = 0; index < tradeList.size();) {
                if (lastIndex > tradeList.size()) {
                    lastIndex = tradeList.size();
                }
                List<InvestTradeRegist001Dto> tradeRegListAdd = chbInvestTradeSubHisMapper
                        .selectInvestTradeRegByTradeCode(tradeList.subList(index, lastIndex));
                if (CollectionUtils.isNotEmpty(tradeRegListAdd)) {
                    tradeRegList.addAll(tradeRegListAdd);
                }
                index = lastIndex;
                lastIndex += queryCount;
            }
            if (CollectionUtils.isNotEmpty(tradeRegList)) {
                // 履历表的hisno
                List<String> hisnoList = new ArrayList<>();
                for (InvestTradeRegist001Dto investTradeSub : tradeRegList){
                    hisnoList.add(investTradeSub.getRegistSeqno());
                }
                // 已报送的数据进入履历表
                logger.debug("投资交易信息登记表插入数据：插入开始----------");
                int count = 0;
                SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                try {
                    InvestTradeRegist001Mapper batchMapper = session.getMapper(InvestTradeRegist001Mapper.class);
                    for (InvestTradeRegist001Dto asset : tradeRegList) {
                        batchMapper.insert(asset);
                        count++;
                        if (count % 1000 == 0) {
                            session.commit();
                            // 清理缓存，防止溢出
                            session.clearCache();
                        }
                    }
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                } catch (Exception e) {
                	logger.error(e.getMessage(),e);
                    session.rollback();
                    throw new BusinessException(e);
                } finally {
                    session.close();
                }
                logger.debug("投资交易信息登记表插入数据：插入结束----------");
                // 插入前先删除未报送的登记数据
                logger.debug("投资交易信息登记履历表删除数据：删除开始----------");
                // 数据批量一次删除条数,如果参数设置过大则强制定位50
                int batchDeleteCount = reportSelectOrDeleteCount;
                // 每批最后一个的下标
                int batchDeleteLastIndex = batchDeleteCount;
                for (int index = 0; index < hisnoList.size();) {
                    if (batchDeleteLastIndex > hisnoList.size()) {
                        batchDeleteLastIndex = hisnoList.size();
                        chbInvestTradeSubHisMapper.deleteByHisno(
                                hisnoList.subList(index, batchDeleteLastIndex));
                        index = batchDeleteLastIndex;// 设置下一批下标
                        batchDeleteLastIndex = index + batchDeleteCount;
                    }
                }
                logger.debug("投资交易信息登记履历表删除数据：删除结束----------");
            }
        }
    }

    /**
     * 功能:获取需要检查的登记数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年6月25日
     * @param param
     * @return 
     * List<String>
     */
    public List<InvestTradeRegist001Dto> getCheckRepList(Map<String, Object> param) {
        dealSearchParam(param);
        List<InvestTradeRegist001Dto> list = investTradeRegist001Mapper.selectAllData(param);
        return list;
    }

    /**
     * 功能:对所选择的数据进行数据检查
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年6月25日
     * @param keyList
     * @return 
     * List<ExcelValidDto>
     */
    public Map<String,Object> checkData(List<InvestTradeRegist001Dto> checkKeys) {
        Map<String,CheckReport001Dto> checkmap = new HashMap<String,CheckReport001Dto>();
        Map<String,InvestTradeRegist001Dto> repmap = new HashMap<String,InvestTradeRegist001Dto>();
		Map<String,Object> returnMap = new HashMap<>();
        List<InvestTradeRegist001Dto> deallist = investTradeRegist001Mapper.selectDataListByRegList(checkKeys);
		dealDataList(deallist,false);
        List<ExcelValidDto> result = new ArrayList<ExcelValidDto>();
//        repmap = deallist.stream().collect(Collectors.toMap(InvestTradeRegist001Dto::getRegistSeqno,(p)->p,(key1,key2)->key1));
        repmap = deallist.stream().collect(Collectors.toMap(p->p.getProdId() + GlobalConstant.COMMA + p.getAssetCode()+GlobalConstant.COMMA+p.getTradeCode()+GlobalConstant.COMMA+p.getCurAssetType(),(p)->p,(key1,key2)->key1));
        // 查询检查表
        List<CheckReport001Dto> resultlist = new ArrayList<CheckReport001Dto>();
        // 检查登记表中小于今天所有未报送的数据
        if(CollectionUtils.isNotEmpty(checkKeys)){
			List<String> keys = checkKeys.stream().map(string -> string.getRegistSeqno()).collect(Collectors.toList());
			// 调用检查方法
            resultlist.addAll(check(null,keys));
            // 同一资产同一产品汇总一条
            Map<String,String> showInMap = new HashMap<String,String>();
            checkmap = resultlist.stream().collect(Collectors.toMap(CheckReport001Dto::getUniqueKey,(p)->p,(key1,key2)->key1));
            for(InvestTradeRegist001Dto key : checkKeys){
                String uniqueKey = key.getProdId()+GlobalConstant.COMMA+key.getAssetCode()+GlobalConstant.COMMA+key.getTradeCode()+GlobalConstant.COMMA+key.getCurAssetType();
                //增加一个uniqueKey，用于匹配预警信息 【预警信息的uniqueKey是：产品代码+行内资产/负债编码】
                String uniqueKey1 = key.getProdId()+GlobalConstant.COMMA+key.getAssetCode();
                // 已经有的资产和产品不重复出数据检查
                if (!showInMap.containsKey(uniqueKey)){
                    showInMap.put(uniqueKey, uniqueKey);
                } else {
                    // 已经出过则跳过
                    continue;
                }
                if(checkmap.get(uniqueKey)!=null){
                    ExcelValidDto exc=new ExcelValidDto();
                    exc.setCheckResult(checkmap.get(uniqueKey).getCheckResult());
                    exc.setErrorInfo(checkmap.get(uniqueKey).getErrorInfo());
                    exc.setWarnInfo(checkmap.get(uniqueKey).getWarnInfo());
                    exc.setValidKey(checkmap.get(uniqueKey).getValidKey());
                    result.add(exc);
                }else if(checkmap.get(uniqueKey1)!=null){
                	ExcelValidDto exc=new ExcelValidDto();
                    exc.setCheckResult(checkmap.get(uniqueKey1).getCheckResult());
                    exc.setErrorInfo(checkmap.get(uniqueKey1).getErrorInfo());
                    exc.setWarnInfo(checkmap.get(uniqueKey1).getWarnInfo());
                    exc.setValidKey(checkmap.get(uniqueKey1).getValidKey()+(StringUtil.isNotEmpty(key.getTradeCode()) ? (";行内交易编码:"+key.getTradeCode()):"")
                    									+(StringUtil.isNotEmpty(key.getCurAssetType()) ? (";资金流动类型:"+(key.getCurAssetType())):""));
                    result.add(exc);
                }else{
                    ExcelValidDto exc=new ExcelValidDto();
                    exc.setValidKey("产品代码:"+repmap.get(uniqueKey).getProdId()
						+ (StringUtil.isNotEmpty(repmap.get(uniqueKey).getAssetCode()) ? (";资产代码:"+repmap.get(uniqueKey).getAssetCode()): "")
						+(StringUtil.isNotEmpty(repmap.get(uniqueKey).getTradeCode()) ? (";行内交易编码:"+repmap.get(uniqueKey).getTradeCode()):"")
						+(StringUtil.isNotEmpty(repmap.get(uniqueKey).getCurAssetType()) ? (";资金流动类型:"+repmap.get(uniqueKey).getCurAssetType()):"")
						);
                    exc.setCheckResult("01");
                    result.add(exc);
                }
            }
			returnMap.put("excelValidDtoList",result);
		}
		returnMap.put("resultList",deallist);
		return returnMap;
    }

    /**
     * @Description 检查登记表中小于今天所有未报送的数据
     * @return
     * @return List<CheckReport001Dto>
     * @version 1.7 2019年6月24日
     */
    public List<CheckReport001Dto> check(Date batchDate,List<String> keys){
        // 数据日期为系统时间
        Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		// 供跑批使用
		if (StringUtil.isNotEmpty(batchDate)){
			checkdate = batchDate;
		}
        // 删除今天所有已检查的数据
		//TODO
		//删除逻辑需要修改表结构,延后处理
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_资产交易信息登记.getValue(),checkdate,keys);
        //增加例外情况过滤
        List<ChbCheckRuleout> listInvestTradeRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_INVEST_TRADE_SUB);
        Map<String, List<ChbCheckRuleout>> mapInvestTradeRuleout = listInvestTradeRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //检查数据
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        //报送前强制的错误检查
        resultList.addAll(beforeSubErrorCheck.checkInvestTradeReg(checkdate,keys,mapInvestTradeRuleout));
        //警告逻辑检查
        resultList.addAll(beforeSubCheck.checkInvestReg(checkdate,mapInvestTradeRuleout,keys));
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
        return resultList;
    }
}
