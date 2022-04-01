package com.joyintech.tams.report.submitmaintenance.agentinstitution.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dao.ChbAgentInstitutionInfoMapper;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dto.AgentInstitutionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AgentInstitution002Service extends BaseService{

    @Autowired
    private ChbAgentInstitutionInfoMapper chbAgentInstitutionInfoMapper;

    /**
     * 双击查看详细信息查询 修改页面信息查询 info
     *
     */
    public AgentInstitutionDto selectDetail(Map<String,Object> param) {
        return chbAgentInstitutionInfoMapper.selectDetail(param);
    }

    /**
     * 新建保存
     * @param
     */
    public void saveAndSubmit(AgentInstitutionDto dto) {
        //check新建的数据是否已逻辑删除
        if(chbAgentInstitutionInfoMapper.checkAgentInstitutionCodeD(dto.getAgentInstitutionCode()) != 0){
            //如果存在就先 删除表中删除状态数据再 新增插入
            chbAgentInstitutionInfoMapper.delete(dto.getAgentInstitutionCode());
        }
        dto.setStatus("E");
        chbAgentInstitutionInfoMapper.insert(dto);
    }

    /**
     * 修改保存
     * @param
     */
    public void update(AgentInstitutionDto dto) {
        dto.setStatus("E");
        //更新代理销售机构信息
        chbAgentInstitutionInfoMapper.update(dto);
    }

    /**
     * 查询发行机构代码
     * @param orgCode
     * @return
     */
    public String selectIssuePartyId(String orgCode){
        return  chbAgentInstitutionInfoMapper.selectIssuePartyId(orgCode);
    }

    /**
     * 代理销售机构代码重复校验
     * @param agentInstitutionCode
     * @return
     */
    public int checkAgentInstitutionCode(String agentInstitutionCode){
        return  chbAgentInstitutionInfoMapper.checkAgentInstitutionCode(agentInstitutionCode);
    }

    /**
     * 代理销售机构名称重复校验
     * @param agentInstitutionName
     * @return
     */
    public int checkAgentInstitutionName(String agentInstitutionName){
        return  chbAgentInstitutionInfoMapper.checkAgentInstitutionName(agentInstitutionName);
    }

    /**
     * 代理销售机构名称重复校验
     * @param agentInstitutionName
     * @return
     */
    public int checkEditAgentInstitutionName(String agentInstitutionName,String agentInstitutionCode){
        return  chbAgentInstitutionInfoMapper.checkEditAgentInstitutionName(agentInstitutionName,agentInstitutionCode);
    }
}