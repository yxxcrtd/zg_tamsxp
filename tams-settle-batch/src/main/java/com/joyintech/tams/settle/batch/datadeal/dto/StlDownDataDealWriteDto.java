package com.joyintech.tams.settle.batch.datadeal.dto;

import java.util.ArrayList;
import java.util.List;

import com.joyintech.batch.batchbase.baseclass.entity.NeedToWriteEntity;
import com.joyintech.tams.common.base.entity.CommissionCaclProcess;
import com.joyintech.tams.common.base.entity.FinProductQuto;
import com.joyintech.tams.common.base.entity.MstFundBonusNotice;
import com.joyintech.tams.common.base.entity.ShareBonusReg;
import com.joyintech.tams.settle.batch.datadeal.dto.step2.StlDownDataDealWriteBaseDto;

/**
 * 数据清算批处理主处理参数DTO
 * 
 * @author 陈卓
 * @date 2020/7/7
 * @see StlDownDataDealWriteDto
 * @since 1.0
 */
public class StlDownDataDealWriteDto extends NeedToWriteEntity {
	
	// StlDownDataDealWriteDtoList
	List<StlDownDataDealWriteDto> stlDownDataDealWriteDtoList;
	
	// 成交数据List
	List<StlDownDataDealWriteBaseDto> stlDownDataDealWriteBaseDtoList;
	
	// 佣金计算过程List
	List<CommissionCaclProcess> commissionCaclProcessList;
	
	// 交易所品种额度List
	List<FinProductQuto> finProductQutoList;

	// 送股权益登记List
	List<ShareBonusReg> shareBonusRegList;

	//基金分红公告List
	List<MstFundBonusNotice> mstFundBonusNoticeList;
	
    public StlDownDataDealWriteDto() {
    	stlDownDataDealWriteDtoList =  new ArrayList<StlDownDataDealWriteDto>();
    	stlDownDataDealWriteBaseDtoList = new ArrayList<StlDownDataDealWriteBaseDto>();
    	commissionCaclProcessList = new ArrayList<CommissionCaclProcess>();
    	finProductQutoList = new ArrayList<FinProductQuto>();
		shareBonusRegList=new ArrayList<ShareBonusReg>();
		mstFundBonusNoticeList=new ArrayList<MstFundBonusNotice>();
    }

	public List<StlDownDataDealWriteBaseDto> getStlDownDataDealWriteBaseDtoList() {
		return stlDownDataDealWriteBaseDtoList;
	}

	public void setStlDownDataDealWriteBaseDtoList(List<StlDownDataDealWriteBaseDto> stlDownDataDealWriteBaseDtoList) {
		this.stlDownDataDealWriteBaseDtoList = stlDownDataDealWriteBaseDtoList;
	}

	public List<CommissionCaclProcess> getCommissionCaclProcessList() {
		return commissionCaclProcessList;
	}

	public void setCommissionCaclProcessList(List<CommissionCaclProcess> commissionCaclProcessList) {
		this.commissionCaclProcessList = commissionCaclProcessList;
	}

	public List<StlDownDataDealWriteDto> getStlDownDataDealWriteDtoList() {
		return stlDownDataDealWriteDtoList;
	}

	public void setStlDownDataDealWriteDtoList(List<StlDownDataDealWriteDto> stlDownDataDealWriteDtoList) {
		this.stlDownDataDealWriteDtoList = stlDownDataDealWriteDtoList;
	}

	public List<FinProductQuto> getFinProductQutoList() {
		return finProductQutoList;
	}

	public void setFinProductQutoList(List<FinProductQuto> finProductQutoList) {
		this.finProductQutoList = finProductQutoList;
	}

	public List<ShareBonusReg> getShareBonusRegList() {
		return shareBonusRegList;
	}

	public void setShareBonusRegList(List<ShareBonusReg> shareBonusRegList) {
		this.shareBonusRegList = shareBonusRegList;
	}

	public List<MstFundBonusNotice> getMstFundBonusNoticeList() {
		return mstFundBonusNoticeList;
	}

	public void setMstFundBonusNoticeList(List<MstFundBonusNotice> mstFundBonusNoticeList) {
		this.mstFundBonusNoticeList = mstFundBonusNoticeList;
	}
}
