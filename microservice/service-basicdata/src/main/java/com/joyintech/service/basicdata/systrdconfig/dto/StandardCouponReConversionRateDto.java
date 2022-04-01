package com.joyintech.service.basicdata.systrdconfig.dto;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

public class StandardCouponReConversionRateDto extends BaseEntity implements ITableRow,ProcessEntity {


    private transient List<StandardCouponReConversionRateDto> selectedItem;
    private String operateType;

    private String elementId;


    private String brokerId;//券商

    private String brokerName; //券商

    public String getBrokerName() {
        if(this.getBrokerId ().equals ("00000000000000000")){
            return "所有";
        }
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    private String acctNo;//资金账户

    private String acctNoStr;//资金账户

    private String moreConvrate;//标准券再折算率

    // modify by etliu 标准券再折算率涉及头寸调整 20220308 start
    private String finprodId;//金融产品代码
    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }
    // modify by etliu 标准券再折算率涉及头寸调整 20220308 end

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


    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }


    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctNoStr() {
        if(this.getAcctNo ().equals ("00000000000000000")){
            return "所有";
        }
        return acctNoStr;
    }

    public void setAcctNoStr(String accountTypeStr) {
        this.acctNoStr = accountTypeStr;
    }

    public String getMoreConvrate() {
        return moreConvrate;
    }

    public void setMoreConvrate(String moreConvrate) {
        this.moreConvrate = moreConvrate;
    }

    public List<StandardCouponReConversionRateDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<StandardCouponReConversionRateDto> selectedItem) {
        this.selectedItem = selectedItem;
    }
}
