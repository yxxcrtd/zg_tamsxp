package com.joyintech.tams.svreport.chb.astposregist.controller;

import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.component.svrreport.chb.dto.ChbAstPosSub;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import com.joyintech.tams.svreport.chb.astposregist.service.ChbAstPosRegist001Service;
import com.joyintech.tams.svreport.chb.astposregist.service.ChbAstPosRegistMService;
import com.joyintech.tams.svreport.chb.prodterminationregist.service.ProdTerminationRegist001Service;
import com.joyintech.tams.svreport.chb.prodterminationregist.service.ProdTerminationRegistMService;
import com.joyintech.tams.svreport.chbsd.astpossub.service.SdAstPosSubRegBatchService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Description 资产持仓登记Controller
 * @author dxy
 */
@Controller
@PageId("SvrChbAstPosRegist001")
@RequestMapping("/chb/astposregist/ChbAstPosRegist001")
public class ChbAstPosRegist001Controller extends BaseController {

	@Autowired
	private ProdTerminationRegist001Service prodTerminationRegist001Service;
	@Autowired
	private ProdTerminationRegistMService prodTerminationRegistMService;
    @Autowired
    private ChbAstPosRegistMService chbAstPosRegistMService;
    @Autowired
    private ChbAstPosRegist001Service chbAstPosRegist001Service;
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SdAstPosSubRegBatchService sdAstPosSubRegBatchService;
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
		}else {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime( DateUtil.platDateFromCache());
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
            model.addAttribute("isMain", "02");
        }
        model.addAttribute("SVR_REPORT_CODE", DictCodeListExt2.报表名称_资产持仓登记.getValue());
        model.addAttribute("FILE_HANDLE_STATUS", DictCodeListExt3.中债一二三期文件处理状态_未处理.getValue());
    	super.setSelectOptionToModel(model, DictCode.报送数据状态,
                DictCode.报送状态,DictCode.报送反馈状态,DictCode.中债持仓类型,DictCode.资产穿透情况);
    	return super.init(model);
    }
    
    /**
     * @Description 资产持仓登记页面查询
     * @return Page<ChbAstPosRegist001Dto>
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<ChbAstPosRegist001Dto> selectAllData() {
        Page<ChbAstPosRegist001Dto> page = chbAstPosRegistMService.selectForPage(RequestUtil.getPageParam());
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
        List<ChbAstPosRegist001Dto> dtoList = new LinkedList<>();
    	if(pageParam.containsKey("keyList")){
          dtoList = chbAstPosRegistMService.selectForExport(RequestUtil.getParameters(getRequest()));

    	} else {
            dtoList =chbAstPosRegist001Service.selectAllData(pageParam);
        }
    	String termId = null;
    	if(CollectionUtils.isNotEmpty(dtoList)){
            termId = dtoList.get(0).getTermid();
        }
        File outFile = chbAstPosRegist001Service.makeFile(
                "/com/joyintech/tams/svreport/chb/astposregist/conf/export_conf.xml", dtoList);
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
        chbAstPosRegistMService.updateSumStaus(map);
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
    	String result = chbAstPosRegistMService.cancelMannul(map);
    	if("fail".equals(result)){
			AjaxResult ajaxresult =  new AjaxResult("有未报送的数据",MessageType.错误消息);
			ajaxresult.setStatuCode(HttpStatus.OK.value());
			return ajaxresult;
		}
		return new AjaxResult(this.getMessage("REPORT_COMMON_I_0008"));
	}

    @ResponseBody
    @RequestMapping(value = "/getCount", method = RequestMethod.POST)
    public Boolean getCount(@RequestBody Map<String, Object> map) {
        Boolean  flag=true;
        Page<ChbAstPosRegist001Dto> page = chbAstPosRegistMService.selectForPage(map);
        List<ChbAstPosRegist001Dto> dtoList = page.getRoot();
        if(CollectionUtils.isNotEmpty(dtoList)){
            //以期数分组
            Map<String,List<ChbAstPosRegist001Dto>> posMap=dtoList.stream().collect(Collectors.groupingBy(ChbAstPosRegist001Dto::getTermid));
            if(posMap.keySet().size()>1){
                flag=false;
            }
        }
        return flag;
    }
    
    /**   
    * @Function: ProdTerminationRegist001Controller.java
    * @Description: 产品终止登记数据检查
    *
    * @return：产品终止登记数据检查结果
    *
    * @author: 王冠/wangguan
    * @date: 2019年6月25日 上午10:26:10 
    */
    @ResponseBody
    @RequestMapping("reportCheck")
    public Map<String,Object> reportCheck() {
    	return chbAstPosRegistMService.reportCheck(RequestUtil.getPageParam());
    }

    /**start  中债一二期直连代码  add  by  wangzehui  20201012*/

    /**
     * 中债直连报送
     *
     * @param param
     *            传入需要报送的主键
     * @return 反馈信息
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(value = "/submitChb", method = RequestMethod.POST)
    public AjaxResult doSubmit(@RequestBody Map<String, Object> param) throws Exception {

        List<ChbAstPosRegist001Dto> list=new ArrayList<>();
        // 选择行数据时
        if (param.containsKey("keyList")) {
            list = chbAstPosRegistMService.selectForExport(RequestUtil.getParameters(getRequest()));
//            String keyList = param.get("keyList").toString();
//            try {
//                list = JsonUtils.json2List(keyList,
//                        ChbAstPosRegist001Dto.class);
//            }
//            catch (IOException e) {
//                // e.printStackTrace();
//                logger.error(e.getMessage(), e);
//            }
        }else{
            Page<ChbAstPosRegist001Dto> page = chbAstPosRegistMService.selectForPage(RequestUtil.getPageParam());
            if(page!=null&&page.getRoot()!=null&&page.getRoot().size()>0){
                list = page.getRoot();
            }
            //查询数据
        }
        /***0722升级**/
        //报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
        List<ChbAstPosSub> dataList = new ArrayList<>();
        for(ChbAstPosRegist001Dto sdDto:list){
            ChbAstPosSub target = new ChbAstPosSub();
            BeanUtil.copyPropertiesIgnoreNull(target, sdDto);
            dataList.add(target);
        }
        /***0722升级**/
        SendDataCheckType sendDataCheckType = SendDataCheckType.报送;
        if (StringUtil.equals(param.get("opertionType"), "reSubmitChb")){
            sendDataCheckType = SendDataCheckType.重新报送;
        }
        sdAstPosSubRegBatchService.sendBatchCommon(dataList, sendDataCheckType);

        return new AjaxResult(this.getMessage("REPORT_COMMON_I_0018"));
    }

    /**end  中债一二期直连代码  add  by  wangzehui  20201012*/

    /**
     *
     * 查询功能(无分页)
     * <功能详细描述>
     * @return 存储了查询结果的list
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/searchInfo")
    public List<ChbAstPosRegist001Dto> searchInfo(@RequestParam Map<String,Object> param){
        return chbAstPosRegistMService.queryForAstPosRegistList(param);
    }

    @ResponseBody
    @RequestMapping(value = "checkExport")
    public AjaxResult checkExport(@RequestBody Map<String, Object> param) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean batchExport = false;
        // 未勾选
        if (!RequestUtil.getPageParam().containsKey("keyList")) {
            // 中债报送批处理导出数据量。大于等于此参数则需要批处理导出。
            String batchExportNum = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_SUBINFO_BATCH_EXPORT_NUM);
            if (StringUtil.isEmpty(batchExportNum)) {
                // 默认为100w
                batchExportNum = "1000000";
            }
            int dataNum = chbAstPosRegistMService.countExportNum(param);
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
        return chbAstPosRegistMService.batchExport(param);
    }

    /**
     * 启动批处理导出和手工处理
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "batchExportAndDoManual")
    public AjaxResult batchExportAndDoManual(@RequestParam Map<String, Object> param) {
        return chbAstPosRegistMService.batchExportAndDoManual(param);
    }

    /**
     * 会计科目下拉框
     * @return 产品名称列表
     */
    @RequestMapping("/getSubjectNameList")
    @ResponseBody
    public List<SelectOptionDto> getSubjectNameList() {
        return chbAstPosRegistMService.getSubjectNameList(RequestUtil.getPageParam());
    }
}
