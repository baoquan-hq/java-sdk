package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultModel<T> implements Serializable {
    private boolean flag = false;

    private T data;

    private String errorMessage;

    private String statusCode;

    ResultModel() {
    }

    public ResultModel(boolean flag) {
        this.flag = flag;
    }

    public ResultModel(boolean flag, String errorMessage) {
        this.flag = flag;
        this.errorMessage = errorMessage;
    }

    public ResultModel(boolean flag, String errorCode, String errorMessage) {
        this.flag = flag;
        this.errorMessage = errorMessage;
        this.statusCode = errorCode;
    }

    public ResultModel(boolean flag, T data, String errorCode, String errorMessage) {
        this.flag = flag;
        this.data = data;
        this.errorMessage = errorMessage;
        this.statusCode = errorCode;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
