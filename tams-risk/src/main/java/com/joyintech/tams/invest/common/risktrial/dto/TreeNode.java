package com.joyintech.tams.invest.common.risktrial.dto;

import java.io.Serializable;

public class TreeNode implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1140183117777486707L;

    /**
     * 节点id
     */
    private String id;

    /**
     * 父节点id
     */
    private String parentId;
    
    private String pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否check
     */
    private boolean checked;

    /**
     * 是否展开
     */
    private boolean open;
    
    /**
     * 额外数据：类型
     */
    private String type;
    
    /**
     * 是否显示checkbox
     */
    private boolean nocheck;
    
    

    

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	/**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the parentId.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId The parentId to set.
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the checked.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked The checked to set.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * @return Returns the open.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @param open The open to set.
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}
    
    
}
