package com.joyintech.tams.invest.risk.valtblimport.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RskValtblFileInfoDto {
    /**
     *实体类含义：估值文件表Dto
     *
     */

    /**
     * 流水号
     */
    private String sqlNO;

    /**
     * 估值表文件组
     */
    private String fileGpr;

    /**
     * 估值表文件名
     */
    private String fileName;

    /**
     * 估值表表头
     */
    private  String fileHeader;

    /**
     * 估值日期
     */
    private String fileDate;

    /**
     * 实收资本
     */
    private BigDecimal capital;


    /**
     * 基金资产净值
     */
    private BigDecimal fundAssetVal;

    /**
     * 资产合计
     */
    private BigDecimal assetTotal;

    /**
     * 负债合计
     */
    private BigDecimal liabilityTotal;

    /**
     * 万份收益
     */
    private BigDecimal porfit;

    /**
     * 今日单位净值
     */
    private BigDecimal todUnitVal;

    /**
     * 异常标识
     */
    private int expFlag;

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

    public String getSqlNO() {
        return sqlNO;
    }

    public void setSqlNO(String sqlNO) {
        this.sqlNO = sqlNO;
    }

    public String getFileGpr() {
        return fileGpr;
    }

    public void setFileGpr(String fileGpr) {
        this.fileGpr = fileGpr;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileHeader() {
        return fileHeader;
    }

    public void setFileHeader(String fileHeader) {
        this.fileHeader = fileHeader;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getFundAssetVal() {
        return fundAssetVal;
    }

    public void setFundAssetVal(BigDecimal fundAssetVal) {
        this.fundAssetVal = fundAssetVal;
    }

    public BigDecimal getAssetTotal() {
        return assetTotal;
    }

    public void setAssetTotal(BigDecimal assetTotal) {
        this.assetTotal = assetTotal;
    }

    public BigDecimal getLiabilityTotal() {
        return liabilityTotal;
    }

    public void setLiabilityTotal(BigDecimal liabilityTotal) {
        this.liabilityTotal = liabilityTotal;
    }

    public BigDecimal getPorfit() {
        return porfit;
    }

    public void setPorfit(BigDecimal porfit) {
        this.porfit = porfit;
    }

    public BigDecimal getTodUnitVal() {
        return todUnitVal;
    }

    public void setTodUnitVal(BigDecimal todUnitVal) {
        this.todUnitVal = todUnitVal;
    }

    public int getExpFlag() {
        return expFlag;
    }

    public void setExpFlag(int expFlag) {
        this.expFlag = expFlag;
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
