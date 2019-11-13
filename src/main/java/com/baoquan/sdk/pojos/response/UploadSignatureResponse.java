package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadSignatureResponse {

  private String signatureId;

  public String getSignatureId() {
    return signatureId;
  }

  public void setSignatureId(String signatureId) {
    this.signatureId = signatureId;
  }
}
