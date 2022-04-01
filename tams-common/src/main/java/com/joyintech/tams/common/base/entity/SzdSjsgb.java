package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.math.BigDecimal;
import java.util.Date;

public class SzdSjsgb extends BaseEntity {
    /**
     * 流水号
     */
    private String seqno;

    /**
     * 导入日期
     */
    private Date importDate;

    /**
     * 数据文件
     */
    private String dataFile;

    /**
     * 广播类别
     */
    private String gblb;

    /**
     * 代码1
     */
    private String gbdm1;

    /**
     * 代码2
     */
    private String gbdm2;

    /**
     * 代码3
     */
    private String gbdm3;

    /**
     * 数量
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal gbsl;

    /**
     * 日期1
     */
    private Date gbrq1;

    /**
     * 日期2
     */
    private Date gbrq2;

    /**
     * 比例
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal gbbl;

    /**
     * 金额1
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal gbje1;

    /**
     * 金额2
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal gbje2;

    /**
     * 广播字段
     */
    private String gbzd;

    /**
     * 标志1
     */
    private String gbbz1;

    /**
     * 标志2
     */
    private String gbbz2;

    /**
     * 备注说明
     */
    private String gbbzsm;

    /**
     * 数量2
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal gbsl2;

    /**
     * 比例2
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal gbbl2;

    /**
     * 日期3
     */
    private Date gbrq3;

    /**
     * 日期4
     */
    private Date gbrq4;

    /**
     * 广播日期
     */
    private Date gbrq;

    /**
     * 取得 流水号
     * @return 流水号
     */
    public String getSeqno() {
        return seqno;
    }

    /**
     * 设置 流水号
     * @param seqno 流水号
     */
    public void setSeqno(String seqno) {
        this.seqno = seqno == null ? null : seqno.trim();
    }

    /**
     * 取得 导入日期
     * @return 导入日期
     */
    public Date getImportDate() {
        return importDate;
    }

    /**
     * 设置 导入日期
     * @param importDate 导入日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    /**
     * 取得 数据文件
     * @return 数据文件
     */
    public String getDataFile() {
        return dataFile;
    }

    /**
     * 设置 数据文件
     * @param dataFile 数据文件
     */
    public void setDataFile(String dataFile) {
        this.dataFile = dataFile == null ? null : dataFile.trim();
    }

    /**
     * 取得 广播类别
     * @return 广播类别
     */
    public String getGblb() {
        return gblb;
    }

    /**
     * 设置 广播类别
     * @param gblb 广播类别
     */
    public void setGblb(String gblb) {
        this.gblb = gblb == null ? null : gblb.trim();
    }

    /**
     * 取得 代码1
     * @return 代码1
     */
    public String getGbdm1() {
        return gbdm1;
    }

    /**
     * 设置 代码1
     * @param gbdm1 代码1
     */
    public void setGbdm1(String gbdm1) {
        this.gbdm1 = gbdm1 == null ? null : gbdm1.trim();
    }

    /**
     * 取得 代码2
     * @return 代码2
     */
    public String getGbdm2() {
        return gbdm2;
    }

    /**
     * 设置 代码2
     * @param gbdm2 代码2
     */
    public void setGbdm2(String gbdm2) {
        this.gbdm2 = gbdm2 == null ? null : gbdm2.trim();
    }

    /**
     * 取得 代码3
     * @return 代码3
     */
    public String getGbdm3() {
        return gbdm3;
    }

    /**
     * 设置 代码3
     * @param gbdm3 代码3
     */
    public void setGbdm3(String gbdm3) {
        this.gbdm3 = gbdm3 == null ? null : gbdm3.trim();
    }

    /**
     * 取得 数量
     * @return 数量
     */
    public BigDecimal getGbsl() {
        return gbsl;
    }

    /**
     * 设置 数量
     * @param gbsl 数量
     */
    public void setGbsl(BigDecimal gbsl) {
        this.gbsl = gbsl;
    }

    /**
     * 取得 日期1
     * @return 日期1
     */
    public Date getGbrq1() {
        return gbrq1;
    }

    /**
     * 设置 日期1
     * @param gbrq1 日期1
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setGbrq1(Date gbrq1) {
        this.gbrq1 = gbrq1;
    }

    /**
     * 取得 日期2
     * @return 日期2
     */
    public Date getGbrq2() {
        return gbrq2;
    }

    /**
     * 设置 日期2
     * @param gbrq2 日期2
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setGbrq2(Date gbrq2) {
        this.gbrq2 = gbrq2;
    }

    /**
     * 取得 比例
     * @return 比例
     */
    public BigDecimal getGbbl() {
        return gbbl;
    }

    /**
     * 设置 比例
     * @param gbbl 比例
     */
    public void setGbbl(BigDecimal gbbl) {
        this.gbbl = gbbl;
    }

    /**
     * 取得 金额1
     * @return 金额1
     */
    public BigDecimal getGbje1() {
        return gbje1;
    }

    /**
     * 设置 金额1
     * @param gbje1 金额1
     */
    public void setGbje1(BigDecimal gbje1) {
        this.gbje1 = gbje1;
    }

    /**
     * 取得 金额2
     * @return 金额2
     */
    public BigDecimal getGbje2() {
        return gbje2;
    }

    /**
     * 设置 金额2
     * @param gbje2 金额2
     */
    public void setGbje2(BigDecimal gbje2) {
        this.gbje2 = gbje2;
    }

    /**
     * 取得 广播字段
     * @return 广播字段
     */
    public String getGbzd() {
        return gbzd;
    }

    /**
     * 设置 广播字段
     * @param gbzd 广播字段
     */
    public void setGbzd(String gbzd) {
        this.gbzd = gbzd == null ? null : gbzd.trim();
    }

    /**
     * 取得 标志1
     * @return 标志1
     */
    public String getGbbz1() {
        return gbbz1;
    }

    /**
     * 设置 标志1
     * @param gbbz1 标志1
     */
    public void setGbbz1(String gbbz1) {
        this.gbbz1 = gbbz1 == null ? null : gbbz1.trim();
    }

    /**
     * 取得 标志2
     * @return 标志2
     */
    public String getGbbz2() {
        return gbbz2;
    }

    /**
     * 设置 标志2
     * @param gbbz2 标志2
     */
    public void setGbbz2(String gbbz2) {
        this.gbbz2 = gbbz2 == null ? null : gbbz2.trim();
    }

    /**
     * 取得 备注说明
     * @return 备注说明
     */
    public String getGbbzsm() {
        return gbbzsm;
    }

    /**
     * 设置 备注说明
     * @param gbbzsm 备注说明
     */
    public void setGbbzsm(String gbbzsm) {
        this.gbbzsm = gbbzsm == null ? null : gbbzsm.trim();
    }

    /**
     * 取得 数量2
     * @return 数量2
     */
    public BigDecimal getGbsl2() {
        return gbsl2;
    }

    /**
     * 设置 数量2
     * @param gbsl2 数量2
     */
    public void setGbsl2(BigDecimal gbsl2) {
        this.gbsl2 = gbsl2;
    }

    /**
     * 取得 比例2
     * @return 比例2
     */
    public BigDecimal getGbbl2() {
        return gbbl2;
    }

    /**
     * 设置 比例2
     * @param gbbl2 比例2
     */
    public void setGbbl2(BigDecimal gbbl2) {
        this.gbbl2 = gbbl2;
    }

    /**
     * 取得 日期3
     * @return 日期3
     */
    public Date getGbrq3() {
        return gbrq3;
    }

    /**
     * 设置 日期3
     * @param gbrq3 日期3
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setGbrq3(Date gbrq3) {
        this.gbrq3 = gbrq3;
    }

    /**
     * 取得 日期4
     * @return 日期4
     */
    public Date getGbrq4() {
        return gbrq4;
    }

    /**
     * 设置 日期4
     * @param gbrq4 日期4
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setGbrq4(Date gbrq4) {
        this.gbrq4 = gbrq4;
    }

    /**
     * 取得 广播日期
     * @return 广播日期
     */
    public Date getGbrq() {
        return gbrq;
    }

    /**
     * 设置 广播日期
     * @param gbrq 广播日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setGbrq(Date gbrq) {
        this.gbrq = gbrq;
    }
}