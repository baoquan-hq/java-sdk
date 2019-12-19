package com.baoquan.jsdk.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Project Name: peacock
 * Package Name: com.baoquan.peacock.comm.ResultModel
 * Description: 返回结果基类
 * Author: la
 * Date: 2019/9/17 11:23
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultModel<T> implements Serializable {
    private boolean flag = false;

    private T data;

    private String errorMessage;

    private String statusCode;

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
}
