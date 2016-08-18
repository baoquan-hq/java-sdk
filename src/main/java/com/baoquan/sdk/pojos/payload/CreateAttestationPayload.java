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
   * identity used to determine who own this attestation
   * the key of identities is one of {@link IdentityType}
   */
  private Map<IdentityType, String> identities;

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
}
