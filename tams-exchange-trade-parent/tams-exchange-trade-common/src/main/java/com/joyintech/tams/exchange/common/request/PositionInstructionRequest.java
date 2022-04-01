package com.joyintech.tams.exchange.common.request;

import com.joyintech.tams.exchange.common.db.domain.Instruction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "下单头寸请求对象", description = "下单头寸请求对象")
public class PositionInstructionRequest extends Instruction {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(required = true, name = "entrustNo", value = "委托编号")
    private String entrustNo;
	
	@ApiModelProperty(required = true, name = "entrustProp", value = "委托属性")
	private String entrustProp;
    
    @ApiModelProperty(name="softSkipMode",value="软控是否继续？Y：继续",example="Y")
    private String softSkipMode;

    public String getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }

	public String getSoftSkipMode() {
		return softSkipMode;
	}

	public void setSoftSkipMode(String softSkipMode) {
		this.softSkipMode = softSkipMode;
	}

	public String getEntrustProp() {
		return entrustProp;
	}

	public void setEntrustProp(String entrustProp) {
		this.entrustProp = entrustProp;
	}
    
}
