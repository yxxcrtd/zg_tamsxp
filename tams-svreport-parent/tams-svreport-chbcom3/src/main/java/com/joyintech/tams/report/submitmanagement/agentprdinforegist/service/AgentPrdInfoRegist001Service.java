package com.joyintech.tams.report.submitmanagement.agentprdinforegist.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.agentinstitutionregist.dao.ChbAgentInstitutionSubMapper;
import com.joyintech.tams.report.submitmanagement.agentinstitutionregist.dto.AgentInstitutionRegistDto;
import com.joyintech.tams.report.submitmanagement.agentprdinforegist.dao.ChbAgentPrdSubMapper;
import com.joyintech.tams.report.submitmanagement.agentprdinforegist.dto.AgentPrdInfoRegistDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
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
 * @see AgentPrdInfoRegist001Service
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AgentPrdInfoRegist001Service extends CommonExportService<AgentPrdInfoRegistDto>{

    @Autowired
    private ChbAgentPrdSubMapper chbAgentPrdSubMapper;
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
    public Page<AgentPrdInfoRegistDto> searchForPage(Map<String, Object> param){
        if (null != param.get(GlobalConstant.PARAM_PAGE)
                && null != param.get(GlobalConstant.PARAM_LENGTH)) {
            int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
            int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
            Page<AgentPrdInfoRegistDto> pageList = super.queryForPage(
                    pagenum, legth, () -> chbAgentPrdSubMapper.queryForRegistPage(param));
            return pageList;
        }else{
            Page<AgentPrdInfoRegistDto> page = queryForPage(ChbAgentPrdSubMapper.class,"queryForRegistPage",param);
            return page;
        }
    }

    /**
     * 无分页查询
     * @param param
     * @return
     */
    public List<AgentPrdInfoRegistDto> queryForInfoRegist(Map<String,Object> param){
        return chbAgentPrdSubMapper.queryForRegistPage(param);
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
        List<AgentPrdInfoRegistDto> chbRegistList  = new ArrayList<AgentPrdInfoRegistDto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            //按条件查询全部
            chbRegistList = chbAgentPrdSubMapper.searchPage(map);
        }else{
            chbRegistList = (List<AgentPrdInfoRegistDto>)map.get("registList");
        }
        Iterator<AgentPrdInfoRegistDto> it=chbRegistList.iterator();
        while(it.hasNext()){
            AgentPrdInfoRegistDto m =  it.next();
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
    public void updateInterfSubmitAndSeq(String sendStatus, List<AgentPrdInfoRegistDto> investList) {
        if (StringUtil.isEmptyList(investList)) {
            return;
        }
            //已报送数据进留痕表
            chbAgentPrdSubMapper.insertIntoHis(investList);
            //删除正式表已报送数据
            chbAgentPrdSubMapper.deleteLastSubmitRegist(investList);
            chbAgentPrdSubMapper.updateInterfSubmitAndSeqBatch(sendStatus, investList);
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
        List<AgentPrdInfoRegistDto> list = new ArrayList<AgentPrdInfoRegistDto>();
        if(map.get("flag") != null && "01".equals(map.get("flag"))){
            list = chbAgentPrdSubMapper.searchPage(map);
        }else{
            list = (List<AgentPrdInfoRegistDto>) map.get("registList");
        }
        chbAgentPrdSubMapper.cancelMannultRegist(list);
        // 将履历表中最后创建时间的数据挪回登记表
        chbAgentPrdSubMapper.insertReportDataFromHis(list);
        //将挪回数据从履历表中删除
        chbAgentPrdSubMapper.deleteReportDataHis(list);
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
                List<AgentPrdInfoRegistDto> astValueList = JsonUtils.json2List(keyList,
                        AgentPrdInfoRegistDto.class);
                pageParam.put("registList", astValueList);
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        } else {
            // 查询参数条件
            // 数据状态
            String status = (String) pageParam.get("status");
            // 报送状态
            String sendStatus = (String) pageParam.get("sendStatus");
            // 反馈状态
            String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");
            // 代销代码
            String agentInstitutionCode = (String) pageParam.get("agentInstitutionCode");
            // 产品代码
            String prodId = (String) pageParam.get("prodId");
            // 登记编码
            String prodRegistCode = (String) pageParam.get("prodRegistCode");

            // 将代销代码当做多选下拉框处理
            if (StringUtil.isNotBlank(agentInstitutionCode)) {
                pageParam.put("agentInstitutionCodeList", StringUtil.splitToList(GlobalConstant.COMMA, agentInstitutionCode));
            }
            // 将产品代码当做多选下拉框处理
            if (StringUtil.isNotBlank(prodId)) {
                pageParam.put("prodIdList", StringUtil.splitToList(GlobalConstant.COMMA, prodId));
            }
            // 将登记编码当做多选下拉框处理
            if (StringUtil.isNotBlank(prodRegistCode)) {
                pageParam.put("prodRegistCodeList", StringUtil.splitToList(GlobalConstant.COMMA, prodRegistCode));
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

    public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam, String datas) throws IOException {
        // 导出文件
        List<File> sourceFiles = new ArrayList<>();
        Map<String,Object> data  = new HashMap<String,Object>();
        List<AgentPrdInfoRegistDto> tempList=new ArrayList<AgentPrdInfoRegistDto>();
        List<AgentPrdInfoRegistDto> dateTempList=new ArrayList<AgentPrdInfoRegistDto>();//本行机构代码
        AgentPrdInfoRegistDto nullDate = new AgentPrdInfoRegistDto();//没有未报送数据时塞入空model
        if(datas == null){
            String status = selectParam.get("status").toString();
            selectParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            tempList = chbAgentPrdSubMapper.setDataList(selectParam);
            if(tempList.size() != 0) {
                dateTempList.add(tempList.get(0));//获取本行机构代码
            }
        }else{
            tempList= com.joyintech.framework.util.JsonUtils.json2List(datas,AgentPrdInfoRegistDto.class);
            dateTempList.add(tempList.get(0));//获取本行机构代码
        }
        if(tempList.size() !=0 && dateTempList.size() !=0) {
            data.put("dataList1", tempList);
            data.put("dataList", dateTempList);
        }else{
            tempList.add(nullDate);
            data.put("dataList1", tempList);
            data.put("dataList", tempList);
        }
        File depositfile = TisUtil.createFile(data,"pbc/" +"AgentPrdInfoRegist_export", "委托销售产品编码信息登记.xls");
        sourceFiles.add(depositfile);
        File outFile = null;
        outFile = sourceFiles.get(0);
        return ExportUtil.export(outFile.getName(), outFile, true);
    }

    /**
     * 代理销售机构代码下拉框
     */
    public List<SuggestDto> getAgentInstitutionCode(Map<String, Object> map) {
        return chbAgentPrdSubMapper.getAgentInstitutionCode(map);
    }

    /**
     * 产品代码下拉框
     */
    public List<SuggestDto> getProdId(Map<String, Object> map) {
        return chbAgentPrdSubMapper.getProdId(map);
    }

    /**
     * 产品登记编码下拉框
     */
    public List<SuggestDto> getProdRegistCode(Map<String, Object> map) {
        return chbAgentPrdSubMapper.getProdRegistCode(map);
    }
}
