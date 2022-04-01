package com.joyintech.component.svrreport.chb.chbcommoncheck.dto;

/**
 * 描述:CHB_DATACHECK_RULE表的实体类
 * @version
 * @author:  zhangliang
 * @创建时间: 2020-11-27
 */
public class ChbCheckRepType {

    /**
     * 画面Id
     */
    private String pageId;
    /**
     * 匹配类型
     */
    private String repType;
    /**
     * 匹配属性
     */
    private String srcproperty;


    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public String getSrcproperty() {
        return srcproperty;
    }

    public void setSrcproperty(String srcproperty) {
        this.srcproperty = srcproperty;
    }
}
