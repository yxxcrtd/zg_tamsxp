package com.joyintech.tams.invest.basicdata.assetdeclarationcodemaintenance.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.base.intf.IDictCode;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectDto;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.basicdata.assetdeclarationcodemaintenance.dto.AssetDeclarationCodeMaintenanceDto;
import com.joyintech.tams.invest.basicdata.assetdeclarationcodemaintenance.service.AssetDeclarationCodeMaintenanceService;
import com.joyintech.tams.invest.common.dto.common.ExcelValidDto;

@Controller
@PageId("AssetDeclarationCodeMaintenance001")
@RequestMapping("/invest/basicdata/assetdeclarationcodemaintenance/AssetDeclarationCodeMaintenance001")
public class AssetDeclarationCodeMaintenance001Controller extends BaseController {

	/**
	 * 汇率行情service
	 */
	@Autowired
	private AssetDeclarationCodeMaintenanceService assetDeclarationCodeMaintenanceService;

	/**
	 * 初始化的查询条件
	 */
	private static final IDictCode[] DICT_CODES = new DictCode[] {};

	/**
	 * 
	 * 查询页面初始化
	 */
	@Override
	@RequestMapping(PAGE_INIT)
	public String init(Model model) {
		// this.setSelectOptionToModel(model, DICT_CODES);
		List<SelectOptionDto> FinprodTypeTG = pickFinprodTypeTG();
		model.addAttribute("finprodTypeTG", JsonUtils.objectArray2Json(FinprodTypeTG.toArray()));

		return super.init(model);
	}

	/**
	 * 生成资产类型的枚举值 <功能详细描述>
	 * 
	 * @return
	 * @see
	 * @since 1.0
	 */
	private List<SelectOptionDto> pickFinprodTypeTG() {
		List<SelectOptionDto> listFinprodType = new ArrayList<SelectOptionDto>();
		SelectDto selectDto1 = this.getSelectDataFromDataDic(DictCode.金融产品类型_投管用, null);
		for (SelectOptionDto dto : selectDto1.getOptions()) {
			if (DictCodeListExt1.金融产品类型_投管用_债券.getValue().equals(dto.getId())
					|| DictCodeListExt1.金融产品类型_投管用_股票.getValue().equals(dto.getId())
					|| DictCodeListExt1.金融产品类型_投管用_基金.getValue().equals(dto.getId())) {// 债券、股票、基金
				listFinprodType.add(dto);
			}
		}
		return listFinprodType;
	}

	/**
	 * 画面查询方法
	 * 
	 * @return 查询结果集
	 * @throws Exception
	 *             异常
	 */
	@ResponseBody
	@RequestMapping("findPage")
	public Page<AssetDeclarationCodeMaintenanceDto> selectAll() throws Exception {
		Page<AssetDeclarationCodeMaintenanceDto> page = assetDeclarationCodeMaintenanceService
				.selectForPage(RequestUtil.getParameters(getRequest()));
		return page;
	}

	@ResponseBody
	@RequestMapping("getFinprodIdList")
	// modify by zhangrui1 2020-07-25 资产分页查询 start
	/**
	public List<SelectOptionDto> getFinprodIdList() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
	*/
	public Page<SelectOptionDto> getFinprodIdList() throws Exception {
        Map<String, Object> map = RequestUtil.getPageParam();
	// modify by zhangrui1 2020-07-25 资产分页查询 end
		String assetsType = super.getRequest().getParameter("assetsType");
 		String param = super.getRequest().getParameter("search");
		if (StringUtils.isNotEmpty(assetsType)) {
			map.put("assetsType", assetsType);
		}
		if (StringUtils.isNotEmpty(param)) {
			map.put("finprodId", param);
		}
		// modify by zhangrui1 2020-07-25 资产分页查询 start
		/**
		List<SelectOptionDto> temp = assetDeclarationCodeMaintenanceService.getFinprodIds(map);
		*/
        Page<SelectOptionDto> temp = assetDeclarationCodeMaintenanceService.getFinprodIdPage(map);
		// modify by zhangrui1 2020-07-25 资产分页查询 end
		return temp;
	}

	@ResponseBody
	@RequestMapping("getOthOtherType")
	public List<SelectOptionDto> getOthOtherType() {
		List<SelectOptionDto> othOtherType = new ArrayList<SelectOptionDto>();
		String assetsTypes = super.getRequest().getParameter("assetsType");
		List<String> assetsTypeList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(assetsTypes)) {
			for (String assetsType : assetsTypes.split(",")) {
				assetsTypeList.add(assetsType);
			}
		} else {
			assetsTypeList.add(DictCodeListExt1.金融产品类型_投管用_债券.getValue());
			assetsTypeList.add(DictCodeListExt1.金融产品类型_投管用_股票.getValue());
			assetsTypeList.add(DictCodeListExt1.金融产品类型_投管用_基金.getValue());
		}
		SelectDto selectDto1 = this.getSelectDataFromDataDic(DictCode.金融产品其他代码类型, null);

		for (String assetsType : assetsTypeList) {
			for (SelectOptionDto dto : selectDto1.getOptions()) {
				// 债券
				if (DictCodeListExt1.金融产品类型_投管用_债券.getValue().equals(assetsType)) {
					if (
						// 业务类型下拉框删除：回售、标准券出入库、可转债转股选项 fuyongsheng 20211103 edit start
						// DictCodeListExt3.金融产品其他代码类型_回售代码.getValue().equals(dto.getId()) ||
						// DictCodeListExt3.金融产品其他代码类型_质押代码.getValue().equals(dto.getId()) ||
						DictCodeListExt3.金融产品其他代码类型_可转债信用申购代码.getValue().equals(dto.getId())
						// || DictCodeListExt3.金融产品其他代码类型_可转债转股代码.getValue().equals(dto.getId())
						// 业务类型下拉框删除：回售、标准券出入库、可转债转股选项 fuyongsheng 20211103 edit end
						|| DictCodeListExt3.金融产品其他代码类型_配债代码.getValue().equals(dto.getId())) {
						othOtherType.add(dto);
					}
				} else if (DictCodeListExt1.金融产品类型_投管用_股票.getValue().equals(assetsType)) {
					if (DictCodeListExt3.金融产品其他代码类型_配股代码.getValue().equals(dto.getId()) || DictCodeListExt3.金融产品其他代码类型_新股申购代码.getValue().equals(dto.getId())) {// 股票
						othOtherType.add(dto);
					}
				} else if (DictCodeListExt1.金融产品类型_投管用_基金.getValue().equals(assetsType)) {
					if (DictCodeListExt3.金融产品其他代码类型_基金分红设置代码.getValue().equals(dto.getId())) {// 基金
						othOtherType.add(dto);
					}
				}
			}
		}
		return othOtherType;
	}

	public List<SelectOptionDto> allOthOtherType() {
		List<SelectOptionDto> othOtherType = new ArrayList<SelectOptionDto>();
		SelectDto selectDto1 = this.getSelectDataFromDataDic(DictCode.金融产品其他代码类型, null);
		for (SelectOptionDto dto : selectDto1.getOptions()) {
			// 债券
			if (DictCodeListExt3.金融产品其他代码类型_回售代码.getValue().equals(dto.getId())
					|| DictCodeListExt3.金融产品其他代码类型_质押代码.getValue().equals(dto.getId())
					|| DictCodeListExt3.金融产品其他代码类型_可转债信用申购代码.getValue().equals(dto.getId())
					|| DictCodeListExt3.金融产品其他代码类型_可转债转股代码.getValue().equals(dto.getId())
					|| DictCodeListExt3.金融产品其他代码类型_配债代码.getValue().equals(dto.getId())) {
				dto.setParentId(DictCodeListExt1.金融产品类型_投管用_债券.getValue());
				othOtherType.add(dto);
			} else if (DictCodeListExt3.金融产品其他代码类型_配股代码.getValue().equals(dto.getId())
					|| DictCodeListExt3.金融产品其他代码类型_新股申购代码.getValue().equals(dto.getId())) {// 股票
				dto.setParentId(DictCodeListExt1.金融产品类型_投管用_股票.getValue());
				othOtherType.add(dto);
			}
		}
		return othOtherType;
	}

	/**
	 * 保存
	 * 
	 * @param dto
	 *            dto
	 * @return 结果
	 * @throws Exception
	 *             异常
	 */
	@ResponseBody
	@RequestMapping("save")
	public boolean save(@RequestBody AssetDeclarationCodeMaintenanceDto dto) throws Exception {
		return assetDeclarationCodeMaintenanceService.save(dto);
	}

	/**
	 * 保存按钮点击时的check
	 *
	 * @param dto
	 *            dto
	 * @param result
	 *            BindingResult
	 * @return check结果
	 * @see
	 * @since 1.0
	 */
	@ResponseBody
	@RequestMapping("check")
	public AjaxResult check(@RequestBody @Validated({ ValidateGroupEdit.class }) AssetDeclarationCodeMaintenanceDto dto,
			BindingResult result) {
		if (result.getAllErrors().isEmpty()) {
			AjaxResult ar = commonCheck(result, dto.getOperateType());
			return ar;
		}
		return commonCheck(result, dto.getOperateType());
	}

	/**
	 * 导入
	 *
	 * @param fileName
	 *            导入的文件名
	 * @return 处理结果
	 * @throws Exception
	 *             异常
	 */
	@ResponseBody
	@RequestMapping("importFile")
	public List<ExcelValidDto> importData(String fileName) throws Exception {
		return this.assetDeclarationCodeMaintenanceService.importData(fileName);
	}

	/**
	 * 文件导出 <功能详细描述>
	 * 
	 * @return ResponseEntity
	 * @throws Exception
	 *             Exception
	 * @see
	 * @since 1.0
	 */
	@RequestMapping("export")
	public ResponseEntity<byte[]> export() throws Exception {
		return assetDeclarationCodeMaintenanceService.export(this.getPageId(), RequestUtil.getParameters(getRequest()));
	}


	@ResponseBody
	@RequestMapping("getFinprodList")
	public Page<SelectOptionDto> getFinprodList() throws Exception {
		Map<String, Object> map = RequestUtil.getPageParam();
		// modify by zhangrui1 2020-07-25 资产分页查询 end
		String assetsType = super.getRequest().getParameter("assetsType");
		String assetsType1 = super.getRequest().getParameter("assetsType");
		assetsType1 = "";
		String param = super.getRequest().getParameter("search");
		if (StringUtils.isNotEmpty(assetsType)) {
			String[] args = assetsType.split(",");
			String[] temp = new String[args.length];
			for(int i=0;i<args.length;i++){
				if(!StringUtil.equals(args[i],"F17")){
				   temp[i] = args[i];
				}else{
					assetsType1 = "F17";
				}
			}
			assetsType = "";
			for(int i=0;i<temp.length;i++){
				if(StringUtil.isNotEmpty(temp[i])){
					assetsType = assetsType+","+temp[i];
				}
			}
			if(StringUtil.isNotEmpty(assetsType)&&StringUtil.equals(assetsType.substring(1),",")){
				assetsType = assetsType.split(",",1)[1];
			}
		}
		if(StringUtil.isEmpty(super.getRequest().getParameter("assetsType").toString())) {
			assetsType1 = "F17";
			assetsType = "F01,F07,F18";
		}
		map.put("assetsType", assetsType);
		map.put("assetsType1", assetsType1);

		if (StringUtils.isNotEmpty(param)) {
			map.put("finprodId", param);
		}
		// modify by zhangrui1 2020-07-25 资产分页查询 start
		/**
		 List<SelectOptionDto> temp = assetDeclarationCodeMaintenanceService.getFinprodIds(map);
		 */
		Page<SelectOptionDto> temp = assetDeclarationCodeMaintenanceService.getFinprodList(map);
		// modify by zhangrui1 2020-07-25 资产分页查询 end
		return temp;
	}

}
