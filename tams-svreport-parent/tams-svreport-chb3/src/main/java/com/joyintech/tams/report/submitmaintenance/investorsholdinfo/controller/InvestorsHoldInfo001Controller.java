/*
 * 文件名：InvestorsHoldInfo001Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：天津滨海农商行理财资管系统
 * 修改人：dingzhipeng
 * 修改时间：2018年5月7日
 * 修改内容：新建
 * 系统名称：天津滨海农商行理财资管系统
 */

package com.joyintech.tams.report.submitmaintenance.investorsholdinfo.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service.InvestorsHoldInfo001Service;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service.InvestorsHoldInfoMService;
import com.joyintech.tams.systemtools.util.CommonUtil;


/**
 * 投资者持有信息维护
 * 〈功能详细描述〉
 * @author dingzhipeng
 * @version 1.0 2018年5月7日
 * @see InvestorsHoldInfo001Controller
 * @since 1.0
 */
@Controller
@PageId("InvestorsHoldInfo001")
@RequestMapping("/svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo001")
public class InvestorsHoldInfo001Controller extends BaseController {
    /**
     * 业务逻辑Service
     */
	@Autowired
	private InvestorsHoldInfo001Service investorsHoldInfo001Service;
    @Autowired
    private InvestorsHoldInfoMService investorsHoldInfoMService;
    /**
     * 页面初始化
     * {@inheritDoc}
     */
    @Override
    public String init(Model model) {
        Map<String, Object> map = RequestUtil.getPageParam();
        if(map.containsKey("status")||map.containsKey("interfType")){
            model.addAttribute("isFromMain","01");
            model.addAttribute("status",DictCodeListExt1.维护数据状态_待处理.getValue());
            model.addAttribute("searchDataStart", map.get("searchDataStart"));
			model.addAttribute("searchDataEnd", map.get("searchDataEnd"));
        }
        else{
            model.addAttribute("isFromMain","02");
        }
        String myOrgCode=CommonUtil.getOrgCode();
        if(StringUtil.isNotEmpty(myOrgCode)){
            model.addAttribute("MY_AGENCY_CODE",myOrgCode);
//            model.addAttribute("AGENCY_CODE_LIST",JsonUtils.objectArray2Json(CommonUtil.getOrgCodeList(myOrgCode).toArray()));
        }
        String dateStr = DateUtil.format(new Date());
        model.addAttribute("DATE_START",dateStr);
        model.addAttribute("DATE_END",dateStr);
        model.addAttribute("statusE", DictCodeListExt1.维护数据状态_有效.getValue());
        super.setSelectOptionToModel(model, DictCode.维护数据状态);
        return super.init(model);
    }

    /**
     *
     * 业务画面查询事件
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<InvestorsHoldInfo001Dto> selectAllData() {
        Page<InvestorsHoldInfo001Dto> page = investorsHoldInfoMService.selectForPage(RequestUtil.getPageParam());
        return page;
    }
    /**
     *
     * 业务画面导出事件
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("export")
    public ResponseEntity<byte[]> export() {
    	Map<String, Object> pageParam = RequestUtil.getPageParam();
    	if(pageParam.containsKey("keyList")){
          List<InvestorsHoldInfo001Dto> dtoList = investorsHoldInfoMService.selectAllList(RequestUtil.getParameters(getRequest()));
          File outFile = investorsHoldInfo001Service.makeFile(
              "/com/joyintech/tams/report/submitmaintenance/investorsholdinfo/conf/export_conf.xml", dtoList);
          return ExportUtil.export(
              this.getMessage(this.getPageId()) + GlobalConstant.OPEN_PARENTHESIS
                                   + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS
                                   + GlobalConstant.POINT + FileNameUtil.getFileExtName(outFile),
              outFile, true);
    	}
    	else{
            return investorsHoldInfo001Service.export(this.getPageId(), RequestUtil.getPageParam());
    	}

    }
    /**
     * 删除的check
     *
     * @param dto dto
     * @param result BindingResult
     * @param operateType 操作类型
     * @return check结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("checkDelete")
    public AjaxResult check(@RequestBody @Validated({ValidateGroupDelete.class}) InvestorsHoldInfo001Dto dto, BindingResult result) {
        return commonCheck(result, dto.getOperateType());
    }

    /**
     *
     * 删除
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(ACTION_DELETE)
    public AjaxResult delete(@RequestBody @Validated({ValidateGroupDelete.class}) InvestorsHoldInfo001Dto dto, BindingResult result){
        String msg="删除成功！";
        investorsHoldInfoMService.delete(dto);
        return new AjaxResult(msg);
    }

    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    @ResponseBody
    @RequestMapping("/importFile")
    public List<ExcelValidDto> importData(String fileName) {
        return this.investorsHoldInfoMService.importData(fileName);
    }
    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForImportData(String fileName){
        return investorsHoldInfoMService.checkConfirmMsgForImportData(fileName);
    }
    
    
    
    /**
     * 导入代销数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    @ResponseBody
    @RequestMapping("/chbImportFile")
    public List<ExcelValidDto> importChbData(String fileName) {
        return this.investorsHoldInfoMService.importChbData(fileName);
    }
    
    
    /**
     *导入代销数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForChbImportData", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForChbImportData(String fileName) {
        return investorsHoldInfoMService.checkConfirmMsgForChbImportData(fileName);
    }
}
