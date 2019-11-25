package com.baoquan.sdk.pojos.response.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAttestationData {

  /**
   * attestation number
   */
  private String no;

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }
}
