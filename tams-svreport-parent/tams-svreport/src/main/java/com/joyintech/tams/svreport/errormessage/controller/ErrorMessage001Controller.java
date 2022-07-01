package com.joyintech.tams.svreport.errormessage.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.errormessage.dto.ErrorMessage001;
import com.joyintech.tams.report.errormessage.service.ErrorMessage001Service;
import com.joyintech.tams.report.queryanalysis.equitysharecheck.dto.EquityShareCheck001Dto;
import com.joyintech.tams.svreport.errormessage.service.ErrorMessageMService;
/**
 * 
 * <p>错误表信息查询Controller</p>
 * @author liuyu
 * @创建时间 2019年5月13日 上午9:38:31
 */
@PageId("ErrorMessage001")
@Controller
@RequestMapping("/chb/errormessage/ErrorMessage001")
public class ErrorMessage001Controller extends BaseController{
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 错误信息service
	 */
	@Autowired
	private ErrorMessage001Service errorMessageService;
	/**
	 * 错误信息service
	 */
	@Autowired
	private ErrorMessageMService errorMessageMService;
	
	/**
	 *页面初始化
	 *@author liuyu
	 *@param model
	 *@return
	 */
	@RequestMapping(PAGE_INIT)
	@Override
	public String init(Model model) {
		Map<String, Object> map = RequestUtil.getPageParam();
		if(StringUtil.isNotEmpty(map.get("interfType"))) {
			model.addAttribute("interfType", map.get("interfType").toString().trim());
			model.addAttribute("interfTypeStr", map.get("interfType").toString().trim());
			model.addAttribute("searchDataStart", map.get("searchDataStart"));
			model.addAttribute("searchDataEnd", map.get("searchDataEnd"));
			model.addAttribute("isFromMain", "01");
		}
		if(StringUtil.isNotEmpty(map.get("searchDataStart"))){
			model.addAttribute("searchDataStart", map.get("searchDataStart"));
			model.addAttribute("searchDataEnd", map.get("searchDataEnd"));
			model.addAttribute("isFromMain", "01");
		}
		// modify by dubaojun 2020-11-03 错误数据页面接口类型取值修改为数据字典 start
		super.setSelectOptionToModel(model,	DictCode.维护数据状态, DictCode.校验接口类型);
		// modify by dubaojun 2020-11-03 错误数据页面接口类型取值修改为数据字典 end
		return super.init(model);
	}
	
	/**
	 * 查询数据
	 * @author liuyu
	 * @time 2019年5月13日 下午3:21:12
	 * @param
	 * @return
	 */
	@ResponseBody
	@PostMapping("searchTable")
	public Page<ErrorMessage001> selectAllData(){
	    
	    return errorMessageMService.queryPage(RequestUtil.getPageParam());
	}
	
	/**
	 * 获取数据接口下拉框
	 * @author liuyu
	 * @time 2019年5月13日 下午3:22:37
	 * @param
	 * @return
	 */
    @RequestMapping(value = "getInterfType", method = RequestMethod.GET)
    @ResponseBody
    public List<SelectOptionDto> getInterfType(){
    	 Map<String, Object> param = RequestUtil.getPageParam();
        return errorMessageMService.getInterTypeSelects(param);
    }
    /**
    *
    * 业务画面导出事件
    * @return 结果
    * @see
    * @since 1.0
    */
   @ResponseBody
   @RequestMapping("export")
   public ResponseEntity<byte[]> export() {
       List<ErrorMessage001> dtoList = errorMessageService.setDataList(RequestUtil.getParameters(getRequest()));
       File outFile = errorMessageService.makeFile(
           "/com/joyintech/tams/svreport/errormessage/conf/export_conf.xml", dtoList);
       return ExportUtil.export(
               "数据接入检查" + GlobalConstant.OPEN_PARENTHESIS
                                    + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS
                                    + GlobalConstant.POINT + FileNameUtil.getFileExtName(outFile),
               outFile, true);
   }
	

}
