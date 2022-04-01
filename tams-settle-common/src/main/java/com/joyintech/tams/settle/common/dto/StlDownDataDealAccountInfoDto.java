package com.joyintech.tams.settle.common.dto;

/**
 * 核算信息DTO
 * 
 * @author 陈卓
 * @date 2020/7/7
 * @see StlDownDataDealAccountInfoDto
 * @since 1.0
 */
public class StlDownDataDealAccountInfoDto {
    
	// 证券账户代码
	String secAcctId;
	
	// 资金账户号
	String acctNo;
	
	// 组合代码
	String portfolioId;
	
	// 券商代码
	String brokerId;
	
	// 席位号
	String seatNumber;
	
	// 币种
	String ccy;
	
	// 登记托管机构
	String registOrg;

	// 股东代码
	String shareholdersCode;

    //上交所席位号
	String szSeatno;

	public String getShareholdersCode() {
		return shareholdersCode;
	}

	public void setShareholdersCode(String shareholdersCode) {
		this.shareholdersCode = shareholdersCode;
	}

	public String getSecAcctId() {
		return secAcctId;
	}

	public void setSecAcctId(String secAcctId) {
		this.secAcctId = secAcctId;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}

	public String getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getRegistOrg() {
		return registOrg;
	}

	public void setRegistOrg(String registOrg) {
		this.registOrg = registOrg;
	}

	public String getSzSeatno() {
		return szSeatno;
	}

	public void setSzSeatno(String szSeatno) {
		this.szSeatno = szSeatno;
	}
}
