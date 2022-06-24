package com.joyintech.tams.report.submitmaintenance.salerinstinfo.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.common.base.entity.ChbSalerInstInfo;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dto.AgentInstitutionDto;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dto.SalerInstInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChbSalerInstInfoMapper {

    /**
     * 分页查询
     */
    List<SalerInstInfoDto> selectForPage(Map<String, Object> param);

    //该机构是否已维护过销售人员身份信息
    int deleteCheck(String salerOrgCode);

    /**
     * 根据机构代码查询中债登记代码
     * @param orgCode
     * @return
     */
    String selectIssuePartyId(@Param("orgCode")String orgCode);

    /**
     * 导入代理销售机构代码重复校验
     * @return
     */
    List<SalerInstInfoDto> checkSalerInstInfoImport();

    /**
     * 新建，导入插入
     * @param
     * @return
     */
    int insert(SalerInstInfoDto dto);

    /**
     * 修改
     * @param
     * @return
     */
    int update(SalerInstInfoDto dto);

    /**
     * 根据主键删除表中删除状态数据
     * @param salerOrgCode
     * @return
     */
    int deleteByPrimaryKey(String salerOrgCode);

    /**
     * 查询有效已维护的身份信息
     * @return
     */
    List<SalerInstInfoDto> selectIdenInfo();

    /**
     * 查询有效已维护的奖惩信息
     * @return
     */
    List<SalerInstInfoDto> selectRewPenaInfo();

    /**
     * 查询总数
     * @param salerOrgCode
     * @return
     */
    int getSalerInstInfoCount(@Param("salerOrgCode") String salerOrgCode);

    /**
     * 根据主键删除erro表中数据
     * @param workflowId
     * @return
     */
    int deleteErroByPrimaryKey(String workflowId);

    /**
     * 根据主键逻辑删除info表中数据
     * @param salerOrgCode
     * @return
     */
    int deleteInfo(String salerOrgCode);

    /**
     * 根据机构主键逻辑删除身份info表中数据
     * @param salerOrgCode
     * @return
     */
    int deleteIdenInfo(String salerOrgCode);


    /**
     * 根据身份主键逻辑删除奖惩info表中数据
     * @param salerInsNo
     * @return
     */
    int deleteRewPenaInfo(String salerInsNo);

    /**
     * 代理销售机构代码D删除状态check
     * @return
     */
    List<SalerInstInfoDto> checkSalerInstInfoDImport();

    ChbSalerInstInfo selectByPrimaryKey(String salerOrgCode);

    /**
     * 总部或分支机构编码联想框
     */
    List<SuggestDto> getSalerOrgCode(Map<String, Object> map);

    /**
     * 双击查看详细信息查询，修改信息查询 <功能详细描述>info
     *
     */
    SalerInstInfoDto selectDetail(Map<String, Object> param);

    /**
     * 双击查看详细信息查询，修改信息查询 <功能详细描述>erro
     *
     */
    List<SalerInstInfoDto> selectErroDetail(Map<String, Object> param);

    //总部或分支机构编码重复校验
    int checkSalerOrgCode(String salerOrgCode);

    //总部或分支机构名称重复校验（新建）
    int checkSalerOrgName(String salerOrgName);

    //总部或分支机构名称重复校验(修改)
    int checkSalerOrgNameEdit(@Param("salerOrgName") String salerOrgName,@Param("salerOrgCode") String salerOrgCode);

}