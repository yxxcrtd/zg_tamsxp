/*
 * 文件名：RskValRelateImport001Dto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：dongzhiling
 * 修改时间：2018年12月20日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.dto;

import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.tams.common.base.entity.RskValtblPrtImpMap;
import com.joyintech.tams.invest.common.constant.BussinessDictCode;
import com.joyintech.tams.invest.common.constant.util.ExcelUtilForRisk;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.valid.RskValRelateImportValidator;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author dongzhiling
 * @version 1.0 2018年12月20日
 * @see RskValRelateImport001Dto
 * @since 1.0
 */
@ValidLogic(targetClass = RskValRelateImportValidator.class)
public class RskValRelateImport001Dto extends RskValtblPrtImpMap implements ProcessEntity{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -2224989069618190674L;
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 页面参数
     */
    private String pageModel;
    /**
     * 组合名称
     */
    private String name;
    
    /**
     * 清理字符

     */
    private String trimChars;

    /**
     * 科目代码列(字母)
     */
    private String subjectCodeColLetter;

    /**
     * 选定参数
     */
    private List<RskValRelateImport001Dto> selectedItem;
    

    /**
     * 获取 operateType
     * @return operateType.
     */
    public String getOperateType() {
        return operateType;
    }
    
    /**
     * 设置 operateType
     * @param operateType operateType
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    
    
    /**
     * 获取 pageModel
     * @return pageModel.
     */
    public String getPageModel() {
        return pageModel;
    }

    
    /**
     * 设置 pageModel
     * @param pageModel pageModel
     */
    public void setPageModel(String pageModel) {
        this.pageModel = pageModel;
    }

    /**
     * 获取 name
     * @return name.
     */
    public String getName() {
        return name;
    }

    
    /**
     * 设置 name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * 获取 selectedItem
     * @return selectedItem.
     */
    public List<RskValRelateImport001Dto> getSelectedItem() {
        return selectedItem;
    }

    
    /**
     * 设置 selectedItem
     * @param selectedItem selectedItem
     */
    public void setSelectedItem(List<RskValRelateImport001Dto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    
    /**
     * 获取 trimChars
     * @return trimChars.
     */
    public String getTrimChars() {
        return trimChars;
    }

    
    /**
     * 设置 trimChars
     * @param trimChars trimChars
     */
    public void setTrimChars(String trimChars) {
        this.trimChars = trimChars;
    }

    public String getFilterName() {
        return SysDataUtil.getNameFromDataDict(BussinessDictCode.估值过滤器, this.getFilterId());
    }

    public String getSubjectCodeColLetter() {
        if(null!=this.getSubjectCodeCol()){
            return ExcelUtilForRisk.indexToColumn(Integer.parseInt(String.valueOf(this.getSubjectCodeCol())));
        }
        return subjectCodeColLetter;
    }

    public void setSubjectCodeColLetter(String subjectCodeColLetter) {
        this.subjectCodeColLetter = subjectCodeColLetter;
    }
}
