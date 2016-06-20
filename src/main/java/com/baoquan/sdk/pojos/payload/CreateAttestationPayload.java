package com.baoquan.sdk.pojos.payload;

import java.util.Map;

/**
 * Created by sbwdlihao on 6/17/16.
 *
 * the payload of create attestation request
 */
public class CreateAttestationPayload extends AttestationPayload{

  /**
   * template id
   */
  private String templateId;

  /**
   * identity used to determine who own this attestation
   * the key of identities is one of {@link IdentityType}
   */
  private Map<IdentityType, String> identities;

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
