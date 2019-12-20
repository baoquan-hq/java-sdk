package com.baoquan.jsdk.beans;

import com.baoquan.jsdk.comm.OldResultModel;
import lombok.Builder;
import lombok.Data;


public class OldAttestationResult extends OldResultModel {
    private BaseResultData data;
    private String request_id;

    public BaseResultData getData() {
        return data;
    }

    public void setData(BaseResultData data) {
        this.data = data;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
