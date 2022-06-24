package com.joyintech.tams.svreport.pbc.monthreport.controller;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.svreport.pbc.monthreport.service.PbcMonthReportMService;
import com.joyintech.tams.svreport.pbc.report.dto.PbcMonthReport;
import com.joyintech.tams.svreport.pbc.report.service.PbcMonthReport001Service;


/**
 * 人行报表界面Controller
 * 〈功能详细描述〉
 * @author yuluan
 * @version 1.0 2019年3月5日
 * @see PbcMonthReport001Controller
 * @since 1.0
 */
@Controller
@PageId("PbcMonthReport001")
@RequestMapping("/svreport/pbc/PbcMonthReport001")
public class PbcMonthReport001Controller extends BaseController{


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * service
     */
    @Autowired
    private PbcMonthReport001Service pbcMonthReport001Service;
    @Autowired
    private PbcMonthReportMService pbcMonthReportMService;
 
	
    /**
	 * {@inheritDoc}
	 */
    @RequestMapping(PAGE_INIT)
	@Override
	public String init(Model model) {
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
			model.addAttribute("pbc_report_type", map.get("pbc_report_type"));
			model.addAttribute("pbc_report_type", map.get("PBC_REPORT_TYPE"));
			model.addAttribute("isMain", "01");
		}else{


			Date sysDate = DateUtil.platDateFromCache();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sysDate);

	        // 获取上月的第一天
	        calendar.add(Calendar.MONTH, -1);
	        calendar.set(Calendar.DAY_OF_MONTH, 1);
	        Date reportStartDate = calendar.getTime();
	        // 获取上月的最后一天
	        calendar.add(Calendar.MONTH, 1);
	        calendar.set(Calendar.DAY_OF_MONTH, 0);
	        Date reportEndDate = calendar.getTime();
	        model.addAttribute("searchDataStart", DateUtil.format(reportStartDate));
	        model.addAttribute("searchDataEnd", DateUtil.format(reportEndDate));
		}
		super.setSelectOptionToModel(model, DictCode.报送数据状态, DictCode.报送状态, DictCode.人行报送反馈状态, DictCode.人行报表名称);
		return super.init(model);
	}

	/**
	 *
	 * 业务画面查询事件
	 *
	 * @return
	 * @see
	 * @since 1.0
	 */
	@ResponseBody
	@RequestMapping("findPage")
	public Page<PbcMonthReport> searchPage() {
		Page<PbcMonthReport> page = pbcMonthReportMService
				.searchPage(RequestUtil.getPageParam());
		return page;
	}


    /**
     *
     * 业务画面查询事件
     *
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPageBysubmitDate")
    public String searchPageBysubmitDate() {
        Map<String,String> result = new HashMap<String,String>();
        PbcMonthReport report = pbcMonthReportMService
                .searchPageBysubmitDate(RequestUtil.getPageParam());
        if (report.getStartdate() != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(report.getStartdate());
            result.put("YEAR",cal.get(Calendar.YEAR)+"");
            result.put("MONTH",cal.get(Calendar.MONTH)+1+"");
        }
		if(report.getSendStatus() != null){
			result.put("SendStatus",report.getSendStatus());
		}
        return JsonUtils.object2Json(result);
    }
	/**
	 *
	 * 业务画面导出事件
	 *
	 * @return
	 * @see
	 * @since 1.0
	 */
	@ResponseBody
	@RequestMapping("export")
	public ResponseEntity<byte[]> export() {
		return pbcMonthReport001Service.export(this.getPageId(), RequestUtil.getPageParam());
	}
	
	/**
	 * 手工处理功能
	 *
	 * @return 提示信息
	 * @param map
	 *            条件map
	 * @seea
	 * @since 1.0
	 */
	@ResponseBody
	@RequestMapping("doManual")
	public AjaxResult doUpdate(@RequestBody Map<String, Object> map) {
		pbcMonthReportMService.updateSumStaus(map);
		return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
	}

	@ResponseBody
	@RequestMapping("doBatch")
	public AjaxResult doAssetbatch(@RequestBody Map<String, Object> map){
		String message = pbcMonthReportMService.recalReport(map);
        AjaxResult result = new AjaxResult(this.getMessage("REPORT_COMMON_I_0009"));
        // 报表生成失败则提示信息
		if (!StringUtils.isEmpty(message) && !"success".equals(message)){
		    result = new AjaxResult(this.getMessage(message));
		}
		return result;
	}
	/**
	 * 取消手工处理功能
	 *
	 * @return 提示信息
	 * @param map
	 *            条件map
	 * @seea
	 * @since 1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelMannul", method = RequestMethod.POST)
	public AjaxResult cancelMannul(@RequestBody Map<String, Object> map) {
		boolean isDuplicate = pbcMonthReportMService.cancelMannul(map);
       // 是否有重复数据(同时有已报送和未报送数据)
        if (isDuplicate){
            AjaxResult ajaxResult = new AjaxResult(this.getMessage("REPORT_COMMON_I_0030"),MessageType.错误消息);
            // 防止提示信息后卡死
            ajaxResult.setStatuCode(HttpStatus.OK.value());
            return ajaxResult;
        } else {
            return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
        }
	}
	
	   /**
	    * 查询列表
	    *
	    * @return DtPage
	    */
	   @ResponseBody
	   @RequestMapping(value ="/preView")
	   public String preView(HttpServletRequest request,HttpServletResponse response) {
		   String htmlExcel = pbcMonthReport001Service.priview(this.getPageId(), RequestUtil.getPageParam());
			if ("fail".equals(htmlExcel)){
	           return "fail";
	       }
	       response.reset();
	       response.setContentType("text/html;charset=UTF-8");
	       response.setCharacterEncoding("UTF-8");
	       InputStream res = new ByteArrayInputStream(htmlExcel.getBytes(Charset.forName("UTF-8")));
	       byte[] buf = new byte[1024*10];
	       int len = 0;
	       OutputStream out;
        try {
            out = response.getOutputStream();
	       while ((len = res.read(buf)) != -1)
	           out.write(buf, 0, len);
	       out.flush();
	       res.close();
	       out.close();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return "fail";
        }
        return "success";
	   }
		/**
		 * 
		* 数据检查.
		* @author yuluan
		* @time 2019年9月4日 下午4:23:31
		* @return
		 */
		@ResponseBody
	    @RequestMapping("reportCheck")
	    public List<ExcelValidDto> reportCheck() {
	        return pbcMonthReportMService.reportCheck(RequestUtil.getPageParam());
	    }
}
