package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;
import java.util.Date;

public class InterfFundBonusFjy extends BaseEntity {
    private String seqno;

    private Date importDate;

    private String dataFile;

    private String cksjlx;

    private String fjyzqdm;

    private String fjyzqmc;

    private String cpzqdm;

    private String cpzqmc;

    private String fjyywlx;

    private Date fjyddsrdatestart;

    private Date fjyddsrdateend;

    private String createUser;

    private String createDept;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno == null ? null : seqno.trim();
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile == null ? null : dataFile.trim();
    }

    public String getCksjlx() {
        return cksjlx;
    }

    public void setCksjlx(String cksjlx) {
        this.cksjlx = cksjlx == null ? null : cksjlx.trim();
    }

    public String getFjyzqdm() {
        return fjyzqdm;
    }

    public void setFjyzqdm(String fjyzqdm) {
        this.fjyzqdm = fjyzqdm == null ? null : fjyzqdm.trim();
    }

    public String getFjyzqmc() {
        return fjyzqmc;
    }

    public void setFjyzqmc(String fjyzqmc) {
        this.fjyzqmc = fjyzqmc == null ? null : fjyzqmc.trim();
    }

    public String getCpzqdm() {
        return cpzqdm;
    }

    public void setCpzqdm(String cpzqdm) {
        this.cpzqdm = cpzqdm == null ? null : cpzqdm.trim();
    }

    public String getCpzqmc() {
        return cpzqmc;
    }

    public void setCpzqmc(String cpzqmc) {
        this.cpzqmc = cpzqmc == null ? null : cpzqmc.trim();
    }

    public String getFjyywlx() {
        return fjyywlx;
    }

    public void setFjyywlx(String fjyywlx) {
        this.fjyywlx = fjyywlx == null ? null : fjyywlx.trim();
    }

    public Date getFjyddsrdatestart() {
        return fjyddsrdatestart;
    }

    public void setFjyddsrdatestart(Date fjyddsrdatestart) {
        this.fjyddsrdatestart = fjyddsrdatestart;
    }

    public Date getFjyddsrdateend() {
        return fjyddsrdateend;
    }

    public void setFjyddsrdateend(Date fjyddsrdateend) {
        this.fjyddsrdateend = fjyddsrdateend;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}