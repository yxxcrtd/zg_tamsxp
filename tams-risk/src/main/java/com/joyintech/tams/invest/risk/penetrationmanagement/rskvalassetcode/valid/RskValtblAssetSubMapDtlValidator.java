/*
 * 文件名：RskValtblAssetSubMapDtlValidator.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月27日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.valid;

import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月27日
 * @see RskValtblAssetSubMapDtlValidator
 * @since 1.0
 */
@Component
public class RskValtblAssetSubMapDtlValidator implements BeanValidator{

    @Override
    public List<ValidMessage> valid(Object value) {
        ArrayList<ValidMessage> resultList = new ArrayList<>();
        return resultList;
    }
}
