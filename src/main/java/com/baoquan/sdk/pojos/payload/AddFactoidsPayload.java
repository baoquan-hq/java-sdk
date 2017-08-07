package com.baoquan.sdk.pojos.payload;

import com.baoquan.sdk.BaoquanClient;

/**
 * Created by sbwdlihao on 6/17/16.
 *
 * the payloa of add factoids request
 */
public class AddFactoidsPayload extends AttestationPayload {

  /**
   * attestation number returned when call CreateAttestation of {@link BaoquanClient}
   */
  private String ano;

  private String templateId;

  public String getAno() {
    return ano;
  }

  public void setAno(String ano) {
    this.ano = ano;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
}
