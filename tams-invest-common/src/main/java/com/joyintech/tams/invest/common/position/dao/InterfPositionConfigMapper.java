package com.joyintech.tams.invest.common.position.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.joyintech.tams.invest.common.position.dto.InterfPositionConfigDto;

/**
 * 头寸接口配置Mapper
 * 
 * @author yflv
 * @version 1.0 2022年2月21日
 * @see InterfPositionConfigMapper
 * @since 1.0
 */
@Repository
public interface InterfPositionConfigMapper {
    

    /**
               *查询头寸接口配置
     * @param param
     * @return
     */
	List<InterfPositionConfigDto> searchInterfPositionConfig();
	List<InterfPositionConfigDto> searchInterfPositionConfigByWhere(InterfPositionConfigDto where);
    
}
