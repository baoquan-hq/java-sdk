package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.CreateAttestationData;
import com.baoquan.sdk.pojos.response.data.GetAttestationUrlData;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class GetAttestationUrlResponse extends BaseResponse {

  private GetAttestationUrlData data;

  public GetAttestationUrlData getData() {
    return data;
  }

  public void setData(GetAttestationUrlData data) {
    this.data = data;
  }
}
