package com.baoquan.sdk.pojos.response.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by CHENLIUFANG on 2017/4/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserKycData {

  private String userId;

  private String returnMsg;

  public UserKycData() {
     }

  public UserKycData(String returnMsg) {
    this.returnMsg = returnMsg;
  }

  public UserKycData(String userId, String returnMsg) {
    this.userId = userId;
    this.returnMsg = returnMsg;
  }

  public String getReturnMsg() {
     return returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
