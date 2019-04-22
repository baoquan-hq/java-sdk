package com.baoquan.sdk.pojos.payload;

import java.util.Map;

/**
 * Created by sbwdlihao on 6/17/16.
 *
 * the payload of create attestation request
 */
public class CreateAttestationPayload extends AttestationPayload{

  /**
   * unique id is used to avoid create same attestation when net exception
   */
  private String uniqueId;

  /**
   * template id
   */
  private String templateId;

  /**
   * sha256
   */
  private String sha256;

  private String url;

  private String openStatusKey;

  /**
   * identity used to determine who own this attestation
   * the key of identities is one of {@link IdentityType}
   */
  private Map<IdentityType, String> identities;
  private String fileLabel;

  public String getFileLabel() {
    return fileLabel;
  }

  public void setFileLabel(String fileLabel) {
    this.fileLabel = fileLabel;
  }
  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public Map<IdentityType, String> getIdentities() {
    return identities;
  }

  public void setIdentities(Map<IdentityType, String> identities) {
    this.identities = identities;
  }

  public String getSha256() {
    return sha256;
  }

  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getOpenStatusKey() {
    return openStatusKey;
  }

  public void setOpenStatusKey(String openStatusKey) {
    this.openStatusKey = openStatusKey;
  }
}
