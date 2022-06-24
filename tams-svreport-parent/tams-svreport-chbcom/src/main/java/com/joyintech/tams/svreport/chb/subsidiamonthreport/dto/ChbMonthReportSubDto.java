package com.joyintech.tams.svreport.chb.subsidiamonthreport.dto;

import java.text.SimpleDateFormat;
import java.util.List;

import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSub;

/**
 * 理财子公司月度报表主表Dto
 *
 * @author 杜保军
 * @version 1.0 2020年6月3日
 * @since 1.0
 */
public class ChbMonthReportSubDto extends ChbMonthReportSub {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -1359121420535753789L;
    /**
     * 产品id
     */
    private String prodid;
    /**
     * 产品id
     */
    private List<String> prodids;

    /**
     * 月报明细数据
     */
    private List<ChbMonthReportSubDetailDto> reportDetailList;
    /**
     * 不需要删除的主表外键 留痕时重新跑批不删除上次已报送
     */
    private String nodelreportuuid;

    /**
     * 未报送uuid删除时用
     */
    private String wbsreportuuid;

    /**
     * 系统参数
     */
    private String intamttype;

    /**
     * 系统参数
     */
    private String cleanamttype;

    /**
     * 系统参数
     */
    private String monthReport;

    public ChbMonthReportSubDto() {
        super();
        this.setStatus(null);
    }

    /**
     * Gets the value of prodid. *
     *
     * @return the value of prodid
     */
    public String getProdid() {
        return prodid;
    }

    /**
     * Sets the prodid. *
     * <p>You can use getProdid() to get the value of prodid</p>
     * * @param prodid prodid
     */
    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    /**
     * Gets the value of prodids. *
     *
     * @return the value of prodids
     */
    public List<String> getProdids() {
        return prodids;
    }

    /**
     * Sets the prodids. *
     * <p>You can use getProdids() to get the value of prodids</p>
     * * @param prodids prodids
     */
    public void setProdids(List<String> prodids) {
        this.prodids = prodids;
    }

    /**
     * Gets the value of reportDetailList. *
     *
     * @return the value of reportDetailList
     */
    public List<ChbMonthReportSubDetailDto> getReportDetailList() {
        return reportDetailList;
    }

    /**
     * Sets the reportDetailList. *
     * <p>You can use getReportDetailList() to get the value of reportDetailList</p>
     * * @param reportDetailList reportDetailList
     */
    public void setReportDetailList(List<ChbMonthReportSubDetailDto> reportDetailList) {
        this.reportDetailList = reportDetailList;
    }

    /**
     * Gets the value of nodelreportuuid. *
     *
     * @return the value of nodelreportuuid
     */
    public String getNodelreportuuid() {
        return nodelreportuuid;
    }

    /**
     * Sets the nodelreportuuid. *
     * <p>You can use getNodelreportuuid() to get the value of nodelreportuuid</p>
     * * @param nodelreportuuid nodelreportuuid
     */
    public void setNodelreportuuid(String nodelreportuuid) {
        this.nodelreportuuid = nodelreportuuid;
    }

    /**
     * Gets the value of wbsreportuuid. *
     *
     * @return the value of wbsreportuuid
     */
    public String getWbsreportuuid() {
        return wbsreportuuid;
    }

    /**
     * Sets the wbsreportuuid. *
     * <p>You can use getWbsreportuuid() to get the value of wbsreportuuid</p>
     * * @param wbsreportuuid wbsreportuuid
     */
    public void setWbsreportuuid(String wbsreportuuid) {
        this.wbsreportuuid = wbsreportuuid;
    }

    /**
     * Gets the value of intamttype. *
     *
     * @return the value of intamttype
     */
    public String getIntamttype() {
        return intamttype;
    }

    /**
     * Sets the intamttype. *
     * <p>You can use getIntamttype() to get the value of intamttype</p>
     * * @param intamttype intamttype
     */
    public void setIntamttype(String intamttype) {
        this.intamttype = intamttype;
    }

    /**
     * Gets the value of cleanamttype. *
     *
     * @return the value of cleanamttype
     */
    public String getCleanamttype() {
        return cleanamttype;
    }

    /**
     * Sets the cleanamttype. *
     * <p>You can use getCleanamttype() to get the value of cleanamttype</p>
     * * @param cleanamttype cleanamttype
     */
    public void setCleanamttype(String cleanamttype) {
        this.cleanamttype = cleanamttype;
    }

    /**
     * Gets the value of monthReport. *
     *
     * @return the value of monthReport
     */
    public String getMonthReport() {
        return monthReport;
    }

    /**
     * Sets the monthReport. *
     * <p>You can use getMonthReport() to get the value of monthReport</p>
     * * @param monthReport monthReport
     */
    public void setMonthReport(String monthReport) {
        this.monthReport = monthReport;
    }

    /**
     * 获取报送数据状态名称
     *
     * @return 报送数据状态名称
     */
    public String getStatusStr() {
        return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getStatus());
    }

    /**
     * 获取报送状态名称
     *
     * @return 报送状态名称
     */
    public String getSendStatusStr() {
        return SvrDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
    }

    /**
     * 获取报送反馈状态名称
     *
     * @return 报送反馈状态名称
     */
    public String getSubmitFeedbackStatusStr() {
        return SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态, this.getSubmitFeedbackStatus());
    }

    /**
     * 获取统计年月
     *
     * @return 统计年月
     */
    public String getTermname() {
        if (this.getTermid() == null) {
            return null;
        }
        return this.getTermid().substring(0, 4) + "年" + this.getTermid().substring(4, 6) + "月";
    }
    public String getTermname1() {
    	if(super.getEnddate() == null){
    		return null;
    	}
    	SimpleDateFormat  format=new SimpleDateFormat("yyyyMMdd");
		return format.format(super.getEnddate());
    }
    
    /**
     * 获取报表名称
     *
     * @return 报表
     */
    public String getReportname() {
        return DictCodeListExt2.报表名称_理财子公司月度统计表.getCodeName() + "（" + this.getTermname() + "）";
    }
}
