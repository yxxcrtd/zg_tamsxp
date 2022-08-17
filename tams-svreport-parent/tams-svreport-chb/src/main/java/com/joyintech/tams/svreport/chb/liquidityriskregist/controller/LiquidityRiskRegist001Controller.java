package com.joyintech.tams.svreport.chb.liquidityriskregist.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.svreport.chb.liquidityriskreg.dto.LiquidityRiskRegist001Dto;
import com.joyintech.tams.svreport.chb.liquidityriskreg.service.LiquidityRiskRegist001Service;
import com.joyintech.tams.svreport.chb.liquidityriskregist.service.LiquidityRiskRegistMService;
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
import java.util.Map;

@Controller
@PageId("SvrLiquidityRiskRegist001")
@RequestMapping("/chb/liquidityriskregist/LiquidityRiskRegist001")
public class LiquidityRiskRegist001Controller extends BaseController {

	@Autowired
	private LiquidityRiskRegistMService liquidityRiskRegistMService;
	@Autowired
	private LiquidityRiskRegist001Service liquidityRiskRegist001Service;
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public String init(Model model) {
        Map<String, Object> map = RequestUtil.getPageParam();
        if (StringUtil.isNotEmpty(map.get("submitStatus"))) {
            model.addAttribute("submitStatus", map.get("submitStatus").toString().trim());
            if (StringUtil.isNotEmpty(map.get("dataStatus"))) {
                model.addAttribute("dataStatus", map.get("dataStatus").toString().trim());
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
    public Page<LiquidityRiskRegist001Dto> searchPage() {
        Page<LiquidityRiskRegist001Dto> page = liquidityRiskRegistMService
                .searchPage(RequestUtil.getPageParam());
        return page;
    }

    /**
     * 导出文件
     *
     * @return 导出的excel
     * @see
     * @since 1.6.0
     */
    @ResponseBody
    @RequestMapping("download")
    public ResponseEntity<byte[]> download() throws IOException {
        String datas = this.getParam("keyList");
        return liquidityRiskRegist001Service.download(this.getPageId(), RequestUtil.getPageParam(),datas);
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
        liquidityRiskRegistMService.updateSumStaus(map);
        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
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
        liquidityRiskRegistMService.cancelMannul(map);
        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
    }

    /**
     * 查看详情
     *
     * @return DtPage
     */
    @ResponseBody
    @RequestMapping(value = "preView")
    public void preView(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reportName = this.getParam("itemName");
        //期数
        String itemId = this.getParam("itemId");
        String htmlExcel = liquidityRiskRegist001Service.priview(reportName,itemId);
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
