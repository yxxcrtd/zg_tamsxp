package com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.validator;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dto.ChbMstBondCreditRatingDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.service.ChbMstBondCreditRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-04-01 15:02
 */
@Component
public class ChbMstBondCreditRatingValidator implements BeanValidator {
    @Autowired
    private ChbMstBondCreditRateService chbMstBondCreditRateService;
    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;
    @Override
    public List<ValidMessage> valid(Object value) {

        ChbMstBondCreditRatingDto dto = (ChbMstBondCreditRatingDto) value;
        if (dto.getOperateType().equals("submit")) {
            List<ValidMessage> resultList = new ArrayList<ValidMessage>();
            if (!StringUtil.isEmpty(dto.getoCode())
                    && !StringUtil.isEmpty(dto.getGradeOrgId())
                    && null != dto.getGradeDate()
                    && !StringUtil.isEmpty(dto.getoType())
                    //新建
                    &&StringUtil.isEmpty(dto.getStatusValid())) {
                ChbMstBondCreditRatingDto resultDto = chbMstBondCreditRateService.selcetByKey(dto.getoCode(), dto.getGradeOrgId(), dto.getGradeDate(), dto.getoType());
                if (null != resultDto) {
                    resultList.add(new ValidMessage("oCode", "债券评级已存在"));
                }
            }
            // add by zhangliang 20210126 中债check调用 start
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
            //modify by tuqirong 2022-01-10 end 错误信息去重处理
            return resultList;
        }
        return null;
    }
}
