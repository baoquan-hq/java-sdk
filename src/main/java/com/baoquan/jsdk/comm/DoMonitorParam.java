package com.baoquan.jsdk.comm;

import java.io.Serializable;

public class DoMonitorParam implements Serializable {

    //例子：[{"times":10,"extId":"10"},{"times":9,"extId":"9"},{"times":4,"extId":"8"}]
    private String resourceList;

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
