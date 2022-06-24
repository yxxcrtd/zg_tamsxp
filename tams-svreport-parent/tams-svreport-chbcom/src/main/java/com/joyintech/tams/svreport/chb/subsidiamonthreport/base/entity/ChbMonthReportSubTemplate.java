package com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import java.math.BigDecimal;

public class ChbMonthReportSubTemplate extends BaseEntity {
    /**
     * 主键
     */
    private String tempuuid;

    /**
     * 模版编号
     */
    private String templateid;

    /**
     * 属性编号
     */
    private String propertyid;

    /**
     * 属性代码
     */
    private String propertycode;

    /**
     * 属性名称
     */
    private String propername;

    /**
     * 排序
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal orderno;

    /**
     * 报表类型：PROD-产品，BALANCE-资产
     */
    private String reporttype;

    /**
     * 报表周期：D-日，WE-周报，M-月报，S-季，Y-年
     */
    private String reportcycle;

    /**
     * 取得 主键
     * @return 主键
     */
    public String getTempuuid() {
        return tempuuid;
    }

    /**
     * 设置 主键
     * @param tempuuid 主键
     */
    public void setTempuuid(String tempuuid) {
        this.tempuuid = tempuuid == null ? null : tempuuid.trim();
    }

    /**
     * 取得 模版编号
     * @return 模版编号
     */
    public String getTemplateid() {
        return templateid;
    }

    /**
     * 设置 模版编号
     * @param templateid 模版编号
     */
    public void setTemplateid(String templateid) {
        this.templateid = templateid == null ? null : templateid.trim();
    }

    /**
     * 取得 属性编号
     * @return 属性编号
     */
    public String getPropertyid() {
        return propertyid;
    }

    /**
     * 设置 属性编号
     * @param propertyid 属性编号
     */
    public void setPropertyid(String propertyid) {
        this.propertyid = propertyid == null ? null : propertyid.trim();
    }

    /**
     * 取得 属性代码
     * @return 属性代码
     */
    public String getPropertycode() {
        return propertycode;
    }

    /**
     * 设置 属性代码
     * @param propertycode 属性代码
     */
    public void setPropertycode(String propertycode) {
        this.propertycode = propertycode == null ? null : propertycode.trim();
    }

    /**
     * 取得 属性名称
     * @return 属性名称
     */
    public String getPropername() {
        return propername;
    }

    /**
     * 设置 属性名称
     * @param propername 属性名称
     */
    public void setPropername(String propername) {
        this.propername = propername == null ? null : propername.trim();
    }

    /**
     * 取得 排序
     * @return 排序
     */
    public BigDecimal getOrderno() {
        return orderno;
    }

    /**
     * 设置 排序
     * @param orderno 排序
     */
    public void setOrderno(BigDecimal orderno) {
        this.orderno = orderno;
    }

    /**
     * 取得 报表类型：PROD-产品，BALANCE-资产
     * @return 报表类型：PROD-产品，BALANCE-资产
     */
    public String getReporttype() {
        return reporttype;
    }

    /**
     * 设置 报表类型：PROD-产品，BALANCE-资产
     * @param reporttype 报表类型：PROD-产品，BALANCE-资产
     */
    public void setReporttype(String reporttype) {
        this.reporttype = reporttype == null ? null : reporttype.trim();
    }

    /**
     * 取得 报表周期：D-日，WE-周报，M-月报，S-季，Y-年
     * @return 报表周期：D-日，WE-周报，M-月报，S-季，Y-年
     */
    public String getReportcycle() {
        return reportcycle;
    }

    /**
     * 设置 报表周期：D-日，WE-周报，M-月报，S-季，Y-年
     * @param reportcycle 报表周期：D-日，WE-周报，M-月报，S-季，Y-年
     */
    public void setReportcycle(String reportcycle) {
        this.reportcycle = reportcycle == null ? null : reportcycle.trim();
    }
}