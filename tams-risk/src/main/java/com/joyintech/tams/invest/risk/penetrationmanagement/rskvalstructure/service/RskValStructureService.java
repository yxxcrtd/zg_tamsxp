/*
 * 文件名：RskValStructureService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月10日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.dto.RskValStructureDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author wangzehui
 * @version 1.0 2018年12月10日
 * @see RskValStructureService
 * @since 1.0
 */
@Service
public class RskValStructureService extends CommonExportService<RskValStructureDto> implements BaseProcessService<RskValStructureDto> {

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    public List<TreeNode<RskValStructureDto>> getStructName(Map<String, Object> map) {
        List<TreeNode<RskValStructureDto>> retList = new ArrayList<>();
        Page<TreeNode<RskValStructureDto>> page = new Page<>();
        // 获得查询得到的申报附件方案名称
        List<RskValStructureDto> list = restTemplate.postForList(
                ServiceApplication.RISK_SERVICE_NAME +
                        "/RskValStructure/getStructName",
                map,RskValStructureDto.class);
        if (!StringUtil.isEmptyList(list)) {
            // 将查询到的结果赋值树节点
            for (RskValStructureDto dto : list) {
                TreeNode data = new TreeNode();
                data.setId(dto.getId());
                data.setName(dto.getStructName());
                retList.add(data);
            }
            page.setRoot(retList);
            page.setRecords(retList.size());
            return retList;
        }
        else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doComplete(RskValStructureDto arg0, OperationType arg1) {
        // TODO Auto-generated method stub
        
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    public Page<RskValStructureDto> selectForPage(Map<String, Object> param) {
        return restTemplate.postForPage(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure/selectForPage",
            param,RskValStructureDto.class);
    }

    /**
     *  查询(不分页)
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public List<RskValStructureDto> selectForList(String structId){
        return restTemplate.getForList(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure/selectForList?structId="+structId,
            RskValStructureDto.class);
    }
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int insert(RskValStructureDto dto) {
        // 二级法人权限处理
        if(MultilevelDataAuthorityUtil.isEnabled()) {
            //创建机构代码
            String createInst =   MultilevelDataAuthorityUtil.getService().queryOrganizationCode(dto.getCreateUser());
            dto.setCreateInst(createInst);

            // 有查询权限的机构代码
            List<String> searchOrgCodeList = null;
            // 有编辑权限的机构代码
            List<String> editOrgCodeList = null;
            // 新建或修改时重新设置二级法人权限，删除时则清空
            //获取所有父级机构。  拥有查看权限
            searchOrgCodeList = CommonUtil.getParentInstitution(createInst);
            // 仅资产的创建机构（当前登录用户所在机构）有编辑权限
            editOrgCodeList = Arrays.asList(dto.getCreateInst());
            // 记录二级法人权限
            insertDataAuthTypeInst(dto.getId(), searchOrgCodeList, editOrgCodeList, DictCodeList.共通业务类型_估值表指标结构映射.getValue());
        }
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure002/insert",
            dto,Integer.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int update(RskValStructureDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure002/update",
            dto,Integer.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param structName
     * @return
     * @see
     * @since 1.0
     */
    public List<RskValStructureDto> selectByName(String structName) {
//        return restTemplate.getForList(
//            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure002/selectByName?structName="+structName,
//            RskValStructureDto.class);
    	
    	Map<String,Object> params = new HashMap<>();
        params.put("structName", structName);
		return restTemplate.postForList(ServiceApplication.RISK_SERVICE_NAME+"/RskValStructure002/selectByName", params , RskValStructureDto.class);     

    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public RskValStructureDto selectById(RskValStructureDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure002/selectById",dto,
            RskValStructureDto.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int delete(RskValStructureDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure002/delete",
            dto,Integer.class);
    }

    /**
     * 选中记录在业务表中是否已关联组合关联组合
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public boolean getCountById(RskValStructureDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure/getCountById",
            dto,boolean.class);
    }
    /**
     * 选中记录是否是默认模板，是则不允许修改，删除
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public boolean getStructInfoIsDefault(RskValStructureDto dto) {
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure/getStructInfoIsDefault",
                dto,boolean.class);
    }

}
