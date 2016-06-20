package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.CreateAttestationData;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class CreateAttestationResponse extends BaseResponse {

  private CreateAttestationData data;

  public CreateAttestationData getData() {
    return data;
  }

  public void setData(CreateAttestationData data) {
    this.data = data;
  }
}
