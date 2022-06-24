package com.joyintech.tams.basicdata.perprodeclareinfo.dto;

import com.joyintech.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wubingbing
 * @date 2022-06-09 18:16
 */
public enum ChbPrdDeclareInfoFileCode {

    报告主文件("01", "报告主文件", false, false, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx"),

    理财产品可行性评估报告("02", "理财产品可行性评估报告", true, false, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx"),

    内部审核文件("03", "内部审核文件", true, true, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx"),

    尽职调查文件("04", "对理财产品投资管理人、托管人、投资顾问等相关方的尽职调查文件", true, false, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx"),

    法律文件("05", "与理财产品投资管理人、托管人、投资顾问等相关方签署的法律文件", true, true, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx"),

    理财产品销售文件("06", "理财产品销售文件", true, false, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx"),

    理财产品说明书("07", "理财产品说明书", true, false, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx"),

    理财产品宣传材料("08", "理财产品宣传材料", true, false, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx"),

    其他材料("09", "其他材料", false, false, 20, "doc,docx,xls,xlsx,zip,pdf,jpg,jpeg,bmp,png,gif,gd,ppt,pptx");



    /**
     * 文件夹序号
     */
    private String folderNo;

    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 是否必填
     */
    private boolean isNend;

    /**
     * 是否共同文件
     */
    private boolean isComm;

    /**
     * 包含文件大小
     */
    private long fileSize;

    /**
     * 包含文件类型
     */
    private String fileType;

    ChbPrdDeclareInfoFileCode(String folderNo, String folderName, boolean isNend, boolean isComm, long fileSize, String fileType) {
        this.folderNo = folderNo;
        this.folderName = folderName;
        this.isNend = isNend;
        this.isComm = isComm;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public String getFolderNo() {
        return folderNo;
    }

    public String getFolderName() {
        return folderName;
    }

    public boolean isNend() {
        return isNend;
    }

    public boolean isComm() {
        return isComm;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public static List<String> genFolderNoList(){
        List<String> folderNoList = new ArrayList<>();
        ChbPrdDeclareInfoFileCode[] allData = ChbPrdDeclareInfoFileCode.class.getEnumConstants();
        for (ChbPrdDeclareInfoFileCode code : allData){
            folderNoList.add(code.getFolderNo());
        }

        return folderNoList;
    }

    public static ChbPrdDeclareInfoFileCode genInfoByFolderNo(String folderNo){
        ChbPrdDeclareInfoFileCode[] allData = ChbPrdDeclareInfoFileCode.class.getEnumConstants();
        for (ChbPrdDeclareInfoFileCode code : allData){
            if (StringUtil.equals(code.getFolderNo(), folderNo)){
                return code;
            }
        }
        return null;
    }
}
