/*
 * 文件名：RskValSumname003Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.common.base.entity.RskValtblSumsubMapDtl;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.service.RskValAssetCode003Service;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.dto.RskValSumnameDto;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author wangzehui
 * @version 1.0 2018年12月19日
 * @see RskValSumname003Service
 * @since 1.0
 */
@Service
public class RskValSumname003Service extends CommonExportService<RskValSumnameDto> implements BaseProcessService<RskValSumnameDto> {
    @Autowired
    private RskValAssetCode003Service rskValAssetCode003Service;
    /**
     * {@inheritDoc}
     */
    @Override
    public void doComplete(RskValSumnameDto arg0, OperationType arg1) {
        // TODO Auto-generated method stub
        
    }
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int insert(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname003/insert",
            dto,Integer.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int update(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname003/update",
            dto,Integer.class);
    }
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int delete(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname003/delete",
            dto,Integer.class);
    }
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public RskValSumnameDto selectByKey(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname003/selectByKey",
            dto,RskValSumnameDto.class);
    }
    /**
     * 映射明细名称不能重复
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public RskValSumnameDto selectByName(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname003/selectByName",
            dto,RskValSumnameDto.class);
    }
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public Page<SelectOptionDto> selecctRskValtblSubjectCodeList(Map<String, Object> pageParam) {
        pageParam.put("subType","02");
        return rskValAssetCode003Service.selecctRskValtblSubjectSumCodeList(pageParam);

    }
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @see
     * @since 1.0
     */
    public void insertList(List<RskValtblSumsubMapDtl> list) {
        restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname003/insertList",
                list,Integer.class);
    }

}
