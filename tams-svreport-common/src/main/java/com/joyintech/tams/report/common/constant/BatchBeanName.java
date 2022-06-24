/*
 * 文件名：BatchBeanName.java 版权：Copyright 2016_2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统 修改人：qiangchengxi 修改时间：2017年4月19日 修改内容：新建 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.common.constant;

import com.joyintech.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 批处理bean名称
 *
 * @author qiangchengxi
 * @version 1.0 2017年4月19日
 * @see BatchBeanName
 * @since 1.0
 */
public enum BatchBeanName {

//    /**
//     * 业务明细数据自动检查报送
//     */
//    业务明细数据自动检查报送("1", "equityBusiInfoAutoSendCBService", "业务明细数据自动检查报送"),
//
//    /**
//     * 分类总量数据自动检查报送
//     */
//    分类总量数据自动检查报送("2", "classifyTotalInfoAutoSendCBService", "分类总量数据自动检查报送"),
    投资者身份报送("3001","equityInfoAutoSendCBService","EquityInfoAutoSendCBMainService","投资者身份报送"),
    投资者持有报送("3002","investorsHoldInfoRegistAutoSendCBService","InvestorsHoldInfoRegistAutoSendCBMainService","投资者持有报送"),
    投资者报送前检查("3003","checkReportAutoSendCheckService","CheckReportAutoSendCheckMainService","投资者报送前检查"),
    投资者报送数据生成("3004","generateInvestorsHoldInfoAutoSendCBService","GenerateInvestorsHoldInfoAutoSendCBMainService","投资这报送数据生成"),
    投资者报送数据归档("3005","reportDataBackupService","ReportDataBackupMainService","投资者报送数据归档"),
    债券评级数据接入("3002","svrSecRatingInterfTaskService","SecRatingImpMainService","债券评级数据接入"),
    节假日数据接入("3007","svrHolidayInterfTaskService","HolidayImpMainService","节假日数据接入"),
    汇率信息数据接入("3008","svrMstExchangeQuoteInterfTaskService","MstExchangeQuoteImpMainService","汇率信息数据接入"),
    市场机构数据接入("3009","svrMarketOrgInterTaskService","MarketOrgInterImpMainService","市场机构数据接入"),
    交易对手接口数据接入("3010","svrThirdPartyInterfTaskService","ThirdPartyInterfMainService","交易对手接口数据接入"),
    产品基本信息接口数据接入("3011","svrProdInfoInterfTaskService","SvrProdInfoInterfMainService","产品基本信息接口数据接入"),
    产品开放计划接口数据接入("3012","svrProdPlanInterfTaskService","ProdPlanImpMainService","产品开放计划接口数据接入"),
    产品交易信息接入接入("3013","svrProdDealInterfTaskService","ProdDealImpMainService","产品交易信息接入接入"),
    产品费用交易接口数据接入("3014","svrProdFeeDealInterfTaskService","SvrProdFeeDealInterfMainService","产品费用交易接口数据接入"),
    产品收益率净值接口数据接入("3015","svrProdIncomeInterfTaskService","ProdIncomeImpMainService","产品收益率净值接口数据接入"),
    资产要素信息接口数据接入("3016","svrAssetInfoInterfTaskService","AssetInfoImpMainService","资产要素信息接口数据接入"),
    投资交易信息接口数据接入("3017","svrAssetDealInterfTaskService","InvestImpMainService","投资交易信息接口数据接入"),
    资管计划底层资产持仓接口数据接入("3018","svrBotAssetPosInterfTaskService","ChbBotAstPosImpMainService","资管计划底层资产持仓接口数据接入"),
    资产基础指标数据接入("3019","svrAssetIndexInterfTaskService","AssetIndexImpMainService","资产基础指标数据接入"),
    科目余额数据接入("3020","svrSubjectBalanceInterfTaskService","SubjectBalanceImpMainService","科目余额数据接入"),
    关联机构信息接入("3021","svrRelOrgInfoInterfTaskService","","关联机构信息接入"),
    基础日历信息数据接入("3022","svrCalendarInterfTaskService","","基础日历信息数据接入"),
    日历管理信息数据接入("3023","svrCalendarMgrInterfTaskService","","日历管理信息数据接入"),
    产品分红登记接入("3024","svrProdBonusInterfTaskService","","产品分红登记接入"),
    产品费率数据接入("3025","svrProdFeePriceInterfTaskService","","产品费率数据接入"),
    产品资产调整数据接入("3026","svrProdAdjustInterfTaskService","","产品资产调整数据接入"),
    产品资产种类投资比例调整数据接入("3027","svrProdInvestRateInterfTaskService","","产品资产种类投资比例调整数据接入"),
    产品基础指标数据接入("3028","svrProdIndexInterfTaskService","","产品基础指标数据接入"),
    产品头寸批处理("3029","svrProdPositionTaskService","ProdPosition001Service","产品头寸批处理"),
    资产头寸批处理("3030","svrAssetPositionTaskService","AssetPositionService","资产头寸批处理"),
    产品发行申报登记报送("3031","svrProdIssueAndDeclareRegTaskService","ProdIssueAndDecalreRegBatchService","产品发行申报登记报送"),
    产品存续期交易登记报送("3032","svrProdDurationRegTaskService","ProdDurationRegRegistBatchService","产品存续期交易登记报送"),
    资产要素登记报送("3033","svrAssetInfoRegTaskService","AssetInfoRegistBatchService","资产要素登记报送"),
    投资交易登记报送("3034","svrAssetDealRegTaskService","InvestTradeRegistBatchService","投资交易登记报送"),
    产品募集总量登记报送("3035","svrProdRaiseRegTaskService","ProdRaiseTotalRegistBatchService","产品募集总量登记报送"),
    产品终止登记报送("3036","svrProdEndRegTaskService","ProdTerminationRegistBatchService","产品终止登记报送"),
    底层持仓信息登记报送("3037","svrBotAssetPosRegTaskService","ChbBotAstPosRegistBatchService","底层持仓信息登记报送"),
    资产估值信息登记报送("3038","svrAssetValRegTaskService","ChbAstValRegistBatchService","资产估值信息登记报送"),
    银行月度统计表生成("3039","svrMonthReportTaskService","MonthReportRegistBatchService","银行月度统计表生成"),
    人行报表生成("3040","svrPbcReportTaskService","PbcMonthReportRegistBatchService","人行报表生成"),
    数据检查("3041","svrDataCheckCBService","ChbDataCheckRegistBatchService","数据检查"),
	科目数据接入("3042","svrBokSubjectInterfTaskService","","科目数据接入"),
    产品费用头寸批处理("3043","svrProdFeePositionTaskService","ProdFeePositionService","产品费用头寸批处理"),
    人行报表单产品批处理("3044","svrPbcSinglePrdReportTaskService","","人行报表单产品批处理"),
	人行单产品dat文件生成批处理("3045","svrPbcDatFileTaskService","","人行单产品dat文件生成批处理"),
	监管报送数据归档批处理("3046","svrDataArchiveTaskService","SvrDataArchiveMainService","监管报送数据归档批处理"),
	产品申报信息接口数据接入("3047","svrProdDeclareInfoInterfTaskService","svrProdDeclareInfoImpMainService","产品申报信息接口数据接入"),
	每日日终接取收益信息接口数据接入("3048","svrProdEndDayInterfTaskService","prodEndDayInterMainService","每日日终接取收益信息接口数据接入"),
	周期型产品余额数据接入("3049","svrCprodBalanceInterfTaskService","svrCprodBalanceImpMainService","周期型产品余额数据接入"),
	子资管计划资产指标信息("3050","svrSubInvestmentPlanAssetIndicatorInfoTaskService","subInvestImpMainService","子资管计划资产指标信息"),
    贷款明细信息接口数据接入("3051","svrLoanDetailInfoInterfTaskService","loanDetailInfoImpMainService","贷款明细信息接口数据接入"),

    新人行单产品报表生成("3055","svrNewPbcSinglePrdReportTaskService","svrNewPbcSinglePrdReportTaskService","新人行单产品报表生成"),
    新人行dat报表生成("3056","svrNewPbcDatFileTaskService","svrNewPbcDatFileTaskService","新人行dat报表生成"),
    新人行月报Dat文件生成批处理("3057","svrNewPbcReportDatFileTaskService","PbcNewReportDatFileCBMainService","新人行月报Dat文件生成批处理"),

    贷款明细原始合同信息接入("3081","svrLoanDetailContractInterfTaskService","loanDetailInfoFileImpService","贷款明细原始合同信息接入"),
	资产基础指标不穿透数据接入("3082","svrAssetIndexNoBtmInterfTaskService","assetIndexNoBtmImpMainService","资产基础指标不穿透数据接入"),
    投资者明细信息登记手工处理("3067", "equityBusiInfoRegistAutoManualCBService", "equityBusiInfoRegistAutoManualCBMainService","投资者明细信息登记手工处理"),
	投资者明细信息数据检查批处理("3068", "eqityBusiInfoRegistManualCheckService","equityBusiInfoRegistManualCheckMainService", "投资者明细信息数据检查批处理"),

    // 监管客户组别区域报送数据接入 add by qufangxu at 2021-02-24
    监管客户组别区域报送数据接入("3003","svrSaleCustSvrreportInterfTastService","saleCustSvrrepportImpMainService","监管客户组别区域报送数据接入"),
    销售结算资金交易信息表报送数据接入("3053","svrChbSalerStlDealInfoInterfTastService","ChbSalerStlDealInfoImpMainService","销售结算资金交易信息表报送数据接入"),
    理财子公司月度统计表生成("3055","svrSubsidiaMonthReportTaskService","SubsidiaMonthReportRegistBatchService","理财子公司月度统计表生成"),

    /**
     * 业务明细数据自动检查报送
     */
	投资者明细报送("3071", "equityBusiInfoAutoSendCBService","equityBusiInfoAutoSendCBMainService", "投资者明细报送"),
	投资者明细信息数据归档批处理("3069", "equityBusiInfoDataBackupService","equityBusiInfoDataBackupMainService", "投资者明细信息数据归档批处理"),
    投资者份额检查批处理("3070", "equityShareCheckService","equityShareCheckMainService", "投资者份额检查批处理"),
    投资者持有信息登记手工处理("3072", "investorsHoldInfoRegistAutoManualCBService","investorsHoldInfoRegistAutoManualCBMainService", "投资者持有信息登记手工处理"),
	投资者身份信息登记手工处理("3073", "equityInfoRegistAutoManualCBService","equityInfoRegistAutoManualCBMainService", "投资者身份信息登记手工处理"),
	投资者明细信息登记导出("3075","equityBusiInfoExportService","equityBusiInfoExportMainService","投资者明细信息登记导出"),
	产品运作模式更新("3074","svrUpdateOperateModeTaskService","UpdateOperateModeMainService","产品运作模式更新"),
	产品费用交易头寸接口数据接入("3076","svrChbPrdFeeBasisInfoTaskService","chbPrdFeeBasisImpMainService","产品费用交易头寸接口数据接入"),
	产品分红交易接口数据接入("3077","svrProdDiviendBasisInfoTaskService","chbPrdDividendBasisImpMainService","产品分红交易接口数据接入"),
    产品利率基本信息接口数据接入("4100","svrProdRateInfoInterfTaskService","SvrProdRateInfoImpMainService","产品利率基本信息接口数据接入"),
    资产利率基本信息接口数据接入("4101","svrAssetRateInfoInterfTaskService","SvrAssetRateInfoImpMainService","资产利率基本信息接口数据接入"),
	投资者身份信息登记导出("3074","equityInfoExportService","equityInfoExportMainService","投资者身份信息登记导出"),
	投资者持有信息登记导出("3076","investorsHoldInfoExportService","investorsHoldInfoExportMainService","投资者持有信息登记导出"),
    // TODO
    理财产品转型情况月度统计表生成("3042","investorsHoldInfoExportService","investorsHoldInfoExportMainService","理财产品转型情况月度统计表生成"),
    总部或分支机构信息接入("3091","svrSaleInstInterfTaskService","SvrSaleInstImpMainService","总部或分支机构信息接入"),
    销售人员身份信息接入("3092","svrSaleIderInterfTaskService","SvrSaleIderImpMainService","销售人员身份信息接入"),
    销售人员奖惩投诉记录信息接入("3093","svrSaleIRewPenaInterfTaskService","SvrSaleRewPenaImpMainService","销售人员奖惩投诉记录信息接入"),

            ;
	/**
     * taskId
     */
    private String taskId;

    /**
     * beanName
     */
    private String beanName;

    /**
     * 批处理名称
     */
    private String batchName;

    /**
     * 批处理实现类beanname
     */
    private String batchRealBean;

    /**
     * @param beanName 批的beanName
     * @param batchRealBean 批处理实现类beanname
     * @param batchName 批的名称
     */
    BatchBeanName(String taskId, String beanName, String batchRealBean, String batchName) {
        this.taskId = taskId;
        this.beanName = beanName;
        this.batchRealBean = batchRealBean;
        this.batchName = batchName;
    }

    public String getBeanName() {
        return beanName;
    }

    /**
     * 获取 taskId
     * @return taskId.
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 获取 batchName
     * @return batchName.
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * 功能:获取批处理实际bean
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月9日
     * @return 
     * String
     */
    public String getBatchRealBean() {
        return batchRealBean;
    }

    /**
     * 获取所有自动检查报送批处理列表
     *
     * @return 所有自动检查报送批处理列表
     * @see
     * @since 1.0
     */
    public static List<BatchBeanName> getAutoSendCBBatchList() {
        List<BatchBeanName> autoSendCBBatchList = new ArrayList<BatchBeanName>();
        autoSendCBBatchList.add(BatchBeanName.投资者身份报送);
        autoSendCBBatchList.add(BatchBeanName.投资者持有报送);
        autoSendCBBatchList.add(BatchBeanName.投资者明细报送);
        return autoSendCBBatchList;
    }

    /**
     * 根据taskId获取批处理名称
     *
     * @param taskId taskId
     * @return 批处理名称
     * @see
     * @since 1.0
     */
    public static String getBatchNameByTaskId(String taskId) {
        for (BatchBeanName batchBeanName : BatchBeanName.values()) {
            if (StringUtil.equals(taskId, batchBeanName.getTaskId())) {
                return batchBeanName.getBatchName();
            }
        }

        return StringUtil.EMPTY;
    }
}
