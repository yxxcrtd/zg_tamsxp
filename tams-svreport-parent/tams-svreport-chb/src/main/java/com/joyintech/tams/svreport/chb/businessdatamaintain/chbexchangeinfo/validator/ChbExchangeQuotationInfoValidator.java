package com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.validator;


import cn.hutool.core.collection.CollectionUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dto.ChbExchangeQuotationInfoDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhoubin
 */
@Component
public class ChbExchangeQuotationInfoValidator implements BeanValidator {

    public static final String OBJ_VALUE = "8,5";

    @Override
    public List<ValidMessage> valid(Object var1) {
        ChbExchangeQuotationInfoDto dto = (ChbExchangeQuotationInfoDto) var1;
        List<ValidMessage> resultList = new ArrayList(5);

        if ( dto.getOpenPrice()!=null && !this.isNumber(dto.getOpenPrice().toString()) ) {
            resultList.add(new ValidMessage("", getMessage("开盘价必须为n（8,5）格式!")));
        }
        if (dto.getClosePrice()!=null && !this.isNumber(dto.getClosePrice().toString())) {
            resultList.add(new ValidMessage("", getMessage("收盘价必须为n（8,5）格式!")));
        }
        if (dto.getHighPrice()!=null && !this.isNumber(dto.getHighPrice().toString())) {
            resultList.add(new ValidMessage("", getMessage("最高价必须为n（8,5）格式!")));
        }
        if (dto.getLowPrice()!=null && !this.isNumber(dto.getLowPrice().toString())) {
            resultList.add(new ValidMessage("", getMessage("最低价必须为n（8,5）格式!")));
        }
        if (dto.getMidPrice()!=null && !this.isNumber(dto.getMidPrice().toString())) {
            resultList.add(new ValidMessage("", getMessage("中间价必须为n（8,5）格式!")));
        }

        if (CollectionUtil.isNotEmpty(resultList)) {
            return resultList;
        }


        return null;
    }

    public boolean isNumber(String value) {

        String srcValue = stripTrailingZeros(value);

        //格式如：10,5
        String[] objFormat = OBJ_VALUE.split(",");
        int allbit = Integer.parseInt(objFormat[0]);
        int decbit = Integer.parseInt(objFormat[1]);
        //"^[-+]?[1-9]{5}[.][0-9]{2}$";
        String format = "";
        if (decbit > 0 && StringUtil.contains(srcValue, GlobalConstant.POINT)) {
            format = "^([-+])?([0-9]{1," + (allbit - decbit) + "})?[.][0-9]{1," + decbit + "}$";
        } else {
            format = "^([-+])?([0-9]{1," + (allbit - decbit) + "})$";
        }

        return srcValue.matches(format);
    }

    private String stripTrailingZeros(String srcValue) {
        if (!StringUtils.isBlank(srcValue)) {
            srcValue = srcValue.trim();
        }
        if (isNumeric(srcValue)) {
            BigDecimal value = new BigDecimal(srcValue);
            BigDecimal noZeros = value.stripTrailingZeros();
            srcValue = noZeros.toPlainString();
        } else if (null != srcValue && srcValue.startsWith("0E-")) {
            double value = new BigDecimal(srcValue).doubleValue();
            srcValue = Double.toString(value);
        }
        return srcValue;
    }

    public boolean isNumeric(String str) {
        if (str == null || str.trim().equals("")) {
            return false;
        }
        if (str.endsWith(".")) {
            str = str.substring(0, str.length() - 1);
        }
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
}
