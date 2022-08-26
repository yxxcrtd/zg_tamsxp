package com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.ChbInvestholdInfo;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.dto.SvrAjaxResult;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;


/**
 * 投资者持有信息维护
 * 〈功能详细描述〉
 * @author zhangzhongliang
 * @version 1.0 2019年4月19日
 * @see InvestorsHoldInfoMService
 * @since 1.0
 */
@Service
public class InvestorsHoldInfoMService extends BaseService<InvestorsHoldInfo001Dto> {

	@Autowired
	private InvestorsHoldInfo001Service investorsHoldInfo001Service;
    /**
     *
     * 业务画面查询
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public Page<InvestorsHoldInfo001Dto> selectForPage(Map<String, Object> param) {
    	return restTemplate.postForPage(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/findPage", param,
	       		InvestorsHoldInfo001Dto.class);
    }
    /**
     *
     * 业务画面导出事件
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
	public List<InvestorsHoldInfo001Dto> selectAllList(Map<String, Object> param) {
    	return restTemplate.postForList(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/selectAllList", param,
	       		InvestorsHoldInfo001Dto.class);
    }


    /**
     *
     * 删除
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public void delete(InvestorsHoldInfo001Dto dto){
    	restTemplate.postForObject(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/delete", dto,
	       		Integer.class);
    }

    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
	public List<ExcelValidDto> importData(String fileName) {
     	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("dataList", ExcelUtil.getDataList(fileName));
    	param.put("tempList", ExcelUtil.getTempList(fileName));
    	param.put("bankCode", CommonUtil.getUserBankCode(null));
    	param.put("deptCode", CommonUtil.getDeptCode());
    	param.put("orgCode", CommonUtil.getOrgCode());
    	return restTemplate.postForList(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/importData", 
	       		param,
	       		ExcelValidDto.class);
    }
    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
	public AjaxResult checkConfirmMsgForImportData(String fileName){
//    	Map<String,Object> param = new HashMap<String,Object>();
//    	param.put("bankCode", CommonUtil.getUserBankCode(null));
//    	param.put("deptCode", CommonUtil.getDeptCode());
//    	param.put("orgCode", CommonUtil.getOrgCode());
//    	param.put("dataList", ExcelUtil.getDataList(fileName));
////    	return restTemplate.postForObject(
////	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/checkConfirmMsgForImportData",
////	       		param,
////	       		SvrAjaxResult.class);
    	return investorsHoldInfo001Service.checkConfirmMsgForImportData(ExcelUtil.getDataList(fileName),CommonUtil.getUserBankCode(null),CommonUtil.getDeptCode(),CommonUtil.getOrgCode());
    }
    
    
    
    /**
     * 导入代销数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    public List<ExcelValidDto> importChbData(String fileName) {
     	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("dataList", ExcelUtil.getDataList(fileName));
    	param.put("tempList", ExcelUtil.getTempList(fileName));
    	param.put("bankCode", CommonUtil.getUserBankCode(null));
    	param.put("deptCode", CommonUtil.getDeptCode());
    	param.put("orgCode", CommonUtil.getOrgCode());
    	return restTemplate.postForList(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/importChbData", 
	       		param,
	       		ExcelValidDto.class);
    }
    
    
    /**
     *导入代销数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForChbImportData(String fileName) {
     	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("dataList", ExcelUtil.getDataList(fileName));
    	param.put("tempList", ExcelUtil.getTempList(fileName));
    	param.put("bankCode", CommonUtil.getUserBankCode(null));
    	param.put("deptCode", CommonUtil.getDeptCode());
    	param.put("orgCode", CommonUtil.getOrgCode());

//    	return restTemplate.postForObject(
//	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/checkConfirmMsgForChbImportData",
//	       		param,
//	       		SvrAjaxResult.class);
		return investorsHoldInfo001Service.checkConfirmMsgForChbImportData(ExcelUtil.getDataList(fileName),ExcelUtil.getTempList(fileName),CommonUtil.getUserBankCode(null),
				CommonUtil.getDeptCode(),CommonUtil.getOrgCode());

	}
    
    /**
     *
     * 保存操作
     * <功能详细描述>
     * @param dto 数据
     * @param result 检查结果
     * @return 返回结果
     * @see
     * @since 1.0
     */
    public void doSaveOrUpdate(InvestorsHoldInfo001Dto dto) {
    	 if(StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_NEW)){
    		 dto.setRegistBankCode(CommonUtil.selectBankCode());
    		 dto.setOrgCode(CommonUtil.getOrgCode());
    		 dto.setDeptCode(CommonUtil.getDeptCode());
    		 dto.setCreateDept(dto.getDeptCode()!=null?dto.getDeptCode():dto.getOrgCode());
    	 }
    	restTemplate.postForObject(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/doSaveOrUpdate", dto,
	       		SvrAjaxResult.class);
    }
    /**
     * 
     * 保存操作
     * <功能详细描述>
     * @param dto 数据
     * @param result 检查结果
     * @return 返回结果
     * @see
     * @since 1.0
     */
    public BigDecimal dealCnyAmt(Map<String, Object> param){
    	return restTemplate.postForObject(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/dealCnyAmt", param,
	       		BigDecimal.class);
    }
    
    /**
     * 根据业务主key获取entity
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    public ChbInvestholdInfo getEntityByKey(@RequestBody Map<String, Object> map) {
    	return restTemplate.postForObject(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/getEntityByKey", map,
	       		ChbInvestholdInfo.class);
    }

    /**
     * 根据业务主key获取entity
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
	public List<SelectOptionDto> queryAllRegistCode(){
    	return restTemplate.getForList(
	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/queryAllRegistCode",
	       		SelectOptionDto.class);
    }
}