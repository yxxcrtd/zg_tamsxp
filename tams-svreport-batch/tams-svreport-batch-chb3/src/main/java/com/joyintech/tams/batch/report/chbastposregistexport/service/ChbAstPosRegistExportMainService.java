package com.joyintech.tams.batch.report.chbastposregistexport.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.joyintech.tams.batch.report.chbastposregistexport.dao.ChbAstPosRegistExportMapper;
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
    public void getExportData(Date executeDate) {
        setBatchBeanName(BatchBeanName.资产持仓登记批处理导出);
        logInfoBatchStart();
        List<ChbSubinfoBatchLog> listBatchLog = chbAstPosRegistExportMapper.getBatchLog();
        for (ChbSubinfoBatchLog batchLog : listBatchLog) {
            //只处理补登数据
            if (Objects.equals(DictCodeListExt3.报送文件类别_资产持仓登记批处理导出.getValue(), batchLog.getFileType())) {
                batchLog.setDealStatus(DictCodeListExt3.报送处理状态_处理中.getValue());
                chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);
                String valueDate = DateUtil.format(executeDate);
                Map<String,Object> param = new HashMap<>(2);
                param.put("valueDateStart", valueDate);
                param.put("valueDateEnd", valueDate);
                //判断导出中债excel持仓日期是否大于当天日期
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String nowDate = format.format(new Date());
                if (DateUtil.compareDate(valueDate, nowDate) > 0) {
                    batchLog.setDealStatus(DictCodeListExt3.报送处理状态_正常结束.getValue());
                    batchLog.setDealMessage("资产持仓登记批处理导出业务确认日期不能大于当天日期!不导出" + valueDate + "数据。");
                    chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);
                    continue;
                }
                long count = chbAstPosRegistExportMapper.countExportNum(param);
                chbAstPosRegistExportCommonService.export(count, param, batchLog);
            }

        }
        logInfoBatchEnd();
    }
}
