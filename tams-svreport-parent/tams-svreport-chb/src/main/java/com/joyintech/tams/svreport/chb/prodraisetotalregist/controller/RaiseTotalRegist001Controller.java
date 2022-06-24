package com.joyintech.tams.svreport.chb.prodraisetotalregist.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.component.svrreport.chb.dto.ChbPrdRaisetotSubDto;
import com.joyintech.framework.util.*;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chbsd.prdraisetotsub.service.SdProdRaisetotBatchService;
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
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.svreport.chb.prodraisetotalregist.dto.RaiseTotalRegist001Dto;
import com.joyintech.tams.svreport.chb.prodraisetotalregist.service.RaiseTotalRegist001Service;
import com.joyintech.tams.svreport.chb.prodraisetotalregist.service.RaiseTotalRegistMService;

/**
 * 募集总量登记Controller
 * 〈功能详细描述〉
 * @author jiangxiao
 * @version 3.0 2019年01月21日
 * @see RaiseTotalRegist001Controller
 * @since 3.0
 */
@Controller
@PageId("SvrRaiseTotalRegist001")
@RequestMapping("/chb/prodraisetotalregist/RaiseTotalRegist001")
public class RaiseTotalRegist001Controller extends BaseController{
	@Autowired
	private RaiseTotalRegist001Service raiseTotalRegist001Service;
	@Autowired
	private RaiseTotalRegistMService raiseTotalRegistMService;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SdProdRaisetotBatchService sdProdRaisetotBatchService;
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
    	super.setSelectOptionToModel(model, DictCode.报送数据状态,
                DictCode.报送状态,DictCode.报送反馈状态,
                DictCode.是否_中债);
            model.addAttribute("indentifyMark",RequestUtil.getParam("indentifyMark"));
            return super.init(model);
    }
    
    /**
     * @Description 产品募集总量登记分页查询
     * @return Page<InvestorsHoldInfo001Dto>
     * @version 3.0 2019年1月22日
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<RaiseTotalRegist001Dto> selectAllData() {
        Page<RaiseTotalRegist001Dto> page = raiseTotalRegistMService.selectForPage(RequestUtil.getPageParam());
        return page;
    }
    
    /**
    * 业务画面导出事件
    * @return
    * @since 1.0
    */
   @ResponseBody
   @RequestMapping("export")
   public ResponseEntity<byte[]> export() {
   	Map<String, Object> pageParam = RequestUtil.getPageParam();
   	if(pageParam.containsKey("keyList")){
         List<RaiseTotalRegist001Dto> dtoList = raiseTotalRegistMService.selectForExport(RequestUtil.getParameters(getRequest()));
         File outFile = raiseTotalRegist001Service.makeFile(
             "/com/joyintech/tams/svreport/chb/prodraisetotalregist/conf/export_conf.xml", dtoList);
         return ExportUtil.export(
             this.getMessage(this.getPageId()) + GlobalConstant.OPEN_PARENTHESIS
                                  + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS
                                  + GlobalConstant.POINT + FileNameUtil.getFileExtName(outFile),
             outFile, true);
   	}
   	else{
        //add by zhoubin 20220530 start
           //处理只有删除状态时导出空的excle文件
        Object status = pageParam.get("status");
        if (Objects.equals(DictCodeListExt2.报送数据状态_删除.getValue(),status)) {
            pageParam.put("status","无效");
        } else{
            pageParam.put("status",DictCodeListExt2.报送数据状态_新建.getValue());
        }
        //add by zhoubin 20220530 end
        return raiseTotalRegist001Service.export(this.getPageId(), pageParam);
   	}

   }
   
   /**
    *手工处理功能
    * @return 提示信息
    * @param map 条件map
    */
   @ResponseBody
   @RequestMapping(value = "/manual" ,method = RequestMethod.POST)
   public AjaxResult doUpdate(@RequestBody Map<String,Object> map) {
	   raiseTotalRegistMService.updateSumStaus(map);
       return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
   }
   
   /**
    * @Description 取消手工处理
    * @param map
    * @return
    * @return AjaxResult
    * @version 3.0 2019年1月24日
    */
    @ResponseBody
	@RequestMapping(value = "/cancelMannul", method = RequestMethod.POST)
	public AjaxResult cancelMannul(@RequestBody Map<String, Object> map) {
        String result = raiseTotalRegistMService.cancelMannul(map);
	   if("fail".equals(result)){
			AjaxResult ajaxresult =  new AjaxResult("有未报送的数据",MessageType.错误消息);
			ajaxresult.setStatuCode(HttpStatus.OK.value());
			return ajaxresult;
		}
		return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
	}
    
    @ResponseBody
    @RequestMapping("reportCheck")
    public Map<String,Object> reportCheck() {
        return raiseTotalRegistMService.reportCheck(RequestUtil.getPageParam());
    }

    /**start  中债一二期直连代码  add  by  wangzehui  20201012*/
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
    public AjaxResult doSubmit(@RequestBody Map<String, Object> param) {

        List<RaiseTotalRegist001Dto> list=new ArrayList<RaiseTotalRegist001Dto>();
        // 选择行数据时
        if (param.containsKey("keyList")) {
            String keyList = param.get("keyList").toString();
            try {
                list = JsonUtils.json2List(keyList,
                        RaiseTotalRegist001Dto.class);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }else{
            Page<RaiseTotalRegist001Dto> page = raiseTotalRegistMService.selectForPage(RequestUtil.getPageParam());
            if(page!=null&&page.getRoot()!=null&&page.getRoot().size()>0){
                list = page.getRoot();
            }
            //查询数据
        }

        //报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
        List<ChbPrdRaisetotSubDto> dataList = new ArrayList<ChbPrdRaisetotSubDto>();
        for(RaiseTotalRegist001Dto sdDto:list){
            ChbPrdRaisetotSubDto target = new ChbPrdRaisetotSubDto();
            BeanUtil.copyPropertiesIgnoreNull(target, sdDto);
            dataList.add(target);
        }

        SendDataCheckType sendDataCheckType = SendDataCheckType.报送;
        if (StringUtil.equals(param.get("opertionType"), "reSubmitChb")){
            sendDataCheckType = SendDataCheckType.重新报送;
        }
        sdProdRaisetotBatchService.sendBatchCommon(dataList, sendDataCheckType);

        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0018"));
    }

    /**end  中债一二期直连代码  add  by  wangzehui  20201012*/
}
