package com.joyintech.tams.settle.batch.datagather.reader.bond;

import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt3;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 债券相关交易取值逻辑
 * @author 何阳阳
 * @date 2020/7/17
 * @see StlDataGatherBond1Reader
 * @since 1.0
 */
@Service
@Lazy
public class StlDataGatherBond1Reader extends StlDataGatherBondBaseReader {
    @Override
    protected List<String> getPSList() {
        return Arrays.asList(DictCodeList.交易方向_买入.getValue()
                ,DictCodeList.交易方向_卖出.getValue(),DictCodeList.交易方向_行权_回售.getValue(),
                // GNS-905:质押券出入库当做债券处理 modify by fuyongsheng 20220219 start
                DictCodeListExt3.交易方向_质押券入库.getValue(), DictCodeListExt3.交易方向_质押券出库.getValue()
                // GNS-905:质押券出入库当做债券处理 modify by fuyongsheng 20220219 end
        );
    }

    @Override
    protected boolean isCreateTrade(){
        return false;
    }
}
