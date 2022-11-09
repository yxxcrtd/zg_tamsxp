/*
 * 文件名：RskValSumname002Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月17日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.dto.RskValSumnameDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author qiangchengxi
 * @version 1.0 2018年12月17日
 * @see RskValSumname002Service
 * @since 1.0
 */
@Service
public class RskValSumname002Service extends CommonExportService<RskValSumnameDto>{

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public RskValSumnameDto selectById(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname002/selectById",dto,
            RskValSumnameDto.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int insert(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname002/insert",
            dto,Integer.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int update(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname002/update",
            dto,Integer.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int delete(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname002/delete",
            dto,Integer.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param name
     * @return
     * @see
     * @since 1.0
     */
    public List<RskValSumnameDto> selectByName(String name) {
//        return restTemplate.getForList(
//            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname002/selectByName?name="+name,
//            RskValSumnameDto.class);
        
        Map<String,Object> params = new HashMap<>();
        params.put("name", name);
		return restTemplate.postForList(ServiceApplication.RISK_SERVICE_NAME+"/RskValSumname002/selectByName", params , RskValSumnameDto.class);     

    }

}
