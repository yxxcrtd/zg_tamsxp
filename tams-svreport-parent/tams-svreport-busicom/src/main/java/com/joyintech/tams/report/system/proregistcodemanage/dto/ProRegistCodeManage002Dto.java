package com.joyintech.tams.report.system.proregistcodemanage.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.CustomDateDeserialize;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.Date;

@PageId("ProRegistCodeManage003")
public class ProRegistCodeManage002Dto extends BaseEntity implements ProcessEntity {
    
	 /**
     * null
     */
    private String declareUuid;
	/**
     * 画面Id
     */
    private String pageId;
    /**
     * 操作类型
     */
    private String operateType;
    
    /**
     * 页面模式
     */
    private String pageModel;
    
    /**
     * elementId
     */
    private String elementId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 /**
     * excel 列
     */
    private String excelNum;
    /**
     * 产品代码
     */
  @PrimaryKey(Field.PK_1)
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
    @NotEmpty(fieldName="ProRegistCodeManage003.finprodName",groups = {ValidateGroupEdit.class})
    private String prodName;

    /**
     * 产品登记编码
     */
    @PrimaryKey(Field.PK_2)
    @NotEmpty(fieldName="ProRegistCodeManage003.prodRegistCode",groups = {ValidateGroupEdit.class})
    private String regCode;

    /**
     * 起息日
     */
    //@NotEmpty(fieldName="ProRegistCodeManage003.vdate",groups = {ValidateGroupEdit.class})
    private Date vdate;

    /**
     * 到期日
     */
    //@NotEmpty(fieldName="ProRegistCodeManage003.mdate",groups = {ValidateGroupEdit.class})
    private Date mdate;

   /**
     * 备注
     */
    private String remark;

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
    //@NotEmpty(fieldName="ProRegistCodeManage003.repOperateMode",groups = {ValidateGroupEdit.class})
    private String operateMode;
    /**
     * 行内识别码
     */
    @NotEmpty(fieldName="ProRegistCodeManage003.prodInsId",groups = {ValidateGroupEdit.class})
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
     * 取得 起息日
     * @return 起息日
     */
    public Date getVdate() {
        return vdate;
    }

    /**
     * 设置 起息日
     * @param vdate 起息日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    /**
     * 取得 到期日
     * @return 到期日
     */
    public Date getMdate() {
        return mdate;
    }

    /**
     * 设置 到期日
     * @param mdate 到期日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setMdate(Date mdate) {
        this.mdate = mdate;
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

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPageModel() {
		return pageModel;
	}

	public void setPageModel(String pageModel) {
		this.pageModel = pageModel;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getDeclareUuid() {
		return declareUuid;
	}

	public void setDeclareUuid(String declareUuid) {
		this.declareUuid = declareUuid;
	}
	
	public String getMdateStr(){
        return DateUtil.format(this.getMdate());
    }
	
	public String getVdateStr(){
        return DateUtil.format(this.getVdate());
    }

    public String getProdInsId() {
        return prodInsId;
    }

    public void setProdInsId(String prodInsId) {
        this.prodInsId = prodInsId;
    }
}
