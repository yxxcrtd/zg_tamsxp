/*
 * 文件名：MstYieldCurveInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年8月8日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dto;

import com.joyintech.background.component.common.pack.common.util.DateUtil;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.validator.ChbExchangeQuotationInfoValidator;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

/**
 * 汇率行情dto
 * 〈功能详细描述〉
 *
 * @author wangzhao
 * @version 1.0 2018年8月8日
 * @see ChbExchangeQuotationInfoDto
 * @since 1.0
 */
@PageId("ChbExchangeQuotationInfo002")
@ValidLogic(targetClass = ChbExchangeQuotationInfoValidator.class, groups = {ValidateGroupEdit.class})
public class ChbExchangeQuotationInfoDto extends ChbExchargeQuote implements ProcessEntity {
    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 5143537191208289654L;




    /**
     * 选中的数据
     */
    private transient List<ChbExchangeQuotationInfoDto> selectedItem;

    /**
     * 操作类型
     */
    private String operateType;

    private String submitType;

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private String pageModel;


    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private String elementId;

    /**
     * 有效状态
     */
    private String statusValid;

    private String orgData;

    private String datauuid;

    // updateFlg 02 不展示  01 显示
    public String getUpdateFlg() {
        if(!StringUtil.isEmpty(statusValid) && StringUtil.equals(DictCodeList.维护数据状态_删除.getValue(), statusValid)){
            return "02";
        }else{
            return "01";
        }
    }

    public String getStatusValidStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,
                getStatusValid());
    }

    public String getStatusValid() {
        return statusValid;
    }

    public void setStatusValid(String statusValid) {
        this.statusValid = statusValid;
    }

    public String getUpdateTimeStr() {
        return DateUtil.formatDateTime(super.getUpdateTime());
    }



    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    /**
     * 获取 selectedItem
     *
     * @return selectedItem.
     */
    public List<ChbExchangeQuotationInfoDto> getSelectedItem() {
        return selectedItem;
    }

    /**
     * 设置 selectedItem
     *
     * @param selectedItem selectedItem
     */
    public void setSelectedItem(List<ChbExchangeQuotationInfoDto> selectedItem) {
        this.selectedItem = selectedItem;
    }


    /**
     * 获取 operateType
     *
     * @return operateType.
     */
    public String getOperateType() {
        return operateType;
    }


    /**
     * 设置 operateType
     *
     * @param operateType operateType
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }


    /**
     * 获取 pageModel
     *
     * @return pageModel.
     */
    public String getPageModel() {
        return pageModel;
    }


    /**
     * 设置 pageModel
     *
     * @param pageModel pageModel
     */
    public void setPageModel(String pageModel) {
        this.pageModel = pageModel;
    }


    /**
     * 获取 elementId
     *
     * @return elementId.
     */
    public String getElementId() {
        return elementId;
    }


    /**
     * 设置 elementId
     *
     * @param elementId elementId
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getOrgData() {
        return orgData;
    }

    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }

    public String getTradeDateStr(){
        return null !=getTradeDate()? com.joyintech.framework.util.DateUtil.format(getTradeDate()):null;
    }

    public String getDatauuid() {
        return datauuid;
    }

    public void setDatauuid(String datauuid) {
        this.datauuid = datauuid;
    }
}
