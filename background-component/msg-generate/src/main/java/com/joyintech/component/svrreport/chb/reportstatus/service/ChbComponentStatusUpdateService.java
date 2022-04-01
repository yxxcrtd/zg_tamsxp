/*
 * 文件名：ChbComponentStatusUpdateService.java
 * 版权：Copyright 2020-2022 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年10月12日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */

package com.joyintech.component.svrreport.chb.reportstatus.service;

import cn.hutool.core.util.StrUtil;
import com.joyintech.component.svrreport.chb.contant.ChbConstant;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.component.svrreport.chb.dto.ChbBondSubmitPara;
import com.joyintech.component.svrreport.chb.dto.ChbMsgBuildInfoDto;
import com.joyintech.component.svrreport.chb.dto.ChbMsgReturnInfoDto;
import com.joyintech.component.svrreport.chb.msglog.service.ChbComponentMsgLogService;
import com.joyintech.component.svrreport.chb.reportstatus.dao.ChbComponentStatusUpdateMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 状态更新Serice
 * @author 王波
 * @date 2020年10月12日
 * @see ChbComponentStatusUpdateService
 * @since 1.0
 */
@Service
public class ChbComponentStatusUpdateService {

    private Logger logger = LoggerFactory.getLogger(ChbComponentStatusUpdateService.class);

    /**
     * 没多少次提交一次
     */
    @Value("${application.chb.maxSqlNum:2000}")
    private int maxSqlNum;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@linkplain}
     */
    @Autowired
    private ChbComponentMsgLogService chbComponentMsgLogService;

    /**
     * 更新接口发送状态
     * @param registSeqs 编码
     * @param dealType 类型
     * @param sendStatus 报送状态
     * @param feedbackStatus 反馈状态
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSendStatus(List<String> registSeqs, String dealType, String sendStatus, String feedbackStatus) {
        // 获取sql 连接， 批量执行模式
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            ChbComponentStatusUpdateMapper msgLogMapper = sqlSession.getMapper(ChbComponentStatusUpdateMapper.class);
            int index = 0;
            if (StrUtil.equals(DictCodeList.报表名称_资产估值信息登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbAssetValSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_产品申报登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdDeclareSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_产品发行登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdIssueSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_募集总量登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdRaisetotSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_产品存续期登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdDealSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_产品终止登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdEndSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_资管计划底层持仓信息登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbBottomAstPosSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_资产交易信息登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbInvestTradeSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_资产要素信息登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbAssetInfoSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_从业人员登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbEmployeeInfoSubSendStatus(registSeq, sendStatus, feedbackStatus);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("批量执行失败", e);
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }

    }

    /**
     * 更新中债反馈状态
     * @param registSeqs 编码
     * @param dealType 类型
     * @param status 状态
     */
    public void updateFeedbackStatus(List<String> registSeqs, String dealType, String status) {
        // 获取sql 连接， 批量执行模式
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            ChbComponentStatusUpdateMapper msgLogMapper = sqlSession.getMapper(ChbComponentStatusUpdateMapper.class);
            int index = 0;
            if (StrUtil.equals(DictCodeList.报表名称_资产估值信息登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbAssetValSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_产品申报登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdDeclareSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_产品发行登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdIssueSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_募集总量登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdRaisetotSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_产品存续期登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdDealSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_产品终止登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbPrdEndSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_资管计划底层持仓信息登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbBottomAstPosSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_资产交易信息登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbInvestTradeSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_资产要素信息登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbAssetInfoSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
            else if (StrUtil.equals(DictCodeList.报表名称_从业人员登记.getValue(), dealType)) {
                for (String registSeq : registSeqs) {
                    msgLogMapper.updateChbEmployeeInfoSubSubmitStatus(registSeq, status);
                    index ++;
                    if (index == maxSqlNum) {
                        sqlSession.commit();
                        index = 0;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("批量执行失败", e);
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 中债直连发送返回值处理
     * @param chbMsgReturnInfoDto
     * @param para
     * @param chbMsgBuildInfoDto
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void sendBackHandle(ChbMsgReturnInfoDto chbMsgReturnInfoDto, ChbBondSubmitPara para, ChbMsgBuildInfoDto chbMsgBuildInfoDto){
        if(chbMsgReturnInfoDto == null){
            //当返回结果为null时，更新反馈状态为发送异常
            this.updateFeedbackStatus(para.getRegistSeq() , para.getDealType(), DictCodeList.报送反馈状态_发送异常.getValue());
            logger.info("反馈信息:发送异常");
        }else{
            logger.info("反馈信息:"+chbMsgReturnInfoDto.getRetCode());
            if (ChbConstant.XCPTNRTRCD_0000.equals(chbMsgReturnInfoDto.getRetCode())) {
                // 更新数据库状态
                this.updateFeedbackStatus(para.getRegistSeq()
                        , para.getDealType(), DictCodeList.报送反馈状态_发送成功.getValue());
            } else {
                this.updateFeedbackStatus(para.getRegistSeq()
                        , para.getDealType(), DictCodeList.报送反馈状态_发送失败.getValue());
            }
            //有返回结果时
            chbComponentMsgLogService.insertInterfLogList(chbMsgBuildInfoDto.getLogList());
            chbComponentMsgLogService.insertReportInfo(chbMsgReturnInfoDto.getChbInfoReportDto());
            chbComponentMsgLogService.insertSubmitStatusInfo(chbMsgReturnInfoDto.getChbSubmitStatusDtos());
        }
    }
}
