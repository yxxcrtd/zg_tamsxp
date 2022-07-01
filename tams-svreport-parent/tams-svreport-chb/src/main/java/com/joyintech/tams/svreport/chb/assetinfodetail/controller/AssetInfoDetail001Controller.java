/*
 * 文件名：InvestorsHoldInfoRegist001Controller.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zuoyinyi
 * 修改时间：2018年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.svreport.chb.assetinfodetail.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.joyintech.component.svrreport.chb.dto.ChbAssetInfoDto;
import com.joyintech.framework.util.*;
import com.joyintech.system.constant.SystemGlobalConstant;
import com.joyintech.tams.common.base.entity.ChbAssetInfoErrodataWithBLOBs;
import com.joyintech.tams.svreport.chb.assetinfodetail.util.AssetInfoDetailUtil;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dto.ChbMstBondCreditRatingDto;
import com.joyintech.tams.svreport.chb.common.util.ErrdataConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.AssetInfoDetailDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.service.AssetInfoDetailMService;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.exception.WorkFlowException;

/**
 * 资产要素登记
 *
 * @author weiyuqiang
 * @version 1.6.0 2018年5月8日
 * @see AssetInfoDetail001Controller
 * @since 1.0
 */
@Controller
@PageId("AssetInfoDetail001")
@RequestMapping("/chb/assetinfodetail/AssetInfoDetail001")
public class AssetInfoDetail001Controller extends BaseController {

	/**
	 * 资产要素service
	 */
	@Autowired
	private AssetInfoDetailMService assetInfoDetailMService;

    
    @SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AssetInfoDetailUtil assetInfoDetailUtil;
	/**
	 * {@inheritDoc}
	 */
	@Override
    @RequestMapping(PAGE_INIT)
	public String init(Model model) {
//        model.addAttribute("searchDataStart", DateUtil.addDay(DateUtil.getSysDate(),-1));
//        model.addAttribute("searchDataEnd", DateUtil.getSysDate());
//        model.addAttribute("statusT", DictCodeListExt2.报送数据状态_新建.getValue());
//        model.addAttribute("sendStatusT", DictCodeListExt2.报送状态_手工处理.getValue());
//        model.addAttribute("cny", DictCodeListExt1.币种_中债_人民币.getValue());
		Map<String, Object> map = RequestUtil.getPageParam();
		if (StringUtil.isNotEmpty(map.get("submitStatus"))) {
			model.addAttribute("submitStatus", map.get("submitStatus").toString().trim());
			if (StringUtil.isNotEmpty(map.get("status"))) {
				model.addAttribute("status", map.get("status").toString().trim());
			}
			if(StringUtil.isNotEmpty(map.get("submitFeedbackStatus"))){
            	model.addAttribute("submitFeedbackStatus", map.get("submitFeedbackStatus").toString().trim());
            }
			model.addAttribute("searchDataStart", map.get("searchDataStart"));
			model.addAttribute("searchDataEnd", map.get("searchDataEnd"));
			model.addAttribute("isMain", "01");
		}
		//默认下载全量模板
		String pageId = "AssetInfoDetail001_3";
		if(StringUtil.equals(CacheUtil.getSysParam("CHB_ASSET_INFO_TEMPLATE_FILE"), "01")){
			pageId = "AssetInfoDetail001_2";
		}
		model.addAttribute("file",pageId);
		super.setSelectOptionToModel(model,DictCode.资产_负债类别, DictCode.资产_负债二级类别,DictCode.资产负债一级类别
				, DictCode.中债_上游资产一级分类, DictCode.中债_上游资产二级分类,DictCode.中债_上游资产三级分类,DictCode.中债_上游资产四级分类,
				DictCode.中债_上游资产五级分类, DictCode.中债数据状态);
		return super.init(model);
	}


	/**
	 *
	 * 业务画面查询事件
	 * <功能详细描述>
	 * @return
	 * @see
	 * @since 1.0
	 */
	@ResponseBody
	@RequestMapping("searchPage")
	public Page<AssetInfoDetailDto> searchPage() {
		Page<AssetInfoDetailDto> assetList = assetInfoDetailMService.searchForPage(RequestUtil.getPageParam());
		return assetList;
	}


	/**
	 *  页面删除
	 * @param dto 费用品种
	 * @param br br
	 * @return 删除结果
	 * @throws WorkFlowException WorkFlowException
	 */
	@ResponseBody
	@RequestMapping(ACTION_DELETE)
	public AjaxResult delete(@RequestBody @Validated({
			ValidateGroupDelete.class}) AssetInfoDetailDto dto, BindingResult br) throws WorkFlowException {
		ProcessParameter<AssetInfoDetailDto> processParameter = new ProcessParameter<AssetInfoDetailDto>();
		WorkFlowParameter<AssetInfoDetailDto> params = new WorkFlowParameter<AssetInfoDetailDto>();
		params.setPageId("AssetInfoDetail002");
		params.setElementId(dto.getElementId());
		params.setBusinessCode(item -> item.getAssetCode());
		params.setBusinessType(DictCodeListExt3.共通业务类型_资产基本信息维护.getValue());
		processParameter.setWorkFlowParameter(params);
		String msg = assetInfoDetailMService.start(processParameter,
				dto.getSelectedItem().toArray(new AssetInfoDetailDto[dto.getSelectedItem().size()]));
		return new AjaxResult(msg);
	}

    /**
     * 导出文件
     *
     * @return 导出的excel
     * @see
     * @since 1.6.0
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = ACTION_EXPORT)
    public ResponseEntity<byte[]> export() {
		Map<String, Object> params= RequestUtil.getPageParam();
			if(StringUtil.equals(CacheUtil.getSysParam("CHB_ASSET_INFO_TEMPLATE_FILE"), "02")){

				List<AssetInfoDetailDto> list = Lists.newArrayList();
				if(StringUtils.isNotEmpty((String)params.get("selectData"))){
					List<Map> selectData = JSONArray.parseArray((String) params.get("selectData"), Map.class);
					List<String> assetUuids = selectData.stream().map(e -> (String)e.get("assetUuid")).collect(Collectors.toList());
					list = assetInfoDetailMService.selectBykeys(assetUuids);

				}else{

					list= assetInfoDetailMService.selectAll(params);
				}
				List<AssetInfoDetailDto> assetInfoDetailDtoList = Lists.newArrayList();
				if(list.size()>0){
					for (int k=0;k<list.size();k++) {
						if(list.get(k).getStatus().equals("A")){
							AssetInfoDetailDto assetInfoDetailDto = ErrdataConvertUtil.errdataConvert(AssetInfoDetailDto.class, list.get(k).getOrgData());
							assetInfoDetailDto.setStatus("A");
							assetInfoDetailDto.setErrMsg(list.get(k).getErrMsg());
							assetInfoDetailDtoList.add(assetInfoDetailDto);
						}else{
							assetInfoDetailDtoList.add(list.get(k));
						}
					}
				}
				//处理融资人，托管人，管理人
				assetInfoDetailUtil.dealFinancie(assetInfoDetailDtoList);
				assetInfoDetailUtil.processAssetInfoDetailDtos(assetInfoDetailDtoList);
				File outFile = assetInfoDetailMService.makeFile(
						"/com/joyintech/tams/svreport/chb/assetinfodetail/conf/export_conf_multiple.xml",
						assetInfoDetailDtoList);
				return ExportUtil.export(
						this.getMessage(this.getPageId()) + SystemGlobalConstant.OPEN_PARENTHESIS
								+ DateUtil.format(new Date())
								+ SystemGlobalConstant.CLOSE_PARENTHESIS
								+ SystemGlobalConstant.POINT
								+ FileNameUtil.getFileExtName(outFile),
						outFile, true);

			}else{
				return assetInfoDetailMService.export(this.getPageId(),RequestUtil.getParameters(getRequest()));
			}
    }
      
    /**
     * 资产代码下拉框
     * @return 产品名称列表
     */
    @RequestMapping("/getAssetCode")
    @ResponseBody
    public List<SelectOptionDto> getRegCode() {
        return assetInfoDetailMService.queryAssetCodeList(RequestUtil.getPageParam());
    }
    
    /**
	 * 导入数据文件之前检查数据
	 *
	 * @param fileName 导入的文件名
	 * @return 处理结果
	 */
	@ResponseBody
	@RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
	public AjaxResult checkConfirmMsgForImportData(String fileName) throws Exception {
		return assetInfoDetailMService.checkConfirmMsgForImportData(fileName);
	}

	@ResponseBody
	@RequestMapping("/importFile")
	public List<com.joyintech.tams.systemtools.dto.ExcelValidDto> importData(String fileName) throws Exception {
		return this.assetInfoDetailMService.importData(fileName);
	}
}
