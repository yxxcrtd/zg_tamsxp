package com.joyintech.tams.svreport.chb.transmonthreport.service;

import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.ChbTransMonthDetailMapper;
import com.joyintech.tams.common.base.entity.ChbTransMonth;
import com.joyintech.tams.common.base.entity.ChbTransMonthTemplate;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthAssetPosition;
import com.joyintech.tams.svreport.chb.transmonthreport.dao.ChbTransMonthReportSubRegistBatchMapper;
import com.joyintech.tams.svreport.chb.transmonthreport.dto.ChbTransMonthReportConstants;
import com.joyintech.tams.svreport.chb.transmonthreport.dto.ChbTransMonthReportSubDetailDto;
import com.joyintech.tams.svreport.chb.transmonthreport.dto.ChbTransMonthReportSubDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 理财产品转型情况月度统计表生成资产端报表数据处理类
 *
 * @author dubaojun
 ** @version 1.0 2020年6月8日
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ChbTransMonthReportAssetBatchService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChbTransMonthDetailMapper chbTransMonthDetailMapper;

    @Autowired
    private ChbTransMonthReportSubRegistBatchMapper chbTransMonthReportSubRegistBatchMapper;

    @Value("${application.reportSelectOrDeleteCount}")
    public int reportSelectOrDeleteCount;

    /**
     * 生成资产端端报表明细数据
     *
     * @param report 生成参数
     */
    public void genMonthAsset(final ChbTransMonthReportSubDto report) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Date firstday = report.getStartdate();
        Date lastday = report.getEnddate();

        ChbTransMonth chbTransMonth = new ChbTransMonth();
        chbTransMonth.setTermid(sdf.format(firstday));

        // 报表ID
        chbTransMonth.setReportuuid(UUID.randomUUID().toString());

        // 报表类型
        chbTransMonth.setReporttype(ChbTransMonthReportConstants.REPORT_TYPE_ASSET);

        // 起止日期
        chbTransMonth.setStartdate(firstday);
        chbTransMonth.setEnddate(lastday);

        // 数据日期：批处理日期
        chbTransMonth.setStatusDate(report.getStatusDate());

        // 报表频率：月报
        chbTransMonth.setReportfrequ(ChbTransMonthReportConstants.REPORT_FREQU_M);

        // 发行机构代码取值
        chbTransMonth.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
        chbTransMonth.setIssuePartyName(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_NAME));

        // 用户信息
        chbTransMonth.setOrgCode(CommonUtil.getOrgCode());
        chbTransMonth.setCreateDept(CommonUtil.getDeptCode());
        chbTransMonth.setCreateUser(CommonUtil.getUserName());
        chbTransMonth.setUpdateUser(CommonUtil.getUserName());

        // 数据状态
        chbTransMonth.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
        chbTransMonth.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());

        Map<String, ChbTransMonthReportSubDetailDto> result = new HashMap<String, ChbTransMonthReportSubDetailDto>();
        // 查询产品月报需要统计的项
        List<ChbTransMonthTemplate> listKey = chbTransMonthReportSubRegistBatchMapper
                .selectSubTemplatesByReporttype(ChbTransMonthReportConstants.REPORT_TYPE_ASSET);
        for (ChbTransMonthTemplate k : listKey) {
            ChbTransMonthReportSubDetailDto detail = new ChbTransMonthReportSubDetailDto();
            detail.setTermid(chbTransMonth.getTermid());
            detail.setReporttype(chbTransMonth.getReporttype());
            detail.setPropertycode(k.getPropertycode());
            result.put(k.getPropertycode(), detail);
        }
        if (listKey.size() != 34) {
            logger.error("理财产品转型情况月度统计表资产端模板配置错误，请检查");
            return;
        }

        List<ChbMonthAssetPosition> listAssetPosition = chbTransMonthReportSubRegistBatchMapper
                .selectAssetPositionsByMonthDate(firstday, lastday);

        // 1 标准化债权类资产
        ChbTransMonthReportSubDetailDto AMT1_1 = result.get(ChbTransMonthReportConstants.AMT1_1); // 1.1 合 计
        ChbTransMonthReportSubDetailDto AMT1_1_a = result.get(ChbTransMonthReportConstants.AMT1_1_a); // 其中 1.1.a 信用债
        ChbTransMonthReportSubDetailDto AMT1_1_a_a = result.get(ChbTransMonthReportConstants.AMT1_1_a_a); // 其中 1.1.a.a PPN等低流动性债券
        ChbTransMonthReportSubDetailDto AMT1_1_b = result.get(ChbTransMonthReportConstants.AMT1_1_b); // 1.1.b 利率债
        ChbTransMonthReportSubDetailDto AMT1_1_c = result.get(ChbTransMonthReportConstants.AMT1_1_c); // 1.1.c 固定收益类公募基金
        ChbTransMonthReportSubDetailDto AMT1_2 = result.get(ChbTransMonthReportConstants.AMT1_2); // 1.2 加权剩余期限
        ChbTransMonthReportSubDetailDto AMT1_1_ex0 = new ChbTransMonthReportSubDetailDto(); // 除去期限为零的1.1合计
        // 2 非标准化债权类资产
        ChbTransMonthReportSubDetailDto AMT2_1 = result.get(ChbTransMonthReportConstants.AMT2_1); // 2.1 通过信托贷款和信托产品
        ChbTransMonthReportSubDetailDto AMT2_2 = result.get(ChbTransMonthReportConstants.AMT2_2); // 2.2 通过证券期货经营机构资管产品
        ChbTransMonthReportSubDetailDto AMT2_3 = result.get(ChbTransMonthReportConstants.AMT2_3); // 2.3 通过保险资管产品
        ChbTransMonthReportSubDetailDto AMT2_4 = result.get(ChbTransMonthReportConstants.AMT2_4); // 2.4 通过委托贷款
        ChbTransMonthReportSubDetailDto AMT2_5 = result.get(ChbTransMonthReportConstants.AMT2_5); // 2.5 通过债权融资类产品
        ChbTransMonthReportSubDetailDto AMT2_6 = result.get(ChbTransMonthReportConstants.AMT2_6); // 2.6 通过其他形式
        ChbTransMonthReportSubDetailDto AMT2_7 = result.get(ChbTransMonthReportConstants.AMT2_7); // 2.7 直接投资
        ChbTransMonthReportSubDetailDto AMT2_8 = result.get(ChbTransMonthReportConstants.AMT2_8); // 2.8 合 计
        ChbTransMonthReportSubDetailDto AMT2_9 = result.get(ChbTransMonthReportConstants.AMT2_9); // 2.9 加权剩余期限
        ChbTransMonthReportSubDetailDto AMT2_8_ex0 = new ChbTransMonthReportSubDetailDto(); // 除去期限为零的2.8合计
        // 3 股票类资产
        ChbTransMonthReportSubDetailDto AMT3_1 = result.get(ChbTransMonthReportConstants.AMT3_1); // 3.1 合 计
        // 4 未上市企业股权
        ChbTransMonthReportSubDetailDto AMT4_1 = result.get(ChbTransMonthReportConstants.AMT4_1); // 4.1 合 计
        ChbTransMonthReportSubDetailDto AMT4_1_a = result.get(ChbTransMonthReportConstants.AMT4_1_a); // 其中 4.1.a 政府出资产业投资基金
        ChbTransMonthReportSubDetailDto AMT4_1_b = result.get(ChbTransMonthReportConstants.AMT4_1_b); // 4.1.b 创业投资基金
        ChbTransMonthReportSubDetailDto AMT4_2 = result.get(ChbTransMonthReportConstants.AMT4_2); // 4.2 加权剩余期限
        ChbTransMonthReportSubDetailDto AMT4_1_ex0 = new ChbTransMonthReportSubDetailDto(); // 除去期限为零的4.1合计
        // 5 银行资本补充工具
        ChbTransMonthReportSubDetailDto AMT5_1 = result.get(ChbTransMonthReportConstants.AMT5_1); // 5.1 合 计
        ChbTransMonthReportSubDetailDto AMT5_1_a = result.get(ChbTransMonthReportConstants.AMT5_1_a); // 其中 5.1.a 优先股
        ChbTransMonthReportSubDetailDto AMT5_1_b = result.get(ChbTransMonthReportConstants.AMT5_1_b); // 5.1.b 永续债（债性）
        ChbTransMonthReportSubDetailDto AMT5_1_c = result.get(ChbTransMonthReportConstants.AMT5_1_c); // 5.1.c 永续债（股性）
        ChbTransMonthReportSubDetailDto AMT5_1_d = result.get(ChbTransMonthReportConstants.AMT5_1_d); // 5.1.d 二级资本债
        ChbTransMonthReportSubDetailDto AMT5_2 = result.get(ChbTransMonthReportConstants.AMT5_2); // 5.2 加权剩余期限
        ChbTransMonthReportSubDetailDto AMT5_1_ex0 = new ChbTransMonthReportSubDetailDto(); // 除去期限为零的5.1合计
        // 6 其他资产
        ChbTransMonthReportSubDetailDto AMT6_1 = result.get(ChbTransMonthReportConstants.AMT6_1); // 6.1 合 计
        ChbTransMonthReportSubDetailDto AMT6_1_a = result.get(ChbTransMonthReportConstants.AMT6_1_a); // 其中 6.1.a 拆放同业及买入返售
        ChbTransMonthReportSubDetailDto AMT6_1_b = result.get(ChbTransMonthReportConstants.AMT6_1_b); // 6.1.b 现金及银行存款
        ChbTransMonthReportSubDetailDto AMT6_1_c = result.get(ChbTransMonthReportConstants.AMT6_1_c); // 6.1.c 其他公募基金
        ChbTransMonthReportSubDetailDto AMT6_1_d = result.get(ChbTransMonthReportConstants.AMT6_1_d); // 6.1.d 商品及金融衍生产品
        ChbTransMonthReportSubDetailDto AMT6_1_e = result.get(ChbTransMonthReportConstants.AMT6_1_e); // 6.1.e QDII
        ChbTransMonthReportSubDetailDto AMT6_2 = result.get(ChbTransMonthReportConstants.AMT6_2); // 6.2 加权剩余期限
        ChbTransMonthReportSubDetailDto AMT6_1_ex0 = new ChbTransMonthReportSubDetailDto(); // 除去期限为零的6.1合计
        // 7 合计
        ChbTransMonthReportSubDetailDto AMT7 = result.get(ChbTransMonthReportConstants.AMT7); // 7 合计

        for (ChbMonthAssetPosition pos : listAssetPosition) {
            if (pos.getAssetvalueB() == null) {
                pos.setAssetvalueB(BigDecimal.ZERO);
            }

            // 1 标准化债权类资产
            if (pos.getSunassettype() != null && pos.getSunassettype().indexOf("F01") != -1) {
                // 1.1 合 计
                calcMonthReportSubDetail(AMT1_1, pos, true, false, null);
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 start
                if(!BigDecimal.ZERO.equals(pos.getTerms())){
                    // 1.1 除去期限等于零合 计
                    calcMonthReportSubDetail(AMT1_1_ex0, pos, true, false, null);
                }
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 end
                // 其中 1.1.a 信用债
                if (pos.getSunassettype2() != null && pos.getSunassettype2().indexOf("F0102")  != -1) {
                    calcMonthReportSubDetail(AMT1_1_a, pos, true, false, null);
                    // 其中 1.1.a.a PPN等低流动性债券 非公开发行：02 公开发行：01
                    //if ("F0101".equals(pos.getSunassettype2()) && "02".equals(pos.getIssueMode())) {
                    //其债项评级在AA+（不含）以下或无信用评级 modify by wuhaisen  20220120  start
                    if (pos.getRating() == null || Integer.parseInt(SvrDataUtil.getCodeFromDataDictByName(DictCode.资产评级, pos.getRating())) > 4) {//不判定是否公开发行
                        //其债项评级在AA+（不含）以下或无信用评级 modify by wuhaisen  20220120  end
                        calcMonthReportSubDetail(AMT1_1_a_a, pos, true, false, null);
                    }
                }
                if (pos.getSunassettype2() != null && pos.getSunassettype2().indexOf("F0101")  != -1) {
                    calcMonthReportSubDetail(AMT1_1_a, pos, true, false, null);
                    // 其中 1.1.a.a PPN等低流动性债券 非公开发行：02 公开发行：01
                    //if ("F0101".equals(pos.getSunassettype2()) && "02".equals(pos.getIssueMode())) {
                    //其债项评级在AA+（不含）以下或无信用评级 modify by wuhaisen  20220120  start
                    if (pos.getRating() == null || Integer.parseInt(SvrDataUtil.getCodeFromDataDictByName(DictCode.资产评级, pos.getRating())) > 4) {//不判定是否公开发行
                        //其债项评级在AA+（不含）以下或无信用评级 modify by wuhaisen  20220120  end
                        calcMonthReportSubDetail(AMT1_1_a_a, pos, true, false, null);
                    }
                }
                // 1.1.b 利率债
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0103")  != -1) {
                    calcMonthReportSubDetail(AMT1_1_b, pos, true, false, null);

                }
                // 1.1.c 固定收益类公募基金
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0104")  != -1) {
                    calcMonthReportSubDetail(AMT1_1_c, pos, true, false, null);
                }
                // 1.2 加权剩余期限
                calcMonthReportSubDetail(AMT1_2, pos, false, true, null);
            }
            // 2 非标准化债权类资产
            if (pos.getSunassettype() != null && pos.getSunassettype().indexOf("F02") != -1) {
                // 2.1 通过信托贷款和信托产品
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0201")  != -1) {
                    calcMonthReportSubDetail(AMT2_1, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_8, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_9, pos, false, true, null);
                    if(!BigDecimal.ZERO.equals(pos.getTerms())){
                        // 2.8 除去期限等于零合 计
                        calcMonthReportSubDetail(AMT2_8_ex0, pos, true, false, null);
                    }
                }
                // 2.2 通过证券期货经营机构资管产品
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0202")  != -1) {
                    calcMonthReportSubDetail(AMT2_2, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_8, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_9, pos, false, true, null);
                    if(!BigDecimal.ZERO.equals(pos.getTerms())){
                        // 2.8 除去期限等于零合 计
                        calcMonthReportSubDetail(AMT2_8_ex0, pos, true, false, null);
                    }
                }
                // 2.3 通过保险资管产品
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0203")  != -1) {
                    calcMonthReportSubDetail(AMT2_3, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_8, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_9, pos, false, true, null);
                    if(!BigDecimal.ZERO.equals(pos.getTerms())){
                        // 2.8 除去期限等于零合 计
                        calcMonthReportSubDetail(AMT2_8_ex0, pos, true, false, null);
                    }
                }
                // 2.4 通过委托贷款
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0204")  != -1) {
                    calcMonthReportSubDetail(AMT2_4, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_8, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_9, pos, false, true, null);
                    if(!BigDecimal.ZERO.equals(pos.getTerms())){
                        // 2.8 除去期限等于零合 计
                        calcMonthReportSubDetail(AMT2_8_ex0, pos, true, false, null);
                    }
                }
                // 2.5 通过债权融资类产品
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0205")  != -1) {
                    calcMonthReportSubDetail(AMT2_5, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_8, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_9, pos, false, true, null);
                    if(!BigDecimal.ZERO.equals(pos.getTerms())){
                        // 2.8 除去期限等于零合 计
                        calcMonthReportSubDetail(AMT2_8_ex0, pos, true, false, null);
                    }
                }
                // 2.6 通过其他形式
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0206")  != -1) {
                    calcMonthReportSubDetail(AMT2_6, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_8, pos, true, false, null);
                    calcMonthReportSubDetail(AMT2_9, pos, false, true, null);
                    if(!BigDecimal.ZERO.equals(pos.getTerms())){
                        // 2.8 除去期限等于零合 计
                        calcMonthReportSubDetail(AMT2_8_ex0, pos, true, false, null);
                    }
                }
                // 2.7 直接投资
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0207")  != -1) {

                    calcMonthReportSubDetail(AMT2_7, pos, true, false, "F0207");
                    calcMonthReportSubDetail(AMT2_8, pos, true, false, "F0207");
                    calcMonthReportSubDetail(AMT2_9, pos, false, true, "F0207");
                    if(!BigDecimal.ZERO.equals(pos.getTerms())){
                        // 2.8 除去期限等于零合 计
                        calcMonthReportSubDetail(AMT2_8_ex0, pos, true, false, "F0207");
                    }
                }
                // 2.8 合 计
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 start
                /*if(!BigDecimal.ZERO.equals(pos.getTerms())){
                    // 2.8 除去期限等于零合 计
                    calcMonthReportSubDetail(AMT2_8_ex0, pos, true, false, null);
                }*/
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 end
                // 2.9 加权剩余期限
                //calcMonthReportSubDetail(AMT2_9, pos, false, true, null);
            }
            // 3 股票类资产
            if (pos.getSunassettype() != null && pos.getSunassettype().indexOf("F03") != -1) {
                // 3.1 合 计
                calcMonthReportSubDetail(AMT3_1, pos, true, false, null);
            }
            // 4 未上市企业股权
            if (pos.getSunassettype() != null && pos.getSunassettype().indexOf("F04") != -1) {
                // 4.1 合 计
                calcMonthReportSubDetail(AMT4_1, pos, true, false, null);
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 start
                if(!BigDecimal.ZERO.equals(pos.getTerms())){
                    // 4.1 除去期限等于零的合计
                    calcMonthReportSubDetail(AMT4_1_ex0, pos, true, false, null);
                }
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 end
                // 其中 4.1.a 政府出资产业投资基金
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0401")  != -1) {
                    calcMonthReportSubDetail(AMT4_1_a, pos, true, false, null);
                }
                // 4.1.b 创业投资基金
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0402")  != -1) {
                    calcMonthReportSubDetail(AMT4_1_b, pos, true, false, null);
                }
                // 4.2 加权剩余期限
                calcMonthReportSubDetail(AMT4_2, pos, false, true, null);
            }
            // 5 银行资本补充工具
//            if (pos.getSunassettype() != null && pos.getSunassettype().indexOf("F05") != -1) {
        if (Objects.equals(pos.getBankCapital(), DictCodeListExt3.银行资本补充工具_永续债_债性.getValue()) ||
                Objects.equals(pos.getBankCapital(),DictCodeListExt3.银行资本补充工具_永续债_股性.getValue()) ||
                Objects.equals(pos.getBankCapital(),DictCodeListExt3.银行资本补充工具_二级资本债.getValue()) ||
                Objects.equals(pos.getBankCapital(),DictCodeListExt3.银行资本补充工具_优先股.getValue()) ) {
                // 5.1 合 计
                calcMonthReportSubDetail(AMT5_1, pos, true, false, null);
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 start
                if(!BigDecimal.ZERO.equals(pos.getTerms())){
                    // 5.1 除去期限等于零的合计
                    calcMonthReportSubDetail(AMT5_1_ex0, pos, true, false, null);
                }
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 end
                // 其中 5.1.a 优先股
                if (Objects.equals(pos.getBankCapital(),DictCodeListExt3.银行资本补充工具_优先股.getValue())) {
                    calcMonthReportSubDetail(AMT5_1_a, pos, true, false, null);
                }
                // 5.1.b 永续债（债性）
                if (Objects.equals(pos.getBankCapital(),DictCodeListExt3.银行资本补充工具_永续债_债性.getValue())) {
                    calcMonthReportSubDetail(AMT5_1_b, pos, true, false, null);
                }
    //                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0506")  != -1) {
    //                    calcMonthReportSubDetail(AMT5_1_b, pos, true, false, null);
    //                }
                // 5.1.c 永续债（股性）
                if (Objects.equals(pos.getBankCapital(),DictCodeListExt3.银行资本补充工具_永续债_股性.getValue())) {
                    calcMonthReportSubDetail(AMT5_1_c, pos, true, false, null);
                }
    //                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0505")  != -1) {
    //                    calcMonthReportSubDetail(AMT5_1_c, pos, true, false, null);
    //                }
                // 5.1.d 二级资本债
                if (Objects.equals(pos.getBankCapital(),DictCodeListExt3.银行资本补充工具_二级资本债.getValue())) {
                    calcMonthReportSubDetail(AMT5_1_d, pos, true, false, null);
                }
    //                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0505")  != -1) {
    //                    calcMonthReportSubDetail(AMT5_1_d, pos, true, false, null);
    //                }
    //                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0506")  != -1) {
    //                    calcMonthReportSubDetail(AMT5_1_d, pos, true, false, null);
    //                }
                // 5.2 加权剩余期限
                calcMonthReportSubDetail(AMT5_2, pos, false, true, null);
            }
            // 6 其他资产
            if (pos.getSunassettype() != null && pos.getSunassettype().indexOf("F06") != -1) {
                // 6.1 合 计
                calcMonthReportSubDetail(AMT6_1, pos, true, false, null);
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 start
                if(!BigDecimal.ZERO.equals(pos.getTerms())){
                    // 6.1 除去期限等于零的合计
                    calcMonthReportSubDetail(AMT6_1_ex0, pos, true, false, null);
                }
                // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 end
                // 其中 6.1.a 拆放同业及买入返售
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0601")  != -1) {
                    calcMonthReportSubDetail(AMT6_1_a, pos, true, false, null);
                }
                // 6.1.b 现金及银行存款
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0602")  != -1) {
                    calcMonthReportSubDetail(AMT6_1_b, pos, true, false, null);
                }
                // 6.1.c 其他公募基金
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0603")  != -1) {
                    calcMonthReportSubDetail(AMT6_1_c, pos, true, false, null);
                }
                // 6.1.d 商品及金融衍生产品
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0604")  != -1) {
                    calcMonthReportSubDetail(AMT6_1_d, pos, true, false, null);
                }
                // 6.1.e QDII
                if (pos.getSunassettype() != null && pos.getSunassettype2().indexOf("F0605")  != -1) {
                    calcMonthReportSubDetail(AMT6_1_e, pos, true, false, null);
                }
                // 6.2 加权剩余期限
                calcMonthReportSubDetail(AMT6_2, pos, false, true, null);
            }
        } // 产品头寸循环结束

        // 7 合计
        calcSumLine(AMT7, AMT1_1, AMT2_8, AMT3_1, AMT4_1, AMT5_1, AMT6_1);

        // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 start
        // 计算加权期限
        // 1.2 加权剩余期限
        calcTerms(AMT1_1_ex0, AMT1_2);
        // 2.9 加权剩余期限
        calcTerms(AMT2_8_ex0, AMT2_9);
        // 4.2 加权剩余期限
        calcTerms(AMT4_1_ex0, AMT4_2);
        // 5.2 加权剩余期限
        calcTerms(AMT5_1_ex0, AMT5_2);
        // 6.2 加权剩余期限
        calcTerms(AMT6_1_ex0, AMT6_2);
        // 计算剩余加权期限使用剩余期限不等于零的合计 modify by wuhaisen 20220120 end
        // 遍历result，插入全部的数据
        // 删除时上一次已报送数据不能删除
        if (StringUtil.isNotEmpty(report.getWbsreportuuid())) {
            chbTransMonthReportSubRegistBatchMapper.deleteChbTransMonthDetail(chbTransMonth.getTermid(), chbTransMonth.getReporttype(),
                    report.getWbsreportuuid());
        }
        for (ChbTransMonthReportSubDetailDto v : result.values()) {
            v.setReportuuid(report.getReportuuid());
            chbTransMonthDetailMapper.insert(v);
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
    private void calcMonthReportSubDetail(ChbTransMonthReportSubDetailDto detailDto, ChbMonthAssetPosition pos,
                                          boolean isCalcBalance, boolean isCalcTerms, String isGetAssetvalueA) {
        if (isCalcBalance) {
            // 新产品
            if ("01".equals(pos.getIsFhxgcp())) {
                if("F0207".equals(isGetAssetvalueA)){
                    detailDto.setNewBalance(ArithmeticUtil.doAdd(detailDto.getNewBalance(), pos.getAssetvalueA()));
                }else{
                    // 新产品余额
                    detailDto.setNewBalance(ArithmeticUtil.doAdd(detailDto.getNewBalance(), pos.getAssetvalueB()));
                }

            }else{
                if("F0207".equals(isGetAssetvalueA)){
                    detailDto.setOldBalance(ArithmeticUtil.doAdd(detailDto.getOldBalance(), pos.getAssetvalueA()));
                }else {
                    // 老产品余额
                    detailDto.setOldBalance(ArithmeticUtil.doAdd(detailDto.getOldBalance(), pos.getAssetvalueB()));
                }
            }
        }
        if (isCalcTerms) {
            // 新产品
            if ("01".equals(pos.getIsFhxgcp())) {
                if("F0207".equals(isGetAssetvalueA)){
                    detailDto.setNewTerms(ArithmeticUtil.doAdd(detailDto.getNewTerms(), ArithmeticUtil.multiply(pos.getAssetvalueA(), pos.getTerms())));
                }else {
                    // 新产品剩余期限
                    detailDto.setNewTerms(ArithmeticUtil.doAdd(detailDto.getNewTerms(), ArithmeticUtil.multiply(pos.getAssetvalueB(), pos.getTerms())));
                }
            }else{
                if("F0207".equals(isGetAssetvalueA)){
                    detailDto.setOldTerms(ArithmeticUtil.doAdd(detailDto.getOldTerms(), ArithmeticUtil.multiply(pos.getAssetvalueA(), pos.getTerms())));
                }else {
                    // 老产品剩余期限
                    detailDto.setOldTerms(ArithmeticUtil.doAdd(detailDto.getOldTerms(), ArithmeticUtil.multiply(pos.getAssetvalueB(), pos.getTerms())));
                }
            }
        }
    }

    /**
     * 计算合计项
     *
     * @param sumLine 合计行
     * @param detailDtos 明细数据
     */
    private void calcSumLine(ChbTransMonthReportSubDetailDto sumLine, ChbTransMonthReportSubDetailDto...detailDtos) {
        for (ChbTransMonthReportSubDetailDto detailDto : detailDtos) {
            sumLine.setNewBalance(ArithmeticUtil.doAdd(sumLine.getNewBalance(), detailDto.getNewBalance()));
            sumLine.setOldBalance(ArithmeticUtil.doAdd(sumLine.getOldBalance(), detailDto.getOldBalance()));
            sumLine.setNewTerms(ArithmeticUtil.doAdd(sumLine.getNewTerms(), detailDto.getNewTerms()));
            sumLine.setOldTerms(ArithmeticUtil.doAdd(sumLine.getOldTerms(), detailDto.getOldTerms()));
        }
    }

    /**
     * 计算加权期限.sum(ASSETVALUE_B*TERMS)/(SUM(ASSETVALUE_B)*365)
     *
     * @param totalBalance 金额总计
     * @param totalTerms 金额 *期限总计
     */
    private void calcTerms(ChbTransMonthReportSubDetailDto totalBalance, ChbTransMonthReportSubDetailDto totalTerms) {
        totalTerms.setNewTerms(ArithmeticUtil.divide(totalTerms.getNewTerms(), ArithmeticUtil.multiply(totalBalance.getNewBalance(), BigDecimal.valueOf(365)), 4));
        totalTerms.setOldTerms(ArithmeticUtil.divide(totalTerms.getOldTerms(), ArithmeticUtil.multiply(totalBalance.getOldBalance(), BigDecimal.valueOf(365)), 4));
    }

}
