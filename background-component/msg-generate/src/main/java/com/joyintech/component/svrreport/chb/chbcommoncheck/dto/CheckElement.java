package com.joyintech.component.svrreport.chb.chbcommoncheck.dto;

/**
 * 描述:基本检验规则实体类
 * @version 1.7
 * @author:  chenxc
 * @创建时间: 2019-04-26
 */
public class CheckElement {
    /**
     * 检查方法
     */
    private String checkmethod;

    /**
     * 被检查的字段
     */
    private String srcproperty;

    /**
     * 检查比较关系：>,<,>=,<=,==,!=
     */
    private String compareto;

    /**
     * 目标结果
     */
    private String objvalue;

    /**
     * 是否可空
     */
    private String nullable;
    
    /**
     * 备注
     */
    private String remark;

    /**
     * 有效状态,E:有效,D:删除
     */
    private String status;

    /**
     * 检查方法
     * @return CHECKMETHOD 检查方法
     */
    public String getCheckmethod() {
        return checkmethod;
    }

    /**
     * 检查方法
     * @param checkmethod 检查方法
     */
    public void setCheckmethod(String checkmethod) {
        this.checkmethod = checkmethod == null ? null : checkmethod.trim();
    }

    /**
     * 被检查的字段
     * @return SRCPROPERTY 被检查的字段
     */
    public String getSrcproperty() {
        return srcproperty;
    }

    /**
     * 被检查的字段
     * @param srcproperty 被检查的字段
     */
    public void setSrcproperty(String srcproperty) {
        this.srcproperty = srcproperty == null ? null : srcproperty.trim();
    }

    /**
     * 检查比较关系：>,<,>=,<=,==,!=
     * @return COMPARETO 检查比较关系：>,<,>=,<=,==,!=
     */
    public String getCompareto() {
        return compareto;
    }

    /**
     * 检查比较关系：>,<,>=,<=,==,!=
     * @param compareto 检查比较关系：>,<,>=,<=,==,!=
     */
    public void setCompareto(String compareto) {
        this.compareto = compareto == null ? null : compareto.trim();
    }

    /**
     * 目标结果
     * @return OBJVALUE 目标结果
     */
    public String getObjvalue() {
        return objvalue;
    }

    /**
     * 目标结果
     * @param objvalue 目标结果
     */
    public void setObjvalue(String objvalue) {
        this.objvalue = objvalue == null ? null : objvalue.trim();
    }
    
    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }
    
    public boolean allowNullable() {
        return ("Y".equals(nullable)?true:false);
    }

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 有效状态,E:有效,D:删除
     * @return STATUS 有效状态,E:有效,D:删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 有效状态,E:有效,D:删除
     * @param status 有效状态,E:有效,D:删除
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    
}