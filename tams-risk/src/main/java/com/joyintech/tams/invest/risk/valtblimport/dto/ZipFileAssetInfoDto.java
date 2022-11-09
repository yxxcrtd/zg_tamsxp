package com.joyintech.tams.invest.risk.valtblimport.dto;

import com.joyintech.framework.select.SelectOptionDto;

import java.util.Date;

/**
 * 批量上传文件资产信息
 *
 * @author swwan
 * @className ZipFileAssetInfoDto
 * @date 2021/6/18 15:40
 */
public class ZipFileAssetInfoDto {
    public ZipFileAssetInfoDto() {
    }

    public ZipFileAssetInfoDto(String fileName, SelectOptionDto info, Date date, String fileNameText) {
        this.fileName = fileName;
        this.info = info;
        this.date = date;
        this.fileNameText = fileNameText;
    }

    private String fileName;
    private SelectOptionDto info;
    private Date date;
    private String fileNameText;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SelectOptionDto getInfo() {
        return info;
    }

    public void setInfo(SelectOptionDto info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileNameText() {
        return fileNameText;
    }

    public void setFileNameText(String fileNameText) {
        this.fileNameText = fileNameText;
    }
}
