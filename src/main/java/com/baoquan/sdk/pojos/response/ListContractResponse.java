package com.baoquan.sdk.pojos.response;

import java.util.List;
import java.util.Map;

public class ListContractResponse {

  private List<Map<String,Object>> list;

  public List<Map<String, Object>> getList() {
    return list;
  }

  public void setList(List<Map<String, Object>> list) {
    this.list = list;
  }
}
