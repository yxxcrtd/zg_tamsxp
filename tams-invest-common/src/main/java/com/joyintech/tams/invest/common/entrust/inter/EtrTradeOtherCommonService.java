package com.joyintech.tams.invest.common.entrust.inter;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.invest.common.entrust.dao.EtrTrdCommonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 公共方法service
 */
@Service("etrTradeOtherCommonService")
public class EtrTradeOtherCommonService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(EtrTradeOtherCommonService.class);


    @Autowired
    private EtrTrdCommonMapper etrTrdCommonMapper;

    public BigDecimal selectFaceValue(Map<String, Object> params) {
        params.put("sysDate",DateUtil.platDateFromCache());
        BigDecimal result = etrTrdCommonMapper.selectFaceValue(params);
        return result == null ? new BigDecimal(100) : result;
    }
}
