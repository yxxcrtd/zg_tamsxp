package com.joyintech.tams.svreport.chb.prodstatusregist.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.svreport.chb.prodstatusregist.dto.ProdStatusRegist001Dto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


/**
 * @Description 产品状态登记服务
 * @author zhoubin
 * @version  2022年05月28日
 * @since 3.0
 */
@Service		
public class ProdStatusRegistMService extends BaseService<ProdStatusRegist001Dto>{


    /**
     * @Description 产品状态登记页面查询
     * @return Page<ProdStatusRegist001Dto>
     * @version 2022年05月28日
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<ProdStatusRegist001Dto> selectForPage(Map<String, Object> param) {
    	return restTemplate.postForPage(
	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ProdStatusRegist/findPage",param,
				ProdStatusRegist001Dto.class);
    }
    
    /**
     * 业务画面导出事件
     * @return
     * @since 3.0
     */
    public List<ProdStatusRegist001Dto> selectForExport(Map<String, Object> param) {
          return restTemplate.postForList(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ProdStatusRegist/selectForExport",param,
				  ProdStatusRegist001Dto.class);
    }
    /**
     *手工处理功能
     * @return 提示信息
     * @param map 条件map
     */
    @PostMapping("updateSumStaus")
    public void updateSumStaus(Map<String,Object> map) {
    	restTemplate.postForObject(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ProdStatusRegist/updateSumStaus",map,
  	       		Integer.class);
    }
    
    /**
     * @Description 取消手工处理
     * @param map
     * @return
     * @return AjaxResult
     * @version 2022年05月28日
     */
	@PostMapping("cancelMannul")
	public String cancelMannul(Map<String, Object> map) {
    	return restTemplate.postForObject(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ProdStatusRegist/cancelMannul",map,
  	       			String.class);
	}
	
	/**
	* @Description: 产品状态登记数据检查
	* @date: 2022年05月28日
	*/
	@PostMapping("reportCheck")
	public Map<String,Object> reportCheck(Map<String, Object> map) {
		return restTemplate.postForObject(
	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ProdStatusRegist/reportCheck", map,
				Map.class);
	}
}
