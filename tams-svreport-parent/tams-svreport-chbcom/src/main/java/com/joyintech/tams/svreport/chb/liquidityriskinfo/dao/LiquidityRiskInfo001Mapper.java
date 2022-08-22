package com.joyintech.tams.svreport.chb.liquidityriskinfo.dao;


import com.joyintech.tams.svreport.chb.liquidityriskinfo.dto.LiquidityRiskInfo001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 流动性风险应对情况信息维护Mapper
 *
 * @author zhoubin
 * @create 2022/08/15
 */
@Repository
public interface LiquidityRiskInfo001Mapper {

    int deleteByPrimaryKey(String seqno);

    int deleteLiquidityRiskInfo(Map<String, String> map);

    int insert(LiquidityRiskInfo001Dto dto);


    int mergeInfoList(@Param("newDataList") List<LiquidityRiskInfo001Dto> insertList);

    LiquidityRiskInfo001Dto selectOne(LiquidityRiskInfo001Dto dto);

    int updateStatusByPrimaryKey(@Param("seqno") String seqno);

    int updateByPrimaryKey(LiquidityRiskInfo001Dto dto);

    /**
     * 查询列表数据
     *
     * @param param
     * @return
     */
    List<LiquidityRiskInfo001Dto> queryForPage(Map<String, Object> param);


    /**
     * 导出功能需要导出查询条件的所有内容
     *
     * @param param
     * @return
     */
    List<LiquidityRiskInfo001Dto> querySearchPageList(Map<String, Object> param);


    /**
     * 主键查询
     *
     * @param seqno
     * @return
     */
    LiquidityRiskInfo001Dto selectByPrimaryKey(@Param("seqno") String seqno);

    /**
     * 查询记录条数
     * @param list
     * @return
     */
    List<String>  selectLiquidityRiskInfoCount(@Param("list")List<Map<String, String>> list);


}
