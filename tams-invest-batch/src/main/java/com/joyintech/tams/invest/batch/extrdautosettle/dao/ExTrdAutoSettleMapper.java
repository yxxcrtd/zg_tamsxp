/*
 * 文件名：ExTrdAutoSettleMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：fuyongsheng
 * 修改时间：2021年11月11日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.batch.extrdautosettle.dao;


import com.joyintech.tams.invest.batch.extrdautosettle.dto.ExTrdAutoSettleInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 场内自动交收批处理Mapper
 *
 * @author fuyongsheng
 * @version 1.0 2021年11月11日
 * @see ExTrdAutoSettleMapper
 * @since 1.0
 */
@Repository
public interface ExTrdAutoSettleMapper {

    /**
     * 查询自动交收批处理任务数据
     *
     * @param paramMap
     * @return
     * @see
     * @since 1.0
     */
    List<ExTrdAutoSettleInfoDto> selectExTrdAutoSettleInfos(Map<String,Object> paramMap);

}