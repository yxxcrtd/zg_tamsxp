package com.joyintech.tams.report.submitmanagement.agentinstitutionregist.service;

import com.joyintech.framework.base.dto.UploadFile;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.system.common.listener.UploadListener;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.agentinstitutionregist.dao.ChbAgentInstitutionSubMapper;
import com.joyintech.tams.report.submitmanagement.agentinstitutionregist.dto.AgentInstitutionRegistDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2017年9月19日
 * @see AgentInstitutionRegist001Service
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AgentInstitutionRegist001Service extends CommonExportService<AgentInstitutionRegistDto>{

    @Autowired
    private ChbAgentInstitutionSubMapper chbAgentInstitutionSubMapper;
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
    public Page<AgentInstitutionRegistDto> searchForPage(Map<String, Object> param){
        dealSearchParam(param);
        if (null != param.get(GlobalConstant.PARAM_PAGE)
                && null != param.get(GlobalConstant.PARAM_LENGTH)) {
        int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
	    int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
        Page<AgentInstitutionRegistDto> pageList = super.queryForPage(
        		pagenum,legth,()->chbAgentInstitutionSubMapper.queryForRegistPage(param));
        return pageList;
        }else{
            Page<AgentInstitutionRegistDto> page = queryForPage(ChbAgentInstitutionSubMapper.class,"queryForRegistPage",param);
            return page;
        }
    }

    /**
     * 无分页查询
     * @param param
     * @return
     */
    public List<AgentInstitutionRegistDto> queryForInfoRegist(Map<String,Object> param){
        return chbAgentInstitutionSubMapper.queryForRegistPage(param);
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
        List<AgentInstitutionRegistDto> chbRegistList  = new ArrayList<AgentInstitutionRegistDto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            //按条件查询全部
            chbRegistList = chbAgentInstitutionSubMapper.searchPage(map);
        }else{
            chbRegistList = (List<AgentInstitutionRegistDto>)map.get("registList");
        }
        Iterator<AgentInstitutionRegistDto> it=chbRegistList.iterator();
        while(it.hasNext()){
            AgentInstitutionRegistDto m =  it.next();
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
    public void updateInterfSubmitAndSeq(String sendStatus, List<AgentInstitutionRegistDto> investList) {
        if (StringUtil.isEmptyList(investList)) {
            return;
        }
            //已报送数据进留痕表
            chbAgentInstitutionSubMapper.insertIntoHis(investList);
            //删除正式表已报送数据
            chbAgentInstitutionSubMapper.deleteLastSubmitRegist(investList);
            chbAgentInstitutionSubMapper.updateInterfSubmitAndSeqBatch(sendStatus, investList);
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
        List<AgentInstitutionRegistDto> list = new ArrayList<AgentInstitutionRegistDto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            list = chbAgentInstitutionSubMapper.searchPage(map);
        }else{
            list = (List<AgentInstitutionRegistDto>) map.get("registList");
        }
        chbAgentInstitutionSubMapper.cancelMannultRegist(list);
        // 将履历表中最后创建时间的数据挪回登记表
        chbAgentInstitutionSubMapper.insertReportDataFromHis(list);
        //将挪回数据从履历表中删除
        chbAgentInstitutionSubMapper.deleteReportDataHis(list);
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
                List<AgentInstitutionRegistDto> astValueList = JsonUtils.json2List(keyList,
                        AgentInstitutionRegistDto.class);
                pageParam.put("registList", astValueList);
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        } else {
            // 查询参数条件
            // 代销代码
            String agentInstitutionCode = (String) pageParam.get("agentInstitutionCode");
            // 数据状态
            String status = (String) pageParam.get("status");
            // 报送状态
            String sendStatus = (String) pageParam.get("sendStatus");
            // 反馈状态
            String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");

            // 将数代销代码当做多选下拉框处理
            if (StringUtil.isNotBlank(agentInstitutionCode)) {
                pageParam.put("agentInstitutionCodeList", StringUtil.splitToList(GlobalConstant.COMMA, agentInstitutionCode));
            }
            // 将数据状态当做多选下拉框处理
            if (StringUtil.isNotBlank(status)) {
                pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
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
    public List<AgentInstitutionRegistDto> setDataList(Map<String, Object> param) {
        List<AgentInstitutionRegistDto> resultList = new ArrayList<>();
        String keyList = (String) param.get("keyList");
        if (StringUtil.isNotEmpty(keyList)) {
            try {
                resultList = JsonUtils.json2List(keyList, AgentInstitutionRegistDto.class);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        } else {
            resultList = chbAgentInstitutionSubMapper.setDataList(param);
        }
        return resultList;
    }

    public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam,String datas) throws IOException {
        // 导出文件
        List<File> sourceFiles = new ArrayList<>();
        Map<String,Object> data  = new HashMap<String,Object>();
        List<AgentInstitutionRegistDto> tempList=new ArrayList<AgentInstitutionRegistDto>();
        AgentInstitutionRegistDto nullDate = new AgentInstitutionRegistDto();
        if(datas == null){
            String status = selectParam.get("status").toString();
            selectParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            tempList = chbAgentInstitutionSubMapper.setDataList(selectParam);
        }else{
            tempList= com.joyintech.framework.util.JsonUtils.json2List(datas,AgentInstitutionRegistDto.class);
        }
        if(tempList.size() != 0) {
            data.put("dataList", tempList);
        }else{
            tempList.add(nullDate);
            data.put("dataList", tempList);
        }
        File depositfile = TisUtil.createFile(data,"pbc/" +"AgentInstitutionRegist_export", "代理销售机构信息登记.xls");
        sourceFiles.add(depositfile);
        File outFile = null;
        outFile = sourceFiles.get(0);
        return ExportUtil.export(outFile.getName(), outFile, true);
    }

    /**
     * 代理销售机构代码下拉框
     */
    public List<SuggestDto> getAgentInstitutionCode(Map<String, Object> map) {
        return chbAgentInstitutionSubMapper.getAgentInstitutionCode(map);
    }

    /**
     * 附件查看
     * @param map
     * @return
     * @throws IOException
     */
    public String getFileList(Map<String, Object> map) throws IOException {
        StringBuffer res=new StringBuffer();
        if(map.get("fileNo")==null){
            return URLEncoder.encode("附件不存在", "UTF-8").replace("+","%20");
        }
        String fileNo=(String) map.get("fileNo");

        UploadFile file=uploadListener.fetch(fileNo);
        if(file==null||file.getTargetFile()==null){
            return URLEncoder.encode("附件丢失", "UTF-8").replace("+","%20");
        }
        res.append(file.getFileName()+":   "+file.getSize()/1000+"K<br><br>");
        try(ZipFile zipFile=new ZipFile(file.getTargetFile(), Charset.forName("GBK"))){
            for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
                ZipEntry entry=e.nextElement();
                if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()>0){
                    res.append(entry.getName().substring(entry.getName().lastIndexOf("/")+1)+"<br>");
                }
            }
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(),e);
            return URLEncoder.encode("文件未找到", "UTF-8").replace("+","%20");
        }
        String msg = URLEncoder.encode(res.toString(), "UTF-8").replace("+","%20");
        return msg;
    }
}
