package com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dto.SalerInstInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dto.SalerRewPenaInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChbSalerRewPenaInfoMapper {
    /**
     * 分页查询
     */
    List<SalerRewPenaInfoDto> selectForPage(Map<String, Object> param);

    /**
     * 根据机构代码查询中债登记代码
     * @param orgCode
     * @return
     */
    String selectIssuePartyId(@Param("orgCode")String orgCode);

    /**
     * 根据内部编号查询姓名
     * @param salerInsNo
     * @return
     */
    String getSalerName(@Param("salerInsNo")String salerInsNo);

    //查询已维护的销售人员内部编码
    List<SalerRewPenaInfoDto> checkSalerInsNoImport();

    /**
     * 根据主键删除erro表中数据
     * @param salerRewPenaNo
     * @return
     */
    int deleteErroByPrimaryKey(String salerRewPenaNo);

    /**
     * 根据主键逻辑删除info表中数据
     * @param salerRewPenaNo
     * @return
     */
    int deleteInfo(String salerRewPenaNo);


    /**
     * 双击查看详细信息查询，修改信息查询 <功能详细描述>info
     *
     */
    SalerRewPenaInfoDto selectDetail(Map<String, Object> param);

    /**
     * 双击查看详细信息查询，修改信息查询 <功能详细描述>erro
     *
     */
    List<SalerRewPenaInfoDto>  selectErroDetail(Map<String, Object> param);

    //内部编号下拉框
    List<SelectOptionDto> selectSalerInsNo(Map<String, Object> pageParam);

    int deleteByPrimaryKey(String salerRewPenaNo);

    int insert(SalerRewPenaInfoDto record);

    int insertSelective(SalerRewPenaInfoDto record);

    int insertBasicByBasicInfo(SalerRewPenaInfoDto record);

    SalerRewPenaInfoDto selectByPrimaryKey(String salerRewPenaNo);

    int updateByPrimaryKeySelective(SalerRewPenaInfoDto record);

    int updateByPrimaryKey(SalerRewPenaInfoDto record);

    /**
     * 内部编号联想框
     */
    List<SuggestDto> getSalerInsNo(Map<String, Object> map);

    int checkSalerInsNo (String salerInsNo);
}