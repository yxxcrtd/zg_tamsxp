package com.joyintech.tams.invest.risk.valtblimport.dto;

import java.util.Date;

public class RskValtblAnlsLogDto {
    /**
     * 实体类含义：估值解析日志表Dto
     */

    /**
     * 流水号
     */
    private String seqNo;

    /**
     *估值文件ID
     */
    private String fileInfoId;

    /**
     *估值表文件名
     */
    private String fileInfoName;

    /**
     * 解析日志
     */
    private String remark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 跟新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(String fileInfoId) {
        this.fileInfoId = fileInfoId;
    }

    public String getFileInfoName() {
        return fileInfoName;
    }

    public void setFileInfoName(String fileInfoName) {
        this.fileInfoName = fileInfoName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
