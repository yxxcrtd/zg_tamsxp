/*
 * 文件名：CnBondUpInterfTaskService.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：Administrator
 * 修改时间：2017年11月9日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.batch.report.autosendcb.cnbondupinterf.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.constant.InterfConstant;
import com.joyintech.tams.report.interf.common.service.InterfService;

/**
 * 中债上游接口定时任务处理
 *
 * @author wm
 * @version 1.0 2017年11月9日
 * @see CnBondUpInterfTaskService
 * @since 1.0
 */
@Service("chb3cnBondUpInterfTaskService")
public class Chb3CnBondUpInterfTaskService extends InterfService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(Chb3CnBondUpInterfTaskService.class);

    /**
     * 上游接口service
     */
    @Autowired
    private Chb3CnBondUpInterfService chb3cnBondUpInterfService;

    /**
     * 上游接口轮询任务
     *
     */
    @SuppressWarnings("resource")
    public void execute(Date batchDate) {
        FileChannel channel = null;
        FileLock lock = null;
        try {
            String fileLockPath = this.getParaVauleByparaCode(InterfConstant.KEY_LOCK_FILEPATH);
            // 查看锁文件是否存在
            File lockPath = new File(fileLockPath);
            if (!lockPath.exists()) {
                lockPath.mkdirs();
            }
            File lockFile = new File(fileLockPath + File.separator + "up.lockfile");
            if (!lockFile.exists()) {
                lockFile.createNewFile();
            }
            channel = new FileOutputStream(lockFile).getChannel();
            // 获得锁方法一：lock()，阻塞的方法，当文件锁不可用时，当前进程会被挂起
            lock = channel.tryLock();// 无参lock()为独占锁
            if (lock == null) {
                return;
            }
            // 合并中债mysql早期改造代码  add by zoujiamin  2021/04/20  start
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date bdate=new Date();
	        Long bt=bdate.getTime();
	        logger.info("上游解析三期文件开始---------:"+ DateUtil.format(batchDate));
            // 合并中债mysql早期改造代码  add by zoujiamin  2021/04/20  end
            try {
                // 获取消息（读取前一日文件夹信息）
//                cnBondUpInterfService.getUpInterfMessage(
//                    DateUtil.addDayLocal(DateUtil.getCurrentDate(), -1));
            	chb3cnBondUpInterfService.getUpInterfMessage(DateUtil.format(batchDate));
            }
            catch (Exception e) {
            	 logger.error("error:",e);
                logger.error("读取前一日文件夹数据异常");
            }

            // 获取消息（读取当前日期文件夹信息）
//            chb3cnBondUpInterfService.getUpInterfMessage(DateUtil.getCurrentDate());

            // 合并中债mysql早期改造代码  add by zoujiamin  2021/04/20  start
            Date edate=new Date();
		    Long et=edate.getTime();
		    Long time=(et-bt)/1000;
			logger.info("上游解析三期文件结束----------:"+DateUtil.format(batchDate)+"耗时"+time+"秒");
            // 合并中债mysql早期改造代码  add by zoujiamin  2021/04/20  end
        }
        catch (FileNotFoundException e) {
            logger.error("任务锁文件", e);
        }
        catch (IOException e) {
            logger.error("任务锁文件", e);
        }
        finally {
            if (lock != null) {
                try {
                    lock.release();
                    lock = null;
                }
                catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }

            if (channel != null) {
                try {
                    channel.close();
                    channel = null;
                }
                catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
    }

}
