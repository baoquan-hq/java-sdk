package com.baoquan.jsdk.comm;

import javax.validation.constraints.NotBlank;

public class DoMonitorParam {

    //例子：[{"times":10,"extId":"10"},{"times":9,"extId":"9"},{"times":4,"extId":"8"}]
    @NotBlank(message = "资源id的json数组字符串不能为空")
    private String resourceList;

    @NotBlank(message = "回调接收结果地址不能为空")
    private String callbackUrl;

    public String getResourceList() {
        return resourceList;
    }

    public void setResourceList(String resourceList) {
        this.resourceList = resourceList;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
