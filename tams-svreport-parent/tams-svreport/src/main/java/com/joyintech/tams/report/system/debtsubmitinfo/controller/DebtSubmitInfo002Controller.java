/*
 * 文件名：SystemFeedback001Controller.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：dingzhipeng
 * 修改时间：2017年9月21日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */
 
package com.joyintech.tams.report.system.debtsubmitinfo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.dto.EquiteBusiDetails001Dto;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.service.EquityBusiDetails001Service;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.service.EquityInfo001Service;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service.InvestorsHoldInfo001Service;
import com.joyintech.tams.report.system.debtsubmitinfo.dto.DebtSubmitInfo001Dto;
import com.joyintech.tams.report.system.debtsubmitinfo.service.DebtSubmitInfo001Service;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;

/**
 * 中债系统报送信息反馈
 * 〈功能详细描述〉
 * @author dingzhipeng
 * @version 1.0 2017年9月21日
 * @see DebtSubmitInfo002Controller
 * @since 1.0
 */
@Controller
@RequestMapping("/system/debtsubmitinfo/DebtSubmitInfo002")
@PageId("DebtSubmitInfo002")
public class DebtSubmitInfo002Controller extends BaseController{
    /**
     * 报送信息反馈Service
     */
    @Autowired
    private DebtSubmitInfo001Service debtSubmitInfo001Service;
    
    /**
     * 投资者身份信息反馈Service
     */
    @Autowired
    private  EquityInfo001Service equityInfo001Service;
    
    /**
     * 投资者持有信息反馈Service
     */
    @Autowired
    private InvestorsHoldInfo001Service investorsHoldInfo001Service;
    /**
     * 投资者明细信息Service
     */
    @Autowired
    private EquityBusiDetails001Service equityBusiDetails001Service;
    
    /**
     * 页面加载
     *
     * @param model ModelMap
     * @return pageUrl
     */
    @Override
    public String init(Model model) {
        Map<String,Object>param=RequestUtil.getPageParam();
        model.addAttribute("reportNo", param.get("reportNo"));
        model.addAttribute("repCode", param.get("repCode"));
        model.addAttribute("repName",param.get("repName"));
        model.addAttribute("reBackStatus", DictCodeListExt2.报送反馈状态_中债退回.getValue());
        super.setSelectOptionToModel(model, DictCode.报送反馈状态);
        return super.init(model);
    }
    /**
    *
    * 取得查询数据
    *
    * @return 存在于查询条件的数据
    */
    @RequestMapping(ReportGlobalConstant.SEARCH_PAGE)
    @ResponseBody
    public Page<DebtSubmitInfo001Dto> search() {
        Map<String, Object> param = RequestUtil.getPageParam();
        return debtSubmitInfo001Service.viewDetailPage(param);
    }
    
    /**
     * 将选中的数据导出excel
     * @return
     */
    @RequestMapping(value = "/exportSelect")
    public ResponseEntity<byte[]> exportSelect() {
        Map<String,Object> map = RequestUtil.getPageParam();
        String exportKeys =(String) map.get("expKeys");
        String repName = (String) map.get("repName");
        //根据逗号分隔exportKeys
        String[] paramsArr = {};
        if(!"".equals(exportKeys)){
        	 paramsArr = exportKeys.split(",");
        }
        /**
         * 根据exportKeys获取报表名称 根据投资者身份信息或持有信息来确定导出的是哪种模版的excel
         * 因为详情页面报表名称均一致，所以根据第一个来判断即可
         */
        if("投资者身份信息登记".equals(repName)){
        	List<EquityInfo001Dto> equInfoList = new ArrayList<EquityInfo001Dto>();
        	Map<String, Object> param = new HashMap<String,Object>();
        	param.put("FEEDBACKSTATUS", "04");//反馈状态为中债退回数据
        	List<String> feedBackMsgList = new ArrayList<String>();
        	List<String> seqnoList = new ArrayList<String>();
        	for(int i = 0;i<paramsArr.length;i++){
        		feedBackMsgList.add(paramsArr[i].substring(paramsArr[i].lastIndexOf("|")+1));
        		seqnoList.add(paramsArr[i].substring(paramsArr[i].indexOf(("|"), 0)+1,paramsArr[i].indexOf("|",paramsArr[i].indexOf("|",0)+1)));
        	}
        	param.put("FEEBACK_MSG", feedBackMsgList);
        	param.put("SEQNO", seqnoList);
        	equInfoList = this.equityInfo001Service.selectFeedBackData(param);
        	@SuppressWarnings("deprecation")
			File outFile = this.equityInfo001Service.makeFile(
                  "/com/joyintech/tams/report/system/debtsubmitinfo/conf/export_equhold_conf.xml", equInfoList);
        	return ExportUtil.export(
                  this.getMessage("EquityInfo001") + GlobalConstant.OPEN_PARENTHESIS
                                      + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
                                       + FileNameUtil.getFileExtName(outFile),
                  outFile, true);
        }else if("投资者持有信息登记".equals(repName)){
        	List<InvestorsHoldInfo001Dto> investHoldInfoList = new ArrayList<InvestorsHoldInfo001Dto>();
        	Map<String, Object> param = new HashMap<String,Object>();
        	param.put("FEEDBACKSTATUS", "04");//反馈状态为中债退回数据
        	List<String> feedBackMsgList = new ArrayList<String>();
        	List<String> seqnoList = new ArrayList<String>();
        	for(int i = 0;i<paramsArr.length;i++){
        		 feedBackMsgList.add(paramsArr[i].substring(paramsArr[i].lastIndexOf("|")+1));
                 seqnoList.add(paramsArr[i].substring(paramsArr[i].indexOf(("|"), 0)+1,paramsArr[i].indexOf("|",paramsArr[i].indexOf("|",0)+1)));
            	
        	}
        	param.put("FEEBACK_MSG", feedBackMsgList);
        	param.put("SEQNO", seqnoList);
        	investHoldInfoList = this.investorsHoldInfo001Service.selectInvestHoldInfoFeedBackData(param);
        	@SuppressWarnings("deprecation")
			File outFile = this.investorsHoldInfo001Service.makeFile(
                  "/com/joyintech/tams/report/system/debtsubmitinfo/conf/export_investhold_conf.xml", investHoldInfoList);
        	return ExportUtil.export(
                    this.getMessage("InvestorsHoldInfo001") + GlobalConstant.OPEN_PARENTHESIS
                                        + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
                                         + FileNameUtil.getFileExtName(outFile),
                    outFile, true);
        }else if("投资者明细信息登记".equals(repName)){
            Map<String, Object> param = new HashMap<String,Object>();
            param.put("FEEDBACKSTATUS", "04");//反馈状态为中债退回数据
            List<String> feedBackMsgList = new ArrayList<String>();
            List<String> seqnoList = new ArrayList<String>();
            for(int i = 0;i<paramsArr.length;i++){
                feedBackMsgList.add(paramsArr[i].substring(paramsArr[i].lastIndexOf("|")+1));
                seqnoList.add(paramsArr[i].substring(paramsArr[i].indexOf(("|"), 0)+1,paramsArr[i].indexOf("|",paramsArr[i].indexOf("|",0)+1)));
            }
            param.put("FEEBACK_MSG", feedBackMsgList);
            param.put("SEQNO", seqnoList);
            List<EquiteBusiDetails001Dto> equiteBusiDetails = equityBusiDetails001Service.selectBusiFeedBackData(param);
            @SuppressWarnings("deprecation")
            File outFile =this.equityBusiDetails001Service.makeFile(
                    "/com/joyintech/tams/report/system/debtsubmitinfo/conf/export_busiDetails_conf.xml",equiteBusiDetails);
            return ExportUtil.export(
                    this.getMessage("SvrEquityBusinessDetails001") + GlobalConstant.OPEN_PARENTHESIS
                            + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
                            + FileNameUtil.getFileExtName(outFile),
                    outFile, true);
        }else{
        	return null;
        }
    }   
    
    /**
     * 将数据全部导出
     * @return
     */
    @RequestMapping(value = "/export")
    public ResponseEntity<byte[]> export() {
        Map<String,Object> params = RequestUtil.getPageParam();
        String repName = (String) params.get("repName");
        params.put("FEEDBACKSTATUS", "04");
        if("投资者身份信息登记".equals(repName)){
        	List<EquityInfo001Dto> equInfoList = new ArrayList<EquityInfo001Dto>();
        	equInfoList = this.equityInfo001Service.selectAllFeedBackData(params);
        	@SuppressWarnings("deprecation")
			File outFile = this.equityInfo001Service.makeFile(
                  "/com/joyintech/tams/report/system/debtsubmitinfo/conf/export_equhold_conf.xml", equInfoList);
        	return ExportUtil.export(
                  this.getMessage("EquityInfo001") + GlobalConstant.OPEN_PARENTHESIS
                                      + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
                                       + FileNameUtil.getFileExtName(outFile),
                  outFile, true);
        }else if("投资者持有信息登记".equals(repName)){
        	List<InvestorsHoldInfo001Dto> investHoldInfoList = new ArrayList<InvestorsHoldInfo001Dto>();
        	investHoldInfoList = this.investorsHoldInfo001Service.selectAllInvestHoldInfoFeedBackData(params);
        	@SuppressWarnings("deprecation")
			File outFile = this.investorsHoldInfo001Service.makeFile(
                  "/com/joyintech/tams/report/system/debtsubmitinfo/conf/export_investhold_conf.xml", investHoldInfoList);
        	return ExportUtil.export(
                    this.getMessage("InvestorsHoldInfo001") + GlobalConstant.OPEN_PARENTHESIS
                                        + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
                                         + FileNameUtil.getFileExtName(outFile),
                    outFile, true);
        }else if("投资者明细信息登记".equals(repName)){
            List<EquiteBusiDetails001Dto> equiteBusiDetails = equityBusiDetails001Service.selectAllBusiFeedBackData(params);
            @SuppressWarnings("deprecation")
            File outFile =this.equityBusiDetails001Service.makeFile(
                "/com/joyintech/tams/report/submitmaintenance/businessdetailed/conf/export_conf.xml",equiteBusiDetails);
            return ExportUtil.export(
                    this.getMessage("SvrEquityBusinessDetails001") + GlobalConstant.OPEN_PARENTHESIS
                            + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
                            + FileNameUtil.getFileExtName(outFile),
                    outFile, true);
        }else{
        	return null;
        }
    }   
}