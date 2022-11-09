/*
 * 文件名：RskValRelateImportValidator.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：dongzhiling
 * 修改时间：2018年12月25日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.valid;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.dto.RskValRelateImport001Dto;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.service.RskValRelateImport002Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author dongzhiling
 * @version 1.0 2018年12月25日
 * @see RskValRelateImportValidator
 * @since 1.0
 */
@Component
public class RskValRelateImportValidator implements BeanValidator {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(RskValRelateImportValidator.class);

    /**
     * RskValRelateImport002Service
     */
    @Autowired
    private RskValRelateImport002Service rskValRelateImport002Service;
    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        RskValRelateImport001Dto dto = (RskValRelateImport001Dto)value;
        List<ValidMessage> errorList = new ArrayList<>();
        if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)) {
            //校验新建映射组合名称是否重复
            int count = rskValRelateImport002Service.checkGroupName(dto);
            if (count>0) {
                errorList.add(new ValidMessage(this.getMessage("RSK_W_0031")));
            }
        }
        if (!StringUtil.equals(dto.getOperateType(), "delete")) {
            if (StringUtil.isEmpty(dto.getPtlId())) {
                errorList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0038",
                    this.getMessage("RskValRelateImport002.groupName"))));
            }
            //add  by  张年  2019/07/31
            try {
                if (StringUtil.isEmpty(dto.getSubjectCodeColLetter())) {
                    errorList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0038",
                            this.getMessage("RskValRelateImport002.subjectCodeCol"))));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
            //add  by  张年  2019/07/31
            if (dto.getStartRow() == null) {
                errorList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0038",
                    this.getMessage("RskValRelateImport002.startRow"))));
            }
            /*if (dto.getSubjectCodeCol() == null) {
                errorList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0038",
                    this.getMessage("RskValRelateImport002.subjectCodeCol"))));
            }*/
            if (StringUtil.isEmpty(dto.getStructMapId())) {
                errorList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0038",
                    this.getMessage("RskValRelateImport002.structMapId"))));
            }
            if (StringUtil.isEmpty(dto.getSumsubMapId())) {
                errorList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0038",
                    this.getMessage("RskValRelateImport002.sumsubMapId"))));
            }
            if (StringUtil.isEmpty(dto.getAssetSubMapId())) {
                errorList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0038",
                    this.getMessage("RskValRelateImport002.assetSubMapId"))));
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){
                //校验该操作数据是否已被删除
                int count = rskValRelateImport002Service.checkGroupName(dto);
                if (count == 0) {
                    errorList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0022")));
                }
            }
        }
        return errorList;
    }

}
