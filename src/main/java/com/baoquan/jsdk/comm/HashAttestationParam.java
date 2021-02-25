package com.baoquan.jsdk.comm;


import javax.validation.constraints.NotBlank;

public class HashAttestationParam extends BaseAttestationPayloadParam {
    @NotBlank(message = "sha256不能为空")
    private String sha256;
    private String sm3;
    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getSm3() {
        return sm3;
    }

    public void setSm3(String sm3) {
        this.sm3 = sm3;
    }
}
