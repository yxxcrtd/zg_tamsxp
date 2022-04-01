package com.joyintech.tams.batch.report.autosendcb.cnbondupinterf.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.framework.base.entity.SysBatchExecInfo;
import com.joyintech.tams.batch.report.autosendcb.cnbondupinterf.service.Chb3CnBondUpInterfTaskService;

/**
 * 中债上游接口批处理
 *
 * @author zhangyangfei
 * @version 1.0 2019年12月3日
 * @see CnBondUpInterfService
 * @since 1.0
 */
@Service(value = "cnBondUpInterfService")
@Scope("prototype")
public class CnBondUpInterfService extends BatchBaseService{

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 接口轮询任务
	 */
	@Autowired
	private Chb3CnBondUpInterfTaskService chb3CnBondUpInterfTaskService;

	@Override
	public JobExecution execute() throws BatchException {
		JobExecution result = new JobExecution(1l);
		result.setStatus(BatchStatus.FAILED);
		SysBatchExecInfo batchExecuteInfo = this.getBatchExecuteInfo();
		if (batchExecuteInfo == null) {
			logger.info("无启动参数，批处理退出。");
			return result;
		}
		try {
			// 调用接口轮询任务逻辑处理反馈信息
			chb3CnBondUpInterfTaskService.execute(batchExecuteInfo.getExecuteDate());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		result.setStatus(BatchStatus.COMPLETED);
		return result;
	}
}
