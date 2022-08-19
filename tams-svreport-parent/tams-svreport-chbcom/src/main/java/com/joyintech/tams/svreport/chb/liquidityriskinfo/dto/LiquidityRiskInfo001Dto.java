package com.joyintech.tams.svreport.chb.liquidityriskinfo.dto;


import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.LiquidityRiskInfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.validator.LiquidityRiskInfo001DtoValidator;


/**
 * 流动性风险应对情况信息维护
 *
 * @author 周彬
 */
@ValidLogic(targetClass = LiquidityRiskInfo001DtoValidator.class,groups = {ValidateGroupEdit.class, ValidateGroupDelete.class})
@PageId("LiquidityRiskInfo002")
public class LiquidityRiskInfo001Dto extends LiquidityRiskInfo {
    // 数据状态
    private String statusValid;

    public String getOccurDateStr(){
        return getOccurDate()==null?null:DateUtil.format(getOccurDate(),"yyyy-MM-dd");
    }

    public String getReplyDateStr(){
        return  getReplyDate()==null?null:DateUtil.format(getReplyDate(),"yyyy-MM-dd");
    }

    public String getUpdateTimeStr(){
        return  DateUtil.format(getUpdateTime(),"yyyy-MM-dd HH:mm:ss");
    }


    public String getSolutionsStr(){
        return  getSolutions()+" "+SysDataUtil.getNameFromDataDict(DictCode.流动性风险应对措施, getSolutions());
    }

    public String getStatusStr(){
        return  SysDataUtil.getNameFromDataDict(DictCode.维护数据状态, getStatusValid());
    }

    public String getStatusValid() {
        return statusValid;
    }

    public void setStatusValid(String statusValid) {
        this.statusValid = statusValid;
    }

    // updateFlg 02 不展示  01 显示
    @Override
    public String getUpdateFlg() {
        if(!StringUtil.isEmpty(statusValid) && StringUtil.equals(DictCodeList.维护数据状态_删除.getValue(), statusValid)){
            return "02";
        }else{
            return "01";
        }
    }
}