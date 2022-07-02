package com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.validator;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dto.ChbBokSubjectDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.service.ChbBokSubjectService;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author wubingbing
 * @date 2022-03-28 14:42
 */
@Component
public class ChbBokSubjectDtoValidator implements BeanValidator {
    @Autowired
    private ChbBokSubjectService bokSubjectService;
    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;
    @Override
    public List<ValidMessage> valid(Object value) {
        ChbBokSubjectDto dto = (ChbBokSubjectDto) value;
        if (dto.getOperateType().equals("submit")) {
            List<ValidMessage> resultList = new ArrayList<ValidMessage>();
            if (!StringUtil.isEmpty(dto.getSubjectNo())
                    && !!StringUtil.isEmpty(dto.getBooksetId())
                    //新建
                    && StringUtil.isEmpty(dto.getStatusValid())) {
                ChbBokSubjectDto resultDto = bokSubjectService.selcetByKey(dto.getSubjectNo(), dto.getBooksetId(),null,null);
                if (null != resultDto) {
                    resultList.add(new ValidMessage("subjectNo", "科目号已存在"));
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
