package com.joyintech.tams.svreport.chb.astposregist.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.system.batch.batchstart.service.SysBatchStart001Service;
import com.joyintech.tams.common.base.dao.ChbBatchParaMapper;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.entity.ChbBatchPara;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;
import com.joyintech.tams.svreport.chb.astposregist.dao.ChbAstPosRegist001Mapper;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Description 资产持仓登记服务
 */
@Service		
public class ChbAstPosRegistMService extends BaseService<ChbAstPosRegist001Dto>{

	@Autowired
	private SysBatchStart001Service sysBatchStart001Service;
	@Autowired
	private ChbAstPosRegist001Mapper chbAstPosRegist001Mapper;
	@Autowired
	private ChbBatchParaMapper chbBatchParaMapper;
	@Autowired
	private ChbTypeMappingExMapper chbTypeMappingExMapper;



    /**
     * @Description 资产持仓登记页面查询
     * @return Page<ProdTerminationRegist001Dto>
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<ChbAstPosRegist001Dto> selectForPage(Map<String, Object> param) {
    	return restTemplate.postForPage(
	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/findPage",param,
				ChbAstPosRegist001Dto.class);
    }
    
    /**
     * 业务画面导出事件
     * @return
     * @since 3.0
     */
    public List<ChbAstPosRegist001Dto> selectForExport(Map<String, Object> param) {
          return restTemplate.postForList(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/selectForExport",param,
				  ChbAstPosRegist001Dto.class);
    }
    /**
     *手工处理功能
     * @return 提示信息
     * @param map 条件map
     */
    @PostMapping("updateSumStaus")
    public void updateSumStaus(Map<String,Object> map) {
    	restTemplate.postForObject(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/updateSumStaus",map,
  	       		Integer.class);
    }
    
    /**
     * @Description 取消手工处理
     * @param map
     * @return
     */
	@PostMapping("cancelMannul")
	public String cancelMannul(Map<String, Object> map) {
    	return restTemplate.postForObject(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/cancelMannul",map,
  	       			String.class);
	}
	
	/**
	* @return：资产持仓登记数据检查结果
	*/
	@PostMapping("reportCheck")
	public Map<String,Object> reportCheck(Map<String, Object> map) {
		return restTemplate.postForObject(
	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/reportCheck", map,
				Map.class);
	}

	/**
	 *
	 * 查询功能（无分页）
	 * <功能详细描述>
	 * @return 存储了查询结果的list
	 * @see
	 * @since 1.0
	 */
	public List<ChbAstPosRegist001Dto> queryForAstPosRegistList(Map<String, Object> param){
		return restTemplate.postForList(
				SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/selectAllData",
				param,
				ChbAstPosRegist001Dto.class);
	}

	@PostMapping("countExportNum")
	public int countExportNum(Map<String, Object> param) {
		return restTemplate.postForObject(SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/countExportNum",param,Integer.class);
	}

	/**
	 * 启动批处理导出
	 * @param param
	 */
	public AjaxResult batchExport(Map<String, Object> param) {
		AjaxResult ajaxResult = new AjaxResult();
		// 是否未执行或正在执行中
		if (!checkIfInBatchByTaskBeanName(
				BatchBeanName.资产持仓登记批处理导出.getTaskId())) {
			ajaxResult.setMessage("资产持仓登记导出批处理正在执行中或待执行！", MessageType.错误消息);
			return ajaxResult;
		}
		ChbSubinfoBatchLog log = new ChbSubinfoBatchLog();
		log.setBatchExeId(IDGenerator.generate());
		log.setTaskId(BatchBeanName.资产持仓登记批处理导出.getTaskId());
		log.setBusiConfirmDate(param.get("busiConfirmDate")==null?"":param.get("busiConfirmDate").toString());
		log.setOperator(LoginUserUtil.getUserName());
		log.setFileType(DictCodeListExt3.报送文件类别_资产持仓登记批处理导出.getValue());
		log.setDealStatus(DictCodeListExt3.报送处理状态_未处理.getValue());
		log.setGenCondition(param.get("key")==null?"":param.get("key").toString());
		log.setCreateUser(LoginUserUtil.getUserName());
		log.setCreateTime(new Date());
		log.setUpdateUser(LoginUserUtil.getUserName());
		log.setUpdateTime(new Date());
		// 日志存表
		restTemplate.postForObject(SvrServiceApplication.COMMON_SERVICE_NAME + "/ChbSubinfoBatchLog/insert", log,
				ChbSubinfoBatchLog.class);
		// 启动批处理
		sysBatchStart001Service.executeBatch();
		// 批处理启动成功，可在批处理监控页面查看进度
//		return new AjaxResult("导出excel文件批处理启动成功，可在批处理监控页面查看进度");
		ajaxResult.setData(true);
		return ajaxResult;
	}


	/**
	 * 启动批处理导出和手工处理
	 * @return
	 * @since 3.0
	 */
	public AjaxResult batchExportAndDoManual(Map<String, Object> param) {
		AjaxResult ajaxResult = new AjaxResult();
		// 是否未执行或正在执行中
		if (!checkIfInBatchByTaskBeanName(
				BatchBeanName.资产持仓登记批处理导出.getTaskId())) {
			ajaxResult.setMessage("资产持仓登记导出批处理正在执行中或待执行！", MessageType.错误消息);
			return ajaxResult;
		}
		if (!checkIfInBatchByTaskBeanName(
				BatchBeanName.资产持仓登记手工处理.getTaskId())) {
			ajaxResult.setMessage("资产持仓登记手工处理批处理正在执行中或待执行！", MessageType.错误消息);
			return ajaxResult;
		}
		ChbSubinfoBatchLog log = new ChbSubinfoBatchLog();
		log.setBatchExeId(IDGenerator.generate());
		log.setTaskId(BatchBeanName.资产持仓登记批处理导出.getTaskId());
		log.setBusiConfirmDate(param.get("busiConfirmDate")==null?"":param.get("busiConfirmDate").toString());
		log.setOperator(LoginUserUtil.getUserName());
		log.setFileType(DictCodeListExt3.报送文件类别_资产持仓登记批处理导出.getValue());
		log.setDealStatus(DictCodeListExt3.报送处理状态_未处理.getValue());
		log.setGenCondition(param.get("key")==null?"":param.get("key").toString());
		log.setCreateUser(LoginUserUtil.getUserName());
		log.setCreateTime(new Date());
		log.setUpdateUser(LoginUserUtil.getUserName());
		log.setUpdateTime(new Date());
		// 日志存表
		restTemplate.postForObject(SvrServiceApplication.COMMON_SERVICE_NAME + "/ChbSubinfoBatchLog/insert", log,
				ChbSubinfoBatchLog.class);
		//手工处理
		ChbBatchPara record = new ChbBatchPara();
		record.setTaskId(BatchBeanName.资产持仓登记手工处理.getTaskId());
		record.setParaId(IDGenerator.generate());
		if (param.get("key") != null) {
			record.setParas(param.get("key").toString());
			chbBatchParaMapper.insert(record);
		}
		// 启动批处理
		sysBatchStart001Service.executeBatch();
		ajaxResult.setData(true);
		return ajaxResult;
	}

	/**
	 * 检查对应的task是否有未执行或正在执行中的批处理任务 <功能详细描述>
	 *
	 * @param beanname
	 * @return false未执行或正在执行中的任务 true 未检查到对应的未执行或正在执行中的任务
	 */
	public boolean checkIfInBatchByTaskBeanName(String beanname) {
		int count = chbAstPosRegist001Mapper.selectNoendCountByTaskBeanName(beanname);
		if (count > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 会计科目下拉框
	 * @return
	 */
	public List<SelectOptionDto> getSubjectNameList(Map<String, Object> map) {
		String subjectName = (String)map.get("subjectName");
		List<String> subjectNameList = StringUtil.splitToList(GlobalConstant.COMMA, subjectName);
		map.put("subjectNameList", subjectNameList);
		return chbTypeMappingExMapper.getSubjectNameList(map);
	}
}
