package com.joyintech.tams.common.svreport.datacheck.beforeinsert.service;

import com.joyintech.framework.base.intf.DataDict;
import com.joyintech.framework.base.intf.DataDictItem;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.*;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dao.CommonCodeCheckMapper;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbInterfErrodataMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbInterfErrodataWithBLOBs;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckElement;
import com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * 中债一二期入业务表前的代码check
 * @author Administrator
 *
 */
@Service
public class CommonCodeCheckBatchService {
	
	@Autowired
	private CommonCodeCheckMapper commonCodeCheckMapper;

    /**
     * mybatis的sqlsession管理核心
     */
    @Autowired
    private SqlSessionTemplate template;

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 登记的入库前的代码检查
     * 只做校验，不做其他处理
     * @param  list 被检查的数据
     * @param  tableInfo  检查的表名
     */
    public void codeCheckCommonOnly(List<Map<String, String>> list, ChbCheckTableInfo tableInfo) {
        Map<String,String>  mapLinshi=new HashMap<>();


        Set<String> prodRegistCodeList = new HashSet<>();
        Set<String> prodIdCodeList = new HashSet<>();

        Set<String> saleInstSet = new HashSet<>();

        Map<String, List<String>> prodRegistCodeMap = new HashMap<>();
        
        Map<String,String> detailMap = new HashMap();
        //循环处理待check数据
        for (int i = list.size() - 1; i >= 0; i--){
            Map<String, String> map=list.get(i);
            switch (tableInfo) {
                case 从业人员登记表SUB:
                case 从业人员登记表INFO:
                    checkEmployeeSubInfo(map);
                    break;
                case 资产要素登记表SUB:
                case 资产要素登记表INFO:
                case 资产要素登记TMP:
                    checkAssetSubInfo(map);
                    break;
                case 个人发行登记表SUB:
                case 个人发行登记表INFO:
                case 个人发行登记TMP:
                    checkPrdoIssue(map, prodRegistCodeList,prodIdCodeList);
                    break;
                case 募集总量登记SUB:
                case 募集总量登记TMP:
                    checkProdRaisetot(map, prodRegistCodeList);
                    break;
                case 底层资产持仓信息登记表SUB:
                case 底层资产持仓信息登记INFO:
                case 底层资产持仓信息登记TMP:
                    checkBotAstPos(map, detailMap);
                    break;
                case 投资交易表登记SUB:
                case 投资交易表登记TMP:
                    checkInvestTradeInfo(map, prodRegistCodeMap);
                    break;
                case 资产估值信息登记表SUB:
                case 资产估值信息登记TMP:
                    checkAstValInfo(map, list);
                    break;
                case 产品存续登记表SUB:
                case 产品存续登记TMP:
                    checkProdDeal(map, list);
                    break;
                case 个人产品申报登记SUB:
                case 个人产品申报登记INFO:
                case 个人产品申报登记TMP:
                    checkPrdoDeclare(map,detailMap, tableInfo);
                    break;
                case 终止登记SUB:
                case 终止登记TMP:
                    checkProdEndSubTmp(map,prodRegistCodeList);
                    break;
                case 销售结算资金交易信息表INFO:
                    checkChbSalerStlDea(map, ChbCheckTableInfo.销售结算资金交易信息表INFO.getTableName());
                    break;
                case 总部或分支机构信息登记表SUB:
                    checkSaleInstInfo(map,saleInstSet,ChbCheckTableInfo.总部或分支机构信息登记表SUB.getTableName());
                case 销售人员身份信息登记表SUB:
                    checkSalerIdenInfo(map,ChbCheckTableInfo.销售人员身份信息登记表SUB.getTableName(), mapLinshi);
                case 销售人员奖惩投诉记录登记表SUB:
                    checkRewPenaInfo(map,ChbCheckTableInfo.销售人员奖惩投诉记录登记表SUB.getTableName());
                    break;
                case 销售人员奖惩投诉记录信息表INFO:
                    checkRewPenaInfo(map,ChbCheckTableInfo.销售人员奖惩投诉记录信息表INFO.getTableName());
                case 销售人员身份信息表INFO:
                    checkSalerIdenInfo(map,ChbCheckTableInfo.销售人员身份信息表INFO.getTableName(), mapLinshi);
                case  总部或分支机构信息表INFO:
                    checkSaleInstInfo(map,saleInstSet,ChbCheckTableInfo.总部或分支机构信息表INFO.getTableName());
                default:
                    break;
            }
        }
    }
    /**
     * 登记的入库前的代码检查
     * 只做校验，不做其他处理
     * @param  list 被检查的数据
     * @param  tableInfo  检查的表名
     */
    public void codeCheckCommonOnly(List<ExcelValidDto> excelValidDtoList,List<Map<String, String>> list, ChbCheckTableInfo tableInfo) {
        Map<String,String>  mapLinshi=new HashMap<>();

        Set<String> prodRegistCodeList = new HashSet<>();
        Set<String> prodIdCodeList = new HashSet<>();
        Set<String > tradeCodeSet = new HashSet<>();
        Set<String> saleInstSet = new HashSet<>();

        Map<String, List<String>> prodRegistCodeMap = new HashMap<>();

        Map<String,String> detailMap = new HashMap();
        //循环处理待check数据
        for (int i = list.size() - 1; i >= 0; i--){
            Map<String, String> map=list.get(i);
            switch (tableInfo) {
                case 从业人员登记表SUB:
                case 从业人员登记表INFO:
                    checkEmployeeSubInfo(map);
                    break;
                case 资产要素登记表SUB:
                case 资产要素登记TMP:
                    checkAssetSubInfo(map);
                    break;
                case 资产要素登记表INFO:
                    checkAssetSubInfo(excelValidDtoList,map,i);
                    break;
                case 个人发行登记表SUB:
                case 个人发行登记TMP:
                    checkPrdoIssue(map, prodRegistCodeList,prodIdCodeList);
                    break;
                case 个人发行登记表INFO:
                    checkChbBaseInfo(excelValidDtoList,map, prodRegistCodeList,prodIdCodeList,i);
                    break;
                case 募集总量登记SUB:
                case 募集总量登记TMP:
                    checkProdRaisetot(map, prodRegistCodeList);
                    break;
                case 底层资产持仓信息登记表SUB:
                case 底层资产持仓信息登记INFO:
                case 底层资产持仓信息登记TMP:
                    checkBotAstPos(map, detailMap);
                    break;
                case 投资交易表登记SUB:
                case 投资交易表登记TMP:
                    checkInvestTradeInfo(map, prodRegistCodeMap);
                    break;
                case 投资交易信息表:
                    checkChbInvestTrade(excelValidDtoList,map,i);
                    break;
                case 资产估值信息登记表SUB:
                case 资产估值信息登记TMP:
                    checkAstValInfo(map, list);
                    break;
                case 产品存续登记表SUB:
                case 产品存续登记TMP:
                    checkProdDeal(map, list);
                    break;
                case 个人产品申报登记SUB:
                case 个人产品申报登记TMP:
                    checkPrdoDeclare(map,detailMap, tableInfo);
                    break;
                case 个人产品申报登记INFO:
                    checkPrdoDeclare(excelValidDtoList,map,detailMap, tableInfo,i);
                    break;
                case 科目定义:
                    checkBokSubject(excelValidDtoList,map,i);
                    break;
                case 终止登记SUB:
                case 终止登记TMP:
                    checkProdEndSubTmp(map,prodRegistCodeList);
                    break;
                case 销售结算资金交易信息表INFO:
                    checkChbSalerStlDea(map, ChbCheckTableInfo.销售结算资金交易信息表INFO.getTableName());
                    break;
                case 总部或分支机构信息登记表SUB:
                    checkSaleInstInfo(map,saleInstSet,ChbCheckTableInfo.总部或分支机构信息登记表SUB.getTableName());
                case 销售人员身份信息登记表SUB:
                    checkSalerIdenInfo(map,ChbCheckTableInfo.销售人员身份信息登记表SUB.getTableName(),mapLinshi);
                case 销售人员奖惩投诉记录登记表SUB:
                    checkRewPenaInfo(map,ChbCheckTableInfo.销售人员奖惩投诉记录登记表SUB.getTableName());
                    break;
                case 销售人员奖惩投诉记录信息表INFO:
                    checkRewPenaInfo(map,ChbCheckTableInfo.销售人员奖惩投诉记录信息表INFO.getTableName());
                case 销售人员身份信息表INFO:
                    checkSalerIdenInfo(map,ChbCheckTableInfo.销售人员身份信息表INFO.getTableName(),mapLinshi);
                case  总部或分支机构信息表INFO:
                    checkSaleInstInfo(map,saleInstSet,ChbCheckTableInfo.总部或分支机构信息表INFO.getTableName());
                default:
                    break;
            }
        }
    }

    private void checkBokSubject(List<ExcelValidDto> excelValidDtoList, Map<String, String> dataMap, int i) {
        String  errMsg=dataMap.getOrDefault("ERRMSG", "");
        StringBuilder sb = new StringBuilder();
        String fsubjectId = dataMap.get("FSUBJECT_ID");
        if(!errMsg.equals("重复数据;")){
            if(commonCodeCheckMapper.checkExistsFsubjectId(fsubjectId)<1){
                sb.append("上级科目号不存在；");
            }

            if(StringUtils.isEmpty(errMsg)){
                if(StringUtils.isNotEmpty(sb.toString())&&excelValidDtoList!=null){
                    excelValidDtoList.add(new ExcelValidDto("导入成功",String.valueOf((i+2)),sb.toString()));
                }
            }else{
                if(StringUtils.isNotEmpty(sb.toString())&& excelValidDtoList!=null){
                    if(excelValidDtoList.size()>0){
                        for (ExcelValidDto excelValidDto : excelValidDtoList) {
                            if(excelValidDto.getRowNum().equals(String.valueOf((i+2)))){
                                excelValidDto.setErrorInfo(errMsg+sb.toString());
                                break;
                            }
                        }
                    }
                }
            }
            dataMap.put("ERRMSG",errMsg+sb.toString());
        }
        if (CollectionUtils.isNotEmpty(excelValidDtoList)) {
            this.excelValidListSort(excelValidDtoList);
        }
    }

    private void checkChbInvestTrade(List<ExcelValidDto> excelValidDtoList, Map<String, String> dataMap, int i) {
        String  errMsg=dataMap.getOrDefault("ERRMSG", "");
        StringBuilder sb = new StringBuilder();
        String assetCode = dataMap.get("ASSET_CODE");
        String curAssetType = dataMap.get("CUR_ASSET_TYPE");
        if(!errMsg.equals("重复数据;")){
            if(commonCodeCheckMapper.checkExistsAssetCode(assetCode)<1){
                sb.append("行内资产/负债编码不存在；");
            }
            if(StringUtil.equals(curAssetType,"11")||StringUtil.equals(curAssetType,"12")){
                Map<String, Object> param = new HashMap<>();
                param.put("prodId",dataMap.get("PROD_ID"));
                param.put("tradeCode",dataMap.get("TRADE_CODE"));
                param.put("curAssetType",curAssetType);
                if(commonCodeCheckMapper.checkInvestTradeCodeOther(param) > 0){
                    sb.append("当“资金流动类型”选择“11其他收入”“12其他支出”时，“产品登记编码+行内交易编码+资金流动类型”不得同时与已有数据重复；");
                }
            }else{
                Map<String, Object> param = new HashMap<>();
                param.put("prodId",dataMap.get("PROD_ID"));
                param.put("tradeCode",dataMap.get("TRADE_CODE"));
                param.put("assetCode",dataMap.get("ASSET_CODE"));
                param.put("curAssetType",curAssetType);
                if(commonCodeCheckMapper.checkInvestTradeCode(param) > 0){
                    sb.append("当“资金流动类型”选择“11其他收入”“12其他支出”之外的类型时，“产品登记编码+行内交易编码+行内资产/负债编码+资金流动类型”不得同时与已有数据重复；");
                }
            }
            try {
                if(dataMap.get("CCY").equals("CNY")){
                    if(!dataMap.get("AMT").equals(dataMap.get("CNY_AMT"))){
                        sb.append("当币种选择人民币时，发生金额必须等于折算人民币金额；");
                    }
                }
            } catch (Exception e) {
            }
            if(StringUtils.isEmpty(errMsg)){
                if(StringUtils.isNotEmpty(sb.toString())&&excelValidDtoList!=null){
                    excelValidDtoList.add(new ExcelValidDto("导入成功",String.valueOf((i+2)),sb.toString()));
                }
            }else{
                if(StringUtils.isNotEmpty(sb.toString())&& excelValidDtoList!=null){
                    if(excelValidDtoList.size()>0){
                        for (ExcelValidDto excelValidDto : excelValidDtoList) {
                            if(excelValidDto.getRowNum().equals(String.valueOf((i+2)))){
                                excelValidDto.setErrorInfo(errMsg+sb.toString());
                                break;
                            }
                        }
                    }
                }
            }
            dataMap.put("ERRMSG",errMsg+sb.toString());
        }
        if (CollectionUtils.isNotEmpty(excelValidDtoList)) {
            this.excelValidListSort(excelValidDtoList);
        }
    }


    private void checkChbBaseInfo(List<ExcelValidDto> excelValidDtoList, Map<String, String> dataMap,Set<String> prodRegistCodeList,Set<String>  prodIdCodeList,int i) {
        String  errMsg=dataMap.getOrDefault("ERRMSG", "");
        String temp = errMsg;
        // 发行机构代码
        String issuePartyId=dataMap.get("ISSUE_PARTY_ID");
        // 产品登记编码
        String prodRegistCode = dataMap.get("REG_CODE");
        //产品代码
        String prodId = dataMap.get("PROD_ID");
        if(errMsg.equals("重复数据;")){

        }else{
            // 产品登记编码报文内不可重复。
            if (StringUtil.isNotEmpty(prodRegistCode) && prodRegistCodeList.contains(prodRegistCode)){
                errMsg +="产品登记编码报文内不可重复[PROD_REGIST_CODE="+prodRegistCode+"];";
            }
            else{
                prodRegistCodeList.add(prodRegistCode);
            }

            // 银行模式才判断发行机构代码，联社模式不判断
            if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
                // 发行机构代码必须与银行代码相同。
                if(!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)){
                    errMsg +="发行机构代码必须与银行代码相同[ISSUE_PARTY_ID="+issuePartyId+"];";
                }
            }
            if(StringUtils.isEmpty(temp)){
                if(!temp.equals(errMsg)&&excelValidDtoList!=null){
                    excelValidDtoList.add(new ExcelValidDto("导入成功",String.valueOf((i+2)),errMsg));
                }
            }else{
                if(!temp.equals(errMsg) && excelValidDtoList!=null){
                    if(excelValidDtoList.size()>0){
                        for (ExcelValidDto excelValidDto : excelValidDtoList) {
                            if(excelValidDto.getRowNum().equals(String.valueOf((i+2)))){
                                excelValidDto.setErrorInfo(errMsg);
                                break;
                            }
                        }
                    }
                }
            }
            dataMap.put("ERRMSG", errMsg);
        }
        if (CollectionUtils.isNotEmpty(excelValidDtoList)) {
            this.excelValidListSort(excelValidDtoList);
        }
    }


    /**
	 * 产品募集总量登记的入库前的代码检查
	 * @param  list 被检查的数据
	 * @param  tableInfo  检查的表名
     * @param  fileName 导入的文件名
	 */
	public void codeCheckCommon(List<Map<String, String>> list, ChbCheckTableInfo tableInfo, String fileName) {
		//错误信息集合
		List<ChbInterfErrodataWithBLOBs> erroList=new ArrayList<ChbInterfErrodataWithBLOBs>();
        // 资产要素错误信息集合
        List<ChbAssetInfoErrodataWithBLOBs> chbAssetInfoErrorList = new ArrayList<ChbAssetInfoErrodataWithBLOBs>();

        // 资产要素错误信息集合
        List<ChbSalerStlDeaErrodataWithBLOBs> chbSalerStlDeaErrodataList = new ArrayList<ChbSalerStlDeaErrodataWithBLOBs>();

        //销售人员信息错误集合
        List<ChbSalerIdenErrodataWithBLOBs>  chbSalerIdenErrodataList= new ArrayList<ChbSalerIdenErrodataWithBLOBs>();

        //总部或分支机构信息
        List<ChbSalerInstErrodataWithBLOBs>  chbSalerInstErrodataList=new ArrayList<ChbSalerInstErrodataWithBLOBs>();

        //销售人员惩奖记录信息
        List<ChbSalerRewPenaErrodataWithBLOBs>  chbSalerRewPenaErrodataList=new ArrayList<ChbSalerRewPenaErrodataWithBLOBs>();

        // 投资交易信息 产品基本信息  add  by  wubingbing 20220518  start
        List<ChbInvestTradeErrodataWithBLOBs> chbInvestTradeErrordataList = Lists.newArrayList();
        List<ChbPrdBaseinfoErrodataWithBLOBs> chbPrdBaseinfoErrordataList = Lists.newArrayList();
        // 投资交易信息 产品基本信息  add  by  wubingbing 20220518  end
        codeCheckCommonOnly(list, tableInfo);

        for (int i = list.size() - 1; i >= 0; i--){
            Map<String, String> map=list.get(i);

            // add by zhangliang 20210112 没有错误信息时删除key，防止后面错误判断
            if (map.containsKey("ERRMSG") && StringUtil.isEmpty(map.get("ERRMSG"))){
                map.remove("ERRMSG");
            }

            //如果代码错误校验有错误信息
            ChbInterfErrodataWithBLOBs errodata = createChbInterfErrdata(StringUtil.isNotEmpty(fileName) ? fileName : tableInfo.getTableName(),
                    tableInfo.getTableName(), map,tableInfo.getBussinessKey(), tableInfo.getCheckEuplicatedKeys());

            if(errodata != null) {
                erroList.add(errodata);
                list.remove(i);
            }

            if (StringUtil.equals(CacheUtil.getSysParam("INSERT_ERRDATA_MODE"), "02") && StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.资产要素登记表INFO.getTableName())){
                // 记录资产要素错误信息表
                ChbAssetInfoErrodataWithBLOBs chbAssetInfoErrodata = createChbAssetInfoInterfErrdata(map,"");
                if(chbAssetInfoErrodata != null) {
                    chbAssetInfoErrorList.add(chbAssetInfoErrodata);
                }
            }

            //销售数据反馈
            if (StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.销售结算资金交易信息表INFO.getTableName())){
                // 记录销售数据信息表
                ChbSalerStlDeaErrodataWithBLOBs ChbSalerStlDeaErrodata = createChbSalerStlDeaErrodata(map);
                if(ChbSalerStlDeaErrodata != null) {
                    chbSalerStlDeaErrodataList.add(ChbSalerStlDeaErrodata);
                }
            }
            //销售人员数据
            if (StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.销售人员身份信息表INFO.getTableName())) {
               //销售人员信息
                ChbSalerIdenErrodataWithBLOBs   chbSalerIdenErrodata=createChbSalerInderErrodata(map);
                if(chbSalerIdenErrodata !=null){
                    chbSalerIdenErrodataList.add(chbSalerIdenErrodata);
                }
            }
            //总部或分支机构信息
            if (StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.总部或分支机构信息表INFO.getTableName())) {
                ChbSalerInstErrodataWithBLOBs   chbSalerIdenErrodata=createChbSalerInstErrodata(map);
                if(chbSalerIdenErrodata !=null){
                    chbSalerInstErrodataList.add(chbSalerIdenErrodata);
                }
            }
            //销售人员惩奖记录信息
            if (StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.销售人员奖惩投诉记录信息表INFO.getTableName())) {
                ChbSalerRewPenaErrodataWithBLOBs   chbSalerRewPenaErrodata=createChbSalerRewErrodata(map);
                if(chbSalerRewPenaErrodata !=null){
                    chbSalerRewPenaErrodataList.add(chbSalerRewPenaErrodata);
                }
            }
        }

		//错误信息入库
        // 资产要素错误信息入库
        if (! StringUtil.isEmptyList(chbAssetInfoErrorList)){
            deleteAssetInfoErrorDataByAssetCode(chbAssetInfoErrorList);
            insertChbAssetInfoErroData(chbAssetInfoErrorList);
        }
        // 销售错误信息表入库
        else if(!chbSalerStlDeaErrodataList.isEmpty()){
            deleteChbSalerStlDeaErrodataByTradeNo(chbSalerStlDeaErrodataList);
            insertChbSalerStlDeaErrodata(chbSalerStlDeaErrodataList);
        }//销售人员信息错误表入库
        else if(!chbSalerIdenErrodataList.isEmpty()){
            deleteChbSalerIdenErrodataByNo(chbSalerIdenErrodataList);
            insertChbSalerIdenErrodata(chbSalerIdenErrodataList);
        }
        //总部或分支机构信息错误表入库
        else if(!chbSalerInstErrodataList.isEmpty()) {
            deleteChbSalerInstErrodataByNo(chbSalerInstErrodataList);
            insertChbSalerInstErrodata(chbSalerInstErrodataList);
        }
        //销售人员惩奖记录信息入库
        else if(!chbSalerRewPenaErrodataList.isEmpty()) {
            deleteChbSalerRewErrodataByNo(chbSalerRewPenaErrodataList);
            insertChbSalerRewErrodata(chbSalerRewPenaErrodataList);
        }
        //投资交易错误信息入库
        else if(!chbInvestTradeErrordataList.isEmpty()) {
            deleteChbInvestTradeErrorData(chbInvestTradeErrordataList);
            insertChbInvestTradeErroData(chbInvestTradeErrordataList);
        }
        //产品基本信息入库
        else if(!chbPrdBaseinfoErrordataList.isEmpty()) {
            deleteChbPrdBaseInfoErrorData(chbPrdBaseinfoErrordataList);
            insertChbPrdBaseInfoErroData(chbPrdBaseinfoErrordataList);
        }
        // 其它错误信息
        else if(!erroList.isEmpty()){
            insertErroData(erroList);
        }
	}



    /**
     * add by wubingbing 20220521
     * @param list
     * @param tableInfo
     * @param fileName
     */
    public void codeCheckCommon(List<ExcelValidDto> excelValidDtoList,List<Map<String, String>> list, ChbCheckTableInfo tableInfo, String fileName, String coverFlag,String flag) {

        //错误信息集合
        List<ChbInterfErrodataWithBLOBs> erroList=new ArrayList<ChbInterfErrodataWithBLOBs>();
        // 资产要素错误信息集合
        List<ChbAssetInfoErrodataWithBLOBs> chbAssetInfoErrorList = new ArrayList<ChbAssetInfoErrodataWithBLOBs>();

        // 资产要素错误信息集合
        List<ChbSalerStlDeaErrodataWithBLOBs> chbSalerStlDeaErrodataList = new ArrayList<ChbSalerStlDeaErrodataWithBLOBs>();

        //销售人员信息错误集合
        List<ChbSalerIdenErrodataWithBLOBs>  chbSalerIdenErrodataList= new ArrayList<ChbSalerIdenErrodataWithBLOBs>();

        //总部或分支机构信息
        List<ChbSalerInstErrodataWithBLOBs>  chbSalerInstErrodataList=new ArrayList<ChbSalerInstErrodataWithBLOBs>();

        //销售人员惩奖记录信息
        List<ChbSalerRewPenaErrodataWithBLOBs>  chbSalerRewPenaErrodataList=new ArrayList<ChbSalerRewPenaErrodataWithBLOBs>();

        // 投资交易信息 产品基本信息  add  by  wubingbing 20220518  start
        List<ChbInvestTradeErrodataWithBLOBs> chbInvestTradeErrordataList = Lists.newArrayList();
        List<ChbPrdBaseinfoErrodataWithBLOBs> chbPrdBaseinfoErrordataList = Lists.newArrayList();
        // 投资交易信息 产品基本信息  add  by  wubingbing 20220518  end
        List<ChbPrdDeclareInfoErrodataWithBLOBs> chbPrdDeclareInfoErrodataList = Lists.newArrayList();

        List<MstHolidayErrodataWithBLOBs> mstHolidayErrodataList = Lists.newArrayList();
        List<MstExchargeQuoteErrodataWithBLOBs> mstExchargeQuoteErrodataList = Lists.newArrayList();
        List<BokSubjectErrodataWithBLOBs> bokSubjectErrodataList = Lists.newArrayList();
        codeCheckCommonOnly(excelValidDtoList,list, tableInfo);
        updateExcelValidDtoList(excelValidDtoList,list,tableInfo);

        for (int i = list.size() - 1; i >= 0; i--){
            Map<String, String> map=list.get(i);

            // add by zhangliang 20210112 没有错误信息时删除key，防止后面错误判断
            if (map.containsKey("ERRMSG") && StringUtil.isEmpty(map.get("ERRMSG"))){
                map.remove("ERRMSG");
            }

            //如果代码错误校验有错误信息
            ChbInterfErrodataWithBLOBs errodata = createChbInterfErrdata(StringUtil.isNotEmpty(fileName) ? fileName : tableInfo.getTableName(),
                    tableInfo.getTableName(), map,tableInfo.getBussinessKey(), tableInfo.getCheckEuplicatedKeys());

            if(errodata != null) {
                erroList.add(errodata);
                list.remove(i);
            }

            if (StringUtil.equals(CacheUtil.getSysParam("INSERT_ERRDATA_MODE"), "02") && StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.资产要素登记表INFO.getTableName())){
                if("重复数据;".equals(map.get("ERRMSG"))){
                    continue;
                }
                // 记录资产要素错误信息表
                ChbAssetInfoErrodataWithBLOBs chbAssetInfoErrodata = createChbAssetInfoInterfErrdata(map,flag);
                if(chbAssetInfoErrodata != null) {
                    chbAssetInfoErrorList.add(chbAssetInfoErrodata);
                }
            }
            // 投资交易
            if(StringUtil.equals(tableInfo.getTableName(),ChbCheckTableInfo.投资交易信息表.getTableName())){
                if("重复数据;".equals(map.get("ERRMSG"))){
                    continue;
                }
                ChbInvestTradeErrodataWithBLOBs ChbInvestTradeErrodataWithBLOBs = createChbInvestTradeErrordata(map,flag);
                if(ChbInvestTradeErrodataWithBLOBs != null) {
                    chbInvestTradeErrordataList.add(ChbInvestTradeErrodataWithBLOBs);
                }
            }
            // 产品基本信息
            if(StringUtil.equals(tableInfo.getTableName(),ChbCheckTableInfo.个人发行登记表INFO.getTableName())){
                if("重复数据;".equals(map.get("ERRMSG"))){
                    continue;
                }
                ChbPrdBaseinfoErrodataWithBLOBs ChbInvestTradeErrodataWithBLOBs = createChbPrdBaseInfoErrordata(map,flag);
                if(ChbInvestTradeErrodataWithBLOBs != null) {
                    chbPrdBaseinfoErrordataList.add(ChbInvestTradeErrodataWithBLOBs);
                }
            }
            // 产品申报信息
            if(StringUtil.equals(tableInfo.getTableName(),ChbCheckTableInfo.个人产品申报登记INFO.getTableName())){
                if("重复数据;".equals(map.get("ERRMSG"))){
                    continue;
                }
                ChbPrdDeclareInfoErrodataWithBLOBs chbPrdBaseinfoErrodataWithBLOBs = createChbPrdDeclareInfoErrodata(map,flag);
                if(chbPrdBaseinfoErrodataWithBLOBs != null) {
                    chbPrdDeclareInfoErrodataList.add(chbPrdBaseinfoErrodataWithBLOBs);
                }
            }
            //交易日历
            if(StringUtil.equals(tableInfo.getTableName(),ChbCheckTableInfo.节假日.getTableName())){
                if("重复数据;".equals(map.get("ERRMSG"))){
                    continue;
                }
                MstHolidayErrodataWithBLOBs mstHolidayErrodataWithBLOBs = createMstHolidayErrodata(map,flag);
                if(mstHolidayErrodataWithBLOBs != null) {
                    mstHolidayErrodataList.add(mstHolidayErrodataWithBLOBs);
                }
            }
            //汇率行情
            if(StringUtil.equals(tableInfo.getTableName(),ChbCheckTableInfo.汇率行情信息.getTableName())){
                if("重复数据;".equals(map.get("ERRMSG"))){
                    continue;
                }
                MstExchargeQuoteErrodataWithBLOBs mstExchargeQuoteErrodataWithBLOBs = createMstExchargeQuoteErrodata(map,flag);
                if(mstExchargeQuoteErrodataWithBLOBs != null) {
                    mstExchargeQuoteErrodataList.add(mstExchargeQuoteErrodataWithBLOBs);
                }
            }
            //科目定义
            if(StringUtil.equals(tableInfo.getTableName(),ChbCheckTableInfo.科目定义.getTableName())){
                if("重复数据;".equals(map.get("ERRMSG"))){
                    continue;
                }
                BokSubjectErrodataWithBLOBs bokSubjectErrodataWithBLOBs = createBokSubjectErrodata(map,flag);
                if(bokSubjectErrodataWithBLOBs != null) {
                    bokSubjectErrodataList.add(bokSubjectErrodataWithBLOBs);
                }
            }
            //销售数据反馈
            if (StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.销售结算资金交易信息表INFO.getTableName())){
                // 记录销售数据信息表
                ChbSalerStlDeaErrodataWithBLOBs ChbSalerStlDeaErrodata = createChbSalerStlDeaErrodata(map);
                if(ChbSalerStlDeaErrodata != null) {
                    chbSalerStlDeaErrodataList.add(ChbSalerStlDeaErrodata);
                }
            }
            //销售人员数据
            if (StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.销售人员身份信息表INFO.getTableName())) {
                //销售人员信息
                ChbSalerIdenErrodataWithBLOBs   chbSalerIdenErrodata=createChbSalerInderErrodata(map);
                if(chbSalerIdenErrodata !=null){
                    chbSalerIdenErrodataList.add(chbSalerIdenErrodata);
                }
            }
            //总部或分支机构信息
            if (StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.总部或分支机构信息表INFO.getTableName())) {
                ChbSalerInstErrodataWithBLOBs   chbSalerIdenErrodata=createChbSalerInstErrodata(map);
                if(chbSalerIdenErrodata !=null){
                    chbSalerInstErrodataList.add(chbSalerIdenErrodata);
                }
            }
            //销售人员惩奖记录信息
            if (StringUtil.equals(tableInfo.getTableName(), ChbCheckTableInfo.销售人员奖惩投诉记录信息表INFO.getTableName())) {
                ChbSalerRewPenaErrodataWithBLOBs   chbSalerRewPenaErrodata=createChbSalerRewErrodata(map);
                if(chbSalerRewPenaErrodata !=null){
                    chbSalerRewPenaErrodataList.add(chbSalerRewPenaErrodata);
                }
            }


        }

        //错误信息入库
        if (StringUtil.isEmptyList(chbAssetInfoErrorList)){
            // 销售错误信息表入库
            if(!chbSalerStlDeaErrodataList.isEmpty()){
                deleteChbSalerStlDeaErrodataByTradeNo(chbSalerStlDeaErrodataList);
                insertChbSalerStlDeaErrodata(chbSalerStlDeaErrodataList);
            }
            //销售人员信息错误表入库
            else if(!chbSalerIdenErrodataList.isEmpty()){
                deleteChbSalerIdenErrodataByNo(chbSalerIdenErrodataList);
                insertChbSalerIdenErrodata(chbSalerIdenErrodataList);
            }
            //总部或分支机构信息错误表入库
            else if(!chbSalerInstErrodataList.isEmpty()) {
                deleteChbSalerInstErrodataByNo(chbSalerInstErrodataList);
                insertChbSalerInstErrodata(chbSalerInstErrodataList);
            }
            //销售人员惩奖记录信息入库
            else if(!chbSalerRewPenaErrodataList.isEmpty()) {
                deleteChbSalerRewErrodataByNo(chbSalerRewPenaErrodataList);
                insertChbSalerRewErrodata(chbSalerRewPenaErrodataList);
            }

            else if(!erroList.isEmpty()){
//                insertErroData(erroList);
            }

        }else{
            Iterator<ChbAssetInfoErrodataWithBLOBs> iterator = chbAssetInfoErrorList.iterator();
            while(iterator.hasNext()){
                ChbAssetInfoErrodataWithBLOBs bloBs = iterator.next();
                if(StringUtils.isEmpty(bloBs.getAssetCode())){
                    iterator.remove();
                }
            }
            // 资产要素错误信息入库
            if(!flag.equals("D")&&coverFlag.equals("N")){
                removeExistsChbAssetInfoError(chbAssetInfoErrorList);
            }
            deleteAssetInfoErrorDataByAssetCode(chbAssetInfoErrorList);
            deleteChbAssetInfo(chbAssetInfoErrorList);
            insertChbAssetInfoErroData(chbAssetInfoErrorList);

        }
        //投资交易错误信息入库
        if(StringUtil.isEmptyList(chbInvestTradeErrordataList)){

        }else{
            Iterator<ChbInvestTradeErrodataWithBLOBs> iterator = chbInvestTradeErrordataList.iterator();
            while(iterator.hasNext()){
                ChbInvestTradeErrodataWithBLOBs next = iterator.next();
                if(StringUtils.isEmpty(next.getProdId())||StringUtils.isEmpty(next.getAssetCode())||StringUtils.isEmpty(next.getTradeCode())){
                    iterator.remove();
                }
            }
            if(!flag.equals("D")&&coverFlag.equals("N")){
                removeExistsChbInvestTrade(chbInvestTradeErrordataList);
            }
            deleteChbInvestTradeErrorData(chbInvestTradeErrordataList);
            deleteChbInvestTrade(chbInvestTradeErrordataList);
            insertChbInvestTradeErroData(chbInvestTradeErrordataList);
        }
        //产品基本信息
        if(StringUtil.isEmptyList(chbPrdBaseinfoErrordataList)){

        }else{
            Iterator<ChbPrdBaseinfoErrodataWithBLOBs> iterator = chbPrdBaseinfoErrordataList.iterator();
            while(iterator.hasNext()){
                ChbPrdBaseinfoErrodataWithBLOBs bloBs = iterator.next();
                if(StringUtils.isEmpty(bloBs.getProdId())){
                    iterator.remove();
                }
            }
            if(!flag.equals("D")&&coverFlag.equals("N")){
                removeExistsChbPrdBaseInfo(chbPrdBaseinfoErrordataList);
            }
            deleteChbPrdBaseInfoErrorData(chbPrdBaseinfoErrordataList);
            deleteChbPrdBaseInfo(chbPrdBaseinfoErrordataList);
            insertChbPrdBaseInfoErroData(chbPrdBaseinfoErrordataList);
        }
        //产品申报
        if(StringUtil.isEmptyList(chbPrdDeclareInfoErrodataList)){

        }else{
            if(!flag.equals("D")&&coverFlag.equals("N")){
                removeExistsChbPrdDeclareInfo(chbPrdDeclareInfoErrodataList);
            }
            deleteChbPrdDeclareInfoErrorData(chbPrdDeclareInfoErrodataList);
            deleteChbPrdDeclareInfo(chbPrdDeclareInfoErrodataList);
            insertChbPrdDeclareInfoErroData(chbPrdDeclareInfoErrodataList);
        }
        //交易日历
        if(StringUtil.isEmptyList(mstHolidayErrodataList)){

        }else{
            Iterator<MstHolidayErrodataWithBLOBs> iterator = mstHolidayErrodataList.iterator();
            while(iterator.hasNext()){
                MstHolidayErrodataWithBLOBs next = iterator.next();
                if(StringUtils.isEmpty(next.getCalendarId())||next.getCdate()==null){
                    iterator.remove();
                }
            }
            deleteMstHolidayErrodata(mstHolidayErrodataList);
            deleteMstHoliday(mstHolidayErrodataList);
            insertMstHolidayErrodata(mstHolidayErrodataList);
        }
        //汇率行情
        if(StringUtil.isEmptyList(mstExchargeQuoteErrodataList)){

        }else{
            Iterator<MstExchargeQuoteErrodataWithBLOBs> iterator = mstExchargeQuoteErrodataList.iterator();
            while(iterator.hasNext()){
                MstExchargeQuoteErrodataWithBLOBs next = iterator.next();
                if(StringUtils.isEmpty(next.getExchargeCode())||next.getTradeDate()==null||StringUtils.isEmpty(next.getDatasourceType())){
                    iterator.remove();
                }
            }
            deleteMstExchargeQuoteErrodata(mstExchargeQuoteErrodataList);
            deleteMstExchargeQuote(mstExchargeQuoteErrodataList);
            insertMstExchargeQuoteErrodata(mstExchargeQuoteErrodataList);
        }
        //科目定义
        if(StringUtil.isEmptyList(bokSubjectErrodataList)){

        }else{
            Iterator<BokSubjectErrodataWithBLOBs> iterator = bokSubjectErrodataList.iterator();
            while(iterator.hasNext()){
                BokSubjectErrodataWithBLOBs next = iterator.next();
                if(StringUtils.isEmpty(next.getSubjectNo())){
                    iterator.remove();
                }
            }
            deleteBokSubjectErrodata(bokSubjectErrodataList);
            deleteBokSubject(bokSubjectErrodataList);
            insertBokSubjectErrodata(bokSubjectErrodataList);
        }

    }

    private void insertBokSubjectErrodata(List<BokSubjectErrodataWithBLOBs> bokSubjectErrodataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("科目定义信息错误信息数据插入开始--------------" + new Date());
            BokSubjectErrodataMapper batchMapper = session.getMapper(BokSubjectErrodataMapper.class);
            for(BokSubjectErrodataWithBLOBs single: bokSubjectErrodataList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("科目定义错误信息数据插入结束--------------" + new Date());
    }

    public void deleteBokSubject(List<BokSubjectErrodataWithBLOBs> bokSubjectErrodataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            batchMapper.deleteBokSubject(bokSubjectErrodataList);
            session.commit();
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    public void deleteBokSubjectErrodata(List<BokSubjectErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("科目定义信息错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(BokSubjectErrodataWithBLOBs single: erroList){
                if (StringUtils.isNotEmpty(single.getSubjectNo())&&StringUtils.isNotEmpty(single.getBooksetId())){
                    try {
                        batchMapper.deleteBokSubjectErrodata(single.getBooksetId(),single.getSubjectNo());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("科目定义信息错误信息数据删除结束--------------" + new Date());
    }

    private BokSubjectErrodataWithBLOBs createBokSubjectErrodata(Map<String, String> map, String flag) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }

        // 拼装错误信息
        BokSubjectErrodataWithBLOBs bokSubjectErrodataWithBLOBs = new BokSubjectErrodataWithBLOBs();
        // 主键uuid
        bokSubjectErrodataWithBLOBs.setDatauuid(IDGenerator.generate(36));
        bokSubjectErrodataWithBLOBs.setSubjectNo(map.get("SUBJECT_NO"));
        bokSubjectErrodataWithBLOBs.setBooksetId(map.get("BOOKSET_ID"));
        bokSubjectErrodataWithBLOBs.setSubjectName(map.get("SUBJECT_NAME"));
        bokSubjectErrodataWithBLOBs.setSubjectType(map.get("SUBJECT_TYPE"));
        bokSubjectErrodataWithBLOBs.setSubjectLevel(map.get("SUBJECT_LEVEL"));
        bokSubjectErrodataWithBLOBs.setFsubjectId(map.get("FSUBJECT_ID"));
        bokSubjectErrodataWithBLOBs.setBalFlag(map.get("BAL_FLAG"));
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        newMap.remove(null);
        bokSubjectErrodataWithBLOBs.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        bokSubjectErrodataWithBLOBs.setErrMsg(msg);
        // 数据状态
        bokSubjectErrodataWithBLOBs.setStatus("A");
        if(flag.equals("I")){
            bokSubjectErrodataWithBLOBs.setCreateUser("interf");
            bokSubjectErrodataWithBLOBs.setUpdateUser("interf");
        }else{
            bokSubjectErrodataWithBLOBs.setCreateUser(CommonUtil.getUserName());
            bokSubjectErrodataWithBLOBs.setUpdateUser(CommonUtil.getUserName());
        }
        bokSubjectErrodataWithBLOBs.setCreateTime(com.joyintech.framework.util.DateUtil.now());
        bokSubjectErrodataWithBLOBs.setUpdateTime(DateUtil.now());
        return bokSubjectErrodataWithBLOBs;
    }

    private void insertMstExchargeQuoteErrodata(List<MstExchargeQuoteErrodataWithBLOBs> mstExchargeQuoteErrodataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("汇率行情信息错误信息数据插入开始--------------" + new Date());
            MstExchargeQuoteErrodataMapper batchMapper = session.getMapper(MstExchargeQuoteErrodataMapper.class);
            for(MstExchargeQuoteErrodataWithBLOBs single: mstExchargeQuoteErrodataList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("汇率行情错误信息数据插入结束--------------" + new Date());

    }

    private void deleteMstExchargeQuote(List<MstExchargeQuoteErrodataWithBLOBs> mstExchargeQuoteErrodataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            batchMapper.deleteMstExchargeQuote(mstExchargeQuoteErrodataList);
            session.commit();
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    public void deleteMstExchargeQuoteErrodata(List<MstExchargeQuoteErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("汇率行情信息错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(MstExchargeQuoteErrodataWithBLOBs single: erroList){
                if (StringUtils.isNotEmpty(single.getExchargeCode())&& single.getTradeDate()!=null&&StringUtils.isNotEmpty(single.getDatasourceType())){
                    try {
                        batchMapper.deleteMstExchargeQuoteErrodata(single.getExchargeCode(), com.joyintech.framework.util.DateUtil.format(single.getTradeDate(),"yyyy-MM-d"),single.getDatasourceType());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("汇率行情信息错误信息数据删除结束--------------" + new Date());
    }

    private MstExchargeQuoteErrodataWithBLOBs createMstExchargeQuoteErrodata(Map<String, String> map, String flag) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }

        // 拼装错误信息
        MstExchargeQuoteErrodataWithBLOBs mstExchargeQuoteErrodataWithBLOBs = new MstExchargeQuoteErrodataWithBLOBs();
        // 主键uuid
        mstExchargeQuoteErrodataWithBLOBs.setDatauuid(IDGenerator.generate(36));
        mstExchargeQuoteErrodataWithBLOBs.setExchargeCode(map.get("EXCHARGE_CODE"));

        try {
            mstExchargeQuoteErrodataWithBLOBs.setTradeDate(com.joyintech.framework.util.DateUtil.parseDate(map.get("TRADE_DATE"),"yyyy-MM-dd"));
        } catch (ParseException e) {
            mstExchargeQuoteErrodataWithBLOBs.setTradeDate(null);
        }
        mstExchargeQuoteErrodataWithBLOBs.setDatasourceType(map.get("DATASOURCE_TYPE"));
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        newMap.remove(null);
        mstExchargeQuoteErrodataWithBLOBs.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        mstExchargeQuoteErrodataWithBLOBs.setErrMsg(msg);
        // 数据状态
        mstExchargeQuoteErrodataWithBLOBs.setStatus("A");
        if(flag.equals("I")){
            mstExchargeQuoteErrodataWithBLOBs.setCreateUser("interf");
            mstExchargeQuoteErrodataWithBLOBs.setUpdateUser("interf");
        }else{
            mstExchargeQuoteErrodataWithBLOBs.setCreateUser(CommonUtil.getUserName());
            mstExchargeQuoteErrodataWithBLOBs.setUpdateUser(CommonUtil.getUserName());
        }
        mstExchargeQuoteErrodataWithBLOBs.setCreateTime(com.joyintech.framework.util.DateUtil.now());
        mstExchargeQuoteErrodataWithBLOBs.setUpdateTime(DateUtil.now());
        return mstExchargeQuoteErrodataWithBLOBs;
    }

    private void insertMstHolidayErrodata(List<MstHolidayErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("节假日信息错误信息数据插入开始--------------" + new Date());
            MstHolidayErrodataMapper batchMapper = session.getMapper(MstHolidayErrodataMapper.class);
            for(MstHolidayErrodataWithBLOBs single: erroList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("节假日信息错误信息数据插入结束--------------" + new Date());

    }

    public void deleteMstHoliday(List<MstHolidayErrodataWithBLOBs> mstHolidayErrodataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            batchMapper.deleteMstHoliday(mstHolidayErrodataList);
            session.commit();
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    public void deleteMstHolidayErrodata(List<MstHolidayErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("节假日信息错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(MstHolidayErrodataWithBLOBs single: erroList){
                if (StringUtils.isNotEmpty(single.getCalendarId())&& single.getCdate()!=null){
                    try {
                        batchMapper.deleteMstHolidayErrodata(single.getCalendarId(), com.joyintech.framework.util.DateUtil.format(single.getCdate(),"yyyy-MM-d"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("节假日信息错误信息数据删除结束--------------" + new Date());
    }

    private MstHolidayErrodataWithBLOBs createMstHolidayErrodata(Map<String, String> map, String flag) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }

        // 拼装错误信息
        MstHolidayErrodataWithBLOBs mstHolidayErrodataWithBLOBs = new MstHolidayErrodataWithBLOBs();
        // 主键uuid
        mstHolidayErrodataWithBLOBs.setDatauuid(IDGenerator.generate(36));
        mstHolidayErrodataWithBLOBs.setCalendarId(map.get("CALENDAR_ID"));
        try {
            mstHolidayErrodataWithBLOBs.setIsHoliday(Long.valueOf(map.get("IS_HOLIDAY")));
        } catch (NumberFormatException e) {
            mstHolidayErrodataWithBLOBs.setIsHoliday(null);
        }
        try {
            mstHolidayErrodataWithBLOBs.setCdate(com.joyintech.framework.util.DateUtil.parseDate(map.get("CDATE"),"yyyy-MM-dd"));
        } catch (ParseException e) {
            mstHolidayErrodataWithBLOBs.setCdate(null);
        }
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        newMap.remove(null);
        mstHolidayErrodataWithBLOBs.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        mstHolidayErrodataWithBLOBs.setErrMsg(msg);
        // 数据状态
        mstHolidayErrodataWithBLOBs.setStatus("A");
        if(flag.equals("I")){
            mstHolidayErrodataWithBLOBs.setCreateUser("interf");
            mstHolidayErrodataWithBLOBs.setUpdateUser("interf");
        }else{
            mstHolidayErrodataWithBLOBs.setCreateUser(CommonUtil.getUserName());
            mstHolidayErrodataWithBLOBs.setUpdateUser(CommonUtil.getUserName());
        }
        mstHolidayErrodataWithBLOBs.setCreateTime(com.joyintech.framework.util.DateUtil.now());
        mstHolidayErrodataWithBLOBs.setUpdateTime(DateUtil.now());
        return mstHolidayErrodataWithBLOBs;

    }

    private void updateExcelValidDtoList(List<ExcelValidDto> excelValidDtoList, List<Map<String, String>> list, ChbCheckTableInfo tableInfo) {
        if(excelValidDtoList!=null && excelValidDtoList.size()>0){
            switch (tableInfo.getTableName()){
                case "CHB_ASSET_INFO":
                    for(int i = 0;i<list.size();i++){
                        if(StringUtils.isEmpty(list.get(i).get("ASSET_CODE"))){
                            for (ExcelValidDto dto : excelValidDtoList) {
                                if(dto.getRowNum().equals(String.valueOf(i+3))){
                                    dto.setValidKey("导入失败");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "CHB_PRD_BASEINFO":
                    for(int i = 0;i<list.size();i++){
                        if(StringUtils.isEmpty(list.get(i).get("PROD_ID"))){
                            for (ExcelValidDto dto : excelValidDtoList) {
                                if(dto.getRowNum().equals(String.valueOf(i+2))){
                                    dto.setValidKey("导入失败");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "CHB_PRD_DECLARE_INFO":
                    for(int i = 0;i<list.size();i++){
                        if(StringUtils.isEmpty(list.get(i).get("PROD_INS_ID"))){
                            for (ExcelValidDto dto : excelValidDtoList) {
                                if(dto.getRowNum().equals(String.valueOf(i+2))){
                                    dto.setValidKey("导入失败");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "CHB_INVEST_TRADE":
                    for(int i = 0;i<list.size();i++){
                        if(StringUtils.isEmpty(list.get(i).get("PROD_ID"))||
                                StringUtils.isEmpty(list.get(i).get("TRADE_CODE"))||
                                StringUtils.isEmpty(list.get(i).get("ASSET_CODE"))){
                            for (ExcelValidDto dto : excelValidDtoList) {
                                if(dto.getRowNum().equals(String.valueOf(i+2))){
                                    dto.setValidKey("导入失败");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "MST_EXCHARGE_QUOTE":
                    for(int i = 0;i<list.size();i++){
                        if(StringUtils.isEmpty(list.get(i).get("EXCHARGE_CODE"))||
                                StringUtils.isEmpty(list.get(i).get("TRADE_DATE"))||
                                StringUtils.isEmpty(list.get(i).get("DATASOURCE_TYPE"))){
                            for (ExcelValidDto dto : excelValidDtoList) {
                                if(dto.getRowNum().equals(String.valueOf(i+2))){
                                    dto.setValidKey("导入失败");
                                    break;
                                }
                            }
                        }else{
                            try {
                                com.joyintech.framework.util.DateUtil.parseDate(list.get(i).get("TRADE_DATE"),"yyyy-MM-dd");
                            } catch (ParseException e) {
                                for (ExcelValidDto dto : excelValidDtoList) {
                                    if(dto.getRowNum().equals(String.valueOf(i+2))){
                                        dto.setValidKey("导入失败");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "BOK_SUBJECT":
                    for(int i = 0;i<list.size();i++){
                        if(StringUtils.isEmpty(list.get(i).get("SUBJECT_NO"))||StringUtils.isEmpty(list.get(i).get("BOOKSET_ID"))){
                            for (ExcelValidDto dto : excelValidDtoList) {
                                if(dto.getRowNum().equals(String.valueOf(i+2))){
                                    dto.setValidKey("导入失败");
                                    break;
                                }
                            }
                        }
                    }
                    break;
            }

        }
    }

    private void insertChbPrdDeclareInfoErroData(List<ChbPrdDeclareInfoErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("产品基本信息错误信息数据插入开始--------------" + new Date());
            ChbPrdDeclareInfoErrodataMapper batchMapper = session.getMapper(ChbPrdDeclareInfoErrodataMapper.class);
            for(ChbPrdDeclareInfoErrodataWithBLOBs single: erroList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("产品基本信息错误信息数据插入结束--------------" + new Date());
    }

    private void deleteChbPrdDeclareInfo(List<ChbPrdDeclareInfoErrodataWithBLOBs> chbPrdDeclareInfoErrodataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            batchMapper.deleteChbPrdDeclareInfo(chbPrdDeclareInfoErrodataList);
            session.commit();
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    private void deleteChbPrdDeclareInfoErrorData(List<ChbPrdDeclareInfoErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("产品申报信息错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(ChbPrdDeclareInfoErrodataWithBLOBs single: erroList){
                if (StringUtils.isNotEmpty(single.getProdInsId())){
                    batchMapper.deleteChbPrdDeclareInfoErrorData(single.getProdInsId());
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("产品申报信息错误信息数据删除结束--------------" + new Date());
    }

    private void removeExistsChbPrdDeclareInfo(List<ChbPrdDeclareInfoErrodataWithBLOBs> chbPrdDeclareInfoErrodataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            List<Map<String,String>> list = batchMapper.selectChbPrdDeclareInfoBusKey(chbPrdDeclareInfoErrodataList);
            if(list !=null){
                Iterator<ChbPrdDeclareInfoErrodataWithBLOBs> iterator = chbPrdDeclareInfoErrodataList.iterator();
                while (iterator.hasNext()){
                    ChbPrdDeclareInfoErrodataWithBLOBs dto = iterator.next();
                    for (Map<String, String> map : list) {
                        if(map.get("PROD_INS_ID").equals(dto.getProdInsId())){
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    private ChbPrdDeclareInfoErrodataWithBLOBs createChbPrdDeclareInfoErrodata(Map<String, String> map, String flag) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }else{
            //行内标识码为空不入库
            if(StringUtils.isEmpty(map.get("PROD_INS_ID"))){
                return null;
            }
        }

        // 拼装错误信息
        ChbPrdDeclareInfoErrodataWithBLOBs chbPrdDeclareInfoErrodataWithBLOBs = new ChbPrdDeclareInfoErrodataWithBLOBs();
        // 主键uuid
        chbPrdDeclareInfoErrodataWithBLOBs.setDatauuid(IDGenerator.generate(36));
        chbPrdDeclareInfoErrodataWithBLOBs.setProdName(map.get("PROD_NAME"));
        chbPrdDeclareInfoErrodataWithBLOBs.setProdInsId(map.get("PROD_INS_ID"));
        chbPrdDeclareInfoErrodataWithBLOBs.setTermtype(map.get("TERMTYPE"));
        chbPrdDeclareInfoErrodataWithBLOBs.setOperateMode(map.get("OPERATE_MODE"));
        try {
            chbPrdDeclareInfoErrodataWithBLOBs.setRaisePstrdate(com.joyintech.framework.util.DateUtil.parseDate(map.get("RAISE_PSTRDATE"),"yyyy-MM-dd"));
        } catch (ParseException e) {
            chbPrdDeclareInfoErrodataWithBLOBs.setRaisePstrdate(null);
        }
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        newMap.remove(null);
        chbPrdDeclareInfoErrodataWithBLOBs.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        chbPrdDeclareInfoErrodataWithBLOBs.setErrMsg(msg);
        // 数据状态
        chbPrdDeclareInfoErrodataWithBLOBs.setStatus("A");
        if(flag.equals("I")){
            chbPrdDeclareInfoErrodataWithBLOBs.setCreateUser("interf");
            chbPrdDeclareInfoErrodataWithBLOBs.setUpdateUser("interf");
        }else{
            chbPrdDeclareInfoErrodataWithBLOBs.setCreateUser(CommonUtil.getUserName());
            chbPrdDeclareInfoErrodataWithBLOBs.setUpdateUser(CommonUtil.getUserName());
        }
        chbPrdDeclareInfoErrodataWithBLOBs.setCreateTime(com.joyintech.framework.util.DateUtil.now());
        chbPrdDeclareInfoErrodataWithBLOBs.setUpdateTime(DateUtil.now());
        return chbPrdDeclareInfoErrodataWithBLOBs;
    }

    private void deleteChbAssetInfo(List<ChbAssetInfoErrodataWithBLOBs> chbAssetInfoErrorList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            batchMapper.deleteChbAssetInfo(chbAssetInfoErrorList);
            session.commit();
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    private void deleteChbInvestTrade(List<ChbInvestTradeErrodataWithBLOBs> chbInvestTradeErrordataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            batchMapper.deleteChbInvestTrade(chbInvestTradeErrordataList);
            session.commit();
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    private void deleteChbPrdBaseInfo(List<ChbPrdBaseinfoErrodataWithBLOBs> chbPrdBaseinfoErrordataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            batchMapper.deleteChbPrdBaseInfo(chbPrdBaseinfoErrordataList);
            session.commit();
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    private void removeExistsChbAssetInfoError(List<ChbAssetInfoErrodataWithBLOBs> chbAssetInfoErrorList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            List<Map<String,String>> list = batchMapper.selectChbAssetInfoBusKey(chbAssetInfoErrorList);
            if(list !=null){
                Iterator<ChbAssetInfoErrodataWithBLOBs> iterator = chbAssetInfoErrorList.iterator();
                while (iterator.hasNext()){
                    ChbAssetInfoErrodataWithBLOBs dto = iterator.next();
                    for (Map<String, String> map : list) {
                        if(map.get("ASSET_CODE").equals(dto.getAssetCode())){
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    private void removeExistsChbPrdBaseInfo(List<ChbPrdBaseinfoErrodataWithBLOBs> chbPrdBaseinfoErrordataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            List<Map<String,String>> list = batchMapper.selectChbPrdBaseInfoBusKey(chbPrdBaseinfoErrordataList);
            if(list !=null){
                Iterator<ChbPrdBaseinfoErrodataWithBLOBs> iterator = chbPrdBaseinfoErrordataList.iterator();
                while (iterator.hasNext()){
                    ChbPrdBaseinfoErrodataWithBLOBs dto = iterator.next();
                    for (Map<String, String> map : list) {
                        if(map.get("PROD_ID").equals(dto.getProdId())){
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    private void removeExistsChbInvestTrade(List<ChbInvestTradeErrodataWithBLOBs> chbInvestTradeErrordataList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            List<Map<String,String>> list = batchMapper.selectChbInvestTradeBusKey(chbInvestTradeErrordataList);
            if(list!=null){
                Iterator<ChbInvestTradeErrodataWithBLOBs> iterator = chbInvestTradeErrordataList.iterator();
                while (iterator.hasNext()){
                    ChbInvestTradeErrodataWithBLOBs dto = iterator.next();
                    for (Map<String, String> e : list) {
                        if(e.get("PROD_ID").equals(dto.getProdId())
                                && e.get("ASSET_CODE").equals(dto.getAssetCode())
                                && e.get("TRADE_CODE").equals(dto.getTradeCode())){
                            iterator.remove();
                            break;
                        }
                    }

                }
            }

        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }

    private void insertChbPrdBaseInfoErroData(List<ChbPrdBaseinfoErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("产品基本信息错误信息数据插入开始--------------" + new Date());
            ChbPrdBaseinfoErrodataMapper batchMapper = session.getMapper(ChbPrdBaseinfoErrodataMapper.class);
            for(ChbPrdBaseinfoErrodataWithBLOBs single: erroList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("产品基本信息错误信息数据插入结束--------------" + new Date());
    }

    private void deleteChbPrdBaseInfoErrorData(List<ChbPrdBaseinfoErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("产品基本信息错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(ChbPrdBaseinfoErrodataWithBLOBs single: erroList){
                if (StringUtils.isNotEmpty(single.getProdId())){
                    batchMapper.deleteChbPrdBaseInfoErrorData(single.getProdId());
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("产品基本信息错误信息数据删除结束--------------" + new Date());
    }

    private ChbPrdBaseinfoErrodataWithBLOBs createChbPrdBaseInfoErrordata(Map<String, String> map,String flag) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
        // 拼装错误信息
        ChbPrdBaseinfoErrodataWithBLOBs chbPrdBaseinfoErrodataWithBLOBs = new ChbPrdBaseinfoErrodataWithBLOBs();
        // 主键uuid
        chbPrdBaseinfoErrodataWithBLOBs.setDatauuid(IDGenerator.generate(36));
        chbPrdBaseinfoErrodataWithBLOBs.setProdId(map.get("PROD_ID"));
        chbPrdBaseinfoErrodataWithBLOBs.setProdName(map.get("PROD_NAME"));
        chbPrdBaseinfoErrodataWithBLOBs.setRegCode(map.get("REG_CODE"));
        chbPrdBaseinfoErrodataWithBLOBs.setCustomerType(map.get("CUSTOMER_TYPE"));
        chbPrdBaseinfoErrodataWithBLOBs.setIssuingStatus(map.get("ISSUING_STATUS"));
        try {
            chbPrdBaseinfoErrodataWithBLOBs.setRaiseVdate(com.joyintech.framework.util.DateUtil.parseDate(map.get("RAISE_VDATE"),"yyyy-MM-dd"));
        } catch (ParseException e) {
            chbPrdBaseinfoErrodataWithBLOBs.setRaiseVdate(null);
        }
        try {
            chbPrdBaseinfoErrodataWithBLOBs.setVdate(com.joyintech.framework.util.DateUtil.parseDate(map.get("VDATE"),"yyyy-MM-dd"));
        } catch (ParseException e) {
            chbPrdBaseinfoErrodataWithBLOBs.setVdate(null);
        }
        chbPrdBaseinfoErrodataWithBLOBs.setProdName(map.get("PROD_NAME"));
        chbPrdBaseinfoErrodataWithBLOBs.setProdInsId(map.get("PROD_INS_ID"));
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        newMap.remove(null);
        chbPrdBaseinfoErrodataWithBLOBs.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        chbPrdBaseinfoErrodataWithBLOBs.setErrMsg(msg);
        // 数据状态
        chbPrdBaseinfoErrodataWithBLOBs.setStatus("A");
        if(flag.equals("I")){
            chbPrdBaseinfoErrodataWithBLOBs.setCreateUser("interf");
            chbPrdBaseinfoErrodataWithBLOBs.setUpdateUser("interf");
        }else{
            chbPrdBaseinfoErrodataWithBLOBs.setCreateUser(CommonUtil.getUserName());
            chbPrdBaseinfoErrodataWithBLOBs.setUpdateUser(CommonUtil.getUserName());
        }
        chbPrdBaseinfoErrodataWithBLOBs.setCreateTime(com.joyintech.framework.util.DateUtil.now());
        chbPrdBaseinfoErrodataWithBLOBs.setUpdateTime(DateUtil.now());
        return chbPrdBaseinfoErrodataWithBLOBs;
    }

    private void insertChbInvestTradeErroData(List<ChbInvestTradeErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("资产要素登记错误信息数据插入开始--------------" + new Date());
            ChbInvestTradeErrodataMapper batchMapper = session.getMapper(ChbInvestTradeErrodataMapper.class);
            for(ChbInvestTradeErrodataWithBLOBs single: erroList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("资产要素登记错误信息数据插入结束--------------" + new Date());
    }

    private void deleteChbInvestTradeErrorData(List<ChbInvestTradeErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("投资交易错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(ChbInvestTradeErrodataWithBLOBs single: erroList){
                if (StringUtils.isNotEmpty(single.getProdId())
                        &&StringUtil.isNotEmpty(single.getAssetCode())
                        &&StringUtil.isNotEmpty(single.getTradeCode())){
                    batchMapper.deleteChbInvestTradeErrorData(single.getProdId(),single.getAssetCode(),single.getTradeCode());
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("投资交易错误信息数据删除结束--------------" + new Date());
    }

    private ChbInvestTradeErrodataWithBLOBs createChbInvestTradeErrordata(Map<String, String> map,String flag) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
        // 拼装错误信息
        ChbInvestTradeErrodataWithBLOBs chbInvestTradeErrodataWithBLOBs = new ChbInvestTradeErrodataWithBLOBs();
        // 主键uuid
        chbInvestTradeErrodataWithBLOBs.setDatauuid(IDGenerator.generate(36));
        chbInvestTradeErrodataWithBLOBs.setProdId(map.get("PROD_ID"));
        chbInvestTradeErrodataWithBLOBs.setRegCode(map.get("REG_CODE"));
        chbInvestTradeErrodataWithBLOBs.setTradeCounter(map.get("TRADE_COUNTER"));
        chbInvestTradeErrodataWithBLOBs.setTradeCode(map.get("TRADE_CODE"));
        chbInvestTradeErrodataWithBLOBs.setAssetCode(map.get("ASSET_CODE"));
        try {
            chbInvestTradeErrodataWithBLOBs.setDealdate(com.joyintech.framework.util.DateUtil.parseDate(map.get("DEALDATE"),"yyyy-MM-dd"));
        } catch (ParseException e) {
            chbInvestTradeErrodataWithBLOBs.setDealdate(null);
        }
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        chbInvestTradeErrodataWithBLOBs.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        chbInvestTradeErrodataWithBLOBs.setErrMsg(msg);
        // 数据状态
        chbInvestTradeErrodataWithBLOBs.setStatus("A");
        if(flag.equals("I")){
            chbInvestTradeErrodataWithBLOBs.setCreateUser("interf");
            chbInvestTradeErrodataWithBLOBs.setUpdateUser("interf");
        }else{
            chbInvestTradeErrodataWithBLOBs.setCreateUser(CommonUtil.getUserName());
            chbInvestTradeErrodataWithBLOBs.setUpdateUser(CommonUtil.getUserName());
        }
        chbInvestTradeErrodataWithBLOBs.setCreateTime(com.joyintech.framework.util.DateUtil.now());
        chbInvestTradeErrodataWithBLOBs.setUpdateTime(DateUtil.now());
        return chbInvestTradeErrodataWithBLOBs;
    }

    /**
	 * 从业人员登记
	 * @param
	 */
    private void checkEmployeeSubInfo(Map<String, String> dataMap) {
    	String  errMsg = dataMap.getOrDefault("ERRMSG", "");
    	
    	// 发行机构代码
        String issuePartyId = dataMap.get("ISSUE_PARTY_ID");
        // 银行模式才判断发行机构代码，联社模式不判断
        if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
	        // 发行机构代码必须与银行代码相同。
	        if(!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)){
	            errMsg +="发行机构代码必须与银行代码相同[ISSUE_PARTY_ID="+issuePartyId+"];";
	        }
        }
        
        //已登记人员
//		int cnt = comminCodeCheckMapper.findEmployeeSub(getKey(dataMap));
//		if (cnt > 0){
//	         errMsg +="从业人员类型: " + (dataMap.get("EMPLOYEE_TYPE") + " " + SvrDataUtil.getNameFromDataDict(DictCode.从业人员类型, dataMap.get("EMPLOYEE_TYPE")))
//			                +" 身份证件号: " + dataMap.get("IDENTIFY_NUMBER") + "已注册,不能重复注册;";
//	    }
		
		dataMap.put("ERRMSG", errMsg);
	}

//    private String getKey(Map<String,String> map){
//    	return map.get("EMPLOYEE_TYPE") + GlobalConstant.VERTICAL_LINE + map.get("IDENTIFY_NUMBER");
//    }
    
	private boolean checkValIsNum(String str){
        Map<String, String> data = new HashMap<>();
        CheckElement rule = new CheckElement();
        rule.setSrcproperty("CHECK_STR");
        rule.setNullable("N");
        data.put("CHECK_STR", str);
        return BaseCheckLib.isNumber2(data, rule);
    }

    private boolean checkValIsDate(String str){
        Map<String, String> data = new HashMap<>();
        CheckElement rule = new CheckElement();
        data.put("CHECK_STR", str);
        rule.setSrcproperty("CHECK_STR");
        rule.setNullable("N");
        rule.setObjvalue(DateUtil.DEFAULT_DATE_FORMAT_EN);
        return BaseCheckLib.isValidDate(data, rule);
    }

    /**
     * 底层资产持仓登记
     * @param dataMap
     */
    private void checkBotAstPos(Map<String, String> dataMap, Map<String,String> ListMap) {
        String  errMsg = dataMap.getOrDefault("ERRMSG", "");
        // 发行机构代码
        String issuePartyId=dataMap.get("ISSUE_PARTY_ID");
        // 资管计划代码(对应资产管理计划行内资产/负债编码)
        String planAssetId=dataMap.get("PLAN_ASSET_ID");
        // 行内资产/负债编码
        String assetCode=dataMap.get("ASSET_CODE");
        // 持仓日期
        String posDate = dataMap.get("POS_DATE");
        // 当前总数量
        String totalNum = dataMap.get("TOTAL_NUM");
        // 资管计划当前总折算人民币金额（元）
        String issueAmt = dataMap.get("ISSUE_AMT");
        // 资管计划折算人民币未投资头寸（元）
        String uninvestCash = dataMap.get("UNINVEST_CASH");

        List<ChbBotAstPositionSub> subList = new ArrayList<>();
        if(StringUtil.isNotEmpty(issuePartyId)){
        // 发行机构代码必须与银行代码相同。
        // 银行模式才判断发行机构代码，联社模式不判断
        if(!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)
                && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
            errMsg +="发行机构代码必须与银行代码相同[ISSUE_PARTY_ID="+issuePartyId+"];";
        }}
        // 对应资管及委外资产行内资产/负债编码和底层资产行内资产/负债编码不能相同。
        if (StringUtil.equals(planAssetId, assetCode)){
            errMsg +="对应资管及委外资产行内资产/负债编码和底层资产行内资产/负债编码不能相同[PLAN_ASSET_ID="+planAssetId+"];";
        }

        if (StringUtil.isNotEmpty(planAssetId) && StringUtil.isNotEmpty(posDate) && StringUtil.isNotEmpty(assetCode)){
            // 报文内相当资产代码个数
        	String key = planAssetId+"_"+posDate;
        	String  dataTotalNum = ListMap.get(key+"_"+"TOTAL_NUM");
        	String  dataIssueAmt = ListMap.get(key+"_"+"ISSUE_AMT");
        	String  dataUninvestCash = ListMap.get(key+"_"+"UNINVEST_CASH");
        	String  valKey = ListMap.get(key+"_"+assetCode);
            // 同一对应资管及委外资产行内资产/负债编码、同一持仓日期时，所有的资管及委外资产当前总数量必须相同。
            if (null!=dataTotalNum&&
            		checkValIsNum(totalNum)&&checkValIsNum(dataTotalNum)&& 
            		new BigDecimal(totalNum).compareTo(new BigDecimal(dataTotalNum)) != 0){
                errMsg +="同一对应资管及委外资产行内资产/负债编码、同一持仓日期时，所有的资管及委外资产当前总数量必须相同[PLAN_ASSET_ID="+planAssetId+"];";
            }else{
            	ListMap.put(key+"_"+"TOTAL_NUM", totalNum);
            }
            // 同一对应资管及委外资产行内资产/负债编码、同一持仓日期时，所有的资管及委外资产当前总折算人民币金额（元）必须相同。
            if (null!=dataIssueAmt&&
            		checkValIsNum(issueAmt)&&checkValIsNum(dataIssueAmt)&& 
            		new BigDecimal(issueAmt).compareTo(new BigDecimal(dataIssueAmt)) != 0){
                errMsg +="同一对应资管及委外资产行内资产/负债编码、同一持仓日期时，所有的资管及委外资产当前总折算人民币金额（元）必须相同[PLAN_ASSET_ID="+planAssetId+"];";
            }else{
            	ListMap.put(key+"_"+"ISSUE_AMT", issueAmt);
            }
            // 同一对应资管及委外资产行内资产/负债编码、同一持仓日期时，所有的资管及委外资产未投资头寸（元）必须相同。
            if (null!=dataUninvestCash&&
            		checkValIsNum(uninvestCash)&&checkValIsNum(dataUninvestCash)&& 
            		new BigDecimal(uninvestCash).compareTo(new BigDecimal(dataUninvestCash)) != 0){
                errMsg +="同一对应资管及委外资产行内资产/负债编码、同一持仓日期时，所有的资管及委外资产未投资头寸（元）必须相同[PLAN_ASSET_ID="+planAssetId+"];";
            }else{
            	ListMap.put(key+"_"+"UNINVEST_CASH", uninvestCash);
            }
            // 对应资管及委外资产行内资产/负债编码、底层资产行内资产/负债编码和持仓日期在报文内不能同时重复。
            if (null!=valKey){
                errMsg +="对应资管及委外资产行内资产/负债编码、底层资产行内资产/负债编码和持仓日期在报文内不能同时重复[PLAN_ASSET_ID="+planAssetId+"];";
            }else{
            	ListMap.put(key+"_"+assetCode, "1");
            }
        }

        // 根据资管计划代码和持仓日期查询底层资产持仓信息
        if (StringUtil.isNotEmpty(planAssetId) && StringUtil.isNotEmpty(posDate) && checkValIsDate(posDate) && StringUtil.isNotEmpty(assetCode)){
            ChbBotAstPositionSub chbBotAstPosSubTmp = new ChbBotAstPositionSub();
            chbBotAstPosSubTmp.setPlanAssetId(planAssetId);
            chbBotAstPosSubTmp.setPosDate(DateUtil.parse(posDate));
            chbBotAstPosSubTmp.setAssetCode(assetCode);
            subList = commonCodeCheckMapper.queryChbBotAstPosByInfo(chbBotAstPosSubTmp);

        }

        // 系统总发行数量标志位
        boolean totleNumSysEquFlag = false;
        boolean totleIssueAmtSysEquFlag = false;
        boolean uninvestCashSysEquFlag = false;

        if (!StringUtil.isEmptyList(subList)){
            for (ChbBotAstPositionSub tmp : subList){
                if (!StringUtil.equals(assetCode, tmp.getAssetCode())){
                    if (checkValIsNum(totalNum) && new BigDecimal(totalNum).compareTo(tmp.getTotalNum()) != 0){
                        totleNumSysEquFlag = true;
                    }
                    if (checkValIsNum(issueAmt) && new BigDecimal(issueAmt).compareTo(tmp.getIssueAmt()) != 0){
                        totleIssueAmtSysEquFlag = true;
                    }
                    if (checkValIsNum(uninvestCash) && new BigDecimal(uninvestCash).compareTo(tmp.getUninvestCash()) != 0){
                        uninvestCashSysEquFlag = true;
                    }
                }
            }
        }

        // 资管及委外资产当前总发行数量与系统中的资管及委外资产当前总发行数量不一致
        if (totleNumSysEquFlag){
            errMsg +="资管及委外资产当前总发行数量与系统中的资管及委外资产当前总发行数量不一致[PLAN_ASSET_ID="+planAssetId+"];";
        }
        // 资管及委外资产当前总折算人民币发行金额(元)与系统中的资管及委外资产当前总折算人民币发行金额(元)不一致
        if (totleIssueAmtSysEquFlag){
            errMsg +="资管及委外资产当前总折算人民币发行金额(元)与系统中的资管及委外资产当前总折算人民币发行金额(元)不一致[PLAN_ASSET_ID="+planAssetId+"];";
        }
        // 资管及委外资产折算人民币未投资头寸(元)与系统中的资管及委外资产折算人民币未投资头寸(元)不一致
        if (uninvestCashSysEquFlag){
            errMsg +="资管及委外资产折算人民币未投资头寸(元)与系统中的资管及委外资产折算人民币未投资头寸(元)不一致[PLAN_ASSET_ID="+planAssetId+"];";
        }

//      // 对应资管及委外资产行内资产/负债编码为:zichan2020041018, 底层资产行内资产负债/编码为:zichan2020041001, 持仓日期为:20200103的记录在系统中重复
//      if (!StringUtil.isEmptyList(subList)){
//          for (ChbBotAstPosSubTmp tmp : subList){
//              if (StringUtil.equals(assetCode, tmp.getAssetCode())){
//                  errMsg +="对应资管及委外资产行内资产/负债编码为:" + planAssetId + ", 底层资产行内资产负债/编码为:" + assetCode + ", 持仓日期为:" + posDate + "的记录在系统中重复";
//                  break;
//              }
//          }
//      }
        // 底层资产行内资产负债/编码为:zichan2020080102的底层资产不存在
//      if (StringUtil.isNotEmpty(assetCode) && comminCodeCheckMapper.queryChbAssetInfoSubByAssetCode(assetCode) == 0){
//          errMsg +="底层资产行内资产负债/编码为:" + assetCode + "的底层资产不存在";
//      }
        dataMap.put("ERRMSG", errMsg);
    }

    /**
     * 代码检查募集总量登记
     * @param dataMap
     */
    private void checkProdRaisetot(Map<String, String> dataMap,Set<String> prodRegistCodeList){
        String  errMsg=dataMap.getOrDefault("ERRMSG", "");
        // 数据对象
        Map<String, String> data = new HashMap<>();
        // 规则对象
        CheckElement rule = new CheckElement();
        // 币种数据字典
        DataDict ccyDto = CacheUtil.getDictCode(DictCode.币种_中债.getValue());
        // 销售区域
        DataDict saleAreaDto = CacheUtil.getDictCode(DictCode.销售区域_中债.getValue());
        // 发行机构代码
        String issuePartyId=dataMap.get("ISSUE_PARTY_ID");
        // 产品登记编码
        String prodRegistCode = dataMap.get("PROD_REGIST_CODE");
        // 产品代码
        String prodId = dataMap.get("PROD_ID");
        // 认购币种及金额
        String subscribeCcyAmt = dataMap.get("SUBSCRIBE_CCY_AMT");
        // 产品销售区域及募集金额
        String saleAreaAmt = dataMap.get("SALE_AREA_AMT");
        // 实际募集金额
        String totalAmt = dataMap.get("TOTAL_AMT");
        // 代销总金额
        String consignAmt = dataMap.get("CONSIGN_AMT");
        // 备注
        String remark = dataMap.get("REMARK");
        // 区域募集总金额
        BigDecimal totleAreaAmt = BigDecimal.ZERO;
        // 区域募集金额
        List<String> areaAmtList = new ArrayList<>();
        // 折算人民币金额
        List<String> subscribeCcyAmtList = new ArrayList<>();
        // 折算人民币金额汇总
        BigDecimal totleCcyAmt = BigDecimal.ZERO;

        // 发行机构代码必须与银行代码相同。
        // 银行模式才判断发行机构代码，联社模式不判断
        if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
        if(!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)){
            errMsg +="发行机构代码必须与银行代码相同[ISSUE_PARTY_ID="+issuePartyId+"];";
        }}
        // 产品登记编码报文内不可重复。
        if (StringUtil.isNotEmpty(prodRegistCode) && prodRegistCodeList.contains(prodRegistCode)){
            errMsg +="产品登记编码报文内不可重复[PROD_REGIST_CODE="+prodRegistCode+"];";
        }
        else{
            prodRegistCodeList.add(prodRegistCode);
        }
//      // 该产品不存在。
//      if (StringUtil.isNotEmpty(prodRegistCode)){
//          List<ChbPrdresistCode> chbPrdresistCodeList = comminCodeCheckMapper.queryChbPrdresistCodeByRegCode(prodRegistCode);
//          if (StringUtil.isEmptyList(chbPrdresistCodeList)){
//              errMsg +="该产品不存在[PROD_REGIST_CODE="+prodRegistCode+"];";
//          }
//      }
        // CNY,2000,2000;HKD,1000,CNY,800
        if (StringUtil.isNotEmpty(subscribeCcyAmt)){
            List<String> singleCcyAmtList = Arrays.asList(subscribeCcyAmt.split(GlobalConstant.SEMICOLON));// CNY,2000,2000
            // 认购币种
            List<String> subscribeCcyList = new ArrayList<>();
            // 认购金额
            List<String> subscribeAmtList = new ArrayList<>();
            /*// 折算人民币金额标志位
            boolean subscribeCcyAmtFlag = false;
            // 认购金额标志位
            boolean subscribeAmtFlag = false;
            // 认购币种标志位
            boolean subscribeCcyFlag = false;*/
            for (String singleCcyAm : singleCcyAmtList){
                List<String> ccyAmtDetailList = Arrays.asList(singleCcyAm.split(GlobalConstant.COMMA));
                if (ccyAmtDetailList.size() == 3 && StringUtil.isNotEmpty(ccyAmtDetailList.get(0))
                    && StringUtil.isNotEmpty(ccyAmtDetailList.get(1)) && StringUtil.isNotEmpty(ccyAmtDetailList.get(2))){
                    subscribeCcyAmtList.add(ccyAmtDetailList.get(2));
                    subscribeAmtList.add(ccyAmtDetailList.get(1));
                    subscribeCcyList.add(ccyAmtDetailList.get(0));

                    // 认购币种
                    boolean ccyDataFlag = false;
                    if (null != ccyDto) {
                        List<? extends DataDictItem> items = ccyDto.getItems();
                        for (DataDictItem item : items) {
                            if (ccyAmtDetailList.get(0).equals(item.getItemCode())){
                                ccyDataFlag = true;
                                break;
                            }
                        }
                    }
                    if (!ccyDataFlag){
                        errMsg +="认购币种必须在枚举范围内[SUBSCRIBE_CCY_AMT="+singleCcyAm+"];";
                    }
                    // 认购金额必须为n（15,2）格式。
                    rule.setSrcproperty("SUBSCRIBEAMT");
                    rule.setObjvalue("15,2");
                    rule.setNullable("Y");
                    data.put("SUBSCRIBEAMT", ccyAmtDetailList.get(1));
                    if (!BaseCheckLib.isNumber2(data, rule)){
                        errMsg +="认购金额必须为n（15,2）格式[SUBSCRIBE_CCY_AMT="+singleCcyAm+"];";
                    }
                    // 折算人民币金额必须为n（15,2）格式。
                    data.put("SUBSCRIBEAMT", ccyAmtDetailList.get(2));
                    if (!BaseCheckLib.isNumber2(data, rule)){
                        errMsg +="折算人民币金额必须为n（15,2）格式[SUBSCRIBE_CCY_AMT="+singleCcyAm+"];";
                    }
                    // 认购金额必须大于等于0。
                    rule.setObjvalue("0");
                    rule.setCompareto(">=");
                    rule.setNullable("N");
                    data.put("SUBSCRIBEAMT", ccyAmtDetailList.get(1));
                    if (!BaseCheckLib.compareValueAsNumber(data, rule)){
                        errMsg +="认购金额必须大于等于0[SUBSCRIBE_CCY_AMT="+singleCcyAm+"];";
                    }
                    // 折算人民币金额必须大于等于0。
                    data.put("SUBSCRIBEAMT", ccyAmtDetailList.get(2));
                    if (!BaseCheckLib.compareValueAsNumber(data, rule)){
                        errMsg +="折算人民币金额必须大于等于0[SUBSCRIBE_CCY_AMT="+singleCcyAm+"];";
                    }

                    if (StringUtil.equals(ccyAmtDetailList.get(0), DictCodeListExt1.币种_中债_人民币.getValue())){
                        if (checkValIsNum(ccyAmtDetailList.get(1)) && checkValIsNum(ccyAmtDetailList.get(2))){
                            if (new BigDecimal(ccyAmtDetailList.get(1)).compareTo(new BigDecimal(ccyAmtDetailList.get(2))) != 0){
                                // 当认购金额的币种为人民币(CNY)时，折算人民币金额与认购金额必须相等。
                                errMsg +="当认购金额的币种为人民币(CNY)时，折算人民币金额与认购金额必须相等[SUBSCRIBE_CCY_AMT="+singleCcyAm+"];";
                            }
                        }
                    }
                }
                else{
                    errMsg +="认购币种及金额格式不正确[SUBSCRIBE_CCY_AMT="+singleCcyAm+"];";
                }
                /*else if (ccyAmtDetailList.size() == 2){
                    subscribeCcyAmtFlag = true;

                    subscribeAmtList.add(ccyAmtDetailList.get(1));
                    subscribeCcyList.add(ccyAmtDetailList.get(0));
                }
                else if (ccyAmtDetailList.size() == 1){
                    subscribeAmtFlag = true;
                    subscribeCcyAmtFlag = true;

                    subscribeCcyList.add(ccyAmtDetailList.get(0));
                }
                else if (ccyAmtDetailList.size() == 0){
                    subscribeCcyFlag = true;
                    subscribeAmtFlag = true;
                    subscribeCcyAmtFlag = true;
                }*/
            }

            /*// 认购币种要素不可为空
            if (subscribeCcyFlag){
                errMsg +="认购币种必须在枚举范围内[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
            }
            // 认购金额要素不可为空。
            if(subscribeAmtFlag){
                errMsg +="认购金额要素不可为空[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
            }
            // 折算人民币金额要素不可为空。
            if (subscribeCcyAmtFlag){
                errMsg +="折算人民币金额要素不可为空[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
            }*/
            // 认购金额的币种的多选选项不可重复。
            HashSet<String> subscribeCcySet = new HashSet<String>(subscribeCcyList);
            if (subscribeCcySet.size() != subscribeCcyList.size()){
                errMsg +="认购金额的币种的多选选项不可重复[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
            }
            /*for (String ccy : subscribeCcyList){
                boolean flag = false;
                if (null != ccyDto) {
                    List<? extends DataDictItem> items = ccyDto.getItems();
                    for (DataDictItem item : items) {
                        if (ccy.equals(item.getItemCode())){
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag){
                    errMsg +="认购币种必须在枚举范围内[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
                    break;
                }
            }
            // 认购金额必须为n（15,2）格式。
            rule.setSrcproperty("SUBSCRIBEAMT");
            rule.setObjvalue("15,2");
            rule.setNullable("Y");
            for (String subscribeAmt : subscribeAmtList){
                data.put("SUBSCRIBEAMT", subscribeAmt);
                if (!BaseCheckLib.isNumber2(data, rule)){
                    errMsg +="认购金额必须为n（15,2）格式[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
                    break;
                }
            }
            // 折算人民币金额必须为n（15,2）格式。
            for (String subscribeccyAmt : subscribeCcyAmtList){
                data.put("SUBSCRIBEAMT", subscribeccyAmt);
                if (!BaseCheckLib.isNumber2(data, rule)){
                    errMsg +="折算人民币金额必须为n（15,2）格式[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
                    break;
                }
            }
            // 认购金额必须大于等于0。
            rule.setObjvalue("0");
            rule.setCompareto(">=");
            for (String subscribeAmt : subscribeAmtList){
                data.put("SUBSCRIBEAMT", subscribeAmt);
                if (!BaseCheckLib.compareValueAsNumber(data, rule)){
                    errMsg +="认购金额必须大于等于0[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
                    break;
                }
            }
            // 折算人民币金额必须大于等于0。
            for (String subscribeccyAmt : subscribeCcyAmtList){
                data.put("SUBSCRIBEAMT", subscribeccyAmt);
                if (!BaseCheckLib.compareValueAsNumber(data, rule)){
                    errMsg +="折算人民币金额必须大于等于0[SUBSCRIBE_CCY_AMT="+subscribeCcyAmt+"];";
                    break;
                }
            }*/
        }

        // 产品销售区域
        List<String> saleAreaList = new ArrayList<>();
        HashSet<String> saleAreaSet = null;
        // 370000 山东省,60000.00;370200 青岛市,60000.00
        if (StringUtil.isNotEmpty(saleAreaAmt)){
            List<String> singleSaleAreaAmtList = Arrays.asList(saleAreaAmt.split(GlobalConstant.SEMICOLON));// 370000 山东省,60000.00
            /*// 产品销售区域标志位
            boolean saleAreaFlag = false;
            // 区域募集金额标志位
            boolean areaAmtFlag = false;*/
            for (String singleAreaAmt : singleSaleAreaAmtList){
                List<String> ccyAmtDetailList = Arrays.asList(singleAreaAmt.split(GlobalConstant.COMMA));
                if (ccyAmtDetailList.size() == 2 && StringUtil.isNotEmpty(ccyAmtDetailList.get(0))&& StringUtil.isNotEmpty(ccyAmtDetailList.get(1))){
                    List<String> areaInfoList = Arrays.asList(ccyAmtDetailList.get(0).split(" "));// 370000 山东省

                    if (areaInfoList.size() == 2 && StringUtil.isNotEmpty(areaInfoList.get(0))&& StringUtil.isNotEmpty(areaInfoList.get(1))){
                        // 产品销售区域不在值域范围内。
                        boolean saleAreaDataFlag = false;
                        String areaDataValue = "";
                        if (null != saleAreaDto) {
                            List<? extends DataDictItem> items = saleAreaDto.getItems();
                            for (DataDictItem item : items) {
                                if (areaInfoList.get(0).equals(item.getItemCode())){
                                    saleAreaDataFlag = true;
                                    areaDataValue = item.getItemValue();
                                    break;
                                }
                            }
                        }
                        if (!saleAreaDataFlag){
                            errMsg +="产品销售区域不在值域范围内[SALE_AREA_AMT="+singleAreaAmt+"];";
                        }
                        else{
                            if (!StringUtil.equals(areaInfoList.get(1), areaDataValue)){
                                errMsg +="产品销售区域值域信息不匹配[SALE_AREA_AMT="+singleAreaAmt+"];";
                            }
                        }

                        // 区域募集金额必须为n（15,2）格式。
                        rule.setSrcproperty("AREAAMT");
                        rule.setObjvalue("15,2");
                        rule.setNullable("Y");
                        data.put("AREAAMT", ccyAmtDetailList.get(1));
                        if (!BaseCheckLib.isNumber2(data, rule)){
                            errMsg +="区域募集金额必须为n（15,2）格式[SALE_AREA_AMT="+singleAreaAmt+"];";
                        }
                        // 区域募集金额必须大于等于0。
                        rule.setObjvalue("0");
                        rule.setCompareto(">=");
                        rule.setNullable("N");
                        data.put("AREAAMT", ccyAmtDetailList.get(1));
                        if (!BaseCheckLib.compareValueAsNumber(data, rule)){
                            errMsg +="区域募集金额必须大于等于0[SALE_AREA_AMT="+singleAreaAmt+"];";
                        }

                        areaAmtList.add(ccyAmtDetailList.get(1));
                        saleAreaList.add(areaInfoList.get(0));
                    }
                    else{
                    	if(!"0".equals(singleAreaAmt)) {
                    		errMsg +="产品销售区域格式不正确[SALE_AREA_AMT="+singleAreaAmt+"];";
                    	}                        
                    }
                }
                else{
                	if(!"0".equals(singleAreaAmt)) {
                		errMsg +="产品销售区域格式不正确[SALE_AREA_AMT="+singleAreaAmt+"];";
                	}  
                }
                /*else if (ccyAmtDetailList.size() == 1){
                    areaAmtFlag = true;

                    saleAreaList.add(ccyAmtDetailList.get(0));
                }
                else if (ccyAmtDetailList.size() == 0){
                    saleAreaFlag = true;
                    areaAmtFlag = true;
                }*/
            }

            /*// 产品销售区域要素不可为空。
            if (saleAreaFlag){
                errMsg +="产品销售区域要素不可为空[SALE_AREA_AMT="+saleAreaAmt+"];";
            }
            // 区域募集金额要素不可为空。
            if(areaAmtFlag){
                errMsg +="区域募集金额要素不可为空[SALE_AREA_AMT="+saleAreaAmt+"];";
            }*/
            // 产品销售区域的多选选项不可重复。
            saleAreaSet = new HashSet<String>(saleAreaList);
            if (saleAreaSet.size() != saleAreaList.size()){
                errMsg +="产品销售区域的多选选项不可重复[SALE_AREA_AMT="+saleAreaAmt+"];";
            }
            /*// 产品销售区域不在值域范围内。
            for (String saleArea : saleAreaList){
                boolean flag = false;
                if (null != saleAreaDto) {
                    List<? extends DataDictItem> items = saleAreaDto.getItems();
                    for (DataDictItem item : items) {
                        if (saleArea.equals(item.getItemCode())){
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag){
                    errMsg +="产品销售区域不在值域范围内[SALE_AREA_AMT="+saleAreaAmt+"];";
                    break;
                }
            }

            // 区域募集金额必须为n（15,2）格式。
            rule.setSrcproperty("AREAAMT");
            rule.setObjvalue("15,2");
            rule.setNullable("Y");
            for (String areaAmt : areaAmtList){
                data.put("AREAAMT", areaAmt);
                if (!BaseCheckLib.isNumber2(data, rule)){
                    errMsg +="区域募集金额必须为n（15,2）格式[SALE_AREA_AMT="+saleAreaAmt+"];";
                    break;
                }
            }
            // 区域募集金额必须大于等于0。
            rule.setObjvalue("0");
            rule.setCompareto(">=");
            for (String areaAmt : areaAmtList){
                data.put("AREAAMT", areaAmt);
                if (!BaseCheckLib.compareValueAsNumber(data, rule)){
                    errMsg +="区域募集金额必须大于等于0[SALE_AREA_AMT="+saleAreaAmt+"];";
                    break;
                }
            }*/
        }

        // 实际募集金额(元)≠∑各区域募集金额（元）≠∑各币种折算人民币金额（元），请检查这三个元素的值，重新填写。
        // 实际募集金额(元)
        BigDecimal actTotalAmt = BigDecimal.ZERO;
        if (checkValIsNum(totalAmt)){
            actTotalAmt = new BigDecimal(totalAmt);
        }
        // ∑各区域募集金额（元）
        for (String areaAmt : areaAmtList){
            if (checkValIsNum(areaAmt)){
                totleAreaAmt = ArithmeticUtil.doAdd(totleAreaAmt, new BigDecimal(areaAmt));
            }
        }
        // ∑各币种折算人民币金额（元）
        for (String subscribeccyAmt : subscribeCcyAmtList){
            if (checkValIsNum(subscribeccyAmt)){
                totleCcyAmt = ArithmeticUtil.doAdd(totleCcyAmt, new BigDecimal(subscribeccyAmt));
            }
        }
        if (actTotalAmt.compareTo(totleAreaAmt) != 0 || totleAreaAmt.compareTo(totleCcyAmt) !=0){
            errMsg +="实际募集金额(元)≠∑各区域募集金额（元）≠∑各币种折算人民币金额（元），请检查这三个元素的值，重新填写。";
        }


        // delete by zhangliang 20201106 逻辑移到报送前处理 start
        // 产品销售区域超出产品申报登记选择的区域，备注必填。
        /*if (saleAreaSet != null && StringUtil.isNotEmpty(prodId)){
            Map<String, Object> map = new HashMap<>();
            map.put("prodId", prodId);
            List<ChbPrdDeclareSub> chbPrdDeclareSubList = comminCodeCheckMapper.queryChbPrdDeclareSubByRegCode(map);
            if(!StringUtil.isEmptyList(chbPrdDeclareSubList) && StringUtil.isNotEmpty(chbPrdDeclareSubList.get(0).getSaleArea())){
                List<String> declareAreaList = Arrays.asList(chbPrdDeclareSubList.get(0).getSaleArea().split(GlobalConstant.COMMA));
                boolean flag = false;
                for (String area : saleAreaSet){
                    if (!declareAreaList.contains(area)){
                        flag = true;
                        break;
                    }
                }
                if (flag && StringUtil.isEmpty(remark)){
                    errMsg +="产品销售区域超出产品申报登记选择的区域，备注必填[REMARK="+remark+"];";
                }
            }
        }*/
        // delete by zhangliang 20201106 逻辑移到报送前处理 end
        // 该要素数值小于等于实际募集金额
        if (checkValIsNum(consignAmt) && checkValIsNum(totalAmt)){
            if (new BigDecimal(consignAmt).compareTo(new BigDecimal(totalAmt)) > 0){
                errMsg +="代销总金额必须小于等于实际募集金额[CONSIGN_AMT="+consignAmt+"];";
            }
        }

        dataMap.put("ERRMSG", errMsg);
    }

    /**
     * 代码检查产品申报登记
     * @param dataMap
     */
    private  void  checkPrdoIssue(Map<String, String> dataMap,Set<String> prodRegistCodeList,Set<String> prodIdCodeList) {
        String  errMsg=dataMap.getOrDefault("ERRMSG", "");
        // 发行机构代码
        String issuePartyId=dataMap.get("ISSUE_PARTY_ID");
        // 产品登记编码
        String prodRegistCode = dataMap.get("PROD_REGIST_CODE");
        // 理财产品代码
        String prodId = dataMap.get("PROD_ID");
        // 募集起始日期
        String raiseVdate = dataMap.get("RAISE_VDATE");
        //分级比例
        String  graduation=dataMap.get("GRAD_PROPORTION");

        // 产品登记编码报文内不可重复。
        if (StringUtil.isNotEmpty(prodRegistCode) && prodRegistCodeList.contains(prodRegistCode)){
            errMsg +="产品登记编码报文内不可重复[PROD_REGIST_CODE="+prodRegistCode+"];";
        }
        else{
            prodRegistCodeList.add(prodRegistCode);
        }
        //产品代码报文内不可重复
        if (StringUtil.isNotEmpty(prodId) && prodIdCodeList.contains(prodId)){
            errMsg +="产品代码报文内不可重复[PROD_ID="+prodId+"];";
        }
        else{
            prodIdCodeList.add(prodId);
        }
//      // 该产品不存在。
//      if (StringUtil.isNotEmpty(prodRegistCode)){
//          List<ChbPrdresistCode> chbPrdresistCodeList = comminCodeCheckMapper.queryChbPrdresistCodeByRegCode(prodRegistCode);
//          if (StringUtil.isEmptyList(chbPrdresistCodeList)){
//              errMsg +="该产品不存在[PROD_REGIST_CODE="+prodRegistCode+"];";
//          }
//      }
        // 银行模式才判断发行机构代码，联社模式不判断
        if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
        // 发行机构代码必须与银行代码相同。
        if(!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)){
            errMsg +="发行机构代码必须与银行代码相同[ISSUE_PARTY_ID="+issuePartyId+"];";
        }
        }
        // 募集起始日期应落在申报登记要素中募集起始日期（从）/（到）的区间内.
        List<ChbPrdDeclareSub> chbPrdDeclareSubList = new ArrayList<>();
        if (StringUtil.isNotEmpty(prodId)){
            Map<String, Object> map = new HashMap<>();
            map.put("prodId", prodId);
            chbPrdDeclareSubList = commonCodeCheckMapper.queryChbPrdDeclareSubByRegCode(map);
            if (!StringUtil.isEmptyList(chbPrdDeclareSubList)){
                if ((StringUtil.isNotEmpty(chbPrdDeclareSubList.get(0).getRaisePstrdate()) &&
                		DateUtil.isDateStr(raiseVdate,"yyyy-MM-dd")&&DateUtil.parse(raiseVdate).compareTo(chbPrdDeclareSubList.get(0).getRaisePstrdate()) < 0)
                        || (StringUtil.isNotEmpty(chbPrdDeclareSubList.get(0).getRaisePenddate()) &&
                        		DateUtil.isDateStr(raiseVdate,"yyyy-MM-dd")&&DateUtil.parse(raiseVdate).compareTo(chbPrdDeclareSubList.get(0).getRaisePenddate()) > 0)){
                    errMsg +="募集起始日期应落在申报登记要素中募集起始日期（从）/（到）的区间内[RAISE_VDATE="+raiseVdate+"];";
                }

                //产品投资性质选择01，并且分级比例不为空时，不得填写小于等于0或大于3的数字
                if(StringUtil.equals("01",chbPrdDeclareSubList.get(0).getIvstorientation())&&
                        StringUtil.isNotEmpty(graduation)){
                    if(new BigDecimal(graduation).compareTo(BigDecimal.ZERO)<=0||
                            new BigDecimal(graduation).compareTo(new BigDecimal(3))>0){
                        errMsg +="产品投资性质选择01 固定收益类时，分级比例不得填写小于等于0或大于3的数字[GRAD_PROPORTION="+graduation+"];";
                    }
                }
                //产品投资性质选择02，并且分级比例不为空时，不得填写小于等于0或大于等于1的数字
                if(StringUtil.equals("02",chbPrdDeclareSubList.get(0).getIvstorientation())&&
                        StringUtil.isNotEmpty(graduation)){
                    if(new BigDecimal(graduation).compareTo(BigDecimal.ZERO)<=0||
                            new BigDecimal(graduation).compareTo(new BigDecimal(1))>0){
                        errMsg +="产品投资性质选择02 收益类时，分级比例不得填写小于等于0或大于1的数字[GRAD_PROPORTION="+graduation+"];";
                    }
                }

                //产品投资性质选择03，04,时候并且分级比例不为空时，不得填写小于等于0或大于等于1的数字
                if((StringUtil.equals("03",chbPrdDeclareSubList.get(0).getIvstorientation())||
                        StringUtil.equals("04",chbPrdDeclareSubList.get(0).getIvstorientation())) &&
                        StringUtil.isNotEmpty(graduation)){
                    if(new BigDecimal(graduation).compareTo(BigDecimal.ZERO)<=0||
                            new BigDecimal(graduation).compareTo(new BigDecimal(2))>0){
                        errMsg +="产品投资性质选择03商品及金融衍生品类、04混合类时，分级比例不得填写小于等于0或大于2的数字[GRAD_PROPORTION="+graduation+"];";
                    }
                }

            }
        }
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,开放模式不能有值；
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,规律开放周期不能有值；
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,其他规律开放周期（天）不能有值！
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,无规律开放说明不能有值。
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,首次开放周期起始日不能有值；
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,节假日是否开放不能有值；
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,平均开放次数（年化）不能有值；
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,开放期业务不能有值；
        // 产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,开放期业务说明不能有值；
        // 产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,开放模式不能为空。
        // 产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,首次开放周期起始日不能为空。
        // 产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,节假日是否开放不能为空。
        // 产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,平均开放次数（年化）不能为空。
        // 产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,开放期业务不能为空。
        if (!StringUtil.isEmptyList(chbPrdDeclareSubList)){
            String operateMode = chbPrdDeclareSubList.get(0).getOperateMode();
            if (StringUtil.equals(operateMode, DictCodeListExt2.产品运作模式_封闭式净值型.getValue())
                    || StringUtil.equals(operateMode, DictCodeListExt2.产品运作模式_封闭式非净值型.getValue())){
                // 开放模式
                if (StringUtil.isNotEmpty(dataMap.get("OPEN_MODE"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,开放模式不能有值[OPEN_MODE="+dataMap.get("OPEN_MODE")+"];";
                }
                // 规律开放周期
                if (StringUtil.isNotEmpty(dataMap.get("OPEN_CIRCLE"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,规律开放周期不能有值[OPEN_CIRCLE="+dataMap.get("OPEN_CIRCLE")+"];";
                }
                // 其他规律开放周期（天）
                if (StringUtil.isNotEmpty(dataMap.get("OPEN_CIRCLE_DAYS"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,其他规律开放周期（天）不能有值[OPEN_CIRCLE_DAYS="+dataMap.get("OPEN_CIRCLE_DAYS")+"];";
                }
                // 无规律开放说明
                if (StringUtil.isNotEmpty(dataMap.get("RULE_COMMENT"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,无规律开放说明不能有值[RULE_COMMENT="+dataMap.get("RULE_COMMENT")+"];";
                }
                // 首次开放周期起始日
                if (StringUtil.isNotEmpty(dataMap.get("FIRST_OPEN_DAY"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,首次开放周期起始日不能有值[FIRST_OPEN_DAY="+dataMap.get("FIRST_OPEN_DAY")+"];";
                }
                // 节假日是否开放
                if (StringUtil.isNotEmpty(dataMap.get("HOLIDAY_ON_FLAG"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,节假日是否开放不能有值[HOLIDAY_ON_FLAG="+dataMap.get("HOLIDAY_ON_FLAG")+"];";
                }
                // 平均开放次数（年化）
                if (StringUtil.isNotEmpty(dataMap.get("AVG_OPEN_COUNT"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,平均开放次数（年化）不能有值[AVG_OPEN_COUNT="+dataMap.get("AVG_OPEN_COUNT")+"];";
                }
                // 开放期业务
                if (StringUtil.isNotEmpty(dataMap.get("OPEN_BUSS"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,开放期业务不能有值[OPEN_BUSS="+dataMap.get("OPEN_BUSS")+"];";
                }
                // 开放期业务说明
                if (StringUtil.isNotEmpty(dataMap.get("OPEN_BUSS_REMARK"))){
                    errMsg +="产品运作模式选择“封闭式净值型”和“封闭式非净值型”的产品,开放期业务说明不能有值[OPEN_BUSS_REMARK="+dataMap.get("OPEN_BUSS_REMARK")+"];";
                }
            }
            if (StringUtil.equals(operateMode, DictCodeListExt2.产品运作模式_开放式净值型.getValue())
                    || StringUtil.equals(operateMode, DictCodeListExt2.产品运作模式_开放式非净值型.getValue())){
                // 开放模式
                if (StringUtil.isEmpty(dataMap.get("OPEN_MODE"))){
                    errMsg +="产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,开放模式不能为空[OPEN_MODE="+dataMap.get("OPEN_MODE")+"];";
                }
                // 首次开放周期起始日
                if (StringUtil.isEmpty(dataMap.get("FIRST_OPEN_DAY"))){
                    errMsg +="产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,首次开放周期起始日不能为空[FIRST_OPEN_DAY="+dataMap.get("FIRST_OPEN_DAY")+"];";
                }
                // 节假日是否开放
                if (StringUtil.isEmpty(dataMap.get("HOLIDAY_ON_FLAG"))){
                    errMsg +="产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,节假日是否开放不能为空[HOLIDAY_ON_FLAG="+dataMap.get("HOLIDAY_ON_FLAG")+"];";
                }
                // 平均开放次数（年化）
                if (StringUtil.isEmpty(dataMap.get("AVG_OPEN_COUNT"))){
                    errMsg +="产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,平均开放次数（年化）不能为空[AVG_OPEN_COUNT="+dataMap.get("AVG_OPEN_COUNT")+"];";
                }
                // 开放期业务
                if (StringUtil.isEmpty(dataMap.get("OPEN_BUSS"))){
                    errMsg +="产品运作模式选择“开放式净值型”和“开放式非净值型”的产品,开放期业务不能为空[OPEN_BUSS="+dataMap.get("OPEN_BUSS")+"];";
                }
            }
        }


        dataMap.put("ERRMSG", errMsg);
    }

    /**
     * 代码检查产品申报登记
     * @param dataMap
     */
    public void checkPrdoDeclare(Map<String, String> dataMap,Map<String, String> listMap, ChbCheckTableInfo tableInfo){
        String  errMsg=dataMap.getOrDefault("ERRMSG", "");
        //行内标识码
        String   prodInsId=dataMap.get("PROD_INS_ID");
        // 产品名称
        String prodName = dataMap.get("PROD_NAME");
        // 产品期次
        String prodStage = dataMap.get("PROD_STAGE");
        //发行机构代码
        String   issuePartyId=dataMap.get("ISSUE_PARTY_ID");
        //投资资产种类及比例要素
        String   invTypeRat=dataMap.get("INV_TYPE_RAT");
        if(StringUtil.isNotEmpty(issuePartyId)){
        //发行机构代码必须与银行代码相同。
        // 银行模式才判断发行机构代码，联社模式不判断
        if(!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)
                && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
            errMsg +="发行机构代码必须与银行代码相同[ISSUE_PARTY_ID="+issuePartyId+"];";
        }
        }

        // modify by zhangliang 20210419 start
        if (StringUtil.isNotEmpty(prodInsId) && StringUtil.isNotEmpty(prodName) && StringUtil.isNotEmpty(prodStage)){
            List<String> countList = new ArrayList<>();
            if (tableInfo == ChbCheckTableInfo.个人产品申报登记TMP || tableInfo == ChbCheckTableInfo.个人产品申报登记INFO){
                countList.addAll(commonCodeCheckMapper.genChbPrdDeclareSub1ByProdInsId(tableInfo.getTableName(), prodInsId, prodName, prodStage));
            }
            if (tableInfo == ChbCheckTableInfo.个人产品申报登记INFO || tableInfo == ChbCheckTableInfo.个人产品申报登记SUB){
                countList.addAll(commonCodeCheckMapper.genChbPrdDeclareSub1ByProdInsId("CHB_PRD_BASEINFO", prodInsId, prodName, prodStage));
            }
            countList.addAll(commonCodeCheckMapper.genChbPrdDeclareSub2ByProdInsId(prodInsId, prodName, prodStage));

            // list  可能是tmp->sub也可能是业务数据维护
            
            
            
            String  code = listMap.get(prodName+"_"+prodStage);
            if(null!=code||countList.size() > 0){
                errMsg +="产品名称和产品期次不能同时重复[PROD_NAME="+dataMap.get("PROD_NAME")+",PROD_STAGE="+dataMap.get("PROD_STAGE")+"];";
            }else{
            	listMap.put(prodName+"_"+prodStage, "1");
            }
            
        }
        //投资资产种类及比例要素的判断
        boolean  flag=true;
        String  regex="^(100|[1-9]?\\d(\\.\\d\\d?)?)%$|0$";
        if(StringUtil.isNotEmpty(invTypeRat)){
            //对于投资资产种类及比例要素按照";"切割成各个单元
            List<String>  invTaypeRatList=Arrays.asList(invTypeRat.split(GlobalConstant.SEMICOLON));
            //循环list，对于单元进行处理
            for(String invTaype:invTaypeRatList){
                //按照":"对于单元进行切割
                List<String>  invTaypePatten=Arrays.asList(invTaype.split(GlobalConstant.COLON));
                //单元切割后，不是2个部分。则格式不通过
                if(invTaypePatten!=null&&invTaypePatten.size()!=2){
                    flag=false;
                    break;
                }else{
                    //单元切割后是2个部分，获取数值和文字
                    //数值
                    String  pattenOne=invTaypePatten.get(0);
                    if(pattenOne.contains(GlobalConstant.SPLIT)){
                        //表示为区间
                        List<String>  pattenOneSection=Arrays.asList(pattenOne.split(GlobalConstant.SPLIT));
                        //校验区间
                        if(!pattenOneSection.get(0).matches(regex)||
                                !pattenOneSection.get(1).matches(regex)){
                            flag=false;
                            break;
                        }
                    }else{
                        //不是区间，单个数字百分号
                        if(!pattenOne.matches(regex)){
                            flag=false;
                            break;
                        }
                    }
                    //文字
                    //String  pattenTwo=invTaypePatten.get(1);
                }
            }
        }
        if(!flag){
            errMsg +="投资资产种类及比例要素格式不对。正确的格式：数字%：文字；数字%-数字%：文字（其中，百分号、冒号、分号、连字号均应为英文标点，文字和数字部分均不得含有英文百分号、冒号、分号、连字号）。其中，数字格式为n..（5，2），且需大于等于0、小于等于100。[INV_TYPE_RAT="+dataMap.get("INV_TYPE_RAT")+"];";
        }
        
        dataMap.put("ERRMSG", errMsg);
    }

    /**
     * 代码检查产品申报登记
     * @param dataMap
     */
    public void checkPrdoDeclare(List<ExcelValidDto> excelValidDtoList,Map<String, String> dataMap,Map<String, String> listMap, ChbCheckTableInfo tableInfo,int i){
        String  errMsg="";
        //行内标识码
        String   prodInsId=dataMap.get("PROD_INS_ID");
        // 产品名称
        String prodName = dataMap.get("PROD_NAME");
        // 产品期次
        String prodStage = dataMap.get("PROD_STAGE");
        //发行机构代码
        String   issuePartyId=dataMap.get("ISSUE_PARTY_ID");
        //投资资产种类及比例要素
        String   invTypeRat=dataMap.get("INV_TYPE_RAT");
        String  temp=dataMap.getOrDefault("ERRMSG", "");

        if(temp.equals("重复数据;")){

        }else {
            if (StringUtil.isNotEmpty(issuePartyId)) {
                //发行机构代码必须与银行代码相同。
                // 银行模式才判断发行机构代码，联社模式不判断
                if (!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)
                        && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")) {
                    errMsg += "发行机构代码必须与银行代码相同[ISSUE_PARTY_ID=" + issuePartyId + "];";
                }
            }

            // modify by zhangliang 20210419 start
            if (StringUtil.isNotEmpty(prodInsId) && StringUtil.isNotEmpty(prodName) && StringUtil.isNotEmpty(prodStage)) {
                List<String> countList = new ArrayList<>();
                if (tableInfo == ChbCheckTableInfo.个人产品申报登记TMP || tableInfo == ChbCheckTableInfo.个人产品申报登记INFO) {
                    countList.addAll(commonCodeCheckMapper.genChbPrdDeclareSub1ByProdInsId(tableInfo.getTableName(), prodInsId, prodName, prodStage));
                }
                if (tableInfo == ChbCheckTableInfo.个人产品申报登记INFO || tableInfo == ChbCheckTableInfo.个人产品申报登记SUB) {
                    countList.addAll(commonCodeCheckMapper.genChbPrdDeclareSub1ByProdInsId("CHB_PRD_BASEINFO", prodInsId, prodName, prodStage));
                }
                countList.addAll(commonCodeCheckMapper.genChbPrdDeclareSub2ByProdInsId(prodInsId, prodName, prodStage));

                // list  可能是tmp->sub也可能是业务数据维护


                String code = listMap.get(prodName + "_" + prodStage);
                if (null != code || countList.size() > 0) {
                    errMsg += "产品名称和产品期次不能同时重复[PROD_NAME=" + dataMap.get("PROD_NAME") + ",PROD_STAGE=" + dataMap.get("PROD_STAGE") + "];";
                } else {
                    listMap.put(prodName + "_" + prodStage, "1");
                }

            }
            //投资资产种类及比例要素的判断
            boolean flag = true;
            String regex = "^(100|[1-9]?\\d(\\.\\d\\d?)?)%$|0$";
            if (StringUtil.isNotEmpty(invTypeRat)) {
                //对于投资资产种类及比例要素按照";"切割成各个单元
                List<String> invTaypeRatList = Arrays.asList(invTypeRat.split(GlobalConstant.SEMICOLON));
                //循环list，对于单元进行处理
                for (String invTaype : invTaypeRatList) {
                    //按照":"对于单元进行切割
                    List<String> invTaypePatten = Arrays.asList(invTaype.split(GlobalConstant.COLON));
                    //单元切割后，不是2个部分。则格式不通过
                    if (invTaypePatten != null && invTaypePatten.size() != 2) {
                        flag = false;
                        break;
                    } else {
                        //单元切割后是2个部分，获取数值和文字
                        //数值
                        String pattenOne = invTaypePatten.get(0);
                        if (pattenOne.contains(GlobalConstant.SPLIT)) {
                            //表示为区间
                            List<String> pattenOneSection = Arrays.asList(pattenOne.split(GlobalConstant.SPLIT));
                            //校验区间
                            if (!pattenOneSection.get(0).matches(regex) ||
                                    !pattenOneSection.get(1).matches(regex)) {
                                flag = false;
                                break;
                            }
                        } else {
                            //不是区间，单个数字百分号
                            if (!pattenOne.matches(regex)) {
                                flag = false;
                                break;
                            }
                        }
                        //文字
                        //String  pattenTwo=invTaypePatten.get(1);
                    }
                }
            }
            if (!flag) {
                errMsg += "投资资产种类及比例要素格式不对。正确的格式：数字%：文字；数字%-数字%：文字（其中，百分号、冒号、分号、连字号均应为英文标点，文字和数字部分均不得含有英文百分号、冒号、分号、连字号）。其中，数字格式为n..（5，2），且需大于等于0、小于等于100。[INV_TYPE_RAT=" + dataMap.get("INV_TYPE_RAT") + "];";
            }
            if (StringUtils.isEmpty(temp)) {
                if (StringUtils.isNotEmpty(errMsg) && excelValidDtoList != null) {
                    excelValidDtoList.add(new ExcelValidDto("导入成功", String.valueOf((i + 2)),errMsg));
                }
            } else {
                if (StringUtils.isNotEmpty(errMsg) && excelValidDtoList != null) {
                    if (excelValidDtoList.size() > 0) {
                        for (ExcelValidDto excelValidDto : excelValidDtoList) {
                            if (excelValidDto.getRowNum().equals(String.valueOf((i + 2)))) {
                                excelValidDto.setErrorInfo(temp + errMsg);
                                break;
                            }
                        }
                    }
                }
            }
            dataMap.put("ERRMSG", temp+errMsg);
        }
        if (CollectionUtils.isNotEmpty(excelValidDtoList)) {
            this.excelValidListSort(excelValidDtoList);
        }

    }

    /**
     * o1在前：根据age从小到大进行排序
     * @param excelValidDtoList
     */
    private void excelValidListSort(List<ExcelValidDto> excelValidDtoList){
        Collections.sort(excelValidDtoList,new Comparator<ExcelValidDto>() {
            @Override
            public int compare(ExcelValidDto o1, ExcelValidDto o2) {

                return Integer.valueOf(o1.getRowNum())-Integer.valueOf(o2.getRowNum());
            }
        });
    }

    // add by dubaojun 2020-10-12 配置无法实现，代码实现的check start
    /**
     * 代码检查产品交易登记
     *
     * @param dataMap 被检查的数据
     * @param prodRegistCodeMap 产品登记编码需要用的数据
     */
    public void checkInvestTradeInfo(Map<String, String> dataMap, Map<String, List<String>> prodRegistCodeMap) {
        StringBuilder errMsgBuilder = new StringBuilder(dataMap.getOrDefault("ERRMSG", ""));
        // 发行机构代码
        String registBankCode = dataMap.get("REGIST_BANK_CODE");
        // 产品登记编码
//        String prodRegistCode = dataMap.get("PROD_REGIST_CODE");
//        // 资金流动类型
//        String curAssetType = dataMap.get("CUR_ASSET_TYPE");
//        // 行内交易编码
//        String tradeCode = dataMap.get("TRADE_CODE");
//        // 行内资产/负债编码
//        String assetCode = dataMap.get("ASSET_CODE");
        // 1、发行机构代码必须与银行代码相同。
        // 银行模式才判断发行机构代码，联社模式不判断
        if(StringUtil.isNotEmpty(registBankCode) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
        if (!StringUtil.equals(CommonUtil.selectBankCode(), registBankCode)) {
            errMsgBuilder.append("发行机构代码必须与银行代码相同[").append("REGIST_BANK_CODE=").append(registBankCode).append("];");
        }
        }
//        if (StringUtil.isNotEmpty(prodRegistCode)) {
//          // 2、产品登记编码是否匹配数据库中已有的记录
//          List<ChbPrdresistCode> chbPrdresistCodeList = comminCodeCheckMapper.queryChbPrdresistCodeByRegCode(prodRegistCode);
//          if (StringUtil.isEmptyList(chbPrdresistCodeList)) {
//              errMsgBuilder.append("产品不存在[").append("PROD_REGIST_CODE=").append(prodRegistCode).append("];");
//          }
            // 3、对于同一只理财产品（产品登记编码相同），当“资金流动类型”选择01-10选项的情况，“行内交易编码”与“行内资产/负债编码”和“资金流动类型”这3个要素不允许同时重复
            // 获取产品对应的数据
//            List<String> list = prodRegistCodeMap.get(prodRegistCode);
//            if(list == null) {
//                list = new ArrayList<>();
//                prodRegistCodeMap.put(prodRegistCode, list);
//            }
//            if("01".equals(curAssetType) || "02".equals(curAssetType) || "03".equals(curAssetType) || "04".equals(curAssetType)
//                    || "05".equals(curAssetType) || "06".equals(curAssetType) || "07".equals(curAssetType)
//                    || "08".equals(curAssetType) || "09".equals(curAssetType) || "10".equals(curAssetType)) {
//                String key = tradeCode + "_" + assetCode + "_" + curAssetType;
//                if(list.contains(key)) {
//                    errMsgBuilder.append("同一只理财产品，当“资金流动类型“选择01-10时，“行内交易编码”与“行内资产/负债编码”和“资金流动类型”在报文内不可同时重复[")
//                            .append("PROD_REGIST_CODE=").append(prodRegistCode).append(",CUR_ASSET_TYPE=").append(curAssetType)
//                            .append(",TRADE_CODE=").append(tradeCode).append(",ASSET_CODE=").append(assetCode).append("];");
//                }
//                else {
//                    list.add(key);
//                }
//            }
//            // 4、对于同一只理财产品（产品登记编码相同），当“资金流动类型”选择11-12选项，“行内交易编码”和“资金流动类型”这2个要素不允许同时重复
//            else if("11".equals(curAssetType) || "12".equals(curAssetType)) {
//                String key = assetCode + "_" + curAssetType;
//                if(list.contains(key)) {
//                    errMsgBuilder.append("同一只理财产品，当“资金流动类型”选择11-12时，“行内交易编码”和“资金流动类型”在报文内不可同时重复[")
//                            .append("PROD_REGIST_CODE=").append(prodRegistCode).append(",CUR_ASSET_TYPE=").append(curAssetType)
//                            .append(",TRADE_CODE=").append(tradeCode).append("];");
//                }
//                else {
//                    list.add(key);
//                }
//            }
//          // 5、对于同一只理财产品，当“资金流动类型”选择01-10选项的情况，该要素与“行内资产/负债编码”和“资金流动类型”这3个要素不允许同时与登记信息重复
//          Map<String, String> paraMap = new HashMap<>();
//          paraMap.put("prodId", dataMap.get("PROD_ID"));
//          paraMap.put("curAssetType", curAssetType);
//          paraMap.put("tradeCode", tradeCode);
//          List<String> assetCodeList = comminCodeCheckMapper.selectChbInvestTradeSubByBussKey(paraMap);
//          if("01".equals(curAssetType) || "02".equals(curAssetType) || "03".equals(curAssetType) || "04".equals(curAssetType)
//                  || "05".equals(curAssetType) || "06".equals(curAssetType) || "07".equals(curAssetType)
//                  || "08".equals(curAssetType) || "09".equals(curAssetType) || "10".equals(curAssetType)) {
//              if(assetCodeList.contains(assetCode)) {
//                  errMsgBuilder.append("理财交易记录已存在[")
//                          .append("PROD_REGIST_CODE=").append(prodRegistCode).append(",CUR_ASSET_TYPE=").append(curAssetType)
//                          .append(",TRADE_CODE=").append(tradeCode).append(",ASSET_CODE=").append(assetCode).append("];");
//              }
//          }
//          // 6、对于同一只理财产品，当“资金流动类型”选择11-12选项，该要素和“资金流动类型”这2个要素不允许同时与登记信息重复
//          else if("11".equals(curAssetType) || "12".equals(curAssetType)) {
//              if(!assetCodeList.isEmpty()) {
//                  errMsgBuilder.append("理财交易记录已存在[")
//                          .append("PROD_REGIST_CODE=").append(prodRegistCode).append(",CUR_ASSET_TYPE=").append(curAssetType)
//                          .append(",TRADE_CODE=").append(tradeCode).append("];");
//              }
//          }
//        }
//      // 7、行内资产/负债编码是否匹配数据库中已有的记录
//      if(StringUtil.isNotEmpty(assetCode)) {
//          int count = comminCodeCheckMapper.countChbAssetInfoSubByAssetCode(assetCode);
//          if(count == 0) {
//              errMsgBuilder.append("未进行资产要素登记[").append("ASSET_CODE=").append(assetCode).append("];");
//          }
//      }
        dataMap.put("ERRMSG", errMsgBuilder.toString());
    }

    /**
     * 代码检查资产估值登记
     *
     * @param dataMap 被检查的数据
     * @param list 所有被检查的数据
     */
    public void checkAstValInfo(Map<String, String> dataMap, List<Map<String, String>> list) {
        StringBuilder errMsgBuilder = new StringBuilder(dataMap.getOrDefault("ERRMSG", ""));
        // 发行机构代码
        String issuePartyId = dataMap.get("ISSUE_PARTY_ID");
        // 1、发行机构代码必须与银行代码相同。
        // 银行模式才判断发行机构代码，联社模式不判断
        if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
        if (!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)) {
            errMsgBuilder.append("发行机构代码必须与银行代码相同[").append("ISSUE_PARTY_ID=").append(issuePartyId).append("];");
        }}
        dataMap.put("ERRMSG", errMsgBuilder.toString());
    }
    // add by dubaojun 2020-10-12 配置无法实现，代码实现的check end

    /**
     * 代码检查资产估值登记
     *
     * @param dataMap 被检查的数据
     * @param
     */
    public void checkAssetSubInfo(Map<String, String> dataMap) {
        StringBuilder errMsgBuilder = new StringBuilder(dataMap.getOrDefault("ERRMSG", ""));
        // 发行机构代码
        String issuePartyId = dataMap.get("ISSUE_PARTY_ID");
        //固定收益部分所占比例
        String   incomeRatio= dataMap.get("INCOME_RATIO");
        //衍生金融工具所占比例
        String   derInstrRatio= dataMap.get("DER_INSTR_RATIO");
        //二级资产类型
        String   assetType2=dataMap.get("ASSET_TYPE2");

        // 1、发行机构代码必须与银行代码相同。
        // 银行模式才判断发行机构代码，联社模式不判断
        if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
        if (!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)) {
            errMsgBuilder.append("发行机构代码必须与银行代码相同[").append("ISSUE_PARTY_ID=").append(issuePartyId).append("];");
        }
        }
        if(StringUtil.equals(assetType2, "1503")){
        //“固定收益部分所占比例”与“衍生金融工具所占比例”之和需小于等于1
        if(StringUtil.isNotEmpty(incomeRatio)&&isBigDecimal(incomeRatio)&&
                StringUtil.isNotEmpty(derInstrRatio)&&isBigDecimal(derInstrRatio)){
            if(new BigDecimal(incomeRatio).add(new BigDecimal(derInstrRatio)).compareTo(new BigDecimal(100))>0){
                errMsgBuilder.append("以百分数形式表示的固定收益部分所占比例与衍生金融工具所占比例之和需小于等于100[").append("INCOME_RATIO=").append(incomeRatio).append(",DER_INSTR_RATIO=").append(derInstrRatio).append("];");
            }
        }
        }

        //投资资产种类及比例要素的判断
        //投资资产种类及比例要素
        String   invTypeRat=dataMap.get("FUND_INVEST_ASSET");
        boolean  flag=true;
        String  regex="^(100|[1-9]?\\d(\\.\\d\\d?)?)%$|0$";
        if(StringUtil.isNotEmpty(invTypeRat)){
            //对于投资资产种类及比例要素按照";"切割成各个单元
            List<String>  invTaypeRatList=Arrays.asList(invTypeRat.split(GlobalConstant.SEMICOLON));
            //循环list，对于单元进行处理
            for(String invTaype:invTaypeRatList){
                //按照":"对于单元进行切割
                List<String>  invTaypePatten=Arrays.asList(invTaype.split(GlobalConstant.COLON));
                //单元切割后，不是2个部分。则格式不通过
                if(invTaypePatten!=null&&invTaypePatten.size()!=2){
                    flag=false;
                    break;
                }else{
                    //单元切割后是2个部分，获取数值和文字
                    //数值
                    String  pattenOne=invTaypePatten.get(0);
                    if(pattenOne.contains(GlobalConstant.SPLIT)){
                        //表示为区间
                        List<String>  pattenOneSection=Arrays.asList(pattenOne.split(GlobalConstant.SPLIT));
                        //校验区间
                        if(!pattenOneSection.get(0).matches(regex)||
                                !pattenOneSection.get(1).matches(regex)){
                            flag=false;
                            break;
                        }
                    }else{
                        //不是区间，单个数字百分号
                        if(!pattenOne.matches(regex)){
                            flag=false;
                            break;
                        }
                    }
                    //文字
                    //String  pattenTwo=invTaypePatten.get(1);
                }
            }
        }
        if(!flag){
            errMsgBuilder.append("基金投资资产要素格式不对。正确的格式：数字%：文字；数字%-数字%：文字（其中，百分号、冒号、分号、连字号均应为英文标点，文字和数字部分均不得含有英文百分号、冒号、分号、连字号）。其中，数字格式为n..（5，2），且需大于等于0、小于等于100。[FUND_INVEST_ASSET="+dataMap.get("FUND_INVEST_ASSET")+"];");
        }

        dataMap.put("ERRMSG", errMsgBuilder.toString());
    }
    public void checkAssetSubInfo(List<ExcelValidDto> excelValidDtoList,Map<String, String> dataMap,int i) {
        String  temp=dataMap.getOrDefault("ERRMSG", "");
        StringBuilder errMsgBuilder = new StringBuilder();
        // 发行机构代码
        String issuePartyId = dataMap.get("ISSUE_PARTY_ID");
        //固定收益部分所占比例
        String   incomeRatio= dataMap.get("INCOME_RATIO");
        //衍生金融工具所占比例
        String   derInstrRatio= dataMap.get("DER_INSTR_RATIO");
        //二级资产类型
        String   assetType2=dataMap.get("ASSET_TYPE2");
        if(temp.equals("重复数据;")){

        }else{

            // 1、发行机构代码必须与银行代码相同。
            // 银行模式才判断发行机构代码，联社模式不判断
            if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
                if (!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)) {
                    errMsgBuilder.append("发行机构代码必须与银行代码相同[").append("ISSUE_PARTY_ID=").append(issuePartyId).append("];");
                }
            }
            if(StringUtil.equals(assetType2, "1503")){
                //“固定收益部分所占比例”与“衍生金融工具所占比例”之和需小于等于1
                if(StringUtil.isNotEmpty(incomeRatio)&&isBigDecimal(incomeRatio)&&
                        StringUtil.isNotEmpty(derInstrRatio)&&isBigDecimal(derInstrRatio)){
                    if(new BigDecimal(incomeRatio).add(new BigDecimal(derInstrRatio)).compareTo(new BigDecimal(100))>0){
                        errMsgBuilder.append("以百分数形式表示的固定收益部分所占比例与衍生金融工具所占比例之和需小于等于100[").append("INCOME_RATIO=").append(incomeRatio).append(",DER_INSTR_RATIO=").append(derInstrRatio).append("];");
                    }
                }
            }

            //投资资产种类及比例要素的判断
            //投资资产种类及比例要素
            String   invTypeRat=dataMap.get("FUND_INVEST_ASSET");
            boolean  flag=true;
            String  regex="^(100|[1-9]?\\d(\\.\\d\\d?)?)%$|0$";
            if(StringUtil.isNotEmpty(invTypeRat)){
                //对于投资资产种类及比例要素按照";"切割成各个单元
                List<String>  invTaypeRatList=Arrays.asList(invTypeRat.split(GlobalConstant.SEMICOLON));
                //循环list，对于单元进行处理
                for(String invTaype:invTaypeRatList){
                    //按照":"对于单元进行切割
                    List<String>  invTaypePatten=Arrays.asList(invTaype.split(GlobalConstant.COLON));
                    //单元切割后，不是2个部分。则格式不通过
                    if(invTaypePatten!=null&&invTaypePatten.size()!=2){
                        flag=false;
                        break;
                    }else{
                        //单元切割后是2个部分，获取数值和文字
                        //数值
                        String  pattenOne=invTaypePatten.get(0);
                        if(pattenOne.contains(GlobalConstant.SPLIT)){
                            //表示为区间
                            List<String>  pattenOneSection=Arrays.asList(pattenOne.split(GlobalConstant.SPLIT));
                            //校验区间
                            if(!pattenOneSection.get(0).matches(regex)||
                                    !pattenOneSection.get(1).matches(regex)){
                                flag=false;
                                break;
                            }
                        }else{
                            //不是区间，单个数字百分号
                            if(!pattenOne.matches(regex)){
                                flag=false;
                                break;
                            }
                        }
                        //文字
                        //String  pattenTwo=invTaypePatten.get(1);
                    }
                }
            }
            if(!flag){
                errMsgBuilder.append("基金投资资产要素格式不对。正确的格式：数字%：文字；数字%-数字%：文字（其中，百分号、冒号、分号、连字号均应为英文标点，文字和数字部分均不得含有英文百分号、冒号、分号、连字号）。其中，数字格式为n..（5，2），且需大于等于0、小于等于100。[FUND_INVEST_ASSET="+dataMap.get("FUND_INVEST_ASSET")+"];");
            }
            if(StringUtils.isEmpty(temp)){
                if(StringUtils.isNotEmpty(errMsgBuilder.toString())&&excelValidDtoList!=null){
                        excelValidDtoList.add(new ExcelValidDto("导入成功",String.valueOf((i+3)),errMsgBuilder.toString()));
                }
            }else{
                if(StringUtils.isNotEmpty(errMsgBuilder.toString())&& excelValidDtoList!=null){
                    if(excelValidDtoList.size()>0){
                        for (ExcelValidDto excelValidDto : excelValidDtoList) {
                            if(excelValidDto.getRowNum().equals(String.valueOf((i+3)))){
                                excelValidDto.setErrorInfo(temp+errMsgBuilder.toString());
                                break;
                            }
                        }
                    }
                }
            }
            dataMap.put("ERRMSG", temp+errMsgBuilder.toString());
        }
        if (CollectionUtils.isNotEmpty(excelValidDtoList)) {
            this.excelValidListSort(excelValidDtoList);
        }
    }
    
    /**
     * 代码检查产品存续期交易临时表
     * @param dataMap
     */
    public  void  checkProdDeal(Map<String, String> dataMap,List<Map<String, String>> list){
        String  errMsg=dataMap.getOrDefault("ERRMSG", "");
        // 数据信息
        Map<String, String> data = new HashMap<>();
        // 规则对象
        CheckElement rule = new CheckElement();
        // 产品登记编码
        String prodRegistCode = dataMap.get("PROD_REGIST_CODE");
        //发行机构代码
        String   issuePartyId=dataMap.get("ISSUE_PARTY_ID");
        // 理财产品代码
        String prodId = dataMap.get("PROD_ID");
        //币种以及申购兑付金额
        String   ccyPurChase =dataMap.get("CCY_PURCHASE");//CNY,135500.00,10700.00,0.00;EUR,235500.00,20700.00,0.00;

        //获取申报登记相关
        List<ChbPrdDeclareSub> chbPrdDeclareSubList = new ArrayList<>();
        if (StringUtil.isNotEmpty(prodId) || StringUtil.isNotEmpty(prodRegistCode)){
            Map<String, Object> map = new HashMap<>();
            map.put("prodId", prodId);
            map.put("regCode", prodRegistCode);
            chbPrdDeclareSubList = commonCodeCheckMapper.queryChbPrdDeclareSubByRegCode(map);
        }
        // 银行模式才判断发行机构代码，联社模式不判断
        if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
        //发行机构代码必须与银行代码相同。
        if(!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)){
            errMsg +="发行机构代码必须与银行代码相同[ISSUE_PARTY_ID="+issuePartyId+"];";
        }}
//      // 该产品不存在。
//      if (StringUtil.isNotEmpty(prodRegistCode)){
//          List<ChbPrdresistCode> chbPrdresistCodeList = comminCodeCheckMapper.queryChbPrdresistCodeByRegCode(prodRegistCode);
//          if (StringUtil.isEmptyList(chbPrdresistCodeList)){
//              errMsg +="该产品不存在[PROD_REGIST_CODE="+prodRegistCode+"];";
//          }
//      }
        if (!StringUtil.isEmptyList(chbPrdDeclareSubList)){
            //运作模式
            String  operateMode=chbPrdDeclareSubList.get(0).getOperateMode();
            if (StringUtil.equals(operateMode, DictCodeListExt2.产品运作模式_开放式净值型.getValue())||
                    StringUtil.equals(operateMode, DictCodeListExt2.产品运作模式_封闭式净值型.getValue())   ){
                //对于净值型产品，净值币种不能为空
                if (StringUtil.isEmpty(dataMap.get("VALUE_CCY"))){
                    errMsg +="对于净值型产品,净值币种不能为空[VALUE_CCY="+dataMap.get("VALUE_CCY")+"];";
                }
                //对于净值型产品,初始净值不能为空  
                if (StringUtil.isEmpty(dataMap.get("ORIGINAL_VALUE"))){
                    errMsg +="对于净值型产品,初始净值不能为空[ORIGINAL_VALUE="+dataMap.get("ORIGINAL_VALUE")+"];";
                }
                //对于净值型产品,产品净值不能为空。
                if (StringUtil.isEmpty(dataMap.get("PRD_VALUE"))){
                    errMsg +="对于净值型产品,产品净值不能为空[PRD_VALUE="+dataMap.get("PRD_VALUE")+"];";
                }
                //对于净值型产品,累计净值不能为空。
                if (StringUtil.isEmpty(dataMap.get("TOTAL_VALUE"))){
                    errMsg +="对于净值型产品,累计净值不能为空[TOTAL_VALUE="+dataMap.get("TOTAL_VALUE")+"];";
                }
                //对于净值型产品,折算人民币初始净值不能为空。
                if (StringUtil.isEmpty(dataMap.get("CNY_ORL_VALUE"))){
                    errMsg +="对于净值型产品,折算人民币初始净值不能为空[CNY_ORL_VALUE="+dataMap.get("CNY_ORL_VALUE")+"];";
                }
                //对于净值型产品,折算人民币净值不能为空。
                if (StringUtil.isEmpty(dataMap.get("CNY_PRD_VALUE"))){
                    errMsg +="对于净值型产品,折算人民币净值不能为空[CNY_PRD_VALUE="+dataMap.get("CNY_PRD_VALUE")+"];";
                }
                //对于净值型产品,折算人民币累计净值不能为空。
                if (StringUtil.isEmpty(dataMap.get("CNY_TAL_VALUE"))){
                    errMsg +="对于净值型产品,折算人民币累计净值不能为空[CNY_TAL_VALUE="+dataMap.get("CNY_TAL_VALUE")+"];";
                }
                //对于净值型产品，净值日期不可为空
                if (StringUtil.isEmpty(dataMap.get("YIELD_DATE"))){
                    errMsg +="对于净值型产品,净值日期不可为空[YIELD_DATE="+dataMap.get("YIELD_DATE")+"];";
                }
            }else if(StringUtil.equals(operateMode, DictCodeListExt2.产品运作模式_开放式非净值型.getValue())||
                    StringUtil.equals(operateMode, DictCodeListExt2.产品运作模式_封闭式非净值型.getValue())  ){
                //对于非净值型产品,实现收益率不能为空。
                if (StringUtil.isEmpty(dataMap.get("REA_YIELD"))){
                    errMsg +="对于非净值型产品,实现收益率不能为空[REA_YIELD="+dataMap.get("REA_YIELD")+"];";
                }
                //对于非净值型产品,产品余额不能为空。
                if (StringUtil.isEmpty(dataMap.get("BALANCE_AMT"))){
                    errMsg +="对于非净值型产品,产品余额不能为空[BALANCE_AMT="+dataMap.get("BALANCE_AMT")+"];";
                }
            }
        }
        //分割币种以及兑付金额字符串
        if(StringUtil.isNotEmpty(ccyPurChase)){
            String[]   ccyStr=ccyPurChase.split(";");
            Set<String>  ccyset=new  HashSet<>();
            if(ccyStr!=null&&ccyStr.length>0){
                //多种币种以及兑付金额字符
                for(int i=0;i<ccyStr.length;i++){
                    String  st1=ccyStr[i];
                    String[]   st1str=st1.split(",");
                    //币种
                    String  ccy="";
                    //币种累计申购金额
                    String  ccyPurchase="";
                    //币种累计兑付金额
                    String  ccyCash="";
                    //币种累计兑付收益
                    String  ccyProfit="";
                    //获取币种
                    if(st1str!=null&&st1str.length<4){
                        errMsg +="币种及申购兑付金额格式不正确!正确格式:CNY,100,100,100[CCY_PURCHASE="+st1+"];";
                        //break;
                    }else{
                        //获取币种
                        ccy=st1str[0];
                        ccyset.add(ccy);
                        //币种累计申购金额
                        ccyPurchase=st1str[1];
                        //币种累计兑付金额
                        ccyCash=st1str[2];
                        //币种累计兑付收益
                        ccyProfit=st1str[3];
                    
                    //币种要素不可为空。
                    if(StringUtil.isEmpty(ccy)){
                        errMsg +="币种要素不可为空[CCY_PURCHASE="+ccy+"];";
                       //break;
                    }
                    //币种不在值域范围内。
                    if(StringUtil.isNotEmpty(ccy)){
                        if(!DictCodeListExt1.valuesfromDictCode(DictCode.币种_中债).contains(ccy)){
                            errMsg +="币种不在值域范围内[CCY_PURCHASE="+ccy+"];";
                               //break;
                        }
                    }

                    //该币种累计申购金额（元）要素不可为空。
                    if(StringUtil.isEmpty(ccyPurchase)){
                        errMsg +="该币种累计申购金额（元）要素不可为空[CCY_PURCHASE="+ccyPurchase+"];";
                           //break;
                    }
                    //该币种累计申购金额（元）必须大于等于0。
                    if(StringUtil.isNotEmpty(ccyPurchase)&&isBigDecimal(ccyPurchase)){
                        if(BigDecimal.ZERO.compareTo(new  BigDecimal(ccyPurchase))>0){
                            errMsg +="币种累计申购金额（元）必须大于等于0[CCY_PURCHASE="+ccyPurchase+"];";
                               //break;
                        }
                    }
                    //该币种累计申购金额（元）必须为n（15,2）格式。
                    rule.setSrcproperty("SUBSCRIBEAMT");
                    rule.setObjvalue("15,2");
                    rule.setNullable("Y");
                    data.put("SUBSCRIBEAMT", ccyPurchase);
                    if (!BaseCheckLib.isNumber2(data, rule)){
                        errMsg +="该币种累计申购金额（元）必须为n（15,2）格式[CCY_PURCHASE="+ccyPurchase+"];";
                           //break;
                    }

                    //该币种累计兑付金额（元）要素不可为空。
                    if(StringUtil.isEmpty(ccyCash)){
                        errMsg +="该币种累计兑付金额（元）要素不可为空[CCY_PURCHASE="+ccyCash+"];";
                           //break;
                    }
                    //该币种累计兑付金额（元）必须大于等于0。
                    if(StringUtil.isNotEmpty(ccyCash)&&isBigDecimal(ccyCash)){
                    	if(BigDecimal.ZERO.compareTo(new  BigDecimal(ccyCash))>0){
                            errMsg +="币种累计兑付金额（元）必须大于等于0[CCY_PURCHASE="+ccyCash+"];";
                              // break;
                        }
                    }
                    //该币种累计兑付金额（元）必须为n（15,2）格式。
                    rule.setSrcproperty("SUBSCRIBEAMT");
                    rule.setObjvalue("15,2");
                    rule.setNullable("Y");
                    data.put("SUBSCRIBEAMT", ccyCash);
                    if(!BaseCheckLib.isNumber2(data, rule)){
                        errMsg +="该币种累计兑付金额（元）必须为n（15,2）格式[CCY_PURCHASE="+ccyCash+"];";
                           //break;
                    }

                    //该币种累计兑付收益金额（元）要素不可为空。
                    if(StringUtil.isEmpty(ccyProfit)){
                        errMsg +="该币种累计兑付收益金额（元）要素不可为空[CCY_PURCHASE="+ccyProfit+"];";
                           //break;
                    }
                    //该币种累计兑付收益金额（元）必须为n（15,2）格式。
                    rule.setSrcproperty("SUBSCRIBEAMT");
                    rule.setObjvalue("15,2");
                    rule.setNullable("Y");
                    data.put("SUBSCRIBEAMT", ccyProfit);
                    if(!BaseCheckLib.isNumber2(data, rule)){
                        errMsg +="该币种累计兑付收益金额（元）必须为n（15,2）格式[CCY_PURCHASE="+ccyProfit+"];";
                           //break;
                    }
                    
                    //该币种累计兑付收益金额（元）必须小于等于该币种累计兑付金额（元）。
                    if(StringUtil.isNotEmpty(ccyProfit)&&StringUtil.isNotEmpty(ccyCash)
                            &&isBigDecimal(ccyProfit)&&isBigDecimal(ccyCash)){
                    if(new BigDecimal(ccyProfit).compareTo(new BigDecimal(ccyCash))>0){
                        errMsg +="该币种累计兑付收益金额（元）必须小于等于该币种累计兑付金额（元）[CCY_PURCHASE="+st1+"];";
                           //break;
                    }
                    }
                    //TODO  币种必须为人名币 待确认
                    }
                }
            }
            if(ccyset!=null&&ccyset.size()>0&&ccyStr!=null&&ccyStr.length>0
                    ){
                if(ccyset.size()<ccyStr.length)
                    errMsg +="币种多选选项不能重复[CCY_PURCHASE="+ccyPurChase+"];";
            }
        }
        dataMap.put("ERRMSG", errMsg);
    }
    /**
     * 代码检查产品终止数据
     * @param dataMap
     * @param
     */
    public  void  checkProdEndSubTmp(Map<String, String> dataMap,Set<String> prodRegistCodeList) {
        String  errMsg=dataMap.getOrDefault("ERRMSG", "");
        //发行机构代码
        String   issuePartyId=dataMap.get("ISSUE_PARTY_ID");
        // 产品登记编码
        String prodRegistCode = dataMap.get("PROD_REGIST_CODE");
        //产品实际终止日
        String mDate=dataMap.get("MDATE");
        //理财产品代码
        String  prodId=dataMap.get("PROD_ID");
        //银行实际实现收入
        String  profit=dataMap.get("PROFIT");
        //本机构管理费
        String  manFeeIn=dataMap.get("MAN_FEE_IN");
        //本机构托管费
        String custFeeIn=dataMap.get("CUST_FEE_IN");
        //本机构销售手续费
        String  couFeeIn=dataMap.get("COU_FEE_IN");
        //本机构其他产品费用
        String  otherFeeIn=dataMap.get("OTHER_FEE_IN");
        // 产品登记编码报文内不可重复。
        if(StringUtil.isNotEmpty(prodRegistCode) && prodRegistCodeList.contains(prodRegistCode)){
            errMsg +="产品登记编码报文内不可重复[PROD_REGIST_CODE="+prodRegistCode+"];";
        }else{
            prodRegistCodeList.add(prodRegistCode);
        }
//      // 该产品不存在。
//      if (StringUtil.isNotEmpty(prodRegistCode)){
//          List<ChbPrdresistCode> chbPrdresistCodeList = comminCodeCheckMapper.queryChbPrdresistCodeByRegCode(prodRegistCode);
//          if (StringUtil.isEmptyList(chbPrdresistCodeList)){
//              errMsg +="该产品不存在[PROD_REGIST_CODE="+prodRegistCode+"];";
//          }
//      }

        //发行机构代码必须与银行代码相同。
        // 银行模式才判断发行机构代码，联社模式不判断
        if(StringUtil.isNotEmpty(issuePartyId) && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
        if(!StringUtil.equals(CommonUtil.selectBankCode(), issuePartyId)){
            errMsg +="发行机构代码必须与银行代码相同[ISSUE_PARTY_ID="+issuePartyId+"];";
        }
        }
        //理财产品实际终止日期必须大于发行登记要素中的“产品起始日期”
        List<ChbPrdIssueSub>  listChbPrdIssueSub= commonCodeCheckMapper.getChbPrdIssueSubByProdId(prodId);
        if(!StringUtil.isEmptyList(listChbPrdIssueSub)){
            //产品起始日期
            Date prdVdate=listChbPrdIssueSub.get(0).getPrdVdate();
            if(prdVdate.compareTo(DateUtil.parse(mDate))>=0){
                errMsg +="理财产品实际终止日期应大于发行登记中的“产品起始日期”[MDATE="+mDate+"];";
            }
        }
        //银行实际实现收入=本机构管理费+本机构托管费+本机构销售手续费+本机构其他产品费用。
        try{
            if(new  BigDecimal(profit).compareTo(new BigDecimal(manFeeIn).
                    add(new BigDecimal(custFeeIn).
                            add(new  BigDecimal(otherFeeIn)
                            ).add(new  BigDecimal(couFeeIn))))!=0){
                errMsg +="银行实际实现收入≠本机构管理费+本机构托管费+本机构销售手续费+本机构其他产品费用，请检查这五个元素的值，重新填写[PROFIT="+profit+
                        ",MAN_FEE_IN="+manFeeIn+",CUST_FEE_IN="+custFeeIn+",COU_FEE_IN="+couFeeIn+",OTHER_FEE_IN="+otherFeeIn+"];";
            }}catch(Exception e){
            errMsg +="银行实际实现收入≠本机构管理费+本机构托管费+本机构销售手续费+本机构其他产品费用，请检查这五个元素的值，重新填写[PROFIT="+profit+
                    ",MAN_FEE_IN="+manFeeIn+",CUST_FEE_IN="+custFeeIn+",COU_FEE_IN="+couFeeIn+",OTHER_FEE_IN="+otherFeeIn+"];";

        }

        dataMap.put("ERRMSG", errMsg);

    }

    public static boolean isBigDecimal(String str){
        if(str==null || str.trim().length() == 0){
            return false;
        }
//      char[] chars = str.toCharArray();
//      int sz = chars.length;
//      int i = (chars[0] == '-') ? 1 : 0;
//      if(i == sz) return false;
//      
//      if(chars[i] == '.') return false;//除了负号，第一位不能为'小数点'
//      
//      boolean radixPoint = false;
//      for(; i < sz; i++){
//          if(chars[i] == '.'){
//              if(radixPoint) return false;
//              radixPoint = true;
//          }else if(!(chars[i] >= '0' && chars[i] <= '9')){
//              return false;
//          }
//      }
        return Pattern.matches("^-?[0-9]+.?[0-9]*$", str);
    }

    private void insertErroData(List<ChbInterfErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("错误信息数据插入开始--------------" + new Date());
            ChbInterfErrodataMapper batchMapper = session.getMapper(ChbInterfErrodataMapper.class);
            for(ChbInterfErrodataWithBLOBs single: erroList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("错误信息数据插入结束--------------" + new Date());
    }

    /**
     * 批量插入销售错误信息表
     * @param erroList 销售错误信息表
     */
    private void insertChbSalerStlDeaErrodata(List<ChbSalerStlDeaErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("销售数据信息表错误信息数据插入开始--------------" + new Date());
            ChbSalerStlDeaErrodataMapper batchMapper = session.getMapper(ChbSalerStlDeaErrodataMapper.class);
            for(ChbSalerStlDeaErrodataWithBLOBs single: erroList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("销售数据信息表错误信息数据插入结束--------------" + new Date());
    }

    /**
     * 批量插入销售人员错误信息表
     * @param erroList 销售错误信息表
     */
    private void insertChbSalerIdenErrodata(List<ChbSalerIdenErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("销售人员数据信息表错误信息数据插入开始--------------" + new Date());
            ChbSalerIdenErrodataMapper batchMapper = session.getMapper(ChbSalerIdenErrodataMapper.class);
            for(ChbSalerIdenErrodataWithBLOBs single: erroList){
                single.setStatus("A");
                single.setWorkflowId(IDGenerator.generate(20));
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("销售人员数据信息表错误信息数据插入结束--------------" + new Date());
    }


    /**
     * 批量插入总部分支机构错误信息表
     * @param erroList 销售错误信息表
     */
    private void insertChbSalerInstErrodata(List<ChbSalerInstErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("总部分支机构信息表错误信息数据插入开始--------------" + new Date());
            ChbSalerInstErrodataMapper batchMapper = session.getMapper(ChbSalerInstErrodataMapper.class);
            for(ChbSalerInstErrodataWithBLOBs single: erroList){
                single.setStatus("A");
                single.setWorkflowId(IDGenerator.generate(20));
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("总部分支机构信息表错误信息数据插入结束--------------" + new Date());
    }

    /**
     * 批量插入销售人员奖惩错误信息表
     * @param erroList 销售错误信息表
     */
    private void insertChbSalerRewErrodata(List<ChbSalerRewPenaErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("销售人员奖惩错误信息数据插入开始--------------" + new Date());
            ChbSalerRewPenaErrodataMapper batchMapper = session.getMapper(ChbSalerRewPenaErrodataMapper.class);
            for(ChbSalerRewPenaErrodataWithBLOBs single: erroList){
                single.setSalerRewPenaNo(IDGenerator.generate(32));
                single.setStatus("A");
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("销售人员奖惩错误信息数据插入结束--------------" + new Date());
    }


    /**
     * 批量插入资产要素错误信息表
     * @param erroList 错误信息表
     */
    private void insertChbAssetInfoErroData(List<ChbAssetInfoErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("资产要素登记错误信息数据插入开始--------------" + new Date());
            ChbAssetInfoErrodataMapper batchMapper = session.getMapper(ChbAssetInfoErrodataMapper.class);
            for(ChbAssetInfoErrodataWithBLOBs single: erroList){
                batchMapper.insert(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("资产要素登记错误信息数据插入结束--------------" + new Date());
    }

    /**
     * 批量插入销售错误信息表
     * @param erroList 销售错误信息表
     */
    private void deleteChbSalerStlDeaErrodataByTradeNo(List<ChbSalerStlDeaErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("销售数据信息表错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(ChbSalerStlDeaErrodataWithBLOBs single: erroList){
                if (StringUtil.isNotEmpty(single.getTradeNo())){
                    batchMapper.deleteChbSalerStlDeaErrorDataByTradeNo(single.getTradeNo());
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("销售数据信息表错误信息数据删除结束--------------" + new Date());
    }

    /**
     * 批量插入销售错误信息表
     * @param erroList 销售错误信息表
     */
    private void deleteChbSalerIdenErrodataByNo(List<ChbSalerIdenErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("销售人员数据信息表错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(ChbSalerIdenErrodataWithBLOBs single: erroList){
                if (StringUtil.isNotEmpty(single.getSalerInsNo())){
                    batchMapper.deleteChbSalerIdenErrorDataByTradeNo(single.getSalerInsNo());
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("销售数据人员信息表错误信息数据删除结束--------------" + new Date());
    }

    /**
     * 批量插入总部分支机构错误信息表
     * @param erroList 销售错误信息表
     */
    private void deleteChbSalerInstErrodataByNo(List<ChbSalerInstErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("总部分支机构信息表错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(ChbSalerInstErrodataWithBLOBs single: erroList){
                if (StringUtil.isNotEmpty(single.getSalerOrgCode())){
                    batchMapper.deleteChbSalerInstErrorDataByTradeNo(single.getSalerOrgCode());
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("总部分支机构信息表错误信息数据删除结束--------------" + new Date());
    }

    /**
     * 批量插入销售人员奖惩错误信息表
     * @param erroList 销售错误信息表
     */
    private void deleteChbSalerRewErrodataByNo(List<ChbSalerRewPenaErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("销售人员奖惩错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(ChbSalerRewPenaErrodataWithBLOBs single: erroList){
                if (StringUtil.isNotEmpty(single.getSalerRewPenaNo())){
                    batchMapper.deleteChbSalerRewErrorDataByTradeNo(single.getSalerRewPenaNo());
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("销售人员奖惩信息表错误信息数据删除结束--------------" + new Date());
    }


    /**
     * 批量插入资产要素错误信息表
     * @param erroList 错误信息表
     */
    private void deleteAssetInfoErrorDataByAssetCode(List<ChbAssetInfoErrodataWithBLOBs> erroList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("资产要素登记错误信息数据删除开始--------------" + new Date());
            CommonCodeCheckMapper batchMapper = session.getMapper(CommonCodeCheckMapper.class);
            for(ChbAssetInfoErrodataWithBLOBs single: erroList){
                if (StringUtil.isNotEmpty(single.getAssetCode())){
                    batchMapper.deleteAssetInfoErrorDataByAssetCode(single.getAssetCode());
                    count ++;
                }
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
        logger.debug("资产要素登记错误信息数据删除结束--------------" + new Date());
    }

    /**
     * 根据错误消息创建需要入库的Dto
     *
     * @param
     * @param map 数据
     * @param
     * @return 需要入库的Dto
     */
    public ChbInterfErrodataWithBLOBs createChbInterfErrdata(String fireName,
                                                             String interfType, Map<String, String> map,
                                                             String bussinessKey, String... checkEuplicatedKeys) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
        String dataId = null;
        if(StringUtil.isEmpty(bussinessKey) || StringUtil.isEmpty(map.get(bussinessKey))){
            //若根据业务主键找不到值，则根据checkEuplicatedKeys联合主键来进行赋值
            StringBuffer keyVFinal = new StringBuffer();
            for(String key: checkEuplicatedKeys){
                keyVFinal.append(map.get(key));
                keyVFinal.append("|");
            }
            dataId = keyVFinal.toString();
        }
        else {
            dataId = map.get(bussinessKey);
        }
        // 拼装错误信息
        ChbInterfErrodataWithBLOBs errodata = new ChbInterfErrodataWithBLOBs();
        // 主键uuid
        errodata.setDatauuid(IDGenerator.generate(36));
        // 文件名
        errodata.setFireName(fireName);
        // 业务主键
        errodata.setDataid(dataId);
        // 接口类型
        errodata.setInterfType(interfType);
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        newMap.remove(null);
        errodata.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        errodata.setErrMsg(msg);
        // 数据状态
        errodata.setStatus("E");
        errodata.setCreateUser("system");
        errodata.setUpdateUser("system");
        errodata.setCreateTime(new Date());
        errodata.setUpdateTime(new Date());
        return errodata;
    }

    /**
     * 根据错误消息创建需要入库的Dto
     *
     * @param map 数据
     * @return 需要入库的Dto
     */
    public ChbSalerStlDeaErrodataWithBLOBs createChbSalerStlDeaErrodata(Map<String, String> map) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
        // 拼装错误信息
        ChbSalerStlDeaErrodataWithBLOBs chbSalerStlDeaErrodata = new ChbSalerStlDeaErrodataWithBLOBs();

        chbSalerStlDeaErrodata.setRegistSeqno(IDGenerator.generate(36));

        // 提供销售结算资金服务机构代码
        chbSalerStlDeaErrodata.setInstitutionCode(map.get("INSTITUTION_CODE"));
        //产品登记编码
        chbSalerStlDeaErrodata.setPrdRegistCode(map.get("PRD_REGIST_CODE"));
        // 销售结算资金账号
        chbSalerStlDeaErrodata.setAccountNo(map.get("ACCOUNT_NO"));
        //销售结算资金流动方向
        chbSalerStlDeaErrodata.setTradePs(map.get("TRADE_PS"));
        // 销售结算资金交易流水号
        chbSalerStlDeaErrodata.setTradeNo(map.get("TRADE_NO"));
        //销售结算资金交易日期
        chbSalerStlDeaErrodata.setTradeVdate(null!=map.get("TRADE_VDATE")&&DateUtil.isDateStr(map.get("TRADE_VDATE").toString())?DateUtil.parse(map.get("TRADE_VDATE").toString()):null);
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        chbSalerStlDeaErrodata.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        chbSalerStlDeaErrodata.setErrMsg(msg);
        // 数据状态
        chbSalerStlDeaErrodata.setStatus("E");
        chbSalerStlDeaErrodata.setCreateUser("system");
        chbSalerStlDeaErrodata.setUpdateUser("system");
        chbSalerStlDeaErrodata.setCreateTime(new Date());
        chbSalerStlDeaErrodata.setUpdateTime(new Date());

        return chbSalerStlDeaErrodata;
    }


    /**
     * 根据错误消息创建需要入库的Dto
     *
     * @param map 数据
     * @return 需要入库的Dto
     */
    public ChbSalerIdenErrodataWithBLOBs createChbSalerInderErrodata(Map<String, String> map) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
        // 拼装错误信息
        ChbSalerIdenErrodataWithBLOBs chbSalerIdenErrodata = new ChbSalerIdenErrodataWithBLOBs();

        chbSalerIdenErrodata.setSalerInsNo(map.get("SALER_INS_NO"));

        chbSalerIdenErrodata.setSalerOrgCode(map.get("SALER_ORG_CODE"));

        chbSalerIdenErrodata.setSalerName(map.get("SALER_NAME"));

        chbSalerIdenErrodata.setSalerSex(map.get("SALER_SEX"));

        chbSalerIdenErrodata.setSalerCredentType(map.get("SALER_CREDENT_TYPE"));

              // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        chbSalerIdenErrodata.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        chbSalerIdenErrodata.setErrMsg(msg);
        // 数据状态
        chbSalerIdenErrodata.setStatus("E");
        chbSalerIdenErrodata.setCreateUser("system");
        chbSalerIdenErrodata.setUpdateUser("system");
        chbSalerIdenErrodata.setCreateTime(new Date());
        chbSalerIdenErrodata.setUpdateTime(new Date());

        return chbSalerIdenErrodata;
    }


    /**
     * 根据错误消息创建需要入库的Dto
     *
     * @param map 数据
     * @return 需要入库的Dto
     */
    public ChbSalerInstErrodataWithBLOBs createChbSalerInstErrodata(Map<String, String> map) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
        // 拼装错误信息
        ChbSalerInstErrodataWithBLOBs chbSalerInstErrodata = new ChbSalerInstErrodataWithBLOBs();


        chbSalerInstErrodata.setSalerOrgCode(map.get("SALER_ORG_CODE"));

        chbSalerInstErrodata.setSalerInstCode(map.get("SALER_INST_CODE"));

        chbSalerInstErrodata.setSalerOrgName(map.get("SALER_ORG_NAME"));

        chbSalerInstErrodata.setSalerOrgLevel(map.get("SALER_ORG_LEVEL"));

        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        chbSalerInstErrodata.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        chbSalerInstErrodata.setErrMsg(msg);
        // 数据状态
        chbSalerInstErrodata.setStatus("E");
        chbSalerInstErrodata.setCreateUser("system");
        chbSalerInstErrodata.setUpdateUser("system");
        chbSalerInstErrodata.setCreateTime(new Date());
        chbSalerInstErrodata.setUpdateTime(new Date());

        return chbSalerInstErrodata;
    }
    /**
     * 根据错误消息创建需要入库的Dto
     *
     * @param map 数据
     * @return 需要入库的Dto
     */
    public ChbSalerRewPenaErrodataWithBLOBs createChbSalerRewErrodata(Map<String, String> map) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
        // 拼装错误信息
        ChbSalerRewPenaErrodataWithBLOBs chbSalerRewPenaErrodata = new ChbSalerRewPenaErrodataWithBLOBs();

        chbSalerRewPenaErrodata.setSalerRewPenaNo(map.get("SALER_REW_PENA_NO"));

        chbSalerRewPenaErrodata.setSalerInsNo(map.get("SALER_INS_NO"));

        chbSalerRewPenaErrodata.setSalerRewPenaTyep(map.get("SALER_REW_PENA_TYEP"));

        chbSalerRewPenaErrodata.setSalerRewPenaInst(map.get("SALER_REW_PENA_INST"));

        if(map.get("SALER_REW_PENA_START")!=null){
            try{
                chbSalerRewPenaErrodata.setSalerRewPenaStart(DateUtil.parse(map.get("SALER_REW_PENA_START")));
            }catch(Exception e){
                chbSalerRewPenaErrodata.setSalerRewPenaStart(null);
            }
        }
        if(map.get("SALER_COMPLAIN_DATE")!=null){
            try{
            chbSalerRewPenaErrodata.setSalerComplainDate(DateUtil.parse(map.get("SALER_COMPLAIN_DATE")));
            }catch(Exception e){
                chbSalerRewPenaErrodata.setSalerComplainDate(null);
            }
        }
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        chbSalerRewPenaErrodata.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        chbSalerRewPenaErrodata.setErrMsg(msg);
        // 数据状态
        chbSalerRewPenaErrodata.setStatus("E");
        chbSalerRewPenaErrodata.setCreateUser("system");
        chbSalerRewPenaErrodata.setUpdateUser("system");
        chbSalerRewPenaErrodata.setCreateTime(new Date());
        chbSalerRewPenaErrodata.setUpdateTime(new Date());

        return chbSalerRewPenaErrodata;
    }




    /**
     * 根据错误消息创建需要入库的Dto
     *
     * @param map 数据
     * @return 需要入库的Dto
     */
    public ChbAssetInfoErrodataWithBLOBs createChbAssetInfoInterfErrdata(Map<String, String> map,String flag) {
        String msg = map.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
        // 拼装错误信息
        ChbAssetInfoErrodataWithBLOBs chbAssetInfoErrodata = new ChbAssetInfoErrodataWithBLOBs();
        // 主键uuid
        chbAssetInfoErrodata.setDatauuid(IDGenerator.generate(36));
        // 行内资产负债编码
        chbAssetInfoErrodata.setAssetCode(map.get("ASSET_CODE"));
        // 资产名称
        chbAssetInfoErrodata.setAssetName(map.get("ASSET_NAME"));
        // 资产类型
        chbAssetInfoErrodata.setAssetType(map.get("ASSET_TYPE"));
        // 资产/负债二级类别
        chbAssetInfoErrodata.setAssetType2(map.get("ASSET_TYPE2"));
        // 起息日
        chbAssetInfoErrodata.setVdate(map.get("VDATE"));
        // 到期日
        chbAssetInfoErrodata.setMdate(map.get("MDATE"));
        // 上游资产一级分类
        chbAssetInfoErrodata.setOrgAssetType1(map.get("ORG_ASSET_TYPE1"));
        // 上游资产二级分类
        chbAssetInfoErrodata.setOrgAssetType2(map.get("ORG_ASSET_TYPE2"));
        // 上游资产三级分类
        chbAssetInfoErrodata.setOrgAssetType3(map.get("ORG_ASSET_TYPE3"));
        // 上游资产四级分类
        chbAssetInfoErrodata.setOrgAssetType4(map.get("ORG_ASSET_TYPE4"));
        // 上游资产五级分类
        chbAssetInfoErrodata.setOrgAssetType5(map.get("ORG_ASSET_TYPE5"));
        // 拼接错误字段信息
        Map<String, String> newMap = new HashMap<>(map);
        newMap.remove("ERRMSG");
        newMap.remove(null);
        chbAssetInfoErrodata.setOrgData(JsonUtils.object2Json(newMap));
        // 错误提示信息
        chbAssetInfoErrodata.setErrMsg(msg);
        // 数据状态
        chbAssetInfoErrodata.setStatus("A");
        if(flag.equals("I")){
            chbAssetInfoErrodata.setCreateUser("interf");
            chbAssetInfoErrodata.setUpdateUser("interf");
        }else{
            chbAssetInfoErrodata.setCreateUser(CommonUtil.getUserName());
            chbAssetInfoErrodata.setUpdateUser(CommonUtil.getUserName());
        }
        chbAssetInfoErrodata.setCreateTime(new Date());
        chbAssetInfoErrodata.setUpdateTime(new Date());

        return chbAssetInfoErrodata;
    }

    /***
     * 检查总部机构信息
     * @param dataMap
     */
    public    void  checkChbSalerStlDea(Map<String, String> dataMap,String tableName){
        String  errMsg = dataMap.getOrDefault("ERRMSG", "");
        // 总部或分支机构名称
        String tradeTime = dataMap.get("TRADE_TIME");
        if(tableName.equals("CHB_SALER_STL_DEAL_INFO")) {
            if (tradeTime != null && tradeTime != "") {
                if (tradeTime.length() != 6) {
                    errMsg += "交易时间填写错误。[TRADE_TIME=" + dataMap.get("TRADE_TIME") + "];";
                }else if (!checkFigure(tradeTime)){
                    errMsg += "交易时间填写错误。[TRADE_TIME=" + dataMap.get("TRADE_TIME") + "];";
                }
                if(tradeTime.length() == 6 && checkFigure(tradeTime)) {
                    if(!checkFigure(tradeTime.substring(0, 1))){
                        errMsg += "交易时间填写错误。[TRADE_TIME=" + dataMap.get("TRADE_TIME") + "];";
                    }
                    else if (Integer.parseInt(tradeTime.substring(0, 2)) > 23
                            || Integer.parseInt(tradeTime.substring(2, 3)) > 5 || Integer.parseInt(tradeTime.substring(4, 5)) > 5) {
                        errMsg += "交易时间填写错误。[TRADE_TIME=" + dataMap.get("TRADE_TIME") + "];";
                    }
                }
            }
        }
        dataMap.put("ERRMSG", errMsg);
    }


    /***
     * 检查销售奖惩信息
     * @param dataMap
     */
    public    void  checkRewPenaInfo(Map<String, String> dataMap,String tableName){
        String  errMsg = dataMap.getOrDefault("ERRMSG", "");
        // 记录分类
        String rewPenaType = dataMap.get("SALER_REW_PENA_TYEP");
        //奖惩单位
        String rewPenaInst = dataMap.get("SALER_REW_PENA_INST");
        //奖惩单位具体名称
        String rewPenaInstName = dataMap.get("SALER_REW_PENA_INST_NAME");
        //内部编号
        String salerInsNo = dataMap.get("SALER_INS_NO");


        //记录分类选择“01 奖励”或“02 惩处”，且“奖惩单位”选择“99 其他”时必填；
        if((StringUtil.equals(DictCodeListExt2.记录分类_奖励.getValue(),rewPenaType)||
                StringUtil.equals(DictCodeListExt2.记录分类_惩处.getValue(),rewPenaType))&&
                        StringUtil.equals(DictCodeListExt2.记录分类_其他.getValue(),rewPenaInst)){
            if(StringUtil.isEmpty(rewPenaInstName)){
                errMsg +="当记录分类选择“01 奖励”或“02 惩处”，且“奖惩单位”选择“99 其他”时，奖惩单位具体名称必填[SALER_REW_PENA_INST_NAME="+dataMap.get("SALER_REW_PENA_INST_NAME")+"];";
            }
        }else if(StringUtil.equals(DictCodeListExt2.记录分类_投诉.getValue(),rewPenaType)||
                StringUtil.equals(DictCodeListExt2.记录分类_金融管理部门.getValue(),rewPenaInst)||
                StringUtil.equals(DictCodeListExt2.记录分类_本机构.getValue(),rewPenaInst)){
              //否则不可填
            if(!StringUtil.isEmpty(rewPenaInstName)){
                errMsg +="奖惩单位具体名称不可填[SALER_REW_PENA_INST_NAME="+dataMap.get("SALER_REW_PENA_INST_NAME")+"];";
            }
        }
        if(tableName.equals("CHB_SALER_REW_PENA_INFO")) {
            //内部编号
            if (salerInsNo != null && salerInsNo != "") {
                if (commonCodeCheckMapper.checkSalerInsNo(salerInsNo) == 0) {
                    errMsg += "内部编号未被维护。[SALER_INS_NO=" + dataMap.get("SALER_INS_NO") + "];";

                }
            }
        }
        dataMap.put("ERRMSG", errMsg);
    }
    /***
     * 检查总部机构信息
     * @param dataMap
     */
    public    void  checkSaleInstInfo(Map<String, String> dataMap,Set<String> saleInstSet,String tableName){
        String  errMsg = dataMap.getOrDefault("ERRMSG", "");
        // 总部或分支机构名称
        String salerOrgName = dataMap.get("SALER_ORG_NAME");
            //销售机构代码
            String rewPenaInst = dataMap.get("SALER_INST_CODE");
        if(tableName.equals("CHB_SALER_INST_INFO")) {
            if (StringUtils.isNotEmpty(salerOrgName)) {
                if (commonCodeCheckMapper.checkSalerOrgName(salerOrgName) > 0 || saleInstSet.contains(salerOrgName)) {
                    errMsg += "总部或分支机构名称已存在。[SALER_ORG_NAME=" + dataMap.get("SALER_ORG_NAME") + "];";
                }
                saleInstSet.add(salerOrgName);
            }
        }
        // 银行模式才判断发行机构代码，联社模式不判断
        if(!StringUtil.equals(CommonUtil.selectBankCode(), rewPenaInst)
                && StringUtil.equals(SvrDataUtil.getSysParam("AUTHORITY_TYPE"), "01")){
            errMsg +="销售机构代码必须与中债登记机构代码相同[SALER_INST_CODE="+rewPenaInst+"];";
        }
        dataMap.put("ERRMSG", errMsg);
    }


    /***
     * 检查销售人员信息
     * @param dataMap
     */
    public    void  checkSalerIdenInfo(Map<String, String> dataMap,String tableName,Map<String, String> map){
        String  errMsg = dataMap.getOrDefault("ERRMSG", "");
        // 证件类别
        String rewPenaType = dataMap.get("SALER_CREDENT_TYPE");
        //证件号码
        String salerCredentNo = dataMap.get("SALER_CREDENT_NO");
        //证件名称
        String salerOtherCredentName = dataMap.get("SALER_OTHER_CREDENT_NAME");
        //理财培训证书
        String salerChbTrainNo = dataMap.get("SALER_CHB_TRAIN_NO");
        //所属区域
        String salerAreaType = dataMap.get("SALER_AREA_TYPE");
        //学位
        String salerDegree = dataMap.get("SALER_DEGREE");
        //学历
        String salerEduLevel = dataMap.get("SALER_EDU_LEVEL");
        //专业技术职称
        String salerLevel = dataMap.get("SALER_LEVLE");
        //理财专业证书
        String salerCertificateType = dataMap.get("SALER_CERTIFICATE_TYPE");
        //备注
        String remark = dataMap.get("REMARK");
        //总部分支机构信息
        String salerOrgCode = dataMap.get("SALER_ORG_CODE");



        //证件号码重复校验
        if(tableName.equals("CHB_SALER_IDEN_INFO")) {
            if (rewPenaType != null && rewPenaType != "") {
                if (rewPenaType.equals("99")) {
                    if (salerOtherCredentName != null && salerOtherCredentName != "") {
                        String keyset = salerOtherCredentName + salerCredentNo;
                        if (map.containsKey(keyset)) {
                            errMsg += "当证件类别选择“99”时，“其他证件名称+证件号码”不可同时相同。[SALER_CREDENT_NO=" + dataMap.get("SALER_CREDENT_NO") + "];";
                        } else {
                            map.put(keyset, keyset);
                        }
                        if (salerCredentNo != null && salerCredentNo != "") {
                            if (commonCodeCheckMapper.checkSalerCredentNo(null, salerOtherCredentName, salerCredentNo) > 0) {
                                errMsg += "其他证件名称相同时，证件号码不可重复。[SALER_CREDENT_NO=" + dataMap.get("SALER_CREDENT_NO") + "];";
                            }
                        }
                    }
                } else {
                    if (salerCredentNo != null && salerCredentNo != "") {
                        String keyset = rewPenaType + salerCredentNo;
                        if (map.containsKey(keyset)) {
                            errMsg += "当证件类别选择“01-05”时,“证件类别+证件号码”不可同时相同。[SALER_CREDENT_NO=" + dataMap.get("SALER_CREDENT_NO") + "];";
                        } else {
                            map.put(keyset, keyset);
                        }
                        if (commonCodeCheckMapper.checkSalerCredentNo(rewPenaType, null, salerCredentNo) > 0) {
                            errMsg += "证件类别相同时，证件号码不可重复。[SALER_CREDENT_NO=" + dataMap.get("SALER_CREDENT_NO") + "];";
                        }
                    }
                }
            }
        }
        //理财登记培训证书编号格式校验
        if(StringUtils.isNotEmpty(salerChbTrainNo)){
            String regex = "^([L][C][0-9]{12})$";
            if(!salerChbTrainNo.matches(regex)){
                errMsg +="理财登记培训证书编号格式不对。[SALER_CHB_TRAIN_NO="+dataMap.get("SALER_CHB_TRAIN_NO")+"];";

            }
        }
        //备注非空校验(所属区域，学位，学历，专业技术职称，理财专业证书为“其他”时备注必填)
        if((salerAreaType != null && salerAreaType != "") || (salerDegree != null && salerDegree != "")
                || (salerEduLevel != null &&salerEduLevel != "") || (salerLevel != null && salerLevel != "")
                ||(salerCertificateType != null && salerCertificateType != "")){
            if (salerAreaType.equals("900000") || salerDegree.equals("99")
                    || salerEduLevel.equals("99") || salerLevel.equals("99")
                    || this.inclodeNine(salerCertificateType)) {
                if (remark.isEmpty()) {
                    errMsg +="所属区域，学位，学历，专业技术职称或理财专业证书选择其他时，备注不能为空。[REMARK="+dataMap.get("REMARK")+"];";
                }
            }
        }
        if(tableName.equals("CHB_SALER_IDEN_INFO")) {
            //所属总部或分支机构校验有效性
            if (salerOrgCode != null && salerOrgCode != "") {
                if (commonCodeCheckMapper.checkSalerOrgCode(salerOrgCode) == 0) {
                    errMsg += "所属总部或分支机构已被删除请重新选择。[SALER_ORG_CODE=" + dataMap.get("SALER_ORG_CODE") + "];";

                }
            }
        }
        dataMap.put("ERRMSG", errMsg);
    }

    public static boolean inclodeNine(String value){
        boolean status = false;
        if (!isEmpty(value)) {
            status = value.contains("99");
        }
        return status;
    }
    public static boolean isEmpty(String value) {
        boolean flag = false;
        if(StringUtil.isEmpty(value)){
            flag = true;
        }
        else if(StringUtil.isEmpty(value.trim())){
            flag = true;
        }
        return flag;
    }

    /**
     * 判断是否为数字
     *
     * @param
     * @return 返回boolean
     */
    private boolean checkFigure(String figure) {
        try{
            new BigDecimal(figure);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
