package com.baoquan.jsdk.comm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadAttestationInfo implements Serializable {
    private String no;

    private String fileName;

    private String chainHash;

    private byte[] fileInputStream;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getChainHash() {
        return chainHash;
    }

    public void setChainHash(String chainHash) {
        this.chainHash = chainHash;
    }

    public byte[] getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(byte[] fileInputStream) {
        this.fileInputStream = fileInputStream;
    }
}
