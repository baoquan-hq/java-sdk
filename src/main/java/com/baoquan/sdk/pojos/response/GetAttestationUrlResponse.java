package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.CreateAttestationData;
import com.baoquan.sdk.pojos.response.data.GetAttestationUrlData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAttestationUrlResponse extends BaseResponse {

  private GetAttestationUrlData data;

  public GetAttestationUrlData getData() {
    return data;
  }

  public void setData(GetAttestationUrlData data) {
    this.data = data;
  }
}
