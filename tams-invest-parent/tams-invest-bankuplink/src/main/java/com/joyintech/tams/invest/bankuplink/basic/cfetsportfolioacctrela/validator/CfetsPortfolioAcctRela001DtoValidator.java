/*
 * 文件名：CfetsBondDeal.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangliang
 * 修改时间：2020年5月14日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.bankuplink.basic.cfetsportfolioacctrela.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.invest.bankuplink.basic.cfetsportfolioacctrela.dto.CfetsPortfolioAcctRela001Dto;
import com.joyintech.tams.invest.bankuplink.basic.cfetsportfolioacctrela.service.CfetsPortfolioAcctRelaService;

/**
 * 现券对话报价验证类
 * 
 * @author zhangliang
 * @version 1.0 2020年5月14日
 * @see CfetsPortfolioAcctRela001DtoValidator
 * @since 1.0
 */
@Component
public class CfetsPortfolioAcctRela001DtoValidator implements BeanValidator {
    
    /**
     * {@linkplain}
     */
    @Autowired
    private CfetsPortfolioAcctRelaService cfetsPortfolioAcctRelaService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        CfetsPortfolioAcctRela001Dto dto = (CfetsPortfolioAcctRela001Dto)value;
        // 验证信息
        if(!StringUtil.isNotEmpty(dto.getPartyIdOld())){
            dto.setPartyIdOld(dto.getPartyId());
        }
        if(!StringUtil.isNotEmpty(dto.getPortfolioIdOld())){
            dto.setPortfolioIdOld(dto.getPortfolioId());
        }
        if(!StringUtil.isNotEmpty(dto.getCfetsAcctTypeOld())){
            dto.setCfetsAcctTypeOld(dto.getCfetsAcctType());
        }
        List<ValidMessage> validList = new ArrayList<ValidMessage>();
        String portfolioIds = dto.getPortfolioId();
        for(String portfolioId:portfolioIds.split(",")) {
            dto.setPortfolioId(portfolioId);
            if (StringUtil.equals(dto.getOperateType(), "submit")) {
                int count = cfetsPortfolioAcctRelaService.checkPortfolioIdAcctTypeExists(dto);
                if (count > 0) {
                    validList.add(new ValidMessage("组合代码[" + portfolioId + "]+账户来源[" + dto.getPartyIdOld() + "]已经存在！"));
                }
            }
        }
        return validList;
    }

}
