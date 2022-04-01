package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

import java.util.Date;

/**
 * 描述:MUTIPTL_PORTFOLIO_TREE表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2021-11-12
 */
public class MutiptlPortfolioTree extends BaseEntity {
    /**
     * 多组合树代码
     */
    private String treeId;

    /**
     * 多组合树名称
     */
    private String treeName;

    /**
     * 多组合树父代码
     */
    private String parTreeId;

    /**
     * 录入方式,画面,自动生成
     */
    private String inputType;

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
     * 多组合树名称
     * @return TREE_NAME 多组合树名称
     */
    public String getTreeName() {
        return treeName;
    }

    /**
     * 多组合树名称
     * @param treeName 多组合树名称
     */
    public void setTreeName(String treeName) {
        this.treeName = treeName == null ? null : treeName.trim();
    }

    /**
     * 多组合树父代码
     * @return PAR_TREE_ID 多组合树父代码
     */
    public String getParTreeId() {
        return parTreeId;
    }

    /**
     * 多组合树父代码
     * @param parTreeId 多组合树父代码
     */
    public void setParTreeId(String parTreeId) {
        this.parTreeId = parTreeId == null ? null : parTreeId.trim();
    }

    /**
     * 录入方式,画面,自动生成
     * @return INPUT_TYPE 录入方式,画面,自动生成
     */
    public String getInputType() {
        return inputType;
    }

    /**
     * 录入方式,画面,自动生成
     * @param inputType 录入方式,画面,自动生成
     */
    public void setInputType(String inputType) {
        this.inputType = inputType == null ? null : inputType.trim();
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