package com.joyintech.tams.svreport.chb.assetinfodetail.util;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.background.component.common.check.dao.DataCheckCommonMapper;
import com.joyintech.background.component.common.check.dto.CheckRule;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.system.base.entity.SysDatadictListLink;
import com.joyintech.tams.common.base.entity.MstCustomerInfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.chb.assetinfodetail.asset.DtoChangeUtil;
import com.joyintech.tams.svreport.chb.assetinfodetail.dao.AssetInfoDetailMapper;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.AssetInfoDetailDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.*;
import com.joyintech.tams.svreport.interf.chb.baseinfo.dao.SvrMstCustomerInfoAddImpMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class AssetInfoDetailUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    static Map<String,Set<String>> map = null;

    static Map<String,String> dict = new HashMap<>();

    static Map<String,String> rule = new HashMap<>();

    @Autowired
    private AssetInfoDetailMapper assetInfoDetailMapper;

    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    @Autowired
    private DataCheckCommonMapper chbDataCheckCommonMapper;
    @Autowired
    private SvrMstCustomerInfoAddImpMapper svrMstCustomerInfoAddImpMapper;

    private static String ISNUMBER2 = "isNumber2";

    private static String ISVALIDDATE = "isValidDate";


    private static String SVRBUSSREPDEPOSITINFODTO = "svrBussRepDepositInfoDto";

    private static String SVRBUSSREPPLACEMENTINFODTO = "svrBussRepPlacementInfoDto";

    private static String SVRBUSSFINBONDREPINFODTO = "svrBussFinBondRepInfoDto";

    private static String SVRBUSSREPASSETCREDITNONSTDINFODTO = "svrBussRepAssetCreditNonstdInfoDto";

    private static String SVRBUSSREPASSETASTBILLINFODTO = "svrBussRepAssetAstBillInfoDto";

    private static String SVRBUSSREPEQUITYINFODTO = "svrBussRepEquityInfoDto";

    private static String SVRBUSSREPFINANCIALLINFODTO = "svrBussRepFinanciallInfoDto";

    private static String SVRBUSSREPQDIIBONDINFODTO = "svrBussRepQDIIBondInfoDto";

    private static String SVRBUSSREPQDIIDEMOLITIONINFODTO = "svrBussRepQDIIdemolitionInfoDto";

    private static String SVRBUSSREPQDIISTRUCTUREINFODTO = "svrBussRepQDIIStructureInfoDto";

    private static String SVRBUSSREPASSETMNGPRODINFODTO = "svrBussRepAssetMngProdInfoDto";

    private static String SVRBUSSREPNOBLEMETALINFODTO = "svrBussRepNobleMetalInfoDto";

    private static String SVRBUSSREPOTHERINVESTTARGETINFODTO = "svrBussRepOtherInvestTargetInfoDto";

    private static String SVRBUSSREPOUTINVESTAGRINFODTO = "svrBussRepOutInvestAgrInfoDto";

    private static String SVRBUSSREPOTHERINFODTO = "svrBussRepOtherInfoDto";

    private static String SVRBUSSREPOTHKINDINFODTO = "svrBussRepOthKindInfoDto";

    /**
     * 获取资产及二级类型的关系
     * @return
     */
    public synchronized Map<String, Set<String>> getDataListLink(){

        if(map==null||map.isEmpty()){
            map = new HashMap<>();
            //界面配置,根据SYS_DATADICT_LIST_LINK
            Map<String,Object> params = new HashMap<>();
            params.put("linkDictCode", DictCode.资产_负债类别.getValue());
            List<SysDatadictListLink> listLink = assetInfoDetailMapper.getDataListLinkData(params);
            for (SysDatadictListLink link:listLink) {
                if(null!=map.get(link.getLinkedItemCode())){
                    map.get(link.getLinkedItemCode()).add(link.getItemCode());
                }else{
                    map.put(link.getLinkedItemCode(),new HashSet<>(Arrays.asList(link.getItemCode())));
                }
            }
        }
        return map;
    }

    public AssetInfoDetailDto getDtoByPageMessgae(AssetInfoDetailDto dto)  {
        try {
            //遍历资产类型数据
            for (ServerMenu menu : ServerMenu.getAllAsset()) {
                //判断数据字典中是否有当前资产类型的实体类
                if (getDataListLink().get(menu.getAssetType()).contains(dto.getAssetType2())) {
                    //获取实体类中对应名称的资产方法
                    PropertyDescriptor pd = new PropertyDescriptor(menu.getServerId(),
                            dto.getClass());
                    Method getMethod =pd.getReadMethod();
                    //获取对应的实体类字段
                    Object obj1 = getMethod.invoke(dto);
                    //执行数据转换方法
                    DtoChangeUtil.SetChbToRep(obj1, dto, menu.getFlagStr(), true);
                    //资产类型赋值
                    dto.setAssetType(menu.getAssetType());
                }
            }
            return dto;
        }catch (Exception e){
            logger.info("实体转换错误,请检查数据类型",e);
        }finally {
            return null;
        }
    }

    public synchronized List<ExcelValidDto> convertToBean(List<AssetInfoDetailDto> entities , List<List<String>> dataList){
        List<ExcelValidDto> resultList = new ArrayList<>();
        Map<String,Object> params = new HashMap<>();
        params.put("linkDictCode", DictCode.资产_负债类别.getValue());
        List<SysDatadictListLink> listLink = assetInfoDetailMapper.getDataListLinkData(params);
        dict = new HashMap<>();
        listLink.forEach(sysDatadictListLink -> {
            dict.put(sysDatadictListLink.getItemCode(),sysDatadictListLink.getLinkedItemCode());
        });
        params.put("linkDictCode", DictCode.机构所属行业一级分类.getValue());
        List<SysDatadictListLink> industryListLink = assetInfoDetailMapper.getDataListLinkData(params);
        industryListLink.forEach(sysDatadictListLink -> {
            dict.put("industry" + sysDatadictListLink.getItemCode(),sysDatadictListLink.getLinkedItemCode());
        });
        params.put("linkDictCode", DictCode.人行资产一级分类.getValue());
        List<SysDatadictListLink> pbcAssetType1ListLink = assetInfoDetailMapper.getDataListLinkData(params);
        pbcAssetType1ListLink.forEach(sysDatadictListLink -> {
            dict.put("pbcAssetType1" + sysDatadictListLink.getItemCode(),sysDatadictListLink.getLinkedItemCode());
        });
        params.put("linkDictCode", DictCode.人行资产二级分类.getValue());
        List<SysDatadictListLink> pbcAssetType2ListLink = assetInfoDetailMapper.getDataListLinkData(params);
        pbcAssetType2ListLink.forEach(sysDatadictListLink -> {
            dict.put("pbcAssetType2" + sysDatadictListLink.getItemCode(),sysDatadictListLink.getLinkedItemCode());
        });
        rule = new HashMap<>();
        List<CheckRule> listRules = chbDataCheckCommonMapper.queryChbCheckRule("AssetInfoRegist002","01");
        if (!StringUtil.isEmptyList(listRules)){
            listRules.forEach(chbCheckRule -> {
                rule.put(chbCheckRule.getSrcproperty() + GlobalConstant.UNDER_LINE + chbCheckRule.getCheckmethod(),chbCheckRule.getRemark());
            });
        }
        for (int i = 0; i < dataList.size(); i++ ) {
            if ( i < 2 ){
                continue;
            }
            List<String> list = dataList.get(i);
            AssetInfoDetailDto entity = new AssetInfoDetailDto();
            List<ValidMessage> messages = new ArrayList<>();
            List<String> headList = new ArrayList();
            getHeadList_gt(headList);
            entity.setAssetCode(list.get(headList.indexOf("*行内资产/负债编码")));
            entity.setIssuePartyId(list.get(headList.indexOf("*发行机构代码")));
            entity.setAssetType2(SysDataUtil.getZZDictCodeFromName(DictCode.资产_负债二级类别,
                    list.get(headList.indexOf("*资产/负债类别"))));
            entity.setAssetType(dict.get(entity.getAssetType2()));
            entity.setTradeMarket(SysDataUtil.getZZDictCodeFromName(DictCode.交易流通场所_中债,
                    list.get(headList.indexOf("*交易流通场所"))));
            //modify by tuqirong 2022-01-20 start
            //导入模板中新增资产二级分类要素，由于导入模板中资产二级分类需依据一级分类联动，
            //在做联动关系处理时，一级分类的值不允许有空格，所以在模板中将一级分类中的空格换成了“_”，后台存值时需处理
            entity.setOrgAssetType1(SysDataUtil.getZZDictCodeFromName(DictCode.中债_上游资产一级分类,
                    list.get(headList.indexOf("*资产一级分类")).replace(GlobalConstant.UNDER_LINE," ")));
            //新增资产二级分类
            entity.setOrgAssetType2(SysDataUtil.getZZDictCodeFromName(DictCode.中债_上游资产二级分类,
                    list.get(headList.indexOf("*资产二级分类"))));
            //modify by tuqirong 2022-01-20 end
            entity.setCcy(SysDataUtil.getZZDictCodeFromName(DictCode.币种_中债, list.get(headList.indexOf("*币种"))));
            if (DictCodeListExt2.资产_负债类别_本行_他行存款_大额存单.getValue().equals(entity.getAssetType())){
                /**维护信息-本行/他行存款||大额存单*/
               /* if (dataList.get(1).size() != 18) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-存款格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_01(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-存款格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepDepositInfoDto(getSvrBussRepDepositInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_拆放同业_买入返售_资产_同业拆入_卖出回购_负债.getValue().equals(entity.getAssetType())){
                /**维护信息-拆放同业/买入返售（资产）||同业拆入/卖出回购（负债）*/
                /*if (dataList.get(1).size() != 18) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-回购格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_02(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-回购格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                if(StringUtil.isNotEmpty(list.get(headList.indexOf("对应资管计划代码")))){
                    entity.setCheckCode(list.get(headList.indexOf("对应资管计划代码")));
                }
                entity.setSvrBussRepPlacementInfoDto(getSvrBussRepPlacementInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_债券类资产_理财直接融资工具_同业存单.getValue().equals(entity.getAssetType())){
                /**维护信息-债券类资产/理财直接融资工具/同业存单*/
                /*if (dataList.get(1).size() != 24) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-债券格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_03(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-债券格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussFinBondRepInfoDto(getSvrBussFinBondRepInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_非标准化债权类资产_信贷资产流转项目.getValue().equals(entity.getAssetType())){
                /**维护信息-非标准化债权类资产/新增可投资资产*/
                /*if (dataList.get(1).size() != 78) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-非标格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_04(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-非标格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
               //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepAssetCreditNonstdInfoDto(getSvrBussRepAssetCreditNonstdInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_票据类资产_信用证类资产.getValue().equals(entity.getAssetType())){
                /**维护信息-票据类资产/信用证类资产*/
                /*if (dataList.get(1).size() != 61) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_05(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepAssetAstBillInfoDto(getSvrBussRepAssetAstBillInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_权益类资产.getValue().equals(entity.getAssetType())){
                /**维护信息-权益类资产*/
               /* if (dataList.get(1).size() != 24) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-权益类格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_06(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-权益类格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf(" *股票/企业名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepEquityInfoDto(getSvrBussRepEquityInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_金融衍生品.getValue().equals(entity.getAssetType())){
                /**维护信息-金融衍生品*/
                /*if (dataList.get(1).size() != 14) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-金融衍生品格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_07(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-金融衍生品格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepFinanciallInfoDto(getSvrBussRepFinanciallInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_QDII债券资产.getValue().equals(entity.getAssetType())){
                /**维护信息-QDII债券资产*/
                /*if (dataList.get(1).size() != 51) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-QDII品格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_08(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-QDII品格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*债券名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepQDIIBondInfoDto(getSvrBussRepQDIIBondInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_QDII拆出_逆回购资产.getValue().equals(entity.getAssetType())){
                /**维护信息-QDII拆出/逆回购资产*/
               /* if (dataList.get(1).size() != 51) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-QDII品格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_08(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-QDII品格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepQDIIdemolitionInfoDto(getSvrBussRepQDIIdemolitionInfoDto( messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_QDII股票_基金类资产.getValue().equals(entity.getAssetType())){
                /**维护信息-QDII股票/基金类资产*/
                /*if (dataList.get(1).size() != 51) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-QDII品格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_08(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-QDII品格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*股票/基金名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussQDIISharesInfoDto(getSvrBussQDIISharesInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_QDII结构性票据类资产.getValue().equals(entity.getAssetType())){
                /**维护信息-QDII结构性票据*/
                /*if (dataList.get(1).size() != 51) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-QDII品格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_08(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-QDII品格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepQDIIStructureInfoDto(getSvrBussRepQDIIStructureInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_资产管理产品.getValue().equals(entity.getAssetType())){
                /**维护信息-资产管理产品*/
                /*if (dataList.get(1).size() != 33) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-资产管理产品格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_12(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-资产管理产品格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf(" *资管计划名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*资管计划成立日期")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*资管计划成立日期")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*资管计划成立日期"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*资管计划终止日期")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*资管计划终止日期")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*资管计划终止日期"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepAssetMngProdInfoDto(getSvrBussRepAssetMngProdInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_商品类资产.getValue().equals(entity.getAssetType())){
                /**维护信息-贵金属/商品类资产*/
                /*if (dataList.get(1).size() != 61) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_05(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepNobleMetalInfoDto(getSvrBussRepNobleMetalInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_各类资产的其他项.getValue().equals(entity.getAssetType())){
                /**维护信息-各类资产的其他项*/
                /*if (dataList.get(1).size() != 39) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-各类资产其他项格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_14(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-各类资产其他项格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepOtherInvestTargetInfoDto(getSvrBussRepOtherInvestTargetInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_公募基金.getValue().equals(entity.getAssetType())
                    || DictCodeListExt2.资产_负债类别_私募基金.getValue().equals(entity.getAssetType())
                    || DictCodeListExt2.资产_负债类别_私募基金_公募基金.getValue().equals(entity.getAssetType())){
                /**维护信息-公募基金/私募基金*/
                /*if (dataList.get(1).size() != 25) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-基金格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_L01(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-基金格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*基金名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepFundInfoDto(getSvrBussRepFundInfoDto(list,headList));
            }else if (DictCodeListExt2.资产_负债类别_委外投资_协议方式.getValue().equals(entity.getAssetType())){
                /**维护信息-委外投资-协议方式*/
               /* if (dataList.get(1).size() != 61) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_05(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != 61) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("委外投资协议名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("投资运作起始日期")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("投资运作起始日期")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("投资运作起始日期"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("投资运作终止日期")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("投资运作终止日期")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("投资运作终止日期"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepOutInvestAgrInfoDto(getSvrBussRepOutInvestAgrInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_其他_负债_类资产.getValue().equals(entity.getAssetType())){
                /**维护信息-其他（负债）类资产*/
                /*if (dataList.get(1).size() != 61) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_05(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 end 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepOtherInfoDto(getSvrBussRepOtherInfoDto(messages , list,headList));
            }else if (DictCodeListExt2.资产_负债类别_另类资产.getValue().equals(entity.getAssetType())){
                /**维护信息-另类资产*/
               /* if (dataList.get(1).size() != 61) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }*/
                headList.clear();
                getHeadList_05(headList);
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                if (dataList.get(1).size() != headList.size()) {
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产要素表模板-票据及其他格式不正确！"));
                    return resultList;
                }
                //add by tuqirong 2022-01-19 start 导入模板存在缺少或多余字段，给予提示
                //资产名称
                entity.setAssetName(list.get(headList.indexOf("*名称")));
                //起息日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                        entity.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
                    }else {
                        entity.setVdate(new Date());
                        messages.add(new ValidMessage("起息日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                //到期日
                if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
                    if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                        entity.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
                    }else {
                        entity.setMdate(new Date());
                        messages.add(new ValidMessage("到期日必须为日期格式（yyyy-MM-dd）"));
                    }
                }
                entity.setSvrBussRepOthKindInfoDto(getSvrBussRepOthKindInfoDto(messages , list,headList));
            }
            else {
                resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "资产/负债类别不在值域范围内"));
                return resultList;
            }
            List<String> message = new ArrayList<>();
            for (int j = i+1; j < dataList.size(); j++){
                if (list.get(0).equals(dataList.get(j).get(0))){
                    resultList.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1), "第"+(i+1)+"行资产代码与第"+(j+1)+"行资产代码重复"));
                }
            }
            messages.addAll(chbDataCheckCommonUtil.chbCheckCommon(resultList, entity, "AssetInfoRegist002", entity.getAssetType2(), ValidMessage.class));
            if (!StringUtil.isEmptyList(messages)){
                //modify by tuqirong 2022-01-10 start 错误信息去重处理
                Map<String, Object> errMap = new HashMap<>();
                messages.forEach(validMessage -> {
                    if (!errMap.containsKey(validMessage.getMessage())) {
                        message.add(validMessage.getMessage());
                        errMap.put(validMessage.getMessage(),validMessage.getMessage());
                    }
                });
                //modify by tuqirong 2022-01-10 end 错误信息去重处理
            }
            getDtoByPageMessgae(entity);
            if (StringUtil.isNotEmpty(entity.getPbcAssetType1()) && StringUtil.isNotEmpty(entity.getPbcAssetType2())){
                if (!StringUtil.equals(entity.getPbcAssetType1(),dict.get("pbcAssetType1"+entity.getPbcAssetType2()))){
                    message.add("所选*新人行二级分类值不在所选*新人行一级分类下");
                }
            }
            if (StringUtil.isNotEmpty(entity.getPbcAssetType2()) && StringUtil.isNotEmpty(entity.getPbcAssetType3())){
                if (!StringUtil.equals(entity.getPbcAssetType2(),dict.get("pbcAssetType2"+entity.getPbcAssetType3()))){
                    message.add("所选*新人行三级分类值不在所选*新人行二级分类下");
                }
            }
            entity.setCreateUser(LoginUserUtil.getUserName());
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            entity.setUpdateUser(LoginUserUtil.getUserName());
            entity.setAssetUuid(IDGenerator.generate(36));
            entity.setStatus(DictCodeListExt2.数据状态_有效.getValue());
            if (CollectionUtils.isNotEmpty(message)) {
                resultList.add(new ExcelValidDto(entity.getAssetCode(), String.valueOf(i+1),
                        StringUtil.joinString(message, "<br>")));
            }
            else {
                entities.add(entity);
            }
        }
        return resultList;
    }

    /**
     * 维护信息-另类资产
     * @param list
     * @return
     */
    private SvrBussRepOthKindInfoDto getSvrBussRepOthKindInfoDto(List<ValidMessage> messages , List<String> list,List<String> headList){
        SvrBussRepOthKindInfoDto dto = new SvrBussRepOthKindInfoDto();
        dto.setCountryArea(SysDataUtil.getZZDictCodeFromName(DictCode.所属国家或地区, list.get(headList.indexOf("*所属国家或地区"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*资产价值")))){
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*资产价值")))){
                dto.setAssetValue(new BigDecimal(list.get(headList.indexOf("*资产价值"))));
            }else {
                dto.setAssetValue(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHKINDINFODTO + GlobalConstant.POINT + "assetValue" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("资产收益率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("资产收益率%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("资产收益率%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHKINDINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-其他（负债）类资产
     * @param list
     * @return
     */
    private SvrBussRepOtherInfoDto getSvrBussRepOtherInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepOtherInfoDto dto = new SvrBussRepOtherInfoDto();
        dto.setCountryArea(SysDataUtil.getZZDictCodeFromName(DictCode.所属国家或地区, list.get(headList.lastIndexOf("*所属国家或地区"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("期限（天）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("期限（天）")))){
                dto.setTermDate(new BigDecimal(list.get(headList.indexOf("期限（天）"))));
            }else {
                dto.setTermDate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHERINFODTO + GlobalConstant.POINT + "termDate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*负债规模")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*负债规模")))){
                dto.setDebtScale(new BigDecimal(list.get(headList.indexOf("*负债规模"))));
            }else {
                dto.setDebtScale(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHERINFODTO + GlobalConstant.POINT + "debtScale" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf(" 利率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf(" 利率%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf(" 利率%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHERINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        //新增资产代码字段 add by tuqirong 2021-12-31 start
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*是否为同业借款")))) {
            dto.setIsDepositFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否为同业借款"))));
        }
        //新增资产代码字段 add by tuqirong 2021-12-31 end
        return dto;
    }

    /**
     * 维护信息-委外投资-协议方式
     * @param list
     * @return
     */
    private SvrBussRepOutInvestAgrInfoDto getSvrBussRepOutInvestAgrInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepOutInvestAgrInfoDto dto = new SvrBussRepOutInvestAgrInfoDto();
        dto.setAssetName(list.get(headList.indexOf("委外投资协议名称")));
        dto.setAssetId(list.get(headList.indexOf("委外投资协议编号")));
        dto.setTrustee(list.get(headList.indexOf("受托人")));
        dto.setManagerId(list.get(headList.indexOf("实际管理人")));
        dto.setTrusteeId(list.get(headList.indexOf("托管人")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("委托投资金额")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("委托投资金额")))){
                dto.setAmt(new BigDecimal(list.get(headList.indexOf("委托投资金额"))));
            }else {
                dto.setAmt(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOUTINVESTAGRINFODTO + GlobalConstant.POINT + "amt" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setTruActType(SysDataUtil.getZZDictCodeFromName(DictCode.资金实际投向, list.get(headList.indexOf("资金实际投向"))));
        dto.setInvestType(list.get(headList.indexOf("资金运用方式")));
        dto.setCapUseInduLevel2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("资金运用行业"))));
        dto.setCapUseIndu(dict.get("industry"+dto.getCapUseInduLevel2()));
        dto.setProProperty(SysDataUtil.getZZDictCodeFromName(DictCode.管理方式_发行登记, list.get(headList.indexOf("委外投资属性"))));
        dto.setRateFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否有预期收益率"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("预期最高收益率")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("预期最高收益率")))){
                dto.setMaxRate(new BigDecimal(list.get(headList.indexOf("预期最高收益率"))));
            }else {
                dto.setMaxRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOUTINVESTAGRINFODTO + GlobalConstant.POINT + "maxRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("预期最低收益率")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("预期最低收益率")))){
                dto.setMinRate(new BigDecimal(list.get(headList.indexOf("预期最低收益率"))));
            }else {
                dto.setMinRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOUTINVESTAGRINFODTO + GlobalConstant.POINT + "minRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("管理费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("管理费率%")))){
                dto.setMgrFeeRate(new BigDecimal(list.get(headList.indexOf("管理费率%"))));
            }else {
                dto.setMgrFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOUTINVESTAGRINFODTO + GlobalConstant.POINT + "mgrFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("托管费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("托管费率%")))){
                dto.setTruFeeRate(new BigDecimal(list.get(headList.indexOf("托管费率%"))));
            }else {
                dto.setTruFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOUTINVESTAGRINFODTO + GlobalConstant.POINT + "truFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("交易相关合计费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("交易相关合计费率%")))){
                dto.setTradeFeeRate(new BigDecimal(list.get(headList.indexOf("交易相关合计费率%"))));
            }else {
                dto.setTradeFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOUTINVESTAGRINFODTO + GlobalConstant.POINT + "tradeFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("中介服务机构合计费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("中介服务机构合计费率%")))){
                dto.setAgencyFeeRate(new BigDecimal(list.get(headList.indexOf("中介服务机构合计费率%"))));
            }else {
                dto.setAgencyFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOUTINVESTAGRINFODTO + GlobalConstant.POINT + "agencyFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("其他合计费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("其他合计费率%")))){
                dto.setOtherFeeRate(new BigDecimal(list.get(headList.indexOf("其他合计费率%"))));
            }else {
                dto.setOtherFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOUTINVESTAGRINFODTO + GlobalConstant.POINT + "otherFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-公募基金/私募基金
     * @param list
     * @return
     */
    private SvrBussRepFundInfoDto getSvrBussRepFundInfoDto(List<String> list,List<String> headList){
        SvrBussRepFundInfoDto dto = new SvrBussRepFundInfoDto();
        dto.setAssetId(list.get(headList.indexOf("*基金代码")));
        dto.setAssetName(list.get(headList.indexOf("*基金名称")));
        dto.setIndustryShares2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("*行业"))));
        dto.setIndustryShares(dict.get("industry"+dto.getIndustryShares2()));
        dto.setRegistOrg1(SysDataUtil.getZZDictCodeFromName(DictCode.登记备案机构, list.get(headList.indexOf("*登记备案机构"))));
        dto.setIsGovernFund(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否属于政府投资基金"))));
        dto.setGovernInvestFund(SysDataUtil.getZZDictCodeFromName(DictCode.政府投资基金投向, list.get(headList.indexOf("政府投资基金投向"))));
        dto.setFinancialComissue(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否由金融资产投资公司发行"))));
        dto.setFundEnpeName(list.get(headList.indexOf("基金公司名称")));
        dto.setMngOrgName(list.get(headList.indexOf("*基金管理机构名称")));
        dto.setTruOrgName(list.get(headList.indexOf("*基金托管机构名称")));
        dto.setInvestStage(SysDataUtil.getZZDictCodeFromName(DictCode.投资阶段, list.get(headList.indexOf("投资阶段"))));
        dto.setSharesType1(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按规模划分, list.get(headList.indexOf("投资企业类型（按规模划分）"))));
        dto.setSharesType3(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按技术领域划分, list.get(headList.indexOf("投资企业类型（按技术领域划分）"))));
        dto.setSharesType2(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按经济类型划分, list.get(headList.indexOf("投资企业类型（按经济类型划分）"))));
        dto.setFundInvestAsset(list.get(headList.indexOf("*基金投资资产")));
        dto.setRemark(list.get(headList.indexOf("备注")));
        dto.setPrivatefundFlag(list.get(headList.indexOf("是否投向金融资产投资公司或其附属机构发行的私募股权投资基金")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        //新增是否为固定收益类字段 add by tuqirong 2021-12-31 start
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("是否为固定收益类")))) {
            dto.setProfitFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否为固定收益类"))));
        }
        //新增是否为固定收益类字段 add by tuqirong 2021-12-31 end
        //add by tuqirong 2021-01-21 start 导入模板新增字段处理
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*管理人")))) {
            dto.setChbManagerId(list.get(headList.indexOf("*管理人")));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*是否政府出资产业投资基金")))) {
            dto.setIsUnlisted(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否政府出资产业投资基金"))));
        }
        //add by tuqirong 2021-01-21 end 导入模板新增字段处理
        return dto;
    }

    /**
     * 维护信息-各类资产的其他项
     * @param list
     * @return
     */
    private SvrBussRepOtherInvestTargetInfoDto getSvrBussRepOtherInvestTargetInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepOtherInvestTargetInfoDto dto = new SvrBussRepOtherInvestTargetInfoDto();
        dto.setInAssetType(SysDataUtil.getZZDictCodeFromName(DictCode.行内资产类别, list.get(headList.indexOf("*行内资产类别"))));
        dto.setInAssetTypeCmm(list.get(headList.indexOf("行内资产类别说明")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*金额")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*金额")))){
                dto.setAssetValue(new BigDecimal(list.get(headList.indexOf("*金额"))));
                dto.setAmt(new BigDecimal(list.get(headList.indexOf("*金额"))));
            }else {
                dto.setAssetValue(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHERINVESTTARGETINFODTO + GlobalConstant.POINT + "assetValue" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setCountryArea(SysDataUtil.getZZDictCodeFromName(DictCode.所属国家或地区, list.get(headList.indexOf("*所属国家或地区"))));
        dto.setRateFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否有预期收益率"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("项目收益率（利率）%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("项目收益率（利率）%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("项目收益率（利率）%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHERINVESTTARGETINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*付息频率（个月/次）")))) {
            try {
                dto.setIntpayCycle(Long.valueOf(list.get(headList.indexOf("*付息频率（个月/次）"))));
            }catch (Exception e){
                dto.setIntpayCycle(0L);
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHERINVESTTARGETINFODTO + GlobalConstant.POINT + "intpayCycle" + GlobalConstant.UNDER_LINE + "isNumber" )));
            }
        }
        dto.setFinancie(list.get(headList.indexOf("*融资人")));
        dto.setFinOrgCode(list.get(headList.indexOf("*融资人组织机构（社会信用）代码")));
        dto.setFcrGradeOut(list.get(headList.indexOf("外部评级机构名称及对融资人评级结果")));
        dto.setFinancierType1(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按规模划分, list.get(headList.indexOf("*融资人类型（按规模划分）"))));
        dto.setFinancierType3(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按技术领域划分, list.get(headList.indexOf("*融资人类型（按技术领域划分）"))));
        dto.setFinancierType2(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按经济类型划分, list.get(headList.indexOf("*融资人类型（按经济类型划分）"))));
        dto.setFinanceProj(list.get(headList.indexOf("*融资项目")));
        dto.setIndustryLevel2Type(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("*融资人所属行业"))));
        dto.setIndustry1(dict.get("industry"+dto.getIndustryLevel2Type()));
        dto.setProIndustryLevel2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("*融资项目所属行业"))));
        dto.setProIndustry(dict.get("industry"+dto.getProIndustryLevel2()));
        dto.setIndustryFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*项目是否属于重点监控行业和领域"))));
        dto.setMonitType(SysDataUtil.getZZDictCodeFromName(DictCode.重点监控行业和领域类别, list.get(headList.indexOf("重点监控行业和领域类别"))));
        dto.setMonTypeComm(list.get(headList.indexOf("重点监控行业和领域类别说明")));
        dto.setOutGrade(SysDataUtil.getZZDictCodeFromName(DictCode.资产评级, list.get(headList.indexOf("对应资产外部评级"))));
        dto.setGuaType(SysDataUtil.getZZDictCodeFromName(DictCode.担保方式, list.get(headList.indexOf("担保方式"))));
        dto.setPledgeType(SysDataUtil.getZZDictCodeFromName(DictCode.抵质押物类型, list.get(headList.indexOf("抵质押物类型"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("抵质押物价值（元）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("抵质押物价值（元）")))){
                dto.setPledgeValue(new BigDecimal(list.get(headList.indexOf("抵质押物价值（元）"))));
            }else {
                dto.setPledgeValue(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPOTHERINVESTTARGETINFODTO + GlobalConstant.POINT + "pledgeValue" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setNatureGuarantee(SysDataUtil.getZZDictCodeFromName(DictCode.担保性质, list.get(headList.indexOf("担保性质"))));
        dto.setRelationFinan(SysDataUtil.getZZDictCodeFromName(DictCode.担保人与融资人关系, list.get(headList.indexOf("担保人与融资人关系"))));
        dto.setGuaTypeRemark(list.get(headList.indexOf("担保情况说明")));
        dto.setDeptEquity(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否为债转股"))));
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-贵金属/商品类资产
     * @param list
     * @return
     */
    private SvrBussRepNobleMetalInfoDto getSvrBussRepNobleMetalInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepNobleMetalInfoDto dto = new SvrBussRepNobleMetalInfoDto();
        dto.setCountryArea(SysDataUtil.getZZDictCodeFromName(DictCode.所属国家或地区, list.get(headList.indexOf("*所属国家或地区"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("期限（天）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("期限（天）")))){
                dto.setTermDate(new BigDecimal(list.get(headList.indexOf("期限（天）"))));
            }else {
                dto.setTermDate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPNOBLEMETALINFODTO + GlobalConstant.POINT + "termDate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*资产价值")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*资产价值")))){
                dto.setAssetValue(new BigDecimal(list.get(headList.indexOf("*资产价值"))));
            }else {
                dto.setAssetValue(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPNOBLEMETALINFODTO + GlobalConstant.POINT + "assetValue" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf(" 资产收益率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf(" 资产收益率%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf(" 资产收益率%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPNOBLEMETALINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-资产管理产品
     * @param list
     * @return
     */
    private SvrBussRepAssetMngProdInfoDto getSvrBussRepAssetMngProdInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepAssetMngProdInfoDto dto = new SvrBussRepAssetMngProdInfoDto();
        dto.setAssetName(list.get(headList.indexOf(" *资管计划名称")));
        dto.setFinancialComissue(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否由金融资产投资公司发行"))));
        dto.setPartyCode(list.get(headList.indexOf("*资管计划发起人机构编码")));
        dto.setCheckCode(list.get(headList.indexOf("资管计划登记编码")));
        dto.setManagerId(list.get(headList.indexOf("*管理人")));
        dto.setTrusteeId(list.get(headList.indexOf("*托管人")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*金额")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*金额")))){
                dto.setAmt(new BigDecimal(list.get(headList.indexOf("*金额"))));
            }else {
                dto.setAmt(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETMNGPRODINFODTO + GlobalConstant.POINT + "amt" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setTruActType(SysDataUtil.getZZDictCodeFromName(DictCode.资金实际投向, list.get(headList.indexOf("*资金实际投向"))));
        dto.setInvestType(list.get(headList.indexOf(" *资金运用方式")));
        dto.setCapUseInduLevel2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf(" *资金运用行业"))));
        dto.setCapUseIndu(dict.get("industry"+dto.getCapUseInduLevel2()));
        dto.setProProperty(SysDataUtil.getZZDictCodeFromName(DictCode.资管计划属性, list.get(headList.indexOf("*资管计划属性"))));
        dto.setRateFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否有预期收益率"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("预期最高收益率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("预期最高收益率%")))){
                dto.setMaxRate(new BigDecimal(list.get(headList.indexOf("预期最高收益率%"))));
            }else {
                dto.setMaxRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETMNGPRODINFODTO + GlobalConstant.POINT + "maxRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("预期最低收益率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("预期最低收益率%")))){
                dto.setMinRate(new BigDecimal(list.get(headList.indexOf("预期最低收益率%"))));
            }else {
                dto.setMinRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETMNGPRODINFODTO + GlobalConstant.POINT + "minRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setBuyStruct(SysDataUtil.getZZDictCodeFromName(DictCode.购买结构, list.get(headList.indexOf("*购买结构"))));
        dto.setManageType(SysDataUtil.getZZDictCodeFromName(DictCode.管理方式, list.get(headList.indexOf("*管理方式"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*管理费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*管理费率%")))){
                dto.setMgrFeeRate(new BigDecimal(list.get(headList.indexOf("*管理费率%"))));
            }else {
                dto.setMgrFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETMNGPRODINFODTO + GlobalConstant.POINT + "mgrFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*托管费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*托管费率%")))){
                dto.setTruFeeRate(new BigDecimal(list.get(headList.indexOf("*托管费率%"))));
            }else {
                dto.setTruFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETMNGPRODINFODTO + GlobalConstant.POINT + "truFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*交易相关合计费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*交易相关合计费率%")))){
                dto.setTradeFeeRate(new BigDecimal(list.get(headList.indexOf("*交易相关合计费率%"))));
            }else {
                dto.setTradeFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETMNGPRODINFODTO + GlobalConstant.POINT + "tradeFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*中介服务机构合计费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*中介服务机构合计费率%")))){
                dto.setAgencyFeeRate(new BigDecimal(list.get(headList.indexOf("*中介服务机构合计费率%"))));
            }else {
                dto.setAgencyFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETMNGPRODINFODTO + GlobalConstant.POINT + "agencyFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf(" *其他合计费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf(" *其他合计费率%")))){
                dto.setOtherFeeRate(new BigDecimal(list.get(headList.indexOf(" *其他合计费率%"))));
            }else {
                dto.setOtherFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETMNGPRODINFODTO + GlobalConstant.POINT + "otherFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRemark(list.get(headList.indexOf("备注")));
        dto.setPrivateprdFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否投向金融资产投资公司发行的私募资产管理产品"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        //新增是否为银行理财产品和产品登记编码字段 add by tuqirong 2021-12-31 start
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("是否为银行理财产品")))) {
            dto.setFinancialProductFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否为银行理财产品"))));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("产品登记编码")))) {
            dto.setProdRegisterCode(list.get(headList.indexOf("产品登记编码")));
        }
        //新增是否为银行理财产品和产品登记编码字段 add by tuqirong 2021-12-31 end

        return dto;
    }

    /**
     * 维护信息-QDII结构性票据
     * @param list
     * @return
     */
    private SvrBussRepQDIIStructureInfoDto getSvrBussRepQDIIStructureInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepQDIIStructureInfoDto dto = new SvrBussRepQDIIStructureInfoDto();
        dto.setCountryArea(SysDataUtil.getZZDictCodeFromName(DictCode.所属国家或地区, list.get(headList.lastIndexOf("*所属国家或地区"))));
        dto.setContractName(list.get(headList.indexOf("*合约名称")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("票面利率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("票面利率%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("票面利率%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIISTRUCTUREINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("付息频率（个月/次）")))) {
            try {
                dto.setIntpayCycle(Long.valueOf(list.get(headList.indexOf("付息频率（个月/次）"))));
            }catch (Exception e){
                dto.setIntpayCycle(0L);
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIISTRUCTUREINFODTO + GlobalConstant.POINT + "intpayCycle" + GlobalConstant.UNDER_LINE + "isNumber" )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*固定收益部分所占比例(%)")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*固定收益部分所占比例(%)")))){
                dto.setIncomeRatio(new BigDecimal(list.get(headList.indexOf("*固定收益部分所占比例(%)"))));
            }else {
                dto.setIncomeRatio(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIISTRUCTUREINFODTO + GlobalConstant.POINT + "incomeRatio" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*衍生金融工具所占比例(%)")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*衍生金融工具所占比例(%)")))){
                dto.setDerInstrRatio(new BigDecimal(list.get(headList.indexOf("*衍生金融工具所占比例(%)"))));
            }else {
                dto.setDerInstrRatio(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIISTRUCTUREINFODTO + GlobalConstant.POINT + "derInstrRatio" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setDerInstrInvType(list.get(headList.indexOf("*衍生金融工具具体投资方式")));
        dto.setDerInstrTarget(list.get(headList.indexOf("*衍生金融工具挂钩的标的资产")));
        dto.setAcctMode(list.get(headList.indexOf("*利息结算方式")));
        dto.setSpecialCmt1(list.get(headList.indexOf("*含权情况说明")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*结构性票据最高收益率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*结构性票据最高收益率%")))){
                dto.setMaxYield(new BigDecimal(list.get(headList.indexOf("*结构性票据最高收益率%"))));
            }else {
                dto.setMaxYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIISTRUCTUREINFODTO + GlobalConstant.POINT + "maxYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("结构性票据最低收益率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("结构性票据最低收益率%")))){
                dto.setMinYield(new BigDecimal(list.get(headList.indexOf("结构性票据最低收益率%"))));
            }else {
                dto.setMinYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIISTRUCTUREINFODTO + GlobalConstant.POINT + "minYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("挂钩标的资产基准价格")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("挂钩标的资产基准价格")))){
                dto.setBasicPrice(new BigDecimal(list.get(headList.indexOf("挂钩标的资产基准价格"))));
            }else {
                dto.setBasicPrice(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIISTRUCTUREINFODTO + GlobalConstant.POINT + "basicPrice" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*挂钩标的资产登记日价格")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*挂钩标的资产登记日价格")))){
                dto.setRegDatePrice(new BigDecimal(list.get(headList.indexOf("*挂钩标的资产登记日价格"))));
            }else {
                dto.setRegDatePrice(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIISTRUCTUREINFODTO + GlobalConstant.POINT + "regDatePrice" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setTradeFee(list.get(headList.indexOf("*交易费")));
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-QDII股票/基金类资产
     * @param list
     * @return
     */
    private SvrBussQDIISharesInfoDto getSvrBussQDIISharesInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussQDIISharesInfoDto dto = new SvrBussQDIISharesInfoDto();
        //add by tuqirong 2022-01-08 start
        //由于list集合中存在多个"*所属国家或地区"，为避免获取"*所属国家或地区"位置错误，从第二个"*所属国家或地区"位置开始截取list集合后再获取相应的值
        int index1 = headList.indexOf("*所属国家或地区");
        headList = headList.subList(index1 + 1, headList.size());
        list = list.subList(index1 + 1, list.size());
        int index2 = headList.indexOf("*所属国家或地区");
        headList = headList.subList(index2 + 1, headList.size());
        list = list.subList(index2 + 1, list.size());
        //add by tuqirong 2022-01-08 end
        dto.setCountryArea(SysDataUtil.getZZDictCodeFromName(DictCode.所属国家或地区, list.get(headList.indexOf("*所属国家或地区"))));
        dto.setAssetId(list.get(headList.indexOf("*股票/基金代码")));
        dto.setAssetName(list.get(headList.indexOf("*股票/基金名称")));
        dto.setIssuePartyName(list.get(headList.lastIndexOf("*发行机构")));
        dto.setIndustryShares2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("*行业"))));
        dto.setIndustryShares(dict.get("industry"+dto.getIndustryShares2()));
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-QDII拆出/逆回购资产
     * @param list
     * @return
     */
    private SvrBussRepQDIIdemolitionInfoDto getSvrBussRepQDIIdemolitionInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepQDIIdemolitionInfoDto dto = new SvrBussRepQDIIdemolitionInfoDto();
        //add by tuqirong 2022-01-08 start
        //由于list集合中存在多个"*所属国家或地区"，为避免获取"*所属国家或地区"位置错误，从第一个"*所属国家或地区"位置开始截取list集合后再获取相应的值
        int index = headList.indexOf("*所属国家或地区");
        headList = headList.subList(index + 1, headList.size());
        list = list.subList(index + 1, list.size());
        //add by tuqirong 2022-01-08 end
        dto.setCountryArea(SysDataUtil.getZZDictCodeFromName(DictCode.所属国家或地区, list.get(headList.indexOf("*所属国家或地区"))));
        dto.setRivalParty(list.get(headList.indexOf("*对手方")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*年利率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*年利率%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("*年利率%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIIDEMOLITIONINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setBasis(SysDataUtil.getZZDictCodeFromName(DictCode.计息基础_中债, list.get(headList.indexOf("*计息基础"))));
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-QDII债券资产
     * @param list
     * @return
     */
    private SvrBussRepQDIIBondInfoDto getSvrBussRepQDIIBondInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepQDIIBondInfoDto dto = new SvrBussRepQDIIBondInfoDto();
        dto.setCountryArea(SysDataUtil.getZZDictCodeFromName(DictCode.所属国家或地区, list.get(headList.indexOf("*所属国家或地区"))));
        dto.setAssetName(list.get(headList.indexOf("*债券名称")));
        dto.setAssetId(list.get(headList.indexOf("*债券代码")));
        dto.setIssuePartyName(list.get(headList.indexOf("*发行机构")));
        dto.setIndustryLevel2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("*发行机构所属行业"))));
        dto.setIndustry(dict.get("industry"+dto.getIndustryLevel2()));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*期限（月）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*期限（月）")))){
                dto.setTermMonth(new BigDecimal(list.get(headList.indexOf("*期限（月）"))));
            }else {
                dto.setTermMonth(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIIBONDINFODTO + GlobalConstant.POINT + "termMonth" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setPartyGrade(list.get(headList.indexOf("*发行机构主体信用评级")));
        dto.setSecCreditGradeType(list.get(headList.indexOf("*债券信用评级")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*票面利率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*票面利率%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("*票面利率%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIIBONDINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*付息频率（个月/次）")))) {
            try {
                dto.setIntpayCycle(Long.valueOf(list.get(headList.indexOf("*付息频率（个月/次）"))));
            }catch (Exception e){
                dto.setIntpayCycle(0L);
                messages.add(new ValidMessage(rule.get(SVRBUSSREPQDIIBONDINFODTO + GlobalConstant.POINT + "intpayCycle" + GlobalConstant.UNDER_LINE + "isNumber" )));
            }
        }
        dto.setGuaranteeCmt(list.get(headList.indexOf("*担保情况说明")));
        dto.setGuaTypeRemark(list.get(headList.indexOf("*担保情况说明")));
        dto.setSpecialCmt(list.get(headList.indexOf("*是否含权等特殊条款情况说明")));
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-金融衍生品
     * @param list
     * @return
     */
    private SvrBussRepFinanciallInfoDto getSvrBussRepFinanciallInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepFinanciallInfoDto dto = new SvrBussRepFinanciallInfoDto();
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*名义本金")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*名义本金")))){
                dto.setNomCapi(new BigDecimal(list.get(headList.indexOf("*名义本金"))));
            }else {
                dto.setNomCapi(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPFINANCIALLINFODTO + GlobalConstant.POINT + "nomCapi" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty( list.get(headList.indexOf("*标的类别")))){
            String[] targetType3s = list.get(headList.indexOf("*标的类别")).split(GlobalConstant.COMMA);
            String targetType3 = "";
            for (int i=0;i<targetType3s.length;i++){
                targetType3 += SysDataUtil.getZZDictCodeFromName(DictCode.标的类别, targetType3s[i]) + GlobalConstant.COMMA;
            }
            dto.setTargetType3(targetType3.substring(0,targetType3.length()-1));
        }
        dto.setHoldPurpose(SysDataUtil.getZZDictCodeFromName(DictCode.持有目的, list.get(headList.indexOf("*持有目的"))));
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-权益类资产
     * @param list
     * @return
     */
    private SvrBussRepEquityInfoDto getSvrBussRepEquityInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepEquityInfoDto dto = new SvrBussRepEquityInfoDto();
        dto.setAssetId(list.get(headList.indexOf("股票代码")));
        dto.setAssetName(list.get(headList.indexOf(" *股票/企业名称")));
        dto.setSharesType(SysDataUtil.getZZDictCodeFromName(DictCode.股票类型, list.get(headList.indexOf("股票类型"))));
        dto.setIndustryShares2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("*行业"))));
        dto.setIndustryShares(dict.get("industry"+dto.getIndustryShares2()));
        dto.setInvestStage(SysDataUtil.getZZDictCodeFromName(DictCode.投资阶段, list.get(headList.indexOf("投资阶段"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("股权退出安排")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("股权退出安排")))){
                dto.setEquityQuit(DateUtil.parse(list.get(headList.indexOf("股权退出安排"))));
            }else {
                dto.setEquityQuit(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPEQUITYINFODTO + GlobalConstant.POINT + "equityQuit" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        dto.setSharesType1(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按规模划分, list.get(headList.indexOf("*企业类型（按规模划分）"))));
        dto.setSharesType3(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按技术领域划分, list.get(headList.indexOf("*企业类型（按技术领域划分）"))));
        dto.setSharesType2(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按经济类型划分, list.get(headList.indexOf("*企业类型（按经济类型划分）"))));
        dto.setIsPledge(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否为质押融资"))));
        dto.setDeptEquity(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否为债转股"))));
        dto.setRemark(list.get(headList.indexOf("备注")));
        dto.setBankSecPrefer(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否商业银行优先股"))));
        dto.setIsGovernFund(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否创业投资基金"))));
        dto.setIsunlisted(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否政府出资产业投资基金"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        return dto;
    }

    /**
     * 维护信息-票据类资产/信用证类资产
     * @param list
     * @return
     */
    private SvrBussRepAssetAstBillInfoDto getSvrBussRepAssetAstBillInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepAssetAstBillInfoDto dto = new SvrBussRepAssetAstBillInfoDto();
        dto.setIsRight(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否为收/受益权"))));
        dto.setIsResale(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否属于买入返售"))));
        dto.setNotstandSecType(SysDataUtil.getZZDictCodeFromName(DictCode.票据类信用证类资产类型, list.get(headList.indexOf("*类型"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*数量")))) {
            try {
                dto.setNotstandSecNum(Long.valueOf(list.get(headList.indexOf("*数量"))));
            }catch (Exception e){
                dto.setNotstandSecNum(0L);
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETASTBILLINFODTO + GlobalConstant.POINT + "notstandSecNum" + GlobalConstant.UNDER_LINE + "isNumber" )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*合计金额")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*合计金额")))){
                dto.setAmt(new BigDecimal(list.get(headList.indexOf("*合计金额"))));
            }else {
                dto.setAmt(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETASTBILLINFODTO + GlobalConstant.POINT + "amt" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*加权剩余期限（天）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*加权剩余期限（天）")))){
                dto.setRemainTerm(new BigDecimal(list.get(headList.indexOf("*加权剩余期限（天）"))));
            }else {
                dto.setRemainTerm(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETASTBILLINFODTO + GlobalConstant.POINT + "remainTerm" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*最长剩余期限（天）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*最长剩余期限（天）")))){
                dto.setMaxRemainTerm(new BigDecimal(list.get(headList.indexOf("*最长剩余期限（天）"))));
            }else {
                dto.setMaxRemainTerm(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETASTBILLINFODTO + GlobalConstant.POINT + "maxRemainTerm" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*最短剩余期限（天）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*最短剩余期限（天）")))){
                dto.setMinRemainTerm(new BigDecimal(list.get(headList.indexOf("*最短剩余期限（天）"))));
            }else {
                dto.setMinRemainTerm(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETASTBILLINFODTO + GlobalConstant.POINT + "minRemainTerm" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setIndustryShares2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("*行业"))));
        dto.setIndustryShares(dict.get("industry"+dto.getIndustryShares2()));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*贴现利率（%）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*贴现利率（%）")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("*贴现利率（%）"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETASTBILLINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setPppflag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*公共PPP"))));
        dto.setBondtostockFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否投向市场化债转股相关产品的部分"))));
        dto.setPrivateFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("投向地方政府融资平台的部分"))));
        dto.setIsBzhpj(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否为标准化票据"))));
        dto.setRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        //新增票据代码字段 add by tuqirong 2021-12-31 start
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("票据代码")))) {
            dto.setBillCode(list.get(headList.indexOf("票据代码")));
        }

        //新增资票据代码字段 add by tuqirong 2021-12-31 end
        return dto;
    }

    /**
     * 维护信息-非标准化债权类资产/新增可投资资产
     * @param list
     * @return
     */
    private SvrBussRepAssetCreditNonstdInfoDto getSvrBussRepAssetCreditNonstdInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepAssetCreditNonstdInfoDto dto = new SvrBussRepAssetCreditNonstdInfoDto();
        dto.setRepProfitType(SysDataUtil.getZZDictCodeFromName(DictCode.收受益权类型, list.get(headList.indexOf("收/受益权类型"))));
        dto.setRepIsResale(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否属于买入返售"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*金额")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*金额")))){
                dto.setAmt(new BigDecimal(list.get(headList.indexOf("*金额"))));
            }else {
                dto.setAmt(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "amt" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("份额面值")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("份额面值")))){
                dto.setRepPrinBaseamt(new BigDecimal(list.get(headList.indexOf("份额面值"))));
            }else {
                dto.setRepPrinBaseamt(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repPrinBaseamt" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                dto.setRepVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
            }else {
                dto.setRepVdate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repVdate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                dto.setRepMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
            }else {
                dto.setRepMdate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repMdate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("法定到期日")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("法定到期日")))){
                dto.setRepFmdate(DateUtil.parse(list.get(headList.indexOf("法定到期日"))));
            }else {
                dto.setRepFmdate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repFmdate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        dto.setRepRateFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否有预期收益率"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("项目收益率(利率)%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("项目收益率(利率)%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("项目收益率(利率)%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRepCouponSpecies(SysDataUtil.getZZDictCodeFromName(DictCode.计息类型, list.get(headList.indexOf("计息类型"))));
        dto.setInterestRuleFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("规则付息标识"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*付息频率（个月/次）")))) {
            try {
                dto.setRepIntpayCycle(Long.valueOf(list.get(headList.indexOf("*付息频率（个月/次）"))));
            }catch (Exception e){
                dto.setRepIntpayCycle(0L);
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repIntpayCycle" + GlobalConstant.UNDER_LINE + "isNumber" )));
            }

        }
        dto.setRepIntpayRule(SysDataUtil.getZZDictCodeFromName(DictCode.利息分布方式, list.get(headList.indexOf("利息分布方式"))));
        dto.setRepRepayCmt(list.get(headList.indexOf("*还本付息情况说明")));
        dto.setBasis(SysDataUtil.getZZDictCodeFromName(DictCode.计息基础_中债, list.get(headList.indexOf("*计息基础"))));
        dto.setBasisType(SysDataUtil.getZZDictCodeFromName(DictCode.基准利率, list.get(headList.indexOf(" 基准利率种类"))));
        dto.setFloatFactorFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否有浮动因子"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("浮动因子（%）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("浮动因子（%）")))){
                dto.setFloatFactor(new BigDecimal(list.get(headList.indexOf("浮动因子（%）"))));
            }else {
                dto.setFloatFactor(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "floatFactor" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("利差(BP)")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("利差(BP)")))){
                dto.setSpreadRate(new BigDecimal(list.get(headList.indexOf("利差(BP)"))));
            }else {
                dto.setSpreadRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "spreadRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRepStructGrade(SysDataUtil.getZZDictCodeFromName(DictCode.结构档次, list.get(headList.indexOf("结构档次"))));
        dto.setRepRepaymentType(SysDataUtil.getZZDictCodeFromName(DictCode.还本方式, list.get(headList.indexOf("还本方式"))));
        dto.setRepPeriodRepaymentFlag(SysDataUtil.getZZDictCodeFromName(DictCode.分期还本条款标识, list.get(headList.indexOf("*分期还本条款标识"))));
        dto.setRepBasicAssetType(list.get(headList.indexOf("基础资产类型")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("超额收益分配比例（%）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("超额收益分配比例（%）")))){
                dto.setRepExcessProfitRate(new BigDecimal(list.get(headList.indexOf("超额收益分配比例（%）"))));
            }else {
                dto.setRepExcessProfitRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repExcessProfitRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRepFinancie(list.get(headList.indexOf("*融资人")));
        dto.setRepFcrGradeIn(list.get(headList.indexOf("*融资人内部信用评级")));
        dto.setRepFcrGradeOut(list.get(headList.indexOf("外部评级机构名称及对融资人评级结果")));
        dto.setRepFinancierType1(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按规模划分,
                list.get(headList.indexOf("*融资人类型（按规模划分）"))));
        dto.setRepFinancierType3(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按经济类型划分,
                list.get(headList.indexOf("*融资人类型（按技术领域划分）"))));
        dto.setRepFinancierType2(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按技术领域划分,
                list.get(headList.indexOf("*融资人类型（按经济类型划分）"))));
        dto.setRepFinanceProj(list.get(headList.indexOf("*融资项目")));
        dto.setRepIndustryLevel2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类,
                list.get(headList.indexOf("*融资人所属行业"))));
        dto.setIndustryLevel2Type(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类,
                list.get(headList.indexOf("*融资人所属行业"))));
        dto.setRepIndustry1(dict.get("industry"+dto.getRepIndustryLevel2()));
        dto.setRepIndustryFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债,
                list.get(headList.indexOf("*项目是否属于重点监控行业和领域"))));
        dto.setRepMonitType(SysDataUtil.getZZDictCodeFromName(DictCode.重点监控行业和领域类别,
                list.get(headList.indexOf("重点监控行业和领域类别"))));
        dto.setRepMonTypeComm(list.get(headList.indexOf("重点监控行业和领域类别说明")));
        dto.setRepGuaType(SysDataUtil.getZZDictCodeFromName(DictCode.担保方式, list.get(headList.indexOf("担保方式"))));
        dto.setRepPledgeType(SysDataUtil.getZZDictCodeFromName(DictCode.抵质押物类型, list.get(headList.indexOf("抵质押物类型"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("抵质押物价值（元）")))){
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("抵质押物价值（元）")))){
                dto.setRepPledgeValue(new BigDecimal(list.get(headList.indexOf("抵质押物价值（元）"))));
            }else {
                dto.setRepPledgeValue(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repPledgeValue" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRepNatureGuarantee(SysDataUtil.getZZDictCodeFromName(DictCode.担保性质, list.get(headList.indexOf("担保性质"))));
        dto.setRepRelationFinan(SysDataUtil.getZZDictCodeFromName(DictCode.担保人与融资人关系, list.get(headList.indexOf("担保人与融资人关系"))));
        dto.setRepGuaTypeRemark(list.get(headList.indexOf("担保情况说明")));
        dto.setRepFinancierGrade(SysDataUtil.getZZDictCodeFromName(DictCode.资产评级, list.get(headList.indexOf("*融资人主体评级"))));
        dto.setRepInGrade(list.get(headList.indexOf("*资产内部评级")));
        dto.setRepOutGrade(SysDataUtil.getZZDictCodeFromName(DictCode.资产评级, list.get(headList.indexOf("*资产外部评级"))));
        dto.setRepContainType(SysDataUtil.getZZDictCodeFromName(DictCode.含权类型, list.get(headList.indexOf("含权类型"))));
        dto.setRepExerciseType(SysDataUtil.getZZDictCodeFromName(DictCode.资产行权方式, list.get(headList.indexOf("行权方式"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("固定行权日")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("固定行权日")))){
                dto.setRepExerciseDate(DateUtil.parse(list.get(headList.indexOf("固定行权日"))));
            }else {
                dto.setRepExerciseDate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repExerciseDate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("首次行权日期")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("首次行权日期")))){
                dto.setRepFirstExerciseDate(DateUtil.parse(list.get(headList.indexOf("首次行权日期"))));
            }else {
                dto.setRepFirstExerciseDate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repFirstExerciseDate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("行权周期")))) {
            try {
                dto.setRepExercisePeriod(Integer.valueOf(list.get(headList.indexOf("行权周期"))));
            }catch (Exception e){
                dto.setRepExercisePeriod(0);
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repExercisePeriod" + GlobalConstant.UNDER_LINE + "isNumber" )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("行权价格")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("行权价格")))){
                dto.setRepExercisePrice(new BigDecimal(list.get(headList.indexOf("行权价格"))));
            }else {
                dto.setRepExercisePrice(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repExercisePrice" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRepPerpetualClauseType(SysDataUtil.getZZDictCodeFromName(DictCode.永续条款类型, list.get(headList.indexOf("永续条款类型"))));
        dto.setRepDeferredInterestType(SysDataUtil.getZZDictCodeFromName(DictCode.利息递延条款类型, list.get(headList.indexOf("利息递延条款类型"))));
        dto.setRepDeferredInterestInt(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("递延利息是否计息"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("首次重定价日期")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("首次重定价日期")))){
                dto.setRepFirstReprceDate(DateUtil.parse(list.get(headList.indexOf("首次重定价日期"))));
            }else {
                dto.setRepFirstReprceDate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repFirstReprceDate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("重定价周期")))) {
            try {
                dto.setRepReprceCycle(Integer.valueOf(list.get(headList.indexOf("重定价周期"))));
            }catch (Exception e){
                dto.setRepReprceCycle(0);
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repReprceCycle" + GlobalConstant.UNDER_LINE + "isNumber" )));
            }
        }
        dto.setRepPartRedemFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("部分赎回标识"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("部分赎回比例（%）")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("部分赎回比例（%）")))){
                dto.setRepPartRedemRate(new BigDecimal(list.get(headList.indexOf("部分赎回比例（%）"))));
            }else {
                dto.setRepPartRedemRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "repPartRedemRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRepChose(list.get(headList.indexOf("*选择权")));
        dto.setRepConditionCmm(list.get(headList.indexOf("*行权条件说明")));
        dto.setRepFinArea(SysDataUtil.getZZDictCodeFromName(DictCode.行政区划_市, list.get(headList.indexOf("*融资人所属地区"))));
        dto.setRepFinOrgCode(list.get(headList.indexOf("*融资人组织机构（社会信用）代码")));
        dto.setCreditOrgCode(list.get(headList.indexOf("增信机构代码")));
        dto.setCreditOrgName(list.get(headList.indexOf("增信机构名称")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*融资总费率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*融资总费率%")))){
                dto.setTotalFeeRate(new BigDecimal(list.get(headList.indexOf("*融资总费率%"))));
            }else {
                dto.setTotalFeeRate(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPASSETCREDITNONSTDINFODTO + GlobalConstant.POINT + "totalFeeRate" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRepProIndustryLevel2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类, list.get(headList.indexOf("*融资项目所属行业"))));
        dto.setRepProIndustry(dict.get("industry"+dto.getRepProIndustryLevel2()));
        dto.setRepRemark(list.get(headList.indexOf("备注")));
        dto.setRepPrivateprdFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("地方政府融资平台"))));
        dto.setPppflag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("公共PPP"))));
        dto.setBondtostockFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否投向市场化债转股相关产品的部分"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        //新增资产代码字段 add by tuqirong 2021-12-31 start
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("资产代码")))) {
            dto.setRepCreditAssetCode(list.get(headList.indexOf("资产代码")));
        }
        //新增资产代码字段 add by tuqirong 2021-12-31 end
        //add by tuqirong 2022-01-21 start  导入模板新增字段处理
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("是否资产四级分类为“北金所债权”")))) {
            dto.setIsBjszq(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否资产四级分类为“北金所债权”"))));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("结构化业务类型")))) {
            dto.setConstracteBusiType(SysDataUtil.getZZDictCodeFromName(DictCode.结构化业务类型, list.get(headList.indexOf("结构化业务类型"))));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("结构化层级")))) {
            dto.setConstracteGrade(SysDataUtil.getZZDictCodeFromName(DictCode.结构化层级, list.get(headList.indexOf("结构化层级"))));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("结构化杠杆倍数")))) {
            dto.setConstracteHeaverTimes(new BigDecimal(list.get(headList.indexOf("结构化杠杆倍数"))));
        }
        //add by tuqirong 2022-01-21 end  导入模板新增字段处理
        return dto;
    }

    /**
     * 维护信息-本行/他行存款||大额存单
     * @param list
     * @return
     */
    private SvrBussRepDepositInfoDto getSvrBussRepDepositInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepDepositInfoDto dto = new SvrBussRepDepositInfoDto();
        dto.setRepBankName(list.get(headList.indexOf("资金存入银行")));
        dto.setBankName(list.get(headList.indexOf("资金存入银行")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*存款金额")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*存款金额")))){
                dto.setAmt(new BigDecimal(list.get(headList.indexOf("*存款金额"))));
            }else {
                dto.setAmt(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPDEPOSITINFODTO + GlobalConstant.POINT + "amt" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                dto.setRepVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
            }else {
                dto.setRepVdate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPDEPOSITINFODTO + GlobalConstant.POINT + "repVdate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }

        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                dto.setRepMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
            }else {
                dto.setRepMdate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPDEPOSITINFODTO + GlobalConstant.POINT + "repMdate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*年利率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*年利率%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("*年利率%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPDEPOSITINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRepBasis(SysDataUtil.getZZDictCodeFromName(DictCode.计息基础_中债, list.get(headList.indexOf("*计息基础"))));
        dto.setBasis(SysDataUtil.getZZDictCodeFromName(DictCode.计息基础_中债, list.get(headList.indexOf("*计息基础"))));
        dto.setRepDepoType(SysDataUtil.getZZDictCodeFromName(DictCode.存款类型, list.get(headList.indexOf("存款类型"))));
        if (StringUtil.isNotEmpty( list.get(headList.indexOf("结构性存款挂钩标的类别")))){
            String[] repTargetTypes = list.get(headList.indexOf("结构性存款挂钩标的类别")).split(GlobalConstant.COMMA);
            String repTargetType = "";
            for (int i=0;i<repTargetTypes.length;i++){
                repTargetType += SysDataUtil.getZZDictCodeFromName(DictCode.标的类别, repTargetTypes[i]) + GlobalConstant.COMMA;
            }
            dto.setRepTargetType(repTargetType.substring(0,repTargetType.length()-1));
        }
        dto.setRepTarget(list.get(headList.indexOf("结构性存款挂钩标的")));
        dto.setRepRemark(list.get(headList.indexOf("备注")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类")))
                && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setRepPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类")))
                && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setRepPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类")))
                && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setRepPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        //新增字段存款账号 add by tuqirong 2021-12-31 start
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*存款账号")))) {
            dto.setRepAccountNo(list.get(headList.indexOf("*存款账号")));
        }
        //新增字段存款账号 add by tuqirong 2021-12-31 end
        return dto;
    }

    /**
     * 维护信息-拆放同业/买入返售（资产）||同业拆入/卖出回购（负债）
     * @param list
     * @return
     */
    private SvrBussRepPlacementInfoDto getSvrBussRepPlacementInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussRepPlacementInfoDto dto = new SvrBussRepPlacementInfoDto();
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*起息日")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*起息日")))){
                dto.setVdate(DateUtil.parse(list.get(headList.indexOf("*起息日"))));
            }else {
                dto.setVdate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPPLACEMENTINFODTO + GlobalConstant.POINT + "vdate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*到期日")))) {
            if (CommonUtil.isEnDateStr(list.get(headList.indexOf("*到期日")))){
                dto.setMdate(DateUtil.parse(list.get(headList.indexOf("*到期日"))));
            }else {
                dto.setMdate(new Date());
                messages.add(new ValidMessage(rule.get(SVRBUSSREPPLACEMENTINFODTO + GlobalConstant.POINT + "mdate" + GlobalConstant.UNDER_LINE + ISVALIDDATE )));
            }
        }
        dto.setRivalParty(list.get(headList.indexOf("*对手方")));
        dto.setRivalPartyType(SysDataUtil.getZZDictCodeFromName(DictCode.对手方类型, list.get(headList.indexOf("*对手方类型"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*年利率%")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("*年利率%")))){
                dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("*年利率%"))));
            }else {
                dto.setAssetYield(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPPLACEMENTINFODTO + GlobalConstant.POINT + "assetYield" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setBasis(SysDataUtil.getZZDictCodeFromName(DictCode.计息基础_中债, list.get(headList.indexOf("*计息基础"))));
        dto.setTargetType1(SysDataUtil.getZZDictCodeFromName(DictCode.回购标的类别, list.get(headList.indexOf("回购标的类别"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("回购标的金额")))) {
            if (CommonUtil.isFloatNumeric(list.get(headList.indexOf("回购标的金额")))){
                dto.setTargetAmt(new BigDecimal(list.get(headList.indexOf("回购标的金额"))));
            }else {
                dto.setTargetAmt(new BigDecimal(0));
                messages.add(new ValidMessage(rule.get(SVRBUSSREPPLACEMENTINFODTO + GlobalConstant.POINT + "targetAmt" + GlobalConstant.UNDER_LINE + ISNUMBER2 )));
            }
        }
        dto.setRemark(list.get(headList.indexOf("备注")));
        dto.setCheckCode(list.get(headList.indexOf("对应资管计划代码")));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类")))
                && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类")))
                && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类")))
                && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        return dto;
    }

    /**
     * 维护信息-债券类资产/理财直接融资工具/同业存单
     * @param list
     * @return
     */
    private SvrBussFinBondRepInfoDto getSvrBussFinBondRepInfoDto(List<ValidMessage> messages,List<String> list,List<String> headList){
        SvrBussFinBondRepInfoDto dto = new SvrBussFinBondRepInfoDto();
        dto.setAssetId(list.get(headList.indexOf("*代码")));
        dto.setRepSectype(SysDataUtil.getZZDictCodeFromName(DictCode.具体类别, list.get(headList.indexOf("具体类别"))));
        dto.setRepIssueMode(SysDataUtil.getZZDictCodeFromName(DictCode.发行方式, list.get(headList.indexOf("*发行方式"))));
        dto.setSecMainGrade(SysDataUtil.getZZDictCodeFromName(DictCode.主体评级_中债, list.get(headList.indexOf("*主体评级"))));
        dto.setRepPartyType1(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按规模划分,
                list.get(headList.indexOf("*发行机构类型（按规模划分）"))));
        dto.setRepPartyType3(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按技术领域划分,
                list.get(headList.indexOf("*发行机构类型（按技术领域划分）"))));
        dto.setRepPartyType2(SysDataUtil.getZZDictCodeFromName(DictCode.发行机构类型_按经济类型划分,
                list.get(headList.indexOf("*发行机构类型（按经济类型划分）"))));
        dto.setRepIndustryLevel2(SysDataUtil.getZZDictCodeFromName(DictCode.机构所属行业二级分类,
                list.get(headList.indexOf("*发行机构所属行业"))));
        dto.setRepIndustry(dict.get("industry"+dto.getRepIndustryLevel2()));
        dto.setRepRegistOrg(SysDataUtil.getZZDictCodeFromName(DictCode.登记托管机构_中债, list.get(headList.indexOf("*登记托管机构"))));
        dto.setRepRegistorgComment(SysDataUtil.getZZDictCodeFromName(DictCode.登记托管机构_监管报送, list.get(headList.indexOf("登记托管机构说明"))));
        dto.setRepRemark(list.get(headList.indexOf("备注")));
        dto.setBankSecZxzq(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否专项债券"))));
        dto.setRepIsFbzb(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否非标转标资产"))));
        dto.setRepBankSecCapital(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否商业银行二级资本债"))));
        dto.setRepBankSecPerpetual(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("是否商业银行永续债"))));
        dto.setIsPerpetual(SysDataUtil.getZZDictCodeFromName(DictCode.永续债属性, list.get(headList.indexOf("永续债属性"))));
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行一级分类"))) && list.get(headList.indexOf("*新人行一级分类")).length()>2){
            dto.setRepPbcAssetType1(list.get(headList.indexOf("*新人行一级分类")).substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行二级分类"))) && list.get(headList.indexOf("*新人行二级分类")).length()>2){
            dto.setRepPbcAssetType2(list.get(headList.indexOf("*新人行二级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }

        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*新人行三级分类"))) && list.get(headList.indexOf("*新人行三级分类")).length()>2){
            dto.setRepPbcAssetType3(list.get(headList.indexOf("*新人行三级分类")).replace(GlobalConstant.UNDER_LINE,"").substring(0,3));
        }
        //add by tuqirong 2022-01-21 start 导入模板新增字段处理
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*票面利率%")))) {
            dto.setAssetYield(new BigDecimal(list.get(headList.indexOf("*票面利率%"))));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*发行人")))) {
            dto.setChbIssuerId(list.get(headList.indexOf("*发行人")));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("*是否含权债")))) {
            dto.setOptionFlag(SysDataUtil.getZZDictCodeFromName(DictCode.是否_中债, list.get(headList.indexOf("*是否含权债"))));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("结构化业务类型")))) {
            dto.setConstracteBusiType(SysDataUtil.getZZDictCodeFromName(DictCode.结构化业务类型, list.get(headList.indexOf("结构化业务类型"))));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("结构化层级")))) {
            dto.setConstracteGrade(SysDataUtil.getZZDictCodeFromName(DictCode.结构化层级, list.get(headList.indexOf("结构化业务类型"))));
        }
        if (StringUtil.isNotEmpty(list.get(headList.indexOf("结构化杠杆倍数")))) {
            dto.setConstracteHeaverTimes(new BigDecimal(list.get(headList.indexOf("结构化杠杆倍数"))));
        }
        //add by tuqirong 2022-01-21 end 导入模板新增字段处理
        return dto;
    }

    public List<ExcelValidDto> checkExcelTemp(String fileName){
        List<ExcelValidDto> tempCheckResult = new ArrayList<ExcelValidDto>();
        String errorMsg = "导入模板不一致，请重新选择文件！";
        ExcelValidDto excelValidDto = new ExcelValidDto();
        excelValidDto.setErrorInfo(errorMsg);
        tempCheckResult.add(excelValidDto);
        List<List<String>> tempDataList;
        try {
            tempDataList =  ExcelUtil.readToList(
                    new File(fileName), 0);
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(),e);
            // Excel解析异常
            return tempCheckResult;
        }
        if (StringUtil.isEmptyList(tempDataList)) {
            // Excel没有有效数据
            return tempCheckResult;
        }
        List<String> excelTypeList = tempDataList.get(0);
        List<String> excelHeadList = tempDataList.get(1);
        List<String> headList = new ArrayList();
        if (excelTypeList.contains("本行/他行存款||大额存单")){
            getHeadList_01(headList);
        }else if (excelTypeList.contains("拆放同业及债券买入返售/同业拆入及卖出回购")){
            getHeadList_02(headList);
        }else if (excelTypeList.contains("债券类资产/理财直接融资工具/同业存单")){
            getHeadList_03(headList);
        }else if (excelTypeList.contains("非标准化债权类资产/新增可投资资产")){
            getHeadList_04(headList);
        }else if (!Collections.disjoint(excelTypeList,Arrays.asList("票据类/信用证类资产","贵金属/商品","另类资产","委外投资——协议方式","其他（负债）类资产"))){
            getHeadList_05(headList);
        }else if (excelTypeList.contains("权益类资产")){
            getHeadList_06(headList);
        }else if (excelTypeList.contains("金融衍生品")){
            getHeadList_07(headList);
        }else if (!Collections.disjoint(excelTypeList,Arrays.asList("QDII债券资产","QDII拆出/逆回购资产","QDII股票/基金类资产","QDII结构性票据类资产"))){
            getHeadList_08(headList);
        }else if (excelTypeList.contains("资产管理产品")){
            getHeadList_12(headList);
        }else if (excelTypeList.contains("其他非标准化债权类/权益类/代客境外理财投资QDII/新增可投资资产")){
            getHeadList_14(headList);
        }else if (excelTypeList.contains("公募基金/私募基金")){
            getHeadList_L01(headList);
        }else {
            return tempCheckResult;
        }
        if (excelHeadList.size() < headList.size()) {
            // Excel中列数少于要求列数
            tempCheckResult.get(0).setErrorInfo("Excel中列数少于要求列数！");
            return tempCheckResult;
        }
        if (excelHeadList.size() > headList.size()) {
            // Excel中列数多于要求列数
            tempCheckResult.get(0).setErrorInfo("Excel中列数多于要求列数！");
            return tempCheckResult;
        }
        for (int i = 0; i < headList.size(); i++ ) {
            if (!excelHeadList.get(i).contains(headList.get(i))) {
                // Excel中列不一致
                return tempCheckResult;
            }
        }
        return null;
    }

    private void getHeadList_gt(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
    }

    private void getHeadList_01(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("资金存入银行");
        headList.add("*存款账号");
        headList.add("*存款金额");
        headList.add("*名称");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("*年利率%");
        headList.add("*计息基础");
        headList.add("存款类型");
        headList.add("结构性存款挂钩标的类别");
        headList.add("结构性存款挂钩标的");
        headList.add("备注");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_02(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("*名称");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("*对手方");
        headList.add("*对手方类型");
        headList.add("*年利率%");
        headList.add("*计息基础");
        headList.add("回购标的类别");
        headList.add("回购标的金额");
        headList.add("备注");
        headList.add("对应资管计划代码");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_03(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("*代码");
        headList.add("*名称");
        headList.add("具体类别");
        headList.add("*发行方式");
        headList.add("*主体评级");
        headList.add("*发行机构类型（按规模划分）");
        headList.add("*发行机构类型（按技术领域划分）");
        headList.add("*发行机构类型（按经济类型划分）");
        headList.add("*发行机构所属行业");
        headList.add("*登记托管机构");
        headList.add("登记托管机构说明");
        headList.add("备注");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("是否专项债券");
        headList.add("是否非标转标资产");
        headList.add("是否商业银行二级资本债");
        headList.add("是否商业银行永续债");
        headList.add("永续债属性");
        headList.add("*票面利率%");
        headList.add("*发行人");
        headList.add("*是否含权债");
        headList.add("结构化业务类型");
        headList.add("结构化层级");
        headList.add("结构化杠杆倍数");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_04(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("收/受益权类型");
        headList.add("*是否属于买入返售");
        headList.add("*名称");
        headList.add("资产代码");
        headList.add("*金额");
        headList.add("份额面值");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("法定到期日");
        headList.add("*是否有预期收益率");
        headList.add("项目收益率(利率)%");
        headList.add("计息类型");
        headList.add("规则付息标识");
        headList.add("*付息频率（个月/次）");
        headList.add("利息分布方式");
        headList.add("*还本付息情况说明");
        headList.add("*计息基础");
        headList.add(" 基准利率种类");
        headList.add("是否有浮动因子");
        headList.add("浮动因子（%）");
        headList.add("利差(BP)");
        headList.add("结构档次");
        headList.add("还本方式");
        headList.add("*分期还本条款标识");
        headList.add("基础资产类型");
        headList.add("超额收益分配比例（%）");
        headList.add("*融资人");
        headList.add("*融资人内部信用评级");
        headList.add("外部评级机构名称及对融资人评级结果");
        headList.add("*融资人类型（按规模划分）");
        headList.add("*融资人类型（按技术领域划分）");
        headList.add("*融资人类型（按经济类型划分）");
        headList.add("*融资项目");
        headList.add("*融资人所属行业");
        headList.add("*项目是否属于重点监控行业和领域");
        headList.add("重点监控行业和领域类别");
        headList.add("重点监控行业和领域类别说明");
        headList.add("担保方式");
        headList.add("抵质押物类型");
        headList.add("抵质押物价值（元）");
        headList.add("担保性质");
        headList.add("担保人与融资人关系");
        headList.add("担保情况说明");
        headList.add("*融资人主体评级");
        headList.add("*资产内部评级");
        headList.add("*资产外部评级");
        headList.add("含权类型");
        headList.add("行权方式");
        headList.add("固定行权日");
        headList.add("首次行权日期");
        headList.add("行权周期");
        headList.add("行权价格");
        headList.add("永续条款类型");
        headList.add("利息递延条款类型");
        headList.add("递延利息是否计息");
        headList.add("首次重定价日期");
        headList.add("重定价周期");
        headList.add("部分赎回标识");
        headList.add("部分赎回比例（%）");
        headList.add("*选择权");
        headList.add("*行权条件说明");
        headList.add("*融资人所属地区");
        headList.add("*融资人组织机构（社会信用）代码");
        headList.add("增信机构代码");
        headList.add("增信机构名称");
        headList.add("*融资总费率%");
        headList.add("*融资项目所属行业");
        headList.add("备注");
        headList.add("地方政府融资平台");
        headList.add("公共PPP");
        headList.add("是否投向市场化债转股相关产品的部分");
        headList.add("是否资产四级分类为“北金所债权”");
        headList.add("结构化业务类型");
        headList.add("结构化层级");
        headList.add("结构化杠杆倍数");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_05(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("*是否为收/受益权");
        headList.add("*是否属于买入返售");
        headList.add("*类型");
        headList.add("是否为标准化票据");
        headList.add("票据代码");
        headList.add("*数量");
        headList.add("*合计金额");
        headList.add("*加权剩余期限（天）");
        headList.add("*最长剩余期限（天）");
        headList.add("*最短剩余期限（天）");
        headList.add("*行业");
        headList.add("*贴现利率（%）");
        headList.add("*公共PPP");
        headList.add("*是否投向市场化债转股相关产品的部分");
        headList.add("投向地方政府融资平台的部分");
        headList.add("*所属国家或地区");
        headList.add("期限（天）");
        headList.add("*资产价值");
        headList.add(" 资产收益率%");
        headList.add("*所属国家或地区");
        headList.add("*资产价值");
        headList.add("资产收益率%");
        headList.add("委外投资协议名称");
        headList.add("委外投资协议编号");
        headList.add("受托人");
        headList.add("实际管理人");
        headList.add("托管人");
        headList.add("委托投资金额");
        headList.add("资金实际投向");
        headList.add("资金运用方式");
        headList.add("资金运用行业");
        headList.add("投资运作起始日期");
        headList.add("投资运作终止日期");
        headList.add("委外投资属性");
        headList.add("是否有预期收益率");
        headList.add("预期最高收益率");
        headList.add("预期最低收益率");
        headList.add("管理费率%");
        headList.add("托管费率%");
        headList.add("交易相关合计费率%");
        headList.add("中介服务机构合计费率%");
        headList.add("其他合计费率%");
        headList.add("*所属国家或地区");
        headList.add("期限（天）");
        headList.add("*负债规模");
        headList.add(" 利率%");
        headList.add("*是否为同业借款");
        headList.add("*名称");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("备注");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_06(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("股票代码");
        headList.add(" *股票/企业名称");
        headList.add("股票类型");
        headList.add("*行业");
        headList.add("投资阶段");
        headList.add("股权退出安排");
        headList.add("*企业类型（按规模划分）");
        headList.add("*企业类型（按技术领域划分）");
        headList.add("*企业类型（按经济类型划分）");
        headList.add("*是否为质押融资");
        headList.add("*是否为债转股");
        headList.add("备注");
        headList.add("是否商业银行优先股");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("是否创业投资基金");
        headList.add("是否政府出资产业投资基金");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_07(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("*名称");
        headList.add("*名义本金");
        headList.add("*标的类别");
        headList.add("*持有目的");
        headList.add("备注");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_08(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("*所属国家或地区");
        headList.add("*债券名称");
        headList.add("*债券代码");
        headList.add("*发行机构");
        headList.add("*发行机构所属行业");
        headList.add("*期限（月）");
        headList.add("*发行机构主体信用评级");
        headList.add("*债券信用评级");
        headList.add("*票面利率%");
        headList.add("*付息频率（个月/次）");
        headList.add("*担保情况说明");
        headList.add("*是否含权等特殊条款情况说明");
        headList.add("*所属国家或地区");
        headList.add("*对手方");
        headList.add("*年利率%");
        headList.add("*计息基础");
        headList.add("*所属国家或地区");
        headList.add("*股票/基金代码");
        headList.add("*股票/基金名称");
        headList.add("*发行机构");
        headList.add("*行业");
        headList.add("*所属国家或地区");
        headList.add("*合约名称");
        headList.add("票面利率%");
        headList.add("付息频率（个月/次）");
        headList.add("*固定收益部分所占比例(%)");
        headList.add("*衍生金融工具所占比例(%)");
        headList.add("*衍生金融工具具体投资方式");
        headList.add("*衍生金融工具挂钩的标的资产");
        headList.add("*利息结算方式");
        headList.add("*含权情况说明");
        headList.add("*结构性票据最高收益率%");
        headList.add("结构性票据最低收益率%");
        headList.add("挂钩标的资产基准价格");
        headList.add("*挂钩标的资产登记日价格");
        headList.add("*交易费");
        headList.add("*名称");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("备注");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_12(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add(" *资管计划名称");
        headList.add("是否为银行理财产品");
        headList.add("产品登记编码");
        headList.add("是否由金融资产投资公司发行");
        headList.add("*资管计划发起人机构编码");
        headList.add("资管计划登记编码");
        headList.add("*管理人");
        headList.add("*托管人");
        headList.add("*金额");
        headList.add("*资金实际投向");
        headList.add(" *资金运用方式");
        headList.add(" *资金运用行业");
        headList.add("*资管计划成立日期");
        headList.add("*资管计划终止日期");
        headList.add("*资管计划属性");
        headList.add("*是否有预期收益率");
        headList.add("预期最高收益率%");
        headList.add("预期最低收益率%");
        headList.add("*购买结构");
        headList.add("*管理方式");
//        headList.add("*是否掌控计划底层资产详情");
        headList.add("*管理费率%");
        headList.add("*托管费率%");
        headList.add("*交易相关合计费率%");
        headList.add("*中介服务机构合计费率%");
        headList.add(" *其他合计费率%");
        headList.add("备注");
        headList.add("是否投向金融资产投资公司发行的私募资产管理产品");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_14(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("*名称");
        headList.add("*行内资产类别");
        headList.add("行内资产类别说明");
        headList.add("*金额");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("*所属国家或地区");
        headList.add("*是否有预期收益率");
        headList.add("项目收益率（利率）%");
        headList.add("*付息频率（个月/次）");
        headList.add("*融资人");
        headList.add("*融资人组织机构（社会信用）代码");
        headList.add("外部评级机构名称及对融资人评级结果");
        headList.add("*融资人类型（按规模划分）");
        headList.add("*融资人类型（按技术领域划分）");
        headList.add("*融资人类型（按经济类型划分）");
        headList.add("*融资项目");
        headList.add("*融资人所属行业");
        headList.add("*融资项目所属行业");
        headList.add("*项目是否属于重点监控行业和领域");
        headList.add("重点监控行业和领域类别");
        headList.add("重点监控行业和领域类别说明");
        headList.add("对应资产外部评级");
        headList.add("担保方式");
        headList.add("抵质押物类型");
        headList.add("抵质押物价值（元）");
        headList.add("担保性质");
        headList.add("担保人与融资人关系");
        headList.add("担保情况说明");
        headList.add("是否为债转股");
        headList.add("备注");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }

    private void getHeadList_L01(List<String> headList){
        headList.add("*行内资产/负债编码");
        headList.add("*发行机构代码");
        headList.add("*资产/负债类别");
        headList.add("*交易流通场所");
        headList.add("*币种");
        headList.add("*资产一级分类");
        headList.add("*资产二级分类");
        headList.add("*基金代码");
        headList.add("*基金名称");
        headList.add("*起息日");
        headList.add("*到期日");
        headList.add("*行业");
        headList.add("*登记备案机构");
        headList.add("是否为固定收益类");
        headList.add("是否属于政府投资基金");
        headList.add("政府投资基金投向");
        headList.add("是否由金融资产投资公司发行");
        headList.add("基金公司名称");
        headList.add("*基金管理机构名称");
        headList.add("*基金托管机构名称");
        headList.add("投资阶段");
        headList.add("投资企业类型（按规模划分）");
        headList.add("投资企业类型（按技术领域划分）");
        headList.add("投资企业类型（按经济类型划分）");
        headList.add("*基金投资资产");
        headList.add("备注");
        headList.add("是否投向金融资产投资公司或其附属机构发行的私募股权投资基金");
        headList.add("*管理人");
        headList.add("*是否政府出资产业投资基金");
        headList.add("*新人行一级分类");
        headList.add("*新人行二级分类");
        headList.add("*新人行三级分类");
    }
    public void processAssetInfoDetailDtos(List<AssetInfoDetailDto> list){
        if(list !=null && list.size()>0){
            list.forEach(e->{
                if(!StringUtil.isEmpty(e.getAssetType())){
                    switch (e.getAssetType()){
                        case "01":
                            try {
                                if(StringUtil.isNotEmpty(e.getAmt())) {
                                    e.setAmt01(e.getAmt().toString());
                                }
                                e.setVdate01(DateUtil.format(e.getVdate()));
                                e.setMdate01(DateUtil.format(e.getMdate()));
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield01("0.00000");
                                    }else{
                                        e.setAssetYield01(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getBasis())) {
                                    e.setBasis01(e.getBasis() + " " +SysDataUtil.getNameFromDataDict(DictCode.计息基础_中债, e.getBasis()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case "02":
                            try {
                                e.setVdate02(DateUtil.format(e.getVdate()));
                                e.setMdate02(DateUtil.format(e.getMdate()));
                                e.setRivalParty02(e.getRivalParty());
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield02("0.00000");
                                    }else{
                                        e.setAssetYield02(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getBasis())) {
                                    e.setBasis02(e.getBasis() + " " + SysDataUtil.getNameFromDataDict(DictCode.计息基础_中债, e.getBasis()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case "03":
                            try {
                                e.setAssetId03(e.getAssetId());
                                e.setAssetName03(e.getAssetName());
                                if(StringUtil.isNotEmpty(e.getIndustryLevel2())) {
                                    e.setIndustryLevel203(e.getIndustryLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryLevel2()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case "04":
                            try {
                                if(StringUtil.isNotEmpty(e.getIsResale())) {
                                    e.setIsResale04(e.getIsResale() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getIsResale()));
                                }
                                e.setAssetName04(e.getAssetName());
                                if(StringUtil.isNotEmpty(e.getAmt())) {
                                    e.setAmt04(e.getAmt().toString());
                                }
                                e.setVdate04(DateUtil.format(e.getVdate()));
                                e.setMdate04(DateUtil.format(e.getMdate()));
                                if(StringUtil.isNotEmpty(e.getRateFlag())) {
                                    e.setRateFlag04(e.getRateFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getRateFlag()));
                                }
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield04("0.00000");
                                    }else{
                                        e.setAssetYield04(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getIntpayCycle())) {
                                    e.setIntpayCycle04(e.getIntpayCycle().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getBasis())) {
                                    e.setBasis04(e.getBasis() + " " + SysDataUtil.getNameFromDataDict(DictCode.计息基础_中债, e.getBasis()));
                                }
                                e.setFinancie04(e.getFinancie());
                                e.setFcrGradeOut04(e.getFcrGradeOut());
                                if(StringUtil.isNotEmpty(e.getFinancierType1())) {
                                    e.setFinancierType104(e.getFinancierType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getFinancierType1()));
                                }
                                if(StringUtil.isNotEmpty(e.getFinancierType2())) {
                                    e.setFinancierType204(e.getFinancierType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按技术领域划分, e.getFinancierType2()));
                                }
                                if(StringUtil.isNotEmpty(e.getFinancierType3())) {
                                    e.setFinancierType304(e.getFinancierType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, e.getFinancierType3()));
                                }
                                e.setFinanceProj04(e.getFinanceProj());
                                if(StringUtil.isNotEmpty(e.getIndustryLevel2Type())) {
                                    e.setIndustryLevel2Type04(e.getIndustryLevel2Type() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryLevel2Type()));
                                }
                                if(StringUtil.isNotEmpty(e.getIndustryFlag())) {
                                    e.setIndustryFlag04(e.getIndustryFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getIndustryFlag()));
                                }
                                if(StringUtil.isNotEmpty(e.getMonitType())) {
                                    e.setMonitType04(e.getMonitType() + " " + SysDataUtil.getNameFromDataDict(DictCode.重点监控行业和领域类别, e.getMonitType()));
                                }
                                e.setMonTypeComm04(e.getMonTypeComm());
                                if(StringUtil.isNotEmpty(e.getGuaType())) {
                                    e.setGuaType04(e.getGuaType() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保方式, e.getGuaType()));
                                }
                                if(StringUtil.isNotEmpty(e.getPledgeType())) {
                                    e.setPledgeType04(e.getPledgeType() + " " + SysDataUtil.getNameFromDataDict(DictCode.抵质押物类型, e.getPledgeType()));
                                }
                                if(StringUtil.isNotEmpty(e.getPledgeValue())) {
                                    e.setPledgeValue04(e.getPledgeValue().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getNatureGuarantee())) {
                                    e.setNatureGuarantee04(e.getNatureGuarantee() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保性质, e.getNatureGuarantee()));
                                }
                                if(StringUtil.isNotEmpty(e.getRelationFinan())) {
                                    e.setRelationFinan04(e.getRelationFinan() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保人与融资人关系, e.getRelationFinan()));
                                }
                                e.setGuaTypeRemark04(e.getGuaTypeRemark());
                                if(StringUtil.isNotEmpty(e.getOutGrade())) {
                                    e.setOutGrade04(e.getOutGrade() + " " +SysDataUtil.getNameFromDataDict(DictCode.资产评级, e.getOutGrade()));
                                }
                                e.setFinOrgCode04(e.getFinOrgCode());
                                if(StringUtil.isNotEmpty(e.getProIndustryLevel2())) {
                                    e.setProIndustryLevel204(e.getProIndustryLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getProIndustryLevel2()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "05":
                            try {
                                if(StringUtil.isNotEmpty(e.getIsResale())) {
                                    e.setIsResale05(e.getIsResale() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getIsResale()));
                                }
                                if(StringUtil.isNotEmpty(e.getAmt())) {
                                    e.setAmt05(e.getAmt().toString());
                                }
                                e.setVdate05(DateUtil.format(e.getVdate()));
                                e.setMdate05(DateUtil.format(e.getMdate()));
                                if(StringUtil.isNotEmpty(e.getIndustryShares2())) {
                                    e.setIndustryShares205(e.getIndustryShares2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryShares2()));
                                }
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield05("0.00000");
                                    }else{
                                        e.setAssetYield05(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "06":
                            try {
                                e.setAssetName06(e.getAssetName());
                                if(StringUtil.isNotEmpty(e.getIndustryShares2())) {
                                    e.setIndustryShares206(e.getIndustryShares2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryShares2()));
                                }
                                if(StringUtil.isNotEmpty(e.getInvestStage())) {
                                    e.setInvestStage06(e.getInvestStage() + " " + SysDataUtil.getNameFromDataDict(DictCode.投资阶段, e.getInvestStage()));
                                }
                                if(StringUtil.isNotEmpty(e.getSharesType1())) {
                                    e.setSharesType106(e.getSharesType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getSharesType1()));
                                }
                                if(StringUtil.isNotEmpty(e.getSharesType3())) {
                                    e.setSharesType306(e.getSharesType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按技术领域划分, e.getSharesType3()));
                                }
                                if(StringUtil.isNotEmpty(e.getSharesType2())) {
                                    e.setSharesType206(e.getSharesType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, e.getSharesType2()));
                                }
                                if(StringUtil.isNotEmpty(e.getDeptEquity())) {
                                    e.setDeptEquity06(e.getDeptEquity() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getDeptEquity()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "07":
                            try {
                                e.setAssetName07(e.getAssetName());
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case "08":
                            try {
                                if(StringUtil.isNotEmpty(e.getCountryArea())) {
                                    e.setCountryArea08(e.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, e.getCountryArea()));
                                }
                                e.setAssetName08(e.getAssetName());
                                e.setAssetId08(e.getAssetId());
                                e.setIssuePartyName08(e.getIssuePartyName());
                                if(StringUtil.isNotEmpty(e.getIndustryLevel2())) {
                                    e.setIndustryLevel208(e.getIndustryLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryLevel2()));
                                }
                                e.setVdate08(DateUtil.format(e.getVdate()));
                                e.setMdate08(DateUtil.format(e.getMdate()));
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield08("0.00000");
                                    }else{
                                        e.setAssetYield08(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getIntpayCycle())) {
                                    e.setIntpayCycle08(e.getIntpayCycle().toString());
                                }
                                e.setGuaTypeRemark08(e.getGuaTypeRemark());
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "09":
                            try {
                                if(StringUtil.isNotEmpty(e.getCountryArea())) {
                                    e.setCountryArea09(e.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, e.getCountryArea()));
                                }
                                e.setVdate09(DateUtil.format(e.getVdate()));
                                e.setMdate09(DateUtil.format(e.getMdate()));
                                e.setRivalParty09(e.getRivalParty09());
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield09("0.00000");
                                    }else{
                                        e.setAssetYield09(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getBasis())) {
                                    e.setBasis09(e.getBasis() + " " + SysDataUtil.getNameFromDataDict(DictCode.计息基础_中债, e.getBasis()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "10":
                            try {
                                if(StringUtil.isNotEmpty(e.getCountryArea())) {
                                    e.setCountryArea10(e.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, e.getCountryArea()));
                                }
                                e.setAssetId10(e.getAssetId());
                                e.setAssetName10(e.getAssetName());
                                e.setIssuePartyName10(e.getIssuePartyName());
                                if(StringUtil.isNotEmpty(e.getIndustryShares2())) {
                                    e.setIndustryShares210(e.getIndustryShares2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryShares2()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case "11":
                            try {
                                if(StringUtil.isNotEmpty(e.getCountryArea())) {
                                    e.setCountryArea11(e.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, e.getCountryArea()));
                                }
                                e.setVdate11(DateUtil.format(e.getVdate()));
                                e.setMdate11(DateUtil.format(e.getMdate()));
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield11("0.00000");
                                    }else{
                                        e.setAssetYield11(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getIntpayCycle())) {
                                    e.setIntpayCycle11(e.getIntpayCycle().toString());
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "12":
                            try {
                                e.setAssetName12(e.getAssetName());
                                if(StringUtil.isNotEmpty(e.getFinancialComissue())) {
                                    e.setFinancialComissue12(e.getFinancialComissue() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getFinancialComissue()));
                                }
                                e.setManagerId12(e.getManagerId());
                                e.setTrusteeId12(e.getTrusteeId());
                                if(StringUtil.isNotEmpty(e.getAmt())) {
                                    e.setAmt12(e.getAmt().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getTruActType())) {
                                    e.setTruActType12(e.getTruActType() + " " + SysDataUtil.getNameFromDataDict(DictCode.资金实际投向, e.getTruActType()));
                                }
                                e.setInvestType12(e.getInvestType());
                                if(StringUtil.isNotEmpty(e.getCapUseInduLevel2())) {
                                    e.setCapUseInduLevel212(e.getCapUseInduLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getCapUseInduLevel2()));
                                }
                                e.setPlanSdate12(DateUtil.format(e.getPlanSdate()));
                                e.setPlanEdate12(DateUtil.format(e.getPlanEdate()));
                                if(StringUtil.isNotEmpty(e.getProProperty())) {
                                    e.setProProperty12(e.getProProperty() + " " + SysDataUtil.getNameFromDataDict(DictCode.资管计划属性, e.getProProperty()));
                                }
                                if(StringUtil.isNotEmpty(e.getRateFlag())) {
                                    e.setRateFlag12(e.getRateFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getRateFlag()));
                                }
                                if(StringUtil.isNotEmpty(e.getMaxRate())) {
                                    if(e.getMaxRate().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getMaxRate().toString(),"0E-14")) {
                                        e.setMaxRate12("0.00000");
                                    }else{
                                        e.setMaxRate12(e.getMaxRate().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getMinRate())) {
                                    if(e.getMinRate().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getMinRate().toString(),"0E-14")) {
                                        e.setMinRate12("0.00000");
                                    }else{
                                        e.setMinRate12(e.getMinRate().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getMgrFeeRate())) {
                                    if(e.getMgrFeeRate().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getMgrFeeRate().toString(),"0E-14")) {
                                        e.setMgrFeeRate12("0.00000");
                                    }else{
                                        e.setMgrFeeRate12(e.getMgrFeeRate().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getTruFeeRate())) {
                                    if(e.getTruFeeRate().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getTruFeeRate().toString(),"0E-14")) {
                                        e.setTruFeeRate12("0.00000");
                                    }else{
                                        e.setTruFeeRate12(e.getTruFeeRate().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getTradeFeeRate())) {
                                    if(e.getTradeFeeRate().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getTradeFeeRate().toString(),"0E-14")) {
                                        e.setTradeFeeRate12("0.00000");
                                    }else{
                                        e.setTradeFeeRate12(e.getTradeFeeRate().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getAgencyFeeRate())) {
                                    if(e.getAgencyFeeRate().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAgencyFeeRate().toString(),"0E-14")) {
                                        e.setAgencyFeeRate12("0.00000");
                                    }else{
                                        e.setAgencyFeeRate12(e.getAgencyFeeRate().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getOtherFeeRate())) {
                                    if(e.getOtherFeeRate().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getOtherFeeRate().toString(),"0E-14")) {
                                        e.setOtherFeeRate12("0.00000");
                                    }else{
                                        e.setOtherFeeRate12(e.getOtherFeeRate().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case "13":
                            try {
                                if(StringUtil.isNotEmpty(e.getCountryArea())) {
                                    e.setCountryArea13(e.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, e.getCountryArea()));
                                }
                                e.setAssetName13(e.getAssetName());
                                if(StringUtil.isNotEmpty(e.getTermDate())) {
                                    e.setTermDate13(e.getTermDate().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getAssetValue())) {
                                    if(e.getAssetValue().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetValue().toString(),"0E-14")) {
                                        e.setAssetValue13("0.00000");
                                    }else{
                                        e.setAssetValue13(e.getAssetValue().toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield13("0.00000");
                                    }else{
                                        e.setAssetYield13(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case "14":
                            try {
                                e.setAssetName14(e.getAssetName());
                                if(StringUtil.isNotEmpty(e.getAmt())) {
                                    e.setAmt14(e.getAmt().toString());
                                }
                                e.setVdate14(DateUtil.format(e.getVdate()));
                                e.setMdate14(DateUtil.format(e.getMdate()));
                                if(StringUtil.isNotEmpty(e.getCountryArea())) {
                                    e.setCountryArea14(e.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, e.getCountryArea()));
                                }
                                if(StringUtil.isNotEmpty(e.getRateFlag())) {
                                    e.setRateFlag14(e.getRateFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getRateFlag()));
                                }
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield14("0.00000");
                                    }else{
                                        e.setAssetYield14(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getIntpayCycle())) {
                                    e.setIntpayCycle14(e.getIntpayCycle().toString());
                                }
                                e.setFinancie14(e.getFinancie());
                                e.setFinOrgCode14(e.getFinOrgCode());
                                e.setFcrGradeOut14(e.getFcrGradeOut());
                                if(StringUtil.isNotEmpty(e.getFinancierType1())) {
                                    e.setFinancierType114(e.getFinancierType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getFinancierType1()));
                                }
                                if(StringUtil.isNotEmpty(e.getFinancierType2())) {
                                    e.setFinancierType214(e.getFinancierType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按技术领域划分, e.getFinancierType2()));
                                }
                                if(StringUtil.isNotEmpty(e.getFinancierType3())) {
                                    e.setFinancierType314(e.getFinancierType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, e.getFinancierType3()));
                                }
                                e.setFinanceProj14(e.getFinanceProj());
                                if(StringUtil.isNotEmpty(e.getIndustryLevel2Type())) {
                                    e.setIndustryLevel2Type14(e.getIndustryLevel2Type() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryLevel2Type()));
                                }
                                if(StringUtil.isNotEmpty(e.getProIndustryLevel2())) {
                                    e.setProIndustryLevel214(e.getProIndustryLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getProIndustryLevel2()));
                                }
                                if(StringUtil.isNotEmpty(e.getIndustryFlag())) {
                                    e.setIndustryFlag14(e.getIndustryFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getIndustryFlag()));
                                }
                                if(StringUtil.isNotEmpty(e.getMonitType())) {
                                    e.setMonitType14(e.getMonitType() + " " + SysDataUtil.getNameFromDataDict(DictCode.重点监控行业和领域类别, e.getMonitType()));
                                }
                                e.setMonTypeComm14(e.getMonTypeComm());
                                if(StringUtil.isNotEmpty(e.getOutGrade())) {
                                    e.setOutGrade14(e.getOutGrade() + " " + SysDataUtil.getNameFromDataDict(DictCode.资产评级, e.getOutGrade()));
                                }
                                if(StringUtil.isNotEmpty(e.getGuaType())) {
                                    e.setGuaType14(e.getGuaType() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保方式, e.getGuaType()));
                                }
                                if(StringUtil.isNotEmpty(e.getPledgeType())) {
                                    e.setPledgeType14(e.getPledgeType() + " " + SysDataUtil.getNameFromDataDict(DictCode.抵质押物类型, e.getPledgeType()));
                                }
                                if(StringUtil.isNotEmpty(e.getPledgeValue())) {
                                    e.setPledgeValue14((e.getPledgeValue().toString()));
                                }
                                if(StringUtil.isNotEmpty(e.getNatureGuarantee())) {
                                    e.setNatureGuarantee14(e.getNatureGuarantee() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保性质, e.getNatureGuarantee()));
                                }
                                if(StringUtil.isNotEmpty(e.getRelationFinan())) {
                                    e.setRelationFinan14(e.getRelationFinan() + " " + SysDataUtil.getNameFromDataDict(DictCode.担保人与融资人关系, e.getRelationFinan()));
                                }
                                e.setGuaTypeRemark14(e.getGuaTypeRemark());
                                if(StringUtil.isNotEmpty(e.getDeptEquity())) {
                                    e.setDeptEquity14(e.getDeptEquity() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getDeptEquity()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "15":
                           try {
                            e.setAssetIdL01(e.getAssetId());
                            e.setAssetNameL01(e.getAssetName());
                            if(StringUtil.isNotEmpty(e.getIndustryShares2())) {
                                e.setIndustryShares2L01(e.getIndustryShares2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryShares2()));
                            }
                            if(StringUtil.isNotEmpty(e.getFinancialComissue())) {
                                e.setFinancialComissueL01(e.getFinancialComissue() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getFinancialComissue()));
                            }
                            if(StringUtil.isNotEmpty(e.getInvestStage())) {
                                e.setInvestStageL01(e.getInvestStage() + " " + SysDataUtil.getNameFromDataDict(DictCode.投资阶段, e.getInvestStage()));
                            }
                            if(StringUtil.isNotEmpty(e.getSharesType1())) {
                                e.setSharesType1L01(e.getSharesType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getSharesType1()));
                            }
                            if(StringUtil.isNotEmpty(e.getSharesType3())) {
                                e.setSharesType3L01(e.getSharesType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getSharesType3()));
                            }
                            if(StringUtil.isNotEmpty(e.getSharesType2())) {
                                e.setSharesType2L01(e.getSharesType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, e.getSharesType2()));
                            }
                         } catch (Exception exception) {
                            exception.printStackTrace();
                         }
                            break;

                        case "16":
                            try {
                                e.setAssetName16(e.getAssetName());
                                e.setAssetId16(e.getAssetId());
                                e.setManagerId16(e.getManagerId());
                                e.setTrusteeId16(e.getTrusteeId());
                                if(StringUtil.isNotEmpty(e.getAmt())) {
                                    e.setAmt16(e.getAmt().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getTruActType())) {
                                    e.setTruActType16(e.getTruActType() + " " + SysDataUtil.getNameFromDataDict(DictCode.资金实际投向, e.getTruActType()));
                                }
                                e.setInvestType16(e.getInvestType());
                                if(StringUtil.isNotEmpty(e.getCapUseInduLevel2())) {
                                    e.setCapUseInduLevel216(e.getCapUseInduLevel2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getCapUseInduLevel2()));
                                }
                                e.setPlanSdate16(DateUtil.format(e.getPlanSdate()));
                                e.setPlanEdate16(DateUtil.format(e.getPlanEdate()));
                                if(StringUtil.isNotEmpty(e.getProProperty())) {
                                    e.setProProperty16(e.getProProperty() + " " + SysDataUtil.getNameFromDataDict(DictCode.资管计划属性, e.getProProperty()));
                                }
                                if(StringUtil.isNotEmpty(e.getRateFlag())) {
                                    e.setRateFlag16(e.getRateFlag() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getRateFlag()));
                                }
                                if(StringUtil.isNotEmpty(e.getMaxRate())) {
                                    e.setMaxRate16(e.getMaxRate().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getMinRate())) {
                                    e.setMinRate16(e.getMinRate().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getMgrFeeRate())) {
                                    e.setMgrFeeRate16(e.getMgrFeeRate().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getTruFeeRate())) {
                                    e.setTruFeeRate16(e.getTruFeeRate().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getTradeFeeRate())) {
                                    e.setTradeFeeRate16(e.getTradeFeeRate().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getAgencyFeeRate())) {
                                    e.setAgencyFeeRate16(e.getAgencyFeeRate().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getOtherFeeRate())) {
                                    e.setOtherFeeRate16(e.getOtherFeeRate().toString());
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case "17":
                            try {
                                if(StringUtil.isNotEmpty(e.getCountryArea())) {
                                    e.setCountryArea17(e.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, e.getCountryArea()));
                                }
                                e.setAssetName17(e.getAssetName());
                                if(StringUtil.isNotEmpty(e.getTermDate())) {
                                    e.setTermDate17(e.getTermDate().toString());
                                }
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield17("0.00000");
                                    }else{
                                        e.setAssetYield17(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "18":
                            try {
                                if(StringUtil.isNotEmpty(e.getCountryArea())) {
                                    e.setCountryArea18(e.getCountryArea() + " " + SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, e.getCountryArea()));
                                }
                                e.setAssetName18(e.getAssetName());
                                e.setVdate18(DateUtil.format(e.getVdate()));
                                e.setMdate18(DateUtil.format(e.getMdate()));
                                if(StringUtil.isNotEmpty(e.getAssetValue())) {
                                    if(e.getAssetValue().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetValue().toString(),"0E-14")) {
                                        e.setAssetValue18("0.00000");
                                    }else{
                                        e.setAssetValue18(e.getAssetValue().toString());
                                    }
                                }
                                if(StringUtil.isNotEmpty(e.getAssetYield())) {
                                    if(e.getAssetYield().compareTo(new BigDecimal(BigInteger.ZERO))==0 || StringUtil.equals(e.getAssetYield().toString(),"0E-14")) {
                                        e.setAssetYield18("0.00000");
                                    }else{
                                        e.setAssetYield18(e.getAssetYield().setScale(5,BigDecimal.ROUND_HALF_UP).toString());
                                    }
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;

                        case "19":
                            try {
                            e.setAssetIdL01(e.getAssetId());
                            e.setAssetNameL01(e.getAssetName());
                            if(StringUtil.isNotEmpty(e.getIndustryShares2())) {
                                e.setIndustryShares2L01(e.getIndustryShares2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryShares2()));
                            }
                            if(StringUtil.isNotEmpty(e.getFinancialComissue())) {
                                e.setFinancialComissueL01(e.getFinancialComissue() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getFinancialComissue()));
                            }
                            if(StringUtil.isNotEmpty(e.getInvestStage())) {
                                e.setInvestStageL01(e.getInvestStage() + " " + SysDataUtil.getNameFromDataDict(DictCode.投资阶段, e.getInvestStage()));
                            }
                            if(StringUtil.isNotEmpty(e.getSharesType1())) {
                                e.setSharesType1L01(e.getSharesType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getSharesType1()));
                            }
                            if(StringUtil.isNotEmpty(e.getSharesType3())) {
                                e.setSharesType3L01(e.getSharesType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getSharesType3()));
                            }
                            if(StringUtil.isNotEmpty(e.getSharesType2())) {
                                e.setSharesType2L01(e.getSharesType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, e.getSharesType2()));
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                            break;

                        case "L01":
                            try {
                                e.setAssetIdL01(e.getAssetId());
                                e.setAssetNameL01(e.getAssetName());
                                if(StringUtil.isNotEmpty(e.getIndustryShares2())) {
                                    e.setIndustryShares2L01(e.getIndustryShares2() + " " + SysDataUtil.getNameFromDataDict(DictCode.机构所属行业二级分类, e.getIndustryShares2()));
                                }
                                if(StringUtil.isNotEmpty(e.getFinancialComissue())) {
                                    e.setFinancialComissueL01(e.getFinancialComissue() + " " + SysDataUtil.getNameFromDataDict(DictCode.是否_中债, e.getFinancialComissue()));
                                }
                                if(StringUtil.isNotEmpty(e.getInvestStage())) {
                                    e.setInvestStageL01(e.getInvestStage() + " " + SysDataUtil.getNameFromDataDict(DictCode.投资阶段, e.getInvestStage()));
                                }
                                if(StringUtil.isNotEmpty(e.getSharesType1())) {
                                    e.setSharesType1L01(e.getSharesType1() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getSharesType1()));
                                }
                                if(StringUtil.isNotEmpty(e.getSharesType3())) {
                                    e.setSharesType3L01(e.getSharesType3() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按规模划分, e.getSharesType3()));
                                }
                                if(StringUtil.isNotEmpty(e.getSharesType2())) {
                                    e.setSharesType2L01(e.getSharesType2() + " " + SysDataUtil.getNameFromDataDict(DictCode.发行机构类型_按经济类型划分, e.getSharesType2()));
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        default:
                    }
                }
            });
        }
    }

    //处理查询出的数据
    public void dealFinancie(List<AssetInfoDetailDto> dealList){
        //获取托管人，管理人，融资人信息
        List<MstCustomerInfo> customerList = svrMstCustomerInfoAddImpMapper.getAllCustomerInfo();
        Map<String,String> customerMap = customerList.stream().collect(HashMap<String,String>::new,
                (map, item)->map.put(item.getCustomerId(), item.getCustomerName()), (m, n)->m.putAll(n));

        if(dealList !=null && dealList.size()>0){
            dealList.forEach(e->{
                //托管人
                if(StringUtil.isNotEmpty(e.getTrusteeId())){
                    if(StringUtil.isNotEmpty(customerMap.get(e.getTrusteeId()))) {
                        e.setTrusteeId(e.getTrusteeId() + " " + customerMap.get(e.getTrusteeId()));
                    }
                }
                //管理人
                if(StringUtil.isNotEmpty(e.getManagerId())){
                    if(StringUtil.isNotEmpty(customerMap.get(e.getManagerId()))) {
                        e.setManagerId(e.getManagerId() + " " + customerMap.get(e.getManagerId()));
                    }
                }
                //融资人
                if(StringUtil.isNotEmpty(e.getFinancie())){
                    if(StringUtil.isNotEmpty(customerMap.get(e.getFinancie()))) {
                        e.setFinancie(e.getFinancie() + " " + customerMap.get(e.getFinancie()));
                    }
                }
                //发行人
                if(StringUtil.isNotEmpty(e.getChbIssuerId())){
                    if(StringUtil.isNotEmpty(customerMap.get(e.getChbIssuerId()))) {
                        e.setChbIssuerId(e.getChbIssuerId() + " " + customerMap.get(e.getChbIssuerId()));
                    }
                }
            });
        }
    }
}
