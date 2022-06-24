package com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.validator;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.svreport.chb.assetinfodetail.dao.AssetInfoDetailMapper;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.AssetInfoDetailDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.SvrBussRepQDIIStructureInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dao.ChbInvestTradeDao;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-05-06 17:38
 */
@Component
public class ChbInvestTradeValidator implements BeanValidator {

    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    @Autowired
    private ChbInvestTradeDao chbInvestTradeDao;
    @Override
    public List<ValidMessage> valid(Object value) {
        ChbInvestTradeDto dto = (ChbInvestTradeDto) value;
        if (dto.getOperateType().equals("submit")) {
            List<ValidMessage> resultList = new ArrayList<ValidMessage>();
            if (!StringUtil.isEmpty(dto.getTradeCode())
                &&!StringUtil.isEmpty(dto.getProdId())
                &&!StringUtil.isEmpty(dto.getAssetCode())
                //新建
                &&StringUtil.isEmpty(dto.getStatusValid())) {
                int i = chbInvestTradeDao.selectByBusinessKey(dto.getProdId(),dto.getTradeCode(),dto.getAssetCode());
                if (i>0) {
                    resultList.add(new ValidMessage("assetCode", "投资交易信息已存在"));
                }
            }
            resultList = chbDataCheckCommonUtil.chbCheckCommon(resultList, dto, null, null, ValidMessage.class);
            Map<String, Object> errMap = new HashMap<>();
            List<ValidMessage> result = new ArrayList<>();
            resultList.forEach(validMessage -> {
                if (!errMap.containsKey(validMessage.getMessage())) {
                    result.add(validMessage);
                    errMap.put(validMessage.getMessage(), validMessage.getMessage());
                }
            });
            resultList.clear();
            resultList.addAll(result);
            return resultList;
        }
        return null;
    }
}
