package com.joyintech.tams.batch.report.equitybusiinfoexport.service;

import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.batch.report.equitybusiinfoexport.dao.EquityBusiInfoExportMapper;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.system.subinfobatchlog.dao.ChbSubinfoAutoBatchLogMapper;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author zhoubin
 * 2022年06月21日
 */
@Service
public class EquityBusiInfoSupplyExportMainService extends AutoSendCBBaseService {

    @Resource
    private EquityBusiInfoSupplyExportCommonService equityBusiInfoSupplyExportCommonService;

    @Resource
    private EquityBusiInfoExportMapper equityBusiInfoExportMapper;

    @Resource
    private ChbSubinfoAutoBatchLogMapper chbSubinfoAutoBatchLogMapper;

    public void getExportData(Date executeDate) {
        setBatchBeanName(BatchBeanName.投资者明细信息补登记);
        logInfoBatchStart();
        List<ChbSubinfoBatchLog> listBatchLog = equityBusiInfoExportMapper.getBatchLog();
        for (ChbSubinfoBatchLog batchLog : listBatchLog) {
            //只处理补登数据
            if (Objects.equals(DictCodeListExt3.报送文件类别_投资者明细信息补登记.getValue(), batchLog.getFileType())) {
                batchLog.setDealStatus(DictCodeListExt3.报送处理状态_处理中.getValue());
                chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);
                String holdDate = DateUtil.format(executeDate);
                Map<String,Object> param = new HashMap<>(2);
                param.put("holdDateStart", holdDate);
                param.put("holdDateEnd", holdDate);
                //判断导出中债excel业务确认日期是否大于当天日期
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String nowDate = format.format(date);
                if (DateUtil.compareDate(holdDate, nowDate) > 0) {
                    batchLog.setDealStatus(DictCodeListExt3.报送处理状态_正常结束.getValue());
                    batchLog.setDealMessage("明细登记导出中债Excel业务确认日期不能大于当天日期!不导出" + holdDate + "数据。");
                    chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);
                    continue;
                }
                long count = equityBusiInfoExportMapper.countExportNum(param);
                equityBusiInfoSupplyExportCommonService.export(count, param, batchLog);
            }

        }
        logInfoBatchEnd();
    }
}
