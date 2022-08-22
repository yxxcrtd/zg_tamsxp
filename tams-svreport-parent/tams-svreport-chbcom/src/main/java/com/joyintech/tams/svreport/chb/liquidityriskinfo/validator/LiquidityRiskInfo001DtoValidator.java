/*
 * 文件名：ValProfitQuery001DtoValidator.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：估值核算系统V1.0
 * 修改人：wanghb
 * 修改时间：2018年9月21日
 * 修改内容：新建
 * 系统名称：估值核算系统V1.0
 */
 
package com.joyintech.tams.svreport.chb.liquidityriskinfo.validator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.dao.LiquidityRiskInfo001Mapper;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.dto.LiquidityRiskInfo001Dto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 校验查询条件
 * @author zhoubin
 * @version 1.0 2022年08月16日
 * @since 1.0
 */
@Component
public class LiquidityRiskInfo001DtoValidator implements BeanValidator{

    @Resource
    private LiquidityRiskInfo001Mapper liquidityRiskInfo001Mapper;

    @Override
    public List<ValidMessage> valid(Object value) {
        LiquidityRiskInfo001Dto dto = (LiquidityRiskInfo001Dto)value;
        List<ValidMessage> errorList = new ArrayList<>();

        if (!this.validDataDuplicate(dto)) {
            errorList.add(new ValidMessage("\"产品登记编码+流动性风险应对措施+风险发生日期+风险应对日期+风险事件描述+风险处理结果\"" + "与现有数据重复"));
        }

        if (StringUtil.isBlank(dto.getProdId())) {
            errorList.add(new ValidMessage("prodId", "产品代码要素不可为空。"));
        }
        if (StringUtil.isBlank(dto.getRegCode())) {
            errorList.add(new ValidMessage("regCode", "产品登记编码要素不可为空。"));
        }
        if (StringUtil.isBlank(dto.getSolutions())) {
            errorList.add(new ValidMessage("solutions", "流动性风险应对措施要素不可为空。"));
        }
        if (dto.getOccurDate()==null) {
            errorList.add(new ValidMessage("occurDate", "风险发生日期要素不可为空。"));
        }
        if (dto.getReplyDate()==null) {
            errorList.add(new ValidMessage("replyDate", "风险应对日期要素不可为空。"));
        }
        if (StringUtil.equals(dto.getSolutions(),"99") && StringUtil.isBlank(dto.getRemark())) {
            errorList.add(new ValidMessage("remark", "流动性风险应对措施选择“99 其他措施 ”时,备注要素不可为空。"));
        }
        return errorList;
    }


    /**
     * 新增校验数据是否已在数据库中存在
     *
     * @param dto
     * @return
     */
    private Boolean validDataDuplicate(LiquidityRiskInfo001Dto dto) {

        Boolean flag = true;
        dto.setRegCode(StringUtil.isBlank(dto.getRegCode()) ? " " : dto.getRegCode());
        dto.setDescribed(StringUtil.isBlank(dto.getDescribed()) ? " " : dto.getDescribed());
        dto.setDisposeResult(StringUtil.isBlank(dto.getDisposeResult()) ? " " : dto.getDisposeResult());
        LiquidityRiskInfo001Dto liquidityRiskInfo001Dto = liquidityRiskInfo001Mapper.selectOne(dto);
        //主键为空为新增
        if (StringUtil.isBlank(dto.getSeqno()) && liquidityRiskInfo001Dto != null) {
            flag = false;
        }

        //主键不为空为修改
        if (StringUtil.isNotBlank(dto.getSeqno()) && (liquidityRiskInfo001Dto != null && !StringUtil.equals(liquidityRiskInfo001Dto.getSeqno(),dto.getSeqno()))) {
            flag = false;
        }

        return flag;
    }


}
