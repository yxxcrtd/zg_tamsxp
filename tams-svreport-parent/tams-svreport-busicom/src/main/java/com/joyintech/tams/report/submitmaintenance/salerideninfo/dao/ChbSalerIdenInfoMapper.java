package com.joyintech.tams.report.submitmaintenance.salerideninfo.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.common.base.entity.ChbSalerIdenInfo;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dto.SalerIdenInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dto.SalerInstInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChbSalerIdenInfoMapper {
    /**
     * 分页查询
     */
    List<SalerIdenInfoDto> selectForPage(Map<String, Object> param);

    //该机构是否已维护过奖惩投诉记录信息
    int deleteCheck(String salerInsNo);

    /**
     * 根据机构代码查询中债登记代码
     * @param orgCode
     * @return
     */
    String selectIssuePartyId(@Param("orgCode")String orgCode);

    //导入时为check查询全部
    List<SalerIdenInfoDto> checkSalerIdenInfoImport();

    //导入时为check查询全部
    List<SalerIdenInfoDto> checkSalerIdentInfoDImport();

    //校验所属总部或分支机构是否已被维护
    List<SalerIdenInfoDto> checkSalerOrgCodeImport();

    /**
     * 理财销售人员姓名联想框
     */
    List<SuggestDto> getSalerName(Map<String, Object> map);

    /**
     * 总部或分支机构编码联想框
     */
    List<SuggestDto> getSalerOrgCode(Map<String, Object> map);

    int deleteByPrimaryKey(String salerInsNo);

    int deleteErroByPrimaryKey(String workflowId);

    int deleteBatchByPrimaryKey(List<String> ids);

    int insert(ChbSalerIdenInfo record);

    ChbSalerIdenInfo selectByPrimaryKey(String salerInsNo);

    int getByPrimaryKeyCount(@Param("salerInsNo") String salerInsNo);

    int updateByPrimaryKeySelective(SalerIdenInfoDto record);

    int updateByPrimaryKey(SalerIdenInfoDto record);

    //导入销售人员登记编码
    int updateSalerRegistNo(SalerIdenInfoDto record);

    //所属总部或分支机构下拉框（新建）
    List<SelectOptionDto> selectSalerOrgCode(Map<String, Object> pageParam);

    /**
     * 双击查看详细信息查询，修改信息查询 <功能详细描述>info
     *
     */
    SalerIdenInfoDto selectDetail(Map<String, Object> param);

    /**
     * 双击查看详细信息查询，修改信息查询 <功能详细描述>erro
     *
     */
    List<SalerIdenInfoDto> selectErroDetail(Map<String, Object> param);

    /**
     * 查询有效已维护的奖惩信息
     * @return
     */
    List<SalerIdenInfoDto> selectRewPenaInfo();

    /**
     * 根据身份主键逻辑删除奖惩info表中数据
     * @param salerInsNo
     * @return
     */
    int deleteRewPenaInfo(String salerInsNo);

    /**
     * 根据主键逻辑删除info表中数据
     * @param salerInsNo
     * @return
     */
    int deleteInfo(String salerInsNo);

    //内部编码重复校验（新建）
    int checkSalerInsNo(String salerInsNo);

    //证件号码重复校验(修改)
    int checkSalerCredentNo(@Param("salerCredentType") String salerCredentType,@Param("salerOtherCredentName") String salerOtherCredentName,@Param("salerCredentNo") String salerCredentNo);

    /**
     * check所属总部或分支机构校验有效性
     * @param
     * @return
     */
    int checkSalerOrgCode(@Param("salerOrgCode") String salerOrgCode);
}