/*
 * 文件名：CnBondInterfMapper.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：何阳阳
 * 修改时间：2017年10月31日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.interf.cnbond.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbInfoReport;
import com.joyintech.tams.common.base.entity.ChbSubmitStatus;


/**
 * 中债报送mapper
 *
 * @author 何阳阳
 * @version 1.0 2017年10月31日
 * @see CnBondInterfMapper
 * @since 1.0
 */
@Repository
public interface CnBondInterfMapper {

    /**
     * 查询中债直连报送报送编码
     *
     * @param report 报送报文编码
     * @return 报送编码
     * @see
     * @since 1.0
     */
    List<ChbInfoReport> selectReportNoByRepMesNo(ChbInfoReport report);

    /**
     * 更新反馈状态
     *
     * @param status 中债报送结果
     * @return 更新结果
     * @see
     * @since 1.0
     */
    int updateChbSubmitStatus(ChbSubmitStatus status);
    /**
     * 更新投资者明细反馈状态
     *
     * @param status 中债报送结果
     * @return 更新结果
     * @see
     * @since 1.0
     */
    int updateChbEquDetSubmitStatus(ChbSubmitStatus status);
    /**
     * 更新投资者明细反馈状态
     *
     * @param status 中债报送结果
     * @return 更新结果
     * @see
     * @since 1.0
     */
    int mergerChbEquDetSubmitStatus(List<ChbSubmitStatus> dataList);

    /**
     * 登录中债直连报送数据
     *
     * @param chbInfoReportList 中债直连报送数据
     * @see
     * @since 1.0
     */
    void insertChbInfoReportList(@Param("dataList") List<ChbInfoReport> chbInfoReportList);

    /**
     * 登录中债报送状态数据
     *
     * @param chbInfoReportList 中债报送状态数据
     * @see
     * @since 1.0
     */
    /***三期CHB_SUBMIT_STATUS去除改造  20220310**/
    void insertChbSubmitStatusList(@Param("chbSubmitStatusList") List<ChbSubmitStatus> chbSubmitStatusList,@Param("SubmitsType") String  SubmitsType);
    /***三期CHB_SUBMIT_STATUS去除改造  20220310**/
    /**
     * 登录中债投资者明细报送状态数据
     *
     * @param chbSubmitStatusList 中债报送状态数据
     * @see
     * @since 1.0
     */
    void insertChbEquDetSubmitStatusList(@Param("dataList") List<ChbSubmitStatus> chbSubmitStatusList);

    /**
     * 根据登记银行代码获取机构代码
     *
     * @param paramMap 查询参数
     * @return 机构代码
     * @see
     * @since 1.0
     */
    String selectOrgCodeByRegistBankCode(Map<String, Object> paramMap);
    
    /**
     * 根据登记银行代码获取机构代码
     *
     * @param paramMap 查询参数
     * @return 机构代码
     * @see
     * @since 1.0
     */
    List<String> selectReportFile(Map<String, Object> paramMap);
}