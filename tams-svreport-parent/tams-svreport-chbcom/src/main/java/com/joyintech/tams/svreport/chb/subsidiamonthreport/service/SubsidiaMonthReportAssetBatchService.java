package com.joyintech.tams.svreport.chb.subsidiamonthreport.service;

import java.math.BigDecimal;
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
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthAssetPosition;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.dao.ChbMonthReportSubDetailMapper;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSub;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSubTemplate;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dao.SubsidiaMonthReportRegistBatchMapper;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDetailDto;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDto;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbSubsidiaMonthReportConstants;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 理财子公司月度报表生成资产端报表数据处理类
 * 
 * @author dubaojun
 ** @version 1.0 2020年6月8日
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SubsidiaMonthReportAssetBatchService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChbMonthReportSubDetailMapper chbMonthReportSubDetailMapper;

    @Autowired
    private SubsidiaMonthReportRegistBatchMapper subsidiaMonthReportRegistBatchMapper;

    @Value("${application.reportSelectOrDeleteCount}")
    public int reportSelectOrDeleteCount;

    /**
     * 生成资产端端报表明细数据
     * 
     * @param report 生成参数
     */
    public void genMonthAsset(final ChbMonthReportSubDto report) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Date firstday = report.getStartdate();
        Date lastday = report.getEnddate();

        ChbMonthReportSub chbMonthReportSub = new ChbMonthReportSub();
        chbMonthReportSub.setTermid(sdf.format(firstday));

        // 报表ID
        chbMonthReportSub.setReportuuid(UUID.randomUUID().toString());

        // 报表类型
        chbMonthReportSub.setReporttype(ChbSubsidiaMonthReportConstants.REPORT_TYPE_ASSET);

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
                .selectSubTemplatesByReporttype(ChbSubsidiaMonthReportConstants.REPORT_TYPE_ASSET);
        for (ChbMonthReportSubTemplate k : listKey) {
            ChbMonthReportSubDetailDto detail = new ChbMonthReportSubDetailDto();
            detail.setTermid(chbMonthReportSub.getTermid());
            detail.setReporttype(chbMonthReportSub.getReporttype());
            detail.setPropertycode(k.getPropertycode());
            result.put(k.getPropertycode(), detail);
        }
        if (listKey.size() != 34) {
            logger.error("理财子公司月报资产端模板配置错误，请检查");
            return;
        }

        List<ChbMonthAssetPosition> listAssetPosition = subsidiaMonthReportRegistBatchMapper
                .selectAssetPositionsByMonthDate(firstday, lastday);

        // 1 标准化债权类资产
        ChbMonthReportSubDetailDto AMT1_1 = result.get(ChbSubsidiaMonthReportConstants.AMT1_1); // 1.1 合 计
        ChbMonthReportSubDetailDto AMT1_1_a = result.get(ChbSubsidiaMonthReportConstants.AMT1_1_a); // 其中 1.1.a 信用债
        ChbMonthReportSubDetailDto AMT1_1_a_a = result.get(ChbSubsidiaMonthReportConstants.AMT1_1_a_a); // 其中 1.1.a.a PPN等低流动性债券
        ChbMonthReportSubDetailDto AMT1_1_b = result.get(ChbSubsidiaMonthReportConstants.AMT1_1_b); // 1.1.b 利率债
        ChbMonthReportSubDetailDto AMT1_1_c = result.get(ChbSubsidiaMonthReportConstants.AMT1_1_c); // 1.1.c 固定收益类公募基金
        ChbMonthReportSubDetailDto AMT1_2 = result.get(ChbSubsidiaMonthReportConstants.AMT1_2); // 1.2 加权剩余期限
        // 2 非标准化债权类资产
        ChbMonthReportSubDetailDto AMT2_1 = result.get(ChbSubsidiaMonthReportConstants.AMT2_1); // 2.1 通过信托贷款和信托产品
        ChbMonthReportSubDetailDto AMT2_2 = result.get(ChbSubsidiaMonthReportConstants.AMT2_2); // 2.2 通过证券期货经营机构资管产品
        ChbMonthReportSubDetailDto AMT2_3 = result.get(ChbSubsidiaMonthReportConstants.AMT2_3); // 2.3 通过保险资管产品
        ChbMonthReportSubDetailDto AMT2_4 = result.get(ChbSubsidiaMonthReportConstants.AMT2_4); // 2.4 通过委托贷款
        ChbMonthReportSubDetailDto AMT2_5 = result.get(ChbSubsidiaMonthReportConstants.AMT2_5); // 2.5 通过债权融资类产品
        ChbMonthReportSubDetailDto AMT2_6 = result.get(ChbSubsidiaMonthReportConstants.AMT2_6); // 2.6 通过其他形式
        ChbMonthReportSubDetailDto AMT2_7 = result.get(ChbSubsidiaMonthReportConstants.AMT2_7); // 2.7 直接投资
        ChbMonthReportSubDetailDto AMT2_8 = result.get(ChbSubsidiaMonthReportConstants.AMT2_8); // 2.8 合 计
        ChbMonthReportSubDetailDto AMT2_9 = result.get(ChbSubsidiaMonthReportConstants.AMT2_9); // 2.9 加权剩余期限
        // 3 股票类资产
        ChbMonthReportSubDetailDto AMT3_1 = result.get(ChbSubsidiaMonthReportConstants.AMT3_1); // 3.1 合 计
        // 4 未上市企业股权
        ChbMonthReportSubDetailDto AMT4_1 = result.get(ChbSubsidiaMonthReportConstants.AMT4_1); // 4.1 合 计
        ChbMonthReportSubDetailDto AMT4_1_a = result.get(ChbSubsidiaMonthReportConstants.AMT4_1_a); // 其中 4.1.a 政府出资产业投资基金
        ChbMonthReportSubDetailDto AMT4_1_b = result.get(ChbSubsidiaMonthReportConstants.AMT4_1_b); // 4.1.b 创业投资基金
        ChbMonthReportSubDetailDto AMT4_2 = result.get(ChbSubsidiaMonthReportConstants.AMT4_2); // 4.2 加权剩余期限
        // 5 银行资本补充工具
        ChbMonthReportSubDetailDto AMT5_1 = result.get(ChbSubsidiaMonthReportConstants.AMT5_1); // 5.1 合 计
        ChbMonthReportSubDetailDto AMT5_1_a = result.get(ChbSubsidiaMonthReportConstants.AMT5_1_a); // 其中 5.1.a 优先股
        ChbMonthReportSubDetailDto AMT5_1_b = result.get(ChbSubsidiaMonthReportConstants.AMT5_1_b); // 5.1.b 永续债（债性）
        ChbMonthReportSubDetailDto AMT5_1_c = result.get(ChbSubsidiaMonthReportConstants.AMT5_1_c); // 5.1.c 永续债（股性）
        ChbMonthReportSubDetailDto AMT5_1_d = result.get(ChbSubsidiaMonthReportConstants.AMT5_1_d); // 5.1.d 二级资本债
        ChbMonthReportSubDetailDto AMT5_2 = result.get(ChbSubsidiaMonthReportConstants.AMT5_2); // 5.2 加权剩余期限
        // 6 其他资产
        ChbMonthReportSubDetailDto AMT6_1 = result.get(ChbSubsidiaMonthReportConstants.AMT6_1); // 6.1 合 计
        ChbMonthReportSubDetailDto AMT6_1_a = result.get(ChbSubsidiaMonthReportConstants.AMT6_1_a); // 其中 6.1.a 拆放同业及买入返售
        ChbMonthReportSubDetailDto AMT6_1_b = result.get(ChbSubsidiaMonthReportConstants.AMT6_1_b); // 6.1.b 现金及银行存款
        ChbMonthReportSubDetailDto AMT6_1_c = result.get(ChbSubsidiaMonthReportConstants.AMT6_1_c); // 6.1.c 其他公募基金
        ChbMonthReportSubDetailDto AMT6_1_d = result.get(ChbSubsidiaMonthReportConstants.AMT6_1_d); // 6.1.d 商品及金融衍生产品
        ChbMonthReportSubDetailDto AMT6_1_e = result.get(ChbSubsidiaMonthReportConstants.AMT6_1_e); // 6.1.e QDII
        ChbMonthReportSubDetailDto AMT6_2 = result.get(ChbSubsidiaMonthReportConstants.AMT6_2); // 6.2 加权剩余期限
        // 7 合计
        ChbMonthReportSubDetailDto AMT7 = result.get(ChbSubsidiaMonthReportConstants.AMT7); // 7 合计

        for (ChbMonthAssetPosition pos : listAssetPosition) {
            if (pos.getAssetvalueB() == null) {
                pos.setAssetvalueB(BigDecimal.ZERO);
            }

            // 1 标准化债权类资产
            if ("F01".equals(pos.getSunassettype())) {
                // 1.1 合 计
                calcMonthReportSubDetail(AMT1_1, pos, true, false);
                // 其中 1.1.a 信用债
                if ("F0101".equals(pos.getSunassettype2()) || "F0102".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT1_1_a, pos, true, false);
                    // 其中 1.1.a.a PPN等低流动性债券 非公开发行：02 公开发行：01
                    //if ("F0101".equals(pos.getSunassettype2()) && "02".equals(pos.getIssueMode())) {
                    if ("F0101".equals(pos.getSunassettype2())) {//不判定是否公开发行
                        calcMonthReportSubDetail(AMT1_1_a_a, pos, true, false);
                    }
                }
                // 1.1.b 利率债
                else if ("F0103".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT1_1_b, pos, true, false);
                }
                // 1.1.c 固定收益类公募基金
                else if ("F0104".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT1_1_c, pos, true, false);
                }
                // 1.2 加权剩余期限
                calcMonthReportSubDetail(AMT1_2, pos, false, true);
            }
            // 2 非标准化债权类资产
            else if ("F02".equals(pos.getSunassettype())) {
                // 2.1 通过信托贷款和信托产品
                if ("F0201".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT2_1, pos, true, false);
                }
                // 2.2 通过证券期货经营机构资管产品
                else if ("F0202".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT2_2, pos, true, false);
                }
                // 2.3 通过保险资管产品
                else if ("F0203".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT2_3, pos, true, false);
                }
                // 2.4 通过委托贷款
                else if ("F0204".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT2_4, pos, true, false);
                }
                // 2.5 通过债权融资类产品
                else if ("F0205".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT2_5, pos, true, false);
                }
                // 2.6 通过其他形式
                else if ("F0206".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT2_6, pos, true, false);
                }
                // 2.7 直接投资
                else if ("F0207".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT2_7, pos, true, false);
                }
                // 2.8 合 计
                calcMonthReportSubDetail(AMT2_8, pos, true, false);
                // 2.9 加权剩余期限
                calcMonthReportSubDetail(AMT2_9, pos, false, true);
            }
            // 3 股票类资产
            else if ("F03".equals(pos.getSunassettype())) {
                // 3.1 合 计
                calcMonthReportSubDetail(AMT3_1, pos, true, false);
            }
            // 4 未上市企业股权
            else if ("F04".equals(pos.getSunassettype())) {
                // 4.1 合 计
                calcMonthReportSubDetail(AMT4_1, pos, true, false);
                // 其中 4.1.a 政府出资产业投资基金
                if ("F0401".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT4_1_a, pos, true, false);
                }
                // 4.1.b 创业投资基金
                else if ("F0402".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT4_1_b, pos, true, false);
                }
                // 4.2 加权剩余期限
                calcMonthReportSubDetail(AMT4_2, pos, false, true);
            }
            // 5 银行资本补充工具
            else if ("F05".equals(pos.getSunassettype())) {
                // 5.1 合 计
                calcMonthReportSubDetail(AMT5_1, pos, true, false);
                // 其中 5.1.a 优先股
                if ("F0501".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT5_1_a, pos, true, false);
                }
                // 5.1.b 永续债（债性）
                else if ("F0502".equals(pos.getSunassettype2()) || "F0506".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT5_1_b, pos, true, false);
                }
                // 5.1.c 永续债（股性）
                else if ("F0503".equals(pos.getSunassettype2()) || "F0505".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT5_1_c, pos, true, false);
                }
                // 5.1.d 二级资本债
                else if ("F0504".equals(pos.getSunassettype2()) || "F0505".equals(pos.getSunassettype2()) || "F0506".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT5_1_d, pos, true, false);
                }
                // 5.2 加权剩余期限
                calcMonthReportSubDetail(AMT5_2, pos, false, true);
            }
            // 6 其他资产
            else if ("F06".equals(pos.getSunassettype())) {
                // 6.1 合 计
                calcMonthReportSubDetail(AMT6_1, pos, true, false);
                // 其中 6.1.a 拆放同业及买入返售
                if ("F0601".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT6_1_a, pos, true, false);
                }
                // 6.1.b 现金及银行存款
                else if ("F0602".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT6_1_b, pos, true, false);
                }
                // 6.1.c 其他公募基金
                else if ("F0603".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT6_1_c, pos, true, false);
                }
                // 6.1.d 商品及金融衍生产品
                else if ("F0604".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT6_1_d, pos, true, false);
                }
                // 6.1.e QDII
                else if ("F0605".equals(pos.getSunassettype2())) {
                    calcMonthReportSubDetail(AMT6_1_e, pos, true, false);
                }
                // 6.2 加权剩余期限
                calcMonthReportSubDetail(AMT6_2, pos, false, true);
            }
        } // 产品头寸循环结束

        // 7 合计
        calcSumLine(AMT7, AMT1_1, AMT2_8, AMT3_1, AMT4_1, AMT5_1, AMT6_1);

        // 计算加权期限
        // 1.2 加权剩余期限
        calcTerms(AMT1_1, AMT1_2);
        // 2.9 加权剩余期限
        calcTerms(AMT2_8, AMT2_9);
        // 4.2 加权剩余期限
        calcTerms(AMT4_1, AMT4_2);
        // 5.2 加权剩余期限
        calcTerms(AMT5_1, AMT5_2);
        // 6.2 加权剩余期限
        calcTerms(AMT6_1, AMT6_2);

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
     * @param isCalcBalance 是否计算余额字段
     * @param isCalcTerms 是否计算期限字段
     */
    private void calcMonthReportSubDetail(ChbMonthReportSubDetailDto detailDto, ChbMonthAssetPosition pos,
            boolean isCalcBalance, boolean isCalcTerms) {
        if (isCalcBalance) {
            // 第1列 所有产品
            detailDto.setBalance(ArithmeticUtil.doAdd(detailDto.getBalance(), pos.getAssetvalueB()));
            // 第2列 从母行划转的产品
            if ("01".equals(pos.getIsFprod())) {
                detailDto.setBalanceBan(ArithmeticUtil.doAdd(detailDto.getBalanceBan(), pos.getAssetvalueB()));
            }
            // 第3列 自主发行产品
            else if ("02".equals(pos.getIsFprod())) {
                detailDto.setBalanceSub(ArithmeticUtil.doAdd(detailDto.getBalanceSub(), pos.getAssetvalueB()));
            }
        }
        if (isCalcTerms) {
            // 第4列 所有产品
            detailDto.setTerms(ArithmeticUtil.doAdd(detailDto.getTerms(), ArithmeticUtil.multiply(pos.getAssetvalueB(), pos.getTerms())));
            // 第5列 从母行划转的产品
            if ("01".equals(pos.getIsFprod())) {
                detailDto.setTermsBan(ArithmeticUtil.doAdd(detailDto.getTermsBan(), ArithmeticUtil.multiply(pos.getAssetvalueB(), pos.getTerms())));
            }
            // 第6列 自主发行产品
            else if ("02".equals(pos.getIsFprod())) {
                detailDto.setTermsSub(ArithmeticUtil.doAdd(detailDto.getTermsSub(), ArithmeticUtil.multiply(pos.getAssetvalueB(), pos.getTerms())));
            }
        }
    }

    /**
     * 计算合计项
     * 
     * @param sumLine 合计行
     * @param detailDtos 明细数据
     */
    private void calcSumLine(ChbMonthReportSubDetailDto sumLine, ChbMonthReportSubDetailDto...detailDtos) {
        for (ChbMonthReportSubDetailDto detailDto : detailDtos) {
            sumLine.setBalance(ArithmeticUtil.doAdd(sumLine.getBalance(), detailDto.getBalance()));
            sumLine.setTerms(ArithmeticUtil.doAdd(sumLine.getTerms(), detailDto.getTerms()));
            sumLine.setBalanceAcc(ArithmeticUtil.doAdd(sumLine.getBalanceAcc(), detailDto.getBalanceAcc()));
            sumLine.setTermsAcc(ArithmeticUtil.doAdd(sumLine.getTermsAcc(), detailDto.getTermsAcc()));
            sumLine.setBalanceInc(ArithmeticUtil.doAdd(sumLine.getBalanceInc(), detailDto.getBalanceInc()));
            sumLine.setTermsInc(ArithmeticUtil.doAdd(sumLine.getTermsInc(), detailDto.getTermsInc()));
            sumLine.setBalanceBan(ArithmeticUtil.doAdd(sumLine.getBalanceBan(), detailDto.getBalanceBan()));
            sumLine.addAllProdBan(detailDto.getProdBanSet());
            sumLine.setTermsBan(ArithmeticUtil.doAdd(sumLine.getTermsBan(), detailDto.getTermsBan()));
            sumLine.setBalanceSub(ArithmeticUtil.doAdd(sumLine.getBalanceSub(), detailDto.getBalanceSub()));
            sumLine.addAllProdSub(detailDto.getProdSubSet());
            sumLine.setTermsSub(ArithmeticUtil.doAdd(sumLine.getTermsSub(), detailDto.getTermsSub()));
        }
    }

    /**
     * 计算加权期限.sum(ASSETVALUE_B*TERMS)/(SUM(ASSETVALUE_B)*365)
     * 
     * @param totalBalance 金额总计
     * @param totalTerms 金额 *期限总计
     */
    private void calcTerms(ChbMonthReportSubDetailDto totalBalance, ChbMonthReportSubDetailDto totalTerms) {
        totalTerms.setTerms(ArithmeticUtil.divide(totalTerms.getTerms(), ArithmeticUtil.multiply(totalBalance.getBalance(), BigDecimal.valueOf(365)), 4));
        totalTerms.setTermsBan(ArithmeticUtil.divide(totalTerms.getTermsBan(), ArithmeticUtil.multiply(totalBalance.getBalanceBan(), BigDecimal.valueOf(365)), 4));
        totalTerms.setTermsSub(ArithmeticUtil.divide(totalTerms.getTermsSub(), ArithmeticUtil.multiply(totalBalance.getBalanceSub(), BigDecimal.valueOf(365)), 4));
    }

}
