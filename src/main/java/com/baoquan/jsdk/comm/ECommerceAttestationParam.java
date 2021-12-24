package com.baoquan.jsdk.comm;

import javax.validation.constraints.NotBlank;

public class ECommerceAttestationParam extends BaseAttestationPayloadParam {

    @NotBlank(message = "platform不能为空")
    private String platform;

    @NotBlank(message = "url不能为空")
    private String url;

    private String evidenceName;

    private String evidenceLabel;

    private String publisher;

    private String type;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEvidenceName() {
        return evidenceName;
    }

    public void setEvidenceName(String evidenceName) {
        this.evidenceName = evidenceName;
    }

    public String getEvidenceLabel() {
        return evidenceLabel;
    }

    public void setEvidenceLabel(String evidenceLabel) {
        this.evidenceLabel = evidenceLabel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
