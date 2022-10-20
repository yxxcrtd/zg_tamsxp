package com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.system.batch.batchstart.service.SysBatchStart001Service;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto.SalesTransactionMaintenanceRegist001Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Service
public class SalesTransactionMaintenanceRegistMService extends BaseService<SalesTransactionMaintenanceRegist001Dto> {
	
    @Autowired
    private SysBatchStart001Service sysBatchStart001Service;
	 /**
    *
    * 查询功能
    * <功能详细描述>
    * @return 存储了查询结果的list
    * @see
    * @since 1.0
    */
   public Page<SalesTransactionMaintenanceRegist001Dto> searchForPage(Map<String, Object> param){
	   return restTemplate.postForPage(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/findPage", param,
			   SalesTransactionMaintenanceRegist001Dto.class);
   }

	/**
	 * 数据检查
	 * @param map 查询条件
	 * @return 检查结果
	 * @see
	 * @since 1.0
	 */
	public List<ExcelValidDto> reportCheck(Map<String,Object> map) {
		return restTemplate.postForList(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/reportCheck", map,
				ExcelValidDto.class);
	}
   /**
    *
    * 查询功能（无分页）
    * <功能详细描述>
    * @return 存储了查询结果的list
    * @see
    * @since 1.0
    */
   public List<SalesTransactionMaintenanceRegist001Dto> queryForInfoRegist(Map<String, Object> param){
	   return restTemplate.postForList(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/queryForInfoRegist",
			   param,
			   SalesTransactionMaintenanceRegist001Dto.class);
   }
	/**
	 * 手工处理功能
	 *
	 * @return 提示信息
	 * @param map
	 *            条件map
	 * @seea
	 * @since 1.0
	 */
	public void updateSumStaus(Map<String, Object> map) {
		restTemplate.postForObject(
				SvrServiceApplication.CHB_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/updateSumStaus",map,
				Integer.class);
	}

	/**
	 * 取消手工处理功能
	 *
	 * @return 提示信息
	 * @param map
	 *            条件map
	 * @seea
	 * @since 1.0
	 */
	public void cancelMannul(Map<String, Object> map) {
		restTemplate.postForObject(
				SvrServiceApplication.CHB_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/cancelMannul",map,
				Integer.class);
	}
   
   /**
    * 将选择的数据导出成excel
    * 查询数据
    * @return 查询结果
    * @see
    * @since 1.0
    */
   public List<SalesTransactionMaintenanceRegist001Dto> queryExportSelectRegistList(String exportKeys){
	   return restTemplate.postForList(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/queryExportSelectRegistList", exportKeys,
	       		SalesTransactionMaintenanceRegist001Dto.class);
   }


   
   public boolean checkIfInBatchByTaskBeanName(String beanname) {
   	return restTemplate.postForObject(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/checkIfInBatchByTaskBeanName", beanname,
	       	boolean.class);
   }


	/**
	 * 报送检查
	 *
	 * @return 检查结果
	 * @see
	 * @since 1.0
	 */
	@ResponseBody
	public List<ExcelValidDto> submitBeforeCheck(Map<String, Object> param) {
		return restTemplate.postForList(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/submitBeforeCheck", param,
				ExcelValidDto.class);
	}

	/**
	 * 报送
	 *
	 * @param param
	 * 传入需要报送的主键
	 * @return 反馈信息
	 * @see
	 * @since 1.0
	 */
	public void doSubmit(Map<String, Object> param) {
		restTemplate.postForObject(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/submitChb", param,
				Integer.class);
	}

	/**
	 * 重新报送
	 *
	 * @param param
	 * 传入需要报送的主键
	 * @return 反馈信息
	 * @see
	 * @since 1.0
	 */
	public void doSubmitAgain(Map<String, Object> param) {
		restTemplate.postForObject(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/SalesTransactionMaintenanceRegist/submitCbAgain", param,
				Integer.class);
	}
}