package com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.cnbond.dto.CnBondInterfItem;
import com.joyintech.tams.report.interf.cnbond.dto.InterfCnBondSubmitPara;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.validate.SalesTransactionMaintenanceValid;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.report.submitmanagement.equityinforegist.common.EquityInfoRegistCommonUtil;
import com.joyintech.tams.report.submitmanagement.salerrewpenainforegist.dao.ChbSalerRewPenaInfoRegistMapper;
import com.joyintech.tams.report.submitmanagement.salerrewpenainforegist.dto.SalerRewPenaInfoRegistDto;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dao.SalesTransactionMaintenanceRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto.SalesTransactionMaintenanceRegist001Dto;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2017年9月19日
 * @see SalesTransactionMaintenanceRegist001Service
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SalesTransactionMaintenanceRegist001Service extends CommonExportService<SalesTransactionMaintenanceRegist001Dto>{

    @Autowired
    private SalesTransactionMaintenanceRegist001Mapper salesTransactionMaintenanceRegist001Mapper;

    /**
     * 长sql最大一次插入或者更新条数
     */
    @Value("${application.reportInsertLongCount}")
    public  int reportInsertLongCount;

    /**
     * CheckReport001Mapper
     */
    @Autowired
    private CheckReport001Mapper checkReport001Mapper;
    /**
     * sql最大一次最大删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public  int reportSelectOrDeleteCount;
    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportInsertCount}")
    public  int reportInsertCount;
    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportSelectOrDeleteCountForShenFen}")
    public  int reportSelectOrDeleteCountForShenFen;
    private static final String IS_CHECK_SUCESS="01";

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SalesTransactionMaintenanceValid salesTransactionMaintenanceValid;

    /**
     * 分页查询
     * @param param 查询条件
     * @return 返回分页信息
     * @see
     * @since 1.0
     */
    public Page<SalesTransactionMaintenanceRegist001Dto> searchForPage(Map<String, Object> param){
        dealSearchParam(param);
        if (null != param.get(GlobalConstant.PARAM_PAGE)
                && null != param.get(GlobalConstant.PARAM_LENGTH)) {
            int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
            int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
            Page<SalesTransactionMaintenanceRegist001Dto> pageList = super.queryForPage(
                    pagenum, legth, () -> salesTransactionMaintenanceRegist001Mapper.queryForRegistPage(param));
            dealDataList(pageList.getRoot());
            return pageList;
        }else{
            Page<SalesTransactionMaintenanceRegist001Dto> page = queryForPage(SalesTransactionMaintenanceRegist001Mapper.class,"queryForRegistPage",param);
            dealDataList(page.getRoot());
            return page;
        }
    }

    /**
     * 无分页查询
     * @param param
     * @return
     */
    public List<SalesTransactionMaintenanceRegist001Dto> queryForInfoRegist(Map<String,Object> param){
        return salesTransactionMaintenanceRegist001Mapper.queryForRegistPage(param);
    }

    /**
     * 手工处理
     * <一句话功能简述> <功能详细描述>
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public void updateSumStaus(Map<String, Object> map) {
        dealSearchParam(map);
        List<SalesTransactionMaintenanceRegist001Dto> chbRegistList  = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            //按条件查询全部
            chbRegistList = salesTransactionMaintenanceRegist001Mapper.searchPage(map);
        }else{
            chbRegistList = (List<SalesTransactionMaintenanceRegist001Dto>)map.get("registList");
        }
        Iterator<SalesTransactionMaintenanceRegist001Dto> it=chbRegistList.iterator();
        while(it.hasNext()){
            SalesTransactionMaintenanceRegist001Dto m =  it.next();
            if(!m.getSendStatus().equals("01")){
                it.remove();
            }
        }
        this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), chbRegistList);
    }
    /**
     * 分批次执行updateInterfSubmitAndSeq
     *
     * @param investList
     *            数据
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateInterfSubmitAndSeq(String sendStatus, List<SalesTransactionMaintenanceRegist001Dto> investList) {
        if (StringUtil.isEmptyList(investList)) {
            return;
        }
            //已报送数据进留痕表
            salesTransactionMaintenanceRegist001Mapper.insertIntoHis(investList);
            //删除正式表已报送数据
            salesTransactionMaintenanceRegist001Mapper.deleteLastSubmitRegist(investList);
            salesTransactionMaintenanceRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, investList);
            return;
    }
    /**
     * 已手工处理的数据将报送状态改为未报送
     *
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public void cancelMannul(Map<String, Object> map) {
        dealSearchParam(map);
        List<SalesTransactionMaintenanceRegist001Dto> list = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            list = salesTransactionMaintenanceRegist001Mapper.searchPage(map);
        }else{
            list = (List<SalesTransactionMaintenanceRegist001Dto>) map.get("registList");
        }
        salesTransactionMaintenanceRegist001Mapper.cancelMannultRegist(list);
        // 将履历表中最后创建时间的数据挪回登记表
        salesTransactionMaintenanceRegist001Mapper.insertReportDataFromHis(list);
        //将挪回数据从履历表中删除
        salesTransactionMaintenanceRegist001Mapper.deleteReportDataHis(list);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCellValue(String beanName, Cell cell, String propName, Object value) {
        if(StringUtil.startsWith(propName, "updateTime")) {
            Date cellValue = (Date)value;
            cell.setCellValue(DateUtil.formatDateTime(cellValue));
        }
        if(StringUtil.startsWith(propName, "submitTime")) {
            Date cellValue = (Date)value;
            cell.setCellValue(DateUtil.formatDateTime(cellValue));
        }
        if(StringUtil.startsWith(propName, "chbSubmitDeadline")) {
            Date cellValue = (Date)value;
            cell.setCellValue(DateUtil.formatDateTime(cellValue));
        }
    }

    /**
     * 校验是否所有的数据都符合
     * <功能详细描述>
     * @param upKey 参数
     * @return error 列表
     * @see
     * @since 1.0
     */
    public List<ValidMessage> checkIsNotSubmit(String upKey) {
        List<ValidMessage> errorList =new ArrayList<ValidMessage>();
        List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, upKey);
        for (String key : keys) {
            List<String> list =StringUtil.splitToList("|", key);
            Map<String , Object>map =new HashMap<String , Object>();
            map.put("institutionCode", list.get(0));
            map.put("tradeNo", list.get(2));
            map.put("noSubmit", DictCodeListExt2.报送状态_未报送.getValue());
            if(salesTransactionMaintenanceRegist001Mapper.isNotSubmitP(map)!= 0){
                errorList.add(new ValidMessage("存在未报送的数据。"));
            }
        }
        return errorList;
    }

    /**
     * 处理查询前的参数 <功能详细描述>
     *
     * @param pageParam
     * @see
     * @since 1.0
     */
    public void dealSearchParam(Map<String, Object> pageParam) {
        // 选择行数据时
        if (pageParam.containsKey("keyList")) {
            String keyList = pageParam.get("keyList").toString();
            try {
                List<SalesTransactionMaintenanceRegist001Dto> astValueList = JsonUtils.json2List(keyList,
                        SalesTransactionMaintenanceRegist001Dto.class);
                pageParam.put("registList", astValueList);
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        } else {
            // 查询参数条件
            // 数据状态
            String dataStatus = (String) pageParam.get("dataStatus");
            // 报送状态
            String sendStatus = (String) pageParam.get("sendStatus");
            // 反馈状态
            String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");


            // 将数据状态当做多选下拉框处理
            if (StringUtil.isNotBlank(dataStatus)) {
                pageParam.put("dataStatusList", StringUtil.splitToList(GlobalConstant.COMMA, dataStatus));
            }
            // 将报送状态当做多选下拉框处理
            if (StringUtil.isNotBlank(sendStatus)) {
                pageParam.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
            }
            // 将反馈状态当做多选下拉框处理
            if (StringUtil.isNotBlank(submitFeedbackStatus)) {
                pageParam.put("submitFeedbackStatusList",
                        StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
            }
        }
    }


    /**
     * 导出查询
     * @param param
     * @return
     */
    public List<SalesTransactionMaintenanceRegist001Dto> setDataList(Map<String, Object> param) {
        List<SalesTransactionMaintenanceRegist001Dto> resultList = new ArrayList<>();
        String keyList = (String) param.get("keyList");
        if (StringUtil.isNotEmpty(keyList)) {
            try {
                resultList = JsonUtils.json2List(keyList, SalesTransactionMaintenanceRegist001Dto.class);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        } else {
            if(StringUtil.isNotBlank((String)param.get("dataStatus"))) {
                String dataStatus = param.get("dataStatus").toString();
                param.put("dataStatusList", StringUtil.splitToList(GlobalConstant.COMMA, dataStatus));
            }
            resultList = salesTransactionMaintenanceRegist001Mapper.setDataList(param);
            dealDataList(resultList);
        }
        return resultList;
    }

    public void dealDataList(List<SalesTransactionMaintenanceRegist001Dto> list) {
        list.forEach(item -> {
            if (StringUtil.isNotEmpty(item.getTradePs())) {
                if(StringUtil.isNotEmpty(item.getTradePs())) {
                    item.setTradePs(item.getTradePs() + " " + SvrDataUtil.getNameFromDataDict(DictCode.销售结算资金流动方向, item.getTradePs()));
                }
                if(StringUtil.isNotEmpty(item.getTradeCcy())) {
                    item.setTradeCcy(item.getTradeCcy() + " " + SvrDataUtil.getNameFromDataDict(DictCode.币种_中债, item.getTradeCcy()));
                }
                if(StringUtil.isNotEmpty(item.getTradeType())){
                    item.setTradeType(item.getTradeType() + " " + SvrDataUtil.getNameFromDataDict(DictCode.销售结算交易类型, item.getTradeType()));
                }
            }
        });
    }


    /**
     * 根据查询条件获取所有未报送数据
     * @param param 查询条件
     * @return list 未报送数据
     * @see
     * @since 1.0
     */
    public List<SalesTransactionMaintenanceRegist001Dto> getAllUnSubmit(Map<String, Object> param) {
        param.put("sendStatus", DictCodeListExt2.报送状态_未报送.getValue());
        param.put("deptCode", CommonUtil.getDeptCode());
        List<SalesTransactionMaintenanceRegist001Dto> list= this.setDataList(param);
        return list;
    }

    /**
     * 报送前检查
     * @param list 报送前检查list
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> sendAllDataBeforeCheck(List<SalesTransactionMaintenanceRegist001Dto> list){
        List<ExcelValidDto>  checkResult = new ArrayList<ExcelValidDto>();
        if(CollectionUtils.isEmpty(list)){
            checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0),"无报送数据"));
        }
        else{
            //yc 暂时注释
            checkResult = this.dealReportCheckData(list, checkResult ,true);
            //yc 暂时注释
        }
        checkResult = checkResult.parallelStream().filter(dto -> {
            return StringUtil.equals(dto.getCheckResult(), DictCodeListExt2.检查结果_检查不通过.getCodeName());
        }).collect(Collectors.toList());
        return checkResult;
    }

    /**
     *
     * 处理报送前检查和数据检查的数据
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> dealReportCheckData(List<SalesTransactionMaintenanceRegist001Dto> equityInfoRegistList,List<ExcelValidDto> checkResult
            ,boolean flag){
        int maxCount =reportInsertCount;
        boolean isDB2DataBase = CommonUtil.isDB2DataBase();

        //存储已经批量进行过数据检查的数据
        List<String> isCheckedDataList = new ArrayList<>();
        //存储没有进行数据检查的数据
        List<SalesTransactionMaintenanceRegist001Dto> isNeedCheckDataList = new ArrayList<>();
        logger.info("检查报送流水号是否校验开始start");

        equityInfoRegistList.forEach(dto -> {
            if(!StringUtil.equals(ReportGlobalConstant.IS_UN_SUBMIT_CHECK,dto.getCheckSeqno())
                    && StringUtil.isNotEmpty(dto.getCheckSeqno())){
                isCheckedDataList.add(dto.getCheckSeqno());
            }
            else{
                isNeedCheckDataList.add(dto);
            }
        });
        logger.info("检查报送流水号是否校验结束end");

        logger.info("已检查数据查询start");
        if(CollectionUtils.isNotEmpty(isCheckedDataList)){
            //查询分批处理（每次最多处理maxCount条）
            if(isCheckedDataList.size()<maxCount){
                checkResult = checkReport001Mapper.selectByCheckSeqno(isCheckedDataList);
            }
            else{
                int part = isCheckedDataList.size()/maxCount;
                for(int j=0; j<part; j++){
                    checkResult.addAll(checkReport001Mapper.selectByCheckSeqno(isCheckedDataList.subList(0, maxCount)));
                    isCheckedDataList.subList(0, maxCount).clear();
                }
                if(CollectionUtils.isNotEmpty(isCheckedDataList)){
                    checkResult.addAll(checkReport001Mapper.selectByCheckSeqno(isCheckedDataList));
                }
            }
        }
        logger.info("已检查送数据查询end");
        if(isDB2DataBase){
            maxCount = 1;
        }
        if(CollectionUtils.isNotEmpty(isNeedCheckDataList)){
            logger.info("数据check开始");
            //yc  校验？
            List<ExcelValidDto> newCheckedInfo = salesTransactionMaintenanceValid.checkData(equityInfoRegistList,"1",null,null);
            // yc
            logger.info("数据check结束");
            checkResult.addAll(newCheckedInfo);
            //组装结果集
            List<CheckReport001Dto> resultList = new ArrayList<>();
            //销售结算资金交易信息
            List<SalesTransactionMaintenanceRegist001Dto> equityInfoRegistUpdateList = new ArrayList<>();
            logger.info("初次检查赋值start");
            newCheckedInfo.forEach(item->{
                if(flag&&!IS_CHECK_SUCESS.equals(com.joyintech.tams.report.common.util.SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()))){
                    CheckReport001Dto chbReportCheck = new CheckReport001Dto();
                    BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
//                  chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
                    chbReportCheck.setCheckSeqno(IDGenerator.generate());
                    chbReportCheck.setRepCode(DictCodeListExt2.报表名称_销售结算资金交易登记.getValue());
                    chbReportCheck.setRepName(DictCodeListExt2.报表名称_销售结算资金交易登记.getCodeName());
                    chbReportCheck.setCheckResult(com.joyintech.tams.report.common.util.SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()));
                    resultList.add(chbReportCheck);

                    //更新检查序列号对应的数据
                    SalesTransactionMaintenanceRegist001Dto dto = new SalesTransactionMaintenanceRegist001Dto();
                    dto.setRegistSeqno(item.getValidKey());
                    dto.setCheckSeqno(chbReportCheck.getCheckSeqno());
                    equityInfoRegistUpdateList.add(dto);
                }
                else if(!flag){
                    CheckReport001Dto chbReportCheck = new CheckReport001Dto();
                    BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
//                  chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
                    chbReportCheck.setCheckSeqno(IDGenerator.generate());
                    chbReportCheck.setRepCode(DictCodeListExt2.报表名称_销售结算资金交易登记.getValue());
                    chbReportCheck.setRepName(DictCodeListExt2.报表名称_销售结算资金交易登记.getCodeName());
                    chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()));
                    resultList.add(chbReportCheck);

                    //更新检查序列号对应的数据
                    SalesTransactionMaintenanceRegist001Dto dto = new SalesTransactionMaintenanceRegist001Dto();
                    dto.setRegistSeqno(item.getValidKey());
                    dto.setCheckSeqno(chbReportCheck.getCheckSeqno());
                    equityInfoRegistUpdateList.add(dto);
                }
            });
            logger.info("初次检查赋值end");
            //由于resultList与equityInfoRegistUpdateList的size相同，故size只判断一个
            if(CollectionUtils.isNotEmpty(resultList)){
                //查询分批处理（每次最多处理maxCount条）
                if(resultList.size()<maxCount){
//                  //批量更新投资者身份信息检查序列号
//                    checkReport001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
                    this.doCheckInfo(resultList,equityInfoRegistUpdateList);
                }
                else{
                    int part = resultList.size()/maxCount;
                    for(int j=0; j<part; j++){
//                      //批量更新投资者身份信息检查序列号
//                        checkReport001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList.subList(0, maxCount));
                        this.doCheckInfo(resultList.subList(0, maxCount),equityInfoRegistUpdateList.subList(0, maxCount));
                        resultList.subList(0, maxCount).clear();
                        equityInfoRegistUpdateList.subList(0, maxCount).clear();
                    }
                    if(CollectionUtils.isNotEmpty(resultList)){
//                    	//将新生成的检查信息插入报送前检查表
//                      //批量更新投资者身份信息检查序列号
//                        checkReport001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
                        this.doCheckInfo(resultList,equityInfoRegistUpdateList);
                    }
                }
            }
        }
        return checkResult;
    }

    private void doCheckInfo(List<CheckReport001Dto> resultList,List<SalesTransactionMaintenanceRegist001Dto> equityInfoRegistUpdateList){
        //将新生成的检查信息插入报送前检查表
        logger.info("单次处理报送前检查表start===============");
        checkReport001Mapper.batchInsert(resultList);

        List<String> indentifyMarkList = new ArrayList<String>();
        //组装检查通过的识别标识结果集
        resultList.parallelStream().forEach(item->{
            if(IS_CHECK_SUCESS.equals(item.getCheckResult())){
                indentifyMarkList.add(item.getValidKey());
            }
        });
        if(CollectionUtils.isNotEmpty(indentifyMarkList)){
            //同一条数据，如果报送检查通过，则将之前检查不通过的删除
            checkReport001Mapper.delReportUnCheckDatas(indentifyMarkList);
        }
        //批量更新投资者身份信息检查序列号
        // yc  暂时注释
//        equityInfoRegist001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
        logger.info("单次处理报送前检查表end===============");
    }



    /**
     * 发送销售结算资金交易信息
     *
     * @param registList 销售结算资金交易信息
     * @see
     * @since 1.0
     */
    public void sendSalesTransactionCHB(List<SalesTransactionMaintenanceRegist001Dto> registList, boolean isSendAllData) {
        if (StringUtil.isEmptyList(registList)) {
            return;
        }
        // 处理已经报送的数据（将已报送数据移至履历表，并删除登记表数据）
        logger.info("handleAlreadySubmitData处理开始：" + DateUtil.getCurrentDateTime());
        this.handleAlreadySubmitData(registList);
        logger.info("handleAlreadySubmitData处理结束：" + DateUtil.getCurrentDateTime());

        int sendCbMaxDataCount = Integer.valueOf(
                SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_SENDCB_MAX_COUNT));

        int dataCount = registList.size();
        if (dataCount <= sendCbMaxDataCount) {
            logger.info("单次sendSalesTransactionCHBOneFile处理开始：" + DateUtil.getCurrentDateTime());
            this.sendSalesTransactionCHBOneFile(registList, isSendAllData);
            logger.info("单次sendSalesTransactionCHBOneFile处理结束：" + DateUtil.getCurrentDateTime());
            return;
        }

        // 分批处理
        List<SalesTransactionMaintenanceRegist001Dto> newDataList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        newDataList.addAll(registList);

        int part = dataCount / sendCbMaxDataCount; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("单次sendSalesTransactionCHBOneFile处理开始：" + DateUtil.getCurrentDateTime());
            this.sendSalesTransactionCHBOneFile(newDataList.subList(0, sendCbMaxDataCount), isSendAllData);
            logger.info("单次sendSalesTransactionCHBOneFile处理结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, sendCbMaxDataCount).clear();
        }
        // 最后剩下的数据
        if (!StringUtil.isEmptyList(newDataList)) {
            logger.info("单次sendSalesTransactionCHBOneFile处理开始：" + DateUtil.getCurrentDateTime());
            this.sendSalesTransactionCHBOneFile(newDataList, isSendAllData);
            logger.info("单次sendSalesTransactionCHBOneFile处理结束：" + DateUtil.getCurrentDateTime());
        }
    }



    /**
     *
     * 销售结算资金交易数据(单文件最大1000000条数据)
     * @param registList 发送销售结算资金交易数据
     * @see
     * @since 1.0
     */
    private void sendSalesTransactionCHBOneFile(List<SalesTransactionMaintenanceRegist001Dto> registList, boolean isSendAllData) {
//        logger.info("拼装报送对象处理开始：" + DateUtil.getCurrentDateTime());
//
//        String defaultPhone = "11111111111";
//        String empt="无";
//        String defaultTel="11111111";
//        String defaultEmail="11@11.com";
//        //元素名称
//        String title = "    wemax_RegistrationBankCode|wemax_InvestorAffiliatedWithIssuingBankPleaseSelect|wemax_InvestorsBankName"
//                + "|wemax_InvestorsBankCode|wemax_InvestorAffiliatedWithRegion|wemax_InvestorsCountryOrRegion|wemax_DataType"
//                + "|wemax_OriginalDiscriminationId|wemax_DiscriminationId|wemax_InvestorClassification|wemax_PersonalIdType"
//                + "|wemax_InstitutionalIdType|wemax_OtherIdName|wemax_IdNumber|wemax_SpvFundCustodianBank|wemax_OtherFundCustodianBank"
//                //去掉spv登记编码
//                /*+ "|wemax_SpvRegistrationCode"*/
//                + "|wemax_InvestorName|wemax_Gender|wemax_RiskPreference|wemax_MobilePhoneNumber"
//                + "|wemax_WorkPhoneNumber|wemax_EmailAddress|wemax_InvestorsInformationRegistrationDetails|wemax_RegistrationSerialNumber";
//        //元素字符串结果集
//        StringBuffer info = new StringBuffer("\n"+"    <![CDATA["+'\n');
//        info.append(title);
//        info.append('\n');
//        List<String> registSeq = new ArrayList<String>();
//        Map<String, String> registSeqKeyMap = new HashMap<String, String>();
//        //后期去除
////       ChbSubmitStatus chbSubmitStatus = null;
////       List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
//        //报送表中无报送流水号list
//        List<EquityInfoRegist001Dto> unSubmitSeqList = new ArrayList<EquityInfoRegist001Dto>();
//        if(CollectionUtils.isNotEmpty(registList)){
//            //仅支持录入11 位定长数字，不得填写“无”。如无手机号码，应统一填写“11111111111”（11个 1）。
//            //            不得填写“无”，如无固定电话，应统一填写“11111111”（8 个1）。
//            //            不得填写“无”，如无固定电话，应统一填写“11@11.com”。
//
//            List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, registList.size());
//            int index = 0;
//            for (EquityInfoRegist001Dto dto : registList) {
//                //针对取消手工处理再报送
//                String currRegistSeq = "";
//                if(DictCodeListExt2.报送反馈状态_发送失败.getValue().equals(dto.getSubmitFeedbackStatusCode())
//                        ||DictCodeListExt2.报送反馈状态_中债退回.getValue().equals(dto.getSubmitFeedbackStatusCode())){
////                   currRegistSeq = CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
//                    currRegistSeq = CommonUtil.getSequenceStr(sequenceList, index);
//                    index++;
//                    registSeq.add(currRegistSeq);
//                    //将报送流水为空的放入list
//                    dto.setSumitSeq(currRegistSeq);
//                    unSubmitSeqList.add(dto);
//                }
//                else if(StringUtil.isEmpty(dto.getSumitSeq())){
////                   currRegistSeq = CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
//                    currRegistSeq = CommonUtil.getSequenceStr(sequenceList, index);
//                    index++;
//                    registSeq.add(currRegistSeq);
//                    //将报送流水为空的放入list
//                    dto.setSumitSeq(currRegistSeq);
//                    unSubmitSeqList.add(dto);
//                }
//                else{
//                    registSeq.add(dto.getSumitSeq());
//                    currRegistSeq = dto.getSumitSeq();
//                }
//                String  value = "";
//                //当为识别标识变更时
//                if(dto.getType().equals(
//                        EquityInfoRegistCommonUtil.getDictCodeAndName(DictCode.数据类型_中债, DictCodeListExt2.数据类型_中债_识别标识变更.getValue()))){
//                    value = ReportGlobalConstant.MANAY_SPACE+dto.getBankCode()+ReportGlobalConstant.VERTICAL_LINE+dto.getIsBelongBank()
//                            +ReportGlobalConstant.VERTICAL_LINE+dto.getTheBankName()+ReportGlobalConstant.VERTICAL_LINE+dto.getTheBankCode()
//                            +ReportGlobalConstant.VERTICAL_LINE+dto.getHolderMark()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderRegion()
//                            +ReportGlobalConstant.VERTICAL_LINE+dto.getType()+ReportGlobalConstant.VERTICAL_LINE+dto.getOrgIndentifyMark()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getIndentifyMark()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderType()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getPersonalIdentifyType()+ReportGlobalConstant.VERTICAL_LINE+dto.getInstitutionIdentifyType()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getOtherIdentifyName()+ReportGlobalConstant.VERTICAL_LINE+dto.getIdentifyNumber()+ReportGlobalConstant.VERTICAL_LINE+dto.getSpvCapAccBank()
//                            +ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getOtherCapAccBank()+ReportGlobalConstant.VERTICAL_LINE/*+dto.getSpvRegistCode()+"|"*/
//                            //要求原则上境内投资者名称中不得含有空格。
//                            +(dto.getHolderName().replaceAll(Pattern.quote(StringUtil.EMPTY), ""))+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getHolderSex()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderRiskPrefer()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getHolderCellphone()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderTelephone()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getHolderEmail()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getRemark()+ReportGlobalConstant.VERTICAL_LINE+dto.getSumitSeq();
//                }
//                else {
//                    value = ReportGlobalConstant.MANAY_SPACE+dto.getBankCode()+ReportGlobalConstant.VERTICAL_LINE+dto.getIsBelongBank()
//                            +ReportGlobalConstant.VERTICAL_LINE+dto.getTheBankName()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getTheBankCode()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderMark()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getHolderRegion()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getType()+ReportGlobalConstant.VERTICAL_LINE+ReportGlobalConstant.VERTICAL_LINE+dto.getIndentifyMark()
//                            +ReportGlobalConstant.VERTICAL_LINE+dto.getHolderType()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getPersonalIdentifyType()+ReportGlobalConstant.VERTICAL_LINE+dto.getInstitutionIdentifyType()
//                            +ReportGlobalConstant.VERTICAL_LINE+dto.getOtherIdentifyName()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getIdentifyNumber()+ReportGlobalConstant.VERTICAL_LINE+dto.getSpvCapAccBank()+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getOtherCapAccBank()+ReportGlobalConstant.VERTICAL_LINE/*+dto.getSpvRegistCode()+"|"*/
//                            //要求原则上境内投资者名称中不得含有空格。
//                            +(dto.getHolderName().replaceAll(Pattern.quote(StringUtil.EMPTY), ""))+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getHolderSex()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderRiskPrefer()+ReportGlobalConstant.VERTICAL_LINE
//                            +(StringUtil.equals(dto.getHolderCellphone(), empt)||StringUtil.isEmpty(dto.getHolderCellphone())?defaultPhone:dto.getHolderCellphone())+ReportGlobalConstant.VERTICAL_LINE
//                            +(StringUtil.equals(dto.getHolderTelephone(), empt)||StringUtil.isEmpty(dto.getHolderTelephone())?defaultTel:dto.getHolderTelephone())+ReportGlobalConstant.VERTICAL_LINE
//                            +(StringUtil.equals(dto.getHolderEmail(), empt)||StringUtil.isEmpty(dto.getHolderEmail())?defaultEmail:dto.getHolderEmail())+ReportGlobalConstant.VERTICAL_LINE
//                            +dto.getRemark()+ReportGlobalConstant.VERTICAL_LINE+dto.getSumitSeq();
//                }
////               value = "    "+dto.getBankCode()+"|"+dto.getIsBelongBank()+"|"+dto.getTheBankName()+"|"
////                       +dto.getTheBankCode()+"|"+dto.getHolderMark()+"|"+dto.getHolderRegion()+"|"
////                       +dto.getType()+"|"+dto.getOrgIndentifyMark()+"|"+dto.getIndentifyMark()+"|"
////                       +dto.getHolderType()+"|"+dto.getPersonalIdentifyType()+"|"+dto.getInstitutionIdentifyType()+"|"
////                       +dto.getOtherIdentifyName()+"|"+dto.getIdentifyNumber()+"|"+dto.getSpvCapAccBank()+"|"
////                       +dto.getOtherCapAccBank()+"|"/*+dto.getSpvRegistCode()+"|"*/
////                       //要求原则上境内投资者名称中不得含有空格。
////                       +(dto.getHolderName().replaceAll(StringUtil.EMPTY, ""))+"|"
////                       +dto.getHolderSex()+"|"+dto.getHolderRiskPrefer()+"|";
//                info.append(value);
////               String value1="";
////               if(dto.getType().equals(
////                     EquityInfoRegistCommonUtil.getDictCodeAndName(DictCode.权益人数据类型, DictCodeListExt2.数据类型_中债_识别标识变更.getValue()))){
////                 value1 = dto.getHolderCellphone()+"|"+dto.getHolderTelephone()+"|"+dto.getHolderEmail()+"|";
////                 }
////               else{
////                 value1 = (StringUtil.equals(dto.getHolderCellphone(), empt)||StringUtil.isEmpty(dto.getHolderCellphone())?defaultPhone:dto.getHolderCellphone())+"|"
////                 +(StringUtil.equals(dto.getHolderTelephone(), empt)||StringUtil.isEmpty(dto.getHolderTelephone())?defaultTel:dto.getHolderTelephone())+"|"
////                    +(StringUtil.equals(dto.getHolderEmail(), empt)||StringUtil.isEmpty(dto.getHolderEmail())?defaultEmail:dto.getHolderEmail())+"|";
////                 }
////               info.append(value1).append(dto.getRemark()).append("|").append(dto.getSumitSeq());
//                info.append('\n');
//
//                registSeqKeyMap.put(currRegistSeq, dto.getIndentifyMark());
//
//                //报送状态表插入组装
////               chbSubmitStatus = new ChbSubmitStatus();
////               chbSubmitStatus.setSeqno(dto.getSumitSeq());
////               chbSubmitStatus.setSubmitFeedbackStatus("01");
////               chbSubmitStatus.setSubmitType(DictCodeList.报表名称_投资者身份信息登记.getValue());
////               listStatus.add(chbSubmitStatus);
//            }
//        }
//        info.append("    ]]>");
//        info.append('\n');
//        logger.info("拼装报送对象处理结束：" + DateUtil.getCurrentDateTime());
//        //更新报送状态表
////       this.doUpdateSubmtitStatus(registList,listStatus);
//        logger.info("doUpdateSubmtitStatus处理开始：" + DateUtil.getCurrentDateTime());
//        this.doUpdateSubmtitStatus(registList,unSubmitSeqList, isSendAllData);
//        logger.info("doUpdateSubmtitStatus处理结束：" + DateUtil.getCurrentDateTime());
//        //将格式转换为中债报送的固定格式
//        List<InterfCnBondSubmitPara> paras = new ArrayList<InterfCnBondSubmitPara>();
//        InterfCnBondSubmitPara it = new InterfCnBondSubmitPara();
//        List<Map<String, CnBondInterfItem>> listMap = new ArrayList<Map<String, CnBondInterfItem>>();
//        CnBondInterfItem cnBondInterfItem = new CnBondInterfItem();
//        cnBondInterfItem.setValue(info.toString());
//        Map<String, CnBondInterfItem> maps =  new HashMap<String,CnBondInterfItem>();
//        maps.put("InvestorsInformationRegistrationCSVDataExplanatory", cnBondInterfItem);
//        listMap.add(maps);
//        it.setDealType(DictCodeListExt2.报表名称_投资者身份信息登记.getValue());
//        it.setInfos(listMap);
//        it.setSysDate(DateUtil.now());
//        it.setRegistSeq(registSeq);
//        it.setRegistBankCode(registList.get(0).getBankCode());
//
//        //将流水号和识别标识键值对存入InterfCnBondSubmitPara
//        it.setRegistSeqKeyMap(registSeqKeyMap);
//
//        paras.add(it);
//        logger.info("sendCnBond处理开始：" + DateUtil.getCurrentDateTime());
//        cnBondInterfService.sendCnBond(paras);
//        logger.info("sendCnBond处理结束：" + DateUtil.getCurrentDateTime());
    }


    /**
     * 处理已经报送的数据（将已报送数据移至履历表，并删除登记表数据）
     *
     * @param equiytRegistList 数据列表
     * @see
     * @since 1.0
     */
    public void handleAlreadySubmitData(List<SalesTransactionMaintenanceRegist001Dto> equiytRegistList) {
        logger.info("queryAlreadyRegistSubmitList处理开始：" + DateUtil.getCurrentDateTime());
        List<SalesTransactionMaintenanceRegist001Dto> unSub = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        for (SalesTransactionMaintenanceRegist001Dto salesTransactionMaintenanceRegist001Dto : equiytRegistList) {
            if(DictCodeListExt2.报送状态_未报送.getValue().equals(salesTransactionMaintenanceRegist001Dto.getSendStatus())){
                unSub.add(salesTransactionMaintenanceRegist001Dto);
            }
        }
        List<SalesTransactionMaintenanceRegist001Dto> list = this.queryAlreadyRegistSubmitList(unSub);
        logger.info("queryAlreadyRegistSubmitList处理结束：" + DateUtil.getCurrentDateTime());
        if(CollectionUtils.isNotEmpty(list)){
            //讲第一次报送的数据数据履历表
            logger.info("insertSalesRegistHisBatch处理开始：" + DateUtil.getCurrentDateTime());
            this.insertSalesRegistHisBatch(list);
            logger.info("insertSalesRegistHisBatch处理结束：" + DateUtil.getCurrentDateTime());
            //删除已报送数据
            logger.info("deleteSubmitRegistBatch处理开始：" + DateUtil.getCurrentDateTime());
            this.deleteSubmitRegistBatch(list);
            logger.info("deleteSubmitRegistBatch处理结束：" + DateUtil.getCurrentDateTime());
        }
    }

    /**
     * 查询已报送数据
     *
     * @param equiytRegistList equiytRegistList
     * @return 已报送数据
     * @see
     * @since 1.0
     */
    private List<SalesTransactionMaintenanceRegist001Dto> queryAlreadyRegistSubmitList(List<SalesTransactionMaintenanceRegist001Dto> equiytRegistList) {
        List<SalesTransactionMaintenanceRegist001Dto> equiytRegistResultList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();

        if (StringUtil.isEmptyList(equiytRegistList)) {
            return equiytRegistResultList;
        }

        if (equiytRegistList.size() <= 100000) {
            return this.queryRegistSubmitListByKeysBatch(equiytRegistList);
        }

        Map<String, String> dataKeyMap = equiytRegistList.stream().collect(
                JoyinCollectors.toMap(
                        x -> x.getInstitutionCode() + GlobalConstant.UNDER_LINE + x.getRegistSeqno(),
                        x -> x.getInstitutionCode() + GlobalConstant.UNDER_LINE + x.getRegistSeqno()));

        logger.info("queryAllRegistSubmitList处理开始：" + DateUtil.getCurrentDateTime());
        List<SalesTransactionMaintenanceRegist001Dto> allRegistDataList = salesTransactionMaintenanceRegist001Mapper.queryAllRegistSubmitList();
        logger.info("queryAllRegistSubmitList处理结束：" + DateUtil.getCurrentDateTime());

        List<SalesTransactionMaintenanceRegist001Dto> resultList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        for (SalesTransactionMaintenanceRegist001Dto tempDataDto : allRegistDataList) {
            String dataKey = tempDataDto.getInstitutionCode() + GlobalConstant.UNDER_LINE + tempDataDto.getRegistSeqno();
            if (dataKeyMap.containsKey(dataKey)) {
                resultList.add(tempDataDto);
            }
        }

        return resultList;
    }



    /**
     * 分批次执行queryRegistSubmitListByKeys
     *
     * @param equiytRegistList 数据
     * @see
     * @since 1.0
     */
    private List<SalesTransactionMaintenanceRegist001Dto> queryRegistSubmitListByKeysBatch(List<SalesTransactionMaintenanceRegist001Dto> equiytRegistList) {
        List<SalesTransactionMaintenanceRegist001Dto> equiytRegistResultList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        if (StringUtil.isEmptyList(equiytRegistList)) {
            return equiytRegistResultList;
        }
        int maxDataCountOneTime = reportSelectOrDeleteCount;
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
            equiytRegistResultList.addAll(salesTransactionMaintenanceRegist001Mapper.queryRegistSubmitListByKeys(equiytRegistList));
            return equiytRegistResultList;
        }

        List<SalesTransactionMaintenanceRegist001Dto> newDataList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("queryRegistSubmitListByKeys单次查询处理开始：" + DateUtil.getCurrentDateTime());
            equiytRegistResultList.addAll(salesTransactionMaintenanceRegist001Mapper.queryRegistSubmitListByKeys(newDataList.subList(0, maxDataCountOneTime)));
            logger.info("queryRegistSubmitListByKeys单次查询处理结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equiytRegistResultList.addAll(salesTransactionMaintenanceRegist001Mapper.queryRegistSubmitListByKeys(newDataList));
        }
        return equiytRegistResultList;
    }

    /**
     * 分批次执行insertEquityRegistHis
     *
     * @param equiytRegistList 数据
     * @see
     * @since 1.0
     */
    private void insertSalesRegistHisBatch(List<SalesTransactionMaintenanceRegist001Dto> equiytRegistList) {
        if (StringUtil.isEmptyList(equiytRegistList)) {
            return;
        }

        int maxDataCountOneTime = reportInsertLongCount;
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
            salesTransactionMaintenanceRegist001Mapper.insertIntoHis(equiytRegistList);;
            return;
        }

        List<SalesTransactionMaintenanceRegist001Dto> newDataList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            salesTransactionMaintenanceRegist001Mapper.insertIntoHis(newDataList.subList(0, maxDataCountOneTime));;
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            salesTransactionMaintenanceRegist001Mapper.insertIntoHis(newDataList);;
        }
    }


    /**
     * 分批次执行deleteSubmitRegist
     *
     * @param equiytRegistList 数据
     * @see
     * @since 1.0
     */
    private void deleteSubmitRegistBatch(List<SalesTransactionMaintenanceRegist001Dto> equiytRegistList) {
        if (StringUtil.isEmptyList(equiytRegistList)) {
            return;
        }

        int maxDataCountOneTime = reportSelectOrDeleteCount;
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
            salesTransactionMaintenanceRegist001Mapper.deleteLastSubmitRegist(equiytRegistList);;
            return;
        }

        List<SalesTransactionMaintenanceRegist001Dto> newDataList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            salesTransactionMaintenanceRegist001Mapper.deleteLastSubmitRegist(newDataList.subList(0, maxDataCountOneTime));;
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            salesTransactionMaintenanceRegist001Mapper.deleteLastSubmitRegist(newDataList);;
        }
    }
}
