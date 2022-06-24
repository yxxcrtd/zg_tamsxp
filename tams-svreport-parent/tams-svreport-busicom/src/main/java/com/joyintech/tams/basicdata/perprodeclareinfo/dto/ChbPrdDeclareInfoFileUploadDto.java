package com.joyintech.tams.basicdata.perprodeclareinfo.dto;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

/**
 * @author wubingbing
 * @date 2022-06-09 18:01
 */
public class ChbPrdDeclareInfoFileUploadDto extends BaseEntity implements ProcessEntity {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 附件上传
     */
    private List<UploadFileInfo> fileArray;

    /**
     * 产品申报dto
     */
    private List<SvrPerProDeclareInfo001Dto> uploadArrStr;

    public List<UploadFileInfo> getFileArray() {
        return fileArray;
    }

    public void setFileArray(List<UploadFileInfo> fileArray) {
        this.fileArray = fileArray;
    }

    public List<SvrPerProDeclareInfo001Dto> getUploadArrStr() {
        return uploadArrStr;
    }

    public void setUploadArrStr(List<SvrPerProDeclareInfo001Dto> uploadArrStr) {
        this.uploadArrStr = uploadArrStr;
    }
}
