/*
 * 文件名：RskValSumnameDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月17日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.dto;

import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RskValtblSumsubMapDtl;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.valid.RskValSumnameValidator;
import com.joyintech.workflow.engine.dto.ProcessEntity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author wangzehui
 * @version 1.0 2018年12月17日
 * @see RskValSumnameDto
 * @since 1.0
 */
@ValidLogic(targetClass = RskValSumnameValidator.class, groups = {ValidateGroupEdit.class,
    ValidateGroupDelete.class})
public class RskValSumnameDto extends RskValtblSumsubMapDtl implements ITableRow, ProcessEntity{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 映射名称

     */
    private String name;

    /**
     * 清理字符

     */
    private String trimChars;
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 页面参数
     */
    private String pageModel;
    /**
     * 映射名称

     */
    private String oldName;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getTrimChars() {
        return trimChars;
    }
    
    public void setTrimChars(String trimChars) {
        this.trimChars = trimChars;
    }
    
    public String getOperateType() {
        return operateType;
    }
    
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    
    public String getPageModel() {
        return pageModel;
    }
    
    public void setPageModel(String pageModel) {
        this.pageModel = pageModel;
    }
    public String getSumCodeStr() {
        return super.getSumCode()+"("+SysDataUtil.getNameFromDataDict(DictCode.汇总科目, super.getSumCode())+")";
    }
    public String getNextIndexTypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.估值字段类型, super.getNextIndexType());
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
}
