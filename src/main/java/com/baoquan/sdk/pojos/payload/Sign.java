package com.baoquan.sdk.pojos.payload;

import java.util.List;

/**
 * Created by sbwdlihao on 6/18/16.
 */
public class Sign {

  /**
   * ca id used to sign attachment
   */
  private String caId;

  /**
   * the keyword to locate where to sign
   */
  private List<String> keywords;

  public String getCaId() {
    return caId;
  }

  public void setCaId(String caId) {
    this.caId = caId;
  }

  public List<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }
}
