/*
 * 文件名：ProRegistCodeManage001Dto.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：tangliang
 * 修改时间：2017年9月20日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */
 
package com.joyintech.tams.report.system.proregistcodemanage.dto;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.TableRow;
import com.joyintech.tams.report.common.base.annotate.DictFormat;

import java.util.Date;


/**
 * 产品登记编码管理Dto
 * 〈功能详细描述〉
 * @author tangliang
 * @version 1.0 2017年9月20日
 * @see ProRegistCodeManage001Dto
 * @since 1.0
 */
public class ProRegistCodeManage001Dto extends BaseEntity implements ITableRow {
    /**
     * excel 列
     */
    private String excelNum;
    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 机构代码
     */
    private String orgCode;
    
    /**
     * 机构代码名称
     */
    private String orgName;
    
    /**
     * 产品名称
     */
    private String prodName;

    /**
     * 产品登记编码
     */
    private String regCode;

    /**
     * 起息日
     */
    private String vdate;

    /**
     * 到期日
     */
    private String mdate;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 投资者类型
     */
    private String investor;

    /**
     * 备注
     */
    private String remark;

    /**
     * 起息日开始
     */
    private Date vdateStart;

    /**
     * 起息日结束
     */
    private Date vdateEnd;

    /**
     * 到期日开始
     */
    private Date mdateStart;

    /**
     * 到期日结束
     */
    private Date mdateEnd;
    
    /**
     * 登记编码为空
     */
    private boolean isNotNull;
    
    /**
     * 募集开始日
     */
    private String raiseVdate;

    /**
     * 发送备注
     */
    private String sendStatus;

    /**
     * send_remark
     */
    private String send_remark;

    /**
     * 募集起始日期（从）
     */
    private String raisePstrDate;
    /**
     * 募集起始日期（到）
     */
    private String raisePendDate;
    
    /**
     * 登记编码生成日
     */
    private String generateDate;
    /**
     * 产品运作模式
     */
    @DictFormat(DictCode.产品运作模式)
    private String operateMode;

    private String prodInsId;

    /**
     * 获取 prodid
     * @return prodId.
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 设置 prodId
     * @param prodId prodid
     */
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    /**
     * 获取 orgCode
     * @return orgCode.
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 orgCode
     * @param orgCode orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取 prodname
     * @return prodname.
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 设置 prodname
     * @param prodName prodname
     */
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    /**
     * 获取 regCode
     * @return regCode.
     */
    public String getRegCode() {
        return regCode;
    }

    /**
     * 设置 regCode
     * @param regCode regCode
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    /**
     * 获取 vdate
     * @return vdate.
     */
    public String getVdate() {
        return vdate;
    }

    /**
     * 设置 vdate
     * @param vdate vdate
     */
    public void setVdate(String vdate) {
        this.vdate = vdate;
    }

    /**
     * 获取 mdate
     * @return mdate.
     */
    public String getMdate() {
        return mdate;
    }

    /**
     * 设置 mdate
     * @param mdate mdate
     */
    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    /**
     * 获取 deptCode
     * @return deptCode.
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 deptCode
     * @param deptCode deptCode
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * 获取 investor
     * @return investor.
     */
    public String getInvestor() {
        return investor;
    }

    /**
     * 设置 investor
     * @param investor investor
     */
    public void setInvestor(String investor) {
        this.investor = investor;
    }

    /**
     * 获取 remark
     * @return remark.
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 remark
     * @param remark remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取 vdateStart
     * @return vdateStart.
     */
    public Date getVdateStart() {
        return vdateStart;
    }

    /**
     * 设置 vdateStart
     * @param vdateStart vdateStart
     */
    public void setVdateStart(Date vdateStart) {
        this.vdateStart = vdateStart;
    }

    /**
     * 获取 vdateEnd
     * @return vdateEnd.
     */
    public Date getVdateEnd() {
        return vdateEnd;
    }

    /**
     * 设置 vdateEnd
     * @param vdateEnd vdateEnd
     */
    public void setVdateEnd(Date vdateEnd) {
        this.vdateEnd = vdateEnd;
    }

    /**
     * 获取 mdateStart
     * @return mdateStart.
     */
    public Date getMdateStart() {
        return mdateStart;
    }

    /**
     * 设置 mdateStart
     * @param mdateStart mdateStart
     */
    public void setMdateStart(Date mdateStart) {
        this.mdateStart = mdateStart;
    }

    /**
     * 获取 mdateEnd
     * @return mdateEnd.
     */
    public Date getMdateEnd() {
        return mdateEnd;
    }

    /**
     * 设置 mdateEnd
     * @param mdateEnd mdateEnd
     */
    public void setMdateEnd(Date mdateEnd) {
        this.mdateEnd = mdateEnd;
    }

    /**
     * 获取 isNotNull
     * @return isNotNull.
     */
    public boolean getIsNotNull() {
        return isNotNull;
    }

    /**
     * 设置 isNotNull
     * @param isNotNull isNotNull
     */
    public void setIsNotNull(boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    
    /**
     * 获取 excelNum
     * @return excelNum.
     */
    public String getExcelNum() {
        return excelNum;
    }

    
    /**
     * 设置 excelNum
     * @param excelNum excelNum
     */
    public void setExcelNum(String excelNum) {
        this.excelNum = excelNum;
    }

	public String getRaiseVdate() {
		return raiseVdate;
	}

	public void setRaiseVdate(String raiseVdate) {
		this.raiseVdate = raiseVdate;
	}

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendStatusStr() {
        return StringUtil.isNotEmpty(SysDataUtil.getNameFromDataDict(DictCode.发送状态, getSendStatus())) ? SysDataUtil.getNameFromDataDict(DictCode.发送状态, getSendStatus()):"";
    }

    public String getSend_remark() {
        return send_remark;
    }

    public void setSend_remark(String send_remark) {
        this.send_remark = send_remark;
    }

    public String getRaisePstrDate() {
        return raisePstrDate;
    }

    public void setRaisePstrDate(String raisePstrDate) {
        this.raisePstrDate = raisePstrDate;
    }

    public String getRaisePendDate() {
        return raisePendDate;
    }

    public void setRaisePendDate(String raisePendDate) {
        this.raisePendDate = raisePendDate;
    }
    /**
     * 获取 operateMode
     * @return operateMode.
     */
	public String getOperateMode() {
		return operateMode;
	}

	
    public String getOperateModeStr() {
        return operateMode==null?null:SysDataUtil.getNameFromDataDict(DictCode.产品运作模式, this.operateMode);
    }

    /**
     * 设置 业务种类
     * @param busiType 业务种类
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode == null ? null : operateMode.trim();
    }

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}

    public String getProdInsId() {
        return prodInsId;
    }

    public void setProdInsId(String prodInsId) {
        this.prodInsId = prodInsId;
    }
}