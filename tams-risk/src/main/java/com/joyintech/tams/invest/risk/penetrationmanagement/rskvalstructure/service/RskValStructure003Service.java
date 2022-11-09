/*
 * 文件名：RskValStructure003Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月13日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.RskValtblStructMapDtl;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.util.ExcelUtilForRisk;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.dto.RskValStructureDto;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author qiangchengxi
 * @version 1.0 2018年12月13日
 * @see RskValStructure003Service
 * @since 1.0
 */
@Service
public class RskValStructure003Service extends CommonExportService<RskValStructureDto> implements BaseProcessService<RskValStructureDto> {

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int insert(RskValStructureDto dto)  throws Exception {
        dto.setSrcColIndex(String.valueOf(ExcelUtilForRisk.columnToIndex(dto.getSrcColIndexLetter())));
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure003/insert",
            dto,Integer.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int update(RskValStructureDto dto) throws Exception {
    	String srcColIndex = "";
    	if(StringUtil.isNotEmpty(dto.getSrcColIndex())&&dto.getSrcColIndex().matches("^[\\d]+$")) {
    		srcColIndex = dto.getSrcColIndex();
    	}
    	else {
    		srcColIndex = String.valueOf(ExcelUtilForRisk.columnToIndex(dto.getSrcColIndexLetter()));	
    	}
    	dto.setSrcColIndex(srcColIndex);
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure003/update",
            dto,Integer.class);
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
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public RskValStructureDto select(RskValStructureDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure003/select",
            dto,RskValStructureDto.class);
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
            ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure003/delete",
            dto,Integer.class);
    }
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public Boolean insertList(List<RskValtblStructMapDtl> list) {
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME +"/RskValStructure003/insertList",
                list,Boolean.class);
    }


}
