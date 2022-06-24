package com.joyintech.tams.common.base.entity;

import java.util.Date;

/**
 * 描述:CHB_INTERF_CONFIG表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-05-08
 */
public class ChbInterfConfigDto {
    /**
     * 接口代码
     */
    private String interfId;

    /**
     * 最近更新时间
     */
    private Date lastUpdatetime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 接口名称
     */
    private String interfName;

    /**
     * 内容类型：F－全量，I－增量
     */
    private String contentType;

    /**
     * 内容来源：F-文件，D－数据库
     */
    private String contentSrc;

    /**
     * 影响的报表，以分号分割
     */
    private String refReport;

    /**
     * 适用范围，按照中债、人行、信批设置1，或者0
     */
    private String sysScope;

    /**
     * 有效状态，E－有效，D－删除
     */
    private String status;

    /**
     * 是否覆盖
     */
    private String coverFlag;

    /**
     * 接口代码
     * @return INTERF_ID 接口代码
     */
    public String getInterfId() {
        return interfId;
    }

    /**
     * 接口代码
     * @param interfId 接口代码
     */
    public void setInterfId(String interfId) {
        this.interfId = interfId == null ? null : interfId.trim();
    }

    /**
     * 最近更新时间
     * @return LAST_UPDATETIME 最近更新时间
     */
    public Date getLastUpdatetime() {
        return lastUpdatetime;
    }

    /**
     * 最近更新时间
     * @param lastUpdatetime 最近更新时间
     */
    public void setLastUpdatetime(Date lastUpdatetime) {
        this.lastUpdatetime = lastUpdatetime;
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
     * 接口名称
     * @return INTERF_NAME 接口名称
     */
    public String getInterfName() {
        return interfName;
    }

    /**
     * 接口名称
     * @param interfName 接口名称
     */
    public void setInterfName(String interfName) {
        this.interfName = interfName == null ? null : interfName.trim();
    }

    /**
     * 内容类型：F－全量，I－增量
     * @return CONTENT_TYPE 内容类型：F－全量，I－增量
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 内容类型：F－全量，I－增量
     * @param contentType 内容类型：F－全量，I－增量
     */
    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    /**
     * 内容来源：F-文件，D－数据库
     * @return CONTENT_SRC 内容来源：F-文件，D－数据库
     */
    public String getContentSrc() {
        return contentSrc;
    }

    /**
     * 内容来源：F-文件，D－数据库
     * @param contentSrc 内容来源：F-文件，D－数据库
     */
    public void setContentSrc(String contentSrc) {
        this.contentSrc = contentSrc == null ? null : contentSrc.trim();
    }

    /**
     * 影响的报表，以分号分割
     * @return REF_REPORT 影响的报表，以分号分割
     */
    public String getRefReport() {
        return refReport;
    }

    /**
     * 影响的报表，以分号分割
     * @param refReport 影响的报表，以分号分割
     */
    public void setRefReport(String refReport) {
        this.refReport = refReport == null ? null : refReport.trim();
    }

    /**
     * 适用范围，按照中债、人行、信批设置1，或者0
     * @return SYS_SCOPE 适用范围，按照中债、人行、信批设置1，或者0
     */
    public String getSysScope() {
        return sysScope;
    }

    /**
     * 适用范围，按照中债、人行、信批设置1，或者0
     * @param sysScope 适用范围，按照中债、人行、信批设置1，或者0
     */
    public void setSysScope(String sysScope) {
        this.sysScope = sysScope == null ? null : sysScope.trim();
    }

    /**
     * 有效状态，E－有效，D－删除
     * @return STATUS 有效状态，E－有效，D－删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 有效状态，E－有效，D－删除
     * @param status 有效状态，E－有效，D－删除
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCoverFlag() {
        return coverFlag;
    }

    public void setCoverFlag(String coverFlag) {
        this.coverFlag = coverFlag;
    }
}