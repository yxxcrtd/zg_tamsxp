package com.joyintech.tams.svreport.pbc.common.util;

import java.io.IOException;
import java.util.Properties;

import com.jcraft.jsch.*;
import com.joyintech.framework.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author liushuai
 * @version 1.0 2019年08月10日
 * @see
 * @since 1.0
 */
@Service
public class SftpUtil {
	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(SftpUtil.class);

	    private static ChannelSftp sftp = new ChannelSftp();
	    
	    private static Session sshSession;
	    /**
	     * 日志
	     */
	    private static Logger log = LoggerFactory.getLogger(SftpUtil.class);
	    

	    /**
	     * sftp将本地文件上传到远程服务器
	     * 
	     * @param local 本地文件路径
	     * @param remote 远程文件路径
	     */
	    public static void putFile(String local, String remote) {
	        try {
	            sftp.put(local, remote);
	            log.info("文件上传成功");
	        }
	        catch (SftpException e) {
	        	log.error("error:",e);
	            log.info("文件上传失败");
	        }
	        SFTPDisConnect();
	    }
	    /**
	     * 关闭ftp
	     * 
	     * @throws IOException
	     */
	    public static void SFTPDisConnect() {
	        if (sftp.isConnected()) {
	            sftp.disconnect();
	            log.info("成功关闭连接SFTP！");
	        }
	        if (sshSession != null) {
	            sshSession.disconnect();
	        }
	    }
	    /**
	     * Sftp 公共连接配置 连接sftp服务器
	     * 
	     * @param host
	     *            主机
	     * @param port
	     *            端口
	     * @param username
	     *            用户名
	     * @param password
	     *            密码
	     * @return
	     * @throws Exception
	     */
	    public static void SFTPconnect(String userName, String ftpIp, int ftpPort, String password) {
	        // 接口 SFTP 服务配置
	        try {
	            JSch jsch = new JSch();
	            jsch.getSession(userName, ftpIp, ftpPort);
	            sshSession = jsch.getSession(userName, ftpIp, ftpPort);
	            sshSession.setPassword(password);
	            Properties sshConfig = new Properties();
	            sshConfig.put("StrictHostKeyChecking", "no");
	            sshSession.setConfig(sshConfig);
	            sshSession.connect();
	            Channel channel = sshSession.openChannel("sftp");
	            channel.connect();
	            sftp = (ChannelSftp) channel;
	            // log.info("成功登录SFTP:" + config.getFtpIp() + ".");
	        } catch (JSchException e) {
	            log.error("登录SFTP:" + ftpIp + "失败.");
	            throw new BusinessException("登录SFTP失败!!!");
	        }
	    }
	    /**
	     * 通过打开指定的文件夹路径的方式，看文件是否存在
	     * 
	     * @param dirName
	     * @return
	     */
	    public static boolean dirIsHave(String dirName) {
	        try {
	            sftp.cd(dirName);
	            return true;
	        } catch (SftpException e) {
				logger.error(e.getMessage(),e);
	            return false;
	        }
	    }
}
