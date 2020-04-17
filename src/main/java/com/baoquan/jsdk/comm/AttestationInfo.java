package com.baoquan.jsdk.comm;

import java.io.Serializable;


public class AttestationInfo implements Serializable {
    private String no;
    private String html;
    private String file_hash;
    private String status;
    private String blockchain_hash;
    private long attestation_at;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getFile_hash() {
        return file_hash;
    }

    public void setFile_hash(String file_hash) {
        this.file_hash = file_hash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBlockchain_hash() {
        return blockchain_hash;
    }

    public void setBlockchain_hash(String blockchain_hash) {
        this.blockchain_hash = blockchain_hash;
    }

    public long getAttestation_at() {
        return attestation_at;
    }

    public void setAttestation_at(long attestation_at) {
        this.attestation_at = attestation_at;
    }
}
