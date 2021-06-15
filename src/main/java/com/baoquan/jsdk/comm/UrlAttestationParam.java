package com.baoquan.jsdk.comm;



import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class UrlAttestationParam extends BaseAttestationPayloadParam {

    @NotBlank(message = "url不能为空")
    private String url;


    @Max(value = 2)
    @Min(value = 1)
    private Integer mode = 1;

    private String evidenceName;

    private String evidenceLabel;

    private String callBackUrl;

    private String taskType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
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

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
