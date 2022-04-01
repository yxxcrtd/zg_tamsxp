package com.joyintech.tams.settle.batch.datagather.reader.repo;

import com.joyintech.tams.constant.DictCodeList;
import org.apache.commons.compress.utils.Lists;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 获取债券质押式回购首期交易数据
 * @author 何阳阳
 * @date 2020/7/20
 * @see StlDataGatherRepo1Reader
 * @since 1.0
 */
@Service
@Lazy
public class StlDataGatherRepo1Reader extends StlDataGatherRepoBaseReader {
    @Override
    protected List<String> getPSList() {
        // return Arrays.asList(DictCodeList.交易方向_买入.getValue());
        return Lists.newArrayList();
    }

    @Override
    protected void setSearchParam(Map<String, Object> searchParam) {
        // 业务类型
        searchParam.put("busiType", DictCodeList.业务类型_债券质押式回购交易.getValue());
        // reader 类型
        searchParam.put("readerType", "Repo1Reader");
        // 交易方向列表
        searchParam.put("psList", getPSList());
    }

    @Override
    protected boolean isCreateTrade(){
        return false;
    }


}
