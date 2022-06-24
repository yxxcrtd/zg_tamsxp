package com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.salerrewpenainforegist.dao.ChbSalerRewPenaInfoRegistMapper;
import com.joyintech.tams.report.submitmanagement.salerrewpenainforegist.dto.SalerRewPenaInfoRegistDto;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dao.SalesTransactionMaintenanceRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto.SalesTransactionMaintenanceRegist001Dto;
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
                item.setTradePs(item.getTradePs() + " " + SvrDataUtil.getNameFromDataDict(DictCode.销售结算资金流动方向, item.getTradePs()));
                item.setTradeCcy(item.getTradeCcy() + " " + SvrDataUtil.getNameFromDataDict(DictCode.币种_中债, item.getTradeCcy()));
            }
        });
    }
}
