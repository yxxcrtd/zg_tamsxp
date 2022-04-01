package com.joyintech.tams.invest.basicdata.assetdeclarationcodemaintenance.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.ExcelUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.basicdata.assetdeclarationcodemaintenance.dto.AssetDeclarationCodeMaintenanceDto;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.dto.common.ExcelValidDto;

@Service
public class AssetDeclarationCodeMaintenanceService extends CommonExportService<AssetDeclarationCodeMaintenanceDto> {
	/**
	 * 查询界面询事件
	 * 
	 * @param map
	 *            页面参数
	 * @return 调用微服务
	 * @see
	 * @since 1.0
	 */
	public Page<AssetDeclarationCodeMaintenanceDto> selectForPage(Map<String, Object> map) {
		Page<AssetDeclarationCodeMaintenanceDto> page = restTemplate.postForPage(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/selectForPage", map,
				AssetDeclarationCodeMaintenanceDto.class);
		return page;
	}

	/**
	 * 客户代码联想文本框service层实现
	 *
	 * @param map
	 *            检索参数
	 * @return SuggestDto
	 */
	public List<SelectOptionDto> getFinprodIds(Map<String, Object> map) {
		return restTemplate.postForList(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/getFinprodIds", map,
				SelectOptionDto.class);
	}

    // add by zhangrui1 2020-07-25  资产分页查询 start
    public Page<SelectOptionDto> getFinprodIdPage(Map<String, Object> map) {
        return restTemplate.postForPage(
            ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/getFinprodIdPage", map,
            SelectOptionDto.class);
    }
    // add by zhangrui1 2020-07-25 资产分页查询 end


	public Page<SelectOptionDto> getFinprodList(Map<String, Object> map) {
		return restTemplate.postForPage(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/getFinprodList", map,
				SelectOptionDto.class);
	}

	/**
	 * 根据资产类型获取申报代码类型
	 *
	 * @param map
	 *            检索参数
	 * @return SuggestDto
	 */
	public List<SelectOptionDto> getfinprodType(Map<String, Object> map) {
		return restTemplate.postForList(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/getfinprodType", map,
				SelectOptionDto.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean save(AssetDeclarationCodeMaintenanceDto entity) {
		String action = StringUtil.EMPTY;
		if (entity.getOperateType().equals("delete")) {
			action = "delete";
		} else if (entity.getOperateType().equals("new")) {
			action = "insert";
		} else {
			action = "update";
		}
		restTemplate.postForObject(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/" + action, entity,
				Integer.class);
		return true;
	}

	/**
	 * excel导入功能
	 *
	 * @param flieName
	 *            文件名称
	 * @return 检验list
	 * @throws Exception
	 *             异常
	 * @see
	 * @since 1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<ExcelValidDto> importData(String flieName) throws Exception {
		// 检查文件名是否为空
		if (StringUtil.isNotEmpty(flieName)) {
			// 检查文件名是否包含资产申报代码维护信息导入
			if (flieName.indexOf("申报信息维护") != -1) {
				ExcelUtil excelUtil = ExcelUtil.build(FileNameUtil.getUploadFilePath(flieName), null);
				List<List<String>> dataList = excelUtil.readToList(0, 1, 0);
				// 检查读出信息是否为空
				if (null != dataList && dataList.size() > 0) {
					// 将excel表格中的数据转化为dto
					List<ExcelValidDto> checkResult = checkExcelData(dataList);
					if (checkResult.isEmpty()) {
						List<AssetDeclarationCodeMaintenanceDto> equityList = converToBean(dataList);
						dealAfterCheck(equityList);
					}
					return checkResult;
				} else {
					List<ExcelValidDto> checkResult = new ArrayList<>();
					checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,getMessage("MST_ASSET_DECLARATION_CODE_E_0015")));
					return checkResult;
				}
			} else {
				List<ExcelValidDto> checkResult = new ArrayList<>();
				checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,getMessage("BUSS_COM_E_0002", "申报信息维护")));
				return checkResult;
			}
		}
		return null;
	}

	/**
	 * check后的保存 <功能详细描述>
	 * 
	 * @param equityList
	 *            需要保存的数据
	 * @see
	 * @since 1.0
	 */
	public void dealAfterCheck(List<AssetDeclarationCodeMaintenanceDto> equityList) {
		List<AssetDeclarationCodeMaintenanceDto> listN = new ArrayList<AssetDeclarationCodeMaintenanceDto>();
		List<AssetDeclarationCodeMaintenanceDto> listA = new ArrayList<AssetDeclarationCodeMaintenanceDto>();
		for (int i = 0; i < equityList.size(); i++) {
			AssetDeclarationCodeMaintenanceDto assetDeclarationCodeMaintenanceDto = equityList.get(i);
			AssetDeclarationCodeMaintenanceDto equityInfoCheck = selectByPrimaryKey(assetDeclarationCodeMaintenanceDto);
			if (equityInfoCheck == null) {
				listN.add(assetDeclarationCodeMaintenanceDto);
			} else {
				listA.add(assetDeclarationCodeMaintenanceDto);
			}
		}
		if (listN != null && listN.size() > 0) {
			this.insertBtachEquityInfoByPart(listN);
		}
		if (listA != null && listA.size() > 0) {
			this.updateBtachEquityInfoByPart(listA);
		}
	}

	/**
	 * 导入文件内容检查 <功能详细描述>
	 * 
	 * @param dataList
	 *            导入数据
	 * @return 返回检查结果
	 * @throws ParseException
	 *             抛出异常
	 * @see
	 * @since 1.0
	 */
	private List<ExcelValidDto> checkExcelData(List<List<String>> dataList) throws ParseException {
		List<ExcelValidDto> checkResult = new ArrayList<>();
		List<AssetDeclarationCodeMaintenanceDto> equityList = converToBean(dataList);
		AssetDeclarationCodeMaintenanceDto assetDeclarationCodeMaintenanceDto = null;
		// 检查表中是否有数据
		if (equityList.size() >= 1) {
			for (int i = 0; i < equityList.size(); i++) {
				assetDeclarationCodeMaintenanceDto = equityList.get(i);
				// 检查表内数据每行是否合法
				// modify by dubaojun 2020-07-29 excel导入默认从第2行开始 start
				if (checkExcelRow(i + 2, dataList.get(i)) != null) {
					checkResult.addAll(checkExcelRow(i + 2, dataList.get(i)));
				}
				// modify by dubaojun 2020-07-29 excel导入默认从第2行开始 end
				// 检查表内数据是否有主键重复
				if (equityList.size() >= 2) {
					for (int k = i + 1; k < equityList.size(); k++) {
						if ((StringUtil.isNotBlank(equityList.get(k).getOthCode())
						    && StringUtil.isNotBlank(assetDeclarationCodeMaintenanceDto.getOthCode())
						    && equityList.get(k).getOthCode().equals(assetDeclarationCodeMaintenanceDto.getOthCode()))
								|| (StringUtil.isNotBlank(equityList.get(k).getFinprodId())
								    && StringUtil.isNotBlank(assetDeclarationCodeMaintenanceDto.getFinprodId())
								    && equityList.get(k).getFinprodId().equals(assetDeclarationCodeMaintenanceDto.getFinprodId())
										&& equityList.get(k).getOthCodeType().equals(assetDeclarationCodeMaintenanceDto.getOthCodeType()))) {
							checkResult.add(new ExcelValidDto(dataList.get(i).get(1), String.valueOf(i + 2),getMessage("MST_ASSET_DECLARATION_CODE_E_0012")));
						}
					}
				}
			}
		}
		return checkResult;
	}

	/**
	 * 检查excel是否为空或者传入列是否正确
	 *
	 * @param i
	 *            第几行
	 * @param list
	 *            每一列解析后的List<String>
	 * @return 检查结构
	 * @see
	 * @since 1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<ExcelValidDto> checkExcelRow(int i, List<String> list) {
		List<ExcelValidDto> excelValidDtolist = new ArrayList<ExcelValidDto>();
		// add by dubaojun 2020-07-30 画面资产代码展示为市场代码 start
		String finprodId = null;
		// add by dubaojun 2020-07-30 画面资产代码展示为市场代码 end
		// 删除数据列 SPV 登记编码
		// 列数变为3
		String finprodType2 = new String();
		if (null == list || list.size() != 8) {
			ExcelValidDto dto = new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0011"));
			excelValidDtolist.add(dto);
		}

		// modify by etliu 20211206 start
		// TISHKS-525:【业务参数】【申报信息维护】导入时，1、标识码提示，期望用“资产代码+业务类型”；2、如图所示，配债导入时，资产代码为股票，系统提示“业务类型类型对应资产非股票”，提示不明确
		String validKey = "资产代码: " + list.get(1) + "<br/>业务类型: " + list.get(3);

		if (StringUtils.isEmpty(list.get(1))) {
//			ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0001"));
			ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0001"));
			excelValidDtolist.add(dto);
		} else {
			Map map = new HashMap();
		// modify by dubaojun 2020-07-30 画面资产代码展示为市场代码 start
//			map.put("finprodId", list.get(0));
			map.put("finprodMarketId", list.get(1));
			List<SelectOptionDto> res = getfinprodType(map);
			if (res != null && res.size() > 0) {
				finprodType2 = res.get(0).getText();// 资产类型
				finprodId = res.get(0).getId();
				//modify start
				if(!res.get (0).getParentId ().equals (list.get (2))){
					if(StringUtil.isNotEmpty (list.get (2))){
//						ExcelValidDto dto = new ExcelValidDto(list.get(2), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_YADD_0002"));
						ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_YADD_0002"));
						excelValidDtolist.add(dto);
					}
				}
//				String secid=finprodId.substring(finprodId.indexOf("_"),finprodId.length ());
//				if(secid.equals (list.get (1))){
//					ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0002"));
//					excelValidDtolist.add(dto);
//				}
				//modify end
			} else {
//				ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0002"));
				ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0002"));
				excelValidDtolist.add(dto);
			}


		// modify by dubaojun 2020-07-30 画面资产代码展示为市场代码 end
		}
		if (StringUtils.isEmpty(list.get(3))) {
//			ExcelValidDto dto = new ExcelValidDto(list.get(3), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0003"));
			ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0003"));
			excelValidDtolist.add(dto);
		} else if (StringUtils.isEmpty(getOthCodeTypeByName(list.get(3)))) {
//			ExcelValidDto dto = new ExcelValidDto(list.get(3), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0004"));
			ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0004"));
			excelValidDtolist.add(dto);
		} else {
			if (DictCodeListExt1.金融产品类型_投管用_债券.getValue().equals(finprodType2)) {
				if (/*!DictCodeListExt3.金融产品其他代码类型_回售代码.getCodeName().equals(list.get(3))
						&& !DictCodeListExt3.金融产品其他代码类型_质押代码.getCodeName().equals(list.get(3))
						&& */!DictCodeListExt3.金融产品其他代码类型_可转债信用申购代码.getCodeName().equals(list.get(3))
						/*&& !DictCodeListExt3.金融产品其他代码类型_可转债转股代码.getCodeName().equals(list.get(3))*/
						&& !DictCodeListExt3.金融产品其他代码类型_配债代码.getCodeName().equals(list.get(3))) {
//					ExcelValidDto dto = new ExcelValidDto(list.get(3), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0013"));
					ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),
							this.getMessage("MST_ASSET_DECLARATION_CODE_E_0016", DictCodeListExt1.金融产品类型_投管用_债券.getCodeName(), list.get(3)));
					excelValidDtolist.add(dto);
				}
			} else if (DictCodeListExt1.金融产品类型_投管用_股票.getValue().equals(finprodType2)) {
				if (!DictCodeListExt3.金融产品其他代码类型_配股代码.getCodeName().equals(list.get(3))
						&& !DictCodeListExt3.金融产品其他代码类型_新股申购代码.getCodeName().equals(list.get(3))) {
//					ExcelValidDto dto = new ExcelValidDto(list.get(3), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0014"));
					ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),
							this.getMessage("MST_ASSET_DECLARATION_CODE_E_0016", DictCodeListExt1.金融产品类型_投管用_股票.getCodeName(), list.get(3)));
					excelValidDtolist.add(dto);
				}
			} else if (DictCodeListExt1.金融产品类型_投管用_基金.getValue().equals(finprodType2)) {
				if (!DictCodeListExt3.金融产品其他代码类型_基金分红设置代码.getCodeName().equals(list.get(3))) {
					ExcelValidDto dto = new ExcelValidDto(list.get(3), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0016"));
					excelValidDtolist.add(dto);
				}
			}
		}
		if (StringUtils.isEmpty(list.get(4))) {
//			ExcelValidDto dto = new ExcelValidDto(list.get(4), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0005"));
			ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0005"));
			excelValidDtolist.add(dto);
		} else if (list.get(4).length() > 32) {
//			ExcelValidDto dto = new ExcelValidDto(list.get(4), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0006"));
			ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0006"));
			excelValidDtolist.add(dto);
		}

		//add start
		if(StringUtils.isNotEmpty(list.get(5))){
			if(DictCodeListExt3.金融产品其他代码类型_质押代码.getCodeName().equals(list.get(3))){
//				ExcelValidDto dto = new ExcelValidDto(list.get(5), String.valueOf(i),this.getMessage ("MST_ASSET_DECLARATION_CODE_E_YADD_001_01"));
				ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage ("MST_ASSET_DECLARATION_CODE_E_YADD_001_01"));
				excelValidDtolist.add(dto);
			}
		}else if(StringUtils.isEmpty(list.get(5))){
			if(!DictCodeListExt3.金融产品其他代码类型_质押代码.getCodeName().equals(list.get(3))) {
//				ExcelValidDto dto = new ExcelValidDto (list.get (5), String.valueOf (i), this.getMessage ("MST_ASSET_DECLARATION_CODE_E_YADD_001"));
				ExcelValidDto dto = new ExcelValidDto (validKey, String.valueOf (i), this.getMessage ("MST_ASSET_DECLARATION_CODE_E_YADD_001"));
				excelValidDtolist.add (dto);
			}
		}

		if(StringUtils.isNotEmpty(list.get(6))){
			if(DictCodeListExt3.金融产品其他代码类型_质押代码.getCodeName().equals(list.get(3))){
//				ExcelValidDto dto = new ExcelValidDto(list.get(6), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_YADD_002_01"));
				ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_YADD_002_01"));
				excelValidDtolist.add(dto);
			}
		}else if(StringUtils.isEmpty(list.get(6))){
			if(!DictCodeListExt3.金融产品其他代码类型_质押代码.getCodeName().equals(list.get(3))) {
//				ExcelValidDto dto = new ExcelValidDto (list.get (6), String.valueOf (i), this.getMessage ("MST_ASSET_DECLARATION_CODE_E_YADD_002"));
				ExcelValidDto dto = new ExcelValidDto (validKey, String.valueOf (i), this.getMessage ("MST_ASSET_DECLARATION_CODE_E_YADD_002"));
				excelValidDtolist.add (dto);
			}
		}

		if(StringUtils.isNotEmpty(list.get(7))){
			if(DictCodeListExt3.金融产品其他代码类型_质押代码.getCodeName().equals(list.get(3))){
//				ExcelValidDto dto = new ExcelValidDto(list.get(7), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_YADD_003_01"));
				ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_YADD_003_01"));
				excelValidDtolist.add(dto);
			}
		}else if(StringUtils.isEmpty(list.get(7))){
			if(!DictCodeListExt3.金融产品其他代码类型_质押代码.getCodeName().equals(list.get(3))) {
//				ExcelValidDto dto = new ExcelValidDto (list.get (7), String.valueOf (i), this.getMessage ("MST_ASSET_DECLARATION_CODE_E_YADD_003"));
				ExcelValidDto dto = new ExcelValidDto (validKey, String.valueOf (i), this.getMessage ("MST_ASSET_DECLARATION_CODE_E_YADD_003"));
				excelValidDtolist.add (dto);
			}
		}
		//add end

		AssetDeclarationCodeMaintenanceDto assetDeclarationCodeMaintenanceDto = new AssetDeclarationCodeMaintenanceDto();
		// modify by dubaojun 2020-07-30 画面资产代码展示为市场代码 start
		assetDeclarationCodeMaintenanceDto.setFinprodId(finprodId);
		// modify by dubaojun 2020-07-30 画面资产代码展示为市场代码 end
		assetDeclarationCodeMaintenanceDto.setOthCodeType(getOthCodeTypeByName(list.get(3)));
		assetDeclarationCodeMaintenanceDto.setOthCode(list.get(4));
		AssetDeclarationCodeMaintenanceDto equityInfoCheck1 = selectByPrimaryKey(assetDeclarationCodeMaintenanceDto);
		AssetDeclarationCodeMaintenanceDto equityInfoCheck2 = selectByUniqueKey(assetDeclarationCodeMaintenanceDto);
		if (equityInfoCheck1 != null) {
//			ExcelValidDto dto = new ExcelValidDto(list.get(3), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0007"));
			ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0007"));
			excelValidDtolist.add(dto);
		}
		if (equityInfoCheck2 != null) {
//			ExcelValidDto dto = new ExcelValidDto(list.get(3), String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0008"));
			ExcelValidDto dto = new ExcelValidDto(validKey, String.valueOf(i),this.getMessage("MST_ASSET_DECLARATION_CODE_E_0008"));
			excelValidDtolist.add(dto);
		}
		// modify by etliu 20211206 end
		return excelValidDtolist;
	}

	/**
	 * 主键验证
	 * 
	 * @param dto
	 *            页面参数
	 * @return 调用微服务
	 * @see
	 * @since 1.0
	 */
	public AssetDeclarationCodeMaintenanceDto selectByPrimaryKey(AssetDeclarationCodeMaintenanceDto dto) {
		AssetDeclarationCodeMaintenanceDto assetDeclarationCodeMaintenanceDto = restTemplate.postForObject(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/selectByPrimaryKey", dto,
				AssetDeclarationCodeMaintenanceDto.class);
		return assetDeclarationCodeMaintenanceDto;
	}

	/**
	 * 唯一验证
	 * 
	 * @param dto
	 *            页面参数
	 * @return 调用微服务
	 * @see
	 * @since 1.0
	 */
	public AssetDeclarationCodeMaintenanceDto selectByUniqueKey(AssetDeclarationCodeMaintenanceDto dto) {
		AssetDeclarationCodeMaintenanceDto assetDeclarationCodeMaintenanceDto = restTemplate.postForObject(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/selectByUniqueKey", dto,
				AssetDeclarationCodeMaintenanceDto.class);
		return assetDeclarationCodeMaintenanceDto;
	}

	/**
	 * list转换为Bean
	 *
	 * @param dataList
	 *            excel解析后的list
	 * @return List<mstMarketCalendarInfo001Dto>
	 * @throws ParseException
	 *             抛出异常
	 * @see
	 * @since 1.0
	 */
	private List<AssetDeclarationCodeMaintenanceDto> converToBean(List<List<String>> dataList) throws ParseException {
		// 返回的list定义
		List<AssetDeclarationCodeMaintenanceDto> returnInfoList = new ArrayList<AssetDeclarationCodeMaintenanceDto>();
		AssetDeclarationCodeMaintenanceDto assetDeclarationCodeMaintenanceDto = null;
		for (int i = 0; i < dataList.size(); i++) {
			List<String> list = dataList.get(i);
			assetDeclarationCodeMaintenanceDto = new AssetDeclarationCodeMaintenanceDto();
		// modify by dubaojun 2020-07-30 画面资产代码展示为市场代码 start
			Map<String, Object> map = new HashMap<>();
            map.put("finprodMarketId", list.get(1));
            List<SelectOptionDto> res = getfinprodType(map);
            if (res != null && res.size() > 0) {
                assetDeclarationCodeMaintenanceDto.setFinprodId(res.get(0).getId());
            }

			assetDeclarationCodeMaintenanceDto.setFinprodName (list.get(2));
			assetDeclarationCodeMaintenanceDto.setVdateStart (list.get(5));
			assetDeclarationCodeMaintenanceDto.setVdateEnd (list.get(6));
			assetDeclarationCodeMaintenanceDto.setAssetsPrice (list.get(7));
		// modify by dubaojun 2020-07-30 画面资产代码展示为市场代码 end
			assetDeclarationCodeMaintenanceDto.setOthCodeType(getOthCodeTypeByName(list.get(3)));
			assetDeclarationCodeMaintenanceDto.setOthCode(list.get(4));
			returnInfoList.add(assetDeclarationCodeMaintenanceDto);
		}
		return returnInfoList;
	}

	/**
	 * 根据文本输入转换为字典代码
	 * 
	 * @param othCodeName
	 * @return
	 */
	private String getOthCodeTypeByName(String othCodeName) {
		String OthCodeType = new String();
		// add by etliu 20211207 start
		// 交易所债券非交易类业务迁移
		/*if (DictCodeListExt3.金融产品其他代码类型_回售代码.getCodeName().equals(othCodeName)) {
			OthCodeType = DictCodeListExt3.金融产品其他代码类型_回售代码.getValue();
		} else if (DictCodeListExt3.金融产品其他代码类型_质押代码.getCodeName().equals(othCodeName)) {
			OthCodeType = DictCodeListExt3.金融产品其他代码类型_质押代码.getValue();
		} else */if (DictCodeListExt3.金融产品其他代码类型_可转债信用申购代码.getCodeName().equals(othCodeName)) {
			OthCodeType = DictCodeListExt3.金融产品其他代码类型_可转债信用申购代码.getValue();
		}/* else if (DictCodeListExt3.金融产品其他代码类型_可转债转股代码.getCodeName().equals(othCodeName)) {
			OthCodeType = DictCodeListExt3.金融产品其他代码类型_可转债转股代码.getValue();
		} */else if (DictCodeListExt3.金融产品其他代码类型_配债代码.getCodeName().equals(othCodeName)) {
			OthCodeType = DictCodeListExt3.金融产品其他代码类型_配债代码.getValue();
		} else if (DictCodeListExt3.金融产品其他代码类型_配股代码.getCodeName().equals(othCodeName)) {
			OthCodeType = DictCodeListExt3.金融产品其他代码类型_配股代码.getValue();
		} else if (DictCodeListExt3.金融产品其他代码类型_新股申购代码.getCodeName().equals(othCodeName)) {
			OthCodeType = DictCodeListExt3.金融产品其他代码类型_新股申购代码.getValue();
		} else if (DictCodeListExt3.金融产品其他代码类型_基金分红设置代码.getCodeName().equals(othCodeName)) {
			OthCodeType = DictCodeListExt3.金融产品其他代码类型_基金分红设置代码.getValue();
		}
		// add by etliu 20211207 end
		return OthCodeType;
	}

	/**
	 * 分批次执行insertBtachEquityInfoByPart
	 *
	 * @param dataList
	 *            数据
	 * @see
	 * @since 1.0
	 */
	private void insertBtachEquityInfoByPart(List<AssetDeclarationCodeMaintenanceDto> dataList) {
		if (StringUtil.isEmptyList(dataList)) {
			return;
		} else {

			int maxDataCountOneTime = 100;
			int dataCount = dataList.size();
			if (dataCount <= maxDataCountOneTime) {
				insertBtachEquityInfo(dataList);
			} else {
				List<AssetDeclarationCodeMaintenanceDto> newDataList = new ArrayList<AssetDeclarationCodeMaintenanceDto>();
				newDataList.addAll(dataList);

				// 分批处理
				int part = dataCount / maxDataCountOneTime; // 分批数
				for (int i = 0; i < part; i++) {
					insertBtachEquityInfo(newDataList.subList(0, maxDataCountOneTime));
					newDataList.subList(0, maxDataCountOneTime).clear();
				}
				// 最后剩下的数据
				if (CollectionUtils.isNotEmpty(newDataList)) {
					insertBtachEquityInfo(newDataList);
				}
			}
		}
	}

	/**
	 * 分批次执行updateBtachEquityInfo
	 *
	 * @param dataList
	 *            数据
	 * @see
	 * @since 1.0
	 */
	private void updateBtachEquityInfoByPart(List<AssetDeclarationCodeMaintenanceDto> dataList) {
		if (StringUtil.isEmptyList(dataList)) {
			return;
		}

		int maxDataCountOneTime = 20;
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			updateBtachEquityInfo(dataList);
		} else {

			List<AssetDeclarationCodeMaintenanceDto> newDataList = new ArrayList<AssetDeclarationCodeMaintenanceDto>();
			newDataList.addAll(dataList);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				updateBtachEquityInfo(newDataList.subList(0, maxDataCountOneTime));
				newDataList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newDataList)) {
				updateBtachEquityInfo(newDataList);
			}
		}
	}

	/**
	 * 插入数据 <功能详细描述>
	 * 
	 * @param list
	 *            参数
	 * @see
	 * @since 1.0
	 */
	private void insertBtachEquityInfo(List<AssetDeclarationCodeMaintenanceDto> list) {
		restTemplate.postForObject(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/insertBatch", list,
				Integer.class);
	}

	/**
	 * 更新数据 <功能详细描述>
	 * 
	 * @param list
	 *            参数
	 * @see
	 * @since 1.0
	 */
	private void updateBtachEquityInfo(List<AssetDeclarationCodeMaintenanceDto> list) {
		restTemplate.postForObject(
				ServiceApplication.BASICDATA_SERVICE_NAME + "/AssetDeclarationCodeMaintenance/updateBatch", list,
				Integer.class);
	}

	/**
	 * 文件导出
	 */
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		exportParam.config("/com/joyintech/tams/invest/basicdata/assetdeclarationcodemaintenance/conf/export_conf.xml")
				.dataSource(DataSourceType.MASERT);
	}

	@Override
	public Page<AssetDeclarationCodeMaintenanceDto> selectPageData(Map<String, Object> selectParam) {
		return selectForPage(selectParam);
	}
}
