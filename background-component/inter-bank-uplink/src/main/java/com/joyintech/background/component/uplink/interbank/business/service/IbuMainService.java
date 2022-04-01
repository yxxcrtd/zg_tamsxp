/*
 * 文件名：IbuMainService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Jiaoxujin
 * 修改时间：2020年5月7日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
package com.joyintech.background.component.uplink.interbank.business.service;

import com.joyintech.background.component.common.pack.common.grid.Page;
import com.joyintech.background.component.common.pack.common.security.User;
import com.joyintech.background.component.uplink.interbank.business.dto.*;
import com.joyintech.background.component.uplink.interbank.database.entity.IbuIntfScheme;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 银行间上行组件服务统一接口
 * 
 * @author Jiaoxujin
 * @version 1.0 2020年5月7日
 * @see IbuMainService
 * @since 1.0
 */
public interface IbuMainService {

    /**
     * 现券报价查询-指定条件<br />
     * 根据传入的不为空的属性进行查询，需支持数据权限
     * 
     * @param inputBean BondQuoteSearchDto 现券报价查询条件
     * @param userInfo User 登录用户信息
     * @return List<BondQuoteExtDto>
     */
    List<BondQuoteExtDto> getBondQuoteList(BondQuoteSearchDto inputBean, User userInfo);

    /**
     * 现券报价查询-按指定条件分页查询<br />
     * 根据传入的不为空的变量进行查询，以分页形式返回查询结果，需支持数据权限
     * 
     * @param pageNum int 页码，从1开始
     * @param pageSize int 分页大小
     * @param inputBean BondQuoteSearchDto 现券报价查询条件
     * @param userInfo User 登录用户信息
     * @return Page<BondQuoteExtDto>
     */
    Page<BondQuoteExtDto> getBondQuotePageList(int pageNum, int pageSize,
                                               BondQuoteSearchDto inputBean, User userInfo);

    /**
     * 现券对话报价查询<br />
     * 根据主键查询对话报价信息
     * 
     * @param quoteId String 报价编号
     * @return BondDialogueQuoteDto
     */
    BondDialogueQuoteDto getBondDialogueQuote(String quoteId);

    /**
     * 现券请求报价查询<br />
     * 根据主键查询请求报价信息
     * 
     * @param quoteId String 报价编号
     * @return BondRequestQuoteDto
     */
    BondRequestQuoteDto getBondRequestQuote(String quoteId);
    
    /**
     * 现券请求报价查询<br />
     * 根据主键查询请求报价信息
     * 
     * @param quoteId String 报价编号
     * @return BondRequestQuoteDto
     */
    BondRequestQuoteDto getWflBondRequestQuote(String quoteId);

    /**
     * 质押式回购报价查询-指定条件<br />
     * 根据传入的不为空的属性进行查询，需支持数据权限
     * 
     * @param inputBean RepoQuoteSearchDto 质押式回购报价查询条件
     * @param userInfo User 登录用户信息
     * @return List<RepoDialogueQuoteDto>
     */
    List<RepoQuoteExtDto> getRepoDialogueQuoteList(RepoQuoteSearchDto inputBean,
                                                        User userInfo);
    /**
     * 质押式回购报价查询-指定条件<br />
     * 根据传入的不为空的属性进行查询，需支持数据权限
     * add by chenjiaxing 质押式回购报价改造
     *
     * @param inputBean RepoQuoteSearchDto 质押式回购报价查询条件
     * @param userInfo User 登录用户信息
     * @return List<RepoDialogueQuoteDto>
     */
    List<RepoQuoteExtDto> getRepoDialogueQuoteList2(RepoQuoteSearchDto inputBean,
                                                   User userInfo);

    /**
     * 质押式回购报价查询-指定条件<br />
     * 根据传入的不为空的属性进行查询，需支持数据权限
     *
     * @param inputBean RepoQuoteSearchDto 质押式回购报价查询条件
     * @param userInfo  User 登录用户信息
     * @return List<RepoDialogueQuoteDto>
     */
    com.joyintech.background.component.common.pack.common.grid.Page<RepoQuoteExtDto> getRepoDialogueQuoteList(int pageNum, int pageSize, RepoQuoteSearchDto inputBean,
                                                                User userInfo);

    /**
     * 质押式回购报价查询-指定条件<br />
     * 根据传入的不为空的属性进行查询，需支持数据权限
     * add by chenjiaxing 质押式回购报价改造
     *
     * @param inputBean RepoQuoteSearchDto 质押式回购报价查询条件
     * @param userInfo  User 登录用户信息
     * @return List<RepoDialogueQuoteDto>
     */
    com.joyintech.background.component.common.pack.common.grid.Page<RepoQuoteExtDto> getRepoDialogueQuoteList2(int pageNum, int pageSize, RepoQuoteSearchDto inputBean,
                                                                 User userInfo);

    /**
     * 质押式回购报价查询-按指定条件分页查询<br />
     * 根据传入的不为空的变量进行查询，以分页形式返回查询结果，需支持数据权限
     * 
     * @param pageNum int 页码，从1开始
     * @param pageSize int 分页大小
     * @param inputBean RepoQuoteSearchDto 质押式回购报价查询条件
     * @param userInfo User 登录用户信息
     * @return Page<RepoDialogueQuoteDto>
     */
    Page<RepoQuoteExtDto> getRepoDialogueQuotePageList(int pageNum, int pageSize,
                                                            RepoQuoteSearchDto inputBean,
                                                            User userInfo);
    /**
     * 现券报价根据成交编号获取委托编号
     * @param execid
     * @return String
     */
    String getBondEntrustIdByExeid(String execid);
    
    /**
     * 质押式回购报价查询<br />
     * 根据主键查询质押式回购报价信息
     * 
     * @param quoteId String 报价编号
     * @return RepoDialogueQuoteDto
     */
    RepoDialogueQuoteDto getRepoDialogueQuote(String quoteId);
    
    /**
     * 
     * 删除对话报价的主表，主表历史，明细，明细历史数据
     * @param quoteId
     * @return QuoteResultDto
     * @see
     * @since 1.0
     */
    QuoteResultDto deleteBondDialogueQuote(String quoteId);
    
    
    /**
     * 
     * 删除债券流程中数据
     * @param workflowId
     * @return QuoteResultDto
     * @see
     * @since 1.0
     */
    boolean deleteWflDataForBond(String workflowId);
    
    
    
    /**
     * 
     * 删除回购流程中数据
     * @param workflowId
     * @return QuoteResultDto
     * @see
     * @since 1.0
     */
    boolean deleteWflDataForRepo(String workflowId);
    
    
    /**
     * 根据流程编号查询报价编号
     * @param pageWflType
     * @param workflowId
     * @return
     */
    String selectWflQuoteIdByWorkflowId(String pageWflType,String workflowId);
    
    
    /**
     * 现券对话报价新增<br />
     * 对话报价信息入流程表
     * 
     * @param inputBean BondDialogueQuoteDto 现券对话报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto insertWflBondDialogueQuote(BondDialogueQuoteDto inputBean, User userInfo);

    /**
     * 现券对话报价新增<br />
     * 对话报价信息入库
     * 
     * @param inputBean BondDialogueQuoteDto 现券对话报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto insertBondDialogueQuote(BondDialogueQuoteDto inputBean, User userInfo);

    /**
     * 批量现券对话报价新增<br />
     * 对话报价信息批量入库
     * 
     * @param inputList List<BondDialogueQuoteDto> 现券对话报价信息列表
     * @param userInfo User 登录用户信息
     * @return QuoteResultBatDto
     */
    QuoteResultBatDto batchInsertBondDialogueQuote(List<BondDialogueQuoteDto> inputList,
                                                   User userInfo);

    /**
     * 现券对话报价批量新增发送<br />
     * @param inputList List<BondDialogueQuoteDto>
     * @param userInfo User 登录用户信息
     * @param QuoteResultBatDto
     */
    QuoteResultBatDto batchNewBondDialogueQuote(List<BondDialogueQuoteDto> inputList,
                                                       User userInfo);
    /**
     * 现券对话报价修改
     * @param inputBean BondDialogueQuoteDto 现券对话报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto updateBondDialogueQuoteByKey(BondDialogueQuoteDto inputBean, User userInfo);

    /**
     * 现券对话报价新增发送<br />
     * 根据传入的对话报价新增进行发送，并更新全部报价信息
     * 
     * @param inputBean BondDialogueQuoteDto 现券对话报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendNewBondDialogueQuote(BondDialogueQuoteDto inputBean, User userInfo);
    
    /**
     * 现券对话报价修改发送<br />
     * 根据传入的对话报价新增进行发送，并更新全部报价信息
     * 
     * @param inputBean BondDialogueQuoteDto 现券对话报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendUpdateBondDialogueQuote(BondDialogueQuoteDto inputBean, User userInfo);

    /**
     * 现券对话报价新增发送_报价代码<br />
     * 仅对指定报价代码进行发送，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondDialogueQuote(String quoteId, User userInfo);

    /**
     * 现券对话报价回复成交<br />
     * 报价回复成交发送处理，更新接口返回数据
     * 
     * @param inputBean BondDialogueQuoteDto
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondDialogueQuoteDeal(BondDialogueQuoteDto inputBean, Map<String, String> attrMap, User userInfo);
    
    /**
     * 现券请求报价修改<br />
     * 报价修改发送处理，更新接口返回数据
     * 
     * @param inputBean BondDialogueQuoteDto
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteModify(BondRequestQuoteDto inputBean, List<String> detIdList,Map<String, String> attrMap, User userInfo);

    /**
     * 现券对话报价修改<br />
     * 报价修改发送处理，更新接口返回数据
     * 
     * @param inputBean BondDialogueQuoteDto
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendRepoDialogueQuoteModify(RepoDialogueQuoteDto inputbean, Map<String, String> attrMap,User userInfo);
    
    /**
     * 现券对话报价回复拒绝<br />
     * 报价回复拒绝发送处理，更新接口返回数据
     * 
     * @param inputBean BondDialogueQuoteDto
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondDialogueQuoteRefuse(BondDialogueQuoteDto inputBean, Map<String, String> attrMap, User userInfo);
    
    /**
     * 现券对话报价撤销<br />
     * 报价回复撤销，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondDialogueQuoteCancel(String quoteId, Map<String, String> attrMap, User userInfo);

    /**
     * 现券对话报价撤回；类似“撤回邮件”<br />
     * 报价撤回发送处理，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondDialogueQuoteRevoke(String quoteId, Map<String, String> attrMap, User userInfo);

    /**
     * 现券对话报价回退；把失败状态还原成待处理<br />
     * 报价回退发送处理，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto bondDialogueQuoteRestore(String quoteId, Map<String, String> attrMap, User userInfo);

    /**
     * 接口接收处理
     * 
     * @param msg String 报文
     * @return QuoteResultDto
     */
    //QuoteResultDto recevieMsgFromInterf(String msg);
    
    /**
     * 更新对话报价的接口反馈信息<br />
     * 处理接口异步返回结果
     * @param inputBean ReceiveBondDialogueQuoteDto
     * @param userInfo User
     * @return QuoteResultDto
     */
    QuoteResultDto updateBondDialogueQuoteInterfResp(ReceiveBondDialogueQuoteDto inputBean, User userInfo);
    
    /**
     * 
     * 删除请求报价的主表，主表历史，明细，明细历史数据
     * @param quoteId
     * @return QuoteResultDto
     * @see
     * @since 1.0
     */
    QuoteResultDto deleteBondRequestQuote(String quoteId);
    
    /**
     * 现券请求报价新增<br />
     * 请求报价新增信息入库
     * 操作类型 ：本方请求报价新增
     * @param inputBean BondRequestQuoteDto 现券请求报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto insertWflBondRequestQuote(BondRequestQuoteDto inputBean, User userInfo);

    /**
     * 现券请求报价新增<br />
     * 请求报价新增信息入库
     * 操作类型 ：本方请求报价新增
     * @param inputBean BondRequestQuoteDto 现券请求报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto insertBondRequestQuote(BondRequestQuoteDto inputBean, User userInfo);
    
    /**
     * 批量现券请求报价新增<br />
     * 请求报价信息批量入库
     * 操作类型 ：本方请求报价新增
     * @param inputList List<BondRequestQuoteDto> 现券请求报价信息列表
     * @param userInfo User 登录用户信息
     * @return QuoteResultBatDto
     */
    QuoteResultBatDto batchInsertBondRequestQuote(List<BondRequestQuoteDto> inputList,
                                                  User userInfo);
    
    /**
     * 现券请求报价批量新增发送<br />
     * 操作类型 ：本方新增请求报价发送
     * @param inputList List<BondRequestQuoteDto>
     * @param userInfo User 登录用户信息
     * @param QuoteResultBatDto
     */
    QuoteResultBatDto batchNewBondRequestQuote(List<BondRequestQuoteDto> inputList,
                                                       User userInfo);

    /**
     * 现券请求报价修改<br />
     * 请求报价修改信息入库
     * 
     * @param inputBean BondRequestQuoteDto 现券请求报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    @Deprecated
    QuoteResultDto updateBondRequestQuoteByKey(BondRequestQuoteDto inputBean, User userInfo);

    /**
     * 现券请求报价新增发送<br />
     * 根据传入的请求报价新增进行发送，并更新全部报价信息
     * 操作类型 ：本方新增请求报价发送
     * @param inputBean BondRequestQuoteDto 请求报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendNewBondRequestQuote(BondRequestQuoteDto inputBean, User userInfo);

    /**
     * 现券请求报价修改发送<br />
     * 根据传入的请求报价修改进行发送，并更新全部报价信息
     * 
     * @param inputBean BondRequestQuoteDto 请求报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    @Deprecated
    QuoteResultDto sendUpdatedBondRequestQuote(BondRequestQuoteDto inputBean, User userInfo);

    /**
     * 现券请求报价新增发送_报价代码<br />
     * 仅对指定报价代码进行发送，更新接口返回数据
     * 操作类型 ：本方新增请求报价发送
     * @param quoteId String 报价编号
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuote(String quoteId, User userInfo);

    /**
     * 现券请求报价回复成交<br />
     * 报价回复成交发送处理，更新接口返回数据
     * 操作类型 ：本方请求回复成交
     * @param inputBean BondRequestQuoteDto
     * @param detIdList 明细编号列表
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteDeal(BondRequestQuoteDto inputBean, List<String> detIdList, Map<String, String> attrMap, User userInfo);

    /**
     * 现券请求报价回复拒绝<br />
     * 报价回复拒绝发送处理，更新接口返回数据
     * 操作类型：本方拒绝请求报价
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteRefuse(String quoteId, Map<String, String> attrMap, User userInfo);

    /**
     * 现券请求报价撤回；类似“撤回邮件”<br />
     * 报价撤回发送处理，更新接口返回数据
     * 操作类型：本方请求报价撤回
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteRevoke(String quoteId, Map<String, String> attrMap, User userInfo);
    
    /**
     * 现券请求报价撤销<br />
     * 报价回复撤销，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteCancel(String quoteId, Map<String, String> attrMap, User userInfo);

    /**
     * 现券请求报价回退；把失败状态还原成待处理<br />
     * 报价回退发送处理，更新接口返回数据
     * 操作类型：本方报价回退
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteRestore(String quoteId, Map<String, String> attrMap, User userInfo);
    
    /**
     * 本方撤销请求回复；<br />
     * 做市方回复非做市方请求报价
     * 操作类型：本方新增请求回复
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteNewReply(BondRequestQuoteDto bondRequestQuote, Map<String, String> attrMap, User userInfo);
    
    /**
     * 本方修改请求回复；<br />
     * 做市方修改回复非做市方请求报价
     * 操作类型：本方修改请求回复
     * @param inputBean BondRequestQuoteDto
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteUpdateReply(BondRequestQuoteDto inputBean, Map<String, String> attrMap, User userInfo);
    
    /**
     * 本方撤销请求回复；<br />
     * 做市方撤销回复非做市方请求报价
     * 操作类型：本方修改请求回复
     * @param quoteId String
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendBondRequestQuoteRevokeReply(String quoteId, Map<String, String> attrMap, User userInfo);

    /**
     * 更新请求报价的接口反馈信息
     * @param inputBean ReceiveBondRequestQuoteDto
     * @param userInfo User
     * @return QuoteResultDto
     */
    QuoteResultDto updateBondRequestQuoteInterfResp(ReceiveBondRequestQuoteDto inputBean, User userInfo);

    /**
     * 质押式回购根据成交编号获取委托编号
     * @param execid
     * @return String
     */
    String getRepoEntrustIdByExeid(String execid);
    
    /**
     * 质押式回购报价新增<br />
     * 质押式回购报价新增信息入库
     * 
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto insertRepoDialogueQuote(RepoDialogueQuoteDto inputBean, User userInfo);
    
    /**
     * 质押式回购报价新增<br />
     * 质押式回购报价新增信息入库
     * 
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto insertWflRepoDialogueQuote(RepoDialogueQuoteDto inputBean, User userInfo);
    
    /**
     * 
     * 删除质押式回购报价的主表，主表历史，明细，明细历史数据
     * @param quoteId
     * @return QuoteResultDto
     * @see
     * @since 1.0
     */
    QuoteResultDto deleteRepoDialogueQuote(String quoteId);

    /**
     * 批量质押式回购报价新增<br />
     * 质押式回购报价信息批量入库
     * 
     * @param inputList List<RepoDialogueQuoteDto> 质押式回购报价信息列表
     * @param userInfo User 登录用户信息
     * @return QuoteResultBatDto
     */
    QuoteResultBatDto batchInsertRepoDialogueQuote(List<RepoDialogueQuoteDto> inputList,
                                                   User userInfo);
    
    /**
     * 质押式回购报价批量新增发送<br />
     * @param inputList List<RepoDialogueQuoteDto>
     * @param userInfo User 登录用户信息
     * @param QuoteResultBatDto
     */
    QuoteResultBatDto batchNewRepoDialogueQuote(List<RepoDialogueQuoteDto> inputList,
                                                       User userInfo);

    /**
     * 质押式回购报价修改<br />
     * 质押式回购报价修改信息入库
     * 
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto updateRepoDialogueQuoteByKey(RepoDialogueQuoteDto inputBean, User userInfo);

    /**
     * 质押式回购报价新增发送<br />
     * 根据传入的质押式回购报价新增进行发送，并更新全部报价信息
     * 
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendNewRepoDialogueQuote(RepoDialogueQuoteDto inputBean, User userInfo);

    /**
     * 质押式回购报价修改发送<br />
     * 根据传入的质押式回购报价修改进行发送，并更新全部报价信息
     * 
     * @param inputBean RepoDialogueQuoteDto 质押式回购报价信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendUpdatedRepoDialogueQuote(RepoDialogueQuoteDto inputBean, Map<String, String> attrMap, User userInfo);

    /**
     * 质押式回购报价新增发送_报价代码<br />
     * 仅对指定报价代码进行发送，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendRepoDialogueQuote(String quoteId, User userInfo);

    /**
     * 质押式回购报价回复成交<br />
     * 报价回复成交发送处理，更新接口返回数据
     * 
     * @param inputbean RepoDialogueQuoteDto
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendRepoDialogueQuoteDeal(RepoDialogueQuoteDto inputbean, Map<String, String> attrMap, User userInfo);

    /**
     * 质押式回购报价回复拒绝<br />
     * 报价回复拒绝发送处理，更新接口返回数据
     * 
     * @param inputbean RepoDialogueQuoteDto
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendRepoDialogueQuoteRefuse(RepoDialogueQuoteDto inputbean, Map<String, String> attrMap, User userInfo);

    /**
     * 质押式回购报价撤回；类似“撤回邮件”<br />
     * 报价撤回发送处理，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendRepoDialogueQuoteRevoke(String quoteId, Map<String, String> attrMap, User userInfo);
    
    /**
     * 质押式回购报价撤销<br />
     * 报价回复撤销，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendRepoDialogueQuoteCancel(String quoteId, Map<String, String> attrMap, User userInfo);

    /**
     * 质押式回购报价回退；把失败状态还原成待处理<br />
     * 报价回退发送处理，更新接口返回数据
     * 
     * @param quoteId String 报价编号
     * @param attrMap Map<String, String>
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto sendRepoDialogueQuoteRestore(String quoteId, Map<String, String> attrMap, User userInfo);
    
    /**
     * 更新质押式回购报价的接口反馈信息
     * @param inputBean ReceiveRepoDialogueQuoteDto
     * @param userInfo User
     * @return QuoteResultDto
     */
    QuoteResultDto updateRepoDialogueQuoteInterfResp(ReceiveRepoDialogueQuoteDto inputBean, User userInfo);

    /**
     * 报文查询
     * 
     * @param condition 查询条件
     * @return 报文列表
     */
    List<String> queryMessgeIbu(String condition);

    /**
     * 根据属性查询路由配置方案 属性MAP <功能详细描述>
     * 
     * @param attrMap 属性MAP
     * @return 配置方案
     * @see
     * @since 1.0
     */
    IbuIntfScheme getSchemeForAttr(Map<String, String> attrMap);

    /**
     * 查询默认方案
     * 
     * @return 配置方案
     * @see
     * @since 1.0
     */
    IbuIntfScheme getDefaultScheme();

    /**
     * 路由配置方案新增 新增路由配置方案
     * 
     * @param ibuIntfScheme
     * @return 处理结果
     * @see
     * @since 1.0
     */
    ResultBaseDto insertScheme(IbuIntfScheme ibuIntfScheme, User userInfo);
    
    /**
     * 路由配置方案修改
     * 
     * @param schemeId 方案代码，主键
     * @param ibuIntfScheme 待更新路由配置方案信息，空属性自动跳过
     * @return 处理结果
     * @see
     * @since 1.0
     */
    ResultBaseDto updateScheme(String schemeId, IbuIntfScheme ibuIntfScheme, User userInfo);

    /**
     * 路由配置方案删除 根据主键删除配置方案
     * 
     * @param schemeId 方案代码，主键
     * @return 处理结果
     * @see
     * @since 1.0
     */
    ResultBaseDto deleleScheme(String schemeId, User userInfo);

    /**
     * 路由配置方案默认方案指定 指定传入的方案代码为默认方案
     * 
     * @param schemeId
     * @return 处理结果
     * @see
     * @since 1.0
     */
    ResultBaseDto setDefaultScheme(String schemeId, User userInfo);

    /**
     * 报价路由方案新增 新增报价路由方案
     * 
     * @param SchemeId 方案代码
     * @param attrMap 属性列表
     * @return
     * @see
     * @since 1.0
     */
    ResultBaseDto insertQuoteScheme(String schemeId, Map<String, String> attrMap, User userInfo);

    /**
     * 报价路由方案修改 根据属性组代码，旧方案代码删除报价路由方案，重新根据属性列表和新方案代码重新生成报价路由方案
     * 
     * @param oldSchemeId 旧方案代码
     * @param newSchemeId 新方案代码
     * @param arrtGrpId 属性组代码
     * @param attrMap 属性列表
     * @return
     * @see
     * @since 1.0
     */
    ResultBaseDto updateQuoteScheme(String oldSchemeId, String newSchemeId, String arrtGrpId,
                                  Map<String, String> attrMap, User userInfo);

    /**
     * 报价路由方案删除 删除报价路由方案
     * 
     * @param schemeId 方案代码
     * @param arrtGrpId 属性组代码
     * @return
     * @see
     * @since 1.0
     */
    ResultBaseDto deleteQuoteScheme(String schemeId, String arrtGrpId, User userInfo);

    /**
     * 接收网关回复现券对话报价信息<br />
     * 接收网关回复现券对话报价信息
     * 
     * @param inputBean GateWayReturnMsgDto 网关返回信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto receBondDlgQuoteGateWayMsg(GateWayReturnMsgDto inputBean, User userInfo);
    
    /**
     * 接收网关回复现券请求报价信息<br />
     * 接收网关回复现券请求报价信息
     * 
     * @param inputBean GateWayReturnMsgDto 网关返回信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto receBondReqQuoteGateWayMsg(GateWayReturnMsgDto inputBean, User userInfo);
    
    
    /**
     * 接收网关回复回购对话报价信息<br />
     * 接收网关回复回购对话报价信息
     * 
     * @param inputBean GateWayReturnMsgDto 网关返回信息
     * @param userInfo User 登录用户信息
     * @return QuoteResultDto
     */
    QuoteResultDto receRepoDlgQuoteGateWayMsg(GateWayReturnMsgDto inputBean, User userInfo);
    
    /**
     * 获取质押面值<br />
     * 
     * @param quoteId String 报价编号
     * @param quoteDate Date 报价日期
     * @param pledgeFinprodId String 质押金融产品代码
     * @return BigDecimal
     */
    BigDecimal getPledgeFaceValue(String quoteId,Date quoteDate,String pledgeFinprodId);

    /**
     * 银行间上行错误码分页查询<br />
     * 根据传入的不为空的变量进行查询，以分页形式返回查询结果，需支持数据权限
     * 
     * @param pageNum int 页码，从1开始
     * @param pageSize int 分页大小
     * @param ibuInrfErrMsgSearchDto IbuInrfErrMsgSearchDto 银行间上行错误码查询条件
     * @return Page<IbuIntfErrMsgDto>
     */
    Page<IbuIntfErrMsgDto> selectForPage(int pageNum, int pageSize,
                                         IbuInrfErrMsgSearchDto ibuInrfErrMsgSearchDto);
    
    /**
     * 银行间上行错误码查询<br />
     * 根据传入的不为空的变量进行查询，需支持数据权限
     * 
     * @param pageNum int 页码，从1开始
     * @param pageSize int 分页大小
     * @param ibuInrfErrMsgSearchDto IbuInrfErrMsgSearchDto 银行间上行错误码查询条件
     * @return List<IbuIntfErrMsgDto>
     */
    List<IbuIntfErrMsgDto> selectIbuIntfErrMsgList(IbuInrfErrMsgSearchDto ibuInrfErrMsgSearchDto);
    
    /**
     * 根据错误码和错误信息模糊查询<br />
     * 
     * @param errCode String 错误码
     * @return IbuIntfErrMsgDto
     */
    IbuIntfErrMsgDto selectByErrCode(String errCode);
    
    /**
     * 
     * 根据报价编号查询错误信息
     * @param quoteId String 
     * @return IbuQuoteErrlogDto
     * @see
     * @since 1.0
     */
    IbuQuoteErrlogDto selectErrlog(String quoteId);

    /**
     * 查询已经存在quoteId列表
     * @param quoteIdList List<String>
     * @return List<String>
     */
    List<String> selectQuoteIdsByIdList(List<String> quoteIdList);

    /**
     * 查询已经存在detId列表
     * @param detIdList List<String>
     * @return List<String>
     */
    List<String> selectDetIdsByIdList(List<String> detIdList);

    /**
     * 对话报价流程表查询
     * @param quoteId
     * @return
     */
	BondDialogueQuoteDto getWflBondDialogueQuote(String quoteId);

    // add by etliu 20210318 银行间上下行多组合 start
    /**
     * 根据报价编号，查询银行间报价指令关系业务表数据
     * @param quoteId 报价编号
     * @return 银行间报价指令关系业务表数据
     */
    List<IbuQuoteEtrReDto> selectQuoteEtrReDataByQuoteId(String quoteId);
    // add by etliu 20210318 银行间上下行多组合 end
}
