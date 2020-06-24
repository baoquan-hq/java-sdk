package com.baoquan.jsdk.comm;


import javax.validation.constraints.NotBlank;

public class ProcessAttestationParam extends BaseAttestationPayloadParam {
    @NotBlank(message = "evidenceType不能为空")
    private String evidenceType;

    public String getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }
}
