package com.joyintech.tams.svreport.pbc.classic.manuallygeneratingpbcdata.controller;

import java.util.List;
import java.util.Map;

import com.joyintech.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.svreport.pbc.classic.manuallygeneratingpbcdata.service.ManuallyGeneratingPbcDataMService;
import com.joyintech.tams.svreport.pbc.classic.manuallygeneratingpbcdata.service.ManuallyGeneratingPbcDataService;

/**
 * <p>Title: ManuallyGeneratingpbcData001Controller<／p>
 * <p>Description: 手工生成人行数据文件Controller<／p>
 * <p>Company: 安徽兆尹信息科技股份有限公司<／p> 
 * @author gaojian
 * @date 2019年6月12日
 */

@Controller
@PageId("ManuallyGeneratingPbcData001")
@RequestMapping("/pbc/classic/manuallygeneratingpbcdata/ManuallyGeneratingPbcData001")
public class ManuallyGeneratingPbcData001Controller extends BaseController{
	
	@Autowired
	private ManuallyGeneratingPbcDataMService manuallyGeneratingpbcDataMService;
	
	@Autowired
	private ManuallyGeneratingPbcDataService manuallyGeneratingPbcDataService;
	
	/**
	 * 页面初始化
	 */
	@Override
    @RequestMapping(PAGE_INIT)
	public String init(Model model) {
		return super.init(model);
	}
	
	/**
	 * 生成产品代码列表
	 * @return 产品代码下拉框初始值
	 */
	@ResponseBody
	@RequestMapping("/getPbcProdId")
	public List<SelectOptionDto> getPbcProdId() {
		Map<String, Object> param = RequestUtil.getPageParam();
		return manuallyGeneratingpbcDataMService.getPbcProdId(param);
	}
	
	/**
     * 生成压缩文件
     * @return 压缩文件存放地址
     */
    @ResponseBody
    @RequestMapping("/createZipFile")
    public String createZipFile() {
    	Map<String, Object> param = RequestUtil.getPageParam();
    	return StringUtil.trimHtmlTag(manuallyGeneratingpbcDataMService.createZipFile(param));
    }
    
    /**
     * 下载压缩文件
     * @return 压缩文件
     */
    @ResponseBody
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download() {
    	Map<String, Object> param = RequestUtil.getPageParam();
        return manuallyGeneratingPbcDataService.download(param);
    }

    /**
     * 生成产品代码列表
     * @return 产品代码下拉框初始值
     */
    @ResponseBody
    @RequestMapping("/getPbcAccountId")
    public List<SelectOptionDto> getPbcAccountId() {
        Map<String, Object> param = RequestUtil.getPageParam();
        return manuallyGeneratingpbcDataMService.getPbcAccountId(param);
    }
    /**
	 * 获取平台日期前一日 (用于生成日期初始化)
	 * @return 平台日期前一日
	 */
	@RequestMapping("/getYesterDayDate")
    @ResponseBody
    public AjaxResult getYesterDayDate(){
        return manuallyGeneratingPbcDataService.getYesterDayDate();
    }
	/**
	 * 产品内部代码
	 * @return 产品内部代码下拉框初始值
	 */
	@ResponseBody
	@RequestMapping("/getInsideProdId")
	public List<SelectOptionDto> getInsideProdId() {
		Map<String, Object> param = RequestUtil.getPageParam();
		return manuallyGeneratingpbcDataMService.getInsideProdId(param);
	}
}
