package com.joyintech.tams.svreport.chb.prodstatusregist.dto;

import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.common.base.entity.ChbPrdStatusSub;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.TableName;

/**
 * 描述:产品状态登记表的实体类
 * @version
 * @author:  zhoubin
 * @创建时间: 2022-05-28
 */
@TableName("CHB_PRD_STATUS_SUB")
public class ProdStatusRegist001Dto extends ChbPrdStatusSub implements ITableRow {
    /**
     * 产品名称
     */
    private String prodName;

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    //数据状态
    public String getStatusStr() {
        return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态,getStatus());
    }
    //报送状态
    public String getSendStatusStr() {
        return SvrDataUtil.getNameFromDataDict(DictCode.报送状态,getSendStatus());
    }

    //报送反馈状态
    public String getSubmitFeedbackStatusStr() {
        return SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态,getSubmitFeedbackStatus());
    }

    //产品状态统计日
    public String getPrdStatusDateStr() {
        return DateUtil.format(super.getPrdStatusDate(),DateUtil.DEFAULT_OTHER_FORMAT);
    }

    //产品状态统计日
    public String getPrdTotalAmtStr() {
        return super.getPrdTotalAmt()==null?null:super.getPrdTotalAmt().toString();
    }

}