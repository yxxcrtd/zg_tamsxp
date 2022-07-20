package com.joyintech.service.chb.prodstatusregist.controller;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.svreport.chb.prodstatusregist.dto.ProdStatusRegist001Dto;
import com.joyintech.tams.svreport.chb.prodstatusregist.service.ProdStatusRegist001Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * @Description 产品状态登记服务
 * @author zhoubin
 * @version  2022年05月28日
 * @since 3.0
 */
@RestController
@RequestMapping("/ProdStatusRegist")
public class ProdStatusRegistController {

	@Autowired
	private ProdStatusRegist001Service prodStatusRegist001Service;

    /**
     * @Description 产品状态登记页面查询
     * @return Page<ProdStatusRegist001Dto>
     * @version 3.0 2022年05月28日
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<ProdStatusRegist001Dto> selectForPage(@RequestBody Map<String, Object> param) {

        return prodStatusRegist001Service.selectForPage(param);
    }
    
    /**
     * 业务画面导出事件
     * @return
     * @since 3.0
     */
    @PostMapping("selectForExport")
    public List<ProdStatusRegist001Dto> selectForExport(@RequestBody Map<String, Object> param) {
          return prodStatusRegist001Service.selectForExport(param);
   
    }
    /**
     *手工处理功能
     * @return 提示信息
     * @param map 条件map
     */
    @PostMapping("updateSumStaus")
    public void doUpdate(@RequestBody Map<String,Object> map) {
		prodStatusRegist001Service.updateSumStaus(map);
    }
    
    /**
     * @Description 取消手工处理
     * @param map
     * @version 3.0 2022年05月28日
     */
	@PostMapping("cancelMannul")
	public String cancelMannul(@RequestBody Map<String, Object> map) {
    	return prodStatusRegist001Service.cancelMannul(map);
	}
	
	@PostMapping("selectAllData")
	public List<ProdStatusRegist001Dto> selectAllData(@RequestBody Map<String, Object> pageParam){
		return prodStatusRegist001Service.selectAllData(pageParam);
	}
	
	/**
	* @Description: 产品状态登记数据检查
	*
	* @author: 周彬/zhoubin
	* @date: 2022年05月28日
	*/
	@PostMapping("reportCheck")
	public Map<String,Object> reportCheck(@RequestBody Map<String,Object> map) {
		List<String> keyList = new ArrayList<String>();
		Map<String, Object> param = map;
		if(!param.containsKey("checkKeys")){
			keyList=prodStatusRegist001Service.getCheckList(param);
		}
		else{
			keyList= StringUtil.splitToList(GlobalConstant.COMMA, (String)param.get("checkKeys"));
		}
		if (CollectionUtils.isEmpty(keyList)) {
			List<ExcelValidDto> checkResult = new ArrayList<ExcelValidDto>();
			checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "无报送数据",null));
			Map<String,Object> returnMap = new HashMap<>();
			returnMap.put("excelValidDtoList",checkResult);
			return returnMap;
		}
		return prodStatusRegist001Service.checkData(keyList);
	}
}
