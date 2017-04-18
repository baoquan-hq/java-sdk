package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.UserKycData;

/**
 * Created by CHENLIUFANG on 2017/4/14.
 */
public class UserKycResponse extends BaseResponse {
  private UserKycData data;

  public UserKycData getData() {
    return data;
  }

  public void setData(UserKycData data) {
    this.data = data;
  }
}
