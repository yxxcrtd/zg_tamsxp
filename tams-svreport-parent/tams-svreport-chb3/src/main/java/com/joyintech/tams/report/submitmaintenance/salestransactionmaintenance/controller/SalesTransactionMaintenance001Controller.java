package com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.system.constant.SystemGlobalConstant;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.dto.SalesTransactionMaintenanceDto;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.service.SalesTransactionMaintenance001Service;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.service.SalesTransactionMaintenanceService;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.tams.systemtools.interf.IPlatformDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.dto.ExcelValidDto;

import javax.validation.Valid;

/**
 * 销售结算资金交易信息维护
 */
@Controller
@PageId("SalesTransactionMaintenance001")
@RequestMapping("/report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance001")
public class SalesTransactionMaintenance001Controller extends BaseController {

    /**
     * dao
     */
    @Autowired
    private SalesTransactionMaintenance001Service salesTransactionmaintenance001Service;
    @Autowired
    private SalesTransactionMaintenanceService salesTransactionmaintenanceService;
    /**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 平台日期
    @Autowired(required = false)
    private IPlatformDate iPlatformDate;
    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        super.setSelectOptionToModel(model, DictCode.维护数据状态, DictCode.销售结算资金流动方向);
        model.addAttribute("sysDate", iPlatformDate.getSysDateStr());
        return super.init(model);
    }

    /**
     * 分页查询列表
     *
     * @return Page
     */
    @ResponseBody
    @RequestMapping(value = "findPage")
    public Page<SalesTransactionMaintenanceDto> findPage() {
        Map<String, Object> param = RequestUtil.getPageParam();
        return salesTransactionmaintenanceService.searchForPage(param);
    }
    /**
     *
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Valid SalesTransactionMaintenanceDto salesTransactionMaintenanceDto,
                            BindingResult br) {
        return commonCheck(br, salesTransactionMaintenanceDto.getOperateType());
    }

    /**
     * 导出数据
     *
     * @return ResponseEntity<byte[]>
     * @throws Exception ExportUtil.export
     * @see
     * @since 1.0
     */
    @RequestMapping(ACTION_EXPORT)
    public ResponseEntity<byte[]> export()
            throws Exception {
        String datas = this.getParam("datas");
        Map<String, Object> params= RequestUtil.getPageParam();
        List<SalesTransactionMaintenanceDto> listdto ;
        if (datas == null) {
            listdto = salesTransactionmaintenanceService.queryAllList(params);
        }
        else {
            listdto = JsonUtils.json2List(datas, SalesTransactionMaintenanceDto.class);
        }
        File outFile = salesTransactionmaintenanceService.makeFile(
                "/com/joyintech/tams/report/submitmaintenance/salestransactionmaintenance/conf/export_conf.xml",
                listdto);
        return ExportUtil.export(
                "销售结算资金交易维护" + SystemGlobalConstant.OPEN_PARENTHESIS
                        + DateUtil.format(DateUtil.platDateFromCache())
                        + SystemGlobalConstant.CLOSE_PARENTHESIS
                        + SystemGlobalConstant.POINT
                        + FileNameUtil.getFileExtName(outFile),
                outFile, true);
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForImportData(String fileName)  {
        return salesTransactionmaintenanceService.checkConfirmMsgForImportData(fileName);
    }


    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    @ResponseBody
    @RequestMapping("importFile")
    public List<ExcelValidDto> importFile(String fileName) {
        return this.salesTransactionmaintenanceService.importData(fileName);
    }

    /**
     *
     * 删除
     * <功能详细描述>
     * @param dto 删除参数
     * @param br 删除参数
     * @return 删除返回参数
     * @throws Exception 抛出异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(@RequestBody @Validated({
            ValidateGroupDelete.class}) SalesTransactionMaintenanceDto dto, BindingResult br)
            throws Exception {
        if(!(dto.getSelectedItem().isEmpty())){
            if (salesTransactionmaintenanceService.deleteByPrimaryKey(dto)) {
                return new AjaxResult("删除成功");
            }
            }
        return new AjaxResult("删除失败");
    }

    /**
     * 提供销售结算资金服务机构代码下拉框
     */
    @ResponseBody
    @RequestMapping("/getInstitutionCode")
    public List<SuggestDto> getInstitutionCode()
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("institutionCode", RequestUtil.getPageParam().get("institutionCode"));
        List<SuggestDto> temp = salesTransactionmaintenanceService.getInstitutionCode(map);
        return temp;
    }

    /**
     * 登记编码下拉框
     */
    @ResponseBody
    @RequestMapping("/getPrdRegistCode")
    public List<SuggestDto> getPrdRegistCode()
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("prdRegistCode", RequestUtil.getPageParam().get("prdRegistCode"));
        List<SuggestDto> temp = salesTransactionmaintenanceService.getPrdRegistCode(map);
        return temp;
    }

    /**
     * 销售结算资金交易流水号下拉框
     */
    @RequestMapping("/getTradeNo")
    @ResponseBody
    public List<SelectOptionDto> getTradeNo() {
        return salesTransactionmaintenanceService.getTradeNo(RequestUtil.getPageParam());
    }

    /**
     * 修改校验
     */
    @ResponseBody
    @RequestMapping("editCheck")
    public AjaxResult check(@RequestBody SalesTransactionMaintenanceDto salesTransactionMaintenanceDto) {
        AjaxResult ar =new AjaxResult();
        List<ValidMessage> errorList = ar.getErrorList();
        if (StringUtil.equals(salesTransactionMaintenanceDto.getStatusValid(), DictCodeListExt1.维护数据状态_删除.getValue())) {
            errorList.add(new ValidMessage("数据状态为删除的数据不允许修改！"));
            ar.setErrorList(errorList);
        }
        return ar;
    }
}
