/*
 * 文件名：EtrBondFirstBiddingMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：yangjm
 * 修改时间：2019年5月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.entrust.etrconbond.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.service.entrust.etrconbond.dto.EtrConBondDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 可转债委托 查询mapper
 * 〈功能详细描述〉
 * @author gejiaao
 * @version 1.0 2019年11月14日
 * @see EtrConBondMapper
 * @since 1.0
 */
@Repository
public interface EtrConBondMapper {

    /**
     * 可转债委托  分页查询
     * <功能详细描述>
     * @param pageParam
     * @return
     * @see
     * @since 1.0
     */
    List<EtrConBondDto> selectForPage(Map<String, Object> pageParam);
    /**
     * 
     * 获取资产代码联想文本框信息
     * <功能详细描述>
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> getFinprodId(Map<String, Object> param);
    /**
     * 
     * 获取新建界面的资产代码联想下拉框框
     * <功能详细描述>
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> getFinprodIdForAdd(Map<String, Object> param);
    
    /**
     * 
     * 通过主键查询修改信息
     * <功能详细描述>
     * @param
     * @return
     * @see
     * @since 1.0
     */ 
    EtrConBondDto selectByKey(Map<String, Object> map);

    /**
     * 获取所有的子组合
     * @param map
     * @return
     */
    List<String> getAllChildPortFolioId(Map<String, Object> map);

    /**
     * 获取交易对手通过资产代码
     */
    String getCounterId(@Param("finprodId") String finprodId);

    String getAccNo(Map<String,String> param);
}
