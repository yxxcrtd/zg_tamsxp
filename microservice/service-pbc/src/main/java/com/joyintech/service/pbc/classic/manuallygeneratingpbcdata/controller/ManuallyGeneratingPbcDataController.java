package com.joyintech.service.pbc.classic.manuallygeneratingpbcdata.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.svreport.pbc.classic.manuallygeneratingpbcdata.service.ManuallyGeneratingPbcDataService;

/**
 * <p>Title: PbcReportFileManageController<／p>
 * <p>Description: 手工生成人行报送文件Controller<／p>
 * <p>Company: 安徽兆尹信息科技股份有限公司<／p> 
 * @author gaojian
 * @date 2019年6月12日
 */

@RestController
@RequestMapping("/ManuallyGeneratingPbcData")
public class ManuallyGeneratingPbcDataController {
	
	@Autowired
	private ManuallyGeneratingPbcDataService manuallyGeneratingpbcDataService;
	
	/**
	 * 产品代码下拉框初始值
	 * 
	 * @return 产品代码下拉框初始值
	 */
	@PostMapping("getPbcProdId")
	public List<SelectOptionDto> getPbcProdId(@RequestBody Map<String, Object> param) {
		return manuallyGeneratingpbcDataService.getPbcProdId(param);
	}
	
	/**
	 * 创建压缩文件
	 * @param param 页面参数
	 * @return 压缩文件之地
	 */
	@PostMapping("createZipFile")
	public String createZipFile(@RequestBody Map<String, Object> param) {
		return manuallyGeneratingpbcDataService.createZipFile(param);
	}
    
    /**
     * 产品代码下拉框初始值
     * 
     * @return 产品代码下拉框初始值
     */
    @PostMapping("getPbcAccountId")
    public List<SelectOptionDto> getPbcAccountId(@RequestBody Map<String, Object> param) {
        return manuallyGeneratingpbcDataService.getPbcAccountId(param);
    }
	/**
	 * 内部产品代码
	 *
	 * @return 内部产品代码下拉框初始值
	 */
	@PostMapping("getInsideProdId")
	public List<SelectOptionDto> getInsideProdId(@RequestBody Map<String, Object> param) {
		return manuallyGeneratingpbcDataService.getInsideProdId(param);
	}
}
