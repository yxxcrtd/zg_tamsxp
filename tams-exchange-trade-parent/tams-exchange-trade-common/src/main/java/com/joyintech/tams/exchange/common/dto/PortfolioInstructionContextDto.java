package com.joyintech.tams.exchange.common.dto;

import com.joyintech.tams.exchange.common.db.domain.Instruction;

import java.math.BigDecimal;

/**
 * @author swwan
 * @date 2022/1/10 - 13:59
 */
public class PortfolioInstructionContextDto {

    /**
     * 指令
     */
    private Instruction instruction;

    /**
     * 指令剩余委托数量
     */
    private long leftVolume;

    /**
     * 指令剩余委托金额
     */
    private BigDecimal leftAmt;

    /**
     * 指令委托价格
     */
    private BigDecimal entrustPrice;

    /**
     * 指令委托数量
     */
    private long entrustVolume;

    /**
     * 指令委托金额
     */
    private BigDecimal entrustBalance;

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public long getLeftVolume() {
        return leftVolume;
    }

    public void setLeftVolume(long leftVolume) {
        this.leftVolume = leftVolume;
    }

    public BigDecimal getLeftAmt() {
        return leftAmt;
    }

    public void setLeftAmt(BigDecimal leftAmt) {
        this.leftAmt = leftAmt;
    }

    public BigDecimal getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(BigDecimal entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public long getEntrustVolume() {
        return entrustVolume;
    }

    public void setEntrustVolume(long entrustVolume) {
        this.entrustVolume = entrustVolume;
    }

    public BigDecimal getEntrustBalance() {
        return entrustBalance;
    }

    public void setEntrustBalance(BigDecimal entrustBalance) {
        this.entrustBalance = entrustBalance;
    }
}
