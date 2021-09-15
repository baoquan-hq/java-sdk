package com.baoquan.jsdk.comm;


import javax.validation.constraints.NotBlank;

public class ProcessAttestationParam extends BaseAttestationPayloadParam {
    @NotBlank(message = "evidenceType不能为空")
    private String evidenceType;

    @NotBlank(message = "nodeGroup不能为空")
    private String nodeGroup;

    private String evidenceName;

    public String getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    public String getNodeGroup() {
        return nodeGroup;
    }

    public void setNodeGroup(String nodeGroup) {
        this.nodeGroup = nodeGroup;
    }

    public String getEvidenceName() {
        return evidenceName;
    }

    public void setEvidenceName(String evidenceName) {
        this.evidenceName = evidenceName;
    }
}
