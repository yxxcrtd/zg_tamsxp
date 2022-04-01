package com.joyintech.tams.report.submitmanagement.salerinstinforegist.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.system.common.listener.UploadListener;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.salerideninforegist.dao.ChbSalerIdenInfoRegistMapper;
import com.joyintech.tams.report.submitmanagement.salerideninforegist.dto.SalerIdenInfoRegistDto;
import com.joyintech.tams.report.submitmanagement.salerinstinforegist.dao.ChbSalerInstInfoRegistMapper;
import com.joyintech.tams.report.submitmanagement.salerinstinforegist.dto.SalerInstInfoRegistDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2017年9月19日
 * @see SalerInstInfoRegist001Service
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SalerInstInfoRegist001Service extends CommonExportService<SalerInstInfoRegistDto>{

    @Autowired
    private ChbSalerInstInfoRegistMapper chbSalerInstInfoRegistMapper;
    @Autowired
    private UploadListener uploadListener;
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
    public Page<SalerInstInfoRegistDto> searchForPage(Map<String, Object> param){
        if(StringUtil.isNotBlank((String)param.get("status"))) {
            String status = param.get("status").toString();
            param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
        }
        if (null != param.get(GlobalConstant.PARAM_PAGE)
                && null != param.get(GlobalConstant.PARAM_LENGTH)) {
            int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
            int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
            Page<SalerInstInfoRegistDto> pageList = super.queryForPage(
                    pagenum, legth, () -> chbSalerInstInfoRegistMapper.queryForRegistPage(param));
            this.formatByType(pageList.getRoot());
            return pageList;
        }else{
            Page<SalerInstInfoRegistDto> page = queryForPage(ChbSalerInstInfoRegistMapper.class,"queryForRegistPage",param);
            return page;
        }
    }

    /**
     * 无分页查询
     * @param param
     * @return
     */
    public List<SalerInstInfoRegistDto> queryForInfoRegist(Map<String,Object> param){
        return chbSalerInstInfoRegistMapper.queryForRegistPage(param);
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
        List<SalerInstInfoRegistDto> chbRegistList  = new ArrayList<SalerInstInfoRegistDto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            //按条件查询全部
            chbRegistList = chbSalerInstInfoRegistMapper.searchPage(map);
        }else{
            chbRegistList = (List<SalerInstInfoRegistDto>)map.get("registList");
        }
        Iterator<SalerInstInfoRegistDto> it=chbRegistList.iterator();
        while(it.hasNext()){
            SalerInstInfoRegistDto m =  it.next();
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
    public void updateInterfSubmitAndSeq(String sendStatus, List<SalerInstInfoRegistDto> investList) {
        if (StringUtil.isEmptyList(investList)) {
            return;
        }
            //已报送数据进留痕表
            chbSalerInstInfoRegistMapper.insertIntoHis(investList);
            //删除正式表已报送数据
            chbSalerInstInfoRegistMapper.deleteLastSubmitRegist(investList);
            chbSalerInstInfoRegistMapper.updateInterfSubmitAndSeqBatch(sendStatus, investList);
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
        List<SalerInstInfoRegistDto> list = new ArrayList<SalerInstInfoRegistDto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            list = chbSalerInstInfoRegistMapper.searchPage(map);
        }else{
            list = (List<SalerInstInfoRegistDto>) map.get("registList");
        }
        chbSalerInstInfoRegistMapper.cancelMannultRegist(list);
        // 将履历表中最后创建时间的数据挪回登记表
        chbSalerInstInfoRegistMapper.insertReportDataFromHis(list);
        //将挪回数据从履历表中删除
        chbSalerInstInfoRegistMapper.deleteReportDataHis(list);
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
                List<SalerInstInfoRegistDto> astValueList = JsonUtils.json2List(keyList,
                        SalerInstInfoRegistDto.class);
                pageParam.put("registList", astValueList);
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        } else {
            // 查询参数条件
            // 代销代码
            String salerInstCode = (String) pageParam.get("salerInstCode");
            // 总部或分支机构名称
            String salerOrgName = (String) pageParam.get("salerOrgName");
            // 报送状态
            String sendStatus = (String) pageParam.get("sendStatus");
            // 反馈状态
            String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");

            String status = (String) pageParam.get("status");

            // 将数据状态当做多选下拉框处理
            if (StringUtil.isNotBlank(status)) {
                pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            }

            // 将总部或分支机构名称当做多选下拉框处理
            if (StringUtil.isNotBlank(salerOrgName)) {
                pageParam.put("salerOrgNameList", StringUtil.splitToList(GlobalConstant.COMMA, salerOrgName));
            }
            // 将销售机构代码当做多选下拉框处理
            if (StringUtil.isNotBlank(salerInstCode)) {
                pageParam.put("salerInstCodeList", StringUtil.splitToList(GlobalConstant.COMMA, salerInstCode));
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
    public List<SalerInstInfoRegistDto> setDataList(Map<String, Object> param) {
        List<SalerInstInfoRegistDto> resultList = new ArrayList<>();
        String keyList = (String) param.get("keyList");
        if (StringUtil.isNotEmpty(keyList)) {
            try {
                resultList = JsonUtils.json2List(keyList, SalerInstInfoRegistDto.class);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        } else {
            resultList = chbSalerInstInfoRegistMapper.setDataList(param);
        }
        return resultList;
    }

    public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam,String datas) throws IOException {
        // 导出文件
        List<File> sourceFiles = new ArrayList<>();
        Map<String,Object> data  = new HashMap<String,Object>();
        List<SalerInstInfoRegistDto> tempList=new ArrayList<SalerInstInfoRegistDto>();
        SalerInstInfoRegistDto nullDate = new SalerInstInfoRegistDto();
        if(datas == null){
            String status = selectParam.get("status").toString();
            selectParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            tempList = chbSalerInstInfoRegistMapper.setDataList(selectParam);
            this.formatByType(tempList);
        }else{
            tempList= JsonUtils.json2List(datas,SalerInstInfoRegistDto.class);
        }
        if(tempList.size() != 0) {
            data.put("dataList", tempList);
        }else{
            tempList.add(nullDate);
            data.put("dataList", tempList);
        }
        File depositfile = TisUtil.createFile(data,"pbc/" +"SalerInstInfoRegist_export", "总部或分支机构信息登记.xls");
        sourceFiles.add(depositfile);
        File outFile = null;
        outFile = sourceFiles.get(0);
        return ExportUtil.export(outFile.getName(), outFile, true);
    }

    /**
     * 销售机构代码下拉框
     */
    public List<SuggestDto> getSalerInstCode(Map<String, Object> map) {
        return chbSalerInstInfoRegistMapper.getSalerInstCode(map);
    }

    /**
     * 总部或分支机构名称下拉框
     */
    public List<SuggestDto> getSalerOrgName(Map<String, Object> map) {
        return chbSalerInstInfoRegistMapper.getSalerOrgName(map);
    }

    public static List<SalerInstInfoRegistDto> formatByType(List<SalerInstInfoRegistDto> dataList) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(item -> {
                item.setSalerOrgLevel(getDictCodeAndName(DictCode.机构层级, item.getSalerOrgLevel()));
            });
        }
        return dataList;
    }

    /**
     * 转换成key+value形式
     * @param dictCode  字典表中数据类型
     * @param value  需要换换的值
     * @return  key+value
     * @see
     * @since 1.0
     */
    public static String getDictCodeAndName(DictCode dictCode,String value){
        if(com.joyintech.framework.util.StringUtil.isNotEmpty(value)){
            String tempName = SysDataUtil.getNameFromDataDict(dictCode, value);
            if (com.joyintech.framework.util.StringUtil.isEmpty(tempName)) {
                return value;
            } else {
                return value + " " + SysDataUtil.getNameFromDataDict(dictCode, value);
            }
        }
        return "";
    }
}
