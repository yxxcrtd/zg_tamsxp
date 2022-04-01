/*
 * 文件名：SystemFeedback001Dto.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：dingzhipeng
 * 修改时间：2017年9月21日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */
 
package com.joyintech.tams.report.system.debtsubmitinfo.dto;

import java.util.Date;

import com.joyintech.tams.common.base.entity.ChbInfoReport;

/**
 * 用于存储报送信息反馈的Dto
 * 〈功能详细描述〉
 * @author dingzhipeng
 * @version 1.0 2017年9月21日
 * @see DebtSubmitInfo001Dto
 * @since 1.0
 */
public class DebtSubmitInfo001Dto extends ChbInfoReport{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -6263678879039171171L;
    /**
     * 报送总数
     */
    private Integer submitCount;
    /**
     * 唯一标识
     */
    private String uniqueIdenti;
    /**
     * 报文流水号
     */
    private String reportNo;

    /**
     * 报文流水号
     */
    private String seqNo;
    /**
     * 报送文件id
     */
    private String repFileId;
    /**
     * 报送结果
     */
    private String repResult;
    /**
     * 报表名称
     */
    private String repName;
    /**
     * 报送失败信息
     */
    private String repFailedInfo;
    /**
     * 报送日期
     */
    private Date repDate;
    
    
    /**
     * 获取 repDate
     * @return repDate.
     */
    public Date getRepDate() {
        return repDate;
    }

    
    /**
     * 设置 repDate
     * @param repDate repDate
     */
    public void setRepDate(Date repDate) {
        this.repDate = repDate;
    }

    /**
     * 获取 seqNo
     * @return seqNo.
     */
    public String getSeqNo() {
        return seqNo;
    }
    
    /**
     * 设置 seqNo
     * @param seqNo seqNo
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    
    /**
     * 获取 repFileId
     * @return repFileId.
     */
    public String getRepFileId() {
        return repFileId;
    }
    
    /**
     * 设置 repFileId
     * @param repFileId repFileId
     */
    public void setRepFileId(String repFileId) {
        this.repFileId = repFileId;
    }
    
    /**
     * 获取 repResult
     * @return repResult.
     */
    public String getRepResult() {
        return repResult;
    }
    
    /**
     * 设置 repResult
     * @param repResult repResult
     */
    public void setRepResult(String repResult) {
        this.repResult = repResult;
    }
    
    /**
     * 获取 repName
     * @return repName.
     */
    public String getRepName() {
        return repName;
    }
    
    /**
     * 设置 repName
     * @param repName repName
     */
    public void setRepName(String repName) {
        this.repName = repName;
    }
    
    /**
     * 获取 repFailedInfo
     * @return repFailedInfo.
     */
    public String getRepFailedInfo() {
        return repFailedInfo;
    }
    
    /**
     * 设置 repFailedInfo
     * @param repFailedInfo repFailedInfo
     */
    public void setRepFailedInfo(String repFailedInfo) {
        this.repFailedInfo = repFailedInfo;
    }
    
    /**
     * 获取 serialversionuid
     * @return serialversionuid.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    
    /**
     * 获取 reportNo
     * @return reportNo.
     */
    public String getReportNo() {
        return reportNo;
    }


    
    /**
     * 设置 reportNo
     * @param reportNo reportNo
     */
    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }
    
    
    /**
     * 获取 uniqueIdenti
     * @return uniqueIdenti.
     */
    public String getUniqueIdenti() {
        return uniqueIdenti;
    }


    
    /**
     * 设置 uniqueIdenti
     * @param uniqueIdenti uniqueIdenti
     */
    public void setUniqueIdenti(String uniqueIdenti) {
        this.uniqueIdenti = uniqueIdenti;
    }


    
    public Integer getSubmitCount() {
        return submitCount;
    }


    
    public void setSubmitCount(Integer submitCount) {
        this.submitCount = submitCount;
    }
    /***三期CHB_SUBMIT_STATUS去除改造  20220310**/

    /**
     * 报表编号
     */
    private String repCode;
    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }
    /***三期CHB_SUBMIT_STATUS去除改造  20220310**/

}
