package com.joyintech.tams.common.svreport.base.dao;

import com.joyintech.tams.common.svreport.base.dto.BaseCheckOracleDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhoubin
 */
@Repository
public interface BaseCheckOracleMapper {

    /**
     * 查询表结构定义
     *
     * @param tableName
     * @return
     */
    List<BaseCheckOracleDto> selectTabColumns(@Param("tableName") String tableName);

    /**
     * 查询表的唯一索引
     *
     * @param tableName
     * @return
     */
    List<String> selectUniqueConstraint(@Param("tableName") String tableName);
}
