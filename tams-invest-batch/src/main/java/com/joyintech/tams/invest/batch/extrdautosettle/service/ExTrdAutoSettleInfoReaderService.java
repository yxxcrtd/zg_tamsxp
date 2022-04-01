package com.joyintech.tams.invest.batch.extrdautosettle.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.batch.extrdautosettle.dao.ExTrdAutoSettleMapper;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ExTrdAutoSettleInfoDto;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ExTrdAutoSettleInfoReaderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * reader相关业务处理
 *
 * @author fuyongsheng
 * @version 1.0 2021年11月11日
 * @see ExTrdAutoSettleInfoReaderService
 * @since 1.0
 */
@Service
@Scope("prototype")
public class ExTrdAutoSettleInfoReaderService extends BaseService {

    @Autowired
    private ExTrdAutoSettleMapper exTrdAutoSettleMapper;

    public List<ExTrdAutoSettleInfoReaderResult> execute(Map<String, Object> param) {
        param.put("SETTLE_STATUS_01", DictCodeListExt3.交收状态_未交收.getValue());
        param.put("SETTLE_STATUS_03", DictCodeListExt3.交收状态_交收延期.getValue());
        List<ExTrdAutoSettleInfoDto> settleInfoDtos = exTrdAutoSettleMapper.selectExTrdAutoSettleInfos(param);
        ExTrdAutoSettleInfoReaderResult readerResult = new ExTrdAutoSettleInfoReaderResult();
        readerResult.setAutoSettleInfoDtos(settleInfoDtos);
        List<ExTrdAutoSettleInfoReaderResult> result = new ArrayList<>();
        result.add(readerResult);
        return result;
    }
}
