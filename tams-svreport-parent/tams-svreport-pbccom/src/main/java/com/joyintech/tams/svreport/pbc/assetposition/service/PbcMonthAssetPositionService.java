package com.joyintech.tams.svreport.pbc.assetposition.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.joyintech.tams.busicommon.productRating.service.CommonProductRatingService;
import com.joyintech.tams.constant.DictCodeListExt3;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.entity.ChbAssetInfo;
import com.joyintech.tams.common.base.entity.ChbMstCounterParty;
import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import com.joyintech.tams.common.svreport.base.service.ChbTypeMappingService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.interf.chb.conf.DataSourceType;
import com.joyintech.tams.svreport.interf.chb.investtrade.dao.ChbMstCounterPartyInterfMapper;
import com.joyintech.tams.svreport.pbc.assetposition.constants.PbcAssetPositionConstants;
import com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper;
import com.joyintech.tams.svreport.pbc.assetposition.dto.PbcMonthAssetPositionDto;

/**
 * 功能:人行报表-资产头寸生成
 * @since 1.6.0
 * @version 1.6.0
 * @作者：weiyuqiang
 * @创建时间：2019年3月11日
 * @修改记录：
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(rollbackFor = Exception.class)
public class PbcMonthAssetPositionService  extends BaseService{

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 映射service
     */
    @Autowired
    private ChbTypeMappingService chbTypeMappingService;

    /**
     * 资产头寸mapper
     */
    @Autowired
    private PbcMonthAssetPositionMapper pbcMonthAssetPositionInterfMapper;

    /**
     * 映射配置mapper
     */
    @Autowired
    private ChbTypeMappingExMapper chbTypeMappingExMapper;

    @Autowired
    private SqlSessionTemplate template;

    @Autowired
    private ChbMstCounterPartyInterfMapper mstCounterPartyDtoMapper;

    @Autowired
    private CommonProductRatingService commonProductRatingService;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 功能:接入资产头寸信息
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param batchDate 跑批日
     * @prodList 产品代码
     * @param isSynchronizeTask 是否为轮训批处理
     * @param isEdit 是否是修改的数据
     * void
     */
    public void generateAssetPositionData(Date batchDate,List<String> prodList,List<String> assetList,
                                          boolean isSynchronizeTask,boolean isEdit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(batchDate);
//        //每月1日生成上月的数据
//        if ( calendar.get(Calendar.DATE)!=1 ) {
//            return;
//        }
        // 获取上月的第一天
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date reportStartDate = calendar.getTime();
        // 获取上月的最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date reportEndDate = calendar.getTime();
        logger.debug("上游资产头寸人行报表入库:----开始------" +  df.format(new Date()));
        // 已接入的资产要素信息
        List<ChbAssetInfo> astList = queryAssetInfoBatch();
        Map<String,ChbAssetInfo> assetMap = astList.stream().collect(Collectors.toMap(ChbAssetInfo::getAssetCode, a->a,(k1,k2)->k1));
        // 登记插入列表
        List<PbcMonthAssetPositionDto> insertDataList = new ArrayList<PbcMonthAssetPositionDto>();
        List<PbcMonthAssetPositionDto> assetPositionList = generateAssetPositionBalanceData(reportEndDate,prodList,assetList);
        List<PbcMonthAssetPositionDto> varesultPositionList = genAssetPositionFormValution(reportEndDate,prodList,assetList,assetMap);
        assetPositionList.addAll(varesultPositionList);
        //从费用头寸表获取数据 add by shaohuchen 20200309 start
        List<PbcMonthAssetPositionDto> prdfeePositionList = genPrdfeePositionData(reportEndDate,prodList,assetList,assetMap);
        if(prdfeePositionList !=null && !prdfeePositionList.isEmpty()){
            assetPositionList.addAll(prdfeePositionList);
        }
        //从费用头寸表获取数据 add by shaohuchen 20200309 end
        // 查询上游资产头寸-科目余额
        // 公共数据处理
        if (!CollectionUtils.isEmpty(assetPositionList)){
            // 查询产品代码对应的发行机构
            List<ChbPrdBaseinfo> prodInfoList = queryProdInfoBatch();
            Map<String,String> orgCodeList = new HashMap<String,String>();
            if (!CollectionUtils.isEmpty(prodInfoList)){
                for(ChbPrdBaseinfo prodinfo:prodInfoList){
                    orgCodeList.put(prodinfo.getProdId(), prodinfo.getOrgCode());
                }
            }
            String accountsReceivableEndDaysFlag=SvrDataUtil.getSysParam(PbcAssetPositionConstants.ACCOUNTS_RECEIVABLE_END_DAYS_FLAG);
            for (PbcMonthAssetPositionDto assetPosition: assetPositionList){
                assetPosition.setPosuuid(IDGenerator.generate(36));
//                assetPosition.setPosuuid(IDGenerator.generate(36));
                // 发行机构代码=产品发行机构代码
                assetPosition.setOrgCode(orgCodeList.get(assetPosition.getProdid()));
                // 统计日期=跑批日
                assetPosition.setBookdate(batchDate);
                // 统计开始日
                assetPosition.setStartdate(reportStartDate);
                // 统计结束日
                assetPosition.setEnddate(reportEndDate);
                /*add by gaojian 2019-12-26 begin*/
                // 资产三级分类ASSET_TYPE3=P12, 应付账款剩余期限配置参数为02时, END_DAYS置为0
                if (StringUtil.equals("P12", assetPosition.getAssetType3()) && StringUtil.equals("02", accountsReceivableEndDaysFlag)) {
                    assetPosition.setEndDays(BigDecimal.ZERO);
                }
                /*add by gaojian 2019-12-26 end*/
                insertDataList.add(assetPosition);
            }
        }
        logger.debug("上游资产头寸人行报表先删后插-删除:----开始------" +  df.format(new Date()));
        pbcMonthAssetPositionInterfMapper.deleteByReportDay(reportStartDate,prodList,assetList);
        logger.debug("上游资产头寸人行报表先删后插-删除:----结束------" +  df.format(new Date()));
        logger.debug("上游资产头寸人行报表插入:----开始------" +  df.format(new Date()));
        dealResultCommitSingle(insertDataList,prodList,assetList);
        logger.debug("上游资产头寸人行报表插入:----结束------" +  df.format(new Date()));

        logger.debug("上游资产头寸人行报表入库:----结束------" +  df.format(new Date()));
    }

    /**
     * 功能:获取资产头寸科目余额处理后数据
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param prodList 产品代码
     * @param reportEndDate 结束日期
     * @return
     * List<PbcMonthAssetPositionDto>
     */
    public List<PbcMonthAssetPositionDto> generateAssetPositionBalanceData(Date reportEndDate,List<String> prodList,
                                                                           List<String> assetList){
        List<PbcMonthAssetPositionDto> assetPositionList = new ArrayList<>();
        List<PbcMonthAssetPositionDto> jzAssetPositionList = new ArrayList<>();
        List<String> subjectNoList = new ArrayList<String>();
        //需要做特殊处理的减值的二级科目
        List<String> fSubjectNoList = new ArrayList<String>();
        fSubjectNoList.add(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNZQ);
        fSubjectNoList.add(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNJHN);
        fSubjectNoList.add(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_MRFSZC);
        fSubjectNoList.add(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_CFTY);
        fSubjectNoList.add(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_CHFTY);

        //逾期科目
        //加一个映射
        List<ChbTypeMappingEx>  yqSubjectNoMappingList = chbTypeMappingExMapper
                .selectByMappingcode(PbcAssetPositionConstants.MAPPINGCODE_YQSUBJECTNO);
        List<String> yqSubjectNoList=yqSubjectNoMappingList.stream().map(ChbTypeMappingEx::getObjfield1).collect(Collectors.toList());

        // 查询已配置的科目号
        List<ChbTypeMappingEx>  subjectNoMappingList = chbTypeMappingExMapper
                .selectByMappingcode(PbcAssetPositionConstants.MAPPINGCODE_SUBJECTNO);
       /*
        //去掉费用科目 2205 2206 2207 2208 2210 2211 add by shaohuchen 20200309 start
        Iterator<ChbTypeMappingEx> iterator = subjectNoMappingList.iterator();
        while (iterator.hasNext()){
        	ChbTypeMappingEx mappingEx = iterator.next();
        	String field1 = mappingEx.getObjfield1();
        	if(field1.equals("2205")||field1.equals("2206")||field1.equals("2207")||field1.equals("2208")||field1.equals("2210")||field1.equals("2211")){
        		iterator.remove();
        	}
        }
        //去掉费用科目 2205 2206 2207 2208 2210 2211 add by shaohuchen 20200309 end
*/
        Map<String,ChbTypeMappingEx> assetPositionMap = subjectNoMappingList.stream().collect(Collectors.toMap(ChbTypeMappingEx::getSrcfield1, a -> a,(k1,k2)->k1));
        Map<String,String> oCCyMap=new HashMap<String,String>();
        // 无配置项则不接入
        if (CollectionUtils.isEmpty(subjectNoMappingList)){
            return assetPositionList;
        } else {
            for(ChbTypeMappingEx mapping: subjectNoMappingList){
                // 获取源属性1：科目号
                subjectNoList.add(mapping.getSrcfield1());
                if(mapping.getOrgCode()!=null&&"O".equals(mapping.getOrgCode())){
                    oCCyMap.put(mapping.getSrcfield1(), mapping.getSrcfield1());
                }
            }
        }
        // 如果有资产代码限制则取综合项，即配置科目号和资产代码同时过滤
        // 查询上游数据
        PbcMonthAssetPositionService pbcAssetPositionInterf =  SpringContextUtil.getBean(PbcMonthAssetPositionService.class);

        //202112人行升级 需要特殊处理的减值
        if (DictCodeList.是否_中债_是.getValue().equals(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM))){
            jzAssetPositionList = pbcAssetPositionInterf.queryJZBalanceByInterfdateBatchSubSystem(DateUtil.format(reportEndDate),
                    prodList,assetList,fSubjectNoList,yqSubjectNoList);
        } else if (DictCodeList.是否_中债_否.getValue().equals(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM))){
            jzAssetPositionList = pbcAssetPositionInterf.queryJZBalanceByInterfdateBatch(DateUtil.format(reportEndDate),
                    prodList,assetList,fSubjectNoList,yqSubjectNoList);
        } else {
            logger.error("系统参数中：SVR_IS_SUB_SYSTEM配置错误！");
        }
        if (!CollectionUtils.isEmpty(jzAssetPositionList)){
            for (PbcMonthAssetPositionDto assetPosition: jzAssetPositionList){
                if(oCCyMap.containsKey(assetPosition.getAssetCode())){
                    assetPosition.setCcy(assetPosition.getOccy());
                    assetPosition.setAmt(assetPosition.getOamt());
                }else{
                    assetPosition.setCcy(assetPosition.getBccy());
                    assetPosition.setAmt(assetPosition.getBamt());
                }
                assetPosition.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_ZC);
                if(DictCodeList.是否_中债_否.getValue().equals(assetPosition.getYqFlag())){
                    //未逾期
                    if(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_CFTY.equals(assetPosition.getAssetCode())){
                        //h51000 存款 取上游系统“130209存放同业减值准备”科目余额非逾期资产
                        assetPosition.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P14);
                        assetPosition.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE2_Z01);
                        assetPosition.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE3_Z01);
                    }
                    if(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_MRFSZC.equals(assetPosition.getAssetCode())
                            ||PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_CHFTY.equals(assetPosition.getAssetCode())){
                        //h55000	  回购和拆借	取上游系统“130207买入返售资产减值准备”+“130210拆放同业减值准备”科目余额非逾期资产
                        assetPosition.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P14);
                        assetPosition.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE2_Z05);
                        assetPosition.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE3_Z05);
                    }

                    if(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNZQ.equals(assetPosition.getAssetCode())&&
                            DictCodeListExt3.人行资产一级分类_债务证券.getValue().equals(assetPosition.getAssetType1())){
                        //债务证券 上游系统三级科目为“130201摊余成本类债券减值准备”科目，且四级科目对应明细资产的人行资产一级分类为“债务证券”
                        assetPosition.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P14);
                        assetPosition.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE2_Z03);
                        assetPosition.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE3_Z03);
                    }
                    if((PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNZQ.equals(assetPosition.getAssetCode())||PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNJHN.equals(assetPosition.getAssetCode()))
                            && DictCodeListExt3.人行资产二级分类_除回购或拆借外贷款.getValue().equals(assetPosition.getAssetType2()) ){
                        //除回购和拆借外贷款
                        //取上游系统三级科目为“130201摊余成本类债券减值准备”或“130203 摊余成本类计划类资产减值准备”科目，且四级科目对应明细资产的人行资产二级分类为“除回购和拆借外贷款”
                        assetPosition.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P14);
                        assetPosition.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE2_Z04);
                        assetPosition.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE3_Z04);
                    }
                    if(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNJHN.equals(assetPosition.getAssetCode())&&
                            DictCodeListExt3.人行资产一级分类_资产收益权.getValue().equals(assetPosition.getAssetType1())){
                        //资产收益权
                        //取上游系统三级科目为 “130203 摊余成本类计划类资产减值准备”科目，且四级科目对应明细资产的人行资产一级分类为“资产收益权”
                        assetPosition.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P14);
                        assetPosition.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE2_Z06);
                        assetPosition.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE3_Z06);
                    }
                    if(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNJHN.equals(assetPosition.getAssetCode())&&
                            DictCodeListExt3.人行资产一级分类_股权及特定目的载体份额.getValue().equals(assetPosition.getAssetType1())){
                        //股权及特定目的载体份额
                        //取上游系统三级科目为 “130203 摊余成本类计划类资产减值准备”科目，且四级科目对应明细资产的人行资产一级分类为“股权及特定目的载体份额”
                        assetPosition.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P14);
                        assetPosition.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE2_Z07);
                        assetPosition.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE3_Z07);
                    }
                }else{
                    //逾期
                    if(PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNZQ.equals(assetPosition.getAssetCode())
                            ||PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_TYCBNJHN.equals(assetPosition.getAssetCode())
                            ||PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_MRFSZC.equals(assetPosition.getAssetCode())
                            ||PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_CFTY.equals(assetPosition.getAssetCode())
                            ||PbcAssetPositionConstants.JZ_SUBJECTNO_TWO_CHFTY.equals(assetPosition.getAssetCode())
                    ){
                        //应收账款
                        //取上游系统二级科目为 “130203 摊余成本类计划类资产减值准备”、“130201 摊余成本类债券减值准备”科目、
                        // “130207 买入返售资产减值准备”、“130209 存放同业减值准备”、“130210 拆放同业减值准备”科目，对应的所以三级科目且三级对应且四级科目对应明细资产的已逾期
                        assetPosition.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P14);
                        assetPosition.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE2_Z09);
                        assetPosition.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE3_Z09);
                    }
                }



            }

        }

        // 根据开关判断是否走中债表中取值还是走一体化上游表中取值
        if (DictCodeList.是否_中债_是.getValue().equals(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM))){
            assetPositionList = pbcAssetPositionInterf.queryBalanceByInterfdateBatchSubSystem(DateUtil.format(reportEndDate),
                    prodList,assetList,subjectNoList);
        } else if (DictCodeList.是否_中债_否.getValue().equals(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM))){
            assetPositionList = pbcAssetPositionInterf.queryBalanceByInterfdateBatch(DateUtil.format(reportEndDate),
                    prodList,assetList,subjectNoList);
        } else {
            logger.error("系统参数中：SVR_IS_SUB_SYSTEM配置错误！");
            return assetPositionList;
        }

        if (!CollectionUtils.isEmpty(assetPositionList)){
            // 查询映射配置:支持前台配置
            // 配置项如下：映射代码MAPPINGCODE = PBC_ASSETPOSITION_BALANCE_TYPE (人行资产头寸_科目余额_资产类型类型)
            // 源属性1          源属性2         源属性3                          目标属性1           目标属性2               目标属性3           目标属性4
            // 资产代码          币种               借贷方向                        资产一级分类      资产二级分类          资产三级分类      期限种类
            // 其中:
            // 资产代码=上游科目余额表.科目号
            // 资产一级分类、资产二级分类、资产三级分类、期限种类根据人行详细设计映射
            // 期限种类：TERM_TYPE_ONE-期限品种固定值(1);TERM_TYPE_PROD-期限品种取产品剩余期限;TERM_TYPE_NULL-期限品种固定值(0)
            // 数据处理
            for (PbcMonthAssetPositionDto assetPosition: assetPositionList){
                if(oCCyMap.containsKey(assetPosition.getAssetCode())){
                    assetPosition.setCcy(assetPosition.getOccy());
                    assetPosition.setAmt(assetPosition.getOamt());
                }else{
                    assetPosition.setCcy(assetPosition.getBccy());
                    assetPosition.setAmt(assetPosition.getBamt());
                }
                String ccy = assetPosition.getCcy()==null?"":assetPosition.getCcy();
                // 映射转换非人民币
                if (!DictCodeListExt1.币种_中债_人民币.getValue().equals(ccy)){
                    ccy = PbcAssetPositionConstants.CCY_OTH;
                }
                // 获取配置的映射
                ChbTypeMappingEx oMappingEx = assetPositionMap.get(assetPosition.getAssetCode());
                ChbTypeMappingEx mappingEx = new ChbTypeMappingEx();
                // 根据配置的映射关系判别
                // 现金
                if (PbcAssetPositionConstants.PBC_ASSETTYPE1_P01.equals(oMappingEx.getObjfield1())){
                    mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_SUBJECTNO,
                            assetPosition.getAssetCode());
                } else if (PbcAssetPositionConstants.PBC_ASSETTYPE1_P08.equals(oMappingEx.getObjfield1())){// 金融衍生品
                    mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_SUBJECTNO,
                            assetPosition.getAssetCode(),ccy);
                } else if (PbcAssetPositionConstants.PBC_ASSETTYPE1_P11.equals(oMappingEx.getObjfield1())
                        || PbcAssetPositionConstants.PBC_ASSETTYPE1_P12.equals(oMappingEx.getObjfield1())){// 应收账款\应付账款
                    mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_SUBJECTNO,
                            assetPosition.getAssetCode(),assetPosition.getBalFlag()==null?"":assetPosition.getBalFlag());
                } else if(PbcAssetPositionConstants.PBC_ASSETTYPE1_P14.equals(oMappingEx.getObjfield1())){//减值
                    mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_SUBJECTNO,
                            assetPosition.getAssetCode());
                }
                else {// 权益类
                    mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_SUBJECTNO,
                            assetPosition.getAssetCode());
                }
                // 根据科目号映射配置资产一级分类、资产二级分类、资产三级分类
                // 有科目配置项,则根据配置型处理资产分类、剩余期限、资产类型、
                if (mappingEx != null){
                    // uuid
                    assetPosition.setPosuuid(IDGenerator.generate(36));
                    // 资产一级分类
                    // 资本公积金和未分配理论一级类型为空，但是映射表添加值方便后续查询使用
                    if (PbcAssetPositionConstants.PBC_ASSETTYPE1_WFPLR.equals(mappingEx.getObjfield1())
                            || PbcAssetPositionConstants.PBC_ASSETTYPE1_ZBGJJ.equals(mappingEx.getObjfield1())){
                        assetPosition.setAssetType1(null);
                    } else {
                        assetPosition.setAssetType1(mappingEx.getObjfield1());
                    }
                    // 资产二级分类
                    assetPosition.setAssetType2(mappingEx.getObjfield2());
                    // 资产三级分类
                    assetPosition.setAssetType3(mappingEx.getObjfield3());
                    // 期限种类：TERM_TYPE_ONE-期限品种固定值(1);TERM_TYPE_PROD-期限品种取产品剩余期限;TERM_TYPE_NULL-期限品种固定值(0)
                    String termType = mappingEx.getObjfield4();
                    // 根据期限种类处理剩余期限
                    // 期限品种固定值(1)
                    if (PbcAssetPositionConstants.TERM_TYPE_ONE.equals(termType)){
                        assetPosition.setEndDays(assetPosition.getEndDaysOne());
                    } else if (PbcAssetPositionConstants.TERM_TYPE_PROD.equals(termType)){
                        assetPosition.setEndDays(assetPosition.getEndDaysProd());
                    } else if (PbcAssetPositionConstants.TERM_TYPE_NULL.equals(termType)){
                        assetPosition.setEndDays(assetPosition.getEndDaysNull());
                    } else {
                        // 没有设置期限则默认为0
                        assetPosition.setEndDays(BigDecimal.ZERO);
                        logger.error("映射配置中Objfield4（期限品种）不正确！");
                    }
                    // 处理剩余期限<0 则直接取0
                    if (BigDecimal.ZERO.compareTo(assetPosition.getEndDays()) > 0){
                        assetPosition.setEndDays(BigDecimal.ZERO);
                    }
                    // 根据资产一级分类处理头寸类型,默认头寸类型为科目余额
                    if (PbcAssetPositionConstants.PBC_ASSETTYPE1_P01.equals(assetPosition.getAssetType1())){
                        // 现金直接统计为资产
                        assetPosition.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_ZC);
                    } else if (PbcAssetPositionConstants.PBC_ASSETTYPE1_WFPLR.equals(mappingEx.getObjfield1())
                            || PbcAssetPositionConstants.PBC_ASSETTYPE1_ZBGJJ.equals(mappingEx.getObjfield1())
                            || PbcAssetPositionConstants.PBC_ASSETTYPE1_QTZHSY.equals(mappingEx.getObjfield1())){
                        // 资本公积金和未分配利润统计为科目余额
                        assetPosition.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_KMYE);
                    } else {
                        // 金融衍生品、应收账款、应付账款根据借贷方向统计为资产还是负债
                        if (PbcAssetPositionConstants.BLG_TYPE_C.equals(assetPosition.getBalFlag())){
                            assetPosition.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_FZ);
                        } else if (PbcAssetPositionConstants.BLG_TYPE_D.equals(assetPosition.getBalFlag())){
                            assetPosition.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_ZC);
                        } else {
                            logger.error("头寸上游接入借贷方向异常（存值不为C|D）！");
                        }
                    }
                }
            }
        }
        assetPositionList.addAll(jzAssetPositionList);
        return assetPositionList;
    }

    /**
     * 功能:查询产品要素
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param interfDate 跑批日
     * @prodList 产品代码
     * @return
     * List<ChbAssetInfo>
     */
    public List<ChbPrdBaseinfo> queryProdInfoBatch(){
        List<ChbPrdBaseinfo> prodInfoList = new ArrayList<ChbPrdBaseinfo>();
        template.select("com.joyintech.tams.svreport.interf.chb.prodinfo.dao.ProdInfoMapper.selectByModel",
                new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        prodInfoList.add((ChbPrdBaseinfo) resultContext.getResultObject());
                    }
                });
        return prodInfoList;
    }
    /**
     * 功能:查询接入日当天的资产头寸-科目余额部分
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param interfDate 跑批日
     * @prodList 产品代码
     * @return
     * List<ChbAssetInfo>
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    // 切换数据源
    @DataSource(DataSourceType.INTERF)
    public List<PbcMonthAssetPositionDto> queryBalanceByInterfdateBatch(String reportEndDate,List<String> prodList,
                                                                        List<String> assetList,List<String> subjectNoList){
        List<PbcMonthAssetPositionDto> assetPositionList = new ArrayList<PbcMonthAssetPositionDto>();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("reportEndDate", reportEndDate);
        param.put("prodList", prodList);
        param.put("subjectNoList", subjectNoList);
        param.put("assetList", assetList);
        template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectBalanceByEndDateAndProd",
                param,new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        assetPositionList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                    }
                });
        return assetPositionList;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    // 切换数据源
    @DataSource(DataSourceType.INTERF)
    public List<PbcMonthAssetPositionDto> queryJZBalanceByInterfdateBatch(String reportEndDate,List<String> prodList,
                                                                          List<String> assetList,List<String> subjectNoList,List<String> yqSubjectNoList){
        List<PbcMonthAssetPositionDto> jzAssetPositionList = new ArrayList<PbcMonthAssetPositionDto>();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("reportEndDate", reportEndDate);
        param.put("prodList", prodList);
        param.put("subjectNoList", subjectNoList);
        param.put("assetList", assetList);
        param.put("yqSubjectNoList", yqSubjectNoList);
        template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectJZBalanceByEndDateAndProd",
                param,new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        jzAssetPositionList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                    }
                });
        return jzAssetPositionList;
    }

    /**
     * 功能:查询接入日当天的资产头寸-科目余额部分-中债表中取值
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月20日
     * @param interfDate 跑批日
     * @prodList 产品代码
     * @return
     * List<ChbAssetInfo>
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public List<PbcMonthAssetPositionDto> queryBalanceByInterfdateBatchSubSystem(String reportEndDate,List<String> prodList,
                                                                                 List<String> assetList,List<String> subjectNoList){
        List<PbcMonthAssetPositionDto> assetPositionList = new ArrayList<PbcMonthAssetPositionDto>();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("reportEndDate", reportEndDate);
        param.put("prodList", prodList);
        param.put("subjectNoList", subjectNoList);
        param.put("assetList", assetList);
        template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectBalanceByEndDateAndProdSubSystem",
                param,new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        assetPositionList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                    }
                });
        return assetPositionList;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public List<PbcMonthAssetPositionDto> queryJZBalanceByInterfdateBatchSubSystem(String reportEndDate,List<String> prodList,
                                                                                   List<String> assetList,List<String> subjectNoList,List<String> yqSubjectNoList){
        List<PbcMonthAssetPositionDto> assetPositionList = new ArrayList<PbcMonthAssetPositionDto>();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("reportEndDate", reportEndDate);
        param.put("prodList", prodList);
        param.put("subjectNoList", subjectNoList);
        param.put("assetList", assetList);
        param.put("yqSubjectNoList", yqSubjectNoList);
        template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectJZBalanceByEndDateAndProdSubSystem",
                param,new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        assetPositionList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                    }
                });
        return assetPositionList;
    }

    /**
     * 接口从上游资产估值表查询的资产头寸原始数据
     * @param batchDate
     * @param prodList
     * @return 需要处理的原始数据
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
    public List<PbcMonthAssetPositionDto> queryAssetByInterfdateValution(Date batchDate,List<String> prodList,List<String> assetList){
        List<PbcMonthAssetPositionDto> relustList = new ArrayList<PbcMonthAssetPositionDto>();
        PbcMonthAssetPositionDto qmode=new PbcMonthAssetPositionDto();
        qmode.setBookdate(batchDate);
        qmode.setProdids(prodList);
        //读取系统参数通道是否穿透 01穿透 02 不穿透
        String flag="";
        flag= SvrDataUtil.getSysParam(SVReportGlobalConstant.PBC_IS_CHL_BTM);
        if("01".equals(flag)){
            template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectPositionFromValution",
                    qmode,new ResultHandler() {
                        @Override
                        public void handleResult(ResultContext resultContext) {
                            relustList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                        }
                    });
        }else{
            //先查直投的 ChlId="999999"为默认直投的
            qmode.setChlId("999999");
            template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectPositionFromValution",
                    qmode,new ResultHandler() {
                        @Override
                        public void handleResult(ResultContext resultContext) {
                            relustList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                        }
                    });
            //再查询 ChlId ！="999999"的 通道交易
            template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectPositionFromValutionChl",
                    qmode,new ResultHandler() {
                        @Override
                        public void handleResult(ResultContext resultContext) {
                            relustList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                        }
                    });
        }
        return relustList;
    }

    /**
     * 接口从上游资产估值表查询的资产头寸原始数据-中债子系统取值
     * @param batchDate
     * @param prodList
     * @return 需要处理的原始数据
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public List<PbcMonthAssetPositionDto> queryAssetByInterfdateValutionSubSystem(Date batchDate,List<String> prodList,List<String> assetList){
        List<PbcMonthAssetPositionDto> relustList = new ArrayList<PbcMonthAssetPositionDto>();
        PbcMonthAssetPositionDto qmode=new PbcMonthAssetPositionDto();
        qmode.setBookdate(batchDate);
        qmode.setProdids(prodList);
        String isChlBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.PBC_IS_CHL_BTM);
        qmode.setIsChlBtm(isChlBtm);
        qmode.setEnddate(batchDate);

        /*
        // 人行资产头寸:通道穿透报送时，通道剩余现金需作为境内存款资产进行统计，不穿透报送时，不需要  zhangyf 20191023 start
        */

        String principalRatingDimension  = SvrDataUtil.getSysParam(SVReportGlobalConstant.PRINCIPAL_RATING_DIMENSION);
        qmode.setPrincipalRatingDimension(principalRatingDimension);
        // 评级取值规则修改
        qmode.setSysflag(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_SEC_RATING_FLAG));

        // 先定义债项和主体排序sql变量，用以调用共通方法时候使用
        String getProductRatingOrderSqlByZx = null;
        String getProductRatingOrderSqlByZt = null;
        // 如果是中债子系统取评级逻辑不变，如果不是，就调共通方法

        Map<String, Object> productRatingOrderSqlMap = new HashMap<>();
        // 先取债项，再取主体排序sql
        productRatingOrderSqlMap.put("oType","02");
        getProductRatingOrderSqlByZx = commonProductRatingService.getProductRatingOrderSql(productRatingOrderSqlMap);
        productRatingOrderSqlMap.put("oType","01");
        getProductRatingOrderSqlByZt = commonProductRatingService.getProductRatingOrderSql(productRatingOrderSqlMap);
        qmode.setGetProductRatingOrderSqlByZx(getProductRatingOrderSqlByZx);
        qmode.setGetProductRatingOrderSqlByZt(getProductRatingOrderSqlByZt);
        qmode.setGradeDateEnd(DateUtil.format(batchDate,"yyyy-MM-dd"));

        // 无论通道是否穿透报送，通道剩余现金均需作为现金资产进行统计
        template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectPositionFromValutionSubSystem",
                qmode,new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        relustList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                    }
                });

        /*
		// 通道穿透报送时，通道剩余现金需作为境内存款资产进行统计，不穿透报送时，不需要。
		// 读取系统参数通道是否穿透 01穿透 02 不穿透
		String flag = "";
		flag = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_CHL_BTM);
		if ("01".equals(flag)) {
			template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectPositionFromValutionSubSystem",
	                qmode,new ResultHandler() {
	            @Override
	            public void handleResult(ResultContext resultContext) {
	                relustList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
	            }
	        });
		}
		// 人行资产头寸:通道穿透报送时，通道剩余现金需作为境内存款资产进行统计，不穿透报送时，不需要  zhangyf 20191023 end
		*/

        return relustList;
    }

    /**
     * 接口从上游资产估值表查询的资产头寸原始数据
     * @param batchDate
     * @param prodList
     * @return 需要处理的原始数据
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
    public List<PbcMonthAssetPositionDto> queryAssetByInterfdateValutionChl(Date batchDate,List<String> prodList,List<String> assetList){
        List<PbcMonthAssetPositionDto> relustList = new ArrayList<PbcMonthAssetPositionDto>();

        // 通道穿透报送时，通道剩余现金需作为境内存款资产进行统计，不穿透报送时，不需要  zhangyf 20191226 start
        // 读取系统参数通道是否穿透 01穿透 02 不穿透
        String flag = "";
        flag = SvrDataUtil.getSysParam(SVReportGlobalConstant.PBC_IS_CHL_BTM);
        if ("01".equals(flag)) {
            PbcMonthAssetPositionDto qmode=new PbcMonthAssetPositionDto();
            qmode.setBookdate(batchDate);
            qmode.setProdids(prodList);
            // 查询通道剩余现金
            template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectPositionFromCashValutionChl",
                    qmode,new ResultHandler() {
                        @Override
                        public void handleResult(ResultContext resultContext) {
                            relustList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                        }
                    });
        }
        // 通道穿透报送时，通道剩余现金需作为境内存款资产进行统计，不穿透报送时，不需要  zhangyf 20191226 end
        return relustList;
    }

    /**
     * 功能:生成资产头寸
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月13日
     * @param list
     * @return
     * List<PbcMonthAssetPositionDto>
     */
    public List<PbcMonthAssetPositionDto> genAssetPositionFormValution(Date reportEndDate,List<String> prodList,
                                                                       List<String> assetList,Map<String,ChbAssetInfo> assetMap){
        PbcMonthAssetPositionService pbcAssetPositionInterf =  SpringContextUtil.getBean(PbcMonthAssetPositionService.class);
        // 从中债的基础指标表中取值
        List<PbcMonthAssetPositionDto> list = pbcAssetPositionInterf.queryAssetByInterfdateValutionSubSystem(reportEndDate,prodList,assetList);
        // 不是中债子系统统计上游通道剩余现金
        List<PbcMonthAssetPositionDto> chlList = new ArrayList<>();

        /* Hidden by zhangyf 20191029
        if (DictCodeList.是否_中债_否.getValue().equals(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM))){
            chlList =  pbcAssetPositionInterf.queryAssetByInterfdateValutionChl(reportEndDate,prodList,assetList);
            list.addAll(chlList);
        }
        */
        // 读取系统参数通道是否穿透 01穿透 02不穿透;穿透时统计通道剩余现金。BSTOZ-945 add by zhangyf 20191029 start
        // 且：是否中债子系统=否
        if (StringUtil.equals(DictCodeList.是否_中债_是.getValue(), SvrDataUtil.getSysParam(SVReportGlobalConstant.PBC_IS_CHL_BTM))
                && StringUtil.equals(DictCodeList.是否_中债_否.getValue(), SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM))) {
            chlList =  pbcAssetPositionInterf.queryAssetByInterfdateValutionChl(reportEndDate,prodList,assetList);
            list.addAll(chlList);
        }
        // 读取系统参数通道是否穿透 01穿透 02不穿透;穿透时统计通道剩余现金。BSTOZ-945 add by zhangyf 20191029 end

        List<ChbMstCounterParty> allList=mstCounterPartyDtoMapper.selectAllCounterParty();
        Map<String,ChbMstCounterParty> counterPartyMap=new HashMap<String,ChbMstCounterParty>();
        List<PbcMonthAssetPositionDto> aList = new ArrayList<PbcMonthAssetPositionDto>();
        for(ChbMstCounterParty single:allList){
            counterPartyMap.put(single.getCounterId(), single);
        }
        Date   openJdate=DateUtil.parse("9998-12-31");
        for(PbcMonthAssetPositionDto singel:list){
            // 处理剩余期限
            int days=0;
//            if(PbcAssetPositionConstants.BS_ASSETTYPE1_F02.equals(singel.getAssetType1())){
//                days=0;
            /* add by  dongxiangyang  人行资产头寸改造 20210105  start*/
            if(DateUtil.compareDate(singel.getMdate(),openJdate)==0
            ){
                days=1; //开放式基金  统计为一个月以下
                /* add by  dongxiangyang  人行资产头寸改造 20210105  end*/
                //添加判断逻辑.. 资产信息到期日2099-12-31全部是资管计划.直接设置为无固定期限
            }else if ((singel.getMdate() == null&& PbcAssetPositionConstants.PBC_ASSETTYPE1_P07.equals(singel.getAssetType1()))||(null!=singel.getAssetMdate()&&singel.getAssetMdate().compareTo(DateUtil.parse("2099-12-31"))==0)){
                days=0;
            }else{
                days=DateUtil.getBetweenDayNumber(reportEndDate, singel.getMdate());
            }
            if(days<0){
                days=0;
            }
            singel.setEndDays(new BigDecimal(days));
            String bankCode=SvrDataUtil.getSysParam(SVReportGlobalConstant.CUSTOMER_BANK_CODE);
            if(StringUtil.equals(bankCode, "001")){
                // add by 计划类净值型资产的剩余期限算法修正 2019年8月24日17:11:25
                ChbAssetInfo assetInfo = assetMap.get(singel.getAssetCode());
                //IF ORG_ASSET_TYPE1='F04'--计划类净值型资产
                if (DictCodeList.平安资产类型_计划类资产_净值型.getValue().equals(assetInfo.getOrgAssetType1())) {
                    if (StringUtil.isNotEmpty(singel.getMdate()) &&
                            DateUtil.format(singel.getMdate(), DateUtil.DEFAULT_OTHER_FORMAT).startsWith("2099")) {
                        singel.setEndDays(new BigDecimal(30));
                    }else{ //PBC_MONTH_ASSET_POSITION.END_DAYS=MDATE-日期
                        int betweenDays = DateUtil.getBetweenDayNumber(reportEndDate,singel.getMdate());
                        singel.setEndDays(new BigDecimal(betweenDays));
                    }
                }
            }
            //※※※※※※※※此部分代码为废代码.永不会进.资产映射在资产基本信息接入的时候已经映射完毕※※※※※
            if (DictCodeList.是否_中债_是.getValue().equals(singel.isNeedReflect())){
                // 优先级先查资产代码取值映射
                ChbTypeMappingEx mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                        singel.getAssetCode());
                if (mappingEx == null){

                    // 根据配置映射项判别是否带相应属性:三级资产类型
                    if (!StringUtils.isEmpty(singel.getAssetType3()) && chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                            singel.getAssetType1(),singel.getAssetType2(),singel.getAssetType3()) == null){
                        singel.setAssetType3(null);
                    }
                    // 根据配置映射项判别是否带相应属性:二级资产类型
                    if (!StringUtils.isEmpty(singel.getAssetType2()) && chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                            singel.getAssetType1(),singel.getAssetType2(),singel.getAssetType3()) == null){
                        singel.setAssetType2(null);
                    }
                    // 融资人/发行人属性 先取发行人属性再取融资人属性
                    // 行业一级类型10为金融 其他为非金融
                    // 资产要素信息
                    ChbAssetInfo assetInfo = assetMap.get(singel.getAssetCode());
                    String industry = null;
                    if (assetInfo != null){
                        industry = assetInfo.getIndustry();
                        if (StringUtils.isEmpty(industry)){
                            industry = assetInfo.getIndustryPbc();
                        }
                        // 修改资产/负债判别方式 修复质押式回购上游三级类型未存值bug  add by weiyuqiang 2019-04-23 start
                        if ("1902".equals(assetInfo.getAssetType2())
                                || "1901".equals(assetInfo.getAssetType2())){
                            singel.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_FZ);
                        } else {
                            singel.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_ZC);
                        }
                        // 修改资产/负债判别方式 修复质押式回购上游三级类型未存值bug  add by weiyuqiang 2019-04-23 start
                    }
                    if (!StringUtils.isEmpty(industry)){
                        if("10".equals(industry)){
                            industry = "01";
                        } else {
                            industry = "02";
                        }
                    }
                    // 根据配置映射项判别是否带相应属性
                    if (!StringUtils.isEmpty(industry) && chbTypeMappingService.getMappingBySrcField(PbcAssetPositionConstants.PBCASSETTYPE_FAIN,
                            singel.getAssetType1(),singel.getAssetType2(),singel.getAssetType3()) == null){
                        industry = null;
                    }
                    // 收受益权
                    String repprofittype = singel.getRepprofittype();
                    // 根据配置映射项判别是否带相应属性
                    if (!StringUtils.isEmpty(repprofittype) && chbTypeMappingService.getMappingBySrcField(PbcAssetPositionConstants.PBCASSETTYPE_REPP,
                            singel.getAssetType1(),singel.getAssetType2(),singel.getAssetType3()) == null){
                        repprofittype = null;
                    }
                    ChbMstCounterParty  counterinfo=counterPartyMap.get(singel.getCounterid());
                    // 是否为中央交易对手
                    String isCentralCounter = counterinfo==null?null:counterinfo.getIsCentralCounter();
                    //                if(PbcAssetPositionConstants.BS_ASSETTYPE3_C0402.equals(counterType2)){//非银行业金融机构 区分是否中央交易对手
                    //                    isCentralCounter = counterinfo==null?null:counterinfo.getIsCentralCounter();
                    //                }
                    // 根据配置映射项判别是否带相应属性
                    if (!StringUtils.isEmpty(isCentralCounter) && chbTypeMappingService.getMappingBySrcField(PbcAssetPositionConstants.PBCASSETTYPE_CENTER,
                            singel.getAssetType1(),singel.getAssetType2(),singel.getAssetType3()) == null){
                        isCentralCounter = null;
                    }
                    // 交易对手二级类型
                    String counterType2 = counterinfo==null?null:counterinfo.getCounterType2();
                    // 根据配置映射项判别是否带相应属性
                    if (!StringUtils.isEmpty(counterType2) && chbTypeMappingService.getMappingBySrcField(PbcAssetPositionConstants.PBCASSETTYPE_COUNTER,
                            singel.getAssetType1(),singel.getAssetType2(),singel.getAssetType3()) == null){
                        counterType2 = null;
                    }
                    // 境内\境外
                    String blnarea = singel.getBlnarea();
                    //                if (PbcAssetPositionConstants.BS_ASSETTYPE2_D02.equals(singel.getAssetType2())){
                    //                    blnarea = singel.getBlnarea();
                    //                }
                    // 根据配置映射项判别是否带相应属性
                    if (!StringUtils.isEmpty(blnarea) && chbTypeMappingService.getMappingBySrcField(PbcAssetPositionConstants.PBCASSETTYPE_COUNTRY,
                            singel.getAssetType1(),singel.getAssetType2(),singel.getAssetType3()) == null){
                        blnarea = null;
                    }
                    mappingEx = chbTypeMappingService.getMappingBySrcField(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                            singel.getAssetType1(),singel.getAssetType2(),singel.getAssetType3(),industry,repprofittype
                            ,isCentralCounter,counterType2,blnarea);
                }
                //          String sx4 = null;
                //          //拆借和回购映射人行资产类型需要交易对手明细类型
                //          if(PbcAssetPositionConstants.BS_ASSETTYPE1_F07.equals(singel.getAssetType1())||PbcAssetPositionConstants.BS_ASSETTYPE1_F08.equals(singel.getAssetType1())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE1_F10.equals(singel.getAssetType1())){
                //              if(singel.getCounterid()!=null&&!"".equals(singel.getCounterid())){
                //
                //                  ChbMstCounterParty  jydsinfo=counterPartyMap.get(singel.getCounterid());
                //                  sx4=jydsinfo.getCounterType2();
                //                  String iscn=null;
                //                      if(PbcAssetPositionConstants.BS_ASSETTYPE3_C0402.equals(sx4)){//非银行业金融机构 区分是否中央交易对手
                //                          iscn=jydsinfo.getIsCentralCounter();
                //                      }
                //                  // 根据科目号映射配置资产一级分类、资产二级分类、资产三级分类
                //                   mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                          singel.getAssetType1(),sx4,iscn);
                //
                //              }else{
                //                  singel.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P05);
                //                  singel.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE2_R01);
                //                  singel.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE3_L03);
                //              }
                //          }else if(PbcAssetPositionConstants.BS_ASSETTYPE1_F05.equals(singel.getAssetType1())&&PbcAssetPositionConstants.BS_ASSETTYPE2_D02.equals(singel.getAssetType2())){
                //               mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1(),singel.getAssetType2(),
                //                      singel.getBlnarea());
                //          }else if(PbcAssetPositionConstants.BS_ASSETTYPE1_F02.equals(singel.getAssetType1())){
                //               mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1());
                //          }else if(PbcAssetPositionConstants.BS_ASSETTYPE1_F06.equals(singel.getAssetType1())&&PbcAssetPositionConstants.BS_ASSETTYPE2_P01.equals(singel.getAssetType2())
                //                  &&!singel.getAssetType3().equals(PbcAssetPositionConstants.BS_ASSETTYPE3_1205)){
                //               mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1(),singel.getAssetType2(),
                //                      singel.getAssetType3());
                //          }else if(PbcAssetPositionConstants.BS_ASSETTYPE1_F06.equals(singel.getAssetType1())&&PbcAssetPositionConstants.BS_ASSETTYPE3_1205.equals(singel.getAssetType3())){
                //              //收受益权
                //              sx4=singel.getRepprofittype();
                //                   mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1(),singel.getAssetType2(),
                //                      singel.getAssetType3(),sx4);
                //          }else if(PbcAssetPositionConstants.BS_ASSETTYPE1_F06.equals(singel.getAssetType1())&&PbcAssetPositionConstants.BS_ASSETTYPE2_P05.equals(singel.getAssetType2())){
                //              sx4=singel.getIndustry();
                //              //行业一级类型10为金融 其他为非金融
                //              if("10".equals(sx4)){
                //                  sx4="01";
                //              }else{
                //                  sx4="02";
                //              }
                //               mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1(),singel.getAssetType2(),
                //                      "",sx4);
                //          }
                //          else if(PbcAssetPositionConstants.BS_ASSETTYPE1_F01.equals(singel.getAssetType1())&&(
                //                  PbcAssetPositionConstants.BS_ASSETTYPE2_B08.equals(singel.getAssetType2())||PbcAssetPositionConstants.BS_ASSETTYPE2_B09.equals(singel.getAssetType2()))){
                //              sx4=singel.getIndustry();
                //              //行业一级类型10为金融 其他为非金融
                //              if("10".equals(sx4)){
                //                  sx4="01";
                //              }else{
                //                  sx4="02";
                //              }
                //              mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1(),singel.getAssetType2(),
                //                      singel.getAssetType3(),sx4);
                //          }else if(PbcAssetPositionConstants.BS_ASSETTYPE1_F01.equals(singel.getAssetType1())
                //                  &&(PbcAssetPositionConstants.BS_ASSETTYPE2_B05.equals(singel.getAssetType2())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE2_B06.equals(singel.getAssetType2())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE2_B07.equals(singel.getAssetType2())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE2_B13.equals(singel.getAssetType2()))){
                //               mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1(),singel.getAssetType2(),
                //                      singel.getAssetType3());
                //          }else if(PbcAssetPositionConstants.BS_ASSETTYPE1_F01.equals(singel.getAssetType1())
                //                  &&(PbcAssetPositionConstants.BS_ASSETTYPE2_B10.equals(singel.getAssetType2())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE2_B12.equals(singel.getAssetType2())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE2_B14.equals(singel.getAssetType2())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE2_B15.equals(singel.getAssetType2())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE2_B16.equals(singel.getAssetType2())
                //                  ||PbcAssetPositionConstants.BS_ASSETTYPE2_B17.equals(singel.getAssetType2()))){
                //              sx4=singel.getIndustry();
                //              //行业一级类型10为金融 其他为非金融
                //              if("10".equals(sx4)){
                //                  sx4="01";
                //              }else{
                //                  sx4="02";
                //              }
                //               mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1(),singel.getAssetType2(),
                //                      "",sx4);
                //          }else{
                //               mappingEx = chbTypeMappingService.getMapping(PbcAssetPositionConstants.MAPPINGCODE_PBCASSETTYPE,
                //                      singel.getAssetType1(),singel.getAssetType2());
                //          }
                if(mappingEx!=null){
                    singel.setAssetType1(mappingEx.getObjfield1());
                    singel.setAssetType2(mappingEx.getObjfield2());
                    singel.setAssetType3(mappingEx.getObjfield3());
                }
            }
            // 根据清算款生成对应的头寸数据
            PbcMonthAssetPositionDto delayAmtPosition = genePostionByDelayPayAmt(singel);
            if (delayAmtPosition != null){
                aList.add(delayAmtPosition);
            }
            aList.add(singel);
        }
        return aList;
    }

    /**
     * 功能:根据清算款生成一笔头寸数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月10日
     * @param src
     * @return
     * PbcMonthAssetPositionDto
     */
    private PbcMonthAssetPositionDto genePostionByDelayPayAmt(PbcMonthAssetPositionDto src){
        BigDecimal delayPayAmt = BigDecimal.ZERO;
        if (src.getDelayPayAmt() != null){
            delayPayAmt = src.getDelayPayAmt();
        }
        // 清算款为空或者为0不生成单独一笔
        if (BigDecimal.ZERO.compareTo(delayPayAmt) == 0){
            return null;
        }
        PbcMonthAssetPositionDto dest = new PbcMonthAssetPositionDto();
        try {
            dest = (PbcMonthAssetPositionDto) src.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("拷贝对象失败！"+e.getMessage(),e);
        }
        // 清算款为正则计入应收款项，负值计入应付款项
        // 资产一级类型=资产二级类型=资产三级类型=P11 应收账款，类型=01 资产；否则为P12 应付账款，类型=02 负债
        if (BigDecimal.ZERO.compareTo(delayPayAmt) < 0){
            // 类型=01 资产
            dest.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_ZC);
            // 资产一级类型=资产二级类型=资产三级类型=P11 应收账款
            dest.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P11);
            // 资产一级类型=资产二级类型=资产三级类型=P11 应收账款
            dest.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE1_P11);
            // 资产一级类型=资产二级类型=资产三级类型=P11 应收账款
            dest.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE1_P11);
        } else{
            // 类型=02 负债
            dest.setPositionType(PbcAssetPositionConstants.POSITION_TYPE_FZ);
            // 资产一级类型=资产二级类型=资产三级类型=P12 应付账款
            dest.setAssetType1(PbcAssetPositionConstants.PBC_ASSETTYPE1_P12);
            // 资产一级类型=资产二级类型=资产三级类型=P12 应付账款
            dest.setAssetType2(PbcAssetPositionConstants.PBC_ASSETTYPE1_P12);
            // 资产一级类型=资产二级类型=资产三级类型=P12 应付账款
            dest.setAssetType3(PbcAssetPositionConstants.PBC_ASSETTYPE1_P12);

        }
        // 金额字段为正值
        dest.setAmt(delayPayAmt.abs());
        // 利息值为0
        dest.setIntAmt(BigDecimal.ZERO);
        return dest;
    }
    /**
     * 功能:带提交的方式单条处理数据结果，入库
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月7日
     * @param assetPositionList
     * @Param reportDay
     * void
     */
    private void dealResultCommitSingle(List<PbcMonthAssetPositionDto> assetPositionList,
                                        List<String> prodList,List<String> assetList){
        if (CollectionUtils.isNotEmpty(assetPositionList)) {
            logger.debug("上游资产头寸人行报表先删后插-插入:----开始------" +  df.format(new Date()));
            int count = 0;
            SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            try{
                PbcMonthAssetPositionMapper batchMapper =  session.getMapper(PbcMonthAssetPositionMapper.class);
                for(PbcMonthAssetPositionDto assetPosition : assetPositionList){
                    batchMapper.insert(assetPosition);
                    count++;
                    if(count%1000==0){
                        session.commit();
                        // 清理缓存，防止溢出
                        session.clearCache();
                        logger.debug("上游资产头寸人行报表先删后插-插入1000条第"+ count/1000 +"次:----结束------" +  df.format(new Date()));
                    }
                }
                session.commit();
                // 清理缓存，防止溢出
                session.clearCache();
            }
//            catch(Exception e){
//                session.rollback();
//                throw e;
//            }
            finally {
                session.close();
            }
            logger.debug("上游资产头寸人行报表先删后插-插入:----结束------" +  df.format(new Date()));
        }
    }
    /**
     * 功能:查询资产要素
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param interfDate 跑批日
     * @return
     * List<AssetInfoDto>
     */
    public List<ChbAssetInfo> queryAssetInfoBatch(){
        List<ChbAssetInfo> assetInfoList = new ArrayList<ChbAssetInfo>();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("status", DictCodeListExt1.维护数据状态_有效.getValue());
        template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectAssetByStatus",
                param,new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        assetInfoList.add((ChbAssetInfo) resultContext.getResultObject());
                    }
                });
        return assetInfoList;
    }


    /**获取费用头寸数据生成资产头寸
     * @param reportEndDate
     * @param prodList
     * @param assetList
     * @param assetMap
     * @return
     */
    public List<PbcMonthAssetPositionDto> genPrdfeePositionData(Date reportEndDate,List<String> prodList,
                                                                List<String> assetList,Map<String,ChbAssetInfo> assetMap){

        List<PbcMonthAssetPositionDto> assetPositionList = new ArrayList<>();
        PbcMonthAssetPositionService pbcAssetPositionInterf =  SpringContextUtil.getBean(PbcMonthAssetPositionService.class);
        assetPositionList= pbcAssetPositionInterf.queryPrdfeeData(DateUtil.format(reportEndDate),
                prodList,assetList);

        // 处理剩余期限,获取应付账款剩余期限配置参数
        String accountsReceivableEndDaysFlag=SvrDataUtil.getSysParam(PbcAssetPositionConstants.ACCOUNTS_RECEIVABLE_END_DAYS_FLAG);
        for(PbcMonthAssetPositionDto singel:assetPositionList){
            int days=0;
            // 应付账款剩余期限配置参数为02时, END_DAYS置为0
            if (StringUtil.equals("02", accountsReceivableEndDaysFlag)) {
                singel.setEndDays(BigDecimal.ZERO);
            }else{
                days=DateUtil.getBetweenDayNumber(reportEndDate, singel.getPayDate());
            }
            if(days<=0){
                days=1;
            }
            singel.setEndDays(new BigDecimal(days));
        }
        return assetPositionList;
    }


    /**从费用头寸表获取数据
     * @param reportEndDate
     * @param prodList
     * @param assetList
     * @return assetPositionList
     */
    public List<PbcMonthAssetPositionDto> queryPrdfeeData(String reportEndDate,List<String> prodList, List<String> assetList){
        List<PbcMonthAssetPositionDto> assetPositionList = new ArrayList<PbcMonthAssetPositionDto>();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("reportEndDate", reportEndDate);
        param.put("prodList", prodList);
        //param.put("subjectNoList", subjectNoList);
        param.put("assetList", assetList);
        template.select("com.joyintech.tams.svreport.pbc.assetposition.dao.PbcMonthAssetPositionMapper.selectPrdfeeByEndDateAndProd",
                param,new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        assetPositionList.add((PbcMonthAssetPositionDto) resultContext.getResultObject());
                    }
                });
        return assetPositionList;
    }

}
