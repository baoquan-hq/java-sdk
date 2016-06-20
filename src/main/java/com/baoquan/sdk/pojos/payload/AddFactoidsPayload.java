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

  public String getAno() {
    return ano;
  }

  public void setAno(String ano) {
    this.ano = ano;
  }
}
