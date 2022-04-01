/*
 * 文件名：DictCodeList.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Jiaoxujin
 * 修改时间：2020年5月9日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
package com.joyintech.background.component.uplink.interbank.common;

import com.joyintech.background.component.common.pack.common.base.BaseDictCodeList;
import com.joyintech.background.component.common.pack.common.util.StringUtil;

/**
 * 银行间上行组件字典明细定义
 * @author Jiaoxujin
 * @version 1.0 2020年5月9日
 * @see DictCodeList
 * @since 1.0
 */
public enum DictCodeList implements BaseDictCodeList {
    银行间上行报价状态_新增中(IbuDictCode.银行间上行报价状态,"01","新增中"),
    银行间上行报价状态_已新增(IbuDictCode.银行间上行报价状态,"02","已新增"),
    银行间上行报价状态_修改中(IbuDictCode.银行间上行报价状态,"03","修改中"),
    银行间上行报价状态_已修改(IbuDictCode.银行间上行报价状态,"04","已修改"),
    银行间上行报价状态_撤回中(IbuDictCode.银行间上行报价状态,"05","撤回中"),
    银行间上行报价状态_已撤回(IbuDictCode.银行间上行报价状态,"06","已撤回"),
    银行间上行报价状态_已成交(IbuDictCode.银行间上行报价状态,"07","已成交"),
    银行间上行报价状态_已终止(IbuDictCode.银行间上行报价状态,"08","已终止"),
    银行间上行报价状态_待回复(IbuDictCode.银行间上行报价状态,"09","待回复"),
    银行间上行报价状态_成交中(IbuDictCode.银行间上行报价状态,"10","成交中"),
    银行间上行报价状态_终止中(IbuDictCode.银行间上行报价状态,"11","终止中"),
    银行间上行报价状态_已回复(IbuDictCode.银行间上行报价状态,"12","已回复"),
    银行间上行报价状态_已过期(IbuDictCode.银行间上行报价状态,"13","已过期"),
    银行间上行报价状态_已拒绝(IbuDictCode.银行间上行报价状态,"14","已拒绝"),
    银行间上行报价状态_已撤销(IbuDictCode.银行间上行报价状态,"15","已撤销"),
    银行间上行报价状态_拒绝中(IbuDictCode.银行间上行报价状态,"16","拒绝中"),
    银行间上行报价状态_回复中(IbuDictCode.银行间上行报价状态,"17","回复中"),
    银行间上行报价状态_交谈中(IbuDictCode.银行间上行报价状态,"18","交谈中"),
    银行间上行报价状态_已交谈(IbuDictCode.银行间上行报价状态,"19","已交谈"),
    银行间上行发送状态_待发送(IbuDictCode.银行间上行发送状态,"00","待发送"),
    银行间上行发送状态_已发送(IbuDictCode.银行间上行发送状态,"01","已发送"),
    银行间上行发送状态_成功(IbuDictCode.银行间上行发送状态,"02","成功"),
    银行间上行发送状态_失败(IbuDictCode.银行间上行发送状态,"03","失败"),
    银行间上行操作类型_本方报价新增(IbuDictCode.银行间上行操作类型,"01","本方报价新增"),
    银行间上行操作类型_本方新增发送(IbuDictCode.银行间上行操作类型,"02","本方新增发送"),
    银行间上行操作类型_本方新增发送成功(IbuDictCode.银行间上行操作类型,"03","本方新增发送成功"),
    银行间上行操作类型_本方新增发送失败(IbuDictCode.银行间上行操作类型,"04","本方新增发送失败"),
    银行间上行操作类型_本方报价修改(IbuDictCode.银行间上行操作类型,"05","本方报价修改"),
    银行间上行操作类型_本方修改发送(IbuDictCode.银行间上行操作类型,"06","本方修改发送"),
    银行间上行操作类型_本方修改发送成功(IbuDictCode.银行间上行操作类型,"07","本方修改发送成功"),
    银行间上行操作类型_本方修改发送失败(IbuDictCode.银行间上行操作类型,"08","本方修改发送失败"),
    银行间上行操作类型_本方回复成交(IbuDictCode.银行间上行操作类型,"09","本方回复成交"),
    银行间上行操作类型_本方回复拒绝(IbuDictCode.银行间上行操作类型,"10","本方回复拒绝"),
    银行间上行操作类型_本方回复成交成功(IbuDictCode.银行间上行操作类型,"11","本方回复成交成功"),
    银行间上行操作类型_本方回复拒绝成功(IbuDictCode.银行间上行操作类型,"12","本方回复拒绝成功"),
    银行间上行操作类型_本方回复成交失败(IbuDictCode.银行间上行操作类型,"13","本方回复成交失败"),
    银行间上行操作类型_本方回复拒绝失败(IbuDictCode.银行间上行操作类型,"14","本方回复拒绝失败"),
    银行间上行操作类型_本方报价撤回(IbuDictCode.银行间上行操作类型,"15","本方报价撤回"),
    银行间上行操作类型_本方报价撤回成功(IbuDictCode.银行间上行操作类型,"16","本方报价撤回成功"),
    银行间上行操作类型_本方报价撤回失败(IbuDictCode.银行间上行操作类型,"17","本方报价撤回失败"),
    银行间上行操作类型_本方报价回退(IbuDictCode.银行间上行操作类型,"18","本方报价回退"),
    银行间上行操作类型_对手方报价新增(IbuDictCode.银行间上行操作类型,"19","对手方报价新增"),
    银行间上行操作类型_对手方报价修改(IbuDictCode.银行间上行操作类型,"20","对手方报价修改"),
    银行间上行操作类型_对手方回复成交(IbuDictCode.银行间上行操作类型,"21","对手方回复成交"),
    银行间上行操作类型_对手方回复拒绝(IbuDictCode.银行间上行操作类型,"22","对手方回复拒绝"),
    银行间上行操作类型_对手方报价撤回(IbuDictCode.银行间上行操作类型,"23","对手方报价撤回"),
    银行间上行操作类型_对手方交谈新增(IbuDictCode.银行间上行操作类型,"24","对手方交谈新增"),
    银行间上行操作类型_对手方交谈修改(IbuDictCode.银行间上行操作类型,"25","对手方交谈修改"),
    银行间上行操作类型_接收已过期(IbuDictCode.银行间上行操作类型,"26","接收已过期"),
    银行间上行操作类型_对手方请求回复(IbuDictCode.银行间上行操作类型,"27","对手方请求回复"),
    银行间上行操作类型_对手方拒绝报价(IbuDictCode.银行间上行操作类型,"28","对手方拒绝报价"),
    银行间上行操作类型_对手方回复报价修改(IbuDictCode.银行间上行操作类型,"29","对手方回复报价修改"),
    银行间上行操作类型_本方请求报价新增(IbuDictCode.银行间上行操作类型,"30","本方请求报价新增"),
    银行间上行操作类型_本方新增请求报价发送(IbuDictCode.银行间上行操作类型,"31","本方新增请求报价发送"),
    银行间上行操作类型_本方新增请求报价发送成功(IbuDictCode.银行间上行操作类型,"32","本方新增请求报价发送成功"),
    银行间上行操作类型_本方新增请求报价发送失败(IbuDictCode.银行间上行操作类型,"33","本方新增请求报价发送失败"),
    银行间上行操作类型_本方请求报价撤回(IbuDictCode.银行间上行操作类型,"34","本方请求报价撤回"),
    银行间上行操作类型_本方请求报价撤回成功(IbuDictCode.银行间上行操作类型,"35","本方请求报价撤回成功"),
    银行间上行操作类型_本方请求报价撤回失败(IbuDictCode.银行间上行操作类型,"36","本方请求报价撤回失败"),
    银行间上行操作类型_接收全拒绝请求报价(IbuDictCode.银行间上行操作类型,"37","接收全拒绝请求报价"),
    银行间上行操作类型_本方请求回复成交(IbuDictCode.银行间上行操作类型,"38","本方请求回复成交"),
    银行间上行操作类型_本方请求回复成交成功(IbuDictCode.银行间上行操作类型,"39","本方请求回复成交成功"),
    银行间上行操作类型_本方请求回复成交失败(IbuDictCode.银行间上行操作类型,"40","本方请求回复成交失败"),
    银行间上行操作类型_接收请求回复成交成功(IbuDictCode.银行间上行操作类型,"41","接收请求回复成交成功"),
    银行间上行操作类型_对手方拒绝请求报价(IbuDictCode.银行间上行操作类型,"42","对手方拒绝请求报价"),
    银行间上行操作类型_对手方新增请求回复(IbuDictCode.银行间上行操作类型,"43","对手方新增请求回复"),
    银行间上行操作类型_对手方修改请求回复(IbuDictCode.银行间上行操作类型,"44","对手方修改请求回复"),
    银行间上行操作类型_对手方撤销请求回复(IbuDictCode.银行间上行操作类型,"45","对手方撤销请求回复"),
    银行间上行操作类型_对手方新增请求报价(IbuDictCode.银行间上行操作类型,"46","对手方新增请求报价"),
    银行间上行操作类型_对手方撤销请求报价(IbuDictCode.银行间上行操作类型,"47","对手方撤销请求报价"),
    银行间上行操作类型_本方拒绝请求报价(IbuDictCode.银行间上行操作类型,"48","本方拒绝请求报价"),
    银行间上行操作类型_本方拒绝请求报价成功(IbuDictCode.银行间上行操作类型,"49","本方拒绝请求报价成功"),
    银行间上行操作类型_本方拒绝请求报价失败(IbuDictCode.银行间上行操作类型,"50","本方拒绝请求报价失败"),
    银行间上行操作类型_本方新增请求回复(IbuDictCode.银行间上行操作类型,"51","本方新增请求回复"),
    银行间上行操作类型_本方新增请求回复成功(IbuDictCode.银行间上行操作类型,"52","本方新增请求回复成功"),
    银行间上行操作类型_本方新增请求回复失败(IbuDictCode.银行间上行操作类型,"53","本方新增请求回复失败"),
    银行间上行操作类型_本方修改请求回复(IbuDictCode.银行间上行操作类型,"54","本方修改请求回复"),
    银行间上行操作类型_本方修改请求回复成功(IbuDictCode.银行间上行操作类型,"55","本方修改请求回复成功"),
    银行间上行操作类型_本方修改请求回复失败(IbuDictCode.银行间上行操作类型,"56","本方修改请求回复失败"),
    银行间上行操作类型_本方撤销请求回复(IbuDictCode.银行间上行操作类型,"57","本方撤销请求回复"),
    银行间上行操作类型_本方撤销请求回复成功(IbuDictCode.银行间上行操作类型,"58","本方撤销请求回复成功"),
    银行间上行操作类型_本方撤销请求回复失败(IbuDictCode.银行间上行操作类型,"59","本方撤销请求回复失败"),
    
    //add by lijie2
    银行间上行操作类型_本方报价交谈(IbuDictCode.银行间上行操作类型,"61","本方报价交谈"),
    银行间上行操作类型_本方交谈发送(IbuDictCode.银行间上行操作类型,"62","本方交谈发送"),
    银行间上行操作类型_本方交谈发送成功(IbuDictCode.银行间上行操作类型,"63","本方交谈发送成功"),
    银行间上行操作类型_本方交谈发送失败(IbuDictCode.银行间上行操作类型,"64","本方交谈发送失败"),
    银行间上行操作类型_本方交谈修改(IbuDictCode.银行间上行操作类型,"65","本方交谈修改"),
    银行间上行操作类型_本方交谈修改发送(IbuDictCode.银行间上行操作类型,"66","本方交谈修改发送"),
    银行间上行操作类型_本方交谈修改发送成功(IbuDictCode.银行间上行操作类型,"67","本方交谈修改发送成功"),
    银行间上行操作类型_本方交谈修改发送失败(IbuDictCode.银行间上行操作类型,"68","本方交谈修改发送失败"),


    银行间上行操作类型_撤销(IbuDictCode.银行间上行操作类型,"60","撤销"),
    报价系统区分类型_现券对话报价(IbuDictCode.报价系统区分类型,"01","现券对话报价"),
    报价系统区分类型_现券请求报价(IbuDictCode.报价系统区分类型,"02","现券请求报价"),
    报价系统区分类型_质押式回购对话报价(IbuDictCode.报价系统区分类型,"03","质押式回购对话报价"),
    银行间上行等待方式_同步(IbuDictCode.银行间上行等待方式,"01","同步"),
    银行间上行等待方式_异步(IbuDictCode.银行间上行等待方式,"02","异步"),
    银行间上行本方角色_询价方(IbuDictCode.银行间上行本方角色,"01","询价方"),
    银行间上行本方角色_回复方(IbuDictCode.银行间上行本方角色,"02","回复方"),
    银行间上行本方角色_非做市方(IbuDictCode.银行间上行本方角色,"03","非做市方"),
    银行间上行本方角色_做市方(IbuDictCode.银行间上行本方角色,"04","做市方"),
    银行间上行发送模式_CFTES直联(IbuDictCode.银行间上行发送模式,"01","CFTES直联"),
    银行间上行发送模式_JET(IbuDictCode.银行间上行发送模式,"02","JET"),

    /**
     * 是
     */
    是否_是(IbuDictCode.是否,"Y","是"),
    /**
     * 否
     */
    是否_否(IbuDictCode.是否,"N","否"),
    
    银行间上行数据状态_流程中(IbuDictCode.银行间上行数据状态,"04","流程中"),
    银行间上行数据状态_有效(IbuDictCode.银行间上行数据状态,"01","有效")
    ;

    private String value;

    public void setDictCode(IbuDictCode dictCode) {
        this.dictCode = dictCode;
    }

    private IbuDictCode dictCode;

    /**
     * 明细名称
     */
    private String codeName;

    DictCodeList(IbuDictCode dictCode, String value, String codeName) {
        this.dictCode = dictCode;
        this.value = value;
        this.setCodeName(codeName);
    }

    /**
     * 获得数据字典详细类
     *
     * @param value 数据字典值
     * @param dictCode 数据字典IbuDictCode
     * @return DictCodeList
     * @see
     * @since 1.0
     */
    public static DictCodeList from(IbuDictCode dictCode, String value) {
        DictCodeList[] values = DictCodeList.values();
        for (DictCodeList code : values) {
            if (StringUtil.equals(value, code.getValue()) && code.getDictCode() == dictCode) {
                return code;
            }
        }
        return null;
    }

    /**
     * 获取 IbuDictCode
     *
     * @return value.
     */
    @Override
    public IbuDictCode getDictCode() {
        return dictCode;
    }

    /**
     * 获取 value
     *
     * @return value.
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 设置 value
     *
     * @param value value
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取 codeName
     *
     * @return codeName.
     */
    @Override
    public String getCodeName() {
        return codeName;
    }

    /**
     * 设置 codeName
     *
     * @param codeName codeName
     */
    @Override
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

}
