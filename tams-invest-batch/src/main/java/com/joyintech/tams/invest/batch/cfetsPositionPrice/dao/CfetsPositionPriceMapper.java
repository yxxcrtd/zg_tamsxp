/*
 * 文件名：ResetBaseRuleInfoMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：sjj
 * 修改时间：2021年11月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.batch.cfetsPositionPrice.dao;

import com.joyintech.tams.business.common.pojo.BaseMap;
import com.joyintech.tams.invest.batch.cfetsPositionPrice.dto.CfetsUnitCostDto;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * reader相关业务处理
 * 〈功能详细描述〉
 * @author sjj
 * @version 1.0 2021年11月19日
 * @see CfetsPositionPriceMapper
 * @since 1.0
 */
@Repository
public interface CfetsPositionPriceMapper {

    /**
     * 查询前一日持仓指标
     * @param baseMap
     * @return
     */
    List<BaseMap> selectLastCostAmt(BaseMap baseMap);

    /**
     * 当日交易数据查询
     * @param baseMap
     * @return
     */
    List<BaseMap> selectCurrTrdData(BaseMap baseMap);
    
    void insert(CfetsUnitCostDto dto);

    void delete(CfetsUnitCostDto dto);
    /**
     * 交易所当日交易数据查询
     * @param baseMap
     * @return
     */
    List<BaseMap> selectExchangeCurrTrdData(BaseMap baseMap);

}
