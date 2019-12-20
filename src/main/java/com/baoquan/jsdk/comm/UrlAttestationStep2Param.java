package com.baoquan.jsdk.comm;



import javax.validation.constraints.NotBlank;

public class UrlAttestationStep2Param extends BaseAttestationPayloadParam {

    @NotBlank(message = "no不能为空")
    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
