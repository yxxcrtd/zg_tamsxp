package com.joyintech.tams.batch.svreport.chb.chbastposregistexport.service;

import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.svreport.chb.astposregist.service.ChbAstPosRegist001Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 资产持仓登记手工处理 业务service
 *
 * @author zhoubin
 * @version 1.0 2022年07月27日
 * @see ChbAstPosRegistAutoManualMainService
 * @since 1.0
 */
@Service
public class ChbAstPosRegistAutoManualMainService extends AutoSendCBBaseService{

	@Resource
	private ChbAstPosRegist001Service chbAstPosRegist001Service;
	
    /**
     * 手工处理
     *
     * @see
     * @since 1.0
     */
    public void autoManual() {
        setBatchBeanName(BatchBeanName.资产持仓登记手工处理);
        logInfoBatchStart();
        Map<String, Object> map=new HashMap<>(1);
        map.put("sendStatus", "01");
        chbAstPosRegist001Service.updateSumStausAll(map);
        logInfoBatchEnd();
    }
}
