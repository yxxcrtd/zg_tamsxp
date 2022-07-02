package com.joyintech.tams.report.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.systemtools.interf.IPlatformDate;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.home.dto.InfoAreaDto;
import com.joyintech.tams.report.home.dto.SubmitInformationRegistrationDto;
import com.joyintech.tams.report.interf.common.constant.DateUtil;

import javax.annotation.Resource;

/**
 * 主页
 *
 * @author zhangzhongliang
 * @version 1.0 2019年4月19日
 * @see ReportHomeMService
 * @since 1.0
 */
@Service
public class ReportHomeMService extends BaseService<ITableRow>{

    @Resource
    private IPlatformDate iPlatformDate;

	  /**
    *
    * 取得信息表区域的数据
    *
    * @return 信息表区域的统计数据
    */
    @SuppressWarnings("unchecked")
    public Map<String,InfoAreaDto> searchInfoArea() {
        return restTemplate.getForObject(
        		SvrServiceApplication.COMMON_SERVICE_NAME + "/ReportHome/searchInfoArea",
        		Map.class);
    }
    /**
    *
    * 取得报送信息登记的数据
    *
    * @return 报送信息登记的统计数据
    */
    @SuppressWarnings("unchecked")
    public Map<String,SubmitInformationRegistrationDto> searchReportInfo(String date,List<String> reportlist) {
    	Map<String,Object> param = new HashMap<>();
    	param.put("searchDate", date);
    	param.put("reportlist", reportlist);
        if(CommonUtil.isBAmodel()){
            param.put("model_type", "01");
            param.put("orgCode", CommonUtil.getTreeInstCode());
        }
        return restTemplate.postForObject(
        		SvrServiceApplication.COMMON_SERVICE_NAME + "/ReportHome/searchReportInfo",param,
        		Map.class);
    }
    /**
    *
    * 取得报送信息登记的数据
    *
    * @return 报送信息登记的统计数据
    */
    @SuppressWarnings("unchecked")
    public List<String> selectMenulist() {
        return restTemplate.getForObject(
        		SvrServiceApplication.COMMON_SERVICE_NAME + "/ReportHome/selectMenulist",
        		List.class);
    }
    /**
    *
    * 取得可批量导出删除模板的数据
    *
    * @return 可批量导出删除模板的统计数据
    */
    @SuppressWarnings("unchecked")
    public Map<String,InfoAreaDto> searcheBatchxportTemplates() {
        return restTemplate.getForObject(
        		SvrServiceApplication.COMMON_SERVICE_NAME + "/ReportHome/searcheBatchexportTemplates",
        		Map.class);
    }
    /**
     *
     * 取得可批量导出删除模板的数据
     *
     * @return 可批量导出删除模板的统计数据
     */
    public AjaxResult getSystemNow(){
        AjaxResult result = new AjaxResult();
        result.setData(DateUtil.format(new DateTime(iPlatformDate.getSysDate()).plusDays(-1).toDate()));
        return result;
    }

    /**
    *
    * 取得持有信息数据来源
    *
    * @return 持有信息数据来源
    */
   public AjaxResult getHoldInfoSourceInfo() {
       AjaxResult result = new AjaxResult();
       Map<String, String> dataMap = new HashMap<String, String>();
       String holdInfoSource = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, ReportGlobalConstant.SYS_PARA_HOLD_INFO_SOURCE);
       dataMap.put("HOLD_INFO_SOURCE", holdInfoSource);
       dataMap.put("HOLD_INFO_SOURCE_01", DictCodeListExt1.持有信息数据来源_业务明细.getValue());
       result.setData(dataMap);
       return result;
   }
	
}
