package com.joyintech.tams.invest.batch.extrdautosettle.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ErrorCode;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ResultBean;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.RtgsStatusRet;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RtgsStatusRetService extends BaseService {

    private static final String prefix = "/joyin/service";
    /**
     * 取手工交收结果
     * @return
     */
    public  Object getRtgsStatusRetList(Set<String> dealNoSet){
        List<String> dealNoList = new ArrayList<>(dealNoSet);
        ResultBean res = restTemplate.postForObject(ServiceApplication.EXCHANGE_SERVICE_NAME  + prefix + "/order/rtgs/status",
                dealNoList, ResultBean.class);
        if( ErrorCode.SUCCESS.equals(res.getErrorCode())){
            return res.getData();
        }else{
            return  null;
        }

    }
}
