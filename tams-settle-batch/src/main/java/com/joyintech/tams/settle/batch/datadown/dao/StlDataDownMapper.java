package com.joyintech.tams.settle.batch.datadown.dao;

import com.joyintech.tams.common.base.entity.SettleTransPara;
import com.joyintech.tams.settle.batch.datadown.dto.BondSeatPtlMapping;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 何阳阳
 * @date 2020/7/2
 * @see StlDataDownMapper
 * @since 1.0
 */
@Repository
public interface StlDataDownMapper {

    /**
     * 查询所有参数
     * @return
     */
    List<SettleTransPara> selectAllParam(@Param("importDate") Date importDate, @Param("taskId") String taskId);

    /**
     * 查询所有组合代码和股东代码、席位号关联关系
     * @return
     */
    List<BondSeatPtlMapping> selectAllPtlNo();

    /**
     * 删除
     * @return
     */
    int removeTempDataJSMX(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

    int removeTempDataABCSJ(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

    int removeTempDataSJSFX(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

    int removeTempDataSJSJG(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

    int removeTempDataSJSMX(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

    int removeTempDataZQBD(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

    int removeTempDataZSMX(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);
    
    int removeTempDataZQD(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

    int removeTempDataSJSKS(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

    int removeTempDataTZXX(@Param("importDate") Date importDate, @Param("dataFile") String dataFile, @Param("portfolioIdList") Set<String> portfolioIdList);

}
