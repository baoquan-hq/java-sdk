package com.baoquan.sdk.pojos.payload;

import java.util.Map;

/**
 * Created by sbwdlihao on 6/17/16.
 *
 * the payload of create attestation request
 */
public class CreateAttestation4UrlPayload extends CreateAttestationPayload{

  private String url;

  private String webName;

  private String remark;

  private String label;


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getWebName() {
    return webName;
  }

  public void setWebName(String webName) {
    this.webName = webName;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
