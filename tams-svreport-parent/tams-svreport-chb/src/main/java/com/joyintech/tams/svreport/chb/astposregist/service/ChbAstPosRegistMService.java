package com.joyintech.tams.svreport.chb.astposregist.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


/**
 * @Description 资产持仓登记服务
 */
@Service		
public class ChbAstPosRegistMService extends BaseService<ChbAstPosRegist001Dto>{


    /**
     * @Description 资产持仓登记页面查询
     * @return Page<ProdTerminationRegist001Dto>
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<ChbAstPosRegist001Dto> selectForPage(Map<String, Object> param) {
    	return restTemplate.postForPage(
	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/findPage",param,
				ChbAstPosRegist001Dto.class);
    }
    
    /**
     * 业务画面导出事件
     * @return
     * @since 3.0
     */
    public List<ChbAstPosRegist001Dto> selectForExport(Map<String, Object> param) {
          return restTemplate.postForList(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/selectForExport",param,
				  ChbAstPosRegist001Dto.class);
    }
    /**
     *手工处理功能
     * @return 提示信息
     * @param map 条件map
     */
    @PostMapping("updateSumStaus")
    public void updateSumStaus(Map<String,Object> map) {
    	restTemplate.postForObject(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/updateSumStaus",map,
  	       		Integer.class);
    }
    
    /**
     * @Description 取消手工处理
     * @param map
     * @return
     */
	@PostMapping("cancelMannul")
	public String cancelMannul(Map<String, Object> map) {
    	return restTemplate.postForObject(
  	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/cancelMannul",map,
  	       			String.class);
	}
	
	/**
	* @return：资产持仓登记数据检查结果
	*/
	@PostMapping("reportCheck")
	public Map<String,Object> reportCheck(Map<String, Object> map) {
		return restTemplate.postForObject(
	       		SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/reportCheck", map,
				Map.class);
	}
}
