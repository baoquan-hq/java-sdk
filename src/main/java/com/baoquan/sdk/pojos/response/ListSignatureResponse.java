package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListSignatureResponse {

  private Map<String,Object> list;

  public Map<String, Object> getList() {
    return list;
  }

  public void setList(Map<String, Object> list) {
    this.list = list;
  }
}
