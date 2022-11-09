/*
 * 文件名：RskValAssetCode001Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月26日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.dto.RskValtblAssetSubMapDtlDto;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.dto.RskValtblAssetSubMapDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月26日
 * @see RskValAssetCode001Service
 * @since 1.0
 */
@Service
public class RskValAssetCode001Service extends CommonExportService<RskValtblAssetSubMapDto>{

    /**
     * 初始化(不分页)
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    
    public List<TreeNode<RskValtblAssetSubMapDto>> selectAll(Map<String, Object> map) {
        List<TreeNode<RskValtblAssetSubMapDto>> retList = new ArrayList<>();
        Page<TreeNode<RskValtblAssetSubMapDto>> page = new Page<>();
        // 获得查询资产科目映射
        List<RskValtblAssetSubMapDto> list = restTemplate.postForList(
                ServiceApplication.RISK_SERVICE_NAME +
                        "/RskValAssetCode/selectAll",
                map,RskValtblAssetSubMapDto.class);
        if (!StringUtil.isEmptyList(list)) {
            // 将查询到的结果赋值树节点
            for (RskValtblAssetSubMapDto dto : list) {
                TreeNode<RskValtblAssetSubMapDto> data = new TreeNode<RskValtblAssetSubMapDto>();
                data.setId(dto.getId());
                data.setName(dto.getName());
                retList.add(data);
            }
            page.setRoot(retList);
            page.setRecords(retList.size());
            return retList;
        }
        else {
            return null;
        }
    }
    /**
     * 初始化查询
     * <功能详细描述>
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    public Page<RskValtblAssetSubMapDto> selectForPage(Map<String, Object> param) {
        return restTemplate.postForPage(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/selectForPage",
            param,RskValtblAssetSubMapDto.class);
    }
    
    /**
     * 初始化明细查询
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public Page<RskValtblAssetSubMapDtlDto> findPageDtl(Map<String, Object> param) {
        return restTemplate.postForPage(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/findPageDtl",
            param,RskValtblAssetSubMapDtlDto.class);
    }
    
    public List<RskValtblAssetSubMapDtlDto> selectDtlList(String id){
        return restTemplate.getForList(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/selectDtlList?id="+id,
            RskValtblAssetSubMapDtlDto.class);
    }
    /**
     * 删除
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public void delete(RskValtblAssetSubMapDto dto) {
         restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/delete",
            dto,Integer.class);
    }
    
    /**
     * 删除明细
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public void deleteDtl(RskValtblAssetSubMapDtlDto dto) {
        restTemplate.postForObject(
           ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/deleteDtl",
           dto,Integer.class);
   }
    
   public int getCountByPtl(String assetSubMapId){
       return restTemplate.getForObject(
           ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/getCountByPtl?assetSubMapId="+assetSubMapId,
           Integer.class);
   }
    public List<RskValtblAssetSubMapDtlDto> findListDtl(Map<String, Object> param) {
        return restTemplate.postForList(
                ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/findListDtl",
                param,RskValtblAssetSubMapDtlDto.class);
    }
    public int getAssetSubInfoIsDefault(String assetSubMapId){
        return restTemplate.getForObject(
                ServiceApplication.RISK_SERVICE_NAME +"/RskValAssetCode/getAssetSubInfoIsDefault?assetSubMapId="+assetSubMapId,
                Integer.class);
    }
}
