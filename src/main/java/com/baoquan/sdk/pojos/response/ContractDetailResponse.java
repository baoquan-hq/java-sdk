package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractDetailResponse {
    private Map<String, Object> detail;

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }
}
