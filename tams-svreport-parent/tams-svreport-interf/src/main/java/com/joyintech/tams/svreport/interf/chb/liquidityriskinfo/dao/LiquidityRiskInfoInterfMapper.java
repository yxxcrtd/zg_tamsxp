package com.joyintech.tams.svreport.interf.chb.liquidityriskinfo.dao;

import com.joyintech.tams.common.base.entity.LiquidityRiskInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 流动性风险应对情况信息Mapper
 *
 * @author zhoubin
 */
@Repository
public interface LiquidityRiskInfoInterfMapper {

    /**
     * 流动性风险应对情况信息插入
     *
     * @param insertList
     * @return
     */
    int mergeInfoList(@Param("newDataList") List<LiquidityRiskInfo> insertList);
}
