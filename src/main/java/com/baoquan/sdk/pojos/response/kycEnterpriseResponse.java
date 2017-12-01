package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.data.CreateAttestationData;

import java.util.Map;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class kycEnterpriseResponse  {

  private Map<String, Object> kycEnterprise  ;

  private String returnMsg;

  public String getReturnMsg() {
    return returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

  public Map<String, Object> getKycEnterprise() {
    return kycEnterprise;
  }

  public void setKycEnterprise(Map<String, Object> kycEnterprise) {
    this.kycEnterprise = kycEnterprise;
  }

}
