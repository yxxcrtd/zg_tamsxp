package com.joyintech.tams.report.submitmaintenance.agentinstitution.dao;

import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dto.AgentInstitutionDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChbAgentInstitutionInfoMapper {

    /**
     * 分页查询
     */
    List<AgentInstitutionDto> selectForPage(Map<String, Object> param);

    /**
     * 双击查看详细信息查询，修改信息查询 <功能详细描述>info
     *
     */
    AgentInstitutionDto selectDetail(Map<String, Object> param);

    int insert(AgentInstitutionDto record);

    int update(AgentInstitutionDto record);

    int delete(@Param("agentInstitutionCode") String agentInstitutionCode);

    /**
     * 代理销售机构信息表内容逻辑删除
     * @param
     * @return
     */
    int deleteInfo(@Param("agentInstitutionCode") String agentInstitutionCode);

    /**
     * 委托销售产品编码表内容逻辑删除
     * @param
     * @return
     */
    int deletePrdInfo(@Param("agentInstitutionCode") String agentInstitutionCode);

    /**
     * 根据机构代码查询中债登记代码
     * @param orgCode
     * @return
     */
    String selectIssuePartyId(@Param("orgCode")String orgCode);

    /**
     * 代理销售机构代码下拉框
     */
    List<SuggestDto> getAgentInstitutionCode(Map<String, Object> map);

    //该机构是否已维护过委托销售产品编码信息
    int deleteCheck(String agentInstitutionCode);

    //该机构是否已报送
    List<AgentInstitutionDto> deleteFileCheck(String agentInstitutionCode);

    //代理销售机构代码重复校验
    int checkAgentInstitutionCode(String agentInstitutionCode);

    //代理销售机构代码重复校验
    int checkAgentInstitutionName(String agentInstitutionName);

    //代理销售机构代码重复校验
    int checkEditAgentInstitutionName(@Param("agentInstitutionName")String agentInstitutionName,@Param("agentInstitutionCode")String agentInstitutionCode);

    //导入代理销售机构代码重复校验
    List<AgentInstitutionDto> checkAgentInstitutionCodeImport();

    //代理销售机构代码D删除状态check
    int checkAgentInstitutionCodeD(String agentInstitutionCode);

    //代理销售机构代码D删除状态check导入
    List<AgentInstitutionDto> checkAgentInstitutionCodeDImport();

    //删除附件
    public void updateEnclosureNum(AgentInstitutionDto agentInstitutionDto);

    String selectFileInfo(AgentInstitutionDto agentInstitutionDto);
}