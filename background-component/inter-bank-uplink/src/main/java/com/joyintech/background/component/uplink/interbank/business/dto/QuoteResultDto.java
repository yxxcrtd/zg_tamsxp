/*
 * 文件名：resultDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：caoguo
 * 修改时间：2020年5月7日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.background.component.uplink.interbank.business.dto;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joyintech.background.component.uplink.interbank.common.CommonUtils;
import com.joyintech.background.component.uplink.interbank.common.DictCodeList;

/**
 * 处理结果<br />
 * 只可能存储现卷对话报价、现卷请求报价和质押式回购报价的其中之一，不可能同时存储这三者的信息
 * 
 * @author caoguo
 * @version 1.0 2020年5月7日
 * @see QuoteResultDto
 * @since 1.0
 */
public class QuoteResultDto extends ResultBaseDto {
    private Logger log = LoggerFactory.getLogger(getClass());
    
    /**
     * 发送方式
     */
    private String sendType;

    /**
     * 现券对话报价DTO
     */
    private BondDialogueQuoteDto bondDialogueQuote;
    
    /**
     * 现券请求报价DTO
     */
    private BondRequestQuoteDto bondRequestQuote;
    
    /**
     * 质押式回购报价DTO
     */
    private RepoDialogueQuoteDto repoDialogueQuote;
    /**
     *数据已入新头寸，Y：已入
     */
    private String isExistNew;

    public QuoteResultDto() {
        super.setStatus(true);
    }
    
    public QuoteResultDto(boolean status) {
        super(status);
    }

    /**
     * 获取报价类型
     */
    public String getQuoteType() {
        if (this.bondDialogueQuote != null) {
            return DictCodeList.报价系统区分类型_现券对话报价.getValue();
        }
        else if (this.bondRequestQuote != null) {
            return DictCodeList.报价系统区分类型_现券请求报价.getValue();
        }
        else if (this.repoDialogueQuote != null) {
            return DictCodeList.报价系统区分类型_质押式回购对话报价.getValue();
        }
        
        return null;
    }

    /**
     * 获取 baseQuoteDto
     * 
     * @return baseQuoteDto.
     */
    public BaseQuoteDto getBaseQuoteDto() {
        BaseQuoteDto baseQuote = new BaseQuoteDto();
        try {
            if (this.bondDialogueQuote != null) {
                CommonUtils.setBaseQuoteDto(baseQuote, this.bondDialogueQuote.getIbuQuoteBondDto(),
                    this.bondDialogueQuote.getIbuQuoteBondDetDto());
            }
            else if (bondRequestQuote != null) {
                CommonUtils.setBaseQuoteDto(baseQuote, this.bondRequestQuote.getIbuQuoteBondDto(), null);
            }
            else if (this.repoDialogueQuote != null) {
                BeanUtils.copyProperties(baseQuote, this.repoDialogueQuote.getIbuQuoteCrepDto());
                //List<IbuQuoteCrepPledge> crepPledgeList = this.repoDialogueQuote.getIbuQuoteCrepPledge();
                //finprodId branch
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return baseQuote;
    }
    
 // add by etliu 20210315 银行间上下行多组合 start
    /**
     * 获取银行间报价指令关系业务表数据
     * @return 银行间报价指令关系业务表数据
     */
    public List<IbuQuoteEtrReDto> getIbuQuoteEtrReDtoList () {
        if (this.bondDialogueQuote != null) {
            return this.bondDialogueQuote.getIbuQuoteEtrReDtoList();
        }
        else if (bondRequestQuote != null) {
            return this.bondRequestQuote.getIbuQuoteEtrReDtoList();
        }
        else if (this.repoDialogueQuote != null) {
            return this.repoDialogueQuote.getIbuQuoteEtrReDtoList();
        }

        return null;
    }
 // add by etliu 20210315 银行间上下行多组合 end

    /**
     * 获取 bondDialogueQuote
     * @return bondDialogueQuote.
     */
    public BondDialogueQuoteDto getBondDialogueQuote() {
        return bondDialogueQuote;
    }

    /**
     * 设置 bondDialogueQuote
     * @param bondDialogueQuote bondDialogueQuote
     */
    public void setBondDialogueQuote(BondDialogueQuoteDto bondDialogueQuote) {
        this.bondDialogueQuote = bondDialogueQuote;
    }

    /**
     * 获取 bondRequestQuote
     * @return bondRequestQuote.
     */
    public BondRequestQuoteDto getBondRequestQuote() {
        return bondRequestQuote;
    }
    
    /**
     * 设置 bondRequestQuote
     * @param bondRequestQuote bondRequestQuote
     */
    public void setBondRequestQuote(BondRequestQuoteDto bondRequestQuote) {
        this.bondRequestQuote = bondRequestQuote;
    }

    /**
     * 获取质押式回购报价信息
     */
    public RepoDialogueQuoteDto getRepoDialogueQuote() {
        return this.repoDialogueQuote;
    }

    /**
     * 设置 repoDialogueQuote（质押式回购报价）
     * @param repoDialogueQuote repoDialogueQuote
     */
    public void setRepoDialogueQuote(RepoDialogueQuoteDto repoDialogueQuote) {
        this.repoDialogueQuote = repoDialogueQuote;
    }

    /**
     * 获取 sendType
     * @return sendType.
     */
    public String getSendType() {
        return sendType;
    }

    /**
     * 设置 sendType
     * @param sendType sendType
     */
    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getIsExistNew() {
        return isExistNew;
    }

    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }
}
