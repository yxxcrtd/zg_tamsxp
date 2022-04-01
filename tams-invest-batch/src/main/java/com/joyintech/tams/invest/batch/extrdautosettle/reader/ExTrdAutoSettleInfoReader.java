package com.joyintech.tams.invest.batch.extrdautosettle.reader;

import com.joyintech.batch.batchbase.baseclass.BaseReader;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ExTrdAutoSettleInfoReaderResult;
import com.joyintech.tams.invest.batch.extrdautosettle.service.ExTrdAutoSettleInfoReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 手工交收信息Reader
 *
 * @author fuyongsheng
 * @version 1.0 2021年11月11日
 * @see ExTrdAutoSettleInfoReader
 * @since 1.0
 */
@Service
@Scope("prototype")
public class ExTrdAutoSettleInfoReader extends BaseReader<ExTrdAutoSettleInfoReaderResult> {

    @Autowired
    private ExTrdAutoSettleInfoReaderService readerService;

    @Override
    public List<ExTrdAutoSettleInfoReaderResult> createList() {
        return readerService.execute(this.getParams());
    }
}
