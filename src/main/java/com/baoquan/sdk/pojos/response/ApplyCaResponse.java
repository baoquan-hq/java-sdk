package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.ApplyCaData;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class ApplyCaResponse extends BaseResponse {

  private ApplyCaData data;

  public ApplyCaData getData() {
    return data;
  }

  public void setData(ApplyCaData data) {
    this.data = data;
  }
}
