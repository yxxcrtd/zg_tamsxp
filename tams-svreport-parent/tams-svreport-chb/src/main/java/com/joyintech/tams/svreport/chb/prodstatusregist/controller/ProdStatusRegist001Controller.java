package com.joyintech.tams.svreport.chb.prodstatusregist.controller;

import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.*;
import com.joyintech.component.svrreport.chb.dto.ChbPrdStatusSub;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.svreport.chb.prodstatusregist.dto.ProdStatusRegist001Dto;
import com.joyintech.tams.svreport.chb.prodstatusregist.service.ProdStatusRegist001Service;
import com.joyintech.tams.svreport.chb.prodstatusregist.service.ProdStatusRegistMService;
import com.joyintech.tams.svreport.chbsd.prodstatusregistsub.service.SdProdStatusRegistBatchService;
import org.apache.commons.collections4.CollectionUtils;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Description 产品状态登记Controller
 * @author zhoubin
 * @version 3.0 2022年5月28日
 * @since 3.0
 */
@Controller
@PageId("SvrProdStatusRegist001")
@RequestMapping("/chb/prodstatusregist/ProdStatusRegist001")
public class ProdStatusRegist001Controller extends BaseController {

	@Autowired
	private ProdStatusRegist001Service prodStatusRegist001Service;
	@Autowired
	private ProdStatusRegistMService prodStatusRegistMService;

    @Autowired
    private SdProdStatusRegistBatchService sdProdStatusRegistBatchService;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
	/**
     * 初始化
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
        model.addAttribute("SVR_REPORT_CODE", DictCodeListExt2.报表名称_产品状态登记.getValue());
        model.addAttribute("FILE_HANDLE_STATUS", DictCodeListExt3.中债一二三期文件处理状态_未处理.getValue());
    	super.setSelectOptionToModel(model, DictCode.报送数据状态,
                DictCode.报送状态,DictCode.报送反馈状态);
            model.addAttribute("indentifyMark",RequestUtil.getParam("indentifyMark"));
            return super.init(model);
    }
    
    /**
     * @Description 产品状态登记页面查询
     * @return Page<ProdStatusRegist001Dto>
     * @version 3.0 2022年5月28日
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<ProdStatusRegist001Dto> selectAllData() {
        Page<ProdStatusRegist001Dto> page = prodStatusRegistMService.selectForPage(RequestUtil.getPageParam());
        return page;
    }
    
    /**
     * 业务画面导出事件
     * @return
     * @since 3.0
     */
    @ResponseBody
    @RequestMapping("export")
    public ResponseEntity<byte[]> export() {
    	Map<String, Object> pageParam = RequestUtil.getPageParam();
        List<ProdStatusRegist001Dto> dtoList = prodStatusRegistMService.selectForExport(RequestUtil.getParameters(getRequest()));
        File outFile = prodStatusRegist001Service.makeFile(
                "/com/joyintech/tams/svreport/chb/prodstatusregist/conf/export_conf.xml", dtoList);
        String termId = null;
        if(CollectionUtils.isNotEmpty(dtoList)){
            termId = dtoList.get(0).getTermid();
        }
        return ExportUtil.export(
                this.getMessage(this.getPageId()) + GlobalConstant.OPEN_PARENTHESIS
                        + termId + GlobalConstant.CLOSE_PARENTHESIS
                        + GlobalConstant.POINT + FileNameUtil.getFileExtName(outFile),
                outFile, true);
    }
    /**
     *手工处理功能
     * @return 提示信息
     * @param map 条件map
     */
    @ResponseBody
    @RequestMapping(value = SVReportGlobalConstant.PAGE_UPDATE ,method = RequestMethod.POST)
    public AjaxResult doUpdate(@RequestBody Map<String,Object> map) {
        prodStatusRegistMService.updateSumStaus(map);
        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
    }
    
    /**
     * @Description 取消手工处理
     * @param map
     * @return
     * @return AjaxResult
     * @version  2022年5月28日
     */
    @ResponseBody
	@RequestMapping(value = "/cancelMannul", method = RequestMethod.POST)
	public AjaxResult cancelMannul(@RequestBody Map<String, Object> map) {
    	String result = prodStatusRegistMService.cancelMannul(map);
    	if("fail".equals(result)){
			AjaxResult ajaxresult =  new AjaxResult("有未报送的数据",MessageType.错误消息);
			ajaxresult.setStatuCode(HttpStatus.OK.value());
			return ajaxresult;
		}
		return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
	}
    
    /**
    * @Description: 产品状态登记数据检查
    * @date:  2022年5月28日
    */
    @ResponseBody
    @RequestMapping("reportCheck")
    public Map<String,Object> reportCheck() {
    	return prodStatusRegistMService.reportCheck(RequestUtil.getPageParam());
    }



    /**
     * 中债直连报送
     * 传入需要报送的主键
     * @return 反馈信息
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(value = "/submitChb", method = RequestMethod.POST)
    public AjaxResult doSubmit(@RequestBody Map<String, Object> param) throws Exception {

        List<ProdStatusRegist001Dto> list=new ArrayList<>();
        // 选择行数据时
        if (param.containsKey("keyList")) {
            String keyList = param.get("keyList").toString();
            try {
                list = JsonUtils.json2List(keyList,
                        ProdStatusRegist001Dto.class);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }else{
            Page<ProdStatusRegist001Dto> page = prodStatusRegistMService.selectForPage(RequestUtil.getPageParam());
            if(page!=null&&page.getRoot()!=null&&page.getRoot().size()>0){
                list = page.getRoot();
            }
            //查询数据
        }

        //报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
        List<ChbPrdStatusSub> dataList = new ArrayList<>();
        for(ProdStatusRegist001Dto sdDto:list){
            ChbPrdStatusSub target = new ChbPrdStatusSub();
            BeanUtil.copyPropertiesIgnoreNull(target, sdDto);
            dataList.add(target);
        }

        SendDataCheckType sendDataCheckType = SendDataCheckType.报送;
        if (StringUtil.equals(param.get("opertionType"), "reSubmitChb")){
            sendDataCheckType = SendDataCheckType.重新报送;
        }
        sdProdStatusRegistBatchService.sendBatchCommon(dataList, sendDataCheckType);

        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0018"));
    }
}
