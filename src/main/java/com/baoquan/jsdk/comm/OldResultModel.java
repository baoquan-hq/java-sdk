package com.baoquan.jsdk.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OldResultModel<T> implements Serializable {

    private String errMessage;
    private String code;
    private Boolean success = false;
    private T responseData;

    public OldResultModel() {

    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public OldResultModel(Boolean flag) {
        this.success = flag;
    }

    public OldResultModel(Boolean flag, String errorCode, String errorMessage) {
        this.success = flag;
        this.errMessage = errorMessage;
        this.code = errorCode;
    }

    public OldResultModel(Boolean flag, String errorCode, String errorMessage, T responseData) {
        this.success = flag;
        this.errMessage = errorMessage;
        this.code = errorCode;
        this.responseData = responseData;
    }

}
