package com.joyintech.tams.svreport.pbc.classic.pbcsendzipdatfile;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.PbcProdAssetpoolCodeMapper;
import com.joyintech.tams.common.base.entity.PbcProdAssetpoolCode;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsUtil;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dao.PbcPrdBaseinfoSubDatMapper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dto.PbcPrdBaseinfoSubDatDiffDto;
import com.joyintech.tams.svreport.pbc.common.annotation.TemplateName;
import com.joyintech.tams.svreport.pbc.common.constants.PbcDatTemplateName;
import com.joyintech.tams.svreport.pbc.common.enums.PbcDictCodeEnums;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * 功能:产品基本信息
 * @since 1.6.0
 * @version 1.6.0
 * @作者：meifaxin
 * @创建时间：2021年7月29日
 * @修改记录：
 */
@Service
@TemplateName(PbcDatTemplateName.PBC_DAT_REPORT_PIB)
public class PbcPrdBaseInfoSubDatService extends PbcReportProdDatTotalService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PbcPrdBaseinfoSubDatMapper pbcPrdBaseinfoSubDatMapper;
    @Autowired
    private PbcProdAssetpoolCodeMapper pbcProdAssetpoolCodeMapper;
    public final static String PBC_PROD_BASEINFO_SEQCODE = "PBCPRDBASEINFO";

    @Override
    public Map<Object, Object> createData(Map<String,Object> param){
        Map<Object,Object> data  = new HashMap<Object,Object>();
        if(param.get("datZipCode")==null){
            return data;
        }
        String datZipCode=StringUtil.isNotEmpty(param.get("datZipCode"))?param.get("datZipCode").toString():"";
        List<PbcPrdBaseinfoSubDatDiffDto> listDetail=pbcPrdBaseinfoSubDatMapper.queryPbcPrdBaseInfoByDatZipCode(datZipCode);
        //查询人行产品代码(外部)
        dealIsspartList(listDetail);
        if (!CollectionUtils.isEmpty(listDetail)){
            for(PbcPrdBaseinfoSubDatDiffDto detail : listDetail){
                //登记表和DAT表数据差异比较
                dealPbcPrdBaseinfoSubDatDiff(detail);
                //处理数据
                dealPbcPrdBaseinfoSubDat(detail);
            }
        }else{
            listDetail.add(new PbcPrdBaseinfoSubDatDiffDto());
        }
        data.put("report", listDetail);
        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Object, Object> createDataMS(Map<String,Object> param){
        return createData(param);
    }

    /**
     * 功能:处理查询出的产品报表明细数据
     * @since 1.6.0
     * @version 1.6.0
     * @param result
     * @return
     * PbcPrdBaseinfoSubDat
     */
    public PbcPrdBaseinfoSubDatDiffDto dealPbcPrdBaseinfoSubDat(PbcPrdBaseinfoSubDatDiffDto result) {
        if (StringUtil.isNotEmpty(result.getIvstorientation())){
            result.setIvstorientation(
                    SysDataUtil.getNameFromDataDict(DictCode.新人行产品类型,result.getIvstorientation()));
        }
        if (StringUtil.isNotEmpty(result.getPbcProdType())){
            result.setPbcProdType( result.getPbcProdType() + "-" + "银行非保本理财");
//						SysDataUtil.getNameFromDataDict(DictCode.人行产品种类,result.getPbcProdType()));
        }
        if (StringUtil.isNotEmpty(result.getCustomerType())){
            result.setCustomerType( SysDataUtil.getNameFromDataDict(DictCode.人行客户类型,result.getCustomerType()));
        }
        if (StringUtil.isNotEmpty(result.getRaiseType())){
            result.setRaiseType( SysDataUtil.getNameFromDataDict(DictCode.人行募集方式,result.getRaiseType()));
        }
        if (StringUtil.isNotEmpty(result.getManageType())){
            result.setManageType( SysDataUtil.getNameFromDataDict(DictCode.人行管理类型,result.getManageType()));
        }
        if (StringUtil.isNotEmpty(result.getFiduciaryDuty())){
            result.setFiduciaryDuty( SysDataUtil.getNameFromDataDict(DictCode.人行管理方式,result.getFiduciaryDuty()));
        }
        if (StringUtil.isNotEmpty(result.getOperateMode())){
            result.setOperateMode(result.getOperateMode() + StringUtil.SPACE + SysDataUtil.getNameFromDataDict(DictCode.新人行产品运作模式,result.getOperateMode()));
        }
        if (StringUtil.isNotEmpty(result.getContMode())){
            result.setContMode(SysDataUtil.getNameFromDataDict(DictCode.人行合作模式,result.getContMode()));
        }
        if (StringUtil.isNotEmpty(result.getBusiModel())){
            result.setBusiModel( SysDataUtil.getNameFromDataDict(DictCode.人行业务模式,result.getBusiModel()));
        }
        if (StringUtil.isNotEmpty(result.getIncomeGuaranIdent())){
            result.setIncomeGuaranIdent(StringUtil.SPACE + SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getIncomeGuaranIdent()));
        }
        if (StringUtil.isNotEmpty(result.getPrinGuaranIdent())){
            result.setPrinGuaranIdent( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getPrinGuaranIdent()));
        }
        if (StringUtil.isNotEmpty(result.getTermRightFlag())){
            result.setTermRightFlag( SysDataUtil.getNameFromDataDict(DictCode.人行有无,result.getTermRightFlag()));
        }
        if (StringUtil.isNotEmpty(result.getCusRedeemFlag())){
            result.setCusRedeemFlag( SysDataUtil.getNameFromDataDict(DictCode.人行有无,result.getCusRedeemFlag()));
        }
        if (StringUtil.isNotEmpty(result.getCreditFlag())){
            result.setCreditFlag( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getCreditFlag()));
        }
        if (StringUtil.isNotEmpty(result.getCrossBorderFlag())){
            result.setCrossBorderFlag( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getCrossBorderFlag()));
        }
        if (StringUtil.isNotEmpty(result.getZjxtFlag())){
            result.setZjxtFlag( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getZjxtFlag()));
        }

        if (StringUtil.isNotEmpty(result.getCreditType())){
            //多个用,切开
            String [] creditModes=result.getCreditType().split(GlobalConstant.COMMA);
            String creditMode="";
            int i=0;
            for(String single:creditModes){
                i++;
                String name = "";
                if(PbcDictCodeEnums.增信机构类型_住户.getCode().equals(single)){
                    name = PbcDictCodeEnums.增信机构类型_住户.getName();
                }
                if(PbcDictCodeEnums.增信机构类型_商品及金融衍生品类.getCode().equals(single)){
                    name = PbcDictCodeEnums.增信机构类型_商品及金融衍生品类.getName();
                }
                if(PbcDictCodeEnums.增信机构类型_境外.getCode().equals(single)){
                    name = PbcDictCodeEnums.增信机构类型_境外.getName();
                }
                if(PbcDictCodeEnums.增信机构类型_权益类.getCode().equals(single)){
                    name = PbcDictCodeEnums.增信机构类型_权益类.getName();
                }
                if(PbcDictCodeEnums.增信机构类型_混合类.getCode().equals(single)){
                    name = PbcDictCodeEnums.增信机构类型_混合类.getName();
                }
                creditMode=creditMode + name;
                if(i<creditModes.length){
                    creditMode=creditMode+GlobalConstant.COMMA;
                }
            }
            result.setCreditType(creditMode);
        }
        if (StringUtil.isNotEmpty(result.getCreditMode())){
            String creditMode = result.getCreditMode();
            String name = "";
            if(PbcDictCodeEnums.增信形式_内部增级.getCode().equals(creditMode)){
                name = PbcDictCodeEnums.增信形式_内部增级.getName();
            }
            if(PbcDictCodeEnums.增信形式_外部增级.getCode().equals(creditMode)){
                name = PbcDictCodeEnums.增信形式_外部增级.getName();
            }
            if(PbcDictCodeEnums.增信形式_内外增级.getCode().equals(creditMode)){
                name = PbcDictCodeEnums.增信形式_内外增级.getName();
            }
            result.setCreditMode(name);
        }
        if (StringUtil.isNotEmpty(result.getIsConstracte())){
            result.setIsConstracte( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getIsConstracte()));
        }
        if (StringUtil.isNotEmpty(result.getTransferType())){
            result.setTransferType( SysDataUtil.getNameFromDataDict(DictCode.人行是否,result.getTransferType()));
        }
        if (StringUtil.isNotEmpty(result.getIdentiFlag())){
            result.setIdentiFlag( SysDataUtil.getNameFromDataDict(DictCode.人行理财产品发起机构标识,result.getIdentiFlag()));
        }
        /*if(result.getOutDepCtry()!=null){
            List<String> outDepCtrys=StringUtil.splitToList(GlobalConstant.COMMA, result.getOutDepCtry());
            result.setOutDepCtry("");
            for(String outDepCtry : outDepCtrys){
                result.setOutDepCtry(result.getOutDepCtry()+","+outDepCtry+" "+SvrDataUtil.getNameFromDataDict(DictCode.所属国家或地区, outDepCtry));
            }
            result.setOutDepCtry(result.getOutDepCtry().substring(1));
        }*/
        // 境内托管机构名称
        if(result.getInDepName()!=null){
            result.setInDepName(result.getInDepName().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.境内托管机构名称, result.getInDepName()));
        }
        // 货币市场基金标识
        if(result.getIsMarketFund()!=null){
            result.setIsMarketFund(SysDataUtil.getNameFromDataDict(DictCode.是否理财子公司, result.getIsMarketFund()));
        }
        return result;
    }
    /**
     * 功能:登记表和DAT表差异
     * @since 1.6.0
     * @version 1.6.0
     * @param result
     * @return
     * PbcPrdBaseinfoSubDat
     */
    public void dealPbcPrdBaseinfoSubDatDiff(PbcPrdBaseinfoSubDatDiffDto result) {
//        if (!StringUtil.equals(result.getPbcProdId(),result.getPbcProdIdPrd())){
//            result.setPbcProdIdDiff(1);
//        }
        if (!StringUtil.equals(result.getIssuePartyId(),result.getIssuePartyIdPrd())){
            result.setIssuePartyIdDiff(1);
        }
        if (!StringUtil.equals(result.getProdName(),result.getProdNamePrd())){
            result.setProdNameDiff(1);
        }
        if (!StringUtil.equals(result.getPbcProdType(),result.getPbcProdTypePrd())){
            result.setPbcProdTypeDiff(1);
        }
        if (!StringUtil.equals(result.getIvstorientation(),result.getIvstorientationPrd())){
            result.setIvstorientationDiff(1);
        }
        if (!StringUtil.equals(result.getProdBrand(),result.getProdBrandPrd())){
            result.setProdBrandDiff(1);
        }
        if (null!=result.getProdStage()&&null!=result.getProdStagePrd()&&result.getProdStage().compareTo(result.getProdStagePrd())!=0){
            result.setProdStageDiff(1);
        }
        if (!StringUtil.equals(result.getInsideProdId(),result.getInsideProdIdPrd())){
            result.setInsideProdIdDiff(1);
        }
        if (!StringUtil.equals(result.getRaiseType(),result.getRaiseTypePrd())){
            result.setRaiseTypeDiff(1);
        }
        if (!StringUtil.equals(result.getManageType(),result.getManageTypePrd())){
            result.setManageTypeDiff(1);
        }
        if (!StringUtil.equals(result.getOperateMode(),result.getOperateModePrd())){
            result.setOperateModeDiff(1);
        }
        if (!StringUtil.equals(result.getRaiseVdate01(),result.getRaiseVdate01Prd())){
            result.setRaiseVdate01Diff(1);
        }
        if (!StringUtil.equals(result.getRaiseEnddate01(),result.getRaiseEnddate01Prd())){
            result.setRaiseEnddate01Diff(1);
        }
        if (!StringUtil.equals(result.getTermRightFlag(),result.getTermRightFlagPrd())){
            result.setTermRightFlagDiff(1);
        }
        if (!StringUtil.equals(result.getCusRedeemFlag(),result.getCusRedeemFlagPrd())){
            result.setCusRedeemFlagDiff(1);
        }
        if (!StringUtil.equals(result.getCreditFlag(),result.getCreditFlagPrd())){
            result.setCreditFlagDiff(1);
        }
        if (!StringUtil.equals(result.getInDepCode(),result.getInDepCodePrd())){
            result.setInDepCodeDiff(1);
        }
        if (!StringUtil.equals(result.getOutDepCtry(),result.getOutDepCtryPrd())){
            result.setOutDepCtryDiff(1);
        }
        if (!StringUtil.equals(result.getOutDepName(),result.getOutDepNamePrd())){
            result.setOutDepNameDiff(1);
        }
        if (!StringUtil.equals(result.getVdate01(),result.getVdate01Prd())){
            result.setVdate01Diff(1);
        }
        if (!StringUtil.equals(result.getMdate01(),result.getMdate01Prd())){
            result.setMdate01Diff(1);
        }
        if (!StringUtil.equals(result.getFiduciaryDuty(),result.getFiduciaryDutyPrd())){
            result.setFiduciaryDutyDiff(1);
        }
        if (!StringUtil.equals(result.getIsConstracte(),result.getIsConstractePrd())){
            result.setIsConstracteDiff(1);
        }
        if (!StringUtil.equals(result.getTransferType(),result.getTransferTypePrd())){
            result.setTransferTypeDiff(1);
        }
        if (!StringUtil.equals(result.getRaiseCcy(),result.getRaiseCcyPrd())){
            result.setRaiseCcyDiff(1);
        }
        if (!StringUtil.equals(result.getCashPrinCcy(),result.getCashPrinCcyPrd())){
            result.setCashPrinCcyDiff(1);
        }
        if (!StringUtil.equals(result.getCashProfCcy(),result.getCashProfCcyPrd())){
            result.setCashProfCcyDiff(1);
        }
        if (!StringUtil.equals(result.getCreditType(),result.getCreditTypePrd())){
            result.setCreditTypeDiff(1);
        }
        if (!StringUtil.equals(result.getCreditMode(),result.getCreditModePrd())){
            result.setCreditModeDiff(1);
        }
        if (!StringUtil.equals(result.getCrossBorderFlag(),result.getCrossBorderFlagPrd())){
            result.setCrossBorderFlagDiff(1);
        }
        if (!StringUtil.equals(result.getZjxtFlag(),result.getZjxtFlagPrd())){
            result.setZjxtFlagDiff(1);
        }
        ///ZGBSEI-419  【新人行升级】人行报送文件管理（资产管理），dat预览页面，字体颜色显示问题
        if(!(result.getProdStage()==null&&result.getProdStagePrd()==null)){
            if(result.getProdStage()==null||result.getProdStagePrd()==null||
                    result.getProdStage().compareTo(result.getProdStagePrd())!=0){
                result.setProdStageDiff(1);
            }
        }
        if (!StringUtil.equals(result.getProdId(),result.getProdIdPrd())){
            result.setProdIdDiff(1);
        }
        if (!StringUtil.equals(result.getIsMarketFund(),result.getIsMarketFundPrd())){
            result.setIsMarketFundDiff(1);
        }


    }
    /**
     * 功能:获取人行代码(外部)
     * @since 1.6.0
     * @version 1.6.0
     * @param listDetail
     */
    private void dealIsspartList(List<PbcPrdBaseinfoSubDatDiffDto> listDetail){
        List<String> innerProdIds = new ArrayList<>();
        List<PbcProdAssetpoolCode> poolCodeList = new ArrayList<>();
        Map<String,String> innerProdIdMapping = new HashMap<>();
        for (PbcPrdBaseinfoSubDatDiffDto pbcPrdBaseinfoSubDat : listDetail) {
            String prodId = pbcPrdBaseinfoSubDat.getProdId();
            if(StrUtil.isNotEmpty(prodId) && !innerProdIds.contains(prodId)){
                innerProdIds.add(prodId);
            }
        }
        List<PbcProdAssetpoolCode> pbcProdAssetpoolCodes = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(innerProdIds)){
            pbcProdAssetpoolCodes = pbcProdAssetpoolCodeMapper.selectByInsideProdList(innerProdIds);
        }
        for (PbcPrdBaseinfoSubDatDiffDto pbcPrdBaseinfoSubDat : listDetail) {
            String prodId = pbcPrdBaseinfoSubDat.getProdId();
            Optional<PbcProdAssetpoolCode> first = pbcProdAssetpoolCodes.stream().filter(pbcProdAssetpoolCode ->
                    prodId.equals(pbcProdAssetpoolCode.getInsideProdId())).findFirst();
            if(first.isPresent()){
                pbcPrdBaseinfoSubDat.setPbcProdIdExternal(first.get().getPbcCode());
            }else{
                //add by zhoubin 20220424 start
                String issuePartyId = pbcPrdBaseinfoSubDat.getIssuePartyId();
                String pbcProdIdExternal = PbcClassicsUtil.getPbcProdId(StringUtils.isBlank(issuePartyId)?null:issuePartyId.substring(0,6), pbcPrdBaseinfoSubDat.getProfitType(), PBC_PROD_BASEINFO_SEQCODE, pbcPrdBaseinfoSubDat.getVdate());
                //add by zhoubin 20220424 start
                pbcPrdBaseinfoSubDat.setPbcProdIdExternal(pbcProdIdExternal);
                innerProdIdMapping.put(prodId,pbcProdIdExternal);
            }
        }
        Date date=DateUtil.parse(DateUtil.getCurrentDate());
        //没有查询到的插入
        for(Map.Entry<String, String> entry : innerProdIdMapping.entrySet()){
            PbcProdAssetpoolCode prodAssetpoolCode = new PbcProdAssetpoolCode();
            prodAssetpoolCode.setInsideProdId(entry.getKey());
            prodAssetpoolCode.setPbcCode(entry.getValue());
            prodAssetpoolCode.setCodeType(DictCodeListExt3.代码类型_产品.getValue());
            prodAssetpoolCode.setStatus("E");
            prodAssetpoolCode.setCreateUser(SecurityUtil.getLoginUser().getUserName());
            prodAssetpoolCode.setCreateTime(date);
            prodAssetpoolCode.setOrgCode(SecurityUtil.getLoginUser().getOrgCode());
            prodAssetpoolCode.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
            prodAssetpoolCode.setUpdateTime(date);
            poolCodeList.add(prodAssetpoolCode);
        }
        if(!CollectionUtils.isEmpty(poolCodeList)){
            pbcProdAssetpoolCodeMapper.batchInsertPbcCode(poolCodeList);
        }
    }


}
