package com.joyintech.service.trade.entrustmsgbymq.dto;

import java.math.BigDecimal;

public class TrdMoreConvrate {

	/**
	 * 券商
	 */
	private String brokerId;

	/**
	 * 资金账户
	 */
	private String acctNo;

	/**
	 * 再折算率
	 */
	private BigDecimal moreConvrate;
	

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

	public BigDecimal getMoreConvrate() {
		return moreConvrate;
	}

	public void setMoreConvrate(BigDecimal moreConvrate) {
		this.moreConvrate = moreConvrate;
	}
}
