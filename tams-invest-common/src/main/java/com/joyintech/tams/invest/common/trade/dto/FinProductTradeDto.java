package com.joyintech.tams.invest.common.trade.dto;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.invest.common.dto.BaseTradeProductDealDto;
import com.joyintech.tams.invest.common.dto.common.BussRepoSecDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: tams-invest-parent
 * @description: 产品交易DTO
 * @author: 强成西
 * @create: 2018-09-28 14:41
 **/
public class FinProductTradeDto extends BaseEntity {

    /**
     * 流程状态 ※新建流程中，有效，撤销
     */
    private String workflowStatus;

    /**
     * 是否汇总
     */
    private boolean needSum;

    /**
     * 子组合代码
     */
    private String actPortfolioId;

    /**
     * 资产二级类型
     */
    private String assetType2;

    /**
     * 收付方向
     */
    private String sffx;
    /**
     * 不需要执行实时头寸处理
     */
    private boolean needExecuteEtrPosition;

    /**
     * 质押券信息
     */
    private BussRepoSecDto bussRepoSec;

    /**
     * 券面总额
     */
    private BigDecimal totalFaceValue;

    /**
     * 金融产品交易信息DTO
     */
    private List<BaseTradeProductDealDto> baseTradeProductDealDtos;
    /**
     * 收支方向
     */
    private String ieFlag;
    /**
     * 是否逾期交易
     */
    private String isOverdueDeal;

    /**
     * 是否手工交收不执行头寸处理
     */
    private String isDealSettlePosition;

    /**
     * 是否处理委托完成度
     */
    private boolean needHandleFinishEntrust;

    /**
     * 是否走工作流（结算下行过来的数据没有流程中的记录）
     */
    private boolean isNotWorkflow;

//    /**
//     * 是否有报价信息（01：是，02：否,03:撤销）
//     */
//    private String isOfferFlag;
    /**
     *数据已入新头寸，Y：已入
     */
    private String isExistNew;


    public String getIsDealSettlePosition() {
        return isDealSettlePosition;
    }

    public void setIsDealSettlePosition(String isDealSettlePosition) {
        this.isDealSettlePosition = isDealSettlePosition;
    }

    /**
     * 获取 ieFlag
     *
     * @return ieFlag
     */
    public String getIeFlag() {
        return ieFlag;
    }

    /**
     * 设置 ieFlag
     *
     * @param ieFlag ieFlag
     */
    public void setIeFlag(String ieFlag) {
        this.ieFlag = ieFlag;
    }

    /**
     * 获取 workflowStatus
     *
     * @return workflowStatus
     */
    public String getWorkflowStatus() {
        return workflowStatus;
    }

    /**
     * 设置 workflowStatus
     *
     * @param workflowStatus workflowStatus
     */
    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    /**
     * 获取 baseTradeProductDealDtos
     *
     * @return baseTradeProductDealDtos
     */
    public List<BaseTradeProductDealDto> getBaseTradeProductDealDtos() {
        return baseTradeProductDealDtos;
    }

    /**
     * 设置 baseTradeProductDealDtos
     *
     * @param baseTradeProductDealDtos baseTradeProductDealDtos
     */
    public void setBaseTradeProductDealDtos(List<BaseTradeProductDealDto> baseTradeProductDealDtos) {
        this.baseTradeProductDealDtos = baseTradeProductDealDtos;
    }

    /**
     * 获取 needSum
     *
     * @return needSum
     */
    public boolean isNeedSum() {
        return needSum;
    }

    /**
     * 设置 needSum
     *
     * @param needSum needSum
     */
    public void setNeedSum(boolean needSum) {
        this.needSum = needSum;
    }

    /**
     * 获取 assetType2
     *
     * @rturn assetType2
     */
    public String getAssetType2() {
        return this.assetType2;
    }

    /**
     * 设置 assetType2
     *
     * @param assetType2 assetType2
     */
    public void setAssetType2(String assetType2) {
        this.assetType2 = assetType2;
    }

    public String getSffx() {
        return sffx;
    }

    public void setSffx(String sffx) {
        this.sffx = sffx;
    }

    /**
     * 获取 needExecuteEtrPosition
     *
     * @return needExecuteEtrPosition
     */
    public boolean isNeedExecuteEtrPosition() {
        return needExecuteEtrPosition;
    }

    /**
     * 设置 needExecuteEtrPosition
     *
     * @param needExecuteEtrPosition needExecuteEtrPosition
     */
    public void setNeedExecuteEtrPosition(boolean needExecuteEtrPosition) {
        this.needExecuteEtrPosition = needExecuteEtrPosition;
    }
    public BigDecimal getTotalFaceValue() {
        return totalFaceValue;
    }

    public void setTotalFaceValue(BigDecimal totalFaceValue) {
        this.totalFaceValue = totalFaceValue;
    }

    /**
     * 获取 actPortfolioId
     *
     * @return actPortfolioId
     */
    public String getActPortfolioId() {
        return actPortfolioId;
    }

    /**
     * 设置 actPortfolioId
     *
     * @param actPortfolioId actPortfolioId
     */
    public void setActPortfolioId(String actPortfolioId) {
        this.actPortfolioId = actPortfolioId;
    }

    /**
     * 获取 bussRepoSec
     *
     * @return bussRepoSec
     */
    public BussRepoSecDto getBussRepoSec() {
        return bussRepoSec;
    }

    /**
     * 设置 bussRepoSec
     *
     * @param bussRepoSec bussRepoSec
     */
    public void setBussRepoSec(BussRepoSecDto bussRepoSec) {
        this.bussRepoSec = bussRepoSec;
    }

	/**
	 * @return 是否逾期交易
	 */
	public String getIsOverdueDeal() {
		return isOverdueDeal;
	}

	/**
	 * @param 是否逾期交易
	 */
	public void setIsOverdueDeal(String isOverdueDeal) {
		this.isOverdueDeal = isOverdueDeal;
	}

    public boolean isNeedHandleFinishEntrust() {
        return needHandleFinishEntrust;
    }

    public void setNeedHandleFinishEntrust(boolean needHandleFinishEntrust) {
        this.needHandleFinishEntrust = needHandleFinishEntrust;
    }

    public String getIsExistNew() {
        return isExistNew;
    }

    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }
    //	public String getIsOfferFlag() {
//		return isOfferFlag;
//	}
//
//	public void setIsOfferFlag(String isOfferFlag) {
//		this.isOfferFlag = isOfferFlag;
//	}

    public boolean isNotWorkflow() {
        return isNotWorkflow;
    }

    public void setNotWorkflow(boolean notWorkflow) {
        isNotWorkflow = notWorkflow;
    }
}
