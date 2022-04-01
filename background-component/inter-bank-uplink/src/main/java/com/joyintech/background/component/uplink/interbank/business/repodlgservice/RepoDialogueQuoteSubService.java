/*
 * 文件名：RepoDialogueQuoteSubService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Jiaoxujin
 * 修改时间：2020年5月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
package com.joyintech.background.component.uplink.interbank.business.repodlgservice;

import java.util.List;

import com.joyintech.background.component.common.pack.common.grid.Page;
import com.joyintech.background.component.uplink.interbank.business.dto.BondDialogueQuoteDto;
import com.joyintech.background.component.uplink.interbank.business.dto.GateWayReturnMsgDto;
import com.joyintech.background.component.uplink.interbank.business.dto.QuoteResultBatDto;
import com.joyintech.background.component.uplink.interbank.business.dto.QuoteResultDto;
import com.joyintech.background.component.uplink.interbank.business.dto.RepoDialogueQuoteDto;
import com.joyintech.background.component.uplink.interbank.business.dto.ValidMessageDto;
import com.joyintech.background.component.uplink.interbank.business.dto.local.BondQuoteHisSearchDto;

/**
 * 质押式回购报价服务接口
 * @author Jiaoxujin
 * @version 1.0 2020年5月6日
 * @see RepoDialogueQuoteSubService
 * @since 1.0
 */
public interface RepoDialogueQuoteSubService {

    /**
     * 质押式回购报价查询<br />
     * 根据主键查询质押式回购报价信息
     * @param quoteId String 报价编号
     * @return RepoDialogueQuoteDto
     */
    RepoDialogueQuoteDto getRepoDialogueQuote(String quoteId);

    /**
     * 质押式回购报价查询<br />
     * 根据主键查询质押式回购报价信息
     * @param quoteId String 报价编号
     * @return RepoDialogueQuoteDto
     */
    RepoDialogueQuoteDto getWflRepoDialogueQuote(String quoteId);

    /**
     * 质押式回购报价查询-指定条件<br />
     * 根据传入的不为空的属性进行查询，需支持数据权限
     * @param inputBean RepoQuoteSearchDto 质押式回购报价查询条件
     * @return List<RepoDialogueQuoteDto>
     */
    List<RepoDialogueQuoteDto> getRepoDialogueQuoteHisList(BondQuoteHisSearchDto inputBean);

    /**
     * 质押式回购报价查询-按指定条件分页查询<br />
     * 根据传入的不为空的变量进行查询，以分页形式返回查询结果，需支持数据权限
     * @param pageNum int 页码，从1开始
     * @param pageSize int 分页大小
     * @param inputBean RepoQuoteSearchDto 质押式回购报价查询条件
     * @return Page<RepoDialogueQuoteDto>
     */
    Page<RepoDialogueQuoteDto> getRepoDialogueQuoteHisPageList(int pageNum, int pageSize,
                                                               BondQuoteHisSearchDto inputBean);

    /**
     * 质押式回购报价必须项检验<br />
     * 对数据库或CFETS明确规定必输进行校验
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @return ValidMessageDto
     */
    ValidMessageDto validateRequiredItems(RepoDialogueQuoteDto inputBean);

    /**
     * 质押式回购报价状态检验<br />
     * 质押式回购报价进行操作时，需校验目前质押式回购报价的报价状态和发送状态是否允许该项操作。
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @return ValidMessageDto
     */
    ValidMessageDto validateRepoDialogueQuoteStatus(RepoDialogueQuoteDto inputBean, boolean needSearch);

    /**
     * 质押式回购报价状态检验<br />
     * 质押式回购报价进行操作时，需校验目前质押式回购报价的报价状态和发送状态是否允许该项操作。
     * @param inputList List<RepoDialogueQuoteDto> 质押式回购报价信息
     * @return List<ValidMessageDto>
     */
    List<ValidMessageDto> validateRepoDialogueQuoteStatus(List<RepoDialogueQuoteDto> inputList, boolean needSearch);

    /**
     * 批量检验质押式回购报价必须项<br />
     * @param inputList List<RepoDialogueQuoteDto> 质押式回购报价信息列表
     * @return List<ValidMessageDto>
     */
    List<ValidMessageDto> batchValidateRequiredItems(List<RepoDialogueQuoteDto> inputList);

    /**
     * 批量质押式回购报价状态检验<br />
     * @param inputList List<RepoDialogueQuoteDto> 质押式回购报价信息列表
     * @return List<ValidMessageDto>
     */
    List<ValidMessageDto> batchValidateRepoDialogueQuoteStatus(List<RepoDialogueQuoteDto> inputList);
    
    /**
     * 质押式回购报价新增<br />
     * 质押式回购报价信息入库
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @param isNew 是否为新增记录（有时更新数据时采取的是先删后插的方式，则为非新增）
     * @return QuoteResultDto
     */
    QuoteResultDto insertRepoDialogueQuote(RepoDialogueQuoteDto inputBean, boolean isNew);

    /**
     * 批量质押式回购报价新增<br />
     * 质押式回购报价信息批量入库
     * @param inputList List<RepoDialogueQuoteDto> 质押式回购报价信息列表
     * @return QuoteResultBatDto
     */
    QuoteResultBatDto batchInsertRepoDialogueQuote(List<RepoDialogueQuoteDto> inputList);

    /**
     * 根据主键进行质押式回购报价更新<br />
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @return QuoteResultDto
     */
    QuoteResultDto updateRepoDialogueQuoteByKey(RepoDialogueQuoteDto inputBean);

    /**
     * 根据主键进行质押式回购报价更新<br />
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @return QuoteResultDto
     */
    QuoteResultDto updateRepoDialogueQuoteByKeyDeal(RepoDialogueQuoteDto inputBean);

    /**
     * 根据主键进行质押式回购报价删除<br />
     * 根据报价代码删除数据
     * @param quoteId String 报价编号
     * @return QuoteResultDto
     */
    QuoteResultDto deleteRepoDialogueQuoteByKey(String quoteId, boolean deleteHis);

    /**
     * 质押式回购报价状态更新<br />
     * 根据操作类型更新质押式回购报价信息的报价状态及发送状态
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @return RepoDialogueQuoteDto
     */
    void updateRepoDialogueQuoteStatus(RepoDialogueQuoteDto inputBean);

    /**
     * 质押式回购报价接口更新<br />
     * 根据操作类型更新质押式回购报价接口反馈信息
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @return RepoDialogueQuoteDto
     */
    void updateRepoDialogueQuoteInterf(RepoDialogueQuoteDto inputBean);
    
    /**
     * 根据主键进行质押式回购报价回退操作<br />
     * @param inputBean RepoDialogueQuoteDto 现券对话报价信息
     * @return QuoteResultDto
     */
    QuoteResultDto restoreRepoDialogueQuoteByKey(RepoDialogueQuoteDto inputBean);
    
    /**
     * 更新质押式回购报价信息中的版本号、外部报价编号、请求编号、对手机构代码21位编号<br />
     * 包括对历史表中的数据更新
     * @param inputBean GateWayReturnMsgDto
     * @return String 验证消息
     */
    QuoteResultDto updateRepoDialogueQuoteVersion(GateWayReturnMsgDto inputBean);
    
    /**
     * 质押式回购根据外部报价编号查询报价编号<br />
     * @param pquoteId String
     * @return String
     */
    String getRepoQuoteIdByPquoteId(String pquoteId);
    
    /**
     * 对话报价新增<br />
     * 对话报价信息入流程表
     * @param inputBean BondDialogueQuoteDto 现券对话报价信息
     * @param isNew 是否为新增记录（有时更新数据时采取的是先删后插的方式，则为非新增）
     * @return QuoteResultDto
     */
    QuoteResultDto insertWflRepoDialogueQuote(RepoDialogueQuoteDto inputBean,  boolean isNew);
    
    /**
     * 对话报价新增<br />
     * 对话报价信息入流程表
     * @param 质押式回购根据外部报价编号查询报价编号<br />
     * @return QuoteResultDto
     */
    QuoteResultDto deleteWflRepoDialogueQuoteByKey(String quoteId);

    int insertHistoryData(RepoDialogueQuoteDto inputBean);
    
}
