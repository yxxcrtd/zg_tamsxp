package com.joyintech.tams.report.submitmanagement.salerideninforegist.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.system.common.listener.UploadListener;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.agentprdinforegist.dao.ChbAgentPrdSubMapper;
import com.joyintech.tams.report.submitmanagement.agentprdinforegist.dto.AgentPrdInfoRegistDto;
import com.joyintech.tams.report.submitmanagement.salerideninforegist.dao.ChbSalerIdenInfoRegistMapper;
import com.joyintech.tams.report.submitmanagement.salerideninforegist.dto.SalerIdenInfoRegistDto;
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
 * @see SalerIdenInfoRegist001Service
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SalerIdenInfoRegist001Service extends CommonExportService<SalerIdenInfoRegistDto>{

    @Autowired
    private ChbSalerIdenInfoRegistMapper chbSalerIdenInfoRegistMapper;
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
    public Page<SalerIdenInfoRegistDto> searchForPage(Map<String, Object> param){
        dealSearchParam(param);
        if (null != param.get(GlobalConstant.PARAM_PAGE)
                && null != param.get(GlobalConstant.PARAM_LENGTH)) {
            int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
            int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
            Page<SalerIdenInfoRegistDto> pageList = super.queryForPage(
                    pagenum, legth, () -> chbSalerIdenInfoRegistMapper.queryForRegistPage(param));
            this.formatByType(pageList.getRoot());
            return pageList;
        }else{
            Page<SalerIdenInfoRegistDto> page = queryForPage(ChbSalerIdenInfoRegistMapper.class,"queryForRegistPage",param);
            this.formatByType(page.getRoot());
            return page;
        }
    }

    /**
     * 无分页查询
     * @param param
     * @return
     */
    public List<SalerIdenInfoRegistDto> queryForInfoRegist(Map<String,Object> param){
        dealSearchParam(param);
        return chbSalerIdenInfoRegistMapper.queryForRegistPage(param);
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
        List<SalerIdenInfoRegistDto> chbRegistList  = new ArrayList<SalerIdenInfoRegistDto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            //按条件查询全部
            chbRegistList = chbSalerIdenInfoRegistMapper.searchPage(map);
        }else{
            chbRegistList = (List<SalerIdenInfoRegistDto>)map.get("registList");
        }
        Iterator<SalerIdenInfoRegistDto> it=chbRegistList.iterator();
        while(it.hasNext()){
            SalerIdenInfoRegistDto m =  it.next();
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
    public void updateInterfSubmitAndSeq(String sendStatus, List<SalerIdenInfoRegistDto> investList) {
        if (StringUtil.isEmptyList(investList)) {
            return;
        }
            //已报送数据进留痕表
            chbSalerIdenInfoRegistMapper.insertIntoHis(investList);
            //删除正式表已报送数据
            chbSalerIdenInfoRegistMapper.deleteLastSubmitRegist(investList);
            chbSalerIdenInfoRegistMapper.updateInterfSubmitAndSeqBatch(sendStatus, investList);
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
        List<SalerIdenInfoRegistDto> list = new ArrayList<SalerIdenInfoRegistDto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            list = chbSalerIdenInfoRegistMapper.searchPage(map);
        }else{
            list = (List<SalerIdenInfoRegistDto>) map.get("registList");
        }
        chbSalerIdenInfoRegistMapper.cancelMannultRegist(list);
        // 将履历表中最后创建时间的数据挪回登记表
        chbSalerIdenInfoRegistMapper.insertReportDataFromHis(list);
        //将挪回数据从履历表中删除
        chbSalerIdenInfoRegistMapper.deleteReportDataHis(list);
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
                List<SalerIdenInfoRegistDto> astValueList = JsonUtils.json2List(keyList,
                        SalerIdenInfoRegistDto.class);
                pageParam.put("registList", astValueList);
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        } else {
            // 查询参数条件
            // 姓名
            String salerName = (String) pageParam.get("salerName");
            // 性别
            String salerSex = (String) pageParam.get("salerSex");
            // 证件类型
            String salerCredentType = (String) pageParam.get("salerCredentType");
            // 报送状态
            String sendStatus = (String) pageParam.get("sendStatus");
            // 反馈状态
            String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");

            String status = (String) pageParam.get("status");

            // 将姓名当做多选下拉框处理
            if (StringUtil.isNotBlank(salerName)) {
                pageParam.put("salerNameList", StringUtil.splitToList(GlobalConstant.COMMA, salerName));
            }
            // 将性别当做多选下拉框处理
            if (StringUtil.isNotBlank(salerSex)) {
                pageParam.put("salerSexList", StringUtil.splitToList(GlobalConstant.COMMA, salerSex));
            }
            // 将证件类型当做多选下拉框处理
            if (StringUtil.isNotBlank(salerCredentType)) {
                pageParam.put("salerCredentTypeList", StringUtil.splitToList(GlobalConstant.COMMA, salerCredentType));
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
            // 将数据状态当做多选下拉框处理
            if (StringUtil.isNotBlank(status)) {
                pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            }
        }
    }


    /**
     * 导出查询
     * @param param
     * @return
     */
    public List<SalerIdenInfoRegistDto> setDataList(Map<String, Object> param) {
        List<SalerIdenInfoRegistDto> resultList = new ArrayList<>();
        String keyList = (String) param.get("keyList");
        if (StringUtil.isNotEmpty(keyList)) {
            try {
                resultList = JsonUtils.json2List(keyList, SalerIdenInfoRegistDto.class);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        } else {
            dealSearchParam(param);
            resultList = chbSalerIdenInfoRegistMapper.setDataList(param);
        }
        return resultList;
    }

    public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam,String datas) throws IOException {
        // 导出文件
        List<File> sourceFiles = new ArrayList<>();
        Map<String,Object> data  = new HashMap<String,Object>();
        List<SalerIdenInfoRegistDto> tempList=new ArrayList<SalerIdenInfoRegistDto>();
        SalerIdenInfoRegistDto nullDate = new SalerIdenInfoRegistDto();
        if(datas == null){
            dealSearchParam(selectParam);
            String status = selectParam.get("status").toString();
            selectParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            tempList = chbSalerIdenInfoRegistMapper.setDataList(selectParam);
            this.formatByType(tempList);
        }else{
            tempList= JsonUtils.json2List(datas,SalerIdenInfoRegistDto.class);
        }
        if(tempList.size() != 0) {
            data.put("dataList", tempList);
        }else{
            tempList.add(nullDate);
            data.put("dataList", tempList);
        }
        File depositfile = TisUtil.createFile(data,"pbc/" +"SalerIdenInfoRegist_export", "销售人员身份信息登记.xls");
        sourceFiles.add(depositfile);
        File outFile = null;
        outFile = sourceFiles.get(0);
        return ExportUtil.export(outFile.getName(), outFile, true);
    }

    /**
     * 姓名下拉框
     */
    public List<SuggestDto> getSalerName(Map<String, Object> map) {
        return chbSalerIdenInfoRegistMapper.getSalerName(map);
    }

    public static List<SalerIdenInfoRegistDto> formatByType(List<SalerIdenInfoRegistDto> dataList) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(item -> {
                item.setSalerSex(getDictCodeAndName(DictCode.性别_中债, item.getSalerSex()));
                item.setSalerCredentType(getDictCodeAndName(DictCode.销售人员证件类型, item.getSalerCredentType()));
                item.setSalerAreaType(getDictCodeAndName(DictCode.销售区域_中债, item.getSalerAreaType()));
                item.setSalerEduLevel(getDictCodeAndName(DictCode.销售人员学历, item.getSalerEduLevel()));
                item.setSalerStatus(getDictCodeAndName(DictCode.销售人员当前状态, item.getSalerStatus()));
                item.setSalerDegree(getDictCodeAndName(DictCode.销售人员学位, item.getSalerDegree()));
                item.setSalerLevle(getDictCodeAndName(DictCode.销售人员专业技术职称, item.getSalerLevle()));
                if(item.getSalerCertificateType()!=null){
                    List<String> salerCertificateTypes=StringUtil.splitToList(GlobalConstant.COMMA, item.getSalerCertificateType());
                    item.setSalerCertificateType("");
                    for(String salerCertificateType : salerCertificateTypes){
                        item.setSalerCertificateType(item.getSalerCertificateType()+","+salerCertificateType+" "+ SvrDataUtil.getNameFromDataDict(DictCode.销售人员理财专业证书, salerCertificateType));
                    }
                    item.setSalerCertificateType(item.getSalerCertificateType().substring(1));
                }
                if(item.getSalerRiskLevel()!=null){
                    List<String> salerRiskLevels=StringUtil.splitToList(GlobalConstant.COMMA, item.getSalerRiskLevel());
                    item.setSalerRiskLevel("");
                    for(String salerRiskLevel : salerRiskLevels){
                        item.setSalerRiskLevel(item.getSalerRiskLevel()+","+salerRiskLevel+" "+SvrDataUtil.getNameFromDataDict(DictCode.可销售产品风险等级, salerRiskLevel));
                    }
                    item.setSalerRiskLevel(item.getSalerRiskLevel().substring(1));
                }
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
