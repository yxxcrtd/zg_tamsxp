package com.joyintech.tams.batch.svreport.chb.sdprdstatussub.service;

import com.joyintech.tams.svreport.chbsd.prddeclaresub.service.SdProDeclareBatchService;
import com.joyintech.tams.svreport.chbsd.prodstatusregistsub.service.SdProdStatusRegistBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * 功能:产品申报信息报送批处理入口
 * @since 
 * @version 
 * @作者：zhaoyongsheng
 * @创建时间：
 * @修改记录：
 */
@Service(value="sdPrdStatusMainService")
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class SdPrdStatusMainService {

    @Autowired
	private SdProdStatusRegistBatchService batchService;
	
    /**
     * @param bookDate 
     * @throws Exception 
     * @see
     * @since 1.0
     */
    public void SendDurationRegMsg(Date batchDate,List<String> prodIdList) throws InvocationTargetException, IllegalAccessException {
        batchService.sendSubInfoMsg(batchDate, prodIdList);
    }

}
