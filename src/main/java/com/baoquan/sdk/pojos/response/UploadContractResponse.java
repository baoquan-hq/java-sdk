package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.BaseResponse;
import com.baoquan.sdk.pojos.response.data.CreateAttestationData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadContractResponse {

  private String contractId;

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }
}
