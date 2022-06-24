package com.joyintech.tams.svreport.pbc.reportdetail.service;

import com.joyintech.background.component.common.pack.common.util.BeanUtil;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.tams.common.base.dao.ChbMonthPrdPositiondetailMapper;
import com.joyintech.tams.common.base.dao.ChbPrdBaseinfoMapper;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.entity.ChbMonthPrdPositiondetail;
import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.pbc.assetposition.dto.PbcMonthAssetPositionDto;
import com.joyintech.tams.svreport.pbc.common.constants.PbcReportProperycode;
import com.joyintech.tams.svreport.pbc.prod.dto.PbcMonthPrdPostionDto;
import com.joyintech.tams.svreport.pbc.reportdetail.dao.PbcMonthReportDetailProdMapper;
import com.joyintech.tams.svreport.pbc.reportdetail.dto.PbcMonthReportDetailProd;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class PbcMonthReportDetailProdUtil {

    @Autowired
    private PbcMonthReportDetailProdMapper pbcMonthReportDetailProdMapper;

    @Autowired
    private ChbPrdBaseinfoMapper chbPrdBaseinfoMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String,ChbTypeMappingEx> investorTypeMap;

    /**
     * 数据批量一次性insert条数
     */
    @Value("${application.reportInsertCount}")
    public int reportInsertCount;

    /**
     * 数据赋值
     * @param prdPosition
     * @param info
     * @param
     * @return
     */
    public static PbcMonthReportDetailProd buildProdDetail(PbcMonthPrdPostionDto prdPosition, ChbPrdBaseinfo info, String termid){
        PbcMonthReportDetailProd pbcMonthPrdPositiondetail = new PbcMonthReportDetailProd();
        //相同内容字段直接赋值
        BeanUtil.copyPropertiesIgnoreNull(pbcMonthPrdPositiondetail,info);
        pbcMonthPrdPositiondetail.setIsMarketFund(info.getCashManaCate());
        //先基本信息,再头寸信息,避免错误覆盖
        BeanUtil.copyPropertiesIgnoreNull(pbcMonthPrdPositiondetail,prdPosition);


        String ordIdentified = null;
        if(DictCodeListExt2.客户二级类型_住户.getValue().equals(prdPosition.getCustomerType2())){
            ordIdentified = DictCodeListExt2.客户一级类型_住户.getValue();
        }else if(DictCodeListExt2.客户二级类型_广义政府.getValue().equals(prdPosition.getCustomerType2())){
            ordIdentified = DictCodeListExt2.客户一级类型_广义政府.getValue();
        }else if(DictCodeListExt2.客户二级类型_非金融企业.getValue().equals(prdPosition.getCustomerType2())){
            ordIdentified  = DictCodeListExt2.客户一级类型_非金融企业.getValue();
        }else if(DictCodeListExt2.客户二级类型_银行业存款类金融机构.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_银行业非存款类金融机构.getValue().equals(prdPosition.getCustomerType2())
                /*****G06升级改造 leipeng 20210128   start**/
                ||DictCodeListExt2.客户二级类型_保险业金融机构.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_信托公司.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_证券公司.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_基金公司.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_其他金融机构.getValue().equals(prdPosition.getCustomerType2())
                /*****G06升级改造 leipeng 20210128   end**/
                ||DictCodeListExt2.客户二级类型_非银行业金融机构.getValue().equals(prdPosition.getCustomerType2())){
            ordIdentified = DictCodeListExt2.客户一级类型_金融机构.getValue();
        }else if(DictCodeListExt2.客户二级类型_银行非保本理财.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_信托公司资管产品.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_证券公司及其子公司资管产品.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_基金管理公司及其子公司专户.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_期货公司及其子公司资管产品.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_保险资管产品.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_金融资产投资公司资管产品.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_公募基金.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_私募机构私募基金.getValue().equals(prdPosition.getCustomerType2())
                ||DictCodeListExt2.客户二级类型_其他特定目的载体.getValue().equals(prdPosition.getCustomerType2())){
            ordIdentified = DictCodeListExt2.客户一级类型_特定目的载体.getValue();
        }else if (null!=prdPosition.getCustomerType2()&&prdPosition.getCustomerType2().contains(DictCodeListExt2.客户一级类型_境外.getValue())) {
            ordIdentified = DictCodeListExt2.客户一级类型_境外.getValue();
        }

        pbcMonthPrdPositiondetail.setOrdIdentified(ordIdentified);

        pbcMonthPrdPositiondetail.setTermdays(getTermDays(info.getOperateMode(),info.getOpenCircle(),info.getOpenCircleDays(),new BigDecimal(DateUtil.getBetweenDayNumber(info.getVdate(),info.getMdate()))));

        pbcMonthPrdPositiondetail.setTermtype(getTermTypeNew(prdPosition.getTerm(),info.getOperateMode()));

        pbcMonthPrdPositiondetail.setTerm(termid);

//        pbcMonthPrdPositiondetail.setTermdays(getTermDays(info.getOperateMode(),info.getOpenCircle(),info.getOpenCircleDays(),prdPosition.getClosetermday()));

        return pbcMonthPrdPositiondetail;
    }


    /**
     * 组装详情信息入库
     * @param posList
     */
    public void buildDatilList(List<PbcMonthPrdPostionDto> posList ,String termid){

        Map<String,ChbPrdBaseinfo> prodMap;
        List<PbcMonthReportDetailProd> prodDetailList = new ArrayList();

        /***获取产品信息列表**/
        List<ChbPrdBaseinfo> prdBaseinfoList = chbPrdBaseinfoMapper.selectAllProd(null);
        prodMap = null!=prdBaseinfoList?prdBaseinfoList.stream().collect(Collectors.toMap(p->p.getProdId(), p-> p,(key1, key2)->key1)):new HashMap<>();

        String source = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE_PBC);
        Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();
        for(PbcMonthPrdPostionDto dto: posList){
            if(!DictCodeListExt1.币种_中债_人民币.getValue().equals(dto.getCcy())){
                if(null!=dto.getEndamtsumOth()){
                    dto.setEndamtsumOth(ChbCcyConvertUtil.exchangeMoney(
                            dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(),dto.getEnddate(),dto.getEndamtsumOth(),rateMap,source));
                }
                if(null!=dto.getDirtyamtsum()){
                    dto.setDirtyamtsum(ChbCcyConvertUtil.exchangeMoney(
                            dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(),dto.getEnddate(),dto.getDirtyamtsum(),rateMap,source));
                }
                if(null!=dto.getRedamt()){
                    dto.setRedamt(ChbCcyConvertUtil.exchangeMoney(
                            dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(),dto.getEnddate(),dto.getRedamt(),rateMap,source));
                }
            }
            prodDetailList.add(buildProdDetail(dto,prodMap.get(dto.getProdid()),termid));
        }

        if(CollectionUtils.isNotEmpty(prodDetailList)){
            insertBatch(prodDetailList,termid);
        }
    }


    /**
     * 开放式净值型、开放式非净值型时取产品的开放天数，开放天数与产品基本信息中的【规律开放周期】的对应关系如下
     * @param openCircle
     * @param openCircleDays
     * @return
     */
    public static BigDecimal getTermDays(String operateMode,String openCircle,BigDecimal openCircleDays,BigDecimal closetermday){
        BigDecimal days = null;
        if(DictCodeListExt2.产品运作模式_封闭式净值型.getValue().equals(operateMode)
                ||DictCodeListExt2.产品运作模式_封闭式非净值型.getValue().equals(operateMode)){
            days = closetermday;
        }else{
            if(StringUtil.isNotEmpty(openCircle)){
                switch (openCircle){
                    case  "01": //DictCodeListExt2.规律开放周期_每天开放.getValue()
                        days = BigDecimal.ONE; break;
                    case "02" :
                        days = new BigDecimal("7"); break;
                    case "03" :
                        days = new BigDecimal("14"); break;
                    case "04" :
                        days = new BigDecimal("31"); break;
                    case "05" :
                        days = new BigDecimal("92"); break;
                    case "06" :
                        days = new BigDecimal("184"); break;
                    case "07" :
                        days = new BigDecimal("366"); break;
                    case "99" :
                        days = openCircleDays; break;
                }
            }else{
                days = BigDecimal.ONE;
            }
        }

        return days;
    }

    /**
     * 开放式都统计到无固定期限，封闭式的根据到期日-起息日
     * @param termDays
     * @param operateMode
     * @return
     */
    public static String getTermTypeNew(BigDecimal termDays,String operateMode){

        String code = null;

        if((DictCodeListExt2.产品运作模式_封闭式净值型.getValue().equals(operateMode)
                ||DictCodeListExt2.产品运作模式_封闭式非净值型.getValue().equals(operateMode))
                &&null!=termDays&&termDays.compareTo(BigDecimal.ZERO) != 0){
            if(termDays.compareTo(BigDecimal.ZERO)>0&&termDays.compareTo(new BigDecimal(30))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_1个月.getValue();
            }else if(termDays.compareTo(new BigDecimal(30))>0&&termDays.compareTo(new BigDecimal(90))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_1到3个月.getValue();
            }else if(termDays.compareTo(new BigDecimal(90))>0&&termDays.compareTo(new BigDecimal(180))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_3到6个月.getValue();
            }else if(termDays.compareTo(new BigDecimal(180))>0&&termDays.compareTo(new BigDecimal(360))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_6到12个月.getValue();
            }else if(termDays.compareTo(new BigDecimal(360))>0&&termDays.compareTo(new BigDecimal(720))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_1到2年.getValue();
            }else if(termDays.compareTo(new BigDecimal(720))>0&&termDays.compareTo(new BigDecimal(1080))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_2到3年.getValue();
            }else {
                code = DictCodeListExt3.人行月度明细_期限类型_3年以上.getValue();
            }
        }else if((DictCodeListExt2.产品运作模式_开放式净值型.getValue().equals(operateMode)
                ||DictCodeListExt2.产品运作模式_开放式非净值型.getValue().equals(operateMode))
                &&null != termDays && termDays.compareTo(BigDecimal.ZERO) == 0){
                code = DictCodeListExt3.人行月度明细_期限类型_无固定期限.getValue();
        }

        return code;
    }

    public static String getTermType(BigDecimal termDays){
        String code = null;
        if(null!=termDays){
            if(termDays.compareTo(BigDecimal.ZERO)>0&&termDays.compareTo(new BigDecimal(30))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_1个月.getValue();
            }else if(termDays.compareTo(new BigDecimal(30))>0&&termDays.compareTo(new BigDecimal(90))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_1到3个月.getValue();
            }else if(termDays.compareTo(new BigDecimal(90))>0&&termDays.compareTo(new BigDecimal(180))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_3到6个月.getValue();
            }else if(termDays.compareTo(new BigDecimal(180))>0&&termDays.compareTo(new BigDecimal(360))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_6到12个月.getValue();
            }else if(termDays.compareTo(new BigDecimal(360))>0&&termDays.compareTo(new BigDecimal(720))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_1到2年.getValue();
            }else if(termDays.compareTo(new BigDecimal(720))>0&&termDays.compareTo(new BigDecimal(1080))<=0){
                code = DictCodeListExt3.人行月度明细_期限类型_2到3年.getValue();
            }else if(termDays.compareTo(new BigDecimal(1080))>0){
                code = DictCodeListExt3.人行月度明细_期限类型_3年以上.getValue();
            }else if(termDays.compareTo(BigDecimal.ZERO)==0){
                code = DictCodeListExt3.人行月度明细_期限类型_无固定期限.getValue();
            }
        }
        return code;
    }






//    /**
//     * 为详情赋值费用数据
//     * @param prdPositiondetail
//     * @param prodId
//     * @param bankprofit
//     */
//    public static void setFeeDetail(List<ChbMonthPrdPositiondetail> prdPositiondetail, ChbMonthFeePosition feePosition){
//        Map<String, List<ChbMonthPrdPositiondetail>> entrustIdMap = prdPositiondetail.stream().collect(JoyinCollectors.groupingBy(ChbMonthPrdPositiondetail::getProdid));
//        for(ChbMonthPrdPositiondetail detail : entrustIdMap.get(feePosition.getProdid())){
//            //覆盖费用数据
//            BeanUtil.copyPropertiesIgnoreNull(detail,feePosition);
//        }
//    }

    /**
     * 批量入库方法
     * @param prdPositionList
     * @return
     */
    public int insertBatch(List<PbcMonthReportDetailProd> prdPositionList, String term){
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertCount;
        //同批次的详情数据删除
        pbcMonthReportDetailProdMapper.deleteDetail(term);

        //插入list大小
        int dataCount = prdPositionList.size();
        //若需要插入的数据比最大插入的数据少，则直接全部插入
        if (dataCount <= maxDataCountOneTime) {
            dataCount = pbcMonthReportDetailProdMapper.insertBatch(prdPositionList);
            return dataCount;
        }

        List<PbcMonthReportDetailProd> newDataList = new ArrayList<PbcMonthReportDetailProd>();
        newDataList.addAll(prdPositionList);

        // 分批存入
        int part = dataCount / maxDataCountOneTime; // 分批数
        int insertCount = 0;
        for (int i = 0; i < part; i++ ) {
            insertCount += pbcMonthReportDetailProdMapper.insertBatch(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            insertCount += pbcMonthReportDetailProdMapper.insertBatch(newDataList);
        }
        return insertCount;
    }






}
