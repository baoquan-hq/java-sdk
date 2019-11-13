package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultResponse {

  /**
   * request id
   */
  private String result;


  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
