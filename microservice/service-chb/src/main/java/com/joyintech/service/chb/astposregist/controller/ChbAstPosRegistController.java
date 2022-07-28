package com.joyintech.service.chb.astposregist.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import com.joyintech.tams.svreport.chb.astposregist.service.ChbAstPosRegist001Service;
import com.joyintech.tams.svreport.chb.investtradereg.dto.InvestTradeRegist001Dto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *资产持仓登记
 */
@RestController
@RequestMapping("/ChbAstPosRegist")
public class ChbAstPosRegistController {

	@Autowired
	private ChbAstPosRegist001Service chbAstPosRegist001Service;

    /**
     * @Description 资产持仓登记登记页面查询
     * @return Page<ChbAstPosRegist001Dto>
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<ChbAstPosRegist001Dto> selectForPage(@RequestBody Map<String, Object> param) {
        Page<ChbAstPosRegist001Dto> page =
				chbAstPosRegist001Service.selectForPage(param);
        return page;
    }
    
    /**
     * 业务画面导出事件
     * @return
     * @since 3.0
     */
    @PostMapping("selectForExport")
    public List<ChbAstPosRegist001Dto> selectForExport(@RequestBody Map<String, Object> param) {
          return chbAstPosRegist001Service.selectForExport(param);

    }
    /**
     *手工处理功能
     * @return 提示信息
     * @param map 条件map
     */
    @PostMapping("updateSumStaus")
    public void doUpdate(@RequestBody Map<String,Object> map) {
		chbAstPosRegist001Service.updateSumStaus(map);
    }

    /**
     * @Description 取消手工处理
     * @param map
     * @return
     * @return AjaxResult
     */
	@PostMapping("cancelMannul")
	public String cancelMannul(@RequestBody Map<String, Object> map) {
    	return chbAstPosRegist001Service.cancelMannul(map);
	}

	@PostMapping("selectAllData")
	public List<ChbAstPosRegist001Dto> selectAllData(@RequestBody Map<String, Object> pageParam){
		return chbAstPosRegist001Service.selectAllData(pageParam);
	}

	/**
	* @Description: 资产持仓登记数据检查
	*/
	@PostMapping("reportCheck")
	public Map<String,Object> reportCheck(@RequestBody Map<String,Object> map) {
		List<String> keyList = new ArrayList<String>();
        Map<String, Object> param = map;
        /***0722升级**/
//        if (!param.containsKey("keyList")) {
           Map<String,Object> map1 = chbAstPosRegist001Service.getCheckEndList(param);
//        } else {
//            keyList = StringUtil.splitToList(GlobalConstant.COMMA, (String)param.get("checkKeys"));
//        }
        keyList=(List<String>) map1.get("keylist");
        /***0722升级**/
        if (CollectionUtils.isEmpty(keyList)) {
            List<ExcelValidDto> checkResult = new ArrayList<ExcelValidDto>();
            checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "无报送数据",null));
			Map<String,Object> returnMap = new HashMap<>();
			returnMap.put("excelValidDtoList",checkResult);
			return returnMap;
        }
        return chbAstPosRegist001Service.checkData((List<String>) map1.get("keylistseq"),keyList);
	}

    @ResponseBody
    @RequestMapping(value = "countExportNum")
    public int countExportNum(@RequestBody Map<String, Object> param) {
        int dataNum = chbAstPosRegist001Service.countExportNum(param);
        return dataNum;
    }
}
