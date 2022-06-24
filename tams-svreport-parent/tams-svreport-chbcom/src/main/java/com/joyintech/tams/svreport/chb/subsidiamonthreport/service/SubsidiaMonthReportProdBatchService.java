package com.joyintech.tams.svreport.chb.subsidiamonthreport.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.constant.ProdConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chb.monthreport.dao.ChbMonthPrdPositionMapper;
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthPrdPosition;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.dao.ChbMonthReportSubDetailMapper;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSub;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSubTemplate;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dao.SubsidiaMonthReportRegistBatchMapper;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDetailDto;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDto;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbSubsidiaMonthReportConstants;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 理财子公司月度报表生成产品端报表数据处理类
 * 
 * @author dubaojun
 ** @version 1.0 2020年6月8日
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SubsidiaMonthReportProdBatchService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChbMonthPrdPositionMapper chbMonthPrdPositionMapper;

    @Autowired
    private ChbMonthReportSubDetailMapper chbMonthReportSubDetailMapper;

    @Autowired
    private SubsidiaMonthReportRegistBatchMapper subsidiaMonthReportRegistBatchMapper;

    @Value("${application.reportSelectOrDeleteCount}")
    public int reportSelectOrDeleteCount;

    /**
     * 生成产品端报表明细数据
     * 
     * @param report 生成参数
     */
    public void genMonthPrd(final ChbMonthReportSubDto report) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Date firstday = report.getStartdate();
        Date lastday = report.getEnddate();

        ChbMonthReportSub chbMonthReportSub = new ChbMonthReportSub();
        chbMonthReportSub.setTermid(sdf.format(firstday));

        // 报表ID
        chbMonthReportSub.setReportuuid(UUID.randomUUID().toString());

        // 报表类型
        chbMonthReportSub.setReporttype(ChbSubsidiaMonthReportConstants.REPORT_TYPE_PROD);

        // 起止日期
        chbMonthReportSub.setStartdate(firstday);
        chbMonthReportSub.setEnddate(lastday);

        // 数据日期：批处理日期
        chbMonthReportSub.setStatusDate(report.getStatusDate());

        // 报表频率：月报
        chbMonthReportSub.setReportfrequ(ChbSubsidiaMonthReportConstants.REPORT_FREQU_M);

        // 发行机构代码取值
        chbMonthReportSub.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
        chbMonthReportSub.setIssuePartyName(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_NAME));

        // 用户信息
        chbMonthReportSub.setOrgCode(CommonUtil.getOrgCode());
        chbMonthReportSub.setCreateDept(CommonUtil.getDeptCode());
        chbMonthReportSub.setCreateUser(CommonUtil.getUserName());
        chbMonthReportSub.setUpdateUser(CommonUtil.getUserName());

        // 数据状态
        chbMonthReportSub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
        chbMonthReportSub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());

        Map<String, ChbMonthReportSubDetailDto> result = new HashMap<String, ChbMonthReportSubDetailDto>();
        // 查询产品月报需要统计的项
        List<ChbMonthReportSubTemplate> listKey = subsidiaMonthReportRegistBatchMapper
                .selectSubTemplatesByReporttype(ChbSubsidiaMonthReportConstants.REPORT_TYPE_PROD);
        for (ChbMonthReportSubTemplate k : listKey) {
            ChbMonthReportSubDetailDto detail = new ChbMonthReportSubDetailDto();
            detail.setTermid(chbMonthReportSub.getTermid());
            detail.setReporttype(chbMonthReportSub.getReporttype());
            detail.setPropertycode(k.getPropertycode());
            result.put(k.getPropertycode(), detail);
        }
        if (listKey.size() != 29) {
            logger.error("理财子公司月报模板配置错误，请检查");
            return;
        }

        ChbMonthPrdPosition prod = new ChbMonthPrdPosition();
        prod.setStartdate(firstday);
        prod.setEnddate(lastday);
        List<ChbMonthPrdPosition> listPrdPosition = chbMonthPrdPositionMapper.select(prod);

        ChbMonthReportSubDetailDto line1Sub1 = result.get(ChbSubsidiaMonthReportConstants.GMLCCP); // 1.1 公募理财产品
        ChbMonthReportSubDetailDto line1Sub2 = result.get(ChbSubsidiaMonthReportConstants.SMLCCP); // 1.2 私募理财产品
        ChbMonthReportSubDetailDto line2Sub1 = result.get(ChbSubsidiaMonthReportConstants.GDSYL); // 2.1 固定收益类
        ChbMonthReportSubDetailDto line2Sub2 = result.get(ChbSubsidiaMonthReportConstants.QYL); // 2.2 权益类
        ChbMonthReportSubDetailDto line2Sub3 = result.get(ChbSubsidiaMonthReportConstants.SPJJRYSPL); // 2.3 商品及金融衍生品类
        ChbMonthReportSubDetailDto line2Sub4 = result.get(ChbSubsidiaMonthReportConstants.HHL); // 2.4 混合类
        ChbMonthReportSubDetailDto line3Sub1 = result.get(ChbSubsidiaMonthReportConstants.FBSCP); // 3.1 封闭式
        ChbMonthReportSubDetailDto line3Sub2 = result.get(ChbSubsidiaMonthReportConstants.KFSCP); // 3.2 开放式
        ChbMonthReportSubDetailDto line3Sub2Sub1 = result.get(ChbSubsidiaMonthReportConstants.XJGLLCP); // 其中：3.2.1 现金管理类产品
        ChbMonthReportSubDetailDto line3Sub2Sub2 = result.get(ChbSubsidiaMonthReportConstants.FBQZBNYSDDQKFCP); // 3.2.2 封闭期在半年以上的定期开放式产品
        ChbMonthReportSubDetailDto line4Sub1Sub1 = result.get(ChbSubsidiaMonthReportConstants.FBSSGYYN); // 4.1.1 3个月（含）以内
        ChbMonthReportSubDetailDto line4Sub1Sub2 = result.get(ChbSubsidiaMonthReportConstants.FBSSLGY); // 4.1.2 3-6个月(含)
        ChbMonthReportSubDetailDto line4Sub1Sub3 = result.get(ChbSubsidiaMonthReportConstants.FBSLSGY); // 4.1.3 6-12个月（含）
        ChbMonthReportSubDetailDto line4Sub1Sub4 = result.get(ChbSubsidiaMonthReportConstants.FBSYSN); // 4.1.4 1-3年（含）
        ChbMonthReportSubDetailDto line4Sub1Sub5 = result.get(ChbSubsidiaMonthReportConstants.FBSSNYS); // 4.1.5 3年以上
        ChbMonthReportSubDetailDto line4Sub2Sub1 = result.get(ChbSubsidiaMonthReportConstants.KFSMR); // 4.2.1 每日
        ChbMonthReportSubDetailDto line4Sub2Sub2 = result.get(ChbSubsidiaMonthReportConstants.KFSQT); // 4.2.2 7天（含）以内
        ChbMonthReportSubDetailDto line4Sub2Sub3 = result.get(ChbSubsidiaMonthReportConstants.KFSQTYGY); // 4.2.3 7天-1个月（含）
        ChbMonthReportSubDetailDto line4Sub2Sub4 = result.get(ChbSubsidiaMonthReportConstants.KFSYSY); // 4.2.4 1-3个月（含）
        ChbMonthReportSubDetailDto line4Sub2Sub5 = result.get(ChbSubsidiaMonthReportConstants.KFSSLY); // 4.2.5 3-6个月(含)
        ChbMonthReportSubDetailDto line4Sub2Sub6 = result.get(ChbSubsidiaMonthReportConstants.KFSLSY); // 4.2.6 6-12个月（含）
        ChbMonthReportSubDetailDto line4Sub2Sub7 = result.get(ChbSubsidiaMonthReportConstants.KFSYSN); // 4.2.7 1-3年（含）
        ChbMonthReportSubDetailDto line4Sub2Sub8 = result.get(ChbSubsidiaMonthReportConstants.KFSSNYS); // 4.2.8 3年以上

        ChbMonthReportSubDetailDto lineSum1Sub3 = result.get(ChbSubsidiaMonthReportConstants.SUM_MJFS); // 1.3 合 计
        ChbMonthReportSubDetailDto lineSum2Sub5 = result.get(ChbSubsidiaMonthReportConstants.SUM_TZXZ); // 2.5 合 计
        ChbMonthReportSubDetailDto lineSum3Sub3 = result.get(ChbSubsidiaMonthReportConstants.SUM_CPYZMS); // 3.3 合 计
        ChbMonthReportSubDetailDto lineSum4Sub1Sub6 = result.get(ChbSubsidiaMonthReportConstants.SUM_FBSCP); // 4.1.6 合计
        ChbMonthReportSubDetailDto lineSum4Sub2Sub9 = result.get(ChbSubsidiaMonthReportConstants.SUM_KFSCP); // 4.2.9 合计
        ChbMonthReportSubDetailDto lineSum4Sub3 = result.get(ChbSubsidiaMonthReportConstants.SUM_QX); // 4.3 合计
        // 汇率
        Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

        for (ChbMonthPrdPosition pos : listPrdPosition) {
            // 本期净募集金额
            if (pos.getCleanamtsum() == null) {
                pos.setCleanamtsum(BigDecimal.ZERO);
            }
            // 本期总募集金额
            if (pos.getDirtyamtsum() == null) {
                pos.setDirtyamtsum(BigDecimal.ZERO);
            }
            // 本期客户实现收益总额
            if (pos.getCustomeramt() == null) {
                pos.setCustomeramt(BigDecimal.ZERO);
            }
            // 期末余额
            if (pos.getEndamtsumOth() == null) {
                pos.setEndamtsumOth(BigDecimal.ZERO);
            }
            if (pos.getVdate() == null) {
                logger.error("产品信息错误：起息日为空（" + pos.getProdid() + ")");
                continue;
            }

            // DURATIONFLAG ='01'&&PROFIT_TYPE='03'，得到存续的非保本理财产品数据进行子公司月报
            if (ProdConstants.DURATIONFLAG_HOLD.equals(pos.getDurationflag())
                    && ProdConstants.PROFITTYPE_FBBFDSY.equals(pos.getProfitType())) { // 非保本
                // 将币种转换成人民币，统计其它行
                // 计算时若本位币不是人民币，则先按照产品折算成人民币保留2位小数后再进行计算其他指标数据；
                // 直接取币种来判断
                if (!ProdConstants.CCY_CNY.equals(pos.getCcy())) {// 外币
                    BigDecimal exchangeRate = ChbCcyConvertUtil.exchangeMoney(pos.getCcy(), ProdConstants.CCY_CNY,
                            chbMonthReportSub.getEnddate(), BigDecimal.ONE, rateMap, ProdConstants.EXCHANGE_SOURCE_SPJ);
                    if (exchangeRate == null) {
                        exchangeRate = BigDecimal.ONE;
                    }
                    pos.setDirtyamtsum(ArithmeticUtil.round(pos.getDirtyamtsum().multiply(exchangeRate), 2, RoundingMode.HALF_UP));
                    pos.setCleanamtsum(ArithmeticUtil.round(pos.getCleanamtsum().multiply(exchangeRate), 2, RoundingMode.HALF_UP));

                    pos.setEndamtsumOth(ArithmeticUtil.round(pos.getEndamtsumOth().multiply(exchangeRate), 2, RoundingMode.HALF_UP));
                    pos.setCustomeramt(ArithmeticUtil.round(pos.getCustomeramt().multiply(exchangeRate), 2, RoundingMode.HALF_UP));
                }
                /* 1 按募集方式划分 */
                // 第1行 1.1 公募理财产品
                if (ProdConstants.RAISEMODE_GM.equals(pos.getRaisemode())) { // 公募
                    calcMonthReportSubDetail(line1Sub1, pos, true, true, true, true, true);
                }
                // 第2行 1.2 私募理财产品
                else {
                    calcMonthReportSubDetail(line1Sub2, pos, true, true, true, true, true);
                }

                /* 2 按投资性质划分 */
                // 第4行 2.1 固定收益类
                if (ProdConstants.IVSTORIENTATION_GDSY.equals(pos.getIvstorientation())) { // 固定收益类
                    calcMonthReportSubDetail(line2Sub1, pos, true, false, false, true, true);
                }
                // 第5行 2.2 权益类
                else if (ProdConstants.IVSTORIENTATION_QYL.equals(pos.getIvstorientation())) { // 权益类
                    calcMonthReportSubDetail(line2Sub2, pos, true, false, false, true, true);
                }
                // 第6行 2.3 商品及金融衍生品类
                else if (ProdConstants.IVSTORIENTATION_SPJRP.equals(pos.getIvstorientation())) { // 商品及金融衍生品类
                    calcMonthReportSubDetail(line2Sub3, pos, true, false, false, true, true);
                }
                // 第7行 2.4 混合类
                else if (ProdConstants.IVSTORIENTATION_HHL.equals(pos.getIvstorientation())) { // 混合类
                    calcMonthReportSubDetail(line2Sub4, pos, true, false, false, true, true);
                }

                /* 3 按产品运作模式划分 */
                // 第9行 3.1 封闭式
                if (ProdConstants.OPERATEMODE_FBSJZX.equals(pos.getOperateMode())
                        || ProdConstants.OPERATEMODE_FBSFJZX.equals(pos.getOperateMode())) { // 封闭式
                    calcMonthReportSubDetail(line3Sub1, pos, true, false, false, true, true);
                }

                // 第10行 3.2 开放式
                else if (ProdConstants.OPERATEMODE_KFSJZX.equals(pos.getOperateMode())
                        || ProdConstants.OPERATEMODE_KFSFJZX.equals(pos.getOperateMode())) { // 开放式
                    calcMonthReportSubDetail(line3Sub2, pos, true, false, false, true, true);

                    // 第11行 其中：3.2.1 现金管理类产品
                    if ("01".equals(pos.getCashmanacate())) { // 现金管理类产品
                        calcMonthReportSubDetail(line3Sub2Sub1, pos, true, false, false, true, true);
                    }

                    // 第12行 3.2.2 封闭期在半年以上的定期开放式产品
                    if ("02".equals(pos.getOpenduration())) { // 封闭期在半年以上的定期开放式产品
                        calcMonthReportSubDetail(line3Sub2Sub2, pos, true, false, false, true, true);
                    }
                }

                /* 4 按期限划分 */
                // 4.1 封闭式产品
                if (ProdConstants.OPERATEMODE_FBSJZX.equals(pos.getOperateMode())
                        || ProdConstants.OPERATEMODE_FBSFJZX.equals(pos.getOperateMode())) { // 封闭式
                    // 第14行 4.1.1 3个月（含）以内
                    if (ProdConstants.TERMTYPE_U1D.equals(pos.getTermtype2())
                            || ProdConstants.TERMTYPE_U7D.equals(pos.getTermtype2())
                            || ProdConstants.TERMTYPE_U1M.equals(pos.getTermtype2())
                            || ProdConstants.TERMTYPE_U3M.equals(pos.getTermtype2())) { // 3个月以内
                        calcMonthReportSubDetail(line4Sub1Sub1, pos, true, false, false, true, true);
                    }
                    // 第15行 4.1.2 3-6个月(含)
                    else if (ProdConstants.TERMTYPE_U6M.equals(pos.getTermtype2())) { // 3-6个月(含)
                        calcMonthReportSubDetail(line4Sub1Sub2, pos, true, false, false, true, true);
                    }
                    // 第16行 4.1.3 6-12个月（含）
                    else if (ProdConstants.TERMTYPE_U12M.equals(pos.getTermtype2())) { // 6-12个月（含）
                        calcMonthReportSubDetail(line4Sub1Sub3, pos, true, false, false, true, true);
                    }
                    // 第17行 4.1.4 1-3年（含）
                    else if (ProdConstants.TERMTYPE_U3Y.equals(pos.getTermtype2())) { // 1-3年（含）
                        calcMonthReportSubDetail(line4Sub1Sub4, pos, true, false, false, true, true);
                    }
                    // 第18行 4.1.5 3年以上
                    else if (ProdConstants.TERMTYPE_B3Y.equals(pos.getTermtype2())) { // 3年以上
                        calcMonthReportSubDetail(line4Sub1Sub5, pos, true, false, false, true, true);
                    }
                }
                // 4.2 开放式产品
                else if (ProdConstants.OPERATEMODE_KFSJZX.equals(pos.getOperateMode())
                        || ProdConstants.OPERATEMODE_KFSFJZX.equals(pos.getOperateMode())) { // 开放式
                    // 第20行 4.2.1 每日
                    if (ProdConstants.TERMTYPE_U1D.equals(pos.getTermtype2())) {
                        calcMonthReportSubDetail(line4Sub2Sub1, pos, true, true, true, true, true);
                    }
                    // 第21行 4.2.2 7天（含）以内
                    else if (ProdConstants.TERMTYPE_U7D.equals(pos.getTermtype2())) {
                        calcMonthReportSubDetail(line4Sub2Sub2, pos, true, true, true, true, true);
                    }
                    // 第22行 4.2.3 7天-1个月（含）
                    else if (ProdConstants.TERMTYPE_U1M.equals(pos.getTermtype2())) {
                        calcMonthReportSubDetail(line4Sub2Sub3, pos, true, true, true, true, true);
                    }
                    // 第23行 4.2.4 1-3个月（含）
                    else if (ProdConstants.TERMTYPE_U3M.equals(pos.getTermtype2())) {
                        calcMonthReportSubDetail(line4Sub2Sub4, pos, true, true, true, true, true);
                    }
                    // 第24行 4.2.5 3-6个月(含)
                    else if (ProdConstants.TERMTYPE_U6M.equals(pos.getTermtype2())) {
                        calcMonthReportSubDetail(line4Sub2Sub5, pos, true, true, true, true, true);
                    }
                    // 第25行 4.2.6 6-12个月（含）
                    else if (ProdConstants.TERMTYPE_U12M.equals(pos.getTermtype2())) {
                        calcMonthReportSubDetail(line4Sub2Sub6, pos, true, true, true, true, true);
                    }
                    // 第26行 4.2.7 1-3年（含）
                    else if (ProdConstants.TERMTYPE_U3Y.equals(pos.getTermtype2())) {
                        calcMonthReportSubDetail(line4Sub2Sub7, pos, true, false, true, true, true);
                    }
                    // 第27行 4.2.8 3年以上
                    else if (ProdConstants.TERMTYPE_B3Y.equals(pos.getTermtype2())) {
                        calcMonthReportSubDetail(line4Sub2Sub8, pos, true, false, true, true, true);
                    }
                }

            } // 非保统计结束

        } // 产品头寸循环结束

        // 合计 1.按募集方式划分    1.3 合 计
        calcSumLine(lineSum1Sub3, true, true, true, true, true, line1Sub1, line1Sub2);
        // 合计:2.按投资性质划分 2.5 合 计
        calcSumLine(lineSum2Sub5, true, false, false, true, true, line2Sub1, line2Sub2, line2Sub3, line2Sub4);
        // 合计:3.按产品运作模式划分   3.3 合 计
        calcSumLine(lineSum3Sub3, true, false, false, true, true, line3Sub1, line3Sub2);
        // 合计:4.1 封闭式产品 4.1.6 合计
        calcSumLine(lineSum4Sub1Sub6, true, false, false, true, true, line4Sub1Sub1, line4Sub1Sub2,
                line4Sub1Sub3, line4Sub1Sub4, line4Sub1Sub5);
        // 合计:4.2 开放式产品 4.2.9 合计
        calcSumLine(lineSum4Sub2Sub9, true, false, false, true, true, line4Sub2Sub1, line4Sub2Sub2,
                line4Sub2Sub3, line4Sub2Sub4, line4Sub2Sub5, line4Sub2Sub6, line4Sub2Sub7, line4Sub2Sub8);
        // 合计:4.按期限划分   4.3 合计
        calcSumLine(lineSum4Sub3, true, false, false, true, true, lineSum4Sub1Sub6, lineSum4Sub2Sub9);

        // 计算加权价格和产品只数
        for(ChbMonthReportSubDetailDto detailDto : result.values()) {
            calcTermsAndProdCount(detailDto);
        }

        // 遍历result，插入全部的数据
        // 删除时上一次已报送数据不能删除
        if (StringUtil.isNotEmpty(report.getWbsreportuuid())) {
            subsidiaMonthReportRegistBatchMapper.deleteChbMonthReportSubDetail(chbMonthReportSub.getTermid(), chbMonthReportSub.getReporttype(),
                    report.getWbsreportuuid());
        }
        for (ChbMonthReportSubDetailDto v : result.values()) {
            v.setReportuuid(report.getReportuuid());
            chbMonthReportSubDetailMapper.insert(v);
        }

    }

    /**
     * 计算月度报表明细数据
     * 
     * @param detailDto 明细数据
     * @param pos 头寸数据
     * @param isCalcAllProd 是否计算所有产品
     * @param isCalcNotice 是否计算完全符合要求产品
     * @param isCalcUnNotice 是否计算未完全符合要求产品
     * @param isCalcFprod 是否计算从母行划转的产品
     * @param isCalcSubProd 是否计算自主发行产品
     */
    private void calcMonthReportSubDetail(ChbMonthReportSubDetailDto detailDto, ChbMonthPrdPosition pos,
            boolean isCalcAllProd, boolean isCalcNotice, boolean isCalcUnNotice, boolean isCalcFprod, boolean isCalcSubProd) {
		
        if (isCalcAllProd) {
            // 第1列 所有产品余额
            detailDto.setBalance(ArithmeticUtil.doAdd(detailDto.getBalance(), pos.getEndamtsumOth()));
            // 第2列 所有产品加权价格
            detailDto.setTerms(ArithmeticUtil.doAdd(detailDto.getTerms(), ArithmeticUtil.multiply(pos.getEndamtsumOth(), pos.getYieldE())));
        }
        // 现金管理类产品
        if ("01".equals(pos.getCashmanacate())) {
            if (isCalcNotice && "01".equals(pos.getIsNotice())) {
                // 第3列 完全符合《通知》要求余额
                detailDto.setBalanceAcc(ArithmeticUtil.doAdd(detailDto.getBalanceAcc(), pos.getEndamtsumOth()));
                // 第4列 完全符合《通知》要求加权价格
                detailDto.setTermsAcc(ArithmeticUtil.doAdd(detailDto.getTermsAcc(), ArithmeticUtil.multiply(pos.getEndamtsumOth(), pos.getYieldE())));
            }
            else if (isCalcUnNotice && "02".equals(pos.getIsNotice())) {
                // 第5列 未完全符合《通知》要求余额
                detailDto.setBalanceInc(ArithmeticUtil.doAdd(detailDto.getBalanceInc(), pos.getEndamtsumOth()));
                // 第6列 未完全符合《通知》要求加权价格
                detailDto.setTermsInc(ArithmeticUtil.doAdd(detailDto.getTermsInc(), ArithmeticUtil.multiply(pos.getEndamtsumOth(), pos.getYieldE())));
            }
        }
        if (isCalcFprod && "01".equals(pos.getIsTransfer())) {
            // 第7列 从母行划转的产品余额
            detailDto.setBalanceBan(ArithmeticUtil.doAdd(detailDto.getBalanceBan(), pos.getEndamtsumOth()));
            // 第8列 从母行划转的产品只数
            detailDto.addProdBan(pos.getProdid());
            // 第9列 从母行划转的产品加权价格
            detailDto.setTermsBan(ArithmeticUtil.doAdd(detailDto.getTermsBan(), ArithmeticUtil.multiply(pos.getEndamtsumOth(), pos.getYieldE())));
        }
        else if (isCalcSubProd && "02".equals(pos.getIsTransfer())) {
            // 第10列 自主发行产品余额
            detailDto.setBalanceSub(ArithmeticUtil.doAdd(detailDto.getBalanceSub(), pos.getEndamtsumOth()));
            // 第11列 自主发行产品只数
            detailDto.addProdSub(pos.getProdid());
            // 第12列 自主发行产品加权价格
            detailDto.setTermsSub(ArithmeticUtil.doAdd(detailDto.getTermsSub(), ArithmeticUtil.multiply(pos.getEndamtsumOth(), pos.getYieldE())));
        }
    }

    /**
     * 计算合计项
     * 
     * @param sumLine 合计行
     * @param isCalcAllProd 是否计算所有产品
     * @param isCalcNotice 是否计算完全符合要求产品
     * @param isCalcUnNotice 是否计算未完全符合要求产品
     * @param isCalcFprod 是否计算从母行划转的产品
     * @param isCalcSubProd 是否计算自主发行产品
     * @param detailDtos 明细数据
     */
    private void calcSumLine(ChbMonthReportSubDetailDto sumLine, boolean isCalcAllProd, boolean isCalcNotice, boolean isCalcUnNotice,
            boolean isCalcFprod, boolean isCalcSubProd,
            ChbMonthReportSubDetailDto...detailDtos) {
        for (ChbMonthReportSubDetailDto detailDto : detailDtos) {
            if(isCalcAllProd) {
                // 第1列 所有产品余额
                sumLine.setBalance(ArithmeticUtil.doAdd(sumLine.getBalance(), detailDto.getBalance()));
                // 第2列 所有产品加权价格
                sumLine.setTerms(ArithmeticUtil.doAdd(sumLine.getTerms(), detailDto.getTerms()));
            }
            if(isCalcNotice) {
                // 第3列 完全符合《通知》要求余额
                sumLine.setBalanceAcc(ArithmeticUtil.doAdd(sumLine.getBalanceAcc(), detailDto.getBalanceAcc()));
                // 第4列 完全符合《通知》要求加权价格
                sumLine.setTermsAcc(ArithmeticUtil.doAdd(sumLine.getTermsAcc(), detailDto.getTermsAcc()));
            }
            if(isCalcUnNotice) {
                // 第5列 未完全符合《通知》要求余额
                sumLine.setBalanceInc(ArithmeticUtil.doAdd(sumLine.getBalanceInc(), detailDto.getBalanceInc()));
                // 第6列 未完全符合《通知》要求加权价格
                sumLine.setTermsInc(ArithmeticUtil.doAdd(sumLine.getTermsInc(), detailDto.getTermsInc()));
            }
            if(isCalcFprod) {
                // 第7列 从母行划转的产品余额
                sumLine.setBalanceBan(ArithmeticUtil.doAdd(sumLine.getBalanceBan(), detailDto.getBalanceBan()));
                // 第8列 从母行划转的产品只数
                sumLine.addAllProdBan(detailDto.getProdBanSet());
                // 第9列 从母行划转的产品加权价格
                sumLine.setTermsBan(ArithmeticUtil.doAdd(sumLine.getTermsBan(), detailDto.getTermsBan()));
            }
            if(isCalcSubProd) {
                // 第10列 自主发行产品余额
                sumLine.setBalanceSub(ArithmeticUtil.doAdd(sumLine.getBalanceSub(), detailDto.getBalanceSub()));
                // 第11列 自主发行产品只数
                sumLine.addAllProdSub(detailDto.getProdSubSet());
                // 第12列 自主发行产品加权价格
                sumLine.setTermsSub(ArithmeticUtil.doAdd(sumLine.getTermsSub(), detailDto.getTermsSub()));
            }
        }
    }

    /**
     * 计算产品加权价格和产品数量
     * 
     * @param detailDto 报表明细数据
     */
    private void calcTermsAndProdCount(ChbMonthReportSubDetailDto detailDto) {
        detailDto.setTerms(ArithmeticUtil.divide(detailDto.getTerms(), detailDto.getBalance(), 2));
        detailDto.setTermsAcc(ArithmeticUtil.divide(detailDto.getTermsAcc(), detailDto.getBalanceAcc(), 2));
        detailDto.setTermsInc(ArithmeticUtil.divide(detailDto.getTermsInc(), detailDto.getBalanceInc(), 2));
        detailDto.setTermsBan(ArithmeticUtil.divide(detailDto.getTermsBan(), detailDto.getBalanceBan(), 2));
        detailDto.setTermsSub(ArithmeticUtil.divide(detailDto.getTermsSub(), detailDto.getBalanceSub(), 2));
        detailDto.setProdcountsBan(Integer.valueOf(detailDto.getProdBanSet().size()).longValue());
        detailDto.setProdcountsSub(Integer.valueOf(detailDto.getProdSubSet().size()).longValue());
    }

}
