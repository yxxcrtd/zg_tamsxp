package com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto.ChbPrdBaseInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-05-09 10:55
 */
@Repository
public interface ChbPrdBaseInfoDao {
    ChbPrdBaseInfoDto selectByKey(String prodId);

    List<SuggestDto> getProdIdAndProdName(Map<String, Object> pageParam);

    Map<String, String> selectRegCodeByProdId(String prodId);

    void update(ChbPrdBaseInfoDto entity);

    void insert(ChbPrdBaseInfoDto entity);

    List<ChbPrdBaseInfoDto> selectAll(Map<String, Object> pageParam);


    List<SelectOptionDto> queryCalendarInfoList(Map<String, Object> pageParam);

    List<ChbPrdBaseInfoDto> queryForList(String[] split);

    void updateIssuingStatus(ChbPrdBaseInfoDto dto);

    void mergeData(@Param("list") List<ChbPrdBaseInfoDto> subList);

    void deleteData();

    List<SelectOptionDto> getProdInsId(Map<String, Object> param);

    int countDuplicatedData(List<Map<String, String>> list);

    List<Map<String,String>> selectByProdIdKey(String prodId);

    List<ChbPrdBaseInfoDto> selectByKeys(@Param("list") List<String> list);

    ChbPrdBaseInfoDto selectByProdId(String prodId);
}
