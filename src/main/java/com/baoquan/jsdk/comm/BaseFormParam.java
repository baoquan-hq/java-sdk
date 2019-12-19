package com.baoquan.jsdk.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
