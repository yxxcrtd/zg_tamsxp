package com.joyintech.tams.common.svreport.select.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.common.base.entity.MstCustomerInfoAdd;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author yxj
 * @date 2022/1/5
 */
@Repository
@Mapper
public interface SelectorMapper {
    List<SelectOptionDto> getCustomerId(Map<String, Object> param);

    List<SelectOptionDto> getPbcCapAssetInfo(Map<String, Object> param);


    MstCustomerInfoAdd getCustomerInfo(Map<String, Object> param);
}
