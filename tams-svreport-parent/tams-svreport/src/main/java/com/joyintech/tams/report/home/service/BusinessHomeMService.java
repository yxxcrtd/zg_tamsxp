package com.joyintech.tams.report.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.systemtools.interf.IPlatformDate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.home.dto.VbusinessHomeDto;
import com.joyintech.tams.report.interf.common.constant.DateUtil;

@Service
public class BusinessHomeMService extends BaseService<ITableRow>{

    @Autowired
    private IPlatformDate iPlatformDate;

    public List<VbusinessHomeDto> searchBusinessHomeInfo(String searchDate, List<String> menulist) {
        Map<String,Object> param = new HashMap<>();
        param.put("searchDate", searchDate);
        param.put("menulist", menulist);
        return restTemplate.postForList(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/BusinessHome/selectBusinessHomeInfo",param,
                VbusinessHomeDto.class);
    }

    public AjaxResult getSystemNow(){
        AjaxResult result = new AjaxResult();
        result.setData(DateUtil.format(iPlatformDate.getSysDate()));
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<String> selectMenulist() {
        return restTemplate.getForObject(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/BusinessHome/selectMenulist",
                List.class);
    }
    /**
     * 一体化业务首页查询
     * @param searchDate 查询日期
     * @param menulist 菜单
     * @return
     */
    public List<VbusinessHomeDto> searchBusinessHomeInfoAll(String searchDate, List<String> menulist) {
        Map<String,Object> param = new HashMap<>();
        param.put("searchDate", searchDate);
        param.put("menulist", menulist);
        return restTemplate.postForList(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/BusinessHome/selectBusinessHomeInfoAll",param,
                VbusinessHomeDto.class);
    }

}
