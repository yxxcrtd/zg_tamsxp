package com.joyintech.tams.svreport.chb.subsidiamonthreport.controller;


import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDto;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.service.ChbSubsidiaMonthReport001Service;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.service.ChbSubsidiaMonthReportService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 中债理财子公司月度报表报送登记Controller
 *
 * @author dubaojun
 * @version 1.0 2020年06月03日
 * @see ChbSubsidiaMonthReport001Controller
 * @since 1.0
 */
@Controller
@PageId("SvrSubsidiaMonthReport001")
@RequestMapping("/chb/subsidiamonthreport/ChbSubsidiaMonthReport001")
public class ChbSubsidiaMonthReport001Controller extends BaseController {
    /**
     * service
     */
    @Autowired
    private ChbSubsidiaMonthReport001Service chbSubsidiaMonthReport001Service;
    
    @Autowired
    private ChbSubsidiaMonthReportService chbSubsidiaMonthReportService;
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
 
	
    /**
	 * {@inheritDoc}
	 */
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
			model.addAttribute("isMain", "01");
		}
		super.setSelectOptionToModel(model, DictCode.报送状态, DictCode.报送数据状态, DictCode.报送反馈状态);
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
	public Page<ChbMonthReportSubDto> searchPage() {
		Page<ChbMonthReportSubDto> page = chbSubsidiaMonthReport001Service.searchPage(RequestUtil.getPageParam());
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
       ChbMonthReportSubDto report = chbSubsidiaMonthReport001Service.searchPageBysubmitDate(RequestUtil.getPageParam());
       if (report.getStartdate() != null){
           Calendar cal = Calendar.getInstance();
           cal.setTime(report.getStartdate());
           result.put("YEAR",cal.get(Calendar.YEAR)+"");
           result.put("MONTH",cal.get(Calendar.MONTH)+1+"");
           result.put("SENDSTATUS", report.getSendStatus());
           result.put("STATUS", report.getStatus());
       }
       return JsonUtils.object2Json(result);
   }
	/**
	 * 
	* 数据检查.
	* @author liuyu
	* @time 2019年6月25日 下午4:23:31
	* @return
	 */
	@ResponseBody
    @RequestMapping("reportCheck")
    public List<ExcelValidDto> reportCheck() {
        return chbSubsidiaMonthReport001Service.reportCheck(RequestUtil.getPageParam());
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
		return chbSubsidiaMonthReportService.export(this.getPageId(), RequestUtil.getPageParam());
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
		chbSubsidiaMonthReport001Service.updateSumStaus(map);
		return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
	}
	@ResponseBody
	@RequestMapping("doBatch")
	public AjaxResult doAssetbatch(@RequestBody Map<String, Object> map){
		String message = chbSubsidiaMonthReport001Service.recalReport(map);
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
		chbSubsidiaMonthReport001Service.cancelMannul(map);
		return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
	}
	
    /**
     * 查询列表
     *
     * @return DtPage
     */
    @ResponseBody
    @RequestMapping(value = "preView")
    public void preView(HttpServletRequest request, HttpServletResponse response) {
        String htmlExcel = chbSubsidiaMonthReportService.priview(this.getPageId(), RequestUtil.getPageParam());
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        InputStream res = new ByteArrayInputStream(htmlExcel.getBytes(Charset.forName("UTF-8")));
        byte[] buf = new byte[1024 * 10];
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
            logger.error(e.getMessage(), e);
            return;
        }
    }
}
