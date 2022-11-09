/*
 * 文件名：RskValStructureDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月10日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.dto;

import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RskValtblStructMapDtl;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.invest.common.util.ExcelUtilForRisk;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.valid.RskValStructureValidator;
import com.joyintech.workflow.engine.dto.ProcessEntity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author wangzehui
 * @version 1.0 2018年12月10日
 * @see RskValStructureDto
 * @since 1.0
 */
@ValidLogic(targetClass = RskValStructureValidator.class, groups = {ValidateGroupEdit.class,
    ValidateGroupDelete.class})
public class RskValStructureDto extends RskValtblStructMapDtl implements ITableRow, ProcessEntity{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 映射名称
     */
    private String structName;

    /**
     * 列索引(字母)
     */
    private String srcColIndexLetter;

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
    private String oldStructName;

    
    public String getStructName() {
        return structName;
    }



    
    public void setStructName(String structName) {
        this.structName = structName;
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
    public String getSrcColDataTypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.估值字段类型, super.getSrcColDataType());
    }
    public String getTgtIndexStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.估值字段, super.getTgtIndex());
    }

    /*public String getSrcColIndexLetter(){
        if(StringUtil.isNotEmpty(this.getSrcColIndex())){
            return this.getSrcColIndex();
        }
        return srcColIndexLetter;
    }*/
    public String getSrcColIndexLetter()  throws Exception {
        String srcColIndexLetter="";
        if(StringUtil.isNotEmpty(this.getSrcColIndex()) && this.getSrcColIndex().matches("\\d+")){
            return ExcelUtilForRisk.indexToColumn(Integer.parseInt(String.valueOf(this.getSrcColIndex())));
        }else{
            srcColIndexLetter= this.getSrcColIndex();
        }
        //保存时需要转换
        if(StringUtil.isEmpty(srcColIndexLetter)){
            srcColIndexLetter = this.srcColIndexLetter;
        }
        return srcColIndexLetter;
    }
    public void setSrcColIndexLetter(String srcColIndexLetter) {
        this.srcColIndexLetter = srcColIndexLetter;
    }

    public String getOldStructName() {
        return oldStructName;
    }

    public void setOldStructName(String oldStructName) {
        this.oldStructName = oldStructName;
    }
}
