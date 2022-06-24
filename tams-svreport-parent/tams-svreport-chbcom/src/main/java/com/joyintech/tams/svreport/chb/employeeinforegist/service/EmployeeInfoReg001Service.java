package com.joyintech.tams.svreport.chb.employeeinforegist.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.service.SvrCommonExportService;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.dao.ChbBeforeSubErrorCheckMapper;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubErrorCheck;
import com.joyintech.tams.svreport.chb.employeeinforegist.dao.EmployeeInfoReg001Mapper;
import com.joyintech.tams.svreport.chb.employeeinforegist.dto.EmployeeInfoReg001Dto;
import com.joyintech.tams.svreport.chb.prodpersonissue.dto.ProdPersonIssue001Dto;
import com.joyintech.tools.collectors.JoyinCollectors;

/**
 * 从业人员登记service
 *
 * @author lingxq
 * @version 1.0 2020年11月3日
 * @see EmployeeInfoReg001Service
 * @since 1.0
 */
@Service(value="svrEmployeeInfoReg001Service")
@Transactional(rollbackFor = Exception.class)
public class EmployeeInfoReg001Service extends SvrCommonExportService<EmployeeInfoReg001Dto> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 长sql最大一次插入或者更新条数
     */
    @Value("${application.reportInsertLongCount}")
    public  String reportInsertLongCount;
    /**
     * sql最大一次最大删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public  String reportSelectOrDeleteCount;
    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportInsertCount}")
    public  String reportInsertCount;
    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportSelectOrDeleteCountForShenFen}")
    public  int reportSelectOrDeleteCountForShenFen;
    
    @Autowired
    private EmployeeInfoReg001Mapper employeeInfoReg001Mapper;
	
	@Autowired
	private CheckReport001Mapper checkReport001Mapper;
	
	@Autowired
	private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
	
	@Autowired
	private BeforeSubErrorCheck beforeSubErrorCheck;

    @Autowired
    private ChbBeforeSubErrorCheckMapper chbBeforeSubErrorCheckMapper;
    /**
     * 中债基础数据mapper
     */
//    @Autowired
//    private BaseMapper baseMapper;
    

	/**
	 * 查询页面数据
	 *
	 * @param pageParam
	 *            查询条件
	 * @return 结果列表
	 * @see
	 * @since 1.0
	 */
	public Page<EmployeeInfoReg001Dto> selectForPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		//将发送状态改为空
		pageParam.put("sendStatus", "");
		Page<EmployeeInfoReg001Dto> page = null;
		if(pageParam.get(GlobalConstant.PARAM_PAGE)!=null){
			int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
	        int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
	        page = queryForPage(
					pagenum,legth,()-> employeeInfoReg001Mapper.selectAllData(pageParam));
		}else{
			page = queryForPage(EmployeeInfoReg001Mapper.class,"selectAllData",pageParam);
		}
		dealResultlist(page.getRoot());
		return page;
		
	}
	
	/**
	 * 数据字典转化
	 * @param listSub
	 */
	public void dealResultlist(List<EmployeeInfoReg001Dto> listSub) {
		listSub.forEach(item -> {
			//从业人员类型
			if(item.getEmployeeType()!=null){
				item.setEmployeeType(item.getEmployeeType().trim()+" "
						+SvrDataUtil.getNameFromDataDict(DictCode.从业人员类型, item.getEmployeeType()));
			}
			//性别
			if(item.getEmployeeSex()!=null){
				item.setEmployeeSex(item.getEmployeeSex().trim()+" "
						+SvrDataUtil.getNameFromDataDict(DictCode.性别_中债, item.getEmployeeSex()));
			}
			//所属总行或分支行
			if(item.getBelongsBank()!=null){
				item.setBelongsBank(item.getBelongsBank().trim()+" "
						+SvrDataUtil.getNameFromDataDict(DictCode.所属总行或分支行, item.getBelongsBank()));
			}
			//所属区域
			if(item.getBelongsPlace()!=null){
				item.setBelongsPlace(item.getBelongsPlace().trim()+" "
						+SvrDataUtil.getNameFromDataDict(DictCode.销售区域_中债, item.getBelongsPlace()));
			}
			//学历
			if(item.getEducation()!=null){
				item.setEducation(item.getEducation().trim()+" "
						+SvrDataUtil.getNameFromDataDict(DictCode.学历, item.getEducation()));
			}
			//学位
			if(item.getAcademicDegree()!=null){
				item.setAcademicDegree(item.getAcademicDegree().trim()+" "
						+SvrDataUtil.getNameFromDataDict(DictCode.学位, item.getAcademicDegree()));
			}
			//专业技术职称
			if(item.getProfessionalTitle()!=null){
				item.setProfessionalTitle(item.getProfessionalTitle().trim()+" "
						+SvrDataUtil.getNameFromDataDict(DictCode.专业技术职称, item.getProfessionalTitle()));
			}
			//理财专业证书
		    if(item.getFinProfessionalCrtf()!=null){
		    	List<String> crtfs=StringUtil.splitToList(GlobalConstant.COMMA, item.getFinProfessionalCrtf());
				item.setFinProfessionalCrtf("");
				for(String crtf : crtfs){
					item.setFinProfessionalCrtf(item.getFinProfessionalCrtf() + "," + crtf + " "
												+SvrDataUtil.getNameFromDataDict(DictCode.理财专业证书, crtf));
				}
				item.setFinProfessionalCrtf(item.getFinProfessionalCrtf().substring(1));
		    }
			//登记业务分类
		    if(item.getRegBusiType()!=null){
		    	List<String> regBusiTypes=StringUtil.splitToList(GlobalConstant.COMMA, item.getRegBusiType());
				item.setRegBusiType("");
				//add by zhoubin 20220426 start
				if (CollectionUtils.isNotEmpty(regBusiTypes)){
					for(String regBusiType : regBusiTypes){
						item.setRegBusiType(item.getRegBusiType() + "," + regBusiType + " "
								+SvrDataUtil.getNameFromDataDict(DictCode.登记业务分类, regBusiType));
					}
					item.setRegBusiType(item.getRegBusiType().substring(1));
				}
				//add by zhoubin 20220426 end
		    }
			//登记人员类别
		    if(item.getRegEmployeeType()!=null){
		    	List<String> regEmployeeTypes=StringUtil.splitToList(GlobalConstant.COMMA, item.getRegEmployeeType());
				item.setRegEmployeeType("");
				if (CollectionUtils.isNotEmpty(regEmployeeTypes)) {
					for (String regEmployeeType : regEmployeeTypes) {
						item.setRegEmployeeType(item.getRegEmployeeType() + "," + regEmployeeType + " "
								+ SvrDataUtil.getNameFromDataDict(DictCode.登记人员类别, regEmployeeType));
					}
					item.setRegEmployeeType(item.getRegEmployeeType().substring(1));
				}
		    }

		});
	}

	/**
	 * 处理查询前的参数 <功能详细描述>
	 * @param pageParam
	 * @see
	 * @since 1.0
	 */
	public void dealSearchParam(Map<String, Object> pageParam) {
		// 选择行数据时
		if (pageParam.containsKey("keyList")) {
			String keyList = pageParam.get("keyList").toString();
			try {
				List<EmployeeInfoReg001Dto> employeeInfoList = JsonUtils.json2List(keyList,
						EmployeeInfoReg001Dto.class);

				for(EmployeeInfoReg001Dto tempDto : employeeInfoList) {
					tempDto.setEmployeeType(null == tempDto.getEmployeeType()? "" : tempDto.getEmployeeType().substring(0,2));
				}

				pageParam.put("employeeInfoList", employeeInfoList);
			} catch (IOException e) {
	            logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 从业人员类型
			String employeeType = (String) pageParam.get("employeeType");
			if (StringUtil.isNotBlank(employeeType)) {
				pageParam.put("employeeTypeList", StringUtil.splitToList(GlobalConstant.COMMA, employeeType));
			}
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 将报送状态当做多选下拉框处理
			if (StringUtil.isNotBlank(sendStatus)) {
				pageParam.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
			}
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");
			// 将反馈状态当做多选下拉框处理
			if (StringUtil.isNotBlank(submitFeedbackStatus)) {
				pageParam.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
			}
			// 反馈状态
			String status = (String) pageParam.get("status");
			// 将反馈状态当做多选下拉框处理
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
		}
	}
	
	 /**
     * 手动处理
     * @param map 
     */
	@SuppressWarnings("unchecked")
	public void updateSumStaus(Map<String, Object> map) {
		dealSearchParam(map);
		List<EmployeeInfoReg001Dto> registList = new ArrayList<EmployeeInfoReg001Dto>();
		//选择的手动处理
		registList = (List<EmployeeInfoReg001Dto>) map.get("employeeInfoList");
        if (StringUtil.isEmptyList(registList)) {
        	//未选中数据
        	registList = employeeInfoReg001Mapper.selectAllData(map);
		}

        if(!CollectionUtils.isEmpty(registList)){
 			// 每批处理的数据条数
 			int partSize = 500;
	 		// 存放业务数据的临时list，数据量不超过partSize
	 		List<EmployeeInfoReg001Dto> tmpList = new ArrayList<EmployeeInfoReg001Dto>();
	 		// 分批次处理
	 		for (int j = 0; j < registList.size(); j++) {
	 			tmpList.add(registList.get(j));
	 			if (partSize == tmpList.size() || j == registList.size() - 1) {
	 				//找sub表有没有已报送的数据
	 				List<String> regList = employeeInfoReg001Mapper.selectRegistBySeqnoList(tmpList,"02");
	 				//更新sub表数据（中债通过、手工处理）
	 	 			this.updateInterfSubmitBySeq(DictCodeListExt2.报送状态_手工处理.getValue(),DictCodeListExt2.报送反馈状态_中债通过.getValue(), tmpList);
	 	 			if(!CollectionUtils.isEmpty(regList)){
	 					List<List<String>> regseqnolist = createStringList(regList,20);
	 					for(int i=0;i<regseqnolist.size();i++){
	 						employeeInfoReg001Mapper.insertReportHisBySeq(regseqnolist.get(i));

	 						//将已报送的数据从sub表物理删除
	 						employeeInfoReg001Mapper.deleteLastSubmitRegistBySeq(regseqnolist.get(i));

	 					}
	 				}
	 	 			tmpList.clear();
	 			}
	 		}
 		}
	}

	/**
 	 * 分批次执行 更新画面选择记录/或者查询出的 待处理列表
 	 *
 	 * @param investList
 	 *            数据
 	 * @see
 	 * @since 1.0
 	 */
 	private void updateInterfSubmitBySeq(String sendStatus,String submitFeeBackStatus , List<EmployeeInfoReg001Dto> investList) {
 		if (StringUtil.isEmptyList(investList)) {
 			return;
 		}
 		int maxDataCountOneTime = 10;
 		int dataCount = investList.size();
 		if (dataCount <= maxDataCountOneTime) {
 			employeeInfoReg001Mapper.updateInterfSubmitBySeqBatch(sendStatus,submitFeeBackStatus, investList);
 			return;
 		}
 		List<EmployeeInfoReg001Dto> newDataList = new ArrayList<EmployeeInfoReg001Dto>();
 		newDataList.addAll(investList);
 		// 分批处理
 		int part = dataCount / maxDataCountOneTime; // 分批数
 		for (int i = 0; i < part; i++) {
 			logger.info("单次更新登记表start===");
 			employeeInfoReg001Mapper.updateInterfSubmitBySeqBatch(sendStatus,submitFeeBackStatus,
                 newDataList.subList(0, maxDataCountOneTime));
 			newDataList.subList(0, maxDataCountOneTime).clear();
 			logger.info("单次更新登记表end===");
 		}
 		// 最后剩下的数据
 		if (CollectionUtils.isNotEmpty(newDataList)) {
 			logger.info("单次更新登记表start===");
 			employeeInfoReg001Mapper.updateInterfSubmitBySeqBatch(sendStatus,submitFeeBackStatus, newDataList);
 			logger.info("单次更新登记表end===");
 		}
 	}



	private List<List<String>> createStringList(List<String> targe,int size) {
		List<List<String>> listArr = new ArrayList<List<String>>();
		//获取被拆分的数组个数
		int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
		for(int i=0;i<arrSize;i++) {
			List<String> sub = new ArrayList<String>();
			//把指定索引数据放入到list中
			for(int j = i*size;j <= size*(i+1)-1;j++) {
				if(j <= targe.size()-1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(sub);
		}
		return listArr;
	}
	
	/**
	 * 已手工处理的数据将报送状态改为未报送
	 *
	 * @param upKey
	 *            传入主键
	 * @see
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public String cancelMannul(Map<String, Object> map) {
		dealSearchParam(map);
		List<EmployeeInfoReg001Dto> infoList = new ArrayList<EmployeeInfoReg001Dto>();
		//选择的手动处理
		infoList = (List<EmployeeInfoReg001Dto>) map.get("employeeInfoList");
        if (StringUtil.isEmptyList(infoList)) {
        	//未选中数据
        	map.put("sendStatus", "03");
        	infoList = employeeInfoReg001Mapper.selectAllData(map);
		}
		// 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> registSeqList = new ArrayList<String>();
		for(EmployeeInfoReg001Dto m : infoList){
			m.setEmployeeType(null == m.getEmployeeType()? "" : m.getEmployeeType().substring(0,2));
			registSeqList.add(m.getEmployeeType() 
			          + GlobalConstant.VERTICAL_LINE + m.getIdentifyNumber()
			          /*+ GlobalConstant.VERTICAL_LINE + m.getStatus()*/);
		}
		if (!StringUtil.isEmptyList(infoList)) {
			//找sub表有没有未报送的数据
			List<String> unregList = employeeInfoReg001Mapper.selectRegistSeqnoList(registSeqList,"01");
			if(!CollectionUtils.isEmpty(unregList)){
				return "fail";
			}
            // 分批处理
            int maxDataCountOneTime = 300;
            int dataCount = registSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
            	//更新sub表报送状态
            	employeeInfoReg001Mapper.updateEmployeeInfoRegist(registSeqList);
            	 // 将履历表中履历号最大的数据挪回登记表
            	employeeInfoReg001Mapper.insertReportDataFromHis(infoList);
                // 删除履历表中移除数据
            	employeeInfoReg001Mapper.deleteLastSubmitHis(infoList);
            }
            else {
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(registSeqList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                	//更新sub表报送状态
                	employeeInfoReg001Mapper.updateEmployeeInfoRegist(newDataList.subList(0, maxDataCountOneTime));
                	 // 将履历表中履历号最大的数据挪回登记表
                	employeeInfoReg001Mapper.insertReportDataFromHis(infoList);
                    // 删除履历表中移除数据
                	employeeInfoReg001Mapper.deleteLastSubmitHis(infoList);
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                	//更新sub表报送状态
                	employeeInfoReg001Mapper.updateEmployeeInfoRegist(newDataList);
                	 // 将履历表中履历号最大的数据挪回登记表
                	employeeInfoReg001Mapper.insertReportDataFromHis(infoList);
                    // 删除履历表中移除数据
                	employeeInfoReg001Mapper.deleteLastSubmitHis(infoList);
                }
            }
        }
		return "success";
	}
	
	/**
	 * 分批次执行updateInterfSubmitAndSeq
	 *
	 * @param employeeInfoList
	 *            数据
	 * @see
	 * @since 1.0
	 */
	private void updateInterfSubmitAndSeq(String sendStatus, List<EmployeeInfoReg001Dto> employeeInfoList, boolean isSendAllData) {
		if (StringUtil.isEmptyList(employeeInfoList)) {
			return;
		}
		int maxDataCountOneTime = reportSelectOrDeleteCountForShenFen;
		int dataCount = employeeInfoList.size();
		if (dataCount <= maxDataCountOneTime) {
			//已报送数据进留痕表
			employeeInfoReg001Mapper.insertReportHis(employeeInfoList);
            //删除正式表已报送数据
			employeeInfoReg001Mapper.deleteLastSubmitRegist(employeeInfoList);
			employeeInfoReg001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, employeeInfoList);
			return;
		}

		// 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
		    logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表start===");
		    if (isSendAllData) {
		    //已报送数据进留痕表
			employeeInfoReg001Mapper.insertReportHis(employeeInfoList);
            //删除正式表已报送数据
			employeeInfoReg001Mapper.deleteLastSubmitRegist(employeeInfoList);
		    employeeInfoReg001Mapper.updateAllInterfSubmitAndSeqBatch(sendStatus);
		    logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表end===");
		    return;
		}

		List<EmployeeInfoReg001Dto> newDataList = new ArrayList<EmployeeInfoReg001Dto>();
		newDataList.addAll(employeeInfoList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			logger.info("单次更新登记表start===");
			//已报送数据进留痕表
			employeeInfoReg001Mapper.insertReportHis(newDataList.subList(0, maxDataCountOneTime));
            //删除正式表已报送数据
			employeeInfoReg001Mapper.deleteLastSubmitRegist(newDataList.subList(0, maxDataCountOneTime));
			employeeInfoReg001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,
                newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("单次更新登记表end===");
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			logger.info("单次更新登记表start===");
			//已报送数据进留痕表
			employeeInfoReg001Mapper.insertReportHis(newDataList);
            //删除正式表已报送数据
			employeeInfoReg001Mapper.deleteLastSubmitRegist(newDataList);
			employeeInfoReg001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
			logger.info("单次更新登记表end===");
		}
	}
	


	/**
	 * 选择导出
	 * @param pageParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeeInfoReg001Dto> selectForExport(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		List<EmployeeInfoReg001Dto> dataList =  (List<EmployeeInfoReg001Dto>) pageParam.get("employeeInfoList");
		List<EmployeeInfoReg001Dto> dtoList = new ArrayList<EmployeeInfoReg001Dto>();
		selectAllDataBatch(dataList, dtoList);
		dealResultlist(dtoList);
		return dtoList;
	}
	
	/**
	 * 根据选择的数据导出
	 * @param dataList
	 * @param dtoList
	 */
	private void selectAllDataBatch(List<EmployeeInfoReg001Dto> dataList, List<EmployeeInfoReg001Dto> dtoList) {
		if (StringUtil.isEmptyList(dataList)) {
            return;
        }
        int maxDataCountOneTime = reportSelectOrDeleteCountForShenFen;
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
        	dtoList.addAll(employeeInfoReg001Mapper.selectAllDataByList(dataList));
            return;
        }

        List<EmployeeInfoReg001Dto> newDataList = new ArrayList<EmployeeInfoReg001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
        	dtoList.addAll(employeeInfoReg001Mapper.selectAllDataByList(newDataList.subList(0, maxDataCountOneTime)));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	dtoList.addAll(employeeInfoReg001Mapper.selectAllDataByList(newDataList));
        }
	}
	
	/**
     * 数据检查
     * @param checkKeys 检查数据的关键字
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public Map<String,Object> checkData(Map<String, Object> param){
    	// param.put("sendStatus", DictCodeListExt2.报送状态_未报送.getValue());
    	dealSearchParam(param);
        List<String> keyList = new ArrayList<String>();
    	if(param.get("checkKeys")!=null){
        	keyList = StringUtil.splitToList(GlobalConstant.COMMA, (String)param.get("checkKeys"));
        	param.put("registList", keyList);
        }
    	Map<String,Object> returnMap = new HashMap<>();
		List<EmployeeInfoReg001Dto> equityInfoRegistList = employeeInfoReg001Mapper.selectAllData(param);
		List<ExcelValidDto> checkResult = new ArrayList<>();
		List<CheckReport001Dto> resultlist = new ArrayList<CheckReport001Dto>();
		if(CollectionUtils.isNotEmpty(equityInfoRegistList)){
			//此处check在info入sub表时已做，此处不再重复check
//	    	//查询数据库表字段
//	        List<String> listCloumn = baseMapper.getTableColumnList("CHB_EMPLOYEE_INFO_SUB");
//	        //调用共通  将list<接口表DTO>转换成List<Map<Key, Object>>格式
//	        List<Map<String, String>>  listMap = SvrMaptoEntityUtil.convertEntityToMap(equityInfoRegistList, listCloumn);
//			// 调用检查方法
//            resultlist.addAll(check(null,listMap));
//			for(CheckReport001Dto dto : resultlist){
//				ExcelValidDto exc = new ExcelValidDto();
//			    exc.setCheckResult(dto.getCheckResult());
//                exc.setErrorInfo(dto.getErrorInfo());
//                exc.setWarnInfo(dto.getWarnInfo());
//                exc.setValidKey(dto.getValidKey());
//				checkResult.add(exc);
//			}
			//没检查出错误数据则给予通过
			if(StringUtil.isEmptyList(resultlist)){
				dealResultlist(equityInfoRegistList);
				for(EmployeeInfoReg001Dto dto : equityInfoRegistList){
					ExcelValidDto exc = new ExcelValidDto();
					exc.setValidKey("姓名:" + dto.getEmployeeName() + " 身份证:" + dto.getIdentifyNumber());
					exc.setCheckResult("01");
					checkResult.add(exc);
				}
			}
		}else{
			 checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "无报送数据",null));
			 returnMap.put("excelValidDtoList",checkResult);
             return returnMap;
		}
		returnMap.put("excelValidDtoList",checkResult);
		returnMap.put("resultList",equityInfoRegistList);
		return returnMap;
    }
    
    /**
     * 检查登记表中小于今天所有未报送的数据
     * @param batchDate  用于后台跑批报送用 
     * @param equityInfoRegistList 用于前台报送用
     * @return
     */
	public List<CheckReport001Dto> check(Date batchDate,List<Map<String,String>> equityInfoRegistList,List<String> keys){
		List<Map<String,String>> listData = null;
		Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		if(!StringUtil.isEmptyList(equityInfoRegistList)){
			listData = equityInfoRegistList;
		}else if(null != batchDate){
			// 数据日期为系统时间
			// 供跑批使用
			checkdate = batchDate;
			listData = chbBeforeSubErrorCheckMapper.selectChbEmployeeSub(checkdate);
		}
		// 删除今天所有已检查的数据
		checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_从业人员登记.getValue(),checkdate,keys);
		//增加例外情况过滤
        List<ChbCheckRuleout> listRuleout = chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_EMPLOYEE_INFO_SUB);
        Map<String, List<ChbCheckRuleout>> mapRuleout = listRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
		//检查数据
		List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
		//报送前强制的错误检查
		resultList.addAll(beforeSubErrorCheck.checkChbEmployeeReg(listData,checkdate,null,mapRuleout));
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
