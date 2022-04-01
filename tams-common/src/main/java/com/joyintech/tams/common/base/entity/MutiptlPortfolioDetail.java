package com.joyintech.tams.common.base.entity;

import com.joyintech.workflow.engine.annotation.PrimaryKey;

import java.util.Date;

/**
 * 描述:MUTIPTL_PORTFOLIO_DETAIL表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2021-11-12
 */
public class MutiptlPortfolioDetail {
    /**
     * 序号
     */
    @PrimaryKey(PrimaryKey.Field.PK_1)
    private String seqNo;

    /**
     * 多组合树代码
     */
    private String treeId;

    /**
     * 组合代码
     */
    private String portfolioId;

    /**
     * 资金账户
     */
    private String acctNo;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 生效日
     */
    private Date effDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建部门
     */
    private String createDept;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 序号
     * @return SEQ_NO 序号
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * 序号
     * @param seqNo 序号
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    /**
     * 多组合树代码
     * @return TREE_ID 多组合树代码
     */
    public String getTreeId() {
        return treeId;
    }

    /**
     * 多组合树代码
     * @param treeId 多组合树代码
     */
    public void setTreeId(String treeId) {
        this.treeId = treeId == null ? null : treeId.trim();
    }

    /**
     * 组合代码
     * @return PORTFOLIO_ID 组合代码
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * 组合代码
     * @param portfolioId 组合代码
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId == null ? null : portfolioId.trim();
    }

    /**
     * 资金账户
     * @return ACCT_NO 资金账户
     */
    public String getAcctNo() {
        return acctNo;
    }

    /**
     * 资金账户
     * @param acctNo 资金账户
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo == null ? null : acctNo.trim();
    }

    /**
     * 币种
     * @return CCY 币种
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 币种
     * @param ccy 币种
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 生效日
     * @return EFF_DATE 生效日
     */
    public Date getEffDate() {
        return effDate;
    }

    /**
     * 生效日
     * @param effDate 生效日
     */
    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    /**
     * 创建人
     * @return CREATE_USER 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 创建部门
     * @return CREATE_DEPT 创建部门
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     * 创建部门
     * @param createDept 创建部门
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新人
     * @return UPDATE_USER 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 更新时间
     * @return UPDATE_TIME 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}