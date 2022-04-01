package com.joyintech.tams.settle.batch.datagather.reader;

import java.util.Arrays;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;

/**
 * 股票新股中签交易数据处理
 * 
 * @author chenzhuo
 * @date 2020/7/20
 * @see StlDataGatherShares1Reader
 * @since 1.0
 */
@Service
@Lazy
public class StlDataGatherShares1Reader extends StlDataGatherReader {
    @Override
    protected void setSearchParam(Map<String, Object> searchParam) {
    	// 业务类型
        searchParam.put("busiType", DictCodeList.业务类型_股票交易.getValue());
        // 交易方向列表
        searchParam.put("psList", Arrays.asList(DictCodeListExt2.交易方向_新股中签.getValue()
                ,DictCodeListExt2.交易方向_增发.getValue(),DictCodeListExt2.交易方向_配股.getValue()
                ,DictCodeList.交易方向_买入.getValue(),DictCodeList.交易方向_卖出.getValue()
                // GZYHTZJY-1014:增加申购方向 fuyongsheng 20211230 add start
                , DictCodeList.交易方向_申购.getValue()
                // GZYHTZJY-1014:增加申购方向 fuyongsheng 20211230 add end
                ));
    }

    @Override
    protected boolean isSecType(String secType) {
        return StringUtil.equals(DictCodeListExt1.证券品种_股票.getValue(), secType);
    }

    @Override
    protected boolean isCreateTrade(){
        return false;
    }
}
