package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.CreateAttestationData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAttestationResponse extends BaseResponse {

  private CreateAttestationData data;

  public CreateAttestationData getData() {
    return data;
  }

  public void setData(CreateAttestationData data) {
    this.data = data;
  }
}
