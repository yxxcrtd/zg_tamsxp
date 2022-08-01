package com.joyintech.tams.batch.svreport.chb.chbastposregistexport.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.joyintech.tams.batch.svreport.chb.chbastposregistexport.dao.ChbAstPosRegistExportMapper;
import org.springframework.stereotype.Service;
import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.system.subinfobatchlog.dao.ChbSubinfoAutoBatchLogMapper;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;

import javax.annotation.Resource;


/**
 * 资产持仓登记批处理导出
 * @author zhoubin
 */
@Service
public class ChbAstPosRegistExportMainService extends AutoSendCBBaseService {

    @Resource
    private ChbAstPosRegistExportCommonService chbAstPosRegistExportCommonService;

    @Resource
    private ChbAstPosRegistExportMapper chbAstPosRegistExportMapper;

    @Resource
    private ChbSubinfoAutoBatchLogMapper chbSubinfoAutoBatchLogMapper;


    public void getExportData() {
        setBatchBeanName(BatchBeanName.资产持仓登记批处理导出);
        logInfoBatchStart();
        List<ChbSubinfoBatchLog> listBatchLog = chbAstPosRegistExportMapper.getBatchLog();
        for (ChbSubinfoBatchLog batchLog : listBatchLog) {
            //只处理补登数据
            if (Objects.equals(DictCodeListExt3.报送文件类别_资产持仓登记批处理导出.getValue(), batchLog.getFileType())) {
                batchLog.setDealStatus(DictCodeListExt3.报送处理状态_处理中.getValue());
                chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);
                Map param = (Map) JSON.parse(batchLog.getGenCondition());
                String valueDateStart = param.get("valueDateStart").toString();
                String valueDateEnd = param.get("valueDateEnd").toString();
                //判断导出中债excel持仓日期是否大于当天日期
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String nowDate = format.format(new Date());
                if (DateUtil.compareDate(valueDateStart, nowDate)>0 && DateUtil.compareDate(valueDateEnd, nowDate)>0) {
                    batchLog.setDealStatus(DictCodeListExt3.报送处理状态_正常结束.getValue());
                    batchLog.setDealMessage("资产持仓登记导出中债Excel业务确认日期不能大于当天日期!不导出"+valueDateStart+"-"+valueDateEnd+"数据。");
                    chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);
                    continue;
                }else if(DateUtil.compareDate(valueDateStart, nowDate)<=0 && DateUtil.compareDate(valueDateEnd, nowDate)>0){
                    param.put("valueDateEnd", nowDate);
                }
                long count = chbAstPosRegistExportMapper.countExportNum(param);
                chbAstPosRegistExportCommonService.export(count, param, batchLog);
            }

        }
        logInfoBatchEnd();
    }
}
