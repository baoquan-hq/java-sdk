package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.UserKycData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by CHENLIUFANG on 2017/4/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserKycResponse extends BaseResponse {
  private UserKycData data;

  public UserKycData getData() {
    return data;
  }

  public void setData(UserKycData data) {
    this.data = data;
  }
}
