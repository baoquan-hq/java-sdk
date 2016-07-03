package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.GetAttestationData;

/**
 * Created by sbwdlihao on 6/30/16.
 */
public class GetAttestationResponse extends BaseResponse{
  private GetAttestationData data;

  public GetAttestationData getData() {
    return data;
  }

  public void setData(GetAttestationData data) {
    this.data = data;
  }
}
