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
}
