package com.joyintech.service.risk.valtblimport.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RskValtblFileInfoDto extends RskValtblDataDto {
    /**
     *实体类含义：估值文件表Dto
     *
     */

    /**
     * 流水号
     */
    private String seqNo;

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
     * 基金资产净值-本币成本
     */
    private BigDecimal fundAssetValCost;
    /**
     * 基金资产净值-本币市值
     */
    private BigDecimal fundAssetValSmv;

    /**
     * 资产合计-本币成本
     */
    private BigDecimal assetTotalCost;

    /**
     * 资产合计-本币市值
     */
    private BigDecimal assetTotalSmv;

    /**
     * 负债合计-本币成本
     */
    private BigDecimal liabilityTotalCost;
    /**
     * 负债合计-本币市值
     */
    private BigDecimal liabilityTotalSmv;

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

    List<RskValtblDataDto> rskValtblDataDto;

    public List<RskValtblDataDto> getRskValtblDataDto() {
        return rskValtblDataDto;
    }

    public void setRskValtblDataDto(List<RskValtblDataDto> rskValtblDataDto) {
        this.rskValtblDataDto = rskValtblDataDto;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
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

    public BigDecimal getFundAssetValCost() {
        return fundAssetValCost;
    }

    public void setFundAssetValCost(BigDecimal fundAssetValCost) {
        this.fundAssetValCost = fundAssetValCost;
    }

    public BigDecimal getFundAssetValSmv() {
        return fundAssetValSmv;
    }

    public void setFundAssetValSmv(BigDecimal fundAssetValSmv) {
        this.fundAssetValSmv = fundAssetValSmv;
    }

    public BigDecimal getAssetTotalCost() {
        return assetTotalCost;
    }

    public void setAssetTotalCost(BigDecimal assetTotalCost) {
        this.assetTotalCost = assetTotalCost;
    }

    public BigDecimal getAssetTotalSmv() {
        return assetTotalSmv;
    }

    public void setAssetTotalSmv(BigDecimal assetTotalSmv) {
        this.assetTotalSmv = assetTotalSmv;
    }

    public BigDecimal getLiabilityTotalCost() {
        return liabilityTotalCost;
    }

    public void setLiabilityTotalCost(BigDecimal liabilityTotalCost) {
        this.liabilityTotalCost = liabilityTotalCost;
    }

    public BigDecimal getLiabilityTotalSmv() {
        return liabilityTotalSmv;
    }

    public void setLiabilityTotalSmv(BigDecimal liabilityTotalSmv) {
        this.liabilityTotalSmv = liabilityTotalSmv;
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
