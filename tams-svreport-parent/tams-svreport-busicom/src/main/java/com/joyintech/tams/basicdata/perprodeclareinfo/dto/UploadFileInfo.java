package com.joyintech.tams.basicdata.perprodeclareinfo.dto;

import com.joyintech.framework.base.BaseEntity;

/**
 * @author wubingbing
 * @date 2022-06-09 18:04
 */
public class UploadFileInfo extends BaseEntity {
    /**
     * 流水号
     */
    private String seqNo;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 路径
     */
    private String path;

    /**
     * 业务代码
     */
    private String businessId;

    /**
     * 文件编号
     */
    private String fileNo;

    /**
     * 文件路径
     */
    private String targetFile;

    /**
     * 文件名
     */
    private String srcName;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }
}
