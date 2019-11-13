package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.ApplyCaData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyCaResponse extends BaseResponse {

  private ApplyCaData data;

  public ApplyCaData getData() {
    return data;
  }

  public void setData(ApplyCaData data) {
    this.data = data;
  }
}
