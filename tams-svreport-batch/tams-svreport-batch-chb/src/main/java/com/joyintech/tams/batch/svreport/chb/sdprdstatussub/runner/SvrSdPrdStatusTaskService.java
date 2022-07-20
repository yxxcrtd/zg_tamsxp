package com.joyintech.tams.batch.svreport.chb.sdprdstatussub.runner;

import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.framework.base.entity.SysBatchExecInfo;
import com.joyintech.framework.base.entity.SysBatchgrpExecInfo;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.batch.report.common.db.SvrDBUtil;
import com.joyintech.tams.batch.svreport.chb.sdprddeclaresub.service.SdProdDeclareMainService;
import com.joyintech.tams.batch.svreport.chb.sdprdstatussub.service.SdPrdStatusMainService;
import com.joyintech.tams.common.svreport.chb.util.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  产品状态信息报送批处理
 * @author zhaoyongsheng
 * @version 1.0 2020年09月27日
 * @see 
 * @since 1.0
 */
@Service(value = "svrSdPrdStatusTaskService")
@Scope("prototype")
public class SvrSdPrdStatusTaskService extends BatchBaseService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SdPrdStatusMainService mainService;
    
    
    
	@Autowired
	private SvrDBUtil dbUtil;
    
	/**
	 * 获取批处理选择的产品组合
	 * @return
	 */
	private List<String> getProdList() {
		SysBatchgrpExecInfo batchExecuteDetail = this.getBatchExecuteDetail();
		List<String> prodList = new ArrayList<String>();
		List<String> prodListex = new ArrayList<String>();
		if (StringUtil.isNotEmpty(batchExecuteDetail.getPrdGroupSeqNo())) {
			Map<String,String> prodMap=dbUtil.getAllProdMapWithNoSameId();
			prodListex = this.getProductIdList();
		    if(!prodListex.isEmpty()) {
		    	for(String prodString : prodListex) {
		    		//净值型产品过滤“F16_”前缀
		    		prodString = prodString.replaceFirst("F16_", "");
		    		if(prodMap.containsKey(prodString)){
		    			prodList.add(prodMap.get(prodString));
		    		}else{
		    			prodList.add(prodString);
		    		}
		    	}
		    }
		}
		return prodList;
	}
    
    @Override
    public JobExecution execute() throws BatchException {
        JobExecution result = new JobExecution(1l);
        result.setStatus(BatchStatus.FAILED);
        SysBatchExecInfo batchExecuteInfo = this.getBatchExecuteInfo();
        if (batchExecuteInfo == null) {
            logger.info("无启动参数，批处理退出。");
            return result;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate=new Date();
        Long bt=bdate.getTime();
        logger.info("产品状态登记信息报送批处理启动:"+sdf.format(bdate));
        try {
            List<String> prodListex  = getProdList();
         
            mainService.SendDurationRegMsg(ToolsUtil.getCurrentTime(batchExecuteInfo.getExecuteDate())
            		                             ,prodListex);
            result.setStatus(BatchStatus.COMPLETED);
        } catch (Exception e) {
			logger.info("批处理异常" + e.getMessage());
        	logger.error("批处理异常Error",e);
        }finally{
        	Date edate=new Date();
            Long et=edate.getTime();
            Long time=(et-bt)/1000;
        	logger.info("产品状态登记信息报送批处理结束:"+sdf.format(edate)+"耗时"+time+"秒");
        }
        return result;
    }


}
