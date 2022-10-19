package com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.service;

import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.component.svrreport.chb.dto.SalesTransactionMaintenanceSubDto;
import com.joyintech.component.svrreport.chb.service.MsgSendService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.ChbOrgRegistBankCodeMapper;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
//import com.joyintech.tams.report.chbsd.salestransactionmaintenancesub.service.SalesTransactionMaintenanceBatchService;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.validate.SalesTransactionMaintenanceValid;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
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

//    @Autowired(required = false)
//    private SalesTransactionMaintenanceBatchService salesTransactionMaintenanceBatchServiceService;

    @Autowired
    private MsgSendService msgSend;

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
//        salesTransactionMaintenanceRegist001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
        logger.info("单次处理报送前检查表end===============");
    }



    /**
     * 发送销售结算资金交易信息
     *
     * @param registList 销售结算资金交易信息
     * @see
     * @since 1.0
     */
    public void sendSalesTransactionCHB(List<SalesTransactionMaintenanceRegist001Dto> registList, boolean isSendAllData,SendDataCheckType sendDataCheckType) throws Exception {
        if (StringUtil.isEmptyList(registList)) {
            return;
        }
        // 处理已经报送的数据（将已报送数据移至履历表，并删除登记表数据）
        logger.info("handleAlreadySubmitData处理开始：" + DateUtil.getCurrentDateTime());
        this.handleAlreadySubmitData(registList);
        logger.info("handleAlreadySubmitData处理结束：" + DateUtil.getCurrentDateTime());

        //处理报送表中无报送流水号list
        logger.info("doUpdateSubmtitStatus处理开始：" + DateUtil.getCurrentDateTime());
        this.doUpdateSubmtitStatus(registList, isSendAllData);
        logger.info("doUpdateSubmtitStatus处理结束：" + DateUtil.getCurrentDateTime());

        int sendCbMaxDataCount = Integer.valueOf(
                SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_SENDCB_MAX_COUNT));

        //类型转换
        List<SalesTransactionMaintenanceSubDto> dataList = new ArrayList<SalesTransactionMaintenanceSubDto>();

        for(SalesTransactionMaintenanceRegist001Dto sdDto:registList){
            SalesTransactionMaintenanceSubDto target = new SalesTransactionMaintenanceSubDto();
            BeanUtil.copyPropertiesIgnoreNull(target, sdDto);
            dataList.add(target);
        }
        //查询银行登记代码
        String registBankCode=salesTransactionMaintenanceRegist001Mapper.selectRegistBankCode();
        dataList.get(0).setRegistBankCode(registBankCode);

        int dataCount = dataList.size();
        if (dataCount <= sendCbMaxDataCount) {
            logger.info("销售结算资金交易登记报文报送开始：" + DateUtil.getCurrentDateTime());
//            salesTransactionMaintenanceBatchServiceService.sendSalesTransactionCHBOneFile(registList,sendDataCheckType);
            msgSend.sendSalesTransactionMaintenanceRegistSubMsgs(dataList,sendDataCheckType);
            logger.info("销售结算资金交易登记报文报送结束：" + DateUtil.getCurrentDateTime());
            return;
        }

        // 分批处理
        List<SalesTransactionMaintenanceSubDto> newDataList = new ArrayList<SalesTransactionMaintenanceSubDto>();
        newDataList.addAll(dataList);

        int part = dataCount / sendCbMaxDataCount; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("销售结算资金交易登记报文报送开始：" + DateUtil.getCurrentDateTime());
//            salesTransactionMaintenanceBatchServiceService.sendSalesTransactionCHBOneFile(newDataList.subList(0, sendCbMaxDataCount), sendDataCheckType);
            msgSend.sendSalesTransactionMaintenanceRegistSubMsgs(newDataList.subList(0, sendCbMaxDataCount),sendDataCheckType);
            logger.info("销售结算资金交易登记报文报送结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, sendCbMaxDataCount).clear();
        }
        // 最后剩下的数据
        if (!StringUtil.isEmptyList(newDataList)) {
            logger.info("销售结算资金交易登记报文报送开始：" + DateUtil.getCurrentDateTime());
//            salesTransactionMaintenanceBatchServiceService.sendSalesTransactionCHBOneFile(newDataList, sendDataCheckType);
            msgSend.sendSalesTransactionMaintenanceRegistSubMsgs(newDataList, sendDataCheckType);
            logger.info("销售结算资金交易登记报文报送结束：" + DateUtil.getCurrentDateTime());
        }
    }

    /**
     * 获取报送列表
     * @param equiytRegistList 条件
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void doUpdateSubmtitStatus(List<SalesTransactionMaintenanceRegist001Dto> equiytRegistList,boolean isSendAllData){
        //报送表中无报送流水号list
        List<SalesTransactionMaintenanceRegist001Dto> unSubmitSeqList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, equiytRegistList.size());
        int index = 0;

        List<SalesTransactionMaintenanceRegist001Dto> unSub = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();

        for (SalesTransactionMaintenanceRegist001Dto dto : equiytRegistList) {
            //针对取消手工处理再报送
            String currRegistSeq = "";
            if(DictCodeListExt2.报送反馈状态_发送失败.getValue().equals(dto.getSubmitFeedbackStatus())
                    ||DictCodeListExt2.报送反馈状态_中债退回.getValue().equals(dto.getSubmitFeedbackStatus())
                    ||StringUtil.isEmpty(dto.getSumitSeq())){
                currRegistSeq = CommonUtil.getSequenceStr(sequenceList, index);
                index++;
                //将报送流水为空的放入list
                dto.setSumitSeq(currRegistSeq);
                unSubmitSeqList.add(dto);
            }

            if(DictCodeListExt2.报送状态_未报送.getValue().equals(dto.getSendStatus())){
                unSub.add(dto);
            }
        }

        logger.info("updateInterfSubmitBatch处理开始：" + DateUtil.getCurrentDateTime());
        this.updateInterfSubmitBatch(DictCodeListExt2.报送状态_接口报送.getValue(), unSub, isSendAllData);
        logger.info("updateInterfSubmitBatch处理结束：" + DateUtil.getCurrentDateTime());

        logger.info("updateInterfSubmitSeq处理开始：" + DateUtil.getCurrentDateTime());
        this.updateInterfSubmitSeq(unSubmitSeqList);
        logger.info("updateInterfSubmitSeq处理结束：" + DateUtil.getCurrentDateTime());
    }

    /**
     * 分批次执行updateInterfSubmitBatch
     *
     * @param salesTransactionMaintenanceRegistList 数据
     * @see
     * @since 1.0
     */
    private void updateInterfSubmitBatch(String sendStatus, List<SalesTransactionMaintenanceRegist001Dto> salesTransactionMaintenanceRegistList, boolean isSendAllData) {
        if (StringUtil.isEmptyList(salesTransactionMaintenanceRegistList)) {
            return;
        }

        int maxDataCountOneTime = reportSelectOrDeleteCount;
        int dataCount = salesTransactionMaintenanceRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
            logger.info("updateInterfSubmit单次处理开始：" + DateUtil.getCurrentDateTime());
            salesTransactionMaintenanceRegist001Mapper.updateInterfSubmit(sendStatus,salesTransactionMaintenanceRegistList);
            logger.info("updateInterfSubmit单次处理结束：" + DateUtil.getCurrentDateTime());
            return;
        }

        // 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
        if (isSendAllData) {
            logger.info("updateAllInterfSubmit处理开始："
                    + DateUtil.getCurrentDateTime());
            salesTransactionMaintenanceRegist001Mapper.updateAllInterfSubmit(sendStatus);
            logger.info("updateAllInterfSubmit处理结束："
                    + DateUtil.getCurrentDateTime());

            return;
        }

        List<SalesTransactionMaintenanceRegist001Dto> newDataList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        newDataList.addAll(salesTransactionMaintenanceRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("updateInterfSubmit单次处理开始：" + DateUtil.getCurrentDateTime());
            salesTransactionMaintenanceRegist001Mapper.updateInterfSubmit(sendStatus,newDataList.subList(0, maxDataCountOneTime));;
            logger.info("updateInterfSubmit单次处理结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            logger.info("updateInterfSubmit单次处理开始：" + DateUtil.getCurrentDateTime());
            salesTransactionMaintenanceRegist001Mapper.updateInterfSubmit(sendStatus,newDataList);
            logger.info("updateInterfSubmit单次处理结束：" + DateUtil.getCurrentDateTime());
        }
    }

    /**
     * 分批次执行updateInterfSubmitSeq
     *
     * @param salesTransactionMaintenanceRegistList 数据
     * @see
     * @since 1.0
     */
    private void updateInterfSubmitSeq(List<SalesTransactionMaintenanceRegist001Dto> salesTransactionMaintenanceRegistList) {
        if (StringUtil.isEmptyList(salesTransactionMaintenanceRegistList)) {
            return;
        }

        int maxDataCountOneTime = reportInsertCount;
        boolean isDB2DataBase = CommonUtil.isDB2DataBase();
        if (isDB2DataBase) {
            maxDataCountOneTime = 1;
        }
        int dataCount = salesTransactionMaintenanceRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
            logger.info("updateInterfSubmitSeq单次处理开始：" + DateUtil.getCurrentDateTime());
            salesTransactionMaintenanceRegist001Mapper.updateInterfSubmitSeq(salesTransactionMaintenanceRegistList);
            logger.info("updateInterfSubmitSeq单次处理结束：" + DateUtil.getCurrentDateTime());
            return;
        }

        List<SalesTransactionMaintenanceRegist001Dto> newDataList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        newDataList.addAll(salesTransactionMaintenanceRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("updateInterfSubmitSeq单次处理开始：" + DateUtil.getCurrentDateTime());
            salesTransactionMaintenanceRegist001Mapper.updateInterfSubmitSeq(newDataList.subList(0, maxDataCountOneTime));;
            logger.info("updateInterfSubmitSeq单次处理结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            logger.info("updateInterfSubmitSeq单次处理开始：" + DateUtil.getCurrentDateTime());
            salesTransactionMaintenanceRegist001Mapper.updateInterfSubmitSeq(newDataList);
            logger.info("updateInterfSubmitSeq单次处理结束：" + DateUtil.getCurrentDateTime());
        }
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
            salesTransactionMaintenanceRegist001Mapper.insertIntoHis(equiytRegistList);
            return;
        }

        List<SalesTransactionMaintenanceRegist001Dto> newDataList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            salesTransactionMaintenanceRegist001Mapper.insertIntoHis(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            salesTransactionMaintenanceRegist001Mapper.insertIntoHis(newDataList);
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
            salesTransactionMaintenanceRegist001Mapper.deleteLastSubmitRegist(equiytRegistList);
            return;
        }

        List<SalesTransactionMaintenanceRegist001Dto> newDataList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            salesTransactionMaintenanceRegist001Mapper.deleteLastSubmitRegist(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            salesTransactionMaintenanceRegist001Mapper.deleteLastSubmitRegist(newDataList);
        }
    }

    /**
     * 中债直连报送前check
     *
     *传入需要check的主键
     * @return 反馈信息
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> submitBeforeCheck(Map<String, Object> param) {
        List<ExcelValidDto> result=new ArrayList<ExcelValidDto>();
        dealSearchParam(param);
        // 权益人报送表主键
        @SuppressWarnings("unchecked")
        List<SalesTransactionMaintenanceRegist001Dto> investorsHoldInfoRegistList = (List<SalesTransactionMaintenanceRegist001Dto>)param.get(
                "investList");
        if (investorsHoldInfoRegistList == null || investorsHoldInfoRegistList.isEmpty()) {
            investorsHoldInfoRegistList = salesTransactionMaintenanceRegist001Mapper.queryForRegistPage(param);
        }
        if(!StringUtil.isEmptyList(result)){
            return result;
        }
        return result;
    }

    public Map<String, List<SalesTransactionMaintenanceRegist001Dto>> getSubmitList(Map<String, Object> map) {
        //返回map
        Map<String, List<SalesTransactionMaintenanceRegist001Dto>> resultMap = new HashMap<>();
        List<SalesTransactionMaintenanceRegist001Dto> resultList = null;
        //处理数据List
        List<SalesTransactionMaintenanceRegist001Dto> list = this.setDataList(map);
        if (CollectionUtils.isNotEmpty(list)){
            //format(list);
            for(SalesTransactionMaintenanceRegist001Dto item : list){
                if (CollectionUtils.isEmpty(resultMap.get(item.getPrdRegistCode()))) {
                    resultList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
                } else {
                    resultList = resultMap.get(item.getPrdRegistCode());
                }
                resultList.add(item);
                resultMap.put(item.getPrdRegistCode(), resultList);
            }
        }
        return resultMap;
    }
}
