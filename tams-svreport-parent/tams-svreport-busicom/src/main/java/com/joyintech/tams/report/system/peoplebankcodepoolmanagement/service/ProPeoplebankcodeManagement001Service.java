package com.joyintech.tams.report.system.peoplebankcodepoolmanagement.service;

import java.util.*;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.svreport.seq.dao.SysSeqMapper;
import com.joyintech.tams.common.svreport.seq.dto.SysSeqModel;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.system.peoplebankcodepoolmanagement.dao.ProPeoplebankcodeManagement001DtoMapper;
import com.joyintech.tams.report.system.peoplebankcodepoolmanagement.dto.ProPeoplebankcodeManagement001Dto;
import com.joyintech.tams.report.system.proregistcodemanage.dto.ProRegistCodeManage001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;

/**
 * 人行产品资产池代码管理Service
 *
 * @author kx
 *
 */
@Service
public class ProPeoplebankcodeManagement001Service extends CommonExportService<ProPeoplebankcodeManagement001Dto> {

	@Autowired
	private ProPeoplebankcodeManagement001DtoMapper peoplebankcodeManagement001DtoMapper;

	@Autowired
	private SysSeqMapper sysSeqMapper;

	/**
	 * 年份前两位
	 */
	public static final String TOP_TWO_NUM_OF_YEAR = "20";

	/**
	 * 查询内部产品代码下拉框
	 *
	 * @param param
	 *            参数
	 * @return 结果list
	 * @see
	 * @since 1.0
	 */
	public List<SelectOptionDto> queryInsideProdIdList(@RequestBody Map<String, Object> param) {
		String statusEffective = DictCodeListExt1.维护数据状态_有效.getValue();
		param.put("statusEffective", statusEffective);
		return peoplebankcodeManagement001DtoMapper.queryInsideProdIdList(param);
	}

	/**
	 * 查询列表
	 *
	 * @param param
	 * @return
	 */
	public Page<ProPeoplebankcodeManagement001Dto> searchForPage(Map<String, Object> param) {
		String codeTypeProduct = DictCodeListExt3.代码类型_产品.getValue();
		String codeTypeAssets = DictCodeListExt3.代码类型_资产.getValue();
		String statusEffective = DictCodeListExt1.维护数据状态_有效.getValue();
		param.put("codeTypeProduct", codeTypeProduct);
		param.put("codeTypeAssets", codeTypeAssets);
		param.put("statusEffective", statusEffective);
		String insideId = (String) param.get("insideProdId");
		param.put("insideIdList", StringUtil.splitToList(GlobalConstant.COMMA, insideId));
		param.put("pbcProjectCode", (String) param.get("pbcProjectCode"));
		param.put("pbcAssetPoolCode", (String) param.get("pbcAssetPoolCode"));
		int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
		int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
		Page<ProPeoplebankcodeManagement001Dto> page = super.queryForPage(pagenum, legth,
				() -> peoplebankcodeManagement001DtoMapper.queryForPage(param));
		List<ProPeoplebankcodeManagement001Dto>  list=page.getRoot();
		page.setRoot(getList(list));
//		page.setRecords(getList(list).size());
		return page;
	}
	/*
	 * 根据内部产品合并列表
	 * List<ProPeoplebankcodeManagement001Dto>  list
	 *
	 */
	public  List<ProPeoplebankcodeManagement001Dto>  getList(List<ProPeoplebankcodeManagement001Dto>  list){

		//定义map
		Map<String,ProPeoplebankcodeManagement001Dto> map=new  LinkedHashMap<String,ProPeoplebankcodeManagement001Dto>();

		for(ProPeoplebankcodeManagement001Dto  dto:list){
			//将list存放在map中
			map.put(dto.getInsideProdId(), dto);
		}
		//循环处理集合塞入map中
		for(ProPeoplebankcodeManagement001Dto  dto:list){
			ProPeoplebankcodeManagement001Dto  mapDto=map.get(dto.getInsideProdId());
			if(dto.getPbcProjectCodeExternal()!=null){
				//人行产品代码(外部)
				mapDto.setPbcProjectCodeExternal(dto.getPbcProjectCodeExternal());
			}
			if(dto.getPbcAssetPoolCodeExternal()!=null){
				//人行资产池代码(外部)
				mapDto.setPbcAssetPoolCodeExternal(dto.getPbcAssetPoolCodeExternal());
			}
			if(dto.getPbcProjectCodeSystem()!=null){
				//人行产品代码(系统)
				mapDto.setPbcProjectCodeSystem(dto.getPbcProjectCodeSystem());
			}
			if(dto.getPbcAssetPoolCodeSystem()!=null){
				//人行资产池代码(系统)
				mapDto.setPbcAssetPoolCodeSystem(dto.getPbcAssetPoolCodeSystem());
			}
			map.put(dto.getInsideProdId(), mapDto);
		}

		Set<String>  stset=map.keySet();
		List<ProPeoplebankcodeManagement001Dto>  clist=new  ArrayList<ProPeoplebankcodeManagement001Dto>();
		for(String  key:stset){
			clist.add(map.get(key));
		}
		return clist;
	}

	/**
	 * 导出功能需要导出查询条件的所有内容
	 *
	 * @param param
	 *            画面查询参数
	 * @return 查询结果列表
	 */
	public List<ProPeoplebankcodeManagement001Dto> queryReportList(Map<String, Object> param) {
		String codeTypeProduct = DictCodeListExt3.代码类型_产品.getValue();
		String codeTypeAssets = DictCodeListExt3.代码类型_资产.getValue();
		String statusEffective = DictCodeListExt1.维护数据状态_有效.getValue();
		param.put("codeTypeProduct", codeTypeProduct);
		param.put("codeTypeAssets", codeTypeAssets);
		param.put("statusEffective", statusEffective);
		String insideId = (String) param.get("insideProdId");
		param.put("insideIdList", StringUtil.splitToList(GlobalConstant.COMMA, insideId));
		param.put("pbcProjectCode", (String) param.get("pbcProjectCode"));
		param.put("pbcAssetPoolCode", (String) param.get("pbcAssetPoolCode"));
		String keys = (String) param.get("keys");
		param.put("keys", StringUtil.splitToList(GlobalConstant.COMMA, keys));
		return peoplebankcodeManagement001DtoMapper.queryReportList(param);
	}

	/**
	 * 导入文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
	 *
	 * @param dataList
	 *            导入的文件名名
	 * @return 检查结果
	 * @see
	 * @since 1.0
	 */
	public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList, String orgCode, String deptCode) {
		AjaxResult webResult = new AjaxResult();
		String statusEffective = DictCodeListExt1.维护数据状态_有效.getValue();
		if (!StringUtil.isEmptyList(dataList)) {
			if (CollectionUtils.isNotEmpty(dataList)) {
				List<ProPeoplebankcodeManagement001Dto> data = new ArrayList<ProPeoplebankcodeManagement001Dto>();
				List<ProPeoplebankcodeManagement001Dto> entities = convertToBean(dataList);

				for(ProPeoplebankcodeManagement001Dto dto : entities){
					if(!StringUtil.isNotEmpty(dto.getPbcCode())){
						webResult.setMessage("人行产品代码不能为空");
						webResult.setMessageType(MessageType.错误消息);
						webResult.setStatuCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						return webResult;
					} else if(dto.getPbcCode().length()>15){
						webResult.setMessage("人行产品代码长度不得大于15");
						webResult.setMessageType(MessageType.错误消息);
						webResult.setStatuCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						return webResult;
					}
					//校验内部产品代码在产品基本信息表中是否存在
					ProPeoplebankcodeManagement001Dto record = new ProPeoplebankcodeManagement001Dto();
					record.setInsideProdId(dto.getInsideProdId());
					record.setCodeType(dto.getCodeType());
					record.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
					ProPeoplebankcodeManagement001Dto proDto = peoplebankcodeManagement001DtoMapper.CheckProdExist(record);
					if (proDto == null) {
						webResult.setMessage("内部产品代码"+dto.getInsideProdId()+"在产品基本信息表中不存在");
						webResult.setMessageType(MessageType.错误消息);
						webResult.setStatuCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						return webResult;
					}
				}

				if (entities.size() > 100) {
					List<ProPeoplebankcodeManagement001Dto> newDataList = new ArrayList<ProPeoplebankcodeManagement001Dto>();
					newDataList.addAll(entities);
					int part = entities.size() / 1000; // 分批数
					for (int i = 0; i < part; i++) {
						data.addAll(peoplebankcodeManagement001DtoMapper
								.getProPeopleBankExcelList(newDataList.subList(0, 1000), statusEffective));
						newDataList.subList(0, 1000).clear();
					}
					// 最后剩下的数据
					if (CollectionUtils.isNotEmpty(newDataList)) {
						data.addAll(peoplebankcodeManagement001DtoMapper.getProPeopleBankExcelList(newDataList,
								statusEffective));
					}
				} else {
					data = peoplebankcodeManagement001DtoMapper.getProPeopleBankExcelList(entities, statusEffective);
				}
				if (data != null && data.size() > 0) {
					Map<String, List<ProPeoplebankcodeManagement001Dto>> equityInfo001Map = entities.stream()
							.collect(JoyinCollectors.groupingBy(x -> x.getInsideProdId()+"|"+x.getCodeType()));
					// Collections.sort(data, Comparator.comparing(EquityInfo001Dto::getExcelNum));
					// 将序号放入list中
					List<String> excelNumList = new ArrayList<String>();
					List<Integer> excelNumIntList = new ArrayList<Integer>();
					for (ProPeoplebankcodeManagement001Dto dto : data) {
						List<ProPeoplebankcodeManagement001Dto> listTemp = new ArrayList<>();
						listTemp = equityInfo001Map.get(dto.getInsideProdId()+"|"+dto.getCodeType());
						// excelNumList.add(listTemp.get(0).getExcelNum());
						excelNumIntList.add(Integer.valueOf(listTemp.get(0).getExcelNum()));
					}
					// 将list进行排序，并转化为string
					// Collections.sort(excelNumList);
					Collections.sort(excelNumIntList);
					for (Integer rowNo : excelNumIntList) {
						excelNumList.add(String.valueOf(rowNo));
					}
					// String messages = StringUtil.joinString(excelNumList, GlobalConstant.COMMA);
					webResult.setMessage(this.getMessage("CHB_ISCOVER_EQU",
							StringUtil.joinString(excelNumList, GlobalConstant.COMMA)), MessageType.确认消息);
				} else {
					// 防止在进行导入前check时进行了两次的弹框
					webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
				}
			} else {
				// 防止在进行导入前check时进行了两次的弹框
				webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
			}
		}
		return webResult;
	}

	/**
	 * 导入文件
	 * <p>
	 * 解析excel文件并进行验证
	 * </p>
	 *
	 * @param dataList
	 *            导入的文件名名
	 * @return 导入结果
	 */
	public List<ExcelValidDto> importData(List<List<String>> dataList, List<List<String>> tempList, String orgCode,
										  String deptCode) {
		if (!StringUtil.isEmptyList(dataList)) {
			List<String> headList = new ArrayList<>(3);
			//支持两个表头
			headList.add("内部产品代码/内部资产代码");
			headList.add("代码类型");
			headList.add("人行代码");
			List<ExcelValidDto> tempCheckResult = this.checkExcelTemp(tempList, headList);
			if (!StringUtil.isEmptyList(tempCheckResult)) {
				return tempCheckResult;
			}
			if (null != dataList) {
				List<ProPeoplebankcodeManagement001Dto> entities = new ArrayList<>(dataList.size());
				Map<String, String> box = new HashMap<String, String>();
				List<ExcelValidDto> checkResult = convertToEntity(dataList, entities, box, orgCode);
				// 保存本次导入的序号最大值 key为资产/产品-年份， key为对应最大序号
				Map<String, Long> seqMap = new HashMap<>(1 << 1);
				if (checkResult.isEmpty()) {
					entities.forEach(item -> {
						ProPeoplebankcodeManagement001Dto record = new ProPeoplebankcodeManagement001Dto();
						BeanUtil.copyPropertiesIgnoreNull(record, item);
						record.setOrgCode(CommonUtil.getOrgCode());
						record.setCreateUser(CommonUtil.getUserName());
						record.setCreateDept(null);
						record.setCreateTime(new Date());
						record.setUpdateUser(CommonUtil.getUserName());
						record.setUpdateTime(new Date());
						record.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
						// 产品要素维护管理中产品代码不存在 存在则更新数据
						ProPeoplebankcodeManagement001Dto dto = peoplebankcodeManagement001DtoMapper.selectByPrimaryKey(record);
						if (dto != null) {
							peoplebankcodeManagement001DtoMapper.updateByPrimaryKeySelective(record);
						} else {
							// 不存在插入新数据
							peoplebankcodeManagement001DtoMapper.insertSelective(record);
						}
						String insideProdId = record.getInsideProdId();
						String codeType = record.getCodeType();
						boolean isStandardCode = checkInsideProdId(insideProdId, codeType);
						if (isStandardCode) {
							StringBuilder seqKeyBuilder = new StringBuilder();
							if (StringUtil.equals(DictCodeListExt3.代码类型_产品.getValue(), codeType)) {
								seqKeyBuilder.append("PBCPRDBASEINFO-");
							} else {
								seqKeyBuilder.append("PBCPRDACCOUNT-");
							}
							seqKeyBuilder.append(TOP_TWO_NUM_OF_YEAR);
							seqKeyBuilder.append(insideProdId.substring(7, 9));
							String seqKey = seqKeyBuilder.toString();
							Long seqNum = Long.parseLong(insideProdId.substring(9, 14));
							if (seqMap.get(seqKey) == null || seqNum > seqMap.get(seqKey)) {
								seqMap.put(seqKey, seqNum);
							}
						}

					});
					// 更新序号
					updateSysSeq(seqMap);
				}
				return checkResult;
			}

		}
		return null;
	}

	/**
	 * excel数据转为java对象
	 * <p>
	 * 将excel导入的数据，按行转为ProRegistCodeManage001Dto对象，同时验证各字段值
	 * </p>
	 *
	 * @param dataList
	 *            excel导入的数据
	 * @return 返回list
	 */
	private List<ProPeoplebankcodeManagement001Dto> convertToBean(List<List<String>> dataList) {
		List<ProPeoplebankcodeManagement001Dto> entities = new ArrayList<ProPeoplebankcodeManagement001Dto>();
		for (int i = 0; i < dataList.size(); i++) {
			List<String> list = dataList.get(i);
			ProPeoplebankcodeManagement001Dto entity = new ProPeoplebankcodeManagement001Dto();
			entity.setInsideProdId(list.get(0));
			if (StringUtils.equals(list.get(1), DictCodeListExt3.代码类型_产品.getCodeName())) {
				entity.setCodeType(DictCodeListExt3.代码类型_产品.getValue());
			} else {
				entity.setCodeType(DictCodeListExt3.代码类型_资产.getValue());
			}
			entity.setInsideProdIdAndCodeType(entity.getInsideProdId() + "|" + entity.getCodeType());
			entity.setPbcCode(list.get(2));
			entity.setExcelNum(String.valueOf(i+2));
			entities.add(entity);
		}
		return entities;
	}

	/**
	 * excel数据转为java对象
	 * <p>
	 * 将excel导入的数据，按行转为ProRegistCodeManage001Dto对象，同时验证各字段值
	 * </p>
	 *
	 * @param dataList
	 *            excel导入的数据
	 * @param entities
	 *            转化后的列表
	 * @param box
	 * @return 验证信息
	 */
	private List<ExcelValidDto> convertToEntity(List<List<String>> dataList,
												List<ProPeoplebankcodeManagement001Dto> entities, Map<String, String> box, String orgCode) {
		List<ExcelValidDto> checkResult = new ArrayList<>();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (int i = 0; i < dataList.size(); i++) {
			List<String> list = dataList.get(i);
			if (null == list || list.size() != 3) {
				checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
						this.getMessage("REPORT_COMMON_FILE_E_0001", "文件内容")));
				continue;
			}
			for (int j = i + 1; j < dataList.size(); j++) {
				if (StringUtils.equals(dataList.get(i).get(2), dataList.get(j).get(2))) {
					checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
							"第" + (i + 1) + "行"+"人行代码与" + "第" + (j + 1) + "行人行代码相同！"));
					//buffer.append("第" + (i + 1) + "行"+"产品代码与" + "第" + (j + 1) + "行产品代码相同！");
				}
			}
			ProPeoplebankcodeManagement001Dto entity = new ProPeoplebankcodeManagement001Dto();
			// 验证消息
			List<String> message = new ArrayList<>();
			// 产品代码
			String error0 = checkCode2(list.get(0), "内部产品代码", 50, true);
			// 为空判断
			if (StringUtil.isEmpty(list.get(0))) {
				message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "内部产品代码"));
			} else {
				entity.setInsideProdId(list.get(0));
			}
			// 为空判断
			if (StringUtil.isEmpty(list.get(1))) {
				message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "代码类型"));
			} else {
				if (StringUtils.equals(list.get(1), DictCodeListExt3.代码类型_产品.getCodeName())) {
					entity.setCodeType(DictCodeListExt3.代码类型_产品.getValue());
				} else {
					entity.setCodeType(DictCodeListExt3.代码类型_资产.getValue());
				}
			}
			if (StringUtil.isNotEmpty(error0)) {
				message.add(error0);
			} else {
				entity.setInsideProdId(list.get(0));
				Map<String, Object> map = new HashMap<>();
				map.put("prodid", entity.getInsideProdId());
				map.put("codeType", entity.getCodeType());
			}
			// 产品名称
			String error1 = checkCode2(list.get(2), "人行代码", 50, false);
			if (StringUtil.isNotEmpty(error1)) {
				message.add(error1);
			} else {
				entity.setPbcCode(list.get(2));
				List<ProPeoplebankcodeManagement001Dto> dto = peoplebankcodeManagement001DtoMapper.selectByPbcCode(list.get(2));
				if (CollectionUtils.isNotEmpty(dto)){
					for (ProPeoplebankcodeManagement001Dto management001Dto : dto) {
						if (!StringUtil.equals(entity.getInsideProdId(),management001Dto.getInsideProdId())){
							message.add("人行代码"+list.get(2)+"已被使用！");
							break;
						}
					}
				}
			}
//			String error2 = checkCode(list.get(1), "内部产品代码", 50, true);
//			if (StringUtil.isNotEmpty(error2)) {
//				message.add(error2);
//			} else {
//				entity.setInsideProdId(list.get(0));
//			}
			// 去除Excel重复数据
			String insideProdIdAndCodeType = entity.getInsideProdId() + "|"
					+ entity.getCodeType();
			if (StringUtils.isNotBlank(insideProdIdAndCodeType)) {
				String value = hashMap.get(insideProdIdAndCodeType);
				if (StringUtils.isBlank(value)) {
					// 如果value是空的 说明是第一次取到
					hashMap.put(insideProdIdAndCodeType, insideProdIdAndCodeType);
				} else {
					message.add("有重复的内部产品代码和代码类型");
				}
			}
			if (CollectionUtils.isNotEmpty(message)) {
				checkResult.add(new ExcelValidDto(entity.getInsideProdId(), String.valueOf(i + 1),
						StringUtil.joinString(message, GlobalConstant.COMMA)));
			} else {
				entities.add(entity);
			}
		}
		return checkResult;
	}

	/**
	 * 判断代码是否合法，返回错误信息
	 *
	 * @param code
	 *            代码
	 * @param length
	 *            长度
	 * @param cloumnTitle
	 *            列名
	 * @param isRequired
	 *            是否必须
	 * @return 错误信息
	 */
	private String checkCode2(String code, String cloumnTitle, int length, boolean isRequired) {
		// 必须验证
		if (StringUtil.isEmpty(code)) {
			if (isRequired) {
				return this.getMessage("REPORT_COMMON_E_0001", cloumnTitle);
			}
			return null;
		}
		// 长度验证
		if (CommonUtil.getStringLen(code) > length) {
			return this.getMessage("REPORT_COMMON_E_0002", cloumnTitle, length);
		}
		return null;
	}

	/**
	 * 判断代码是否合法，返回错误信息
	 *
	 * @param code
	 *            代码
	 * @param length
	 *            长度
	 * @param cloumnTitle
	 *            列名
	 * @param isRequired
	 *            是否必须
	 * @return 错误信息
	 */
	private String checkCode(String code, String cloumnTitle, int length, boolean isRequired) {
		// 必须验证
		if (StringUtil.isEmpty(code)) {
			if (isRequired) {
				return this.getMessage("REPORT_COMMON_E_0001", cloumnTitle);
			}
			return null;
		}
		// 属性验证
		if (!CommonUtil.isEnglish(code)) {
			return this.getMessage("REPORT_COMMON_E_0004", cloumnTitle);
		}
		// 长度验证
		if (code.length() > length) {
			return this.getMessage("REPORT_COMMON_E_0002", cloumnTitle, length);
		}
		return null;
	}



	/**
	 *
	 * @Description 根据参数修改SYS_SEQ值
	 * @Author      高健
	 * @Date        2020年1月13日 下午2:51:31
	 * @param  seqMap
	 * @return void 返回类型
	 * @throws
	 */
	public void updateSysSeq(Map<String, Long> seqMap) {
		for (String key : seqMap.keySet()) {
			String[] keyArr = key.split("-");
			SysSeqModel sysSeqModel = new SysSeqModel();
			sysSeqModel.setSeqcode(keyArr[0]);
			sysSeqModel.setSeqdate(keyArr[1]);
			//查询序列
			SysSeqModel seqModel = sysSeqMapper.selectByPrimaryKey(sysSeqModel);
			if(seqModel == null) {
				//添加序列
				sysSeqModel.setSeqvalue(seqMap.get(key));
				sysSeqModel.setRemark("1");
				sysSeqMapper.insertSelective(sysSeqModel);
			} else {
				sysSeqModel.setSeqvalue(seqMap.get(key));
				//更新序列
				sysSeqMapper.updateByPrimaryKeySelective(sysSeqModel);
			}
		}
	}

	/**
	 *
	 * @Description 判断导入的内部资产代码是否为标准值
	 * @Author      高健
	 * @Date        2020年1月13日 下午4:06:12
	 * @param @param insideProdId
	 * @param @param codeType
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean checkInsideProdId(String insideProdId, String codeType) {
		String standardCodeRegex = "";
		if (StringUtil.equals(DictCodeListExt3.代码类型_产品.getValue(), codeType)) {
			standardCodeRegex = "^[a-zA-Z0-9]{7}[0-9]{8}$";
		} else {
			standardCodeRegex = "^[a-zA-Z0-9]{7}[0-9]{7}$";
		}
		return insideProdId.matches(standardCodeRegex);
	}



	/**
	 * 检查Excel模板是否正确
	 *
	 * @param flieName 上传文件名
	 * @return headList 模板头list
	 * @see
	 * @since 1.0
	 */
	private   List<ExcelValidDto> checkExcelTemp(List<List<String>> tempDataList, List<String> headList) {
		List<ExcelValidDto> tempCheckResult = new ArrayList<ExcelValidDto>();
		String errorMsg = "导入模板不一致，请重新选择文件！";
		ExcelValidDto excelValidDto = new ExcelValidDto();
		excelValidDto.setErrorInfo(errorMsg);
		tempCheckResult.add(excelValidDto);


		if (StringUtil.isEmptyList(tempDataList)) {
			// Excel没有有效数据
			return tempCheckResult;
		}
		List<String> excelHeadList = tempDataList.get(0);
		if (excelHeadList.size() < headList.size()) {
			// Excel中列数少于要求列数
			return tempCheckResult;
		}

		for (int i = 0; i < headList.size(); i++ ) {
			if (!headList.get(i).contains(excelHeadList.get(i))) {
				// Excel中列不一致
				return tempCheckResult;
			}
		}

		return null;
	}
}
