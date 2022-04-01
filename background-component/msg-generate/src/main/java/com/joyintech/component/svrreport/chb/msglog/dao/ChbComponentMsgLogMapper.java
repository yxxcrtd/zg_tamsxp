/*
 * 文件名：ChbComponentMsgLogMapper.java
 * 版权：Copyright 2020-2022 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年10月10日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
package com.joyintech.component.svrreport.chb.msglog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.joyintech.component.svrreport.chb.dto.ChbInfoReportDto;
import com.joyintech.component.svrreport.chb.dto.ChbSubmitStatusDto;
import com.joyintech.component.svrreport.chb.dto.InterfLog;
import org.springframework.stereotype.Repository;

/**
 * @author 王波
 * @date 2020年10月10日
 * @see ChbComponentMsgLogMapper
 * @since 1.0
 */
@Repository
public interface ChbComponentMsgLogMapper {

    /**
     * 登陆接口日志
     *
     * @param log 日志
     * @return 影响记录数
     * @see
     * @since 1.0
     */
    int insertInterfLog(InterfLog log);

    /**
     * 插入中债直连表
     * @param infoReportDto
     * @return
     */
    int insertChbInfoReport(ChbInfoReportDto infoReportDto);

    /**
     * 投资者明细报送状态表
     * @return
     */
    int insertChbEquDetSubmitStatus(ChbSubmitStatusDto submitStatusDto);
    
    /**
     * 中债报送状态表
     * @return
     */
    int insertChbSubmitStatusTwo(ChbSubmitStatusDto submitStatusDto);

    /**
     * 删除中债报送状态表
     * @return
     */
    int deleteChbSubmitStatusTwo(@Param("seqno")String seqno);

    /**
     * 查询中债直连报送报送编码
     *
     * @param report 报送报文编码
     * @return 报送编码
     * @see
     * @since 1.0
     */
    List<ChbInfoReportDto> selectReportNoByRepMesNo(@Param("repMesNo")String repMesNo);
    
    
    /**
     * 根据登记银行代码获取机构代码
     *
     * @param paramMap 查询参数
     * @return 机构代码
     * @see
     * @since 1.0
     */
    String selectOrgCodeByRegistBankCode(Map<String, Object> paramMap);

    int delChbInfoReportByMesNo(@Param("repMesNo")String repMesNo);
}
