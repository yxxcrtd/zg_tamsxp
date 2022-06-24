package com.joyintech.tams.svreport.chb.common.dao;

import com.joyintech.tams.svreport.chb.common.dto.TabColumnsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhoubin
 */
@Repository
public interface UserTabColumnsMapper {

    /**
     * 查询表结构定义
     *
     * @param tableName
     * @return
     */
    List<TabColumnsDto> selectTabColumns(@Param("tableName") String tableName);
}
