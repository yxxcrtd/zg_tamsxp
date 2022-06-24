package com.joyintech.tams.svreport.pbc.classic.manuallygeneratingpbcdata.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.svreport.pbc.classic.data.dto.PbcDataFile;

/**
 * <p>Title: ManuallyGeneratingpbcDataMService<／p>
 * <p>Description: 手工生成人行数据文件微服务业务逻辑层<／p>
 * <p>Company: 安徽兆尹信息科技股份有限公司<／p> 
 * @author gaojian
 * @date 2019年6月17日
 */
@Service
public class ManuallyGeneratingPbcDataMService extends BaseService<PbcDataFile>{

	/**
	 * 人行产品代码下拉框初始值
	 * @param param 
	 * 
	 * @return 人行产品代码下拉框初始值
	 */
	@SuppressWarnings("unchecked")
	public List<SelectOptionDto> getPbcProdId(Map<String, Object> param) {
		return restTemplate.postForObject(
	               SvrServiceApplication.PBC_SERVICE_NAME + "/ManuallyGeneratingPbcData/getPbcProdId",param,
	               List.class);
	}
	
	/**
     * 生成压缩文件
     * @return 压缩文件存放地址
     */
	public String createZipFile(Map<String, Object> param) {
		return restTemplate.postForObject(
	               SvrServiceApplication.PBC_SERVICE_NAME + "/ManuallyGeneratingPbcData/createZipFile",param,
	               String.class);
	}
    /**
     * 人行资产池代码下拉框
     * @param param 
     * 
     * @return 人行资产池代码下拉框
     */
    @SuppressWarnings("unchecked")
    public List<SelectOptionDto> getPbcAccountId(Map<String, Object> param) {
        return restTemplate.postForObject(
                   SvrServiceApplication.PBC_SERVICE_NAME + "/ManuallyGeneratingPbcData/getPbcAccountId",param,
                   List.class);
    }

	/**
	 * 内部产品代码
	 * @param param
	 * @return
	 */
	public List<SelectOptionDto> getInsideProdId(Map<String, Object> param) {
		return restTemplate.postForObject(
				SvrServiceApplication.PBC_SERVICE_NAME + "/ManuallyGeneratingPbcData/getInsideProdId",param,
				List.class);
	}
}
