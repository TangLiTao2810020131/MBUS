package com.ets.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 模板下载路径和excel文件上传路径
 */
@Component
public class FileUploadUrl
{
    @Value("${excel.upload.url}")
    private String excelUploadUrl;
    @Value("${excel.download.url}")
    private String excelDownloadUrl;

    public String getExcelUploadUrl() {
        return excelUploadUrl;
    }

    public void setExcelUploadUrl(String excelUploadUrl) {
        this.excelUploadUrl = excelUploadUrl;
    }

    public String getExcelDownloadUrl() {
        return excelDownloadUrl;
    }

    public void setExcelDownloadUrl(String excelDownloadUrl) {
        this.excelDownloadUrl = excelDownloadUrl;
    }
}