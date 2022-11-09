/*
 * 文件名：RskValAssetCode002Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月26日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.tams.common.base.entity.RskValtblAssetSubMap;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.dto.RskValtblAssetSubMapDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 资产科目映射
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月26日
 * @see RskValAssetCode002Service
 * @since 1.0
 */
@Service
public class RskValAssetCode002Service extends BaseService<RskValtblAssetSubMapDto>{
    
    /**
     * 资产科目映射修改初始化
     * <功能详细描述>
     * @param id
     * @return
     * @see
     * @since 1.0
     */
    public RskValtblAssetSubMap selectAssetCodeByPrimaryKey(String id){
        return restTemplate.getForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/selectAssetCodeByPrimaryKey?id="+id,
            RskValtblAssetSubMap.class);
    }
    
    /**
     * 映射名称是否重复
     * <功能详细描述>
     * @param name
     * @param id
     * @return
     * @see
     * @since 1.0
     */
    public int selecctCountByName(String name,String id){
//        return restTemplate.getForObject(
//            ServiceApplication.RISK_SERVICE_NAME + "/RskValAssetCode/selecctCountByName?id="+id+"&name="+name,
//            Integer.class);
        
        Map<String,Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
		return restTemplate.postForObject(ServiceApplication.RISK_SERVICE_NAME+"/RskValAssetCode/selecctCountByName", params , Integer.class);     

    }
    /**
     * 保存
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public void insert(RskValtblAssetSubMapDto dto) {
        restTemplate.postForObject(
           ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/saveAssetCode",
           dto,Integer.class);
   }
    
}
