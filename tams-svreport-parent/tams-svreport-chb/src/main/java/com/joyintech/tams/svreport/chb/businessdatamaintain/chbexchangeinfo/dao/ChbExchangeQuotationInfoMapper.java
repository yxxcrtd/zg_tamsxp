/*
 * 文件名：MstYieldCurveInfoMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年8月8日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dao;

import com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dto.ChbExchangeQuotationInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 汇率行情mapper 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author wangzhao
 * @version 1.0 2018年8月8日
 * @see ChbExchangeQuotationInfoMapper
 * @since 1.0
 */
@Repository
public interface ChbExchangeQuotationInfoMapper {

    /**
     * 查询汇率行情信息
     * 
     * @param param 查询参数
     * @return 返回查询结果
     * @see
     * @since 1.0
     */
    List<ChbExchangeQuotationInfoDto> select(Map<String, Object> param);


    /**
     * 查询新增数据主键是否重复
     * @param dto
     * @return list
     */

    Integer getChbExchargeQuoteByKey(ChbExchangeQuotationInfoDto dto);

    /**
     *
     * 导入（新建）
     * <功能详细描述>
     * @param listN 导入数据
     * @return 导入结果
     */
    int insertExchargeQuoteInfo(@Param("list")List<ChbExchangeQuotationInfoDto> listN);

    /**
     *
     * 导入（修改）
     * <功能详细描述>
     * @param list 导入数据
     * @return 导入结果
     * @see
     * @since 1.0
     */
    int updateExchargeQuoteInfo(@Param("list")List<ChbExchangeQuotationInfoDto> list);

    /**
     * 更新状态
     * @param dto
     * @return
     */
    int updateStatus(ChbExchangeQuotationInfoDto dto);

    /**
     * 主键删除
     * @return
     */
    int deleteChbExchargeQuoteByKey(@Param("exchargeCode") String exchargeCode,@Param("tradeDate") Date tradeDate,
                                    @Param("datasourceType") String datasourceType,@Param("status") String status);

    /**
     * 查询记录条数
     * @param list
     * @return
     */
    int  selectExchargeQuoteCount(@Param("list")List<Map<String, String>> list);

    int countDuplicatedData(@Param("list") List<Map<String, String>> list);

    List<ChbExchangeQuotationInfoDto> selectByKeys(@Param("list") List<ChbExchangeQuotationInfoDto> list);
}
