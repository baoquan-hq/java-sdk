package com.baoquan.sdk.pojos.response;

import com.baoquan.sdk.pojos.response.BaseResponse;
import com.baoquan.sdk.pojos.response.data.CreateAttestationData;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class UploadContractResponse {

  private String contractId;

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }
}
