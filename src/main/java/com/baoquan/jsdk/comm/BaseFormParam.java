package com.baoquan.jsdk.comm;

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
}
