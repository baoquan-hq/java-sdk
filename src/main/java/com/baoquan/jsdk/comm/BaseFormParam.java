package com.baoquan.jsdk.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class BaseFormParam<T> {

    @NotBlank(message = "request_id不能为空")
    private String request_id;


    @NotBlank(message = "access_key不能为空")
    private String access_key;


    @NotNull(message = "tonce不能为空")
    private Long tonce;


    @NotBlank(message = "payload不能为空")
    private T payload;


    @NotBlank(message = "signature不能为空")
    private String signature;


    //
    private String requestMethod;
    private String requestURI;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public Long getTonce() {
        return tonce;
    }

    public void setTonce(Long tonce) {
        this.tonce = tonce;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }
}
