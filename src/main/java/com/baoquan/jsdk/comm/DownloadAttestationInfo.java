package com.baoquan.jsdk.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.io.InputStream;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadAttestationInfo implements Serializable {
    private String fileName;
    private InputStream fileInputStream;

    private String fileType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
