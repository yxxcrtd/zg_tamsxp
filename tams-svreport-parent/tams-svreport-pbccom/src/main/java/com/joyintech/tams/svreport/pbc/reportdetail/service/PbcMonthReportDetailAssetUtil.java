package com.joyintech.tams.svreport.pbc.reportdetail.service;

import com.joyintech.background.component.common.pack.common.util.BeanUtil;
import com.joyintech.background.component.common.pack.common.util.IDGenerator;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.tams.common.base.dao.ChbPrdBaseinfoMapper;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.*;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.pbc.assetposition.dto.PbcMonthAssetPositionDto;
import com.joyintech.tams.svreport.pbc.common.constants.PbcMonthReportConstants;
import com.joyintech.tams.svreport.pbc.common.constants.PbcReportProperycode;
import com.joyintech.tams.svreport.pbc.prod.dto.PbcMonthPrdPostionDto;
import com.joyintech.tams.svreport.pbc.reportdetail.dao.PbcMonthReportDetailAssetMapper;
import com.joyintech.tams.svreport.pbc.reportdetail.dto.PbcMonthReportDetailAsset;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class PbcMonthReportDetailAssetUtil {

    @Autowired
    private PbcMonthReportDetailAssetMapper pbcMonthReportDetailAssetMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 数据批量一次性insert条数
     */
    @Value("${application.reportInsertCount}")
    public int reportInsertCount;
    /***特殊的要获取科目*/
    private static List<String> existsCode = Arrays.asList(PbcReportProperycode.AMT_710000_XJ,PbcReportProperycode.AMT_790000_YSZK,PbcReportProperycode.AMT_7A0000_HJ
            ,PbcReportProperycode.AMT_7B0000_FJRZC,PbcReportProperycode.AMT_822000_ZBGJ,PbcReportProperycode.AMT_823000_WFPLR);


    /***4-1特殊的要获取科目*/
    private static List<String> existsCode3 = Arrays.asList(PbcReportProperycode.AMT_H21000_QZQYZ,PbcReportProperycode.AMT_H22000_GSZ,PbcReportProperycode.AMT_H23000_FJRQYZW
            ,PbcReportProperycode.AMT_H51000_CK,PbcReportProperycode.AMT_H52000_KZRCD,PbcReportProperycode.AMT_H53000_ZWZQ,PbcReportProperycode.AMT_H54000_CHGHCJWDK
            ,PbcReportProperycode.AMT_H55000_HGHCJ,PbcReportProperycode.AMT_H56000_ZCSYQ,PbcReportProperycode.AMT_H57000_GQJTDMDDZTFE,PbcReportProperycode.AMT_H58000_JRYSP
            ,PbcReportProperycode.AMT_H59000_YSZK,PbcReportProperycode.AMT_H5A000_QT);

    /***产品端屏蔽科目*/
    private static List<String> notExistsPrdCode = Arrays.asList(PbcReportProperycode.AMT_821200_LHJ,PbcReportProperycode.AMT_821000_SSBJ,PbcReportProperycode.AMT_820000_QYHJ
            ,PbcReportProperycode.AMT_800000_FZJQYHJ,PbcReportProperycode.AMT_821100_YXJ,PbcReportProperycode.AMT_821270_TDMDZT,PbcReportProperycode.AMT_821170_TDMDZT);
    /***资产端屏蔽科目*/
    private static List<String> notExistsCode = Arrays.asList(PbcReportProperycode.AMT_772000_TDMDZTFE,PbcReportProperycode.AMT_752000_HGHCJ,
            PbcReportProperycode.AMT_761000_DKSYQ,  PbcReportProperycode.AMT_763000_YSZKSYQ, PbcReportProperycode.AMT_764000_QTZQSYQ,PbcReportProperycode.AMT_771000_GP,
            PbcReportProperycode.AMT_767000_WQZCSYQ,PbcReportProperycode.AMT_766000_QTGQSYQ,PbcReportProperycode.AMT_765000_GPSYQ,PbcReportProperycode.AMT_764000_QTZQSYQ,
            PbcReportProperycode.AMT_763000_YSZKSYQ,PbcReportProperycode.AMT_762000_ZWZQSYQ,PbcReportProperycode.AMT_761000_DKSYQ,PbcReportProperycode.AMT_811200_HGHCJ,
            PbcReportProperycode.AMT_811000_DK,PbcReportProperycode.AMT_773000_QTGQ,PbcReportProperycode.AMT_811270_TDMDZT,PbcReportProperycode.AMT_752700_TDMDZT,
            PbcReportProperycode.AMT_811260_FYXYJRJG,PbcReportProperycode.AMT_721000_JNCK,PbcReportProperycode.AMT_722000_JWCK,PbcReportProperycode.AMT_745000_QYZQ
            ,PbcReportProperycode.AMT_821000_SSBJ);


    private static final Map<String, String> ENTERPRISE_SCALE_MAP = new HashMap<String, String>(1 << 4);
    static {
        // CS01-大型 统计到【大型企业】项中
        ENTERPRISE_SCALE_MAP.put("CS01", "02");
        // CS02-中型 统计到【中型企业】项中
        ENTERPRISE_SCALE_MAP.put("CS02", "01");
        // CS03-小型 统计到【小型企业】项中
        ENTERPRISE_SCALE_MAP.put("CS03", "03");
        // CS04-微型 统计到【微型企业】项中
        ENTERPRISE_SCALE_MAP.put("CS04", "04");
        // CS05-其他 统计到【中型企业】项中
        ENTERPRISE_SCALE_MAP.put("CS05", "01");
    }



    private static Map<String,ChbPrdBaseinfo> prodInfoMap;

    private static Map<String,ChbAssetInfo> assetInfoMap;

    private static  Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

    /**
     * 2-1入库处理 -产品
     * @param position
     * @return
     */
    public static PbcMonthReportDetailAsset buildAssetDetailPrd(PbcMonthPrdPostionDto position, Object dto, String code,BigDecimal amt){

        if(null==prodInfoMap){
            /***获取产品信息列表**/
            ChbPrdBaseinfoMapper chbPrdBaseinfoMapper =  SpringContextUtil.getBean(ChbPrdBaseinfoMapper.class);
            List<ChbPrdBaseinfo> prdBaseinfoList = chbPrdBaseinfoMapper.selectAllProd(null);
            prodInfoMap = null!=prdBaseinfoList?prdBaseinfoList.stream().collect(Collectors.toMap(p->p.getProdId(), p-> p,(key1,key2)->key1)):new HashMap<>();
        }
        PbcMonthReportDetailAsset pbcMonthReportDetailAsset = new PbcMonthReportDetailAsset();
        //相同内容字段直接赋值
        if(null!=prodInfoMap.get(position.getProdid())){
            BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,prodInfoMap.get(position.getProdid()));
        }

        //先基本信息,再头寸信息,避免错误覆盖
        BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,position);

        //金额丢给最后一起改.资产端也要
        pbcMonthReportDetailAsset.setAmt(amt);
        pbcMonthReportDetailAsset.setCcy(position.getCcy());
        BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,dto);
        //资产端绑定编号
        pbcMonthReportDetailAsset.setDetailno(code);

        //期次赋值
        pbcMonthReportDetailAsset.setTerm(DateUtil.format(position.getEnddate(),"yyyyMM"));

        return pbcMonthReportDetailAsset;
    }


    /**
     * wfplr特殊处理
     * @param position
     * @return
     */
    public static PbcMonthReportDetailAsset buildAssetDetailwfplr(String code,BigDecimal amt,Date endDate){
        PbcMonthReportDetailAsset pbcMonthReportDetailAsset = new PbcMonthReportDetailAsset();
        //资产端绑定编号
        pbcMonthReportDetailAsset.setDetailno(code);
        pbcMonthReportDetailAsset.setAmt(amt);
        pbcMonthReportDetailAsset.setCcy(DictCodeListExt1.币种_中债_人民币.getValue());
        //期次赋值
        pbcMonthReportDetailAsset.setTerm(DateUtil.format(DateUtil.addDay(endDate,-1),"yyyyMM"));

        return pbcMonthReportDetailAsset;
    }



    /**
     * 2-1入库处理-资产
     * @param position
     * @return
     */
    public static PbcMonthReportDetailAsset buildAssetDetail(PbcMonthAssetPositionDto position,Object dto,String code,String amtFlag){

        if(null==assetInfoMap){
            PbcMonthReportDetailAssetMapper detailAssetMapper =  SpringContextUtil.getBean(PbcMonthReportDetailAssetMapper.class);
            List<ChbAssetInfo> assetInfoList = detailAssetMapper.selectChbAssetInfoByAssetCodeList(null);
            assetInfoMap = null!=assetInfoList?assetInfoList.stream().collect(Collectors.toMap(p->p.getAssetCode(), p-> p,(key1, key2)->key1)):new HashMap<>();
        }
        if(null==prodInfoMap){
            /***获取产品信息列表**/
            ChbPrdBaseinfoMapper chbPrdBaseinfoMapper =  SpringContextUtil.getBean(ChbPrdBaseinfoMapper.class);
            List<ChbPrdBaseinfo> prdBaseinfoList = chbPrdBaseinfoMapper.selectAllProd(null);
            prodInfoMap = null!=prdBaseinfoList?prdBaseinfoList.stream().collect(Collectors.toMap(p->p.getProdId(), p-> p,(key1,key2)->key1)):new HashMap<>();
        }
        PbcMonthReportDetailAsset pbcMonthReportDetailAsset = new PbcMonthReportDetailAsset();
        //相同内容字段直接赋值
        if(null!=prodInfoMap.get(position.getProdid())){
            BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,prodInfoMap.get(position.getProdid()));
        }
        if(null!=assetInfoMap.get(position.getAssetCode())) {
            ChbAssetInfo assetInfo = assetInfoMap.get(position.getAssetCode());
            BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset, assetInfo);

            if(DictCodeList.是否_中债_是.getValue().equals(assetInfo.getOptionFlag())){
                pbcMonthReportDetailAsset.setOpenplan(assetInfo.getExerciseDate());
            }
            pbcMonthReportDetailAsset.setSubjectName(SubjectName(assetInfo,pbcMonthReportDetailAsset.getOrgAssetType2(),pbcMonthReportDetailAsset.getOrgAssetType1()));

            //2-5取值规则
            pbcMonthReportDetailAsset.setPartyType1(null);
            pbcMonthReportDetailAsset.setIndustry(null);

            if(position.getAssetType3()!=null&&PbcMonthReportConstants.PBC_ASSETTYPE3_S05.equals(position.getAssetType3())) {
                String industryType1 = assetInfo.getIndustry();
                if (StringUtils.isEmpty(industryType1)){
                    industryType1 = assetInfo.getIndustryPbc();
                }
                String partyType1 = PbcMonthReportConstants.SVR_INSTIT_SIZE_01;
                if(null!=assetInfo.getInstitSize()){
                   if(null!=assetInfo.getInstitSize()){
                       switch (assetInfo.getInstitSize()){
                           case PbcMonthReportConstants.SVR_INSTIT_SIZE_01:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_01; break;
                           case PbcMonthReportConstants.SVR_INSTIT_SIZE_02:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_02; break;
                           case PbcMonthReportConstants.SVR_INSTIT_SIZE_03:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_03; break;
                           case PbcMonthReportConstants.SVR_INSTIT_SIZE_04:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_04; break;
                       }
                   }
               }
                pbcMonthReportDetailAsset.setPartyType1(partyType1);
                pbcMonthReportDetailAsset.setIndustry(industryType1);
            }
        }
        //先基本信息,再头寸信息,避免错误覆盖
        BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,position);

        BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,dto);
        //资产端绑定编号
        pbcMonthReportDetailAsset.setDetailno(code);

        BigDecimal amt = position.getAmt();
        if(StringUtil.isNotEmpty(amtFlag)){
            amt = position.getIntAmt();
        }
        pbcMonthReportDetailAsset.setAmt(amt);

        pbcMonthReportDetailAsset.setChlId(amtFlag);


        //期次赋值
        pbcMonthReportDetailAsset.setTerm(DateUtil.format(position.getEnddate(),"yyyyMM"));

        return pbcMonthReportDetailAsset;
    }





    /**
     * 4-1入库处理-资产
     * @param position
     * @return
     */
    public static PbcMonthReportDetailAsset buildAssetDetail3(PbcMonthAssetPositionDto position,Object dto,String code,String amtFlag){

        if(null==assetInfoMap){
            PbcMonthReportDetailAssetMapper detailAssetMapper =  SpringContextUtil.getBean(PbcMonthReportDetailAssetMapper.class);
            List<ChbAssetInfo> assetInfoList = detailAssetMapper.selectChbAssetInfoByAssetCodeList(null);
            assetInfoMap = null!=assetInfoList?assetInfoList.stream().collect(Collectors.toMap(p->p.getAssetCode(), p-> p,(key1, key2)->key1)):new HashMap<>();
        }
        if(null==prodInfoMap){
            /***获取产品信息列表**/
            ChbPrdBaseinfoMapper chbPrdBaseinfoMapper =  SpringContextUtil.getBean(ChbPrdBaseinfoMapper.class);
            List<ChbPrdBaseinfo> prdBaseinfoList = chbPrdBaseinfoMapper.selectAllProd(null);
            prodInfoMap = null!=prdBaseinfoList?prdBaseinfoList.stream().collect(Collectors.toMap(p->p.getProdId(), p-> p,(key1,key2)->key1)):new HashMap<>();
        }
        PbcMonthReportDetailAsset pbcMonthReportDetailAsset = new PbcMonthReportDetailAsset();
        //相同内容字段直接赋值
        if(null!=prodInfoMap.get(position.getProdid())){
            BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,prodInfoMap.get(position.getProdid()));
        }
        if(null!=assetInfoMap.get(position.getAssetCode())) {
            ChbAssetInfo assetInfo = assetInfoMap.get(position.getAssetCode());
            BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset, assetInfo);

            if(DictCodeList.是否_中债_是.getValue().equals(assetInfo.getOptionFlag())){
                pbcMonthReportDetailAsset.setOpenplan(assetInfo.getExerciseDate());
            }
            pbcMonthReportDetailAsset.setSubjectName(SubjectName(assetInfo,pbcMonthReportDetailAsset.getOrgAssetType2(),pbcMonthReportDetailAsset.getOrgAssetType1()));

            //2-5取值规则
            pbcMonthReportDetailAsset.setPartyType1(null);
            pbcMonthReportDetailAsset.setIndustry(null);

            if(position.getAssetType3()!=null&&PbcMonthReportConstants.PBC_ASSETTYPE3_S05.equals(position.getAssetType3())) {
                String industryType1 = assetInfo.getIndustry();
                if (StringUtils.isEmpty(industryType1)){
                    industryType1 = assetInfo.getIndustryPbc();
                }
                String partyType1 = PbcMonthReportConstants.SVR_INSTIT_SIZE_01;
                if(null!=assetInfo.getInstitSize()){
                    if(null!=assetInfo.getInstitSize()){
                        switch (assetInfo.getInstitSize()){
                            case PbcMonthReportConstants.SVR_INSTIT_SIZE_01:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_01; break;
                            case PbcMonthReportConstants.SVR_INSTIT_SIZE_02:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_02; break;
                            case PbcMonthReportConstants.SVR_INSTIT_SIZE_03:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_03; break;
                            case PbcMonthReportConstants.SVR_INSTIT_SIZE_04:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_04; break;
                        }
                    }
                }
                pbcMonthReportDetailAsset.setPartyType1(partyType1);
                pbcMonthReportDetailAsset.setIndustry(industryType1);
            }
        }
        //先基本信息,再头寸信息,避免错误覆盖
        BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,position);

        BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,dto);
        //资产端绑定编号
        pbcMonthReportDetailAsset.setDetailno(code);

        BigDecimal amt = position.getAmt();
        if(StringUtil.isNotEmpty(amtFlag)){
            amt = position.getIntAmt();
        }
        pbcMonthReportDetailAsset.setAmt(amt);

        pbcMonthReportDetailAsset.setChlId(amtFlag);


        //期次赋值
        pbcMonthReportDetailAsset.setTerm(DateUtil.format(position.getEnddate(),"yyyyMM"));

        return pbcMonthReportDetailAsset;
    }

    /**
     * 2-4数据特殊处理
     * @param position
     * @return
     */
    public static PbcMonthReportDetailAsset buildAssetDetail2(PbcMonthAssetPositionDto position,Object dto,String code,String code2){

        if(null==assetInfoMap){
            PbcMonthReportDetailAssetMapper detailAssetMapper =  SpringContextUtil.getBean(PbcMonthReportDetailAssetMapper.class);
            List<ChbAssetInfo> assetInfoList = detailAssetMapper.selectChbAssetInfoByAssetCodeList(null);
            assetInfoMap = null!=assetInfoList?assetInfoList.stream().collect(Collectors.toMap(p->p.getAssetCode(), p-> p,(key1, key2)->key1)):new HashMap<>();
        }
        if(null==prodInfoMap){
            /***获取产品信息列表**/
            ChbPrdBaseinfoMapper chbPrdBaseinfoMapper =  SpringContextUtil.getBean(ChbPrdBaseinfoMapper.class);
            List<ChbPrdBaseinfo> prdBaseinfoList = chbPrdBaseinfoMapper.selectAllProd(null);
            prodInfoMap = null!=prdBaseinfoList?prdBaseinfoList.stream().collect(Collectors.toMap(p->p.getProdId(), p-> p,(key1,key2)->key1)):new HashMap<>();
        }
        PbcMonthReportDetailAsset pbcMonthReportDetailAsset = new PbcMonthReportDetailAsset();
        //相同内容字段直接赋值
        if(null!=prodInfoMap.get(position.getProdid())){
            BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,prodInfoMap.get(position.getProdid()));
        }
        if(null!=assetInfoMap.get(position.getAssetCode())) {
            ChbAssetInfo assetInfo = assetInfoMap.get(position.getAssetCode());
            BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset, assetInfo);

            if(DictCodeList.是否_中债_是.getValue().equals(assetInfo.getOptionFlag())){
                pbcMonthReportDetailAsset.setOpenplan(assetInfo.getExerciseDate());
            }
            pbcMonthReportDetailAsset.setSubjectName(SubjectName(assetInfo,pbcMonthReportDetailAsset.getOrgAssetType2(),pbcMonthReportDetailAsset.getOrgAssetType1()));


            pbcMonthReportDetailAsset.setPartyType1(null);
            pbcMonthReportDetailAsset.setIndustry(null);
            //2-5 特殊逻辑
            if(position.getAssetType3()!=null&&PbcMonthReportConstants.PBC_ASSETTYPE3_S05.equals(position.getAssetType3())) {
                String industryType1 = assetInfo.getIndustry();
                if (StringUtils.isEmpty(industryType1)){
                    industryType1 = assetInfo.getIndustryPbc();
                }
                String partyType1 = PbcMonthReportConstants.SVR_INSTIT_SIZE_01;
                if(null!=assetInfo.getInstitSize()){
                    if(null!=assetInfo.getInstitSize()){
                        switch (assetInfo.getInstitSize()){
                            case PbcMonthReportConstants.SVR_INSTIT_SIZE_01:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_01; break;
                            case PbcMonthReportConstants.SVR_INSTIT_SIZE_02:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_02; break;
                            case PbcMonthReportConstants.SVR_INSTIT_SIZE_03:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_03; break;
                            case PbcMonthReportConstants.SVR_INSTIT_SIZE_04:partyType1 =PbcMonthReportConstants.SVR_INSTIT_SIZE_04; break;
                        }
                    }
                }
                pbcMonthReportDetailAsset.setPartyType1(partyType1);
                pbcMonthReportDetailAsset.setIndustry(industryType1);
            }
        }
        //先基本信息,再头寸信息,避免错误覆盖
        BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,position);

        BeanUtil.copyPropertiesIgnoreNull(pbcMonthReportDetailAsset,dto);

        //资产端绑定编号
        pbcMonthReportDetailAsset.setDetailno(code);

        pbcMonthReportDetailAsset.setDetailno2(code2);

        //期次赋值
        pbcMonthReportDetailAsset.setTerm(DateUtil.format(position.getEnddate(),"yyyyMM"));

        return pbcMonthReportDetailAsset;
    }


    /**
     * 	债券：发行人
     * 	存款类资产：存/借款机构，接口表中叫资金存入银行
     * 	资产支持证券：发行人
     * 	基金：管理人，接口表中叫基金公司
     * 	项目类：融资人
     * 	计划类：管理人
     * 	股票：公司名称
     * 	质押式回购、买断式回购、金融衍生品：交易对手
     * 	用科目展示的资产项，没有具体主体，展示为空
     * @param assetInfo
     * @param assetType2
     * @param assetType
     * @return
     */
    public static String SubjectName(ChbAssetInfo assetInfo,String assetType2,String assetType){
        String  SubjectName = null;
        if(DictCodeListStl.资产二级分类_资产支持证券.getValue().equals(assetType2)){
            SubjectName = assetInfo.getChbIssuerId();
        }else if(DictCodeListExt3.中债_上游资产一级分类_债券.getValue().equals(assetType)){
            SubjectName = assetInfo.getChbIssuerId();
        }else if(DictCodeListExt3.中债_上游资产一级分类_存款类资产.getValue().equals(assetType)){
            if("D02".equals(assetType2)){
                SubjectName = assetInfo.getBankName();
            }else if("D01".equals(assetType2)){
                SubjectName = assetInfo.getRivalParty();
            }
        }else if(DictCodeListExt3.中债_上游资产一级分类_基金类资产.getValue().equals(assetType)){
            SubjectName = assetInfo.getFundEnpeName();
        }else if(DictCodeListExt3.中债_上游资产一级分类_项目类资产.getValue().equals(assetType)){
            SubjectName = assetInfo.getChbFinancierId();
        }else if(DictCodeListExt3.中债_上游资产一级分类_计划类利率型.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_计划类净值型.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_计划类货币型.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_债券借贷.getValue().equals(assetType)){
            SubjectName = assetInfo.getChbManagerId();
        }else if(DictCodeListExt3.中债_上游资产一级分类_股票.getValue().equals(assetType)){
            SubjectName = assetInfo.getAssetName();
        }else if(DictCodeListExt3.中债_上游资产一级分类_质押式回购.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_买断式回购.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_外汇即期.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_外汇远期.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_外汇掉期.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_利率互换.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_拆借.getValue().equals(assetType)
                ||DictCodeListExt3.中债_上游资产一级分类_金融衍生品.getValue().equals(assetType)){
            SubjectName = assetInfo.getRivalParty();
        }
        return SubjectName;
    }



    /**
     * 赋值贷款类资产数据
     * @param position
     * @param dto
     * @param code
     */
    public static void updateLoan(List<PbcMonthReportDetailAsset> position, Map<String, ChbLoanDetailInfo> loanMap){
        for(PbcMonthReportDetailAsset detailAsset: position){
            //所有的应收账款都屏蔽掉行业和机构规模

            if(PbcReportProperycode.AMT_790000_YSZK.equals(detailAsset.getDetailno())){
                detailAsset.setPartyType1(null);
                detailAsset.setIndustry(null);
            }
            if(null!=loanMap.get(detailAsset.getAssetCode())){
                //所有贷款的将机构与行业全部清空
                if(!PbcReportProperycode.AMT_790000_YSZK.equals(detailAsset.getDetailno())){
                    BeanUtil.copyPropertiesIgnoreNull(detailAsset,loanMap.get(detailAsset.getAssetCode()));
                    String finArea = detailAsset.getBorrowerCity();
                    if (com.joyintech.framework.util.StringUtil.isEmpty(finArea)){
                        continue;
                    }
                    String province = finArea;
                    // 通过融资人所属地区获取上级省
                    if (SvrDataUtil.getNameFromDataDict(DictCode.行政区划_县, province) != null){
                        province = SvrDataUtil.getParentCodeFromDataDict(DictCode.行政区划_县, province);
                    }
                    if (SvrDataUtil.getNameFromDataDict(DictCode.行政区划_市, province) != null){
                        province = SvrDataUtil.getParentCodeFromDataDict(DictCode.行政区划_市, province);
                    }
                    detailAsset.setBorrowerCity(province);

                    //2-2 企业规模
                    if (PbcMonthReportConstants.POSITION_TYPE_ZC.equals(detailAsset.getPositionType())
                            &&PbcMonthReportConstants.PBC_ASSETTYPE1_P05.equals(detailAsset.getAssetType1())
                            && PbcMonthReportConstants.PBC_ASSETTYPE2_R02.equals(detailAsset.getAssetType2())) {
                        //企业规模
                        detailAsset.setPartyType1(ENTERPRISE_SCALE_MAP.get(loanMap.get(detailAsset.getAssetCode()).getEnterpriseScale()));

                        // 行业信息
                        String industryInfo = detailAsset.getIndustryInfo();
                        // 行业信息为空，统计到公共管理、社会保障和社会组织中
                        if (com.joyintech.framework.util.StringUtil.isEmpty(industryInfo)) {
                            industryInfo = PbcMonthReportConstants.SVR_INDUSTRY_DOOR_CODE_S;
                        }
                        detailAsset.setIndustry(industryInfo);
                    }
                }
            }
        }

    }

    /**
     * 判断2-1数据是否要进入,所有
     * @param code
     * @return
     */
    public static Boolean isAssetDate(String code){
        boolean flag = false;
        if(null!=code){
            //特殊资产类型判断svrPbcReportTaskService
            if(existsCode.contains(code)){
                flag = true;
            //如果6-7位编号不是0.则认为是一二级类型.屏蔽,需要屏蔽类型的直接屏蔽
            }else if(!code.substring(6,7).equals("0")&&!notExistsCode.contains(code)){
                flag = true;
            }
        }
        return flag;
    }



    /**
     * 判断4-1数据是否要进入,所有
     * @param code
     * @return
     */
    public static Boolean isAssetDate3(String code){
        boolean flag = false;
        if(null!=code){
            //特殊资产类型判断svrPbcReportTaskService
            if(existsCode3.contains(code)){
                flag = true;
            }
        }
        return flag;
    }

    public static Boolean isAssetPrd(String code){
        boolean flag = false;
        if(null!=code){
            //特殊资产类型判断svrPbcReportTaskService
            if(existsCode.contains(code)){
                flag = true;
                //产品端添加屏蔽部分
            }else if(!notExistsPrdCode.contains(code)){
                flag = true;
            }
        }
        return flag;
    }



    public List<PbcMonthReportDetailAsset>  insertBefore(List<PbcMonthReportDetailAsset> prdPositionList,Date generateDate){

        /****需要特殊处理的科目*/
        Map<String, PbcMonthReportDetailAsset>  detail1001 = prdPositionList.stream().filter(detail ->
                (detail.getDetailno().equals(PbcReportProperycode.AMT_752600_FYXYJRJG)||detail.getDetailno().equals(PbcReportProperycode.AMT_752610_QZZYJYDSF)
                )).collect(Collectors.toMap(PbcMonthReportDetailAsset::getDetailCode,Positiondetail -> Positiondetail));


        /**展示产品购买资产首次买入交易的交易对手名称***/
        Map<String,Object> paraMap = new HashMap<>();
        Set<String> codeList = prdPositionList.stream().map(PbcMonthReportDetailAsset::getCounterListCode).collect(Collectors.toSet());
        paraMap.put("keysList",codeList);
        //展示产品购买资产首次买入交易的交易对手名称
        List<SelectOptionDto> oDto = pbcMonthReportDetailAssetMapper.getCounterList(paraMap);
        Map<String,String> CounterMap = oDto.stream().collect(JoyinCollectors.toMap(x -> x.getId(), x -> x.getText()));

        String source = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE_PBC);
        Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

        for(PbcMonthReportDetailAsset dto : prdPositionList){
            //再赋值交易对手名称  展示产品购买资产首次买入交易的交易对手名称
            dto.setRivalParty(CounterMap.get(dto.getProdid()+"_"+dto.getAssetCode()));
            dto.setAmt(ChbCcyConvertUtil.exchangeMoney(
                    dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(),generateDate,dto.getAmt(),rateMap,source));

            /***银行业金融机构特殊处理**/
            if (dto.getDetailno().equals(PbcReportProperycode.AMT_752600_FYXYJRJG)) {
                //转换为AMT1_4_2_b
                dto.setDetailno("AMT_752620_FZYJYDSF");
                //如果包含专项债券
                if (null != detail1001.get(dto.getCounterListCode() + "_" + PbcReportProperycode.AMT_752610_QZZYJYDSF)) {
                    if (null != dto.getAmt()) {
                        dto.setAmt(dto.getAmt().subtract(
                                null != detail1001.get(dto.getCounterListCode() + "_" + PbcReportProperycode.AMT_752610_QZZYJYDSF) ? detail1001.get(dto.getCounterListCode() + "_" + PbcReportProperycode.AMT_752610_QZZYJYDSF).getAmt() : BigDecimal.ZERO));
                    }
                }
            }

        }
        return prdPositionList;
    }

    /**
     * 批量入库方法
     * @param prdPositionList
     * @return
     */
    public int insertBatch(List<PbcMonthReportDetailAsset> prdPositionList, Date  generateDate){
        //一次性最大插入条数
        int maxDataCountOneTime = reportInsertCount;
        //同批次的详情数据删除
        pbcMonthReportDetailAssetMapper.deleteDetail(DateUtil.getLastDayOfLayMonth(generateDate,"yyyyMM"));
        //部分数据的统一处理
        prdPositionList = this.insertBefore(prdPositionList,generateDate);
        //插入list大小
        int dataCount = prdPositionList.size();
        // 分批存入
        int part = dataCount / maxDataCountOneTime; // 分批数
        int insertCount = 0;
        if(CollectionUtils.isNotEmpty(prdPositionList)){

            //若需要插入的数据比最大插入的数据少，则直接全部插入
            if (dataCount <= maxDataCountOneTime) {
                dataCount = pbcMonthReportDetailAssetMapper.insertBatch(prdPositionList);
                return dataCount;
            }

            List<PbcMonthReportDetailAsset> newDataList = new ArrayList<PbcMonthReportDetailAsset>();
            newDataList.addAll(prdPositionList);



            for (int i = 0; i < part; i++ ) {
                insertCount += pbcMonthReportDetailAssetMapper.insertBatch(newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                insertCount += pbcMonthReportDetailAssetMapper.insertBatch(newDataList);
            }
        }


        prodInfoMap=null;
        assetInfoMap=null;
        return insertCount;



    }
}
