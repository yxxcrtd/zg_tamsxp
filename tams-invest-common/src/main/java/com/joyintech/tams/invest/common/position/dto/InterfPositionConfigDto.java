package com.joyintech.tams.invest.common.position.dto;

import com.joyintech.framework.base.BaseEntity;

/**
 * 头寸接口配置DTO
 * 
 * @author yflv
 * @version 1.0 2022年2月21日
 * @see InterfPositionConfigDto
 * @since 1.0
 */
public class InterfPositionConfigDto extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 头寸类型
     */
    private String positionType;

    /**
     * 交易平台
     */
    private String tradePlat;
    
    /**
     * 业务品种
     */
    private String finprodType;
    
    /**
     * 交易方向
     */
    private String ps;
    
    /**
     * 业务动作
     */
    private String busiAct;
    
    /**
     * 实体类名
     */
    private String entity;
    
    /**
     * 接口类型
     */
    private String interfType;

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}


	public String getTradePlat() {
		return tradePlat;
	}

	public void setTradePlat(String tradePlat) {
		this.tradePlat = tradePlat;
	}

	public String getFinprodType() {
		return finprodType;
	}

	public void setFinprodType(String finprodType) {
		this.finprodType = finprodType;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getBusiAct() {
		return busiAct;
	}

	public void setBusiAct(String busiAct) {
		this.busiAct = busiAct;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getInterfType() {
		return interfType;
	}

	public void setInterfType(String interfType) {
		this.interfType = interfType;
	}

}
