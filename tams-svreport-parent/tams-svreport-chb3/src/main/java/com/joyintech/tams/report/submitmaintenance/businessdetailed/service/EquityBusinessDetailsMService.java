package com.joyintech.tams.report.submitmaintenance.businessdetailed.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.dto.SvrAjaxResult;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.dto.EquiteBusiDetails001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 权益业务明细信息维护 〈功能详细描述〉
 * 
 * @author zhangzhongliang
 * @version 1.0 2019年4月19日
 * @see EquityBusinessDetailsMService
 * @since 1.0
 */
@Service
public class EquityBusinessDetailsMService extends CommonExportService<EquiteBusiDetails001Dto> {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(EquityBusinessDetailsMService.class);

	@Autowired
	private EquityBusiDetails001Service equityBusiDetails001Service;
	/**
	 * 产品名称下拉框
	 * 
	 * @return 产品名称列表
	 */
	public List<SuggestDto> queryPrdNameList(String param) {
		return restTemplate.postForList(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/queryPrdNameList", param,
				SuggestDto.class);
	}

	/**
	 * 产品名称下拉框
	 * 
	 * @return 产品名称列表
	 */
	public List<SelectOptionDto> queryPrdNames(Map<String, Object> map) {
		return restTemplate.postForList(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/queryPrdNames", map,
				SelectOptionDto.class);
	}

	/**
	 * 查询列表
	 * 
	 * @return Page
	 */
	public Page<EquiteBusiDetails001Dto> searchForPage(Map<String, Object> param) {
		return restTemplate.postForPage(SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/findPage",
				param, EquiteBusiDetails001Dto.class);
	}

	/**
	 * 导入数据文件
	 * 
	 * @param fileName 导入的文件名
	 * @return 导入结果，包含验证内容，如果验证失败，返回message
	 */
	public List<ExcelValidDto> importData(String fileName, String pageId) {
		// 检查导入的Excel模板
		List<String> headList = new ArrayList<String>();
		headList.add("发行机构代码");
		headList.add("销售合同号");
		headList.add("核心交易流水号");
		headList.add("理财账号");
		headList.add("客户统一编号");
		headList.add("识别标识");
		headList.add("客户姓名");
		/**add by dxy  投资者明细7月升级  20220520 start*/
		headList.add("交易序列号");
		headList.add("关联活期存款账号");
		headList.add("关联活期存款账号开户行代码");
		headList.add("关联活期存款账号开户行名称");
		/**add by dxy  投资者明细7月升级  20220520 end*/
		headList.add("关联账号开户所在地");
		headList.add("是否代销");
		headList.add("代销机构代码");
		headList.add("代销机构名称");
		headList.add("代销机构所属监管机构");
		headList.add("产品代码");
		headList.add("业务种类");
		headList.add("业务发生地所属监管");
		headList.add("业务确认日期");
		headList.add("业务确认时间");
		headList.add("币种");
		headList.add("金额");
		headList.add("折算人民币金额");
		headList.add("确认净值");
		headList.add("份额");
		headList.add("费用");
		headList.add("渠道");
		headList.add("交易柜员号");
		headList.add("备注");
		// headList.add("导入检查结果");
		// headList.add("数据状态");

		List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(fileName, headList);
		if (!StringUtil.isEmptyList(tempCheckResult)) {
			return tempCheckResult;
		}

		List<List<String>> dataList = null;
		try {
			dataList = ExcelUtil.readToList(FileNameUtil.getUploadFilePath(fileName), null, null, 0, 1, 0);
		} catch (FileNotFoundException e) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageId", pageId);
		param.put("dataList", dataList);
		param.put("bankCode", CommonUtil.getUserBankCode(null));
		param.put("deptCode", CommonUtil.getDeptCode());
		param.put("orgCode", CommonUtil.getOrgCode());
		param.put("userName", LoginUserUtil.getUserName());
		return restTemplate.postForList(SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/importData",
				param, ExcelValidDto.class);
	}

	/**
	 * 导入数据文件
	 * 
	 * @param fileName 导入的文件名
	 * @return 导入结果，包含验证内容，如果验证失败，返回message
	 */
	public List<ExcelValidDto> importChbData(String fileName, String pageId) {
		// 检查导入的Excel模板
		List<String> headList = new ArrayList<String>();
		headList.add("发行机构代码");
		headList.add("销售合同号");
		headList.add("核心交易流水号");
		headList.add("理财账号");
		headList.add("客户统一编号");
		headList.add("识别标识");
		headList.add("客户姓名");
		/**add by dxy  投资者明细7月升级  20220520 start*/
		headList.add("交易序列号");
		headList.add("关联活期存款账号");
		headList.add("关联活期存款账号开户行代码");
		headList.add("关联活期存款账号开户行名称");
		/**add by dxy  投资者明细7月升级  20220520 end*/
		headList.add("关联账号开户所在地");
		headList.add("是否代销");
		headList.add("代销机构代码");
		headList.add("代销机构名称");
		headList.add("代销机构所属监管机构");
		headList.add("产品登记编码");
		headList.add("业务种类");
		headList.add("业务发生地所属监管");
		headList.add("业务确认日期");
		headList.add("业务确认时间");
		headList.add("币种");
		headList.add("金额");
		headList.add("折算人民币金额");
		headList.add("确认净值");
		headList.add("份额");
		headList.add("费用");
		headList.add("渠道");
		headList.add("交易柜员号");
		headList.add("备注");

		List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(fileName, headList);
		if (!StringUtil.isEmptyList(tempCheckResult)) {
			return tempCheckResult;
		}

		List<List<String>> dataList = null;
		try {
			dataList = ExcelUtil.readToList(FileNameUtil.getUploadFilePath(fileName), null, null, 0, 1, 0);
		} catch (FileNotFoundException e) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageId", pageId);
		param.put("dataList", dataList);
		param.put("bankCode", CommonUtil.getUserBankCode(null));
		param.put("deptCode", CommonUtil.getDeptCode());
		param.put("orgCode", CommonUtil.getOrgCode());
		param.put("userName", LoginUserUtil.getUserName());
		return restTemplate.postForList(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/importChbData", param,
				ExcelValidDto.class);
	}

	/**
	 * 导出excel查询
	 *
	 * @return 导出
	 */
	public List<EquiteBusiDetails001Dto> queryReportList(Map<String, Object> param) {
		return restTemplate.postForList(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/queryReportList", param,
				EquiteBusiDetails001Dto.class);
	}

	/**
	 * 删除数据
	 * <p>
	 * 逻辑删除数据，并返回处理结果
	 * </p>
	 * 
	 * @return 处理结果
	 */
	public void doDelete(EquiteBusiDetails001Dto dto) {
		restTemplate.postForObject(SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/doDelete", dto,
				Integer.class);
	}

	/**
	 * 导入数据文件之前检查数据
	 *
	 * @param fileName 导入的文件名
	 * @return 处理结果
	 */
	public AjaxResult checkConfirmMsgForImportData(String fileName) {
		List<List<String>> dataList = null;
		try {
			dataList = ExcelUtil.readToList(FileNameUtil.getUploadFilePath(fileName), null, null, 0, 1, 0);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dataList", dataList);
		param.put("bankCode", CommonUtil.getUserBankCode(null));
		param.put("deptCode", CommonUtil.getDeptCode());
		param.put("orgCode", CommonUtil.getOrgCode());
		param.put("userName", LoginUserUtil.getUserName());

//		return restTemplate.postForObject(
//				SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/checkConfirmMsgForImportData", param,
//				SvrAjaxResult.class);

		return equityBusiDetails001Service.checkConfirmMsgForImportData(param);
	}

	/**
	 * 导入代销文件之前检查数据
	 *
	 * @param fileName 导入的文件名
	 * @return 处理结果
	 */
	public AjaxResult checkConfirmMsgForChbImportData(String fileName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fileName", fileName);
		param.put("bankCode", CommonUtil.getUserBankCode(null));
		param.put("deptCode", CommonUtil.getDeptCode());
		param.put("orgCode", CommonUtil.getOrgCode());
		param.put("userName", LoginUserUtil.getUserName());
		return equityBusiDetails001Service.checkConfirmMsgForChbImportData(param);
	}

	public EquiteBusiDetails001Dto getEntityByKey(Map<String, Object> map) {
		return restTemplate.postForObject(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/getEntityByKey", map,
				EquiteBusiDetails001Dto.class);
	}

	public List<SelectOptionDto> queryAllRegistCode() {
		return restTemplate.getForList(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/queryAllRegistCode",
				SelectOptionDto.class);
	}

	public AjaxResult doSaveOrUpdate(@RequestBody EquiteBusiDetails001Dto dto) {
		dto.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dto", dto);
		param.put("bankCode", CommonUtil.getUserBankCode(null));
		return restTemplate.postForObject(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityBusinessDetails/doSaveOrUpdate", param,
				SvrAjaxResult.class);
	}

}