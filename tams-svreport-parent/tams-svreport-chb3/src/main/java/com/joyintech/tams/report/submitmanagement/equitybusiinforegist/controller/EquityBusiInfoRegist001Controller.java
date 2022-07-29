/*
 * 文件名：EquityBusiInfo001Controller.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：zyf
 * 修改时间：2017年9月19日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.submitmanagement.equitybusiinforegist.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.progressbar.ProgressBarService;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.queryanalysis.equitysharecheck.dao.EquityShareCheck001Mapper;
import com.joyintech.tams.report.queryanalysis.equitysharecheck.dto.EquityShareCheck001Dto;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dto.EquityBusiInfoRegist001Dto;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.service.EquityBusiInfoRegist001Service;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.service.EquityBusiInfoRegistMService;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dto.InvestorsHoldInfoRegist001Dto;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zyf
 * @version 1.0 2017年9月19日
 * @see EquityBusiInfoRegist001Controller
 * @since 1.0
 */
@Controller
@PageId("SvrEquityBusiInfoRegist001")
@RequestMapping("/svreport/submitmanagement/equitybusiinforegist/SvrEquityBusiInfoRegist001")
public class EquityBusiInfoRegist001Controller extends BaseController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(EquityBusiInfoRegist001Controller.class);
    /**
     * 进度条接口
     */
    @Autowired
    private ProgressBarService progressBarService;
    /**
     * 投资者持有数量信息登记
     */
    @Autowired
    private EquityBusiInfoRegist001Service equityBusiInfoRegist001Service;
    @Autowired
    private EquityBusiInfoRegistMService equityBusiInfoRegistMService;
    @Autowired
    private EquityShareCheck001Mapper equityShareCheck001Mapper;
    @Autowired
    private EquityBusiInfoRegist001Mapper equityBusiInfoRegist001Mapper;



//    @Autowired
//    private ChbBatchParaMapper chbBatchParaMapper;
    /**
     * 进度条ID
     */
    private static final String PROCESS_KEY = "exportProcessBar";

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(PAGE_INIT)
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
        super.setSelectOptionToModel(model, DictCode.报送状态, DictCode.业务种类,
                DictCode.报送数据状态, DictCode.报送反馈状态, DictCode.投资者明细渠道);
        List<SelectOptionDto> listSendStatus = querySendStatus();
        // 增加发送状态
        model.addAttribute("SUBMIT_SEND_STATUS",
                JsonUtils.objectArray2Json(listSendStatus.toArray()));
        if (StringUtil.isNotEmpty(RequestUtil.getParam("validKey"))) {
            // 报送前检查链接数据主键解析
            String[] validKeyArr = StringUtil.split(RequestUtil.getParam("validKey"),
                    GlobalConstant.COMMA);
            InvestorsHoldInfoRegist001Dto dto = new InvestorsHoldInfoRegist001Dto();
            dto.setPrdRegistCode(StringUtil.split(validKeyArr[0], " ")[1]);
            dto.setIndentifyMark(StringUtil.split(validKeyArr[1], " ")[1]);
            dto.setHoldDate(DateUtil.parse(StringUtil.split(validKeyArr[2], " ")[1]));
            dto.setCcy(StringUtil.split(validKeyArr[3], " ")[1]);
            dto.setRegistBankCode(StringUtil.split(validKeyArr[4], " ")[1]);
            model.addAttribute("validKey", dto);
        }
        //业务确认日-市场节假日表中有值则取前一个工作日，无则取前一天
        String dateStr = null;
        Date ywDate = equityBusiInfoRegist001Service.getLastWeekDay(
                CacheUtil.getSysParam(SVReportGlobalConstant.SVR_CALENDAR_ID),DateUtil.getCurrentDate());
        if(StringUtil.isNotEmpty(ywDate)){
            dateStr = DateUtil.format(ywDate);
        }else{
            dateStr = DateUtil.plusDays(DateUtil.getCurrentDate(), -1);
        }
        String svrSystemType=SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SEND_EQUDET);
        if(svrSystemType!=null&&svrSystemType.equals("01")){
            model.addAttribute("isSend",true);
        }else{
            model.addAttribute("isSend",false);
        }
        String dayselectRange = CacheUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY,
                SVReportGlobalConstant.SVR_EQUITY_CONF_DATE_SELECT_RANGE);
        model.addAttribute("DATE_START",dateStr);
        model.addAttribute("DATE_END",dateStr);
        model.addAttribute("DAY_SELECT_RANGE",dayselectRange);
        return super.init(model);
    }

    /**
     * 业务画面查询事件
     *
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<EquityBusiInfoRegist001Dto> selectAllData() {
        Page<EquityBusiInfoRegist001Dto> page = equityBusiInfoRegistMService.selectForPage(
                RequestUtil.getPageParam());
        return page;
    }

    /**
     * 查询事件（无分页）
     *
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("searchInfo")
    public List<EquityBusiInfoRegist001Dto> searchInfo(@RequestParam Map<String,Object> param) {
        return equityBusiInfoRegistMService.searchInfo(param);
    }


    /**
     * 产品名称下拉框
     *
     * @return 产品名称列表
     */
    @RequestMapping("getPrdName")
    @ResponseBody
    public List<SuggestDto> getPrdName(String param) {
        return equityBusiInfoRegistMService.queryPrdNameList(param);
    }

    /**
     * 业务画面导出事件
     *
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("export")
    public ResponseEntity<byte[]> export() {
        //是否升级投资者明细  01是  02否
        String isUpgrade = SvrDataUtil.getSysParam("SVR_HX_ISUPGRADE");
        if (RequestUtil.getPageParam().containsKey("keyList")) {
            String flag = (String)RequestUtil.getPageParam().get("flag");
            List<EquityBusiInfoRegist001Dto> dtoList = equityBusiInfoRegist001Service.selectAllList(
                    RequestUtil.getParameters(getRequest()));
            File outFile = null;
            if (StringUtil.equals("0", flag)) {
                if(StringUtil.isNotEmpty(isUpgrade)&&StringUtil.equals("01",isUpgrade)) {
                    outFile = equityBusiInfoRegist001Service.makeFile(
                            "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf.xml",
                            dtoList);
                }else{
                    outFile = equityBusiInfoRegist001Service.makeFile(
                            "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/q_export_conf.xml",
                            dtoList);
                }
            }
            else if (StringUtil.equals("1", flag)) {
                outFile = equityBusiInfoRegist001Service.makeFile(
                        "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf_all.xml",
                        dtoList);
            }
            return ExportUtil.export(
                    this.getMessage(this.getPageId()) + GlobalConstant.OPEN_PARENTHESIS
                            + DateUtil.format(new Date())
                            + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
                            + FileNameUtil.getFileExtName(outFile),
                    outFile, true);
        }
        else {

            return equityBusiInfoRegist001Service.export(this.getPageId(), RequestUtil.getPageParam());

//            String flag = (String)RequestUtil.getPageParam().get("flag");
//            if (StringUtil.equals("2", flag)) {
//                return equityBusiInfoRegist001Service.export(this.getPageId(), RequestUtil.getPageParam());
//            }else{
//                // 判断当前画面的导出进度是否正在处理
//                if(progressBarService.isProcessing(this.getPageId(), PROCESS_KEY)) {
//                    throw new BusinessException("当前画面正在执行导出操作，请耐心等待！");
//                }
//                // 设置当前画面进度为0
//                progressBarService.updatePercent(this.getPageId(), PROCESS_KEY, 0D, false);
//                try{
//                    List<EquityBusiInfoRegist001Dto> dtoList = equityBusiInfoRegist001Service.selectAllListEx(
//                        RequestUtil.getParameters(getRequest()));
//                    // 设置当前画面进度为0
//                    progressBarService.updatePercent(this.getPageId(), PROCESS_KEY, 0.1D, false);
//                    MultiThread tt= new MultiThread(this.getPageId(),dtoList.size());
//                    new Thread(tt).start();// 启动该内部类线程
//                    File outFile = null;
//                    if (StringUtil.equals("0", flag)) {
//                        outFile = equityBusiInfoRegist001Service.makeFile(
//                            "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf.xml",
//                            dtoList);
//                    }
//                    else if (StringUtil.equals("1", flag)) {
//                        outFile = equityBusiInfoRegist001Service.makeFile(
//                            "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf_all.xml",
//                            dtoList);
//                    }
//                    progressBarService.updatePercent(this.getPageId(), PROCESS_KEY, 1D, false);
//                    progressBarService.removePercent(this.getPageId(), PROCESS_KEY);
//                    return ExportUtil.export(
//                        this.getMessage(this.getPageId()) + GlobalConstant.OPEN_PARENTHESIS
//                        + DateUtil.format(new Date())
//                        + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
//                        + FileNameUtil.getFileExtName(outFile),
//                        outFile, true);
//                }
//                catch (Exception e) {
//                    progressBarService.removePercent(this.getPageId(), PROCESS_KEY);
//                    throw new BusinessException(e);
//                }
//            }
            // return equityBusiInfoRegist001Service.export(this.getPageId(),
            // RequestUtil.getPageParam());
        }

    }
    class MultiThread implements Runnable {
        private String pageId;
        private Integer size;
        public MultiThread(String pageId,Integer size) {
            this.pageId = pageId;
            this.size = size;
        }
        @Override
        public void run() {
            try {
                int a =size/1000;
                double d=0.1;
                for(int i=0;i<a;i++){
                    d=d+0.01d;
                    if(d>0.98d){
                        break;
                    }
                    Thread.sleep(2000);
                    progressBarService.updatePercent(pageId, PROCESS_KEY, d, false);
                }
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage(),e);
            }

        }

    }
    // /**
    // * 将选择的数据导出成excel
    // * @return 返回需要的格式
    // * @see
    // * @since 1.0
    // */
    // @RequestMapping(value = "/exportSelect")
    // public ResponseEntity<byte[]> exportSelect(){
    //
    //
    // }

    /**
     * 导出删除模板
     *
     * @return 返回需要的格式
     * @see
     * @since 1.0
     */
    @RequestMapping(value = "exportDel")
    public ResponseEntity<byte[]> exportDel() {
        Map<String, Object> map = RequestUtil.getPageParam();
        String exportKeys = (String)map.get("expKeys");
        // String flag = (String) RequestUtil.getPageParam().get("flag");
        List<EquityBusiInfoRegist001Dto> dtoList = equityBusiInfoRegist001Service.queryExportSelectRegistList(
                exportKeys);
        File outFile = null;

        // 导出删除模板
        outFile = equityBusiInfoRegist001Service.makeFile(
                "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf_del.xml",
                dtoList);

        return ExportUtil.export(
                this.getMessage(this.getPageId()) + GlobalConstant.OPEN_PARENTHESIS
                        + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS
                        + GlobalConstant.POINT + FileNameUtil.getFileExtName(outFile),
                outFile, true);

    }

    /**
     * 报送检查
     *
     * @return 检查结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("sendBeforeCheck")
    public List<ExcelValidDto> sendBeforeCheck() {
        Map<String, Object> param = RequestUtil.getPageParam();
        return equityBusiInfoRegistMService.sendBeforeCheck(param);
    }
    @ResponseBody
    @RequestMapping("submitBeforeCheck")
    public List<ExcelValidDto> submitBeforeCheck() {
        Map<String, Object> param = RequestUtil.getPageParam();
        return equityBusiInfoRegistMService.submitBeforeCheck(param);
    }
    /**
     * 中债直连报送
     *
     * @param map
     *            传入需要报送的主键
     * @return 反馈信息
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(value = "/submitChb", method = RequestMethod.POST)
    public AjaxResult doSubmit(@RequestBody Map<String, Object> map) {
        equityBusiInfoRegistMService.doSubmit(map);
        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0018"));
    }

    /**
     * 重新报送
     *
     * @param map
     *            传入需要报送的主键
     * @return 反馈信息
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(value = "/submitCbAgain", method = RequestMethod.POST)
    public AjaxResult doSubmitAgain(@RequestBody Map<String, Object> map) {
        equityBusiInfoRegistMService.doSubmit(map);
        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0018"));
    }

    /**
     * 手工处理功能
     *
     * @return 提示信息
     * @param map 条件map
     * @seea
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("doManual")
    public AjaxResult doUpdate(@RequestParam Map<String, Object> map) {
        return equityBusiInfoRegistMService.doUpdate(map);
    }

    /**
     * 手工处理功能
     *
     * @return 提示信息
     * @param map 条件map
     * @seea
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("doExportManual")
    public AjaxResult doExportUpdate() {
        // 先查询所有数据状态为删除且报送状态为未报送的数据
        Map<String, Object> map = equityBusiInfoRegistMService.selectDeletedAndUnsubmittedData();

        if (equityBusiInfoRegistMService.checkIfInBatchByTaskBeanName(
                BatchBeanName.投资者明细信息登记手工处理.getTaskId())) {
            equityBusiInfoRegistMService.updateSumStausAll(map);
        }
        else {
            return new AjaxResult(this.getMessage("REPORT_MANUAL_0008"));
        }

        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
    }

    /**
     * 取消手工处理功能
     *
     * @return 提示信息
     * @param map 条件map
     * @seea
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(value = "/cancelMannul", method = RequestMethod.POST)
    public AjaxResult cancelMannul(@RequestBody Map<String, Object> map) {
        return equityBusiInfoRegistMService.cancelMannul(map);
    }

    /**
     * @Title: querySendStatus @Description: (报送登记发送状态) @param @return 参数 @return
     *         List<SelectOptionDto> 返回类型 @throws
     */
    private List<SelectOptionDto> querySendStatus() {
        List<SelectOptionDto> listSendStatus = new ArrayList<SelectOptionDto>();
        SelectOptionDto selectOptionDto1 = new SelectOptionDto();
        selectOptionDto1.setId("01");
        selectOptionDto1.setText("发送成功");
        selectOptionDto1.setCol("");
        listSendStatus.add(selectOptionDto1);
        SelectOptionDto selectOptionDto2 = new SelectOptionDto();
        selectOptionDto2.setId("02");
        selectOptionDto2.setText("发送失败");
        selectOptionDto2.setCol("");
        listSendStatus.add(selectOptionDto2);
        return listSendStatus;
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForAllManual", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForAllManual(String fileName) {
        AjaxResult ajaxResult = new AjaxResult();
        if (!equityBusiInfoRegistMService.checkIfInBatchByTaskBeanName(
                BatchBeanName.投资者明细信息登记手工处理.getTaskId())) {
            ajaxResult.setMessage(this.getMessage("REPORT_MANUAL_0008"), MessageType.错误消息);
        }

        return ajaxResult;
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForAllCheck", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForAllCheck(String fileName) {
        AjaxResult ajaxResult = new AjaxResult();
        if (!equityBusiInfoRegistMService.checkIfInBatchByTaskBeanName(
                BatchBeanName.投资者明细信息数据检查批处理.getTaskId())) {
            ajaxResult.setMessage(this.getMessage("REPORT_MANUAL_0014"), MessageType.错误消息);
        }

        return ajaxResult;
    }

    @ResponseBody
    @RequestMapping("doCheck")
    public AjaxResult doCheck(@RequestParam Map<String, Object> map) {

        // // 处理全部数据
        // ChbBatchPara record =new ChbBatchPara();
        // record.setTaskId(BatchBeanName.投资者明细信息数据检查批处理.getTaskId());
        // record.setParaId(IDGenerator.generate());
        // record.setParas(map.get("key").toString());
        // chbBatchParaMapper.insert(record);
        equityBusiInfoRegistMService.executeBatch();
        return new AjaxResult(this.getMessage("REPORT_MANUAL_0009"));

    }

    /**
     * 导出中债excel之前检查数据量
     *
     * @param param 查询条件
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "countExportNum")
    public int countExportNum(@RequestParam Map<String, Object> param) {
        // AjaxResult ajaxResult = new AjaxResult();
        int dataNum = equityBusiInfoRegistMService.countExportNum(param);
        return dataNum;

    }

    /**
     * 导出检查
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkExport")
    public AjaxResult checkExport(@RequestBody Map<String, Object> param) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean batchExport = false;
        // 未勾选
        if (!RequestUtil.getPageParam().containsKey("keyList")) {
            // 中债报送批处理导出数据量。大于等于此参数则需要批处理导出。
            String batchExportNum = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_SUBINFO_BATCH_EXPORT_NUM);
            if (!StringUtil.isNotEmpty(batchExportNum)) {
                batchExportNum = "1000000"; // 默认为100w
            }
            int dataNum = equityBusiInfoRegistMService.countExportNum(param);
            if (dataNum >= Integer.parseInt(batchExportNum)) {
                batchExport = true;
            }
        }
        ajaxResult.setData(batchExport);
        return ajaxResult;
    }

    /**
     * 启动批处理导出
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "batchExport")
    public AjaxResult batchExport(@RequestParam Map<String, Object> param) {
        return equityBusiInfoRegistMService.batchExport(param);
    }
    /**
     * 启动批处理导出
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "batchExportAndDoManual")
    public AjaxResult batchExportAndDoManual(@RequestParam Map<String, Object> param) {
        return equityBusiInfoRegistMService.batchExportAndDoManual(param);
    }
}
