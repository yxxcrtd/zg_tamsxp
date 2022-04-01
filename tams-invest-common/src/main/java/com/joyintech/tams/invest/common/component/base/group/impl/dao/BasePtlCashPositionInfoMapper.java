/*
 * 文件名：BasePtlCashPositionInfoMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：fanshuanghu
 * 修改时间：2018年9月3日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.common.component.base.group.impl.dao;

import com.joyintech.tams.common.base.entity.PtlCashPosition;
import com.joyintech.tams.invest.common.dto.PtlCashPositionFlowDto;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 组合可用现金和实际现金查询更新组件mapper
 *
 * @author fanshuanghu
 * @version 1.0 2018年9月3日
 * @see BasePtlCashPositionInfoMapper
 * @since 1.0
 */
@Repository
public interface BasePtlCashPositionInfoMapper {

    /**
     * 查询可用现金
     *
     * @param positionQueryDto PtlCashPositionFlowDto
     * @return: java.math.BigDecimal
     * @date: 2018/9/29
     */
    BigDecimal selectCashPositionFlow(PtlCashPositionFlowDto positionQueryDto);

    /**
     * 查询在途、实际现金
     *
     * @param positionQueryDto
     * @return java.math.BigDecimal
     * @date 2018/9/30
     */
    BigDecimal selectCashPosition(PtlCashPositionFlowDto positionQueryDto);

    /**
     * 查询最大日期
     *
     * @param positionQueryDto
     * @return 最大日期
     * @date 2018/9/30
     */
    Date selectCashPositionMaxCdate(PtlCashPositionFlowDto positionQueryDto);

    /**
     * 检索组合现金的最大日期
     *
     * @param positionFlowDto PtlCashPositionFlowDto
     * @return: java.util.Date
     * @date: 2018/9/30
     */
    String selectMaxCdate(PtlCashPositionFlowDto positionFlowDto);

    /**
     * 更新项目 <br/>
     * 数量 = 数量 + 输入参数.数量<br/>
     * 更新表 组合现金 更新条件 <br/>
     * 组合代码 = 输入参数.组合代码<br/>
     * 证券管理账户/通道代码 = 输入参数.证券管理账户/通道代码<br/>
     * 金融产品代码 = 输入参数.金融产品代码<br/>
     * 分支序号 = 输入参数.分支序号<br/>
     * 现金类型 = 输入参数.现金类型 <br/>
     * 日期 = 循环.日期
     *
     * @param params
     * @return 更新的数量
     */
    int updatePosition(Map<String, Object> params);

    int deletePtlCashPositionFlow(PtlCashPositionFlowDto positionQueryDto);

    /**
     * 查询在途、实际现金
     *
     * @param positionQueryDto
     * @return java.math.BigDecimal
     * @date 2018/9/30
     */
    BigDecimal selectCashPositionNew(PtlCashPositionFlowDto positionQueryDto);
	
	    /**
     * 根据查询日期、交易场所代码查询下一工作日
     * 
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    Date selectNextWorkDay(Map<String,Object> map);

    BigDecimal selectPtlCashPositionFlowAmount(PtlCashPositionFlowDto ptlCashPositionFlowDto);

    // add by etliu 汇总表更新并发问题解决 20210511 start
    /**
     * 针对一笔流水，将该笔流水的数值加到组合现金的主表
     * @param record 一笔流水的数据
     * @return 更新条数
     */
    int updateByPrimaryKeySelectiveForFlow(PtlCashPosition record);
    // add by etliu 汇总表更新并发问题解决 20210511 end

    /**
     * 查询可用现金(新表)
     *
     * @param positionQueryDto
     * @return java.math.BigDecimal
     * @date 2018/9/30
     */
    BigDecimal queryTrdAvailableCashNew(PtlCashPositionFlowDto positionQueryDto);
}
